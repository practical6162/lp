CREATE DATABASE studentdb;
USE studentdb;

CREATE TABLE Stud_Marks (
    Roll INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(50),
    Total_Marks INT
);

CREATE TABLE Result (
    Roll INT,
    Name VARCHAR(50),
    Class VARCHAR(30)
);

INSERT INTO Stud_Marks (Name, Total_Marks) VALUES
('Amit', 1200),
('Seema', 950),
('Ravi', 870),
('Pooja', 800),
('John', 1400);

DELIMITER $$

CREATE PROCEDURE proc_Grade()
BEGIN
    DECLARE v_roll INT;
    DECLARE v_name VARCHAR(50);
    DECLARE v_marks INT;
    DECLARE v_class VARCHAR(30);

    -- Cursor to read all students
    DECLARE cur_student CURSOR FOR
        SELECT Roll, Name, Total_Marks FROM Stud_Marks;

    -- Exit handler when no more rows
    DECLARE CONTINUE HANDLER FOR NOT FOUND
        SET v_roll = NULL;

    OPEN cur_student;

    read_loop: LOOP
        FETCH cur_student INTO v_roll, v_name, v_marks;
        IF v_roll IS NULL THEN
            LEAVE read_loop;
        END IF;

        -- Categorization logic
        IF v_marks BETWEEN 990 AND 1500 THEN
            SET v_class = 'Distinction';
        ELSEIF v_marks BETWEEN 900 AND 989 THEN
            SET v_class = 'First Class';
        ELSEIF v_marks BETWEEN 825 AND 899 THEN
            SET v_class = 'Higher Second Class';
        ELSE
            SET v_class = 'Fail';
        END IF;

        -- Insert result into Result table
        INSERT INTO Result VALUES (v_roll, v_name, v_class);

        -- Show message (for terminal output)
        SELECT CONCAT(v_name, ' => ', v_class) AS Result;
    END LOOP;

    CLOSE cur_student;
END$$

DELIMITER ;
--call
CALL proc_Grade();

