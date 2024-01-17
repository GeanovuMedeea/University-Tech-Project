drop table Patients;
drop table Doctors;
drop table Medications;
drop table Appointments;
drop table Prescriptions;

CREATE TABLE Patients(
	pid int UNIQUE,
	PRIMARY KEY(pid),
	pname varchar(50),
	bdate date,
	gender varchar(50)
);

CREATE TABLE Doctors(
	did int,
	PRIMARY KEY(did),
	dname varchar(50),
	department int
);

CREATE TABLE Medications(
	mid int,
	PRIMARY KEY(mid),
	mname varchar(50) UNIQUE,
	manufacturer varchar(50)
);

CREATE TABLE Appointments(
	aid int,
	PRIMARY KEY(aid),
	pid int,
	did int,
	FOREIGN KEY(pid) references Patients(pid),
	FOREIGN KEY(did) references Doctors(did),
	adate date
);

CREATE TABLE Prescriptions(
	aid int,
	mid int,
	FOREIGN KEY(aid) references Appointments(aid),
	FOREIGN KEY(mid) references Medications(mid),
	dosage int
);

GO
CREATE OR ALTER PROCEDURE CreateAppointment(@pid int,@did int,@date date)
AS
BEGIN
	IF @date < GETDATE() BEGIN
		PRINT('DATE WRONG');
		return 1
	END

	IF EXISTS (SELECT * FROM Appointments AS A WHERE A.did=@did AND A.pid = pid) BEGIN
		UPDATE Appointments
		SET Appointments.adate = @date
		WHERE Appointments.did = @did AND Appointments.pid =@pid
	END
	ELSE BEGIN
		declare @tempid int;
		set @tempid=(SELECT MAX(aid) FROM Appointments)+1;
		INSERT INTO Appointments(aid,pid,did,adate) VALUES (@tempid,@pid,@did,@date)
	END
END
GO

CREATE OR ALTER VIEW DoctorsFebruary AS
SELECT dname FROM 
(SELECT D.dname from Doctors AS D
INNER JOIN
Appointments AS A ON A.did = D.did AND (DATEPART(yy,A.adate) =2024 AND DATEPART(mm,A.adate)=2)) AS R
GO

CREATE OR ALTER FUNCTION MedicationCount(@D int)
RETURNS TABLE
AS
RETURN(
	SELECT did,COUNT(P.mid) AS MedCount
	FROM Appointments AS A
	LEFT JOIN Prescriptions AS P ON A.aid = P.aid
	WHERE A.aid=@D
	GROUP BY did
)
GO