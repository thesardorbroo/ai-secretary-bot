package uz.sardorbroo.secretarybot.service.mapper;

import com.google.api.services.calendar.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.sardorbroo.secretarybot.service.dto.EventDTO;
import uz.sardorbroo.secretarybot.service.util.DateTimeUtils;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {DateTimeUtils.class})
public interface EventMapper {

    @Mapping(target = "start", expression = "java(DateTimeUtils.convert(event.getStart().getDateTime().toStringRfc3339()))")
    @Mapping(target = "end", expression = "java(DateTimeUtils.convert(event.getEnd().getDateTime().toStringRfc3339()))")
    EventDTO toDto(Event event);

}
