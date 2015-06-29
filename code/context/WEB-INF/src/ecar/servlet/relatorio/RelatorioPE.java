package ecar.servlet.relatorio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import comum.util.Pagina;

import ecar.dao.AcompReferenciaDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.TipoAcompanhamentoDao;
import ecar.dao.TipoFuncAcompDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.servlet.relatorio.dto.RelatorioDTOFac;
import ecar.servlet.relatorio.dto.RelatorioDTOFacPE2012;
import ecar.util.jasper.JasperService;
import ecar.util.jasper.Report;
import ecar.util.jasper.servlet.RelatorioServlet;

/**
 * 
 * @author Rafael de Morais
 * 
 */
public class RelatorioPE extends RelatorioServlet {

	private static final long serialVersionUID = 2070208791792650418L;
	
	private AcompReferenciaDao acompReferenciaDao;
	
	private String mesRef;
	private AcompReferenciaAref ciclo;
	private List<String> listCiclos;
	
	@Override
	public void doRelatorioGet(HttpServletRequest request,
			HttpServletResponse response) {
		SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
		RelatorioDTOFac dtoFac = null;
		RelatorioDTOFacPE2012 dtoFacPe = null;
		List<?> listDTO = null;
		Report report = null;
		
		String pe = Pagina.getParamStr(request, "pe");
		try {
			/*
			if(pe.equals("1")) {
				dtoFacPe = new RelatorioDTOFacPE2012(request, response);
				listDTO = dtoFacPe.dtoRedePlanoAcao(seguranca.getGruposAcesso(), seguranca.getUsuario());
				report = Report.REDE_PLANO_ACAO_PE_JASPER;
			}else if(pe.equals("2")) {
				dtoFac = new RelatorioDTOFac(request);
				listDTO = dtoFac.dtoRedePlanoAcao(seguranca.getGruposAcesso(), seguranca.getUsuario());
				report = Report.REDE_PLANO_ACAO_JASPER;
			}
			*/
			dtoFac = new RelatorioDTOFac(request);
			listDTO = dtoFac.dtoRedePlanoAcao(seguranca.getGruposAcesso(), seguranca.getUsuario());
			report = Report.REDE_PLANO_ACAO_JASPER;
			String formatoRel = Pagina.getParamStr(request, "f");
			if(formatoRel.equals("1")) {
				buildRel(response, listDTO, report);
			}else if(formatoRel.equals("2")) {
				buildRelXLS(response, listDTO, report);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ECARException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Método de submit do relatório da página relAcompFormato.jsp
	 * 
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public void doRelatorioPost(HttpServletRequest req, HttpServletResponse resp) {

		RelatorioDTOFac dtoFac = new RelatorioDTOFac(req);
		
		acompReferenciaDao = new AcompReferenciaDao(req);
		
		String arisSelecionados = Pagina.getParamStr(req, "arisSelecionados");
		String opcaoModelo = Pagina.getParamStr(req, "opcaoModelo");
		mesRef = Pagina.getParamStr(req, "mesReferencia");
		String 	formatoRelatorio = req.getParameter("formatoRelatorio");
		
		try {
			ciclo = ((AcompReferenciaAref)acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(mesRef)));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (ECARException e1) {
			e1.printStackTrace();
		}
		
		
		AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(req);
		TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(req);

		List codArisSelecionados = new ArrayList();

		if (!"".equals(arisSelecionados)) {
			String[] codAris = arisSelecionados.split(";");
			for (int i = 0; i < codAris.length; i++) {
				if (!"".equals(codAris[i]) && !";".equals(codAris[i])) {
					codArisSelecionados.add(Long.valueOf(codAris[i]));
				}
			}
		}
		
		try {

			TipoFuncAcompDao tfDao = new TipoFuncAcompDao(req);
			//LOCAL
			//TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa)tfDao.buscar(TipoFuncAcompTpfa.class, 2L);
			
			//REMOTO 
			//TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa)tfDao.buscar(TipoFuncAcompTpfa.class, 6L);
			
			/* Altera��o feita para comportar a mudan�a de estruturas (PRODUTO -> RESULTADO) */
			TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa)tfDao.buscar(TipoFuncAcompTpfa.class, 3L);
			
			TipoAcompanhamentoTa tipoAcomp = null;
			List codIetts = new ArrayList();
			List listArel = new ArrayList();
			
			
			if(!opcaoModelo.equals("ECAR-001C")) {
				tipoAcomp = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, 
						Long.valueOf(Pagina.getParam(req, "codTipoAcompanhamento")));
				
				codIetts = acompReferenciaItemDao.getCodIettsFromAris(arisSelecionados);
				
				listCiclos = new ArrayList<String>();
				
				listArel = acompReferenciaItemDao.getUltimosAcompanhamentos(
						codIetts, 
					 	tipoAcomp,
					 	3,		
						3,
						ciclo,
						listCiclos);	
			}
			
			if(formatoRelatorio.equalsIgnoreCase("PDF")){
				if(opcaoModelo.equals("ECAR-001A")) {
					buildRel(resp, dtoFac.dtoSituacaoProdutos(listArel, tpfa, opcaoModelo,listCiclos), Report.SITUACAO_PRODUTO_JASPER);
				}else if(opcaoModelo.equals("ECAR-001C")){
					List<AcompReferenciaItemAri> itens = montaListaReferenciasAgrupadas(codArisSelecionados, req, acompReferenciaItemDao);
					buildRel(resp, dtoFac.dtoCadernoPE(itens, tpfa), Report.CADERNO_PE_JASPER);
				}else if(opcaoModelo.equals("ECAR-001D")) {
					buildRel(resp, dtoFac.dtoSituacaoProdutos(listArel, tpfa, opcaoModelo,listCiclos), Report.SIT_PROD_PARECER_JASPER);
				}else if(opcaoModelo.equals("ECAR-001E")) {
					buildRel(resp, dtoFac.dtoSituacaoProdutos(listArel, tpfa, opcaoModelo,listCiclos), Report.MONITORAMENTO_CICLO_JASPER);
				}else if(opcaoModelo.equals("ECARM-002A")) {
					List<AcompReferenciaItemAri> itens = montaListaReferenciasAgrupadas(codArisSelecionados, req, acompReferenciaItemDao);
					buildRel(resp, dtoFac.dtoRedesCiclo(itens, tpfa, listCiclos), Report.REDE_CICLO_JASPER);
				}else if(opcaoModelo.equals("ECARM-002B")) {
					List<AcompReferenciaItemAri> itens = montaListaReferenciasAgrupadas(codArisSelecionados, req, acompReferenciaItemDao);
					buildRel(resp, dtoFac.dtoRedesCicloSimples(itens, tpfa, listCiclos), Report.REDE_CICLO_SIMPLES_JASPER);
				}
			}else if(formatoRelatorio.equalsIgnoreCase("XLS")){
				if(opcaoModelo.equals("ECAR-001A")) {
					buildRelXLS(resp, dtoFac.dtoSituacaoProdutos(listArel, tpfa, opcaoModelo,listCiclos), Report.SITUACAO_PRODUTO_JASPER);
				}else if(opcaoModelo.equals("ECAR-001C")){
					List<AcompReferenciaItemAri> itens = montaListaReferenciasAgrupadas(codArisSelecionados, req, acompReferenciaItemDao);
					buildRelXLS(resp, dtoFac.dtoCadernoPE(itens, tpfa), Report.CADERNO_PE_JASPER);
				}else if(opcaoModelo.equals("ECAR-001D")) {
					buildRelXLS(resp, dtoFac.dtoSituacaoProdutos(listArel, tpfa, opcaoModelo,listCiclos), Report.SIT_PROD_PARECER_JASPER);
				}else if(opcaoModelo.equals("ECAR-001E")) {
					buildRelXLS(resp, dtoFac.dtoSituacaoProdutos(listArel, tpfa, opcaoModelo,listCiclos), Report.MONITORAMENTO_CICLO_JASPER);
				}else if(opcaoModelo.equals("ECARM-002A")) {
					List<AcompReferenciaItemAri> itens = montaListaReferenciasAgrupadas(codArisSelecionados, req, acompReferenciaItemDao);
					buildRelXLS(resp, dtoFac.dtoRedesCiclo(itens, tpfa, listCiclos), Report.REDE_CICLO_JASPER);
				}else if(opcaoModelo.equals("ECARM-002B")) {
					List<AcompReferenciaItemAri> itens = montaListaReferenciasAgrupadas(codArisSelecionados, req, acompReferenciaItemDao);
					buildRelXLS(resp, dtoFac.dtoRedesCicloSimples(itens, tpfa, listCiclos), Report.REDE_CICLO_SIMPLES_JASPER);
				}
			}
			
			
			
		} catch (ECARException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void buildRel(HttpServletResponse resp, List<?> listDTO, Report tRep) throws IOException {
		
		JasperReport report = JasperService.loadReport(JasperService.class.getResource("/peJasper/" 
				+ tRep.getReportName()).getPath());
		
		JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(listDTO);
		Map<String, String> param = new HashMap<String, String>();
		
		if(listCiclos != null) {
			for (int i = 0;  i < listCiclos.size(); i++) {
				String p = "ciclo"+(i+1);
				param.put(p, listCiclos.get(i));
			}			
		}
		
		if(ciclo != null) {
			param.put("ciclo", ciclo.getNomeAref());
		}
		param.put("imgMS", JasperService.class.getResource("/images/ms_rodape.gif").getPath());
		param.put("ecarLogo", JasperService.class.getResource("/images/ecarLogo_2.png").getPath());
		param.put("brasao", JasperService.class.getResource("/images/brasao.jpg").getPath());
		
		switch (tRep) {
		case SITUACAO_PRODUTO_JASPER:
			param.put("srIndicadorPath", JasperService.class.getResource("/peJasper/" + 
					Report.SITUACAO_PRODUTO_SR_INDICADOR_JASPER.getReportName()).getPath());
			break;
			
		case SIT_PROD_PARECER_JASPER:
			param.put("srIndicadores", JasperService.class.getResource("/peJasper/" +
					Report.REDE_CICLO_IND_JASPER.getReportName()).getPath());
			break;
			
		case CADERNO_PE_JASPER:
			param.put("srEstrategia", JasperService.class.getResource("/peJasper/" + 
					Report.CADERNO_PE_SR_ESTRATEGIA_JASPER.getReportName()).getPath());
			
			param.put("srProduto", JasperService.class.getResource("/peJasper/" + 
					Report.CADERNO_PE_SR_PRODUTO_JASPER.getReportName()).getPath());
			
			param.put("srIndicador", JasperService.class.getResource("/peJasper/" + 
					Report.CADERNO_PE_SR_INDICADOR_JASPER.getReportName()).getPath());
			
			param.put("srGrafPath", JasperService.class.getResource("/peJasper/" + 
					Report.CADERNO_PE_SR_INDICADOR_GRAF_JASPER.getReportName()).getPath());
			break;
		case REDE_CICLO_JASPER:
			param.put("srIndRedes", JasperService.class.getResource("/peJasper/" + 
					Report.REDE_CICLO_IND_JASPER.getReportName()).getPath());
			param.put("srPlanoAcao", JasperService.class.getResource("/peJasper/" + 
					Report.REDE_CICLO_PT_JASPER.getReportName()).getPath());
			break;
		case REDE_CICLO_SIMPLES_JASPER:
			param.put("srIndRedes", JasperService.class.getResource("/peJasper/" + 
					Report.REDE_CICLO_SIMPLES_IND_JASPER.getReportName()).getPath());
			break;
		}
		
		JasperPrint rel = JasperService.fillReport(report, param, beanDS);
		
		byte[] arq = JasperService.geraRelatorioPDF(rel);
		
		this.responseToPDF(resp, arq);
	}
	
	/**
	 * Gera o relatório no formato de arquivo XLS (Excel). Feito a exemplo do método buildRel()
	 * 
	 * @since 15/03/2012
	 * @author fabio.bicalho (fabiosbicalho.contato@gmail.com, fabio.bicalho@cast.com.br)
	 * @param resp Objeto responsável por mandar o relatório para o usuário.
	 * @param listDTO Lista de Objetos a serem listados no relatório.
	 * @param tRep Objeto com as referências locais do relatório .jasper
	 * */
	private void buildRelXLS(HttpServletResponse resp, List<?> listDTO, Report tRep) throws IOException, ECARException,  Exception {
		try {
			JasperReport report = JasperService.loadReport(JasperService.class.getResource("/peJasper/" 
					+ tRep.getReportName()).getPath());
			
			JRBeanCollectionDataSource beanDS = new JRBeanCollectionDataSource(listDTO);
			Map<String, String> param = new HashMap<String, String>();
			
			if(listCiclos != null) {
				for (int i = 0;  i < listCiclos.size(); i++) {
					String p = "ciclo"+(i+1);
					param.put(p, listCiclos.get(i));
				}			
			}
			
			if(ciclo != null) {
				param.put("ciclo", ciclo.getNomeAref());
			}
			param.put("imgMS", JasperService.class.getResource("/images/ms_rodape.gif").getPath());
			param.put("ecarLogo", JasperService.class.getResource("/images/ecarLogo_2.png").getPath());
			param.put("brasao", JasperService.class.getResource("/images/brasao.jpg").getPath());
			
			switch (tRep) {
			case SITUACAO_PRODUTO_JASPER:
				param.put("srIndicadorPath", JasperService.class.getResource("/peJasper/" + 
						Report.SITUACAO_PRODUTO_SR_INDICADOR_JASPER.getReportName()).getPath());
				break;
				
			case SIT_PROD_PARECER_JASPER:
				param.put("srIndicadores", JasperService.class.getResource("/peJasper/" +
						Report.REDE_CICLO_IND_JASPER.getReportName()).getPath());
				break;
				
			case CADERNO_PE_JASPER:
				param.put("srEstrategia", JasperService.class.getResource("/peJasper/" + 
						Report.CADERNO_PE_SR_ESTRATEGIA_JASPER.getReportName()).getPath());
				
				param.put("srProduto", JasperService.class.getResource("/peJasper/" + 
						Report.CADERNO_PE_SR_PRODUTO_JASPER.getReportName()).getPath());
				
				param.put("srIndicador", JasperService.class.getResource("/peJasper/" + 
						Report.CADERNO_PE_SR_INDICADOR_JASPER.getReportName()).getPath());
				
				param.put("srGrafPath", JasperService.class.getResource("/peJasper/" + 
						Report.CADERNO_PE_SR_INDICADOR_GRAF_JASPER.getReportName()).getPath());
				break;
			case REDE_CICLO_JASPER:
				param.put("srIndRedes", JasperService.class.getResource("/peJasper/" + 
						Report.REDE_CICLO_IND_JASPER.getReportName()).getPath());
				break;
			case REDE_CICLO_SIMPLES_JASPER:
				param.put("srIndRedes", JasperService.class.getResource("/peJasper/" + 
						Report.REDE_CICLO_SIMPLES_IND_JASPER.getReportName()).getPath());
				break;
			}
			
			JasperPrint rel = JasperService.fillReport(report, param, beanDS);
			
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			
			JRXlsExporter exporterXLS = new JRXlsExporter();
	        exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, rel);
	        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, baos);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	        exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	        exporterXLS.exportReport(); 
	        
	        byte[] bytes = baos.toByteArray();
	        
	        this.responseToXLS(resp, bytes);
	        

		} catch ( JRException e) {
			throw new ECARException("Erro ao gerar o relatório. MESSAGEM: " + e.getMessage());
		} catch (IOException e) {
			throw new ECARException("Erro ao mostrar o relatório. MESSAGEM: " + e.getMessage());
		} catch (Exception e) {
			throw new ECARException("Erro. MESSAGEM: " + e.getMessage());
		}
				
	}
	

	/**
	 * Monta a lista de refrencias agrupadas a partir dos acompanhamentos
	 * selecionados na tela.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param codArisSelecionados
	 * @return referenciasAgrupadas
	 * @throws ECARException
	 */
	@SuppressWarnings("unchecked")
	public List montaListaReferenciasAgrupadas(List codArisSelecionados,
			HttpServletRequest request,
			AcompReferenciaItemDao acompReferenciaItemDao) throws ECARException {

		List referenciasAgrupadas = new ArrayList();

		try {
			if (codArisSelecionados != null && !codArisSelecionados.isEmpty()) {
				Iterator itCodArisSelecionados = codArisSelecionados.iterator();

				while (itCodArisSelecionados.hasNext()) {
					Long codAri = (Long) itCodArisSelecionados.next();
					AcompReferenciaItemAri ari = (AcompReferenciaItemAri) acompReferenciaItemDao
							.buscar(AcompReferenciaItemAri.class, codAri);
					if (ari != null
							&& !referenciasAgrupadas.contains(ari
									.getAcompReferenciaAref())) {
						referenciasAgrupadas.add(ari);
					}

				}
			}

			return referenciasAgrupadas;

		} catch (Exception e) {
			throw new ECARException(
					"Erro na cria��o do relat�rio: Lista de Acompanhamento Selecionado - "
							+ e.getMessage());
		}
	}
}
