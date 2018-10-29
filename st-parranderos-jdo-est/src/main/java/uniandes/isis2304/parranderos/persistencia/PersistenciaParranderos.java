/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad	de	los	Andes	(Bogotá	- Colombia)
 * Departamento	de	Ingeniería	de	Sistemas	y	Computación
 * Licenciado	bajo	el	esquema	Academic Free License versión 2.1
 * 		
 * Curso: isis2304 - Sistemas Transaccionales
 * Proyecto: Parranderos Uniandes
 * @version 1.0
 * @author Germán Bravo
 * Julio de 2018
 * 
 * Revisado por: Claudia Jiménez, Christian Ariza
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package uniandes.isis2304.parranderos.persistencia;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.log4j.Logger;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import uniandes.isis2304.parranderos.negocio.Almacenamiento;
import uniandes.isis2304.parranderos.negocio.Bodega;
import uniandes.isis2304.parranderos.negocio.Carrito;
import uniandes.isis2304.parranderos.negocio.Estante;
import uniandes.isis2304.parranderos.negocio.Factura;
import uniandes.isis2304.parranderos.negocio.Pedido;
import uniandes.isis2304.parranderos.negocio.Producto;
import uniandes.isis2304.parranderos.negocio.Promocion;
import uniandes.isis2304.parranderos.negocio.Proveedor;
import uniandes.isis2304.parranderos.negocio.Sucursal;
import uniandes.isis2304.parranderos.negocio.Supermercado;
import uniandes.isis2304.parranderos.negocio.Vende;
import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.Contiene;
import uniandes.isis2304.parranderos.negocio.Ofrecen;
import uniandes.isis2304.parranderos.negocio.Subpedido;

/**
 * Clase para el manejador de persistencia del proyecto Parranderos
 * Traduce la información entre objetos Java y tuplas de la base de datos, en ambos sentidos
 * Sigue un patrón SINGLETON (Sólo puede haber UN objeto de esta clase) para comunicarse de manera correcta
 * con la base de datos
 * Se apoya en las clases SQLBar, SQLBebedor, SQLBebida, SQLGustan, SQLSirven, SQLTipoBebida y SQLVisitan, que son 
 * las que realizan el acceso a la base de datos
 * 
 * @author Germán Bravo
 */
