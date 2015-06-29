package ecar.intercambioDados.dto;


public class ItemEstruturaTrailerTXTDTO implements IBusinessObjectDTO{

	/**
	 * Identificador do registro
	 */
	private String identificador;
	/**
	 * Código do item no sistema origem.
	 */
	private String codigo;
	/**
	 * Valor da associacao do item para comparação com item do e-car
	 */
	private String valorAssociacao;
	/**
	 * Valor to Tipo de Empreendimento do item para comparação com item do e-car
	 */
	private String tipoEmpreendimento;
	
	/**
	 * Sequencial do registro
	 */
	private String sequencial;
	/**
	 * Número da linha no arquivo
	 */
	private int numeroLinha;
	
	/**
	 * Linha do arquivo
	 */
	private String linha;
	
	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}
	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the valorAssociacao
	 */
	public String getValorAssociacao() {
		return valorAssociacao;
	}
	/**
	 * @param valorAssociacao the valorAssociacao to set
	 */
	public void setValorAssociacao(String valorAssociacao) {
		this.valorAssociacao = valorAssociacao;
	}
	public String getTipoEmpreendimento() {
		return tipoEmpreendimento;
	}
	public void setTipoEmpreendimento(String tipoEmpreendimento) {
		this.tipoEmpreendimento = tipoEmpreendimento;
	}
	/**
	 * @return the sequencial
	 */
	public String getSequencial() {
		return sequencial;
	}
	/**
	 * @param sequencial the sequencial to set
	 */
	public void setSequencial(String sequencial) {
		this.sequencial = sequencial;
	}
	/**
	 * @return the numeroLinha
	 */
	public int getNumeroLinha() {
		return numeroLinha;
	}
	/**
	 * @param numeroLinha the numeroLinha to set
	 */
	public void setNumeroLinha(int numeroLinha) {
		this.numeroLinha = numeroLinha;
	}
	/**
	 * @return the linha
	 */
	public String getLinha() {
		return linha;
	}
	/**
	 * @param linha the linha to set
	 */
	public void setLinha(String linha) {
		this.linha = linha;
	}
	
	

}
