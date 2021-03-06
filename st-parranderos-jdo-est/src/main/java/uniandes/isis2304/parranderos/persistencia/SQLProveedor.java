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
				"   distinct nombre,\r\n" + 
				"     pedidos,\r\n" + 
				"     semana\r\n" + 
				" FROM\r\n" + 
				"     (\r\n" + 
				"         SELECT\r\n" + 
				"             MAX(cantidad) AS pedidos, to_char(to_date(fecha), 'IW') as semana\r\n" + 
				"         FROM\r\n" + 
				"             (\r\n" + 
				"                 SELECT DISTINCT\r\n" + 
				"                    SUM(s.cantidad) AS cantidad,\r\n" + 
				"                     p.idproveedor as id,\r\n" + 
				"                     ap.nombre as nombre,\r\n" + 
				"                     p.fechaentrega as fecha\r\n" + 
				"                 FROM\r\n" + 
				"                     a_pedido p,\r\n" + 
				"                     a_subpedido s, \r\n" + 
				"                     a_proveedor ap\r\n" + 
				"                 WHERE\r\n" + 
				"                     p.idproveedor = ap.nit AND p.id = s.idpedido AND p.fechaentrega between '01/01/2019' and '01/08/2019' \r\n" + 
				"                 group by \r\n" + 
				"                 ap.nombre, p.idproveedor, p.fechaentrega\r\n" + 
				"                 \r\n" + 
				"                  ORDER BY\r\n" + 
				"                     cantidad DESC) group by to_char(to_date(fecha), 'IW') order by semana asc\r\n" + 
				"                 \r\n" + 
				"             )\r\n" + 
				"      t1,\r\n" + 
				"     (\r\n" + 
				"         SELECT DISTINCT\r\n" + 
				"             SUM(s.cantidad) AS cantidad,\r\n" + 
				"              p.idproveedor as id,\r\n" + 
				"              ap.nombre as nombre,\r\n" + 
				"               p.fechaentrega as fecha\r\n" + 
				"         FROM\r\n" + 
				"            a_pedido p,\r\n" + 
				"            a_subpedido s, \r\n" + 
				"            a_proveedor ap\r\n" + 
				"         WHERE\r\n" + 
				"             p.idproveedor = ap.nit AND p.id = s.idpedido AND p.fechaentrega between '01/01/2019' and '01/08/2019' \r\n" + 
				"         GROUP BY\r\n" + 
				"                 ap.nombre, p.idproveedor, p.fechaentrega\r\n" + 
				"     ) t2\r\n" + 
				"where t1.pedidos = t2.cantidad\r\n" + 
				"order by semana asc";
		
		Query q = pm.newQuery(SQL, sql);
		
		return q.executeList();
		
	}
	
	public List<Object[]> consultarFuncionamiento4(PersistenceManager pm)
	{
		String sql = "SELECT\r\n" + 
				"   distinct nombre,\r\n" + 
				"     pedidos,\r\n" + 
				"     semana\r\n" + 
				" FROM\r\n" + 
				"     (\r\n" + 
				"         SELECT\r\n" + 
				"             MIN(cantidad) AS pedidos, to_char(to_date(fecha), 'IW') as semana\r\n" + 
				"         FROM\r\n" + 
				"             (\r\n" + 
				"                 SELECT DISTINCT\r\n" + 
				"                    SUM(s.cantidad) AS cantidad,\r\n" + 
				"                     p.idproveedor as id,\r\n" + 
				"                     ap.nombre as nombre,\r\n" + 
				"                     p.fechaentrega as fecha\r\n" + 
				"                 FROM\r\n" + 
				"                     a_pedido p,\r\n" + 
				"                     a_subpedido s, \r\n" + 
				"                     a_proveedor ap\r\n" + 
				"                 WHERE\r\n" + 
				"                     p.idproveedor = ap.nit AND p.id = s.idpedido AND p.fechaentrega between '01/01/2019' and '01/08/2019' \r\n" + 
				"                 group by \r\n" + 
				"                 ap.nombre, p.idproveedor, p.fechaentrega\r\n" + 
				"                 \r\n" + 
				"                  ORDER BY\r\n" + 
				"                     cantidad DESC) group by to_char(to_date(fecha), 'IW') order by semana asc\r\n" + 
				"                 \r\n" + 
				"             )\r\n" + 
				"      t1,\r\n" + 
				"     (\r\n" + 
				"         SELECT DISTINCT\r\n" + 
				"             SUM(s.cantidad) AS cantidad,\r\n" + 
				"              p.idproveedor as id,\r\n" + 
				"              ap.nombre as nombre,\r\n" + 
				"               p.fechaentrega as fecha\r\n" + 
				"         FROM\r\n" + 
				"            a_pedido p,\r\n" + 
				"            a_subpedido s, \r\n" + 
				"            a_proveedor ap\r\n" + 
				"         WHERE\r\n" + 
				"             p.idproveedor = ap.nit AND p.id = s.idpedido AND p.fechaentrega between '01/01/2019' and '01/08/2019' \r\n" + 
				"         GROUP BY\r\n" + 
				"                 ap.nombre, p.idproveedor, p.fechaentrega\r\n" + 
				"     ) t2\r\n" + 
				"where t1.pedidos = t2.cantidad\r\n" + 
				"order by semana asc";
		
		Query q = pm.newQuery(SQL, sql);
		return q.executeList();
	}
	
}

