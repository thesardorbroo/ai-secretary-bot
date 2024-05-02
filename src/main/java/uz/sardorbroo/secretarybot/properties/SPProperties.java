package uz.sardorbroo.secretarybot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "send-pulse")
public class SPProperties {

    private String botId;

    private String eventId;

}
