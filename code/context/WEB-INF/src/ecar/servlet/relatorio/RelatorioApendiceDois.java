/*
 * Created on 19/05/2006
 *
 */
package ecar.servlet.relatorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;
import comum.util.XmlBuilder;

import ecar.bean.FonteRecursosPPA;
import ecar.bean.IndResulExercicioBean;
import ecar.bean.TotalizadorRelatorios;
import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstLocalRevIettlrDAO;
import ecar.dao.ItemEstrtIndResulDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.ItemEstruturaPrevisaoDao;
import ecar.exception.ECARException;
import ecar.pojo.EfIettFonteTotEfieft;
import ecar.pojo.EfItemEstPrevisaoEfiep;
import ecar.pojo.ExercicioExe;
import ecar.pojo.IettIndResulRevIettrr;
import ecar.pojo.ItemEstFisicoRevIettfr;
import ecar.pojo.ItemEstLocalRevIettlr;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrutFisicoIettf;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturarevisaoIettrev;
import ecar.util.Dominios;

/**
 * @author aleixo
 *
 */
public class RelatorioApendiceDois extends AbstractServletReportXmlXsl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2395457474744685932L;
	private static final int nivelPrograma = 2;
	private static final int nivelAcao = 3;
	private static final int nivelProduto = 4;
	
	private ItemEstruturaDao itemEstruturaDao;
	private ItemEstLocalRevIettlrDAO itemEstLocalDao;
	private ItemEstruturaPrevisaoDao itemEstruturaPrevisaoDao;
	private ExercicioDao exercicioDao;
	private ItemEstrtIndResulDao indResulDao;
	
	private String periodoIni;
	private String periodoFim;
	private int paginaInicial;
	private String titulo;
	private List todosItens;
	private String tipoRelatorio;
	private List idsFontesRecursos;
	private List fontesRecursosPPAs;
	private String tipoValor;
	
	private List idsEstrutura;
	private List totalizadorEstrutura;
	private List idsValores;
	private List totalizadorValores;
	
	private double totalGeralProgramas;
	private double totalGeralAcoes;
	
	/**
	 * Gera XML.<br>
	 * 
         * @param request
         * @author N/C
     * @since N/C
     * @version N/C
	 * @return StringBuffer
	 * @throws ECARException
	 */
	public StringBuffer getXml(HttpServletRequest request) throws ECARException{
		XmlBuilder builder = new XmlBuilder();
		itemEstruturaDao = new ItemEstruturaDao(request);
		itemEstLocalDao = new ItemEstLocalRevIettlrDAO(request);
		itemEstruturaPrevisaoDao = new ItemEstruturaPrevisaoDao(request);
		exercicioDao = new ExercicioDao(request);
		indResulDao = new ItemEstrtIndResulDao(request);
		
		idsFontesRecursos = new ArrayList();
		fontesRecursosPPAs = new ArrayList();
		idsEstrutura = new ArrayList();
		idsValores = new ArrayList();
		totalizadorEstrutura = new ArrayList();
		totalizadorValores = new ArrayList();
		
		totalGeralAcoes = 0;
		totalGeralProgramas = 0;
		
		periodoIni = Pagina.getParamStr(request, "periodoIni");
		periodoFim = Pagina.getParamStr(request, "periodoFim");
		paginaInicial = Pagina.getParamInt(request, "paginaInicial");
		tipoRelatorio = Pagina.getParamStr(request, "indTipoRelatorio");
		tipoValor = Pagina.getParamStr(request, "indTipoValor");
		
		titulo = "ESTADO DO PARANÁ - PPA " + periodoIni + "/" + periodoFim + " - Atualizado";
		
		Util.liberarImagem();
		
		String nomeRelatorio = "";
		String valorRelatorio = ""; //A = Valores Aprovados, R = Valores Revisados (Não aparece este último pq é o oficial
		String indMostrarTotalizador = Pagina.getParamStr(request, "indMostrarTotalizador");

		if("A".equals(tipoValor)){
			valorRelatorio = " - Valores Aprovados (Histórico)";
		}

		nomeRelatorio = "REVISÃO DO PPA " + periodoIni + "-" + periodoFim + " - PPA ATUALIZADO";
		
		String mesAnoGeracao = Data.getNomeMesExtenso(Data.getMes(Data.getDataAtual()) + 1).toUpperCase() + " " + String.valueOf(Data.getAno(Data.getDataAtual()));
		
		builder.addNode("relatorio", 
				" titulo=\"" + builder.normalize(titulo) + "\"" +
				" capa=\"" + builder.normalize(String.valueOf(paginaInicial)) + "\"" +
				" paginaInicial=\"" + builder.normalize(String.valueOf(paginaInicial+3)) + "\"" +
				" nomeRelatorio=\"" + builder.normalize(nomeRelatorio) + "\"" +
				" nomeRelatorioRodape=\"" + builder.normalize(valorRelatorio) + "\"" +
				" tipo=\"" + builder.normalize(tipoRelatorio) + "\"" +
				" anoIni=\"" + builder.normalize(periodoIni) + "\"" +
				" anoFim=\"" + builder.normalize(periodoFim) + "\"" +
				" valores=\"" + builder.normalize(tipoValor) + "\"" +
				" mesAnoGeracao=\"" + builder.normalize(mesAnoGeracao) + "\"" +
				" mostrarTotalizador=\"" + builder.normalize(indMostrarTotalizador) + "\"");
		
		todosItens = new ArrayList(itemEstruturaDao.getArvoreItensIettComRevisao(tipoRelatorio, periodoIni, periodoFim));
		
		geraXMLItens(builder);
		
		geraXMLTotalizador(builder);
		
		geraXMLTotalizadorValores(builder);
		
		builder.closeNode("relatorio");
        return builder.toStringBuffer();
    }
    

	/**
	 * pega o nome do arq xsl.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
    public String getXslFileName() {
        return "relatorioApendice.xsl";
    }
    
    /**
     * Pega o erro de pagina.<br>
     * 
     * @param request 
     * @param mensagem
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getErrorPage(HttpServletRequest request, String mensagem){        
        String errorPage = "relatorios/ctrl_ppa.jsp?msgOperacao=" + mensagem; 
        return errorPage;
    }
    
    /**
     * Gera itens XML.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @throws ECARException
     */
    public void geraXMLItens (XmlBuilder builder) throws ECARException {
    	List itens = new ArrayList(todosItens);
    	
    	if(itens != null){
    		Iterator itItens = itens.iterator();
    		while(itItens.hasNext()){
    			ItemEstruturaIett iett = (ItemEstruturaIett) itItens.next();
    			
    			if(iett.getNivelIett().intValue() == nivelPrograma){
    				//if(verificarProgramaApresentaFilhos(iett))
    					geraXMLPrograma(builder, iett);
    			}
    		}
    	}
    }
    
    /**
     * gera tag Programa no XML.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder 
     * @param item
     * @throws ECARException
     */
    public void geraXMLPrograma(XmlBuilder builder, ItemEstruturaIett item) throws ECARException{

    	//this.incrementarTotalizadorEstrutura(item.getEstruturaEtt().getCodEtt(), "Quantidade de Programas");
    	String nomePrograma = item.getNomeIett();
    	String codigoPrograma = item.getSiglaIett();
    	
    	builder.addNode("programa", 
    			"nome=\"" + builder.normalize(nomePrograma) + "\"" +
    			" codigo=\"" + builder.normalize(codigoPrograma) + "\"");
    	
    	geraXMLDadosPrograma(builder, item);
    	
   		geraXMLValoresFinanceiros(builder, item);
    	
    	builder.addNode("acao", 
    			"periodo=\"" + builder.normalize(periodoIni + "-" + periodoFim) + "\"" +
    			" tipoApendice=\"" + builder.normalize(tipoRelatorio) + "\"");
    	
    	//Para cada Programa, gerar as ações...
    	List itensAcao = new ArrayList(todosItens);
    	Iterator itAcao = itensAcao.iterator();
    	boolean gerouAcao = false;
    	while(itAcao.hasNext()){
    		ItemEstruturaIett iettAcao = (ItemEstruturaIett) itAcao.next();
    		if(iettAcao.getNivelIett().intValue() == nivelAcao && item.equals(iettAcao.getItemEstruturaIett())){
    			boolean aux = geraXMLAcao(builder, iettAcao);
    			gerouAcao = gerouAcao || aux;
    			/*
    			 * Foi necessário criar uma variavel, pois se fizer:
    			 * gerouAcao = gerouAcao || geraXMLAcao() e gerouAcao for setada como true,
    			 * nas próximas vezes o método que gera as ações nem é executado.
    			 */
    		}
    	}
    	
    	if(gerouAcao)
    		this.incrementarTotalizadorEstrutura(item.getEstruturaEtt().getCodEtt(), "Quantidade de Programas");
    	
    	builder.closeNode("acao");
    	
    	builder.closeNode("programa");
    }

    /**
     * Gera dados XML Programa.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @throws ECARException
     */
    public void geraXMLDadosPrograma(XmlBuilder builder, ItemEstruturaIett item) throws ECARException{
    	builder.addNode("dados");
    	String orgao = "";
    	if(item.getOrgaoOrgByCodOrgaoResponsavel2Iett() != null)
    		orgao = item.getOrgaoOrgByCodOrgaoResponsavel2Iett().getDescricaoOrg();
    	
    	//String objetivo = item.getObjetivoGeralIett();
    	String objetivo = item.getObjetivoEspecificoIett(); //Segundo conversa com Igor, é para pegar objetivo específico.
    	String publicoAlvo = item.getDescricaoR2();
    	builder.addClosedNode("campo", "label=\"Órgão Resp.\" valor=\"" + builder.normalize(orgao) + "\"");
    	builder.addClosedNode("campo", "label=\"Objetivo\" valor=\"" + builder.normalize(objetivo) + "\"");
    	builder.addClosedNode("campo", "label=\"Público-Alvo\" valor=\"" + builder.normalize(publicoAlvo) + "\"");
    	builder.closeNode("dados");
    }

    /**
     * Gera valores financeiros XML.<br>
     * 
     * @param builder
     * @param item
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
    public void geraXMLValoresFinanceiros(XmlBuilder builder, ItemEstruturaIett item) throws ECARException{
    	builder.addNode("valores-financeiros", "periodo=\"" + periodoIni + "-" + periodoFim + "\"");
    	/*
    	valores financeiros são somados todos os valores revisados de todas as ações do programa no período
    	
    	*/
    	geraXMLValoresFinanceirosItem(builder, item);
    	
    	builder.closeNode("valores-financeiros");
    }
    
    /**
     * Gera  valor finaceiro itens xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @throws ECARException
     */
    public void geraXMLValoresFinanceirosItem(XmlBuilder builder, ItemEstruturaIett item) throws ECARException{
		//Soma todos os valoresRevisados das revisoes de todas as ações do programa no periodo,
		//Separando por Correntes e Capital (ver rel. impresso)
    	
    	String valor = "";
    	double valorTotal = 0;
    	limparIdsFontesRecursos();
    	
    	List itensTemp = new ArrayList(todosItens);
    	Iterator itTudo = itensTemp.iterator();
    	while(itTudo.hasNext()){
    		ItemEstruturaIett iett = (ItemEstruturaIett) itTudo.next();
    		if(nivelAcao == iett.getNivelIett().intValue() && iett.getItemEstruturaIett().equals(item)){
    			
    			
    			ItemEstruturarevisaoIettrev ultRevAcao = getUltimaRevisaoIett(iett.getItemEstruturarevisaoIettrevs());
    			
    			if(ultRevAcao != null && "E".equals(ultRevAcao.getSituacaoIettrev())){
    				//Se a ação possuir revisão com situacao = Excluída, não contabilizar os valores porque esta ação não irá aparecer no relatorio.
    				continue;
    			}
    			
    			/* 
    			 * Se for valores aprovados e a ação tiver revisões, a ação não será gerada, portanto seus valores serão ignorados.*/
    			if("A".equals(tipoValor)){
    				if(ultRevAcao != null){
    					continue;
    				}
    			}

    			List listFontes = new ArrayList(iett.getEfIettFonteTotEfiefts());
    			Iterator itFontes = listFontes.iterator();
    			while(itFontes.hasNext()){
    				EfIettFonteTotEfieft fonte = (EfIettFonteTotEfieft) itFontes.next();

	    			valor = "0";
    				List listaRecursos = itemEstruturaPrevisaoDao.getRecursosByFonteRecurso(fonte.getFonteRecursoFonr().getCodFonr(), fonte.getItemEstruturaIett().getCodIett(), Dominios.SIM);
    				Iterator itRecursos = listaRecursos.iterator();
    				while(itRecursos.hasNext()){
    					EfItemEstPrevisaoEfiep recurso = (EfItemEstPrevisaoEfiep) itRecursos.next();
    	    			
    	    			if(recurso.getExercicioExe() != null &&
    	    			   recurso.getExercicioExe().getDataInicialExe() != null &&
    	    			   recurso.getExercicioExe().getDataFinalExe() != null
    	    			){
    	    				int exeAnoIni = Data.getAno(recurso.getExercicioExe().getDataInicialExe());
    	    				int exeAnoFim = Data.getAno(recurso.getExercicioExe().getDataFinalExe());
    	    				if(exeAnoIni >= Integer.parseInt(periodoIni) && exeAnoFim <= Integer.parseInt(periodoFim)){
    	    					if("A".equals(tipoValor)){
    	    						valor = String.valueOf(recurso.getValorAprovadoEfiep());
    	    						totalGeralProgramas += recurso.getValorAprovadoEfiep().doubleValue();
    	    					}
    	    					if("R".equals(tipoValor)){
    	    						valor = String.valueOf(recurso.getValorRevisadoEfiep());
    	    						totalGeralProgramas += recurso.getValorRevisadoEfiep().doubleValue();
    	    					}
    	        				incrementarFonteRecurso(fonte.getFonteRecursoFonr().getCodFonr(), fonte.getFonteRecursoFonr().getNomeFonr(), Double.valueOf(valor).doubleValue());
    	        				incrementarTotalizadorValor(fonte.getFonteRecursoFonr().getCodFonr(), fonte.getFonteRecursoFonr().getNomeFonr(), Double.valueOf(valor).doubleValue());
    	    				}
    	    			}
    				}
    			}
    		}
    	}
    	
    	Iterator itFontesPPA = fontesRecursosPPAs.iterator();
    	while(itFontesPPA.hasNext()){
    		FonteRecursosPPA fonteIncluida = (FonteRecursosPPA) itFontesPPA.next();
			builder.addClosedNode("valor", 
					"label=\"" + builder.normalize(fonteIncluida.getLabel()) + "\"" +
					" valor=\"" + builder.normalize("R$ " + Util.formataMoeda(fonteIncluida.getValor())) + "\"");
			valorTotal +=  fonteIncluida.getValor();
    	}
    	    	
    	builder.addClosedNode("valor", "label=\"Total\" valor=\"" + builder.normalize("R$ " + Util.formataMoeda(valorTotal)) + "\"");
    	
    }
    
    /**
     * gera tag Ação no XML.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @return boolean
     * @throws ECARException
     */   
  
    public boolean geraXMLAcao(XmlBuilder builder, ItemEstruturaIett item) throws ECARException{

		String nome = "";
    	String situacaoAcao = "";
    	String tipoSituacaoAcao = "";
    	
    	boolean gerouAcao = false;
    	boolean acaoExcluida = false;
    	boolean gerarAcao = true;
    	
    	if(item.getItemEstruturarevisaoIettrevs() != null && item.getItemEstruturarevisaoIettrevs().size() > 0){
    		ItemEstruturarevisaoIettrev ultRevAcao = getUltimaRevisaoIett(item.getItemEstruturarevisaoIettrevs());
    		if(ultRevAcao != null){
    			tipoSituacaoAcao = ultRevAcao.getSituacaoIettrev();
    			
    			if(ultRevAcao.getDescricaoR3rev() != null && !"".equals(ultRevAcao.getDescricaoIettrev()))
    				nome = ultRevAcao.getDescricaoR3rev();
    			else
    				nome = ultRevAcao.getNomeIettrev();
    			
    			if("I".equals(tipoSituacaoAcao))
    				situacaoAcao = " (Ação Incluída)";
    			if("A".equals(tipoSituacaoAcao))
    				situacaoAcao = " (Ação Alterada)";
    			if("E".equals(tipoSituacaoAcao)){
    				acaoExcluida = true;
    			}
    			
    			//Se for valores aprovados (histórico) e possuir revisão, não gerar ação.
    			if("A".equals(tipoValor)){
    				gerarAcao = false;
    			}
    		}
    	}

    	if(!acaoExcluida && gerarAcao){
    		if("".equals(nome)){
				if(item.getDescricaoR3() != null && !"".equals(item.getDescricaoR3()))
					nome = item.getDescricaoR3();
				else
					nome = item.getNomeIett();
    		}
			
			String valorTotal = "";
			if("A".equals(tipoValor)){
				valorTotal = somaValoresAprovadosAcao(item);
			}
			if("R".equals(tipoValor)){
				valorTotal = somaValoresRevisadosAcao(item);
			}
				
	    	builder.addNode("itemAcao", 
	    			//"nome=\"" + builder.normalize(item.getNomeIett()) + "\"" +
	    			"nome=\"" + builder.normalize(nome.toUpperCase()) + "\"" +
	    			" codigo=\"" + builder.normalize(item.getSiglaIett()) + "\"" +
	    			" valorTotal=\""+ builder.normalize(valorTotal) +"\"" +
	    			" tipoSituacaoAcao=\"" + builder.normalize(tipoSituacaoAcao) + "\"" +
	    			" situacaoAcao=\"" + builder.normalize(situacaoAcao) + "\"" +
	    			" justificativa=\"\"" +  //É necessário colocar este campo, pois ele é usado no Apendice 3, e tanto o Apendice 2 como o Apendice 3 utilizam o mesmo XSL.
	    			" larguraColAcao=\"21cm\"" + 
	    			" larguraColAux=\"0.1mm\"");
	    	/*valorTotal é a soma dos valores revisados da ação no período*/

	    	this.incrementarTotalizadorEstrutura(item.getEstruturaEtt().getCodEtt(), "Quantidade de Ações");	
	    	gerouAcao = true;
	    	
	    	//Para cada Ação, gerar os produtos...
	    	List itensProduto = new ArrayList(todosItens);
	    	Iterator itProduto = itensProduto.iterator();
	    	boolean gerouProduto = false;
	    	while(itProduto.hasNext()){
	    		ItemEstruturaIett iettProduto = (ItemEstruturaIett) itProduto.next();
	    		if(iettProduto.getNivelIett().intValue() == nivelProduto && item.equals(iettProduto.getItemEstruturaIett())){
	    			boolean aux = geraXMLProduto(builder, iettProduto);
	    			gerouProduto = gerouProduto || aux;
	    			/*
	    			 * Foi necessário criar uma variavel, pois se fizer:
	    			 * gerouProduto = gerouProduto || geraXMLProduto() e gerouProduto for setada como true,
	    			 * nas próximas vezes o método que gera os produtos nem é executado.
	    			 */
	    			
	    		}
	    	}
	    	
	    	//gerouAcao = gerouProduto;
	    	
	    	//if(gerouAcao)
		   // 	this.incrementarTotalizadorEstrutura(item.getEstruturaEtt().getCodEtt(), "Quantidade de Ações");
		    	
	    	builder.closeNode("itemAcao");
    	}

    	return gerouAcao;
    }
    
    /**
     * Verifica produtos excluidos.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param acao
     * @return boolean
     */
    public boolean verificarProdutosExcluidos(ItemEstruturaIett acao){
    	List itensProduto = new ArrayList(todosItens);
    	Iterator itProduto = itensProduto.iterator();
    	while(itProduto.hasNext()){
    		ItemEstruturaIett produto = (ItemEstruturaIett) itProduto.next();
    		if(produto.getNivelIett().intValue() == nivelProduto && acao.equals(produto.getItemEstruturaIett())){
    			if(produto.getItemEstruturarevisaoIettrevs() != null && produto.getItemEstruturarevisaoIettrevs().size() > 0){
    				Iterator itRevisoesProduto = produto.getItemEstruturarevisaoIettrevs().iterator();
    				while(itRevisoesProduto.hasNext()){
    					ItemEstruturarevisaoIettrev revProduto = (ItemEstruturarevisaoIettrev) itRevisoesProduto.next();
    					if("E".equals(revProduto.getSituacaoIettrev())){
    						return true;
    					}
    				}
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * Soma todos os valoresRevisados da ação no periodo.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param item
     * @return String
     * @throws ECARException
     */
    public String somaValoresRevisadosAcao(ItemEstruturaIett item) throws ECARException{
   		//Soma todos os valoresRevisados da ação no periodo.
    	double valorTotal = 0;
		List iettsFontes = new ArrayList(item.getEfIettFonteTotEfiefts());
		Iterator itFontes = iettsFontes.iterator();
		while(itFontes.hasNext()){
			EfIettFonteTotEfieft fonte = (EfIettFonteTotEfieft) itFontes.next();
			Iterator itRecursos = itemEstruturaPrevisaoDao.getRecursosByFonteRecurso(fonte.getFonteRecursoFonr().getCodFonr(), item.getCodIett(), Dominios.SIM).iterator();
			while(itRecursos.hasNext()){
				EfItemEstPrevisaoEfiep recurso = (EfItemEstPrevisaoEfiep) itRecursos.next();
    			if(recurso.getExercicioExe() != null &&
   			       recurso.getExercicioExe().getDataInicialExe() != null &&
    	    	   recurso.getExercicioExe().getDataFinalExe() != null
    	    	){
    				int exeAnoIni = Data.getAno(recurso.getExercicioExe().getDataInicialExe());
    				int exeAnoFim = Data.getAno(recurso.getExercicioExe().getDataFinalExe());
    				if(exeAnoIni >= Integer.parseInt(periodoIni) && exeAnoFim <= Integer.parseInt(periodoFim)){
    					valorTotal += recurso.getValorRevisadoEfiep().doubleValue();
    				}
    			}
			}
		}
		totalGeralAcoes += valorTotal;
		return "R$ " + Util.formataMoeda(valorTotal);
    }

    /**
     * Soma todos os valoresAprovados da ação no periodo.<br>
     * 
     * @param item
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     * @throws ECARException
     */
    public String somaValoresAprovadosAcao(ItemEstruturaIett item) throws ECARException{
   		//Soma todos os valoresRevisados da ação no periodo.
    	double valorTotal = 0;
		List iettsFontes = new ArrayList(item.getEfIettFonteTotEfiefts());
		Iterator itFontes = iettsFontes.iterator();
		while(itFontes.hasNext()){
			EfIettFonteTotEfieft fonte = (EfIettFonteTotEfieft) itFontes.next();
			Iterator itRecursos = itemEstruturaPrevisaoDao.getRecursosByFonteRecurso(fonte.getFonteRecursoFonr().getCodFonr(), item.getCodIett(), Dominios.SIM).iterator();
			while(itRecursos.hasNext()){
				EfItemEstPrevisaoEfiep recurso = (EfItemEstPrevisaoEfiep) itRecursos.next();
    			if(recurso.getExercicioExe() != null &&
   			       recurso.getExercicioExe().getDataInicialExe() != null &&
    	    	   recurso.getExercicioExe().getDataFinalExe() != null
    	    	){
    				int exeAnoIni = Data.getAno(recurso.getExercicioExe().getDataInicialExe());
    				int exeAnoFim = Data.getAno(recurso.getExercicioExe().getDataFinalExe());
    				if(exeAnoIni >= Integer.parseInt(periodoIni) && exeAnoFim <= Integer.parseInt(periodoFim)){
    					valorTotal += recurso.getValorAprovadoEfiep().doubleValue();
    				}
    			}
			}
		}
		totalGeralAcoes += valorTotal;
		return "R$ " + Util.formataMoeda(valorTotal);
    }

    /**
     * gera tag Produto no XML.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @return boolean
     * @throws ECARException
     */
    public boolean geraXMLProduto(XmlBuilder builder, ItemEstruturaIett item) throws ECARException{
    	boolean gerouProduto = false;
    	
    	String nome = "";
    	String tipo = "";
    	String unidade = "";
    	String tipoSituacao = "";
    	String regiao = "";
    	String valor = "";

		/* Se for Valores Aprovados, pegar valores dos indicadores do item (qtdes previstas)*/
    	if("A".equals(tipoValor)){

    		/*
    		 * Se for valores aprovados, mostrar itens que não possuem revisão.*/
    		ItemEstruturarevisaoIettrev ultRevProduto = getUltimaRevisaoIett(item.getItemEstruturarevisaoIettrevs());
    		if(ultRevProduto == null){
	        	List indicadores = new ArrayList(item.getItemEstrtIndResulIettrs());
	        	if(indicadores != null && indicadores.size() > 0){
		    		Iterator itInd = indicadores.iterator();
		    		while(itInd.hasNext()){
		    			ItemEstrtIndResulIettr indicador = (ItemEstrtIndResulIettr) itInd.next();
		    			
		    			nome = item.getDescricaoR3(); //NomePPA
		    			tipo = indicador.getNomeIettir();
		    			unidade = indicador.getUnidMedidaIettr();
	
		    			if(item.getItemEstrutLocalIettls() != null && item.getItemEstrutLocalIettls().size() > 0){
		    				List lLocal = new ArrayList(item.getItemEstrutLocalIettls());
		    				ItemEstrutLocalIettl local = (ItemEstrutLocalIettl) lLocal.get(0);
		    				//regiao = local.getLocalItemLit().getLocalGrupoLgp().getIdentificacaoLgp();
		    				regiao = local.getLocalItemLit().getIdentificacaoLit();
		    			}
	
		    			tipoSituacao = "";
		    			
		    			valor = indResulDao.getSomaQuantidadePrevista(indicador);
		    			
		    			geraXMLItemProduto(builder, item, nome, tipo, unidade, tipoSituacao, regiao, valor, "");
		    			gerouProduto = true;
		    		}
	        	}
    		}
    	}
    	/* Se for Valores Revisados, pegar valores dos indicadores das revisões (qtdes revisadas)*/
    	else if ("R".equals(tipoValor)){
    		
    		/* Alteração ref. Mantis 5879.
    		 * 
    		 * Caso o item tenha sido modificado ou incluído, então valem as metas físicas da revisão. 
    		 * Se o item não passou por mudança, então valem as quantidades da tabela original.
    		 * */
    		List revisoes = new ArrayList(item.getItemEstruturarevisaoIettrevs());
    		if(revisoes.isEmpty()){
    			//Se não possui revisões, pegar dados da tabela original
	        	List indicadores = new ArrayList(item.getItemEstrtIndResulIettrs());
	        	if(indicadores != null && indicadores.size() > 0){
		    		Iterator itInd = indicadores.iterator();
		    		while(itInd.hasNext()){
		    			ItemEstrtIndResulIettr indicador = (ItemEstrtIndResulIettr) itInd.next();
		    			
		    			nome = item.getDescricaoR3(); //NomePPA
		    			tipo = indicador.getNomeIettir();
		    			unidade = indicador.getUnidMedidaIettr();
	
		    			if(item.getItemEstrutLocalIettls() != null && item.getItemEstrutLocalIettls().size() > 0){
		    				List lLocal = new ArrayList(item.getItemEstrutLocalIettls());
		    				ItemEstrutLocalIettl local = (ItemEstrutLocalIettl) lLocal.get(0);
		    				//regiao = local.getLocalItemLit().getLocalGrupoLgp().getIdentificacaoLgp();
		    				regiao = local.getLocalItemLit().getIdentificacaoLit();
		    			}
	
		    			tipoSituacao = "";
		    			
		    			valor = indResulDao.getSomaQuantidadePrevista(indicador);
		    			
		    			geraXMLItemProduto(builder, item, nome, tipo, unidade, tipoSituacao, regiao, valor, "");
		    			gerouProduto = true;
		    		}
	        	}
    		}
    		else {
    			//Possui revisões
    			
	    		Iterator itRevisoes = revisoes.iterator();
	    		while(itRevisoes.hasNext()){
	    			ItemEstruturarevisaoIettrev iettRev = (ItemEstruturarevisaoIettrev) itRevisoes.next();
	    			
	    			/*
	    			 * Ref Mantis 6264:
	    			 * 1) Ao somar as quantidades do exercícios das metas físicas de revisão, se um exercício 
	    			 * estiver nulo/não foi informado então buscar o valor de exercício correspondente na 
	    			 * meta física do item.
	    			 * Ex.: Na revisão foi informado apenas o ano 2004. Deve-se somar o ano de 2004 da revisão 
	    			 * com os anos 2005, 2006, 2007 informado no cadastro de item.
	    			 * 
	    			 * 2) Ao somar as quantidades, respeitar as regras do Indicador (se permite 
	    			 * totalização, maior, último, etc..)
	    			 * 
	    			 * O exercício da meta física do item é o que foi informado na meta física de revisão.
	    			 * Conforme conversa com elmar em 17/08/2006, se esta meta física do item não foi 
	    			 * informada da meta física de revisão, deve-se para a geração do relatório e exibir
	    			 * mensagem de erro com o item que falta a informação.
	    			 */
	    			
	    			if(iettRev.getIettIndResulRevIettrrs() != null && iettRev.getIettIndResulRevIettrrs().size() > 0){
		    			//Se a revisão possui indicadores de revisão, fazer um "merge" entre os valores da meta física
		    			//de revisão e a meta física do item. Ex.: Na revisão foi informado apenas o ano 2004. 
		    			//Deve-se somar o ano de 2004 da revisão com os anos 2005, 2006, 2007 
		    			//informado no cadastro de item.
	    				Iterator itIndRev = iettRev.getIettIndResulRevIettrrs().iterator();
	    				while(itIndRev.hasNext()){
	    					IettIndResulRevIettrr indRev = (IettIndResulRevIettrr) itIndRev.next();
	    					
	    					nome = "";
	    					if(iettRev.getDescricaoR3rev() != null && !"".equals(iettRev.getDescricaoR3rev()))
	    						nome = iettRev.getDescricaoR3rev();
	    					else
	    						nome = iettRev.getNomeIettrev();
	    					
	    					if("".equals(nome)){
	    						if(item.getDescricaoR3() != null && !"".equals(item.getDescricaoR3()))
	    							nome = item.getDescricaoR3(); //NomePPA
	    						else
	    							nome = item.getNomeIett();
	    					}
	    					
    						/*
    						 * Ref Mantis 6010
    						 * Se a revisão não possuir o indicador informado, pegar os dados
    						 * do primeiro indicador do item informado
    						 * 
    						 * NOVA REGRA: Ref. Mantis 6264, se a revisão possui meta física
    						 * mas a meta física de revisão não possui associação com o indicador
    						 * do item, então exibir mensagem de erro e para a geração de relatório.
    						 */
	    					
	    					
	    					if(indRev.getItemEstrtIndResulIettr() == null)
	    						throw new ECARException("Erro na geração do Relatório do Apêndice 2 do PPA: O Item \"" + item.getSiglaIett() + "-" + item.getNomeIett() + "\" possui Meta Física de Revisão sem tipo informado.");	
	    					
	    					tipo = indRev.getItemEstrtIndResulIettr().getNomeIettir();
	    					unidade = indRev.getItemEstrtIndResulIettr().getUnidMedidaIettr();
	    					valor = somaValoresMetasRevisadas(indRev);
	    					
	    					//regiao = itemEstLocalDao.getAbrangencia(iettRev.getCodIettrev());
	    					
	    					if(iettRev.getItemEstLocalRevIettlrs() != null && iettRev.getItemEstLocalRevIettlrs().size() > 0){
	    						List lLocal = new ArrayList(iettRev.getItemEstLocalRevIettlrs());
	    						ItemEstLocalRevIettlr local = (ItemEstLocalRevIettlr) lLocal.get(0);
	    						regiao = local.getLocalItemLit().getIdentificacaoLit();
	    					}
	    					else {
	    						//Se a revisão não possui locais cadastrados, buscar local do item. Ref Mantis: 6010
	    						if(item.getItemEstrutLocalIettls() != null && item.getItemEstrutLocalIettls().size() > 0){
	    							List lLocal = new ArrayList(item.getItemEstrutLocalIettls());
	    							ItemEstrutLocalIettl local = (ItemEstrutLocalIettl) lLocal.get(0);
	    							regiao = local.getLocalItemLit().getIdentificacaoLit();
	    						}
	    					}
	    					
	    					tipoSituacao = iettRev.getSituacaoIettrev();
	    					
	    					if(!"E".equals(tipoSituacao)){
		    					geraXMLItemProduto(builder, item, nome, tipo, unidade, tipoSituacao, regiao, valor, "");
		    					gerouProduto = true;
	    					}
	    				}
	    			}
	    			else{
	    				//Se a revisão não possui indicadores na revisão, 
	    				//somar somente os indicadores do item da revisão
    					nome = "";
    					if(iettRev.getDescricaoR3rev() != null && !"".equals(iettRev.getDescricaoR3rev()))
    						nome = iettRev.getDescricaoR3rev();
    					else
    						nome = iettRev.getNomeIettrev();
    					
    					if("".equals(nome)){
    						if(item.getDescricaoR3() != null && !"".equals(item.getDescricaoR3()))
    							nome = item.getDescricaoR3(); //NomePPA
    						else
    							nome = item.getNomeIett();
    					}
    					/*
    					 * Ref. Mantis 6010:
  						 * Se a revisão não possuir o indicador informado, pegar os dados
 						 * do primeiro indicador do item informado
 						 * 
 						 * Como aqui a revisão não possui metas físicas cadastradas, valem os valores
 						 * e regras do indicador do item. 
    					 */
    					tipo = "não informado";
    					unidade = "não informado";
    					valor = "-";
    					
    					List indResulIett = new ArrayList(item.getItemEstrtIndResulIettrs());
    					if(indResulIett != null && !indResulIett.isEmpty()){
    						ItemEstrtIndResulIettr indResul = (ItemEstrtIndResulIettr) indResulIett.get(0);
    						tipo = indResul.getNomeIettir();
    						unidade = indResul.getUnidMedidaIettr();
    						valor = indResulDao.getSomaQuantidadePrevista(indResul);
    					}
    					
    					//regiao = itemEstLocalDao.getAbrangencia(iettRev.getCodIettrev());
    					if(iettRev.getItemEstLocalRevIettlrs() != null && iettRev.getItemEstLocalRevIettlrs().size() > 0){
    						List lLocal = new ArrayList(iettRev.getItemEstLocalRevIettlrs());
    						ItemEstLocalRevIettlr local = (ItemEstLocalRevIettlr) lLocal.get(0);
    						regiao = local.getLocalItemLit().getIdentificacaoLit();
    					}
    					else {
    						//Se a revisão não possui locais cadastrados, buscar local do item. Ref Mantis: 6010
    						if(item.getItemEstrutLocalIettls() != null && item.getItemEstrutLocalIettls().size() > 0){
    							List lLocal = new ArrayList(item.getItemEstrutLocalIettls());
    							ItemEstrutLocalIettl local = (ItemEstrutLocalIettl) lLocal.get(0);
    							regiao = local.getLocalItemLit().getIdentificacaoLit();
    						}
    					}
    					
    					tipoSituacao = iettRev.getSituacaoIettrev();
    					
    					if(!"E".equals(tipoSituacao)){
	    					geraXMLItemProduto(builder, item, nome, tipo, unidade, tipoSituacao, regiao, valor, "");
	    					gerouProduto = true;
    					}
	    			}
	    		}
    		}
    	}
    	return gerouProduto;
    }

    /**
     * Gera item produto xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item 
     * @param nome 
     * @param unidade
     * @param tipoSituacao 
     * @param tipo
     * @param regiao
     * @param valor
     * @param justificativa
     */
    public void geraXMLItemProduto(XmlBuilder builder, ItemEstruturaIett item, String nome, String tipo, 
    		String unidade, String tipoSituacao, String regiao, String valor, String justificativa){
    	String situacao = "";
    	if("I".equals(tipoSituacao)){
			situacao = "(Incluído)";
		}
		if("A".equals(tipoSituacao)){
			situacao = "(Alterado)";
		}

    	this.incrementarTotalizadorEstrutura(item.getEstruturaEtt().getCodEtt(), "Quantidade de Produtos");
		
    	builder.addClosedNode("produto", 
    			"nome=\"" + builder.normalize(nome) + "\" " +
    			"tipo=\"" + builder.normalize(tipo) + "\" " +
    			"unidade=\"" + builder.normalize(unidade) + "\" " +
    			"situacao=\"" + builder.normalize(situacao) + "\" " +
    			"tipoSituacao=\"" + builder.normalize(tipoSituacao) + "\" " +
    			"regiao=\"" + builder.normalize(regiao) + "\" " +
    			"valor=\"" + builder.normalize(valor) + "\" " +
    			"justificativa=\"" + builder.normalize(justificativa) + "\" " +
    			"tipoRelatorio=\"" + builder.normalize(tipoRelatorio) + "\"");
    }
    /**
     * Com base na tabela de exercícios do sistema, o método verifica se o exercicio existe
     * na meta fisica de revisão.<br>
     * Se existir soma.<br>
     * Se não existir verifica se o exercício existe na meta física do item.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param metaRevisao
     * @return String - Soma das quantidades previstas revisadas
     * @throws ECARException
     */
    public String somaValoresMetasRevisadas(IettIndResulRevIettrr metaRevisao) throws ECARException{
    	List qtdesPrevistas = new ArrayList();
    	
    	List exerciciosValidos = exercicioDao.listar(ExercicioExe.class, new String[]{"descricaoExe","desc"});
    	Iterator itExe = exerciciosValidos.iterator();
    	while(itExe.hasNext()){
    		ExercicioExe exe = (ExercicioExe) itExe.next();
    		if(exe.getDataInicialExe() != null && exe.getDataFinalExe() != null){
				int exeAnoIni = Data.getAno(exe.getDataInicialExe());
				int exeAnoFim = Data.getAno(exe.getDataFinalExe());
				
				//Verifica se o exercício está dentro do período informado
				if(exeAnoIni >= Integer.parseInt(periodoIni) && exeAnoFim <= Integer.parseInt(periodoFim)){
					
					boolean possuiExercicioNaRevisao = false;
					
					//Procura valor do exercício nas metas físicas de revisão
					if(metaRevisao.getItemEstFisicoRevIettfrs() != null && metaRevisao.getItemEstFisicoRevIettfrs().size() > 0){
						Iterator itEstRev = metaRevisao.getItemEstFisicoRevIettfrs().iterator();
						while(itEstRev.hasNext()){
							ItemEstFisicoRevIettfr estRev = (ItemEstFisicoRevIettfr) itEstRev.next();
							if(exe.equals(estRev.getExercicioExe()) && "S".equals(estRev.getIndAtivoIettfr())){
								possuiExercicioNaRevisao = true;
								IndResulExercicioBean indResulExeBean = new IndResulExercicioBean();
								indResulExeBean.setCodExe(estRev.getExercicioExe().getCodExe());
								indResulExeBean.setValor(estRev.getQtdPrevistaIettfr());
								
								qtdesPrevistas.add(indResulExeBean);
								break;
							}
						}
					}
					
					if(!possuiExercicioNaRevisao){
						//Procura valor do exercício nas metas físicas do item
						Iterator itEstItem = metaRevisao.getItemEstrtIndResulIettr().getItemEstrutFisicoIettfs().iterator();
						while(itEstItem.hasNext()){
							ItemEstrutFisicoIettf estItem = (ItemEstrutFisicoIettf) itEstItem.next();
							//Mantis 0010128 - Qtd prevista não é mais informado por exercício
							/*
							if(exe.equals(estItem.getExercicioExe()) && "S".equals(estItem.getIndAtivoIettf())){
								IndResulExercicioBean indResulExeBean = new IndResulExercicioBean();
								indResulExeBean.setCodExe(estItem.getExercicioExe().getCodExe());
								indResulExeBean.setValor(estItem.getQtdPrevistaIettf());
								
								qtdesPrevistas.add(indResulExeBean);
								break;
							}
							*/
						}
					}
					
				}//if validacao periodo exe
    		} //if validacao exe
    	} //while exe
    	
    	//Verificar na lista de totais as regras do indicador...
    	String retorno = "0";
    	String indAcumulavel = metaRevisao.getItemEstrtIndResulIettr().getIndAcumulavelIettr();
    	String indValorFinal = metaRevisao.getItemEstrtIndResulIettr().getIndValorFinalIettr();
    	
    	/* =========== Verificação das regras de totalização ============== */
        double total = 0;
        
        if ("S".equals(indAcumulavel)){
            Iterator it = qtdesPrevistas.iterator();
            while (it.hasNext()) {
            	IndResulExercicioBean valor = (IndResulExercicioBean) it.next();
           		total += valor.getValor().doubleValue();
            }
            retorno = Util.formataNumeroSemDecimal(total);
        }else{
        	
        	/*
        	 * Anotação ref. Mantis 5016:
        	 * - Maior: obter o maior valor de ItemEstrutFisicoIettf
        	 * - Último: obter valor do último exercício informado de ItemEstrutFisicoIettf
        	 * - Não se aplica: soma total ItemEstrutFisicoIettf
        	 */
        	
        	if("M".equals(indValorFinal)){ //Maior
   	            Iterator it = qtdesPrevistas.iterator();
	            double maior = 0;
	            while (it.hasNext()) {
	            	IndResulExercicioBean valor = (IndResulExercicioBean) it.next();
            		if(valor.getValor().doubleValue() > maior){
            			maior = valor.getValor().doubleValue();
            		}
            		total = maior;
	            }
	            retorno = Util.formataNumeroSemDecimal(total);
        	}
        	else if("U".equals(indValorFinal)){ //Ultimo
	            double ultimo = 0;
        		ExercicioExe ultimoExe = indResulDao.getMaiorExercicioIndicador(metaRevisao.getItemEstrtIndResulIettr());
        		
   	            Iterator it = qtdesPrevistas.iterator();
	            while (it.hasNext()) {
	            	IndResulExercicioBean valor = (IndResulExercicioBean) it.next();
            		if(valor.getCodExe().equals(ultimoExe.getCodExe())){
            			ultimo = valor.getValor().doubleValue();
            			break;
            		}
	            }
    	        retorno = Util.formataNumeroSemDecimal(ultimo);
        	}
        	else if("N".equals(indValorFinal)){ //Não se aplica
        		retorno = "Não se aplica";
        	}
        }
    	/* =========== Fim da Verificação das regras de totalização ============== */
    	
    	return retorno;
    }

    /**
     * Pega ultima revisão iett.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param listaRevisoes
     * @return ItemEstruturarevisaoIettrev
     */
    public ItemEstruturarevisaoIettrev getUltimaRevisaoIett(Set listaRevisoes){
    	ItemEstruturarevisaoIettrev retorno = null;
    	if(listaRevisoes != null && listaRevisoes.size() > 0){
    		List revisoes = new ArrayList(listaRevisoes);
    		Collections.sort(revisoes,
    			new Comparator(){
					public int compare(Object o1, Object o2) {
						ItemEstruturarevisaoIettrev iett1 = (ItemEstruturarevisaoIettrev) o1;
						ItemEstruturarevisaoIettrev iett2 = (ItemEstruturarevisaoIettrev) o2;
						
						/*
						String data1 = Data.parseDate(iett1.getDataInclusaoIettrev());
						String data2 = Data.parseDate(iett2.getDataInclusaoIettrev());
						
						//dd/mm/yyyy -> yyyymmaa
						data1 = data1.substring(6) + data1.substring(3, 5) + data1.substring(0, 2);
						data2 = data2.substring(6) + data2.substring(3, 5) + data2.substring(0, 2);
						
						return data1.compareTo(data2);*/
						return iett1.getDataInclusaoIettrev().compareTo(iett2.getDataInclusaoIettrev());
					}	
    			}
    		);
    		ItemEstruturarevisaoIettrev ultimoIettRev = (ItemEstruturarevisaoIettrev) revisoes.get(revisoes.size() - 1);
    		retorno = ultimoIettRev;
    	}
    	
    	return retorno;
    }
    
    /**
     * Verifica programa Apresenta Filhos.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param item
     * @return boolean
     */
    public boolean verificarProgramaApresentaFilhos(ItemEstruturaIett item){
    	boolean achou = false;
    	List lTodos = new ArrayList(todosItens);
    	Iterator it = lTodos.iterator();
    	while(it.hasNext()){
    		ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
    		if(iett.getItemEstruturaIett() != null && iett.getItemEstruturaIett().equals(item)){
    			achou = true;
    			break;
    		}
    	}
    	
    	//if (!achou)
    		//System.out.println("Programa sem filhos: " + item.getSiglaIett() + " - " + item.getNomeIett());
    	
    	return true;
    }
    
    /**
     * Incrementa fonte Recurso.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param idFonteRecurso
     * @param valor 
     * @param label
     * 
     */
    public void incrementarFonteRecurso(Long idFonteRecurso, String label, double valor){
    	if(!idsFontesRecursos.contains(idFonteRecurso)){
    		FonteRecursosPPA fonteBean = new FonteRecursosPPA();
    		fonteBean.setId(idFonteRecurso);
    		fonteBean.setLabel(label);
    		fonteBean.setValor(valor);
    		
    		idsFontesRecursos.add(idFonteRecurso);
    		fontesRecursosPPAs.add(fonteBean);
    	}
    	else {
    		Iterator itFontesBean = fontesRecursosPPAs.iterator();
    		while(itFontesBean.hasNext()){
    			FonteRecursosPPA fonteBean = (FonteRecursosPPA) itFontesBean.next();
    			if(fonteBean.getId().equals(idFonteRecurso)){
    				fonteBean.somaValor(valor);
    				break;
    			}
    		}
    	}
    }
    
    /**
     * Limpa id's das fontes recursos.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void limparIdsFontesRecursos(){
    	idsFontesRecursos.clear();
    	fontesRecursosPPAs.clear();
    }
    
    /**
     * Incrementa totalizador estrutura.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Long idEstrutura
     * @param String nomeEstrutura
     */
    private void incrementarTotalizadorEstrutura(Long idEstrutura, String nomeEstrutura){
    	if(!idsEstrutura.contains(idEstrutura)){
    		TotalizadorRelatorios totalizador = new TotalizadorRelatorios();
    		totalizador.setId(idEstrutura);
    		totalizador.setEstrutura(nomeEstrutura);
    		totalizador.setTotal(1);
    		
    		idsEstrutura.add(idEstrutura);
    		totalizadorEstrutura.add(totalizador);
    	}
    	else {
    		Iterator itTotalizadores = totalizadorEstrutura.iterator();
    		while(itTotalizadores.hasNext()){
    	   		TotalizadorRelatorios totalizador = (TotalizadorRelatorios) itTotalizadores.next();
    	   	    
    	   		if(idEstrutura.equals(totalizador.getId())){
    	   			totalizador.incrementeTotal();
    	   			break;
    	   	    }
    		}
    	}
    }
    
    /**
     * Incrementa totalizador valor.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Long idValor
     * @param String nomeValor
     * @param double valor
     */
    private void incrementarTotalizadorValor(Long idValor, String nomeValor, double valor){
    	if(!idsValores.contains(idValor)){
    		FonteRecursosPPA totalizador = new FonteRecursosPPA();
    		totalizador.setId(idValor);
    		totalizador.setLabel(nomeValor);
    		totalizador.setValor(valor);
    		
    		idsValores.add(idValor);
    		totalizadorValores.add(totalizador);
    	}
    	else {
    		Iterator itTotalizadores = totalizadorValores.iterator();
    		while(itTotalizadores.hasNext()){
    	   		FonteRecursosPPA totalizador = (FonteRecursosPPA) itTotalizadores.next();
    	   		if(idValor.equals(totalizador.getId())){
    	   			totalizador.somaValor(valor);
    	   			break;
    	   	    }
    		}
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
        	
        	Collections.sort(totalizadorEstrutura,
        			new Comparator(){
						public int compare(Object arg0, Object arg1) {
							TotalizadorRelatorios t1 = (TotalizadorRelatorios) arg0;
							TotalizadorRelatorios t2 = (TotalizadorRelatorios) arg1;

							return t1.getId().compareTo(t2.getId());
						}
        			});
        	
        	int total = 0;
        	builder.addNode("totalizadores");        	
            Iterator itTotalizadores = totalizadorEstrutura.iterator();
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
     * Gera totalizador xml VALORES.<br>
     * 
     * @param builder
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
    public void geraXMLTotalizadorValores(XmlBuilder builder) throws ECARException{
        try{
        	//int total = 0;
        	builder.addNode("totalizadorValores");

        	Iterator itTotalizadores = totalizadorValores.iterator();
            while(itTotalizadores.hasNext()){
            	FonteRecursosPPA tr = (FonteRecursosPPA) itTotalizadores.next();
            	String nome = tr.getLabel();
            	String valor = "R$ " + Util.formataMoeda(tr.getValor());
            	
            	//total += tr.getTotal();
            	builder.addClosedNode("totalizador",
            			"nome=\"" + builder.normalize(nome) + "\"" +
            			" valor=\"" + builder.normalize(valor) + "\"");
            }        	

            builder.addClosedNode("totalizador",
        			"nome=\"Total Geral dos Programas\"" +
        			" valor=\"" + builder.normalize("R$ " + Util.formataMoeda(totalGeralProgramas)) + "\"");
        	
        	builder.addClosedNode("totalizador",
        			"nome=\"Total Geral das Ações\"" +
        			" valor=\"" + builder.normalize("R$ " + Util.formataMoeda(totalGeralAcoes)) + "\"");

        	builder.closeNode("totalizadorValores");
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: TotalizadorValores");
        }            
    }
}
