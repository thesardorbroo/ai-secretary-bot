package uz.sardorbroo.secretarybot.service.dto.gpt;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;
import uz.sardorbroo.secretarybot.util.StringMaskUtils;

@Getter
@ToString
@Accessors(chain = true)
public class GptThreadCacheDTO {

    private String userId;

    private String threadId;

    public GptThreadCacheDTO setUserId(String userId) {
        this.userId = StringMaskUtils.requireNotBlank(userId, "GptThreadCacheDTO.UserID must not be blank!");
        return this;
    }

    public GptThreadCacheDTO setThreadId(String threadId) {
        this.threadId = StringMaskUtils.requireNotBlank(threadId, "GptThreadCacheDTO.ThreadID must not be blank!");
        return this;
    }
}
