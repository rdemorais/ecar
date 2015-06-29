/***********************************************************************
 * Module:  ResultadoSituacao.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class ResultadoSituacao
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** Situação do Resultado
 * 
 * @pdOid bb688178-1495-46ea-8c39-4f43d14fb332 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultadoSituacao {
   
   private long codigo;
   
   private String nome;
   
   /** @pdOid c2614e3e-d671-4a1a-a125-e0fb21409095 */
   public long getCodigo() {
      return codigo;
   }
   
   /** @param newCodigo
    * @pdOid 78278f55-92a0-4455-9ba7-98817e9be846 */
   public void setCodigo(long newCodigo) {
      codigo = newCodigo;
   }
   
   /** @pdOid f9b2611f-d28f-4389-a90e-cccaba90fd5b */
   public String getNome() {
      return nome;
   }
   
   /** @param newNome
    * @pdOid cb5c5340-f3ed-4fef-8ff3-df24414168aa */
   public void setNome(String newNome) {
      nome = newNome;
   }

}