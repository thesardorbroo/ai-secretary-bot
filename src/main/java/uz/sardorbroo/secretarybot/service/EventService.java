package uz.sardorbroo.secretarybot.service;

import com.google.api.client.util.DateTime;
import uz.sardorbroo.secretarybot.service.dto.EventDTO;

import java.util.List;
import java.util.Optional;

public interface EventService extends Calendar {

    List<EventDTO> getAllEvents(DateTime date, String calendarId);

    List<EventDTO> getEventsOfDate(String dateAsString, String calendarId);

    Optional<EventDTO> findById(String eventId, String calendar);

    Optional<EventDTO> findEventByIndex(Integer index, String calendarId);

    Optional<Void> create(String calendarId);

    void delete(String eventId);

    default List<EventDTO> getAllEvents(String calendarId) {
        return getAllEvents(new DateTime(System.currentTimeMillis()), calendarId);
    }

    List<EventDTO> getEventsOfDateWithSPDate(String dateAsString, String calendarId);

}
