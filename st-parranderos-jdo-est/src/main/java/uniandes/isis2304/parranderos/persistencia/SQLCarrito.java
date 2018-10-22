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
	public long adicionarCarrito (PersistenceManager pm, long id, String estado, String idCliente)
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCarrito() + "(id, estado, idcliente) values (?, ?, ?)");
        q.setParameters(id, estado, idCliente);
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
	public List<Carrito> darCarritosPorNumero (PersistenceManager pm, long id) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCarrito() + " WHERE id = ?");
		q.setResultClass(Carrito.class);
		q.setParameters(id);
		return (List<Carrito>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de FACTURAS de la 
	 * base de datos de Superandes, por su cliente
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El cliente de la carrito
	 * @return Una lista de objetos FACTURA que tienen el cliente dado
	 */
	public List<Carrito> darCarritosPorCliente (PersistenceManager pm, long idCliente) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCarrito() + " WHERE idcliente = ?");
		q.setResultClass(Carrito.class);
		q.setParameters(idCliente);
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

}
