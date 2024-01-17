use [Flower Shop]

DROP TABLE Orders;
DROP TABLE FlowerPlot;
DROP TABLE Flowers;
DROP TABLE FlowerData;
DROP TABLE ClientPrivate;
DROP TABLE Regulars;
DROP TABLE ClientPublic;
DROP TABLE Employees;
DROP TABLE Greenhouses;
DROP TABLE Packaging;
DROP TABLE Occasions;
DROP TABLE Shipping;

CREATE TABLE Greenhouses(
	gid INT NOT NULL,
	PRIMARY KEY(gid),
	adress VARCHAR(100),
	city VARCHAR(30),
	efficiency FLOAT
);

CREATE TABLE FlowerData(
	ftid INT NOT NULL,
	PRIMARY KEY(ftid),
	colour VARCHAR(30) NOT NULL,
	witherDate DATE NOT NULL,
	quantity INT DEFAULT 0,
	perfume VARCHAR(30),
	symbollism VARCHAR(50),
	CONSTRAINT quantity_check CHECK (quantity >= 0)
);

CREATE TABLE Flowers(
	fid INT NOT NULL,
	PRIMARY KEY(fid),
	ftid INT REFERENCES FlowerData ON DELETE CASCADE,
	flowerName VARCHAR(30) NOT NULL
);

/*Employees is in many to 1 relation with the Greenhouses*/
CREATE TABLE Employees(
	eid INT NOT NULL,
	PRIMARY KEY(eid),
	gid INT REFERENCES Greenhouses(gid),
	firstName VARCHAR(30) NOT NULL,
	lastName VARCHAR(30) NOT NULL,
	salary INT DEFAULT 2000,
	CONSTRAINT salary_check CHECK (salary >= 1898)
);

/*A greenhouse can grow multiple flowers, and a flower can be grown in multiple, many to many */
CREATE TABLE FlowerPlot(
	--gid INT FOREIGN KEY REFERENCES Greenhouses(gid) ON DELETE CASCADE ON UPDATE NO ACTION,
	--fid INT FOREIGN KEY REFERENCES Flowers(fid) ON DELETE CASCADE ON UPDATE NO ACTION,
	gid INT,
	fid INT,
	eid INT,
	CONSTRAINT gid_FK FOREIGN KEY (gid) REFERENCES Greenhouses(gid) ON DELETE CASCADE ON UPDATE NO ACTION,
	CONSTRAINT fid_FK FOREIGN KEY (fid) REFERENCES Flowers(fid) ON DELETE CASCADE ON UPDATE NO ACTION,
	PRIMARY KEY(gid, fid),
	growthRate INT DEFAULT 0,
	season VARCHAR(30),
	CONSTRAINT season_check CHECK (season IN ('Autumn', 'Winter', 'Spring', 'Summer'))
);

/*Client to orders is one to many, a client can have multiple orders*/
CREATE TABLE ClientPublic(
	cid INT NOT NULL,
	PRIMARY KEY(cid),
	firstName VARCHAR(30) NOT NULL,
	lastName VARCHAR(30) NOT NULL,
	payment VARCHAR(30) NOT NULL,
	email VARCHAR(50) NOT NULL
);

CREATE TABLE ClientPrivate(
	cid INT FOREIGN KEY REFERENCES ClientPublic(cid) ON DELETE CASCADE ON UPDATE NO ACTION,
	CNP VARCHAR(10) NOT NULL,
	VX VARCHAR(10) NOT NULL,
	series VARCHAR(10) NOT NULL,
	adress VARCHAR(100) NOT NULL,
	city VARCHAR(30) NOT NULL,
	dateOfBirth DATE,
	customerType VARCHAR(30) DEFAULT 'NATURAL',
	CONSTRAINT person_data CHECK ((LEFT(CNP,1) IN ('1','2','5','6')) AND LEN(CNP) = 10)
);

CREATE TABLE Regulars(
	cid INT FOREIGN KEY REFERENCES clientPublic(cid) ON DELETE CASCADE ON UPDATE NO ACTION,
	dateOfRegistration DATE NOT NULL,
	points INT default 0,
	CONSTRAINT points_check CHECK (points >= 0)
);

/*Occassion to order is 1 to many, an occassion can have multiple orders*/
CREATE TABLE Occasions(
	ocid INT NOT NULL,
	CONSTRAINT PK_Occasion PRIMARY KEY (ocid),
	occasionName VARCHAR(30),
	category VARCHAR(30)
);

CREATE TABLE Packaging(
	pid INT NOT NULL,
	PRIMARY KEY(pid),
	price FLOAT,
	category VARCHAR(30),
	CONSTRAINT price_check CHECK (price > 0)
);

CREATE TABLE Shipping(
	shid INT NOT NULL,
	PRIMARY KEY(shid),
	price FLOAT,
	category VARCHAR(30),
	expressDelivery VARCHAR(30) DEFAULT 'NO',
	CONSTRAINT price_check_ship CHECK (price > 0)
);

/*flower to orders are many to many, a flower can be in multiple orders, and orders have multiple flowers*/
CREATE TABLE Orders(
	fid INT REFERENCES Flowers(fid) ON DELETE CASCADE,
	cid INT REFERENCES ClientPublic(cid) ON DELETE CASCADE,
	ocid INT REFERENCES Occasions(ocid) ON DELETE CASCADE,
	shid INT REFERENCES Shipping(shid) ON DELETE CASCADE,
	pid INT REFERENCES Packaging(pid) ON DELETE CASCADE,
	CONSTRAINT PK_Orders PRIMARY KEY (fid, cid, ocid, shid, pid),
	orderName VARCHAR(50),
	orderPlacementDate DATE,
	price FLOAT DEFAULT 0,
	CONSTRAINT price_check_order CHECK ((price > 0) AND (orderPlacementDate > '2023/01/01'))
);
