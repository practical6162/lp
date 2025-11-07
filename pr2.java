CREATE TABLE Stud (
    Roll INT PRIMARY KEY,
    Att DECIMAL(5,2),
    Status VARCHAR(5)
);

INSERT INTO Stud VALUES
(1, 80, NULL),
(2, 70, NULL),
(3, 50, NULL),
(4, 90, NULL);


DELIMITER $$

CREATE PROCEDURE CheckAttendance(IN p_roll INT)
BEGIN
    DECLARE v_att DECIMAL(5,2);
    DECLARE not_found CONDITION FOR SQLSTATE '02000';   -- no data found
    DECLARE EXIT HANDLER FOR not_found
    BEGIN
        SELECT CONCAT('Error: Roll number ', p_roll, ' not found.') AS Message;
    END;

    -- Get attendance of the given roll number
    SELECT Att INTO v_att FROM Stud WHERE Roll = p_roll;

    -- Decision making
    IF v_att < 75 THEN
        UPDATE Stud SET Status = 'D' WHERE Roll = p_roll;
        SELECT 'Term not granted' AS Message;
    ELSE
        UPDATE Stud SET Status = 'ND' WHERE Roll = p_roll;
        SELECT 'Term granted' AS Message;
    END IF;
END$$

DELIMITER ;


--call
CALL CheckAttendance(2);

