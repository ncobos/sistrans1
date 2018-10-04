package uniandes.isis2304.parranderos.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.Parranderos;
import uniandes.isis2304.parranderos.negocio.VOSucursal;
import uniandes.isis2304.parranderos.negocio.VOSupermercado;

/**
 * Clase con los métdos de prueba de funcionalidad sobre SUCURSAL
 * @author n.cobos, jf.torresp
 *
 */
public class SucursalTest {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(SupermercadoTest.class.getName());
	
	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos: La unidad de persistencia existe y el esquema de la BD también
	 */
	private static final String CONFIG_TABLAS_A = "./src/main/resources/config/TablasBD_A.json";
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
    /**
     * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
     */
    private JsonObject tableConfig;
    
	/**
	 * La clase que se quiere probar
	 */
    private Parranderos parranderos;
    
    /* ****************************************************************
	 * 			Métodos de prueba para la tabla Sucursal - Creación y borrado
	 *****************************************************************/
    @Test
	public void CRDSucursalTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre Sucursal");
			parranderos = new Parranderos (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de Sucursal incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Sucursal incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los sucursal con la tabla vacía
			List <VOSucursal> lista = parranderos.darVOSucursal();

			// Lectura de los supermercados con un nombre adicionado
			String nombreSucursal1 = "Carulla 116";
			String ciudad = "Bogotá";
			String direccion = "Calle 116";
			String segmentomercado = "Mercado emergente";
			int tamano = 20000;
			String supermercado = "Carulla";
			VOSucursal sucursal1 = parranderos.adicionarSucursal(nombreSucursal1, ciudad, direccion, segmentomercado, tamano, supermercado);
			lista = parranderos.darVOSucursal();
			assertEquals ("Debe haber una sucursal creado !!", 8, lista.size ());
			assertEquals ("El objeto creado y el traido de la BD deben ser iguales !!", sucursal1, null);

			// Lectura de los supermercados con dos nombres adicionados
			String nombreSucursal2 = "Carulla Colina";
			String ciudad2 = "Bogotá";
			String direccion2 = "Colins campestre";
			String segmentomercado2= "Mercado emergente";
			int tamano2 = 25000;
			String supermercado2 = "Carulla";
			VOSucursal sucursal2 = parranderos.adicionarSucursal(nombreSucursal2, ciudad2, direccion2, segmentomercado2, tamano2, supermercado2);
			lista = parranderos.darVOSucursal();
			assertEquals ("Debe haber dos supermercados creados !!", 9, lista.size ());
			assertTrue ("La primer sucursal adicionada debe estar en la tabla", sucursal1.equals (lista.get (0)) || sucursal2.equals (lista.get (1)));
			assertTrue ("La segunda  sucursal adicionada debe estar en la tabla", sucursal2.equals (lista.get (0)) || sucursal2.equals (lista.get (1)));
			
			// Prueba de eliminación de un supermercado, dado su nombre
			long smEliminados = parranderos.eliminarSucursalPorNombre(nombreSucursal1);
			assertEquals ("Debe haberse eliminado una sucursal!!", 6, smEliminados);
			lista = parranderos.darVOSucursal();
			assertEquals ("La tabla debió quedar vacía !!", 0, lista.size ());
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla Sucursal.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas sobre la tabla Supermercado");
		}
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
	}

    /**
     * Método de prueba de la restricción de unicidad sobre la Sucursal
     */
	@Test
	public void unicidadSucursalTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de UNICIDAD de sucursal");
			parranderos = new Parranderos (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de Sucursal incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Sucursal incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de las sucursales con la tabla vacía
			List <VOSucursal> lista = parranderos.darVOSucursal();

			// Lectura de los tipos de bebida con un tipo de bebida adicionado
			String nombreSucursal2 = "Carulla Colina";
			String ciudad2 = "Bogotá";
			String direccion2 = "Colins campestre";
			String segmentomercado2= "Mercado emergente";
			int tamano2 = 25000;
			String supermercado2 = "Carulla";
			VOSucursal sucursal1 = parranderos.adicionarSucursal(nombreSucursal2, ciudad2, direccion2, segmentomercado2, tamano2, supermercado2);
			lista = parranderos.darVOSucursal();
			String ciudad = "Bogotá";
			String direccion = "Calle 116";
			String segmentomercado = "Mercado emergente";
			int tamano = 20000;
			String supermercado = "Carulla";
			assertEquals ("Debe haber una sucursal creada sumado a los que ya están !!", 7, lista.size ());
			VOSucursal sucursal2 = parranderos.adicionarSucursal(nombreSucursal2, ciudad, direccion, segmentomercado, tamano, supermercado);
			assertNull ("No puede adicionar dos sucursales con el mismo nombre !!", sucursal2);
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla Sucursal.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas de UNICIDAD sobre la tabla Sucursal");
		}    				
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
	}
	
	@Test
	public void integridadSucursalTest()
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de INTEGRIDAD de sucursal");
			parranderos = new Parranderos (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de INTEGRIDAD de Sucursal incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de INTEGRIDAD de Sucursal incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los supermercado con la tabla vacía
			List <VOSucursal> lista = parranderos.darVOSucursal();
			String nombreSucursal2 = "Carulla Colina";
			String ciudad2 = "Bogotá";
			String direccion2 = "Colins campestre";
			String segmentomercado2= "Mercado emergente";
			int tamano2 = 25000;
			String supermercado2 = "Carulla";
			VOSucursal sucursal1 = parranderos.adicionarSucursal(nombreSucursal2, ciudad2, direccion2, segmentomercado2, tamano2, supermercado2);
			lista = parranderos.darVOSucursal();
			assertEquals ("Debe haber una sucursal creada sumado a los que ya están !!", 8, lista.size ());
			
			String nombreSucursal3 = "";
			String ciudad3 = "";
			String direccion3 = "Colins campestre";
			String segmentomercado3= "Mercado emergente";
			int tamano3 = -25000;
			String supermercado3 = "Carulla2";
			VOSucursal sucursal2 = parranderos.adicionarSucursal(nombreSucursal3, ciudad3, direccion3, segmentomercado3, tamano3, supermercado3);
			lista = parranderos.darVOSucursal();
			assertEquals ("No se debió haber agregado la sucursal porque es nula!!", 8, lista.size ());
			
			long eliminados= parranderos.eliminarSucursalPorNombre(nombreSucursal2);
			assertEquals("Debió haber eliminado una sucursal", 1, eliminados);
			lista = parranderos.darVOSucursal();
			assertEquals ("La tabla debió haber quedado con una tupla menos !!", 7, lista.size ());
						
			
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de UNICIDAD sobre la tabla Supermercado.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);

    		fail ("Error en las pruebas de UNICIDAD sobre la tabla Supermercado");
		}    				
		finally
		{
			parranderos.limpiarParranderos ();
    		parranderos.cerrarUnidadPersistencia ();    		
		}
		
	}
	
	/* ****************************************************************
	 * 			Métodos de configuración
	 *****************************************************************/
    /**
     * Lee datos de configuración para la aplicación, a partir de un archivo JSON o con valores por defecto si hay errores.
     * @param tipo - El tipo de configuración deseada
     * @param archConfig - Archivo Json que contiene la configuración
     * @return Un objeto JSON con la configuración del tipo especificado
     * 			NULL si hay un error en el archivo.
     */
    private JsonObject openConfig (String archConfig)
    {
    	JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración de tablas válido");
		} 
		catch (Exception e)
		{
//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "TipoBebidaTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }
    

}

