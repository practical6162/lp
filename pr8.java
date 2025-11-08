CREATE TABLE areas (
 radius NUMBER(5,2),
 area NUMBER(10,2)
);
-------------------------------------------------------------------------
SQL> edit area_circle.sql
SET SERVEROUTPUT ON;
DECLARE
 v_radius NUMBER(5,2);
 v_area NUMBER(10,2);
 PI CONSTANT NUMBER := 3.14159;
BEGIN
 -- Loop radius values from 5 to 9
 FOR v_radius IN 5..9 LOOP
 v_area := PI * v_radius * v_radius;
 -- Insert into table
 INSERT INTO areas (radius, area)
 VALUES (v_radius, v_area);
 DBMS_OUTPUT.PUT_LINE('Radius: ' || v_radius || ' => Area: ' || v_area);
 END LOOP;
 DBMS_OUTPUT.PUT_LINE('--- Areas inserted successfully ---');
END;
/
------------------------------------------------------------------------------------------------
SQL> @area_circle.sql
--------------------------------------------------------------------------------------------------
SELECT * FROM areas;
