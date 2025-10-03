# Delivery App - ShortestPathFinder

![Java](https://img.shields.io/badge/Java-21-blue)
![JavaFX](https://img.shields.io/badge/JavaFX-21-lightgrey)
![MySQL](https://img.shields.io/badge/MySQL-8.0.33-blue)

This is a **JavaFX-based Delivery Application** that allows users to place orders and riders to accept and deliver them using the shortest path. The project uses **MySQL** for database management and includes features like password hashing, phone validation, and card validation for payments.

---

## Features

### User
- Registration and login
- Menu with selectable items
- Payment interface with card validation
- Billing information saved in the database

### Rider
- Registration and login
- Dashboard to view orders from users
- Accept orders and calculate shortest delivery route
- Integration with Shortest Path Finder (Dijkstra's algorithm)

### Utility / Common
- Database connection via `DBUtil`
- Password hashing using `PasswordUtil`
- Phone number validation using `ValidNumberChecker`
- Card validation using `CardValidator`

---

## Project Structure
ShortestPathFinder/
├── src/
│ ├── Main/
│ │ └── MainApp.java
│ ├── User/
│ │ ├── Gui/
│ │ │ ├── LoginGUI.java
│ │ │ └── RegisterGUI.java
│ │ ├── UserDB.java
│ │ └── Payment.java
│ ├── Rider/
│ │ ├── LoginGUI.java
│ │ ├── RegisterGUI.java
│ │ ├── RiderDB.java
│ │ └── DashboardGUI.java
│ ├── Utils/
│ │ ├── DBUtil.java
│ │ ├── PasswordUtil.java
│ │ ├── ValidNumberChecker.java
│ │ └── CardValidator.java
│ └── shortestpathfinder/
│ └── ShortestPathApp.java
├── pom.xml
└── README.md
