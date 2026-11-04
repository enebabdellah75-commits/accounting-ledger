

package com.pluralsight;

import java.util.Scanner;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

    /**
     * Main class of the Accounting Ledger application.
     * Displays the home menu and handles user selections.
     */

public class AccountingLedgerApp {

    /**
     * Starts the application, loads existing transactions,
     * and displays the menu until the user chooses to exit.
     */

    public static void main(String[] args) {


        Scanner scanner = new Scanner(System.in);

        // Loads existing transactions from the CSV file.

        TransactionFileManager fileManager = new TransactionFileManager();

        ArrayList<Transaction> transactions =
                fileManager.readTransactions("src/main/resources/transactions.csv");


        // Sorts all transactions by date and time, newest first.
        transactions.sort(
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed()
        );

        String choice = "";

        // Keeps the application running until the user selects Exit.

        while (!choice.equalsIgnoreCase("X")) {

            System.out.println("\n===== ACCOUNTING LEDGER =====");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");

            choice = scanner.nextLine().trim();

            // Processes the user's menu selection.

            switch (choice.toUpperCase()) {

                // Handles deposits by collecting and validating user input.

                case "D":

                    try {
                        System.out.print("Enter deposit amount: ");
                        BigDecimal amount = new BigDecimal(scanner.nextLine());

                        // Validates that the entered amount is a positive number.

                        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                            System.out.println("Deposit must be greater than zero.");
                            break;
                        }

                        System.out.print("Enter description: ");
                        String description = scanner.nextLine();

                        System.out.print("Enter vendor: ");
                        String vendor = scanner.nextLine();


                        Transaction deposit = new Transaction(
                                LocalDate.now(),
                                LocalTime.now(),
                                description,
                                vendor,
                                amount
                        );

                        transactions.add(deposit);

                        fileManager.saveTransaction("src/main/resources/transactions.csv", deposit);

                        System.out.println("Deposit added successfully!");

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount. Please enter a valid number.");
                    }

                    break;


                // Handles payments and records them as negative transactions.

                case "P":

                    try {
                        System.out.print("Enter payment amount: ");
                        BigDecimal amount = new BigDecimal(scanner.nextLine());

                        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                            System.out.println("Payment must be greater than zero.");
                            break;
                        }

                        System.out.print("Enter description: ");
                        String description = scanner.nextLine();

                        System.out.print("Enter vendor: ");
                        String vendor = scanner.nextLine();

                        // Convert payment into a negative amount
                        BigDecimal paymentAmount = amount.negate();

                        Transaction payment = new Transaction(
                                LocalDate.now(),
                                LocalTime.now(),
                                description,
                                vendor,
                                paymentAmount
                        );

                        transactions.add(payment);

                        fileManager.saveTransaction("src/main/resources/transactions.csv", payment);

                        System.out.println("Payment added successfully!");

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount. Please enter a valid number.");
                    }

                    break;

                // Opens the ledger section.

                case "L":

                    String ledgerChoice = "";

                    while (!ledgerChoice.equalsIgnoreCase("H")) {

                        System.out.println("\n===== LEDGER MENU =====");
                        System.out.println("A) All Transactions");
                        System.out.println("D) Deposits");
                        System.out.println("P) Payments");
                        System.out.println("H) Return to Home");

                        System.out.print("Choose an option: ");
                        ledgerChoice = scanner.nextLine().trim();

                        switch (ledgerChoice.toUpperCase()) {

                            case "A":

                                System.out.println("\n===== ALL TRANSACTIONS =====");


                                if (transactions.isEmpty()) {
                                    System.out.println("No transactions found.");
                                } else {
                                    for (Transaction transaction : transactions) {
                                        System.out.println(transaction);
                                    }
                                }

                                break;

                            case "D":

                                System.out.println("\n===== DEPOSITS =====");

                                boolean depositFound = false;

                                for (Transaction transaction : transactions) {

                                    if (transaction.getAmount().compareTo(BigDecimal.ZERO) > 0) {

                                        System.out.println(transaction);
                                        depositFound = true;
                                    }
                                }

                                if (!depositFound) {
                                    System.out.println("No deposits found.");
                                }

                                break;

                            case "P":

                                System.out.println("\n===== PAYMENTS =====");

                                boolean paymentFound = false;

                                for (Transaction transaction : transactions) {

                                    if (transaction.getAmount().compareTo(BigDecimal.ZERO) < 0) {

                                        System.out.println(transaction);
                                        paymentFound = true;
                                    }
                                }

                                if (!paymentFound) {
                                    System.out.println("No payments found.");
                                }

                                break;

                            case "H":
                                System.out.println("Returning to Home Menu...");
                                break;

                            default:
                                System.out.println("Invalid selection. Please try again.");
                                break;
                        }
                    }

                    break;

                case "X":
                    System.out.println("Thank you for using Accounting Ledger!");
                    break;

                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}
