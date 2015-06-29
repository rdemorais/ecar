package ecar.intercambioDados.montador;

import ecar.exception.intercambioDados.SemanticValidationException;
import ecar.intercambioDados.IBusinessObject;

public interface IMontadorLinhaResultadoValidacaoTXT {
	
	public LinhaResultadoValidacao montarValida(IBusinessObject objetoNegocio, String operacao, String linha, int numeroLinha);
	
	public LinhaResultadoValidacao montarInvalida(SemanticValidationException excecaoSemantica, String operacao, String linha, int numeroLinha);

}
