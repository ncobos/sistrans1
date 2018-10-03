SELECT promocion, (numero/fecha) as ratio
FROM(SELECT
     promocion,
     (fechafin - fechainicio) as fecha,
     COUNT (promocion) as numero
 FROM
     a_transaccion t,
     a_promocion p
 WHERE
      t.promocion = p.id AND ROWNUM <= 20
 GROUP BY
     promocion, (fechafin - fechainicio))