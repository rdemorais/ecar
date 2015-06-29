package ecar.pojo.simpr;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlType(name = "wsPessoaFisica")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class WsPessoaFisica {
	@XmlElement(required = true, nillable = false)
	private String numeroCpf;

	@XmlElement(required = true, nillable = false)
	private String nome;

	@XmlElement(required = true, nillable = false)
	private String dataNascimento;

	@XmlElement(required = true, nillable = false)
	private String numeroRg;

	@XmlElement(required = true, nillable = false)
	private String orgaoExpedidor;

	@XmlElement(required = true, nillable = false)
	private String sexo;

	@XmlElement(required = true, nillable = false)
	private String email;

	@XmlElement(required = true, nillable = false)
	private String telefoneResidencial;

	@XmlElement(required = true, nillable = false)
	private String telefoneComercial;

	@XmlElement(required = true, nillable = false)
	private String telefoneFax;

	@XmlElement(required = true, nillable = false)
	private String telefoneCelular;

	@XmlElement(required = true, nillable = false)
	private String observacao;

	@XmlElement(required = true, nillable = false)
	private WsEndereco endereco;

	public WsPessoaFisica() {
	}

	public WsPessoaFisica(String numeroCpf, String nome) {
		this.numeroCpf = numeroCpf;
		this.nome = nome;
	}
	
	public WsPessoaFisica(String numeroCpf, String nome, String dataNascimento,
			String numeroRg, String orgaoExpedidor, String sexo, String email,
			String telefoneResidencial, String telefoneComercial,
			String telefoneFax, String telefoneCelular, String observacao,
			WsEndereco endereco) {
		this.numeroCpf = numeroCpf;
		this.nome = nome;
		this.dataNascimento = dataNascimento;
		this.numeroRg = numeroRg;
		this.orgaoExpedidor = orgaoExpedidor;
		this.sexo = sexo;
		this.email = email;
		this.telefoneResidencial = telefoneResidencial;
		this.telefoneComercial = telefoneComercial;
		this.telefoneFax = telefoneFax;
		this.telefoneCelular = telefoneCelular;
		this.observacao = observacao;
		this.endereco = endereco;
	}
	
	public static WsPessoaFisica newWsPessoaFisica(String numeroCpf, String nome) {
		return new WsPessoaFisica(numeroCpf, nome);
	}

	public String getNumeroCpf() {
		return numeroCpf;
	}

	public void setNumeroCpf(String numeroCpf) {
		this.numeroCpf = numeroCpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNumeroRg() {
		return numeroRg;
	}

	public void setNumeroRg(String numeroRg) {
		this.numeroRg = numeroRg;
	}

	public String getOrgaoExpedidor() {
		return orgaoExpedidor;
	}

	public void setOrgaoExpedidor(String orgaoExpedidor) {
		this.orgaoExpedidor = orgaoExpedidor;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefoneResidencial() {
		return telefoneResidencial;
	}

	public void setTelefoneResidencial(String telefoneResidencial) {
		this.telefoneResidencial = telefoneResidencial;
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

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
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
		return "WsPessoaFisica [numeroCpf=" + numeroCpf + ", nome=" + nome
				+ "]";
	}
	
}
