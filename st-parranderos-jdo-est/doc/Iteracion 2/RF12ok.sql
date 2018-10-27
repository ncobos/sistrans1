SELECT * FROM a_carrito WHERE id = 1;
UPDATE a_carrito SET estado = 'en uso' WHERE id = 1;
UPDATE a_carrito SET clave = 12345 WHERE id = 1;
commit;