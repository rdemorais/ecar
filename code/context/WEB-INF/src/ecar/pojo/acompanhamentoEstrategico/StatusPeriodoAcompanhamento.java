/***********************************************************************
 * Module:  StatusPeriodoAcompanhamento.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class StatusPeriodoAcompanhamento
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** @pdOid 03c47e9f-284e-4ca4-ae37-06001b168113 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusPeriodoAcompanhamento {

	private long codigo; // codigo da COR

	private String nome;

	public StatusPeriodoAcompanhamento() {
		
	}
	
	public StatusPeriodoAcompanhamento(long codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	/** @pdOid 501c1b09-45ad-4d87-acd5-8cc21547502a */
	public long getCodigo() {
		return codigo;
	}

	/**
	 * @param newCodigo
	 * @pdOid c503e638-6c57-4c2a-94b1-33412604828e
	 */
	public void setCodigo(long newCodigo) {
		codigo = newCodigo;
	}

	/** @pdOid ff937016-e4c1-405d-b874-7673484b336c */
	public String getNome() {
		return nome;
	}

	/**
	 * @param newNome
	 * @pdOid e3d75d94-b57f-4327-a9e4-a9f4646b961a
	 */
	public void setNome(String newNome) {
		nome = newNome;
	}

}