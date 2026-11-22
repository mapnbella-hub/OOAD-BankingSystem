package DAO;

import model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {

    // Insert a customer without login details
    public boolean create(Customer customer) {
        String sql = "INSERT INTO customer (first_name, last_name, address, company) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getSurname());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getCompany());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error creating customer: " + e.getMessage());
            return false;
        }
    }

    // Insert a customer who can log in (sign up / add customer with login)
    public boolean createWithLogin(Customer customer, String username, String password) {
        String sql = "INSERT INTO customer (first_name, last_name, address, company, username, password) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getSurname());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getCompany());
            ps.setString(5, username);
            ps.setString(6, password);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error creating customer: " + e.getMessage());
            return false;
        }
    }

    // Retrieve customer using customer ID
    public Customer getCustomer(int customerID) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving customer: " + e.getMessage());
        }
        return null;
    }

    // Returns the customer if username and password match, otherwise null
    public Customer login(String username, String password) {
        String sql = "SELECT * FROM customer WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCustomer(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
        return null;
    }

    private Customer mapCustomer(ResultSet rs) throws SQLException {
        Customer c = new Customer();
        c.setCustomerID(rs.getInt("customer_id"));
        c.setFirstName(rs.getString("first_name"));
        c.setSurname(rs.getString("last_name"));
        c.setAddress(rs.getString("address"));
        c.setCompany(rs.getString("company"));
        return c;
    }
}