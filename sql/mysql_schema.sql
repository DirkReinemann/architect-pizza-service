CREATE DATABASE architectpizza DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci
;

CREATE USER 'architectpizza'@'localhost' IDENTIFIED BY 'architectpizza'
;

GRANT ALL ON architectpizza.* TO 'architectpizza'@'localhost'
;

USE architectpizza
;

CREATE TABLE users (
  id INT NOT NULL AUTO_INCREMENT,
  email VARCHAR(30) COLLATE utf8_unicode_ci NOT NULL,
  password VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
  firstname VARCHAR(30) COLLATE utf8_unicode_ci NOT NULL,
  lastname VARCHAR(30) COLLATE utf8_unicode_ci NOT NULL,
  street VARCHAR(100) COLLATE utf8_unicode_ci NOT NULL,
  zip VARCHAR(30) COLLATE utf8_unicode_ci NOT NULL,
  phone VARCHAR(30) COLLATE utf8_unicode_ci NOT NULL,
  role VARCHAR(10) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
;

CREATE TABLE orders (
  id INT NOT NULL AUTO_INCREMENT,
  orderState VARCHAR(50) COLLATE utf8_unicode_ci NOT NULL,
  ordertime VARCHAR(50) COLLATE utf8_unicode_ci NOT NULL,
  deliverytime VARCHAR(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  payment VARCHAR(30) COLLATE utf8_unicode_ci NOT NULL,
  addition VARCHAR(30) COLLATE utf8_unicode_ci DEFAULT NULL,
  iduser INT NOT NULL,
  PRIMARY KEY (id),
  KEY fk_users (iduser),
  CONSTRAINT fk_orders_users FOREIGN KEY (iduser) REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
;

CREATE TABLE pizza (
  id INT NOT NULL AUTO_INCREMENT,
  week BIT NOT NULL DEFAULT 0,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
;

CREATE TABLE orderposition (
  id INT NOT NULL AUTO_INCREMENT,
  idpizza INT NOT NULL,
  idorder INT NOT NULL,
  amount INT NOT NULL DEFAULT 1,
  KEY fk_pizza (idpizza),
  KEY fk_order (idorder),
  CONSTRAINT fk_orderpizza_pizza FOREIGN KEY (idpizza) REFERENCES pizza (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_orderpizza_order FOREIGN KEY (idorder) REFERENCES orders (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
; 

CREATE TABLE category (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(30) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
;

CREATE TABLE ingredient (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(30) COLLATE utf8_unicode_ci NOT NULL,
  price DOUBLE NOT NULL,
  idcategory INT NOT NULL,
  KEY fk_category (idcategory),
  CONSTRAINT fk_ingredient_category FOREIGN KEY (idcategory) REFERENCES category (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
;

CREATE TABLE pizzaingredient (
  idpizza INT NOT NULL,
  idingredient INT NOT NULL,
  KEY fk_pizza (idpizza),
  KEY fk_ingredient (idingredient),
  CONSTRAINT fk_pizzaingredient_pizza FOREIGN KEY (idpizza) REFERENCES pizza (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_pizzaingredient_ingredient FOREIGN KEY (idingredient) REFERENCES ingredient (id) ON DELETE RESTRICT ON UPDATE CASCADE,
  PRIMARY KEY (idpizza, idingredient)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
;

CREATE TABLE newsletter (
  id INT NOT NULL AUTO_INCREMENT,
  email VARCHAR(30) COLLATE utf8_unicode_ci NOT NULL,
  state VARCHAR(30) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci
;

/**
delimiter //
*/

CREATE PROCEDURE generate_pizza_of_the_week()
BEGIN
	DECLARE i INT;
	
	UPDATE pizza SET week=0;
	
	SELECT id INTO i FROM pizza ORDER BY RAND() LIMIT 1;
	UPDATE pizza SET week=1 WHERE id=i;
END
;
/*
//
delimiter ;
*/

INSERT INTO users(id, email, password, firstname, lastname, street, zip, phone, role) VALUES
(1, 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'ADMIN', 'ADMIN', 'ADMIN', 'ADMIN', 'ADMIN', 'ADMIN')
;

INSERT INTO category(id, name) VALUES
(1, 'MEAT'),
(2, 'FISH'),
(3, 'VEGETABLES'),
(4, 'SAUCES')
;

INSERT INTO ingredient(id, name, price, idcategory) VALUES
(1, 'Bacon', 1, 1),
(2, 'Salami', 1, 1),
(3, 'Ham', 1, 1),
(4, 'Minced Meat', 1, 1),
(5, 'Chicken Strips', 1, 1),
(6, 'Tuna', 1, 2),
(7, 'Crab', 1, 2),
(8, 'Anchovies', 1, 2),
(9, 'Pineapple', 1, 3),
(10, 'Spinat', 1, 3),
(11, 'Paprika', 1, 3),
(12, 'Mushrooms', 1, 3),
(13, 'Olives', 1, 3),
(14, 'Egg', 1, 3),
(15, 'Onions', 1, 3),
(16, 'Broccoli', 1, 3),
(17, 'Artichokes', 1, 3),
(18, 'Pepperoni', 1, 3),
(19, 'Mozzarella', 1, 3),
(20, 'Corn', 1, 3),
(21, 'Chili Peppers', 1, 3),
(22, 'Gorgonzola', 1, 3),
(23, 'Garlic', 1, 3),
(24, 'Tomatoes', 1, 3),
(25, 'Crème Fraiche', 0.50, 4),
(26, 'Barbeceau Sauce', 0.50, 4),
(27, 'Spinach', 1, 3),
(28, 'Curry Sauce', 0.50, 4),
(29, 'Scampi', 1, 2),
(30, 'Sauce Hollandaise', 0.50, 4)
;