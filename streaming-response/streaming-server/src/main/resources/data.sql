DROP TABLE IF EXISTS EMPLOYEE;

CREATE TABLE EMPLOYEE(
    ID         INT AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME VARCHAR(250) NOT NULL,
    LAST_NAME  VARCHAR(250) NOT NULL,
    IS_ACTIVE   BIT NOT NULL
);

INSERT INTO EMPLOYEE (FIRST_NAME, LAST_NAME, IS_ACTIVE)
VALUES ('Lincoln', 'Burrows', 'true'),
       ('Michael', 'Scofield', 'true'),
       ('Theodore', 'T Bagwell', 'true'),
       ('Fernando', 'Sucre', 'true'),
       ('Alex', 'Mahone', 'true'),
       ('Brad', 'Bellick', 'true');