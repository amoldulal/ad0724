package com.company.service;

import com.company.exception.InvalidDiscountException;
import com.company.exception.InvalidRentalDaysException;
import com.company.exception.InvalidToolCodeException;
import com.company.model.RentalAgreement;
import com.company.model.Tool;
import com.company.util.DateUtil;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;


public class RentalService {
    private ToolService toolService = new ToolService();

    /**
     * Processes the rental checkout operation.
     *
     * @param toolCode        the code of the tool to be rented
     * @param rentalDays      the number of days the tool will be rented
     * @param discountPercent the discount percentage to be applied
     * @param checkoutDate    the date the tool is checked out
     * @return a RentalAgreement object containing the details of the rental
     */

    public RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, LocalDate checkoutDate) throws IllegalArgumentException {
        validateInputs(rentalDays, discountPercent);

        Tool tool = toolService.findToolByCode(toolCode);
        if (tool == null) {
            throw new InvalidToolCodeException("Invalid tool code. Tool code does not exist");
        }

        LocalDate dueDate = checkoutDate.plusDays(rentalDays);
        int chargeDays = calculateBillableDays(tool, checkoutDate, dueDate);

        BigDecimal preDiscountCharge = calculatePreDiscountCharge(tool, chargeDays);
        BigDecimal discountAmount = calculateDiscountAmount(preDiscountCharge, discountPercent);
        BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);

        return new RentalAgreement(
                toolCode, tool.getType(), tool.getBrand(), rentalDays, checkoutDate, dueDate, tool.getDailyCharge(), chargeDays, preDiscountCharge, discountPercent, discountAmount, finalCharge
        );
    }

    private void validateInputs(int rentalDays, int discountPercent) {
        if (rentalDays < 1) {
            throw new InvalidRentalDaysException("Rental day count must be 1 or greater.");
        }
        if (discountPercent < 0 || discountPercent > 100) {
            throw new InvalidDiscountException("Discount percent must be between 0 and 100.");
        }
    }

    private int calculateBillableDays(Tool tool, LocalDate checkoutDate, LocalDate dueDate) {
        int chargeDays = 0;

        for (LocalDate date = checkoutDate.plusDays(1); !date.isAfter(dueDate); date = date.plusDays(1)) {
            if (DateUtil.isHoliday(date) && !tool.isHolidayCharge()) {
                continue;
            }
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            if (isWeekend(dayOfWeek) && tool.isWeekendCharge()) {
                chargeDays++;
            } else if (isWeekday(dayOfWeek) && tool.isWeekdayCharge()) {
                chargeDays++;
            }
        }
        return chargeDays;
    }

    private boolean isWeekend(DayOfWeek dayOfWeek) {
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private boolean isWeekday(DayOfWeek dayOfWeek) {
        return !isWeekend(dayOfWeek);
    }

    private BigDecimal calculatePreDiscountCharge(Tool tool, int chargeDays) {
        return tool.getDailyCharge().multiply(BigDecimal.valueOf(chargeDays)).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDiscountAmount(BigDecimal preDiscountCharge, int discountPercent) {
        return preDiscountCharge.multiply(BigDecimal.valueOf(discountPercent).divide(BigDecimal.valueOf(100))).setScale(2, RoundingMode.HALF_UP);
    }
}
