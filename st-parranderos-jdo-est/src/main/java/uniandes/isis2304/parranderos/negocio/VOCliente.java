package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los m�todos get de Cliente.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOCliente {

	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	/**
	 * @return El id del Cliente
	 */
	public long getId();

	/**
	 * @return El nombre del Cliente
	 */
	public String getNombre();
	
	/**
	 * @return La direcci�n del Cliente
	 */
	public String getDireccion();
	
	/**
	 * @return El correo del Cliente
	 */
	public String getCorreo();
	
	/**
	 * @return El tipo del Cliente
	 */
	public String getTipo();
}