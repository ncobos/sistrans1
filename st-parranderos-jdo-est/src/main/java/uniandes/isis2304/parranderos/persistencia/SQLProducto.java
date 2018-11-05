package uniandes.isis2304.parranderos.persistencia;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import uniandes.isis2304.parranderos.negocio.AyudaRFC7;
import uniandes.isis2304.parranderos.negocio.AyudaRFC72;
import uniandes.isis2304.parranderos.negocio.Producto;
import uniandes.isis2304.parranderos.negocio.Producto;
import uniandes.isis2304.parranderos.persistencia.PersistenciaParranderos;

/**
 * Clase que encapsula los m�todos que hacen acceso a la base de datos para el concepto PRODUCTO de Parranderos
 * N�tese que es una clase que es s�lo conocida en el paquete de persistencia
 * 
 * @author n.cobos
 */
class SQLProducto 
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
	public SQLProducto (PersistenciaParranderos pp)
	{
		this.pp = pp;
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para adicionar una PRODUCTO a la base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @param id - El identificador del producto
	 * @param nombre - El nombre del producto
	 * @param marca - La marca del producto
	 * @param presentacion - la presentaci�n del producto
	 * @param codigoproductoras - El codigo de productoras del producto
	 * @param unidadmedida - la unidad de medida del producto
	 * @param categoria - la categor�a del producto
	 * @param tipo - el tipo del producto
	 * @return El n�mero de tuplas insertadas
	 */
	public long adicionarProducto (PersistenceManager pm, long id, String nombre, String marca, String presentacion, String codigobarras, String unidadmedida, String categoria, String tipo) 
	{
        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaProducto () + "(id, nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo) values (?, ?, ?, ?, ?, ?, ?, ?)");
        q.setParameters(id, nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PRODUCTOS de la base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreProducto - El nombre del producto
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarProductoPorNombre (PersistenceManager pm, String nombreProducto)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto () + " WHERE nombre = ?");
        q.setParameters(nombreProducto);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para eliminar PRODUCTOS de la base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del producto
	 * @return EL n�mero de tuplas eliminadas
	 */
	public long eliminarProductoPorId (PersistenceManager pm, long idProducto)
	{
        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaProducto () + " WHERE id = ?");
        q.setParameters(idProducto);
        return (long) q.executeUnique();            
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de PRODUCTOS de la 
	 * base de datos de Parranderos, por su nombre
	 * @param pm - El manejador de persistencia
	 * @param nombreProducto - El nombre del producto
	 * @return Una lista de objetos PRODUCTO que tienen el nombre dado
	 */
	public List<Producto> darProductosPorNombre (PersistenceManager pm, String nombreProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto () + " WHERE nombre = ?");
		q.setResultClass(Producto.class);
		q.setParameters(nombreProducto);
		return (List<Producto>) q.executeList();
	}
	
	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la información de UN PRODUCTO de la 
	 * base de datos de Parranderos, por su identificador
	 * @param pm - El manejador de persistencia
	 * @param idProducto - El identificador del producto
	 * @return El objeto PRODUCTO que tiene el identificador dado
	 */
	public Producto darProductoPorId (PersistenceManager pm, long idProducto) 
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto () + " WHERE id = ?");
		q.setResultClass(Producto.class);
		q.setParameters(idProducto);
		return (Producto) q.executeUnique();
	}

	/**
	 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS PRODUCTOS de la 
	 * base de datos de Parranderos
	 * @param pm - El manejador de persistencia
	 * @return Una lista de objetos PRODUCTO
	 */
	public List<Producto> darProductos (PersistenceManager pm)
	{
		Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaProducto ());
		q.setResultClass(Producto.class);
		return (List<Producto>) q.executeList();
	}
	
	public List<Producto> darProductosPorCiudad(PersistenceManager pm, String ciudad)
	{
        String sql = "SELECT DISTINCT p.id, p.nombre, p.marca, p.presentacion, p.codigobarras, p.unidadmedida, p.categoria, p.tipo";
        sql += " FROM " + pp.darTablaProducto() + " p, " + pp.darTablaSucursal() + " s, " + pp.darTablaVende() + " v ";
		sql += " WHERE p.id = v.idproducto AND s.ciudad = ? ";		
		
		Query q = pm.newQuery(SQL, sql);
		q.setResultClass(Producto.class);
		q.setParameters(ciudad);
		return (List<Producto>) q.executeList();
	}
	
	/**
	 * Método que obtiene un la fecha de más ventas de un tipo de producto
	 * @param pm manejador de persistencia
	 * @param tipo de producto que se quiere revisar
	 * @return objeto de tipo AyudaRFC7 que tiene una fecha y una cantidad total de productos vendidos
	 */
	public AyudaRFC7 reqSiete(PersistenceManager pm, String tipo)
	{
		Query q = pm.newQuery(SQL, "SELECT mes, anio, MAX(cantidadtotal) as cantidadMaxima\r\n" + 
				"FROM(SELECT  mes, anio, sum(cantidad) as cantidadtotal\r\n" + 
				"FROM(SELECT\r\n" + 
				"     t.idproducto idproducto,\r\n" + 
				"     t.cantidad cantidad,\r\n" + 
				"     t.numerofactura factura,\r\n" + 
				"     t.costo costo,\r\n" + 
				"     EXTRACT(MONTH FROM f.fecha) as mes,\r\n" + 
				"     EXTRACT(YEAR FROM f.fecha) as anio\r\n" + 
				" FROM\r\n" + 
				"     a_transaccion t,\r\n" + 
				"     a_producto p,\r\n" + 
				"     a_factura f\r\n" + 
				" WHERE\r\n" + 
				"     t.idproducto = p.id\r\n" + 
				"     AND p.tipo = ? \r\n" + 
				"         AND t.numerofactura = f.numero) group by mes, anio\r\n" + 
				"         ORDER BY cantidadtotal DESC) group by mes, anio");
		q.setResultClass(AyudaRFC7.class);
		q.setParameters(tipo);
		return (AyudaRFC7) q.executeUnique();
	}
	
	/**
	 * Método que obtiene un objeto de tipo AyudaRFC72 
	 * @param pm manejador de persistencia
	 * @param tipo de producto que se quiere revisar
	 * @return objeto de tipo AyudaRFC72 que tiene una fecha y un costo total de productos vendidos
	 */
	public AyudaRFC72 reqSiete2(PersistenceManager pm, String tipo)
	{
		Query q = pm.newQuery(SQL, "SELECT mes, anio, MAX(costototal) as costomaximo\r\n" + 
				"FROM(SELECT  mes, anio, sum(costo) as costototal\r\n" + 
				"FROM(SELECT\r\n" + 
				"     t.idproducto idproducto,\r\n" + 
				"     t.cantidad cantidad,\r\n" + 
				"     t.numerofactura factura,\r\n" + 
				"     t.costo costo,\r\n" + 
				"     EXTRACT(MONTH FROM f.fecha) as mes,\r\n" + 
				"     EXTRACT(YEAR FROM f.fecha) as anio\r\n" + 
				" FROM\r\n" + 
				"     a_transaccion t,\r\n" + 
				"     a_producto p,\r\n" + 
				"     a_factura f\r\n" + 
				" WHERE\r\n" + 
				"     t.idproducto = p.id\r\n" + 
				"     AND p.tipo = ? \r\n" + 
				"         AND t.numerofactura = f.numero) group by mes, anio\r\n" + 
				"         ORDER BY costototal DESC) group by mes, anio");
		q.setResultClass(AyudaRFC72.class);
		q.setParameters(tipo);
		return (AyudaRFC72) q.executeUnique();
	}
}

