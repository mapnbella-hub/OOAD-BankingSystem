package DAO;

import model.Account;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    // Insert a new account; returns the account if saved, null if it failed
    public Account create(Account account) {
        String sql = "INSERT INTO account " +
                "(account_number, customer_id, account_type, balance, employer_name, employer_address) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, account.getAccountID());
            ps.setInt(2, Integer.parseInt(account.getCustomerID()));
            ps.setString(3, account.getAccountType());
            ps.setBigDecimal(4, account.getBalance());
            ps.setString(5, account.getEmployerName());
            ps.setString(6, account.getEmployerAddress());

            if (ps.executeUpdate() > 0) {
                return account;
            }

        } catch (SQLException e) {
            System.out.println("Error creating account: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error creating account: customer ID must be a number.");
        }
        return null;
    }

    // Look up an account by its account number; null if not found
    public Account findByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM account WHERE account_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, accountNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapAccount(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error finding account: " + e.getMessage());
        }
        return null;
    }

    // All accounts that belong to one customer
    public List<Account> findByCustomerID(int customerID) {
        String sql = "SELECT * FROM account WHERE customer_id = ?";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    accounts.add(mapAccount(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error listing accounts: " + e.getMessage());
        }
        return accounts;
    }

    // Save the account's current balance (after a deposit or withdrawal)
    public void update(Account account) {
        String sql = "UPDATE account SET balance = ? WHERE account_number = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBigDecimal(1, account.getBalance());
            ps.setString(2, account.getAccountID());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating account: " + e.getMessage());
        }
    }

    // Builds an Account from the current row
    private Account mapAccount(ResultSet rs) throws SQLException {
        final String number = rs.getString("account_number");
        final String custId = String.valueOf(rs.getInt("customer_id"));
        final String type   = rs.getString("account_type");

        Account account = new Account() {
            private BigDecimal bal = BigDecimal.ZERO;
            private String empName, empAddress;

            @Override public BigDecimal calculateInterest() { return BigDecimal.ZERO; }
            @Override public void setAccountNumber(String s) { }
            @Override public void setBalance(BigDecimal b) { bal = b; }
            @Override public void setEmployerName(String s) { empName = s; }
            @Override public void setEmployerAddress(String s) { empAddress = s; }
            @Override public String getEmployerName() { return empName; }
            @Override public String getEmployerAddress() { return empAddress; }
            @Override public String getAccountType() { return type; }
            @Override public String getAccountID() { return number; }
            @Override public String getCustomerID() { return custId; }

            public BigDecimal getBalance() { return bal; }
        };

        account.setBalance(rs.getBigDecimal("balance"));
        account.setEmployerName(rs.getString("employer_name"));
        account.setEmployerAddress(rs.getString("employer_address"));
        return account;
    }
}