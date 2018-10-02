package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

/**
 * Clase para modelar el concepto Factura del negocio de SuperAndes
 *
 * @author n.cobos, jf.torresp
 */

public class Factura implements VOFactura{

	/*****************************************************************
	 * 			Atributos
	 *****************************************************************/
	
	/**
	 * El identificador ÚNICO de las facturas
	 */
	private long numero;
	
	/**
	 * Fecha de la factura
	 */
	private Timestamp fecha;
	
	/**
	 * Cliente de la factura 
	 */
	private String cliente;
	
	/**
	 * Sucursal donde se emiti� la factura
	 */
	private long sucursal;
	
	/* ****************************************************************
	 * 			M�todos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Factura() 
	{
		this.numero = 0;
		this.fecha = new Timestamp(0);
		this.cliente = "";
		this.sucursal = 0;
	}

	/**
	 * Constructor con valores
	 * @param pNumero - El identificador de la factura. Debe existir una factura con dicho identificador
	 * @param pFecha - La fecha en la cual se realiza la visita
	 * @param pCliente - El identificador del cliente. Debe existir un cliente con dicho identificador. 
	 * @param pSucursal - El identificador de la sucursal. Debe existir una sucursal con dicho identificador.
	 */
	public Factura(long pNumero, long pSucursal, Timestamp pFecha, String pCliente) 
	{
		this.numero = pNumero;
		this.cliente = pCliente;
		this.fecha= pFecha;
		this.sucursal = pSucursal;
	}
	
	
	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public long getSucursal() {
		return sucursal;
	}

	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
	}

	@Override
	public String toString() {
		return "Factura [numero=" + numero + ", fecha=" + fecha + ", cliente=" + cliente + ", sucursal=" + sucursal
				+ "]";
	}
	
	
}
