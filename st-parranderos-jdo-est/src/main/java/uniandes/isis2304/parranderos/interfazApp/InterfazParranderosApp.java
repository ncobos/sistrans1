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

package uniandes.isis2304.parranderos.interfazApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jdo.JDODataStoreException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import uniandes.isis2304.parranderos.negocio.AyudaRFC7;
import uniandes.isis2304.parranderos.negocio.AyudaRFC72;
import uniandes.isis2304.parranderos.negocio.Carrito;
import uniandes.isis2304.parranderos.negocio.Cliente;
import uniandes.isis2304.parranderos.negocio.Contiene;
import uniandes.isis2304.parranderos.negocio.Factura;
import uniandes.isis2304.parranderos.negocio.Parranderos;
import uniandes.isis2304.parranderos.negocio.Pedido;
import uniandes.isis2304.parranderos.negocio.Producto;
import uniandes.isis2304.parranderos.negocio.Transaccion;
import uniandes.isis2304.parranderos.negocio.VOFactura;
import uniandes.isis2304.parranderos.negocio.VOPedido;
import uniandes.isis2304.parranderos.negocio.VOPromocion;
import uniandes.isis2304.parranderos.negocio.VOSupermercado;
import uniandes.isis2304.parranderos.negocio.VOTipoBebida;

/**
 * Clase principal de la interfaz
 * @author Germán Bravo
 */
@SuppressWarnings("serial")

