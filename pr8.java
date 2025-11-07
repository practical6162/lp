CREATE DATABASE circleDB;
USE circleDB;

CREATE TABLE Circle_Area (
    Radius INT,
    Area DOUBLE
);
DELIMITER $$

CREATE PROCEDURE calc_circle_area()
BEGIN
    DECLARE v_radius INT DEFAULT 5;
    DECLARE v_area DOUBLE;
    DECLARE pi DOUBLE DEFAULT 3.14159;

    -- Loop from radius 5 to 9
    WHILE v_radius <= 9 DO
        SET v_area = pi * v_radius * v_radius;

        -- Insert radius and area into table
        INSERT INTO Circle_Area VALUES (v_radius, v_area);

        -- Show output
        SELECT CONCAT('Radius: ', v_radius, ' | Area: ', ROUND(v_area,2)) AS Result;

        SET v_radius = v_radius + 1;
    END WHILE;
END$$

DELIMITER ;

-call
CALL calc_circle_area();

