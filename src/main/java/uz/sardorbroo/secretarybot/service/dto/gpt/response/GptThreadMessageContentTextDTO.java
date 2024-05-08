package uz.sardorbroo.secretarybot.service.dto.gpt.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Data
@Accessors
public class GptThreadMessageContentTextDTO {

    @JsonProperty("type")
    private String type;

    private String text;

    @JsonProperty("text")
    public GptThreadMessageContentTextDTO setText(ContentTextDTO contentText) {
        if (Objects.nonNull(contentText) && StringUtils.isNotBlank(contentText.getValue())) {
            this.text = contentText.getValue();
        }

        return this;
    }

    @Data
    @Accessors(chain = true)
    private static class ContentTextDTO {

        private String value;

    }

}
