package ecar.intercambioDados.recurso.txt;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import comum.util.ConstantesECAR;

import ecar.exception.ECARException;
import ecar.exception.intercambioDados.SintaticValidationException;
import ecar.intercambioDados.importacao.comunicacao.Configuracao;
import ecar.intercambioDados.importacao.comunicacao.ConfiguracaoImportacaoTXT;
import ecar.intercambioDados.log.LogIntercambioDados;
import ecar.intercambioDados.montador.LinhaResultadoValidacao;
import ecar.intercambioDados.montador.LinhaResultadoValidacaoTXT;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.intercambioDados.DadosTecnlogiaLogIntercambioDadosLogdtid;
import ecar.pojo.intercambioDados.EntidadeLogIntercambioDadosEtlogid;
import ecar.pojo.intercambioDados.LogIntercambioDadosLid;
import ecar.pojo.intercambioDados.MotivoRejeicaoMtr;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosLogPflogid;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc;
import ecar.pojo.intercambioDados.funcionalidade.TipoFuncionalidadeEnum;

public class LogIntercambioDadosTXT extends LogIntercambioDados {

	Configuracao configuracao;
	
	public LogIntercambioDadosTXT(Configuracao configuracao) {

		this.configuracao = configuracao;
	}
	
	public LogIntercambioDadosLid montaLogSintatico(SintaticValidationException sintex, UsuarioUsu usuarioLogado) throws ECARException {
	
		String dadosFuncionalidadePerfil =  null;
		String dadosTecnologiaPerfil =  null;
		EntidadeLogIntercambioDadosEtlogid entidade = null;
		
		ConfiguracaoImportacaoTXT configTXT = ((ConfiguracaoImportacaoTXT)configuracao);
		LogIntercambioDadosLid logIntercambio = new LogIntercambioDadosLid();
		
		logIntercambio.setConteudoLid(configTXT.getSource());
		logIntercambio.setDataHoraProcessamentoLid(new Date());
		logIntercambio.setIndSituacaoProcessamentoLid(ConstantesECAR.REJEICAO);
		logIntercambio.setSituacaoLogLid(ConstantesECAR.REJEICAO);
		
		dadosFuncionalidadePerfil = ((PerfilIntercambioDadosCadastroPidc)configTXT.getPerfil()).montarDadosFuncionalidadeLog();
		dadosTecnologiaPerfil =  ((PerfilIntercambioDadosCadastroPidc)configTXT.getPerfil()).montarDadosTecnologiaLog();
			
		logIntercambio.setDadosTecnologia(montarDadosTecnlogialLog(configTXT.getDataGeracaoArquivo(), configTXT.getFileName(), null, null, logIntercambio));
		logIntercambio.setPerfilLog(montarPerfilLog(TipoFuncionalidadeEnum.CADASTRO.getDescricao(), dadosFuncionalidadePerfil, configTXT.getPerfil().getDadosTecnologiaPerfilDtp().getTipoTecnologia().getDescricao(),dadosTecnologiaPerfil));
		logIntercambio.setQuantidadeRegistrosProcessadosLid(new Long(ConstantesECAR.ZERO));
		logIntercambio.setUsuarioUsu(usuarioLogado);
				
		entidade = montarEntidadelLog(sintex.getGerador().getClass().getName(), 
				sintex.getMotivoRejeicaoMtr(), null, ((RegistroTXT)sintex.getGerador()).getLinha()
				, sintex.getNumeroLinha(),  
				ConstantesECAR.REJEICAO, null, logIntercambio);
		
		if (logIntercambio.getEntidadesLog()==null) {
			logIntercambio.setEntidadesLog(new HashSet<EntidadeLogIntercambioDadosEtlogid>());
		}
		logIntercambio.getEntidadesLog().add(entidade);	
		
		return logIntercambio;
	}


