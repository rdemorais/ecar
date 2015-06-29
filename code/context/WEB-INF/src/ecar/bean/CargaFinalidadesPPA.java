package ecar.bean;


/**
 * Bean contendo as informações para Carga das Finalidades do PPA.<br>
 * 
 * @author aleixo
 */

public class CargaFinalidadesPPA {
	
	private String sigla;
	private String finalidade;
	
	/**
	 * @return the finalidade
	 */
	public String getFinalidade() {
		return finalidade;
	}
	/**
	 * @param finalidade the finalidade to set
	 */
	public void setFinalidade(String finalidade) {
		this.finalidade = finalidade;
	}
	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}
	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
}
