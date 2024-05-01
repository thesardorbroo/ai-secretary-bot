package uz.sardorbroo.secretarybot.service.mapper;

import com.google.api.services.calendar.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.sardorbroo.secretarybot.service.dto.EventDTO;

import java.time.Instant;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EventMapper {

    @Mapping(target = "start", expression = "java(getInstant(event.getStart().getDateTime().getValue()))")
    @Mapping(target = "end", expression = "java(getInstant(event.getEnd().getDateTime().getValue()))")
    EventDTO toDto(Event event);

    default Instant getInstant(long dateTimeAsLong) {

        return Instant.ofEpochMilli(dateTimeAsLong);
    }
}
