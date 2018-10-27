SELECT * FROM a_carrito WHERE id = 1 AND clave = 2345;
SELECT * FROM a_contiene WHERE carrito = 1 AND producto = 1;
UPDATE a_almacenamiento SET existencias = existencias + 10 WHERE producto = 1 AND sucursal = 1 AND tipo = 'Estante';
DELETE FROM a_contiene WHERE carrito = 1 AND producto = 1;
commit;