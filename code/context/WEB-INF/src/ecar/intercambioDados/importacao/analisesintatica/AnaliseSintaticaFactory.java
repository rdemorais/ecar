package ecar.intercambioDados.importacao.analisesintatica;

import org.apache.log4j.Logger;

import ecar.intercambioDados.TipoDadosImportacao;
import ecar.intercambioDados.importacao.comunicacao.Configuracao;

public abstract class AnaliseSintaticaFactory {

	public abstract AnaliseSintatica criar(TipoDadosImportacao tipo, Configuracao configuracaoImportacao, Logger logger);
	
}
