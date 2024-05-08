package uz.sardorbroo.secretarybot.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.sardorbroo.secretarybot.service.dto.sendpulse.request.SPPublishEventRequestDTO;
import uz.sardorbroo.secretarybot.service.dto.sendpulse.response.SPResponseDTO;

@FeignClient(name = "SendPulseFeignClient", url = "https://events.sendpulse.com")
public interface SPFeignClient {

    @PostMapping("/events/id/{id}")
    SPResponseDTO publishEvent(@PathVariable String id, @RequestBody SPPublishEventRequestDTO dto);

}
