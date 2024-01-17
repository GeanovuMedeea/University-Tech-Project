﻿USE [Flower Shop Temp]
GO
/****** Object:  StoredProcedure [dbo].[delete_rows]    Script Date: 1/17/2018 6:26:20 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[delete_rows]
	-- Add the parameters for the stored procedure here
	@nb_of_rows varchar(30),
	@table_name varchar(30)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	--SET NOCOUNT ON;

	-- Insert statements for procedure here
	IF ISNUMERIC(@nb_of_rows) != 1
	BEGIN
		PRINT('Not a number')
		RETURN 1 
	END

	SET @nb_of_rows = CAST(@nb_of_rows AS INT)

	DECLARE @last_row INT
	IF @table_name = 'Packaging'
	BEGIN
		SET @last_row = (SELECT MAX(pid) FROM Packaging) - @nb_of_rows
		DELETE FROM Packaging
		WHERE pid > @last_row
	END
		
	IF @table_name = 'Regulars'
	BEGIN
		SET @last_row = (SELECT MAX(rid) FROM Regulars) - @nb_of_rows
		DELETE FROM Regulars
		WHERE rid > @last_row
	END

	IF @table_name = 'Orders'
	BEGIN
		DELETE FROM Orders
	END

	IF @table_name = 'Shipping'
	BEGIN
		DELETE FROM Shipping
	END

	IF @table_name = 'Occasions'
	BEGIN
		DELETE FROM Occasions
	END

	IF @table_name = 'Flowers'
	BEGIN
		SET @last_row = (SELECT MAX(fid) FROM Flowers) - @nb_of_rows
		DELETE FROM Flowers
		WHERE fid > @last_row
	END

	IF @table_name = 'FlowerData'
	BEGIN
		DELETE FROM FlowerData
	END

	IF @table_name = 'ClientPublic'
	BEGIN
		SET @last_row = (SELECT MAX(cid) FROM ClientPublic) - @nb_of_rows
		DELETE FROM ClientPublic
		WHERE cid > @last_row
	END
END
