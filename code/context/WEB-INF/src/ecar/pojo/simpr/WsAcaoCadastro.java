package ecar.pojo.simpr;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ecar.pojo.ItemEstruturaIett;

/**
 * Representa os dados b�sicos da A��o 
 */
//@XmlType(name = "wsAcaoCadastro")
@XmlRootElement		
@XmlAccessorType(XmlAccessType.FIELD)
public class WsAcaoCadastro {
	
	/**
	 * Chave no SimPr. Valor NULL
	 */
	@XmlElement(required = true, nillable = false)
	private Integer chvAcao;
	
	/**
	 * Chave do eCar. Valor: {@link ItemEstruturaIett.codIett}
	 * 
	 * Observar que este campo � Long e aqui deve ser convertido para Integer.
	 */
	@XmlElement(required = true, nillable = false)
	private Integer chvExterno;
	
	@XmlElement(required = true, nillable = false)
	private Integer chvAcaoExterna;
	
	/**
	 * PR ficou de enviar o c�digo
	 */
	@XmlElement(required = true, nillable = false)
	private WsDominio areaTematica;
	
	/**
	 * C�digo do projeto cadastrado no SimPr. PR ficou de enviar os c�digos
	 */
	@XmlElement(required = true, nillable = false)
	private WsDominio projeto;
	
	/**
	 * C�digo do projeto cadastrado no SimPr. PR ficou de enviar os c�digos
	 */
	@XmlElement(required = true, nillable = false)
	private WsDominio eixo;
	
	/**
	 * Valor: {@link ItemEstruturaIett.nomeIett}
	 */
	@XmlElement(required = true, nillable = false)
	private String nomeAcao;
	
	/**
	 * Valor: {@link ItemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett}
	 */
	@XmlElement(required = true, nillable = false)
	private WsPessoaJuridica orgaoResponsavel;
	
	@XmlElement(required = true, nillable = false)
	private WsPessoaFisica gestorAcao;
	
	@XmlElement(required = true, nillable = false)
	private WsPessoaFisica gestorSuplenteAcao;
	
	@XmlElement(required = true, nillable = false)
	private WsPessoaFisica monitorAcao;
	
	@XmlElement(required = true, nillable = false)
	private WsPessoaFisica monitorSuplenteAcao;
	
	@XmlElement(required = true, nillable = false)
	private String dataInicioAcao;
	
	@XmlElement(required = true, nillable = false)
	private String dataTerminoAcao;
	
	@XmlElement(required = true, nillable = false)
	private Integer situacaoAcao;
	
	@XmlElement(required = true, nillable = false)
	private Integer prioridadeAcao;
	
	@XmlElement(required = true, nillable = false)
	private Integer percExecucaoAcao;
	
	@XmlElement(required = true, nillable = false)
	private WsDominio tipoAcao;
	
	@XmlElement(required = true, nillable = false)
	private Float valorOrcamentario;
	
	@XmlElement(required = true, nillable = false)
	private String populacaoAlvoAcao;
	
	@XmlElement(required = true, nillable = false)
	private String objetivoAcao;
	
	@XmlElement(required = true, nillable = false)
	private String planoInterno;
	
	@XmlElement(required = true, nillable = false)
	private String unidadeOrcamentaria;
	
	@XmlElement(required = true, nillable = false)
	private Integer pesoAcao;
	
	@XmlElement(required = true, nillable = false)
	private String urlCameraMonitoramento;
	
	@XmlElement(required = true, nillable = false)
	private boolean ativo;

	public WsAcaoCadastro() {
	}
	
	public WsAcaoCadastro(Long chvExterno, String codProj, String descProj, String codEixo, String descEixo,
			String nomeAcao, String numeroCnpj, String nomeEmpresa,
			Date dataInicioAcao, Date dataTerminoAcao, Long codSituacao, String descricaoSituacao,
			String numeroCpfGerenteAcao, String nomeGerenteAcao, String indAtivo) {
		this.chvExterno = Integer.parseInt(chvExterno.toString());
		this.areaTematica = new WsDominio(4, "Melhoria da saude da populacao");
		this.projeto = new WsDominio(new Integer(codProj), descProj);
		this.eixo = new WsDominio(new Integer(codEixo), descEixo);
		this.nomeAcao = nomeAcao;
		this.orgaoResponsavel = new WsPessoaJuridica(numeroCnpj, nomeEmpresa);
		if(dataInicioAcao != null) {
			this.dataInicioAcao = dataInicioAcao.toString();
		}
		if(dataTerminoAcao != null) {
			this.dataTerminoAcao = dataTerminoAcao.toString();
		}
		this.situacaoAcao = codSituacao.intValue();
		this.gestorAcao = new WsPessoaFisica(numeroCpfGerenteAcao, nomeGerenteAcao);
		//this.gestorSuplenteAcao = this.gestorAcao;
		if(indAtivo.equals("S")) {
			ativo = true;
		}else {
			ativo = false;
		}
	}

