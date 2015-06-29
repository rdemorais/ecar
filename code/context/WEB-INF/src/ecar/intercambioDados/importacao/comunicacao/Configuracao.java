package ecar.intercambioDados.importacao.comunicacao;

import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;



public abstract class Configuracao {

	protected PerfilIntercambioDadosPflid perfil;
	
	protected Configuracao(PerfilIntercambioDadosPflid perfil) {
		this.perfil = perfil;
	}

	public PerfilIntercambioDadosPflid getPerfil(){
		return perfil;
	}
	
}
