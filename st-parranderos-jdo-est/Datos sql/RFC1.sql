SELECT
     sucursal,
     SUM(costo) as ventas
 FROM
     a_transaccion t,
     a_factura f
 WHERE
     t.numerofactura = f.numero
 GROUP BY
     sucursal