/*
 * Criado em 03/12/2004
 *
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

/**
 * @author evandro
 *
 */
public class OpcaoDao extends Dao{

	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public OpcaoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
}
