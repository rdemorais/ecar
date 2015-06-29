package ecar.intercambioDados;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.ConstantesECAR;

import ecar.bean.AtributoEstruturaListagemItens;
import ecar.bean.intercambioDados.CaminhoArquivoExportacaoBean;
import ecar.bean.intercambioDados.TelaExportacaoBean;
import ecar.dao.AbaDao;
import ecar.dao.AcompReferenciaDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.EstruturaDao;
import ecar.dao.FuncaoDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.TipoAcompanhamentoDao;
import ecar.dao.UsuarioDao;
import ecar.dao.intercambioDados.LogIntercambioDadosDao;
import ecar.dao.intercambioDados.PerfilIntercambioDadosDao;
import ecar.email.AgendadorEmail;
import ecar.exception.ECARException;
import ecar.exception.intercambioDados.SemanticValidationException;
import ecar.exception.intercambioDados.SintaticValidationException;
import ecar.intercambioDados.dto.MontadorDTO;
import ecar.intercambioDados.exportacao.ExportaFuncao;
import ecar.intercambioDados.exportacao.ExportaFuncaoAnexo;
import ecar.intercambioDados.exportacao.ExportaFuncaoConfiguravelAtributoLivreDadosGerais;
import ecar.intercambioDados.exportacao.ExportaFuncaoConfiguravelAtributoLivrePontosCriticos;
import ecar.intercambioDados.exportacao.ExportaFuncaoEntidades;
import ecar.intercambioDados.exportacao.ExportaFuncaoItemEstrtIndResulIettr;
import ecar.intercambioDados.exportacao.ExportaFuncaoItemEstrutAcaoIetta;
import ecar.intercambioDados.exportacao.ExportaFuncaoLocalidades;
import ecar.intercambioDados.exportacao.ExportaIndicadoresRealizado;
import ecar.intercambioDados.exportacao.ExportaIndicadoresRealizadoPorLocal;
import ecar.intercambioDados.exportacao.ExportaSubFuncaoApontamentos;
import ecar.intercambioDados.exportacao.ExportaSubFuncaoLocais;
import ecar.intercambioDados.exportacao.ExportarParecerMonitoramento;
import ecar.intercambioDados.exportacao.comunicacao.ComunicacaoExportacao;
import ecar.intercambioDados.importacao.command.ImportacaoDadosGeraisImportacaoPacInter;
import ecar.intercambioDados.importacao.comunicacao.Configuracao;
import ecar.intercambioDados.importacao.comunicacao.IRegistro;
import ecar.intercambioDados.log.LogIntercambioDados;
import ecar.intercambioDados.montador.IMontadorLinhaResultadoValidacaoTXT;
import ecar.intercambioDados.montador.LinhaResultadoValidacao;
import ecar.intercambioDados.montador.MontadorLinhaResultadoValidacaoItemEstruturaTXT;
import ecar.intercambioDados.montador.MontadorLinhaResultadoValidacaoTXTFactory;
import ecar.intercambioDados.montador.ResultadoValidacaoBean;
import ecar.intercambioDados.recurso.txt.LogIntercambioDadosTXT;
import ecar.intercambioDados.recurso.txt.RegistroTXT;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.Aba;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.FuncaoFun;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.TextosSiteTxt;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.intercambioDados.LogIntercambioDadosLid;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc;
import ecar.util.Dominios;

public class ControladorIntercambioDados {

	

	private ComunicacaoExportacao comunicacaoExportacao;
	private MontadorDTO montadorDTO;
	private Configuracao configuracao;
	private LogIntercambioDados log;
	protected Logger logger = null;
	private FuncaoDao funDao; 	
	
	public ControladorIntercambioDados(Configuracao configuracao) {
		super();
		this.configuracao = configuracao;
		
		logger = Logger.getLogger(this.getClass());
	}
	
	
	public ControladorIntercambioDados(ComunicacaoExportacao comunicaoExportacao,
									   MontadorDTO montador) {
		super();
		this.comunicacaoExportacao = comunicaoExportacao;
		this.montadorDTO = montador;
		
		funDao = new FuncaoDao(null);
		
		logger = Logger.getLogger(this.getClass());
	}
	
	
	public ControladorIntercambioDados() {
		super();
	}
	
	
	/**
	 * Faz valida��o sint�tica e sem�ntica dos registros a serem importados
	 * @throws ECARException
	 * @throws SintaticValidationException
	 */
	public ResultadoValidacaoBean validarRegistros (UsuarioUsu usuarioLogado, PerfilIntercambioDadosPflid perfil) throws ECARException, SintaticValidationException{
		

		ResultadoValidacaoBean resultadoValidacaoBean = null;
		ImportacaoDadosGeraisImportacaoPacInter importacao = ImportacaoDadosGeraisImportacaoPacInter.getInstancia();
		Transaction tx = null;
		IBusinessObject objetoNegocio = null;
		int numeroLinha = 1;

		
		if (perfil instanceof PerfilIntercambioDadosCadastroPidc) {
			this.log = new LogIntercambioDadosTXT(this.configuracao);
		}
		
		// comunica��o
		List<IRegistro> registros = importacao.comunicar(perfil, this.configuracao);
		
		// analise sintatica
		try {
			importacao.analisarSintaxe(TipoDadosImportacao.ITEM, registros, perfil, configuracao, logger);	 
		
		} catch (SintaticValidationException sintex) {
			
			try {
				
				LogIntercambioDadosLid logSintatico =  log.montaLogSintatico(sintex,usuarioLogado);
				
				LogIntercambioDadosDao logDao = new LogIntercambioDadosDao();
				
				tx = logDao.getSession().beginTransaction();
				
				logDao.salvar(logSintatico);
				
				tx.commit();
			
			} catch (HibernateException hbmex){
				if (tx != null) {
					try {
						tx.rollback();
					} catch (HibernateException r) {
			            this.logger.error(r);
						throw new ECARException("erro.hibernateException"); 
					}
				}
	            this.logger.error(hbmex);
				throw new ECARException("erro.hibernateException"); 

			}
			
			throw sintex;
			
		}
		
		resultadoValidacaoBean = new ResultadoValidacaoBean();
		
		MontadorLinhaResultadoValidacaoTXTFactory montaFact = new MontadorLinhaResultadoValidacaoTXTFactory();
		IMontadorLinhaResultadoValidacaoTXT montadorResulVal = null;
//		IMontadorLinhaResultadoValidacaoTXT montadorResulInval = null;
		LinhaResultadoValidacao linha = null;
		
		// analise semantica
		for (Iterator iterator = registros.iterator(); iterator.hasNext();) {
			IRegistro iRegistro = (IRegistro) iterator.next();
		
			montadorResulVal = (MontadorLinhaResultadoValidacaoItemEstruturaTXT)montaFact.criar(iRegistro, perfil);
			if (montadorResulVal != null) {
				objetoNegocio = importacao.montar(iRegistro, perfil, usuarioLogado);
				
				try {
					if (importacao.analisarSemantica(objetoNegocio, perfil)){
						linha = montadorResulVal.montarValida(objetoNegocio, ((RegistroTXT)iRegistro).getOperacao(perfil), ((RegistroTXT)iRegistro).getLinha(), numeroLinha);
						resultadoValidacaoBean.getLinhasRegistrosValidos().add(linha);
						resultadoValidacaoBean.getLinhasRegistrosOriginais().add(linha);
						resultadoValidacaoBean.getRegistrosValidos().add(objetoNegocio);
						resultadoValidacaoBean.getRegistrosOriginais().add(objetoNegocio);
					}
				} catch (SemanticValidationException e) {
					linha = montadorResulVal.montarInvalida(e, ((RegistroTXT)iRegistro).getOperacao(perfil), ((RegistroTXT)iRegistro).getLinha(), numeroLinha);
					resultadoValidacaoBean.getLinhasRegistrosInvalidos().add(linha);
					resultadoValidacaoBean.getLinhasRegistrosOriginais().add(linha);
					resultadoValidacaoBean.getRegistrosInvalidos().add(objetoNegocio);
					resultadoValidacaoBean.getRegistrosOriginais().add(objetoNegocio);
				}	
			}
			numeroLinha++;
		}
				
		return resultadoValidacaoBean;
		
	}
	
