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

public class EventoVencimentoLimiteFisico extends Evento {
	
	/**
	 * 
	 * Lista dos parâmetros esperados pela tela alvo associada ao evento de Vencimento Data Termino Item
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
	public EventoVencimentoLimiteFisico() {
		super(Dominios.CFG_MAIL_VENCIMENTO_LIMITE_FISICO);   
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}