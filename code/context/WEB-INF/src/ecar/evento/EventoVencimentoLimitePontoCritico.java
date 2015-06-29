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

public class EventoVencimentoLimitePontoCritico extends Evento {
	
	/**
	 * 
	 * Lista dos parâmetros esperados pela tela alvo associada ao evento de Vencimento Data Termino Item
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
	public EventoVencimentoLimitePontoCritico() {
		super(Dominios.CFG_MAIL_VENCIMENTO_LIMITE_PONTO_CRITICO);   
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}