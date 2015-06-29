package ecar.servico;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.lang.StringUtils;

import comum.database.Dao;
import comum.util.Data;
import comum.util.EtiquetaUtils;

import ecar.bean.FiltroPesquisaMonitoramento;
import ecar.dao.EtiquetaDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.OrgaoDao;
import ecar.dao.SiteDao;
import ecar.enumerator.TipoRelatorioAcompanhamento;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.acompanhamentoEstrategico.Estrategia;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.pojo.acompanhamentoEstrategico.Resultado;
import ecar.pojo.acompanhamentoEstrategico.ResultadoComparator;
import ecar.servlet.relatorio.dto.SecretariaDTO;
import ecar.util.GraficoUtils;
import ecar.util.jasper.JasperService;

public class RelatorioAcompanhamentoService {

	public List<ItemEstruturaIett> listarOpcoesPickListObjetivosEstrategicos() {
		List<ItemEstruturaIett> listaOpcoes = new ArrayList<ItemEstruturaIett>();
		StringBuilder hql = new StringBuilder();
		hql.append("select new ItemEstruturaIett(item.codIett, item.siglaIett) ");
		hql.append("from ItemEstruturaIett item where item.itemEstruturaIett is null ");
		hql.append("and item.nivelIett = 1 and item.indAtivoIett = 'S' order by item.siglaIett ");

		Dao<ItemEstruturaIett> iettDao = new Dao<ItemEstruturaIett>();

		listaOpcoes = (List<ItemEstruturaIett>) iettDao.listarPorHQL(hql.toString());

		return listaOpcoes;
	}
	
	public List<SecretariaDTO> listarSecretarias(){
//		Dao<OrgaoOrg> orgaoDao = new Dao<OrgaoOrg>();
//		StringBuilder hql = new StringBuilder();
//		hql.append("select new OrgaoOrg(codOrg, siglaOrg) from OrgaoOrg where ");
//		hql.append("(codOrg = 17) or ");
//		hql.append("(codOrg = 18) or ");
//		hql.append("(codOrg = 19) or ");
//		hql.append("(codOrg = 20) or ");
//		hql.append("(codOrg = 21) or ");
//		hql.append("upper(descricaoOrg) like upper('%secretaria%') order by siglaOrg ");
//		List<OrgaoOrg> lista = orgaoDao.listarPorHQL(hql.toString());
		List<SecretariaDTO> listaSecretarias = preencherListaSecretarias(/*lista*/);
		
		return listaSecretarias;
		
	}
	
	private List<SecretariaDTO> preencherListaSecretarias(/*List<OrgaoOrg> lista*/){
		OrgaoDao orgaoDao = new OrgaoDao(null);
//		StringBuilder hql = new StringBuilder();
//		hql.append("select new OrgaoOrg(codOrg, siglaOrg) from OrgaoOrg ");
//		List<OrgaoOrg> orgaosCadastrados = orgaoDao.listarPorHQL(hql.toString());
		List<SecretariaDTO> listaSecretarias = new ArrayList<SecretariaDTO>();
		SecretariaDTO secretariaDTO = null;
		
		EtiquetaDao etiquetaDao = new EtiquetaDao();
		
		List<ecar.pojo.acompanhamentoEstrategico.Etiqueta> listaEtiqueta = etiquetaDao.listarEtiqueta(null, "S", null);
		
		
		for(ecar.pojo.acompanhamentoEstrategico.Etiqueta etiqueta:listaEtiqueta){
			
			if(etiqueta.getCategoria().getCodigo()==9L||etiqueta.getCategoria().getCodigo()==10L){
				secretariaDTO = new SecretariaDTO();
				secretariaDTO.setSiglaSecretaria(etiqueta.getNome());
				
				List<Long> listaIdsSecretarias = orgaoDao.listarIdsSecretarias(etiqueta.getNome());
				
				secretariaDTO.setCodigosSecretarias(listaIdsSecretarias);
				listaSecretarias.add(secretariaDTO);
				
			}
			
		}
		
		
//		for(OrgaoOrg secretaria : lista){
//			secretariaDTO = new SecretariaDTO();
//			secretariaDTO.setSiglaSecretaria(secretaria.getSiglaOrg());
//			for(OrgaoOrg orgaoOrg : orgaosCadastrados){
//				if(secretaria.getCodOrg() != orgaoOrg.getCodOrg()){
//					if(secretaria.getSiglaOrg().contains("/")){
//						String [] siglaSecretaria = secretaria.getSiglaOrg().split("/");
//						for(String sigla : siglaSecretaria){
//							if(StringUtils.contains(orgaoOrg.getSiglaOrg(), sigla)){
//								secretariaDTO.getCodigosSecretarias().add(orgaoOrg.getCodOrg());
//							}
//						}
//					}else{
//						if(StringUtils.contains(orgaoOrg.getSiglaOrg(), secretaria.getSiglaOrg())){
//							secretariaDTO.getCodigosSecretarias().add(orgaoOrg.getCodOrg());
//						}
//					}
//				}
//			}
//			
//			listaSecretarias.add(secretariaDTO);
//		}
			
		return listaSecretarias;
	}
	

	
	/**
	 * @deprecated Use {@link #preencherFiltroPesquisaMonitoramento(String[],String[],String[],String[],String,String,String,SegurancaECAR,String,String,String,String,String,String)} instead
	 */
	public FiltroPesquisaMonitoramento preencherFiltroPesquisaMonitoramento(String [] objetivosSelecionados, String [] etiquetasSelecionadas, String [] statusSelecionados, String [] secretariasSelecionadas, String prioritario, String codigoRelatorio, String ordenarPorStatus, SegurancaECAR seguranca, String dataInicial, String dataFinal, String comIndicadores, String exercicio, String somenteREM){
		return preencherFiltroPesquisaMonitoramento(objetivosSelecionados,
				etiquetasSelecionadas, statusSelecionados,
				secretariasSelecionadas, prioritario, codigoRelatorio,
				ordenarPorStatus, seguranca, dataInicial, dataFinal,
				comIndicadores, exercicio, somenteREM, null);
	}

