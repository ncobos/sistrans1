Pasos para ejecutar la aplicaci�n:


1. Ingrese a: https://github.com/ncobos/sistrans1



2. Copie el link del repositorio para clonarlo y crear un repositorio local



3. Abra la consola del computador y escoja el directorio para crear la carpeta del proyecto, a continuaci�n escriba: 
git clone seguido del link que acaba de copiar.


4. Abra el ambiente de programaci�n Eclipse y en archivo, importe la carpeta que acaba de crear (esta contiene todo el 
proyecto SuperAndes).



5. Antes de correr la interfaz de la aplicaci�n, debe abrir el programa SQL Developer para conectarse a la base de datos 
de esta misma y as� poder probar la aplicaci�n.
*Credenciales de la conexi�n a la base de datos:

Configuraci�n de conexi�n 
a Oracle:



HOST: fn3.oracle.virtual.uniandes.edu.co  
|Puerto: 1521 
|SID: prod 
| Login Oracle: ISIS2304A101820
                            
| Contrase�a Oracle: Ve08gbup0W
   
                             

6. Una vez haya cargado la aplicaci�n, corra como Java Application la clase InterfazParranderosApp la cual corre 
la aplicaci�n SuperAndes.

*El nombre del proyecto as� como el de unas clases es Parranderos debido a que en la creaci�n 
individual del proyecto SuperAndes, no fue posible conectar la base de datos en SQL Developer por lo que las funciones de 
la aplicaci�n no funcionaban correctamente. Por esta raz�n se decidi� reutilizar el proyecto de parranderos-jo, 
reemplazando las clases de Parranderos con las de SuperAndes para as� implementar el funcionamiento de la misma.
 


7. En este momento ya dispone de las funciones principales de la aplicaci�n. El men� se divide en 4 funcionalidades:

-Requerimientos Funcionales:
 -Adicionar un supermercado
 -Adicionar una promoci�n
 -Eliminar una promoci�n
 -
Registrar un pedido
 -Recibir un pedido


-Requerimientos de consulta:
 -Req Ventas y Sucursal
 -Mostrar promociones populares
 -Dar �ndice ocupaci�n bodegas y 
estantes
 -Dar productos por ciudad
 

-Funcionalidades de limpieza del log y de la BD
-Log


-Documentaci�n
-Acerca De
