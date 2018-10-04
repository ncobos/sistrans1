UPDATE A_PEDIDO SET estadoorden = 'entregado' WHERE id = 10;
UPDATE A_PEDIDO SET calificacionservicio = 8 WHERE id = 10;
UPDATE A_BODEGA SET existencias = existencias + 5 WHERE producto = 1 AND sucursal = 1;