	public FiltroPesquisaMonitoramento preencherFiltroPesquisaMonitoramento(String [] objetivosSelecionados, String [] etiquetasSelecionadas, String [] statusSelecionados, String [] secretariasSelecionadas, String prioritario, String codigoRelatorio, String ordenarPorStatus, SegurancaECAR seguranca, String dataInicial, String dataFinal, String comIndicadores, String exercicio, String somenteREM, String acompReferencia){
		FiltroPesquisaMonitoramento filtroPesquisaMonitoramento = new FiltroPesquisaMonitoramento();
		filtroPesquisaMonitoramento.setCodigosObjetivosEstrategicos(preencherCodigosObjetivosEstrategicos(objetivosSelecionados != null ? objetivosSelecionados : new String[0]));
		filtroPesquisaMonitoramento.setEtiquetasSelecionadas(preencherEtiquetasSelecionadas(etiquetasSelecionadas != null ? etiquetasSelecionadas : new String[0]));
		filtroPesquisaMonitoramento.setStatusSelecionados(preencherStatusSelecionados(statusSelecionados != null ? statusSelecionados : new String[0]));
		filtroPesquisaMonitoramento.setSecretariasSelecionadas(preencherSecretariasSelecionadas(secretariasSelecionadas != null ? secretariasSelecionadas : new String[0]));
		filtroPesquisaMonitoramento.setPrioritario(StringUtils.isNotBlank(prioritario) ? true : false);
		filtroPesquisaMonitoramento.setTipoRelatorioAcompanhamento(preencherTipoRelatorioAcompanhamento(codigoRelatorio));
		filtroPesquisaMonitoramento.setOrdenarPorStatus(preencherOrdenacaoPorStatus(ordenarPorStatus));
		filtroPesquisaMonitoramento.setCodigosUsuariosPermissao(preencherCodigosGruposUsuarios(seguranca));
		filtroPesquisaMonitoramento.setCodigoUsuario(seguranca.getCodUsu());
		filtroPesquisaMonitoramento.setEtiquetasSemFonetizacao(preencherEtiquetasSemFonetizacao(etiquetasSelecionadas));
		filtroPesquisaMonitoramento.setDataInicial(dataInicial != null ? Data.parseDate(dataInicial) : null);		 
		filtroPesquisaMonitoramento.setDataFinal(preencherDataFinal(dataFinal != null ? dataFinal : null));
		filtroPesquisaMonitoramento.setComIndicadores(preencherComIndicadores(comIndicadores));
		filtroPesquisaMonitoramento.setExercicio((exercicio!=null && !exercicio.equals(""))?Long.parseLong(exercicio.toString()):0L);
		filtroPesquisaMonitoramento.setSomenteREM(preencherComIndicadores(somenteREM));
		filtroPesquisaMonitoramento.setAcompReferencia((acompReferencia!=null && acompReferencia!="")?Long.parseLong(acompReferencia):0L);
		
		return filtroPesquisaMonitoramento;
	}

	private Date preencherDataFinal(String dataFinal) {
		if(dataFinal == null || dataFinal.isEmpty()) {
			return null;
		}
		
		Calendar dt = Data.getCalendar(Data.parseDate(dataFinal));
		dt.add(Calendar.HOUR_OF_DAY, 23);
		dt.add(Calendar.MINUTE, 59);
		dt.add(Calendar.SECOND, 59);
		
		return dt.getTime();
	}
	
