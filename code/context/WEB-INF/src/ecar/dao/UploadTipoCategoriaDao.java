/*
 * Created on 18/05/2005
 * 
 */
package ecar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.UploadTipoCategoriaUtc;

/**
 * @author Evandro
 *
 */

public class UploadTipoCategoriaDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public UploadTipoCategoriaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
     * Devolve uma lista de UploadTipoCategoria ordenado por nome
         * @return
         * @throws ECARException
     */
    public List getListUploadTipoCategoria() throws ECARException {
        return listar(UploadTipoCategoriaUtc.class, new String[] {"nomeUtc","asc"});
    }
		
}