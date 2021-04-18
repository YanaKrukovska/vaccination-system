package com.krukovska.vaccinationsystem.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilTest {

    @Test
    void getDateInDaysFromNow() {
        assertTrue(convertDate(DateUtil.getDateInDaysFromNow(10)).isEqual(LocalDate.now().plusDays(10)));

    }

    @Test
    void getMidnightOfCurrentDate() {
        LocalDateTime result = convertDateTime(DateUtil.getMidnightOfCurrentDate());
        assertEquals(0, result.getHour());
        assertEquals(0, result.getMinute());
        assertEquals(0, result.getSecond());
    }

    @Test
    void convertStringToDate() {
        assertTrue( convertDate(DateUtil.convertStringToDate("2021-01-12")).isEqual(LocalDate.of(2021,1,12)));
    }


    private LocalDate convertDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    private LocalDateTime convertDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}