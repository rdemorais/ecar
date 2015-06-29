 package ecar.servlet.relatorio.PPA_LinhaPrograma;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.ItemEstruturaPrevisaoDao;
import ecar.exception.ECARException;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstruturaIett;
import ecar.servlet.relatorio.PPA_LinhaAcao.LinhaAcaoComparatorNome;
import ecar.servlet.relatorio.PPA_LinhaAcao.LinhaAcaoComparatorSigla;
import ecar.servlet.relatorio.PPA_LinhaAcao.PPA_LinhaAcaoBean;
import ecar.servlet.relatorio.PPA_Util.CalcularTotalVisitor;

/**
 * Classe de acoplamento do relatorio PPA por Linha de Acao e Programa com o sistema E-car
 * 
 * <BR/>
 * Arvore de chamada de Metodos ::
 * 
 * RelatorioPPAServlet
 * 	generatePPA
 * 	  generateBeans
 * 			geraPrevisao
 * <BR/>
 * @author Gabriel Solana
 * @since 07/2007
 */
public class RelatorioPPALinhaProgramaService {

	/**
	 * Log da classe
	 */
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * Requisicao
	 */
	private HttpServletRequest request;
	
	private static final Long codEstruturaLinhaAcao = Long.valueOf(27);

	/**
	 * Dados do relatorio
	 */
	private ArrayList<PPA_LinhaAcaoBean> dados;
	
	
	// constante de base de dados - Periodo de 2008 - 2011
	private final Long CONSTANTE_PERIODO_EXERCICIO = Long.valueOf(2);
	
	// constante de base de dados - Status ativo / inativo
	private final Character CONSTANTE_IND_ATIVO = new Character('S');
	
	/**
	 * Listagem de exercicios do periodo
	 */
	private List<ExercicioExe> listaExercicios = new ArrayList<ExercicioExe>();
	
	
	/**
	 * Calcula os totais na tabela de resumo do relatorio PPA
	 */
	private final CalcularTotalVisitor calcularTotal = new CalcularTotalVisitor();
	
	private BigDecimal totalGeral1 = new BigDecimal(0);
	private BigDecimal totalGeral2 = new BigDecimal(0);
	private BigDecimal totalGeral3 = new BigDecimal(0);
	private BigDecimal totalGeral4 = new BigDecimal(0);	
	
	
	/*
	 * Impossibilita instanciar objeto de classe externa
	 */
	private RelatorioPPALinhaProgramaService(){
		dados = new ArrayList<PPA_LinhaAcaoBean>();
	}
	
	/**
	 * Retorna instancia da classe RelatorioPPAProgramaService com parametro de entrada request
	 * @param paramRequest
	 * @return
	 */
	public static RelatorioPPALinhaProgramaService getInstance( HttpServletRequest paramRequest ){
		RelatorioPPALinhaProgramaService objeto = new RelatorioPPALinhaProgramaService();
		objeto.request  = paramRequest;
		return objeto;
	}
	
	/**
	 * Recupera dados e dispara actions para geracao do relatorio
	 * @return Listagem
	 */
	public ArrayList<PPA_LinhaAcaoBean> generatePPA(){
		getDados();
		gerarContador();
		return dados;
	}
	
        /**
         *
         * @return
         */
        public BigDecimal getTotalGeral1(){
		return totalGeral1;
	}

        /**
         *
         * @return
         */
        public BigDecimal getTotalGeral2(){
		return totalGeral2;		
	}

        /**
         *
         * @return
         */
        public BigDecimal getTotalGeral3(){
		return totalGeral3;		
	}
	
        /**
         *
         * @return
         */
        public BigDecimal getTotalGeral4(){
		return totalGeral4;		
	}	
	
        /**
         *
         * @return
         */
        public BigDecimal getTotalGeral(){
		return new BigDecimal(0).add(totalGeral1).add(totalGeral2).add(totalGeral3).add(totalGeral4);
	}
	
	private void gerarContador(){
		
		int cont = 0;
		for (Iterator iter = dados.iterator(); iter.hasNext();) {
			PPA_LinhaAcaoBean elemento = (PPA_LinhaAcaoBean) iter.next();
			elemento.setIndice(cont);
			cont++;
		}
		
	}
	