	public WsAcaoCadastro(Integer chvAcao, Integer chvExterno,
			WsDominio areaTematica, WsDominio projeto, WsDominio eixo,
			String nomeAcao, WsPessoaJuridica orgaoResponsavel,
			WsPessoaFisica gestorAcao, WsPessoaFisica gestorSuplenteAcao,
			WsPessoaFisica monitorAcao, WsPessoaFisica monitorSuplenteAcao,
			String dataInicioAcao, String dataTerminoAcao,
			Integer situacaoAcao, Integer prioridadeAcao,
			Integer percExecucaoAcao, WsDominio tipoAcao,
			Float valorOrcamentario, String populacaoAlvoAcao,
			String objetivoAcao) {
		this.chvAcao = chvAcao;
		this.chvExterno = chvExterno;
		this.areaTematica = areaTematica;
		this.projeto = projeto;
		this.eixo = eixo;
		this.nomeAcao = nomeAcao;
		this.orgaoResponsavel = orgaoResponsavel;
		this.gestorAcao = gestorAcao;
		//this.gestorSuplenteAcao = gestorSuplenteAcao;
		this.monitorAcao = monitorAcao;
		//this.monitorSuplenteAcao = monitorSuplenteAcao;
		this.dataInicioAcao = dataInicioAcao;
		this.dataTerminoAcao = dataTerminoAcao;
		this.situacaoAcao = situacaoAcao;
		this.prioridadeAcao = prioridadeAcao;
		this.percExecucaoAcao = percExecucaoAcao;
		this.tipoAcao = tipoAcao;
		this.valorOrcamentario = valorOrcamentario;
		this.populacaoAlvoAcao = populacaoAlvoAcao;
		this.objetivoAcao = objetivoAcao;
	}

	public Integer getChvAcao() {
		return chvAcao;
	}

	public void setChvAcao(Integer chvAcao) {
		this.chvAcao = chvAcao;
	}

	public Integer getChvExterno() {
		return chvExterno;
	}

	public void setChvExterno(Integer chvExterno) {
		this.chvExterno = chvExterno;
	}

	public WsDominio getAreaTematica() {
		return areaTematica;
	}

	public void setAreaTematica(WsDominio areaTematica) {
		this.areaTematica = areaTematica;
	}

	public WsDominio getProjeto() {
		return projeto;
	}

	public void setProjeto(WsDominio projeto) {
		this.projeto = projeto;
	}

	public WsDominio getEixo() {
		return eixo;
	}

	public void setEixo(WsDominio eixo) {
		this.eixo = eixo;
	}

	public String getNomeAcao() {
		return nomeAcao;
	}

	public void setNomeAcao(String nomeAcao) {
		this.nomeAcao = nomeAcao;
	}

	public WsPessoaJuridica getOrgaoResponsavel() {
		return orgaoResponsavel;
	}

	public void setOrgaoResponsavel(WsPessoaJuridica orgaoResponsavel) {
		this.orgaoResponsavel = orgaoResponsavel;
	}

	public WsPessoaFisica getGestorAcao() {
		return gestorAcao;
	}

	public void setGestorAcao(WsPessoaFisica gestorAcao) {
		this.gestorAcao = gestorAcao;
	}

	public WsPessoaFisica getGestorSuplenteAcao() {
		return gestorSuplenteAcao;
	}

	public void setGestorSuplenteAcao(WsPessoaFisica gestorSuplenteAcao) {
		this.gestorSuplenteAcao = gestorSuplenteAcao;
	}

	public WsPessoaFisica getMonitorAcao() {
		return monitorAcao;
	}

	public void setMonitorAcao(WsPessoaFisica monitorAcao) {
		this.monitorAcao = monitorAcao;
	}

	public WsPessoaFisica getMonitorSuplenteAcao() {
		return monitorSuplenteAcao;
	}

	public void setMonitorSuplenteAcao(WsPessoaFisica monitorSuplenteAcao) {
		this.monitorSuplenteAcao = monitorSuplenteAcao;
	}

	public String getDataInicioAcao() {
		return dataInicioAcao;
	}

	public void setDataInicioAcao(String dataInicioAcao) {
		this.dataInicioAcao = dataInicioAcao;
	}

	public String getDataTerminoAcao() {
		return dataTerminoAcao;
	}

