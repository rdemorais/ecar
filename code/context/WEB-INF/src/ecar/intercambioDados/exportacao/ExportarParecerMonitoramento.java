package ecar.intercambioDados.exportacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.Util;

import ecar.bean.intercambioDados.CaminhoArquivoExportacaoBean;
import ecar.dao.AbaDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.EstruturaFuncaoDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.intercambioDados.exportacao.comunicacao.ComunicacaoExportacao;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.Aba;
import ecar.pojo.AcompRefItemLimitesArli;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.FuncaoFun;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.StatusRelatorioSrl;
import ecar.pojo.TipoAcompTipofuncacompSisatributoTaTpfaSatb;
import ecar.util.Dominios;

public class ExportarParecerMonitoramento extends ExportaFuncao {
	
	@Override
	protected List<IRegistro> montarConteudo(List<ItemEstruturaIett> listaItensEstruturaExportacao, List<AcompReferenciaItemAri> listaArisEstruturaExportacao,
			ConfiguracaoCfg configuracao, List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref,
			SegurancaECAR segurancaECAR) throws ECARException {

		ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
		AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(null);
		AcompRefItemLimitesArli limites = null;
		boolean periodoSeparadoPorOrgao = false;
		if(acompReferenciaAref != null && acompReferenciaAref.getTipoAcompanhamentoTa().getIndSepararOrgaoTa() != null && acompReferenciaAref.getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals(Dominios.SIM)) {
			periodoSeparadoPorOrgao = true;
		}
		StringBuffer linha = null;
		String separadorCampos = configuracao.getSeparadorArqTXT();

		IRegistro registro = null;
		List<IRegistro> listaRegistros = new ArrayList<IRegistro>();
		
						
		List<AcompRelatorioArel> listaAcompRelatorioArel = obterParecerMonitoramento(listaArisEstruturaExportacao, acompReferenciaAref, segurancaECAR);
				
		StatusRelatorioSrl statusLiberado = (StatusRelatorioSrl) itemDao.buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
		
		for(AcompRelatorioArel acompArel : listaAcompRelatorioArel) {
			linha = new StringBuffer();
			
			String exigeLiberarAcompanhamento = acompArel.getAcompReferenciaItemAri().getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndLiberarAcompTa();
			
			//monta o conteudo
			
			String parecerLiberado = "N";
			if(acompArel.getIndLiberadoArel() != null && acompArel.getIndLiberadoArel().equals(Dominios.SIM)) {
				parecerLiberado = acompArel.getIndLiberadoArel();
			}
			
			// se item pai existe			
			if(acompArel.getAcompReferenciaItemAri().getItemEstruturaIett().getItemEstruturaIett() != null) {
				linha.append(acompArel.getAcompReferenciaItemAri().getItemEstruturaIett().getItemEstruturaIett().getCodIett());
				linha.append(separadorCampos);
				linha.append(acompArel.getAcompReferenciaItemAri().getItemEstruturaIett().getCodIett());
				linha.append(separadorCampos);
			// senao
			} else {
				linha.append(separadorCampos);
				linha.append(acompArel.getAcompReferenciaItemAri().getItemEstruturaIett().getCodIett());
				linha.append(separadorCampos);
			}
			
			if (periodoSeparadoPorOrgao){
				//Sigla e descri��o do �rg�o da Refer�ncia
				if (acompArel.getAcompReferenciaItemAri().getAcompReferenciaAref().getOrgaoOrg() != null){
					//Sigla
					linha.append(acompArel.getAcompReferenciaItemAri().getAcompReferenciaAref().getOrgaoOrg().getSiglaOrg());
					linha.append(separadorCampos);
					//Descri��o
					linha.append(acompArel.getAcompReferenciaItemAri().getAcompReferenciaAref().getOrgaoOrg().getDescricaoOrg());
					linha.append(separadorCampos);
				}
				//caso a refer�ncia n�o possua �rg�o
				else{
					//Separador para sigla
					linha.append(separadorCampos);
					//Separador para a descri��o
					linha.append(separadorCampos);
				}
			}
			
			// Campos do parecer 
			
			//ID_<label da aba> e Fun��o de Acompanhamento 
			linha.append(acompArel.getCodArel());
			linha.append(separadorCampos);
			linha.append(acompArel.getTipoFuncAcompTpfa().getLabelTpfa().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " "));
			linha.append(separadorCampos);
			
			// se exige que o acompanhamento seja liberado e este n�o foi, exibir "N�o Liberado"
			if("S".equals(exigeLiberarAcompanhamento) && !acompArel.getAcompReferenciaItemAri().getStatusRelatorioSrl().equals(statusLiberado)){
				// Situa��o
				linha.append("N/L");
				linha.append(separadorCampos);
				// Cor
				linha.append("N/L");
				linha.append(separadorCampos);
				// Parecer
				linha.append("N/L");
				linha.append(separadorCampos);
				// Observa��es
				if(configuracao.getIndOcultarObservacoesParecer() != null && configuracao.getIndOcultarObservacoesParecer().equals(Dominios.NAO)) {
					linha.append("N/L");
					linha.append(separadorCampos);
				}
			// caso contr�rio, exibe o que foi informado ou "N�o Informado"
			} else {
				// Situa��o
				if(acompArel.getSituacaoSit() != null) {
					linha.append(acompArel.getSituacaoSit().getDescricaoSit().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " "));
				} else { 
					linha.append("N/I");
				}
				linha.append(separadorCampos);
				
				// Cor
				if(acompArel.getCor() != null) { 
					linha.append(acompArel.getCor().getSignificadoCor().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " "));
				} else {
					linha.append("N/I");
				}
				linha.append(separadorCampos);
				