	/**
	 * Carrega exercicios do periodo de 2008 - 2011
	 *
	 */
	private void loadExerciciosValidos(){
		
		try {
			
			final ExercicioDao exercicioDao = new ExercicioDao(request);
			
			// carrega todos os exercicios do periodo de 2008 / 2011
			listaExercicios = exercicioDao.getExercicioByPeriodicidade( CONSTANTE_PERIODO_EXERCICIO );
			
		} catch (ECARException e) {
			logger.error("Nao foi possivel carregar Exercicio", e);
		} catch (Exception e) {
			logger.error("Nao foi possivel carregar Exercicio", e);			
		}				
		
	}		
	
	
	/**
	 * Retorna itens para geracao do relatorio PPA
	 * @param periodoIni
	 * @param periodoFim
	 * @return
	 */
	private void getDados(){
		
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		ArrayList<ItemEstruturaIett> itens = new ArrayList<ItemEstruturaIett>();
		try {
			
			itens = new ArrayList<ItemEstruturaIett>(itemEstruturaDao.getItensByEstrutura(codEstruturaLinhaAcao));
			
			// 	carrega exercicios do periodo de 2008 / 2011
			loadExerciciosValidos();
			
			
			if(itens != null){
				if (itens.size()==0){
					logger.info("Nao existe itens na estrutura com este criterio de pesquisa!!!");
				}else{
					dados = generateBeans(itens);
				}
			}
		} catch (Exception e) {
			logger.error("Nao foi possivel carregar dados", e);
		}		
	}
	
