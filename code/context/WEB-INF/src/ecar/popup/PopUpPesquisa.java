/*
 * Created on 14/12/2004
 *
 */
package ecar.popup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;

/**
 * Essa interface oferece m�todos para um template de janela de pesquisa. Os m�todos set e get devem ser vistos 
 * do ponto de vista desse template.
 * @author garten
 */
public interface PopUpPesquisa {
    
    /**
     * Oferece um objeto Dao para o template.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return Dao
     */
    public Dao getDao();
    
    /**
     * Devolve para o template o titulo da janela de pesquisa.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getTitulo();
    
    /**
     * Passa os par�metros escolhidos no template para a classe de pesquisa.<br>
     * String arg - � o argumento de pesquisa informado pelo usuario.<br>
     * String[] pesquisarEm - � um array de String com os campos em que o usuario deseja pesquisar.<br>
     * 
     * @param request
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
    public void setArgPesquisa(HttpServletRequest request) throws ECARException;
    
    /**
     * Devolve uma lista com o resultado da pesquisa para o template.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return List
     * @throws ECARException
     */
    public List pesquisar() throws ECARException;
    
    /**
     * Devolve o c�digo de um item da lista para o template.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getCodigo();
    
    /**
     * Devolve a descri��o de um iotem da lista para o template.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getDescricao();
    
    /**
     * Utilizado pelo template para obter um objeto da lista e convert�-lo para o objeto em quest�o.<br>
     * 
     * @param o
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void setPojo(Object o);
   
    /**
     * Devolve para o template um array de campos que podem ser utilizados na pesquisa.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String[]
     */
    public String[] getPesquisarEm();

}
