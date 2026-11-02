package com.pluralsight;

import java.util.ArrayList;

public class TransactionTest {
    public static void main(String[] args) {
        // Create the file manager
        TransactionFileManager fileManager = new TransactionFileManager();

        // Read transactions from the CSV file
        ArrayList<Transaction> transactions =
                fileManager.readTransactions("src/main/resources/transactions.csv");

        // Display all transactions
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }

        // Display the total number of transactions
        System.out.println("Total transactions: " + transactions.size());
    }
}