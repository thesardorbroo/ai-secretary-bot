package uz.sardorbroo.secretarybot.constants;

public class Constants {
    public static final String APPLICATION_NAME = "Spring Boot application with Google Calendar";
    public static final Integer DATE_TIME_SPLIT_MAX_COUNT = 3;
    public static final String DATE_TIME_SPLITERATOR = "\\.";
    public static final String EMAIL_EXTRA_REGEX = "[^@]+@[^@]+\\.[^@.]+";
    public static final long GPT_THREADS_CACHE_TTL = 18000L; // 5 minute
    public static final String EVENT_SPEECH_PROMPT = """
            You should extract some data from speech and build JSON data.
            And some data has validation, if data violates the return JSON data with error code
            You can send JSON with error code if required fields are not found in speech.
            
            Data:
            Number, Field, Name Field in JSON, Type, Validation, Error message
            1, Name of event, summary, required, no validation, Name of event is not found
            2, Start date of event, start, required, date time >= present, Start time of event should be present or future
            3, End date of event, end, required, date time  > start date time, End time of event should be grater than start time of event
            4, Description, description, optional, no validation, no error code
            
            Example of valid JSON
            {
                "summary": "Daily meeting",
                "start": "11:00",
                "end": "13:00",
                "description": null
            }
                        
            Example of invalid JSON
            {
                "error": "End time of event should be grater than start time of event"
            }
            """;

}
