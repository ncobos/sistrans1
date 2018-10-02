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
	private long idProveedor;

	/**
	 * El identificador de la sucursal que hizo el pedido
	 */
	private long idSucursal;

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
	private int calificacionServicio;

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
		this.idProveedor = 0;
		this.idSucursal = 0;
		this.fechaEntrega = new Timestamp(0);
		this.estadoOrden = "";
		this.cantidad = 0;
		this.calificacionServicio = 0;
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
		this.idProveedor = proveedor;
		this.idSucursal = sucursal;
		this.fechaEntrega = fechaEntrega;
		this.estadoOrden = estadoOrden;
		this.cantidad = cantidad;
		this.calificacionServicio = calificacion;
		this.costoTotal = costoTotal;
	}


	public long getId() {
		return id;
	}

	

	public void setId(long id) {
		this.id = id;
	}

	public long getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(long proveedor) {
		this.idProveedor = proveedor;
	}

	public long getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(long sucursal) {
		this.idSucursal = sucursal;
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

	public int getCalificacionServicio() {
		return calificacionServicio;
	}

	public void setCalificacionServicio(int calificacion) {
		this.calificacionServicio = calificacion;
	}

	public double getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(double costoTotal) {
		this.costoTotal = costoTotal;
	}

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", proveedor=" + idProveedor + ", sucursal=" + idSucursal
				+ ", fechaEntrega=" + fechaEntrega + ", estadoOrden=" + estadoOrden + ", cantidad=" + cantidad
				+ ", calificacion=" + calificacionServicio + ", costoTotal=" + costoTotal + "]";
	}



}
