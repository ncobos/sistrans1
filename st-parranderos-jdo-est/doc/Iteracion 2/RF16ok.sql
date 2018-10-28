SELECT * FROM a_carrito WHERE id = 1 AND clave = 1234;
UPDATE a_carrito SET estado = 'abandonado' WHERE id = 1;
UPDATE a_carrito SET clave = 0 WHERE id = 1;
commit;