package uniandes.isis2304.parranderos.persistencia;


import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Subpedido;

/**
 * Clase que encapsula los métodos que hacen acceso a la base de datos para el concepto SUBPEDIDO de Parranderos
 * Nótese que es una clase que es sólo conocida en el paquete de persistencia
 * 
 * @author n.cobos, jf.torresp
 */
class SQLSubpedido {
	
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
	public SQLSubpedido(PersistenciaParranderos pp) {
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un SUBPEDIDO a la base de datos de Superandes
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del subpedido
	 * @param producto - El producto del subpedido
	 * @param cantidad - La cantidad de unidades pedidas por producto
	 * @param costo - El costo del subpedido
	 * @return EL número de tuplas insertadas
	 */
	public long adicionarPedido (PersistenceManager pm, long id, long producto, int cantidad, double costo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaPedido() + "(idpedido, idproducto, cantidad, costo) values (?, ?, ?, ?)");
        q.setParameters(id, producto, cantidad, costo);
        return (long) q.executeUnique();            
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar SUBPEDIDOS de la base de datos de Superandes, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del pedido
	 * @return EL número de tuplas eliminadas
	 */
	public long eliminarSubPedidoPorId(PersistenceManager pm, long idPedido)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaPedido() + " WHERE idpedido = ?");
        q.setParameters(idPedido);
        return (long) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN SUBPEDIDO de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del subpedido
	 * @return El objeto SUBPEDIDO que tiene el identificador dado
	 */
	public Subpedido darSubPedidoPorId (PersistenceManager pm, long idPedido) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSubpedido() + " WHERE idpedido = ?");
		q.setResultClass(Subpedido.class);
		q.setParameters(idPedido);
		return (Subpedido) q.executeUnique();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de SUBPEDIDOS de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del subpedido
	 * @return Una lista de objetos SUBPEDIDO que tienen el identifiacor dado
	 */
	public List<Subpedido> darSubPedidosPorId(PersistenceManager pm, long idPedido) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSubpedido() + " WHERE idpedido = ?");
		q.setResultClass(Subpedido.class);
		q.setParameters(idPedido);
		return (List<Subpedido>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de PEDIDOS de la 
	 * base de datos de Superandes, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idPedido - El identificador del pedido
	 * @return Una lista de objetos PEDIDO que tienen el identifiacor dado
	 */
	public List<Subpedido> darSubPedidosPorProducto(PersistenceManager pm, long producto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaSubpedido() + " WHERE idproducto = ?");
		q.setResultClass(Subpedido.class);
		q.setParameters(producto);
		return (List<Subpedido>) q.executeList();
	}
}
