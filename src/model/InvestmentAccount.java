package model;

import java.math.BigDecimal;

    public class InvestmentAccount extends Account {
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

        private static final BigDecimal MINIMUM_DEPOSIT =
                new BigDecimal("500.00");

        private static final BigDecimal INTEREST_RATE =
                new BigDecimal("0.05");

        private final String riskLevel;

        public InvestmentAccount(String accountNumber, BigDecimal balance,
                                 String branch, Customer customer,
                                 String riskLevel) {
            super(
            );
            this.riskLevel = riskLevel;
        }

        private static BigDecimal validateInitialBalance(BigDecimal balance) {
            if (balance == null || balance.compareTo(MINIMUM_DEPOSIT) < 0) {
                throw new IllegalArgumentException(
                        "Minimum investment deposit is BWP 500.");
            }
            return balance;
        }

        public String getRiskLevel() {
            return riskLevel;
        }

        @Override
        public BigDecimal calculateInterest() {
            return getBalance().multiply(INTEREST_RATE);
        }

        public void addInvestment(BigDecimal amount) {
            deposit(amount);
        }
    }


