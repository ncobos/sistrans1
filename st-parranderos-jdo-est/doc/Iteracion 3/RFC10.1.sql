select *
from a_transaccion t, a_factura f, a_cliente c
where t.numerofactura = f.numero AND f.idcliente = c.id AND t.idproducto = 1 AND f.fecha BETWEEN ('15/06/2018') AND ('24/12/2018') 
ORDER BY t.cantidad DESC;
