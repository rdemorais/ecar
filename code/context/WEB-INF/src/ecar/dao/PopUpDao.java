/*
 * Created on 09/05/2005
 * 
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.PopupComportamentoPppc;
import ecar.pojo.PopupPpp;

/**
 * @author felipe
 * 
 */
public class PopUpDao extends Dao{
	
    /**
     *
     */
    public static final int POPUP_COMPORTAMENTO_ABRIR_SEMPRE =	1;
        /**
         *
         */
        public static final int POPUP_COMPORTAMENTO_ABRIR_SOMENTE_UMA_VEZ = 2;
		
	/**
	 * Construtora
	 * @param request
	 */
	public PopUpDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Salva
	 * @param popUp
	 * @throws ECARException
	 */
	public void salvar(PopupPpp popUp) throws ECARException{
		popUp.setDataInclusaoPpp(Data.getDataAtual());
		super.salvar(popUp);
	}
	
	
    /**
     * A partir de dados passados por request popula um objeto PopUpPpp
     * @param popUp
     * @param recuperarParametrosComoString indica se irá recuperar dados nulos como String vazia
     * @param request
     * @throws ECARException
     */
    public void setPopup(PopupPpp popUp, HttpServletRequest request, boolean recuperarParametrosComoString) throws ECARException{
        
        try{                     
            if(!"".equals(Pagina.getParamStr(request, "popupComportamentoPppc"))){
                popUp.setPopupComportamentoPppc((PopupComportamentoPppc) this.buscar(
                			PopupComportamentoPppc.class,
                            Long.valueOf(Pagina.getParamStr(request, "popupComportamentoPppc"))));
            }
        	if(!"".equals(Pagina.getParamStr(request, "janelaAlturaPpp")))
        		popUp.setJanelaAlturaPpp(Integer.valueOf(Pagina.getParamStr(request, "janelaAlturaPpp")));
        	if(!"".equals(Pagina.getParamStr(request, "janelaLarguraPpp")))
        		popUp.setJanelaLarguraPpp(Integer.valueOf(Pagina.getParamStr(request, "janelaLarguraPpp")));
        	if(!"".equals(Pagina.getParamStr(request, "dataIniExibicaoPpp")))
        		popUp.setDataIniExibicaoPpp(Data.parseDate(Pagina.getParamStr(request, "dataIniExibicaoPpp")));
        	if(!"".equals(Pagina.getParamStr(request, "dataFimExibicaoPpp")))
        		popUp.setDataFimExibicaoPpp(Data.parseDate(Pagina.getParamStr(request, "dataFimExibicaoPpp")));

            if(recuperarParametrosComoString){
            	popUp.setNomePpp(Pagina.getParamStr(request, "nomePpp"));
            	popUp.setConteudoPpp(Pagina.getParamStr(request, "conteudoPpp"));
            	popUp.setIndDesativarPpp(Pagina.getParamStr(request, "indDesativarPpp"));
            	popUp.setIndAtivaPpp(Pagina.getParamStr(request, "indAtivaPpp"));            	
            } else {
            	popUp.setNomePpp(Pagina.getParam(request, "nomePpp"));
            	popUp.setConteudoPpp(Pagina.getParam(request, "conteudoPpp"));
            	popUp.setIndDesativarPpp(Pagina.getParam(request, "indDesativarPpp"));
            	popUp.setIndAtivaPpp(Pagina.getParam(request, "indAtivaPpp"));            	
            }
        } catch(Exception e){
            this.logger.error(e);
            throw new ECARException(e);
        }
        
    }
    
    /**
     * Retorna uma lista de Avisos que podem ser apresentados em um dia.
     * @param dia
     * @return
     * @throws ECARException
     */
    public List getPopUpsApresentadasNoDia(Date dia) throws ECARException{
	    List resultado = new ArrayList();
	    
	    try {
            Query query = session.createQuery(
            		"from PopupPpp p where not (:dia < p.dataIniExibicaoPpp or :dia > p.dataFimExibicaoPpp) and p.indAtivaPpp = 'S'");

            query.setDate("dia", dia);
            
            resultado = query.list(); 
        } catch (HibernateException e) {
            this.logger.error(e);
            throw new ECARException("erro.hibernateException");
        }	    
	    return (resultado);
    	
    }

}
