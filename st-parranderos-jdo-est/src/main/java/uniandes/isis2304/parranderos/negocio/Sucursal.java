package uniandes.isis2304.parranderos.negocio;

/**
 * Clase para modelar el concepto Bodega del negocio de SuperAndes
 *
 * @author n.cobos, jf.torresp
 */

public class Sucursal implements VOSucursal {
	/* ****************************************************************
	 * 			Atributos
	 *****************************************************************/
	
	/**
	 * El identificador �nico de las sucursales
	 */
	private long id;
	
	/**
	 * El nombre de la surcursal
	 */
	private String nombre;

	/**
	 * La ciudad donde se encuentra la sucursal
	 */
	private String ciudad;
	
	/**
	 * La direccion de la sucursal
	 */
	private String direccion;
	
	/**
	 * El segmento de mercado de la sucursal
	 */
	private String segmentoMercado;
	
	/**
	 * El tamano de la sucursal en metros cuadrados
	 */
	private double tamano;
	
	/**
	 * El nombre del supermercado al que pertence la sucursal
	 */
	private String superMercado;
	
	/* ****************************************************************
	 * 			Métodos 
	 *****************************************************************/
	/**
     * Constructor por defecto
     */
	public Sucursal() 
    {
    	this.id = 0;
		this.nombre = "";
		this.ciudad = "";
		this.direccion = "";
		this.segmentoMercado = "";
		this.tamano = 0;
		this.superMercado = "";
	}

	/**
	 * Constructor con valores
	 * @param id - El id de la sucursal
	 * @param nombre - El nombre de la sucursal
	 * @param ciudad - La ciudad de la sucursal
	 * @param direccion - La direccion de la sucursal
	 * @param segmentoMercado - El segmento de mercado de la sucursal
	 * @param tamano - El tamano del bar (mayor que 0)
	 * @param superMercado - El supermercado al que pertence la sucursal
	 */
    public Sucursal(long id, String nombre, String ciudad, String direccion, String segmentoMercado, int tamano, String superMercado) 
    {
    		this.id = id;
		this.nombre = nombre;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.segmentoMercado = segmentoMercado;
		this.tamano = tamano;
		this.superMercado = superMercado;
	}

    /**
	 * @return El id de la sucursal
	 */
	@Override
	public long getId() {
		return id;
	}
	
	/**
	 * @param id - El nuevo id de la sucursal
	 */
	public void setId(long id) 
	{
		this.id = id;
	}

    /**
	 * @return El nombre de la sucursal
	 */
	@Override
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * @param nombre - El nuevo nombre de la sucursal
	 */
	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

    /**
	 * @return La ciudad de la sucursal
	 */
	@Override
	public String getCiudad() {
		return ciudad;
	}
	
	/**
	 * @param ciudad - La nueva ciudad de la sucursal
	 */
	public void setCiudad(String ciudad) 
	{
		this.ciudad = ciudad;
	}

    /**
	 * @return La direccion de la sucursal
	 */
	@Override
	public String getDireccion() {
		return direccion;
	}
	
	/**
	 * @param direccion - La nueva direccion de la sucursal
	 */
	public void setDireccion(String direccion) 
	{
		this.direccion = direccion;
	}

    /**
	 * @return El segmento de mercado de la sucursal
	 */
	@Override
	public String getSegmentoMercado() {
		return segmentoMercado;
	}
	
	/**
	 * @param segmentoMercado - El nuevo segmentoMercado de la sucursal
	 */
	public void setSegmentoMercado(String segmentoMercado) 
	{
		this.segmentoMercado = segmentoMercado;
	}

    /**
	 * @return El tamano de la sucursal
	 */
	@Override
	public double getTamano() {
		return tamano;
	}
	
	/**
	 * @param tamano - El nuevo tamano de la sucursal
	 */
	public void setTamano(int tamano) 
	{
		this.tamano = tamano;
	}

    /**
	 * @return El supermercado al que pertence la sucursal
	 */
	@Override
	public String getSupermercado() {
		return superMercado;
	}
	
	@Override
	/**
	 * @return Una cadena de caracteres con todos los atributos de la sucursal
	 */
	public String toString() 
	{
		return "Sucursal [id=" + id + ", nombre=" + nombre + ", ciudad=" + ciudad + ", direccion=" + direccion
				+ ",segmentoMercado=" + segmentoMercado + ", tamano=" + tamano + ",superMercado=" + superMercado + "]";
	}
}
