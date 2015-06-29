package ecar.intercambioDados.exportacao;

import java.util.ArrayList;
import java.util.List;

import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.IConfiguracaoAtributoLivre;
import ecar.pojo.ItemEstruturaIett;

public class ExportaFuncaoConfiguravelAtributoLivreDadosGerais extends ExportaFuncaoConfiguravelAtributoLivre{

	@Override
	protected List<IConfiguracaoAtributoLivre> identificarObjeto(List<ItemEstruturaIett> listaItensEstruturaExportacao) {
		
		List<IConfiguracaoAtributoLivre> listaObjetosNegocio = new ArrayList<IConfiguracaoAtributoLivre>();
		
		listaObjetosNegocio.addAll(listaItensEstruturaExportacao);
		
		return listaObjetosNegocio;
	}

	/**
	 * Não há implementação pois o campo não deve ser impresso para Dados gerais.
	 */
	@Override
	protected void imprimirCampoCodigoObjetoNegocio(StringBuffer objetoNegocioStr, IConfiguracaoAtributoLivre objetoNegocio,ConfiguracaoCfg configuracao) {}

	/**
	 * Não há implementação pois o campo não deve ser impresso para Dados gerais.
	 */
	@Override
	protected void imprimirCampoHeaderObjetoNegocio(StringBuffer headerStr,	String labelFuncaoAba, ConfiguracaoCfg configuracaoCfg) {}

}
