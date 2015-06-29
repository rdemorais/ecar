package ecar.exception.intercambioDados;

import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.pojo.intercambioDados.MotivoRejeicaoMtr;

public class SintaticValidationException extends ValidationException {

	private IRegistro gerador;

	public IRegistro getGerador() {
		return gerador;
	}

	public void setGerador(IRegistro gerador) {
		this.gerador = gerador;
	}

	public SintaticValidationException(Throwable causa,String[] messageArgsLoc,MotivoRejeicaoMtr motivoRejeicaoMtr,IRegistro gerador, int numeroLinha) {
		super(causa,messageArgsLoc,motivoRejeicaoMtr,new Long(numeroLinha));
		this.gerador = gerador;
	}
	
	
}
