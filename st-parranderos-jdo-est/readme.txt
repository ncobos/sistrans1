Pasos para ejecutar la aplicación:


1. Ingrese a: https://github.com/ncobos/sistrans1



2. Copie el link del repositorio para clonarlo y crear un repositorio local



3. Abra la consola del computador y escoja el directorio para crear la carpeta del proyecto, a continuación escriba: 
git clone seguido del link que acaba de copiar.


4. Abra el ambiente de programación Eclipse y en archivo, importe la carpeta que acaba de crear (esta contiene todo el 
proyecto SuperAndes).



5. Antes de correr la interfaz de la aplicación, debe abrir el programa SQL Developer para conectarse a la base de datos 
de esta misma y así poder probar la aplicación.
*Credenciales de la conexión a la base de datos:

Configuración de conexión 
a Oracle:



HOST: fn3.oracle.virtual.uniandes.edu.co  
|Puerto: 1521 
|SID: prod 
| Login Oracle: ISIS2304A101820
                            
| Contraseña Oracle: Ve08gbup0W
   
                             

6. Una vez haya cargado la aplicación, corra como Java Application la clase InterfazParranderosApp la cual corre 
la aplicación SuperAndes.

*El nombre del proyecto así como el de unas clases es Parranderos debido a que en la creación 
individual del proyecto SuperAndes, no fue posible conectar la base de datos en SQL Developer por lo que las funciones de 
la aplicación no funcionaban correctamente. Por esta razón se decidió reutilizar el proyecto de parranderos-jo, 
reemplazando las clases de Parranderos con las de SuperAndes para así implementar el funcionamiento de la misma.
 


7. En este momento ya dispone de las funciones principales de la aplicación. El menú se divide en 4 funcionalidades:

-Requerimientos Funcionales:
 -Adicionar un supermercado
 -Adicionar una promoción
 -Eliminar una promoción
 -
Registrar un pedido
 -Recibir un pedido


-Requerimientos de consulta:
 -Req Ventas y Sucursal
 -Mostrar promociones populares
 -Dar índice ocupación bodegas y 
estantes
 -Dar productos por ciudad
 

-Funcionalidades de limpieza del log y de la BD
-Log


-Documentación
-Acerca De
