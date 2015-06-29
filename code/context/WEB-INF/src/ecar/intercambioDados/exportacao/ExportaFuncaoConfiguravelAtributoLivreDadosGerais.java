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
	 * N�o h� implementa��o pois o campo n�o deve ser impresso para Dados gerais.
	 */
	@Override
	protected void imprimirCampoCodigoObjetoNegocio(StringBuffer objetoNegocioStr, IConfiguracaoAtributoLivre objetoNegocio,ConfiguracaoCfg configuracao) {}

	/**
	 * N�o h� implementa��o pois o campo n�o deve ser impresso para Dados gerais.
	 */
	@Override
	protected void imprimirCampoHeaderObjetoNegocio(StringBuffer headerStr,	String labelFuncaoAba, ConfiguracaoCfg configuracaoCfg) {}

}
