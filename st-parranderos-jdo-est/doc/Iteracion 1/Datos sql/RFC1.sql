SELECT
     sucursal,
     SUM(costo) as ventas
 FROM
     a_transaccion t,
     a_factura f
 WHERE
     t.numerofactura = f.numero   AND f.fecha BETWEEN '12/10/2000' AND '30/12/2030'
 GROUP BY
     sucursal