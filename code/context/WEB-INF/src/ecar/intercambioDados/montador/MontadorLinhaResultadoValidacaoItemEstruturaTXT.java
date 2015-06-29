package ecar.intercambioDados.montador;

import ecar.exception.intercambioDados.SemanticValidationException;
import ecar.intercambioDados.IBusinessObject;

public class MontadorLinhaResultadoValidacaoItemEstruturaTXT implements IMontadorLinhaResultadoValidacaoTXT {

	
	public LinhaResultadoValidacao montarValida(IBusinessObject objetoNegocio, String operacao, String linha, int numeroLinha) {
	
		LinhaResultadoValidacao resultadoValidacao = new LinhaResultadoValidacaoTXT();		
		
		((LinhaResultadoValidacaoTXT)resultadoValidacao).setObjetoNegocio(objetoNegocio);
		((LinhaResultadoValidacaoTXT)resultadoValidacao).setNumeroLinha(numeroLinha);
		((LinhaResultadoValidacaoTXT)resultadoValidacao).setLinha(linha);
		((LinhaResultadoValidacaoTXT)resultadoValidacao).setOperacao(operacao);
		
		return resultadoValidacao;
	
	}
	
	public LinhaResultadoValidacao montarInvalida(SemanticValidationException excecaoSemantica, String operacao, String linha, int numeroLinha) {
		
		LinhaResultadoValidacao resultadoValidacao = new LinhaResultadoValidacaoTXT();		
		
		((LinhaResultadoValidacaoTXT)resultadoValidacao).setMotivo(excecaoSemantica.getMotivoRejeicaoMtr());
		((LinhaResultadoValidacaoTXT)resultadoValidacao).setObjetoNegocio(excecaoSemantica.getGerador());
		((LinhaResultadoValidacaoTXT)resultadoValidacao).setNumeroLinha(numeroLinha);
		((LinhaResultadoValidacaoTXT)resultadoValidacao).setLinha(linha);
		((LinhaResultadoValidacaoTXT)resultadoValidacao).setOperacao(operacao);
		
		return resultadoValidacao;
	}

}