	/**
	 * Monta um objeto de LogIntercambioDadosLid com o resultado da validação semântica 
	 * @param registrosValidos
	 * @param registrosInvalidos
	 * @return
	 * @throws ECARException 
	 */
	public LogIntercambioDadosLid montaLogSemantico(UsuarioUsu usuarioUsu, List<LinhaResultadoValidacao> registrosValidos, List<LinhaResultadoValidacao> registrosInvalidos, PerfilIntercambioDadosPflid perfil) throws ECARException {
	
		ConfiguracaoImportacaoTXT configTXT = ((ConfiguracaoImportacaoTXT)configuracao);
		
		String dadosFuncionalidadePerfil =  null;
		String dadosTecnologiaPerfil =  null;
		
		
		//Cria o objeto de log
		LogIntercambioDadosLid logIntercambioDadosLid = new LogIntercambioDadosLid();
		logIntercambioDadosLid.setConteudoLid(configTXT.getSource());
		logIntercambioDadosLid.setDataHoraProcessamentoLid(new Date());
		
		// se não há registro válido
		if (registrosValidos==null || registrosValidos.size()==0) {
			logIntercambioDadosLid.setIndSituacaoProcessamentoLid(ConstantesECAR.REJEICAO);
			logIntercambioDadosLid.setSituacaoLogLid(ConstantesECAR.REJEICAO);
		} else {
			logIntercambioDadosLid.setIndSituacaoProcessamentoLid(ConstantesECAR.PROCESSADO);
			logIntercambioDadosLid.setSituacaoLogLid(ConstantesECAR.PROCESSADO);
		}
		dadosFuncionalidadePerfil = ((PerfilIntercambioDadosCadastroPidc)configTXT.getPerfil()).montarDadosFuncionalidadeLog();
		dadosTecnologiaPerfil =  ((PerfilIntercambioDadosCadastroPidc)configTXT.getPerfil()).montarDadosTecnologiaLog();
		
		logIntercambioDadosLid.setDadosTecnologia(montarDadosTecnlogialLog(configTXT.getDataGeracaoArquivo(), configTXT.getFileName(), null, null, logIntercambioDadosLid));
		logIntercambioDadosLid.setPerfilLog(montarPerfilLog(TipoFuncionalidadeEnum.CADASTRO.getDescricao(), dadosFuncionalidadePerfil, configTXT.getPerfil().getDadosTecnologiaPerfilDtp().getTipoTecnologia().getDescricao(),dadosTecnologiaPerfil));
		logIntercambioDadosLid.setUsuarioUsu(usuarioUsu);
				
		montarSucessoSemantica(logIntercambioDadosLid, registrosValidos);
		montarRejeicaoSemantica(logIntercambioDadosLid, registrosInvalidos);
		
		//A quantidade de registros será a soma da quantidade de itens importados e itens rejeitados 
		long qtdRegistros = registrosValidos.size() + registrosInvalidos.size();
		
		logIntercambioDadosLid.setQuantidadeRegistrosProcessadosLid(Long.valueOf(qtdRegistros));
		
		return logIntercambioDadosLid;
	}
	
	/**
	 * Monta o log de importados de acordo com os registros válidos (Semântico)
	 * @param logIntercambioDadosLid
	 * @param registrosValidos
	 */
	private void montarSucessoSemantica(LogIntercambioDadosLid logIntercambioDadosLid, List<LinhaResultadoValidacao> registrosValidos){
		
		EntidadeLogIntercambioDadosEtlogid entidade = null;
		
		
		if (registrosValidos != null && registrosValidos.size() > 0){	

			for (LinhaResultadoValidacao resultado : registrosValidos) {
				
				if (resultado instanceof LinhaResultadoValidacaoTXT 
					//&& (((LinhaResultadoValidacaoTXT) resultado).isEhValido()) 
				   && (((LinhaResultadoValidacaoTXT) resultado).getObjetoNegocio() instanceof ItemEstruturaIett)) {
					
					entidade = montarEntidadelLog(resultado.getObjetoNegocio().getClass().getName(), 
							null, montarValorLigacao(resultado)
							, ((LinhaResultadoValidacaoTXT) resultado).getLinha(), 
							new Long(((LinhaResultadoValidacaoTXT) resultado).getNumeroLinha()), 
							ConstantesECAR.PROCESSADO, ((LinhaResultadoValidacaoTXT) resultado).getTipoOperacao(), logIntercambioDadosLid);
					
					if (logIntercambioDadosLid.getEntidadesLog()==null)
						logIntercambioDadosLid.setEntidadesLog(new HashSet<EntidadeLogIntercambioDadosEtlogid>());
					logIntercambioDadosLid.getEntidadesLog().add(entidade);	
				}
			}
		}
	}
	
	/**
	 * Monta o log de rejeitados de acordo com os registros inválidos (Semântico)
	 * @param logIntercambioDadosLid
	 * @param registrosinValidos
	 */
	private void montarRejeicaoSemantica(LogIntercambioDadosLid logIntercambioDadosLid, List<LinhaResultadoValidacao> registrosInvalidos){
		
		EntidadeLogIntercambioDadosEtlogid entidade = null;
		
		if (registrosInvalidos != null && registrosInvalidos.size() > 0){
			for (LinhaResultadoValidacao resultado : registrosInvalidos) {
				
				if (resultado instanceof LinhaResultadoValidacaoTXT && !(((LinhaResultadoValidacaoTXT) resultado).isEhValido()) 
						   && (((LinhaResultadoValidacaoTXT) resultado).getObjetoNegocio() instanceof ItemEstruturaIett)) {
					
					entidade = montarEntidadelLog(resultado.getObjetoNegocio().getClass().getName(), 
							((LinhaResultadoValidacaoTXT) resultado).getMotivo(), montarValorLigacao(resultado)
							, ((LinhaResultadoValidacaoTXT) resultado).getLinha(), 
							new Long(((LinhaResultadoValidacaoTXT) resultado).getNumeroLinha()), 
							ConstantesECAR.REJEICAO, ((LinhaResultadoValidacaoTXT) resultado).getTipoOperacao(), 
							logIntercambioDadosLid);
					if (logIntercambioDadosLid.getEntidadesLog()==null)
						logIntercambioDadosLid.setEntidadesLog(new HashSet<EntidadeLogIntercambioDadosEtlogid>());
					logIntercambioDadosLid.getEntidadesLog().add(entidade);
				}
			}
		}
	}
	
