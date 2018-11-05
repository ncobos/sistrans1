package uniandes.isis2304.parranderos.negocio;

/**
 * Clase que apoya la realización del requerimiento funcional de consulta RFC7
 * @author ncobos
 *
 */
public class AyudaRFC72 {

	private long mes;

	private long anio;

	private int costomaximo;
	
	public AyudaRFC72()
	{
		mes = 0;
		anio = 0;
		costomaximo = 0;
	}

	public AyudaRFC72(long pMes, long pAnio, int pMaximo)
	{
		mes = pMes;
		anio = pAnio;
		costomaximo = pMaximo;
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
	 * @return the costomaximo
	 */
	public int getCostomaximo() {
		return costomaximo;
	}

	/**
	 * @param costomaximo the costomaximo to set
	 */
	public void setCostomaximo(int costomaximo) {
		this.costomaximo = costomaximo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AyudaRFC72 [mes=" + mes + ", anio=" + anio + ", costomaximo=" + costomaximo + "]";
	}
}
