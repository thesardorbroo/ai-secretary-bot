package uz.sardorbroo.secretarybot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import uz.sardorbroo.secretarybot.properties.OpenAiProperties;
import uz.sardorbroo.secretarybot.properties.RedisProperties;
import uz.sardorbroo.secretarybot.properties.SPProperties;

@SpringBootApplication
@EnableFeignClients(basePackages = "uz.sardorbroo.secretarybot")
public class SecretaryBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecretaryBotApplication.class, args);
    }

}
