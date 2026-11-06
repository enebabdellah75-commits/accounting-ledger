

package com.pluralsight;

import java.util.Scanner;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;

/**
 * Main class of the Accounting Ledger application.
 * Handles the menus and transaction operations.
 */

public class AccountingLedgerApp {

    // Console colors
    private static final String RESET = "\u001B[0m";
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RED = "\u001B[31m";

    /**
     * Starts the Accounting Ledger application.
     * Loads and sorts existing transactions, then displays the home menu
     * until the user chooses to exit.
     */
    public static void main(String[] args) {

        // Create the scanner and file manager.
        Scanner scanner = new Scanner(System.in);
        TransactionFileManager fileManager = new TransactionFileManager();

        System.out.println();
        System.out.println("+--------------------------------------+");
        System.out.println("|                                      |");
        System.out.println("|       " + BLUE + "WELCOME TO" + RESET + "                  |");
        System.out.println("|       " + BLUE + "ACCOUNTING LEDGER!" + RESET + "           |");
        System.out.println("|                                      |");
        System.out.println("|   Your Personal Financial System     |");
        System.out.println("|                                      |");
        System.out.println("+--------------------------------------+");
        System.out.println();

        // Load existing transactions from the CSV file.
        ArrayList<Transaction> transactions =
                fileManager.readTransactions("src/main/resources/transactions.csv");

        // Display transactions from newest to oldest.
        sortTransactions(transactions);

        String choice = "";

        // Keep the application running until the user exits.
        while (!choice.equalsIgnoreCase("X")) {

            showHomeMenu();

            choice = scanner.nextLine().trim();

            // Handle the user's menu selection.
            switch (choice.toUpperCase()) {

                case "D":
                    addDeposit(scanner, fileManager, transactions);
                    break;

                case "P":
                    makePayment(scanner, fileManager, transactions);
                    break;

                case "L":
                    showLedgerMenu(scanner, transactions);
                    break;

                case "X":
                    System.out.println();
                    System.out.println("+--------------------------------------+");
                    System.out.println("|                                      |");
                    System.out.println("|     " + GREEN + "THANK YOU FOR USING" + RESET + "             |");
                    System.out.println("|     " + GREEN + "ACCOUNTING LEDGER!" + RESET + "                |");
                    System.out.println("|                                      |");
                    System.out.println("|       Have a wonderful day!         |");
                    System.out.println("|                                      |");
                    System.out.println("+--------------------------------------+");
                    break;

                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }
        }

        // Close the scanner when the application ends.
        scanner.close();
    }

    // Sorts transactions by date and time, newest first.
    private static void sortTransactions(ArrayList<Transaction> transactions) {
        transactions.sort(
                Comparator.comparing(Transaction::getDate)
                        .thenComparing(Transaction::getTime)
                        .reversed()
        );
    }

    private static void showHomeMenu() {

        System.out.println();
        System.out.println("+--------------------------------------+");
        System.out.println(BLUE + "|           ACCOUNTING LEDGER          |" + RESET);
        System.out.println("|      PERSONAL FINANCIAL MANAGEMENT   |");
        System.out.println("+--------------------------------------+");
        System.out.println("|                                      |");
        System.out.println("|  MAIN MENU                           |");
        System.out.println("|                                      |");
        System.out.println("|  D) Add Deposit                      |");
        System.out.println("|  P) Make Payment                     |");
        System.out.println("|  L) Ledger                           |");
        System.out.println("|  X) Exit                             |");
        System.out.println("|                                      |");
        System.out.println("+--------------------------------------+");
        System.out.print("Choose an option: ");
    }

