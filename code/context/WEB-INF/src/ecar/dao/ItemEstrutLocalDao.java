/*
 * Criado em 25/01/2004
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.api.facade.Local;
import ecar.exception.ECARException;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstrutLocalIettlPK;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.LocalItemLit;
import ecar.pojo.UsuarioUsu;

/**
 * @author evandro
 *
 */
public class ItemEstrutLocalDao extends Dao{
	
	/*private LocalItemDao localItemDao = null;
	private ItemEstruturaDao itemEstruturaDao = null;*/
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ItemEstrutLocalDao(HttpServletRequest request) {
		super();
		this.request = request;
		/*localItemDao = new LocalItemDao(request);
    	itemEstruturaDao = new ItemEstruturaDao(request);*/
	}
	
	/**
     * Cria um objeto itemEstrutLocal a partir de parâmetros passados
     * no objeto request
     * 
     * @author n/c
     * @param request
     * @param itemEstrutLocal
     * @throws ECARException
     */
    public void setItemEstrutLocal(HttpServletRequest request, ItemEstrutLocalIettl itemEstrutLocal) throws ECARException {
        itemEstrutLocal.setItemEstruturaIett( (ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett"))) );
        itemEstrutLocal.setLocalItemLit((LocalItemLit) new LocalItemDao(request).buscar(LocalItemLit.class, Long.valueOf(Pagina.getParamStr(request, "codLit"))));
    }
	
    /**
     * Método utilizado para setar os valores da PK da classe
     * 
     * @author n/c
     * @param itemEstrutLocal
     */
    public void setPK(ItemEstrutLocalIettl itemEstrutLocal) {
        ItemEstrutLocalIettlPK chave = new ItemEstrutLocalIettlPK();
        
        chave.setCodIett(itemEstrutLocal.getItemEstruturaIett().getCodIett());
        chave.setCodLit(itemEstrutLocal.getLocalItemLit().getCodLit());
        
        itemEstrutLocal.setComp_id(chave);
    }
    
    /**
     * Grava uma relação entre itemEstrutura e LocalItem
     * 
     * @author n/c
     * @param itemEstrutLocal
     * @throws ECARException
     */
    public void salvar(ItemEstrutLocalIettl itemEstrutLocal) throws ECARException {
        setPK(itemEstrutLocal);
        try {
            if (buscar(ItemEstrutLocalIettl.class, itemEstrutLocal.getComp_id()) != null)
            	throw new ECARException("itemEstrutura.localItem.inclusao.jaExiste");
        } catch (ECARException e) {
            if (e.getMessageKey().equalsIgnoreCase("erro.objectNotFound")) {
                super.salvar(itemEstrutLocal);
            } else{
            	this.logger.error(e);
                /* joga para frente a inclusao.jaExiste */
                throw e;
            }
        }
    }
    
    /**
     * Recebe o código de item estrutura e um array contendo códigos de
     * locais e exclui todos os registros que relacionam este 
     * item estrutura com cada um dos códigosLocalItem
     * 
     * @author n/c
     * @param codigosParaExcluir
     * @param codItemEstrutura
     * @param usuarioUsu
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir, Long codItemEstrutura, UsuarioUsu usuarioUsu) throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	        	ItemEstrutLocalIettlPK chave = new ItemEstrutLocalIettlPK(Long.valueOf(codigosParaExcluir[i]), codItemEstrutura);
	            ItemEstrutLocalIettl itemEstrutLocal = (ItemEstrutLocalIettl) buscar(ItemEstrutLocalIettl.class, chave);

//	            itemEstrutLocal.setUsuarioUsuManutencao(usuarioUsu);
//	            itemEstrutLocal.setIndExclusaoPosHistorico(Boolean.TRUE);
            	
            	session.delete(itemEstrutLocal);
				objetos.add(itemEstrutLocal);
	        }
			
			tx.commit();
	
			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("EXC");
				Iterator itObj = objetos.iterator();
	
				while(itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}
    
    /**
     * Recebe o código de item estrutura e um array contendo códigos de
     * locais e exclui todos os registros que relacionam este 
     * item estrutura com cada um dos códigosLocalItem
     * 
     * @param codItemEstrutura
     * @return
     * @throws ECARException
     */
    public String getAbrangencia(Long codItemEstrutura) throws ECARException {
    	ItemEstruturaIett itemEstrutura = new ItemEstruturaIett();
    	ItemEstrutLocalIettl itemEstrutLocal = new ItemEstrutLocalIettl();
    	
    	itemEstrutura = (ItemEstruturaIett) new ItemEstruturaDao(request).buscar(ItemEstruturaIett.class, codItemEstrutura);
    	List lista = new ItemEstruturaDao(request).ordenaSet(itemEstrutura.getItemEstrutLocalIettls(), "this.localItemLit.identificacaoLit", "asc");
		Iterator it = lista.iterator();
    	
    	if(it.hasNext()){
    		itemEstrutLocal = (ItemEstrutLocalIettl) it.next();
    		return itemEstrutLocal.getLocalItemLit().getLocalGrupoLgp().getIdentificacaoLgp();
    	} else {
    		return "";
    	}
    	
    }
    
    /**
     * Retorna o código da abrangência.
     * 
     * 
     * @param codItemEstrutura
     * @return
     * @throws ECARException
     */
    public long getAbrangenciaAsInteger(Long codItemEstrutura) throws ECARException {
    	ItemEstruturaIett itemEstrutura = new ItemEstruturaIett();
    	ItemEstrutLocalIettl itemEstrutLocal = new ItemEstrutLocalIettl();
    	
    	itemEstrutura = (ItemEstruturaIett) new ItemEstruturaDao(request).buscar(ItemEstruturaIett.class, codItemEstrutura);
    	List lista = new ItemEstruturaDao(request).ordenaSet(itemEstrutura.getItemEstrutLocalIettls(), "this.localItemLit.identificacaoLit", "asc");
		Iterator it = lista.iterator();
    	
    	if(it.hasNext()){
    		itemEstrutLocal = (ItemEstrutLocalIettl) it.next();
    		return itemEstrutLocal.getLocalItemLit().getLocalGrupoLgp().getCodLgp();
    	} else {
    		return Local.NAO_DEFINIDO;
    	}
    	
    }
    /**
     * Verifica se não existe valor previsto ou realizado associado aos locais do item estrutura
     * 
     * 
     * @param codItemEstrutura
     * @return
     * @throws ECARException
     */
    public Boolean validaExclusaoLocalItemEstrutura(String[] codigosParaExcluir, Long codItemEstrutura, StringBuffer sbmsg) throws ECARException {
    	
    	Long qtdeValoresPrevistos = 0l;
    	Long qtdeValoresRealizados = 0l;    	
    	List<Long> locais = new ArrayList<Long>();
    	for (int i = 0; i < codigosParaExcluir.length;i++){
    		locais.add(Long.valueOf(codigosParaExcluir[i]));
    	}    	
    	ItemEstrtIndResultLocalIettirlDao itemEstrtIndResultLocalIettirlDao = new ItemEstrtIndResultLocalIettirlDao(null);
    	AcompRealFisicoLocalDao acompRealFisicoLocalDao = new AcompRealFisicoLocalDao(null);
    	    	
    	qtdeValoresPrevistos = itemEstrtIndResultLocalIettirlDao.countByLocaisItemEstrutura(locais, codItemEstrutura);
    	
    	if (qtdeValoresPrevistos > 0){ //nesse caso já existem valores previstos informados.
    		sbmsg.append("itemEstrutura.localItem.exclusao.valorPrevistoExistente") ;
    		return false;
    	}
    	qtdeValoresRealizados = acompRealFisicoLocalDao.countByLocaisItemEstrutura(locais, codItemEstrutura);
    	
    	if (qtdeValoresRealizados > 0){ //nesse caso já existem valores realizados informados.
    		sbmsg.append("itemEstrutura.localItem.exclusao.valorRealizadoExistente");
    		return false;
    	}
    	return true;
    	
    }
    
    
}
