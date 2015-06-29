/*
 * Criado em 20/12/2004
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;

import ecar.exception.ECARException;
import ecar.pojo.CriterioCri;
import ecar.pojo.ItemEstrutCriterioIettc;
import ecar.pojo.ItemEstrutCriterioIettcPK;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.UsuarioUsu;

/**
 * @author felipev
 *  
 */
public class ItemEstruturaCriterioDao extends Dao {

    /**
     *
     * @param request
     */
    public ItemEstruturaCriterioDao(HttpServletRequest request) {
		super();
		this.request = request;
    }

    /**
     * Devolve um objeto ItemEstruturaCriterio a partir de um código de Item
     * Estrutura e um Código do Critério
     * 
     * @param codItemEstrutura
     * @param codCriterio
     * @return
     * @throws ECARException
     */
    public ItemEstrutCriterioIettc buscar(Long codItemEstrutura,
            Long codCriterio) throws ECARException {
        ItemEstrutCriterioIettcPK pk = new ItemEstrutCriterioIettcPK();
        pk.setCodCri(codCriterio);
        pk.setCodIett(codItemEstrutura);
        return (ItemEstrutCriterioIettc) super.buscar(ItemEstrutCriterioIettc.class, pk);
    }

    /**
     * Grava um registro de itemEstruturaCriterio
     * @author felipev, rogerio
     * @since n/c
     * @version 0.2, 04/06/2007
     * @param codCriterio
     * @param codItemEstrutura
     * @param usuario
     * @throws ECARException
     */
    public void salvar(Long codCriterio, Long codItemEstrutura, UsuarioUsu usuario ) throws ECARException {
        
    	ItemEstrutCriterioIettc itemEstruturaCriterio = null;
        	
    	try {
    		itemEstruturaCriterio = this.buscar(codItemEstrutura, codCriterio);
    	} catch (ECARException ecarex) {
    		if (!(ecarex.getCausaRaiz() instanceof ObjectNotFoundException)){
    			throw ecarex;
    		}
    	}
    	
    	//O Criterio nunca foi cadastrado para o item
    	if (itemEstruturaCriterio == null) {
            ItemEstrutCriterioIettcPK pk = new ItemEstrutCriterioIettcPK();
            pk.setCodCri(codCriterio);
            pk.setCodIett(codItemEstrutura);
            
            ItemEstrutCriterioIettc ieCriterio = new ItemEstrutCriterioIettc();
            ieCriterio.setComp_id(pk);
            ieCriterio.setCriterioCri((CriterioCri) super.buscar(CriterioCri.class, codCriterio));
            ieCriterio.setItemEstruturaIett((ItemEstruturaIett) super.buscar(ItemEstruturaIett.class, codItemEstrutura));
            
            ieCriterio.setDataUltManutencao(Data.getDataAtual());
            ieCriterio.setUsuManutencao(usuario);
            
            super.salvar(ieCriterio);
    	} else if (!itemEstruturaCriterio.getIndExclusaoPosHistorico()){ // O Criterio já existe e está ativo deve levantar erro de tentativa de cadastro duplicado 
    		throw new ECARException("itemEstrutura.criterio.inclusao.jaExiste");
    	} else if (itemEstruturaCriterio.getIndExclusaoPosHistorico()){//O Criterio já existe e NÃO está ativo deve apenas ativá-lo
    		itemEstruturaCriterio.setIndExclusaoPosHistorico(false);
    		itemEstruturaCriterio.setDataUltManutencao(Data.getDataAtual());
    		itemEstruturaCriterio.setUsuManutencao(usuario);

    		super.alterar(itemEstruturaCriterio);
    	}
    	
    }

	/**
     * Recebe um array com Códigos de Recurso e um código de Item Estrutura e
     * exclui todas as relações dos critério que estes códigos representam com o
     * item da EStrutura
     * 
     * @param codCriterio
     * @param codItemEstrutura
     * @param usuario
     * @throws ECARException
     */
    public void excluir(String[] codCriterio, Long codItemEstrutura, UsuarioUsu usuario )
            throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

