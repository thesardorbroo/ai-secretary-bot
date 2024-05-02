package uz.sardorbroo.secretarybot.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.sardorbroo.secretarybot.service.dto.SPPublishEventRequestDTO;
import uz.sardorbroo.secretarybot.service.dto.SPResponseDTO;

@FeignClient(name = "SendPulseFeignClient", url = "https://events.sendpulse.com")
public interface SPFeignClient {

    @PostMapping("/events/id/${send-pulse.event-id}")
    SPResponseDTO publishEvent(@RequestBody SPPublishEventRequestDTO dto);

}
