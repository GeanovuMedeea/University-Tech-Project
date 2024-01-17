drop table Clients;
drop table Company;
drop table Department;
drop table Employees;

CREATE TABLE Clients(
	cid int,
	primary key(cid),
	project varchar(50)
);

CREATE TABLE Company(
	coid int,
	PRIMARY KEY(coid)
);

CREATE TABLE ClientCompanies(
	cid int,
	coid int,
	FOREIGN KEY(cid) references Clients(cid),
	FOREIGN KEY(coid) references Company(coid)
);

CREATE TABLE Department(
	coid int,
	FOREIGN KEY(coid) references Company(coid),
	did int,
	PRIMARY KEY(did),
	dtype varchar(50),
	dname varchar(50)
);

CREATE TABLE Employees(
	eid int,
	PRIMARY KEY(eid),
	ename varchar(50),
	title varchar(50),
	did int,
	FOREIGN KEY(did) references Department(did)
);

GO

-- Your table creation statements

CREATE OR ALTER VIEW ClientsDepartments AS
SELECT cid, dname
FROM (
    SELECT C.coid, D.dname
    FROM ClientsCompanies AS C
    INNER JOIN Department AS D ON C.coid = D.coid
) AS CD;

GO