package ecar.intercambioDados.exportacao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import comum.util.ConstantesECAR;
import comum.util.Data;

import ecar.bean.intercambioDados.CaminhoArquivoExportacaoBean;
import ecar.dao.AbaDao;
import ecar.dao.AcompReferenciaDao;
import ecar.dao.EstruturaFuncaoDao;
import ecar.exception.ECARException;
import ecar.intercambioDados.exportacao.comunicacao.ComunicacaoExportacao;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.login.SegurancaECAR;
import ecar.pojo.Aba;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.FuncaoFun;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;

public abstract class ExportaFuncao {

	
	ComunicacaoExportacao comunicacao;
	
	public static final String TIPO_EXPORTACAO_CADASTRO = "Cadastro";
	
	public static final String TIPO_EXPORTACAO_ACOMPANHAMENTO = "Acompanhamento";
	
	public static final String TIPO_PREFIXO_EXPORTACAO_CADASTRO = "CAD";
	
	public static final String TIPO_PREFIXO_EXPORTACAO_ACOMPANHAMENTO = "ACP";
	
	
	/**
	 * 
	 * @param estruturaEtt
	 * @param listaItensEstruturaExportacao
	 * @throws ECARException 
	 */
	public CaminhoArquivoExportacaoBean exportar(EstruturaEtt estruturaEtt,List<ItemEstruturaIett> listaItensEstruturaExportacao, List<AcompReferenciaItemAri> listaArisEstruturaExportacao, FuncaoFun funcao, ConfiguracaoCfg config,Date dataHoraExportacao,ComunicacaoExportacao comunicacao,List<ObjetoEstrutura> colunas, SegurancaECAR segurancaECAR, AcompReferenciaAref acompReferenciaAref, Aba aba) throws ECARException {
		EstruturaFuncaoDao estruturaFunDao = new EstruturaFuncaoDao(null);
		AbaDao abaDao = new AbaDao(null);
		this.comunicacao = comunicacao;
		boolean exportar = false;
		String labelFuncaoAba = "";
		EstruturaFuncaoEttf estruturaFuncaoEttf = null;
		//Se acompReferenciaAref != null então é exportação de monitoramento
		//caso contrário é de cadastro
		if (acompReferenciaAref != null){
			//exportação de monitoramento
			if (aba != null){
				//Veririca se o usuário logado tem permissão para visualizar a aba.
				if (abaDao.getListaAbasComAcesso(acompReferenciaAref.getTipoAcompanhamentoTa(), segurancaECAR.getGruposAcesso()).contains(aba)){
					//Se a função != null, então é uma aba com sua respectiva função (dados gerais, pontos críticos, Diarios de Bordos) 
					if (funcao != null){
						//exportação de aba com respectiva função
						//valida se a função está configurada na estrutura
						estruturaFuncaoEttf = estruturaFunDao.getEstruturaFuncao(estruturaEtt, funcao);
						if (estruturaFuncaoEttf != null){
							exportar = true;
						}
					} else {
						//Aba sem função (aba de parecer)
						exportar = true;
					}
					//Quando a aba != null e o usuário tem acesso a essa aba
					//o sistema obtem o label da funcaoAba de acordo com a função associada no cadastro de aba
					//caso não tenha função associada, o sistema retorna o label da aba no cadastro de aba
					if (exportar){
						if (aba.getFuncaoFun() != null){
							estruturaFuncaoEttf = estruturaFunDao.getEstruturaFuncao(estruturaEtt, aba.getFuncaoFun());
							if (estruturaFuncaoEttf != null) {
								labelFuncaoAba = estruturaFuncaoEttf.getLabelEttf();
							} else {
								labelFuncaoAba = aba.getFuncaoFun().getLabelPadraoFun();
							}
						} else {
							labelFuncaoAba = aba.getLabelAba();
						}
					}
					
				}
			} else if (funcao != null){
				//exportação de função sem respectiva aba (apontamentos de pontos críticos)
				estruturaFuncaoEttf = estruturaFunDao.getEstruturaFuncao(estruturaEtt, funcao);
				if (estruturaFuncaoEttf != null) {
					labelFuncaoAba = estruturaFuncaoEttf.getLabelEttf();
					exportar = true;
				}
			}
		} else {
			//exportação do cadastro (funções definidas)
			estruturaFuncaoEttf = estruturaFunDao.getEstruturaFuncao(estruturaEtt, funcao);
			if (estruturaFuncaoEttf != null) {
				labelFuncaoAba = estruturaFuncaoEttf.getLabelEttf();
				exportar = true;
			} 
		}
		
		if (exportar){
			//Caso tenha permissão para exportar, chama o montarArquivo
			return montarArquivo(estruturaEtt, labelFuncaoAba, listaItensEstruturaExportacao, listaArisEstruturaExportacao, config,dataHoraExportacao,colunas, acompReferenciaAref, segurancaECAR);
		} else {
			//Se não tem permissão para exportar, retorna null
			return null;
		}
		
	}
	
	
	/**
	 * 
	 * @param estrutura
	 * @param listaItensEstruturaExportacao
	 * @return
	 * @throws ECARException 
	 */
	protected CaminhoArquivoExportacaoBean montarArquivo(EstruturaEtt estruturaEtt, String labelFuncaoAba, List<ItemEstruturaIett> listaItensEstruturaExportacao, List<AcompReferenciaItemAri> listaArisEstruturaExportacao, ConfiguracaoCfg configuracao, Date dataHoraExportacao,List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref, SegurancaECAR segurancaECAR) throws ECARException {
		
		CaminhoArquivoExportacaoBean caminho = null;
		EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(null);
		caminho = new CaminhoArquivoExportacaoBean();
		caminho.setCaminhoFisico(configuracao.getUploadExportacaoDemandas());
		List<IRegistro> listaRegistros = new ArrayList<IRegistro>();
		//Primeiro Header
		IRegistro registroPrimeiroHeader = this.montarPrimeiroHeader(configuracao, dataHoraExportacao, acompReferenciaAref);
		listaRegistros.add(registroPrimeiroHeader);

		//Segundo Header
		IRegistro registroSegundoHeader = montarSegundoHeader(estruturaEtt,listaItensEstruturaExportacao, labelFuncaoAba, configuracao,colunas, acompReferenciaAref);
		listaRegistros.add(registroSegundoHeader);
		
		//Registros
		listaRegistros.addAll(montarConteudo(listaItensEstruturaExportacao, listaArisEstruturaExportacao, configuracao,colunas, acompReferenciaAref, segurancaECAR));
		
		//Trailler
		IRegistro traillerRegistro  = montarTrailler(listaRegistros);
		listaRegistros.add(traillerRegistro);

		//Nome do arquivo
		String nomeArquivo = montarNomeArquivo(estruturaEtt, labelFuncaoAba, acompReferenciaAref);
		
		//Criar arquivo fisico
		String caminhoFisico = comunicacao.gerarArquivoDiscoFisico(configuracao, nomeArquivo, listaRegistros);
		
		
		caminho.setCaminhoFisico(caminhoFisico);
		caminho.setNomeEstrutura(estruturaEtt.getNomeExibicao());
		caminho.setNomeFuncao(labelFuncaoAba);
		caminho.setNomeArquivo(nomeArquivo);
		
		return caminho;
	}

	
	/**
	 * 
	 * @param estruturaEtt
	 * @param listaItensEstruturaExportacao
	 * @return
	 */
	protected IRegistro montarPrimeiroHeader(ConfiguracaoCfg configuracao, Date dataHoraExportacao, AcompReferenciaAref acompReferenciaAref) throws ECARException{
		StringBuffer header = new StringBuffer();
		if (acompReferenciaAref != null){
			
			boolean existeMaisDeUmaReferenciaDiaMesAno = 
				new AcompReferenciaDao(null).getListaMesmaReferenciaDiaMesAno(acompReferenciaAref).size() > 1 ? true : false;
			
			header.append(TIPO_EXPORTACAO_ACOMPANHAMENTO);
			header.append(configuracao.getSeparadorArqTXT());
			header.append(Data.parseDateHourMinuteSecond(dataHoraExportacao));
			header.append(configuracao.getSeparadorArqTXT());
			header.append(acompReferenciaAref.getTipoAcompanhamentoTa().getDescricaoTa());
			header.append(configuracao.getSeparadorArqTXT());
			header.append(acompReferenciaAref.getCodAref());
			header.append(configuracao.getSeparadorArqTXT());
			
			if (existeMaisDeUmaReferenciaDiaMesAno){
				header.append(ConstantesECAR.LABEL_ORGAO_CONSOLIDADO);
			}
			else{
				header.append(acompReferenciaAref.getNomeAref());
			}
			
			header.append(configuracao.getSeparadorArqTXT());
			header.append(acompReferenciaAref.getMesAref()).append("/").append(acompReferenciaAref.getAnoAref());
		} else {
			header.append(TIPO_EXPORTACAO_CADASTRO);
			header.append(configuracao.getSeparadorArqTXT());
			header.append(Data.parseDateHourMinuteSecond(dataHoraExportacao));
		}
		IRegistro registro = new RegistroTXT(header.toString());
		return registro;
	}

	
	/**
	 * Monta o Trailler do arquivo 
	 */
	protected IRegistro montarTrailler(List<IRegistro> listaRegistros){
		//Menos 2 pois a listaRegistros possui uma linha referente ao primeiro e segundo Header
		int tamanhoLista = listaRegistros.size()-2;
		String mensagemTrailler = "Nenhum registro exportado";
		
		if (tamanhoLista == 1){
			mensagemTrailler = tamanhoLista + " registro exportado";
		} else if (tamanhoLista > 1){
			mensagemTrailler = tamanhoLista + " registros exportados";
		}
		IRegistro registroTrailler = new RegistroTXT(mensagemTrailler);
		
		return registroTrailler;
	}
	
	
	/**
	 * Monta a parte genérica do nome do arquivo 
	 * @throws ECARException 
	 */
	protected String montarNomeArquivo(EstruturaEtt estruturaEtt, String labelFuncaoAba, AcompReferenciaAref acompReferenciaAref) throws ECARException{

		StringBuffer nomeArquivoTemp = new StringBuffer();
		
		if (acompReferenciaAref != null){
			nomeArquivoTemp.append(TIPO_PREFIXO_EXPORTACAO_ACOMPANHAMENTO);
			nomeArquivoTemp.append (ConstantesECAR.UNDERLINE);
		} else {
			nomeArquivoTemp.append(TIPO_PREFIXO_EXPORTACAO_CADASTRO);
			nomeArquivoTemp.append (ConstantesECAR.UNDERLINE);
		}
		
		//Id do Pai
		if (estruturaEtt.getEstruturaEtt() != null){
			nomeArquivoTemp.append(estruturaEtt.getEstruturaEtt().getCodEtt());
			nomeArquivoTemp.append (ConstantesECAR.UNDERLINE);
		} else {
			nomeArquivoTemp.append (ConstantesECAR.HIFEN);
			nomeArquivoTemp.append (ConstantesECAR.UNDERLINE);
		}
		
		//Id do Filho
		nomeArquivoTemp.append(estruturaEtt.getCodEtt());
		nomeArquivoTemp.append(ConstantesECAR.UNDERLINE);

		//Nome do pai
		if (estruturaEtt.getEstruturaEtt() != null){
			nomeArquivoTemp.append(estruturaEtt.getEstruturaEtt().getNomeEtt().replace("/", " "));
			nomeArquivoTemp.append (ConstantesECAR.UNDERLINE);
		} else {
			nomeArquivoTemp.append (ConstantesECAR.HIFEN);
			nomeArquivoTemp.append (ConstantesECAR.UNDERLINE);
		}

		//Nome do filho
		nomeArquivoTemp.append(estruturaEtt.getNomeEtt().replace("/", " "));
		nomeArquivoTemp.append(ConstantesECAR.UNDERLINE);
		
		montarNomeArquivoFuncao(nomeArquivoTemp, estruturaEtt, labelFuncaoAba);

		nomeArquivoTemp.append(ConstantesECAR.EXTENSAO_ARQUIVO_TXT);

		return nomeArquivoTemp.toString();
	}
		
	
	/**
	 * Retirar caracteres de controle.
	 * 
	 * @param valorStr
	 * @return
	 */
	protected String retirarCaracteresControle(String valorStr) {
		
		return valorStr.replaceAll(ConstantesECAR.QUEBRA_LINHA_MENSAGEM, " ").replaceAll(ConstantesECAR.CARACTER_POSICIONA_CURSOR_INICIO_LINHA," ").replaceAll(ConstantesECAR.CARACTER_TAB, " ");
	}	

