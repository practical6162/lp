edit term_check.sql ;
SET SERVEROUTPUT ON;
-- Create the table (only run once; skip next time if table already exists)
CREATE TABLE stud (
 roll NUMBER PRIMARY KEY,
 att NUMBER,
 status VARCHAR2(5)
);
-- Insert sample data (only run once)
INSERT INTO stud VALUES (1, 80, NULL);
INSERT INTO stud VALUES (2, 65, NULL);
COMMIT;
-- ====== PL/SQL Block Starts ======
DECLARE
 v_roll NUMBER;
 v_att NUMBER;
BEGIN
 -- Ask user to enter Roll Number
 v_roll := &roll;
 -- Fetch attendance
 SELECT att INTO v_att
 FROM stud
 WHERE roll = v_roll;
 -- Check attendance
 IF v_att < 75 THEN
 DBMS_OUTPUT.PUT_LINE('Term not granted');
 UPDATE stud SET status = 'D' WHERE roll = v_roll;
 ELSE
 DBMS_OUTPUT.PUT_LINE('Term granted');
 UPDATE stud SET status = 'ND' WHERE roll = v_roll;
 END IF;
 COMMIT;
EXCEPTION
 WHEN NO_DATA_FOUND THEN
 DBMS_OUTPUT.PUT_LINE('No student found with that Roll Number.');
 WHEN OTHERS THEN
 DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
-- ====== PL/SQL Block Ends ======
-- Show updated table
SELECT * FROM stud;
SQL > @term_check.sql ;
SQL> set serveroutput on ;
