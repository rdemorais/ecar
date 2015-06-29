package ecar.exception.intercambioDados;

import ecar.intercambioDados.IBusinessObject;
import ecar.pojo.intercambioDados.MotivoRejeicaoMtr;

public class SemanticValidationException extends ValidationException {

	IBusinessObject gerador;

	public SemanticValidationException(Throwable causa,String[] messageArgsLoc,MotivoRejeicaoMtr motivoRejeicaoMtr,IBusinessObject gerador,Long numeroLinha) {
		super(causa,messageArgsLoc,motivoRejeicaoMtr,numeroLinha);
		this.gerador = gerador;
	}

	public IBusinessObject getGerador() {
		return gerador;
	}

	public void setGerador(IBusinessObject gerador) {
		this.gerador = gerador;
	}

	
	
	
}