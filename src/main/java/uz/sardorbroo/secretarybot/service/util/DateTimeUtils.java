package uz.sardorbroo.secretarybot.service.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import uz.sardorbroo.secretarybot.exception.InvalidArgumentException;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * Resource for info about ZoneID:
 *
 * @see <a href="https://howtodoinjava.com/java/date-time/supported-zone-ids-offsets/">List of Zone Ids and Zone Offsets in Java</a>
 */
@Slf4j
public class DateTimeUtils {
    private static final SimpleDateFormat BEAUTIFUL_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final ZoneId ZONE_ID = ZoneId.of("Asia/Kolkata");
    private static final LocalDate TODAY = LocalDate.now();
    // This is last value of minute and second
    private static final Integer LAST_MIN = 59;
    private static final Integer LAST_HOUR = 23;
    private static final Integer LAST_NANO = 999;


    public static Instant convert(String dateTimeAsString) {
        log.debug("Start converting date time as string to Instant");

        validateStringDate(dateTimeAsString);

        ZonedDateTime zonedDateTime = parse(dateTimeAsString, DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZONE_ID));

        return zonedDateTime.toInstant();
    }

    public static LocalDateTime convertOnlySPDate(String dateAsString) {
        log.debug("Start converting date as string to Instant");

        validateStringDate(dateAsString);

        ZonedDateTime zonedDateTime = parse(dateAsString, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").withZone(ZONE_ID));

        return zonedDateTime.toLocalDateTime();
    }

    public static boolean isToday(LocalDateTime date) {

        return TODAY.equals(date.toLocalDate());
    }

    public static Instant getStartOfDate(LocalDateTime date) {

        // todo should refactor this
        return date.toLocalDate().atStartOfDay().minusHours(5L).toInstant(ZoneOffset.UTC);
    }

    public static Instant getEndOfToday() {

        LocalDateTime localDateTime = LocalDateTime.now()
                .withHour(LAST_HOUR)
                .withMinute(LAST_MIN)
                .withSecond(LAST_MIN)
                .withNano(LAST_NANO);

        ZonedDateTime zonedDateTime = parse(localDateTime.toString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZONE_ID));

        return zonedDateTime.toInstant();
    }

    public static String beauty(Instant date) {

        if (Objects.isNull(date)) {
            return null;
        }

        return BEAUTIFUL_FORMATTER.format(Date.from(date));
    }

    public static String beauty(LocalTime date) {

        if (Objects.isNull(date)) {
            return null;
        }

        return BEAUTIFUL_FORMATTER.format(date);
    }

    private static ZonedDateTime parse(String dateAsString, DateTimeFormatter formatter) {

        return ZonedDateTime.parse(dateAsString, formatter);
    }

    private static void validateStringDate(String dateTimeAsString) {

        if (StringUtils.isBlank(dateTimeAsString)) {
            log.warn("Invalid argument is passed! DateTime string must not be blank!");
            throw new InvalidArgumentException("Invalid argument is passed! DateTime string must not be blank!");
        }

    }
}
