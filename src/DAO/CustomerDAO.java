
package DAO;

import model.Customer;
import java.sql.*;

public class CustomerDAO {

    private Connection connection;

    public CustomerDAO() {
    }

    // Retrieve customer using customer ID
    public Customer getCustomer(int customerID) {

        String sql = "SELECT * FROM customer WHERE customer_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, customerID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Customer customer = new Customer();

                customer.setCustomerID(rs.getInt("customer_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setSurname(rs.getString("last_name"));
                customer.setAddress(rs.getString("address"));
                customer.setCompany(rs.getString("company"));

                return customer;
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving customer: " + e.getMessage());
        }

        return null;
    }

    public Customer getClass(String username, String password) {
        return null;
    }
}
