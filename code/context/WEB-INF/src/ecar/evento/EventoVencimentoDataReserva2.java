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

public class EventoVencimentoDataReserva2 extends Evento {
	
	/**
	 * 
	 * Lista dos parâmetros esperados pela tela alvo associada ao evento de Vencimento Data Reserva 2
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
	public EventoVencimentoDataReserva2() {
		super(Dominios.CFG_MAIL_DATA_RESERVA_2);   
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}