# Employee Pair Longest Project Time

Full-stack web application that identifies the pair of employees who have worked together on common projects for the longest total time.

## 🧩 Project Overview

This application allows users to upload a CSV file containing employee project assignments. It calculates how many days employee pairs have worked together on common projects and identifies the pair with the longest cumulative collaboration.

---

## 🖥️ Tech Stack

- **Frontend**: Angular
- **Backend**: Spring Boot (Java)
- **Build Tool**: Maven
- **Testing**: JUnit (Backend)

---

## 📁 CSV File Format

The CSV file should contain the following columns:

```csv
EmpID, ProjectID, DateFrom, DateTo
143, 12, 2013-11-01, 2014-01-05
218, 10, 2012-05-16, NULL
143, 10, 2009-01-01, 2011-04-27
```

- `DateTo` can be `NULL` (will be interpreted as today’s date).
- Supports multiple date formats (e.g., `yyyy-MM-dd`, `dd/MM/yyyy`, `11 Jul 2024`, etc.).

---

## ⚙️ Backend Setup (Spring Boot)

### Prerequisites

- Java 17+
- Maven

### Installation

1. Navigate to the backend project directory:
   ```bash
   cd employees-backend
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the backend server:
   ```bash
   mvn spring-boot:run
   ```

4. The backend will be running at:  
   `http://localhost:8080`

### API Endpoints

| Method | Endpoint                     | Description                                     |
|--------|------------------------------|-------------------------------------------------|
| POST   | `/api/employees/upload`      | Returns the pair with the longest collaboration |
| POST   | `/api/employees/load-csv-data` | Returns all overlapping collaborations         |

---

## 🌐 Frontend Setup (Angular)

### Prerequisites

- Node.js
- Angular CLI

### Installation

1. Install Angular CLI (if not installed):
   ```bash
   npm install -g @angular/cli
   ```

2. Navigate to the frontend project directory and install dependencies:
   ```bash
   cd employees-frontend
   npm install
   ```

3. Start the Angular development server:
   ```bash
   ng serve
   ```

4. Open your browser at:
   ```text
   http://localhost:4200
   ```

---

## 🧪 Usage Guide

1. Click **Choose File** to select a CSV file
2. The table will load and display all employee collaborations
3. Click **Upload** to calculate the longest-working employee pair
4. The result will appear below the upload section

---

## 🏗️ Build Frontend for Production

To compile the Angular app for deployment:

```bash
ng build
```

Compiled files will be in the `dist/` directory.

---

## 🔐 CORS Configuration

The backend is configured to accept requests from the Angular frontend (`http://localhost:4200`) via Spring CORS setup using `WebMvcConfigurer`.

---

## ✅ Testing

To run backend tests:

```bash
mvn test
```

---

## 📂 Project Structure

```
/project-root
│
├── backend/        → Spring Boot project
│   └── src/main/
│       └── java/... → Business logic & REST controllers
│
├── frontend/       → Angular project
│   └── src/
│       └── app/... → UI and service logic
│
└── README.md       → This file
```

---

## 📬 Contact

For questions or suggestions, feel free to reach out.
