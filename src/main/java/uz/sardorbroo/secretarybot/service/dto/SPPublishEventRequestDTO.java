package uz.sardorbroo.secretarybot.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import uz.sardorbroo.secretarybot.service.enumeration.ChatBotsChannel;
import uz.sardorbroo.secretarybot.util.StringMaskUtils;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SPPublishEventRequestDTO {

    @JsonProperty("email")
    private String email;

    @JsonProperty("chatbots_channel")
    private String chatBotsChannel;

    @JsonProperty("bot_id")
    private String botId;

    public SPPublishEventRequestDTO setChatBotsChannel(ChatBotsChannel channel) {

        if (Objects.nonNull(channel)) {
            this.chatBotsChannel = channel.getChannel();
        }

        return this;
    }

    @Override
    public String toString() {
        return "SPPublishEventRequestDTO{" +
                "email='" + StringMaskUtils.mask(email) + '\'' +
                ", chatBotsChannel=" + chatBotsChannel +
                ", botId='" + botId + '\'' +
                '}';
    }
}
