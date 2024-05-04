package uz.sardorbroo.secretarybot.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import uz.sardorbroo.secretarybot.service.util.DateTimeUtils;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class EventDTO {

    private String id;

    private String summary;

    private String description;

    private Instant start;

    private Instant end;

    @JsonProperty("beauty_start")
    public String getBeautyStart() {
        return DateTimeUtils.beauty(start);
    }

    @JsonProperty("beauty_end")
    public String getBeautyEnd() {
        return DateTimeUtils.beauty(end);
    }
}