    private static void addDeposit(Scanner scanner,
                                   TransactionFileManager fileManager,
                                   ArrayList<Transaction> transactions) {

        try {
            System.out.print("Enter deposit amount: ");
            BigDecimal amount = new BigDecimal(scanner.nextLine());

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Deposit must be greater than zero.");
                return;
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

            if (fileManager.saveTransaction(
                    "src/main/resources/transactions.csv", deposit)) {

                transactions.add(deposit);
                sortTransactions(transactions);
                System.out.println("Deposit added successfully!");

            } else {
                System.out.println("Unable to save deposit. Please try again.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
        }
    }

    private static void makePayment(Scanner scanner,
                                    TransactionFileManager fileManager,
                                    ArrayList<Transaction> transactions) {

        try {
            System.out.print("Enter payment amount: ");
            BigDecimal amount = new BigDecimal(scanner.nextLine());

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Payment must be greater than zero.");
                return;
            }

            System.out.print("Enter description: ");
            String description = scanner.nextLine();

            System.out.print("Enter vendor: ");
            String vendor = scanner.nextLine();

            BigDecimal paymentAmount = amount.negate();

            Transaction payment = new Transaction(
                    LocalDate.now(),
                    LocalTime.now(),
                    description,
                    vendor,
                    paymentAmount
            );

            if (fileManager.saveTransaction(
                    "src/main/resources/transactions.csv", payment)) {

                transactions.add(payment);
                sortTransactions(transactions);
                System.out.println("Payment added successfully!");

            } else {
                System.out.println("Unable to save payment. Please try again.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid amount. Please enter a valid number.");
        }
    }

    private static void showLedgerMenu(Scanner scanner,
                                       ArrayList<Transaction> transactions) {

        String ledgerChoice = "";

        while (!ledgerChoice.equalsIgnoreCase("H")) {

            System.out.println();
            System.out.println("+--------------------------------------+");
            System.out.println("|              LEDGER MENU             |");
            System.out.println("+--------------------------------------+");
            System.out.println("|                                      |");
            System.out.println("|  A) All Transactions                 |");
            System.out.println("|  D) Deposits                         |");
            System.out.println("|  P) Payments                         |");
            System.out.println("|  R) Reports                          |");
            System.out.println("|  H) Return to Home                   |");
            System.out.println("|                                      |");
            System.out.println("+--------------------------------------+");

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
                    showReportsMenu(scanner, transactions);
                    break;

                case "H":
                    System.out.println("Returning to Home Menu...");
                    break;

                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }
        }
    }

    private static void showReportsMenu(Scanner scanner,
                                        ArrayList<Transaction> transactions) {

        String reportChoice = "";

        while (!reportChoice.equals("0")) {

            System.out.println();
            System.out.println("+--------------------------------------+");
            System.out.println("|             REPORTS MENU             |");
            System.out.println("+--------------------------------------+");
            System.out.println("|                                      |");
            System.out.println("|  1) Month To Date                    |");
            System.out.println("|  2) Previous Month                   |");
            System.out.println("|  3) Year To Date                     |");
            System.out.println("|  4) Previous Year                    |");
            System.out.println("|  5) Search by Vendor                 |");
            System.out.println("|  6) Custom Search                    |");
            System.out.println("|  0) Back to Ledger                   |");
            System.out.println("|                                      |");
            System.out.println("+--------------------------------------+");

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

                // Search transactions using optional filters.

                case "6": {
                    System.out.println("\n===== CUSTOM SEARCH =====");

                    System.out.print("Enter start date (yyyy-MM-dd) or press Enter to skip: ");
                    String startDateInput = scanner.nextLine().trim();

                    System.out.print("Enter end date (yyyy-MM-dd) or press Enter to skip: ");
                    String endDateInput = scanner.nextLine().trim();

                    System.out.print("Enter description or press Enter to skip: ");
                    String descriptionSearch = scanner.nextLine().trim();

                    System.out.print("Enter vendor or press Enter to skip: ");
                    String vendorSearchInput = scanner.nextLine().trim();

                    System.out.print("Enter amount or press Enter to skip: ");
                    String amountSearchInput = scanner.nextLine().trim();

                    LocalDate startDate = null;
                    LocalDate endDate = null;
                    BigDecimal amountSearch = null;

                    try {
                        if (!startDateInput.isEmpty()) {
                            startDate = LocalDate.parse(startDateInput);
                        }

                        if (!endDateInput.isEmpty()) {
                            endDate = LocalDate.parse(endDateInput);
                        }

                        if (!amountSearchInput.isEmpty()) {
                            amountSearch = new BigDecimal(amountSearchInput);
                        }

                    } catch (java.time.format.DateTimeParseException e) {
                        System.out.println("Invalid date. Please use yyyy-MM-dd.");
                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount. Please enter a valid number.");
                        break;
                    }

                    if (startDate != null && endDate != null
                            && startDate.isAfter(endDate)) {

                        System.out.println("Start date cannot be after end date.");
                        break;
                    }

                    System.out.println("\n===== CUSTOM SEARCH RESULTS =====");

                    boolean customSearchFound = false;

                    for (Transaction transaction : transactions) {

                        if (startDate != null
                                && transaction.getDate().isBefore(startDate)) {
                            continue;
                        }

                        if (endDate != null
                                && transaction.getDate().isAfter(endDate)) {
                            continue;
                        }

                        if (!descriptionSearch.isEmpty()
                                && !transaction.getDescription().toLowerCase()
                                .contains(descriptionSearch.toLowerCase())) {
                            continue;
                        }

                        if (!vendorSearchInput.isEmpty()
                                && !transaction.getVendor().toLowerCase()
                                .contains(vendorSearchInput.toLowerCase())) {
                            continue;
                        }

                        if (amountSearch != null
                                && transaction.getAmount().compareTo(amountSearch) != 0) {
                            continue;
                        }

                        System.out.println(transaction);
                        customSearchFound = true;
                    }

                    if (!customSearchFound) {
                        System.out.println("No transactions matched your search.");
                    }
                    break;
                }

                case "0":
                    System.out.println("Returning to Ledger Menu...");
                    break;

                default:
                    System.out.println("Invalid selection. Please try again.");
                    break;
            }
        }
    }
}



