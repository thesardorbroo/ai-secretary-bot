package uz.sardorbroo.secretarybot.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SendPulseDocumentFeignClient", url = "https://login.sendpulse.com")
public interface SPDocumentFeignClient {

    @GetMapping("/api/telegram-service/guest/messages/media")
    Resource download(@RequestParam("bot_id") String botId, @RequestParam("file_id") String fileId);
}
