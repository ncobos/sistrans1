--- Sentencias SQL para la creacion del esquema de superandes
--- Las tablas tienen prefijo A_ para facilitar su acceso desde SQL Developer

-- USO
-- Copie el contenido deseado de este archivo en una pestaña SQL de SQL Developer
-- Ejecútelo como un script - Utilice el botón correspondiente de la pestaña utilizada
    
-- Eliminar todas las tablas de la base de datos
DROP TABLE "A_SUPERMERCADO" CASCADE CONSTRAINTS;
DROP TABLE "A_SUCURSAL" CASCADE CONSTRAINTS;
DROP TABLE "A_PRODUCTO" CASCADE CONSTRAINTS;
DROP TABLE "A_ALMACENAMIENTO" CASCADE CONSTRAINTS;
DROP TABLE "A_VENDE" CASCADE CONSTRAINTS;
DROP TABLE "A_PROVEEDOR" CASCADE CONSTRAINTS;
DROP TABLE "A_PEDIDO" CASCADE CONSTRAINTS;
DROP TABLE "A_CLIENTE" CASCADE CONSTRAINTS;
DROP TABLE "A_OFRECEN" CASCADE CONSTRAINTS;
DROP TABLE "A_FACTURA" CASCADE CONSTRAINTS;
DROP TABLE "A_CARRITO" CASCADE CONSTRAINTS;
DROP TABLE "A_PROMOCION" CASCADE CONSTRAINTS;
DROP TABLE "A_TRANSACCION" CASCADE CONSTRAINTS;

COMMIT;

-- Eliminar el contenido de todas las tablas de la base de datos
-- El orden es importante.

delete from a_subpedido;
delete from a_transaccion;
delete from a_promocion;
delete from a_carrito;
delete from a_factura;
delete from a_ofrecen;
delete from a_cliente;
delete from a_pedido;
delete from a_proveedor;
delete from a_vende;
delete from a_almacenamiento;
delete from a_producto;
delete from a_sucursal;
delete from a_supermercado;

commit;



