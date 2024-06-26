package uz.sardorbroo.secretarybot.service.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.calendar.Calendar;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import uz.sardorbroo.secretarybot.exception.InvalidArgumentException;
import uz.sardorbroo.secretarybot.service.CalendarService;

import java.util.Optional;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final NetHttpTransport transport;
    private final JsonFactory jsonFactory;
    private final Credential credential;

    @Override
    public Optional<Calendar> getById(String calendarId) {
        log.debug("Start fetch calendar by ID. ID: {}", calendarId);

        if (StringUtils.isBlank(calendarId)) {
            log.warn("Invalid argument is passed! CalendarID must not be blank!");
            throw new InvalidArgumentException("Invalid argument is passed! CalendarID must not be blank!");
        }

        throw new NotImplementedException("Get Google calendar by ID logic is not implemented yet");
    }
}
