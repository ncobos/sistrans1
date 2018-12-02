SELECT
     distinct nombre,
     ventas,
     semana
 FROM
     ( (SELECT
             MIN(cantidad) AS ventas, to_char(to_date(fecha), 'IW') as semana
         FROM
             (
                 SELECT DISTINCT
                     SUM(t.cantidad) AS cantidad,
                     t.idproducto   AS id,
                     p.nombre       AS nombre,
                     f.fecha AS fecha
                 FROM
                     a_transaccion t,
                     a_producto p,
                     a_factura f
                 WHERE
                     t.idproducto = p.id and t.numerofactura = f.numero and f.fecha between '01/01/2018' and '01/08/2018'
                 GROUP BY
                     p.nombre, t.idproducto, f.fecha
                 ORDER BY
                     cantidad DESC) group by to_char(to_date(fecha), 'IW') order by semana asc
     ) t1),
     (
         SELECT DISTINCT
                     sum(t.cantidad) AS cantidad,
                     t.idproducto   AS id,
                     p.nombre       AS nombre,
                     f.fecha AS fecha
                 FROM
                     a_transaccion t,
                     a_producto p,
                     a_factura f
                 WHERE
                     t.idproducto = p.id and t.numerofactura = f.numero and f.fecha between '01/01/2018' and '01/08/2018'
                 GROUP BY
                     p.nombre, t.idproducto, f.fecha
                 ORDER BY
                     cantidad DESC
     ) t2

where t1.ventas = t2.cantidad
order by semana asc;