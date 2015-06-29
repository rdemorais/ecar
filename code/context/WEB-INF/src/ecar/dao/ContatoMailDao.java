/*
 * Created on 11/05/2005
 * 
 */
package ecar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.ContatoAreaCtta;
import ecar.pojo.ContatoMailCategoriaCttm;
import ecar.pojo.ContatoMailCttm;

/**
 * @author felipe
 *
 */
public class ContatoMailDao extends Dao{

    /**
     *
     * @param request
     */
    public ContatoMailDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	
	/**
     * A partir de dados passados por request popula um objeto Contato Mail 
         * @param contato
         * @param request
     * @param recuperarParametrosComoString indica se irá recuperar dados nulos como String vazia
     * @throws ECARException
     */
    public void setContatoMail(ContatoMailCttm contato, HttpServletRequest request, boolean recuperarParametrosComoString) throws ECARException{
        
        try{               
        	
        	if(!"".equals(Pagina.getParamStr(request,"contatoAreaCtta")))
        		contato.setContatoAreaCtta((ContatoAreaCtta) this.buscar(ContatoAreaCtta.class, Long.valueOf(Pagina.getParamStr(request,"contatoAreaCtta"))));
        	if(!"".equals(Pagina.getParamStr(request,"contatoMailCategoriaCttm")))
    			contato.setContatoMailCategoriaCttm((ContatoMailCategoriaCttm) this.buscar(ContatoMailCategoriaCttm.class, Long.valueOf(Pagina.getParamStr(request,"contatoMailCategoriaCttm"))));
        	
            if(recuperarParametrosComoString){
            	contato.setNomeCttm(Pagina.getParamStr(request, "nomeCttm"));
            	contato.setEmailCttm(Pagina.getParamStr(request,"emailCttm"));
            	contato.setIndOrigemCttm(Pagina.getParamStr(request,"indOrigemCttm"));
            } else {
            	contato.setNomeCttm(Pagina.getParam(request, "nomeCttm"));
            	contato.setEmailCttm(Pagina.getParam(request,"emailCttm"));
            	contato.setIndOrigemCttm(Pagina.getParam(request,"indOrigemCttm"));
            }
        } catch(Exception e){
            this.logger.error(e);
            throw new ECARException(e);
        }
        
    }	
    
    /**
     * 
     * @param ctta
     * @return list de contatos
     * @throws ECARException
     */
    public List getListContatoMailCttm(ContatoAreaCtta ctta) throws ECARException{
    	
    	ContatoMailCttm cttm = new ContatoMailCttm();
    	cttm.setContatoAreaCtta(ctta);    	
    	
    	return this.pesquisar(cttm, null);
    }
    
}
