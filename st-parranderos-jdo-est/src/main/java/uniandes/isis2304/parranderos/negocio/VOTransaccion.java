package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los m�todos get de TRANSACCION.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOTransaccion {
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
	/**
	 * @return El numero de la factura
	 */
	public long getNumeroFactura();
	
	/**
	 * @return La cantidad de productos de la transacci�n
	 */
	public int getCantidad();
	
	/**
	 * @return El costo de la transacci�n
	 */
	public double getCosto();
	
	/**
	 * @return El producto de la transacci�n
	 */
	public long getProducto();
	
	/**
	 * @return La promoci�n de la transacci�n
	 */
	public long getPromocion();
	

	
	/** 
	 * @return Una cadena con la informaci�n b�sica
	 */
	@Override
	public String toString();


}
