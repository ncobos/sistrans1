package uniandes.isis2304.parranderos.negocio;

import java.sql.Timestamp;

/**
 * Clase que apoya la realización del requerimiento funcional de consulta RFC7
 * @author ncobos
 *
 */
public class AyudaRFC7 {
	
	private long mes;
	
	private long anio;
	
	private int cantidadmaxima;
	
	public AyudaRFC7(long pMes, long pAnio, int pMaxima)
	{
		mes = pMes;
		anio = pAnio;
		cantidadmaxima = pMaxima;
	}
	
	public AyudaRFC7()
	{
		mes = 0;
		anio = 0;
		cantidadmaxima = 0;
	}

	/**
	 * @return the mes
	 */
	public long getMes() {
		return mes;
	}

	/**
	 * @param mes the mes to set
	 */
	public void setMes(long mes) {
		this.mes = mes;
	}

	/**
	 * @return the anio
	 */
	public long getAnio() {
		return anio;
	}

	/**
	 * @param anio the anio to set
	 */
	public void setAnio(long anio) {
		this.anio = anio;
	}

	/**
	 * @return the cantidadmaxima
	 */
	public int getCantidadmaxima() {
		return cantidadmaxima;
	}

	/**
	 * @param cantidadmaxima the cantidadmaxima to set
	 */
	public void setCantidadmaxima(int cantidadmaxima) {
		this.cantidadmaxima = cantidadmaxima;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AyudaRFC7 [mes=" + mes + ", anio=" + anio + ", cantidadmaxima=" + cantidadmaxima + "]";
	}

	
}
