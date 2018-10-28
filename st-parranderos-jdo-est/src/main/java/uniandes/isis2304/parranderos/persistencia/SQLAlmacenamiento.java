package uniandes.isis2304.parranderos.persistencia;


import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Almacenamiento;


/**
 * Clase que encapsula los m�todos que hacen acceso a la base de datos para el concepto ESTANTE de Parranderos
 * N�tese que es una clase que es s�lo conocida en el paquete de persistencia
 * 
 * @author n.cobos
 */
class SQLAlmacenamiento 
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
	public SQLAlmacenamiento (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un ESTANTE a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del almacenamiento
	 * @param capacidadVolumen - el volumen del almacenamiento
	 * @param capacidadPeso - Capacidad de peso del almacenamiento
	 * @param producto - producto que almacena el almacenamiento 
	 * @param sucursal - la sucursal a la que pertenece el almacenamiento
	 * @param nivelabastecimientobodega nivel m�nimo de productos antes de reabastecer
	 * @param existencias - existencias del producto en el almacenamiento
	 * @return El n�mero de tuplas insertadas
	 */
	public long adicionarAlmacenamiento (PersistenceManager pm, long id, double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int nivelabastecimientobodega, int existencias, int capacidadproductos, String tipo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaAlmacenamiento () + "(id, capacidadvolumen, capacidadpeso, producto, sucursal, nivelabastecimientobodega, existencias, capacidadproductos, tipo) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, capacidadVolumen, capacidadPeso, capacidadPeso, producto, sucursal, nivelabastecimientobodega, existencias, capacidadproductos, tipo);
        return (long) q.executeUnique();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN ESTANTE de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlmacenamiento - El identificador del almacenamiento
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarAlmacenamientoPorId (PersistenceManager pm, long idAlmacenamiento)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaAlmacenamiento () + " WHERE id = ?");
        q.setParameters(idAlmacenamiento);
        return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UN ESTANTE de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idAlmacenamiento - El identificador del almacenamiento
	 * @return El objeto ESTANTE que tiene el identificador dado
	 */
	public Almacenamiento darAlmacenamientoPorId (PersistenceManager pm, long idAlmacenamiento) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlmacenamiento () + " WHERE id = ?");
		q.setResultClass(Almacenamiento.class);
		q.setParameters(idAlmacenamiento);
		return (Almacenamiento) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS ESTANTEES de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param sucursal - numero de la sucursal
	 * @return Una lista de objetos ESTANTE que pertenecen a cierta sucursal
	 */
	public List<Almacenamiento> darAlmacenamientosPorSucursal (PersistenceManager pm, long sucursal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlmacenamiento () + " WHERE sucursal = ?");
		q.setResultClass(Almacenamiento.class);
		q.setParameters(sucursal);
		return (List<Almacenamiento>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS ESTANTES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos ESTANTE
	 */
	public List<Almacenamiento> darAlmacenamientos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaAlmacenamiento ());
		q.setResultClass(Almacenamiento.class);
		return (List<Almacenamiento>) q.executeList();
	}

	
	/**
	 * Crea y ejecuta la sentencia SQL para aumentar las existencias de los almacenamientos de 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param cantidad a aumentar
	 * @param sucursal del almacenamiento
	 * @param producto que almacena el almacenamiento
	 * @return El número de tuplas modificadas
	 */
	public long aumentarExistenciasAlmacenamientos(PersistenceManager pm, int cantidad, long sucursal, long producto, String tipo)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaAlmacenamiento() + " SET existencias = existencias + ? WHERE producto = ? AND sucursal = ? AND tipo = ?");
        q.setParameters(cantidad, producto, sucursal, tipo);
        return (long) q.executeUnique();
	}
	
	
	/**
	 * Crea y ejecuta la sentencia SQL para disminuir las existencias de los almacenamientos de 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param cantidad a disminuir
	 * @param sucursal del almacenamiento
	 * @param producto que almacena el almacenamiento
	 * @return El número de tuplas modificadas
	 */
	public long disminuirExistenciasAlmacenamientos(PersistenceManager pm, int cantidad, long sucursal, long producto, String tipo)
	{
        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaAlmacenamiento() + " SET existencias = existencias - ? WHERE producto = ? AND sucursal = ? AND tipo = ?");
        q.setParameters(cantidad, producto, sucursal, tipo);
        return (long) q.executeUnique();
	}
	
	
	
}