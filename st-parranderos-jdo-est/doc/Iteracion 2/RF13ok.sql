SELECT * FROM a_carrito WHERE id = 1 AND clave = 2345;

INSERT INTO A_CONTIENE (carrito, producto, cantidad)
VALUES (1, 3, 5);

UPDATE a_almacenamiento SET existencias = existencias - 5 WHERE producto = 3 AND sucursal = 1 AND tipo = 'Estante';
COMMIT;

