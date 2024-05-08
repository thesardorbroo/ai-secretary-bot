package uz.sardorbroo.secretarybot.service.dto.gpt.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class GptThreadResponseDTO {

    private String id;

    private Instant createdAt;
}
