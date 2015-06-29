package ecar.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.AreaAre;
import ecar.pojo.EstrutTpFuncAcmpEtttfa;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.IettIndResulRevIettrr;
import ecar.pojo.IettUsutpfuacrevIettutfar;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstruturarevisaoIettrev;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.PaiFilho;
import ecar.pojo.PeriodicidadePrdc;
import ecar.pojo.SubAreaSare;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UnidadeOrcamentariaUO;
import ecar.pojo.UsuarioUsu;

/**
 * Classe de manipulação de objetos da classe ItemEstruturarevisaoIettrev.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Fri Apr 28 17:12:23 BRT 2006
 *
 */
public class ItemEstruturarevisaoIettrevDAO extends Dao{
	
    /**
     *
     * @param request
     */
    public ItemEstruturarevisaoIettrevDAO(HttpServletRequest request) {
		super();
		this.request = request;
    }
    
    /**
     * Recebe um array de códigos de ItemEstruturarevisao e exclui os registro
     * referenciados por estes códigos
     * 
     * @param codigosParaExcluir
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir) throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	            ItemEstruturarevisaoIettrev itemEstruturaRev = (ItemEstruturarevisaoIettrev) this
	                    .buscar(ItemEstruturarevisaoIettrev.class, Integer.valueOf(codigosParaExcluir[i]));
	            session.delete(itemEstruturaRev);
				objetos.add(itemEstruturaRev);
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
     * Retorna uma lista com todos os itens de Estrutura Revisao acima de um dado Item
     * 
     * @param itemEstrutura
     * @return
     */
    public List getAscendentes(ItemEstruturarevisaoIettrev itemEstrutura) {
        List retorno = new ArrayList();
        while (itemEstrutura.getItemEstruturarevisaoIettrev() != null) {
            itemEstrutura = itemEstrutura.getItemEstruturarevisaoIettrev();
            retorno.add(itemEstrutura);
        }
        Collections.reverse(retorno);
        return retorno;
    }    
    
    
    /**
     * Retorna uma lista com todos os itens de Estrutura revisao abaixo de um dado Item
     * 
     * @param itemEstrutura
     * @param efetuarRefreshItemEstrutura 
     * @return
     * @throws ECARException
     */   
    public List getDescendentes(ItemEstruturarevisaoIettrev itemEstrutura, boolean efetuarRefreshItemEstrutura) throws ECARException{
        List retorno = new ArrayList();
 
        if(efetuarRefreshItemEstrutura) {
	        /* faz um refresh no item para que não seja aproveitado o objeto existente na session do Hibernate
	         * e termos um objeto com a coleçaõ de filhos completa
	         */
	        try{
	            this.session.refresh(itemEstrutura);    
	        } catch(HibernateException e){
	        	this.logger.error(e);
	            throw new ECARException(e);
	        }
        }
        
        if (itemEstrutura.getItemEstruturarevisaoIettrevs() != null) {
            
            Iterator it = itemEstrutura.getItemEstruturarevisaoIettrevs().iterator();
            while (it.hasNext()) {
                ItemEstruturarevisaoIettrev itemEstruturaFilho = (ItemEstruturarevisaoIettrev) it.next();
                
                if (!retorno.contains(itemEstruturaFilho))
                    retorno.add(itemEstruturaFilho);
                retorno.addAll(this.getDescendentes(itemEstruturaFilho, efetuarRefreshItemEstrutura));
            }
        }
        return retorno;
    }

    /**
     * 
     * @param request
     * @param itemEstruturaRev
     * @throws ECARException
     */   

