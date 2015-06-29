/**
 * 
 */
package ecar.intercambioDados.exportacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import comum.database.DaoUtil;
import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.Pagina;

import ecar.api.facade.EcarData;
import ecar.api.facade.IndicadorResultado;
import ecar.api.facade.ItemEstrutura;
import ecar.api.facade.Previsto;
import ecar.dao.CorDao;
import ecar.dao.ItemEstrtIndResulCorIettrcorDAO;
import ecar.exception.ECARException;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.Cor;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstrtIndResulCorIettrcor;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.util.Dominios;

/**
 *
 */
public class ExportaFuncaoItemEstrtIndResulIettr extends ExportaFuncao {

	/* (non-Javadoc)
	 * @see ecar.intercambioDados.ExportaFuncao#montarConteudo(ecar.pojo.EstruturaEtt, java.util.List, ecar.pojo.ConfiguracaoCfg, java.util.List)
	 */
	@Override
	protected List<IRegistro> montarConteudo(List<ItemEstruturaIett> listaItensEstruturaExportacao, List<AcompReferenciaItemAri> listaArisEstruturaExportacao, ConfiguracaoCfg configuracao,List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref, SegurancaECAR segurancaECAR) throws ECARException {
		StringBuffer linha = null;
		String separadorCampos = configuracao.getSeparadorArqTXT();
		IRegistro registro = null;
		List<IRegistro> listaRegistros = new ArrayList<IRegistro>();
		ItemEstruturaIett itemEstruturaIett = null;
		ItemEstrtIndResulCorIettrcorDAO iettrCorDao = new ItemEstrtIndResulCorIettrcorDAO(null);
		SisGrupoAtributoSga sgaMetasFisicas = configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas();
		SisGrupoAtributoSga sgaUnidMedida = configuracao.getSisGrupoAtributoSgaByUnidMedida();
		Cor corFiltro = new Cor();
		corFiltro.setIndIndicadoresFisicosCor("S");
		List listaCores = new CorDao(null).pesquisar(corFiltro, new String[]{"ordemCor","asc"});
		
		List<ItemEstrtIndResulIettr> listaItemEstrtIndResulIettrs = obterItemEstrtIndResulIettr(listaItensEstruturaExportacao);
		
		//datas que serão usadas para verificar o valor previsto
		Set<EcarData> datas = new TreeSet<EcarData>();
		for(ItemEstruturaIett _item_: listaItensEstruturaExportacao){
			ItemEstrutura item = new ItemEstrutura(_item_);
			if(item.getMeses() != null){
				datas.addAll(item.getMeses());
			}
		}

		
		for (ItemEstrtIndResulIettr itemEstrtIndResulIettr : listaItemEstrtIndResulIettrs) {
			linha = new StringBuffer();
			// se item pai existe
			itemEstruturaIett = itemEstrtIndResulIettr.getItemEstruturaIett();
			
			if(itemEstruturaIett.getItemEstruturaIett() != null) {
				linha.append(itemEstruturaIett.getItemEstruturaIett().getCodIett());
			}
			
			linha.append(separadorCampos);
			linha.append(itemEstruturaIett.getCodIett());
			linha.append(separadorCampos);
			
			linha.append(itemEstrtIndResulIettr.getCodIettir());
			linha.append(separadorCampos);
			
			//Inicio dos campos da tela de metas/indicadores
			linha.append(Pagina.trocaNull(itemEstrtIndResulIettr.getNomeIettir()));
			linha.append(separadorCampos);
			
			//Exibe esse campo apenas se o grupo de metas estiver configurado
			if (sgaMetasFisicas != null){
				if (itemEstrtIndResulIettr.getSisAtributoSatb() != null){
					linha.append(itemEstrtIndResulIettr.getSisAtributoSatb().getDescricaoSatb());
				}
				linha.append(separadorCampos);
			}
			
			linha.append(Pagina.trocaNull(itemEstrtIndResulIettr.getDescricaoIettir()));
			linha.append(separadorCampos);
			
			
			//Se o grupo de unidade de medida estiver configurado
			//usa o grupo, caso contrário usa a unidade de medida da própria meta
			if (sgaUnidMedida != null) {
				if (itemEstrtIndResulIettr.getCodUnidMedidaIettr() != null){
					linha.append(itemEstrtIndResulIettr.getCodUnidMedidaIettr().getDescricaoSatb());
				} 
			} else {
				linha.append(itemEstrtIndResulIettr.getUnidMedidaIettr());
			}
			linha.append(separadorCampos);
			//Nome do agrupamento para o Gráfico de Grupos
			linha.append(Pagina.trocaNull(itemEstrtIndResulIettr.getLabelGraficoGrupoIettir()));
			linha.append(separadorCampos);
			
			//Formato
			linha.append(Pagina.trocaFormato(itemEstrtIndResulIettr.getIndTipoQtde()));
			linha.append(separadorCampos);
			//Projeção
			linha.append(Pagina.trocaNull(itemEstrtIndResulIettr.getIndProjecaoIettr()));
			linha.append(separadorCampos);
			//Acumulável
			linha.append(Pagina.trocaNull(itemEstrtIndResulIettr.getIndAcumulavelIettr()));
			linha.append(separadorCampos);
			//Valor final
			linha.append(Pagina.trocaNull(itemEstrtIndResulIettr.getIndValorFinalIettr()));
			linha.append(separadorCampos);
			//É Público?
			linha.append(Pagina.trocaNull(itemEstrtIndResulIettr.getIndPublicoIettr()));
			linha.append(separadorCampos);
			//Qtde. Prevista Informada por Local?
			linha.append(Pagina.trocaNull(itemEstrtIndResulIettr.getIndPrevPorLocal()));
			linha.append(separadorCampos);
			//Qtde. Realizada Informada por Local?
			linha.append(Pagina.trocaNull(itemEstrtIndResulIettr.getIndRealPorLocal()));
			linha.append(separadorCampos);
			//Valor inicial de referência
			linha.append(Pagina.trocaNullQtdValor(itemEstrtIndResulIettr.getIndiceMaisRecenteIettr(), "V"));
			linha.append(separadorCampos);
			//Data de apuração
			linha.append(Data.parseDate(itemEstrtIndResulIettr.getDataApuracaoIettr()));
			linha.append(separadorCampos);
//			//Base geográfica - O CAMPO NÃO ESTÁ SENDO SALVO
//			linha.append(Dominios.STRING_VAZIA);
//			linha.append(separadorCampos);
			//Periodicidade
			if (itemEstrtIndResulIettr.getPeriodicidadePrdc() != null){
				linha.append(itemEstrtIndResulIettr.getPeriodicidadePrdc().getDescricaoPrdc());
			}
			linha.append(separadorCampos);
			//Fonte
			linha.append(Pagina.trocaNull(itemEstrtIndResulIettr.getFonteIettr()));
			linha.append(separadorCampos);
			//Fórmula
			linha.append(Pagina.trocaNull(itemEstrtIndResulIettr.getFormulaIettr()));
			linha.append(separadorCampos);
			//Utiliza Sinalização
			linha.append(Pagina.trocaNull(itemEstrtIndResulIettr.getIndSinalizacaoIettr()));
			linha.append(separadorCampos);
			
			//Lista de cores
			List<ItemEstrtIndResulCorIettrcor> listaCoresIettrcor = iettrCorDao.listarIettrcorAtivosPorIettrOrderByValorPrimEmailIettrcor(itemEstrtIndResulIettr);
			String menorValor = ItemEstrtIndResulCorIettrcorDAO.MENOR_VALOR;
			for (ItemEstrtIndResulCorIettrcor itemEstrtIndResulCorIettrcor : listaCoresIettrcor) {
				//Estado
				linha.append(itemEstrtIndResulCorIettrcor.getCor().getSignificadoCor());
				linha.append(separadorCampos);
				//Menor Valor
				linha.append(menorValor);
				linha.append(separadorCampos);
				//Maior Valor
				if (itemEstrtIndResulCorIettrcor.getIndMaiorValorIettrcor() != null && itemEstrtIndResulCorIettrcor.getIndMaiorValorIettrcor().equals(Dominios.SIM)){
					linha.append(ItemEstrtIndResulCorIettrcorDAO.MAIOR_VALOR);
				} else {
					linha.append(Pagina.trocaNullQtdValor(itemEstrtIndResulCorIettrcor.getValorPrimEmailIettrcor(), "V"));
					menorValor = Pagina.trocaNullQtdValor(itemEstrtIndResulCorIettrcor.getValorPrimEmailIettrcor(), "V");
				}		
				linha.append(separadorCampos);
				
			}
			
			for (int count = listaCoresIettrcor.size(); count < listaCores.size(); count++) {
				linha.append(separadorCampos);
				linha.append(separadorCampos);
				linha.append(separadorCampos);
			}
			//Serviço Valor Previsto
			if (itemEstrtIndResulIettr.getPrevistoServicoSer() != null){
				linha.append(itemEstrtIndResulIettr.getPrevistoServicoSer().getNomeSer());
			}
			linha.append(separadorCampos);
			//Automático
			linha.append(itemEstrtIndResulIettr.getIndTipoAtualizacaoPrevisto());
			linha.append(separadorCampos);
			//Serviço Valor Realizado
			if (itemEstrtIndResulIettr.getRealizadoServicoSer() != null){
				linha.append(itemEstrtIndResulIettr.getRealizadoServicoSer().getNomeSer());
			}
			linha.append(separadorCampos);
			//Automático
			linha.append(itemEstrtIndResulIettr.getIndTipoAtualizacaoRealizado());

			IndicadorResultado indicador = new IndicadorResultado(itemEstrtIndResulIettr);

			for(EcarData data: datas){
				linha.append(separadorCampos);
				Previsto p = indicador.getPrevistoMensal(data);
				if(p != null){
					linha.append(Pagina.trocaNullQtdValor(p.getValorPrevisto(), indicador.getRealObject().getIndTipoQtde()));
				}
				
			}

			registro = new RegistroTXT(linha.toString());
			listaRegistros.add(registro);
		}
		
		return listaRegistros;
	}

