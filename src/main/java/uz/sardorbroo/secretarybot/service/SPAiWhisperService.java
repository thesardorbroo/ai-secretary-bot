package uz.sardorbroo.secretarybot.service;

import uz.sardorbroo.secretarybot.service.dto.EventWithErrorDTO;
import uz.sardorbroo.secretarybot.service.dto.sendpulse.SPFileDTO;

import java.util.Optional;

public interface SPAiWhisperService {

    Optional<EventWithErrorDTO> transcribe(SPFileDTO dto, String fileId);

}
