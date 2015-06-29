/*
 * Created on 28/12/2004
 *
 */
package ecar.servlet.relatorio;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
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
import ecar.dao.ExercicioDao;
import ecar.dao.FuncaoDao;
import ecar.dao.ItemEstrtIndResulDao;
import ecar.dao.ItemEstruturaContaOrcamentoDao;
import ecar.dao.ItemEstruturaCriterioDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.ItemEstruturaFonteRecursoDao;
import ecar.dao.ItemEstruturaPrevisaoDao;
import ecar.dao.ItemEstruturaUploadCategoriaDao;
import ecar.dao.SituacaoDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.ApontamentoApt;
import ecar.pojo.AtributoLivre;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.CriterioCri;
import ecar.pojo.EfIettFonteTotEfieft;
import ecar.pojo.EfItemEstContaEfiec;
import ecar.pojo.EfItemEstPrevisaoEfiep;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.EstruturaFuncaoEttfPK;
import ecar.pojo.ExercicioExe;
import ecar.pojo.FuncaoFun;
import ecar.pojo.ItemEstrUplCategIettuc;
import ecar.pojo.ItemEstrtBenefIettb;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrutAcaoIetta;
import ecar.pojo.ItemEstrutCriterioIettc;
import ecar.pojo.ItemEstrutEntidadeIette;
import ecar.pojo.ItemEstrutFisicoIettf;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstrutUploadIettup;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.RecursoRec;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SituacaoSit;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * @author felipe, aleixo
 *
 */
