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
import uniandes.isis2304.parranderos.negocio.Bar;
import uniandes.isis2304.parranderos.negocio.Bebedor;
import uniandes.isis2304.parranderos.negocio.Bebida;
import uniandes.isis2304.parranderos.negocio.Bodega;
import uniandes.isis2304.parranderos.negocio.Estante;
import uniandes.isis2304.parranderos.negocio.Factura;
import uniandes.isis2304.parranderos.negocio.Gustan;
import uniandes.isis2304.parranderos.negocio.Pedido;
import uniandes.isis2304.parranderos.negocio.Producto;
import uniandes.isis2304.parranderos.negocio.Promocion;
import uniandes.isis2304.parranderos.negocio.Proveedor;
import uniandes.isis2304.parranderos.negocio.Sirven;
import uniandes.isis2304.parranderos.negocio.Sucursal;
import uniandes.isis2304.parranderos.negocio.Supermercado;
import uniandes.isis2304.parranderos.negocio.TipoBebida;
import uniandes.isis2304.parranderos.negocio.Vende;
import uniandes.isis2304.parranderos.negocio.Visitan;
import uniandes.isis2304.parranderos.negocio.Cliente;
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

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Atributo privado que es el único objeto de la clase - Patrón SINGLETON
	 */
	private static PersistenciaParranderos instance;

	/**
	 * Fábrica de Manejadores de persistencia, para el manejo correcto de las transacciones
	 */
	private PersistenceManagerFactory pmf;

	/**
	 * Arreglo de cadenas con los nombres de las tablas de la base de datos, en su orden:
	 * Secuenciador, tipoBebida, bebida, bar, bebedor, gustan, sirven y visitan
	 */
	private List <String> tablas;

	/**
	 * Atributo para el acceso a las sentencias SQL propias a PersistenciaParranderos
	 */
	private SQLUtil sqlUtil;

	/**
	 * Atributo para el acceso a la tabla TIPOBEBIDA de la base de datos
	 */
	private SQLTipoBebida sqlTipoBebida;

	/**
	 * Atributo para el acceso a la tabla BEBIDA de la base de datos
	 */
	private SQLBebida sqlBebida;

	/**
	 * Atributo para el acceso a la tabla BAR de la base de datos
	 */
	private SQLBar sqlBar;

	/**
	 * Atributo para el acceso a la tabla BEBIDA de la base de datos
	 */
	private SQLBebedor sqlBebedor;

	/**
	 * Atributo para el acceso a la tabla GUSTAN de la base de datos
	 */
	private SQLGustan sqlGustan;

	/**
	 * Atributo para el acceso a la tabla SIRVEN de la base de datos
	 */
	private SQLSirven sqlSirven;

	/**
	 * Atributo para el acceso a la tabla VISITAN de la base de datos
	 */
	private SQLVisitan sqlVisitan;

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
	 * Atributo para el acceso a la tabla BODEGA de la base de datos
	 */
	private SQLBodega sqlBodega;

	/**
	 * Atributo para el acceso a la tabla ESTANTE de la base de datos
	 */
	private SQLEstante sqlEstante;

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
		tablas.add ("BODEGA");
		tablas.add ("ESTANTE");
		tablas.add ("VENDE");
		tablas.add ("PROVEEDOR");
		tablas.add ("PEDIDO");
		tablas.add ("SUBPEDIDO");
		tablas.add ("OFRECEN");
		tablas.add ("CLIENTE");
		tablas.add ("FACTURA");
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
		sqlTipoBebida = new SQLTipoBebida(this);
		sqlBebida = new SQLBebida(this);
		sqlBar = new SQLBar(this);
		sqlBebedor = new SQLBebedor(this);
		sqlGustan = new SQLGustan(this);
		sqlSirven = new SQLSirven (this);
		sqlVisitan = new SQLVisitan(this);		
		sqlUtil = new SQLUtil(this);
		sqlSupermercado = new SQLSupermercado(this);
		sqlSucursal = new SQLSucursal(this);
		sqlProducto = new SQLProducto(this);
		sqlBodega = new SQLBodega(this);
		sqlEstante = new SQLEstante(this);
		sqlVende = new SQLVende(this);
		sqlProveedor = new SQLProveedor(this);
		sqlPedido = new SQLPedido(this);
		sqlSubPedido = new SQLSubpedido(this);
		sqlOfrecen = new SQLOfrecen(this);
		sqlCliente = new SQLCliente(this);
		sqlFactura = new SQLFactura(this);
		sqlPromocion = new SQLPromocion(this);
		sqlTransaccion = new SQLTransaccion(this);
	}

	/**
	 * @return La cadena de caracteres con el nombre del secuenciador de parranderos
	 */
	public String darSeqParranderos ()
	{
		return tablas.get (0);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de TipoBebida de parranderos
	 */
	public String darTablaTipoBebida ()
	{
		return tablas.get (1);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebida de parranderos
	 */
	public String darTablaBebida ()
	{
		return tablas.get (2);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bar de parranderos
	 */
	public String darTablaBar ()
	{
		return tablas.get (3);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Bebedor de parranderos
	 */
	public String darTablaBebedor ()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Gustan de parranderos
	 */
	public String darTablaGustan ()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Sirven de parranderos
	 */
	public String darTablaSirven ()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Visitan de parranderos
	 */
	public String darTablaVisitan ()
	{
		return tablas.get (7);
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

	/* ****************************************************************
	 * 			Métodos para manejar los TIPOS DE BEBIDA
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla TipoBebida
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
	 */
	public TipoBebida adicionarTipoBebida(String nombre)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idTipoBebida = nextval ();
			long tuplasInsertadas = sqlTipoBebida.adicionarTipoBebida(pm, idTipoBebida, nombre);
			tx.commit();

			log.trace ("Inserción de tipo de bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new TipoBebida (idTipoBebida, nombre);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla TipoBebida, dado el nombre del tipo de bebida
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarTipoBebidaPorNombre (String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlTipoBebida.eliminarTipoBebidaPorNombre(pm, nombre);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla TipoBebida, dado el identificador del tipo de bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarTipoBebidaPorId (long idTipoBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlTipoBebida.eliminarTipoBebidaPorId(pm, idTipoBebida);
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
	 * Método que consulta todas las tuplas en la tabla TipoBebida
	 * @return La lista de objetos TipoBebida, construidos con base en las tuplas de la tabla TIPOBEBIDA
	 */
	public List<TipoBebida> darTiposBebida ()
	{
		return sqlTipoBebida.darTiposBebida (pmf.getPersistenceManager());
	}

	/**
	 * Método que consulta todas las tuplas en la tabla TipoBebida que tienen el nombre dado
	 * @param nombre - El nombre del tipo de bebida
	 * @return La lista de objetos TipoBebida, construidos con base en las tuplas de la tabla TIPOBEBIDA
	 */
	public List<TipoBebida> darTipoBebidaPorNombre (String nombre)
	{
		return sqlTipoBebida.darTiposBebidaPorNombre (pmf.getPersistenceManager(), nombre);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla TipoBebida con un identificador dado
	 * @param idTipoBebida - El identificador del tipo de bebida
	 * @return El objeto TipoBebida, construido con base en las tuplas de la tabla TIPOBEBIDA con el identificador dado
	 */
	public TipoBebida darTipoBebidaPorId (long idTipoBebida)
	{
		return sqlTipoBebida.darTipoBebidaPorId (pmf.getPersistenceManager(), idTipoBebida);
	}

	/* ****************************************************************
	 * 			Métodos para manejar las BEBIDAS
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla Bebida
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida (Debe existir en la tabla TipoBebida)
	 * @param gradoAlcohol - El grado de alcohol de la bebida (mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Bebida adicionarBebida(String nombre, long idTipoBebida, int gradoAlcohol) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();            
			long idBebida = nextval ();
			long tuplasInsertadas = sqlBebida.adicionarBebida(pm, idBebida, nombre, idTipoBebida, gradoAlcohol);
			tx.commit();

			log.trace ("Inserción bebida: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");
			return new Bebida (idBebida,nombre, idTipoBebida, gradoAlcohol);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla Bebida, dado el nombre de la bebida
	 * Adiciona entradas al log de la aplicación
	 * @param nombreBebida - El nombre de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBebidaPorNombre (String nombreBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlBebida.eliminarBebidaPorNombre(pm, nombreBebida);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla Bebida, dado el identificador de la bebida
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBebidaPorId (long idBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlBebida.eliminarBebidaPorId (pm, idBebida);
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
	 * Método que consulta todas las tuplas en la tabla Bebida que tienen el nombre dado
	 * @param nombreBebida - El nombre de la bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<Bebida> darBebidasPorNombre (String nombreBebida)
	{
		return sqlBebida.darBebidasPorNombre (pmf.getPersistenceManager(), nombreBebida);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla Bebida
	 * @return La lista de objetos Bebida, construidos con base en las tuplas de la tabla BEBIDA
	 */
	public List<Bebida> darBebidas ()
	{
		return sqlBebida.darBebidas (pmf.getPersistenceManager());
	}

	/**
	 * Método que elimina, de manera transaccional, las bebidas que no son referenciadas en la tabla SIRVEN de Parranderos
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBebidasNoServidas ()
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlBebida.eliminarBebidasNoServidas(pm);
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

	/* ****************************************************************
	 * 			Métodos para manejar los BEBEDORES
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla BEBEDOR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param ciudad - La ciudad del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public Bebedor adicionarBebedor(String nombre, String ciudad, String presupuesto) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idBebedor = nextval ();
			long tuplasInsertadas = sqlBebedor.adicionarBebedor(pmf.getPersistenceManager(), idBebedor, nombre, ciudad, presupuesto);
			tx.commit();

			log.trace ("Inserción de bebedor: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Bebedor (idBebedor, nombre, ciudad, presupuesto);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el nombre del bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBebedorPorNombre(String nombre) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlBebedor.eliminarBebedorPorNombre (pm, nombre);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla BEBEDOR, dado el identificador del bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBebedorPorId (long idBebedor) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlBebedor.eliminarBebedorPorId (pm, idBebedor);
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
	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el nombre dado
	 * @param nombreBebedor - El nombre del bebedor
	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
	 */
	public List<Bebedor> darBebedoresPorNombre (String nombreBebedor) 
	{
		return sqlBebedor.darBebedoresPorNombre (pmf.getPersistenceManager(), nombreBebedor);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR que tienen el identificador dado
	 * @param idBebedor - El identificador del bebedor
	 * @return El objeto BEBEDOR, construido con base en la tuplas de la tabla BEBEDOR, que tiene el identificador dado
	 */
	public Bebedor darBebedorPorId (long idBebedor) 
	{
		return (Bebedor) sqlBebedor.darBebedorPorId (pmf.getPersistenceManager(), idBebedor);
	}

	/**
	 * Método que consulta TODA LA INFORMACIÓN DE UN BEBEDOR con el identificador dado. Incluye la información básica del bebedor,
	 * las visitas realizadas y las bebidas que le gustan.
	 * @param idBebedor - El identificador del bebedor
	 * @return El objeto BEBEDOR, construido con base en las tuplas de la tablas BEBEDOR, VISITAN, BARES, GUSTAN, BEBIDAS y TIPOBEBIDA,
	 * relacionadas con el identificador de bebedor dado
	 */
	public Bebedor darBebedorCompleto (long idBebedor) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Bebedor bebedor = (Bebedor) sqlBebedor.darBebedorPorId (pm, idBebedor);
		bebedor.setVisitasRealizadas(armarVisitasBebedor (sqlBebedor.darVisitasRealizadas (pm, idBebedor)));
		bebedor.setBebidasQueLeGustan(armarGustanBebedor (sqlBebedor.darBebidasQueLeGustan (pm, idBebedor)));
		return bebedor;
	}

	/**
	 * Método que consulta todas las tuplas en la tabla BEBEDOR
	 * @return La lista de objetos BEBEDOR, construidos con base en las tuplas de la tabla BEBEDOR
	 */
	public List<Bebedor> darBebedores ()
	{
		return sqlBebedor.darBebedores (pmf.getPersistenceManager());
	}

	/**
	 * Método que consulta los bebedores y el número de visitas que ha realizado
	 * @return La lista de parejas de objetos, construidos con base en las tuplas de la tabla BEBEDOR y VISITAN. 
	 * El primer elemento de la pareja es un bebedor; 
	 * el segundo elemento es el número de visitas de ese bebedor (0 en el caso que no haya realizado visitas)
	 */
	public List<Object []> darBebedoresYNumVisitasRealizadas ()
	{
		List<Object []> respuesta = new LinkedList <Object []> ();
		List<Object> tuplas = sqlBebedor.darBebedoresYNumVisitasRealizadas (pmf.getPersistenceManager());
		for ( Object tupla : tuplas)
		{
			Object [] datos = (Object []) tupla;
			long idBebedor = ((BigDecimal) datos [0]).longValue ();
			String nombreBebedor = (String) datos [1];
			String ciudadBebedor = (String) datos [2];
			String presupuesto = (String) datos [3];
			int numBares = ((BigDecimal) datos [4]).intValue ();

			Object [] resp = new Object [2];
			resp [0] = new Bebedor (idBebedor, nombreBebedor, ciudadBebedor, presupuesto);
			resp [1] = numBares;	

			respuesta.add(resp);
		}

		return respuesta;
	}

	/**
	 * Método que consulta CUÁNTOS BEBEDORES DE UNA CIUDAD VISITAN BARES
	 * @param ciudad - La ciudad que se quiere consultar
	 * @return El número de bebedores de la ciudad dada que son referenciados en VISITAN
	 */
	public long darCantidadBebedoresCiudadVisitanBares (String ciudad)
	{
		return sqlBebedor.darCantidadBebedoresCiudadVisitanBares (pmf.getPersistenceManager(), ciudad);
	}

	/**
	 * Método que actualiza, de manera transaccional, la ciudad de un  BEBEDOR
	 * @param idBebedor - El identificador del bebedor
	 * @param ciudad - La nueva ciudad del bebedor
	 * @return El número de tuplas modificadas. -1 si ocurre alguna Excepción
	 */
	public long cambiarCiudadBebedor (long idBebedor, String ciudad)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlBebedor.cambiarCiudadBebedor (pm, idBebedor, ciudad);
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
	 * Método que elimima, de manera transaccional, un BEBEDOR y las VISITAS que ha realizado
	 * Si el bebedor está referenciado en alguna otra relación, no se borra ni el bebedor NI las visitas
	 * @param idBebedor - El identificador del bebedor
	 * @return Un arreglo de dos números que representan el número de bebedores eliminados y 
	 * el número de visitas eliminadas, respectivamente. [-1, -1] si ocurre alguna Excepción
	 */
	public long []  eliminarBebedorYVisitas_v1 (long idBebedor)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long [] resp = sqlBebedor.eliminarBebedorYVisitas_v1 (pm, idBebedor);
			tx.commit();
			return resp;
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return new long[] {-1, -1};
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
	 * Método que elimima, de manera transaccional, un BEBEDOR y las VISITAS que ha realizado
	 * Si el bebedor está referenciado en alguna otra relación, no se puede borrar, SIN EMBARGO SÍ SE BORRAN TODAS SUS VISITAS
	 * @param idBebedor - El identificador del bebedor
	 * @return Un arreglo de dos números que representan el número de bebedores eliminados y 
	 * el número de visitas eliminadas, respectivamente. [-1, -1] si ocurre alguna Excepción
	 */
	public long [] eliminarBebedorYVisitas_v2 (long idBebedor)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long visitasEliminadas = eliminarVisitanPorIdBebedor(idBebedor);
			long bebedorEliminado = eliminarBebedorPorId (idBebedor);
			tx.commit();
			return new long [] {bebedorEliminado, visitasEliminadas};
		}
		catch (Exception e)
		{
			//        	e.printStackTrace();
			log.error ("Exception : " + e.getMessage() + "\n" + darDetalleException(e));
			return new long [] {-1, -1};
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
	 * Método privado para generar las información completa de las visitas realizadas por un bebedor: 
	 * La información básica del bar visitado, la fecha y el horario, en el formato esperado por los objetos BEBEDOR
	 * @param tuplas - Una lista de arreglos de 7 objetos, con la información del bar y de la visita realizada, en el siguiente orden:
	 *   bar.id, bar.nombre, bar.ciudad, bar.presupuesto, bar.cantsedes, vis.fechavisita, vis.horario
	 * @return Una lista de arreglos de 3 objetos. El primero es un objeto BAR, el segundo corresponde a la fecha de la visita y
	 * el tercero corresponde al horaario de la visita
	 */
	private List<Object []> armarVisitasBebedor (List<Object []> tuplas)
	{
		List<Object []> visitas = new LinkedList <Object []> ();
		for (Object [] tupla : tuplas)
		{
			long idBar = ((BigDecimal) tupla [0]).longValue ();
			String nombreBar = (String) tupla [1];
			String ciudadBar = (String) tupla [2];
			String presupuestoBar = (String) tupla [3];
			int sedesBar = ((BigDecimal) tupla [4]).intValue ();
			Timestamp fechaVisita = (Timestamp) tupla [5];
			String horarioVisita = (String) tupla [6];

			Object [] visita = new Object [3];
			visita [0] = new Bar (idBar, nombreBar, ciudadBar, presupuestoBar, sedesBar);
			visita [1] = fechaVisita;
			visita [2] = horarioVisita;

			visitas.add (visita);
		}
		return visitas;
	}

	/**
	 * Método privado para generar las información completa de las bebidas que le gustan a un bebedor: 
	 * La información básica de la bebida, especificando también el nombre de la bebida, en el formato esperado por los objetos BEBEDOR
	 * @param tuplas - Una lista de arreglos de 5 objetos, con la información de la bebida y del tipo de bebida, en el siguiente orden:
	 * 	 beb.id, beb.nombre, beb.idtipobebida, beb.gradoalcohol, tipobebida.nombre
	 * @return Una lista de arreglos de 2 objetos. El primero es un objeto BEBIDA, el segundo corresponde al nombre del tipo de bebida
	 */
	private List<Object []> armarGustanBebedor (List<Object []> tuplas)
	{
		List<Object []> gustan = new LinkedList <Object []> ();
		for (Object [] tupla : tuplas)
		{			
			long idBebida = ((BigDecimal) tupla [0]).longValue ();
			String nombreBebida = (String) tupla [1];
			long idTipoBebida = ((BigDecimal) tupla [2]).longValue ();
			int gradoAlcohol = ((BigDecimal) tupla [3]).intValue ();
			String nombreTipo = (String) tupla [4];

			Object [] gusta = new Object [2];
			gusta [0] = new Bebida (idBebida, nombreBebida, idTipoBebida, gradoAlcohol);
			gusta [1] = nombreTipo;	

			gustan.add(gusta);
		}
		return gustan;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los BARES
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla BAR
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bar
	 * @param ciudad - La ciudad del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param sedes - El número de sedes del bar en la ciudad (Mayor que 0)
	 * @return El objeto Bar adicionado. null si ocurre alguna Excepción
	 */
	public Bar adicionarBar(String nombre, String ciudad, String presupuesto, int sedes) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idBar = nextval ();
			long tuplasInsertadas = sqlBar.adicionarBar(pm, idBar, nombre, ciudad, presupuesto, sedes);
			tx.commit();

			log.trace ("Inserción de Bar: " + nombre + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Bar (idBar, nombre, ciudad, presupuesto, sedes);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla BAR, dado el nombre del bar
	 * Adiciona entradas al log de la aplicación
	 * @param nombreBar - El nombre del bar
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBarPorNombre (String nombreBar) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlBar.eliminarBaresPorNombre(pm, nombreBar);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla BAR, dado el identificador del bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarBarPorId (long idBar) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlBar.eliminarBarPorId (pm, idBar);
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
	 * Método que consulta todas las tuplas en la tabla BAR
	 * @return La lista de objetos BAR, construidos con base en las tuplas de la tabla BAR
	 */
	public List<Bar> darBares ()
	{
		return sqlBar.darBares (pmf.getPersistenceManager());
	}

	/**
	 * Método que consulta todas las tuplas en la tabla BAR que tienen el nombre dado
	 * @param nombreBar - El nombre del bar
	 * @return La lista de objetos BAR, construidos con base en las tuplas de la tabla BAR
	 */
	public List<Bar> darBaresPorNombre (String nombreBar)
	{
		return sqlBar.darBaresPorNombre (pmf.getPersistenceManager(), nombreBar);
	}

	/**
	 * Método que consulta todas las tuplas en la tabla BAR que tienen el identificador dado
	 * @param idBar - El identificador del bar
	 * @return El objeto BAR, construido con base en la tuplas de la tabla BAR, que tiene el identificador dado
	 */
	public Bar darBarPorId (long idBar)
	{
		return sqlBar.darBarPorId (pmf.getPersistenceManager(), idBar);
	}

	/**
	 * Método que actualiza, de manera transaccional, aumentando en 1 el número de sedes de todos los bares de una ciudad
	 * @param ciudad - La ciudad que se quiere modificar
	 * @return El número de tuplas modificadas. -1 si ocurre alguna Excepción
	 */
	public long aumentarSedesBaresCiudad (String ciudad)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlBar.aumentarSedesBaresCiudad(pm, ciudad);
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

	/* ****************************************************************
	 * 			Métodos para manejar la relación GUSTAN
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla GUSTAN
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor - Debe haber un bebedor con ese identificador
	 * @param idBebida - El identificador de la bebida - Debe haber una bebida con ese identificador
	 * @return Un objeto GUSTAN con la información dada. Null si ocurre alguna Excepción
	 */
	public Gustan adicionarGustan(long idBebedor, long idBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlGustan.adicionarGustan (pm, idBebedor, idBebida);
			tx.commit();

			log.trace ("Inserción de gustan: [" + idBebedor + ", " + idBebida + "]. " + tuplasInsertadas + " tuplas insertadas");

			return new Gustan (idBebedor, idBebida);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla GUSTAN, dados los identificadores de bebedor y bebida
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarGustan(long idBebedor, long idBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlGustan.eliminarGustan(pm, idBebedor, idBebida);           
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
	 * Método que consulta todas las tuplas en la tabla GUSTAN
	 * @return La lista de objetos GUSTAN, construidos con base en las tuplas de la tabla GUSTAN
	 */
	public List<Gustan> darGustan ()
	{
		return sqlGustan.darGustan (pmf.getPersistenceManager());
	}


	/* ****************************************************************
	 * 			Métodos para manejar la relación SIRVEN
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla SIRVEN
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar - Debe haber un bar con ese identificador
	 * @param idBebida - El identificador de la bebida - Debe haber una bebida con ese identificador
	 * @param horario - El hororio en que se sirve (DIURNO, NOCTURNO, TODOS)
	 * @return Un objeto SIRVEN con la información dada. Null si ocurre alguna Excepción
	 */
	public Sirven adicionarSirven (long idBar, long idBebida, String horario) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlSirven.adicionarSirven (pmf.getPersistenceManager(), idBar, idBebida, horario);
			tx.commit();

			log.trace ("Inserción de gustan: [" + idBar + ", " + idBebida + "]. " + tuplasInsertadas + " tuplas insertadas");

			return new Sirven (idBar, idBebida, horario);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla SIRVEN, dados los identificadores de bar y bebida
	 * @param idBar - El identificador del bar
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarSirven (long idBar, long idBebida) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlSirven.eliminarSirven (pm, idBar, idBebida);	            
			tx.commit();

			return resp;
		}
		catch (Exception e)
		{
			//	        	e.printStackTrace();
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
	 * Método que consulta todas las tuplas en la tabla SIRVEN
	 * @return La lista de objetos SIRVEN, construidos con base en las tuplas de la tabla SIRVEN
	 */
	public List<Sirven> darSirven ()
	{
		return sqlSirven.darSirven (pmf.getPersistenceManager());
	}

	/**
	 * Método que encuentra el identificador de los bares y cuántas bebidas sirve cada uno de ellos. Si una bebida se sirve en diferentes horarios,
	 * cuenta múltiples veces
	 * @return Una lista de arreglos de 2 números. El primero corresponde al identificador del bar, el segundo corresponde al nombre del tipo de bebida
	 */
	public List<long []> darBaresYCantidadBebidasSirven ()
	{
		List<long []> resp = new LinkedList<long []> ();
		List<Object []> tuplas =  sqlSirven.darBaresYCantidadBebidasSirven (pmf.getPersistenceManager());
		for ( Object [] tupla : tuplas)
		{
			long [] datosResp = new long [2];

			datosResp [0] = ((BigDecimal) tupla [0]).longValue ();
			datosResp [1] = ((BigDecimal) tupla [1]).longValue ();
			resp.add (datosResp);
		}
		return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar la relación VISITAN
	 *****************************************************************/

	/**
	 * Método que inserta, de manera transaccional, una tupla en la tabla VISITAN
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor - Debe haber un bebedor con ese identificador
	 * @param idBar - El identificador del bar - Debe haber un bar con ese identificador
	 * @param fecha - La fecha en que se realizó la visita
	 * @param horario - El hororio en que se sirve (DIURNO, NOCTURNO, TODOS)
	 * @return Un objeto VISITAN con la información dada. Null si ocurre alguna Excepción
	 */	
	public Visitan adicionarVisitan (long idBebedor, long idBar, Timestamp fecha, String horario) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long tuplasInsertadas = sqlVisitan.adicionarVisitan(pm, idBebedor, idBar, fecha, horario);
			tx.commit();

			log.trace ("Inserción de gustan: [" + idBebedor + ", " + idBar + "]. " + tuplasInsertadas + " tuplas insertadas");

			return new Visitan (idBebedor, idBar, fecha, horario);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla VISITAN, dados los identificadores de bebedor y bar
	 * @param idBebedor - El identificador del bebedor
	 * @param idBar - El identificador del bar
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarVisitan (long idBebedor, long idBar) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlVisitan.eliminarVisitan(pm, idBebedor, idBar);
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla VISITAN, dados el identificador del bebedor
	 * @param idBebedor - El identificador del bebedor
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarVisitanPorIdBebedor (long idBebedor) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long visitasEliminadas = sqlVisitan.eliminarVisitanPorIdBebedor (pm, idBebedor);
			tx.commit();

			return visitasEliminadas;
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
	 * Método que elimina, de manera transaccional, una tupla en la tabla VISITAN, dados el identificador del bar
	 * @param idBar - El identificador del bar
	 * @return El número de tuplas eliminadas. -1 si ocurre alguna Excepción
	 */
	public long eliminarVisitanPorIdBar (long idBar) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long visitasEliminadas = sqlVisitan.eliminarVisitanPorIdBar (pm, idBar);
			tx.commit();

			return visitasEliminadas;
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
	 * Método que consulta todas las tuplas en la tabla VISITAN
	 * @return La lista de objetos VISITAN, construidos con base en las tuplas de la tabla VISITAN
	 */
	public List<Visitan> darVisitan ()
	{
		return sqlVisitan.darVisitan (pmf.getPersistenceManager());
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
	 * @return La cadena de caracteres con el nombre de la tabla de Bodega de superandes
	 */
	public String darTablaBodega()
	{
		return tablas.get (4);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Estante de superandes
	 */
	public String darTablaEstante()
	{
		return tablas.get (5);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Vende de superandes
	 */
	public String darTablaVende()
	{
		return tablas.get (6);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Proveedor de superandes
	 */
	public String darTablaProveedor()
	{
		return tablas.get (7);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Pedido de superandes
	 */
	public String darTablaPedido()
	{
		return tablas.get (8);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Subpedido de superandes
	 */
	public String darTablaSubpedido()
	{
		return tablas.get (9);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Ofrecen de superandes
	 */
	public String darTablaOfrecen()
	{
		return tablas.get (10);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Cliente de superandes
	 */
	public String darTablaCliente()
	{
		return tablas.get (11);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Factura de superandes
	 */
	public String darTablaFactura()
	{
		return tablas.get (12);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Promocion de superandes
	 */
	public String darTablaPromocion()
	{
		return tablas.get (13);
	}

	/**
	 * @return La cadena de caracteres con el nombre de la tabla de Transaccion de superandes
	 */
	public String darTablaTransaccion()
	{
		return tablas.get (14);
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

			return new Producto(idProducto, nombre, marca, presentacion, unidadmedida, categoria, tipo);
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

	/**
	 * MÃ©todo que inserta, de manera transaccional, una tupla en la tabla BODEGAs
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param capacidadVolumen - La capacidad en volumen de la bodega (metros cÃºbicos)
	 * @param capacidadPeso - La capacidad en peso de la bodega (en kg)
	 * @param producto - Identificador del producto que almacena la bodega
	 * @param sucursal - La sucursal a la que pertenece la bodega
	 * @param existencias - Las existencias disponibles en la bodega
	 * @return El objeto Bodega adicionado. null si ocurre alguna ExcepciÃ³n
	 */
	public Bodega adicionarBodega(double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int existencias)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idBodega = nextval ();
			long tuplasInsertadas = sqlBodega.adicionarBodega(pm, idBodega, capacidadVolumen, capacidadPeso, producto, sucursal, existencias);
			tx.commit();

			log.trace ("InserciÃ³n de la bodega: " + idBodega + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Bodega(idBodega, capacidadVolumen, capacidadPeso, existencias, producto, sucursal);
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
	 * MÃ©todo que elimina, de manera transaccional, una tupla en la tabla Bodega, dado el identificador de la bodega
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param idBodega - El identificador de la bodega
	 * @return El nÃºmero de tuplas eliminadas. -1 si ocurre alguna ExcepciÃ³n
	 */
	public long eliminarBodegaPorId (long idBodega) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlBodega.eliminarBodegaPorId(pm, idBodega);
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
	 * MÃ©todo que consulta todas las tuplas en la tabla Bodega con un identificador dado
	 * @param idBodega - El identificador de la bodega
	 * @return El objeto Bodega, construido con base en las tuplas de la tabla BODEGA con el identificador dado
	 */
	public Bodega darBodegaPorId(long idBodega)
	{
		return sqlBodega.darBodegaPorId(pmf.getPersistenceManager(), idBodega);
	}

	/**
	 * MÃ©todo que consulta todas las tuplas en la tabla Bodega que tienen una sucursal dada
	 * @param sucursal - La sucursal a la que pertenece la bodega
	 * @return La lista de objetos Bodega, construidos con base en las tuplas de la tabla BODEGA
	 */
	public List<Bodega> darBodegasPorSucursal(long sucursal)
	{
		return sqlBodega.darBodegasPorSucursal(pmf.getPersistenceManager(), sucursal);
	}

	/**
	 * MÃ©todo que consulta todas las tuplas en la tabla Bodega
	 * @return La lista de objetos Bodega, construidos con base en las tuplas de la tabla BODEGA
	 */
	public List<Bodega> darBodegas()
	{
		return sqlBodega.darBodegas(pmf.getPersistenceManager());
	}

	/**
	 * MÃ©todo que aumenta las existencias en 10 unidades de una bodega con id dado
	 * @return Las tuplas modificadas con el aumento de existencias
	 */
	public long aumentarExistenciasBodegaEnDiez(long idBodega)
	{
		return sqlBodega.aumentarExistenciasBodegasEnDiez(pmf.getPersistenceManager(), idBodega);
	}

	/* ****************************************************************
	 * 			MÃ©todos para manejar los ESTANTES
	 *****************************************************************/

	/**
	 * MÃ©todo que inserta, de manera transaccional, una tupla en la tabla ESTANTE
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param capacidadVolumen - La capacidad en volumen del estante(metros cÃºbicos)
	 * @param capacidadPeso - La capacidad en peso del estante (en kg)
	 * @param producto - Identificador del producto que almacena el estante
	 * @param sucursal - La sucursal a la que pertenece el estante
	 * @param nivelabastecimientobodega - Cantidad de unidades mÃ­nimas que debe tener en la bodega por producto
	 * @param existencias - Las existencias disponibles en la bodega
	 * @return El objeto Estante adicionado. null si ocurre alguna ExcepciÃ³n
	 */
	public Estante adicionarEstante(double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int nivelabastecimientobodega, int existencias)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idEstante = nextval ();
			long tuplasInsertadas = sqlEstante.adicionarEstante(pm, idEstante, capacidadVolumen, capacidadPeso, producto, sucursal, nivelabastecimientobodega, existencias);
			tx.commit();

			log.trace ("InserciÃ³n del estante: " + idEstante + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Estante(idEstante, capacidadVolumen, capacidadPeso, existencias, producto, sucursal, nivelabastecimientobodega);
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
	 * MÃ©todo que elimina, de manera transaccional, una tupla en la tabla Estante, dado el identificador del estante
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param idBodega - El identificador del estante
	 * @return El nÃºmero de tuplas eliminadas. -1 si ocurre alguna ExcepciÃ³n
	 */
	public long eliminarEstantePorId (long idEstante) 
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long resp = sqlEstante.eliminarEstantePorId(pm, idEstante);
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
	 * MÃ©todo que consulta todas las tuplas en la tabla Estante con un identificador dado
	 * @param idEstante - El identificador del estante
	 * @return El objeto Estante, construido con base en las tuplas de la tabla ESTANTE con el identificador dado
	 */
	public Estante darEstantePorId(long idEstante)
	{
		return sqlEstante.darEstantePorId(pmf.getPersistenceManager(), idEstante);
	}

	/**
	 * MÃ©todo que consulta todas las tuplas en la tabla Estante que tienen una sucursal dada
	 * @param sucursal - La sucursal a la que pertenece al estante
	 * @return La lista de objetos Estante, construidos con base en las tuplas de la tabla ESTANTE
	 */
	public List<Estante> darEstantesPorSucursal(long sucursal)
	{
		return sqlEstante.darEstantesPorSucursal(pmf.getPersistenceManager(), sucursal);
	}

	/**
	 * MÃ©todo que consulta todas las tuplas en la tabla Estante
	 * @return La lista de objetos Estante, construidos con base en las tuplas de la tabla ESTANTEs
	 */
	public List<Estante> darEstantes()
	{
		return sqlEstante.darEstantes(pmf.getPersistenceManager());
	}

	/**
	 * MÃ©todo que aumenta las existencias en 10 unidades de un estante con id dado
	 * @return Las tuplas modificadas con el aumento de existencias
	 */
	public long aumentarExistenciasEstanteEnDiez(long idEstante)
	{
		return sqlEstante.aumentarExistenciasEstantesEnDiez(pmf.getPersistenceManager(), idEstante);
	}

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
	 * Adiciona entradas al log de la aplicaciÃ³n
	 * @param proveedor - El proveedor del pedido
	 * @param sucursal - La sucursal que hace el pedido
	 * @param fechaEntrega - La fecha de entrega del pedido
	 * @param estadoOrden - El estado de orden del pedido
	 * @param cantidad - El numero de unidades solicitadas
	 * @param calificacion - La calificacion del pedido
	 * @param costoTotal - El costo total del pedido
	 * @return El objeto Proveedor adicionado. null si ocurre alguna ExcepciÃ³n
	 */
	public Pedido adicionarPedido(long proveedor, long sucursal, Timestamp fechaEntrega, String estadoOrden, int cantidad, int calificacion, double costoTotal, long producto, int cantidadSub, double costo)
	{
		PersistenceManager pm = pmf.getPersistenceManager();
		Transaction tx=pm.currentTransaction();
		try
		{
			tx.begin();
			long idPedido = nextval ();
			long tuplasInsertadas = sqlPedido.adicionarPedido(pm, idPedido, proveedor, sucursal, fechaEntrega, estadoOrden, cantidad, calificacion, costoTotal);
			tx.commit();

			log.trace ("InserciÃ³n del proveedor: " + idPedido + ": " + tuplasInsertadas + " tuplas insertadas");

			return new Pedido(idPedido, proveedor, sucursal, fechaEntrega, estadoOrden, cantidad, calificacion, costoTotal);
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
	public long cambiarEstadoOrdenPedido(long idPedido, String estadoOrden)
	{
		return sqlPedido.cambiarEstadoOrdenPedido(pmf.getPersistenceManager(), idPedido, estadoOrden);
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
	public Factura adicionarFactura(Timestamp fecha, long idCliente, long idSucursal)
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
	 * MÃ©todo que inserta, de manera transaccional, una tupla en la tabla PROMOCION. Primero se aï¿½ade un producto especial y luego es asociado con la promociï¿½n
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

	public Factura adicionarVenta(Timestamp fecha, long cliente, long sucursal, long producto, long promocion, int cantidad)
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
			
			tx.begin();
			sqlEstante.disminuirExistenciasEstantes(pm, cantidad, sucursal, producto);
			tx.commit();

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

}
