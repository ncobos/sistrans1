package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto Almacenamiento del negocio de SuperAndes
 *
 * @author n.cobos, jf.torresp
 */
public class Almacenamiento implements VOAlmacenamiento {

	/*
	 * ****************************************************************
	 * Atributos
	 *****************************************************************/
	/**
	 * El identificador �NICO de las Almacenamientos
	 */
	private long id;

	/**
	 * La capacidad en t�rminos de volumen (metros c�bicos) que posee la Almacenamiento
	 */

	private double capacidadVolumen;

	/**
	 * La capacidad en t�rminos de peso (toneladas) que posee la Almacenamiento
	 */

	private double capacidadPeso;

	/**
	 * Las existencias del producto que posee la Almacenamiento
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

	/**
	 * Nivel m�nimo de unidades que deben haber, en caso contrario, se debe
	 * pedir reabastecimiento a la bodega. En el caso de que sea bodega será cero.
	 */
	private int nivelAbastecimientoBodega;
	
	/**
	 * La capacidad de productos que posee la Almacenamiento
	 */
	private int capacidadProductos;
	
	/**
	 * El tipo del almacenamiento
	 */
	private String tipo;

	/*
	 * **************************************************************** M�todos
	 *****************************************************************/
	/**
	 * Constructor por defecto
	 */
	public Almacenamiento() {
		this.id = 0;
		this.capacidadVolumen = 0;
		this.capacidadPeso = 0;
		this.existencias = 0;
		this.producto = 0;
		this.sucursal = 0;
		this.nivelAbastecimientoBodega = 0;
		this.capacidadProductos = 0;
		this.tipo = "";
	}

	/**
	 * Constructor con valores
	 * 
	 * @param id
	 *            - El id de la sucursal
	 * @param pCapacidadV
	 *            - la capacidad de volumen de la Almacenamiento (Mayor que 0).
	 * @param pCapacidadP
	 *            - la capacidad de peso de la Almacenamiento (Mayor que 0).
	 * @param pEx
	 *            - Las existencias del producto en la Almacenamiento (Mayor que 0).
	 * @param pPro
	 *            - El c�digo del producto que se almacena
	 * @param pSu
	 *            - El c�digo de la sucursal a la que pertenece la Almacenamiento.
	 * @param pniv
	 *            - Nivel de reabastecimiento de bodega.
	 */
	public Almacenamiento(long id, double pCapacidadV, double pCapacidadP, int pEx, long pPro, long pSu, int pNiv, int pCprod, String pTipo) {
		this.id = id;
		this.capacidadVolumen = pCapacidadV;
		this.capacidadPeso = pCapacidadP;
		this.existencias = pEx;
		this.producto = pPro;
		this.sucursal = pSu;
		this.nivelAbastecimientoBodega = pNiv;
		this.capacidadProductos = pCprod;
		this.tipo = pTipo;
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

	public int getNivelAbastecimientoBodega() {
		return nivelAbastecimientoBodega;
	}

	public void setNivelAbastecimientoBodega(int pNivel) {
		this.nivelAbastecimientoBodega = pNivel;
	}
	

	/**
	 * @return the capacidadProductos
	 */
	public int getCapacidadProductos() {
		return capacidadProductos;
	}

	/**
	 * @param capacidadProductos the capacidadProductos to set
	 */
	public void setCapacidadProductos(int capacidadProductos) {
		this.capacidadProductos = capacidadProductos;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos del almacenamiento
	 */
	public String toString() {
		return "Almacenamiento [id=" + id + ", capacidadVolumen=" + capacidadVolumen + ", capacidadPeso="
				+ capacidadPeso + ", existencias=" + existencias + ", producto=" + producto + ", sucursal=" + sucursal
				+ ", nivelAbastecimientoBodega=" + nivelAbastecimientoBodega + ", capacidadProductos="
				+ capacidadProductos + ", tipo=" + tipo + "]";
	}

}
