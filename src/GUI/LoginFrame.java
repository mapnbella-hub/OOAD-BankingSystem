package GUI;

import DAO.CustomerDAO;
import model.Customer;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JLabel message = new JLabel(" ");

    public LoginFrame() {
        setTitle("Banking System - Login");
        setSize(340, 240);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> openSignUp());

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(signUpButton);
        panel.add(loginButton);
        panel.add(message);

        add(panel);
        getRootPane().setDefaultButton(loginButton);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            message.setText("Enter your username and password.");
            return;
        }

        Customer user = new CustomerDAO().login(username, password);

        if (user == null) {
            message.setText("Invalid username or password.");
        } else {
            new DashboardFrame(user).setVisible(true);
            dispose();
        }
    }

    private void openSignUp() {
        JDialog dialog = new JDialog(this, "Sign Up", true);
        dialog.setSize(360, 340);
        dialog.setLocationRelativeTo(this);

        JTextField firstName = new JTextField(15);
        JTextField surname = new JTextField(15);
        JTextField address = new JTextField(15);
        JTextField company = new JTextField(15);
        JTextField username = new JTextField(15);
        JPasswordField password = new JPasswordField(15);

        JPanel form = new JPanel(new GridLayout(7, 2, 8, 8));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JButton createButton = new JButton("Create Account");
        createButton.addActionListener(e -> {
            String pass = new String(password.getPassword());

            if (firstName.getText().isBlank() || surname.getText().isBlank()
                    || username.getText().isBlank() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                        "First name, surname, username and password are required.",
                        "Missing details", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Customer c = new Customer();
            c.setFirstName(firstName.getText().trim());
            c.setSurname(surname.getText().trim());
            c.setAddress(address.getText().trim());
            c.setCompany(company.getText().trim());

            if (new CustomerDAO().createWithLogin(c, username.getText().trim(), pass)) {
                JOptionPane.showMessageDialog(dialog, "Sign up successful! You can now log in.");
                usernameField.setText(username.getText().trim());
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog,
                        "Sign up failed. That username may already exist.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        form.add(new JLabel("First name:"));
        form.add(firstName);
        form.add(new JLabel("Surname:"));
        form.add(surname);
        form.add(new JLabel("Address:"));
        form.add(address);
        form.add(new JLabel("Company:"));
        form.add(company);
        form.add(new JLabel("Username:"));
        form.add(username);
        form.add(new JLabel("Password:"));
        form.add(password);
        form.add(new JLabel());
        form.add(createButton);

        dialog.add(form);
        dialog.setVisible(true);
    }
}