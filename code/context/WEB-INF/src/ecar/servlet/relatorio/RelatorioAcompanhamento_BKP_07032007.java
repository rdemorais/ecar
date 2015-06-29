/*
 * Created on 29/09/2006
 *
 */
package ecar.servlet.relatorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import comum.util.Data;
import comum.util.Mensagem;
import comum.util.Pagina;
import comum.util.Util;
import comum.util.XmlBuilder;

import ecar.dao.AcompRealFisicoDao;
import ecar.dao.AcompReferenciaDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.ConfigRelatorioCfgrelDAO;
import ecar.dao.ConfiguracaoDao;
import ecar.dao.CorDao;
import ecar.dao.EstruturaAtributoDao;
import ecar.dao.EstruturaFuncaoDao;
import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstrtIndResulDao;
import ecar.dao.ItemEstruturaContaOrcamentoDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.ItemEstruturaPrevisaoDao;
import ecar.dao.ItemEstruturaRealizadoDao;
import ecar.dao.ModeloRelatorioMrelDAO;
import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ConfigRelatorioCfgrel;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EfItemEstContaEfiec;
import ecar.pojo.EfItemEstPrevisaoEfiep;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ModeloRelatorioMrel;
import ecar.util.Dominios;

/**
 * Servlet do Relatório de Acompanhamento. <br>
 * Relatório antigamente feito em Access, foi convertido para Java (FOP).<br>
 * 
 * @author aleixo
 * @since Outubro/2006.
 * @see Mantis: 6052
 */

