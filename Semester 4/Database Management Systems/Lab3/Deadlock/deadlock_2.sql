USE [FlowerShop]

--Deadlock Part 2
SET DEADLOCK_PRIORITY HIGH
BEGIN TRAN
UPDATE Flowers SET flowerName = 'Plant' WHERE fid = 2
SELECT * FROM Flowers
WAITFOR DELAY '00:00:10'
UPDATE Greenhouses set adress = 'Road' WHERE gid = 1
SELECT * FROM Greenhouses
COMMIT TRAN
