/*
 * Criado em 04/11/2004
 *
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

/**
 * @author aleixo
 *
 */
public class EspecieDao extends Dao{

	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public EspecieDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
}
