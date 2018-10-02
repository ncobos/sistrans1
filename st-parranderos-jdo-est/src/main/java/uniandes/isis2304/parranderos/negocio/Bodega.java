package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto Bodega del negocio de SuperAndes
 *
 * @author n.cobos, jf.torresp
 */
public class Bodega implements VOBodega{

	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	/**
	 * El identificador �NICO de las bodegas
	 */
	private long id;

	/**
	 * La capacidad en t�rminos de volumen (metros c�bicos) que posee la bodega 
	 */
	
	private double capacidadVolumen;
	
	/**
	 * La capacidad en t�rminos de peso (toneladas) que posee la bodega 
	 */
	
	private double capacidadPeso;
	
	/**
	 * Las existencias del producto que posee la bodega 
	 */
	
	private int existencias;
	
	/**
	 * El c�digo del producto que almacena
	 */
	private long producto;
	
	/**
	 * El c�digo de la sucursal a la que pertenece
	 */
	private long sucursal;
	
	/* ****************************************************************
	 * 			M�todos 
	 *****************************************************************/
    /**
     * Constructor por defecto
     */
	public Bodega() 
    {
    	this.id = 0;
		this.capacidadVolumen = 0;
		this.capacidadPeso = 0;
		this.existencias = 0;
		this.producto = 0;
		this.sucursal = 0;
	}

	/**
	 * Constructor con valores
	 * @param id - El id de la sucursal
	 * @param pCapacidadV - la capacidad de volumen de la bodega (Mayor que 0).
	 * @param pCapacidadP - la capacidad de peso de la bodega (Mayor que 0).
	 * @param pEx - Las existencias del producto en la bodega (Mayor que 0).
	 * @param pPro - El c�digo del producto que se almacena
	 * @param pSu - El c�digo de la sucursal a la que pertenece la bodega.
	 */
    public Bodega(long id,  double pCapacidadV, double pCapacidadP, int pEx, long pPro, long pSu)
    {
    	this.id = id;
    	this.capacidadVolumen = pCapacidadV;
    	this.capacidadPeso = pCapacidadP;
    	this.existencias = pEx;
    	this.producto = pPro;
		this.sucursal = pSu;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getCapacidadVolumen() {
		return capacidadVolumen;
	}

	public void setCapacidadVolumen(double capacidadVolumen) {
		this.capacidadVolumen = capacidadVolumen;
	}

	public double getCapacidadPeso() {
		return capacidadPeso;
	}

	public void setCapacidadPeso(double capacidadPeso) {
		this.capacidadPeso = capacidadPeso;
	}

	public int getExistencias() {
		return existencias;
	}

	public void setExistencias(int existencias) {
		this.existencias = existencias;
	}

	public long getProducto() {
		return producto;
	}

	public void setProducto(long producto) {
		this.producto = producto;
	}

	public long getSucursal() {
		return sucursal;
	}

	public void setSucursal(long sucursal) {
		this.sucursal = sucursal;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del bar
	 */
	public String toString() 
	{
		return "Bodega [id=" + id + ", capacidad volumen=" + capacidadVolumen + ", capacidad peso=" + capacidadPeso + ", existencias=" + existencias
				+ ", producto=" + producto  + ", sucursal=" + sucursal + "]";
	}
}
