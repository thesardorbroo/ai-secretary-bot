package uz.sardorbroo.secretarybot.client.sdk;

import uz.sardorbroo.secretarybot.service.dto.gpt.response.GptThreadResponseDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.request.TranscriptionRequestDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.response.TranscriptionResponseDTO;

public interface AiWhisperSdkClient {

    TranscriptionResponseDTO transcribe(TranscriptionRequestDTO dto);

    GptThreadResponseDTO createThread();

    void deleteThread(String id);

}
