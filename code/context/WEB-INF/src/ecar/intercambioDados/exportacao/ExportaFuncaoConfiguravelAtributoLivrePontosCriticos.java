package ecar.intercambioDados.exportacao;

import java.util.ArrayList;
import java.util.List;

import comum.database.Dao;
import comum.util.ConstantesECAR;

import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.IConfiguracaoAtributoLivre;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.PontoCriticoPtc;

public class ExportaFuncaoConfiguravelAtributoLivrePontosCriticos extends ExportaFuncaoConfiguravelAtributoLivre {

	@Override
	protected List<IConfiguracaoAtributoLivre> identificarObjeto(List<ItemEstruturaIett> listaItensEstruturaExportacao) {
		

		Dao dao = new Dao();
		List<PontoCriticoPtc> listaPontosCriticos = new ArrayList<PontoCriticoPtc>();
		
		if (!listaItensEstruturaExportacao.isEmpty()) {
			StringBuffer hql = new StringBuffer ("select ptc from PontoCriticoPtc ptc inner join ptc.itemEstruturaIett item where ptc.indExcluidoPtc = 'N' ");
			hql.append(" and item.codIett in (");

			for (int i = 0; i < listaItensEstruturaExportacao.size(); i++) {
				hql.append(listaItensEstruturaExportacao.get(i).getCodIett());
				if (i < listaItensEstruturaExportacao.size()-1) {
					hql.append(",");
				}
			}
			hql.append(" ) ");
			
			listaPontosCriticos = dao.consultarPorHQL(hql.toString());
		}
		
		List<IConfiguracaoAtributoLivre> listaObjetosNegocio = new ArrayList<IConfiguracaoAtributoLivre>();
		
		listaObjetosNegocio.addAll(listaPontosCriticos);
		
		return listaObjetosNegocio;
	}

	@Override
	protected void imprimirCampoCodigoObjetoNegocio(StringBuffer objetoNegocioStr, IConfiguracaoAtributoLivre objetoNegocio,ConfiguracaoCfg configuracao) {
		objetoNegocioStr.append(configuracao.getSeparadorArqTXT());
		objetoNegocioStr.append(objetoNegocio.getCodigo());
	}

	@Override
	protected void imprimirCampoHeaderObjetoNegocio(StringBuffer headerStr,	String labelFuncaoAba, ConfiguracaoCfg configuracao) {
		headerStr.append(configuracao.getSeparadorArqTXT());
		headerStr.append(ConstantesECAR.ID);
		headerStr.append(ConstantesECAR.UNDERLINE);
		headerStr.append(labelFuncaoAba);
	}

}
