package Controller;

import DAO.AccountDAO;
import DAO.TransactionDAO;
import model.Account;

import java.math.BigDecimal;

    public class AccountController {

        private final AccountDAO accountDAO;
        private final TransactionDAO transactionDAO;

        public AccountController() {
            this.accountDAO = new AccountDAO();
            this.transactionDAO = new TransactionDAO();
        }

        public AccountController(AccountDAO accountDAO,
                                 TransactionDAO transactionDAO) {
            this.accountDAO = accountDAO;
            this.transactionDAO = transactionDAO;
        }

        public Account openAccount(Account account) {

            if (account == null) {
                throw new IllegalArgumentException(
                        "Account information is required.");
            }

            return accountDAO.create(account);
        }

        public void deposit(String accountNumber, BigDecimal amount) {

            Account account = accountDAO.findByAccountNumber(accountNumber);

            if (account == null) {
                throw new IllegalArgumentException("Account not found.");
            }

            account.deposit(amount);
            accountDAO.update(account);

            transactionDAO.create(
                    accountNumber, amount, "DEPOSIT", "SUCCESS");
        }

        public void withdraw(String accountNumber, BigDecimal amount) {

            Account account = accountDAO.findByAccountNumber(accountNumber);

            if (account == null) {
                throw new IllegalArgumentException("Account not found.");
            }

            account.withdraw(amount);
            accountDAO.update(account);

            transactionDAO.create(
                    accountNumber, amount, "WITHDRAWAL", "SUCCESS");
        }

        public BigDecimal calculateInterest(String accountNumber) {

            Account account = accountDAO.findByAccountNumber(accountNumber);

            if (account == null) {
                throw new IllegalArgumentException("Account not found.");
            }

            return account.calculateInterest();
        }
    }


