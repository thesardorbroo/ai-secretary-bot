package uz.sardorbroo.secretarybot.service;

import uz.sardorbroo.secretarybot.service.dto.EventWithErrorDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.response.TranscriptionResponseDTO;

import java.util.Optional;

public interface AiWhisperService {

    Optional<TranscriptionResponseDTO> transcribe(byte[] fileAsBytes);

    Optional<EventWithErrorDTO> recognizeTranscribedText(String userId, String text);

}
