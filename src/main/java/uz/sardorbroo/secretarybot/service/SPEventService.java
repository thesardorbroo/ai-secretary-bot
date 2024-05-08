package uz.sardorbroo.secretarybot.service;

import uz.sardorbroo.secretarybot.service.dto.sendpulse.response.SPResponseDTO;

import java.util.Optional;

public interface SPEventService {

    Optional<SPResponseDTO> publishEventForEmail(String email, String spEventId);

}
