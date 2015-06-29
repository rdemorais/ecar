/***********************************************************************
 * Module:  ResutadoSituacaoContar.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class ResutadoSituacaoContar
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Conta quantos produtos possuiem uma determinada Marcação.
 * 
 * @pdOid 0788459c-b944-4fa6-b7b8-2a33df6bf583
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ResutadoSituacaoContar {

	private int contar;

	private long codigo;
	   
    private String nome;

    public ResutadoSituacaoContar() {
    	
    }
    
	public ResutadoSituacaoContar(Long contar, Long codigo, String nome) {		
		this.contar = contar.intValue();
		this.codigo = codigo;
		this.nome = nome;
	}

	public int getContar() {
		return contar;
	}

	public void setContar(int contar) {
		this.contar = contar;
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