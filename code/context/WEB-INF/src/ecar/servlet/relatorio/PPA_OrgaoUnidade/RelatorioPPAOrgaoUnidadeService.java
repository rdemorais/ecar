 package ecar.servlet.relatorio.PPA_OrgaoUnidade;

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
import ecar.dao.ItemEstruturaPrevisaoDao;
import ecar.dao.OrgaoDao;
import ecar.dao.PoderDao;
import ecar.dao.UnidadeOrcamentariaDao;
import ecar.exception.ECARException;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.PoderPod;
import ecar.pojo.UnidadeOrcamentariaUO;
import ecar.servlet.relatorio.PPA_Orgao.OrgaoComparatorNome;
import ecar.servlet.relatorio.PPA_Orgao.PPA_OrgaoBean;
import ecar.servlet.relatorio.PPA_Util.CalcularTotalVisitor;

/**
 * Classe de acoplamento do relatorio PPA por Órgão / Unidade com o sistema E-car
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
public class RelatorioPPAOrgaoUnidadeService {

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
	private ArrayList<PPA_OrgaoBean> dados;
	
	/**
	 * Calcula os totais na tabela de resumo do relatorio PPA
	 */
	private final CalcularTotalVisitor calcularTotal = new CalcularTotalVisitor();
	
	
	// indicador de campo
	private final int CONSTANTE_PODER = 1;
	private final int CONSTANTE_ORGAO = 2;
	private final int CONSTANTE_UNIDADE = 3;
	
	//  MANTIS # 0012100
	private final Long CONSTANTE_MINISTERIO_PUBLICO = Long.valueOf( 3L );
	// 103 -
	private final Long ORGAO_ADMIN_SEPL = Long.valueOf( 103L ); 
	private final Long ORGAO_ADMIN_SEFA = Long.valueOf( 104L );
	private final Long CONSTANTE_PODER_LEGISLATIVO = Long.valueOf( 1L );
	
	
	// constante de base de dados - Status ativo / inativo
	private final Character CONSTANTE_IND_ATIVO = new Character('S');
	
	// constante de base de dados - Periodo de 2008 - 2011
	private final Long CONSTANTE_PERIODO_EXERCICIO = Long.valueOf(2);

	/**
	 * Listagem de exercicios do periodo
	 */
	private List<ExercicioExe> listaExercicios = new ArrayList<ExercicioExe>();
	
	private BigDecimal totalGeral1 = new BigDecimal(0);
	private BigDecimal totalGeral2 = new BigDecimal(0);
	private BigDecimal totalGeral3 = new BigDecimal(0);
	private BigDecimal totalGeral4 = new BigDecimal(0);		
	
	/*
	 * Impossibilita instanciar objeto de classe externa
	 */
	private RelatorioPPAOrgaoUnidadeService(){
		dados = new ArrayList<PPA_OrgaoBean>();
	}
	
	/**
	 * Retorna instancia da classe RelatorioPPAProgramaService com parametro de entrada request
	 * @param paramRequest
	 * @return
	 */
	public static RelatorioPPAOrgaoUnidadeService getInstance( HttpServletRequest paramRequest ){
		RelatorioPPAOrgaoUnidadeService objeto = new RelatorioPPAOrgaoUnidadeService();
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
	 * Retorna itens para geracao do relatorio PPA
	 * 
	 * 	- carrega exercicios de periodicidade 2008-2011
	 *  - Carrega poderes de indAtivo <S> e periodicidade 2008-2001
	 *  	- para cada poder carrega orgaos
	 *  		- para cada orgao carrega unidade orcamentaria
	 *  			- para cada Unidade Orcamentaria calcula previsao e totaliza bean.
	 * 
	 */
	private void getDados(){
		
		final PoderDao poderDao = new PoderDao(request);
		final OrgaoDao orgaoDao = new OrgaoDao(request);
		final UnidadeOrcamentariaDao unidadeDao = new UnidadeOrcamentariaDao(request);

		try{
			ArrayList<PoderPod> listPoder = poderDao.getPoderesByPeriodicidade(CONSTANTE_PERIODO_EXERCICIO, CONSTANTE_IND_ATIVO);
			
			// carrega exercicios do periodo de 2008 / 2011
			loadExerciciosValidos();
			
			for (Iterator iter = listPoder.iterator(); iter.hasNext();) {
				PoderPod poder = (PoderPod) iter.next();
				
				if (poder.getIndAtivoPod().equalsIgnoreCase( CONSTANTE_IND_ATIVO.toString() ) ){
					
					PPA_OrgaoBean orgaoBeanPod = new PPA_OrgaoBean();
					orgaoBeanPod.setNome( poder.getNomePod().toUpperCase() );
					orgaoBeanPod.setFlag(CONSTANTE_PODER); // indica que eh campo de poder
					
					ArrayList<OrgaoOrg> listOrgao = orgaoDao.getOrgaoByPeriodicidade(CONSTANTE_PERIODO_EXERCICIO, poder.getCodPod(), CONSTANTE_IND_ATIVO);  
					
					listOrgao = fixarOrgaos( reordenarOrgao( listOrgao ) );
					
					//if ( listOrgao!=null && listOrgao.size()>0 ){
						dados.add(orgaoBeanPod);	
					//}
					
					BigDecimal totalOrgaoAno1 = new BigDecimal(0);
					BigDecimal totalOrgaoAno2 = new BigDecimal(0);
					BigDecimal totalOrgaoAno3 = new BigDecimal(0);
					BigDecimal totalOrgaoAno4 = new BigDecimal(0);
					for (Iterator iterator = listOrgao.iterator(); iterator
							.hasNext();) {
					
						OrgaoOrg orgao = (OrgaoOrg) iterator.next();
					
						if ( orgao.getIndAtivoOrg().equalsIgnoreCase(CONSTANTE_IND_ATIVO.toString() ) ){
							
							PPA_OrgaoBean orgaoBeanOrg = new PPA_OrgaoBean();
							orgaoBeanOrg.setNome( orgao.getDescricaoOrg() );
							orgaoBeanOrg.setFlag(CONSTANTE_ORGAO); // indica que eh campo de orgao
							
							ArrayList<UnidadeOrcamentariaUO> unidades =  unidadeDao.getUnidadesByPeriodicidade(CONSTANTE_PERIODO_EXERCICIO, orgao.getCodOrg(), CONSTANTE_IND_ATIVO);
							
							if ( unidades!=null && unidades.size()>0 ){
								int contIndireta = 0;
								int contDireta = 0;
								for (Iterator itUnid = unidades.iterator(); itUnid.hasNext();) {
									UnidadeOrcamentariaUO elemento = (UnidadeOrcamentariaUO) itUnid.next();
									if ( elemento.getIndTipoAdministracaoUo().equalsIgnoreCase("I")  ){
											contIndireta++;
									}else{
										contDireta++;
									}
								}
								
								if (contIndireta > 0 || contDireta>0){
									dados.add(orgaoBeanOrg);	
								}
	
							}							
							
							BigDecimal totalUnidadeAno1 = new BigDecimal(0);
							BigDecimal totalUnidadeAno2 = new BigDecimal(0);
							BigDecimal totalUnidadeAno3 = new BigDecimal(0);
							BigDecimal totalUnidadeAno4 = new BigDecimal(0);
							

							if ( ORGAO_ADMIN_SEPL.longValue() != orgao.getCodOrg().longValue() &&
									ORGAO_ADMIN_SEFA.longValue() != orgao.getCodOrg().longValue() ){


								// agrupamento de tipo administracao Direta
								PPA_OrgaoBean adminDiretaBean = agruparDiretas(unidades);
								if ( adminDiretaBean != null ){
									adminDiretaBean.setNome("Administração Direta");
									adminDiretaBean.setFlag(CONSTANTE_UNIDADE);
									adminDiretaBean.setTpAdminDireta(Boolean.FALSE);
									dados.add(adminDiretaBean);
	
									totalUnidadeAno1 = totalUnidadeAno1.add( adminDiretaBean.getValor1() );
									totalUnidadeAno2 = totalUnidadeAno2.add( adminDiretaBean.getValor2() );
									totalUnidadeAno3 = totalUnidadeAno3.add( adminDiretaBean.getValor3() );
									totalUnidadeAno4 = totalUnidadeAno4.add( adminDiretaBean.getValor4() );
									
								}
								// agrupamento de tipo administracao Direta							
							
							}
							
							for (Iterator itUnidade = unidades.iterator(); itUnidade
									.hasNext();) {
								UnidadeOrcamentariaUO unidade = (UnidadeOrcamentariaUO) itUnidade.next();

								if ( unidade.getIndAtivoUo().equalsIgnoreCase( CONSTANTE_IND_ATIVO.toString() ) ){
									
									if ( unidade.getIndTipoAdministracaoUo()!=null && unidade.getIndTipoAdministracaoUo().equalsIgnoreCase("I") 
											|| unidade.getOrgaoOrg().getCodOrg().equals( ORGAO_ADMIN_SEPL)  
											|| unidade.getOrgaoOrg().getCodOrg().equals( ORGAO_ADMIN_SEFA)  
									){
										
										PPA_OrgaoBean orgaoBeanUnidade = new PPA_OrgaoBean();
										orgaoBeanUnidade.setNome( unidade.getDescricaoUo() );
										orgaoBeanUnidade.setFlag( CONSTANTE_UNIDADE ); // indica que eh campo de unidade
										orgaoBeanUnidade.setTpAdminDireta(false);
										orgaoBeanOrg.setTpAdminDireta(false);
										orgaoBeanPod.setTpAdminDireta(false);
										
										Set itens = unidade.getItemEstruturaIettsByCodUo();
				
										for (Iterator itItem = itens.iterator(); itItem.hasNext();) {
											ItemEstruturaIett itemOrgao = (ItemEstruturaIett) itItem.next();
											
											if ( CONSTANTE_IND_ATIVO.toString().equalsIgnoreCase(  itemOrgao.getIndAtivoIett() )  ){
												if ( itemOrgao.getNivelIett().equals( Integer.valueOf(3) )){ // itens de nivel de acao
													
													BigDecimal[] previsto = getPrevisao(itemOrgao); 
													totalizarBean( orgaoBeanUnidade, previsto );
												}
											
											}
											
										}
										totalUnidadeAno1 = totalUnidadeAno1.add( orgaoBeanUnidade.getValor1() );
										totalUnidadeAno2 = totalUnidadeAno2.add( orgaoBeanUnidade.getValor2() );
										totalUnidadeAno3 = totalUnidadeAno3.add( orgaoBeanUnidade.getValor3() );
										totalUnidadeAno4 = totalUnidadeAno4.add( orgaoBeanUnidade.getValor4() );
										
										if ( (!unidade.getOrgaoOrg().getCodOrg().equals( ORGAO_ADMIN_SEPL)) && (!unidade.getOrgaoOrg().getCodOrg().equals( ORGAO_ADMIN_SEFA) ) ){
											dados.add(orgaoBeanUnidade);	
										}
										

										
									}	
									
									
									
								}
								
							}
							orgaoBeanOrg.setValor1( totalUnidadeAno1 );
							orgaoBeanOrg.setValor2( totalUnidadeAno2 );
							orgaoBeanOrg.setValor3( totalUnidadeAno3 );
							orgaoBeanOrg.setValor4( totalUnidadeAno4 );
							calcularTotal.visit(orgaoBeanOrg);
							
							
							
							totalOrgaoAno1 = totalOrgaoAno1.add( orgaoBeanOrg.getValor1() );
							totalOrgaoAno2 = totalOrgaoAno2.add( orgaoBeanOrg.getValor2() );
							totalOrgaoAno3 = totalOrgaoAno3.add( orgaoBeanOrg.getValor3() );
							totalOrgaoAno4 = totalOrgaoAno4.add( orgaoBeanOrg.getValor4() );						
							
						}
						
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
				

				
			}
			
			//removeDiretas();
			
		}catch (Exception e) {
			logger.error("Nao foi possivel carregar dados do relatorio", e);
			listError();
		}
		
	}
		

	/**
	 * Agrupa todas as unidades Orçamentarias do tipo Administração Direta.
	 * @param unidades orçamentarias
	 * @return Pojo com os valores previstos somados.
	 * @throws ECARException
	 */
	private PPA_OrgaoBean agruparDiretas(ArrayList unidades) throws ECARException{
		
		ArrayList<UnidadeOrcamentariaUO> somenteDiretas = new ArrayList<UnidadeOrcamentariaUO>();
		PPA_OrgaoBean adminDiretaTmp = new PPA_OrgaoBean();
		
		// filtra somente tipo administracao Direta
		for (Iterator iter = unidades.iterator(); iter.hasNext();) {
			UnidadeOrcamentariaUO unidade = (UnidadeOrcamentariaUO) iter.next();
		
			if ( unidade.getIndTipoAdministracaoUo().equals("D")){
				somenteDiretas.add(unidade);
			}
			
		}
		
		if ( somenteDiretas.isEmpty() ){
			return null;			
		}
		
		for (Iterator iter2 = somenteDiretas.iterator(); iter2.hasNext();) {
			UnidadeOrcamentariaUO unidTmp = (UnidadeOrcamentariaUO) iter2.next();
			
			Set itens = unidTmp.getItemEstruturaIettsByCodUo();
			
			for (Iterator itItem = itens.iterator(); itItem.hasNext();) {
				ItemEstruturaIett itemOrgao = (ItemEstruturaIett) itItem.next();
				
				if ( CONSTANTE_IND_ATIVO.toString().equalsIgnoreCase( itemOrgao.getIndAtivoIett()) ){
					if ( itemOrgao.getNivelIett().equals( Integer.valueOf(3) )){ // itens de nivel de acao
						
						BigDecimal[] previsto = getPrevisao(itemOrgao); 
						totalizarBean( adminDiretaTmp , previsto );
					}														
				
				}
				
			}		
			
		}
		
		return adminDiretaTmp;
	}
	
	
	/**
	 * Indexador da List de dados
	 *
	 */
	private void gerarContador(){
		
		int cont = 0;
		for (Iterator iter = dados.iterator(); iter.hasNext();) {
			PPA_OrgaoBean elemento = (PPA_OrgaoBean) iter.next();
			elemento.setIndice(cont);
			cont++;
		}
		
	}
	
	/**
	 * Soma valores de previsao e atribui ao bean PPA_OrgaoBean
	 * @param orgaoBean
	 * @param valores
	 */
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
			logger.error("Nao foi possivel totalizar bean: " + orgaoBean.getNome(),  e);
		}
		
		
	}
	
	
	/**
	 * Carrega valores de previsao - <valor aprovado> para cada exercicio
	 */
	private BigDecimal[] getPrevisao(final ItemEstruturaIett itemEstrut ) throws ECARException{
		
		final ExercicioDao exercicioDao = new ExercicioDao(request);
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
	
	/**
	 * Popula list com bean vazio
	 *
	 */
	private void listError(){
		dados = new ArrayList<PPA_OrgaoBean>();
		PPA_OrgaoBean novoBean = new PPA_OrgaoBean();
		novoBean.setNome("Vazio");
		dados.add(novoBean);
	}
	
}
