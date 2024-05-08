package uz.sardorbroo.secretarybot.service.dto.gpt.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GptThreadMessageRequestDTO {

    private String role;

    private String content;

}
