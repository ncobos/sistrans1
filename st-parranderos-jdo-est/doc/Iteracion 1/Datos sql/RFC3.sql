SELECT b.id as id_bodega, b.existencias/b.capacidadproductos as ratio_bodega, e.id as id_estante, e.existencias/e.capacidadproductos as ratio_estante
FROM a_bodega b, a_estante e
WHERE b.sucursal = e.sucursal;
