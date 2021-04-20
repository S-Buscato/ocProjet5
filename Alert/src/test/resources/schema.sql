DROP TABLE IF EXISTS medicalrecords;
DROP TABLE IF EXISTS firestations;
DROP TABLE IF EXISTS persons;

CREATE TABLE persons
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name  VARCHAR(250) NOT NULL,
    address   VARCHAR(250) NOT NULL,
    city      VARCHAR(250) NOT NULL,
    zip       VARCHAR(250) NOT NULL,
    phone     VARCHAR(250) NOT NULL,
    email     VARCHAR(250) DEFAULT NULL
);

CREATE TABLE firestations
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(250) NOT NULL,
    station VARCHAR(250) NOT NULL
);

CREATE TABLE medicalrecords
(
    id      INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    birthdate VARCHAR(250) NOT NULL,
    medications RESULT_SET,
    allergies RESULT_SET
);