				// Parecer
				if(acompArel.getDescricaoArel() != null) {
					String descricao = acompArel.getDescricaoArel().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
					descricao = Util.stripHTMLModificado(Util.normalizaCaracterMarcador(descricao));
	    			descricao = Util.stripHTMLCommentsModificado(descricao);
	    			descricao = Util.stripHTML(descricao);
	    			descricao = descricao.replace("&lt;","<");
	    			descricao = descricao.replace("&gt;",">");
	    			linha.append(descricao);
				} else {
					linha.append("N/I");
				}
				linha.append(separadorCampos);
				
				// Observa��es
				if(configuracao.getIndOcultarObservacoesParecer() != null && configuracao.getIndOcultarObservacoesParecer().equals(Dominios.NAO)) {
					if(acompArel.getComplementoArel() != null && !(acompArel.getComplementoArel().trim()).equals("")) {
						String observacao = acompArel.getComplementoArel().replaceAll("\n"," ").replaceAll("\r"," ").replaceAll("\t", " ");
						observacao = Util.stripHTMLModificado(Util.normalizaCaracterMarcador(observacao));
						observacao = Util.stripHTMLCommentsModificado(observacao);
						observacao = Util.stripHTML(observacao);
						observacao = observacao.replace("&lt;","<");
						observacao = observacao.replace("&gt;",">");
						linha.append(observacao);
					} else {
						linha.append("N/I");
					}
					linha.append(separadorCampos);
				}
			}

			// Indica se o parecer foi liberado
			linha.append(parecerLiberado);
			linha.append(separadorCampos);
			
			// Data Limite
			limites = ariDao.getAcompRefItemLimitesByAcompReferenciaItemTipoFuncAcomp(acompArel.getTipoFuncAcompTpfa(), acompArel.getAcompReferenciaItemAri());
			if(limites != null && limites.getDataLimiteArli() != null) {
				linha.append(Data.parseDateHourMinuteSecond(limites.getDataLimiteArli())).append(separadorCampos);
			} else {
				// exibe "-" quando o parecer da fun��o de acompanhamento for opcional
				linha.append("-").append(separadorCampos);
			}
			
			// Data Inclus�o | Usu�rio Inclus�o 
			if(acompArel.getDataInclusaoArel() != null) {
				linha.append(Data.parseDateHourMinuteSecond(acompArel.getDataInclusaoArel())).append(separadorCampos);
			} else {
				linha.append("").append(separadorCampos);
			}
			if(acompArel.getUsuarioUsu() != null) {
				linha.append(acompArel.getUsuarioUsu().getNomeUsu()).append(separadorCampos);
			} else {
				linha.append("").append(separadorCampos);
			}
			
			// Data �ltima Altera��o | Usu�rio �ltima Altera��o
			if(acompArel.getDataUltManutArel() != null) {
				linha.append(Data.parseDateHourMinuteSecond(acompArel.getDataUltManutArel())).append(separadorCampos);
			} else {
				linha.append("").append(separadorCampos);
			}
			if(acompArel.getUsuarioUsuUltimaManutencao() != null) {
				linha.append(acompArel.getUsuarioUsuUltimaManutencao().getNomeUsu()).append(separadorCampos);
			} else {
				linha.append("").append(separadorCampos);
			}
			
			registro = new RegistroTXT(linha.toString());
			listaRegistros.add(registro);
		}
				
		return listaRegistros;
	}
	
	@Override
	protected IRegistro montarSegundoHeader(EstruturaEtt estruturaEtt,List<ItemEstruturaIett> listaItensEstruturaExportacao, String labelFuncaoAba, 
			ConfiguracaoCfg configuracao, List<ObjetoEstrutura> colunas, AcompReferenciaAref acompReferenciaAref) {
		StringBuffer headerStr = new StringBuffer();
		String separadorCampos = configuracao.getSeparadorArqTXT();
		boolean periodoSeparadoPorOrgao = false;
		if(acompReferenciaAref != null && acompReferenciaAref.getTipoAcompanhamentoTa().getIndSepararOrgaoTa() != null && acompReferenciaAref.getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals(Dominios.SIM)) {
			periodoSeparadoPorOrgao = true;
		} 
		
		//Inicio Primeiro campo: ID_PAI_<Nome da estrutura Pai>
		headerStr.append(ConstantesECAR.ID_PAI);
		
		if (estruturaEtt.getEstruturaEtt()!= null){
			headerStr.append(ConstantesECAR.UNDERLINE);
			headerStr.append(estruturaEtt.getEstruturaEtt().getNomeEtt());
		}
		
		headerStr.append(separadorCampos);

		//Inicio Segundo campo: ID_<Nome da estrutura corrente>
		headerStr.append(ConstantesECAR.ID);
		headerStr.append(ConstantesECAR.UNDERLINE);
		headerStr.append(estruturaEtt.getNomeEtt());
		headerStr.append(separadorCampos);
		//Fim Segundo campo

		if (periodoSeparadoPorOrgao){
			//Inicio Terceiro campo: Sigla do �rg�o da Refer�ncia do Item
			headerStr.append("Sigla " + configuracao.getLabelOrgao());		
			headerStr.append(separadorCampos);
			//Fim Terceiro campo
			//Inicio Quarto campo: Descri��o do �rg�o da Refer�ncia do Item
			headerStr.append(configuracao.getLabelOrgao());		
			headerStr.append(separadorCampos);
			//Fim Quarto campo

		}
	
		//Inicio Quinto campo: ID_<label da aba>
		headerStr.append(ConstantesECAR.ID);
		headerStr.append(ConstantesECAR.UNDERLINE);
		headerStr.append(labelFuncaoAba);
		headerStr.append(separadorCampos);
		//Fim Quinto campo
		
		// Inicio demais campos
		
		// Fun��o de Acompanhamento  
		headerStr.append("Fun��o de Acompanhamento").append(separadorCampos);

		//  <Label para Situa��o em Parecer> ou Situa��o
		if(configuracao.getLabelSituacaoParecer() != null && !configuracao.getLabelSituacaoParecer().equals("")) {
			headerStr.append(configuracao.getLabelSituacaoParecer()).append(separadorCampos);
		} else {
			headerStr.append("Situa��o").append(separadorCampos);
		}
		
		// <Label para Cor em Parecer> ou Cor
		if(configuracao.getLabelCorParecer() != null && !configuracao.getLabelCorParecer().equals("")) {
			headerStr.append(configuracao.getLabelCorParecer()).append(separadorCampos);
		} else {
			headerStr.append("Cor").append(separadorCampos);
		}
		
		// Parecer da fun��o
		headerStr.append("Parecer da Fun��o").append(separadorCampos);
		
		// Caso esteja configurado para exibir o campo Observa��es
		if(configuracao.getIndOcultarObservacoesParecer() != null && configuracao.getIndOcultarObservacoesParecer().equals(Dominios.NAO)) {
			headerStr.append("Observa��es").append(separadorCampos);
		}
		
		// Indica se o parecer foi liberado
		headerStr.append("Parecer Liberado").append(separadorCampos);
		
		// Data Limite | Data Inclus�o | Usu�rio Inclus�o | Data �ltima Altera��o | Usu�rio �ltima Altera��o
		headerStr.append("Data Limite").append(separadorCampos).append("Data Inclus�o").append(separadorCampos).append("Usu�rio Inclus�o").append(separadorCampos);
		headerStr.append("Data �ltima Altera��o").append(separadorCampos).append("Usu�rio �ltima Altera��o");
		// Fim demais campos
		
		IRegistro registro = new RegistroTXT(headerStr.toString());
		
		return registro;
	}
	
	/**
	 * 
	 * @param listaItensEstruturaExportacao
	 * @param acompReferenciaAref
	 * @return
	 */
	private List<AcompRelatorioArel> obterParecerMonitoramento(List<AcompReferenciaItemAri> listaArisEstruturaExportacao, 
			AcompReferenciaAref acompReferenciaAref, SegurancaECAR seguranca) throws ECARException {
		
//		AcompReferenciaItemDao acompRefItemDao = new AcompReferenciaItemDao(null);
		AcompReferenciaItemAri acompAri = null;
						
		List<AcompRelatorioArel> retorno = new ArrayList<AcompRelatorioArel>();
		
		Iterator itListaAris = listaArisEstruturaExportacao.iterator();
		
		while (itListaAris.hasNext()){
		
			acompAri = (AcompReferenciaItemAri) itListaAris.next();
			
			// Consulta permiss�o de visualiza��o de parecer
			ValidaPermissao validaPermissao = new ValidaPermissao();
			List<TipoAcompTipofuncacompSisatributoTaTpfaSatb> listaPermissaoTpfa = 
				validaPermissao.permissaoVisualizarPareceres(acompAri.getAcompReferenciaAref().getTipoAcompanhamentoTa(), seguranca.getGruposAcesso());
					
			Collection<AcompRelatorioArel> arels = acompAri.getAcompRelatorioArels();
			
			// Verificar, para cada arel, a permissao para visualizar o parecer
			Iterator<AcompRelatorioArel> arelsIt = arels.iterator();
			while(arelsIt.hasNext()){												
				
				AcompRelatorioArel relatorio = (AcompRelatorioArel) arelsIt.next();
				if(listaPermissaoTpfa.contains(relatorio.getTipoFuncAcompTpfa()) 
						&& validaPermissao.permissaoLeituraAcompanhamento(relatorio.getAcompReferenciaItemAri(), seguranca.getUsuario(), seguranca.getGruposAcesso())) {	
					retorno.add(relatorio);
				}	
			}
							
		}
		return retorno;		
	}
	
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
		//Se acompReferenciaAref != null ent�o � exporta��o de monitoramento
		//caso contr�rio � de cadastro
		if (acompReferenciaAref != null){
			//exporta��o de monitoramento
			if (aba != null){
				//Veririca se o usu�rio logado tem permiss�o para visualizar a aba.
				if (abaDao.getListaAbasComAcesso(acompReferenciaAref.getTipoAcompanhamentoTa(), segurancaECAR.getGruposAcesso()).contains(aba)){
					//Se a fun��o != null, ent�o � uma aba com sua respectiva fun��o (dados gerais, pontos cr�ticos, Diarios de Bordos) 
					if (funcao != null){
						//exporta��o de aba com respectiva fun��o
						//valida se a fun��o est� configurada na estrutura
						estruturaFuncaoEttf = estruturaFunDao.getEstruturaFuncao(estruturaEtt, funcao);
						if (estruturaFuncaoEttf != null){
							exportar = true;
						}
					} else {
						//Aba sem fun��o (aba de parecer)
						exportar = true;
					}
					//Quando a aba != null e o usu�rio tem acesso a essa aba
					//o sistema obtem o label da funcaoAba de acordo com a fun��o associada no cadastro de aba
					//caso n�o tenha fun��o associada, o sistema retorna o label da aba no cadastro de aba
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
				//exporta��o de fun��o sem respectiva aba (apontamentos de pontos cr�ticos)
				estruturaFuncaoEttf = estruturaFunDao.getEstruturaFuncao(estruturaEtt, funcao);
				if (estruturaFuncaoEttf != null) {
					labelFuncaoAba = estruturaFuncaoEttf.getLabelEttf();
					exportar = true;
				}
			}
		} else {
			//exporta��o do cadastro (fun��es definidas)
			estruturaFuncaoEttf = estruturaFunDao.getEstruturaFuncao(estruturaEtt, funcao);
			if (estruturaFuncaoEttf != null) {
				labelFuncaoAba = estruturaFuncaoEttf.getLabelEttf();
				exportar = true;
			} 
		}
		
		if (exportar){
			//Caso tenha permiss�o para exportar, chama o montarArquivo
			return montarArquivo(estruturaEtt, labelFuncaoAba, listaItensEstruturaExportacao, listaArisEstruturaExportacao, config,dataHoraExportacao,colunas, acompReferenciaAref, segurancaECAR);
		} else {
			//Se n�o tem permiss�o para exportar, retorna null
			return null;
		}
		
	}
		
}
