package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cliente;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto CLIENTE de parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author n.cobos, jf.torresp
 */
class SQLCliente {

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
	public SQLCliente (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un CLIENTE a la base de datos de parranderos
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El identificador del cliente
	 * @param nombre - El nombre del cliente
	 * @param correo - El correo del cliente
	 * @param tipo - El tipo de cliente(PERSONA, EMPRESA)
	 * @param direccion - La direccion del cliente
	 * @return El número de tuplas insertadas
	 */
	public long adicionarCliente (PersistenceManager pm, long idCliente, String nombre, String correo, String tipo, String direccion) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaCliente () + "(id, nombre, correo, tipo, direccion) values (?, ?, ?, ?, ?)");
		q.setParameters(idCliente, nombre, correo, tipo, direccion);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar CLIENTES de la base de datos de parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreCliente - El nombre del cliente
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarClientePorNombre (PersistenceManager pm, String nombreCliente)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente () + " WHERE nombre = ?");
		q.setParameters(nombreCliente);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN CLIENTE de la base de datos de parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El identificador del cliente
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarClientePorId (PersistenceManager pm, long idCliente)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaCliente () + " WHERE id = ?");
		q.setParameters(idCliente);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN CLIENTE de la 
	 * base de datos de parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idCliente - El identificador del cliente
	 * @return El objeto CLIENTE que tiene el identificador dado
	 */
	public Cliente darClientePorId (PersistenceManager pm, long idCliente) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente () + " WHERE id = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(idCliente);
		return (Cliente) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CLIENTES de la 
	 * base de datos de parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreCliente - El nombre de cliente buscado
	 * @return Una lista de objetos CLIENTE que tienen el nombre dado
	 */
	public List<Cliente> darClientesPorNombre (PersistenceManager pm, String nombreCliente) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente() + " WHERE nombre = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(nombreCliente);
		return (List<Cliente>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CLIENTES de la 
	 * base de datos de parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CLIENTE
	 */
	public List<Cliente> darClientes (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente());
		q.setResultClass(Cliente.class);
		return (List<Cliente>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS CLIENTES de la 
	 * base de datos de parranderos, por su tipo
	 * @param pm - El manejador de persistencia
	 * @param tipoCliente - El tipo del cliente buscado
	 * @return Una lista de objetos CLIENTE que tienen el tipo dado
	 */
	public List<Cliente> darClientesPorTipo (PersistenceManager pm, String tipoCliente) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaCliente() + " WHERE tipo = ?");
		q.setResultClass(Cliente.class);
		q.setParameters(tipoCliente);
		return (List<Cliente>) q.executeList();
	}

	public List<Object[]> darClientesFrecuentes(PersistenceManager pm, long sucursal)
	{
		String sql = "SELECT IDCLIENTE, MES, ANIO, COUNT(*) AS COMPRAS";
		sql += " FROM ( SELECT NUMERO, IDCLIENTE, EXTRACT(DAY FROM FECHA) AS DIA, EXTRACT(MONTH FROM FECHA) AS MES, EXTRACT(YEAR FROM FECHA) AS ANIO";  
		sql += " FROM " + pp.darTablaFactura();
		sql += " WHERE SUCURSAL = ?) ";
		sql += " GROUP BY IDCLIENTE, MES, ANIO ";
		sql += " HAVING COUNT(*) >= 2 ";
		sql += " ORDER BY MES ASC, ANIO ASC ";

		Query q = pm.newQuery(SQL, sql);
		q.setParameters(sucursal);
		return q.executeList();
	}

	public List<Cliente> consumo1(PersistenceManager pm, long producto, String fechainicio, String fechafin,
			String criterio, String criterio2)
	{
		String sql = "";

		if(criterio.equals("id"))
		{
			criterio = "c.id";
		}

		if(criterio.equals("fecha"))
		{
			criterio = "f.fecha";
		}

		if(criterio.equals("cantidad"))
		{
			criterio = "t.cantidad";
		}

		if(criterio2.equals("DESC")) {
			sql = "select c.id, c.nombre, c.correo, c.direccion, c.tipo";
			sql += " from a_transaccion t, a_factura f, a_cliente c";  
			sql += " where t.numerofactura = f.numero AND f.idcliente = c.id AND t.idproducto = ? AND f.fecha BETWEEN (?) AND (?)";
			sql += " ORDER BY ? DESC";
		}

		if(criterio2.equals("ASC"))
		{
			sql = "select c.id, c.nombre, c.correo, c.direccion, c.tipo";
			sql += " from a_transaccion t, a_factura f, a_cliente c";  
			sql += " where t.numerofactura = f.numero AND f.idcliente = c.id AND t.idproducto = ? AND f.fecha BETWEEN (?) AND (?)";
			sql += " ORDER BY ? ASC";
		}

		Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Cliente.class);
		q.setParameters(producto, fechainicio, fechafin, criterio);
		return (List<Cliente>) q.executeList();
	}

	public List<Cliente> consumo2(PersistenceManager pm, long producto, String fechainicio, String fechafin,
		String pcriterio, String pcriterio2)
	{
		String sql = "";

		if(pcriterio.equals("id"))
		{
			pcriterio = "c.id";
		}

		if(pcriterio.equals("fecha"))
		{
			pcriterio = "f.fecha";
		}

		if(pcriterio.equals("cantidad"))
		{
			pcriterio = "t.cantidad";
		}

		if(pcriterio2.equals("DESC")) {
			sql = "select c.id, c.nombre, c.correo, c.direccion, c.tipo";
			sql += " from a_transaccion t, a_factura f, a_cliente c";  
			sql += " where t.numerofactura = f.numero AND f.idcliente = c.id AND t.idproducto = ? AND f.fecha BETWEEN (?) AND (?)";
			sql += " ORDER BY ? DESC";
		}

		if(pcriterio2.equals("ASC"))
		{
			sql = "select c.id, c.nombre, c.correo, c.direccion, c.tipo";
			sql += " from a_transaccion t, a_factura f, a_cliente c";  
			sql += " where t.numerofactura = f.numero AND f.idcliente = c.id AND t.idproducto != ? AND f.fecha BETWEEN (?) AND (?)";
			sql += " ORDER BY ? ASC";
		}

		Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Cliente.class);
		q.setParameters(producto, fechainicio, fechafin, pcriterio);
		return (List<Cliente>) q.executeList();
	}
}
