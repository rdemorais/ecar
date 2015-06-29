/*
 * Created on 25/11/2004
 * 
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.PaginaAreaSitePa;

/**
 * @author denilson
 *
 */
public class PaginaAreaSiteDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public PaginaAreaSiteDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * verifica depois exclui
	 * 
	 * @param paginaAreaSite
	 * @throws ECARException
	 */
	public void excluir(PaginaAreaSitePa paginaAreaSite) throws ECARException {	    
	   try{
	       	boolean excluir = true;

		    if(contar(paginaAreaSite.getOpcaoMenuOpcms()) > 0){
		        excluir = false;
		        throw new ECARException("paginaAreaSite.exclusao.erro.opcaoMenuOpcms");			       
			}
		    if(contar(paginaAreaSite.getPaginaPgns()) > 0){ 
		        excluir = false;
			    throw new ECARException("paginaAreaSite.exclusao.erro.paginaPgns");
		    }      			       
		    
		    if(excluir)
		        super.excluir(paginaAreaSite);	
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}
	

    /**
     * Devolve um conjunto de PaginaAreaSitePa
     * 
     * @param indCapa String S/N
     * 
     * @return List de PaginaAreaSitePa de acordo com o parâmetro indCapa ordenados por seqApresentacaoPas
     * @throws ECARException
     */
    public List getPaginaAreaSitePa(String indCapa) throws ECARException {
        List retorno = new ArrayList();

        PaginaAreaSitePa pagina = new PaginaAreaSitePa();

        pagina.setIndCapaPas(indCapa);

        retorno = this.pesquisar(pagina, new String[]{"seqApresentacaoPas","asc"});

        return retorno;

    }

}
