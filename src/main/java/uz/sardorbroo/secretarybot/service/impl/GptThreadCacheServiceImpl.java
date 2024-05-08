package uz.sardorbroo.secretarybot.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;
import uz.sardorbroo.secretarybot.exception.InternalServerErrorException;
import uz.sardorbroo.secretarybot.exception.InvalidArgumentException;
import uz.sardorbroo.secretarybot.service.GptThreadCacheService;
import uz.sardorbroo.secretarybot.service.dto.gpt.GptThreadCacheDTO;
import uz.sardorbroo.secretarybot.util.StringMaskUtils;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GptThreadCacheServiceImpl implements GptThreadCacheService {

    private final JedisPooled jedis;
    private final ObjectMapper objectMapper;

    @Override
    // todo should add timer task for deleting threads in GPT after running time to live
    public Optional<GptThreadCacheDTO> save(GptThreadCacheDTO dto, long ttlInSeconds) {
        log.debug("Start caching GPT threads by UserID. GptThreadCacheDTO: {}", dto);

        Objects.requireNonNull(dto, "Invalid argument is passed! GptThreadCacheDTO must not be null!");

        if (ttlInSeconds <= 0) {
            log.warn("Invalid argument is passed! Time to live must be positive");
            throw new InvalidArgumentException("Invalid argument is passed! Time to live must be positive");
        }

        if (jedis.exists(dto.getUserId())) {
            log.warn("GPT thread has already cached! Cached object ID: {}", dto.getUserId());
            throw new InvalidArgumentException("GPT thread has already cached!");
        }

        jedis.set(dto.getUserId(), convertToString(dto));
        jedis.expire(dto.getUserId(), ttlInSeconds);

        log.debug("GPT thread is cached successfully");
        return Optional.of(dto);
    }

    @Override
    public Optional<GptThreadCacheDTO> findById(String userId) {
        log.debug("Start find cached GPT thread by UserID. UserID: {}", userId);

        StringMaskUtils.requireNotBlank(userId, "Invalid argument is passed! UserID must not be blank!");

        if (!jedis.exists(userId)) {
            log.warn("Cached GPT thread is not found by given UserID! UserID: {}", userId);
            return Optional.empty();
        }

        GptThreadCacheDTO cachedThread = convertToDto(String.valueOf(jedis.get(userId)));

        log.debug("Cached GPT thread is found by given UserID. GPT thread: {}", cachedThread);
        return Optional.of(cachedThread);
    }

    @Override
    public void delete(String userId) {
        log.debug("Start delete cached GPT thread by UserID. UserID: {}", userId);

        StringMaskUtils.requireNotBlank(userId, "Invalid argument is passed! UserID must not be blank!");

        if (!jedis.exists(userId)) {
            log.warn("Cached GPT thread is not found! UserID: {}", userId);
            throw new InvalidArgumentException("Cached GPT thread is not found!");
        }

        long deletedGptThreadResult = jedis.jsonDel(userId);
        log.debug("Cached GPT thread is deleted successfully. Result: {}", deletedGptThreadResult);
    }

    private String convertToString(GptThreadCacheDTO dto) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
        } catch (Exception e) {
            log.error("Error while converting GptThreadCacheDTO to String! Exception: ", e);
            throw new InternalServerErrorException("Error while converting GptThreadCacheDTO to String! Exception: " + e.getMessage());
        }
    }

    private GptThreadCacheDTO convertToDto(String dtoAsString) {
        try {
            return objectMapper.readValue(dtoAsString, new TypeReference<GptThreadCacheDTO>() {
            });
        } catch (Exception e) {
            log.error("Error while converting GptThreadCacheDTO to String! Exception: ", e);
            throw new InternalServerErrorException("Error while converting GptThreadCacheDTO to String! Exception: " + e.getMessage());
        }
    }
}
