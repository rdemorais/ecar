package ecar.pojo.simpr;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsAcaoTarefaCadastro")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsAcaoAtividadeCadastro {
	
	@XmlElement(required = true, nillable = false)
	private Integer chvAtividade;
	
	@XmlElement(required = true, nillable = false)
	private Integer chvAtividadeExterna;
	
	@XmlElement(required = true, nillable = false)
	private Integer tipoAtividade;
	
	@XmlElement(required = true, nillable = false)
	private String valorAlcancado;
	
	@XmlElement(required = true, nillable = false)
	private String nomeAtividade;
	
	@XmlElement(required = true, nillable = false)
	private Integer unidadeMedida;
	
	@XmlElement(required = true, nillable = false)
	private Double percExecutado;
	
	@XmlElement(required = true, nillable = false)
	private WsPessoaFisica gestorAtividade;
	
	@XmlElement(required = true, nillable = false)
	private String dataInicioAtividade;
	
	@XmlElement(required = true, nillable = false)
	private String dataTerminoAtividade;
	
	@XmlElement(required = true, nillable = false)
	private String dataHoraInicioReal;
	
	@XmlElement(required = true, nillable = false)
	private String dataHoraTerminoReal;
	
	@XmlElement(required = false, nillable = false)
	private String ordenacao;
	
	@XmlElement(required = true, nillable = false)
	private Integer situacaoAtividade;
	
	@XmlElement(required = true, nillable = false)
	private String justificativaAtividade;

	public WsAcaoAtividadeCadastro() {
	}
	
	public WsAcaoAtividadeCadastro(Long chvExterno, String tipoTarefaCod, 
			String nomeTarefa, String unidadeMedidaDuracaoCod, 
			String responsavelCpf, String responsavelNome,
			Date dataHoraInicioPrevisto, Date dataHoraTerminoPrevisto, String ordenacao, Long codSit) {
		this.chvAtividadeExterna = Integer.parseInt(chvExterno.toString());
		this.tipoAtividade = Integer.valueOf(tipoTarefaCod);
		this.nomeAtividade = nomeTarefa;
		this.unidadeMedida = Integer.valueOf(unidadeMedidaDuracaoCod);
		this.gestorAtividade = new WsPessoaFisica(responsavelCpf, responsavelNome);
		if(dataHoraInicioPrevisto != null) {
			this.dataInicioAtividade = dataHoraInicioPrevisto.toString();			
		}
		if(dataHoraTerminoPrevisto != null) {
			this.dataTerminoAtividade = dataHoraTerminoPrevisto.toString();			
		}
		this.ordenacao = ordenacao;
		this.percExecutado = 0.0;
		if(codSit != null) {
			this.situacaoAtividade = codSit.intValue();			
		}
	}

	public Integer getChvAtividade() {
		return chvAtividade;
	}

	public void setChvAtividade(Integer chvAtividade) {
		this.chvAtividade = chvAtividade;
	}

	public Integer getChvAtividadeExterna() {
		return chvAtividadeExterna;
	}

	public void setChvAtividadeExterna(Integer chvAtividadeExterna) {
		this.chvAtividadeExterna = chvAtividadeExterna;
	}

	public Integer getTipoAtividade() {
		return tipoAtividade;
	}

	public void setTipoAtividade(Integer tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}

	public String getValorAlcancado() {
		return valorAlcancado;
	}

	public void setValorAlcancado(String valorAlcancado) {
		this.valorAlcancado = valorAlcancado;
	}

	public String getNomeAtividade() {
		return nomeAtividade;
	}

	public void setNomeAtividade(String nomeAtividade) {
		this.nomeAtividade = nomeAtividade;
	}

	public Integer getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(Integer unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public Double getPercExecutado() {
		return percExecutado;
	}

	public void setPercExecutado(Double percExecutado) {
		this.percExecutado = percExecutado;
	}

	public WsPessoaFisica getGestorAtividade() {
		return gestorAtividade;
	}

	public void setGestorAtividade(WsPessoaFisica gestorAtividade) {
		this.gestorAtividade = gestorAtividade;
	}

	public String getDataInicioAtividade() {
		return dataInicioAtividade;
	}

	public void setDataInicioAtividade(String dataInicioAtividade) {
		this.dataInicioAtividade = dataInicioAtividade;
	}

	public String getDataTerminoAtividade() {
		return dataTerminoAtividade;
	}

	public void setDataTerminoAtividade(String dataTerminoAtividade) {
		this.dataTerminoAtividade = dataTerminoAtividade;
	}

	public String getDataHoraInicioReal() {
		return dataHoraInicioReal;
	}

	public void setDataHoraInicioReal(String dataHoraInicioReal) {
		this.dataHoraInicioReal = dataHoraInicioReal;
	}

	public String getDataHoraTerminoReal() {
		return dataHoraTerminoReal;
	}

	public void setDataHoraTerminoReal(String dataHoraTerminoReal) {
		this.dataHoraTerminoReal = dataHoraTerminoReal;
	}

	public String getOrdenacao() {
		return ordenacao;
	}

	public void setOrdenacao(String ordenacao) {
		this.ordenacao = ordenacao;
	}

	public Integer getSituacaoAtividade() {
		return situacaoAtividade;
	}

	public void setSituacaoAtividade(Integer situacaoAtividade) {
		this.situacaoAtividade = situacaoAtividade;
	}

	public String getJustificativaAtividade() {
		return justificativaAtividade;
	}

	public void setJustificativaAtividade(String justificativaAtividade) {
		this.justificativaAtividade = justificativaAtividade;
	}

	@Override
	public String toString() {
		return "WsAcaoAtividadeCadastro [chvAtividade=" + chvAtividade
				+ ", chvAtividadeExterna=" + chvAtividadeExterna
				+ ", tipoAtividade=" + tipoAtividade + ", valorAlcancado="
				+ valorAlcancado + ", nomeAtividade=" + nomeAtividade
				+ ", unidadeMedida=" + unidadeMedida + ", percExecutado="
				+ percExecutado + ", gestorAtividade=" + gestorAtividade
				+ ", dataInicioAtividade=" + dataInicioAtividade
				+ ", dataTerminoAtividade=" + dataTerminoAtividade
				+ ", dataHoraInicioReal=" + dataHoraInicioReal
				+ ", dataHoraTerminoReal=" + dataHoraTerminoReal
				+ ", ordenacao=" + ordenacao + ", situacaoAtividade="
				+ situacaoAtividade + ", justificativaAtividade="
				+ justificativaAtividade + "]";
	}
	
	public String toStringCSV() {
		StringBuffer s = new StringBuffer();
		//s.append("|");
		s.append(chvAtividadeExterna);
		s.append("|");
		s.append("|");
		s.append(nomeAtividade);
		s.append("|");
		s.append(gestorAtividade.getNome());
		s.append("|");
		s.append(dataInicioAtividade);
		s.append("|");
		s.append(dataTerminoAtividade);
		s.append("|");
		s.append(situacaoAtividade);
		return s.toString();
	}
}