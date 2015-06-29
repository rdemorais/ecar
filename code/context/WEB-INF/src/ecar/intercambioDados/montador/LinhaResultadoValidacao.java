package ecar.intercambioDados.montador;

import ecar.intercambioDados.IBusinessObject;

public class LinhaResultadoValidacao {
	
	IBusinessObject objetoNegocio;
	
	String tipoOperacao;

	public IBusinessObject getObjetoNegocio() {
		return objetoNegocio;
	}

	public void setObjetoNegocio(IBusinessObject objetoNegocio) {
		this.objetoNegocio = objetoNegocio;
	}
	
	 @Override
	 public boolean equals(Object other) {
	      return this.getObjetoNegocio().equals(other);
	  }

	 @Override
	 public int hashCode() {
		 return this.getObjetoNegocio().hashCode();
	 }

	public String getTipoOperacao() {
		return tipoOperacao;
	}

	public void setTipoOperacao(String tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}
	 
	 

}
