package ecar.servlet.relatorio;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.Mensagem;
import comum.util.Pagina;
import comum.util.Util;
import comum.util.XmlBuilder;

import ecar.bean.AtributoEstruturaListagemItens;
import ecar.dao.AbaDao;
import ecar.dao.AcompRealFisicoDao;
import ecar.dao.AcompReferenciaDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.ConfiguracaoDao;
import ecar.dao.CorDao;
import ecar.dao.EstruturaDao;
import ecar.dao.EstruturaFuncaoDao;
import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstrtIndResulDao;
import ecar.dao.ItemEstruturaContaOrcamentoDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.ItemEstruturaPrevisaoDao;
import ecar.dao.ItemEstruturaRealizadoDao;
import ecar.dao.OrgaoDao;
import ecar.dao.TipoAcompanhamentoDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.Aba;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EfItemEstContaEfiec;
import ecar.pojo.EfItemEstPrevisaoEfiep;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.StatusRelatorioSrl;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.taglib.util.Input;
import ecar.util.Dominios;

/**
 * @author evandro
 * @author cristiano
 * @author claudismar
 *
 */
public class RelatorioAcompanhamentoImpresso extends AbstractServletReportXmlXsl {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3230673174665052076L;
	
	private String realPath;
	
	private ConfiguracaoCfg configura;
	private List listaIett = new ArrayList();		// Lista de Ietts a partir dos Aris
	private List listFunAcomp;	// Funções Acomp - Posições
	
	boolean posicoes;
	boolean funcoes;
	boolean dadosGerais;
	boolean indicadores;
	boolean financeiro;
	boolean projecao;
	boolean ultimo;
	
	private HttpServletRequest request;
	private String codAriFilhos[];
	private List listaCompletaOrdenada = new ArrayList(); 
	private List listaOrgaos = new ArrayList(); 
	private List listaAriCompleta = new ArrayList();
	private AcompReferenciaAref aref;
	
	private ItemEstruturaDao itemDao;
	private AcompReferenciaItemDao acompRefItemDao;
	private Map <ItemEstruturaIett , AcompReferenciaAref> mapArisReferencia;
	
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
        ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(null);
        this.itemDao = new ItemEstruturaDao(null);
        TipoAcompanhamentoDao tipoAcompDao = new TipoAcompanhamentoDao(null);
        AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(null); 
        this.acompRefItemDao = new AcompReferenciaItemDao(null);
    	OrgaoDao orgaoDao = new OrgaoDao(null);
        
        
        Util.liberarImagem();
        
		this.request = request;
		
		String codigosItensSelecionadosTela = Pagina.getParamStr(request, "codigosItemSelecionadosTela");
		String mesReferencia = Pagina.getParamStr(request, "mesReferencia");
		String codTipoAcompanhamento  = Pagina.getParamStr(request, "codTipoAcompanhamento");
		String codigosAri = Pagina.getParamStr(request, "codigosAri");
        String orgaoResponsavelNome = Pagina.getParamStr(request, "orgaoResponsavel");
		codAriFilhos = request.getParameterValues("codAriFilhos");
		String tela = Pagina.getParamStr(request, "tela");
		TipoAcompanhamentoTa tipoAcomp = null;
		ItemEstruturaIett iett = null;
		OrgaoOrg orgao = null;
		String nomeReferencia  = "";
		AcompReferenciaAref arefOrgao =  null;
		Iterator itListaOrgaos = null;
		String codigosItensSelecionados = "";
		List listaItensSelecionadosComDescendentes = new ArrayList();
        ultimo = false;
        this.listaOrgaos = new ArrayList();
        
		
	
		
		
		/* criar classe para geração de XML */
        XmlBuilder builder = new XmlBuilder();
        
        configura = configuracaoDao.getConfiguracao();
        
        listFunAcomp = new ArrayList();
        
    	posicoes = false;
    	funcoes = false;
    	dadosGerais = false;
    	indicadores = false;
    	financeiro = false;
    	
        
        /* atribui o endereço http */
    	String protocol = ( request.getRequestURL().indexOf("https") == 0 ? "https://" : "http://" );
        realPath = protocol + request.getLocalName() + ":" + request.getLocalPort();
        this.defineFuncoes();        

        String datahora = Data.parseDateHour(new Date()).substring(0,16); //este método retorna dd/mm/aaaa hh:mm:ss:ssss. Faço Substring para poder pegar só "dd/mm/aaaa hh:mm"

        boolean ehSeparadoPorOrgao =  false;
        if(codTipoAcompanhamento != null && !codTipoAcompanhamento.equals("")) {
        	tipoAcomp = (TipoAcompanhamentoTa) tipoAcompDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
		}
        if(tipoAcomp != null && tipoAcomp.getIndSepararOrgaoTa() != null && tipoAcomp.getIndSepararOrgaoTa().equals("S")) {
        	ehSeparadoPorOrgao =  true;
        } else {
        	//se nao for separado por orgao, o órgao vai ser vazio
        	ultimo = true;
        }
        
