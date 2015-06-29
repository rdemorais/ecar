package ecar.pojo.simpr;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsAcaoTarefa")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)	
public class WsAcaoTarefa {
	
	@XmlElement(required = true, nillable = false)
	private WsAcaoAtividadeCadastro dadosCadastro;
	
	@XmlElement(required = true, nillable = false)
	private WsAcaoTarefaRecurso[] dadosRecursos;
	
	@XmlElement(required = true, nillable = false)
	private WsAcaoTarefaDependencia[] dadosDependencia;
	
	@XmlElement(required = true, nillable = false)
	private WsAcaoTarefa[] dadosTarefasSubordinadas;
	
	@XmlElement(required = true, nillable = false)
	private WsAcaoTarefaRisco[] dadosRisco;
	
	@XmlElement(required = true, nillable = false)
	private WsAcaoTarefaRestrProvid dadosRestricaoProvidencia;

	public WsAcaoTarefa() {
	}

	public WsAcaoTarefa(WsAcaoAtividadeCadastro dadosCadastro,
			WsAcaoTarefaRecurso[] dadosRecursos,
			WsAcaoTarefaDependencia[] dadosDependencia,
			WsAcaoTarefa[] dadosTarefasSubordinadas,
			WsAcaoTarefaRisco[] dadosRisco,
			WsAcaoTarefaRestrProvid dadosRestricaoProvidencia) {
		this.dadosCadastro = dadosCadastro;
		this.dadosRecursos = dadosRecursos;
		this.dadosDependencia = dadosDependencia;
		this.dadosTarefasSubordinadas = dadosTarefasSubordinadas;
		this.dadosRisco = dadosRisco;
		this.dadosRestricaoProvidencia = dadosRestricaoProvidencia;
	}

	public WsAcaoAtividadeCadastro getDadosCadastro() {
		return dadosCadastro;
	}

	public void setDadosCadastro(WsAcaoAtividadeCadastro dadosCadastro) {
		this.dadosCadastro = dadosCadastro;
	}

	public WsAcaoTarefaRecurso[] getDadosRecursos() {
		return dadosRecursos;
	}

	public void setDadosRecursos(WsAcaoTarefaRecurso[] dadosRecursos) {
		this.dadosRecursos = dadosRecursos;
	}

	public WsAcaoTarefaDependencia[] getDadosDependencia() {
		return dadosDependencia;
	}

	public void setDadosDependencia(WsAcaoTarefaDependencia[] dadosDependencia) {
		this.dadosDependencia = dadosDependencia;
	}

	public WsAcaoTarefa[] getDadosTarefasSubordinadas() {
		return dadosTarefasSubordinadas;
	}

	public void setDadosTarefasSubordinadas(
			WsAcaoTarefa[] dadosTarefasSubordinadas) {
		this.dadosTarefasSubordinadas = dadosTarefasSubordinadas;
	}

	public WsAcaoTarefaRisco[] getDadosRisco() {
		return dadosRisco;
	}

	public void setDadosRisco(WsAcaoTarefaRisco[] dadosRisco) {
		this.dadosRisco = dadosRisco;
	}

	public WsAcaoTarefaRestrProvid getDadosRestricaoProvidencia() {
		return dadosRestricaoProvidencia;
	}

	public void setDadosRestricaoProvidencia(
			WsAcaoTarefaRestrProvid dadosRestricaoProvidencia) {
		this.dadosRestricaoProvidencia = dadosRestricaoProvidencia;
	}

	@Override
	public String toString() {
		return "WsAcaoTarefa [dadosCadastro=" + dadosCadastro
				+ ", dadosRecursos=" + Arrays.toString(dadosRecursos)
				+ ", dadosDependencia=" + Arrays.toString(dadosDependencia)
				+ ", dadosTarefasSubordinadas="
				+ Arrays.toString(dadosTarefasSubordinadas) + ", dadosRisco="
				+ Arrays.toString(dadosRisco) + ", dadosRestricaoProvidencia="
				+ dadosRestricaoProvidencia + "]";
	}
}
