package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los m�todos get de PEDIDO.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOPedido {
	
	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * @return El id del pedido
	 */
	public long getId();

	/**
	 * @return La sucursal que hace el pedido
	 */
	public long getSucursal();
	
	
	/**
	 * @return El proveedor al que se le hace el pedido
	 */
	public long getProveedor();

	/**
	 * @return La fecha de entrega
	 */
	public Timestamp getFechaEntrega();

	/**
	 * @return El estado de la orden
	 */
	public String getEstadoOrden();

	
	/**
	 * @return La calificaci�n del pedido y el proveedor
	 */
	public int getCalificacion();

	/**
	 * @return Costo total del pedido
	 */
	public double getCostoTotal();
	

	/** 
	 * @return Una cadena con la informaci�n b�sica
	 */
	@Override
	public String toString();


}
