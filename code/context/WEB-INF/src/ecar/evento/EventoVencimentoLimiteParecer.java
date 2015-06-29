package ecar.evento;

import java.util.Arrays;

import ecar.util.Dominios;


/**
 * 
 * @author Patricia Pessoa
 *
 *
 * 
 */

public class EventoVencimentoLimiteParecer extends Evento {
	
	/**
	 * 
	 * Lista dos parâmetros esperados pela tela alvo associada ao evento de Vencimento Data Termino Item
	 * 
	 *   
	 */ 
	private static final String parametrosEsperados[] = { "codTipoAcompanhamento",
														  "referencia_hidden",
														 "codAri"
                     									};

	/**
	 * 
	 * Indicando o evento e iniciando os parametros.
	 *    
	 */
	public EventoVencimentoLimiteParecer() {
		super(Dominios.CFG_MAIL_VENCIMENTO_LIMITE_PARECER);   
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}