	public void setDataTerminoAcao(String dataTerminoAcao) {
		this.dataTerminoAcao = dataTerminoAcao;
	}

//	public WsDominio getSituacaoAcao() {
//		return situacaoAcao;
//	}
//
//	public void setSituacaoAcao(WsDominio situacaoAcao) {
//		this.situacaoAcao = situacaoAcao;
//	}

	public Integer getPrioridadeAcao() {
		return prioridadeAcao;
	}

	public void setPrioridadeAcao(Integer prioridadeAcao) {
		this.prioridadeAcao = prioridadeAcao;
	}

	public Integer getPercExecucaoAcao() {
		return percExecucaoAcao;
	}

	public void setPercExecucaoAcao(Integer percExecucaoAcao) {
		this.percExecucaoAcao = percExecucaoAcao;
	}

	public WsDominio getTipoAcao() {
		return tipoAcao;
	}

	public void setTipoAcao(WsDominio tipoAcao) {
		this.tipoAcao = tipoAcao;
	}

	public Float getValorOrcamentario() {
		return valorOrcamentario;
	}

	public void setValorOrcamentario(Float valorOrcamentario) {
		this.valorOrcamentario = valorOrcamentario;
	}

	public String getPopulacaoAlvoAcao() {
		return populacaoAlvoAcao;
	}

	public void setPopulacaoAlvoAcao(String populacaoAlvoAcao) {
		this.populacaoAlvoAcao = populacaoAlvoAcao;
	}

	public String getObjetivoAcao() {
		return objetivoAcao;
	}

	public void setObjetivoAcao(String objetivoAcao) {
		this.objetivoAcao = objetivoAcao;
	}
	
	public Integer getChvAcaoExterna() {
		return chvAcaoExterna;
	}
	
	public void setChvAcaoExterna(Integer chvAcaoExterna) {
		this.chvAcaoExterna = chvAcaoExterna;
	}
	
	public String getPlanoInterno() {
		return planoInterno;
	}
	
	public void setPlanoInterno(String planoInterno) {
		this.planoInterno = planoInterno;
	}

	public Integer getSituacaoAcao() {
		return situacaoAcao;
	}

	public void setSituacaoAcao(Integer situacaoAcao) {
		this.situacaoAcao = situacaoAcao;
	}

	public String getUnidadeOrcamentaria() {
		return unidadeOrcamentaria;
	}

	public void setUnidadeOrcamentaria(String unidadeOrcamentaria) {
		this.unidadeOrcamentaria = unidadeOrcamentaria;
	}

	public Integer getPesoAcao() {
		return pesoAcao;
	}

	public void setPesoAcao(Integer pesoAcao) {
		this.pesoAcao = pesoAcao;
	}

	public String getUrlCameraMonitoramento() {
		return urlCameraMonitoramento;
	}

	public void setUrlCameraMonitoramento(String urlCameraMonitoramento) {
		this.urlCameraMonitoramento = urlCameraMonitoramento;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "WsAcaoCadastro [chvAcao=" + chvAcao + ", chvExterno=" + chvExterno
				+ ", areaTematica=" + areaTematica + ", projeto=" + projeto
				+ ", eixo=" + eixo + ", nomeAcao=" + nomeAcao
				+ ", orgaoResponsavel=" + orgaoResponsavel + ", gerenteAcao="
				+ gestorAcao + ", gerenteSuplenteAcao=" + gestorSuplenteAcao
				+ ", monitorAcao=" + monitorAcao + ", monitorSuplenteAcao="
				+ monitorSuplenteAcao + ", dataInicioAcao=" + dataInicioAcao
				+ ", dataTerminoAcao=" + dataTerminoAcao + ", situacaoAcao="
				+ situacaoAcao + ", prioridadeAcao=" + prioridadeAcao
				+ ", percExecucaoAcao=" + percExecucaoAcao + ", tipoAcao="
				+ tipoAcao + ", valorOrcamentario=" + valorOrcamentario
				+ ", populacaoAlvoAcao=" + populacaoAlvoAcao
				+ ", objetivoAcao=" + objetivoAcao + ", ativo="+ ativo +"]";
	}
	
	public String toStringCSV() {
		StringBuffer s = new StringBuffer();
		//s.append("|");
		s.append(chvExterno);
		s.append("|");
		s.append(projeto.getDescricao());
		s.append(" - ");
		s.append(eixo.getDescricao());
		s.append("|");
		s.append(nomeAcao);
		s.append("|");
		s.append(gestorAcao.getNome());
		s.append("|");
		s.append(dataInicioAcao);
		s.append("|");
		s.append(dataTerminoAcao);
		s.append("|");
		s.append(ativo);
		
		return s.toString();
	}
}
