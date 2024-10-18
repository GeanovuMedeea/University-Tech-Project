USE [FlowerShop]

--Deadlock Solution
BEGIN TRAN
UPDATE Flowers SET flowerName = 'Vegetable' WHERE fid = 6
SELECT * FROM Flowers
WAITFOR DELAY '00:00:10'
UPDATE Greenhouses set adress = 'Lane' WHERE gid = 4
SELECT * FROM Greenhouses
COMMIT TRAN