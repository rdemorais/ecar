/*
 * Created on 02/12/2004
 * 
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

/**
 * @author evandro
 *
 */
public class SisTipoOrdenacaoDao extends Dao{
	
    
    /**
     *
     */
    public static final String CODIGO_ASC = "1";
    /**
     *
     */
    public static final String CODIGO_DESC = "2";
    /**
     *
     */
    public static final String DESCRICAO_ASC = "3";
    /**
     *
     */
    public static final String DESCRICAO_DESC = "4";
    /**
     *
     */
    public static final String INF_COMPLEMENTAR_ASC = "5";
    /**
     *
     */
    public static final String INF_COMPLEMENTAR_DESC = "6";
    
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public SisTipoOrdenacaoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}

}