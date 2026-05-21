# Java Programming Project

## Student Event Registration and Fee Management System

**Course:** BIT 33803 Java Programming  
**Semester:** Semester 2 2025/2026  
**Event:** FSKTM TechFest 2026  
**Venue:** Auditorium FSKTM  
**Organizer:** Faculty of Computer Science and Information Technology

## Project Overview

This project is a Java Swing GUI-based system developed for managing student registration and fee calculation for a faculty event. The system allows event staff to register participants, select event categories, choose optional add-ons, calculate fees, manage payment methods, update existing records, delete participants, search records, and view useful dashboard summaries.

The system is designed to support faculty event management by reducing manual calculation errors, improving registration organization, and providing a more professional event administration interface.

## Main Features

- Login page with username and password validation.
- Student registration form.
- Event category selection.
- Optional add-on selection.
- Payment method selection.
- Automatic fee calculation.
- Register new participant.
- Update selected participant.
- Delete selected participant.
- Search/filter participants by name or matric number.
- Duplicate matric number prevention.
- Auto-load selected table row into the form.
- Dashboard summary cards.
- Registration statistics by category.
- Payment summary by method.
- Recent activity log.
- CSV save/load for registration records.

## Default Login

```text
Username: admin
Password: 1234
```

## Event Categories

| Category | Fee |
|---|---:|
| Workshop | RM20 |
| Seminar | RM15 |
| Competition | RM30 |

## Optional Add-ons

| Add-on | Fee |
|---|---:|
| Certificate | RM5 |
| Food Package | RM10 |
| Event T-Shirt | RM25 |

## Payment Methods

- Cash
- Online Banking
- E-Wallet

## Object-Oriented Design

The project uses clear class separation and object-oriented programming concepts.

| Class | Purpose |
|---|---|
| `Main.java` | Starts the application and opens the login page. |
| `LoginFrame.java` | Handles login GUI and credential validation. |
| `RegistrationFrame.java` | Main GUI for registration, dashboard, table, search, update, delete, and file saving/loading. |
| `Student.java` | Stores student details using encapsulated private attributes. |
| `EventRegistration.java` | Stores registration details such as student, category, add-ons, payment method, and total fee. |
| `FeeCalculator.java` | Handles event fee and add-on fee calculation. |

## OOP Concepts Used

- Encapsulation with private attributes and public getters/setters.
- Constructors for object initialization.
- Separate classes for different responsibilities.
- ArrayList to store registration records.
- Methods for fee calculation, validation, searching, updating, deleting, and saving records.
- Clean separation between data objects and GUI logic.

## Java Swing Components Used

The system uses multiple Java Swing GUI components, including:

- `JFrame`
- `JPanel`
- `JLabel`
- `JTextField`
- `JPasswordField`
- `JButton`
- `JComboBox`
- `JCheckBox`
- `JRadioButton`
- `JTable`
- `JScrollPane`
- `JOptionPane`
- `JList`

## Validation Rules

Before registering or updating a participant, the system validates:

- Student name cannot be empty.
- Matric number cannot be empty.
- Phone number cannot be empty.
- Phone number must contain digits only.
- Payment method must be selected.
- Duplicate matric numbers are not allowed during new registration.

## Dashboard Features

The main registration screen includes:

- Total participants.
- Total collection.
- Most selected category, including tied categories.
- Registration statistics:
  - Workshop count.
  - Seminar count.
  - Competition count.
- Payment summary:
  - Cash count.
  - Online Banking count.
  - E-Wallet count.
- Recent activities log.

## Business Perspective

This system is suitable for university or faculty event management because it:

- Helps staff manage participant registration faster.
- Reduces manual fee calculation errors.
- Prevents duplicate matric number registration.
- Keeps registration records organized.
- Provides dashboard summaries for event monitoring.
- Improves professionalism during event administration.

## How to Compile and Run

Open a terminal in the project folder:

```bash
javac *.java
java Main
```

## Project Files

```text
Main.java
LoginFrame.java
RegistrationFrame.java
Student.java
EventRegistration.java
FeeCalculator.java
registrations.csv
PROJECT_SUMMARY.md
README.txt
```

## Conclusion

The Student Event Registration and Fee Management System is a complete Java Swing object-oriented application that supports real faculty event registration operations. It includes login security, registration management, fee calculation, update/delete functions, search/filter features, dashboard summaries, activity tracking, validation, and simple CSV record storage.
