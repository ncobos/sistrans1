package uniandes.isis2304.parranderos.persistencia;


import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Carrito;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CARRITO de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author n.cobos, jf.torresp
 */
class SQLCarrito {

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
	PersistenciaParranderos pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicación
	 */
	public SQLCarrito(PersistenciaParranderos pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una FACTURA a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param numero - El identificador de la carrito
	 * @param fecha - Fecha de la carrito
	 * @param idCliente - El identificador del cliente de la carrito
	 * @param idSucursal - El identificador de la sucursal donde se generó la carrito
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarCarrito (PersistenceManager pm, long id, String estado, long clave, long sucursal)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCarrito() + "(id, estado, clave, sucursal) values (?, ?, ?, ?)");
        q.setParameters(id, estado, clave, sucursal);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar FACTURAS de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param numero- El identificador de la carrito
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarCarritoPorId(PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCarrito() + " WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA FACTURA de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param numero - El identificador de la carrito
	 * @return El objeto FACTURA que tiene el identificador dado
	 */
	public Carrito darCarritoPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCarrito() + " WHERE id = ?");
		q.setResultClass(Carrito.class);
		q.setParameters(id);
		return (Carrito) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de FACTURAS de la 
	 * base de datos de Superandes, por su numero
	 * @param pm - El manejador de persistencia
	 * @param numero - El numero de la carrito
	 * @return Una lista de objetos FACTURA que tienen el numero dado
	 */
	public List<Carrito> darCarritosPorId (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCarrito() + " WHERE id = ?");
		q.setResultClass(Carrito.class);
		q.setParameters(id);
		return (List<Carrito>) q.executeList();
	}
	
	
	

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS FACTURAS de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos FACTURA
	 */
	public List<Carrito> darCarritos(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCarrito());
		q.setResultClass(Carrito.class);
		return (List<Carrito>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CARRITOS abandonados de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos FACTURA
	 */
	public List<Carrito> darCarritosAbandonados(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCarrito() + " WHERE estado = 'abandonado'");
		q.setResultClass(Carrito.class);
		return (List<Carrito>) q.executeList();
	}

	/**
	 * 
	 * Crea y ejecuta la sentencia SQL para cambiar el estado de un carrito en la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idCarrito - El identificador del carrito
	 * @param estadoOrden - El estado de orden modificado a entregado o pendiente
	 * @return El número de tuplas modificadas
	 */
	public long cambiarEstadoCarrito(PersistenceManager pm, long idCarrito, String estado) 
	{
		Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaCarrito() + " SET estado = ? WHERE id = ?");
		q.setParameters(estado, idCarrito);
		return (long) q.executeUnique();            
	}
	
	/**
	 * 
	 * Crea y ejecuta la sentencia SQL para cambiar la clave de un carrito en la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del carrito
	 * @param clave - La nueva clave del carrito
	 * @return El número de tuplas modificadas
	 */
	public long cambiarClaveCarrito(PersistenceManager pm, long idCarrito, long clave) 
	{
		Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaCarrito() + " SET clave = ? WHERE id = ?");
		q.setParameters(clave, idCarrito);
		return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA FACTURA de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param numero - El identificador de la carrito
	 * @return El objeto FACTURA que tiene el identificador dado
	 */
	public Carrito darCarritoPorIdClave (PersistenceManager pm, long id, long clave) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCarrito() + " WHERE id = ? AND clave = ?");
		q.setResultClass(Carrito.class);
		q.setParameters(id, clave);
		return (Carrito) q.executeUnique();
	}
}
