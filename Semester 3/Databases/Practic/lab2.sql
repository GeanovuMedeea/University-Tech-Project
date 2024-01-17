use [Flower Shop]

SELECT * FROM ClientPrivate;
SELECT * FROM ClientPublic;
SELECT * FROM Employees;
SELECT * FROM Flowers;
SELECT * FROM FlowerData;
SELECT * FROM FlowerPlot;
SELECT * FROM Greenhouses;
SELECT * FROM Occasions;
SELECT * FROM Orders;
SELECT * FROM Packaging;
SELECT * FROM Regulars;
SELECT * FROM Shipping;

DELETE FROM ClientPrivate;
DELETE FROM ClientPublic;
DELETE FROM Employees;
DELETE FROM Flowers;
DELETE FROM FlowerData;
DELETE FROM FlowerPlot;
DELETE FROM Greenhouses;
DELETE FROM Occasions;
DELETE FROM Orders;
DELETE FROM Packaging;
DELETE FROM Regulars;
DELETE FROM Shipping;

-- Inserting data into the Shipping table
INSERT INTO Shipping (shid, price, category, expressDelivery) VALUES (1, 9.99, 'Standard', 'NO');
INSERT INTO Shipping (shid, price, category, expressDelivery) VALUES (2, 14.99, 'Standard', 'YES');
INSERT INTO Shipping (shid, price, category, expressDelivery) VALUES (3, 19.99, 'Express', 'YES');
INSERT INTO Shipping (shid, price, category, expressDelivery) VALUES (4, 5.99, 'Economy', 'NO');
INSERT INTO Shipping (shid, price, category, expressDelivery) VALUES (5, 12.99, 'Standard', 'NO');

-- Inserting data into the Packaging table for flower packages
INSERT INTO Packaging (pid, price, category) VALUES (1, 2.99, 'Flower Bouquet Box');
INSERT INTO Packaging (pid, price, category) VALUES (2, 1.49, 'Floral Wrapping Paper');
INSERT INTO Packaging (pid, price, category) VALUES (3, 0.99, 'Flower Vase');
INSERT INTO Packaging (pid, price, category) VALUES (4, 3.99, 'Flower Pot');
INSERT INTO Packaging (pid, price, category) VALUES (5, 1.79, 'Flower Sleeve');

-- Inserting data into the Occasions table for flower arrangements
INSERT INTO Occasions (ocid, occasionName, category) VALUES (1, 'Wedding', 'Special Events');
INSERT INTO Occasions (ocid, occasionName, category) VALUES (2, 'Anniversary', 'Special Events');
INSERT INTO Occasions (ocid, occasionName, category) VALUES (3, 'Birthday', 'Personal Celebrations');
INSERT INTO Occasions (ocid, occasionName, category) VALUES (4, 'Funeral', 'Sympathy');
INSERT INTO Occasions (ocid, occasionName, category) VALUES (5, 'Mothers Day', 'Holidays');
INSERT INTO Occasions (ocid, occasionName, category) VALUES (6, 'Valentines Day', 'Holidays');
INSERT INTO Occasions (ocid, occasionName, category) VALUES (7, 'Graduation', 'Special Events');

-- Inserting data into the Greenhouses table
INSERT INTO Greenhouses (gid, adress, city, efficiency) VALUES (1, '123 Greenhouse Lane', 'Springfield', 0.85);
INSERT INTO Greenhouses (gid, adress, city, efficiency) VALUES (2, '456 Garden Road', 'Meadowville', 0.92);
INSERT INTO Greenhouses (gid, adress, city, efficiency) VALUES (3, '789 Bloom Street', 'Floral City', 0.78);

-- Inserting data into the Employees table
INSERT INTO Employees (eid, gid, firstName, lastName, salary) VALUES (1, 1, 'John', 'Smith', 2500);
INSERT INTO Employees (eid, gid, firstName, lastName, salary) VALUES (2, 2, 'Alice', 'Johnson', 2200);
INSERT INTO Employees (eid, gid, firstName, lastName, salary) VALUES (3, 1, 'Robert', 'Brown', 2300);
INSERT INTO Employees (eid, gid, firstName, lastName, salary) VALUES (4, 3, 'Sarah', 'Davis', 2100);
INSERT INTO Employees (eid, gid, firstName, lastName, salary) VALUES (5, 2, 'Michael', 'Wilson', 2400);

-- Inserting data into the FlowerData table
-- Details for Roses
INSERT INTO FlowerData (ftid, colour, witherDate, quantity, perfume, symbollism)
VALUES (1, 'Red', '2023-10-20', 50, 'Sweet', 'Love and passion');

-- Details for Tulips
INSERT INTO FlowerData (ftid, colour, witherDate, quantity, perfume, symbollism)
VALUES (2, 'Pink', '2023-10-25', 75, 'Light and fresh', 'Love and cheerfulness');

