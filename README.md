# 💰 Personal Finance Tracker  

## 🧾 Overview  
The **Personal Finance Tracker** is a Java-based web application designed to help users manage their income, expenses, and savings efficiently.  
It provides a clean, interactive dashboard to record transactions, view category-wise spending, and generate reports in CSV and PDF formats.  

This project follows the **MVC (Model–View–Controller)** architecture and is built using **Java Servlets, JSP, JDBC, and MySQL**.  

---

## 🚀 Features  

✅ **Add / View / Delete Transactions** — Record and manage daily financial entries.  
✅ **Dashboard Summary** — Displays total income, expense, and savings dynamically.  
✅ **Interactive Charts** — Visualize spending patterns using Chart.js (Pie & Bar charts).  
✅ **Report Generation** — Export transactions in **CSV** and **PDF** formats using iTextPDF.  
✅ **Category-wise Breakdown** — Track how much is spent on each category (Food, Bills, Travel, etc.).  
✅ **User-Friendly Interface** — Responsive JSP pages styled with CSS for a modern look.  

---

## 🧠 Architecture  

This project implements the **MVC architecture**:

    ┌──────────────┐
    │   JSP Pages  │  ← View Layer (Frontend)
    └──────┬───────┘
           │
           ▼
    ┌──────────────┐
    │   Servlets   │  ← Controller Layer (Logic)
    └──────┬───────┘
           │
           ▼
    ┌──────────────┐
    │   DAO Layer  │  ← Model Layer (Database Access)
    └──────┬───────┘
           │
           ▼
    ┌──────────────┐
    │   MySQL DB   │  ← Data Storage
    └──────────────┘

---

## 🛠️ Technologies Used  

| Component | Technology |
|------------|-------------|
| **Frontend** | HTML, CSS, JSP, Chart.js |
| **Backend** | Java Servlets, JDBC |
| **Database** | MySQL |
| **Libraries** | iTextPDF (PDF Export), Gson (JSON Processing) |
| **Server** | Apache Tomcat 9+ |
| **IDE** | Eclipse IDE for Enterprise Java Developers |
| **JDK Version** | JDK 11 or above |

---

## 💾 Database Configuration  

```sql
CREATE DATABASE IF NOT EXISTS finance_tracker_db;
USE finance_tracker_db;

CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(20) NOT NULL,
    category VARCHAR(50) NOT NULL,
    amount DOUBLE NOT NULL,
    date DATE NOT NULL,
    note VARCHAR(255)
);



<img width="997" height="836" alt="Screenshot 2025-11-08 131553" src="https://github.com/user-attachments/assets/c98454e4-942d-4a0a-8e3c-bb087caf2561" />
<img width="1447" height="903" alt="Screenshot 2025-11-08 131651" src="https://github.com/user-attachments/assets/0bba0194-35e5-4ff9-a5a5-c93750c5eb1c" />
<img width="1352" height="736" alt="Screenshot 2025-11-08 131717" src="https://github.com/user-attachments/assets/b23b21ac-9ebb-4809-8984-bd2ce46d39f3" />

