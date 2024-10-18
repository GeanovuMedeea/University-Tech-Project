USE [FlowerShop]

--Non Repeatable Reads Part 1
INSERT INTO Greenhouses(adress, city, efficiency) VALUES ('Pine Street','Lilactown',3)
BEGIN TRAN
WAITFOR DELAY '00:00:05'
UPDATE Greenhouses SET city='Rosevale' WHERE adress = 'Pine Street'
COMMIT TRAN