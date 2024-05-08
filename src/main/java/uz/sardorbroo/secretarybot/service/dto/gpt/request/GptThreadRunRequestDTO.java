package uz.sardorbroo.secretarybot.service.dto.gpt.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GptThreadRunRequestDTO {

    @JsonProperty("assistant_id")
    private String assistantId;

    @JsonProperty("model")
    private String model;

}
