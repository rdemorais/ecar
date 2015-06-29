package ecar.intercambioDados.montador;

import ecar.intercambioDados.TipoDadosImportacao;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.pojo.intercambioDados.tecnologia.TipoTecnologiaEnum;

public class MontadorLinhaResultadoValidacaoTXTFactory {


	public static IMontadorLinhaResultadoValidacaoTXT criar(IRegistro registro, PerfilIntercambioDadosPflid perfil) {
		
		IMontadorLinhaResultadoValidacaoTXT montadorResultadoValidacao = null;
		TipoDadosImportacao tipo = null;
		
		tipo = registro.getTipo(perfil);
		
		// se é TXT e ITEM ESTRUTURA
		if (perfil.getDadosTecnologiaPerfilDtp()!=null && perfil.getDadosTecnologiaPerfilDtp().getTipoTecnologia().equals(TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER) && 
			tipo!=null && tipo.equals(TipoDadosImportacao.ITEM))
			montadorResultadoValidacao = new MontadorLinhaResultadoValidacaoItemEstruturaTXT();
			
		return montadorResultadoValidacao;
	}
	

//	public static IMontadorLinhaResultadoValidacaoTXT criarInvalida(IRegistro registro, PerfilIntercambioDadosPflid perfil) {
//		
//		IMontadorLinhaResultadoValidacaoTXT montadorResultadoValidacao = null;
//		TipoDadosImportacao tipo = null;
//		
//		tipo = registro.getTipo(perfil);
//		
//		//se é TXT e ITEM ESTRUTURA
//		if (perfil.getDadosTecnologiaPerfilDtp()!=null && perfil.getDadosTecnologiaPerfilDtp().getTipoTecnologia().equals(TipoTecnologiaEnum.ARQUIVOTEXTO_PACINTER) && 
//				tipo!=null && tipo.equals(TipoDadosImportacao.ITEM))
//			montadorResultadoValidacao = new MontadorLinhaResultadoValidacaoItemEstruturaTXT();
//			
//		return montadorResultadoValidacao;
//	}
	
}
