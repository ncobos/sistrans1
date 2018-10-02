package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto Producto del negocio de SuperAndes
 *
 * @author n.cobos, jf.torresp
 */

public class Producto implements VOProducto{

	/**
	 *  El identificador del producto
	 */
	private long id;
	
	/**
	 *  El nombre del producto
	 */
	private String nombre;
	
	/**
	 *  La marca del producto
	 */
	private String marca;
	
	/**
	 *  La presentaci�n del producto
	 */
	private String presentacion;
	
	/**
	 *  LA unidad de medida del producto
	 */
	private String unidadMedida;
	
	/**
	 *  La categor�a del producto
	 */
	private String categoria;
	
	/**
	 *  El tipo del producto
	 */
	private String tipo;

	
	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Producto() {
		this.id = 0;
		this.nombre = "";
		this.marca = "";
		this.presentacion = "";
		this.unidadMedida = "";
		this.categoria = "";
		this.tipo = "";
	}
	
	
	
	/** Constructor con valores
	 * @param id
	 * @param nombre
	 * @param marca
	 * @param presentacion
	 * @param unidadMedida
	 * @param categoria
	 * @param tipo
	 */
	public Producto(long id, String nombre, String marca, String presentacion, String unidadMedida, String categoria,
			String tipo) {
		this.id = id;
		this.nombre = nombre;
		this.marca = marca;
		this.presentacion = presentacion;
		this.unidadMedida = unidadMedida;
		this.categoria = categoria;
		this.tipo = tipo;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public String getPresentacion() {
		return presentacion;
	}


	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}


	public String getUnidadMedida() {
		return unidadMedida;
	}



	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}


	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", marca=" + marca + ", presentacion=" + presentacion
				+ ", unidadMedida=" + unidadMedida + ", categoria=" + categoria + ", tipo=" + tipo + "]";
	}
	
	
	
}