	private PerfilIntercambioDadosLogPflogid montarPerfilLog(String tipoFuncionalidade, String dadosFuncionalidade,
			String tipoTecnologia, String dadosTecnologia) {
		
		PerfilIntercambioDadosLogPflogid perfil = new PerfilIntercambioDadosLogPflogid(null, tipoFuncionalidade, dadosFuncionalidade, tipoTecnologia, dadosTecnologia);
		return perfil;
	}
	
	private EntidadeLogIntercambioDadosEtlogid montarEntidadelLog(String nomeEtlogid, MotivoRejeicaoMtr motivoRejeicaoEtlogid,
																  String valorLigacaoEtlogid, String registroEtlogid,
																  Long numeroRegistroEtlogid, String situcaoEtlogid,
																  String indOperacaoRealizadaEtlogid,
																  LogIntercambioDadosLid logIntercambioDados) {
		
		EntidadeLogIntercambioDadosEtlogid entidade = new EntidadeLogIntercambioDadosEtlogid(null, nomeEtlogid, motivoRejeicaoEtlogid, valorLigacaoEtlogid, registroEtlogid, numeroRegistroEtlogid, situcaoEtlogid, indOperacaoRealizadaEtlogid, logIntercambioDados);
		
		
		return entidade;
	}
	
	private DadosTecnlogiaLogIntercambioDadosLogdtid montarDadosTecnlogialLog(Date dataGeracaoArquivoDtlid, String nomeArquivoDtlid,
			Date dataInicioIntervaloImportacaoDtlid,
			Date dataFimIntervaloImportacaoDtlid,
			LogIntercambioDadosLid logIntercambioDados) {

		DadosTecnlogiaLogIntercambioDadosLogdtid dadosTecnlogiaLog = new DadosTecnlogiaLogIntercambioDadosLogdtid(null, dataGeracaoArquivoDtlid, nomeArquivoDtlid, dataInicioIntervaloImportacaoDtlid, dataFimIntervaloImportacaoDtlid, logIntercambioDados);
		
		
		return dadosTecnlogiaLog;
	}
	
	private String montarValorLigacao(LinhaResultadoValidacao resultado) {
	
		String valorLigacao = null;
		
		if (resultado instanceof LinhaResultadoValidacaoTXT) {
			if (((ItemEstruturaIett)((LinhaResultadoValidacaoTXT) resultado).getObjetoNegocio()).getSiglaIett()!=null) {
				valorLigacao = EntidadeLogIntercambioDadosEtlogid.VALOR_LIG_COD_ITEM_EST + 
				"=" + ((ItemEstruturaIett)((LinhaResultadoValidacaoTXT) resultado).getObjetoNegocio()).getSiglaIett();
			} else {
				valorLigacao = EntidadeLogIntercambioDadosEtlogid.VALOR_LIG_COD_ITEM_EST + "=" + "null";
			}
			
			if (((ItemEstruturaIett)((LinhaResultadoValidacaoTXT) resultado).getObjetoNegocio()).getDescricaoR3()==null) {
				valorLigacao += LogIntercambioDadosLid.LOG_SEPARADOR + EntidadeLogIntercambioDadosEtlogid.VALOR_LIG_TIPO_EMP + "="
				+ "null";
			} else {
				valorLigacao += LogIntercambioDadosLid.LOG_SEPARADOR + EntidadeLogIntercambioDadosEtlogid.VALOR_LIG_TIPO_EMP + "="
				+ ((ItemEstruturaIett)((LinhaResultadoValidacaoTXT) resultado).getObjetoNegocio()).getDescricaoR3();
			}
			
			if (((ItemEstruturaIett)((LinhaResultadoValidacaoTXT) resultado).getObjetoNegocio()).getDescricaoR1()==null) {
				valorLigacao += LogIntercambioDadosLid.LOG_SEPARADOR + EntidadeLogIntercambioDadosEtlogid.VALOR_LIGACAO + "="
				+ "null";
			} else {
				valorLigacao += LogIntercambioDadosLid.LOG_SEPARADOR + EntidadeLogIntercambioDadosEtlogid.VALOR_LIGACAO + "="
				+ ((ItemEstruturaIett)((LinhaResultadoValidacaoTXT) resultado).getObjetoNegocio()).getDescricaoR1();
			}
		}
		
		return valorLigacao;
		
	}	
	
}
