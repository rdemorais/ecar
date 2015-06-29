/*
 * Created on 13/09/2004
 * 
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

/**
 * @author garten
 *
 */
public class TemplateDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public TemplateDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/*
	 * 
	 * @author garten
	 * Colocar aqui os seus métodos específicos para salvar, alterar, pesquisar, etc
	 *
	 */
	
}