package uniandes.isis2304.parranderos.negocio;

/**
* Clase para modelar la relaci�n TRANSACCION del negocio de SuperAndes:
* Cada objeto de esta clase representa el hecho de que una factura consta de varias transacciones de acuerdo a los productos y viceversa.
* Se modela mediante los identificadores de la factura y del producto respectivamente.
* Debe existir una factura con el identificador dado
* Debe existir un producto con el identificador dado 
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
	 *  La cantidad de productos de la transacci�n
	 */
	private int cantidad;
	
	/**
	 *  El costo de la transacci�n
	 */
	private double costo;
	
	/**
	 *  El producto de la transacci�n
	 */
	private long producto;
	
	/**
	 *  La promoci�n de la transacci�n
	 */
	private long promocion;
	
	/**
	 *  El carrito al cual está asociado la transacci�n
	 */
	private long idCarrito;
	
	/**
	 *  El estado de la transacci�n
	 */
	private String estado;
	
	
	/**
	 * Constructor por defecto
	 */
	public Transaccion() {
		this.numeroFactura = 0;
		this.cantidad = 0;
		this.costo = 0;
		this.producto = 0;
		this.promocion = 0;
		this.idCarrito = 0;
		this.estado = "";
	}
	
	

	/**
	 * Constructor con valores
	 * @param numeroFactura
	 * @param cantidad
	 * @param costo
	 * @param producto
	 * @param promocion
	 */
	public Transaccion(long numeroFactura, int cantidad, double costo, long producto, long promocion, long carrito, String estado) {
		this.numeroFactura = numeroFactura;
		this.cantidad = cantidad;
		this.costo = costo;
		this.producto = producto;
		this.promocion = promocion;
		this.idCarrito = carrito;
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
	 * @return the producto
	 */
	public long getProducto() {
		return producto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setProducto(long producto) {
		this.producto = producto;
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
	 * @return the idCarrito
	 */
	public long getIdCarrito() {
		return idCarrito;
	}



	/**
	 * @param idCarrito the idCarrito to set
	 */
	public void setIdCarrito(long idCarrito) {
		this.idCarrito = idCarrito;
	}



	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}



	/**
	 * @param estado the estado to set
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
				+ ", producto=" + producto + ", promocion=" + promocion + ", idCarrito=" + idCarrito + ", estado="
				+ estado + "]";
	}
	
}
