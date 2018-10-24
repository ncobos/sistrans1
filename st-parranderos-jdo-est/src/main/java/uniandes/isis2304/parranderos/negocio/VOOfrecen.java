package uniandes.isis2304.parranderos.negocio;



/**
 * Interfaz para los m�todos get de OFRECEN.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOOfrecen {
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
     /**
	 * @return El identificador del producto.
	 */
	public long getIdProducto();
	
	/**
	 * @return El identificador del proveedor.
	 */
	public long getIdProveedor();
	
	/**
	 * @return El costo unitario del producto
	 */
	public double getCosto();
	
	
	/** 
	 * @return Una cadena con la informaci�n b�sica
	 */
	@Override
	public String toString();
}
