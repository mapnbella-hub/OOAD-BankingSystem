package model;


import java.math.BigDecimal;

    public class InterestCalculator {

        public BigDecimal calculateInterest(Account account) {

            if (account == null) {
                throw new IllegalArgumentException("Account cannot be null.");
            }

            return account.calculateInterest();
        }
    }


