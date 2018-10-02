package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los m�todos get de BODEGA.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOBodega {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
     /**
	 * @return El id de la bodega
	 */
	public long getId();
	
	/**
	 * @return la capacidad de peso que tiene la bodega 
	 */
	public double getCapacidadPeso();
	
	/**
	 * @return la capacidad de volumen que tiene la bodega 
	 */
	public double getCapacidadVolumen();
	
	/**
	 * @return La existencias de un producto en la bodega.
	 */
	public int getExistencias();
	
	/**
	 * @return la sucursal de la bodega
	 */
	public long getSucursal();
	
	/**
	 * @return el producto que almacena la bodega
	 */
	public long getProducto();

	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la bodega
	 */
	public String toString();

}
