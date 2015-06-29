package ecar.intercambioDados.dto;


public class ItemEstruturaTXTDTO implements IBusinessObjectDTO {
	/**
	 * Identificador do registro
	 */
	private String identificador;
	/**
	 * Operação "M" ou "E".
	 * Manutenção ou exclusão
	 */
	private String operacao;
	/**
	 * Código do item no sistema origem.
	 */
	private String codigo;
	/**
	 * Nome do item no sistema origem.
	 */
	private String nome;
	/**
	 * Descrição do item no sistema origem.
	 */
	private String descricao;
	/**
	 * Data de início do item no sistema origem.
	 */
	private String dataInicio;
	/**
	 * Data de conclusão do item no sistema origem.
	 */
	private String dataConclusao;
	/**
	 * Órgão do item no sistema origem.
	 */
	private String orgao;
	/**
	 * Custo associado ao item no sistema origem.
	 */
	private String custo;
	/**
	 * Situação do item no sistema origem
	 */
	private String situacao;
	/**
	 * Tipo do item no sistema origem.
	 */
	private String tipo;
	/**
	 * Subtipo do item no sistema origem
	 */
	private String subTipo;
	/**
	 * Valor da associacao do item para comparação com item do e-car
	 */
	private String valorAssociacao;
	//
	//
	//
	/**
	 * Estes campos ainda não foram definidos pelo gestor. 
	 */
	private String executor;
	private String metaPac;
	private String investPrev20072010;
	private String investPrevApos2010;
	private String estagio;
	private String tipoEmpreendimento;
	//
	//
	//
	
	/**
	 * Sequencial do registro
	 */
	private String sequencial;
	
	/**
	 * Número da linha
	 */
	private int numeroLinha;

	/**
	 * Linha do arquivo
	 */
	private String linha;
	
	/**
	 * 
	 */
	private ItemEstruturaTrailerTXTDTO itemEstruturaTrailerDTO;

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
	 * @return the operacao
	 */
	public String getOperacao() {
		return operacao;
	}

	/**
	 * @param operacao the operacao to set
	 */
	public void setOperacao(String operacao) {
		this.operacao = operacao;
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
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return the dataInicio
	 */
	public String getDataInicio() {
		return dataInicio;
	}

	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the dataConclusao
	 */
	public String getDataConclusao() {
		return dataConclusao;
	}

	/**
	 * @param dataConclusao the dataConclusao to set
	 */
	public void setDataConclusao(String dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	/**
	 * @return the orgao
	 */
	public String getOrgao() {
		return orgao;
	}

	/**
	 * @param orgao the orgao to set
	 */
	public void setOrgao(String orgao) {
		this.orgao = orgao;
	}

	/**
	 * @return the custo
	 */
	public String getCusto() {
		return custo;
	}

	/**
	 * @param custo the custo to set
	 */
	public void setCusto(String custo) {
		this.custo = custo;
	}

	/**
	 * @return the situacao
	 */
	public String getSituacao() {
		return situacao;
	}

	/**
	 * @param situacao the situacao to set
	 */
	public void setSituacao(String situacao) {
		this.situacao = situacao;
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

	/**
	 * @return the subTipo
	 */
	public String getSubTipo() {
		return subTipo;
	}

	/**
	 * @param subTipo the subTipo to set
	 */
	public void setSubTipo(String subTipo) {
		this.subTipo = subTipo;
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
	 * @return the itemEstruturaTrailerDTO
	 */
	public ItemEstruturaTrailerTXTDTO getItemEstruturaTrailerDTO() {
		return itemEstruturaTrailerDTO;
	}

	/**
	 * @param itemEstruturaTrailerDTO the itemEstruturaTrailerDTO to set
	 */
	public void setItemEstruturaTrailerDTO(
			ItemEstruturaTrailerTXTDTO itemEstruturaTrailerDTO) {
		this.itemEstruturaTrailerDTO = itemEstruturaTrailerDTO;
	}

	/**
	 * @return the linha
	 */
	public int getNumeroLinha() {
		return numeroLinha;
	}

	/**
	 * @param linha the linha to set
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

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getMetaPac() {
		return metaPac;
	}

	public void setMetaPac(String metaPac) {
		this.metaPac = metaPac;
	}

	public String getInvestPrev20072010() {
		return investPrev20072010;
	}

	public void setInvestPrev20072010(String investPrev20072010) {
		this.investPrev20072010 = investPrev20072010;
	}

	public String getInvestPrevApos2010() {
		return investPrevApos2010;
	}

	public void setInvestPrevApos2010(String investPrevApos2010) {
		this.investPrevApos2010 = investPrevApos2010;
	}

	public String getEstagio() {
		return estagio;
	}

	public void setEstagio(String estagio) {
		this.estagio = estagio;
	}

	public String getTipoEmpreendimento() {
		return tipoEmpreendimento;
	}

	public void setTipoEmpreendimento(String tipoEmpreendimento) {
		this.tipoEmpreendimento = tipoEmpreendimento;
	}
	
	
}
