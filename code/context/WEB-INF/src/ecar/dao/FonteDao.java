package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

/**
 * Dao para pojo FonteFon
 * 
 * @author aleixo
 * @since 12/05/2006
 */
public class FonteDao extends Dao {
	
    /**
     *
     * @param request
     */
    public FonteDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
}
