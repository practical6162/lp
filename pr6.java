CREATE TABLE O_RollCall (
 Roll_No NUMBER PRIMARY KEY,
 Name VARCHAR2(30)
);
CREATE TABLE N_RollCall (
 Roll_No NUMBER PRIMARY KEY,
 Name VARCHAR2(30)
);
-- Old RollCall data (existing)
INSERT INTO O_RollCall VALUES (1, 'Gayatri');
INSERT INTO O_RollCall VALUES (2, 'Amit');
INSERT INTO O_RollCall VALUES (3, 'Riya');
-- New RollCall data (new entries)
INSERT INTO N_RollCall VALUES (2, 'Amit');
INSERT INTO N_RollCall VALUES (3, 'Riya');
INSERT INTO N_RollCall VALUES (4, 'Neha');
INSERT INTO N_RollCall VALUES (5, 'Rakesh');
COMMIT;
SQL> edit merge_rollcall.sql
SET SERVEROUTPUT ON;
DECLARE
 -- Parameterized cursor: accepts Roll_No as input
 CURSOR c_new(p_roll N_RollCall.Roll_No%TYPE) IS
 SELECT Roll_No, Name
 FROM N_RollCall
 WHERE Roll_No = p_roll;
 v_roll N_RollCall.Roll_No%TYPE;
 v_name N_RollCall.Name%TYPE;
 v_count NUMBER;
BEGIN
 -- Outer loop over all records in N_RollCall
 FOR rec IN (SELECT * FROM N_RollCall) LOOP
 -- Check if Roll_No already exists in O_RollCall
 SELECT COUNT(*) INTO v_count
 FROM O_RollCall
 WHERE Roll_No = rec.Roll_No;
 -- If not found, fetch data using parameterized cursor and insert
 IF v_count = 0 THEN
 OPEN c_new(rec.Roll_No);
 FETCH c_new INTO v_roll, v_name;
 INSERT INTO O_RollCall VALUES (v_roll, v_name);
 CLOSE c_new;
 DBMS_OUTPUT.PUT_LINE('Inserted Roll_No: ' || v_roll || ' Name: ' || v_name);
 ELSE
 DBMS_OUTPUT.PUT_LINE('Skipped Roll_No: ' || rec.Roll_No || ' (already exists)');
 END IF;
 END LOOP;
 DBMS_OUTPUT.PUT_LINE('--- Merge Complete ---');
EXCEPTION
 WHEN OTHERS THEN
 DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
END;
/
SQL> @merge_rollcall.sql
SQL> SELECT * FROM O_RollCall;
