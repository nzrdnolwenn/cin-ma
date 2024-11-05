DROP DATABASE IF EXISTS movietheater;
CREATE DATABASE movietheater;
USE movietheater;

CREATE TABLE Customer (
	name VARCHAR(255),
	birthDate DATE,
	PRIMARY KEY (name)
);

CREATE TABLE Movie (
	title VARCHAR(255),
	duration INT,
	isForbiddenUnder18 BOOLEAN,
	PRIMARY KEY (title)
);

CREATE TABLE Room (
	numberRoom INT,
	seatCount INT,
	PRIMARY KEY (numberRoom)
);

CREATE TABLE Session (
	id INT AUTO_INCREMENT,
	startHourly DATETIME,
	movieTitle VARCHAR(255),
	numberRoom INT,
	isVo BOOLEAN,
	isSt BOOLEAN,
	PRIMARY KEY (id),
	FOREIGN KEY (movieTitle) REFERENCES Movie(title) ON DELETE CASCADE,
	FOREIGN KEY (numberRoom) REFERENCES Room(numberRoom) ON DELETE CASCADE
);

CREATE TABLE Reservation (
	id INT AUTO_INCREMENT,
	customerName VARCHAR(255),
	sessionId INT,
	PRIMARY KEY (id),
	FOREIGN KEY (customerName) REFERENCES Customer(name) ON DELETE CASCADE,
	FOREIGN KEY (sessionId) REFERENCES Session(id) ON DELETE CASCADE
);


INSERT INTO Room (numberRoom, seatCount) VALUES (1, 50);
INSERT INTO Room (numberRoom, seatCount) VALUES (2, 30);
INSERT INTO Room (numberRoom, seatCount) VALUES (3, 100);

INSERT INTO Movie (title, duration, isForbiddenUnder18) VALUES ('Shrek', 89, false);
INSERT INTO Movie (title, duration, isForbiddenUnder18) VALUES ('Inception', 148, false);
INSERT INTO Movie (title, duration, isForbiddenUnder18) VALUES ('Avatar', 161, false);
INSERT INTO Movie (title, duration, isForbiddenUnder18) VALUES ('Vaiana', 113, false);
INSERT INTO Movie (title, duration, isForbiddenUnder18) VALUES ('Le parrain', 175, true);
INSERT INTO Movie (title, duration, isForbiddenUnder18) VALUES ('La reine des neiges', 102, false);