            ItemEstrutCriterioIettc criterio;
            for (int i = 0; i < codCriterio.length; i++) {
            	/* -- 
            	 * #2156 - Histórico
            	 * Atualiza o registro antes de excluir, para garantir que o usuário que fez a 
            	 * exclusão, será registrado na tabela master de histórico. 
            	 * -- */
            	criterio = this.buscar(codItemEstrutura, Long.valueOf(codCriterio[i]));
            	criterio.setDataUltManutencao(Data.getDataAtual());
            	criterio.setUsuManutencao(usuario);
            	criterio.setIndExclusaoPosHistorico(Boolean.TRUE); 
            	session.update(criterio);
            	
            	// apaga pela trigger 
	            //session.delete(criterio);
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
     * Devolve uma lista com todos os Critérios de um Item da Estrutura
     * @param itemEstrutura
     * @return
     * @throws ECARException
     */
    public List<ItemEstrutCriterioIettc> listarCriterios(ItemEstruturaIett itemEstrutura) throws ECARException{
        List<ItemEstrutCriterioIettc> retorno = new ArrayList<ItemEstrutCriterioIettc>();
        if (itemEstrutura.getItemEstrutCriterioIettcs() != null) {
        	
            retorno.addAll(itemEstrutura.getItemEstrutCriterioIettcs());
            
        	Collections.sort(retorno,new Comparator<ItemEstrutCriterioIettc>(){
            
            				public int compare(ItemEstrutCriterioIettc o1,
            						ItemEstrutCriterioIettc o2) {
            					return o1.getCriterioCri().getDescricaoCri().compareTo(o2.getCriterioCri().getDescricaoCri());
            				}
                    		
                    	});
            
        }
        return retorno;
    }
    
    /**
     * Verifica se o item possui um determinado critério passado pelo parametro "id"
     * nas listas de códigos de critérios.
     * 
     * @param itemEstrutura
     * @param idCriteriosCom
     * @param idCriteriosSem
     * @return boolean
     * @throws ECARException
     */
    public boolean verificarCriterio(ItemEstruturaIett itemEstrutura, List idCriteriosCom, List idCriteriosSem) throws ECARException{
    	boolean existemCriteriosCom = (idCriteriosCom != null && idCriteriosCom.size() > 0);
    	boolean existemCriteriosSem = (idCriteriosSem != null && idCriteriosSem.size() > 0);

    	if (itemEstrutura.getItemEstrutCriterioIettcs() != null) {
        	boolean criterioExisteListaCom = false;
        	boolean criterioExisteListaSem = false;
        	
        	List codCriteriosIett = new ArrayList();

        	Iterator itCriterios = itemEstrutura.getItemEstrutCriterioIettcs().iterator();
        	while(itCriterios.hasNext()){
            	ItemEstrutCriterioIettc itemCriterio = (ItemEstrutCriterioIettc) itCriterios.next();
            	String codigoCriterio = itemCriterio.getComp_id().getCodCri().toString();
            	codCriteriosIett.add(codigoCriterio);
        	}
        	
        	if(codCriteriosIett.containsAll(idCriteriosCom)){
        		criterioExisteListaCom = true;
        	}
        	
        	if(codCriteriosIett.containsAll(idCriteriosSem)){
        		criterioExisteListaSem = true;
        	}
        	        	
        	if(existemCriteriosCom && existemCriteriosSem){
        		if(criterioExisteListaCom && !criterioExisteListaSem){
        			return true;
        		}
        	}
        	else if (existemCriteriosCom){
        		if(criterioExisteListaCom){
        			return true;
        		}
        	}
        	else if (existemCriteriosSem){
        		if(!criterioExisteListaSem){
        			return true;
        		}
        	}
    	}
    	return false;
    }

}