package uz.sardorbroo.secretarybot.client.sdk.impl;

import com.theokanning.openai.audio.CreateTranscriptionRequest;
import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.sardorbroo.secretarybot.client.sdk.AiWhisperSdkClient;
import uz.sardorbroo.secretarybot.service.dto.gpt.response.GptThreadResponseDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.request.TranscriptionRequestDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.response.TranscriptionResponseDTO;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiWhisperSdkClientImpl implements AiWhisperSdkClient {

    private final OpenAiService service;

    @Override
    public TranscriptionResponseDTO transcribe(TranscriptionRequestDTO dto) {

        CreateTranscriptionRequest request = new CreateTranscriptionRequest();
        request.setLanguage("en");
        request.setModel("whisper-1");

        var result = service.createTranscription(request, dto.getFile());

        TranscriptionResponseDTO response = new TranscriptionResponseDTO();
        response.setText(result.getText());

        log.debug("Audio file is transcribed successfully");
        return response;
    }

    @Override
    public GptThreadResponseDTO createThread() {

        return null;
    }

    @Override
    public void deleteThread(String id) {

    }
}
