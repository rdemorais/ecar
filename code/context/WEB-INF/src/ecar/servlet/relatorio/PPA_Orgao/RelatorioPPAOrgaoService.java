 package ecar.servlet.relatorio.PPA_Orgao;

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
import ecar.dao.OrgaoDao;
import ecar.dao.PoderDao;
import ecar.exception.ECARException;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.PoderPod;
import ecar.servlet.relatorio.PPA_Util.CalcularTotalVisitor;

/**
 * Classe de acoplamento do relatorio PPA por Órgão com o sistema E-car
 * 
 * <BR/>
 * Arvore de chamada de Metodos ::
 * 
 * RelatorioPPAServlet
 * 	generatePPA
 * 	  getDados
 * <BR/>
 * @author Gabriel Solana
 * @since 07/2007
 */
public class RelatorioPPAOrgaoService {

	/**
	 * Log da classe
	 */
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * Requisicao
	 */
	private HttpServletRequest request;
	
	// constante de base de dados - Periodo de 2008 - 2011
	private final Long CONSTANTE_PERIODO_EXERCICIO = Long.valueOf(2);

	// constante de base de dados - Status ativo / inativo
	private final Character CONSTANTE_IND_ATIVO = new Character('S');
	
	/**
	 * Dados do relatorio
	 */
	private ArrayList<PPA_OrgaoBean> dados;
	
	/**
	 * Calcula os totais na tabela de resumo do relatorio PPA
	 */
	private final CalcularTotalVisitor calcularTotal = new CalcularTotalVisitor();
	
	/**
	 * Listagem de exercicios do periodo
	 */
	private List<ExercicioExe> listaExercicios = new ArrayList<ExercicioExe>();
	
	private final int CONSTANTE_PODER = 1;
	private final int CONSTANTE_ORGAO = 2;
	
	//  MANTIS # 0012100
	private final Long CONSTANTE_MINISTERIO_PUBLICO = Long.valueOf( 3L );
	// 103 -
	private final Long ORGAO_ADMIN_SEPL = Long.valueOf( 103L ); 
	private final Long ORGAO_ADMIN_SEFA = Long.valueOf( 104L );
	private final Long CONSTANTE_PODER_LEGISLATIVO = Long.valueOf( 1L );
	
	private BigDecimal totalGeral1 = new BigDecimal(0);
	private BigDecimal totalGeral2 = new BigDecimal(0);
	private BigDecimal totalGeral3 = new BigDecimal(0);
	private BigDecimal totalGeral4 = new BigDecimal(0);		
	
	
	/*
	 * Impossibilita instanciar objeto de classe externa
	 */
	private RelatorioPPAOrgaoService(){
		dados = new ArrayList<PPA_OrgaoBean>();
	}
	
	/**
	 * Retorna instancia da classe RelatorioPPAProgramaService com parametro de entrada request
	 * @param paramRequest
	 * @return
	 */
	public static RelatorioPPAOrgaoService getInstance( HttpServletRequest paramRequest ){
		RelatorioPPAOrgaoService objeto = new RelatorioPPAOrgaoService();
		objeto.request  = paramRequest;
		return objeto;
	}
	
