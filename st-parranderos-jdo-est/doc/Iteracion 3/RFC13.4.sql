select f.idcliente as cliente, t.idproducto as producto, t.costo as precio, p.categoria as categoria
         from a_factura f, a_transaccion t, a_producto p
         where f.numero = t.numerofactura and t.idproducto = p.id and p.categoria = 'electrodomésticos'
         order by cliente asc