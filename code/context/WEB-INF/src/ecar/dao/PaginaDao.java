/*
 * Created on 25/11/2004
 * 
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.PaginaPgn;

/**
 * @author denilson
 *
 */
public class PaginaDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public PaginaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Verifica depois exclui
	 * 
	 * @param paginaPgn
	 * @throws ECARException
	 */
	public void excluir(PaginaPgn paginaPgn) throws ECARException {	    
	   try{
	       	boolean excluir = true;

		    if(contar(paginaPgn.getPaginaPgns()) > 0){ 
		        excluir = false;
			    throw new ECARException("paginaPgn.exclusao.erro.paginaPgns");
		    }      			       
		    
		    if(excluir)
		        super.excluir(paginaPgn);	
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}
}