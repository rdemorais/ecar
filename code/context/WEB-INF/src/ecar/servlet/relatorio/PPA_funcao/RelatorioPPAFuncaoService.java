package ecar.servlet.relatorio.PPA_funcao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.ItemEstruturaPrevisaoDao;
import ecar.exception.ECARException;
import ecar.pojo.AreaAre;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.SubAreaSare;
import ecar.servlet.relatorio.PPA_funcao.bean.FuncaoAreaAreBean;
import ecar.servlet.relatorio.PPA_funcao.bean.FuncaoSubAreaSareBean;
import ecar.servlet.relatorio.PPA_funcao.bean.comparator.FuncaoComparatorAreaAre;
import ecar.servlet.relatorio.PPA_funcao.bean.comparator.FuncaoComparatorSubAreaSare;

/**
 * Classe de acoplamento do relatorio PPA por Funcao com o sistema E-car
 * 

 * @author Gabriel Solana
 * @since 09/2007
 */
public class RelatorioPPAFuncaoService {

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
	private TreeSet<FuncaoAreaAreBean> dados;
	
	private static final Long codEstruturaAcao = Long.valueOf(20);
	
	
	// constante de base de dados - Status ativo / inativo
	//private final Character CONSTANTE_IND_ATIVO = new Character('S');	
	
	/**
	 * Listagem de exercicios do periodo
	 */
	private List<ExercicioExe> listaExercicios = new ArrayList<ExercicioExe>();
	
	// constante de base de dados - Periodo de 2008 - 2011
	private final Long CONSTANTE_PERIODO_EXERCICIO = Long.valueOf(2);
	
	
	private BigDecimal totalGeral1;
	private BigDecimal totalGeral2;
	private BigDecimal totalGeral3;
	private BigDecimal totalGeral4;
	private BigDecimal totalGeral;	
	
        /**
         *
         * @return
         */
        public BigDecimal getTotalGeral() {
		return totalGeral;
	}

        /**
         *
         * @return
         */
        public BigDecimal getTotalGeral1() {
		return totalGeral1;
	}

        /**
         *
         * @return
         */
        public BigDecimal getTotalGeral2() {
		return totalGeral2;
	}

        /**
         *
         * @return
         */
        public BigDecimal getTotalGeral3() {
		return totalGeral3;
	}

        /**
         *
         * @return
         */
        public BigDecimal getTotalGeral4() {
		return totalGeral4;
	}



	/*
	 * Impossibilita instanciar objeto de classe externa
	 */
	private RelatorioPPAFuncaoService(){
		dados = new TreeSet<FuncaoAreaAreBean>( new FuncaoComparatorAreaAre() );
	}
	
	/**
	 * Retorna instancia da classe RelatorioPPAProgramaService com parametro de entrada request
	 * @param paramRequest
	 * @return
	 */
	public static RelatorioPPAFuncaoService getInstance( HttpServletRequest paramRequest ){
		RelatorioPPAFuncaoService objeto = new RelatorioPPAFuncaoService();
		objeto.request  = paramRequest;
		return objeto;
	}
	
