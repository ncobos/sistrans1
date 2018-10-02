package uniandes.isis2304.parranderos.persistencia;

import java.sql.Timestamp;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.Factura;
import uniandes.isis2304.parranderos.negocio.Pedido;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto PEDIDO de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author n.cobos, jf.torresp
 */
class SQLPedido {

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
	public SQLPedido(PersistenciaParranderos pp) {
		this.pp = pp;
	}

	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un PEDIDO a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del pedido
	 * @param proveedor - El proveedor del pedido
	 * @param sucursal - La sucursal que hace el pedido
	 * @param fechaEntrega - La fecha de entrega del pedido
	 * @param estadoOrden - El estado de orden del pedido
	 * @param cantidad - El numero de unidades solicitadas
	 * @param calificacion - La calificacion del pedido
	 * @param costoTotal - El costo total del pedido
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarPedido (PersistenceManager pm, long idPedido, long proveedor, long sucursal, Timestamp fechaEntrega, String estadoOrden, int cantidad, int calificacion, double costoTotal) 
	{
		Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPedido() + "(id, idsucursal, idproveedor, fechaentrega, estadoorden, calificacionservicio, costototal) values (?, ?, ?, ?, ?, ?, ?)");
		q.setParameters(idPedido, proveedor, sucursal, fechaEntrega, estadoOrden, cantidad, calificacion, costoTotal);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PEDIDOS de la base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del pedido
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPedidoPorId(PersistenceManager pm, long idPedido)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPedido() + " WHERE id = ?");
		q.setParameters(idPedido);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN PEDIDO de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del pedido
	 * @return El objeto PEDIDO que tiene el identificador dado
	 */
	public Pedido darPedidoPorId (PersistenceManager pm, long idPedido) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPedido() + " WHERE id = ?");
		q.setResultClass(Pedido.class);
		q.setParameters(idPedido);
		return (Pedido) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de PEDIDOS de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del pedido
	 * @return Una lista de objetos PEDIDO que tienen el identifiacor dado
	 */
	public List<Pedido> darPedidosPorId(PersistenceManager pm, long idPedido) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPedido() + " WHERE id = ?");
		q.setResultClass(Pedido.class);
		q.setParameters(idPedido);
		return (List<Pedido>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de PEDIDOS de la 
	 * base de datos de Superandes, por su sucursal
	 * @param pm - El manejador de persistencia
	 * @param sucursal - La sucursal del pedido
	 * @return Una lista de objetos PEDIDO que tienen una sucursal dada
	 */
	public List<Pedido> darPedidosPorSucursal(PersistenceManager pm, long sucursal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPedido() + " WHERE idsucursal = ?");
		q.setResultClass(Pedido.class);
		q.setParameters(sucursal);
		return (List<Pedido>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de PEDIDOS de la 
	 * base de datos de Superandes, por su proveedor
	 * @param pm - El manejador de persistencia
	 * @param proveedor - El proveedor del pedido
	 * @return Una lista de objetos PEDIDO que tienen un proveedor dado
	 */
	public List<Pedido> darPedidosPorProveedor(PersistenceManager pm, long proveedor) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPedido() + " WHERE idproveedor = ?");
		q.setResultClass(Pedido.class);
		q.setParameters(proveedor);
		return (List<Pedido>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de PEDIDOS de la 
	 * base de datos de Superandes, por su proveedor y sucursal
	 * @param pm - El manejador de persistencia
	 * @param sucursal - La sucursal del pedido
	 * @param proveedor - El proveedor del pedido
	 * @return Una lista de objetos PEDIDO que tienen una sucursal y un proveedor dado
	 */
	public List<Pedido> darPedidosPorProveedorYSucursal(PersistenceManager pm, long proveedor, long sucursal) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPedido() + " WHERE idproveedor = ? AND idsucursal = ?");
		q.setResultClass(Pedido.class);
		q.setParameters(proveedor, sucursal);
		return (List<Pedido>) q.executeList();
	}

	/**
	 * 
	 * Crea y ejecuta la sentencia SQL para cambiar el estado de orden de un pedido en la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del pedido
	 * @param estadoOrden - El estado de orden modificado a entregado o pendiente
	 * @return El número de tuplas modificadas
	 */
	public long cambiarEstadoOrdenPedido(PersistenceManager pm, long idPedido) 
	{
		String estadoOrden = "entregado";
		Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaPedido() + " SET estadoorden = ? WHERE id = ?");
		q.setParameters(estadoOrden, idPedido);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de PEDIDOS de la 
	 * base de datos de Superandes, por su calificacion
	 * @param pm - El manejador de persistencia
	 * @param calificacion - La calificacion del pedido
	 * @return Una lista de objetos PEDIDO que tienen una calificacion dada
	 */
	public List<Pedido> darPedidosPorCalificacion(PersistenceManager pm, int calificacion) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPedido() + " WHERE calificacionservicio = ?");
		q.setResultClass(Pedido.class);
		q.setParameters(calificacion);
		return (List<Pedido>) q.executeList();
	}

	/**
	 * 
	 * Crea y ejecuta la sentencia SQL para cambiar la calificacion de un pedido en la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del pedido
	 * @param calificacion - La calificacion modificada
	 * @return El número de tuplas modificadas
	 */
	public long cambiarCalificacionPedido(PersistenceManager pm, long idPedido, int calificacion) 
	{
		Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaPedido() + " SET calificacionservicio = ? WHERE id = ?");
		q.setParameters(calificacion, idPedido);
		return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de PEDIDOS de la 
	 * base de datos de Superandes, por su fecha de entrega
	 * @param pm - El manejador de persistencia
	 * @param fechaEntrega - La fecha de entrega del pedido
	 * @return Una lista de objetos PEDIDO que tienen una fecha de entrega dada
	 */
	public List<Pedido> darPedidosPorFechaEntrega(PersistenceManager pm, Timestamp fechaEntrega) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPedido() + " WHERE fechaentrega = ?");
		q.setResultClass(Pedido.class);
		q.setParameters(fechaEntrega);
		return (List<Pedido>) q.executeList();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PEDIDOS de la base de datos de Superandes dado el estado de orden: entregado
	 * @param pm - El manejador de persistencia
	 * @param estadoorden - el estado de orden del pedido (entregado)
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarPedidosTerminados(PersistenceManager pm, String estadoOrden)
	{
		Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPedido() + " WHERE estadoorden = 'Entregado'");
		q.setParameters(estadoOrden);
		return (long) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de LOS PEDIDOS de la 
	 * base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PEDIDO
	 */
	public List<Pedido> darPedidos(PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaPedido());
		q.setResultClass(Pedido.class);
		return (List<Pedido>) q.executeList();
	}
}
