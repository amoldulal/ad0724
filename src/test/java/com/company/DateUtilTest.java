package com.company;

import com.company.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DateUtilTest {

    @Test
    public void testIsHoliday_IndependenceDay() {
        // Actual Independence Day
        assertTrue(DateUtil.isHoliday(LocalDate.of(2023, 7, 4)));
        assertTrue(DateUtil.isHoliday(LocalDate.of(2024, 7, 4)));

        // Observed Independence Day (July 4th, 2021 is a Sunday)
        assertTrue(DateUtil.isHoliday(LocalDate.of(2021, 7, 5)));

        // Not Independence Day
        assertFalse(DateUtil.isHoliday(LocalDate.of(2023, 7, 3)));
        assertFalse(DateUtil.isHoliday(LocalDate.of(2023, 8, 4)));
    }

    @Test
    public void testIsHoliday_LaborDay() {
        // First Monday of September 2023
        assertTrue(DateUtil.isHoliday(LocalDate.of(2023, 9, 4)));
        // First Monday of September 2024
        assertTrue(DateUtil.isHoliday(LocalDate.of(2024, 9, 2)));

        // Not Labor Day
        assertFalse(DateUtil.isHoliday(LocalDate.of(2023, 9, 5)));
        assertFalse(DateUtil.isHoliday(LocalDate.of(2023, 9, 3)));
    }
}
