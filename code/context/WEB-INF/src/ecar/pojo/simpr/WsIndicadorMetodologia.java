package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsIndicadorMetodologia")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsIndicadorMetodologia {

	@XmlElement(required = true, nillable = false)
	private WsDominio metodoCalculo;

	@XmlElement(required = true, nillable = false)
	private WsDominio formaAtualizacao;

	@XmlElement(required = true, nillable = false)
	private String formulaIndicador;

	@XmlElement(required = true, nillable = false)
	private String fonteInformacao;

	@XmlElement(required = true, nillable = false)
	private String criteriosIndicador;

	@XmlElement(required = true, nillable = false)
	private String restricoesIndicador;

	@XmlElement(required = true, nillable = false)
	private String sistemaFornecedor;

	public WsIndicadorMetodologia() {
	}

	public WsIndicadorMetodologia(WsDominio metodoCalculo,
			WsDominio formaAtualizacao, String formulaIndicador,
			String fonteInformacao, String criteriosIndicador,
			String restricoesIndicador, String sistemaFornecedor) {
		this.metodoCalculo = metodoCalculo;
		this.formaAtualizacao = formaAtualizacao;
		this.formulaIndicador = formulaIndicador;
		this.fonteInformacao = fonteInformacao;
		this.criteriosIndicador = criteriosIndicador;
		this.restricoesIndicador = restricoesIndicador;
		this.sistemaFornecedor = sistemaFornecedor;
	}

	public WsDominio getMetodoCalculo() {
		return metodoCalculo;
	}

	public void setMetodoCalculo(WsDominio metodoCalculo) {
		this.metodoCalculo = metodoCalculo;
	}

	public WsDominio getFormaAtualizacao() {
		return formaAtualizacao;
	}

	public void setFormaAtualizacao(WsDominio formaAtualizacao) {
		this.formaAtualizacao = formaAtualizacao;
	}

	public String getFormulaIndicador() {
		return formulaIndicador;
	}

	public void setFormulaIndicador(String formulaIndicador) {
		this.formulaIndicador = formulaIndicador;
	}

	public String getFonteInformacao() {
		return fonteInformacao;
	}

	public void setFonteInformacao(String fonteInformacao) {
		this.fonteInformacao = fonteInformacao;
	}

	public String getCriteriosIndicador() {
		return criteriosIndicador;
	}

	public void setCriteriosIndicador(String criteriosIndicador) {
		this.criteriosIndicador = criteriosIndicador;
	}

	public String getRestricoesIndicador() {
		return restricoesIndicador;
	}

	public void setRestricoesIndicador(String restricoesIndicador) {
		this.restricoesIndicador = restricoesIndicador;
	}

	public String getSistemaFornecedor() {
		return sistemaFornecedor;
	}

	public void setSistemaFornecedor(String sistemaFornecedor) {
		this.sistemaFornecedor = sistemaFornecedor;
	}

}
