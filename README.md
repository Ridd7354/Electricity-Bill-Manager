# ⚡ Electricity Bill Manager (Java Swing + MySQL)

## 🧾 Description
The **Electricity Bill Manager** is a Java Swing–based desktop application that helps calculate and manage electricity bills efficiently.  
Users can enter customer details, calculate bills based on units consumed, and store data securely in a **MySQL database** using **JDBC**.  
The app also provides a **search feature** to retrieve existing customer records quickly.

---

## 🚀 Features
- 🖥️ **User-friendly GUI** built with Java Swing  
- 💡 **Automatic bill calculation** based on units consumed  
- 🗃️ **Database integration** using MySQL (via JDBC)  
- 🔍 **Search function** to find bills by customer name  
- ⚙️ **Input validation** and error handling for better reliability  


<img width="514" height="496" alt="e2" src="https://github.com/user-attachments/assets/2a8301ee-77ab-41bc-8f62-cfa7acc29cc5" />
<img width="506" height="485" alt="e3" src="https://github.com/user-attachments/assets/6adeeb4d-88d4-4574-97f6-102a26872e04" />
<img width="505" height="493" alt="e1" src="https://github.com/user-attachments/assets/2d952241-c2eb-4424-8feb-689860f9fd7b" />


## 🧮 Bill Calculation Logic
| Units Consumed | Rate per Unit |
|-----------------|----------------|
| 0 – 100 units   | ₹5 per unit     |
| 101 – 200 units | ₹7 per unit     |
| Above 200 units | ₹10 per unit    |

**Example:**  
If a user consumes 250 units →  
`(100 × 5) + (100 × 7) + (50 × 10) = ₹1,700`


<img width="1470" height="956" alt="mySQL" src="https://github.com/user-attachments/assets/ef3e2329-a03b-4601-bd55-3b16dac7eabf" />


## 🗄️ Database Setup (MySQL)

### 🧩 Step 1: SQL Script
Run the following SQL commands in **MySQL Workbench** or **phpMyAdmin**:

```sql
CREATE DATABASE IF NOT EXISTS electricity;

USE electricity;

CREATE TABLE IF NOT EXISTS electricity_bill (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  units INT,
  amount INT
);

SELECT * FROM electricity_bill;
