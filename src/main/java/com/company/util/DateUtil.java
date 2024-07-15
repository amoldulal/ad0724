package com.company.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.DayOfWeek;

public class DateUtil {

    public static boolean isHoliday(LocalDate date) {
        return isIndependenceDay(date) || isLaborDay(date);
    }

    /**
     * Checks if a given date is Independence Day (July 4th) or its observed date.
     * If July 4th is a Saturday, it is observed on the previous Friday (July 3rd).
     * If July 4th is a Sunday, it is observed on the following Monday (July 5th).
     *
     * @param date the date to check
     * @return true if the date is Independence Day or its observed date, false otherwise
     */

    private static boolean isIndependenceDay(LocalDate date) {
        Month month = date.getMonth();
        int day = date.getDayOfMonth();
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        if (month == Month.JULY) {
            if (day == 4) {
                return true;
            } else if (day == 3 && dayOfWeek == DayOfWeek.FRIDAY) {
                return true;
            } else return day == 5 && dayOfWeek == DayOfWeek.MONDAY;
        }
        return false;
    }

    /**
     * Checks if a given date is Labor Day.
     * Labor Day is observed on the first Monday in September.
     *
     * @param date the date to check
     * @return true if the date is Labor Day, false otherwise
     */

    private static boolean isLaborDay(LocalDate date) {
        LocalDate firstOfMonth = LocalDate.of(date.getYear(), Month.SEPTEMBER, 1);

        if (date.getMonth() == Month.SEPTEMBER) {
            while (firstOfMonth.getDayOfWeek() != DayOfWeek.MONDAY) {
                firstOfMonth = firstOfMonth.plusDays(1);
            }
            return date.equals(firstOfMonth);
        }
        return false;
    }
}
