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

import ecar.exception.ECARException;
import ecar.pojo.ItemEstLocalRevIettlr;
import ecar.pojo.ItemEstLocalRevIettlrPK;
import ecar.pojo.ItemEstruturarevisaoIettrev;
import ecar.pojo.LocalItemLit;

/**
 * Classe de manipulação de objetos da classe ItemEstLocalRevIettlr.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Fri Apr 28 17:12:24 BRT 2006
 *
 */
public class ItemEstLocalRevIettlrDAO extends Dao{
	
	/*private LocalItemDao localItemDao = null;
	private ItemEstruturaDao itemEstruturaDao = null;*/
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ItemEstLocalRevIettlrDAO(HttpServletRequest request) {
		super();
		this.request = request;
		/*localItemDao = new LocalItemDao(request);
    	itemEstruturaDao = new ItemEstruturaDao(request);*/
	}
	
	/**
     * Cria um objeto itemEstrutLocal a partir de parâmetros passados
     * no objeto request
     * 
     * @param request
     * @param itemEstrutLocal
     * @throws ECARException
     */
    public void setItemEstLocalRevIettlrDAO(HttpServletRequest request, ItemEstLocalRevIettlr itemEstrutLocal) throws ECARException {
        itemEstrutLocal.setItemEstruturarevisaoIettrev( (ItemEstruturarevisaoIettrev) this.buscar(ItemEstruturarevisaoIettrev.class, Integer.valueOf(Pagina.getParamStr(request, "codIettrev"))) );
        itemEstrutLocal.setLocalItemLit((LocalItemLit) new LocalItemDao(request).buscar(LocalItemLit.class, Long.valueOf(Pagina.getParamStr(request, "codLit"))));
    }
	
    /**
     * Método utilizado para setar os valores da PK da classe
     * 
     * @param itemEstrutLocal
     */
    public void setPK(ItemEstLocalRevIettlr itemEstrutLocal) {
        ItemEstLocalRevIettlrPK chave = new ItemEstLocalRevIettlrPK();
        
        chave.setCodIettrev(Integer.valueOf(itemEstrutLocal.getItemEstruturarevisaoIettrev().getCodIettrev().intValue()));
        chave.setCodLit(Integer.valueOf(itemEstrutLocal.getLocalItemLit().getCodLit().intValue()));
        
        itemEstrutLocal.setComp_id(chave);
    }
    
    /**
     * Grava uma relação entre itemEstrutura e LocalItem
     * 
     * @param itemEstrutLocal
     * @throws ECARException
     */
    public void salvar(ItemEstLocalRevIettlr itemEstrutLocal) throws ECARException {
        setPK(itemEstrutLocal);
        try {
            if (buscar(ItemEstLocalRevIettlr.class, itemEstrutLocal.getComp_id()) != null)
            	throw new ECARException("itemEstrutura.localItem.inclusao.jaExiste");
        } catch (ECARException e) {
        	this.logger.error(e);
            if (e.getMessageKey().equalsIgnoreCase("erro.objectNotFound")) {
                super.salvar(itemEstrutLocal);
            } else
                /* joga para frente a inclusao.jaExiste */
                throw e;
        }
    }
    
    /**
     * Recebe o código de item estrutura e um array contendo códigos de
     * locais e exclui todos os registros que relacionam este 
     * item estrutura com cada um dos códigosLocalItem
     * 
     * @param codigosParaExcluir
     * @param codItemEstrutura
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir, Integer codItemEstrutura) throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	        	ItemEstLocalRevIettlrPK chave = new ItemEstLocalRevIettlrPK(Integer.valueOf(codigosParaExcluir[i]), codItemEstrutura);
	            ItemEstLocalRevIettlr itemEstrutLocal = (ItemEstLocalRevIettlr) buscar(ItemEstLocalRevIettlr.class, chave);
	            
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
    public String getAbrangencia(Integer codItemEstrutura) throws ECARException {
    	ItemEstruturarevisaoIettrev itemEstrutura = new ItemEstruturarevisaoIettrev();
    	ItemEstLocalRevIettlr itemEstrutLocal = new ItemEstLocalRevIettlr();
    	
    	itemEstrutura = (ItemEstruturarevisaoIettrev) new ItemEstruturarevisaoIettrevDAO(request).buscar(ItemEstruturarevisaoIettrev.class, codItemEstrutura);
    	List lista = new ItemEstruturarevisaoIettrevDAO(request).ordenaSet(itemEstrutura.getItemEstLocalRevIettlrs(), "this.localItemLit.identificacaoLit", "asc");
		Iterator it = lista.iterator();
    	
    	if(it.hasNext()){
    		itemEstrutLocal = (ItemEstLocalRevIettlr) it.next();
    		return itemEstrutLocal.getLocalItemLit().getLocalGrupoLgp().getIdentificacaoLgp();
    	} else {
    		return "";
    	}
    	
    }

}
