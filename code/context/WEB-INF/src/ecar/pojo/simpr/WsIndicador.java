package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsIndicador")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsIndicador {
	
	@XmlElement(required = true, nillable = false)
	private WsIndicadorCadastro dadosCadastro;

	@XmlElement(required = true, nillable = false)
	private WsIndicadorExibicao dadosCalculo;

	@XmlElement(required = true, nillable = false)
	private WsIndicadorAbrangencia dadosAbrangencia;

	@XmlElement(required = true, nillable = false)
	private WsIndicadorMetodologia dadosMetodologia;

	public WsIndicador() {
	}

	public WsIndicador(WsIndicadorCadastro dadosCadastro,
			WsIndicadorExibicao dadosCalculo,
			WsIndicadorAbrangencia dadosAbrangencia,
			WsIndicadorMetodologia dadosMetodologia) {
		this.dadosCadastro = dadosCadastro;
		this.dadosCalculo = dadosCalculo;
		this.dadosAbrangencia = dadosAbrangencia;
		this.dadosMetodologia = dadosMetodologia;
	}

	public WsIndicadorCadastro getDadosCadastro() {
		return dadosCadastro;
	}

	public void setDadosCadastro(WsIndicadorCadastro dadosCadastro) {
		this.dadosCadastro = dadosCadastro;
	}

	public WsIndicadorExibicao getDadosCalculo() {
		return dadosCalculo;
	}

	public void setDadosCalculo(WsIndicadorExibicao dadosCalculo) {
		this.dadosCalculo = dadosCalculo;
	}

	public WsIndicadorAbrangencia getDadosAbrangencia() {
		return dadosAbrangencia;
	}

	public void setDadosAbrangencia(WsIndicadorAbrangencia dadosAbrangencia) {
		this.dadosAbrangencia = dadosAbrangencia;
	}

	public WsIndicadorMetodologia getDadosMetodologia() {
		return dadosMetodologia;
	}

	public void setDadosMetodologia(WsIndicadorMetodologia dadosMetodologia) {
		this.dadosMetodologia = dadosMetodologia;
	}

}