-- Details for Daisies
INSERT INTO FlowerData (ftid, colour, witherDate, quantity, perfume, symbollism)
VALUES (3, 'White', '2023-10-22', 60, 'Subtle', 'Innocence and purity');

-- Details for Lilies
INSERT INTO FlowerData (ftid, colour, witherDate, quantity, perfume, symbollism)
VALUES (4, 'Yellow', '2023-10-24', 45, 'Delicate', 'Majesty and truth');

-- Details for Sunflowers
INSERT INTO FlowerData (ftid, colour, witherDate, quantity, perfume, symbollism)
VALUES (5, 'Yellow', '2023-10-28', 40, 'Earthy', 'Adoration and loyalty');

-- Details for Orchids
INSERT INTO FlowerData (ftid, colour, witherDate, quantity, perfume, symbollism)
VALUES (6, 'Purple', '2023-10-23', 30, 'Exotic', 'Elegance and refinement');

-- Details for Carnations
INSERT INTO FlowerData (ftid, colour, witherDate, quantity, perfume, symbollism)
VALUES (7, 'Pink', '2023-10-26', 55, 'Spicy', 'Love and gratitude');

-- Details for Hydrangeas
INSERT INTO FlowerData (ftid, colour, witherDate, quantity, perfume, symbollism)
VALUES (8, 'Blue', '2023-10-29', 25, 'Fragrant', 'Understanding and heartfelt emotions');

-- Details for Strelizias
INSERT INTO FlowerData (ftid, colour, witherDate, quantity, perfume, symbollism)
VALUES (9, 'Orange', '2023-11-03', 10, 'Subtle', 'Freedom and immortality');

-- Inserting data into the Flowers table
INSERT INTO Flowers (fid, ftid, flowerName) VALUES (1,1, 'Roses');
INSERT INTO Flowers (fid, ftid, flowerName) VALUES (2,2, 'Tulips');
INSERT INTO Flowers (fid, ftid, flowerName) VALUES (3,3, 'Daisies');
INSERT INTO Flowers (fid, ftid, flowerName) VALUES (4,4, 'Lilies');
INSERT INTO Flowers (fid, ftid, flowerName) VALUES (5,5, 'Sunflowers');
INSERT INTO Flowers (fid, ftid, flowerName) VALUES (6,6, 'Orchids');
INSERT INTO Flowers (fid, ftid, flowerName) VALUES (7,7, 'Carnations');
INSERT INTO Flowers (fid, ftid, flowerName) VALUES (8,8, 'Hydrangeas');
INSERT INTO Flowers (fid, ftid, flowerName) VALUES (9,9, 'Strelizias');

-- Inserting data into the FlowerPlot table
-- Greenhouse 1 with Roses
INSERT INTO FlowerPlot (gid, fid, growthRate, season)
VALUES (1, 1, 2, 'Spring');

-- Greenhouse 2 with Tulips
INSERT INTO FlowerPlot (gid, fid, growthRate, season)
VALUES (2, 2, 3, 'Spring');

-- Greenhouse 1 with Daisies
INSERT INTO FlowerPlot (gid, fid, growthRate, season)
VALUES (1, 3, 1, 'Summer');

-- Greenhouse 3 with Lilies
INSERT INTO FlowerPlot (gid, fid, growthRate, season)
VALUES (3, 4, 4, 'Spring');

-- Greenhouse 2 with Sunflowers
INSERT INTO FlowerPlot (gid, fid, growthRate, season)
VALUES (2, 5, 2, 'Summer');

-- Greenhouse 2 with Roses
INSERT INTO FlowerPlot (gid, fid, growthRate, season)
VALUES (2, 1, 3, 'Spring');

-- Inserting data into the ClientPublic table
INSERT INTO ClientPublic (cid, firstName, lastName, payment, email)
VALUES (1, 'John', 'Doe', 'Credit Card', 'john.doe@example.com');

INSERT INTO ClientPublic (cid, firstName, lastName, payment, email)
VALUES (2, 'Alice', 'Smith', 'PayPal', 'alice.smith@example.com');

INSERT INTO ClientPublic (cid, firstName, lastName, payment, email)
VALUES (3, 'Robert', 'Johnson', 'Cash', 'robert.johnson@example.com');

INSERT INTO ClientPublic (cid, firstName, lastName, payment, email)
VALUES (4, 'Sarah', 'Brown', 'Credit Card', 'sarah.brown@example.com');

INSERT INTO ClientPublic (cid, firstName, lastName, payment, email)
VALUES (5, 'Michael', 'Wilson', 'Bank Transfer', 'michael.wilson@example.com');

