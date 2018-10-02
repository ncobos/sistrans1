package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

/**
 * Interfaz para los m�todos get de PRODUCTO.
 * Sirve para proteger la informaci�n del negocio de posibles manipulaciones desde la interfaz 
 * 
 * @author n.cobos, jf.torresp
 */

public interface VOProducto {

	/*****************************************************************
	 * 			M�todos 
	 *****************************************************************/
     /**
	 * @return El identificador del producto
	 */
	public long getId();
	
	/**
	 * @return El nombre del producto
	 */
	public String getNombre();
	
	/**
	 * @return La marca del producto
	 */
	public String getMarca();
	
	/**
	 * @return La presentaci�n del producto
	 */
	public String getPresentacion();
	
	/**
	 * @return LA unidad de medida del producto
	 */
	public String getUnidadMedida();
	
	/**
	 * @return La categor�a del producto
	 */
	public String getCategoria();
	
	/**
	 * @return El tipo del producto
	 */
	public String getTipo();
	
	/** 
	 * @return Una cadena con la informaci�n b�sica
	 */
	@Override
	public String toString();
}
