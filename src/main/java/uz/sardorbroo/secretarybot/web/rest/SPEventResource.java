package uz.sardorbroo.secretarybot.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.sardorbroo.secretarybot.constants.Constants;
import uz.sardorbroo.secretarybot.service.SPEventService;
import uz.sardorbroo.secretarybot.service.dto.SPResponseDTO;
import uz.sardorbroo.secretarybot.web.rest.util.ResponseUtils;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/send-pulse")
public class SPEventResource {

    private final SPEventService service;

    @PostMapping("/event/publish/{email}")
    public ResponseEntity<SPResponseDTO> publishEvent(@Valid @NotNull @Email(regexp = Constants.EMAIL_EXTRA_REGEX) @PathVariable String email) {
        log.debug("REST request to publish SendPulse event for email");
        Optional<SPResponseDTO> spResponseOptional = service.publishEventForEmail(email);
        ResponseEntity<SPResponseDTO> response = ResponseUtils.wrap(spResponseOptional);
        log.debug("Publishing SendPulse event result. Response: {}", response);
        return response;
    }

}
