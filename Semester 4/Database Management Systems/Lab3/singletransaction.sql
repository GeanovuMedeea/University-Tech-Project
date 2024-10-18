USE [FlowerShop]
GO
ALTER PROCEDURE [dbo].[InsertFlowerDataAndPlot]
    @colour VARCHAR(30),
    @witherDate DATE,
    @quantity INT,
    @perfume VARCHAR(30),
    @symbollism VARCHAR(50),
    @flowerName VARCHAR(30),
    @address VARCHAR(100),
    @city VARCHAR(30),
    @efficiency FLOAT,
    @growthRate INT,
    @season VARCHAR(30)
AS
BEGIN
    SET NOCOUNT ON

    BEGIN TRANSACTION
    BEGIN TRY
		IF dbo.IsValidColour(@colour) = 0
		BEGIN
				RAISERROR('colour is wrong', 14, 1)
		END
		IF dbo.IsValidDate(@witherDate) = 0
		BEGIN
				RAISERROR('witherDate is wrong', 14, 1)
		END
		IF dbo.IsValidQuantity(@quantity) = 0
		BEGIN
				RAISERROR('quantity is wrong', 14, 1)
		END
		IF dbo.IsValidPerfume(@perfume) = 0
		BEGIN
				RAISERROR('perfume is wrong', 14, 1)
		END
		IF dbo.IsValidSymbollism(@symbollism) = 0
		BEGIN
				RAISERROR('symbollism is wrong', 14, 1)
		END
		IF dbo.IsValidFlowerName(@flowerName) = 0
		BEGIN
				RAISERROR('flowerName is wrong', 14, 1)
		END
		IF dbo.IsValidAddress(@address) = 0
		BEGIN
				RAISERROR('adress is wrong', 14, 1)
		END
		IF dbo.IsValidCity(@city) = 0
		BEGIN
				RAISERROR('city is wrong', 14, 1)
		END
		IF dbo.IsValidGrowthRate(@growthRate) = 0
		BEGIN
				RAISERROR('growthRate is wrong', 14, 1)
		END
		IF dbo.IsValidSeason(@season) = 0
		BEGIN
				RAISERROR('season is wrong', 14, 1)
		END

        INSERT INTO FlowerData (colour, witherDate, quantity, perfume, symbollism)
        VALUES (@colour, @witherDate, @quantity, @perfume, @symbollism)

        DECLARE @ftid INT
		SET @ftid = (select IDENT_CURRENT('FlowerData'))
        --SET @ftid = SCOPE_IDENTITY()

        INSERT INTO Flowers (ftid, flowerName)
        VALUES (@ftid, @flowerName)

		DECLARE @fid INT;
        SET @fid = (select IDENT_CURRENT('Flowers'))

        INSERT INTO Greenhouses (adress, city, efficiency)
        VALUES (@address, @city, @efficiency)

        DECLARE @gid INT;
        SET @gid = (select IDENT_CURRENT('Greenhouses'))

        INSERT INTO FlowerPlot (gid, fid, growthRate, season)
        VALUES (@gid, @fid, @growthRate, @season)

        PRINT 'Data inserted successfully'
        COMMIT TRAN
        SELECT 'Transaction committed'
    END TRY

    BEGIN CATCH
        ROLLBACK TRAN
        SELECT 'Insert transaction rolled back'
		SELECT ERROR_MESSAGE()
    END CATCH
END

GO

Select * From FlowerData
Select * From Flowers
Select * From Greenhouses
Select * from FlowerPlot
USE [FlowerShop]
exec InsertFlowerDataAndPlot 'Red', '2023-10-20', 50, 'Sweet', 'Love and cheerfulness', 'Iris', 'Pine Road', 'Meadowville', 2, 3, 'Autumn'
exec InsertFlowerDataAndPlot '', '2023-10-20', 50, 'Sweet', 'Love and cheerfulness', 'Iris', 'Pine Road', 'Meadowville', 2, 3, 'Autumn'

Select * From FlowerData
Select * From Flowers
Select * From Greenhouses
Select * from FlowerPlot