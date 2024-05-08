package uz.sardorbroo.secretarybot.service.dto.sendpulse;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SPFileDTO {

    @NotBlank(message = "Invalid argument is passed! BotID must not be blank!")
    private String botId;

    @NotBlank(message = "Invalid argument is passed! UserID must not be blank!")
    private String userId;
}
