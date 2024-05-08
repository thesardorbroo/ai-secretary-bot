package uz.sardorbroo.secretarybot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPooled;
import uz.sardorbroo.secretarybot.properties.RedisProperties;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisProperties properties;

    @Bean
    public JedisPooled initializeJedisPooled() {
        log.info("Start initializing Redis java client Jedis...");

        JedisPooled jedis = new JedisPooled(properties.getHost(), properties.getPort());

        log.info("Jedis is initialized successfully. Jedis ping: ", jedis.ping());
        return jedis;
    }
}
