package ecar.evento;

import java.util.Arrays;

import ecar.util.Dominios;

/**
 * 
 */

public class EventoLiberacaoAvaliacaoPararecer extends Evento {
	
	
    /**
     *
     * @param id
     */
    public EventoLiberacaoAvaliacaoPararecer(Integer id) {
		super(id);
	}
	
	
	
	/**
	 * 
	 * Lista dos parâmetros esperados pela tela alvo associada ao evento de Liberação de Parecer
	 * 
	 * Verificar forma melhor de implementar, que não seja pelo usando array.
	 *   
	 */
	// Aqui foram considerados apenas os itens pegos do request da página principal, sem considerar os itens dos includes das páginas JSP. 
	private static final String parametrosEsperados[] = {"codTipoAcompanhamento",
                                        				 "referencia_hidden",
                                        				 "codAri"
                                        				};

	/**
	 * 
	 * Indicando o evento e iniciando os parametros.
	 * 
	 * Verificar forma melhor de implementar, que não seja pelo usando array.   
	 */
	public EventoLiberacaoAvaliacaoPararecer() {
		super(Dominios.CFG_MAIL_LIBERACAO_PARECER);   // Definido pelo desenvolvedor.
		
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}