public class RelatorioAcompanhamento_BKP_07032007 extends AbstractServletReportXmlXsl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2395457474744685932L;
	
	private static final String MODELO_ESTRUTURA = "ECAR-001C";
	
	private HttpServletRequest request;
	private ConfiguracaoDao configDao;
	private ConfiguracaoCfg config;
	private AcompReferenciaDao acompReferenciaDao;
	private AcompReferenciaItemDao acompReferenciaItemDao;
	private AcompRealFisicoDao acompRealFisicoDao;
	private CorDao corDao;
	private ConfigRelatorioCfgrel configRel;
	private ConfigRelatorioCfgrelDAO configRelDao;
	private String pathEcar;
	private String modelo;
	private EstruturaAtributoDao estAtribDao;
	private EstruturaFuncaoDao estFuncDao;
	private ItemEstruturaDao itemEstruturaDao;
	private ItemEstruturaPrevisaoDao itemEstPrevDao;
	private ItemEstruturaRealizadoDao itemEstRealizadoDao;
	private ItemEstruturaContaOrcamentoDao itemEstContaOrcDao;
	private ItemEstrtIndResulDao itemEstrtIndResulDao;
	
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
		
		Util.liberarImagem();
		
		acompReferenciaDao = new AcompReferenciaDao(request);
		acompReferenciaItemDao = new AcompReferenciaItemDao(request);
		acompRealFisicoDao = new AcompRealFisicoDao(request);
		configDao = new ConfiguracaoDao(request);
		corDao = new CorDao(request);
		configRelDao = new ConfigRelatorioCfgrelDAO(request);
		estAtribDao = new EstruturaAtributoDao(request);
		estFuncDao = new EstruturaFuncaoDao(request);
		itemEstruturaDao = new ItemEstruturaDao(request);
		itemEstPrevDao = new ItemEstruturaPrevisaoDao(request);
		itemEstRealizadoDao = new ItemEstruturaRealizadoDao(request);
		itemEstContaOrcDao = new ItemEstruturaContaOrcamentoDao(request);
		itemEstrtIndResulDao = new ItemEstrtIndResulDao(request);
		
		this.request = request;
		config = configDao.getConfiguracao();
		pathEcar = request.getContextPath();
		configRel = configRelDao.getConfigRelatorioCfgrel();		
		
		String opcaoModelo = Pagina.getParamStr(request, "opcaoModelo");
		ModeloRelatorioMrel mrel = new ModeloRelatorioMrelDAO(request).getModeloRelatorioByCodAlfa(opcaoModelo);
		
		AcompReferenciaAref mesReferencia = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(Pagina.getParamStr(request, "mesReferencia")));
		
		/*Definindo o título*/
		String titulo = "";
		if(!"".equals(Pagina.getParamStr(request, "tituloCustomizado")))
			titulo = Pagina.getParamStr(request, "tituloCustomizado");
		else {
			if(configRel != null && !"".equals(configRel.getTituloCfgrel()))
				titulo = configRel.getTituloCfgrel();
			else
				titulo = config.getTituloSistema();
				
		}

		/*Definindo o rodapé*/
		String rodape = geraDataRodape();
		if(!"".equals(Pagina.getParamStr(request, "rodapeCustomizado")))
			rodape += " - "  + Pagina.getParamStr(request, "rodapeCustomizado");
		else {
			if(configRel != null && configRel.getNotaRodapeCfgrel() != null && !"".equals(configRel.getNotaRodapeCfgrel()))
				rodape += " - " + configRel.getNotaRodapeCfgrel();
		}
		
		String arisSelecionados = Pagina.getParamStr(request, "arisSelecionados");
		
		/*
		 * Se arisSelecionados == "", é por que o relatório foi pedido da tela de Opçoes,
		 * ou seja, se opcaoModelo == ECAR-001B, são escolhidos todos os itens de um órgão específico;
		 * se opcaoModelo == ECAR-002B, são escolhidos todos os itens que possuem uma situação específica.
		 * 
		 * Obs.: opcaoModelo só vai ter esses valores se arisSelecionados == "". Caso arisSelecionados != "",
		 * é por que os itens foram filtrados e as opções escolhidas virão da tela de Formato.
		 */
		
		List codArisSelecionados = new ArrayList();
		
		if(!"".equals(arisSelecionados)){
			String[] codAris = arisSelecionados.split(";");
			for(int i = 0; i < codAris.length; i++){
				if(!"".equals(codAris[i]) && !";".equals(codAris[i])){
					codArisSelecionados.add(Long.valueOf(codAris[i]));
				}
			}
		}
		
		List arels = acompReferenciaItemDao.getAcompRelatorioAcompanhamentoByAris(codArisSelecionados, mesReferencia, opcaoModelo, Pagina.getParamStr(request, "chaveEscolhida"), Pagina.getParamStr(request, "tipoFuncAcompTpfa"));
		
		/* Início do relatório */
		builder.addNode("relatorio", 
				" titulo=\"" + builder.normalize(titulo) + "\"" +
				" mesReferencia=\"" + builder.normalize(mesReferencia.getNomeAref()) + "\"" +
				" codModelo=\"" + builder.normalize(mrel.getCodAlfaMrel() + " - " + mrel.getClassifMrel()) + "\"" +
				" rodape=\"" + builder.normalize(rodape) + "\"" +
				"");
		
		modelo = mrel.getCodAlfaMrel();
		
		geraXmlPrincipal(builder, arels);
		
		builder.closeNode("relatorio");
        return builder.toStringBuffer();
    }
    
	/**
	 * Gera xml principal.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param XmlBuilder builder
	 * @param List arels
	 * @throws ECARException
	 */
	private void geraXmlPrincipal(XmlBuilder builder, List arels) throws ECARException{
		builder.addNode("principal");
		
		if(arels != null && !arels.isEmpty()){
			Iterator it = arels.iterator();
			boolean primeiroItem = true;
			String siglaOrgaoImpresso = "";
			
			long codIett = -1;
			int indice = 0;
			Set ascendentes = new HashSet();
			while(it.hasNext()){
				AcompRelatorioArel arel = (AcompRelatorioArel) it.next();

				String siglaOrg = "";
				
				if(arel.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
					siglaOrg = arel.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg();
				}
				else {
					siglaOrg = "Órgão não informado";
				}

				String siglaOrgItem = "";
				if(MODELO_ESTRUTURA.equals(modelo)){
					if(arel.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
						siglaOrgItem = arel.getAcompReferenciaItemAri().getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg();
					}
					else {
						siglaOrgItem = "Órgão não informado";
					}
				}
				
				if(!siglaOrgaoImpresso.equals(siglaOrg)){
					siglaOrgaoImpresso = siglaOrg;
					if(!primeiroItem){
						builder.closeNode("itens");
					}
					
					String exibirOrgao = "S";
					String quebrarPaginaItens = "S";
					if(MODELO_ESTRUTURA.equals(modelo)){
						exibirOrgao = "N";
						quebrarPaginaItens = "N";
					}
					builder.addNode("itens", "orgao=\"" + builder.normalize(siglaOrgaoImpresso) + "\" exibirOrgao=\"" + builder.normalize(exibirOrgao) + "\" quebrarPaginaItens=\"" + builder.normalize(quebrarPaginaItens) + "\"");
				}
				
				
				if(codIett != arel.getAcompReferenciaItemAri().getItemEstruturaIett().getCodIett().longValue()){
					boolean geraHierarquia = primeiroItem || (ascendentes != null && !ascendentes.containsAll(itemEstruturaDao.getAscendentes(arel.getAcompReferenciaItemAri().getItemEstruturaIett())));
					codIett = arel.getAcompReferenciaItemAri().getItemEstruturaIett().getCodIett().longValue();
					ascendentes.addAll(geraXmlItens(builder, arel, estAtribDao.getDescricaoItemByAtributo(arel.getAcompReferenciaItemAri().getItemEstruturaIett(), arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getTipoAcompanhamentoTa()), false, geraHierarquia, siglaOrgItem, primeiroItem));
					
				}
				else {
					boolean exibirComplemento = false;
					if((indice + 1) < arels.size() || (indice == arels.size() - 1)){
						int proximo = indice + 1;
						
						try{
							AcompRelatorioArel proximoArel = (AcompRelatorioArel) arels.get(proximo);
							exibirComplemento = !arel.getAcompReferenciaItemAri().equals(proximoArel.getAcompReferenciaItemAri());
						}
						catch (IndexOutOfBoundsException e) {
							//Não tem mais itens na lista, exibe o complemento.
							exibirComplemento = true;
						}
					}
					
					geraXmlItens(builder, arel, "", exibirComplemento, false, siglaOrgItem, primeiroItem);
				}
				indice++;
				primeiroItem = false;
				
			}
			builder.closeNode("itens");
		}
		else {
			builder.addClosedNode("semItens");			
		}

		builder.closeNode("principal");
	}
	
	/**
	 * Gera itens xml.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param AcompRelatorioArel arel
     * @param String nomeItem
     * @param boolean exibirComplemento
     * @param boolean gerarHierarquia
     * @param String orgao
     * @param boolean primeiroItem
	 * @return List
	 * @throws ECARException
	 */
	private List geraXmlItens(XmlBuilder builder, AcompRelatorioArel arel, String nomeItem, 
			boolean exibirComplemento, boolean gerarHierarquia, String orgao, boolean primeiroItem) throws ECARException{

		if(!MODELO_ESTRUTURA.equals(modelo)){
			orgao = "";
		}
		
		builder.addNode("item", "nomeItem=\"" + builder.normalize(nomeItem) + "\" orgaoItem=\"" + builder.normalize(orgao) + "\"");
		List retorno = new ArrayList();
		
		if(MODELO_ESTRUTURA.equals(modelo) && gerarHierarquia){
			boolean quebrarPagina = true;
			
			if(primeiroItem)
				quebrarPagina = false;
			
			retorno = geraXmlHierarquia(builder, arel.getAcompReferenciaItemAri().getItemEstruturaIett(), quebrarPagina);
		}

		String caminhoImagem = pathEcar + "/images/relAcomp/" + corDao.getImagemRelatorio(arel.getCor(), arel.getTipoFuncAcompTpfa());
		String descricao = Util.normalizaCaracterMarcador(arel.getDescricaoArel());
		String observacoes = Util.normalizaCaracterMarcador(arel.getComplementoArel());
		
		String ocultarObservacoesParecer = new ConfiguracaoDao(request).getConfiguracao().getIndOcultarObservacoesParecer();
		
		if(descricao != null && !"".equals(descricao)){
			if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
				
				builder.addClosedNode("parecer", 
						" caminhoImagem=\"" + builder.normalize(caminhoImagem) + "\"" +
						" descricao=\"" + builder.normalize(descricao) + "\"" +
						" observacoes=\"" + builder.normalize(observacoes) + "\"" +
						"");
			}
			else{
				
				builder.addClosedNode("parecer", 
						" caminhoImagem=\"" + builder.normalize(caminhoImagem) + "\"" +
						" descricao=\"" + builder.normalize(descricao) + "\"" +
						"");
			}
		}
		else{
			if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
				
				builder.addClosedNode("parecer", 
						" caminhoImagem=\"" + builder.normalize(caminhoImagem) + "\"" +
						" descricao=\"" + builder.normalize("Sem itens de parecer cadastrados") + "\"" +
						" observacoes=\"\"" +
						"");
			}
			else{
				
				builder.addClosedNode("parecer", 
						" caminhoImagem=\"" + builder.normalize(caminhoImagem) + "\"" +
						" descricao=\"" + builder.normalize("Sem itens de parecer cadastrados") + "\"" +						
						"");
			}
		}
		
		if(exibirComplemento){
			if("S".equals(Pagina.getParamStr(request, "indResultado"))){
				geraXMLIndicadores(builder, arel.getAcompReferenciaItemAri());
				
				String arisSelecionados = Pagina.getParamStr(request, "arisSelecionados");
				List codArisSelecionados = new ArrayList();
				
				if(!"".equals(arisSelecionados)){
					String[] codAris = arisSelecionados.split(";");
					for(int i = 0; i < codAris.length; i++){
						if(!"".equals(codAris[i]) && !";".equals(codAris[i])){
							codArisSelecionados.add(Long.valueOf(codAris[i]));
						}
					}
				}				
				
    			builder.addNode("itensFilhos");
				List descendentes = new ItemEstruturaDao(request).getDescendentes(arel.getAcompReferenciaItemAri().getItemEstruturaIett(), true);
		        Iterator itDes = descendentes.iterator();
		        while(itDes.hasNext()){
		            ItemEstruturaIett item = (ItemEstruturaIett) itDes.next();
		            AcompReferenciaItemAri acompanhamentoFilho = acompReferenciaItemDao.getAcompReferenciaItemByItemEstruturaIett(arel.getAcompReferenciaItemAri().getAcompReferenciaAref(), item);
		            if(acompanhamentoFilho != null && !codArisSelecionados.contains(acompanhamentoFilho.getCodAri().toString())) {
		            	
		            	//FIXME: ver com Beier regra de ARF anteriores!!!
			            List listARF = acompRealFisicoDao.buscarPorIett(
			            		acompanhamentoFilho.getItemEstruturaIett().getCodIett(),
			            		Long.valueOf(acompanhamentoFilho.getAcompReferenciaAref().getMesAref()),
			            		Long.valueOf(acompanhamentoFilho.getAcompReferenciaAref().getAnoAref()));
			            if(listARF != null && !listARF.isEmpty()){
			            	geraXMLIndicadoresFilhos(builder, acompanhamentoFilho);		            	
			            }
		            }
		        }
    			builder.closeNode("itensFilhos");
		            
			}
			if("S".equals(Pagina.getParamStr(request, "evolucaoFinanceira")))
				geraXmlEvolucaoFinanceira(builder, arel.getAcompReferenciaItemAri());
		}
		
		builder.closeNode("item");
		return retorno;
	}
	
	/**
	 * Gera hierarquia Xml.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param XmlBuilder builder
	 * @param ItemEstruturaIett item
	 * @param boolean quebrarPagina
	 * @return List
	 * @throws ECARException
	 */
	private List geraXmlHierarquia(XmlBuilder builder, ItemEstruturaIett item, boolean quebrarPagina) throws ECARException{
		List ascendentes =  itemEstruturaDao.getAscendentes(item);
		
		if(ascendentes != null && !ascendentes.isEmpty()){
			if(quebrarPagina)
				builder.addNode("hierarquia", "quebrarPagina=\"S\"");
			else
				builder.addNode("hierarquia", "quebrarPagina=\"N\"");
			
			Iterator it = ascendentes.iterator();
			while(it.hasNext()){
				ItemEstruturaIett iettAsc = (ItemEstruturaIett) it.next();
				//System.out.println(iettAsc.getEstruturaEtt().getNomeEtt() + " - " + iettAsc.getNomeIett());
				String nomeItem = "";
				if(iettAsc.getSiglaIett() != null)
					nomeItem += "- ";
				nomeItem += iettAsc.getNomeIett();
				
				String siglaItem = iettAsc.getSiglaIett();
				
				builder.addClosedNode("itemHierarquia", "nomeItem=\"" + builder.normalize(nomeItem) + "\" nivel=\"" + builder.normalize(iettAsc.getNivelIett().toString()) + "\" sigla=\"" + builder.normalize(siglaItem) + "\"");
			}
			builder.closeNode("hierarquia");
		}
		return ascendentes;
	}


	/**
	 * Gera data Rodapé.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	private String geraDataRodape(){
		Date dataAtual = Data.getDataAtual();
		String dia = String.valueOf(Data.getDia(dataAtual));
		String mes = Data.getNomeMesExtenso(Data.getMes(dataAtual) + 1).toLowerCase();
		String ano = String.valueOf(Data.getAno(dataAtual));
		String hora = Data.getHorario(dataAtual);
				
		return Data.getDiaSemanaNomeExtenso(dataAtual) + ", " + dia + " de " + mes + " de " + ano + ", às " + hora; 
	}
	
	/**
	 * Pega o nome do arquivo xls.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
     * @return String
	 */
    public String getXslFileName() {
        return "relatorioAcompanhamento.xsl";
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
        String errorPage = "listaRelAcomp.jsp?msgOperacao=" + mensagem; 
        return errorPage;
    }
    
    /**
     * Gera xml evolução financeira.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param AcompReferenciaItemAri itemAri
     * @throws ECARException
     */
    private void geraXmlEvolucaoFinanceira(XmlBuilder builder, AcompReferenciaItemAri itemAri) throws ECARException{
    	try{
	    	List lista = itemEstPrevDao.getListaItemEstruturaPrevisao(itemAri.getItemEstruturaIett(), itemAri.getAcompReferenciaAref().getExercicioExe());
	    	Iterator it = lista.iterator();
	    	
	    	EfItemEstPrevisaoEfiep itemEstPrev = new EfItemEstPrevisaoEfiep();
	    	
	    	if (it.hasNext()){

	    		int colunas = 0;

	    		boolean mostrarValores[] = new boolean[6];

	    		String descricoes[] = new String[6];
	    		descricoes[0] = config.getFinanceiroDescValor1Cfg();
	    		descricoes[1] = config.getFinanceiroDescValor2Cfg();
	    		descricoes[2] = config.getFinanceiroDescValor3Cfg();
	    		descricoes[3] = config.getFinanceiroDescValor4Cfg();
	    		descricoes[4] = config.getFinanceiroDescValor5Cfg();
	    		descricoes[5] = config.getFinanceiroDescValor6Cfg();
	    		
				for(int i = 0; i < 6; i++){
	    			mostrarValores[i] = itemEstRealizadoDao.getVerificarMostrarValorByPosicaoCfg(i);
	    			if(mostrarValores[i]){
	    				colunas++;
	    			}
	    		}	    
				
	    		builder.addNode("evolucaoFinanceira", "colunasRealizadas=\"" + builder.normalize(String.valueOf(colunas)) + "\"");
	    		
	    		
	    		builder.addNode("colunas");
	    		
				builder.addClosedNode("coluna", "nome=\"\"");
				builder.addClosedNode("coluna", "nome=\"\"");
				builder.addClosedNode("coluna", "nome=\"Aprovado\"");
				builder.addClosedNode("coluna", "nome=\"Revisado\"");
	    		int numeroColunasExibidas = 2; //Aprovado - Revisado 
				
				for(int i = 0; i < 6; i++){
	    			mostrarValores[i] = itemEstRealizadoDao.getVerificarMostrarValorByPosicaoCfg(i);
	    			if(mostrarValores[i]){
	    				builder.addClosedNode("coluna", "nome=\"" + builder.normalize(descricoes[i]) + "\"");
	    				numeroColunasExibidas++;
	    			}
	    		}	    		

				builder.closeNode("colunas");

				/*
				 * 22.50cm / numeroColunasExibidas = tamanho de cada coluna dinâmica
				 * Quanto mais colunas, menos espaço no relatório.
				 */
				double t = 22.50;
				String tam = String.valueOf(t/numeroColunasExibidas) + "cm";
				
				builder.addNode("colunasHeader");
				for(int x = 1; x <= numeroColunasExibidas; x++){
					builder.addClosedNode("colunaHeader", "tamanho=\"" + builder.normalize(tam) + "\"");
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
				
				long codFonte = -1;
	    		while(it.hasNext()){
	    			itemEstPrev = (EfItemEstPrevisaoEfiep) it.next();
	    			
	    			/* ler EfItemEstContaEfiec */
	    			EfItemEstContaEfiec itemEstConta = 
	    					itemEstContaOrcDao.getItemEstruturaConta(
	    						itemAri.getItemEstruturaIett(),
	    						itemAri.getAcompReferenciaAref().getExercicioExe(),
	    						itemEstPrev.getFonteRecursoFonr(),
	    						itemEstPrev.getRecursoRec());
	    			
	    			/* verificar valores em Efier */
	    			Double[] valores = itemEstRealizadoDao.getSomaItemEstruturaRealizado(
	    					itemEstConta,
	    					itemAri.getAcompReferenciaAref().getExercicioExe());
	    			
	    			/* Ao trocar a fonte e ao iniciar imprimir linha de fonte */
	    			if(codFonte != itemEstPrev.getFonteRecursoFonr().getCodFonr().longValue()){
	    				/* se não é a primeira passada da fonte */
	    				if(codFonte != -1){

	    					builder.closeNode("fonte");
		    				
	    					/* somar nos valores do total geral */
	    					totGerAprovado = totGerAprovado + totFonAprovado;
	    					totGerRevisado = totGerRevisado + totFonRevisado;
	    					
	    					for(int i = 0; i < 6; i++){
	    						if(mostrarValores[i])
	    							totGerValor[i] = totGerValor[i] + totFonValor[i];
	    					}

	    					/* zerar os valores do total da fonte */
	    					totFonAprovado = 0;
	    					totFonRevisado = 0;

	    					for(int i = 0; i < 6; i++){
	    						totFonValor[i] = 0;
	    					}
	    				}
	    				
	    				codFonte = itemEstPrev.getFonteRecursoFonr().getCodFonr().longValue();
	    				//IMPRIMIR FONTE
	    				builder.addNode("fonte", "nome=\"" + builder.normalize(itemEstPrev.getFonteRecursoFonr().getNomeFonr()) + "\"");
	    			}
	    			
	    			/* somar nos valores do total da fonte */
	    			totFonAprovado = totFonAprovado + itemEstPrev.getValorAprovadoEfiep().doubleValue();
	    			totFonRevisado = totFonRevisado + itemEstPrev.getValorRevisadoEfiep().doubleValue();

	    			for(int i = 0; i < 6; i++){
						if(mostrarValores[i])
							totFonValor[i] = totFonValor[i] + valores[i].doubleValue();
	    			}
	    			
	    			//IMPRIMIR RECURSO
					builder.addNode("recurso", "nome=\"" + builder.normalize(itemEstPrev.getRecursoRec().getNomeRec()) + "\"");
					builder.addClosedNode("rec", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(itemEstPrev.getValorAprovadoEfiep().doubleValue()))+ "\"");
					builder.addClosedNode("rec", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(itemEstPrev.getValorRevisadoEfiep().doubleValue()))+ "\"");
					for(int i = 0; i < 6; i++){
						if(mostrarValores[i])
	    					builder.addClosedNode("rec", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(valores[i].doubleValue()))+ "\"");
					}
					builder.closeNode("recurso");
	    		}
	    		
				builder.closeNode("fonte");
				
				/* somar nos valores do total geral */
				totGerAprovado = totGerAprovado + totFonAprovado;
				totGerRevisado = totGerRevisado + totFonRevisado;

				for(int i = 0; i < 6; i++){
					if(mostrarValores[i])
						totGerValor[i] = totGerValor[i] + totFonValor[i];
				}
	    		
	    		//IMPRIMIR TOTAL
				builder.addNode("totalEvolucaoFinanceira");
				builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerAprovado))+ "\"");
				builder.addClosedNode("total", "valor=\"" + builder.normalize(Util.formataNumeroSemDecimal(totGerRevisado))+ "\"");
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
    		if(itemAri != null) {
    			//FIXME
    			/* Comentado pelo Claudismar, verificar com ele o que é...
				List exerciciosAnteriores = new ExercicioDao(null).getExerciciosAnteriores(itemAri.getAcompReferenciaAref().getExercicioExe());
				
   				exerciciosAnteriores.add(itemAri.getAcompReferenciaAref().getExercicioExe());
				
   				// recuperar os dois últimos anos
   				String ano1 = "";
   				String ano2 = "";
  				
   				String mostrarProjecao = ("S".equals(Pagina.getParamStr(request, "indProjecao"))) ? "S" : "N";
   				
  				ArrayList arrayListExercicios = new ArrayList();
   				
   				if(exerciciosAnteriores.size() == 1) {
					ExercicioExe exercicio = (ExercicioExe) exerciciosAnteriores.get(0);
					ano1 = exercicio.getDescricaoExe();
					arrayListExercicios.add(exercicio);
   				}
   				else if(exerciciosAnteriores.size() > 1) {
					ExercicioExe exercicio = (ExercicioExe) exerciciosAnteriores.get(exerciciosAnteriores.size() - 2);
					ano1 = exercicio.getDescricaoExe();
					arrayListExercicios.add(exercicio);
					exercicio = (ExercicioExe) exerciciosAnteriores.get(exerciciosAnteriores.size() - 1);
					ano2 = exercicio.getDescricaoExe();
					arrayListExercicios.add(exercicio);
   				}
  				
				List indResultados = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(itemAri, "T");
				if(indResultados != null && indResultados.size() > 0){

					builder.addNode("indicadores", "labelFuncao=\"" + builder.normalize(estFuncDao.getLabelIndicadoresResultado(itemAri.getItemEstruturaIett().getEstruturaEtt())) + "\" ano1=\"" + builder.normalize(ano1) + "\" ano2=\"" + builder.normalize(ano2) + "\" mostrarProjecao=\"" + builder.normalize(mostrarProjecao) + "\"");
					
					Iterator itIndResult = indResultados.iterator();
					while(itIndResult.hasNext()){
						AcompRealFisicoArf indicador = (AcompRealFisicoArf) itIndResult.next();

						String situacao = "";
						if(indicador.getSituacaoSit()!=null) {
							situacao = indicador.getSituacaoSit().getDescricaoSit();
						}

						// OBS.: para o relatório haverá 0, 1 ou 2 exercícios
						ArrayList arrayRealizado = new ArrayList();
						ArrayList arrayPrevisto = new ArrayList();
						Iterator itValoresExAnt = arrayListExercicios.iterator();
						double totalRealizado = 0;
						double totalPrevisto = 0;

						while(itValoresExAnt.hasNext()){
							ExercicioExe exercicioAnt = (ExercicioExe) itValoresExAnt.next();										
							double realizadoNoExercicio = 0;
							if(exercicioAnt.equals(itemAri.getAcompReferenciaAref().getExercicioExe())){
								// Se o exercício em questão é mesmo exercicio do periodo de referência e o indicador for acumulável
								// soma todas as quantidades até o mes/ano do periodo
								if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
									AcompReferenciaAref aref = itemAri.getAcompReferenciaAref();
									int mesRef = Integer.valueOf(aref.getMesAref()).intValue();
									int anoRef = Integer.valueOf(aref.getAnoAref()).intValue();
									realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicioAnt, indicador.getItemEstrtIndResulIettr(), mesRef, anoRef);																					
								} else {
									//se não for acumulável o realizado no exercicio é o realizado no periodo
									if (indicador.getQtdRealizadaArf() != null)
										realizadoNoExercicio = indicador.getQtdRealizadaArf().doubleValue();
								}
							} else {
								if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
									realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicioAnt, indicador.getItemEstrtIndResulIettr());										
								} else {
									realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exercicioAnt, indicador.getItemEstrtIndResulIettr());																						
								}
							} 
							
							double previstoNoExercicio = new ItemEstrtIndResulDao(null).getQtdPrevistoExercicio(indicador.getItemEstrtIndResulIettr(), exercicioAnt);
							if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
								totalRealizado += realizadoNoExercicio;
								totalPrevisto += previstoNoExercicio;
							}else{
								totalRealizado = realizadoNoExercicio;
								totalPrevisto = previstoNoExercicio;											
							}
							
			   				arrayPrevisto.add(new Double(previstoNoExercicio));
							arrayRealizado.add(new Double(realizadoNoExercicio));
							
						}

			  			String previstoAno1 = "";
			  			String realizadoAno1 = "";
			  			String previstoAno2 = ""; 
		  				String realizadoAno2 = "";

		   				if(arrayPrevisto.size() == 1) {
				  			previstoAno1 = Pagina.trocaNullNumeroDecimalSemMilhar((Double)arrayPrevisto.get(0));
				  			realizadoAno1 = Pagina.trocaNullNumeroDecimalSemMilhar((Double)arrayRealizado.get(0));;
		  				}
		  				else if(arrayPrevisto.size() > 1) {
				  			previstoAno1 = Pagina.trocaNullNumeroDecimalSemMilhar((Double)arrayPrevisto.get(0));
				  			realizadoAno1 = Pagina.trocaNullNumeroDecimalSemMilhar((Double)arrayRealizado.get(0));;

				  			previstoAno2 = Pagina.trocaNullNumeroDecimalSemMilhar((Double)arrayPrevisto.get(1));
				  			realizadoAno2 = Pagina.trocaNullNumeroDecimalSemMilhar((Double)arrayRealizado.get(1));;
		  				}

						double realizadoPrevisto = 0;
						if(totalPrevisto > 0) {
							realizadoPrevisto = ((totalRealizado/totalPrevisto) * 100);
						}

						String strProjecao = "";
						String strPorcentual = "-";

						if("S".equals(indicador.getItemEstrtIndResulIettr().getIndProjecaoIettr()) && totalRealizado > 0){
							double resultado = acompReferenciaItemDao.calculoProjecao(indicador.getItemEstrtIndResulIettr(), itemAri);
							ExercicioExe exercicioPrevisto = itemEstrtIndResulDao.getMaiorExercicioIndicador(indicador.getItemEstrtIndResulIettr());
							double qtdePrevista = itemEstrtIndResulDao.getQtdPrevistoExercicio(indicador.getItemEstrtIndResulIettr(), exercicioPrevisto);
							
							Mensagem msg = new Mensagem(this.request.getSession().getServletContext());

							if(resultado == qtdePrevista)
								strProjecao = msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingida");
							if(resultado > qtdePrevista)
								strProjecao = msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingidaAntes");												
							if(resultado < qtdePrevista){
								strProjecao = msg.getMensagem("acompRelatorio.indicadorResultado.projecao.naoSeraAtingida");																									
							}
							
							Double porcentagem = new Double((resultado/qtdePrevista) * 100);
							
							strPorcentual = Pagina.trocaNullNumeroDecimalSemMilhar(porcentagem);
						} else {
							if(totalRealizado == 0){
								strProjecao = "Não é possível realizar projeção sem informação de quantidades realizadas.";										
							} else {
								strProjecao = "N/A";
							}
						}
						builder.addClosedNode("indicador", 
	                         	"nome=\"" + builder.normalize(indicador.getItemEstrtIndResulIettr().getNomeIettir()) + "\"" + 
	                         	" situacao=\"" + builder.normalize(situacao) + "\"" +
	                         	" projecao=\"" + builder.normalize(strProjecao) + "\"" +
	                         	" percentualRealizadoPrevisto=\"" + builder.normalize(Pagina.trocaNullNumeroDecimalSemMilhar(new Double((realizadoPrevisto)))) + "\"" +
	                         	" percentual=\"" + builder.normalize(strPorcentual) + "\"" +
	                         	" totalPrevisto=\"" + builder.normalize(Pagina.trocaNullNumeroDecimalSemMilhar(new Double(totalPrevisto))) + "\"" +
	                         	" totalRealizado=\"" + builder.normalize(Pagina.trocaNullNumeroDecimalSemMilhar(new Double(totalRealizado))) + "\"" +
	                         	" realizadoNoMes=\"" + builder.normalize(Pagina.trocaNullNumeroDecimalSemMilhar(indicador.getQtdRealizadaArf()) + " " + indicador.getItemEstrtIndResulIettr().getUnidMedidaIettr()) + "\"" +
	                         	" previstoAno1=\"" + builder.normalize(previstoAno1) + "\"" +
	                         	" realizadoAno1=\"" + builder.normalize(realizadoAno1) + "\"" +
	                         	" previstoAno2=\"" + builder.normalize(previstoAno2) + "\"" +
	                         	" realizadoAno2=\"" + builder.normalize(realizadoAno2) + "\"" +
	                         	" mostrarProjecao=\"" + builder.normalize(mostrarProjecao) + "\""
	                         	);
					}
					builder.closeNode("indicadores");
				}
				*/

    			String mostrarProjecao = ("S".equals(Pagina.getParamStr(request, "indProjecao"))) ? "S" : "N";
    			//List<ExercicioExe> exercicios = new ExercicioDao(null).getExerciciosProjecao(itemAri.getItemEstruturaIett().getCodIett());
    			//Ref. mantis 7770: exibir os últimos quatro anos...
    			//Pegando exercicios do mais novo para mais antigo para pegar os ultimos anos primeiro...
    			List<ExercicioExe> todosExercicios = new ExercicioDao(null).listar(ExercicioExe.class, new String[] {"dataInicialExe","desc"});
    			List<ExercicioExe> exercicios = new ArrayList<ExercicioExe>();

    			if(todosExercicios == null || todosExercicios.isEmpty()) {
    				throw new Exception("Não existe exercícios cadastrado");
    			}
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

					builder.addNode("indicadores", "labelFuncao=\"" + builder.normalize(estFuncDao.getLabelIndicadoresResultado(itemAri.getItemEstruturaIett().getEstruturaEtt())) + "\" mostrarProjecao=\"" + builder.normalize(mostrarProjecao) + "\"");
					
					int numeroExercicios = 0;
					for(ExercicioExe exercicio : exercicios){
						builder.addClosedNode("columnExercicio", "ano=\"" + builder.normalize(exercicio.getDescricaoExe()) + "\"");
						builder.addClosedNode("indExercicio", "exercicio=\"" + builder.normalize(exercicio.getDescricaoExe()) + "\"");
						numeroExercicios++;
					}
					
					Iterator itIndResult = indResultados.iterator();
					while(itIndResult.hasNext()){
						AcompRealFisicoArf indicador = (AcompRealFisicoArf) itIndResult.next();
						
						String situacao = "";
						if(indicador.getSituacaoSit()!=null) {
							situacao = indicador.getSituacaoSit().getDescricaoSit();
						}

						double totalRealizado = 0;
						double totalPrevisto = 0;
						
						builder.addNode("indicador", 
	                         	"nome=\"" + builder.normalize(indicador.getItemEstrtIndResulIettr().getNomeIettir()) + "\"" + 
	                         	" situacao=\"" + builder.normalize(situacao) + "\"" +
	                         	" realizadoNoMes=\"" + builder.normalize(Pagina.trocaNullMoeda(indicador.getQtdRealizadaArf()) + " " + indicador.getItemEstrtIndResulIettr().getUnidMedidaIettr()) + "\"" +
	                         	" numeroExercicios=\"" + builder.normalize(String.valueOf(numeroExercicios)) + "\"" +
	                         	" mostrarProjecao=\"" + builder.normalize(mostrarProjecao) + "\""
	                         	);
						

						for(ExercicioExe exercicio : exercicios){
							double realizadoNoExercicio = 0;
//							if(exercicio.equals(itemAri.getAcompReferenciaAref().getExercicioExe())){
//								// Se o exercício em questão é mesmo exercicio do periodo de referência e o indicador for acumulável
//								// soma todas as quantidades até o mes/ano do periodo
//								if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
//									AcompReferenciaAref aref = itemAri.getAcompReferenciaAref();
//									int mesRef = Integer.valueOf(aref.getMesAref()).intValue();
//									int anoRef = Integer.valueOf(aref.getAnoAref()).intValue();
//									realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicio, indicador.getItemEstrtIndResulIettr(), mesRef, anoRef);																					
//								} else {
//									//se não for acumulável o realizado no exercicio é o realizado no periodo
//									if (indicador.getQtdRealizadaArf() != null)
//										realizadoNoExercicio = indicador.getQtdRealizadaArf().doubleValue();
//								}
//							} else {
//								if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
//									realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicio, indicador.getItemEstrtIndResulIettr(), itemAri.getAcompReferenciaAref());										
//								} else {
//									realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exercicio, indicador.getItemEstrtIndResulIettr(), itemAri.getAcompReferenciaAref());																						
//								}
//							} 
							
							if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
								realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicio(exercicio, indicador.getItemEstrtIndResulIettr(), itemAri.getAcompReferenciaAref());										
							} else {
								realizadoNoExercicio = acompRealFisicoDao.getQtdRealizadaExercicioNaoAcumulavel(exercicio, indicador.getItemEstrtIndResulIettr(), itemAri.getAcompReferenciaAref());																						
							}
							

							double previstoNoExercicio = new ItemEstrtIndResulDao(null).getQtdPrevistoExercicio(indicador.getItemEstrtIndResulIettr(), exercicio);
							if("S".equals(indicador.getItemEstrtIndResulIettr().getIndAcumulavelIettr())){
								totalRealizado += realizadoNoExercicio;
								totalPrevisto += previstoNoExercicio;
							}else{
								totalRealizado = realizadoNoExercicio;
								totalPrevisto = previstoNoExercicio;											
							}

							builder.addClosedNode("valorExercicio", 
								"exercicio=\"" + builder.normalize(exercicio.getDescricaoExe()) + "\"" + 
								" valorPrevisto=\"" + builder.normalize(Pagina.trocaNullMoeda(previstoNoExercicio)) + "\"" +
								" valorRealizado=\"" + builder.normalize(Pagina.trocaNullMoeda(realizadoNoExercicio)) + "\"");								
						}
						
						double realizadoPrevisto = 0;
						if(totalPrevisto > 0) {
							realizadoPrevisto = ((totalRealizado/totalPrevisto) * 100);
						}
						builder.addClosedNode("valorTotal", 
	                         	" percentualRealizadoPrevisto=\"" + builder.normalize(Pagina.trocaNullNumeroDecimalSemMilhar(new Double((realizadoPrevisto)))) + "\"" +
	                         	" totalPrevisto=\"" + builder.normalize(Pagina.trocaNullMoeda(new Double(totalPrevisto))) + "\"" +
	                         	" totalRealizado=\"" + builder.normalize(Pagina.trocaNullMoeda(new Double(totalRealizado))) + "\""
	                         	);
						

						String strProjecao = "";
						String strPorcentual = "-";

						if("S".equals(indicador.getItemEstrtIndResulIettr().getIndProjecaoIettr()) && totalRealizado > 0){
							double resultado = acompReferenciaItemDao.calculoProjecao(indicador.getItemEstrtIndResulIettr(), itemAri);
							ExercicioExe exercicioPrevisto = itemEstrtIndResulDao.getMaiorExercicioIndicador(indicador.getItemEstrtIndResulIettr());
							double qtdePrevista = itemEstrtIndResulDao.getQtdPrevistoExercicio(indicador.getItemEstrtIndResulIettr(), exercicioPrevisto);
							
							Mensagem msg = new Mensagem(this.request.getSession().getServletContext());

							if(resultado == qtdePrevista)
								strProjecao = msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingida");
							if(resultado > qtdePrevista)
								strProjecao = msg.getMensagem("acompRelatorio.indicadorResultado.projecao.seraAtingidaAntes");												
							if(resultado < qtdePrevista){
								strProjecao = msg.getMensagem("acompRelatorio.indicadorResultado.projecao.naoSeraAtingida");																									
							}
							
							Double porcentagem = new Double((resultado/qtdePrevista) * 100);
							
							strPorcentual = Pagina.trocaNullNumeroDecimalSemMilhar(porcentagem);
						} else {
							if(totalRealizado == 0){
								strProjecao = "Não é possível realizar projeção sem informação de quantidades realizadas.";										
							} else {
								strProjecao = "N/A";
							}
						}
						
						builder.addClosedNode("valorProjecao",
	                         	" projecao=\"" + builder.normalize(strProjecao) + "\"" +
	                         	" mostrarProjecao=\"" + builder.normalize(mostrarProjecao) + "\"" +
	                         	" percentual=\"" + builder.normalize(strPorcentual) + "\"" +
	                         	" situacao=\"" + builder.normalize(situacao) + "\""
								);
						
						
						builder.closeNode("indicador");

					}
					builder.closeNode("indicadores");
				}
			}

    	} catch(Exception e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do relatório: Indicadores - " + e.getMessage());               
    	}
    }

    /**
     * Gera Relatorio IndicadoresFilhos.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param XmlBuilder builder
     * @param AcompReferenciaItemAri itemAri
     * @throws ECARException
     */
    private void geraXMLIndicadoresFilhos(XmlBuilder builder, AcompReferenciaItemAri itemAri) throws ECARException{
    	try {
    		String nomeItem = itemAri.getItemEstruturaIett().getNomeIett();
			String siglaOrg = "";
			
			if(itemAri.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
				siglaOrg = itemAri.getItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg();
			}
			else {
				siglaOrg = "Órgão não informado";
			}

    		builder.addNode("itemFilho", "nomeItem=\"" + builder.normalize(nomeItem) + "\" orgaoItem=\"" + builder.normalize(siglaOrg) + "\"");
    		geraXMLIndicadores(builder, itemAri);
			builder.closeNode("itemFilho");

    	} catch(Exception e){
    		this.logger.error(e);
    		throw new ECARException("Erro na criação do relatório: IndicadoresFilhos - " + e.getMessage());               
    	}
    }
}
