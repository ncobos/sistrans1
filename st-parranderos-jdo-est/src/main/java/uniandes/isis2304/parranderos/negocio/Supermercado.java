package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto Supermercado del negocio de SuperAndes
 *
 * @author n.cobos, jf.torresp
 */

public class Supermercado implements VOSupermercado{
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * Nombre del supermercado
	 */
	private String nombre;

	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	/**
	 * Constructor por defecto.
	 */
	public Supermercado() {
		this.nombre = "";
	}
	
	
	/**
	 * Constructor con valores.
	 * @param nombre del supermercado
	 */
	public Supermercado(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}


	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Supermercado [nombre=" + nombre + "]";
	}

}
