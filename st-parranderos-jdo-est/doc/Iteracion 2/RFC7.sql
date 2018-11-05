SELECT mes, anio, MAX(cantidadtotal) as cantidadmaxima
FROM(SELECT  mes, anio, sum(cantidad) as cantidadtotal
FROM(SELECT
     t.idproducto idproducto,
     t.cantidad cantidad,
     t.numerofactura factura,
     t.costo costo,
     EXTRACT(MONTH FROM f.fecha) as mes,
     EXTRACT(YEAR FROM f.fecha) as anio
 FROM
     a_transaccion t,
     a_producto p,
     a_factura f
 WHERE
     t.idproducto = p.id
     AND p.tipo = 'lacteos'
         AND t.numerofactura = f.numero) group by mes, anio
         ORDER BY cantidadtotal DESC) group by mes, anio;
         
SELECT mes, anio, MAX(costototal) as costomaximo
FROM(SELECT  mes, anio, sum(costo) as costototal
FROM(SELECT
     t.idproducto idproducto,
     t.cantidad cantidad,
     t.numerofactura factura,
     t.costo costo,
     EXTRACT(MONTH FROM f.fecha) as mes,
     EXTRACT(YEAR FROM f.fecha) as anio
 FROM
     a_transaccion t,
     a_producto p,
     a_factura f
 WHERE
     t.idproducto = p.id
     AND p.tipo = 'lacteos'
         AND t.numerofactura = f.numero) group by mes, anio
         ORDER BY costototal DESC) group by mes, anio;