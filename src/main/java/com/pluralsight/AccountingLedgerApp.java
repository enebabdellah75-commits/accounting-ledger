

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
                        System.out.println("R) Reports");
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

                            case "R":

                                String reportChoice = "";

                                while (!reportChoice.equals("0")) {

                                    System.out.println("\n===== REPORTS MENU =====");
                                    System.out.println("1) Month To Date");
                                    System.out.println("2) Previous Month");
                                    System.out.println("3) Year To Date");
                                    System.out.println("4) Previous Year");
                                    System.out.println("5) Search by Vendor");
                                    System.out.println("0) Back to Ledger");

                                    System.out.print("Choose an option: ");
                                    reportChoice = scanner.nextLine().trim();

                                    switch (reportChoice) {

                                        case "1":

                                            LocalDate today = LocalDate.now();

                                            LocalDate startOfMonth = today.withDayOfMonth(1);

                                            System.out.println("\n===== MONTH TO DATE REPORT =====");

                                            boolean transactionFound = false;

                                            for (Transaction transaction : transactions) {

                                                if (!transaction.getDate().isBefore(startOfMonth)
                                                        && !transaction.getDate().isAfter(today)) {

                                                    System.out.println(transaction);
                                                    transactionFound = true;
                                                }
                                            }

                                            if (!transactionFound) {
                                                System.out.println("No transactions found for this month.");
                                            }

                                            break;

                                        case "2":

                                            LocalDate previousMonthToday = LocalDate.now();

                                            // Gets the first and last day of the previous month.
                                            LocalDate firstDayOfPreviousMonth =
                                                    previousMonthToday.minusMonths(1).withDayOfMonth(1);

                                            LocalDate lastDayOfPreviousMonth =
                                                    previousMonthToday.withDayOfMonth(1).minusDays(1);

                                            System.out.println("\n===== PREVIOUS MONTH REPORT =====");

                                            boolean previousMonthTransactionFound = false;

                                            for (Transaction transaction : transactions) {

                                                if (!transaction.getDate().isBefore(firstDayOfPreviousMonth)
                                                        && !transaction.getDate().isAfter(lastDayOfPreviousMonth)) {

                                                    System.out.println(transaction);
                                                    previousMonthTransactionFound = true;
                                                }
                                            }

                                            if (!previousMonthTransactionFound) {
                                                System.out.println("No transactions found for the previous month.");
                                            }

                                            break;

                                        case "3":

                                            LocalDate currentDate = LocalDate.now();
                                            LocalDate startOfYear = currentDate.withDayOfYear(1);

                                            System.out.println("\n===== YEAR TO DATE REPORT =====");

                                            boolean yearTransactionFound = false;

                                            for (Transaction transaction : transactions) {

                                                if (!transaction.getDate().isBefore(startOfYear)
                                                        && !transaction.getDate().isAfter(currentDate)) {

                                                    System.out.println(transaction);
                                                    yearTransactionFound = true;
                                                }
                                            }

                                            if (!yearTransactionFound) {
                                                System.out.println("No transactions found for this year.");
                                            }

                                            break;

                                        case "4":

                                            LocalDate currentYearDate = LocalDate.now();

                                            LocalDate firstDayOfPreviousYear =
                                                    currentYearDate.minusYears(1).withDayOfYear(1);

                                            LocalDate lastDayOfPreviousYear =
                                                    currentYearDate.withDayOfYear(1).minusDays(1);

                                            System.out.println("\n===== PREVIOUS YEAR REPORT =====");

                                            boolean previousYearTransactionFound = false;

                                            for (Transaction transaction : transactions) {

                                                if (!transaction.getDate().isBefore(firstDayOfPreviousYear)
                                                        && !transaction.getDate().isAfter(lastDayOfPreviousYear)) {

                                                    System.out.println(transaction);
                                                    previousYearTransactionFound = true;
                                                }
                                            }

                                            if (!previousYearTransactionFound) {
                                                System.out.println("No transactions found for the previous year.");
                                            }

                                            break;

                                        case "5":

                                            System.out.print("Enter vendor name: ");
                                            String vendorSearch = scanner.nextLine().trim();

                                            boolean vendorFound = false;

                                            System.out.println("\n===== VENDOR SEARCH RESULTS =====");

                                            for (Transaction transaction : transactions) {

                                                if (transaction.getVendor().equalsIgnoreCase(vendorSearch)) {

                                                    System.out.println(transaction);
                                                    vendorFound = true;
                                                }
                                            }

                                            if (!vendorFound) {
                                                System.out.println("No transactions found for this vendor.");
                                            }

                                            break;

                                        case "0":
                                            System.out.println("Returning to Ledger Menu...");
                                            break;

                                        default:
                                            System.out.println("Invalid selection. Please try again.");
                                            break;
                                    }
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
