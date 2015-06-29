package ecar.pojo.simpr;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ecar.pojo.ItemEstruturaIett;

/**
 * Esta classe representa as a��es cadastradas no sistema. No m�dulo de Marcas elas podem ser encontradas em
 * Marca -> Eixo -> A��o
 * 		 		 -> Sub-Eixo -> A��o
 * 
 * O nome do Eixo, ou sub-eixo, s�o s�o importantes e quando for necess�rio seu envio, isso ser� sinalizado.
 * 
 * Os dois atributos desta classe, dadosCadastro e dadosTarefas, referen-se, respectivamente, aos dados cadastrais da A��o
 * e ao conjunto de tarefas imediatamente inferiores. � importante ressaltar que na estrutura configurada no eCar
 * Tarefa = Atividade.
 * 
 * Todos os dados ser�o extra�dos a partir da classe {@link ItemEstruturaIett}
 * 
 */
@XmlType(name = "wsAcao")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsAcao {
	
	/**
	 * Dados b�sicos da A��o
	 */
	@XmlElement(required = true, nillable = false)
	private WsAcaoCadastro dadosCadastro;

	@XmlElement(required = true, nillable = false)
	private WsAcaoAtividade[] dadosAtividades;
	
	@XmlElement(required = true, nillable = false)
	private WsAcaoOrcamentariaCadastro [] dadosOrcamentarios;

	public WsAcao() {
	}

	public WsAcao(WsAcaoCadastro dadosCadastro, WsAcaoAtividade[] dadosTarefas) {
		this.dadosCadastro = dadosCadastro;
		this.dadosAtividades = dadosTarefas;
	}

	public WsAcaoCadastro getDadosCadastro() {
		return dadosCadastro;
	}

	public void setDadosCadastro(WsAcaoCadastro dadosCadastro) {
		this.dadosCadastro = dadosCadastro;
	}

	@Override
	public String toString() {
		return "WsAcao [dadosCadastro=" + dadosCadastro + ", dadosAtividades="
				+ Arrays.toString(dadosAtividades) + ", dadosOrcamentarios="
				+ Arrays.toString(dadosOrcamentarios) + "]";
	}

	public WsAcaoAtividade[] getDadosAtividades() {
		return dadosAtividades;
	}

	public void setDadosAtividades(WsAcaoAtividade[] dadosAtividades) {
		this.dadosAtividades = dadosAtividades;
	}
	
	public WsAcaoOrcamentariaCadastro[] getDadosOrcamentarios() {
		return dadosOrcamentarios;
	}
	
	public void setDadosOrcamentarios(
			WsAcaoOrcamentariaCadastro[] dadosOrcamentarios) {
		this.dadosOrcamentarios = dadosOrcamentarios;
	}
	
}
