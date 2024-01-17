﻿USE [Flower Shop Temp]
GO
/****** Object:  StoredProcedure [dbo].[insert_rows]    Script Date: 1/17/2018 6:26:10 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE OR ALTER PROCEDURE [dbo].[insert_rows] 
	-- Add the parameters for the stored procedure here
	@nb_of_rows varchar(30),
	@table_name varchar(30)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	--SET NOCOUNT ON;

	declare @table varchar(100)
	set @table = ('[' + @table_name + ']')

	--if @table_name = 'Regulars' or @table_name = 'Packaging'
	--begin
	--	DBCC CHECKIDENT (@table, RESEED, 0);
	--end

    -- Insert statements for procedure here
	if ISNUMERIC(@nb_of_rows) != 1
	BEGIN
		print('Not a number')
		return 1 
	END
	
	SET @nb_of_rows = cast(@nb_of_rows as INT)

	declare @contor int
	set @contor = 1

	--regulars
	declare @regulars_rid int
	declare @regulars_cid int
	declare @regulars_dateOfRegistration DATE
	declare @regulars_points int

	--packaging
	declare @packaging_pid int
	declare @packaging_price float
	declare @packaging_category varchar(30)

	--orders
	declare @orders_fid int
	declare @orders_cid int
	declare @orders_ocid int
	declare @orders_shid int
	--declare @orders_pid int
	declare @orders_name varchar(50)
	declare @orders_placement_date DATE
	declare @orders_price float

	--flowers
	declare @flower_fid int
    declare @flower_ftid int
    declare @flower_name varchar(30)

	--ClientPublic
	declare @client_cid int
    declare @client_firstName varchar(30)
    declare @client_lastName varchar(30)
    declare @client_payment varchar(30)
    declare @client_email varchar(50)

	--Occasion
	declare @occasion_ocid int
	declare @occasion_name varchar(30)
	declare @occasion_category varchar(30)

	--Shipping
	declare @shipping_shid int
	declare @shipping_price float
	declare @shipping_category varchar(30)

	--FlowerData
	declare @flowerdata_ftid int
    declare @flowerdata_colour varchar(30)
    declare @flowerdata_witherDate DATE
    declare @flowerdata_quantity int
    declare @flowerdata_perfume varchar(30)
    declare @flowerdata_symbollism varchar(50)
	declare @maxvalue varchar(50);
	set @maxvalue = (select max(cid) from ClientPublic);
	declare @maxval int;

	while @contor <= @nb_of_rows
	begin
		if @table_name = 'FlowerData'
		begin
			set @maxval = (select isnull(max(ftid), 0) + 1 from FlowerData);
			set @flowerdata_ftid = 	@maxval;
			set @flowerdata_colour = 'Colour' + convert(varchar(7), @maxval)
			set @flowerdata_witherDate = GETDATE() -- replace with the appropriate date
			set @flowerdata_quantity = @maxval
			set @flowerdata_perfume = 'Perfume' + convert(varchar(7), @maxval)
			set @flowerdata_symbollism = 'Symbollism' + convert(varchar(7), @maxval)
			insert into FlowerData (ftid, colour, witherDate, quantity, perfume, symbollism) values (@flowerdata_ftid, @flowerdata_colour, @flowerdata_witherDate, @flowerdata_quantity, @flowerdata_perfume, @flowerdata_symbollism)
			end

		if @table_name = 'Shipping'
		begin
			set @maxval = (select isnull(max(shid), 0) + 1 from Shipping);
			set @shipping_shid = @maxval
			set @shipping_price = @maxval
			set @shipping_category = 'Category' + convert(varchar(7), (@maxval))
			insert into Shipping (shid, price, category) values (@shipping_shid, @shipping_price, @shipping_category)
			end


		if @table_name = 'Occasions'
		begin
			set @maxval = (select isnull(max(ocid), 0) + 1 from Occasions);
			set @occasion_ocid = @maxval
			set @occasion_name = 'Occasion' + convert(varchar(7), (@maxval))
			set @occasion_category = 'Category' + convert(varchar(7), (@maxval))
			insert into Occasions (ocid, occasionName, category) values (@occasion_ocid, @occasion_name, @occasion_category)
			end


		if @table_name = 'Flowers'
		begin
			set @maxval = (select isnull(max(ftid), 0) + 1 from FlowerData)- @contor;
			set @flower_fid = @maxval
			set @flower_ftid = @maxval
			set @flower_name = 'Flower' + convert(varchar(7), (@maxval))
			insert into Flowers (fid, ftid, flowerName) values (@flower_fid, @flower_ftid, @flower_name)
			end

		if @table_name = 'ClientPublic'
		begin
			set @maxval = (select isnull(max(cid), 0) + 1 from ClientPublic);
			set @client_cid = @maxval
			set @client_firstName = 'FirstName' + convert(varchar(7), (@maxval))
			set @client_lastName = 'LastName' + convert(varchar(7), (@maxval))
			set @client_payment = 'Payment' + convert(varchar(7), (@maxval))
			set @client_email = 'email' + convert(varchar(7), (@maxval)) + '@example.com'	
			insert into ClientPublic (cid, firstName, lastName, payment, email) values (@client_cid, @client_firstName, @client_lastName, @client_payment, @client_email)
			end

		begin
			if @table_name = 'Packaging'
			begin
				set @maxval = (select isnull(max(pid), 0) + 1 from Packaging);
				set @packaging_pid = @contor+10000
				set @packaging_price = @contor+10000
				set @packaging_category = 'Category' + convert(varchar(7), (@maxval + 1))
				insert into Packaging (pid, price, category) values (@packaging_pid, @packaging_price, @packaging_category)
			end
		
			if @table_name = 'Regulars'
			begin
				set @maxval = (select isnull(max(cid), 0) + 1 from ClientPublic)- @contor;
				set @regulars_rid = @maxval
				set @regulars_points = @maxval
				set @regulars_dateOfRegistration = GETDATE()
				set @regulars_cid = @maxval
				insert into Regulars(rid,cid,dateOfRegistration,points) values (@regulars_rid, @regulars_cid, @regulars_dateOfRegistration, @regulars_points)
			end

			if @table_name = 'Orders'
			begin
				set @maxval = (select isnull(max(shid), 0) + 1 from Shipping)-@contor;
				set @orders_fid = (select max(fid) from Flowers) - @contor + 1
				set @orders_cid = (select max(cid) from ClientPublic) - @contor + 1
				set @orders_ocid = (select max(ocid) from Occasions) - @contor + 1
				set @orders_shid = (select max(shid) from Shipping) - @contor + 1
				--set @orders_pid = (select max(pid) from Packaging) - @contor + 1
				set @orders_name = 'Order' + convert(varchar(7), (@maxval))
				set @orders_placement_date = GETDATE()
				set @orders_price = @maxval
				insert into Orders (fid, cid, ocid, shid, orderName, orderPlacementDate, price) values (@orders_fid, @orders_cid, @orders_ocid, @orders_shid, @orders_name, @orders_placement_date, @orders_price)
			end

			set @contor = @contor + 1
		end
	end
END