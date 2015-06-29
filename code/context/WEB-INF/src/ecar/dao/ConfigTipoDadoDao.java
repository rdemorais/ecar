/*
 * Created on 30/11/2004
 * 
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

/**
 * @author denilson
 *
 */
public class ConfigTipoDadoDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ConfigTipoDadoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
}