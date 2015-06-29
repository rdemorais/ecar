package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsAcaoOrcamentariaCadastro {
	
	@XmlElement(required = true, nillable = false)
	private Integer chvAcao;
	
	@XmlElement(required = true, nillable = false)
	private Integer anoExercicio;
	
	@XmlElement(required = true, nillable = false)
	private Integer codigoEsfera;
	
	@XmlElement(required = true, nillable = false)
	private String codigoFuncao;
	
	@XmlElement(required = true, nillable = false)
	private String codigoSubfuncao;
	
	@XmlElement(required = true, nillable = false)
	private String codigoPrograma;
	
	@XmlElement(required = true, nillable = false)
	private String codigoAcaoOrcamentaria;
	
	@XmlElement(required = true, nillable = false)
	private String descricaoAcaoOrcametaria;
	
	@XmlElement(required = true, nillable = false)
	private String codigoLocalizadorGasto;
	
	@XmlElement(required = true, nillable = false)
	private Float valorEmpenhado;
	
	@XmlElement(required = true, nillable = false)
	private Float valorLiquidado;
	
	@XmlElement(required = true, nillable = false)
	private Float valorPago;
	
	@XmlElement(required = true, nillable = false)
	private Float valorRapInscrito;
	
	@XmlElement(required = true, nillable = false)
	private Float valorRapAPagar;
	
	public WsAcaoOrcamentariaCadastro() {
	}
	public Integer getChvAcao() {
		return chvAcao;
	}

	public void setChvAcao(Integer chvAcao) {
		this.chvAcao = chvAcao;
	}

	public Integer getAnoExercicio() {
		return anoExercicio;
	}

	public void setAnoExercicio(Integer anoExercicio) {
		this.anoExercicio = anoExercicio;
	}

	public Integer getCodigoEsfera() {
		return codigoEsfera;
	}

	public void setCodigoEsfera(Integer codigoEsfera) {
		this.codigoEsfera = codigoEsfera;
	}

	public String getCodigoFuncao() {
		return codigoFuncao;
	}

	public void setCodigoFuncao(String codigoFuncao) {
		this.codigoFuncao = codigoFuncao;
	}

	public String getCodigoSubfuncao() {
		return codigoSubfuncao;
	}

	public void setCodigoSubfuncao(String codigoSubfuncao) {
		this.codigoSubfuncao = codigoSubfuncao;
	}

	public String getCodigoPrograma() {
		return codigoPrograma;
	}

	public void setCodigoPrograma(String codigoPrograma) {
		this.codigoPrograma = codigoPrograma;
	}

	public String getCodigoAcaoOrcamentaria() {
		return codigoAcaoOrcamentaria;
	}

	public void setCodigoAcaoOrcamentaria(String codigoAcaoOrcamentaria) {
		this.codigoAcaoOrcamentaria = codigoAcaoOrcamentaria;
	}

	public String getDescricaoAcaoOrcametaria() {
		return descricaoAcaoOrcametaria;
	}

	public void setDescricaoAcaoOrcametaria(String descricaoAcaoOrcametaria) {
		this.descricaoAcaoOrcametaria = descricaoAcaoOrcametaria;
	}

	public String getCodigoLocalizadorGasto() {
		return codigoLocalizadorGasto;
	}

	public void setCodigoLocalizadorGasto(String codigoLocalizadorGasto) {
		this.codigoLocalizadorGasto = codigoLocalizadorGasto;
	}

	public Float getValorEmpenhado() {
		return valorEmpenhado;
	}

	public void setValorEmpenhado(Float valorEmpenhado) {
		this.valorEmpenhado = valorEmpenhado;
	}

	public Float getValorLiquidado() {
		return valorLiquidado;
	}

	public void setValorLiquidado(Float valorLiquidado) {
		this.valorLiquidado = valorLiquidado;
	}

	public Float getValorPago() {
		return valorPago;
	}

	public void setValorPago(Float valorPago) {
		this.valorPago = valorPago;
	}

	public Float getValorRapInscrito() {
		return valorRapInscrito;
	}

	public void setValorRapInscrito(Float valorRapInscrito) {
		this.valorRapInscrito = valorRapInscrito;
	}

	public Float getValorRapAPagar() {
		return valorRapAPagar;
	}

	public void setValorRapAPagar(Float valorRapAPagar) {
		this.valorRapAPagar = valorRapAPagar;
	}

}
