package ecar.servlet.relatorio;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ecar.dao.ItemEstruturaDao;
import ecar.pojo.acompanhamentoEstrategico.Estrategia;
import ecar.pojo.acompanhamentoEstrategico.Indicador;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.pojo.acompanhamentoEstrategico.Resultado;
import ecar.util.GraficoUtils;
import ecar.util.jasper.JasperService;

public class RelatorioPE2012 {
	
	/**
	 * Par�metros a serem passados ao relat�rio jasper. A chave do mapa � o nome do par�metro declarado no relat�rio.
	 * */
	Map<String, Object> param = new HashMap<String, Object>();

	public JasperPrint gerarRelatorioExecutivo() {
		JasperPrint relatorio = null;
		List<Integer> codigos = new ArrayList<Integer>();
		List<String> etiquetas = new ArrayList<String>();
		List<String> prioritarios = new ArrayList<String>();
		param.clear();
		try {
			ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
			
			List<ObjetivoEstrategico> listaOE = itemEstruturaDao.listarObjetivoEstrategicoFiltro(codigos, etiquetas, prioritarios, null, null, null, false, null);
			List<Long> idsResultados = GraficoUtils.extrairIdsResultados(listaOE);
			BufferedImage graficoResultadosRelatorioExecutivo = GraficoUtils.gerarGraficoResultados(idsResultados, 277, 105, 3, false, new Font("Arial", Font.BOLD, 8), null, null);
			BufferedImage graficoRelatorioExecutivoResultadosPrioritarios = GraficoUtils.gerarGraficoResultados(idsResultados, 278, 103, 3, true, new Font("Arial", Font.BOLD, 8), null, null);


			param.put("graficoRelatorioExecutivoResultados", graficoResultadosRelatorioExecutivo);
			param.put("graficoRelatorioExecutivoResultadosPrioritarios", graficoRelatorioExecutivoResultadosPrioritarios);
			param.put("cruzLogo", JasperService.class.getResource("/images/" + "/logo_small_bijari.gif").getPath());
			param.put("msLogo", JasperService.class.getResource("/images/" + "/header_logos_bijari.gif").getPath());
			param.put("ecarLogo", JasperService.class.getResource("/images/" + "/ecarLogo_2.png").getPath());

			JasperReport report = JasperService.loadReport(JasperService.class.getResource("/peJasper/"	+ "/relBijari.jasper").getPath());
					
			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(listaOE);

			
			/* Par�metros */
			param.put("caminho_smileAmarelo", JasperService.class.getResource("/images/" + "/sAmarelo9.png").getPath()); // Caminho para carinha de status amarelo
			param.put("caminho_smileVerde", JasperService.class.getResource("/images/" + "/sVerde9.png").getPath()); // Caminho para carinha de status verde
			param.put("caminho_smileVermelho", JasperService.class.getResource("/images/" + "/sVermelho9.png").getPath()); // Caminho para carinha de status vermelho
			param.put("caminho_smileAzul", JasperService.class.getResource("/images/" + "/sAzul9.png").getPath()); // Caminho para carinha de status azul 
			param.put("caminho_smileBranco", JasperService.class.getResource("/images/" + "/sBranco9.png").getPath()); // Caminho para carinha de status branco
			
			param.put("caminho_relBijari_subOE", JasperService.class.getResource("/peJasper/" + "/relBijari_subOE.jasper").getPath()); // Caminho para sub-relatorio de estrat�gias
			param.put("caminho_relBijari_subOE_subEst", JasperService.class.getResource("/peJasper/" + "/relBijari_subOE_subEst.jasper").getPath()); // Caminho para sub-relatorio de resultados
			param.put("caminho_relBijari_subOE_subEst_subResult", JasperService.class.getResource("/peJasper/"+ "/relBijari_subOE_subEst_subResult.jasper").getPath()); // Caminho para sub-relatorio de status

			relatorio = JasperService.fillReport(report, param, beanDS);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return relatorio;
	}

	public JasperPrint gerarRelatorioOperacionalProdutos(String codOEs, String tipoRelatorioProduto){
		JasperPrint relatorio = null;
		List<Integer> codigos = new ArrayList<Integer>();
		/*
		codigos.add(19);
		codigos.add(18);
		codigos.add(20);
		codigos.add(23); //OE 16
		*/
		//codigos.add(10);//OE 02
		//codigos.add(21);//OE 06
		if(codOEs != null) {
			codigos.addAll(extratCodOE(codOEs));
		}
		List<String> etiquetas = new ArrayList<String>();
		List<String> prioritarios = new ArrayList<String>();
		Map<String, Object> parametros = new HashMap<String, Object>();
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);

		List<ObjetivoEstrategico> listaOE = itemEstruturaDao.listarObjetivoEstrategicoFiltroMaisRapidoEuEspero(codigos, etiquetas, prioritarios, null, null, null, false, null, null, 0, 0L);
		
		JRBeanCollectionDataSource listaObjetivosEstrategicos = new JRBeanCollectionDataSource(listaOE);
		
		JasperReport report = JasperService.loadReport(JasperService.class.getResource("/peJasper/" + "/relatorioOperacionalBijari.jasper").getPath());
		parametros.put("caminhoSubRelEstratOperacional", JasperService.class.getResource("/peJasper/" + "/subRelatorioOperacionalEstrategias.jasper").getPath());
		parametros.put("caminhoSubRelResultOperacional", JasperService.class.getResource("/peJasper/" + "/subRelatorioOperacionalResultado.jasper").getPath());
		parametros.put("subRelOperacionalTitleProd", JasperService.class.getResource("/peJasper/" + "/subRelOperacionalTitleProd.jasper").getPath());
		parametros.put("subRelProdutos", JasperService.class.getResource("/peJasper/" + "/subRelatorioGerencialEstrategiasResultadosProdutos.jasper").getPath());
		parametros.put("ecarLogo", JasperService.class.getResource("/images/" + "/ecarLogo_2.png").getPath());
		parametros.put("cruzLogo", JasperService.class.getResource("/images/" + "/logo_small_bijari.gif").getPath());
		parametros.put("msLogo", JasperService.class.getResource("/images/" + "/header_logos_bijari.gif").getPath());
		relatorio = JasperService.fillReport(report, parametros, listaObjetivosEstrategicos);
		
		return relatorio;
	}

