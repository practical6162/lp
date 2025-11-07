CREATE DATABASE librarydb;
USE librarydb;

CREATE TABLE Library (
    Lib_ID INT PRIMARY KEY,
    Book_Name VARCHAR(50),
    Author VARCHAR(50),
    Price DECIMAL(8,2)
);

CREATE TABLE Library_Audit (
    Audit_ID INT AUTO_INCREMENT PRIMARY KEY,
    Lib_ID INT,
    Book_Name VARCHAR(50),
    Author VARCHAR(50),
    Price DECIMAL(8,2),
    Action_Type VARCHAR(20),
    Action_Date DATETIME
);
INSERT INTO Library VALUES
(101, 'DBMS', 'Ramakrishnan', 550.00),
(102, 'Operating System', 'Galvin', 600.00),
(103, 'Computer Networks', 'Tanenbaum', 700.00);
DELIMITER $$

CREATE TRIGGER trg_after_update_Library
AFTER UPDATE ON Library
FOR EACH ROW
BEGIN
    INSERT INTO Library_Audit (Lib_ID, Book_Name, Author, Price, Action_Type, Action_Date)
    VALUES (OLD.Lib_ID, OLD.Book_Name, OLD.Author, OLD.Price, 'UPDATED', NOW());
END$$

DELIMITER ;
DELIMITER $$

CREATE TRIGGER trg_after_delete_Library
AFTER DELETE ON Library
FOR EACH ROW
BEGIN
    INSERT INTO Library_Audit (Lib_ID, Book_Name, Author, Price, Action_Type, Action_Date)
    VALUES (OLD.Lib_ID, OLD.Book_Name, OLD.Author, OLD.Price, 'DELETED', NOW());
END$$

DELIMITER ;

UPDATE Library SET Price = 650 WHERE Lib_ID = 102;

DELETE FROM Library WHERE Lib_ID = 103;


--final display
SELECT * FROM Library_Audit;

