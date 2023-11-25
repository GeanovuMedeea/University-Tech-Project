use [Flower Shop Lab2]


CREATE TABLE Greenhouses(
	gid INT NOT NULL,
	PRIMARY KEY(gid),
	adress VARCHAR(100) CONSTRAINT adress_unique UNIQUE (adress),
	city VARCHAR(30),
	efficiency FLOAT
);

CREATE TABLE Flowers(
	fid INT NOT NULL,
	gid INT FOREIGN KEY REFERENCES Greenhouses(gid),
	PRIMARY KEY(fid),
	flowerName VARCHAR(30) NOT NULL
);

CREATE TABLE FlowerData(
	fid INT FOREIGN KEY REFERENCES Flowers(fid),
	colour VARCHAR(30) NOT NULL,
	witherDate DATE NOT NULL,
	quantity INT DEFAULT 0,
	perfume VARCHAR(30),
	symbollism VARCHAR(50)
);


CREATE TABLE ClientPublic(
	cid INT NOT NULL,
	PRIMARY KEY(cid),
	firstName VARCHAR(30) NOT NULL,
	lastName VARCHAR(30) NOT NULL,
	payment VARCHAR(30) NOT NULL,
	email VARCHAR(50) CONSTRAINT email_unique UNIQUE (email)
);

CREATE TABLE ClientPrivate(
	cid INT FOREIGN KEY REFERENCES ClientPublic(cid),
	CNP VARCHAR(10) NOT NULL CONSTRAINT cnp_unique UNIQUE (CNP),
	VX VARCHAR(10) NOT NULL,
	series VARCHAR(10) NOT NULL,
	adress VARCHAR(100) NOT NULL,
	city VARCHAR(30) NOT NULL,
	dateOfBirth DATE,
	customerType VARCHAR(30) DEFAULT 'NATURAL'
);

CREATE TABLE Regulars(
	cid INT FOREIGN KEY REFERENCES clientPublic(cid),
	dateOfRegistration DATE NOT NULL,
	points INT default 0
);

CREATE TABLE Occassion(
	/*oid INT FOREIGN KEY REFERENCES Orders(oid),*/
	ocid INT NOT NULL,
	PRIMARY KEY(ocid),
	occasionName VARCHAR(30),
	category VARCHAR(30)
);

CREATE TABLE Packaging(
	/*oid INT FOREIGN KEY REFERENCES Orders(oid),*/
	pid INT NOT NULL,
	PRIMARY KEY(pid),
	price FLOAT,
	category VARCHAR(30)
);

CREATE TABLE Shipping(
	/*oid INT FOREIGN KEY REFERENCES Orders(oid),*/
	shid INT NOT NULL,
	PRIMARY KEY(shid),
	price FLOAT,
	category VARCHAR(30),
	expressDelivery VARCHAR(30) DEFAULT 'NO'
);

CREATE TABLE Orders(
	fid INT FOREIGN KEY REFERENCES Flowers(fid),
	cid INT FOREIGN KEY REFERENCES ClientPublic(cid),
	ocid INT FOREIGN KEY REFERENCES Occassion(ocid),
	shid INT FOREIGN KEY REFERENCES Shipping(shid),
	pid INT FOREIGN KEY REFERENCES Packaging(pid),
	PRIMARY KEY(fid, cid, ocid, shid, pid),
	orderName VARCHAR(50),
	orderPlacementDate DATE,
	price FLOAT DEFAULT 0
);

CREATE TABLE Employees(
	gid INT FOREIGN KEY REFERENCES Greenhouses(gid),
	firstName VARCHAR(30) NOT NULL,
	lastName VARCHAR(30) NOT NULL,
	salary INT DEFAULT 2000,
);