/*
 * Created on 25/11/2004
 *
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.TextosSiteTxt;

/**
 *
 * @author 70744416353
 */
public class TextosSiteDao extends Dao {

	/**
	 * Construtora
	 * @param request
	 */
    public TextosSiteDao(HttpServletRequest request) {
		super();
		this.request = request;
    }
    
    /**
     * Salva
     * @param texto
     * @throws ECARException
     */
    public void salvar(TextosSiteTxt texto) throws ECARException {
    	super.salvar(texto);
    }

}
