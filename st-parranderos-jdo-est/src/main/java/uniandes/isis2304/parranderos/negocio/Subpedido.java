package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto SUBPEDIDO del negocio de SuperAndes
 *
 * @author n.cobos, jf.torresp
 */

public class Subpedido implements VOSubpedido{

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 *  El producto del que se hace el pedido
	 */
	private long idProducto;
	
	/**
	 *  El pedido al que est� asociado
	 */
	private long idPedido;
	
	/**
	 *  El numero de unidades solicitadas
	 */
	private int cantidad;
	
	/**
	 *  El costo de las unidades solicitadas
	 */
	private double costo;

	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Subpedido() {
		this.idProducto = 0;
		this.idPedido = 0;
		this.cantidad = 0;
		this.costo = 0;
	}
	
	/**
	 * Constructor con valores
	 * @param producto
	 * @param pedido
	 * @param cantidad
	 * @param costo
	 */
	public Subpedido(long producto, long pedido, int cantidad, double costo) {
		this.idProducto = producto;
		this.idPedido = pedido;
		this.cantidad = cantidad;
		this.costo = costo;
	}

	/**
	 * @return the producto
	 */
	public long getIdProducto() {
		return idProducto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setIdProducto(long producto) {
		this.idProducto = producto;
	}

	/**
	 * @return the pedido
	 */
	public long getIdPedido() {
		return idPedido;
	}

	/**
	 * @param pedido the pedido to set
	 */
	public void setIdPedido(long pedido) {
		this.idPedido = pedido;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Subpedido [producto=" + idProducto + ", pedido=" + idPedido + ", cantidad=" + cantidad + ", costo=" + costo
				+ "]";
	}
	
	
}
