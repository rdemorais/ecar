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
public class SisTipoExibicGrupoDao extends Dao{
	
    /**
     *
     */
    public static final String COMBOBOX = "1";
    /**
     *
     */
    public static final String CHECKBOX = "2";
    /**
     *
     */
    public static final String LISTBOX = "3";
    /**
     *
     */
    public static final String RADIO_BUTTON = "4";
    /**
     *
     */
    public static final String TEXT = "5";
    /**
     *
     */
    public static final String IMAGEM = "6";
    /**
     *
     */
    public static final String MULTITEXTO = "7";
    /**
     *
     */
    public static final String VALIDACAO = "8";
    /**
     *
     */
    public static final String TEXTAREA = "10";
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public SisTipoExibicGrupoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}

}