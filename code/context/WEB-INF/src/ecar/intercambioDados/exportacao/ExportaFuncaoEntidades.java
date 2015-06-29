package ecar.intercambioDados.exportacao;

import java.util.ArrayList;
import java.util.List;

import comum.database.DaoUtil;
import comum.util.ConstantesECAR;
import comum.util.Data;

import ecar.dao.EstruturaFuncaoDao;
import ecar.exception.ECARException;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstrutEntidadeIette;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;

public class ExportaFuncaoEntidades extends ExportaFuncao {

	@Override
	protected List<IRegistro> montarConteudo(List<ItemEstruturaIett> listaItensEstruturaExportacao, List<AcompReferenciaItemAri> listaArisEstruturaExportacao, ConfiguracaoCfg configuracao,List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref, SegurancaECAR segurancaECAR) throws ECARException {
		
		StringBuffer linha = null;
		String separadorCampos = configuracao.getSeparadorArqTXT();
		IRegistro registro = null;
		List<IRegistro> listaRegistros = new ArrayList<IRegistro>();		
		
		ItemEstruturaIett itemEstruturaIett = null;
		
		List<ItemEstrutEntidadeIette> listaItemEstrutEntidadeIettes = obterItemEstrutEntidadeIette(listaItensEstruturaExportacao);
		
		for(ItemEstrutEntidadeIette itemEstrutEntidadeIette : listaItemEstrutEntidadeIettes) {
			linha = new StringBuffer();
			itemEstruturaIett = itemEstrutEntidadeIette.getItemEstruturaIett();
			
			// se item pai existe			
			if(itemEstruturaIett.getItemEstruturaIett() != null) {
				linha.append(itemEstruturaIett.getItemEstruturaIett().getCodIett());
				linha.append(separadorCampos);
				linha.append(itemEstruturaIett.getCodIett());
				linha.append(separadorCampos);
			// senao
			} else {
				linha.append(separadorCampos);
				linha.append(itemEstruturaIett.getCodIett());
				linha.append(separadorCampos);
			}
			
			// campos: ID, Nome, Atuação (Tipo Participação), Codigo Atuação, Participação, Data inicio, Data Fim
			linha.append(itemEstrutEntidadeIette.getComp_id().getCodEnt());
			linha.append(separadorCampos);
			linha.append(itemEstrutEntidadeIette.getEntidadeEnt().getNomeEnt());
			linha.append(separadorCampos);
			linha.append(itemEstrutEntidadeIette.getTipoParticipacaoTpp().getDescricaoTpp());
			linha.append(separadorCampos);
			linha.append(itemEstrutEntidadeIette.getTipoParticipacaoTpp().getCodTpp());
			linha.append(separadorCampos);
			linha.append(itemEstrutEntidadeIette.getDescricaoIette());
			linha.append(separadorCampos);
			linha.append(Data.parseDateHourMinuteSecond(itemEstrutEntidadeIette.getDataInicioIette()));
			linha.append(separadorCampos);
			linha.append(Data.parseDateHourMinuteSecond(itemEstrutEntidadeIette.getDataFimIette()));
			
			registro = new RegistroTXT(linha.toString());
			listaRegistros.add(registro);
		}
		
		return listaRegistros;
	}

	@Override
	protected IRegistro montarSegundoHeader(EstruturaEtt estruturaEtt,List<ItemEstruturaIett> listaItensEstruturaExportacao, String labelFuncaoAba, ConfiguracaoCfg configuracao,List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref) throws ECARException{
		String separadorCampos = configuracao.getSeparadorArqTXT();
		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(null);
		
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

		//Inicio Terceiro campo

		headerStr.append(ConstantesECAR.ID);
		headerStr.append(ConstantesECAR.UNDERLINE);
		headerStr.append(labelFuncaoAba);
		headerStr.append(separadorCampos);
		//Fim Terceiro campo
		
		//Inicio demais campos
		headerStr.append("Nome").append(separadorCampos).append("Atuação").append(separadorCampos).append("Código Atuação").append(separadorCampos);
		headerStr.append("Participação").append(separadorCampos).append("Data Início").append(separadorCampos).append("Data Fim");
		//Fim demais campos
		
		IRegistro registro = new RegistroTXT(headerStr.toString());
		
		return registro;
	}
	
	/**
	 * 
	 * @param listaItensEstruturaExportacao
	 * @return
	 */
	private List<ItemEstrutEntidadeIette> obterItemEstrutEntidadeIette(List<ItemEstruturaIett> listaItensEstruturaExportacao) {
		DaoUtil<ItemEstrutEntidadeIette> daoUtil = new DaoUtil<ItemEstrutEntidadeIette>(){};
		int tamanhoListaItens = listaItensEstruturaExportacao.size();
		Long[] codIetts = new Long[tamanhoListaItens];
		for (int i = 0; i < tamanhoListaItens; i++) {
			codIetts[i] = listaItensEstruturaExportacao.get(i).getCodIett();
		}
		//return daoUtil.listaIN("itemEstruturaIett.codIett", codIetts, "indAtivoIetta", true);
		return daoUtil.listaIN("itemEstruturaIett.codIett", codIetts, null, null);
	}
	
}