-- Inserting data into the ClientPrivate table
-- Client 1
INSERT INTO ClientPrivate (cid, CNP, VX, series, adress, city, dateOfBirth, customerType)
VALUES (1, '1234567890', 'X123456', 'AB', '123 Main Street', 'Springfield', '1990-03-15', 'NATURAL');

-- Client 2
INSERT INTO ClientPrivate (cid, CNP, VX, series, adress, city, dateOfBirth, customerType)
VALUES (2, '6876543210', 'Y654321', 'CD', '456 Elm Avenue', 'Meadowville', '1985-07-20', 'NATURAL');

-- Client 3
INSERT INTO ClientPrivate (cid, CNP, VX, series, adress, city, dateOfBirth, customerType)
VALUES (3, '5456789012', 'Z789012', 'EF', '789 Oak Road', 'Floral City', '1978-12-05', 'NATURAL');

-- Client 4
INSERT INTO ClientPrivate (cid, CNP, VX, series, adress, city, dateOfBirth, customerType)
VALUES (4, '2890123456', 'W345678', 'GH', '234 Maple Lane', 'Springfield', '1982-09-30', 'NATURAL');

-- Client 5
INSERT INTO ClientPrivate (cid, CNP, VX, series, adress, city, dateOfBirth, customerType)
VALUES (5, '5678901234', 'U234567', 'IJ', '567 Pine Street', 'Meadowville', '1995-04-10', 'NATURAL');

-- Inserting data into the Regulars table
-- Regular Client 1
INSERT INTO Regulars (cid, dateOfRegistration, points)
VALUES (1, '2023-03-01', 100);

-- Regular Client 2
INSERT INTO Regulars (cid, dateOfRegistration, points)
VALUES (2, '2023-02-15', 75);


-- Sample entries in the Orders table
-- Order 1
INSERT INTO Orders (fid, cid, ocid, shid, pid, orderName, orderPlacementDate, price)
VALUES (1, 1, 1, 1, 1, 'Wedding Roses', '2023-10-01', 50.99);

-- Order 2
INSERT INTO Orders (fid, cid, ocid, shid, pid, orderName, orderPlacementDate, price)
VALUES (2, 2, 2, 2, 2, 'Anniversary Tulips', '2023-09-15', 45.99);

-- Order 3
INSERT INTO Orders (fid, cid, ocid, shid, pid, orderName, orderPlacementDate, price)
VALUES (3, 3, 3, 3, 3, 'Birthday Daisies', '2023-08-20', 30.99);

-- Order 4
INSERT INTO Orders (fid, cid, ocid, shid, pid, orderName, orderPlacementDate, price)
VALUES (4, 4, 4, 4, 4, 'Funeral Lilies', '2023-09-10', 55.99);

-- Order 5
INSERT INTO Orders (fid, cid, ocid, shid, pid, orderName, orderPlacementDate, price)
VALUES (5, 5, 5, 5, 5, 'Mothers Day Sunflowers', '2023-05-02', 40.99);

-- Order 6
INSERT INTO Orders (fid, cid, ocid, shid, pid, orderName, orderPlacementDate, price)
VALUES (6, 1, 6, 1, 1, 'Valentines Day Orchids', '2023-02-14', 60.99);

-- Order 7
INSERT INTO Orders (fid, cid, ocid, shid, pid, orderName, orderPlacementDate, price)
VALUES (7, 2, 7, 2, 3, 'Graduation Carnations', '2023-06-20', 35.99);

-- Order 8
INSERT INTO Orders (fid, cid, ocid, shid, pid, orderName, orderPlacementDate, price)
VALUES (8, 3, 5, 3, 2, 'Special Occasion Hydrangeas', '2023-07-10', 75.99);

-- Order 9
INSERT INTO Orders (fid, cid, ocid, shid, pid, orderName, orderPlacementDate, price)
VALUES (1, 4, 1, 4, 3, 'Wedding Roses', '2023-09-25', 50.99);

-- Order 10
INSERT INTO Orders (fid, cid, ocid, shid, pid, orderName, orderPlacementDate, price)
VALUES (2, 5, 2, 5, 2, 'Anniversary Tulips', '2023-08-12', 45.99);

--errors
INSERT INTO ClientPublic (cid, firstName, lastName, payment, email)
VALUES (5, 'Michael', 'Wilson', 'Bank Transfer', 'michael.wilson@example.com');

INSERT INTO ClientPrivate (cid, CNP, VX, series, adress, city, dateOfBirth, customerType)
VALUES (5, '8678901234', 'U234567', 'IJ', '567 Pine Street', 'Meadowville', '1995-04-10', 'NATURAL');

INSERT INTO FlowerData (ftid, colour, witherDate, quantity, perfume, symbollism)
VALUES (11, 'Purple', '2023-10-23', -6, 'Exotic', 'Elegance and refinement');