## 📚 Table of Contents

- [Technologies](#technologies)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Pages](#pages)

---

## 🛠️ Technologies

- Java 17+
- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL
- Maven
- HTML/CSS
- Bootstrap v.4.3.1

---

## 🚀 Installation

1. **Clone the project**
   ```bash
   git clone https://github.com/Kappa6103/P7_Spring_Boot.git
   cd P7_Spring_Boot
   ```

2**Build the project**
   ```bash
   mvn clean install
   ```
---

## ▶️ Running the Application

1. **Configure the MySQL DataBase**

The connection to the Database can be with environment variables or you can add this lines to the application.properties file :
```
spring.datasource.username=your_username
spring.datasource.password=your_pwd
```

2**Start the application**
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application**
```bash
    URL: http://localhost:8080/home
```
---


## 📁 Project Structure (MVC)
```
src
├── main
│   └── java
│   |   └── com.springboot
│   |       ├── configuration
│   |       ├── controllers
│   |       ├── models
│   |       │   └── config
│   |       ├── repositories
│   |       └── services
│   |           └── interfaces            
│   └── resources
│        ├── DB_scripts
│       ├── static
│       │   └── css
│       └── templates
│           ├── bidList
│           ├── curvePoint
│           ├── rating
│           ├── ruleName
│           ├── trade
│           └── user
└── test
    └── java
        └── com.springboot
            ├── constant
            ├── integration
            │    ├── controllers
            │    └── repositories
            └── services
```

---

## 📱 Pages

### 💼 Main Features
 - CRUD operations on five entities : Bid List, Curve Points, Ratings, Trade and Rule
 - Available there after authentication :
```aiignore
localhost:8080/bidList/list
localhost:8080/curvePoint/list
localhost:8080/ratingbidList/list
localhost:8080/trade/list
localhost:8080/ruleName/list

```

---

## 🔐 Security Features

- Password encryption
- Session management
- CSRF protection
- Form validation

---

## 📫 Educational Context

This project was developed as part of the OpenClassrooms Java Development course, focusing on:
- Spring Boot application development
- Database design and implementation
- Security implementation
- MVC architecture
- Clean code principles

---

## 👤 Author

[Kevin Schade] - OpenClassrooms Student
