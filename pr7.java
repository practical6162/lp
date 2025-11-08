CREATE TABLE Stud_Marks (
 Roll NUMBER PRIMARY KEY,
 Name VARCHAR2(30),
 Total_Marks NUMBER(5)
);
CREATE TABLE Result (
 Roll NUMBER,
 Name VARCHAR2(30),
 Class VARCHAR2(30)
);
INSERT INTO Stud_Marks VALUES (1, 'Gayatri', 1450);
INSERT INTO Stud_Marks VALUES (2, 'Amit', 920);
INSERT INTO Stud_Marks VALUES (3, 'Sneha', 870);
INSERT INTO Stud_Marks VALUES (4, 'Rohit', 800);
INSERT INTO Stud_Marks VALUES (5, 'Neha', 1520);
COMMIT;
CREATE OR REPLACE PROCEDURE proc_Grade (
 p_roll Stud_Marks.Roll%TYPE,
 p_name Stud_Marks.Name%TYPE,
 p_total Stud_Marks.Total_Marks%TYPE
) AS
 v_class VARCHAR2(30);
BEGIN
 -- Determine category based on total marks
 IF p_total BETWEEN 990 AND 1500 THEN
 v_class := 'Distinction';
 ELSIF p_total BETWEEN 900 AND 989 THEN
 v_class := 'First Class';
 ELSIF p_total BETWEEN 825 AND 899 THEN
 v_class := 'Higher Second Class';
 ELSE
 v_class := 'Fail';
 END IF;
 -- Insert into Result table
 INSERT INTO Result (Roll, Name, Class)
 VALUES (p_roll, p_name, v_class);
 DBMS_OUTPUT.PUT_LINE('Inserted: ' || p_name || ' - ' || v_class);
EXCEPTION
 WHEN OTHERS THEN
 DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
------------------------------------------------------------------------------------------------
SET SERVEROUTPUT ON;
BEGIN
 FOR rec IN (SELECT * FROM Stud_Marks) LOOP
 proc_Grade(rec.Roll, rec.Name, rec.Total_Marks);
 END LOOP;
 DBMS_OUTPUT.PUT_LINE('--- Student Grade Categorization Completed ---');
END;
/
------------------------------------------------------------------------------------------
SELECT * FROM Result;
