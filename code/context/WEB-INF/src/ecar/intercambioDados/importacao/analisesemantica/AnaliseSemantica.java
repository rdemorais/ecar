package ecar.intercambioDados.importacao.analisesemantica;

import org.apache.log4j.Logger;

import ecar.exception.ECARException;
import ecar.exception.intercambioDados.SemanticValidationException;
import ecar.intercambioDados.IBusinessObject;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;

public abstract class AnaliseSemantica {
	
	
	protected Logger logger = null;
	
	
	
	public Logger getLogger() {
		return logger;
	}



	public void setLogger(Logger logger) {
		this.logger = logger;
	}



	public abstract boolean valida(IBusinessObject objNegocio, PerfilIntercambioDadosPflid perfil) throws SemanticValidationException, ECARException;

}
