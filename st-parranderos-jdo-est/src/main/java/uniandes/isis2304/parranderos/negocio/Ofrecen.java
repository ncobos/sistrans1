package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

/**
* Clase para modelar la relaci�n OFRECEN del negocio de SuperAndes:
* Cada objeto de esta clase representa el hecho que un proveedor ofrece un producto y viceversa.
* Se modela mediante los identificadores del proveedor y del producto respectivamente.
* Debe existir un proveedor con el identificador dado
* Debe existir un producto con el identificador dado 
* 
* @author n.cobos, jf.torresp
*/

public class Ofrecen implements VOOfrecen{
	
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador del producto
	 */
	private long producto;
	
	/**
	 * El identificador del proveedor
	 */
	private long proveedor;
	
	/**
	 * El costo del producto
	 */
	private double costo;

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Ofrecen() 
	{
		this.producto = 0;
		this.proveedor = 0;
		this.costo = 0;
	}
	
	/**
	 * Constructor con valores
	 * @param producto asociado al proveedor
	 * @param proveedor que vende el producto
	 * @param costo al que se vende el producto
	 */

	public Ofrecen(long producto, long proveedor, double costo) {
		this.producto = producto;
		this.proveedor = proveedor;
		this.costo = costo;
	}

	public long getProducto() {
		return producto;
	}

	public void setProducto(long producto) {
		this.producto = producto;
	}

	public long getProveedor() {
		return proveedor;
	}

	public void setProveedor(long proveedor) {
		this.proveedor = proveedor;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}
	
	@Override
	public String toString() {
		return "Ofrecen [producto=" + producto + ", proveedor=" + proveedor + ", costo=" + costo + "]";
	}
}
