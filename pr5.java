-- Create Department-Based Schema with Constraints

-- Drop tables if they already exist (to avoid conflicts)
DROP TABLE course CASCADE CONSTRAINTS;
DROP TABLE instructor CASCADE CONSTRAINTS;
DROP TABLE student CASCADE CONSTRAINTS;

-- Create STUDENT table
CREATE TABLE student (
  S_ID INT PRIMARY KEY,
  name VARCHAR(50),
  dept_name VARCHAR(50),
  tot_cred INT
);

-- Create INSTRUCTOR table
CREATE TABLE instructor (
  T_ID INT PRIMARY KEY,
  name VARCHAR(50),
  dept_name VARCHAR(50),
  salary NUMBER(10,2)
);

-- Create COURSE table
CREATE TABLE course (
  course_id INT PRIMARY KEY,
  title VARCHAR(50),
  dept_name VARCHAR(50),
  credits INT,
  FOREIGN KEY (dept_name) REFERENCES instructor(dept_name)
);

-- Insert sample data
INSERT INTO student VALUES (1, 'Amol', 'Computer', 80);
INSERT INTO student VALUES (2, 'Amit', 'Mechanical', 75);
INSERT INTO student VALUES (3, 'Samyak', 'Computer', 85);
INSERT INTO student VALUES (4, 'Kamlesh', 'Electrical', 70);
INSERT INTO student VALUES (5, 'Ramesh', 'Computer', 90);

INSERT INTO instructor VALUES (101, 'Anil', 'Computer', 50000);
INSERT INTO instructor VALUES (102, 'Sneha', 'Mechanical', 40000);
INSERT INTO instructor VALUES (103, 'Ravi', 'Electrical', 60000);
INSERT INTO instructor VALUES (104, 'Meena', 'Computer', 45000);
INSERT INTO instructor VALUES (105, 'Amol', 'Civil', 35000);

INSERT INTO course VALUES (201, 'DBMS', 'Computer', 4);
INSERT INTO course VALUES (202, 'DSA', 'Computer', 4);
INSERT INTO course VALUES (203, 'Thermo', 'Mechanical', 3);
INSERT INTO course VALUES (204, 'Networks', 'Computer', 4);
INSERT INTO course VALUES (205, 'Circuits', 'Electrical', 3);

COMMIT;

-- -------------------------------------------------------
-- i. Find the average salary of instructors in departments
-- where the average salary is more than Rs. 42000
-- -------------------------------------------------------
SELECT dept_name, AVG(salary) AS avg_salary
FROM instructor
GROUP BY dept_name
HAVING AVG(salary) > 42000;

-- -------------------------------------------------------
-- ii. Increase the salary of each instructor
-- in the Computer department by 10%
-- -------------------------------------------------------
UPDATE instructor
SET salary = salary * 1.10
WHERE dept_name = 'Computer';

-- -------------------------------------------------------
-- iii. Find names of instructors whose names are neither ‘Amol’ nor ‘Amit’
-- -------------------------------------------------------
SELECT name
FROM instructor
WHERE name NOT IN ('Amol', 'Amit');

-- -------------------------------------------------------
-- iv. Find the names of students which contain ‘am’ as a substring
-- -------------------------------------------------------
SELECT name
FROM student
WHERE LOWER(name) LIKE '%am%';

-- -------------------------------------------------------
-- v. Find the names of students from Computer department
-- that take “DBMS” course
-- (Assume students from same department take their dept’s courses)
-- -------------------------------------------------------
SELECT s.name
FROM student s
JOIN course c ON s.dept_name = c.dept_name
WHERE s.dept_name = 'Computer' AND c.title = 'DBMS';

COMMIT;
