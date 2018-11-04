package uniandes.isis2304.parranderos.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import static org.junit.Assert.fail;

import java.io.FileReader;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.Carrito;
import uniandes.isis2304.parranderos.negocio.Parranderos;

/**
 * Clase con los métodos de prueba de funcionalidad sobre los requerimientos de la iteracion 2
 * @author n.cobos
 *
 */
public class Iteracion2Test {
	
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Iteracion2Test.class.getName());
	
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
	
    
    /**
     * Metodo que testea el requerimiento funcional RF12
     */
    @Test
   	public void CRDCarritoTest() 
   	{
       	// Probar primero la conexión a la base de datos
   		try
   		{
   			log.info ("Probando las operaciones CRD sobre Carrito");
   			parranderos = new Parranderos (openConfig (CONFIG_TABLAS_A));
   		}
   		catch (Exception e)
   		{
   			e.printStackTrace();
   			log.info ("Prueba de CRD de RF12 incompleta. No se pudo conectar a la base de datos !!. La excepción generada es: " + e.getClass ().getName ());
   			log.info ("La causa es: " + e.getCause ().toString ());

   			String msg = "Prueba de CRD de RF12 incompleta. No se pudo conectar a la base de datos !!.\n";
   			msg += "Revise el log de superandes y el de datanucleus para conocer el detalle de la excepción";
   			System.out.println (msg);
   			fail (msg);
   		}
   		
   		// Ahora si se pueden probar las operaciones
       	try
   		{
   			
       		Carrito carrito = parranderos.asignarCarrito(1, 1);
   			assertNotNull("El carrito no puede ser nulo", carrito);

   			assertEquals("El estado debe ser 'en uso'", "en uso", carrito.getEstado());
   			assertEquals("La contraseña debe coincidir", 1, carrito.getClave());
   		
   		}
   		catch (Exception e)
   		{
   			e.printStackTrace();
   			String msg = "Error en la ejecución de las pruebas de operaciones sobre el requerimiento de RF12.\n";
   			msg += "Revise el log de parranderos y el de datanucleus para conocer el detalle de la excepción";
   			System.out.println (msg);

       		fail ("Error en las pruebas sobre el RF12");
   		}
   		finally
   		{
//   			parranderos.limpiarParranderos ();
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
			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de tablas válido: ", "CarritoTest", JOptionPane.ERROR_MESSAGE);
		}	
        return config;
    }	

}
