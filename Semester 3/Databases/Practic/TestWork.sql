use [Test]

drop table ClientBankingServices;
drop table ClientInvestingServices;
drop table ClientBank;
drop table Clients;
drop table Banks;
drop table BankingServices;
drop table InvestingServices;

select * from ClientBankingServices;
select * from ClientInvestingServices;
select * from ClientBank;
select * from Clients;
select * from Banks;


CREATE TABLE Clients(
	cid INT not null,
	PRIMARY KEY(cid),
	cname VARCHAR(50) not null
);

CREATE TABLE Banks(
	bid INT not null UNIQUE,
	PRIMARY KEY(bid)
);

CREATE TABLE ClientBank(
	bid INT references Banks(bid),
	cid INT references Clients(cid),
	PRIMARY KEY(bid,cid)
);

CREATE TABLE BankingServices(
	bsid INT not null,
	bid INT references Banks(bid),
	PRIMARY KEY(bsid)
);

CREATE TABLE ClientBankingServices(
	cid int,
	bsid int,
	PRIMARY KEY(bsid,cid),
	FOREIGN KEY(cid) references Clients(cid),
	FOREIGN KEY(bsid) references BankingServices(bsid)
);

CREATE TABLE InvestingServices(
	isid INT not null,
	bid INT references Banks(bid),
	PRIMARY KEY(isid)
);

CREATE TABLE ClientInvestingServices(
	isid INT,
	cname varchar(50),
	cid int,
	PRIMARY KEY(isid,cname,cid),
	FOREIGN KEY(isid) references InvestingServices(isid)
);


INSERT INTO Clients(cid,cname) VALUES (1,'Ana');
INSERT INTO Clients(cid,cname) VALUES (2, 'Ana');
INSERT INTO Clients(cid,cname) VALUES (3,'Kiki');

INSERT INTO Banks(bid) VALUES (1);
INSERT INTO Banks(bid) VALUES (2);

INSERT INTO ClientBank(cid,bid) VALUES (1,1);
INSERT INTO ClientBank(cid,bid) VALUES (2,2);
INSERT INTO ClientBank(cid,bid) VALUES (1,2);
INSERT INTO ClientBank(cid,bid) VALUES (2,1);

INSERT INTO BankingServices(bsid,bid) VALUES (1,1);
INSERT INTO BankingServices(bsid,bid) VALUES (2,2);

INSERT INTO InvestingServices(isid,bid) VALUES (1,2);
INSERT INTO InvestingServices(isid,bid) VALUES (2,2);

GO

CREATE OR ALTER PROCEDURE AddClientServices (@bid int,@cid int,@cname varchar(50),@bankingid int,@investingid int)
AS
BEGIN
	IF NOT EXISTS (SELECT * FROM Banks AS B WHERE B.bid = @bid) AND NOT EXISTS (SELECT * FROM Clients AS C WHERE C.cid = @cid) BEGIN
		PRINT('No such bank/client')
		return 1
		END

	IF @bankingid IS NOT NULL BEGIN
		IF NOT EXISTS (SELECT * FROM BankingServices AS B WHERE B.bsid = @bankingid) BEGIN
		print('No such banking service')
		return 1
		END

		IF EXISTS (SELECT * FROM ClientBankingServices AS B WHERE B.bsid = @bankingid AND B.cid = @cid) BEGIN
			PRINT('The client already chose this service')
			return 1
		END

		INSERT INTO ClientBankingServices(bsid,cid) VALUES(@bankingid,@cid);
	END

	IF @investingid IS NOT NULL BEGIN
		IF NOT EXISTS (SELECT * FROM InvestingServices AS I WHERE I.isid = @investingid) BEGIN
		print('No such investing service')
		return 1
		END

		IF EXISTS (SELECT * FROM ClientInvestingServices AS C WHERE C.isid =@investingid AND C.cname = @cname AND C.cid = @cid) BEGIN
			print('Client already chose this service')
			return 1
		END

		INSERT INTO ClientInvestingServices(cid,cname,isid) VALUES (@cid, @cname,@investingid);
	END
END

-- Example use

EXEC AddClientServices 1,2,'Ana',1,2
EXEC AddClientServices 1,1,'Ana',1,1

GO

CREATE OR ALTER VIEW pointb AS
SELECT C.cid AS ClientId, C.cname AS ClientName,
row_number() OVER (ORDER BY B.cid) AS BankingServiceRank,
row_number() OVER (ORDER BY I.cid) AS InvestingServiceRank
FROM Clients AS C
INNER JOIN ClientBankingServices AS B ON C.cid = B.cid
INNER JOIN ClientInvestingServices AS I ON B.cid = I.cid

GO
select * from pointb;

GO
CREATE OR ALTER PROCEDURE ClientServices
AS
BEGIN
	DECLARE @cname varchar(50);
	declare @bid int;
	declare @servicelist varchar(300);

	DECLARE clientCursor CURSOR FOR 
	SELECT DISTINCT C.cname,CB.bid
	FROM Clients AS C
	INNER JOIN ClientBank AS CB ON C.cid = CB.cid;

	OPEN clientCursor;

	FETCH NEXT FROM clientCursor INTO @cname,@bid;

	WHILE @@FETCH_STATUS = 0
	BEGIN
	SET @servicelist ='';
	SELECT @servicelist=@servicelist+'Bank ID' + CAST(BS.bsid AS VARCHAR)+', '
	FROM ClientBankingServices AS BS
	WHERE BS.cid IN (SELECT cid FROM ClientBank WHERE bid=@bid);

	IF(LEN(@servicelist)>0)
	BEGIN
		PRINT 'Client name: ' + @cname + ', ' + 'Bank id: ' + CAST(@bid AS VARCHAR) + ', ' + 'Services: ' + @servicelist;
	END
	FETCH NEXT FROM clientCursor INTO @cname,@bid;
	END
	CLOSE clientCursor;
	deallocate clientCursor;
END
GO

CREATE OR ALTER PROCEDURE ListClientsWithMultipleBanksAndServices
AS
BEGIN
    SELECT 
        C.cname AS ClientName,
        COUNT(DISTINCT CB.bid) AS NumberOfBanks,
        STRING_AGG('Bank ID: ' + CAST(CB.bid AS VARCHAR) + ' - Banking Service ID: ' + CAST(CBS.bsid AS VARCHAR) + ' - Investing ID: ' + CAST(SI.isid AS VARCHAR), ', ') AS ServicesUsed
    FROM 
        Clients AS C
    INNER JOIN 
        ClientBank AS CB ON C.cid = CB.cid
	INNER JOIN
		BankingServices AS BS ON BS.bid = CB.bid
    INNER JOIN 
        ClientBankingServices AS CBS ON BS.bsid = CBS.bsid AND C.cid = CBS.cid
	INNER JOIN
		InvestingServices AS SI ON SI.bid = CB.bid
	INNER JOIN
		ClientInvestingServices AS CIS ON CIS.isid = SI.isid
    GROUP BY 
        C.cname
    HAVING 
        COUNT(DISTINCT CB.bid) > 1;
END

exec ClientServices
exec ListClientsWithMultipleBanksAndServices

SELECT STRING_AGG('Bank ID: ' + CAST(c.cid AS VARCHAR) +' - Name ID: ' + CAST(c.cname AS VARCHAR), ', ') AS ServicesUsed FROM Clients AS C
