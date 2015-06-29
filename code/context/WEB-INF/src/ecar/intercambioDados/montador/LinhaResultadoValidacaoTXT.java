package ecar.intercambioDados.montador;

import ecar.pojo.intercambioDados.MotivoRejeicaoMtr;


public class LinhaResultadoValidacaoTXT extends LinhaResultadoValidacao {
	/**
	 * Identificador do registro
	 */
	private String identificador;

	/**
	 * Sequencial
	 */
	private String sequencial;
	
	/**
	 * Operação "M" ou "E".
	 * Manutenção ou exclusão
	 */
	private String operacao;
		
	/**
	 * Número da linha
	 */
	private int numeroLinha;
	
	
	/**
	 * Informa se o resultado é válido ou não
	 */
	
	private boolean ehValido;

	
	/**
	 * Motivo rejeicao
	 */
	private MotivoRejeicaoMtr motivo;
	
	/**
	 * Linha do arquivo
	 */
	private String linha;

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public String getSequencial() {
		return sequencial;
	}

	public void setSequencial(String sequencial) {
		this.sequencial = sequencial;
	}

	public int getNumeroLinha() {
		return numeroLinha;
	}

	public void setNumeroLinha(int numeroLinha) {
		this.numeroLinha = numeroLinha;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}

	public boolean isEhValido() {
		return ehValido;
	}

	public void setEhValido(boolean ehValido) {
		this.ehValido = ehValido;
	}

	public MotivoRejeicaoMtr getMotivo() {
		return motivo;
	}

	public void setMotivo(MotivoRejeicaoMtr motivo) {
		this.motivo = motivo;
	}	
	
}
