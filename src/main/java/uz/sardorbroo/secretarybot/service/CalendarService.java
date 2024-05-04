package uz.sardorbroo.secretarybot.service;

import com.google.api.services.calendar.Calendar;

import java.util.Optional;

public interface CalendarService extends uz.sardorbroo.secretarybot.service.Calendar {

    Optional<Calendar> getById(String calendarId);

}
