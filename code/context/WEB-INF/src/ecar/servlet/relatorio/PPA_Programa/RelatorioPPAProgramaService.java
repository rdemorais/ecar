package ecar.servlet.relatorio.PPA_Programa;

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
import ecar.servlet.relatorio.PPA_Util.CalcularTotalVisitor;

/**
 * Classe de acoplamento do relatorio PPA por Programa com o sistema E-car
 * 
 * <BR/>
 * Arvore de chamada de Metodos ::
 * 
 * RelatorioPPAServlet
 * 	generatePPA
 * 	  getItem
 * 		itemEstruturaDao.getItensPPA
 * 		generateBean
 * 			geraPrograma
 * 			geraPrevisao
 * 			CalcularTotal
 * <BR/>
 * @author Gabriel Solana
 * @since 07/2007
 */
public class RelatorioPPAProgramaService {

	/**
	 * Log da classe
	 */
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * Requisicao
	 */
	private HttpServletRequest request;
	

	private static final Long codEstruturaPrograma = Long.valueOf(22);
	
	/**
	 * Dados do relatorio
	 */
	private ArrayList<PPA_ProgramaBean> dados;
	
	/**
	 * Calcula os totais na tabela de resumo do relatorio PPA
	 */
	private final CalcularTotalVisitor calcularTotal = new CalcularTotalVisitor();
	
	final private String PARAM_DATA_INICIO = "2008";
	final private String PARAM_DATA_TERMINO = "2011";
	
	// constante de base de dados - Periodo de 2008 - 2011
	private final Long CONSTANTE_PERIODO_EXERCICIO = Long.valueOf(2);
	
	// constante de base de dados - Status ativo / inativo
	private final Character CONSTANTE_IND_ATIVO = new Character('S');	
	
	/**
	 * Listagem de exercicios do periodo
	 */
	private List<ExercicioExe> listaExercicios = new ArrayList<ExercicioExe>();
	
	
	/*
	 * Impossibilita instanciar objeto de classe externa
	 */
	private RelatorioPPAProgramaService(){
		dados = new ArrayList<PPA_ProgramaBean>();
	}
	
	/**
	 * Retorna instancia da classe RelatorioPPAProgramaService com parametro de entrada request
	 * @param paramRequest
	 * @return
	 */
	public static RelatorioPPAProgramaService getInstance( HttpServletRequest paramRequest ){
		RelatorioPPAProgramaService objeto = new RelatorioPPAProgramaService();
		objeto.request  = paramRequest;
		return objeto;
	}
	
	/**
	 * Recupera dados e dispara actions para geracao do relatorio
	 * @return Listagem
	 */
	public ArrayList<PPA_ProgramaBean> generatePPA(){
		getDados();
		gerarContador();
		return dados;
	}
	
	private void gerarContador(){
		
		int cont = 0;
		for (Iterator iter = dados.iterator(); iter.hasNext();) {
			PPA_ProgramaBean elemento = (PPA_ProgramaBean) iter.next();
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
	 * Reordena Colecao de elementos de acordo com objeto Comparator
	 * @param list listagem
	 * @return Set de objetos reordenados
	 */
	private Set reordenarPrograma(Collection list){
		TreeSet<ItemEstruturaIett> novalista = new TreeSet<ItemEstruturaIett>( new ProgramaComparatorNome() );
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			ItemEstruturaIett item = (ItemEstruturaIett) iter.next();
			
			if ( CONSTANTE_IND_ATIVO.toString().equalsIgnoreCase( item.getIndAtivoIett() ) ){
				novalista.add(item);	
			}
			
		}			
		return novalista;
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
		boolean vazio = true;
		try {
			
//			 carrega exercicios do periodo de 2008 / 2011
			loadExerciciosValidos();
			
			//itens = new ArrayList<ItemEstruturaIett>(itemEstruturaDao.getItensPPA( PARAM_DATA_INICIO, PARAM_DATA_TERMINO ) );
			itens = new ArrayList<ItemEstruturaIett>(itemEstruturaDao.getItensByEstrutura(codEstruturaPrograma));
			
			if(itens != null){
				for (Iterator<ItemEstruturaIett> iter = reordenarPrograma(itens).iterator(); iter.hasNext();) {
					ItemEstruturaIett iett = iter.next();
					vazio = false;					
					generateBean(iett);	

	    		}
	    	}			
			
			if (vazio){
				logger.info("Nao existe item de nivelprograma na estrutura no periodo estipulado!!!");
			}
			
			
		} catch (Exception e) {
			logger.error("Nao foi possivel carregar dados", e);
		}		
	}
	
	/**
	 * Dispara servicos para gerar os beans do relatorio PPA
	 * @param itemPrograma Item de programa referencial
	 * @throws Exception
	 */
	private void generateBean( ItemEstruturaIett itemPrograma ) throws Exception{
		PPA_ProgramaBean programaTmp = geraPrograma(itemPrograma);
		geraPrevisao(programaTmp, itemPrograma );
		calcularTotal.visit(programaTmp); // gera calculo dos totais
		dados.add(programaTmp);
	}
	
	/**
	 * Gera Bean de Valores Aprovados.
	 * @param programa POJO a ser populado
	 * @param itemPrograma Item de programa referencial
	 * @throws ECARException
	 */
	private void geraPrevisao( PPA_ProgramaBean programa, ItemEstruturaIett itemPrograma ) throws ECARException{

		BigDecimal[] previsoes = getPrevisao(itemPrograma);		
		if ( previsoes != null && previsoes.length == 4 ){
			programa.setValor1( previsoes[0] );
			programa.setValor2( previsoes[1] );
			programa.setValor3( previsoes[2] );
			programa.setValor4( previsoes[3] );			
		}
		
		
	}
	
	
	/**
	 * Adiciona um bean de Programa para a tabela de PPA
	 * @param itemPrograma
	 */
	private PPA_ProgramaBean geraPrograma(ItemEstruturaIett itemPrograma ) throws ECARException{
		
		PPA_ProgramaBean programa = new PPA_ProgramaBean();
		programa.setNome( itemPrograma.getNomeIett() );
		return programa;
		
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
			previsaoItem =  prevDao.previsaoItem( itemEstrut.getCodIett(), elementoExerc.getCodExe() ) ;
			previsao.add(previsaoItem);
		}
		return previsao.toArray(new BigDecimal[previsao.size()]);

	}
	
}