	/**
	 * Reordena Colecao de elementos de acordo com objeto Comparator
	 * @param list listagem
	 * @return Set de objetos reordenados
	 */
	private Set reordenarPrograma(Collection list){
		TreeSet<ItemEstruturaIett> novalista = new TreeSet<ItemEstruturaIett>( new LinhaAcaoComparatorNome() );
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			ItemEstruturaIett item = (ItemEstruturaIett) iter.next();
			
			if ( CONSTANTE_IND_ATIVO.toString().equalsIgnoreCase( item.getIndAtivoIett() ) ){
				novalista.add(item);	
			}
			
		}			
		return novalista;
	}
	
	/**
	 * Reordena Colecao de elementos de acordo com objeto Comparator
	 * @param list listagem
	 * @return Set de objetos reordenados
	 */
	private Set reordenarLinhaAcao(Collection list){
		TreeSet<ItemEstruturaIett> novalista = new TreeSet<ItemEstruturaIett>( new LinhaAcaoComparatorSigla() );
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			ItemEstruturaIett item = (ItemEstruturaIett) iter.next();
			
			if ( CONSTANTE_IND_ATIVO.toString().equalsIgnoreCase( item.getIndAtivoIett() ) ){
				novalista.add(item);	
			}
			
		}			
		return novalista;
	}	
	
	/**
	 * Dispara servicos para gerar os beans do relatorio PPA
	 * @param itemPrograma Item de nivel de acao referencial
	 * @throws Exception
	 */
	private ArrayList<PPA_LinhaAcaoBean> generateBeans( Collection itens ) throws Exception{

		ArrayList<PPA_LinhaAcaoBean> beans = new ArrayList<PPA_LinhaAcaoBean>();
			
		final BigDecimal ZERO = new BigDecimal(0);
		
		// percorre niveis
		for (Iterator iter = reordenarLinhaAcao(itens).iterator(); iter.hasNext();) {
			ItemEstruturaIett item = (ItemEstruturaIett) iter.next();
		
			PPA_LinhaAcaoBean Niveltmp = new PPA_LinhaAcaoBean();
			Niveltmp.setNome( item.getNomeIett() );		
			Niveltmp.setNivelItem(1);
			beans.add(Niveltmp);

			BigDecimal totalProgramaAno1 = new BigDecimal(0);
			BigDecimal totalProgramaAno2 = new BigDecimal(0);
			BigDecimal totalProgramaAno3 = new BigDecimal(0);
			BigDecimal totalProgramaAno4 = new BigDecimal(0);
			
			// percorre programas do nivel
			for (Iterator iter2 = reordenarPrograma(item.getItemEstruturaIetts()).iterator(); iter2.hasNext();) {
				ItemEstruturaIett programa = (ItemEstruturaIett) iter2.next();
				
				PPA_LinhaAcaoBean programaTmp = new PPA_LinhaAcaoBean();
				programaTmp.setNome( programa.getNomeIett() );
				programaTmp.setNivelItem(Integer.valueOf(2));
				beans.add(programaTmp);	

				
				BigDecimal totalAcoesAno1 = new BigDecimal(0);
				BigDecimal totalAcoesAno2 = new BigDecimal(0);
				BigDecimal totalAcoesAno3 = new BigDecimal(0);
				BigDecimal totalAcoesAno4 = new BigDecimal(0);

				//percorre acoes do programa
				for (Iterator iter3 = reordenarPrograma(programa.getItemEstruturaIetts()).iterator(); iter3.hasNext();) {
					ItemEstruturaIett acao = (ItemEstruturaIett) iter3.next();
					
					PPA_LinhaAcaoBean acaoTmp = new PPA_LinhaAcaoBean();
					acaoTmp.setNivelItem(Integer.valueOf(3));
					BigDecimal[] totais = getPrevisao(acao);
					
					acaoTmp.setValor1( totais[0]==null?ZERO:totais[0] );
					acaoTmp.setValor2( totais[1]==null?ZERO:totais[1] );
					acaoTmp.setValor3( totais[2]==null?ZERO:totais[2] );
					acaoTmp.setValor4( totais[3]==null?ZERO:totais[3] );
					
					calcularTotal.visit(acaoTmp);
					totalAcoesAno1 = totalAcoesAno1.add( acaoTmp.getValor1() );
					totalAcoesAno2 = totalAcoesAno2.add( acaoTmp.getValor2() );
					totalAcoesAno3 = totalAcoesAno3.add( acaoTmp.getValor3() );
					totalAcoesAno4 = totalAcoesAno4.add( acaoTmp.getValor4() );
					
				} // acao
				
				
				// os valores do programa eh a soma de todas as acoes daquele programa
				programaTmp.setValor1( totalAcoesAno1 );
				programaTmp.setValor2( totalAcoesAno2 );
				programaTmp.setValor3( totalAcoesAno3 );
				programaTmp.setValor4( totalAcoesAno4 );
				calcularTotal.visit( programaTmp );
				
				totalProgramaAno1 = totalProgramaAno1.add( programaTmp.getValor1() );
				totalProgramaAno2 = totalProgramaAno2.add( programaTmp.getValor2() );
				totalProgramaAno3 = totalProgramaAno3.add( programaTmp.getValor3() );
				totalProgramaAno4 = totalProgramaAno4.add( programaTmp.getValor4() );
				
			} // programa
			
			// os valores do nivel eh a soma de todos os programas.
			Niveltmp.setValor1(  totalProgramaAno1 );
			Niveltmp.setValor2(  totalProgramaAno2 );
			Niveltmp.setValor3(  totalProgramaAno3 );
			Niveltmp.setValor4(  totalProgramaAno4 );
			calcularTotal.visit( Niveltmp );
			
			totalGeral1 = totalGeral1.add( Niveltmp.getValor1() );
			totalGeral2 = totalGeral2.add( Niveltmp.getValor2() );
			totalGeral3 = totalGeral3.add( Niveltmp.getValor3() );
			totalGeral4 = totalGeral4.add( Niveltmp.getValor4() );			
			
			
		} // nivel
		
		return beans;
		
	}
	
	
	/**
	 * Gera os totais valores da tabela para o item  na tabela do relatorio de PPA 
	 * @param Item estrutura
	 * @return array de valores da tabela PPA
	 * @throws ECARException
	 */
	private BigDecimal[] getPrevisao(final ItemEstruturaIett itemEstrut ) throws ECARException{
		
		final ItemEstruturaPrevisaoDao prevDao = new ItemEstruturaPrevisaoDao(request);

		ArrayList<BigDecimal> previsao = new ArrayList<BigDecimal>();
		BigDecimal previsaoItem = null;
		for (Iterator iterExer = listaExercicios.iterator(); iterExer.hasNext();) {
			ExercicioExe elementoExerc = (ExercicioExe) iterExer.next();
			previsaoItem =  prevDao.previsaoItemAcao( itemEstrut.getCodIett(), elementoExerc.getCodExe() ) ;
			previsao.add(previsaoItem);
		}
		return previsao.toArray(new BigDecimal[previsao.size()]);

	}
	
}
