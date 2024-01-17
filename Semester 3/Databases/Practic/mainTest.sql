USE [Flower Shop Temp]
GO
--
-- This block of comments will not be included in
-- the definition of the procedure.
-- ================================================
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================

CREATE OR ALTER PROCEDURE [dbo].[runTestTable]
	-- Add the parameters for the stored procedure here
	@NoOfRows varchar(30), @TableId varchar(30)
AS
BEGIN
	--SET NOCOUNT ON;

	declare @table varchar(100)
	set @table = ('[' + (SELECT Name FROM Tables WHERE Tables.TableID = @TableID) + ']')

	declare @insert_start datetime
	set @insert_start = GETDATE()

	if @table = '[Orders]'
		begin
		exec delete_rows @NoOfRows, 'Orders'
		exec delete_rows @NoOfRows, 'Shipping'
		exec delete_rows @NoOfRows, 'ClientPublic'
		exec delete_rows @NoOfRows, 'FlowerData'
		exec delete_rows @NoOfRows, 'Flowers'
		exec delete_rows @NoOfRows, 'Occasions'
		exec insert_rows @NoOfRows, 'Shipping'
		exec insert_rows @NoOfRows, 'Occasions'
		exec insert_rows @NoOfRows, 'FlowerData'
		exec insert_rows @NoOfRows, 'Flowers'
		exec insert_rows @NoOfRows, 'ClientPublic'
		exec insert_rows @NoOfRows, 'Orders'
		end

	if @table = '[Regulars]'
		begin
		exec delete_rows @NoOfRows, 'Regulars'
		exec delete_rows @NoOfRows, 'ClientPublic'
		exec insert_rows @NoOfRows, 'ClientPublic'
		exec insert_rows @NoOfRows, 'Regulars'
		end
	
	if @table = '[Packaging]'
		begin
		exec delete_rows @NoOfRows, 'Packaging'
		exec insert_rows @NoOfRows, 'Packaging'
		end

	declare @insert_end datetime
	set @insert_end = GETDATE()

	declare @lastTestRunID int; 
	set @lastTestRunID = (select max(TestRunID) from TestRuns);
	insert into TestRunTables(TestRunID, TableId, StartAt, EndAt) values(@lastTestRunId,@TableId, @insert_start, @insert_end)

END
go

CREATE OR ALTER PROCEDURE [dbo].[runTestView]
	-- Add the parameters for the stored procedure here
	@TableId varchar(30), @ViewId varchar(30)
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	--SET NOCOUNT ON;

	declare @view varchar(100)
	set @view = ('[' + (SELECT Name FROM Views WHERE Views.ViewID = @ViewId) + ']')

	declare @table_start datetime
	set @table_start = GETDATE()

	if @view = '[View_1_table]'
		begin
		exec select_view 'View_1_table'
		end
		
	if @view = '[View_2_tables]'
		begin
		exec select_view 'View_2_tables'
		end
	
	if @view = '[View_2_tables_group_by]'
		begin
		exec select_view 'View_2_tables_group_by'
		end

	declare @table_end datetime
	set @table_end = GETDATE()
	declare @lastTestRunID int; 
	set @lastTestRunID = (select max(TestRunID) from TestRuns);
	insert into TestRunViews(TestRunID,ViewID, StartAt,EndAt) values(@lastTestRunId, @ViewId, @table_start, @table_end)

END
go

CREATE OR ALTER PROCEDURE [dbo].[mainTest]
	-- Add the parameters for the stored procedure here
	@testNumber INT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	declare @all_stop datetime 
	declare @all_start datetime
	set @all_start = GETDATE();
	insert into TestRuns(StartAt) values(@all_start);

	-- Declare variables and cursor for iterating through TestTables
	DECLARE @TestID INT, @TableID INT, @NoOfRows INT, @Position INT, @ViewId INT;
	DECLARE TestTablesCursor CURSOR FOR
		SELECT T.TestID, T.TableID, T.NoOfRows, T.Position
		FROM TestTables AS T

	-- Open and fetch the first row
	OPEN TestTablesCursor;
	FETCH NEXT FROM TestTablesCursor INTO @TestID, @TableID, @NoOfRows, @Position;

	-- Loop through TestTables and execute RunTest for each test
	WHILE @@FETCH_STATUS = 0
	BEGIN
		-- Execute RunTest for the current test
		EXEC runTestTable @NoOfRows, @TableID
		-- Fetch the next row
		FETCH NEXT FROM TestTablesCursor INTO @TestID, @TableID, @NoOfRows, @Position;
	END
	CLOSE TestTablesCursor;
	DEALLOCATE TestTablesCursor;

	DECLARE ViewTablesCursor CURSOR FOR
		SELECT T.TestID, T.ViewID
		FROM TestViews AS T

	-- Open and fetch the first row
	OPEN ViewTablesCursor;
	FETCH NEXT FROM ViewTablesCursor INTO @TestID, @ViewId;

	-- Loop through TestTables and execute RunTest for each test
	WHILE @@FETCH_STATUS = 0
	BEGIN
		-- Execute RunTest for the current test
		EXEC runTestView @TableID, @ViewId
		-- Fetch the next row
		FETCH NEXT FROM ViewTablesCursor INTO @TestID, @ViewId;
	END
	CLOSE ViewTablesCursor;
	DEALLOCATE ViewTablesCursor;

	
	set @all_stop = getdate() 
	declare @lastTestRunID int; 
	set @lastTestRunID = (select max(TestRunID) from TestRuns);

	declare @description nvarchar(2000);
	set @description = 'TestRun' + convert(nvarchar(30), @lastTestRunID)

	UPDATE TestRuns SET
	EndAt = @all_stop, Description = @description
	WHERE TestRunID = @lastTestRunID;
	--insert into TestRuns(Description, EndAt) values(@description, @all_stop);
	-- Close and deallocate the cursor
END
go


delete from Tables;
	insert into Tables(Name) values ('Packaging');
	insert into Tables(Name) values ('Regulars');
	insert into Tables(Name) values ('Orders');
	delete from Views;
	insert into Views(Name) values ('View_1_table');
	insert into Views(Name) values ('View_2_tables');
	insert into Views(Name) values ('View_2_tables_group_by');

	-- Example: Define Test Scenarios
	delete from Tests;
	INSERT INTO Tests (Name) VALUES ('Test1'),('Test2');
	delete from TestTables;
	-- Example: Associate Tables and Views with Tests
	--INSERT INTO TestTables (TestID, TableID, NoOfRows, Position) VALUES (1, 2, 12552, 1);
	INSERT INTO TestTables (TestID, TableID, NoOfRows, Position) VALUES (1, 1, 14000, 1), (1, 2, 12552, 2), (1, 3, 11231, 3);
	INSERT INTO TestTables (TestID, TableID, NoOfRows, Position) VALUES (2, 2, 41231, 1), (2, 3, 13241, 2);
	delete from TestViews;
	--INSERT INTO TestViews (TestID, ViewID) VALUES (1, 2)
	INSERT INTO TestViews (TestID, ViewID) VALUES (1, 1), (1, 2), (1,3)
	INSERT INTO TestViews (TestID, ViewID) VALUES (2, 1);

EXEC mainTest 2;

select * FROM Tables;
select * FROM Views;
select * FROM Tests;
select * FROM TestTables;
select * FROM TestViews;
select * FROM TestRuns;
select * FROM TestRunTables;
select * FROM TestRunViews;

SELECT * FROM View_1_table;
SELECT * FROM View_2_tables;
SELECT * FROM View_2_tables_group_by;