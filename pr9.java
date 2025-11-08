CREATE TABLE Library (
 Book_ID NUMBER PRIMARY KEY,
 Title VARCHAR2(50),
 Author VARCHAR2(50),
 Price NUMBER(8,2)
);
CREATE TABLE Library_Audit (
 Book_ID NUMBER,
 Title VARCHAR2(50),
 Author VARCHAR2(50),
 Price NUMBER(8,2),
 Operation_Type VARCHAR2(10),
 Operation_Date DATE
);
INSERT INTO Library VALUES (1, 'DBMS Concepts', 'Ramakrishnan', 550);
INSERT INTO Library VALUES (2, 'Operating Systems', 'Galvin', 650);
INSERT INTO Library VALUES (3, 'Computer Networks', 'Tanenbaum', 600);
COMMIT;
SQL> edit trg_library_audit.sql
CREATE OR REPLACE TRIGGER trg_library_audit
BEFORE UPDATE OR DELETE
ON Library
FOR EACH ROW
BEGIN
 INSERT INTO Library_Audit
 (Book_ID, Title, Author, Price, Operation_Type, Operation_Date)
 VALUES
 (:OLD.Book_ID, :OLD.Title, :OLD.Author, :OLD.Price,
 CASE
 WHEN UPDATING THEN 'UPDATE'
 WHEN DELETING THEN 'DELETE'
 END,
 SYSDATE);
END;
/
SQL> @trg_library_audit.sql
----------------------------------------------------------------------------
UPDATE Library
SET Price = 700
WHERE Book_ID = 2;
-----------------------------------------------------------------------------------
DELETE FROM Library
WHERE Book_ID = 3;
------------------------------------------------------------------------------------
SQL> SELECT * FROM Library_Audit;
*******************************
