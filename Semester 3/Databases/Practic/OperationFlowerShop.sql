use [Flower Shop]

-- 3 Update operations--
-- for flowerplots grown in greenhouses 1 and 2, with growth rate less than two or greater or equal than 2, the season will
-- be set to summer; for greenhouses that are either 1 or 2 and have growthrate greater or equal than 2, then the growthrate will be
-- set to 5; these operations happen for the flowerplots with id les or equal than 3, but it's not 1
UPDATE FlowerPlot SET
season = CASE
	WHEN (gid = 1 AND growthRate < 2) THEN 'Summer'
	WHEN (gid = 2 AND growthRate >= 2) THEN 'Summer'
END,
growthRate = CASE
	WHEN ((gid = 1 OR gid = 2) AND growthRate >= 2) THEN 5
	WHEN (season = 'Summer') THEN 3
END
WHERE (fid <=3) AND NOT (fid = 1)

--for clientsprivate that have custumoer type natural, the adress is not null and the cnp is 1234567890, then their city location
-- will be changed to Rosefield
UPDATE ClientPrivate SET
city = 'Rosefield'
WHERE ((customerType = 'NATURAL') AND (adress IS NOT NULL))AND (CNP LIKE '1234567890')

-- for packaging with id different from 1 abd two, or its price is greater than 3.50, the new price will be 4.99
UPDATE Packaging SET
price = 4.99
WHERE ((pid <> 1) AND (pid <> 2)) OR (price > 3.50)

-- 2 Delete operations -
-- from flower data delete every entry with quantity less than 40 or with symbollism field null
DELETE FROM FlowerData
WHERE quantity < 40 OR (symbollism IS NULL)

-- delete from orders all entries whose price is either 45.99/35.99 or the price is between 55 and 61
DELETE FROM Orders
WHERE price IN (45.99,35.99) OR price BETWEEN 55 AND 61

-- a) 2 queries with the union operation; use UNION [ALL] and OR; --
-- locate where clients are relative to the establishment's greenhouses --
-- select all cities, in short
SELECT city, adress FROM Greenhouses
UNION
SELECT city, adress FROM ClientPrivate
ORDER by city;

-- compile the flowers bought by customers who buy them with credit card or paypal, accepting duplicates --
-- the first select chooses the flower names in that are placed in orders paid with credit card or paypal
-- the second select chooses the names of the clients who have placed orders paid in this method
SELECT F.flowerName
FROM Flowers AS F
WHERE F.fid IN (
    SELECT O.fid
    FROM Orders AS O
    WHERE O.cid IN (
        SELECT C.cid
        FROM ClientPublic AS C
        WHERE C.payment = 'Credit Card' OR C.payment = 'PayPal'
    )
)
UNION ALL
SELECT C2.firstName
FROM ClientPublic AS C2
WHERE C2.cid IN(
	SELECT O2.cid
	FROM Orders AS O2
	WHERE O2.fid IN(
		SELECT F2.fid
		FROM Flowers as F2
		) AND C2.payment = 'Credit Card' OR C2.payment = 'PayPal'
);

-- b) 2 queries with the intersection operation; use INTERSECT and IN;
-- cities in which there are clients living nearby greenhouses -
-- the intersect chooses the columns that match
SELECT city FROM ClientPrivate
INTERSECT
SELECT city FROM Greenhouses;

-- employees who work in greenhouses with efficiency greater than 80%--
SELECT firstName, lastName FROM Employees
WHERE gid IN(
	SELECT G.gid
	FROM Greenhouses AS G
	WHERE G.efficiency > 0.80
);

-- c) 2 queries with the difference operation; use EXCEPT and NOT IN;
-- Find flowers that are not part of any orders --
SELECT flowerName
FROM Flowers
EXCEPT
SELECT DISTINCT F.flowerName
FROM Flowers AS F
INNER JOIN Orders AS O ON F.fid = O.fid;

-- Find flower names that are not part of any orders
SELECT flowerName
FROM Flowers
WHERE fid NOT IN(
	SELECT DISTINCT F.fid
	FROM Flowers AS F
	INNER JOIN Orders AS O ON F.fid = O.fid
);

-- d) 4 queries with INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL JOIN (one query per operator); one query will join at least 3 tables, while another one will join at least two many-to-many relationships;
-- Join three tables: Orders, Clients, and Flowers, without flowers that aren't in any orders
SELECT O.orderName, C.firstName, C.lastName, F.flowerName
FROM Orders AS O
LEFT JOIN ClientPublic AS C ON O.cid = C.cid
LEFT JOIN Flowers AS F ON O.fid = F.fid;

