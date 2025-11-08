CREATE TABLE Borrower (
 Rollin NUMBER PRIMARY KEY,
 Name VARCHAR2(30),
 DateofIssue DATE,
 NameofBook VARCHAR2(50),
 Status CHAR(1)
);
CREATE TABLE Fine (
 Roll_no NUMBER,
 Date DATE,
 Amt NUMBER
);
INSERT INTO Borrower VALUES (101, 'Gayatri', TO_DATE('10-OCT-2025','DDMON-YYYY'), 'DBMS Concepts', 'I');
INSERT INTO Borrower VALUES (102, 'Rahul', TO_DATE('20-OCT-2025','DD-MONYYYY'), 'OS Notes', 'I');
INSERT INTO Borrower VALUES (103, 'Sneha', TO_DATE('05-SEP-2025','DD-MONYYYY'), 'CN Book', 'I');
COMMIT;
SET SERVEROUTPUT ON;
DECLARE
 v_rollno Borrower.Rollin%TYPE;
 v_bookname Borrower.NameofBook%TYPE;
 v_dateofissue Borrower.DateofIssue%TYPE;
 v_days NUMBER;
 v_fine_amt NUMBER := 0;
 v_status Borrower.Status%TYPE;
BEGIN
 v_rollno := &RollNo;
 v_bookname := '&BookName';
 SELECT DateofIssue, Status
 INTO v_dateofissue, v_status
 FROM Borrower
 WHERE Rollin = v_rollno AND NameofBook = v_bookname;
 v_days := TRUNC(SYSDATE - v_dateofissue);
 IF v_status = 'I' THEN
 IF v_days BETWEEN 15 AND 30 THEN
 v_fine_amt := v_days * 5;
 ELSIF v_days > 30 THEN
 v_fine_amt := v_days * 50;
 ELSE
 v_fine_amt := 0;
 END IF;
 UPDATE Borrower
 SET Status = 'R'
 WHERE Rollin = v_rollno AND NameofBook = v_bookname;
 IF v_fine_amt > 0 THEN
 INSERT INTO Fine (Roll_no, Date, Amt)
 VALUES (v_rollno, SYSDATE, v_fine_amt);
 END IF;
 DBMS_OUTPUT.PUT_LINE('Book returned successfully!');
 DBMS_OUTPUT.PUT_LINE('Total days: ' || v_days);
 DBMS_OUTPUT.PUT_LINE('Fine amount: Rs. ' || v_fine_amt);
 ELSE
 DBMS_OUTPUT.PUT_LINE('Book already returned or invalid status.');
 END IF;
EXCEPTION
 WHEN NO_DATA_FOUND THEN
 DBMS_OUTPUT.PUT_LINE('No such record found for the given Roll No. and
Book Name.');
 WHEN OTHERS THEN
 DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
**************
