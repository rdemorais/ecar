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

public class EventoVencimentoDataReserva4 extends Evento {
	
	/**
	 * 
	 * Lista dos parâmetros esperados pela tela alvo associada ao evento de Vencimento Data Reserva 4
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
	public EventoVencimentoDataReserva4() {
		super(Dominios.CFG_MAIL_DATA_RESERVA_4);   
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}