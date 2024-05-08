package uz.sardorbroo.secretarybot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import uz.sardorbroo.secretarybot.client.feign.SPDocumentFeignClient;
import uz.sardorbroo.secretarybot.exception.InternalServerErrorException;
import uz.sardorbroo.secretarybot.exception.InvalidArgumentException;
import uz.sardorbroo.secretarybot.service.AiWhisperService;
import uz.sardorbroo.secretarybot.service.SPAiWhisperService;
import uz.sardorbroo.secretarybot.service.dto.EventWithErrorDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.response.TranscriptionResponseDTO;
import uz.sardorbroo.secretarybot.service.dto.sendpulse.SPFileDTO;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
// todo create new method which is updates old(in 5 min) transcribed text

public class SPAiWhisperServiceImpl implements SPAiWhisperService {
    private static final String AUDIO_FILE_EXTENSION = ".mp3";
    private final SPDocumentFeignClient documentClient;
    private final AiWhisperService whisperService;

    @SneakyThrows // todo remove @SneakyThrows, use try-catch
    @Override
    public Optional<EventWithErrorDTO> transcribe(SPFileDTO dto, String fileId) {
        log.debug("Start transcribing uploaded SendPulse audio. SPFileDTO: {}", dto);

        Resource resource = documentClient.download(dto.getBotId(), fileId);
        if (isNotResourceAudio(resource)) {
            log.warn("Invalid argument is passed! Resource should be audio!");
            throw new InvalidArgumentException("Invalid argument is passed! Resource should be audio!");
        }

        log.debug("Uploaded SendPulse document is downloaded successfully. Resource size: {}", resource.contentLength());

        Optional<TranscriptionResponseDTO> transcriptionOptional = whisperService.transcribe(resource.getContentAsByteArray());
        if (transcriptionOptional.isEmpty()) {
            log.warn("Audio is not transcribed!");
            throw new InternalServerErrorException("Audio is not transcribed!");
        }

        return whisperService.recognizeTranscribedText(dto.getUserId(), transcriptionOptional.get().getText());
    }

    private boolean isNotResourceAudio(Resource resource) {
        log.debug("Validate resource content type");

        if (Objects.isNull(resource)) {
            log.warn("Invalid argument is passed! Resource must not be null!");
            throw new InvalidArgumentException("Invalid argument is passed! Resource must not be null!");
        }

        String filename = resource.getFilename();
        if (StringUtils.isBlank(filename) || !filename.endsWith(AUDIO_FILE_EXTENSION)) {
            log.warn("Invalid argument is passed! Resource content type is not audio(.mp3)!");
            return false;
        }

        log.debug("Resource content type is audio");
        return true;
    }
}
