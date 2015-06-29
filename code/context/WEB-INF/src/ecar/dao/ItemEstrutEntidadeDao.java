/*
 * Criado em 13/12/2004
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.EntidadeEnt;
import ecar.pojo.ItemEstrutEntidadeIette;
import ecar.pojo.ItemEstrutEntidadeIettePK;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.TipoParticipacaoTpp;
import ecar.pojo.UsuarioUsu;

/**
 * @author evandro
 *
 */
public class ItemEstrutEntidadeDao extends Dao{
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ItemEstrutEntidadeDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
     * Recebe o código de item estrutura e um array contendo códigos de
     * entidades e de tipoParticipacao e exclui todos os registros que 
     * relacioname este item estrutura com cada um dos códigosEntidade
     * 
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
            	//Separar os códigos formato "codEnt,codTpp" - tirar a virgula entre e eles
            	String separaCod[] = codigosParaExcluir[i].split(",");
        	
            	ItemEstrutEntidadeIettePK chave = new ItemEstrutEntidadeIettePK(codItemEstrutura, Long.valueOf(separaCod[0]), Long.valueOf(separaCod[1]));
            	ItemEstrutEntidadeIette itemEstrutEntidade = (ItemEstrutEntidadeIette) buscar(ItemEstrutEntidadeIette.class, chave);
            	    			            	
            	session.delete(itemEstrutEntidade);
            	objetos.add(itemEstrutEntidade);
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
     * Cria um objeto itemEstrutEntidade a partir de parâmetros passados
     * no objeto request
     * 
     * @param request
     * @param itemEstrutEntidade
     * @throws ECARException
     */
    public void setItemEstrutEntidade(HttpServletRequest request, ItemEstrutEntidadeIette itemEstrutEntidade) throws ECARException {
        itemEstrutEntidade.setItemEstruturaIett( (ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett"))) );
        itemEstrutEntidade.setEntidadeEnt((EntidadeEnt) new EntidadeDao(request).buscar(EntidadeEnt.class, Long.valueOf(Pagina.getParamStr(request, "codEnt"))));
       	itemEstrutEntidade.setTipoParticipacaoTpp((TipoParticipacaoTpp) new TipoParticipacaoDao(request).buscar(TipoParticipacaoTpp.class, Long.valueOf(Pagina.getParamStr(request, "codTpp"))));
        itemEstrutEntidade.setDescricaoIette(Pagina.getParamStr(request, "descricaoIette"));
        
        if(!"".equals(Pagina.getParamStr(request, "dataInicioIette"))){
        	itemEstrutEntidade.setDataInicioIette( Data.parseDate( Pagina.getParam(request, "dataInicioIette") ));
        }
        else {
        	itemEstrutEntidade.setDataInicioIette(null);
        }
        
        if(!"".equals(Pagina.getParamStr(request, "dataFimIette"))){
        	itemEstrutEntidade.setDataFimIette( Data.parseDate( Pagina.getParam(request, "dataFimIette") ));
        }
        else {
        	itemEstrutEntidade.setDataFimIette(null);
        }
    }
    
    /**
     * Método utilizado para setar os valores da PK da classe
     * 
     * @author n/c
     * @param itemEstrutEntidade
     */
    public void setPK(ItemEstrutEntidadeIette itemEstrutEntidade) {
        ItemEstrutEntidadeIettePK chave = new ItemEstrutEntidadeIettePK();
        
        chave.setCodIett(itemEstrutEntidade.getItemEstruturaIett().getCodIett());
        chave.setCodEnt(itemEstrutEntidade.getEntidadeEnt().getCodEnt());
        chave.setCodTpp(itemEstrutEntidade.getTipoParticipacaoTpp().getCodTpp());
        
        itemEstrutEntidade.setComp_id(chave);
    }
    
    /**
     * Grava uma relação entre itemEstrutura e Entidade
     * 
     * @author n/c
     * @param itemEstrutEntidade
     * @throws ECARException
     */
    public void salvar(ItemEstrutEntidadeIette itemEstrutEntidade) throws ECARException {
        setPK(itemEstrutEntidade);
        try {
            if (buscar(ItemEstrutEntidadeIette.class, itemEstrutEntidade.getComp_id()) != null)
            	throw new ECARException("itemEstrutura.entidade.inclusao.jaExiste");
        } catch (ECARException e) {
        	this.logger.error(e);
            if (e.getMessageKey().equalsIgnoreCase("erro.objectNotFound")) {
                super.salvar(itemEstrutEntidade);
            } else
                /* joga para frente a inclusao.jaExiste */
                throw e;
        }
    }
    
    
    /**
     * Retorna um objeto ItemEstrutEntidade a partir dos códigos
     * 
     * @author n/c
     * @param codIett 
     * @param codEnt
     * @param codTpp
     * @return ItemEstrutEntidadeIette
     * @throws ECARException
     */
    public ItemEstrutEntidadeIette buscar(Long codIett, Long codEnt, Long codTpp) throws ECARException {
        ItemEstrutEntidadeIettePK chave = new ItemEstrutEntidadeIettePK();
    
        chave.setCodEnt(codEnt);
        chave.setCodIett(codIett);
        chave.setCodTpp(codTpp);
        
        return (ItemEstrutEntidadeIette) super.buscar(ItemEstrutEntidadeIette.class, chave);
    }
}
