/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.persistencia;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto BAR de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author Germán Bravo
 */
class SQLUtil
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra acá para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaParranderos.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicación
	 */
	private PersistenciaParranderos pp;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLUtil (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para obtener un nuevo número de secuencia
	 * @param pm - El manejador de persistencia
	 * @return El número de secuencia generado
	 */
	public long nextval (PersistenceManager pm)
	{
        Query q = pm.newQuery(SQL, "SELECT "+ pp.darSeqSuperandes() + ".nextval FROM DUAL");
        q.setResultClass(Long.class);
        long resp = (long) q.executeUnique();
        return resp;
	}

	/**
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @param pm - El manejador de persistencia
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos (PersistenceManager pm)
	{
        Query qAlmacenamiento = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlmacenamiento());          
        Query qCliente = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente());
        Query qFactura = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaFactura ());
        Query qOfrecen = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaOfrecen ());
        Query qPedido = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPedido ());
        Query qProducto = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto ());
        Query qPromocion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPromocion ());
        Query qProveedor = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProveedor ());
        Query qSubpedido = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSubpedido ());
        Query qSucursal = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSucursal ());
        Query qSupermercado = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSupermercado ());
        Query qTransaccion = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaTransaccion ());
        Query qVende = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaVende ());


        long almacenamientoEliminados = (long) qAlmacenamiento.executeUnique ();
        long clienteEliminados = (long) qCliente.executeUnique ();
        long facturaEliminadas = (long) qFactura.executeUnique ();
        long ofrecenEliminados = (long) qOfrecen.executeUnique ();
        long pedidoEliminados = (long) qPedido.executeUnique ();
        long productoEliminados = (long) qProducto.executeUnique ();
        long promocionEliminados = (long) qPromocion.executeUnique ();
        long proveedorEliminados = (long) qProveedor.executeUnique ();
        long subpedidoEliminados = (long) qSubpedido.executeUnique ();
        long sucursalEliminados = (long) qSucursal.executeUnique ();
        long supermercadoEliminados = (long) qSupermercado.executeUnique ();
        long transaccionEliminados = (long) qTransaccion.executeUnique ();
        long vendeEliminados = (long) qVende.executeUnique ();

        return new long[] {almacenamientoEliminados, clienteEliminados, facturaEliminadas, ofrecenEliminados, pedidoEliminados, productoEliminados, promocionEliminados, proveedorEliminados, subpedidoEliminados, sucursalEliminados, supermercadoEliminados, transaccionEliminados, vendeEliminados};
	}

}
