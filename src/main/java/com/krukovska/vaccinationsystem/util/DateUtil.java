package com.krukovska.vaccinationsystem.util;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final int ONE_DAY = 1;
    public static final int TWO_WEEKS_IN_DAYS = 14;
    public static final int SIX_WEEKS_IN_DAYS = 41;

    private DateUtil() {
    }

    public static Date getDateInDaysFromNow(int daysToSkip) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, daysToSkip);
        return calendar.getTime();
    }

    public static Date getMidnightOfCurrentDate() {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);
        return date.getTime();
    }


    @SneakyThrows
    public static Date convertStringToDate(String stringToConvert) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(stringToConvert);
    }
}
