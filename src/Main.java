import DAO.AccountDAO;
import DAO.CustomerDAO;
import DAO.TransactionDAO;
import GUI.LoginFrame;
import model.Account;
import model.Customer;

import javax.swing.SwingUtilities;
import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    // true  = open the GUI login window
    // false = run the console menu
    static final boolean USE_GUI = true;

    static Scanner input = new Scanner(System.in);

    static CustomerDAO customerDAO = new CustomerDAO();
    static AccountDAO accountDAO = new AccountDAO();
    static TransactionDAO transactionDAO = new TransactionDAO();

    // Program entry point
    public static void main(String[] args) {
        if (USE_GUI) {
            SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
        } else {
            runConsole();
        }
    }

    // ---------- CONSOLE MENU ----------
    public static void runConsole() {

        int choice;

        do {
            System.out.println("\n===== BANKING SYSTEM =====");
            System.out.println("1. Insert Customer");
            System.out.println("2. Insert Account");
            System.out.println("3. Insert Transaction");
            System.out.println("0. Exit");

            System.out.print("Enter choice: ");
            try {
                choice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number from the menu.");
                choice = -1;   // not 0, so the loop continues
                continue;
            }

            switch (choice) {
                case 1:
                    insertCustomer();
                    break;
                case 2:
                    insertAccount();
                    break;
                case 3:
                    insertTransaction();
                    break;
                case 0:
                    System.out.println("Program closed.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 0);
    }

    // ---------- CUSTOMER ----------
    public static void insertCustomer() {

        Customer customer = new Customer();

        System.out.print("Enter first name: ");
        customer.setFirstName(input.nextLine());

        System.out.print("Enter surname: ");
        customer.setSurname(input.nextLine());

        System.out.print("Enter address: ");
        customer.setAddress(input.nextLine());

        System.out.print("Enter company: ");
        customer.setCompany(input.nextLine());

        System.out.println(customerDAO.create(customer)
                ? "Customer inserted successfully!"
                : "Customer insertion failed.");
    }

    // ---------- ACCOUNT ----------
    public static void insertAccount() {

        ConsoleAccount account = new ConsoleAccount();

        System.out.print("Enter account number: ");
        account.setAccountNumber(input.nextLine());

        System.out.print("Enter customer ID: ");
        account.setCustomerID(readInt());

        System.out.print("Enter account type (SAVINGS/INVESTMENT/CHEQUE): ");
        account.setAccountType(input.nextLine().toUpperCase());

        System.out.print("Enter opening balance: ");
        account.setBalance(readAmount());

        System.out.print("Enter employer name: ");
        account.setEmployerName(input.nextLine());

        System.out.print("Enter employer address: ");
        account.setEmployerAddress(input.nextLine());

        Account saved = accountDAO.create(account);

        System.out.println(saved != null
                ? "Account inserted successfully!"
                : "Account insertion failed.");
    }

    // ---------- TRANSACTION ----------
    public static void insertTransaction() {

        System.out.print("Enter account number: ");
        String accountNumber = input.nextLine();

        System.out.print("Enter transaction amount: ");
        BigDecimal amount = readAmount();

        System.out.print("Enter transaction type (Deposit/Withdrawal): ");
        String type = input.nextLine();

        System.out.print("Enter description: ");
        String description = input.nextLine();

        transactionDAO.create(accountNumber, amount, type, description);

        System.out.println("Transaction submitted.");
    }

    // ---------- INPUT HELPERS ----------
    static int readInt() {
        while (true) {
            try {
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a whole number: ");
            }
        }
    }

    static BigDecimal readAmount() {
        while (true) {
            try {
                return new BigDecimal(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid amount (e.g. 250.50): ");
            }
        }
    }

    // Concrete account used by the console menu: it stores the values,
    // so AccountDAO.create() receives real data.
    static class ConsoleAccount extends Account {
        private String accountNumber, accountType, employerName, employerAddress;
        private int customerID;
        private BigDecimal balance = BigDecimal.ZERO;

        @Override public BigDecimal calculateInterest() { return BigDecimal.ZERO; }

        @Override public void setAccountNumber(String s) { this.accountNumber = s; }
        @Override public void setBalance(BigDecimal b) { this.balance = b; }
        @Override public void setEmployerName(String s) { this.employerName = s; }
        @Override public void setEmployerAddress(String s) { this.employerAddress = s; }

        @Override public String getEmployerName() { return employerName; }
        @Override public String getEmployerAddress() { return employerAddress; }
        @Override public String getAccountType() { return accountType; }
        @Override public String getAccountID() { return accountNumber; }
        @Override public String getCustomerID() { return String.valueOf(customerID); }

        // Delete any of these that your Account class already defines
        public void setCustomerID(int id) { this.customerID = id; }
        public void setAccountType(String t) { this.accountType = t; }
        public String getAccountNumber() { return accountNumber; }
        public BigDecimal getBalance() { return balance; }
    }
}