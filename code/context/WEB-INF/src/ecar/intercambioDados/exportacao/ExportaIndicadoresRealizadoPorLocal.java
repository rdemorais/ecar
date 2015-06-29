/**
 * 
 */
package ecar.intercambioDados.exportacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import comum.util.ConstantesECAR;
import comum.util.Pagina;

import ecar.api.facade.EcarData;
import ecar.api.facade.IndicadorResultado;
import ecar.api.facade.ItemEstrutura;
import ecar.api.facade.Local;
import ecar.api.facade.RealizadoLocal;
import ecar.dao.AcompRealFisicoDao;
import ecar.dao.AcompRealFisicoLocalDao;
import ecar.exception.ECARException;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompRealFisicoLocalArfl;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;
import ecar.util.Dominios;

/**
 *
 */
public class ExportaIndicadoresRealizadoPorLocal extends ExportaFuncao {

	/* (non-Javadoc)
	 * @see ecar.intercambioDados.ExportaFuncao#montarConteudo(ecar.pojo.EstruturaEtt, java.util.List, ecar.pojo.ConfiguracaoCfg, java.util.List)
	 */
	@Override
	protected List<IRegistro> montarConteudo(List<ItemEstruturaIett> listaItensEstruturaExportacao, List<AcompReferenciaItemAri> listaArisEstruturaExportacao, ConfiguracaoCfg configuracao, List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref, SegurancaECAR segurancaECAR) throws ECARException {

		StringBuffer linha = null;
		String separadorCampos = configuracao.getSeparadorArqTXT();
		IRegistro registro = null;
		List<IRegistro> listaRegistros = new ArrayList<IRegistro>();
		ItemEstruturaIett itemEstruturaIett = null;
		AcompRealFisicoLocalDao acompRealFisicoLocalDao = new AcompRealFisicoLocalDao(null);
		
		List<AcompRealFisicoArf> listaARFs = obterARFs(listaArisEstruturaExportacao);

		//datas que serão usadas para exportar o valor realizado
		Set<EcarData> datas = new TreeSet<EcarData>();
		for(ItemEstruturaIett _item_: listaItensEstruturaExportacao){
			ItemEstrutura item = new ItemEstrutura(_item_);
			if(item.getMeses() != null){
				datas.addAll(item.getMeses());
			}
		}		
	
		for (AcompRealFisicoArf arf : listaARFs) {

			IndicadorResultado indicador = new IndicadorResultado(arf.getItemEstrtIndResulIettr());
			
			if(indicador.isIndicadorPorLocal() == false){
				continue;
			}			
			
			//Recupera os locais
			List<AcompRealFisicoLocalArfl> listLocais = acompRealFisicoLocalDao.getAcompRealFisicoLocalByArf(arf);
			
			for (AcompRealFisicoLocalArfl arfl : listLocais) {
			
				Local local = new Local(arfl.getLocalItemLit()); 
				
				linha = new StringBuffer();
				
				// se item pai existe
				itemEstruturaIett = arf.getItemEstruturaIett();
				
				//Primeiro Campo
				if(itemEstruturaIett.getItemEstruturaIett() != null) {
					linha.append(itemEstruturaIett.getItemEstruturaIett().getCodIett());
				}
				
				//Segundo Campo
				linha.append(separadorCampos);
				linha.append(itemEstruturaIett.getCodIett());
				linha.append(separadorCampos);
				
				//Terceiro Campo
				linha.append(arf.getItemEstrtIndResulIettr().getCodIettir());
				linha.append(separadorCampos);
				
				//Nome do Indicador
				linha.append(Pagina.trocaNull(arf.getItemEstrtIndResulIettr().getNomeIettir()));
				linha.append(separadorCampos);
	
				//Local
				linha.append(local.getNome());
	
				List<RealizadoLocal> listRealizadoLocal = local.getRealizados();
				
				for(EcarData data: datas){
					linha.append(separadorCampos);
					
					//boolean realizadoInformado = false;
					for (RealizadoLocal r: listRealizadoLocal){
						
						int mes = r.getRealObject().getAcompRealFisicoArf().getMesArf().intValue();
						int ano = r.getRealObject().getAcompRealFisicoArf().getAnoArf().intValue();;
						EcarData d = new EcarData(mes, ano);
						
						if (data.equals(d)){ 										
							linha.append(Pagina.trocaNullQtdValor(r.getRealizado(), indicador.getRealObject().getIndTipoQtde()));
							//realizadoInformado = true;
						}
					}
					//if(!realizadoInformado){
					//	linha.append(ConstantesECAR.HIFEN);
					//}
					
				}
		
				registro = new RegistroTXT(linha.toString());
				listaRegistros.add(registro);
			
			}
		}
		
		return listaRegistros;
	}

