package uz.sardorbroo.secretarybot.service;

import com.google.api.services.calendar.Calendar;

import java.util.Optional;

public interface CalendarService {

    Optional<Calendar> getById(String calendarId);

}