	/**
	 * Executa a importa��o dos dados.
	 * @param dto
	 * @param usuarioLogado
	 */
	public LogIntercambioDadosLid importarDados(List<LinhaResultadoValidacao> linhasResultadoValidacao, PerfilIntercambioDadosPflid perfil, UsuarioUsu usuarioLogado, HttpServletRequest request) throws ECARException {
		
		ImportacaoDadosGeraisImportacaoPacInter importacao = ImportacaoDadosGeraisImportacaoPacInter.getInstancia();
		
		IBusinessObject objetoNegocio = null;
		LinhaResultadoValidacao linha = null;
		ResultadoValidacaoBean resultadoValidacaoBean = null;
		LogIntercambioDadosDao logDao = new LogIntercambioDadosDao();
		Transaction tx = null;
		int numeroLinha = 1;
		String tipoOperacao = null;
		
		if (perfil instanceof PerfilIntercambioDadosCadastroPidc) {
			this.log = new LogIntercambioDadosTXT(this.configuracao);
		}
		
		tx = new Dao().getSession().beginTransaction();
		
		resultadoValidacaoBean = new ResultadoValidacaoBean();
		
		
//		IMontadorLinhaResultadoValidacaoTXT montadorResulInval = null;
		for (Iterator<LinhaResultadoValidacao> iterator = linhasResultadoValidacao.iterator(); iterator.hasNext();) {
			
			linha = (LinhaResultadoValidacao) iterator.next();
			
			objetoNegocio = importacao.montar(linha.getObjetoNegocio(), perfil, usuarioLogado);
						
			try {
				if (importacao.analisarSemantica(objetoNegocio, perfil)){
					resultadoValidacaoBean.getLinhasRegistrosValidos().add(linha);
					tipoOperacao = importacao.importar(objetoNegocio, tx, request, usuarioLogado, logger);
				}
			} catch (SemanticValidationException e) {
				resultadoValidacaoBean.getLinhasRegistrosInvalidos().add(linha);
			}			
		
			
			linha.setTipoOperacao(tipoOperacao);
			linha.setObjetoNegocio(objetoNegocio);
			numeroLinha++;
			
		}

		// montar log
		LogIntercambioDadosLid logIntercambioDadosLid = log.montaLogSemantico(usuarioLogado, resultadoValidacaoBean.getLinhasRegistrosValidos(), resultadoValidacaoBean.getLinhasRegistrosInvalidos(), perfil);
		
		//salvar log
		logDao.salvar(logIntercambioDadosLid);
		
		tx.commit();
		
		enviarEmailImportacao(usuarioLogado);
		
		return logIntercambioDadosLid;
		

		
//		ResultadoValidacaoBean resultadoValidacaoBean = montarResultadoValidacaoSemantica(dto);
//		ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
//		LogIntercambioDadosDao logDao = new LogIntercambioDadosDao();
//		
//		//montar lista de itens
//		List<List<ItemEstruturaIett>> listaItensImportacao = geraItensEstruturaIetts(resultadoValidacaoBean.getRegistrosValidos(), usuarioLogado); 	
//
//		//montar log
//		LogIntercambioDadosLid logIntercambioDadosLid = log.montaLogSemantico(usuarioLogado, resultadoValidacaoBean.getRegistrosValidos(), resultadoValidacaoBean.getRegistrosInvalidos());
//		//salvar lista de itens e log
//		Transaction tx = null;
//		try {
//			tx = itemDao.getSession().beginTransaction();
//	
//			//salvar lista de itens
//			List<ItemEstruturaIett> listaItensInclusao = listaItensImportacao.get(Integer.parseInt(ConstantesECAR.ZERO));
//
//			//Salvar itens enviados no arquivo
//			if (!listaItensInclusao.isEmpty()) {
//				//Obtem a Fun��o de Dados Gerais 
//				FuncaoDao funcaoDao = new FuncaoDao(request);
//				FuncaoFun funcao = funcaoDao.getFuncaoDadosGerais();
//		
//				for (ItemEstruturaIett itemEstrutura : listaItensInclusao) {
//					itemDao.salvar(request,tx,itemEstrutura,funcao,false);
//				}
//			}
//			
//
//			List<ItemEstruturaIett> listaItensAlteracao = listaItensImportacao.get(Integer.parseInt(ConstantesECAR.UM));
//
//			//Alterar itens enviados no arquivo
//			if (!listaItensAlteracao.isEmpty()) {
//				for (ItemEstruturaIett itemEstruturaIett : listaItensAlteracao) {
//					try {
//						itemDao.alterar(tx, request, usuarioLogado, itemEstruturaIett);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//			
//			
////			Excluir itens enviados no arquivo			
//			List<ItemEstruturaIett> listaItensExclusao = listaItensImportacao.get(Integer.parseInt(ConstantesECAR.DOIS));
//			if (!listaItensExclusao.isEmpty()) {
//				String[] codIett = new String [listaItensExclusao.size()];
//				for (int i =0;i<listaItensExclusao.size();i++) {
//					ItemEstruturaIett itemEstruturaIett = listaItensExclusao.get(i);
//					codIett[i] = itemEstruturaIett.getCodIett().toString();
//				}
//				itemDao.excluir(tx,codIett,usuarioLogado);
//			}
//
//			//salvar log
//			logDao.salvar(logIntercambioDadosLid);
//			
//			tx.commit();
//			
//			enviarEmailImportacao(usuarioLogado);
//
//			return logIntercambioDadosLid;
//		} catch (IOException ioex){
//			if (tx != null) {
//				try {
//					tx.rollback();
//				} catch (HibernateException r) {
//		            this.logger.error(r);
//					throw new ECARException("erro.arquivo"); 
//				}
//			}
//            this.logger.error(ioex);
//			throw new ECARException("erro.arquivo");
//		} catch (HibernateException hbmex){
//			if (tx != null) {
//				try {
//					tx.rollback();
//				} catch (HibernateException r) {
//		            this.logger.error(r);
//					throw new ECARException("erro.hibernateException"); 
//				}
//			}
//            this.logger.error(hbmex);
//			throw new ECARException("erro.hibernateException"); 
//
//		}
	}
	

