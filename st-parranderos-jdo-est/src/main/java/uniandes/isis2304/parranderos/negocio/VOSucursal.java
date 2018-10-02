package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los m�todos get de SUCURSAL.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOSucursal {
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
    /**
	 * @return El id de la sucursal
	 */
	public long getId();
	
	/**
	 * @return el nombre de la sucursal
	 */
	public String getNombre();
	
	/**
	 * @return la ciudad de la sucursal
	 */
	public String getCiudad();
	
	/**
	 * @return la direccion de la sucursal
	 */
	public String getDireccion();
	
	/**
	 * @return el segmento de mercado de la sucursal
	 */
	public String getSegmentoMercado();
	
	/**
	 * @return el tamano de la sucursal en metros cuadrados
	 */
	public double getTamano();
	
	/**
	 * @return el supermercado al que pertenece la sucursal
	 */
	public String getSupermercado();

}
