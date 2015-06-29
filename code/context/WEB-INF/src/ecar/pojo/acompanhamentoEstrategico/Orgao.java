/***********************************************************************
 * Module:  Orgao.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class Orgao
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** @pdOid fb10e0d6-a392-4ff9-9526-208dd8d9ef92 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Orgao implements Serializable {

	private static final long serialVersionUID = 1L;
	private long codigo;
	private String nome = "";

	public Orgao() {		
		this.codigo = 0;
		this.nome = "";
	}
	
	public Orgao(long codigo, String nome) {		
		this.codigo = codigo;
		this.nome = nome;
	}

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	

}