	/**
	 * Exporta��o dos dados da estrutura
	 * 
	 * @param listaEstruturas
	 * @param itemPrincipal
	 * @return
	 * @throws ECARException 
	 */
	public TelaExportacaoBean gerarArquivosCadastro(List<EstruturaEtt> listaEstruturas,ItemEstruturaIett itemPrincipal,SegurancaECAR seguranca) throws ECARException{
		List<EstruturaEtt> listEstruturasGeral = new ArrayList<EstruturaEtt>();
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		List<ItemEstruturaIett> listaItensPorEstrutura = null;
		List<ItemEstruturaIett> listaItensVisualizacaoPermitida = null;

		
		SortedMap<EstruturaEtt, List<ItemEstruturaIett>> mapaEstruturaItens = new  TreeMap<EstruturaEtt, List<ItemEstruturaIett>>();
		ConfiguracaoCfg configuracao = new ecar.dao.ConfiguracaoDao(null).getConfiguracao();
		
		Date dataHoraGeracao = new Date();
		
		
		//gera uma lista com as estruturas filhas da estrutura selecionada
		for (EstruturaEtt estruturaCorrente : listaEstruturas) {
											
			
			//adiciona a estrutura � lista de exporta��o caso a mesma esteja ativa
			if (estruturaCorrente.getIndAtivoEtt().equals("S")){
				listEstruturasGeral.add(estruturaCorrente);
			}
			
			if (estruturaCorrente.getEstruturaEtts() != null && !estruturaCorrente.getEstruturaEtts().isEmpty()){
										
				listEstruturasGeral.addAll(getEstruturasFilhas(estruturaCorrente));		
				
			}
		}

		//Consulta os itens dispon�veis de cada estrutura considerando itens descendentes e a permiss�o de visualiza��o do usu�rio ao item.
		for (EstruturaEtt estruturaCorrente : listEstruturasGeral) {
			
			if (itemPrincipal != null){

				List codPossiveisItensPais = montarListaCodigosPais(itemPrincipal);
				listaItensPorEstrutura = itemEstruturaDao.getItensByEstruturaOrdenadosPorItemPai(estruturaCorrente.getCodEtt(), codPossiveisItensPais);

			} else {
				listaItensPorEstrutura = itemEstruturaDao.getItensByEstrutura(estruturaCorrente.getCodEtt());
			}
			
			listaItensVisualizacaoPermitida =  validarPermissaoItem (listaItensPorEstrutura,seguranca);
			
			estruturaCorrente.setOrdemInsercao(System.currentTimeMillis());
			
			mapaEstruturaItens.put(estruturaCorrente, listaItensVisualizacaoPermitida);
		}
		
		TelaExportacaoBean telaBean = exportarCadastro(configuracao ,mapaEstruturaItens,dataHoraGeracao, seguranca);
		
		return telaBean;
	}
	
