package model;

import java.math.BigDecimal;

    public class ChequeAccount extends Account {

        private final BigDecimal overdraftLimit;

        public ChequeAccount(String accountNumber, BigDecimal balance,
                             String branch, Customer customer,
                             BigDecimal overdraftLimit) {
            super();

            if (overdraftLimit == null ||
                    overdraftLimit.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Invalid overdraft limit.");
            }

            this.overdraftLimit = overdraftLimit;
        }

        private static Customer validateCustomer(Customer customer) {
            if (customer == null || customer.getCompany() == null ||
                    customer.getCompany().isBlank()) {
                throw new IllegalArgumentException(
                        "Employment or company information is required.");
            }
            return customer;
        }

        public BigDecimal getOverdraftLimit() {
            return overdraftLimit;
        }

        @Override
        public BigDecimal calculateInterest() {
            return BigDecimal.ZERO;
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
            validateAmount(amount);

            BigDecimal available = getBalance().add(overdraftLimit);

            if (amount.compareTo(available) > 0) {
                throw new IllegalArgumentException(
                        "Amount exceeds the available balance and overdraft limit.");
            }

            updateBalance(getBalance().subtract(amount));
        }

        private void validateAmount(BigDecimal  amount) {
            
        }

        public void processCheque(BigDecimal amount) {
            withdraw(amount);
        }
    }


