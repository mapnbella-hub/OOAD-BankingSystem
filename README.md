# OOAD Banking System

## Project Overview

The **OOAD Banking System** is a Java-based banking application developed using Object-Oriented Analysis and Design (OOAD) principles.

The system provides a graphical user interface for banking operations and uses a MySQL database for data storage and management.

## Technologies Used

- **Programming Language:** Java
- **GUI Framework:** Java Swing
- **Database:** MySQL
- **Build Management:** Apache Maven
- **Development Environment:** IntelliJ IDEA
- **Version Control:** Git and GitHub

## Project Structure

```text
OOAD-BankingSystem/
│
├── src/
│   └── banking/
│       ├── Controller/
│       ├── DAO/
│       ├── GUI/
│       ├── model/
│       └── Main.java
│
├── pom.xml
├── .gitignore
└── README.md
```

## Database Structure

The application uses a MySQL database named `banking`.

The database contains the following tables:

1. `customer` – Stores customer information.
2. `account` – Stores banking account information.
3. `transaction` – Stores transaction records.

## Main Components

- **GUI:** Provides the graphical interface.
- **Controller:** Handles application operations and communication.
- **DAO:** Handles database access and operations.
- **Model:** Represents application data and objects.
- **Main:** Application entry point.

## Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/mapnbella-hub/OOAD-BankingSystem.git
```

### 2. Open the Project

Open the cloned project using IntelliJ IDEA.

### 3. Configure the Database

- Install and start MySQL.
- Create the `banking` database.
- Configure the database connection in the application.
- Ensure the required database tables exist.

### 4. Build the Project

Run:

```bash
mvn clean package
```

### 5. Run the Application

Run the `Main.java` class from IntelliJ IDEA.

## Version Control

This project is managed using Git and hosted on GitHub.

**Repository:** https://github.com/mapnbella-hub/OOAD-BankingSystem

**Default Branch:** master

---

*OOAD Banking System – Java Banking Application Project.*
