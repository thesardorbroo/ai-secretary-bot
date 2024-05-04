package uz.sardorbroo.secretarybot.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import uz.sardorbroo.secretarybot.constants.Constants;

public interface Calendar {

    NetHttpTransport getTransport();

    JsonFactory getJsonFactory();

    Credential getCredential();

    default com.google.api.services.calendar.Calendar getCalendar() {

        return new com.google.api.services.calendar.Calendar.Builder(getTransport(), getJsonFactory(), getCredential())
                .setApplicationName(Constants.APPLICATION_NAME)
                .build();
    }
}
