package uz.sardorbroo.secretarybot.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sardorbroo.secretarybot.constants.Constants;
import uz.sardorbroo.secretarybot.service.SPEventService;
import uz.sardorbroo.secretarybot.service.dto.sendpulse.response.SPResponseDTO;
import uz.sardorbroo.secretarybot.web.rest.util.ResponseUtils;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/send-pulse")
public class SPEventResource {

    private final SPEventService service;

    @PostMapping("/event/publish")
    public ResponseEntity<SPResponseDTO> publishEvent(@Valid @Email(regexp = Constants.EMAIL_EXTRA_REGEX) @RequestParam("email") String email,
                                                      @RequestParam("sp_event_id") String spEventId) {
        log.debug("REST request to publish SendPulse event for email");
        Optional<SPResponseDTO> spResponseOptional = service.publishEventForEmail(email, spEventId);
        ResponseEntity<SPResponseDTO> response = ResponseUtils.wrap(spResponseOptional);
        log.debug("Publishing SendPulse event result. Response: {}", response);
        return response;
    }

}
