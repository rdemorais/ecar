package ecar.dao.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.jxls.exception.ParsePropertyException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import ecar.bean.FiltroPesquisaMonitoramento;
import ecar.relatorio.RelatorioExcelMS;

public class ExcelTest {
	
	@Test
	public void testGerarRelatorioExcel() throws ParsePropertyException, InvalidFormatException, IOException {
		
		FiltroPesquisaMonitoramento filtro = new FiltroPesquisaMonitoramento();
		RelatorioExcelMS relatorioExcelMs = new RelatorioExcelMS();
		List<Integer> oes = new ArrayList<Integer>();
		List<Long> secretarias = new ArrayList<Long>();
		List<Long> gruposUsuarios = new ArrayList<Long>();
		gruposUsuarios.add(134L);
		gruposUsuarios.add(19L);
		
		oes.add(692);
		secretarias.add(16L);
		
		filtro.setCodigosObjetivosEstrategicos(oes);
		//filtro.setSecretariasSelecionadas(secretarias);
		filtro.setExercicio(12L);
		filtro.setCodigosUsuariosPermissao(gruposUsuarios);
		filtro.setCodigoUsuario(1L);
		
		HSSFWorkbook resultWorkbook = relatorioExcelMs.exportarRelatorioExcel(filtro);
		FileOutputStream fout=new
				FileOutputStream("/Users/rafaeldemorais/Projetos/MS/relatorio-excel.xls");
				        resultWorkbook.write(fout);
	}
}