    public void setItemEstruturaRevisao(HttpServletRequest request,  ItemEstruturarevisaoIettrev itemEstruturaRev) throws ECARException {
       
        if (!"".equals(Pagina.getParamStr(request, "codIettrevPai")))
            itemEstruturaRev.setItemEstruturarevisaoIettrev((ItemEstruturarevisaoIettrev) this.buscar(ItemEstruturarevisaoIettrev.class, Long.valueOf(Pagina.getParamStr(request, "codIettrevPai")))); 

        if (!"".equals(Pagina.getParamStr(request, "codIettrev")))
            itemEstruturaRev.setCodIettrev(Integer.valueOf(Pagina.getParamStr(request, "codIettrev")));
        if (!"".equals(Pagina.getParamStr(request, "codEtt")))      
        	itemEstruturaRev.setEstruturaEttrev((EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(Pagina.getParamStr(request, "codEtt"))));
        
        itemEstruturaRev.setSiglaIettrev(Pagina.getParamStr(request, "siglaIettrev"));
        
        itemEstruturaRev.setNomeIettrev(Pagina.getParamStr(request, "nomeIettrev"));
        itemEstruturaRev.setDescricaoIettrev(Pagina.getParamStr(request, "descricaoIettrev"));
        itemEstruturaRev.setOrigemIettrev(Pagina.getParamStr(request, "origemIettrev"));
        itemEstruturaRev.setIndMonitoramentoIettrev(Pagina.getParamStr(request, "indMonitoramentoIettrev"));
        itemEstruturaRev.setIndBloqPlanejamentoIettrev(Pagina.getParamStr(request, "indBloqPlanejamentoIettrev"));
        itemEstruturaRev.setObjetivoGeralIettrev(Pagina.getParamStr(request, "objetivoGeralIettrev"));
        itemEstruturaRev.setObjetivoEspecificoIettrev(Pagina.getParamStr(request, "objetivoEspecificoIettrev"));        		 
        itemEstruturaRev.setBeneficiosIettrev(Pagina.getParamStr(request, "beneficiosIettrev"));
        itemEstruturaRev.setJustificativaIettrev(Pagina.getParamStr(request, "justificativaIettrev"));
        itemEstruturaRev.setSituacaoIettrev(Pagina.getParamStr(request, "situacaoIettrev"));

        
        // hint de desempenho. Já diz a qual nivel o item pertence.
        itemEstruturaRev.setNivelIettrev(Integer.valueOf(getNivel(itemEstruturaRev)));
        
        if (!"".equals(Pagina.getParamStr(request, "dataInicioIettrev")))
            itemEstruturaRev.setDataInicioIettrev(Pagina.getParamDataBanco(request, "dataInicioIettrev"));
        else
            itemEstruturaRev.setDataInicioIettrev(null);
 
        if (!"".equals(Pagina.getParamStr(request, "dataTerminoIettrev")))
            itemEstruturaRev.setDataTerminoIettrev(Pagina.getParamDataBanco(request, "dataTerminoIettrev"));
        else
            itemEstruturaRev.setDataTerminoIettrev(null);            
        
        if (!"".equals(Pagina.getParamStr(request, "areaArerev")))
            itemEstruturaRev.setAreaArerev((AreaAre) new AreaDao(request).buscar(AreaAre.class, Long.valueOf(Pagina.getParamStr(request, "areaArerev"))));
        else
            itemEstruturaRev.setAreaArerev(null);
        
        if (!"".equals(Pagina.getParamStr(request, "subAreaSarerev")))
            itemEstruturaRev.setSubAreaSarerev((SubAreaSare) new SubAreaDao(request).buscar(SubAreaSare.class, Long.valueOf(Pagina.getParamStr(request, "subAreaSarerev"))));
        else
            itemEstruturaRev.setSubAreaSarerev(null);
        
        if (!"".equals(Pagina.getParamStr(request, "unidadeOrcamentariaUorev")))
            itemEstruturaRev.setUnidadeOrcamentariaUorev((UnidadeOrcamentariaUO) new UnidadeOrcamentariaDao(request).buscar(UnidadeOrcamentariaUO.class, Long.valueOf(Pagina.getParamStr(request, "unidadeOrcamentariaUorev"))));
        else
            itemEstruturaRev.setUnidadeOrcamentariaUorev(null);
        
        if (!"".equals(Pagina.getParamStr(request,"orgaoOrgByCodOrgaoResponsavel1Iettrev")))
            itemEstruturaRev.setOrgaoOrgByCodOrgaoResponsavel1Iettrev((OrgaoOrg) new OrgaoDao(request).buscar(OrgaoOrg.class,Long
                                            .valueOf(Pagina.getParamStr(request, "orgaoOrgByCodOrgaoResponsavel1Iettrev"))));
        else
            itemEstruturaRev.setOrgaoOrgByCodOrgaoResponsavel1Iettrev(null);
        
        if (!"".equals(Pagina.getParamStr(request,"orgaoOrgByCodOrgaoResponsavel2Iettrev")))
            itemEstruturaRev.setOrgaoOrgByCodOrgaoResponsavel2Iettrev((OrgaoOrg) new OrgaoDao(request).buscar(OrgaoOrg.class,Long
                                            .valueOf(Pagina.getParamStr(request, "orgaoOrgByCodOrgaoResponsavel2Iettrev"))));
        else
            itemEstruturaRev.setOrgaoOrgByCodOrgaoResponsavel2Iettrev(null);
            
        if (!"".equals(Pagina.getParamStr(request, "periodicidadePrdcrev")))
            itemEstruturaRev.setPeriodicidadePrdcrev((PeriodicidadePrdc) new PeriodicidadeDao(request).buscar(PeriodicidadePrdc.class, Long.valueOf(Pagina.getParamStr(request, "periodicidadePrdcrev"))));
        else
            itemEstruturaRev.setPeriodicidadePrdcrev(null);
            
        itemEstruturaRev.setIndCriticaIettrev(Pagina.getParamStr(request,"indCriticaIettrev"));
        if (!"".equals(Pagina.getParamStr(request, "valPrevistoFuturoIettrev")))
            itemEstruturaRev.setValPrevistoFuturoIettrev(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "valPrevistoFuturoIettrev"))));
        else
            itemEstruturaRev.setValPrevistoFuturoIettrev(null);

        if (!"".equals(Pagina.getParamStr(request, "dataR1rev")))
            itemEstruturaRev.setDataR1rev(Pagina.getParamDataBanco(request, "dataR1rev"));
        else
            itemEstruturaRev.setDataR1rev(null);
        
        if (!"".equals(Pagina.getParamStr(request, "dataR2rev")))
            itemEstruturaRev.setDataR2rev(Pagina.getParamDataBanco(request, "dataR2rev"));
        else
            itemEstruturaRev.setDataR2rev(null);
        
        if (!"".equals(Pagina.getParamStr(request, "dataR3rev")))
            itemEstruturaRev.setDataR3rev(Pagina.getParamDataBanco(request, "dataR3rev"));
        else
            itemEstruturaRev.setDataR3rev(null);
        
        if (!"".equals(Pagina.getParamStr(request, "dataR4rev")))
            itemEstruturaRev.setDataR4rev(Pagina.getParamDataBanco(request, "dataR4rev"));
        else
            itemEstruturaRev.setDataR4rev(null);
        
        if (!"".equals(Pagina.getParamStr(request, "dataR5rev")))
            itemEstruturaRev.setDataR5rev(Pagina.getParamDataBanco(request, "dataR5rev"));
        else
            itemEstruturaRev.setDataR5rev(Pagina.getParamDataBanco(request, "dataR5rev"));
        
        itemEstruturaRev.setDescricaoR1rev(Pagina.getParamStr(request, "descricaoR1rev"));
        itemEstruturaRev.setDescricaoR2rev(Pagina.getParamStr(request, "descricaoR2rev"));
        itemEstruturaRev.setDescricaoR3rev(Pagina.getParamStr(request, "descricaoR3rev"));
        itemEstruturaRev.setDescricaoR4rev(Pagina.getParamStr(request, "descricaoR4rev"));
        itemEstruturaRev.setDescricaoR5rev(Pagina.getParamStr(request, "descricaoR5rev"));

        
        setFuncoesAcompanhamentoItemEstrutura(request, itemEstruturaRev);
    }

    /**
     * Devolve um int indicando em qual nível da hierarquia de itens o Item se encontra
     * @param itemEstrutura
     * @return
     * @throws ECARException
     */
    private int getNivel(ItemEstruturarevisaoIettrev itemEstruturaRev) throws ECARException {
        int nivel = 1;

        while (itemEstruturaRev.getItemEstruturarevisaoIettrev() != null) {
            itemEstruturaRev = itemEstruturaRev.getItemEstruturarevisaoIettrev();
            nivel++;

        }

        return nivel;
    }    
    
    
    /**
     * Adiciona elementos à coleção de Funções de Acompanhamento de um
     * ItemEstrutura
     * 
     * @param request
     * @param itemEstruturaRev
     * @throws ECARException
     */
    public void setFuncoesAcompanhamentoItemEstrutura(HttpServletRequest request, ItemEstruturarevisaoIettrev itemEstruturaRev) throws ECARException {
        //Limpa a collection
        itemEstruturaRev.setIettUsutpfuacrevIettutfars(new HashSet());
        // Descobre a Estrutura do item
        EstruturaEtt estrutura = itemEstruturaRev.getEstruturaEttrev();
        // Descobre as funções de acompanhamento de uma estrutura
        Set funcoesAcomp = estrutura.getEstrutTpFuncAcmpEtttfas();
        // Itera pelas funções buscando no request o valor passado.
        if (funcoesAcomp != null) {
            Iterator it = funcoesAcomp.iterator();
            while (it.hasNext()) {
                EstrutTpFuncAcmpEtttfa funcao = (EstrutTpFuncAcmpEtttfa) it.next();
                if (!"".equals(Pagina.getParamStr(request, "Fun" + funcao.getTipoFuncAcompTpfa().getCodTpfa()+"rev"))) {
                    IettUsutpfuacrevIettutfar funcaoItemEstruturaRev = new IettUsutpfuacrevIettutfar();
                    funcaoItemEstruturaRev.setItemEstruturarevisaoIettrev(itemEstruturaRev);
                    funcaoItemEstruturaRev.setTipoFuncAcompTpfa(funcao.getTipoFuncAcompTpfa());
                    funcaoItemEstruturaRev.setUsuarioUsu((UsuarioUsu) this.buscar(UsuarioUsu.class, 
                        Long.valueOf(Pagina.getParamStr(request, "Fun" + funcao.getTipoFuncAcompTpfa().getCodTpfa()+"rev"))));
                    itemEstruturaRev.getIettUsutpfuacrevIettutfars().add(funcaoItemEstruturaRev);
                }
            }
        }
    }
    
    /**
     * Retorna o valor de um atributo em um itemEstrutura
     * @param itemEstrutura
     * @param nomeAtributo
     * @param fkAtributo
     * @return
     * @throws ECARException
     */
    public String getValorAtributoItemEstrutura(
            ItemEstruturarevisaoIettrev itemEstrutura, String nomeAtributo,
            String fkAtributo) throws ECARException {
    	
    	Object retorno = Util.invocaGet(itemEstrutura, nomeAtributo+"rev");
        if (retorno != null) {
            if (fkAtributo != null) {
                retorno = Util.invocaGet(retorno, fkAtributo);
                if (retorno != null)
                    return retorno.toString();
                else
                    return "";
            } else {
                if (retorno.getClass().equals(Timestamp.class)
                        || retorno.getClass().equals(Date.class))
                    retorno = Data.parseDate((Date) retorno);
                return retorno.toString();
            }
        } else
            return "";
    }
    /**
     * Retorna o usuário associoado a uma função de acompanhamento em um itemEstrutura
     * @param itemEstrutura
     * @param funAcomp
     * @return
     * @throws ECARException
     */
    public UsuarioUsu getValorFunAcompItemEstrutura(ItemEstruturarevisaoIettrev itemEstrutura, TipoFuncAcompTpfa funAcomp) 
    throws ECARException{
        IettUsutpfuacrevIettutfar ieUsuTf = new IettUsutpfuacrevIettutfar();
        ieUsuTf.setItemEstruturarevisaoIettrev(itemEstrutura);
        ieUsuTf.setTipoFuncAcompTpfa(funAcomp);
        try{
            List result = this.pesquisar(ieUsuTf, null);
            if( result != null){
                Iterator it = result.iterator();
                if(it.hasNext())
                    return ((IettUsutpfuacrevIettutfar) it.next()).getUsuarioUsu();
                else
                    return null;
            } else {
                return null;
            }            
        } catch(ECARException e){
        	this.logger.error(e);
            return null;
        }
             
    }
    
    
    /**
     * Salva um registro de itemEstrutura. Salva à parte os item-estrutura-funcao-tipo-acomp
     * devido a chave composta. (que deve ser setada depois de gravar o item)
     * 
     * @param itemEstrutura
     * @throws ECARException
     */
    public void salvar(ItemEstruturarevisaoIettrev itemEstrutura) throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

            itemEstrutura.setDataInclusaoIettrev(Data.getDataAtual());
	        List filhos = new ArrayList();
	        if ((itemEstrutura.getIettUsutpfuacrevIettutfars() != null) && (itemEstrutura.getIettUsutpfuacrevIettutfars().size() > 0))
	            filhos.addAll(itemEstrutura.getIettUsutpfuacrevIettutfars());
	
	        //
	        // controlar as permissoes passando o item e a lista das funcoes de acompanhamento velhas (vai ser uma lista vazia)
	        //