	public JasperPrint gerarRelatorioIndicadores(){
		param.clear();
		JasperPrint relatorio = null;
		List<Integer> codigos = new ArrayList<Integer>();
		List<String> etiquetas = new ArrayList<String>();
		List<String> prioritarios = new ArrayList<String>();
		try {
			ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
			List<ObjetivoEstrategico> listaOE = itemEstruturaDao.listarObjetivoEstrategicoFiltroComIndicador(codigos, etiquetas, prioritarios, null, null, null, true, false, false, null, false, null, null);
			List<ObjetivoEstrategico> listaOEGerais = new ArrayList<ObjetivoEstrategico>();
			
			/*
			 * Caminhos para as imagens
			 * */
			param.put("caminho_smileAmarelo", JasperService.class.getResource("/images/" + "/sAmarelo9.png").getPath()); 
			param.put("caminho_smileVerde", JasperService.class.getResource("/images/" + "/sVerde9.png").getPath());
			param.put("caminho_smileVermelho", JasperService.class.getResource("/images/" + "/sVermelho9.png").getPath()); 
			param.put("caminho_smileAzul", JasperService.class.getResource("/images/" + "/sAzul9.png").getPath());  
			param.put("caminho_smileBranco", JasperService.class.getResource("/images/" + "/sBranco9.png").getPath());
			param.put("cruzLogo", JasperService.class.getResource("/images/" + "/logo_small_bijari.gif").getPath());
			param.put("msLogo", JasperService.class.getResource("/images/" + "/header_logos_bijari.gif").getPath());
			param.put("ecarLogo", JasperService.class.getResource("/images/" + "/ecarLogo_2.png").getPath());
			
			/*
			 * Caminhos para os relat�rios jasper
			 * */
			param.put("listaOE", listaOE);
			param.put("listaOEGerais", listaOEGerais);
			param.put("caminho_indicadores_subOE", JasperService.class.getResource("/peJasper/" + "/indicadores_subOE.jasper").getPath()); 
			param.put("caminho_indicadores_subOE_subEstrat", JasperService.class.getResource("/peJasper/" + "/indicadores_subOE_subEstrat.jasper").getPath()); 
			param.put("caminho_indicadores_subOE_subEstrat_subResul", JasperService.class.getResource("/peJasper/" + "/indicadores_subOE_subEstrat_subResul.jasper").getPath()); 
			param.put("caminho_indicadores_subOE_subEstrat_subResul_subIndi", JasperService.class.getResource("/peJasper/" + "/indicadores_subOE_subEstrat_subResul_subIndi.jasper").getPath()); 
			param.put("caminho_relBijari_subOE_subEst_subResult", JasperService.class.getResource("/peJasper/" + "/relBijari_subOE_subEst_subResult.jasper").getPath()); 
			param.put("caminho_indicadores_subResultados_subIndicadores", JasperService.class.getResource("/peJasper/" + "/indicadores_subResultados_subIndicadores.jasper").getPath()); 
			
			
			/* Gerando dados falsos para metas e valor realizado */
			for (ObjetivoEstrategico objetivoEstrategico : listaOE) {
				for (Estrategia estrategia : objetivoEstrategico.getEstrategias()) {
					for (Resultado  resultado: estrategia.getResultados()) {
						for (Indicador  indicador: resultado.getIndicador()) {
							indicador.setMeta2012("1000000");
							indicador.setMeta2013("1000000");
							indicador.setMeta2014("1000000");
							indicador.setMeta2015("1000000");
							indicador.setValorRealizado2012("1000000");
							indicador.setValorRealizado2013("1000000");
							indicador.setValorRealizado2014("1000000");
							indicador.setValorRealizado2015("1000000");
						}
						
					}
					
				}
				
			}
			
			JasperReport report = JasperService.loadReport(JasperService.class.getResource("/peJasper/" + "/indicadores.jasper").getPath());
			relatorio = JasperService.fillReport(report, param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return relatorio;
	}
	
	public JasperPrint gerarRelatorioGerencial(String codOEs){
		JasperPrint relatorio = null;
		List<Integer> codigos = new ArrayList<Integer>();
		String filtro = "";
		/*
		codigos.add(19);
		codigos.add(18);
		codigos.add(20);
		codigos.add(23);
		codigos.add(10);//OE 02
		codigos.add(21);//OE 06
		*/
		List<String> etiquetas = new ArrayList<String>();
		List<String> prioritarios = new ArrayList<String>();
		Map<String, Object> parametros = new HashMap<String, Object>();
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);

		if(codOEs != null) {
			codigos.addAll(extratCodOE(codOEs));
		}
		
		List<ObjetivoEstrategico> listaOE = itemEstruturaDao.listarObjetivoEstrategicoFiltro(codigos, etiquetas, prioritarios, null, null, null, false, null);
		List<Long> idsResultados = GraficoUtils.extrairIdsResultados(listaOE);
		BufferedImage graficoResultados = GraficoUtils.gerarGraficoResultados(idsResultados, 360, 360, 3, false, new Font("Arial", Font.BOLD, 11), null, null);
		BufferedImage graficoResultadosPrioritarios = GraficoUtils.gerarGraficoResultados(idsResultados, 360, 360, 3, true, new Font("Arial", Font.BOLD, 11), null, null);
		BufferedImage graficoProdutos = GraficoUtils.gerarGraficoProdutos(idsResultados, 360, 360, 6, false, new Font("Arial", Font.BOLD, 11), null, null);
		BufferedImage graficoProdutosResultadosPrioritarios = GraficoUtils.gerarGraficoProdutos(idsResultados, 360, 360, 6, true, new Font("Arial", Font.BOLD, 11), null, null);
		parametros.put("filtro", filtro);
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
		relatorio = JasperService.fillReport(report, parametros, listaObjetivosEstrategicos);
		
		return relatorio;
	}
	
	public List<Integer> extratCodOE(String codOEs) {
		List<Integer> codsInt = new ArrayList<Integer>();
		String[] cods = codOEs.split(",");
		for (String codS : cods) {
			codsInt.add(Integer.parseInt(codS));
		}
		return codsInt;
	}
}
