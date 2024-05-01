package uz.sardorbroo.secretarybot.config;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Configuration
public class GoogleConfig {
    private static final List<String> SCOPES = Arrays.asList(
            CalendarScopes.CALENDAR_READONLY,
            CalendarScopes.CALENDAR,
            CalendarScopes.CALENDAR_EVENTS,
            CalendarScopes.CALENDAR_EVENTS_READONLY,
            CalendarScopes.CALENDAR_SETTINGS_READONLY
    );

    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Google sends GET request to http://localhost:8080/api/login , but we don't need to create the API
     */
    private static final String REDIRECT_URI_ENDPOINT = "/api/login";

    @Value("${google.credentials.file-name}")
    private String credentials;

    @Value("${server.port}")
    private Integer port;

    @Bean
    @SneakyThrows
    public Credential initializeCredential(final NetHttpTransport HTTP_TRANSPORT, JsonFactory jsonFactory) {

        GoogleClientSecrets clientSecrets = getGoogleCredentials(jsonFactory);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, jsonFactory, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder()
                .setPort(port)
                .setCallbackPath(REDIRECT_URI_ENDPOINT)
                .build();

        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    @Bean
    @SneakyThrows
    public NetHttpTransport initializeHttpTransport() {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

    @Bean
    public JsonFactory initializeJsonFactory() {
        return GsonFactory.getDefaultInstance();
    }

    @SneakyThrows
    private InputStreamReader getCredentialsReader() {
        return new InputStreamReader(new FileInputStream(credentials));
    }

    @SneakyThrows
    private GoogleClientSecrets getGoogleCredentials(JsonFactory jsonFactory) {
        return GoogleClientSecrets.load(jsonFactory, getCredentialsReader());
    }
}
