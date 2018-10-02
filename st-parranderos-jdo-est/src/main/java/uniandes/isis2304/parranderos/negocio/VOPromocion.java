package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los m�todos get de PROMOCION.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOPromocion {


	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * @return El identificador de la promoci�n
	 */
	public long getId();
	
	/**
	 * @return El precio de la promoci�n
	 */
	public double getPrecio();

	/**
	 * @return La descripci�n de la promoci�n
	 */
	public String getDescripcion();
	
	/**
	 * @return La fecha de inicio de la promoci�n
	 */
	public Timestamp getFechaInicio();
	
	/**
	 * @return La fecha de fin de la promoci�n
	 */
	public Timestamp getFechaFin();
	
	/**
	 * @return Las unidades disponibles para la venta de la promoci�n
	 */
	public int getUnidadesDisponibles();
	
	/**
	 * @return El identificador del producto en promoci�n.
	 */
	public long getIdProducto();
	
	/** 
	 * @return Una cadena con la informaci�n b�sica
	 */
	@Override
	public String toString();

}