package ecar.intercambioDados.exportacao;

import java.util.ArrayList;
import java.util.List;

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Data;

import ecar.dao.EstruturaFuncaoDao;
import ecar.exception.ECARException;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ApontamentoApt;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.PontoCriticoPtc;

public class ExportaSubFuncaoApontamentos extends ExportaFuncao {

	private static final String DATA = "Data";
	private static final String AUTOR = "Autor";
	private static final String TEXTO = "Texto";
	
	
	@Override
	protected List<IRegistro> montarConteudo(List<ItemEstruturaIett> listaItensEstruturaExportacao, List<AcompReferenciaItemAri> listaArisEstruturaExportacao, ConfiguracaoCfg configuracao,List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref, SegurancaECAR segurancaECAR) throws ECARException {
		
		List<ApontamentoApt> listaApontamentos = obterApontamentos(listaItensEstruturaExportacao);
		List<IRegistro> listaRegistrosApontamentos = new ArrayList<IRegistro>();
		IRegistro registroApontamento;
		
		for (ApontamentoApt apontamento : listaApontamentos) {
			
			StringBuffer headerStr = new StringBuffer(); 
			ItemEstruturaIett itemEstruturaProprietario = apontamento.getPontoCriticoPtc().getItemEstruturaIett();
			PontoCriticoPtc pontoCriticoProprietario = apontamento.getPontoCriticoPtc();
			
			//Inicio primeiro campo se houver
			if (itemEstruturaProprietario.getItemEstruturaIett() != null){
				headerStr.append(itemEstruturaProprietario.getItemEstruturaIett().getCodIett());
				headerStr.append(configuracao.getSeparadorArqTXT());
			} else {
				headerStr.append(configuracao.getSeparadorArqTXT());
			}
			//fim primeiro campo se houver

			//Inicio segundo campo
			headerStr.append(itemEstruturaProprietario.getCodIett());
			headerStr.append(configuracao.getSeparadorArqTXT());
			//fim segundo campo			

			//Inicio terceiro campo
			headerStr.append(pontoCriticoProprietario.getCodPtc());
			headerStr.append(configuracao.getSeparadorArqTXT());
			//fim terceiro campo			

			//Inicio quarto campo
			headerStr.append(apontamento.getCodApt());
			headerStr.append(configuracao.getSeparadorArqTXT());
			//fim quarto campo			

			//Inicio quinto campo
			headerStr.append(Data.parseDate(apontamento.getDataInclusaoApt()));
			headerStr.append(configuracao.getSeparadorArqTXT());
			//fim quinto campo			

			//Inicio sexto campo
			headerStr.append(apontamento.getUsuarioUsu().getNomeUsu());
			headerStr.append(configuracao.getSeparadorArqTXT());
			//fim sexto campo			

			//Inicio setimo campo
			headerStr.append(apontamento.getTextoApt());
			//fim setimo campo			

			registroApontamento =  new RegistroTXT(headerStr.toString());
			
			listaRegistrosApontamentos.add(registroApontamento);
		}
		
		return listaRegistrosApontamentos;
	}


	@Override
	protected IRegistro montarSegundoHeader(EstruturaEtt estruturaEtt, List<ItemEstruturaIett> listaItensEstruturaExportacao, String labelFuncaoAba, ConfiguracaoCfg configuracao, List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref)
			throws ECARException {
		
		StringBuffer headerStr = new StringBuffer(); 
		IRegistro header; 
		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(null);
		//Inicio Primeiro campo
		headerStr.append(ConstantesECAR.ID_PAI);
		
		if (estruturaEtt.getEstruturaEtt()!= null){
			headerStr.append(ConstantesECAR.UNDERLINE);
			headerStr.append(estruturaEtt.getEstruturaEtt().getNomeEtt());
		}
		
		headerStr.append(configuracao.getSeparadorArqTXT());
		//Fim Primeiro campo

		//Inicio Segundo campo
		headerStr.append(ConstantesECAR.ID);
		headerStr.append(ConstantesECAR.UNDERLINE);
		headerStr.append(estruturaEtt.getNomeEtt());

		headerStr.append(configuracao.getSeparadorArqTXT());
		//Fim Segundo campo

		//Inicio terceiro campo
		String labelFuncaoPontosCriticos  = estruturaFuncaoDao.getLabelFuncaoPontosCriticos(estruturaEtt);
		
		headerStr.append(ConstantesECAR.ID);
		headerStr.append(ConstantesECAR.UNDERLINE);
		headerStr.append(labelFuncaoPontosCriticos);
		headerStr.append(configuracao.getSeparadorArqTXT());
		//Fim do terceiro campo

		
		//Inicio quarto campo
		headerStr.append(ConstantesECAR.ID);
		headerStr.append(ConstantesECAR.UNDERLINE);
		headerStr.append(labelFuncaoAba);
		headerStr.append(configuracao.getSeparadorArqTXT());
		//Fim do quarto campo
		
		headerStr.append(ExportaSubFuncaoApontamentos.DATA);
		headerStr.append(configuracao.getSeparadorArqTXT());
		headerStr.append(ExportaSubFuncaoApontamentos.AUTOR);
		headerStr.append(configuracao.getSeparadorArqTXT());
		headerStr.append(ExportaSubFuncaoApontamentos.TEXTO);
		
		header = new RegistroTXT(headerStr.toString());
		
		return header;
	}

	
	private List<ApontamentoApt> obterApontamentos(List<ItemEstruturaIett> listaItensEstruturaExportacao) {
		
		Dao dao = new Dao();
		List<ApontamentoApt> listaApontamentos = new ArrayList<ApontamentoApt>();
		
		if (!listaItensEstruturaExportacao.isEmpty()) {
			StringBuffer hql = new StringBuffer ("select apt from ApontamentoApt apt inner join apt.pontoCriticoPtc ptc inner join ptc.itemEstruturaIett item where ptc.indAtivoPtc = 'S' ");
			hql.append(" and item.codIett in (");

			for (int i = 0; i < listaItensEstruturaExportacao.size(); i++) {
				hql.append(listaItensEstruturaExportacao.get(i).getCodIett());
				if (i < listaItensEstruturaExportacao.size()-1) {
					hql.append(",");
				}
			}
			hql.append(" ) ");
			
			listaApontamentos = dao.consultarPorHQL(hql.toString());
		}
		
		return listaApontamentos;
	}
	
	@Override
	protected void montarNomeArquivoFuncao(StringBuffer nomeArquivoTemp, EstruturaEtt estruturaEtt, String labelFuncaoAba) throws ECARException{
		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(null);
		String labelFuncaoPontosCriticos  = estruturaFuncaoDao.getLabelFuncaoPontosCriticos(estruturaEtt);
			
		if (labelFuncaoPontosCriticos != null) {
			nomeArquivoTemp.append(labelFuncaoPontosCriticos.replace("/", " "));
			nomeArquivoTemp.append(ConstantesECAR.UNDERLINE);
		} else {
			nomeArquivoTemp.append(ConstantesECAR.HIFEN);
			nomeArquivoTemp.append(ConstantesECAR.UNDERLINE);
		}

		nomeArquivoTemp.append(labelFuncaoAba.replace("/", " "));
		
	}

}