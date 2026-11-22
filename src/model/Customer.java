package model;

public class Customer {

    private int customerID;
    private String firstName;
    private String surname;
    private String address;
    private String company;

    public Customer() {
    }

    public Customer(int customerID, String firstName,
                    String surname, String address, String company) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.surname = surname;
        this.address = address;
        this.company = company;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFullName() {
        return firstName + " " + surname;
    }

    public void updateAddress(String newAddress) {
        this.address = newAddress;
    }

    public void displayDetails() {
        System.out.println("Customer ID: " + customerID);
        System.out.println("Name: " + getFullName());
        System.out.println("Address: " + address);
        System.out.println("Company: " + company);
    }
}
