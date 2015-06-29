package ecar.servlet.relatorio.dto.marcas;

import java.util.ArrayList;
import java.util.List;

public class RedeCicloDTOCompleto {
	private String rede = "";
	private String eixo = "";
	private String acao = "";
	private String depResp = "";
	private String responsavel = "";
	private String parecer = "";
	private String situacao = "";
	private List<RedeCicloIndicadorDTO> listIndicadores = new ArrayList<RedeCicloIndicadorDTO>();
	private List<RedePlanoAcaoDTO> listPlanoAcao = new ArrayList<RedePlanoAcaoDTO>();
	public String getRede() {
		return rede;
	}
	public void setRede(String rede) {
		this.rede = rede;
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
	public String getDepResp() {
		return depResp;
	}
	public void setDepResp(String depResp) {
		this.depResp = depResp;
	}
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public String getParecer() {
		return parecer;
	}
	public void setParecer(String parecer) {
		this.parecer = parecer;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public List<RedeCicloIndicadorDTO> getListIndicadores() {
		return listIndicadores;
	}
	public void setListIndicadores(List<RedeCicloIndicadorDTO> listIndicadores) {
		this.listIndicadores = listIndicadores;
	}
	
	public void addIndicador(RedeCicloIndicadorDTO ind) {
		this.listIndicadores.add(ind);
	}
	public List<RedePlanoAcaoDTO> getListPlanoAcao() {
		return listPlanoAcao;
	}
	public void setListPlanoAcao(List<RedePlanoAcaoDTO> listPlanoAcao) {
		this.listPlanoAcao = listPlanoAcao;
	}
	public void addPlanoAcao(RedePlanoAcaoDTO plano) {
		this.listPlanoAcao.add(plano);
	}
}