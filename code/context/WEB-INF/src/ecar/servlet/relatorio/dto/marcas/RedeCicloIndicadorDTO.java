package ecar.servlet.relatorio.dto.marcas;

import java.util.Comparator;

public class RedeCicloIndicadorDTO implements Comparator<RedeCicloIndicadorDTO> {
	private String codInd = "";
	private String rede = "";
	private String eixo = "";
	private String acao = "";
	private String tipoIndicador = "";
	private String nomeIndicador = "";
	private String unidade = "";
	private String sitInd = "";
	private String linhaBase = "";
	private String acumula = "";
	private String conceituacao = "";
	private String fonte = "";
	private int ordem = -1;
	private String ind_prev_1 = "";
	private String ind_prev_2 = "";
	private String ind_prev_3 = "";
	private String ind_real_1 = "";
	private String ind_real_2 = "";
	private String ind_real_3 = "";
	private String meta;
	private String periodicidade;
	
	public String getCodInd() {
		return codInd;
	}
	public void setCodInd(String codInd) {
		this.codInd = codInd;
	}
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
	public String getTipoIndicador() {
		return tipoIndicador;
	}
	public void setTipoIndicador(String tipoIndicador) {
		this.tipoIndicador = tipoIndicador;
	}
	public String getNomeIndicador() {
		return nomeIndicador;
	}
	public void setNomeIndicador(String nomeIndicador) {
		this.nomeIndicador = nomeIndicador;
	}
	public String getUnidade() {
		return unidade;
	}
	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}
	public String getSitInd() {
		return sitInd;
	}
	public void setSitInd(String sitInd) {
		this.sitInd = sitInd;
	}
	public String getLinhaBase() {
		return linhaBase;
	}
	public void setLinhaBase(String linhaBase) {
		this.linhaBase = linhaBase;
	}
	public String getInd_prev_1() {
		return ind_prev_1;
	}
	public void setInd_prev_1(String indPrev_1) {
		ind_prev_1 = indPrev_1;
	}
	public String getInd_prev_2() {
		return ind_prev_2;
	}
	public void setInd_prev_2(String indPrev_2) {
		ind_prev_2 = indPrev_2;
	}
	public String getInd_prev_3() {
		return ind_prev_3;
	}
	public void setInd_prev_3(String indPrev_3) {
		ind_prev_3 = indPrev_3;
	}
	public String getInd_real_1() {
		return ind_real_1;
	}
	public void setInd_real_1(String indReal_1) {
		ind_real_1 = indReal_1;
	}
	public String getInd_real_2() {
		return ind_real_2;
	}
	public void setInd_real_2(String indReal_2) {
		ind_real_2 = indReal_2;
	}
	public String getInd_real_3() {
		return ind_real_3;
	}
	public void setInd_real_3(String indReal_3) {
		ind_real_3 = indReal_3;
	}
	public String getAcumula() {
		return acumula;
	}
	public void setAcumula(String acumula) {
		this.acumula = acumula;
	}
	public int getOrdem() {
		return ordem;
	}
	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}
	public String getConceituacao() {
		return conceituacao;
	}
	public void setConceituacao(String conceituacao) {
		this.conceituacao = conceituacao;
	}
	public String getFonte() {
		return fonte;
	}
	public void setFonte(String fonte) {
		this.fonte = fonte;
	}
	public String getMeta() {
		return meta;
	}
	public void setMeta(String meta) {
		this.meta = meta;
	}
	public String getPeriodicidade() {
		return periodicidade;
	}
	public void setPeriodicidade(String periodicidade) {
		this.periodicidade = periodicidade;
	}
	public int compare(RedeCicloIndicadorDTO o1, RedeCicloIndicadorDTO o2) {
		if(o1.getTipoIndicador().equals(o2.getTipoIndicador())) {
			return 0;
		}
		return 1;
	}
	@Override
	public boolean equals(Object obj) {
		RedeCicloIndicadorDTO ind = (RedeCicloIndicadorDTO) obj;
		if(this.getNomeIndicador().equals(ind.getNomeIndicador())) {
			return true;
		}
		return false;
	}
}