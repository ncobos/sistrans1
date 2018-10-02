package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.Supermercado;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SUPERMERCADO de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author n.cobos, jf.torresp
 */
class SQLSupermercado{

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
	public SQLSupermercado(PersistenciaParranderos pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un SUPERMERCADO a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param nombre - El nombre del supermercado
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarSupermercado(PersistenceManager pm, String nombre) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaSupermercado() + "(nombre) values (?)");
        q.setParameters(nombre);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar SUPERMERCADO de la base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombre - El identificador del supermercado
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarSupermercadoPorNombre(PersistenceManager pm, String nombre)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaSupermercado() + " WHERE nombre = ?");
        q.setParameters(nombre);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN SUPERMERCADO de la 
	 * base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombre - El nombre del supermercado
	 * @return El objeto SUPERMERCADO que tiene el nombre dado
	 */
	public Supermercado darSupermercadoPorNombre(PersistenceManager pm, String nombre) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSupermercado() + " WHERE nombre = ?");
		q.setResultClass(Supermercado.class);
		q.setParameters(nombre);
		return (Supermercado) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS SUPERMERCADOS de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos SUPERMERCADO
	 */
	public List<Supermercado> darSupermercados(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSupermercado());
		q.setResultClass(Supermercado.class);
		return (List<Supermercado>) q.executeList();
	}
}
