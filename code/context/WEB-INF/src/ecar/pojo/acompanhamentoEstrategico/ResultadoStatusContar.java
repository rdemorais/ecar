/***********************************************************************
 * Module:  ResultadoStatusContar.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class ResultadoStatusContar
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** Conta o Status do mes corrente de todos os resultados.
 * 
 * @pdOid 6a36d697-b66e-4165-87e8-d181f98cc6c0 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultadoStatusContar {
   
   private Long contar;
      
   private Long codigo;
   
	private String nome;

	public ResultadoStatusContar() {
		
	}
	
	public ResultadoStatusContar(Long contar, Long codigo, String nome) {		
		if (codigo == null){
			this.codigo = 0L;
			this.nome = "Nao Monitorado";	
		}else {
			this.codigo = codigo;
			this.nome = nome;
		}
		this.contar = contar;		
	}

	public Long getContar() {
		return contar;
	}

	public void setContar(Long contar) {
		
		this.contar = contar;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		if (codigo == null){
			this.codigo = 0L;		
		}else{
			this.codigo = codigo;	
		}		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome == null){		
			this.nome = "Nao Monitorado";	
		}else{
			this.nome = nome;	
		}		
	}

	@Override
	public String toString() {
		return "ResultadoStatusContar [contar=" + contar + ", codigo=" + codigo + ", nome=" + nome + "]";
	}
	
	
}