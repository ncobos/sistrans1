package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto Proveedor del negocio de SuperAndes
 *
 * @author n.cobos, jf.torresp
 */

public class Proveedor implements VOProveedor {

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 *  El nit del proveedor
	 */
	private long nit;
	
	/**
	 *  El nombre del proveedor
	 */
	private String nombre;
	
	/**
	 *  La calificaci�n del servicio del proveedor
	 */
	private int calificacion;

	
	/* ****************************************************************
	 * 			Metodos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Proveedor() {
		this.nit = 0;
		this.nombre = "";
		this.calificacion = 0;
	}
	
	/**
	 * Constructor con valores
	 * @param nit
	 * @param nombre
	 * @param calificacion
	 */
	public Proveedor(long nit, String nombre, int calificacion) {
		this.nit = nit;
		this.nombre = nombre;
		this.calificacion = calificacion;
	}

	public long getNit() {
		return nit;
	}

	public void setNit(long nit) {
		this.nit = nit;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	@Override
	public String toString() {
		return "Proveedor [nit=" + nit + ", nombre=" + nombre + ", calificacion=" + calificacion + "]";
	}
	
	
}
