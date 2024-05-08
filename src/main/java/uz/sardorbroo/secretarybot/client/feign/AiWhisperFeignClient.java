package uz.sardorbroo.secretarybot.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import uz.sardorbroo.secretarybot.service.dto.gpt.request.GptThreadMessageRequestDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.request.GptThreadRunRequestDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.response.GptThreadMessageResponseDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.response.GptThreadMessagesResponseDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.response.GptThreadResponseDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.response.GptThreadRunResponseDTO;

@FeignClient(name = "AiWhisperFeignClient", url = "https://api.openai.com")
public interface AiWhisperFeignClient {

    @PostMapping(value = "/v1/threads", headers = {"OpenAI-Beta=assistants=v2", "Authorization=Bearer ${openai.api-key}"})
    GptThreadResponseDTO create();

    @GetMapping(value = "/v1/threads/{threadId}/messages", headers = {"OpenAI-Beta=assistants=v2", "Authorization=Bearer ${openai.api-key}"})
    GptThreadMessagesResponseDTO getMessages(@PathVariable String threadId);

    @PostMapping(value = "/v1/threads/{threadId}/messages", headers = {"OpenAI-Beta=assistants=v2", "Authorization=Bearer ${openai.api-key}"})
    GptThreadMessageResponseDTO addMessage(@PathVariable String threadId, @RequestBody GptThreadMessageRequestDTO dto);

    @PostMapping(value = "/v1/threads/{threadId}/runs", headers = {"OpenAI-Beta=assistants=v2", "Authorization=Bearer ${openai.api-key}"})
    GptThreadRunResponseDTO runThread(@PathVariable String threadId, @RequestBody GptThreadRunRequestDTO dto);

}
