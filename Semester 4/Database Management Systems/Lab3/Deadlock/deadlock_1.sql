USE [FlowerShop]

--Deadlock Part 1
BEGIN TRAN
UPDATE Greenhouses set adress = 'Street' WHERE gid = 1
SELECT * FROM Greenhouses
WAITFOR DELAY '00:00:10'
UPDATE Flowers SET flowerName = 'Flower' WHERE fid = 2
SELECT * FROM Flowers
COMMIT TRAN
