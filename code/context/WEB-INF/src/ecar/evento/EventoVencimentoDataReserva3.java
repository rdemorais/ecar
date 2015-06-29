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

public class EventoVencimentoDataReserva3 extends Evento {
	
	/**
	 * 
	 * Lista dos parâmetros esperados pela tela alvo associada ao evento de Vencimento Data Reserva 3
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
	public EventoVencimentoDataReserva3() {
		super(Dominios.CFG_MAIL_DATA_RESERVA_3);   
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}