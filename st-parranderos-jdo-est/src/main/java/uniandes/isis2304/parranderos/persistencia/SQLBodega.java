package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Bodega;


/**
 * Clase que encapsula los m�todos que hacen acceso a la base de datos para el concepto BODEGA de Parranderos
 * N�tese que es una clase que es s�lo conocida en el paquete de persistencia
 * 
 * @author n.cobos
 */
class SQLBodega 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra ac� para facilitar la escritura de las sentencias
	 */
	private final static String SQL = PersistenciaParranderos.SQL;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia general de la aplicaci�n
	 */
	private PersistenciaParranderos pp;

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/

	/**
	 * Constructor
	 * @param pp - El Manejador de persistencia de la aplicaci�n
	 */
	public SQLBodega (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un BODEGA a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador de la bodega
	 * @param capacidadVolumen - el volumen de la bodega
	 * @param capacidadPeso - Capacidad de peso de la bodega
	 * @param producto - producto que almacena la bodega 
	 * @param sucursal - la sucursal a la que pertenece la bodega
	 * @param existencias - existencias del producto en la bodega
	 * @return El n�mero de tuplas insertadas
	 */
	public long adicionarBodega (PersistenceManager pm, long id, double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int existencias) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaBodega () + "(id, capacidadvolumen, capacidadpeso, producto, sucursal, existencias) values (?, ?, ?, ?, ?, ?)");
        q.setParameters(id, capacidadVolumen, capacidadPeso, capacidadPeso, producto, sucursal, existencias);
        return (long) q.executeUnique();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN BODEGA de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBodega - El identificador del bodega
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarBodegaPorId (PersistenceManager pm, long idBodega)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaBodega () + " WHERE id = ?");
        q.setParameters(idBodega);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UN BODEGA de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idBodega - El identificador del bodega
	 * @return El objeto BODEGA que tiene el identificador dado
	 */
	public Bodega darBodegaPorId (PersistenceManager pm, long idBodega) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaBodega () + " WHERE id = ?");
		q.setResultClass(Bodega.class);
		q.setParameters(idBodega);
		return (Bodega) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS BODEGAES de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param sucursal - numero de la sucursal
	 * @return Una lista de objetos BODEGA que pertenecen a cierta sucursal
	 */
	public List<Bodega> darBodegasPorSucursal (PersistenceManager pm, long sucursal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaBodega () + " WHERE sucursal = ?");
		q.setResultClass(Bodega.class);
		q.setParameters(sucursal);
		return (List<Bodega>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS BODEGAS de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos BODEGA
	 */
	public List<Bodega> darBodegas (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaBodega ());
		q.setResultClass(Bodega.class);
		return (List<Bodega>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para aumentar en diez el n�mero de existencias de las bodegas de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param id - bodega a la cual se le quiere realizar el proceso
	 * @return El n�mero de tuplas modificadas
	 */
	public long aumentarExistenciasBodegasEnDiez (PersistenceManager pm, long id)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaBodega () + " SET existencias = existencias + 10 WHERE id = ?");
        q.setParameters(id);
        return (long) q.executeUnique();
	}
	
}
