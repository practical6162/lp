CREATE DATABASE companydb;


USE companydb;


CREATE TABLE emp (
  Eno INT PRIMARY KEY AUTO_INCREMENT,
  Ename VARCHAR(50) NOT NULL,
  Address VARCHAR(100) DEFAULT 'Nashik',
  Joindate DATE,
  Salary DECIMAL(10,2)
) AUTO_INCREMENT = 101;



ALTER TABLE emp ADD Post VARCHAR(50);


INSERT INTO emp (Ename, Address, Joindate, Salary, Post)
VALUES 
('Amit', 'Pune', '2023-06-15', 25000, 'Manager'),
('Sneha', DEFAULT, '2022-09-10', 18000, 'Engineer'),
('Ravi', 'Mumbai', '2021-05-25', 30000, 'Clerk'),
('Karan', 'Nashik', '2023-01-10', 22000, 'Analyst');


CREATE INDEX idx_emp_ename ON emp(Ename);


CREATE VIEW emp_view AS SELECT Ename, Salary FROM emp;


SELECT * FROM emp_view;


SELECT * FROM emp;


SHOW INDEX FROM emp;


SHOW CREATE VIEW emp_view;
