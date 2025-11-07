
-- DBMS PRACTICAL EXAM SQL SCRIPT (MySQL Compatible)
-- Database Creation
CREATE DATABASE dbms_exam;
USE dbms_exam;

-- Table Creation
CREATE TABLE Person (
  driver_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50),
  address VARCHAR(100)
);

CREATE TABLE Car (
  license VARCHAR(20) PRIMARY KEY,
  model VARCHAR(30),
  year INT
);

CREATE TABLE Accident (
  report_no INT PRIMARY KEY AUTO_INCREMENT,
  date_acc DATE,
  location VARCHAR(50)
);

CREATE TABLE Owns (
  driver_id INT,
  license VARCHAR(20),
  PRIMARY KEY (driver_id, license),
  FOREIGN KEY (driver_id) REFERENCES Person(driver_id),
  FOREIGN KEY (license) REFERENCES Car(license)
);

CREATE TABLE Participated (
  driver_id INT,
  model VARCHAR(30),
  report_no INT,
  damage_amount DECIMAL(10,2),
  FOREIGN KEY (driver_id) REFERENCES Person(driver_id),
  FOREIGN KEY (report_no) REFERENCES Accident(report_no)
);

-- Data Insertion
INSERT INTO Person (name, address) VALUES
('Rahul', 'Pune'),
('Amit', 'Mumbai'),
('Sneha', 'Delhi');

INSERT INTO Car VALUES
('MH12AB1234', 'Honda City', 2020),
('MH14CD5678', 'Swift', 2018),
('MH15EF9101', 'Creta', 2022);

INSERT INTO Accident (date_acc, location) VALUES
('2025-01-15', 'Pune'),
('2025-05-12', 'Mumbai');

INSERT INTO Owns VALUES
(1, 'MH12AB1234'),
(2, 'MH14CD5678'),
(3, 'MH15EF9101');

INSERT INTO Participated VALUES
(1, 'Honda City', 1, 25000),
(2, 'Swift', 2, 18000);

-- Views
CREATE VIEW Driver_Car_View AS
SELECT p.name, c.model FROM Person p
JOIN Owns o ON p.driver_id = o.driver_id
JOIN Car c ON o.license = c.license;

SELECT * FROM Driver_Car_View;

-- Modify view to include year
CREATE OR REPLACE VIEW Driver_Car_View AS
SELECT p.name, c.model, c.year
FROM Person p
JOIN Owns o ON p.driver_id = o.driver_id
JOIN Car c ON o.license = c.license;

-- Composite Index
CREATE INDEX idx_model_year ON Car(model, year);

-- View for high damage
CREATE VIEW High_Damage_View AS
SELECT p.name, pa.damage_amount
FROM Person p
JOIN Participated pa ON p.driver_id = pa.driver_id
WHERE pa.damage_amount > 20000;

-- Index on name
CREATE INDEX idx_name ON Person(name);
DROP INDEX idx_name ON Person;

-- View for recent accidents
CREATE VIEW Recent_Accidents AS
SELECT * FROM Accident WHERE YEAR(date_acc) = 2025;
DROP VIEW Recent_Accidents;

-- Synonym simulation using view
CREATE VIEW acc_info AS SELECT * FROM Accident;
INSERT INTO acc_info (date_acc, location) VALUES ('2025-07-07', 'Nashik');

-- Unique Index
CREATE UNIQUE INDEX idx_license ON Car(license);

-- View for cars after 2019
CREATE VIEW New_Cars_Owners AS
SELECT p.name, c.model, c.year
FROM Person p
JOIN Owns o ON p.driver_id = o.driver_id
JOIN Car c ON o.license = c.license
WHERE c.year > 2019;

-- Synonym for Owns table
CREATE VIEW owns_syn AS SELECT * FROM Owns;
SELECT * FROM owns_syn;

-- Index for damage amount
CREATE INDEX idx_damage_amount ON Participated(damage_amount);
