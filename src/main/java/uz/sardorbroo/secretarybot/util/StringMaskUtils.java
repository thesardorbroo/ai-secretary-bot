package uz.sardorbroo.secretarybot.util;

import org.apache.commons.lang3.StringUtils;

public class StringMaskUtils {
    private static final Integer MAX_WIDTH_OF_MASKED_STRING = 8;
    private static final String MASK = "****";

    public static String mask(String source) {

        if (StringUtils.isBlank(source)) {
            return null;
        }

        return StringUtils.abbreviate(source, MASK, MAX_WIDTH_OF_MASKED_STRING);
    }
}
