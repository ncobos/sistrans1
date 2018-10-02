package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Factura;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto FACTURA de Superandes
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author n.cobos, jf.torresp
 */
class SQLFactura {

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
	public SQLFactura(PersistenciaParranderos pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una FACTURA a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param numero - El identificador de la factura
	 * @param fecha - Fecha de la factura
	 * @param idCliente - El identificador del cliente de la factura
	 * @param idSucursal - El identificador de la sucursal donde se generó la factura
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarFactura (PersistenceManager pm, long numero, Timestamp fecha, String idCliente, long sucursal) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaFactura() + "(numero, fecha, idcliente, sucursal) values (?, ?, ?, ?)");
        q.setParameters(numero, fecha, idCliente, sucursal);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar FACTURAS de la base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param numero- El identificador de la factura
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarFacturaPorNumero(PersistenceManager pm, long numero)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaFactura() + " WHERE numero = ?");
        q.setParameters(numero);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UNA FACTURA de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param numero - El identificador de la factura
	 * @return El objeto FACTURA que tiene el identificador dado
	 */
	public Factura darFacturaPorNumero (PersistenceManager pm, long numero) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaFactura() + " WHERE numero = ?");
		q.setResultClass(Factura.class);
		q.setParameters(numero);
		return (Factura) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de FACTURAS de la 
	 * base de datos de Superandes, por su numero
	 * @param pm - El manejador de persistencia
	 * @param numero - El numero de la factura
	 * @return Una lista de objetos FACTURA que tienen el numero dado
	 */
	public List<Factura> darFacturasPorNumero (PersistenceManager pm, long numero) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaFactura() + " WHERE numero = ?");
		q.setResultClass(Factura.class);
		q.setParameters(numero);
		return (List<Factura>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de FACTURAS de la 
	 * base de datos de Superandes, por su cliente
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El cliente de la factura
	 * @return Una lista de objetos FACTURA que tienen el cliente dado
	 */
	public List<Factura> darFacturasPorCliente (PersistenceManager pm, long idCliente) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaFactura() + " WHERE idcliente = ?");
		q.setResultClass(Factura.class);
		q.setParameters(idCliente);
		return (List<Factura>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de FACTURAS de la 
	 * base de datos de Superandes, por su sucursal
	 * @param pm - El manejador de persistencia
	 * @param sucursal - La sucursal de la factura
	 * @return Una lista de objetos FACTURA que tienen una sucursal dada
	 */
	public List<Factura> darFacturasPorSucursal(PersistenceManager pm, long sucursal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaFactura() + " WHERE sucursal = ?");
		q.setResultClass(Factura.class);
		q.setParameters(sucursal);
		return (List<Factura>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de FACTURAS de la 
	 * base de datos de Superandes, por su fecha
	 * @param pm - El manejador de persistencia
	 * @param fecha - La fecha de la factura
	 * @return Una lista de objetos FACTURA que tienen una fecha dada
	 */
	public List<Factura> darFacturasPorFecha(PersistenceManager pm, Timestamp fecha) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaFactura() + " WHERE fecha = ?");
		q.setResultClass(Factura.class);
		q.setParameters(fecha);
		return (List<Factura>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de FACTURAS de la 
	 * base de datos de Superandes, por su cliente y sucursal
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El cliente de la factura
	 * @param sucursal - La sucursal de la factura
	 * @return Una lista de objetos FACTURA que tienen un cliente y una sucursal dada
	 */
	public List<Factura> darFacturasPorClienteYSucursal(PersistenceManager pm, long idCliente, long sucursal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaFactura() + " WHERE idcliente = ? AND sucursal = ?");
		q.setResultClass(Factura.class);
		q.setParameters(idCliente, sucursal);
		return (List<Factura>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LAS FACTURAS de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos FACTURA
	 */
	public List<Factura> darFacturas(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaFactura());
		q.setResultClass(Factura.class);
		return (List<Factura>) q.executeList();
	}

}
