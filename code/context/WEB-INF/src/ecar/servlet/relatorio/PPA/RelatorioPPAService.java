package ecar.servlet.relatorio.PPA;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrutFisicoIettf;
import ecar.pojo.ItemEstruturaIettPPA;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.UsuarioUsu;
import ecar.servlet.relatorio.PPA.bean.AcaoBean;
import ecar.servlet.relatorio.PPA.bean.IndicadorBean;
import ecar.servlet.relatorio.PPA.bean.ProdutoBean;
import ecar.servlet.relatorio.PPA.bean.ProgramaBean;
import ecar.servlet.relatorio.PPA.bean.RelatorioPPABean;
import ecar.servlet.relatorio.PPA.bean.comparator.ItemEstruturaComparatorNome;
import ecar.servlet.relatorio.PPA.bean.comparator.ItemEstruturaComparatorSiglaNumero;
import ecar.servlet.relatorio.PPA.bean.comparator.ProdutoBeanComparatorDescricao;
import ecar.servlet.relatorio.PPA_Util.CalcularTotalVisitor;

/**
 * Classe de acoplamento do relatorio PPA com o sistema E-car
 * 
 * 
 * @author Gabriel Solana
 * @since 08/2007
 */
public class RelatorioPPAService {

	/**
	 * Log da classe
	 */
	private Logger logger = Logger.getLogger(this.getClass());

	/**
	 * Requisicao
	 */
	private HttpServletRequest request;

	/**
	 * Dados do relatorio
	 */
	private ArrayList<RelatorioPPABean> dados;

	/**
	 * Calcula os totais na tabela de resumo do relatorio PPA
	 */
	private final CalcularTotalVisitor calcularTotal = new CalcularTotalVisitor();

	private RelatorioParametro buscarItensService;

	private CalcularPrevisaoService calcularPrevisaoService;
	
	CarregaItensCompleto itensService = null;
	
	private final String CONSTANTE_LINHA_ACAO_OBRIGACOES_ESPECIAIS = "99";
	
	/*
	 * Impossibilita instanciar objeto de classe externa
	 */
	private RelatorioPPAService() {
		dados = new ArrayList<RelatorioPPABean>();
	}

	

	
	/**
	 * Retorna instancia da classe RelatorioPPAService com parametro de entrada
	 * request
	 * 
	 * @param paramRequest
	 * @return
	 */
	public static RelatorioPPAService getInstance(
			HttpServletRequest paramRequest) {
		RelatorioPPAService objeto = new RelatorioPPAService();
		objeto.request = paramRequest;

		return objeto;
	}

	/**
	 * Recupera dados e dispara actions para geracao do relatorio
	 * 
         * @param tipoParametro
         * @param programa
         * @param orgao 
         * @param listaCriteriosCom 
         * @param listaCriteriosSem 
         * @return Listagem
         * @throws ECARException
	 */
	public ArrayList<RelatorioPPABean> generatePPA(String tipoParametro,
			ArrayList<String> programa, String orgao,
			ArrayList<String> listaCriteriosCom,
			ArrayList<String> listaCriteriosSem) throws ECARException {

		try {
			calcularPrevisaoService = new CalcularPrevisaoService(request);

			testParametros(tipoParametro, programa, orgao, listaCriteriosCom,
					listaCriteriosSem);
			logger.info("Carregando dados de linhas de acao...");
			ArrayList<ItemEstruturaIettPPA> itens = buscarItensService.execute(
					programa, Long.valueOf(orgao), listaCriteriosCom,
					listaCriteriosSem);

			if (buscarItensService.getTipo().equals(
					TipoPesquisaRelatorio.TIPO_1)
					|| buscarItensService.getTipo().equals(
							TipoPesquisaRelatorio.TIPO_2)) {
				getDados(itens);
			}

		} catch (Exception e) {
			e.printStackTrace(System.out);
			logger.error(e);
			throw new ECARException(
					"Nao foi possivel carregar dados do relatorio");
		}

		return dados == null ? new ArrayList<RelatorioPPABean>() : dados;

	}


