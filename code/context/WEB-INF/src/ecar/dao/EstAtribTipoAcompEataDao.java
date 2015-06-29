package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.CacheManagerImpl;
import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.EstAtribTipoAcompEata;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.TipoAcompanhamentoTa;

/**
 * Classe de manipula��o de objetos da classe EstAtribTipoAcompEata.
 * 
 * @author Igor Steinmacher
 *
 */
public class EstAtribTipoAcompEataDao extends Dao {
	
	
	public static final String NOME_EATA_EH_FILHO = "Eh_Filho";
	
	
    /**
     *
     * @param request
     */
    public EstAtribTipoAcompEataDao(HttpServletRequest request) {
		super();
		this.request = request;		
	}

	/**
	 * Busca um EstAtribTipoAcompEata com base na EstruturaAtributoEttat e no TipoAcompanhamentoTa informado.
	 * @param estruturaAtributo
	 * @param tipoAcompanhamento
	 * @return EstAtribTipoAcompEata
	 * @throws ECARException
	 */
	public EstAtribTipoAcompEata getEstAtribTipoAcompEata(EstruturaAtributoEttat estruturaAtributo, TipoAcompanhamentoTa tipoAcompanhamento) throws ECARException{
		try {
			StringBuilder select = new StringBuilder("select eata from EstAtribTipoAcompEata eata")
										   .append(" where eata.estruturaAtributoEttat.estruturaEtt.codEtt = :codEtt")
										   .append("   and eata.estruturaAtributoEttat.atributosAtb.codAtb = :codAtb")
										   .append("   and eata.tipoAcompanhamentoTa.codTa = :codTa");
		
			Query q = this.session.createQuery(select.toString());
			
			q.setLong("codEtt", estruturaAtributo.getEstruturaEtt().getCodEtt().longValue());
			q.setLong("codAtb", estruturaAtributo.getAtributosAtb().getCodAtb().longValue());
			q.setLong("codTa", tipoAcompanhamento.getCodTa().longValue());
			q.setMaxResults(1);
			
			Object o = q.uniqueResult();
			
			if(o != null){
				return (EstAtribTipoAcompEata) o;
			}
			else {
				return null;
			}
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}
	
		
        /**
         *
         * @param tipoAcompanhamentoTa
         * @return
         * @throws ECARException
         */
        public List getEstAtribTipoAcompEata(TipoAcompanhamentoTa tipoAcompanhamentoTa) throws ECARException{
		try {
			
			EstAtribTipoAcompEata estAtribTipoAcompEata = new EstAtribTipoAcompEata();

			estAtribTipoAcompEata.setTipoAcompanhamentoTa(tipoAcompanhamentoTa);
			
			estAtribTipoAcompEata.setFiltroEata("S");
//			estAtribTipoAcompEata.getFiltroEata()
		   	
		    List lista = this.pesquisar(estAtribTipoAcompEata, new String[]{
		    		"estruturaAtributoEttat", Dao.ORDEM_ASC,
		    		"estruturaAtributoEttat.estruturaEtt", Dao.ORDEM_ASC});
		    
		    Iterator it = lista.iterator();
		    
		    
		    
/*		    while (it.hasNext()){
		    	EstAtribTipoAcompEata eata = (EstAtribTipoAcompEata) it.next();
		    	
		    	System.out.println("Nome Pai: " + (eata.getEstruturaAtributoEttat().getEstruturaEtt().getEstruturaEtt() == null ? "":eata.getEstruturaAtributoEttat().getEstruturaEtt().getEstruturaEtt().getNomeEtt()));
		    	
		    	System.out.println("Nome estrutura: " + eata.getEstruturaAtributoEttat().getEstruturaEtt().getNomeEtt());
		    	
		    	System.out.println("Abributo: " + eata.getEstruturaAtributoEttat().getAtributosAtb().getNomeAtb());
		    	
		    	System.out.println("Tipo Acompanhamento: " + eata.getTipoAcompanhamentoTa().getDescricaoTa());
		    	
		    	System.out.println("Eh filtro: " + eata.getFiltroEata());
		    	
		    }
*/
		    
		    return lista;
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}
	
	
        /**
         *
         * @param tipoAcompanhamentoTa
         * @return
         * @throws ECARException
         */
        public List getEstruturaEhFiltro(TipoAcompanhamentoTa tipoAcompanhamentoTa) throws ECARException{
		try {
			StringBuilder select = new StringBuilder("select distinct eata.estruturaAtributoEttat.estruturaEtt from EstAtribTipoAcompEata eata")										  
										   .append(" where eata.tipoAcompanhamentoTa.codTa = :codTa")
										   .append(" and eata.filtroEata = 'S'");
										   //.append(" order by eata.estruturaAtributoEttat.estruturaEtt ASC,  eata.estruturaAtributoEttat.estruturaEtt.estruturaEtt ASC");
		
			Query q = this.session.createQuery(select.toString());
						
			q.setLong("codTa", tipoAcompanhamentoTa.getCodTa().longValue());
			
			
			/* Tratamento de cache */
			String cacheId = EstAtribTipoAcompEataDao.NOME_EATA_EH_FILHO;
			List results = (List) CacheManagerImpl.get(cacheId);
			
			if (results == null){
				results = q.list();
				CacheManagerImpl.add(cacheId, results);
			}
			 
			
						
			Iterator itResults = results.iterator();
			EstruturaEtt estruturaCorrente = null;
			
			List estruturasOrdenadas = new ArrayList();
			
			//adiciona as estruturas raizes (que n�o tem pai) na lista ordenada
			while (itResults.hasNext()){
				
				estruturaCorrente = (EstruturaEtt) itResults.next();
				
				if (estruturaCorrente.getEstruturaEtt() == null){
					
					estruturasOrdenadas.add(estruturaCorrente);
				}
				
			}
			
			//remove apenas as ra�zes da lista de estruturas
//			results.removeAll(estruturasOrdenadas);
			
			Iterator itEstruturasFilhas = results.iterator();
			EstruturaEtt estruturaFilhaCorrente = null;
									
			while (itEstruturasFilhas.hasNext()){
				
				estruturaFilhaCorrente = (EstruturaEtt)itEstruturasFilhas.next();
				
				if(estruturaFilhaCorrente.getEstruturaEtt() != null){
										
					if (estruturasOrdenadas.indexOf(estruturaFilhaCorrente.getEstruturaEtt())+1 < estruturasOrdenadas.size() ){
						
						int indicePai = estruturasOrdenadas.indexOf(estruturaFilhaCorrente.getEstruturaEtt());
						
						estruturasOrdenadas.add(
								indicePai+1,
								estruturaFilhaCorrente);
					}
					else{
						estruturasOrdenadas.add(estruturaFilhaCorrente);
					}
					
				}
			}			

			return estruturasOrdenadas;
			
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}
}