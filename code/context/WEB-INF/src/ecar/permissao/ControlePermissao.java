/*
 * Created on 08/06/2005
 *
 */
package ecar.permissao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import comum.util.Data;

import ecar.dao.ConfiguracaoDao;
import ecar.dao.ItemEstrutUsuarioDao;
import ecar.exception.ECARException;
import ecar.historico.HistoricoIettus;
import ecar.pojo.EstrutTpFuncAcmpEtttfa;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstrutUsuarioIettus;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.TipoFuncAcompTpfa;

/**
 * @author garten
 */
public class ControlePermissao {
    
    public static final String PERMISSAO_FUNCAO_ACOMPANHAMENTO = "F";
    public static final String PERMISSAO_USUARIO = "U";
    public static final String PERMISSAO_GRUPO = "G";
    public static final String SIM = "S";
    public static final String NAO = "N";
        

    
	/**
	 * Atualiza todos os registros de item_estrut_usuario_iettus para uma dado item da Estrutura.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstruturaIett item
	 * @param Set fuacAnteriores - Coleção de fuac existente antes da alteração do item
	 * @param Session session
	 * @param boolean novoItem
	 */
	public void atualizarPermissoesItemEstrutura(ItemEstruturaIett item, Set fuacAnteriores, Session session, boolean novoItem, HttpServletRequest request) throws ECARException {
		
		Iterator itFuncoesAcompanhamento = item.getEstruturaEtt().getEstrutTpFuncAcmpEtttfas().iterator();
		
		/****** historico *****/
        HistoricoIettus historico = new HistoricoIettus(new ItemEstrutUsuarioIettus(), 
				HistoricoIettus.excluirPermissoes,
				session,
				new ConfiguracaoDao(request),
				request);
        /****** historico *****/
		
		while(itFuncoesAcompanhamento.hasNext()){
			
			TipoFuncAcompTpfa funcaoAcompanhamento = ((EstrutTpFuncAcmpEtttfa) itFuncoesAcompanhamento.next()).getTipoFuncAcompTpfa();
			ItemEstUsutpfuacIettutfa fuacNovo = getUsuarioAcompanhamento(item, funcaoAcompanhamento);
			ItemEstUsutpfuacIettutfa fuacAnterior = getUsuarioAcompanhamento(fuacAnteriores, item, funcaoAcompanhamento);
			if(fuacNovo == null && fuacAnterior != null) {
				excluirPermissoesItem(item, fuacAnterior, session, request, historico);
			}
		}
		
		itFuncoesAcompanhamento = item.getEstruturaEtt().getEstrutTpFuncAcmpEtttfas().iterator();

		while(itFuncoesAcompanhamento.hasNext()){
			
			EstrutTpFuncAcmpEtttfa funcaoAcomp = (EstrutTpFuncAcmpEtttfa) itFuncoesAcompanhamento.next(); 
			TipoFuncAcompTpfa funcaoAcompanhamento = funcaoAcomp.getTipoFuncAcompTpfa();
			ItemEstUsutpfuacIettutfa fuacNovo = getUsuarioAcompanhamento(item, funcaoAcompanhamento);
			ItemEstUsutpfuacIettutfa fuacAnterior = getUsuarioAcompanhamento(fuacAnteriores, item, funcaoAcompanhamento);
			if(fuacNovo != null){
				if(fuacAnterior == null){
					String manterProximoNivel = funcaoAcomp.getIndManterProximoNivelEtttfa();
					ItemEstrutUsuarioIettus iettus = incluirPermissaoItem(item, fuacNovo,manterProximoNivel);
					session.save(iettus);

					item.getEstruturaEtt();

					//Manter a permissão no próximo nível de acordo com o definido na 'Função de Acompanhamento na Estrutura'
					if(manterProximoNivel != null && manterProximoNivel.equals("S")){
						incluirPermissoesItensFilho(item, fuacNovo, session,manterProximoNivel);
					}

				} else {
					if(fuacNovo.getUsuarioUsu() != null && fuacAnterior.getUsuarioUsu() != null && !fuacNovo.getUsuarioUsu().equals(fuacAnterior.getUsuarioUsu())){
						alterarPermissoesItem(item, fuacAnterior, session, request, historico);						
					} else if(fuacNovo.getSisAtributoSatb() != null && fuacAnterior.getSisAtributoSatb() != null && !fuacNovo.getSisAtributoSatb().equals(fuacAnterior.getSisAtributoSatb())){
						alterarPermissoesItem(item, fuacAnterior, session, request, historico);						
					} else if(fuacNovo.getUsuarioUsu() != null && fuacAnterior.getUsuarioUsu() == null){
						alterarPermissoesItem(item, fuacAnterior, session, request, historico);
					} else if(fuacNovo.getSisAtributoSatb() != null && fuacAnterior.getSisAtributoSatb() == null){
						alterarPermissoesItem(item, fuacAnterior, session, request, historico);
					}
				}
			}							
		}
		
		if(novoItem) {
			Set iettusCopiaPai = copiarPermissoesItemPai(item);
			
			Iterator it = iettusCopiaPai.iterator();
			while(it.hasNext()) {
				ItemEstrutUsuarioIettus iettus = (ItemEstrutUsuarioIettus)it.next();
				session.save(iettus);
			}
		}
	}
	
