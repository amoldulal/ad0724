package com.company;

import com.company.service.RentalService;
import com.company.model.RentalAgreement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        RentalService rentalService = new RentalService();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter tool code: ");
            String toolCode = scanner.nextLine();

            System.out.print("Enter rental days: ");
            int rentalDays;
            try {
                rentalDays = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Rental days must be an integer.");
                return;
            }

            System.out.print("Enter discount percent: ");
            int discountPercent;
            try {
                discountPercent = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
            } catch (InputMismatchException e) {
                System.out.println("Discount percent must be an integer.");
                return;
            }

            System.out.print("Enter checkout date (YYYY-MM-DD): ");
            LocalDate checkoutDate;
            try {
                checkoutDate = LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Checkout date must be in the format YYYY-MM-DD.");
                return;
            }

            try {
                RentalAgreement agreement = rentalService.checkout(toolCode, rentalDays, discountPercent, checkoutDate);
                System.out.println(agreement.printAgreement());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (NumberFormatException | DateTimeParseException e) {
            System.out.println("Invalid input. Please enter valid values.");
        }
    }
}
