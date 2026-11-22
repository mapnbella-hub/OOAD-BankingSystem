package model;

import java.math.BigDecimal;

    public interface Transactionable {

        boolean executeTransaction(
                Account account,
                BigDecimal amount,
                String transactionType
        );
    }