	/**
	 * 
	 * @param estruturaEtt
	 * @param labelFuncaoAba
	 * @param configuracao
	 * @param colunas
	 * @return
	 * @throws ECARException
	 */
	protected abstract IRegistro montarSegundoHeader(EstruturaEtt estruturaEtt, List<ItemEstruturaIett>listaItensEstruturaExportacao ,String labelFuncaoAba, ConfiguracaoCfg configuracao, List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref) throws ECARException;	
	/**
	 * 
	 * @param estruturaEtt
	 * @param labelFuncaoAba
	 * @return
	 * @throws ECARException
	 */
	protected void montarNomeArquivoFuncao(StringBuffer nomeArquivoTemp, EstruturaEtt estruturaEtt, String labelFuncaoAba) throws ECARException{
		nomeArquivoTemp.append(labelFuncaoAba.replace("/", " "));
	}

	/**
	 * 
	 * @param estruturaEtt
	 * @param listaItensEstruturaExportacao
	 * @return
	 */
	protected abstract List<IRegistro> montarConteudo(List<ItemEstruturaIett> listaItensEstruturaExportacao, List<AcompReferenciaItemAri> listaArisEstruturaExportacao, ConfiguracaoCfg configuracao,List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref, SegurancaECAR segurancaECAR) throws ECARException;

}