	/* (non-Javadoc)
	 * @see ecar.intercambioDados.ExportaFuncao#montarSegundoHeader(ecar.pojo.EstruturaEtt, ecar.pojo.ConfiguracaoCfg, ecar.pojo.FuncaoFun, java.util.List)
	 */
	@Override
	protected IRegistro montarSegundoHeader(EstruturaEtt estruturaEtt,List<ItemEstruturaIett> listaItensEstruturaExportacao, String labelFuncaoAba, ConfiguracaoCfg configuracao, List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref) throws ECARException {
		String separadorCampos = configuracao.getSeparadorArqTXT();
		SisGrupoAtributoSga sgaMetasFisicas = configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas();
		SisGrupoAtributoSga sgaUnidMedida = configuracao.getSisGrupoAtributoSgaByUnidMedida();
		
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
		
		headerStr.append(ConstantesECAR.ID);
		headerStr.append(ConstantesECAR.UNDERLINE);
		headerStr.append(labelFuncaoAba);
		headerStr.append(separadorCampos);
		
		//Inicio dos campos da tela de metas/indicadores
		headerStr.append("Nome");
		headerStr.append(separadorCampos);
		
		//Exibe esse campo apenas se o grupo de metas estiver configurado
		if (sgaMetasFisicas != null){
			headerStr.append(sgaMetasFisicas.getDescricaoSga());
			headerStr.append(separadorCampos);
		}
		headerStr.append("Informações Complementares");
		headerStr.append(separadorCampos);
		//Se o grupo de unidade de medida estiver configurado
		//usa o grupo, caso contrário usa a unidade de medida da própria meta
		if (sgaUnidMedida != null) {
			headerStr.append(sgaUnidMedida.getDescricaoSga());
			headerStr.append(separadorCampos);
		} else {
			headerStr.append("Unidade de Medida");
			headerStr.append(separadorCampos);
		}
		headerStr.append("Nome do agrupamento para o Gráfico de Grupos");
		headerStr.append(separadorCampos);
		headerStr.append("Formato");
		headerStr.append(separadorCampos);
		headerStr.append("Projeção?");
		headerStr.append(separadorCampos);
		headerStr.append("Acumulável?");
		headerStr.append(separadorCampos);
		headerStr.append("Valor final");
		headerStr.append(separadorCampos);
		headerStr.append("É Público?");
		headerStr.append(separadorCampos);
		headerStr.append("Qtde. Prevista Informada por Local?");
		headerStr.append(separadorCampos);
		headerStr.append("Qtde. Realizada Informada por Local?");
		headerStr.append(separadorCampos);
		headerStr.append("Valor inicial de referência");
		headerStr.append(separadorCampos);
		headerStr.append("Data de Apuração");
		headerStr.append(separadorCampos);
		//
//		headerStr.append("Base Geográfica");
//		headerStr.append(separadorCampos);
		headerStr.append("Periodicidade");
		headerStr.append(separadorCampos);
		headerStr.append("Fonte");
		headerStr.append(separadorCampos);
		headerStr.append("Fórmula");
		headerStr.append(separadorCampos);
		headerStr.append("Utiliza Sinalização");
		headerStr.append(separadorCampos);
		
		Cor corFiltro = new Cor();
		corFiltro.setIndIndicadoresFisicosCor("S");
		List listaCores = new CorDao(null).pesquisar(corFiltro, new String[]{"ordemCor","asc"});
		//Lista de cores configuradas para indicadores físicos
		for (int i = 1;i <= listaCores.size();i++) {
			headerStr.append ("Estado "+i);
			headerStr.append (configuracao.getSeparadorArqTXT());
			headerStr.append ("Maior que");
			headerStr.append (configuracao.getSeparadorArqTXT());
			headerStr.append ("Até");
			headerStr.append (configuracao.getSeparadorArqTXT());
		}
		headerStr.append("Serviço Valor Previsto");
		headerStr.append(separadorCampos);
		headerStr.append("Automático");
		headerStr.append(separadorCampos);
		headerStr.append("Serviço Valor Realizado");
		headerStr.append(separadorCampos);
		headerStr.append("Automático");

		
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
	 * @param listaItensEstruturaExportacao
	 * @return
	 */
	private List<ItemEstrtIndResulIettr> obterItemEstrtIndResulIettr(List<ItemEstruturaIett> listaItensEstruturaExportacao) {
		DaoUtil<ItemEstrtIndResulIettr> daoUtil = new DaoUtil<ItemEstrtIndResulIettr>(){};
		int tamanhoListaItens = listaItensEstruturaExportacao.size();
		Long[] codIetts = new Long[tamanhoListaItens];
		for (int i = 0; i < tamanhoListaItens; i++) {
			codIetts[i] = listaItensEstruturaExportacao.get(i).getCodIett();
		}
		return daoUtil.listaIN("itemEstruturaIett.codIett", codIetts, new String[]{"indAtivoIettr"}, new String[]{"S"});
	}

}
