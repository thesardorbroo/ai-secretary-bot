package uz.sardorbroo.secretarybot.web.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sardorbroo.secretarybot.service.EventService;
import uz.sardorbroo.secretarybot.service.dto.EventDTO;
import uz.sardorbroo.secretarybot.web.rest.util.ResponseUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EventResource {

    private final EventService service;

    @GetMapping("/event")
    public ResponseEntity<List<EventDTO>> getAllEvents(@RequestParam("calendarId") String calendarId) {
        log.debug("REST request to fetch all events");
        List<EventDTO> events = service.getAllEvents(calendarId);
        ResponseEntity<List<EventDTO>> response = ResponseEntity.ok(events);
        log.debug("Fetch all events result. Response: {}", response);
        return response;
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<EventDTO> findById(@PathVariable String id,
                                             @RequestParam("calendarId") String calendarId) {
        log.debug("REST request to fetch event by ID");
        Optional<EventDTO> eventOptional = service.findById(id, calendarId);
        ResponseEntity<EventDTO> response = ResponseUtils.wrap(eventOptional);
        log.debug("Fetch event by ID result. Response: {}", response);
        return response;
    }

    @GetMapping("/event/index/{index}")
    public ResponseEntity<EventDTO> findEventByIndex(@PathVariable("index") Integer index,
                                                     @RequestParam("calendarId") String calendarId
    ) {
        log.debug("REST request to fetch event in index");
        Optional<EventDTO> eventOptional = service.findEventByIndex(index, calendarId);
        ResponseEntity<EventDTO> response = ResponseUtils.wrap(eventOptional);
        log.debug("Event in index result. Response: {}", response);
        return response;
    }

    @GetMapping("/event/date/{date}")
    public ResponseEntity<Collection<EventDTO>> getEventsOfDate(@PathVariable String date,
                                                                @RequestParam("calendarId") String calendarId) {
        log.debug("REST request to fetch events of date");
        List<EventDTO> events = service.getEventsOfDateWithSPDate(date, calendarId);
        ResponseEntity<Collection<EventDTO>> response = ResponseUtils.wrap(events, false);
        log.debug("Date events fetch. Response: {}", response);
        return response;
    }

}
