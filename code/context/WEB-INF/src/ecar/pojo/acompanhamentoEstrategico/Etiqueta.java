/***********************************************************************
 * Module:  Etiqueta.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class Etiqueta
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/** @pdOid 24812686-3679-48ca-96ec-873345c4691e */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Etiqueta {
   
   private int codigo;
   
   private String nome;
   
   private String prioritaria;
   
//   private List<Etiqueta> etiquetas;
   
//   public Etiqueta(List<Etiqueta> etiquetas) {
//	   this.etiquetas = etiquetas;
//   }
      
   private Categoria categoria;
      
   
   public Etiqueta() {
	   
   }

   public Etiqueta(Long codigo, String nome, String prioritaria, Long codCategoria, String categoria) {	
	this.codigo = codigo.intValue();
	this.nome = nome;
	this.prioritaria = prioritaria;
	
	this.categoria = new Categoria();
	
	if(codCategoria != null) {
		this.categoria = new Categoria(codCategoria, categoria);		
	}
}

/** @pdOid 5300cfca-d905-4167-b97c-944ec68a1597 */
   public int getCodigo() {
      return codigo;
   }
   
   /** @param newCodigo
    * @pdOid deb96f30-4df9-4855-9999-f30b3409def8 */
   public void setCodigo(int newCodigo) {
      codigo = newCodigo;
   }
   
   /** @pdOid 1707c84a-44d7-4a74-bddd-af70518b4b4d */
   public String getNome() {
      return nome;
   }
   
   /** @param newNome
    * @pdOid 3451ba67-5596-4318-8b11-53e418f8f1f6 */
   public void setNome(String newNome) {
      nome = newNome;
   }
   
   /** @pdOid 9557d2c7-aba2-4367-888a-3db98b4ba73f */
   public String getPrioritaria() {
      return prioritaria;
   }
   
   /** @param newPrioritaria
    * @pdOid 4271025f-a595-4754-94c3-032756a67511 */
   public void setPrioritaria(String newPrioritaria) {
      prioritaria = newPrioritaria;
   }

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}	
	
//	public List<Etiqueta> getEtiquetas() {
//		return etiquetas;
//	}
//
//	public void setEtiquetas(List<Etiqueta> etiquetas) {
//		this.etiquetas = etiquetas;
//	}	
   
}