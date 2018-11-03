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
	public long id;
	
	/**
	 * Estado del carrito
	 */
	public String estado;
	
	/**
	 * Clave del carrito
	 */
	public long clave;
	
	/**
	 * Sucursal del carrito
	 */
	public long sucursal;
	
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
	 * @param pNumero - El identificador del carrito. Debe existir una carrito con dicho identificador
	 * @param pEstado - El estado del carrito (en uso, abandonado, libre, pagado)
	 * @param pClave- Clave asignada al carrito
	 * @param pSucursal - Sucursal a la que pertenece el carrito
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
	 * @return the sucursal
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Carrito [id=" + id + ", estado=" + estado + ", clave=" + clave + ", sucursal=" + sucursal + "]";
	}

}
