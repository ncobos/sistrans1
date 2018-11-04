SELECT * FROM a_carrito WHERE id = 1 AND clave = 2345;

INSERT INTO A_FACTURA (numero, fecha, idcliente, sucursal) values (101, '09/10/2018', 1, 1);
INSERT INTO A_TRANSACCION (idproducto, cantidad, numerofactura, costo, promocion, estado) values (1, 2, 101, 2000, 0);
DELETE FROM A_CONTIENE WHERE carrito = 1 AND producto = 1;

UPDATE A_ALMACENAMIENTO SET existencias = existencias - 2 WHERE producto = 1 AND sucursal = 1 AND tipo = 'Estante';

INSERT INTO A_PEDIDO(id , idproveedor , idsucursal , fechaentrega , estadoorden , costototal , calificacionservicio )
VALUES(1, 1, 1, '05/11/2018', 'pendiente', 50000, 5);

INSERT INTO A_SUBPEDIDO(idpedido , idproducto , costo , cantidad )
VALUES(1,1,3000,5);
