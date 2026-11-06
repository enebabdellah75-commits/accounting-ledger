package com.pluralsight;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a single financial transaction with its date, time,
 * description, vendor, and amount.
 */

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private BigDecimal amount;

    /**
     * Creates a transaction using the provided details.
     */
    public Transaction(LocalDate date, LocalTime time, String description,
                       String vendor, BigDecimal amount) {

        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // Getter methods provide access to transaction fields.

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    // Setter methods allow transaction fields to be updated.

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Returns the transaction details as a readable string.
     */
    @Override
    public String toString() {
        return "----------------------------------------\n" +
                "Date:        " + date + "\n" +
                "Time:        " + time.format(
                java.time.format.DateTimeFormatter.ofPattern("hh:mm:ss a")) + "\n" +
                "Description: " + description + "\n" +
                "Vendor:      " + vendor + "\n" +
                "Amount:      " + (amount.signum() < 0 ? "-$" : "$")
                + amount.abs().setScale(2, java.math.RoundingMode.HALF_UP) + "\n" +
                "----------------------------------------";
    }
}



