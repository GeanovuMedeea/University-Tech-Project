use [Test]

drop table Cities;
drop table Stadiums;
drop table NationalTeams;
drop table Players;
drop table Coaches;
drop table Games;

CREATE TABLE Cities(
	cname varchar(50) UNIQUE,
	PRIMARY KEY(cname)
);

CREATE TABLE Stadiums(
	stid int,
	PRIMARY KEY(stid),
	sname varchar(50),
	cname varchar(50),
	FOREIGN KEY(cname) references Cities(cname),
);

CREATE TABLE Countries(
	cname varchar(50),
	PRIMARY KEY(cname)
);

CREATE TABLE NationalTeams(
	tid int,
	PRIMARY KEY(tid),
	cname varchar(50),
	FOREIGN KEY(cname) references Countries(cname)
);

CREATE TABLE Players(
	pid int,
	PRIMARY KEY(pid),
	pname varchar(50),
	bdate DATE,
	nationality varchar(50),
	position varchar(50),
	captain bit default 0,
	tid int,
	FOREIGN KEY(tid) references NationalTeams(tid)
);

CREATE TABLE Coaches(
	cid int,
	PRIMARY KEY(cid),
	cname varchar(50),
	nationality varchar(50),
	tid int,
	FOREIGN KEY(tid) references NationalTeams(tid)
);

CREATE TABLE Games(
	gid int,
	PRIMARY KEY(gid),
	gdate date,
	tid1 int,
	tid2 int,
	stid int,
	score int,
	winner int,
	overtime bit default 0,
	FOREIGN KEY(tid1) references NationalTeams(tid),
	FOREIGN KEY (tid2) references NationalTeams(tid),
	FOREIGN KEY(stid) references Stadiums(stid),
	FOREIGN KEY(winner) references NationalTeams(tid)
);

-- Inserting data into Cities table
INSERT INTO Cities (cname) VALUES ('City1');

-- Inserting data into Stadiums table
INSERT INTO Stadiums (stid, sname, cname) VALUES (1, 'Stadium1', 'City1');

-- Inserting data into Countries table (assuming that Countries is the correct table for NationalTeams)
INSERT INTO Countries (cname) VALUES ('Country1');

-- Inserting data into NationalTeams table
INSERT INTO NationalTeams (tid, cname) VALUES (1, 'Country1');
INSERT INTO NationalTeams (tid, cname) VALUES (2, 'Country1');

-- Inserting data into Players table
INSERT INTO Players (pid, pname, bdate, nationality, position, captain, tid) VALUES (1, 'Player1', '1990-01-01', 'Nationality1', 'Position1', 0, 1);
INSERT INTO Players (pid, pname, bdate, nationality, position, captain, tid) VALUES (2, 'Player2', '1990-01-01', 'Nationality1', 'Position1', 1, 2);

-- Inserting data into Coaches table
INSERT INTO Coaches (cid, cname, nationality, tid) VALUES (1, 'Coach1', 'Nationality1', 1);
INSERT INTO Coaches (cid, cname, nationality, tid) VALUES (2, 'Coach1', 'Nationality1', 2);

-- Inserting data into Games table
INSERT INTO Games (gid, gdate, tid1, tid2, stid, score, winner, overtime) VALUES (1, '2023-01-01', 1, 2, 1, 20, 1, 0);

GO
CREATE OR ALTER PROCEDURE StoreGame(@gid int, @gdate date,
@tid1 int, @tid2 int, @stid int, @score int, @winner int, @overtime bit)
AS
BEGIN
	IF EXISTS (SELECT * FROM Games AS G WHERE @tid1 =G.tid1 AND @tid2 = G.tid2 AND @gdate = G.gdate) BEGIN
		UPDATE Games
		SET score=@score
		WHERE Games.tid1=@tid1 AND Games.tid2 =@tid2
	END
	ELSE BEGIN
		INSERT INTO Games(gid,gdate,tid1,tid2,stid,score,winner,overtime) VALUES (@gid,@gdate,@tid1,@tid2,@stid,@score,@winner,@overtime)
	END
END
GO

select * from Games;
StoreGame 3, '2023-05-01', 1, 2, 1, 123, 1, 1

GO

CREATE OR ALTER VIEW PointB AS
SELECT sname AS StadiumName FROM
(SELECT sname FROM Stadiums AS S
INNER JOIN
Games AS G ON S.stid = G.stid AND G.overtime = 1) AS R
GO
select * from PointB

GO
CREATE OR ALTER FUNCTION NumberTeams(@S INT, @R INT)
RETURNS INT
AS
BEGIN
    DECLARE @number INT;
    SET @number = (
		SELECT COUNT(N.tid)
        FROM NationalTeams AS N
        INNER JOIN (
            SELECT G.winner
            FROM Games AS G
            WHERE G.score > @R
        ) AS G2 ON N.tid = G2.winner
    );
    RETURN @number;
END
GO

declare @temp int;
set @temp=dbo.NumberTeams(1,10);
select @temp AS Result;

GO
CREATE FUNCTION TableTeams()
RETURNS TABLE
AS
BEGIN
END
GO



CREATE TABLE R(
	FK1 int,
	FK2 int,
	PRIMARY KEY(FK1,FK2),
	C1 varchar(50),
	C2 varchar(50),
	C3 int,
	C4 float,
	C5 float
);

drop table R;

INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES (9,6,'Pride and Prejudice','Jane Austen',123,43.99,10);
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES (8,8,'The Beautiful and Damned','F.Scott Firzgerald',100,52.99,9.8);
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES (7,9,'The Wind-Up Bird Chronicle','Haruki Murakami',99,39.99,8.4);
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES (12,18,'Emma','Jane Austen',101,40.99,8);
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES (6,5,'Love in the Time of Cholera','Gabriel Garcia Marquez',55,53.99,9.7);
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES (2,4,'Death on the Nile','Agatha Cristie',80,49.99,9.5);
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES (5,3,'One Hundred Years of Solitude','Gabriel Garcia Marquez',100,50.99,9.4);
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES (3,7,'Sense and Sensibility','Jane Austen',85,43.99,8.4);
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES (2,9,'The Great Gatsby','F. Scott Fitzgerald',95,32.99,8.9);
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES (1,8,'Kafka on the Shore','Haruki Murakami',123,42.99,7.9);
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES (6,4,'Murder on the Orient Express','Agatha Cristie',89,29.99,8.2);
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES (5,2,'The Autumn of the Patriach','Gabriel Garcia Marquez',120,45.99,8.9);

SELECT * FROM
(SELECT FK1,FK2,C4+C3 C4sumC3
FROM R
WHERE FK1<FK2) r1
INNER JOIN
(SELECT FK1,FK2,C5
FROM R
WHERE C5>8.4 AND C5<9.8) r2
ON r1.FK1 = r2.FK1 AND r1.FK2=r2.FK2
GO

CREATE OR ALTER TRIGGER TrOnUpdate
ON R
FOR UPDATE
AS
DECLARE @total INT=0
SELECT @total=sum(i.C3+d.C3)
FROM deleted d
INNER JOIN inserted i
ON d.FK1=i.FK1 AND d.FK2=i.FK2
WHERE d.C3<I.C3
PRINT @total

UPDATE R
SET C3=C3+10
WHERE FK1>=FK2