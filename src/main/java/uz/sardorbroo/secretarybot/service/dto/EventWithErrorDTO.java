package uz.sardorbroo.secretarybot.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventWithErrorDTO {

    private String id;

    private String summary;

    private String description;

    private String start;

    private String end;

    @JsonProperty("error")
    private String error;

}