//			new ControlePermissao().atualizarPermissoesItemEstrutura(itemEstrutura, null);
			
			
			// gravar permissão para o usuário que criou o item 
/*			ItemEstrutUsuarioIettus itemEstrutUsuario = new ItemEstrutUsuarioIettus();
	
			itemEstrutUsuario.setItemEstruturaIett(itemEstrutura);
			itemEstrutUsuario.setItemEstruturaIettOrigem(itemEstrutura);
			itemEstrutUsuario.setCodTpPermIettus(Dominios.PERMISSAO_USUARIO);
			itemEstrutUsuario.setUsuarioUsu(itemEstrutura.getUsuarioUsuByCodUsuIncIett());
	
			itemEstrutUsuario.setIndLeituraIettus("S");
			itemEstrutUsuario.setIndEdicaoIettus("S");
			itemEstrutUsuario.setIndExcluirIettus("S");
			
			itemEstrutUsuario.setIndAtivMonitIettus("N");
			itemEstrutUsuario.setIndDesatMonitIettus("N");
			itemEstrutUsuario.setIndBloqPlanIettus("N");
			itemEstrutUsuario.setIndDesblPlanIettus("N");
			itemEstrutUsuario.setIndInfAndamentoIettus("N");
			itemEstrutUsuario.setIndEmitePosIettus("N");
			itemEstrutUsuario.setIndProxNivelIettus("N");
			
			itemEstrutUsuario.setDataInclusaoIettus(Data.getDataAtual());
*/			
            session.save(itemEstrutura);
			objetos.add(itemEstrutura);

			Iterator it = filhos.iterator();
			while(it.hasNext()) {
			    PaiFilho object = (PaiFilho) it.next();
			    object.atribuirPKPai();
			    //salva os filhos
			    session.save(object);
				objetos.add(object);
			}

