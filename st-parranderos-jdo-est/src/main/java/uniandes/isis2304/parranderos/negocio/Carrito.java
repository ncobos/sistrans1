package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

/**
 * Clase para modelar el concepto Carrito del negocio de SuperAndes
 *
 * @author n.cobos
 */

public class Carrito implements VOCarrito{

	/*****************************************************************
	 * 			Atributos
	 *****************************************************************/
	
	/**
	 * El identificador ÚNICO de las carritos
	 */
	private long id;
	
	
	/**
	 * Cliente del carrito 
	 */
	private String idCliente;
	
	/**
	 * Estado del carrito
	 */
	private String estado;
	
	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Carrito() 
	{
		this.id = 0;
		this.idCliente = "";
		this.estado = "";
	}

	/**
	 * Constructor con valores
	 * @param pId - El identificador del carrito. Debe existir una carrito con dicho identificador
	 * @param pCliente - El identificador del idCliente. Debe existir un cliente con dicho identificador. 
	 * @param pEstado - El estado del carrito (en uso, abandonado, 
	 */
	public Carrito(long pNumero, String pCliente, String pEstado) 
	{
		this.id = pNumero;
		this.idCliente = pCliente;
		this.estado = pEstado;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the idCliente
	 */
	public String getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	
	@Override
	public String toString() {
		return "Carrito [id=" + id + ", idCliente=" + idCliente + ", estado=" + estado + "]";
	}

}
