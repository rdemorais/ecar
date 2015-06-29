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

public class EventoManutencaoEvento extends Evento {
	
	/**
	 * 
	 * Lista dos par�metros esperados pela tela alvo associada ao evento de Manuten��o Evento
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
	public EventoManutencaoEvento() {
		super(Dominios.CFG_MAIL_MANUTENCAO_EVENTO);   
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}