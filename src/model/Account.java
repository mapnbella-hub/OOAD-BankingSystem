package model;

import java.math.BigDecimal;

    public abstract class Account {

        private String accountNumber;
        private BigDecimal balance;
        private final String branch = null;
        private final Customer customer = new Customer();

        public Account() {

        }

        // Getters
        public String getAccountNumber() {
            return accountNumber;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public String getBranch() {
            return branch;
        }

        public Customer getCustomer() {
            return customer;
        }

        // Deposit money
        public void deposit(BigDecimal amount) {

            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                        "Deposit amount must be greater than zero.");
            }

            balance = balance.add(amount);
        }

        // Withdraw money
        public void withdraw(BigDecimal amount) {

            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException(
                        "Withdrawal amount must be greater than zero.");
            }

            if (amount.compareTo(balance) > 0) {
                throw new IllegalArgumentException(
                        "Insufficient funds.");
            }

            balance = balance.subtract(amount);
        }

        // Update account balance
        protected void updateBalance(BigDecimal newBalance) {
            this.balance = newBalance;
        }

        // Each account calculates interest differently
        public abstract BigDecimal calculateInterest();

        public abstract void setAccountNumber(String s);

        public void setCustomerID(int i) {
            
        }

        public void setAccountType(String s) {
            
        }

        public abstract void setBalance(BigDecimal bigDecimal);

        public abstract String getEmployerAddress();

        public abstract void setEmployerName(String s);

        public abstract String getAccountType();

        public abstract void setEmployerAddress(String s);

        public abstract String getEmployerName();

        public abstract String getAccountID();

        public abstract String getCustomerID();
    }

