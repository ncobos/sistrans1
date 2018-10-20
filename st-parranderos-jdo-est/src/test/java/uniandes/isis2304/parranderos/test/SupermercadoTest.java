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
import uniandes.isis2304.parranderos.negocio.VOSupermercado;

/**
 * Clase con los métdos de prueba de funcionalidad sobre SUPERMERCADO
 * @author n.cobos, jf.torresp
 *
 */
public class SupermercadoTest {
	
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
	 * 			Métodos de prueba para la tabla TipoBebida - Creación y borrado
	 *****************************************************************/
	/**
	 * Método que prueba las operaciones sobre la tabla TipoBebida
	 * 1. Adicionar un tipo de bebida
	 * 2. Listar el contenido de la tabla con 0, 1 y 2 registros insertados
	 * 3. Borrar un tipo de bebida por su identificador
	 * 4. Borrar un tipo de bebida por su nombre
	 */
    @Test
	public void CRDSupermercadoTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando las operaciones CRD sobre Supermercado");
			parranderos = new Parranderos (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de CRD de Supermercado incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de CRD de Supermercado incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
    	try
		{
			// Lectura de los supermercados con la tabla vacía
			List <VOSupermercado> lista = parranderos.darVOSupermercado();

			// Lectura de los supermercados con un nombre adicionado
			String nombreSupermercado1 = "Metro";
			VOSupermercado supermercado1 = parranderos.adicionarSupermercado(nombreSupermercado1);
			lista = parranderos.darVOSupermercado();
			assertEquals ("Debe haber un supermercado creado !!", 8, lista.size ());
			assertEquals ("El objeto creado y el traido de la BD deben ser iguales !!", supermercado1, null);

			// Lectura de los supermercados con dos nombres adicionados
			String nombreSupermercado2 = "Jumbo2";
			VOSupermercado supermercado2 = parranderos.adicionarSupermercado(nombreSupermercado2);
			lista = parranderos.darVOSupermercado();
			assertEquals ("Debe haber dos supermercados creados !!", 9, lista.size ());
			assertTrue ("El primer supermercado adicionado debe estar en la tabla", supermercado1.equals (lista.get (0)) || supermercado1.equals (lista.get (1)));
			assertTrue ("El segundo  supermercado adicionado debe estar en la tabla", supermercado2.equals (lista.get (0)) || supermercado2.equals (lista.get (1)));
			
			// Prueba de eliminación de un supermercado, dado su nombre
			long smEliminados = parranderos.eliminarSupermercadoPorNombre(nombreSupermercado1);
			assertEquals ("Debe haberse eliminado un supermercado !!", 6, smEliminados);
			lista = parranderos.darVOSupermercado();
			assertEquals ("La tabla debió quedar vacía !!", 0, lista.size ());
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			String msg = "Error en la ejecución de las pruebas de operaciones sobre la tabla Supermercado.\n";
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
     * Método de prueba de la restricción de unicidad sobre el nombre de TipoBebida
     */
	@Test
	public void unicidadSupermercadoTest() 
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de UNICIDAD del nombre del supermercado");
			parranderos = new Parranderos (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de UNICIDAD de Supermercado incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de UNICIDAD de Supermercado incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los supermercado con la tabla vacía
			List <VOSupermercado> lista = parranderos.darVOSupermercado();

			// Lectura de los tipos de bebida con un tipo de bebida adicionado
			String nombreSupermercado1 = "Carulla";
			VOSupermercado supermercado1 = parranderos.adicionarSupermercado(nombreSupermercado1);
			lista = parranderos.darVOSupermercado();
			assertEquals ("Debe haber un supermercado creado sumado a los que ya están !!", 8, lista.size ());
			// Se espera un resultado de 6 suoermercados debido a que inicilamente para las pruebas en SQL Developer, ya haían 5 supermercados creados.
			VOSupermercado supermercado2 = parranderos.adicionarSupermercado(nombreSupermercado1);
			assertNull ("No puede adicionar dos supermercado con el mismo nombre !!", supermercado2);
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
	
	@Test
	public void integridadSupermercadoTest()
	{
    	// Probar primero la conexión a la base de datos
		try
		{
			log.info ("Probando la restricción de INTEGRIDAD del supermercado");
			parranderos = new Parranderos (openConfig (CONFIG_TABLAS_A));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
			log.info ("Prueba de INTEGRIDAD de Supermercado incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
			log.info ("La causa es: " + e.getCause ().toString ());

			String msg = "Prueba de INTEGRIDAD de Supermercado incompleta. No se pudo conectar a la base de datos !!.\n";
			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
			System.out.println (msg);
			fail (msg);
		}
		
		// Ahora si se pueden probar las operaciones
		try
		{
			// Lectura de los supermercado con la tabla vacía
			List <VOSupermercado> lista = parranderos.darVOSupermercado();
			String nombreSupermercado1 = "PriceSmart";
			VOSupermercado supermercado1 = parranderos.adicionarSupermercado(nombreSupermercado1);
			lista = parranderos.darVOSupermercado();
			assertEquals ("Debe haber un supermercado creado sumado a los que ya están !!", 8, lista.size ());
			
			String nombreSupermercado2 = "";
			VOSupermercado supermercado2 = parranderos.adicionarSupermercado(nombreSupermercado2);
			lista = parranderos.darVOSupermercado();
			assertEquals ("No se debió haber agregado el supermercado porque es nulo!!", 8, lista.size ());
			
			long eliminados= parranderos.eliminarSupermercadoPorNombre(nombreSupermercado1);
			assertEquals("Debi´p haber eliminado un supermercado", 1, eliminados);
			lista = parranderos.darVOSupermercado();
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
