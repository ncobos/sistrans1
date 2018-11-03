--- Sentencias SQL para la creaci�n del esquema de SuperAndes
--- Las tablas tienen prefijo A_ para facilitar su acceso desde SQL Developer

-- USO
-- Copie el contenido de este archivo en una pesta�a SQL de SQL Developer
-- Ejec�telo como un script - Utilice el bot�n correspondiente de la pesta�a utilizada

-- Creaci�n del secuenciador
CREATE SEQUENCE superandes_sequence;

-- Creaci�n de la tabla supermercado y especificaci�n de sus restricciones.

CREATE TABLE a_supermercado (
    nombre   VARCHAR2(255 BYTE),
    CONSTRAINT a_supermercado_pk PRIMARY KEY ( nombre )
);
   
-- Creaci�n de la tabla SUCURSAL y especificaci�n de sus restricciones

CREATE TABLE a_sucursal (
    id                NUMBER,
    nombre            VARCHAR2(255 BYTE) NOT NULL,
    ciudad            VARCHAR2(255 BYTE) NOT NULL,
    direccion         VARCHAR2(255 BYTE) NOT NULL,
    segmentomercado   VARCHAR2(255 BYTE) NOT NULL,
    tamano            NUMBER NOT NULL,
    supermercado      VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT a_sucursal_pk PRIMARY KEY ( id )
);

ALTER TABLE a_sucursal
    ADD CONSTRAINT fk_s_supermercado FOREIGN KEY ( supermercado )
        REFERENCES a_supermercado ( nombre )
    ENABLE;

ALTER TABLE a_sucursal ADD CONSTRAINT ck_s_tamano CHECK ( tamano > 0 ) ENABLE;



-- Creaci�n de la tabla producto y especificaci�n de sus restricciones.

CREATE TABLE a_producto (
    id             NUMBER,
    nombre         VARCHAR2(255 BYTE) NOT NULL,
    marca          VARCHAR2(255 BYTE) NOT NULL,
    presentacion   VARCHAR2(255 BYTE) NOT NULL,
    codigobarras   VARCHAR2(255 BYTE) NOT NULL,
    unidadmedida   VARCHAR2(255 BYTE) NOT NULL,
    categoria      VARCHAR2(255 BYTE) NOT NULL,
    tipo           VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT a_producto_pk PRIMARY KEY ( id )
);


-- Creaci�n de la tabla almacenamiento y especificaci�n de sus restricciones.

CREATE TABLE a_almacenamiento (
    id                          NUMBER,
    capacidadvolumen            NUMBER NOT NULL,
    capacidadpeso               NUMBER NOT NULL,
    producto                    NUMBER NOT NULL,
    sucursal                    NUMBER NOT NULL,
    nivelabastecimientobodega   NUMBER NOT NULL,
    existencias                 NUMBER NOT NULL,
    capacidadproductos          NUMBER NOT NULL,
    tipo                        VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT a_almacenamiento_pk PRIMARY KEY ( id )
);

ALTER TABLE a_almacenamiento
    ADD CONSTRAINT fk_al_producto FOREIGN KEY ( producto )
        REFERENCES a_producto ( id )
    ENABLE;

ALTER TABLE a_almacenamiento
    ADD CONSTRAINT fk_al_sucursal FOREIGN KEY ( sucursal )
        REFERENCES a_sucursal ( id )
    ENABLE;

ALTER TABLE a_almacenamiento ADD CONSTRAINT ck_al_volumen CHECK ( capacidadvolumen > 0 ) ENABLE;

ALTER TABLE a_almacenamiento ADD CONSTRAINT ck_al_peso CHECK ( capacidadpeso > 0 ) ENABLE;

ALTER TABLE a_almacenamiento ADD CONSTRAINT ck_al_capprod CHECK ( capacidadproductos > 0 ) ENABLE;

ALTER TABLE a_almacenamiento
    ADD CONSTRAINT ck_al_abastecimiento CHECK ( nivelabastecimientobodega >-1 ) ENABLE;

ALTER TABLE a_almacenamiento
    ADD CONSTRAINT ck_al_existencias CHECK ( existencias >-1 ) ENABLE;

