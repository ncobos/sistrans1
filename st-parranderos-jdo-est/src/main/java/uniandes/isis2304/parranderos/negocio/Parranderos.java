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

package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import com.google.gson.JsonObject;
import uniandes.isis2304.parranderos.persistencia.PersistenciaParranderos;

/**
 * Clase principal del negocio
 * Sarisface todos los requerimientos funcionales del negocio
 *
 * @author Germán Bravo
 */
public class Parranderos 
{
	/* ****************************************************************
	 * 			Constantes
	 *****************************************************************/
	/**
	 * Logger para escribir la traza de la ejecución
	 */
	private static Logger log = Logger.getLogger(Parranderos.class.getName());
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El manejador de persistencia
	 */
	private PersistenciaParranderos pp;
	
	/* ****************************************************************
	 * 			Métodos
	 *****************************************************************/
	/**
	 * El constructor por defecto
	 */
	public Parranderos ()
	{
		pp = PersistenciaParranderos.getInstance ();
	}
	
	/**
	 * El constructor qye recibe los nombres de las tablas en tableConfig
	 * @param tableConfig - Objeto Json con los nombres de las tablas y de la unidad de persistencia
	 */
	public Parranderos (JsonObject tableConfig)
	{
		pp = PersistenciaParranderos.getInstance (tableConfig);
	}
	