        if(mesReferencia != null && !mesReferencia.equals("")) {
	      	this.aref = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(mesReferencia));
	     }
        
    	String tipoDadosGerais = Pagina.getParamStr(request, "indTipoDadosGerais");
		
        if("S".equals(Pagina.getParamStr(request, "dadosGerais"))) {
			if("L".equals(tipoDadosGerais)) {
				tipoDadosGerais = "Relação"; 
			} else if("R".equals(tipoDadosGerais)) {
				tipoDadosGerais = "Resumo"; 
			} else if("C".equals(tipoDadosGerais)) {
				tipoDadosGerais = "Completo"; 
			}
        } else {
        	tipoDadosGerais = "";
        }
        
        
      //verifica qual vai ser o nome da referencia. Se for mais de uma, será consolidado.
    	if(acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(aref).size() > 1){
			nomeReferencia = ConstantesECAR.LABEL_ORGAO_CONSOLIDADO;
		} else {
			nomeReferencia = aref.getNomeAref();
		}
    
    	
    	
    	builder.addNode("relatorio",
    			"titulo=\"" + builder.normalize(Util.normalizaCaracterMarcador(this.configura.getTituloSistema())) + "\"" +
    			" datahora=\"" + builder.normalize(Util.normalizaCaracterMarcador(datahora)) + "\"" +
    			" tipoAcomp=\"" + builder.normalize(Util.normalizaCaracterMarcador(tipoAcomp.getDescricaoTa())) + "\"" +
    			" mesReferencia=\""+ builder.normalize(Util.normalizaCaracterMarcador(nomeReferencia + " (" + aref.getDiaAref() + "/" + aref.getMesAref() + "/" + aref.getAnoAref() + ")")) +"\"" +
    			" tipoDadosGerais=\"" + builder.normalize(Util.normalizaCaracterMarcador(tipoDadosGerais)) + "\"");

	    
	    
    	
    	if(!codigosAri.equals("") && 
    			//se for separado por orgao e a lista nao vier separada por orgao
    			((ehSeparadoPorOrgao && !codigosItensSelecionadosTela.contains("_org")) || 
    			//se a lista de itens nao tiver sido montada		
    			codigosItensSelecionadosTela.equals(""))){
    		codigosItensSelecionadosTela = montaListaItensOrgaosPeloAri(codigosAri, ehSeparadoPorOrgao);
    		
    	} else {
    		if(ehSeparadoPorOrgao)
    			montaListaOrgaosPelosItens(codigosItensSelecionadosTela);
    	}
    	
    	if(!listaOrgaos.isEmpty())
    		itListaOrgaos = listaOrgaos.iterator();
    	
        while((itListaOrgaos != null && itListaOrgaos.hasNext()) || ultimo) {
        	
        	if(itListaOrgaos != null && itListaOrgaos.hasNext())
        		orgao = (OrgaoOrg) itListaOrgaos.next();
        	else if(ultimo){
    			orgao = null;
    			ultimo = false;
    		}
        	
        	if(ehSeparadoPorOrgao)
        		//descobre a referencia correspondente ao orgao 
        		arefOrgao =  acompReferenciaDao.getAcompReferenciaByOrgaoDiaMesAnoAref(orgao, this.aref);
        	else 
        		arefOrgao = this.aref;
        	
        	if(arefOrgao != null) {
        		
        		if(ehSeparadoPorOrgao) {
        			//monta a lista de itens selecionados pertencentes ao orgao (sem a parte do _org)
        			 codigosItensSelecionados = montaCodigosSelecionadosTelaComSeparacaoOrgao(codigosItensSelecionadosTela, orgao);
        		} else {
        			codigosItensSelecionados = codigosItensSelecionadosTela;
        		}	
	           	//monta a lista de descendenstes 
	       		listaItensSelecionadosComDescendentes = montaListaDescendentes(codigosItensSelecionados);
	        		    
	       	    //Monta a lista de codigos ari 
	       		List listaCodigos = montaListaAris(arefOrgao, codigosItensSelecionados, orgao, ehSeparadoPorOrgao);
	        			
	        		
	       	    listaCompletaOrdenada = itemDao.getItensOrdenados(listaItensSelecionadosComDescendentes, arefOrgao.getTipoAcompanhamentoTa());
	       	    listaAriCompleta = new ArrayList();
	       	    listaIett = new ArrayList();
	        		
	       		//ARIs selecionados (EM NEGRITO)
	       	     if (listaCodigos != null && !listaCodigos.isEmpty()) {
	       	        	List[] arisIetts = this.acompRefItemDao.listarArisAndIetts(listaCodigos);
	       	        	listaAriCompleta.addAll(arisIetts[0]);
	       	        	listaIett.addAll(arisIetts[1]);
	       	        	
	       	      } else {
	       	        	String codAri = Pagina.getParamStr(request, "codAri");
	       	        	AcompReferenciaItemAri ari = null;
	       	        	if (!codAri.equals("")){
	       	        		ari = (AcompReferenciaItemAri) acompRefItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(codAri));
	       	        	}	       	        	
	       	    		if(ari != null) {
	      	    			listaAriCompleta.add(ari);
	       	    			listaIett.add(ari.getItemEstruturaIett());
	       	    		}
	        	        	
	       	      }
	
	       	      // ARIs filhos selecionados (CHECKBOX)
	       	      if(codAriFilhos != null) {
	       	      	List listaCodAriFilhos = new ArrayList();
	       	       	for (int i = 0; i < codAriFilhos.length; i++) {
	       	       		listaCodAriFilhos.add(Long.valueOf(codAriFilhos[i]));
	       	       	}
	        	        	
	       	       	List[] arisIetts = this.acompRefItemDao.listarArisAndIetts(listaCodAriFilhos);
	       	       	listaAriCompleta.addAll(arisIetts[0]);
	       	       	listaIett.addAll(arisIetts[1]);
	       	      }
	        	        
	       	        geraXMLItensGeral(builder, orgao);  
        		
       		}
       	}	
        	
    	builder.closeNode("relatorio");
    	
    	
        return builder.toStringBuffer();
    }
 

	/**
	 * Define funções.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
	 */
	private void defineFuncoes() throws ECARException 
	{
		String funcoesAcomp[] = request.getParameterValues("tipoFuncAcompTpfa");
        if(funcoesAcomp != null){
			for (int i = 0; i < funcoesAcomp.length; i++) {
				TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) this.itemDao.buscar(TipoFuncAcompTpfa.class, Long.valueOf(funcoesAcomp[i]));
				listFunAcomp.add(funcao);
			}
        }
	        
        posicoes = false;
        funcoes = false;
    	dadosGerais = false;
       	indicadores = false;
       	financeiro = false;
       	projecao = false;
       	
        if("S".equals(Pagina.getParamStr(request, "todosOsItens"))) {
	    	posicoes = true;
	    	funcoes = true;
	    	dadosGerais = true;
	       	indicadores = true;
	       	projecao = true;
	       	financeiro = true;
        }
        
        if("S".equals(Pagina.getParamStr(request, "posicoes")))
        	posicoes = true;
    
        if(!listFunAcomp.isEmpty())
        	funcoes = true;
        
        if("S".equals(Pagina.getParamStr(request, "dadosGerais")))
        	dadosGerais = true;
        
        if("S".equals(Pagina.getParamStr(request, "indicadores")))
        	indicadores = true;
        
        if("S".equals(Pagina.getParamStr(request, "financeiro")))
        	financeiro = true;
        
        if("S".equals(Pagina.getParamStr(request, "projecao")))
        	projecao = true;	        
	}
	
	/**
	 * Gera itens Geral xml.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param XmlBuilder builder
	 * @throws ECARException
	 */
    private void geraXMLItensGeral(XmlBuilder builder, OrgaoOrg orgao) throws ECARException{
    	String mesRef = "";
    	mesRef = aref.getDiaAref() + "/";
    	mesRef = mesRef + aref.getMesAref() + "/";
    	mesRef = mesRef + aref.getAnoAref();
    	
    	//seta a variável para true caso o tipo de acompanhamento selecionado esteja configurado para separar por órgãos
    	//caso contrário seta false
    	boolean ehSeparadoPorOrgao = aref.getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals("S") ? true : false;
    	String nomeSiglaOrg = "";
    	String exibirOrgao = "S";
    	
    	if(ehSeparadoPorOrgao && orgao != null){
			
			nomeSiglaOrg = orgao.getDescricaoOrg() +
			" - " + 
			orgao.getSiglaOrg();
		} 
		else if (ehSeparadoPorOrgao && orgao == null){
			nomeSiglaOrg = configura.getLabelAgrupamentoItensSemOrgao();
		}
    	
//    	builder.addNode("itensGeral");
    	
    	builder.addNode("itensGeral", "orgao=\"" + builder.normalize(nomeSiglaOrg) + "\" exibirOrgao=\"" + builder.normalize(exibirOrgao) + "\"");
    	
    	geraXMLItens(builder);

    	builder.closeNode("itensGeral");
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
        return "posicoes.xsl";
    }
    
    /**
     * Pega o erro de pagina.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param request
     * @param mensagem
     * @return String
     */
    public String getErrorPage(HttpServletRequest request, String mensagem){        
        String errorPage = "acompanhamento/relAcompanhamento/relatorios/relatorioImpresso2.jsp?codAri=" + Pagina.getParamStr(request, "codAri") + "&mesReferencia=" + Pagina.getParamStr(request, "mesReferencia") + "&msgOperacao=" + mensagem; 
        return errorPage;
    }

    /**
     * Gera itens xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @throws ECARException
     */
    private void geraXMLItens(XmlBuilder builder) throws ECARException{
    	
    	//seta a variável para true se o tipo de acompanhamento da referência estiver configurado para separar por órgãos
    	//caso contrário seta para false.
    	boolean ehSeparadoPorOrgao = aref.getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals("S") ? true : false;
    	
    	if(!listaCompletaOrdenada.isEmpty()) {
    	
        	builder.addNode("itens");
	    	
        	String nomeSiglaOrgaoImpresso = "";
        	
        	//Melhoria de performance
        	//Iterator it = listaCompletaOrdenada.iterator();
	    	//while (it.hasNext()){
        	for(Iterator it = listaCompletaOrdenada.iterator(); it.hasNext();){
        		
	    		AtributoEstruturaListagemItens ae = (AtributoEstruturaListagemItens) it.next();
	    		ItemEstruturaIett iett = ae.getItem();
	    		
	    		String nomeSiglaOrgao = "";
	        	String nomeItem = "";
	        	
	        	if(iett.getSiglaIett() != null && !"".equals(iett.getSiglaIett())){
	        		nomeItem = iett.getSiglaIett() + " - ";
	        	}
	        	
	        	//se o tipo de acompanhamento for separado por órgãos e a referência possui um órgão
//	        	if(ehSeparadoPorOrgao && aref.getOrgaoOrg() != null){
//					
//	        		nomeSiglaOrgao = aref.getOrgaoOrg().getDescricaoOrg() +
//					" - " + 
//					aref.getOrgaoOrg() .getSiglaOrg();
//	        	//se o tipo de acompanhamento não for separado por órgãos mas o item possui um órgão
//				} else if (ehSeparadoPorOrgao == false && iett.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null) {
//					
//					nomeSiglaOrgao = iett.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg() +
//					" - " + 
//					iett.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg();
//				}
//				else {
//					nomeSiglaOrgao = "Órgão não informado";
//				}
	        	
	        	nomeItem = iett.getEstruturaEtt().getNomeEtt() + ": " + nomeItem + ae.getDescricao();
	        	
//	        	builder.addNode("item", "orgao=\"" + builder.normalize(nomeSiglaOrgao) + "\"");
	        	
            	builder.addNode("item",	"nomeItem=\"" + builder.normalize(Util.normalizaCaracterMarcador(nomeItem)) + "\"");
            	geraXMLHierarquia(builder, iett);
	        	
	        	if(this.dadosGerais) {
	        		geraXMLDadosGerais(builder, iett);
	        	}

	        	// Testar se o item tem ARI
	        	if(listaIett.contains(iett)) {
	        		
	        		//Melhoria de performance
			    	//Iterator itAri = listaAriCompleta.iterator();
			    	//while (itAri.hasNext()){
	        		for(Iterator itAri = listaAriCompleta.iterator(); itAri.hasNext();){
			        	AcompReferenciaItemAri ari = (AcompReferenciaItemAri) itAri.next();

			    		if(ari.getItemEstruturaIett().equals(iett) && ari.getAcompReferenciaAref().getTipoAcompanhamentoTa().equals(aref.getTipoAcompanhamentoTa())) {
			    			geraXMLItemAri(builder, ari);
			    			break;
			    		}
	        		}
	        	}

		    	builder.closeNode("item");
	    	}
    		//Chamando Garbage Collector para ver se melhora a performance.
			//TODO: Verificar se isso ajuda em alguma coisa...
    		//System.out.println("===================== Chamando o Garbage Collector ===========================");
    		System.gc();
	    	
	    	builder.closeNode("itens");
    	}
    }

    /**
     * Gera XML do ItemAri.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param AcompReferenciaItemAri itemAri
     * @throws ECARException
     */
    private void geraXMLItemAri(XmlBuilder builder, AcompReferenciaItemAri itemAri) throws ECARException{
		itemAri = (AcompReferenciaItemAri) this.acompRefItemDao.buscar(AcompReferenciaItemAri.class, itemAri.getCodAri());

    	if(this.posicoes || this.funcoes) {
	    	
	    	if (itemAri.getAcompRelatorioArels() != null && itemAri.getAcompRelatorioArels().size() > 0){
		    	geraXMLAvaliacoes(builder, itemAri);
	    	}
    	}

    	if(this.indicadores) {
    		geraXMLIndicadores(builder, itemAri);
    	}

    	if(this.financeiro) {
    		geraXMLEvolucaoFinanceira(builder, itemAri);
    	}
    }
    
    /**
     * Gera Hierarquia xml.<br>
     * 
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param ItemEstruturaIett iett
     * @throws ECARException
     */
    private void geraXMLHierarquia(XmlBuilder builder, ItemEstruturaIett iett) throws ECARException{
    	try {
	    	List lista = this.itemDao.getAscendentes(iett);
    		lista.add(iett);
	    	
	    	if(lista == null || lista.isEmpty()) {
	    		return;
	    	}
	    	
	    	//Melhoria de performance
	    	//Iterator it = lista.iterator();
	    	
	    	builder.addNode("hierarquia");
	    	
	    	//while(it.hasNext()){
	    	for(Iterator it = lista.iterator(); it.hasNext();){
	    		ItemEstruturaIett item = (ItemEstruturaIett) it.next();
	    		
	    		String descricaoItem = "";
	    		// Obter o nome do item de acordo com a configuração
	    		
	    		//Melhoria de performance
		    	//Iterator it2 = listaCompletaOrdenada.iterator();
		    	//while (it2.hasNext()){
	    		for(Iterator it2 = listaCompletaOrdenada.iterator(); it2.hasNext();){
		    		AtributoEstruturaListagemItens ae = (AtributoEstruturaListagemItens) it2.next();
		    		if(ae.getItem().equals(item)) {
		    			descricaoItem = ae.getDescricao();
		    			break;
		    		}
		    	}
		    	
		    	String destacarItem = "N";
		    	if(iett.equals(item)) {
		    		destacarItem = "S";
		    	}
				
	    		builder.addClosedNode("itemHierarquia", "nome=\"" + builder.normalize(Util.normalizaCaracterMarcador(descricaoItem)) + "\"" +
							" nomeEstrutura=\"" + builder.normalize(Util.normalizaCaracterMarcador(item.getEstruturaEtt().getNomeEtt())) + "\"" +
							" nivel=\"" + builder.normalize(Util.normalizaCaracterMarcador(String.valueOf(item.getNivelIett().intValue()-1))) + "\"" +
							" destacarItem=\"" + builder.normalize(Util.normalizaCaracterMarcador(destacarItem)) + "\"");
	    	}
	    	builder.closeNode("hierarquia");
	    	
	    } catch(Exception e){
	    	this.logger.error(e);
	        throw new ECARException("Erro na criação do relatório: Hierarquia - " + e.getMessage());               
	    }
    }
    
    /**
     * Gera avaliações xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param AcompReferenciaItemAri itemAri
     * @throws ECARException
     */
    private void geraXMLAvaliacoes(XmlBuilder builder, AcompReferenciaItemAri itemAri) throws ECARException{
        try{
        	CorDao corDao = new CorDao(null);

        	String exigeLiberarAcompanhamento = itemAri.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndLiberarAcompTa();

        	String labelSituacaoListaParecer = new ConfiguracaoDao(request).getConfiguracao().getLabelSituacaoListaPareceres();	
        	builder.addNode("avaliacoes",
        			" labelSituacaoListaParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelSituacaoListaParecer)) + "\"");
	    	
	    	List lista = new ArrayList();
	    	if("N".equals(exigeLiberarAcompanhamento) || itemAri.getAcompRelatorioArels() != null){
	    		lista = this.acompRefItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(itemAri);
	    	}
	    
	    	String ocultarObservacoesParecer = new ConfiguracaoDao(request).getConfiguracao().getIndOcultarObservacoesParecer();
	    	
	    	//Melhoria de performance
	    	//Iterator it = lista.iterator();
	    	
	    	String descricao = "";
	    	String atribuidoPor = "";
	    	String situacao = "";
	    	String cor = "";
	    	String statusLiberadoRelatorio = "N";
	    	String complemento = "";
	    	String ultManutencao = "";
	    	
	    	StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) this.acompRefItemDao.buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
	    	TipoAcompanhamentoTa tipoAcompanhamento = itemAri.getAcompReferenciaAref().getTipoAcompanhamentoTa();
	    	SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
	    	ValidaPermissao validaPermissao = new ValidaPermissao();
	    	List listaPermissaoTpfa = validaPermissao.permissaoVisualizarPareceres(tipoAcompanhamento,seguranca.getGruposAcesso());
	    	
	    	//while (it.hasNext()){
	    	for(Iterator it = lista.iterator(); it.hasNext();){
	    		AcompRelatorioArel acompRelatorio = (AcompRelatorioArel) it.next();
	    		
	    		if(listaPermissaoTpfa.contains(acompRelatorio.getTipoFuncAcompTpfa())  
	    				&& validaPermissao.permissaoLeituraAcompanhamento(acompRelatorio.getAcompReferenciaItemAri(), seguranca.getUsuario(), seguranca.getGruposAcesso())){
	    		
		    		if(acompRelatorio.getUsuarioUsuUltimaManutencao() != null)
		    			atribuidoPor = acompRelatorio.getUsuarioUsuUltimaManutencao().getNomeUsuSent();
		    		else
		    			atribuidoPor = "N/I";
		    		
	    			if(acompRelatorio.getIndLiberadoArel() == null || "N".equals(acompRelatorio.getIndLiberadoArel())) {
		    			descricao = "";
		    			situacao = "";
		    			cor = "";
		    			statusLiberadoRelatorio = "N";
		    			complemento = "";
	    			} else if("N".equals(exigeLiberarAcompanhamento) || acompRelatorio.getAcompReferenciaItemAri().getStatusRelatorioSrl().equals(statusLiberado)) {
			    		statusLiberadoRelatorio = "S";
	
			    		if(acompRelatorio.getDescricaoArel() != null) {
			    			descricao = Util.stripHTMLModificado(Util.normalizaCaracterMarcador(acompRelatorio.getDescricaoArel()));
			    			descricao = Util.stripHTMLCommentsModificado(descricao);
			    			descricao = descricao.replace("&lt;","<");
			    			descricao = descricao.replace("&gt;",">");
			    		} else {
			    			descricao = "N/I";
			    		}
			    		
			    		if(acompRelatorio.getSituacaoSit() != null)
			    			situacao = acompRelatorio.getSituacaoSit().getDescricaoSit();
			    		else
			    			situacao = "N/I";
			    		
		    			cor = realPath + request.getContextPath() + "/images/" + corDao.getImagemSinalRelPosicoes(acompRelatorio.getCor(), acompRelatorio.getTipoFuncAcompTpfa());
			    		
		    				    			
			    		if(acompRelatorio.getComplementoArel() != null)
			    			complemento = Util.normalizaCaracterMarcador(acompRelatorio.getComplementoArel());
			    		else
			    			complemento = "N/I";
	    			
			    		
			    		if(acompRelatorio.getDataUltManutArel() != null)
			    			ultManutencao = Data.parseDate(acompRelatorio.getDataUltManutArel());
			    		else if(acompRelatorio.getDataInclusaoArel() != null)
			    			ultManutencao = Data.parseDate(acompRelatorio.getDataInclusaoArel());
			    		else
			    			ultManutencao = "N/I";
		    		}
		    		
	        		//TODO: temporário SEPL
	//        		if(atribuidoPor.equals("Elmar Haas")) {
	//        			atribuidoPor = "SEPL";
	//        		} else if(atribuidoPor.equals("Sérgio Augusto Negrão")) {
	//        			atribuidoPor = "Regina Elena Sabóia Iório";
	//        		} else if(atribuidoPor.equals("Caroline Jablonski Ruppel Santos")) {
	//        			atribuidoPor = "Rosina Coeli Alice Parchen";
	//        		}
	
	    			
	    			String labelSituacaoParecer = new ConfiguracaoDao(request).getConfiguracao().getLabelSituacaoParecer();
	    			
	        		if(this.posicoes){
	        			
	        			if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
	        			
			    		builder.addClosedNode("avaliacao", 
			    				"label=\"" + builder.normalize(Util.normalizaCaracterMarcador(acompRelatorio.getTipoFuncAcompTpfa().getLabelPosicaoTpfa())) + "\"" + 
								" responsavel=\"" + builder.normalize(Util.normalizaCaracterMarcador(atribuidoPor)) + "\"" +
								" labelSituacaoParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelSituacaoParecer)) + "\"" +
								" situacao=\"" + builder.normalize(Util.normalizaCaracterMarcador(situacao)) + "\"" + 
								" cor=\"" + builder.normalize(Util.normalizaCaracterMarcador(cor)) + "\"" +
								" descricao=\"" + builder.normalize(Util.normalizaCaracterMarcador(descricao)) + "\"" +
								" complemento=\"" + builder.normalize(Util.normalizaCaracterMarcador(complemento)) + "\"" +
								" statusLiberado=\"" + builder.normalize(Util.normalizaCaracterMarcador(statusLiberadoRelatorio)) + "\"" +
								" ultManutencao=\"" + builder.normalize(Util.normalizaCaracterMarcador(ultManutencao)) + "\"");
	        			}
	        			else{
	        				
	        				builder.addClosedNode("avaliacao", 
	    		    				"label=\"" + builder.normalize(Util.normalizaCaracterMarcador(acompRelatorio.getTipoFuncAcompTpfa().getLabelPosicaoTpfa())) + "\"" + 
	    							" responsavel=\"" + builder.normalize(Util.normalizaCaracterMarcador(atribuidoPor)) + "\"" +
	    							" labelSituacaoParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelSituacaoParecer)) + "\"" +
	    							" situacao=\"" + builder.normalize(Util.normalizaCaracterMarcador(situacao)) + "\"" + 
	    							" cor=\"" + builder.normalize(Util.normalizaCaracterMarcador(cor)) + "\"" +
	    							" descricao=\"" + builder.normalize(Util.normalizaCaracterMarcador(descricao)) + "\"" +   
	    							" complemento=\"ocultar\"" +
	    							" statusLiberado=\"" + builder.normalize(Util.normalizaCaracterMarcador(statusLiberadoRelatorio)) + "\"" +
	    							" ultManutencao=\"" + builder.normalize(Util.normalizaCaracterMarcador(ultManutencao)) + "\"");
	        			}
		    		}
		    		else if (funcoes){
		    			if(listFunAcomp.contains(acompRelatorio.getTipoFuncAcompTpfa())){
		    				
		    				if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
		    				
				    		builder.addClosedNode("avaliacao", 
				    				"label=\"" + builder.normalize(Util.normalizaCaracterMarcador(acompRelatorio.getTipoFuncAcompTpfa().getLabelPosicaoTpfa())) + "\"" + 
									" responsavel=\"" + builder.normalize(Util.normalizaCaracterMarcador(atribuidoPor)) + "\"" +
									" labelSituacaoParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelSituacaoParecer)) + "\"" +
									" situacao=\"" + builder.normalize(Util.normalizaCaracterMarcador(situacao)) + "\"" + 
									" cor=\"" + builder.normalize(Util.normalizaCaracterMarcador(cor)) + "\"" +
									" descricao=\"" + builder.normalize(Util.normalizaCaracterMarcador(descricao)) + "\"" +
									" complemento=\"" + builder.normalize(Util.normalizaCaracterMarcador(complemento)) + "\"" +
									" statusLiberado=\"" + builder.normalize(Util.normalizaCaracterMarcador(statusLiberadoRelatorio)) + "\"" +
									" ultManutencao=\"" + builder.normalize(Util.normalizaCaracterMarcador(ultManutencao)) + "\"");
		    				}
		    				else{
		    					
		    					builder.addClosedNode("avaliacao", 
		    		    				"label=\"" + builder.normalize(Util.normalizaCaracterMarcador(acompRelatorio.getTipoFuncAcompTpfa().getLabelPosicaoTpfa())) + "\"" + 
		    							" responsavel=\"" + builder.normalize(Util.normalizaCaracterMarcador(atribuidoPor)) + "\"" +
		    							" labelSituacaoParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelSituacaoParecer)) + "\"" +
		    							" situacao=\"" + builder.normalize(Util.normalizaCaracterMarcador(situacao)) + "\"" + 
		    							" cor=\"" + builder.normalize(Util.normalizaCaracterMarcador(cor)) + "\"" +
		    							" descricao=\"" + builder.normalize(Util.normalizaCaracterMarcador(descricao)) + "\"" +   
		    							" complemento=\"\"" +
		    							" statusLiberado=\"" + builder.normalize(Util.normalizaCaracterMarcador(statusLiberadoRelatorio)) + "\"" +
		    							" ultManutencao=\"" + builder.normalize(Util.normalizaCaracterMarcador(ultManutencao)) + "\"");
		    				}
		    			}
		    		}
		    	}
	    	
	    	}
	    	
	    	builder.closeNode("avaliacoes");
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: Avaliações - " + e.getMessage());               
        }
    }
    
    /**
     * Gera dados gerais xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param ItemEstruturaIett iett
     * @throws ECARException
     */
    private void geraXMLDadosGerais(XmlBuilder builder, ItemEstruturaIett iett) throws ECARException{
    	String separador = new ConfiguracaoDao(request).getConfiguracao().getSeparadorCampoMultivalor();
    	
        try{
        	//builder.addNode("dadosGerais");
        	
        	String labelDadosGerais = "";
        	
        	AbaDao abaDao = new AbaDao(request);
            Aba aba = new Aba();
            aba.setNomeAba("DADOS_GERAIS");
            aba = (Aba)((new AbaDao(request)).pesquisar(aba, new String[]{"codAba", "asc"}).get(0));
            
            if(iett != null){
            	labelDadosGerais = abaDao.getLabelAbaEstrutura(aba, iett.getEstruturaEtt());
            }
            else{
            	labelDadosGerais = "Dados Gerais";
            }
        	
        	builder.addNode("dadosGerais",
        			" labelDadosGerais=\"" + builder.normalize(labelDadosGerais) + "\"");
            
        	EstruturaDao estruturaDao = new EstruturaDao(null);
            List atributos = estruturaDao.getAtributosEstruturaRelatorio(iett.getEstruturaEtt(), Pagina.getParamStr(request, "indTipoDadosGerais"));
            
            //Melhoria de performance
            //Iterator itAtributos = atributos.iterator();
            //while (itAtributos.hasNext()){
            for(Iterator itAtributos = atributos.iterator(); itAtributos.hasNext();){
            	ObjetoEstrutura atributo = (ObjetoEstrutura) itAtributos.next();
            	
            	String label = atributo.iGetLabel();
            	String conteudo = atributo.iGetValor(iett);
            	           	
            	/*******Codigo responsavel pelos atributos livres no relatório******/
            	
            	if( atributo.iGetGrupoAtributosLivres() != null 
			    		&& (atributo.iGetGrupoAtributosLivres().getIndAtivoSga()!=null 
			    	    &&  atributo.iGetGrupoAtributosLivres().getIndAtivoSga().equals("S"))) {
						
					if(iett.getItemEstruturaSisAtributoIettSatbs() != null) {
		    	    	String separadorParaCamposMulti = "";
		    	    	String separadorParaCamposCheck = "";
						//Iterador com os atributos livres do ítem em questao
		    	    	Iterator<ItemEstruturaSisAtributoIettSatb> itAtribLivres = iett.getItemEstruturaSisAtributoIettSatbs().iterator();
		    	    	
		    	    	//Para cada atributo livre do ítem, verifica-se se este corresponde 
		    	    	//ao atributo que esta sendo tratado
		    	    	while(itAtribLivres.hasNext()){
		    	    		
		    	    		ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtrib = (ItemEstruturaSisAtributoIettSatb) itAtribLivres.next();
		    	    		//Iterador com os atributos livres associados ao atributo em questão
		    	    		Iterator<SisAtributoSatb> sisAtributosSatbIt = atributo.iGetGrupoAtributosLivres().getSisAtributoSatbs().iterator();
		    	    		
		    	    		while (sisAtributosSatbIt.hasNext()) {
		    	    			SisAtributoSatb sisAtributo = (SisAtributoSatb) sisAtributosSatbIt.next();
			    	    		if( (sisAtributo).equals(itemEstruturaSisAtrib.getSisAtributoSatb()) ) {
			    	    			//Caso seja um atributo tipo texto
			    	    			if (sisAtributo.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().intValue() == Input.IMAGEM){
		    	    					if (itemEstruturaSisAtrib.getInformacao().indexOf("/") != -1){
		    	    						String conteudoImagem = itemEstruturaSisAtrib.getInformacao().substring(itemEstruturaSisAtrib.getInformacao().lastIndexOf("/")+1);
		    	    						if (conteudoImagem.indexOf("--") != -1){
		    	    							conteudoImagem = conteudoImagem.substring(conteudoImagem.lastIndexOf("--")+3);
		    	    						}
		    	    						conteudo += separadorParaCamposMulti + conteudoImagem;
		    	    					}
		    	    				} else if(itemEstruturaSisAtrib.getInformacao() != null) {
			    	    				conteudo += separadorParaCamposMulti + itemEstruturaSisAtrib.getInformacao().replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");
			    	    				separadorParaCamposMulti = separador; 
			    	    			//Caso seja atributo tipo check, radio ou select
			    	    			} else if(itemEstruturaSisAtrib.getSisAtributoSatb().getDescricaoSatb() != null) {
			    	    				conteudo += separadorParaCamposCheck + (itemEstruturaSisAtrib.getSisAtributoSatb().getDescricaoSatb()).replaceAll("\n", " ").replaceAll("\r"," ").replaceAll("\t", " ");;
			    	    				separadorParaCamposCheck = separador;
			    	    			//Caso não tenha valor associado ao atributo
			    	    			} else {
			    	    				conteudo += "";
			    	    			}
			    	    		}
		    	    		}
		    	    	}
		    	    	separadorParaCamposMulti = "";
		    	    	separadorParaCamposCheck = "";
		        	}
						
				}
//            	
//            	
//              	if(iett.getItemEstruturaSisAtributoIettSatbs() != null){
//              		List<ItemEstruturaSisAtributoIettSatb> listaSisAtributosInformados = iett.obterAtirbutosLivresInformados(atributo.iGetGrupoAtributosLivres());
//            		int tam = listaSisAtributosInformados.size();
//            		int pos = 0;
//        	    	Iterator itAtribLivres = iett.getItemEstruturaSisAtributoIettSatbs().iterator();
//        	    	while(itAtribLivres.hasNext()){
//        	    		ItemEstruturaSisAtributoIettSatb atr = (ItemEstruturaSisAtributoIettSatb) itAtribLivres.next();
//        	    		AtributoLivre atributoLivre = new AtributoLivre();
//        	    		atributoLivre.setSisAtributoSatb(atr.getSisAtributoSatb());
//        	    		atributoLivre.setInformacao(atr.getInformacao());
//        	    		
//        	    		if(!atributoLivre.getSisAtributoSatb().getSisGrupoAtributoSga().equals(atributo.iGetGrupoAtributosLivres()))
//        	    			continue;
//        	    		
//        	    		if((atributoLivre.getSisAtributoSatb()
//        	    				.getSisGrupoAtributoSga()
//        	    				.getSisTipoExibicGrupoSteg()
//        	    				.getCodSteg().intValue() != TEXT) && 
//        	    			(atributoLivre.getSisAtributoSatb()
//                	    			.getSisGrupoAtributoSga()
//                	    			.getSisTipoExibicGrupoSteg()
//                	    			.getCodSteg().intValue() != TEXTAREA) && 
//                	    			(atributoLivre.getSisAtributoSatb()
//                        	    			.getSisGrupoAtributoSga()
//                        	    			.getSisTipoExibicGrupoSteg()
//                        	    			.getCodSteg().intValue() != MULTITEXTO) && 
//                        	    			(atributoLivre.getSisAtributoSatb()
//                                	    			.getSisGrupoAtributoSga()
//                                	    			.getSisTipoExibicGrupoSteg()
//                                	    			.getCodSteg().intValue() != VALIDACAO) && 
//                                	    			(atributoLivre.getSisAtributoSatb()
//                                        	    			.getSisGrupoAtributoSga()
//                                        	    			.getSisTipoExibicGrupoSteg()
//                                        	    			.getCodSteg().intValue() != IMAGEM) ) {
//            	    		
//            	    		if(atributoLivre.getSisAtributoSatb() != null) {
//            	    			conteudo += atributoLivre.getSisAtributoSatb().getDescricaoSatb();
//            	    			pos++;
//            	    		} else if (){
//            	    			conteudo += atributoLivre.getInformacao();
//            	    		}
//            	    		
//            	    		if (pos < tam){
//            	    			conteudo += separador;
//            	    		}
//        	    		} else {
//        	    			conteudo = atributoLivre.getInformacao();
//        	    		}
//        	    	}
//            	}
              	
              	/*******Codigo responsavel pelos atributos livres no relatório******/           	

            	
            	if(label != null && !"".equals(label) && conteudo != null && !"".equals(conteudo)){
            		
            		//TODO: temporário SEPL
//            		if(conteudo.equals("Elmar Haas")) {
//            			conteudo = "SEPL";
//            		} else if(conteudo.equals("Sérgio Augusto Negrão")) {
//            			conteudo = "Regina Elena Sabóia Iório";
//            		} else if(conteudo.equals("Caroline Jablonski Ruppel Santos")) {
//            			conteudo = "Rosina Coeli Alice Parchen";
//            		}
            		
            		builder.addClosedNode("atributosDadosGerais",
                			"label=\"" + builder.normalize(Util.normalizaCaracterMarcador(label)) + "\"" + 
                			" conteudo=\"" + builder.normalize(Util.normalizaCaracterMarcador(conteudo)) + "\"");
            	}
            }
    		
            builder.closeNode("dadosGerais");
            geraXMLDadosAbasItem(builder, iett);
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: Dados Gerais Itens Sem ARI - " + e.getMessage());               
        }
    }
    
    /**
     * Gera XML dos Dados das Abas de itens.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param ItemEstruturaIett iett
     * @throws ECARException
     */
    private void geraXMLDadosAbasItem(XmlBuilder builder, ItemEstruturaIett iett) throws ECARException{
        try{
            RelatorioItemEstrutura relatorioItemEstrutura = new RelatorioItemEstrutura();
            
            //"indTipoDadosGerais"
        	// "C" = COMPLETO
            // "R" = RESUMO
            // "L" = RELAÇÃO

            relatorioItemEstrutura.geraXMLFuncoes(builder, iett, Pagina.getParamStr(request, "indTipoDadosGerais"), false);
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: Dados Gerais - " + e.getMessage());               
        }
    }
    
    /**
     * Gera indicadores xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param AcompReferenciaItemAri itemAri
     * @throws ECARException
     */
    private void geraXMLIndicadores(XmlBuilder builder, AcompReferenciaItemAri itemAri) throws ECARException{
    	try {
    		AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);
    		ExercicioDao exercicioDao = new ExercicioDao(null);
    		ItemEstrtIndResulDao itemEstrtIndResulDao = new ItemEstrtIndResulDao(null);
    		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(null);

    		String mostrarProjecao = (projecao) ? "S" : "N";
			//List<ExercicioExe> exercicios = new ExercicioDao(null).getExerciciosProjecao(itemAri.getItemEstruturaIett().getCodIett());
			//Ref. mantis 7770: exibir os últimos quatro anos...
			//Pegando exercicios do mais novo para mais antigo para pegar os ultimos anos primeiro...
			//List<ExercicioExe> todosExercicios = exercicioDao.listar(ExercicioExe.class, new String[] {"dataInicialExe","desc"});
    		
			//Mantis #11071: Ajustes no Relatório do PPA (metas físicas de exercícios)
			List<ExercicioExe> todosExercicios = exercicioDao.getExeByPerExe(itemAri.getAcompReferenciaAref().getExercicioExe());
    		
    		
			if(todosExercicios == null || todosExercicios.isEmpty()) {
				throw new Exception("Não existem exercícios cadastrado");
			}
			List<ExercicioExe> exercicios = new ArrayList<ExercicioExe>();
			//Obtendo ultimos 4 anos...
			int qtdeAnos = 4;
			if(todosExercicios.size() < qtdeAnos) {
				qtdeAnos = todosExercicios.size(); 
			}
			exercicios.addAll(todosExercicios.subList(0, qtdeAnos));
			//Re-ordenando do mais antigo para o o mais novo... 
			Collections.reverse(exercicios);
			
			List indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(itemAri, Dominios.TODOS, false);
			if(indResultados != null && indResultados.size() > 0){

				String exibirSituacaoColuna = "N";					
				if("N".equals(mostrarProjecao)) {
					exibirSituacaoColuna = "S";					
				}
				builder.addNode("indicadores", "labelFuncao=\"" 
						+ builder.normalize(Util.normalizaCaracterMarcador(estruturaFuncaoDao.getLabelIndicadoresResultado(itemAri.getItemEstruturaIett().getEstruturaEtt()))) 
						+ "\" mostrarProjecao=\"" + builder.normalize(Util.normalizaCaracterMarcador(mostrarProjecao)) + "\""
						+ " exibirSituacaoColuna=\"" + builder.normalize(Util.normalizaCaracterMarcador(exibirSituacaoColuna)) + "\"");
				
				int numeroExercicios = 0;
				for(ExercicioExe exercicio : exercicios){
					builder.addClosedNode("columnExercicio", "ano=\"" + builder.normalize(Util.normalizaCaracterMarcador(exercicio.getDescricaoExe())) + "\"");
					builder.addClosedNode("indExercicio", "exercicio=\"" + builder.normalize(Util.normalizaCaracterMarcador(exercicio.getDescricaoExe())) + "\"");
					numeroExercicios++;
				}

				String grupoIndicador = "Indicador";
				String exibirGrupoIndicador = "N";
				
				//Melhoria de performance
				//Iterator itIndResult = indResultados.iterator();
				//while(itIndResult.hasNext()){
				for(Iterator itIndResult = indResultados.iterator(); itIndResult.hasNext();){
					AcompRealFisicoArf indicador = (AcompRealFisicoArf) itIndResult.next();
					String tipoQtde = indicador.getItemEstrtIndResulIettr().getIndTipoQtde();
					String situacao = "";
					if(indicador.getSituacaoSit()!=null) {
						situacao = indicador.getSituacaoSit().getDescricaoSit();
					}
					
					//TODO: temporário para SEPL:
//					if("/ecar".equals(request.getContextPath())) {
//						if("".equals(situacao)) {
//							situacao = "Em andamento";
//						}
//					}

					exibirGrupoIndicador = "N";

					if(configura.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas() != null){
						if(indicador.getItemEstrtIndResulIettr().getSisAtributoSatb() != null && !grupoIndicador.equals(indicador.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb())){
							grupoIndicador = indicador.getItemEstrtIndResulIettr().getSisAtributoSatb().getDescricaoSatb();
							exibirGrupoIndicador = "S";
						}
						else if(indicador.getItemEstrtIndResulIettr().getSisAtributoSatb() == null && !"".equals(grupoIndicador)) {
							exibirGrupoIndicador = "S";
						}
					}
					
					double totalRealizado = 0;
					double totalPrevisto = 0;
					String labelSituacaoParecer = new ConfiguracaoDao(request).getConfiguracao().getLabelSituacaoParecer();
					
					builder.addNode("indicadorAcomp", 
                         	"nome=\"" + builder.normalize(Util.normalizaCaracterMarcador(indicador.getItemEstrtIndResulIettr().getNomeIettir())) + "\"" + 
                         	" labelSituacaoParecer=\"" + builder.normalize(Util.normalizaCaracterMarcador(labelSituacaoParecer)) + "\"" +
                         	" situacao=\"" + builder.normalize(Util.normalizaCaracterMarcador(situacao)) + "\"" +
                         	" realizadoNoMes=\"" + builder.normalize(Pagina.trocaNullMoeda(indicador.getQtdRealizadaArf()) + " " + indicador.getItemEstrtIndResulIettr().getUnidMedidaIettr()) + "\"" +
                         	" numeroExercicios=\"" + builder.normalize(String.valueOf(numeroExercicios)) + "\"" +
                         	" mostrarProjecao=\"" + builder.normalize(Util.normalizaCaracterMarcador(mostrarProjecao)) + "\"" +
                         	" grupoIndicador=\"" + builder.normalize(Util.normalizaCaracterMarcador(grupoIndicador)) + "\"" +
                         	" exibirGrupoIndicador=\"" + builder.normalize(Util.normalizaCaracterMarcador(exibirGrupoIndicador)) + "\"" +
                         	" numeroExe=\"" + builder.normalize(Util.normalizaCaracterMarcador(String.valueOf(numeroExercicios))) + "\""
                         	);
					
					if(indicador.getItemEstrtIndResulIettr().getSisAtributoSatb() == null && !"".equals(grupoIndicador)) {
						grupoIndicador = "";
					}

					List valoresR = new ArrayList();
					List valoresP = new ArrayList();

					for(ExercicioExe exercicio : exercicios){
						double realizadoNoExercicio = 0;
						//Comentado por aleixo em 18/04/2007
//						if(exercicio.equals(itemAri.getAcompReferenciaAref().getExercicioExe())){
//							// Se o exercício em questão é mesmo exercicio do periodo de referência e o indicador for acumulável
//							// soma todas as quantidades até o mes/ano do periodo
//							if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
//								AcompReferenciaAref aref = itemAri.getAcompReferenciaAref();
//								int mesRef = Integer.valueOf(aref.getMesAref()).intValue();
//								int anoRef = Integer.valueOf(aref.getAnoAref()).intValue();
//								realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicio, indicador.getItemEstrtIndResulIettr(), mesRef, anoRef);																					
//							} else {
//								//se não for acumulável o realizado no exercicio é o realizado no periodo
//								/*if (indicador.getQtdRealizadaArf() != null)
//									realizadoNoExercicio = indicador.getQtdRealizadaArf().doubleValue();
//								*/
//								realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exercicio, indicador.getItemEstrtIndResulIettr(), itemAri.getAcompReferenciaAref());
//							}
//						} else {
//							if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
//								realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicio, indicador.getItemEstrtIndResulIettr(), itemAri.getAcompReferenciaAref());										
//							} else {
//								realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exercicio, indicador.getItemEstrtIndResulIettr(), itemAri.getAcompReferenciaAref());																						
//							}
//						} 

						if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
							realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicio, indicador.getItemEstrtIndResulIettr(), aref);										
						} else {
							realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exercicio, indicador.getItemEstrtIndResulIettr(), aref);																						
						}

						double previstoNoExercicio = itemEstrtIndResulDao.getQtdPrevistoExercicio(indicador.getItemEstrtIndResulIettr(), exercicio);
						if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
							totalRealizado += realizadoNoExercicio;
							totalPrevisto += previstoNoExercicio;
						}else{
							totalRealizado = realizadoNoExercicio;
							totalPrevisto = previstoNoExercicio;											
						}

						valoresR.add(realizadoNoExercicio);
						valoresP.add(previstoNoExercicio);

						String strPrevistoExe = "";
						String strRealizadoExe = "";
						if ("Q".equalsIgnoreCase(tipoQtde)){ //Quantidade --> sem casas decimais
							strPrevistoExe = Pagina.trocaNullNumeroSemDecimal(previstoNoExercicio);
							strRealizadoExe = Pagina.trocaNullNumeroSemDecimal(realizadoNoExercicio);
						}
						else {
							strPrevistoExe = Pagina.trocaNullMoeda(previstoNoExercicio);
							strRealizadoExe = Pagina.trocaNullMoeda(realizadoNoExercicio);
						}

						
						builder.addClosedNode("valorExercicio", 
							"exercicio=\"" + builder.normalize(Util.normalizaCaracterMarcador(exercicio.getDescricaoExe())) + "\"" + 
//							" valorPrevisto=\"" + builder.normalize(Pagina.trocaNullMoeda(previstoNoExercicio)) + "\"" +
//							" valorRealizado=\"" + builder.normalize(Pagina.trocaNullMoeda(realizadoNoExercicio)) + "\"");
							" valorPrevisto=\"" + builder.normalize(strPrevistoExe) + "\"" +
							" valorRealizado=\"" + builder.normalize(strRealizadoExe) + "\"");								
					}

					Collections.reverse(valoresR);
					Collections.reverse(valoresP);
					
					totalPrevisto = acompRealFisicoDao.getSomaValoresArfs(indicador.getItemEstrtIndResulIettr(), valoresP).doubleValue();
					totalRealizado = acompRealFisicoDao.getSomaValoresArfs(indicador.getItemEstrtIndResulIettr(), valoresR).doubleValue();

					double realizadoPrevisto = 0;
					if(totalPrevisto > 0) {
						realizadoPrevisto = ((totalRealizado/totalPrevisto) * 100);
					}

					String strTotalPrevisto = "";
					String strTotalRealizado = "";
					if ("Q".equalsIgnoreCase(tipoQtde)){ //Quantidade --> sem casas decimais
						strTotalPrevisto = Pagina.trocaNullNumeroSemDecimal(totalPrevisto);
						strTotalRealizado = Pagina.trocaNullNumeroSemDecimal(totalRealizado);
					}
					else {
						strTotalPrevisto = Pagina.trocaNullMoeda(totalPrevisto);
						strTotalRealizado = Pagina.trocaNullMoeda(totalRealizado);
					}

					
					builder.addClosedNode("valorTotal", 
                         	" percentualRealizadoPrevisto=\"" + builder.normalize(Pagina.trocaNullMoeda(new Double((realizadoPrevisto)))) + "\"" +
//                         	" totalPrevisto=\"" + builder.normalize(Pagina.trocaNullMoeda(new Double(totalPrevisto))) + "\"" +
//                         	" totalRealizado=\"" + builder.normalize(Pagina.trocaNullMoeda(new Double(totalRealizado))) + "\""
                         	" totalPrevisto=\"" + builder.normalize(strTotalPrevisto) + "\"" +
                         	" totalRealizado=\"" + builder.normalize(strTotalRealizado) + "\""
                         	);
					

					String strProjecao = "";
					String dataProjecao = "-";

					if("S".equals(indicador.getItemEstrtIndResulIettr().getIndProjecaoIettr()) && totalRealizado > 0 && totalPrevisto > 0){
						//double resultado = this.acompRefItemDao.calculoProjecao(indicador.getItemEstrtIndResulIettr(), itemAri);
						double resultado = this.acompRefItemDao.calculoProjecao(indicador.getItemEstrtIndResulIettr(),itemAri);
						ExercicioExe exercicioPrevisto = itemEstrtIndResulDao.getMaiorExercicioIndicador(indicador.getItemEstrtIndResulIettr());
						//double qtdePrevista = itemEstrtIndResulDao.getQtdPrevistoExercicio(indicador.getItemEstrtIndResulIettr(), exercicioPrevisto);
						
						Mensagem msg = new Mensagem(this.request.getSession().getServletContext());

						if(resultado == totalPrevisto)
							strProjecao = msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingida");
						if(resultado > totalPrevisto)
							strProjecao = msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingidaAntes");												
						if(resultado < totalPrevisto){
							strProjecao = msg.getMensagem("acompRelatorio.indicadorResultado.projecao.naoSeraAtingida");																									
						}
						
						//Double porcentagem = new Double((resultado/totalPrevisto) * 100);
						
						GregorianCalendar dataTermino = acompRefItemDao.getProjecaoDataTermino(indicador.getItemEstrtIndResulIettr(),itemAri,totalPrevisto);
						
						//strPorcentual = Pagina.trocaNullNumeroDecimalSemMilhar(porcentagem);
																		
						if (totalPrevisto > 0){
							DateFormat month = new SimpleDateFormat("MMMMM");
							String sMonth = month.format(dataTermino.getTime());
							dataProjecao = sMonth + "/" + dataTermino.get(GregorianCalendar.YEAR);
						
						}
						
					} else {
						if(totalRealizado == 0){
							strProjecao = "Não é possível realizar projeção sem informação de quantidades realizadas.";										
						} else {
							strProjecao = "N/A";
						}
					}
					
					builder.addClosedNode("valorProjecao",
                         	" projecao=\"" + builder.normalize(Util.normalizaCaracterMarcador(strProjecao)) + "\"" +
                         	" mostrarProjecao=\"" + builder.normalize(Util.normalizaCaracterMarcador(mostrarProjecao)) + "\"" +
                         	" dataTermino=\"" + builder.normalize(Util.normalizaCaracterMarcador(dataProjecao)) + "\""
							);
					
					
					builder.closeNode("indicadorAcomp");

				}
				builder.closeNode("indicadores");
			}
    	} catch(Exception e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do relatório: Indicadores - " + e.getMessage());               
    	}
    }
    
    /**
     * Monta a lista de aris a partir dos itens para o caso da listagem de acompanhamento.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param aref
     * @param strCodigosItem
     * @param orgaoResponsavelStr 
     * @return
     * @throws ECARException
     */
    public List montaListaAris(AcompReferenciaAref aref, String strCodigosItem, OrgaoOrg orgaoResponsavel, boolean ehSeparadoPorOrgao) throws ECARException{
    
    	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao (request);
    	List listaAriSelecionados = new ArrayList();
    	List arisByIetts = new ArrayList();
    	
    	try {
    	
	    	
			if(ehSeparadoPorOrgao) {
				//busca a lista de aris pelos codigos iett e orgaoResponsavel
				arisByIetts = acompReferenciaItemDao.getAcompReferenciaItemFilhosByIettComSeparacaoOrgao(strCodigosItem, aref, orgaoResponsavel);
			} else {
				arisByIetts = acompReferenciaItemDao.getAcompReferenciaItemFilhosByIett(strCodigosItem, aref, orgaoResponsavel);
			}	
			
			if(!arisByIetts.isEmpty()) {
				Iterator itArvore = arisByIetts.iterator();
				List lista = new ArrayList();
				
				while(itArvore.hasNext()){
					AcompReferenciaItemAri ari = (AcompReferenciaItemAri) itArvore.next();
					listaAriSelecionados.add(ari.getCodAri());
				}
		       
			} 
			
			return listaAriSelecionados;
		
    	} catch(Exception e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do relatório: Lista de Acompanhamento Selecionado - " + e.getMessage());               
    	}
    
    }
    
    
    /**
     * Gera evolução financeira xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param AcompReferenciaItemAri itemAri
     * @throws ECARException
     */
    private void geraXMLEvolucaoFinanceira(XmlBuilder builder, AcompReferenciaItemAri itemAri) throws ECARException{
    	try{
    		ItemEstruturaPrevisaoDao itemEstPrevDao = new ItemEstruturaPrevisaoDao(null);
    		ItemEstruturaRealizadoDao itemEstRealizadoDao = new ItemEstruturaRealizadoDao(null);
    		ItemEstruturaContaOrcamentoDao itemEstContaOrcDao = new ItemEstruturaContaOrcamentoDao(null);

    		
    		List listaExercicios = itemEstPrevDao.getListaExerciciosItemEstruturaPrevisao(itemAri.getItemEstruturaIett());
    		
    		//List lista = itemEstPrevDao.getListaItemEstruturaPrevisao(itemAri.getItemEstruturaIett(), itemAri.getAcompReferenciaAref().getExercicioExe());
    		List lista = itemEstPrevDao.getListaItemEstruturaPrevisao(itemAri.getItemEstruturaIett(), null);
    		
    		//Melhoria de performance
    		//Iterator it = lista.iterator();
	    	
	    	EfItemEstPrevisaoEfiep itemEstPrev = new EfItemEstPrevisaoEfiep();
	    	
	    	//if (it.hasNext()){
	    	if (lista != null && !lista.isEmpty()){

	    		int colunas = 0;

	    		int numeroColunasExibidas = 0; 

	    		String descFinanceiro[] = new String[3];
	    		boolean mostrarDescFinanceiro[] = new boolean[3];
	    		descFinanceiro[0] = configura.getRecursoDescValor1Cfg();
	    		descFinanceiro[1] = configura.getRecursoDescValor2Cfg();
	    		descFinanceiro[2] = configura.getRecursoDescValor3Cfg();
	    		
	    		
	    		boolean mostrarValores[] = new boolean[6];

	    		String descricoes[] = new String[6];
	    		descricoes[0] = configura.getFinanceiroDescValor1Cfg();
	    		descricoes[1] = configura.getFinanceiroDescValor2Cfg();
	    		descricoes[2] = configura.getFinanceiroDescValor3Cfg();
	    		descricoes[3] = configura.getFinanceiroDescValor4Cfg();
	    		descricoes[4] = configura.getFinanceiroDescValor5Cfg();
	    		descricoes[5] = configura.getFinanceiroDescValor6Cfg();
	    		
				for(int i = 0; i < 6; i++){
	    			mostrarValores[i] = itemEstRealizadoDao.getVerificarMostrarValorByPosicaoCfg(i);
	    			if(mostrarValores[i]){
	    				colunas++;
	    			}
	    		}	    
				
	    		builder.addNode("evolucaoFinanceira", "colunasRealizadas=\"" + builder.normalize(String.valueOf(colunas)) + "\"");
	    		
	    		
	    		builder.addNode("colunas");
	    		
				builder.addClosedNode("coluna", "nome=\"Categoria Econômica\"");
				builder.addClosedNode("coluna", "nome=\"Fonte\"");
				//builder.addClosedNode("coluna", "nome=\"Aprovado\"");
				//builder.addClosedNode("coluna", "nome=\"Revisado\"");
	    		//int numeroColunasExibidas = 2; //Aprovado - Revisado 
				
	    		for(int i = 0; i < 3; i++){
	    			mostrarDescFinanceiro[i] = itemEstRealizadoDao.getVerificarMostrarRecursoByPosicaoCfg(i);
	    			if(mostrarDescFinanceiro[i]){
	    				builder.addClosedNode("coluna", "nome=\"" + builder.normalize(Util.normalizaCaracterMarcador(descFinanceiro[i])) + "\"");
	    				numeroColunasExibidas++;
	    			}
	    		}
				
				for(int i = 0; i < 6; i++){
	    			mostrarValores[i] = itemEstRealizadoDao.getVerificarMostrarValorByPosicaoCfg(i);
	    			if(mostrarValores[i]){
	    				builder.addClosedNode("coluna", "nome=\"" + builder.normalize(Util.normalizaCaracterMarcador(descricoes[i])) + "\"");
	    				numeroColunasExibidas++;
	    			}
	    		}	    		

				builder.closeNode("colunas");

				/*
				 * 13.62cm / numeroColunasExibidas = tamanho de cada coluna dinâmica
				 * Quanto mais colunas, menos espaço no relatório.
				 */
				double t = 13.62;
				String tam = String.valueOf(t/numeroColunasExibidas) + "cm";
				
				builder.addNode("colunasHeader");
				for(int x = 1; x <= numeroColunasExibidas; x++){
					builder.addClosedNode("colunaHeader", "tamanho=\"" + builder.normalize(Util.normalizaCaracterMarcador(tam)) + "\"");
				}
				builder.closeNode("colunasHeader");
	    		
				double totFonAprovado = 0, totFonRevisado = 0;
				double totGerAprovado = 0, totGerRevisado = 0;
				double[] totFonValor = new double[6];
				double[] totGerValor = new double[6];
				
				/*Inicializar os valores...*/
				for(int i = 0; i < 6; i++){
					totFonValor[i] = 0;
					totGerValor[i] = 0;
				}
				
				//Melhoria de performance
				//Iterator itExe = listaExercicios.iterator();
				//while(itExe.hasNext()){
				for(Iterator itExe = listaExercicios.iterator(); itExe.hasNext();){
					ExercicioExe exercicio = (ExercicioExe) itExe.next();
					String exercicioDesc = exercicio.getDescricaoExe();
					String exercicioDescTotal = exercicioDesc;
					double totExeAprovado = 0, totExeRevisado = 0; 
					double[] totExeValor = new double[6];
					
					/*Inicializar os valores...*/
					for(int i = 0; i < 6; i++){
						totExeValor[i] = 0;
					}
					
					
					lista = itemEstPrevDao.getListaItemEstruturaPrevisao(itemAri.getItemEstruturaIett(), exercicio);
					
					//it = lista.iterator();
					long codFonte = -1;
		    		//while(it.hasNext()){
					for(Iterator it = lista.iterator(); it.hasNext();){
		    			itemEstPrev = (EfItemEstPrevisaoEfiep) it.next();
		    			
		    			/* ler EfItemEstContaEfiec */
		    			EfItemEstContaEfiec itemEstConta = 
		    					itemEstContaOrcDao.getItemEstruturaConta(
		    						itemAri.getItemEstruturaIett(),
		    						exercicio,
		    						itemEstPrev.getFonteRecursoFonr(),
		    						itemEstPrev.getRecursoRec());
		    			
		    			/* verificar valores em Efier */
		    			Double[] valores = itemEstRealizadoDao.getSomaItemEstruturaRealizado(
		    					itemEstConta,
		    					exercicio);
		    			
		    			/* Ao trocar a fonte e ao iniciar imprimir linha de fonte */
		    			if(codFonte != itemEstPrev.getFonteRecursoFonr().getCodFonr().longValue()){
		    				/* se não é a primeira passada da fonte */
		    				if(codFonte != -1){
	
		    					builder.closeNode("fonte");
			    				
		    					/* zerar os valores do total da fonte */
		    					totFonAprovado = 0;
		    					totFonRevisado = 0;
	
		    					for(int i = 0; i < 6; i++){
		    						totFonValor[i] = 0;
		    					}
		    				}
		    				
		    				codFonte = itemEstPrev.getFonteRecursoFonr().getCodFonr().longValue();
		    				//IMPRIMIR FONTE
		    				builder.addNode("fonte", "nome=\"" + builder.normalize(Util.normalizaCaracterMarcador(itemEstPrev.getFonteRecursoFonr().getNomeFonr())) + "\" exercicio=\"" + builder.normalize(Util.normalizaCaracterMarcador(exercicioDesc)) + "\"");
		    				exercicioDesc = "";
		    			}

		    			/* somar nos valores do total da fonte */
		    			if(itemEstPrev.getValorAprovadoEfiep() != null){
		    				totFonAprovado = totFonAprovado + itemEstPrev.getValorAprovadoEfiep().doubleValue();
		    				totExeAprovado = totExeAprovado + itemEstPrev.getValorAprovadoEfiep().doubleValue();
		    			}
		    			if(itemEstPrev.getValorRevisadoEfiep() != null){
		    				totFonRevisado = totFonRevisado + itemEstPrev.getValorRevisadoEfiep().doubleValue();
		    				totExeRevisado = totExeRevisado + itemEstPrev.getValorRevisadoEfiep().doubleValue();
		    			}
	
		    			for(int i = 0; i < 6; i++){
							if(mostrarValores[i]){
								totFonValor[i] = totFonValor[i] + valores[i].doubleValue();
								totExeValor[i] = totExeValor[i] + valores[i].doubleValue();
							}
		    			}
		    			
		    			//IMPRIMIR RECURSO
						builder.addNode("recursoFinanceiro", "nome=\"" + builder.normalize(Util.normalizaCaracterMarcador(itemEstPrev.getRecursoRec().getNomeRec())) + "\"");
						//builder.addClosedNode("rec", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(itemEstPrev.getValorAprovadoEfiep().doubleValue()))+ "\"");
						//builder.addClosedNode("rec", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(itemEstPrev.getValorRevisadoEfiep().doubleValue()))+ "\"");
						
		    			for(int i = 0; i < 3; i++){
							if(mostrarDescFinanceiro[i]){
								if(i == 0)
									builder.addClosedNode("rec", "valor=\"" + builder.normalize(Pagina.trocaNullNumeroSemDecimal(itemEstPrev.getValorAprovadoEfiep())) + "\"");
								if(i == 1)
									builder.addClosedNode("rec", "valor=\"" + builder.normalize(Pagina.trocaNullNumeroSemDecimal(itemEstPrev.getValorRevisadoEfiep())) + "\"");
							}
						}
						
						for(int i = 0; i < 6; i++){
							if(mostrarValores[i])
		    					builder.addClosedNode("rec", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(valores[i].doubleValue()))+ "\"");
						}
						builder.closeNode("recursoFinanceiro");
		    		}
		    		
					//builder.closeNode("fonte");
					
					/* somar nos valores do total geral */
					totGerAprovado = totGerAprovado + totExeAprovado;
					totGerRevisado = totGerRevisado + totExeRevisado;
					
					for(int i = 0; i < 6; i++){
						if(mostrarValores[i])
							totGerValor[i] = totGerValor[i] + totExeValor[i];
					}
					
		    		//IMPRIMIR TOTAL
					builder.addNode("totalEvolucaoFinanceiraExercicio", "exercicio=\"" + builder.normalize(Util.normalizaCaracterMarcador(exercicioDescTotal)) + "\"");
					//builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerAprovado))+ "\"");
					//builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerRevisado))+ "\"");
					
	    			for(int i = 0; i < 3; i++){
						if(mostrarDescFinanceiro[i]){
							if(i == 0)
								builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totExeAprovado))+ "\"");
							if(i == 1)
								builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totExeRevisado))+ "\"");
						}
					}
					
					for(int i = 0; i < 6; i++){
						if(mostrarValores[i])
	    					builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totExeValor[i]))+ "\"");
					}
					builder.closeNode("totalEvolucaoFinanceiraExercicio");
					
		    		
					builder.closeNode("fonte");
				}
	    		//IMPRIMIR TOTAL
				builder.addNode("totalEvolucaoFinanceira");
				//builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerAprovado))+ "\"");
				//builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerRevisado))+ "\"");
				
    			for(int i = 0; i < 3; i++){
					if(mostrarDescFinanceiro[i]){
						if(i == 0)
							builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerAprovado))+ "\"");
						if(i == 1)
							builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerRevisado))+ "\"");
					}
				}
				
				for(int i = 0; i < 6; i++){
					if(mostrarValores[i])
    					builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerValor[i]))+ "\"");
				}
				builder.closeNode("totalEvolucaoFinanceira");

				builder.closeNode("evolucaoFinanceira");
	    	}
    	} catch (HibernateException e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do relatório: Evolução Financeira - " + e.getMessage());
    	}
    }
    
    
    
    /**
     * Monta a lista de codigos de itens cujo acompanhamento seja separado por órgao. 
     *  
     * @author N/C
     * @since N/C
     * @version N/C
     * @param aref
     * @param strCodigosItem
     * @param orgaoResponsavelStr 
     * @return
     * @throws ECARException
     */
    public List montaListaDescendentes(String codigosItensSelecionadosTela) throws ECARException{
    
    	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao (request);
    	ItemEstruturaIett iett = null;
    	//Obter lista de itens descendentes dos selecionado na tela
        List listaItensSelecionadosComDescendentes = new ArrayList();
      
    	
    	try {
    	
    		//se nao for separado por orgao 
            if(!"".equals(codigosItensSelecionadosTela)){
	       	String[] iettSelecionado = codigosItensSelecionadosTela.split(";");
				for(int i = 0; i < iettSelecionado.length; i++){
					if(!"".equals(iettSelecionado[i])){
						iett = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, new Long(iettSelecionado[i]));
						if(iett != null) {
							listaItensSelecionadosComDescendentes.add(iett);
							//Nao quer imprimir todos os filhos de cada item de estrutura, apenas os que foram selecionados na tela de monitoramento
							//listaItensSelecionadosComDescendentes.addAll(itemDao.getDescendentes(iett, true));
						}
					}
				}
			}
    		
    		
    		//Chamando Garbage Collector para ver se melhora a performance.
    		//TODO: Verificar se isso ajuda em alguma coisa...
    		//System.out.println("===================== Chamando o Garbage Collector ===========================");
    		System.gc();
    		
    		//TODO: Após gerar relatório para a SEPL mudar a regra abaixo
    		if("/ecar".equals(request.getContextPath())) {
    			// remover os itens com nível de estrutura maior que produto
    			for(Iterator it = listaItensSelecionadosComDescendentes.iterator(); it.hasNext();){
    	    		iett = (ItemEstruturaIett) it.next();
    	    		if(iett.getNivelIett().intValue() > 4) { 
    	    			it.remove();
    	    		}
    	    	}
    		}
    		
	        
    		return listaItensSelecionadosComDescendentes;
     	
    	} catch(Exception e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do relatório: Lista de Acompanhamento Selecionado - " + e.getMessage());               
    	}
    
    }
    
    
    
    
    /**
     * Monta a lista de orgaos a partir dos itens selecionados na tela.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param aref
     * @param strCodigosItem
     * @param orgaoResponsavelStr 
     * @return
     * @throws ECARException
     */
    public void montaListaOrgaosPelosItens(String codigos) throws ECARException{
    
    	
    	OrgaoOrg orgao = new OrgaoOrg();
    	OrgaoDao orgaoDao = new OrgaoDao(request);
    	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao (request);
    	
    	
    	
       	try {
			if(!"".equals(codigos)){
		       	String[] objSelecionado = codigos.split(";");
		       	
		      //cria a lista de orgaos
		      	for(int i = 0; i < objSelecionado.length; i++){
					if(!"".equals(objSelecionado[i])){
						
						//quando for montar a lista de órgãos pelo itens
						if(objSelecionado[i].contains("_org")) {
							String strCodOrg = objSelecionado[i].substring(objSelecionado[i].indexOf("_org") +4 , objSelecionado[i].length());
							if(!strCodOrg.equals("X")) {
								orgao = (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, new Long(strCodOrg));
								if(!listaOrgaos.contains(orgao)) {
									listaOrgaos.add(orgao);
								}
							} else {
								ultimo = true;
							}
						} 
					}	
				}
			}
			
       	} catch(Exception e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do relatório: Lista de Acompanhamento Selecionado - " + e.getMessage());               
    	}
    }	
    
    
    /**
     * Monta a String de codIett separados por orgao.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param aref
     * @param strCodigosItem
     * @param orgaoResponsavelStr 
     * @return
     * @throws ECARException
     */
    public String montaCodigosSelecionadosTelaComSeparacaoOrgao (String codigosItensSelecionadosTela, OrgaoOrg orgao) throws ECARException{
    
    	String strCodigosItem = "";
    
    	
       	try {
			if(!"".equals(codigosItensSelecionadosTela)){
				//monta a lista de itens que pertencem ao orgao
			  	String[] iettSelecionado = codigosItensSelecionadosTela.split(";");
              	for(int i = 0; i < iettSelecionado.length; i++){
        			if(!"".equals(iettSelecionado[i])){
        				String strIett = iettSelecionado[i].substring(0, iettSelecionado[i].indexOf("_org"));
        				String strCodOrg = iettSelecionado[i].substring(iettSelecionado[i].indexOf("_org") +4 , iettSelecionado[i].length());
        				
        				//se o item pertencer ao orgao
        				if((orgao != null && orgao.getCodOrg().toString().equals(strCodOrg)) ||
        					//se o item for sem orgao
        					(orgao == null && strCodOrg.equals("X"))) {
        					if(strCodigosItem.equals(""))
        						strCodigosItem =  strIett ;
        					else
        						strCodigosItem =  strCodigosItem + ";" + strIett ;
        				} 
        				
        			}
        		}
			}
			
			return strCodigosItem;
			
       	} catch(Exception e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do relatório: Lista de Acompanhamento Selecionado - " + e.getMessage());               
    	}
    }
    
    /**
     * Monta a lista de itens  a partir dos aris selecionados na tela.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param aref
     * @param strCodigosItem
     * @param orgaoResponsavelStr 
     * @return
     * @throws ECARException
     */
    public String montaListaItensOrgaosPeloAri(String codigos, boolean ehSeparadoPorOrgao) throws ECARException{
    
    	
    	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao (request);
    	
    	ItemEstruturaIett item = null;
    	String strCodOrg = "";
    	String strCodItem = "";
    	String listaItens = "";
    	
       	try {
			if(!"".equals(codigos)){
		       	String[] objSelecionado = codigos.split(";");
		       	
		      //cria a lista de orgaos
		      	for(int i = 0; i < objSelecionado.length; i++){
					if(!"".equals(objSelecionado[i])){
						String strCodAri = objSelecionado[i];
						if(strCodAri != null && !strCodAri.equals("")) {
							AcompReferenciaItemAri ari = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(strCodAri));
							if(ari!= null) {
								if(ehSeparadoPorOrgao && ari.getAcompReferenciaAref() != null && ari.getAcompReferenciaAref().getOrgaoOrg() != null) {
									strCodOrg = ari.getAcompReferenciaAref().getOrgaoOrg().getCodOrg().toString();
									if(!listaOrgaos.contains(ari.getAcompReferenciaAref().getOrgaoOrg())) {
										listaOrgaos.add(ari.getAcompReferenciaAref().getOrgaoOrg());
									} 	
								} else {
									// para itens sem orgao
									strCodOrg = "X";
									ultimo = true;
								}
								
								if(ari.getItemEstruturaIett() != null && ari.getItemEstruturaIett().getCodIett() != null) {
									strCodItem = ari.getItemEstruturaIett().getCodIett().toString();
								}
								
								if(listaItens.equals("")) {
									listaItens = strCodItem;
								} else {
									listaItens = ";" + strCodItem;
								}
								if(ehSeparadoPorOrgao) {
									listaItens += "_org" + strCodOrg;
								}
							}
						}
					}
				}	
			}
			
			return listaItens;
			
       	} catch(Exception e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do relatório: Lista de Acompanhamento Selecionado - " + e.getMessage());               
    	}
    }	
    
}
