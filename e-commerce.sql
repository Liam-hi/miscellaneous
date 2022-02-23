CREATE DATABASE IF NOT EXISTS Ollesmobelhus1;
USE Ollesmobelhus1;

DROP TRIGGER IF EXISTS encryption;

CREATE TABLE IF NOT EXISTS Payments (
	payment_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    payment_details VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS Deliveries (
	delivery_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    delivery_description VARCHAR(100) NOT NULL, 
    delivery_date DATE,
    delivery_address VARCHAR(30) NOT NULL,
    delivery_cost INT NOT NULL,
    delivery_type VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS Customers (
	customer_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	customer_name VARCHAR(20) NOT NULL,
    customer_lastname VARCHAR(20) NOT NULL,
    customer_creditlevel DECIMAL(10,2) DEFAULT NULL,
    customer_phone VARCHAR(20),
    customer_ssn VARCHAR(100) NOT NULL COMMENT 'Social Security number', 
    customer_address VARCHAR(50) NOT NULL,
    customer_city VARCHAR(20) NOT NULL,
    customer_country VARCHAR(20) NOT NULL,
    customer_postalcode VARCHAR(10) NOT NULL,
    customer_mail VARCHAR(50) NOT NULL,
    customer_details VARCHAR(100),
    payment_id INT NOT NULL,
    FOREIGN KEY (payment_id) REFERENCES Payments (payment_id)
);

CREATE TRIGGER encryption
BEFORE INSERT ON Customers
FOR EACH ROW
SET NEW.customer_ssn = HEX(aes_encrypt(NEW.customer_ssn, "key123"));

CREATE TABLE IF NOT EXISTS Employees (
	employee_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
	employee_name VARCHAR(20) NOT NULL,
	employee_lastname VARCHAR(40) NOT NULL, 
	employee_role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS Orders (
	order_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    order_date DATE,
    customer_id INT NOT NULL,
    employee_id INT NOT NULL,
    delivery_id INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Customers (customer_id),
    FOREIGN KEY (employee_id) REFERENCES Employees (employee_id),
    FOREIGN KEY (delivery_id) REFERENCES Deliveries (delivery_id)
);

CREATE TABLE IF NOT EXISTS Suppliers (
	supplier_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    supplier_name VARCHAR(20) NOT NULL,
    supplier_phone VARCHAR(30),
    supplier_fax VARCHAR(30),
    supplier_adress VARCHAR(30) NOT NULL,
    supplier_mail VARCHAR(50) NOT NULL, 
    supplier_details VARCHAR(50),
    supperlier_contactperson VARCHAR(50),
    employee_id INT NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES Employees (employee_id)
);

CREATE TABLE IF NOT EXISTS Products (
	product_id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    product_name VARCHAR(50) NOT NULL,
    product_price INT NOT NULL,
    product_brand VARCHAR(50),
    product_series VARCHAR(50),
    product_quantity INT NOT NULL,
    product_category VARCHAR(50),
    product_weight VARCHAR(20),
    product_coluor VARCHAR(20), 
    product_depth VARCHAR(20),
    product_height VARCHAR(20),
    product_width VARCHAR(20),
    product_material VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS OrderDetails(
	order_id INT,
    product_id INT,
    quantity INT,
    unit_price INT,
    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES Orders (order_id),
    FOREIGN KEY (product_id) REFERENCES Products (product_id)
);

INSERT INTO Payments (payment_details) VALUES
("E-faktura"),
("E-faktura"),
("Direkt betalning"),
("Direkt betalning"),
("E-faktura"),
("Direkt betalning"),
("E-faktura"),
("E-faktura"),
("E-Faktura"),
("Delbetalning med Klarna"),
("E-faktura"),
("Delbetalning med Klarna");

INSERT INTO Products (product_name, product_price, product_brand, product_series, product_quantity, product_category, product_weight, product_coluor, product_depth, product_height, product_width, product_material) VALUES
("Nordik Dining Chair", 1380, "Nordik", "Select21", 100, "Chairs & Stools", "2.3 kg", "Black", "450 mm", "430", "800 mm", "Ash" );

INSERT INTO Products (product_name, product_price, product_brand, product_series, product_quantity, product_category, product_weight, product_coluor, product_depth, product_height, product_width, product_material) VALUES
("Wall mount", 290, "Meraki", "", 100, "Storage", "0.2 g", "Black", "58 mm", "58 mm", "67 mm", "Ash" ),
("String Pocket Shelf", 1100, "String", "String Pocket", 100, "Shelves", "3.2 kg", "Ash Wood", "150 mm", "500 mm", "600 mm", "Ash" ),
("The Round Dorm", 1997, "Ferm Living KIDS", "Objects", 200, "Shelves", "0.4 kg", "Wood", "", "", "", "Wood" ),
("Mr. Oulsen Lounge Chair", 29999, "Hans Olsen", "Warm Nordic", 7, "Armchairs", "25 kg", "Green", "790 mm", "780 mm", "810 mm", "Leather" ),
("Plant Box Flower Shelf", 4000, "Ferm Living", "Plant Box", 300, "Tables", "0.9 kg", "Light Grey", "250 mm", "750 mm", "800 mm", "Metal" ),
("Hitch Console Table", 7580, "Muubs", "", 100, "Tables", "2 kg", "Antique Brass", "300 mm", "900 mm", "1100 mm", "Iron" ),
("Bill XS Hanger", 510, "Maze", "0", 500, "Hooks & Hangers", "0.1 kg", "White", "", "", "", "Wood" ),
("Hello Sunshine Pouf", 720, "Roommate", "0", 100, "Chairs & Stools", "0.5 kg", "Grey", "370 mm", "400 mm", "", "Cotton" ),
("Knot XL Pouf", 7500, "", "Design House Stockholm", 100, "Chairs & Stools", "0.5 kg", "Black", "450 mm", "430 mm", "800 mm", "Cotton" );

INSERT INTO Customers (customer_name, customer_lastname, customer_creditlevel, customer_phone, customer_ssn, customer_address, customer_city, customer_country, customer_postalcode, customer_mail, customer_details, payment_id) VALUES
("Peter", "Johnsson", 7, "070-5663847", HEX(aes_encrypt("7509120506", "key123")), "Krattevägen 3", "Torsåker", "Sweden", "81396", "johnsson@aol.com", "", 1);

INSERT INTO Customers (customer_name, customer_lastname, customer_creditlevel, customer_phone, customer_ssn, customer_address, customer_city, customer_country, customer_postalcode, customer_mail, customer_details, payment_id) VALUES
("Mikaela", "Holmborg", 2, "073-7201420", HEX(aes_encrypt("8104201314", "key123")), "Arnövägen 44", "Bålsta", "Sweden", "74693", "holmborg@gmail.se", "", 2),
("Amanda", "Andersson", 5, "076-3099321", HEX(aes_encrypt("9507024563", "key123")), "Bäckmansvägen 2", "Orsa", "Sweden", "79490", "amanda.a@gmail.se", "", 3),
("Alexander", "Wiklund", 2, "073-2049581", HEX(aes_encrypt("8806134503", "key123")), "Njurunda 9 D", "Gnarp", "Sweden", "86241", "badman77@gmail.com", "", 4),
("Anders", "Linde", 9, "073-0731875", HEX(aes_encrypt("5501022319", "key123")), "Albacksvägen 8", "Ambjörby", "Sweden", "68052", "mathador@hotmail.se", "", 5);

INSERT INTO Employees (employee_name, employee_lastname, employee_role) VALUES
("Julius", "Thomsen", "Försäljningschef"),
("Olle", "Hammarström", "VD"),
("Mats", "Persson", "Säljare"),
("Cecilia", "Ekström", "Säljare"),
("Jonas", "Söderlund", "Inköpsassistent"),
("Lisa", "Karlsson", "Inköpsansvarig"),
("Carl", "Palm", "Säljare"),
("Johanna", "Söderlund", "Inköpsansvarig"),
("Berthold", "Åsebring", "Säljare"),
("Anton", "Eriksson", "Säljare");

INSERT INTO Suppliers (supplier_name, supplier_phone, supplier_fax, supplier_adress, supplier_mail, supplier_details, supperlier_contactperson, employee_id) VALUES 
("Johns Trä", "0850322332", "saknas", "Hjortv. 31", "johns@traed.se", "material", "Jonas Potterström", 3),
("Jacob co", "083848132", "00011143", "Palatsv. 22", "inkop@jakobco.se", "möbler", "Lisa Andersson", 3),
("Britts möbelhus", "0850509121", "saknas", "Avantv. 64", "sofie@britts.se", "material", "Albin Stenfeldt", 5),
("Carin möbelservice", "4673399923", "saknas", "Hapelv.231", "Carin@msn.se", "material", "Britt-Marie Korforfen", 3);

INSERT INTO Deliveries (delivery_description, delivery_date, delivery_address, delivery_cost, delivery_type) VALUES
("", current_date(), "Skepphsholmen 33", 33, "PostNord"),
("Fragile", current_date(), "Lundgatan 19 194 75", 33, "PostNord");

INSERT INTO Orders (order_id, order_date, customer_id, employee_id, delivery_id) VALUES
(1, current_date(), 1, 3, 1);
INSERT INTO Orders (order_id, order_date, customer_id, employee_id, delivery_id) VALUES
(2, current_date(), 2, 4, 2);

SET autocommit = 0;
START TRANSACTION;

 SELECT @newOrderId := MAX(order_id) +1 FROM Orders;
 SELECT @customer := customer_id FROM customers where customer_name = "Amanda";
 
  SELECT @employee := employee_id FROM employees where employee_name = "Julius";
  
 
 
 INSERT INTO orders (order_id, order_date, customer_id, employee_id, delivery_id) VALUES
 (@newOrderId, current_date(), @customer, @employee, 1);
 
 SELECT @quantity := product_quantity FROM Products WHERE product_id = 2;
 
 CALL new_procedure(5, @quantity);
 
 UPDATE Products
 
 SET product_quantity = @quantity - 5 WHERE product_id = 2;
 
 SELECT @price := product_price FROM Products WHERE product_id = 2;
 
 INSERT INTO orderdetails (order_id, product_id, quantity, unit_price) VALUES
 (@newOrderId, 2, 5, @price * 5);
 
 SELECT * FROM Orderdetails;
 
 SELECT * FROM Products;
 
 
 

COMMIT;
SET autocommit = 1;
