

package com.pluralsight;

import java.util.Scanner;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class AccountingLedgerApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        TransactionFileManager fileManager = new TransactionFileManager();

        ArrayList<Transaction> transactions =
                fileManager.readTransactions("src/main/resources/transactions.csv");

        String choice = "";

        while (!choice.equalsIgnoreCase("X")) {

            System.out.println("\n===== ACCOUNTING LEDGER =====");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");

            choice = scanner.nextLine().trim();

            switch (choice.toUpperCase()) {


                case "D":

                    try {
                        System.out.print("Enter deposit amount: ");
                        BigDecimal amount = new BigDecimal(scanner.nextLine());

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

                        System.out.println("Deposit added successfully!");

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount. Please enter a valid number.");
                    }

                    break;



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

                        System.out.println("Payment added successfully!");

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount. Please enter a valid number.");
                    }

                    break;


                case "L":
                    System.out.println("Ledger selected.");
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
