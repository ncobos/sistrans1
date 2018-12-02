SELECT distinct IDCLIENTE as cliente ,  EXTRACT(MONTH FROM FECHA) AS MES, COUNT(*) AS COMPRAS
                FROM A_FACTURA
                GROUP BY IDCLIENTE, EXTRACT(MONTH FROM FECHA)
                HAVING COUNT(*) >= 1
                order by mes asc