ALTER TABLE a_almacenamiento
    ADD CONSTRAINT ck_almacenamiento_tipo CHECK ( tipo IN (
        'Bodega',
        'Estante'
    ) ) ENABLE;



-- Creaci�n de la tabla vende y especificaci�n de sus restricciones.

CREATE TABLE a_vende (
    idsucursal           NUMBER,
    idproducto           NUMBER NOT NULL,
    nivelreorden         NUMBER NOT NULL,
    preciounitario       NUMBER NOT NULL,
    preciounidadmedida   NUMBER NOT NULL,
    CONSTRAINT a_vende_pk PRIMARY KEY ( idsucursal,
                                        idproducto,
                                        nivelreorden,
                                        preciounitario,
                                        preciounidadmedida )
);

ALTER TABLE a_vende
    ADD CONSTRAINT fk_v_sucursal FOREIGN KEY ( idsucursal )
        REFERENCES a_sucursal ( id )
    ENABLE;

ALTER TABLE a_vende
    ADD CONSTRAINT fk_v_producto FOREIGN KEY ( idproducto )
        REFERENCES a_producto ( id )
    ENABLE;

ALTER TABLE a_vende ADD CONSTRAINT ck_v_preciou CHECK ( preciounitario > 0 ) ENABLE;

ALTER TABLE a_vende ADD CONSTRAINT ck_v_preciom CHECK ( preciounidadmedida > 0 ) ENABLE;

ALTER TABLE a_vende ADD CONSTRAINT ck_v_nivel CHECK ( nivelreorden > 0 ) ENABLE;



-- Creaci�n de la tabla proveedor y especificaci�n de sus restricciones.

CREATE TABLE a_proveedor (
    nit            NUMBER,
    nombre         VARCHAR2(255 BYTE) NOT NULL,
    calificacion   NUMBER NOT NULL,
    CONSTRAINT a_proveedor_pk PRIMARY KEY ( nit )
);

ALTER TABLE a_proveedor ADD CONSTRAINT ck_p_calificaciona CHECK ( calificacion > 0 ) ENABLE;

ALTER TABLE a_proveedor ADD CONSTRAINT ck_p_calificacionb CHECK ( calificacion < 11 ) ENABLE;



-- Creaci�n de la tabla pedido y especificaci�n de sus restricciones.

CREATE TABLE a_pedido (
    id                     NUMBER,
    idsucursal             NUMBER NOT NULL,
    idproveedor            NUMBER NOT NULL,
    fechaentrega           DATE NOT NULL,
    estadoorden            VARCHAR2(255 BYTE) NOT NULL,
    calificacionservicio   NUMBER,
    costototal             NUMBER NOT NULL,
    CONSTRAINT a_pedido_pk PRIMARY KEY ( id )
);

ALTER TABLE a_pedido
    ADD CONSTRAINT fk_ped_sucursal FOREIGN KEY ( idsucursal )
        REFERENCES a_sucursal ( id )
    ENABLE;

ALTER TABLE a_pedido
    ADD CONSTRAINT fk_ped_proveedor FOREIGN KEY ( idproveedor )
        REFERENCES a_proveedor ( nit )
    ENABLE;

ALTER TABLE a_pedido ADD CONSTRAINT ck_ped_calificacion CHECK ( calificacionservicio > 0 ) ENABLE;

ALTER TABLE a_pedido ADD CONSTRAINT ck_ped_costo CHECK ( costototal > 0 ) ENABLE;

ALTER TABLE a_pedido
    ADD CONSTRAINT ck_ped_estado CHECK ( estadoorden IN (
        'pendiente',
        'entregado'
    ) ) ENABLE;


-- Creaci�n de la tabla subpedido y especificaci�n de sus restricciones.

CREATE TABLE a_subpedido (
    idpedido     NUMBER NOT NULL,
    idproducto   NUMBER NOT NULL,
    cantidad     NUMBER NOT NULL,
    costo        NUMBER NOT NULL,
    CONSTRAINT a_subpedido_pk PRIMARY KEY ( idpedido,
                                            idproducto,
                                            cantidad,
                                            costo )
);

