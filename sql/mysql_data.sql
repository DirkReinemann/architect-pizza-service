USE architectpizza
;

INSERT INTO users (id, email, password, firstname, lastname, zip, street, phone, role) VALUES
(2, 'marcel.mueller@test.de', '1f7d8737f70b022f518d7181ec439a24f09ca41df0b9a0cfc8b2622da537a10e', 'Marcel', 'Müller', '28615', 'Berner Straße 9', '67819400', 'USER'),
(3, 'heinz.frech@test.de', '7f508eb48af994ebd62614945cedafffea9e99b5cab9bb5dc5f158c4d918b8a0', 'Heinz', 'Frech', '91735', 'Brussenstraße 48', '78642931', 'USER'),
(4, 'matthias.knoll@test.de', '05a642215a9ffabdf33cdcb5fb0e909fe8e51e662fb792d4f94d423982a32cb6', 'Matthias', 'Knoll', '84365', 'Wartstraße 17', '83763883', 'USER'),
(5, 'josephine.gerhard@test.de', 'aec0e78824de1205cf98384780983c6f2b7c03f8a6e5036a508ece76f1b3c5a7', 'Josephine', 'Gerhard', '92765', 'Kieler Weg 100', '18326529', 'USER'),
(6, 'anne.brunn@test.de', '90b8de4051f02b7a29484341f3a903e1b2c6a233f5465e19c634535c7b315e6f', 'Anne', 'Brunn', '15387', 'Friesenstraße 1', '83719839', 'USER')
;

INSERT INTO orders (id, orderstate, ordertime, deliverytime, payment, iduser) VALUES
(1, 'DELIVERED', '2012-01-24 17:45:00', '2012-01-24 18:15:15', 'CASH', 1),
(2, 'PREPARED', '2012-01-10 22:07:21', NULL, 'CASH', 2),
(3, 'ORDERED', '2012-01-14 19:48:23', NULL, 'CASH', 3),
(4, 'DELIVERED', '2012-01-07 22:45:01', '2012-01-07 23:10:48', 'CASH', 4),
(5, 'PREPARED', '2012-01-05 18:18:00', NULL, 'CASH', 5)
;

INSERT INTO pizza(id) VALUES
(1),
(2),
(3),
(4),
(5),
(6),
(7),
(8),
(9),
(10)
;

INSERT INTO pizzaingredient(idpizza, idingredient) VALUES
(1, 3),
(1, 9),
(2, 28),
(2, 4),
(2, 1),
(2, 17),
(3, 5),
(3, 27),
(3, 29),
(3, 25),
(4, 2),
(5, 3),
(5, 2),
(5, 12),
(5, 19),
(6, 5),
(6, 26),
(6, 20),
(7, 6),
(7, 15),
(8, 28),
(8, 5),
(8, 2),
(8, 15),
(9, 30),
(9, 20),
(9, 5),
(9, 9),
(10, 29),
(10, 26),
(10, 27),
(10, 30)
;

INSERT INTO orderposition(id, idpizza, idorder, amount) VALUES
(1, 1, 1, 1),
(2, 2, 2, 2),
(3, 3, 2, 1),
(4, 4, 2, 3),
(5, 5, 3, 1),
(6, 6, 3, 1),
(7, 7, 4, 1),
(8, 8, 4, 2),
(9, 9, 4, 2),
(10, 10, 5, 2)
;

CALL generate_pizza_of_the_week()
;