package uniandes.isis2304.parranderos.negocio;

/**
* Clase para modelar la relaci�n TRANSACCION del negocio de SuperAndes:
* Cada objeto de esta clase representa el hecho de que una factura consta de varias transacciones de acuerdo a los idProductos y viceversa.
* Se modela mediante los identificadores de la factura y del idProducto respectivamente.
* Debe existir una factura con el identificador dado
* Debe existir un idProducto con el identificador dado 
* 
* @author n.cobos, jf.torresp
*/

public class Transaccion implements VOTransaccion{

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 *  El numero de la factura
	 */
	private long numeroFactura;
	
	/**
	 *  La cantidad de idProductos de la transacci�n
	 */
	private int cantidad;
	
	/**
	 *  El costo de la transacci�n
	 */
	private double costo;
	
	/**
	 *  El idProducto de la transacci�n
	 */
	private long idProducto;
	
	/**
	 *  La promoci�n de la transacci�n
	 */
	private long promocion;
	
	/**
	 *  Estado de la transacci�n
	 */
	private String estado;
	
	/**
	 * Constructor por defecto
	 */
	public Transaccion() {
		this.numeroFactura = 0;
		this.cantidad = 0;
		this.costo = 0;
		this.idProducto = 0;
		this.promocion = 0;
		this.estado = "";
		
	}
	
	

	/**
	 * Constructor con valores
	 * @param numeroFactura
	 * @param cantidad
	 * @param costo
	 * @param idProducto
	 * @param promocion
	 */
	public Transaccion(long numeroFactura, int cantidad, double costo, long idProducto, long promocion, long carrito, String estado) {
		this.numeroFactura = numeroFactura;
		this.cantidad = cantidad;
		this.costo = costo;
		this.idProducto = idProducto;
		this.promocion = promocion;
		this.estado = estado;
	}

	/**
	 * @return the numeroFactura
	 */
	public long getNumeroFactura() {
		return numeroFactura;
	}

	/**
	 * @param numeroFactura the numeroFactura to set
	 */
	public void setNumeroFactura(long numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	/**
	 * @return the cantidad
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the costo
	 */
	public double getCosto() {
		return costo;
	}

	/**
	 * @param costo the costo to set
	 */
	public void setCosto(double costo) {
		this.costo = costo;
	}

	/**
	 * @return the idProducto
	 */
	public long getIdProducto() {
		return idProducto;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * @return the promocion
	 */
	public long getPromocion() {
		return promocion;
	}

	/**
	 * @param promocion the promocion to set
	 */
	public void setPromocion(long promocion) {
		this.promocion = promocion;
	}

	/**
	 * @return the idProducto
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Transaccion [numeroFactura=" + numeroFactura + ", cantidad=" + cantidad + ", costo=" + costo
				+ ", idProducto=" + idProducto + ", promocion=" + promocion +"]";
	}
	
}