	/**
	 * Realiza testes de parametros e instancia service para consulta de itens
	 * 
	 * @param programa
	 * @param orgao
	 * @param listaCriteriosCom
	 * @param listaCriteriosSem
	 * @return
	 */
	private void testParametros(String tipoParametro, ArrayList programa,
			String orgao, ArrayList listaCriteriosCom,
			ArrayList listaCriteriosSem) {

		final String PPA_COMPLETO = "completo";
		final String PPA_ORGAO = "orgao";
		final String PPA_PROGRAMA = "programa";

		if (PPA_COMPLETO.equalsIgnoreCase(tipoParametro)) {

			if (listaCriteriosCom.isEmpty() && listaCriteriosSem.isEmpty()) {
				logger.info("Carregando relatorio PPA Projeto de Lei:::: Parametro:Completo sem criterios");
				buscarItensService = RelatorioParametroFactory.getPesquisa(
						request, TipoPesquisaRelatorio.TIPO_1);
			} else {
				logger.info("Carregando relatorio PPA Projeto de Lei:::: Parametro:Completo com filtro de criterios");
				buscarItensService = RelatorioParametroFactory.getPesquisa(
						request, TipoPesquisaRelatorio.TIPO_2);
			}

		}

		// caso nao seja possivel identificar parametros trazer todos
		if (buscarItensService == null) {
			buscarItensService = RelatorioParametroFactory.getPesquisa(request,
					TipoPesquisaRelatorio.TIPO_1);
		}

	}

	/**
	 * recebe todos os itens ativos da estrutura de nivel de programa, percorre
	 * e gera os beans de relatorio
	 * 
	 */
	private void getDados(ArrayList<ItemEstruturaIettPPA> itensLinhaAcao) {

		boolean vazio = true;
		try {

			
			
			TipoPesquisaRelatorio tipoRelatorio = buscarItensService.getTipo();

			if ( tipoRelatorio.equals(TipoPesquisaRelatorio.TIPO_1)){
				itensService = new CarregaItensCompleto(request, false);	
			}else{
				itensService = new CarregaItensCompleto(request, true);
			}			
			itensService.initPrevisao(request);
			
			dados = new ArrayList<RelatorioPPABean>();

			for (Iterator<ItemEstruturaIettPPA> iter = itensLinhaAcao.iterator(); iter
					.hasNext();) {
				ItemEstruturaIettPPA iett = iter.next();

				boolean check99 = "99".equals( iett.getSiglaIett() )?false:true;
				
				vazio = false;

				RelatorioPPABean linha = new RelatorioPPABean();
				linha.setCodigo(iett.getSiglaIett());
				linha.setNome(iett.getNomeIett());
				linha.setDescricao( iett.getDescricaoIett() );
				logger.info("Carregando dados de programa da linha de acao::" + linha.getNome());
				
				//Set programaTmp = Reduzir.reduzir( iett.getItemEstruturaIetts() );
				linha.setProgramas(generateBeanPrograma(  iett.getItemEstruturaIetts() , check99 ) ); 

				dados.add(linha);

			}

			if (vazio) {
				logger.info("Nao existe item de nivelprograma na estrutura no periodo estipulado!!!");
			}

		} catch (Exception e) {
			logger.error("Nao foi possivel carregar dados", e);
		}
	}

	/**
	 * gera bean de programa, realiza chamada para calculo de previsao e realiza
	 * calculo de total da tabela.
	 * 
	 * @param itemPrograma
	 *            Item de programa referencial
	 * @throws Exception
	 */
	private ArrayList<ProgramaBean> generateBeanPrograma(
			Set<ItemEstruturaIettPPA> programas, boolean bean99 ) throws Exception {

		ArrayList<ProgramaBean> progs = new ArrayList<ProgramaBean>();

		for (Iterator iter = reordenarPorNome(programas).iterator(); iter
				.hasNext();) {
			ItemEstruturaIettPPA programa = (ItemEstruturaIettPPA) iter.next();

			if ("S".equalsIgnoreCase(programa.getIndAtivoIett())) {

				ProgramaBean item = geraPrograma(programa,bean99);
				
				if ( bean99 ){
					geraPrevisao(item, programa);
					calcularTotal.visit(item); // gera calculo dos totais
				}else{
					item.setTotalGeralRecursoTot( new BigDecimal(-1) );
				}

				progs.add(item);

			}

		}

		return progs;

	}

