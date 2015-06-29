package ecar.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import ecar.bean.FiltroPesquisaMonitoramento;
import ecar.dao.ItemEstruturaDao;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.acompanhamentoEstrategico.Estrategia;
import ecar.pojo.acompanhamentoEstrategico.Indicador;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.pojo.acompanhamentoEstrategico.Resultado;
import ecar.servico.RelatorioAcompanhamentoService;
import ecar.servlet.relatorio.dto.SecretariaDTO;

public class ExcelUtilIndicador extends ExcelUtil implements Serializable {

	private HttpServletRequest request;
	private Logger logger = Logger.getLogger(this.getClass());

	public ExcelUtilIndicador(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	private static final long serialVersionUID = 1L;


	/**
	 * Preenche o início de cada cabeçalho das Abas geradas.<br/>
	 * Exemplo:
	 * <strong>preencheCabecalho(sheet);</strong>
	 * @author gustavo.edin
	 * @since 2013-08-21	 
	 * @param sheet
	 */
	private void preencheCabecalhoIndicadorApenas(HSSFSheet sheet) {

		short cor = HSSFColor.GREY_50_PERCENT.index;
		HSSFRow row = sheet.createRow(1);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 3, 9));
		row.createCell(0).setCellValue("OE");
		row.createCell(1).setCellValue("ES");
		row.createCell(2).setCellValue("R");
		row.createCell(3).setCellValue("Descri\u00e7\u00f5es");
		row.createCell(10).setCellValue("Situa\u00e7\u00e3o");
		row.createCell(11).setCellValue("Respons\u00e1vel");
		row.createCell(12).setCellValue("\u00c1rea Respons\u00e1vel");
		row.createCell(13).setCellValue("Sinaliza\u00e7\u00e3o");

		sheet.autoSizeColumn(1);
		sheet.autoSizeColumn(10);
		sheet.autoSizeColumn(11);
		sheet.autoSizeColumn(12);
		sheet.autoSizeColumn(13);

