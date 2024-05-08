package uz.sardorbroo.secretarybot.service.dto.gpt.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

@Data
@Accessors(chain = true)
public class GptThreadRunResponseDTO {

    @JsonProperty("id")
    private String id;

    @JsonProperty("thread_id")
    private String threadId;

    @JsonProperty("assistant_id")
    private String assistantId;

    @JsonProperty("created_at")
    private Instant createdAt;

    @JsonProperty("expires_at")
    private Instant expiresAt;

    @JsonProperty("model")
    private String model;

    @JsonProperty("instructions")
    private String instructions;

    @JsonProperty("response_format.type")
    private String responseType;
}
