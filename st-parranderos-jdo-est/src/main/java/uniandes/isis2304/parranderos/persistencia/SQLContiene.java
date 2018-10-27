package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.Contiene;

/**
 * Clase que encapsula los m�todos que hacen acceso a la base de datos para el concepto CONTIENE de Parranderos
 * N�tese que es una clase que es s�lo conocida en el paquete de persistencia
 * 
 * @author n.cobos
 */
class SQLContiene 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Cadena que representa el tipo de consulta que se va a realizar en las sentencias de acceso a la base de datos
	 * Se renombra aca para facilitar la escritura de las sentencias
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
	public SQLContiene (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar un CONTIENE a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del producto
	 * @param cantidad - numero de productos
	 * @param numerofactura - la factura a la que est� asociada la transacci�n
	 * @param costo - costo de la transacci�n
	 * @param promocion - identificador de la promoci�n a la cual est� asociada (puede no tener)
	 * @return El n�mero de tuplas insertadas
	 */
	public long adicionarContiene (PersistenceManager pm, long idProducto, int cantidad, long numerofactura, double costo, long promocion) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaContiene () + "(idproducto, cantidad, numerofactura, costo, promocion) values (?, ?, ?, ?, ?)");
        q.setParameters(idProducto, cantidad, numerofactura, costo, promocion);
        return (long) q.executeUnique();
	}

	
	/**
	 * Crea y ejecuta la sentencia SQL para eliminar UN CONTIENE de la base de datos de Superandes
	 * por carrito y producto
	 * @param pm - El manejador de persistencia
	 * @param idCarrito carrito asociado
	 * @param idProducto producto contenido en el carrito
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarContienePorCarritoProducto (PersistenceManager pm, long idCarrito, long idProducto)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaContiene () + " WHERE carrito = ? AND producto = ?");
        q.setParameters(idCarrito, idProducto);
        return (long) q.executeUnique();
	}


	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n del CONTIENE de la 
	 * base de datos de SUPERANES, por su carrito y producto asociado
	 * @param pm - El manejador de persistencia
	 * @param idCarrito carrito asociado
	 * @param idProducto producto contenido en el carrito
	 * @return Un objeto CONTIENE asociado a cierto carrito
	 */
	public Contiene darContienePorCarritoProducto(PersistenceManager pm, long idCarrito, long idProducto)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContiene () + " WHERE carrito = ? AND producto = ?");
		q.setResultClass(Contiene.class);
		q.setParameters(idCarrito, idProducto);
		return (Contiene) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS CONTIENEES de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos CONTIENE
	 */
	public List<Contiene> darContienes (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaContiene ());
		q.setResultClass(Contiene.class);
		return (List<Contiene>) q.executeList();
	}

	
}

