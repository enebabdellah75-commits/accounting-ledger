
package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;


 // Handles reading transactions from and saving transactions to a CSV file.

public class TransactionFileManager {
    /**
     * Reads transaction records from a file, converts them into Transaction objects,
     * and returns them in an ArrayList.
     */
    public ArrayList<Transaction> readTransactions(String fileName) {

        ArrayList<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|", -1);

                if (parts.length != 5) {
                    System.out.println("Skipping invalid transaction: " + line);
                    continue;
                }

                try {
                    LocalDate date = LocalDate.parse(parts[0]);
                    LocalTime time = LocalTime.parse(parts[1]);
                    String description = parts[2];
                    String vendor = parts[3];
                    BigDecimal amount = new BigDecimal(parts[4]);

                    Transaction transaction = new Transaction(
                            date,
                            time,
                            description,
                            vendor,
                            amount
                    );

                    transactions.add(transaction);

                } catch (DateTimeParseException | NumberFormatException e) {
                    System.out.println("Skipping invalid transaction: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }

        return transactions;
    }

    /**
     * Saves a transaction to the CSV file.
     * Returns true if saving succeeds, otherwise false.
     */
    public boolean saveTransaction(String fileName, Transaction transaction) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(fileName, true))) {

            writer.write(
                    transaction.getDate() + "|" +
                            transaction.getTime() + "|" +
                            transaction.getDescription() + "|" +
                            transaction.getVendor() + "|" +
                            transaction.getAmount()
            );

            writer.newLine();

            return true;

        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
            return false;
        }
    }

}