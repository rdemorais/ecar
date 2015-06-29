package ecar.intercambioDados.validador;

import org.apache.log4j.Logger;

import comum.database.Dao;

import ecar.intercambioDados.importacao.comunicacao.Configuracao;

public abstract class Validator {

	protected Configuracao config;
	protected Dao dao;
	
	protected Logger logger = null;
	
}