	/**
	 * Recupera dados e dispara actions para geracao do relatorio
	 * @return Listagem
	 */
	public TreeSet<FuncaoAreaAreBean> generatePPA(){
		loadExerciciosValidos();
		getDados();
		//imprimirTela();
		calcularTotalAreaARE();
		return dados;
	}
	
	
	/**
	 * Retorna itens para geracao do relatorio PPA
	 * @param periodoIni
	 * @param periodoFim
	 * @return
	 */
	private void getDados(){

		try {
			final BigDecimal ZERO = new BigDecimal(0);
			
			final ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
			//List itens = itemDao.getItensByEstrutura( codEstruturaAcao );
			List itens = new ArrayList();
			logger.info( "Quantidade de itens:: " + itens.size() );
			TreeSet<FuncaoAreaAreBean> colecao = new TreeSet<FuncaoAreaAreBean>( new FuncaoComparatorAreaAre() );
			
			for (Iterator iter = itens.iterator(); iter.hasNext();) {
				ItemEstruturaIett itemAcao = (ItemEstruturaIett) iter.next();
				
				AreaAre areaAre = itemAcao.getAreaAre();
				SubAreaSare subAreaSare = itemAcao.getSubAreaSare();
				
				FuncaoAreaAreBean tmpAreBean = new FuncaoAreaAreBean();
				tmpAreBean.setAreaAre( areaAre );
				tmpAreBean.setCodigoIdentAre( areaAre.getCodigoIdentAre() );
				tmpAreBean.setNomeAre( areaAre.getNomeAre() );
				
				FuncaoSubAreaSareBean tmpSareBean = new FuncaoSubAreaSareBean();
				tmpSareBean.setSubAreaSare(subAreaSare);					
				tmpSareBean.setCodigoIdentSare( subAreaSare.getCodigoIdentSare() );
				tmpSareBean.setNomeSare( subAreaSare.getNomeSare() );
				
				TreeSet<FuncaoSubAreaSareBean> tmpSubAreaSet = new TreeSet<FuncaoSubAreaSareBean>( new FuncaoComparatorSubAreaSare() );
				tmpSubAreaSet.add(tmpSareBean);					
				tmpAreBean.setSubAreaList( tmpSubAreaSet );
				
				ArrayList<Long> codIettList = new ArrayList<Long>();
				codIettList.add( itemAcao.getCodIett() );
				tmpAreBean.setCodIettList( codIettList );
				
				BigDecimal[] totais = getPrevisao(itemAcao);
				
				tmpAreBean.setValor1( totais[0]==null?ZERO:totais[0] );
				tmpAreBean.setValor2( totais[1]==null?ZERO:totais[1] );
				tmpAreBean.setValor3( totais[2]==null?ZERO:totais[2] );
				tmpAreBean.setValor4( totais[3]==null?ZERO:totais[3] );
				BigDecimal total = new BigDecimal(0D);
				total = total.add( tmpAreBean.getValor1() );
				total = total.add( tmpAreBean.getValor2() );
				total = total.add( tmpAreBean.getValor3() );
				total = total.add( tmpAreBean.getValor4() );					
				tmpAreBean.setTotal( total );
				
				tmpSareBean.setValor1( totais[0]==null?ZERO:totais[0] );
				tmpSareBean.setValor2( totais[1]==null?ZERO:totais[1] );
				tmpSareBean.setValor3( totais[2]==null?ZERO:totais[2] );
				tmpSareBean.setValor4( totais[3]==null?ZERO:totais[3] );
				BigDecimal totalSare = new BigDecimal(0D);
				totalSare = totalSare.add( tmpSareBean.getValor1() );
				totalSare = totalSare.add( tmpSareBean.getValor2() );
				totalSare = totalSare.add( tmpSareBean.getValor3() );
				totalSare = totalSare.add( tmpSareBean.getValor4() );					
				tmpSareBean.setTotal( totalSare );
				
				boolean jaExisteAre = colecao.contains( tmpAreBean );
				if ( jaExisteAre ){
					for (Iterator iterator = colecao.iterator(); iterator
							.hasNext();) {
						FuncaoAreaAreBean itemAreaAre = (FuncaoAreaAreBean) iterator.next();
						
						if (  tmpAreBean.equals( itemAreaAre ) ){
							itemAreaAre.setValor1(  itemAreaAre.getValor1().add(  tmpAreBean.getValor1() ) );
							itemAreaAre.setValor2(  itemAreaAre.getValor2().add(  tmpAreBean.getValor2() ) );
							itemAreaAre.setValor3(  itemAreaAre.getValor3().add(  tmpAreBean.getValor3() ) );
							itemAreaAre.setValor4(  itemAreaAre.getValor4().add(  tmpAreBean.getValor4() ) );
							itemAreaAre.setTotal(  itemAreaAre.getTotal().add(  tmpAreBean.getTotal() ) );
							
							TreeSet<FuncaoSubAreaSareBean> itemSubAreaSet = itemAreaAre.getSubAreaList();
							boolean naoExisteSare = (!itemSubAreaSet.contains( tmpSareBean ));
							if(naoExisteSare){
								itemSubAreaSet.add( tmpSareBean );
							}
							
							ArrayList<Long> itemCodIettList = itemAreaAre.getCodIettList();
							itemCodIettList.addAll( tmpAreBean.getCodIettList() );
							
						}
						
					}
					
				}else{
					boolean adicionou = colecao.add( tmpAreBean );
					if ( !adicionou ){
						logger.error( "Nao foi possivel adicionar bean " + tmpAreBean.toString() );
					}
				}
				
			}
			
			this.dados = colecao;
			
			
		} catch (Exception e) {
			logger.error("Nao foi possivel carregar dados", e);
		}		
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
	
	/*
	private void imprimirTela(){
		for (Iterator iter = dados.iterator(); iter.hasNext();) {
			FuncaoAreaAreBean itemAre = (FuncaoAreaAreBean) iter.next();
			logger.info( "\n\n\n\n" );
			logger.info( "### " + itemAre );
			
			for (Iterator iterator = itemAre.getSubAreaList().iterator(); iterator.hasNext();) {
				FuncaoSubAreaSareBean subItem = (FuncaoSubAreaSareBean) iterator.next();
				logger.info( "###### " + subItem);
			}
		}
	}
	*/
	
	private void calcularTotalAreaARE(){
		
		BigDecimal localTotalGeral1 = new BigDecimal(0);
		BigDecimal localTotalGeral2 = new BigDecimal(0);
		BigDecimal localTotalGeral3 = new BigDecimal(0);
		BigDecimal localTotalGeral4 = new BigDecimal(0);
		BigDecimal localTotalGeral = new BigDecimal(0);		
		
		for (Iterator iter = dados.iterator(); iter.hasNext();) {
			FuncaoAreaAreBean tmp = (FuncaoAreaAreBean) iter.next();
			
			BigDecimal vlr1 = new BigDecimal(0);
			BigDecimal vlr2 = new BigDecimal(0);
			BigDecimal vlr3 = new BigDecimal(0);
			BigDecimal vlr4 = new BigDecimal(0);
			BigDecimal total = new BigDecimal(0);
			
			int cont = 0;
			for (Iterator iter2 = tmp.getSubAreaList().iterator(); iter2.hasNext();) {
				FuncaoSubAreaSareBean tmp2 = (FuncaoSubAreaSareBean) iter2.next();
				
				vlr1 = vlr1.add( tmp2.getValor1() );
				vlr2 = vlr2.add( tmp2.getValor2() );
				vlr3 = vlr3.add( tmp2.getValor3() );
				vlr4 = vlr4.add( tmp2.getValor4() );
				total = total.add( tmp2.getTotal() );
				tmp2.setIndice(new Integer(cont));
				cont++;
			}
			
			tmp.setValor1( vlr1 );
			tmp.setValor2( vlr2 );
			tmp.setValor3( vlr3 );
			tmp.setValor4( vlr4 );
			tmp.setTotal( total );			
			
			localTotalGeral1 = localTotalGeral1.add( vlr1 );
			localTotalGeral2 = localTotalGeral2.add( vlr2 );
			localTotalGeral3 = localTotalGeral3.add( vlr3 );
			localTotalGeral4 = localTotalGeral4.add( vlr4 );
			localTotalGeral = localTotalGeral.add( total );
			
		}
		
		this.totalGeral1 = localTotalGeral1;
		this.totalGeral2 = localTotalGeral2;
		this.totalGeral3 = localTotalGeral3;
		this.totalGeral4 = localTotalGeral4;
		this.totalGeral = localTotalGeral;		
	}
	
	
}
