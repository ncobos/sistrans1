package uniandes.isis2304.parranderos.negocio;

/**
* Clase para modelar la relaci�n VENDE del negocio de SuperAndes:
* Cada objeto de esta clase representa el hecho que una sucursal vende un producto y viceversa.
* Se modela mediante los identificadores de la sucursal y del producto respectivamente.
* Debe existir una sucursal con el identificador dado
* Debe existir un producto con el identificador dado 
* 
* @author n.cobos, jf.torresp
*/

public class Vende implements VOVende{

	/* ****************************************************************
	 * 			Atributos 
	 *****************************************************************/
	/**
	 *  El identificador de la sucursal que vende los productos
	 */
	private long sucursal;
	
	/**
	 *  El identificador del producto que se vende
	 */
	private long producto;
	
	/**
	 *  La cantidad m�nima de productos antes de pedir m�s a los proveedores
	 */
	private int nivelReorden;
	
	/**
	 *  El precio unitario de un producto en una sucursal
	 */
	private double precioUnitario;
	
	/**
	 *  El precio por unidad de medida de un producto en una sucursal
	 */
	private double precioPorUnidadMedida;

	
	/* ****************************************************************
	 * 			Atributos 
	 *****************************************************************/
	
	/**
	 * Constructor por defecto
	 */
	public Vende() {
		this.sucursal = 0;
		this.producto = 0;
		this.nivelReorden = 0;
		this.precioUnitario = 0;
		this.precioPorUnidadMedida = 0;
	}
	
	
	/**
	 * Constructor con valores
	 * @param sucursal
	 * @param producto
	 * @param nivelReorden
	 * @param precioUnitario
	 * @param precioPorUnidadMedida
	 */
	public Vende(long sucursal, long producto, int nivelReorden, double precioUnitario, double precioPorUnidadMedida) {
		this.sucursal = sucursal;
		this.producto = producto;
		this.nivelReorden = nivelReorden;
		this.precioUnitario = precioUnitario;
		this.precioPorUnidadMedida = precioPorUnidadMedida;
	}

	/**
	 * @return the sucursal
	 */
	public long getSucursal() {
		return sucursal;
	}

	/**
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
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
	 * @return the nivelReorden
	 */
	public int getNivelReorden() {
		return nivelReorden;
	}

	/**
	 * @param nivelReorden the nivelReorden to set
	 */
	public void setNivelReorden(int nivelReorden) {
		this.nivelReorden = nivelReorden;
	}

	/**
	 * @return the precioUnitario
	 */
	public double getPrecioUnitario() {
		return precioUnitario;
	}

	/**
	 * @param precioUnitario the precioUnitario to set
	 */
	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	/**
	 * @return the precioPorUnidadMedida
	 */
	public double getPrecioPorUnidadMedida() {
		return precioPorUnidadMedida;
	}

	/**
	 * @param precioPorUnidadMedida the precioPorUnidadMedida to set
	 */
	public void setPrecioPorUnidadMedida(double precioPorUnidadMedida) {
		this.precioPorUnidadMedida = precioPorUnidadMedida;
	}

	
	@Override
	public String toString() {
		return "Vende [sucursal=" + sucursal + ", producto=" + producto + ", nivelReorden=" + nivelReorden
				+ ", precioUnitario=" + precioUnitario + ", precioPorUnidadMedida=" + precioPorUnidadMedida + "]";
	}
	
}
