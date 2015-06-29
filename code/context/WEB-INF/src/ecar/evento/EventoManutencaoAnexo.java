package ecar.evento;

import java.util.Arrays;

import ecar.util.Dominios;

/**
 *
 * @author 70744416353
 */
public class EventoManutencaoAnexo extends Evento{

	/**
	 * 
	 * Lista dos parâmetros esperados pela tela alvo associada ao evento de Conclusão de Acompanhamento.
	 * 
	 * Verificar forma melhor de implementar, que não seja pelo usando array.
	 *   
	 */
	// Aqui foram considerados apenas os itens pegos do request da página principal, sem considerar os itens dos includes das páginas JSP. 
	private static final String parametrosEsperados[] = {"codIett",
                                        				 "codAba",
                                        				 "codIettuc"};
	
	/**
	 * 
	 * Indicando o evento e iniciando os parametros.
	 * 
	 * Verificar forma melhor de implementar, que não seja pelo usando array.   
	 */
	public EventoManutencaoAnexo() {
		super(Dominios.CFG_MAIL_MANUTENCAO_ANEXO);   // Definido pelo desenvolvedor.
		
		parametros = Arrays.asList(parametrosEsperados);
	}

}