	/**
	 * Atualiza todos os registros de item_estrut_usuario_iettus para uma dado item da Estrutura usado como modelo.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstruturaIett item
	 * @param Set fuacAnteriores - Coleção de fuac existente antes da alteração do item
	 * @param Session session
	 * @param boolean novoItem
	 */
	public Set getPermissoesItemEstruturaUsarModelo(ItemEstruturaIett item, HttpServletRequest request) throws ECARException {
		Set itemEstrutUsuarioIettus = new HashSet();
		Iterator itFuncoesAcompanhamento = item.getEstruturaEtt().getEstrutTpFuncAcmpEtttfas().iterator();
		
		while(itFuncoesAcompanhamento.hasNext()){
			
			EstrutTpFuncAcmpEtttfa funcaoAcomp = (EstrutTpFuncAcmpEtttfa) itFuncoesAcompanhamento.next(); 
			TipoFuncAcompTpfa funcaoAcompanhamento = funcaoAcomp.getTipoFuncAcompTpfa();
			ItemEstUsutpfuacIettutfa fuacNovo = getUsuarioAcompanhamento(item, funcaoAcompanhamento);
			
			if(fuacNovo != null){
				String manterProximoNivel = funcaoAcomp.getIndManterProximoNivelEtttfa();
				ItemEstrutUsuarioIettus iettus = incluirPermissaoItem(item, fuacNovo,manterProximoNivel);
				itemEstrutUsuarioIettus.add(iettus);

				item.getEstruturaEtt();
			}							
		}
		
		Set iettusCopiaPai = copiarPermissoesItemPai(item);
		
		Iterator it = iettusCopiaPai.iterator();
		while(it.hasNext()) {
			ItemEstrutUsuarioIettus iettus = (ItemEstrutUsuarioIettus)it.next();
			itemEstrutUsuarioIettus.add(iettus);
		}
		
		return itemEstrutUsuarioIettus;
	}
	
	
    /**
     * Atualiza todos os registros de item_estrut_usuario_iettus para uma dada funcao de acompanhamento.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param TipoFuncAcompTpfa funcao
     * @throws ECARException
     */
    public void atualizarPermissoesPorFuncaoDeAcompanhamento(TipoFuncAcompTpfa funcao) throws ECARException {
        
        ItemEstrutUsuarioIettus iettus;
        
        Iterator it = funcao.getItemEstrutUsuarioIettuses().iterator();
        while (it.hasNext()) {
            iettus = (ItemEstrutUsuarioIettus) it.next();
            
            if (PERMISSAO_FUNCAO_ACOMPANHAMENTO.equals(iettus.getCodTpPermIettus()) &&
                iettus.getTipoFuncAcompTpfa().equals(funcao)) {
                
                iettus.setIndLeituraIettus(getIndLeitura());
                iettus.setIndExcluirIettus(getIndExcluir(funcao));
                iettus.setIndEdicaoIettus(getIndEdicao(funcao));
                iettus.setIndLeituraParecerIettus(getIndLeituraParecer(funcao));
                iettus.setIndAtivMonitIettus(getIndAtivMonit(funcao));
                iettus.setIndDesatMonitIettus(getIndDesatMonit(funcao));
                iettus.setIndBloqPlanIettus(getIndBloqPlan(funcao));
                iettus.setIndDesblPlanIettus(getIndDesblPlan(funcao));
                iettus.setIndInfAndamentoIettus(getIndInfAndamento(funcao));
                iettus.setIndEmitePosIettus(getIndEmitePos(funcao));
                iettus.setIndProxNivelIettus(getIndProxNivel());
            }
        }
    }
    
	/**
	 * Inclui permissões para todos os filhos de um item.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstruturaIett item
	 * @param ItemEstUsutpfuacIettutfa fuac
	 * @param Session session
	 */
	private void incluirPermissoesItensFilho(ItemEstruturaIett item, ItemEstUsutpfuacIettutfa fuac, Session session,String manterProximoNivel) {

		List itensFilhos = getDescendentesItem(item);
		
		if(itensFilhos != null){
			Iterator it = itensFilhos.iterator();
			while(it.hasNext()){
				ItemEstruturaIett itemFilho = (ItemEstruturaIett) it.next();
				if(getPermissao(itemFilho, item, fuac.getTipoFuncAcompTpfa()) == null){
					ItemEstrutUsuarioIettus permissaoItemFilho = incluirPermissaoItem(itemFilho, fuac,manterProximoNivel);
					permissaoItemFilho.setItemEstruturaIettOrigem(item);

					session.save(permissaoItemFilho);
				}
			}			
		}			
	}
	
