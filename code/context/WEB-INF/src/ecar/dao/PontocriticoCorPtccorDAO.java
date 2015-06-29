package ecar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.Cor;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.PontocriticoCorPtccor;

/**
 * Classe de manipulacao de objetos da classe PontocriticoCorPtccor.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Wed Aug 16 17:18:32 BRT 2006
 *
 */
public class PontocriticoCorPtccorDAO extends Dao{
		 
	/**
	 * Construtora
	 * @param request
	 */
	public PontocriticoCorPtccorDAO (HttpServletRequest request)
	{
		super();
		this.request = request;
    }
	
	/**
	 * 
	 * @param cor
	 * @param ptc
	 * @return PontocriticoCorPtccor
	 * @throws ECARException
	 */
	public PontocriticoCorPtccor buscar(Cor cor, PontoCriticoPtc ptc) throws ECARException {
		
		PontocriticoCorPtccor ptcCor = new PontocriticoCorPtccor();
		try{
			if(ptc.getCodPtc() != null){
				Query q = session.createQuery("from PontocriticoCorPtccor ptcCor where " +
						 " ptcCor.id.codCor = :cor" + 
						 " and ptcCor.id.codPtc = :codPtc");
				q.setLong("cor", cor.getCodCor().longValue());    	
				q.setLong("codPtc", ptc.getCodPtc().longValue());    	
				
				List lista = q.list();
				
				if (lista.size() != 0)
					ptcCor = (PontocriticoCorPtccor) lista.iterator().next();
			}
        } catch (ObjectNotFoundException e){
			this.logger.error(e);
        	throw new ECARException("erro.objectNotFound");
        } catch (HibernateException e){
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
        }catch (Exception e){
			this.logger.error(e);
        	throw new ECARException("erro.exception");
        }
		
		return ptcCor; 
	}
}
