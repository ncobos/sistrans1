package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Proveedor;
import uniandes.isis2304.parranderos.persistencia.PersistenciaParranderos;

/**
 * Clase que encapsula los m�todos que hacen acceso a la base de datos para el concepto PROVEEDOR de Parranderos
 * N�tese que es una clase que es s�lo conocida en el paquete de persistencia
 * 
 * @author n.cobos
 */
class SQLProveedor 
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
	public SQLProveedor (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una PROVEEDOR a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param nit - El identificador del proveedor
	 * @param nombre - El nombre del proveedor
	 * @param calificacion - La calificaci�n del proveedor
	 * @return El n�mero de tuplas insertadas
	 */
	public long adicionarProveedor (PersistenceManager pm, long id, String nombre, int calificacion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaProveedor () + "(nit, nombre, calificacion) values (?, ?, ?)");
        q.setParameters(id, nombre, calificacion);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PROVEEDORES de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreProveedor - El nombre del proveedor
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarProveedorPorNombre (PersistenceManager pm, String nombreProveedor)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProveedor () + " WHERE nombre = ?");
        q.setParameters(nombreProveedor);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PROVEEDORES de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProveedor - El identificador del proveedor
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarProveedorPorId (PersistenceManager pm, long idProveedor)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProveedor () + " WHERE nit = ?");
        q.setParameters(idProveedor);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de PROVEEDORES de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreProveedor - El nombre del proveedor
	 * @return Una lista de objetos PROVEEDOR que tienen el nombre dado
	 */
	public List<Proveedor> darProveedorPorNombre (PersistenceManager pm, String nombreProveedor) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProveedor () + " WHERE nombre = ?");
		q.setResultClass(Proveedor.class);
		q.setParameters(nombreProveedor);
		return (List<Proveedor>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS PROVEEDORES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PROVEEDOR
	 */
	public List<Proveedor> darProveedores (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProveedor ());
		q.setResultClass(Proveedor.class);
		return (List<Proveedor>) q.executeList();
	}
	
	public List<Object[]> consultarFuncionamiento3(PersistenceManager pm)
	{
		String sql = "SELECT\r\n" + 
				"     t2.idproveedor,\r\n" + 
				"     t1.cantidad1\r\n" + 
				" FROM\r\n" + 
				"     (\r\n" + 
				"         SELECT\r\n" + 
				"             MAX(quantity2) AS cantidad1\r\n" + 
				"         FROM\r\n" + 
				"             (\r\n" + 
				"                 SELECT\r\n" + 
				"                     p.idproveedor,\r\n" + 
				"                     SUM(s.cantidad) AS quantity2\r\n" + 
				"                 FROM\r\n" + 
				"                     a_pedido p,\r\n" + 
				"                     a_subpedido s\r\n" + 
				"                 WHERE\r\n" + 
				"                     p.id = s.idpedido\r\n" + 
				"                 GROUP BY\r\n" + 
				"                     p.idproveedor\r\n" + 
				"             )\r\n" + 
				"     ) t1,\r\n" + 
				"     (\r\n" + 
				"         SELECT\r\n" + 
				"             p.idproveedor,\r\n" + 
				"             SUM(s.cantidad) AS quantity\r\n" + 
				"         FROM\r\n" + 
				"             a_pedido p,\r\n" + 
				"             a_subpedido s\r\n" + 
				"         WHERE\r\n" + 
				"             p.id = s.idpedido\r\n" + 
				"         GROUP BY\r\n" + 
				"             p.idproveedor\r\n" + 
				"     ) t2\r\n" + 
				" WHERE\r\n" + 
				"     t2.quantity = t1.cantidad1";
		
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}
	
	public List<Object[]> consultarFuncionamiento4(PersistenceManager pm)
	{
		String sql = "SELECT\r\n" + 
				"     t2.idproveedor,\r\n" + 
				"     t1.cantidad1 as cantidadPedidos\r\n" + 
				" FROM\r\n" + 
				"     (\r\n" + 
				"         SELECT\r\n" + 
				"             MIN(quantity2) AS cantidad1\r\n" + 
				"         FROM\r\n" + 
				"             (\r\n" + 
				"                 SELECT\r\n" + 
				"                     p.idproveedor,\r\n" + 
				"                     SUM(s.cantidad) AS quantity2\r\n" + 
				"                 FROM\r\n" + 
				"                     a_pedido p,\r\n" + 
				"                     a_subpedido s\r\n" + 
				"                 WHERE\r\n" + 
				"                     p.id = s.idpedido\r\n" + 
				"                 GROUP BY\r\n" + 
				"                     p.idproveedor\r\n" + 
				"             )\r\n" + 
				"     ) t1,\r\n" + 
				"     (\r\n" + 
				"         SELECT\r\n" + 
				"             p.idproveedor,\r\n" + 
				"             SUM(s.cantidad) AS quantity\r\n" + 
				"         FROM\r\n" + 
				"             a_pedido p,\r\n" + 
				"             a_subpedido s\r\n" + 
				"         WHERE\r\n" + 
				"             p.id = s.idpedido\r\n" + 
				"         GROUP BY\r\n" + 
				"             p.idproveedor\r\n" + 
				"     ) t2\r\n" + 
				" WHERE\r\n" + 
				"     t2.quantity = t1.cantidad1";
		
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}
	
}