ALTER TABLE a_subpedido
    ADD CONSTRAINT fk_sub_producto FOREIGN KEY ( idproducto )
        REFERENCES a_producto ( id )
    ENABLE;

ALTER TABLE a_subpedido
    ADD CONSTRAINT fk_sub_pedido FOREIGN KEY ( idpedido )
        REFERENCES a_pedido ( id )
    ENABLE;

ALTER TABLE a_subpedido ADD CONSTRAINT ck_sub_costo CHECK ( costo > 0 ) ENABLE;

ALTER TABLE a_subpedido ADD CONSTRAINT ck_sub_cantidad CHECK ( cantidad > 0 ) ENABLE;


-- Creaci�n de la tabla ofrecen y especificaci�n de sus restricciones.

CREATE TABLE a_ofrecen (
    idproducto    NUMBER,
    idproveedor   NUMBER NOT NULL,
    costo         NUMBER NOT NULL,
    CONSTRAINT a_ofrece_pk PRIMARY KEY ( idproducto,
                                         idproveedor,
                                         costo )
);

ALTER TABLE a_ofrecen
    ADD CONSTRAINT fk_o_producto FOREIGN KEY ( idproducto )
        REFERENCES a_producto ( id )
    ENABLE;

ALTER TABLE a_ofrecen
    ADD CONSTRAINT fk_o_proveedor FOREIGN KEY ( idproveedor )
        REFERENCES a_proveedor ( nit )
    ENABLE;

ALTER TABLE a_ofrecen ADD CONSTRAINT ck_o_costo CHECK ( costo > 0 ) ENABLE;


-- Creaci�n de la tabla cliente y especificaci�n de sus restricciones.

CREATE TABLE a_cliente (
    id          VARCHAR2(255 BYTE),
    nombre      VARCHAR2(255 BYTE) NOT NULL,
    correo      VARCHAR2(255 BYTE) NOT NULL,
    direccion   VARCHAR2(255 BYTE),
    tipo        VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT a_cliente_pk PRIMARY KEY ( id )
);

ALTER TABLE a_cliente
    ADD CONSTRAINT ck_cliente_tipo CHECK ( tipo IN (
        'persona',
        'empresa'
    ) ) ENABLE;


-- Creaci�n de la tabla factura y especificaci�n de sus restricciones.

CREATE TABLE a_factura (
    numero      NUMBER,
    fecha       DATE NOT NULL,
    idcliente   VARCHAR2(255 BYTE) NOT NULL,
    sucursal    NUMBER NOT NULL,
    CONSTRAINT a_factura_pk PRIMARY KEY ( numero )
);

ALTER TABLE a_factura
    ADD CONSTRAINT fk_factura_cliente FOREIGN KEY ( idcliente )
        REFERENCES a_cliente ( id )
    ENABLE;

ALTER TABLE a_factura
    ADD CONSTRAINT fk_factura_sucursal FOREIGN KEY ( sucursal )
        REFERENCES a_sucursal ( id )
    ENABLE;


-- Creaci�n de la tabla carrito y especificaci�n de sus restricciones.

CREATE TABLE a_carrito (
    id       NUMBER,
    estado   VARCHAR2(255 BYTE) NOT NULL,
    clave    NUMBER NOT NULL,
    sucursal NUMBER NOT NULL,
    CONSTRAINT a_carrito_pk PRIMARY KEY ( id )
);

ALTER TABLE a_carrito
    ADD CONSTRAINT ck_carrito_estado CHECK ( estado IN (
        'en uso',
        'abandonado',
        'pagado',
        'libre'
    ) ) ENABLE;

ALTER TABLE a_carrito
    ADD CONSTRAINT ck_car_clave CHECK ( clave >-1 ) ENABLE;
    
ALTER TABLE a_carrito
    ADD CONSTRAINT fk_c_sucursal FOREIGN KEY ( sucursal )
        REFERENCES a_sucursal ( id )
    ENABLE;


-- Creaci�n de la tabla ofrecen y especificaci�n de sus restricciones.

