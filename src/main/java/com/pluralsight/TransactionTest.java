package com.pluralsight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionTest {
    public static void main(String[] args) {
        // Create a transaction
        Transaction transaction = new Transaction(
                LocalDate.of(2026, 11, 2),
                LocalTime.of(10, 30),
                "Lunch",
                "Walmart",
                new BigDecimal("-12.50")
        );

        // Display the transaction
        System.out.println(transaction);

        // Test getters
        System.out.println("Vendor: " + transaction.getVendor());
        System.out.println("Amount: " + transaction.getAmount());

        // Test setter
        transaction.setVendor("Target");

        System.out.println("Updated vendor: " + transaction.getVendor());
    }
}
