package ecar.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFBorderFormatting;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.jfree.util.Log;

import ecar.bean.FiltroPesquisaMonitoramento;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.SiteDao;
import ecar.dao.UsuarioDao;
import ecar.exception.ECARException;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.acompanhamentoEstrategico.Acao;
import ecar.pojo.acompanhamentoEstrategico.Estrategia;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.pojo.acompanhamentoEstrategico.Produto;
import ecar.pojo.acompanhamentoEstrategico.Resultado;
import ecar.pojo.acompanhamentoEstrategico.ResultadoComparator;
import ecar.servico.RelatorioAcompanhamentoService;
import ecar.servlet.relatorio.dto.SecretariaDTO;

public class ExcelUtil extends ItemEstruturaDao implements Serializable {

	private HttpServletRequest request;
	private Logger logger = Logger.getLogger(this.getClass());

	public ExcelUtil() {
		super(null);
	}
	
	public ExcelUtil(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Verifica se existe uma determinada secretaria no filtro proveniente da
	 * consulta do relatório. <br/>
	 * Exemplo:
	 * <p>
	 * verificaSecretaria(filtro, secretariaDTO);
	 * </p>
	 * 
	 * @author gustavo.edin
	 * @since 2013-08-09
	 * @param filtro
	 * @param secretariaDTO
	 * @return boolean
	 * 
	 */
	protected boolean verificaSecretaria(FiltroPesquisaMonitoramento filtro, SecretariaDTO secretariaDTO) {
		for (long codigoSecretaria : filtro.getSecretariasSelecionadas()) {
			for (Long codigo : secretariaDTO.getCodigosSecretarias()) {
				if (codigoSecretaria == codigo) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Gera um relatório no formato Excel passando como parâmetro o filtro da página de pesquisa da Servlet relatorioAcompanhamentoServlet <br/>
	 * <strong>Exemplo:</strong>
	 * <p>
	 * testConsolidadoObjetivoEstrategicoFiltroExcell(response, filtro);
	 * </p> 
	 * @author gustavo.edin
	 * @since 2013-08-21
	 * @param response
	 * @param filtro
	 */
	public void testConsolidadoObjetivoEstrategicoFiltroExcell(HttpServletResponse response, FiltroPesquisaMonitoramento filtro) {

		// Cria o serviço
		RelatorioAcompanhamentoService relatorioAcompanhamentoService = new RelatorioAcompanhamentoService();

		// Recupera a Lista de todas as Secretarias
		List<SecretariaDTO> listaSecretarias = relatorioAcompanhamentoService.listarSecretarias();

		// Cria uma planilha do excel
		HSSFWorkbook workbook = new HSSFWorkbook();

		// Recupera a lista de todos os objetivos estratégicos selecionados na página de pesquisa
		List<ObjetivoEstrategico> lista = listarObjetivoEstrategicoPorFiltroSecretaria(null, filtro);

		// Cria variaveis de controle de tempo.
		long tempoInicial = 0L;
		long tempoFinal = 0L;

		// Se o filtro conter uma ou mais secretarias selecionadas
		if (filtro.getSecretariasSelecionadas().size() > 0) {
			for (SecretariaDTO secretariaDTO : listaSecretarias) {
				// captura a lista de acordo com o filtro
				// se houver secretaria no filtro entao realiza o processo todo
				if (verificaSecretaria(filtro, secretariaDTO)) {
					tempoInicial = System.currentTimeMillis();
					// lista =
					// listarObjetivoEstrategicoPorFiltroSecretaria(secretariaDTO,
					// filtro);
					criarAbaExcel(workbook, secretariaDTO, lista);
					tempoFinal = System.currentTimeMillis();
					this.logger.debug(String.format("Tempo Estimado Secretaria %s: %.2f segundos", secretariaDTO.getSiglaSecretaria(), (tempoFinal - tempoInicial) / 1000d));
				}
			}
		} else {
			// lista = listarObjetivoEstrategicoPorFiltroSecretaria(null,
			// filtro);
			for (SecretariaDTO secretariaDTO : listaSecretarias) {
				tempoInicial = System.currentTimeMillis();
				criarAbaExcel(workbook, secretariaDTO, lista);
				tempoFinal = System.currentTimeMillis();
				this.logger.debug(String.format("Tempo Estimado Secretaria %s: %.2f segundos", secretariaDTO.getSiglaSecretaria(), (tempoFinal - tempoInicial) / 1000d));
			}
		}

		exportaServletExcel(response, workbook);

	}

	/**
	 * Recupera uma Lista de Objetivo(s) Estratégico(s) de acordo com a Secretaria enviada. <br/>
	 * Exemplo:
	 * <strong>listarObjetivoEstrategicoPorFiltroSecretaria(secretariaDTO, filtro);</strong>
	 * @author gustavo.edin
	 * @since 2013-08-21
	 * @param secretariaDTO
	 * @param filtro
	 * @return Lista ObjetivoEstrategico
	 */
	protected List<ObjetivoEstrategico> listarObjetivoEstrategicoPorFiltroSecretaria(SecretariaDTO secretariaDTO, FiltroPesquisaMonitoramento filtro) {
//		ItemEstruturaDao dao = new ItemEstruturaDao(request);
		SiteDao dao = new SiteDao(request);
		List<ObjetivoEstrategico> lista = null;
		List<Long> secretarias = new ArrayList<Long>();
		if (secretariaDTO != null) {
			secretarias = secretariaDTO.getCodigosSecretarias();
		}
		try {
			lista = dao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(
					filtro.getCodigosObjetivosEstrategicos(), 	// List<Integer> codigoObjetivoEstrategico
					filtro.getEtiquetasSelecionadas(), 			// List<String> etiqueta
					null, 										// List<String> etiquetaPrioritaria
					filtro.getStatusSelecionados(),				// List<Integer> resultadoStatus
					filtro.getCodigoUsuario(), 					// Long codigoUsuario					 
					filtro.getCodigosUsuariosPermissao(),		// List<Long> gruposUsuarios
					filtro.isComIndicadores(), 					// boolean isIndicador
					false, 										// boolean apenasParecer
					filtro.isPrioritario(), 					// boolean prioritario
					secretarias, 								// List<Long> secretarias
					false, 										// boolean relEncaminhamento
					filtro.getDataInicial(), 					// Date dataInicial
					filtro.getDataFinal(), 						// Date dataFinal
					filtro.getExercicio(), 						// Long exercicio
					filtro.isSomenteREM(), 						// boolean somenteREM
					filtro.getAcompReferencia(), 				// Long acompReferencia
					true, 										// boolean isDetalhamento
					0
			);
			
			 //ordena a lista se for o caso
			if (filtro.isOrdenarPorStatus()) {
				for (ObjetivoEstrategico objetivoEstrategico : lista) {
					for (Estrategia estrategia : objetivoEstrategico.getEstrategias()) {
						java.util.Collections.sort(estrategia.getResultados(), new ResultadoComparator());
					}
				}
			}
			
		} catch (ECARException e) {
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Separa a Lista de Resultados por Secretaria provenientes da Lista de Resultados da Estratégia do Objetivo Estratégico.
	 * Exemplo:
	 * <strong>separaResultadoPorSecretaria(secretariaDTO, resultadoList);</strong>
	 * @author gustavo.edin
	 * @since 2013-08-21	 
	 * @param secretariaDTO
	 * @param resultadoList
	 * @return Lista Resultado
	 */
	protected List<Resultado> separaResultadoPorSecretaria(SecretariaDTO secretariaDTO, List<Resultado> resultadoList) {
		ItemEstruturaDao dao = new ItemEstruturaDao(request);
		List<Resultado> resultados = new ArrayList<Resultado>();
		for (Resultado resultado : resultadoList) {
			ItemEstruturaIett itemEstrutura = null;
			if (resultado.responsavel != null) {
				try {
					itemEstrutura = (ItemEstruturaIett) dao.buscar(ItemEstruturaIett.class, resultado.getCodigo());
					if (itemEstrutura.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null) {
						if (secretariaDTO.getSiglaSecretaria().equals(itemEstrutura.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg())) {
							resultados.add(resultado);
						}
					}
				} catch (ECARException e) {
					e.printStackTrace();
				}
			}
		}
		return resultados;
	}

	/**
	 * Cria uma Aba na Planilha do Excel a ser gerada.<br/>
	 * Exemplo:
	 * <strong>criarAbaExcel(workbook, secretariaDTO, lista);</strong>  
	 * @author gustavo.edin
	 * @since 2013-08-21	 
	 * @param workbook
	 * @param secretariaDTO
	 * @param lista
	 */
	protected void criarAbaExcel(HSSFWorkbook workbook, SecretariaDTO secretariaDTO, List<ObjetivoEstrategico> lista) {
		populaPlanilhaPorSecretaria(workbook, secretariaDTO, lista);
	}

	/**
	 * Exporta a planilha com o nome de <strong>relatorioAcompanhamento.xls</strong> na resposta da Servlet (abertura em tela)<br/>
	 * Exemplo:
	 * <strong>exportaServletExcel(response, workbook);</strong>
	 * @author gustavo.edin
	 * @since 2013-08-21	 
	 * @param response
	 * @param workbook
	 */
	public void exportaServletExcel(HttpServletResponse response, HSSFWorkbook workbook) {
		OutputStream os = null;
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=relatorioAcompanhamento.xls");
			os = response.getOutputStream();
			workbook.write(os);
			os.close();
			Log.info("Processo finalizado");
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo Não encontrado.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erro ao Gerar arquivo do excel.");
			e.printStackTrace();
		}
	}

	/**
	 * Preenche o início de cada cabeçalho das Abas geradas.<br/>
	 * Exemplo:
	 * <strong>preencheCabecalho(sheet);</strong>
	 * @author gustavo.edin
	 * @since 2013-08-21	 
	 * @param sheet
	 */
	protected void preencheCabecalho(HSSFSheet sheet) {

		HSSFRow row = sheet.createRow(1);
		short cor = HSSFColor.GREY_50_PERCENT.index;
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 17));
		row.createCell(0).setCellValue("OE");
		row.createCell(1).setCellValue("ES");
		row.createCell(2).setCellValue("R");
		row.createCell(3).setCellValue("Descri\u00e7\u00f5es");
		row.createCell(18).setCellValue("REM");
		row.createCell(19).setCellValue("Indicador");
		row.createCell(20).setCellValue("Situa\u00e7\u00e3o");
		row.createCell(21).setCellValue("Respons\u00e1vel");
		row.createCell(22).setCellValue("E-mail");
		row.createCell(23).setCellValue("\u00c1rea Respons\u00e1vel");
		row.createCell(24).setCellValue("Codigo IETTIR");

		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(19);
		sheet.autoSizeColumn(18);
		sheet.autoSizeColumn(20);
		sheet.autoSizeColumn(21);
		sheet.autoSizeColumn(22);
		sheet.autoSizeColumn(23);
		sheet.autoSizeColumn(24);

		setCellStyleBackgroundColor(row.getCell(0), cor);
		setCellStyleBackgroundColor(row.getCell(1), cor);
		setCellStyleBackgroundColor(row.getCell(2), cor);
		setCellStyleBackgroundColor(row.getCell(3), cor);
		setCellStyleBackgroundColor(row.getCell(18), cor);
		setCellStyleBackgroundColor(row.getCell(19), cor);
		setCellStyleBackgroundColor(row.getCell(20), cor);
		setCellStyleBackgroundColor(row.getCell(21), cor);
		setCellStyleBackgroundColor(row.getCell(22), cor);
		setCellStyleBackgroundColor(row.getCell(23), cor);
		setCellStyleBackgroundColor(row.getCell(24), cor);

	}

	
	/**
	 * Recupera a Secretaria Responsável.<br/>
	 * Exemplo:
	 * <strong>getItemEstruturaByResultado(resultado, dao);</strong>
	 * @author gustavo.edin
	 * @since 2013-08-21	 
	 * @param resultado
	 * @param dao
	 * @return
	 */
	protected ItemEstruturaIett getItemEstruturaByResultado(Resultado resultado, ItemEstruturaDao dao){
		
		
		ItemEstruturaIett itemEstrutura = null;

		if (resultado != null) {
			try {
				itemEstrutura = (ItemEstruturaIett) dao.buscar(ItemEstruturaIett.class, resultado.getCodigo());
			} catch (ECARException e) {
				e.printStackTrace();
			}
		}
		
		return itemEstrutura;
		
	}

	/**
	 * Recupera o Email do Responsável.<br/>
	 * Exemplo:
	 * <strong>getEmailByResponsavel(itemEstruturaIett);</strong>
	 * @author gustavo.edin
	 * @since 2013-08-21	 
	 * @param resultado
	 * @param dao
	 * @return
	 */
	protected String getEmailByResponsavel(String nomeResponsavel, HttpServletRequest request){
		
		String emailResponsavel = null;
		UsuarioDao usuarioDao = new UsuarioDao(request);
		
		UsuarioUsu usuario = null;
		try {
			usuario = (UsuarioUsu) usuarioDao.getUsuarioUsuByNome(nomeResponsavel);
		} catch (ECARException e) {
			e.printStackTrace();
		}
		if(usuario != null){
			emailResponsavel = usuario.getEmail1Usu();
		}
		
		return emailResponsavel;
		
	}
	
	/**
	 * Gera a Aba do Excel de acordo com a Secretaria e a Lista de ObjetivoEstrategico passados como parâmetros.<br/>
	 * Exemplo:
	 * <strong>populaPlanilhaPorSecretaria(workbook, secretariaDTO, lista);</strong>
	 * @author gustavo.edin
	 * @since 2013-08-21	 
	 * @param workbook
	 * @param secretariaDTO
	 * @param lista
	 */
	protected void populaPlanilhaPorSecretaria(HSSFWorkbook workbook, SecretariaDTO secretariaDTO, List<ObjetivoEstrategico> lista) {
		int i = 2;
		int totalResultado = 0;
		ItemEstruturaIett itemEstruturaIett = null;

		Map<String, Object> cellValues = new HashMap<String, Object>();

		HSSFRow row = null;

		ItemEstruturaDao dao = new ItemEstruturaDao(request);

		HSSFSheet sheet = workbook.createSheet(secretariaDTO.getSiglaSecretaria());
		preencheCabecalho(sheet);

		for (ObjetivoEstrategico objetivoEstrategico : lista) {

			for (Estrategia estrategia : objetivoEstrategico.getEstrategias()) {

				for (Resultado resultado : separaResultadoPorSecretaria(secretariaDTO, estrategia.getResultados())) {

					row = sheet.createRow(i);

					sheet.addMergedRegion(new CellRangeAddress(i, i, 3, 17));

					String secretariaResponsavel = "";

					itemEstruturaIett = getItemEstruturaByResultado(resultado, dao);
					secretariaResponsavel = itemEstruturaIett.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg();

					row.createCell(0).setCellValue(objetivoEstrategico.getSiglaIett());
					row.createCell(1).setCellValue(estrategia.getSiglaEstrategia());
					row.createCell(2).setCellValue(resultado.getSiglaResultado());
					row.createCell(3).setCellValue(resultado.getNome());

					row.createCell(18).setCellValue(" ");
					if (resultado.getPrioritario() != null && resultado.getPrioritario().equals("S")) {
						// TODO : finalizar o REM e ANDAMENTO NAS OUTRAS COLUNAS
						setCellStyleBackgroundColor(HSSFColor.GREY_40_PERCENT.index, row.getCell(18));
					}

					cellValues.put("objetivoEstrategico", objetivoEstrategico);
					cellValues.put("estrategia", estrategia);
					cellValues.put("resultado", resultado);
					cellValues.put("itemEstruturaIett", itemEstruturaIett);

					StringBuilder listaIndicador = new StringBuilder();
					for (ecar.pojo.acompanhamentoEstrategico.Indicador indicador : resultado.indicador) {
						listaIndicador.append(indicador.getNome());
					}

					row.createCell(19).setCellValue(listaIndicador.toString());

					row.createCell(20).setCellValue(resultado.getSituacao());

					row.createCell(21).setCellValue(resultado.responsavel.getNome());

					row.createCell(22).setCellValue(getEmailByResponsavel(resultado.responsavel.getNome(), request));

					row.createCell(23).setCellValue((secretariaResponsavel != null ? secretariaResponsavel : ""));

					row.createCell(24).setCellValue("");
					
					setCellStyle(row.getCell(0));
					setCellStyle(row.getCell(1));
					setCellStyle(row.getCell(2));
					setCellStyle(row.getCell(3));
					setCellStyle(row.getCell(19));
					setCellStyle(row.getCell(20));
					setCellStyle(row.getCell(22));
					setCellStyle(row.getCell(23));
					setCellStyle(row.getCell(24));

					setCellStyleBackgroundColor((short) 41, row.getCell(3), (byte) 216, (byte) 228, (byte) 188);

					cellValues.put("indice", ++i);

					i = preencheLinhaProdutos(sheet, cellValues, dao);

					totalResultado++;
				}
			}
		}
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(20);
		sheet.autoSizeColumn(21);
		sheet.autoSizeColumn(22);
		sheet.autoSizeColumn(23);

	}

	protected int preencheLinhaProdutos(HSSFSheet sheet, Map<String, Object> cellValues, ItemEstruturaDao dao) {

		Resultado resultado = (Resultado) cellValues.get("resultado");
		ObjetivoEstrategico objetivoEstrategico = (ObjetivoEstrategico) cellValues.get("objetivoEstrategico");
		Estrategia estrategia = (Estrategia) cellValues.get("estrategia");
		int i = (Integer) cellValues.get("indice");

		for (Produto produto : resultado.getProdutos()) {

			// Cria uma linha
			HSSFRow row = sheet.createRow(i);

			// Adiciona uma linha de merge do Produto
			sheet.addMergedRegion(new CellRangeAddress(i, i, 4, 17));

			// Configura as colunas iniciais

			row.createCell(0).setCellValue(objetivoEstrategico.getSiglaIett());
			row.createCell(1).setCellValue(estrategia.getSiglaEstrategia());
			row.createCell(2).setCellValue(resultado.getSiglaResultado());
			row.createCell(3).setCellValue("P " + produto.getSiglaProduto());
			row.createCell(4).setCellValue(produto.getNome());

			row.createCell(20).setCellValue(produto.getSituacao());
			row.createCell(21).setCellValue(resultado.responsavel.getNome());
			row.createCell(22).setCellValue(getEmailByResponsavel(resultado.responsavel.getNome(), request));
			if(produto.getResponsavel() != null && produto.getOrgao() != null && produto.getNome() != null){
				row.createCell(23).setCellValue(produto.getResponsavel().getOrgao().getNome());
				cellValues.put("areaResponsavel", produto.getResponsavel().getOrgao().getNome());
			}

			setCellStyleBackgroundColor((short) 42, row.getCell(4), new byte[] { (byte) 253, (byte) 233, (byte) 217 });

			cellValues.remove("indice");
			cellValues.put("indice", ++i);
			cellValues.put("produto", produto);

			i = preencheLinhaAcoes(sheet, cellValues);

		}
		return i;
	}

	protected int preencheLinhaAcoes(HSSFSheet sheet, Map<String, Object> cellValues) {

		// Recupera os objetos do Mapa
		Resultado resultado = (Resultado) cellValues.get("resultado");
		ObjetivoEstrategico objetivoEstrategico = (ObjetivoEstrategico) cellValues.get("objetivoEstrategico");
		Estrategia estrategia = (Estrategia) cellValues.get("estrategia");
		String secretariaResponsavel = (String) cellValues.get("areaResponsavel");
		int i = (Integer) cellValues.get("indice");
		Produto produto = (Produto) cellValues.get("produto");
		cellValues.remove("produto");

		// percorre a lista de acoes do produto atual
		for (Acao acao : produto.getAcoes()) {

			// Cria uma linha
			HSSFRow row = sheet.createRow(i);

			// Adiciona uma linha de merge do Produto
			sheet.addMergedRegion(new CellRangeAddress(i, i, 5, 17));

			// Configura as colunas iniciais

			row.createCell(0).setCellValue(objetivoEstrategico.getSiglaIett());
			row.createCell(1).setCellValue(estrategia.getSiglaEstrategia());
			row.createCell(2).setCellValue(resultado.getSiglaResultado());
			row.createCell(3).setCellValue("P " + produto.getSiglaProduto());
			row.createCell(4).setCellValue("A " + acao.getSiglaAcao());
			row.createCell(5).setCellValue(acao.getNome());
			row.createCell(20).setCellValue(acao.getSituacao());
			if (acao.getResponsavel() != null) {
				row.createCell(21).setCellValue(acao.getResponsavel().getNome());
			}
			row.createCell(23).setCellValue(secretariaResponsavel);

			i++;

		}

		return i;
	}

	public void setCellStyleBackgroundColor(HSSFCell cell, short color) {

		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		HSSFCellStyle style = cell.getSheet().getWorkbook().createCellStyle();
		HSSFFont font = cell.getSheet().getWorkbook().createFont();
		font.setColor(HSSFColor.WHITE.index);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setBorderBottom(HSSFBorderFormatting.BORDER_THIN);
		style.setBorderLeft(HSSFBorderFormatting.BORDER_THIN);
		style.setBorderTop(HSSFBorderFormatting.BORDER_THIN);
		style.setBorderRight(HSSFBorderFormatting.BORDER_THIN);
		style.setFillForegroundColor(color);
		//HSSFColor.GREY_50_PERCENT.index
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cell.setCellStyle(style);

	}

	/**
	 * Atenção a posição deve ser maior que 40
	 * 
	 * @param posicao
	 * @param cell
	 * @param cores
	 */
	protected void setCellStyleBackgroundColor(short posicao, HSSFCell cell, byte... cores) {
		HSSFPalette palette = cell.getSheet().getWorkbook().getCustomPalette();
		palette.setColorAtIndex(posicao, cores[0], cores[1], cores[2]);
		HSSFCellStyle style = cell.getSheet().getWorkbook().createCellStyle();
		style.setFillForegroundColor(palette.getColor(posicao).getIndex());
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cell.setCellStyle(style);
	}

	public void setCellStyleBackgroundColor(short color, HSSFCell cell) {

		HSSFCellStyle style = cell.getSheet().getWorkbook().createCellStyle();
		style.setFillForegroundColor(color);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		cell.setCellStyle(style);

	}

	protected void setCellStyle(HSSFCell cell) {

		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		HSSFCellStyle style = cell.getSheet().getWorkbook().createCellStyle();
		cell.setCellStyle(style);

	}


}
