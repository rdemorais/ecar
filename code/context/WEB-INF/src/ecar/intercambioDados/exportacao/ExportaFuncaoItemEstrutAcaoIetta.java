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
import ecar.pojo.ItemEstrutAcaoIetta;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;


public class ExportaFuncaoItemEstrutAcaoIetta extends ExportaFuncao {


	@Override
	protected List<IRegistro> montarConteudo(List<ItemEstruturaIett> listaItensEstruturaExportacao, List<AcompReferenciaItemAri> listaArisEstruturaExportacao, ConfiguracaoCfg configuracao,List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref, SegurancaECAR segurancaECAR) throws ECARException {
		
		StringBuffer linha = null;
		String separadorCampos = configuracao.getSeparadorArqTXT();
		IRegistro registro = null;
		List<IRegistro> listaRegistros = new ArrayList<IRegistro>();
		ItemEstruturaIett itemEstruturaIett = null;
		
		List<ItemEstrutAcaoIetta> listaItemEstrutAcaoIettas = obterItemEstrutAcaoIetta(listaItensEstruturaExportacao);
		
		for (ItemEstrutAcaoIetta itemEstrutAcaoIetta : listaItemEstrutAcaoIettas) {
			linha = new StringBuffer();
			// se item pai existe
			itemEstruturaIett = itemEstrutAcaoIetta.getItemEstruturaIett();
			
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
			
			linha.append(itemEstrutAcaoIetta.getCodIetta());
			linha.append(separadorCampos);
			linha.append(Data.parseDate(itemEstrutAcaoIetta.getDataIetta()));
			linha.append(separadorCampos);
			linha.append(itemEstrutAcaoIetta.getDescricaoIetta());
			linha.append(separadorCampos);
			linha.append(Data.parseDateHourMinuteSecond(itemEstrutAcaoIetta.getDataInclusaoIetta()));
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
		IRegistro header; 
		
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
		headerStr.append(configuracao.getSeparadorArqTXT());
		
		headerStr.append("Data").append(separadorCampos).append("Descrição").append(separadorCampos).append("Data de Inclusão");
		
		IRegistro registro = new RegistroTXT(headerStr.toString());
		
		return registro;
	}

	/**
	 * 
	 * @param listaItensEstruturaExportacao
	 * @return
	 */
	private List<ItemEstrutAcaoIetta> obterItemEstrutAcaoIetta(List<ItemEstruturaIett> listaItensEstruturaExportacao) {
		DaoUtil<ItemEstrutAcaoIetta> daoUtil = new DaoUtil<ItemEstrutAcaoIetta>(){};
		int tamanhoListaItens = listaItensEstruturaExportacao.size();
		Long[] codIetts = new Long[tamanhoListaItens];
		for (int i = 0; i < tamanhoListaItens; i++) {
			codIetts[i] = listaItensEstruturaExportacao.get(i).getCodIett();
		}
		//return daoUtil.listaIN("itemEstruturaIett.codIett", codIetts, "indAtivoIetta", true);
		return daoUtil.listaIN("itemEstruturaIett.codIett", codIetts, null, null);
	}
	
}
