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

public class EventoDesbloquearPlanejamento extends Evento {
	
	/**
	 * 
	 * Lista dos parâmetros esperados pela tela alvo associada ao evento de Desbloquear Planejamento
	 * 
	 *   
	 */ 
	private static final String parametrosEsperados[] = {"codIett",
	                                       				 "codAba",
                                        				 "codEttSelecionado",
                     									};

	/**
	 * 
	 * Indicando o evento e iniciando os parametros.
	 *    
	 */
	public EventoDesbloquearPlanejamento() {
		super(Dominios.CFG_MAIL_DESBLOQUEAR_PLANEJAMENTO);   
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}