package uz.sardorbroo.secretarybot.config;

import com.theokanning.openai.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.sardorbroo.secretarybot.properties.OpenAiProperties;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class OpenAiConfig {

    private final OpenAiProperties properties;

    @Bean
    public OpenAiService initializeOpenAiService() {
        log.info("Configuring OpenAi service...");

        OpenAiService service = new OpenAiService(properties.getApiKey());

        log.info("OpenAi service is initialized successfully");
        return service;
    }
}