	/* (non-Javadoc)
	 * @see ecar.intercambioDados.ExportaFuncao#montarSegundoHeader(ecar.pojo.EstruturaEtt, ecar.pojo.ConfiguracaoCfg, ecar.pojo.FuncaoFun, java.util.List)
	 */
	@Override
	protected IRegistro montarSegundoHeader(EstruturaEtt estruturaEtt,List<ItemEstruturaIett> listaItensEstruturaExportacao, String labelFuncaoAba, ConfiguracaoCfg configuracao, List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref) throws ECARException {
		
		String separadorCampos = configuracao.getSeparadorArqTXT();
		
		StringBuffer headerStr = new StringBuffer(); 
		
		//Inicio Primeiro campo
		headerStr.append(ConstantesECAR.ID_PAI);
		
		if (estruturaEtt.getEstruturaEtt()!= null){
			headerStr.append(ConstantesECAR.UNDERLINE);
			headerStr.append(estruturaEtt.getEstruturaEtt().getNomeEtt());
		}
		
		headerStr.append(separadorCampos);

		//Inicio Segundo campo
		headerStr.append(ConstantesECAR.ID);
		headerStr.append(ConstantesECAR.UNDERLINE);
		headerStr.append(estruturaEtt.getNomeEtt());
		headerStr.append(separadorCampos);
		//Fim Segundo campo
		
		//Inicio Terceiro Campo
		headerStr.append(ConstantesECAR.ID);
		headerStr.append(ConstantesECAR.UNDERLINE);
		headerStr.append(labelFuncaoAba);
		headerStr.append(separadorCampos);
		//Fim Terceiro Campo
		
		//Nome do Indicador
		headerStr.append("Nome");
		headerStr.append(separadorCampos);

		//Local
		headerStr.append("Local");

		//Datas
		Set<EcarData> datas = new TreeSet<EcarData>();
		
		for(ItemEstruturaIett _item_: listaItensEstruturaExportacao){
			ItemEstrutura item = new ItemEstrutura(_item_);
			if(item.getMeses() != null){
				datas.addAll(item.getMeses());
			}
		}	
						
		for(EcarData data: datas){
			headerStr.append(configuracao.getSeparadorArqTXT());
			headerStr.append(data.getDataFormatada());
		}			

		IRegistro registro = new RegistroTXT(headerStr.toString());
		
		return registro;
	}
	
	/**
	 * 
	 * @param listaArisEstruturaExportacao
	 * @return
	 * @throws Exception 
	 */
	private List<AcompRealFisicoArf> obterARFs(List<AcompReferenciaItemAri> listaArisEstruturaExportacao) {
		
		List<AcompRealFisicoArf> listaRetorno = new ArrayList<AcompRealFisicoArf>();
		
		for(AcompReferenciaItemAri ari : listaArisEstruturaExportacao){
			AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(null);
			List<AcompRealFisicoArf> arfs = new ArrayList<AcompRealFisicoArf>();
			try {
				arfs = acompRealFisicoDao.getIndResulByAcompRefItemBySituacao(ari, Dominios.TODOS, false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (AcompRealFisicoArf arf : arfs){
				if (!listaRetorno.contains(arf)){
					listaRetorno.add(arf);
				}
			}
		}
		
		return listaRetorno;
		
	}

	/* (non-Javadoc)
	 * @see ecar.intercambioDados.exportacao.ExportaFuncao#montarNomeArquivoFuncao(java.lang.StringBuffer, ecar.pojo.EstruturaEtt, java.lang.String)
	 */
	@Override
	protected void montarNomeArquivoFuncao(StringBuffer nomeArquivoTemp, EstruturaEtt estruturaEtt, String labelFuncaoAba) throws ECARException {

		nomeArquivoTemp.append(labelFuncaoAba.replace("/", " "));
		nomeArquivoTemp.append(ConstantesECAR.UNDERLINE);
		nomeArquivoTemp.append("RealizadoPorLocal");
    
	}

	
	
}
