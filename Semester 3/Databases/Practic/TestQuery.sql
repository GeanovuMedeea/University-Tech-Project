use [PracticLab]

CREATE TABLE R(
	FK1 int,
	FK2 int,
	C1 varchar(50),
	C2 varchar(50),
	C3 int,
	C4 float,
	C5 varchar(10)
);

DROP TABLE R;
select * from R;

INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES(1,3,'Chicken Breast','Caesar Salad',10,150.5,'R123');
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES(2,5,'Tomato','Caprese Salad',12,175.25,'R454');
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES(3,3,'Salmon','Honey Mustard Salmon',29,200.75,'R183');
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES(4,1,'Avocado','Shrimp Wraps',18,150.25,'R194');
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES(7,2,'Spinach','Spinach Risotto',32,125.75,'R104');
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES(16,12,'Quinoa','Burrito Bowl',15,250.25,'R193');
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES(5,13,'Avocado','Guacamole',19,175.25,'R185');
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES(2,2,'Tomato','Bruschetta',13,100.5,'R182');
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES(6,1,'Chicken Breast','Alfredo Pasta',25,250,'R190');
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES(9,6,'Salmon','Teriyaki Salmon',16,220.25,'R100');
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES(8,4,'Quinoa','Quinoa Salad',30,170.75,'R97');
INSERT INTO R(FK1,FK2,C1,C2,C3,C4,C5) VALUES(5,8,'Spinach','Spinach and Feta',14,100.25,'R341');

SELECT C1,AVG(C3) AvgC3,SUM(C4) SumC4
FROM R WHERE C4>145 AND C5 LIKE 'R1%'
GROUP BY C1
HAVING AVG(C3)>15 AND SUM(C4)<490

select * from
(select FK1,FK2,C3+C4 C3C4
FROM R
WHERE FK1>=FK2) r1
FULL JOIN
(SELECT FK1,FK2,C4
FROM R
WHERE C4>=180) r2
ON r1.FK1=r2.FK1 AND r1.FK2=r1.FK2

CREATE OR ALTER TRIGGER TrOnUpdate
ON R
FOR UPDATE
AS
DECLARE @total INT=0
SELECT @total =i.C3+5+d.C3+5
FROM deleted d
INNER JOIN
inserted i
ON d.FK1=i.FK1 AND d.FK2=i.FK2
WHERE d.C3 <= i.C3
PRINT @total

UPDATE R
SET C3=28
WHERE FK1<=FK2

drop table Dentists;
drop table Patients;
drop table Services;
drop table TreatmentPlans;
drop table Appointments;
drop table PatientAppointments;
drop table PatientAppointments;
drop table TreatmentAppointmentDentist;
DROP TABLE DentistTreatmentPlans;


CREATE TABLE Dentists(
	did int,
	PRIMARY KEY(did),
	dname varchar(50) UNIQUE,
	phone varchar(20),
	email varchar(40)
);

CREATE TABLE Patients(
	pid int,
	PRIMARY KEY(pid),
	pname varchar(50) UNIQUE,
	phone varchar(50),
	email varchar(50),
	paddress varchar(50)
);
CREATE TABLE Services(
	seid int,
	PRIMARY KEY(seid),
	sname varchar(50) UNIQUE,
	fee float
);
CREATE TABLE TreatmentPlans(
	tid int,
	PRIMARY KEY(tid),
	tname varchar(50),
	duration int,
	fee float,
	seid int,
	FOREIGN KEY(seid) references Services(seid)
);

CREATE TABLE Appointments(
	aid int,
	PRIMARY KEY(aid),
	atime time,
	adate date,
	details varchar(50)
);

CREATE TABLE TreatmentAppointmentDentist(
	did int,
	aid int,
	tid int,
	FOREIGN KEY(did) references Dentists(did),
	FOREIGN KEY(aid) references Appointments(aid),
	FOREIGN KEY(tid) references TreatmentPlans(tid),
);

CREATE TABLE PatientAppointments(
	pid int,
	aid int,
	FOREIGN KEY(pid) references Patients(pid),
	FOREIGN KEY(aid) references Appointments(aid)
);

CREATE TABLE DentistTreatmentPlans(
	did int,
	tid int,
	FOREIGN KEY(did) references Dentists(did),
	FOREIGN KEY(tid) references TreatmentPlans(tid)
);

INSERT INTO Appointments(aid,atime,adate,details) VALUES (1,'12:30','2024-02-12','detail1');
INSERT INTO Appointments(aid,atime,adate,details) VALUES (2,'12:30','2024-04-12','detail1');

INSERT INTO PatientAppointments(pid,aid) VALUES (1,1);
INSERT INTO PatientAppointments(pid,aid) VALUES (1,2);

GO
CREATE OR ALTER VIEW PatientsFebruary AS
SELECT pname,adate FROM 
(SELECT P.pname,A.adate from Patients AS P
INNER JOIN
PatientAppointments AS PA ON PA.pid = P.pid
INNER JOIN
Appointments AS A ON A.aid = PA.aid AND (DATEPART(yy,A.adate) =2024 AND DATEPART(mm,A.adate)=2)) AS R
GO

SELECT * from PatientsFebruary

GO
CREATE OR ALTER PROCEDURE AddPatient(@pid int, @pname varchar(50), @phone varchar(50), @email varchar(50), @paddress varchar(50))
AS
BEGIN
	IF EXISTS (SELECT * FROM Patients AS P WHERE P.pid =@pid) BEGIN
		PRINT('A patient with pid =' + cast(@pid AS VARCHAR) + ' already exists')
		return 1
	END
	INSERT INTO Patients(pid,pname,phone,email,paddress) VALUES (@pid,@pname,@phone,@email,@paddress)
END
GO

EXEC AddPatient 3,'Patient2','1','ex1.com','address1'
select * from Patients;

CREATE OR ALTER FUNCTION PopularDentist(@start time, @end time)
RETURNS INT
AS
BEGIN
	SELECT did, COUNT(A.aid) FROM
	(SELECT D.did, COUNT(A.aid) AppointmentTimes FROM TreatmentAppointmentDentist AS D
	INNER JOIN Appointments AS A ON D.aid = A.aid AND (A.atime>@start AND A.atime<@end)
	) AS AD
END


declare @start time='00:00';
declare @end time ='23:59';
declare @result int;
SET @result = dbo.PopularDentist(@start,@end)

--
--CREATE TABLE Appointments(
--	aid int,
--	PRIMARY KEY(aid),
--	atime time,
--	adate date,
--	details varchar(50)
--);

--CREATE TABLE TreatmentAppointmentDentist(
--	did int,
--	aid int,
--	tid int,
--	FOREIGN KEY(did) references Dentists(did),
--	FOREIGN KEY(aid) references Appointments(aid),
--	FOREIGN KEY(tid) references TreatmentPlans(tid),
--);


-- dentist highest nr treatment plans during appointments within a time period