package ecar.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;

import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrutFisicoIettf;

/**
 *
 * @author 70744416353
 */
public class ItemEstrtIndResulIettrDao extends Dao{

    /**
     *
     * @param request
     */
    public ItemEstrtIndResulIettrDao(HttpServletRequest request) {
		super();
		this.request = request;
	}	
	
    /**
     *
     * @param codIett
     * @return
     */
    public ArrayList<ItemEstrtIndResulIettr> getIndicadoresMetaFisicaPPA(  Long codIett ){
    	
        try {
        	
        	final Long CLASSIFICACAO_META_FISICA_PPA = 41L;
        	
        	StringBuilder sb = new StringBuilder();
        	sb.append("from ItemEstrtIndResulIettr bean");
        	sb.append(" where ");
        	sb.append(" bean.sisAtributoSatb.codSatb = :codSatb ");
        	sb.append("and bean.itemEstruturaIett.codIett = :codIett ");
        	sb.append("and bean.indAtivoIettr = :status ");          	
        	
            Query query = session.createQuery(sb.toString());
            
            query.setLong("codIett", codIett.longValue());
            query.setLong("codSatb", CLASSIFICACAO_META_FISICA_PPA );
            query.setString("status", "S" );            
            
            List retorno = (List)query.list();
           	return (ArrayList<ItemEstrtIndResulIettr>)retorno;
            
        } catch (HibernateException e) {
        	e.printStackTrace(System.out);
        	this.logger.error(e);
        	return null;	
        }    	
    }
    /**
     * Retorna uma lista com os valores previstos.
     * @param indicador
     * @return 
     */
    public ArrayList<ItemEstrutFisicoIettf> GetMesesPrevisto(ItemEstrtIndResulIettr indicador){
    	
        try {
        	
        	StringBuilder sb = new StringBuilder();
        	sb.append("select itemEstrutFisicoIettf ");
        	sb.append("from ItemEstrutFisicoIettf itemEstrutFisicoIettf");
        	sb.append(" where ");
        	sb.append(" itemEstrutFisicoIettf.itemEstrtIndResulIettr.codIettir = :codIettir ");
        	
            Query query = session.createQuery(sb.toString());
            
            query.setLong("codIettir", indicador.getCodIettir().longValue());
            
            List<ItemEstrutFisicoIettf> retorno = (List<ItemEstrutFisicoIettf>)query.list();
           	return (ArrayList<ItemEstrutFisicoIettf>)retorno;
            
        } catch (HibernateException e) {
        	e.printStackTrace(System.out);
        	this.logger.error(e);
        	return null;	
        }
    }

}
