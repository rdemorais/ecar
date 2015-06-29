package ecar.intercambioDados.exportacao.comunicacao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import comum.util.ConstantesECAR;

import ecar.exception.ECARException;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.pojo.ConfiguracaoCfg;

public class ComunicacaoExportacaoTXT extends ComunicacaoExportacao {
	
	/**
	 * Gera um arquivo fisico no disco com o nome do arquivo passado e os dados enviados no IRegistro. 
	 * @param nomeArquivo
	 * @return
	 */
	public String gerarArquivoDiscoFisico (ConfiguracaoCfg configuracao, String nomeArquivo,List<IRegistro> registros) throws ECARException {
		
		//TODO			
		String caminho = configuracao.getRaizUpload() + configuracao.getUploadExportacaoDemandas();
		File diretorio = new File(caminho);
		
		if(!diretorio.isDirectory()) 
			diretorio.mkdirs();
		
		String caminhoCompleto = caminho + "/" + nomeArquivo;
		
		FileOutputStream arquivo = null;
		
		try {
			arquivo = new FileOutputStream(caminhoCompleto);
		
			// enquanto há registros
			for (IRegistro registro : registros) {
				if (registro!=null && ((RegistroTXT)registro).getLinha()!=null) {
					arquivo.write(new StringBuffer(((RegistroTXT)registro).getLinha()).append("\n").toString().getBytes(ConstantesECAR.ENCODE_PADRAO));
				}
			}
			arquivo.close();
			
		} catch (FileNotFoundException fnfe) {
			throw new ECARException("exportacao.comunicacao.geracaoArquivo.arquivoNaoEncontrado", fnfe);
		} catch (IOException fnfe) {
			throw new ECARException("exportacao.comunicacao.geracaoArquivo.erroEscritaArquivo", fnfe);
		}
		return caminhoCompleto;
	}
	
	
}
