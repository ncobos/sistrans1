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
	private long idproducto;
	
	/**
	 *  El pedido al que est� asociado
	 */
	private long idpedido;
	
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
		this.idproducto = 0;
		this.idpedido = 0;
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
		this.idproducto = producto;
		this.idpedido = pedido;
		this.cantidad = cantidad;
		this.costo = costo;
	}

	/**
	 * @return the producto
	 */
	public long getidproducto() {
		return idproducto;
	}

	/**
	 * @param producto the producto to set
	 */
	public void setidproducto(long producto) {
		this.idproducto = producto;
	}

	/**
	 * @return the pedido
	 */
	public long getidpedido() {
		return idpedido;
	}

	/**
	 * @param pedido the pedido to set
	 */
	public void setidpedido(long pedido) {
		this.idpedido = pedido;
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
		return "Subpedido [producto=" + idproducto + ", pedido=" + idpedido + ", cantidad=" + cantidad + ", costo=" + costo
				+ "]";
	}
	
	
}
