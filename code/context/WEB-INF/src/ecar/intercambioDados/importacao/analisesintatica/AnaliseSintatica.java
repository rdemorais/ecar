package ecar.intercambioDados.importacao.analisesintatica;

import java.util.List;

import org.apache.log4j.Logger;

import ecar.exception.ECARException;
import ecar.exception.intercambioDados.SintaticValidationException;
import ecar.intercambioDados.importacao.comunicacao.Configuracao;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;

public abstract class AnaliseSintatica {
	
	
	protected Configuracao configuracaoImportacao;
	protected Logger logger = null;
	

	public Configuracao getConfiguracaoImportacao() {
		return configuracaoImportacao;
	}



	public void setConfiguracaoImportacao(
			Configuracao configuracaoImportacao) {
		this.configuracaoImportacao = configuracaoImportacao;
	}



	public Logger getLogger() {
		return logger;
	}



	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	public abstract boolean valida(List<IRegistro> listaRegistro, PerfilIntercambioDadosPflid perfil) throws ECARException, SintaticValidationException;

}
