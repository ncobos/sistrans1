package uniandes.isis2304.parranderos.negocio;


/**
 * Clase para modelar el concepto Cliente del negocio de SuperAndes
 *
 * @author n.cobos, jf.torresp
 */

public class Cliente implements VOCliente {

	/*
	 * **************************************************************** Atributos
	 *****************************************************************/

	/**
	 * El identificador �NICO de los clientes
	 */
	private String id;

	/**
	 * El nombre del cliente
	 */
	private String nombre;

	/**
	 * El correo del cliente
	 */
	private String correo;

	/**
	 * El tipo del cliente
	 */
	private String tipo;

	/**
	 * La direcci�n del cliente
	 */
	private String direccion;

	/*
	 * **************************************************************** M�todos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Cliente() {
		this.id = "";
		this.nombre = "";
		this.correo = "";
		this.direccion = "";
		this.tipo = "";
	}

	/**
	 * Constructor con valores
	 * @param id - El id del cliente
	 * @param nombre - El nombre del cliente
	 * @param direccion - La direccion del cliente
	 * @param correo - El correo del cliente 
  	 * @param correo - El tipo del cliente (empresa, persona)

	 */
	public Cliente(String id, String nombre, String direccion, String correo, String tipo) 
	{
		this.id = id;
		this.nombre = nombre;
		this.correo = correo;
		this.direccion = direccion;
		this.tipo = tipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return Una cadena de caracteres con la informaci�n b�sica del cliente
	 */
	@Override
	public String toString() 
	{
		return "Cliente [id=" + id + ", nombre=" + nombre + ", direcci�n=" + direccion + ", correo=" + correo + ", tipo=" + tipo + "]";
	}
}
