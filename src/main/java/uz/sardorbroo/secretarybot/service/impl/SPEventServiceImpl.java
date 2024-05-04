package uz.sardorbroo.secretarybot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import uz.sardorbroo.secretarybot.client.SPFeignClient;
import uz.sardorbroo.secretarybot.exception.InvalidArgumentException;
import uz.sardorbroo.secretarybot.properties.SPProperties;
import uz.sardorbroo.secretarybot.service.SPEventService;
import uz.sardorbroo.secretarybot.service.dto.SPPublishEventRequestDTO;
import uz.sardorbroo.secretarybot.service.dto.SPResponseDTO;
import uz.sardorbroo.secretarybot.service.enumeration.ChatBotsChannel;
import uz.sardorbroo.secretarybot.util.StringMaskUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SPEventServiceImpl implements SPEventService {

    private final SPFeignClient client;
    private final SPProperties properties;

    @Override
    public Optional<SPResponseDTO> publishEventForEmail(String email) {
        log.debug("Start publish SendPulse event for given email. Email: {}", StringMaskUtils.mask(email));

        if (StringUtils.isBlank(email)) {
            log.warn("Invalid argument is passed! Email must not be blank!");
            throw new InvalidArgumentException("Invalid argument is passed! Email must not be blank!");
        }

        SPPublishEventRequestDTO requestDTO = new SPPublishEventRequestDTO();
        requestDTO.setEmail(email);
        requestDTO.setBotId(properties.getBotId());
        requestDTO.setBin(UUID.randomUUID().toString());
        // Only with Telegram now
        requestDTO.setChatBotsChannel(ChatBotsChannel.TG);

        log.debug("DTO: {}", requestDTO);

        SPResponseDTO response = client.publishEvent(requestDTO);
        if (Objects.isNull(response) || !Boolean.TRUE.equals(response.getResult())) {
            log.warn("Unexpected response from SendPulse! Response: {}", response);
            throw new RuntimeException("Unexpected response from SendPulse! Response: " + response);
        }

        log.debug("SendPulse event is published successfully. Response: {}", response);
        return Optional.of(response);
    }
}
