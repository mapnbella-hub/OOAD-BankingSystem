package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

    public class Transaction implements Transactionable {

        private int transactionID;
        private String accountNumber;
        private LocalDateTime date;
        private BigDecimal amount;
        private String type;
        private String status;

        public Transaction() {
            this.date = LocalDateTime.now();
        }

        public Transaction(int transactionID, String accountNumber,
                           BigDecimal amount, String type, String status) {
            this.transactionID = transactionID;
            this.accountNumber = accountNumber;
            this.date = LocalDateTime.now();
            this.amount = amount;
            this.type = type;
            this.status = status;
        }

        public int getTransactionID() {
            return transactionID;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public String getType() {
            return type;
        }

        public String getStatus() {
            return status;
        }

        @Override
        public boolean executeTransaction(Account account,
                                          BigDecimal amount,
                                          String transactionType) {

            if (account == null || amount == null ||
                    transactionType == null) {
                throw new IllegalArgumentException(
                        "Transaction details are required.");
            }

            try {
                if (transactionType.equalsIgnoreCase("DEPOSIT")) {
                    account.deposit(amount);

                } else if (transactionType.equalsIgnoreCase("WITHDRAWAL")) {
                    account.withdraw(amount);

                } else {
                    throw new IllegalArgumentException(
                            "Invalid transaction type.");
                }

                this.accountNumber = account.getAccountNumber();
                this.amount = amount;
                this.type = transactionType.toUpperCase();
                this.date = LocalDateTime.now();
                this.status = "SUCCESS";

                return true;

            } catch (RuntimeException exception) {
                this.status = "FAILED";
                throw exception;
            }
        }

        public void getTransactionDetails() {
            System.out.println("Transaction ID: " + transactionID);
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Date: " + date);
            System.out.println("Amount: " + amount);
            System.out.println("Type: " + type);
            System.out.println("Status: " + status);
        }
    }


