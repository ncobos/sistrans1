package uniandes.isis2304.parranderos.negocio;


/**
 * Interfaz para los m�todos get de Carrito.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOCarrito {
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
     /**
	 * @return El Id del carrito
	 */
	public long getId();
	
	
	/**
	 * @return El estado del carrito
	 */
	public String getEstado();
	
	/**
	 * @return La clave del carrito
	 */
	public long getClave();
	
	/**
	 * @return La sucursal del carrito
	 */

	public long getSucursal();
	
	/** 
	 * @return Una cadena con la información básica del carrito
	 */
	@Override
	public String toString();
}
