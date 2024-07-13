package com.rentalcar.rental.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    private DateUtil() {
    }

    public static LocalDate addDays(LocalDate date, Integer days) {
        return date.plusDays(days);
    }

    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}
