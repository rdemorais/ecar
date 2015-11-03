package ecar.relatorio;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import ecar.bean.FiltroPesquisaMonitoramento;
import ecar.dao.SiteDao;
import ecar.pojo.ms.rel.AcaoExcel;
import ecar.pojo.ms.rel.IndicadorExcel;
import ecar.pojo.ms.rel.ProdutoExcel;
import ecar.pojo.ms.rel.ResultadoExcel;
import ecar.pojo.ms.rel.SecretariaExcel;

public class RelatorioExcelMS {
	
	private static final String NAO_MONITORADO = "Não Monitorado";
	
	public HSSFWorkbook exportarRelatorioExcel(FiltroPesquisaMonitoramento filtro) throws FileNotFoundException, ParsePropertyException, InvalidFormatException {
		SiteDao siteDao = new SiteDao();
		
		List<Object[]> listaDb = siteDao.getListaPorFiltroRelatorioExcel(filtro);
		
		Map<String, Object> beans = new HashMap<String, Object>();
		List<String> sheetNames = new ArrayList<String>();
		List<SecretariaExcel> secretarias = getConteudo(listaDb);
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		InputStream is = 
					new BufferedInputStream(new FileInputStream(RelatorioExcelMS.class.getResource("/relatorio-excel-template.xls").getPath()));
		
		Map<String, Object> map;
		for (SecretariaExcel secretariaExcel : secretarias) {
			sheetNames.add(secretariaExcel.getNome());
			map = new HashMap<String, Object>();
			map.put("resultados", secretariaExcel.getResultados());
			
			maps.add(map);
		}
		
		XLSTransformer transformer = new XLSTransformer();
		return (HSSFWorkbook) transformer.transformMultipleSheetsList(is, maps, sheetNames, "map", beans, 0);
	}
	
	private List<SecretariaExcel> getConteudo(List<Object[]> listaDb) {
		List<SecretariaExcel> secretarias = new ArrayList<SecretariaExcel>();
		ResultadoExcel res;
		IndicadorExcel ind;
		ProdutoExcel prod;
		AcaoExcel ac;
		SecretariaExcel sec;
		
		String situacao;
		String responsavel;
		String emailResponsavel;
		String areaResponsavel;
		String rem;
		
		for (Object[] obj : listaDb) {
			
			//Secretaria
			if(obj[8] != null) {
				sec = new SecretariaExcel(obj[8].toString());
			}else {
				sec = new SecretariaExcel("NAO_INFORMADA");
			}
			
			if(!secretarias.contains(sec)) {
				secretarias.add(sec);
			}else {
				sec = secretarias.get(secretarias.indexOf(sec));
			}
			
			//Resultado
			if(obj[0] != null && obj[2] != null && obj[4] != null) { //Campos que contem os codigos de OE, Est e RES
				
				if(obj[10] != null) {
					situacao = obj[10].toString();
				}else {
					situacao = NAO_MONITORADO;
				}
				
				if(obj[9] != null) {
					responsavel = obj[9].toString();
				}else {
					responsavel = "";
				}
				
				if(obj[22] != null) {
					emailResponsavel = obj[22].toString();
				}else {
					emailResponsavel = "";
				}
				
				rem = "";
				
				if(obj[7] != null) {
					if(obj[7].toString().equalsIgnoreCase("Y")) {
						rem = "*";
					}
				}
				
				res = new ResultadoExcel(
						obj[1].toString(), 
						obj[3].toString(), 
						obj[5].toString(), 
						obj[6].toString(),
						rem,
						situacao,
						responsavel,
						emailResponsavel,
						sec.getNome());
				
				if(!sec.getResultados().contains(res)) {
					sec.addResultado(res);
				}else {
					res = sec.getResultados().get(sec.getResultados().indexOf(res));
				}
				
				//Indicador
				if(obj[26] != null) {
					ind = new IndicadorExcel(obj[26].toString());
					
					res.getIndicadorLista().append(ind.getNome() + "; ");
				}
				
				//Produto
				
				if(obj[16] != null) {
					situacao = obj[16].toString();
				}else {
					situacao = NAO_MONITORADO;
				}
				
				if(obj[15] != null) {
					responsavel = obj[15].toString();
				}else {
					responsavel = "";
				}
				
				if(obj[14] != null) {
					areaResponsavel = obj[14].toString();
				}else {
					areaResponsavel = "";
				}
				
				if(obj[23] != null) {
					emailResponsavel = obj[23].toString();
				}else {
					emailResponsavel = "";
				}
				
				if (obj[11] != null) {
					prod = new ProdutoExcel(
							obj[12].toString(), 
							obj[13].toString(), 
							situacao, 
							responsavel, 
							emailResponsavel, 
							areaResponsavel);
					prod.setResultado(res);
					
					if(!res.getProdutos().contains(prod)) {
						res.addProduto(prod);
					}else {
						prod = res.getProdutos().get(res.getProdutos().indexOf(prod));
					}
					
					//Acao...
					if(obj[20] != null) {
						situacao = obj[20].toString();
					}else {
						situacao = NAO_MONITORADO;
					}
					
					if(obj[21] != null) {
						responsavel = obj[21].toString();
					}else {
						responsavel = "";
					}
					
					if(obj[25] != null) {
						areaResponsavel = obj[25].toString();
					}else {
						areaResponsavel = "";
					}
					
					if(obj[24] != null) {
						emailResponsavel = obj[24].toString();
					}else {
						emailResponsavel = "";
					}
					
					if (obj[17] != null) {
						ac = new AcaoExcel(
								obj[18].toString(), 
								obj[19].toString(), 
								situacao, 
								responsavel, 
								emailResponsavel, 
								areaResponsavel);
						ac.setProduto(prod);
						if(!prod.getAcoes().contains(ac)) {
							prod.addAcao(ac);
						}
					}
				}
			}
		}
		return secretarias;
	}
}