	/**
	 * Exporta��o dos dados do acompanhamento
	 * 
	 * @param 
	 * @param 
	 * @return
	 * @throws ECARException 
	 */
	public TelaExportacaoBean gerarArquivosMonitoramento(Long codArefReferencia, Collection<SisAtributoSatb> niveisPlanejamento, Long codTipoAcompanhamento, 
														 Boolean itensSemInformacaoNivelPlanejamento, Long codCor, SegurancaECAR seguranca) throws ECARException {
		AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(null);
		AcompReferenciaAref acompReferenciaAref = null;
		if (codArefReferencia != null && codArefReferencia.longValue() > 0){
			acompReferenciaAref = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, codArefReferencia); 
		}
		ConfiguracaoCfg configuracaoCfg = new ecar.dao.ConfiguracaoDao(null).getConfiguracao();
		Collection periodosConsiderados = new ArrayList();
		OrgaoOrg orgaoReferencia = new OrgaoOrg();
		TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa) new TipoAcompanhamentoDao().buscar(TipoAcompanhamentoTa.class, codTipoAcompanhamento);
		List<OrgaoOrg> orgaosReferencias = new ArrayList<OrgaoOrg>();
		
		if(tipoAcompanhamento != null && tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S") && acompReferenciaAref != null) {
			//Recupera as refer�ncias que cont�m o mesmo dia/mes/ano da refer�ncia escolhida
			periodosConsiderados = acompReferenciaDao.getListaMesmaReferenciaDiaMesAno(acompReferenciaAref);
			//Recupera os distintos �rg�os de cada refer�ncia da lista						
			orgaosReferencias = acompReferenciaDao.getOrgaosReferencias(periodosConsiderados);		
		} else {
			if(codArefReferencia.intValue() > 0) {
				periodosConsiderados = acompReferenciaDao.getPeriodosAnteriores(codArefReferencia, Integer.parseInt(ConstantesECAR.UM),  Long.valueOf(codTipoAcompanhamento));
			}
			
		}
		
		Object itens[] = acompReferenciaItemDao.getItensAcompanhamentoInPeriodosByOrgaoRespPaginadoConsiderarPermissao(periodosConsiderados,
        		niveisPlanejamento, orgaosReferencias, seguranca.getUsuario(),
        		seguranca.getGruposAcesso(), codTipoAcompanhamento, null, 
        		itensSemInformacaoNivelPlanejamento, codCor, "", -2, 1, false, false, "", false); 

		List<AtributoEstruturaListagemItens> itensAcompanhamentos = new ArrayList((Collection<AtributoEstruturaListagemItens>)itens[0]);
		
		List<AcompReferenciaItemAri> acompReferenciaItens = new ArrayList((Collection<AcompReferenciaItemAri>)itens[1]);
		
		Map<EstruturaEtt, List<ItemEstruturaIett>> mapaEstruturaItens = montarMapaItensPorEstruturas(itensAcompanhamentos);
		Map<EstruturaEtt, List<AcompReferenciaItemAri>> mapaEstruturaAris = montarMapaArisPorEstruturas(acompReferenciaItens);
		
		Date dataHoraGeracao = new Date();
		
		TelaExportacaoBean telaBean = exportarMonitoramento(configuracaoCfg, mapaEstruturaItens, mapaEstruturaAris, dataHoraGeracao, acompReferenciaAref, seguranca);
		
		return telaBean;
	}
	
	/**
	 * Verifica se a fun��o est� associada a estrutura e invoca o m�todo correspondente a fun��o.
	 * 
	 * @param mapaItensEstruturaExportacao
	 * @return
	 * @throws ECARException 
	 */
	@SuppressWarnings("unchecked")
	private TelaExportacaoBean exportarCadastro (ConfiguracaoCfg configuracao, Map<EstruturaEtt,List<ItemEstruturaIett>> mapaItensEstruturaExportacao,Date dataHoraGeracao, SegurancaECAR segurancaECAR) throws ECARException{

		TelaExportacaoBean telaBean = new TelaExportacaoBean();
		telaBean.setDataGeracao(dataHoraGeracao);
		EstruturaDao estruturaDao = new EstruturaDao(null);
		
		Set<EstruturaEtt> estruturas = mapaItensEstruturaExportacao.keySet(); 
		
		for (EstruturaEtt estruturaCorrente : estruturas) {
			List<ItemEstruturaIett> listaItens = mapaItensEstruturaExportacao.get(estruturaCorrente);
			
			
			//Inicio dos Dados Gerais
				FuncaoFun funcaoDadosGerais = funDao.getFuncaoDadosGerais();
				List<ObjetoEstrutura> colunas = estruturaDao.getAtributosEstruturaDadosGerais(estruturaCorrente);
				
				ExportaFuncao exportaFuncaoDadosGerais = new ExportaFuncaoConfiguravelAtributoLivreDadosGerais();
				CaminhoArquivoExportacaoBean caminho =  exportaFuncaoDadosGerais.exportar(estruturaCorrente, listaItens, null, funcaoDadosGerais, configuracao,dataHoraGeracao, comunicacaoExportacao,colunas, segurancaECAR, null, null);
				telaBean.adicionarCaminhoArquivo (caminho);
			//Fim dos Dados Gerais
			
			//Inicio dos Pontos Criticos
				FuncaoFun funcaoPontosCriticos = funDao.getFuncaoPontosCriticos();
				List<ObjetoEstrutura> colunasPontosCriticos = estruturaDao.getAtributosEstrutura(estruturaCorrente, funcaoPontosCriticos);
				
				ExportaFuncao exportaFuncaoPontosCriticos = new ExportaFuncaoConfiguravelAtributoLivrePontosCriticos();
				CaminhoArquivoExportacaoBean caminhoPontoCritico =  exportaFuncaoPontosCriticos.exportar(estruturaCorrente, listaItens, null, funcaoPontosCriticos, configuracao,dataHoraGeracao, comunicacaoExportacao,colunasPontosCriticos, segurancaECAR, null, null);
				
				if (caminhoPontoCritico != null) {
					telaBean.adicionarCaminhoArquivo (caminhoPontoCritico);
				}
				
			//Fim dos Pontos Criticos
	
			//Inicio dos Apontamentos dos Pontos Criticos
				if (caminhoPontoCritico != null) {
					FuncaoFun subFuncaoApontamentos = funDao.getFuncaoPorNome(FuncaoDao.NOME_FUNCAO_APONTAMENTOS);
					ExportaFuncao exportaSubFuncaoApontamentos = new ExportaSubFuncaoApontamentos ();
	
					CaminhoArquivoExportacaoBean caminhoApontamentos = exportaSubFuncaoApontamentos.exportar(estruturaCorrente, listaItens, null, subFuncaoApontamentos, configuracao, dataHoraGeracao, comunicacaoExportacao, null, segurancaECAR, null, null);
	
					if (caminhoApontamentos != null) {
						telaBean.adicionarCaminhoArquivo (caminhoApontamentos);
					}
				}
			//Fim dos Apontamentos dos Pontos Criticos
	
			//Inicio das Meta indicadores
			FuncaoFun funcaoMetasIndicadores = funDao.getFuncaoPorNome(FuncaoDao.NOME_FUNCAO_METAS_INDICADORES);
			CaminhoArquivoExportacaoBean caminhoMetasIndicadores = new ExportaFuncaoItemEstrtIndResulIettr().exportar(estruturaCorrente, listaItens, null, funcaoMetasIndicadores, configuracao,dataHoraGeracao,comunicacaoExportacao,null, segurancaECAR, null, null);
			if (caminhoMetasIndicadores != null){
				telaBean.adicionarCaminhoArquivo(caminhoMetasIndicadores);
			}
			//Fim dos Meta indicadores
	
			//Inicio dos Locais de Meta indicadores
			if (caminhoMetasIndicadores != null){
				FuncaoFun subFuncaoLocais = funDao.getFuncaoPorNome(FuncaoDao.NOME_FUNCAO_QUANTIDADES_PREVISTAS);
				ExportaFuncao exportaSubFuncaoLocais = new ExportaSubFuncaoLocais();
	
				CaminhoArquivoExportacaoBean caminhoSubFuncaoLocais = exportaSubFuncaoLocais.exportar(estruturaCorrente, listaItens, null, subFuncaoLocais, configuracao, dataHoraGeracao, comunicacaoExportacao, null, segurancaECAR, null, null);
	
				if (caminhoSubFuncaoLocais != null) {
					telaBean.adicionarCaminhoArquivo (caminhoSubFuncaoLocais);
				}
			}
			//Fim dos Locais de Meta indicadores
	
			//Inicio dos Diarios de Bordos
			FuncaoFun funcaoDiarioBordo = funDao.getFuncaoPorNome(FuncaoDao.NOME_FUNCAO_EVENTOS);
			CaminhoArquivoExportacaoBean caminhoDiarioBordo = new ExportaFuncaoItemEstrutAcaoIetta().exportar(estruturaCorrente, listaItens, null, funcaoDiarioBordo, configuracao,dataHoraGeracao,comunicacaoExportacao,null, segurancaECAR, null, null);
			if (caminhoDiarioBordo != null){
				telaBean.adicionarCaminhoArquivo(caminhoDiarioBordo);
			}
			
			//Fim dos Diarios de Bordos
	
			//Inicio das Localidades
			FuncaoFun funcaoLocalidade = funDao.getFuncaoPorNome(FuncaoDao.NOME_FUNCAO_LOCALIZACAO);
			CaminhoArquivoExportacaoBean caminhoLocalidade = new ExportaFuncaoLocalidades().exportar(estruturaCorrente, listaItens, null, funcaoLocalidade, configuracao,dataHoraGeracao,comunicacaoExportacao, null, segurancaECAR, null, null);  
			if (caminhoLocalidade != null){
				telaBean.adicionarCaminhoArquivo(caminhoLocalidade);
			}

			//Fim das Localidades
	
			//Inicio das Entidades
			FuncaoFun funcaoEntidades = funDao.getFuncaoEntidades();
			CaminhoArquivoExportacaoBean caminhoEntidades =  new ExportaFuncaoEntidades().exportar(estruturaCorrente, listaItens, null, funcaoEntidades, configuracao,dataHoraGeracao,comunicacaoExportacao,null, segurancaECAR, null, null);
			if(caminhoEntidades != null)
				telaBean.adicionarCaminhoArquivo(caminhoEntidades);
			//Fim das Entidades

			
			//exportar anexos
			FuncaoFun categorias = funDao.getFuncaoPorNome(FuncaoDao.NOME_FUNCAO_CATEGORIAS);
			CaminhoArquivoExportacaoBean caminhoAnexos = new ExportaFuncaoAnexo().exportar(estruturaCorrente, listaItens, null, categorias, configuracao,dataHoraGeracao,comunicacaoExportacao,null, segurancaECAR, null, null);
			if(caminhoAnexos != null){
				telaBean.adicionarCaminhoArquivo(caminhoAnexos);
			}
		}
		

		return telaBean;
	}

	/**
	 * 
	 * @param configuracao
	 * @param mapaItensEstruturaExportacao
	 * @param dataHoraGeracao
	 * @param acompReferenciaAref
	 * @param segurancaECAR
	 * @return
	 * @throws ECARException
	 */
	private TelaExportacaoBean exportarMonitoramento(ConfiguracaoCfg configuracao, Map<EstruturaEtt,List<ItemEstruturaIett>> mapaItensEstruturaExportacao, Map<EstruturaEtt,List<AcompReferenciaItemAri>> mapaArisEstruturaExportacao, Date dataHoraGeracao, AcompReferenciaAref acompReferenciaAref, SegurancaECAR segurancaECAR) throws ECARException{

		TelaExportacaoBean telaBean = new TelaExportacaoBean();
		telaBean.setDataGeracao(dataHoraGeracao);
		EstruturaDao estruturaDao = new EstruturaDao(null);
		AbaDao abaDao = new AbaDao(null);
		boolean possuiFuncaoConfigurada;
		
		Set<EstruturaEtt> estruturas = mapaItensEstruturaExportacao.keySet(); 
		
		for (EstruturaEtt estruturaCorrente : estruturas) {
			List<ItemEstruturaIett> listaItens = mapaItensEstruturaExportacao.get(estruturaCorrente);
			List<AcompReferenciaItemAri> listaAris = mapaArisEstruturaExportacao.get(estruturaCorrente);			
			
			//Inicio dos Dados Gerais
			FuncaoFun funcaoDadosGerais = funDao.getFuncaoDadosGerais();
			
			Aba abaDadosGerais = abaDao.buscarAba(ConstantesECAR.ABA_DADOS_GERAIS);
			//abaDadosGerais.setNomeAba(abaDadosGerais.getFuncaoFun().getNomeFun());
			//funcaoDadosGerais.setNomeFun(abaDadosGerais.getFuncaoFun().getNomeFun());
			
			List<ObjetoEstrutura> colunas = estruturaDao.getAtributosEstruturaDadosGerais(estruturaCorrente);
			Set listaFuncoes = estruturaCorrente.getEstruturaFuncaoEttfs();
			Iterator itListaFuncoes = listaFuncoes.iterator();
			
			possuiFuncaoConfigurada = false;
			if(abaDadosGerais.getFuncaoFun() == null){
				possuiFuncaoConfigurada = true;
			} else{
	        	while(itListaFuncoes.hasNext()){
	        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
	        		if(abaDadosGerais.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
	        			possuiFuncaoConfigurada = true;
	        			break;
	        		}
	        	}
			}
			
			ExportaFuncao exportaFuncaoDadosGerais = new ExportaFuncaoConfiguravelAtributoLivreDadosGerais();
			CaminhoArquivoExportacaoBean caminho =  exportaFuncaoDadosGerais.exportar(estruturaCorrente, listaItens, null, funcaoDadosGerais, configuracao,dataHoraGeracao,comunicacaoExportacao,colunas, segurancaECAR, acompReferenciaAref, abaDadosGerais);
			if (caminho != null && possuiFuncaoConfigurada){
				telaBean.adicionarCaminhoArquivo (caminho);
			}
			//Fim dos Dados Gerais

			//Inicio das Meta indicadores
			FuncaoFun funcaoMetasIndicadores = funDao.getFuncaoPorNome(FuncaoDao.NOME_FUNCAO_METAS_INDICADORES);
			Aba abaMetasIndicadores = abaDao.buscarAba(ConstantesECAR.ABA_INDICADORES);
			
			itListaFuncoes = listaFuncoes.iterator();
			possuiFuncaoConfigurada = false;
			if(abaMetasIndicadores.getFuncaoFun() == null){
				possuiFuncaoConfigurada = true;
			} else{
				possuiFuncaoConfigurada = false;
	        	while(itListaFuncoes.hasNext()){
	        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
	        		if(abaMetasIndicadores.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
	        			possuiFuncaoConfigurada = true;
	        			break;
	        		}
	        	}
			}
			
			CaminhoArquivoExportacaoBean caminhoMetasIndicadores = new ExportaIndicadoresRealizado().exportar(estruturaCorrente, listaItens, listaAris, funcaoMetasIndicadores, configuracao, dataHoraGeracao, comunicacaoExportacao, null, segurancaECAR, acompReferenciaAref, abaMetasIndicadores);
			if (caminhoMetasIndicadores != null && possuiFuncaoConfigurada){
				telaBean.adicionarCaminhoArquivo(caminhoMetasIndicadores);
			}
			//Fim das Meta indicadores						

			//Inicio Meta indicadores por Local
			if (caminhoMetasIndicadores != null && possuiFuncaoConfigurada){				
				//FuncaoFun subFuncaoIndicadores = funDao.getFuncaoPorNome(FuncaoDao.NOME_FUNCAO_QUANTIDADES_PREVISTAS);
				abaMetasIndicadores = abaDao.buscarAba(ConstantesECAR.ABA_INDICADORES);
				ExportaFuncao exportaRealizadoLocal = new ExportaIndicadoresRealizadoPorLocal();
	
				CaminhoArquivoExportacaoBean caminhoSubFuncaoRealizadoPorLocal = exportaRealizadoLocal.exportar(estruturaCorrente, listaItens, listaAris, null, configuracao, dataHoraGeracao, comunicacaoExportacao, null, segurancaECAR, acompReferenciaAref, abaMetasIndicadores);
	
				if (caminhoSubFuncaoRealizadoPorLocal != null) {
					telaBean.adicionarCaminhoArquivo (caminhoSubFuncaoRealizadoPorLocal);
				}
			}
			//Fim Meta indicadores por Local			
			
			//Inicio Parecer
			//Parecer n�o tem fun��o correspondente
			Aba abaParecer = abaDao.buscarAba(ConstantesECAR.ABA_SITUACAO);
			itListaFuncoes = listaFuncoes.iterator();
			possuiFuncaoConfigurada = false;
			if(abaParecer.getFuncaoFun() == null){
				possuiFuncaoConfigurada = true;
			} else{
				possuiFuncaoConfigurada = false;
	        	while(itListaFuncoes.hasNext()){
	        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
	        		if(abaParecer.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
	        			possuiFuncaoConfigurada = true;
	        			break;
	        		}
	        	}
			}
			
			CaminhoArquivoExportacaoBean caminhoParecer = new ExportarParecerMonitoramento().exportar(estruturaCorrente, listaItens, listaAris ,null, configuracao, dataHoraGeracao, comunicacaoExportacao, null, segurancaECAR, acompReferenciaAref, abaParecer);
			if (caminhoParecer != null && possuiFuncaoConfigurada){
				telaBean.adicionarCaminhoArquivo(caminhoParecer);
			}
			//Fim Parecer
			
			//Inicio dos Pontos Criticos
			FuncaoFun funcaoPontosCriticos = funDao.getFuncaoPontosCriticos();
			Aba abaPontosCriticos = abaDao.buscarAba(ConstantesECAR.ABA_PONTOS_CRITICOS);
			List<ObjetoEstrutura> colunasPontosCriticos = estruturaDao.getAtributosEstrutura(estruturaCorrente, funcaoPontosCriticos);
			itListaFuncoes = listaFuncoes.iterator();
			possuiFuncaoConfigurada = false;
			if(abaPontosCriticos.getFuncaoFun() == null){
				possuiFuncaoConfigurada = true;
			} else{
				possuiFuncaoConfigurada = false;
	        	while(itListaFuncoes.hasNext()){
	        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
	        		if(abaPontosCriticos.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
	        			possuiFuncaoConfigurada = true;
	        			break;
	        		}
	        	}
			}
			
			ExportaFuncao exportaFuncaoPontosCriticos = new ExportaFuncaoConfiguravelAtributoLivrePontosCriticos();
			CaminhoArquivoExportacaoBean caminhoPontoCritico =  exportaFuncaoPontosCriticos.exportar(estruturaCorrente, listaItens, null, funcaoPontosCriticos, configuracao,dataHoraGeracao,comunicacaoExportacao,colunasPontosCriticos, segurancaECAR, acompReferenciaAref, abaPontosCriticos);
			
			if (caminhoPontoCritico != null && possuiFuncaoConfigurada) {
				telaBean.adicionarCaminhoArquivo(caminhoPontoCritico);
			}
			//Fim dos Pontos Criticos
	
			//Inicio dos Apontamentos dos Pontos Criticos
			if (caminhoPontoCritico != null && possuiFuncaoConfigurada) {
				FuncaoFun subFuncaoApontamentos = funDao.getFuncaoPorNome(FuncaoDao.NOME_FUNCAO_APONTAMENTOS);
				//A subfun��o de apontamentos n�o possui aba correspondente
				ExportaFuncao exportaSubFuncaoApontamentos = new ExportaSubFuncaoApontamentos();
				CaminhoArquivoExportacaoBean caminhoApontamentos = exportaSubFuncaoApontamentos.exportar(estruturaCorrente, listaItens, null, subFuncaoApontamentos, configuracao, dataHoraGeracao, comunicacaoExportacao, null, segurancaECAR, acompReferenciaAref, null);
	
				if (caminhoApontamentos != null) {
					telaBean.adicionarCaminhoArquivo (caminhoApontamentos);
				}
			}
			//Fim dos Apontamentos dos Pontos Criticos
	
			//Inicio dos Diarios de Bordos
			FuncaoFun funcaoDiarioBordo = funDao.getFuncaoPorNome(FuncaoDao.NOME_FUNCAO_EVENTOS);
			Aba abaDiarioBordo = abaDao.buscarAba(ConstantesECAR.ABA_EVENTOS);
			itListaFuncoes = listaFuncoes.iterator();			
			possuiFuncaoConfigurada = false;
			if(abaDiarioBordo.getFuncaoFun() == null){
				possuiFuncaoConfigurada = true;
			} else{
				possuiFuncaoConfigurada = false;
	        	while(itListaFuncoes.hasNext()){
	        		EstruturaFuncaoEttf funcao = (EstruturaFuncaoEttf) itListaFuncoes.next();
	        		if(abaDiarioBordo.getFuncaoFun().getCodFun().equals(funcao.getFuncaoFun().getCodFun())){
	        			possuiFuncaoConfigurada = true;
	        			break;
	        		}
	        	}
			}
			
			CaminhoArquivoExportacaoBean caminhoDiarioBordo = new ExportaFuncaoItemEstrutAcaoIetta().exportar(estruturaCorrente, listaItens, null, funcaoDiarioBordo, configuracao,dataHoraGeracao,comunicacaoExportacao,null, segurancaECAR, acompReferenciaAref, abaDiarioBordo);
			if (caminhoDiarioBordo != null && possuiFuncaoConfigurada){
				telaBean.adicionarCaminhoArquivo(caminhoDiarioBordo);
			}		
			//Fim dos Diarios de Bordos
		}
		return telaBean;
	}
	
	
	/**
	 * Identifica as estruturas filhas de uma estrutura passada como par�metro.
	 * 
	 * Obs.: Trecho de c�digo removido da classe ExportacaoRelatorioItemEstruturaDao
	 * 
	 * @param estrutura
	 * @return
	 * @throws ECARException
	 */
	private List getEstruturasFilhas(EstruturaEtt estrutura) throws ECARException {
		
		List retorno = new ArrayList();
		
		Iterator itEstruturaFilha = estrutura.getEstruturaEtts().iterator();
		
		while (itEstruturaFilha.hasNext()){
			
			EstruturaEtt estruturaFilha = (EstruturaEtt) itEstruturaFilha.next();
			
			if (estruturaFilha.getIndAtivoEtt().equals("S")){
				retorno.add(estruturaFilha);
			}
			
			if (estruturaFilha.getEstruturaEtts() != null && !estruturaFilha.getEstruturaEtts().isEmpty()){
				
				retorno.addAll(getEstruturasFilhas(estruturaFilha));
			}
		}
		
		return retorno;
	}

	
	//DAVI
	/**
	 * Monta Lista de c�digo Pai,
	 * 
	 * Obs.:trecho de c�digo removido da classe ExportacaoRelatorioItemEstruturaDao
	 */
	private List<Long> montarListaCodigosPais(ItemEstruturaIett itemPai){
		
		List<Long> possiveisItensPais = new ArrayList<Long>();
		
		possiveisItensPais.add(itemPai.getCodIett());
		
		if (itemPai.getItemEstruturaIetts() != null && itemPai.getItemEstruturaIetts().size() > 0){
			
			Iterator<ItemEstruturaIett> itensFilhosIt = itemPai.getItemEstruturaIetts().iterator();
			
			while (itensFilhosIt.hasNext()){
				
				possiveisItensPais.addAll(montarListaCodigosPais((ItemEstruturaIett)itensFilhosIt.next()));
			}
		}
		
		return possiveisItensPais;
	}
	

	/**
	 * Identifica na lista passada como par�metro, os itens que o usu�rio possui permiss�o de visualiza��o. 
	 * 
	 * @param listaItensPorEstrutura
	 * @param seguranca
	 * @return
	 * @throws ECARException 
	 */
	private List<ItemEstruturaIett> validarPermissaoItem(List<ItemEstruturaIett> listaItensPorEstrutura, SegurancaECAR seguranca) throws ECARException {
		
		List<ItemEstruturaIett> listaItensPermitidos = new ArrayList<ItemEstruturaIett>();
		ValidaPermissao validaPermissao = new ValidaPermissao();
		
		for (ItemEstruturaIett itemEstruturaCorrente : listaItensPorEstrutura) {

			boolean permissaoAcessoItem = validaPermissao.permissaoConsultarItem(itemEstruturaCorrente, seguranca.getUsuario(), seguranca.getGruposAcesso()); 

			if(permissaoAcessoItem) {
				listaItensPermitidos.add(itemEstruturaCorrente);
			}
			
		}
		
		return listaItensPermitidos;
	}

	
	
	
