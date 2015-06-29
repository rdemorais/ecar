package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsEndereco")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsEndereco {
	
	@XmlElement(required = true, nillable = false)
	private String logradouro;

	@XmlElement(required = true, nillable = false)
	private String numero;

	@XmlElement(required = true, nillable = false)
	private String complemento;

	@XmlElement(required = true, nillable = false)
	private String bairro;

	@XmlElement(required = true, nillable = false)
	private String codigoMunicipio;

	@XmlElement(required = true, nillable = false)
	private String municipio;

	@XmlElement(required = true, nillable = false)
	private String uf;

	@XmlElement(required = true, nillable = false)
	private String cep;

	@XmlElement(required = true, nillable = false)
	private String featureGeografica;

	public WsEndereco() {
	}

	public WsEndereco(String logradouro, String numero, String complemento,
			String bairro, String codigoMunicipio, String municipio, String uf,
			String cep, String featureGeografica) {
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.codigoMunicipio = codigoMunicipio;
		this.municipio = municipio;
		this.uf = uf;
		this.cep = cep;
		this.featureGeografica = featureGeografica;
	}

	public java.lang.String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}

	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getFeatureGeografica() {
		return featureGeografica;
	}

	public void setFeatureGeografica(String featureGeografica) {
		this.featureGeografica = featureGeografica;
	}

}
