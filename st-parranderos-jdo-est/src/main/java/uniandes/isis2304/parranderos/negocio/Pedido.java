package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

/**
 * Clase para modelar el concepto PEDIDO del negocio de SuperAndes
 *
 * @author n.cobos, jf.torresp
 */

public class Pedido implements VOPedido{

	/*****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador del pedido
	 */
	private long id;

	/**
	 * El identificador del proveedor encargado del pedido
	 */
	private long proveedor;

	/**
	 * El identificador de la sucursal que hizo el pedido
	 */
	private long sucursal;

	/**
	 * La fecha de entrega
	 */
	private Timestamp fechaEntrega;

	/**
	 * El estado de la orden
	 */
	private String estadoOrden;


	/**
	 *  El numero de unidades solicitadas
	 */
	private int cantidad;


	/**
	 *  La calificaci�n del pedido y el proveedor
	 */
	private int calificacion;

	/**
	 *  Costo total del pedido
	 */
	private double costoTotal;

	/****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	
	public Pedido() {
		this.id = 0;
		this.proveedor = 0;
		this.sucursal = 0;
		this.fechaEntrega = new Timestamp(0);
		this.estadoOrden = "";
		this.cantidad = 0;
		this.calificacion = 0;
		this.costoTotal = 0;
	}
	
	/**
	 * Metodo constructor con valores
	 * @param id
	 * @param proveedor
	 * @param sucursal
	 * @param fechaEntrega
	 * @param estadoOrden
	 * @param cantidad
	 * @param calificacion
	 * @param costoTotal
	 */
	public Pedido(long id, long proveedor, long sucursal, Timestamp fechaEntrega, String estadoOrden,
			int cantidad, int calificacion, double costoTotal) {
		this.id = id;
		this.proveedor = proveedor;
		this.sucursal = sucursal;
		this.fechaEntrega = fechaEntrega;
		this.estadoOrden = estadoOrden;
		this.cantidad = cantidad;
		this.calificacion = calificacion;
		this.costoTotal = costoTotal;
	}


	public long getId() {
		return id;
	}

	

	public void setId(long id) {
		this.id = id;
	}

	public long getProveedor() {
		return proveedor;
	}

	public void setProveedor(long proveedor) {
		this.proveedor = proveedor;
	}

	public long getSucursal() {
		return sucursal;
	}

	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
	}

	public Timestamp getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Timestamp fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public String getEstadoOrden() {
		return estadoOrden;
	}

	public void setEstadoOrden(String estadoOrden) {
		this.estadoOrden = estadoOrden;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(double costoTotal) {
		this.costoTotal = costoTotal;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", proveedor=" + proveedor + ", sucursal=" + sucursal
				+ ", fechaEntrega=" + fechaEntrega + ", estadoOrden=" + estadoOrden + ", cantidad=" + cantidad
				+ ", calificacion=" + calificacion + ", costoTotal=" + costoTotal + "]";
	}



}
