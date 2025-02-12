-- Insert into CATEGORIA with discriminator values
INSERT INTO CATEGORIA (ID, NAME, tipo_categoria) VALUES 
(1, 'Salario', 'CATINGRESO'),
(2, 'Intereses', 'CATINGRESO'),
(3, 'Servicios', 'CATINGRESO');

INSERT INTO CATEGORIA (ID, NAME, tipo_categoria) VALUES 
(4, 'Alimentacion', 'CATEGRESO'),
(5, 'Transporte', 'CATEGRESO'),
(6, 'Golosina', 'CATEGRESO'),
(7, 'Guaguas', 'CATEGRESO');

INSERT INTO CATEGORIA (ID, NAME, tipo_categoria) VALUES (1, 'Transferencia', 'CATTRANSFERENCIA');

INSERT INTO Cuenta (ID, NAME, BALANCE) VALUES (1, 'Bco. Pichincha ahorros', 1000.00);
INSERT INTO Cuenta (ID, NAME, BALANCE) VALUES (2, 'Bco. Pacifico corriente', 2000.00);
INSERT INTO Cuenta (ID, NAME, BALANCE) VALUES (3, 'Efectivo', 20.00);
INSERT INTO Cuenta (ID, NAME, BALANCE) VALUES (4, 'Bco. Pichincha corriente', 3000.00);
INSERT INTO Cuenta (ID, NAME, BALANCE) VALUES (5, 'Bco. Pacifico corriente', 4000.00);

