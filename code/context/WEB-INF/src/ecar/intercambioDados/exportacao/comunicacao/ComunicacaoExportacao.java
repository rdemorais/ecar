package ecar.intercambioDados.exportacao.comunicacao;

import java.util.List;

import ecar.exception.ECARException;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.pojo.ConfiguracaoCfg;

public abstract class ComunicacaoExportacao {
	
	public abstract String gerarArquivoDiscoFisico (ConfiguracaoCfg configuracao, String nomeArquivo,List<IRegistro> registros) throws ECARException;
}