	/**
	 * Reordena Colecao de elementos de acordo com objeto Comparator
	 * 
	 * @param list
	 *            listagem
	 * @return Set de objetos reordenados
	 */
	private Set reordenarPorSiglaNumero(Collection list) {
		TreeSet<ItemEstruturaIettPPA> novalista = new TreeSet<ItemEstruturaIettPPA>(
				new ItemEstruturaComparatorSiglaNumero());
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			ItemEstruturaIettPPA item = (ItemEstruturaIettPPA) iter.next();
			novalista.add(item);

		}
		return novalista;
	}	
	
	
	/**
	 * Reordena Colecao de elementos de acordo com objeto Comparator
	 * 
	 * @param list
	 *            listagem
	 * @return Set de objetos reordenados
	 */
	private Set reordenarPorNome(Collection list) {
		TreeSet<ItemEstruturaIettPPA> novalista = new TreeSet<ItemEstruturaIettPPA>(
				new ItemEstruturaComparatorNome());
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			ItemEstruturaIettPPA item = (ItemEstruturaIettPPA) iter.next();
			novalista.add(item);

		}
		return novalista;
	}

	/**
	 * Realiza chamada para calculo de valor aprovado para o item de programa e
	 * gera previsao bean.
	 * 
	 * @param item
	 *            POJO a ser populado
	 * @param itemPrograma
	 *            Item de programa referencial
	 * @throws ECARException
	 */
	private void geraPrevisao(ProgramaBean item, ItemEstruturaIettPPA itemPrograma)
			throws ECARException {
		BigDecimal[] previsoes = calcularPrevisaoService.calcularPrevisao((itemPrograma));
		final BigDecimal ZERO = new BigDecimal(0);
		// populando bean de relatorio com os valores de previsao
		if (previsoes != null && previsoes.length == 24) {
			item.setValor1(previsoes[0] == null ? ZERO : previsoes[0]);
			item.setValor2(previsoes[1] == null ? ZERO : previsoes[1]);
			item.setValor3(previsoes[2] == null ? ZERO : previsoes[2]);
			item.setValor4(previsoes[3] == null ? ZERO : previsoes[3]);
			item.setValor5(previsoes[4] == null ? ZERO : previsoes[4]);
			item.setValor6(previsoes[5] == null ? ZERO : previsoes[5]);
			item.setValor7(previsoes[6] == null ? ZERO : previsoes[6]);
			item.setValor8(previsoes[7] == null ? ZERO : previsoes[7]);

			item.setValor9(previsoes[8] == null ? ZERO : previsoes[8]);
			item.setValor10(previsoes[9] == null ? ZERO : previsoes[9]);
			item.setValor11(previsoes[10] == null ? ZERO : previsoes[10]);
			item.setValor12(previsoes[11] == null ? ZERO : previsoes[11]);
			item.setValor13(previsoes[12] == null ? ZERO : previsoes[12]);
			item.setValor14(previsoes[13] == null ? ZERO : previsoes[13]);
			item.setValor15(previsoes[14] == null ? ZERO : previsoes[14]);
			item.setValor16(previsoes[15] == null ? ZERO : previsoes[15]);

			item.setValor17(previsoes[16] == null ? ZERO : previsoes[16]);
			item.setValor18(previsoes[17] == null ? ZERO : previsoes[17]);
			item.setValor19(previsoes[18] == null ? ZERO : previsoes[18]);
			item.setValor20(previsoes[19] == null ? ZERO : previsoes[19]);
			item.setValor21(previsoes[20] == null ? ZERO : previsoes[20]);
			item.setValor22(previsoes[21] == null ? ZERO : previsoes[21]);
			item.setValor23(previsoes[22] == null ? ZERO : previsoes[22]);
			item.setValor24(previsoes[23] == null ? ZERO : previsoes[23]);

		} else {

			item.setValor1(ZERO);
			item.setValor2(ZERO);
			item.setValor3(ZERO);
			item.setValor4(ZERO);
			item.setValor5(ZERO);
			item.setValor6(ZERO);
			item.setValor7(ZERO);
			item.setValor8(ZERO);

			item.setValor9(ZERO);
			item.setValor10(ZERO);
			item.setValor11(ZERO);
			item.setValor12(ZERO);
			item.setValor13(ZERO);
			item.setValor14(ZERO);
			item.setValor15(ZERO);
			item.setValor16(ZERO);

			item.setValor17(ZERO);
			item.setValor18(ZERO);
			item.setValor19(ZERO);
			item.setValor20(ZERO);
			item.setValor21(ZERO);
			item.setValor22(ZERO);
			item.setValor23(ZERO);
			item.setValor24(ZERO);
		}

	}

	/**
	 * Gera Bean de Valores Aprovados.
	 * 
	 * @param acao
	 *            POJO a ser populado
	 * @param item
	 *            Item de programa referencial
	 * @throws ECARException
	 */
	private void geraPrevisao(AcaoBean acao, ItemEstruturaIettPPA item)
			throws ECARException {
		BigDecimal[] previsoes = calcularPrevisaoService.calcularPrevisaoSemIntegralizacao((item));
		final BigDecimal ZERO = new BigDecimal(0);
		// populando bean de relatorio com os valores de previsao
		if (previsoes != null && previsoes.length == 24) {
			acao.setValor1(previsoes[0] == null ? ZERO : previsoes[0]);
			acao.setValor2(previsoes[1] == null ? ZERO : previsoes[1]);
			acao.setValor3(previsoes[2] == null ? ZERO : previsoes[2]);
			acao.setValor4(previsoes[3] == null ? ZERO : previsoes[3]);
			acao.setValor5(previsoes[4] == null ? ZERO : previsoes[4]);
			acao.setValor6(previsoes[5] == null ? ZERO : previsoes[5]);
			acao.setValor7(previsoes[6] == null ? ZERO : previsoes[6]);
			acao.setValor8(previsoes[7] == null ? ZERO : previsoes[7]);

			acao.setValor9(previsoes[8] == null ? ZERO : previsoes[8]);
			acao.setValor10(previsoes[9] == null ? ZERO : previsoes[9]);
			acao.setValor11(previsoes[10] == null ? ZERO : previsoes[10]);
			acao.setValor12(previsoes[11] == null ? ZERO : previsoes[11]);
			acao.setValor13(previsoes[12] == null ? ZERO : previsoes[12]);
			acao.setValor14(previsoes[13] == null ? ZERO : previsoes[13]);
			acao.setValor15(previsoes[14] == null ? ZERO : previsoes[14]);
			acao.setValor16(previsoes[15] == null ? ZERO : previsoes[15]);

			acao.setValor17(previsoes[16] == null ? ZERO : previsoes[16]);
			acao.setValor18(previsoes[17] == null ? ZERO : previsoes[17]);
			acao.setValor19(previsoes[18] == null ? ZERO : previsoes[18]);
			acao.setValor20(previsoes[19] == null ? ZERO : previsoes[19]);
			acao.setValor21(previsoes[20] == null ? ZERO : previsoes[20]);
			acao.setValor22(previsoes[21] == null ? ZERO : previsoes[21]);
			acao.setValor23(previsoes[22] == null ? ZERO : previsoes[22]);
			acao.setValor24(previsoes[23] == null ? ZERO : previsoes[23]);

		} else {

			acao.setValor1(ZERO);
			acao.setValor2(ZERO);
			acao.setValor3(ZERO);
			acao.setValor4(ZERO);
			acao.setValor5(ZERO);
			acao.setValor6(ZERO);
			acao.setValor7(ZERO);
			acao.setValor8(ZERO);

			acao.setValor9(ZERO);
			acao.setValor10(ZERO);
			acao.setValor11(ZERO);
			acao.setValor12(ZERO);
			acao.setValor13(ZERO);
			acao.setValor14(ZERO);
			acao.setValor15(ZERO);
			acao.setValor16(ZERO);

			acao.setValor17(ZERO);
			acao.setValor18(ZERO);
			acao.setValor19(ZERO);
			acao.setValor20(ZERO);
			acao.setValor21(ZERO);
			acao.setValor22(ZERO);
			acao.setValor23(ZERO);
			acao.setValor24(ZERO);
		}

	}

	/**
	 * Adiciona um bean de Programa para a tabela de PPA
	 * 
	 * @param itemPrograma
	 */
	private ProgramaBean geraPrograma(ItemEstruturaIettPPA itemPrograma, boolean bean99  )
			throws ECARException {

		ProgramaBean programa = new ProgramaBean();

		
		if ( bean99 ){
			programa.setCodigo(itemPrograma.getSiglaIett());
			programa.setNome(itemPrograma.getNomeIett());
			programa.setJustificativa(Util.normalizaCaracterMarcador(itemPrograma.getDescricaoIett()));
			programa.setObjetivo(Util.normalizaCaracterMarcador(itemPrograma.getObjetivoEspecificoIett()));
			programa.setEstrategiaImpl(Util.normalizaCaracterMarcador(itemPrograma.getObjetivoGeralIett()));
			programa.setOrgao(itemPrograma.getOrgaoOrgByCodOrgaoResponsavel1Iett()
					.getDescricaoOrg());
	
			Set usuarioOrgao = itemPrograma.getOrgaoOrgByCodOrgaoResponsavel1Iett()
					.getUsuarioUsus();
			String usrTmp = "";
			for (Iterator iter = usuarioOrgao.iterator(); iter.hasNext();) {
				UsuarioUsu element = (UsuarioUsu) iter.next();
				usrTmp = element.getNomeUsu();
			}
	
			programa.setGestorPrograma(usrTmp);
			programa.setInicio(itemPrograma.getDataInicioIett());
			programa.setFim(itemPrograma.getDataTerminoIett());
			programa.setPublicoAlvo(Util.normalizaCaracterMarcador(itemPrograma.getDescricaoR2()));
	
			Set atributos = itemPrograma.getItemEstruturaSisAtributoIettSatbs();
			String tipoAcao = null;
			
			for (Iterator iter = atributos.iterator(); iter.hasNext();) {
				ItemEstruturaSisAtributoIettSatb atributo = (ItemEstruturaSisAtributoIettSatb) iter
						.next();
				if ( atributo.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga().longValue() == 15L  ){
					tipoAcao = atributo.getSisAtributoSatb().getDescricaoSatb();
					break;
				}
				
			}
			programa.setTipoAcao(tipoAcao == null ? "" : tipoAcao);

			
			
			
			//logger.info("Carregando dados de Indicadores do programa ::" + itemPrograma.getNomeIett());
			
	//		logger.info("Codigo do item:: " + itemPrograma.getCodIett().toString());
			ArrayList<IndicadorBean> indicadores = generateIndicadores(itemPrograma.getItemEstrtIndResulIettrs());
	
			programa.setIndicadores(indicadores);

	
			//logger.info("Carregando dados de acao do programa ::"
					//+ itemPrograma.getNomeIett());
		}
			//Set acaoTmp = Reduzir.reduzir( itemPrograma.getItemEstruturaIetts()  );
			ArrayList<AcaoBean> ItensAcao = generateAcaoBean( itemPrograma.getItemEstruturaIetts()  );

		programa.setAcoes(ItensAcao);

		return programa;

	}
	
	private ArrayList<IndicadorBean> generateIndicadores(Set indicadores)
			throws ECARException {

		IndicadorBean bean = null;

		ArrayList<IndicadorBean> retorno = new ArrayList<IndicadorBean>();

		for (Iterator iter = indicadores.iterator(); iter.hasNext();) {
			ItemEstrtIndResulIettr ind = (ItemEstrtIndResulIettr) iter.next();
			
			if ( ind.getIndAtivoIettr()!=null &&  "S".equalsIgnoreCase(ind.getIndAtivoIettr())) {

				bean = new IndicadorBean();

				bean.setNome(ind.getNomeIettir());
				bean.setUnidade(ind.getUnidMedidaIettr());
				bean.setPeriodicidade(ind.getPeriodicidadePrdc() != null ? ind
						.getPeriodicidadePrdc().getDescricaoPrdc() : "");
				bean.setFonte(ind.getFonteIettr());
				bean.setIndiceRecente(ind.getIndiceMaisRecenteIettr());
				bean.setDataApuracao(ind.getDataApuracaoIettr());

				Set fisico = ind.getItemEstrutFisicoIettfs();
				for (Iterator iterator = fisico.iterator(); iterator.hasNext();) {
					ItemEstrutFisicoIettf elemento = (ItemEstrutFisicoIettf) iterator
							.next();
					//Mantis 0010128 - Qtd prevista não é mais informado por exercício
					/*
					if (elemento.getExercicioExe().getDescricaoExe()
							.equalsIgnoreCase("2008")) {
						bean.setIndiceDt1(elemento.getQtdPrevistaIettf());

					}
					if (elemento.getExercicioExe().getDescricaoExe()
							.equalsIgnoreCase("2009")) {
						bean.setIndiceDt2(elemento.getQtdPrevistaIettf());

					}
					if (elemento.getExercicioExe().getDescricaoExe()
							.equalsIgnoreCase("2010")) {
						bean.setIndiceDt3(elemento.getQtdPrevistaIettf());

					}

					if (elemento.getExercicioExe().getDescricaoExe()
							.equalsIgnoreCase("2011")) {
						bean.setIndiceDt4(elemento.getQtdPrevistaIettf());

					}
					*/
				} // for fisicoiett

				retorno.add(bean);
			} // if ( "S" )
		} // for indicadores
		
		return contadorIndicador(retorno);

	}

	private ArrayList<IndicadorBean> contadorIndicador(ArrayList<IndicadorBean> list){
		
		
		int cont =0;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			IndicadorBean element = (IndicadorBean) iter.next();
			element.setIndice(new Integer(cont));
			cont++;
			
		}
		return list;
	}
	
	
	/**
	 * Gera o Bean de itens de acao
	 * 
	 * @param acoesParam
	 *            Itens de acao
	 * @return Itens de acao ( Pojo do relatorio )
	 * @throws ECARException
	 */
	private ArrayList<AcaoBean> generateAcaoBean(Set acoesParam)
			throws ECARException {

		try {
			AcaoBean beanTmp = new AcaoBean();
			ArrayList<AcaoBean> acoes = new ArrayList<AcaoBean>();
			for (Iterator iterAcao = reordenarPorSiglaNumero(acoesParam)
					.iterator(); iterAcao.hasNext();) {
				ItemEstruturaIettPPA itemAcao = (ItemEstruturaIettPPA) iterAcao.next();

				if ("S".equalsIgnoreCase(itemAcao.getIndAtivoIett())) {

					beanTmp = new AcaoBean();

					beanTmp.setCodigo(itemAcao.getSiglaIett());
					beanTmp.setNome(itemAcao.getNomeIett());
					beanTmp.setOrgao(itemAcao.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());
					
					// Nao exibir finalidade e descricao para acoes de linha de acao Obrigaçoes especiais
					if (  itemAcao.getItemEstruturaIett().getSiglaIett().equalsIgnoreCase( CONSTANTE_LINHA_ACAO_OBRIGACOES_ESPECIAIS ) ){
						beanTmp.setFinalidade( null );
						beanTmp.setDescricao( null );						
					}else{
						beanTmp.setFinalidade( itemAcao.getObjetivoGeralIett() );
						beanTmp.setDescricao( itemAcao.getDescricaoIett() );						
					}

					
					geraPrevisao(beanTmp, itemAcao); // recebe os valores da tabela
					calcularTotal.visit(beanTmp); // calcula os totais da tabela
					
/*					if (  "2380".equalsIgnoreCase(itemAcao.getSiglaIett()) ){
						logger.info("Carregando dados de Produtos da acao ::  " + itemAcao.getCodIett()  + "  " + itemAcao.getNomeIett());
					}
*/					//Set prodColecao = Reduzir.reduzir( itemAcao.getItemEstruturaIetts() );
					Set produtos =  itemAcao.getItemEstruturaIetts();
					
					if ( produtos!=null && !produtos.isEmpty()){
						ArrayList<ProdutoBean> prod = itensService.generateProdutoPPA(produtos);						
						beanTmp.setProdutos( contadorProduto(reordenarProduto(prod))  );
						
					}
					
					acoes.add(beanTmp);

				}

			}

			return acoes;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			logger.error(e);
			throw new ECARException("Nao foi possivel gerar bean de acao", e);
		}

	}

	// ordenar produto por descricao
	private TreeSet<ProdutoBean> reordenarProduto(ArrayList<ProdutoBean> colecao){
		TreeSet<ProdutoBean> novaColecao = new TreeSet<ProdutoBean>( new ProdutoBeanComparatorDescricao() );
		for (Iterator iter = colecao.iterator(); iter.hasNext();) {
			ProdutoBean prod = (ProdutoBean) iter.next();			
			novaColecao.add( prod );
		}		
		return novaColecao;
	}
	
	// linha corsim cor nao
	private TreeSet<ProdutoBean> contadorProduto(TreeSet<ProdutoBean> colecao){
		int cont = 0;
		for (Iterator iter = colecao.iterator(); iter.hasNext();) {
			ProdutoBean prod = (ProdutoBean) iter.next();
			prod.setIndice( new Integer(cont) );			
			cont++;
		}		
		return colecao;
	}

	
	
}
