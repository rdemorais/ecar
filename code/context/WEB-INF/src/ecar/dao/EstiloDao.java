/*
 * Created on 21/01/2008
 *
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.Estilo;

/**
 *
 * @author 70744416353
 */
public class EstiloDao extends Dao {

	/**
	 * Construtor
	 * @param request
	 */
    public EstiloDao(HttpServletRequest request) {
		super();
		this.request = request;
    }
    
    /**
     * Salva
     * @param estilo
     * @throws ECARException
     */
    public void salvar(Estilo estilo) throws ECARException {
    	super.salvar(estilo);
    }

}
