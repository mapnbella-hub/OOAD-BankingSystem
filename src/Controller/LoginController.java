package Controller;

import DAO.CustomerDAO;
import model.Customer;

public class LoginController {

    private final CustomerDAO customerDAO;

    public LoginController() {
        this.customerDAO = new CustomerDAO();
    }

    public LoginController(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    // Returns true if the username and password match a customer
    public Boolean login(String username, String password) {

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return false;
        }

        Customer customer = customerDAO.login(username.trim(), password);
        return customer != null;
    }
}