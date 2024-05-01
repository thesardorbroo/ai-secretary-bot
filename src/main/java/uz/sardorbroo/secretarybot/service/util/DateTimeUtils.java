package uz.sardorbroo.secretarybot.service.util;

import com.google.api.client.util.DateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import uz.sardorbroo.secretarybot.constants.Constants;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class DateTimeUtils {

    public static Optional<DateTime> convert(String dateTimeAsString) {
        log.debug("Start converting date as string to DateTime");

        if (StringUtils.isBlank(dateTimeAsString)) {
            log.warn("Invalid argument is passed! DateTime string must not be blank!");
            return Optional.empty();
        }

        String[] datePieces = dateTimeAsString.split(Constants.DATE_TIME_SPLITERATOR);
        if (ArrayUtils.isEmpty(datePieces) || !Objects.equals(Constants.DATE_TIME_SPLIT_MAX_COUNT, datePieces.length)) {
            log.warn("Array of date time wrong split! Array: {}", Arrays.toString(datePieces));
            return Optional.empty();
        }

        Date date = new Date(parseInt(datePieces[2]), parseInt(datePieces[1]), parseInt(datePieces[0]));
        return Optional.of(new DateTime(date));
    }

    private static Integer parseInt(String numAsString) {

        return Integer.parseInt(numAsString.trim());
    }

}
