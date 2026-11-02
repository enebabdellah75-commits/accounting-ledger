package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class TransactionFileManager {
    // Read transactions from the CSV file
    public ArrayList<Transaction> readTransactions(String fileName) {

        ArrayList<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|");

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
            }

        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }

        return transactions;
    }
}
