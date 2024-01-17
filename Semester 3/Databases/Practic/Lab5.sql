USE [Lab5]

drop table Tc;
drop table Ta;
drop table Tb;
-- could have used other ones

create table Ta (
    aid int primary key,
    a2 int unique,
    vala int
)

create table Tb (
    bid int primary key,
    b2 int,
    valb int
)

create table Tc (
    cid int primary key,
    aid int FOREIGN KEY references Ta(aid),
    bid int FOREIGN KEY references Tb(bid),
	valc int
)
GO

create or alter procedure valuesTa(@rows int) as
    declare @maxval int
    set @maxval = @rows*3
    while @rows > 0 begin
        insert into Ta values (@rows, @maxval, @rows%100)
        set @rows = @rows-1
        set @maxval = @maxval-1
    end
GO

create or alter procedure valuesTb(@rows int) as
    declare @maxval int
    set @maxval = @rows*2
    while @rows > 0 begin
        insert into Tb values (@rows, @maxval, @rows%140)
        set @rows = @rows-1
        set @maxval = @maxval-2
    end
GO

create or alter procedure valuesTc(@rows int) as
    declare @aid int
    declare @bid int
    while @rows > 0 begin
        set @aid = (select top 1 aid from Ta order by NEWID())
        set @bid = (select top 1 bid from Tb order by NEWID())
        insert into Tc values (@rows,@aid,@bid, (@aid + @bid) % 180)
        set @rows = @rows-1
    end
GO

delete from Ta;
delete from Tb;
delete from Tc;
exec valuesTa 3000
exec valuesTb 30
exec valuesTc 200
select * from Ta;
select * from Tb;
select * from Tc;

create nonclustered index index1 on Ta(aid)
drop index index1 on Ta
    
select * from Ta order by aid -- Clustered Index Scan
select * from Ta where aid = 65 -- Clustered Index Seek
SELECT COUNT(*) FROM Ta -- NonClustered Index Scan
select a2 from Ta where a2 = 1 -- Nonclustered Index Seek
select vala from Ta where a2 = 26093 -- Key Lookup

select valb, b2 from Tb where b2 = 42732 GROUP BY valb,b2 ORDER BY b2 -- Clustered Index Scan 0.002s/0,001 Nonclustered Index Seek 0.000s

create nonclustered index index2 on Tb(b2)
drop index index2 on Tb

create nonclustered index index3 on Tc(bid) include (aid)
drop index index3 on Tc


GO
CREATE OR ALTER VIEW V1 AS
SELECT TOP 2000
    T1.a2,
    T2.b2
FROM
    Tc AS T3
    JOIN Ta AS T1 ON T3.aid = T1.aid
    JOIN Tb AS T2 ON T3.bid = T2.bid
WHERE
    T1.a2 > 1 AND
    T2.b2 < 6000
GROUP BY
    T1.a2, T2.b2
ORDER BY
    T1.a2, T2.b2;
GO

select * from V1


SELECT COUNT(DISTINCT vala) AS unique_value_count
FROM Ta;

SELECT COUNT(DISTINCT valb) AS unique_value_count
FROM Tb;

SELECT COUNT(DISTINCT cid) AS unique_value_count
FROM Tc;


-- 0.18 total cost without indexes
-- 0.8 total cost with indexes
