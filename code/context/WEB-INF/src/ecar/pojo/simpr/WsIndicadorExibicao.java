package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsIndicadorExibicao")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsIndicadorExibicao {

	@XmlElement(required = true, nillable = false)
	private WsDominio unidadeMedida;

	@XmlElement(required = true, nillable = false)
	private WsDominio tipoIndicador;

	@XmlElement(required = true, nillable = false)
	private WsDominio periodicidade;

	@XmlElement(required = true, nillable = false)
	private WsDominio polaridadeIndicador;

	@XmlElement(required = true, nillable = false)
	private WsDominio acumularDados;

	@XmlElement(required = true, nillable = false)
	private Float percentualMinimoIndicador;

	@XmlElement(required = true, nillable = false)
	private Float percentualMaximoIndicador;

	public WsIndicadorExibicao() {
	}

	public WsIndicadorExibicao(WsDominio unidadeMedida,
			WsDominio tipoIndicador, WsDominio periodicidade,
			WsDominio polaridadeIndicador, WsDominio acumularDados,
			Float percentualMinimoIndicador, Float percentualMaximoIndicador) {
		this.unidadeMedida = unidadeMedida;
		this.tipoIndicador = tipoIndicador;
		this.periodicidade = periodicidade;
		this.polaridadeIndicador = polaridadeIndicador;
		this.acumularDados = acumularDados;
		this.percentualMinimoIndicador = percentualMinimoIndicador;
		this.percentualMaximoIndicador = percentualMaximoIndicador;
	}

	public WsDominio getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(WsDominio unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public WsDominio getTipoIndicador() {
		return tipoIndicador;
	}

	public void setTipoIndicador(WsDominio tipoIndicador) {
		this.tipoIndicador = tipoIndicador;
	}

	public WsDominio getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(WsDominio periodicidade) {
		this.periodicidade = periodicidade;
	}

	public WsDominio getPolaridadeIndicador() {
		return polaridadeIndicador;
	}

	public void setPolaridadeIndicador(WsDominio polaridadeIndicador) {
		this.polaridadeIndicador = polaridadeIndicador;
	}

	public WsDominio getAcumularDados() {
		return acumularDados;
	}

	public void setAcumularDados(WsDominio acumularDados) {
		this.acumularDados = acumularDados;
	}

	public Float getPercentualMinimoIndicador() {
		return percentualMinimoIndicador;
	}

	public void setPercentualMinimoIndicador(Float percentualMinimoIndicador) {
		this.percentualMinimoIndicador = percentualMinimoIndicador;
	}

	public Float getPercentualMaximoIndicador() {
		return percentualMaximoIndicador;
	}

	public void setPercentualMaximoIndicador(Float percentualMaximoIndicador) {
		this.percentualMaximoIndicador = percentualMaximoIndicador;
	}

}
