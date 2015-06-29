/*
 * Criado em 19/05/2006
 *
 */
package ecar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.TipoAcompFuncAcompPK;
import ecar.pojo.TipoAcompFuncAcompTafc;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.util.Dominios;

/**
 * @author evandro
 *  
 */
public class TipoAcompFuncAcompDao extends Dao {
	
	/**
     * Construtor. Chama o Session factory do Hibernate
         * @param request
         */
    public TipoAcompFuncAcompDao(HttpServletRequest request) {
		super();
		this.request = request;
    }
    
    /**
     *
     */
    public TipoAcompFuncAcompDao(){
    	super();
    }
    
    /**
     * Busca
     * @param codTa
     * @param codTpfa
     * @return TipoAcompFuncAcompTafc 
     * @throws ECARException
     */
    public TipoAcompFuncAcompTafc buscar(Long codTa, Long codTpfa) throws ECARException {
    	try{
//        return (TipoAcompFuncAcompTafc) this.getSession()
//        	.createCriteria(TipoAcompFuncAcompTafc.class)
//        	.add(Restrictions.eq("comp_id",new TipoAcompFuncAcompPK(codTa,codTpfa)))
//        	.uniqueResult();
    		return (TipoAcompFuncAcompTafc) this.getSession().get(TipoAcompFuncAcompTafc.class,new TipoAcompFuncAcompPK(codTa,codTpfa));
    	}
    	catch (HibernateException e) {
    		this.logger.error(e);
    		throw new ECARException(e);
		}
    }
    
    /**
     * @param ta
     * @author Robson
     * @return List<TipoAcompFuncAcompTafc>
     * @since 26/11/2007
     */
    public List getListaAcessotpfa(TipoAcompanhamentoTa ta){
    	return this.getSession()
    		.createCriteria(TipoAcompFuncAcompTafc.class)
    		.add(Restrictions.eq("tipoAcompanhamentoTa",ta))
    		.add(Restrictions.eq("indLeituraTipoAcomp",Dominios.COM_ACESSO_LEITURA))
    		.list();
    }
    
    /**
     * @author Robson
     * @param tafc
     * @param request
     * @since 26/11/2007
     */
    public void setTipoAcompFuncAcompTafc(TipoAcompFuncAcompTafc tafc, HttpServletRequest request){
    	
    	tafc.setIndLeituraTipoAcomp(
    			Pagina.getParamOrDefault(request, 
    			"Tpfa" + tafc.getComp_id().getCodTa() + "-" + tafc.getComp_id().getCodTpfa(), 
    			Dominios.SEM_ACESSO_LEITURA));
    }
}