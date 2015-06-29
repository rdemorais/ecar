package ecar.servlet.relatorio.PPA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import ecar.dao.AcompRealFisicoDao;
import ecar.dao.ItemEstrtIndResulIettrDao;
import ecar.dao.ItemEstrtIndResultLocalIettirlDao;
import ecar.exception.ECARException;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstruturaIettPPA;
import ecar.servlet.relatorio.PPA.bean.ProdutoBean;
import ecar.servlet.relatorio.PPA.bean.comparator.LinhaAcaoComparatorSigla;

/**
 *
 * @author 70744416353
 */
public class CarregaItensCompleto extends CarregaItens implements RelatorioParametro{


	/**
	 * Log da classe
	 */
	private Logger logger = Logger.getLogger(this.getClass());
	
	private Boolean validarCriterio = Boolean.FALSE;
	
	private final Long LOCAL_ITEM_CENTRO_EXPANDIDO = 422L;
	private final Long LOCAL_ITEM_LESTE = 418L;
	private final Long LOCAL_ITEM_NORTE = 420L;
	private final Long LOCAL_ITEM_NOROESTE = 419L;
	private final Long LOCAL_ITEM_OESTE = 421L;
	private final Long LOCAL_ITEM_SUDOESTE = 423L;
	private final Long LOCAL_ITEM_ESTADO = 407L;
	
	
	private CalcularPrevisaoService calcularPrevisaoService = new CalcularPrevisaoService();
	
	private ItemEstrtIndResulIettrDao indicadorMetaFisicaDao = null;
	private CarregaItensCompleto(){}
	
	
        /**
         *
         * @param paramRequest
         * @param paramValidarCriterio
         */
        public CarregaItensCompleto( HttpServletRequest paramRequest, Boolean paramValidarCriterio){
		super(paramRequest);
		this.validarCriterio = paramValidarCriterio;
	}
	
	public ArrayList<ItemEstruturaIettPPA> execute(ArrayList<String> programa, Long orgao, ArrayList<String> listaCriteriosCom, ArrayList<String> listaCriteriosSem) throws ECARException{

		try{
			
			final LinhaAcaoComparatorSigla comparadorSigla = new LinhaAcaoComparatorSigla();			
			this.getItensLinhaAcao();
			
			if ( validarCriterio ){
				ArrayList<ItemEstruturaIettPPA> list = new ArrayList<ItemEstruturaIettPPA>(itemDao.filtrarItensPorCriterio(itensLinhaAcao, listaCriteriosCom, listaCriteriosSem));
				Collections.sort(list, comparadorSigla);
				return list;
			}else{	
				Collections.sort(itensLinhaAcao, comparadorSigla);
				return itensLinhaAcao;
			}
 	
		}catch (Exception e) {
			e.printStackTrace(System.out);
			logger.error(e);
			throw new ECARException("Nao foi possivel filtrar itens por criterio na opcao completo");
		}		

		
	}

        /**
         *
         * @param paramRequest
         */
        public void initPrevisao(HttpServletRequest paramRequest ){
		calcularPrevisaoService.setRequest(paramRequest);
		calcularPrevisaoService.loadExerciciosValidos();
	}
	
