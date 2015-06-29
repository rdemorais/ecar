/*
 * Criado em 04/11/2004
 *
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.CriterioCri;

/**
 * @author felipev
 *
 */
public class CriterioDao extends Dao{
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public CriterioDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * 
	 * @author n/c
	 * @param criterio
	 * @throws ECARException
	 */
	public void excluir(CriterioCri criterio) throws ECARException {	    
	   try{
	       	boolean excluir = true;
		    if(contar(criterio.getItemEstrutCriterioIettcs()) > 0){ 
		        excluir = false;
			    throw new ECARException("criterio.exclusao.erro.itemEstrutCriterioIettcs");
		    }      			       		    
		    if(excluir)
		        super.excluir(criterio);	
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}
}
