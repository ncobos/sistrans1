package uniandes.isis2304.parranderos.negocio;

/**
* Clase para modelar la relaci�n VENDE del negocio de SuperAndes:
* Cada objeto de esta clase representa el hecho que una idIdSucursal vende un idProducto y viceversa.
* Se modela mediante los identificadores de la idIdSucursal y del idProducto respectivamente.
* Debe existir una idIdSucursal con el identificador dado
* Debe existir un idProducto con el identificador dado 
* 
* @author n.cobos, jf.torresp
*/

public class Vende implements VOVende{

	/* ****************************************************************
	 * 			Atributos 
	 *****************************************************************/
	/**
	 *  El identificador de la idIdSucursal que vende los idProductos
	 */
	private long idSucursal;
	
	/**
	 *  El identificador del idProducto que se vende
	 */
	private long idProducto;
	
	/**
	 *  La cantidad m�nima de idProductos antes de pedir m�s a los proveedores
	 */
	private int nivelReorden;
	
	/**
	 *  El precio unitario de un idProducto en una idIdSucursal
	 */
	private double precioUnitario;
	
	/**
	 *  El precio por unidad de medida de un idProducto en una idIdSucursal
	 */
	private double precioUnidadMedida;

	
	/* ****************************************************************
	 * 			Atributos 
	 *****************************************************************/
	
	/**
	 * Constructor por defecto
	 */
	public Vende() {
		this.idSucursal = 0;
		this.idProducto = 0;
		this.nivelReorden = 0;
		this.precioUnitario = 0;
		this.precioUnidadMedida = 0;
	}
	
	
	/**
	 * Constructor con valores
	 * @param idIdSucursal
	 * @param idProducto
	 * @param nivelReorden
	 * @param precioUnitario
	 * @param precioUnidadMedida
	 */
	public Vende(long idIdSucursal, long idProducto, int nivelReorden, double precioUnitario, double precioUnidadMedida) {
		this.idSucursal = idIdSucursal;
		this.idProducto = idProducto;
		this.nivelReorden = nivelReorden;
		this.precioUnitario = precioUnitario;
		this.precioUnidadMedida = precioUnidadMedida;
	}

	/**
	 * @return the idIdSucursal
	 */
	public long getIdSucursal() {
		return idSucursal;
	}

	/**
	 * @param idIdSucursal the idIdSucursal to set
	 */
	public void setIdSucursal(long idIdSucursal) {
		this.idSucursal = idIdSucursal;
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
	 * @return the precioUnidadMedida
	 */
	public double getPrecioUnidadMedida() {
		return precioUnidadMedida;
	}

	/**
	 * @param precioUnidadMedida the precioUnidadMedida to set
	 */
	public void setPrecioUnidadMedida(double precioUnidadMedida) {
		this.precioUnidadMedida = precioUnidadMedida;
	}

	
	@Override
	public String toString() {
		return "Vende [idIdSucursal=" + idSucursal + ", idProducto=" + idProducto + ", nivelReorden=" + nivelReorden
				+ ", precioUnitario=" + precioUnitario + ", precioUnidadMedida=" + precioUnidadMedida + "]";
	}
	
}
