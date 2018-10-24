INSERT INTO A_PROMOCION (id , idproducto ,tipo, precio ,descripcion, fechainicio , fechafin , unidadesdisponibles)
VALUES (1, 1, 'porcentaje', 2000, 'descuento del 25% al producto', '20/10/2018', '28/10/2018', 20);

UPDATE A_VENDE
SET preciounitario = 2000
WHERE idproducto = 1;