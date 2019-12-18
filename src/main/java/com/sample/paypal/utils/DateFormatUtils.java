package com.sample.paypal.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatUtils {

    public static String toStr(ZonedDateTime dateTime, final String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        String strDateTime = dateTime.format(dateTimeFormatter);
        return strDateTime;
    }
}