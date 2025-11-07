-- PL/SQL Unnamed Block for Borrower and Fine Management (Control + Exception Handling)

-- Create Borrower table
CREATE TABLE Borrower (
  Rollin NUMBER PRIMARY KEY,
  Name VARCHAR2(30),
  DateofIssue DATE,
  NameofBook VARCHAR2(50),
  Status CHAR(1)
);

-- Create Fine table
CREATE TABLE Fine (
  Roll_no NUMBER,
  Date_ DATE,
  Amt NUMBER(10,2)
);

-- Insert sample data
INSERT INTO Borrower VALUES (1, 'Rahul', TO_DATE('2025-10-01', 'YYYY-MM-DD'), 'DBMS', 'I');
INSERT INTO Borrower VALUES (2, 'Sneha', TO_DATE('2025-10-20', 'YYYY-MM-DD'), 'OS', 'I');
INSERT INTO Borrower VALUES (3, 'Amit', TO_DATE('2025-09-25', 'YYYY-MM-DD'), 'CN', 'I');
COMMIT;

-- Enable output
SET SERVEROUTPUT ON;

-- Unnamed PL/SQL Block
DECLARE
  v_roll Borrower.Rollin%TYPE;
  v_book Borrower.NameofBook%TYPE;
  v_date_issue Borrower.DateofIssue%TYPE;
  v_days NUMBER;
  v_fine NUMBER := 0;
BEGIN
  -- Accept roll number and book name from user
  v_roll := &roll_no;
  v_book := '&book_name';

  -- Fetch date of issue
  SELECT DateofIssue INTO v_date_issue
  FROM Borrower
  WHERE Rollin = v_roll AND NameofBook = v_book AND Status = 'I';

  -- Calculate number of days since issue
  v_days := TRUNC(SYSDATE - v_date_issue);

  -- Fine calculation using control structures
  IF v_days BETWEEN 15 AND 30 THEN
    v_fine := v_days * 5;
  ELSIF v_days > 30 THEN
    v_fine := v_days * 50;
  ELSE
    v_fine := 0;
  END IF;

  -- Update status from 'I' (Issued) to 'R' (Returned)
  UPDATE Borrower
  SET Status = 'R'
  WHERE Rollin = v_roll AND NameofBook = v_book;

  -- Insert fine details if applicable
  IF v_fine > 0 THEN
    INSERT INTO Fine VALUES (v_roll, SYSDATE, v_fine);
    DBMS_OUTPUT.PUT_LINE('Fine generated: Rs. ' || v_fine);
  ELSE
    DBMS_OUTPUT.PUT_LINE('No fine applicable.');
  END IF;

  COMMIT;
  DBMS_OUTPUT.PUT_LINE('Book returned successfully!');

EXCEPTION
  WHEN NO_DATA_FOUND THEN
    DBMS_OUTPUT.PUT_LINE('Error: Record not found or book already returned.');
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Unexpected error occurred: ' || SQLERRM);
END;
/