-- Joining two many-to-many relationships: Orders-Flowers and Flowers-Greenhouses
SELECT O.orderName, C.firstName, F.flowerName, G.city
FROM Orders AS O
INNER JOIN Flowers AS F ON O.fId = F.fId
INNER JOIN FlowerPlot AS FP ON F.fid = FP.fid
INNER JOIN Greenhouses AS G ON FP.gid = G.gid
INNER JOIN ClientPublic AS C ON O.cid = C.cid;

-- Right join Orders and Shipping
-- matches all of the records on the right(the shippment) with the matching records in orders
SELECT O.orderName, S.price, S.category
FROM Orders AS O
RIGHT JOIN Shipping AS S ON O.shid = S.shid;

-- Full join Employees and Greenhouses
-- matches all records from employees with all the records from greenhouses
SELECT E.firstName, E.lastName, G.city
FROM Employees AS E
FULL JOIN Greenhouses AS G ON E.gid = G.gid;

-- e) 2 queries with the IN operator and a subquery in the WHERE clause; in at least one case, the subquery must include a subquery in its own WHERE clause;
-- Find employees working in greenhouses located in cities with orders for weddings
SELECT E.firstName, E.lastName
FROM Employees AS E
WHERE E.gid IN(
	SELECT DISTINCT G.gid
	FROM Greenhouses AS G
	WHERE G.gid IN(
		SELECT DISTINCT FP.gid
		FROM FlowerPlot AS FP
		WHERE FP.fid IN(
			SELECT DISTINCT O.fid
			FROM Orders as O
			INNER JOIN Occasions AS Oc ON O.ocid = Oc.ocid
			WHERE O.orderName LIKE 'Wedding%' OR Oc.category = 'Wedding%'
		)
	)
);

-- Find clients who placed orders for roses with the colous red
SELECT CP.firstName, CP.lastName
FROM ClientPublic AS CP
WHERE CP.cid IN(
    SELECT DISTINCT O.cid
    FROM Orders AS O
    WHERE O.fid IN(
        SELECT DISTINCT F.fid
        FROM Flowers AS F
        WHERE F.flowerName = 'Roses' AND F.ftid IN(
			SELECT ftid
			FROM FlowerData WHERE colour = 'Red'
		)
    )
);

-- f) 2 queries with the EXISTS operator and a subquery in the WHERE clause;
-- Find employees who grown daisies with a subtle perfume
SELECT E.gid, E.firstName, E.lastName
FROM Employees AS E
WHERE EXISTS (
	SELECT *
	FROM FlowerPlot as FP
	WHERE FP.fid IN(
		SELECT F.fid
		FROM Flowers AS F
		INNER JOIN FlowerData AS FD ON F.ftid = FD.ftid
		WHERE F.flowerName = 'Daisies' AND FD.perfume = 'Subtle'
		)
	AND FP.gid = E.gid
);

-- Find clients who have placed orders for flowers with quantities lower than the average
SELECT CP.firstName, CP.lastName
FROM ClientPublic AS CP
WHERE EXISTS (
    SELECT *
    FROM Orders AS O
    WHERE O.cid = CP.cid
    AND O.fid IN (
        SELECT F.fid
        FROM Flowers AS F
        WHERE F.ftid IN (
            SELECT ftid
            FROM FlowerData
            WHERE quantity < (SELECT AVG(quantity) FROM FlowerData)
        )
    )
);

-- g) 2 queries with a subquery in the FROM clause;
-- Find the average salary for employees in each greenhouse
SELECT G.city, AVG(E.salary) AS AverageSalary
FROM (
    SELECT gid, city
    FROM Greenhouses
) AS G
LEFT JOIN Employees AS E ON G.gid = E.gid
GROUP BY G.city;


-- Find the number of orders placed for each flower, and the whole quantity of flowers
SELECT F.flowerName, COUNT(O.fid) AS NumberOfOrders
FROM (
    SELECT fid, flowerName
    FROM Flowers
) AS F
LEFT JOIN Orders AS O ON F.fid = O.fid
GROUP BY F.flowerName;


-- h) 4 queries with the GROUP BY clause, 3 of which also contain the HAVING clause; 2 of the latter will also have a subquery in the HAVING clause; use the aggregation operators: COUNT, SUM, AVG, MIN, MAX;
-- Find the average salary for the employees in each city with an average salary greater than the average salary of all employees
SELECT G.city, AVG(E.salary) AS AverageSalary, MAX(E.salary) - AVG(E.salary) AS SalaryDifference, SUM(E.salary)+15*MAX(E.salary)/100 AS BruteBudget
FROM Greenhouses AS G
LEFT JOIN Employees AS E ON G.gid = E.gid
GROUP BY G.city
HAVING AVG(E.salary) > (
	SELECT AVG(salary)
	FROM Employees
);

