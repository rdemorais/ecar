package ecar.intercambioDados.montador;

import ecar.intercambioDados.ControladorIntercambioDados;
import ecar.intercambioDados.exportacao.comunicacao.ComunicacaoExportacao;
import ecar.intercambioDados.exportacao.comunicacao.ComunicacaoExportacaoTXT;
import ecar.intercambioDados.importacao.comunicacao.Configuracao;
import ecar.intercambioDados.recurso.txt.MontadorTXTDTO;

public class Montador {

	public static final int TIPO_MOVIMENTO_IMPORTACAO = 0;
	public static final int TIPO_MOVIMENTO_EXPORTACAO = 1;
	
	ControladorIntercambioDados controladorIntercambioDados;
	
	public Montador(Configuracao config,int tipo) {
				
		switch (tipo) {
		case TIPO_MOVIMENTO_IMPORTACAO:
				
				controladorIntercambioDados = new ControladorIntercambioDados  (config);
			break;

		case TIPO_MOVIMENTO_EXPORTACAO:
			ComunicacaoExportacao comunicacao = new ComunicacaoExportacaoTXT();
			
			controladorIntercambioDados = new ControladorIntercambioDados  (comunicacao, new MontadorTXTDTO(config));
		break;

		default:
				controladorIntercambioDados = new ControladorIntercambioDados();
			break;
		}
		
	}
	
	public ControladorIntercambioDados getControladorIntercambioDados() {
		return controladorIntercambioDados;
	}
	
}
