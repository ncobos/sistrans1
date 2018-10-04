INSERT INTO A_FACTURA (numero, fecha, idcliente, sucursal) values (101, '09/10/2018', 1, 1);
UPDATE A_ESTANTE SET existencias = existencias - 2 WHERE producto = 1 AND sucursal = 1;
INSERT INTO A_TRANSACCION (idproducto, cantidad, numerofactura, costo, promocion) values (1, 2, 101, 2000, 0);