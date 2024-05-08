package uz.sardorbroo.secretarybot.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sardorbroo.secretarybot.service.SPAiWhisperService;
import uz.sardorbroo.secretarybot.service.dto.EventWithErrorDTO;
import uz.sardorbroo.secretarybot.service.dto.sendpulse.SPFileDTO;
import uz.sardorbroo.secretarybot.web.rest.util.ResponseUtils;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai/whisper")
public class AiWhisperResource {

    private final SPAiWhisperService service;

    @PostMapping("/transcribe")
    public ResponseEntity<EventWithErrorDTO> transcribe(@Valid @RequestBody SPFileDTO dto, @RequestParam("file_id") String fileId) {
        log.debug("REST request to transcribe audio");
        Optional<EventWithErrorDTO> responseOptional = service.transcribe(dto, fileId);
        ResponseEntity<EventWithErrorDTO> response = ResponseUtils.wrap(responseOptional);
        log.debug("Transcribing audio result. Response: {}", response);
        return response;
    }

    // todo create /api/ai/whisper/transcribe/cancel API for canceling event creation flow and deleting GPT threads, caches

}
