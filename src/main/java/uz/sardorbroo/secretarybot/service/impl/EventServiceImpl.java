package uz.sardorbroo.secretarybot.service.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import uz.sardorbroo.secretarybot.exception.InvalidArgumentException;
import uz.sardorbroo.secretarybot.service.EventService;
import uz.sardorbroo.secretarybot.service.dto.EventDTO;
import uz.sardorbroo.secretarybot.service.mapper.EventMapper;
import uz.sardorbroo.secretarybot.service.util.DateTimeUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final NetHttpTransport transport;
    private final JsonFactory jsonFactory;
    private final Credential credential;
    private final EventMapper mapper;

    @SneakyThrows
    @Override
    public List<EventDTO> getAllEvents(DateTime date, String calendarId) {
        log.debug("Start fetch events by date and calendar ID. CalendarID: {} ", calendarId);

        if (Objects.isNull(date)) {
            log.warn("Invalid argument is passed! Date must not be null!");
            throw new InvalidArgumentException("Invalid argument is passed! Date must not be null!");
        }

        validateCalendarId(calendarId);

        Calendar calendar = getCalendar();

        DateTime endOfToday = new DateTime(Date.from(DateTimeUtils.getEndOfToday()));

        Events events = calendar.events()
                .list(calendarId)
                .setTimeMin(date)
                .setTimeMax(endOfToday)
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
    public List<EventDTO> getEventsOfDate(String dateAsString, String calendarId) {
        log.debug("Start fetch all events of date. Date: {} | CalendarID: {}", dateAsString, calendarId);

        validateStringDate(dateAsString);

        validateCalendarId(calendarId);

        LocalDateTime date = DateTimeUtils.convertOnlySPDate(dateAsString);
        if (!DateTimeUtils.isToday(date)) {
            log.warn("Invalid argument is passed! Date should be equal to today! Date: {}", date);
            throw new InvalidArgumentException("Invalid argument is passed! Date should be equal to today!");
        }

        DateTime dateTime = new DateTime(Date.from(DateTimeUtils.getStartOfDate(date)));
        List<EventDTO> events = getAllEvents(dateTime, calendarId);

        log.debug("Events are fetch successfully. Events count: {}", events.size());
        return events;
    }

    @Override
    @SneakyThrows
    public Optional<EventDTO> findById(String eventId, String calendarId) {
        log.debug("Start fetch event by ID. EventID: {} | CalendarID: {}", eventId, calendarId);

        validateEventId(eventId);

        validateCalendarId(calendarId);

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

        validateCalendarId(calendarId);

        List<EventDTO> events = getAllEvents(calendarId).stream()
                .filter(event -> Instant.now().isBefore(event.getStart()))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(events)) {
            log.debug("Events are not found in calendar. CalendarID: {}", calendarId);
            return Optional.empty();
        }

        EventDTO event = events.get(Integer.min(events.size(), index) - 1);

        log.debug("Event is fetched successfully by index. EventID: {} | Index: {}", event.getId(), index);
        return Optional.of(event);
    }

    @Override
    public Optional<Void> create(String calendarId) {
        throw new NotImplementedException("Create new event in calendar logic is not implemented yet!");
    }

    @Override
    public void delete(String eventId) {

    }

    @Override
    public List<EventDTO> getEventsOfDateWithSPDate(String dateAsString, String calendarId) {
        log.debug("Start fetch all events of date");

        validateStringDate(dateAsString);

        // dateAsString = dateAsString.replaceAll("\\.", "-");
        return getEventsOfDate(dateAsString, calendarId);
    }

    private void validateStringDate(String dateAsString) {
        if (StringUtils.isBlank(dateAsString)) {
            log.warn("Invalid argument is passed! Date must not be blank!");
            throw new InvalidArgumentException("Invalid argument is passed! Date must not be blank!");
        }
    }

    private void validateCalendarId(String calendarId) {
        if (StringUtils.isBlank(calendarId)) {
            log.warn("Invalid argument is passed! CalendarID must not be blank!");
            throw new InvalidArgumentException("Invalid argument is passed! CalendarID must not be blank!");
        }
    }

    private void validateEventId(String eventId) {
        if (StringUtils.isBlank(eventId)) {
            log.warn("Invalid argument is passed! EventID must not be blank!");
            throw new InvalidArgumentException("Invalid argument is passed! EventID must not be blank!");
        }
    }
}
