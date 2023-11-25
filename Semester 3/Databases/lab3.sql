USE [Flower Shop]
GO
--a. modify the type of a column;--
CREATE OR ALTER PROCEDURE setPackagingCategoryToChar
AS
BEGIN
    alter table Packaging alter column category CHAR(30)
	PRINT 'ALTER TABLE Packaging FROM VARCHAR TO CHAR'
END
GO

CREATE OR ALTER PROCEDURE setPackagingCategoryToVARCHAR
AS
BEGIN
    alter table Packaging alter column category VARCHAR(30)
	PRINT 'ALTER TABLE Packaging FROM CHAR TO VARCHAR'
END
GO

--b. add / remove a column;--

CREATE OR ALTER PROCEDURE addCountryToClientPrivate
AS
BEGIN
    alter table ClientPrivate add country VARCHAR(30)
	PRINT 'ALTER TABLE ClientPrivate ADD COLUMN country to ClientPrivate'
END
GO

CREATE OR ALTER PROCEDURE removeCountryFromClientPrivate
AS
BEGIN
    alter table ClientPrivate drop column country
	PRINT 'ALTER TABLE ClientPrivate REMOVE COLUMN country to ClientPrivate'
END
GO

--c. add / remove a DEFAULT constraint;
CREATE OR ALTER PROCEDURE addDefaultToPriceFromPackaging 
AS
BEGIN
    alter table Packaging add constraint DEFAULT0 default(1.00) for price
	PRINT 'ALTER TABLE Packaging ADD CONSTRAINT DEFAULT 1.00 FOR price'
END
GO

CREATE OR ALTER PROCEDURE removeDefaultFromPriceFromPackaging
AS
BEGIN
    alter table Packaging drop constraint DEFAULT0
	PRINT 'ALTER TABLE Packaging REMOVE CONSTRAINT DEFAULT 1.00 FOR price'
END
GO

--g. create / drop a table.--
CREATE OR ALTER PROCEDURE addRequestedFlowers
AS
BEGIN
	CREATE TABLE RequestedFlowers(
		rfid INT NOT NULL,
		rfname VARCHAR(30),
		popularity INT,
		CONSTRAINT RFLOWERS_PRIMARY_KEY PRIMARY KEY(rfid)
	)
	PRINT 'CREATE TABLE RequestedFlowers'
END
GO

CREATE OR ALTER PROCEDURE dropRequestedFlowers
AS
BEGIN
    drop table RequestedFlowers
	PRINT 'DROP TABLE IF EXISTS RequestedFlowers'
END
GO

--d. add / remove a primary key;--
CREATE OR ALTER PROCEDURE addPrimaryKeyOrders
AS
BEGIN
    alter table Orders
        drop constraint PK_Orders
    alter table Orders
        add constraint PK_Orders primary key (fid,cid)
		PRINT 'ALTER TABLE Orders ADD CONSTRAINT PK_Orders from Orders'
END
GO

CREATE OR ALTER PROCEDURE removeOrdersPrimaryKey
AS
BEGIN
    alter table Orders
        drop constraint PK_Orders
    alter table Orders
        add constraint PK_Orders primary key (fid, cid, ocid, shid, pid)
		PRINT 'ALTER TABLE Orders REMOVE CONSTRAINT PK_Orders from Orders and return to initial constraint'
END
GO

--e. add / remove a candidate key;--
CREATE OR ALTER PROCEDURE newCandidateKeyRegulars 
AS
BEGIN
    alter table Regulars
        add constraint REGULARS_CANDIDATE_KEY_1 unique (cid,dateOfRegistration,points)
		PRINT 'ALTER TABLE Regulars ADD CONSTRAINT REGULARS_CANDIDATE_KEY_1(cid,dateOfRegistration,points)'
END
GO

CREATE OR ALTER PROCEDURE dropCandidateKeyRegulars
AS
BEGIN
    alter table Regulars
        drop constraint REGULARS_CANDIDATE_KEY_1
		PRINT 'ALTER TABLE Regulars REMOVE CONSTRAINT REGULARS_CANDIDATE_KEY_1(cid,dateOfRegistration,points)'
END
GO

--f. add / remove a foreign key;--
CREATE OR ALTER PROCEDURE newForeignKeyFlowerPlot
AS
BEGIN
    alter table FlowerPlot
		drop constraint fid_FK
	alter table FlowerPlot
		drop constraint gid_FK
	alter table FlowerPlot
        add constraint FLOWERPLOT_FOREIGN_KEY_Employee foreign key(eid) references Employees(eid)
		PRINT 'ALTER TABLE FlowerPlot ADD CONSTRAINT FlOWERPLOT_FOREIGN_KEY_1(eid)'
END
GO

CREATE OR ALTER PROCEDURE dropForeignKeyFlowerPlot
AS
BEGIN
    alter table FlowerPlot
		drop constraint FLOWERPLOT_FOREIGN_KEY_Employee
	alter table FlowerPlot
		add constraint fid_FK FOREIGN KEY (fid) REFERENCES Flowers(fid)
	alter table FlowerPlot
		add constraint gid_FK FOREIGN KEY (gid) REFERENCES Greenhouses(gid)
	PRINT 'ALTER TABLE FlowerPlot REMOVE CONSTRAINT FlOWERPLOT_FOREIGN_KEY_EMPLOYEE(eid) and remove to initial references'
