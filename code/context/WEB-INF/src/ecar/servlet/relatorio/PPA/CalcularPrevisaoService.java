package ecar.servlet.relatorio.PPA;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ecar.dao.ExercicioDao;
import ecar.dao.FonteRecursoDao;
import ecar.dao.ItemEstrtIndResultLocalIettirlDao;
import ecar.dao.ItemEstruturaPrevisaoDao;
import ecar.dao.LocalItemDao;
import ecar.dao.RecursoDao;
import ecar.exception.ECARException;
import ecar.pojo.ExercicioExe;
import ecar.pojo.FonteRecursoFonr;
import ecar.pojo.ItemEstruturaIettPPA;
import ecar.pojo.LocalItemLit;
import ecar.pojo.RecursoRec;

/**
 *
 * @author 70744416353
 */
public class CalcularPrevisaoService {


	/**
	 * Log da classe
	 */
	private Logger logger = Logger.getLogger(this.getClass());
	
	
	/**
	 * Requisicao
	 */
	private HttpServletRequest request;	
	
	/**
	 * Constante que representa periodicidade 2008 - 2011
	 */
	private final Long CONSTANTE_PERIODO_EXERCICIO = Long.valueOf(2);		
	
	/**
	 * Listagem de exercicios do periodo
	 */
	private List<ExercicioExe> listaExercicios = new ArrayList<ExercicioExe>();
	
	private List<RecursoRec> listaRecursos = new ArrayList<RecursoRec>();
	
	private List<FonteRecursoFonr> listaFontes = new ArrayList<FonteRecursoFonr>();
	
        /**
         *
         * @param request
         */
        public CalcularPrevisaoService( HttpServletRequest request ) {
		this.request = request;
		loadExerciciosValidos();
		loadFontes();
		loadRecurso();
	}

        /**
         *
         */
        public CalcularPrevisaoService() {}
	
        /**
         *
         * @param paramRequest
         */
        public void setRequest( HttpServletRequest paramRequest  ){
		this.request = paramRequest;
	}
	
	private void loadRecurso(){
		
		try {
			
			final RecursoDao recursoDao = new RecursoDao(request);
			
			// carrega todos os recursos ativos
			listaRecursos = recursoDao.getAtivos();
		} catch (ECARException e) {
			logger.error("Nao foi possivel carregar Recursos ", e);
		} catch (Exception e) {
			logger.error("Nao foi possivel carregar Recursos", e);			
		}			
		
	}
	
