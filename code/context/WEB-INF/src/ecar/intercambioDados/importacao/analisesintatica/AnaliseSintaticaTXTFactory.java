package ecar.intercambioDados.importacao.analisesintatica;

import org.apache.log4j.Logger;

import ecar.intercambioDados.TipoDadosImportacao;
import ecar.intercambioDados.importacao.comunicacao.Configuracao;

public class AnaliseSintaticaTXTFactory extends AnaliseSintaticaFactory {


	public AnaliseSintatica criar(TipoDadosImportacao tipo, Configuracao configuracaoImportacao, Logger logger) {
		
		AnaliseSintatica analise = null;
		
		if (tipo.equals(TipoDadosImportacao.ITEM)) {
			analise = new AnaliseSintaticaItemEstruturaTXT();
		}
				
		analise.setConfiguracaoImportacao(configuracaoImportacao);
		analise.setLogger(logger);
		
		return analise;
	}


}
