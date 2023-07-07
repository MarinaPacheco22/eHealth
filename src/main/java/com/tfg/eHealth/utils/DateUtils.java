package com.tfg.eHealth.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static long toMilliseconds(LocalDateTime localDateTime) {
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    public static String toString(LocalDateTime localDateTime, String format) {
        String result = null;
        if (localDateTime != null && format != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            result = formatter.format(localDateTime);
        }
        return result;
    }

    public static String toString(LocalDate localDate, String format) {
        String result = null;
        if (localDate != null && format != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            result = formatter.format(localDate);
        }
        return result;
    }

    public static String toString(Date date, String format) {
        String result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            result = sdf.format(date);
        } catch (Exception ignore) {
        }

        return result;
    }

    public static String toString(LocalTime localTime, String format) {

        String result = null;
        if (localTime != null && format != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            result = localTime.format(formatter);
        }
        return result;


    }

    public static LocalDateTime toLocalDateTime(String dateTime, String format) {
        LocalDateTime result = null;
        if (dateTime != null && format != null) {
            DateTimeFormatter formatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern(format).toFormatter(Locale.US);
            result = LocalDateTime.parse(dateTime, formatter);
        }
        return result;
    }

    public static LocalDate toLocalDate(String dateTime, String format) {
        LocalDate result = null;
        if (dateTime != null && format != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
            result = LocalDate.parse(dateTime, formatter);
        }
        return result;
    }


}
