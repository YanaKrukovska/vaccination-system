package com.krukovska.vaccinationsystem.util;

import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date getTwoWeeksFromNow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        return calendar.getTime();
    }

    @SneakyThrows
    public static Date convertStringToDate(String stringToConvert) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.parse(stringToConvert);
    }
}
