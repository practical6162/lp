CREATE DATABASE librarydb;
USE librarydb;

-- Borrower Table
CREATE TABLE Borrower (
    Rollin INT PRIMARY KEY,
    Name VARCHAR(50),
    DateofIssue DATE,
    NameofBook VARCHAR(50),
    Status CHAR(1)
);

-- Fine Table
CREATE TABLE Fine (
    Roll_no INT,
    Date DATE,
    Amt INT
);

INSERT INTO Borrower VALUES
(1, 'Amit', '2025-09-10', 'DBMS', 'I'),
(2, 'Seema', '2025-10-15', 'CN', 'I'),
(3, 'Ravi', '2025-10-25', 'AI', 'I'),
(4, 'Pooja', '2025-09-05', 'DBMS', 'I'),
(5, 'John', '2025-10-20', 'OS', 'I');

DELIMITER $$

CREATE PROCEDURE CheckFine(IN v_rollin INT, IN v_bookname VARCHAR(50))
BEGIN
    DECLARE v_dateofissue DATE;
    DECLARE v_days INT DEFAULT 0;
    DECLARE v_fine INT DEFAULT 0;
    DECLARE v_status CHAR(1);

    -- Exception handler: if any error occurs
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SELECT 'Error: Invalid Roll No or Book Name!' AS Message;
        ROLLBACK;
    END;

    START TRANSACTION;

    -- Fetch date of issue and status
    SELECT DateofIssue, Status
    INTO v_dateofissue, v_status
    FROM Borrower
    WHERE Rollin = v_rollin AND NameofBook = v_bookname;

    -- Calculate number of days from issue
    SET v_days = DATEDIFF(CURDATE(), v_dateofissue);

    -- Fine logic
    IF v_days BETWEEN 15 AND 30 THEN
        SET v_fine = v_days * 5;
    ELSEIF v_days > 30 THEN
        SET v_fine = v_days * 50;
    ELSE
        SET v_fine = 0;
    END IF;

    -- Update borrower status
    UPDATE Borrower
    SET Status = 'R'
    WHERE Rollin = v_rollin AND NameofBook = v_bookname;

    -- If fine is applicable, insert into Fine table
    IF v_fine > 0 THEN
        INSERT INTO Fine VALUES (v_rollin, CURDATE(), v_fine);
        SELECT CONCAT('Fine of Rs. ', v_fine, ' added for Roll No ', v_rollin) AS Message;
    ELSE
        SELECT ' No fine applicable.' AS Message;
    END IF;

    COMMIT;
END$$

DELIMITER ;
 
 
 
 --call
 CALL CheckFine(1, 'DBMS');