//	/**
//	 * Faz a valida��o semantica e retorna o objeto resultadoValidacaoBean com a lista de todos os dto's originais, 
//	 * lista de todos os dto's v�lidos e a lista de exce��o com registros rejeitados
//	 * @param dto
//	 * @return
//	 * @throws ECARException
//	 */
//	private ResultadoValidacaoBean montarResultadoValidacaoSemantica(List<IBusinessObjectDTO> dto) throws ECARException{
//		ResultadoValidacaoBean resultadoValidacaoBean = new ResultadoValidacaoBean();
//		resultadoValidacaoBean.setRegistrosOriginais(dto);
//		for (IBusinessObjectDTO iBusinessObjectDTO : dto) {
//			//Faz a valida��o semantica e adiciona os objetos �s listas de registros validos e invalidos
//			try {
//				if (validadorSemantico.valida(iBusinessObjectDTO)){
//					resultadoValidacaoBean.getRegistrosValidos().add(iBusinessObjectDTO);
//				}
//			} catch (SemanticValidationException e) {
//				resultadoValidacaoBean.getRegistrosInvalidos().add(e);
//			}
//		}
//		return resultadoValidacaoBean;
//	}
//	
//	/**
//	 * Gera os itens a partir do dto 
//	 * @param perfilIntercambioDadosPflid
//	 * @param dtosValidos
//	 * @return
//	 * @throws ECARException
//	 */
//	private List<List<ItemEstruturaIett>> geraItensEstruturaIetts(List<IBusinessObjectDTO> dtosValidos, UsuarioUsu usuarioLogado) throws ECARException{
//		MontadorDTO montadorObjetonegocio = montadorDTO;
//		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
//		List<List<ItemEstruturaIett>> listaItensImportacao = new ArrayList<List<ItemEstruturaIett>>();
//		List<ItemEstruturaIett> listaItensInclusao = new ArrayList<ItemEstruturaIett>();
//		List<ItemEstruturaIett> listaItensAlteracao = new ArrayList<ItemEstruturaIett>();
//		List<ItemEstruturaIett> listaItensExclusao = new ArrayList<ItemEstruturaIett>();
//		
//		for (IBusinessObjectDTO iBusinessObjectDTO : dtosValidos) {
//			ItemEstruturaDTO itemEstruturaDTO = (ItemEstruturaDTO) iBusinessObjectDTO;
//			ItemEstruturaIett itemEstruturaIett = itemEstruturaDao.getItemEstruturaIettByEstruturaSiglaIett(configuracao.getPerfil().getEstruturaEttCriacaoItemImp(), itemEstruturaDTO.getCodigo(), itemEstruturaDTO.getValorAssociacao(), itemEstruturaDTO.getTipoEmpreendimento());
//			if (itemEstruturaDTO.getOperacao().equals(ConstantesECAR.TIPO_OPERACAO_MANUTENCAO)){				
//				//Inclus�o se o item n�o existir na base de dados 
//				if (itemEstruturaIett == null){
//					itemEstruturaDTO.setOperacao(ConstantesECAR.TIPO_OPERACAO_INCLUSAO);
//					listaItensInclusao.add(montadorObjetonegocio.montaItemEstruturaIett(itemEstruturaIett, itemEstruturaDTO, usuarioLogado));
//				} else {
//					//Altera o item caso j� exista na base de dados
//					itemEstruturaDTO.setOperacao(ConstantesECAR.TIPO_OPERACAO_ALTERACAO);
//					listaItensAlteracao.add(montadorObjetonegocio.montaItemEstruturaIett(itemEstruturaIett, itemEstruturaDTO, usuarioLogado));
//				}
//			} else if (itemEstruturaDTO.getOperacao().equals(ConstantesECAR.TIPO_OPERACAO_EXCLUSAO)){
//				listaItensExclusao.add(itemEstruturaIett);
//			}
//		}
//		listaItensImportacao.add(listaItensInclusao);
//		listaItensImportacao.add(listaItensAlteracao);
//		listaItensImportacao.add(listaItensExclusao);
//		
//		return listaItensImportacao;
//	}
	
	/**
	 * Envia e-mail da importa��o
	 * @param usuarioLogado
	 * @throws ECARException
	 */
	private void enviarEmailImportacao(UsuarioUsu usuarioLogado) throws ECARException{
		PerfilIntercambioDadosDao perfilDao = new PerfilIntercambioDadosDao();
		//Carrega o perfil novamente, para evitar o erro do lazy
		//pois esse objeto � guardado na sess�o
		PerfilIntercambioDadosPflid perfil = (PerfilIntercambioDadosPflid) perfilDao.buscar(PerfilIntercambioDadosPflid.class, configuracao.getPerfil().getCodPflid());
		AgendadorEmail agendadorEmail = new AgendadorEmail();
		UsuarioDao usuarioDao = new UsuarioDao();
		//Verifica se est� ativo o envio de e-mail na importa��o
		if (perfil.getIndAtivoAvisoImpPflid() != null && perfil.getIndAtivoAvisoImpPflid().equals(Dominios.SIM)){
			//SisAtributoSatb corresponde ao grupo de acesso configurado para envio de e-mail no perfil 
			SisAtributoSatb grupoEnvioEmail = perfil.getSisAtributoSatbAcessoEnvioEmailImp();
			TextosSiteTxt textosSiteTxt = perfil.getComposicaoEmailPflid();
			//envia e-mail apenas se o grupo e o textoSite(e-mail) estiverem configurados no perfil.
			if (grupoEnvioEmail != null && textosSiteTxt != null){
				//carrega a lista de usu�rios que pertence ao grupo de acesso passado como par�metro
				List <UsuarioUsu> usuarios = usuarioDao.getUsuariosBySisAtributoSatb(grupoEnvioEmail);
				if (usuarios != null){
					//O remetente ser� o configurado no textoSiteTxt, caso 
					//n�o tenha nada configurado, o remente ser� o e-mail de contato 
					//configurado no cadastro de empresa
					String remetente = "";
					String assuntoEmail = textosSiteTxt.getDescricaoUsoTxts();
					if (textosSiteTxt.getEmailResponsavelTxts() != null){
						remetente = textosSiteTxt.getEmailResponsavelTxts();
					} else {
						if (textosSiteTxt.getEmpresaEmp() != null){
							remetente = textosSiteTxt.getEmpresaEmp().getEmailContatoEmp();
						}
					}
					//Varre a lista de usu�rios montando e enviando o e-mail
					for (UsuarioUsu usuarioUsu : usuarios) {
						if (usuarioUsu.getEmail1UsuSent() != null && !usuarioUsu.getEmail1UsuSent().equals(Dominios.STRING_VAZIA)){
							//monta o texto do e-mail
							String textoEmail = agendadorEmail.montaEmail(textosSiteTxt.getTextoTxts(), usuarioUsu.getNomeUsu()).toString();
							//envia o e-mail
							agendadorEmail.enviarEmail(assuntoEmail, remetente, textoEmail, usuarioUsu.getEmail1UsuSent(), null, null, usuarioLogado);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Monta um mapa com estrutura como chave e a lista de itens como valor
	 * @param listAtributoEstruturaListagemItens
	 * @return mapa com estrutura e lista de itens
	 * @throws ECARException
	 */
	private SortedMap<EstruturaEtt, List<ItemEstruturaIett>> montarMapaItensPorEstruturas(List<AtributoEstruturaListagemItens> listAtributoEstruturaListagemItens){
		SortedMap<EstruturaEtt, List<ItemEstruturaIett>> mapaEstruturaItens = new  TreeMap<EstruturaEtt, List<ItemEstruturaIett>>();
		ItemEstruturaIett itemEstruturaIett = null;
		EstruturaEtt estruturaEtt = null;
		for (AtributoEstruturaListagemItens atributoEstruturaListagemItens : listAtributoEstruturaListagemItens) {
			itemEstruturaIett = atributoEstruturaListagemItens.getItem();
			estruturaEtt = atributoEstruturaListagemItens.getItem().getEstruturaEtt();
			//verifica se a estrutura j� existe no mapa
			//caso exista, apenas adiciona o item a lista de itens
			if (mapaEstruturaItens.containsKey(estruturaEtt)){
				mapaEstruturaItens.get(estruturaEtt).add(itemEstruturaIett);
			} else {
				//caso n�o exista, cria uma lista e coloca no map com a estrutura 
				List<ItemEstruturaIett> itensEstrutura = new ArrayList<ItemEstruturaIett>();
				itensEstrutura.add(itemEstruturaIett);
				mapaEstruturaItens.put(estruturaEtt, itensEstrutura);
			}
		}
		return mapaEstruturaItens;
	}
	
	/**
	 * Monta um mapa com estrutura como chave e a lista de AcompReferenciaItens como valor
	 * @param listAtributoEstruturaListagemItens
	 * @return mapa com estrutura e lista de AcompReferenciaItens
	 * @throws ECARException
	 */
	private SortedMap<EstruturaEtt, List<AcompReferenciaItemAri>> montarMapaArisPorEstruturas(List<AcompReferenciaItemAri> listAcompReferenciaItens){
		SortedMap<EstruturaEtt, List<AcompReferenciaItemAri>> mapaEstruturaAris = new  TreeMap<EstruturaEtt, List<AcompReferenciaItemAri>>();
		ItemEstruturaIett itemEstruturaIett = null;
		EstruturaEtt estruturaEtt = null;
		for (AcompReferenciaItemAri acompReferenciaItem : listAcompReferenciaItens) {
			
			estruturaEtt = acompReferenciaItem.getItemEstruturaIett().getEstruturaEtt();
			//verifica se a estrutura j� existe no mapa
			//caso exista, apenas adiciona o item a lista de itens
			if (mapaEstruturaAris.containsKey(estruturaEtt)){
				mapaEstruturaAris.get(estruturaEtt).add(acompReferenciaItem);
			} else {
				//caso n�o exista, cria uma lista e coloca no map com a estrutura 
				List<AcompReferenciaItemAri> acompReferenciaItens = new ArrayList<AcompReferenciaItemAri>();
				acompReferenciaItens.add(acompReferenciaItem);
				mapaEstruturaAris.put(estruturaEtt, acompReferenciaItens);
			}
		}
		return mapaEstruturaAris;
	}

}