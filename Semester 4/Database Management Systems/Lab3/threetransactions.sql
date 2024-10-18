USE [FlowerShop]
GO
ALTER PROCEDURE [dbo].[InsertFlowerDataAndPlotSeparated]
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
    SET NOCOUNT ON;

    BEGIN TRANSACTION
    BEGIN TRY
        IF dbo.IsValidColour(@colour) = 0
            RAISERROR('Colour is invalid', 14, 1)

        IF dbo.IsValidDate(@witherDate) = 0
            RAISERROR('Wither Date is invalid', 14, 1)

        IF dbo.IsValidQuantity(@quantity) = 0
            RAISERROR('Quantity is invalid', 14, 1)

        IF dbo.IsValidPerfume(@perfume) = 0
            RAISERROR('Perfume is invalid', 14, 1)

        IF dbo.IsValidSymbollism(@symbollism) = 0
            RAISERROR('Symbollism is invalid', 14, 1)

        INSERT INTO FlowerData (colour, witherDate, quantity, perfume, symbollism)
        VALUES (@colour, @witherDate, @quantity, @perfume, @symbollism)

        DECLARE @ftid INT
        SET @ftid = (select IDENT_CURRENT('FlowerData'))

        IF dbo.IsValidFlowerName(@flowerName) = 0
            RAISERROR('Flower Name is invalid', 14, 1)

        INSERT INTO Flowers (ftid, flowerName)
        VALUES (@ftid, @flowerName)

        COMMIT TRANSACTION;
        SELECT 'FlowerData and Flowers inserted successfully'
    END TRY

    BEGIN CATCH
        ROLLBACK TRANSACTION;
        SELECT 'Insert transaction for FlowerData and Flowers rolled back'
		SELECT ERROR_MESSAGE()
        RETURN
    END CATCH

    BEGIN TRANSACTION
    BEGIN TRY
        IF dbo.IsValidAddress(@address) = 0
            RAISERROR('Address is invalid', 14, 1)

        IF dbo.IsValidCity(@city) = 0
            RAISERROR('City is invalid', 14, 1)

        IF dbo.IsValidEfficiency(@efficiency) = 0
            RAISERROR('Efficiency is invalid', 14, 1)

        INSERT INTO Greenhouses (adress, city, efficiency)
        VALUES (@address, @city, @efficiency);

        COMMIT TRANSACTION;
        SELECT 'Greenhouses inserted successfully';
    END TRY

    BEGIN CATCH
        ROLLBACK TRANSACTION;
        SELECT 'Insert transaction for Greenhouses rolled back'
		SELECT ERROR_MESSAGE()
        RETURN
    END CATCH

    BEGIN TRANSACTION;
    BEGIN TRY
        IF dbo.IsValidGrowthRate(@growthRate) = 0
            RAISERROR('Growth Rate is invalid', 14, 1)

        IF dbo.IsValidSeason(@season) = 0
            RAISERROR('Season is invalid', 14, 1)

        DECLARE @fid INT, @gid INT;

        --SET @fid = (SELECT MAX(fid) FROM Flowers)
		SET @fid = (select IDENT_CURRENT('Flowers'))

        --SET @gid = (SELECT MAX(gid) FROM Greenhouses)
		SET @gid = (select IDENT_CURRENT('Greenhouses'))

        INSERT INTO FlowerPlot (gid, fid, growthRate, season)
        VALUES (@gid, @fid, @growthRate, @season)

        COMMIT TRANSACTION;
        SELECT 'FlowerPlot inserted successfully'
    END TRY

    BEGIN CATCH
        ROLLBACK TRANSACTION
        SELECT 'Insert transaction for FlowerPlot rolled back'
		SELECT ERROR_MESSAGE()
        RETURN
    END CATCH
END
GO

Select * From FlowerData
Select * From Flowers
Select * From Greenhouses
Select * from FlowerPlot
USE [FlowerShop]
exec InsertFlowerDataAndPlotSeparated 'Pink', '2024-11-20', 20, 'Spicy', 'Friendship', 'Tulip', 'Lotus Street', 'Flowertown', 3, 5, 'Spring'
exec InsertFlowerDataAndPlotSeparated 'Pink', '2024-11-20', 20, 'Spicy', 'Friendship', 'Tulip', 'Lotus AAA', 'Flowertown', 3, 5, 'Spring'
exec InsertFlowerDataAndPlotSeparated '', '2024-11-20', 20, 'Spicy', 'Friendship', 'Tulip', 'Lotus Street', 'Flowertown', 3, 5, 'Spring'
exec InsertFlowerDataAndPlotSeparated 'Pink', '2024-11-20', 20, 'Spicy', 'Friendship', '', 'Lotus Street', 'Flowertown', 3, 5, 'Spring'

Select * From FlowerData
Select * From Flowers
Select * From Greenhouses
Select * from FlowerPlot