/*			session.save(itemEstrutUsuario);
			objetos.add(itemEstrutUsuario);
*/
			tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC");
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
     * 
     * @param colecao
     * @return
     * @throws ECARException
     */
	public List ordenaSetByCompare(Set colecao) throws ECARException{
		List retorno = new ArrayList();
		if(colecao != null){
			retorno = new ArrayList(colecao);
			
			Collections.sort(retorno,
					new Comparator(){

						public int compare(Object arg1, Object arg2) {
							IettIndResulRevIettrr iettirr1 = (IettIndResulRevIettrr) arg1;
							IettIndResulRevIettrr iettirr2 = (IettIndResulRevIettrr) arg2;
							
							ItemEstrtIndResulIettr indRev1 = iettirr1.getItemEstrtIndResulIettr();
							ItemEstrtIndResulIettr indRev2 = iettirr2.getItemEstrtIndResulIettr();
							
							if(indRev1 != null && indRev2 != null){
								return indRev1.getNomeIettir().compareToIgnoreCase(indRev2.getNomeIettir());
							}
							else if(indRev1 != null && indRev2 == null){
								return 1;								
							}
							else if(indRev1 == null && indRev2 != null){
								return -1;
							}
							else{
								return 0;
							}
						}
			});
		}
		else {
			retorno = new ArrayList();
		}
		
		return retorno;
	}
}