public class PersistenciaParranderos 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(PersistenciaParranderos.class.getName());

	/**
	 * Cadena para indicar el tipo de sentencias que se va a utilizar en una consulta
	 */
	public final static String SQL = "javax.jdo.query.SQL";

	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;

	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaParranderos instance;

	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;



	/**
	 * Atributo para el acceso a la tabla SUPERMERCADO de la base de datos
	 */
	private SQLSupermercado sqlSupermercado;

	/**
	 * Atributo para el acceso a la tabla SUCURSAL de la base de datos
	 */
	private SQLSucursal sqlSucursal;

	/**
	 * Atributo para el acceso a la tabla PRODUCTO de la base de datos
	 */
	private SQLProducto sqlProducto;
	
	/**
	 * Atributo para el acceso a la tabla ALMACENAMIENTO de la base de datos
	 */
	private SQLAlmacenamiento sqlAlmacenamiento;

	/**
	 * Atributo para el acceso a la tabla VENDE de la base de datos
	 */
	private SQLVende sqlVende;

	/**
	 * Atributo para el acceso a la tabla PROVEEDOR de la base de datos
	 */
	private SQLProveedor sqlProveedor;

	/**
	 * Atributo para el acceso a la tabla PEDIDO de la base de datos
	 */
	private SQLPedido sqlPedido;

	/**
	 * Atributo para el acceso a la tabla SUBPEDIDO de la base de datos
	 */
	private SQLSubpedido sqlSubPedido;

	/**
	 * Atributo para el acceso a la tabla OFRECEN de la base de datos
	 */
	private SQLOfrecen sqlOfrecen;

	/**
	 * Atributo para el acceso a la tabla CLIENTE de la base de datos
	 */
	private SQLCliente sqlCliente;

	/**
	 * Atributo para el acceso a la tabla FACTURA de la base de datos
	 */
	private SQLFactura sqlFactura;

	/**
	 * Atributo para el acceso a la tabla CARRITO de la base de datos
	 */
	private SQLCarrito sqlCarrito;
	
	/**
	 * Atributo para el acceso a la tabla CONTIENE de la base de datos
	 */
	private SQLContiene sqlContiene;
	
	/**
	 * Atributo para el acceso a la tabla PROMOCION de la base de datos
	 */
	private SQLPromocion sqlPromocion;

	/**
	 * Atributo para el acceso a la tabla TRANSACCION de la base de datos
	 */
	private SQLTransaccion sqlTransaccion;

	/* ****************************************************************
	 * 			Métodos del MANEJADOR DE PERSISTENCIA
	 *****************************************************************/

	/**
	 * Constructor privado con valores por defecto - Patrón SINGLETON
	 */
	private PersistenciaParranderos ()
	{
		pmf = JDOHelper.getPersistenceManagerFactory("Parranderos");		
		crearClasesSQL ();

		// Define los nombres por defecto de las tablas de la base de datos
		tablas = new LinkedList<String> ();
		tablas.add ("Superandes_sequence");
		tablas.add ("SUPERMERCADO");
		tablas.add ("SUCURSAL");
		tablas.add ("PRODUCTO");
		tablas.add("ALMACENAMIENTO");
		tablas.add ("VENDE");
		tablas.add ("PROVEEDOR");
		tablas.add ("PEDIDO");
		tablas.add ("SUBPEDIDO");
		tablas.add ("OFRECEN");
		tablas.add ("CLIENTE");
		tablas.add ("FACTURA");
		tablas.add ("CARRITO");
		tablas.add ("CONTIENE");
		tablas.add ("PROMOCION");
		tablas.add ("TRANSACCION");
	}

	/**
	 * Constructor privado, que recibe los nombres de las tablas en un objeto Json - Patrón SINGLETON
	 * @param tableConfig - Objeto Json que contiene los nombres de las tablas y de la unidad de persistencia a manejar
	 */
	private PersistenciaParranderos (JsonObject tableConfig)
	{
		crearClasesSQL ();
		tablas = leerNombresTablas (tableConfig);

		String unidadPersistencia = tableConfig.get ("unidadPersistencia").getAsString ();
		log.trace ("Accediendo unidad de persistencia: " + unidadPersistencia);
		pmf = JDOHelper.getPersistenceManagerFactory (unidadPersistencia);
	}

	/**
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaParranderos getInstance ()
	{
		if (instance == null)
		{
			instance = new PersistenciaParranderos ();
		}
		return instance;
	}

	/**
	 * Constructor que toma los nombres de las tablas de la base de datos del objeto tableConfig
	 * @param tableConfig - El objeto JSON con los nombres de las tablas
	 * @return Retorna el único objeto PersistenciaParranderos existente - Patrón SINGLETON
	 */
	public static PersistenciaParranderos getInstance (JsonObject tableConfig)
	{
		if (instance == null)
		{
			instance = new PersistenciaParranderos (tableConfig);
		}
		return instance;
	}

	/**
	 * Cierra la conexión con la base de datos
	 */
	public void cerrarUnidadPersistencia ()
	{
		pmf.close ();
		instance = null;
	}

	/**
	 * Genera una lista con los nombres de las tablas de la base de datos
	 * @param tableConfig - El objeto Json con los nombres de las tablas
	 * @return La lista con los nombres del secuenciador y de las tablas
	 */
	private List <String> leerNombresTablas (JsonObject tableConfig)
	{
		JsonArray nombres = tableConfig.getAsJsonArray("tablas") ;

		List <String> resp = new LinkedList <String> ();
		for (JsonElement nom : nombres)
		{
			resp.add (nom.getAsString ());
		}

		return resp;
	}

	/**
	 * Crea los atributos de clases de apoyo SQL
	 */
	private void crearClasesSQL ()
	{	
		sqlUtil = new SQLUtil(this);
		sqlSupermercado = new SQLSupermercado(this);
		sqlSucursal = new SQLSucursal(this);
		sqlProducto = new SQLProducto(this);
		sqlAlmacenamiento = new SQLAlmacenamiento(this);
		sqlVende = new SQLVende(this);
		sqlProveedor = new SQLProveedor(this);
		sqlPedido = new SQLPedido(this);
		sqlSubPedido = new SQLSubpedido(this);
		sqlOfrecen = new SQLOfrecen(this);
		sqlCliente = new SQLCliente(this);
		sqlFactura = new SQLFactura(this);
		sqlCarrito = new SQLCarrito (this);
		sqlContiene = new SQLContiene(this);
		sqlPromocion = new SQLPromocion(this);
		sqlTransaccion = new SQLTransaccion(this);
	}

	

	/**
	 * Transacción para el generador de secuencia de Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return El siguiente número del secuenciador de Parranderos
	 */
	private long nextval ()
	{
		long resp = sqlUtil.nextval (pmf.getPersistenceManager());
		log.trace ("Generando secuencia: " + resp);
		return resp;
	}

	/**
	 * Extrae el mensaje de la exception JDODataStoreException embebido en la Exception e, que da el detalle específico del problema encontrado
	 * @param e - La excepción que ocurrio
	 * @return El mensaje de la excepción JDO
	 */
	private String darDetalleException(Exception e) 
	{
		String resp = "";
		if (e.getClass().getName().equals("javax.jdo.JDODataStoreException"))
		{
			JDODataStoreException je = (javax.jdo.JDODataStoreException) e;
			return je.getNestedExceptions() [0].getMessage();
		}
		return resp;
	}



	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * Crea y ejecuta las sentencias SQL para cada tabla de la base de datos - EL ORDEN ES IMPORTANTE 
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long [] resp = sqlUtil.limpiarParranderos (pm);
			tx.commit ();
			log.info ("Borrada la base de datos");
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return new long[] {-1, -1, -1, -1, -1, -1, -1};
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}

	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de superandes
	 */
	public String darSeqSuperandes ()
	{
		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Supermercado de superandes
	 */
	public String darTablaSupermercado()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sucursal de superandes
	 */
	public String darTablaSucursal()
	{
		return tablas.get (2);
	}

	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Producto de superandes
	 */
	public String darTablaProducto()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Almacenamiento de superandes
	 */
	public String darTablaAlmacenamiento()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Vende de superandes
	 */
	public String darTablaVende()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Proveedor de superandes
	 */
	public String darTablaProveedor()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Pedido de superandes
	 */
	public String darTablaPedido()
	{
		return tablas.get (7);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Subpedido de superandes
	 */
	public String darTablaSubpedido()
	{
		return tablas.get (8);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Ofrecen de superandes
	 */
	public String darTablaOfrecen()
	{
		return tablas.get (9);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Cliente de superandes
	 */
	public String darTablaCliente()
	{
		return tablas.get (10);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Factura de superandes
	 */
	public String darTablaFactura()
	{
		return tablas.get (11);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Carrito de superandes
	 */
	public String darTablaCarrito()
	{
		return tablas.get (12);
	}
	
	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Contiene de superandes
	 */
	public String darTablaContiene()
	{
		return tablas.get (13);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Promocion de superandes
	 */
	public String darTablaPromocion()
	{
		return tablas.get (14);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Transaccion de superandes
	 */
	public String darTablaTransaccion()
	{
		return tablas.get (15);
	}




	/* ****************************************************************
	 * 			MÃ©todos para manejar los SUPERMERCADOS
	 *****************************************************************/

	/**
	 * MÃ©todo que inserta, de manera transaccional, una tupla en la tabla SUPERMERCADO
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param nombre - El nombre del supermercado
	 * @return El objeto Supermercado adicionado. null si ocurre alguna Excepciï¿½n
	 */
	public Supermercado adicionarSupermercado(String nombre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlSupermercado.adicionarSupermercado(pm, nombre);
			tx.commit();

			log.trace ("InserciÃ³n de producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Supermercado(nombre);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * MÃ©todo que elimina, de manera transaccional, una tupla en la tabla Supermercado, dado el nombre del supermercado
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param nombre - El nombre del supermercados
	 * @return El nÃºmero de tuplas eliminadas. -1 si ocurre alguna ExcepciÃ³n
	 */
	public long eliminarSupermercadoPorNombre (String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlSupermercado.eliminarSupermercadoPorNombre(pm, nombre);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * MÃ©todo que consulta todas las tuplas en la tabla Supermercado con un nombre dado
	 * @param nombre - El nombre del supermercado
	 * @return El objeto Supermercado, construido con base en las tuplas de la tabla SUPERMERCADO con el identificador de nombre dado
	 */
	public Supermercado darSupermercadoPorNombre (String nombre)
	{
		return sqlSupermercado.darSupermercadoPorNombre(pmf.getPersistenceManager(), nombre);
	}

	/**
	 * MÃ©todo que consulta todas las tuplas en la tabla Supermercado
	 * @return La lista de objetos Supermercado, construidos con base en las tuplas de la tabla SUPERMERCADO
	 */
	public List<Supermercado> darSupermercados()
	{
		return sqlSupermercado.darSupermercados(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			MÃ©todos para manejar las SUCURSALES
	 *****************************************************************/
	/**
	 * MÃ©todo que inserta, de manera transaccional, una tupla en la tabla SUCURSAL
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param nombre - El nombre de la sucursal
	 * @param ciudad - Ciudad de la sucursal
	 * @param direccion - Direccion de la sucursal
	 * @param segmentomercado - Segmento de mercado de la sucursal
	 * @param tamano - TamaÃ±o de la sucursal (en metros cuadrados)
	 * @param supermercado - El supermercado al que pertenece la sucursal
	 * @return El objeto Sucursal adicionado. null si ocurre alguna Excepciï¿½n
	 */
	public Sucursal adicionarSucursal(String nombre, String ciudad, String direccion, String segmentomercado, int tamano, String supermercado)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idSucursal = nextval();
			long tuplasInsertadas = sqlSucursal.adicionarSucursal(pm, idSucursal, nombre, ciudad, direccion, segmentomercado, tamano, supermercado);
			tx.commit();

			log.trace ("InserciÃ³n de sucursal: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Sucursal(idSucursal, nombre, ciudad, direccion, segmentomercado, tamano, supermercado);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * MÃ©todo que elimina, de manera transaccional, una tupla en la tabla Sucursal, dado el identificador de la sucursal
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param idSucursal - El identificador de la sucursal
	 * @return El nÃºmero de tuplas eliminadas. -1 si ocurre alguna ExcepciÃ³n
	 */
	public long eliminarSucursalPorId (long idSucursal) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlSucursal.eliminarSucursalPorId(pm, idSucursal);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * MÃ©todo que elimina, de manera transaccional, una tupla en la tabla Sucursal, dado el nombre de la sucursal
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param nombre - El nombre de la sucursal
	 * @return El nÃºmero de tuplas eliminadas. -1 si ocurre alguna ExcepciÃ³n
	 */
	public long eliminarSucursalPorNombre (String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlSucursal.eliminarSucursalesPorNombre(pm, nombre);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * MÃ©todo que consulta todas las tuplas en la tabla Sucursal con un identificador dado
	 * @param idSucursal - El identificador de la sucursal
	 * @return El objeto Sucursal, construido con base en las tuplas de la tabla SUCURSAL con el identificador dado
	 */
	public Sucursal darSucursalPorId(long idSucursal)
	{
		return sqlSucursal.darSucursalPorId(pmf.getPersistenceManager(), idSucursal);
	}

	/**
	 * MÃ©todo que consulta todas las tuplas en la tabla Sucursal que tienen un supermercado dado
	 * @param supermercado - El supermercado al que pertenece la sucursal
	 * @return La lista de objetos Sucursal, construidos con base en las tuplas de la tabla SUCURSAL
	 */
	public List<Sucursal> darSucursalesPorSupermercado(String supermercado)
	{
		return sqlSucursal.darSucursalesPorSupermercado(pmf.getPersistenceManager(), supermercado);
	}

	/**
	 * MÃ©todo que consulta todas las tuplas en la tabla Sucursal
	 * @return La lista de objetos Sucursal, construidos con base en las tuplas de la tabla SUCURSAL
	 */
	public List<Sucursal> darSucursales()
	{
		return sqlSucursal.darSucursales(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			MÃ©todos para manejar los PRODUCTOS
	 *****************************************************************/

	/**
	 * MÃ©todo que inserta, de manera transaccional, una tupla en la tabla PRODUCTO
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param nombre - El nombre del producto
	 * @param marca - Marca del producto
	 * @param presentacion - Presentacion del producto
	 * @param codigobarras - CÃ³digo de barras del producto
	 * @param unidadmedida - Las unidades de medida del producto
	 * @param categoria - La categoria del producto (perecederos, no perecederos, aseo, abarrotes, etc)
	 * @param tipo - El tipo de producto por categoria
	 * @return El objeto Producto adicionado. null si ocurre alguna Excepciï¿½n
	 */
	public Producto adicionarProducto(String nombre, String marca, String presentacion, String codigobarras, String unidadmedida, String categoria, String tipo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idProducto = nextval ();
			long tuplasInsertadas = sqlProducto.adicionarProducto(pm, idProducto, nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo);
			tx.commit();

			log.trace ("Inserciï¿½n de producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Producto(idProducto, nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * MÃ©todo que elimina, de manera transaccional, una tupla en la tabla Producto, dado el nombre del producto
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param nombre - El nombre del producto
	 * @return El nÃºmero de tuplas eliminadas. -1 si ocurre alguna ExcepciÃ³n
	 */
	public long eliminarProductoPorNombre (String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlProducto.eliminarProductoPorNombre(pm, nombre);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * MÃ©todo que elimina, de manera transaccional, una tupla en la tabla Producto, dado el identificador del producto
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param idProducto - El identificador del producto
	 * @return El nÃºmero de tuplas eliminadas. -1 si ocurre alguna ExcepciÃ³n
	 */
	public long eliminarProductoPorId (long idProducto) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlProducto.eliminarProductoPorId(pm, idProducto);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * MÃ©todo que consulta todas las tuplas en la tabla Producto que tienen un nombre dado
	 * @param nombre - El nombre del producto
	 * @return La lista de objetos Producto, construidos con base en las tuplas de la tabla PRODUCTO
	 */
	public List<Producto> darProductosPorNombre(String nombre)
	{
		return sqlProducto.darProductosPorNombre(pmf.getPersistenceManager(), nombre);
	}

	/**
	 * MÃ©todo que consulta todas las tuplas en la tabla Producto
	 * @return La lista de objetos Producto, construidos con base en las tuplas de la tabla PRODUCTO
	 */
	public List<Producto> darProductos()
	{
		return sqlProducto.darProductos(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			MÃ©todos para manejar las BODEGAS
	 *****************************************************************/

//	/**
//	 * MÃ©todo que inserta, de manera transaccional, una tupla en la tabla BODEGAs
//	 * Adiciona entradas al log de la aplicaciÃ³n
//	 * @param capacidadVolumen - La capacidad en volumen de la bodega (metros cÃºbicos)
//	 * @param capacidadPeso - La capacidad en peso de la bodega (en kg)
//	 * @param producto - Identificador del producto que almacena la bodega
//	 * @param sucursal - La sucursal a la que pertenece la bodega
//	 * @param existencias - Las existencias disponibles en la bodega
//	 * @return El objeto Bodega adicionado. null si ocurre alguna ExcepciÃ³n
//	 */
//	public Bodega adicionarBodega(double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int existencias)
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//		Transaction tx=pm.currentTransaction();
//		try
//		{
//			tx.begin();
//			long idBodega = nextval ();
//			long tuplasInsertadas = sqlBodega.adicionarBodega(pm, idBodega, capacidadVolumen, capacidadPeso, producto, sucursal, existencias);
//			tx.commit();
//
//			log.trace ("InserciÃ³n de la bodega: " + idBodega + ": " + tuplasInsertadas + " tuplas insertadas");
//
//			return new Bodega(idBodega, capacidadVolumen, capacidadPeso, existencias, producto, sucursal);
//		}
//		catch (Exception e)
//		{
//			//        	e.printStackTrace();
//			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//			return null;
//		}
//		finally
//		{
//			if (tx.isActive())
//			{
//				tx.rollback();
//			}
//			pm.close();
//		}
//	}
//
//	/**
//	 * MÃ©todo que elimina, de manera transaccional, una tupla en la tabla Bodega, dado el identificador de la bodega
//	 * Adiciona entradas al log de la aplicaciÃ³n
//	 * @param idBodega - El identificador de la bodega
//	 * @return El nÃºmero de tuplas eliminadas. -1 si ocurre alguna ExcepciÃ³n
//	 */
//	public long eliminarBodegaPorId (long idBodega) 
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//		Transaction tx=pm.currentTransaction();
//		try
//		{
//			tx.begin();
//			long resp = sqlBodega.eliminarBodegaPorId(pm, idBodega);
//			tx.commit();
//			return resp;
//		}
//		catch (Exception e)
//		{
//			//        	e.printStackTrace();
//			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//			return -1;
//		}
//		finally
//		{
//			if (tx.isActive())
//			{
//				tx.rollback();
//			}
//			pm.close();
//		}
//	}
//
//	/**
//	 * MÃ©todo que consulta todas las tuplas en la tabla Bodega con un identificador dado
//	 * @param idBodega - El identificador de la bodega
//	 * @return El objeto Bodega, construido con base en las tuplas de la tabla BODEGA con el identificador dado
//	 */
//	public Bodega darBodegaPorId(long idBodega)
//	{
//		return sqlBodega.darBodegaPorId(pmf.getPersistenceManager(), idBodega);
//	}
//
//	/**
//	 * MÃ©todo que consulta todas las tuplas en la tabla Bodega que tienen una sucursal dada
//	 * @param sucursal - La sucursal a la que pertenece la bodega
//	 * @return La lista de objetos Bodega, construidos con base en las tuplas de la tabla BODEGA
//	 */
//	public List<Bodega> darBodegasPorSucursal(long sucursal)
//	{
//		return sqlBodega.darBodegasPorSucursal(pmf.getPersistenceManager(), sucursal);
//	}
//
//	/**
//	 * MÃ©todo que consulta todas las tuplas en la tabla Bodega
//	 * @return La lista de objetos Bodega, construidos con base en las tuplas de la tabla BODEGA
//	 */
//	public List<Bodega> darBodegas()
//	{
//		return sqlBodega.darBodegas(pmf.getPersistenceManager());
//	}
//
//	/**
//	 * MÃ©todo que aumenta las existencias en 10 unidades de una bodega con id dado
//	 * @return Las tuplas modificadas con el aumento de existencias
//	 */
//	public long aumentarExistenciasBodegaEnDiez(long idBodega)
//	{
//		return sqlBodega.aumentarExistenciasBodegasEnDiez(pmf.getPersistenceManager(), idBodega);
//	}
//
//	/* ****************************************************************
//	 * 			MÃ©todos para manejar los ESTANTES
//	 *****************************************************************/
//
//	/**
//	 * MÃ©todo que inserta, de manera transaccional, una tupla en la tabla ALMACENAMIENTO
//	 * Adiciona entradas al log de la aplicaciÃ³n
//	 * @param capacidadVolumen - La capacidad en volumen del estante(metros cÃºbicos)
//	 * @param capacidadPeso - La capacidad en peso del estante (en kg)
//	 * @param producto - Identificador del producto que almacena el estante
//	 * @param sucursal - La sucursal a la que pertenece el estante
//	 * @param nivelabastecimientobodega - Cantidad de unidades mÃ­nimas que debe tener en la bodega por producto
//	 * @param existencias - Las existencias disponibles en la bodega
//	 * @param capacidadproductos numero de productos que se pueden almacenar
//	 * @param tipo Tipo de almacenamiento (bodega o estante)
//	 * @return El objeto Estante adicionado. null si ocurre alguna ExcepciÃ³n
//	 */
//	public Almacenamiento adicionarEstante(double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int nivelabastecimientobodega, int existencias, int capacidadproductos, String tipo)
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//		Transaction tx=pm.currentTransaction();
//		try
//		{
//			tx.begin();
//			long idAlmacenamiento = nextval ();
//			long tuplasInsertadas = sqlAlmacenamiento.adicionarAlmacenamiento(pm, idAlmacenamiento, capacidadVolumen, capacidadPeso, producto, sucursal, nivelabastecimientobodega, existencias, capacidadproductos, tipo);
//			tx.commit();
//
//			log.trace ("InserciÃ³n del almacenamiento: " + idAlmacenamiento + ": " + tuplasInsertadas + " tuplas insertadas");
//
//			return new Almacenamiento(idAlmacenamiento, capacidadVolumen, capacidadPeso, existencias, producto, sucursal, nivelabastecimientobodega, capacidadproductos, tipo);
//		}
//		catch (Exception e)
//		{
//			//        	e.printStackTrace();
//			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//			return null;
//		}
//		finally
//		{
//			if (tx.isActive())
//			{
//				tx.rollback();
//			}
//			pm.close();
//		}
//	}
//
//	/**
//	 * MÃ©todo que elimina, de manera transaccional, una tupla en la tabla Estante, dado el identificador del estante
//	 * Adiciona entradas al log de la aplicaciÃ³n
//	 * @param idBodega - El identificador del estante
//	 * @return El nÃºmero de tuplas eliminadas. -1 si ocurre alguna ExcepciÃ³n
//	 */
//	public long eliminarEstantePorId (long idEstante) 
//	{
//		PersistenceManager pm = pmf.getPersistenceManager();
//		Transaction tx=pm.currentTransaction();
//		try
//		{
//			tx.begin();
//			long resp = sqlEstante.eliminarEstantePorId(pm, idEstante);
//			tx.commit();
//			return resp;
//		}
//		catch (Exception e)
//		{
//			//        	e.printStackTrace();
//			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//			return -1;
//		}
//		finally
//		{
//			if (tx.isActive())
//			{
//				tx.rollback();
//			}
//			pm.close();
//		}
//	}
//
//	/**
//	 * MÃ©todo que consulta todas las tuplas en la tabla Estante con un identificador dado
//	 * @param idEstante - El identificador del estante
//	 * @return El objeto Estante, construido con base en las tuplas de la tabla ESTANTE con el identificador dado
//	 */
//	public Estante darEstantePorId(long idEstante)
//	{
//		return sqlEstante.darEstantePorId(pmf.getPersistenceManager(), idEstante);
//	}
//
//	/**
//	 * MÃ©todo que consulta todas las tuplas en la tabla Estante que tienen una sucursal dada
//	 * @param sucursal - La sucursal a la que pertenece al estante
//	 * @return La lista de objetos Estante, construidos con base en las tuplas de la tabla ESTANTE
//	 */
//	public List<Estante> darEstantesPorSucursal(long sucursal)
//	{
//		return sqlEstante.darEstantesPorSucursal(pmf.getPersistenceManager(), sucursal);
//	}
//
//	/**
//	 * MÃ©todo que consulta todas las tuplas en la tabla Estante
//	 * @return La lista de objetos Estante, construidos con base en las tuplas de la tabla ESTANTEs
//	 */
//	public List<Estante> darEstantes()
//	{
//		return sqlEstante.darEstantes(pmf.getPersistenceManager());
//	}

	/* ****************************************************************
	 * 			MÃ©todos para manejar la relaciÃ³n VENDE
	 *****************************************************************/

	/* ****************************************************************
	 * 			MÃ©todos para manejar los PROVEEDORES
	 *****************************************************************/

	/**
	 * MÃ©todo que inserta, de manera transaccional, una tupla en la tabla PROVEEDOR
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param nombre - El nombre del proveedor
	 * @param calificacion - La calificacion que tiene el proveedor
	 * @return El objeto Proveedor adicionado. null si ocurre alguna ExcepciÃ³n
	 */
	public Proveedor adicionarProveedor(String nombre, int calificacion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idProveedor = nextval ();
			long tuplasInsertadas = sqlProveedor.adicionarProveedor(pm, idProveedor, nombre, calificacion);
			tx.commit();

			log.trace ("InserciÃ³n del proveedor: " + idProveedor + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Proveedor(idProveedor, nombre, calificacion);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * MÃ©todo que elimina, de manera transaccional, una tupla en la tabla Proveedor, dado el nombre del proveedor
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param nombre - El nombre del proveedor
	 * @return El nÃºmero de tuplas eliminadas. -1 si ocurre alguna ExcepciÃ³n
	 */
	public long eliminarProoveedorPorNombre (String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlProveedor.eliminarProveedorPorNombre(pm, nombre);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * MÃ©todo que elimina, de manera transaccional, una tupla en la tabla Proveedor, dado el identificador del proveedor
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param idProveedor - El identificador del proveedor
	 * @return El nÃºmero de tuplas eliminadas. -1 si ocurre alguna ExcepciÃ³n
	 */
	public long eliminarProveedorPorId (long idProveedor) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlProveedor.eliminarProveedorPorId(pm, idProveedor);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * MÃ©todo que consulta todas las tuplas en la tabla Proveedor que tienen un nombre dado
	 * @param nombre - El nombre del proveedor
	 * @return La lista de objetos Proveedor, construidos con base en las tuplas de la tabla PROVEEDOR
	 */
	public List<Proveedor> darProveedoresPorNombre(String nombre)
	{
		return sqlProveedor.darProveedorPorNombre(pmf.getPersistenceManager(), nombre);
	}

	/**
	 * MÃ©todo que consulta todas las tuplas en la tabla Proveedor
	 * @return La lista de objetos Proveedor, construidos con base en las tuplas de la tabla PROVEEDOR
	 */
	public List<Proveedor> darProveedores()
	{
		return sqlProveedor.darProveedores(pmf.getPersistenceManager());
	}	

	/* ****************************************************************
	 * 			MÃ©todos para manejar los PEDIDOS
	 *****************************************************************/

	/**
	 * MÃ©todo que inserta, de manera transaccional, una tupla en la tabla PEDIDO
	 * Adiciona entradas al log de la aplicaciÃ³n. La clase subpedido existe para asociar solicitudes de diferentes producto a un pedido.
	 * @param proveedor - El proveedor del pedido
	 * @param sucursal - La sucursal que hace el pedido
	 * @param fechaEntrega - La fecha de entrega del pedido
	 * @param estadoOrden - El estado de orden del pedido
	 * @param cantidad - El numero de unidades solicitadas
	 * @param calificacion - La calificacion del pedido
	 * @param costoTotal - El costo total del pedido
	 * @return El objeto Proveedor adicionado. null si ocurre alguna ExcepciÃ³n
	 */
	public Pedido adicionarPedido(long proveedor, long sucursal, Timestamp fechaEntrega, String estadoOrden, int cantidad, int calificacion, long producto)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			Ofrecen of = sqlOfrecen.darOfrecenPorProveedorYProducto(pm, proveedor, producto);
			double costoP = of.getCosto()*cantidad;
			tx.commit();

			tx.begin();
			long idPedido = nextval ();
			long tuplasInsertadas = sqlPedido.adicionarPedido(pm, idPedido, proveedor, sucursal, fechaEntrega, estadoOrden, cantidad, calificacion, costoP);
			tx.commit();

			tx.begin();
			long tuplasInsertadas2 = sqlSubPedido.adicionarSubPedido(pm, idPedido, producto, cantidad, costoP);
			tx.commit();

			log.trace ("Inserción del pedido: " + idPedido + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Pedido(idPedido, proveedor, sucursal, fechaEntrega, estadoOrden, cantidad, calificacion, costoP);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Pedido, dado el identificador del pedido
	 * Adiciona entradas al log de la aplicación
	 * @param idPedido - El identificador del pedido
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarPedidoPorId (long idPedido) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlPedido.eliminarPedidoPorId(pm, idPedido);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Pedido con un identificador dado
	 * @param idPedido - El identificador del Pedido
	 * @return El objeto Pedido, construido con base en las tuplas de la tabla PEDIDO con el identificador dado
	 */
	public Pedido darPedidoPorId(long idPedido)
	{
		return sqlPedido.darPedidoPorId(pmf.getPersistenceManager(), idPedido);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Pedido que tienen un identificador dado
	 * @param idPedido - El identificador del pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidosPorId(long idPedido)
	{
		return sqlPedido.darPedidosPorId(pmf.getPersistenceManager(), idPedido);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Pedido que tienen una sucursal dada
	 * @param idSucursal - El identificador de la sucursal que hace el pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidosPorSucursal(long idSucursal)
	{
		return sqlPedido.darPedidosPorSucursal(pmf.getPersistenceManager(), idSucursal);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Pedido que tienen un proveedor dado
	 * @param idProveedor - El identificador del proveedor que hace el pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidosPorProveedor(long idProveedor)
	{
		return sqlPedido.darPedidosPorProveedor(pmf.getPersistenceManager(), idProveedor);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Pedido que tienen una sucursal dada y un proveedor dado
	 * @param idSucursal - El identificador de la sucursal que hace el pedido
	 * @param idProveedor - El identificador del proveedor que hace el pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidosPorProveedorYSucursal(long idProveedor, long idSucursal)
	{
		return sqlPedido.darPedidosPorProveedorYSucursal(pmf.getPersistenceManager(), idProveedor, idSucursal);
	}

	/**
	 * Método que cambia el estado de orden de un pedido (entregado o pendiente) dependiendo de si el pedido se emitió o si ya fue entregado.
	 * @param idPedido - el identificador del pedido
	 * @param estadoOrden - El estado de orden del pedido (pendiente, entregado)
	 * @return El número de tuplas modificadas
	 */
	public long cambiarEstadoOrdenPedido(long idPedido)
	{
		return sqlPedido.cambiarEstadoOrdenPedido(pmf.getPersistenceManager(), idPedido);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Pedido que tienen una calificacion dada
	 * @param calificacion - La calificacion del pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidosPorCalificacion(int calificacion)
	{
		return sqlPedido.darPedidosPorCalificacion(pmf.getPersistenceManager(), calificacion);
	}

	/**
	 * Método que cambia la calificacion de un pedido dado
	 * @param idPedido - El estado de orden del pedido (pendiente, entregado)
	 * @param calificacion - La calificacion a cambiar del pedido
	 * @return El número de tuplas modificadas
	 */
	public long cambiarCalificacionPedido(long idPedido, int calificacion)
	{
		return sqlPedido.cambiarCalificacionPedido(pmf.getPersistenceManager(), idPedido, calificacion);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Pedido que tienen una fecha de entrega dada dada
	 * @param fechaEntrega- La fecha de entrega del pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidosPorFechaEntrega(Timestamp fechaEntrega)
	{
		return sqlPedido.darPedidosPorFechaEntrega(pmf.getPersistenceManager(), fechaEntrega);
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Pedido, la cual su estado de orden es 'Entregado'
	 * Adiciona entradas al log de la aplicación
	 * @param estadoOrden - El estado de orden del pedido
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarPedidosTerminados (String estadoOrden) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			estadoOrden = "Entregado";
			tx.begin();
			long resp = sqlPedido.eliminarPedidosTerminados(pm, estadoOrden);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Pedido
	 * @return La lista de objetos Pedido, construidos con base en las tuplas de la tabla PEDIDO
	 */
	public List<Pedido> darPedidos()
	{
		return sqlPedido.darPedidos(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			Métodos para manejar los SUBPEDIDOS
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla SUBPEDIDO
	 * Adiciona entradas al log de la aplicación
	 * @param producto - El producto del subpedido
	 * @param cantidad - La cantidad de unidades pedidas por producto
	 * @param costo - El costo del subpedido
	 * @return El objeto Subpedido adicionado. null si ocurre alguna Excepción
	 */
	public Subpedido adicionarSubPedido(long producto, int cantidad, double costo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			//adicionar subpedido

			tx.begin();
			long idPedido = nextval ();
			long tuplasInsertadas = sqlSubPedido.adicionarSubPedido(pm, idPedido, producto, cantidad, costo);
			tx.commit();

			log.trace ("Inserción del pedido: " + idPedido + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Subpedido(producto, idPedido, cantidad, costo);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Subpedido, dado el identificador del pedido
	 * Adiciona entradas al log de la aplicación
	 * @param idPedido - El identificador del pedido
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarSubPedidoPorId (long idPedido) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlSubPedido.eliminarSubPedidoPorId(pm, idPedido);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Subpedido con un identificador dado
	 * @param idPedido - El identificador del Pedido
	 * @return El objeto Subpedido, construido con base en las tuplas de la tabla SUBPEDIDO con el identificador dado
	 */
	public Subpedido darSubPedidoPorId(long idPedido)
	{
		return sqlSubPedido.darSubPedidoPorId(pmf.getPersistenceManager(), idPedido);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Subpedido que tienen un producto dado
	 * @param producto - El identificador del producto pedido
	 * @return La lista de objetos Subpedido, construidos con base en las tuplas de la tabla SUBPEDIDO
	 */
	public List<Subpedido> darSubPedidosPorProducto(long producto)
	{
		return sqlSubPedido.darSubPedidosPorProducto(pmf.getPersistenceManager(), producto);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Subpedido
	 * @return La lista de objetos Subpedido, construidos con base en las tuplas de la tabla `SUBPEDIDO
	 */
	public List<Subpedido> darSubPedidos()
	{
		return sqlSubPedido.darSubPedidos(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			Métodos para manejar la relación OFRECEN
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla OFRECEN
	 * Adiciona entradas al log de la aplicación
	 * @param idProducto - El identificador del producto
	 * @param idProveedor - El identificador del proveedor
	 * @param costo - el costo del producto según el proveedor
	 * @return El objeto Ofrecen adicionado. null si ocurre alguna Excepción
	 */
	public Ofrecen adicionarOfrecen(long idProducto, long idProveedor, double costo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlOfrecen.adicionarOfrecen(pm, idProducto, idProveedor, costo);
			tx.commit();

			log.trace ("Inserción de ofrecen: [" + idProducto + ", " + idProveedor + "]. " + tuplasInsertadas + " tuplas insertadas");

			return new Ofrecen(idProducto, idProveedor, costo);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Ofrecen, dado el identificador del producto y del proveedor
	 * Adiciona entradas al log de la aplicación
	 * @param idProducto - El identificador del producto
	 * @param idProveedor - El identificador del proveedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarOfrecen(long idProducto, long idProveedor) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlOfrecen.eliminarOfrecen(pm, idProducto, idProveedor);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Ofrecen
	 * @return La lista de objetos Ofrecen, construidos con base en las tuplas de la tabla OFRECEN
	 */
	public List<Ofrecen> darOfrecen()
	{
		return sqlOfrecen.darOfrecen(pmf.getPersistenceManager());
	}

	/**
	 * Método que encuentra el identificador y el número de productos que ofrecen los proveedores
	 * @return Una lista de parejas de objetos, el primer elemento de cada pareja representa el identificador de un proveedor,
	 * 	el segundo elemento representa el productos que ofrece.
	 */
	public List<Object []> darProveedorYCantidadProductosOfrecen()
	{
		return sqlOfrecen.darProveedorYCantidadProductosOfrecen(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			Métodos para manejar los CLIENTES
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla CLIENTE
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del cliente
	 * @param correo - El correo del cliente
	 * @param tipo - El tipo de cliente(PERSONA, EMPRESA)
	 * @param direccion - La direccion del cliente
	 * @return El objeto Cliente adicionado. null si ocurre alguna Excepción
	 */
	public Cliente adicionarCliente(String nombre, String correo, String tipo, String direccion)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{

			tx.begin();
			long idCliente = nextval();
			long tuplasInsertadas = sqlCliente.adicionarCliente(pm, idCliente, nombre, correo, tipo, direccion);
			tx.commit();

			log.trace ("Inserción del cliente: " + idCliente + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Cliente(idCliente, nombre, direccion, correo, tipo);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Cliente, dado el identificador del cliente
	 * Adiciona entradas al log de la aplicación
	 * @param idCliente - El identificador del cliente
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarClientePorId (long idCliente) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCliente.eliminarClientePorId(pm, idCliente);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Cliente, dado el nombre del cliente
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del cliente
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarClientePorNombre(String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlCliente.eliminarClientePorNombre(pm, nombre);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Cliente con un identificador dado
	 * @param idCliente - El identificador del Cliente
	 * @return El objeto Cliente, construido con base en las tuplas de la tabla CLIENTE con el identificador dado
	 */
	public Cliente darClientePorId(long idCliente)
	{
		return sqlCliente.darClientePorId(pmf.getPersistenceManager(), idCliente);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Cliente que tienen un nombre dado
	 * @param nombre - El nombre del cliente
	 * @return La lista de objetos Cliente, construidos con base en las tuplas de la tabla CLIENTE
	 */
	public List<Cliente> darClientesPorNombre(String nombre)
	{
		return sqlCliente.darClientesPorNombre(pmf.getPersistenceManager(), nombre);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Cliente que tienen un tipo dado (PERSONA, EMPRESA)
	 * @param tipo - El tipo del cliente (PERSONA, EMPRESA)
	 * @return La lista de objetos Cliente, construidos con base en las tuplas de la tabla CLIENTE
	 */
	public List<Cliente> darClientesPorTipo(String tipo)
	{
		return sqlCliente.darClientesPorTipo(pmf.getPersistenceManager(), tipo);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Cliente
	 * @return La lista de objetos Cliente, construidos con base en las tuplas de la tabla CLIENTE
	 */
	public List<Cliente> darClientes()
	{
		return sqlCliente.darClientes(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			Métodos para manejar las FACTURAS
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla FACTURA
	 * Adiciona entradas al log de la aplicación
	 * @param fecha - Fecha de la factura
	 * @param idCliente - El identificador del cliente de la factura
	 * @param idSucursal - El identificador de la sucursal donde se generó la factura
	 * @return El objeto Factura adicionado. null si ocurre alguna Excepción
	 */
	public Factura adicionarFactura(Timestamp fecha, String idCliente, long idSucursal)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{

			tx.begin();
			long idFactura = nextval();
			long tuplasInsertadas = sqlFactura.adicionarFactura(pm, idFactura, fecha, idCliente, idSucursal);
			tx.commit();

			log.trace ("Inserción del cliente: " + idCliente + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Factura(idFactura, idSucursal, fecha, idCliente);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Factura, dado el identificador de la factura
	 * Adiciona entradas al log de la aplicación
	 * @param idFactura - El identificador de la factura
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarFacturaPorId (long idFactura) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlFactura.eliminarFacturaPorNumero(pm, idFactura);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Factura con un identificador dado
	 * @param idFactura- El identificador de la Factura
	 * @return El objeto Factura, construido con base en las tuplas de la tabla FACTURA con el identificador dado
	 */
	public Factura darFacturaPorId(long idFactura)
	{
		return sqlFactura.darFacturaPorNumero(pmf.getPersistenceManager(), idFactura);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Factura que tienen un identificador dado
	 * @param idFactura - El identificador de la factura
	 * @return La lista de objetos Factura, construidos con base en las tuplas de la tabla FACTURA
	 */
	public List<Factura> darFacturasPorId(long idFactura)
	{
		return sqlFactura.darFacturasPorNumero(pmf.getPersistenceManager(), idFactura);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Factura que tienen un cliente dado
	 * @param idCliente - El identificador del cliente de la factura
	 * @return La lista de objetos Factura, construidos con base en las tuplas de la tabla FACTURA
	 */
	public List<Factura> darFacturasPorCliente(long idCliente)
	{
		return sqlFactura.darFacturasPorCliente(pmf.getPersistenceManager(), idCliente);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Factura que tienen una sucursal dada
	 * @param idSucursal - El identificador de la sucursal de la factura
	 * @return La lista de objetos Factura, construidos con base en las tuplas de la tabla FACTURA
	 */
	public List<Factura> darFacturasPorSucursal(long idSucursal)
	{
		return sqlFactura.darFacturasPorSucursal(pmf.getPersistenceManager(), idSucursal);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Factura que tienen una fecha dada
	 * @param fecha - La fecha en la que se generó la factura
	 * @return La lista de objetos Factura, construidos con base en las tuplas de la tabla FACTURA
	 */
	public List<Factura> darFacturasPorFecha(Timestamp fecha)
	{
		return sqlFactura.darFacturasPorFecha(pmf.getPersistenceManager(), fecha);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Factura que tienen una sucursal dada y un cliente dado
	 * @param idSucursal - El identificador de la sucursal de la factura
	 * @param idCliente - El cliente de la factura
	 * @return La lista de objetos Factura, construidos con base en las tuplas de la tabla FACTURA
	 */
	public List<Factura> darFacturasPorClienteYSucursal(long idCliente, long idSucursal)
	{
		return sqlFactura.darFacturasPorClienteYSucursal(pmf.getPersistenceManager(), idCliente, idSucursal);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Factura
	 * @return La lista de objetos Factura, construidos con base en las tuplas de la tabla FACTURA
	 */
	public List<Factura> darFacturas()
	{
		return sqlFactura.darFacturas(pmf.getPersistenceManager());
	}

	/* ****************************************************************
	 * 			Métodos para manejar las PROMOCIONES
	 *****************************************************************/

	/**
	 * MÃ©todo que inserta, de manera transaccional, una tupla en la tabla PROMOCION. Primero se añade un producto especial y luego es asociado con la promociï¿½n
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param nombre - El nombre del producto
	 * @param marca - Marca del producto
	 * @param presentacion - Presentacion del producto
	 * @param codigobarras - CÃ³digo de barras del producto
	 * @param unidadmedida - Las unidades de medida del producto
	 * @param categoria - La categoria del producto (perecederos, no perecederos, aseo, abarrotes, etc)
	 * @param tipo - El tipo de producto por categoria
	 * @param descripcion de la promocion
	 * @param fechainicio de la promocion
	 * @param fechafin de la promocion
	 * @param unidadesdisponibles para la promocion
	 * @return El objeto Proveedor adicionado. null si ocurre alguna ExcepciÃ³n
	 */
	public Promocion adicionarPromocion(String nombre, String marca, String presentacion, String codigobarras, String unidadmedida, String categoria, String tipo, double precio, String descripcion, Timestamp fechaInicio, Timestamp fechaFin, int unidadesdisponibles)
	{

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idProducto = nextval ();
			long tuplasInsertadas = sqlProducto.adicionarProducto(pm, idProducto, nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo);
			tx.commit();

			log.trace ("Insercion de producto: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			tx.begin();
			long idPromocion = nextval ();
			long tuplasInsertadas2 = sqlPromocion.adicionarPromocion(pm, idPromocion, precio, descripcion, fechaInicio, fechaFin, unidadesdisponibles, idProducto);
			tx.commit();

			log.trace ("InserciÃ³n de la promocion: " + descripcion + ": " + tuplasInsertadas2 + " tuplas insertadas");

			return new Promocion(idPromocion, precio, descripcion, fechaInicio, fechaFin, unidadesdisponibles, idProducto);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * MÃ©todo que inserta, de manera transaccional, una tupla en la tabla FACTURA. Primero se genera una factura y luego se asocian transacciones a la factura, cada transacción es de un producto.
	 * Adiciona entradas al log de la aplicaciÃ³n	 
	 * @param fecha de la factura
	 * @param cliente que realiza la compra
	 * @param sucursal donde se realiza la compra
	 * @param producto que se compra
	 * @param promocion si existe, si no se pone cero
	 * @param cantidad de producto que se compran
	 * @return El objeto factura adicionado. null si ocurre alguna excepción
	 */
	public Factura adicionarVenta(Timestamp fecha, String cliente, long sucursal, long producto, long promocion, int cantidad)
	{

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idFactura = nextval ();
			long tuplasInsertadas = sqlFactura.adicionarFactura(pm, idFactura, fecha, cliente, sucursal);
			tx.commit();

			log.trace ("Insercion de factura numero: " + idFactura + ": " + tuplasInsertadas + " tuplas insertadas");

			tx.begin();
			Vende vende = sqlVende.darVendePorProductoSucursal(pm, producto, sucursal);
			double costo = vende.getPrecioUnitario() * cantidad;
			tx.commit();

			if(promocion == 0)
			{
				tx.begin();
//				sqlEstante.disminuirExistenciasEstantes(pm, cantidad, sucursal, producto);
				tx.commit();
			}
			else
			{
				tx.begin();
				sqlPromocion.disminuirExistenciasPromocion(pm, promocion, cantidad);
			}
			tx.begin();
			long tuplasInsertadas2 = sqlTransaccion.adicionarTransaccion(pm, producto, cantidad, idFactura, costo, promocion);
			tx.commit();

			log.trace ("Inserción de la transacción: " + tuplasInsertadas2 + " tuplas insertadas");

			return new Factura(idFactura, sucursal, fecha, cliente);
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	public Pedido recibirPedido(long idPedido, int calificacion)
	{

		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			Subpedido sub = sqlSubPedido.darSubPedidoPorId(pm, idPedido);
			int cantidad = sub.getCantidad();
			long producto = sub.getIdProducto();
			tx.commit();

			log.trace ("Recepcion de pedido: " + idPedido);

			tx.begin();
			long tuplas = sqlPedido.cambiarEstadoOrdenPedido(pm, idPedido);
			tx.commit();

			tx.begin();
			Pedido pedido = sqlPedido.darPedidoPorId(pm, idPedido);
			long sucursal = pedido.getIdSucursal();
			tx.commit();

			tx.begin();
			long tupla2 = sqlPedido.cambiarCalificacionPedido(pm, idPedido, calificacion);
			tx.commit();

			tx.begin();
			long tuplasInsertadas2 = sqlAlmacenamiento.aumentarExistenciasAlmacenamientos(pm, cantidad, sucursal, producto, "Bodega");
			tx.commit();

			log.trace ("Se recibió el pedido. " + tuplasInsertadas2 + " tuplas insertadas");
			Pedido pedido2 = sqlPedido.darPedidoPorId(pm, idPedido);

			return pedido2;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que elimina, de manera transaccional, una tupla en la tabla Promocion, dado el identificador de la promocion
	 * Adiciona entradas al log de la aplicación
	 * @param idProm - El identificador de la promoción
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarPromocionPorId (long idProm) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			Promocion prom = sqlPromocion.darPromocionPorId(pm, idProm);
			long prod = prom.getIdProducto();
			tx.commit();

			tx.begin();
			long resp = sqlPromocion.eliminarPromocionPorId(pm, idProm);
			tx.commit();

			tx.begin();
			long resp2 = sqlProducto.eliminarProductoPorId(pm, prod);
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return -1;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

	/**
	 * Método que consulta las sucursales y las ventas de esa sucursal
	 * @return La lista de parejas de objetos, construidos con base en las tuplas de la tabla SUCURSAL y FACTURA. 
	 * El primer elemento de la pareja es una sucursal; 
	 * el segundo elemento es el numero total de dinero que se ha adquirido por ventas (0 en el caso que no haya realizado ventas)
	 */
	public List<long []> darSucursalesYVentasRealizadas (Timestamp fechainicio, Timestamp fechafin)
	{
		List<long []> resp = new LinkedList<long []> ();
		List<Object []> tuplas = sqlSucursal.darSucursalesYVentas(pmf.getPersistenceManager(), fechainicio, fechafin);
		for ( Object [] tupla : tuplas)
		{
			long [] datosResp = new long [2];

			datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
			datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
			resp.add (datosResp);
		}
		return resp;
	}
	
//	/**
//	 * Método que consulta los indices de ocupacion de bodegas y estantes por una sucursal
//	 * @return La lista de parejas de objetos, construidos con base en los valores de la tabla SUCURSAL.
//	 * El primer elemento del arreglo es el id de la bodega.
//	 * El segundo elemento del arreglo es el indice de ocupación del estante.
//	 * El tercer elemento del arreglo es el id del estante
//	 * El cuarto elemento del arreglo es el indice de ocupación del estante.
//	 */
//	public List<long[]> darIndiceOcupacionBodegasEstantes (long idSucursal)
//	{
//		List<long []> resp = new LinkedList<long []>();
//		List<Object []> tuplas = sqlSucursal.darIndiceOcupacionBodegasEstantes(pmf.getPersistenceManager(), idSucursal);
//		for(Object [] tupla : tuplas)
//		{
//			long [] datos = new long [4];
//			
//			datos [0] = ((BigDecimal) tupla [0]).longValue();
//			datos [1] = ((BigDecimal) tupla [1]).longValue();
//			datos [2] = ((BigDecimal) tupla [2]).longValue();
//			datos [3] = ((BigDecimal) tupla [3]).longValue();
//			resp.add(datos);
//		}
//		return resp;
//	}

	/**
	 * Método que encuentra el identificador de las promociones y sus ventas
	 * @return Una lista de arreglos de 2 números. El primero corresponde al identificador de la promocion, el segundo corresponde al ratio ventas/dias
	 */
	public List<long []> darPromocionVentas()
	{
		List<long []> resp = new LinkedList<long []> ();
		List<Object []> tuplas =  sqlPromocion.darPromocionesMasVendidas(pmf.getPersistenceManager());
		for ( Object [] tupla : tuplas)
		{
			long [] datosResp = new long [2];

			datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
			datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
			resp.add (datosResp);
		}
		return resp;
	}
		
	/**
	 * Método que consulta todas las tuplas en la tabla Productos y Sucursal que tienen una ciudad dada
	 * @param ciudad - La ciudad de donde proviene el producto
	 * @return La lista de objetos Producto, construidos con base en las tuplas de las tablas PRODUCTO y SUCURSAL
	 */
	public List<Producto> darProductosPorCiudad(String ciudad)
	{
		return sqlProducto.darProductosPorCiudad(pmf.getPersistenceManager(), ciudad);
	}
	
	/**
	 * Método que consulta las sucursales y las ventas de esa sucursal
	 * @return La lista de parejas de objetos, construidos con base en las tuplas de la tabla SUCURSAL y FACTURA. 
	 * El primer elemento de la pareja es una sucursal; 
	 * @throws Exception 
	 */
	public List<List<Pedido>> darPedidosProveedor() throws Exception
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		List<List<Pedido>> resp = new LinkedList ();
		List<Long> ids = new LinkedList<>();
		try
		{
			tx.begin();
			List<Pedido> ped= sqlPedido.darPedidos(pm);
			for(Pedido pedido:ped)
			{
				long proveedor = pedido.getIdProveedor();
				boolean esta = false;
				if(ids.size() == 0)
				{
					System.out.println("veces");
					ids.add(proveedor);
				}
				else {
					for(Long numero:ids)
					{
						if(numero == proveedor)
						{
							System.out.println("entro");
							esta = true;
						}
					}
					if(!esta) {
						System.out.println("Esta es:" + esta);
						System.out.println("añade este: " + proveedor);
					ids.add(proveedor);
					};
				}
				
				
			}
			tx.commit();
			
			
			tx.begin();
			for(Long id:ids)
			{
				resp.add(sqlPedido.darPedidosPorProveedor(pm, id));
			}
			tx.commit();
			
			
			log.trace ("Creación de la lista con listas de pedidos por proveedor");
			
			for (Long numero:ids)
			{
				System.out.println(numero);
			}

			return resp;
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//			return null;
			throw new Exception (e.getMessage());
			
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	
	/**
	 * Método que consulta todas las tuplas en la tabla Carrito con un identificador dado
	 * @param idCarrito- El identificador del carrito
	 * @return El objeto Carrito, construido con base en las tuplas de la tabla CARRITO con el identificador dado
	 */
	public Carrito darCarritoPorId(long idCarrito)
	{
		return sqlCarrito.darCarritoPorId(pmf.getPersistenceManager(), idCarrito);
	}
	
	
	
	public Carrito asignarCarrito(long id, long clave) throws Exception
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			Carrito car= sqlCarrito.darCarritoPorId(pm, id);
			tx.commit();
			
			if(car.getEstado().equalsIgnoreCase("en uso"))
			{
				throw new Exception ("El carrito está en uso");
			}

			log.trace ("Asignación de carrito: " + id);

			tx.begin();
			long tuplas = sqlCarrito.cambiarEstadoCarrito(pm, id, "en uso");
			long tuplas2 = sqlCarrito.cambiarClaveCarrito(pm, id, clave);
			tx.commit();

			tx.begin();
			Carrito car2= sqlCarrito.darCarritoPorId(pm, id);
			tx.commit();
			
			log.trace ("Se asignó el carrito. " + tuplas + " tuplas insertadas");
			log.trace ("Se cambió la clave del carrito. " + tuplas2 + " tuplas insertadas");

			return car2;
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
//			return null;
			throw new Exception (e.getMessage());
			
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	/**
	 * Busca el carrito dado, y elimina la tupla de la tabla contiene que se asocia al carrito y al producto
	 * @param idCarrito
	 * @param clave
	 * @param idProducto
	 * @return
	 */
	public Contiene adicionarProducto (long idCarrito, long clave, long idProducto, long sucursal, int cantidad)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			Carrito car= sqlCarrito.darCarritoPorIdClave(pm, idCarrito, clave);
			tx.commit();
			
			if(car == null)
			{
				throw new Exception ("La contraseña del carrito es incorrecta");
			}
			
			log.trace ("Buscando carrito " + idCarrito+ " con contraseña " + clave );

			tx.begin();
			long disminuir = sqlAlmacenamiento.disminuirExistenciasAlmacenamientos(pm, cantidad, sucursal, idProducto, "Estante");
			log.trace ("Disminuyendo " + cantidad + " productos " + idProducto + "  del estante de la sucursal " + sucursal );
			
			long add = sqlContiene.adicionarContiene(pm, idProducto, cantidad, idCarrito);
			log.trace ("Agregando " + add + "a la tabla contiene"  );
			
			tx.commit();

			return new Contiene(idCarrito, cantidad, idProducto);
		}
		catch (Exception e)
		{
			        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	/**
	 * Busca el carrito dado, y elimina la tupla de la tabla contiene que se asocia al carrito y al producto
	 * @param idCarrito
	 * @param clave
	 * @param idProducto
	 * @return
	 */
	public Contiene devolverProducto (long idCarrito, long clave, long idProducto, long sucursal)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			Carrito car= sqlCarrito.darCarritoPorIdClave(pm, idCarrito, clave);
			tx.commit();
			
			if(car == null)
			{
				throw new Exception ("La contraseña del carrito es incorrecta");
			}

			log.trace ("Buscando carrito " + idCarrito+ " con contraseña " + clave );

			tx.begin();
			Contiene con1 = sqlContiene.darContienePorCarritoProducto(pm, idCarrito, idProducto);
			int cantidad = con1.getCantidad();
			long can = sqlAlmacenamiento.aumentarExistenciasAlmacenamientos(pm, cantidad, sucursal, idProducto, "Estante");
			log.trace ("Devolviendo " + cantidad + " productos " + idProducto + "  al estante de la sucursal " + sucursal );
			
			long dev = sqlContiene.eliminarContienePorCarritoProducto(pm, idCarrito, idProducto);
			log.trace ("Eliminando " + dev + "de la tabla contiene"  );

			
			tx.commit();

			return con1;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	/**
	 * Busca el carrito dado, lo abandona, cambiando su estado a abandonado.
	 * @param idCarrito
	 * @param clave
	 * @return
	 */
	public Carrito abandonarCarrito (long idCarrito, long clave)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			Carrito car= sqlCarrito.darCarritoPorIdClave(pm, idCarrito, clave);
			tx.commit();
			
			if(car == null)
			{
				throw new Exception ("La contraseña del carrito es incorrecta");
			}

			log.trace ("Buscando carrito " + idCarrito+ " con contraseña " + clave );

			tx.begin();
			long tupla = sqlCarrito.cambiarEstadoCarrito(pm, idCarrito, "abandonado");
			log.trace ("Cambiando el estado de: " + tupla + " tuplas en la tabla Carrito" );
			long tupla2 = sqlCarrito.cambiarClaveCarrito(pm, idCarrito, 0);
			log.trace ("Cambiando la clave a cero de: " + tupla + " tuplas en la tabla Carrito" );
			tx.commit();

			return car;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return null;
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}
	
	public void recolectarProductosAbandonados()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();

		try
		{
			tx.begin();
			List<Object[]> abandonados = sqlContiene.darContieneCarritosAbandonados(pmf.getPersistenceManager());
			for ( Object[] abandonado: abandonados)
			{
				long [] datosResp = new long [4];

				datosResp [0] = ((BigDecimal) abandonado [0]).longValue (); // id producto
				datosResp [1] = ((BigDecimal) abandonado [1]).longValue (); // cantidad unidades
				datosResp [2] = ((BigDecimal) abandonado [2]).longValue (); // id del carrito
				datosResp [3] = ((BigDecimal) abandonado [3]).longValue (); // estado del carrito
				
				sqlContiene.eliminarContienePorCarritoProducto(pmf.getPersistenceManager(), datosResp[2], datosResp[0]);	
			}
			
			List<Object[]> existencias = sqlAlmacenamiento.darExistenciasProductosAbandonados(pmf.getPersistenceManager());
			for ( Object[] abandonado: abandonados)
			{
				long [] datosResp = new long [4];
				System.out.println(datosResp[1]);

				datosResp [0] = ((BigDecimal) abandonado [0]).longValue (); // existencias
				datosResp [1] = ((BigDecimal) abandonado [1]).longValue (); // id de la sucursal
				datosResp [2] = ((BigDecimal) abandonado [2]).longValue (); // id del producto
				datosResp [3] = ((BigDecimal) abandonado [3]).longValue (); // cantidad unidades
				
				sqlAlmacenamiento.aumentarExistenciasAlmacenamientos(pmf.getPersistenceManager(), (int)datosResp[3], datosResp[1], datosResp[2], "Estante");
			}
			
			tx.commit();
			
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			
		}
		finally
		{
			if (tx.isActive())
			{
				tx.rollback();
			}
			pm.close();
		}
	}

}
