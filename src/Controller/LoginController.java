

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

    public Customer login(String username, String password) {

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return null;
        }

        return customerDAO.getClass(username, password);
    }
}