        /**
         *
         * @param produtos
         * @return
         */
        public ArrayList<ProdutoBean> generateProdutoPPA(Set produtos){

		ArrayList<ProdutoBean> retorno = new ArrayList<ProdutoBean>();
		ProdutoBean bean = null;
		final ItemEstrtIndResultLocalIettirlDao indResultDao = new ItemEstrtIndResultLocalIettirlDao(request);
		final AcompRealFisicoDao acompDao = new AcompRealFisicoDao(request);
		indicadorMetaFisicaDao = new ItemEstrtIndResulIettrDao(request);

		// percorre produtos
		for (Iterator iter = produtos.iterator(); iter.hasNext();) {
			ItemEstruturaIettPPA prod = (ItemEstruturaIettPPA) iter.next();
			
			bean = new ProdutoBean();
			
			if( prod.getIndAtivoIett()!=null && "S".equalsIgnoreCase( prod.getIndAtivoIett() ) ){

/*				if (  "2380".equalsIgnoreCase(prod.getItemEstruturaIett().getSiglaIett()) ){
					logger.info(  "Produto: " + prod.getCodIett() );					
				}
*/
				
				// recebe indicadores do produto
				ArrayList<ItemEstrtIndResulIettr> indicadores = indicadorMetaFisicaDao.getIndicadoresMetaFisicaPPA( prod.getCodIett() );
				
				bean.setDescricao( prod.getNomeIett() );
				
				if ( indicadores !=null && (!indicadores.isEmpty()) ){
					
					// percorre indicadores
					for (Iterator iterator = indicadores.iterator(); iterator
							.hasNext();) {
						ItemEstrtIndResulIettr ind = (ItemEstrtIndResulIettr) iterator.next();
						
						if ( ind.getIndAtivoIettr()!=null &&  "S".equalsIgnoreCase(ind.getIndAtivoIettr())) {
							
							bean.setProduto( ind.getNomeIettir() );
							if ( ind.getIndAcumulavelIettr()!=null ){
								if ( "N".equalsIgnoreCase( ind.getIndAcumulavelIettr() ) ){
									
									List<Double> qtdePrevista = indResultDao.getListQtdePrevistaByLocal(ind.getCodIettir(),LOCAL_ITEM_CENTRO_EXPANDIDO);
									Double qtdeCentroExpandido = acompDao.getSomaValoresArfs(ind,  qtdePrevista );
									bean.setCentroExpandido( qtdeCentroExpandido );
									
									List<Double> qtdeLesteList = indResultDao.getListQtdePrevistaByLocal(ind.getCodIettir(),LOCAL_ITEM_LESTE);
									Double qtdeLeste = acompDao.getSomaValoresArfs(ind,  qtdeLesteList );
									bean.setLeste( qtdeLeste );
									
									List<Double> qtdeNorteList = indResultDao.getListQtdePrevistaByLocal(ind.getCodIettir(),LOCAL_ITEM_NORTE);
									Double qtdeNorte = acompDao.getSomaValoresArfs(ind,  qtdeNorteList );
									bean.setNorte( qtdeNorte );
	
									List<Double> qtdeNoroesteList = indResultDao.getListQtdePrevistaByLocal(ind.getCodIettir(),LOCAL_ITEM_NOROESTE);
									Double qtdeNoroeste = acompDao.getSomaValoresArfs(ind,  qtdeNoroesteList );
									bean.setNoroeste( qtdeNoroeste );
	
									List<Double> setOesteList = indResultDao.getListQtdePrevistaByLocal(ind.getCodIettir(),LOCAL_ITEM_OESTE);
									Double qtdeOeste = acompDao.getSomaValoresArfs(ind,  setOesteList );
									bean.setOeste( qtdeOeste );								
									
									List<Double> setSudoesteList = indResultDao.getListQtdePrevistaByLocal(ind.getCodIettir(),LOCAL_ITEM_SUDOESTE);
									Double qtdeSudoeste = acompDao.getSomaValoresArfs(ind,  setSudoesteList );
									bean.setSudoeste( qtdeSudoeste );									
	
									List<Double> qtdeEstadoList = indResultDao.getListQtdePrevistaByLocal(ind.getCodIettir(),LOCAL_ITEM_ESTADO);
									Double qtdeEstado = acompDao.getSomaValoresArfs(ind,  qtdeEstadoList );
									bean.setEstado( qtdeEstado );									
									
									Double qtdeTotal = qtdeCentroExpandido + qtdeLeste + qtdeNorte + qtdeNoroeste + qtdeOeste + qtdeSudoeste + qtdeEstado; 
									bean.setTotal( qtdeTotal );								
									
								}else {
									
									Double qtdeCentroExpandido = calcularPrevisaoService.calcularPrevisaoByLocal( ind.getCodIettir() , LOCAL_ITEM_CENTRO_EXPANDIDO );
									bean.setCentroExpandido( qtdeCentroExpandido );
		
									Double qtdeLeste = calcularPrevisaoService.calcularPrevisaoByLocal( ind.getCodIettir() , LOCAL_ITEM_LESTE );
									bean.setLeste( qtdeLeste );
									
									Double qtdeNorte = calcularPrevisaoService.calcularPrevisaoByLocal( ind.getCodIettir() , LOCAL_ITEM_NORTE );
									bean.setNorte( qtdeNorte );
									
									Double qtdeNoroeste = calcularPrevisaoService.calcularPrevisaoByLocal( ind.getCodIettir() , LOCAL_ITEM_NOROESTE );
									bean.setNoroeste( qtdeNoroeste );
									
									Double qtdeOeste = calcularPrevisaoService.calcularPrevisaoByLocal( ind.getCodIettir() , LOCAL_ITEM_OESTE );
									bean.setOeste( qtdeOeste );
		
									Double qtdeSudoeste = calcularPrevisaoService.calcularPrevisaoByLocal( ind.getCodIettir() , LOCAL_ITEM_SUDOESTE );
									bean.setSudoeste( qtdeSudoeste );
		
									Double qtdeEstado = calcularPrevisaoService.calcularPrevisaoByLocal( ind.getCodIettir() , LOCAL_ITEM_ESTADO );
									bean.setEstado( qtdeEstado );
		
									Double qtdeTotal = qtdeCentroExpandido + qtdeLeste + qtdeNorte + qtdeNoroeste + qtdeOeste + qtdeSudoeste + qtdeEstado; 
									bean.setTotal( qtdeTotal );
																	
								}
							}else{

							}
							
							bean.setUnidMedida(  ind.getUnidMedidaIettr() );
																
						} // indicador ativo
						
					} // percorre indicadores
					
				}else{ 					// if indicadores != null
					bean.setProduto( "" );
					bean.setUnidMedida(  "" );
					bean.setTotal(0D);
					bean.setEstado(0D);
					bean.setLeste(0D);
					bean.setOeste(0D);
					bean.setSudoeste(0D);
					bean.setNoroeste(0D);
					bean.setLeste(0D);
					bean.setCentroExpandido(0D);

				}

				retorno.add(bean);				
				
			} // if produto ativo 
			
		} // percorre produtos
		
		

		
		return retorno;
	}
	
	
        /**
         *
         * @return
         */
        public TipoPesquisaRelatorio getTipo() {
		return validarCriterio?TipoPesquisaRelatorio.TIPO_2:TipoPesquisaRelatorio.TIPO_1;
	}
	
}
