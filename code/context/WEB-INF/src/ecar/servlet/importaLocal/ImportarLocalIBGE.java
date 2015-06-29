package ecar.servlet.importaLocal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hpsf.IllegalPropertySetDataException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import ecar.dao.LocalItemDao;
import ecar.exception.ECARException;
import ecar.pojo.LocalGrupoLgp;
import ecar.pojo.LocalItemLit;
import ecar.util.Dominios;

/**
 *
 * @author 70744416353
 */
public class ImportarLocalIBGE {

	//public static void main(String[] args) throws IOException {
    /**
     *
     * @param xml
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ECARException
     */
    public String importaDadosIBGE(String xml, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ECARException {

		String mensagem = "";
		try {
			// Carrega o arquivo .xls com os dados do IBGE
			File file = new File(new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getRaizUpload()+"/cidades/cidades_ibge.xls");
			//File file = new File(xml);
			InputStream myxls = new FileInputStream(file);
		    
			// Cria o objeto para o arquivo .xls
			HSSFWorkbook wb = new HSSFWorkbook(myxls);
		    
		    ImportarLocalIBGE importarLocalIBGE = new ImportarLocalIBGE();
		    // Importa os dados que constam no arquivo para a base de dados
		    importarLocalIBGE.importaDados(wb, request);
		    //importarLocalIBGE.importaDados(wb);
		    
		    //System.out.println("Locais importados com sucesso!");
		    mensagem = "Locais importados com sucesso!";
		    
		} catch(IllegalPropertySetDataException ex) {
			//System.out.println("Exceção IllegalPropertySetDataException: " + ex.getMessage());
			mensagem = "Erro: " + ex.getMessage();
			throw new FileNotFoundException(ex.getMessage());
		} catch(FileNotFoundException fex) {
			//System.out.println("Exceção FileNotFoundException: " + fex.getMessage());
			mensagem = "Erro: " + fex.getMessage();
			throw new FileNotFoundException(fex.getMessage());
		} catch(ECARException ecarEx) {
			//System.out.println("Exceção ECARException: " + ecarEx.getMessage());
			mensagem = "Erro: " + ecarEx.getMessage();
			throw new FileNotFoundException(ecarEx.getMessage());
		}
		
		return mensagem;
	}
	
	//private void importaDados(HSSFWorkbook pWB) 
	private void importaDados(HSSFWorkbook pWB, HttpServletRequest request) 
			throws ECARException {

		LocalItemDao localItemDao = new LocalItemDao(request);
		
		// Lista de locais
		List<LocalItemLit> listaLocais = null;
		// Map com os locais de cada UF
		Map<Long, List<LocalItemLit>> mapLocais = new HashMap<Long, List<LocalItemLit>>();
		
		// Pega a primeira folha
		HSSFSheet sheet = pWB.getSheetAt(0);
		
		// Pega quantas linhas a planilha tem
		int numeroLinhas = sheet.getPhysicalNumberOfRows();
		
		// Para cada linha da folha pegar os valores das colunas
		HSSFRow row = null;
		HSSFCell cell = null;

		try {
			for(int i = 4; i < numeroLinhas; i++) {
				
				// Pega linha
				row = sheet.getRow(i);
				listaLocais = new ArrayList<LocalItemLit>();
				
				String linha ="";
				
				// Cria o localItem e o grupo do local a ser inserido
				// O grupo do local é: Município
				LocalItemLit localItem = new LocalItemLit();
				LocalGrupoLgp localGrupoLgp = new LocalGrupoLgp();
				localGrupoLgp.setCodLgp(new Long(8));// tem que ver qual o codigo para Municipio no banco presidencia
				
				// Pega as celulas da linha
				int numeroColunas = 4;
				Long codUF = null;
				
				// Para cada celula da linha pega o valor e cria:
				// 1 - o objeto referente a linha que deverá ser inserido na tabela tb_local_item_lit
				// 2 - o objeto referente a linha que deverá ser inserido na tabela tb_local_item_hierarquia_lith
				for(int j = 0; j < numeroColunas; j++) {
	
					cell = row.getCell(Short.parseShort(""+j));
	
					// Só pega a célula diferente de vazia
					if(cell != null && cell.getCellType() != HSSFCell.CELL_TYPE_BLANK) {	
	
						// Só pega a célula que não for mesclada
						if(!cell.getCellStyle().getWrapText()) {
							switch (j) {
								
								// Pega sigla uf
								case 0:
									linha += "-" + cell.toString();
									break;
	
								// Pega cod uf
								case 1:
									Double db = new Double(cell.toString());
									codUF = new Long(db.longValue());
									linha += "-" + cell.toString();
									break;
								
								// Pega cod municipio ibge
								case 2:
									linha += "-" + cell.toString();
									localItem.setCodIbgeLit(cell.toString());
									break;
									
								// Pega identificacao (nome) do municipio
								case 3:
									if(cell.toString().endsWith("*")) {
										linha += "-" + cell.toString().substring(0,cell.toString().indexOf('*')-1);
										localItem.setIdentificacaoLit(cell.toString().substring(0,cell.toString().indexOf('*')));
									} else {
										linha += "-" + cell.toString();
										localItem.setIdentificacaoLit(cell.toString());
									}
									break;
							}
						}
						
					}
				} // FIM: for das colunas da linha
				
				// Se existir localItem com identificação e código IBGE coloca na coleção de locais a ser salva
				if( localItem.getIdentificacaoLit() != null && !localItem.getIdentificacaoLit().equals("") && 
					localItem.getCodIbgeLit() != null && !localItem.getCodIbgeLit().equals("") ) {
					
					// Seta demais atributos
					localItem.setLocalGrupoLgp(localGrupoLgp); // grupo: município
					localItem.setIndAtivoLit(Dominios.ATIVO); // indAtivo: S
					localItem.setDataInclusaoLit(new Date()); // Data inclusao: data atual
					
					// Para cada UF, associa-se uma lista de municipios
					if(mapLocais.containsKey(codUF)) {
						listaLocais = mapLocais.get(codUF);
						listaLocais.add(localItem); // adiciona localItem a lista de municipios
						mapLocais.put(codUF, listaLocais);
					} else {
						listaLocais.add(localItem); // adiciona localItem a lista de municipios
						mapLocais.put(codUF, listaLocais);
					}
				}
				
			}// FIM: for das linhas da planilha
			
			localItemDao.salvar(mapLocais);

		} catch(ECARException ecarEx) {
			throw new ECARException(ecarEx.getMessage());
		}
	}
	
}
