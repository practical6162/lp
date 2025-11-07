CREATE DATABASE rollcalldb;
USE rollcalldb;

-- Old RollCall Table
CREATE TABLE O_RollCall (
    RollNo INT PRIMARY KEY,
    Name VARCHAR(50),
    City VARCHAR(50)
);

-- New RollCall Table
CREATE TABLE N_RollCall (
    RollNo INT,
    Name VARCHAR(50),
    City VARCHAR(50)
);

-- Sample Data
INSERT INTO O_RollCall VALUES
(1, 'Amit', 'Pune'),
(2, 'Seema', 'Nashik');

INSERT INTO N_RollCall VALUES
(2, 'Seema', 'Nashik'),
(3, 'Ravi', 'Mumbai'),
(4, 'John', 'Pune');

DELIMITER $$

CREATE PROCEDURE MergeRollCall()
BEGIN
    DECLARE v_roll INT;
    DECLARE v_name VARCHAR(50);
    DECLARE v_city VARCHAR(50);
    DECLARE v_exists INT DEFAULT 0;

    -- Cursor for N_RollCall
    DECLARE cur_nroll CURSOR FOR
        SELECT RollNo, Name, City FROM N_RollCall;

    -- Exit handler when cursor ends
    DECLARE CONTINUE HANDLER FOR NOT FOUND
        SET v_roll = NULL;

    OPEN cur_nroll;

    read_loop: LOOP
        FETCH cur_nroll INTO v_roll, v_name, v_city;
        IF v_roll IS NULL THEN
            LEAVE read_loop;
        END IF;

        -- Check if RollNo already exists in O_RollCall
        SELECT COUNT(*) INTO v_exists
        FROM O_RollCall
        WHERE RollNo = v_roll;

        -- If not exists, insert
        IF v_exists = 0 THEN
            INSERT INTO O_RollCall VALUES (v_roll, v_name, v_city);
            SELECT CONCAT('Inserted RollNo ', v_roll, ' (', v_name, ')') AS Message;
        ELSE
            SELECT CONCAT('Skipped RollNo ', v_roll, ' (Already Exists)') AS Message;
        END IF;
    END LOOP;

    CLOSE cur_nroll;
END$$

DELIMITER ;

--call
CALL MergeRollCall();


