package ecar.intercambioDados.recurso.xml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ecar.exception.ECARException;
import ecar.intercambioDados.importacao.comunicacao.ComunicacaoLogger;
import ecar.intercambioDados.importacao.comunicacao.Configuracao;
import ecar.intercambioDados.importacao.comunicacao.ConfiguracaoImportacaoTXT;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.pojo.ConfiguracaoCfg;

public class ComunicacaoXML extends ComunicacaoLogger {

	InputStream stream;
	
	public ComunicacaoXML(Configuracao config) {
		this.stream = ((ConfiguracaoImportacaoTXT)config).getStream();
	}
	
	public List<IRegistro> obtemRegistros() {
		//TODO Implementar leitura por XML
		System.out.println("Implementação da leitura do arquivo XML");
		return new ArrayList<IRegistro>();
	}

	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	public String gerarArquivoDiscoFisico(ConfiguracaoCfg configuracao, String nomeArquivo, List<IRegistro> registros) throws ECARException {
		// TODO Auto-generated method stub
		return null;
	}

}
