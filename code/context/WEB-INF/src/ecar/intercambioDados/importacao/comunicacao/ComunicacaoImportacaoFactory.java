package ecar.intercambioDados.importacao.comunicacao;

import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;




public class ComunicacaoImportacaoFactory {
	
	public static ComunicacaoImportacao criar(PerfilIntercambioDadosPflid perfil, Configuracao configuracao) {
		
		ComunicacaoImportacao comunicacao = null;
		
		// se perfil de txt 
		comunicacao = new ComunicacaoImportacaoTXT(perfil, configuracao);
	
		// se perfil de ws	
		
		
		return comunicacao;
	}

}
