package uniandes.isis2304.parranderos.negocio;

/**
 * Interfaz para los m�todos get de SUBPEDIDO.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOSubpedido {
	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	
	/**
	 * @return El producto del que se hace el pedido
	 */
	public long getIdProducto();
	
	/**
	 * @return El pedido al que est� asociado
	 */
	public long getIdPedido();
	
	/**
	 * @return El numero de unidades solicitadas
	 */
	public int getCantidad();
	
	/**
	 * @return El costo de las unidades solicitadas
	 */
	public double getCosto();
	
	/** 
	 * @return Una cadena con la informaci�n b�sica
	 */
	@Override
	public String toString();
	
}
