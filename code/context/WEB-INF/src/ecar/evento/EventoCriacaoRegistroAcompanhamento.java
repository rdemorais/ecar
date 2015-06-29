package ecar.evento;

import java.util.Arrays;

import ecar.util.Dominios;

/**
 * 
 * 
 */

public class EventoCriacaoRegistroAcompanhamento extends Evento {
	
	/**
	 * 
	 * Lista dos parâmetros esperados pela tela alvo associada ao evento de Registro de Acompanhamento.
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
	public EventoCriacaoRegistroAcompanhamento() {
		super(Dominios.CFG_MAIL_CRIACAO_REGISTRO_ACOMPANHAMENTO);   // Definido pelo desenvolvedor.
		
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}