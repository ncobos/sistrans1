package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los m�todos get de Estante.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOEstante {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
     /**
	 * @return El id del Estante
	 */
	public long getId();
	
	/**
	 * @return la capacidad de peso que tiene el Estante 
	 */
	public double getCapacidadPeso();
	
	/**
	 * @return la capacidad de volumen que tiene el Estante 
	 */
	public double getCapacidadVolumen();
	
	/**
	 * @return La existencias de un producto en el Estante.
	 */
	public int getExistencias();
	
	/**
	 * @return la sucursal del Estante
	 */
	public long getSucursal();
	
	/**
	 * @return el producto que almacena el Estante
	 */
	public long getProducto();
	
	/**
	 * @return el nivel de unidades m�nimo antes de pedir producto a bodega
	 */
	public int getNivelAbastecimientoBodega();

	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del Estante
	 */
	public String toString();

}
