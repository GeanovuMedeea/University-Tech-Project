USE [FlowerShop]

--Dirty Reads Part 1
BEGIN TRANSACTION
UPDATE Greenhouses SET city='Petuniaville'
WHERE gid = 2
WAITFOR DELAY '00:00:10'
ROLLBACK TRANSACTION
