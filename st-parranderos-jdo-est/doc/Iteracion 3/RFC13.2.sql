select f.idcliente as cliente, t.idproducto as producto, (t.costo/t.cantidad) as precio
         from a_factura f, a_transaccion t
         where f.numero = t.numerofactura and (t.costo/t.cantidad) > 100000
         order by cliente asc