CREATE TABLE a_contiene (
    producto   NUMBER,
    carrito    NUMBER NOT NULL,
    cantidad   NUMBER NOT NULL,
    sucursal   NUMBER NOT NULL,
    CONSTRAINT a_contiene_pk PRIMARY KEY ( producto,
                                           carrito,
                                           cantidad )
);

ALTER TABLE a_contiene
    ADD CONSTRAINT fk_contiene_producto FOREIGN KEY ( producto )
        REFERENCES a_producto ( id )
    ENABLE;

ALTER TABLE a_contiene
    ADD CONSTRAINT fk_c_carrito FOREIGN KEY ( carrito )
        REFERENCES a_carrito ( id )
    ENABLE;
    
ALTER TABLE a_contiene
    ADD CONSTRAINT fk_co_sucursal FOREIGN KEY ( sucursal )
        REFERENCES a_sucursal ( id )
    ENABLE;

ALTER TABLE a_contiene ADD CONSTRAINT ck_contiene_cantidad CHECK ( cantidad > 0 ) ENABLE;

-- Creaci�n de la tabla promoci�n y especificaci�n de sus restricciones.

CREATE TABLE a_promocion (
    id                    NUMBER,
    idproducto            NUMBER NOT NULL,
    precio                NUMBER NOT NULL,
    descripcion           VARCHAR2(255 BYTE) NOT NULL,
    fechainicio           DATE NOT NULL,
    fechafin              DATE NOT NULL,
    tipo                  VARCHAR2(255 BYTE) NOT NULL,
    unidadesdisponibles   NUMBER NOT NULL,
    CONSTRAINT a_promocion_pk PRIMARY KEY ( id )
);

ALTER TABLE a_promocion ADD CONSTRAINT ck_prom_precio CHECK ( precio > 0 ) ENABLE;

ALTER TABLE a_promocion
    ADD CONSTRAINT fk_prom_producto FOREIGN KEY ( idproducto )
        REFERENCES a_producto ( id )
    ENABLE;

ALTER TABLE a_promocion
    ADD CONSTRAINT ck_prom_unidades CHECK ( tipo IN (
        '2x1',
        'porcentaje',
        'cantidad',
        'segundo porcentaje',
        'paquete'
    ) ) ENABLE;

ALTER TABLE a_promocion
    ADD CONSTRAINT ck_tipo_prom CHECK ( unidadesdisponibles >-1 ) ENABLE;


-- Creaci�n de la tabla transaccion y especificaci�n de sus restricciones.

CREATE TABLE a_transaccion (
    idproducto      NUMBER NOT NULL,
    cantidad        NUMBER NOT NULL,
    numerofactura   NUMBER NOT NULL,
    costo           NUMBER NOT NULL,
    promocion       NUMBER,
    estado          VARCHAR2(255 BYTE) NOT NULL,
    CONSTRAINT a_transaccion_pk PRIMARY KEY ( idproducto,
                                              cantidad,
                                              numerofactura )
);

ALTER TABLE a_transaccion
    ADD CONSTRAINT fk_t_producto FOREIGN KEY ( idproducto )
        REFERENCES a_producto ( id )
    ENABLE;

ALTER TABLE a_transaccion
    ADD CONSTRAINT fk_tr_numfactura FOREIGN KEY ( numerofactura )
        REFERENCES a_factura ( numero )
    ENABLE;

ALTER TABLE a_transaccion ADD CONSTRAINT ck_tr_cantidad CHECK ( cantidad > 0 ) ENABLE;

ALTER TABLE a_transaccion ADD CONSTRAINT ck_tr_costo CHECK ( costo > 0 ) ENABLE;

ALTER TABLE a_transaccion
    ADD CONSTRAINT fk_tr_factura FOREIGN KEY ( promocion )
        REFERENCES a_promocion ( id )
    ENABLE;

ALTER TABLE a_transaccion
    ADD CONSTRAINT ck_transaccion_estado CHECK ( estado IN (
        'pendiente',
        'pagada'
    ) ) ENABLE;

COMMIT;