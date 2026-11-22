
import DAO.CustomerDAO;
import DAO.AccountDAO;
import DAO.TransactionDAO;
import model.Customer;
import model.Account;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);

    static CustomerDAO customerDAO = new CustomerDAO();
    static AccountDAO accountDAO = new AccountDAO();
    static TransactionDAO transactionDAO = new TransactionDAO();

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== BANKING SYSTEM =====");
            System.out.println("1. Insert Customer");
            System.out.println("2. Insert Account");
            System.out.println("3. Insert Transaction");
            System.out.println("0. Exit");

            System.out.print("Enter choice: ");
            choice = Integer.parseInt(input.nextLine());

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

    public static void insertCustomer() {

        Customer customer= new Customer();

        System.out.print("Enter first name: ");
        customer.setFirstName(input.nextLine());

        System.out.print("Enter surname: ");
        customer.setSurname(input.nextLine());

        System.out.print("Enter address: ");
        customer.setAddress(input.nextLine());

        System.out.print("Enter company: ");
        customer.setCompany(input.nextLine());

        // Save customer using CustomerDAO
        System.out.println("Customer information collected.");
        System.out.println("Connect this method to your CustomerDAO create method.");
    }

    public static void insertAccount() {

        Account account = new Account() {
            @Override
            public BigDecimal calculateInterest() {
                return null;
            }

            @Override
            public void setAccountNumber(String s) {

            }

            @Override
            public void setBalance(BigDecimal bigDecimal) {

            }

            @Override
            public String getEmployerAddress() {
                return "";
            }

            @Override
            public void setEmployerName(String s) {

            }

            @Override
            public String getAccountType() {
                return "";
            }

            @Override
            public void setEmployerAddress(String s) {

            }

            @Override
            public String getEmployerName() {
                return "";
            }

            @Override
            public String getAccountID() {
                return "";
            }

            @Override
            public String getCustomerID() {
                return "";
            }
        };

        System.out.print("Enter account number: ");
        account.setAccountNumber(input.nextLine());

        System.out.print("Enter customer ID: ");
        account.setCustomerID(Integer.parseInt(input.nextLine()));

        System.out.print("Enter account type: ");
        account.setAccountType(input.nextLine());

        System.out.print("Enter opening balance: ");
        account.setBalance(new BigDecimal(input.nextLine()));

        System.out.print("Enter employer name: ");
        account.setEmployerName(input.nextLine());

        System.out.print("Enter employer address: ");
        account.setEmployerAddress(input.nextLine());

        Account saved = accountDAO.create(account);

        if (saved != null) {
            System.out.println("Account inserted successfully!");
        } else {
            System.out.println("Account insertion failed.");
        }
    }


    public static void insertTransaction() {

        System.out.print("Enter account number: ");
        String accountNumber = input.nextLine();

        System.out.print("Enter transaction amount: ");
        BigDecimal amount = new BigDecimal(input.nextLine());

        System.out.print("Enter transaction type (Deposit/Withdrawal): ");
        String type = input.nextLine();

        System.out.print("Enter description: ");
        String description = input.nextLine();

        transactionDAO.create(
                accountNumber,
                amount,
                type,
                description
        );

        System.out.println("Transaction submitted.");
    }
}


