package ecar.evento;

import java.util.Arrays;

import ecar.util.Dominios;

/**
 */

public class EventoRecuperacaoAvaliacaoPararecer extends Evento {
	
	
    /**
     *
     * @param id
     */
    public EventoRecuperacaoAvaliacaoPararecer(Integer id) {
		super(id);
	}
	
	
	
	/**
	 * 
	 * Lista dos par�metros esperados pela tela alvo associada ao evento de Recupera��o de Parecer
	 * 
	 * Verificar forma melhor de implementar, que n�o seja pelo usando array.
	 *   
	 */
	// Aqui foram considerados apenas os itens pegos do request da p�gina principal, sem considerar os itens dos includes das p�ginas JSP. 
	private static final String parametrosEsperados[] = {"codTipoAcompanhamento",
                                        				 "referencia_hidden",
                                        				 "codAri"
                                        				};

	/**
	 * 
	 * Indicando o evento e iniciando os parametros.
	 * 
	 * Verificar forma melhor de implementar, que n�o seja pelo usando array.   
	 */
	public EventoRecuperacaoAvaliacaoPararecer() {
		super(Dominios.CFG_MAIL_RECUPERACAO_PARECER);   // Definido pelo desenvolvedor.
		
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}