	@SuppressWarnings("unchecked")
	private List<Long> preencherCodigosGruposUsuarios(SegurancaECAR seguranca){
		List<Long> codigosGruposUsuarios = new ArrayList<Long>();
		Iterator i = seguranca.getGruposAcesso().iterator();
		while(i.hasNext()){
			SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) i.next();
			codigosGruposUsuarios.add(sisAtributoSatb.getCodSatb());
		}
		return codigosGruposUsuarios;
	}
	
	private boolean preencherOrdenacaoPorStatus(String ordenarPorStatus){
		if(StringUtils.isNotBlank(ordenarPorStatus)){
			return true;
		}else{
			return false;
		}
	}
	
	private boolean preencherComIndicadores(String comIndicadores){
		if(StringUtils.isNotBlank(comIndicadores)){
			return true;
		}else{
			return false;
		}
	}	
	
	private TipoRelatorioAcompanhamento preencherTipoRelatorioAcompanhamento(String codigoRelatorio){
		TipoRelatorioAcompanhamento tipoRelatorioAcompanhamento = null;
		for(TipoRelatorioAcompanhamento tipo : TipoRelatorioAcompanhamento.values()){
			if(StringUtils.equals(tipo.getCodigo(), codigoRelatorio)){
				tipoRelatorioAcompanhamento = tipo;
				break;
			}
		}
		
		return tipoRelatorioAcompanhamento;
	}
	
	private List<Long> preencherSecretariasSelecionadas(String [] secretariasSelecionadas){
		List<Long> codigosSecretarias = new ArrayList<Long>();
		if(secretariasSelecionadas != null){
			for(String secretaria : secretariasSelecionadas){
				String [] codigos = secretaria.split(",");
				for(String codigo : codigos){
					if(StringUtils.isNotBlank(codigo)){
						codigosSecretarias.add(new Long(codigo.replaceAll("[^0-9]", "")));
					}
				}
			}
		}
		
		return codigosSecretarias;
	}
	
	private List<Integer> preencherStatusSelecionados(String [] statusSelecionados){
		List<Integer> listaStatus = new ArrayList<Integer>();
		if(statusSelecionados != null){
			for(String status : statusSelecionados){
				listaStatus.add(new Integer(status));
			}
		}
		
		return listaStatus;
	}
	
	private List<String> preencherEtiquetasSelecionadas(String [] etiquetasSelecionadas){
		List<String> listaEtiquetas = new ArrayList<String>();		
		for(String etiquetaSelecionada : etiquetasSelecionadas){
			String [] etiquetas = etiquetaSelecionada.split(",");
			for(String etiqueta : etiquetas){
				if(StringUtils.isNotBlank(etiqueta)){
					listaEtiquetas.add(EtiquetaUtils.fonetizar(etiqueta));					
				}
			}
		}
		return listaEtiquetas;
	}
	
	private List<String> preencherEtiquetasSemFonetizacao(String [] etiquetasSelecionadas){		
		List<String> listaEtiquetasSemFonetizacao = new ArrayList<String>();
		for(String etiquetaSelecionada : etiquetasSelecionadas){
			String [] etiquetas = etiquetaSelecionada.split(",");
			for(String etiqueta : etiquetas){
				if(StringUtils.isNotBlank(etiqueta)){					
					listaEtiquetasSemFonetizacao.add(etiqueta);
				}
			}
		}
		return listaEtiquetasSemFonetizacao;
	}
	
	private List<Integer> preencherCodigosObjetivosEstrategicos(String [] objetivosSelecionados){
		List<Integer> codigosObjetivos = new ArrayList<Integer>();
		for(String codigo : objetivosSelecionados){
			codigosObjetivos.add(new Integer(codigo));
		}
		return codigosObjetivos;
	}

	public JasperPrint emitirRelatorioAcompanhamento(FiltroPesquisaMonitoramento filtro) {
		JasperPrint jasperPrint = null;
		if(filtro.getTipoRelatorioAcompanhamento() == TipoRelatorioAcompanhamento.EXECUTIVO){
			jasperPrint = emitirRelatorioGerencial(filtro);
		}else if(filtro.getTipoRelatorioAcompanhamento() == TipoRelatorioAcompanhamento.OPERACIONAL_PRODUTOS){
			jasperPrint = emitirRelatorioOperacionalProdutos(filtro);
		}else if(filtro.getTipoRelatorioAcompanhamento() == TipoRelatorioAcompanhamento.OPERACIONAL_PRODUTOS_ACOES){
			jasperPrint = emitirRelatorioOperacionalProdutos(filtro);
		}else if(filtro.getTipoRelatorioAcompanhamento() == TipoRelatorioAcompanhamento.OPERACIONAL_PRODUTOS_ACOES_ATIVIDADES){
			jasperPrint = emitirRelatorioOperacionalProdutos(filtro);
		}else if(filtro.getTipoRelatorioAcompanhamento() == TipoRelatorioAcompanhamento.INDICADORES){
			jasperPrint = emitirRelatorioIndicadores(filtro);
		}else if(filtro.getTipoRelatorioAcompanhamento() == TipoRelatorioAcompanhamento.GERENCIAL){
			jasperPrint = emitirRelatorioGerencial(filtro);
		}else if(filtro.getTipoRelatorioAcompanhamento() == TipoRelatorioAcompanhamento.ENCAMINHAMENTOS){
			jasperPrint = emitirRelatorioEncaminhamentos(filtro);
		}else if(filtro.getTipoRelatorioAcompanhamento() == TipoRelatorioAcompanhamento.INDICADORES_DETALHAMENTO){
			jasperPrint = emitirRelatorioIndicadoresDetalhamento(filtro);
		}else if(filtro.getTipoRelatorioAcompanhamento() == TipoRelatorioAcompanhamento.GERENCIAL_MONITORAMENTOS){
			jasperPrint = emitirRelatorioGerencialMonitoramentos(filtro);
		}
		
		return jasperPrint;
	}
	
	private JasperPrint emitirRelatorioExecutivo(FiltroPesquisaMonitoramento filtro){
		JasperPrint relatorio = null;
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		List<ObjetivoEstrategico> listaOE;
		try {
			listaOE = itemEstruturaDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(filtro.getCodigosObjetivosEstrategicos(), filtro.getEtiquetasSelecionadas(), new ArrayList<String>(), filtro.getStatusSelecionados(), filtro.getCodigoUsuario(), filtro.getCodigosUsuariosPermissao(), false, true, filtro.isPrioritario(), filtro.getSecretariasSelecionadas(), false, null, null, filtro.getExercicio(), false, filtro.getAcompReferencia(), false);
			
			if(filtro.isOrdenarPorStatus()){
				for(ObjetivoEstrategico objetivoEstrategico : listaOE){
					for(Estrategia estrategia : objetivoEstrategico.getEstrategias()){
						java.util.Collections.sort(estrategia.getResultados(), new ResultadoComparator());
					}
				}
			}
			
			List<Long> idsResultados = GraficoUtils.extrairIdsResultados(listaOE);
			BufferedImage graficoResultadosRelatorioExecutivo = GraficoUtils.gerarGraficoResultados(idsResultados, 277, 105, 3, false, new Font("Arial", Font.BOLD, 8), filtro.getExercicio(), filtro.getAcompReferencia());
			BufferedImage graficoRelatorioExecutivoResultadosPrioritarios = GraficoUtils.gerarGraficoResultados(idsResultados, 278, 103, 3, true, new Font("Arial", Font.BOLD, 8), filtro.getExercicio(), filtro.getAcompReferencia());
			
			Map<String, Object> parametros = new HashMap<String, Object>();
	
			parametros.put("graficoRelatorioExecutivoResultados", graficoResultadosRelatorioExecutivo);
			parametros.put("graficoRelatorioExecutivoResultadosPrioritarios", graficoRelatorioExecutivoResultadosPrioritarios);
			parametros.put("cruzLogo", JasperService.class.getResource("/images/" + "/logo_small_bijari.gif").getPath());
			parametros.put("msLogo", JasperService.class.getResource("/images/" + "/header_logos_bijari.gif").getPath());
			parametros.put("ecarLogo", JasperService.class.getResource("/images/" + "/ecarLogo_2.png").getPath());
	
			JasperReport report = JasperService.loadReport(JasperService.class.getResource("/peJasper/"	+ "/relBijari.jasper").getPath());
					
			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(listaOE);
	
			/* Par�metros */
			parametros.put("caminho_smileAmarelo", JasperService.class.getResource("/images/" + "/sAmarelo9.png").getPath()); // Caminho para carinha de status amarelo
			parametros.put("caminho_smileVerde", JasperService.class.getResource("/images/" + "/sVerde9.png").getPath()); // Caminho para carinha de status verde
			parametros.put("caminho_smileVermelho", JasperService.class.getResource("/images/" + "/sVermelho9.png").getPath()); // Caminho para carinha de status vermelho
			parametros.put("caminho_smileAzul", JasperService.class.getResource("/images/" + "/sAzul9.png").getPath()); // Caminho para carinha de status azul 
			parametros.put("caminho_smileBranco", JasperService.class.getResource("/images/" + "/sBranco9.png").getPath()); // Caminho para carinha de status branco
			
			parametros.put("caminho_relBijari_subOE", JasperService.class.getResource("/peJasper/" + "/relBijari_subOE.jasper").getPath()); // Caminho para sub-relatorio de estrat�gias
			parametros.put("caminho_relBijari_subOE_subEst", JasperService.class.getResource("/peJasper/" + "/relBijari_subOE_subEst.jasper").getPath()); // Caminho para sub-relatorio de resultados
			parametros.put("caminho_relBijari_subOE_subEst_subResult", JasperService.class.getResource("/peJasper/"+ "/relBijari_subOE_subEst_subResult.jasper").getPath()); // Caminho para sub-relatorio de status
	
			relatorio = JasperService.fillReport(report, parametros, beanDS);
			
			return relatorio;
		} catch (ECARException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private JasperPrint emitirRelatorioOperacionalProdutos(FiltroPesquisaMonitoramento filtro){
		JasperPrint relatorio = null;
		Map<String, Object> parametros = new HashMap<String, Object>();
//		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		SiteDao siteDao = new SiteDao();
		Boolean mostrarParecerProd = false;
		String tRel = "";
		
		List<ObjetivoEstrategico> listaOE;
		try {
//			listaOE = itemEstruturaDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(filtro.getCodigosObjetivosEstrategicos(), filtro.getEtiquetasSelecionadas(), new ArrayList<String>(), filtro.getStatusSelecionados(), filtro.getCodigoUsuario(), filtro.getCodigosUsuariosPermissao(), false, false, filtro.isPrioritario(), filtro.getSecretariasSelecionadas(), false, null, null, filtro.getExercicio(), false, filtro.getAcompReferencia(), false);
			listaOE = siteDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(filtro.getCodigosObjetivosEstrategicos(), filtro.getEtiquetasSelecionadas(), new ArrayList<String>(), filtro.getStatusSelecionados(), filtro.getCodigoUsuario(), filtro.getCodigosUsuariosPermissao(), false, false, filtro.isPrioritario(), filtro.getSecretariasSelecionadas(), false, null, null, filtro.getExercicio(), false, filtro.getAcompReferencia(), false, 0);
			
			if(filtro.isOrdenarPorStatus()){
				for(ObjetivoEstrategico objetivoEstrategico : listaOE){
					for(Estrategia estrategia : objetivoEstrategico.getEstrategias()){
						java.util.Collections.sort(estrategia.getResultados(), new ResultadoComparator());
					}
				}
			}
			
			JRBeanCollectionDataSource listaObjetivosEstrategicos = new JRBeanCollectionDataSource(listaOE);
			
			JasperReport report = JasperService.loadReport(JasperService.class.getResource("/peJasper/" + "/relatorioOperacionalBijari.jasper").getPath());
			parametros.put("caminhoSubRelEstratOperacional", JasperService.class.getResource("/peJasper/" + "/subRelatorioOperacionalEstrategias.jasper").getPath());
			if(filtro.isComIndicadores()){
				parametros.put("caminhoSubRelIndicadores", JasperService.class.getResource("/peJasper/" + "/subRelatorioIndicadores_Indicadores.jasper").getPath());
			}	
			parametros.put("caminhoSubRelResultOperacional", JasperService.class.getResource("/peJasper/" + "/subRelatorioOperacionalResultado.jasper").getPath());
			parametros.put("subRelProdutos", JasperService.class.getResource("/peJasper/" + "/subRelatorioGerencialEstrategiasResultadosProdutos.jasper").getPath());
			
			tRel = TipoRelatorioAcompanhamento.OPERACIONAL_PRODUTOS.getDescricaoReport();
			if(filtro.getTipoRelatorioAcompanhamento() == TipoRelatorioAcompanhamento.OPERACIONAL_PRODUTOS_ACOES) {
				parametros.put("caminhoSubRelProdutoOperacional", JasperService.class.getResource("/peJasper/" + "/subRelatorioOperacionalProduto.jasper").getPath());
				parametros.put("subRelatorioAcoes", JasperService.class.getResource("/peJasper/" + "/subRelatorioAcoes.jasper").getPath());
				mostrarParecerProd = true;
				tRel = TipoRelatorioAcompanhamento.OPERACIONAL_PRODUTOS_ACOES.getDescricaoReport();
			}						
			
			parametros.put("ecarLogo", JasperService.class.getResource("/images/" + "/ecarLogo_2.png").getPath());
			parametros.put("cruzLogo", JasperService.class.getResource("/images/" + "/logo_small_bijari.gif").getPath());
			parametros.put("msLogo", JasperService.class.getResource("/images/" + "/header_logos_bijari.gif").getPath());
			parametros.put("mostrarParecerProd", mostrarParecerProd);
			parametros.put("tipoRelatorio", tRel);
			parametros.put("filtro", filtro.toString());
			relatorio = JasperService.fillReport(report, parametros, listaObjetivosEstrategicos);
			
			return relatorio;
		} catch (ECARException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private JasperPrint emitirRelatorioIndicadores(FiltroPesquisaMonitoramento filtro){
		//modifiquei o ultimo parametro para true, para testar o relatorio de detalhamento, voltar para false, quando criar o método próprio
		if(1==1){
			return emitirRelatorioIndicadoresExcel(filtro);	
		}		
		
		JasperPrint relatorio = null;
		try {
//			ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
			SiteDao siteDao = new SiteDao();
//			List<ObjetivoEstrategico> listaOE = itemEstruturaDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(
					List<ObjetivoEstrategico> listaOE = siteDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(
					filtro.getCodigosObjetivosEstrategicos(), filtro.getEtiquetasSelecionadas(), new ArrayList<String>(), 
					filtro.getStatusSelecionados(), filtro.getCodigoUsuario(), filtro.getCodigosUsuariosPermissao(), false, false, 
					filtro.isPrioritario(), filtro.getSecretariasSelecionadas(), false, null, null, filtro.getExercicio(), 
					filtro.isSomenteREM(), filtro.getAcompReferencia(), false, 0);
			
			if(filtro.isOrdenarPorStatus()){
				for(ObjetivoEstrategico objetivoEstrategico : listaOE){
					for(Estrategia estrategia : objetivoEstrategico.getEstrategias()){
						java.util.Collections.sort(estrategia.getResultados(), new ResultadoComparator());
					}
				}
			}
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			
			parametros.put("titulo", "INDICADORES DE RESULTADOS");
			parametros.put("filtro", filtro.toString());
			parametros.put("tipoRelatorio", filtro.getTipoRelatorioAcompanhamento().getDescricaoReport());
			parametros.put("ecarLogo", JasperService.class.getResource("/images/" + "/ecarLogo_2.png").getPath());
			parametros.put("cruzLogo", JasperService.class.getResource("/images/" + "/logo_small_bijari.gif").getPath());
			parametros.put("msLogo", JasperService.class.getResource("/images/" + "/header_logos_bijari.gif").getPath());
			
			parametros.put("caminhoSubRelatorioIndicadores_Estrategias", JasperService.class.getResource("/peJasper/" + "/subRelatorioIndicadores_Estrategias.jasper").getPath());
			parametros.put("caminhoSubRelatorioIndicadores_Resultados", JasperService.class.getResource("/peJasper/" + "/subRelatorioIndicadores_Resultados.jasper").getPath());
			parametros.put("caminhoSubRelatorioIndicadores_Indicadores", JasperService.class.getResource("/peJasper/" + "/subRelatorioIndicadores_Indicadores.jasper").getPath());
			
			JasperReport report = JasperService.loadReport(JasperService.class.getResource("/peJasper/" + "/relatorioIndicadores.jasper").getPath());
			
			JRBeanCollectionDataSource listaObjetivos = new JRBeanCollectionDataSource(listaOE);
			
			relatorio = JasperService.fillReport(report, parametros, listaObjetivos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return relatorio;
	}
	
	private JasperPrint emitirRelatorioIndicadoresDetalhamento(FiltroPesquisaMonitoramento filtro){
		JasperPrint relatorio = null;
		try {
			//ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
			SiteDao dao = new SiteDao();			
			List<ObjetivoEstrategico> listaOE = dao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(filtro.getCodigosObjetivosEstrategicos(),
					filtro.getEtiquetasSelecionadas(), new ArrayList<String>(), filtro.getStatusSelecionados(), filtro.getCodigoUsuario(), filtro.getCodigosUsuariosPermissao(), true, false, 
					filtro.isPrioritario(), filtro.getSecretariasSelecionadas(), false, null, null, filtro.getExercicio(), 
					filtro.isSomenteREM(), filtro.getAcompReferencia(), true, 0);
					/* 
					 itemEstruturaDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(
					filtro.getCodigosObjetivosEstrategicos(), filtro.getEtiquetasSelecionadas(), new ArrayList<String>(), 
					filtro.getStatusSelecionados(), filtro.getCodigoUsuario(), filtro.getCodigosUsuariosPermissao(), true, false, 
					filtro.isPrioritario(), filtro.getSecretariasSelecionadas(), false, null, null, filtro.getExercicio(), 
					filtro.isSomenteREM(), filtro.getAcompReferencia(), true);
					*/
					
			
			if(filtro.isOrdenarPorStatus()){
				for(ObjetivoEstrategico objetivoEstrategico : listaOE){
					for(Estrategia estrategia : objetivoEstrategico.getEstrategias()){
						java.util.Collections.sort(estrategia.getResultados(), new ResultadoComparator());
					}
				}
			}

			Map<String, Object> parametros = new HashMap<String, Object>();
			
			parametros.put("titulo", "INDICADORES DE RESULTADOS");
			parametros.put("filtro", filtro.toString());
			parametros.put("tipoRelatorio", filtro.getTipoRelatorioAcompanhamento().getDescricaoReport());
			parametros.put("ecarLogo", JasperService.class.getResource("/images/" + "/ecarLogo_2.png").getPath());
			parametros.put("cruzLogo", JasperService.class.getResource("/images/" + "/logo_small_bijari.gif").getPath());
			parametros.put("msLogo", JasperService.class.getResource("/images/" + "/header_logos_bijari.gif").getPath());
			
			parametros.put("caminhoSubRelatorioIndicadores_Estrategias", "Detalhamento");
			parametros.put("caminhoSubRelatorioIndicadores_Detalhamento_Estrategias", JasperService.class.getResource("/peJasper/" + "/subRelatorioIndicadores_Detalhamento_Estrategias.jasper").getPath());
			parametros.put("caminhoSubRelatorioIndicadores_Resultados", JasperService.class.getResource("/peJasper/" + "/subRelatorioIndicadores_Resultados.jasper").getPath());
			parametros.put("caminhoSubRelatorioIndicadores_Detalhamento", JasperService.class.getResource("/peJasper/" + "/subRelatorioIndicadores_Indicadores_detalhamento.jasper").getPath());
			parametros.put("caminhoSubRelatorioIndicadores_Detalhamento_Detalhe", JasperService.class.getResource("/peJasper/" + "/subRelatorioIndicadores_Indicadores_detalhamento_detalhe.jasper").getPath());
			
			JasperReport report = JasperService.loadReport(JasperService.class.getResource("/peJasper/" + "/relatorioIndicadores.jasper").getPath());
			
			JRBeanCollectionDataSource listaObjetivos = new JRBeanCollectionDataSource(listaOE);
			
			relatorio = JasperService.fillReport(report, parametros, listaObjetivos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return relatorio;
	}
	
	public JasperPrint emitirRelatorioGerencial(FiltroPesquisaMonitoramento filtro){
		JasperPrint relatorio = null;
		Map<String, Object> parametros = new HashMap<String, Object>();
//		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		SiteDao siteDao = new SiteDao();
		boolean isGerencial = true;
		String tRel = TipoRelatorioAcompanhamento.GERENCIAL.getDescricaoReport();
		
		if(filtro.getTipoRelatorioAcompanhamento() == TipoRelatorioAcompanhamento.EXECUTIVO) {
			isGerencial = false;
			tRel = TipoRelatorioAcompanhamento.EXECUTIVO.getDescricaoReport();
		}
		
		List<ObjetivoEstrategico> listaOE;
		try {
//			listaOE = itemEstruturaDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(filtro.getCodigosObjetivosEstrategicos(), filtro.getEtiquetasSelecionadas(), new ArrayList<String>(), filtro.getStatusSelecionados(), filtro.getCodigoUsuario(), filtro.getCodigosUsuariosPermissao(), false, true, filtro.isPrioritario(), filtro.getSecretariasSelecionadas(), false, null, null, filtro.getExercicio(), false, filtro.getAcompReferencia(), false);
			listaOE = siteDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(filtro.getCodigosObjetivosEstrategicos(), filtro.getEtiquetasSelecionadas(), new ArrayList<String>(), filtro.getStatusSelecionados(), filtro.getCodigoUsuario(), filtro.getCodigosUsuariosPermissao(), false, true, filtro.isPrioritario(), filtro.getSecretariasSelecionadas(), false, null, null, filtro.getExercicio(), false, filtro.getAcompReferencia(), false, 0);
		
			if(filtro.isOrdenarPorStatus()){
				for(ObjetivoEstrategico objetivoEstrategico : listaOE){
					for(Estrategia estrategia : objetivoEstrategico.getEstrategias()){
						java.util.Collections.sort(estrategia.getResultados(), new ResultadoComparator());
					}
				}
			}
			
			List<Long> idsResultados = GraficoUtils.extrairIdsResultados(listaOE);
			BufferedImage graficoResultados = GraficoUtils.gerarGraficoResultados(idsResultados, 360, 360, 3, false, new Font("Arial", Font.BOLD, 11), filtro.getExercicio(), filtro.getAcompReferencia());
			BufferedImage graficoResultadosPrioritarios = GraficoUtils.gerarGraficoResultados(idsResultados, 360, 360, 3, true, new Font("Arial", Font.BOLD, 11), filtro.getExercicio(), filtro.getAcompReferencia());
			BufferedImage graficoProdutos = GraficoUtils.gerarGraficoProdutos(idsResultados, 360, 360, 6, false, new Font("Arial", Font.BOLD, 11), filtro.getExercicio(), filtro.getAcompReferencia());
			BufferedImage graficoProdutosResultadosPrioritarios = GraficoUtils.gerarGraficoProdutos(idsResultados, 360, 360, 6, true, new Font("Arial", Font.BOLD, 11), filtro.getExercicio(), filtro.getAcompReferencia());
			parametros.put("filtro", filtro.toString());
			parametros.put("graficoResultados", graficoResultados);
			parametros.put("graficoResultadosPrioritarios", graficoResultadosPrioritarios);
			parametros.put("graficoProdutos", graficoProdutos);
			parametros.put("graficoProdutosResultadosPrioritarios", graficoProdutosResultadosPrioritarios);
			
			JRBeanCollectionDataSource listaObjetivosEstrategicos = new JRBeanCollectionDataSource(listaOE);
			
			JasperReport report = JasperService.loadReport(JasperService.class.getResource("/peJasper/" + "/relatorioGerencialBijari.jasper").getPath());
			parametros.put("caminhoSubRelatorioGerencialEstrategias", JasperService.class.getResource("/peJasper/" + "/subRelatorioGerencialEstrategias.jasper").getPath());
			parametros.put("caminhoSubRelatorioGerencialEstrategiasResultados", JasperService.class.getResource("/peJasper/" + "/subRelatorioGerencialEstrategiasResultados.jasper").getPath());
			parametros.put("caminhoSubRelatorioStatusResultadoRelatorioGerencial", JasperService.class.getResource("/peJasper/" + "/subRelatorioStatusResultadoRelatorioGerencial.jasper").getPath());
			parametros.put("caminhoSubRelatorioGerencialEstrategiasResultadosProdutos", JasperService.class.getResource("/peJasper/" + "/subRelatorioGerencialEstrategiasResultadosProdutos.jasper").getPath());
			parametros.put("caminhoSubRelatorioStatusProdutoRelatorioGerencial", JasperService.class.getResource("/peJasper/" + "/subRelatorioStatusProdutoRelatorioGerencial.jasper").getPath());
			parametros.put("caminhoImagemStatusAmarelo", JasperService.class.getResource("/images/" + "/sAmarelo9.png").getPath());
			parametros.put("caminhoImagemStatusVerde", JasperService.class.getResource("/images/" + "/sVerde9.png").getPath()); 
			parametros.put("caminhoImagemStatusVermelho", JasperService.class.getResource("/images/" + "/sVermelho9.png").getPath());
			parametros.put("ecarLogo", JasperService.class.getResource("/images/" + "/ecarLogo_2.png").getPath());
			parametros.put("cruzLogo", JasperService.class.getResource("/images/" + "/logo_small_bijari.gif").getPath());
			parametros.put("msLogo", JasperService.class.getResource("/images/" + "/header_logos_bijari.gif").getPath());
			parametros.put("isGerencial", isGerencial);
			parametros.put("tipoRelatorio", tRel);
			relatorio = JasperService.fillReport(report, parametros, listaObjetivosEstrategicos);
			
			return relatorio;
			
		} catch (ECARException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private JasperPrint emitirRelatorioEncaminhamentos(FiltroPesquisaMonitoramento filtro){
		JasperPrint relatorio = null; 
		try {
			ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
//			SiteDao siteDao = new SiteDao();
			List<ObjetivoEstrategico> listaOE = itemEstruturaDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(filtro.getCodigosObjetivosEstrategicos(), filtro.getEtiquetasSelecionadas(), new ArrayList<String>(), filtro.getStatusSelecionados(), filtro.getCodigoUsuario(), filtro.getCodigosUsuariosPermissao(), false, false, filtro.isPrioritario(), filtro.getSecretariasSelecionadas(), true, filtro.getDataInicial(), filtro.getDataFinal(), filtro.getExercicio(), false, filtro.getAcompReferencia(), false);
//			List<ObjetivoEstrategico> listaOE = siteDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(filtro.getCodigosObjetivosEstrategicos(), filtro.getEtiquetasSelecionadas(), new ArrayList<String>(), filtro.getStatusSelecionados(), filtro.getCodigoUsuario(), filtro.getCodigosUsuariosPermissao(), false, false, filtro.isPrioritario(), filtro.getSecretariasSelecionadas(), true, filtro.getDataInicial(), filtro.getDataFinal(), filtro.getExercicio(), false, filtro.getAcompReferencia(), false, 0);
			
			if(filtro.isOrdenarPorStatus()){
				for(ObjetivoEstrategico objetivoEstrategico : listaOE){
					for(Estrategia estrategia : objetivoEstrategico.getEstrategias()){
						java.util.Collections.sort(estrategia.getResultados(), new ResultadoComparator());
					}
				}
			}
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			
			parametros.put("titulo", "ENCAMINHAMENTO DOS RESULTADOS");
			parametros.put("filtro", filtro.toString());
			parametros.put("tipoRelatorio", filtro.getTipoRelatorioAcompanhamento().getDescricaoReport());
			parametros.put("ecarLogo", JasperService.class.getResource("/images/" + "/ecarLogo_2.png").getPath());
			parametros.put("cruzLogo", JasperService.class.getResource("/images/" + "/logo_small_bijari.gif").getPath());
			parametros.put("msLogo", JasperService.class.getResource("/images/" + "/header_logos_bijari.gif").getPath());
			
			parametros.put("caminhoSubRelatorioEncaminhamento_Estrategias", JasperService.class.getResource("/peJasper/" + "/subRelatorioEncaminhamento_Estrategias.jasper").getPath());
			parametros.put("caminhoSubRelatorioEncaminhamento_Resultados", JasperService.class.getResource("/peJasper/" + "/subRelatorioEncaminhamento_Resultados.jasper").getPath());
			parametros.put("caminhoSubRelatorioEncaminhamento_Encaminhamentos", JasperService.class.getResource("/peJasper/" + "/subRelatorioEncaminhamento_Encaminhamentos.jasper").getPath());
			parametros.put("caminhoSubRelatorioIndicadores_Estrategias", "");
			
			JasperReport report = JasperService.loadReport(JasperService.class.getResource("/peJasper/" + "/relatorioIndicadores.jasper").getPath());
			
			JRBeanCollectionDataSource listaObjetivos = new JRBeanCollectionDataSource(listaOE);
			
			relatorio = JasperService.fillReport(report, parametros, listaObjetivos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return relatorio;
	}		
	
	public ItemEstruturaIett recuperarSiglaIettPorCodigo(Long codEstruturaIett){
		StringBuilder hql = new StringBuilder();
		hql.append("from ItemEstruturaIett item ");
		hql.append("where item.codIett = ? ");
		
		Dao<ItemEstruturaIett> itemDao = new Dao<ItemEstruturaIett>();
		
		return itemDao.buscarPorHQL(hql.toString(), codEstruturaIett);
	}
	
	
	public OrgaoOrg recuperarOrgaoPorCodigo(Long codOrgao){
		StringBuilder hql = new StringBuilder();
		hql.append("from OrgaOrg item ");
		hql.append("where item.codIett = ? ");
		
		Dao<ItemEstruturaIett> orgaoDao = new Dao<ItemEstruturaIett>();
		
		OrgaoOrg orgaoOrg = new OrgaoOrg();
		
		try {
			orgaoOrg =  (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, codOrgao);
		} catch (ECARException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orgaoOrg;
	}	
	
	private JasperPrint emitirRelatorioIndicadoresExcel(FiltroPesquisaMonitoramento filtro){
		//modifiquei o ultimo parametro para true, para testar o relatorio de detalhamento, voltar para false, quando criar o método próprio
//		if(1==1){
//			return emitirRelatorioIndicadoresDetalhamento(filtro);	
//		}		
		
		JasperPrint relatorio = null;
		try {
//			ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
			SiteDao siteDao = new SiteDao();
			
			List<ObjetivoEstrategico> listaOE = siteDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(
//					List<ObjetivoEstrategico> listaOE = itemEstruturaDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(
					filtro.getCodigosObjetivosEstrategicos(), filtro.getEtiquetasSelecionadas(), new ArrayList<String>(), 
					filtro.getStatusSelecionados(), filtro.getCodigoUsuario(), filtro.getCodigosUsuariosPermissao(), false, false, 
					filtro.isPrioritario(), filtro.getSecretariasSelecionadas(), false, null, null, filtro.getExercicio(), 
					filtro.isSomenteREM(), filtro.getAcompReferencia(), false, 0);
			
			if(filtro.isOrdenarPorStatus()){
				for(ObjetivoEstrategico objetivoEstrategico : listaOE){
					for(Estrategia estrategia : objetivoEstrategico.getEstrategias()){
						java.util.Collections.sort(estrategia.getResultados(), new ResultadoComparator());
					}
				}
			}
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			
			parametros.put("titulo", "INDICADORES DE RESULTADOS");
			parametros.put("filtro", filtro.toString());
			parametros.put("tipoRelatorio", filtro.getTipoRelatorioAcompanhamento().getDescricaoReport());
			parametros.put("ecarLogo", JasperService.class.getResource("/images/" + "/ecarLogo_2.png").getPath());
			parametros.put("cruzLogo", JasperService.class.getResource("/images/" + "/logo_small_bijari.gif").getPath());
			parametros.put("msLogo", JasperService.class.getResource("/images/" + "/header_logos_bijari.gif").getPath());
			
			parametros.put("caminhoSubRelatorioIndicadoresExcel_Estrategias", JasperService.class.getResource("/peJasper/" + "/subRelatorioIndicadoresExcel_Estrategias.jasper").getPath());
			parametros.put("caminhoSubRelatorioIndicadoresExcel_Resultados", JasperService.class.getResource("/peJasper/" + "/subRelatorioIndicadoresExcel_Resultados.jasper").getPath());
			parametros.put("caminhoSubRelatorioIndicadoresExcel_Produtos", JasperService.class.getResource("/peJasper/" + "/subRelatorioIndicadoresExcel_Produtos.jasper").getPath());
			parametros.put("caminhoSubRelatorioIndicadoresExcel_Acoes", JasperService.class.getResource("/peJasper/" + "/subRelatorioIndicadoresExcel_Acoes.jasper").getPath());
			parametros.put("caminhoSubRelatorioIndicadoresExcel_Indicadores", JasperService.class.getResource("/peJasper/" + "/subRelatorioIndicadoresExcel_Indicadores.jasper").getPath());
			
			JasperReport report = JasperService.loadReport(JasperService.class.getResource("/peJasper/" + "/relatorioIndicadoresExcel.jasper").getPath());
			
			JRBeanCollectionDataSource listaObjetivos = new JRBeanCollectionDataSource(listaOE);
			
			relatorio = JasperService.fillReport(report, parametros, listaObjetivos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return relatorio;
	}
	
	
	public JasperPrint emitirRelatorioGerencialMonitoramentos(FiltroPesquisaMonitoramento filtro){
		JasperPrint relatorio = null;
		Map<String, Object> parametros = new HashMap<String, Object>();
		SiteDao siteDao = new SiteDao();
		boolean isGerencial = true;
		String tRel = TipoRelatorioAcompanhamento.GERENCIAL_MONITORAMENTOS.getDescricaoReport();
		
		List<ObjetivoEstrategico> listaOE;
		try {
			listaOE = siteDao.listarObjetivoEstrategicoFiltroComIndicadorMonitoramentos(filtro.getCodigosObjetivosEstrategicos(), filtro.getEtiquetasSelecionadas(), new ArrayList<String>(), filtro.getStatusSelecionados(), filtro.getCodigoUsuario(), filtro.getCodigosUsuariosPermissao(), false, true, filtro.isPrioritario(), filtro.getSecretariasSelecionadas(), false, null, null, filtro.getExercicio(), false, filtro.getAcompReferencia(), false, 0);
			
			if(filtro.isOrdenarPorStatus()){
				for(ObjetivoEstrategico objetivoEstrategico : listaOE){
					for(Estrategia estrategia : objetivoEstrategico.getEstrategias()){
						java.util.Collections.sort(estrategia.getResultados(), new ResultadoComparator());
					}
				}
			}
			
			List<Long> idsResultados = GraficoUtils.extrairIdsResultados(listaOE);
			BufferedImage graficoResultados = GraficoUtils.gerarGraficoResultados(idsResultados, 360, 360, 3, false, new Font("Arial", Font.BOLD, 11), filtro.getExercicio(), filtro.getAcompReferencia());
			BufferedImage graficoResultadosPrioritarios = GraficoUtils.gerarGraficoResultados(idsResultados, 360, 360, 3, true, new Font("Arial", Font.BOLD, 11), filtro.getExercicio(), filtro.getAcompReferencia());
			BufferedImage graficoProdutos = GraficoUtils.gerarGraficoProdutos(idsResultados, 360, 360, 6, false, new Font("Arial", Font.BOLD, 11), filtro.getExercicio(), filtro.getAcompReferencia());
			BufferedImage graficoProdutosResultadosPrioritarios = GraficoUtils.gerarGraficoProdutos(idsResultados, 360, 360, 6, true, new Font("Arial", Font.BOLD, 11), filtro.getExercicio(), filtro.getAcompReferencia());
			parametros.put("filtro", filtro.toString());
			parametros.put("graficoResultados", graficoResultados);
			parametros.put("graficoResultadosPrioritarios", graficoResultadosPrioritarios);
			parametros.put("graficoProdutos", graficoProdutos);
			parametros.put("graficoProdutosResultadosPrioritarios", graficoProdutosResultadosPrioritarios);
			
			JRBeanCollectionDataSource listaObjetivosEstrategicos = new JRBeanCollectionDataSource(listaOE);
			
			JasperReport report = JasperService.loadReport(JasperService.class.getResource("/peJasper/" + "/relatorioGerencialBijariMonitoramentos.jasper").getPath());
			parametros.put("caminhoSubRelatorioGerencialEstrategiasMonitoramentos", JasperService.class.getResource("/peJasper/" + "/subRelatorioGerencialEstrategiasMonitoramentos.jasper").getPath());
			parametros.put("caminhoSubRelatorioGerencialEstrategiasResultadosMonitoramentos", JasperService.class.getResource("/peJasper/" + "/subRelatorioGerencialEstrategiasResultadosMonitoramentos.jasper").getPath());
			parametros.put("caminhoSubRelatorioStatusResultadoRelatorioGerencialMonitoramentos", JasperService.class.getResource("/peJasper/" + "/subRelatorioStatusResultadoRelatorioGerencialMonitoramentos.jasper").getPath());
			parametros.put("caminhoSubRelatorioGerencialEstrategiasResultadosProdutosMonitoramentos", JasperService.class.getResource("/peJasper/" + "/subRelatorioGerencialEstrategiasResultadosProdutosMonitoramentos.jasper").getPath());
			parametros.put("caminhoSubRelatorioStatusProdutoRelatorioGerencialMonitoramentos", JasperService.class.getResource("/peJasper/" + "/subRelatorioStatusProdutoRelatorioGerencialMonitoramentos.jasper").getPath());
			parametros.put("caminhoImagemStatusAmarelo", JasperService.class.getResource("/images/" + "/sAmarelo9.png").getPath());
			parametros.put("caminhoImagemStatusVerde", JasperService.class.getResource("/images/" + "/sVerde9.png").getPath()); 
			parametros.put("caminhoImagemStatusVermelho", JasperService.class.getResource("/images/" + "/sVermelho9.png").getPath());
			parametros.put("ecarLogo", JasperService.class.getResource("/images/" + "/ecarLogo_2.png").getPath());
			parametros.put("cruzLogo", JasperService.class.getResource("/images/" + "/logo_small_bijari.gif").getPath());
			parametros.put("msLogo", JasperService.class.getResource("/images/" + "/header_logos_bijari.gif").getPath());
			parametros.put("isGerencial", isGerencial);
			parametros.put("tipoRelatorio", tRel);
			relatorio = JasperService.fillReport(report, parametros, listaObjetivosEstrategicos);
			
			return relatorio;
			
		} catch (ECARException e) {
			e.printStackTrace();
			return null;
		}
	}

}