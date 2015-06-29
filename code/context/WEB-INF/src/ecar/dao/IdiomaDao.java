/*
 * Created on 29/11/2004
 * 
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

/**
 * @author denilson
 *
 */
public class IdiomaDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public IdiomaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
}