public class RelatorioItemEstrutura extends AbstractServletReportXmlXsl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2395457474744685932L;
	private List itensMarcados;
	private String orgaoEscolhido = "";
	private SegurancaECAR seguranca = null;
	private List idsEstrutura;
	private List totalizadores;
	private OrgaoOrg orgaoResponsavel;
	private boolean imprimirEstrutura;
	private String codEttEscolhida; 
	private long codIettPai;
	private EstruturaEtt estruturaEscolhida;
	
	/**
	 * CONSTANTES
	 */
	private static final int COMBOBOX = 1;
	private static final int CHECKBOX = 2;
	private static final int LISTBOX = 3;
	private static final int RADIO_BUTTON = 4;
	private static final int TEXT = 5;
	private static final int IMAGEM = 6;   
	private static final int MULTITEXTO = 7;   
	private static final int VALIDACAO = 8;
	private static final int MULTIPLO = 9;
	private static final int TEXTAREA = 10;
	 
	
	/* TODO: Remover esta variável quando terminar o processo do PPA.
	 * Isto só será usando durante o processo PPA. Após este processo, essa verificação será retirada. 
	 * Este comentário foi feito para facilitar a retirada deste código após o término do processo do PPA. 
	 */
	private String contextoEcarPPA;
	
	private HttpServletRequest request;

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

		this.request = request;
		
		XmlBuilder builder = new XmlBuilder();
		itensMarcados = new ArrayList();
        seguranca = (SegurancaECAR)request.getSession().getAttribute("seguranca");
        idsEstrutura = new ArrayList();
        totalizadores = new ArrayList();
    	orgaoResponsavel = new OrgaoOrg();
    	
    	Util.liberarImagem();
    	
    	imprimirEstrutura = ("S".equals(Pagina.getParamStr(request, "imprimirEstrutura"))) ? true : false;
    	codEttEscolhida = Pagina.getParamStr(request, "codEttImprimir");
    	codIettPai = Pagina.getParamLong(request, "codIettPaiImprimir");
    	
    	/* TODO: Remover esta variável quando terminar o processo do PPA.
    	 * Isto só será usando durante o processo PPA. Após este processo, essa verificação será retirada. 
    	 * Este comentário foi feito para facilitar a retirada deste código após o término do processo do PPA. 
    	 */
    	contextoEcarPPA = Pagina.getParamStr(request, "contextoEcarPPA");
        
        ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
        
        ItemEstruturaIett item = new ItemEstruturaIett(); 
        if(!imprimirEstrutura){
        	item = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
        }
        else {
        	EstruturaDao estruturaDao = new EstruturaDao(null);        	
        	estruturaEscolhida = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, Long.valueOf(codEttEscolhida)); 
        }

        String datahora = Data.parseDateHour(new Date()).substring(0,16); //este método retorna dd/mm/aaaa hh:mm:ss:ssss. Faço Substring para poder pegar só "dd/mm/aaaa hh:mm"
        ConfiguracaoCfg config = new ConfiguracaoDao(null).getConfiguracao();
        String titulo = config.getTituloSistema();//Pagina.getParamStr(request, "titulo_sistema");
        String tituloItens = "";
        String cabecalho = "";
        
        
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
        
    	cabecalho = "Relatório ";
        if("C".equals(Pagina.getParamStr(request, "indTipoRelatorio"))){
        	cabecalho += "Completo";
        }
        else {
        	if(!"S".equals(contextoEcarPPA))
        		cabecalho += "Resumido";
        	else
        		cabecalho += "do Resumo do PPA";
        }
        
        if(!"S".equals(contextoEcarPPA))
        	cabecalho += " da Estrutura do " + titulo;
        
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
        geraXMLItem(builder, item, Pagina.getParamStr(request, "indTipoRelatorio"), orgao, listaCriteriosCom, listaCriteriosSem, listaSituacoes);
            
        geraXMLTotalizador(builder);
        
        builder.closeNode("relatorio");
        
        return builder.toStringBuffer();
    }
    
	/**
	 * Gera Capa XML.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param builder
         * @param titulo
         * @param tituloItens
         * @param orgao
         * @param listaCriteriosCom
         * @param listaSituacoes
         * @param listaCriteriosSem
         * @throws ECARException
	 */
    public void geraXMLCapa(XmlBuilder builder, String titulo, String tituloItens, 
    		String orgao, List listaCriteriosCom, List listaCriteriosSem, List listaSituacoes) throws ECARException{
        builder.addNode("capa",
        		"titulo=\"" + builder.normalize(titulo) + "\"" +
        		" tituloItens=\"" + builder.normalize(tituloItens) + "\"" +
        		" orgao=\"" + builder.normalize(orgao) + "\"");
        
        SituacaoDao situacaoDao = new SituacaoDao(null);
        ItemEstruturaCriterioDao itemCriterioDao = new ItemEstruturaCriterioDao(null);
        FuncaoDao funcaoDao = new FuncaoDao(null);
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
     * @param tipoRelatorio
     * @param orgao
     * @param listaCriteriosCom
     * @param listaSituacoes
     * @param listaCriteriosSem
     * @throws ECARException
     */
    public void geraXMLItem(XmlBuilder builder, ItemEstruturaIett item, String tipoRelatorio, 
    		String orgao, List listaCriteriosCom, List listaCriteriosSem, List listaSituacoes) throws ECARException{

		/* TODO: Remover esta validação quando terminar o processo do PPA.
		 * 
		 * "No relatório "Resumo", não exibir itens do nível de subproduto
		 * Isto só será usando durante o processo PPA. Após este processo, essa verificação será retirada. 
		 * Este comentário foi feito para facilitar a retirada deste código após o término do processo do PPA. 
		 */
		boolean exibirItem = true;
		if("R".equals(tipoRelatorio)){
			exibirItem = false;
			if(!imprimirEstrutura && item.getNivelIett() != null && item.getNivelIett().intValue() != 5){ //5 = Nível de Subproduto.
				exibirItem = true;
			}
			else if(imprimirEstrutura){
				exibirItem = true;
			}
		}
		
		if(!"S".equals(contextoEcarPPA))
			exibirItem = true;
		
    	if(exibirItem){
	    	
	    	builder.addNode("item");

	    	/*
	    	if(!imprimirEstrutura){
		        //geraXMLHierarquia(builder, item, tipoRelatorio);
		        
		        boolean mostrarDados = false;
		        
		        if("".equals(orgao) || orgao == null){ //Todos
		        	mostrarDados = true;
		        }
		        else if (orgao != null && item.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null && orgao.equals(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getCodOrg().toString())){
		        	mostrarDados = true;
		        }
		        
		        if (!itensMarcados.contains(item.getCodIett().toString())){
		        	mostrarDados = false;
		        }
		        
		        if(mostrarDados){
		        	
		        	this.incrementarTotalizador(item.getEstruturaEtt().getCodEtt(), item.getEstruturaEtt().getNomeEtt());
		        	
		        	geraXMLDadosBasicos(builder, item, tipoRelatorio);
		        
		        	if("C".equals(tipoRelatorio)){
		        		geraXMLNiveisPlanejamento(builder, item);
		        	}
		        
		        	geraXMLFuncoes(builder, item, tipoRelatorio,false);
		        }
	    	}*/
	       	geraXMLFilhosPorOrgao(builder, item, tipoRelatorio, orgao, listaCriteriosCom, listaCriteriosSem, listaSituacoes);
	        builder.closeNode("item");
    	}
    }
    

    /**
     * Gera funções xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder 
     * @param exibirCategoriaSemItemAnexo
     * @param item
     * @param tipoRelatorio
     * @throws ECARException
     */
    public void geraXMLFuncoes(XmlBuilder builder, ItemEstruturaIett item, String tipoRelatorio, 
    		boolean exibirCategoriaSemItemAnexo) throws ECARException{
        
    	boolean mostrouIndicadorResultado = false;
    	boolean mostrouFontesRecursos = false;
    	boolean mostrouPontosCriticos = false;
    	boolean mostrouCategorias = false;
    	
    	boolean exibirQuantidadesPrevistas = false;
    	boolean exibirRecursos = false;
    	boolean exibirApontamentos = false;
    	boolean exibirItensAnexo = false;
    	
    	Set funcoes = item.getEstruturaEtt().getEstruturaFuncaoEttfs();
        List funcoesParalela = new ArrayList(funcoes);
    	
        Iterator itFuncoes = funcoes.iterator();
        while(itFuncoes.hasNext()){
            EstruturaFuncaoEttf estruturaFuncao = (EstruturaFuncaoEttf) itFuncoes.next();
            try{
                boolean mostraDados = false;
                if("S".equals(estruturaFuncao.getIndListagemImpressaResEttf()) && "R".equals(tipoRelatorio)){
                    mostraDados = true;
                }
                if("S".equals(estruturaFuncao.getIndListagemImpressCompEttf()) && "C".equals(tipoRelatorio)){
                    mostraDados = true;
                }
                if(mostraDados){
                    
//                	this.getClass().getMethod("geraXML"+ Util.primeiraLetraToUpperCase(estruturaFuncao.getFuncaoFun().getNomeFun()), new Class[]{XmlBuilder.class, ItemEstruturaIett.class, String.class}).invoke(this, new Object[]{builder, item, estruturaFuncao.getLabelEttf()});
                	
                	String funcao = Util.primeiraLetraToUpperCase(estruturaFuncao.getFuncaoFun().getNomeFun());
                	if ("Quantidades_Previstas".equals(funcao)){
                		exibirQuantidadesPrevistas = true;
                		if (!mostrouIndicadorResultado){
                			mostrouIndicadorResultado = true;
                			EstruturaFuncaoEttf funcaoPai = this.buscarFuncaoPai(item, Long.valueOf("14")); //Indicadores de Resultado
	                		if(funcaoPai != null){
	                			this.geraXMLIndicadores_Resultado(builder, item, funcaoPai.getLabelEttf(), exibirQuantidadesPrevistas);
	                		}
                		}
                	}
                	else if ("Indicadores_Resultado".equals(funcao)){
            			if(!mostrouIndicadorResultado){
            				mostrouIndicadorResultado = true;
               				exibirQuantidadesPrevistas = this.verificarFuncao("Quantidades_Previstas", funcoesParalela, tipoRelatorio);
            				this.geraXMLIndicadores_Resultado(builder, item, estruturaFuncao.getLabelEttf(), exibirQuantidadesPrevistas);
            			}
            			else {
            				continue;
            			}
            		}
                	else if ("Recursos".equals(funcao)){
                		exibirRecursos = true;
                		if (!mostrouFontesRecursos){
                			mostrouFontesRecursos = true;
                			EstruturaFuncaoEttf funcaoPai = this.buscarFuncaoPai(item, Long.valueOf("9")); //Fontes de Recursos
                			if(funcaoPai != null){
                				this.geraXMLFontes_Recursos(builder, item, funcaoPai.getLabelEttf(), exibirRecursos);
                			}
                		}
                	}
                	else if ("Fontes_Recursos".equals(funcao)){
                		if (!mostrouFontesRecursos){
                			mostrouFontesRecursos = true;
                			exibirRecursos = this.verificarFuncao("Recursos", funcoesParalela, tipoRelatorio);
                			this.geraXMLFontes_Recursos(builder, item, estruturaFuncao.getLabelEttf(), exibirRecursos);
                		}
                		else {
                			continue;
                		}
                	}
                	else if ("Apontamentos".equals(funcao)){
                		exibirApontamentos = true;
                		if (!mostrouPontosCriticos){
                			mostrouPontosCriticos = true;
                			EstruturaFuncaoEttf funcaoPai = this.buscarFuncaoPai(item, Long.valueOf("12")); //Pontos Críticos
                			if(funcaoPai != null){
                				this.geraXMLPontos_Criticos(builder, item, funcaoPai.getLabelEttf(), exibirApontamentos);
                			}
                		}
                	}
                	else if ("Pontos_Criticos".equals(funcao)){
                		if (!mostrouPontosCriticos){
                			mostrouPontosCriticos = true;
                			exibirApontamentos = this.verificarFuncao("Apontamentos", funcoesParalela, tipoRelatorio);
                			this.geraXMLPontos_Criticos(builder, item, estruturaFuncao.getLabelEttf(), exibirApontamentos);
                		}
                		else {
                			continue;
                		}
                	}
                	else if("Itens_de_Anexo".equals(funcao)){
                		exibirItensAnexo = true;
                		if (!mostrouCategorias){
                			mostrouCategorias = true;
                			EstruturaFuncaoEttf funcaoPai = this.buscarFuncaoPai(item, Long.valueOf("2")); //Categorias
                			if (funcaoPai != null){
                				this.geraXMLCategorias(builder, item, funcaoPai.getLabelEttf(), exibirItensAnexo, exibirCategoriaSemItemAnexo);
                			}
                		}
                	}
                	else if ("Categorias".equals(funcao)){
                		if (!mostrouCategorias){
                			mostrouCategorias = true;
                			exibirItensAnexo = this.verificarFuncao("Itens_de_Anexo", funcoesParalela, tipoRelatorio);
                			this.geraXMLCategorias(builder, item, estruturaFuncao.getLabelEttf(), exibirItensAnexo, exibirCategoriaSemItemAnexo);
                		}
                		else {
                			continue;
                		}
                	}
            		else {
                		this.getClass().getMethod("geraXML"+ funcao, new Class[]{XmlBuilder.class, ItemEstruturaIett.class, String.class}).invoke(this, new Object[]{builder, item, estruturaFuncao.getLabelEttf()});
            		}
//                		this.getClass().getMethod("geraXML"+ funcao, new Class[]{XmlBuilder.class, ItemEstruturaIett.class, String.class}).invoke(this, new Object[]{builder, item, estruturaFuncao.getLabelEttf()});
                }
            } catch(InvocationTargetException e){     
            	this.logger.error(e);
                /* se a exceção capturada for uma ECARException deve ser passada para frente, senao deve ser ignorada pois ocorreu 
                 * uma NoSuchMethodException devido ao sistema ter tentado chamar um método geraXMLxxxx que não foi implementado.
                 * Como os métodos são chamados por reflexão, todas as exceptions que eles dispararem serão encapsuladas em uma
                 * InvocationTargetException. Por isso a ECARException é retirada dela e passada para frente.
                 * Por exemplo, não existe um método geraXMLRecursos, pois os recursos são tratados pelo método que gera relatório
                 * da fonte de recurso. Porém sempre que cria um relatório o sistema tentará chamar o método geraXMLRecurso se na
                 * configuração da estrutura estiver informado que "recursos" deve aparecer no relatório ( configuração incorreta,
                 * pois os recursos apareceram sempre que aprecer Fonte de Recurso)                 
                 */                    

                throw (ECARException) e.getTargetException();
            } catch (Exception e) {
                this.logger.error(e);
            } 
            
        } 

    }
    
    /**
     * Gera hieraquia xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @param tipoRelatorio
     * @throws ECARException
     */
    public void geraXMLHierarquia(XmlBuilder builder, ItemEstruturaIett item, String tipoRelatorio) throws ECARException{
        try{
            builder.addNode("hierarquia");
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
//			String nomeNivel = item.getNomeIett();
			
			/* TODO: Remover esta validação quando terminar o processo do PPA.
			 * 
			 * Este if é referente ao item 4 do Mantis 5160:
			 * "No relatório "Resumo", no nível da ação, Substituir o Nome da ação pelo Nome PPA"
			 * Isto só será usando durante o processo PPA. Após este processo, essa verificação será retirada. 
			 * Este comentário foi feito para facilitar a retirada deste código após o término do processo do PPA. 
			 */
			String nomeNivel = "";
			boolean buscarNomePPA = false;
			if("R".equals(tipoRelatorio)){
				if(item.getNivelIett() != null && (item.getNivelIett().intValue() == 3 || item.getNivelIett().intValue() == 4)){ //3 = Nível de Ação, 4 = Nível de Produto
					buscarNomePPA = true;
				}
			}

			if(!"S".equals(contextoEcarPPA))
				buscarNomePPA = false;
			
			if(buscarNomePPA)
				nomeNivel += builder.normalize(item.getDescricaoR3());
			else
				nomeNivel += builder.normalize(item.getNomeIett());
			
			
			if(item.getSiglaIett() != null && !"".equals(item.getSiglaIett()))
				nomeNivel = item.getSiglaIett() + " - " + nomeNivel;
			builder.addClosedNode("nivel", "estrutura=\"" + builder.normalize(item.getEstruturaEtt().getNomeEtt()) + ":\" nomeNivel=\"" + builder.normalize(nomeNivel) + "\"");
    	    
			this.incrementarTotalizador(item.getEstruturaEtt().getCodEtt(), item.getEstruturaEtt().getNomeEtt() + ":");
			
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
     * @param item 
     * @param tipoRelatorio
     * @throws ECARException
     */
    public void geraXMLDadosBasicos(XmlBuilder builder, ItemEstruturaIett item, String tipoRelatorio) throws ECARException{
    	
    	String separador = null;
    	
        try{
            /* DADOS BÁSICOS */
            List dados = new EstruturaDao(null).getAtributosEstruturaRelatorio(item.getEstruturaEtt(), tipoRelatorio);
            if(dados.size() > 0){
                builder.addNode("dadosBasicos");
                Iterator itDados = dados.iterator();
                while(itDados.hasNext()){
                    ObjetoEstrutura estruturaAtributo = (ObjetoEstrutura) itDados.next();

                    String nomeAtributo = estruturaAtributo.iGetNome();
                    String valor = "";
                    
                    if(estruturaAtributo.iGetGrupoAtributosLivres() != null){
                    	//Atributo Livre:
                    	if(item.getItemEstruturaSisAtributoIettSatbs() != null){
                    		List<ItemEstruturaSisAtributoIettSatb> listaSisAtributosInformados = item.obterAtirbutosLivresInformados(estruturaAtributo.iGetGrupoAtributosLivres());
                    		int tam = listaSisAtributosInformados.size();
                    		int pos = 0;
                    		Iterator itAtribLivres = item.getItemEstruturaSisAtributoIettSatbs().iterator();
                	    	while(itAtribLivres.hasNext()){
                	    		ItemEstruturaSisAtributoIettSatb atributo = (ItemEstruturaSisAtributoIettSatb) itAtribLivres.next();
                	    		AtributoLivre atributoLivre = new AtributoLivre();
                	    		atributoLivre.setSisAtributoSatb(atributo.getSisAtributoSatb());
                	    		atributoLivre.setInformacao(atributo.getInformacao());
                	    		
                	    		if(!atributoLivre.getSisAtributoSatb().getSisGrupoAtributoSga().equals(estruturaAtributo.iGetGrupoAtributosLivres()))
                	    			continue;
                	    		
                	    		if((atributoLivre.getSisAtributoSatb()
                	    				.getSisGrupoAtributoSga()
                	    				.getSisTipoExibicGrupoSteg()
                	    				.getCodSteg().intValue() != TEXT) && 
                	    			(atributoLivre.getSisAtributoSatb()
                        	    			.getSisGrupoAtributoSga()
                        	    			.getSisTipoExibicGrupoSteg()
                        	    			.getCodSteg().intValue() != TEXTAREA) &&
                	    			(atributoLivre.getSisAtributoSatb()
                        	    			.getSisGrupoAtributoSga()
                        	    			.getSisTipoExibicGrupoSteg()
                        	    			.getCodSteg().intValue() != VALIDACAO)) {
                	    		
	                	    		
	                	    		if(separador == null){
	                	    			separador = new ConfiguracaoDao(request).getConfiguracao().getSeparadorCampoMultivalor();
	                	    		}
	                	    		
	                	    		
	                	    		if(atributoLivre.getSisAtributoSatb() != null) {
	                	    			valor += atributoLivre.getSisAtributoSatb().getDescricaoSatb();
	                	    			pos++;
	                	    		} else {
	                	    			valor += atributoLivre.getInformacao();
	                	    		}
	                	    		
	                	    		if (pos < tam){
	                	    			valor += separador;
	                	    		}
	                	    		
                	    		} else {
                	    			valor = atributoLivre.getInformacao();
                	    		}
                	    	}
                    	}
             
                    	valor = valor.trim(); 
                    }
                    else {
                    	if("nivelPlanejamento".equals(estruturaAtributo.iGetNome())){
                        	if(item.getItemEstruturaNivelIettns() != null){
                        		int tam = item.getItemEstruturaNivelIettns().size();
                        		int pos = 1;
                    	    	Iterator niveis = item.getItemEstruturaNivelIettns().iterator();
                    	    	while(niveis.hasNext()){
                    	    		SisAtributoSatb nivel = (SisAtributoSatb) niveis.next();

                    	    		if(separador == null){
                    	    			separador = new ConfiguracaoDao(request).getConfiguracao().getSeparadorCampoMultivalor();
                    	    		}

                    	    		valor += nivel.getDescricaoSatb();
                    	    		pos++;
                    	    		
                    	    		if (pos < tam){
                    	    			valor += separador;
                    	    		}
                    	    	}
                        	}
                 
                        	valor = valor.trim(); 
                    		
                    	}
                    	else {
                        	valor = estruturaAtributo.iGetValor(item);
                    	}
                    	
                    }
                    
                    String tipoAtributo = descobreTipo(nomeAtributo);
                    
                    valor = Util.normalizaCaracterMarcador(valor);
                    
                    boolean gerarDados = true;
                    if("S".equals(contextoEcarPPA) && "R".equals(tipoRelatorio) && "".equals(valor))
                    	gerarDados = false;
                    
                    if(gerarDados)
                    	builder.addClosedNode(tipoAtributo, "label=\"" + builder.normalize(estruturaAtributo.iGetLabel()) + ": \"" + " valor=\"" + builder.normalize(valor) + "\"");
                }
                builder.closeNode("dadosBasicos");
            }
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: Dados Básicos");               
        }
    }
    
    /**
     * Gera niveis de planejamento xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @throws ECARException
     */
    public void geraXMLNiveisPlanejamento(XmlBuilder builder, ItemEstruturaIett item) throws ECARException{
        try{            
            if(item.getItemEstruturaNivelIettns().size() > 0){
                boolean first = true;
                Iterator it = item.getItemEstruturaNivelIettns().iterator();        
                while(it.hasNext()){
                    SisAtributoSatb atributo = (SisAtributoSatb) it.next();
                    if(first){
                        builder.addNode("niveisPlanejamento");
                        first = false;
                    }
                    builder.addAndCloseNode("nivelPlanejamento", builder.normalize(Pagina.trocaNull(atributo.getDescricaoSatb())));
                }        
                builder.closeNode("niveisPlanejamento");                    
            }    
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: Níveis do Planejamento");    
        }        
    }
    
    /**
     * Gera beneficiarios xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item 
     * @param label
     * @throws ECARException
     */
    public void geraXMLBeneficiarios(XmlBuilder builder, ItemEstruturaIett item, String label) throws ECARException{
        try{
            if(item.getItemEstrtBenefIettbs().size() > 0){
                builder.addNode("beneficiarios", "label=\"" + builder.normalize(label) + "\"");
                Iterator it = item.getItemEstrtBenefIettbs().iterator();
                while(it.hasNext()){
                    ItemEstrtBenefIettb beneficiario = (ItemEstrtBenefIettb) it.next();
                    builder.addClosedNode("beneficiario", "descricao=\"" + builder.normalize(Pagina.trocaNull(beneficiario.getBeneficiarioBnf().getNomeBnf())) +
                            			"\" quantidade=\"" + builder.normalize(Pagina.trocaNull(beneficiario.getQtdPrevistaIettb())) + "\" observacoes=\"" 
                            			+ builder.normalize(Pagina.trocaNull(beneficiario.getComentarioIettb())) + "\"");
                }        
                builder.closeNode("beneficiarios");           
            }
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: " + label);              
        }
    }
    
    /**
     * Gera eventos xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @param label
     * @throws ECARException
     */
    public void geraXMLEventos(XmlBuilder builder, ItemEstruturaIett item, String label) throws ECARException{
        try{
            if(item.getItemEstrutAcaoIettas().size() > 0){            
                builder.addNode("acoes", "label=\"" + builder.normalize(label) + "\"");
                Iterator it = item.getItemEstrutAcaoIettas().iterator();
                while(it.hasNext()){
                    ItemEstrutAcaoIetta acao = (ItemEstrutAcaoIetta) it.next();
                    
    				// ignorar registro inativo
    				if(Dominios.NAO.equals(acao.getIndAtivoIetta())) {
    					continue;
    				}
    				
                    String ultManutencao = (acao.getUsuarioUsu() != null) ? acao.getUsuarioUsu().getNomeUsu() : "";
                    
                    builder.addClosedNode("acao", 
                    		"data=\"" + builder.normalize(Pagina.trocaNullData(acao.getDataIetta())) + "\""+
                    		" descricao=\"" + builder.normalize(Pagina.trocaNull(acao.getDescricaoIetta())) + "\"" +
                            " ultManutencao=\"" + builder.normalize(ultManutencao) + "\"" +
                            " dataInclusao=\"" + builder.normalize(Pagina.trocaNullData(acao.getDataInclusaoIetta())) + "\"");
                }        
                builder.closeNode("acoes");                    
            }
        } catch(Exception e){          
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: " + label);    
        }
    }

    /**
     * Gera criterios xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder 
     * @param item
     * @param label
     * @throws ECARException
     */
    public void geraXMLCriterios(XmlBuilder builder, ItemEstruturaIett item, String label) throws ECARException{
        try{            
            if(item.getItemEstrutCriterioIettcs().size() > 0){
                builder.addNode("criterios", "label=\"" + builder.normalize(label) + "\"");
                
                List criterios = new ArrayList(item.getItemEstrutCriterioIettcs());
                Collections.sort(criterios,
						new Comparator() {
			        		public int compare(Object o1, Object o2) {
			        		    return ( (ItemEstrutCriterioIettc)o1 ).getCriterioCri().getDescricaoCri().compareToIgnoreCase( ( (ItemEstrutCriterioIettc)o2 ).getCriterioCri().getDescricaoCri() );
			        		}
			    		} );                
                
                //Iterator it = item.getItemEstrutCriterioIettcs().iterator();
                Iterator it = criterios.iterator();
                while(it.hasNext()){
                    ItemEstrutCriterioIettc criterio = (ItemEstrutCriterioIettc) it.next();
                    builder.addAndCloseNode("criterio", builder.normalize(Pagina.trocaNull(criterio.getCriterioCri().getDescricaoCri())));
                }        
                builder.closeNode("criterios");   
            }
        } catch( Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: " + label);                
        }
    }

    /**
     * Gera entidades xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder 
     * @param item
     * @param label
     * @throws ECARException
     */
    public void geraXMLEntidades(XmlBuilder builder, ItemEstruturaIett item, String label) throws ECARException{
        try{            
            if(item.getItemEstrutEntidadeIettes().size() > 0){
                builder.addNode("entidades", "label=\"" + builder.normalize(label) + "\"");
                Iterator it = item.getItemEstrutEntidadeIettes().iterator();
                while(it.hasNext()){
                    ItemEstrutEntidadeIette entidade = (ItemEstrutEntidadeIette) it.next();
                    builder.addClosedNode("entidade", "nome=\"" + builder.normalize(Pagina.trocaNull(entidade.getEntidadeEnt().getNomeEnt())) +
                            "\" atuacao=\"" + builder.normalize(Pagina.trocaNull(entidade.getTipoParticipacaoTpp().getDescricaoTpp())) + "\" inicio=\"" 
                            + Pagina.trocaNullData(entidade.getDataInicioIette()) + "\" termino=\"" + Pagina.trocaNullData(entidade.getDataFimIette()) + "\" descricao=\""
                            + builder.normalize(Pagina.trocaNull(entidade.getDescricaoIette())) + "\"");
                    
                }        
                builder.closeNode("entidades");                    
            }            
        }
        catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: " + label);    
        }
    }
    
    /**
     * Gera localização xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item 
     * @param label
     * @throws ECARException
     */
    public void geraXMLLocalizacao(XmlBuilder builder, ItemEstruturaIett item, String label) throws ECARException{
        try{
            if(item.getItemEstrutLocalIettls().size() > 0){
                boolean first = true;
                
                List locais = new ArrayList(item.getItemEstrutLocalIettls());
                Collections.sort(locais,
						new Comparator() {
			        		public int compare(Object o1, Object o2) {
			        		    return ( (ItemEstrutLocalIettl)o1 ).getLocalItemLit().getIdentificacaoLit().compareToIgnoreCase( ( (ItemEstrutLocalIettl)o2 ).getLocalItemLit().getIdentificacaoLit() );
			        		}
			    		} );                 
                
                //Iterator it = item.getItemEstrutLocalIettls().iterator();        
                Iterator it = locais.iterator();
                while(it.hasNext()){
                    ItemEstrutLocalIettl local = (ItemEstrutLocalIettl) it.next();                   
                    
                    if(first){
                        builder.addNode("localizacao", "label=\"" + builder.normalize(label) + "\" abrangencia=\"" + builder.normalize(local.getLocalItemLit().getLocalGrupoLgp().getIdentificacaoLgp()) + "\"");
                        first = false;
                    }
                    builder.addAndCloseNode("local", builder.normalize(Pagina.trocaNull(local.getLocalItemLit().getIdentificacaoLit())));
                }        
                builder.closeNode("localizacao");                    
            }
        } catch (Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: " + label);               
        }        
    }
    
    /**
     * Gera fontes_Recursos xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @param label 
     * @param exibirRecursos
     * @throws ECARException
     */
    public void geraXMLFontes_Recursos(XmlBuilder builder, ItemEstruturaIett item, String label, 
    		boolean exibirRecursos) throws ECARException{
        try{
        	ExercicioDao exercicioDao = new ExercicioDao(null);

        	ItemEstruturaPrevisaoDao itemPrevisaoDao = new ItemEstruturaPrevisaoDao(null);
        	/*
            if(item.getEfIettFonteTotEfiefts().size() > 0){
                builder.addNode("fontesRecurso", "label=\"" + builder.normalize(label) + "\"");
                Iterator itFontes = item.getEfIettFonteTotEfiefts().iterator();        
                while(itFontes.hasNext()){
                    EfIettFonteTotEfieft fontes = (EfIettFonteTotEfieft) itFontes.next();
                    builder.addNode("fonteRecurso", "nome=\"" + builder.normalize(Pagina.trocaNull(fontes.getFonteRecursoFonr().getNomeFonr())) + "\"");
                    if (exibirRecursos){
	                    Iterator itRecursos = new ItemEstruturaPrevisaoDao(null).getRecursosByFonteRecurso(fontes.getFonteRecursoFonr().getCodFonr(), fontes.getItemEstruturaIett().getCodIett()).iterator();
	                    
	                    long codRecurso = -1;
	                    while(itRecursos.hasNext()){
	                        EfItemEstPrevisaoEfiep recurso = (EfItemEstPrevisaoEfiep) itRecursos.next();
	                        String nomeRecurso = "";
	                        if(recurso.getRecursoRec().getCodRec().longValue() != codRecurso){
	                        	nomeRecurso = recurso.getRecursoRec().getNomeRec();
	                        	codRecurso = recurso.getRecursoRec().getCodRec().longValue();
	                        }
	                        
	                        builder.addClosedNode("recurso", 
	                        		"nome=\"" + builder.normalize(nomeRecurso) + "\"" +
	                        		//" valorAprovado=\"" + Util.formataMoeda(recurso.getValorAprovadoEfiep().doubleValue()) + "\"" +
	                        		//" valorRevisado=\"" + Util.formataMoeda(recurso.getValorRevisadoEfiep().doubleValue()) + "\"" +
	                        		" valorAprovado=\"" + Util.formataNumeroSemDecimal(recurso.getValorAprovadoEfiep().setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()) + "\"" +
	                        		" valorRevisado=\"" + Util.formataNumeroSemDecimal(recurso.getValorRevisadoEfiep().setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()) + "\"" +
	                        		" exercicio=\"" + builder.normalize(recurso.getExercicioExe().getDescricaoExe()) + "\"");
	                    }
                    }
                    builder.closeNode("fonteRecurso");
                }        
                builder.closeNode("fontesRecurso");                    
            }*/
        	
        	if(item.getEfIettFonteTotEfiefts().size() > 0){
        		
        		boolean exibirQuadroTotais = false;
        		List totaisExercicios = exercicioDao.getExerciciosValidos(item.getCodIett());
        		int qtdeExeGeral = totaisExercicios.size();
        		double[] totAprovGeral = new double[qtdeExeGeral];
        		double[] totRevGeral = new double[qtdeExeGeral];
        		
        		for(int i = 0; i < qtdeExeGeral; i++){
        			totAprovGeral[i] = 0;
        			totRevGeral[i] = 0;
        		}
        		
        		builder.addNode("fontesRecurso", "label=\"" + builder.normalize(label) + "\"");
        		
        		Iterator itFontes = item.getEfIettFonteTotEfiefts().iterator();
        		while(itFontes.hasNext()){
        			EfIettFonteTotEfieft fontes = (EfIettFonteTotEfieft) itFontes.next();
        			builder.addNode("fonteRecurso");
        			
        			if (exibirRecursos){
        				List listaRecursos = itemPrevisaoDao.getRecursosByFonteRecurso(fontes.getComp_id().getCodFonr(), item.getCodIett(), Dominios.SIM);
        				if(listaRecursos != null && listaRecursos.size() > 0){
        					
        					exibirQuadroTotais = true;
        					
        					List listaItemRec = itemPrevisaoDao.getRecursosByFonteRecurso(fontes.getFonteRecursoFonr().getCodFonr(), fontes.getItemEstruturaIett().getCodIett(), Dominios.SIM);
        					Iterator itItemRec = listaItemRec.iterator();
        					
        					List listaExercicios = exercicioDao.getExerciciosValidos(item.getCodIett());
							Iterator itExerc = listaExercicios.iterator();
							
							int numExe = 0;

							/* Monta o Cabeçalho da Tabela
							 * | Recurso | Valor | 2004 | 2005 | 2006 | 2007 | Total |
							 * */
							builder.addNode("fonteRecursoCabecalho");
							
							builder.addClosedNode("itemCabecalho", "tam=\"2.5cm\"");
							builder.addClosedNode("itemCabecalho", "tam=\"2.7cm\"");
							//builder.addClosedNode("exercicioRecurso", "exercicio=\"Recurso\" alinhamento=\"justify\"");
							builder.addClosedNode("exercicioRecurso", "exercicio=\"" + builder.normalize(fontes.getFonteRecursoFonr().getNomeFonr()) +"\" alinhamento=\"justify\"");
							builder.addClosedNode("exercicioRecurso", "exercicio=\"Valor\" alinhamento=\"justify\"");
							while (itExerc.hasNext()) {
								ExercicioExe exercicio = (ExercicioExe) itExerc.next();
								numExe++;
								builder.addClosedNode("itemCabecalho", "tam=\"3.0cm\"");
								builder.addClosedNode("exercicioRecurso", "exercicio=\"" + builder.normalize(exercicio.getDescricaoExe()) + "\" alinhamento=\"right\"");
							}
							builder.addClosedNode("itemCabecalho", "tam=\"3.3cm\"");
							builder.addClosedNode("exercicioRecurso", "exercicio=\"Total\" alinhamento=\"right\"");
	        				builder.closeNode("fonteRecursoCabecalho");

	        				/* Criar os Arrays de valores Aprovados e Revisados conforme qtde de exercícios*/
							double[] totalAprovExe = new double[numExe];
							double[] totalRevExe = new double[numExe];
							itExerc = listaExercicios.iterator();
							numExe = 0;
							while (itExerc.hasNext()) {
								ExercicioExe exercicio = (ExercicioExe) itExerc.next();
								totalAprovExe[numExe] = 0;
								totalRevExe[numExe] = 0;
								numExe++;
							}
							
							
							int col = 0;
							String valorCampo = "";
							String alinhamento = "";

							/*buscar os valores para cada recurso da fonte de recursos*/
							List listaRec = itemPrevisaoDao.getRecursosDistintosByFonteRecurso(fontes.getFonteRecursoFonr().getCodFonr(), fontes.getItemEstruturaIett().getCodIett());
							Iterator itRec = listaRec.iterator();
							
							while (itRec.hasNext()) {
								RecursoRec recurso = (RecursoRec) itRec.next();
								double totalAprovRec = 0;
								double totalRevRec = 0;

								builder.addNode("recursos");
								
								/*Valores Aprovados*/
								String nomeRecurso = recurso.getNomeRec();
								String valorRecurso = "Aprovado";
								valorCampo = "";

								builder.addClosedNode("itemRecurso", "tam=\"2mm\"");
								builder.addClosedNode("itemRecurso", "tam=\"2.3cm\"");
								builder.addClosedNode("itemRecurso", "tam=\"2.7cm\"");
								builder.addClosedNode("recurso", "valor=\"\" alinhamento=\"justify\" conteudo=\"N\"");
								builder.addClosedNode("recurso", "valor=\"" + builder.normalize(nomeRecurso) + "\" alinhamento=\"justify\" conteudo=\"S\"");
								builder.addClosedNode("recurso", "valor=\"" + builder.normalize(valorRecurso) + "\" alinhamento=\"justify\" conteudo=\"S\"");
								
								col = 0;
								itExerc = listaExercicios.iterator();
								while (itExerc.hasNext()) {
									ExercicioExe exercicio = (ExercicioExe) itExerc.next();
									
									valorCampo = "-";
									alinhamento = "center";
									itItemRec = listaItemRec.iterator();
									while (itItemRec.hasNext()) {
										EfItemEstPrevisaoEfiep ieRecurso = (EfItemEstPrevisaoEfiep) itItemRec.next();
										if (ieRecurso.getExercicioExe().equals(exercicio) && 
													ieRecurso.getRecursoRec().equals(recurso)) {
											
											valorCampo = Pagina.trocaNullMoeda(ieRecurso.getValorAprovadoEfiep());
											alinhamento = "right";
											if(ieRecurso.getValorAprovadoEfiep() != null){
												totalAprovRec = totalAprovRec + ieRecurso.getValorAprovadoEfiep().doubleValue();
												totalAprovExe[col] = totalAprovExe[col] + ieRecurso.getValorAprovadoEfiep().doubleValue();
												totAprovGeral[col] = totAprovGeral[col] + ieRecurso.getValorAprovadoEfiep().doubleValue();
											}
											
										}
									}
									builder.addClosedNode("itemRecurso", "tam=\"3.0cm\"");
									builder.addClosedNode("recurso", "valor=\"" + builder.normalize(valorCampo) + "\" alinhamento=\""+ builder.normalize(alinhamento) +"\" conteudo=\"S\"");
									col++;
								}
								builder.addClosedNode("itemRecurso", "tam=\"3.3cm\"");
								builder.addClosedNode("recurso", "valor=\"" + builder.normalize(Util.formataMoeda(totalAprovRec)) + "\" alinhamento=\"right\" conteudo=\"S\"");

								builder.closeNode("recursos");
								builder.addNode("recursos");
								
								/*Valores Revisados*/
								nomeRecurso = "";
								valorRecurso = "Revisado";
								valorCampo = "";

								builder.addClosedNode("itemRecurso", "tam=\"2mm\"");
								builder.addClosedNode("itemRecurso", "tam=\"2.3cm\"");
								builder.addClosedNode("itemRecurso", "tam=\"2.7cm\"");
								builder.addClosedNode("recurso", "valor=\"\" alinhamento=\"justify\" conteudo=\"N\"");
								builder.addClosedNode("recurso", "valor=\"" + builder.normalize(nomeRecurso) + "\" alinhamento=\"justify\" conteudo=\"S\"");
								builder.addClosedNode("recurso", "valor=\"" + builder.normalize(valorRecurso) + "\" alinhamento=\"justify\" conteudo=\"S\"");
								
								col = 0;
								itExerc = listaExercicios.iterator();
								while (itExerc.hasNext()) {
									ExercicioExe exercicio = (ExercicioExe) itExerc.next();
									
									valorCampo = "-";
									alinhamento = "center";
									itItemRec = listaItemRec.iterator();
									while (itItemRec.hasNext()) {
										EfItemEstPrevisaoEfiep ieRecurso = (EfItemEstPrevisaoEfiep) itItemRec.next();
										if (ieRecurso.getExercicioExe().equals(exercicio) && 
													ieRecurso.getRecursoRec().equals(recurso)) {
											
											valorCampo = Pagina.trocaNullMoeda(ieRecurso.getValorRevisadoEfiep());
											alinhamento = "right";
											if(ieRecurso.getValorRevisadoEfiep() != null){
												totalRevRec += ieRecurso.getValorRevisadoEfiep().doubleValue();
												totalRevExe[col] += ieRecurso.getValorRevisadoEfiep().doubleValue();
												totRevGeral[col] += ieRecurso.getValorRevisadoEfiep().doubleValue();
											}
										}
									}
									builder.addClosedNode("itemRecurso", "tam=\"3.0cm\"");
									builder.addClosedNode("recurso", "valor=\"" + builder.normalize(valorCampo) + "\" alinhamento=\""+ builder.normalize(alinhamento) +"\" conteudo=\"S\"");
									col++;
								}
								builder.addClosedNode("itemRecurso", "tam=\"3.3cm\"");
								builder.addClosedNode("recurso", "valor=\"" + builder.normalize(Util.formataMoeda(totalRevRec)) + "\" alinhamento=\"right\" conteudo=\"S\"");
								
								
								builder.closeNode("recursos");
							}
							
							/*Mostra o Rodapé da Tabela*/
							builder.addNode("fonteRecursosRodape");
							
							/*Total Aprovado*/
							valorCampo = "";
							builder.addNode("linhaTotal");
							builder.addClosedNode("itemTotal", "tam=\"2mm\"");
							builder.addClosedNode("itemTotal", "tam=\"2.3cm\"");
							builder.addClosedNode("itemTotal", "tam=\"2.7cm\"");
							builder.addClosedNode("linha", "valor=\"\" alinhamento=\"justify\" borda=\"\" conteudo=\"N\" corFundo=\"#CCC\"");
							builder.addClosedNode("linha", "valor=\"\" alinhamento=\"justify\" borda=\"cima\" conteudo=\"N\" corFundo=\"#CCC\"");
							builder.addClosedNode("linha", "valor=\"Total Aprovado\" alinhamento=\"justify\" borda=\"cima\" conteudo=\"S\" corFundo=\"#CCC\"");

							col = 0;
							double totalAprovadoGeral = 0;
							Iterator itTotAprov = listaExercicios.iterator();
							while (itTotAprov.hasNext()) {
								ExercicioExe exercicio = (ExercicioExe) itTotAprov.next();
								
								valorCampo = Util.formataMoeda(totalAprovExe[col]);
								totalAprovadoGeral = totalAprovadoGeral + totalAprovExe[col];
								col++;
								builder.addClosedNode("itemTotal", "tam=\"3.0cm\"");
								builder.addClosedNode("linha", "valor=\"" + builder.normalize(valorCampo)+ "\" alinhamento=\"right\" borda=\"cima\" conteudo=\"S\" corFundo=\"#FFF\"");
							}
							builder.addClosedNode("itemTotal", "tam=\"3.3cm\"");
							builder.addClosedNode("linha", "valor=\"" + builder.normalize(Util.formataMoeda(totalAprovadoGeral)) + "\" alinhamento=\"right\" borda=\"cima\" conteudo=\"S\" corFundo=\"#FFF\"");
							builder.closeNode("linhaTotal");

							/*Total Revisado*/
							valorCampo = "";
							builder.addNode("linhaTotal");
							builder.addClosedNode("itemTotal", "tam=\"2mm\"");
							builder.addClosedNode("itemTotal", "tam=\"2.3cm\"");
							builder.addClosedNode("itemTotal", "tam=\"2.7cm\"");
							builder.addClosedNode("linha", "valor=\"\" alinhamento=\"justify\" borda=\"\" conteudo=\"N\" corFundo=\"#CCC\"");
							builder.addClosedNode("linha", "valor=\"\" alinhamento=\"justify\" borda=\"\" conteudo=\"N\" corFundo=\"#CCC\"");
							builder.addClosedNode("linha", "valor=\"Total Revisado\" alinhamento=\"justify\" borda=\"\" conteudo=\"S\" corFundo=\"#CCC\"");

							col = 0;
							double totalRevisadoGeral = 0;
							Iterator itTotRev = listaExercicios.iterator();
							while (itTotRev.hasNext()) {
								ExercicioExe exercicio = (ExercicioExe) itTotRev.next();
								
								valorCampo = Util.formataMoeda(totalRevExe[col]);
								totalRevisadoGeral += totalRevExe[col];
								col++;
								builder.addClosedNode("itemTotal", "tam=\"3.0cm\"");
								builder.addClosedNode("linha", "valor=\"" + builder.normalize(valorCampo)+ "\" alinhamento=\"right\" borda=\"\" conteudo=\"S\" corFundo=\"#CCC\"");
							}
							builder.addClosedNode("itemTotal", "tam=\"3.3cm\"");
							builder.addClosedNode("linha", "valor=\"" + builder.normalize(Util.formataMoeda(totalRevisadoGeral)) + "\" alinhamento=\"right\" borda=\"\" conteudo=\"S\" corFundo=\"#CCC\"");
							builder.closeNode("linhaTotal");
							
							/*Aumento/Redução*/
							valorCampo = "";
							builder.addNode("linhaTotal");
							builder.addClosedNode("itemTotal", "tam=\"2mm\"");
							builder.addClosedNode("itemTotal", "tam=\"2.3cm\"");
							builder.addClosedNode("itemTotal", "tam=\"2.7cm\"");
							builder.addClosedNode("linha", "valor=\"\" alinhamento=\"justify\" borda=\"\" conteudo=\"N\" corFundo=\"#CCC\"");
							builder.addClosedNode("linha", "valor=\"\" alinhamento=\"justify\" borda=\"\" conteudo=\"N\" corFundo=\"#CCC\"");
							builder.addClosedNode("linha", "valor=\"Aumento/Redução\" alinhamento=\"justify\" borda=\"baixo\" conteudo=\"S\" corFundo=\"#CCC\"");

							col = 0;
							Iterator itDif = listaExercicios.iterator();
							while (itDif.hasNext()) {
								ExercicioExe exercicio = (ExercicioExe) itDif.next();
								
								valorCampo = Util.formataMoeda(totalRevExe[col] - totalAprovExe[col]);
								col++;
								builder.addClosedNode("itemTotal", "tam=\"3.0cm\"");
								builder.addClosedNode("linha", "valor=\"" + builder.normalize(valorCampo)+ "\" alinhamento=\"right\" borda=\"baixo\" conteudo=\"S\" corFundo=\"#FFF\"");
							}
							
							double difTotal = totalRevisadoGeral - totalAprovadoGeral;
							
							builder.addClosedNode("itemTotal", "tam=\"3.3cm\"");
							builder.addClosedNode("linha", "valor=\"" + builder.normalize(Util.formataMoeda(difTotal)) + "\" alinhamento=\"right\" borda=\"baixo\" conteudo=\"S\" corFundo=\"#FFF\"");
							builder.closeNode("linhaTotal");

							builder.closeNode("fonteRecursosRodape");
        				}
        			}
        			
        			builder.closeNode("fonteRecurso");
        		}
        		
        		if(exibirQuadroTotais){
        			/* Início da montagem da tabela de totais gerais */
        			builder.addNode("fonteTotais");

        			/* Monta o Cabeçalho da Tabela de totais
					 * | TOTAIS | Valor | 2004 | 2005 | 2006 | 2007 | Total |
					 * */
					builder.addNode("fonteTotaisCabecalho");
					
					builder.addClosedNode("itemTotaisCabecalho", "tam=\"2.5cm\"");
					builder.addClosedNode("itemTotaisCabecalho", "tam=\"2.7cm\"");
					builder.addClosedNode("exercicioTotais", "exercicio=\"TOTAIS\" alinhamento=\"justify\"");
					builder.addClosedNode("exercicioTotais", "exercicio=\"Valor\" alinhamento=\"justify\"");
					
					int colunasExercicios = 0;
					
					Iterator itExercFinal = totaisExercicios.iterator();
					while (itExercFinal.hasNext()) {
						ExercicioExe exercicio = (ExercicioExe) itExercFinal.next();
						colunasExercicios++;
						builder.addClosedNode("itemTotaisCabecalho", "tam=\"3.0cm\"");
						builder.addClosedNode("exercicioTotais", "exercicio=\"" + builder.normalize(exercicio.getDescricaoExe()) + "\" alinhamento=\"right\"");
					}
					builder.addClosedNode("itemTotaisCabecalho", "tam=\"3.3cm\"");
					builder.addClosedNode("exercicioTotais", "exercicio=\"Total\" alinhamento=\"right\"");
    				builder.closeNode("fonteTotaisCabecalho");
    				
    				/* Monta linha de valores aprovados totais */
					/*Total Aprovado*/
					String valorCampoTotais = "";
					builder.addNode("linhaTotalGeral");
					builder.addClosedNode("itemTotalGeral", "tam=\"2mm\"");
					builder.addClosedNode("itemTotalGeral", "tam=\"2.3cm\"");
					builder.addClosedNode("itemTotalGeral", "tam=\"2.7cm\"");
					builder.addClosedNode("linhaGeral", "valor=\"\" alinhamento=\"justify\" borda=\"\" conteudo=\"N\" corFundo=\"#CCC\"");
					builder.addClosedNode("linhaGeral", "valor=\"\" alinhamento=\"justify\" borda=\"cima\" conteudo=\"N\" corFundo=\"#CCC\"");
					builder.addClosedNode("linhaGeral", "valor=\"Total Aprovado\" alinhamento=\"justify\" borda=\"cima\" conteudo=\"S\" corFundo=\"#CCC\"");

					double totalAprovadoGeral = 0;
					
					for(int col = 0; col < colunasExercicios; col++){
						valorCampoTotais = Util.formataMoeda(totAprovGeral[col]);
						totalAprovadoGeral = totalAprovadoGeral + totAprovGeral[col];
						builder.addClosedNode("itemTotalGeral", "tam=\"3.0cm\"");
						builder.addClosedNode("linhaGeral", "valor=\"" + builder.normalize(valorCampoTotais)+ "\" alinhamento=\"right\" borda=\"cima\" conteudo=\"S\" corFundo=\"#FFF\"");
					}
					builder.addClosedNode("itemTotalGeral", "tam=\"3.3cm\"");
					builder.addClosedNode("linhaGeral", "valor=\"" + builder.normalize(Util.formataMoeda(totalAprovadoGeral)) + "\" alinhamento=\"right\" borda=\"cima\" conteudo=\"S\" corFundo=\"#FFF\"");
					builder.closeNode("linhaTotalGeral");

    				/* Monta linha de valores revisados totais */
					/*Total Revisado*/
					valorCampoTotais = "";
					builder.addNode("linhaTotalGeral");
					builder.addClosedNode("itemTotalGeral", "tam=\"2mm\"");
					builder.addClosedNode("itemTotalGeral", "tam=\"2.3cm\"");
					builder.addClosedNode("itemTotalGeral", "tam=\"2.7cm\"");
					builder.addClosedNode("linhaGeral", "valor=\"\" alinhamento=\"justify\" borda=\"\" conteudo=\"N\" corFundo=\"#CCC\"");
					builder.addClosedNode("linhaGeral", "valor=\"\" alinhamento=\"justify\" borda=\"\" conteudo=\"N\" corFundo=\"#CCC\"");
					builder.addClosedNode("linhaGeral", "valor=\"Total Revisado\" alinhamento=\"justify\" borda=\"\" conteudo=\"S\" corFundo=\"#CCC\"");

					double totalRevisadoGeral = 0;

					for(int col = 0; col < colunasExercicios; col++){
						valorCampoTotais = Util.formataMoeda(totRevGeral[col]);
						totalRevisadoGeral = totalRevisadoGeral + totRevGeral[col];
						builder.addClosedNode("itemTotalGeral", "tam=\"3.0cm\"");
						builder.addClosedNode("linhaGeral", "valor=\"" + builder.normalize(valorCampoTotais)+ "\" alinhamento=\"right\" borda=\"\" conteudo=\"S\" corFundo=\"#CCC\"");
					}
					
					builder.addClosedNode("itemTotalGeral", "tam=\"3.3cm\"");
					builder.addClosedNode("linhaGeral", "valor=\"" + builder.normalize(Util.formataMoeda(totalRevisadoGeral)) + "\" alinhamento=\"right\" borda=\"\" conteudo=\"S\" corFundo=\"#CCC\"");
					builder.closeNode("linhaTotalGeral");
					
    				/* Monta linha de valores Aumento/Redução totais */
					/*Aumento/Redução*/
					valorCampoTotais = "";
					builder.addNode("linhaTotalGeral");
					builder.addClosedNode("itemTotalGeral", "tam=\"2mm\"");
					builder.addClosedNode("itemTotalGeral", "tam=\"2.3cm\"");
					builder.addClosedNode("itemTotalGeral", "tam=\"2.7cm\"");
					builder.addClosedNode("linhaGeral", "valor=\"\" alinhamento=\"justify\" borda=\"\" conteudo=\"N\" corFundo=\"#CCC\"");
					builder.addClosedNode("linhaGeral", "valor=\"\" alinhamento=\"justify\" borda=\"baixo\" conteudo=\"N\" corFundo=\"#CCC\"");
					builder.addClosedNode("linhaGeral", "valor=\"Aumento/Redução\" alinhamento=\"justify\" borda=\"baixo\" conteudo=\"S\" corFundo=\"#CCC\"");


					for(int col = 0; col < colunasExercicios; col++){
						valorCampoTotais = Util.formataMoeda(totRevGeral[col] - totAprovGeral[col]);
						
						builder.addClosedNode("itemTotalGeral", "tam=\"3.0cm\"");
						builder.addClosedNode("linhaGeral", "valor=\"" + builder.normalize(valorCampoTotais)+ "\" alinhamento=\"right\" borda=\"baixo\" conteudo=\"S\" corFundo=\"#FFF\"");
					}
					
					double difTotal = totalRevisadoGeral - totalAprovadoGeral;
					
					builder.addClosedNode("itemTotalGeral", "tam=\"3.3cm\"");
					builder.addClosedNode("linhaGeral", "valor=\"" + builder.normalize(Util.formataMoeda(difTotal)) + "\" alinhamento=\"right\" borda=\"baixo\" conteudo=\"S\" corFundo=\"#FFF\"");
					builder.closeNode("linhaTotalGeral");
        			builder.closeNode("fonteTotais");
        			/* Fim da montagem da tabela de totais gerais */
        		}
        		
        		builder.closeNode("fontesRecurso");
        	}
        	
        } catch (Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: " + label);            
        }
    }
    
    /**
     * Soma total Aprovado Revisado.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param fonteRecurso 
     * @param tipo
     * @return String
     * @throws ECARException
     */
    public String somarTotalAprovadoRevisado(EfIettFonteTotEfieft fonteRecurso, String tipo) throws ECARException{
    	ItemEstruturaFonteRecursoDao fonteRecursoDao = new ItemEstruturaFonteRecursoDao(null);
    	double total = fonteRecursoDao.getSomaRecursosFonteRecurso(fonteRecurso, tipo);
        return Util.formataMoeda(total); 
    }

    /**
     * Gera pontos_criticos xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item 
     * @param label
     * @param exibirApontamentos
     * @throws ECARException
     */
    public void geraXMLPontos_Criticos(XmlBuilder builder, ItemEstruturaIett item, String label, 
    		boolean exibirApontamentos) throws ECARException{
        try{            
            if(item.getPontoCriticoPtcs().size() > 0){
                builder.addNode("pontosCriticos", "label=\"" + builder.normalize(label) + "\"");
                Iterator it = item.getPontoCriticoPtcs().iterator();
                while(it.hasNext()){
                    PontoCriticoPtc ponto = (PontoCriticoPtc) it.next();
                    String ambito = "";
                    if("I".equals(ponto.getIndAmbitoInternoGovPtc()))
                        ambito = "Interno";
                    if("E".equals(ponto.getIndAmbitoInternoGovPtc()))
                        ambito = "Externo";
                    String nomeUsuario = "";
                    if(ponto.getUsuarioUsu() != null)
                        nomeUsuario = ponto.getUsuarioUsu().getNomeUsuSent();
                    
                    /*
                    builder.addClosedNode("pontoCritico", "dataIdentificacao=\"" + Pagina.trocaNullData(ponto.getDataIdentificacaoPtc()) + "\" descricao=\"" 
                            + builder.normalize(Pagina.trocaNull(ponto.getDescricaoPtc())) + "\" ambitoDoGoverno=\"" + builder.normalize(ambito) + "\" dataLimiteSolucao=\""
                            + Pagina.trocaNullData(ponto.getDataLimitePtc()) + "\" sugestao=\"" + builder.normalize(Pagina.trocaNull(ponto.getDescricaoSolucaoPtc())) + "\" "
                            + " dataSolucao=\"" + Pagina.trocaNullData(ponto.getDataSolucaoPtc()) + "\" responsavel=\"" + builder.normalize(nomeUsuario) + "\"");
                    */
                    builder.addNode("pontoCritico", "dataIdentificacao=\"" + Pagina.trocaNullData(ponto.getDataIdentificacaoPtc()) + "\" descricao=\"" 
                            + builder.normalize(Pagina.trocaNull(ponto.getDescricaoPtc())) + "\" ambitoDoGoverno=\"" + builder.normalize(ambito) + "\" dataLimiteSolucao=\""
                            + Pagina.trocaNullData(ponto.getDataLimitePtc()) + "\" sugestao=\"" + builder.normalize(Pagina.trocaNull(ponto.getDescricaoSolucaoPtc())) + "\" "
                            + " dataSolucao=\"" + Pagina.trocaNullData(ponto.getDataSolucaoPtc()) + "\" responsavel=\"" + builder.normalize(nomeUsuario) + "\"");
                    if(exibirApontamentos && ponto.getApontamentoApts() != null && ponto.getApontamentoApts().size() > 0){
                    	Iterator itApontamentos = ponto.getApontamentoApts().iterator();
                    	while(itApontamentos.hasNext()){
                    		ApontamentoApt apontamento = (ApontamentoApt) itApontamentos.next();
                    		builder.addClosedNode("apontamento", "dataInclusao=\"" + Pagina.trocaNullData(apontamento.getDataInclusaoApt()) + "\"" +
                    				" texto=\"" + builder.normalize(apontamento.getTextoApt()) + "\""); 
                    	}
                    }
                    builder.closeNode("pontoCritico");
                }        
                builder.closeNode("pontosCriticos");                    
            }            
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: " + label);              
        }
    }
    
    /**
     * Gera categorias xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @param label
     * @param exibirItensAnexo
     * @param exibirCategoriaSemItemAnexo
     * @throws ECARException
     */
    public void geraXMLCategorias(XmlBuilder builder, ItemEstruturaIett item, String label, 
    		boolean exibirItensAnexo, boolean exibirCategoriaSemItemAnexo) throws ECARException{
        try{
        	Collection categoriasAtivas = new ItemEstruturaUploadCategoriaDao(request).getAtivos(item);
        	
        	if(categoriasAtivas != null && categoriasAtivas.size() > 0){
        		        		        		
            	builder.addNode("categorias", "label=\"" + builder.normalize(label) + "\"");
        		Iterator itCategorias = item.getItemEstrUplCategIettucs().iterator();
        		while (itCategorias.hasNext()){
        			ItemEstrUplCategIettuc categoria = (ItemEstrUplCategIettuc) itCategorias.next();
    				if (!categoria.getIndAtivoIettuc().equals("S") || ( !exibirCategoriaSemItemAnexo && (categoria.getItemEstrutUploadIettups() == null || categoria.getItemEstrutUploadIettups().isEmpty()))) {
    					continue;
    				}

    				String nomeCat = categoria.getNomeIettuc(); 
        			String tipoCat = "";
        			if (categoria.getUploadTipoCategoriaUtc() != null){
        				tipoCat = categoria.getUploadTipoCategoriaUtc().getNomeUtc();
        			}
        			builder.addNode("categoria", "nome=\"" + builder.normalize(nomeCat) + "\"" +
        					" tipo=\"" + builder.normalize(tipoCat) + "\"");
        			if (exibirItensAnexo){
        				if (categoria.getItemEstrutUploadIettups() != null && !categoria.getItemEstrutUploadIettups().isEmpty()) {
        					Iterator itAnexos = categoria.getItemEstrutUploadIettups().iterator();
        					
        					String nomeAnexo = "";
    						String descAnexo = "";
    						String tamanhoAnexo = "";
    						
        					while (itAnexos.hasNext()){
        						ItemEstrutUploadIettup anexo = (ItemEstrutUploadIettup) itAnexos.next();
        						
        						if (anexo.getIndAtivoIettup().equals("S")){
        						
	        						if (anexo.getNomeOriginalIettup() != null)
	        							nomeAnexo = anexo.getNomeOriginalIettup();
	        						
	        						if (anexo.getDescricaoIettup() != null)
	        							descAnexo = anexo.getDescricaoIettup();
	        						
	        						if (anexo.getTamanhoIettup() != null)
	        							tamanhoAnexo = Util.formataByte(anexo.getTamanhoIettup());
	        						
	        						builder.addClosedNode("anexo", "nomeOriginal=\"" + builder.normalize(nomeAnexo) + "\"" +
	        								" descricao=\"" + builder.normalize(descAnexo) + "\"" +
	        								" tamanho=\"" + builder.normalize(tamanhoAnexo) + "\"");
        						}
        					}
        				}
        			}
        			builder.closeNode("categoria");
        		}
            	builder.closeNode("categorias");
        	}
        } catch (Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: " + label);            
        }
    }

    /**
     * Gera contas do orçamento xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item 
     * @param label
     * @throws ECARException
     */
//    public void geraXMLContas(XmlBuilder builder, ItemEstruturaIett item, String label) throws ECARException {
      public void geraXMLContas_do_Orcamento(XmlBuilder builder, ItemEstruturaIett item, String label) throws ECARException {
        try{
            if (item.getEfItemEstContaEfiecs().size() > 0){
                builder.addNode("contas", "label=\"" + builder.normalize(label) + "\" nomeColunaConta=\"" + builder.normalize(ItemEstruturaContaOrcamentoDao.geraLabelCadastroEstruturaConta(null)) + "\"");
                Iterator it = item.getEfItemEstContaEfiecs().iterator();
                while(it.hasNext()){
                    EfItemEstContaEfiec conta = (EfItemEstContaEfiec) it.next();
                    String acumulado = "";
                    if("S".equals(conta.getIndAcumuladoEfiec()))
                        acumulado = "Sim";
                    if("N".equals(conta.getIndAcumuladoEfiec()))
                        acumulado = "Não";
                    
                    builder.addClosedNode("conta", "fonteRecurso=\"" + builder.normalize(Pagina.trocaNull(conta.getFonteRecursoFonr().getNomeFonr())) + "/" + builder.normalize(Pagina.trocaNull(conta.getRecursoRec().getNomeRec()))
                            + "\" exercicio=\"" + builder.normalize(Pagina.trocaNull(conta.getExercicioExe().getDescricaoExe())) + "\" acumulado=\"" 
                            + builder.normalize(acumulado) + "\" estrutura=\"" + builder.normalize(Pagina.trocaNull(conta.getContaSistemaOrcEfiec())) + "\"");
                    
                }        
                builder.closeNode("contas");                              
            }                                
        }catch(ECARException e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: " + label);    
        }        
    }

      /**
       * Gera indicadores de resultado XML.<br>
       * 
       * @author N/C
       * @since N/C
       * @version N/C
       * @param builder
       * @param item 
       * @param label
       * @param mostrarQuantidades
       * @throws ECARException
       */
      public void geraXMLIndicadores_Resultado(XmlBuilder builder, ItemEstruturaIett item, String label, boolean mostrarQuantidades) throws ECARException{
        try{
            if(item.getItemEstrtIndResulIettrs().size() > 0){
                builder.addNode("indicadoresResultado", "label=\"" + label + "\"");
                Iterator itIndicadores = item.getItemEstrtIndResulIettrs().iterator();        
                while(itIndicadores.hasNext()){
                    ItemEstrtIndResulIettr indicador = (ItemEstrtIndResulIettr) itIndicadores.next();
                    String projecoes = "";
                    String totalizacoes = "";
                    String valorFinal = "";
                    if("S".equals(indicador.getIndProjecaoIettr()))
                        projecoes = "Sim";
                    if("N".equals(indicador.getIndProjecaoIettr()))
                        projecoes = "Não";
                    //double total = 0;
                    String strTotal = "";
                    if("S".equals(indicador.getIndAcumulavelIettr())){
                        totalizacoes = "Sim";
                        //total = new ItemEstrtIndResulDao(null).getSomaQuantidades(indicador);
                        //strTotal = String.valueOf(total);
                    }   
                    else {
                    	if("M".equals(indicador.getIndValorFinalIettr()))
                    		valorFinal = "Maior";
                    	else if("U".equals(indicador.getIndValorFinalIettr()))
                    		valorFinal = "Último";
                    	else if("N".equals(indicador.getIndValorFinalIettr()))
                    		valorFinal = "Não se aplica";
                    	strTotal = new ItemEstrtIndResulDao(null).getSomaQuantidadePrevista(indicador);
                    	/*
                    	if(!"".equals(strTotal)){
                    		total = new Double(strTotal).doubleValue();
                    		strTotal = Util.formataMoeda(total);
                    	}
                    	*/
                    }
                    
                	strTotal = new ItemEstrtIndResulDao(null).getSomaQuantidadePrevista(indicador);
                	/*if(!"".equals(strTotal)){
                		total = new Double(strTotal).doubleValue();
                		strTotal = Util.formataNumeroSemDecimal(total);
                	}*/
                                       
                    if("N".equals(indicador.getIndAcumulavelIettr())){                    
                        totalizacoes = "Não";                
                    }                    
                    builder.addNode("indicador",
                    		"nome=\"" + builder.normalize(Pagina.trocaNull(indicador.getNomeIettir())) + "\"" +
                    		" descricao=\"" + builder.normalize(Pagina.trocaNull(indicador.getDescricaoIettir())) + "\"" +
                    		" unidade=\"" + builder.normalize(Pagina.trocaNull(indicador.getUnidMedidaIettr())) + "\"" +
                    		" totalizacoes=\"" + builder.normalize(totalizacoes) + "\"" +
                    		" valorFinal=\"" + builder.normalize(valorFinal) + "\"" + 
                    		" projecoes=\"" + builder.normalize(projecoes) + "\"" +
                    		" total = \"" + builder.normalize(strTotal) + "\"");
                    
                    if(mostrarQuantidades){
                    	List exercicios = new ArrayList(indicador.getItemEstrutFisicoIettfs());
                    	
                    	//Mantis 0010128 - Qtd prevista não é mais informado por exercício
                    	/*
                    	Collections.sort(exercicios,
							new Comparator() {
				        		public int compare(Object o1, Object o2) {
				        		    return ( (ItemEstrutFisicoIettf)o1 ).getExercicioExe().getDescricaoExe().compareToIgnoreCase( ( (ItemEstrutFisicoIettf)o2 ).getExercicioExe().getDescricaoExe() );
				        		}
				    		} );
                    	*/
	                    //Iterator it = indicador.getItemEstrutFisicoIettfs().iterator();
	                    Iterator it = exercicios.iterator();                
	                    while(it.hasNext()){
	                        ItemEstrutFisicoIettf exercicio = (ItemEstrutFisicoIettf) it.next();
	                        //Mantis 0010128 - Qtd prevista não é mais informado por exercício
	                        //builder.addClosedNode("exercicio", "descricao=\"" + builder.normalize(Pagina.trocaNull(exercicio.getExercicioExe().getDescricaoExe())) + "\" quantidade=\"" + Pagina.trocaNullNumeroSemDecimal(exercicio.getQtdPrevistaIettf().toString()) + "\"");
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
       * Gera filhos xml.<br>
       * 
       * @author N/C
       * @since N/C
       * @version N/C
       * @param builder
       * @param item
       * @param tipoRelatorio
       * @param orgao
       * @throws ECARException
       */
    public void geraXMLFilhos(XmlBuilder builder, ItemEstruturaIett item, String tipoRelatorio, String orgao) throws ECARException{
        try{
            Set descendentes = item.getItemEstruturaIetts();
            
            if(descendentes != null && descendentes.size() > 0){
                Iterator it = descendentes.iterator();            
                while(it.hasNext()){
                    ItemEstruturaIett itemFilho = (ItemEstruturaIett) it.next();
            
            		/* TODO: Remover esta validação quando terminar o processo do PPA.
            		 * 
            		 * "No relatório "Resumo", não exibir itens do nível de subproduto
            		 * Isto só será usando durante o processo PPA. Após este processo, essa verificação será retirada. 
            		 * Este comentário foi feito para facilitar a retirada deste código após o término do processo do PPA. 
            		 */
            		boolean exibirItem = true;
            		if("R".equals(tipoRelatorio)){
            			exibirItem = false;
            			if(itemFilho.getNivelIett() != null && itemFilho.getNivelIett().intValue() != 5){ //5 = Nível de Subproduto.
            				exibirItem = true;
            			}
            		}
            		
            		if(!"S".equals(contextoEcarPPA))
            			exibirItem = true;
                    
                    if(exibirItem){
						String nomeNivelItem = builder.normalize(itemFilho.getEstruturaEtt().getNomeEtt()) + ": ";
						
						String nomeItem = " ";
	
						if(itemFilho.getSiglaIett() != null && !"".equals(itemFilho.getSiglaIett()))
							nomeItem += itemFilho.getSiglaIett() + " - ";
						
						/* TODO: Remover esta validação quando terminar o processo do PPA.
						 * 
						 * Este if é referente ao item 4 do Mantis 5160:
						 * "No relatório "Resumo", no nível da ação, Substituir o Nome da ação pelo Nome PPA"
						 * Isto só será usando durante o processo PPA. Após este processo, essa verificação será retirada. 
						 * Este comentário foi feito para facilitar a retirada deste código após o término do processo do PPA. 
						 */
						boolean buscarNomePPA = false;
						if("R".equals(tipoRelatorio)){
							if(itemFilho.getNivelIett() != null && (itemFilho.getNivelIett().intValue() == 3 || itemFilho.getNivelIett().intValue() == 4)){ //3 = Nível de Ação, 4 = Nível de Produto
								buscarNomePPA = true;
							}
						}
	
						if(!"S".equals(contextoEcarPPA))
							buscarNomePPA = false;
						
						if(buscarNomePPA)
							nomeItem += builder.normalize(itemFilho.getDescricaoR3());
						else
							nomeItem += builder.normalize(itemFilho.getNomeIett());
						
	
						builder.addNode("filho", "nomeNivel=\"" + builder.normalize(nomeNivelItem) + "\" nome=\"" + nomeItem + "\" " +
	                                    " nivel=\"" + itemFilho.getNivelIett().intValue() + "\"");
	
						builder.closeNode("filho");
						
						this.incrementarTotalizador(itemFilho.getEstruturaEtt().getCodEtt(), nomeNivelItem);
	
	                    boolean mostrarDados = false;
	                    
	                    if("".equals(orgao) || orgao == null){ //Todos
	                    	mostrarDados = true;
	                    }
	                    else if (orgao != null && itemFilho.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null && orgao.equals(itemFilho.getOrgaoOrgByCodOrgaoResponsavel1Iett().getCodOrg().toString())){
	                    	mostrarDados = true;
	                    }
	                    
	                    if (!itensMarcados.contains(itemFilho.getCodIett().toString())){
	                    	mostrarDados = false;
	                    }
	                    
	                    if(mostrarDados){
	                		geraXMLDadosBasicos(builder, itemFilho, tipoRelatorio);
	                    
	                		if("C".equals(tipoRelatorio)){
	                			//geraXMLNiveisPlanejamento(builder, itemFilho);
	                		}
	                    
	                    	geraXMLFuncoes(builder, itemFilho, tipoRelatorio, true);
	                    }
	                    geraXMLFilhos(builder, itemFilho, tipoRelatorio, orgao);
	                    //builder.closeNode("filho");
	                }   
            	}
            }            
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: Listagem de Itens Filho");
        }            
    }

    /**
     * Gera filhos por orgao xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item 
     * @param orgao
     * @param tipoRelatorio 
     * @param listaCriteriosCom
     * @param listaCriteriosSem
     * @param listaSituacoes
     * @throws ECARException
     */
    public void geraXMLFilhosPorOrgao(XmlBuilder builder, ItemEstruturaIett item, String tipoRelatorio, 
    		String orgao, List listaCriteriosCom, List listaCriteriosSem, List listaSituacoes) throws ECARException{
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
        	
            String submitPPA = "";
            if("S".equals(contextoEcarPPA) && "R".equals(tipoRelatorio)){
            	submitPPA = "S";
            }
            List itensTemp = new ArrayList(itemEstruturaDao.filtrarRelatorioItemEstrutura(filhos, orgaoResponsavel.getCodOrg(), listaCriteriosCom, listaCriteriosSem, listaSituacoes, "N", submitPPA));

            filhos.clear();
        	//filhos.addAll(itemEstruturaDao.getArvoreItens(itensTemp, null));
        	filhos.addAll(itensTemp);
         
        	Iterator it;
        	
        	//remover os itens superiores ao nível atual
        	if(!imprimirEstrutura){
	    		it = filhos.iterator();
	    		while(it.hasNext()) {
	    			ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
	    			
	    			if(iett.getNivelIett().intValue() < item.getNivelIett().intValue()) {
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
                while(it.hasNext()){
                	
                    //ItemEstruturaIett itemFilho = (ItemEstruturaIett) it.next();
                	AtributoEstruturaListagemItens atbList = (AtributoEstruturaListagemItens) it.next();
                	ItemEstruturaIett itemFilho = atbList.getItem();
                    
            		/* TODO: Remover esta validação quando terminar o processo do PPA.
            		 * 
            		 * "No relatório "Resumo", não exibir itens do nível de subproduto
            		 * Isto só será usando durante o processo PPA. Após este processo, essa verificação será retirada. 
            		 * Este comentário foi feito para facilitar a retirada deste código após o término do processo do PPA. 
            		 */
            		boolean exibirItem = true;
            		if("R".equals(tipoRelatorio)){
            			exibirItem = false;
            			if(itemFilho.getNivelIett() != null && itemFilho.getNivelIett().intValue() != 5){ //5 = Nível de Subproduto.
            				exibirItem = true;
            			}
            		}
            		
            		if(!"S".equals(contextoEcarPPA))
            			exibirItem = true;
            		
                	if(exibirItem){
                    
						String nomeNivelItem = builder.normalize(itemFilho.getEstruturaEtt().getNomeEtt()) + ": ";
						String nomeItem = " ";
						String itemSelecionado = "S";
	
						if(itemFilho.getSiglaIett() != null && !"".equals(itemFilho.getSiglaIett()))
							nomeItem += itemFilho.getSiglaIett() + " - ";
						
						/* TODO: Remover esta validação quando terminar o processo do PPA.
						 * 
						 * Este if é referente ao item 4 do Mantis 5160:
						 * "No relatório "Resumo", no nível da ação, Substituir o Nome da ação pelo Nome PPA"
						 * Isto só será usando durante o processo PPA. Após este processo, essa verificação será retirada. 
						 * Este comentário foi feito para facilitar a retirada deste código após o término do processo do PPA. 
						 */
						boolean buscarNomePPA = false;
						if("R".equals(tipoRelatorio)){
							if(itemFilho.getNivelIett() != null && (itemFilho.getNivelIett().intValue() == 3 || itemFilho.getNivelIett().intValue() == 4)){ //3 = Nível de Ação, 4 = Nível de Produto
								buscarNomePPA = true;
							}
						}
	
						if(!"S".equals(contextoEcarPPA))
							buscarNomePPA = false;
						
						if(buscarNomePPA)
							nomeItem += builder.normalize(itemFilho.getDescricaoR3());
						else{
							//nomeItem += builder.normalize(itemFilho.getNomeIett());
							nomeItem = builder.normalize(" " + ("".equals(atbList.getDescricao().trim()) ? itemFilho.getNomeIett() : atbList.getDescricao()));
						}
						
						if (!itensMarcados.contains(itemFilho.getCodIett().toString()))
							itemSelecionado = "N";
						
						builder.addNode("filho", "nomeNivel=\"" + builder.normalize(nomeNivelItem) + "\" nome=\"" + nomeItem + "\" " +
	                                    " nivel=\"" + itemFilho.getNivelIett().intValue() + "\"" +
	                                    " itemSelecionado=\"" + builder.normalize(itemSelecionado) + "\"");
	
						builder.closeNode("filho");
	
						this.incrementarTotalizador(itemFilho.getEstruturaEtt().getCodEtt(), nomeNivelItem);
						
	                    boolean mostrarDados = false;
	                    
	                    if("".equals(orgao) || orgao == null){ //Todos
	                    	mostrarDados = true;
	                    }
	                    else if (orgao != null && itemFilho.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null && orgao.equals(itemFilho.getOrgaoOrgByCodOrgaoResponsavel1Iett().getCodOrg().toString())){
	                    	mostrarDados = true;
	                    }
	                    
	                    if (!itensMarcados.contains(itemFilho.getCodIett().toString())){
	                    	mostrarDados = false;
	                    }
	                    
	                    if(mostrarDados){
	                		geraXMLDadosBasicos(builder, itemFilho, tipoRelatorio);
	                    
	                		if("C".equals(tipoRelatorio)){
	                			//geraXMLNiveisPlanejamento(builder, itemFilho);
	                		}
	                    
	                    	geraXMLFuncoes(builder, itemFilho, tipoRelatorio, true);
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
     * @param builder
     * @author N/C
     * @since N/C
     * @version N/C
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
     * Descobre tipo.<br>
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
        return "itemEstrutura.xsl";
    }
    
    /**
     * Retorna pagina de erro.<br>
     * 
     * @param request 
     * @param mensagem
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getErrorPage(HttpServletRequest request, String mensagem){        
        String errorPage = "frm_rel.jsp?codIett=" + Pagina.getParamStr(request, "codIett") + "&msgOperacao=" + mensagem + "&codAba="+Pagina.getParamStr(request, "codAba"); 
        return errorPage;
    }

    /**
     * Busca Funcao Pai.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param ItemEstruturaIett item
     * @param Long codigo
     * @return EstruturaFuncaoEttf
     * @throws ECARException
     */
    private EstruturaFuncaoEttf buscarFuncaoPai(ItemEstruturaIett item, Long codigo) throws ECARException {
    	try {
    		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(null);
			
			EstruturaFuncaoEttfPK chave = new EstruturaFuncaoEttfPK();
			chave.setCodEtt(item.getEstruturaEtt().getCodEtt());
			chave.setCodFun(codigo);
			EstruturaFuncaoEttf funcaoPai = (EstruturaFuncaoEttf) estruturaFuncaoDao.buscar(EstruturaFuncaoEttf.class, chave);
			
			return funcaoPai;
    	} catch (Exception e) {
    		return null;
    	}
    }
    
    /**
     * Verifica funcao.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param String funcao
     * @param List funcoes
     * @param String tipoRelatorio
     * @return boolean
     */
    private boolean verificarFuncao(String funcao, List funcoes, String tipoRelatorio){
		Iterator itFuncoes = funcoes.iterator();
		boolean retorno = false;
		while(itFuncoes.hasNext()){
			EstruturaFuncaoEttf funcaoParalela = (EstruturaFuncaoEttf) itFuncoes.next();
			if(funcao.equals(funcaoParalela.getFuncaoFun().getNomeFun())){

				if("S".equals(funcaoParalela.getIndListagemImpressaResEttf()) && "R".equals(tipoRelatorio)){
					retorno = true;
	                break;
                }
                if("S".equals(funcaoParalela.getIndListagemImpressCompEttf()) && "C".equals(tipoRelatorio)){
                	retorno = true;
	                break;
                }
			}
		}
    	return retorno;
    }
    
    /**
     * Incrementa Totalizador.<br>
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
