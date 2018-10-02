package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los m�todos get de PROVEEDOR.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOProveedor {

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * @return El nit del proveedor
	 */
	public long getNit();
	
	/**
	 * @return El nombre del proveedor
	 */
	public String getNombre();
	
	/**
	 * @return La calificaci�n del servicio del proveedor
	 */
	public int getCalificacion();
	
	/** 
	 * @return Una cadena con la informaci�n b�sica
	 */
	@Override
	public String toString();
}
