package ecar.pojo.simpr;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsAcaoAtividade {
	
	@XmlElement(required = true, nillable = false)
	private WsAcaoAtividadeCadastro dadosCadastro;
	
	@XmlElement(required = true, nillable = false)
	private WsAcaoAtividade[] dadosAtividadesSubordinadas;
	
	@XmlElement(required = true, nillable = false)
	private WsAcaoAtividadeDependencia[] dadosDependencia;
	
	@XmlElement(required = true, nillable = false)
	private WsAcaoAtividadeRestrProvid[] dadosRestricaoProvidencia;

	public WsAcaoAtividade() {
	}
	
	public WsAcaoAtividadeCadastro getDadosCadastro() {
		return dadosCadastro;
	}

	public void setDadosCadastro(WsAcaoAtividadeCadastro dadosCadastro) {
		this.dadosCadastro = dadosCadastro;
	}

	public WsAcaoAtividade[] getDadosAtividadesSubordinadas() {
		return dadosAtividadesSubordinadas;
	}

	public void setDadosAtividadesSubordinadas(
			WsAcaoAtividade[] dadosAtividadesSubordinadas) {
		this.dadosAtividadesSubordinadas = dadosAtividadesSubordinadas;
	}

	public WsAcaoAtividadeDependencia[] getDadosDependencia() {
		return dadosDependencia;
	}

	public void setDadosDependencia(
			WsAcaoAtividadeDependencia[] dadosDependencia) {
		this.dadosDependencia = dadosDependencia;
	}

	public WsAcaoAtividadeRestrProvid[] getDadosRestricaoProvidencia() {
		return dadosRestricaoProvidencia;
	}

	public void setDadosRestricaoProvidencia(
			WsAcaoAtividadeRestrProvid[] dadosRestricaoProvidencia) {
		this.dadosRestricaoProvidencia = dadosRestricaoProvidencia;
	}

	@Override
	public String toString() {
		return "WsAcaoAtividade [dadosCadastro=" + dadosCadastro
				+ ", dadosAtividadesSubordinadas="
				+ Arrays.toString(dadosAtividadesSubordinadas)
				+ ", dadosDependencia=" + Arrays.toString(dadosDependencia)
				+ ", dadosRestricaoProvidencia="
				+ Arrays.toString(dadosRestricaoProvidencia) + "]";
	}
}
