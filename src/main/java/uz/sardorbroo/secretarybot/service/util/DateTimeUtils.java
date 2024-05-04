package uz.sardorbroo.secretarybot.service.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import uz.sardorbroo.secretarybot.exception.InvalidArgumentException;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    // This is last value of minute and second
    private static final Integer LAST_MIN = 59;
    private static final Integer LAST_HOUR = 23;
    private static final Integer LAST_NANO = 999;


    public static Instant convert(String dateTimeAsString) {
        log.debug("Start converting date as string to DateTime");

        if (StringUtils.isBlank(dateTimeAsString)) {
            log.warn("Invalid argument is passed! DateTime string must not be blank!");
            throw new InvalidArgumentException("Invalid argument is passed! DateTime string must not be blank!");
        }

        ZonedDateTime zonedDateTime = parse(dateTimeAsString, DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZONE_ID));

        return zonedDateTime.toInstant();
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

    private static ZonedDateTime parse(String dateAsString, DateTimeFormatter formatter) {

        return ZonedDateTime.parse(dateAsString, formatter);
    }

}
