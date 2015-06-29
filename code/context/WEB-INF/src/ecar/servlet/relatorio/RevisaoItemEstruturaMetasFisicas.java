/*
 * Created on 19/05/2006
 *
 */
package ecar.servlet.relatorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;
import comum.util.XmlBuilder;

import ecar.bean.AtributoEstruturaListagemItens;
import ecar.bean.TotalizadorRelatorios;
import ecar.dao.ConfiguracaoDao;
import ecar.dao.EstruturaDao;
import ecar.dao.EstruturaFuncaoDao;
import ecar.dao.FuncaoDao;
import ecar.dao.IettIndResulRevIettrrDAO;
import ecar.dao.ItemEstruturaCriterioDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.SituacaoDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.CriterioCri;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.EstruturaFuncaoEttfPK;
import ecar.pojo.FuncaoFun;
import ecar.pojo.IettIndResulRevIettrr;
import ecar.pojo.ItemEstFisicoRevIettfr;
import ecar.pojo.ItemEstLocalRevIettlr;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturarevisaoIettrev;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.SituacaoSit;
import ecar.pojo.UsuarioUsu;

/**
 * @author aleixo
 *
 */
public class RevisaoItemEstruturaMetasFisicas extends AbstractServletReportXmlXsl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2395457474744685932L;
	private EstruturaDao estruturaDao;
	private ItemEstruturaDao itemEstruturaDao;
	private ItemEstruturaCriterioDao itemCriterioDao;
	private FuncaoDao funcaoDao;
	private SituacaoDao situacaoDao;
	private EstruturaFuncaoDao estruturaFuncaoDao;
	private SegurancaECAR seguranca = null;
	private List itensMarcados;
	private List idsEstrutura;
	private List totalizadores;
	private OrgaoOrg orgaoResponsavel;
	private String orgaoEscolhido = "";
	private boolean imprimirEstrutura;
	private String codEttEscolhida; 
	private long codIettPai;
	private EstruturaEtt estruturaEscolhida;
	
	/**
	 * Gera XML.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param request
	 * @return StringBuffer
	 * @throws ECARException
	 */
	public StringBuffer getXml(HttpServletRequest request) throws ECARException{

		XmlBuilder builder = new XmlBuilder();
        estruturaDao = new EstruturaDao(request);
        itemEstruturaDao = new ItemEstruturaDao(request);
        itemCriterioDao = new ItemEstruturaCriterioDao(request);
        estruturaFuncaoDao = new EstruturaFuncaoDao(request);
        funcaoDao = new FuncaoDao(request);
        situacaoDao = new SituacaoDao(request);
        seguranca = (SegurancaECAR)request.getSession().getAttribute("seguranca");
		itensMarcados = new ArrayList();
        idsEstrutura = new ArrayList();
        totalizadores = new ArrayList();
    	orgaoResponsavel = new OrgaoOrg();
        
    	imprimirEstrutura = ("S".equals(Pagina.getParamStr(request, "imprimirEstrutura"))) ? true : false;
    	codEttEscolhida = Pagina.getParamStr(request, "codEttImprimir");
    	codIettPai = Pagina.getParamLong(request, "codIettPaiImprimir");

    	ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
        ItemEstruturaIett item = new ItemEstruturaIett();
        
        if(!imprimirEstrutura){
        	item = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
        }
        else {
        	estruturaEscolhida = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, Long.valueOf(codEttEscolhida)); 
        }

        String datahora = Data.parseDateHour(new Date()).substring(0,16); //este método retorna dd/mm/aaaa hh:mm:ss:ssss. Faço Substring para poder pegar só "dd/mm/aaaa hh:mm"
        ConfiguracaoCfg config = new ConfiguracaoDao(null).getConfiguracao();
        String titulo = config.getTituloSistema();//Pagina.getParamStr(request, "titulo_sistema");
        String tituloItens = "";
        String cabecalho = "Itens de Revisão Das Metas Físicas da Estrutura do " + titulo;
        
        
    	//Verifica se é para filtrar por Critérios
    	String criteriosCom = Pagina.getParamStr(request, "criteriosIncluidosCom");
    	String criteriosSem = Pagina.getParamStr(request, "criteriosIncluidosSem");
    	String[] criteriosParaFiltrarCom = (!"".equals(criteriosCom.trim())) ? criteriosCom.split("\\|") : new String[] {};
    	String[] criteriosParaFiltrarSem = (!"".equals(criteriosSem.trim())) ? criteriosSem.split("\\|") : new String[] {};

    	List listaCriteriosCom = new ArrayList();					
    	List listaCriteriosSem = new ArrayList();					

    	for(int i = 0; i < criteriosParaFiltrarCom.length; i++){
    		listaCriteriosCom.add(criteriosParaFiltrarCom[i]);
    	}
        
    	for(int i = 0; i < criteriosParaFiltrarSem.length; i++){
    		listaCriteriosSem.add(criteriosParaFiltrarSem[i]);
    	}

    	//Pegando as situacoes que foram selecionados como filtro
    	String situacoes = Pagina.getParamStr(request, "situacoesIncluidas");
    	String[] situacoesParaFiltrar = (!"".equals(situacoes.trim())) ? situacoes.split("\\|") : new String[] {};

    	List listaSituacoes = new ArrayList();					

    	for(int i = 0; i < situacoesParaFiltrar.length; i++){
    		listaSituacoes.add(situacoesParaFiltrar[i]);
    	}        
    	
    	//Pegando o órgão selecionado
    	Dao dao = new Dao();
		String orgao =  request.getParameter("codOrgaoSelecionado");
        
    	if(!"".equals(orgao) && orgao != null){
    		orgaoResponsavel = (OrgaoOrg) dao.buscar(OrgaoOrg.class, Long.valueOf(orgao));
    	}
    	
    	orgaoEscolhido = (orgaoResponsavel.getCodOrg() != null) ? orgaoResponsavel.getSiglaOrg() : "Todos os Órgãos";
        
        if("T".equals(Pagina.getParamStr(request, "todosCheck"))){
        	tituloItens = "Todos";
        }
        else if("M".equals(Pagina.getParamStr(request, "todosCheck"))){
        	tituloItens = "Monitorados";
        }
        else if("N".equals(Pagina.getParamStr(request, "todosCheck"))){
        	tituloItens = "Não Monitorados";
        }
        else {
        	tituloItens = "Conforme Relação Informada Pelo Usuário";
        }
        
        builder.addNode("relatorio", 
        		"titulo=\"" + builder.normalize(cabecalho) + "\"" +
        		" datahora=\"" + datahora + "\"");
        
        /* Imprime a capa*/
        geraXMLCapa(builder, titulo, tituloItens, orgaoEscolhido, listaCriteriosCom, listaCriteriosSem, listaSituacoes);

        if(!imprimirEstrutura){
        	itensMarcados.add(item.getCodIett().toString());
        }
        
        String[] itensMarcadosNaPagina = request.getParameterValues("itemFilho");
        
        if(itensMarcadosNaPagina != null){
            for(int i = 0; i < itensMarcadosNaPagina.length; i++){
            	itensMarcados.add(itensMarcadosNaPagina[i]);
            }
        }

        /* Imprime o relatório do Item Principal */ 
        geraXMLItem(builder, item, orgao, listaCriteriosCom, listaCriteriosSem, listaSituacoes);
            
        geraXMLTotalizador(builder);
        
        builder.closeNode("relatorio");

        return builder.toStringBuffer();
    }
    
	/**
	 * Gera capa xml.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param builder
         * @param titulo
         * @param tituloItens
         * @param orgao
         * @param listaCriteriosCom
         * @param listaCriteriosSem
         * @param listaSituacoes
         * @throws ECARException
	 */
    public void geraXMLCapa(XmlBuilder builder, String titulo, String tituloItens, String orgao, 
    		List listaCriteriosCom, List listaCriteriosSem, List listaSituacoes) throws ECARException{
        builder.addNode("capa",
        		"titulo=\"" + builder.normalize(titulo) + "\"" +
        		" tituloItens=\"" + builder.normalize(tituloItens) + "\"" +
        		" orgao=\"" + builder.normalize(orgao) + "\"");
        
    	FuncaoFun criterioFun = funcaoDao.getFuncao(Long.valueOf("6")); // 6 = Critérios
    	
    	if(criterioFun != null && 
    			(listaCriteriosCom != null && listaCriteriosCom.size() > 0
    			|| listaCriteriosSem != null && listaCriteriosSem.size() > 0)){
        	builder.addNode("filtros", "tipo=\"" + builder.normalize(criterioFun.getLabelPadraoFun()) + "\"" +
        					" estilo=\"CS\""); //CS --> Monta tabela com colunas de Com/Sem
	        
        	if(listaCriteriosCom != null && listaCriteriosCom.size() > 0){
	        	Iterator itCriterios = listaCriteriosCom.iterator();
		        while(itCriterios.hasNext()){
		        	String id = (String) itCriterios.next();
		        	CriterioCri criterio = (CriterioCri) itemCriterioDao.buscar(CriterioCri.class, Long.valueOf(id));
		        	
		        	if(criterio != null){
		        		builder.addClosedNode("filtro", " valor=\"" + builder.normalize(criterio.getDescricaoCri()) + "\" tipo=\"C\"");
		        	}
		        }
        	}
        	
        	if(listaCriteriosSem != null && listaCriteriosSem.size() > 0){
	        	Iterator itCriterios = listaCriteriosSem.iterator();
		        while(itCriterios.hasNext()){
		        	String id = (String) itCriterios.next();
		        	CriterioCri criterio = (CriterioCri) itemCriterioDao.buscar(CriterioCri.class, Long.valueOf(id));
		        	
		        	if(criterio != null){
		        		builder.addClosedNode("filtro", " valor=\"" + builder.normalize(criterio.getDescricaoCri()) + "\" tipo=\"S\"");
		        	}
		        }
        	}
	        
        	builder.closeNode("filtros");
    	}
    	
    	if(listaSituacoes != null && listaSituacoes.size() > 0){
        	builder.addNode("filtros", "tipo=\"" + builder.normalize("Situações") + "\"" +
			" estilo=\"SI\""); //SI --> Simples, Sem colunas de Com/Sem
        	
        	Iterator itSituacoes = listaSituacoes.iterator();
        	while(itSituacoes.hasNext()){
        		String id = (String) itSituacoes.next();
        		SituacaoSit situacao = (SituacaoSit) situacaoDao.buscar(SituacaoSit.class, Long.valueOf(id)); 
        		if(situacao != null){
        			builder.addClosedNode("filtro", " valor=\"" + builder.normalize(situacao.getDescricaoSit()) + "\" tipo=\"N\"");
        		}
        	}
        	builder.closeNode("filtros");
    	}
        
        builder.closeNode("capa");
    }

    /**
     * Gera item xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder 
     * @param item
     * @param orgao
     * @param listaCriteriosSem
     * @param listaSituacoes 
     * @param listaCriteriosCom
     * @throws ECARException
     */
    public void geraXMLItem(XmlBuilder builder, ItemEstruturaIett item, String orgao, 
    		List listaCriteriosCom, List listaCriteriosSem, List listaSituacoes) throws ECARException{
        builder.addNode("item");
        /*
        if(!imprimirEstrutura){
	        geraXMLHierarquia(builder, item);
	        
	        boolean mostrarDados = false;
	        
	        if("".equals(orgao) || orgao == null){ //Todos
	        	mostrarDados = true;
	        }
	        //Para o relatório de Revisão, pegar sempre OrgaoResponsavel2
	        else if (orgao != null && item.getOrgaoOrgByCodOrgaoResponsavel2Iett() != null && orgao.equals(item.getOrgaoOrgByCodOrgaoResponsavel2Iett().getCodOrg().toString())){
	        	mostrarDados = true;
	        }
	        
	        if (!itensMarcados.contains(item.getCodIett().toString())){
	        	mostrarDados = false;
	        }
	        
	        if(mostrarDados){
	        	
	        	List revisoes = itemEstruturaDao.getItemEstruturaRevisaoIettrevOrderByData(item.getCodIett());
	        	
	        	if(revisoes != null && revisoes.size() > 0){
	        		Iterator itItemRevisao = revisoes.iterator();
	        		while(itItemRevisao.hasNext()){
	        			ItemEstruturarevisaoIettrev itemRevisao = (ItemEstruturarevisaoIettrev) itItemRevisao.next();
	        			geraXMLDadosBasicos(builder, itemRevisao);
	        			geraXMLFuncoes(builder, itemRevisao);
	        		}
	        	}
	        }
        }*/
        
       	geraXMLFilhosPorOrgao(builder, item, orgao, listaCriteriosCom, listaCriteriosSem, listaSituacoes);
       
        builder.closeNode("item");
    }
    
    /**
     * Gera funções xml.<br>
     * 
     * @param builder
     * @param itemRevisao
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
    public void geraXMLFuncoes(XmlBuilder builder, ItemEstruturarevisaoIettrev itemRevisao) throws ECARException{
    	String funcaoAtual = "";
    	try {
	    	boolean mostrouIndicadorResultado = false;
	    	boolean exibirQuantidadesPrevistas = false;
	    	
	    	Set funcoes = itemRevisao.getEstruturaEttrev().getEstruturaFuncaoEttfs();
	        List funcoesParalela = new ArrayList(funcoes);
	    	
	        Iterator itFuncoes = funcoes.iterator();
	        while(itFuncoes.hasNext()){
	            EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) itFuncoes.next();
	            funcaoAtual = estruturaFuncao.getLabelEttf();
	
	            boolean mostraDados = false;
	            if("S".equals(estruturaFuncao.getIndRevisaoEttf())){
	                mostraDados = true;
	            }
	
	            if(mostraDados){
	
	            	String funcao = Util.primeiraLetraToUpperCase(estruturaFuncao.getFuncaoFun().getNomeFun());
	            	if ("Quantidades_Previstas".equals(funcao)){
	            		exibirQuantidadesPrevistas = true;
	            		if (!mostrouIndicadorResultado){
	            			mostrouIndicadorResultado = true;
	            			EstruturaFuncaoEttf funcaoPai = this.buscarFuncaoPai(itemRevisao, Long.valueOf("14")); //Indicadores de Resultado
	                		if(funcaoPai != null){
	                			this.geraXMLIndicadores_Resultado(builder, itemRevisao, funcaoPai.getLabelEttf(), exibirQuantidadesPrevistas);
	                		}
	            		}
	            	}
	            	else if ("Indicadores_Resultado".equals(funcao)){
	        			if(!mostrouIndicadorResultado){
	        				mostrouIndicadorResultado = true;
	           				exibirQuantidadesPrevistas = this.verificarFuncao("Quantidades_Previstas", funcoesParalela);
	        				this.geraXMLIndicadores_Resultado(builder, itemRevisao, estruturaFuncao.getLabelEttf(), exibirQuantidadesPrevistas);
	        			}
	        			else {
	        				continue;
	        			}
	        		}
	            	else if ("Localizacao".equals(funcao)){
	            		this.geraXMLLocalizacao(builder, itemRevisao, estruturaFuncao.getLabelEttf());
	            	}
	            }
	        } 

    	} catch(Exception e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do Relatório: Funções - " + funcaoAtual);
    	}
    }
    
    /**
     * Gera hierarquia xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @param quebrarPagina
     * @param orgao
     * @throws ECARException
     */
    public void geraXMLHierarquia(XmlBuilder builder, ItemEstruturaIett item, String quebrarPagina, String orgao) throws ECARException{
        try{
            builder.addNode("hierarquia", "quebrarPagina=\"" + builder.normalize(quebrarPagina) + "\" orgaoResponsavel=\"" + builder.normalize(orgao) + "\"");
            ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
    	    List pais = itemEstruturaDao.getAscendentes(item);
    	    Iterator it = pais.iterator();
    	    while(it.hasNext()){
    	        ItemEstruturaIett pai = (ItemEstruturaIett) it.next();
				String nomeNivel = pai.getNomeIett();
				if(pai.getSiglaIett() != null && !"".equals(pai.getSiglaIett()))
					nomeNivel = pai.getSiglaIett() + " - " + nomeNivel;
				builder.addClosedNode("nivel", "estrutura=\"" + builder.normalize(pai.getEstruturaEtt().getNomeEtt()) + ":\" nomeNivel=\"" + builder.normalize(nomeNivel) + "\"");
				
				this.incrementarTotalizador(pai.getEstruturaEtt().getCodEtt(), pai.getEstruturaEtt().getNomeEtt() + ":");
    	    }

    	    builder.closeNode("hierarquia");
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do Relatório: Hierarquia de Itens");
        }
    }

    /**
     * Gera dados basicos xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder 
     * @param itemRevisao
     * @throws ECARException
     */
    public void geraXMLDadosBasicos(XmlBuilder builder, ItemEstruturarevisaoIettrev itemRevisao) throws ECARException{
        try{
            /* DADOS BÁSICOS */
            builder.addNode("dadosBasicos");
            
            //Situação é um dado fixo e obrigatório em cadastroItens/revisao/form.jsp
            String situacao = "";
            if("A".equals(itemRevisao.getSituacaoIettrev()))
	            situacao = "Alteração";
            else if ("I".equals(itemRevisao.getSituacaoIettrev()))
            	situacao = "Inclusão";
            else if ("E".equals(itemRevisao.getSituacaoIettrev()))
            	situacao = "Exclusão";
            else if ("S".equals(itemRevisao.getSituacaoIettrev()))
            	situacao = "Sem Modificação";
            
            if (situacao != null && !"".equals(situacao.trim()))
	            builder.addClosedNode(descobreTipo("situacaoIettrev"), 
	            		"label=\"" + builder.normalize("Situação") + ": \"" + 
	            		" valor=\"" + builder.normalize(situacao) + "\"");
            
            List dados = estruturaDao.getAtributosEstruturaRevisao(itemRevisao.getEstruturaEttrev());
            if(dados.size() > 0){
                Iterator itDados = dados.iterator();
                while(itDados.hasNext()){
                    ObjetoEstrutura estruturaAtributo = (ObjetoEstrutura) itDados.next();

                    if(estruturaAtributo != null){
	                    String nomeAtributo = estruturaAtributo.iGetNome();  
	                    String valor = Util.normalizaCaracterMarcador(estruturaAtributo.iGetValor(itemRevisao));
	                    String tipoAtributo = descobreTipo(nomeAtributo);
	                    if(valor != null && !"".equals(valor.trim()))
	                    	builder.addClosedNode(tipoAtributo, "label=\"" + builder.normalize(estruturaAtributo.iGetLabel()) + ": \"" + " valor=\"" + builder.normalize(valor) + "\"");
                    }
                }
            }
            //Justificativa SEMPRE mostra
            if (itemRevisao.getJustificativaIettrev() != null && !"".equals(itemRevisao.getJustificativaIettrev().trim()))
	            builder.addClosedNode(descobreTipo("justificativaIettrev"), 
	            		"label=\"" + builder.normalize("Justificativa") + ": \"" + 
	            		" valor=\"" + builder.normalize(Util.normalizaCaracterMarcador(itemRevisao.getJustificativaIettrev())) + "\"");

            builder.closeNode("dadosBasicos");
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: Dados Básicos");               
        }
    }

    
    /**
     * Gera localização xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param itemRevisao
     * @param label
     * @throws ECARException
     */
    public void geraXMLLocalizacao(XmlBuilder builder, ItemEstruturarevisaoIettrev itemRevisao, String label) throws ECARException{
        try{
            if(itemRevisao != null && itemRevisao.getItemEstLocalRevIettlrs() != null 
            		&& itemRevisao.getItemEstLocalRevIettlrs().size() > 0){
                boolean first = true;
                
                List locais = new ArrayList(itemRevisao.getItemEstLocalRevIettlrs());
                Collections.sort(locais,
						new Comparator() {
			        		public int compare(Object o1, Object o2) {
			        		    return ( (ItemEstLocalRevIettlr)o1 ).getLocalItemLit().getIdentificacaoLit().compareToIgnoreCase( ( (ItemEstLocalRevIettlr)o2 ).getLocalItemLit().getIdentificacaoLit() );
			        		}
			    		} );                 
                
                Iterator it = locais.iterator();
                while(it.hasNext()){
                    ItemEstLocalRevIettlr local = (ItemEstLocalRevIettlr) it.next();                   
                    
                    if(first){
                        builder.addNode("localizacao", "label=\"" + builder.normalize(label) + "\" abrangencia=\"" + builder.normalize(local.getLocalItemLit().getLocalGrupoLgp().getIdentificacaoLgp()) + "\"");
                        first = false;
                    }
                    builder.addAndCloseNode("local", builder.normalize(Pagina.trocaNull(local.getLocalItemLit().getIdentificacaoLit())));
                }        
                if(locais != null && locais.size() > 0)
                	builder.closeNode("localizacao");                    
            }
        } catch (Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: " + label);               
        }        
    }

    /**
     * Gera indicadores de resultado xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param itemRevisao 
     * @param label
     * @param mostrarQuantidades
     * @throws ECARException
     */
    public void geraXMLIndicadores_Resultado(XmlBuilder builder, ItemEstruturarevisaoIettrev itemRevisao, 
    		String label, boolean mostrarQuantidades) throws ECARException{
        try{
        	List indicadores = new ArrayList(itemRevisao.getIettIndResulRevIettrrs());
            if(indicadores != null && indicadores.size() > 0){
                builder.addNode("indicadoresResultado", "label=\"" + label + "\"");
                Iterator itIndicadores = indicadores.iterator();        
                while(itIndicadores.hasNext()){
                	IettIndResulRevIettrr indicador = (IettIndResulRevIettrr) itIndicadores.next();
                    //ItemEstrtIndResulIettr indicador = (ItemEstrtIndResulIettr) itIndicadores.next();
/*                    String projecoes = "";
                    String totalizacoes = "";
                	
                    if("S".equals(indicador.getIndProjecaoIettrr()))
                        projecoes = "Sim";
                    if("N".equals(indicador.getIndProjecaoIettrr()))
                        projecoes = "Não";
                    double total = 0;
                    if("S".equals(indicador.getIndAcumulavelIettrr())){
                        totalizacoes = "Sim";
                        total = new IettIndResulRevIettrrDAO(null).getSomaQuantidades(indicador);
                    }   
                                        
                    if("N".equals(indicador.getIndAcumulavelIettrr())){                    
                        totalizacoes = "Não";                
                    }
*/                     
                	String nome = "não informado";
                	String unidade = "";
                	String projecoes = "";
                	String totalizacoes = "";
                	String valorFinal = "";
                    String strTotal = "";
                	
                	if(indicador.getItemEstrtIndResulIettr() != null){
                		nome = indicador.getItemEstrtIndResulIettr().getNomeIettir();
                		unidade = indicador.getItemEstrtIndResulIettr().getUnidMedidaIettr();
                		
                        if("S".equals(indicador.getItemEstrtIndResulIettr().getIndProjecaoIettr()))
                            projecoes = "Sim";
                        if("N".equals(indicador.getItemEstrtIndResulIettr().getIndProjecaoIettr()))
                            projecoes = "Não";
                        
                        if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
                            totalizacoes = "Sim";
                        }   
                        else {
                        	if("M".equals(indicador.getItemEstrtIndResulIettr().getIndValorFinalIettr()))
                        		valorFinal = "Maior";
                        	else if("U".equals(indicador.getItemEstrtIndResulIettr().getIndValorFinalIettr()))
                        		valorFinal = "Último";
                        	else if("N".equals(indicador.getItemEstrtIndResulIettr().getIndValorFinalIettr()))
                        		valorFinal = "Não se aplica";
                        }
                        
                    	strTotal = new IettIndResulRevIettrrDAO(null).getSomaQuantidadePrevista(indicador);
                                           
                        if("N".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){                    
                            totalizacoes = "Não";                
                        }                    

                        
                	}
                    builder.addNode("indicador", "nome=\"" + builder.normalize(nome) + "\"" +
//                    		" descricao=\"" + builder.normalize(Pagina.trocaNull(indicador.getDescricaoIettirr())) + "\"" +
                    		" unidade=\"" + builder.normalize(unidade) + "\"" +
                    		" totalizacoes=\"" + builder.normalize(totalizacoes) + "\"" +
                    		" valorFinal=\"" + builder.normalize(valorFinal) + "\"" + 
                    		" projecoes=\"" + builder.normalize(projecoes) + "\"" +
                    		" total = \"" + builder.normalize(strTotal) + "\"" +
                    		"");
                    
                    if(mostrarQuantidades){
                    	List exercicios = new ArrayList(indicador.getItemEstFisicoRevIettfrs());
                    	
                    	Collections.sort(exercicios,
							new Comparator() {
				        		public int compare(Object o1, Object o2) {
				        		    return ( (ItemEstFisicoRevIettfr)o1 ).getExercicioExe().getDescricaoExe().compareToIgnoreCase( ( (ItemEstFisicoRevIettfr)o2 ).getExercicioExe().getDescricaoExe() );
				        		}
				    		} );
                    	
	                    Iterator it = exercicios.iterator();                
	                    while(it.hasNext()){
	                        ItemEstFisicoRevIettfr exercicio = (ItemEstFisicoRevIettfr) it.next();
	                        builder.addClosedNode("exercicio", "descricao=\"" + builder.normalize(Pagina.trocaNull(exercicio.getExercicioExe().getDescricaoExe())) + "\" quantidade=\"" + Pagina.trocaNullNumeroSemDecimal(exercicio.getQtdPrevistaIettfr().toString()) + "\"");
	                    }
                    }
                    builder.closeNode("indicador");
                }        
                builder.closeNode("indicadoresResultado");                    
            }
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: " + label);            
        }
    }    
      
    /**
     * Gera Filhos por orgão xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item 
     * @param orgao
     * @param listaCriteriosCom 
     * @param listaCriteriosSem
     * @param listaSituacoes
     * @throws ECARException
     */
    public void geraXMLFilhosPorOrgao(XmlBuilder builder, ItemEstruturaIett item, String orgao, 
    		List listaCriteriosCom, List listaCriteriosSem, List listaSituacoes) throws ECARException{
        try{
        	UsuarioUsu usuarioLogado = seguranca.getUsuario();
        	Set gruposUsuario = seguranca.getGruposAcesso();        	
        	
            //Set filhos = item.getItemEstruturaIetts();
            ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
            List filhos = new ArrayList();
            
            if(!imprimirEstrutura){
            	filhos.addAll(itemEstruturaDao.getDescendentesComPermissoesOrdenado(item, usuarioLogado, gruposUsuario));
            }
            else {
            	filhos.addAll(itemEstruturaDao.getDescendentesComPermissoesOrdenadoByEstrutura(estruturaEscolhida, codIettPai, usuarioLogado, gruposUsuario));
            }
        	
            List itensTemp = new ArrayList(itemEstruturaDao.filtrarRelatorioItemEstrutura(filhos, orgaoResponsavel.getCodOrg(), listaCriteriosCom, listaCriteriosSem, listaSituacoes, "S", "N"));

            filhos.clear();
        	//filhos.addAll(itemEstruturaDao.getArvoreItens(itensTemp, null));
        	filhos.addAll(itensTemp);
         
        	Iterator it;
        	if(!imprimirEstrutura){
	        	//remover os itens superiores ao nível atual
	    		it = filhos.iterator();
	    		while(it.hasNext()) {
	    			ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
	    			
	    			if(iett.getNivelIett().intValue() <= item.getNivelIett().intValue()) {
	    				it.remove();
	    			}
	    		}
        	}
        	
        	// ordenar os itens pela sigla
        	itensTemp = new ArrayList(filhos);
        	filhos.clear();
        	//filhos.addAll(itemEstruturaDao.getItensOrdenadosPorSiglaIett(itensTemp));
        	//filhos.addAll(itemEstruturaDao.getItensOrdenados(itensTemp));
        	List atributosListagem = new ArrayList(itemEstruturaDao.getItensOrdenados(itensTemp, null));
        	Iterator itAtrList = atributosListagem.iterator();
        	while(itAtrList.hasNext()){
        		AtributoEstruturaListagemItens atList = (AtributoEstruturaListagemItens) itAtrList.next();
        		filhos.add(atList.getItem());	
        	}
            
            //if(filhos != null && filhos.size() > 0){
        	if(atributosListagem != null && atributosListagem.size() > 0){
                //it = filhos.iterator();
        		it = atributosListagem.iterator();
        		boolean primeiraPagina = true;
                while(it.hasNext()){
                	
                    //ItemEstruturaIett itemFilho = (ItemEstruturaIett) it.next();
                	AtributoEstruturaListagemItens atbList = (AtributoEstruturaListagemItens) it.next();
                	ItemEstruturaIett itemFilho = atbList.getItem();
					
                	if(itemFilho.getNivelIett().intValue() < 3)
                		continue;
                	
                    String nomeNivelItem = builder.normalize(itemFilho.getEstruturaEtt().getNomeEtt()) + ": ";
					String nomeItem = " ";
					String itemSelecionado = "S";
					String quebrarPagina = "N";
					
					String orgaoResponsavel = "";
					/*
					if(itemFilho.getSiglaIett() != null && !"".equals(itemFilho.getSiglaIett()))
						nomeItem += itemFilho.getSiglaIett() + " - ";
					
					nomeItem += builder.normalize(itemFilho.getNomeIett());
					*/
					nomeItem = " " + ("".equals(atbList.getDescricao().trim()) ? itemFilho.getNomeIett() : atbList.getDescricao());
					
					if (!itensMarcados.contains(itemFilho.getCodIett().toString()))
						itemSelecionado = "N";
					
					if(!primeiraPagina && itemFilho.getNivelIett().intValue() == 3){
						quebrarPagina = "S";
					}
					primeiraPagina = false;
					
					if(itemFilho.getNivelIett().intValue() == 3){
						if(itemFilho.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null)
							orgaoResponsavel = itemFilho.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() + " - " + itemFilho.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg();
						geraXMLHierarquia(builder, itemFilho, quebrarPagina, orgaoResponsavel);
					}
					
					builder.addNode("filho", "nomeNivel=\"" + builder.normalize(nomeNivelItem) + "\" nome=\"" + nomeItem + "\" " +
                                    " nivel=\"" + itemFilho.getNivelIett().intValue() + "\"" +
                                    " itemSelecionado=\"" + builder.normalize(itemSelecionado) + "\"");

					builder.closeNode("filho");
					
					this.incrementarTotalizador(itemFilho.getEstruturaEtt().getCodEtt(), nomeNivelItem);
					
                    boolean mostrarDados = false;
                    
                    if("".equals(orgao) || orgao == null){ //Todos
                    	mostrarDados = true;
                    }
                    //Para relatório de Revisao, pegar sempre OrgaoResponsavel2
                    else if (orgao != null && itemFilho.getOrgaoOrgByCodOrgaoResponsavel2Iett() != null && orgao.equals(itemFilho.getOrgaoOrgByCodOrgaoResponsavel2Iett().getCodOrg().toString())){
                    	mostrarDados = true;
                    }
                    
                    if (!itensMarcados.contains(itemFilho.getCodIett().toString())){
                    	mostrarDados = false;
                    }
                    
                    if(mostrarDados){

                    	List revisoes = itemEstruturaDao.getItemEstruturaRevisaoIettrevOrderByData(itemFilho.getCodIett());

                    	if(revisoes != null && revisoes.size() > 0){
                    		Iterator itItemRevisao = revisoes.iterator();
                    		while(itItemRevisao.hasNext()){
                    			ItemEstruturarevisaoIettrev itemRevisao = (ItemEstruturarevisaoIettrev) itItemRevisao.next();
                    			geraXMLDadosBasicos(builder, itemRevisao);
                    			geraXMLFuncoes(builder, itemRevisao);
                    		}
                    	}
                    }
                }    
            }            
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: Listagem de Itens Filho");
        }            
    }

    /**
     * Gera totalizador xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @throws ECARException
     */
    public void geraXMLTotalizador(XmlBuilder builder) throws ECARException{
        try{
        	int total = 0;
        	builder.addNode("totalizadores");        	
            Iterator itTotalizadores = totalizadores.iterator();
            while(itTotalizadores.hasNext()){
            	TotalizadorRelatorios tr = (TotalizadorRelatorios) itTotalizadores.next();
            	String nome = tr.getEstrutura();
            	String valor = "" + tr.getTotal();
            	total += tr.getTotal();
            	builder.addClosedNode("totalizador",
            			"nome=\"" + builder.normalize(nome) + "\"" +
            			" valor=\"" + builder.normalize(valor) + "\"");
            }        	
        	builder.addClosedNode("total", 
        			"nome=\"Total:\"" +
        			" valor=\"" + builder.normalize(String.valueOf(total)) + "\"");
            builder.closeNode("totalizadores");
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: Totalizadores");
        }            
    }

    /**
     * Descobre o tipo.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param String nomeAtb
     * @return String
     */
    private String descobreTipo(String nomeAtb) {
        
       if("objetivoGeralIett".equals(nomeAtb) 
           || "objetivoEspecificoIett".equals(nomeAtb)
           || "beneficiosIett".equals(nomeAtb)
           || "descricaoIett".equals(nomeAtb)
           || "descricaoR1".equals(nomeAtb)
           || "descricaoR2".equals(nomeAtb)
           || "descricaoR3".equals(nomeAtb)
           || "descricaoR4".equals(nomeAtb)
           || "descricaoR5".equals(nomeAtb)
       )
            return "atributo2";
        else
            return "atributo1";
    }

    /**
     * Pega o nome do arquivo xsl.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getXslFileName() {
        return "revisaoEstruturaMetasFisicas.xsl";
    }
    
    /**
     * Pega Erro de pagina.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param request
     * @param mensagem
     * @return String
     */
    public String getErrorPage(HttpServletRequest request, String mensagem){        
        String errorPage = "frm_rel.jsp?codIett=" + Pagina.getParamStr(request, "codIett") + "&msgOperacao=" + mensagem + "&codAba="+Pagina.getParamStr(request, "codAba"); 
        return errorPage;
    }

    /**
     * Busca função pai.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturarevisaoIettrev item
     * @param Long codigo
     * @return EstruturaFuncaoEttf
     * @throws ECARException
     */
    private EstruturaFuncaoEttf buscarFuncaoPai(ItemEstruturarevisaoIettrev item, Long codigo) throws ECARException {
		EstruturaFuncaoEttfPK chave = new EstruturaFuncaoEttfPK();
		chave.setCodEtt(item.getEstruturaEttrev().getCodEtt());
		chave.setCodFun(codigo);
		EstruturaFuncaoEttf funcaoPai = (EstruturaFuncaoEttf) this.estruturaFuncaoDao.buscar(EstruturaFuncaoEttf.class, chave);
		
		return funcaoPai;    	
    }
    
    /**
     * Verifica funções.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param String funcao
     * @param List funcoes
     * @return boolean
     */
    private boolean verificarFuncao(String funcao, List funcoes){
		Iterator itFuncoes = funcoes.iterator();
		boolean retorno = false;
		while(itFuncoes.hasNext()){
			EstruturaFuncaoEttf funcaoParalela = (EstruturaFuncaoEttf) itFuncoes.next();
			if(funcao.equals(funcaoParalela.getFuncaoFun().getNomeFun())){
                if("S".equals(funcaoParalela.getIndRevisaoEttf())){
                	retorno = true;
                	break;
                }
			}
		}
    	return retorno;
    }
    
    /**
     * Incrementa totalizador.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Long idEstrutura
     * @param String nomeEstrutura
     */
    private void incrementarTotalizador(Long idEstrutura, String nomeEstrutura){
    	if(!idsEstrutura.contains(idEstrutura)){
    		TotalizadorRelatorios totalizador = new TotalizadorRelatorios();
    		totalizador.setId(idEstrutura);
    		totalizador.setEstrutura(nomeEstrutura);
    		totalizador.setTotal(1);
    		
    		idsEstrutura.add(idEstrutura);
    		totalizadores.add(totalizador);
    	}
    	else {
    		Iterator itTotalizadores = totalizadores.iterator();
    		while(itTotalizadores.hasNext()){
    	   		TotalizadorRelatorios totalizador = (TotalizadorRelatorios) itTotalizadores.next();
    	   	    
    	   		if(idEstrutura.equals(totalizador.getId())){
    	   			totalizador.incrementeTotal();
    	   			break;
    	   	    }
    		}
    	}
    }
}