-- Find clients with the highest number of orders
SELECT CP.firstName, CP.lastName, COUNT(O.cid) AS OrderCount
FROM ClientPublic AS CP
LEFT JOIN Orders AS O ON CP.cid = O.cid
GROUP BY CP.firstName, CP.lastName
HAVING COUNT(O.cid) = (
	SELECT MAX(OrderCount)
	FROM (
		SELECT COUNT(O2.cid)
		AS OrderCount
		FROM Orders
		AS O2
		GROUP BY O2.cid)
	AS Temp
);

-- Find cities with more than one greenhouse and average efficiency less than the average, and the difference between minimum wage and average
SELECT G.gid, G.city, AVG(G.efficiency) - MIN(G.efficiency) 
FROM Greenhouses AS G
GROUP BY G.gid, G.city
HAVING COUNT(G.gid) > 1 AND AVG(G.efficiency) <(
	SELECT AVG(efficiency)
	FROM Greenhouses
);

-- Find the minimum and maximum quantity of flowers for each color
SELECT Fd.colour, MIN(Fd.quantity) AS MinQuantity, MAX(Fd.quantity) AS MaxQuantity, MAX(Fd.quantity) - MIN(Fd.quantity) AS QuantityDifference
FROM FlowerData AS Fd
GROUP BY Fd.colour;

-- i) 4 queries using ANY and ALL to introduce a subquery in the WHERE clause (2 queries per operator); rewrite 2 of them with aggregation operators, and the other 2 with IN / [NOT] IN.
-- Find employees whose salary is greater any random employee in Greenhouse 2 --
SELECT E.firstName, E.lastName
FROM Employees AS E
WHERE E.salary > ANY (
	SELECT TOP 1 E2.salary
	FROM Employees AS E2
	WHERE E2.gid = 2
	ORDER BY E2.salary
);

--Find employees whose salary is greater than all employees in Greenhouse 2--
SELECT E.firstName, E.lastName
FROM Employees AS E
WHERE E.salary > ALL (
	SELECT E2.salary
	FROM Employees AS E2
	WHERE E2.gid = 2	
);

--Find all greenhouses that have an efficiency greater than the lowest efficiency of any greenhouse in the 'Spring' season
SELECT G.adress, G.city
FROM Greenhouses G
WHERE G.efficiency > ANY (
    SELECT G1.efficiency
    FROM Greenhouses AS G1
    WHERE G1.gid IN (
        SELECT gid
        FROM FlowerPlot
        WHERE season = 'Spring'
    )
);

--Find all greenhouses that have an efficiency lower or equal to the lowest efficiency of all greenhouse in the 'Summer' season
SELECT G.adress, G.city, G.efficiency
FROM Greenhouses G
WHERE G.efficiency <= ALL (
	SELECT TOP 1 G2.efficiency
    --SELECT MIN(efficiency) AS minEfficiency
    FROM Greenhouses AS G2
    WHERE gid IN (
        SELECT gid
        FROM FlowerPlot
        WHERE season = 'Summer'
    )
	ORDER BY G.efficiency
);

--rewritten with aggregation operations, in the same order--
SELECT E.firstName, E.lastName
FROM Employees AS E
WHERE E.salary > (
    SELECT MIN(E2.salary)
    FROM Employees AS E2
    WHERE E2.gid = 2
);

SELECT E.firstName, E.lastName
FROM Employees AS E
WHERE E.salary > (
    SELECT MAX(E2.salary)
    FROM Employees AS E2
    WHERE E2.gid = 2
);

SELECT DISTINCT G1.adress, G1.city
FROM Greenhouses G1
INNER JOIN FlowerPlot FP1 ON G1.gid = FP1.gid
WHERE G1.efficiency NOT IN (
    SELECT TOP 1 G2.efficiency
    FROM Greenhouses G2
    INNER JOIN FlowerPlot FP2 ON G2.gid = FP2.gid
    WHERE FP2.season = 'Spring'
    ORDER BY G2.efficiency DESC
);

SELECT DISTINCT g1.adress, g1.city
FROM Greenhouses g1
INNER JOIN FlowerPlot fp1 ON g1.gid = fp1.gid
WHERE g1.efficiency IN (
	SELECT TOP 1 g2.efficiency
	FROM Greenhouses g2
	INNER JOIN FlowerPlot fp2 ON g2.gid = fp2.gid
	WHERE fp2.season = 'Summer'
	ORDER BY g1.efficiency
) OR g1.efficiency <(
	SELECT TOP 1 g2.efficiency
	FROM Greenhouses g2
	INNER JOIN FlowerPlot fp2 ON g2.gid = fp2.gid
	WHERE fp2.season = 'Summer'
	ORDER BY g2.efficiency
);

--You must use:
--arithmetic expressions in the SELECT clause in at least 3 queries;
--conditions with AND, OR, NOT, and parentheses in the WHERE clause in at least 3 queries;
--DISTINCT in at least 3 queries, ORDER BY in at least 2 queries, and TOP in at least 2 queries.