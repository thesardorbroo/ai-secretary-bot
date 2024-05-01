package uz.sardorbroo.secretarybot.service.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import uz.sardorbroo.secretarybot.constants.Constants;
import uz.sardorbroo.secretarybot.exception.InvalidArgumentException;
import uz.sardorbroo.secretarybot.service.EventService;
import uz.sardorbroo.secretarybot.service.dto.EventDTO;
import uz.sardorbroo.secretarybot.service.mapper.EventMapper;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final NetHttpTransport transport;
    private final JsonFactory jsonFactory;
    private final Credential credential;
    private final EventMapper mapper;

    @SneakyThrows
    @Override
    public List<EventDTO> getAllEvents(String calendarId) {
        log.debug("Start fetch events by date and calendar ID. CalendarID: {} ", calendarId);

        if (StringUtils.isBlank(calendarId)) {
            log.warn("Invalid argument is passed! CalendarID must not be blank!");
            throw new InvalidArgumentException("Invalid argument is passed! CalendarID must not be blank!");
        }

        Calendar calendar = getCalendar();

        Events events = calendar.events()
                .list(calendarId)
                .setTimeMin(new DateTime(new Date()))
                .setSingleEvents(true)
                .setOrderBy("startTime")
                .execute();

        List<EventDTO> items = events.getItems()
                .stream()
                .filter(event -> StringUtils.isNotBlank(event.getSummary()))
                .map(mapper::toDto)
                .collect(Collectors.toList());

        log.debug("Events are fetch successfully. Events count: {}", items.size());
        return items;
    }

    @Override
    @SneakyThrows
    public Optional<EventDTO> findById(String eventId, String calendarId) {
        log.debug("Start fetch event by ID. EventID: {} | CalendarID: {}", eventId, calendarId);

        if (StringUtils.isBlank(eventId)) {
            log.warn("Invalid argument is passed! EventID must not be blank!");
            throw new InvalidArgumentException("Invalid argument is passed! EventID must not be blank!");
        }
        if (StringUtils.isBlank(calendarId)) {
            log.warn("Invalid argument is passed! CalendarID must not be blank!");
            throw new InvalidArgumentException("Invalid argument is passed! CalendarID must not be blank!");
        }

        Calendar calendar = getCalendar();

        Event event = calendar.events()
                .get(calendarId, eventId)
                .execute();

        log.debug("Event is fetch successfully. Event: {}", event);
        return Optional.of(mapper.toDto(event));
    }

    @Override
    public Optional<EventDTO> findEventByIndex(Integer index, String calendarId) {
        log.debug("Start fetch one event in index. CalendarID: {} | Index: {}", calendarId, index);

        if (Objects.isNull(index) || index <= 0) {
            log.warn("Invalid argument is passed! Index must not be negative!");
            throw new InvalidArgumentException("Invalid argument is passed! Index must not be negative!");
        }

        if (StringUtils.isBlank(calendarId)) {
            log.warn("Invalid argument is passed! Index must not be blank!");
            throw new InvalidArgumentException("Invalid argument is passed! Index must not be blank!");
        }

        List<EventDTO> events = getAllEvents(calendarId);
        if (CollectionUtils.isEmpty(events)) {
            log.debug("Events are not found in calendar. CalendarID: {}", calendarId);
            return Optional.empty();
        }

        EventDTO event = events.get(Integer.min(events.size(), index) - 1);

        log.debug("Event is fetched successfully by index. EventID: {} | Index: {}", event.getId(), index);
        return Optional.of(event);
    }

    @Override
    public void delete(String eventId) {

    }

    private Calendar getCalendar() {

        return new Calendar.Builder(transport, jsonFactory, credential)
                .setApplicationName(Constants.APPLICATION_NAME)
                .build();
    }
}
