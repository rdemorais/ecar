package ecar.evento;

import java.util.Arrays;

import ecar.util.Dominios;

/**
 *
 * @author 70744416353
 */
public class EventoRecuperacaoAcompanhamento extends Evento{

	/**
	 * 
	 * Lista dos par�metros esperados pela tela alvo associada ao evento de Conclus�o de Acompanhamento.
	 * 
	 * Verificar forma melhor de implementar, que n�o seja pelo usando array.
	 *   
	 */
	// Aqui foram considerados apenas os itens pegos do request da p�gina principal, sem considerar os itens dos includes das p�ginas JSP. 
	private static final String parametrosEsperados[] = {//"itemDoNivelClicado",
														 "referencia_hidden",
														 "codAri",
														 //"periodo",
														 //"codUsuario",
														 "codTipoAcompanhamento",
														 //"mesReferencia",
														 //"codSrl"
														 /*"itemDoNivelClicado",
	                                       				 "codTipoAcompanhamento",
                                        				 "referencia_hidden",
                                        				 "codAri",
                                        				 "periodo",
                                        				 "niveisPlanejamento",
                                        				 "recarregaCodArisControle",
                                        				 "orgaoResponsavel",
                                        				 "primeiroIettClicado",
                                        				 "primeiroAriClicado",
										   				 "codArisControle"*/};

	/**
	 * 
	 * Indicando o evento e iniciando os parametros.
	 * 
	 * Verificar forma melhor de implementar, que n�o seja pelo usando array.   
	 */
	public EventoRecuperacaoAcompanhamento() {
		super(Dominios.CFG_MAIL_RECUPERACAO_ACOMPANHAMENTO);   // Definido pelo desenvolvedor.
		
		parametros = Arrays.asList(parametrosEsperados);
	}

}
