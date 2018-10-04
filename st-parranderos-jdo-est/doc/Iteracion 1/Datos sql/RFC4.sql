SELECT DISTINCT p.id, p.nombre, p.marca, p.presentacion, p.codigobarras, p.unidadmedida, p.categoria, p.tipo
FROM a_producto p, a_sucursal s, a_vende v
WHERE p.id = v.idproducto AND s.ciudad = 'Bogotá';