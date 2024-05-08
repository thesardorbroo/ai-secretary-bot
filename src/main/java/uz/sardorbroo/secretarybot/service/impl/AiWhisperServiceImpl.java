package uz.sardorbroo.secretarybot.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Files;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import uz.sardorbroo.secretarybot.client.feign.AiWhisperFeignClient;
import uz.sardorbroo.secretarybot.client.sdk.AiWhisperSdkClient;
import uz.sardorbroo.secretarybot.exception.InternalServerErrorException;
import uz.sardorbroo.secretarybot.exception.InvalidArgumentException;
import uz.sardorbroo.secretarybot.properties.OpenAiProperties;
import uz.sardorbroo.secretarybot.service.AiWhisperService;
import uz.sardorbroo.secretarybot.service.GptThreadCacheService;
import uz.sardorbroo.secretarybot.service.dto.EventWithErrorDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.GptThreadCacheDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.request.GptThreadMessageRequestDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.request.GptThreadRunRequestDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.request.TranscriptionRequestDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.response.*;
import uz.sardorbroo.secretarybot.service.mapper.GptThreadMapper;
import uz.sardorbroo.secretarybot.util.StringMaskUtils;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiWhisperServiceImpl implements AiWhisperService {
    private static final String USER_ROLE_FOR_ADD_MESSAGE_TO_THREAD = "user";
    private static final String TEMP_AUDIO_FILE_NAME = "temp-audio.mp3";
    private static final String MODEL_NAME = "whisper-1";
    private final AiWhisperSdkClient sdkClient;
    private final AiWhisperFeignClient feignClient;
    private final GptThreadCacheService threadCacheService;
    private final GptThreadMapper mapper;
    private final OpenAiProperties openAiProperties;
    private final ObjectMapper objectMapper;

    @SneakyThrows // todo remove @SneakyThrows, use try-catch
    @Override
    public Optional<TranscriptionResponseDTO> transcribe(byte[] fileAsBytes) {
        log.debug("Start transcribing audio message");

        if (Objects.isNull(fileAsBytes) || fileAsBytes.length == 0) {
            log.warn("Invalid argument is passed! Audio file(byte array) must not be empty!");
            throw new InvalidArgumentException("Invalid argument is passed! Audio file(byte array) must not be empty!");
        }

        File tempFile = new File(TEMP_AUDIO_FILE_NAME);
        Files.write(fileAsBytes, tempFile);

        TranscriptionRequestDTO requestDTO = new TranscriptionRequestDTO()
                .setFile(tempFile)
                .setModel(MODEL_NAME);

        TranscriptionResponseDTO response = sdkClient.transcribe(requestDTO);

        log.debug("Audio message transcribed successfully");
        tempFile.delete();
        return Optional.of(response);
    }

    @Override
    public Optional<EventWithErrorDTO> recognizeTranscribedText(String userId, String text) {
        log.debug("Start recognizing transcribed text. UserID: {}", userId);

        StringMaskUtils.requireNotBlank(userId, "Invalid argument is passed! UserID must not be blank!");
        StringMaskUtils.requireNotBlank(text, "Invalid argument is passed! Text must not be blank!");

        GptThreadCacheDTO cachedThread = threadCacheService.findById(userId)
                .orElseGet(() -> createThread(userId));

        GptThreadMessageResponseDTO messageResponse = addMessageToThread(cachedThread, text);

        runThread(cachedThread);

        GptThreadMessagesResponseDTO response = getResultsFromThread(cachedThread);

        if (CollectionUtils.isEmpty(response.getData())) {
            log.warn("GPT thread messages must not be null! GptThreadMessageResponseDTO: {}", response);
            throw new InternalServerErrorException("GPT thread messages must not be null!");
        }

        Optional<EventWithErrorDTO> eventOptional = response.getData()
                .stream()
                .peek(message -> System.out.println("MessageID: " + message.getId() + "Message response ID: " + messageResponse.getId()))
                .filter(message -> !Objects.equals(messageResponse.getId(), message.getId()))
                .findFirst()
                .map(this::convertGptThreadTextToEventDTO);

        log.debug("EventWithErrorDTO is created successfully");
        return eventOptional;
    }

    private GptThreadCacheDTO createThread(String userId) {
        log.debug("GPT thread hasn't cached earlier. Start create GPT thread for user. UserID: {}", userId);

        GptThreadResponseDTO threadResponse = feignClient.create();
        GptThreadCacheDTO threadCacheDTO = mapper.toCacheDto(threadResponse, userId);

        return threadCacheService.save(threadCacheDTO)
                .orElseThrow(() -> new InternalServerErrorException("Something went wrong! Thread cannot cached!"));
    }

    private GptThreadMessageResponseDTO addMessageToThread(GptThreadCacheDTO cachedThread, String text) {
        log.debug("Start add message to thread. GptThreadCacheDTO: {} | Text: {}", cachedThread, text);

        GptThreadMessageRequestDTO messageRequestDTO = new GptThreadMessageRequestDTO();
        messageRequestDTO.setContent(text);
        messageRequestDTO.setRole(USER_ROLE_FOR_ADD_MESSAGE_TO_THREAD);

        GptThreadMessageResponseDTO response = feignClient.addMessage(cachedThread.getThreadId(), messageRequestDTO);
        log.debug("Message is added to thread successfully. GptThreadMessageResponsesDTO: {}", response);
        return response;
    }

    private GptThreadRunResponseDTO runThread(GptThreadCacheDTO cachedThread) {
        log.debug("Start run thread. GptThreadCachedDTO: {}", cachedThread);

        GptThreadRunRequestDTO runThreadRequestDto = new GptThreadRunRequestDTO();
        runThreadRequestDto.setModel("gpt-3.5-turbo");
        runThreadRequestDto.setAssistantId(openAiProperties.getAssistantId());

        GptThreadRunResponseDTO response = feignClient.runThread(cachedThread.getThreadId(), runThreadRequestDto);
        log.debug("Thread is run successfully. GptThreadRunResponseDTO: {}", response);
        return response;
    }

    private GptThreadMessagesResponseDTO getResultsFromThread(GptThreadCacheDTO cachedThread) {
        log.debug("Get result of process from GPT thread. CachedThreadDTO: {}", cachedThread);

        GptThreadMessagesResponseDTO response = feignClient.getMessages(cachedThread.getThreadId());
        log.debug("Result of process from GPT thread. GptThreadMessagesResponseDTO: {}", response);
        return response;
    }

    private EventWithErrorDTO convertGptThreadTextToEventDTO(GptThreadMessageResponseDTO threadMessage) {
        log.debug("Start converting GPT thread message to EventWithErrorDTO. GptThreadMessageResponseDTO: {}", threadMessage);

        if (Objects.isNull(threadMessage)) {
            log.warn("Invalid argument is passed! GPT thread message must not be null!");
            throw new InvalidArgumentException("Invalid argument is passed! GPT thread message must not be null!");
        }

        if (CollectionUtils.isEmpty(threadMessage.getContent())) {
            log.warn("Invalid argument is passed! Content of GPT thread message must not be empty! GptThreadMessageResponseDTO: {}", threadMessage);
            throw new InvalidArgumentException("Invalid argument is passed! Content of GPT thread message must not be empty!");
        }

        return threadMessage.getContent().stream()
                .filter(Objects::nonNull)
                .map(GptThreadMessageContentTextDTO::getText)
                .map(this::convertStringToEventWithErrorDto)
                .findFirst()
                .orElseThrow(() -> new InternalServerErrorException("Something went wrong! GPT thread message not converted to EventWithErrorDTO!"));
    }

    private EventWithErrorDTO convertStringToEventWithErrorDto(String text) {

        try {
            return objectMapper.readValue(text, new TypeReference<EventWithErrorDTO>() {
            });
        } catch (Exception e) {
            log.error("Error while converting String text to EventWithErrorDTO! Exception: ", e);
            throw new InternalServerErrorException("Error while converting String text to EventWithErrorDTO! Exception: " + e.getMessage());
        }
    }
}