	/**
	 * Retorna um objeto itemEstrutUsuario a partir de um item , item de origem e função de acompanhamento.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstruturaIett item
	 * @param ItemEstruturaIett itemOrigem
	 * @param TipoFuncAcompTpfa funcaoAcomp
	 * @return ItemEstrutUsuarioIettus
	 */
	public ItemEstrutUsuarioIettus getPermissao(ItemEstruturaIett item, ItemEstruturaIett itemOrigem, TipoFuncAcompTpfa funcaoAcomp){
		Iterator it = item.getItemEstrutUsuarioIettusesByCodIett().iterator();
		while(it.hasNext()){
			ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
			if(ieUsuario.getItemEstruturaIett().equals(item) && 
					ieUsuario.getItemEstruturaIettOrigem().equals(itemOrigem) && 
					ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO) && 
					ieUsuario.getTipoFuncAcompTpfa().equals(funcaoAcomp)){
					return ieUsuario;
			}
		}
		return null;
	}
	
    /**
     * Retorna os descendentes de um item. Não é utilizado o método getDescendentes de ItemEstruturaDao pois ele
     * faz um refresh no objeto e nesta classe os objetos estão sendo alterados em memória e por isso não é 
     * desejado que sejam recarregados.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett itemEstrutura
     * @return List
     */
    public List getDescendentesItem(ItemEstruturaIett itemEstrutura) {
        List retorno = new ArrayList();

        if (itemEstrutura.getItemEstruturaIetts() != null) {
            
            Iterator it = itemEstrutura.getItemEstruturaIetts().iterator();
            while (it.hasNext()) {
                ItemEstruturaIett itemEstruturaFilho = (ItemEstruturaIett) it.next();
                
                if (!retorno.contains(itemEstruturaFilho))
                    retorno.add(itemEstruturaFilho);
                retorno.addAll(this.getDescendentesItem(itemEstruturaFilho));
            }
        }
        return retorno;
    }
	
    /**
     * Seleciona todas as permissões do pai do item atual onde indProxNivel='S'.
     * Gerando uma cópia das permissões do pai para o item atual.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item - (itemPai)
     * @return Set - (Iettus)
     */
    protected Set copiarPermissoesItemPai(ItemEstruturaIett item) {
    	Set iettusPai = new HashSet();
    	Set iettusHeranca = new HashSet();
    	
    	if (item.getItemEstruturaIett() != null) {
    	    
	    	iettusPai = item.getItemEstruturaIett().getItemEstrutUsuarioIettusesByCodIett();
	    	
	    	if (iettusPai!=null) {
	    	
		    	Iterator it = iettusPai.iterator();
		    	
		    	while(it.hasNext()) {
		    		ItemEstrutUsuarioIettus pai = (ItemEstrutUsuarioIettus) it.next();
		    		
	    			if("S".equals(pai.getIndProxNivelIettus())) {
			    		ItemEstrutUsuarioIettus itemEstUsuario = copiarItemEstUsuario(pai);
	
			    		itemEstUsuario.setItemEstruturaIett(item);
			    		
		    			iettusHeranca.add(itemEstUsuario);			
		    		}
		    	}
	    	}
    	}
	
	    return iettusHeranca;
    }
    
    /**
     * Devolve um registro de permissao iettus a partir de um item e de uma funcao de acompanhamento.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item
     * @param ItemEstUsutpfuacIettutfa fuac
     * @return ItemEstrutUsuarioIettus
     */
    protected ItemEstrutUsuarioIettus incluirPermissaoItem(ItemEstruturaIett item, ItemEstUsutpfuacIettutfa fuac, String manterProximoNivel) {
        
        return new ItemEstrutUsuarioIettus(
				item, 
				item, 
				fuac.getUsuarioUsu(), 
				ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO,
				fuac.getSisAtributoSatb(), 
				fuac.getTipoFuncAcompTpfa(), 
				getIndLeitura(),
				getIndEdicao(fuac.getTipoFuncAcompTpfa()),
				getIndExcluir(fuac.getTipoFuncAcompTpfa()),
				getIndAtivMonit(fuac.getTipoFuncAcompTpfa()),
				getIndDesatMonit(fuac.getTipoFuncAcompTpfa()),
				getIndBloqPlan(fuac.getTipoFuncAcompTpfa()),
				getIndDesblPlan(fuac.getTipoFuncAcompTpfa()),
				getIndInfAndamento(fuac.getTipoFuncAcompTpfa()),
				getIndEmitePos(fuac.getTipoFuncAcompTpfa()),
				manterProximoNivel,
				Data.getDataAtual(), 
				getIndLeituraParecer(fuac.getTipoFuncAcompTpfa())
		);
    }
    
	/**
	 * Procura na lista de fuac de um item um objeto fuac para um item e uma função de acomanhamento.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstruturaIett item
	 * @param TipoFuncAcompTpfa funcao
	 * @return ItemEstUsutpfuacIettutfa
	 */
	protected ItemEstUsutpfuacIettutfa getUsuarioAcompanhamento(ItemEstruturaIett item, TipoFuncAcompTpfa funcao){
		
		if(item.getItemEstUsutpfuacIettutfas() != null){
			Iterator it = item.getItemEstUsutpfuacIettutfas().iterator();
			while(it.hasNext()){
				ItemEstUsutpfuacIettutfa fuac = (ItemEstUsutpfuacIettutfa) it.next();
				if(fuac.getItemEstruturaIett().equals(item) && fuac.getTipoFuncAcompTpfa().equals(funcao))
					return fuac;
			}			
		}
		return null;
		
	}

	/**
	 * Procura na lista de fuac um objeto fuac para um item e uma função de acomanhamento.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
     * @param Set lFuac
	 * @param ItemEstruturaIett item
	 * @param TipoFuncAcompTpfa funcao
	 * @return ItemEstUsutpfuacIettutfa
	 */
	protected ItemEstUsutpfuacIettutfa getUsuarioAcompanhamento(Set lFuac, ItemEstruturaIett item, TipoFuncAcompTpfa funcao){
		
		if(lFuac != null){
			Iterator it = lFuac.iterator();
			while(it.hasNext()){
				ItemEstUsutpfuacIettutfa fuac = (ItemEstUsutpfuacIettutfa) it.next();
				if(fuac.getItemEstruturaIett().equals(item) && fuac.getTipoFuncAcompTpfa().equals(funcao))
					return fuac;
			}			
		}
		return null;
		
	}

	
    /**
     * Altera a lista de cod_iett_orig do item, pesquisando o usuario anterior e trocando para o novo usuario.
     * O novo usuario está no fuac associado no item.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item
     * @param ItemEstUsutpfuacIettutfa fuacAnterior
     * @param Session session
     */
    protected void alterarPermissoesItem(ItemEstruturaIett item, ItemEstUsutpfuacIettutfa fuacAnterior, Session session, HttpServletRequest request, HistoricoIettus historico) throws ECARException {
    	try
    	{
	        Iterator it = item.getItemEstrutUsuarioIettusesByCodIettOrigem().iterator();
	        
	        /******** Historico *********/
	        historico.gerarMaster(HistoricoIettus.alterarPermissoes);
	        /******** Historico *********/
	        
			while(it.hasNext()){
				ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
				ItemEstrutUsuarioIettus old = (ItemEstrutUsuarioIettus)ieUsuario.clone();
				if(ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO)
						&& 
						( (ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(fuacAnterior.getUsuarioUsu()))
						 || (ieUsuario.getSisAtributoSatb() != null && ieUsuario.getSisAtributoSatb().equals(fuacAnterior.getSisAtributoSatb())) )
						
						&& ieUsuario.getTipoFuncAcompTpfa().equals(fuacAnterior.getTipoFuncAcompTpfa())){
					ieUsuario.setUsuarioUsu(getUsuarioAcompanhamento(item, fuacAnterior.getTipoFuncAcompTpfa()).getUsuarioUsu());
					ieUsuario.setSisAtributoSatb(getUsuarioAcompanhamento(item, fuacAnterior.getTipoFuncAcompTpfa()).getSisAtributoSatb());
					/******** Historico *********/
		            historico.gerarHistorico(old);
		        	/******** Historico *********/				
					
					session.update(ieUsuario);
				}
			}
    	}catch (HibernateException e) {
			throw new ECARException("erro.hibernateException"); 			
		}
    }
    
    /**
     * Exclui os cod_item_orig da lista do item, utilizando o usuario associado à funcao de acompanhamento anterior.
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item
     * @param ItemEstUsutpfuacIettutfa fuacAnterior
     * @param Session session
     */
    protected void excluirPermissoesItem(ItemEstruturaIett item, ItemEstUsutpfuacIettutfa fuacAnterior, Session session, HttpServletRequest request, HistoricoIettus historico) throws ECARException {
		Iterator it = item.getItemEstrutUsuarioIettusesByCodIettOrigem().iterator();
		while(it.hasNext()){
			ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) it.next();
			ItemEstrutUsuarioIettus old = (ItemEstrutUsuarioIettus) ieUsuario.clone();
			if(ieUsuario.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO)
					&& 
						( (ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(fuacAnterior.getUsuarioUsu()))
						 || (ieUsuario.getSisAtributoSatb() != null && ieUsuario.getSisAtributoSatb().equals(fuacAnterior.getSisAtributoSatb())) )
						
					&& ieUsuario.getTipoFuncAcompTpfa().equals(fuacAnterior.getTipoFuncAcompTpfa())){
								
	            /******** Historico *********/
	            historico.gerarHistorico(old);
	        	/******** Historico *********/
				
				session.delete(ieUsuario);
			}
		}
    }
    
    /**
     * Recebe um itemEstrutUsuarioIettus persistente e devolve uma copia transiente.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param itemEstrutUsuarioIettus itemEstUsuario
     * @return itemEstrutUsuarioIettus
     */
    protected ItemEstrutUsuarioIettus copiarItemEstUsuario (ItemEstrutUsuarioIettus itemEstUsuario) {
    	return new ItemEstrutUsuarioIettus(
				itemEstUsuario.getItemEstruturaIett(),
				itemEstUsuario.getItemEstruturaIettOrigem(),
				itemEstUsuario.getUsuarioUsu(),
				itemEstUsuario.getCodTpPermIettus(),
				itemEstUsuario.getSisAtributoSatb(),
				itemEstUsuario.getTipoFuncAcompTpfa(),
				itemEstUsuario.getIndLeituraIettus(),
				itemEstUsuario.getIndEdicaoIettus(),
				itemEstUsuario.getIndExcluirIettus(),
				itemEstUsuario.getIndAtivMonitIettus(),
				itemEstUsuario.getIndDesatMonitIettus(),
				itemEstUsuario.getIndBloqPlanIettus(),
				itemEstUsuario.getIndDesblPlanIettus(),
				itemEstUsuario.getIndInfAndamentoIettus(),
				itemEstUsuario.getIndEmitePosIettus(),
				itemEstUsuario.getIndProxNivelIettus(),
				Data.getDataAtual(),
				itemEstUsuario.getIndLeituraParecerIettus()
		);    	
    }
        
    
    /**
     * Atualizar os dados de um itemEstrutUsuarioIettus destino de acordo com o origem.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstrutUsuarioIettus itemEstrutUsuarioIettusOrigem
     * @param ItemEstrutUsuarioIettus itemEstrutUsuarioIettusDestino
     * @return ItemEstrutUsuarioIettus - (itemEstrutUsuarioIettusDestino)
     */
    protected ItemEstrutUsuarioIettus atualizarItemEstUsuario (ItemEstrutUsuarioIettus itemEstrutUsuarioIettusOrigem, ItemEstrutUsuarioIettus itemEstrutUsuarioIettusDestino){
    	itemEstrutUsuarioIettusDestino.setIndLeituraIettus( itemEstrutUsuarioIettusOrigem.getIndLeituraIettus() );
    	itemEstrutUsuarioIettusDestino.setIndEdicaoIettus( itemEstrutUsuarioIettusOrigem.getIndEdicaoIettus() );
    	itemEstrutUsuarioIettusDestino.setIndExcluirIettus( itemEstrutUsuarioIettusOrigem.getIndExcluirIettus() );
    	itemEstrutUsuarioIettusDestino.setIndLeituraParecerIettus( itemEstrutUsuarioIettusOrigem.getIndLeituraParecerIettus() );
    	itemEstrutUsuarioIettusDestino.setIndAtivMonitIettus( itemEstrutUsuarioIettusOrigem.getIndAtivMonitIettus() );
    	itemEstrutUsuarioIettusDestino.setIndDesatMonitIettus( itemEstrutUsuarioIettusOrigem.getIndDesatMonitIettus() );
    	itemEstrutUsuarioIettusDestino.setIndBloqPlanIettus( itemEstrutUsuarioIettusOrigem.getIndBloqPlanIettus() );
    	itemEstrutUsuarioIettusDestino.setIndDesblPlanIettus( itemEstrutUsuarioIettusOrigem.getIndDesblPlanIettus() );
    	itemEstrutUsuarioIettusDestino.setIndInfAndamentoIettus( itemEstrutUsuarioIettusOrigem.getIndInfAndamentoIettus() );
    	itemEstrutUsuarioIettusDestino.setIndEmitePosIettus( itemEstrutUsuarioIettusOrigem.getIndEmitePosIettus() );
    	itemEstrutUsuarioIettusDestino.setIndProxNivelIettus( itemEstrutUsuarioIettusOrigem.getIndProxNivelIettus() );
    	itemEstrutUsuarioIettusDestino.setIndLeituraParecerIettus( itemEstrutUsuarioIettusOrigem.getIndLeituraParecerIettus() );
    	
    	return itemEstrutUsuarioIettusDestino;
    }
    
	/**
	 * Propagar permissões para todos os filhos de um item caso o itemEstrutUsuario.getIndProxNivelIettus() seja 'S',
	 * Utilizado na inclusão de permissão de acesso de itens da estrutura.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstrutUsuarioIettus itemEstrutUsuarioIettus - itemEstrutUsuarioIettus que está sendo incluído
	 * @param Session session
	 */
	public void propagarPermissoesItensFilhos(ItemEstrutUsuarioIettus itemEstrutUsuarioIettus, Session session) {
		
		if("S".equals(itemEstrutUsuarioIettus.getIndProxNivelIettus())) {
			
			ItemEstruturaIett item = itemEstrutUsuarioIettus.getItemEstruturaIett();
	
			List itensFilhos = getDescendentesItem(item);

			if(itensFilhos != null){
				List iettusAux = new ArrayList();
				Iterator it = itensFilhos.iterator();
				while(it.hasNext()){
					ItemEstruturaIett itemFilho = (ItemEstruturaIett) it.next();
					
					ItemEstrutUsuarioIettus itemEstrutUsuarioIettusFilho = this.copiarItemEstUsuario(itemEstrutUsuarioIettus);
					
					itemEstrutUsuarioIettusFilho.setItemEstruturaIett(itemFilho);
					if(!iettusAux.contains(itemEstrutUsuarioIettusFilho)) {
						session.save(itemEstrutUsuarioIettusFilho);
						iettusAux.add(itemEstrutUsuarioIettusFilho);
					}
				}			
			}	
		}
	}

        
    
	/**
	 * Atualiza permissões para todos os filhos de um item.
	 * Utilizado na alteração de permissão de acesso de itens da estrutura.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstrutUsuarioIettus itemEstrutUsuarioIettusNovo - itemEstrutUsuarioIettus que está sendo alterado
	 * @param String indProxNivelIettusAnterior - itemEstrutUsuarioIettus que foi alterado
	 * @param Session session
	 * @throws HibernateException
	 */
	public void atualizarPermissoesItensFilhos(ItemEstrutUsuarioIettus itemEstrutUsuarioIettusNovo, 
			String indProxNivelIettusAnterior, Session session, HttpServletRequest request, HistoricoIettus historico) throws HibernateException, ECARException {

		ItemEstruturaIett item = itemEstrutUsuarioIettusNovo.getItemEstruturaIett();

		List itensFilhos = getDescendentesItem(item);
		List listIettusAtualizados = new ArrayList();

		if(itensFilhos != null){
			Iterator it = itensFilhos.iterator();
			while(it.hasNext()){
				ItemEstruturaIett itemFilho = (ItemEstruturaIett) it.next();
				
				ItemEstrutUsuarioIettus itemEstrutUsuarioIettusFilho = this.copiarItemEstUsuario(itemEstrutUsuarioIettusNovo);
			
				itemEstrutUsuarioIettusFilho.setItemEstruturaIett(itemFilho);
				itemEstrutUsuarioIettusFilho.setIndExclusaoPosHistorico(Boolean.FALSE); // não apaga pelo historico (Mantis #2156)
				
				// se a opção "próximo nível" anterior não for mais 'S' remover as permissões dos itens filhos
				if(("S".equals(indProxNivelIettusAnterior) && "N".equals(itemEstrutUsuarioIettusNovo.getIndProxNivelIettus()))) {
					
					if(itemFilho.getItemEstrutUsuarioIettusesByCodIett() != null) { 

						Iterator itEstrutAnterior = itemFilho.getItemEstrutUsuarioIettusesByCodIett().iterator();
						while(itEstrutAnterior.hasNext()){
							ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) itEstrutAnterior.next();
							
							if(ieUsuario.getItemEstruturaIettOrigem().equals(item)) {
								if(ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(itemEstrutUsuarioIettusNovo.getUsuarioUsu())) {
									
									ieUsuario.setUsuManutencao(itemEstrutUsuarioIettusNovo.getUsuManutencao());
						            /******** Historico *********/
						            historico.gerarHistorico(ieUsuario);
						        	/******** Historico *********/									
									
									session.delete(ieUsuario);
								}
								else if(ieUsuario.getCodTpPermIettus().equals(PERMISSAO_GRUPO) 
										&& (ieUsuario.getSisAtributoSatb() != null && itemEstrutUsuarioIettusNovo.getSisAtributoSatb() != null &&
												ieUsuario.getSisAtributoSatb().getCodSatb().longValue() == itemEstrutUsuarioIettusNovo.getSisAtributoSatb().getCodSatb().longValue())){
									
									ieUsuario.setUsuManutencao(itemEstrutUsuarioIettusNovo.getUsuManutencao());
						            /******** Historico *********/
						            historico.gerarHistorico(ieUsuario);
						        	/******** Historico *********/									
									
									session.delete(ieUsuario);
								}
							}
						}
					}
				}
				else if("S".equals(itemEstrutUsuarioIettusNovo.getIndProxNivelIettus())) {

					if(itemFilho.getItemEstrutUsuarioIettusesByCodIett() != null) { 

						boolean achou = false;
						
						Iterator itAux = itemFilho.getItemEstrutUsuarioIettusesByCodIett().iterator();
						while(itAux.hasNext()) {
							ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) itAux.next();
							
							if(ieUsuario.getItemEstruturaIettOrigem().equals(item)) {
								if(ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(itemEstrutUsuarioIettusNovo.getUsuarioUsu())) {
									
									ItemEstrutUsuarioIettus old = (ItemEstrutUsuarioIettus) ieUsuario.clone();
									old.setUsuManutencao(itemEstrutUsuarioIettusNovo.getUsuManutencao());
						            /******** Historico *********/
						            historico.gerarHistorico(old);
						        	/******** Historico *********/
						            
									achou = true;
									ieUsuario = this.atualizarItemEstUsuario(itemEstrutUsuarioIettusFilho, ieUsuario);						            
									
									session.update(ieUsuario);
								}
								else if(ieUsuario.getCodTpPermIettus().equals(PERMISSAO_GRUPO) 
										&& (ieUsuario.getSisAtributoSatb() != null && itemEstrutUsuarioIettusNovo.getSisAtributoSatb() != null &&
												ieUsuario.getSisAtributoSatb().getCodSatb().longValue() == itemEstrutUsuarioIettusNovo.getSisAtributoSatb().getCodSatb().longValue())){
									
									
									ItemEstrutUsuarioIettus old = (ItemEstrutUsuarioIettus) ieUsuario.clone();
									old.setUsuManutencao(itemEstrutUsuarioIettusNovo.getUsuManutencao());
						            /******** Historico *********/
						            historico.gerarHistorico(old);
						        	/******** Historico *********/									
																		
									achou = true;
									ieUsuario = this.atualizarItemEstUsuario(itemEstrutUsuarioIettusFilho, ieUsuario);
									session.update(ieUsuario);
								}
							}
						}
						
						if(!achou && !listIettusAtualizados.contains(itemEstrutUsuarioIettusNovo)) {
							listIettusAtualizados.add(itemEstrutUsuarioIettusNovo);
							this.propagarPermissoesItensFilhos(itemEstrutUsuarioIettusNovo, session);
						}
					}
					else {
						session.save(itemEstrutUsuarioIettusFilho);
					}
				}
				else {
					break;
				}
			}			
		}	
	}
	
        
    
	/**
	 * Remover permissões para todos os filhos de um item.
	 * Utilizado na exclusão de permissão de acesso de itens da estrutura.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstrutUsuarioIettus itemEstrutUsuario
	 * @param Session session
	 * @throws HibernateException
	 */
	public void removerPermissoesItensFilhos(ItemEstrutUsuarioIettus itemEstrutUsuario, Session session, HttpServletRequest request) throws HibernateException, ECARException {
		ItemEstruturaIett item = itemEstrutUsuario.getItemEstruturaIett();
		
		List itensFilhos = getDescendentesItem(item);

		if(itensFilhos != null){
			Iterator it = itensFilhos.iterator();
			while(it.hasNext()){
				ItemEstruturaIett itemFilho = (ItemEstruturaIett) it.next();
				
				if(itemFilho.getItemEstrutUsuarioIettusesByCodIett() != null) { 

					Iterator itEstrutAnterior = itemFilho.getItemEstrutUsuarioIettusesByCodIett().iterator();
					while(itEstrutAnterior.hasNext()){
						ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) itEstrutAnterior.next();
						if(ieUsuario.getItemEstruturaIettOrigem() != null && ieUsuario.getItemEstruturaIettOrigem().equals(item)) {
							if(ieUsuario.getUsuarioUsu() != null && ieUsuario.getUsuarioUsu().equals(itemEstrutUsuario.getUsuarioUsu())) {
								
								ieUsuario.setUsuManutencao(itemEstrutUsuario.getUsuManutencao());
					            /******** Historico *********/
					            
					            HistoricoIettus historico = new HistoricoIettus(ieUsuario, 
					            												HistoricoIettus.excluirPermissoes,
					            												session,
					            												new ConfiguracaoDao(request),
					            												request);
					            historico.gerarHistorico();
					            
					        	/******** Historico *********/								
								
								
								session.delete(ieUsuario);
							}
							else if(ieUsuario.getCodTpPermIettus().equals(PERMISSAO_GRUPO) 
									&& (ieUsuario.getSisAtributoSatb() != null && itemEstrutUsuario.getSisAtributoSatb() != null &&
											ieUsuario.getSisAtributoSatb().getCodSatb().longValue() == itemEstrutUsuario.getSisAtributoSatb().getCodSatb().longValue())){
								
								ieUsuario.setUsuManutencao(itemEstrutUsuario.getUsuManutencao());
					            /******** Historico *********/
					            
					            HistoricoIettus historico = new HistoricoIettus(ieUsuario, 
					            												HistoricoIettus.excluirPermissoes,
					            												session,
					            												new ConfiguracaoDao(request),
					            												request);
					            historico.gerarHistorico();
					            
					        	/******** Historico *********/								
								
								session.delete(ieUsuario);
							}
						}
					}
				}
			}			
		}	
	}
	

    
	/**
	 * Verificar se é possível incluir um usuário ou grupo com indicador de 'manter 
	 * permissão no próximo nível' para um mesmo item da estrutura.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstruturaIett item
	 * @param ItemEstrutUsuarioIettus iettusParaIncluir
	 * @return boolean
	 */
	public boolean verificarInclusaoUsuarioGrupo(ItemEstruturaIett item, ItemEstrutUsuarioIettus iettusParaIncluir ) {
		boolean permiteInclusao = true;

		if("S".equals(iettusParaIncluir.getIndProxNivelIettus())) {
				
			Iterator it = item.getItemEstrutUsuarioIettusesByCodIett().iterator();
			
			while(it.hasNext() && permiteInclusao){
				ItemEstrutUsuarioIettus iettusGravado = (ItemEstrutUsuarioIettus) it.next();
				
				if("S".equals(iettusGravado.getIndProxNivelIettus()) && !iettusGravado.getCodIettus().equals(iettusParaIncluir.getCodIettus())) {
					if(iettusGravado.getCodTpPermIettus().equals(PERMISSAO_USUARIO) && iettusGravado.getUsuarioUsu() != null && iettusGravado.getUsuarioUsu().equals(iettusParaIncluir.getUsuarioUsu())) {
						permiteInclusao = false;
					}
					else if(iettusGravado.getCodTpPermIettus().equals(PERMISSAO_GRUPO) 
							&& (iettusGravado.getSisAtributoSatb() != null && iettusParaIncluir.getSisAtributoSatb() != null &&
									iettusGravado.getSisAtributoSatb().getCodSatb().longValue() == iettusParaIncluir.getSisAtributoSatb().getCodSatb().longValue())) {
						permiteInclusao = false;
					}
				}
			}
		}
		
		return permiteInclusao;
	}
	
	/**
     * Para a funcao de acompanhamento, se IndPlaneja == "S" retorna "S", cc "N".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param TipoFuncAcompTpfa funcao
     * @return String - "S" ou "N"
     */
    protected String getIndEdicao(TipoFuncAcompTpfa funcao) {
        return SIM.equals(funcao.getIndAlterarItemEstrutura()) ? SIM : NAO;
    }
    
    /**
     * Por enquanto, retorna um valor constante "S", pois todos podem ler.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String - "S"
     */
    protected String getIndLeitura() {
        return SIM;
    }

    /**
     * Por enquanto, retorna um valor constante "S", pois todos podem ler.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String - "S"
     */
    protected String getIndLeituraParecer(TipoFuncAcompTpfa funcao) {
        return SIM.equals(funcao.getIndVisualizarParecer()) ? SIM : NAO;
    }

    /**
     * Para a funcao de acompanhamento, se IndPlaneja == "S" retorna "S", cc "N".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param TipoFuncAcompTpfa funcao
     * @return String - "S" ou "N"
     */
    protected String getIndExcluir(TipoFuncAcompTpfa funcao) {
        return SIM.equals(funcao.getIndExcluirItemEstrutura()) ? SIM : NAO;
    }

    /**
     * Para a funcao de acompanhamento, se IndPlaneja ou IndMonitora == "S" retorna "S", cc "N".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param TipoFuncAcompTpfa funcao
     * @return String - "S" ou "N"
     */
    protected String getIndAtivMonit(TipoFuncAcompTpfa funcao) {
        return SIM.equals(funcao.getIndAtivarMonitoramento()) ? SIM : NAO;
    }
    
    /**
     * Para a funcao de acompanhamento, se IndMonitora == "S" retorna "S", cc "N".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param TipoFuncAcompTpfa funcao
     * @return String
     */
    protected String getIndDesatMonit(TipoFuncAcompTpfa funcao) {
        return SIM.equals(funcao.getIndDesativarMonitoramento()) ? SIM : NAO;
    }


    /**
     * Para a funcao de acompanhamento, se IndPlaneja ou IndMonitora == "S" retorna "S", cc "N".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param TipoFuncAcompTpfa funcao
     * @return String - "S" ou "N"
     */
    protected String getIndBloqPlan(TipoFuncAcompTpfa funcao) {
        return SIM.equals(funcao.getIndBloquearPlanejamento()) ? SIM : NAO;
    }
    
    /**
     * Para a funcao de acompanhamento, se IndMonitora == "S" retorna "S", cc "N".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param TipoFuncAcompTpfa funcao
     * @return String
     */
    protected String getIndDesblPlan(TipoFuncAcompTpfa funcao) {
        return SIM.equals(funcao.getIndDesbloquearPlanejamento()) ? SIM : NAO;
    }
    
    /**
     * Para a funcao de acompanhamento, se IndInformaAndamento == "S" retorna "S", cc "N".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param TipoFuncAcompTpfa funcao
     * @return String
     */
    protected String getIndInfAndamento(TipoFuncAcompTpfa funcao) {
        return SIM.equals(funcao.getIndInformaAndamentoTpfa()) ? SIM : NAO;
    }
    
    /**
     * Para a funcao de acompanhamento, se IndEmitePosicao == "S" retorna "S", cc "N".<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param TipoFuncAcompTpfa funcao
     * @return String
     */
    protected String getIndEmitePos(TipoFuncAcompTpfa funcao) {
        return SIM.equals(funcao.getIndEmitePosicaoTpfa()) ? SIM : NAO;
    }
    
    /**
     * Retorna String SIM.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    protected String getIndProxNivel() {
        return SIM;
    }
    
    /**Atualiza, de acordo com a opção "manter próximo nível", as permissões de acesso de todos os itens filhos da estrutura
     * passada como parâmetro. 
     * 
     * @param estrutTpFuncAcmpEtttfa
     * @param session
     */
    public void atualizarPermissaoManterProximoNivel(EstrutTpFuncAcmpEtttfa estrutTpFuncAcmpEtttfa, Session session, HttpServletRequest request) throws ECARException{
    	
    	
    	ItemEstrutUsuarioDao itemEstrutUsuarioDao = new ItemEstrutUsuarioDao(request);
    	ItemEstruturaIett itemEstruturaIettFiltro = new ItemEstruturaIett();
    	itemEstruturaIettFiltro.setIndAtivoIett("S");
    	itemEstruturaIettFiltro.setEstruturaEtt(estrutTpFuncAcmpEtttfa.getEstruturaEtt());
    	
    	List itensEstruturaIetts = itemEstrutUsuarioDao.pesquisar(itemEstruturaIettFiltro, null);
    	Iterator itItensEstruturaIetts = itensEstruturaIetts.iterator();
    	String manterProximoNivel = estrutTpFuncAcmpEtttfa.getIndManterProximoNivelEtttfa();
    	while (itItensEstruturaIetts.hasNext()){
    		ItemEstruturaIett itemEstruturaIett = (ItemEstruturaIett) itItensEstruturaIetts.next();
    		ItemEstrutUsuarioIettus itemEstrutUsuarioIettusFiltro = new ItemEstrutUsuarioIettus();
    		itemEstrutUsuarioIettusFiltro.setItemEstruturaIett(itemEstruturaIett);
    		itemEstrutUsuarioIettusFiltro.setTipoFuncAcompTpfa(estrutTpFuncAcmpEtttfa.getTipoFuncAcompTpfa());
    		itemEstrutUsuarioIettusFiltro.setCodTpPermIettus(ControlePermissao.PERMISSAO_FUNCAO_ACOMPANHAMENTO);
    		itemEstrutUsuarioIettusFiltro.setItemEstruturaIettOrigem(itemEstruturaIett);
    		
    		List itensEstrutUsuarioIettus = itemEstrutUsuarioDao.pesquisar(itemEstrutUsuarioIettusFiltro, null);
    		Iterator itIettus = itensEstrutUsuarioIettus.iterator();
    		
    		while (itIettus.hasNext()){
				ItemEstrutUsuarioIettus itemEstrutUsuarioIettus = (ItemEstrutUsuarioIettus) itIettus.next();
				if(manterProximoNivel != null && manterProximoNivel.equals("S")){
					this.propagarPermissoesIndProximoNivel(itemEstrutUsuarioIettus, session);
				}else {
					this.removerPermissoesIndProximoNivel(itemEstrutUsuarioIettus, session, request);
				}
			}
    	}    	
    }

    /**
	 * Propagar permissões para todos os filhos de um item caso o itemEstrutUsuario.getIndProxNivelIettus() seja 'S',
	 * Utilizado na inclusão de permissão de acesso de itens da estrutura.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstrutUsuarioIettus itemEstrutUsuarioIettus - itemEstrutUsuarioIettus que está sendo incluído
	 * @param Session session
	 */
	public void propagarPermissoesIndProximoNivel(ItemEstrutUsuarioIettus itemEstrutUsuarioIettus, Session session) {

		itemEstrutUsuarioIettus.setIndProxNivelIettus("S");
		session.update(itemEstrutUsuarioIettus);
		
		ItemEstruturaIett item = itemEstrutUsuarioIettus.getItemEstruturaIett();

		List itensFilhos = getDescendentesItem(item);

		if(itensFilhos != null){
			List iettusAux = new ArrayList();
			Iterator it = itensFilhos.iterator();
			while(it.hasNext()){
				ItemEstruturaIett itemFilho = (ItemEstruturaIett) it.next();
				
				ItemEstrutUsuarioIettus itemEstrutUsuarioIettusFilho = this.copiarItemEstUsuario(itemEstrutUsuarioIettus);
				
				itemEstrutUsuarioIettusFilho.setItemEstruturaIett(itemFilho);
				if(!iettusAux.contains(itemEstrutUsuarioIettusFilho)) {
					session.save(itemEstrutUsuarioIettusFilho);
					iettusAux.add(itemEstrutUsuarioIettusFilho);
				}
			}			
		}	
		
	}
	
	/**
	 * Propagar permissões para todos os filhos de um item caso o itemEstrutUsuario.getIndProxNivelIettus() seja 'S',
	 * Utilizado na inclusão de permissão de acesso de itens da estrutura.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param ItemEstrutUsuarioIettus itemEstrutUsuarioIettus - itemEstrutUsuarioIettus que está sendo incluído
	 * @param Session session
	 * @throws ECARException 
	 */
	public void removerPermissoesIndProximoNivel(ItemEstrutUsuarioIettus itemEstrutUsuarioIettus, Session session, HttpServletRequest request) throws ECARException {
		
		ItemEstrutUsuarioDao itemEstrutUsuarioDao = new ItemEstrutUsuarioDao(request);
		List itensFilhos = getDescendentesItem(itemEstrutUsuarioIettus.getItemEstruturaIett());
		itemEstrutUsuarioIettus.setIndProxNivelIettus("N");
		session.update(itemEstrutUsuarioIettus);

		if(itensFilhos != null){
			Iterator it = itensFilhos.iterator();
			while(it.hasNext()){
				ItemEstruturaIett itemFilho = (ItemEstruturaIett) it.next();
				
				ItemEstrutUsuarioIettus itemEstrutUsuarioIettusFiltro = new ItemEstrutUsuarioIettus();
				itemEstrutUsuarioIettusFiltro.setItemEstruturaIett(itemFilho);
				itemEstrutUsuarioIettusFiltro.setItemEstruturaIettOrigem(itemEstrutUsuarioIettus.getItemEstruturaIett());
				itemEstrutUsuarioIettusFiltro.setCodTpPermIettus(PERMISSAO_FUNCAO_ACOMPANHAMENTO);
				itemEstrutUsuarioIettusFiltro.setTipoFuncAcompTpfa(itemEstrutUsuarioIettus.getTipoFuncAcompTpfa());

				List itensEstrutUsuarioIettus = itemEstrutUsuarioDao.pesquisar(itemEstrutUsuarioIettusFiltro, null);
				
				if(itensEstrutUsuarioIettus != null) { 

					Iterator itEstrutAnterior = itensEstrutUsuarioIettus.iterator();
					while(itEstrutAnterior.hasNext()){
						ItemEstrutUsuarioIettus ieUsuario = (ItemEstrutUsuarioIettus) itEstrutAnterior.next();
													
						
						ieUsuario.setUsuManutencao(itemEstrutUsuarioIettus.getUsuManutencao());
			            /******** Historico *********/
			            
			            HistoricoIettus historico = new HistoricoIettus(ieUsuario, 
			            												HistoricoIettus.excluirPermissoes,
			            												session,
			            												new ConfiguracaoDao(request),
			            												request);
			            historico.gerarHistorico();
			            
			        	/******** Historico *********/								
								
								
						session.delete(ieUsuario);
						
					}
				}	
			}
		}
	}
	
}