public class InterfazParranderosApp extends JFrame implements ActionListener
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(InterfazParranderosApp.class.getName());

	/**
	 * Ruta al archivo de configuración de la interfaz
	 */
	private static final String CONFIG_INTERFAZ = "./src/main/resources/config/interfaceConfigApp.json"; 

	/**
	 * Ruta al archivo de configuración de los nombres de tablas de la base de datos
	 */
	private static final String CONFIG_TABLAS = "./src/main/resources/config/TablasBD_A.json"; 

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Objeto JSON con los nombres de las tablas de la base de datos que se quieren utilizar
	 */
	private JsonObject tableConfig;

	/**
	 * Asociación a la clase principal del negocio.
	 */
	private Parranderos parranderos;

	/* ****************************************************************
	 * 			Atributos de interfaz
	 *****************************************************************/
	/**
	 * Objeto JSON con la configuración de interfaz de la app.
	 */
	private JsonObject guiConfig;

	/**
	 * Panel de despliegue de interacción para los requerimientos
	 */
	private PanelDatos panelDatos;

	/**
	 * Menú de la aplicación
	 */
	private JMenuBar menuBar;

	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * Construye la ventana principal de la aplicación. <br>
	 * <b>post:</b> Todos los componentes de la interfaz fueron inicializados.
	 */
	public InterfazParranderosApp( )
	{
		// Carga la configuración de la interfaz desde un archivo JSON
		guiConfig = openConfig ("Interfaz", CONFIG_INTERFAZ);

		// Configura la apariencia del frame que contiene la interfaz gráfica
		configurarFrame ( );
		if (guiConfig != null) 	   
		{
			crearMenu( guiConfig.getAsJsonArray("menuBar") );
		}

		tableConfig = openConfig ("Tablas BD", CONFIG_TABLAS);
		parranderos = new Parranderos (tableConfig);

		String path = guiConfig.get("bannerPath").getAsString();
		panelDatos = new PanelDatos ( );

		setLayout (new BorderLayout());
		add (new JLabel (new ImageIcon (path)), BorderLayout.NORTH );          
		add( panelDatos, BorderLayout.CENTER );        
	}

	/* ****************************************************************
	 * 			Métodos de configuración de la interfaz
	 *****************************************************************/
	/**
	 * Lee datos de configuración para la aplicació, a partir de un archivo JSON o con valores por defecto si hay errores.
	 * @param tipo - El tipo de configuración deseada
	 * @param archConfig - Archivo Json que contiene la configuración
	 * @return Un objeto JSON con la configuración del tipo especificado
	 * 			NULL si hay un error en el archivo.
	 */
	private JsonObject openConfig (String tipo, String archConfig)
	{
		JsonObject config = null;
		try 
		{
			Gson gson = new Gson( );
			FileReader file = new FileReader (archConfig);
			JsonReader reader = new JsonReader ( file );
			config = gson.fromJson(reader, JsonObject.class);
			log.info ("Se encontró un archivo de configuración válido: " + tipo);
		} 
		catch (Exception e)
		{
			//			e.printStackTrace ();
			log.info ("NO se encontró un archivo de configuración válido");			
			JOptionPane.showMessageDialog(null, "No se encontró un archivo de configuración de interfaz válido: " + tipo, "Parranderos App", JOptionPane.ERROR_MESSAGE);
		}	
		return config;
	}

	/**
	 * Método para configurar el frame principal de la aplicación
	 */
	private void configurarFrame(  )
	{
		int alto = 0;
		int ancho = 0;
		String titulo = "";	

		if ( guiConfig == null )
		{
			log.info ( "Se aplica configuración por defecto" );			
			titulo = "Parranderos APP Default";
			alto = 300;
			ancho = 500;
		}
		else
		{
			log.info ( "Se aplica configuración indicada en el archivo de configuración" );
			titulo = guiConfig.get("title").getAsString();
			alto= guiConfig.get("frameH").getAsInt();
			ancho = guiConfig.get("frameW").getAsInt();
		}

		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLocation (50,50);
		setResizable( true );
		setBackground( Color.WHITE );

		setTitle( titulo );
		setSize ( ancho, alto);        
	}

	/**
	 * Método para crear el menú de la aplicación con base em el objeto JSON leído
	 * Genera una barra de menú y los menús con sus respectivas opciones
	 * @param jsonMenu - Arreglo Json con los menùs deseados
	 */
	private void crearMenu(  JsonArray jsonMenu )
	{    	
		// Creación de la barra de menús
		menuBar = new JMenuBar();       
		for (JsonElement men : jsonMenu)
		{
			// Creación de cada uno de los menús
			JsonObject jom = men.getAsJsonObject(); 

			String menuTitle = jom.get("menuTitle").getAsString();        	
			JsonArray opciones = jom.getAsJsonArray("options");

			JMenu menu = new JMenu( menuTitle);

			for (JsonElement op : opciones)
			{       	
				// Creación de cada una de las opciones del menú
				JsonObject jo = op.getAsJsonObject(); 
				String lb =   jo.get("label").getAsString();
				String event = jo.get("event").getAsString();

				JMenuItem mItem = new JMenuItem( lb );
				mItem.addActionListener( this );
				mItem.setActionCommand(event);

				menu.add(mItem);
			}       
			menuBar.add( menu );
		}        
		setJMenuBar ( menuBar );	
	}

	/* ****************************************************************
	 * 			CRUD de TipoBebida
	 *****************************************************************/



	/* ****************************************************************
	 * 			Métodos administrativos
	 *****************************************************************/
	/**
	 * Muestra el log de Parranderos
	 */
	public void mostrarLogParranderos ()
	{
		mostrarArchivo ("parranderos.log");
	}

	/**
	 * Muestra el log de datanucleus
	 */
	public void mostrarLogDatanuecleus ()
	{
		mostrarArchivo ("datanucleus.log");
	}

	/**
	 * Limpia el contenido del log de parranderos
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogParranderos ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("parranderos.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de parranderos ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia el contenido del log de datanucleus
	 * Muestra en el panel de datos la traza de la ejecución
	 */
	public void limpiarLogDatanucleus ()
	{
		// Ejecución de la operación y recolección de los resultados
		boolean resp = limpiarArchivo ("datanucleus.log");

		// Generación de la cadena de caracteres con la traza de la ejecución de la demo
		String resultado = "\n\n************ Limpiando el log de datanucleus ************ \n";
		resultado += "Archivo " + (resp ? "limpiado exitosamente" : "NO PUDO ser limpiado !!");
		resultado += "\nLimpieza terminada";

		panelDatos.actualizarInterfaz(resultado);
	}

	/**
	 * Limpia todas las tuplas de todas las tablas de la base de datos de parranderos
	 * Muestra en el panel de datos el número de tuplas eliminadas de cada tabla
	 */
	public void limpiarBD ()
	{
		try 
		{
			// Ejecución de la demo y recolección de los resultados
			long eliminados [] = parranderos.limpiarParranderos();

			// Generación de la cadena de caracteres con la traza de la ejecución de la demo
			String resultado = "\n\n************ Limpiando la base de datos ************ \n";
			resultado += eliminados [0] + " Gustan eliminados\n";
			resultado += eliminados [1] + " Sirven eliminados\n";
			resultado += eliminados [2] + " Visitan eliminados\n";
			resultado += eliminados [3] + " Bebidas eliminadas\n";
			resultado += eliminados [4] + " Tipos de bebida eliminados\n";
			resultado += eliminados [5] + " Bebedores eliminados\n";
			resultado += eliminados [6] + " Bares eliminados\n";
			resultado += "\nLimpieza terminada";

			panelDatos.actualizarInterfaz(resultado);
		} 
		catch (Exception e) 
		{
			//			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Muestra la presentación general del proyecto
	 */
	public void mostrarPresentacionGeneral ()
	{
		mostrarArchivo ("data/00-ST-ParranderosJDO.pdf");
	}

	/**
	 * Muestra el modelo conceptual de Parranderos
	 */
	public void mostrarModeloConceptual ()
	{
		mostrarArchivo ("data/Modelo Conceptual.pdf");
	}

	/**
	 * Muestra el esquema de la base de datos de Parranderos
	 */
	public void mostrarEsquemaBD ()
	{
		mostrarArchivo ("data/Base de datos superandes.pdf");
	}

	/**
	 * Muestra el script de creación de la base de datos
	 */
	public void mostrarScriptBD ()
	{
		mostrarArchivo ("doc/Iteracion 1/Datos sql/EsquemaSuperAndes.sql");
	}


	/**
	 * Muestra la documentación Javadoc del proyectp
	 */
	public void mostrarJavadoc ()
	{
		mostrarArchivo ("doc/index.html");
	}

	/**
	 * Muestra la información acerca del desarrollo de esta apicación
	 */
	public void acercaDe ()
	{
		String resultado = "\n\n ************************************\n\n";
		resultado += " * Universidad	de	los	Andes	(Bogotá	- Colombia)\n";
		resultado += " * Departamento	de	Ingeniería	de	Sistemas	y	Computación\n";
		resultado += " * \n";		
		resultado += " * Curso: isis2304 - Sistemas Transaccionales\n";
		resultado += " * Proyecto: SuperAndes \n";
		resultado += " * @author n.cobos, jf.torresp\n";
		resultado += " * \n";
		resultado += "\n ************************************\n\n";

		panelDatos.actualizarInterfaz(resultado);		
	}




	/**
	 * Genera una cadena de caracteres con la descripción de la excepcion e, haciendo énfasis en las excepcionsde JDO
	 * @param e - La excepción recibida
	 * @return La descripción de la excepción, cuando es javax.jdo.JDODataStoreException, "" de lo contrario
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
	 * Genera una cadena para indicar al usuario que hubo un error en la aplicación
	 * @param e - La excepción generada
	 * @return La cadena con la información de la excepción y detalles adicionales
	 */
	private String generarMensajeError(Exception e) 
	{
		String resultado = "************ Error en la ejecución\n";
		resultado += e.getLocalizedMessage() + ", " + darDetalleException(e);
		resultado += "\n\nRevise datanucleus.log y parranderos.log para más detalles";
		return resultado;
	}

	/**
	 * Limpia el contenido de un archivo dado su nombre
	 * @param nombreArchivo - El nombre del archivo que se quiere borrar
	 * @return true si se pudo limpiar
	 */
	private boolean limpiarArchivo(String nombreArchivo) 
	{
		BufferedWriter bw;
		try 
		{
			bw = new BufferedWriter(new FileWriter(new File (nombreArchivo)));
			bw.write ("");
			bw.close ();
			return true;
		} 
		catch (IOException e) 
		{
			//			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Abre el archivo dado como parámetro con la aplicación por defecto del sistema
	 * @param nombreArchivo - El nombre del archivo que se quiere mostrar
	 */
	private void mostrarArchivo (String nombreArchivo)
	{
		try
		{
			Desktop.getDesktop().open(new File(nombreArchivo));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* ****************************************************************
	 * 			Métodos de la Interacción
	 *****************************************************************/
	/**
	 * Método para la ejecución de los eventos que enlazan el menú con los métodos de negocio
	 * Invoca al método correspondiente según el evento recibido
	 * @param pEvento - El evento del usuario
	 */
	@Override
	public void actionPerformed(ActionEvent pEvento)
	{
		String evento = pEvento.getActionCommand( );		
		try 
		{
			Method req = InterfazParranderosApp.class.getMethod ( evento );			
			req.invoke ( this );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}

	/* ****************************************************************
	 * 			Programa principal
	 *****************************************************************/
	/**
	 * Este método ejecuta la aplicación, creando una nueva interfaz
	 * @param args Arreglo de argumentos que se recibe por línea de comandos
	 */
	public static void main( String[] args )
	{
		try
		{

			// Unifica la interfaz para Mac y para Windows.
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
			InterfazParranderosApp interfaz = new InterfazParranderosApp( );
			interfaz.setVisible( true );
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}


	/**
	 * Adiciona un supermercado con la información dada por el usuario
	 * Se crea una nueva tupla de supermercado en la base de datos, si un supermercado con ese nombre no existía
	 */
	public void adicionarSupermercado( )
	{
		try 
		{
			String nombreTipo = JOptionPane.showInputDialog (this, "Nombre del supermercado", "Adicionar supermercado", JOptionPane.QUESTION_MESSAGE);


			if (nombreTipo != null)
			{
				VOSupermercado tb = parranderos.adicionarSupermercado(nombreTipo);
				if (tb == null)
				{
					throw new Exception ("No se pudo crear un supermercado con nombre: " + nombreTipo);
				}
				String resultado = "En adicionarSupermercado\n\n";
				resultado += "Supermercado adicionado exitosamente: " + tb;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Adiciona una promoción con la información dada por el usuario
	 * Se crea una nueva tupla de promoción en la base de datos.
	 */
	public void adicionarPromocion( )
	{
		try 
		{
			String nombre = JOptionPane.showInputDialog (this, "Nombre del producto en promoción", "Adicionar promocion", JOptionPane.QUESTION_MESSAGE);
			String marca = JOptionPane.showInputDialog (this, "Marca del producto en promoción", "Adicionar promocion", JOptionPane.QUESTION_MESSAGE);
			String presentacion = JOptionPane.showInputDialog (this, "Presentación del producto en promoción", "Adicionar promocion", JOptionPane.QUESTION_MESSAGE);
			String codigobarras = JOptionPane.showInputDialog (this, "Código de barras del producto en promoción", "Adicionar promocion", JOptionPane.QUESTION_MESSAGE);
			String unidadmedida = JOptionPane.showInputDialog (this, "Unidad de medida del producto en promoción", "Adicionar promocion", JOptionPane.QUESTION_MESSAGE);
			String categoria = JOptionPane.showInputDialog (this, "Categoría del producto en promoción", "Adicionar promocion", JOptionPane.QUESTION_MESSAGE);
			String tipo = JOptionPane.showInputDialog (this, "Tipodel producto en promoción", "Adicionar promocion", JOptionPane.QUESTION_MESSAGE);

			String precio = JOptionPane.showInputDialog (this, "Precio de la promoción", "Adicionar promocion", JOptionPane.QUESTION_MESSAGE);
			double precio2 = Double.parseDouble(precio);
			String descripcion = JOptionPane.showInputDialog (this, "Descripción de la promoción", "Adicionar promocion", JOptionPane.QUESTION_MESSAGE);
			String fechainicio = JOptionPane.showInputDialog (this, "Fecha de inicio de la promoción. Escribir dia/mes/año sin espacios (ej: 14/09/2018)", "Adicionar promocion", JOptionPane.QUESTION_MESSAGE);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse(fechainicio);
			long time = date.getTime();
			Timestamp fechaInicio = new Timestamp(time);

			String fechafin = JOptionPane.showInputDialog (this, "Fecha de fin de la promoción. Escribir dia/mes/año sin espacios (ej: 14/09/2018)", "Adicionar promocion", JOptionPane.QUESTION_MESSAGE);
			Date date2 = dateFormat.parse(fechafin);
			long time2 = date2.getTime();
			Timestamp fechaFin = new Timestamp(time2);
			String unidadesdisponibles = JOptionPane.showInputDialog (this, "Unidades disponibles para la promoción", "Adicionar promocion", JOptionPane.QUESTION_MESSAGE);
			int unidades = Integer.parseInt(unidadesdisponibles);

			if (nombre!=null && marca!= null && presentacion!= null && codigobarras!= null && unidadmedida!= null && categoria!= null && tipo!= null &&precio != null && descripcion != null && fechainicio!=null && fechafin!=null && unidadesdisponibles!=null)
			{
				VOPromocion tb = parranderos.adicionarPromocion(nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo, precio2, descripcion, fechaInicio, fechaFin, unidades);
				if (tb == null)
				{
					throw new Exception ("No se pudo crear la promoción : " + descripcion);
				}
				String resultado = "En adicionarPromocion\n\n";
				resultado += "Promocion adicionado exitosamente: " + tb;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}


	/**
	 * Adiciona una venta con la información dada por el usuario
	 * Se crea una nueva tupla de factura en la base de datos, si una factura con ese numero no existía
	 */
	public void adicionarVenta( )
	{
		try 
		{
			String cliente = JOptionPane.showInputDialog (this, "Identificación del cliente", "Adicionar venta", JOptionPane.QUESTION_MESSAGE);
			String fecha2 = JOptionPane.showInputDialog (this, "Fecha de la creación de la factura. Escribir dia/mes/año sin espacios (ej: 14/09/2018)", "Adicionar venta", JOptionPane.QUESTION_MESSAGE);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse(fecha2);
			long time = date.getTime();
			Timestamp fecha = new Timestamp(time);
			String sucursal2 = JOptionPane.showInputDialog (this, "Identificación de la sucursal donde se realiza la venta", "Adicionar venta", JOptionPane.QUESTION_MESSAGE);
			long sucursal = Long.parseLong(sucursal2);
			String producto2 = JOptionPane.showInputDialog (this, "Identificación del producto", "Adicionar venta", JOptionPane.QUESTION_MESSAGE);
			long producto = Long.parseLong(producto2);
			String promocion2 = JOptionPane.showInputDialog (this, "Identificación de la promoción, si no existe poner 0", "Adicionar venta", JOptionPane.QUESTION_MESSAGE);
			long promocion = Long.parseLong(promocion2);
			String cantidad2 = JOptionPane.showInputDialog (this, "Cantidad de unidades vendidas", "Adicionar venta", JOptionPane.QUESTION_MESSAGE);
			int cantidad = Integer.parseInt(cantidad2);


			if ( cliente != null && fecha2!= null && sucursal2 != null && producto2 != null && cantidad2 != null  )
			{
				VOFactura tb = parranderos.adicionarVenta(fecha, cliente, sucursal, producto, promocion, cantidad);
				if (tb == null)
				{
					throw new Exception ("No se pudo crear una factura para el cliente: " + cliente);
				}
				String resultado = "En adicionarVenta\n\n";
				resultado += "Venta adicionada exitosamente: " + tb;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}

	}

	/**
	 *  Adiciona una pedido con la información dada por el usuario
	 * Se crea una nueva tupla de pedido en la base de datos.
	 */
	public void registrarPedido()
	{
		try 
		{
			String proveedor2 = JOptionPane.showInputDialog (this, "Identificación del proveedor al cual se le solicita el producto", "Adicionar pedido", JOptionPane.QUESTION_MESSAGE);
			long proveedor = Long.parseLong(proveedor2);
			String fecha2 = JOptionPane.showInputDialog (this, "Fecha de recepción del producto. Recuerde que existe un tiempo hábil para que los proveedores procesen la orden. Escribir dia/mes/año sin espacios (ej: 14/09/2018)", "Adicionar pedido", JOptionPane.QUESTION_MESSAGE);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse(fecha2);
			long time = date.getTime();
			Timestamp fechaEntrega = new Timestamp(time);
			String sucursal2 = JOptionPane.showInputDialog (this, "Identificación de la sucursal donde se realiza el pedido", "Adicionar pedido", JOptionPane.QUESTION_MESSAGE);
			long sucursal = Long.parseLong(sucursal2);
			String producto2 = JOptionPane.showInputDialog (this, "Identificación del producto a pedir", "Adicionar pedido", JOptionPane.QUESTION_MESSAGE);
			long producto = Long.parseLong(producto2);
			String cantidad2 = JOptionPane.showInputDialog (this, "Cantidad de unidades solicitadas", "Adicionar pedido", JOptionPane.QUESTION_MESSAGE);
			int cantidad = Integer.parseInt(cantidad2);


			if ( proveedor2 != null && fecha2!= null && sucursal2 != null && producto2 != null && cantidad2 != null  )
			{
				VOPedido tb = parranderos.adicionarPedido(proveedor, sucursal, fechaEntrega, "pendiente", cantidad, 1, producto);
				if (tb == null)
				{
					throw new Exception ("No se pudo crear un pedido del producto: " + producto);
				}
				String resultado = "En adicionarPedido\n\n";
				resultado += "Pedido registrado exitosamente: " + tb;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Se recibe un pedido
	 * Se actualizan las existencias en la base de datos
	 */
	public void recibirPedido()
	{
		try 
		{
			String idPedido2 = JOptionPane.showInputDialog (this, "Identificación del pedido que se quiere recibir", "Recibir pedido", JOptionPane.QUESTION_MESSAGE);
			long idPedido = Long.parseLong(idPedido2);
			String calificacion2 = JOptionPane.showInputDialog (this, "Calificación del servicio prestado por el proveedor (1-10)", "Recibir pedido", JOptionPane.QUESTION_MESSAGE);
			int calificacion = Integer.parseInt(calificacion2);


			if ( idPedido2!=null && calificacion2!= null)
			{
				VOPedido tb = parranderos.recibirPedido(idPedido, calificacion);
				if (tb == null)
				{
					throw new Exception ("No se pudo recibir el pedido: " + idPedido);
				}
				String resultado = "En recibirPedido\n\n";
				resultado += "Pedido recibido exitosamente: " + tb;
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Se elimina una promocion dado un id
	 * Se elimina una tupla de promoción en la base de datos.
	 */
	public void eliminarPromocion()
	{
		try 
		{
			String idProm2 = JOptionPane.showInputDialog (this, "Identificador de la promoción que se quiere eliminar", "Eliminar promoción", JOptionPane.QUESTION_MESSAGE);
			long idProm = Long.parseLong(idProm2);


			if ( idProm2!=null)
			{
				long tbEliminados = parranderos.eliminarPromocionPorId(idProm);

				String resultado = "En eliminar Promocion\n\n";
				resultado += tbEliminados + " Promociones eliminadas\n";
				resultado += "\n Operación terminada";
				panelDatos.actualizarInterfaz(resultado);
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	/**
	 * Genera una cadena de caracteres con la lista de parejas de números recibida: una línea por cada pareja
	 * @param lista - La lista con las pareja
	 * @return La cadena con una líea para cada pareja recibido
	 */
	public String listarSucursalesyVentas() 
	{
		String resp = "Las sucursales y sus ventas son:\n";

		try 
		{
			String fecha2 = JOptionPane.showInputDialog (this, "Fecha inicio de ventas. Escribir dia/mes/año sin espacios (ej: 14/09/2018)", "Ventas sucursal", JOptionPane.QUESTION_MESSAGE);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse(fecha2);
			long time = date.getTime();
			Timestamp fechainicio = new Timestamp(time);

			String fecha22 = JOptionPane.showInputDialog (this, "Fecha fin de ventas. Escribir dia/mes/año sin espacios (ej: 14/09/2018)", "Ventas sucursal", JOptionPane.QUESTION_MESSAGE);
			DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
			Date date2 = dateFormat2.parse(fecha22);
			long time2 = date2.getTime();
			Timestamp fechafin = new Timestamp(time2);

			List<long[]> lista = parranderos.darSucursalYVentas(fechainicio, fechafin);

			int i = 1;
			for ( long [] tupla : lista)
			{
				long [] datos = tupla;
				String resp1 = i++ + ". " + "[";
				resp1 += "idSucursal: " + datos [0] + ", ";
				resp1 += "ventas: " + datos [1];
				resp1 += "]";
				resp += resp1 + "\n";
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}

	//	public String darIndiceOcupacionBodegasEstantes()
	//	{
	//		String resp = "Las bodegas y estantes con su respectivo indice de ocupación son:\n";
	//		try 
	//		{
	//			String idSucursal = JOptionPane.showInputDialog(this, "Ingrese el id de la sucursal que desea:", "Indice de ocupación", JOptionPane.QUESTION_MESSAGE);
	//			long id = Long.parseLong(idSucursal);
	//			List<long []> lista = parranderos.darIndiceOcupacionBodegasEstantes(id);
	//
	//			int i = 1;
	//			for ( long [] tupla : lista)
	//			{
	//				long [] datos = tupla;
	//				String resp1 = i++ + ". " + "[";
	//				resp1 += "idBodega: " + datos [0] + ", ";
	//				resp1 += "indice ocupación: " + datos [1];
	//				resp1 += "idEstante" + datos[2] + ",";
	//				resp1 += "indice ocupación:" + datos[3];
	//				resp1 += "]";
	//				resp += resp1 + "\n";
	//			}
	//			panelDatos.actualizarInterfaz(resp);
	//			return resp;
	//
	//		} 
	//		catch (Exception e) 
	//		{
	//			e.printStackTrace();
	//			String resultado = generarMensajeError(e);
	//			panelDatos.actualizarInterfaz(resultado);
	//		}
	//
	//		return resp;
	//	}

	/**
	 * Genera una cadena de caracteres con la lista de parejas de números recibida: una línea por cada pareja
	 * @param lista - La lista con las pareja
	 * @return La cadena con una líea para cada pareja recibido
	 */
	public String mostrarPromocionesPopulares() 
	{
		List<long[]> lista = parranderos.darPromocionesPopulares();

		String resp = "Las promociones y sus ventas son:\n";
		int i = 1;
		for ( long [] tupla : lista)
		{
			long [] datos = tupla;
			String resp1 = i++ + ". " + "[";
			resp1 += "idPromocion: " + datos [0] + ", ";
			resp1 += "ratio ventas: " + datos [1];
			resp1 += "]";
			resp += resp1 + "\n";
		}
		return resp;
	}

	public String darProductosPorCiudad()
	{
		String resp = "Los productos con la ciudad dada son:\n";
		try 
		{
			String ciudad = JOptionPane.showInputDialog(this, "Ingrese la ciudad de donde desea conocer los productos:", "Productos por ciudad", JOptionPane.QUESTION_MESSAGE);
			List<Producto> lista = parranderos.darProductosPorCiudad(ciudad);

			int i = 1;
			for ( Producto producto : lista)
			{
				Producto datos = producto;
				String resp1 = i++ + ". " + "[";
				resp1 += "id: " + datos.getId() + ", ";
				resp1 += "nombre: " + datos.getNombre() + ", ";
				resp1 += "marca:" + datos.getMarca() + ", ";
				resp1 += "presentacion:" + datos.getPresentacion() + ", ";
				resp1 += "código barras:" + datos.getCodigobarras() + ", ";
				resp1 += "unidad medida:" + datos.getUnidadMedida() + ", ";
				resp1 += "categoria:" + datos.getCategoria() + ", ";
				resp1 += "tipo:" + datos.getTipo();
				resp1 += "]";
				resp += resp1 + "\n";
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;

		} catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}

	/**
	 * Método en la interfaz para mostrar las compras hechas a los proveedores.
	 * @return respuesta de la solicitud
	 */
	public String comprasProveedores() 
	{
		String resp = "Las compras hechas a los proveedores son:\n";

		try 
		{
			List<List<Pedido>> lista = parranderos.darPedidosProveedores();
			System.out.println(lista.size());

			int i = 1;
			for ( List<Pedido> tupla : lista)
			{
				List<Pedido> datos = tupla;
				String resp1 = (i++) + ". " + "Proveedor con id: " + datos.get(0).getIdProveedor() + "[";
				
				for ( Pedido tupla2 : datos)
				{
					resp1 += tupla2.toString();
					resp1 += "\n";
				}
				
				resp += resp1 + "\n";
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}
	
	/**
	 * Método en la interfaz para solicitar un carrito de compras
	 * @return respuesta de la solicitud
	 */
	public String solicitarCarrito()
	{
		String resp = "El identificador y la contraseña del carrito asignado es: ";
		try 
		{
			String sucursalita = JOptionPane.showInputDialog(this, "Ingrese la sucursal para usar su carrito de mercado:", "Asignar carrito", JOptionPane.QUESTION_MESSAGE);
			long sucursal = Long.parseLong(sucursalita);
			String pass = JOptionPane.showInputDialog(this, "Ingrese la contraseña para su carrito de mercado:", "Asignar carrito", JOptionPane.QUESTION_MESSAGE);
			long clave = Long.parseLong(pass);
			Carrito car = parranderos.asignarCarrito(sucursal, clave);
			System.out.println(car);
			long id = car.getId();
			resp+=id;
			panelDatos.actualizarInterfaz(resp);
			return resp;

		} catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}

	/**
	 * Método en la interfaz para agregar un producto a un carrito de compras
	 * @return respuesta de la solicitud
	 */
	public String adicionarProductoCarrito()
	{
		String resp = "El producto adicionado es:";
		try {
			String id = JOptionPane.showInputDialog(this, "Ingrese el identificador de su carrito de mercado:", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
			String pass = JOptionPane.showInputDialog(this, "Ingrese la contraseña de su carrito de mercado:", "Adicionar producto", JOptionPane.QUESTION_MESSAGE);
			String producto = JOptionPane.showInputDialog(this, "Ingrese el identificador del producto que desea agregar:", "Agregar producto", JOptionPane.QUESTION_MESSAGE);
			String cantidad = JOptionPane.showInputDialog(this, "Ingrese la cantidad de unidades del producto:", "Agregar producto", JOptionPane.QUESTION_MESSAGE);
			
			long idCarrito = Long.parseLong(id);
			long clave = Long.parseLong(pass);
			long idProducto  = Long.parseLong(producto);
			int cant = Integer.parseInt(cantidad);

			Contiene add = parranderos.adicionarProductoCarrito(idCarrito, clave, idProducto, cant);
			
			if(add == null)
			{
				throw new Exception ("Los datos del carrito no son correctos");
			}
			System.out.println(add);
			long prod = add.getProducto();
			resp+=prod;
			
			panelDatos.actualizarInterfaz(resp);
			return resp;
		} 
		catch (Exception e) {

			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}

	/**
	 * Método en la interfaz para devolver un producto que está en un carrito de compras
	 * @return respuesta de la solicitud de devolución
	 */
	public String devolverProductoCarrito()
	{
		String resp = "El producto devuelto es: ";
		Boolean hola = false;
		try 
		{
			String id = JOptionPane.showInputDialog(this, "Ingrese el identificador de su carrito de mercado:", "Devolver producto", JOptionPane.QUESTION_MESSAGE);
			if(id == null) {hola = true;}
			String pass = JOptionPane.showInputDialog(this, "Ingrese la contraseña de su carrito de mercado:", "Devolver producto", JOptionPane.QUESTION_MESSAGE);
			if(pass == null) {hola = true;}
			String producto = JOptionPane.showInputDialog(this, "Ingrese el identificador del producto que desea devolver:", "Devolver producto", JOptionPane.QUESTION_MESSAGE);
			if(producto == null) {hola = true;}
			String sucursal = JOptionPane.showInputDialog(this, "Ingrese la sucursal que vende el producto que desea devolver:", "Devolver producto", JOptionPane.QUESTION_MESSAGE);
			if( sucursal == null) {hola = true;}

			if(!hola)
			{
				long idCarrito = Long.parseLong(id);
				long clave = Long.parseLong(pass);
				long idProducto  = Long.parseLong(producto);
				long idSucursal= Long.parseLong(sucursal);

				Contiene con = parranderos.devolverProducto(idCarrito,clave, idProducto, idSucursal);
				if(con == null)
				{
					throw new Exception ("Los datos del carrito no son correctos");
				}
				System.out.println(con);
				long id2 = con.getProducto();
				resp+=id2;
				panelDatos.actualizarInterfaz(resp);
				return resp;
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}

		} catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}
	
	/**
	 * Método de la interfaz que se conecta con la lógica para abandonar un carrito.
	 * @return mensaje de confirmación/rechazo
	 */
	public String abandonarCarrito()
	{
		String resp = "El carrito abandonado fue: ";
		Boolean hola = false;
		try 
		{
			String id = JOptionPane.showInputDialog(this, "Ingrese el identificador de su carrito de mercado:", "Abandonar carrito de compras", JOptionPane.QUESTION_MESSAGE);
			if(id == null) {hola = true;}
			String pass = JOptionPane.showInputDialog(this, "Ingrese la contraseña de su carrito de mercado:", "Abandonar carrito de compras", JOptionPane.QUESTION_MESSAGE);
			if(pass == null) {hola = true;}
			
			if(!hola)
			{
				long idCarrito = Long.parseLong(id);
				long clave = Long.parseLong(pass);

				Carrito car = parranderos.abandonarCarrito(idCarrito,clave);
				if(car == null)
				{
					throw new Exception ("Los datos del carrito no son correctos");
				}
				System.out.println(car);
				long id2 = car.getId();
				resp+=id2;
				panelDatos.actualizarInterfaz(resp);
				return resp;
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}

		} catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}
	
	/**
	 * Método de la interfaz que se conecta con la lógica de la aplicación para recoger
	 * los productos de los carritos abandonados. Actualiza en la interfaz el resultado del
	 * proceso
	 */
	
	public void recolectarProductos()
	{
		try {
			

			parranderos.recolectarProductos();
			String resultado = "Productos recolectados exitosamente";
			panelDatos.actualizarInterfaz(resultado);		
			
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
	}

	
	/**
	 * Método de la interfaz que se conecta con la lógica para analizar la operacion de Superandes.
	 * @return mensaje con la informacion requerida
	 */
	public String analizarOperacion()
	{
		String resp = "Resumen de la operación de SuperAndes según el rango dado : \n";
		Boolean hola = false;
		try 
		{
			String fecha = JOptionPane.showInputDialog(this, "Ingrese las unidades de tiempo a evaluar:", "Analizar operación SuperAndes", JOptionPane.QUESTION_MESSAGE);
			if(fecha == null) {hola = true;}
			String producto = JOptionPane.showInputDialog(this, "Ingrese el tipo de producto que se quiere buscar:", "Analizar operación SuperAndes", JOptionPane.QUESTION_MESSAGE);
			if(producto == null) {hola = true;}
			
			if(!hola)
			{
				

				AyudaRFC7 car = parranderos.analizarOperacion1(fecha, producto);
				AyudaRFC72 car2 = parranderos.analizarOperacion2(fecha, producto);
				if(car == null || car2 == null)
				{
					throw new Exception ("El objeto resultó siendo null");
				}
				System.out.println(car);
				
				resp+=fecha + " con más productos vendidos: "+"  año: "+  + car.getAnio() + ", mes:" + car.getMes()+ ", cantidad total de unidades vendidas: " + car.getCantidadmaxima() + "\n";
				resp+=fecha + " con más dinero recaudado: "+"  año: "+  + car2.getAnio() + ", mes:" + car2.getMes()+ ", costo total de unidades vendidas: " + car2.getCostomaximo() + "\n";

				panelDatos.actualizarInterfaz(resp);
				return resp;
			}
			else
			{
				panelDatos.actualizarInterfaz("Operación cancelada por el usuario");
			}

		} catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}
	
	
	/**
	 * Método de la interfaz que se conecta con la lógica para pagar una compra haciendo uso de un carrito
	 * @return respuesta de confirmación o errror
	 */
	public String pagarCompra()
	{
		String resp = "Información de la factura del carrito pagado:" + "\n";
		
		try {
			
			String fecha2 = JOptionPane.showInputDialog (this, "Fecha de la creación de la factura. Escribir dia/mes/año sin espacios (ej: 14/09/2018)", "Pagar compra", JOptionPane.QUESTION_MESSAGE);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse(fecha2);
			long time = date.getTime();
			Timestamp fecha = new Timestamp(time);
			
			String cliente = JOptionPane.showInputDialog (this, "Identificación del cliente", "Pagar compra", JOptionPane.QUESTION_MESSAGE);
			
			String id = JOptionPane.showInputDialog(this, "Ingrese el identificador de su carrito de mercado:", "Pagar compra", JOptionPane.QUESTION_MESSAGE);
			
			String pass = JOptionPane.showInputDialog(this, "Ingrese la contraseña de su carrito de mercado:", "Pagar compra", JOptionPane.QUESTION_MESSAGE);
			
			long idCarrito = Long.parseLong(id);
			
			long clave = Long.parseLong(pass);
			
			

			Factura factura = parranderos.pagarcompra(idCarrito, clave, fecha, cliente);
			
			long idfactura = factura.getNumero();
			//System.out.println(idfactura);
			
			List<Transaccion> transacciones = parranderos.darTransaccionesPorFactura(idfactura);
			//System.out.println(transacciones.size());
		
			double costoTotal = 0;
			
			resp += "Id Cliente:" + cliente + "," + "\n"+ "Id Carrito:" + id + "," + "\n" + "Fecha factura:" + fecha + "\n";
			
			System.out.println("voy a entrar");
			for(Transaccion actual: transacciones)
			{
				int cantidad = actual.getCantidad();
				double costo = actual.getCosto();
				long producto = actual.getIdProducto();
				
				System.out.println("ahí voy");
				
				costoTotal+= costo;
				//System.out.println("estoy dentro");
				
				resp+=  "Id producto:" + producto + "," + "Costo: "+ costo + "\n";
			}
			
			//System.out.println(factura);
			
			resp+= "Costo Total:" + costoTotal;
			
			panelDatos.actualizarInterfaz(resp);
				
			return resp;		
		} 
		catch (Exception e) {
			// TODO: handle exception
		}
		return resp;
	}
	
	public String darClientesFrecuentes()
	{
		String resp = "Los clientes más frecuentes son:\n";

		try 
		{
			String sucursal2 = JOptionPane.showInputDialog (this, "Ingrese la sucursal:", "Clientes frecuentes", JOptionPane.QUESTION_MESSAGE);
			long sucursal = Long.parseLong(sucursal2);

			
			List<long[]> lista = parranderos.darClientesFrecuentes(sucursal);

			int i = 1;
			for ( long [] tupla : lista)
			{
				long [] datos = tupla;
				String resp1 = i++ + ". " + "[";
				resp1 += "idCliente: " + datos [0] + ", ";
				resp1 += "Mes: " + datos [1] + ", ";
				resp1 += "Año:" + datos [2] + ", ";
				resp1 += "Número facturas:" + datos[3];
				resp1 += "]";
				resp += resp1 + "\n";
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}

	
	public String consumo1()
	{
		String resp = "Los clientes que consumieron ese producto son:\n";

		try 
		{
			String producto2 = JOptionPane.showInputDialog (this, "Ingrese el identificador del producto:", "Consumo en SuperAndes", JOptionPane.QUESTION_MESSAGE);
			long producto = Long.parseLong(producto2);

			String fechainicio = JOptionPane.showInputDialog (this, "Fecha de inicio de la consulta. Escribir dia/mes/año sin espacios (ej: 14/09/2018)", "Consumo en SuperAndes", JOptionPane.QUESTION_MESSAGE);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse(fechainicio);
			long time = date.getTime();
			Timestamp fechaInicio = new Timestamp(time);

			String fechafin = JOptionPane.showInputDialog (this, "Fecha de fin de la consulta. Escribir dia/mes/año sin espacios (ej: 14/09/2018)", "Consumo en SuperAndes", JOptionPane.QUESTION_MESSAGE);
			Date date2 = dateFormat.parse(fechafin);
			long time2 = date2.getTime();
			Timestamp fechaFin = new Timestamp(time2);
			
			String criterio = JOptionPane.showInputDialog (this, "Ingrese el criterio de ordenamiento (ejemplo: identificación del cliente, fecha, numero de unidades compradas):", "Consumo en SuperAndes", JOptionPane.QUESTION_MESSAGE);
			String criterio2 = JOptionPane.showInputDialog (this, "Ingrese el criterio de ordenamiento, ascendente (ASC) y descendente (DESC):", "Consumo en SuperAndes", JOptionPane.QUESTION_MESSAGE);

			
			List<Cliente> lista = parranderos.consumo1(producto, fechainicio, fechafin, criterio, criterio2);

			if (lista != null){
			for (Cliente cliente: lista) {
				resp+=cliente.toString();
				 resp+="\n";
				
			}
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}
	
	public String consumo2()
	{
		String resp = "Los clientes que no consumieron ese producto son:\n";

		try 
		{
			String producto2 = JOptionPane.showInputDialog (this, "Ingrese el identificador del producto:", "Consumo en SuperAndes V2", JOptionPane.QUESTION_MESSAGE);
			long producto = Long.parseLong(producto2);

			String fechainicio = JOptionPane.showInputDialog (this, "Fecha de inicio de la consulta. Escribir dia/mes/año sin espacios (ej: 14/09/2018)", "Consumo V2 en SuperAndes", JOptionPane.QUESTION_MESSAGE);
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date date = dateFormat.parse(fechainicio);
			long time = date.getTime();
			Timestamp fechaInicio = new Timestamp(time);

			String fechafin = JOptionPane.showInputDialog (this, "Fecha de fin de la consulta. Escribir dia/mes/año sin espacios (ej: 14/09/2018)", "Consumo V2 en SuperAndes", JOptionPane.QUESTION_MESSAGE);
			Date date2 = dateFormat.parse(fechafin);
			long time2 = date2.getTime();
			Timestamp fechaFin = new Timestamp(time2);
			
			String criterio = JOptionPane.showInputDialog (this, "Ingrese el criterio de ordenamiento (ejemplo: identificación del cliente, fecha):", "Consumo V2 en SuperAndes", JOptionPane.QUESTION_MESSAGE);
			String criterio2 = JOptionPane.showInputDialog (this, "Ingrese el criterio de ordenamiento, ascendente (ASC) y descendente (DESC):", "Consumo V2 en SuperAndes", JOptionPane.QUESTION_MESSAGE);

			
			List<Cliente> lista = parranderos.consumo1(producto, fechainicio, fechafin, criterio, criterio2);

			if (lista != null){
			for (Cliente cliente: lista) {
				resp+=cliente.toString();
				 resp+="\n";
				
			}
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}
	
	public String clienteMasFrecuente1()
	{
		String resp = "Los clientes más frecuentes mensualmente son:\n";

		try 
		{	
			List<long[]> lista = parranderos.clienteMasFrecuente1();

			int i = 1;
			for ( long [] tupla : lista)
			{
				long [] datos = tupla;
				String resp1 = i++ + ". " + "[";
				resp1 += "idCliente: " + datos [0] + ", ";
				resp1 += "Mes: " + datos [1] + ", ";
				resp1 += "Compras:" + datos[2];
				resp1 += "]";
				resp += resp1 + "\n";
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}
	
	public String clienteMasFrecuente2()
	{
		String resp = "Los clientes más frecuentes que compran productos de mas de $100,000 son:\n";

		try 
		{	
			List<long[]> lista = parranderos.clienteMasFrecuente2();

			int i = 1;
			for ( long [] tupla : lista)
			{
				long [] datos = tupla;
				String resp1 = i++ + ". " + "[";
				resp1 += "idCliente: " + datos [0] + ", ";
				resp1 += "Producto: " + datos [1] + ", ";
				resp1 += "Precio:" + datos[2];
				resp1 += "]";
				resp += resp1 + "\n";
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}
	
	public String clienteMasFrecuente3()
	{
		String resp = "Los clientes más frecuentes que compran productos de tipo herramientas son:\n";

		try 
		{	
			List<String[]> lista = parranderos.clienteMasFrecuente3();

			int i = 1;
			for ( String [] tupla : lista)
			{
				String [] datos = tupla;
				String resp1 = i++ + ". " + "[";
				resp1 += "idCliente: " + datos [0] + ", ";
				resp1 += "Producto: " + datos [1] + ", ";
				resp1 += "Precio: " + datos [2] + ", ";
				resp1 += "Categoria:" + datos[3];
				resp1 += "]";
				resp += resp1 + "\n";
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}
	
	public String clienteMasFrecuente4()
	{
		String resp = "Los clientes más frecuentes que compran productos de tipo electrodomésticos son:\n";

		try 
		{	
			List<String[]> lista = parranderos.clienteMasFrecuente4();

			int i = 1;
			for ( String [] tupla : lista)
			{
				String [] datos = tupla;
				String resp1 = i++ + ". " + "[";
				resp1 += "idCliente: " + datos [0] + ", ";
				resp1 += "Producto: " + datos [1] + ", ";
				resp1 += "Precio: " + datos [2] + ", ";
				resp1 += "Categoria:" + datos[3];
				resp1 += "]";
				resp += resp1 + "\n";
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}

	
	public String consultarFuncionamiento1()
	{
		String resp = "El (los) productos más vendido(s) es (son):\n";

		try 
		{	
			List<String[]> lista = parranderos.consultarFuncionamiento1();

			int i = 1;
			for ( String [] tupla : lista)
			{
				String [] datos = tupla;
				String resp1 = i++ + ". " + "[";
				resp1 += "Nombre del producto: " + datos [0] + ", ";
				resp1 += "Ventas del producto: " + datos [1] + ", ";
				resp1 += "Semana: " + datos [2];
				resp1 += "]";
				resp += resp1 + "\n";
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}
	
	public String consultarFuncionamiento2()
	{
		String resp = "El (los) producto(s) menos vendido(s) es (son):\n";

		try 
		{	
			List<String[]> lista = parranderos.consultarFuncionamiento2();

			int i = 1;
			for ( String [] tupla : lista)
			{
				String [] datos = tupla;
				String resp1 = i++ + ". " + "[";
				resp1 += "Nombre del producto: " + datos [0] + ", ";
				resp1 += "Ventas del producto: " + datos [1] + ", ";
				resp1 += "Semana: " + datos [2];
				resp1 += "]";
				resp += resp1 + "\n";
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}
	
	
	public String consultarFuncionamiento3()
	{
		String resp = "El (los) proveedor(es) más solicitado(s) es (son):\n";

		try 
		{	
			List<String[]> lista = parranderos.consultarFuncionamiento3();

			int i = 1;
			for ( String [] tupla : lista)
			{
				String [] datos = tupla;
				String resp1 = i++ + ". " + "[";
				resp1 += "Identificador del proveedor: " + datos [0] + ", ";
				resp1 += "Cantidad de pedidos a su nombre: " + datos [1];
				resp1 += "]";
				resp += resp1 + "\n";
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}
	
	public String consultarFuncionamiento4()
	{
		String resp = "El (los) proveedor(es) menos solicitado(s) es (son):\n";

		try 
		{	
			List<String[]> lista = parranderos.consultarFuncionamiento4();

			int i = 1;
			for ( String [] tupla : lista)
			{
				String [] datos = tupla;
				String resp1 = i++ + ". " + "[";
				resp1 += "Identificador del proveedor: " + datos [0] + ", ";
				resp1 += "Cantidad de pedidos a su nombre: " + datos [1];
				resp1 += "]";
				resp += resp1 + "\n";
			}
			panelDatos.actualizarInterfaz(resp);
			return resp;
		}

		catch (Exception e) 
		{
			e.printStackTrace();
			String resultado = generarMensajeError(e);
			panelDatos.actualizarInterfaz(resultado);
		}
		return resp;
	}

}
