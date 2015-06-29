package ecar.intercambioDados.exportacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import comum.database.DaoUtil;
import comum.util.ConstantesECAR;
import comum.util.Pagina;

import ecar.api.facade.EcarData;
import ecar.api.facade.IndicadorResultado;
import ecar.api.facade.ItemEstrutura;
import ecar.api.facade.Local;
import ecar.api.facade.PrevistoLocal;
import ecar.dao.EstruturaFuncaoDao;
import ecar.dao.ExercicioDao;
import ecar.exception.ECARException;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;
import ecar.util.Dominios;

public class ExportaSubFuncaoLocais extends ExportaFuncao {

	
	private static final String LOCAL = "Local";
	
	@Override
	protected List<IRegistro> montarConteudo(List<ItemEstruturaIett> listaItensEstruturaExportacao, List<AcompReferenciaItemAri> listaArisEstruturaExportacao, ConfiguracaoCfg configuracao,List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref, SegurancaECAR segurancaECAR) throws ECARException {
		List<IRegistro> listaRegistros = new ArrayList<IRegistro>();
		List<ItemEstrtIndResulIettr> listaIndResul = obterItemEstrtIndResulIettr(listaItensEstruturaExportacao);

		//datas dos items de estrutura
		Set<EcarData> datas = new TreeSet<EcarData>();
		for(ItemEstruturaIett _item_: listaItensEstruturaExportacao){
			ItemEstrutura item = new ItemEstrutura(_item_);
			if(item.getMeses() != null){			
				datas.addAll(item.getMeses());
			}
		}
		
		for (ItemEstrtIndResulIettr indResul : listaIndResul) {
			IndicadorResultado indicador = new IndicadorResultado(indResul);
			
			if(indicador.isIndicadorPorLocal() == false){
				continue;
			}
			
			//pegas os locais
			Set<Local> locais = indicador.getLocaisPrevistos();
			ItemEstrutura item = indicador.getItemEstrutura();
			
			for (Local local : locais) {
				
				StringBuffer headerStr = new StringBuffer(); 
				IRegistro registro = null;
			
				//Inicio primeiro campo se houver
				if(item.getRealObject().getItemEstruturaIett() != null){
					headerStr.append(item.getRealObject().getItemEstruturaIett().getCodIett());
					headerStr.append(configuracao.getSeparadorArqTXT());
				} else {
					headerStr.append(configuracao.getSeparadorArqTXT());
				}
				//fim primeiro campo se houver

				//Inicio segundo campo
				headerStr.append(item.getId());
				headerStr.append(configuracao.getSeparadorArqTXT());
				//fim segundo campo			

				//Inicio terceiro campo
				headerStr.append(indicador.getId());
				headerStr.append(configuracao.getSeparadorArqTXT());
				//fim terceiro campo			

				//Inicio quarto campo
				headerStr.append(local.getNome());
				//fim quarto campo
				
				//para cada mes do item pega valor previsto
				for(EcarData data: datas){
					PrevistoLocal previsto = indicador.getPrevistoPorLocal(data, local);
					headerStr.append(configuracao.getSeparadorArqTXT());
					if(previsto != null && Dominios.SIM.equals(previsto.getRealObject().getIndAtivoIettirl())){  
						headerStr.append(Pagina.trocaNullQtdValor(previsto.getValorPrevisto(), indicador.getRealObject().getIndTipoQtde()));
					}
				}
				
				registro = new RegistroTXT(headerStr.toString());
				listaRegistros.add(registro);
		  }
		}
		
		return listaRegistros;
	}

	
	
	@Override
	protected IRegistro montarSegundoHeader(EstruturaEtt estruturaEtt, List<ItemEstruturaIett> listaItensEstruturaExportacao ,String labelFuncaoAba, ConfiguracaoCfg configuracao, List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref)
			throws ECARException {
		
		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(null);
		ExercicioDao exeDao = new ExercicioDao(null);
		StringBuffer headerStr = new StringBuffer(); 
		IRegistro header; 
		
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
		String labelFuncaoMetasIndicadores  = estruturaFuncaoDao.getLabelFuncaoMetasIndicadores(estruturaEtt);
		
		headerStr.append(ConstantesECAR.ID);
		headerStr.append(ConstantesECAR.UNDERLINE);
		headerStr.append(labelFuncaoMetasIndicadores);
		headerStr.append(configuracao.getSeparadorArqTXT());
		//Fim do terceiro campo
		
		//Inicio quarto campo
		headerStr.append(ExportaSubFuncaoLocais.LOCAL);
		//fim quarto campo
		
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
		
		header = new RegistroTXT(headerStr.toString());
		
		return header;
	}
	
	
	private List<ItemEstrtIndResulIettr> obterItemEstrtIndResulIettr(List<ItemEstruturaIett> listaItensEstruturaExportacao) {
        DaoUtil<ItemEstrtIndResulIettr> daoUtil = new DaoUtil<ItemEstrtIndResulIettr>(){};
        int tamanhoListaItens = listaItensEstruturaExportacao.size();
        Long[] codIetts = new Long[tamanhoListaItens];
        for (int i = 0; i < tamanhoListaItens; i++) {
            codIetts[i] = listaItensEstruturaExportacao.get(i).getCodIett();
        }
        return daoUtil.listaIN("itemEstruturaIett.codIett", codIetts, new String[]{"indAtivoIettr"}, new String[]{"S"});
    }

	@Override
	protected void montarNomeArquivoFuncao(StringBuffer nomeArquivoTemp, EstruturaEtt estruturaEtt, String labelFuncaoAba) throws ECARException{
		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(null);
		String labelFuncaoMetasIndicadores  = estruturaFuncaoDao.getLabelFuncaoMetasIndicadores(estruturaEtt);
			
		if (labelFuncaoMetasIndicadores != null) {
			nomeArquivoTemp.append(labelFuncaoMetasIndicadores.replace("/", " "));
			nomeArquivoTemp.append(ConstantesECAR.UNDERLINE);
		} else {
			nomeArquivoTemp.append(ConstantesECAR.HIFEN);
			nomeArquivoTemp.append(ConstantesECAR.UNDERLINE);
		}

		nomeArquivoTemp.append(labelFuncaoAba.replace("/", " "));
		
	}
}