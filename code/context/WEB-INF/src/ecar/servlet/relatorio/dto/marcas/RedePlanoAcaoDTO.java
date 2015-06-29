package ecar.servlet.relatorio.dto.marcas;

import java.util.ArrayList;
import java.util.List;

public class RedePlanoAcaoDTO {
	
	private String rede;
	private String objetivo;
	private String eixo;
	private String acao;
	private String respAcao;
	private String inicioAcao;
	private String terminoAcao;
	private String atividade;
	private String respAtividade;
	private String inicioAtividade;
	private String terminoAtividade;
	private String situacaoAtividade;
	private List<Integer> listMeses = new ArrayList<Integer>();
	
	public String getRede() {
		return rede;
	}
	public void setRede(String rede) {
		this.rede = rede;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public String getEixo() {
		return eixo;
	}
	public void setEixo(String eixo) {
		this.eixo = eixo;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public String getRespAcao() {
		return respAcao;
	}
	public void setRespAcao(String respAcao) {
		this.respAcao = respAcao;
	}
	public String getInicioAcao() {
		return inicioAcao;
	}
	public void setInicioAcao(String inicioAcao) {
		this.inicioAcao = inicioAcao;
	}
	public String getTerminoAcao() {
		return terminoAcao;
	}
	public void setTerminoAcao(String terminoAcao) {
		this.terminoAcao = terminoAcao;
	}
	public String getAtividade() {
		return atividade;
	}
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}
	public String getRespAtividade() {
		return respAtividade;
	}
	public void setRespAtividade(String respAtividade) {
		this.respAtividade = respAtividade;
	}
	public String getInicioAtividade() {
		return inicioAtividade;
	}
	public void setInicioAtividade(String inicioAtividade) {
		this.inicioAtividade = inicioAtividade;
	}
	public String getTerminoAtividade() {
		return terminoAtividade;
	}
	public void setTerminoAtividade(String terminoAtividade) {
		this.terminoAtividade = terminoAtividade;
	}
	public String getSituacaoAtividade() {
		return situacaoAtividade;
	}
	public void setSituacaoAtividade(String situacaoAtividade) {
		this.situacaoAtividade = situacaoAtividade;
	}
	public List<Integer> getListMeses() {
		return listMeses;
	}
	public void setListMeses(List<Integer> listMeses) {
		this.listMeses = listMeses;
	}
	public void addMes(Integer mes) {
		this.listMeses.add(mes);
	}
	
	@Override
	public String toString() {
		return this.getRede() + " " + this.getEixo() + " " + this.getAcao() + " " 
			+ this.getInicioAtividade() + " " + this.getAtividade().hashCode();
	}
}