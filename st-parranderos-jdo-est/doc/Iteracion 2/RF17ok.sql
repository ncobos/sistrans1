SELECT * FROM a_carrito WHERE estado = 'abandonado';

SELECT * FROM a_contiene WHERE carrito = 1 AND producto = 1;
UPDATE a_carrito SET estado = 'libre';
UPDATE a_almacenamiento SET existencias = existencias + 10 WHERE producto = 1 AND sucursal = 1 AND tipo = 'Estante';
DELETE FROM a_contiene WHERE carrito = 1 AND producto = 1;

COMMIT;

