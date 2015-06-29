package ecar.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.Cor;
import ecar.pojo.ItemEstrtIndResulCorIettrcor;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.historico.HistoricoItemEstrtIndResulIettr;

/**
 * Classe de manipulacao de objetos da classe PontocriticoCorPtccor.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Wed Aug 16 17:18:32 BRT 2006
 *
 */
public class ItemEstrtIndResulCorIettrcorDAO extends Dao{
	
	public static final String MENOR_VALOR = "Menor Valor";
	public static final String MAIOR_VALOR = "Maior Valor";
	/**
	 * 
	 * @param request
	 */
	public ItemEstrtIndResulCorIettrcorDAO (HttpServletRequest request)
	{
		super();
		this.request = request;
    }
	
	/**
	 * 
	 * @param cor
	 * @param iettr
	 * @return
	 * @throws ECARException
	 */
	public ItemEstrtIndResulCorIettrcor buscar(Cor cor, ItemEstrtIndResulIettr iettr) throws ECARException {		
		
		ItemEstrtIndResulCorIettrcor  iettrCor = new ItemEstrtIndResulCorIettrcor();
		try{
			if(iettr.getCodIettir() != null){
				Query q = session.createQuery("from ItemEstrtIndResulCorIettrcor iettrCor where " +
						 " iettrCor.id.codCor = :cor" + 
						 " and iettrCor.id.codIettir = :codIettr");
				q.setLong("cor", cor.getCodCor().longValue());    	
				q.setLong("codIettr", iettr.getCodIettir().longValue());    	
				
				List lista = q.list();
				
				if (lista.size() != 0)
					iettrCor = (ItemEstrtIndResulCorIettrcor) lista.iterator().next();
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
		
		return iettrCor; 
	}
	
    /**
     * Retorna a lista de Iettrcor Ativos de acordo com o ItemEstrtIndResulIettr informado. <br> 
     * @author 
     * @param iettr
     * @return List
     * @throws ECARException
     */
    public List listarIettrcorAtivosPorIettrOrderByValorPrimEmailIettrcor(ItemEstrtIndResulIettr iettr) throws ECARException {
    	List list = null;
    	
    	try {
	    	Query query = session.createQuery(
		       		"from ItemEstrtIndResulCorIettrcor " +
		       		"where indAtivoEnvioEmailIettrcor = :ativo " +
		       		"and itemEstrtIndResulIettr = :iettr " +
		       		"order by valorPrimEmailIettrcor asc " );	
	    	
		    query.setString("ativo", "S");
		    query.setParameter("iettr", iettr);		    
		    
		    list = query.list();
    	} catch( HibernateException e ) {
    		this.logger.error(e);
    		throw new ECARException("erro.hibernateException");
    	}
    	
    	return list;
    } 
    
    
    /**
     * Retorna a lista de cores informada no iettr. <br> 
     * @author 
     * @param iettr
     * @return List
     * @throws ECARException
     */
    public List listarCoresIettr(ItemEstrtIndResulIettr iettr) throws ECARException{
    	List list = new ArrayList();
    	
    	if (iettr != null && iettr.getItemEstrtIndResulCorIettrcores() != null){
    	
	    	List itemEstrtIndResulCorIettrcores = new ArrayList(iettr.getItemEstrtIndResulCorIettrcores());
	    	
	    	Collections.sort(itemEstrtIndResulCorIettrcores,
					new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			return ((ItemEstrtIndResulCorIettrcor) o1 ).getSequenciaIettrcor().compareTo( ( (ItemEstrtIndResulCorIettrcor) o2).getSequenciaIettrcor());
		        		}
		    		} );
	    	
	    	
			Iterator iIettrcores = itemEstrtIndResulCorIettrcores.iterator();
			while (iIettrcores.hasNext()){
				ItemEstrtIndResulCorIettrcor iettrcor = (ItemEstrtIndResulCorIettrcor) iIettrcores.next(); 
				list.add(iettrcor.getCor()); 
			}
		}
    	
    	
    	
    	Cor corFiltro = new Cor();
		corFiltro.setIndIndicadoresFisicosCor("S");
    	
    	Iterator itCores = new CorDao(request).pesquisar(corFiltro, new String[]{"ordemCor","asc"}).iterator();
    	
    	while (itCores.hasNext()){
    		Cor cor = (Cor) itCores.next();
    		if (!list.contains(cor)){
    			list.add(cor);
    		}
    	}
    	
    	
    	
    	
    	return list;
    }
    
    /*
     * Historico
     */
    
    /**
     *
     * @param cor
     * @param iettr
     * @return
     * @throws ECARException
     */
    public ItemEstrtIndResulCorIettrcor buscar(Cor cor, HistoricoItemEstrtIndResulIettr iettr) throws ECARException {
		
		ItemEstrtIndResulCorIettrcor  iettrCor = new ItemEstrtIndResulCorIettrcor();
		try{
			if(iettr.getCodIettir() != null){
				
			Iterator lista = iettr.getItemEstrtIndResulCorIettrcores().iterator();
			
			while(lista.hasNext())
				iettrCor = (ItemEstrtIndResulCorIettrcor) lista.next();
				if (iettrCor.getCor() == cor)
					return iettrCor;
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
		
		return iettrCor; 
	}
	/*
	 * Fim do Histórico
	 */

}
