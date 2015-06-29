/***********************************************************************
 * Module:  ResultadoNaoMonitorado.java
 * Author:  leonardo.marques
 * Purpose: Defines the Class ResultadoNaoMonitorado
 ***********************************************************************/

package ecar.pojo.acompanhamentoEstrategico;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/** @pdOid 5ada7868-9ba2-447d-bab6-e521a0b80104 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultadoNaoMonitorado {
   
   private long codigo;
   
   private String nome;
   
   private int contar;
   
   public ResultadoNaoMonitorado() {		
		
	}
   
   public ResultadoNaoMonitorado(Long contar, Long codigo, String nome) {		
		this.contar = contar.intValue();
		if(contar != 0) {
			this.codigo = codigo;
			this.nome = nome;	
		}		
	}
   
   /** @pdOid cfdce106-99fd-4679-a512-f3cf32ad82ae */
   public long getCodigo() {
      return codigo;
   }
   
   /** @param newCodigo
    * @pdOid c2f68591-d2be-4daa-8298-cfa7f0538cf9 */
   public void setCodigo(long newCodigo) {
      codigo = newCodigo;
   }
   
   /** @pdOid 89f0538f-0d7c-4872-9c7a-e574982cf705 */
   public String getNome() {
      return nome;
   }
   
   /** @param newNome
    * @pdOid 8d01f2bb-d25a-4e86-84b5-e011f47544b8 */
   public void setNome(String newNome) {
      nome = newNome;
   }
   
   /** @pdOid bf26d7dd-5780-4e62-bdc3-286e51badf6f */
   public int getContar() {
      return contar;
   }
   
   /** @param newContar
    * @pdOid 5ec665bb-5f96-46d8-a606-f68e50fb9237 */
   public void setContar(int newContar) {
      contar = newContar;
   }

}