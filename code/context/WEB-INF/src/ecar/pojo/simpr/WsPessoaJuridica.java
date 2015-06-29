package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsPessoaJuridica")
@XmlRootElement		
@XmlAccessorType(XmlAccessType.FIELD)
public class WsPessoaJuridica {

	@XmlElement(required = true, nillable = false)
	private String numeroCnpj;

	@XmlElement(required = true, nillable = false)
	private String nomeEmpresa;

	@XmlElement(required = true, nillable = false)
	private String email;

	@XmlElement(required = true, nillable = false)
	private String telefoneComercial;

	@XmlElement(required = true, nillable = false)
	private String telefoneFax;

	@XmlElement(required = true, nillable = false)
	private WsDominio naturezaJuridica;

	@XmlElement(required = true, nillable = false)
	private WsDominio classificacao;

	@XmlElement(required = true, nillable = false)
	private String observacao;

	@XmlElement(required = true, nillable = false)
	private WsEndereco endereco;

	public WsPessoaJuridica() {
	}
	
	public WsPessoaJuridica(String numeroCnpj, String nomeEmpresa) {
		this.numeroCnpj = numeroCnpj;
		this.nomeEmpresa = nomeEmpresa;
	}

	public WsPessoaJuridica(String numeroCnpj, String nomeEmpresa,
			String email, String telefoneComercial, String telefoneFax,
			WsDominio naturezaJuridica, WsDominio classificacao,
			String observacao, WsEndereco endereco) {
		this.numeroCnpj = numeroCnpj;
		this.nomeEmpresa = nomeEmpresa;
		this.email = email;
		this.telefoneComercial = telefoneComercial;
		this.telefoneFax = telefoneFax;
		this.naturezaJuridica = naturezaJuridica;
		this.classificacao = classificacao;
		this.observacao = observacao;
		this.endereco = endereco;
	}
	
	public static WsPessoaJuridica newWsPessoaJuridica(String numeroCnpj, String nomeEmpresa) {
		return new WsPessoaJuridica(numeroCnpj, nomeEmpresa);
	}

	public String getNumeroCnpj() {
		return numeroCnpj;
	}

	public void setNumeroCnpj(String numeroCnpj) {
		this.numeroCnpj = numeroCnpj;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefoneComercial() {
		return telefoneComercial;
	}

	public void setTelefoneComercial(String telefoneComercial) {
		this.telefoneComercial = telefoneComercial;
	}

	public String getTelefoneFax() {
		return telefoneFax;
	}

	public void setTelefoneFax(String telefoneFax) {
		this.telefoneFax = telefoneFax;
	}

	public WsDominio getNaturezaJuridica() {
		return naturezaJuridica;
	}

	public void setNaturezaJuridica(WsDominio naturezaJuridica) {
		this.naturezaJuridica = naturezaJuridica;
	}

	public WsDominio getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(WsDominio classificacao) {
		this.classificacao = classificacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public WsEndereco getEndereco() {
		return endereco;
	}

	public void setEndereco(WsEndereco endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "WsPessoaJuridica [numeroCnpj=" + numeroCnpj + ", nomeEmpresa="
				+ nomeEmpresa + "]";
	}
}