		setCellStyleBackgroundColor(row.getCell(0), cor);
		setCellStyleBackgroundColor(row.getCell(1), cor);
		setCellStyleBackgroundColor(row.getCell(2), cor);
		setCellStyleBackgroundColor(row.getCell(3), cor);
		setCellStyleBackgroundColor(row.getCell(10), cor);
		setCellStyleBackgroundColor(row.getCell(11), cor);
		setCellStyleBackgroundColor(row.getCell(12), cor);
		setCellStyleBackgroundColor(row.getCell(13), cor);

	}	
	
	public void objetivoEstrategicoFiltroExcellIndicador(HttpServletResponse response, FiltroPesquisaMonitoramento filtro) {

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
					criarAbaExcelIndicador(workbook, secretariaDTO, lista);
					tempoFinal = System.currentTimeMillis();
					this.logger.debug(String.format("Tempo Estimado Secretaria %s: %.2f segundos", secretariaDTO.getSiglaSecretaria(), (tempoFinal - tempoInicial) / 1000d));
				}
			}
		} else {
			for (SecretariaDTO secretariaDTO : listaSecretarias) {
				tempoInicial = System.currentTimeMillis();
				criarAbaExcelIndicador(workbook, secretariaDTO, lista);
				tempoFinal = System.currentTimeMillis();
				this.logger.debug(String.format("Tempo Estimado Secretaria %s: %.2f segundos", secretariaDTO.getSiglaSecretaria(), (tempoFinal - tempoInicial) / 1000d));
			}
		}

		exportaServletExcel(response, workbook);
		
	}

	private void criarAbaExcelIndicador(HSSFWorkbook workbook, SecretariaDTO secretariaDTO, List<ObjetivoEstrategico> lista) {

		int i = 2;
		
		int indiceAtual = 0;

		int lastIndex = 0;
		
		Map<String, Object> cellValues = new HashMap<String, Object>();

		HSSFRow row = null;

		ItemEstruturaDao dao = new ItemEstruturaDao(request);
		ItemEstruturaIett itemEstruturaIett = null;
		
		HSSFSheet sheet = workbook.createSheet(secretariaDTO.getSiglaSecretaria());
		preencheCabecalhoIndicadorApenas(sheet);

		for (ObjetivoEstrategico objetivoEstrategico : lista) {

			for (Estrategia estrategia : objetivoEstrategico.getEstrategias()) {

				for (Resultado resultado : separaResultadoPorSecretaria(secretariaDTO, estrategia.getResultados())) {

					row = sheet.createRow(i);

					sheet.addMergedRegion(new CellRangeAddress(i, i, 3, 9));

					String secretariaResponsavel = "";
					itemEstruturaIett = getItemEstruturaByResultado(resultado, dao);

					secretariaResponsavel = itemEstruturaIett.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg();

					row.createCell(0).setCellValue(objetivoEstrategico.getSiglaIett());
					row.createCell(1).setCellValue(estrategia.getSiglaEstrategia());
					row.createCell(2).setCellValue(resultado.getSiglaResultado());
					row.createCell(3).setCellValue(resultado.getNome());

					cellValues.put("objetivoEstrategico", objetivoEstrategico);
					cellValues.put("estrategia", estrategia);
					cellValues.put("resultado", resultado);
					cellValues.put("indice", i);					
					
					row.createCell(10).setCellValue(resultado.getSituacao());

					row.createCell(11).setCellValue(resultado.responsavel.getNome());

					row.createCell(12).setCellValue((secretariaResponsavel != null ? secretariaResponsavel : ""));
					
					//row.createCell(22).setCellValue(resultado.indicador.get(0).getNome());
					
					setCellStyle(row.getCell(0));
					setCellStyle(row.getCell(1));
					setCellStyle(row.getCell(2));
					setCellStyle(row.getCell(3));
					setCellStyle(row.getCell(10));
					setCellStyle(row.getCell(11));
					setCellStyle(row.getCell(12));

					setCellStyleBackgroundColor((short) 41, row.getCell(3), (byte) 216, (byte) 228, (byte) 188);

					cellValues.put("indice", ++i);
					
					indiceAtual = i;
					
					i = preencheLinhaIndicadores(sheet, cellValues, dao);

//					if(indiceAtual == i){
//						sheet.removeRow(row);
//					}
					
				}
			}
		}

		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(2);
		sheet.autoSizeColumn(3);
		sheet.autoSizeColumn(4);
		sheet.autoSizeColumn(5);
		sheet.autoSizeColumn(6);
		sheet.autoSizeColumn(7);
		sheet.autoSizeColumn(8);
		sheet.autoSizeColumn(19);
		sheet.autoSizeColumn(21);
		
	}
	
	protected int preencheLinhaIndicadores(HSSFSheet sheet, Map<String, Object> cellValues, ItemEstruturaDao dao){

		int i = (Integer) cellValues.get("indice");
		
		ObjetivoEstrategico objetivoEstrategico = (ObjetivoEstrategico) cellValues.get("objetivoEstrategico");
		Estrategia estrategia = (Estrategia) cellValues.get("estrategia");
		Resultado resultado = (Resultado) cellValues.get("resultado");
		HSSFRow row = null;
		
		
		if(resultado.indicador.size() > 0){
			
			preencheCabecalhoIndicador(sheet, cellValues, i++);
			
			for (Indicador indicador : resultado.indicador) {
				// Cria uma linha
				row = sheet.createRow(i);

				// Adiciona uma linha de merge do Produto
				//sheet.addMergedRegion(new CellRangeAddress(i, i, 9, 17));

				row.createCell(0).setCellValue(objetivoEstrategico.getSiglaIett());
				row.createCell(1).setCellValue(estrategia.getSiglaEstrategia());
				row.createCell(2).setCellValue(resultado.getSiglaResultado());
				row.createCell(3).setCellValue(indicador.getNome());
				row.createCell(4).setCellValue(resultado.getConceituacao());
				row.createCell(5).setCellValue(resultado.getInterpretacao());
				row.createCell(6).setCellValue(indicador.getFonte());
				row.createCell(7).setCellValue(indicador.getMetodoCalculo());
				row.createCell(8).setCellValue(indicador.getUnidadeMedida());
				row.createCell(9).setCellValue(indicador.getCodigo());
				//resultado.getSinalizacao().getIdentificacao();
				
				i++;
				
			}		
		}

		cellValues.remove("indice");
		cellValues.put("indice", i);
				
		return i;
		
	}
	
	protected void preencheCabecalhoIndicador(HSSFSheet sheet, Map<String, Object> cellValues, int i){

		short cor = HSSFColor.LIGHT_BLUE.index;
		
		ObjetivoEstrategico objetivoEstrategico = (ObjetivoEstrategico) cellValues.get("objetivoEstrategico");
		Estrategia estrategia = (Estrategia) cellValues.get("estrategia");
		Resultado resultado = (Resultado) cellValues.get("resultado");

		// Cria uma linha
		HSSFRow row = sheet.createRow(i);

		// Adiciona uma linha de cabeçalho dos indicadores
//		sheet.addMergedRegion(new CellRangeAddress(i, i, 9, 17));

		row.createCell(0).setCellValue(objetivoEstrategico.getSiglaIett());
		row.createCell(1).setCellValue(estrategia.getSiglaEstrategia());
		row.createCell(2).setCellValue(resultado.getSiglaResultado());
		row.createCell(3).setCellValue("Nome do indicador");
		row.createCell(4).setCellValue("Conceitua\u00e7\u00e3o");
		row.createCell(5).setCellValue("Interpreta\u00e7\u00e3o");
		row.createCell(6).setCellValue("Fonte");
		row.createCell(7).setCellValue("M\u00e9todo de C\u00e1lculo");
		row.createCell(8).setCellValue("Unidade de Medida");
		row.createCell(9).setCellValue("COD IETTIR");

		setCellStyleBackgroundColor(row.getCell(0), cor);
		setCellStyleBackgroundColor(row.getCell(1), cor);
		setCellStyleBackgroundColor(row.getCell(2), cor);
		setCellStyleBackgroundColor(row.getCell(3), cor);
		setCellStyleBackgroundColor(row.getCell(4), cor);
		setCellStyleBackgroundColor(row.getCell(5), cor);
		setCellStyleBackgroundColor(row.getCell(6), cor);
		setCellStyleBackgroundColor(row.getCell(7), cor);
		setCellStyleBackgroundColor(row.getCell(8), cor);
		setCellStyleBackgroundColor(row.getCell(9), cor);

	}
	

}
