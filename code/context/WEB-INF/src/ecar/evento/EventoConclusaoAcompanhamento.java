package ecar.evento;

import java.util.Arrays;

import ecar.util.Dominios;

/**
 * 
 */

public class EventoConclusaoAcompanhamento extends Evento {
	
	/**
	 * 
	 * Lista dos parâmetros esperados pela tela alvo associada ao evento de Conclusão de Acompanhamento.
	 * 
	 *   
	 */
	 
	private static final String parametrosEsperados[] = {"codTipoAcompanhamento",
                                        				 "referencia_hidden",
                                        				 "codAri"
                                        				};

	/**
	 * 
	 * Indicando o evento e iniciando os parametros.
	 * 
	 */
	public EventoConclusaoAcompanhamento() {
		super(Dominios.CFG_MAIL_CONCLUSAO_ACOMPANHAMENTO);   // Definido pelo desenvolvedor.
		
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}