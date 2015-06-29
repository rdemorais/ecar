package ecar.evento;

import java.util.Arrays;

import ecar.util.Dominios;

/**
 * 
 */

public class EventoManutencaoPontoCritico extends Evento {
	
	
    /**
     *
     * @param id
     */
    public EventoManutencaoPontoCritico(Integer id) {
		super(id);
	}
	
	
	
	/**
	 * 
	 * Lista dos parâmetros esperados pela tela alvo associada ao evento de Conclusão de Acompanhamento.
	 * 
	 * Verificar forma melhor de implementar, que não seja pelo usando array.
	 *   
	 */
	// Aqui foram considerados apenas os itens pegos do request da página principal, sem considerar os itens dos includes das páginas JSP. 
	private static final String parametrosEsperados[] = {"codAba",
                                        				 "codIett"};

	/**
	 * 
	 * Indicando o evento e iniciando os parametros.
	 * 
	 * Verificar forma melhor de implementar, que não seja pelo usando array.   
	 */
	public EventoManutencaoPontoCritico() {
		super(Dominios.CFG_MAIL_MANUTENCAO_PONTO_CRITICO);   // Definido pelo desenvolvedor.
		
		parametros = Arrays.asList(parametrosEsperados);
	}
	
}