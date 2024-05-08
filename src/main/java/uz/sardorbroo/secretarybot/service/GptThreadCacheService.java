package uz.sardorbroo.secretarybot.service;

import uz.sardorbroo.secretarybot.constants.Constants;
import uz.sardorbroo.secretarybot.service.dto.gpt.GptThreadCacheDTO;

import java.util.Optional;

/**
 * GptThreadCacheService caches GPT thread objects by UserID
 * Cache key = UserID
 * Cache value = { "userId": userId, "threadId": threadId }
 */
public interface GptThreadCacheService {

    Optional<GptThreadCacheDTO> save(GptThreadCacheDTO dto, long ttlInSeconds);

    Optional<GptThreadCacheDTO> findById(String userId);

    void delete(String userId);

    default Optional<GptThreadCacheDTO> save(GptThreadCacheDTO dto) {
        return save(dto, Constants.GPT_THREADS_CACHE_TTL);
    }

}
