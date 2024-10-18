USE [FlowerShop]

--Phantom Reads Serializable Part 1
--DELETE FROM Greenhouses
BEGIN TRAN
WAITFOR DELAY '00:00:04'
INSERT INTO Greenhouses(adress, city, efficiency) VALUES ('Tree Road','Rosetown',3)
COMMIT TRAN