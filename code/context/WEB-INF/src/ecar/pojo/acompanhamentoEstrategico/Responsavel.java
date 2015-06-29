/***********************************************************************
 * Module:  Responsavel.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class Responsavel
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Responsavel {

	private long codigo = 0;

	private String nome = "";

	public Orgao orgao;
	
	@XmlTransient
	private String nomeEOrgao = "";
	
	@XmlTransient
	private Integer codTipo;

	public String toString() {
		return this.nome;
	}

	public Responsavel() {
		this.codigo = 0;
		this.nome = "";
		this.orgao = new Orgao();
	}

	public Responsavel(long codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
		this.orgao = new Orgao();
	}
	
	public Responsavel(long codigo, String nome, Orgao orgao) {
		this.codigo = codigo;
		this.nome = nome;
		this.orgao = orgao;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long newCodigo) {
		codigo = newCodigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String newNome) {
		nome = newNome;
	}

	public void setNomeEOrgao(String nomeEOrgao) {
		this.nomeEOrgao = nomeEOrgao;
	}

	public String getNomeEOrgao() {
		if (nomeEOrgao != null) {
			return nomeEOrgao;
		} else if (this.nome + " " + this.orgao.getNome() != null) {
			return this.nome + " " + this.orgao.getNome();
		} else {
			return "N/A";
		}

	}

	public Integer getCodTipo() {
		return codTipo;
	}

	public void setCodTipo(Integer codTipo) {
		this.codTipo = codTipo;
	}

	public Orgao getOrgao() {
		return orgao;
	}

	public void setOrgao(Orgao orgao) {
		this.orgao = orgao;
	}
	
	
}