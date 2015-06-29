package ecar.intercambioDados.validador;

import ecar.exception.ECARException;
import ecar.exception.intercambioDados.SemanticValidationException;
import ecar.intercambioDados.dto.IBusinessObjectDTO;

public abstract class SemanticValidator extends Validator{

	public abstract boolean valida(IBusinessObjectDTO dto) throws ECARException, SemanticValidationException;	

}
