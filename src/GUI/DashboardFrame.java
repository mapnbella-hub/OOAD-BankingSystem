package GUI;

import DAO.AccountDAO;
import DAO.CustomerDAO;
import DAO.TransactionDAO;
import model.Account;
import model.Customer;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class DashboardFrame extends JFrame {

    private final Customer user;
    private final CustomerDAO customerDAO = new CustomerDAO();
    private final AccountDAO accountDAO = new AccountDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();

    public DashboardFrame(Customer user) {
        this.user = user;

        setTitle("Banking System - Dashboard");
        setSize(520, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel welcome = new JLabel("Welcome, " + user.getFirstName() + " " + user.getSurname());
        welcome.setFont(welcome.getFont().deriveFont(Font.BOLD, 15f));

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        top.add(welcome, BorderLayout.WEST);
        top.add(logout, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Customer", customerTab());
        tabs.addTab("Open Account", openAccountTab());
        tabs.addTab("Deposit / Withdraw", transactionTab());
        tabs.addTab("Check Balance", balanceTab());

        add(top, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }

    // ---------- CUSTOMER TAB (add customer / sign up) ----------
    private JPanel customerTab() {
        JTextField firstName = new JTextField(15);
        JTextField surname = new JTextField(15);
        JTextField address = new JTextField(15);
        JTextField company = new JTextField(15);
        JTextField username = new JTextField(15);
        JPasswordField password = new JPasswordField(15);

        JPanel form = form(7);
        addRow(form, "First name:", firstName);
        addRow(form, "Surname:", surname);
        addRow(form, "Address:", address);
        addRow(form, "Company:", company);
        addRow(form, "Username (optional):", username);
        addRow(form, "Password (optional):", password);

        JButton save = new JButton("Add Customer");
        save.addActionListener(e -> {
            if (firstName.getText().isBlank() || surname.getText().isBlank()) {
                warn("First name and surname are required.");
                return;
            }

            String loginName = username.getText().trim();
            String loginPass = new String(password.getPassword());

            if (loginName.isEmpty() != loginPass.isEmpty()) {
                warn("Enter both a username and a password, or leave both empty.");
                return;
            }

            Customer c = new Customer();
            c.setFirstName(firstName.getText().trim());
            c.setSurname(surname.getText().trim());
            c.setAddress(address.getText().trim());
            c.setCompany(company.getText().trim());

            boolean saved = loginName.isEmpty()
                    ? customerDAO.create(c)
                    : customerDAO.createWithLogin(c, loginName, loginPass);

            if (saved) {
                info("Customer inserted successfully!");
                firstName.setText(""); surname.setText(""); address.setText("");
                company.setText(""); username.setText(""); password.setText("");
            } else {
                error("Customer insertion failed. If you entered a username, it may already exist.");
            }
        });

        form.add(new JLabel());
        form.add(save);
        return wrap(form);
    }

    // ---------- OPEN ACCOUNT TAB ----------
    private JPanel openAccountTab() {
        JTextField customerId = new JTextField(String.valueOf(user.getCustomerID()), 15);
        JTextField number = new JTextField(15);
        JComboBox<String> type = new JComboBox<>(new String[]{"SAVINGS", "INVESTMENT", "CHEQUE"});
        JTextField balance = new JTextField(15);
        JTextField empName = new JTextField(15);
        JTextField empAddress = new JTextField(15);

        JPanel form = form(7);
        addRow(form, "Customer ID:", customerId);
        addRow(form, "Account number:", number);
        addRow(form, "Account type:", type);
        addRow(form, "Opening balance:", balance);
        addRow(form, "Employer name:", empName);
        addRow(form, "Employer address:", empAddress);

        JButton open = new JButton("Open Account");
        open.addActionListener(e -> {
            int custId;
            BigDecimal opening;

            try {
                custId = Integer.parseInt(customerId.getText().trim());
            } catch (NumberFormatException ex) {
                warn("Customer ID must be a whole number.");
                return;
            }
            try {
                opening = new BigDecimal(balance.getText().trim()).setScale(2, RoundingMode.HALF_UP);
            } catch (NumberFormatException ex) {
                warn("Enter a valid opening balance (e.g. 500.00).");
                return;
            }

            String accNo = number.getText().trim();
            String accType = (String) type.getSelectedItem();

            if (accNo.isEmpty()) {
                warn("Account number is required.");
                return;
            }
            if (opening.signum() < 0) {
                warn("Opening balance cannot be negative.");
                return;
            }
            if (accType.equals("CHEQUE")
                    && (empName.getText().isBlank() || empAddress.getText().isBlank())) {
                warn("A cheque account needs the employer name and address.");
                return;
            }
            if (customerDAO.getCustomer(custId) == null) {
                error("No customer with ID " + custId + " exists. Add the customer first.");
                return;
            }
            if (accountDAO.findByAccountNumber(accNo) != null) {
                error("Account number " + accNo + " is already in use.");
                return;
            }

            BankAccount account = new BankAccount(accNo, String.valueOf(custId), accType, opening);
            account.setEmployerName(empName.getText().trim());
            account.setEmployerAddress(empAddress.getText().trim());

            if (accountDAO.create(account) != null) {
                info(accType + " account " + accNo + " opened with balance " + opening);
                number.setText(""); balance.setText("");
                empName.setText(""); empAddress.setText("");
            } else {
                error("Account could not be opened. Check the console for details.");
            }
        });

        form.add(new JLabel());
        form.add(open);
        return wrap(form);
    }

    // ---------- DEPOSIT / WITHDRAW TAB ----------
    private JPanel transactionTab() {
        JTextField number = new JTextField(15);
        JComboBox<String> type = new JComboBox<>(new String[]{"DEPOSIT", "WITHDRAWAL"});
        JTextField amount = new JTextField(15);
        JTextField description = new JTextField(15);
        JLabel result = new JLabel(" ");

        JPanel form = form(5);
        addRow(form, "Account number:", number);
        addRow(form, "Transaction type:", type);
        addRow(form, "Amount:", amount);
        addRow(form, "Description:", description);

        JButton submit = new JButton("Submit Transaction");
        submit.addActionListener(e -> {
            BigDecimal amt;
            try {
                amt = new BigDecimal(amount.getText().trim()).setScale(2, RoundingMode.HALF_UP);
            } catch (NumberFormatException ex) {
                warn("Enter a valid amount (e.g. 250.50).");
                return;
            }
            if (amt.signum() <= 0) {
                warn("Amount must be greater than zero.");
                return;
            }

            Account acc = accountDAO.findByAccountNumber(number.getText().trim());
            if (acc == null) {
                error("Account not found.");
                return;
            }

            String txType = (String) type.getSelectedItem();
            BigDecimal newBalance = txType.equals("DEPOSIT")
                    ? acc.getBalance().add(amt)
                    : acc.getBalance().subtract(amt);

            if (newBalance.signum() < 0) {
                error("Insufficient funds. Current balance: " + acc.getBalance());
                return;
            }

            acc.setBalance(newBalance);
            accountDAO.update(acc);
            transactionDAO.create(acc.getAccountID(), amt, txType, description.getText().trim());

            result.setText(txType + " of " + amt + " done. New balance: " + newBalance);
            amount.setText("");
            description.setText("");
        });

        form.add(new JLabel());
        form.add(submit);

        JPanel panel = wrap(form);
        result.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        panel.add(result, BorderLayout.CENTER);
        return panel;
    }

    // ---------- CHECK BALANCE TAB ----------
    private JPanel balanceTab() {
        JTextField number = new JTextField(15);
        JLabel result = new JLabel(" ");
        JTextArea myAccounts = new JTextArea(8, 30);
        myAccounts.setEditable(false);

        JPanel form = form(3);
        addRow(form, "Account number:", number);

        JButton check = new JButton("Check Balance");
        check.addActionListener(e -> {
            Account acc = accountDAO.findByAccountNumber(number.getText().trim());
            result.setText(acc == null
                    ? "No account found."
                    : acc.getAccountType() + " account " + acc.getAccountID()
                    + " - Balance: " + acc.getBalance());
        });

        JButton listMine = new JButton("Show My Accounts");
        listMine.addActionListener(e -> {
            List<Account> accounts = accountDAO.findByCustomerID(
                    Integer.parseInt(String.valueOf(user.getCustomerID())));
            if (accounts.isEmpty()) {
                myAccounts.setText("You have no accounts yet.");
                return;
            }
            StringBuilder sb = new StringBuilder();
            for (Account a : accounts) {
                sb.append(a.getAccountID()).append("  |  ")
                        .append(a.getAccountType()).append("  |  Balance: ")
                        .append(a.getBalance()).append("\n");
            }
            myAccounts.setText(sb.toString());
        });

        form.add(listMine);
        form.add(check);

        JPanel panel = wrap(form);
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(5, 15, 10, 15));
        result.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));
        bottom.add(result, BorderLayout.NORTH);
        bottom.add(new JScrollPane(myAccounts), BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.CENTER);
        return panel;
    }

    // ---------- HELPERS ----------
    private JPanel form(int rows) {
        JPanel p = new JPanel(new GridLayout(rows, 2, 8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        return p;
    }

    private void addRow(JPanel p, String label, JComponent field) {
        p.add(new JLabel(label));
        p.add(field);
    }

    private JPanel wrap(JPanel form) {
        JPanel p = new JPanel(new BorderLayout());
        p.add(form, BorderLayout.NORTH);
        return p;
    }

    private void info(String msg)  { JOptionPane.showMessageDialog(this, msg); }
    private void warn(String msg)  { JOptionPane.showMessageDialog(this, msg, "Check your input", JOptionPane.WARNING_MESSAGE); }
    private void error(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }

    // Concrete account used by the dashboard (the abstract Account needs a subclass)
    private static class BankAccount extends Account {
        private final String number, customerId, type;
        private BigDecimal bal;
        private String empName, empAddress;

        BankAccount(String number, String customerId, String type, BigDecimal bal) {
            this.number = number;
            this.customerId = customerId;
            this.type = type;
            this.bal = bal;
        }

        @Override public BigDecimal calculateInterest() { return BigDecimal.ZERO; }
        @Override public void setAccountNumber(String s) { }
        @Override public void setBalance(BigDecimal b) { bal = b; }
        @Override public void setEmployerName(String s) { empName = s; }
        @Override public void setEmployerAddress(String s) { empAddress = s; }
        @Override public String getEmployerName() { return empName; }
        @Override public String getEmployerAddress() { return empAddress; }
        @Override public String getAccountType() { return type; }
        @Override public String getAccountID() { return number; }
        @Override public String getCustomerID() { return customerId; }

        public BigDecimal getBalance() { return bal; }
    }
}