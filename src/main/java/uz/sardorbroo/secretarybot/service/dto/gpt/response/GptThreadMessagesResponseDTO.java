package uz.sardorbroo.secretarybot.service.dto.gpt.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class GptThreadMessagesResponseDTO {

    @JsonProperty("object")
    private String object;

    @JsonProperty("data")
    private List<GptThreadMessageResponseDTO> data;
}
