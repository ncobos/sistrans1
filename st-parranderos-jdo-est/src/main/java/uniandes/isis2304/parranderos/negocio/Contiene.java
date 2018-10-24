package uniandes.isis2304.parranderos.negocio;

/**
* Clase para modelar la relaci�n CONTIENE del negocio de SuperAndes:
* Cada objeto de esta clase representa el hecho de que una carrito consta de varios productos
* Se modela mediante los identificadores de carrito y del producto respectivamente.
* Debe existir un carrito con el identificador dado
* Debe existir un producto con el identificador dado 
* 
* @author n.cobos, jf.torresp
*/

public class Contiene implements VOContiene{

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 *  El numero del carrito
	 */
	private long idCarrito;
	
	/**
	 *  La cantidad de productos 
	 */
	private int cantidad;
	
	
	/**
	 *  El producto 
	 */
	private long producto;
	
	

	
	/**
	 * Constructor por defecto
	 */
	public Contiene() {
		this.idCarrito = 0;
		this.cantidad = 0;
		this.producto = 0;
		
	}
	
	

	/**
	 * Constructor con valores
	 * @param idCarrito
	 * @param cantidad
	 * @param costo
	 * @param producto
	 * @param promocion
	 */
	public Contiene(long idCarrito, int cantidad, long producto) {
		this.idCarrito = idCarrito;
		this.cantidad = cantidad;
		this.producto = producto;
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

	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Contiene [idCarrito=" + idCarrito + ", cantidad=" + cantidad + 
				", producto=" + producto + "]";
	}
	
}
