package uz.sardorbroo.secretarybot.util;

import org.apache.commons.lang3.StringUtils;
import uz.sardorbroo.secretarybot.exception.InvalidArgumentException;

public class StringMaskUtils {
    private static final Integer MAX_WIDTH_OF_MASKED_STRING = 8;
    private static final String MASK = "****";

    public static String mask(String source) {

        if (StringUtils.isBlank(source)) {
            return null;
        }

        return StringUtils.abbreviate(source, MASK, MAX_WIDTH_OF_MASKED_STRING);
    }

    // todo move it to another class or rename StringMaskUtils to another, not StringUtils
    public static String requireNotBlank(String source, String message) {

        if (StringUtils.isBlank(source)) {
            throw new InvalidArgumentException(message);
        }

        return source;
    }
}
