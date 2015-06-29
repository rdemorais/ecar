package ecar.intercambioDados.importacao.command;

import java.util.List;

import org.apache.log4j.Logger;

import ecar.exception.ECARException;
import ecar.exception.intercambioDados.SemanticValidationException;
import ecar.exception.intercambioDados.SintaticValidationException;
import ecar.intercambioDados.IBusinessObject;
import ecar.intercambioDados.TipoDadosImportacao;
import ecar.intercambioDados.importacao.comunicacao.Configuracao;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;

public interface ImportacaoDadosGeraisAnalisePacInterCommand {
		
	List<IRegistro> comunicar(PerfilIntercambioDadosPflid perfil, Configuracao configuracao) throws ECARException;
	
	boolean analisarSintaxe(TipoDadosImportacao tipo, List<IRegistro> listaRegistros, PerfilIntercambioDadosPflid perfil, Configuracao configuracaoImportacao, Logger logger) throws SintaticValidationException, ECARException;
	
	boolean analisarSemantica(IBusinessObject objetoNegocio, PerfilIntercambioDadosPflid perfil) throws SemanticValidationException, ECARException;
	
}
