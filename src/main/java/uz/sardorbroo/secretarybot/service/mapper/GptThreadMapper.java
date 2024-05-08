package uz.sardorbroo.secretarybot.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.sardorbroo.secretarybot.service.dto.gpt.GptThreadCacheDTO;
import uz.sardorbroo.secretarybot.service.dto.gpt.response.GptThreadResponseDTO;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GptThreadMapper {

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "threadId", source = "dto.id")
    GptThreadCacheDTO toCacheDto(GptThreadResponseDTO dto, String userId);

}
