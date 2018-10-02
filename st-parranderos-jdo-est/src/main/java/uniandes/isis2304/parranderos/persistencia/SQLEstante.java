package uniandes.isis2304.parranderos.persistencia;

	import java.util.List;

	import javax.jdo.PersistenceManager;
	import javax.jdo.Query;

	import uniandes.isis2304.parranderos.negocio.Estante;


	/**
	 * Clase que encapsula los m�todos que hacen acceso a la base de datos para el concepto ESTANTE de Parranderos
	 * N�tese que es una clase que es s�lo conocida en el paquete de persistencia
	 * 
	 * @author n.cobos
	 */
	class SQLEstante 
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
		public SQLEstante (PersistenciaParranderos pp)
		{
			this.pp = pp;
		}
		
		/**
		 * Crea y ejecuta la sentencia SQL para adicionar un ESTANTE a la base de datos de Parranderos
		 * @param pm - El manejador de persistencia
		 * @param id - El identificador del estante
		 * @param capacidadVolumen - el volumen del estante
		 * @param capacidadPeso - Capacidad de peso del estante
		 * @param producto - producto que almacena el estante 
		 * @param sucursal - la sucursal a la que pertenece el estante
		 * @param nivelabastecimientobodega nivel m�nimo de productos antes de reabastecer
		 * @param existencias - existencias del producto en el estante
		 * @return El n�mero de tuplas insertadas
		 */
		public long adicionarEstante (PersistenceManager pm, long id, double capacidadVolumen, double capacidadPeso, long producto, long sucursal, int nivelabastecimientobodega, int existencias) 
		{
	        Query q = pm.newQuery(SQL, "INSERT INTO " + pp.darTablaEstante () + "(id, capacidadvolumen, capacidadpeso, producto, sucursal, nivelabastecimientobodega, existencias) values (?, ?, ?, ?, ?, ?, ?)");
	        q.setParameters(id, capacidadVolumen, capacidadPeso, capacidadPeso, producto, sucursal, nivelabastecimientobodega, existencias);
	        return (long) q.executeUnique();
		}


		/**
		 * Crea y ejecuta la sentencia SQL para eliminar UN ESTANTE de la base de datos de Parranderos, por su identificador
		 * @param pm - El manejador de persistencia
		 * @param idEstante - El identificador del estante
		 * @return EL n�mero de tuplas eliminadas
		 */
		public long eliminarEstantePorId (PersistenceManager pm, long idEstante)
		{
	        Query q = pm.newQuery(SQL, "DELETE FROM " + pp.darTablaEstante () + " WHERE id = ?");
	        q.setParameters(idEstante);
	        return (long) q.executeUnique();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de UN ESTANTE de la 
		 * base de datos de Parranderos, por su identificador
		 * @param pm - El manejador de persistencia
		 * @param idEstante - El identificador del estante
		 * @return El objeto ESTANTE que tiene el identificador dado
		 */
		public Estante darEstantePorId (PersistenceManager pm, long idEstante) 
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEstante () + " WHERE id = ?");
			q.setResultClass(Estante.class);
			q.setParameters(idEstante);
			return (Estante) q.executeUnique();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS ESTANTEES de la 
		 * base de datos de Parranderos, por su nombre
		 * @param pm - El manejador de persistencia
		 * @param sucursal - numero de la sucursal
		 * @return Una lista de objetos ESTANTE que pertenecen a cierta sucursal
		 */
		public List<Estante> darEstantesPorSucursal (PersistenceManager pm, long sucursal) 
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEstante () + " WHERE sucursal = ?");
			q.setResultClass(Estante.class);
			q.setParameters(sucursal);
			return (List<Estante>) q.executeList();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para encontrar la informaci�n de LOS ESTANTES de la 
		 * base de datos de Parranderos
		 * @param pm - El manejador de persistencia
		 * @return Una lista de objetos ESTANTE
		 */
		public List<Estante> darEstantes (PersistenceManager pm)
		{
			Query q = pm.newQuery(SQL, "SELECT * FROM " + pp.darTablaEstante ());
			q.setResultClass(Estante.class);
			return (List<Estante>) q.executeList();
		}

		/**
		 * Crea y ejecuta la sentencia SQL para aumentar en diez el n�mero de existencias de las estantes de la 
		 * base de datos de Parranderos
		 * @param pm - El manejador de persistencia
		 * @param id - estante a la cual se le quiere realizar el proceso
		 * @return El n�mero de tuplas modificadas
		 */
		public long aumentarExistenciasEstantesEnDiez (PersistenceManager pm, long id)
		{
	        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaEstante () + " SET existencias = existencias + 10 WHERE id = ?");
	        q.setParameters(id);
	        return (long) q.executeUnique();
		}
		
		/**
		 * Crea y ejecuta la sentencia SQL para disminuir las existencias de los estantes de 
		 * base de datos de Parranderos
		 * @param pm - El manejador de persistencia
		 * @param cantidad a disminuir
		 * @param sucursal del estante
		 * @param producto que almacena el estante
		 * @return El número de tuplas modificadas
		 */
		public long disminuirExistenciasEstantes(PersistenceManager pm, int cantidad, long sucursal, long producto)
		{
	        Query q = pm.newQuery(SQL, "UPDATE " + pp.darTablaEstante() + " SET existencias = existencias - ? WHERE producto = ? AND sucursal = ?");
	        q.setParameters(cantidad, producto, sucursal);
	        return (long) q.executeUnique();
		}
		

}
