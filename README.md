# Accounting Ledger

## About

This is my first Java Accounting Ledger project. I built this application to practice Java programming and create a simple way to manage financial transactions.

Users can record deposits and payments, view their transaction history, and search for specific transactions. The application saves transactions in a CSV file so the information is available when the program is reopened.

## Technologies Used

- Java 17
- Maven
- IntelliJ IDEA
- CSV files

## Features

- Add deposits and payments.
- View all transactions, deposits, and payments.
- View transactions from the current month, previous month, current year, and previous year.
- Search transactions by vendor.
- Use custom search filters for dates, description, vendor, and amount.
- Display the newest transactions first.
- Validate user input and handle errors.

## Project Structure

- `AccountingLedgerApp.java` – Runs the application and handles the menus and reports.
- `Transaction.java` – Represents each financial transaction.
- `TransactionFileManager.java` – Reads and saves transaction data.
- `TransactionTest.java` – Tests reading and displaying transactions.
- `transactions.csv` – Stores the transaction records.

## How to Run

**Requirements:** Java JDK 17 or later and IntelliJ IDEA.

1. Clone the repository:

   ```bash
   git clone https://github.com/enebabdellah75-commits/accounting-ledger.git
   ```

2. Open the project in IntelliJ IDEA.
3. Allow Maven to load the project.
4. Open `AccountingLedgerApp.java`.
5. Run the `main()` method.

## Interesting Part of the Project

One feature I enjoyed working on was the custom search. It allows users to search for transactions using different filters instead of having only one search option.

I used an enhanced for loop, if statements, and the `continue` statement to check each transaction and skip the ones that do not match the selected conditions.

## What I Learned

This project helped me practice classes and objects, constructors, getters and setters, ArrayLists, file handling, loops, conditionals, exception handling, and working with dates and money in Java.

I also learned how to organize a project into separate classes and use GitHub to manage my code.

## Future Improvements

In the future, I would like to add a database, a graphical user interface, and account balance summaries.
