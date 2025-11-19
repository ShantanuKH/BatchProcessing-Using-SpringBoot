# 📦 Spring Boot Batch Processing

This project shows how to use **Spring Boot + Spring Batch** to read employee data from a **CSV file**, process it, calculate duration spent in the company, and finally store everything into a **MySQL database**.

The entire project is written in a clean, simple, and beginner-friendly style, making it easy to understand how batch processing works in real-world applications.


---

## 🔧 Technologies Used

- **Java**
- **Spring Boot**
- **Spring Batch**
- **MySQL Driver**
- **Spring Data JDBC**
- **MySQL**
- **JDBC Batch Writer**
- **CSV FlatFileItemReader**

---

## 🗂️ SQL Query

```sql
CREATE DATABASE springBatchProcessing;

USE springBatchProcessing;

CREATE TABLE Employees (
    EmployeeID INT PRIMARY KEY,
    Name VARCHAR(100),
    Department VARCHAR(50),
    StartDate DATE,
    EndDate DATE,
    Duration VARCHAR(50)
);

SELECT * FROM Employees;

DROP TABLE Employees;

```
## 🖼️ SQL Database Screenshot

The SQL database screenshot shows the final processed output stored in the **Employees** table.  
This confirms that:

- All records from the CSV file were successfully inserted  
- Active employees have `NULL` as their EndDate  
- Processed duration values (days and years) were stored correctly  
- The Batch Writer executed the INSERT queries without errors  

This screenshot verifies that the **Reader → Processor → Writer** pipeline worked correctly and the database reflects accurate, clean, and structured data.

<div style="text-align:center;">
    <img src="https://raw.githubusercontent.com/ShantanuKH/BatchProcessing-Using-SpringBoot/refs/heads/master/Screenshots/DB.png" alt="Image" style="width:400px; height:auto;">
</div>

---

## 🖥️ Console Output Screenshot

The console screenshot displays the complete batch execution logs.  
It highlights the following:

- Spring Batch job successfully started  
- Each CSV record was processed  
- Duration for each employee was calculated  
- Active employees were detected and handled properly  
- The job finished with **COMPLETED** status  

This confirms that the batch job ran smoothly from start to end with correct processing and no failures.

<div style="text-align:center;">
    <img src="https://raw.githubusercontent.com/ShantanuKH/BatchProcessing-Using-SpringBoot/refs/heads/master/Screenshots/console.png" alt="Console Output" style="width:400px; height:auto;">
</div>

---

## ⚙️ Don't Forget to Update Your `application.properties` File

Add your MySQL database configuration (URL, username, and password) in the `application.properties` file so Spring Boot can connect to your database properly.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.batch.jdbc.initialize-schema=always
