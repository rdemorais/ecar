package ecar.servlet.relatorio.dto;

import java.util.ArrayList;
import java.util.List;

public class SecretariaDTO {

	private List<Long> codigosSecretarias;
	private String siglaSecretaria;

	public List<Long> getCodigosSecretarias() {
		if(codigosSecretarias == null){
			codigosSecretarias = new ArrayList<Long>();
		}
		return codigosSecretarias;
	}
	
	public void setCodigosSecretarias(List<Long> codigosSecretarias) {
		this.codigosSecretarias = codigosSecretarias;
	}

	public String getSiglaSecretaria() {
		return siglaSecretaria;
	}

	public void setSiglaSecretaria(String siglaSecretaria) {
		this.siglaSecretaria = siglaSecretaria;
	}

}
