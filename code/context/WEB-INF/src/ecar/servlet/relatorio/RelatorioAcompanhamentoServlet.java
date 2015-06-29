package ecar.servlet.relatorio;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.jasperreports.engine.JasperPrint;
import ecar.bean.FiltroPesquisaMonitoramento;
import ecar.enumerator.TipoRelatorioAcompanhamento;
import ecar.login.SegurancaECAR;
import ecar.pojo.ItemEstruturaIett;
import ecar.relatorio.RelatorioExcelMS;
import ecar.servico.RelatorioAcompanhamentoService;
import ecar.servlet.relatorio.dto.SecretariaDTO;
import ecar.util.ExcelUtil;
import ecar.util.ExcelUtilIndicador;
import ecar.util.jasper.JasperService;
import ecar.util.jasper.servlet.RelatorioServlet;

public class RelatorioAcompanhamentoServlet extends RelatorioServlet{

	private static final long serialVersionUID = 7966319780490446400L;
	
	@Override
	public void doRelatorioGet(HttpServletRequest request, HttpServletResponse response) {
		RelatorioAcompanhamentoService relatorioAcompanhamentoService = new RelatorioAcompanhamentoService();
		List<ItemEstruturaIett> listaOpcoesObjetivosEstrategicos = relatorioAcompanhamentoService.listarOpcoesPickListObjetivosEstrategicos();
		List<TipoRelatorioAcompanhamento> tiposRelatoriosAcompanhamento = Arrays.asList(TipoRelatorioAcompanhamento.values());
		List<SecretariaDTO> listaSecretarias = relatorioAcompanhamentoService.listarSecretarias();
		request.setAttribute("listaOpcoesObjetivosEstrategicos", listaOpcoesObjetivosEstrategicos);
		request.setAttribute("tiposRelatoriosAcompanhamento", tiposRelatoriosAcompanhamento);
		request.setAttribute("listaSecretarias", listaSecretarias);
		try {
			request.getRequestDispatcher("/relAcompanhamento/emitirRelatorioAcompanhamento.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void doRelatorioPost(HttpServletRequest request, HttpServletResponse response){
		
		String metodo 						 = request.getParameter("metodo");
		String [] objetivosSelecionados      = request.getParameterValues("objetivosSelecionados[]");
		String [] secretariasSelecionadas    = request.getParameterValues("secretariasSelecionadas");
		String [] etiquetasSelecionadas      = request.getParameterValues("etiquetas");
		String [] statusSelecionados         = request.getParameterValues("status");
		String prioritario                   = request.getParameter("prioritario");
		String ordenarPorStatus              = request.getParameter("ordenarPorStatus");
		String codigoRelatorio               = request.getParameter("codigoRelatorio");
		SegurancaECAR seguranca              = (SegurancaECAR) request.getSession().getAttribute("seguranca");
		String dataInicial		             = request.getParameter("dataInicial");
		String dataFinal					 = request.getParameter("dataFinal");
		String comIndicadores				 = request.getParameter("comIndicadores");
		String exercicio					 = request.getParameter("exercicio");
		String somenteREM					 = request.getParameter("somenteREM");
		String acompReferencia				 = request.getParameter("acompReferencia");

		try{
			RelatorioAcompanhamentoService relatorioAcompanhamentoService = new RelatorioAcompanhamentoService();
			RelatorioExcelMS relatorioExcelMS = new RelatorioExcelMS();

			FiltroPesquisaMonitoramento filtroPesquisaMonitoramento = relatorioAcompanhamentoService.preencherFiltroPesquisaMonitoramento(objetivosSelecionados, etiquetasSelecionadas, statusSelecionados, secretariasSelecionadas, prioritario, codigoRelatorio, ordenarPorStatus, seguranca,
					dataInicial, dataFinal, comIndicadores, exercicio, somenteREM, acompReferencia);

			if (metodo.equalsIgnoreCase("excel")) {
				ExcelUtil excelUtil = new ExcelUtil(request);
				//excelUtil.testConsolidadoObjetivoEstrategicoFiltroExcell(response, filtroPesquisaMonitoramento);
				HSSFWorkbook resultWorkbook = relatorioExcelMS.exportarRelatorioExcel(filtroPesquisaMonitoramento);
				
				excelUtil.exportaServletExcel(response, resultWorkbook);
			} else if (metodo.equalsIgnoreCase("excel_indicador")) {
				ExcelUtilIndicador excelUtilIndicador = new ExcelUtilIndicador(request);
				excelUtilIndicador.objetivoEstrategicoFiltroExcellIndicador(response, filtroPesquisaMonitoramento);
			} else {

				JasperPrint relatorio = relatorioAcompanhamentoService.emitirRelatorioAcompanhamento(filtroPesquisaMonitoramento);

				if (relatorio != null) {
					byte[] arquivo = JasperService.geraRelatorioPDF(relatorio);
					this.responseToPDF(response, arquivo);
				}
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