	private void loadFontes(){
		
		
		try {
			final FonteRecursoDao fonteDao = new FonteRecursoDao(request);
			// carrega todas as fontes de recursos ativos
			listaFontes = fonteDao.getAtivos();	
		} catch (ECARException e) {
			logger.error("Nao foi possivel carregar fontes de recurso", e);
		} catch (Exception e) {
			logger.error("Nao foi possivel carregar fontes de recurso", e);			
		}		
		
	}
	
	
	/**
	 * Gera os totais valores da tabela para o item  na tabela do relatorio de PPA 
         * @param itemEstrut
         * @return array de valores da tabela PPA
	 * @throws ECARException
	 */
	public BigDecimal[] calcularPrevisaoSemIntegralizacao(final ItemEstruturaIettPPA itemEstrut ) throws ECARException{
		
		final ItemEstruturaPrevisaoDao prevDao = new ItemEstruturaPrevisaoDao(request);
		final int NIVEL_PROGRAMA = 2;
		
		// #####################
		//  Para cada Fonte de Recurso
		//  	Para cada Exercicio da Fonte de Recurso
		//			Pra cada Recurso Ativo
		//				Recupera valor Aprovado
		// #####################		
		ArrayList<BigDecimal> previsao = new ArrayList<BigDecimal>();

		
		
		
		// ateh 2
		for (Iterator iterFonte = listaFontes.iterator(); iterFonte.hasNext();) {
			FonteRecursoFonr elementoFonte = (FonteRecursoFonr) iterFonte.next();
			// ateh 4
			for (Iterator iterExer = listaExercicios.iterator(); iterExer.hasNext();) {
				ExercicioExe elementoExerc = (ExercicioExe) iterExer.next();
				// ateh 3
				for (Iterator iter = listaRecursos.iterator(); iter.hasNext();) {
					RecursoRec elementoRec = (RecursoRec) iter.next();
					
					BigDecimal previsaoItem = null;

					if ( itemEstrut.getNivelIett().equals(( Integer.valueOf( Integer.valueOf(NIVEL_PROGRAMA) ) ) ) )  { 
						previsaoItem =  prevDao.previsaoSomaItensFilhosSemIntegralizacao(itemEstrut.getCodIett(), elementoFonte.getCodFonr(), elementoExerc.getCodExe(), elementoRec.getCodRec()) ;	
					}
					else{
						previsaoItem =  prevDao.previsaoItemSemIntegralizacao( itemEstrut.getCodIett(), elementoFonte.getCodFonr(), elementoExerc.getCodExe(), elementoRec.getCodRec()) ;						
					}

					previsao.add(previsaoItem);

				}
				
				
			}
		}
		return previsao.toArray(new BigDecimal[previsao.size()]);


	}
	
	
	/**
	 * Gera os totais valores da tabela para o item  na tabela do relatorio de PPA 
         * @param itemEstrut
	 * @return array de valores da tabela PPA
	 * @throws ECARException
	 */
	public BigDecimal[] calcularPrevisao(final ItemEstruturaIettPPA itemEstrut ) throws ECARException{
		
		final ItemEstruturaPrevisaoDao prevDao = new ItemEstruturaPrevisaoDao(request);
		final int NIVEL_PROGRAMA = 2;
		
		// #####################
		//  Para cada Fonte de Recurso
		//  	Para cada Exercicio da Fonte de Recurso
		//			Pra cada Recurso Ativo
		//				Recupera valor Aprovado
		// #####################		
		ArrayList<BigDecimal> previsao = new ArrayList<BigDecimal>();

		
		
		
		// ateh 2
		for (Iterator iterFonte = listaFontes.iterator(); iterFonte.hasNext();) {
			FonteRecursoFonr elementoFonte = (FonteRecursoFonr) iterFonte.next();
			// ateh 4
			for (Iterator iterExer = listaExercicios.iterator(); iterExer.hasNext();) {
				ExercicioExe elementoExerc = (ExercicioExe) iterExer.next();
				// ateh 3
				for (Iterator iter = listaRecursos.iterator(); iter.hasNext();) {
					RecursoRec elementoRec = (RecursoRec) iter.next();
					
					BigDecimal previsaoItem = null;

					if ( itemEstrut.getNivelIett().equals(( Integer.valueOf( Integer.valueOf(NIVEL_PROGRAMA) ) ) ) )  { 
						previsaoItem =  prevDao.previsaoSomaItensFilhos(itemEstrut.getCodIett(), elementoFonte.getCodFonr(), elementoExerc.getCodExe(), elementoRec.getCodRec()) ;	
					}
					else{
						previsaoItem =  prevDao.previsaoItem( itemEstrut.getCodIett(), elementoFonte.getCodFonr(), elementoExerc.getCodExe(), elementoRec.getCodRec()) ;						
					}

					previsao.add(previsaoItem);

				}
				
				
			}
		}
		return previsao.toArray(new BigDecimal[previsao.size()]);


	}


	
	/**
	 * Retorna previsto por local - Somatorio de todos os exercicios
	 * @param codIettir Item Estrutura Indicador Resultado
	 * @param codLitl Local Item
	 * @return qtde prevista
	 */
	public Double calcularPrevisaoByLocal(  Long codIettir, Long codLitl ) {
		
		final ItemEstrtIndResultLocalIettirlDao dao = new ItemEstrtIndResultLocalIettirlDao(request);
		final LocalItemDao localDao = new LocalItemDao(request);
		final Long LOCAL_ITEM_ESTADO = 407L;

		ArrayList<Long> listaExercicioPK = new ArrayList<Long>();		
		Double qtde = new Double(0D);	
		try {
			LocalItemLit localChave = (LocalItemLit)localDao.buscar( LocalItemLit.class ,  codLitl );
			
			for (Iterator iter = listaExercicios.iterator(); iter.hasNext();) {
				ExercicioExe exe = (ExercicioExe) iter.next();
				listaExercicioPK.add( exe.getCodExe() );
			}
			
			if ( codLitl.longValue() != LOCAL_ITEM_ESTADO ){
				Set locais = localChave.getLocalItemHierarquiaLithsByCodLitPai();
				
				double qtdeTmp = 0D;			
				
	
				for (Iterator iter = locais.iterator(); iter.hasNext();) {
					LocalItemLit loc = (LocalItemLit) iter.next();
	
	
					Double result = dao.getSomaQtdePrevistaByLocal( codIettir, listaExercicioPK, loc.getCodLit() );
					qtdeTmp = qtdeTmp + result.doubleValue();
				
				}
				qtde = Double.valueOf(qtdeTmp);
			}
			
			qtde = qtde + dao.getSomaQtdePrevistaByLocal( codIettir, listaExercicioPK, codLitl );

			
		} catch (Exception e) {
			e.printStackTrace(System.out);
			logger.error(e);
			return null;
		}

		return qtde;
		
	}
	
	
	/**
	 * Carrega exercicios do periodo de 2008 - 2011
	 *
	 */
	public void loadExerciciosValidos(){
		
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
	
	
	
}
