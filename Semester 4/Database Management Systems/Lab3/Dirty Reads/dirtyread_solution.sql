USE [FlowerShop]

--Dirty Reads Solution
SET TRANSACTION ISOLATION LEVEL READ COMMITTED
BEGIN TRAN
SELECT * FROM Greenhouses
WAITFOR DELAY '00:00:05'
SELECT * FROM Greenhouses
COMMIT TRAN