	/**
	 * Cierra la conexión con la base de datos (Unidad de persistencia)
	 */
	public void cerrarUnidadPersistencia ()
	{
		pp.cerrarUnidadPersistencia ();
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los TIPOS DE BEBIDA
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un tipo de bebida 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida
	 * @return El objeto TipoBebida adicionado. null si ocurre alguna Excepción
	 */
	public TipoBebida adicionarTipoBebida (String nombre)
	{
        log.info ("Adicionando Tipo de bebida: " + nombre);
        TipoBebida tipoBebida = pp.adicionarTipoBebida (nombre);		
        log.info ("Adicionando Tipo de bebida: " + tipoBebida);
        return tipoBebida;
	}
	
	/**
	 * Elimina un tipo de bebida por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del tipo de bebida a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarTipoBebidaPorNombre (String nombre)
	{
		log.info ("Eliminando Tipo de bebida por nombre: " + nombre);
        long resp = pp.eliminarTipoBebidaPorNombre (nombre);		
        log.info ("Eliminando Tipo de bebida por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina un tipo de bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idTipoBebida - El id del tipo de bebida a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarTipoBebidaPorId (long idTipoBebida)
	{
		log.info ("Eliminando Tipo de bebida por id: " + idTipoBebida);
        long resp = pp.eliminarTipoBebidaPorId (idTipoBebida);		
        log.info ("Eliminando Tipo de bebida por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los tipos de bebida en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos TipoBebida con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<TipoBebida> darTiposBebida ()
	{
		log.info ("Consultando Tipos de bebida");
        List<TipoBebida> tiposBebida = pp.darTiposBebida ();	
        log.info ("Consultando Tipos de bebida: " + tiposBebida.size() + " existentes");
        return tiposBebida;
	}

	/**
	 * Encuentra todos los tipos de bebida en Parranderos y los devuelve como una lista de VOTipoBebida
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOTipoBebida con todos los tipos de bebida que conoce la aplicación, llenos con su información básica
	 */
	public List<VOTipoBebida> darVOTiposBebida ()
	{
		log.info ("Generando los VO de Tipos de bebida");        
        List<VOTipoBebida> voTipos = new LinkedList<VOTipoBebida> ();
        for (TipoBebida tb : pp.darTiposBebida ())
        {
        	voTipos.add (tb);
        }
        log.info ("Generando los VO de Tipos de bebida: " + voTipos.size() + " existentes");
        return voTipos;
	}

	/**
	 * Encuentra el tipos de bebida en Parranderos con el nombre solicitado
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la bebida
	 * @return Un objeto TipoBebida con el tipos de bebida de ese nombre que conoce la aplicación, 
	 * lleno con su información básica
	 */
	public TipoBebida darTipoBebidaPorNombre (String nombre)
	{
		log.info ("Buscando Tipo de bebida por nombre: " + nombre);
		List<TipoBebida> tb = pp.darTipoBebidaPorNombre (nombre);
		return !tb.isEmpty () ? tb.get (0) : null;
	}

	/* ****************************************************************
	 * 			Métodos para manejar las BEBIDAS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una bebida 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre la bebida
	 * @param idTipoBebida - El identificador del tipo de bebida de la bebida - Debe existir un TIPOBEBIDA con este identificador
	 * @param gradoAlcohol - El grado de alcohol de la bebida (Mayor que 0)
	 * @return El objeto Bebida adicionado. null si ocurre alguna Excepción
	 */
	public Bebida adicionarBebida (String nombre, long idTipoBebida, int gradoAlcohol)
	{
		log.info ("Adicionando bebida " + nombre);
		Bebida bebida = pp.adicionarBebida (nombre, idTipoBebida, gradoAlcohol);
        log.info ("Adicionando bebida: " + bebida);
        return bebida;
	}
	
	/**
	 * Elimina una bebida por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la bebida a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarBebidaPorNombre (String nombre)
	{
        log.info ("Eliminando bebida por nombre: " + nombre);
        long resp = pp.eliminarBebidaPorNombre (nombre);
        log.info ("Eliminando bebida por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina una bebida por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebida - El identificador de la bebida a eliminar
	 * @return El número de tuplas eliminadas (1 o 0)
	 */
	public long eliminarBebidaPorId (long idBebida)
	{
        log.info ("Eliminando bebida por id: " + idBebida);
        long resp = pp.eliminarBebidaPorId (idBebida);
        log.info ("Eliminando bebida por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todas las bebida en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebida con todos las bebidas que conoce la aplicación, llenos con su información básica
	 */
	public List<Bebida> darBebidas ()
	{
        log.info ("Consultando Bebidas");
        List<Bebida> bebidas = pp.darBebidas ();	
        log.info ("Consultando Bebidas: " + bebidas.size() + " bebidas existentes");
        return bebidas;
	}

	/**
	 * Encuentra todos los tipos de bebida en Parranderos y los devuelve como una lista de VOTipoBebida
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebida con todos las bebidas que conoce la aplicación, llenos con su información básica
	 */
	public List<VOBebida> darVOBebidas ()
	{
		log.info ("Generando los VO de las bebidas");       
        List<VOBebida> voBebidas = new LinkedList<VOBebida> ();
        for (Bebida beb : pp.darBebidas ())
        {
        	voBebidas.add (beb);
        }
        log.info ("Generando los VO de las bebidas: " + voBebidas.size() + " existentes");
        return voBebidas;
	}

	/**
	 * Elimina las bebidas que no son servidas en ningún bar (No son referenciadas en ninguna tupla de SIRVEN)
	 * Adiciona entradas al log de la aplicación
	 * @return El número de bebidas eliminadas
	 */
	public long eliminarBebidasNoServidas ()
	{
        log.info ("Borrando bebidas no servidas");
        long resp = pp.eliminarBebidasNoServidas ();
        log.info ("Borrando bebidas no servidas: " + resp + " bebidas eliminadas");
        return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los BEBEDORES
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente un bebedor 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor
	 * @param presupuesto - El presupuesto del bebedor (ALTO, MEDIO, BAJO)
	 * @param ciudad - La ciudad del bebedor
	 * @return El objeto BEBEDOR adicionado. null si ocurre alguna Excepción
	 */
	public Bebedor adicionarBebedor (String nombre, String presupuesto, String ciudad)
	{
        log.info ("Adicionando bebedor: " + nombre);
        Bebedor bebedor = pp.adicionarBebedor (nombre, presupuesto, ciudad);
        log.info ("Adicionando bebedor: " + bebedor);
        return bebedor;
	}

	/**
	 * Elimina un bebedor por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bebedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarBebedorPorNombre (String nombre)
	{
        log.info ("Eliminando bebedor por nombre: " + nombre);
        long resp = pp.eliminarBebedorPorNombre (nombre);
        log.info ("Eliminando bebedor por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}

	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarBebedorPorId (long idBebedor)
	{
        log.info ("Eliminando bebedor por id: " + idBebedor);
        long resp = pp.eliminarBebedorPorId (idBebedor);
        log.info ("Eliminando bebedor por Id: " + resp + " tuplas eliminadas");
        return resp;
	}

	/**
	 * Encuentra un bebedor y su información básica, según su identificador
	 * @param idBebedor - El identificador del bebedor buscado
	 * @return Un objeto Bebedor que corresponde con el identificador buscado y lleno con su información básica
	 * 			null, si un bebedor con dicho identificador no existe
	 */
	public Bebedor darBebedorPorId (long idBebedor)
	{
        log.info ("Dar información de un bebedor por id: " + idBebedor);
        Bebedor bebedor = pp.darBebedorPorId (idBebedor);
        log.info ("Buscando bebedor por Id: " + bebedor != null ? bebedor : "NO EXISTE");
        return bebedor;
	}

	/**
	 * Encuentra la información básica de los bebedores, según su nombre
	 * @param nombre - El nombre de bebedor a buscar
	 * @return Una lista de Bebedores con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen bebedores con ese nombre
	 */
	public List<Bebedor> darBebedoresPorNombre (String nombre)
	{
        log.info ("Dar información de bebedores por nombre: " + nombre);
        List<Bebedor> bebedores = pp.darBebedoresPorNombre (nombre);
        log.info ("Dar información de Bebedores por nombre: " + bebedores.size() + " bebedores con ese nombre existentes");
        return bebedores;
 	}

	/**
	 * Encuentra la información básica de los bebedores, según su nombre y los devuelve como VO
	 * @param nombre - El nombre de bebedor a buscar
	 * @return Una lista de Bebedores con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen bebedores con ese nombre
	 */
	public List<VOBebedor> darVOBebedoresPorNombre (String nombre)
	{
        log.info ("Generando VO de bebedores por nombre: " + nombre);
        List<VOBebedor> voBebedores = new LinkedList<VOBebedor> ();
       for (Bebedor bdor : pp.darBebedoresPorNombre (nombre))
       {
          	voBebedores.add (bdor);
       }
       log.info ("Generando los VO de Bebedores: " + voBebedores.size() + " bebedores existentes");
      return voBebedores;
 	}

	/**
	 * Encuentra un bebedor, su información básica y los bares y las bebidas 
	 * con las que está directamente relacionado, según su identificador
	 * @param idBebedor - El identificador del bebedor buscado
	 * @return Un objeto Bebedor que corresponde con el identificador buscado y lleno con su información básica y 
	 * 		los bares y bebidas con los que está directamente relacionado<br>
	 * 			null, si un bebedor con dicho identificador no existe
	 */
	public Bebedor darBebedorCompleto (long idBebedor)
	{
        log.info ("Dar información COMPLETA de un bebedor por id: " + idBebedor);
        Bebedor bebedor = pp.darBebedorCompleto (idBebedor);
        log.info ("Buscando bebedor por Id: " + bebedor.toStringCompleto() != null ? bebedor : "NO EXISTE");
        return bebedor;
	}

	/**
	 * Encuentra todos los bebedores en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<Bebedor> darBebedores ()
	{
        log.info ("Listando Bebedores");
        List<Bebedor> bebedores = pp.darBebedores ();	
        log.info ("Listando Bebedores: " + bebedores.size() + " bebedores existentes");
        return bebedores;
	}
	
	/**
	 * Encuentra todos los bebedores en Parranderos y los devuelve como VOBebedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBebedor con todos las bebedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOBebedor> darVOBebedores ()
	{
        log.info ("Generando los VO de Bebedores");
         List<VOBebedor> voBebedores = new LinkedList<VOBebedor> ();
        for (Bebedor bdor : pp.darBebedores ())
        {
        	voBebedores.add (bdor);
        }
        log.info ("Generando los VO de Bebedores: " + voBebedores.size() + " bebedores existentes");
       return voBebedores;
	}
	
	/**
	 * Encuentra todos los bebedores que conoce la aplicación y el número visitas realizadas por cada uno
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de parejas [Bebedor, numVisitas]
	 */
	public List<Object []> darBebedoresYNumVisitasRealizadas ()
	{
        log.info ("Listando Bebedores y cuántas visitas ha realizado");
        List<Object []> tuplas = pp.darBebedoresYNumVisitasRealizadas ();
        log.info ("Listando Bebedores y cuántas visitas ha realizado: Listo!");
        return tuplas;
	}
	
	/**
	 * Dado el nombre de una ciudad, encuentra el número de bebedores de esa ciudad que han realizado por lo menos una visita a un bar
	 * Adiciona entradas al log de la aplicación
	 * @param ciudad - La ciudad de interés
	 * @return Un número que representa el número de bebedores de esa ciudad que hab realizado por lo menos una visita a un bar
	 */
	public long darCantidadBebedoresCiudadVisitanBares (String ciudad)
	{
        log.info ("Calculando cuántos Bebedores de una ciudad visitan bares");
        long resp = pp.darCantidadBebedoresCiudadVisitanBares (ciudad);
        log.info ("Calculando cuántos Bebedores de una ciudad visitan bares de " + ciudad +": " + resp);
        return resp;
	}
	
	/**
	 * Cambia la ciudad de un bebedor dado su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor que va a cambiar de ciudad
	 * @param ciudad - La nueva ciudad del bebedor
	 * @return El número de tuplas modificadas: 1 o 0. 0 significa que un bebedor con ese identificador no existe
	 */
	public long cambiarCiudadBebedor (long idBebedor, String ciudad)
	{
        log.info ("Cambiando ciudad de bebedor: " + idBebedor);
        long cambios = pp.cambiarCiudadBebedor (idBebedor, ciudad);
        return cambios;
	}
	
	/**
	 * Elimina un bebedor y las visitas a bares que haya realizado v1: 
	 * En caso que el bebedor esté referenciado por otra relación, NO SE BORRA NI EL BEBEDOR, NI SUS VISITAS
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El bebedor que se quiere eliminar
	 * @return Una pareja de números [número de bebedores eliminados, número de visitas eliminadas]
	 */
	public long [] eliminarBebedorYVisitas_v1 (long idBebedor)
	{
        log.info ("Eliminando bebedor con sus visitas v1: " + idBebedor);
        long [] resp = pp.eliminarBebedorYVisitas_v1 (idBebedor);
        log.info ("Eliminando bebedor con sus visitas v1: " + resp [0] + " bebedor y " + resp [1] + " visitas");
        return resp;
	}

	/**
	 * Elimina un bebedor y las visitas a bares que haya realizado v2
	 * En caso que el bebedor esté referenciado por otra relación, EL BEBEDOR NO SE BORRA. PERO SUS VISITAS SÍ
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El bebedor que se quiere eliminar
	 * @return Una pareja de números [número de bebedores eliminados, número de visitas eliminadas]
	 */
	public long [] eliminarBebedorYVisitas_v2 (long idBebedor)
	{
        log.info ("Eliminando bebedor con sus visitas v2: " + idBebedor);
        long [] resp = pp.eliminarBebedorYVisitas_v2 (idBebedor);
        log.info ("Eliminando bebedor con sus visitas v2: " + resp [0] + " bebedor y " + resp [1] + " visitas");
        return resp;
	}

	/* ****************************************************************
	 * 			Métodos para manejar los BARES
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un bar 
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bar
	 * @param presupuesto - El presupuesto del bar (ALTO, MEDIO, BAJO)
	 * @param ciudad - La ciudad del bar
	 * @param sedes - El número de sedes que tiene el bar en la ciudad (Mayor que 0)
	 * @return El objeto Bar adicionado. null si ocurre alguna Excepción
	 */
	public Bar adicionarBar (String nombre, String presupuesto, String ciudad, int sedes)
	{
        log.info ("Adicionando bar: " + nombre);
        Bar bar = pp.adicionarBar (nombre, presupuesto, ciudad, sedes);
        log.info ("Adicionando bar: " + bar);
        return bar;
	}
	
	/**
	 * Elimina un bar por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del bar a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarBarPorNombre (String nombre)
	{
        log.info ("Eliminando bar por nombre: " + nombre);
        long resp = pp.eliminarBarPorNombre (nombre);
        log.info ("Eliminando bar: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina un bebedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarBarPorId (long idBar)
	{
        log.info ("Eliminando bar por id: " + idBar);
        long resp = pp.eliminarBarPorId (idBar);
        log.info ("Eliminando bar: " + resp);
        return resp;
	}
	
	/**
	 * Encuentra todos los bares en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bar con todos las bares que conoce la aplicación, llenos con su información básica
	 */
	public List<Bar> darBares ()
	{
        log.info ("Listando Bares");
        List<Bar> bares = pp.darBares ();	
        log.info ("Listando Bares: " + bares.size() + " bares existentes");
        return bares;
	}

	/**
	 * Encuentra todos los bares en Parranderos y los devuelce como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bar con todos las bares que conoce la aplicación, llenos con su información básica
	 */
	public List<VOBar> darVOBares ()
	{
		log.info ("Generando los VO de Bares");
		List<VOBar> voBares = new LinkedList<VOBar> ();
		for (Bar bar: pp.darBares ())
		{
			voBares.add (bar);
		}
		log.info ("Generando los VO de Bares: " + voBares.size () + " bares existentes");
		return voBares;
	}

	/**
	 * Aumenta en 1 el número de sedes de los bares de una ciudad
	 * Adiciona entradas al log de la aplicación
	 * @param ciudad - La ciudad en la cual se aumenta el número de sedes de los bares
	 * @return El número de tuplas actualizadas
	 */
	public long aumentarSedesBaresCiudad (String ciudad)
	{
        log.info ("Aumentando sedes de bares de una ciudad: " + ciudad);
        long resp = pp.aumentarSedesBaresCiudad (ciudad);
        log.info ("Aumentando sedes de bares de una ciudad: " + resp + " tuplas actualizadas");
        return resp;
	}
	
	/**
	 * Encuentra los bares que conoce la aplicación y el número de bebidas que sirve cada uno, 
	 * para aquellos bares que sirven por lo menos una bebida
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de parejas [IdBar, numBebidas]
	 */
	public List<long []> darBaresYCantidadBebidasSirven ()
	{
        log.info ("Listando Bares y cuántos bebidas sirven");
        List<long []> tuplas = pp.darBaresYCantidadBebidasSirven ();
        log.info ("Listando Bares y cuántos bebidas sirven: Listo!");
        return tuplas;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la relación GUSTAN
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente una preferencia de una bebida por un bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return Un objeto Gustan con los valores dados
	 */
	public Gustan adicionarGustan (long idBebedor, long idBebida)
	{
        log.info ("Adicionando gustan [" + idBebedor + ", " + idBebida + "]");
        Gustan resp = pp.adicionarGustan (idBebedor, idBebida);
        log.info ("Adicionando gustan: " + resp + " tuplas insertadas");
        return resp;
	}
	
	/**
	 * Elimina de manera persistente una preferencia de una bebida por un bebedor
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarGustan (long idBebedor, long idBebida)
	{
        log.info ("Eliminando gustan");
        long resp = pp.eliminarGustan (idBebedor, idBebida);
        log.info ("Eliminando gustan: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los gustan en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Gustan con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<Gustan> darGustan ()
	{
        log.info ("Listando Gustan");
        List<Gustan> gustan = pp.darGustan ();	
        log.info ("Listando Gustan: " + gustan.size() + " preferencias de gusto existentes");
        return gustan;
	}

	/**
	 * Encuentra todos los gustan en Parranderos y los devuelve como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Gustan con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<VOGustan> darVOGustan ()
	{
		log.info ("Generando los VO de Gustan");
		List<VOGustan> voGustan = new LinkedList<VOGustan> ();
		for (VOGustan bar: pp.darGustan ())
		{
			voGustan.add (bar);
		}
		log.info ("Generando los VO de Gustan: " + voGustan.size () + " Gustan existentes");
		return voGustan;
	}

	/* ****************************************************************
	 * 			Métodos para manejar la relación SIRVEN
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente el hecho que una bebida es servida por un bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar
	 * @param idBebida - El identificador de la bebida
	 * @param horario - El horario en el que se sirve la bebida (DIURNO, NOCTURNO, TODOS)
	 * @return Un objeto Sirven con los valores dados
	 */
	public Sirven adicionarSirven (long idBar, long idBebida, String horario)
	{
        log.info ("Adicionando sirven [" + idBar + ", " + idBebida + "]");
        Sirven resp = pp.adicionarSirven (idBar, idBebida, horario);
        log.info ("Adicionando sirven: " + resp + " tuplas insertadas");
        return resp;
	}
	
	/**
	 * Elimina de manera persistente el hecho que una bebida es servida por un bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBar - El identificador del bar
	 * @param idBebida - El identificador de la bebida
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarSirven (long idBar, long idBebida)
	{
        log.info ("Eliminando sirven");
        long resp = pp.eliminarSirven (idBar, idBebida);
        log.info ("Eliminando sirven: " + resp + "tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los SIRVEN en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos SIRVEN con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<Sirven> darSirven ()
	{
        log.info ("Listando Sirven");
        List<Sirven> sirven = pp.darSirven ();	
        log.info ("Listando Sirven: " + sirven.size() + " sirven existentes");
        return sirven;
	}

	/**
	 * Encuentra todos los sirven en Parranderos y los devuelve como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos SIRVEN con todos los SIRVEN que conoce la aplicación, llenos con su información básica
	 */
	public List<VOSirven> darVOSirven ()
	{
		log.info ("Generando los VO de Sirven");
		List<VOSirven> voGustan = new LinkedList<VOSirven> ();
		for (VOSirven sirven: pp.darSirven ())
		{
			voGustan.add (sirven);
		}
		log.info ("Generando los VO de Sirven: " + voGustan.size () + " Sirven existentes");
		return voGustan;
	}

	/* ****************************************************************
	 * 			Métodos para manejar la relación VISITAN
	 *****************************************************************/

	/**
	 * Adiciona de manera persistente el hecho que un bebedor visita un bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBar - El identificador del bar
	 * @param fecha - La fecha en la que se realizó la visita
	 * @param horario - El horario en el que se sirve la bebida (DIURNO, NOCTURNO, TODOS)
	 * @return Un objeto Visitan con los valores dados
	 */
	public Visitan adicionarVisitan (long idBebedor, long idBar, Timestamp fecha, String horario)
	{
        log.info ("Adicionando visitan [" + idBebedor + ", " + idBar + "]");
        Visitan resp = pp.adicionarVisitan (idBebedor, idBar, fecha, horario);
        log.info ("Adicionando visitan: " + resp + " tuplas insertadas");
        return resp;
	}
	
	/**
	 * Elimina de manera persistente el hecho que un bebedor visita un bar
	 * Adiciona entradas al log de la aplicación
	 * @param idBebedor - El identificador del bebedor
	 * @param idBar - El identificador del bar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarVisitan (long idBebedor, long idBar)
	{
        log.info ("Eliminando visitan");
        long resp = pp.eliminarVisitan (idBebedor, idBar);
        log.info ("Eliminando visitan: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los VISITAN en Parranderos
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VISITAN con todos los GUSTAN que conoce la aplicación, llenos con su información básica
	 */
	public List<Visitan> darVisitan ()
	{
        log.info ("Listando Visitan");
        List<Visitan> visitan = pp.darVisitan ();	
        log.info ("Listando Visitan: Listo!");
        return visitan;
	}

	/**
	 * Encuentra todos los visitan en Parranderos y los devuelve como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Visitan con todos los Visitan que conoce la aplicación, llenos con su información básica
	 */
	public List<VOVisitan> darVOVisitan ()
	{
		log.info ("Generando los VO de Visitan");
		List<VOVisitan> voGustan = new LinkedList<VOVisitan> ();
		for (VOVisitan vis: pp.darVisitan ())
		{
			voGustan.add (vis);
		}
		log.info ("Generando los VO de Visitan: " + voGustan.size () + " Visitan existentes");
		return voGustan;
	}

	/* ****************************************************************
	 * 			Métodos para administración
	 *****************************************************************/

	/**
	 * Elimina todas las tuplas de todas las tablas de la base de datos de Parranderos
	 * @return Un arreglo con 7 números que indican el número de tuplas borradas en las tablas GUSTAN, SIRVEN, VISITAN, BEBIDA,
	 * TIPOBEBIDA, BEBEDOR y BAR, respectivamente
	 */
	public long [] limpiarParranderos ()
	{
        log.info ("Limpiando la BD de Parranderos");
        long [] borrrados = pp.limpiarParranderos();	
        log.info ("Limpiando la BD de Parranderos: Listo!");
        return borrrados;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los SUPERMERCADOS
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente un supermercado
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del supermercado
	 * @return El objeto Supermercado adicionado. null si ocurre alguna Excepción
	 */
	public Supermercado adicionarSupermercado(String nombre)
	{
        log.info ("Adicionando Supermercado: " + nombre);
        Supermercado supermercado = pp.adicionarSupermercado(nombre);	
        log.info ("Adicionando Supermercado: " + supermercado);
        return supermercado;
	}
	
	/**
	 * Elimina un supermercado por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del supermercado a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarSupermercadoPorNombre (String nombre)
	{
		log.info ("Eliminando Supermercado por nombre: " + nombre);
        long resp = pp.eliminarSupermercadoPorNombre(nombre);		
        log.info ("Eliminando Supermercado por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los supermercados en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Supermercados con todos los supermercados que conoce la aplicación, llenos con su información básica
	 */
	public List<Supermercado> darSupermercados()
	{
		log.info ("Consultando Supermercados");
        List<Supermercado> supermercados = pp.darSupermercados();	
        log.info ("Consultando Supermercados: " + supermercados.size() + " existentes");
        return supermercados;
	}
	
	/**
	 * Encuentra todos los supermercados en SuperAndes y los devuelve como una lista de VOSupermercado
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOSupermercado con todos los supermercados que conoce la aplicación, llenos con su información básica
	 */
	public List<VOSupermercado> darVOSupermercado()
	{
		log.info ("Generando los VO de supermercados");        
        List<VOSupermercado> voSupermercados = new LinkedList<VOSupermercado> ();
        for (Supermercado sm : pp.darSupermercados())
        {
        	voSupermercados.add (sm);
        }
        log.info ("Generando los VO de Supermercados: " + voSupermercados.size() + " existentes");
        return voSupermercados;
	}

	/**
	 * Encuentra un supermercado y su información básica, según su nombre
	 * @param nombre - El nombre del supermercado buscado
	 * @return Un objeto Supermercado que corresponde con el nombre buscado y lleno con su información básica
	 * 			null, si un supermercado con dicho nombre no existe
	 */
	public Supermercado darSupermercadoPorNombre (String nombre)
	{
        log.info ("Dar información de un supermercado por nombre: " + nombre);
        Supermercado sm = pp.darSupermercadoPorNombre(nombre);
        log.info ("Buscando supermercado por nombre: " + sm != null ? sm : "NO EXISTE");
        return sm;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las SUCURSALES
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente una sucursal
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la sucursal
	 * @param ciudad - La ciudad de la sucursal
	 * @param direccion - La direccion de la sucursal
	 * @param segmentomercado - El segmento de mercado de la sucursal
	 * @param tamano - El tamaño de la sucursal (en metros cuadrados)
	 * @param supermercado - El supermercado al que pertenece la sucursal
	 * @return El objeto Sucursal adicionado. null si ocurre alguna Excepción
	 */
	public Sucursal adicionarSucursal(String nombre, String ciudad, String direccion, String segmentomercado, int tamano, String supermercado)
	{
        log.info ("Adicionando Sucursal: " + nombre);
        Sucursal sucursal = pp.adicionarSucursal(nombre, ciudad, direccion, segmentomercado, tamano, supermercado);
        log.info ("Adicionando Sucursal: " + sucursal);
        return sucursal;
	}
	
	/**
	 * Elimina una sucursal por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre de la sucursal a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarSucursalPorNombre (String nombre)
	{
		log.info ("Eliminando Sucursal por nombre: " + nombre);
        long resp = pp.eliminarSucursalPorNombre(nombre);	
        log.info ("Eliminando Sucursal por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina una sucursal por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idSucursal - El id de la sucursal a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarSucursalPorId (long idSucursal)
	{
		log.info ("Eliminando Sucursal por id: " + idSucursal);
        long resp = pp.eliminarSucursalPorId(idSucursal);		
        log.info ("Eliminando Sucursal por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra una sucursal y su información básica, según su identificador
	 * @param idSucursal - El identificador de la sucursal buscada
	 * @return Un objeto Sucursal que corresponde con el id buscado y lleno con su información básica
	 * 			null, si una sucursal con dicho id no existe
	 */
	public Sucursal darSucursalPorId(long idSucursal)
	{
        log.info ("Dar información de una sucursal por id: " + idSucursal);
        Sucursal sucursal = pp.darSucursalPorId(idSucursal);
        log.info ("Buscando sucursal por id: " + sucursal != null ? sucursal : "NO EXISTE");
        return sucursal;
	}
	
	/**
	 * Encuentra la información básica de las sucursales, según su supermercado
	 * @param supermercado - El supermercado al que pertence la sucursal
	 * @return Una lista de Sucursales con su información básica, donde todos tienen el supermercado buscado.
	 * 	La lista vacía indica que no existen sucursales con ese supermercado.
	 */
	public List<Sucursal> darSucursalesPorSupermercado (String supermercado)
	{
        log.info ("Dar información de sucursales por supermercado: " + supermercado);
        List<Sucursal> sucursales = pp.darSucursalesPorSupermercado(supermercado);
        log.info ("Dar información de Sucursales por supermercado: " + sucursales.size() + " sucursales con ese supermercado existentes");
        return sucursales;
 	}
	
	/**
	 * Encuentra todos las sucursales en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Sucurusal con todos las sucursales que conoce la aplicación, llenos con su información básica
	 */
	public List<Sucursal> darSucursales()
	{
		log.info ("Consultando Sucursales");
        List<Sucursal> sucursales = pp.darSucursales();	
        log.info ("Consultando Sucursales: " + sucursales.size() + " existentes");
        return sucursales;
	}
	
	/**
	 * Encuentra todas las sucursales en SuperAndes y los devuelve como una lista de VOSucursal
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOSucursal con todas las sucursales que conoce la aplicación, llenos con su información básica
	 */
	public List<VOSucursal> darVOSucursal()
	{
		log.info ("Generando los VO de sucursales");        
        List<VOSucursal> voSucursales = new LinkedList<VOSucursal> ();
        for (Sucursal sucursal : pp.darSucursales())
        {
        	voSucursales.add (sucursal);
        }
        log.info ("Generando los VO de Sucursales: " + voSucursales.size() + " existentes");
        return voSucursales;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los PRODUCTOS
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un producto
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del producto
	 * @param marca - La marca del producto
     * @param presentacion - La presentacion del producto
	 * @param codigobarras - El código de barras del producto
	 * @param unidadmedida - Las unidades de medida del producto
	 * @param categoria - Ls categoria del producto (perecederos, no perecederos, aseo, abarrotes, etc)
	 * @param tipo - El tipo del producto por categoria
	 * @return El objeto Producto adicionado. null si ocurre alguna Excepción
	 */
	public Producto adicionarProducto(String nombre, String marca, String presentacion, String codigobarras, String unidadmedida, String categoria, String tipo)
	{
        log.info ("Adicionando Producto: " + nombre);
        Producto producto = pp.adicionarProducto(nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo);
        log.info ("Adicionando Producto: " + producto);
        return producto;
	}
	
	/**
	 * Elimina un producto por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del producto a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarProductoPorNombre (String nombre)
	{
		log.info ("Eliminando Producto por nombre: " + nombre);
        long resp = pp.eliminarProductoPorNombre(nombre);	
        log.info ("Eliminando Producto por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina un producto por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idProducto - El id del producto a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarProductoPorId (long idProducto)
	{
		log.info ("Eliminando Producto por id: " + idProducto);
        long resp = pp.eliminarProductoPorId(idProducto);		
        log.info ("Eliminando Producto por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra la información básica de los productos, según su nombre
	 * @param nombre - El nombre del producto
	 * @return Una lista de Productos con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen productos con ese nombre.
	 */
	public List<Producto> darProductosPorNombre (String nombre)
	{
        log.info ("Dar información de productos por nombre: " + nombre);
        List<Producto> productos = pp.darProductosPorNombre(nombre);
        log.info ("Dar información de Productos por nombre: " + productos.size() + " productos con ese nombre existentes");
        return productos;
 	}
	
	/**
	 * Encuentra todos los productos en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Producto con todos los productos que conoce la aplicación, llenos con su información básica
	 */
	public List<Producto> darProductos()
	{
		log.info ("Consultando Productos");
        List<Producto> productos = pp.darProductos();	
        log.info ("Consultando Productos: " + productos.size() + " existentes");
        return productos;
	}
	
	/**
	 * Encuentra todos los productos en SuperAndes y los devuelve como una lista de VOProducto
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOProducto con todas los productos que conoce la aplicación, llenos con su información básica
	 */
	public List<VOProducto> darVOProducto()
	{
		log.info ("Generando los VO de prodcutos");        
        List<VOProducto> voProductos = new LinkedList<VOProducto> ();
        for (Producto producto : pp.darProductos())
        {
        	voProductos.add (producto);
        }
        log.info ("Generando los VO de Productos: " + voProductos.size() + " existentes");
        return voProductos;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las BODEGAS
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente una bodega
	 * Adiciona entradas al log de la aplicación
	 * @param idBodega - El identificador de la bodega
	 * @param capacidadVolumen - La capacidad en volumen de la bodega (en metros cúbicos)
     * @param capacidadPeso - La capacidad en peso de la bodega (en metros cuadrados)
	 * @param producto - El producto que está almacenado en la bodega
	 * @param sucursal - La sucursal a la que pertence la bodega
	 * @param existencias - Las unidades disponibles en la bodega
	 * @return El objeto Bodega adicionado. null si ocurre alguna Excepción
	 */
	public Bodega adicionarBodega(long idBodega, double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int existencias)
	{
        log.info ("Adicionando Bodega: " + idBodega);
        Bodega bodega = pp.adicionarBodega(capacidadVolumen, capacidadPeso, producto, sucursal, existencias);
        log.info ("Adicionando Bodega: " + bodega);
        return bodega;
	}
	
	/**
	 * Elimina una bodega por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBodega - El id de la bodega eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarBodegaPorId (long idBodega)
	{
		log.info ("Eliminando Bodega por id: " + idBodega);
        long resp = pp.eliminarBodegaPorId(idBodega);	
        log.info ("Eliminando Bodega por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra una bodega y su información básica, según su identificador
	 * @param idBodega - El identificador de la bodega buscada
	 * @return Un objeto Bodega que corresponde con el id buscado y lleno con su información básica
	 * 			null, si una bodega con dicho id no existe
	 */
	public Bodega darBodegaPorId(long idBodega)
	{
        log.info ("Dar información de una bodega por id: " + idBodega);
        Bodega bodega = pp.darBodegaPorId(idBodega);
        log.info ("Buscando bodega por id: " + bodega != null ? bodega : "NO EXISTE");
        return bodega;
	}
	
	/**
	 * Encuentra la información básica de las bodegas, según su sucursal
	 * @param sucursal - La sucursal a la que pertenece la bodega
	 * @return Una lista de Bodegas con su información básica, donde todos tienen la sucursal buscada.
	 * 	La lista vacía indica que no existen bodegas con esa sucursal.
	 */
	public List<Bodega> darBodegasPorSucursal(long sucursal)
	{
        log.info ("Dar información de bodegas por sucursal: " + sucursal);
        List<Bodega> bodegas = pp.darBodegasPorSucursal(sucursal);
        log.info ("Dar información de Bodegas por sucursal: " + bodegas.size() + " bodegas con esa sucursal existentes");
        return bodegas;
 	}
	
	/**
	 * Encuentra todos las bodegas en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Bodega con todos los productos que conoce la aplicación, llenos con su información básica
	 */
	public List<Bodega> darBodegas()
	{
		log.info ("Consultando Bodegas");
        List<Bodega> bodegas = pp.darBodegas();	
        log.info ("Consultando Bodegas: " + bodegas.size() + " existentes");
        return bodegas;
	}
	
	/**
	 * Encuentra todos las bodegas en SuperAndes y los devuelve como una lista de VOBodega
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOBodega con todas las bodegas que conoce la aplicación, llenos con su información básica
	 */
	public List<VOBodega> darVOBodega()
	{
		log.info ("Generando los VO de bodegas");        
        List<VOBodega> voBodegas = new LinkedList<VOBodega> ();
        for (Bodega bodega : pp.darBodegas())
        {
        	voBodegas.add (bodega);
        }
        log.info ("Generando los VO de Bodegas: " + voBodegas.size() + " existentes");
        return voBodegas;
	}
	
	/**
	 * Aumenta las existencias en 10 unidades de una bodega con id dado
	 * @return Las tuplas modificadas con el aumento de existencias
	 */
	public long aumentarExistenciasBodegaEnDiez(long idBodega)
	{
		log.info("Aumentando eixstencias de la bodega en diez");
		long aumento = pp.aumentarExistenciasBodegaEnDiez(idBodega);
		log.info("Bodega con id: " + idBodega + "aumentada en 10 sus existencias");
		return aumento;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los ESTANTES
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un estante
	 * Adiciona entradas al log de la aplicación
	 * @param idEstante - El identificador del estante
	 * @param capacidadVolumen - La capacidad en volumen del estante(metros cúbicos)
	 * @param capacidadPeso - La capacidad en peso del estante (en kg)
	 * @param producto - Identificador del producto que almacena el estante
	 * @param sucursal - La sucursal a la que pertenece el estante
	 * @nivelabastecimientobodega - Cantidad de unidades mínimas que debe tener en la bidega por producto
	 * @param existencias - Las existencias disponibles en la bodega
	 * @return El objeto Estante adicionado. null si ocurre alguna Excepción
	 */
	public 	Estante adicionarEstante(long idEstante, double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int nivelabastecimientobodega, int existencias)
	{
        log.info ("Adicionando Bodega: " + idEstante);
        Estante estante = pp.adicionarEstante(capacidadVolumen, capacidadPeso, producto, sucursal, nivelabastecimientobodega, existencias);
        log.info ("Adicionando Bodega: " + estante);
        return estante;
	}
	
	/**
	 * Elimina un estante por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idBodega - El id del estante a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarEstantePorId (long idEstante)
	{
		log.info ("Eliminando Estante por id: " + idEstante);
        long resp = pp.eliminarEstantePorId(idEstante);
        log.info ("Eliminando Estante por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra un estante y su información básica, según su identificador
	 * @param idEstante - El identificador del estante buscado
	 * @return Un objeto Estante que corresponde con el id buscado y lleno con su información básica
	 * 			null, si un estante con dicho id no existe
	 */
	public Estante darEstantePorId(long idEstante)
	{
        log.info ("Dar información de un estante por id: " + idEstante);
        Estante estante = pp.darEstantePorId(idEstante);
        log.info ("Buscando estante por id: " + estante != null ? estante : "NO EXISTE");
        return estante;
	}
	
	/**
	 * Encuentra la información básica de los estantes, según su sucursal
	 * @param sucursal - La sucursal a la que pertenece el estante
	 * @return Una lista de Estantess con su información básica, donde todos tienen la sucursal buscada.
	 * 	La lista vacía indica que no existen estantes con esa sucursal.
	 */
	public List<Estante> darEstantesPorSucursal(long sucursal)
	{
        log.info ("Dar información de estantes por sucursal: " + sucursal);
        List<Estante> estantes = pp.darEstantesPorSucursal(sucursal);
        log.info ("Dar información de Estantes por sucursal: " + estantes.size() + " estantes con esa sucursal existentes");
        return estantes;
 	}
	
	/**
	 * Encuentra todos los estantes en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Estante con todos los productos que conoce la aplicación, llenos con su información básica
	 */
	public List<Estante> darEstantes()
	{
		log.info ("Consultando Estantes");
        List<Estante> estantes = pp.darEstantes();	
        log.info ("Consultando Estantes: " + estantes.size() + " existentes");
        return estantes;
	}
	
	/**
	 * Encuentra todos los estantes en SuperAndes y los devuelve como una lista de VOEstante
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOEstante con todas los estantes que conoce la aplicación, llenos con su información básica
	 */
	public List<VOEstante> darVOEsatnte()
	{
		log.info ("Generando los VO de estantes");        
        List<VOEstante> voEstantes = new LinkedList<VOEstante>();
        for (Estante estante : pp.darEstantes())
        {
        	voEstantes.add (estante);
        }
        log.info ("Generando los VO de Estantes: " + voEstantes.size() + " existentes");
        return voEstantes;
	}
	
	/**
	 * Aumenta las existencias en 10 unidades de un estante con id dado
	 * @return Las tuplas modificadas con el aumento de existencias
	 */
	public long aumentarExistenciasEstanteEnDiez(long idEstante)
	{
		log.info("Aumentando eixstencias de la bodega en diez");
		long aumento = pp.aumentarExistenciasEstanteEnDiez(idEstante);
		log.info("Estante con id: " + idEstante + "aumentada en 10 sus existencias");
		return aumento;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar la relación VENDE
	 *****************************************************************/
	
	/* ****************************************************************
	 * 			Métodos para manejar los PROVEEDORES
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un proveedor
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del proveedor
	 * @param calificacion - La calificacion del proveeodr
	 * @return El objeto Proveedor adicionado. null si ocurre alguna Excepción
	 */
	public Proveedor adicionarProveedor(String nombre, int calificacion)
	{
        log.info ("Adicionando Proveedor: " + nombre);
        Proveedor proveedor = pp.adicionarProveedor(nombre, calificacion);
        log.info ("Adicionando Bodega: " + proveedor);
        return proveedor;
	}
	
	/**
	 * Elimina un proveedor por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del proveedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarProveedorPorNombre (String nombre)
	{
		log.info ("Eliminando Proveedor por nombre: " + nombre);
        long resp = pp.eliminarProoveedorPorNombre(nombre);	
        log.info ("Eliminando Proveedor por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina un proveedor por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idProveedor - El id del proveedor a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarProveedorPorId (long idProveedor)
	{
		log.info ("Eliminando Proveedor por id: " + idProveedor);
        long resp = pp.eliminarProveedorPorId(idProveedor);	
        log.info ("Eliminando Proveedor por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra la información básica de los proveedores, según su nombre
	 * @param nombre - El nombre del proveedor
	 * @return Una lista de Proveedores con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen proveedores con ese nombre.
	 */
	public List<Proveedor> darProveedoresPorNombre (String nombre)
	{
        log.info ("Dar información de proveedores por nombre: " + nombre);
        List<Proveedor> proveedores = pp.darProveedoresPorNombre(nombre);
        log.info ("Dar información de Proveedores por nombre: " + proveedores.size() + " proveedores con ese nombre existentes");
        return proveedores;
 	}
	
	/**
	 * Encuentra todos los proveedores en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Proveedor con todos los productos que conoce la aplicación, llenos con su información básica
	 */
	public List<Proveedor> darProveedores()
	{
		log.info ("Consultando Proveedores");
        List<Proveedor> proveedores = pp.darProveedores();	
        log.info ("Consultando Proveedores: " + proveedores.size() + " existentes");
        return proveedores;
	}
	
	/**
	 * Encuentra todos los proveedores en SuperAndes y los devuelve como una lista de VOProveedor
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOProveedor con todas los proveedores que conoce la aplicación, llenos con su información básica
	 */
	public List<VOProveedor> darVOProveedor()
	{
		log.info ("Generando los VO de proveedores");        
        List<VOProveedor> voProveedores = new LinkedList<VOProveedor> ();
        for (Proveedor proveedor : pp.darProveedores())
        {
        	voProveedores.add (proveedor);
        }
        log.info ("Generando los VO de Proveedores: " + voProveedores.size() + " existentes");
        return voProveedores;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar los PEDIDOS
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un pedido
	 * Adiciona entradas al log de la aplicación 
	 * @param proveedor - El proveedor del pedido
	 * @param sucursal - La sucursal que hace el pedido
	 * @param fechaEntrega - La fecha de entrega del pedido
	 * @param estadoOrden - El estado de orden del pedido
	 * @param cantidad - El numero de unidades solicitadas
	 * @param calificacion - La calificacion del pedido
	 * @param costoTotal - El costo total del pedido
	 * @return El objeto Pedido adicionado. null si ocurre alguna Excepción
	 */
	public Pedido adicionarPedido(long proveedor, long sucursal, Timestamp fechaEntrega, String estadoOrden, int cantidad, int calificacion, long producto)
	{
        log.info ("Adicionando pedido de producto con id: " + producto);
        Pedido pedido = pp.adicionarPedido(proveedor, sucursal, fechaEntrega, estadoOrden, cantidad, calificacion, producto);
        log.info ("Adicionando pedido: " + pedido);
        return pedido;
	}
	
	/**
	 * Elimina un pedido por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idPedido - El id del pedido a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarPedidoPorId (long idPedido)
	{
		log.info ("Eliminando Pedido por id: " + idPedido);
        long resp = pp.eliminarPedidoPorId(idPedido);
        log.info ("Eliminando Pedido por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra un pedido y su información básica, según su identificador
	 * @param idPedido - El identificador del pedido buscado
	 * @return Un objeto Pedido que corresponde con el id buscado y lleno con su información básica
	 * 			null, si un pedido con dicho id no existe
	 */
	public Pedido darPedidoPorId(long idPedido)
	{
        log.info ("Dar información de un pedido por id: " + idPedido);
        Pedido pedido = pp.darPedidoPorId(idPedido);
        log.info ("Buscando pedido por id: " + pedido != null ? pedido : "NO EXISTE");
        return pedido;
	}
	
	/**
	 * Encuentra la información básica de los pedidos, según su id
	 * @param idPedido - El identificador del pedido
	 * @return Una lista de Pedidos con su información básica, donde todos tienen el id buscado.
	 * 	La lista vacía indica que no existen pedidos con ese id.
	 */
	public List<Pedido> darPedidosPorId(long idPedido)
	{
        log.info ("Dar información de pedidos por id: " + idPedido);
        List<Pedido> pedidos = pp.darPedidosPorId(idPedido);
        log.info ("Dar información de Pedidos por id: " + pedidos.size() + " pedidos con ese id existentes");
        return pedidos;
 	}
	
	/**
	 * Encuentra la información básica de los pedidos, según su sucursal
	 * @param idSucursal - El identificador de la sucursal desde donde se realia el pedido
	 * @return Una lista de Pedidos con su información básica, donde todos tienen la sucursal buscada.
	 * 	La lista vacía indica que no existen pedidos con esa sucursal.
	 */
	public List<Pedido> darPedidosPorSucursal(long idSucursal)
	{
        log.info ("Dar información de pedidos por sucursal: " + idSucursal);
        List<Pedido> pedidos = pp.darPedidosPorSucursal(idSucursal);
        log.info ("Dar información de Pedidos por sucursal: " + pedidos.size() + " pedidos con esa sucursal existentes");
        return pedidos;
 	}
	
	/**
	 * Encuentra la información básica de los pedidos, según su proveedor
	 * @param idProveedor - El identificador del proveedor que realiza el pedidos
	 * @return Una lista de Pedidos con su información básica, donde todos tienen el proveedor buscado.
	 * 	La lista vacía indica que no existen pedidos con ese proveedor.
	 */
	public List<Pedido> darPedidosPorProveedor(long idProveedor)
	{
        log.info ("Dar información de pedidos por proveedor: " + idProveedor);
        List<Pedido> pedidos = pp.darPedidosPorProveedor(idProveedor);
        log.info ("Dar información de Pedidos por proveedor: " + pedidos.size() + " pedidos con ese proveedor existentes");
        return pedidos;
 	}
	
	/**
	 * Encuentra la información básica de los pedidos, según su proveedor y su sucursal
	 * @param idProveedor - El identificador del proveedor que realiza el pedidos
	 * @param idSucursal - El identificador de la sucursal desde donde se realia el pedido
	 * @return Una lista de Pedidos con su información básica, donde todos tienen el proveedor y sucursal buscada.
	 * 	La lista vacía indica que no existen pedidos con ese proveedor y sucursal.
	 */
	public List<Pedido> darPedidosPorProveedorYSucursal(long idProveedor, long idSucursal)
	{
        log.info ("Dar información de pedidos por proveedor y sucursal: " + idProveedor + "," + idSucursal);
        List<Pedido> pedidos = pp.darPedidosPorProveedorYSucursal(idProveedor, idSucursal);
        log.info ("Dar información de Pedidos por proveedor y sucursal: " + pedidos.size() + " pedidos con ese proveedor y esa sucursal existentes");
        return pedidos;
 	}
	
	public long cambiarEstadoOrdenPedido(long idPedido)
	{
		log.info("Cambiando estado de orden del pedido:" + idPedido);
		long estado = pp.cambiarEstadoOrdenPedido(idPedido);
		log.info("Estado de orden del pedido:" + idPedido + "actualizado");
		return estado;
	}
	
	/**
	 * Encuentra la información básica de los pedidos, según su calificacion
	 * @param calificacion - La calificacion del pedido
	 * @return Una lista de Pedidos con su información básica, donde todos tienen la calificacion buscada.
	 * 	La lista vacía indica que no existen pedidos con esa calificacion
	 */
	public List<Pedido> darPedidosPorCalificacion(int calificacion)
	{
        log.info ("Dar información de pedidos por calificacion: " + calificacion);
        List<Pedido> pedidos = pp.darPedidosPorCalificacion(calificacion);
        log.info ("Dar información de Pedidos por calificacion: " + pedidos.size() + " pedidos con esa calificacion existentes");
        return pedidos;
 	}
	
	public long cambiarCalificacionPedido(long idPedido, int calificacion)
	{
		log.info("Cambiando califiacion del pedido:" + idPedido + "a una calificacion:" + calificacion);
		long estado = pp.cambiarCalificacionPedido(idPedido, calificacion);
		log.info("Calificsacion del pedido:" + idPedido + "actualizada");
		return estado;
	}
	
	/**
	 * Encuentra la información básica de los pedidos, según su fecha de entrega
	 * @param fechaEntrega - La fecha de entrega del pedido
	 * @return Una lista de Pedidos con su información básica, donde todos tienen la fecha de entrega buscada.
	 * 	La lista vacía indica que no existen pedidos con esa fecha de entrega
	 */
	public List<Pedido> darPedidosPorFechaEntrega(Timestamp fechaEntrega)
	{
        log.info ("Dar información de pedidos por fecha de entrega: " + fechaEntrega);
        List<Pedido> pedidos = pp.darPedidosPorFechaEntrega(fechaEntrega);
        log.info ("Dar información de Pedidos por fecha de entrega: " + pedidos.size() + " pedidos con esa fecha de entrega existentes");
        return pedidos;
 	}
	
	/**
	 * Elimina un pedido terminado (estado de orden: entregado)
	 * Adiciona entradas al log de la aplicación
	 * @param estadoOrden - El estado de orden del pedido
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarPedidosTerminados()
	{
		String estadoOrden = "Entregado";
		log.info ("Eliminando Pedidos terminados con estado de orden: " + estadoOrden);
        long resp = pp.eliminarPedidosTerminados(estadoOrden);
        log.info ("Eliminando Pedidos terminados: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los pedidos en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Pedido con todos los pedidos que conoce la aplicación, llenos con su información básica
	 */
	public List<Pedido> darPedidos()
	{
		log.info ("Consultando Pedidos");
        List<Pedido> pedidos = pp.darPedidos();	
        log.info ("Consultando Pedidos: " + pedidos.size() + " existentes");
        return pedidos;
	}
	
	/**
	 * Encuentra todos los pedidos en SuperAndes y los devuelve como una lista de VOPedido
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOPedido con todas los pedidos que conoce la aplicación, llenos con su información básica
	 */
	public List<VOPedido> darVOPedido()
	{
		log.info ("Generando los VO de pedidos");        
        List<VOPedido> voPedidos = new LinkedList<VOPedido> ();
        for (Pedido pedido : pp.darPedidos())
        {
        	voPedidos.add (pedido);
        }
        log.info ("Generando los VO de Pedidos: " + voPedidos.size() + " existentes");
        return voPedidos;
	}
	
	/* ****************************************************************
	 * 			M�todos para manejar los SUBPEDIDOS
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un subpedido
	 * Adiciona entradas al log de la aplicación 
	 * @param producto - El producto del subpedido
	 * @param cantidad - La cantidad de unidades pedidas por producto
	 * @param costo - El costo del subpedido
	 * @return El objeto Subpedido adicionado. null si ocurre alguna Excepción
	 */
	public Subpedido adicionarSubPedido(long idPedido, long producto, int cantidadSub, double costo)
	{
        log.info ("Adicionando pedido: " + idPedido);
        Subpedido subpedido = pp.adicionarSubPedido(producto, cantidadSub, costo);
        log.info ("Adicionando pedido: " + subpedido);
        return subpedido;
	}
	
	/**
	 * Elimina un subpedido por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idPedido - El id del pedido a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarSubPedidoPorId (long idPedido)
	{
		log.info ("Eliminando SubPedido por id: " + idPedido);
        long resp = pp.eliminarSubPedidoPorId(idPedido);
        log.info ("Eliminando SubPedido por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra un subpedido y su información básica, según su identificador
	 * @param idEstante - El identificador del estante buscado
	 * @return Un objeto Estante que corresponde con el id buscado y lleno con su información básica
	 * 			null, si un subpedido con dicho id no existe
	 */
	public Subpedido darSubPedidoPorId(long idPedido)
	{
        log.info ("Dar información de un subpedido por id: " + idPedido);
        Subpedido subpedido = pp.darSubPedidoPorId(idPedido);
        log.info ("Buscando subpedido por id: " + subpedido != null ? subpedido : "NO EXISTE");
        return subpedido;
	}
	
	/**
	 * Encuentra la información básica de los subpedidos, según su producto
	 * @param idProducto - El identificador del producto pedido
	 * @return Una lista de SubPedidos con su información básica, donde todos tienen el producto buscado.
	 * 	La lista vacía indica que no existen subpedidos con ese producto.
	 */
	public List<Subpedido> darSubPedidosPorProducto(long idProducto)
	{
        log.info ("Dar información de subpedidos por producto: " + idProducto);
        List<Subpedido> subpedidos = pp.darSubPedidosPorProducto(idProducto);
        log.info ("Dar información de SubPedidos por producto: " + subpedidos.size() + " pedidos con ese producto existentes");
        return subpedidos;
 	}
	
	/**
	 * Encuentra todos los subpedidos en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Subpedido con todos los subpedidos que conoce la aplicación, llenos con su información básica
	 */
	public List<Subpedido> darSubPedidos()
	{
		log.info ("Consultando SubPedidos");
        List<Subpedido> subpedidos = pp.darSubPedidos();	
        log.info ("Consultando SubPedidos: " + subpedidos.size() + " existentes");
        return subpedidos;
	}
	
	/**
	 * Encuentra todos los subpedidos en SuperAndes y los devuelve como una lista de VOSubpedido
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOSubpedido con todas los subpedidos que conoce la aplicación, llenos con su información básica
	 */
	public List<VOSubpedido> darVOSubPedido()
	{
		log.info ("Generando los VO de subpedidos");        
        List<VOSubpedido> voSubPedidos = new LinkedList<VOSubpedido> ();
        for (Subpedido subpedido : pp.darSubPedidos())
        {
        	voSubPedidos.add (subpedido);
        }
        log.info ("Generando los VO de SubPedidos: " + voSubPedidos.size() + " existentes");
        return voSubPedidos;
	}
	
	/* ****************************************************************
	 * 			M�todos para manejar la relación OFRECEN
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente el hecho de que un producto es ofrecido por un proveedor
	 * Adiciona entradas al log de la aplicación 
	 * @param idProducto - El identificador del producto
	 * @param idProveedor - El identificador del proveedor
	 * @param costo - el costo del producto según el proveedor
	 * @return El objeto Ofrecen adicionado. null si ocurre alguna Excepción
	 */
	public Ofrecen adicionarOfrecen(long idProducto, long idProveedor, double costo)
	{
        log.info ("Adicionando ofrecen: [" + idProducto + ", " + idProveedor + "]");
        Ofrecen ofrecen = pp.adicionarOfrecen(idProducto, idProveedor, costo);
        log.info ("Adicionando ofrecen: " + ofrecen);
        return ofrecen;
	}
	
	/**
	 * Elimina de manera persistente el hecho de que un producto es ofrecido por un proveedor
	 * Adiciona entradas al log de la aplicación
	 * @param idProducto - El identificador del producto
	 * @param idProveedor - El identificador del proveedor
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarOfrecen(long idProducto, long idProveedor)
	{
		log.info ("Eliminando ofrecen");
        long resp = pp.eliminarOfrecen(idProducto, idProveedor);
        log.info ("Eliminando ofrecen: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra todos los OFRECEN en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Ofrecen con todos los ofrecen que conoce la aplicación, llenos con su información básica
	 */
	public List<Ofrecen> darOfrecen()
	{
		log.info ("Listando Ofrecen");
        List<Ofrecen> ofrecen = pp.darOfrecen();
        log.info ("Listando ofrecen: Listo!");
        return ofrecen;
	}
	
	/**
	 * Encuentra todos los proveedores y la cantidad de productos que ofrecen en Superandes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos con todos los proveedores y la cantidad de productos que ofrecen y que conoce la aplicación, llenos con su información básica
	 */
	public List<Object []> darProveedorYCantidadProductosOfrecen()
	{
		log.info ("Listando los proveedores y la cantidad de productos que ofrecen");
        List<Object []> pYp = pp.darProveedorYCantidadProductosOfrecen();
        log.info ("Listando proveedores y la cantidad de productos que ofrecen: Listo!");
        return pYp;
	}
	
	/**
	 * Encuentra todos los ofrecen en SuperAndes y los devuelve como VO
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOOfrecen con todas los Ofrecen que conoce la aplicación, llenos con su información básica
	 */
	public List<VOOfrecen> darVOOfrecen()
	{
		log.info ("Generando los VO de ofrecen");        
        List<VOOfrecen> voOfrecen = new LinkedList<VOOfrecen> ();
        for (Ofrecen ofrecen : pp.darOfrecen())
        {
        	voOfrecen.add (ofrecen);
        }
        log.info ("Generando los VO de Ofrecen: " + voOfrecen.size() + " existentes");
        return voOfrecen;
	}
	
	/* ****************************************************************
	 * 			M�todos para manejar los CLIENTES
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un cliente
	 * Adiciona entradas al log de la aplicación 
	 * @param nombre - El nombre del cliente
	 * @param correo - El correo del cliente
	 * @param tipo - El tipo de cliente(PERSONA, EMPRESA)
	 * @param direccion - La direccion del cliente
	 * @param unidadesdisponibles de la promocion
	 * @return El objeto Cliente adicionado. null si ocurre alguna Excepción
	 */
	public Cliente adicionarCliente(String nombre, String correo, String tipo, String direccion)
	{
        log.info ("Adicionando producto: " + nombre);
        Cliente cliente = pp.adicionarCliente(nombre, correo, tipo, direccion);
        log.info ("Adicionando cliente: " + cliente);
        return cliente;
	}
	
	/**
	 * Elimina un cliente por su identificador
	 * Adiciona entradas al log de la aplicación
	 * @param idCliente - El id del cliente a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarClientePorId (long idCliente)
	{
		log.info ("Eliminando Cliente por id: " + idCliente);
        long resp = pp.eliminarClientePorId(idCliente);
        log.info ("Eliminando Cliente por id: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Elimina un cliente por su nombre
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del cliente que se va a eliminar
	 * @return El número de tuplas eliminadas
	 */
	public long eliminarClientePorNombre (String nombre)
	{
		log.info ("Eliminando Cliente por nombre: " + nombre);
        long resp = pp.eliminarClientePorNombre(nombre);
        log.info ("Eliminando Cliente por nombre: " + resp + " tuplas eliminadas");
        return resp;
	}
	
	/**
	 * Encuentra un cliente y su información básica, según su identificador
	 * @param idCliente - El identificador del cliente buscado
	 * @return Un objeto Cliente que corresponde con el id buscado y lleno con su información básica
	 * 			null, si un cliente con dicho id no existe
	 */
	public Cliente darClientePorId(long idCliente)
	{
        log.info ("Dar información de un subpedido por id: " + idCliente);
        Cliente cliente = pp.darClientePorId(idCliente);
        log.info ("Buscando cliente por id: " + cliente != null ? cliente : "NO EXISTE");
        return cliente;
	}
	
	/**
	 * Encuentra la información básica de los clientes, según su nombre
	 * @param nombre - El nombre del cliente
	 * @return Una lista de Clientes con su información básica, donde todos tienen el nombre buscado.
	 * 	La lista vacía indica que no existen clientes con ese nombre.
	 */
	public List<Cliente> darClientesPorNombre(String nombre)
	{
        log.info ("Dar información de clientes por nombre: " + nombre);
        List<Cliente> clientes = pp.darClientesPorNombre(nombre);
        log.info ("Dar información de clientes por nombre: " + clientes.size() + " clientes con ese nombre existentes");
        return clientes;
 	}
	
	/**
	 * Encuentra la información básica de los clientes, según su tipo
	 * @param tipo - El tipo del cliente
	 * @return Una lista de Clientes con su información básica, donde todos tienen el tipo buscado.
	 * 	La lista vacía indica que no existen clientes con ese tipo.
	 */
	public List<Cliente> darClientesPorTipo(String tipo)
	{
        log.info ("Dar información de clientes por tipo: " + tipo);
        List<Cliente> clientes = pp.darClientesPorTipo(tipo);
        log.info ("Dar información de clientes por tipo: " + clientes.size() + " clientes con ese tipo existentes");
        return clientes;
 	}
	
	/**
	 * Encuentra todos los clientes en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Cliente con todos los clientes que conoce la aplicación, llenos con su información básica
	 */
	public List<Cliente> darClientes()
	{
		log.info ("Consultando Clientes");
        List<Cliente> clientes = pp.darClientes();	
        log.info ("Consultando Clientes: " + clientes.size() + " existentes");
        return clientes;
	}
	
	/**
	 * Encuentra todos los clientes en SuperAndes y los devuelve como una lista de VOCliente
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOCliente con todas los clientes que conoce la aplicación, llenos con su información básica
	 */
	public List<VOCliente> darVOCliente()
	{
		log.info ("Generando los VO de pedidos");        
        List<VOCliente> voClientes = new LinkedList<VOCliente> ();
        for (Cliente cliente : pp.darClientes())
        {
        	voClientes.add (cliente);
        }
        log.info ("Generando los VO de Clientes: " + voClientes.size() + " existentes");
        return voClientes;
	}
	
	/* ****************************************************************
	 * 			Métodos para manejar las FACTURAS
	 *****************************************************************/
	
	/**
	 * Adiciona de manera persistente un cliente
	 * Adiciona entradas al log de la aplicación 
	 * @param idFactura - El identificador de la factura
	 * @param fecha - Fecha de la factura
	 * @param idCliente - El identificador del cliente de la factura
	 * @param idSucursal - El identificador de la sucursal donde se generó la factura
	 * @return El objeto Factura adicionado. null si ocurre alguna Excepción
	 */
	public Factura adicionarFactura(long idFactura, Timestamp fecha, String idCliente, long idSucursal)
	{
        log.info ("Adicionando producto: " + idFactura);
        Factura factura = pp.adicionarFactura(fecha, idCliente, idSucursal);
        log.info ("Adicionando cliente: " + factura);
        return factura;
	}
	
	/**
	 * Encuentra una factura y su información básica, según su identificador
	 * @param idFactura - El identificador de la factura buscada
	 * @return Un objeto Factura que corresponde con el id buscado y lleno con su información básica
	 * 			null, si uturana fac con dicho id no existe
	 */
	public Factura darFacturaPorId(long idFactura)
	{
        log.info ("Dar información de una factura por id: " + idFactura);
        Factura factura = pp.darFacturaPorId(idFactura);
        log.info ("Buscando factura por id: " + factura != null ? factura : "NO EXISTE");
        return factura;
	}
	
	/**
	 * Encuentra la información básica de las facturas, según su id
	 * @param idFactura - El identificador de la factura
	 * @return Una lista de Facturas con su información básica, donde todos tienen el id buscado.
	 * 	La lista vacía indica que no existen facturas con ese id.
	 */
	public List<Factura> darFacturasPorId(long idFactura)
	{
        log.info ("Dar información de facturas por id: " + idFactura);
        List<Factura> facturas = pp.darFacturasPorId(idFactura);
        log.info ("Dar información de facturas por id: " + facturas.size() + " facturas con ese id existentes");
        return facturas;
 	}
	
	/**
	 * Encuentra la información básica de las facturas, según su cliente
	 * @param idCliente- El identificador del cliente de la factura
	 * @return Una lista de Facturas con su información básica, donde todos tienen el cliente buscado.
	 * 	La lista vacía indica que no existen facturas con ese cliente.
	 */
	public List<Factura> darFacturasPorCliente(long idCliente)
	{
        log.info ("Dar información de facturas por cliente: " + idCliente);
        List<Factura> facturas = pp.darFacturasPorCliente(idCliente);
        log.info ("Dar información de facturas por cliente: " + facturas.size() + " facturas con ese id existentes");
        return facturas;
 	}
	
	/**
	 * Encuentra la información básica de las facturas, según su sucursal
	 * @param idSucursal- El identificador de la sucursal de la factura
	 * @return Una lista de Facturas con su información básica, donde todos tienen la sucursal buscada.
	 * 	La lista vacía indica que no existen facturas con esa sucursal.
	 */
	public List<Factura> darFacturasPorSucursal(long idSucursal)
	{
        log.info ("Dar información de facturas por sucursal: " + idSucursal);
        List<Factura> facturas = pp.darFacturasPorId(idSucursal);
        log.info ("Dar información de facturas por sucursal: " + facturas.size() + " facturas con esa sucursal existentes");
        return facturas;
 	}
	
	/**
	 * Encuentra la información básica de las facturas, según su fecha
	 * @param fecha- La fecha de la factura
	 * @return Una lista de Facturas con su información básica, donde todos tienen la fecha buscada.
	 * 	La lista vacía indica que no existen facturas con esa fecha.
	 */
	public List<Factura> darFacturasPorFecha(Timestamp fecha)
	{
        log.info ("Dar información de facturas por fecha: " + fecha);
        List<Factura> facturas = pp.darFacturasPorFecha(fecha);
        log.info ("Dar información de facturas por fecha: " + facturas.size() + " facturas con esa fecha existentes");
        return facturas;
 	}
	
	/**
	 * Encuentra la información básica de las facturas, según su cliente y su sucursal
	 * @param idSucursal- El identificador de la sucursal de la factura
	 * @param idCliente - El identificador del cliente de la factura
	 * @return Una lista de Facturas con su información básica, donde todos tienen la sucursal buscada.
	 * 	La lista vacía indica que no existen facturas con ese cliente y esa sucursal.
	 */
	public List<Factura> darFacturasPorClienteYSucursal(long idCliente, long idSucursal)
	{
        log.info ("Dar información de facturas por cliente y sucursal: " + idCliente + "," + idSucursal);
        List<Factura> facturas = pp.darFacturasPorClienteYSucursal(idCliente, idSucursal);
        log.info ("Dar información de facturas por cliente y sucursal: " + facturas.size() + " facturas con ese cliente y sucursal existentes");
        return facturas;
 	}
	
	/**
	 * Encuentra todos las facturas en SuperAndes
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos Factura con todas las facturas que conoce la aplicación, llenos con su información básica
	 */
	public List<Factura> darFacturas()
	{
		log.info ("Consultando Facturas");
        List<Factura> facturas = pp.darFacturas();	
        log.info ("Consultando Facturas: " + facturas.size() + " existentes");
        return facturas;
	}
	
	/**
	 * Encuentra todos los facturas en SuperAndes y los devuelve como una lista de VOFactura
	 * Adiciona entradas al log de la aplicación
	 * @return Una lista de objetos VOFactura con todas las facturas que conoce la aplicación, llenos con su información básica
	 */
	public List<VOFactura> darVOFactura()
	{
		log.info ("Generando los VO de facturas");        
        List<VOFactura> voFacturas = new LinkedList<VOFactura> ();
        for (Factura factura : pp.darFacturas())
        {
        	voFacturas.add (factura);
        }
        log.info ("Generando los VO de Facturas: " + voFacturas.size() + " existentes");
        return voFacturas;
	}
	
	
	
	
	/* ****************************************************************
	 * 			Métodos para manejar las PROMOCIONES
	 *****************************************************************/
	/**
	 * Adiciona de manera persistente una promocion
	 * Adiciona entradas al log de la aplicación
	 * @param nombre - El nombre del proveedor
	 * @param calificacion - La calificacion del proveeodr
	 * @return El objeto Proveedor adicionado. null si ocurre alguna Excepción
	 */
	
	/**
	 * Adiciona de manera persistente una promocion
	 * Adiciona entradas al log de la aplicación 
	 * @param nombre del producto asociado a la promocion
	 * @param marca del producto asociado a la promocion
	 * @param presentacion del producto asociado a la promocion
	 * @param codigobarras del producto asociado a la promocion
	 * @param unidadmedida del producto asociado a la promocion
	 * @param categoria del producto asociado a la promocion
	 * @param tipo del producto asociado a la promocion
	 * @param precio de la promocion	
	 * @param descripcion de la promocion
	 * @param fechaInicio de la promocion
	 * @param fechaFin de la promocion 
	 * @param unidadesdisponibles de la promocion
	 * @return El objeto Promocion adicionado. null si ocurre alguna Excepción
	 */
	public Promocion adicionarPromocion(String nombre, String marca, String presentacion, String codigobarras, String unidadmedida, String categoria, String tipo, double precio, String descripcion, Timestamp fechaInicio, Timestamp fechaFin, int unidadesdisponibles)
	{
        log.info ("Adicionando producto: " + nombre);
        Promocion promocion = pp.adicionarPromocion(nombre, marca, presentacion, codigobarras, unidadmedida, categoria, tipo, precio, descripcion, fechaInicio, fechaFin, unidadesdisponibles);
        log.info ("Adicionando promocion: " + promocion);
        return promocion;
	}
	
	/**
	 * Adiciona de manera persistente un proceso de venta
	 * Adiciona entradas al log de la aplicación 
	 * @param fecha de la venta
	 * @param cliente que compra el producto
	 * @param sucursal en donde se realiza el proceso
	 * @param producto que se compra
	 * @param promocion si existe, si no se pone cero.
	 * @param cantidad de productos a comprar
	 * @return El objeto Factura adicionado. null si ocurre alguna excepción 
	 */
	public Factura adicionarVenta(Timestamp fecha, String cliente, long sucursal, long producto, long promocion, int cantidad)
	{
		log.info ("Adicionando factura de cliente: " + cliente);
        Factura factura = pp.adicionarVenta(fecha, cliente, sucursal, producto, promocion, cantidad);
		log.info ("Adicionando factura de cliente: " + cliente);
        return factura;
	}
	
	public Pedido recibirPedido (long idPedido, int calificacion)
	{
		log.info ("Recibiendo pedido: " + idPedido);
        Pedido pedido= pp.recibirPedido(idPedido, calificacion);
		log.info ("Recibiendo pedido: " + idPedido);
        return pedido;
	}
	
}
