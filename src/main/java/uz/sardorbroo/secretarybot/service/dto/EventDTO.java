package uz.sardorbroo.secretarybot.service.dto;

import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
}
