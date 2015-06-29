package ecar.servlet.relatorio.PPA;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe responsavel por instanciar objetos que carregam listagem de itens para o relatorio PPA
 * @author gabriel
 */
public class RelatorioParametroFactory {

	private RelatorioParametroFactory() {}
	
	
	/**
	 * Instancia factory recebendo o identificador de qual pesquisa realizar para recuperar itens
         * @param request
         * @param escolha identificador do item
         * @return
	 */
	public static RelatorioParametro getPesquisa( HttpServletRequest request, TipoPesquisaRelatorio escolha ){
		
		final Boolean VALIDARCRITERIO = Boolean.TRUE;
		final Boolean COMPLETO = Boolean.FALSE;
		
		switch ( escolha ) {
		case TIPO_1:
			return new CarregaItensCompleto(request, COMPLETO );
		case TIPO_2:
			return new CarregaItensCompleto(request, VALIDARCRITERIO );					
		default:
			return new CarregaItensCompleto(request, COMPLETO);
		}
		
	}
	

	
	
}
