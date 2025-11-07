
-- DBMS PRACTICAL EXAM: Problem 3
-- Create Customer and Account Tables and Perform Queries

-- Step 1: Create Tables
CREATE TABLE Customer (
  C_Id INT PRIMARY KEY,
  Cname VARCHAR(30),
  City VARCHAR(30)
);

CREATE TABLE Account (
  C_Id INT,
  Acc_Type VARCHAR(20),
  Amount INT,
  FOREIGN KEY (C_Id) REFERENCES Customer(C_Id)
);

-- Step 2: Insert Values into Customer Table
INSERT INTO Customer VALUES
(1, 'John', 'Nashik'),
(2, 'Seema', 'Aurangabad'),
(3, 'Amita', 'Nagar'),
(4, 'Rakesh', 'Pune'),
(5, 'Samata', 'Nashik'),
(6, 'Ankita', 'Chandwad'),
(7, 'Bhavika', 'Pune'),
(8, 'Deepa', 'Mumbai'),
(9, 'Nitin', 'Nagpur'),
(10, 'Pooja', 'Pune');

-- Step 3: Insert Values into Account Table
INSERT INTO Account VALUES
(1, 'Current', 5000),
(2, 'Saving', 20000),
(3, 'Saving', 70000),
(4, 'Saving', 50000),
(6, 'Current', 35000),
(7, 'Loan', 30000),
(8, 'Saving', 50000),
(9, 'Saving', 90000),
(10, 'Loan', 8000),
(11, 'Current', 45000);

-- Step 4: Queries

-- 1) Show the cname, Acc_Type, amount for customers having saving account
SELECT c.Cname, a.Acc_Type, a.Amount
FROM Customer c
JOIN Account a ON c.C_Id = a.C_Id
WHERE a.Acc_Type = 'Saving';

-- 2) Display data using Natural, Left and Right Join
SELECT * FROM Customer NATURAL JOIN Account;

SELECT c.C_Id, c.Cname, c.City, a.Acc_Type, a.Amount
FROM Customer c LEFT JOIN Account a ON c.C_Id = a.C_Id;

SELECT c.C_Id, c.Cname, c.City, a.Acc_Type, a.Amount
FROM Customer c RIGHT JOIN Account a ON c.C_Id = a.C_Id;

-- 3) Display customers living in same city as 'Pooja'
SELECT * FROM Customer
WHERE City = (SELECT City FROM Customer WHERE Cname = 'Pooja');

-- 4) Display accounts with less than average amount
SELECT * FROM Account
WHERE Amount < (SELECT AVG(Amount) FROM Account);

-- 5) Display C_Id having maximum amount
SELECT C_Id FROM Account
WHERE Amount = (SELECT MAX(Amount) FROM Account);

-- 6) Display amount and acc_type of customers whose amount is the minimum of that acc_type
SELECT Acc_Type, Amount
FROM Account a
WHERE Amount = (
  SELECT MIN(Amount) FROM Account b WHERE a.Acc_Type = b.Acc_Type
);

-- 7) Display amount of accounts whose amount is higher than any saving account amount
SELECT * FROM Account
WHERE Amount > ANY (SELECT Amount FROM Account WHERE Acc_Type = 'Saving');
