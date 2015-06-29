package ecar.bean;

public class DownloadArquivoBean {
	
	private String nomeArquivo;
	
	private byte[] conteudoArquivo;

	/**
	 * 
	 */
	public DownloadArquivoBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param nomeArquivo
	 * @param conteudoArquivo
	 */
	public DownloadArquivoBean(String nomeArquivo, byte[] conteudoArquivo) {
		super();
		this.nomeArquivo = nomeArquivo;
		this.conteudoArquivo = conteudoArquivo;
	}

	/**
	 * @return the nomeArquivo
	 */
	public String getNomeArquivo() {
		return nomeArquivo;
	}

	/**
	 * @param nomeArquivo the nomeArquivo to set
	 */
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	/**
	 * @return the conteudoArquivo
	 */
	public byte[] getConteudoArquivo() {
		return conteudoArquivo;
	}

	/**
	 * @param conteudoArquivo the conteudoArquivo to set
	 */
	public void setConteudoArquivo(byte[] conteudoArquivo) {
		this.conteudoArquivo = conteudoArquivo;
	}
}