END
GO


exec setPackagingCategoryToChar
exec setPackagingCategoryToVARCHAR
exec addCountryToClientPrivate
exec removeCountryFromClientPrivate
exec addDefaultToPriceFromPackaging 
exec removeDefaultFromPriceFromPackaging
exec addRequestedFlowers
exec dropRequestedFlowers
exec addPrimaryKeyOrders
exec removeOrdersPrimaryKey
exec newCandidateKeyRegulars 
exec dropCandidateKeyRegulars
exec newForeignKeyFlowerPlot
exec dropForeignKeyFlowerPlot

drop procedure setPackagingCategoryToChar
drop procedure setPackagingCategoryToVARCHAR
drop procedure addCountryToClientPrivate
drop procedure removeCountryFromClientPrivate
drop procedure addDefaultToPriceFromPackaging 
drop procedure removeDefaultFromPriceFromPackaging
drop procedure addRequestedFlowers
drop procedure dropRequestedFlowers
drop procedure addPrimaryKeyOrders
drop procedure removeOrdersPrimaryKey
drop procedure newCandidateKeyRegulars 
drop procedure dropCandidateKeyRegulars
drop procedure newForeignKeyFlowerPlot
drop procedure dropForeignKeyFlowerPlot
drop procedure goToVersion

----Create a table that holds the versions of the database schema (the version is an integer number)--
DROP TABLE IF EXISTS VersionsTable
CREATE TABLE VersionsTable (
	doProcedure VARCHAR(100),
	undoProcedure VARCHAR(100),
	versionToGoTo INT UNIQUE)
GO

INSERT INTO VersionsTable(doProcedure,undoProcedure,versionToGoTo) VALUES ('setPackagingCategoryToChar', 'setPackagingCategoryToVARCHAR', 1),
('addCountryToClientPrivate', 'removeCountryFromClientPrivate', 2),
('addDefaultToPriceFromPackaging','removeDefaultFromPriceFromPackaging', 3),
('addRequestedFlowers','dropRequestedFlowers', 4),
('addPrimaryKeyOrders', 'removeOrdersPrimaryKey', 5),
('newCandidateKeyRegulars', 'dropCandidateKeyRegulars', 6),
('newForeignKeyFlowerPlot', 'dropForeignKeyFlowerPlot', 7)

SELECT * FROM VersionsTable


-- create a table that keeps only the current version (the version is an integer number)

drop TABLE IF EXISTS CurrentVersionTable
CREATE TABLE CurrentVersionTable(
	currentVersion INT DEFAULT 0
)

INSERT INTO CurrentVersionTable VALUES(0)
GO

CREATE OR ALTER PROCEDURE goToVersion(@version INT)
AS
BEGIN
	DECLARE @currentVersion INT = 0
	DECLARE @currentProcedure NVARCHAR(100) = ''
	--IF @version < 0 OR @version > 7
	IF @version NOT BETWEEN 0 AND 7
		BEGIN
			RAISERROR('Version number must be between 0 and 7',17,1)
			RETURN
		END
	ELSE
	SET @currentVersion = (SELECT currentVersion
						   FROM CurrentVersionTable)
	IF @version = @currentVersion
		BEGIN
			PRINT 'This is the current version'
			RETURN
		END
	ELSE
	--SET @currentVersion = (SELECT currentVersion
	--					   FROM CurrentVersionTable)
	IF @currentVersion < @version
		BEGIN
			WHILE @currentVersion < @version
				BEGIN
					PRINT 'This is version ' + CAST(@currentVersion as VARCHAR(10))
					SET @currentProcedure = (SELECT doProcedure
											 FROM VersionsTable
											 WHERE versionToGoTo = @currentVersion + 1)
					EXEC @currentProcedure
					SET @currentVersion = @currentVersion + 1
				END
		END
	ELSE 
	IF @currentVersion > @version
		BEGIN
			WHILE @currentVersion > @version
				BEGIN
					PRINT 'This is version ' + CAST(@currentVersion as VARCHAR(10))

					SET @currentProcedure = (SELECT undoProcedure
											FROM VersionsTable
											WHERE versionToGoTo = @currentVersion)
					EXEC @currentProcedure
					SET @currentVersion = @currentVersion - 1
				END
		END
	UPDATE CurrentVersionTable
	SET currentVersion = @currentVersion
	PRINT 'Reached version: ' + CAST(@currentVersion as VARCHAR(10))
END
GO

Select * FROM VersionsTable
SELECT * FROM CurrentVersionTable
GO
EXEC goToVersion 1
EXEC goToVersion 2
EXEC goToVersion 3
EXEC goToVersion 4
EXEC goToVersion 5
EXEC goToVersion 6
EXEC goToVersion 7
EXEC goToVersion 0
EXEC goToVersion -1

SELECT * FROM ClientPrivate;

INSERT INTO Packaging (pid, category) VALUES (6, 'Flower Box');
INSERT INTO Packaging (pid, category) VALUES (7, 'Flower Box 2');
INSERT INTO Packaging (pid, category) VALUES (8, 'Flower Box 2');

SELECT * FROM Packaging;
DELETE FROM Packaging WHERE pid=6
drop TABLE RequestedFlowers;
SELECT * FROM RequestedFlowers;

SELECT * FROM FlowerPlot;