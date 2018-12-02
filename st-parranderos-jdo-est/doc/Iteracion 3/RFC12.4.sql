SELECT
   distinct nombre,
     pedidos,
     semana
 FROM
     (
         SELECT
             MIN(cantidad) AS pedidos, to_char(to_date(fecha), 'IW') as semana
         FROM
             (
                 SELECT DISTINCT
                    SUM(s.cantidad) AS cantidad,
                     p.idproveedor as id,
                     ap.nombre as nombre,
                     p.fechaentrega as fecha
                 FROM
                     a_pedido p,
                     a_subpedido s, 
                     a_proveedor ap
                 WHERE
                     p.idproveedor = ap.nit AND p.id = s.idpedido AND p.fechaentrega between '01/01/2019' and '01/08/2019' 
                 group by 
                 ap.nombre, p.idproveedor, p.fechaentrega
                 
                  ORDER BY
                     cantidad DESC) group by to_char(to_date(fecha), 'IW') order by semana asc
                 
             )
      t1,
     (
         SELECT DISTINCT
             SUM(s.cantidad) AS cantidad,
              p.idproveedor as id,
              ap.nombre as nombre,
               p.fechaentrega as fecha
         FROM
            a_pedido p,
            a_subpedido s, 
            a_proveedor ap
         WHERE
             p.idproveedor = ap.nit AND p.id = s.idpedido AND p.fechaentrega between '01/01/2019' and '01/08/2019' 
         GROUP BY
                 ap.nombre, p.idproveedor, p.fechaentrega
     ) t2
where t1.pedidos = t2.cantidad
order by semana asc;     