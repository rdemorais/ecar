package ecar.servlet.relatorio.dto;

import java.util.ArrayList;
import java.util.List;

import ecar.servlet.relatorio.dto.marcas.RedeCicloIndicadorDTO;

/**
 * 
 * @author Rafael de Morais
 *
 */
/**
 * @author rafael
 *
 */
public class SituacaoProdutoDTO {
	private String objSigla;
	private String estSigla;
	private String prodSigla;
	private String prodNome;
	private String nap;
	private String cor1;
	private String cor2;
	private String cor3;
	private String orgSigla;
	private String periodicidade;
	private String situacao;
	private List<SituacaoProdutoIndicadorDTO> listIndicadores = new ArrayList<SituacaoProdutoIndicadorDTO>();
	private String listCoResponsavel;
	private String parecer;
	private String responsavel;
	private String prioritario;
	private List<RedeCicloIndicadorDTO> listIndicadoresPainel = new ArrayList<RedeCicloIndicadorDTO>();
	
	public String getObjSigla() {
		return objSigla;
	}
	public void setObjSigla(String objSigla) {
		this.objSigla = objSigla;
	}
	public String getEstSigla() {
		return estSigla;
	}
	public void setEstSigla(String estSigla) {
		this.estSigla = estSigla;
	}
	public String getProdSigla() {
		return prodSigla;
	}
	public void setProdSigla(String prodSigla) {
		this.prodSigla = prodSigla;
	}
	public String getProdNome() {
		return prodNome;
	}
	public void setProdNome(String prodNome) {
		this.prodNome = prodNome;
	}
	public String getCor1() {
		return cor1;
	}
	public void setCor1(String cor1) {
		this.cor1 = cor1;
	}
	public String getCor2() {
		return cor2;
	}
	public void setCor2(String cor2) {
		this.cor2 = cor2;
	}
	public String getCor3() {
		return cor3;
	}
	public void setCor3(String cor3) {
		this.cor3 = cor3;
	}
	public String getOrgSigla() {
		return orgSigla;
	}
	public void setOrgSigla(String orgSigla) {
		this.orgSigla = orgSigla;
	}
	public String getPeriodicidade() {
		return periodicidade;
	}
	public void setPeriodicidade(String periodicidade) {
		this.periodicidade = periodicidade;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	
	public List<SituacaoProdutoIndicadorDTO> getListIndicadores() {
		return listIndicadores;
	}
	
	public void setListIndicadores(
			List<SituacaoProdutoIndicadorDTO> listIndicadores) {
		this.listIndicadores = listIndicadores;
	}
	
	public void addIndicador(SituacaoProdutoIndicadorDTO indicador) {
		this.listIndicadores.add(indicador);
	}
	
	public String getListCoResponsavel() {
		return listCoResponsavel;
	}
	public void setListCoResponsavel(String listCoResponsavel) {
		this.listCoResponsavel = listCoResponsavel;
	}
	
	public String getNap() {
		return nap;
	}
	public void setNap(String nap) {
		this.nap = nap;
	}
	
	public String getParecer() {
		return parecer;
	}
	public void setParecer(String parecer) {
		this.parecer = parecer;
	}
	
	public String getResponsavel() {
		return responsavel;
	}
	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}
	public void addIndicadorPainel(RedeCicloIndicadorDTO ind) {
		listIndicadoresPainel.add(ind);
	}
	public String getPrioritario() {
		return prioritario;
	}
	public void setPrioritario(String prioritario) {
		this.prioritario = prioritario;
	}
	public List<RedeCicloIndicadorDTO> getListIndicadoresPainel() {
		return listIndicadoresPainel;
	}
	public void setListIndicadoresPainel(
			List<RedeCicloIndicadorDTO> listIndicadoresPainel) {
		this.listIndicadoresPainel = listIndicadoresPainel;
	}
	@Override
	public String toString() {
		return "SituacaoProdutoDTO [estSigla=" + estSigla
				+ ", listCoResponsavel=" + listCoResponsavel
				+ ", listIndicadores=" + listIndicadores + ", objSigla="
				+ objSigla + ", orgSigla=" + orgSigla + ", periodicidade="
				+ periodicidade + ", prodNome=" + prodNome + ", prodSigla="
				+ prodSigla + ", situacao=" + situacao + "]";
	}
	
}