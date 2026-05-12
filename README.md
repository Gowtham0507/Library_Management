# LibraFlow - Library Management System

LibraFlow is a modern full-stack Library Management System built with Java Spring Boot (Backend) and HTML/CSS/JavaScript with Bootstrap 5 (Frontend). It provides robust functionality to manage books, members, and book issues with real-time validations.

##  Features
- **Book Management:** Add, list, search, and delete books.
- **Member Management:** Register and manage library members.
- **Issue/Return Books:** Issue up to 3 books per member, prevent duplicate issues, and handle returns dynamically.
- **Beautiful Dashboard:** View real-time statistics and recently issued books on a responsive, beautifully styled dashboard.

##  Technology Stack
- **Backend:** Java 17, Spring Boot 3, Spring Data JPA, Hibernate.
- **Database:** MySQL.
- **Frontend:** HTML5, CSS3 (Custom Glassmorphism), JavaScript (Fetch API), Bootstrap 5.

---

##  How to Run Locally

### 1. Prerequisites
- Java 17+ installed
- Maven installed
- MySQL running locally

### 2. Database Setup
Create the database manually (or let Hibernate auto-create it depending on MySQL settings):
```sql
CREATE DATABASE IF NOT EXISTS libraflow;
```

Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.username=root
spring.datasource.password=Asif@89789
```

### 3. Build & Run
Open your terminal in the project root directory:
```bash
mvn clean install
mvn spring-boot:run
```

### 4. Access the Application
Open your browser and navigate to:
**[http://localhost:8080/](http://localhost:8080/)**

---

##  GitHub Collaboration Guide (4 Members)

To collaborate smoothly, create a GitHub repository and follow this structure.

### Member 1: Book Management Module
Responsible for the Book entity, repository, service, and controller.
```bash
git checkout -b feature/book-module
git add .
git commit -m "Added Book Management Module (Entities, Services, Controllers)"
git push origin feature/book-module
```

### Member 2: Member Management Module
Responsible for Member entity, repository, service, and controller.
```bash
git checkout -b feature/member-module
git add .
git commit -m "Implemented Member APIs and validation"
git push origin feature/member-module
```

### Member 3: Issue/Return Services
Responsible for IssueRecord entity, business logic (max 3 books limit, duplicate checks), and issue controller.
```bash
git checkout -b feature/issue-services
git add .
git commit -m "Developed Issue and Return Services with business rules"
git push origin feature/issue-services
```

### Member 4: UI + Integration + Testing
Responsible for HTML/CSS/JS frontend, connecting the UI to REST APIs, and Postman testing.
```bash
git checkout -b feature/ui-integration
git add .
git commit -m "Integrated UI, Bootstrap, and connected MySQL Database via Fetch API"
git push origin feature/ui-integration
```

**Merging:** Create Pull Requests (PRs) for each branch to merge into `main`.

---

##  API Reference

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/books` | Get all books |
| GET | `/books/available` | Get available books |
| GET | `/books/search?query=...`| Search books by title/author |
| POST | `/books` | Add a new book |
| PUT | `/books/{id}` | Update a book |
| DELETE | `/books/{id}` | Delete a book |
| POST | `/members` | Register a new member |
| GET | `/members` | Get all members |
| DELETE | `/members/{id}`| Delete a member |
| POST | `/issues/issue`| Issue a book to member |
| PUT | `/issues/return/{id}`| Return an issued book |
| GET | `/issues/active`| View all currently issued books |
