/*
 * Criado em 10/02/2005
 *
 */
package ecar.dao;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.Mensagem;
import comum.util.Pagina;
import comum.util.Util;
import comum.util.grafico.FactoryCalculoProjecao;
import comum.util.grafico.Projection;

import ecar.bean.AtributoEstruturaListagemItens;
import ecar.bean.OrdenacaoTpfaEstrutura;
import ecar.email.AgendadorEmail;
import ecar.exception.ECARException;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompRefItemLimitesArli;
import ecar.pojo.AcompRefItemLimitesArliPK;
import ecar.pojo.AcompRefLimitesArl;
import ecar.pojo.AcompRefLimitesArlPK;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.AvaliaMetodo;
import ecar.pojo.ConfigMailCfgm;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.Cor;
import ecar.pojo.EmpresaEmp;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstrUplCategIettuc;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrutFisicoIettf;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaIettMin;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.PesquisaIett;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.StatusRelatorioSrl;
import ecar.pojo.TfuncacompConfigmailTfacfgm;
import ecar.pojo.TfuncacompConfigmailTfacfgmPK;
import ecar.pojo.TipoAcompFuncAcompTafc;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UsuarioAtributoUsua;
import ecar.pojo.UsuarioUsu;
import ecar.servlet.grafico.bean.PosicaoBean;
import ecar.util.Dominios;

/**
 * @author felipev, aleixo
 *
 */
public class AcompReferenciaItemDao extends Dao{
	
	ValidaPermissao validaPermissao = new ValidaPermissao();
        /**
         *
         */
        public static int STATUS_EM_EDICAO = 1;
    /**
     *
     */
    public static int STATUS_LIBERADO = 2;
    
    /**
     *
     */
    public static String RELATORIO_ORGAO = "ECAR-001A";
    /**
     *
     */
    public static String RELATORIO_ORGAO_ESPECIFICO = "ECAR-001B";
    /**
     *
     */
    public static String RELATORIO_SITUACAO = "ECAR-002A";
    /**
     *
     */
    public static String RELATORIO_SITUACAO_ESPECIFICO = "ECAR-002B";
    /**
     *
     */
    public static String RELATORIO_ESTRUTURA = "ECAR-001C";
      
	public static String KEY_ARRAY_OBJECT = "KEY_ARRAY_OBJECT";
	public static String KEY_LISTAARIS = "KEY_LISTAARIS";
	
	
    /**
     *
     * @param request
     */
    public AcompReferenciaItemDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
     * Grava uma lista de acompanhamento Refer�ncia e todos os seus itens.
     * Se o item possui fun��es de acompanhamento, cria um registro de AcompRelatorio para cada uma delas
     * Se o item possui indicadores de resultado, cria um registro de AcompRealFisico para cada um deles
     * 
         * @param listAcompReferenciaAref
         * @param request
         * @param listNiveis
         * @throws ECARException
     */
    public void salvarOuAlterarAcompReferenciaItens(List listAcompReferenciaAref, HttpServletRequest request, List listNiveis) throws ECARException{
        Transaction tx = null;
        
        try{
			//super.inicializarLogBean();

			tx = session.beginTransaction();
            
        	AcompRealFisicoDao arfDao = new AcompRealFisicoDao(request);
        	ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
        	TipoAcompanhamentoDao tipoAcompanhamentoDao = new TipoAcompanhamentoDao(request);
        	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
        	
		    ArrayList objetos = new ArrayList();
		    AcompReferenciaAref acompanhamento = (AcompReferenciaAref)listAcompReferenciaAref.get(0);
			
        	ItemEstUsutpfuacDao itemEstUsutpfuacDao = new ItemEstUsutpfuacDao(request);
        	AcompRelatorioDao acompRelatorioDao = new AcompRelatorioDao(request);
	    
	        UsuarioUsu usuario = (UsuarioUsu) new UsuarioDao(request).buscar(UsuarioUsu.class, Long.valueOf(Pagina.getParamStr(request, "codUsuario")));

			//// Obter configura��o de grupo de meta f�sica
			ConfiguracaoCfg cfg = configuracaoDao.getConfiguracao(); 
			ArrayList<SisAtributoSatb> listAtribMetasFisicasCfg = new ArrayList<SisAtributoSatb>(); 
			
			TipoAcompanhamentoTa ta = (TipoAcompanhamentoTa) tipoAcompanhamentoDao.buscar(TipoAcompanhamentoTa.class, acompanhamento.getTipoAcompanhamentoTa().getCodTa());
			if(cfg.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null) {
				ArrayList listSatb = new ArrayList(ta.getSisAtributoSatbs());
				
				if( listSatb != null ) {
					for (Iterator itSatbs = listSatb.iterator(); itSatbs.hasNext();) {
						SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) itSatbs.next();
						if( sisAtributoSatb.getSisGrupoAtributoSga().equals(cfg.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas()) ) {
							listAtribMetasFisicasCfg.add(sisAtributoSatb);
						}
					}
				}
			}
			////
			
            /* 
             * itensSelecionados em Tela ser�o os checkboxes marcados em selecaoItem.jsp             
             */
            List<ItemEstruturaIett> itensSelecionadosEmTela = new ArrayList<ItemEstruturaIett>();
            
            String[] itemSelecao = request.getParameterValues("iett");
            if(itemSelecao != null){
                for(int i = 0; i < itemSelecao.length; i++){
                    ItemEstruturaIett item = (ItemEstruturaIett) buscar(ItemEstruturaIett.class, Long.valueOf(itemSelecao[i]));
                    
                    	// se for separado por orgao e o item selecionado tiver o mesmo orgao da referencia OU
                    if((acompanhamento.getTipoAcompanhamentoTa().getIndSepararOrgaoTa() != null && 
                    		acompanhamento.getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals(Dominios.SIM) &&
                    		(item.getOrgaoOrgByCodOrgaoResponsavel1Iett() == null && acompanhamento.getOrgaoOrg() == null) || 
                    		(item.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null && acompanhamento.getOrgaoOrg() != null &&
                    		item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getCodOrg().equals(acompanhamento.getOrgaoOrg().getCodOrg()))) ||
                    	//se nao for separado por orgao OU
                    	(acompanhamento.getTipoAcompanhamentoTa().getIndSepararOrgaoTa() == null || acompanhamento.getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals(Dominios.NAO)) ||
                    	//se for altera��o, mesmo que n�o seja do mesmo �rg�o e estiver marcado vai continuar sendo acompanhado. 
                    	(acompanhamento.getCodAref() != null)) {
                    	itensSelecionadosEmTela.add(item);
                    
                     
                    } 
                }
            } 
            
            List listaCompletaItens = adicionaDescendentesQuantidadePrevista(itensSelecionadosEmTela, acompanhamento);
			
            for (Iterator itAref = listAcompReferenciaAref.iterator(); itAref.hasNext();) {
				acompanhamento = (AcompReferenciaAref) itAref.next();

	            /*
	             * Cria uma vari�vel acompAuxiliar para receber o objeto acompanhamento com os dados que estavam ne session do
	             * servidor e foram setados a partir de dados do Formul�rio. 
	             * Faz buscar do acompanhamento que esta sendo alterado e seta as valores que estavam em acompAuxiliar para
	             * acompanhamento
	             */
	            if(acompanhamento.getCodAref() != null && acompanhamento.getCodAref().longValue() > 0){
	            	AcompReferenciaAref acompAuxiliar = acompanhamento;
	            	acompanhamento = (AcompReferenciaAref) this.buscar(AcompReferenciaAref.class, acompAuxiliar.getCodAref());
	                acompanhamento.setDataInicioAref(acompAuxiliar.getDataInicioAref());
	                acompanhamento.setDataLimiteAcompFisicoAref(acompAuxiliar.getDataLimiteAcompFisicoAref());
	                acompanhamento.setNomeAref(acompAuxiliar.getNomeAref());
	                acompanhamento.setAcompRefLimitesArls(acompAuxiliar.getAcompRefLimitesArls());
	                session.update(acompanhamento);
	            }
	            else {
	                session.save(acompanhamento);
	            }
	
				objetos.add(acompanhamento);
				
	            /* lista com os itens monitorados pelo acompanhamento (no caso de inclus�o de acompanhamento essa ser� um lista 
	             * vazia
	             */
	            List itensMonitoradosBancoDeDados = getListaItensAcompanhamento(acompanhamento);
	            
	            Collection objetosParaSalvar  = Util.diferenca(listaCompletaItens, itensMonitoradosBancoDeDados);
	            Collection objetosParaAlterar = Util.intersecao(listaCompletaItens, itensMonitoradosBancoDeDados);
	            Collection objetosParaExcluir = Util.diferenca(itensMonitoradosBancoDeDados, listaCompletaItens);

	            /*
	             * Itera e salva ( ou altera ) os limites do acompanhamento
	             */                    

	            for (Iterator itLimites = acompanhamento.getAcompRefLimitesArls().iterator(); itLimites
						.hasNext();) {
					AcompRefLimitesArl acompLimites = (AcompRefLimitesArl) itLimites.next();

	                if(acompLimites.getComp_id() != null){
	                    session.update(acompLimites);
	                } else{
	                    AcompRefLimitesArlPK pk = new AcompRefLimitesArlPK();
	                    pk.setCodAref(acompLimites.getAcompReferenciaAref().getCodAref());
	                    pk.setCodTpfa(acompLimites.getTipoFuncAcompTpfa().getCodTpfa());
	                    acompLimites.setComp_id(pk);
	                    session.save(acompLimites);                    
	                }                                  
					objetos.add(acompLimites);
	            }
	            
	            /*cria os novos acompanhamento de item */ 
	            
	            for (Iterator itObjetosIncluidos = objetosParaSalvar.iterator(); itObjetosIncluidos
						.hasNext();) {
					ItemEstruturaIett iett = (ItemEstruturaIett) itObjetosIncluidos.next();
	                AcompReferenciaItemAri  acompReferenciaItem = criaNovoAcompReferenciaItemAri(acompanhamento, iett, request, itensSelecionadosEmTela, usuario);
	                
	                session.save(acompReferenciaItem);
					objetos.add(acompReferenciaItem);
	                
					
					for (Iterator itAcompReferenciaItemLimites = acompReferenciaItem.getAcompRefItemLimitesArlis().iterator(); itAcompReferenciaItemLimites
							.hasNext();) {
						AcompRefItemLimitesArli limite = (AcompRefItemLimitesArli) itAcompReferenciaItemLimites.next();
	                    setPKAcompReferenciaItemLimite(limite);                    
	                    session.save(limite);                    
						objetos.add(limite);
	                }
	                
	                /* salva os realizados fisicos conforme regra*/

        	        if(iett.getItemEstrtIndResulIettrs() != null && !iett.getItemEstrtIndResulIettrs().isEmpty()){
        	        	
        	        	boolean gravaARF = false;
        	        	
	        	        if(listNiveis != null && listNiveis.size() > 0) {
	        	        	if(iett.getItemEstruturaNivelIettns() != null && iett.getItemEstruturaNivelIettns().size() > 0) {
		        	            Iterator itNiveis = iett.getItemEstruturaNivelIettns().iterator();
		        	            while(itNiveis.hasNext() && !gravaARF) {
		        	                SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
		        	                
		        	                if(listNiveis.contains(nivel)) {
		        	                	gravaARF = true;
		        	                }
		        	                
		        	            }
	        	        	}
	        	        } else {
	        	        	gravaARF = true;
	        	        }

	        	        if(gravaARF) {
                			
                			for (Iterator itIettir = iett.getItemEstrtIndResulIettrs().iterator(); itIettir
									.hasNext();) {
								ItemEstrtIndResulIettr iettir = (ItemEstrtIndResulIettr) itIettir.next();

								//Mantis-POA: caso 0010021 
								//Testar se indicador (iettir) est� exclu�do antes de criar ARF para ele.
								//Se indicador est� exclu�do, n�o cria ARF
								if("N".equals(iettir.getIndAtivoIettr())){
									continue;
								}
								
	                			//verificacao de grupo de meta f�sica
		                        if(!listAtribMetasFisicasCfg.isEmpty()) {
		                        	if(iettir.getSisAtributoSatb() == null || !listAtribMetasFisicasCfg.contains(iettir.getSisAtributoSatb())) {
		                        		// n�o gravar ARF para o indicador, se o indicador n�o tiver a meta f�sica configurada no tipo de acomp.
		                        		continue;
		                        	}
		                        }
	                			////
		                        
	                			
		            			// verificar a exist�ncia do ARF
		            			AcompRealFisicoArf arf = arfDao.buscarPorIettir(
		            					Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()),
		            					Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()),
		            					iettir.getCodIettir());
		
			        	        if(arf == null) {
			                        long mes = Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()).longValue();
			                        long ano = Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()).longValue();
		                			AcompRealFisicoArf arfAnterior = arfDao.getArfAnterior(iettir, mes, ano);
		                			
		                			if(arfAnterior != null){
		                				if(arfAnterior.getSituacaoSit() != null && "S".equals(arfAnterior.getSituacaoSit().getIndConcluidoSit())){
		                					//Se o ARF anterior representa conclus�o, n�o gero ARF deste indicador para o acompanhamento.
		                					continue;
		                				}
		                			}
		                			
			        	        	arf = new AcompRealFisicoArf();
			
			        	        	arf.setItemEstrtIndResulIettr(iettir);
			        	        	arf.setDataInclusaoArf(Data.getDataAtual());
			        	        	arf.setItemEstruturaIett(iett);
			        	        	arf.setMesArf(Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()));
			        	        	arf.setAnoArf(Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()));
			
			        	        	session.save(arf);                        
									objetos.add(arf);
			        	        }
		                    }
                		}
        	        }

	                /* salva os relat�rios (se existirem) */
	                if(acompReferenciaItem.getAcompRelatorioArels() != null){
	                	for (Iterator itAcompRel = acompReferenciaItem.getAcompRelatorioArels().iterator(); itAcompRel
								.hasNext();) {
							AcompRelatorioArel acompRel = (AcompRelatorioArel) itAcompRel.next();                        
	                        session.save(acompRel);
							objetos.add(acompRel);
	                    }
	                }
	            }

	            for (Iterator itObjetosDeletados = objetosParaExcluir.iterator(); itObjetosDeletados
						.hasNext();) {
					ItemEstruturaIett itemRemoverMonitoramento = (ItemEstruturaIett) itObjetosDeletados.next();
	                AcompReferenciaItemAri acompReferenciaItemAExcluir = this.getAcompReferenciaItemByItemEstruturaIett(acompanhamento, itemRemoverMonitoramento);
	                List filhos = new ArrayList();
	                /* excluir os realizados f�sico e Relatorios do acompanhamento */
	                filhos.addAll(acompReferenciaItemAExcluir.getAcompRelatorioArels());
	                
	                if (acompReferenciaItemAExcluir.getItemEstrUplCategIettuc() != null && acompReferenciaItemAExcluir.getItemEstrUplCategIettuc().size() > 0){
		                List<ItemEstrUplCategIettuc> categoriasUpload = new ArrayList<ItemEstrUplCategIettuc>(acompReferenciaItemAExcluir.getItemEstrUplCategIettuc());                	                
		                List historicosCategoriaExcluir = new HistoricoItemEstrutUploadDao(request).getHistoricoPorCategoriaUpload(categoriasUpload);
		                //Adiciona o hist�rico de upload aos objetos a serem exclu�dos
		                filhos.addAll(historicosCategoriaExcluir);
	                }
	                
	                // verificar o ARF correspondente ao ARI que podem ser exclu�do
	    			StringBuilder query = new StringBuilder("select ARI from AcompReferenciaItemAri as ARI")
	    								.append(" where ARI.itemEstruturaIett.codIett = :codIett")
	    								.append(" and ARI.acompReferenciaAref.diaAref = :dia")
	    								.append(" and ARI.acompReferenciaAref.mesAref = :mes")
	    								.append(" and ARI.acompReferenciaAref.anoAref = :ano")
	    								.append(" and ARI.codAri<> :codAri")
	    								.append(" and ARI.itemEstruturaIett.indAtivoIett = 'S'");
	    			
	    			Query q = this.getSession().createQuery(query.toString());
	    			
	    			q.setLong("codIett", acompReferenciaItemAExcluir.getItemEstruturaIett().getCodIett().longValue());
	    			q.setString("dia", acompReferenciaItemAExcluir.getAcompReferenciaAref().getDiaAref());
	    			q.setString("mes", acompReferenciaItemAExcluir.getAcompReferenciaAref().getMesAref());
	    			q.setString("ano", acompReferenciaItemAExcluir.getAcompReferenciaAref().getAnoAref());
	    			q.setLong("codAri", acompReferenciaItemAExcluir.getCodAri().longValue());
					
					List listaARI = q.list();
					
					if(listaARI == null || listaARI.isEmpty()) {
						// o ARF correspondente pode ser removido pois n�o est� em uso
						List listArf = arfDao.buscarPorIett(
								acompReferenciaItemAExcluir.getItemEstruturaIett().getCodIett(),
								Long.valueOf(acompReferenciaItemAExcluir.getAcompReferenciaAref().getMesAref()),
								Long.valueOf(acompReferenciaItemAExcluir.getAcompReferenciaAref().getAnoAref()));
						
						if(listArf != null && !listArf.isEmpty()) {
							
							for (Iterator itListArf = listArf.iterator(); itListArf
									.hasNext();) {
								filhos.add(itListArf.next());
								
							}

						}
					}
	
					for (Iterator itExcluirFilhos = filhos.iterator(); itExcluirFilhos.hasNext();) {
						session.delete(itExcluirFilhos.next());
					}

					for (Iterator itExcluirFilhos = filhos.iterator(); itExcluirFilhos.hasNext();) {
						objetos.add(itExcluirFilhos.next());
						
					}
					
	                session.delete(acompReferenciaItemAExcluir);                       
					objetos.add(acompReferenciaItemAExcluir);
	                
	            }
	            
            	Set setNovosArlis = new HashSet();
            	Set setNovosArels = new HashSet();
	            for (Iterator itObjetosAlterados = objetosParaAlterar.iterator(); itObjetosAlterados.hasNext();) {
	            	ItemEstruturaIett itemAlterarMonitoramento = (ItemEstruturaIett) itObjetosAlterados.next();
					
	                AcompReferenciaItemAri acompReferenciaItemAlterar = this.getAcompReferenciaItemByItemEstruturaIett(acompanhamento, itemAlterarMonitoramento);
	                acompReferenciaItemAlterar.setDataUltManutAri(Data.getDataAtual());
	                acompReferenciaItemAlterar.setDataInicioAri(acompanhamento.getDataInicioAref());
	                acompReferenciaItemAlterar.setDataLimiteAcompFisicoAri(acompanhamento.getDataLimiteAcompFisicoAref());
	                acompReferenciaItemAlterar.setCodUsuUltManutAri(Long.valueOf(Pagina.getParamStr(request, "codUsuario")));

                    session.update(acompReferenciaItemAlterar);                       
					objetos.add(acompReferenciaItemAlterar);

					for (Iterator it = acompReferenciaItemAlterar.getAcompRefItemLimitesArlis().iterator(); it.hasNext();) {
	                    AcompRefItemLimitesArli acompRefItemLimites = (AcompRefItemLimitesArli) it.next();
	                    
	                    AcompRefLimitesArl limiteAcomp = acompReferenciaDao.getAcompRefLimitesByFuncaoAcomp(acompanhamento, acompRefItemLimites.getTipoFuncAcompTpfa());
	                    acompRefItemLimites.setDataLimiteArli(limiteAcomp.getDataLimiteArl());
	                    session.update(acompRefItemLimites);
						objetos.add(acompRefItemLimites);
	                }

					// Mantis: 10715 (Criar AREL (parecer) para novas fun��es de acompanhamento)
	                
	                //Testar confirma��o do usu�rio se confirma cria��o de parecer para novas fun��es de acompanhamento
	                if("S".equals(Pagina.getParamStr(request, "existeAriFaltandoParecerConfirma"))) {
		                // S� gerar AREL(parecer) para itens que foram selecionados na tela
	                    if(itensSelecionadosEmTela.contains(itemAlterarMonitoramento)) {
			                Set arelsGravados = acompReferenciaItemAlterar.getAcompRelatorioArels();
		
			                // obtem as fun��es de acompanhamento do item que emitem posi��o
			                Iterator it = itemEstUsutpfuacDao.getFuacEmitePosicaoOrderByFuncAcomp(itemAlterarMonitoramento).iterator();
			                while(it.hasNext()){
			                    TipoFuncAcompTpfa funcAcomp = ((ItemEstUsutpfuacIettutfa)it.next()).getTipoFuncAcompTpfa();
			                    
		        	            // S� gerar AREL(parecer) para as fun��es cadastradas no tipo de acompanhamento
		                        Iterator itTafc = ta.getTipoAcompFuncAcompTafcs().iterator();
		                        while(itTafc.hasNext()){
		                        	TipoAcompFuncAcompTafc tafc = (TipoAcompFuncAcompTafc) itTafc.next();
		                        	//verifica a fun��o e se ela n�o est� como "ignorar"
		                        	if(tafc.getTipoFuncAcompTpfa().equals(funcAcomp) && (tafc.getIndObrigatorio().equals("S") || tafc.getIndOpcional().equals("S"))){
		                        		boolean existeArelParaFuncao = false;
		                        		Iterator itArelsGravados = arelsGravados.iterator();
		            	                while(itArelsGravados.hasNext() && !existeArelParaFuncao){
		            	                	AcompRelatorioArel arel = (AcompRelatorioArel)itArelsGravados.next();
		            	                	if(arel.getTipoFuncAcompTpfa().equals(funcAcomp)) {
		            	                		existeArelParaFuncao = true;
		            	                	}
		            	                }
		            	                
		            	                if(!existeArelParaFuncao) {
		            	                    AcompRefLimitesArl arlAcomp = acompReferenciaDao.getAcompRefLimitesByFuncaoAcomp(acompanhamento, funcAcomp);
		            	                    
		            	                    if(arlAcomp != null) {
			            	                	AcompRefItemLimitesArli arliAri = acompReferenciaDao.getAcompRefItemLimitesArliByAriAndTpfa(acompReferenciaItemAlterar.getCodAri(), funcAcomp.getCodTpfa());
			            	                	if(arliAri == null) {
			            	                        AcompRefItemLimitesArli acompRefItemLimite = new AcompRefItemLimitesArli();  
			            	                    	acompRefItemLimite.setDataLimiteArli(arlAcomp.getDataLimiteArl());
			            	                        acompRefItemLimite.setAcompReferenciaItemAri(acompReferenciaItemAlterar);
			            	                        acompRefItemLimite.setTipoFuncAcompTpfa(funcAcomp);
			            	                        acompRefItemLimite.atribuirPKPai();
				            	                    if(!setNovosArlis.contains(acompRefItemLimite)) {
				            	                        setNovosArlis.add(acompRefItemLimite);
				            	                    }
			            	                	}
		            	                    }
		            	                    
		            	                    AcompRelatorioArel arelAux = acompRelatorioDao.criaNovoAcompRelatorio(acompReferenciaItemAlterar, usuario, funcAcomp);
		            	                    if(!setNovosArels.contains(arelAux)) {
		            	                    	setNovosArels.add(arelAux);
		            	                    }
		            	                }
		                        	}
		                        }
			                }    
	                    }
	                }
	            }
	            Iterator it = setNovosArlis.iterator();
	            while(it.hasNext()){
	            	session.save(it.next());
	            }
	            it = setNovosArels.iterator();
	            while(it.hasNext()){
	            	session.save(it.next());
	            }
                // Fim Mantis: 10715
			}

			tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC_ALT_EXC");
				
				for (Iterator itObj = objetos.iterator(); itObj
						.hasNext();) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
        } catch (HibernateException e) {
        	e.printStackTrace();
			this.logger.error(e);
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
     * Recebe uma lista de itens e completa essa lista adicionando descendentes destes itens
     * que possuam quantidades previstas
     * @param itensSelecionadosEmTela
     */
    private List adicionaDescendentesQuantidadePrevista(List itensSelecionadosEmTela, AcompReferenciaAref aref) throws ECARException{
    	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
        TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa)buscar(TipoAcompanhamentoTa.class, aref.getTipoAcompanhamentoTa().getCodTa());
        
	    String indMonitoramento = "";
	    
	    if("S".equals(tipoAcompanhamento.getIndMonitoramentoTa()) && "N".equals(tipoAcompanhamento.getIndNaoMonitoramentoTa())) {
	    	indMonitoramento = "S";
	    }
	    else if("N".equals(tipoAcompanhamento.getIndMonitoramentoTa()) && "S".equals(tipoAcompanhamento.getIndNaoMonitoramentoTa())) {
		    	indMonitoramento = "N";
	    }
    	
	    List listNivelPlanejamentoTipoAcomp = new ArrayList(tipoAcompanhamento.getSisAtributoSatbs());

	    List retorno = new ArrayList();
        retorno.addAll(itensSelecionadosEmTela);
        
        for (Iterator it = itensSelecionadosEmTela.iterator(); it.hasNext();) {
			ItemEstruturaIett itemTela = (ItemEstruturaIett) it.next();
            List descendentes = itemEstruturaDao.getDescendentes(itemTela, true);
                        
            for (Iterator itDescendentes = descendentes.iterator(); itDescendentes.hasNext();) {
				ItemEstruturaIett itemDescendente = (ItemEstruturaIett) itDescendentes.next();
                if(itemDescendente.getItemEstrtIndResulIettrs() != null 
                		&& itemDescendente.getItemEstrtIndResulIettrs().size() > 0 
                		&& !retorno.contains(itemDescendente)
                		&& (itemDescendente.getSituacaoSit() == null 
                			|| (!"S".equals(itemDescendente.getSituacaoSit().getIndConcluidoSit())
                				&& !"S".equals(itemDescendente.getSituacaoSit().getIndSemAcompanhamentoSit())))) {
                	
        	        // verifica��o monitoramento
        	        if(!"".equals(indMonitoramento)) {
            	        if(!indMonitoramento.equals(itemDescendente.getIndMonitoramentoIett())) {
            	        	// ignora o item
            	        	continue;
            	        }
        	        }

        	        // verifica��o dos n�veis de planejamento
                	boolean nivelOk = false;

                    if(listNivelPlanejamentoTipoAcomp != null && listNivelPlanejamentoTipoAcomp.size() > 0) {
                    	if(itemDescendente.getItemEstruturaNivelIettns() != null && itemDescendente.getItemEstruturaNivelIettns().size() > 0) {
            	            Iterator itNiveis = itemDescendente.getItemEstruturaNivelIettns().iterator();
            	            while(itNiveis.hasNext() && !nivelOk) {
            	                SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
            	                
            	                if(listNivelPlanejamentoTipoAcomp.contains(nivel)) {
            	                	nivelOk = true;
            	                }
            	                
            	            }
                    	}
                    } else {
                    	nivelOk = true;
                    }

                    if(!nivelOk) {
        	        	// ignora o item
                    	continue;
                    }

                	retorno.add(itemDescendente);
                }
            }

        }        
        return retorno;
    }

    /**
     * @param limite
     */
    private void setPKAcompReferenciaItemLimite(AcompRefItemLimitesArli limite) {
        AcompRefItemLimitesArliPK pk = new AcompRefItemLimitesArliPK();
        pk.setCodAri(limite.getAcompReferenciaItemAri().getCodAri());
        pk.setCodTpfa(limite.getTipoFuncAcompTpfa().getCodTpfa());
        limite.setComp_id(pk);
    }

    /**
     * Cria um novo objeto AcompReferenciaItemAri baseado em um Item e um Acompanhamento
     * @param acompanhamento
     * @param iett
     * @param listaItensSelecionadosTela
     */
    private AcompReferenciaItemAri criaNovoAcompReferenciaItemAri(AcompReferenciaAref acompanhamento, ItemEstruturaIett iett, 
    		HttpServletRequest request, List listaItensSelecionadosTela, UsuarioUsu usuario) throws ECARException{
        TipoAcompanhamentoDao tipoAcompanhamentoDao = new TipoAcompanhamentoDao(null);
        AcompRelatorioDao acompRelatorioDao = new AcompRelatorioDao(request);

    	TipoAcompanhamentoTa taTemp = (TipoAcompanhamentoTa) tipoAcompanhamentoDao.buscar(TipoAcompanhamentoTa.class, acompanhamento.getTipoAcompanhamentoTa().getCodTa());
        AcompReferenciaItemAri acompReferenciaItem = new AcompReferenciaItemAri();
       
        acompReferenciaItem.setItemEstruturaIett(iett);
        acompReferenciaItem.setAcompReferenciaAref(acompanhamento);

        acompReferenciaItem.setIndItemMonitoradosAri(acompanhamento.getTipoAcompanhamentoTa().getIndMonitoramentoTa());
        
        acompReferenciaItem.setDataLimiteAcompFisicoAri(acompanhamento.getDataLimiteAcompFisicoAref());                
        acompReferenciaItem.setCodUsuincAri(usuario.getCodUsu());
        acompReferenciaItem.setDataInclusaoAri(Data.getDataAtual());
        acompReferenciaItem.setDataInicioAri(acompanhamento.getDataInicioAref());
        
        /* salvar item como EM EDICAO */
        acompReferenciaItem.setStatusRelatorioSrl((StatusRelatorioSrl) this.buscar(StatusRelatorioSrl.class, Long.valueOf(STATUS_EM_EDICAO)));

        /* grava as fun��es de acompanhamento */
        
        acompReferenciaItem.setAcompRefItemLimitesArlis(new HashSet());
        acompReferenciaItem.setAcompRelatorioArels(new HashSet());
        
        Iterator it = new ItemEstUsutpfuacDao(request).getFuacEmitePosicaoOrderByFuncAcomp(iett).iterator();
        while(it.hasNext()){
            TipoFuncAcompTpfa funcAcomp = ((ItemEstUsutpfuacIettutfa)it.next()).getTipoFuncAcompTpfa();
            
            AcompRefLimitesArl acompRefLimite = getAcompRefLimitesByAcompReferenciaTipoFuncAcomp(funcAcomp, acompanhamento);
            if(acompRefLimite != null){
                 AcompRefItemLimitesArli acompRefItemLimite = new AcompRefItemLimitesArli();  
                 acompRefItemLimite.setAcompReferenciaItemAri(acompReferenciaItem);
                 acompRefItemLimite.setTipoFuncAcompTpfa(funcAcomp);
                 acompRefItemLimite.setDataLimiteArli(acompRefLimite.getDataLimiteArl());
                 // adiciona na lista de ARLI
                 acompReferenciaItem.getAcompRefItemLimitesArlis().add(acompRefItemLimite);
            }
            
            // S� gerar AREL para itens que foram selecionados na tela
            if(listaItensSelecionadosTela.contains(iett)) {
	            /* S� gerar AcompRelatorioArel para as fun��es cadastradas no tipo de acompanhamento */
                Iterator itTafc = taTemp.getTipoAcompFuncAcompTafcs().iterator();
                while(itTafc.hasNext()){
                	TipoAcompFuncAcompTafc tafc = (TipoAcompFuncAcompTafc) itTafc.next();
                	//verifica a fun��o e se ela n�o est� como "ignorar"
                	if(tafc.getTipoFuncAcompTpfa().equals(funcAcomp) && ((tafc.getIndObrigatorio() != null && tafc.getIndObrigatorio().equals("S")) || (tafc.getIndOpcional()!= null && tafc.getIndOpcional().equals("S")))){
	    	         
                		/* para cada fun��o de acompanhamento gerar um AcompRelatorioArel */            
	    	            AcompRelatorioArel relatorio = acompRelatorioDao.criaNovoAcompRelatorio(acompReferenciaItem, usuario, funcAcomp);
	    	            if(relatorio != null) {
	    	                acompReferenciaItem.getAcompRelatorioArels().add(relatorio);
	    	            }
                	}
                }
            	
            }
        }    
        
        return acompReferenciaItem;
        
    }

    /**
     * Devolve uma lista de ItemEstruturaIett correspondentes aos itens de um acompanhamento
     * @param acompanhamento
     * @return
     * @throws ECARException
     */
    public List getListaItensAcompanhamento(AcompReferenciaAref acompanhamento) throws ECARException{
    	
    	try{
    		StringBuilder query = new StringBuilder("select ari.itemEstruturaIett from AcompReferenciaItemAri ari")
    								.append(" where ari.acompReferenciaAref.codAref = ?")
    								.append(" and ari.itemEstruturaIett.indAtivoIett = 'S'");
    		
    		Query q = session.createQuery(query.toString());

    		q.setLong(0, acompanhamento.getCodAref().longValue());
    		
    		return q.list();
    	} catch(HibernateException e){
    		this.logger.error(e);
    		throw new ECARException(e);
    	}    	 
    }
    
    /**
     * Altera um registro de Acompanhamento de Refer�ncia e suas datas Limite
     * Altera as datas limite do acompanhamento de todos os descendentes do item
     * @param request
     * @throws ECARException
     */
    public void alterar(HttpServletRequest request) throws ECARException {
    	inicializarLogBean();
		Transaction tx = null;
		
		try {
		    
			tx = session.beginTransaction();

	    	List objetosParaAlterar = new ArrayList();
	        
	        AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) this.buscar(AcompReferenciaItemAri.class, Long.valueOf(Pagina.getParamStr(request, "codAri")));
	        
	        String novoCodSrl = Pagina.getParamStr(request, "codSrl");

	        if("liberarRecuperar".equals(Pagina.getParamStr(request, "botaoClicado"))
	        	&& novoCodSrl.equals(String.valueOf(acompReferenciaItem.getStatusRelatorioSrl().getCodSrl().intValue()))) {
	        	
		        if(String.valueOf(STATUS_LIBERADO).equals(novoCodSrl)) {
					throw new ECARException("acompanhamento.dadosBasicos.liberar.jaEfetuado"); 
		        }
		        else {
					throw new ECARException("acompanhamento.dadosBasicos.recuperar.jaEfetuado"); 
		        }
	        }
	        
	        /* se est� em edi��o, altera campos */
	        if(acompReferenciaItem.getStatusRelatorioSrl() == null || (acompReferenciaItem.getStatusRelatorioSrl() != null && acompReferenciaItem.getStatusRelatorioSrl().getCodSrl().intValue() == STATUS_EM_EDICAO)){

	            acompReferenciaItem.setDataLimiteAcompFisicoAri(Pagina.getParamDataBanco(request, "dataLimiteAcompFisico"));                
	            acompReferenciaItem.setCodUsuUltManutAri(Long.valueOf(Pagina.getParamStr(request, "codUsuario")));
	            acompReferenciaItem.setDataUltManutAri(Data.getDataAtual());
	            acompReferenciaItem.setDataInicioAri(Pagina.getParamDataBanco(request, "dataInicioAri"));        
	            acompReferenciaItem.setStatusRelatorioSrl((StatusRelatorioSrl) this.buscar(StatusRelatorioSrl.class, Long.valueOf(Pagina.getParamStr(request, "codSrl"))));
	            
	            objetosParaAlterar.add(acompReferenciaItem);
	            
	            Iterator it = acompReferenciaItem.getAcompRefItemLimitesArlis().iterator();
	            while(it.hasNext()){
	                AcompRefItemLimitesArli limite = (AcompRefItemLimitesArli) it.next();
	                limite.setDataLimiteArli(Pagina.getParamDataBanco(request, "dataLimite" + limite.getTipoFuncAcompTpfa().getCodTpfa()));
	                objetosParaAlterar.add(limite);
	            }
	            
	        } else {
	           /* sen�o s� atualiza status */
	            acompReferenciaItem.setCodUsuUltManutAri(Long.valueOf(Pagina.getParamStr(request, "codUsuario")));
	            acompReferenciaItem.setDataUltManutAri(Data.getDataAtual());
	            acompReferenciaItem.setStatusRelatorioSrl((StatusRelatorioSrl) this.buscar(StatusRelatorioSrl.class, Long.valueOf(Pagina.getParamStr(request, "codSrl"))));
	            objetosParaAlterar.add(acompReferenciaItem);            
	        }
	        
	        /* busca os descendentes do item a altera os acompanhamentos feitos para estes filhos */
	        List descendentes = new ItemEstruturaDao(request).getDescendentes(acompReferenciaItem.getItemEstruturaIett(), true);
	        
	        for (Iterator it = descendentes.iterator(); it.hasNext();) {
				ItemEstruturaIett item = (ItemEstruturaIett) it.next();	            
	            AcompReferenciaItemAri acompanhamentoFilho = this.getAcompReferenciaItemByItemEstruturaIett(acompReferenciaItem.getAcompReferenciaAref(), item);
	                                                      
	            if(acompanhamentoFilho != null){
	                acompanhamentoFilho.setDataLimiteAcompFisicoAri(Pagina.getParamDataBanco(request, "dataLimiteAcompFisico"));                
	                acompanhamentoFilho.setCodUsuUltManutAri(Long.valueOf(Pagina.getParamStr(request, "codUsuario")));
	                acompanhamentoFilho.setDataUltManutAri(Data.getDataAtual());
	                acompanhamentoFilho.setDataInicioAri(Pagina.getParamDataBanco(request, "dataInicioAri"));                
	                Iterator itDatasFilho = acompanhamentoFilho.getAcompRefItemLimitesArlis().iterator();                
	                while(itDatasFilho.hasNext()){
	                    AcompRefItemLimitesArli limite = (AcompRefItemLimitesArli) itDatasFilho.next();
	                    if(!"".equals(Pagina.getParamStr(request, "dataLimite" + limite.getTipoFuncAcompTpfa().getCodTpfa()))){
	                        limite.setDataLimiteArli(Pagina.getParamDataBanco(request, "dataLimite" + limite.getTipoFuncAcompTpfa().getCodTpfa()));
	                        objetosParaAlterar.add(limite);
	                    }
	                }
	                objetosParaAlterar.add(acompanhamentoFilho);
	            }
	        }

	        Iterator itAlt = objetosParaAlterar.iterator();
			while(itAlt.hasNext()) {
			    session.update(itAlt.next());
			}
			
			tx.commit();
			
			if(logBean != null) {
				Iterator it = objetosParaAlterar.iterator();
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("ALT");
				while(it.hasNext()) {
					logBean.setObj(it.next());
					loggerAuditoria.info(logBean.toString());
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
     * Libera uma lista de AcompReferenciaItemAris, enviando e-mail aos respons�veis (se configurado no sistema).
     * Retorna true se conseguiu efetuar a libera��o em todos os itens; false caso contr�rio.
     * 
     * @author aleixo
     * @since 25/06/2007
     * @param acompRefItens
     * @param usuarioLogado
     * @param enviarEmail
     * @param configMailCfgm
     * @return boolean
     * @throws ECARException
     */    
    public boolean liberarAcompanhamentos(Set acompRefItens, UsuarioUsu usuarioLogado, boolean enviarEmail, ConfigMailCfgm configMailCfgm) throws ECARException{
    	boolean retorno = false;
    	if(acompRefItens != null && !acompRefItens.isEmpty()){
    		StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) this.buscar(StatusRelatorioSrl.class, Long.valueOf(STATUS_LIBERADO));
    		TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
    		UsuarioDao usuDao = new UsuarioDao();
    		EmpresaDao empDAO = new EmpresaDao(request);
    		List listArisAlterados = new ArrayList();
    		
			Transaction tx = null;
			
			try {
			    
				tx = session.beginTransaction();
    			
	    		for(Iterator it = acompRefItens.iterator(); it.hasNext();){
	    			AcompReferenciaItemAri ari = (AcompReferenciaItemAri) it.next();
	    			if(ari.getStatusRelatorioSrl() == null || (ari.getStatusRelatorioSrl() != null && ari.getStatusRelatorioSrl().getCodSrl().intValue() == STATUS_EM_EDICAO)){
	    				ari.setDataUltManutAri(Data.getDataAtual());
	    				ari.setCodUsuUltManutAri(usuarioLogado.getCodUsu());
	    				ari.setStatusRelatorioSrl(statusLiberado);
	    				
	    				listArisAlterados.add(ari);	    				
	    				
	    				session.update(ari);
	    			}
	    		}
	    		
	    		tx.commit();
	    		
				if(enviarEmail){
   				
    				for(Iterator itList = listArisAlterados.iterator(); itList.hasNext();){
    					AcompReferenciaItemAri ari = (AcompReferenciaItemAri) itList.next();
    					
    					List listUtfas = new ArrayList(ari.getItemEstruturaIett().getItemEstUsutpfuacIettutfas());
    					for(Iterator itUtfa = listUtfas.iterator(); itUtfa.hasNext();){
    					
	    					ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itUtfa.next();
	    					
	    					TfuncacompConfigmailTfacfgmPK tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
	    					tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
	    					tfcfgmPK.setCodTpfa(itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa().getCodTpfa());
	    					
	    					TfuncacompConfigmailTfacfgm tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
	    					
	    					if ("S".equals(tfcfm.getEnviaMailTfacfgm())) {

	    						//UsuarioUsu usu = (UsuarioUsu) usuDao.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getComp_id().getCodUsu());
	    						
	    						List usuarios = new ArrayList();
	    						if (itemEstUsutpfacIetutfa.getUsuarioUsu() != null) {
	    							usuarios.add((UsuarioUsu) usuDao.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getUsuarioUsu().getCodUsu()));
	    						} else if (itemEstUsutpfacIetutfa.getSisAtributoSatb() != null){
	    							usuarios.addAll(usuDao.getUsuariosBySisAtributoSatb(itemEstUsutpfacIetutfa.getSisAtributoSatb()));
	    						}
	    						
	    						Iterator itUsu = usuarios.iterator();
	    						
	    						while (itUsu.hasNext()){
	    						
	    							UsuarioUsu usu = (UsuarioUsu) itUsu.next();							
	    						
	    							// controle para n�o enviar e-mail para o usu�rio logado
	    	    					if(usu.equals(usuarioLogado)) {
	    	    						continue;
	    	    					}
	    					
		    						Long   codIett	 = Long.valueOf(0);
		    						String textoMail = "";
		    						String assunto   = "";
		    						String remetente = "";
		    					
		    						if( configMailCfgm.getTextosSiteMail() != null ) {
		    							textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
		    							assunto   = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
		    							remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
		    						}
		    						
		    						// se nao tem remetente configurado no textoSite, pega da empresa.
		    						if( "".equals(remetente) || remetente == null ) {
		    							List listEmpresa = new ArrayList();
		    							EmpresaEmp emp = null;
		
		    							listEmpresa = empDAO.listar(EmpresaEmp.class, null);
		
		    							for(Iterator itEmp = listEmpresa.iterator(); itEmp.hasNext();){
		    								emp = (EmpresaEmp) itEmp.next();
		    							}
		    							
		    							remetente = emp.getEmailContatoEmp();
		    						}
		    					
		    						if( ari.getItemEstruturaIett() != null )
		    							codIett = ari.getItemEstruturaIett().getCodIett();
		
		    						AgendadorEmail ae = new AgendadorEmail();
		    						
		    						String descricaoEvento = null;
		    						String html = null;
									
									descricaoEvento = "Libera��o/Recupera��o de Acompanhamento";
									String labelQuemAlterou = AgendadorEmail.LABEL_WHO_CHANGE_LIBERACAO;
									html = ae.montaEmail(textoMail, usu.getNomeUsu(), usuarioLogado.getNomeUsuSent(), codIett, 
											descricaoEvento, null, null, labelQuemAlterou,
											ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa()).toString();
									ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "", usu);
	    						}
	    					}
    					}
    				}
				}
				
				retorno = true;
    		}catch (HibernateException e) {
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
    	return retorno;
    }

    /**
     * Verifica se existe algum acompanhamento em edi��o em um per�odo.
     * 
     * @author aleixo
     * @param aref - Per�odo
     * @return
     * @throws ECARException
     */
    public boolean verificarAcompanhamentoEmEdicao(AcompReferenciaAref aref) throws ECARException{
    	if(aref.getAcompReferenciaItemAris() != null && !aref.getAcompReferenciaItemAris().isEmpty()){
    		for(Iterator it = aref.getAcompReferenciaItemAris().iterator(); it.hasNext();){
    			AcompReferenciaItemAri ari = (AcompReferenciaItemAri) it.next();
    			//Se existir algum ari em edi��o, retorna true (o acompanhamento est� em edi��o (pode ser liberado).
    			if(ari.getStatusRelatorioSrl() == null || (ari.getStatusRelatorioSrl() != null && ari.getStatusRelatorioSrl().getCodSrl().intValue() == STATUS_EM_EDICAO)){
    				return true;
    			}
    		}
	    		
    	}
    	return false;
    }

    /**
     * Verifica a exist�ncia do AcompanhamentoReferencia de um determinado item.
     * Se existir, retorna o objeto AcompReferenciaItemAri. Sen�o, retorna null
     * @param acompanhamento
     * @param item
     * @return
     * @throws ECARException
     * 
     */
    public AcompReferenciaItemAri getAcompReferenciaItemByItemEstruturaIett(AcompReferenciaAref acompanhamento, ItemEstruturaIett item) throws ECARException{
    	try{
            if(acompanhamento.getCodAref() != null){
    	    	StringBuilder query = new StringBuilder("select ari from AcompReferenciaItemAri ari")
    	    							.append(" where ari.itemEstruturaIett.codIett = :codIett")
    	    							.append(" and ari.acompReferenciaAref.codAref = :codAref")
    	    							.append(" and ari.itemEstruturaIett.indAtivoIett = 'S'");

    	    	Query q = this.getSession().createQuery(query.toString());
    			q.setLong("codIett", item.getCodIett().longValue());
    			q.setLong("codAref", acompanhamento.getCodAref().longValue());
    			q.setCacheable(true);
    			q.setCacheMode(CacheMode.NORMAL);
    			List retorno = q.list();

    			
    			if(retorno == null || retorno.isEmpty()){
                    return null;
                } else { 
                	return (AcompReferenciaItemAri) retorno.get(0);
                }
            }else{
                return null;
            }            
        } catch(HibernateException e){
			this.logger.error(e);
            throw new ECARException(e);
        }
    }
   
    
    //List<AcompReferenciaItemAri> lista =  referenciaItemDao.getListaAcompReferenciaItemByItemEstruturaIett(arefSelecionada,listaCodigosIett);
    public List<AcompReferenciaItemAri> getListaAcompReferenciaItemByItemEstruturaIett(Long arefSelecionada,List<Long> listaCodigosIett)throws ECARException{
    	List<AcompReferenciaItemAri> lista = null;
    	
    	try{
    	    	StringBuilder query = new StringBuilder("select ari from AcompReferenciaItemAri ari")
    	    							.append(" where ari.itemEstruturaIett.codIett in (:listCodIett)")
    	    							.append(" and ari.acompReferenciaAref.codAref = :codAref")
    	    							.append(" and ari.itemEstruturaIett.indAtivoIett = 'S'");

    	    	Query q = this.getSession().createQuery(query.toString());
    			q.setParameterList("listCodIett", listaCodigosIett);
    			q.setLong("codAref", arefSelecionada);
    			q.setCacheable(true);
    			q.setCacheMode(CacheMode.NORMAL);
    			//List retorno = q.list();
    			lista = q.list();
    			
    			if(lista == null || lista.isEmpty()){
                    return null;
                } else { 
                	return lista;
                }
        } catch(HibernateException e){
			this.logger.error(e);
            throw new ECARException(e);
        }
    	
    	//return lista;
    }
    
    
    /**
     * 
     * @param lista
     * @param iett
     * @param codigoArefSelecionada
     * @return
     * @throws ECARException
     */
    
    public AcompReferenciaItemAri getAcompReferenciaItemByItemEstruturaIett(List<AcompReferenciaItemAri> lista,
    		ItemEstruturaIett iett,Long codigoArefSelecionada) throws ECARException{
    	AcompReferenciaItemAri objetoRetorno = null;
    	for (AcompReferenciaItemAri objeto : lista) {
    		Long codigo = objeto.getItemEstruturaIett().getCodigo();
    		Long codAref = objeto.getAcompReferenciaAref().getCodAref();
    		
    		if(codAref != null
    				&& codAref != null){
    				
    			if(codigo.equals(iett.getCodigo()) 
    					&& codAref.equals(codigoArefSelecionada)){
    				objetoRetorno =  objeto;
    				break;
    			}
    		}
		}
    	
    	return objetoRetorno;
    }
    public AcompReferenciaItemAri getAcompReferenciaItemByItemEstruturaIettOrgao(OrgaoOrg secretaria,
    		List<AcompReferenciaItemAri> lista,ItemEstruturaIett iett,AcompReferenciaAref acompanhamento) throws ECARException{
    	
    	AcompReferenciaItemAri objetoRetorno = null;
    	for (AcompReferenciaItemAri objeto : lista) {
    		
    		if(!isValidoParametrosDePesquisa(objeto)){
    			continue;
    		}
    		
    		Long codigo = objeto.getItemEstruturaIett().getCodigo();
    		Long codAref = objeto.getAcompReferenciaAref().getCodAref();
    		Long codOrg = null;
    		String diaAref = objeto.getAcompReferenciaAref().getDiaAref();
    		String mesAref = objeto.getAcompReferenciaAref().getMesAref();
    		String anoAref = objeto.getAcompReferenciaAref().getAnoAref();
    		Long codTa =objeto.getAcompReferenciaAref().getTipoAcompanhamentoTa().getCodTa();
    		
    		if(secretaria != null){
    		  		codOrg = secretaria.getCodOrg(); 
    		}
    		
    		
   			if(codigo.equals(iett.getCodigo()) 
   					&& (codAref.equals(acompanhamento.getCodAref())
   					&& diaAref.equals(acompanhamento.getDiaAref())
   					&& mesAref.equals(acompanhamento.getMesAref())
   					&& anoAref.equals(acompanhamento.getAnoAref())
   					&& codTa.equals(acompanhamento.getTipoAcompanhamentoTa().getCodTa()))){
    		
   				if(codOrg == null){
   					objetoRetorno =  objeto;
   					break;
   				}else if(codOrg.equals(objeto.getAcompReferenciaAref().getOrgaoOrg().getCodOrg())){
   					objetoRetorno =  objeto;
   					break;
   				}
   			}
    		
		}
    	
    	return objetoRetorno;
    }

    private Boolean isValidoParametrosDePesquisa(AcompReferenciaItemAri objeto){
    	
    	try {
    		
    		objeto.getItemEstruturaIett().getCodigo();
    		objeto.getAcompReferenciaAref().getCodAref();
    		objeto.getAcompReferenciaAref().getDiaAref();
    		objeto.getAcompReferenciaAref().getMesAref();
    		objeto.getAcompReferenciaAref().getAnoAref();
    		objeto.getAcompReferenciaAref().getTipoAcompanhamentoTa().getCodTa();
    		
    		return true;
		} catch (NullPointerException e) {
			return false;
		}
    }
    
    
    
    /**
     * Retorna um registro de AcompRefLimitesArl a partir de uma fun��o de Acompanhamento e um Acompanhamento
     * @param funcAcomp
     * @param acompanhamento
     * @return
     * @throws ECARException
     */
    public AcompRefLimitesArl getAcompRefLimitesByAcompReferenciaTipoFuncAcomp(
            													TipoFuncAcompTpfa funcAcomp, 
            													AcompReferenciaAref acompanhamento) throws ECARException{
        AcompRefLimitesArlPK pk = new AcompRefLimitesArlPK();
        pk.setCodAref(acompanhamento.getCodAref());
        pk.setCodTpfa(funcAcomp.getCodTpfa());
        try{
            AcompRefLimitesArl acomp = (AcompRefLimitesArl) this.buscar(AcompRefLimitesArl.class, pk);
            return acomp;
        } catch (ECARException e){
        	//n�o � necess�rio lan�ar exce��o aqui
            return null;
        }
        
    }
    
    /**
     * Retorna um registro de AcompRefItemLimitesArli a partir de uma fun��o de Acompanhamento e um Acompanhamento
     * @param funcAcomp
     * @param acompanhamento
     * @return
     * @throws ECARException
     */
    public AcompRefItemLimitesArli getAcompRefItemLimitesByAcompReferenciaItemTipoFuncAcomp(
            													TipoFuncAcompTpfa funcAcomp, 
            													AcompReferenciaItemAri acompanhamento) throws ECARException{
        AcompRefItemLimitesArliPK pk = new AcompRefItemLimitesArliPK(); 
        pk.setCodAri(acompanhamento.getCodAri());
        pk.setCodTpfa(funcAcomp.getCodTpfa());
        try{
            AcompRefItemLimitesArli acomp = (AcompRefItemLimitesArli) this.buscar(AcompRefItemLimitesArli.class, pk);
            return acomp;
        } catch (ECARException e){
        	this.logger.error(e);
            return null;
        }
        
    }
        
    /**
     * Retorna as fun��es de acompanhamento vinculadas a um Item de Acompanhamento de
     *  Refer�ncia ordenadas hierarquicamento
     * @param acompReferenciaItem
     * @return
     * @throws ECARException
     */
    public List getTipoFuncAcompByAcompRefrenciaItem(AcompReferenciaItemAri acompReferenciaItem) throws ECARException{
        List retorno = new ArrayList();
        Iterator it = acompReferenciaItem.getAcompRefItemLimitesArlis().iterator();
        List funcoesEmitemPosicao = new TipoFuncAcompDao(request).getTipoFuncAcompEmitePosicao();
        List funcoesItem = new ArrayList();
        while(it.hasNext()){
            TipoFuncAcompTpfa tipoFuncaoAcomp = ((AcompRefItemLimitesArli)it.next()).getTipoFuncAcompTpfa();
            funcoesItem.add( tipoFuncaoAcomp );
        }
        
        for (Iterator itFuncoesEmitemPosicao = funcoesEmitemPosicao.iterator(); itFuncoesEmitemPosicao.hasNext();) {
			TipoFuncAcompTpfa funcaoEmitePosicao = (TipoFuncAcompTpfa) itFuncoesEmitemPosicao.next();
            if(funcoesItem.contains(funcaoEmitePosicao))
                retorno.add(funcaoEmitePosicao);
        }

        return (List) new TipoFuncAcompDao(null).ordenarTpfaBySequencia(acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt());
    }
    
    /**
     * Retorna objetos  AcompRefLimitesArl vinculados a um AcompReferenciaItemAri
     * @param acompReferenciaItem
     * @return
     * @throws ECARException
     */
    public List getAcompRefItemLimitesArliByAcompRefrenciaItem(AcompReferenciaItemAri acompReferenciaItem) throws ECARException{
        List retorno = new ArrayList();

        TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
        List funcoesEmitemPosicao = tipoFuncAcompDao.ordenarTpfaBySequencia(acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt());
        List funcoesItem = new ArrayList();
        List limites = new ArrayList();
        
        for (Iterator it = acompReferenciaItem.getAcompRefItemLimitesArlis().iterator(); it.hasNext();) {
			AcompRefItemLimitesArli limite = (AcompRefItemLimitesArli) it.next();
            TipoFuncAcompTpfa tipoFuncaoAcomp = limite.getTipoFuncAcompTpfa();            
            funcoesItem.add( tipoFuncaoAcomp );
            limites.add(limite);
        }
        for (Iterator itFuncoesEmitemPosicao = funcoesEmitemPosicao.iterator(); itFuncoesEmitemPosicao.hasNext();) {
			TipoFuncAcompTpfa funcaoEmitePosicao = (TipoFuncAcompTpfa) itFuncoesEmitemPosicao.next();
            if(funcoesItem.contains(funcaoEmitePosicao)){
                retorno.add(limites.get(funcoesItem.indexOf(funcaoEmitePosicao)));
            }
        }
        return retorno;
    }
    
    /**
     * Retorna a mensagem a ser exibida quando o usu�rio clica em Liberar Acompanhamento, avisando quais posi��es ainda
     * n�o foram gravadas
     * @param acompReferenciaItem
     * @return
     * @throws ECARException
     */
    public String getMensagemAlertLiberarAcompanhamento(AcompReferenciaItemAri acompReferenciaItem) throws ECARException{
        String msg = "";
        List<String> listaLabelPosicaoFuncaoAcompanhamento = new ArrayList<String>();
        
        for (Iterator it = getAcompRefItemLimitesArliByAcompRefrenciaItem(acompReferenciaItem).iterator(); it.hasNext();) {
			AcompRefItemLimitesArli limite = (AcompRefItemLimitesArli) it.next();
			boolean encontrou = false;
			
			for (Iterator itAcompRelatorio = acompReferenciaItem.getAcompRelatorioArels().iterator(); itAcompRelatorio.hasNext();) {
				AcompRelatorioArel acompRelatorio = (AcompRelatorioArel) itAcompRelatorio.next();
                if(acompRelatorio.getTipoFuncAcompTpfa().equals(limite.getTipoFuncAcompTpfa())){
                    if("N".equals(acompRelatorio.getIndLiberadoArel())){
                    	listaLabelPosicaoFuncaoAcompanhamento.add(limite.getTipoFuncAcompTpfa().getLabelPosicaoTpfa());
                        encontrou = true;
                    } else {
                        if("S".equals(acompRelatorio.getIndLiberadoArel()))
                            encontrou = true;
                    }
                }                
            }
            if(!encontrou){
            	listaLabelPosicaoFuncaoAcompanhamento.add(limite.getTipoFuncAcompTpfa().getLabelPosicaoTpfa());
            }
            
        }                   
        
        if(!listaLabelPosicaoFuncaoAcompanhamento.isEmpty()) {
        	msg = montarMensagem(listaLabelPosicaoFuncaoAcompanhamento);
        }
        
        return msg;
    }
       
    /**
     * Retorna uma String contendo os elementos da lista separados por virgula.
     * 
     * @param listaLabelPosicaoFuncaoAcompanhamento
     * @return
     */
    private String montarMensagem(List<String> listaLabelPosicaoFuncaoAcompanhamento) {
		
    	StringBuffer retorno = new StringBuffer();
    	int deslocamento;
    	
    	for (int i=0;i<listaLabelPosicaoFuncaoAcompanhamento.size();i++) {
			
    		String label = listaLabelPosicaoFuncaoAcompanhamento.get(i);
    		
    		retorno.append(label);
    		
    		//posi��es separadas por virgula
    		deslocamento = i+2;  
    		if (deslocamento < listaLabelPosicaoFuncaoAcompanhamento.size()){
    			retorno.append(", ");
    		} else if (deslocamento == listaLabelPosicaoFuncaoAcompanhamento.size()){
    			retorno.append(" e ");
    		} 
    		
		}
    	
    	if (listaLabelPosicaoFuncaoAcompanhamento.size() > 1) {
    		retorno.append(" n�o foram liberadas. Concluir este acompanhamento? ");
    	} else {
    		retorno.append(" n�o foi liberada. Concluir este acompanhamento? ");
    	}
    	
		return retorno.toString();
	}

	/**
     * Verifica se um item deve ser acompanhado no m�s atual, utilizando a data inicial do item e sua periodicidade
     * @param item
     * @param acompanhamento
     * @return
     */
    public boolean verificaAcompanhamentoItemMes(ItemEstruturaIett item, AcompReferenciaAref acompanhamento){

        Date dataAtual = new Date();
        /* numero de meses de um ano: 0..11 + 1 = 12 meses */
        int numMeses = Data.getGregorianCalendar(dataAtual).getMaximum(Calendar.MONTH) + 1; 
        /* numero do mes atual 0..11 */
        
        int mesRef = Integer.parseInt(acompanhamento.getMesAref());  
        
        if(item.getPeriodicidadePrdc() == null || item.getDataInicioIett() == null){
            return false;
        }

        /* periodicidade do item em meses */
        int periodicidade = item.getPeriodicidadePrdc().getNumMesesPrdc().intValue();
        /* numero do mes da data de inicio do item 0.11 */
        int mesInicioItem = Data.getGregorianCalendar(item.getDataInicioIett()).get(Calendar.MONTH)+1;
        
        /* verifica se o mes atual est� entre os meses de periodicidade para o item
         * Faz um loop em quando periodos existirem testando se o mesAtual � igual a um mes obtido
         * ex Data = 02/02/2005 periodicidade = 3 -> 02, 05, 08, 11
         * ex Data = 01/10/2005 periodicidade = 2 -> 12, 02, 04, 06, 08, 10
         *  
         * quantidade de periodos:
         * 12 / 1 = 12 periodos 
         * 12 / 5 = 2 + 1 periodos (1 6 11) soma um por causa do resto
         */

        boolean ehMesPeriodicidade = false;
        int qtdPeriodos = numMeses / periodicidade + (numMeses % periodicidade == 0 ? 0 : 1);
        int mesInicioIett = mesInicioItem;
        for (int p = 0; p < qtdPeriodos && !ehMesPeriodicidade; p++, mesInicioIett = (mesInicioIett + periodicidade)%numMeses) {
            if (mesRef == mesInicioIett)
                ehMesPeriodicidade = true;
            
        }
        return ehMesPeriodicidade;
    }
    
    /**
     * Verifica a exist�ncia de um AcompReferenciaItem para o mesmo Item, M�s e Ano, 
     *   tanto para altera��o, quanto para inclus�o.
     * 
     * Funcionamento: 
     *     1 - Seleciona todos os AcompReferencia que possuem mesmo M�s e Ano, se n�o encontrar, o m�todo 
     * 				j� retorna FALSE, indicando que n�o existe um AcompReferenciaItem para o mesmo Item,
     * 				M�s e Ano.
     *     2 - Caso encontre, realiza um buscar verificando se estou utilizando um AcompReferenciaItem,
     * 				caso n�o encontre retorna NULL satisfazendo a futura utilidade do objeto.
     *     3 - Ap�s isso, realiza um iterator nos AcompReferencia encontrados (item 1).
     * 	   4 - Durante o iterator faz um select verificando a existencia de todos os AcompReferenciaItem 
     * 				para o AcompReferencia da lista e o Item passado como par�metro.
     * 	   5 - Caso o select (item 4) retorne mais de um AcompReferenciaItem quer dizer que existe outro 
     * 				AcompReferenciaItem para o mesmo Item, M�s e Ano, e o m�todo retorna TRUE.
     *     6 - Caso o select (item 4) retorne um AcompReferenciaItem, compara com o AcompReferenciaItem
     * 				que caso esteja utilizando (item 2), se for diferente, o m�todo tamb�m retornar� TRUE,
     * 				indicando que existe outro AcompReferenciaItem para o mesmo Item, M�s e Ano.  
     * 
     * @param item
     * @param acompReferencia
     * @return boolean
     * @throws HibernateException
     * @throws ECARException
     */
    public boolean verificaItemMesAno(ItemEstruturaIett item, AcompReferenciaAref acompReferencia) throws HibernateException, ECARException{
    	StringBuilder select = new StringBuilder("select acompRef from AcompReferenciaAref as acompRef")
    							.append(" where acompRef.anoAref = :ano")
    							.append(" and acompRef.mesAref = :mes");
    	
    	Query q = this.getSession().createQuery(select.toString());
    	
    	q.setString("ano", acompReferencia.getAnoAref());
    	q.setString("mes", acompReferencia.getMesAref());
    	
		List listaAcompRef = q.list();
		
		if(listaAcompRef.size() <= 0){
			// N�o existe nenhum AcompReferencia para o m�s e ano
			return false;
		}else{
			AcompReferenciaItemAri acompRefItem = new AcompReferenciaItemAri();
			
			// Buscar o acompRefItem que estou utilizando
			if(acompReferencia.getCodAref() != null){
				acompRefItem = this.getAcompReferenciaItemByItemEstruturaIett(acompReferencia, item);
			}else{
				acompRefItem = null;
			}
			
			for (Iterator it = listaAcompRef.iterator(); it.hasNext();) {
				AcompReferenciaAref acompReferenciaLista = (AcompReferenciaAref) it.next();			
				select = new StringBuilder("select acompRefItem from AcompReferenciaItemAri as acompRefItem ")
								.append(" join acompRefItem.acompReferenciaAref as acompRef")
								.append(" join acompRefItem.itemEstruturaIett as item")
								.append(" where item.codIett = :codIett")
								.append(" and item.indAtivoIett = 'S'")
								.append(" and acompRef.codAref = :codAref");
				
				q = this.getSession().createQuery(select.toString());
				
				q.setLong("codIett", item.getCodIett().longValue());
				q.setLong("codAref", acompReferenciaLista.getCodAref().longValue());
				
				List listaAcompRefItem = q.list();
				
				if(listaAcompRefItem.size() > 0){
					if(listaAcompRefItem.size() == 1){
						// Existe, mas com possibilidade de ser a que eu estou utilizando
						if(!listaAcompRefItem.contains(acompRefItem)){
							return true;
						}
					}else{
						// Existe AcompReferenciaItem para o item, m�s e ano
						return true;
					}
				}
			}
		}
		
    	return false;
    }
    
    /**
     * Verifica se os relat�rios ou realizado f�sico do acompanhamento de um item foi alterado
     * @param acompanhamento
     * @param item
     * @return
     * @throws ECARException
     */
    public boolean isAcompanhamentoAlterado(AcompReferenciaAref acompanhamento, ItemEstruturaIett item) throws ECARException{
        try{
            AcompReferenciaItemAri acompReferenciaItem = getAcompReferenciaItemByItemEstruturaIett(acompanhamento, item);
            
            if(acompReferenciaItem != null){
            	StringBuilder query = new StringBuilder("select ARF from AcompRealFisicoArf as ARF")
            						.append(" where ARF.qtdRealizadaArf is not null ")
            						.append(" and ARF.itemEstruturaIett.codIett = :codIett")
            						.append(" and ARF.itemEstruturaIett.indAtivoIett = 'S'")
            						.append(" and ARF.mesArf = :mes")
            						.append(" and ARF.anoArf = :ano");
            	
            	Query q = this.getSession().createQuery(query.toString());
            	
            	q.setLong("codIett", acompReferenciaItem.getItemEstruturaIett().getCodIett().longValue());
            	q.setLong("mes", Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()).longValue());
            	q.setLong("ano", Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()).longValue());
		
            	List listArf = q.list();
            	
            	if(listArf != null && listArf.size() > 0) {
                    return true;
            	}
            	else {
                    if(this.session.createFilter(acompReferenciaItem.getAcompRelatorioArels(), "where this.dataUltManutArel is not null").list().size() > 0) {
                        return true;
                    }
            	}
            }

            return false;

        } catch(HibernateException e){
			this.logger.error(e);
            throw new ECARException(e);            
        }
    }
    
    
    /**
     * Retorna lista de AcompRealFisicoArf onde qtdRealizada n�o foram informadas
     * @param acompReferenciaItem
     * @return
     * @throws ECARException
     * @throws HibernateException
     */
    public List getAcompRealFisicoArfsComQtdNaoInformada(AcompReferenciaItemAri acompReferenciaItem) throws ECARException, HibernateException{
    	try {
			StringBuilder query = new StringBuilder("select ARF from AcompRealFisicoArf as ARF")
								.append(" where ARF.qtdRealizadaArf is null and ARF.situacaoSit is null")
								.append(" and ARF.itemEstruturaIett.codIett = :codIett")
								.append(" and ARF.itemEstruturaIett.indAtivoIett = 'S'")
								.append(" and ARF.mesArf = :mes")
								.append(" and ARF.anoArf = :ano");
			
			Query q = this.getSession().createQuery(query.toString());
			
			q.setLong("codIett", acompReferenciaItem.getItemEstruturaIett().getCodIett().longValue());
			q.setLong("mes", Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()).longValue());
			q.setLong("ano", Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()).longValue());
			
			return q.list();
    	}
    	catch(Exception e) {
			throw new ECARException(e);
    	}
    }
    
    /**
     * Retorna lista de Indicadores De Resultado que n�o estejam gravados em 
     * AcompRealFisico que fa�am parte do Item do ARI.
     * 
     * @param acompReferenciaItem
     * @return
     * @throws Exception 
     */
    public List getNovosIndicadores(AcompReferenciaItemAri acompReferenciaItem) throws Exception {
    	AcompRealFisicoArf acompRealFisico = new AcompRealFisicoArf();
    	
    	List retorno = new ArrayList();
        List lista = new ArrayList();
        List listaIndAcompRealFisico = new ArrayList();
        List listaIndicadores = new ArrayList();
        
    	AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null);
//    	List listArf = arfDao.buscarPorIett(
//    			acompReferenciaItem.getItemEstruturaIett().getCodIett(),
//    			Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()),
//    			Long.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()));
    	List listArf = arfDao.getIndResulByAcompRefItemBySituacao(acompReferenciaItem, Dominios.TODOS, false);

    	lista.addAll(listArf);
    	
    	for (Iterator it = lista.iterator(); it.hasNext();) {
			acompRealFisico = (AcompRealFisicoArf) it.next();

			int mesAref = Integer.valueOf(acompReferenciaItem.getAcompReferenciaAref().getMesAref()).intValue();
			int anoAref = Integer.valueOf(acompReferenciaItem.getAcompReferenciaAref().getAnoAref()).intValue();
			int mesArf = Integer.valueOf(acompRealFisico.getMesArf().toString()).intValue();
			int anoArf = Integer.valueOf(acompRealFisico.getAnoArf().toString()).intValue();
			
			if(anoArf == anoAref && mesArf == mesAref){
				listaIndAcompRealFisico.add(acompRealFisico.getItemEstrtIndResulIettr());
			}
			else {
				if(acompRealFisico.getSituacaoSit() != null && "S".equals(acompRealFisico.getSituacaoSit().getIndConcluidoSit())){
					listaIndAcompRealFisico.add(acompRealFisico.getItemEstrtIndResulIettr());
				}
			}
        }
        
		StringBuilder select = new StringBuilder("select indResul from ItemEstrtIndResulIettr as indResul")
							.append(" where indResul.itemEstruturaIett.codIett = :codIett")
							.append(" and indResul.itemEstruturaIett.indAtivoIett = 'S'")
							//Mantis-POA: caso 0010021 
							//Adicionado para n�o listar no acompanhamento os indicadores exclu�dos no cadastro
							.append(" and indResul.indAtivoIettr = 'S'");
		
		Query q = this.getSession().createQuery(select.toString());
		
		q.setLong("codIett", acompReferenciaItem.getItemEstruturaIett().getCodIett().longValue());
		
		listaIndicadores = q.list();
		
		/* Retira da primeira lista todos os da segunda lista, sobrando os novos */
		retorno.addAll(Util.diferenca(listaIndicadores,listaIndAcompRealFisico));

		return retorno;
    }
    
    /**
     * Metodo que retorna valor booleano para a Edi��o do AcompanhamentoRealFisico.
     * 		Para que seja edit�vel:
     * 			- deve possuir fun��o com InformaAndamento = "S";
     * 			- a data limite para acompanhamento n�o pode ser atingida;
     * 			- o ARI deve estar liberado.
     * @param usuario
     * @param acompReferenciaItem
     * @throws ECARException
     */
    public void verificaEditarAcompRealFisico(UsuarioUsu usuario, 
    								AcompReferenciaItemAri acompReferenciaItem) throws ECARException{
    	/* O USU�RIO DEVE POSSUIR FUN��O COM INFORMA ANDAMENTO = "S" */
    	if(!new ItemEstUsutpfuacDao(request).getFuncaoAcompInfAndamento(usuario, acompReferenciaItem.getItemEstruturaIett()))
    		throw new ECARException(new Mensagem(null).getMensagem("acompanhamento.realizadoFisico.naoEditar.usuarioSemPermissao"));

    	// Estava com erro o tratamento da data limite (BUG 2133)
    	Calendar dataAtual = Calendar.getInstance();

    	dataAtual.clear(Calendar.HOUR);
    	dataAtual.clear(Calendar.HOUR_OF_DAY);
    	dataAtual.clear(Calendar.MINUTE);
    	dataAtual.clear(Calendar.SECOND);
    	dataAtual.clear(Calendar.MILLISECOND);
    	dataAtual.clear(Calendar.AM_PM);

    	Calendar dataLimite = Calendar.getInstance();
    	dataLimite.setTime(acompReferenciaItem.getDataLimiteAcompFisicoAri());
    	
    	dataLimite.clear(Calendar.HOUR);
    	dataLimite.clear(Calendar.HOUR_OF_DAY);
    	dataLimite.clear(Calendar.MINUTE);
    	dataLimite.clear(Calendar.SECOND);
    	dataLimite.clear(Calendar.MILLISECOND);
    	dataLimite.clear(Calendar.AM_PM);
    	
    	/* PODE SER EDITADO AT� A DATA LIMITE */
    	if(dataAtual.compareTo(dataLimite) > 0)
    		throw new ECARException(new Mensagem(null).getMensagem("acompanhamento.realizadoFisico.naoEditar.dataLimite"));
    	
    	/* O ARI DEVE ESTAR EM EDI��O */
    	if(acompReferenciaItem.getStatusRelatorioSrl().getCodSrl().intValue() == STATUS_LIBERADO)
    		throw new ECARException(new Mensagem(null).getMensagem("acompanhamento.realizadoFisico.naoEditar.liberado"));

    	/* SE DATA DE INICIO FOR NULA - (BUG 1935) */
    	//if(acompReferenciaItem.getItemEstruturaIett().getDataInicioIett() == null) {
    	//	throw new ECARException(new Mensagem(null).getMensagem("acompanhamento.realizadoFisico.naoEditar.dataInicioNaoInformada"));
    	//}

    	Calendar dataInicioIett = Calendar.getInstance();
    	dataInicioIett.setTime(new ItemEstruturaDao(request).ObtemDataInicialItemEstrutura( acompReferenciaItem.getItemEstruturaIett()));

    	Calendar dataInicioAcomp = Calendar.getInstance();
    	dataInicioAcomp.setTime(acompReferenciaItem.getDataInicioAri());
    	
    	dataInicioAcomp.clear(Calendar.HOUR);
    	dataInicioAcomp.clear(Calendar.HOUR_OF_DAY);
    	dataInicioAcomp.clear(Calendar.MINUTE);
    	dataInicioAcomp.clear(Calendar.SECOND);
    	dataInicioAcomp.clear(Calendar.MILLISECOND);
    	dataInicioAcomp.clear(Calendar.AM_PM);

    	// (BUG 1051): M�s/Ano da data de in�cio do acompanhamento deve ser posterior ou igual ao M�s/Ano da data informada no cadastro de programas e a��es.
    	String mesAcomp = "";
    	String mesIett = "";
    	
    	if (dataInicioAcomp.get(Calendar.MONTH) < 10){
    		mesAcomp = "0" + String.valueOf(dataInicioAcomp.get(Calendar.MONTH));
    	} else {
    		mesAcomp = String.valueOf(dataInicioAcomp.get(Calendar.MONTH));
    	}
    	
    	if (dataInicioIett.get(Calendar.MONTH) < 10){
    		mesIett = "0" + String.valueOf(dataInicioIett.get(Calendar.MONTH));
    	} else {
    		mesIett = String.valueOf(dataInicioIett.get(Calendar.MONTH));
    	}
    	
    	String inicioAcomp = String.valueOf(dataInicioAcomp.get(Calendar.YEAR)) + mesAcomp;
    	String inicioIett = String.valueOf(dataInicioIett.get(Calendar.YEAR)) + mesIett;
    	
    	if(Integer.parseInt(inicioIett) > Integer.parseInt(inicioAcomp)) {
    		throw new ECARException(new Mensagem(null).getMensagem("acompanhamento.realizadoFisico.naoEditar.dataInicioIettEAcomp"));
        }    	
    	
//    	String inicioAcomp = String.valueOf(dataInicioAcomp.get(Calendar.YEAR)) + String.valueOf(dataInicioAcomp.get(Calendar.MONTH));
//    	String inicioIett = String.valueOf(dataInicioIett.get(Calendar.YEAR)) + String.valueOf(dataInicioIett.get(Calendar.MONTH));
//
//    	if(Integer.parseInt(inicioIett) > Integer.parseInt(inicioAcomp)) {
//        	throw new ECARException(new Mensagem(null).getMensagem("acompanhamento.realizadoFisico.naoEditar.dataInicioIettEAcomp"));
//        }
    	
    	// (BUG 1051): Data atual deve ser posterior ou igual a data de in�cio do acompanhamento    
    	if(dataAtual.before(dataInicioAcomp)) {
    		throw new ECARException(new Mensagem(null).getMensagem("acompanhamento.realizadoFisico.naoEditar.dataAtualAnteriorDataInicioAcompanhamento"));
    	}
    }
    
    /**
     *
     * @param usuario
     * @param acompReferenciaItem
     * @return
     * @throws ECARException
     */
    public boolean podeAcessarAri(UsuarioUsu usuario, AcompReferenciaItemAri acompReferenciaItem) throws ECARException{
        Set usuariosPermitidos = acompReferenciaItem.getItemEstruturaIett().getItemEstUsutpfuacIettutfas();
        for (Iterator it = usuariosPermitidos.iterator(); it.hasNext();) {
			ItemEstUsutpfuacIettutfa fuac = (ItemEstUsutpfuacIettutfa) it.next();
			if (fuac.getUsuarioUsu() != null) {
				if(fuac.getUsuarioUsu().equals(usuario)){
					return true;
				}
			} else if (fuac.getSisAtributoSatb() != null){
				UsuarioDao usuDao = new UsuarioDao();
				Iterator itUsu = usuDao.getUsuariosBySisAtributoSatb(fuac.getSisAtributoSatb()).iterator();
				while (itUsu.hasNext()){
					UsuarioUsu usu = (UsuarioUsu) itUsu.next();
					if (usu.equals(usuario)){
						return true;
					}
				}
			}        
        }
        return false;
    }
    
    /**
     * Varia��o do metodo getAcompRelatorioArelOrderByFuncaoAcomp para receber a lista de fun��es de acompanhamento
     * ordenadas hieraquicamente. Para ser usada caso esse metodo tenha que ser chamado v�rias vezes para que n�o
     * se repita a busca pela hierarquia
     * 
     * A List ordenacaoTpfaEstrutura � obtida pelo m�todo tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas().
     * 
     * @param acompRefItem 
     * @param ordenacaoTpfaEstrutura
     * @return
     * @throws ECARException 
     */
    public List getAcompRelatorioArelOrderByFuncaoAcomp(AcompReferenciaItemAri acompRefItem, List ordenacaoTpfaEstrutura) throws ECARException{

        List lResultado = new ArrayList();
        //funAcomp = (List) new TipoFuncAcompDao(null).ordenarTpfaBySequencia(funAcomp, acompRefItem.getItemEstruturaIett().getEstruturaEtt());
        TipoFuncAcompTpfa funcao; 
        AcompRelatorioArel acompRelatorio;
        
        if (acompRefItem != null && acompRefItem.getAcompRelatorioArels() != null) {

        	
        	for (Iterator itOrd = ordenacaoTpfaEstrutura.iterator(); itOrd.hasNext();) {

            	OrdenacaoTpfaEstrutura ord = (OrdenacaoTpfaEstrutura) itOrd.next();
            	
            	
            	if(acompRefItem.getItemEstruturaIett().getEstruturaEtt().equals(ord.getEstrutura())){
	            	if(ord.getTipoFuncAcomp() != null && !ord.getTipoFuncAcomp().isEmpty()){

	            		for (Iterator itFunc = ord.getTipoFuncAcomp().iterator(); itFunc
								.hasNext();) {
	    	                funcao = (TipoFuncAcompTpfa) itFunc.next();
	    	                
	    	                for (Iterator itAcomp = acompRefItem.getAcompRelatorioArels().iterator(); itAcomp
									.hasNext();) {
	    	                    acompRelatorio = (AcompRelatorioArel) itAcomp.next();
	    	                    if (acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa() == funcao.getCodTpfa()) {
	    	                        lResultado.add(acompRelatorio);
	    	                        break;
	    	                    }
	    	                }
	            		}
	            	}
            	}
            }
        }
        return lResultado;
        
    }

    /**
     * Varia��o do metodo getAcompRelatorioArelOrderByFuncaoAcomp para v�rios Arels para receber a lista de fun��es de acompanhamento
     * ordenadas hieraquicamente. Para ser usada caso esse metodo tenha que ser chamado v�rias vezes para que n�o
     * se repita a busca pela hierarquia
     * 
     * A List ordenacaoTpfaEstrutura � obtida pelo m�todo tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas().
     * @param arels
     * @param ordenacaoTpfaEstrutura
     * @return List
     * @throws ECARException
     */
    public List getAcompRelatorioArelOrderByFuncaoAcomp(List arels, List ordenacaoTpfaEstrutura) throws ECARException{

        List lResultado = new ArrayList();
        
        if (arels != null && !arels.isEmpty()) {
        	
        	for (Iterator itOrd = ordenacaoTpfaEstrutura.iterator(); itOrd.hasNext();) {
				OrdenacaoTpfaEstrutura ord = (OrdenacaoTpfaEstrutura) itOrd.next();
	        	if(ord.getTipoFuncAcomp() != null && !ord.getTipoFuncAcomp().isEmpty()){
	                /* loop nas funcoes em ordem de filho para pai */
	        		
	        		for (Iterator itFunc = ord.getTipoFuncAcomp().iterator(); itFunc.hasNext();) {
		                TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) itFunc.next();
		                
		                for (Iterator itAcomp = arels.iterator(); itAcomp
								.hasNext();) {
		                    AcompRelatorioArel arel = (AcompRelatorioArel) itAcomp.next();
		                    if (arel.getTipoFuncAcompTpfa().getCodTpfa() == funcao.getCodTpfa() && arel.getAcompReferenciaItemAri().getItemEstruturaIett().getEstruturaEtt().equals(ord.getEstrutura())) {
		                        lResultado.add(arel);
		                    }
		               }
	        		}
	        	}
			}
        }
        return lResultado;
        
    }

    /**
     * Devolve a lista de AcompRelatorioArel ordenadas de acordo com a hierarquia
     * de funcoes de acompanhamento de filho para pai, ou seja, do mais fraco para
     * o mais forte na hierarquia
     * @param acompRefItem
     * @return
     * @throws ECARException
     */
    public List getAcompRelatorioArelOrderByFuncaoAcomp(
    				AcompReferenciaItemAri acompRefItem) throws ECARException {
        
    	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(request);
    	
        List lResultado = new ArrayList();
        List lFuncAcomp = tipoFuncAcompDao.ordenarTpfaBySequencia(acompRefItem.getItemEstruturaIett().getEstruturaEtt());
        
        TipoFuncAcompTpfa funcao; 
        AcompRelatorioArel acompRelatorio;
        
        if (acompRefItem != null && acompRefItem.getAcompRelatorioArels() != null) {
            /* loop nas funcoes em ordem de filho para pai */
        	for (Iterator itFunc = lFuncAcomp.iterator(); itFunc.hasNext();) {
                funcao = (TipoFuncAcompTpfa) itFunc.next();
                
                for (Iterator itAcomp = acompRefItem.getAcompRelatorioArels().iterator(); itAcomp.hasNext();) {
                	acompRelatorio = (AcompRelatorioArel) itAcomp.next();
                    if (acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa() == funcao.getCodTpfa()) {
                        lResultado.add(acompRelatorio);
                        break;
                    }
                }
            }
        }
        return lResultado;
    }
    
    /**
     * Retorna todos os AcompReferenciaItem que possuam o mesmo Item do ARI 
     * passado por par�metro, ordenado por ano e mes.
     * @param acompRefItem
     * @return
     * @throws HibernateException
     */
    public List getReferenciaByItem(AcompReferenciaItemAri acompRefItem) throws HibernateException{
    	String comparacao = "";
    	
    	if (acompRefItem.getAcompReferenciaAref().getOrgaoOrg() == null)
    		comparacao = "is null";
    	else
    		comparacao = "= " + acompRefItem.getAcompReferenciaAref().getOrgaoOrg().getCodOrg();
    	
    	StringBuilder select = new StringBuilder();
    	
    	select.append("select ari from AcompReferenciaItemAri as ari ");
    	select.append(" where ari.acompReferenciaAref.tipoAcompanhamentoTa.codTa = :codTa");
    	select.append(" and ari.itemEstruturaIett.codIett = :codIett");
    	select.append(" and ari.itemEstruturaIett.indAtivoIett = 'S'");
    	select.append(" and ari.acompReferenciaAref.orgaoOrg " + comparacao);
    	select.append(" order by ari.acompReferenciaAref.anoAref desc, ari.acompReferenciaAref.mesAref desc");
    	
    	
		Query q = this.session.createQuery(select.toString());
		
		q.setLong("codTa", acompRefItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getCodTa().longValue());
		q.setLong("codIett", acompRefItem.getItemEstruturaIett().getCodIett().longValue());
		
    	return q.list();
    }
    
    /**
     * Retorna lista de itens que tenham um Acompanhamento (AcompReferenciaItemAri) em algum dos Per�odos de Refer�ncia 
     * (AcompReferenciaAref) passados como par�metro
     * @param periodosConsiderados Lista de AcompReferenciaAref
     * @param niveisPlanejamento
     * @return List de AcompReferenciaItemAri. lista vazia se n�o foi informado nenhum periodo
     * @throws ECARException
     */
    public List getItensAcompanhamentoInPeriodos(Collection periodosConsiderados, Collection niveisPlanejamento) throws ECARException{
        
        try{        
                        
            if(periodosConsiderados.size() > 0){

                StringBuilder query = new StringBuilder("select distinct item from ItemEstruturaIett item ")
                					.append("join item.acompReferenciaItemAris aris join aris.acompReferenciaAref aref ")
                					.append("join item.itemEstruturaNivelIettns niveis ")
                					.append("where aref.codAref in (:listaAcompanhamentos)")
                					.append(" and item.indAtivoIett = 'S'");
                
                if(niveisPlanejamento.size() > 0)
                    query.append(" and niveis.codSatb in (:listaNiveis)");
                                               
                Query queryItens = this.getSession().createQuery(query.toString());                        
                        
                List listaCodigosAref = new ArrayList();
                
                for (Iterator it = periodosConsiderados.iterator(); it
						.hasNext();) {
                    AcompReferenciaAref aReferencia = (AcompReferenciaAref)it.next();
                    listaCodigosAref.add(aReferencia.getCodAref());
                }
                
                queryItens.setParameterList("listaAcompanhamentos", listaCodigosAref);
                
                
                if(niveisPlanejamento.size() > 0){
                    List listaCodigosNiveis = new ArrayList();
                    
                    for (Iterator itNiveis = niveisPlanejamento.iterator(); itNiveis
							.hasNext();) {
                        SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
                        listaCodigosNiveis.add(nivel.getCodSatb());
                    }
                    queryItens.setParameterList("listaNiveis", listaCodigosNiveis);
                }
            
                List listaItens = queryItens.list();
                                  
                List arvoreItens = new ItemEstruturaDao(request).getArvoreItens(listaItens, null);
                
                return arvoreItens;
                
            } else {
                // Se n�o foi informado nenhum periodo, retorna uma lista vazia
                return new ArrayList();
                
            }
            

        } catch(HibernateException e){
			this.logger.error(e);
            throw new ECARException(e);            
        }
        
        
    }
    
    /**
     * Retorna lista de itens que tenham um Acompanhamento (AcompReferenciaItemAri) em algum dos Per�odos de Refer�ncia 
     * (AcompReferenciaAref) passados como par�metro
     * @param periodosConsiderados Collection de AcompReferenciaAref
     * @param niveisPlanejamento Collection
     * @param orgaoResponsavel OrgaoOrg
     * @param usuarioUsu UsuarioUsu
     * @param gruposUsuario Set
     * @param codTipoAcompanhamento Long (se for nulo ignora o tipo de acompanhamento)
     * @param codIettPai Long (se for diferente de nulo, obt�m os filhos desse item)
     * @param itensSemInformacaoNivelPlanejamento
     * @param codCor
     * @param indLiberados
     * @return List de AcompReferenciaItemAri. lista vazia se n�o foi informado nenhum periodo
     * @throws ECARException
     */
    public Object[] getItensAcompanhamentoInPeriodosByOrgaoResp(Collection periodosConsiderados,
    		Collection niveisPlanejamento, List orgaos, UsuarioUsu usuarioUsu,
    		Set gruposUsuario, Long codTipoAcompanhamento, Long codIettPai, 
    		Boolean itensSemInformacaoNivelPlanejamento, Long codCor, String indLiberados, String filtroOE) 
    			throws ECARException {
    	//Chamado a partir dos relatorios
    	
    	
    	return getItensAcompanhamentoInPeriodosByOrgaoRespPaginadoConsiderarPermissao(periodosConsiderados,
        		niveisPlanejamento, orgaos, usuarioUsu,
        		gruposUsuario, codTipoAcompanhamento, codIettPai, 
        		itensSemInformacaoNivelPlanejamento, codCor, indLiberados, -2, 1, true, true, filtroOE, false);
    }
    
    
    /**
     * Retorna lista de itens que tenham um Acompanhamento (AcompReferenciaItemAri) em algum dos Per�odos de Refer�ncia 
     * (AcompReferenciaAref) passados como par�metro, considerando apenas os itens com permiss�o de visualiza��o e edi��o.
     * @param periodosConsiderados Collection de AcompReferenciaAref
     * @param niveisPlanejamento Collection
     * @param orgaoResponsavel OrgaoOrg
     * @param usuarioUsu UsuarioUsu
     * @param gruposUsuario Set
     * @param codTipoAcompanhamento Long (se for nulo ignora o tipo de acompanhamento)
     * @param codIettPai Long (se for diferente de nulo, obt�m os filhos desse item)
     * @param itensSemInformacaoNivelPlanejamento
     * @param codCor
     * @param indLiberados
     * @return List de AcompReferenciaItemAri. lista vazia se n�o foi informado nenhum periodo
     * @throws ECARException
     */
     public Object[] getItensAcompanhamentoInPeriodosByOrgaoRespConsiderarPermissao(Collection periodosConsiderados,
    		Collection niveisPlanejamento, Collection orgaosReferencias, UsuarioUsu usuarioUsu,
    		Set gruposUsuario, Long codTipoAcompanhamento, Long codIettPai, 
    		Boolean itensSemInformacaoNivelPlanejamento, Long codCor, String indLiberados,boolean montarHierarquia) 
    			throws ECARException {
    	 
    	
    	return getItensAcompanhamentoInPeriodosByOrgaoRespPaginadoConsiderarPermissao(periodosConsiderados,
        		niveisPlanejamento, orgaosReferencias, usuarioUsu,
        		gruposUsuario, codTipoAcompanhamento, codIettPai, 
        		itensSemInformacaoNivelPlanejamento, codCor, indLiberados, -2, 1,montarHierarquia, true, "", false);
    	
    }
    
    /**
     *
     * @param codTipoAcompanhamento
     * @param listPeriodosConsiderados
     * @param listCores
     * @param listTipoFuncoesAcompanhamento
     * @param usuario
     * @param gruposUsuario
     * @param niveisPlanejamento
     * @param itensSemInformacaoNivelPlanejamento
     * @param orgaoResponsavel
     * @param request
     * @return
     * @throws ECARException
     */
    @SuppressWarnings("static-access")
    public Object[] getItensAcompanhamentoFiltroItens(
    		Long codTipoAcompanhamento,
    		Collection listPeriodosConsiderados,
    		Collection listCores,
    		Collection listTipoFuncoesAcompanhamento,
    		UsuarioUsu usuario,
    		Set gruposUsuario,
    		Collection niveisPlanejamento,
    		Boolean itensSemInformacaoNivelPlanejamento,
    		OrgaoOrg orgaoResponsavel,
    		HttpServletRequest request
    		    		
    		) throws ECARException{
    	
    	try{
    		
//    		AcompRelatorioArel arel = null;
//   		arel.getCor().getCodCor()
    		
    		ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
            TipoAcompanhamentoDao tipoAcompanhamentoDao = new TipoAcompanhamentoDao(null);
            
            EstAtribTipoAcompEataDao estAtribTipoAcompEataDao = new EstAtribTipoAcompEataDao(request);
            
            TipoAcompanhamentoTa ta = (TipoAcompanhamentoTa) this.buscar(TipoAcompanhamentoTa.class, codTipoAcompanhamento);
            List listEstruturas = estAtribTipoAcompEataDao.getEstruturaEhFiltro(ta);	
    		
    		if(listPeriodosConsiderados.size() > 0){
    			StringBuilder query = new StringBuilder("select arel.acompReferenciaItemAri.itemEstruturaIett  from AcompRelatorioArel arel ");
    			query.append(" left join arel.acompReferenciaItemAri.itemEstruturaIett.itemEstruturaNivelIettns niveis ");

	    		
	    		StringBuilder where = new StringBuilder("where arel.acompReferenciaItemAri.acompReferenciaAref.codAref in (:listaReferencias) ");
	            
	    		if(itensSemInformacaoNivelPlanejamento.booleanValue()) {
                    if(niveisPlanejamento != null && niveisPlanejamento.size() > 0) {
                    	where.append(" and (niveis.codSatb in (:listaNiveis) or niveis is null)");
                    } else {
                    	where.append(" and niveis is null");
                    }
                } else {
                    if(niveisPlanejamento != null && niveisPlanejamento.size() > 0) {
                        where.append(" and (niveis.codSatb in (:listaNiveis)) ");
                    }
                }
	    			    			    		
	    		if (ta.getIndSepararOrgaoTa() != null && ta.getIndSepararOrgaoTa().equals("S")){
	                if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
	     //           	where.append(" and (arel.acompReferenciaItemAri.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg = :orgaoResp)");                	
	                	where.append(" and (arel.acompReferenciaItemAri.acompReferenciaAref.orgaoOrg.codOrg = :orgaoResp)");
	                }
	                else{
	                	where.append(" and (arel.acompReferenciaItemAri.acompReferenciaAref.orgaoOrg.codOrg is null)");
	                }
                }
	    			    		
	    		where.append(" and (arel.acompReferenciaItemAri.itemEstruturaIett.indAtivoIett = 'S') ");                
	            
	            if(codTipoAcompanhamento != null) {
	                where.append(" and (arel.acompReferenciaItemAri.acompReferenciaAref.tipoAcompanhamentoTa.codTa = :codTa) ");
	            }
	                        
	           
	            // s� filtra pelas cores se o acompanhamento tiver sido liberado. 
	            if(listCores != null && listCores.size() > 0) {            	
	            	where.append(" and (arel.cor.codCor in (:cores) ");  
	            	
	            	
	            	//verifica se tem que liberar o parecer para poder aparecer as carinhas
	            	if(ta.getIndLiberarParecerTa().equals("S")) {
	            		where.append(" and arel.indLiberadoArel = 'S'");
	            	} 
	            		
	            	//verifica se tem que liberar o acompanhamento para poder aparecer as carinhas
	            	if(ta.getIndLiberarAcompTa().equals("S")) {
	            		where.append(" and arel.acompReferenciaItemAri.statusRelatorioSrl.codSrl = :codLiberado");
	            	}
	            	
	            	where.append(")");
	            	
	            }
	            
	            if(listTipoFuncoesAcompanhamento != null && listTipoFuncoesAcompanhamento.size() > 0) {            	
	            	where.append(" and arel.tipoFuncAcompTpfa.codTpfa in (:listTpFuncAcomp) ");                
	            }
	            
	            Query queryItens = this.getSession().createQuery(query.toString() + where.toString());
	            
	            if(niveisPlanejamento != null && niveisPlanejamento.size() > 0) {
                    List<Long> listaCodigosNiveis = new ArrayList<Long>();                    
                    for (Iterator itNiveis = niveisPlanejamento.iterator(); itNiveis.hasNext();) {
						SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
                        listaCodigosNiveis.add(nivel.getCodSatb());
					}
                    queryItens.setParameterList("listaNiveis", listaCodigosNiveis);
                }
                
	            if (ta.getIndSepararOrgaoTa() != null && ta.getIndSepararOrgaoTa().equals("S")){
	                if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
	                	queryItens.setLong("orgaoResp", orgaoResponsavel.getCodOrg().longValue());
	                }
                }
                	                        
	            if(codTipoAcompanhamento != null) {
	            	// listar ARIs conforme o tipo de acompanhamento passado como par�metro
	                queryItens.setLong("codTa", codTipoAcompanhamento.longValue());
	            }
	            
	               
	            List<Long> listCodCores = new ArrayList<Long>();
	            for (Iterator iter = listCores.iterator(); iter.hasNext();) {
	            	Cor cor = (Cor) iter.next();
	            	listCodCores.add(cor.getCodCor());
	            }
	            
	            if(listCodCores != null && listCodCores.size() > 0){
	            	// listar ARIs conforme as cores passadas como par�metro
	            	queryItens.setParameterList("cores", listCodCores);
	            	if(ta.getIndLiberarAcompTa().equals("S")) {
	            		queryItens.setLong("codLiberado", Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO).longValue());
	            	}
	             	
	            }
	            
	            List<Long> listaCodigosAref = new ArrayList<Long>();	            
	            for (Iterator iter = listPeriodosConsiderados.iterator(); iter.hasNext();) {
	            	AcompReferenciaAref aReferencia = (AcompReferenciaAref) iter.next();
	                listaCodigosAref.add(aReferencia.getCodAref());
	            }
	            
	            queryItens.setParameterList("listaReferencias", listaCodigosAref);
	            
	            List<Long> listCodTipoFuncoesAcompanhamento = new ArrayList<Long>();
	            for (Iterator iter = listTipoFuncoesAcompanhamento.iterator(); iter.hasNext();) {
	            	TipoFuncAcompTpfa tipoFuncaoAcompanhamento = (TipoFuncAcompTpfa) iter.next();
	            	listCodTipoFuncoesAcompanhamento.add(tipoFuncaoAcompanhamento.getCodTpfa());
	            }
	            
	            if(listTipoFuncoesAcompanhamento != null && listTipoFuncoesAcompanhamento.size() > 0){
	            	queryItens.setParameterList("listTpFuncAcomp", listCodTipoFuncoesAcompanhamento);
	            }
	            
	            List<ItemEstruturaIett> listaItens = new ArrayList<ItemEstruturaIett>();
                List listaAris = queryItens.list();
                Iterator itListaAris = listaAris.iterator();
                
            	if(usuario == null) { //utilizado para o grafico.jsp - teste de performance
                    while(itListaAris.hasNext()) {
                    	AcompReferenciaItemAri ari = (AcompReferenciaItemAri)itListaAris.next();
                   		listaItens.add(ari.getItemEstruturaIett());
                    }
            	} else {
	                while(itListaAris.hasNext()) {
	                	/*AcompReferenciaItemAri ari = (AcompReferenciaItemAri)itListaAris.next();
	                	if(validaPermissao.permissaoLeituraAcompanhamento(ari, usuario, gruposUsuario)) {
	                		listaItens.add(ari.getItemEstruturaIett());
	                	}*/
	                	
	                	ItemEstruturaIett iett = (ItemEstruturaIett)itListaAris.next();
                		listaItens.add(iett);
	                }
                }

                List<ItemEstruturaIett> itensGeralComArvore = itemDao.getArvoreItens(listaItens, null);
                List<ItemEstruturaIett> arvoreItens = new ArrayList<ItemEstruturaIett>(itensGeralComArvore);
                                
                TipoAcompanhamentoTa tipoAcomp = null;
                
//               if(codTipoAcompanhamento != null) {
//              	tipoAcomp = (TipoAcompanhamentoTa) tipoAcompanhamentoDao.buscar(TipoAcompanhamentoTa.class, codTipoAcompanhamento);
//               }
               
               	return new Object[]{aplicarFiltros(itemDao.getItensOrdenados(arvoreItens, ta), listEstruturas, tipoAcomp, request), itensGeralComArvore};
                	            
    		}
    	
    		else{
    			return new Object[]{new ArrayList(), new ArrayList()};
    		}
    		
	    } catch(HibernateException e){
	    	this.logger.error(e);
	        throw new ECARException(e);            
	    } 
    }
    
    
    /**
	 * Aplica os filtros selecionados na tela na lista de resultado
	 * 
	 * @param itensEstrutura Collection 
	 * @param estruturas Collection 
	 * @param tipoAcompanhamentoTa TipoAcompanhamentoTa
	 * @param request HttpServletRequest 
	 * @return Collection contendo a lista de itens filtradas
	 * @throws ECARException
	 */
    private Collection aplicarFiltros(Collection itensEstrutura, Collection estruturas, TipoAcompanhamentoTa tipoAcompanhamentoTa, HttpServletRequest request) throws ECARException{
    	Collection retorno = new ArrayList();
    	
    	Iterator itEstrutura = estruturas.iterator();
    	List itensRemovidos = new ArrayList();
    	EstruturaDao estruturaDao = new EstruturaDao(request);
    	while (itEstrutura.hasNext()) {
    		EstruturaEtt ett = (EstruturaEtt) itEstrutura.next();
    		String codEtt = ett.getCodEtt().toString();
    		
    		List atributosLivres = estruturaDao.getAtributosLivresEstruturaDadosGeraisEhFiltro(ett, tipoAcompanhamentoTa);
    		
    		Iterator itItensEstrutura = itensEstrutura.iterator();
    		
    		/*
    		 * La�o para verificar se o item estrutura possui valor igual a algum definido no filtro
    		 * Entrada: todos os itens de estruturas
    		 * Sa�da: itens de estruturas que pertencem ao conjunto de valores definidos pelo filtro
    		 * Algoritmo:
    		 * 	Remove o item de estrutura que n�o atende a condi��o definida em cada filtro. Com isso simula um "AND" dos valores dos filtros.
    		 * Comentado por Leonardo Ribeiro, 21/01/09
    		 */
    		while (itItensEstrutura.hasNext()){
    			AtributoEstruturaListagemItens atributoEstruturaListagemItens= (AtributoEstruturaListagemItens) itItensEstrutura.next(); 
    			ItemEstruturaIett iett = atributoEstruturaListagemItens.getItem();
    			
    			if (iett.getItemEstruturaIett() != null){
    				if (itensRemovidos.contains(iett.getItemEstruturaIett().getCodIett())){
    					itensRemovidos.add(iett.getCodIett());
    					itItensEstrutura.remove();
    					continue;
    				}
    			}
    			
    			if (iett.getEstruturaEtt().equals(ett)){
    				
    				if (!"".equals(Pagina.getParamStr(request, codEtt + "_codIettPai"))) { 
                		if (iett.getItemEstruturaIett() != null && !iett.getItemEstruturaIett().getCodIett().equals(Long.valueOf(Pagina.getParamStr(request, codEtt + "_codIettPai")))){
                			itensRemovidos.add(iett.getCodIett());
                			itItensEstrutura.remove();
                			continue;
                		}
                	}
    				
    				if (!"".equals(Pagina.getParamStr(request, codEtt + "_codIett"))) { 
                		
    					if (iett.getCodIett() == null || !iett.getCodIett().equals(Long.valueOf(Pagina.getParamStr(request, codEtt + "_codIett")))){
    						itensRemovidos.add(iett.getCodIett());
                			itItensEstrutura.remove();
                			continue;
                		}
                	}
    				//Faz o filtro para cada campo do tipo String fixo da tabela de itemEstruturaIett
    				if (filtrarItemString(request, "siglaIett", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				} 				
    				if (filtrarItemString(request, "nomeIett", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
    				if (filtrarItemString(request, "descricaoIett", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
     				if (filtrarItemString(request, "origemIett", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
    				if (filtrarItemStringComoListaChecks(request, "indAtivoIett", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
    				if (filtrarItemStringComoListaChecks(request, "indMonitoramentoIett", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
    				if (filtrarItemStringComoListaChecks(request, "indBloqPlanejamentoIett", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
    				if (filtrarItemString(request, "objetivoGeralIett", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
    				if (filtrarItemString(request, "beneficiosIett", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
    				
    				//Faz o filtro para cada campo do tipo Date fixo da tabela de itemEstruturaIett
    				//para cada data chama o filtrarItemData para o intervalo inicio/fim.
   				
    				
    				/*DATA INICIO*/
    				if (filtrarItemData(request, "dataInicioIett", iett, itensRemovidos, itItensEstrutura, "Inicio")){
    					continue;
    				}  				   				
    				if (filtrarItemData(request, "dataInicioIett", iett, itensRemovidos, itItensEstrutura, "Fim")){
    					continue;
    				}
    				
    				
    				/*DATA TERMINO*/
    				if (filtrarItemData(request, "dataTerminoIett", iett, itensRemovidos, itItensEstrutura, "Inicio")){
    					continue;
    				}  				   				
    				if (filtrarItemData(request, "dataTerminoIett", iett, itensRemovidos, itItensEstrutura, "Fim")){
    					continue;
    				}
    				
    				
    				/*DATA ULTIMA MANUTEN��O*/
    				if (filtrarItemData(request, "dataUltManutencaoIett", iett, itensRemovidos, itItensEstrutura, "Inicio")){
    					continue;
    				}  				   				
    				if (filtrarItemData(request, "dataUltManutencaoIett", iett, itensRemovidos, itItensEstrutura, "Fim")){
    					continue;
    				}
    				
    				
    				/*DATA INCLUSAO*/
    				if (filtrarItemData(request, "dataInclusaoIett", iett, itensRemovidos, itItensEstrutura, "Inicio")){
    					continue;
    				}  				   				
    				if (filtrarItemData(request, "dataInclusaoIett", iett, itensRemovidos, itItensEstrutura, "Fim")){
    					continue;
    				}
    				
    				
    				/*DATA INICIO MONITORAMENTO*/
    				if (filtrarItemData(request, "dataInicioMonitoramentoIett", iett, itensRemovidos, itItensEstrutura, "Inicio")){
    					continue;
    				}  				   				
    				if (filtrarItemData(request, "dataInicioMonitoramentoIett", iett, itensRemovidos, itItensEstrutura, "Fim")){
    					continue;
    				}
    			
    				
    				
    				String valorCampo[] = Pagina.getParamLista(request, iett.getEstruturaEtt().getCodEtt() + "_areaAre");
    				boolean diferente = true;
    				if (valorCampo!=null && !(valorCampo.length==1 && valorCampo[0].equals(""))) {                 		
    					if (valorCampo.length==1) {
    						if (iett.getAreaAre() == null || !iett.getAreaAre().getCodAre().equals(Long.valueOf(valorCampo[0]))){
        						itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
                    		}
    					} else {
    						for (int i=0; i<valorCampo.length;i++) {
		        				if (valorCampo[i]!=null && !"".equals(valorCampo[i])) {
				        			if (diferente && iett.getAreaAre()!= null && !iett.getAreaAre().getCodAre().equals(Long.valueOf(valorCampo[i]))) {
				        				diferente = true;
				        			} else {
				        				diferente = false;
				        			}
				    			}	
		        			}
		        			if (diferente){
		        				itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
		            		}
    					}
                	}
    				
    				valorCampo = Pagina.getParamLista(request, iett.getEstruturaEtt().getCodEtt() + "_subAreaSare");
    				diferente = true;
    				if (valorCampo!=null && !(valorCampo.length==1 && valorCampo[0].equals(""))) {                 		
    					if (valorCampo.length==1 ) {
    						if (iett.getSubAreaSare() == null || !iett.getSubAreaSare().getCodSare().equals(Long.valueOf(valorCampo[0]))){
        						itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
                    		}
    					} else {
    						for (int i=0; i<valorCampo.length;i++) {
		        				if (!"".equals(valorCampo[i])) {
				        			if (diferente && iett.getSubAreaSare()!= null && !iett.getSubAreaSare().getCodSare().equals(Long.valueOf(valorCampo[i]))) {
				        				diferente = true;
				        			} else {
				        				diferente = false;
				        			}
				    			}	
		        			}
		        			if (diferente){
		        				itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
		            		}
    					}
                	}
    				
    				valorCampo = Pagina.getParamLista(request, codEtt + "_unidadeOrcamentariaUO");
    				diferente = true;
    				if (valorCampo!=null && !(valorCampo.length==1 && valorCampo[0].equals(""))) {  
    					if (valorCampo.length==1) {
    						if (iett.getUnidadeOrcamentariaUO().getCodUo() == null || !iett.getUnidadeOrcamentariaUO().getCodUo().equals(Long.valueOf(valorCampo[0]))){
    							itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
                    		}
    					} else {
    						for (int i=0; i<valorCampo.length;i++) {
		        				if (!"".equals(valorCampo[i])) {
				        			if (diferente && iett.getUnidadeOrcamentariaUO() != null && !iett.getUnidadeOrcamentariaUO().getCodUo().equals(Long.valueOf(valorCampo[i]))) {
				        				diferente = true;
				        			} else {
				        				diferente = false;
				        			}
				    			}	
		        			}
		        			if (diferente){
		        				itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
		            		}
    					}
    				}
    				
    				//pega todos os valores dos campos
    				valorCampo = Pagina.getParamLista(request, codEtt + "_orgaoOrgByCodOrgaoResponsavel1Iett");
    				diferente = true;
    				if (valorCampo!=null && !(valorCampo.length==1 && valorCampo[0].equals(""))) {  
    					if (valorCampo.length==1) {
    						if (iett.getOrgaoOrgByCodOrgaoResponsavel1Iett().getCodOrg() == null || !iett.getOrgaoOrgByCodOrgaoResponsavel1Iett().getCodOrg().equals(Long.valueOf(valorCampo[0]))){
    							itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
                    		}
    					} else {
    						for (int i=0; i<valorCampo.length;i++) {
		        				if (!"".equals(valorCampo[i])) {
				        			if (diferente && iett.getOrgaoOrgByCodOrgaoResponsavel1Iett()!= null && !iett.getOrgaoOrgByCodOrgaoResponsavel1Iett().getCodOrg().equals(Long.valueOf(valorCampo[i]))) {
				        				diferente = true;
				        			} else {
				        				diferente = false;
				        			}
				    			}	
		        			}
		        			if (diferente){
		        				itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
		            		}
    					}
    				}

    				
    				valorCampo = Pagina.getParamLista(request, codEtt + "_orgaoOrgByCodOrgaoResponsavel2Iett");
    				diferente = true;
    				if (valorCampo!=null && !(valorCampo.length==1 && valorCampo[0].equals(""))) {  
    					if (valorCampo.length==1) {
    						if (iett.getOrgaoOrgByCodOrgaoResponsavel2Iett().getCodOrg() == null || !iett.getOrgaoOrgByCodOrgaoResponsavel2Iett().getCodOrg().equals(Long.valueOf(valorCampo[0]))){
    							itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
                    		}
    					} else {
    						for (int i=0; i<valorCampo.length;i++) {
		        				if (!"".equals(valorCampo[i])) {
				        			if (diferente && iett.getOrgaoOrgByCodOrgaoResponsavel2Iett()!= null && !iett.getOrgaoOrgByCodOrgaoResponsavel2Iett().getCodOrg().equals(Long.valueOf(valorCampo[i]))) {
				        				diferente = true;
				        			} else {
				        				diferente = false;
				        			}
				    			}	
		        			}
		        			if (diferente){
		        				itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
		            		}
    					}
    				}

    				
    				if (filtrarItemString(request, "descricaoR1", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
    				if (filtrarItemString(request, "descricaoR2", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
    				if (filtrarItemString(request, "descricaoR3", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
    				if (filtrarItemString(request, "descricaoR4", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
    				if (filtrarItemString(request, "descricaoR5", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
    				
    				valorCampo = Pagina.getParamLista(request, codEtt + "_periodicidadePrdc");
    				diferente = true;
    				if (valorCampo!=null && !(valorCampo.length==1 && valorCampo[0].equals(""))) {  
    					if (valorCampo.length==1) {
    						if (iett.getPeriodicidadePrdc() == null || 
    									!iett.getPeriodicidadePrdc().getCodPrdc().equals(Long.valueOf(valorCampo[0]))){
    							itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
                    		}
    					} else {
    						for (int i=0; i<valorCampo.length;i++) {
		        				if (!"".equals(valorCampo[i])) {
				        			if (diferente && iett.getPeriodicidadePrdc()!= null && !iett.getPeriodicidadePrdc().getCodPrdc().equals(Long.valueOf(valorCampo[i]))) {
				        				diferente = true;
				        			} else {
				        				diferente = false;
				        			}
				    			}	
		        			}
		        			if (diferente){
		        				itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
		            		}
    					}
    				}

    				
    				if (filtrarItemString(request, "indCriticaIett", iett, itensRemovidos, itItensEstrutura)){
    					continue;
    				}
    				if (filtrarItemValor(request, "valPrevistoFuturoIett", iett, itensRemovidos, itItensEstrutura, "Inicio")){
    					continue;
    				}
    				if (filtrarItemValor(request, "valPrevistoFuturoIett", iett, itensRemovidos, itItensEstrutura, "Fim")){
    					continue;
    				}
   					if (filtrarItemData(request, "dataR1", iett, itensRemovidos, itItensEstrutura, "Inicio")){
    					continue;
    				}  				   				
    				if (filtrarItemData(request, "dataR1", iett, itensRemovidos, itItensEstrutura, "Fim")){
    					continue;
    				}
    				if (filtrarItemData(request, "dataR2", iett, itensRemovidos, itItensEstrutura, "Inicio")){
    					continue;
    				}  				   				
    				if (filtrarItemData(request, "dataR2", iett, itensRemovidos, itItensEstrutura, "Fim")){
    					continue;
    				}
    				if (filtrarItemData(request, "dataR3", iett, itensRemovidos, itItensEstrutura, "Inicio")){
    					continue;
    				}  				   				
    				if (filtrarItemData(request, "dataR3", iett, itensRemovidos, itItensEstrutura, "Fim")){
    					continue;
    				}
    				if (filtrarItemData(request, "dataR4", iett, itensRemovidos, itItensEstrutura, "Inicio")){
    					continue;
    				}  				   				
    				if (filtrarItemData(request, "dataR4", iett, itensRemovidos, itItensEstrutura, "Fim")){
    					continue;
    				}
    				if (filtrarItemData(request, "dataR5", iett, itensRemovidos, itItensEstrutura, "Inicio")){
    					continue;
    				}  				   				
    				if (filtrarItemData(request, "dataR5", iett, itensRemovidos, itItensEstrutura, "Fim")){
    					continue;
    				}
    				
    				//Filtro para situa��o do item
    				valorCampo = Pagina.getParamLista(request, codEtt + "_situacaoSit");
    				diferente = true;
    				if (valorCampo!=null && !(valorCampo.length==1 && valorCampo[0].equals(""))) {                 		
    					if (valorCampo.length==1) {
    						if (iett.getSituacaoSit() == null || !iett.getSituacaoSit().getCodSit().equals(Long.valueOf(valorCampo[0]))){
    							itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
                    		}
    					} else {
    						for (int i=0; i<valorCampo.length;i++) {
		        				if (!"".equals(valorCampo[i])) {
				        			if (diferente && iett.getSituacaoSit()!= null && !iett.getSituacaoSit().getCodSit().equals(Long.valueOf(valorCampo[i]))) {
				        				diferente = true;
				        			} else {
				        				diferente = false;
				        			}
				    			}	
		        			}
		        			if (diferente){
		        				itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
		            		}
    					}
                	}
    				
    				//Filtro para n�vel de planejamento
       				ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
       				String campoNivelPlanejamento = codEtt + "_a" + configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getCodSga().toString();        
       				valorCampo = Pagina.getParamLista(request, campoNivelPlanejamento);
    				diferente = true;
    				if (valorCampo!=null && !(valorCampo.length==1 && valorCampo[0].equals(""))) {                 		
    					if (valorCampo.length==1) {
    						if (iett.getItemEstruturaNivelIettns()== null || !iett.getItemEstruturaNivelIettns().contains(this.buscar(SisAtributoSatb.class, Long.valueOf(valorCampo[0])))){
    							itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
                    		}
    					} else {
    						for (int i=0; i<valorCampo.length;i++) {
		        				if (!"".equals(valorCampo[i])) {
				        			if (diferente && iett.getItemEstruturaNivelIettns()!= null && !iett.getItemEstruturaNivelIettns().contains(this.buscar(SisAtributoSatb.class, Long.valueOf(valorCampo[i])))) {
				        				diferente = true;
				        			} else {
				        				diferente = false;
				        			}
				    			}	
		        			}
		        			if (diferente){
		        				itensRemovidos.add(iett.getCodIett());
                    			itItensEstrutura.remove();
                    			continue;
		            		}
    					}
                	}

    				//Chama o m�todo respons�vel por efetuar os filtros dos atributos livres
    				if (filtrarAtributosLivres(request, iett, itensRemovidos, itItensEstrutura, atributosLivres)){
    					continue;
    				}
    			}
    		}
    		
    	}
    	return itensEstrutura;
    }
    
    /**
     * Retorna lista de itens que tenham um Acompanhamento (AcompReferenciaItemAri) em algum dos Per�odos de Refer�ncia 
     * (AcompReferenciaAref) passados como par�metro
     * @param periodosConsiderados Collection de AcompReferenciaAref
     * @param niveisPlanejamento Collection
     * @param orgaoResponsavel OrgaoOrg
     * @param usuarioUsu UsuarioUsu
     * @param gruposUsuario Set
     * @param codTipoAcompanhamento Long (se for nulo ignora o tipo de acompanhamento)
     * @param codIettPai Long (se for diferente de nulo, obt�m os filhos desse item)
     * @param itensSemInformacaoNivelPlanejamento 
     * @param codCor
     * @param nuPaginaSelecionada
     * @param menorNivel
     * @param indLiberados
     * @return List de AcompReferenciaItemAri. lista vazia se n�o foi informado nenhum periodo
     * @throws ECARException
     */
    @SuppressWarnings("static-access")
    public Object[] getItensAcompanhamentoInPeriodosByOrgaoRespPaginado(Collection periodosConsiderados,
    		Collection niveisPlanejamento, OrgaoOrg orgaoResponsavel, UsuarioUsu usuarioUsu,
    		Set gruposUsuario, Long codTipoAcompanhamento, Long codIettPai, 
    		Boolean itensSemInformacaoNivelPlanejamento, Long codCor, String indLiberados, 
    		int menorNivel, int nuPaginaSelecionada, List etiquetas, boolean minhaVisao) 
    			throws ECARException {
    	//Chamado pela area de Monitoramento
    	return this.getItensAcompanhamentoInPeriodosByOrgaoRespPaginadoConsiderarPermissao(periodosConsiderados,
        		niveisPlanejamento, orgaoResponsavel, usuarioUsu, gruposUsuario, codTipoAcompanhamento, codIettPai, 
        		itensSemInformacaoNivelPlanejamento, codCor, indLiberados, menorNivel, nuPaginaSelecionada,true, etiquetas, minhaVisao);
    }
    
    
    
    /**
     * Retorna lista de itens que tenham um Acompanhamento (AcompReferenciaItemAri) em algum dos Per�odos de Refer�ncia 
     * (AcompReferenciaAref) passados como par�metro
     * @param periodosConsiderados Collection de AcompReferenciaAref
     * @param niveisPlanejamento Collection
     * @param orgaoResponsavel OrgaoOrg
     * @param usuarioUsu UsuarioUsu
     * @param gruposUsuario Set
     * @param codTipoAcompanhamento Long (se for nulo ignora o tipo de acompanhamento)
     * @param codIettPai Long (se for diferente de nulo, obt�m os filhos desse item)
     * @param itensSemInformacaoNivelPlanejamento 
     * @param codCor
     * @param nuPaginaSelecionada
     * @param menorNivel
     * @param indLiberados
     * @return List de AcompReferenciaItemAri. lista vazia se n�o foi informado nenhum periodo
     * @throws ECARException
     */
    @SuppressWarnings("static-access")
    public Object[] getItensAcompanhamentoInPeriodosByOrgaoRespPaginadoConsiderarPermissao(Collection periodosConsiderados,
    		Collection niveisPlanejamento, OrgaoOrg orgaoResponsavel, UsuarioUsu usuarioUsu,
    		Set gruposUsuario, Long codTipoAcompanhamento, Long codIettPai, 
    		Boolean itensSemInformacaoNivelPlanejamento, Long codCor, String indLiberados, 
    		int menorNivel, int nuPaginaSelecionada, boolean montarHierarquiaVisualizacao, List etiquetas, boolean minhaVisao) 
    			throws ECARException {
        try{        
        	ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
            TipoAcompanhamentoDao tipoAcompanhamentoDao = new TipoAcompanhamentoDao(null);
            TipoAcompanhamentoTa tipoAcomp = null;
            if(codTipoAcompanhamento != null) {
            	tipoAcomp = (TipoAcompanhamentoTa) tipoAcompanhamentoDao.buscar(TipoAcompanhamentoTa.class, codTipoAcompanhamento);
            }
            List<ItemEstruturaIettMin> iettFilhos = new ArrayList<ItemEstruturaIettMin>();
                        
            if(periodosConsiderados.size() > 0){
            	int retornarAteNivel = -1;
            	
            	List<ItemEstruturaIett> listaItens = new ArrayList<ItemEstruturaIett>();
            	boolean filtrarPorOrg = true;
            	if(orgaoResponsavel == null) {
            		filtrarPorOrg = false;
            	}
            	
            	List listaAris = getItensAcompanhamento(periodosConsiderados,niveisPlanejamento,Arrays.asList(orgaoResponsavel),
            			usuarioUsu,
                		gruposUsuario,codTipoAcompanhamento,codIettPai,itensSemInformacaoNivelPlanejamento,codCor,indLiberados,
                		menorNivel,nuPaginaSelecionada,montarHierarquiaVisualizacao,
                		filtrarPorOrg,
                		retornarAteNivel,
                		itemDao,
                		tipoAcomp,
                		iettFilhos,
                		true,
                		"", etiquetas, minhaVisao
                		);
               
            	Iterator itListaAris = listaAris.iterator();
            	
            	if (montarHierarquiaVisualizacao) {
	            	//Lista de itens(itensGeralComArvore) montada a partir da lista de itens consultada(listaItens) mais os itens necess�rios para monta a hierarquia de visualiza��o.
	                List<ItemEstruturaIett> itensGeralComArvore = itemDao.getArvoreItensFromAris(listaAris, null);
	                List<ItemEstruturaIett> arvoreItens = new ArrayList<ItemEstruturaIett>(itensGeralComArvore);
	                
	                if(retornarAteNivel != -1){
	                	for (Iterator itArvore = arvoreItens.iterator(); itArvore.hasNext();) {
							ItemEstruturaIett iett = (ItemEstruturaIett) itArvore.next();
							ItemEstruturaIettMin iettMin = new ItemEstruturaIettMin();
							iettMin.setCodIett(iett.getCodIett());
							if(iett.getNivelIett().intValue() > retornarAteNivel){
		                		itArvore.remove();
		                	} else if(!iettFilhos.contains(iettMin)){
		                		itArvore.remove();
		                	}
	                    	
		                }
	                }
	
	                //se � para paginar 
	                if(menorNivel != -2) {
		                // indices inicial e final para a consulta dos itensEstruturaIett
	                	ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
		                int nuItensPaginacao = configuracaoDao.getConfiguracao().getNuItensExibidosPaginacao();
		            	List itensOrdenados =  itemDao.getItensOrdenados(arvoreItens, tipoAcomp);
		                if(itensOrdenados.size() <= nuItensPaginacao*nuPaginaSelecionada) {
		            		nuPaginaSelecionada = 1;
		            	}
		            	int indiceInicial = (nuPaginaSelecionada-1)*nuItensPaginacao + 1;
		            	int indiceFinal = (nuPaginaSelecionada-1)*nuItensPaginacao + nuItensPaginacao;
		            	int contador = 1; // variavel que guarda qual o indice no iterador
		            	
		            	List itensOrdenadosPaginados = new ArrayList();
		            	List itensOrdenadosTotalPorNivel = new ArrayList();
		            	Iterator itensOrdenadosIt = itensOrdenados.iterator();
		            	while(itensOrdenadosIt.hasNext()) {
		            		AtributoEstruturaListagemItens atEstListagem = (AtributoEstruturaListagemItens)itensOrdenadosIt.next();
		            		if(indiceInicial <= contador && contador <= indiceFinal) {
		            			itensOrdenadosPaginados.add(atEstListagem);
		            		}
		            		if(menorNivel == -1 || atEstListagem.getItem().getNivelIett().intValue() <= menorNivel) {
		            			itensOrdenadosTotalPorNivel.add(atEstListagem);
		            			contador++;
		            		}
		            	}
		            	
		            	Object[] _object = new Object[]{itensOrdenadosPaginados, itensGeralComArvore, itensOrdenadosTotalPorNivel};
		            	
		            	return _object;
	                } else {
	                	
                		Object[] _object = new Object[]{itemDao.getItensOrdenados(arvoreItens, tipoAcomp), itensGeralComArvore};
                		
                		return _object; 
	                }
            	} else {
            		Object[] _object = {itemDao.getItensOrdenados(listaItens, tipoAcomp,false)};
            		
            		return _object;            		
            	}
            } else {
                return new Object[]{new ArrayList(), new ArrayList()};
            }

        } catch(HibernateException e){
        	this.logger.error(e);
            throw new ECARException(e);            
        }        
    }
    
    
    
        
    /**
     * Recebe uma cole��o de AcomReferenciaAref e um item e retorna um map tendo como chaves os Aref da cole��o e como dado
     * o AcompReferenciaItemAri correspondente ao item no respectivo periodo.
     * @param periodosConsiderados
     * @param item
     * @return
     * @throws ECARException
     */
    public Map criarMapPeriodoAri(Collection periodosConsiderados, ItemEstruturaIett item) throws ECARException{
  
        try{
            StringBuilder query = new StringBuilder("");
            
            if(periodosConsiderados != null) {
            	query = new StringBuilder("select ari from AcompReferenciaItemAri ari ")
            				.append("where ari.itemEstruturaIett = ? and ari.acompReferenciaAref.codAref in (:listaAcompanhamentos)");
            }
            else {
                query = new StringBuilder("select ari from AcompReferenciaItemAri ari ")
                			.append("where ari.itemEstruturaIett = ?");
            }

            Query queryAri = this.getSession().createQuery(query.toString());   
            
            queryAri.setLong(0, item.getCodIett().longValue());

            if(periodosConsiderados != null) {
	            List listaCodigosAref = new ArrayList();
	            
	            for (Iterator it = periodosConsiderados.iterator(); it
						.hasNext();) {
	                AcompReferenciaAref aReferencia = (AcompReferenciaAref)it.next();
	                listaCodigosAref.add(aReferencia.getCodAref());                
	            }
	            
	            queryAri.setParameterList("listaAcompanhamentos", listaCodigosAref);
            }
            
            List listaAris = queryAri.list();
            
            Map map = new HashMap();
            
            for (Iterator itAris = listaAris.iterator(); itAris.hasNext();) {
                AcompReferenciaItemAri ari =  (AcompReferenciaItemAri) itAris.next();
                map.put(ari.getAcompReferenciaAref(), ari);
            }  
            
            return map;
            
        } catch(HibernateException e){
			this.logger.error(e);
            throw new ECARException(e);              
        }
        
    }
    
    /**
     * Método criado para otimizar a listagens em relaçãoItens.jsp, faz a busca somente uma vez 
     * @param periodosConsiderados
     * @param item
     * @return
     * @throws ECARException
     */
    public Map criarMapPeriodoAri(Collection periodosConsiderados,List<Long> listaCodigosIett) throws ECARException{
        try{
        	//List<Long> listaCodigosIett = new ArrayList<Long>();
            StringBuilder query = new StringBuilder("");
            
            if(periodosConsiderados != null) {
            	query = new StringBuilder("select ari from AcompReferenciaItemAri ari ")
            				.append("where ari.itemEstruturaIett.codIett in (:listaCodigosIett)")
            				.append(" and ari.acompReferenciaAref.codAref in (:listaAcompanhamentos)");
            }
            else {
                query = new StringBuilder("select ari from AcompReferenciaItemAri ari ")
                			.append("where ari.itemEstruturaIett = ?");
            }

            Query queryAri = this.getSession().createQuery(query.toString());   
            
            //queryAri.setLong(0, item.getCodIett().longValue());
            queryAri.setParameterList("listaCodigosIett", listaCodigosIett);

            if(periodosConsiderados != null) {
	            List listaCodigosAref = new ArrayList();
	            
	            for (Iterator it = periodosConsiderados.iterator(); it
						.hasNext();) {
	                AcompReferenciaAref aReferencia = (AcompReferenciaAref)it.next();
	                listaCodigosAref.add(aReferencia.getCodAref());                
	            }
	            
	            queryAri.setParameterList("listaAcompanhamentos", listaCodigosAref);
            }
            
            List listaAris = queryAri.list();
            
            Map map = new HashMap();
            
            for (Iterator itAris = listaAris.iterator(); itAris.hasNext();) {
                AcompReferenciaItemAri ari =  (AcompReferenciaItemAri) itAris.next();
                map.put(ari.getAcompReferenciaAref().toString()+ari.getItemEstruturaIett(), ari);
            }  
            
            return map;
            
        } catch(HibernateException e){
			this.logger.error(e);
            throw new ECARException(e);              
        }
    }
	
	/**
	 * Cria a matriz utilizada para a c�lculo da proje��o. Aprensenta como chave o numero sequencial do m�s 
	 * (1 - primeiro m�s do per�odo, 2 - segundo m�s) e como valor a quantidade realizada para o indicador
	 * no respectivo m�s. 
	 * @param indicador
	 * @param anoInicioProjecao
	 * @param anoFimProjecao
	 * @param mesInicioProjecao
	 * @param mesFimProjecao
	 * @return
	 * @throws ECARException
	 */
	public Map criaMapCalculoProjecao(ItemEstrtIndResulIettr indicador, int anoInicioProjecao, int anoFimProjecao, int mesInicioProjecao, int mesFimProjecao) throws ECARException{
		
		// Contru��o da "Matriz"
		List meses = new ArrayList();					
		while((anoInicioProjecao != anoFimProjecao) || (mesInicioProjecao != mesFimProjecao && anoInicioProjecao == anoFimProjecao)){
			meses.add(mesInicioProjecao + "-" + anoInicioProjecao);
			mesInicioProjecao++;
			if(mesInicioProjecao == 13){
				mesInicioProjecao = 1;
				anoInicioProjecao++;
			}			
		}
		meses.add(mesInicioProjecao + "-" + anoInicioProjecao);
		
		Map qtdeRealizada = new HashMap();
		double valorAnterior = 0;
		int i = 1;
		Iterator it = meses.iterator();
		while(it.hasNext()){
			String strMesAno = (String) it.next();
			String strMes = strMesAno.split("-")[0];
			if(strMes.length() == 1)
				strMes = "0" + strMes;
			String strAno = strMesAno.split("-")[1];			
			Double valor = new AcompRealFisicoDao(request).getQtdRealizadaMesAno(indicador, Long.valueOf(strMes), Long.valueOf(strAno));
			if(valor != null && valor.doubleValue() != 0){
				if("S".equals(indicador.getIndAcumulavelIettr())){
					valorAnterior = valorAnterior + valor.doubleValue();
					qtdeRealizada.put(Integer.valueOf(i), new Double(valorAnterior));
					i++;
				} else {
					valorAnterior = valor.doubleValue();
					qtdeRealizada.put(Integer.valueOf(i), new Double(valorAnterior));
					i++;
				}					
			} else {
				qtdeRealizada.put(Integer.valueOf(i), new Double(valorAnterior));
				i++;
			}			
		}
		 
		return qtdeRealizada;
		
	}
	
	/**
	 * Retorna um array com: m�s e ano de in�cio da proje��o (data de in�cio do projeto ou primeira data de 
	 * registro de informa��o de quantidade realizada para o indicador de resultado - a menor das duas datas) e
	 * m�s e ano limites da matriz (�ltimo m�s ano com registro de valor realizado informado para o indicador).
	 * Estes meses ser�o os limites da matriz para c�lculo de proje��es.
	 * @param indicador
	 * @param comQtde
	 * @param soPrevisao
	 * @return
	 * @throws ECARException
	 */
	public int[] getMesAnoInicioFimMatrizProjecao(ItemEstrtIndResulIettr indicador, boolean comQtde, boolean soPrevisao) throws ECARException{
		try{
			
			Collection acompanhamentosReferencia = this.getSession().createFilter(indicador.getAcompRealFisicoArfs(), 
				" WHERE this.qtdRealizadaArf >= 0 OR this.qtdRealizadaArf IS NULL ORDER BY this.anoArf asc, this.mesArf asc").list();
			
			Object[] acompanhamentos = acompanhamentosReferencia.toArray();
			
			int mesInicioProjecao;
			int anoInicioProjecao;
			
			Date dataInicioProjeto = indicador.getItemEstruturaIett().getDataInicioIett();
			
			//int mesQuantidadeInformada = Integer.valueOf(((AcompRealFisicoArf)acompanhamentos[0]).getMesArf().toString()).intValue();
			//int anoQuantidadeInformada = Integer.valueOf(((AcompRealFisicoArf)acompanhamentos[0]).getAnoArf().toString()).intValue();
			
			int mesQuantidadeInformada = 0;
			int anoQuantidadeInformada = 0;

			if(!soPrevisao){
				mesQuantidadeInformada = Integer.valueOf(((AcompRealFisicoArf)acompanhamentos[0]).getMesArf().toString()).intValue();
				anoQuantidadeInformada = Integer.valueOf(((AcompRealFisicoArf)acompanhamentos[0]).getAnoArf().toString()).intValue();
			}
			
			if(dataInicioProjeto != null){

				int mesInicioProjeto = Data.getCalendar(dataInicioProjeto).get(Calendar.MONTH) + 1;
				int anoInicioProjeto = Data.getCalendar(dataInicioProjeto).get(Calendar.YEAR);
												
				/* Definir qual o m�s e ano inicial devem ser considerados */
				if(anoInicioProjeto > anoQuantidadeInformada){
					mesInicioProjecao = mesQuantidadeInformada;
					anoInicioProjecao = anoQuantidadeInformada;
				} else {
					if(mesInicioProjeto >= mesQuantidadeInformada){
						mesInicioProjecao = mesQuantidadeInformada;
						anoInicioProjecao = anoQuantidadeInformada;						
					} else {
						mesInicioProjecao = mesInicioProjeto;
						anoInicioProjecao = anoInicioProjeto;											
					}
				}		
				
			} else {
				mesInicioProjecao = mesQuantidadeInformada;
				anoInicioProjecao = anoQuantidadeInformada;
			}
			

			// Descobrir m�s e ano que devem ser representados pelo ultimo elemento da matriz
			int mesFimMatriz = 0;
			int anoFimMatriz = 0;		
			if(!soPrevisao){
				mesFimMatriz = Integer.valueOf(((AcompRealFisicoArf)acompanhamentos[acompanhamentos.length - 1]).getMesArf().toString()).intValue();
				anoFimMatriz = Integer.valueOf(((AcompRealFisicoArf)acompanhamentos[acompanhamentos.length - 1]).getAnoArf().toString()).intValue();		
			}
			
			int[] resultado = new int[4];
			resultado[0] = mesInicioProjecao;
			resultado[1] = anoInicioProjecao;
			resultado[2] = mesFimMatriz;
			resultado[3] = anoFimMatriz;
			
			return resultado;
			
		}catch(HibernateException e){
			throw new ECARException(e);
		}
	}
	
	/**
	 * Retorna o m�s e o ano final da proje��o: �ltimo exerc�cio com quantidade informada ou para a data de t�rmino do 
	 * projeto correspondente ao indicador. A menor das duas datas.
	 * @param indicador
	 * @return
	 * @throws ECARException
	 */
	public int[] getMesAnoFimProjecao(ItemEstrtIndResulIettr indicador) throws ECARException{
		try{
			Collection fisicoPrevisto = this.getSession().createFilter(indicador.getItemEstrutFisicoIettfs(), 
			" where this.qtdPrevistaIettf > 0 order by this.anoIettf, this.mesIettf desc").list();
			
			Object[] previstos = fisicoPrevisto.toArray();
			
			int mesFimProjecao;
			int anoFimProjecao;
			
			ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(this.request);
			Date dataFimProjeto = itemEstruturaDao.ObtemDataTerminoItemEstrutura(indicador.getItemEstruturaIett());
						
			// Mantis 0010128 - Qtd prevista n�o � mais informado por exerc�cio
						
			//Date dataFimExercicio = ((ItemEstrutFisicoIettf)previstos[0]).getExercicioExe().getDataFinalExe();
			//int mesQuantidadeInformadaTermino = Data.getCalendar(dataFimExercicio).get(Calendar.MONTH) + 1;
			//int anoQuantidadeInformadaTermino = Data.getCalendar(dataFimExercicio).get(Calendar.YEAR);	
			
			int mesQuantidadeInformadaTermino = 0;
			int anoQuantidadeInformadaTermino = 0;
			
			if(previstos.length > 0){
				mesQuantidadeInformadaTermino = ((ItemEstrutFisicoIettf)previstos[0]).getMesIettf();
				anoQuantidadeInformadaTermino = ((ItemEstrutFisicoIettf)previstos[0]).getAnoIettf();
			}
			
			if(dataFimProjeto != null && previstos.length > 0){
				int mesFimProjeto = Data.getCalendar(dataFimProjeto).get(Calendar.MONTH) + 1;
				int anoFimProjeto = Data.getCalendar(dataFimProjeto).get(Calendar.YEAR);	
				/* Definir qual o m�s e ano final devem ser considerados na proje��o */
				if((anoFimProjeto >  anoQuantidadeInformadaTermino)||
				   (mesFimProjeto >= mesQuantidadeInformadaTermino)){

					mesFimProjecao = mesQuantidadeInformadaTermino;
					anoFimProjecao = anoQuantidadeInformadaTermino;
				} else {
					mesFimProjecao = mesFimProjeto;
					anoFimProjecao = anoFimProjeto;											
				}

			} else {
				mesFimProjecao = mesQuantidadeInformadaTermino;
				anoFimProjecao = anoQuantidadeInformadaTermino;
			}
			
			
			int[] resultado = new int[2];
			resultado[0] = mesFimProjecao;
			resultado[1] = anoFimProjecao;
			
			return resultado;
			
		}catch(HibernateException e){
			throw new ECARException(e);
		}
	}
			
	/**
	 * Faz a Proje��o de um Indicador. Devolve a data de t�rmino projetada para o projeto
	 * @param indicador
	 * @param ari
	 * @return
	 * @throws ECARException
	 */
	public GregorianCalendar getProjecaoDataTermino(ItemEstrtIndResulIettr indicador, AcompReferenciaItemAri ari, double previsto) throws ECARException{
		try{
			GregorianCalendar start = this.getDateInicioRealizado(indicador);
			List<Double> values = getRealizado(indicador, ari);
			Projection projection = FactoryCalculoProjecao.getProjection(values, start);
			return projection.getDate(previsto);
		} catch(Exception e){
			this.logger.error(e);
			return null;
		}
	}
			
	/**
	 * Faz a Proje��o de um Indicador. Devolve o valor projetada para a data de fim do projeto ou �ltimo mes do maior
	 * exerc�cio com quantidade prevista ( a menor das duas datas)
	 * @param indicador
	 * @param ari
	 * @return
	 * @throws ECARException
	 */
	public double calculoProjecao(ItemEstrtIndResulIettr indicador, AcompReferenciaItemAri ari) throws ECARException{
		
		try{
			GregorianCalendar start = this.getDateInicioRealizado(indicador);
			GregorianCalendar end   = this.getDateFimProjecao(indicador);
			List<Double> values = getRealizado(indicador, ari);
			Projection projection = FactoryCalculoProjecao.getProjection(values, start);
			
			return projection.getValue(end);
			//return auxCalculoProjecao(indicador,ari);
		} catch(Exception e){
			this.logger.error(e);
			return 0;
		}
		
		
	}	
	
	private GregorianCalendar getDateInicioRealizado(ItemEstrtIndResulIettr indicador) throws ECARException{
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(this.request);		
		Date date = itemEstruturaDao.ObtemDataInicialItemEstrutura(indicador.getItemEstruturaIett());		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar;
	}
	private GregorianCalendar getDateFimProjecao(ItemEstrtIndResulIettr indicador){
		try{
			int[] mesAnoFimProjecao = getMesAnoFimProjecao(indicador);
			int mesFimProjecao = mesAnoFimProjecao[0];
			int anoFimProjecao = mesAnoFimProjecao[1];
			return new GregorianCalendar(anoFimProjecao,mesFimProjecao-1,1);
		}catch(Exception e){
			this.logger.error(e);
			return null;
		}
		
	}
	
		private List<Double> getRealizado(ItemEstrtIndResulIettr indicador, AcompReferenciaItemAri ari){
		try{
			AcompRealFisicoDao arfDao = new AcompRealFisicoDao(null);
			ArrayList<Double> sRealizado = new ArrayList<Double>();
		
			int[] meses = getMesAnoInicioFimMatrizProjecao(indicador, true, false);
			int mesInicioProjecao = meses[0];
			int anoInicioProjecao = meses[1];
			int mesFimRealizado = Integer.valueOf(ari.getAcompReferenciaAref().getMesAref()).intValue(); 
			int anoFimRealizado = Integer.valueOf(ari.getAcompReferenciaAref().getAnoAref()).intValue();
			
			int[] mesAnoFimProjecao = getMesAnoFimProjecao(indicador);
			
			int auxMes = mesInicioProjecao;
			int auxAno = anoInicioProjecao;

			double qtdeAnterior = 0;
			while(auxAno < anoFimRealizado || (auxAno == anoFimRealizado && auxMes <= mesFimRealizado)){
				Double qtde = arfDao.getQtdRealizadaMesAno(indicador, (long)auxMes, (long)auxAno);
				double qtdeSerie = 0;
				
				if (indicador.getIndiceMaisRecenteIettr()!= null && qtdeAnterior==0) {
					qtdeAnterior = indicador.getIndiceMaisRecenteIettr().doubleValue();
				}
				
				if(qtde != null){
					qtdeSerie = qtdeAnterior + qtde.doubleValue();
					qtdeAnterior = qtdeSerie;
				} else 
					qtdeSerie = qtdeAnterior;

				sRealizado.add(new Double(qtdeSerie));
				
				auxMes++;
				if(auxMes == 13){
					auxMes = 1;
					auxAno++;
				}
			}
			return sRealizado;
			
		} catch(Exception e){
			this.logger.error(e);
			return null;
		}
	}
	
	/**
	 * Faz a previs�o do realizado de um m�s para um indicador de resultado
	 * @param meses Lista com todos os meses que se conhece o realizado
	 * @param realizado Lista do realizado em cada m�s
	 * @param mesPrevisao M�s que deseja prevar
	 * @return
	 */
	public double calcularPrevistoMes(Collection meses, Collection realizado, int mesPrevisao){
				
		double mediaRealizado = Util.calculaMediaValores(realizado);
		double mediaMeses = Util.calculaMediaValoresInteger(meses);
		double A = calcularConstanteCalculoPrevisao(meses, realizado, mediaRealizado, mediaMeses);
		double B = calcularIncrementoMedioEixoY(meses.toArray(), realizado.toArray(), mediaRealizado, mediaMeses);
		return A + (B * mesPrevisao);
		
	}

	private double calcularConstanteCalculoPrevisao(Collection meses, Collection realizado, double mediaRealizado, double mediaMeses) {		
		double B = calcularIncrementoMedioEixoY(meses.toArray(), realizado.toArray(), mediaRealizado, mediaMeses); 
		return mediaRealizado - (B * mediaMeses);
	}

	private double calcularIncrementoMedioEixoY(Object[] meses, Object[] realizado, double mediaRealizado, double mediaMeses) {
		Arrays.sort(meses);
		Arrays.sort(realizado);
		double soma1 = 0;
		double soma2 = 0;
		int i = 0;
		int qtdeMeses = meses.length;
		while(i<qtdeMeses){
			soma1 = soma1 + (((Integer)meses[i]).intValue() - mediaMeses) * (((Double)realizado[i]).doubleValue() - mediaRealizado);
			soma2 = soma2 + Math.pow( (((Integer)meses[i]).intValue() - mediaMeses),2);
			i++;
		}		
		if(soma2 == 0)
			return 0;
		return soma1 / soma2;
	}
    
	/**
	 * A partir de um AcompReferenciaItem inclu�do na lista verifica a exist�ncia de
	 * 		Acompanhamentos de Itens filhos do Ari passado, que s�o inclu�dos juntamente
	 * 		na lista.
	 * H� ainda a possibilidade dos filhos serem filtrados pelo orgaoResponsavel.
	 * 		
	 * @param acompRefItem
	 * @param orgaoResponsavel
	 * @return
	 * @throws ECARException
	 * 
	 */
	public List getAcompReferenciaItemFilhosByAri (AcompReferenciaItemAri acompRefItem,
					OrgaoOrg orgaoResponsavel) throws ECARException{
		List lista = new ArrayList();
		
		lista.add(acompRefItem);
		
		try{
			ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
			
			List descendentes = itemDao.getDescendentes(acompRefItem.getItemEstruturaIett(), true);
			
			if(descendentes != null && descendentes.size() > 0){
				List codIettDescendentes = new ArrayList();
				
				for (Iterator itDescendentes = descendentes.iterator(); itDescendentes
						.hasNext();) {
					ItemEstruturaIett iett = (ItemEstruturaIett) itDescendentes.next();
					codIettDescendentes.add(iett.getCodIett());
				}
				
				StringBuilder query = new StringBuilder("select ari from AcompReferenciaItemAri ari")
									.append(" where ari.acompReferenciaAref = ? and ari.itemEstruturaIett.codIett in (:listaItens)")
									.append(" and ari.itemEstruturaIett.indAtivoIett = 'S'");
				
				if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
                	query.append(" and ( ari.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg = :orgaoResp ")
                	     .append("or ( ari.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg is null ")
                	     .append("and ari.itemEstruturaIett.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg = :orgaoResp ) )");
                }

				Query queryAri = this.getSession().createQuery(query.toString());   
	        	queryAri.setParameterList("listaItens", codIettDescendentes);
		        queryAri.setLong(0, acompRefItem.getAcompReferenciaAref().getCodAref().longValue());
		        
		        if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
		        	queryAri.setLong("orgaoResp", orgaoResponsavel.getCodOrg().longValue());
		        }

		        lista.addAll(queryAri.list());


		        List listaTemp = new ArrayList();
		        List listaAri = new ArrayList();
		        for (Iterator it = lista.iterator(); it.hasNext();) {
		        	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) it.next();
		        	
		        	if(!listaTemp.contains(ari.getItemEstruturaIett().getCodIett())){
		        		listaTemp.add(ari.getItemEstruturaIett().getCodIett());
		        		listaAri.add(ari);
		        	}
		        }

		        lista.clear();
		        lista.addAll(listaAri);
		        
		        Collections.sort(lista,
			            new Comparator() {
		        			ItemEstruturaDao iettDaoSort = new ItemEstruturaDao(null);
			        		public int compare(Object o1, Object o2) {
		        		    	AcompReferenciaItemAri ari1 = (AcompReferenciaItemAri) o1;
		        		    	AcompReferenciaItemAri ari2 = (AcompReferenciaItemAri) o2;
		        		    	
			        		    ItemEstruturaIett item1 = ari1.getItemEstruturaIett();
			        		    ItemEstruturaIett item2 = ari2.getItemEstruturaIett();							
					        	if(iettDaoSort.getAscendenteMaximo(item1).equals(iettDaoSort.getAscendenteMaximo(item2))){												        			
					        		if(item1.getNivelIett().intValue() == item2.getNivelIett().intValue()){										
					        				return item1.getNomeIett().compareToIgnoreCase(item2.getNomeIett());	
					        		} else{										
					        				return item1.getNivelIett().intValue() - item2.getNivelIett().intValue(); 
					        		}				        	    	    
					        	} else {
					        	    if(item1.getNivelIett().intValue() == item2.getNivelIett().intValue()){									
				        	    	    return item1.getNomeIett().compareToIgnoreCase(item2.getNomeIett());
				        	    	} else {									
				        	    	    return iettDaoSort.getAscendenteMaximo(item1).getNomeIett().compareToIgnoreCase(iettDaoSort.getAscendenteMaximo(item2).getNomeIett());
				        	    	}				        	    
					        	}   		            	        		        
			        		}
			   	}
				);
			}
		}catch (HibernateException e){
			this.logger.error(e);
			throw new ECARException(); 
		}
		
		return lista;
	}

	/**
	 * A partir de um AcompReferenciaItem inclu�do na lista verifica a exist�ncia de
	 * 		Acompanhamentos de Itens filhos do Ari passado com pontos cr�ticos ativos,
	 * 		que s�o inclu�dos juntamente
	 * 		na lista.
	 * H� ainda a possibilidade dos filhos serem filtrados pelo orgaoResponsavel.
	 * 		
	 * @param acompRefItem
	 * @return
	 * @throws ECARException
	 * 
	 */
	public List getAcompReferenciaItemFilhosByAriPorPtoCritico (AcompReferenciaItemAri acompRefItem) throws ECARException{
		List lista = new ArrayList();
		
		lista.add(acompRefItem);
		
		try{
			ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
			
			List descendentes = itemDao.getDescendentes(acompRefItem.getItemEstruturaIett(), true);
			
			if(descendentes != null && descendentes.size() > 0){
				List codIettDescendentes = new ArrayList();
				
				for (Iterator itDescendentes = descendentes.iterator(); itDescendentes
						.hasNext();) {
					ItemEstruturaIett iett = (ItemEstruturaIett) itDescendentes.next();
					codIettDescendentes.add(iett.getCodIett());
				}
				
				StringBuilder query = new StringBuilder("select ari from AcompReferenciaItemAri ari")
									.append(" where ari.acompReferenciaAref = ? and ari.itemEstruturaIett.codIett in (:listaItens)")
									.append(" and ari.itemEstruturaIett.indAtivoIett = 'S'");
								
				/*
				if(orgaoResponsavel.getCodOrg() != null){
                	query.append(" and ( ari.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg = :orgaoResp ")
                	     .append("or ( ari.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg is null ")
                	     .append("and ari.itemEstruturaIett.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg = :orgaoResp ) )");
                }
                */
				
				Query queryAri = this.getSession().createQuery(query.toString());
				queryAri.setLong(0, acompRefItem.getAcompReferenciaAref().getCodAref().longValue());
	        	queryAri.setParameterList("listaItens", codIettDescendentes);
		        		        
		        /*
		        if(orgaoResponsavel.getCodOrg() != null){
		        	queryAri.setLong("orgaoResp", orgaoResponsavel.getCodOrg().longValue());
		        }
		        */

		        lista.addAll(queryAri.list());
		        
		        List listaTemp = new ArrayList();
		        List listaAri = new ArrayList();
		        for (Iterator it = lista.iterator(); it.hasNext();) {
		        	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) it.next();
		        	
		        	if(!listaTemp.contains(ari.getItemEstruturaIett().getCodIett())){
				        //Verifica se possui algum ponto critico
				        //Caso n�o exista ele devolve a lista vazia pra instru��o que acionou o m�todo
		        		if(ari.getItemEstruturaIett().getPontoCriticoPtcs().size() > 0) {
			        		listaTemp.add(ari.getItemEstruturaIett().getCodIett());
			        		listaAri.add(ari);
		        		}

		        	}
		        }

		        lista.clear();
		        lista.addAll(listaAri);
		        
		        Collections.sort(lista,
			            new Comparator() {
		        			ItemEstruturaDao iettDaoSort = new ItemEstruturaDao(null);
			        		public int compare(Object o1, Object o2) {
		        		    	AcompReferenciaItemAri ari1 = (AcompReferenciaItemAri) o1;
		        		    	AcompReferenciaItemAri ari2 = (AcompReferenciaItemAri) o2;
		        		    	
			        		    ItemEstruturaIett item1 = ari1.getItemEstruturaIett();
			        		    ItemEstruturaIett item2 = ari2.getItemEstruturaIett();							
					        	if(iettDaoSort.getAscendenteMaximo(item1).equals(iettDaoSort.getAscendenteMaximo(item2))){												        			
					        		if(item1.getNivelIett().intValue() == item2.getNivelIett().intValue()){										
					        				return item1.getNomeIett().compareToIgnoreCase(item2.getNomeIett());	
					        		} else{										
					        				return item1.getNivelIett().intValue() - item2.getNivelIett().intValue(); 
					        		}				        	    	    
					        	} else {
					        	    if(item1.getNivelIett().intValue() == item2.getNivelIett().intValue()){									
				        	    	    return item1.getNomeIett().compareToIgnoreCase(item2.getNomeIett());
				        	    	} else {									
				        	    	    return iettDaoSort.getAscendenteMaximo(item1).getNomeIett().compareToIgnoreCase(iettDaoSort.getAscendenteMaximo(item2).getNomeIett());
				        	    	}				        	    
					        	}   		            	        		        
			        		}
			   	}
				);
			}
		}catch (HibernateException e){
			this.logger.error(e);
			throw new ECARException(); 
		}
		
		return lista;
	}
	
	
	/**
	 * Retorna uma lista de Aris a partir de Itens ou seus descendentes.
	 * 
	 * @param strCodIetts
	 * @param aref
	 * @param orgaoResponsavel
	 * @return
	 * @throws ECARException
	 */
	public List getAcompReferenciaItemFilhosByIett (final String strCodIetts, final AcompReferenciaAref aref,
			final OrgaoOrg orgaoResponsavel) throws ECARException{

		final ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
		List<AcompReferenciaItemAri> lista = new ArrayList<AcompReferenciaItemAri>();
		
		if(!strCodIetts.equals("")){
			String[] codIett = strCodIetts.split(";");
			Set<Long> codIetts = new HashSet<Long>();
			for(int i = 0; i < codIett.length; i++){
				if(!"".equals(codIett[i])){
					ItemEstruturaIettMin item = (ItemEstruturaIettMin) itemDao.buscar(ItemEstruturaIettMin.class, Long.valueOf(codIett[i]));
					codIetts.add(item.getCodIett());
					List<ItemEstruturaIettMin> descendentes = itemDao.getDescendentesMin( item, false);

					for (ItemEstruturaIettMin element : descendentes) {
						codIetts.add(element.getCodIett());
					}					
				}
			}
			
			if(!codIetts.isEmpty()){
				StringBuilder sql = new StringBuilder();
				sql.append("select ari from AcompReferenciaItemAri ari")
				   .append(" where ari.acompReferenciaAref.codAref = :codAref")
				   	.append(" and ari.itemEstruturaIett.indAtivoIett = 'S' ");
				
				if(codIetts.size() == 1){
					sql.append(" and ari.itemEstruturaIett.codIett = :codIett ");					
				}
				else {
					sql.append(" and ari.itemEstruturaIett.codIett in (:codIett) ");
				}
				
				if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
                	sql.append(" and ( ari.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg = :orgaoResp ")
                	     .append("or ( ari.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg is null ")
                	     .append("and ari.itemEstruturaIett.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg = :orgaoResp ) )");
                }

				Query queryAri = this.getSession().createQuery(sql.toString());   
		        
				queryAri.setLong("codAref", aref.getCodAref().longValue());

				if(codIetts.size() == 1){
					List<Long> listCodIetts = new ArrayList<Long>(codIetts);
					queryAri.setLong("codIett", ((Long) listCodIetts.get(0)).longValue());
				}
				else {
					queryAri.setParameterList("codIett", codIetts);
				}
				
		        if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
		        	queryAri.setLong("orgaoResp", orgaoResponsavel.getCodOrg().longValue());
		        }
		        lista.addAll( (ArrayList<AcompReferenciaItemAri>)queryAri.list() );
			}
		}
		return lista;
	}
	
	/**
	 * Retorna uma lista de Aris a partir de Itens ou seus descendentes desde que existam pontos cr�ticos nos itens.
	 * @param strCodIetts
	 * @param aref
	 * @param orgaoResponsavel
	 * @return
	 * @throws ECARException
	 */
	public List getAcompReferenciaItemFilhosByIettPorPtosCriticos (final String strCodIetts, final AcompReferenciaAref aref,
			final OrgaoOrg orgaoResponsavel) throws ECARException{

		final ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
		List<AcompReferenciaItemAri> lista = new ArrayList<AcompReferenciaItemAri>();
		
		if(!strCodIetts.equals("")){
			String[] codIett = strCodIetts.split(";");
			Set<Long> codIetts = new HashSet<Long>();
			for(int i = 0; i < codIett.length; i++){
				if(!"".equals(codIett[i])){
					ItemEstruturaIettMin item = (ItemEstruturaIettMin) itemDao.buscar(ItemEstruturaIettMin.class, Long.valueOf(codIett[i]));
					codIetts.add(item.getCodIett());
					List<ItemEstruturaIettMin> descendentes = itemDao.getDescendentesMin( item, false);

					for (ItemEstruturaIettMin element : descendentes) {
						codIetts.add(element.getCodIett());
					}
				}
			}

			
			if(!codIetts.isEmpty()){
				StringBuilder sql = new StringBuilder();
				sql.append("select ari from AcompReferenciaItemAri ari")
				   .append(" where ari.acompReferenciaAref.codAref = :codAref")
				   	.append(" and ari.itemEstruturaIett.indAtivoIett = 'S' ");
					
				
				if(codIetts.size() == 1){
					sql.append(" and ari.itemEstruturaIett.codIett = :codIett ");					
				}
				else {
					sql.append(" and ari.itemEstruturaIett.codIett in (:codIett) ");
				}
				
				if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
                	sql.append(" and ( ari.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg = :orgaoResp ")
                	     .append("or ( ari.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg is null ")
                	     .append("and ari.itemEstruturaIett.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg = :orgaoResp ) )");
                }

				Query queryAri = this.getSession().createQuery(sql.toString());   
		        
				queryAri.setLong("codAref", aref.getCodAref().longValue());

				if(codIetts.size() == 1){
					List<Long> listCodIetts = new ArrayList<Long>(codIetts);
					queryAri.setLong("codIett", ((Long) listCodIetts.get(0)).longValue());
				}
				else {
					queryAri.setParameterList("codIett", codIetts);
				}
				
		        if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
		        	queryAri.setLong("orgaoResp", orgaoResponsavel.getCodOrg().longValue());
		        }
		        lista.addAll( (ArrayList<AcompReferenciaItemAri>)queryAri.list() );
			}
		}
		
		//Retorna apenas os itens que possuem pontos cr�ticos
		List<AcompReferenciaItemAri> listaRetorno = new ArrayList<AcompReferenciaItemAri>();
		for(AcompReferenciaItemAri obj : lista) {
			
			if(obj.getItemEstruturaIett().getPontoCriticoPtcs().size() > 0) {
				listaRetorno.add(obj);
			}
		}

		return listaRetorno;
	}	
	
	
    /**
     * Retornar o ARI de um IETT
     *  
     * @param aris Collection
     * @param iett ItemEstruturaIett
     * @return AcompReferenciaItemAri ou null
     * @throws ECARException
     */
    public AcompReferenciaItemAri getAriByIett(Collection aris, ItemEstruturaIett iett) throws ECARException {
        
        try{        
        	for (Iterator it = aris.iterator(); it.hasNext();) {
				AcompReferenciaItemAri ari = (AcompReferenciaItemAri) it.next();           	
            	if(ari.getItemEstruturaIett().equals(iett)) {
            		return ari;
            	}
            }
            return null;
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException(e);            
        }        
    }

    /**
     * Gerar dados para o gr�fico evolu��o das posi��es 
     * @param request 
     * @return
     * @throws ECARException
     * 
     */
    public List getDadosEvolucaoPosicao(HttpServletRequest request) throws ECARException {
    	try {        
	    	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
	    	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(null);
	    	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
	    	OrgaoDao orgaoDao = new OrgaoDao(null);
	        List orgaos = null;
	        
 	        List tpfaOrdenadosEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturasAtivasInativas();
	        
//	        if(!"".equals(Pagina.getParamStr(request, "codOrgao"))) {
//	            orgao = (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, Long.valueOf(Pagina.getParamStr(request, "codOrgao")));
//	        }

 	        //Recupera da sess�o lista de refer�ncias agrupadas pelo mesmo dia/mes/ano
	        Collection periodosConsideradosAgrupados = (Collection) request.getSession().getAttribute("periodosConsideradosAgrupados"); // + Pagina.getParamStr(request, "codOrgao"));
	        //Recupera da sess�o lista total de refer�ncias
	        Collection periodosConsideradosTotais = (Collection) request.getSession().getAttribute("periodosConsideradosListagem");
	        
	        String codTipoAcompanhamento = Pagina.getParamStr(request, "codTipoAcompanhamento");
	    	TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa) new TipoAcompanhamentoDao(request).buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
	                
	        Map<AcompReferenciaAref, List<AcompReferenciaAref>> mapDiaMesAnoReferencias = criaMapDiaMesAnoReferencias(periodosConsideradosAgrupados);
	        
			StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemEstruturaDao.buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
	        
			List niveis = (List) request.getAttribute("listNiveis");
			
			UsuarioUsu usuario = null;
			Set gruposAcessoUsuario = null;
			if(((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")) != null) {
				usuario = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
				gruposAcessoUsuario = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getGruposAcesso();
			}
			Boolean itensSemInformacaoNivelPlanejamento = new Boolean(false);
			if("S".equals(Pagina.getParamStr(request, "semInformacaoNivelPlanejamento"))) {
				itensSemInformacaoNivelPlanejamento = new Boolean(true);
			}
			
			// limpar niveis para visualizar todos
			//niveis = null;
			
			List itensAcompanhamentos = (List) acompReferenciaItemDao.getItensAcompanhamentoInPeriodosByOrgaoRespPaginadoConsiderarPermissao(
	        		periodosConsideradosTotais, 
						niveis,
						orgaos, 
						usuario, 
						gruposAcessoUsuario,
						tipoAcompanhamento.getCodTa(), 
						null,
						itensSemInformacaoNivelPlanejamento,
						null,
						null,  -2, 1, true, false,
						"", false)[0];
	        
	        
	        
	        
	        //FIXME: vari�vel n�o utilizada no c�digo
	        //Comentado por Claudismar
			//List listFunAcomp = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();
	
			List listAvaliacoes = new ArrayList();
	        
			
			for (Iterator itPeriodos = periodosConsideradosAgrupados.iterator(); itPeriodos.hasNext();) {
				AcompReferenciaAref acompReferenciaDiaMesAno = (AcompReferenciaAref) itPeriodos.next();

				List<AcompReferenciaAref> referenciasMesmoDiaMesAno = mapDiaMesAnoReferencias.get(acompReferenciaDiaMesAno);
								
				
	
				PosicaoBean posicaoBean = new PosicaoBean();
				posicaoBean.setARef(acompReferenciaDiaMesAno);
				List coresPosicoes = new ArrayList();
				
				for (AcompReferenciaAref acompReferencia : referenciasMesmoDiaMesAno) {
					
					Iterator itItens = itensAcompanhamentos.iterator();
					
					while(itItens.hasNext()) {
						
						/*
						 * Este m�todo s� � usado para gera��o do gr�fico, ent�o n�o precisa da ordena��o dos itens.
						 * No m�todo que ordena os itens, � tratado se tipoAcompanhamento == null, o m�todo retorna
						 * a pr�pria lista de itens. Quando existe um tipoAcompanhamento, o m�todo retorna uma lista
						 * de AtributoEstruturaListagemItens.
						 */
						AtributoEstruturaListagemItens aeIett = (AtributoEstruturaListagemItens) itItens.next();
						//ItemEstruturaIett item = (ItemEstruturaIett) itItens.next();
						ItemEstruturaIett item = aeIett.getItem();
										
						Map map = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsideradosTotais, item);
						
						if(!map.containsKey(acompReferencia)){
							//N�o foi solicitado acompanhamento
							coresPosicoes.add("N/A");
						} 
						else {
							AcompReferenciaItemAri ari = (AcompReferenciaItemAri) map.get(acompReferencia);										
							
							//entra se o ARI t� liberado ou se n�o exige liberar acompanhamento
							if(ari.getStatusRelatorioSrl().equals(statusLiberado) || acompReferencia.getTipoAcompanhamentoTa().getIndLiberarAcompTa().equals(Pagina.NAO) ) {
								List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, tpfaOrdenadosEstrutura);
								
								for (Iterator itRelatorios = relatorios.iterator(); itRelatorios
										.hasNext();) {											
									AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();
	
									if(relatorio.getTipoFuncAcompTpfa().getCodTpfa().toString().equals(Pagina.getParamStr(request, "tipoFuncAcompTpfa"))) {
										if(relatorio.getCor() != null && "S".equals(relatorio.getIndLiberadoArel())){
											coresPosicoes.add(relatorio.getCor().getCodCor().toString());
										}
										else {
											TipoAcompFuncAcompTafc tafc = new TipoAcompFuncAcompTafc();
											TipoAcompFuncAcompDao tafcDao = new TipoAcompFuncAcompDao(request);										
											tafc = tafcDao.buscar(acompReferencia.getTipoAcompanhamentoTa().getCodTa(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
											//Verifica se � obrigat�rio
											if(tafc.getIndRegistroPosicaoTafc() != null && tafc.getIndRegistroPosicaoTafc().equals("O")){
												coresPosicoes.add("BRANCO");
											}
										}
									}
								}
							} 
							// Se Exige Liberar Acompanhamento
							else {
								List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, tpfaOrdenadosEstrutura);
								
								if(ari.getAcompRelatorioArels() != null && ari.getAcompRelatorioArels().size() > 0) {							
									for (Iterator itRelatorios = relatorios.iterator(); itRelatorios
											.hasNext();) {											
										AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();
		
										if(relatorio.getTipoFuncAcompTpfa().getCodTpfa().toString().equals(Pagina.getParamStr(request, "tipoFuncAcompTpfa"))) {
											TipoAcompFuncAcompTafc tafc = new TipoAcompFuncAcompTafc();
											TipoAcompFuncAcompDao tafcDao = new TipoAcompFuncAcompDao(request);										
											tafc = tafcDao.buscar(acompReferencia.getTipoAcompanhamentoTa().getCodTa(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
											//Conta como n�o liberados apenas os pareceres obrigat�rios e n�o informados 
											if(tafc.getIndRegistroPosicaoTafc() != null && tafc.getIndRegistroPosicaoTafc().equals("O") && 
													(relatorio.getIndLiberadoArel()==null || 
													relatorio.getIndLiberadoArel().equals(Pagina.NAO)) ){
												//N�o foi liberado acompanhamento
												coresPosicoes.add("N/L");
											}
										}
									}
								}
								else {
									//N�o foi solicitado acompanhamento
									coresPosicoes.add("N/A");
								}							
							}
						}
															
					}
				}//fim do foreach
								
				posicaoBean.setCor(coresPosicoes);
				listAvaliacoes.add(posicaoBean);
			}
			
			return listAvaliacoes;

		} catch(ECARException e){
			this.logger.error(e);
			throw e;            
		} catch(Exception e){
			this.logger.error(e);
			throw new ECARException(e);            
		}
    
    
    }
    
    
    
    /**
     * Gerar dados para o gr�fico evolu��o das posi��es 
     * @param request
     * @return
     * @throws ECARException
     */
    public List getDadosEvolucaoPosicaoGrafico(HttpServletRequest request) throws ECARException {
    	try {        
	    	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
	    	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(null);
	    	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
	    	OrgaoDao orgaoDao = new OrgaoDao(null);
	        List orgaos = null;
	        String tipoFuncAcompTpfa = Pagina.getParamStr(request, "tipoFuncAcompTpfa");
	        
 	        List tpfaOrdenadosEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
	        
//	        if(!"".equals(Pagina.getParamStr(request, "codOrgao"))) {
//	            orgao = (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, Long.valueOf(Pagina.getParamStr(request, "codOrgao")));
//	        }

	        Collection periodosConsiderados = (Collection) request.getSession().getAttribute("periodosConsiderados" + Pagina.getParamStr(request, "codOrgao"));
	        
			StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemEstruturaDao.buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
	        
			List niveis = (List) request.getAttribute("listNiveis");
			
			UsuarioUsu usuario = null;
			Set gruposAcessoUsuario = null;
			if(((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")) != null) {
				usuario = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
				gruposAcessoUsuario = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getGruposAcesso();
			}
			Boolean itensSemInformacaoNivelPlanejamento = new Boolean(false);
			if("S".equals(Pagina.getParamStr(request, "semInformacaoNivelPlanejamento"))) {
				itensSemInformacaoNivelPlanejamento = new Boolean(true);
			}
	        List itensAcompanhamentos = (List) acompReferenciaItemDao.getItensAcompanhamentoInPeriodosByOrgaoResp(
						periodosConsiderados, 
						niveis,
						orgaos, 
						usuario, 
						gruposAcessoUsuario,
						null, 
						null,
						itensSemInformacaoNivelPlanejamento,
						null,
						null,
						"")[0];
	        
	        //FIXME: vari�vel n�o utilizada no c�digo
	        //Comentado por Claudismar
			//List listFunAcomp = tipoFuncAcompDao.getTipoFuncAcompEmitePosicao();
	
			List listAvaliacoes = new ArrayList();
	        
			
			for (Iterator itPeriodos = periodosConsiderados.iterator(); itPeriodos.hasNext();) {
				AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodos.next();

				Iterator itItens = itensAcompanhamentos.iterator();
	
				PosicaoBean posicaoBean = new PosicaoBean();
				posicaoBean.setARef(acompReferencia);
				List coresPosicoes = new ArrayList();
				
				while(itItens.hasNext()) {
					
					/*
					 * Este m�todo s� � usado para gera��o do gr�fico, ent�o n�o precisa da ordena��o dos itens.
					 * No m�todo que ordena os itens, � tratado se tipoAcompanhamento == null, o m�todo retorna
					 * a pr�pria lista de itens. Quando existe um tipoAcompanhamento, o m�todo retorna uma lista
					 * de AtributoEstruturaListagemItens.
					 */
					AtributoEstruturaListagemItens aeIett = (AtributoEstruturaListagemItens) itItens.next();
					//ItemEstruturaIett item = (ItemEstruturaIett) itItens.next();
					ItemEstruturaIett item = aeIett.getItem();
									
					Map map = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsiderados, item);
					
					if(!map.containsKey(acompReferencia)){
						//N�o foi solicitado acompanhamento
						//coresPosicoes.add(Cor.NAO_ACOMPANHADO);
					} 
					else {
						AcompReferenciaItemAri ari = (AcompReferenciaItemAri) map.get(acompReferencia);										
						
						//entra se o ARI t� liberado ou se n�o exige liberar acompanhamento
						if(ari.getStatusRelatorioSrl().equals(statusLiberado) || acompReferencia.getTipoAcompanhamentoTa().getIndLiberarAcompTa().equals(Pagina.NAO) ) {
							List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, tpfaOrdenadosEstrutura);
							
							for (Iterator itRelatorios = relatorios.iterator(); itRelatorios.hasNext();) {											
								AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();

								if(relatorio.getTipoFuncAcompTpfa().getCodTpfa().toString().equals(tipoFuncAcompTpfa)) {
									if(relatorio.getCor() != null && "S".equals(relatorio.getIndLiberadoArel())){
										coresPosicoes.add(relatorio.getCor().getCodCor().toString());
									}
									else {
										TipoAcompFuncAcompTafc tafc = new TipoAcompFuncAcompTafc();
										TipoAcompFuncAcompDao tafcDao = new TipoAcompFuncAcompDao(request);										
										tafc = tafcDao.buscar(acompReferencia.getTipoAcompanhamentoTa().getCodTa(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
										//Verifica se � obrigat�rio
										if(tafc.getIndRegistroPosicaoTafc() != null && tafc.getIndRegistroPosicaoTafc().equals("O")){
											coresPosicoes.add(Cor.BRANCO);
										}
									}
								}
							}
						} 
						// Se Exige Liberar Acompanhamento
						else {
							List relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, tpfaOrdenadosEstrutura);
							
							if(ari.getAcompRelatorioArels() != null && ari.getAcompRelatorioArels().size() > 0) {							
								for (Iterator itRelatorios = relatorios.iterator(); itRelatorios
										.hasNext();) {											
									AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();
	
									if(relatorio.getTipoFuncAcompTpfa().getCodTpfa().toString().equals(tipoFuncAcompTpfa)) {
										TipoAcompFuncAcompTafc tafc = new TipoAcompFuncAcompTafc();
										TipoAcompFuncAcompDao tafcDao = new TipoAcompFuncAcompDao(request);										
										tafc = tafcDao.buscar(acompReferencia.getTipoAcompanhamentoTa().getCodTa(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
										//Conta como n�o liberados apenas os pareceres obrigat�rios e n�o informados 
										if(tafc.getIndRegistroPosicaoTafc() != null && tafc.getIndRegistroPosicaoTafc().equals("O") && 
												(relatorio.getIndLiberadoArel()==null || 
												relatorio.getIndLiberadoArel().equals(Pagina.NAO)) ){
											//N�o foi liberado acompanhamento
											coresPosicoes.add(Cor.NAO_LIBERADO);
										}
									}
								}
							}
							else {
								//N�o foi solicitado acompanhamento
								coresPosicoes.add(Cor.NAO_ACOMPANHADO);
							}							
						}// fim else exije liberar
					} //fim else solicitado acompanhamento 
					
				}// fim while 
				
				posicaoBean.setCor(coresPosicoes);
				listAvaliacoes.add(posicaoBean);
			}
			
			return listAvaliacoes;

		} catch(Exception e){
			this.logger.error(e);
			throw new ECARException(e);            
		}
    
    
    }    
    
    
    
    
    /**
     *
     * @param ari
     * @param tpfa
     * @param qtdeUltimosAcompanhamentos
     * @return
     * @throws HibernateException
     * @throws ECARException
     */
    public List getUltimosAcompanhamentosItem(AcompReferenciaItemAri ari, TipoFuncAcompTpfa tpfa, Integer qtdeUltimosAcompanhamentos)
		throws HibernateException, ECARException {
    	try{
    		StringBuilder str = new StringBuilder();

    		str.append("select arel from AcompRelatorioArel arel");
    		str.append(" join arel.acompReferenciaItemAri as ari");
    		str.append(" join ari.acompReferenciaAref as aref");
    		str.append(" where ari.itemEstruturaIett.codIett = :codItem");
    		str.append("   and ari.itemEstruturaIett.indAtivoIett = 'S'");
    		str.append("   and arel.tipoFuncAcompTpfa.codTpfa = :codTpfa");
    		str.append("   and arel.situacaoSit is not null");
    		str.append("   and aref.tipoAcompanhamentoTa.codTa = :codTa");
    		str.append("   and arel.indLiberadoArel = 'S'");
    		
    		//verifica se o tipo acompanhamento utilizado exige liberar acompanhamento
    		if (ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndLiberarAcompTa() != null && ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndLiberarAcompTa().equals(Dominios.SIM)){
    			str.append("   and ari.statusRelatorioSrl.codSrl = :codSrl");
    		} 
    		
    		if (ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndSepararOrgaoTa() != null && 
    				ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals(Dominios.SIM) &&
    				ari.getAcompReferenciaAref().getOrgaoOrg() != null){
    			str.append("   and aref.orgaoOrg = :codOrgao");	
    		}
    		
		    if(qtdeUltimosAcompanhamentos != null){
	    		str.append("   and aref <> :acompanhamentoAref") ;
		    }
    		str.append(" order by aref.anoAref desc, aref.mesAref desc, aref.diaAref");

		    Query query = this.getSession().createQuery(str.toString());
		    query.setLong("codItem", ari.getItemEstruturaIett().getCodIett().longValue());
		    query.setLong("codTpfa", tpfa.getCodTpfa().longValue());
		    query.setLong("codTa", ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getCodTa().longValue());

    		//verifica se o tipo acompanhamento exige liberar acompanhamento
    		if (ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndLiberarAcompTa() != null && ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndLiberarAcompTa().equals(Dominios.SIM)){
    			query.setLong("codSrl", AcompReferenciaItemDao.STATUS_LIBERADO);
    		}
		    
    		if (ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndSepararOrgaoTa() != null &&
    				ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals(Dominios.SIM) &&
    				ari.getAcompReferenciaAref().getOrgaoOrg() != null){
    			query.setLong("codOrgao", ari.getAcompReferenciaAref().getOrgaoOrg().getCodOrg().longValue());
    		}
		    
		    if(qtdeUltimosAcompanhamentos != null){
		    	query.setEntity("acompanhamentoAref", ari.getAcompReferenciaAref());
		    	query.setMaxResults(qtdeUltimosAcompanhamentos.intValue());
		    }
			
			return query.list();
		
		} catch(HibernateException e){
		    throw new ECARException(e);
		}
	}
    
    public List getCodIettsFromAris(String arisSelecionados) {
    	
    	String[] codAris = arisSelecionados.split(";");
    	List<Long> aris = new ArrayList<Long>();
		for (int i = 0; i < codAris.length; i++) {
			if (!"".equals(codAris[i]) && !";".equals(codAris[i])) {
				aris.add(Long.valueOf(codAris[i]));
			}
		}
    	
    	String q = "SELECT ari.itemEstruturaIett.codIett FROM AcompReferenciaItemAri as ari " +
    			"WHERE ari.codAri IN (:codAris)";
    	Query query = this.getSession().createQuery(q);
    	query.setParameterList("codAris", aris);
    	return query.list();
    	
    }
    
    public List getUltimosAcompanhamentos(List codIETTs, TipoAcompanhamentoTa ta, int nivelIett, Integer qtdeUltimosAcompanhamentos,
    		AcompReferenciaAref arefPartida,
    		List<String> listCiclos)
	throws HibernateException, ECARException {
	try{
		
		//Pesquisa de Arefs
		String hqlAref = "FROM AcompReferenciaAref aref " +
				"WHERE aref.dataInicioAref <= :dataCiclo " +
				"AND aref.tipoAcompanhamentoTa.codTa = :codTA " +
				"ORDER BY aref.dataInicioAref DESC";
		
		Query query = this.getSession().createQuery(hqlAref);
		query.setDate("dataCiclo", arefPartida.getDataInicioAref());
		query.setLong("codTA", ta.getCodTa());
		query.setMaxResults(qtdeUltimosAcompanhamentos);
		
		List<Long> listCodAref = new ArrayList<Long>();
		
		List<AcompReferenciaAref> arefs = query.list();
		for (AcompReferenciaAref acompReferenciaAref : arefs) {
			listCodAref.add(acompReferenciaAref.getCodAref());
			listCiclos.add(acompReferenciaAref.getNomeAref());
		}
		
		StringBuilder str = new StringBuilder();

		str.append("select arel from AcompRelatorioArel arel");
		str.append(" join arel.acompReferenciaItemAri as ari");
		str.append(" join ari.acompReferenciaAref as aref");
		str.append(" where ari.itemEstruturaIett.codIett in (:cods)");
		str.append("   and ari.itemEstruturaIett.indAtivoIett = 'S'");
		str.append("   and aref.tipoAcompanhamentoTa.codTa = :codTa");
		str.append("   and ari.itemEstruturaIett.nivelIett = :nivelIett");
		str.append("   and aref.codAref in (:listCodArefs)");
		
		//str.append("   and arel.indLiberadoArel = 'S'");
		
		/*
		//verifica se o tipo acompanhamento utilizado exige liberar acompanhamento
		if (ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndLiberarAcompTa() != null && ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndLiberarAcompTa().equals(Dominios.SIM)){
			str.append("   and ari.statusRelatorioSrl.codSrl = :codSrl");
		} 
		
		if (ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndSepararOrgaoTa() != null && 
				ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals(Dominios.SIM) &&
				ari.getAcompReferenciaAref().getOrgaoOrg() != null){
			str.append("   and aref.orgaoOrg = :codOrgao");	
		}
		*/
		
		str.append(" order by aref.anoAref desc, aref.mesAref desc, aref.diaAref desc");

	    query = this.getSession().createQuery(str.toString());
	    query.setParameterList("cods", codIETTs);
	    query.setLong("codTa", ta.getCodTa());
	    query.setInteger("nivelIett", nivelIett);
	    query.setParameterList("listCodArefs", listCodAref);
	    
	    /*
		//verifica se o tipo acompanhamento exige liberar acompanhamento
		if (ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndLiberarAcompTa() != null && ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndLiberarAcompTa().equals(Dominios.SIM)){
			query.setLong("codSrl", AcompReferenciaItemDao.STATUS_LIBERADO);
		}
		
		if (ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndSepararOrgaoTa() != null &&
				ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals(Dominios.SIM) &&
				ari.getAcompReferenciaAref().getOrgaoOrg() != null){
			query.setLong("codOrgao", ari.getAcompReferenciaAref().getOrgaoOrg().getCodOrg().longValue());
		}
	    */
		List<AcompRelatorioArel> arels = query.list();
		
		//Extrair ietts
		List<ItemEstruturaIett> ietts = new ArrayList<ItemEstruturaIett>();
		for (AcompRelatorioArel arel : arels) {
			if(!ietts.contains(arel.getAcompReferenciaItemAri().getItemEstruturaIett())) {
				ietts.add(arel.getAcompReferenciaItemAri().getItemEstruturaIett());
			}
		}
		
		//Ordenar
		ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
		
		List<AtributoEstruturaListagemItens> itensOrd = itemDao.getItensOrdenados(ietts, ta);
		
		//Ordenar arels
		List<AcompRelatorioArel> arelsOrd = new ArrayList<AcompRelatorioArel>();
		
		for (AtributoEstruturaListagemItens itemIett : itensOrd) {
			for (AcompRelatorioArel acompRelatorioArel : arels) {
				if(acompRelatorioArel.getAcompReferenciaItemAri().getItemEstruturaIett().equals(itemIett.getItem())) {
					arelsOrd.add(acompRelatorioArel);
				}
			}
		}
		
		return arelsOrd;
	
	} catch(HibernateException e){
	    throw new ECARException(e);
	}
}
    
    /**
     * Retorna uma lista de AcompReferenciaItemAri, sendo que:<br>
     * Se opcaoModelo for "ECAR-001B": Retorna Aris de um �rg�o espec�fico;<br>
     * Se opcaoModelo for "ECAR-002B": Retorna Aris de uma situa��o (Cor) espec�fica.
     * 
     * @param codArisSelecionados
     * @param mesReferencia
     * @param opcaoModelo
     * @param chave
     * @param codTpfa
     * @return List
     * @throws ECARException 
     */
    public List getAcompRelatorioAcompanhamentoByAris(List codArisSelecionados, AcompReferenciaAref mesReferencia, String opcaoModelo, String chave, String codTpfa) throws ECARException{
   		try {
   			StringBuilder sql = new StringBuilder("select arel from AcompRelatorioArel arel");
	    	
	    	sql.append(" where ");
	    	sql.append(" arel.acompReferenciaItemAri.itemEstruturaIett.indAtivoIett = 'S'");
	    	
	    	
	    	if("ECAR-001B".equalsIgnoreCase(opcaoModelo) && !"".equals(chave)){
	    		if(mesReferencia != null && mesReferencia.getTipoAcompanhamentoTa() != null && mesReferencia.getTipoAcompanhamentoTa().getIndSepararOrgaoTa() != null &&
	    		mesReferencia.getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals("S")) {
	    			sql.append(" and arel.acompReferenciaItemAri.acompReferenciaAref.orgaoOrg.codOrg = :chave ");
	    		} else {
	    			sql.append(" and arel.acompReferenciaItemAri.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg = :chave ");
	    		}	
	    	}
	    	else if("ECAR-002B".equalsIgnoreCase(opcaoModelo) && !"".equals(chave)){
	    		sql.append(" and arel.cor.codCor = :chave ");
	    	}
	    	
	    	if(codArisSelecionados != null && codArisSelecionados.size() > 1){
	    		sql.append(" and arel.acompReferenciaItemAri.codAri in (:listaAris) ");
	    	}
	    	else if(codArisSelecionados != null && !codArisSelecionados.isEmpty()) {
	    		sql.append(" and arel.acompReferenciaItemAri.codAri = :listaAris ");
	    	}
	    	
	    	if(!"".equals(codTpfa)){
	    		sql.append(" and arel.tipoFuncAcompTpfa.codTpfa = :codTpfa");
	    	}
	    	
	   		sql.append(" and arel.acompReferenciaItemAri.acompReferenciaAref.codAref = :codAref")
	   			 .append(" order by arel.acompReferenciaItemAri.itemEstruturaIett.nomeIett");
	   		
			Query q = this.session.createQuery(sql.toString());
			
			q.setLong("codAref", mesReferencia.getCodAref().longValue());
			
			if(!"".equals(chave)){
				q.setLong("chave", Long.valueOf(chave).longValue());
			}
			
	    	if(codArisSelecionados != null && codArisSelecionados.size() > 1){
	    		q.setParameterList("listaAris", codArisSelecionados);
	    	}
	    	else if (codArisSelecionados != null && !codArisSelecionados.isEmpty()) {
	    		q.setLong("listaAris", ((Long)codArisSelecionados.get(0)).longValue());
	    	}
			
	    	if(!"".equals(codTpfa)){
	    		q.setLong("codTpfa", Long.valueOf(codTpfa).longValue());
	    	}
	    	
	    	List retorno = q.list();
	    	
	    	if(retorno != null && !retorno.isEmpty()){
	    		//Alterado ref. Mantis 7557: ordem hier�rquica do parecer, ou seja, a ordem inversa de apresenta��o dos campos na tela de "dados gerais": smiles � frente e seta a seguir.
	    		List arelsTpfaOrdenados = this.getAcompRelatorioArelOrderByFuncaoAcomp(retorno, new TipoFuncAcompDao(null).getFuncaoAcompOrderByEstruturasHierarquicamente());
	    		ordenarListaRelatorioAcompanhamento(arelsTpfaOrdenados, opcaoModelo);
	    		retorno.clear();
	    		retorno.addAll(arelsTpfaOrdenados);
	    	}
	    	
	    	return retorno;
	    	
		} catch (HibernateException e) {
			e.printStackTrace();
			this.logger.error(e);
			throw new ECARException(e);            
		}
    }
    
    /**
     * M�dodo que ordena os itens para o relat�rio de acompanhamento. Este m�todo s� � utilizado por getAcompRelatorioAcompanhamentoByAris().<br>
     * <br> 
	 * Se opcaoModelo == RELATORIO_ORGAO, primeiro ordeno por situa��o e depois por �rg�o para ficar ordenado por "�rg�o e Situa��o".<br>
	 * Se opcaoModelo == RELATORIO_ORGAO_ESPECIFICO os itens ser�o de 1 �rg�o espec�fico, portanto s� ordeno por situa��o.<br>
	 * Se opcaoModelo == RELATORIO_SITUACAO_ESPECIFICO os itens s�o s� de 1 situa��o, s� ordeno por �rg�o.<br>
	 * Se opcaoModelo == RELATORIO_SITUACAO, primeiro ordeno por �rg�o e depois por situa��o para ficar ordenado por "Situa��o e Org�o".<br>
	 * Se opcaoModelo == RELATORIO_ESTRUTURA, ordeno os itens conforme estiver configurado para ordenar.
	 * <br>
	 * Obs.: � sempre a �ltima ordena��o que predomina.
     * 
     * @param lista
     * @param opcaoModelo
     * @throws ECARException 
     */
    private void ordenarListaRelatorioAcompanhamento(List lista, String opcaoModelo) throws ECARException{

    	//Se opcaoModelo == RELATORIO_ORGAO, primeiro ordeno por situa��o e depois por �rg�o para ficar ordenado por "�rg�o e Situa��o".
		if(RELATORIO_ORGAO.equals(opcaoModelo)){
			
			/*
			 * TEste ref. mantis 0007679: O Relat�rio para o Conselho Revisor, 
			 * vers�o PPA, quando a emiss�o solicitada � por �rg�o, deve classificar 
			 * por �rg�o e tamb�m por "sigla" do item (em ordem crescente).
			 */
			boolean ppa = false;
			if(lista != null && !lista.isEmpty()){
				AcompRelatorioArel arel = (AcompRelatorioArel) lista.get(0);
				ppa = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa().contains("PPA");
			}
			
			if(!ppa){
				ordenarArelsPorIettsConfigurados(lista);
			}
			else {
	    		Collections.sort(lista, new Comparator(){
					public int compare(Object arg1, Object arg2) {
						
						AcompRelatorioArel arel1 = (AcompRelatorioArel) arg1;
						AcompRelatorioArel arel2 = (AcompRelatorioArel) arg2;
						
						ItemEstruturaIett iett1 = arel1.getAcompReferenciaItemAri().getItemEstruturaIett();
						ItemEstruturaIett iett2 = arel2.getAcompReferenciaItemAri().getItemEstruturaIett();
						
						return iett1.getSiglaIett().compareTo(iett2.getSiglaIett());
					}
	    			
	    		});
			}
			
    		Collections.sort(lista, new Comparator(){
				public int compare(Object arg1, Object arg2) {
					
					AcompRelatorioArel arel1 = (AcompRelatorioArel) arg1;
					AcompRelatorioArel arel2 = (AcompRelatorioArel) arg2;
					
					OrgaoOrg org1 = arel1.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett();
					OrgaoOrg org2 = arel2.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett();
					
					if(org1 == null && org2 == null)
						return 0;
					if(org1 != null && org2 == null)
						return 1;
					if(org1 == null && org2 != null)
						return -1;
					
					return org1.getSiglaOrg().compareTo(org2.getSiglaOrg());
				}
    			
    		});
		}
		// Se opcaoModelo == RELATORIO_ORGAO_ESPECIFICO os itens ser�o de 1 �rg�o espec�fico, portanto s� ordeno por situa��o.
		else if(RELATORIO_ORGAO_ESPECIFICO.equals(opcaoModelo)){
    		Collections.sort(lista, new Comparator(){
				public int compare(Object arg1, Object arg2) {
					
					AcompRelatorioArel arel1 = (AcompRelatorioArel) arg1;
					AcompRelatorioArel arel2 = (AcompRelatorioArel) arg2;
					
					Cor cor1 = arel1.getCor();
					Cor cor2 = arel2.getCor();
					
					if(cor1 == null && cor2 == null)
						return 0;
					if(cor1 != null && cor2 == null)
						return 1;
					if(cor1 == null && cor2 != null)
						return -1;
					
					return cor1.getOrdemCor().compareTo(cor2.getOrdemCor());
				}
    			
    		});

    		ordenarArelsPorIettsConfigurados(lista);
    		
		}
		//opcaoModelo == RELATORIO_SITUACAO_ESPECIFICO os itens s�o s� de 1 situa��o, s� ordeno por �rg�o.
		else if(RELATORIO_SITUACAO_ESPECIFICO.equals(opcaoModelo)){

			ordenarArelsPorIettsConfigurados(lista);
    		
			Collections.sort(lista, new Comparator(){
				public int compare(Object arg1, Object arg2) {
					
					AcompRelatorioArel arel1 = (AcompRelatorioArel) arg1;
					AcompRelatorioArel arel2 = (AcompRelatorioArel) arg2;
					
					OrgaoOrg org1 = arel1.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett();
					OrgaoOrg org2 = arel2.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett();
					
					if(org1 == null && org2 == null)
						return 0;
					if(org1 != null && org2 == null)
						return 1;
					if(org1 == null && org2 != null)
						return -1;
					
					return org1.getSiglaOrg().compareTo(org2.getSiglaOrg());
				}
    			
    		});
		}
		// Se opcaoModelo == RELATORIO_SITUACAO, primeiro ordeno por �rg�o e depois por situa��o para ficar ordenado por "Situa��o e Org�o".
		else if(RELATORIO_SITUACAO.equals(opcaoModelo)){
    		//Situa��o e �rg�o

			ordenarArelsPorIettsConfigurados(lista);

			Collections.sort(lista, new Comparator(){
				public int compare(Object arg1, Object arg2) {
					
					AcompRelatorioArel arel1 = (AcompRelatorioArel) arg1;
					AcompRelatorioArel arel2 = (AcompRelatorioArel) arg2;
					
					OrgaoOrg org1 = arel1.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett();
					OrgaoOrg org2 = arel2.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett();
					
					if(org1 == null && org2 == null)
						return 0;
					if(org1 != null && org2 == null)
						return 1;
					if(org1 == null && org2 != null)
						return -1;
					
					return org1.getSiglaOrg().compareTo(org2.getSiglaOrg());
				}
    			
    		});

    		Collections.sort(lista, new Comparator(){
				public int compare(Object arg1, Object arg2) {
					
					AcompRelatorioArel arel1 = (AcompRelatorioArel) arg1;
					AcompRelatorioArel arel2 = (AcompRelatorioArel) arg2;
					
					Cor cor1 = arel1.getCor();
					Cor cor2 = arel2.getCor();
					
					if(cor1 == null && cor2 == null)
						return 0;
					if(cor1 != null && cor2 == null)
						return 1;
					if(cor1 == null && cor2 != null)
						return -1;
					
					return cor1.getOrdemCor().compareTo(cor2.getOrdemCor());
				}
    			
    		});
    	}
		//Se opcaoModelo == RELATORIO_ESTRUTURA, ordeno os itens conforme estiver configurado para ordenar.
		else if (RELATORIO_ESTRUTURA.equals(opcaoModelo)){
			ordenarArelsPorIettsConfigurados(lista);
		}
    }
    
    private void ordenarArelsPorIettsConfigurados(List lista) throws ECARException{
    	List ietts = new ArrayList();
    	TipoAcompanhamentoTa tipoAcomp = null;

    	
    	for (Iterator itAcomps = lista.iterator(); itAcomps.hasNext();) {
			AcompRelatorioArel arel = (AcompRelatorioArel) itAcomps.next();
    		
    		if(tipoAcomp == null){
    			tipoAcomp = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getTipoAcompanhamentoTa();
    		}
    		if(!ietts.contains(arel.getAcompReferenciaItemAri().getItemEstruturaIett())){
    			ietts.add(arel.getAcompReferenciaItemAri().getItemEstruturaIett());
    		}
    	}
    	
    	List aeList = new ItemEstruturaDao(null).getItensOrdenados(ietts, tipoAcomp);
    	List arels = new ArrayList();
    	
    	for (Iterator itAe = aeList.iterator(); itAe.hasNext();) {
    		AtributoEstruturaListagemItens ae = (AtributoEstruturaListagemItens) itAe.next();
    		
    		for (Iterator itArels = lista.iterator(); itArels.hasNext();) {
    			AcompRelatorioArel arel = (AcompRelatorioArel) itArels.next();

    			if(!arels.contains(arel) && arel.getAcompReferenciaItemAri().getItemEstruturaIett().equals(ae.getItem())){
    				arels.add(arel);
    			}
    		}
    	}
    	
    	lista.clear();
    	lista.addAll(arels);    	
    }
    
    /**
     * Lista os itens ARI de acordo com a data limite informada. <br>
     * @author rogerio
     * @since 0.1, 23/02/2007
     * @version 0.1, 23/02/2007
     * @param dataLimiteStr
     * @return List
     * @throws ECARException
     */
    public List listarAcompReferenciaItemPorDataLimiteFisico(String dataLimiteStr) throws ECARException {
    	List list = null;
    	
    	try {
	    	Query query = session.createQuery(
		       		"from AcompReferenciaItemAri " +
		       		"where dataLimiteAcompFisicoAri = :data " +
		       		"order by codAri asc " );
		    	
		    	Date data = Data.parseDate(dataLimiteStr);
		    	query.setDate("data", data);
		
		    	list = query.list();
    	} catch( HibernateException e ) {
    		this.logger.error(e);
    		throw new ECARException("erro.hibernateException");
    	}
    	
    	return list;
    } 
    
    /**
     * Lista os itens ARI de acordo com a data limite informada. <br>
     * @param dataLimite
     * @author rogerio
     * @since 0.1, 23/02/2007
     * @version 0.1, 23/02/2007
     * @return List
     * @throws ECARException
     */
    public List listarAcompReferenciaItemPorDataLimiteFisico(Date dataLimite) throws ECARException {
    	List list = null;
    	
    	try {
    		
    		// no intervalo do dia
    		Calendar cDataLimite = Calendar.getInstance();
    		Calendar cInicial = Calendar.getInstance();
    		Calendar cFinal = Calendar.getInstance();
    		cDataLimite.setTime(dataLimite);
    		cInicial.set(cDataLimite.get(Calendar.YEAR), cDataLimite.get(Calendar.MONTH), cDataLimite.get(Calendar.DAY_OF_MONTH), 0,0,0);
    		cFinal.set(cDataLimite.get(Calendar.YEAR), cDataLimite.get(Calendar.MONTH), cDataLimite.get(Calendar.DAY_OF_MONTH), 23,59,59);
    		
	    	Query query = session.createQuery(
		       		"from AcompReferenciaItemAri " +
		       		"where dataLimiteAcompFisicoAri between :dataInicial and :dataFinal " +
		       		"order by codAri asc " );
		    	query.setDate("dataInicial",cInicial.getTime());
		    	query.setDate("dataFinal",cFinal.getTime());
		
		    	list = query.list();
    	} catch( HibernateException e ) {
    		this.logger.error(e);
    		throw new ECARException("erro.hibernateException");
    	}
    	
    	return list;
    } 
    
    /**
     * Lista os itens ARLI de acordo com a data limite informada. <br>
     * @author rogerio
     * @since 0.1, 26/02/2007
     * @version 0.1, 26/02/2007
     * @param dataLimiteStr
     * @return List
     * @throws ECARException
     */
    public List listarAcompReferenciaItemLimitesPorVenctoParecer(String dataLimiteStr) throws ECARException {
    	List list = null;
    	
    	try {
	    	Query query = session.createQuery(
		       		"from AcompRefItemLimitesArli " +
		       		"where dataLimiteArli = :data " +
		       		"order by dataLimiteArli asc " );
		    	
		    	Date data = Data.parseDate(dataLimiteStr);
		    	query.setDate("data", data);
		
		    	list = query.list();
    	} catch( HibernateException e ) {
    		this.logger.error(e);
    		throw new ECARException("erro.hibernateException");
    	}
    	
    	return (list == null ? new ArrayList() : list);
    }
  
    
    /**
     * Lista os itens ARLI de acordo com a data limite informada. <br>
     * @author rogerio
     * @since 0.1, 26/02/2007
     * @version 0.1, 26/02/2007
     * @param dataLimite
     * @return List
     * @throws ECARException
     */
    public List listarAcompReferenciaItemLimitesPorVenctoParecer(Date dataLimite) throws ECARException {
    	List list = null;
    	
    	try {
    		
    		// no intervalo do dia
    		Calendar cDataLimite = Calendar.getInstance();
    		Calendar cInicial = Calendar.getInstance();
    		Calendar cFinal = Calendar.getInstance();
    		cDataLimite.setTime(dataLimite);
    		cInicial.set(cDataLimite.get(Calendar.YEAR), cDataLimite.get(Calendar.MONTH), cDataLimite.get(Calendar.DAY_OF_MONTH), 0,0,0);
    		cFinal.set(cDataLimite.get(Calendar.YEAR), cDataLimite.get(Calendar.MONTH), cDataLimite.get(Calendar.DAY_OF_MONTH), 23,59,59);
    				    	
	    	Query query = session.createQuery(
		       		"from AcompRefItemLimitesArli " +
		       		"where dataLimiteArli between :dataInicial and :dataFinal  " +
		       		"order by dataLimiteArli asc " );
	    	query.setDate("dataInicial",cInicial.getTime());
	    	query.setDate("dataFinal",cFinal.getTime());
		
		    	list = query.list();
    	} catch( HibernateException e ) {
    		this.logger.error(e);
    		throw new ECARException("erro.hibernateException");
    	}
    	
    	return (list == null ? new ArrayList() : list);
    }
    
    /**
     * M�todo que traz 2 listas:<br>
     * [0] = Lista de Aris dos c�digos passados como par�metros.<br>
     * [1] = Lista de Itens dos Aris dos c�digos passados como par�metros.<br>
     * 
     * @author aleixo
     * @since 04/06/2007
     * @param codAris
     * @return List
     * @throws ECARException
     */
    public List[] listarArisAndIetts(List codAris) throws ECARException{
    	List listAris = new ArrayList();
    	List listIetts = new ArrayList();
    	
    	try {
    		
    		StringBuilder selectAri = new StringBuilder();
    		StringBuilder selectIett = new StringBuilder();
    		StringBuilder where = new StringBuilder();
    		
    		selectAri.append("select ari from AcompReferenciaItemAri ari where ");
    		selectIett.append("select ari.itemEstruturaIett from AcompReferenciaItemAri ari where ");
    					
    		
    		int tamanho = codAris.size() - 1;
    		int cont = 0;
    		for(Iterator it = codAris.iterator(); it.hasNext();){
    			Object cod = (Object) it.next();
    			
    			where.append(" ari.codAri = " + cod.toString());
    			
    			if(cont < tamanho){
    				where.append(" or ");
    			}
    			
    			cont++;
    		}
    		
    		selectAri.append(where.toString());
    		selectIett.append(where.toString())
    					.append(" and ari.itemEstruturaIett.indAtivoIett = 'S' ");
    		
    		Query query = this.session.createQuery(selectAri.toString());
		    listAris = query.list();
		    
		    query = this.session.createQuery(selectIett.toString());
		    listIetts = query.list();
		    
    	} catch( HibernateException e ) {
    		this.logger.error(e);
    		throw new ECARException("erro.hibernateException");
    	}
    	
    	return new List[] {(listAris != null ? listAris : new ArrayList()), (listIetts != null ? listIetts : new ArrayList()) };
    }
    
    
    /**
     *
     * @param referenciaItem
     * @param periodosConsiderados
     * @return
     * @throws ECARException
     */
    public List getAcompItemReferenciasOrderByReferecias(AcompReferenciaItemAri referenciaItem, Collection periodosConsiderados, OrgaoOrg orgao) throws ECARException{
    	try{
    		StringBuilder query = new StringBuilder("");
    		int numPeriodos = 1;
            
            if(periodosConsiderados != null) { 
            	query = new StringBuilder("SELECT ari FROM AcompReferenciaItemAri ari ")
            				.append(" JOIN ari.acompReferenciaAref as aref" )
 //           				.append(" JOIN ari.acompRelatorioArels as arels")
//            				.append(" JOIN arels.tipoFuncAcompTpfa as tpfa")           	
            				.append(" where ari.itemEstruturaIett = ? and aref.codAref in (:listaAcompanhamentos) ");

            				if (orgao != null){
            					query.append(" AND ari.acompReferenciaAref.orgaoOrg.codOrg = :codOrg ");
            				}
            				else{
            					query.append(" AND ari.acompReferenciaAref.orgaoOrg.codOrg is null ");
            				}
            				query.append(" order by ari.acompReferenciaAref.anoAref asc, ari.acompReferenciaAref.mesAref asc, ari.acompReferenciaAref.diaAref asc");
            } else {
                query = new StringBuilder("select ari from AcompReferenciaItemAri ari ")
                			.append("where ari.itemEstruturaIett = ?");
            }
            
            Query queryAri = this.getSession().createQuery(query.toString());   
            queryAri.setLong(0, referenciaItem.getItemEstruturaIett().getCodIett().longValue());

            if(orgao != null)
            	queryAri.setLong("codOrg", orgao.getCodOrg().longValue());
            
            List listaCodigosAref = new ArrayList();
            if(periodosConsiderados != null) {
	            //List listaCodigosAref = new ArrayList();
            	numPeriodos = periodosConsiderados.size();
	            
	            for (Iterator it = periodosConsiderados.iterator(); it.hasNext();) {
	                AcompReferenciaAref aReferencia = (AcompReferenciaAref)it.next();
	                listaCodigosAref.add(aReferencia.getCodAref());                
	            }
	            
	            queryAri.setParameterList("listaAcompanhamentos", listaCodigosAref);
            }
            
            List retorno = queryAri.list();
            
            return retorno;
        } catch(HibernateException e){
			this.logger.error(e);
            throw new ECARException(e);              
        }

    	}
            
            
            
    /**
     *
     * @param listaAris
     * @param periodosConsiderados
     * @return
     */
    public Map agrupaRelatorioTpfa_e_Aref(List listaAris, Collection periodosConsiderados ){
            /*
             * Map com a chave pela funcao de acompanhamento para organizacao das linhas
             * por tipo de funcao de acompanhamento
             */
            Map mapTpfaReferenciaRelatorio = new HashMap<TipoFuncAcompTpfa, Map>();
            int numPeriodos = periodosConsiderados.size();
            
            /*
             * Map para organizao das colunas de referencia
             */
            Map mapReferenciaRelatorio = null;
            
            for (Iterator itAris = listaAris.iterator(); itAris.hasNext();) {
            	AcompReferenciaItemAri ari =  (AcompReferenciaItemAri) itAris.next();
                Set arels =  ari.getAcompRelatorioArels() ;
                
                for(Iterator itArels = arels.iterator(); itArels.hasNext();){
                	AcompRelatorioArel arel = (AcompRelatorioArel) itArels.next();
                	
                	/*if (!mapTpfaReferenciaRelatorio .containsKey(arel.getTipoFuncAcompTpfa().getLabelPosicaoTpfa() ) ){
                		mapTpfaReferenciaRelatorio .put(arel.getTipoFuncAcompTpfa().getLabelPosicaoTpfa(), new HashMap< Long ,AcompRelatorioArel>(numPeriodos) );
                	}*/
                	if (!mapTpfaReferenciaRelatorio .containsKey(arel.getTipoFuncAcompTpfa() ) ){
                		mapTpfaReferenciaRelatorio .put(arel.getTipoFuncAcompTpfa(), new HashMap< Long ,AcompRelatorioArel>(numPeriodos) );
                	} 
                	
                	Map mapTpfaRef = (Map) mapTpfaReferenciaRelatorio.get(arel.getTipoFuncAcompTpfa());
                		
                	if (mapTpfaRef!=null && !mapTpfaRef.containsKey(arel.getAcompReferenciaItemAri().getAcompReferenciaAref() ) ){
                		mapTpfaRef.put(arel.getAcompReferenciaItemAri().getAcompReferenciaAref(), arel);
					} 
                	
                }
            }
            return mapTpfaReferenciaRelatorio ;
            
        }
        
        /**
         * Returna um boolean que indica se o usu�rio � administrador de um acompanhamento que ainda
         * n�o foi liberado
         * 
         * @param itemEstrutura ItemEstruturaIett
         * @param gruposUsuario Grupos os quais o usu�rio pertence
         * @param acompAri AcompReferenciaItemAri
         * @param tipoAcompanhamento TipoAcompanhamentoTa tipo de acompanhament escolhido
         * @return boolean
         * @throws ECARException
         */
        private boolean usuarioAdministraAcompanhamentoNaoLiberado(
        		ItemEstruturaIett itemEstrutura,    		
        		Set gruposUsuario,
        		AcompReferenciaItemAri acompAri,
        		TipoAcompanhamentoTa tipoAcompanhamento) throws ECARException{
        	
        	boolean usuarioAdministraAcompanhamentoNaoLiberado = false;
        	Boolean administradorAcompanhamento = new Boolean(false);
        	
        	String exigeLiberarAcompanhamento = tipoAcompanhamento.getIndLiberarAcompTa();
        	
        	//verifica se o usuario � administrador do tipo de acompanhamento    	
        	administradorAcompanhamento = new Boolean (validaPermissao.permissaoAcessoReferencia(acompAri.getAcompReferenciaAref().getTipoAcompanhamentoTa(), gruposUsuario ));
        	
     //      	administradorAcompanhamento = new Boolean (validaPermissao.permissaoAcessoReferencia(tipoAcompanhamento, gruposUsuario ));
        	    	    	
        	if (administradorAcompanhamento && exigeLiberarAcompanhamento.equals("S")){
        		
        		StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) buscar(StatusRelatorioSrl.class,Long.valueOf(STATUS_LIBERADO));

        		if (!acompAri.getStatusRelatorioSrl().equals(statusLiberado)){
        			
        			usuarioAdministraAcompanhamentoNaoLiberado = true;
        		}
        	}
        	
        	return usuarioAdministraAcompanhamentoNaoLiberado;
        }
        
        /**
         * Retorna um booleano que indica se o usu�rio logado pode emitir parecer do �tem de um acompanhamento
         * para determinada refer�ncia escolhida e se o parecer ainda n�o foi liberado
         * 
         * @param itemEstrutura ItemEstruturaIett
         * @param tpfaOrdenadosPorEstrutura Lista de tipos de fun��o de acompanhamento ordenadas
         * @param usuario Usu�rio logado no sistema
         * @param acompAri AcomReferenciaItemAri
         * @return boolean 
         * @throws ECARException
         */
        private boolean usuarioLogadoEmiteParecerNaoLiberado(
        		ItemEstruturaIett itemEstrutura,   		
        		List tpfaOrdenadosPorEstrutura,
        		UsuarioUsu usuario,
        		AcompReferenciaItemAri acompAri) throws ECARException{
    	    	
        	boolean usuarioLogadoEmiteParecerNaoLiberado = false;
        	boolean usuarioLogadoEmiteParecer = false;
        	
        	//Dao do objeto principal(ItemEstUsutpfuac) onde veremos a permiss�o do usu�rio/grupo 
    		ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(request);																					
//    		Iterator itPeriodosAcao = periodosConsiderados.iterator();					
//    		Map  mapAcao = criarMapPeriodoAri(periodosConsiderados, itemEstrutura);
    				
//    		if(itPeriodosAcao.hasNext()) {
    			//Pega s� o per�odo selecionado (Aref), que � o primeiro
//    			AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodosAcao.next();
//    			if(!mapAcao.isEmpty() && mapAcao.containsKey(acompReferencia)) {
//    				AcompReferenciaItemAri ariAcao = (AcompReferenciaItemAri) mapAcao.get(acompReferencia);
    				
    				//Pega os Arels do Ari selecionado 
    				List relatorios = getAcompRelatorioArelOrderByFuncaoAcomp(acompAri, tpfaOrdenadosPorEstrutura);
    				Iterator itRelatorios = relatorios.iterator();
    				
    				while(itRelatorios.hasNext()){												
    					AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();										
    					
    					ItemEstUsutpfuacIettutfa itemEstUsu = null;
    					
    					if (relatorio.getTipoFuncAcompTpfa() != null){
    						
    						try{
	    					itemEstUsu 
	    							= itemEstUsuDao.buscar(itemEstrutura.getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
    						}
    						catch(ECARException e){
    							Logger.getLogger(this.getClass()).error(e);
    			//				e.printStackTrace();
    						}
	    					//Verifica se a permiss�o � de grupo ou usu�rio							 							
	    					if (itemEstUsu != null && itemEstUsu.getUsuarioUsu() != null) {
	    						usuarioLogadoEmiteParecer = itemEstUsu.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
	    						
	    						if (usuarioLogadoEmiteParecer && !"S".equals(relatorio.getIndLiberadoArel())){
	    							usuarioLogadoEmiteParecerNaoLiberado = true;
	    							break;
	    						}
	    					} 
	    					else if (itemEstUsu != null && itemEstUsu.getSisAtributoSatb() != null) {
	    						if (itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
	    							Iterator itUsuarios = itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
	    							while (itUsuarios.hasNext()) {
	    								UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
	    								if (usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
	    									
	    									if(!"S".equals(relatorio.getIndLiberadoArel())){
	    							    		usuarioLogadoEmiteParecerNaoLiberado = true;
	    							    		break;
	    							        }
	    																		
	    								}
	    							}
	    						}
	    					}
    					}
   // 					else
   // 						continue;
    				}//fecha while relat�rios																
    	//		}
//    		}    	
    	    	
        	    	
        	return usuarioLogadoEmiteParecerNaoLiberado;
        }
        
        
        /**
         * Retorna um booleano que indica se o usu�rio logado pode emitir parecer do �tem de um acompanhamento
         * para determinada refer�ncia escolhida
         * 
         * @param itemEstrutura ItemEstruturaIett
         * @param tpfaOrdenadosPorEstrutura Lista de tipos de fun��o de acompanhamento ordenadas
         * @param usuario Usu�rio logado no sistema
         * @param acompAri AcomReferenciaItemAri
         * @return boolean 
         * @throws ECARException
         */
        private boolean usuarioLogadoEmiteParecer(
        		ItemEstruturaIett itemEstrutura,    		
        		List tpfaOrdenadosPorEstrutura,
        		UsuarioUsu usuario,
        		AcompReferenciaItemAri acompAri) throws ECARException{
        	
        	//Dao do objeto principal(ItemEstUsutpfuac) onde veremos a permiss�o do usu�rio/grupo 
    		ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(request);																					
//    		Iterator itPeriodosAcao = periodosConsiderados.iterator();					
//    		Map  mapAcao = criarMapPeriodoAri(periodosConsiderados, itemEstrutura);

    		boolean usuarioLogadoEmiteParecer = false;
    		
//    		if(itPeriodosAcao.hasNext()) {
    			//Pega s� o per�odo selecionado (Aref), que � o primeiro
//    			AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodosAcao.next();
//    			if(!mapAcao.isEmpty() && mapAcao.containsKey(acompReferencia)) {
//    				AcompReferenciaItemAri ariAcao = (AcompReferenciaItemAri) mapAcao.get(acompReferencia);
    				
    				//Pega os Arels do Ari selecionado 
    				List relatorios = getAcompRelatorioArelOrderByFuncaoAcomp(acompAri, tpfaOrdenadosPorEstrutura);
    				Iterator itRelatorios = relatorios.iterator();
    				
    				while(itRelatorios.hasNext()){												
    					AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();										
    					
    					ItemEstUsutpfuacIettutfa itemEstUsu;
    					
    					
    					if (relatorio.getTipoFuncAcompTpfa() != null){
	    					itemEstUsu 
	    							= itemEstUsuDao.buscar(itemEstrutura.getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
    					
    					 
    					//Verifica se a permiss�o � de grupo ou usu�rio							 							
    					if (itemEstUsu.getUsuarioUsu() != null) {
    						usuarioLogadoEmiteParecer = itemEstUsu.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
    					} else if (itemEstUsu.getSisAtributoSatb() != null) {
    						if (itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
    							Iterator itUsuarios = itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
    							while (itUsuarios.hasNext()) {
    								UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
    								if (usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
    									usuarioLogadoEmiteParecer = true;
    									break;
    								}
    							}
    						}
    					}
    					if(usuarioLogadoEmiteParecer==true)
    						break;
    					}
    					else
    						continue;
    				}//fecha while relat�rios																
//    			}
//    		}
    		
    		return usuarioLogadoEmiteParecer;
        }
        
        /**
         * Retira dos �tens retornados na consulta aqueles cujos acompanhamentos n�o podem ser editados
         * pelo usu�rio logado
         * 
         * @param itensEstrutura Collection
         * @param tipoAcompanhamento TipoAcompanhamentoTa Tipo acompanhamento padr�o
         * @param gruposUsuario Set
         * @param arefSelecionada AcompReferenciaAref Refer�ncia selecionada
         * @param periodosConsiderados Collection
         * @param tpfaOrdenadosPorEstrutura List Lista de tipos de fun��o de acompanhamento ordenados por estrutura
         * @param usuario UsuarioUsu usu�rio logado
         * @return Cole��o de �tens de estrutura
         * @throws ECARException
         */
        private Collection aplicarMinhaVisao(
        		Collection itensEstrutura, 
        		TipoAcompanhamentoTa tipoAcompanhamento, 
        		Set gruposUsuario,
        		AcompReferenciaAref arefSelecionada,        		
        		List tpfaOrdenadosPorEstrutura,
        		UsuarioUsu usuario,
        		OrgaoOrg orgao) throws ECARException {
        	try{
        	Iterator itItensEstrutura = itensEstrutura.iterator();    	
        	ItemEstruturaIett itemEstrutura;
        	AtributoEstruturaListagemItens atributoEstruturaListagemItens;
        	EstruturaAcessoDao estruturaAcessoDao = new EstruturaAcessoDao(null);
        	
        	//verifica se o usu�rio pode "Gerar Per�odo de Acompanhamento"
        	boolean permissaoAdministradorAcompanhamento = estruturaAcessoDao.temPermissoesAcessoAcomp(tipoAcompanhamento, gruposUsuario);
        
        	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
        	boolean usuarioLogadoEmiteParecer = false;
        	
        	while (itItensEstrutura.hasNext()) {
        		
        		atributoEstruturaListagemItens= (AtributoEstruturaListagemItens) itItensEstrutura.next(); 
        		itemEstrutura = atributoEstruturaListagemItens.getItem();
        		 
        		//se o �tem n�o � um setor
        		if (itemEstrutura.getNivelIett().intValue()>1){
        			
//        			AcompReferenciaItemAri acompAri = ariDao.getAcompReferenciaItemByItemEstruturaIett(arefSelecionada,itemEstrutura);
           			AcompReferenciaItemAri acompAri = ariDao.getAcompReferenciaItemByItemEstruturaIettOrgao(orgao, arefSelecionada,itemEstrutura);
        			
        			usuarioLogadoEmiteParecer = usuarioLogadoEmiteParecer(
    	    				itemEstrutura,	    				
    	    				tpfaOrdenadosPorEstrutura,
    	    	    		usuario,
    	    	    		acompAri
    	    	    		);
    	    		
    	    		boolean algumFilhoTemPermissaoEditar = false;    		    		
    	    		
    	    	
    	    		if((acompAri == null || (!usuarioLogadoEmiteParecer && !permissaoAdministradorAcompanhamento))){
    	    			
    	    			Set itensFilhos = itemEstrutura.getItemEstruturaIetts();
    	    			
    	    			//se o �tem possui filhos, remove o �tem apenas se os filhos tamb�m n�o podem ser editados
    	    			if (itensFilhos != null && itensFilhos.size() > 0){
    	    				
    	    				Iterator itItensFilhos = itensFilhos.iterator();
    	    				ItemEstruturaIett itemFilho;
    	    				boolean usuarioEmiteParecerFilho = false;
    	    				AcompReferenciaItemAri acompAriFilho;
    	    				
    	    				
    	    				while (itItensFilhos.hasNext()){
    	    					
    	    					itemFilho = (ItemEstruturaIett)itItensFilhos.next();
    	    					
    	    					acompAriFilho = 
    	    						ariDao.getAcompReferenciaItemByItemEstruturaIett(arefSelecionada,itemFilho);
    	    					
    	    					usuarioEmiteParecerFilho = 
    	    						usuarioLogadoEmiteParecer(
    	    								itemFilho,	    								 
    	    								tpfaOrdenadosPorEstrutura, 
    	    								usuario,
    	    								acompAriFilho);
    	    					
    	    					if((acompAriFilho != null && (usuarioEmiteParecerFilho || permissaoAdministradorAcompanhamento))){
    	    						
    	    						algumFilhoTemPermissaoEditar = true;
    	    					}
    	    				}
    	    				
    	    				 
    	    			}
    	    			
    	    			if (!algumFilhoTemPermissaoEditar)
    	    				itItensEstrutura.remove();
    	    		}
        		}
        	}
        	}catch (Exception e) {
				System.out.println(e.getMessage());
			}
        	return itensEstrutura;
        }
        
        /**
         * Remove os �tens de estrutura que o usu�rio logado n�o possua pend�ncias        
         * 
         * @param itensEstrutura
         * @param tipoAcompanhamento
         * @param gruposUsuario
         * @param arefSelecionada
         * @param tpfaOrdenadosPorEstrutura
         * @param usuario
         * @return Collection Cole��o de Itens de estrutura
         * @throws ECARException
         */
        private Collection aplicarMinhasPendencias(Collection itensEstrutura, 
    	    		TipoAcompanhamentoTa tipoAcompanhamento, 
    	    		Set gruposUsuario,
    	    		Collection periodosConsiderados,    	    		
    	    		List tpfaOrdenadosPorEstrutura,
    	    		UsuarioUsu usuario,
    	    		OrgaoOrg orgao
    	    		) throws ECARException{
        	
        	
        	Iterator itItensEstrutura = itensEstrutura.iterator();    	
        	ItemEstruturaIett itemEstrutura;
        	AtributoEstruturaListagemItens atributoEstruturaListagemItens;
        	AcompReferenciaItemAri acompAri;
        	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
        	        	
        	while (itItensEstrutura.hasNext()) {
        		
        		boolean usuarioLogadoEmiteParecerNaoLiberado = false;
            	boolean usuarioAdministraAcompanhamentoNaoLiberado = false;
            	boolean acompanhamentoLiberado = false;
        		
            	boolean algumFilhoTemPendencias = false;
            	boolean itemPossuiPendencias = false;
            	            	
        		atributoEstruturaListagemItens= (AtributoEstruturaListagemItens) itItensEstrutura.next(); 
        		itemEstrutura = atributoEstruturaListagemItens.getItem();
        		
        		//se o �tem n�o � um setor
	//        		if (itemEstrutura.getNivelIett().intValue()>1){
	        		
	        	Iterator itPeriodos = periodosConsiderados.iterator();
	        		
	        	while (itPeriodos.hasNext()){
	        			        			        		
	        		AcompReferenciaAref arefCorrente = (AcompReferenciaAref)itPeriodos.next();
	        		
//	        		acompAri = ariDao.getAcompReferenciaItemByItemEstruturaIett(arefCorrente, itemEstrutura);
	        		acompAri = ariDao.getAcompReferenciaItemByItemEstruturaIettOrgao(orgao, arefCorrente, itemEstrutura);
	        			        		
	        		if (acompAri != null){
	        		
	    	    		usuarioLogadoEmiteParecerNaoLiberado = 
	    	    			usuarioLogadoEmiteParecerNaoLiberado(
	    	    					itemEstrutura,     					 
	    	    					tpfaOrdenadosPorEstrutura, 
	    	    					usuario,
	    	    					acompAri);
	    	    		
	    	    		usuarioAdministraAcompanhamentoNaoLiberado = 
	    	    			usuarioAdministraAcompanhamentoNaoLiberado(
	    	    					itemEstrutura,    					    					
	    	    					gruposUsuario,
	    	    					acompAri,
	    	    					tipoAcompanhamento);
	    	    		
	    	    		StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) buscar(StatusRelatorioSrl.class,Long.valueOf(STATUS_LIBERADO));
	    	    		
	            		if (acompAri.getStatusRelatorioSrl().equals(statusLiberado)){
	            			
	            			acompanhamentoLiberado = true;	            			
	            			//break;
	            		}
	    	    		
	    	    		if ((usuarioLogadoEmiteParecerNaoLiberado || usuarioAdministraAcompanhamentoNaoLiberado) &&
	    	    				!acompanhamentoLiberado){
	    	    			itemPossuiPendencias = true;
	    	    			break;
	    	    		}
	    	    		
	    	    		
	        		}
	        		
	        		//Se n�o existe acompanhamento, ou o acompanhamento foi liberado, ou o usu�rio nem � administrador do
	        		//acompanhamento n�o liberado nem pode emitir parecer para o acompanhamento, remove o �tem da lista se
	        		//este n�o tiver algum �tem filho com pend�ncias
	        		if(acompAri == null || acompanhamentoLiberado ||
	        				(!usuarioAdministraAcompanhamentoNaoLiberado && !usuarioLogadoEmiteParecerNaoLiberado)){
	        			
	        			Set itensFilhosImediatos = itemEstrutura.getItemEstruturaIetts();
	        			
	        			//se o �tem possui filhos, remove o �tem apenas se os filhos tamb�m n�o possuem algum
	        			//parecer n�o liberado ou se o n�o acompanhamento exija libera��o ou exija mas ja tenha
	        			//sido liberado
	        			if (itensFilhosImediatos != null && itensFilhosImediatos.size() > 0){
	        				
	        				algumFilhoTemPendencias = verificarFilhos(itensFilhosImediatos, 
	        						tipoAcompanhamento,
	        						gruposUsuario,
	        						arefCorrente,
	        						tpfaOrdenadosPorEstrutura,
	        						usuario,
	        						ariDao);
	        				        	
	        				if (algumFilhoTemPendencias)
	        					break;
	        			}
	        			
	        			
	        		}
	        		
	        	}// fim do while periodos
	        	
	        	if (!itemPossuiPendencias && !algumFilhoTemPendencias)
    				itItensEstrutura.remove();
    		}//fim do while itens
        	
        	return itensEstrutura;
        }
        
        private boolean verificarFilhos(Set itensFilhos,
        		TipoAcompanhamentoTa tipoAcompanhamento, 
	    		Set gruposUsuario,
	    		AcompReferenciaAref arefCorrente,    	    		
	    		List tpfaOrdenadosPorEstrutura,
	    		UsuarioUsu usuario,
        		AcompReferenciaItemDao ariDao) throws ECARException{
        	
        	Iterator itItensFilhos = itensFilhos.iterator();
			ItemEstruturaIett itemFilho;			
			AcompReferenciaItemAri acompAriFilho;			
			
			boolean retorno = false;
			
			while (itItensFilhos.hasNext()){
				
				boolean usuarioLogadoEmiteParecerNaoLiberadoFilho = false;
				boolean usuarioAdministraAcompanhamentoNaoLiberadoFilho = false;
				boolean acompanhamentoLiberado = false;
				
				itemFilho = (ItemEstruturaIett)itItensFilhos.next();
				
				acompAriFilho = 
					ariDao.getAcompReferenciaItemByItemEstruturaIett(arefCorrente,itemFilho);
				
				if (acompAriFilho != null){
				
					usuarioLogadoEmiteParecerNaoLiberadoFilho = 
		    			usuarioLogadoEmiteParecerNaoLiberado(
		    					itemFilho, 
		    					tpfaOrdenadosPorEstrutura, 
		    					usuario,
		    					acompAriFilho);
		    		
		    		usuarioAdministraAcompanhamentoNaoLiberadoFilho = 
		    			usuarioAdministraAcompanhamentoNaoLiberado(
		    					itemFilho,    		    					
		    					gruposUsuario,
		    					acompAriFilho,
		    					tipoAcompanhamento);
		    		
		    		StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) buscar(StatusRelatorioSrl.class,Long.valueOf(STATUS_LIBERADO));

            		if (acompAriFilho.getStatusRelatorioSrl().equals(statusLiberado)){
            			
            			acompanhamentoLiberado = true;
            		}
				}
				
				if(acompAriFilho != null && !acompanhamentoLiberado){
					if(usuarioAdministraAcompanhamentoNaoLiberadoFilho || usuarioLogadoEmiteParecerNaoLiberadoFilho){
					
						return true;
					}
				}
				else{
					     
					
        			//usa recursividade para verificar os filhos at� o �ltimo n�vel
        			if (itemFilho.getItemEstruturaIetts() != null && itemFilho.getItemEstruturaIetts().size() > 0){
        				retorno = verificarFilhos(
        						itemFilho.getItemEstruturaIetts(), 
        						tipoAcompanhamento, 
        						gruposUsuario, 
        						arefCorrente, 
        						tpfaOrdenadosPorEstrutura, 
        						usuario, 
        						ariDao);
        				
        				if (retorno == true)
        					break;
        			}
				}
			}
			
			return retorno;
        }
        
        /**
         * Retorna lista de itens de acompanhamentos que o usu�rio logado tenha algum parecer ainda n�o
         * liberado e/ou todos os itens de acompanhamentos em que o usu�rio � administrador
         * e que o acompanhamento exija liberar o acompanhamento (definido em tipo de acompanhamento),
         * e o acompanhamento ainda n�o tenha sido liberado
         * 
         * @param periodosConsiderados Collection de AcompReferenciaAref
    	 * @param niveisPlanejamento Collection
    	 * @param orgaoResponsavel OrgaoOrg
    	 * @param usuarioUsu UsuarioUsu
    	 * @param gruposUsuario Set
    	 * @param tipoAcompanhamento TipoAcompanhamentoTA (se for nulo ignora o tipo de acompanhamento)
    	 * @param codIettPai Long (se for diferente de nulo, obt�m os filhos desse item)
    	 * @param arefSelecionada AcompReferenciaAref
    	 * @param tpfaOrdenadosPorEstrutura lista de tipos de fun��o de acompanhamento ordenados por estrutura
    	 * @param itensSemInformacaoNivelPlanejamento Boolean
    	 * @param codCor Long c�digo da cor selecionada (ignora se n�o foi selecionada cor)
    	 * @param indLiberados Long
    	 * @param menorNivel int o menor n�vel dos itens de estrutura
    	 * @param nuPaginaSelecionada int
    	 * @return
    	 * @throws ECARException
         */
        public Object[] getItensAcompanhamentoComPendencias(Collection periodosConsiderados,
        		Collection niveisPlanejamento, OrgaoOrg orgao, UsuarioUsu usuarioUsu,
        		Set gruposUsuario, TipoAcompanhamentoTa tipoAcompanhamento, Long codIettPai,
        		AcompReferenciaAref arefSelecionada, List tpfaOrdenadosPorEstrutura,
        		Boolean itensSemInformacaoNivelPlanejamento, Long codCor, String indLiberados, 
        		int menorNivel, int nuPaginaSelecionada) 
        			throws ECARException {
        	
        	try{
        	
        	Object[] itensAcompanhamentoMinhasPendencias = getItensAcompanhamentoInPeriodosByOrgaoRespPaginado(
    				periodosConsiderados, niveisPlanejamento, orgao, usuarioUsu,
    				gruposUsuario, tipoAcompanhamento.getCodTa().longValue(), codIettPai,
    				itensSemInformacaoNivelPlanejamento, codCor, indLiberados,
    				menorNivel, nuPaginaSelecionada, null, false);
        	
        	itensAcompanhamentoMinhasPendencias[0] = 
    			aplicarMinhasPendencias(
    					(Collection)itensAcompanhamentoMinhasPendencias[0], 
    					tipoAcompanhamento, 
    					gruposUsuario, 
    					periodosConsiderados,    					
    					tpfaOrdenadosPorEstrutura,
    					usuarioUsu,
    					orgao);
    		    		    		
    		return itensAcompanhamentoMinhasPendencias;
    		
    		} catch(HibernateException e){
    	    	this.logger.error(e);
    	        throw new ECARException(e);            
    	    }
        }
        
            
    	/**
    	 * Retorna lista de itens de acompanhamentos que o usu�rio logado tenha permiss�o para editar algo
    	 * 
    	 * @param periodosConsiderados Collection de AcompReferenciaAref
    	 * @param niveisPlanejamento Collection
    	 * @param orgaoResponsavel OrgaoOrg
    	 * @param usuarioUsu UsuarioUsu
    	 * @param gruposUsuario Set
    	 * @param tipoAcompanhamento TipoAcompanhamentoTA (se for nulo ignora o tipo de acompanhamento)
    	 * @param codIettPai Long (se for diferente de nulo, obt�m os filhos desse item)
    	 * @param arefSelecionada AcompReferenciaAref
    	 * @param tpfaOrdenadosPorEstrutura lista de tipos de fun��o de acompanhamento ordenados por estrutura
    	 * @param itensSemInformacaoNivelPlanejamento Boolean
    	 * @param codCor Long c�digo da cor selecionada (ignora se n�o foi selecionada cor)
    	 * @param indLiberados Long
    	 * @param menorNivel int o menor n�vel dos itens de estrutura
    	 * @param nuPaginaSelecionada int
    	 * @return
    	 * @throws ECARException
    	 */
        public Object[] getItensAcompanhamentoComPermissaoAlteracao(Collection periodosConsiderados,
        		Collection niveisPlanejamento, OrgaoOrg orgao, UsuarioUsu usuarioUsu,
        		Set gruposUsuario, TipoAcompanhamentoTa tipoAcompanhamento, Long codIettPai,
        		AcompReferenciaAref arefSelecionada, List tpfaOrdenadosPorEstrutura,
        		Boolean itensSemInformacaoNivelPlanejamento, Long codCor, String indLiberados, 
        		int menorNivel, int nuPaginaSelecionada) 
        			throws ECARException {
        	
        	try{ 
        		    		
        		
        		Object[] itensAcompanhamentoMinhaVisao = getItensAcompanhamentoInPeriodosByOrgaoRespPaginado(
        				periodosConsiderados, niveisPlanejamento, orgao, usuarioUsu,
        				gruposUsuario, tipoAcompanhamento.getCodTa().longValue(), codIettPai,
        				itensSemInformacaoNivelPlanejamento, codCor, indLiberados,
        				menorNivel, nuPaginaSelecionada, null, false);
        		
        		itensAcompanhamentoMinhaVisao[0] = 
        			aplicarMinhaVisao(
        					(Collection)itensAcompanhamentoMinhaVisao[0], 
        					tipoAcompanhamento, 
        					gruposUsuario, 
        					arefSelecionada,        					
        					tpfaOrdenadosPorEstrutura,
        					usuarioUsu,
        					orgao);
        		    		    		
        		return itensAcompanhamentoMinhaVisao;
        		
        	} catch(HibernateException e){
            	this.logger.error(e);
                throw new ECARException(e);            
            } catch(Exception e){
            	this.logger.error(e);
                throw new ECARException(e);            
            }
        }
        
        
        /**
    	 * Filtra o item de acordo com o campo passado como par�metro
    	 * 
    	 * @param request HttpServletRequest
    	 * @param campo String
    	 * @param iett ItemEstruturaIett
    	 * @param itensRemovidos List
    	 * @param itItensEstrutura Iterator
    	 * @return true se o item foi exclu�do, false se n�o foi
    	 * @throws ECARException
    	 */
        private boolean filtrarItemString(HttpServletRequest request, String campo, ItemEstruturaIett iett, List itensRemovidos, Iterator itItensEstrutura) {
        	boolean retorno = false;
        	String valorCampo = Pagina.getParamStr(request, iett.getEstruturaEtt().getCodEtt() + "_" + campo);
        	try {
        			
        		if (!"".equals(valorCampo)) {
        				
		        	String valorCampoIett = (String) iett.getClass().getMethod("get" + Util.primeiraLetraToUpperCase(campo), null).invoke(iett, null);  
		            	 
		        	if (valorCampoIett == null || !valorCampoIett.toUpperCase().contains((valorCampo.toUpperCase()))){
		        		itensRemovidos.add(iett.getCodIett());
		        		itItensEstrutura.remove();
		        		retorno = true;
		        	}
		        }
        	} catch (Exception e) {
				// TODO: handle exception
			}
        	
    		return retorno;		
    	}
        
        /**
    	 * Filtra o item de acordo com o campo passado como par�metro.
    	 * 
    	 * @param request HttpServletRequest
    	 * @param campo String
    	 * @param iett ItemEstruturaIett
    	 * @param itensRemovidos List
    	 * @param itItensEstrutura Iterator
    	 * @return true se o item foi exclu�do, false se n�o foi
    	 * @throws ECARException
    	 */
        private boolean filtrarItemStringComoListaChecks(HttpServletRequest request, String campo, ItemEstruturaIett iett, List itensRemovidos, Iterator itItensEstrutura) {
        	boolean retorno = false;

        	String valorCampo[] = Pagina.getParamLista(request, iett.getEstruturaEtt().getCodEtt() + "_" + campo);
        	boolean diferente = true;
        	try {
        		
        		if (valorCampo!=null) {
	        		if(valorCampo.length==1) {
		        		if (!"".equals(valorCampo[0])) {
		        		
		        			String valorCampoIett = (String) iett.getClass().getMethod("get" + Util.primeiraLetraToUpperCase(campo), null).invoke(iett, null);  
		            	 
		    				if (valorCampoIett == null || !valorCampoIett.toUpperCase().contains((valorCampo[0].toUpperCase()))){
		    					itensRemovidos.add(iett.getCodIett());
		            			itItensEstrutura.remove();
		            			retorno = true;
		            		}
		    			}
	        		} else {
	        				// � implementado "ou" para valores do check, quando "valorCampoIett" for diferente em rela��o a todos os valores do check, a� removi da lista
		        			for (int i=0; i<valorCampo.length;i++) {
		        				if (valorCampo[i]!=null && !"".equals(valorCampo[i])) {
				        			String valorCampoIett = (String) iett.getClass().getMethod("get" + Util.primeiraLetraToUpperCase(campo), null).invoke(iett, null);  
				            	 
				        			if (diferente && !valorCampoIett.toUpperCase().contains((valorCampo[i].toUpperCase()))) {
				        				diferente = true;
				        			} else {
				        				diferente = false;
				        			}
				    			}	
		        			}
		        			if (diferente){
		    					itensRemovidos.add(iett.getCodIett());
		            			itItensEstrutura.remove();
		            			retorno = true;
		            		}
	        		}
        		}
        	} catch (Exception e) {
				// TODO: handle exception
			}
        	
    		return retorno;		
    	}
        
        /**
    	 * Filtra o item de acordo com o campo passado como par�metro
    	 * 
    	 * @param request HttpServletRequest
    	 * @param campo String
    	 * @param iett ItemEstruturaIett
    	 * @param itensRemovidos List
    	 * @param itItensEstrutura Iterator
    	 * @return true se o item foi exclu�do, false se n�o foi
    	 * @throws ECARException
    	 */
        private boolean filtrarItemData(HttpServletRequest request, String campo, ItemEstruturaIett iett, List itensRemovidos, Iterator itItensEstrutura, String inicioFim) {
        	boolean retorno = false;
        	String valorCampo = Pagina.getParamStr(request, iett.getEstruturaEtt().getCodEtt() + "_" + campo + "_" + inicioFim);
        	try {
        		
        		if (!"".equals(valorCampo)) {
        			//Modifica as ordens de dia e m�s na data, para ficar no formato MM/DD/YYYY
        			valorCampo = 
        				valorCampo.substring(3, valorCampo.lastIndexOf("/")+1) +
        				valorCampo.substring(0, valorCampo.indexOf("/")+1) +
        				valorCampo.substring(valorCampo.lastIndexOf("/")+1);
        			
        			Date valorCampoData = new Date(valorCampo);
        			
        			Date valorCampoDataIett = (Date) iett.getClass().getMethod("get" + Util.primeiraLetraToUpperCase(campo), null).invoke(iett, null);
        			
        			if(valorCampoDataIett != null){
	        			Calendar dataCampoDataIett = Data.getCalendar(valorCampoDataIett);
	        			dataCampoDataIett.set(Calendar.HOUR_OF_DAY, 0);
	        			dataCampoDataIett.set(Calendar.MINUTE, 0);
	        			dataCampoDataIett.set(Calendar.SECOND, 0);
	        			dataCampoDataIett.set(Calendar.MILLISECOND, 0);
	        			valorCampoDataIett = dataCampoDataIett.getTime();
        			}
        			
        			if (inicioFim.equals("Inicio")){
        				//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
        				if (valorCampoDataIett == null || valorCampoDataIett.compareTo(valorCampoData) < 0){
        					itensRemovidos.add(iett.getCodIett());
                			itItensEstrutura.remove();
                			retorno = true;
                		}
        			} else if (inicioFim.equals("Fim")){
        				//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
        				if (valorCampoDataIett == null || valorCampoDataIett.compareTo(valorCampoData) > 0){
        					itensRemovidos.add(iett.getCodIett());
                			itItensEstrutura.remove();
                			retorno = true;
                		}
        			}
    				
    			}
        	} catch (Exception e) {
				// TODO: handle exception
			}
        	
    		return retorno;		
    	}
        
        /**
    	 * Filtra o item de acordo com o campo passado como par�metro
    	 * 
    	 * @param request HttpServletRequest
    	 * @param campo String
    	 * @param iett ItemEstruturaIett
    	 * @param itensRemovidos List
    	 * @param itItensEstrutura Iterator
    	 * @return true se o item foi exclu�do, false se n�o foi
    	 * @throws ECARException
    	 */
        private boolean filtrarItemValor(HttpServletRequest request, String campo, ItemEstruturaIett iett, List itensRemovidos, Iterator itItensEstrutura, String inicioFim) {
        	boolean retorno = false;
        	String valorCampo = Pagina.getParamStr(request, iett.getEstruturaEtt().getCodEtt() + "_" + campo + "_" + inicioFim);
        	try {
        		
        		if (!"".equals(valorCampo)) {
        			
        			BigDecimal valorCampoBigDecimal = new BigDecimal(valorCampo);
        			
					BigDecimal valorCampoBigDecimalIett = (BigDecimal) iett.getClass().getMethod("get" + Util.primeiraLetraToUpperCase(campo), null).invoke(iett, null);
        			
					if (inicioFim.equals("Inicio")){
						if (valorCampoBigDecimalIett == null || 
								//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
								valorCampoBigDecimalIett.doubleValue() < valorCampoBigDecimal.doubleValue()){
							itensRemovidos.add(iett.getCodIett());
	            			itItensEstrutura.remove();
	            			retorno = true;
	            		}
					} else if (inicioFim.equals("Fim")){
						if (valorCampoBigDecimalIett == null || 
								//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
								valorCampoBigDecimalIett.doubleValue() > valorCampoBigDecimal.doubleValue()){
							itensRemovidos.add(iett.getCodIett());
	            			itItensEstrutura.remove();
	            			retorno = true;
	            		}
					}
					
    			}      		
        	} catch (Exception e) {
				// TODO: handle exception
			}
        	
    		return retorno;		
    	}
        
        
        /**
    	 * Filtra o item de acordo com os atributos livres passados como par�metro
    	 * 
    	 * @param request HttpServletRequest
    	 * @param iett ItemEstruturaIett
    	 * @param itensRemovidos List
    	 * @param itItensEstrutura Iterator
    	 * @param atributosLivres List
    	 * @return true se o item foi exclu�do, false se n�o foi
    	 * @throws ECARException
    	 */
        private boolean filtrarAtributosLivres(HttpServletRequest request, ItemEstruturaIett iett, List itensRemovidos, Iterator itItensEstrutura, List atributosLivres) {
        	boolean retorno = false;
        	try {
        		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
        		String codEtt = iett.getEstruturaEtt().getCodEtt().toString();
        		boolean filtrarAtributoLivre = true;
				Iterator itAtributos = atributosLivres.iterator();
				//Filtrar pelos atributos livres definidos como filtros
				while (itAtributos.hasNext() && filtrarAtributoLivre){
					ObjetoEstrutura objetoEstrutura = (ObjetoEstrutura) itAtributos.next();
					SisGrupoAtributoSga grupoAtributo = objetoEstrutura.iGetGrupoAtributosLivres();
					
					String nomeCampo = codEtt + "_a" + grupoAtributo.getCodSga();
		    		String tipoCampo = grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString();
		    		
		    		//Se for CheckBox ou RadioButton ou ListBox, n�o procura em InformacaoIettSatb
		    		if(tipoCampo.equals(SisTipoExibicGrupoDao.CHECKBOX) || tipoCampo.equals(SisTipoExibicGrupoDao.LISTBOX)){
		    			String[] atributos = request.getParameterValues(nomeCampo);
		    			int numAtributos = 0;
		    			if (atributos != null) {
		    				numAtributos = atributos.length;
		    			}
		    			for(int i = 0; i < numAtributos && filtrarAtributoLivre; i++){
		    				boolean diferente = true;
		    				List listaSisAtributosIett = itemEstruturaDao.getSisAtributosIett(iett, grupoAtributo.getCodSga());
		    				if (diferente && listaSisAtributosIett!= null && !listaSisAtributosIett.contains((SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(atributos[i])))){
		    					diferente = true;
		        			} else {
		        				diferente = false;
		        			}
		    				
		    				if (diferente) {
		    					itensRemovidos.add(iett.getCodIett());
	                			itItensEstrutura.remove();
	                			filtrarAtributoLivre = false;
	                			retorno = true;
	                			break;
		    				}
		    			}
		    		}
		    		//Se for Radio Button...
		    		else if(tipoCampo.equals(SisTipoExibicGrupoDao.RADIO_BUTTON) || tipoCampo.equals(SisTipoExibicGrupoDao.COMBOBOX)){

		    			String[] atributos = request.getParameterValues(nomeCampo);
		    			
		    			
		    			if (atributos== null || atributos.length==1) {
			    			if(!"".equals(Pagina.getParamStr(request, nomeCampo))){
			    				List listaSisAtributosIett = itemEstruturaDao.getSisAtributosIett(iett, grupoAtributo.getCodSga());
			    				if (listaSisAtributosIett== null || !listaSisAtributosIett.contains((SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(Pagina.getParamStr(request, nomeCampo))))){
			    					itensRemovidos.add(iett.getCodIett());
		                			itItensEstrutura.remove();
		                			filtrarAtributoLivre = false;
		                			retorno = true;
		                			break;
			    				} 		    				
	   		    			}
		    			} else {
		    				
		    				boolean diferente = true;
		    				for (int i=0; i<atributos.length;i++) {
		        				if (!"".equals(atributos[i])) {
		        					List listaSisAtributosIett = itemEstruturaDao.getSisAtributosIett(iett, grupoAtributo.getCodSga());
				        			if (diferente && listaSisAtributosIett!= null && !listaSisAtributosIett.contains((SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(atributos[i])))) {
				        				diferente = true;
				        			} else {
				        				diferente = false;
				        			}
				    			}	
		        			}
		    				// se o valor do atributo livre for diferente em rela��o a todos valores escolhidos pelo usuario 
		        			if (diferente){
		        				itensRemovidos.add(iett.getCodIett());
	                			itItensEstrutura.remove();
	                			filtrarAtributoLivre = false;
	                			retorno = true;
	                			break;
		            		}
		    			}
		    		}
		    		//Se for TEXT Field ou TEXT AREA
		    		else if (tipoCampo.equals(SisTipoExibicGrupoDao.TEXT) || tipoCampo.equals(SisTipoExibicGrupoDao.TEXTAREA)) {
		    			
		    			if(!"".equals(Pagina.getParamStr(request, nomeCampo))){
		    				List atributosTextTextArea = itemEstruturaDao.getItemEstruturaSisAtributoIettSatbsIett(iett, grupoAtributo.getCodSga());
		    				if (atributosTextTextArea.size() == 0){
		    					itensRemovidos.add(iett.getCodIett());
	                			itItensEstrutura.remove();
	                			filtrarAtributoLivre = false;
	                			retorno = true;
	                			break;
		    				}
		    				
		    				Iterator itAtributosTextTextArea = atributosTextTextArea.iterator();

		    				while (itAtributosTextTextArea.hasNext() && filtrarAtributoLivre){
		    					ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoIettSatb = (ItemEstruturaSisAtributoIettSatb) itAtributosTextTextArea.next();
		    					if (!itemEstruturaSisAtributoIettSatb.getInformacao().toUpperCase().trim().contains(Pagina.getParamStr(request, nomeCampo).toUpperCase().trim())){
		    						itensRemovidos.add(iett.getCodIett());
    	                			itItensEstrutura.remove();
    	                			filtrarAtributoLivre = false;
    	                			retorno = true;
    	                			break;
		    					}
		    					
		    				}
		    			}
		    		//Se for MULTITEXTO
		    		} else if (tipoCampo.equals(SisTipoExibicGrupoDao.MULTITEXTO)) {
						Enumeration lAtrib = request.getParameterNames();
						while (lAtrib.hasMoreElements() && filtrarAtributoLivre) {
							nomeCampo = (String) lAtrib.nextElement();
							if (nomeCampo.startsWith(codEtt + "_a" + grupoAtributo.getCodSga().toString())){
								if(!"".equals(Pagina.getParamStr(request, nomeCampo))){
									String codSisAtrib = nomeCampo.substring(nomeCampo.lastIndexOf("_") + 1);
									SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(codSisAtrib));
									List atributosMultiText = itemEstruturaDao.getItemEstruturaSisAtributoIettSatbsIett(iett, grupoAtributo.getCodSga());
									if (atributosMultiText.size() == 0 || !itemEstruturaDao.getSisAtributosIett(iett, grupoAtributo.getCodSga()).contains(sisAtributoSatb)){
										itensRemovidos.add(iett.getCodIett());
        	                			itItensEstrutura.remove();
        	                			filtrarAtributoLivre = false;
        	                			retorno = true;
        	                			break;
									}
									Iterator itAtributosMultiTexto = atributosMultiText.iterator();
    								
        		    				while (itAtributosMultiTexto.hasNext() && filtrarAtributoLivre){
        		    					ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoIettSatb = (ItemEstruturaSisAtributoIettSatb) itAtributosMultiTexto.next();
        		    					if (itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getCodSatb().toString().equals(codSisAtrib)){
        		    						if (!itemEstruturaSisAtributoIettSatb.getInformacao().toUpperCase().trim().contains(Pagina.getParamStr(request, nomeCampo).toUpperCase().trim())){
            		    						itensRemovidos.add(iett.getCodIett());
                	                			itItensEstrutura.remove();
                	                			filtrarAtributoLivre = false;
                	                			retorno = true;
                	                			break;
            		    					}
        		    					}
        		    				}
    							}
							}
						}    				    			     			 			 
					//Se for VALIDACAO
					} else if (tipoCampo.equals(SisTipoExibicGrupoDao.VALIDACAO)) {
						nomeCampo = codEtt + "_a" + grupoAtributo.getCodSga() + "_Inicio";
						String filtroParametro = Pagina.getParamStr(request, nomeCampo);
						if(!"".equals(Pagina.getParamStr(request, nomeCampo))){
	    				
							List atributosValidacao = itemEstruturaDao.getItemEstruturaSisAtributoIettSatbsIett(iett, grupoAtributo.getCodSga());
							
							if (atributosValidacao.size() == 0){
								itensRemovidos.add(iett.getCodIett());
	                			itItensEstrutura.remove();
	                			filtrarAtributoLivre = false;
	                			retorno = true;
	                			break;
							}
							
		    				Iterator itAtributosValidacao = atributosValidacao.iterator();
		    				
		    				while (itAtributosValidacao.hasNext() && filtrarAtributoLivre){
		    					ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoIettSatb = (ItemEstruturaSisAtributoIettSatb) itAtributosValidacao.next();
		    					
		    					if (itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getAtribInfCompSatb().equals("dataScript")){
		    						
		    						//Modifica as ordens de dia e m�s na data, para ficar no formato MM/DD/YYYY
		    						filtroParametro = 
		    							filtroParametro.substring(3, filtroParametro.lastIndexOf("/")+1) +
		    							filtroParametro.substring(0, filtroParametro.indexOf("/")+1) +
		    							filtroParametro.substring(filtroParametro.lastIndexOf("/")+1);
		        					    					
		        					Date dataFiltro = new Date(filtroParametro);
		        					
		        					String valorAtribLivre = itemEstruturaSisAtributoIettSatb.getInformacao();
			    					
		        					//Modifica as ordens de dia e m�s na data, para ficar no formato MM/DD/YYYY
		        					valorAtribLivre = 
		        						valorAtribLivre.substring(3, valorAtribLivre.lastIndexOf("/")+1) +
		        						valorAtribLivre.substring(0, valorAtribLivre.indexOf("/")+1) +
		        						valorAtribLivre.substring(valorAtribLivre.lastIndexOf("/")+1);
		        					    					
		        					Date dataAtribLivre = new Date(valorAtribLivre);
		        					//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro					    					
		        					if (dataAtribLivre.compareTo(dataFiltro) < 0){
		        						itensRemovidos.add(iett.getCodIett());
	    	                			itItensEstrutura.remove();
	    	                			filtrarAtributoLivre = false;
	    	                			retorno = true;
	    	                			break;
		                    		}
		    						
		    						
		    					} else if (itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getAtribInfCompSatb().equals("numeroInteiroScript") ||
		    							   itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getAtribInfCompSatb().equals("numeroRealScript") ||
		    							   itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getAtribInfCompSatb().equals("valorMonetarioScript") ){
		        					
		    						BigDecimal valorFiltro = new BigDecimal(filtroParametro);
		    						  					
		        					BigDecimal valorAtribLivre = new BigDecimal(itemEstruturaSisAtributoIettSatb.getInformacao());
		        					    					    					
		    						//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
		        					if (valorAtribLivre.doubleValue() < valorFiltro.doubleValue()){
		        						itensRemovidos.add(iett.getCodIett());
		                    			itItensEstrutura.remove();
		                    			filtrarAtributoLivre = false;
		                    			retorno = true;
		                    			break;
		                    		}
		        				}
		    				}
						}
						
						nomeCampo = codEtt + "_a" + grupoAtributo.getCodSga() + "_Fim";
						filtroParametro = Pagina.getParamStr(request, nomeCampo);
						if(!"".equals(Pagina.getParamStr(request, nomeCampo))){
	    				
							List atributosValidacao = itemEstruturaDao.getItemEstruturaSisAtributoIettSatbsIett(iett, grupoAtributo.getCodSga());
							
							if (atributosValidacao.size() == 0){
								itensRemovidos.add(iett.getCodIett());
	                			itItensEstrutura.remove();
	                			filtrarAtributoLivre = false;
	                			retorno = true;
	                			break;
							}
							
		    				Iterator itAtributosValidacao = atributosValidacao.iterator();
		    				
		    				while (itAtributosValidacao.hasNext() && filtrarAtributoLivre){
		    					ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoIettSatb = (ItemEstruturaSisAtributoIettSatb) itAtributosValidacao.next();
		    					
		    					if (itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getAtribInfCompSatb().equals("dataScript")){
		    						
		    						//Modifica as ordens de dia e m�s na data, para ficar no formato MM/DD/YYYY
		    						filtroParametro = 
		    							filtroParametro.substring(3, filtroParametro.lastIndexOf("/")+1) +
		    							filtroParametro.substring(0, filtroParametro.indexOf("/")+1) +
		    							filtroParametro.substring(filtroParametro.lastIndexOf("/")+1);
		        					    					
		        					Date dataFiltro = new Date(filtroParametro);
		        					
		        					String valorAtribLivre = itemEstruturaSisAtributoIettSatb.getInformacao();
			    					
		        					//Modifica as ordens de dia e m�s na data, para ficar no formato MM/DD/YYYY
		        					valorAtribLivre = 
		        						valorAtribLivre.substring(3, valorAtribLivre.lastIndexOf("/")+1) +
		        						valorAtribLivre.substring(0, valorAtribLivre.indexOf("/")+1) +
		        						valorAtribLivre.substring(valorAtribLivre.lastIndexOf("/")+1);
		        					    					
		        					Date dataAtribLivre = new Date(valorAtribLivre);
		        					//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro					    					
		        					if (dataAtribLivre.compareTo(dataFiltro) > 0){
		        						itensRemovidos.add(iett.getCodIett());
	    	                			itItensEstrutura.remove();
	    	                			filtrarAtributoLivre = false;
	    	                			retorno = true;
	    	                			break;
		                    		}
		        					
		    					} else if (itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getAtribInfCompSatb().equals("numeroInteiroScript") ||
		    							   itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getAtribInfCompSatb().equals("numeroRealScript") ||
		    							   itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getAtribInfCompSatb().equals("valorMonetarioScript") ){
		        					
		    						BigDecimal valorFiltro = new BigDecimal(filtroParametro);
				  					
		        					BigDecimal valorAtribLivre = new BigDecimal(itemEstruturaSisAtributoIettSatb.getInformacao());
		        					    					    					
		    						//Remove da lista se a data estiver estiver fora do intervalo especificado no filtro
		        					if (valorAtribLivre.doubleValue() > valorFiltro.doubleValue()){
		        						itensRemovidos.add(iett.getCodIett());
		                    			itItensEstrutura.remove();
		                    			filtrarAtributoLivre = false;
		                    			retorno = true;
		                    			break;
		                    		}
		        				}
		    					
		    				}
						}
						
	    				nomeCampo = codEtt + "_a" + grupoAtributo.getCodSga();
						filtroParametro = Pagina.getParamStr(request, nomeCampo);
						if(!"".equals(Pagina.getParamStr(request, nomeCampo))){
	    				
							List atributosValidacao = itemEstruturaDao.getItemEstruturaSisAtributoIettSatbsIett(iett, grupoAtributo.getCodSga());
							
							if (atributosValidacao.size() == 0){
								itensRemovidos.add(iett.getCodIett());
	                			itItensEstrutura.remove();
	                			filtrarAtributoLivre = false;
	                			retorno = true;
	                			break;
							}
							
		    				Iterator itAtributosValidacao = atributosValidacao.iterator();
		    				
		    				while (itAtributosValidacao.hasNext() && filtrarAtributoLivre){
		    					ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoIettSatb = (ItemEstruturaSisAtributoIettSatb) itAtributosValidacao.next();
		    										    					
	        					if (!itemEstruturaSisAtributoIettSatb.getInformacao().toUpperCase().contains(filtroParametro.toUpperCase())){
	        						itensRemovidos.add(iett.getCodIett());
    	                			itItensEstrutura.remove();
    	                			filtrarAtributoLivre = false;
    	                			retorno = true;
    	                			break;
	                    		}
		    				}
						}
					}						
				}
        		
        	} catch (Exception e) {
				// TODO: handle exception
			}
        	
    		return retorno;		
    	}
        
        /**
         * Ordena uma lista de AcompRelatorioArel de acordo com a Data Limite das fun��es de acompanhamento de cada Arel.
         * 
         * @param arels
         * @return
         * @throws ECARException
         */
        public List ordenaRelatorioFuncaoAcompByDataLimite(List arels) throws ECARException{

        	List arelsOrdenados = new ArrayList();
        	
        	if (arels != null && arels.size() > 0){
				AcompRelatorioArel relatorioOrdenar = (AcompRelatorioArel) arels.get(0);
				Set setDatasLimites = relatorioOrdenar.getAcompReferenciaItemAri().getAcompRefItemLimitesArlis();
				List listaDatasLimites = new ArrayList(setDatasLimites);
				
				//Ordena os AcompRefItemLimitesArli por datas limites.
				Collections.sort(listaDatasLimites,
					new Comparator(){
    					public int compare(Object o1, Object o2) {
		        			AcompRefItemLimitesArli a1 = (AcompRefItemLimitesArli) o1;
		        			AcompRefItemLimitesArli a2 = (AcompRefItemLimitesArli) o2;
		        			if (a1.getDataLimiteArli()!=null && a2.getDataLimiteArli()!=null)
		        				return a1.getDataLimiteArli().compareTo(a2.getDataLimiteArli());
		        			else
		        				return -1;
    					}
					}
				);
						
				Iterator itAcompRefItemLimitesOrdenados = listaDatasLimites.iterator();
				AcompRefItemLimitesArli acompRef = null;

				//Compara com os Arels passados como par�metro com a lista ordenada de AcompRefItemLimitesArli e adiciona os Arels ordenados
				//na lista de retorno.
				while (itAcompRefItemLimitesOrdenados.hasNext()){
					acompRef = (AcompRefItemLimitesArli) itAcompRefItemLimitesOrdenados.next();
					Iterator itRelatorios = arels.iterator();
					
					while (itRelatorios.hasNext()){
						AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();
						
						if (relatorio.getTipoFuncAcompTpfa().equals(acompRef.getTipoFuncAcompTpfa())){
							arelsOrdenados.add(relatorio);
						}
					}
				}
			}			
			
			return arelsOrdenados;
        }
        
        
        
        /**
         * Devolve uma lista de usuarios associados a fun��es de acompamhentos de um item especifico
         * @param item 
         * @return
         * @throws ECARException
         */
        public List<UsuarioUsu> getListaUsuariosAcompReferenciaItem(ItemEstruturaIett item) throws ECARException {
        	
    		List<UsuarioUsu> retorno = new ArrayList<UsuarioUsu>();		

            try {           
    			
            	String hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_USUARIOS_COM_FUNCAO_ACOMPANHAMENTO_ASSOCIADOS_A_ITEM, request.getSession().getServletContext()), item.getCodIett().toString());
    		
    			Query q = this.getSession().createQuery(hql);
    									
    			retorno = (List<UsuarioUsu>)q.list();
    			
            } catch(IOException e){
    			this.logger.error(e);
    	        throw new ECARException(e);	
            } catch (HibernateException e) {
                this.logger.error(e);
                throw new ECARException("erro.hibernateException");
            }
            return retorno;

        }
        
        /**
         * Retorna uma lista de itens que possuem os niveis de planejamento passados como parametro
         *
         * @param periodosConsiderados Collection de AcompReferenciaAref
         * @param niveisPlanejamento Collection
         * @param orgaoResponsavel OrgaoOrg
         * @param usuarioUsu UsuarioUsu
         * @param gruposUsuario Set
         * @param codTipoAcompanhamento Long (se for nulo ignora o tipo de acompanhamento)
         * @param codIettPai Long (se for diferente de nulo, obt�m os filhos desse item)
         * @param itensSemInformacaoNivelPlanejamento
         * @param menorNivel 
         * @param nuPaginaSelecionada
         * @param itensSalvos
         * @return
         * @throws ECARException
         */
    	public Object[] getItensAcompanhamentoPesquisa(Collection periodosConsiderados,
        		List niveisPlanejamento, OrgaoOrg orgaoResponsavel, UsuarioUsu usuarioUsu,
        		Set gruposUsuario, Long codTipoAcompanhamento, Long codIettPai, 
        		Boolean itensSemInformacaoNivelPlanejamento, int menorNivel, int nuPaginaSelecionada, Set pesquisaIetts, OrgaoOrg secretaria) 
        			throws ECARException {
        
        	
    		List<ItemEstruturaIett> retorno = new ArrayList<ItemEstruturaIett>();		

          	
            try{        
            	ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
                TipoAcompanhamentoDao tipoAcompanhamentoDao = new TipoAcompanhamentoDao(null);
                            
                if(periodosConsiderados.size() > 0){
                	StringBuilder query = new StringBuilder("select arel.acompReferenciaItemAri from AcompRelatorioArel arel ");


                   	query.append(" left join arel.acompReferenciaItemAri.itemEstruturaIett.itemEstruturaNivelIettns niveis");


                    StringBuilder where = new StringBuilder(" where arel.acompReferenciaItemAri.acompReferenciaAref.codAref in (:listaAcompanhamentos)");
                    where.append(" and (arel.acompReferenciaItemAri.itemEstruturaIett.indAtivoIett = 'S')");                
                    
                    if(pesquisaIetts != null) {
                        where.append(" and (arel.acompReferenciaItemAri.itemEstruturaIett.codIett in (:itensSalvos))");
                    }
                    
                    if(codTipoAcompanhamento != null) {
                        where.append(" and (arel.acompReferenciaItemAri.acompReferenciaAref.tipoAcompanhamentoTa.codTa = :codTa)");
                    }
    							
                    if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
                    	where.append(" and (arel.acompReferenciaItemAri.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg = :orgaoResp)");                	
                    }
                    
                    if (secretaria != null && secretaria.getCodOrg() != null){
                    	where.append(" and (arel.acompReferenciaItemAri.acompReferenciaAref.orgaoOrg.codOrg = :codSecretaria)");
                    } else {
                    	where.append(" and (arel.acompReferenciaItemAri.acompReferenciaAref.orgaoOrg.codOrg is null)");
                    }
    				
                	int retornarAteNivel = -1;
                	List<ItemEstruturaIettMin> iettFilhos = new ArrayList<ItemEstruturaIettMin>();
                    if(codIettPai != null){
                    	ItemEstruturaIettMin iettPai = (ItemEstruturaIettMin) itemDao.buscar(ItemEstruturaIettMin.class, codIettPai);
                    	if(iettPai != null){
                    		retornarAteNivel = iettPai.getNivelIett().intValue() + 1;
                    		iettFilhos.addAll(itemDao.getDescendentesMin(iettPai, false));
                    	}
                    }
                    
                    //quando estiver selecionado os itens sem informa��o
                    if(itensSemInformacaoNivelPlanejamento.booleanValue()) {
                        if(niveisPlanejamento != null && niveisPlanejamento.size() > 0) {
                        	where.append(" and (niveis.codSatb in (:listaNiveis) or niveis is null)");
                        } else {
                        	where.append(" and niveis is null");
                        }
                        
                    } else {
                    	
                    	//quando n�o estiver selecionado os itens sem informa��o
                        if(niveisPlanejamento != null && niveisPlanejamento.size() > 0) {
                            where.append(" and (niveis.codSatb in (:listaNiveis)) ");
                        }
                    }
                   
                    Query queryItens = this.getSession().createQuery(query.toString() + where.toString());
                            
                    List<Long> listaCodigosAref = new ArrayList<Long>();
                    
                    for (Iterator iter = periodosConsiderados.iterator(); iter.hasNext();) {
                    	AcompReferenciaAref aReferencia = (AcompReferenciaAref) iter.next();
                        listaCodigosAref.add(aReferencia.getCodAref());
                    }
                    
                    queryItens.setParameterList("listaAcompanhamentos", listaCodigosAref);

                    if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
                    	queryItens.setLong("orgaoResp", orgaoResponsavel.getCodOrg().longValue());
                    }
                    
                    if(niveisPlanejamento != null && niveisPlanejamento.size() > 0) {
                        List<Long> listaCodigosNiveis = new ArrayList<Long>();                    
                        for (Iterator itNiveis = niveisPlanejamento.iterator(); itNiveis.hasNext();) {
    						SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
                            listaCodigosNiveis.add(nivel.getCodSatb());
    					}
                        queryItens.setParameterList("listaNiveis", listaCodigosNiveis);
                    }
                    
                    
                    if(pesquisaIetts != null && pesquisaIetts.size() > 0) {
                        List<Long> listaCodigosItensSalvos = new ArrayList<Long>();                    
                        for (Iterator itItensSalvos = pesquisaIetts.iterator(); itItensSalvos.hasNext();) {
    						PesquisaIett pesquisaIett = (PesquisaIett) itItensSalvos.next();
    						listaCodigosItensSalvos.add(pesquisaIett.getItemEstruturaIett().getCodIett());
    					}
                        queryItens.setParameterList("itensSalvos", listaCodigosItensSalvos);
                    }
                    
                    
                    if(codTipoAcompanhamento != null) {
                    	// listar ARIs conforme o tipo de acompanhamento passado como par�metro
                        queryItens.setLong("codTa", codTipoAcompanhamento.longValue());
                    }
                    
                    if (secretaria != null && secretaria.getCodOrg() != null){
                    	queryItens.setLong("codSecretaria", secretaria.getCodOrg().longValue());
                    }
                
                    List<ItemEstruturaIett> listaItens = new ArrayList<ItemEstruturaIett>();
                    List listaAris = queryItens.list();
                    Iterator itListaAris = listaAris.iterator();
                    
                	if(usuarioUsu == null) { //utilizado para o grafico.jsp - teste de performance
                        while(itListaAris.hasNext()) {
                        	AcompReferenciaItemAri ari = (AcompReferenciaItemAri)itListaAris.next();
                       		listaItens.add(ari.getItemEstruturaIett());
                        }
                	} else {
    	                while(itListaAris.hasNext()) {
    	                	AcompReferenciaItemAri ari = (AcompReferenciaItemAri)itListaAris.next();
    	                	if(validaPermissao.permissaoLeituraAcompanhamento(ari, usuarioUsu, gruposUsuario)) {
    	                		listaItens.add(ari.getItemEstruturaIett());
    	                	}
    	                }
                    }

                    List<ItemEstruturaIett> itensGeralComArvore = itemDao.getArvoreItens(listaItens, null);
                    List<ItemEstruturaIett> arvoreItens = new ArrayList<ItemEstruturaIett>(itensGeralComArvore);
                    
                    if(retornarAteNivel != -1){                	
                    	for (Iterator itArvore = arvoreItens.iterator(); itArvore.hasNext();) {
    						ItemEstruturaIett iett = (ItemEstruturaIett) itArvore.next();
    						ItemEstruturaIettMin iettMin = new ItemEstruturaIettMin();
    						iettMin.setCodIett(iett.getCodIett());
    						if(iett.getNivelIett().intValue() > retornarAteNivel){
    	                		itArvore.remove();
    	                	} else if(!iettFilhos.contains(iettMin)){
    	                		itArvore.remove();
    	                	}
                        	
    	                }
                    }
                    TipoAcompanhamentoTa tipoAcomp = null;
                    if(codTipoAcompanhamento != null) {
                    	tipoAcomp = (TipoAcompanhamentoTa) tipoAcompanhamentoDao.buscar(TipoAcompanhamentoTa.class, codTipoAcompanhamento);
                    }

                    //se � para paginar 
                    if(menorNivel != -2) {
    	                // indices inicial e final para a consulta dos itensEstruturaIett
                    	ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
    	                int nuItensPaginacao = configuracaoDao.getConfiguracao().getNuItensExibidosPaginacao();
    	            	List itensOrdenados =  itemDao.getItensOrdenados(arvoreItens, tipoAcomp);
    	                if(itensOrdenados.size() <= nuItensPaginacao*nuPaginaSelecionada) {
    	            		nuPaginaSelecionada = 1;
    	            	}
    	            	int indiceInicial = (nuPaginaSelecionada-1)*nuItensPaginacao + 1;
    	            	int indiceFinal = (nuPaginaSelecionada-1)*nuItensPaginacao + nuItensPaginacao;
    	            	int contador = 1; // variavel que guarda qual o indice no iterador
    	            	
    	            	List itensOrdenadosPaginados = new ArrayList();
    	            	List itensOrdenadosTotalPorNivel = new ArrayList();
    	            	Iterator itensOrdenadosIt = itensOrdenados.iterator();
    	            	while(itensOrdenadosIt.hasNext()) {
    	            		AtributoEstruturaListagemItens atEstListagem = (AtributoEstruturaListagemItens)itensOrdenadosIt.next();
    	            		if(indiceInicial <= contador && contador <= indiceFinal) {
    	            			itensOrdenadosPaginados.add(atEstListagem);
    	            		}
    	            		if(menorNivel == -1 || atEstListagem.getItem().getNivelIett().intValue() <= menorNivel) {
    	            			itensOrdenadosTotalPorNivel.add(atEstListagem);
    	            			contador++;
    	            		}
    	            	}
    	            	return new Object[]{itensOrdenadosPaginados, itensGeralComArvore, itensOrdenadosTotalPorNivel};
                    } else {
                    	return new Object[]{itemDao.getItensOrdenados(arvoreItens, tipoAcomp), itensGeralComArvore};
                    }

                } else {
                    return new Object[]{new ArrayList(), new ArrayList()};
                }

            } catch(HibernateException e){
            	this.logger.error(e);
                throw new ECARException(e);            
            }        
        }
        
        /**
         *
         * @param obj
         * @param ordem
         * @return
         * @throws ECARException
         */
        @SuppressWarnings("unchecked")
    	public List pesquisarPorData(Object obj, String[] ordem) throws ECARException {
    		
    		List list = new ArrayList();	// lista resultado
    		Criteria select;				// select (do hibernate)
    		
    		if (obj == null)
    			return list;
    		
    		try {
    		    /* limpa os objeto do cache antes de buscar, para garantir que a busca ser� no BD e nao no cache */
    		    /* TODO avaliar e utilizacao do cache */
    		    //clearSession();
    		    
    			select = session.createCriteria(obj.getClass());
    			List listaMetodos = Util.listaMetodosGet(obj);
    			Object auxObj = null;
    			String nomeAtributo;
    			String nomeMetodo;
    			String tipoRetorno;
    			AvaliaMetodo avaliaMetodo;
    			for (int i = 0; i < listaMetodos.size(); i++) {
    				if (((Method)listaMetodos.get(i)).getParameterTypes().length==0)
    					auxObj = ((Method)listaMetodos.get(i)).invoke(obj, null);
    				// somente adiciona um criterio se o conteudo for != vazio
    				if (auxObj != null) {
    					// obtem o nome do m�todo para retirar o nome do atributo
    					nomeMetodo = ((Method)listaMetodos.get(i)).getName();
    					tipoRetorno = ((Method)listaMetodos.get(i)).getReturnType().getName().toLowerCase();
    					nomeAtributo = nomeMetodo.substring(3,4).toLowerCase() +nomeMetodo.substring(4);
    					avaliaMetodo = ((Method)listaMetodos.get(i)).getAnnotation(AvaliaMetodo.class);
    					
    					if (tipoRetorno.endsWith("string")) {
    						select.add(Expression.ilike(nomeAtributo, "%" + auxObj + "%"));
    					} else if (avaliaMetodo != null && avaliaMetodo.valida() && tipoRetorno.endsWith("boolean")) {
    						select.add(Expression.eq(nomeAtributo, auxObj));
    					} else {
    						
    						if (tipoRetorno.endsWith("date")) {
    							//c.add(Restrictions.ge(campoIntervalo, primeiro));
    							select.add(Expression.le(nomeAtributo, auxObj));
    						}else if(!tipoRetorno.endsWith("set")){
    					        select.add(Expression.eq(nomeAtributo, auxObj));
    						}
    					}    
    				}
    			}
    			
    			if (ordem != null)
    				for (int i = 0; i < ordem.length; i+=2) //anda aos pares
    					if (ordem[i+1].equalsIgnoreCase(Dao.ORDEM_ASC))
    						select.addOrder(Order.asc(ordem[i]));
    					else if (ordem[i+1].equalsIgnoreCase(Dao.ORDEM_DESC))
    						select.addOrder(Order.desc(ordem[i]));
    			
    			select.setCacheable(true);
    			select.setCacheMode(CacheMode.GET);
    			list = select.list();
    		
    		} catch (HibernateException e) {
    			this.logger.error(e);
            	throw new ECARException("erro.hibernateException");
    		} catch (IllegalAccessException e) {
    			this.logger.error(e);
            	throw new ECARException("erro.exception");
    		} catch (IllegalArgumentException e) {
    			this.logger.error(e);
            	throw new ECARException("erro.exception");
    		} catch (InvocationTargetException e) {
    			this.logger.error(e);
            	throw new ECARException("erro.exception");
    		}
    		
    		return list;
    	}
        
        
        /**
         * Verifica se um item deve ser acompanhado no m�s atual, utilizando a data inicial do item e sua periodicidade
         * @param item
         * @param acompanhamento
         * @return
         */
        public boolean verificaAlteracaoOrgao(ItemEstruturaIett item, AcompReferenciaAref acompanhamento) throws ECARException {
        	
        	boolean alterou = false;
        	
        	try {
        		AcompReferenciaItemAri acompReferenciaItemAri = this.getAcompReferenciaItemByItemEstruturaIett(acompanhamento,item);	
        		
        	

    		} catch (HibernateException e) {
    			this.logger.error(e);
            	throw new ECARException("erro.hibernateException");
    		
    		} catch (IllegalArgumentException e) {
    			this.logger.error(e);
            	throw new ECARException("erro.exception");
    		}
    		
            return alterou;
        }
     
        /**
         * Verifica a exist�ncia do AcompReferenciaItemAri de um determinado item.
         * Se existir, retorna o objeto AcompReferenciaItemAri. Sen�o, retorna null
         * @param acompanhamento
         * @param item
         * @return
         * @throws ECARException
         * 
         */
        public AcompReferenciaItemAri getAcompReferenciaItemByItemEstruturaIettOrgao(OrgaoOrg orgao, AcompReferenciaAref acompanhamento, ItemEstruturaIett item) throws ECARException{
        	try{
        		        		        		
                if(acompanhamento.getCodAref() != null){
        	    	StringBuilder query = new StringBuilder("select ari from AcompReferenciaItemAri ari")
        	    							.append(" where ari.itemEstruturaIett.codIett = :codIett")
        	    							.append(" and ari.acompReferenciaAref.diaAref = :diaAref")
        	    							.append(" and ari.acompReferenciaAref.mesAref = :mesAref")
        	    							.append(" and ari.acompReferenciaAref.anoAref = :anoAref")
        	    							.append(" and ari.acompReferenciaAref.tipoAcompanhamentoTa.codTa = :codTa")
        	    							.append(" and ari.itemEstruturaIett.indAtivoIett = 'S'");
        	    	
        	    	if(orgao != null)
        	    		query.append(" and ari.acompReferenciaAref.orgaoOrg.codOrg = :codOrg");
        	    	else 
        	    		query.append(" and ari.acompReferenciaAref.orgaoOrg.codOrg is null");

        	    	Query q = this.getSession().createQuery(query.toString());
        			q.setLong("codIett", item.getCodIett().longValue());
        			if(orgao != null)
        				q.setLong("codOrg", orgao.getCodOrg().longValue());
        			q.setString("diaAref", acompanhamento.getDiaAref());
        			q.setString("mesAref", acompanhamento.getMesAref());
        			q.setString("anoAref", acompanhamento.getAnoAref());
        			q.setLong("codTa", acompanhamento.getTipoAcompanhamentoTa().getCodTa());
        			
        			List retorno = q.list();

        			if(retorno == null || retorno.isEmpty()){
                        return null;
                    } else { 
                    	return (AcompReferenciaItemAri) retorno.get(0);
                    }
                }else{
                    return null;
                }            
            } catch(HibernateException e){
    			this.logger.error(e);
                throw new ECARException(e);
            }
        }
        
        /**
         *  Verifica a exist�ncia do AcompReferenciaItemAri de uma lista de itens.
         * Se existir, retorna lista de objetos AcompReferenciaItemAri. Sen�o, retorna null
         * @param secretaria
         * @param lista
         * @param iett
         * @param codigoArefSelecionada
         * @return
         * @throws ECARException
         */
        public List<AcompReferenciaItemAri> getListaAcompReferenciaItemByItemEstruturaIettOrgao(OrgaoOrg orgao, AcompReferenciaAref acompanhamento, List<Long> listaCodigosIett) throws ECARException{
        	try{
        		
                if(acompanhamento.getCodAref() == null){
                    return null;
                }
                	
        	    	StringBuilder query = new StringBuilder("select ari from AcompReferenciaItemAri ari")
        	    							.append(" where ari.itemEstruturaIett.codIett in (:listCodIett)")
        	    							.append(" and ari.acompReferenciaAref.diaAref = :diaAref")
        	    							.append(" and ari.acompReferenciaAref.mesAref = :mesAref")
        	    							.append(" and ari.acompReferenciaAref.anoAref = :anoAref")
        	    							.append(" and ari.acompReferenciaAref.tipoAcompanhamentoTa.codTa = :codTa")
        	    							.append(" and ari.itemEstruturaIett.indAtivoIett = 'S'");
        	    	
        	    	if(orgao != null)
        	    		query.append(" and ari.acompReferenciaAref.orgaoOrg.codOrg = :codOrg");
        	    	else 
        	    		query.append(" and ari.acompReferenciaAref.orgaoOrg.codOrg is null");

        	    	Query q = this.getSession().createQuery(query.toString());
        			//q.setLong("codIett", item.getCodIett().longValue());
        			q.setParameterList("listCodIett", listaCodigosIett);
        			if(orgao != null)
        				q.setLong("codOrg", orgao.getCodOrg().longValue());
        			q.setString("diaAref", acompanhamento.getDiaAref());
        			q.setString("mesAref", acompanhamento.getMesAref());
        			q.setString("anoAref", acompanhamento.getAnoAref());
        			q.setLong("codTa", acompanhamento.getTipoAcompanhamentoTa().getCodTa());
        			
        			List retorno = q.list();

        			if(retorno == null || retorno.isEmpty()){
                        return null;
                    } else { 
                    	return retorno;
                    }
                            
            } catch(HibernateException e){
    			this.logger.error(e);
                throw new ECARException(e);
            }
        }
        
        
        private Map<AcompReferenciaAref, List<AcompReferenciaAref>> criaMapDiaMesAnoReferencias(Collection referenciasAgrupadas) throws ECARException{
        	
        	Map<AcompReferenciaAref, List<AcompReferenciaAref>> mapRetorno = new HashMap<AcompReferenciaAref, List<AcompReferenciaAref>>();
        	
        	Iterator itReferenciasAgrupadas = referenciasAgrupadas.iterator();
        	
        	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
        	
        	while (itReferenciasAgrupadas.hasNext()){
        		
        		AcompReferenciaAref referenciaAgrupada = (AcompReferenciaAref) itReferenciasAgrupadas.next();
        		
        		List<AcompReferenciaAref> acompReferenciasMesmoDiaMesAno = acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(referenciaAgrupada);
        		
        		mapRetorno.put(referenciaAgrupada, acompReferenciasMesmoDiaMesAno);
        	}
        	
        	return mapRetorno;
        }
        
        
        /**
         * Recebe uma cole��o de AcomReferenciaAref com os periodos agrupados do combo , a cole��o de com todos os arefs e o item.
         * A cole��o de arefs serve para descobrir os aris do item. 
         * Retorna o que vai ser usado para montar a tela de pareceres <aref do combo, ari referente ao orgao>
         * @param periodosConsiderados
         * @param item
         * @return
         * @throws ECARException
         */
      
        public Map criarMapPeriodoAriMontarListagem(Collection periodosConsideradosAgrupados, Collection periodosConsideradosListagem, ItemEstruturaIett item) throws ECARException{
      
            try{
                StringBuilder query = new StringBuilder("");
                
                if(periodosConsideradosListagem != null) {
                	query = new StringBuilder("select ari from AcompReferenciaItemAri ari ")
                				.append("where ari.itemEstruturaIett = ? and ari.acompReferenciaAref.codAref in (:listaAcompanhamentos)");
                }
                else {
                    query = new StringBuilder("select ari from AcompReferenciaItemAri ari ")
                    			.append("where ari.itemEstruturaIett = ?");
                }

                Query queryAri = this.getSession().createQuery(query.toString());   
                
                queryAri.setLong(0, item.getCodIett().longValue());

                if(periodosConsideradosListagem != null && !periodosConsideradosListagem.isEmpty()) {
    	            List listaCodigosAref = new ArrayList();
    	            
    	            for (Iterator it = periodosConsideradosListagem.iterator(); it
    						.hasNext();) {
    	                AcompReferenciaAref aReferencia = (AcompReferenciaAref)it.next();
    	                listaCodigosAref.add(aReferencia.getCodAref());                
    	            }
    	            
    	            queryAri.setParameterList("listaAcompanhamentos", listaCodigosAref);
                }
                
                List listaAris = queryAri.list();
                
                Map map = new HashMap();
                
                for (Iterator itAris = listaAris.iterator(); itAris.hasNext();) {
                    AcompReferenciaItemAri ari =  (AcompReferenciaItemAri) itAris.next();
                    for (Iterator itPeriodos = periodosConsideradosAgrupados.iterator(); itPeriodos.hasNext();) {
                        AcompReferenciaAref arefLista =  (AcompReferenciaAref) itPeriodos.next();
                        
                        if(arefLista.getDiaAref().equals(ari.getAcompReferenciaAref().getDiaAref()) 
                        		&& arefLista.getMesAref().equals(ari.getAcompReferenciaAref().getMesAref())  
                        		&& arefLista.getAnoAref().equals(ari.getAcompReferenciaAref().getAnoAref() ))
                        	map.put(arefLista, ari);
                    } 
                    
                } 
                
        
                return map;
                
            } catch(HibernateException e){
    			this.logger.error(e);
                throw new ECARException(e);              
            }
            
        }
        
        /**
         * Recebe uma cole??o de AcomReferenciaAref com os periodos agrupados do combo , a cole??o de com todos os arefs e o item.
         * A cole??o de arefs serve para descobrir os aris do item. 
         * Retorna o que vai ser usado para montar a tela de pareceres <aref do combo, ari referente ao orgao>
         * @param periodosConsiderados
         * @param item
         * @return
         * @throws ECARException
         */
      
        public Map criarMapPeriodoAriMontarListagem(Collection periodosConsideradosAgrupados, Collection periodosConsideradosListagem,List<Long> listaCodigosIett) throws ECARException{
      
            try{
                StringBuilder query = new StringBuilder("");
                
                if(periodosConsideradosListagem != null && !periodosConsideradosListagem.isEmpty()) {
                	query = new StringBuilder("select ari from AcompReferenciaItemAri ari ")
                				.append("where ari.itemEstruturaIett.codIett in (:listaCodigosIett)")
                				.append(" and ari.acompReferenciaAref.codAref in (:listaAcompanhamentos)");
                }
                else {
                    query = new StringBuilder("select ari from AcompReferenciaItemAri ari ")
                    			.append("where ari.itemEstruturaIett.codIett in (:listaCodigosIett)");
                }

                Query queryAri = this.getSession().createQuery(query.toString());   
                
                //queryAri.setLong(0, item.getCodIett().longValue());
                queryAri.setParameterList("listaCodigosIett", listaCodigosIett);

                if(periodosConsideradosListagem != null && !periodosConsideradosListagem.isEmpty()) {
    	            List listaCodigosAref = new ArrayList();
    	            
    	            for (Iterator it = periodosConsideradosListagem.iterator(); it
    						.hasNext();) {
    	                AcompReferenciaAref aReferencia = (AcompReferenciaAref)it.next();
    	                listaCodigosAref.add(aReferencia.getCodAref());                
    	            }
    	            
    	            queryAri.setParameterList("listaAcompanhamentos", listaCodigosAref);
                }
                
                List listaAris = queryAri.list();
                
                Map map = new HashMap();
                
                for (Iterator itAris = listaAris.iterator(); itAris.hasNext();) {
                    AcompReferenciaItemAri ari =  (AcompReferenciaItemAri) itAris.next();
                    for (Iterator itPeriodos = periodosConsideradosAgrupados.iterator(); itPeriodos.hasNext();) {
                        AcompReferenciaAref arefLista =  (AcompReferenciaAref) itPeriodos.next();
                        
                        if(arefLista.getDiaAref().equals(ari.getAcompReferenciaAref().getDiaAref()) 
                        		&& arefLista.getMesAref().equals(ari.getAcompReferenciaAref().getMesAref())  
                        		&& arefLista.getAnoAref().equals(ari.getAcompReferenciaAref().getAnoAref() ))
                        	map.put(arefLista, ari);
                    } 
                    
                } 
                
        
                return map;
                
            } catch(HibernateException e){
    			this.logger.error(e);
                throw new ECARException(e);              
            }
            
        }   
        
        
        
        
        /**
    	 * Retorna uma lista de Aris a partir de Itens ou seus descendentes de acordo com o orgao da referencia.
    	 * 
    	 * @param strCodIetts
    	 * @param aref
    	 * @param orgaoResponsavel
    	 * @return
    	 * @throws ECARException
    	 */
    	public List getAcompReferenciaItemFilhosByIettComSeparacaoOrgao (final String strCodIetts, final AcompReferenciaAref aref,
    			final OrgaoOrg orgaoResponsavel) throws ECARException{

    		final ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
    		List<AcompReferenciaItemAri> lista = new ArrayList<AcompReferenciaItemAri>();
    		
    		if(!strCodIetts.equals("")){
    			String[] codIett = strCodIetts.split(";");
    			Set<Long> codIetts = new HashSet<Long>();
    			for(int i = 0; i < codIett.length; i++){
    				if(!"".equals(codIett[i])){
    					ItemEstruturaIettMin item = (ItemEstruturaIettMin) itemDao.buscar(ItemEstruturaIettMin.class, Long.valueOf(codIett[i]));
    					codIetts.add(item.getCodIett());
    					List<ItemEstruturaIettMin> descendentes = itemDao.getDescendentesMin( item, false);

    					for (ItemEstruturaIettMin element : descendentes) {
    						codIetts.add(element.getCodIett());
    					}					
    				}
    			}
    			
    			if(!codIetts.isEmpty()){
    				StringBuilder sql = new StringBuilder();
    				sql.append("select ari from AcompReferenciaItemAri ari")
    				   .append(" where ari.acompReferenciaAref.codAref = :codAref")
    				   	.append(" and ari.itemEstruturaIett.indAtivoIett = 'S' ");
    				
    				if(codIetts.size() == 1){
    					sql.append(" and ari.itemEstruturaIett.codIett = :codIett ");					
    				}
    				else {
    					sql.append(" and ari.itemEstruturaIett.codIett in (:codIett) ");
    				}
    				
    				if(orgaoResponsavel == null){
    					sql.append("and ari.acompReferenciaAref.orgaoOrg.codOrg is null ");
    				} else {	
                    	sql.append(" and ari.acompReferenciaAref.orgaoOrg.codOrg = :orgaoResp ");
                    }

    				Query queryAri = this.getSession().createQuery(sql.toString());   
    		        
    				queryAri.setLong("codAref", aref.getCodAref().longValue());

    				if(codIetts.size() == 1){
    					List<Long> listCodIetts = new ArrayList<Long>(codIetts);
    					queryAri.setLong("codIett", ((Long) listCodIetts.get(0)).longValue());
    				}
    				else {
    					queryAri.setParameterList("codIett", codIetts);
    				}
    				
    		        if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
    		        	queryAri.setLong("orgaoResp", orgaoResponsavel.getCodOrg().longValue());
    		        }
    		        lista.addAll( (ArrayList<AcompReferenciaItemAri>)queryAri.list() );
    			}
    		}
    		return lista;
    	}
    	
    	
    	/**
    	 * A partir de um AcompReferenciaItem inclu�do na lista verifica a exist�ncia de
    	 * 		Acompanhamentos de Itens filhos do Ari passado, que s�o inclu�dos juntamente
    	 * 		na lista.
    	 * H� ainda a possibilidade dos filhos serem filtrados pelo orgao da referencia.
    	 * 		
    	 * @param acompRefItem
    	 * @param orgaoResponsavel
    	 * @return
    	 * @throws ECARException
    	 * 
    	 */
    	public List getAcompReferenciaItemFilhosByAriComSeparacaoOrgao (AcompReferenciaItemAri acompRefItem,
    					OrgaoOrg orgaoResponsavel) throws ECARException{
    		List lista = new ArrayList();
    		
    		lista.add(acompRefItem);
    		
    		try{
    			ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
    			
    			List descendentes = itemDao.getDescendentes(acompRefItem.getItemEstruturaIett(), true);
    			
    			if(descendentes != null && descendentes.size() > 0){
    				List codIettDescendentes = new ArrayList();
    				
    				for (Iterator itDescendentes = descendentes.iterator(); itDescendentes
    						.hasNext();) {
    					ItemEstruturaIett iett = (ItemEstruturaIett) itDescendentes.next();
    					codIettDescendentes.add(iett.getCodIett());
    				}
    				
    				StringBuilder query = new StringBuilder("select ari from AcompReferenciaItemAri ari")
    									.append(" where ari.acompReferenciaAref = ? and ari.itemEstruturaIett.codIett in (:listaItens)")
    									.append(" and ari.itemEstruturaIett.indAtivoIett = 'S'");
    				
    				if(orgaoResponsavel == null){
    					query.append("and ari.acompReferenciaAref.orgaoOrg.codOrg is null ");
    				} else {	
                    	query.append(" and ari.acompReferenciaAref.orgaoOrg.codOrg = :orgaoResp ");
                    }

    				Query queryAri = this.getSession().createQuery(query.toString());   
    	        	queryAri.setParameterList("listaItens", codIettDescendentes);
    		        queryAri.setLong(0, acompRefItem.getAcompReferenciaAref().getCodAref().longValue());
    		        
    		        if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
    		        	queryAri.setLong("orgaoResp", orgaoResponsavel.getCodOrg().longValue());
    		        }

    		        lista.addAll(queryAri.list());


    		        List listaTemp = new ArrayList();
    		        List listaAri = new ArrayList();
    		        for (Iterator it = lista.iterator(); it.hasNext();) {
    		        	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) it.next();
    		        	
    		        	if(!listaTemp.contains(ari.getItemEstruturaIett().getCodIett())){
    		        		listaTemp.add(ari.getItemEstruturaIett().getCodIett());
    		        		listaAri.add(ari);
    		        	}
    		        }

    		        lista.clear();
    		        lista.addAll(listaAri);
    		        
    		        Collections.sort(lista,
    			            new Comparator() {
    		        			ItemEstruturaDao iettDaoSort = new ItemEstruturaDao(null);
    			        		public int compare(Object o1, Object o2) {
    		        		    	AcompReferenciaItemAri ari1 = (AcompReferenciaItemAri) o1;
    		        		    	AcompReferenciaItemAri ari2 = (AcompReferenciaItemAri) o2;
    		        		    	
    			        		    ItemEstruturaIett item1 = ari1.getItemEstruturaIett();
    			        		    ItemEstruturaIett item2 = ari2.getItemEstruturaIett();							
    					        	if(iettDaoSort.getAscendenteMaximo(item1).equals(iettDaoSort.getAscendenteMaximo(item2))){												        			
    					        		if(item1.getNivelIett().intValue() == item2.getNivelIett().intValue()){										
    					        				return item1.getNomeIett().compareToIgnoreCase(item2.getNomeIett());	
    					        		} else{										
    					        				return item1.getNivelIett().intValue() - item2.getNivelIett().intValue(); 
    					        		}				        	    	    
    					        	} else {
    					        	    if(item1.getNivelIett().intValue() == item2.getNivelIett().intValue()){									
    				        	    	    return item1.getNomeIett().compareToIgnoreCase(item2.getNomeIett());
    				        	    	} else {									
    				        	    	    return iettDaoSort.getAscendenteMaximo(item1).getNomeIett().compareToIgnoreCase(iettDaoSort.getAscendenteMaximo(item2).getNomeIett());
    				        	    	}				        	    
    					        	}   		            	        		        
    			        		}
    			   	}
    				);
    			}
    		}catch (HibernateException e){
    			this.logger.error(e);
    			throw new ECARException(); 
    		}
    		
    		return lista;
    	}
  
  /**
     * Overload do m�todo getItensAcompanhamentoInPeriodosByOrgaoRespPaginadoConsiderarPermissao que passa uma cole��o de �rg�os 
     * como par�metro.
     * Retorna lista de itens que tenham um Acompanhamento (AcompReferenciaItemAri) em algum dos Per�odos de Refer�ncia 
     * (AcompReferenciaAref) passados como par�metro
     * @param periodosConsiderados Collection de AcompReferenciaAref
     * @param niveisPlanejamento Collection
     * @param orgaosReferencias Collection
     * @param usuarioUsu UsuarioUsu
     * @param gruposUsuario Set
     * @param codTipoAcompanhamento Long (se for nulo ignora o tipo de acompanhamento)
     * @param codIettPai Long (se for diferente de nulo, obt�m os filhos desse item)
     * @param itensSemInformacaoNivelPlanejamento 
     * @param codCor
     * @param nuPaginaSelecionada
     * @param menorNivel
     * @param indLiberados
     * @return List de AcompReferenciaItemAri. lista vazia se n�o foi informado nenhum periodo
     * @throws ECARException
     */
    @SuppressWarnings("static-access")
    public Object[] getItensAcompanhamentoInPeriodosByOrgaoRespPaginadoConsiderarPermissao(Collection periodosConsiderados,
    		Collection niveisPlanejamento, Collection orgaosReferencias, UsuarioUsu usuarioUsu,
    		Set gruposUsuario, Long codTipoAcompanhamento, Long codIettPai, 
    		Boolean itensSemInformacaoNivelPlanejamento, Long codCor, String indLiberados, 
    		int menorNivel, int nuPaginaSelecionada, boolean montarHierarquiaVisualizacao, boolean filtrarOrgao, String filtroOE, boolean minhaVisao) 
    			throws ECARException {
    	

    	try{ 
        	
        	//AJUSTAR PARA PEGAR ARIS AT� PRODUTO. USAR menorNivel
        	//menorNivel = 3;
        	
        	ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
            TipoAcompanhamentoDao tipoAcompanhamentoDao = new TipoAcompanhamentoDao(null);
            TipoAcompanhamentoTa tipoAcomp = null;
            if(codTipoAcompanhamento != null) {
            	tipoAcomp = (TipoAcompanhamentoTa) tipoAcompanhamentoDao.buscar(TipoAcompanhamentoTa.class, codTipoAcompanhamento);
            }
            
            List<ItemEstruturaIettMin> iettFilhos = new ArrayList<ItemEstruturaIettMin>();
            List<ItemEstruturaIett> listaItens = new ArrayList<ItemEstruturaIett>();
            
            if(periodosConsiderados.size() > 0){
            	int retornarAteNivel = -1;
            	
                List listaAris = getItensAcompanhamento(periodosConsiderados,niveisPlanejamento,orgaosReferencias,usuarioUsu,
                		gruposUsuario,codTipoAcompanhamento,codIettPai,itensSemInformacaoNivelPlanejamento,codCor,indLiberados,
                		menorNivel,nuPaginaSelecionada,montarHierarquiaVisualizacao,
                		filtrarOrgao,
                		retornarAteNivel,
                		itemDao,
                		tipoAcomp,
                		iettFilhos,
                		false,
                		filtroOE, null, minhaVisao
                		);
                
                menorNivel = -2;
                //System.out.println("\nTAMANHO DA LISTA: " + listaAris.size());
                
                Iterator itListaAris = listaAris.iterator();

            	if (montarHierarquiaVisualizacao) {
	            	//Lista de itens(itensGeralComArvore) montada a partir da lista de itens consultada(listaItens) mais os itens necess�rios para monta a hierarquia de visualiza��o.
	                List<ItemEstruturaIett> itensGeralComArvore = itemDao.getArvoreItensFromAris(listaAris, null);
	                List<ItemEstruturaIett> arvoreItens = new ArrayList<ItemEstruturaIett>(itensGeralComArvore);
	                
	                if(retornarAteNivel != -1){
	                	for (Iterator itArvore = arvoreItens.iterator(); itArvore.hasNext();) {
							ItemEstruturaIett iett = (ItemEstruturaIett) itArvore.next();
							ItemEstruturaIettMin iettMin = new ItemEstruturaIettMin();
							iettMin.setCodIett(iett.getCodIett());
							if(iett.getNivelIett().intValue() > retornarAteNivel){
		                		itArvore.remove();
		                	} else if(!iettFilhos.contains(iettMin)){
		                		itArvore.remove();
		                	}
	                    	
		                }
	                }
	
	                //se � para paginar 
	                if(menorNivel != -2) {
		                // indices inicial e final para a consulta dos itensEstruturaIett
	                	ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
		                int nuItensPaginacao = configuracaoDao.getConfiguracao().getNuItensExibidosPaginacao();
		            	List itensOrdenados =  itemDao.getItensOrdenados(arvoreItens, tipoAcomp);
		                if(itensOrdenados.size() <= nuItensPaginacao*nuPaginaSelecionada) {
		            		nuPaginaSelecionada = 1;
		            	}
		            	int indiceInicial = (nuPaginaSelecionada-1)*nuItensPaginacao + 1;
		            	int indiceFinal = (nuPaginaSelecionada-1)*nuItensPaginacao + nuItensPaginacao;
		            	int contador = 1; // variavel que guarda qual o indice no iterador
		            	
		            	List itensOrdenadosPaginados = new ArrayList();
		            	List itensOrdenadosTotalPorNivel = new ArrayList();
		            	Iterator itensOrdenadosIt = itensOrdenados.iterator();
		            	while(itensOrdenadosIt.hasNext()) {
		            		AtributoEstruturaListagemItens atEstListagem = (AtributoEstruturaListagemItens)itensOrdenadosIt.next();
		            		if(indiceInicial <= contador && contador <= indiceFinal) {
		            			itensOrdenadosPaginados.add(atEstListagem);
		            		}
		            		if(menorNivel == -1 || atEstListagem.getItem().getNivelIett().intValue() <= menorNivel) {
		            			itensOrdenadosTotalPorNivel.add(atEstListagem);
		            			contador++;
		            		}
		            	}
		            	
		            	Object[] _object = new Object[]{itensOrdenadosPaginados, listaAris, itensGeralComArvore, itensOrdenadosTotalPorNivel};
		            	
		            	
		            	return _object;
	                } else {
	                	
                		Object[] _object = new Object[]{itemDao.getItensOrdenados(arvoreItens, tipoAcomp), listaAris, itensGeralComArvore};
                		
                		
                		return _object; 
	                }
            	} else {
            		Object[] _object = {itemDao.getItensOrdenados(listaItens, tipoAcomp,false), listaAris};
            		
            		return _object;            		
            	}
            } else {

                return new Object[]{new ArrayList(), new ArrayList()};
            }

        } catch(HibernateException e){
        	this.logger.error(e);
            throw new ECARException(e);            
        }        
    }
  
    private List getItensAcompanhamento(Collection periodosConsiderados,
    		Collection niveisPlanejamento, Collection orgaosReferencias, UsuarioUsu usuarioUsu,
    		Set gruposUsuario, Long codTipoAcompanhamento, Long codIettPai, 
    		Boolean itensSemInformacaoNivelPlanejamento, Long codCor, String indLiberados, 
    		int menorNivel, int nuPaginaSelecionada, boolean montarHierarquiaVisualizacao, boolean filtrarOrgao,
    		int retornarAteNivel,
    		ItemEstruturaDao itemDao,
    		TipoAcompanhamentoTa tipoAcomp,
    		List<ItemEstruturaIettMin> iettFilhos,
    		boolean considerarLeituraItem,
    		String filtroOE, List etiquetas, boolean minhaVisao
    		) throws ECARException {
    	try {
        	StringBuilder query = new StringBuilder("SELECT distinct ari FROM AcompReferenciaItemAri ari ");

           	query.append(" join fetch ari.itemEstruturaIett itemIett");
           	query.append(" left join fetch itemIett.itemEstruturaNivelIettns niveis");
           	query.append(" join fetch itemIett.itemEstrutUsuarioIettusesByCodIett perm");
           	query.append(" join fetch ari.acompReferenciaAref Aref");
           	query.append(" join fetch Aref.tipoAcompanhamentoTa ta");
           	query.append(" join fetch ta.tipoacompTipofuncacompSisatributoTatpfasatbs permTAGRU");
           	if(etiquetas != null && !etiquetas.isEmpty()){
           		query.append(" join fetch itemIett.relIettEtiqueta rel");
           	}
           	
           	//query.append(" inner join fetch ari.acompReferenciaAref.tipoAcompanhamentoTa.tipoacompTipofuncacompSisatributoTatpfasatbs permTAGRU");
           	query.append(" left join fetch ari.acompRelatorioArels arel");
           	
            StringBuilder where = new StringBuilder(
            		" WHERE ari.acompReferenciaAref.codAref in (:listaAcompanhamentos) ");
            
            if(filtroOE != null && !filtroOE.equals("")) {
            	//where.append(" and ari.itemEstruturaIett.siglaIett = :sigla ");
            }
            if(etiquetas != null && !etiquetas.isEmpty()){
            	where.append(" and rel.etiqueta.codigo in (:etiquetas) ");
            }
            
            where.append(" AND ");
            if(!minhaVisao) {
            	where.append(" (((perm.indEmitePosIettus = 'S' OR " +
                		"perm.indLeituraParecerIettus = 'S' OR ");
                
                if(considerarLeituraItem) {
                	//where.append(" perm.indLeituraIettus = 'S' OR ");
                    //where.append(" perm.indEdicaoIettus = 'S' OR ");	
                }
                		
                where.append(" perm.indInfAndamentoIettus = 'S')");

                
                where.append(" AND (perm.usuarioUsu.codUsu = :codUsu  ");
                where.append(" OR perm.sisAtributoSatb in (:gruposUsuarios)))");
                
                where.append(" AND (permTAGRU.indLeituraParecer = 'S'");
                where.append(" and permTAGRU.tipoFuncAcompTpfa.indEmitePosicaoTpfa = 'S'" +
                		" AND permTAGRU.sisAtributoSatb IN (:gruposUsuarios)))");	
            }else {
            	where.append(" (perm.indEmitePosIettus = 'S' AND perm.usuarioUsu.codUsu = :codUsu) ");
            }
            
            where.append(" and (ari.itemEstruturaIett.indAtivoIett = 'S')");
            
            if(menorNivel != -2) {
            	where.append(" and (ari.itemEstruturaIett.nivelIett = :nivelIett)");
            }
            
            if(codTipoAcompanhamento != null) {
                where.append(" and (ari.acompReferenciaAref.tipoAcompanhamentoTa.codTa = :codTa)");
            }
			
            if (filtrarOrgao){
            	if(tipoAcomp != null && tipoAcomp.getIndSepararOrgaoTa().equals("S")){
                	if(orgaosReferencias != null && orgaosReferencias.size() > 0)
                		where.append(" and (ari.acompReferenciaAref.orgaoOrg.codOrg in (:orgaosReferencias))");
                	else
                		where.append(" and (ari.acompReferenciaAref.orgaoOrg.codOrg is null)");
                } else {
                	if(orgaosReferencias != null && orgaosReferencias.size() > 0)
                		where.append(" and (ari.itemEstruturaIett.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg in (:orgaosReferencias))");
                }
            }
            
        	
            if(codIettPai != null){
            	ItemEstruturaIettMin iettPai = (ItemEstruturaIettMin) itemDao.buscar(ItemEstruturaIettMin.class, codIettPai);
            	if(iettPai != null){
            		retornarAteNivel = iettPai.getNivelIett().intValue() + 1;
            		iettFilhos.addAll(itemDao.getDescendentesMin(iettPai, false));
            	}
            }
            
            //quando estiver selecionado os itens sem informa��o
            if(itensSemInformacaoNivelPlanejamento.booleanValue()) {
                if(niveisPlanejamento != null && niveisPlanejamento.size() > 0) {
                	where.append(" and (niveis.codSatb in (:listaNiveis) or niveis is null)");
                } else {
                	where.append(" and niveis is null");
                }
                
            } else {
            	
            	//quando n�o estiver selecionado os itens sem informa��o
                if(niveisPlanejamento != null && niveisPlanejamento.size() > 0) {
                    where.append(" and (niveis.codSatb in (:listaNiveis)) ");
                }
            }
            
            if(codCor != null){
            	where.append(" and arel.cor.codCor = :codCor");
            }
            
            // Teste ref. mantis 10848
            if(indLiberados != null && "S".equals(indLiberados)){
            	where.append(" and ari.statusRelatorioSrl.codSrl = :codLiberado");
            }
            
//            if(etiquetas != null && etiquetas.size() > 0){
//           		where.append(" and relIettEtiqueta.etiqueta.codigo in (:etiquetas) ");
//           	}
            
            Query queryItens = this.getSession().createQuery(query.toString() + where.toString());
            
            //filtro OE
            
            if(filtroOE != null && !filtroOE.equals("")) {
            	//queryItens.setString("sigla", filtroOE);
            }
            
            //perm
            queryItens.setLong("codUsu", usuarioUsu.getCodUsu());
            
            //permTA
            //queryItens.setString("acessoInclusao", ValidaPermissao.SIM);
            
            if(!minhaVisao) {
            	queryItens.setParameterList("gruposUsuarios", gruposUsuario);
            }
            
            //Menor Nivel
            if(menorNivel != -2) {
            	queryItens.setParameter("nivelIett", menorNivel);
            }
            
            List<Long> listaCodigosAref = new ArrayList<Long>();
            
            for (Iterator iter = periodosConsiderados.iterator(); iter.hasNext();) {
            	AcompReferenciaAref aReferencia = (AcompReferenciaAref) iter.next();
                listaCodigosAref.add(aReferencia.getCodAref());
            }
            
            queryItens.setParameterList("listaAcompanhamentos", listaCodigosAref);

            if (filtrarOrgao && orgaosReferencias != null && orgaosReferencias.size() > 0){
            
                List<Long> listaCodigosOrgaos = new ArrayList<Long>();
                
                for (Iterator iter = orgaosReferencias.iterator(); iter.hasNext();) {
                	OrgaoOrg orgaoReferencia = (OrgaoOrg) iter.next();
                	listaCodigosOrgaos.add(orgaoReferencia.getCodOrg());
                }
                
                queryItens.setParameterList("orgaosReferencias", listaCodigosOrgaos);
            }
            
                            
//            if(orgaoResponsavel != null && orgaoResponsavel.getCodOrg() != null){
//            	queryItens.setLong("orgaoResp", orgaoResponsavel.getCodOrg().longValue());
//            }
            
            if(niveisPlanejamento != null && niveisPlanejamento.size() > 0) {
                List<Long> listaCodigosNiveis = new ArrayList<Long>();                    
                for (Iterator itNiveis = niveisPlanejamento.iterator(); itNiveis.hasNext();) {
					SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
                    listaCodigosNiveis.add(nivel.getCodSatb());
				}
                queryItens.setParameterList("listaNiveis", listaCodigosNiveis);
            }
            
            if(codTipoAcompanhamento != null) {
            	// listar ARIs conforme o tipo de acompanhamento passado como par�metro
                queryItens.setLong("codTa", codTipoAcompanhamento.longValue());
            }
        
            if(codCor != null){
            	queryItens.setLong("codCor", codCor.longValue());
            }
            
            // Teste ref. mantis 10848
            if(indLiberados != null && "S".equals(indLiberados)){
            	queryItens.setLong("codLiberado", Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO).longValue());
            }
            
            if(etiquetas != null && !etiquetas.isEmpty()){            	
           		queryItens.setParameterList("etiquetas", etiquetas);
           	}
            
          //Itens validados de acordo com a permiss�o de edi��o e visualiza��o.
            queryItens.setCacheable(true);
            queryItens.setCacheMode(CacheMode.GET);
			
            List lista = queryItens.list();
            
            return lista;
		} catch(HibernateException e){
			 e.printStackTrace();
        	this.logger.error(e);
            throw new ECARException(e);   
        } 
    }
    
    public List obterLegendasGraficoRelatorioExecutivo(Integer codTpfa, boolean isPrioritario){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT cor.nome_cor, cor.cod_cor_grafico, count(cor.cod_cor) ");
		sql.append("AS qtdRegistros, cor.significado_cor FROM tb_acomp_referencia_item_ari ari ");
		sql.append("INNER JOIN tb_acomp_referencia_aref aref ");
		sql.append("ON ari.cod_aref = aref.cod_aref ");
		sql.append("INNER JOIN tb_acomp_relatorio_arel arel ");
		sql.append("ON ari.cod_ari = arel.cod_ari ");
		sql.append("INNER JOIN tb_tipo_func_acomp_tpfa tpfa ");
		sql.append("ON arel.cod_tpfa = tpfa.cod_tpfa ");
		sql.append("INNER JOIN tb_cor cor ");
		sql.append("ON arel.cod_cor = cor.cod_cor ");
		sql.append("INNER JOIN tb_situacao_sit situacao ");
		sql.append("ON arel.cod_sit = situacao.cod_sit ");
		if(isPrioritario){
			sql.append("INNER JOIN TB_ITEM_ESTRUTURA_NIVEL_IETTN prioritario ");
			sql.append("ON ari.cod_iett = prioritario.cod_iett ");
		}
		sql.append("WHERE tpfa.cod_tpfa = ? ");
		if(isPrioritario){
			sql.append("AND prioritario.cod_atb = 64 ");
		}
		sql.append("GROUP BY cor.nome_cor, cor.cod_cor_grafico, cor.cod_cor, cor.significado_cor ");
		
		Query itens = this.getSession().createSQLQuery(sql.toString());
		
		itens.setParameter(0, codTpfa);
		
		return itens.list();
	}
    
    /**
	 * @deprecated Use {@link #obterLegendasGraficoRelatorioGerencial(Integer,List<Long>,boolean,Long,Long)} instead
	 */
	public List obterLegendasGraficoRelatorioGerencial(Integer codTpfa, List<Long> idsResultados, boolean isPrioritario, Long exercicio){
		return obterLegendasGraficoRelatorioGerencial(codTpfa, idsResultados,
				isPrioritario, exercicio, null);
	}

	public List obterLegendasGraficoRelatorioGerencial(Integer codTpfa, List<Long> idsResultados, boolean isPrioritario, Long exercicio, Long acompReferencia){
    	StringBuilder sql = new StringBuilder();
   	List<Integer> codigoResultado = new ArrayList<Integer>();
    	
    	for(Long resultado: idsResultados){
    		codigoResultado.add(resultado.intValue());
    	}
    	
    	ItemEstruturaDao daoIett = new ItemEstruturaDao(null);
    	daoIett.queryRecursivoFiltro(codigoResultado, sql);
    	
		sql.append("SELECT DISTINCT COALESCE(cor.nome_cor, 'Branco'), cor.cod_cor_grafico, count(iett.cod_iett) ");
		sql.append("AS qtdRegistros, COALESCE(cor.significado_cor,'NÃO MONITORADO')  AS significado_cor ");
		sql.append(
			"        FROM"+
					"            iett_recursivo iett"+    
					"        INNER JOIN"+
					"            tb_acomp_referencia_item_ari ari"+ 
					"                ON ari.cod_iett = iett.cod_iett"+
					"        LEFT JOIN"+
					"            tb_acomp_referencia_aref aref"+ 
					"                ON aref.cod_aref = ari.cod_aref"+   
					"        LEFT JOIN"+
					"            tb_acomp_relatorio_arel arel"+ 
					"                ON arel.cod_ari = ari.cod_ari"+   
					"	LEFT JOIN"+
					"	    tb_cor cor ON cor.cod_cor = arel.cod_cor ");
		
		//Produto
//		if(codTpfa == 6) {
//			sql.append("inner join tb_item_estrutura_iett prod on prod.cod_iett = ari.cod_iett ");
//			sql.append("inner join tb_item_estrutura_iett resul on prod.cod_iett_pai = resul.cod_iett ");			
//		}else if(codTpfa == 3) { 
			//Resultado
//			sql.append("inner join tb_item_estrutura_iett resul on resul.cod_iett = ari.cod_iett   ");
//		}
		
		if(isPrioritario&&codTpfa==3){
			sql.append("INNER JOIN TB_ITEM_ESTRUTURA_NIVEL_IETTN prioritario ");
			sql.append("ON iett.cod_iett = prioritario.cod_iett ");
		}
		
		
		sql.append(
		"  WHERE");
		if(codTpfa==3)
			sql.append("        iett.nivel_iett = 3");
		else if(codTpfa==6)
			sql.append("        iett.nivel_iett = 4");
		
		
		if(isPrioritario&&codTpfa==6){
			sql.append(" AND iett.cod_iett_pai IN (SELECT prioritario.cod_iett FROM TB_ITEM_ESTRUTURA_NIVEL_IETTN prioritario WHERE prioritario.cod_atb = 64)");
		}
		
		
		sql.append("        AND iett.ind_ativo_iett = 'S'"+
		"						  "+
		"        AND (((iett.cod_iett,arel.data_ult_manut_arel) IN (SELECT ari.cod_iett, MAX(arel.data_ult_manut_arel)"+ 
		"							 FROM tb_acomp_referencia_item_ari ari INNER JOIN tb_acomp_relatorio_arel arel ON ari.cod_ari = arel.cod_ari"+
		"							 WHERE (" +
		"										(:codAref<>0 AND ari.cod_aref<=:codAref) OR" +
												"((:exercicio=0 AND :codAref=0) OR (:exercicio<>0 AND :codAref=0 AND arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "+
		"										        AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio)))" +
		"									)"+
		"							 GROUP BY 1)"+
		"	AND arel.cod_cor IS NOT NULL)"+
		"			"+
		"	OR"+
		"		"+
		"	(iett.cod_iett, ari.cod_ari, 0) IN("+ 
		"				SELECT cod_iett, cod_ari, contagem"+ 
		"				FROM(	 "+
		"				WITH todos_ietts_ari as (SELECT DISTINCT ari.cod_iett, ari.cod_ari"+ 
		"							 FROM tb_acomp_referencia_item_ari ari "+
		"							 INNER JOIN tb_acomp_relatorio_arel arel "+
		"								ON arel.cod_ari = ari.cod_ari"+
		"							 INNER JOIN tb_acomp_referencia_aref aref"+
		"								ON aref.cod_aref = ari.cod_aref"+
		"							 WHERE " +
		"								  ((:codAref<>0 AND ari.cod_aref<=:codAref) OR" +
		"							      ((:exercicio=0 AND :codAref=0) OR (arel.data_inclusao_arel"+
		"							      BETWEEN (SELECT"+
		"								    data_inicial_exe"+ 
		"								FROM"+
		"								    tb_exercicio_exe"+ 
		"								WHERE"+
		"								    cod_exe=:exercicio) AND (SELECT"+
		"								    data_final_exe "+
		"								FROM"+
		"								    tb_exercicio_exe"+ 
		"								WHERE"+
		"								    cod_exe=:exercicio))))"+
		"		"+
		"								   AND (TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY')) IN (SELECT MAX(TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY'))"+
		"									 FROM tb_acomp_referencia_aref arefFiltro "+
		"									 WHERE (" +
		"								  				(:codAref<>0 AND arefFiltro.cod_aref<=:codAref) OR" +
		"												((:exercicio=0 AND :codAref=0) OR (:exercicio<>0 AND :codAref=0 AND TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY')"+
		"									 							 BETWEEN (SELECT"+
		"								    							 			data_inicial_exe "+
		"																		  FROM"+
		"								    										tb_exercicio_exe"+ 
		"																		  WHERE"+
		"								    									  cod_exe=:exercicio) AND (SELECT"+
		"								    									  data_final_exe "+
		"																		  FROM"+
		"								    									  tb_exercicio_exe"+ 
		"																		  WHERE"+
		"								    									  cod_exe=:exercicio)))" +
		"											)"+
		"									 ) 	    "+
		"								     "+
		"							), "+
		"				     totais_por_iett as (SELECT ari.cod_iett, COUNT(arel.cod_arel) as total"+ 
		"							 FROM tb_acomp_referencia_item_ari ari	 "+
		"							 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari"+ 
		"							 WHERE (" +
		"								  (:codAref<>0 AND ari.cod_aref<=:codAref) OR" +
		"								 ((:exercicio=0 AND :codAref=0) OR (:exercicio<>0 AND :codAref=0 AND arel.data_ult_manut_arel"+
		"							      BETWEEN (SELECT"+
		"								    data_inicial_exe"+ 
		"								FROM"+
		"								    tb_exercicio_exe"+ 
		"								WHERE"+
		"								    cod_exe=:exercicio) AND (SELECT"+
		"								    data_final_exe "+
		"								FROM"+
		"								    tb_exercicio_exe"+ 
		"								WHERE"+
		"								    cod_exe=:exercicio))))"+
		"							 AND arel.cod_cor IS NOT NULL"+
		"							 AND arel.data_ult_manut_arel IS NOT NULL"+
		"							 GROUP BY 1"+
		"							 ) "+
		"				SELECT tia.cod_iett, tia.cod_ari, COALESCE(tpi.total,0) as contagem"+ 	
		"				FROM todos_ietts_ari tia "+
		"				LEFT JOIN totais_por_iett tpi ON tpi.cod_iett = tia.cod_iett"+ 
		"				) AS NAOMONITORADOS)"+
		"	)"); 		
		
		
		
		//sql.append("WHERE tpfa.cod_tpfa = ? ");
		if(isPrioritario&&codTpfa==3){
			sql.append("AND prioritario.cod_atb = 64 ");
		}
		
		
//		if(idsResultados != null && idsResultados.size() > 0) {
//			sql.append("AND resul.cod_iett in (:ids) ");	
//		}
		
//		if(codTpfa == 6) {
//			sql.append("and prod.ind_ativo_iett='S' ");
//		}else if(codTpfa == 3) { 
//			sql.append("and resul.ind_ativo_iett='S' ");
//		}
//		sql.append("and arel.data_ult_manut_arel = (" +
//				"select " +
//				"max(arel.data_ult_manut_arel) " +
//				"from " +
//				"tb_acomp_relatorio_arel arel " +
//				"inner join " +
//				"tb_acomp_referencia_item_ari ari " +
//				"on ari.cod_ari = arel.cod_ari " +
//				"where " );
//		if(codTpfa == 6) {
//			sql.append("ari.cod_iett = prod.cod_iett  ");
//		}else if(codTpfa == 3) { 
//			sql.append("ari.cod_iett = resul.cod_iett  ");
//		}
//		sql.append("and arel.cod_cor is not null) " );
		
		sql.append("GROUP BY cor.nome_cor, cor.cod_cor_grafico, cor.cod_cor, cor.significado_cor ");
		Query itens = this.getSession().createSQLQuery(sql.toString());
		
		itens.setParameterList("codObj", codigoResultado);

		if(exercicio != null){
			itens.setParameter("exercicio", exercicio);
		}
		else{
			itens.setParameter("exercicio",0L);
		}
		
		if(acompReferencia != null){
			itens.setParameter("codAref", acompReferencia);
		}
		else{
			itens.setParameter("codAref",0L);
		}
		
		
//		itens.setParameter(0, codTpfa);
//		if(idsResultados != null && idsResultados.size() > 0) {
//			itens.setParameterList("ids", idsResultados.toArray());	
//		}
		
		
		return itens.list();
    }
} 