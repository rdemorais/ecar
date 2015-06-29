/***********************************************************************
 * Module:  Estrategia.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class Estrategia
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/** @pdOid 4a9975bc-9f1c-4a6f-bcab-108dbdeb4a38 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Estrategia implements Comparable<Estrategia> {
   
   private long codigo;
   
   private String nome;
   
   private String siglaEstrategia;
     
   public List<Resultado> resultados;
   
   private String situacao;
   
   public String getSituacao() {
	return situacao;
}

public void setSituacao(String situacao) {
	this.situacao = situacao;
}

@XmlTransient
   private long codObj;
   
   public Estrategia() {		
	   
   }
      
   public Estrategia(long codigo) {	
	   this.codigo = codigo;
   }
   
   public Estrategia(long codigo, String nome) {	
	   this.codigo = codigo;
	   this.nome = nome;
   }

	public Estrategia(long codigo, String nome, long codObj, String sigla) {
		this.codigo = codigo;
		this.nome = nome;
		this.codObj = codObj;
		this.siglaEstrategia = sigla;
	}

public long getCodigo() {
      return codigo;
   }
   
   /** @param newCodigo
    * @pdOid b5bede20-f7c8-42a0-9d5e-60f5d8327926 */
   public void setCodigo(long newCodigo) {
      codigo = newCodigo;
   }
   
   /** @pdOid 215fe81e-49f6-4ed6-b31e-7d9df8a47af6 */
   public String getNome() {
      return nome;
   }
   
   /** @param newNome
    * @pdOid 1db700d9-1e1e-49a4-8d2a-be3b4ddf5613 */
   public void setNome(String newNome) {
      nome = newNome;
   }
   
   
   /** @pdGenerated default getter */
   public java.util.List<Resultado> getResultados() {
      if (resultados == null)
         resultados = new java.util.ArrayList<Resultado>();
      return resultados;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorResultados() {
      if (resultados == null)
         resultados = new java.util.ArrayList<Resultado>();
      return resultados.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newResultados */
   public void setResultados(java.util.List<Resultado> newResultados) {
      removeAllResultados();
      for (java.util.Iterator iter = newResultados.iterator(); iter.hasNext();)
         addResultados((Resultado)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newResultado */
   public void addResultados(Resultado newResultado) {
      if (newResultado == null)
         return;
      if (this.resultados == null)
         this.resultados = new java.util.ArrayList<Resultado>();
      if (!this.resultados.contains(newResultado))
         this.resultados.add(newResultado);
   }
   
   /** @pdGenerated default remove
     * @param oldResultado */
   public void removeResultados(Resultado oldResultado) {
      if (oldResultado == null)
         return;
      if (this.resultados != null)
         if (this.resultados.contains(oldResultado))
            this.resultados.remove(oldResultado);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllResultados() {
      if (resultados != null)
         resultados.clear();
   }

	public long getCodObj() {
		return codObj;
	}

	public void setCodObj(long codObj) {
		this.codObj = codObj;
	}

	public String getSiglaEstrategia() {
		return siglaEstrategia;
	}

	public void setSiglaEstrategia(String siglaEstrategia) {
		this.siglaEstrategia = siglaEstrategia;
	}

	public int compareTo(Estrategia e) {
		if (this.siglaEstrategia.compareTo(e.getSiglaEstrategia()) < 0) {
            return -1;
        }
        if (this.siglaEstrategia.compareTo(e.getSiglaEstrategia()) > 0) {
            return 1;
        }
		return 0;
	}

	@Override
	public String toString() {
		return "Estrategia [codigo=" + codigo + ", nome=" + nome
				+ ", siglaEstrategia=" + siglaEstrategia + ", resultados="
				+ resultados + "]";
	}
	
	

	
}