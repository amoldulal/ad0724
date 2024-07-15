package com.company;


import com.company.model.RentalAgreement;
import com.company.model.ToolType;
import com.company.service.RentalService;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


public class RentalServiceTest {

    @Test
    public void testValidCheckout() {
        RentalService rentalService = new RentalService();

        /*
        Test case 1: Discount percent out of range
        Tool code: JAKR, Checkout date: 9/3/15, Rental days: 5, Discount: 101%
        Expected: Exception thrown for invalid discount percent
        */
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("JAKR", 5, 101, LocalDate.of(2015, 9, 3));});
        String expectedMessage1 = "Discount percent must be between 0 and 100.";
        assertTrue(exception1.getMessage().contains(expectedMessage1));


        /*
        Test case 2: Valid rental with holiday and weekend
        Tool code: LADW, Checkout date: 7/2/20, Rental days: 3, Discount: 10%
        Rental period: 7/3/20 to 7/5/20
        July 4, 2020 (Saturday): Independence Day observed on July 3, 2020 (Friday) - no charge
        Charge days: 1 (weekend charge on Sunday)
        */
        RentalAgreement agreement2 = rentalService.checkout("LADW", 3, 10, LocalDate.of(2020, 7, 2));
        assertEquals("LADW", agreement2.getToolCode());
        assertEquals(ToolType.LADDER, agreement2.getToolType());
        assertEquals("Werner", agreement2.getToolBrand());
        assertEquals(3, agreement2.getRentalDays());
        assertEquals(LocalDate.of(2020, 7, 2), agreement2.getCheckoutDate());
        assertEquals(LocalDate.of(2020, 7, 5), agreement2.getDueDate());
        assertEquals(BigDecimal.valueOf(1.99), agreement2.getDailyRentalCharge());
        assertEquals(1, agreement2.getChargeDays()); // Adjusted based on holidays and weekend
        assertEquals(BigDecimal.valueOf(1.99).setScale(2, RoundingMode.HALF_UP), agreement2.getPreDiscountCharge()); // Adjusted pre-discount charge
        assertEquals(10, agreement2.getDiscountPercent());
        assertEquals(BigDecimal.valueOf(0.20).setScale(2, RoundingMode.HALF_UP), agreement2.getDiscountAmount()); // Adjusted discount amount
        assertEquals(BigDecimal.valueOf(1.79).setScale(2, RoundingMode.HALF_UP), agreement2.getFinalCharge()); // Adjusted final charge

        /*
        Test case 3: Rental period including holidays and weekends
        Tool code: CHNS, Checkout date: 7/2/15, Rental days: 5, Discount: 25%
        Rental period: 7/3/15 to 7/7/15
        Charge days: 3 (includes holidays, excludes weekend)
        */
        RentalAgreement agreement3 = rentalService.checkout("CHNS", 5, 25, LocalDate.of(2015, 7, 2));
        assertEquals("CHNS", agreement3.getToolCode());
        assertEquals(ToolType.CHAINSAW, agreement3.getToolType());
        assertEquals("Stihl", agreement3.getToolBrand());
        assertEquals(5, agreement3.getRentalDays());
        assertEquals(LocalDate.of(2015, 7, 2), agreement3.getCheckoutDate());
        assertEquals(LocalDate.of(2015, 7, 7), agreement3.getDueDate());
        assertEquals(BigDecimal.valueOf(1.49), agreement3.getDailyRentalCharge());
        assertEquals(3, agreement3.getChargeDays()); // 3 days charged including holidays
        assertEquals(BigDecimal.valueOf(4.47).setScale(2, RoundingMode.HALF_UP), agreement3.getPreDiscountCharge());
        assertEquals(25, agreement3.getDiscountPercent());
        assertEquals(BigDecimal.valueOf(1.12).setScale(2, RoundingMode.HALF_UP), agreement3.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(3.35).setScale(2, RoundingMode.HALF_UP), agreement3.getFinalCharge());


        /*
        Test case 4: Rental period including Labor Day
        Tool code: JAKD, Checkout date: 9/3/15, Rental days: 6, Discount: 0%
        Rental period: 9/4/15 to 9/9/15
        Charge days: 3 (weekdays only, no weekend charge, no holiday charge)
        */
        RentalAgreement agreement4 = rentalService.checkout("JAKD", 6, 0, LocalDate.of(2015, 9, 3));
        assertEquals("JAKD", agreement4.getToolCode());
        assertEquals(ToolType.JACKHAMMER, agreement4.getToolType());
        assertEquals("DeWalt", agreement4.getToolBrand());
        assertEquals(6, agreement4.getRentalDays());
        assertEquals(LocalDate.of(2015, 9, 3), agreement4.getCheckoutDate());
        assertEquals(LocalDate.of(2015, 9, 9), agreement4.getDueDate());
        assertEquals(BigDecimal.valueOf(2.99), agreement4.getDailyRentalCharge());
        assertEquals(3, agreement4.getChargeDays()); // One holiday (Labor Day) and weekends are not charged
        assertEquals(BigDecimal.valueOf(8.97).setScale(2, RoundingMode.HALF_UP), agreement4.getPreDiscountCharge());
        assertEquals(0, agreement4.getDiscountPercent());
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), agreement4.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(8.97).setScale(2, RoundingMode.HALF_UP), agreement4.getFinalCharge());

        /*
        Test case 5: Rental period including Independence Day
        Tool code: JAKR, Checkout date: 7/2/15, Rental days: 9, Discount: 0%
        Rental period: 7/3/15 to 7/11/15
        July 3, 2015 (Friday): Observed holiday for Independence Day - no charge
        July 4, 2015 (Saturday): Actual Independence Day - no charge
        Charge days: 5 (weekdays only, no weekend charge)
        */
        RentalAgreement agreement5 = rentalService.checkout("JAKR", 9, 0, LocalDate.of(2015, 7, 2));
        assertEquals("JAKR", agreement5.getToolCode());
        assertEquals(ToolType.JACKHAMMER, agreement5.getToolType());
        assertEquals("Ridgid", agreement5.getToolBrand());
        assertEquals(9, agreement5.getRentalDays());
        assertEquals(LocalDate.of(2015, 7, 2), agreement5.getCheckoutDate());
        assertEquals(LocalDate.of(2015, 7, 11), agreement5.getDueDate());
        assertEquals(BigDecimal.valueOf(2.99), agreement5.getDailyRentalCharge());
        assertEquals(5, agreement5.getChargeDays()); // One observed holiday (Independence Day) and weekends not charged
        assertEquals(BigDecimal.valueOf(14.95).setScale(2, RoundingMode.HALF_UP), agreement5.getPreDiscountCharge());
        assertEquals(0, agreement5.getDiscountPercent());
        assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), agreement5.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(14.95).setScale(2, RoundingMode.HALF_UP), agreement5.getFinalCharge());

        /*
        Test case 6: Rental period including observed holiday and weekends
        Tool code: JAKR, Checkout date: 7/2/20, Rental days: 4, Discount: 50%
        Rental period: 7/3/20 to 7/6/20
        Charge days: 1 (weekdays only, no weekend charge, observed holiday not charged)
        */
        RentalAgreement agreement6 = rentalService.checkout("JAKR", 4, 50, LocalDate.of(2020, 7, 2));
        assertEquals("JAKR", agreement6.getToolCode());
        assertEquals(ToolType.JACKHAMMER, agreement6.getToolType());
        assertEquals("Ridgid", agreement6.getToolBrand());
        assertEquals(4, agreement6.getRentalDays());
        assertEquals(LocalDate.of(2020, 7, 2), agreement6.getCheckoutDate());
        assertEquals(LocalDate.of(2020, 7, 6), agreement6.getDueDate());
        assertEquals(BigDecimal.valueOf(2.99), agreement6.getDailyRentalCharge());
        assertEquals(1, agreement6.getChargeDays()); // Only Monday is charged
        assertEquals(BigDecimal.valueOf(2.99).setScale(2, RoundingMode.HALF_UP), agreement6.getPreDiscountCharge());
        assertEquals(50, agreement6.getDiscountPercent());
        assertEquals(BigDecimal.valueOf(1.50).setScale(2, RoundingMode.HALF_UP), agreement6.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(1.49).setScale(2, RoundingMode.HALF_UP), agreement6.getFinalCharge());
    }

    @Test
    public void testInvalidRentalDays() {
        RentalService rentalService = new RentalService();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("LADW", 0, 10, LocalDate.of(2023, 7, 2));
        });

        String expectedMessage = "Rental day count must be 1 or greater.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testInvalidDiscountPercent() {
        RentalService rentalService = new RentalService();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("LADW", 3, 110, LocalDate.of(2023, 7, 2));
        });

        String expectedMessage = "Discount percent must be between 0 and 100.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testNegativeDiscountPercent() {
        RentalService rentalService = new RentalService();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            rentalService.checkout("LADW", 3, -10, LocalDate.of(2023, 7, 2));
        });

        String expectedMessage = "Discount percent must be between 0 and 100.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}