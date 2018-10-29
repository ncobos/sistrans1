SELECT
     t.table_name NombreTabla,
     t.column_name NombreColumna,
     t.data_type TipoDato,
     c.constraint_name NombreRestriccion
 FROM
     all_tab_columns t,
     all_cons_columns c
 WHERE
     t.table_name =  c.table_name AND
     t.column_name = c.column_name AND c.owner = 'ISIS2304A101820';
     
