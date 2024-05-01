package uz.sardorbroo.secretarybot.service;

import com.google.api.client.util.DateTime;
import uz.sardorbroo.secretarybot.constants.Constants;
import uz.sardorbroo.secretarybot.exception.InvalidArgumentException;
import uz.sardorbroo.secretarybot.service.dto.EventDTO;
import uz.sardorbroo.secretarybot.service.util.DateTimeUtils;

import java.util.List;
import java.util.Optional;

public interface EventService {

    List<EventDTO> getAllEvents(String calendarId);

    Optional<EventDTO> findById(String eventId, String calendar);

    Optional<EventDTO> findEventByIndex(Integer index, String calendarId);

    void delete(String eventId);

    default List<EventDTO> getAllEvents(String dateTimeAsString, String calendarId) {
        DateTime date = DateTimeUtils.convert(dateTimeAsString)
                .orElseThrow(() -> new InvalidArgumentException("Invalid argument is passed! Wrong date time argument. Date time spliterator: " + Constants.DATE_TIME_SPLITERATOR));

        return getAllEvents(calendarId);
    }

}
