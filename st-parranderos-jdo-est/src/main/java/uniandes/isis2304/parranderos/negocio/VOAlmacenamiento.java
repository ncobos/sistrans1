package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los m�todos get de Almacenamiento.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOAlmacenamiento {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
     /**
	 * @return El id del Almacenamiento
	 */
	public long getId();
	
	/**
	 * @return la capacidad de peso que tiene el Almacenamiento 
	 */
	public double getCapacidadPeso();
	
	/**
	 * @return la capacidad de volumen que tiene el Almacenamiento 
	 */
	public double getCapacidadVolumen();
	
	/**
	 * @return La existencias de un producto en el Almacenamiento.
	 */
	public int getExistencias();
	
	/**
	 * @return la sucursal del Almacenamiento
	 */
	public long getSucursal();
	
	/**
	 * @return el producto que almacena el Almacenamiento
	 */
	public long getProducto();
	
	/**
	 * @return el nivel de unidades m�nimo antes de pedir producto a bodega
	 */
	public int getNivelAbastecimientoBodega();
	
	/**
	 * @return the capacidadProductos
	 */
	public int getCapacidadProductos() ;
	
	/**
	 * @return the tipo
	 */
	public String getTipo();

	
	/**
	 * @return Una cadena de caracteres con todos los atributos del Almacenamiento
	 */
	public String toString();

}

