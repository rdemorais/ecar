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

public class EventoAtivarMonitoramento extends Evento {
	
	/**
	 * 
	 * Lista dos parâmetros esperados pela tela alvo associada ao evento de Ativar Monitoramento
	 * 
	 *   
	 */ 
	private static final String parametrosEsperados[] = {"codIett",
	                                       				 "codAba",
                                        				 "codEttSelecionado"
                     									};

	/**
	 * 
	 * Indicando o evento e iniciando os parametros.
	 *    
	 */
	public EventoAtivarMonitoramento() {
		super(Dominios.CFG_MAIL_ATIVAR_MONITORAMENTO);   
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}