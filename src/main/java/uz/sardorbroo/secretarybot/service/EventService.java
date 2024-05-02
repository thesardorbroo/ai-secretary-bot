package uz.sardorbroo.secretarybot.service;

import uz.sardorbroo.secretarybot.service.dto.EventDTO;

import java.util.List;
import java.util.Optional;

public interface EventService {

    List<EventDTO> getAllEvents(String calendarId);

    Optional<EventDTO> findById(String eventId, String calendar);

    Optional<EventDTO> findEventByIndex(Integer index, String calendarId);

    void delete(String eventId);

}
