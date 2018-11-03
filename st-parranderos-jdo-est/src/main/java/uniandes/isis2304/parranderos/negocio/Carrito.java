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
	 * Estado del carrito
	 */
	private String estado;
	
	/**
	 * Clave del carrito
	 */
	private long clave;
	
	/**
	 * Sucursal a la que pertence el carrito
	 */
	private long sucursal;
	
	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Carrito() 
	{
		this.id = 0;
		this.estado = "";
		this.clave = 0;
		this.sucursal = 0;
	}

	/**
	 * Constructor con valores
	 * @param pId - El identificador del carrito. Debe existir una carrito con dicho identificador
	 * @param pCliente - El identificador del idCliente. Debe existir un cliente con dicho identificador. 
	 * @param pEstado - El estado del carrito (en uso, abandonado, 
	 */
	public Carrito(long pNumero, String pEstado, long pClave, long pSucursal) 
	{
		this.id = pNumero;
		this.estado = pEstado;
		this.clave = pClave;
		this.sucursal = pSucursal;
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

	
	
	

	/**
	 * @return the clave
	 */
	public long getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(long clave) {
		this.clave = clave;
	}
	
	/**
	 * @return la sucursal
	 */
	public long getSucursal() {
		return sucursal;
	}

	/**
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
	}

	/** 
	 * @return Una cadena con la información básica del carrito
	 */
	@Override
	public String toString() {
		return "Carrito [id=" + id + ", estado=" + estado + ", clave=" + clave + ", sucursal=" + sucursal + "]";
	}

}
