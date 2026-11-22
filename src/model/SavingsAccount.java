package model;

import java.math.BigDecimal;

    public class SavingsAccount extends Account {

        private static final BigDecimal INTEREST_RATE =
                new BigDecimal("0.0005");

        public SavingsAccount(String accountNumber, BigDecimal balance,
                              String branch, Customer customer) {
            super();
        }

        @Override
        public BigDecimal calculateInterest() {
            return getBalance().multiply(INTEREST_RATE);
        }

        @Override
        public void setAccountNumber(String s) {

        }

        @Override
        public void setBalance(BigDecimal bigDecimal) {

        }

        @Override
        public String getEmployerAddress() {
            return "";
        }

        @Override
        public void setEmployerName(String s) {

        }

        @Override
        public String getAccountType() {
            return "";
        }

        @Override
        public void setEmployerAddress(String s) {

        }

        @Override
        public String getEmployerName() {
            return "";
        }

        @Override
        public String getAccountID() {
            return "";
        }

        @Override
        public String getCustomerID() {
            return "";
        }

        @Override
        public void withdraw(BigDecimal amount) {
            throw new UnsupportedOperationException(
                    "Withdrawals are not allowed on savings accounts.");
        }

        public BigDecimal getSavingsBalance() {
            return getBalance();
        }
    }