	/**
	 * Recupera dados e dispara actions para geracao do relatorio
	 * @return Listagem
	 */
	public ArrayList<PPA_OrgaoBean> generatePPA(){
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
	
	private ArrayList<OrgaoOrg> fixarOrgaos(Set<OrgaoOrg> list){
		
		OrgaoOrg org_SEPL = null;			
		OrgaoOrg org_SEFA = null;
		
		// 13
		final Long ORGAO_SECR_FAZENDA = Long.valueOf( 13L );
		
		// 1
		final Long ORGAO_SECR_PLAN = Long.valueOf( 1L );
		
		ArrayList<OrgaoOrg> novaLista = new ArrayList<OrgaoOrg>();
		
		// removendo
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			OrgaoOrg org = (OrgaoOrg) iter.next();
			
			if ( org.getCodOrg().equals( ORGAO_ADMIN_SEPL )){
				org_SEPL = org; 
				iter.remove();
			}
			
			if ( org.getCodOrg().equals( ORGAO_ADMIN_SEFA )){
				org_SEFA = org;
				iter.remove();
			}
			
			
			
		}
		
		for (Iterator iter2 = list.iterator(); iter2.hasNext();) {
			OrgaoOrg tmp = (OrgaoOrg) iter2.next();
			
			if ( tmp.getCodOrg().equals( ORGAO_SECR_FAZENDA )  ){
				novaLista.add(tmp);
				novaLista.add(org_SEFA);
			}

			if ( tmp.getCodOrg().equals( ORGAO_SECR_PLAN )  ){
				novaLista.add(tmp);
				novaLista.add(org_SEPL);
			}

			
			if ( tmp.getCodOrg().equals( ORGAO_SECR_FAZENDA )  ){
			}else if (  tmp.getCodOrg().equals( ORGAO_SECR_PLAN )  ){
			}else{
				novaLista.add(tmp);		
					
			}
			
		}
		
		return novaLista;
				
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
	
	private void gerarContador(){
		
		int cont = 0;
		for (Iterator iter = dados.iterator(); iter.hasNext();) {
			PPA_OrgaoBean elemento = (PPA_OrgaoBean) iter.next();
			elemento.setIndice(cont);
			cont++;
		}
		
	}
	
	/**
	 * Reordena Colecao de elementos de acordo com objeto Comparator
	 * @param list listagem
	 * @return Set de objetos reordenados
	 */
	private Set reordenarOrgao(Collection list){
		TreeSet<OrgaoOrg> novalista = new TreeSet<OrgaoOrg>( new OrgaoComparatorNome() );
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			OrgaoOrg org = (OrgaoOrg) iter.next();
			
			if ( CONSTANTE_IND_ATIVO.toString().equalsIgnoreCase( org.getIndAtivoOrg() ) ){
				novalista.add(org);	
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
		
		final PoderDao poderDao = new PoderDao(request);
		final ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		final ItemEstruturaPrevisaoDao prevDao = new ItemEstruturaPrevisaoDao(request);
		final OrgaoDao orgaoDao = new OrgaoDao(request);
		//final UnidadeOrcamentariaDao unidadeDao = new UnidadeOrcamentariaDao(request);
		
		try{
			ArrayList<PoderPod> listPoder = poderDao.getPoderesByPeriodicidade(CONSTANTE_PERIODO_EXERCICIO, CONSTANTE_IND_ATIVO);
			
			// 	carrega exercicios do periodo de 2008 / 2011
			loadExerciciosValidos();
			
			for (Iterator iter = listPoder.iterator(); iter.hasNext();) {
				PoderPod poder = (PoderPod) iter.next();
				
				PPA_OrgaoBean orgaoBeanPod = new PPA_OrgaoBean();
				orgaoBeanPod.setNome( poder.getNomePod().toUpperCase() );
				orgaoBeanPod.setFlag(CONSTANTE_PODER); // indica que eh campo de poder
				dados.add(orgaoBeanPod);
				
				ArrayList<OrgaoOrg> listOrgao = fixarOrgaos( reordenarOrgao(orgaoDao.getOrgaoByPeriodicidade(CONSTANTE_PERIODO_EXERCICIO, poder.getCodPod(), CONSTANTE_IND_ATIVO)));
				
				BigDecimal totalOrgaoAno1 = new BigDecimal(0);
				BigDecimal totalOrgaoAno2 = new BigDecimal(0);
				BigDecimal totalOrgaoAno3 = new BigDecimal(0);
				BigDecimal totalOrgaoAno4 = new BigDecimal(0);
				for (Iterator iterator = listOrgao.iterator(); iterator
						.hasNext();) {
					OrgaoOrg orgao = (OrgaoOrg) iterator.next();
				
					PPA_OrgaoBean orgaoBeanOrg = new PPA_OrgaoBean();
					orgaoBeanOrg.setNome( orgao.getDescricaoOrg() );
					orgaoBeanOrg.setFlag(CONSTANTE_ORGAO); // indica que eh campo de orgao

					Set itens = orgao.getItemEstruturaIettsByCodOrgaoResponsavel1Iett();

					for (Iterator itItem = itens.iterator(); itItem.hasNext();) {
						ItemEstruturaIett itemOrgao = (ItemEstruturaIett) itItem.next();

						if ( CONSTANTE_IND_ATIVO.toString().equalsIgnoreCase( itemOrgao.getIndAtivoIett() ) ){
						
							if ( itemOrgao.getNivelIett().equals( Integer.valueOf(3) )){ // itens de nivel de acao
								
								BigDecimal[] previsto = getPrevisao(itemOrgao); 
								totalizarBean( orgaoBeanOrg, previsto );
							}
						}
						
						
					}
					
					dados.add(orgaoBeanOrg);
					totalOrgaoAno1 = totalOrgaoAno1.add( orgaoBeanOrg.getValor1() );
					totalOrgaoAno2 = totalOrgaoAno2.add( orgaoBeanOrg.getValor2() );
					totalOrgaoAno3 = totalOrgaoAno3.add( orgaoBeanOrg.getValor3() );
					totalOrgaoAno4 = totalOrgaoAno4.add( orgaoBeanOrg.getValor4() );
					
					
				}
				orgaoBeanPod.setValor1( totalOrgaoAno1 );
				orgaoBeanPod.setValor2( totalOrgaoAno2 );
				orgaoBeanPod.setValor3( totalOrgaoAno3 );
				orgaoBeanPod.setValor4( totalOrgaoAno4 );
				calcularTotal.visit(orgaoBeanPod);
				
				totalGeral1 = totalGeral1.add( orgaoBeanPod.getValor1() );
				totalGeral2 = totalGeral2.add( orgaoBeanPod.getValor2() );
				totalGeral3 = totalGeral3.add( orgaoBeanPod.getValor3() );
				totalGeral4 = totalGeral4.add( orgaoBeanPod.getValor4() );					
				
				
			}
			
			
		}catch (Exception e) {
			logger.error("Nao foi possivel carregar dados do relatorio", e);
			listError();
		}
		
		
	}
	
	private void totalizarBean(PPA_OrgaoBean orgaoBean, BigDecimal[] valores){
		
		final BigDecimal ZERO = new BigDecimal(0);
		
		try {
			if( valores!=null && valores.length == 4 ){
				
				BigDecimal vlr1 = orgaoBean.getValor1();
				BigDecimal vlr2 = orgaoBean.getValor2();
				BigDecimal vlr3 = orgaoBean.getValor3();
				BigDecimal vlr4 = orgaoBean.getValor4();
				
				orgaoBean.setValor1( vlr1.add( valores[0]==null?ZERO:valores[0] ) );
				orgaoBean.setValor2( vlr2.add( valores[1]==null?ZERO:valores[1] ) );
				orgaoBean.setValor3( vlr3.add( valores[2]==null?ZERO:valores[2] ) );
				orgaoBean.setValor4( vlr4.add( valores[3]==null?ZERO:valores[3] ) );
				calcularTotal.visit(orgaoBean);
				
			}else{
				orgaoBean.setValor1( ZERO );
				orgaoBean.setValor2( ZERO );
				orgaoBean.setValor3( ZERO );
				orgaoBean.setValor4( ZERO );
				orgaoBean.setTotal( ZERO );
			}
		} catch (Exception e) {
			logger.error("Nao foi possivel totalizar bean: " + orgaoBean.getNome() + " Valores: " + valores ,  e);
		}
		
		
	}
	
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
	
	
	private void listError(){
		dados = new ArrayList<PPA_OrgaoBean>();
		PPA_OrgaoBean novoBean = new PPA_OrgaoBean();
		novoBean.setNome("Vazio");
		dados.add(novoBean);
	}
	
}
