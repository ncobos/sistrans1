package uniandes.isis2304.parranderos.negocio;


/**
 * Interfaz para los m�todos get de CONTIENE.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOContiene {
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	/**
	 * @return el id del carrito
	 */
	public long getCarrito();
	
	/**
	 * @return La cantidad de productos
	 */
	public int getCantidad();
	
	/**
	 * @return El producto de la transacci�n
	 */
	public long getProducto();
	
	/** 
	 * @return Una cadena con la informaci�n b�sica
	 */
	@Override
	public String toString();


}
