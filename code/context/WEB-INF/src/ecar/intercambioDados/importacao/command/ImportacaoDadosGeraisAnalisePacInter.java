package ecar.intercambioDados.importacao.command;

import java.util.List;

import org.apache.log4j.Logger;

import ecar.exception.ECARException;
import ecar.exception.intercambioDados.SemanticValidationException;
import ecar.exception.intercambioDados.SintaticValidationException;
import ecar.intercambioDados.IBusinessObject;
import ecar.intercambioDados.TipoDadosImportacao;
import ecar.intercambioDados.importacao.analisesemantica.AnaliseSemanticaTXTFactory;
import ecar.intercambioDados.importacao.analisesintatica.AnaliseSintaticaTXTFactory;
import ecar.intercambioDados.importacao.comunicacao.ComunicacaoImportacaoFactory;
import ecar.intercambioDados.importacao.comunicacao.Configuracao;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;


public class ImportacaoDadosGeraisAnalisePacInter implements ImportacaoDadosGeraisAnalisePacInterCommand {

	
	private static ImportacaoDadosGeraisAnalisePacInter importacaoDadosAnalise = null;
	
	
	public static ImportacaoDadosGeraisAnalisePacInter getInstancia() {
		
		if (importacaoDadosAnalise==null) {
			importacaoDadosAnalise = new ImportacaoDadosGeraisAnalisePacInter();
		} 
		return importacaoDadosAnalise;
	}
	
	
	public boolean analisarSintaxe(TipoDadosImportacao tipo, List<IRegistro> listaRegistros, PerfilIntercambioDadosPflid perfil, Configuracao configuracaoImportacao, Logger logger) throws SintaticValidationException, ECARException {
		// if (perfil tem tecnologia TXT)
		return new AnaliseSintaticaTXTFactory().criar(tipo, configuracaoImportacao, logger).valida(listaRegistros, perfil);
		// if (perfil tem tecnologia WS)
		
	}
	
	public List<IRegistro> comunicar(PerfilIntercambioDadosPflid perfil, Configuracao configuracao) throws ECARException {
		return ComunicacaoImportacaoFactory.criar(perfil, configuracao).obtemRegistros();
	}

	public boolean analisarSemantica(IBusinessObject objetoNegocio, PerfilIntercambioDadosPflid perfil) throws SemanticValidationException, ECARException{
		return new AnaliseSemanticaTXTFactory().criar(objetoNegocio).valida(objetoNegocio, perfil);
	}

	
}
