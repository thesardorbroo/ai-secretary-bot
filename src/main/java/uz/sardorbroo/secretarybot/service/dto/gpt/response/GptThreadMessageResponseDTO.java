package uz.sardorbroo.secretarybot.service.dto.gpt.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import uz.sardorbroo.secretarybot.service.dto.gpt.response.GptThreadMessageContentTextDTO;

import java.time.Instant;
import java.util.List;

@Data
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GptThreadMessageResponseDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("thread_id")
    private String threadId;

    @JsonProperty("assistant_id")
    private String assistantId;

    @JsonProperty("run_id")
    private String runId;

    @JsonProperty("role")
    private String role;

    @JsonProperty("content")
    private List<GptThreadMessageContentTextDTO> content;
}
