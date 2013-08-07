DROP DATABASE IF EXISTS contrlsysempl
;
CREATE DATABASE contrlsysempl DEFAULT CHARACTER SET 'utf8';

USE contrlsysempl;

create table division (divId INT(4) AUTO_INCREMENT, nameDiv VARCHAR(50)  UNIQUE NOT NULL, PRIMARY KEY (divId));


create table empl (emplId INT(4) AUTO_INCREMENT, firstName VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, emplDiv INT(4) NOT NULL, salary DOUBLE, birthdate DATE, active BOOLEAN NOT NULL DEFAULT 1,  PRIMARY KEY (emplId), FOREIGN KEY (emplDiv) REFERENCES division (divId));

