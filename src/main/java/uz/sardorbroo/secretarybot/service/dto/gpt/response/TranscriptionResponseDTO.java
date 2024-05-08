package uz.sardorbroo.secretarybot.service.dto.gpt.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class TranscriptionResponseDTO {

    private String text;

}
