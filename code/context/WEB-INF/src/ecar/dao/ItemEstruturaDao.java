/*
 * Criado em 13/12/2004
 *
 */
package ecar.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.LinkedMap;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;

import br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAtividadeCadastro;

import comum.database.Dao;
import comum.util.ConstantesECAR;
import comum.util.Data;
import comum.util.EtiquetaUtils;
import comum.util.FileUpload;
import comum.util.Mensagem;
import comum.util.Pagina;
import comum.util.Util;

import ecar.action.ActionEstrutura;
import ecar.action.ActionSisAtributo;
import ecar.api.facade.EcarData;
import ecar.api.facade.Exercicio;
import ecar.api.facade.IndicadorResultado;
import ecar.api.facade.Previsto;
import ecar.bean.AtributoEstruturaBean;
import ecar.bean.AtributoEstruturaListagemItens;
import ecar.bean.IndicativoItemWebServiceBean;
import ecar.bean.ItemWSMinBean;
import ecar.bean.ItemWebServiceBean;
import ecar.bean.NomeImgsNivelPlanejamentoBean;
import ecar.bean.OrdenacaoIett;
import ecar.bean.OrdenacaoPorNivel;
import ecar.exception.ECARException;
import ecar.historico.Historico;
import ecar.historico.HistoricoIett;
import ecar.login.SegurancaECAR;
import ecar.permissao.ControlePermissao;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.AgendaAge;
import ecar.pojo.AgendaEntidadesAgeent;
import ecar.pojo.AgendaOcorrenciaAgeo;
import ecar.pojo.ApontamentoApt;
import ecar.pojo.AreaAre;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.DestaqueItemRelDtqir;
import ecar.pojo.EfIettFonteTotEfieft;
import ecar.pojo.EstrutTpFuncAcmpEtttfa;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.ExercicioExe;
import ecar.pojo.FuncaoFun;
import ecar.pojo.FuncaoSisAtributo;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstrUplCategIettuc;
import ecar.pojo.ItemEstrtBenefIettb;
import ecar.pojo.ItemEstrtIndResulCorIettrcor;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrtIndResulLocalIettirl;
import ecar.pojo.ItemEstrutAcaoIetta;
import ecar.pojo.ItemEstrutCriterioIettc;
import ecar.pojo.ItemEstrutEntidadeIette;
import ecar.pojo.ItemEstrutFisicoIettf;
import ecar.pojo.ItemEstrutLocalIettl;
import ecar.pojo.ItemEstrutUploadIettup;
import ecar.pojo.ItemEstrutUsuarioIettus;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaIettMin;
import ecar.pojo.ItemEstruturaIettPPA;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.ItemEstruturaSisAtributoIettSatbPK;
import ecar.pojo.ItemFuncaoLink;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.PaiFilho;
import ecar.pojo.PeriodicidadePrdc;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.PontoCriticoSisAtributoPtcSatb;
import ecar.pojo.RegDemandaRegd;
import ecar.pojo.SequenciadoraSeq;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.SituacaoSit;
import ecar.pojo.SubAreaSare;
import ecar.pojo.TipoAcompFuncAcompTafc;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.TipoValor;
import ecar.pojo.TipoValorEnum;
import ecar.pojo.UnidadeOrcamentariaUO;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.acompanhamentoEstrategico.Acao;
import ecar.pojo.acompanhamentoEstrategico.Atividade;
import ecar.pojo.acompanhamentoEstrategico.Estrategia;
import ecar.pojo.acompanhamentoEstrategico.Indicador;
import ecar.pojo.acompanhamentoEstrategico.IndicadorDetalhamento;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.pojo.acompanhamentoEstrategico.Orgao;
import ecar.pojo.acompanhamentoEstrategico.PeriodoAcompanhamento;
import ecar.pojo.acompanhamentoEstrategico.Produto;
import ecar.pojo.acompanhamentoEstrategico.Responsavel;
import ecar.pojo.acompanhamentoEstrategico.Resultado;
import ecar.pojo.acompanhamentoEstrategico.ResultadoNaoMonitorado;
import ecar.pojo.acompanhamentoEstrategico.ResultadoStatusContar;
import ecar.pojo.acompanhamentoEstrategico.ResutadoSituacaoContar;
import ecar.pojo.historico.HistoricoItemEstruturaIett;
import ecar.pojo.historico.HistoricoPontoCriticoPtc;
import ecar.pojo.historico.HistoricoXml;
import ecar.pojo.simpr.WsAcaoAtividadeCadastro;
import ecar.pojo.simpr.WsAcaoCadastro;
import ecar.pojo.simpr.WsDominio;
import ecar.pojo.simpr.WsIndicador;
import ecar.pojo.simpr.WsIndicadorCadastro;
import ecar.pojo.simpr.WsIndicadorMetodologia;
import ecar.servlet.indicador.IndicadorMonitoramentoBean;
import ecar.taglib.util.Input;
import ecar.util.CriptografiaUtil;
import ecar.util.Dominios;
import ecar.util.Entidade;

/**
 * @author felipev, aleixo
 * 
 */
public class ItemEstruturaDao extends Dao {

	ItemEstruturaAcaoDao daoIettAcao;
	ItemEstrtIndResulIettrDao indResulIettrDao;
	ItemEstruturaUploadDao itemEstruturaUploadDao;
	ExercicioDao exercicioDao;
	AcompRealFisicoDao daoRealFisico = new AcompRealFisicoDao(null);
	String uuid;

	// private List<ItemEstruturaIett> lista = new
	// ArrayList<ItemEstruturaIett>();
	//
	// public void setLista(List<ItemEstruturaIett> lista) {
	// this.lista = lista;
	// }
	//
	// public List<ItemEstruturaIett> getLista() {
	// return lista;
	// }

	/**
	 * Construtor. Chama o Session factory do Hibernate
	 * 
	 * @param request
	 */
	public ItemEstruturaDao(HttpServletRequest request) {
		super();
		this.request = request;
		daoIettAcao = new ItemEstruturaAcaoDao(null);
		indResulIettrDao = new ItemEstrtIndResulIettrDao(null);
		exercicioDao = new ExercicioDao(null);
	}

	/**
	 * Construtor. Chama o Session factory do Hibernate
	 * 
	 * @param request
	 */
	public ItemEstruturaDao(HttpServletRequest request, String cfg) {
		super(cfg);

	}

	/**
	 * Construtor. Utiliza a Session criada para manter uma ï¿½nica
	 * transaï¿½ï¿½o
	 * 
	 * @param request
	 * @param session
	 */
	public ItemEstruturaDao(HttpServletRequest request, Session session) {
		super(session);
		if (request != null) {
			this.request = request;
		}
	}

	@SuppressWarnings("rawtypes")
	public Integer[] listaAcaoSimPR(Integer chvExternoMinimo, Integer chvExternoMaximo, EstruturaEtt... estruturas) {
		StringBuilder hql = new StringBuilder();

		hql.append("SELECT DISTINCT iett.codIett FROM ItemEstruturaIett iett ");
		hql.append("WHERE ");
		hql.append("iett.descricaoR1 <> '' ");
		hql.append("AND ");
		hql.append("iett.descricaoR2 <> '' ");
		hql.append("AND ");
		hql.append("iett.descricaoR5 <> 'EXCP' ");
		hql.append("AND ");
		hql.append("iett.estruturaEtt in (:ests) ");

		if (chvExternoMinimo > 0 && chvExternoMaximo > 0) {
			hql.append("AND ");
			hql.append("iett.codIett BETWEEN :chvMin AND :chvMax ");
		}

		// TODO Agrupar pela estratï¿½gia e ordenar od IDs
		// hql.append("ORDER BY iett.siglaIett ");

		Query q = getSession().createQuery(hql.toString());
		q.setParameterList("ests", estruturas);
		if (chvExternoMinimo > 0 && chvExternoMaximo > 0) {
			q.setParameter("chvMin", Long.parseLong(chvExternoMinimo.toString()));
			q.setParameter("chvMax", Long.parseLong(chvExternoMaximo.toString()));
		}
		List list = q.list();
		logger.debug("listaAcaoSimPR -> SQL: " + q.getQueryString());
		logger.debug("listaAcaoSimPR -> PARAM: " + estruturas[0] + " , " + estruturas[1]);

		Integer ids[] = new Integer[list.size()];
		Long id;
		for (int i = 0; i < list.size(); i++) {
			id = (Long) list.get(i);
			ids[i] = Integer.valueOf(id.toString());
		}
		return ids;
	}

	@SuppressWarnings("unchecked")
	public List<WsAcaoAtividadeCadastro> obtemAcaoTarefa(Long chvExterno, Map<String, Object> params, boolean isCodPai, boolean nIntermediario) {
		StringBuilder hqlTafCad = new StringBuilder();
		logger.debug("Inicio do obtemAcaoTarefa()...");
		hqlTafCad.append("SELECT NEW ecar.pojo.simpr.WsAcaoAtividadeCadastro(");
		hqlTafCad.append("ativSP.codIett,");// chvExterno
		hqlTafCad.append("tipoTaf.atribInfCompSatb,");// tipoTarefa - COD
		hqlTafCad.append("ativSP.descricaoR2,");// nomeTarefa
		hqlTafCad.append("unMedida.atribInfCompSatb,");// unidadeMedidaDuracaoCod
														// - COD
		hqlTafCad.append("resp.cnpjCpfUsu,");// responsavel - CPF
		hqlTafCad.append("resp.nomeUsu,");// responsavel - NOME
		hqlTafCad.append("ativSP.dataR1,");// dataHoraInicioPrevisto
		hqlTafCad.append("ativSP.dataR2,");// dataHoraInicioPrevisto
		hqlTafCad.append("ativSP.descricaoR1,");// Sigla
		hqlTafCad.append("ativSP.situacaoSit.codSit");// Situacao
		hqlTafCad.append(") ");
		hqlTafCad.append("FROM ItemEstruturaIett ativSP ");
		if (nIntermediario) {
			hqlTafCad.append("JOIN ativSP.itemEstruturaIett nIntermediario ");
			hqlTafCad.append("JOIN nIntermediario.itemEstruturaIett acaoSP ");
		} else {
			hqlTafCad.append("JOIN ativSP.itemEstruturaIett acaoSP ");
		}

		hqlTafCad.append("JOIN ativSP.itemEstUsutpfuacIettutfas resp1 ");
		hqlTafCad.append("JOIN resp1.usuarioUsu resp ");
		hqlTafCad.append("JOIN ativSP.itemEstruturaSisAtributoIettSatbs tipoTaf1 ");
		hqlTafCad.append("JOIN tipoTaf1.sisAtributoSatb tipoTaf ");
		hqlTafCad.append("JOIN ativSP.itemEstruturaSisAtributoIettSatbs unMedida1 ");
		hqlTafCad.append("JOIN unMedida1.sisAtributoSatb unMedida ");
		hqlTafCad.append("WHERE ");
		hqlTafCad.append("tipoTaf.sisGrupoAtributoSga.codSga = 37 ");
		hqlTafCad.append("AND unMedida.sisGrupoAtributoSga.codSga = 39 ");

		hqlTafCad.append("AND ");
		hqlTafCad.append("resp1.tipoFuncAcompTpfa.codTpfa in (3,6,7) ");

		hqlTafCad.append("AND ");
		if (isCodPai) {
			hqlTafCad.append("acaoSP.codIett = :chvEx ");
		} else {
			hqlTafCad.append("ativSP.codIett = :chvEx ");
		}

		hqlTafCad.append("ORDER BY ativSP.siglaIett ");

		Query q = getSession().createQuery(hqlTafCad.toString());

		q.setLong("chvEx", chvExterno);
		// q.setParameter("tpfa", params.get("tpfa"));
		return q.list();
	}

	public WsAcaoCadastro obtemAcao(Long chvExterno, Map<String, Object> params) {
		StringBuilder hqlAcaoCad = new StringBuilder();
		// WsAcaoCadastro
		hqlAcaoCad.append("SELECT ");
		hqlAcaoCad.append("NEW ecar.pojo.simpr.WsAcaoCadastro( ");
		hqlAcaoCad.append("iett.codIett,");// chvExterno
		hqlAcaoCad.append("proj.atribInfCompSatb,");// projeto - COD
		hqlAcaoCad.append("proj.descricaoSatb,");// projeto - DESC
		hqlAcaoCad.append("eixo.atribInfCompSatb,");// eixo - COD
		hqlAcaoCad.append("eixo.descricaoSatb,");// eixo - DESC
		hqlAcaoCad.append("iett.descricaoR2,");// nomeAcao
		hqlAcaoCad.append("'" + String.valueOf(params.get("cnpj")) + "', ");// orgaoResponsavel
																			// -
																			// CNPJ
		hqlAcaoCad.append("'" + String.valueOf(params.get("nomeEmpresa")) + "', ");// orgaoResponsavel
																					// -
																					// NomeEmpresa
		hqlAcaoCad.append("iett.dataR1,");// dataInicioAcao
		hqlAcaoCad.append("iett.dataR2,");// dataTerminoAcao
		hqlAcaoCad.append("iett.situacaoSit.codSit,");// situacaoAcao - Cod
		hqlAcaoCad.append("iett.situacaoSit.descricaoSit,");// situacaoAcao -
															// Cod
		hqlAcaoCad.append("resp.cnpjCpfUsu,");// gerenteAcao - numeroCpf
		// hqlAcaoCad.append("respAcao.usuarioUsu.nomeUsu,");//gerenteAcao -
		// numeroCpf
		hqlAcaoCad.append("resp.nomeUsu, ");// gerenteAcao - nome
		hqlAcaoCad.append("iett.indAtivoIett ");// Ativo
		hqlAcaoCad.append(") ");
		hqlAcaoCad.append("FROM ");
		hqlAcaoCad.append("ItemEstruturaIett iett ");
		hqlAcaoCad.append("JOIN iett.itemEstUsutpfuacIettutfas respAcao ");
		hqlAcaoCad.append("JOIN respAcao.usuarioUsu resp ");
		hqlAcaoCad.append("JOIN iett.itemEstruturaSisAtributoIettSatbs proj1 ");
		hqlAcaoCad.append("JOIN proj1.sisAtributoSatb proj ");
		hqlAcaoCad.append("JOIN iett.itemEstruturaSisAtributoIettSatbs eixo1 ");
		hqlAcaoCad.append("JOIN eixo1.sisAtributoSatb eixo ");
		hqlAcaoCad.append("WHERE ");
		hqlAcaoCad.append("proj.sisGrupoAtributoSga.codSga = 34 ");
		hqlAcaoCad.append("AND ");
		hqlAcaoCad.append("eixo.sisGrupoAtributoSga.codSga = 36 ");
		hqlAcaoCad.append("AND ");
		hqlAcaoCad.append("iett.codIett = :chvEx ");
		hqlAcaoCad.append("AND ");
		hqlAcaoCad.append("respAcao.tipoFuncAcompTpfa in (3,6) ");

		Query q = getSession().createQuery(hqlAcaoCad.toString());

		q.setLong("chvEx", chvExterno);
		// q.setParameter("tpfa", params.get("tpfa"));

		return (WsAcaoCadastro) q.uniqueResult();
	}

	/**
	 * Retorna o valor de um atributo em um itemEstrutura
	 * 
	 * @param itemEstrutura
	 * @param nomeAtributo
	 * @param fkAtributo
	 * @return
	 * @throws ECARException
	 */
	public String getValorAtributoItemEstrutura(ItemEstruturaIett itemEstrutura, String nomeAtributo, String fkAtributo) throws ECARException {
		try {
			Object retorno = Util.invocaGet(itemEstrutura, nomeAtributo);
			if (retorno != null) {
				if (fkAtributo != null && !"".equals(fkAtributo)) {
					retorno = Util.invocaGet(retorno, fkAtributo);
					if (retorno != null)
						return retorno.toString();
					else
						return "";
				} else {
					if (retorno.getClass().equals(Timestamp.class) || retorno.getClass().equals(Date.class))
						retorno = Data.parseDate((Date) retorno);
					return retorno.toString();
				}
			}
		} catch (Exception e) {

		}
		return "";
	}

	/**
	 * Retorna o valor de um atributo em um historicoItemEstruturaIett
	 * 
	 * @param historicoItemEstruturaIett
	 * @param nomeAtributo
	 * @param fkAtributo
	 * @return
	 * @throws ECARException
	 */
	public String getValorAtributoItemEstrutura(HistoricoItemEstruturaIett historicoItemEstruturaIett, String nomeAtributo, String fkAtributo) throws ECARException {
		try {
			Object retorno = Util.invocaGet(historicoItemEstruturaIett, nomeAtributo);
			if (retorno != null) {
				if (fkAtributo != null && !"".equals(fkAtributo)) {
					retorno = Util.invocaGet(retorno, fkAtributo);
					if (retorno != null)
						return retorno.toString();
					else
						return "";
				} else {
					if (retorno.getClass().equals(Timestamp.class) || retorno.getClass().equals(Date.class))
						retorno = Data.parseDate((Date) retorno);
					return retorno.toString();
				}
			}
		} catch (Exception e) {

		}
		return "";
	}

	/**
	 * Retorna o usuï¿½rio associoado a uma funï¿½ï¿½o de acompanhamento em um
	 * itemEstrutura
	 * 
	 * @param itemEstrutura
	 * @param funAcomp
	 * @return
	 * @throws ECARException
	 */
	public UsuarioUsu getValorFunAcompItemEstrutura(ItemEstruturaIett itemEstrutura, TipoFuncAcompTpfa funAcomp) throws ECARException {
		ItemEstUsutpfuacIettutfa ieUsuTf = new ItemEstUsutpfuacIettutfa();
		ieUsuTf.setItemEstruturaIett(itemEstrutura);
		ieUsuTf.setTipoFuncAcompTpfa(funAcomp);
		try {
			List result = this.pesquisar(ieUsuTf, null);
			if (result != null) {
				for (Iterator it = result.iterator(); it.hasNext();) {
					ItemEstUsutpfuacIettutfa element = (ItemEstUsutpfuacIettutfa) it.next();
					return element.getUsuarioUsu();
				}
				return null;
			} else {
				return null;
			}
		} catch (ECARException e) {
			this.logger.error(e);
			return null;
		}

	}

	/**
	 * Retorna o usuï¿½rio associoado a uma funï¿½ï¿½o de acompanhamento em um
	 * historicoItemEstruturaIett
	 * 
	 * @param historicoItemEstruturaIett
	 * @param funAcomp
	 * @return
	 * @throws ECARException
	 */
	@SuppressWarnings("empty-statement")
	public UsuarioUsu getValorFunAcompItemEstrutura(HistoricoItemEstruturaIett historicoItemEstruturaIett, TipoFuncAcompTpfa funAcomp) throws ECARException {
		try {
			Set result = historicoItemEstruturaIett.getItemEstUsutpfuacIettutfas();
			;
			if (result != null) {
				for (Iterator it = result.iterator(); it.hasNext();) {
					ItemEstUsutpfuacIettutfa element = (ItemEstUsutpfuacIettutfa) it.next();
					if (element.getTipoFuncAcompTpfa().equals(funAcomp)) {
						return element.getUsuarioUsu();
					}
				}
				return null;
			} else {
				return null;
			}
		} catch (Exception e) {
			this.logger.error(e);
			return null;
		}

	}

	/**
	 * Retorna o SisAtributoSatb associado a uma funï¿½ï¿½o de acompanhamento em
	 * um itemEstrutura
	 * 
	 * @param itemEstrutura
	 * @param funAcomp
	 * @return
	 * @throws ECARException
	 */
	public SisAtributoSatb getValorSatbFunAcompItemEstrutura(ItemEstruturaIett itemEstrutura, TipoFuncAcompTpfa funAcomp) throws ECARException {
		ItemEstUsutpfuacIettutfa ieUsuTf = new ItemEstUsutpfuacIettutfa();
		ieUsuTf.setItemEstruturaIett(itemEstrutura);
		ieUsuTf.setTipoFuncAcompTpfa(funAcomp);
		try {
			List result = this.pesquisar(ieUsuTf, null);
			if (result != null) {
				for (Iterator it = result.iterator(); it.hasNext();) {
					ItemEstUsutpfuacIettutfa element = (ItemEstUsutpfuacIettutfa) it.next();
					return element.getSisAtributoSatb();
				}
				return null;
			} else {
				return null;
			}
		} catch (ECARException e) {
			this.logger.error(e);
			return null;
		}

	}

	/**
	 * Retorna o SisAtributoSatb associado a uma funï¿½ï¿½o de acompanhamento em
	 * um historicoItemEstruturaIett
	 * 
	 * @param historicoItemEstruturaIett
	 * @param funAcomp
	 * @return
	 * @throws ECARException
	 */
	public SisAtributoSatb getValorSatbFunAcompItemEstrutura(HistoricoItemEstruturaIett historicoItemEstruturaIett, TipoFuncAcompTpfa funAcomp) throws ECARException {

		try {
			Set result = historicoItemEstruturaIett.getItemEstUsutpfuacIettutfas();
			if (result != null) {
				for (Iterator it = result.iterator(); it.hasNext();) {
					ItemEstUsutpfuacIettutfa element = (ItemEstUsutpfuacIettutfa) it.next();
					if (element.getTipoFuncAcompTpfa().equals(funAcomp)) {
						return element.getSisAtributoSatb();
					}
				}
				return null;
			} else {
				return null;
			}
		} catch (Exception e) {
			this.logger.error(e);
			return null;
		}

	}

	/**
	 * Cria um objeto ItemEstruturaIett a partir de parï¿½metros passados no
	 * objeto request
	 * 
	 * @param request
	 * @param itemEstrutura
	 * @throws ECARException
	 */
	public void setItemEstrutura(HttpServletRequest request, ItemEstruturaIett itemEstrutura) throws ECARException {
		// Verifica se o planejamento estï¿½ bloqueado
		Boolean planejamentoBloqueado = false;
		if (itemEstrutura.getIndBloqPlanejamentoIett() != null && itemEstrutura.getIndBloqPlanejamentoIett().equals("S")) {
			planejamentoBloqueado = true;
		}

		// SegurancaECAR seg =
		// (SegurancaECAR)request.getSession().getAttribute("seguranca");
		// List funcoesDoUsuario = (new
		// TipoFuncAcompDao(null)).getFuncoesAcompNaEstruturaDoUsuario(itemEstrutura
		// , seg.getUsuario(), seg.getGruposAcesso());

		// Define os atributos que sï¿½o independentes de o planejamento estar
		// bloqueado
		itemEstrutura.setDataUltManutencaoIett(Data.getDataAtual());
		if (!"".equals(Pagina.getParamStr(request, "codIettPai"))) {
			itemEstrutura.setItemEstruturaIett((ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIettPai"))));
		}
		if (!"".equals(Pagina.getParamStr(request, "codIett"))) {
			itemEstrutura.setCodIett(Long.valueOf(Pagina.getParamStr(request, "codIett")));
		}

		// TODO Para ser Testado
		if (Pagina.getParamStr(request, "codEtt") != null && !Pagina.getParamStr(request, "codEtt").equals(Dominios.STRING_VAZIA)) {
			itemEstrutura.setEstruturaEtt((EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(Pagina.getParamStr(request, "codEtt"))));
		} else {
			itemEstrutura.setEstruturaEtt((EstruturaEtt) new EstruturaDao(request).buscar(EstruturaEtt.class, Long.valueOf(Pagina.getParamStr(request, "itemEstrutura"))));
		}

		itemEstrutura.setIndAtivoIett(Pagina.SIM);
		// hint de desempenho. Jï¿½ diz a qual nivel o item pertence.
		itemEstrutura.setNivelIett(Integer.valueOf(getNivel(itemEstrutura)));

		SegurancaECAR seg = (SegurancaECAR) request.getSession().getAttribute("seguranca");
		List funcoesDoUsuario = (new TipoFuncAcompDao(null)).getFuncoesAcompNaEstruturaDoUsuario(itemEstrutura, seg.getUsuario(), seg.getGruposAcesso());

		// Busca a lista de atributos definidos para estrutura. Estï¿½ lista ï¿½
		// necessï¿½ria para saber
		// se, no caso do planejamento estar bloqueado, o atributo pode ou nï¿½o
		// ser alterado.
		// No caso de o planejamento nï¿½o estar bloqueado ou ser a inserï¿½ï¿½o
		// de um novo item,
		// nï¿½o faz diferenï¿½a.
		List atributos = new ArrayList();
		EstruturaDao estruturaDao = new EstruturaDao(request);
		EstruturaEtt estrutura = itemEstrutura.getEstruturaEtt();
		atributos = estruturaDao.getAtributosEstruturaDadosGerais(estrutura);
		// Realiza iteraï¿½ï¿½o entre todos os atributos configurados na
		// estrutura.
		Iterator it = atributos.iterator();
		while (it.hasNext()) {
			ObjetoEstrutura element = (ObjetoEstrutura) it.next();

			// Verifica se o atritubo ï¿½ "siglaIett".
			if (element.iGetNome().equals("siglaIett")) {
				// Verifica se o planejamento nï¿½o estï¿½ bloqueado ou se, caso
				// bloqueado, o campo nï¿½o estï¿½
				// bloqueado. Satisfeito estas condiï¿½ï¿½es, o atributo pode
				// ser definido.
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if ("".equals(Pagina.getParamStr(request, "siglaIett"))) {
						itemEstrutura.setSiglaIett(null);
					} else {
						itemEstrutura.setSiglaIett(Pagina.getParamStr(request, "siglaIett"));
					}
				} else {

				}
			} else if (element.iGetNome().equals("nomeIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					itemEstrutura.setNomeIett(Pagina.getParamStr(request, "nomeIett"));
				}
			} else if (element.iGetNome().equals("descricaoIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					itemEstrutura.setDescricaoIett(Pagina.getParamStr(request, "descricaoIett"));
				}
			} else if (element.iGetNome().equals("origemIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					itemEstrutura.setOrigemIett(Pagina.getParamStr(request, "origemIett"));
				}
			} else if (element.iGetNome().equals("indAtivoIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					String indAtivo = Pagina.getParamStr(request, "indAtivoIett");
					if ("".equals(indAtivo.trim())) {
						indAtivo = "S";
					}
					itemEstrutura.setIndAtivoIett(indAtivo);
				}
			} else if (element.iGetNome().equals("indMonitoramentoIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					String indMonitoramento = Pagina.getParamStr(request, "indMonitoramentoIett");
					if ("".equals(indMonitoramento.trim())) {
						indMonitoramento = "N";
					}
					itemEstrutura.setIndMonitoramentoIett(indMonitoramento);
				}
			} else if (element.iGetNome().equals("indBloqPlanejamentoIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					String indBloqPlanejamento = Pagina.getParamStr(request, "indBloqPlanejamentoIett");
					if ("".equals(indBloqPlanejamento.trim())) {
						indBloqPlanejamento = "N";
					}
					itemEstrutura.setIndBloqPlanejamentoIett(indBloqPlanejamento);
				}
			} else if (element.iGetNome().equals("objetivoGeralIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					itemEstrutura.setObjetivoGeralIett(Pagina.getParamStr(request, "objetivoGeralIett"));
				}
			} else if (element.iGetNome().equals("objetivoEspecificoIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					itemEstrutura.setObjetivoEspecificoIett(Pagina.getParamStr(request, "objetivoEspecificoIett"));
				}
			} else if (element.iGetNome().equals("beneficiosIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					itemEstrutura.setBeneficiosIett(Pagina.getParamStr(request, "beneficiosIett"));
				}
			} else if (element.iGetNome().equals("dataInicioIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "dataInicioIett"))) {
						itemEstrutura.setDataInicioIett(Pagina.getParamDataBanco(request, "dataInicioIett"));
					} else {
						itemEstrutura.setDataInicioIett(null);
					}
				}
			} else if (element.iGetNome().equals("dataTerminoIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "dataTerminoIett"))) {
						itemEstrutura.setDataTerminoIett(Pagina.getParamDataBanco(request, "dataTerminoIett"));
					} else {
						itemEstrutura.setDataTerminoIett(null);
					}
				}
			} else if (element.iGetNome().equals("areaAre")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "areaAre"))) {
						itemEstrutura.setAreaAre((AreaAre) new AreaDao(request).buscar(AreaAre.class, Long.valueOf(Pagina.getParamStr(request, "areaAre"))));
					} else {
						itemEstrutura.setAreaAre(null);
					}
				}
			} else if (element.iGetNome().equals("subAreaSare")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "subAreaSare"))) {
						itemEstrutura.setSubAreaSare((SubAreaSare) new SubAreaDao(request).buscar(SubAreaSare.class, Long.valueOf(Pagina.getParamStr(request, "subAreaSare"))));
					} else {
						itemEstrutura.setSubAreaSare(null);
					}
				}
			} else if (element.iGetNome().equals("unidadeOrcamentariaUO")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "unidadeOrcamentariaUO"))) {
						itemEstrutura.setUnidadeOrcamentariaUO((UnidadeOrcamentariaUO) new UnidadeOrcamentariaDao(request).buscar(UnidadeOrcamentariaUO.class,
								Long.valueOf(Pagina.getParamStr(request, "unidadeOrcamentariaUO"))));
					} else {
						itemEstrutura.setUnidadeOrcamentariaUO(null);
					}
				}
			} else if (element.iGetNome().equals("orgaoOrgByCodOrgaoResponsavel1Iett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "orgaoOrgByCodOrgaoResponsavel1Iett"))) {
						itemEstrutura.setOrgaoOrgByCodOrgaoResponsavel1Iett((OrgaoOrg) new OrgaoDao(request).buscar(OrgaoOrg.class,
								Long.valueOf(Pagina.getParamStr(request, "orgaoOrgByCodOrgaoResponsavel1Iett"))));
					} else {
						itemEstrutura.setOrgaoOrgByCodOrgaoResponsavel1Iett(null);
					}
				}
			} else if (element.iGetNome().equals("orgaoOrgByCodOrgaoResponsavel2Iett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "orgaoOrgByCodOrgaoResponsavel2Iett"))) {
						itemEstrutura.setOrgaoOrgByCodOrgaoResponsavel2Iett((OrgaoOrg) new OrgaoDao(request).buscar(OrgaoOrg.class,
								Long.valueOf(Pagina.getParamStr(request, "orgaoOrgByCodOrgaoResponsavel2Iett"))));
					} else {
						itemEstrutura.setOrgaoOrgByCodOrgaoResponsavel2Iett(null);
					}
				}
			} else if (element.iGetNome().equals("periodicidadePrdc")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "periodicidadePrdc"))) {
						itemEstrutura.setPeriodicidadePrdc((PeriodicidadePrdc) new PeriodicidadeDao(request).buscar(PeriodicidadePrdc.class,
								Long.valueOf(Pagina.getParamStr(request, "periodicidadePrdc"))));
					} else {
						itemEstrutura.setPeriodicidadePrdc(null);
					}
				}
			} else if (element.iGetNome().equals("indCriticaIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					String indCritica = Pagina.getParamStr(request, "indCriticaIett");
					if (Dominios.STRING_VAZIA.equals(indCritica.trim())) {
						itemEstrutura.setIndCriticaIett(null);
					} else {
						itemEstrutura.setIndCriticaIett(indCritica);
					}
				}
			} else if (element.iGetNome().equals("indModeloIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					String indModelo = Pagina.getParamStr(request, "indModeloIett");
					if (Dominios.STRING_VAZIA.equals(indModelo.trim())) {
						itemEstrutura.setIndModeloIett(null);
					} else {
						itemEstrutura.setIndModeloIett(indModelo);
					}
				}
			} else if (element.iGetNome().equals("valPrevistoFuturoIett")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "valPrevistoFuturoIett"))) {
						itemEstrutura.setValPrevistoFuturoIett(new BigDecimal(Double.valueOf(Util.formataNumero(Pagina.getParamStr(request, "valPrevistoFuturoIett"))).doubleValue()));
					} else {
						itemEstrutura.setValPrevistoFuturoIett(null);
					}
				}
			} else if (element.iGetNome().equals("dataR1")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "dataR1"))) {
						itemEstrutura.setDataR1(Pagina.getParamDataBanco(request, "dataR1"));
					} else {
						itemEstrutura.setDataR1(null);
					}
				}
			} else if (element.iGetNome().equals("dataR2")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "dataR2"))) {
						itemEstrutura.setDataR2(Pagina.getParamDataBanco(request, "dataR2"));
					} else {
						itemEstrutura.setDataR2(null);
					}
				}
			} else if (element.iGetNome().equals("dataR3")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "dataR3"))) {
						itemEstrutura.setDataR3(Pagina.getParamDataBanco(request, "dataR3"));
					} else {
						itemEstrutura.setDataR3(null);
					}
				}
			} else if (element.iGetNome().equals("dataR4")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "dataR4"))) {
						itemEstrutura.setDataR4(Pagina.getParamDataBanco(request, "dataR4"));
					} else {
						itemEstrutura.setDataR4(null);
					}
				}
			} else if (element.iGetNome().equals("dataR5")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "dataR5"))) {
						itemEstrutura.setDataR5(Pagina.getParamDataBanco(request, "dataR5"));
					} else {
						itemEstrutura.setDataR5(null);
					}
				}
			} else if (element.iGetNome().equals("descricaoR1")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					itemEstrutura.setDescricaoR1(Pagina.getParamStr(request, "descricaoR1"));
				}
			} else if (element.iGetNome().equals("descricaoR2")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					itemEstrutura.setDescricaoR2(Pagina.getParamStr(request, "descricaoR2"));
				}
			} else if (element.iGetNome().equals("descricaoR3")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					itemEstrutura.setDescricaoR3(Pagina.getParamStr(request, "descricaoR3"));
				}
			} else if (element.iGetNome().equals("descricaoR4")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					itemEstrutura.setDescricaoR4(Pagina.getParamStr(request, "descricaoR4"));
				}
			} else if (element.iGetNome().equals("descricaoR5")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					itemEstrutura.setDescricaoR5(Pagina.getParamStr(request, "descricaoR5"));
				}
			} else if (element.iGetNome().equals("situacaoSit")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "situacaoSit"))) {
						String codSit = Pagina.getParamStr(request, "situacaoSit");
						itemEstrutura.setSituacaoSit((SituacaoSit) new SituacaoDao(null).buscar(SituacaoSit.class, Long.valueOf(codSit)));
					} else {
						itemEstrutura.setSituacaoSit(null);
					}
				}
			} else if (element.iGetNome().equals("nivelPlanejamento")) {
				if (!planejamentoBloqueado || (planejamentoBloqueado && !element.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, element, funcoesDoUsuario)) {
					itemEstrutura.setItemEstruturaNivelIettns(new HashSet());
					ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
					String campoNivelPlanejamento = "a" + configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan().getCodSga().toString();
					if (request.getParameterValues(campoNivelPlanejamento) != null) {
						String[] atributosLivres = request.getParameterValues(campoNivelPlanejamento);
						for (int i = 0; i < atributosLivres.length; i++) {
							if (!atributosLivres[i].equals("")) {
								itemEstrutura.getItemEstruturaNivelIettns().add(this.buscar(SisAtributoSatb.class, Long.valueOf(atributosLivres[i])));
							}
						}
					}
				}
			}
		}
		// Define as Funï¿½ï¿½es de acompanhamentos.
		setFuncoesAcompanhamentoItemEstrutura(request, itemEstrutura);
	}

	/**
	 * Devolve um int indicando em qual nï¿½vel da hierarquia de itens o Item se
	 * encontra
	 * 
	 * @param itemEstrutura
	 * @return
	 * @throws ECARException
	 */
	private int getNivel(ItemEstruturaIett itemEstrutura) throws ECARException {
		int nivel = 1;

		while (itemEstrutura.getItemEstruturaIett() != null) {
			itemEstrutura = itemEstrutura.getItemEstruturaIett();
			nivel++;

		}

		return nivel;
	}

	/**
	 * Seta os atributos livres vindos do request para um itemEstrutura
	 * 
	 * @author aleixo
	 * @since 28/05/2007
	 * @param request
	 * @param itemEstrutura
	 * @throws ECARException
	 */
	private List getAtributosLivresItemEstrutura(HttpServletRequest request, ItemEstruturaIett itemEstrutura) throws ECARException {
		Boolean planejamentoBloqueado = false;
		if (itemEstrutura.getIndBloqPlanejamentoIett() != null && itemEstrutura.getIndBloqPlanejamentoIett().equals("S")) {
			planejamentoBloqueado = true;
		}

		SegurancaECAR seg = (SegurancaECAR) request.getSession().getAttribute("seguranca");
		List funcoesDoUsuario = (new TipoFuncAcompDao(null)).getFuncoesAcompNaEstruturaDoUsuario(itemEstrutura, seg.getUsuario(), seg.getGruposAcesso());

		/*
		 * Obter o cï¿½digo do grupo do atributo livre para pegar pelo campo "a"
		 * + codSga;
		 */
		FuncaoDao funcaoDao = new FuncaoDao(request);
		FuncaoFun funcao = funcaoDao.getFuncaoDadosGerais();

		List sgas = new ArrayList();
		if (itemEstrutura.getEstruturaEtt() != null && itemEstrutura.getEstruturaEtt().getEstruturaAtributoEttats() != null && !itemEstrutura.getEstruturaEtt().getEstruturaAtributoEttats().isEmpty()) {
			for (Iterator it = itemEstrutura.getEstruturaEtt().getEstruturaAtributoEttats().iterator(); it.hasNext();) {
				EstruturaAtributoEttat ettat = (EstruturaAtributoEttat) it.next();
				if (ettat.getAtributosAtb() != null && ettat.getAtributosAtb().getSisGrupoAtributoSga() != null) {
					if ((ettat.getAtributosAtb().getFuncaoFun().equals(funcao))
							&& (!planejamentoBloqueado || (planejamentoBloqueado && !ettat.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, ettat, funcoesDoUsuario))) {

						sgas.add(ettat.getAtributosAtb().getSisGrupoAtributoSga());
					}
				}
			}
		}

		List atributosLivres = new ArrayList();
		// Percorrer grupo de atributos para selecionar os atributos livres...
		for (Iterator it = sgas.iterator(); it.hasNext();) {
			SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) it.next();

			String nomeCampo = "a" + grupoAtributo.getCodSga().toString();
			String tipoCampo = grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString();

			// Se for CheckBox ou RadioButton ou ListBox, nï¿½o gravar
			// InformacaoIettSatb
			// Alterado por Josï¿½ Andrï¿½ Fernandes, foi acrescentado ListBox
			if (tipoCampo.equals(SisTipoExibicGrupoDao.CHECKBOX) || tipoCampo.equals(SisTipoExibicGrupoDao.LISTBOX)) {
				String[] atributos = request.getParameterValues(nomeCampo);
				int numAtributos = 0;
				if (atributos != null) {
					numAtributos = atributos.length;
				}
				for (int i = 0; i < numAtributos; i++) {
					// Nï¿½o seto todos os outros campos, pois eles serï¿½o
					// setados depois de gravar o item.
					ItemEstruturaSisAtributoIettSatb atributoLivre = new ItemEstruturaSisAtributoIettSatb();
					atributoLivre.setDataUltManutencao(Data.getDataAtual());
					atributoLivre.setUsuarioUsu(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
					atributoLivre.setItemEstruturaIett(itemEstrutura);
					atributoLivre.setSisAtributoSatb((SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(atributos[i])));
					atributoLivre.setFuncao(funcao);
					atributoLivre.atribuirPKPai();

					atributosLivres.add(atributoLivre);
				}
			}
			// Se for Radio Button...
			else if (tipoCampo.equals(SisTipoExibicGrupoDao.RADIO_BUTTON) || tipoCampo.equals(SisTipoExibicGrupoDao.COMBOBOX)) {

				if (!"".equals(Pagina.getParamStr(request, nomeCampo))) {
					// Nï¿½o seto todos os outros campos, pois eles serï¿½o
					// setados depois de gravar o item.
					ItemEstruturaSisAtributoIettSatb atributoLivre = new ItemEstruturaSisAtributoIettSatb();
					atributoLivre.setDataUltManutencao(Data.getDataAtual());
					atributoLivre.setUsuarioUsu(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
					atributoLivre.setItemEstruturaIett(itemEstrutura);
					atributoLivre.setSisAtributoSatb((SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(Pagina.getParamStr(request, nomeCampo))));
					atributoLivre.setFuncao(funcao);
					atributoLivre.atribuirPKPai();

					atributosLivres.add(atributoLivre);
				}
			}
			// Se for TEXT Field
			else if (tipoCampo.equals(SisTipoExibicGrupoDao.TEXT) || tipoCampo.equals(SisTipoExibicGrupoDao.TEXTAREA)) {

				// System.out.println(Pagina.getParamStr(request,
				// atributosLivres));
				if (!"".equals(Pagina.getParamStr(request, nomeCampo))) {

					// Nï¿½o seto todos os outros campos, pois eles serï¿½o
					// setados depois de gravar o item.
					ItemEstruturaSisAtributoIettSatb atributoLivre = new ItemEstruturaSisAtributoIettSatb();
					atributoLivre.setDataUltManutencao(Data.getDataAtual());
					atributoLivre.setUsuarioUsu(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
					atributoLivre.setItemEstruturaIett(itemEstrutura);
					atributoLivre.setSisAtributoSatb((SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next());
					atributoLivre.setInformacao(Pagina.getParamStr(request, nomeCampo));
					atributoLivre.setFuncao(funcao);
					atributoLivre.atribuirPKPai();

					atributosLivres.add(atributoLivre);
				}

			} else if (tipoCampo.equals(SisTipoExibicGrupoDao.IMAGEM)) {
				if (!"".equals(Pagina.getParamStr(request, nomeCampo))) {

					String pathRaiz = request.getContextPath();

					// Nï¿½o seto todos os outros campos, pois eles serï¿½o
					// setados depois de gravar o item.
					ItemEstruturaSisAtributoIettSatb atributoLivre = new ItemEstruturaSisAtributoIettSatb();
					atributoLivre.setDataUltManutencao(Data.getDataAtual());
					atributoLivre.setUsuarioUsu(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
					atributoLivre.setItemEstruturaIett(itemEstrutura);
					atributoLivre.setSisAtributoSatb((SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next());
					atributoLivre.setInformacao(Pagina.getParamStr(request, nomeCampo));
					atributoLivre.setFuncao(funcao);
					atributoLivre.atribuirPKPai();

					// tratamento imagem
					String caminhoAuxiliarImagem = Pagina.getParamStr(request, "hidImagem" + "a" + grupoAtributo.getCodSga().toString());
					if (caminhoAuxiliarImagem != null && caminhoAuxiliarImagem.length() > 0) {

						String chave = atributoLivre.getInformacao();
						chave = chave.substring(chave.indexOf("RemoteFile=") + "RemoteFile=".length());
						UsuarioUsu usuario = ((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario();
						if (usuario.getMapArquivosAtuaisUsuarios() != null && usuario.getMapArquivosAtuaisUsuarios().containsKey(chave)) {
							// atributoLivre.setInformacao(usuario.getMapArquivosAtuaisUsuarios().get(chave));

							caminhoAuxiliarImagem = usuario.getMapArquivosAtuaisUsuarios().get(chave);
							caminhoAuxiliarImagem = pathRaiz + "/DownloadFile?RemoteFile=" + caminhoAuxiliarImagem;

						}
						// else{

						// salvar a imagem fisicamente que tem o caminho real em
						// "hidImagem"
						try {
							String nomeArquivoNovo = FileUpload.salvarArquivoSessaoFisicamente(request, "a" + grupoAtributo.getCodSga().toString(), caminhoAuxiliarImagem);
							if (nomeArquivoNovo != null && !nomeArquivoNovo.equals(""))
								atributoLivre.setInformacao(nomeArquivoNovo);
						} catch (FileNotFoundException e) {
							throw new ECARException("erro.arquivoUrl", e, new String[] { caminhoAuxiliarImagem });
						} catch (Exception e) {
							throw new ECARException("erro.upload", e, new String[] { caminhoAuxiliarImagem });
						}
						// }
					}

					atributosLivres.add(atributoLivre);
				}
			} else if (tipoCampo.equals(SisTipoExibicGrupoDao.MULTITEXTO)) {
				Enumeration lAtrib = request.getParameterNames();
				while (lAtrib.hasMoreElements()) {
					String atrib = (String) lAtrib.nextElement();
					if (atrib.lastIndexOf('_') > 0) {
						// System.out.println("nomeatributo" + atrib);
						String nomeAtrib = atrib.substring(0, atrib.lastIndexOf('_'));
						String codSisAtrib = atrib.substring(atrib.lastIndexOf('_') + 1);
						nomeCampo = "a" + grupoAtributo.getCodSga().toString() + "_" + codSisAtrib;

						if (nomeAtrib.equals("a" + grupoAtributo.getCodSga().toString()) && !"".equals(Pagina.getParamStr(request, atrib))) {
							ItemEstruturaSisAtributoIettSatb atributoLivre = new ItemEstruturaSisAtributoIettSatb();
							atributoLivre.setDataUltManutencao(Data.getDataAtual());
							atributoLivre.setUsuarioUsu(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
							atributoLivre.setItemEstruturaIett(itemEstrutura);
							atributoLivre.setSisAtributoSatb((SisAtributoSatb) this.buscar(SisAtributoSatb.class, Long.valueOf(codSisAtrib)));
							atributoLivre.setInformacao(Pagina.getParamStr(request, nomeCampo));
							atributoLivre.setFuncao(funcao);
							atributoLivre.atribuirPKPai();
							atributosLivres.add(atributoLivre);
						}
					}
				}

			} else if (tipoCampo.equals(SisTipoExibicGrupoDao.VALIDACAO)) {

				SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next();

				// Eh verdadeiro se o conteudo do atributo livre for diferente
				// de branco ou se o tipo de validaï¿½ï¿½o for igual as
				// descritas abaixo.
				if (!(Pagina.getParamStr(request, nomeCampo).equals(Dominios.STRING_VAZIA)) || (sisAtributoSatb.isAtributoAutoIcremental()) || (sisAtributoSatb.isAtributoContemMascara())) {

					// Nï¿½o seto todos os outros campos, pois eles serï¿½o
					// setados depois de gravar o item.
					ItemEstruturaSisAtributoIettSatb atributoLivre = new ItemEstruturaSisAtributoIettSatb();
					atributoLivre.setDataUltManutencao(Data.getDataAtual());
					atributoLivre.setUsuarioUsu(((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario());
					atributoLivre.setItemEstruturaIett(itemEstrutura);
					atributoLivre.setSisAtributoSatb((SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next());
					atributoLivre.setInformacao(Pagina.getParamStr(request, nomeCampo));
					atributoLivre.setFuncao(funcao);
					atributoLivre.atribuirPKPai();

					atributosLivres.add(atributoLivre);
				}

			}

		}

		return atributosLivres;
	}

	/**
	 * Adiciona elementos ï¿½ coleï¿½ï¿½o de Funï¿½ï¿½es de Acompanhamento de um
	 * ItemEstrutura
	 * 
	 * @param request
	 * @param itemEstrutura
	 * @throws ECARException
	 */
	public void setFuncoesAcompanhamentoItemEstrutura(HttpServletRequest request, ItemEstruturaIett itemEstrutura) throws ECARException {
		// Verifica se o planejamento estï¿½ bloqueado
		Boolean planejamentoBloqueado = false;
		if (itemEstrutura.getIndBloqPlanejamentoIett() != null && itemEstrutura.getIndBloqPlanejamentoIett().equals("S")) {
			planejamentoBloqueado = true;
		}

		/* obtem as funcoes de acompanhamento exercidas pelo usuario */
		SegurancaECAR seg = (SegurancaECAR) request.getSession().getAttribute("seguranca");
		List funcoesDoUsuario = (new TipoFuncAcompDao(null)).getFuncoesAcompNaEstruturaDoUsuario(itemEstrutura, seg.getUsuario(), seg.getGruposAcesso());

		// Limpa a collection
		itemEstrutura.setItemEstUsutpfuacIettutfas(new HashSet());
		// Descobre a Estrutura do item
		EstruturaEtt estrutura = itemEstrutura.getEstruturaEtt();
		// Descobre as funï¿½ï¿½es de acompanhamento de uma estrutura
		Set funcoesAcomp = estrutura.getEstrutTpFuncAcmpEtttfas();

		// Itera pelas funï¿½ï¿½es buscando no request o valor passado.
		if (funcoesAcomp != null) {

			for (Iterator it = funcoesAcomp.iterator(); it.hasNext();) {
				EstrutTpFuncAcmpEtttfa funcao = (EstrutTpFuncAcmpEtttfa) it.next();
				if (!planejamentoBloqueado || (planejamentoBloqueado && !funcao.iGetBloqueado()) || podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, funcao, funcoesDoUsuario)) {
					if (!"".equals(Pagina.getParamStr(request, "Fun" + funcao.getTipoFuncAcompTpfa().getCodTpfa()))) {
						ItemEstUsutpfuacIettutfa funcaoItemEstrutura = new ItemEstUsutpfuacIettutfa();
						funcaoItemEstrutura.setItemEstruturaIett(itemEstrutura);
						funcaoItemEstrutura.setTipoFuncAcompTpfa(funcao.getTipoFuncAcompTpfa());
						if (Pagina.getParamStr(request, "Fun" + funcao.getTipoFuncAcompTpfa().getCodTpfa()).startsWith("U")) {
							funcaoItemEstrutura.setUsuarioUsu((UsuarioUsu) this.buscar(UsuarioUsu.class,
									Long.valueOf(Pagina.getParamStr(request, "Fun" + funcao.getTipoFuncAcompTpfa().getCodTpfa()).substring(1))));
						} else if (Pagina.getParamStr(request, "Fun" + funcao.getTipoFuncAcompTpfa().getCodTpfa()).startsWith("G")) {
							funcaoItemEstrutura.setSisAtributoSatb((SisAtributoSatb) this.buscar(SisAtributoSatb.class,
									Long.valueOf(Pagina.getParamStr(request, "Fun" + funcao.getTipoFuncAcompTpfa().getCodTpfa()).substring(1))));
						}
						itemEstrutura.getItemEstUsutpfuacIettutfas().add(funcaoItemEstrutura);
					}
				}
			}
		}
	}

	/**
	 * Salva um registro de itemEstrutura. Salva ï¿½ parte os
	 * item-estrutura-funcao-tipo-acomp devido a chave composta. (que deve ser
	 * setada depois de gravar o item)
	 * 
	 * Provenientes da view(jsp)
	 * 
	 * @param request
	 * @param itemEstrutura
	 * @param funcao
	 * @throws ECARException
	 */
	@SuppressWarnings("unchecked")
	public void salvar(HttpServletRequest request, ItemEstruturaIett itemEstrutura, FuncaoFun funcao) throws ECARException {
		try {
			this.salvar(request, null, itemEstrutura, funcao, true);
		} catch (IOException ioex) {
			this.logger.error(ioex);
			throw new ECARException("erro.arquivo");
		}
	}

	/**
	 * Salva um registro de itemEstrutura. Salva ï¿½ parte os
	 * item-estrutura-funcao-tipo-acomp devido a chave composta. (que deve ser
	 * setada depois de gravar o item)
	 * 
	 * @param request
	 * @param itemEstrutura
	 * @param funcao
	 * @throws ECARException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void salvar(HttpServletRequest request, Transaction transactionArg, ItemEstruturaIett itemEstrutura, FuncaoFun funcao, boolean avaliarAtributoLivre) throws ECARException, IOException,
			HibernateException {
		Transaction tx = null;
		HistoricoItemEstruturaIett historicoItemEstruturaIett = new HistoricoItemEstruturaIett();
		try {
			ArrayList objetos = new ArrayList();

			super.inicializarLogBean();

			if (transactionArg == null) {
				tx = session.beginTransaction();
			}

			itemEstrutura.setDataInclusaoIett(Data.getDataAtual());
			List filhos = new ArrayList();
			if (itemEstrutura.getItemEstUsutpfuacIettutfas() != null) {
				filhos.addAll(itemEstrutura.getItemEstUsutpfuacIettutfas());
			}

			session.save(itemEstrutura);
			objetos.add(itemEstrutura);

			/*
			 * Salvar os atributos livres, pois agora jï¿½ tenho o codIett
			 */
			List atributosLivres = null;
			if (avaliarAtributoLivre) {
				atributosLivres = this.getAtributosLivresItemEstrutura(request, itemEstrutura);
				for (Iterator it = atributosLivres.iterator(); it.hasNext();) {
					ItemEstruturaSisAtributoIettSatb atbLivre = (ItemEstruturaSisAtributoIettSatb) it.next();

					SisAtributoSatb sisAtributo = atbLivre.getSisAtributoSatb();

					// Caso o tipo de validaï¿½ï¿½o seja uma das trï¿½s abaixo,
					// serï¿½ gerado um cï¿½digo incremental.
					if (sisAtributo != null && (sisAtributo.isAtributoAutoIcremental() || sisAtributo.isAtributoContemMascara())) {

						this.geraValorIncremental(atbLivre, sisAtributo, funcao, itemEstrutura.getEstruturaEtt(), objetos);

					}

					session.save(atbLivre);
					objetos.add(atbLivre);
				}
			}

			// Intercï¿½mbio de dados
			if (itemEstrutura.getItemEstrutUsuarioIettusesByCodIett() != null) {
				Iterator itIettuses = itemEstrutura.getItemEstrutUsuarioIettusesByCodIett().iterator();
				while (itIettuses.hasNext()) {
					ItemEstrutUsuarioIettus iettus = (ItemEstrutUsuarioIettus) itIettuses.next();
					session.save(iettus);
					objetos.add(iettus);
				}
			}
			//
			// controlar as permissoes passando o item e a lista das funcoes de
			// acompanhamento velhas (vai ser uma lista vazia)
			//
			new ControlePermissao().atualizarPermissoesItemEstrutura(itemEstrutura, null, session, true, request);

			// gravar permissï¿½o para o usuï¿½rio que criou o item
			ItemEstrutUsuarioIettus itemEstrutUsuario = new ItemEstrutUsuarioIettus();

			itemEstrutUsuario.setItemEstruturaIett(itemEstrutura);
			itemEstrutUsuario.setItemEstruturaIettOrigem(itemEstrutura);
			itemEstrutUsuario.setCodTpPermIettus(ControlePermissao.PERMISSAO_USUARIO);
			itemEstrutUsuario.setUsuarioUsu(itemEstrutura.getUsuarioUsuByCodUsuIncIett());

			itemEstrutUsuario.setIndLeituraIettus("S");
			itemEstrutUsuario.setIndEdicaoIettus("S");
			itemEstrutUsuario.setIndExcluirIettus("S");

			itemEstrutUsuario.setIndAtivMonitIettus("N");
			itemEstrutUsuario.setIndDesatMonitIettus("N");
			itemEstrutUsuario.setIndBloqPlanIettus("N");
			itemEstrutUsuario.setIndDesblPlanIettus("N");
			itemEstrutUsuario.setIndInfAndamentoIettus("N");
			itemEstrutUsuario.setIndEmitePosIettus("N");
			itemEstrutUsuario.setIndProxNivelIettus("N");

			itemEstrutUsuario.setDataInclusaoIettus(Data.getDataAtual());

			Iterator it = filhos.iterator();
			while (it.hasNext()) {
				PaiFilho object = (PaiFilho) it.next();
				object.atribuirPKPai();
				// salva os filhos
				session.save(object);
				objetos.add(object);
			}

			session.save(itemEstrutUsuario);
			objetos.add(itemEstrutUsuario);

			historicoItemEstruturaIett.carregar(itemEstrutura);

			if (atributosLivres != null) {
				historicoItemEstruturaIett.setItemEstruturaSisAtributoIettSatbs(new HashSet(atributosLivres));
			} else {
				historicoItemEstruturaIett.setItemEstruturaSisAtributoIettSatbs(new HashSet<SisAtributoSatb>());
			}

			gerarHistorico(historicoItemEstruturaIett, Historico.INCLUIR);

			if (transactionArg == null) {
				tx.commit();
			}

			if (super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC");
				Iterator itObj = objetos.iterator();

				while (itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (IOException ioex) {
			ioex.printStackTrace();
			// Se a transaï¿½ï¿½o passada for igual a null executa o cï¿½digo
			// abaixo, pois se a transaï¿½ï¿½o passada for vï¿½lida
			// o chamador deverï¿½ fechar a transaï¿½ï¿½o
			if (transactionArg == null) {
				if (tx != null) {
					try {
						tx.rollback();
					} catch (HibernateException r) {
						this.logger.error(r);
						throw new ECARException("erro.arquivo");
					}
					this.logger.error(ioex);
					throw new ECARException("erro.arquivo");
				}
			} else {
				throw ioex;
			}
		} catch (HibernateException hbmex) {
			hbmex.printStackTrace();
			// Se a transaï¿½ï¿½o passada for igual a null executa o cï¿½digo
			// abaixo, pois se a transaï¿½ï¿½o passada for vï¿½lida
			// o chamador deverï¿½ fechar a transaï¿½ï¿½o
			if (transactionArg == null) {
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
			} else {
				throw hbmex;
			}
		}
	}

	/**
	 * Gera o valor para a parte incremental do atributo livre do tipo ID
	 * 
	 * @param atributoLivreBean
	 * @param sisAtributo
	 * @param funcao
	 * @param estrutura
	 * @param objetos
	 * @throws IOException
	 * @throws ECARException
	 */
	protected void geraValorIncremental(FuncaoSisAtributo atributoLivreBean, SisAtributoSatb sisAtributo, FuncaoFun funcao, EstruturaEtt estrutura, List objetos) throws IOException, ECARException {

		SequenciadorDao seqDao = new SequenciadorDao(session);
		seqDao.setHttpRequest(request);

		SequenciadoraSeq sequenciador = seqDao.consultar(sisAtributo, funcao, estrutura);
		ActionSisAtributo action = new ActionSisAtributo();

		if (sequenciador == null) { // O sequenciador serï¿½ nulo quando ainda
									// nï¿½o tiver sequenciador inserido no
									// banco para o tipo de configuraï¿½ï¿½o
									// deste atributo.

			sequenciador = new SequenciadoraSeq();
			sequenciador.inicializar();
			sequenciador.setAtributoLivreSistema(sisAtributo);

			GregorianCalendar gc = new GregorianCalendar();
			gc.setGregorianChange(atributoLivreBean.getDataUltManutencao());
			sequenciador.setAno(gc.get(GregorianCalendar.YEAR));

			sequenciador.setEstrutura(estrutura);
			sequenciador.setFuncao(funcao);

			seqDao.salvar(sequenciador);
			if (objetos != null) {
				objetos.add(sequenciador);
			}

		} else { // O sequenciador nï¿½o serï¿½ nulo quando jï¿½ houver um
					// sequenciador inserido no banco para o tipo de
					// configuraï¿½ï¿½o deste atributo.

			// incrementa o contador sequencial.
			sequenciador.incrementar();

			// Validar, com base na mï¿½scara, o novo valor do sequenciador que
			// foi gerado.
			if (sisAtributo.isAtributoContemMascara()) {

				action.validarValorIncrementalComBaseMascara(atributoLivreBean.getInformacao(), sequenciador.getSequenciaSeq());
			} else {
				action.validarValorIncrementalComBaseMascara(null, sequenciador.getSequenciaSeq());
			}

			if (objetos != null) {
				objetos.add(sequenciador);
			}

		}

		atributoLivreBean.atualizaListaTiposValores(sisAtributo, sequenciador, action, funcao);

		String novoConteudo;

		if (sisAtributo.isAtributoContemMascara()) {

			novoConteudo = action.formatarConteudoParteIncremental(atributoLivreBean.getInformacao(), sequenciador.getSequenciaSeq());

		} else {
			novoConteudo = sequenciador.getSequenciaSeq().toString();
		}

		atributoLivreBean.setInformacao(novoConteudo);

	}

	/**
	 * Realiza a alteraï¿½ï¿½o do item, invocado a partir da view(jsp)
	 * 
	 * 
	 * @param request
	 * @param usuarioLogado
	 * @param historico
	 * @return
	 * @throws Exception
	 */
	public ItemEstruturaIett alterar(HttpServletRequest request, UsuarioUsu usuarioLogado, HistoricoIett historico) throws Exception {
		SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
		return this.alterar(null, request, usuarioLogado, seguranca.getGruposAcesso(), historico, null);
	}

	/**
	 * Altera um item estrutura com as funcoes de acompanhamento.
	 * 
	 * @param request
	 * @param usuarioLogado
	 * @param gruposAcesso
	 * @param historico
	 * @return ItemEstruturaIett
	 * @throws Exception
	 */
	public ItemEstruturaIett alterar(HttpServletRequest request, UsuarioUsu usuarioLogado, Set gruposAcesso, HistoricoIett historico) throws Exception {

		SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");

		ItemEstruturaIett itemEstruturaAlterado = null;

		itemEstruturaAlterado = this.alterar(null, request, usuarioLogado, gruposAcesso, historico, null);

		return itemEstruturaAlterado;
	}

	/**
	 * Invocado a partir da rotina de intercambio de dados
	 * 
	 * @param transactionArg
	 * @param request
	 * @param usuarioLogado
	 * @param itemEstruturaArg
	 * @return
	 * @throws Exception
	 */
	public ItemEstruturaIett alterar(Transaction transactionArg, HttpServletRequest request, UsuarioUsu usuarioLogado, ItemEstruturaIett itemEstruturaArg) throws Exception {

		SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
		Set gruposAcesso = seguranca.getGruposAcesso();

		HistoricoIett historico = new HistoricoIett(itemEstruturaArg, HistoricoIett.alteracao, this.getSession(), new ConfiguracaoDao(request), request);

		ItemEstruturaIett itemEstruturaAlterado = null;

		itemEstruturaAlterado = this.alterar(transactionArg, request, usuarioLogado, gruposAcesso, historico, itemEstruturaArg);

		return itemEstruturaAlterado;
	}

	/**
	 * Altera um item estrutura com as funcoes de acompanhamento na mesma
	 * transaï¿½ï¿½o.
	 * 
	 * @param request
	 * @param usuarioLogado
	 * @param gruposAcesso
	 * @param historico
	 * @return ItemEstruturaIett
	 * @throws Exception
	 */
	private ItemEstruturaIett alterar(Transaction transactionArg, HttpServletRequest request, UsuarioUsu usuarioLogado, Set gruposAcesso, HistoricoIett historico, ItemEstruturaIett itemEstruturaArg)
			throws Exception {
		Transaction tx = null;
		HistoricoItemEstruturaIett historicoItemEstruturaIett = new HistoricoItemEstruturaIett();
		ItemEstruturaIett itemEstrutura = null;
		boolean existeSisAtributoStabUsadoComoRestrititvo = false;
		List listaSisAtributosSatbUsadosComoRestritivo = new ArrayList();
		EstruturaDao estruturaDao = new EstruturaDao(request);
		SisGrupoAtributoDao sgaDao = new SisGrupoAtributoDao(request);

		try {
			ArrayList objetos = new ArrayList();
			super.inicializarLogBean();

			if (transactionArg == null) {
				tx = session.beginTransaction();
			}

			if (itemEstruturaArg == null) {
				itemEstrutura = (ItemEstruturaIett) buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
			} else {
				itemEstrutura = itemEstruturaArg;
			}

			ItemEstruturaIett old = (ItemEstruturaIett) itemEstrutura.clone();

			String indBloqPlanejamentoAnterior = itemEstrutura.getIndBloqPlanejamentoIett();
			String indMonitoramentoAnterior = itemEstrutura.getIndMonitoramentoIett();

			itemEstrutura.getItemEstUsutpfuacIettutfas().size();
			Set lFuac = itemEstrutura.getItemEstUsutpfuacIettutfas();

			/*** Historico ***/
			historico.gerarHistorico(old);
			/*** Historico ***/

			// Verifica se o planejamento estï¿½ bloqueado
			Boolean planejamentoBloqueado = false;
			if (itemEstrutura.getIndBloqPlanejamentoIett() != null && itemEstrutura.getIndBloqPlanejamentoIett().equals("S")) {
				planejamentoBloqueado = true;
			}

			List funcoesDoUsuario = (new TipoFuncAcompDao(null)).getFuncoesAcompNaEstruturaDoUsuario(itemEstrutura, usuarioLogado, gruposAcesso);

			List funcoesBloqueadas = new ArrayList();
			// Caso o planejamento esteja bloqueado, Busca as funï¿½ï¿½es de
			// acompanhamento que
			// estï¿½o bloqueadas e adiciona elas na lista "funcoesBloqueadas".
			if (planejamentoBloqueado) {
				// Descobre a Estrutura do item
				EstruturaEtt estrutura = itemEstrutura.getEstruturaEtt();
				// Descobre as funï¿½ï¿½es de acompanhamento de uma estrutura
				Set funcoesAcomp = estrutura.getEstrutTpFuncAcmpEtttfas();
				// Itera pelas funï¿½ï¿½es buscando as que estï¿½o bloqueadas
				// para alteraï¿½ï¿½o
				if (funcoesAcomp != null) {
					for (Iterator it = funcoesAcomp.iterator(); it.hasNext();) {
						EstrutTpFuncAcmpEtttfa funcao = (EstrutTpFuncAcmpEtttfa) it.next();
						if (funcao.iGetBloqueado() && (!podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, funcao, funcoesDoUsuario))) {
							funcoesBloqueadas.add(funcao);
						}
					}
				}
			}

			List funcoesNaoAlteradas = new ArrayList();
			// apagar as funcoes de acompanhamento do item
			if (itemEstrutura.getItemEstUsutpfuacIettutfas() != null) {
				Iterator it = itemEstrutura.getItemEstUsutpfuacIettutfas().iterator();
				while (it.hasNext()) {
					ItemEstUsutpfuacIettutfa obj = (ItemEstUsutpfuacIettutfa) it.next();
					// Assume que a funï¿½ï¿½o estï¿½ desbloqueada
					Boolean podeAlterar = true;
					// Itera entre as funï¿½ï¿½es que estï¿½o bloqueadas. Sï¿½
					// haverï¿½ valores nesta lista,
					// caso o planejamento esteja bloqueado.
					Iterator it2 = funcoesBloqueadas.iterator();
					while (it2.hasNext()) {
						EstrutTpFuncAcmpEtttfa funcaoBloqueada = (EstrutTpFuncAcmpEtttfa) it2.next();
						// compara a funï¿½ï¿½o e verifica se a confiraï¿½ï¿½o
						// estï¿½ para bloqueada.
						// Caso bloqueada, define que o usuï¿½rio nï¿½o pode ter
						// alterado a funï¿½ï¿½o.
						if (funcaoBloqueada.getComp_id().getCodTpfa().equals(obj.getComp_id().getCodTpfa())) {
							podeAlterar = false;
							break;
						}
					}
					// Sï¿½ remove do banco de dados as funï¿½ï¿½es que o
					// usuï¿½rio poderia ter alterado.
					if (podeAlterar) {
						obj.setDataUltManutencao(Data.getDataAtual());
						obj.setUsuManutencao(usuarioLogado);
						// System.out.println("Deletou Funï¿½ï¿½o Acomp: " +
						// obj.getTipoFuncAcompTpfa().getDescricaoTpfa());
						session.delete(obj);
						objetos.add(obj);
					} else {
						funcoesNaoAlteradas.add(obj);
					}
				}
			}

			// Verifica quais sï¿½o os atributos livres vinculados a estrutura.
			// Isto sï¿½ ï¿½ feito se o planejamento estiver bloqueado.
			List sgas = new ArrayList();
			if (planejamentoBloqueado) {
				// busca os atributos livre configurados para o item em seu
				// nï¿½vel na estrutura
				if (itemEstrutura.getEstruturaEtt() != null && itemEstrutura.getEstruturaEtt().getEstruturaAtributoEttats() != null
						&& !itemEstrutura.getEstruturaEtt().getEstruturaAtributoEttats().isEmpty()) {
					for (Iterator it = itemEstrutura.getEstruturaEtt().getEstruturaAtributoEttats().iterator(); it.hasNext();) {
						EstruturaAtributoEttat ettat = (EstruturaAtributoEttat) it.next();
						if (ettat.getAtributosAtb() != null && ettat.getAtributosAtb().getSisGrupoAtributoSga() != null) {
							// verifica se o atributo livre esta configurado
							// para estar bloqueado.
							if (ettat.iGetBloqueado() && (!podeEditarAtributoBloqueadoNaEstrutura(itemEstrutura, ettat, funcoesDoUsuario))) {
								// System.out.println("Atributo Livre" +
								// ettat.getLabelEstruturaEttat());
								sgas.add(ettat.getAtributosAtb().getSisGrupoAtributoSga());
							}
						}
					}
				}
			}

			List atributosLivres = this.getAtributosLivresItemEstrutura(request, itemEstrutura);
			// apagar os atributos livres do item
			if (itemEstrutura.getItemEstruturaSisAtributoIettSatbs() != null) {
				// busca os atributos livre configurados para este nï¿½vel da
				// estrutura
				Iterator it = itemEstrutura.getItemEstruturaSisAtributoIettSatbs().iterator();
				List satbsRestritivos = new ArrayList();
				SisGrupoAtributoSga sgaRestritivo = null;
				while (it.hasNext()) {
					ItemEstruturaSisAtributoIettSatb obj = (ItemEstruturaSisAtributoIettSatb) it.next();
					// assumo que o usuï¿½rio poderia alterar este atributo.
					Boolean podeAlterar = true;
					if (planejamentoBloqueado) {
						for (Iterator it2 = sgas.iterator(); it2.hasNext();) {
							SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) it2.next();
							if (grupoAtributo.getCodSga().equals(obj.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga())) {
								// System.out.println("Atributo livre nï¿½o editavel: "
								// + grupoAtributo.getDescricaoSga());
								podeAlterar = false;
								break;
							}
						}
					}

					// verifica se a estrutura do item tem estrturas filhas e o
					// atributo ï¿½ um atributo restritivo.
					if (obj.getSisAtributoSatb().getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg() == Input.LISTBOX
							|| obj.getSisAtributoSatb().getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg() == Input.RADIO_BUTTON
							|| obj.getSisAtributoSatb().getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg() == Input.COMBOBOX
							|| obj.getSisAtributoSatb().getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg() == Input.CHECKBOX) {

						if (existeEstruturaFilhaUsandoAtributoComoRestritivo(itemEstrutura, obj.getSisAtributoSatb()) && !atributosLivres.contains(obj) && podeAlterar) {
							if (sgaRestritivo == null) {
								sgaRestritivo = obj.getSisAtributoSatb().getSisGrupoAtributoSga();
								satbsRestritivos.add(obj.getSisAtributoSatb());
								sgaRestritivo = obj.getSisAtributoSatb().getSisGrupoAtributoSga();
								// estruturaDao.getLabelAtributoEstrutra(itemEstrutura.getEstruturaEtt(),
								// obj.getSisAtributoSatb().getSisGrupoAtributoSga());
							} else if (sgaRestritivo.equals(obj.getSisAtributoSatb().getSisGrupoAtributoSga())) {
								sgaRestritivo = obj.getSisAtributoSatb().getSisGrupoAtributoSga();
								satbsRestritivos.add(obj.getSisAtributoSatb());
								// args.append(", ").append(obj.getSisAtributoSatb().getDescricaoSatb());
							}
							existeSisAtributoStabUsadoComoRestrititvo = true;
						}
					}

					if (podeAlterar && !existeSisAtributoStabUsadoComoRestrititvo) {

						if (obj.getSisAtributoSatb().getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg() == Input.IMAGEM) {

							String nomeCampo = request.getParameter("a" + obj.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga().toString());

							if (nomeCampo != null && nomeCampo.equals("")) {

								String fullFile = obj.getInformacao();

								if (fullFile.lastIndexOf("=") != -1)
									fullFile = fullFile.substring(fullFile.lastIndexOf("=") + 1);

								File f = new File(fullFile);
								if (f.exists())
									FileUpload.apagarArquivo(fullFile);
							}

						}

						session.delete(obj);
						objetos.add(obj);

					}
				}

				if (existeSisAtributoStabUsadoComoRestrititvo && satbsRestritivos.size() > 0) {
					String labelAtributoRestritivo = "'" + estruturaDao.getLabelAtributoEstrutra(itemEstrutura.getEstruturaEtt(), sgaRestritivo) + "'";
					List satbsRestritivosOrdenados = sgaDao.ordenadarSisAtributos(sgaRestritivo, satbsRestritivos);
					Iterator itSatbsRestritivosOrdenados = satbsRestritivosOrdenados.iterator();
					StringBuilder labelSatbsRestritivos = new StringBuilder();
					while (itSatbsRestritivosOrdenados.hasNext()) {
						SisAtributoSatb sisAtb = (SisAtributoSatb) itSatbsRestritivosOrdenados.next();
						labelSatbsRestritivos.append("'" + sisAtb.getDescricaoSatb() + "'");
						if (itSatbsRestritivosOrdenados.hasNext()) {
							labelSatbsRestritivos.append(", ");
						}
					}
					if (satbsRestritivosOrdenados.size() > 1) {
						throw new ECARException("itemEstrutura.sisAtributoSatbEttSuperior.sisAtributoSatbUsadoComoAtributoRestritoEstruturaFilha", null, new String[] {
								labelSatbsRestritivos.toString(), labelAtributoRestritivo });
					} else {
						throw new ECARException("itemEstrutura.sisAtributoSatbEttSuperior.umSisAtributoSatbUsadoComoAtributoRestritoEstruturaFilha", null, new String[] {
								labelSatbsRestritivos.toString(), labelAtributoRestritivo });
					}
				}

			}

			// seta o novo item_estrutura
			/**
			 * Sï¿½ deverï¿½ setar os dados recebidos do request no objeto item
			 * Estrutura, se o objeto itemEstruturaArg passado como parï¿½metro
			 * estiver nulo, caso o objeto itemEstruturaArg tenha valor ele jï¿½
			 * deverï¿½ estar com todos os dados que serï¿½o alterados.
			 */
			if (itemEstruturaArg == null) {
				this.setItemEstrutura(request, itemEstrutura);
			}
			itemEstrutura.setUsuarioUsuByCodUsuUltManutIett(usuarioLogado);
			itemEstrutura.setDataUltManutencaoIett(Data.getDataAtual());

			/*
			 * Salvar os atributos livres, pois agora jï¿½ tenho o codIett
			 */

			for (Iterator it = atributosLivres.iterator(); it.hasNext();) {
				ItemEstruturaSisAtributoIettSatb atbLivre = (ItemEstruturaSisAtributoIettSatb) it.next();

				SisAtributoSatb sisAtributo = atbLivre.getSisAtributoSatb();
				FuncaoFun funcao;
				FuncaoDao funcaoDao;
				// Caso o tipo de validaï¿½ï¿½o seja igual a Mascara Editavel,
				// serï¿½ gerado um cï¿½digo incremental.
				if (sisAtributo.getAtribInfCompSatb() != null && sisAtributo.isAtributoMascaraEditavel()) {

					funcaoDao = new FuncaoDao(request);
					funcao = funcaoDao.getFuncaoDadosGerais();

					this.atualizaValorAtributosID(atbLivre, sisAtributo, funcao);
				} else if (sisAtributo.getAtribInfCompSatb() != null && (sisAtributo.isAtributoAutoIcremental() || sisAtributo.isAtributoMascara())) {

					ItemEstruturaSisAtributoIettSatb atributoLivreNoItem = atbLivre.getItemEstruturaIett().buscarItemEstruturaSisAtributoLista(sisAtributo);

					funcaoDao = new FuncaoDao(request);
					funcao = funcaoDao.getFuncaoDadosGerais();

					if (atributoLivreNoItem != null && atributoLivreNoItem.getTiposValores() != null && !atributoLivreNoItem.getTiposValores().isEmpty()) {
						this.copiarTipoValorSemID(atbLivre, atributoLivreNoItem.getTiposValores(), funcao);
					} else {
						String arg = "Item " + atbLivre.getItemEstruturaIett().getCodIett() + " atributo livre " + sisAtributo.getCodSatb();
						throw new ECARException("erro.atributo.item.inconsistente");
					}
				}

				session.save(atbLivre);
				objetos.add(atbLivre);
			}

			// Salva as Funï¿½ï¿½es de acompanhamento do item.
			if (itemEstrutura.getItemEstUsutpfuacIettutfas() != null) {
				Iterator it = itemEstrutura.getItemEstUsutpfuacIettutfas().iterator();
				while (it.hasNext()) {
					PaiFilho object = (PaiFilho) it.next();
					object.atribuirPKPai();
					// salva os filhos
					session.save(object);
					objetos.add(object);
				}
			}

			// Seta no itemEstrutura as funï¿½ï¿½es de acompanhamento que o
			// usuï¿½rio nï¿½o podia modificar.
			// para atualizar logo abaixo as permissï¿½es do item.
			Iterator it = funcoesNaoAlteradas.iterator();
			while (it.hasNext()) {
				itemEstrutura.getItemEstUsutpfuacIettutfas().add((ItemEstUsutpfuacIettutfa) it.next());
			}

			//
			// controlar as permissoes passando o item e a lista das funcoes de
			// acompanhamento antigas
			//
			new ControlePermissao().atualizarPermissoesItemEstrutura(itemEstrutura, lFuac, session, false, request);

			if (Dominios.SIM.equals(Pagina.getParamStr(request, "ativarRetirarMonitoramentoItensFilho"))) {
				if (indMonitoramentoAnterior != null && !indMonitoramentoAnterior.equals(itemEstrutura.getIndMonitoramentoIett())) {
					this.propagarMonitoramento(itemEstrutura, historico);
				}
			}

			if (indBloqPlanejamentoAnterior != null && !indBloqPlanejamentoAnterior.equals(itemEstrutura.getIndBloqPlanejamentoIett())) {
				this.propagarPlanejamento(itemEstrutura, historico);
			}

			// Salva o ItemEstrutura
			session.update(itemEstrutura);
			objetos.add(itemEstrutura);

			historicoItemEstruturaIett.carregar(itemEstrutura);

			if (atributosLivres != null) {
				historicoItemEstruturaIett.setItemEstruturaSisAtributoIettSatbs(new HashSet(atributosLivres));
			} else {
				historicoItemEstruturaIett.setItemEstruturaSisAtributoIettSatbs(new HashSet<SisAtributoSatb>());
			}

			gerarHistorico(historicoItemEstruturaIett, Historico.ALTERAR);

			if (transactionArg == null) {
				tx.commit();
			}

			if (super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC_ALT_EXC");
				Iterator itObj = objetos.iterator();

				while (itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}

		} catch (ECARException ecar) {
			if (transactionArg == null) {
				if (tx != null) {
					try {
						tx.rollback();
					} catch (HibernateException r) {
						this.logger.error(r);
						throw new ECARException("erro.hibernateException");
					}
				}
			}
			this.logger.error(ecar);
			throw ecar;
		} catch (HibernateException hbmex) {
			hbmex.printStackTrace();
			if (transactionArg == null) {
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
			} else {
				this.logger.error(hbmex);
				throw hbmex;
			}
		}

		return itemEstrutura;
	}

	private void copiarTipoValorSemID(ItemEstruturaSisAtributoIettSatb atbLivre, Set<TipoValor> tiposValores, FuncaoFun funcao) {

		Set listaTiposValores = new HashSet();
		for (TipoValor tipoValor : tiposValores) {

			TipoValor tipoValorInner = new TipoValor();

			tipoValorInner.setConteudo(tipoValor.getConteudo());
			tipoValorInner.setItemEstruturaSisAtributo(atbLivre);
			tipoValorInner.setTipo(tipoValor.getTipo());
			tipoValorInner.setFuncao(funcao);

			listaTiposValores.add(tipoValorInner);

		}
		atbLivre.setTiposValores(listaTiposValores);

	}

	/**
	 * Atualiza a lista de tipos valores existentes
	 * 
	 * @param atributoLivreBean
	 * @param sisAtributo
	 * @param funcao
	 * @throws ECARException
	 */
	protected void atualizaValorAtributosID(FuncaoSisAtributo atributoLivreBean, SisAtributoSatb sisAtributo, FuncaoFun funcao) throws ECARException {

		ActionSisAtributo action = new ActionSisAtributo();

		// cria novos tipos de valores.
		atributoLivreBean.atualizaListaTiposValores(sisAtributo, null, action, funcao);
		// Set listaTiposValores =
		// listaTiposValores(atributoLivreBean,sisAtributo,null,action,funcao);
		// atributoLivreBean.setTiposValores(listaTiposValores);

	}

	/**
	 * Mï¿½todo estï¿½ sendo usado para excluir
	 * Item-estrutura-usuario-funcao-acomp de um ItemEstrutura quando o item ï¿½
	 * excluido
	 * 
	 * @param itemEstrutura
	 * @throws ECARException
	 */
	public void excluirItemEstruturaTipoFuncaoAcomp(ItemEstruturaIett itemEstrutura) throws ECARException {
		List<ItemEstUsutpfuacIettutfa> filhos = new ArrayList<ItemEstUsutpfuacIettutfa>();
		if (itemEstrutura.getItemEstUsutpfuacIettutfas() != null) {
			for (Iterator it = itemEstrutura.getItemEstUsutpfuacIettutfas().iterator(); it.hasNext();) {
				ItemEstUsutpfuacIettutfa ieFun = (ItemEstUsutpfuacIettutfa) it.next();
				filhos.add(ieFun);
			}
			super.excluir(filhos);
		}
	}

	/**
	 * Recebe um array contendo cï¿½digos de itens da estrutura e exclui todos
	 * os registros. Todas os dados do item e todos os dados dos dependentes
	 * sï¿½o excluidos tambï¿½m
	 * 
	 * @param codigosParaExcluir
	 * @param usuario
	 * @throws ECARException
	 */
	public void excluir(String[] codigosParaExcluir, UsuarioUsu usuario) throws ECARException {
		this.excluir(null, codigosParaExcluir, usuario);
	}

	/**
	 * Recebe um array contendo cï¿½digos de itens da estrutura e exclui todos
	 * os registros. Todas os dados do item e todos os dados dos dependentes
	 * sï¿½o excluidos tambï¿½m
	 * 
	 * @param codigosParaExcluir
	 * @param usuario
	 * @throws ECARException
	 */
	public void excluir(Transaction transactionArg, String[] codigosParaExcluir, UsuarioUsu usuario) throws ECARException, HibernateException {
		Transaction tx = null;

		HistoricoItemEstruturaIett historicoItemEstruturaIett = new HistoricoItemEstruturaIett();
		try {
			ArrayList<ItemEstruturaIett> objetos = new ArrayList<ItemEstruturaIett>();
			super.inicializarLogBean();

			if (transactionArg == null) {
				tx = session.beginTransaction();
			}

			for (int i = 0; i < codigosParaExcluir.length; i++) {
				ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) buscar(ItemEstruturaIett.class, Long.valueOf(codigosParaExcluir[i]));

				ItemEstruturaIett old = (ItemEstruturaIett) itemEstrutura.clone();

				itemEstrutura.setDataUltManutencaoIett(Data.getDataAtual());
				itemEstrutura.setUsuarioUsuByCodUsuUltManutIett(usuario);
				itemEstrutura.setIndAtivoIett(Dominios.NAO);

				/******** Historico *********/
				HistoricoIett historico = new HistoricoIett(itemEstrutura, HistoricoIett.exclusao, session, new ConfiguracaoDao(request), request);
				historico.gerarHistorico(old);
				/******** Historico *********/

				session.update(itemEstrutura);

				objetos.add(itemEstrutura);

				List iettFilhos = this.getDescendentes(itemEstrutura, false);

				for (Iterator it = iettFilhos.iterator(); it.hasNext();) {
					ItemEstruturaIett itemEstruturaFilho = (ItemEstruturaIett) it.next();

					old = (ItemEstruturaIett) itemEstruturaFilho.clone();

					/*** Historico ***/
					historico.gerarHistorico(old);
					/*** Historico ***/

					itemEstruturaFilho.setDataUltManutencaoIett(Data.getDataAtual());
					itemEstruturaFilho.setUsuarioUsuByCodUsuUltManutIett(usuario);
					itemEstruturaFilho.setIndAtivoIett(Dominios.NAO);

					session.update(itemEstruturaFilho);

					objetos.add(itemEstruturaFilho);
				}

				historicoItemEstruturaIett.carregar(itemEstrutura);

				gerarHistorico(historicoItemEstruturaIett, Historico.EXCLUIR);

			}

			if (transactionArg == null) {
				tx.commit();
			}

			if (super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("EXC");

				for (Iterator itObj = objetos.iterator(); itObj.hasNext();) {
					Object element = (Object) itObj.next();
					super.logBean.setObj(element);
					super.loggerAuditoria.info(logBean.toString());
				}
			}

		} catch (HibernateException hbmex) {
			hbmex.printStackTrace();
			// Se a transaï¿½ï¿½o passada for igual a null executa o cï¿½digo
			// abaixo, pois se a transaï¿½ï¿½o passada for vï¿½lida
			// o chamador deverï¿½ fechar a transaï¿½ï¿½o
			if (transactionArg == null) {
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
			} else {
				throw hbmex;
			}
		}
	}

	/**
	 * Retorna uma lista com todos os itens de Estrutura acima de um dado Item
	 * 
	 * @param itemEstrutura
	 * @return
	 */
	public List getAscendentes(ItemEstruturaIett itemEstrutura) {
		List<ItemEstruturaIett> retorno = new ArrayList<ItemEstruturaIett>();
		while (itemEstrutura.getItemEstruturaIett() != null) {
			itemEstrutura = itemEstrutura.getItemEstruturaIett();
			retorno.add(itemEstrutura);
		}
		Collections.reverse(retorno);
		return retorno;
	}

	/**
	 * 
	 * @param itemEstrutura
	 * @return
	 */
	public ItemEstruturaIett getAscendenteMaximo(ItemEstruturaIett itemEstrutura) {
		while (itemEstrutura.getItemEstruturaIett() != null) {
			itemEstrutura = itemEstrutura.getItemEstruturaIett();
		}
		return itemEstrutura;
	}

	/**
	 * Retorna uma lista com todos os itens de Estrutura acima de um dado Item
	 * cujo nï¿½vel mais alto da hierarquia ï¿½ o itemPai passado como
	 * parï¿½metro
	 * 
	 * @param itemEstrutura
	 * @param itemPai
	 * @return
	 */
	public List getAscendentes(ItemEstruturaIett itemEstrutura, ItemEstruturaIett itemPai) {
		List<ItemEstruturaIett> retorno = new ArrayList<ItemEstruturaIett>();
		while (!itemEstrutura.equals(itemPai)) {
			itemEstrutura = itemEstrutura.getItemEstruturaIett();
			retorno.add(itemEstrutura);
		}
		Collections.reverse(retorno);
		return retorno;
	}

	/**
	 * Retorna uma lista com todos os itens de Estrutura abaixo de um dado Item
	 * 
	 * @param itemEstrutura
	 * @param efetuarRefreshItemEstrutura
	 * @return
	 * @throws ECARException
	 */
	public List getDescendentes(ItemEstruturaIett itemEstrutura, boolean efetuarRefreshItemEstrutura) throws ECARException {
		List<ItemEstruturaIett> retorno = new ArrayList<ItemEstruturaIett>();

		if (efetuarRefreshItemEstrutura) {
			try {
				// this.session.refresh(itemEstrutura);
			} catch (HibernateException e) {
				this.logger.error(e);
				throw new ECARException(e);
			}
		}

		if (itemEstrutura.getItemEstruturaIetts() != null) {

			for (Iterator it = itemEstrutura.getItemEstruturaIetts().iterator(); it.hasNext();) {
				ItemEstruturaIett itemEstruturaFilho = (ItemEstruturaIett) it.next();
				if (!retorno.contains(itemEstruturaFilho))
					retorno.add(itemEstruturaFilho);
				retorno.addAll(this.getDescendentes(itemEstruturaFilho, efetuarRefreshItemEstrutura));
			}
		}
		return retorno;
	}

	/**
	 * recupera descendentes com pojo (ItemEstruturaIettMin)
	 * 
	 * @param itemEstrutura
	 * @param efetuarRefreshItemEstrutura
	 * @return
	 * @throws ECARException
	 */
	public List getDescendentesMin(ItemEstruturaIettMin itemEstrutura, boolean efetuarRefreshItemEstrutura) throws ECARException {
		List<ItemEstruturaIettMin> retorno = new ArrayList<ItemEstruturaIettMin>();

		if (efetuarRefreshItemEstrutura) {
			try {
				this.session.refresh(itemEstrutura);
			} catch (HibernateException e) {
				this.logger.error(e);
				throw new ECARException(e);
			}
		}

		if (itemEstrutura.getItemEstruturaIetts() != null) {

			for (ItemEstruturaIettMin itemEstruturaFilho : itemEstrutura.getItemEstruturaIetts()) {
				if (!retorno.contains(itemEstruturaFilho))
					retorno.add(itemEstruturaFilho);
				retorno.addAll(this.getDescendentesMin(itemEstruturaFilho, efetuarRefreshItemEstrutura));
			}
		}
		return retorno;
	}

	/**
	 * Retorna uma lista com todos os itens de Estrutura abaixo de um dado Item
	 * - Busca os itens no BD.
	 * 
	 * @param itemEstrutura
	 * @return
	 * @throws ECARException
	 */
	@SuppressWarnings("unchecked")
	public List<ItemEstruturaIett> getDescendentesViaQry(ItemEstruturaIett itemEstrutura) throws ECARException {
		try {
			return this.getSession().createQuery("select iett from ItemEstruturaIett iett " + "where iett.itemEstruturaIett.codIett = :codPai " + "and iett.itemEstruturaIett.indAtivoIett = 'S'")
					.setLong("codPai", itemEstrutura.getCodIett().longValue()).list();

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}

	public List<ItemEstruturaIettMin> getDistinctSigla(int nivel, EstruturaEtt ett) {
		Query q = this.getSession()
				.createQuery(
						"SELECT DISTINCT iett FROM " + "ItemEstruturaIettMin iett " + "WHERE iett.indAtivoIett = 'S' " + "AND iett.estruturaEtt = :ett " + "AND iett.nivelIett = :n "
								+ "ORDER BY iett.codIett");
		q.setInteger("n", nivel);
		q.setParameter("ett", ett);

		return q.list();
	}

	/**
	 * Retorna uma lista com todos os itens de Estrutura abaixo de um dado Item,
	 * recursivamente. Verifica tambï¿½m se o usuï¿½rio tem permissï¿½o para
	 * dado item. Se nï¿½o tem permissï¿½o para o item, verifica se tem para
	 * algum filho
	 * 
	 * @param itemEstrutura
	 * @param efetuarRefreshItemEstrutura
	 * @param usuario
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
	public List getDescendentesComPermissao(ItemEstruturaIett itemEstrutura, boolean efetuarRefreshItemEstrutura, UsuarioUsu usuario, Set gruposUsuario) throws ECARException {
		ValidaPermissao validaPermissao = new ValidaPermissao();
		List<ItemEstruturaIett> retorno = new ArrayList<ItemEstruturaIett>();

		if (efetuarRefreshItemEstrutura) {
			/*
			 * faz um refresh no item para que nï¿½o seja aproveitado o objeto
			 * existente na session do Hibernate e termos um objeto com a
			 * coleï¿½aï¿½ de filhos completa
			 */
			try {
				this.session.refresh(itemEstrutura);
			} catch (HibernateException e) {
				this.logger.error(e);
				throw new ECARException(e);
			}
		}

		if (itemEstrutura.getItemEstruturaIetts() != null) {

			Iterator it = itemEstrutura.getItemEstruturaIetts().iterator();
			while (it.hasNext()) {
				ItemEstruturaIett itemEstruturaFilho = (ItemEstruturaIett) it.next();

				if (!retorno.contains(itemEstruturaFilho)) {
					validaPermissao.permissoesItem(itemEstruturaFilho, usuario, gruposUsuario);
					if (validaPermissao.permissaoConsultarItem())
						retorno.add(itemEstruturaFilho);
				}

				retorno.addAll(this.getDescendentesComPermissao(itemEstruturaFilho, efetuarRefreshItemEstrutura, usuario, gruposUsuario));
			}
		}
		return retorno;
	}

	/**
	 * Retorna uma lista com todos os itens de Estrutura abaixo de um dado Item
	 * que sejam de um dos nï¿½veis de palenjamento passados como parï¿½metros
	 * na Lista
	 * 
	 * @param itemEstrutura
	 * @param niveisPlanejamento
	 * @return
	 * @throws ECARException
	 */
	public List getDescendentesPorNivelPlanejamento(ItemEstruturaIett itemEstrutura, List niveisPlanejamento) throws ECARException {
		List<ItemEstruturaIett> retorno = new ArrayList<ItemEstruturaIett>();

		try {
			this.session.refresh(itemEstrutura);
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}

		if (itemEstrutura.getItemEstruturaIetts() != null) {
			for (Iterator it = itemEstrutura.getItemEstruturaIetts().iterator(); it.hasNext();) {
				ItemEstruturaIett itemEstruturaFilho = (ItemEstruturaIett) it.next();
				if (!retorno.contains(itemEstruturaFilho) && Util.intersecao(itemEstruturaFilho.getItemEstruturaNivelIettns(), niveisPlanejamento).size() > 0) {
					retorno.add(itemEstruturaFilho);
				}
				retorno.addAll(this.getDescendentesPorNivelPlanejamento(itemEstruturaFilho, niveisPlanejamento));
			}
		}
		return retorno;
	}

	/**
	 * Retorna os Itens filhos de um Item cujo nï¿½vel seja no mï¿½ximo nivel
	 * pai + (nivel)
	 * 
	 * @param itemEstrutura
	 * @param nivel
	 * @return
	 * @throws ECARException
	 */
	public List getDescendentes(ItemEstruturaIett itemEstrutura, int nivel) throws ECARException {
		List<ItemEstruturaIett> retorno = new ArrayList<ItemEstruturaIett>();
		int nivelMaximo = nivel + itemEstrutura.getNivelIett().intValue();
		if (itemEstrutura.getItemEstruturaIetts() != null) {
			for (Iterator it = itemEstrutura.getItemEstruturaIetts().iterator(); it.hasNext();) {
				ItemEstruturaIett itemEstruturaFilho = (ItemEstruturaIett) it.next();
				if (itemEstruturaFilho.getNivelIett().intValue() <= nivelMaximo && !retorno.contains(itemEstruturaFilho))
					retorno.add(itemEstruturaFilho);
				retorno.addAll(this.getDescendentes(itemEstruturaFilho, true));
			}
		}
		return retorno;
	}

	/**
	 * Devolve uma lista de itens da estrutura ordenados (estrutura + primeiro
	 * campo da listagem de colunas da estrutura) e somente os itens que o
	 * usuï¿½rio pode consultar.
	 * 
	 * @param itemEstrutura
	 * @param usuarioUsu
	 * @param gruposUsuario
	 * @return List
	 * @throws ECARException
	 */
	public List getDescendentesComPermissoesOrdenado(ItemEstruturaIett itemEstrutura, UsuarioUsu usuarioUsu, Set gruposUsuario) throws ECARException {
		ValidaPermissao validaPermissao = new ValidaPermissao();
		List retorno = new ArrayList();

		/*
		 * 1.Obter um conjunto de estrutura do item passado como parï¿½metro
		 * (EstruturaDao.getSetEstruturasItem(item);)
		 */
		EstruturaDao estruturaDao = new EstruturaDao(request);
		List itens = estruturaDao.getSetEstruturasItem(itemEstrutura);

		retorno.add(itemEstrutura);

		/* 2.Para cada estrutura 'e' obtida no passo anterior */

		Iterator itEstrutura = itens.iterator();
		while (itEstrutura.hasNext()) {
			EstruturaEtt e = (EstruturaEtt) itEstrutura.next();

			/*
			 * 3.Obter as colunas dessa estrutura
			 * (EstruturaDao.getAtributosAcessoEstrutura('e');)
			 */
			List atributos = estruturaDao.getAtributosAcessoEstrutura(e);
			if (atributos != null && atributos.size() > 0) {
				ObjetoEstrutura objeto = (ObjetoEstrutura) atributos.get(0); // primeira
																				// coluna
																				// obtida

				/*
				 * 4.Obter os itens filhos da estrutura 'e', ordenados pela
				 * primeira coluna obtida no passo anterior;
				 * ItemEstruturaDao.getItensFilhos(item, 'e',
				 * ObjetoEstrutura.iGetNomeOrdenarLista())
				 */
				List itensFilhos = getItensFilho(itemEstrutura, e, objeto.iGetNomeOrdenarLista());

				/* 5.Para cada item 'it' obtido no passo anterior */
				Iterator itFilhos = itensFilhos.iterator();
				while (itFilhos.hasNext()) {
					ItemEstruturaIett itemFilho = (ItemEstruturaIett) itFilhos.next();

					/*
					 * 6.Obter a permissao do usuario para este item
					 * (ValidaPermissao.permissoesItem('it', usuario, grupos);
					 */
					validaPermissao.permissoesItem(itemFilho, usuarioUsu, gruposUsuario);

					/* 7.Se validaPermissao.permissaoConsultarItem() */
					if (validaPermissao.permissaoConsultarItem()) {
						/* 8.Adiciona o item 'it' na lista de retorno */
						retorno.add(itemFilho);
						/*
						 * 9.Chama o mï¿½todo recursivamente
						 * (getDescendentesComPermissoesOrdenado('it', usuario,
						 * grupos);)
						 */
						retorno.addAll(getDescendentesComPermissoesOrdenado(itemFilho, usuarioUsu, gruposUsuario));
					}
				}
			}
		}
		return retorno;
	}

	/**
	 * Devolve uma lista de itens da estrutura ordenados (estrutura + primeiro
	 * campo da listagem de colunas da estrutura) e somente os itens que o
	 * usuï¿½rio pode consultar por estrutura.
	 * 
	 * @param estrutura
	 * @param codItemPai
	 * @param usuarioUsu
	 * @param gruposUsuario
	 * @return
	 * @throws ECARException
	 */
	public List getDescendentesComPermissoesOrdenadoByEstrutura(EstruturaEtt estrutura, long codItemPai, UsuarioUsu usuarioUsu, Set gruposUsuario) throws ECARException {
		ValidaPermissao validaPermissao = new ValidaPermissao();
		List<ItemEstruturaIett> retorno = new ArrayList<ItemEstruturaIett>();
		try {
			String select = "select iett from ItemEstruturaIett iett" + " where iett.estruturaEtt.codEtt = :codEstrutura" + " and iett.indAtivoIett = 'S'";

			if (codItemPai != 0) {
				select += " and iett.itemEstruturaIett.codIett = :codPai";
			}

			Query q = this.session.createQuery(select);
			q.setLong("codEstrutura", estrutura.getCodEtt().longValue());

			if (codItemPai != 0) {
				q.setLong("codPai", codItemPai);
			}

			List itensEstrutura = q.list();

			if (itensEstrutura != null && !itensEstrutura.isEmpty()) {
				for (Iterator itEtt = itensEstrutura.iterator(); itEtt.hasNext();) {
					ItemEstruturaIett iett = (ItemEstruturaIett) itEtt.next();
					validaPermissao.permissoesItem(iett, usuarioUsu, gruposUsuario);
					if (validaPermissao.permissaoConsultarItem()) {
						retorno.add(iett);
						retorno.addAll(this.getDescendentesComPermissoesOrdenado(iett, usuarioUsu, gruposUsuario));
					}
				}
			}
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}

		return retorno;
	}

	/**
	 * Retorna um list com identificaï¿½ï¿½es de todas os Atributos de Nï¿½vel
	 * de Acesso de um item
	 * 
	 * @param item
	 * @return List de Long
	 */
	public ArrayList getNivelAcessoById(ItemEstruturaIett item) {
		/*
		 * comentado bug #880 A idï¿½ia ï¿½ que os nï¿½veis de acesso venham na
		 * ordem da maneira que estï¿½ cadastrado no grupo de atributos, ou
		 * seja, por informaï¿½ï¿½es complementares. Do jeito que estï¿½ vem em
		 * qualquer ordem.
		 * 
		 * List lNiveis = new ArrayList();
		 * if(item.getItemEstruturaNivelIettns()!= null &&
		 * item.getItemEstruturaNivelIettns().size() > 0){ Iterator it =
		 * item.getItemEstruturaNivelIettns().iterator(); while(it.hasNext()){
		 * SisAtributoSatb atributo = (SisAtributoSatb) it.next();
		 * lNiveis.add(atributo.getCodSatb()); } } return lNiveis;
		 */

		// obter os niveis de acesso do item na ordem
		List lNiveis = getNivelAcesso(item);
		ArrayList<Long> resultado = new ArrayList<Long>();

		// extrair os cï¿½digos
		for (Iterator iter = lNiveis.iterator(); iter.hasNext();) {
			SisAtributoSatb atributo = (SisAtributoSatb) iter.next();
			resultado.add(atributo.getCodSatb());
		}
		return resultado;
	}

	/**
	 * Retorna um list com os os Atributos de Nï¿½vel de Acesso de um item
	 * 
	 * @param item
	 * @return List de SisAtributoSatb
	 */
	public List getNivelAcesso(ItemEstruturaIett item) {
		/*
		 * comentando por causa do bug 880;
		 * 
		 * List lNiveis = new ArrayList();
		 * if(item.getItemEstruturaNivelIettns()!= null &&
		 * item.getItemEstruturaNivelIettns().size() > 0){ Iterator it =
		 * item.getItemEstruturaNivelIettns().iterator(); while(it.hasNext()){
		 * SisAtributoSatb atributo = (SisAtributoSatb) it.next();
		 * lNiveis.add(atributo); } } return lNiveis;
		 */

		/*
		 * funcionamento: 1. Determinar um atributo do nivel de acesso para
		 * obter o grupo de atributos; 2. a partir do grupo obter todos os
		 * atributos ordenados; 3. retirar do resultado de 2 os atributos que
		 * nï¿½o fazem parte do item
		 */

		List lNiveis = new ArrayList();
		if (item.getItemEstruturaNivelIettns() != null && item.getItemEstruturaNivelIettns().size() > 0) {
			// 1.
			SisGrupoAtributoSga grupoAtributo = ((SisAtributoSatb) item.getItemEstruturaNivelIettns().iterator().next()).getSisGrupoAtributoSga();

			try {
				// 2.
				lNiveis = new SisGrupoAtributoDao().getAtributosOrdenados(grupoAtributo);

				// 3.
				lNiveis.retainAll(item.getItemEstruturaNivelIettns());

			} catch (ECARException e) {
				this.logger.error(e);
			}
		}

		return lNiveis;

	}

	/**
	 * Devolve uma Lista com todos os itens filho do item passado que sejam da
	 * estrutura passada como parï¿½metro. Caso o item seja nulo, retorna uma
	 * Collection com os itens que pertencem a estrutura passada como
	 * parï¿½metro. Recebe como argumento o item, a estrutura e o nome do campo
	 * pelo qual se deseja ordenar a lista
	 * 
	 * @param item
	 * @param estrutura
	 * @param nomeCampoOrderBy
	 * @return List de itemEstruturaIett
	 * @throws ECARException
	 */
	public List getItensFilho(ItemEstruturaIett item, EstruturaEtt estrutura, String nomeCampoOrderBy) throws ECARException {
		List lista = new ArrayList();
		String nomeOrder;

		/*
		 * assume um default quando nao pode mapear o campo para ordenar no
		 * cadastro de itens por exemplo, quando colocar a primeira coluna com
		 * uma funcao de acompanhamento
		 */
		if (nomeCampoOrderBy == null || "".equals(nomeCampoOrderBy))
			nomeOrder = "this.nomeIett";
		else
			nomeOrder = "this." + nomeCampoOrderBy;

		try {
			if (item != null) {
				lista.addAll(this.getSession().createFilter(item.getItemEstruturaIetts(), "where this.estruturaEtt.codEtt = " + estrutura.getCodEtt() + " order by " + nomeOrder).list());
			} else {
				lista.addAll(this.getSession().createFilter(estrutura.getItemEstruturaIetts(), "order by " + nomeOrder).list());
			}
			return lista;
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}

	/**
	 * Devolve uma Lista com todos os itens filho do item passado que sejam da
	 * estrutura passada como parï¿½metro, ordenados conforme a ordem de
	 * apresentaï¿½ï¿½o das colunas (campos) da lista de colunas.
	 * 
	 * @author aleixo
	 * @since 26/06/2007
	 * @param item
	 * @param estrutura
	 * @param colunas
	 * @return List
	 * @throws ECARException
	 */
	public List getItensFilho(ItemEstruturaIett item, EstruturaEtt estrutura, List colunas) throws ECARException {
		List lista = new ArrayList();
		List<OrdenacaoIett> listaOrdem = new ArrayList<OrdenacaoIett>();

		Set itensFilhos = (item != null) ? item.getItemEstruturaIetts() : estrutura.getItemEstruturaIetts();
		if (itensFilhos != null && !itensFilhos.isEmpty()) {
			int tamanho = this.getTamanhoMaximoCampo(colunas, itensFilhos);

			for (Iterator it = itensFilhos.iterator(); it.hasNext();) {
				ItemEstruturaIett iett = (ItemEstruturaIett) it.next();

				if (!iett.getEstruturaEtt().equals(estrutura)) {
					continue;
				}

				OrdenacaoIett ordem = new OrdenacaoIett();
				ordem.setIett(iett);

				String campo = "";
				// percorre as colunas
				if (colunas != null && !colunas.isEmpty()) {

					for (Iterator it2 = colunas.iterator(); it2.hasNext();) {
						ObjetoEstrutura atb = (ObjetoEstrutura) it2.next();
						String valor = "";
						// String valor =
						// this.getValorAtributoItemEstrutura(iett,
						// atb.iGetNome(), atb.iGetNomeFk());

						if ("nivelPlanejamento".equals(atb.iGetNome())) {
							String niveis = "";
							if (iett.getItemEstruturaNivelIettns() != null && !iett.getItemEstruturaNivelIettns().isEmpty()) {
								Iterator itNiveis = iett.getItemEstruturaNivelIettns().iterator();
								while (itNiveis.hasNext()) {
									SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
									niveis += nivel.getDescricaoSatb() + "; ";
								}
								niveis = niveis.substring(0, niveis.lastIndexOf(";"));
							}
							valor = niveis;

						} else if (atb.iGetGrupoAtributosLivres() != null) {
							Iterator itIettSatbs = iett.getItemEstruturaSisAtributoIettSatbs().iterator();
							String informacaoIettSatb = "";
							while (itIettSatbs.hasNext()) {
								ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoIettSatb = (ItemEstruturaSisAtributoIettSatb) itIettSatbs.next();

								if (itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getSisGrupoAtributoSga().equals(atb.iGetGrupoAtributosLivres())) {
									if (atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT))
											|| atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA))
											|| atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO))
											|| atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO))) {

										informacaoIettSatb = informacaoIettSatb + itemEstruturaSisAtributoIettSatb.getInformacao() + "; ";

									} else if (!atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
										// se for do tipo imagem nï¿½o faz nada,
										// deixa em branco.
										informacaoIettSatb = informacaoIettSatb + itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getDescricaoSatb() + "; ";
									}
								}
							}
							if (informacaoIettSatb.length() > 0) {
								informacaoIettSatb = informacaoIettSatb.substring(0, informacaoIettSatb.length() - 2);
							}
							valor = informacaoIettSatb;
						} else {
							valor = this.getValorAtributoItemEstrutura(iett, atb.iGetNome(), atb.iGetNomeFk());
						}

						Date data = Data.parseDate(valor, "dd/MM/yyyy");
						if (data != null) { // ï¿½ campo data!
							// Conseguiu converter para objeto Date!!!
							int d = Data.getDia(data);
							int m = Data.getMes(data) + 1;
							int a = Data.getAno(data);
							String dia = (d < 10) ? "0" + String.valueOf(d) : String.valueOf(d);
							String mes = (m < 10) ? "0" + String.valueOf(m) : String.valueOf(m);
							String ano = String.valueOf(a);

							valor = ano + mes + dia;
						}

						// campo += this.completarParaOrdenacao(valor, tamanho);
						campo = valor;
						break;
						// campo = iett.getNomeIett();
					}
				} else {
					// campo = this.completarParaOrdenacao(iett.getNomeIett(),
					// tamanho);
					campo = iett.getNomeIett();
					break;
				}

				ordem.setCampoOrdenar(Util.retiraAcentuacao(campo.toLowerCase().trim()));

				listaOrdem.add(ordem);
			}

		}
		// Ordenando pelo campo de ordenaï¿½ï¿½o
		Collections.sort(listaOrdem, new Comparator() {

			public int compare(Object arg0, Object arg1) {
				OrdenacaoIett o1 = (OrdenacaoIett) arg0;
				OrdenacaoIett o2 = (OrdenacaoIett) arg1;

				String o1String = o1.getCampoOrdenar().toLowerCase().replace("ï¿½", "a").replace("ï¿½", "e").replace("ï¿½", "i").replace("ï¿½", "o").replace("ï¿½", "u").replace("ï¿½", "a")
						.replace("ï¿½", "e").replace("ï¿½", "o").replace("ï¿½", "a").replace("ï¿½", "o").replace("ï¿½", "u").replace("ï¿½", "a");
				String o2String = o2.getCampoOrdenar().toLowerCase().replace("ï¿½", "a").replace("ï¿½", "e").replace("ï¿½", "i").replace("ï¿½", "o").replace("ï¿½", "u").replace("ï¿½", "a")
						.replace("ï¿½", "e").replace("ï¿½", "o").replace("ï¿½", "a").replace("ï¿½", "o").replace("ï¿½", "u").replace("ï¿½", "a");

				return o1String.compareTo(o2String);
			}

		});

		for (OrdenacaoIett o : listaOrdem) {
			lista.add(o.getIett());
		}

		return lista;
	}

	/**
	 * Devolve uma Lista com todos os itens filho do item passado que sejam da
	 * estrutura passada como parï¿½metro, ordenados conforme a ordem de
	 * apresentaï¿½ï¿½o das colunas (campos) da lista de colunas.
	 * 
	 * @author aleixo
	 * @since 26/06/2007
	 * @param estrutura
	 * @param colunas
	 * @param estruturaVirtual
	 * @param seguranca
	 * @return List
	 * @throws ECARException
	 */
	public List getItensAssociados(EstruturaEtt estrutura, List colunas, EstruturaEtt estruturaVirtual, SegurancaECAR seguranca) throws ECARException {
		List lista = new ArrayList();
		List<OrdenacaoIett> listaOrdem = new ArrayList<OrdenacaoIett>();
		Map mapItensEstruturaVirtual = null;
		ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
		ActionEstrutura action = new ActionEstrutura();

		// recupera os itens associados a uma estrutura virtual que pertencem a
		// uma determinada estrutura real
		Set<ItemEstruturaIett> itensFilhos = action.getItensComPermissaoPorEstruturaReal(estrutura, estruturaVirtual, seguranca);

		if (itensFilhos != null && !itensFilhos.isEmpty()) {
			int tamanho = this.getTamanhoMaximoCampo(colunas, itensFilhos);

			for (Iterator it = itensFilhos.iterator(); it.hasNext();) {
				ItemEstruturaIett iett = (ItemEstruturaIett) it.next();

				if (!iett.getEstruturaEtt().equals(estrutura)) {
					continue;
				}

				OrdenacaoIett ordem = new OrdenacaoIett();
				ordem.setIett(iett);

				String campo = "";
				// percorre as colunas
				if (colunas != null && !colunas.isEmpty()) {

					for (Iterator it2 = colunas.iterator(); it2.hasNext();) {
						ObjetoEstrutura atb = (ObjetoEstrutura) it2.next();
						String valor = "";
						// String valor =
						// this.getValorAtributoItemEstrutura(iett,
						// atb.iGetNome(), atb.iGetNomeFk());

						if ("nivelPlanejamento".equals(atb.iGetNome())) {
							String niveis = "";
							if (iett.getItemEstruturaNivelIettns() != null && !iett.getItemEstruturaNivelIettns().isEmpty()) {
								Iterator itNiveis = iett.getItemEstruturaNivelIettns().iterator();
								while (itNiveis.hasNext()) {
									SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
									niveis += nivel.getDescricaoSatb() + "; ";
								}
								niveis = niveis.substring(0, niveis.lastIndexOf(";"));
							}
							valor = niveis;

						} else if (atb.iGetGrupoAtributosLivres() != null) {
							Iterator itIettSatbs = iett.getItemEstruturaSisAtributoIettSatbs().iterator();
							String informacaoIettSatb = "";
							while (itIettSatbs.hasNext()) {
								ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoIettSatb = (ItemEstruturaSisAtributoIettSatb) itIettSatbs.next();

								if (itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getSisGrupoAtributoSga().equals(atb.iGetGrupoAtributosLivres())) {
									if (atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT))
											|| atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA))
											|| atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO))
											|| atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO))) {

										informacaoIettSatb = informacaoIettSatb + itemEstruturaSisAtributoIettSatb.getInformacao() + "; ";

									} else if (!atb.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
										// se for do tipo imagem nï¿½o faz nada,
										// deixa em branco.
										informacaoIettSatb = informacaoIettSatb + itemEstruturaSisAtributoIettSatb.getSisAtributoSatb().getDescricaoSatb() + "; ";
									}
								}
							}
							if (informacaoIettSatb.length() > 0) {
								informacaoIettSatb = informacaoIettSatb.substring(0, informacaoIettSatb.length() - 2);
							}
							valor = informacaoIettSatb;
						} else {
							valor = this.getValorAtributoItemEstrutura(iett, atb.iGetNome(), atb.iGetNomeFk());
						}

						Date data = Data.parseDate(valor, "dd/MM/yyyy");
						if (data != null) { // ï¿½ campo data!
							// Conseguiu converter para objeto Date!!!
							int d = Data.getDia(data);
							int m = Data.getMes(data) + 1;
							int a = Data.getAno(data);
							String dia = (d < 10) ? "0" + String.valueOf(d) : String.valueOf(d);
							String mes = (m < 10) ? "0" + String.valueOf(m) : String.valueOf(m);
							String ano = String.valueOf(a);

							valor = ano + mes + dia;
						}

						campo += this.completarParaOrdenacao(valor, tamanho);
					}
				} else {
					campo = this.completarParaOrdenacao(iett.getNomeIett(), tamanho);
				}

				ordem.setCampoOrdenar(Util.retiraAcentuacao(campo.toLowerCase()));

				listaOrdem.add(ordem);
			}

		}
		// Ordenando pelo campo de ordenaï¿½ï¿½o
		Collections.sort(listaOrdem, new Comparator() {

			public int compare(Object arg0, Object arg1) {
				OrdenacaoIett o1 = (OrdenacaoIett) arg0;
				OrdenacaoIett o2 = (OrdenacaoIett) arg1;

				return o1.getCampoOrdenar().compareTo(o2.getCampoOrdenar());
			}

		});

		for (OrdenacaoIett o : listaOrdem) {
			lista.add(o.getIett());
		}

		return lista;
	}

	/**
	 * Retorna o tamanho mï¿½ximo da string dos valores de uma lista de campos
	 * numa lista de itens.
	 * 
	 * @author aleixo
	 * @since 26/06/2007
	 * @param colunas
	 * @param itens
	 * @return
	 * @throws ECARException
	 */
	public int getTamanhoMaximoCampo(List colunas, Set itens) throws ECARException {
		int tam = 0;
		for (Iterator it = itens.iterator(); it.hasNext();) {
			ItemEstruturaIett item = (ItemEstruturaIett) it.next();

			if (colunas != null && !colunas.isEmpty()) {
				for (Iterator it2 = colunas.iterator(); it2.hasNext();) {
					ObjetoEstrutura atb = (ObjetoEstrutura) it2.next();
					String valor = this.getValorAtributoItemEstrutura(item, atb.iGetNome(), atb.iGetNomeFk());

					if (valor != null) {
						if (valor.length() > tam) {
							tam = valor.length();
						}
					}
				}
			} else {
				String valor = item.getNomeIett();
				if (valor != null) {
					if (valor.length() > tam) {
						tam = valor.length();
					}
				}
			}

		}
		return tam;
	}

	/**
	 * 
	 * @param lItens
	 * @param indConclusao
	 * @return List
	 */
	public List getItensIndConclusao(List lItens, String indConclusao) {
		ArrayList<ItemEstruturaIett> lItensIndConclusao = new ArrayList<ItemEstruturaIett>();
		Iterator itLista = lItens.iterator();
		if ("T".equals(indConclusao))
			return lItens;
		else {
			while (itLista.hasNext()) {
				ItemEstruturaIett item = (ItemEstruturaIett) itLista.next();
				if ("C".equals(indConclusao)) {
					if (item.getSituacaoSit() != null && "S".equals(item.getSituacaoSit().getIndConcluidoSit())) {
						lItensIndConclusao.add(item);
					}
				} else {
					if (item.getSituacaoSit() == null || !"S".equals(item.getSituacaoSit().getIndConcluidoSit().toString())) {
						lItensIndConclusao.add(item);
					}
				}
			}
		}
		return lItensIndConclusao;
	}

	/**
	 * Select para descobrir os itens selecionï¿½veis. Item Selecionï¿½vel ï¿½ o
	 * que: (1) Possui ind_monitoramento = 'S' caso a seleï¿½ï¿½o seja por itens
	 * EM MONITORAMENTO ou possui ï¿½rgï¿½o Responavel 1 ou 2 igual ao
	 * ï¿½rgï¿½ao escolhido em um seleï¿½ï¿½o POR ï¿½RGï¿½O e (2) Possui alguma
	 * funï¿½ï¿½o de acompanhamento que ind_emite_posicao = 'S' ou
	 * informa_andamento = 'S'
	 * 
	 * OU
	 * 
	 * (3) Possui quantidade Prevista Cadstrada e (4) O item ainda nao terminou
	 * (data atual <= data de termino)
	 * 
	 * ALGORITMO: X = itens do select; Z = { }; para cada i em X { L = i sem os
	 * filhos + ascendentes de i sem os filhos (caso jï¿½ nao tenha limpado os
	 * filhos); aux = L.primeiroDaLista; ultimo = null; enquanto ( aux != null )
	 * { filhos = aux.getFilhos(); se ( !filhos.contains( ultimo ) ){
	 * filhos.add(ultimo) aux.setFilhos(filhos); } ultimo = aux; aux =
	 * aux.getPai(); } se (! Z.cotains(ultimo) ) Z.add(ultimo); } return X, Z
	 * 
	 * Mostra em tela todos de Z Item ï¿½ selecionavel se estï¿½ contido em X. <br>
	 * 
	 * @author N/C, rogerio
	 * @since N/C
	 * @version 0.2, 19/03/2007
	 * @param OrgaoOrg
	 *            orgao
	 * @param AcompReferenciaAref
	 *            acompReferencia
	 * @param List
	 *            listNiveis
	 * @param String
	 *            indMonitoramento
	 * @return List
	 * @throws ECARException
	 */

	private List getItensSelecionaveisGeracaoPeriodoReferencia(OrgaoOrg orgao, AcompReferenciaAref acompReferencia, List listNiveis, String indMonitoramento, boolean acessoSomenteUsuariosOrgaos)
			throws ECARException {

		try {
			StringBuilder query = new StringBuilder("select distinct item from ItemEstruturaIett as item").append(" join item.itemEstUsutpfuacIettutfas as itemFuncao");

			query.append(" left join item.itemEstrtIndResulIettrs as indResultados");
			query.append(" where ");
			query.append(" item.indAtivoIett = 'S' AND ");
			// se a estrutura do item estiver ativa
			query.append(" item.estruturaEtt.indAtivoEtt = 'S' AND ");
			query.append(" (itemFuncao.tipoFuncAcompTpfa.indInformaAndamentoTpfa='S' ");

			query.append("   OR itemFuncao.tipoFuncAcompTpfa.indEmitePosicaoTpfa='S')");

			// FIXME: Nï¿½o funcionou no Hibernate 3
			// query.append(" AND (item.situacaoSit is null ");
			// query.append("       OR (item.situacaoSit is not null AND item.situacaoSit.indConcluidoSit != 'S'))");;

			if (!"".equals(indMonitoramento)) {
				if (("N").equals(indMonitoramento.toUpperCase())) {
					query.append(" AND (item.indMonitoramentoIett = :indMonitoramento OR item.indMonitoramentoIett is null)");
				} else
					query.append(" AND item.indMonitoramentoIett = :indMonitoramento");
			}

			List listaCodSatb = new ArrayList();
			if (listNiveis != null && listNiveis.size() > 0) {
				query.append(" AND (");
				// int cont = 0;

				Iterator itNiveis = listNiveis.iterator();
				while (itNiveis.hasNext()) {
					SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();

					listaCodSatb.add(nivel.getCodSatb());
					// query.append("niveis = " +
					// nivel.getCodSatb().toString());

					// if((cont + 1) < listNiveis.size()) {
					// query.append(" OR ");
					// }
					// cont++;
				}
				query.append("item.itemEstruturaNivelIettns.codSatb in (:listaNiveis) ");
				query.append(") ");
			}

			// Por falta de documentaï¿½ï¿½o da condiï¿½ï¿½o abaixo,
			// foi alterado o cï¿½digo para funcionar corretamente quando nï¿½o
			// for separado por orgï¿½o
			if (acompReferencia.getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals(Dominios.SIM)) {

				if (orgao != null) {
					query.append(" AND item.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg = :codOrg");
				} else {

					// if(orgao == null) E (tipoAcompanhamentoSeparadoPorOrgao
					// == SIM) E (referencia Sï¿½ com itens SEM INFORMACAO)
					if ((acompReferencia.getTipoAcompanhamentoTa().getIndSepararOrgaoTa() != null && acompReferencia.getTipoAcompanhamentoTa().getIndSepararOrgaoTa().equals(Dominios.SIM)
							&& acompReferencia.getIndInformacaoOrgaoAref() != null && acompReferencia.getIndInformacaoOrgaoAref().equals("N"))
							// ou se a opï¿½ï¿½o for todos, inclui os itens sem
							// informaï¿½ï¿½o
							|| !acessoSomenteUsuariosOrgaos) {
						query.append(" AND item.orgaoOrgByCodOrgaoResponsavel1Iett.codOrg is null");
					}
				}
			}

			Query queryItens = this.getSession().createQuery(query.toString());

			if (!"".equals(indMonitoramento)) {
				queryItens.setString("indMonitoramento", indMonitoramento);
			}

			if (orgao != null) {
				queryItens.setLong("codOrg", orgao.getCodOrg().longValue());
			}

			if (!listaCodSatb.isEmpty()) {
				queryItens.setParameterList("listaNiveis", listaCodSatb);
			}

			List itens = queryItens.list();

			for (Iterator itItens = itens.iterator(); itItens.hasNext();) {
				ItemEstruturaIett iett = (ItemEstruturaIett) itItens.next();
				if (iett.getSituacaoSit() != null && ("S".equals(iett.getSituacaoSit().getIndConcluidoSit()) || "S".equals(iett.getSituacaoSit().getIndSemAcompanhamentoSit()))) {
					itItens.remove();
				} else {
					// verificar o pai do item (Mantis: 7507)

					ItemEstruturaIett itemPai = iett.getItemEstruturaIett();
					if (itemPai != null
							&& (itemPai.getSituacaoSit() != null && ("S".equals(itemPai.getSituacaoSit().getIndConcluidoSit()) || "S".equals(itemPai.getSituacaoSit().getIndSemAcompanhamentoSit())))) {
						itItens.remove();
					}
				}

			}

			// Mantis 5257: Filtrar os itens conforme associaï¿½ï¿½o de
			// funï¿½ï¿½o de acompanhamento (obrigatï¿½ria/opcional) e tipo de
			// acompanhamento.
			// Gerar acompanhamento para as funï¿½ï¿½es obrigatï¿½rias e
			// opcionais se o item possuir a(s) funï¿½ï¿½o(ï¿½es)
			// obrigatï¿½ria(s)
			// de acordo com o tipo de acompanhamento

			List<TipoFuncAcompTpfa> funcaoAcompanhamentoObrigatoria = new ArrayList<TipoFuncAcompTpfa>();
			List<TipoFuncAcompTpfa> funcaoAcompanhamentoOpcional = new ArrayList<TipoFuncAcompTpfa>();

			TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa) buscar(TipoAcompanhamentoTa.class, acompReferencia.getTipoAcompanhamentoTa().getCodTa());

			if (tipoAcompanhamento != null) {

				for (Iterator it = tipoAcompanhamento.getTipoAcompFuncAcompTafcs().iterator(); it.hasNext();) {
					TipoAcompFuncAcompTafc tafc = (TipoAcompFuncAcompTafc) it.next();
					if ("S".equalsIgnoreCase(tafc.getIndObrigatorio())) {
						funcaoAcompanhamentoObrigatoria.add(tafc.getTipoFuncAcompTpfa());
					} else if ("S".equalsIgnoreCase(tafc.getIndOpcional())) {
						funcaoAcompanhamentoOpcional.add(tafc.getTipoFuncAcompTpfa());
					}
				}
			}

			if (!funcaoAcompanhamentoObrigatoria.isEmpty() || !funcaoAcompanhamentoOpcional.isEmpty()) {
				Iterator itItens = itens.iterator();
				while (itItens.hasNext()) {
					ItemEstruturaIett iett = (ItemEstruturaIett) itItens.next();

					List<TipoFuncAcompTpfa> tpfaIett = new ArrayList<TipoFuncAcompTpfa>();

					if (iett.getItemEstUsutpfuacIettutfas() != null) {
						Iterator it = iett.getItemEstUsutpfuacIettutfas().iterator();
						while (it.hasNext()) {
							ItemEstUsutpfuacIettutfa iettutfa = (ItemEstUsutpfuacIettutfa) it.next();

							tpfaIett.add(iettutfa.getTipoFuncAcompTpfa());
						}
					}

					if (!funcaoAcompanhamentoObrigatoria.isEmpty()) {
						if (!tpfaIett.containsAll(funcaoAcompanhamentoObrigatoria)) {
							itItens.remove();
						}

						// como o item possui todas as funï¿½ï¿½es de
						// acompanhamento obrigatï¿½rias ignorar a regra de
						// opcionais
						continue;
					}

					if (!funcaoAcompanhamentoOpcional.isEmpty()) {
						boolean validacaoOpcionalOk = false;
						Iterator it = tpfaIett.iterator();

						while (it.hasNext()) {
							TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa) it.next();

							if (funcaoAcompanhamentoOpcional.contains(tpfa)) {
								validacaoOpcionalOk = true;

								break;
							}
						}

						if (!validacaoOpcionalOk) {
							itItens.remove();
						}
					}
				}
			}

			if (acompReferencia.getCodAref() != null) {
				List itensBanco = new AcompReferenciaItemDao(request).getListaItensAcompanhamento(acompReferencia);
				if (itensBanco != null) {
					Iterator it = itensBanco.iterator();
					while (it.hasNext()) {
						ItemEstruturaIett itemBanco = (ItemEstruturaIett) it.next();
						if (!itens.contains(itemBanco))
							itens.add(itemBanco);
					}
				}

			}

			return itens;
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}

	}

	/**
	 * Mï¿½todo devolve duas listas para serem usadas na construï¿½ï¿½o da tela
	 * de seleï¿½ï¿½o de Itens da Estrutura para Geraï¿½ï¿½o de Perï¿½odo de
	 * Referï¿½ncia. A primiera com os itens que sï¿½o selecionï¿½veis, ou seja,
	 * permitem acompanhamento) e outra com os que devem ser mostrados em tela
	 * para a construï¿½ï¿½o da hierarquia correta (inclui os selecionaveis). <br>
	 * 
	 * @author N/C, rogerio
	 * @since N/C
	 * @version 0.2, 19/03/2007
	 * @param orgao
	 * @param acompReferencia
	 * @param tipoAcesso
	 * @param gruposUsuario
	 * @param listNiveis
	 * @return List[]
	 * @throws ECARException
	 */
	public List[] getItensGerarAcompanhamento(OrgaoOrg orgao, AcompReferenciaAref acompReferencia, Set gruposUsuario, String tipoAcesso, List listNiveis, boolean acessoSomenteUsuariosOrgaos)
			throws ECARException {

		ValidaPermissao validaPermissao = new ValidaPermissao();
		List[] retorno = new List[2];

		String indMonitoramento = "";

		if ("S".equals(acompReferencia.getTipoAcompanhamentoTa().getIndMonitoramentoTa()) && "N".equals(acompReferencia.getTipoAcompanhamentoTa().getIndNaoMonitoramentoTa())) {
			indMonitoramento = "S";
		} else if ("N".equals(acompReferencia.getTipoAcompanhamentoTa().getIndMonitoramentoTa()) && "S".equals(acompReferencia.getTipoAcompanhamentoTa().getIndNaoMonitoramentoTa())) {
			indMonitoramento = "N";
		}

		// Mï¿½todo exclui da lista de selecionï¿½veis o itens que o usuï¿½rio
		// nï¿½o tem acesso
		List selecionaveis = getItensSelecionaveisGeracaoPeriodoReferencia(orgao, acompReferencia, listNiveis, indMonitoramento, acessoSomenteUsuariosOrgaos);
		selecionaveis = this.getItensSelecionaveisFiltradosPorAtributo(selecionaveis, acompReferencia.getTipoAcompanhamentoTa());
		retorno[0] = getItensOrdenados(selecionaveis, acompReferencia.getTipoAcompanhamentoTa());
		retorno[1] = selecionaveis;
		return retorno;
	}

	/**
	 * Ordena uma lista de objetos item estrutura, fazendo com que os itens no
	 * mesmo nivel da hierarquia fiquem organizados alfabeticamente
	 * 
	 * @param itens
	 */
	public void ordenaListaItemEstrutura(List itens) {

		Collections.sort(itens, new Comparator() {
			public int compare(Object o1, Object o2) {
				ItemEstruturaIett item1 = (ItemEstruturaIett) o1;
				ItemEstruturaIett item2 = (ItemEstruturaIett) o2;
				if (getAscendenteMaximo(item1).equals(getAscendenteMaximo(item2))) {
					if (item1.getNivelIett().intValue() == item2.getNivelIett().intValue()) {
						return item1.getNomeIett().compareToIgnoreCase(item2.getNomeIett());
					} else {
						return item1.getNivelIett().intValue() - item2.getNivelIett().intValue();
					}
				} else {
					if (item1.getNivelIett().intValue() == item2.getNivelIett().intValue()) {
						return item1.getNomeIett().compareToIgnoreCase(item2.getNomeIett());
					} else {
						return getAscendenteMaximo(item1).getNomeIett().compareToIgnoreCase(getAscendenteMaximo(item2).getNomeIett());
					}
				}
			}
		});

	}

	/**
	 * Recebe uma lista de itens e adiciona na lista outros itens de modo a
	 * completar a hierarquia necessï¿½ria para apresentaï¿½ï¿½o da ï¿½rvore de
	 * itens em tela Se itemPai for utilizado, ï¿½ montada a ï¿½rvore atï¿½ seu
	 * nï¿½vel. Caso nï¿½o seja necessï¿½rio passar itemPai = null
	 * 
	 * @param itens
	 * @param itemPai
	 * @return
	 * @throws ECARException
	 */
	public ArrayList getArvoreItens(List<ItemEstruturaIett> itens, ItemEstruturaIett itemPai) throws ECARException {
		Set<ItemEstruturaIett> retorno = new LinkedHashSet<ItemEstruturaIett>();
		for (ItemEstruturaIett item : itens) {
			List<ItemEstruturaIett> ascendentes = new ArrayList<ItemEstruturaIett>();
			if (itemPai != null) {
				ascendentes.addAll(getAscendentes(item, itemPai));
			} else {
				ascendentes.addAll(getAscendentes(item));
			}
			ascendentes.add(item);
			retorno.addAll(ascendentes);
		}
		return new ArrayList<ItemEstruturaIett>(retorno);
	}

	public ArrayList getArvoreItensFromAris(List<AcompReferenciaItemAri> itens, ItemEstruturaIett itemPai) throws ECARException {
		Set<ItemEstruturaIett> retorno = new LinkedHashSet<ItemEstruturaIett>();
		for (AcompReferenciaItemAri item : itens) {
			List<ItemEstruturaIett> ascendentes = new ArrayList<ItemEstruturaIett>();
			if (itemPai != null) {
				ascendentes.addAll(getAscendentes(item.getItemEstruturaIett(), itemPai));
			} else {
				ascendentes.addAll(getAscendentes(item.getItemEstruturaIett()));
			}
			ascendentes.add(item.getItemEstruturaIett());
			retorno.addAll(ascendentes);
		}
		return new ArrayList<ItemEstruturaIett>(retorno);
	}

	/**
	 * 
	 * @param itens
	 * @param itemPai
	 * @return List
	 * @throws ECARException
	 */
	public List getArvoreItensPesquisaEstrutura(List itens, ItemEstruturaIett itemPai) throws ECARException {
		List itensTemp = new ArrayList(itens);

		Set retorno = new HashSet();
		Iterator it = itens.iterator();

		while (it.hasNext()) {
			ItemEstruturaIett item = (ItemEstruturaIett) it.next();

			/*
			 * Foi utilizado um refresh no item, pois quando utilizado com a
			 * pesquisa na estrutura depois de passada a lista para montar a
			 * ï¿½rvore, gerava erro de lazy, alguns filhos jï¿½ nï¿½o estavam
			 * persistentes.
			 */
			try {
				this.session.refresh(item);
			} catch (HibernateException e) {
				this.logger.error(e);
				throw new ECARException(e);
			}

			List ascendentes = new ArrayList();

			if (itemPai != null) {
				ascendentes.addAll(getAscendentes(item, itemPai));
			} else {
				ascendentes.addAll(getAscendentes(item));
			}

			ascendentes.add(item);

			retorno.addAll(ascendentes);
		}

		List itensParaExibicao = this.getItensOrdenadosPorSiglaIett(new ArrayList(retorno));

		boolean remover = true;

		while (remover) {

			remover = false;
			int maiorNivelSemOcorrencia = 0;
			Iterator itTodosItens = itensParaExibicao.iterator();

			while (itTodosItens.hasNext()) {
				ItemEstruturaIett iett = (ItemEstruturaIett) itTodosItens.next();

				int nivel = iett.getNivelIett().intValue();

				if (nivel > maiorNivelSemOcorrencia) {
					if (!itensTemp.contains(iett)) {
						maiorNivelSemOcorrencia = nivel;
					}
				}
			}

			// remover os itens do ï¿½ltimo nï¿½vel que nï¿½o apresentam a
			// ocorrï¿½ncia
			itTodosItens = itensParaExibicao.iterator();
			while (itTodosItens.hasNext()) {
				ItemEstruturaIett iett = (ItemEstruturaIett) itTodosItens.next();

				if (iett.getNivelIett().intValue() == maiorNivelSemOcorrencia) {
					if (!itensTemp.contains(iett)) {

						boolean possuiFilhoComOcorrencia = false;

						Iterator itDescendentes = getDescendentes(iett, true).iterator();

						while (itDescendentes.hasNext()) {
							ItemEstruturaIett iettFilho = (ItemEstruturaIett) itDescendentes.next();
							if (itensTemp.contains(iettFilho)) {
								possuiFilhoComOcorrencia = true;
								break;
							}
						}

						if (!possuiFilhoComOcorrencia) {
							itTodosItens.remove();
							remover = true;
						}
					}
				}
			}
		}

		return itensParaExibicao;
	}

	/**
	 * Devolve uma lista ordenada de nomes das imagens de nivel de planejamento.
	 * 
	 * @author felipev
	 * @param item
	 *            Item da estrutura
	 * @return List lista de Strings que representam o nome da imagem
	 */
	public List getNomeImgsNivelPlanejamentoItem(ItemEstruturaIett item) {

		List retorno = new ArrayList();
		List niveisAcesso = this.getNivelAcesso(item);
		if (niveisAcesso != null) {
			Iterator itNiveis = niveisAcesso.iterator();
			while (itNiveis.hasNext()) {
				SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
				/*
				 * comentado por causa do bug 880
				 * 
				 * retorno.add("icon_" +
				 * Util.retiraAcentuacao(nivel.getDescricaoSatb().toLowerCase())
				 * + ".png");
				 */
				retorno.add(nivel.getAtribInfCompSatb()); // o nome da imagem
															// estï¿½ aqui
			}
		}
		return retorno;
	}

	/**
	 * Devolve uma lista ordenada de nomes das imagens de nivel de planejamento,
	 * agrupadas.<br>
	 * Os nomes estï¿½o encapsulados utilizando o bean
	 * NomeImgsNivelPlanejamentoBean.
	 * 
	 * @author garten, aleixo
	 * @since N/I
	 * @version 0.2 - 21/05/2007; 0.1 - N/I
	 * @param item
	 *            Item da estrutura
	 * @return List lista de NomeImgsNivelPlanejamentoBean que contï¿½m o nome e
	 *         o title da imagem
	 */
	public List getNomeImgsNivelPlanejamentoItemAgrupado(ItemEstruturaIett item) {

		/*
		 * Especificaï¿½ï¿½o como consta no bug #880
		 * 
		 * Procedimentos da rotina que mostra a imagem no relatï¿½rio de
		 * acompanhamento: 1. Para cada registro de item da estrutura selecionar
		 * todos os atributos do nivel de planejamento relacionados; 2. Ordenar
		 * os atributos pelos 2 primeiros dï¿½gitos dentro de cada item 3.
		 * Agrupar pelo primeiro dï¿½gito; 4. Montar o nome da imagem (#nome)
		 * concatenando os demais caracteres no campo de informaï¿½ï¿½es
		 * complementares (xxxxx) 5. Prever: Estratï¿½gico + Tï¿½tico,
		 * Estratï¿½gico + Operacional, Tï¿½tico + Operacional e ainda
		 * Estratï¿½gico + Tï¿½tico + Operacional. 6. Montar os nomes das
		 * imagens com os caracteres "icon_", mais o #nome, mais os caracteres
		 * ".PNG".
		 */

		/*
		 * algoritmo
		 * 
		 * enquanto existir niveis de planejamento controlar os grupos. se
		 * trocar de grupo, guarda o nome da imagem na lista nome da imagem <-
		 * pedaï¿½o do nome contido em inf. complementares ex. 11est.png (est)
		 * fim-enquanto
		 */

		List retorno = new ArrayList();
		try {
			List niveisAcesso = this.getNivelAcesso(item);
			if (niveisAcesso != null && !niveisAcesso.isEmpty()) {

				String grupoAnterior = null;
				String grupoAtual = null;
				StringBuilder nomeImagem = null;
				StringBuilder titleImagem = null;
				String title = "";
				int ultimaVirgula = 0;
				int tamanho = 0;

				for (Iterator it = niveisAcesso.iterator(); it.hasNext();) {
					SisAtributoSatb atributo = (SisAtributoSatb) it.next();
					grupoAtual = atributo.getAtribInfCompSatb().substring(5, 6); // icon_x
					if (!grupoAtual.equals(grupoAnterior)) {
						if (grupoAnterior != null) {
							title = String.valueOf(titleImagem.toString()).trim();
							ultimaVirgula = title.lastIndexOf(',');
							tamanho = title.length() - 1;
							if (ultimaVirgula == tamanho) {
								title = title.substring(0, ultimaVirgula);
							}
							NomeImgsNivelPlanejamentoBean nomeImgBean = new NomeImgsNivelPlanejamentoBean(nomeImagem.append(".png").toString(), title);
							retorno.add(nomeImgBean);
						}

						grupoAnterior = grupoAtual;
						nomeImagem = new StringBuilder("icon");
						titleImagem = new StringBuilder();
					}

					nomeImagem.append("_");
					nomeImagem.append(atributo.getAtribInfCompSatb().substring(7, atributo.getAtribInfCompSatb().indexOf('.'))); // {est
																																	// |
																																	// ope
																																	// |
																																	// tat
																																	// |
																																	// ...}

					titleImagem.append(atributo.getDescricaoSatb()).append(", ");

				}

				if (nomeImagem != null && titleImagem != null) {
					title = String.valueOf(titleImagem.toString()).trim();
					ultimaVirgula = title.lastIndexOf(',');
					tamanho = title.length() - 1;
					if (ultimaVirgula == tamanho) {
						title = title.substring(0, ultimaVirgula);
					}
					NomeImgsNivelPlanejamentoBean nomeImgBean = new NomeImgsNivelPlanejamentoBean(nomeImagem.append(".png").toString(), title);
					retorno.add(nomeImgBean);
				} else {
					NomeImgsNivelPlanejamentoBean nomeImgBean = new NomeImgsNivelPlanejamentoBean("", "");
					retorno.add(nomeImgBean);
				}
			}
		} catch (Exception e) {
			// retorno.add("erro.png");
			NomeImgsNivelPlanejamentoBean nomeImgBean = new NomeImgsNivelPlanejamentoBean("erro.png", "");
			retorno.add(nomeImgBean);
			// a exception foi tratada aqui para nï¿½o ocorrer problema ao
			// mostrar a imagem
			this.logger.error("Nï¿½o possï¿½vel montar o nome da imagem: " + e.getMessage());
		}

		return retorno;
	}

	/**
	 * Pesquisa na Estrutura, o mï¿½todo realiza a pesquisa em vï¿½rias
	 * funï¿½ï¿½es do Item, somando todos os resultados encontrados num HashMap,
	 * indicando em quais funï¿½ï¿½es foi encontrado o parï¿½metro de pesquisa.
	 * 
	 * @param request
	 * @param codUsu
	 *            - cï¿½digo do usuï¿½rio logado
	 * @param application
	 * @return
	 * @throws ECARException
	 */
	public HashMap pesquisarNaEstrutura(HttpServletRequest request, Long codUsu, ServletContext application) throws ECARException {
		HashMap itensMap = new HashMap();
		Mensagem properties = new Mensagem(application);

		// String opcaoPesquisa = Pagina.getParamStr(request, "opcaoPesquisa");
		String palavraChave = Pagina.getParamStr(request, "palavraChave").trim();

		/* Pesquisa nas funï¿½ï¿½es da estrutura */

		/*
		 * Sempre vai pesquisar na estrutura, pq a parte da pesquisa de
		 * informaï¿½ï¿½es foi retirada da tela, referente ao Bug - 621.
		 */

		// if("E".equals(opcaoPesquisa)){

		/*
		 * A data tambï¿½m foi ignorada na tela de pesquisa, portanto na
		 * pesquisa abaixo eu simplesmente passo como NULL para que na pesquisa
		 * a data seja ignorada.
		 */
		// Date data = Pagina.getParamDataBanco(request, "estData");
		String[] estruturas = request.getParameterValues("estruturaEtt");
		String todos = Pagina.getParamStr(request, "estTodos");

		// itensMap = montaMapPesquisaEstrutura(palavraChave, data, estruturas,
		// todos, codUsu, properties);
		itensMap = montaMapPesquisaEstrutura(request, palavraChave, null, estruturas, todos, codUsu, properties);
		// }

		/*
		 * Pesquisa nas informaï¿½ï¿½es do portal --> Foi retirada da tela. Fica
		 * o Comentï¿½rio para posterior informaï¿½ï¿½o.
		 * 
		 * 
		 * if("I".equals(opcaoPesquisa)){ Date dataIni =
		 * Pagina.getParamDataBanco(request, "infDataInicial"); Date dataFim =
		 * Pagina.getParamDataBanco(request, "infDataFinal"); String[]
		 * informacoes = request.getParameterValues("informacoes"); String todos
		 * = Pagina.getParamStr(request, "infTodos");
		 * 
		 * itensMap = montaMapPesquisaInformacoes(palavraChave, dataIni,
		 * dataFim, informacoes, todos); }
		 */

		Iterator it = itensMap.keySet().iterator();
		while (it.hasNext()) {
			ItemEstruturaIett item = (ItemEstruturaIett) it.next();
			try {
				session.refresh(item);
			} catch (HibernateException e) {
				this.logger.error(e);
				throw new ECARException(e);
			}

		}

		return itensMap;
	}

	/**
	 * Mï¿½todo que recebe todos os resultados das pesquisas jï¿½ utilizando-se
	 * de um map e apenas adicionando a partir da primeira
	 * 
	 * @param palavraChave
	 * @param data
	 * @param estruturas
	 * @param todos
	 * @return
	 * @throws ECARException
	 */
	private HashMap montaMapPesquisaEstrutura(HttpServletRequest request, String palavraChave, Date data, String[] estruturas, String todos, Long codUsu, Mensagem properties) throws ECARException {
		HashMap itensMap = new HashMap();

		/* Strings que formarï¿½o o select para cada funcao */
		String select = "select item from ItemEstruturaIett as item ";
		select = select + " left join fetch item.itemEstruturaIetts ";

		String where = "";
		boolean flag = false;

		if (estruturas.length > 0) {
			if (!"todos".equals(todos)) {
				where = " where item.estruturaEtt.codEtt in (";
				flag = true;
				for (int i = 0; i < estruturas.length; i++) {
					where = where + estruturas[i];
					if ((i + 1) < estruturas.length)
						where = where + ",";
				}
				where = where + ")";
			}
		}

		if (flag) {
			where = where + " and item.indAtivoIett = 'S' ";
		} else {
			where = where + " where item.indAtivoIett = 'S' ";
		}

		boolean pesquisaAvancada = ("A".equals(Pagina.getParamStr(request, "tipoPesquisa"))) ? true : false;

		/*
		 * Inicio das pesquisas montando o MAP a partir da lista (resultado da
		 * pesquisa)
		 */

		if (pesquisaAvancada) {
			// Aï¿½ï¿½o
			atualizaMapItens(request, itensMap, pesquisaEstruturaAcao(select, where, palavraChave), properties.getMensagem("item.funcao.acao"), "");

			// Anexos
			atualizaMapItens(request, itensMap, pesquisaEstruturaAnexo(select, where, palavraChave), properties.getMensagem("item.funcao.anexo"), "");

			// Beneficiï¿½rios
			atualizaMapItens(request, itensMap, pesquisaEstruturaBeneficiario(select, where, palavraChave), properties.getMensagem("item.funcao.beneficiario"), "");

			// Critï¿½rios
			atualizaMapItens(request, itensMap, pesquisaEstruturaCriterio(select, where, palavraChave), properties.getMensagem("item.funcao.criterio"), "");

			// Dados Bï¿½sicos
			atualizaMapItens(request, itensMap, pesquisaEstruturaDadosBasicos(select, where, palavraChave, data, pesquisaAvancada), "", "Dados Bï¿½sicos");

			// Entidades
			atualizaMapItens(request, itensMap, pesquisaEstruturaEntidade(select, where, palavraChave, data), properties.getMensagem("item.funcao.entidade"), "");

			// Fonte de Rescurso e Recurso
			atualizaMapItens(request, itensMap, pesquisaEstruturaFonteRecurso(select, where, palavraChave), properties.getMensagem("item.funcao.fonteRecurso"), "");

			// Localizaï¿½ï¿½o
			atualizaMapItens(request, itensMap, pesquisaEstruturaLocalizacao(select, where, palavraChave), properties.getMensagem("item.funcao.localizacao"), "");

			// Marcadores
			atualizaMapItens(request, itensMap, pesquisaEstruturaMarcador(select, where, palavraChave, codUsu, pesquisaAvancada), "", "Marcadores");

			// Pontos Crï¿½ticos
			atualizaMapItens(request, itensMap, pesquisaEstruturaPontoCritico(select, where, palavraChave), properties.getMensagem("item.funcao.pontoCritico"), "");
		} else {
			// Dados Bï¿½sicos
			atualizaMapItens(request, itensMap, pesquisaEstruturaDadosBasicos(select, where, palavraChave, data, pesquisaAvancada), "", "Dados Bï¿½sicos");
		}

		/*
		 * fim das pesquisas
		 * ************************************************************
		 */

		return itensMap;
	}

	/**
	 * Mï¿½todo que recebe o MAP e atualiza a partir da lista passada da
	 * pesquisa Detalhe, se nomeFixo != "", utiliza mï¿½todo que busca o nome da
	 * funï¿½ï¿½o no banco senï¿½o utiliza o nomeFixo
	 * 
	 * @param itensMap
	 * @param lista
	 * @param funcao
	 * @param nomeFixo
	 */
	private void atualizaMapItens(HttpServletRequest request, HashMap itensMap, List lista, String funcao, String nomeFixo) throws ECARException {
		ValidaPermissao validaPermissao = new ValidaPermissao();
		if (lista.size() > 0) {
			Iterator it = lista.iterator();

			// correï¿½ï¿½o do bug #1575
			// Obtem o bean de seguranca para verificar se o usuï¿½rio tem
			// acesso a um determinado item, utilizando as
			// funï¿½ï¿½es definidas na classe de permissï¿½es.
			// Se o usuï¿½rio puder acessar o item, ele ï¿½ incluï¿½do na lista,
			// cc nï¿½o.
			// Essa abordagem parece melhor do que testar os itens apenas na
			// pï¿½gina, pois pode ocorrer de gastar tempo
			// incluindo itens na lista sem considerar permissï¿½es e depois
			// nï¿½o poder mostrar os itens porque o usuï¿½rio nï¿½o tem acesso.
			SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");

			while (it.hasNext()) {
				ItemEstruturaIett item = (ItemEstruturaIett) it.next();

				// somente inclui o item na lista se o usuï¿½rio tem permissoes
				validaPermissao.permissoesItem(item, seguranca.getUsuario(), seguranca.getGruposAcesso());
				if (validaPermissao.permissaoConsultarItem()) {
					Set colecao = new HashSet();
					ItemFuncaoLink funcaoLink = new ItemFuncaoLink();

					if (!"".equals(nomeFixo)) {
						funcaoLink.setNome(nomeFixo);
						FuncaoDao funcaoDao = new FuncaoDao(request);
						FuncaoFun funcaoDadosGerais = funcaoDao.getFuncaoDadosGerais();

						if ("Dados Bï¿½sicos".equals(nomeFixo))
							funcaoLink
									.setLink("../dadosGerais/frm_con.jsp?codIett=" + item.getCodIett().toString() + "&codEttSelecionado=" + item.getEstruturaEtt().getCodEtt().toString() + "&codAba="
											+ funcaoDadosGerais.getCodFun().toString() + "&ultimoIdLinhaDetalhado=" + "iett" + item.getCodIett() + "_pai_ett" + item.getEstruturaEtt().getCodEtt());
						else {
							/*
							 * TODO Definir link de marcadores na pesquisa de
							 * estrutura
							 */
							funcaoLink.setNome("marcadores");
							funcaoLink.setLink("codIett=" + item.getCodIett().toString());
						}
						// "marcadores=" + item.getCodIett().toString()
						// + "&ultimoIdLinhaDetalhado=" + "iett" +
						// item.getCodIett() + "_pai_ett" +
						// item.getEstruturaEtt().getCodEtt());
					} else {
						EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();

						estruturaFuncao = (EstruturaFuncaoEttf) new EstruturaFuncaoDao(request).getLabelFuncao(item.getEstruturaEtt(), Long.valueOf(funcao));

						if (estruturaFuncao != null) {
							funcaoLink.setNome(estruturaFuncao.getLabelEttf());
							funcaoLink.setLink("../" + estruturaFuncao.getFuncaoFun().getLinkFuncaoFun() + "?codAba=" + estruturaFuncao.getFuncaoFun().getCodFun() + "&codIett=" + item.getCodIett()
									+ "&ultimoIdLinhaDetalhado=" + "iett" + item.getCodIett() + "_pai_ett" + item.getEstruturaEtt().getCodEtt());

						} else {
							funcaoLink.setNome("Dados Bï¿½sicos");
							funcaoLink.setLink("../dadosGerais/frm_con.jsp?codIett=" + item.getCodIett().toString() + "&ultimoIdLinhaDetalhado=" + "iett" + item.getCodIett() + "_pai_ett"
									+ item.getEstruturaEtt().getCodEtt());
						}
					}
					if (!itensMap.containsKey(item)) {
						colecao.add(funcaoLink);
						itensMap.put(item, colecao);
					} else {
						colecao.addAll((Collection) itensMap.get(item));
						colecao.add(funcaoLink);
						itensMap.put(item, colecao);
					}
				}
			}
		}
	}

	/**
	 * Realiza a pesquisa na Estrutura do item em itemEstrutAcaoIetta
	 * 
	 * @param itensMap
	 * @param select
	 * @param where
	 * @param palavraChave
	 * @throws ECARException
	 */
	private List pesquisaEstruturaAcao(String select, String where, String palavraChave) throws ECARException {
		try {
			String joinFuncao = " left join item.itemEstrutAcaoIettas as acao";
			String whereFuncao = "";

			if (!"".equals(where))
				whereFuncao = " and";
			else
				whereFuncao = " where";
			whereFuncao = whereFuncao + " lower( acao.descricaoIetta ) like :palavraChave and acao.indAtivoIetta = 'S' ";

			Query query = this.getSession().createQuery(select + joinFuncao + where + whereFuncao);
			query.setString("palavraChave", "%" + palavraChave.toLowerCase() + "%");
			return query.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException();
		}
	}

	/**
	 * Realiza a pesquisa na Estrutura do item em itemEstrutUploadIettup
	 * 
	 * @param itensMap
	 * @param select
	 * @param where
	 * @param palavraChave
	 * @throws ECARException
	 */
	private List pesquisaEstruturaAnexo(String select, String where, String palavraChave) throws ECARException {
		try {
			String joinFuncao = " left join item.itemEstrutUploadIettups as anexo";
			String whereFuncao = "";

			if (!"".equals(where))
				whereFuncao = " and";
			else
				whereFuncao = " where";
			whereFuncao = whereFuncao + " ( lower( anexo.nomeOriginalIettup ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( anexo.descricaoIettup ) like :palavraChave ) and anexo.indAtivoIettup = 'S' ";

			Query query = this.getSession().createQuery(select + joinFuncao + where + whereFuncao);
			query.setString("palavraChave", "%" + palavraChave.toLowerCase() + "%");
			return query.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException();
		}
	}

	/**
	 * Realiza a pesquisa na Estrutura do item em itemEstrtBenefIettb
	 * 
	 * @param itensMap
	 * @param select
	 * @param where
	 * @param palavraChave
	 * @throws ECARException
	 */
	private List pesquisaEstruturaBeneficiario(String select, String where, String palavraChave) throws ECARException {
		try {
			String joinFuncao = " left join item.itemEstrtBenefIettbs as beneficiario";
			String whereFuncao = "";

			if (!"".equals(where))
				whereFuncao = " and";
			else
				whereFuncao = " where";
			whereFuncao = whereFuncao + " ( lower( beneficiario.comentarioIettb ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( beneficiario.beneficiarioBnf.nomeBnf ) like :palavraChave )";

			Query query = this.getSession().createQuery(select + joinFuncao + where + whereFuncao);
			query.setString("palavraChave", "%" + palavraChave.toLowerCase() + "%");
			return query.list();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException();
		}
	}

	/**
	 * Realiza a pesquisa na Estrutura do item em itemEstrutCriterioIettc
	 * 
	 * @param itensMap
	 * @param select
	 * @param where
	 * @param palavraChave
	 * @throws ECARException
	 */
	private List pesquisaEstruturaCriterio(String select, String where, String palavraChave) throws ECARException {
		try {
			String joinFuncao = " left join item.itemEstrutCriterioIettcs as criterio";
			String whereFuncao = "";

			if (!"".equals(where))
				whereFuncao = " and";
			else
				whereFuncao = " where";
			whereFuncao = whereFuncao + " lower( criterio.criterioCri.descricaoCri ) like :palavraChave";

			Query query = this.getSession().createQuery(select + joinFuncao + where + whereFuncao);
			query.setString("palavraChave", "%" + palavraChave.toLowerCase() + "%");
			return query.list();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException();
		}
	}

	/**
	 * Realiza a pesquisa na Estrutura do item em
	 * 
	 * @param itensMap
	 * @param select
	 * @param where
	 * @param palavraChave
	 * @throws ECARException
	 */
	private List pesquisaEstruturaDadosBasicos(String select, String where, String palavraChave, Date data, boolean pesquisaAvancada) throws ECARException {
		try {
			String joinFuncao = " left join item.areaAre as area";
			joinFuncao = joinFuncao + " left join item.subAreaSare as subArea";
			joinFuncao = joinFuncao + " left join item.orgaoOrgByCodOrgaoResponsavel1Iett as orgao1";
			joinFuncao = joinFuncao + " left join item.orgaoOrgByCodOrgaoResponsavel2Iett as orgao2";

			if (pesquisaAvancada) {
				joinFuncao = joinFuncao + " left join item.itemEstrutUsuarioIettusesByCodIett as editorLeitor";
			}

			joinFuncao = joinFuncao + " left join item.itemEstUsutpfuacIettutfas as funcaoAcomp";
			joinFuncao = joinFuncao + " left join funcaoAcomp.usuarioUsu as usuario";

			String whereFuncao = "";

			if (!"".equals(where))
				whereFuncao = " and";
			else
				whereFuncao = " where";

			whereFuncao = whereFuncao + " ( lower( item.nomeIett ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( item.siglaIett ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( item.descricaoIett ) like :palavraChave or";
			// whereFuncao = whereFuncao +
			// " lower( item.unidadeMedia ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( item.objetivoGeralIett ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( item.objetivoEspecificoIett ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( item.origemIett ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( item.beneficiosIett ) like :palavraChave or";

			/* Area */
			whereFuncao = whereFuncao + " lower( area.nomeAre ) like :palavraChave or";

			/* Sub-Area */
			whereFuncao = whereFuncao + " lower( subArea.nomeSare ) like :palavraChave or";

			/* ï¿½rgï¿½o resp 1 */
			whereFuncao = whereFuncao + " lower( orgao1.descricaoOrg ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( orgao1.siglaOrg ) like :palavraChave or";

			/* ï¿½rgï¿½o resp 2 */
			whereFuncao = whereFuncao + " lower( orgao2.descricaoOrg ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( orgao2.siglaOrg ) like :palavraChave";

			if (pesquisaAvancada) {
				/* Editor Leitor */
				whereFuncao = whereFuncao + " or lower( editorLeitor.usuarioUsu.nomeUsu ) like :palavraChave";
			}

			/* Funï¿½ï¿½o de Acompanhamento */
			whereFuncao = whereFuncao + " or lower( usuario.nomeUsu ) like :palavraChave";

			if (data != null) {
				whereFuncao = whereFuncao + " or ( item.dataInicioIett <= :data";
				whereFuncao = whereFuncao + " and item.dataTerminoIett >= :data )";
				whereFuncao = whereFuncao + " or item.dataInicioMonitoramentoIett = :data";
			}

			// Incluir somente ativos
			whereFuncao = whereFuncao + " and area.indAtivoAre = 'S'";
			whereFuncao = whereFuncao + " and subArea.indAtivoSare = 'S'";
			whereFuncao = whereFuncao + " and orgao1.indAtivoOrg = 'S'";
			whereFuncao = whereFuncao + " and orgao2.indAtivoOrg = 'S'";

			whereFuncao = whereFuncao + " )";

			Query query = this.getSession().createQuery(select + joinFuncao + where + whereFuncao);
			query.setString("palavraChave", "%" + palavraChave.toLowerCase() + "%");

			if (data != null) {
				query.setDate("data", data);
			}

			return query.list();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException();
		}
	}

	/**
	 * Realiza a pesquisa na Estrutura do item em ItemEstrutEntidadeIette
	 * 
	 * @param itensMap
	 * @param select
	 * @param where
	 * @param palavraChave
	 * @throws ECARException
	 */
	private List pesquisaEstruturaEntidade(String select, String where, String palavraChave, Date data) throws ECARException {
		try {
			String joinFuncao = " left join item.itemEstrutEntidadeIettes as entidade";
			String whereFuncao = "";

			if (!"".equals(where))
				whereFuncao = " and";
			else
				whereFuncao = " where";

			whereFuncao = whereFuncao + " ( lower( entidade.descricaoIette ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( entidade.entidadeEnt.nomeEnt ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( entidade.entidadeEnt.siglaEnt ) like :palavraChave";

			if (data != null) {
				whereFuncao = whereFuncao + " or ( entidade.dataInicioIette <= :data";
				whereFuncao = whereFuncao + " and entidade.dataFimIette >= :data )";
			}

			whereFuncao = whereFuncao + " )";

			Query query = this.getSession().createQuery(select + joinFuncao + where + whereFuncao);
			query.setString("palavraChave", "%" + palavraChave.toLowerCase() + "%");

			if (data != null) {
				query.setDate("data", data);
			}

			return query.list();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException();
		}
	}

	/**
	 * Realiza a pesquisa na Estrutura do item em EfIettFonteTotEfieft ->
	 * ItemFonteRecurso EfItemEstPrevisaoEfieps -> ItemRecurso
	 * 
	 * @param itensMap
	 * @param select
	 * @param where
	 * @param palavraChave
	 * @throws ECARException
	 */
	private List pesquisaEstruturaFonteRecurso(String select, String where, String palavraChave) throws ECARException {
		try {
			String joinFuncao = " left join item.efIettFonteTotEfiefts as fonteRecurso";
			joinFuncao = joinFuncao + " left join item.efItemEstPrevisaoEfieps as recurso";
			String whereFuncao = "";

			if (!"".equals(where))
				whereFuncao = " and";
			else
				whereFuncao = " where";
			whereFuncao = whereFuncao + " ( lower( fonteRecurso.fonteRecursoFonr.nomeFonr ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( recurso.recursoRec.nomeRec ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( recurso.recursoRec.siglaRec ) like :palavraChave ) and";
			whereFuncao = whereFuncao + " fonteRecurso.indAtivoEfieft = 'S' and ";
			whereFuncao = whereFuncao + " recurso.indAtivoEfiep = 'S' ";

			Query query = this.getSession().createQuery(select + joinFuncao + where + whereFuncao);
			query.setString("palavraChave", "%" + palavraChave.toLowerCase() + "%");
			return query.list();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException();
		}
	}

	/**
	 * Realiza a pesquisa na Estrutura do item em ItemEstrutLocalIettl
	 * 
	 * @param itensMap
	 * @param select
	 * @param where
	 * @param palavraChave
	 * @throws ECARException
	 */
	private List pesquisaEstruturaLocalizacao(String select, String where, String palavraChave) throws ECARException {
		try {
			String joinFuncao = " left join item.itemEstrutLocalIettls as local";
			String whereFuncao = "";

			if (!"".equals(where))
				whereFuncao = " and";
			else
				whereFuncao = " where";
			whereFuncao = whereFuncao + " ( lower( local.localItemLit.identificacaoLit ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( local.localItemLit.localGrupoLgp.identificacaoLgp ) like :palavraChave )";

			Query query = this.getSession().createQuery(select + joinFuncao + where + whereFuncao);
			query.setString("palavraChave", "%" + palavraChave.toLowerCase() + "%");
			return query.list();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException();
		}
	}

	/**
	 * Realiza a pesquisa na Estrutura do item em ItemEstrutLocalIettl
	 * 
	 * @param itensMap
	 * @param select
	 * @param where
	 * @param palavraChave
	 * @throws ECARException
	 */
	private List pesquisaEstruturaMarcador(String select, String where, String palavraChave, Long codUsu, boolean pesquisaAvancada) throws ECARException {
		try {
			String joinFuncao = " left join item.itemEstrutMarcadorIettms as marcador";

			if (pesquisaAvancada) {
				joinFuncao = joinFuncao + " left join marcador.usuarioUsu as usuario";
			}

			String whereFuncao = "";

			if (!"".equals(where))
				whereFuncao = " and";
			else
				whereFuncao = " where";
			whereFuncao = whereFuncao + " lower( marcador.descricaoIettm ) like :palavraChave";

			if (pesquisaAvancada) {
				whereFuncao = whereFuncao + " and usuario.codUsu = :codUsu";
			}

			Query query = this.getSession().createQuery(select + joinFuncao + where + whereFuncao);
			query.setString("palavraChave", "%" + palavraChave.toLowerCase() + "%");

			if (pesquisaAvancada) {
				query.setLong("codUsu", codUsu.longValue());
			}

			return query.list();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException();
		}
	}

	/**
	 * Realiza a pesquisa na Estrutura do item em ItemEstrutLocalIettl
	 * 
	 * @param itensMap
	 * @param select
	 * @param where
	 * @param palavraChave
	 * @throws ECARException
	 */
	private List pesquisaEstruturaPontoCritico(String select, String where, String palavraChave) throws ECARException {
		try {
			String joinFuncao = " left join item.pontoCriticoPtcs as pontoCritico";
			joinFuncao = joinFuncao + " left join pontoCritico.apontamentoApts as apontamento";
			String whereFuncao = "";

			if (!"".equals(where))
				whereFuncao = " and";
			else
				whereFuncao = " where";
			whereFuncao = whereFuncao + " ( lower( pontoCritico.descricaoPtc ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( pontoCritico.descricaoSolucaoPtc ) like :palavraChave or";
			whereFuncao = whereFuncao + " lower( apontamento.textoApt ) like :palavraChave ) and ";
			whereFuncao = whereFuncao + " pontoCritico.indAtivoPtc = 'S'";

			Query query = this.getSession().createQuery(select + joinFuncao + where + whereFuncao);
			query.setString("palavraChave", "%" + palavraChave.toLowerCase() + "%");
			return query.list();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException();
		}
	}

	/**
	 * Altera um item estrutura quando clicar em Ativar/Retirar Monitoramento
	 * 
	 * @param request
	 * @param usuarioLogado
	 * @param historico
	 * @return ItemEstruturaIett
	 * @throws ECARException
	 */
	public ItemEstruturaIett alterarMonitoramento(HttpServletRequest request, UsuarioUsu usuarioLogado, HistoricoIett historico) throws ECARException {
		Transaction tx = null;
		HistoricoItemEstruturaIett historicoItemEstruturaIett = new HistoricoItemEstruturaIett();
		try {
			ArrayList objetos = new ArrayList();
			super.inicializarLogBean();

			tx = session.beginTransaction();

			ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
			itemEstrutura.getItemEstUsutpfuacIettutfas().size();
			String indMonitoramentoAnterior = itemEstrutura.getIndMonitoramentoIett();

			ItemEstruturaIett old = (ItemEstruturaIett) itemEstrutura.clone();
			/*** Historico ***/
			historico.gerarHistorico(old);
			/*** Historico ***/

			itemEstrutura.setIndMonitoramentoIett(Pagina.getParamStr(request, "indMonitoramentoIett"));
			itemEstrutura.setUsuarioUsuByCodUsuUltManutIett(usuarioLogado);
			itemEstrutura.setDataUltManutencaoIett(Data.getDataAtual());

			// se o usuario escolheu ativar/desativar todos os itens filhos
			if ("S".equals(Pagina.getParamStr(request, "ativarRetirarMonitoramentoItensFilho"))) {
				if (indMonitoramentoAnterior != null && !indMonitoramentoAnterior.equals(itemEstrutura.getIndMonitoramentoIett())) {
					this.propagarMonitoramento(itemEstrutura, historico);
				}
			}

			session.update(itemEstrutura);
			objetos.add(itemEstrutura);

			historicoItemEstruturaIett.carregar(itemEstrutura);

			gerarHistorico(historicoItemEstruturaIett, Historico.ALTERAR);

			tx.commit();

			if (super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("ALT");
				Iterator itObj = objetos.iterator();

				while (itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}

			return itemEstrutura;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Altera um item estrutura quando clicar em Liberar/Bloquear Planejamento
	 * 
	 * @param request
	 * @param usuarioLogado
	 * @param historico
	 * @return ItemEstruturaIett
	 * @throws ECARException
	 */
	public ItemEstruturaIett alterarPlanejamento(HttpServletRequest request, UsuarioUsu usuarioLogado, HistoricoIett historico) throws ECARException {
		Transaction tx = null;
		HistoricoItemEstruturaIett historicoItemEstruturaIett = new HistoricoItemEstruturaIett();
		try {
			ArrayList objetos = new ArrayList();
			super.inicializarLogBean();

			tx = session.beginTransaction();

			ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
			itemEstrutura.getItemEstUsutpfuacIettutfas().size();
			String indBloqPlanejamentoAnterior = itemEstrutura.getIndBloqPlanejamentoIett();

			ItemEstruturaIett old = (ItemEstruturaIett) itemEstrutura.clone();

			/*** Historico ***/
			historico.gerarHistorico(old);
			/*** Historico ***/

			itemEstrutura.setIndBloqPlanejamentoIett(Pagina.getParamStr(request, "indBloqPlanejamentoIett"));

			// se o usuario escolheu bloquear/desbloquear todos os itens filhos
			if ("S".equals(Pagina.getParamStr(request, "bloquearDesbloquearPlanejamentoItensFilho"))) {
				if (indBloqPlanejamentoAnterior != null && !indBloqPlanejamentoAnterior.equals(itemEstrutura.getIndBloqPlanejamentoIett())) {
					this.propagarPlanejamento(itemEstrutura, historico);
				}
			}
			session.update(itemEstrutura);
			objetos.add(itemEstrutura);

			historicoItemEstruturaIett.carregar(itemEstrutura);

			gerarHistorico(historicoItemEstruturaIett, Historico.ALTERAR);

			tx.commit();

			if (super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("ALT");
				Iterator itObj = objetos.iterator();

				while (itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}

			return itemEstrutura;
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Propagar Liberar/Bloquear Planejamento para os itens filhos de um item
	 * (indBloqPlanejamentoIett)
	 * 
	 * @param itemEstruturaAtual
	 *            ItemEstruturaIett contendo os dados a serem propagados
	 */
	private void propagarPlanejamento(ItemEstruturaIett itemEstruturaAtual, HistoricoIett historico) throws ECARException {
		HistoricoItemEstruturaIett historicoItemEstruturaIett = null;
		try {
			List iettFilhos = this.getDescendentes(itemEstruturaAtual, false);

			for (Iterator it = iettFilhos.iterator(); it.hasNext();) {
				ItemEstruturaIett itemEstruturaFilho = (ItemEstruturaIett) it.next();
				itemEstruturaFilho.getItemEstUsutpfuacIettutfas().size();
				ItemEstruturaIett old = (ItemEstruturaIett) itemEstruturaFilho.clone();

				/*** Historico ***/
				historico.gerarHistorico(old);
				/*** Historico ***/

				itemEstruturaFilho.setIndBloqPlanejamentoIett(itemEstruturaAtual.getIndBloqPlanejamentoIett());
				itemEstruturaFilho.setUsuarioUsuByCodUsuUltManutIett(itemEstruturaAtual.getUsuarioUsuByCodUsuUltManutIett());
				itemEstruturaFilho.setDataUltManutencaoIett(Data.getDataAtual());

				historicoItemEstruturaIett = new HistoricoItemEstruturaIett();

				historicoItemEstruturaIett.carregar(itemEstruturaFilho);

				gerarHistorico(historicoItemEstruturaIett, Historico.ALTERAR);

			}
		} catch (Exception e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Propagar Ativar/Retirar Monitoramento para os itens filhos de um item
	 * (indMonitoramentoIett)
	 * 
	 * @param itemEstruturaAtual
	 *            ItemEstruturaIett contendo os dados a serem propagados
	 */
	private void propagarMonitoramento(ItemEstruturaIett itemEstruturaAtual, HistoricoIett historico) throws ECARException {
		HistoricoItemEstruturaIett historicoItemEstruturaIett = null;
		try {
			List iettFilhos = this.getDescendentes(itemEstruturaAtual, false);

			for (Iterator it = iettFilhos.iterator(); it.hasNext();) {
				ItemEstruturaIett itemEstruturaFilho = (ItemEstruturaIett) it.next();
				itemEstruturaFilho.getItemEstUsutpfuacIettutfas().size();
				ItemEstruturaIett old = (ItemEstruturaIett) itemEstruturaFilho.clone();
				/*** Historico ***/
				historico.gerarHistorico(old);
				/*** Historico ***/

				itemEstruturaFilho.setIndMonitoramentoIett(itemEstruturaAtual.getIndMonitoramentoIett());
				itemEstruturaFilho.setUsuarioUsuByCodUsuUltManutIett(itemEstruturaAtual.getUsuarioUsuByCodUsuUltManutIett());
				itemEstruturaFilho.setDataUltManutencaoIett(Data.getDataAtual());

				historicoItemEstruturaIett = new HistoricoItemEstruturaIett();

				historicoItemEstruturaIett.carregar(itemEstruturaFilho);

				gerarHistorico(historicoItemEstruturaIett, Historico.ALTERAR);

			}
		} catch (Exception e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Ordenar uma lista de itens pelo campo SiglaIett (utilizado para
	 * visualizaï¿½ï¿½o como nï¿½mero do item), para cada nï¿½vel da estrutura
	 * Obs: Se a sigla for nula serï¿½ ordenado pelo nome do item
	 * 
	 * <b>Importante: Este mï¿½todo sï¿½ deve ser usado para ordenar os itens do
	 * relatï¿½rio do PPA. Para ordenar outros itens, utilize o mï¿½todo
	 * getItensOrdenados();</b>
	 * 
	 * @param itens
	 * @return List de itens ordenado pelo campo SiglaIett
	 * @throws ECARException
	 */

	/*
	 * Este mï¿½todo sï¿½ ï¿½ usado para os itens do Relatï¿½rio de Apï¿½ndices
	 * do PPA.
	 */
	public List getItensOrdenadosPorSiglaIett(List itens) throws ECARException {
		List listRetorno = new ArrayList();
		List listOrdenacaoIett = new ArrayList();

		Iterator it = itens.iterator();
		while (it.hasNext()) {
			ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
			OrdenacaoIett ord = new OrdenacaoIett();

			ord.setIett(iett);

			List ascendentes = new ArrayList(this.getAscendentes(iett));

			if (ascendentes != null && ascendentes.size() > 0) {
				String niveis = "";
				Iterator itAux = ascendentes.iterator();

				while (itAux.hasNext()) {
					ItemEstruturaIett iettAux = (ItemEstruturaIett) itAux.next();

					if (iettAux.getSiglaIett() != null) {
						niveis += iettAux.getSiglaIett();
					} else {
						niveis += iettAux.getNomeIett();
					}
				}

				if (iett.getSiglaIett() != null) {
					ord.setCampoOrdenar(niveis + iett.getSiglaIett());
				} else {
					ord.setCampoOrdenar(niveis + iett.getNomeIett());
				}
			} else {
				if (iett.getSiglaIett() != null) {
					ord.setCampoOrdenar(iett.getSiglaIett());
				} else {
					ord.setCampoOrdenar(iett.getNomeIett());
				}
			}

			if (ord.getCampoOrdenar() == null) {
				ord.setCampoOrdenar("");
			}

			listOrdenacaoIett.add(ord);
		}

		Collections.sort(listOrdenacaoIett, new Comparator() {
			public int compare(Object o1, Object o2) {
				OrdenacaoIett ord1 = (OrdenacaoIett) o1;
				OrdenacaoIett ord2 = (OrdenacaoIett) o2;

				return ord1.getCampoOrdenar().compareToIgnoreCase(ord2.getCampoOrdenar());
			}
		});

		it = listOrdenacaoIett.iterator();

		while (it.hasNext()) {
			OrdenacaoIett ord = (OrdenacaoIett) it.next();

			listRetorno.add(ord.getIett());
		}

		return listRetorno;
	}

	/**
	 * Ordena os itens de acordo com o que foi definido nas configuraï¿½ï¿½es de
	 * tipo de Acompanhamento.
	 * 
	 * @param itens
	 * @param tipoAcomp
	 * @return List
	 * @throws ECARException
	 */
	public List getItensOrdenados(List itens, TipoAcompanhamentoTa tipoAcomp, boolean montarHierarquia) throws ECARException {
		List listRetorno = new ArrayList();

		EstruturaAtributoDao estruturaAtributoDao = new EstruturaAtributoDao(null);

		/*
		 * 1) Pegar qual os campos de ordenaï¿½ï¿½o que ï¿½ apresentado em cada
		 * estrutura. Ordenar campos da estrutura (EstruturaAtributo) pela
		 * sequencia de apresentaï¿½ï¿½o (seqApresListagemTelaEttat) e pegar o
		 * primeiro campo que possuir Listagem Impressa Relaï¿½ï¿½o
		 * (indRelacaoImpressaEttat) = "S". Se uma estrutura nï¿½o estiver
		 * apresentada neste filtro informado, assume que o Primeiro Atributo
		 * Valido desta estrutura ï¿½ o CodIett.
		 */
		List estruturasAtributos = estruturaAtributoDao.getAtributosValidoEstruturas(tipoAcomp);

		// Descubro o maior length dos campos dos itens para preencher com ZEROS
		// para ordenaï¿½ï¿½o.
		int tamanho = this.getTamanhoMaximoAtributoEstruturaItem(estruturasAtributos, itens, tipoAcomp);

		for (Iterator it = itens.iterator(); it.hasNext();) {
			ItemEstruturaIett iett = (ItemEstruturaIett) it.next();

			if (iett.getIndAtivoIett() != null && !Dominios.SIM.equals(iett.getIndAtivoIett())) {
				it.remove();
			}
		}

		// montar a ï¿½rvore completa de itens
		List listItensComPais = null;
		Iterator itItens = null;

		// verifica se ï¿½ necessï¿½rio montar hierarquia.
		if (montarHierarquia) {
			listItensComPais = new ArrayList(this.getArvoreItens(itens, null));
			itItens = listItensComPais.iterator();
		} else {
			itItens = itens.iterator();
		}

		// montar os objetos OrdenacaoIett
		List listNiveis = new ArrayList();
		List listOrdenacaoIett = new ArrayList();
		List<EstruturaEtt> listEstruturas = new ArrayList<EstruturaEtt>();
		while (itItens.hasNext()) {
			ItemEstruturaIett iett = (ItemEstruturaIett) itItens.next();

			if (iett.getIndAtivoIett() != null && !Dominios.SIM.equals(iett.getIndAtivoIett())) {
				continue;
			}

			OrdenacaoIett ord = new OrdenacaoIett();

			ord.setIett(iett);

			if (!listNiveis.contains(iett.getNivelIett())) {
				listNiveis.add(iett.getNivelIett());
			}

			if (!listEstruturas.contains(iett.getEstruturaEtt())) {
				listEstruturas.add(iett.getEstruturaEtt());
			}

			ord.setCampoOrdenar(this.getValorAtributoEstruturaItem(estruturasAtributos, iett, tamanho, tipoAcomp));

			if (ord.getCampoOrdenar() == null) {
				ord.setCampoOrdenar("");
			}

			listOrdenacaoIett.add(ord);
		}

		// ordenar a lista de OrdenacaoIett, aplicando regra de ordenamento
		Collections.sort(listOrdenacaoIett, new Comparator() {
			public int compare(Object o1, Object o2) {
				OrdenacaoIett ord1 = (OrdenacaoIett) o1;
				OrdenacaoIett ord2 = (OrdenacaoIett) o2;

				return Util.retiraAcentuacao(ord1.getCampoOrdenar().toLowerCase().trim()).compareToIgnoreCase(Util.retiraAcentuacao(ord2.getCampoOrdenar()).toLowerCase().trim());
			}
		});

		// ordenar a lista de nï¿½veis: 1, 2, 3, ... n
		Collections.sort(listNiveis, new Comparator() {
			public int compare(Object o1, Object o2) {
				Integer ord1 = (Integer) o1;
				Integer ord2 = (Integer) o2;

				return ord1.compareTo(ord2);
			}
		});

		// ordenar a lista de estruturas por label
		Collections.sort(listEstruturas, new Comparator() {
			public int compare(Object o1, Object o2) {
				EstruturaEtt ord1 = (EstruturaEtt) o1;
				EstruturaEtt ord2 = (EstruturaEtt) o2;

				return ord1.getNomeEtt().compareTo(ord2.getNomeEtt());
			}
		});

		// Gerar uma lista de itens para cada nï¿½vel
		List listOrdenacaoPorNivel = new ArrayList();
		Iterator itNiveis = listNiveis.iterator();
		Iterator<EstruturaEtt> itEstruturas = listEstruturas.iterator();
		while (itNiveis.hasNext()) {
			Integer nivel = (Integer) itNiveis.next();

			OrdenacaoPorNivel opn = new OrdenacaoPorNivel();
			opn.setNivel(nivel);
			opn.setOrdenacaoIett(new ArrayList());

			itEstruturas = listEstruturas.iterator();

			// varre cada estrutura dos itens da listagem
			while (itEstruturas.hasNext()) {

				EstruturaEtt estrutura = itEstruturas.next();

				itItens = listOrdenacaoIett.iterator();

				// varre todos os itens da listagem e virifica se eles sï¿½o do
				// nï¿½vel corrente e da estrutura corrente
				while (itItens.hasNext()) {
					OrdenacaoIett ord = (OrdenacaoIett) itItens.next();

					if (nivel.equals(ord.getIett().getNivelIett()) && estrutura.equals(ord.getIett().getEstruturaEtt())) {
						opn.getOrdenacaoIett().add(ord);
					}
				}

			}

			if (!listOrdenacaoPorNivel.contains(opn)) {
				listOrdenacaoPorNivel.add(opn);
			}

		}

		// ordenar a lista de OrdenacaoPorNivel, aplicando regra de ordenamento
		// itNiveis = listOrdenacaoPorNivel.iterator();
		// while(itNiveis.hasNext()) {
		// OrdenacaoPorNivel opn = (OrdenacaoPorNivel) itNiveis.next();
		//
		// //Ordena primeiro por nome da estrutura estrutura
		// Collections.sort(opn.getOrdenacaoIett(),
		// new Comparator() {
		// public int compare(Object o1, Object o2) {
		// OrdenacaoIett ord1 = (OrdenacaoIett) o1;
		// OrdenacaoIett ord2 = (OrdenacaoIett) o2;
		//
		// return
		// Util.retiraAcentuacao(ord1.getIett().getEstruturaEtt().getLabelEtt().toLowerCase().trim()).compareToIgnoreCase(
		// Util.retiraAcentuacao(ord2.getIett().getEstruturaEtt().getLabelEtt()).toLowerCase().trim());
		// }
		// }
		// );
		// }

		Iterator it = null;

		// verifica se ï¿½ necessï¿½rio montar hierarquia.
		if (montarHierarquia) {

			// organizar os itens de acordo com a hierarquia de
			// pai/filho/neto/bisneto/etc a partir dos itens do primeiro nï¿½vel
			List itensOrganizados = new ArrayList();
			if (!listOrdenacaoPorNivel.isEmpty()) {
				OrdenacaoPorNivel opn = (OrdenacaoPorNivel) listOrdenacaoPorNivel.get(0);

				itNiveis = opn.getOrdenacaoIett().iterator();
				while (itNiveis.hasNext()) {
					OrdenacaoIett ord = (OrdenacaoIett) itNiveis.next();

					List listIettAux = getItemFilhoOrdenacaoPorNivel(ord.getIett(), listOrdenacaoPorNivel);
					itensOrganizados.addAll(listIettAux);
				}
			}

			it = itensOrganizados.iterator();
		} else {// Senï¿½o for necessï¿½rio montar hierarquia, utiliza a
				// lista(listOrdenacaoPorNivel) que jï¿½ estï¿½ ordenada.
			/*
			 * Extrai do objeto bean de ordenaï¿½ï¿½o os itens que serï¿½o
			 * exibidos.
			 */
			List<ItemEstruturaIett> listaItensTemp = new ArrayList<ItemEstruturaIett>();

			itNiveis = listOrdenacaoPorNivel.iterator();

			// itera na lista ordenada
			while (itNiveis.hasNext()) {
				OrdenacaoPorNivel opn = (OrdenacaoPorNivel) itNiveis.next();

				// Obtem a lista beans(OrdenacaoIett) por nivel.
				Iterator itInnerItens = opn.getOrdenacaoIett().iterator();

				while (itInnerItens.hasNext()) {
					// obtem o bean(OrdenacaoIett) que encapsula o objeto de
					// negï¿½cio(ItemEstruturaIett)
					OrdenacaoIett ordenacaoIett = (OrdenacaoIett) itInnerItens.next();

					listaItensTemp.add(ordenacaoIett.getIett());
				}
			}

			it = listaItensTemp.iterator();

		}

		while (it.hasNext()) {
			ItemEstruturaIett iett = (ItemEstruturaIett) it.next();

			AtributoEstruturaListagemItens atEstListagem = new AtributoEstruturaListagemItens();
			atEstListagem.setDescricao(estruturaAtributoDao.getDescricaoItemByAtributo(iett, tipoAcomp));
			atEstListagem.setItem(iett);

			listRetorno.add(atEstListagem);
		}

		return listRetorno;
	}

	/**
	 * Obter prï¿½ximo item filho de um item e seus netos/bisnetos/etc a partir
	 * de uma lista de OrdenacaoPorNivel
	 * 
	 * @param iett
	 * @param listOrdenacaoPorNivel
	 * @return List de Iett
	 * @throws ECARException
	 */
	private List getItemFilhoOrdenacaoPorNivel(ItemEstruturaIett iett, List listOrdenacaoPorNivel) throws ECARException {
		List retorno = new ArrayList();

		retorno.add(iett);

		Iterator it = listOrdenacaoPorNivel.iterator();
		while (it.hasNext()) {
			OrdenacaoPorNivel opn = (OrdenacaoPorNivel) it.next();

			Iterator itItens = opn.getOrdenacaoIett().iterator();
			while (itItens.hasNext()) {
				OrdenacaoIett ord = (OrdenacaoIett) itItens.next();

				if (iett.equals(ord.getIett().getItemEstruturaIett())) {
					retorno.addAll(getItemFilhoOrdenacaoPorNivel(ord.getIett(), listOrdenacaoPorNivel));
				}

			}
		}

		return retorno;
	}

	/**
	 * completa para ordenaï¿½ï¿½o
	 * 
	 * @param valor
	 * @param tamanho
	 * @return String
	 */
	private String completarParaOrdenacao(String valor, int tamanho) {
		String completar = "E";
		try {
			Long dummy = Long.valueOf(valor); // se conseguiu transformar, ï¿½
												// nï¿½mero - completar zeros
												// ï¿½ esquerda
		} catch (NumberFormatException e) {
			// ATENï¿½ï¿½O: Nï¿½o ï¿½ necesï¿½rio lancar exceï¿½ï¿½o aqui

			// nï¿½o conseguiu transformar, nï¿½o ï¿½ nï¿½mero - completar zeros
			// ï¿½ direita
			completar = "D";
		}
		return Util.completarCaracteres(valor, "0", tamanho, completar);
	}

	/**
	 * Retorna o valor do atributo da estrutura.
	 * 
	 * @param atributosEstruturas
	 * @param iett
	 * @return
	 * @throws ECARException
	 */
	private String getValorAtributoEstruturaItem(List atributosEstruturas, ItemEstruturaIett iett, int tamanho, TipoAcompanhamentoTa ta) throws ECARException {
		StringBuilder retorno = new StringBuilder("");
		// flag que aponta se um tipo de acompanhamento especï¿½fico tem algum
		// atributo configurado para a determinada estrutura
		boolean temItemConfigurado = false;
		if (atributosEstruturas != null && !atributosEstruturas.isEmpty()) {
			Iterator it = atributosEstruturas.iterator();
			while (it.hasNext()) {
				AtributoEstruturaBean aeBean = (AtributoEstruturaBean) it.next();
				// if(iett.getEstruturaEtt().getIndAtivoEtt().equals("S") &&
				// iett.getEstruturaEtt().equals(aeBean.getEstrutura())){
				if (iett.getEstruturaEtt().equals(aeBean.getEstrutura())) {
					temItemConfigurado = true;
					String campo = this.getValorAtributoItemEstrutura(iett, aeBean.getAtributo().getAtributosAtb().getNomeAtb(), aeBean.getAtributo().getAtributosAtb().getNomeFkAtb()).trim();

					// Retorna os campos com atributos livre configurados
					if (campo.equals("") && aeBean.getAtributo().getAtributosAtb() != null && aeBean.getAtributo().getAtributosAtb().getSisGrupoAtributoSga() != null) {

						List<SisAtributoSatb> listSisAtributo = this.getSisAtributosIett(iett, aeBean.getAtributo().getAtributosAtb().getSisGrupoAtributoSga().getCodSga());

						if (listSisAtributo != null && listSisAtributo.size() > 0) {

							campo = listSisAtributo.get(0).getDescricaoSatb();
						}
					}
					// campo =
					// aeBean.getAtributo().getAtributosAtb().getSisGrupoAtributoSga().getDescricaoSga();

					/*
					 * Verificaï¿½ï¿½o ref mantis 9959: Se for um campo que
					 * consiga converter para data, entï¿½o deve-se gerar uma
					 * string com yyyyMMdd para que a ordenaï¿½ï¿½o fique
					 * correta.
					 * 
					 * Se nï¿½o conseguir converter para data, ï¿½ outro tipo de
					 * campo. Entï¿½o fica como estï¿½.
					 */
					Date data = Data.parseDate(campo, "dd/MM/yyyy");
					if (data != null) { // ï¿½ campo data!
						// Conseguiu converter para objeto Date!!!
						int d = Data.getDia(data);
						int m = Data.getMes(data) + 1;
						int a = Data.getAno(data);
						String dia = (d < 10) ? "0" + String.valueOf(d) : String.valueOf(d);
						String mes = (m < 10) ? "0" + String.valueOf(m) : String.valueOf(m);
						String ano = String.valueOf(a);

						campo = ano + mes + dia;
					}

					retorno.append(this.completarParaOrdenacao(campo, tamanho));
				}
			}
		} else {
			StringBuilder msg = new StringBuilder("Nï¿½o foi possï¿½vel obter a configuraï¿½ï¿½o de atributo na estrutura.");
			if (ta != null) {
				msg.append(" Tipo de acompanhamento: " + ta.getDescricaoTa() + ".");
			}
			msg.append(" Efetue a configuraï¿½ï¿½o da Estrutura: " + iett.getEstruturaEtt().getNomeEtt());

			throw new ECARException(msg.toString());
		}

		if (!temItemConfigurado) {
			StringBuilder msg = new StringBuilder("Nï¿½o foi possï¿½vel obter a configuraï¿½ï¿½o de atributo na estrutura.");
			if (ta != null) {
				msg.append(" Tipo de acompanhamento: " + ta.getDescricaoTa() + ".");
			}
			msg.append(" Efetue a configuraï¿½ï¿½o da Estrutura: " + iett.getEstruturaEtt().getNomeEtt());

			throw new ECARException(msg.toString());
		}

		boolean estruturaAbaixoNivelGeracao = true;
		if (ta != null) {
			estruturaAbaixoNivelGeracao = verificaNivelHierarquicoEstrutura(ta.getEstruturaNivelGeracaoTa(), iett.getEstruturaEtt());
		}

		if (estruturaAbaixoNivelGeracao && "".equals(retorno.toString()) && iett.getEstruturaEtt().getIndAtivoEtt().equals("S")) {
			// Se nï¿½o houver configuraï¿½ï¿½o, lanï¿½ar
			// exceï¿½ï¿½o!!!!!!!!!!!!!!!!

			/*
			 * StringBuilder msg = new StringBuilder(
			 * "Nï¿½o foi possï¿½vel obter a configuraï¿½ï¿½o de atributo na estrutura."
			 * ); if(ta != null) { msg.append(" Tipo de acompanhamento: " +
			 * ta.getDescricaoTa() + "."); }
			 * msg.append(" Efetue a configuraï¿½ï¿½o da Estrutura: " +
			 * iett.getEstruturaEtt().getNomeEtt());
			 */

			StringBuilder msg = new StringBuilder("Nï¿½o foi possï¿½vel gerar o perï¿½odo. Existem itens sem valores informados para o campo configurado para apresentaï¿½ï¿½o.");

			throw new ECARException(msg.toString());
		} else {
			return retorno.toString();
		}

	}

	/**
	 * 
	 * Verifica se uma estrutura pertence ao nï¿½vel hierï¿½rquico de outra, que
	 * representa uma configuraï¿½ï¿½o no tipo de acompanhamento. Retorna
	 * verdadeiro caso essa configuraï¿½ï¿½o nï¿½o esteja apontando nenhuma
	 * estrutura como nï¿½vel.
	 * 
	 * @param estruturaNivel
	 * @param estruturaItem
	 * @return boolean
	 * @throws ECARException
	 */
	public boolean verificaNivelHierarquicoEstrutura(EstruturaEtt estruturaNivel, EstruturaEtt estruturaItem) throws ECARException {

		boolean estruturaAbaixoNivelGeracao = true;
		EstruturaDao estruturaDao = new EstruturaDao(request);

		if (estruturaNivel != null) {

			estruturaAbaixoNivelGeracao = false;

			EstruturaEtt estruturaTa = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, estruturaNivel.getCodEtt());
			List descendentesEstruturaTa = estruturaDao.getDescendentes(estruturaTa);
			List ascendentesEstruturaTa = estruturaDao.getAscendentes(estruturaTa);

			// Verifica se a estrutura do item estï¿½ abaixo do nï¿½vel
			// hierï¿½rquico da estrutura estabelecida para geraï¿½ï¿½o de
			// perï¿½odos no Tipo de Acompanhamento
			if (estruturaItem.equals(estruturaNivel) || descendentesEstruturaTa.contains(estruturaItem) || ascendentesEstruturaTa.contains(estruturaItem)) {
				estruturaAbaixoNivelGeracao = true;
			}
		}

		return estruturaAbaixoNivelGeracao;
	}

	/**
	 * Percorre a lista de itens verificando qual o atributo tem o maior length
	 * para ser usado no mï¿½todo de ordenaï¿½ï¿½o.
	 * 
	 * @param estruturasAtributos
	 * @param itens
	 * @param ta
	 * @return
	 * @throws ECARException
	 */
	private int getTamanhoMaximoAtributoEstruturaItem(List estruturasAtributos, List itens, TipoAcompanhamentoTa ta) throws ECARException {

		int retorno = 0;

		for (Iterator iter = itens.iterator(); iter.hasNext();) {
			ItemEstruturaIett iett = (ItemEstruturaIett) iter.next();

			String strAux = this.getValorAtributoEstruturaItem(estruturasAtributos, iett, -1, ta);
			if (strAux.length() > retorno) {
				retorno = strAux.length();
			}
		}
		return retorno;
	}

	/*
	 * private int getTamanhoMaximoAtributoEstruturaItem(List
	 * estruturasAtributos, List itens) throws ECARException{ Iterator it =
	 * itens.iterator(); int retorno = 0; while(it.hasNext()) {
	 * ItemEstruturaIett iett = (ItemEstruturaIett) it.next();
	 * 
	 * List ascendentes = new ArrayList(this.getAscendentes(iett));
	 * 
	 * if(ascendentes != null && ascendentes.size() > 0) { Iterator itAux =
	 * ascendentes.iterator(); while (itAux.hasNext()) { ItemEstruturaIett
	 * iettAux = (ItemEstruturaIett) itAux.next();
	 * 
	 * String strAux = this.getValorAtributoEstruturaItem(estruturasAtributos,
	 * iettAux); if(strAux.length() > retorno){ retorno = strAux.length(); } } }
	 * else { String strAux =
	 * this.getValorAtributoEstruturaItem(estruturasAtributos, iett);
	 * if(strAux.length() > retorno){ retorno = strAux.length(); } } } return
	 * retorno; }
	 */

	/**
	 * Verifica se o item possui uma determinada situacao nas listas de
	 * cï¿½digos de situacoes.
	 * 
	 * @param itemEstrutura
	 * @param idSituacoes
	 * @return boolean
	 * @throws ECARException
	 */
	public boolean verificarSituacao(ItemEstruturaIett itemEstrutura, List idSituacoes) throws ECARException {

		if (itemEstrutura != null && itemEstrutura.getSituacaoSit() != null && idSituacoes != null && idSituacoes.size() > 0) {

			for (Iterator itSituacoes = idSituacoes.iterator(); itSituacoes.hasNext();) {
				String codSit = (String) itSituacoes.next();
				if (codSit.equals(itemEstrutura.getSituacaoSit().getCodSit().toString()))
					return true;
			}

		}
		return false;
	}

	/**
	 * Filtra uma lista de ItemEstruturaIetts
	 * 
	 * @param listaIett
	 * @param codOrgao
	 * @param criteriosCom
	 * @param criteriosSem
	 * @param situacoes
	 * @param revisao
	 *            - utilize "S" para filtrar sï¿½ os itens que possuem revisï¿½o
	 * @param submitPPA
	 *            - utilize "S" para filtrar sï¿½ os itens que possuem PPA
	 * @return List
	 * @throws ECARException
	 */
	public List filtrarRelatorioItemEstrutura(List listaIett, Long codOrgao, List criteriosCom, List criteriosSem, List situacoes, String revisao, String submitPPA) throws ECARException {
		ItemEstruturaCriterioDao itemCriterioDao = new ItemEstruturaCriterioDao(request);
		SisAtributoDao sisAtributoDao = new SisAtributoDao(request);

		Iterator itIetts = listaIett.iterator();
		while (itIetts.hasNext()) {
			ItemEstruturaIett iett = (ItemEstruturaIett) itIetts.next();

			/* Verificando itens com revisï¿½o... */
			if ("S".equals(revisao)) {
				if (!(iett.getItemEstruturarevisaoIettrevs() != null && iett.getItemEstruturarevisaoIettrevs().size() > 0)) {
					itIetts.remove();
					continue;
				}
			}

			/* Verificando itens com PPA */
			if ("S".equals(submitPPA)) {

				boolean possuiNivelPPA = false;
				List niveisPlanejamento = getNivelAcessoById(iett);
				Iterator itNiveis = niveisPlanejamento.iterator();

				while (itNiveis.hasNext()) {
					Long codSatb = (Long) itNiveis.next();
					SisAtributoSatb nivel = (SisAtributoSatb) sisAtributoDao.buscar(SisAtributoSatb.class, codSatb);

					if (nivel != null && "PPA".equals(nivel.getDescricaoSatb())) {// Nï¿½o
																					// foi
																					// usado
																					// o
																					// codSatb
																					// para
																					// testar
																					// devido
																					// ï¿½s
																					// diferenï¿½as
																					// de
																					// cï¿½digos
																					// em
																					// Produï¿½ï¿½o
																					// e
																					// Desenvolvimento
						possuiNivelPPA = true;
						break;
					}
				}

				if (!possuiNivelPPA) {
					itIetts.remove();
					continue;
				}
			}

			/* Verificando ï¿½rgï¿½os... */
			if (codOrgao != null) {
				if (iett.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null) {
					if (!codOrgao.equals(iett.getOrgaoOrgByCodOrgaoResponsavel1Iett().getCodOrg())) {
						itIetts.remove();
						continue;
					}
				}
			}

			/* Verificando Critï¿½rios... */
			if ((criteriosCom != null && criteriosCom.size() > 0) || (criteriosSem != null && criteriosSem.size() > 0)) {
				if (!itemCriterioDao.verificarCriterio(iett, criteriosCom, criteriosSem)) {
					itIetts.remove();
					continue;
				}
			}

			/* Verificando Situaï¿½ï¿½es... */
			if (situacoes != null && situacoes.size() > 0) {
				if (!this.verificarSituacao(iett, situacoes)) {
					itIetts.remove();
					continue;
				}
			}
		}

		return listaIett;
	}

	/**
	 * Retorna itens filtrados pela listagem de criterios
	 * 
	 * @param itens
	 * @param idCriteriosCom
	 * @param idCriteriosSem
	 * @return
	 * @throws ECARException
	 */
	public List filtrarItensPorCriterio(ArrayList<ItemEstruturaIettPPA> itens, ArrayList<String> idCriteriosCom, ArrayList<String> idCriteriosSem) throws ECARException {
		final ItemEstruturaCriterioDao itemCriterioDao = new ItemEstruturaCriterioDao(request);

		List tmp = itens;
		for (Iterator iter = tmp.iterator(); iter.hasNext();) {
			ItemEstruturaIett item = (ItemEstruturaIett) iter.next();

			if (!itemCriterioDao.verificarCriterio(item, idCriteriosCom, idCriteriosSem)) {
				iter.remove();
				continue;
			}

		}

		return tmp;

	}

	/**
	 * Retorna uma Lista de todos os ItemEstruraRevisaoIettrev de um
	 * ItemEstruturaIett ordenados por Data
	 * 
	 * @param codIett
	 * @return
	 * @throws ECARException
	 */
	public List getItemEstruturaRevisaoIettrevOrderByData(Long codIett) throws ECARException {
		try {
			StringBuilder qry = new StringBuilder("select iettRev").append(" from ItemEstruturarevisaoIettrev as iettRev").append(" where iettRev.itemEstruturaIettrev.codIett = :codigo")
					.append(" order by iettRev.dataInclusaoIettrev");

			Query q = this.session.createQuery(qry.toString());
			q.setLong("codigo", codIett.longValue());

			return q.list();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Recupera Itens de programa para o relatorio PPA
	 * 
	 * @param periodoIni
	 *            Data de inicio para geracao do relatorio
	 * @param periodoFim
	 *            Data de termino para geracao do relatorio
	 * @return Listagem de Itens de Programa <ItemEstruturaIett>
	 * @throws ECARException
	 */
	public List getItensPPA(String periodoIni, String periodoFim) throws ECARException {
		try {
			SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
			List retorno = new ArrayList();
			StringBuilder qry = new StringBuilder("from ItemEstruturaIett as iett").append(" where ")
			// " iett.nivelIett = :nivelProduto and" +
					.append(" iett.dataInicioIett >= :dataIni").append(" and iett.indAtivoIett = 'S'").append(" and (iett.dataTerminoIett is null or iett.dataTerminoIett <= :dataFim)");

			Query q = this.session.createQuery(qry.toString());

			Date dataIni = Data.parseDate("01/01/" + periodoIni);
			Date dataFim = Data.parseDate("31/12/" + periodoFim);

			// q.setInteger("nivelProduto", 4);
			q.setDate("dataIni", dataIni);
			q.setDate("dataFim", dataFim);

			List itens = new ArrayList(q.list());
			if (itens != null && itens.size() > 0) {
				Iterator itRet = itens.iterator();
				while (itRet.hasNext()) {
					ItemEstruturaIett iett = (ItemEstruturaIett) itRet.next();

					boolean possuiNivelPPA = false;
					List niveisPlanejamento = getNivelAcessoById(iett);
					Iterator itNiveis = niveisPlanejamento.iterator();

					while (itNiveis.hasNext()) {
						Long codSatb = (Long) itNiveis.next();
						SisAtributoSatb nivel = (SisAtributoSatb) sisAtributoDao.buscar(SisAtributoSatb.class, codSatb);
						// Nï¿½o foi usado o codSatb para testar devido ï¿½s
						// diferenï¿½as de
						// cï¿½digos em Produï¿½ï¿½o e Desenvolvimento
						if (nivel != null && "PPA".equals(nivel.getDescricaoSatb())) {
							possuiNivelPPA = true;
							break;
						}
					}
					if (possuiNivelPPA) {
						retorno.add(iett);
					}
				}
			}
			return getItensOrdenadosPorSiglaIett(getArvoreItens(retorno, null));
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Retorna uma lista de ItemEstruturaIett ordenados pelos cï¿½digos dos pais
	 * e cujos pais estï¿½o contidos na lista passada por parï¿½metro
	 * 
	 * @param codEstrutura
	 * @param codItensPais
	 * @return List de ItemEstruturaIett
	 * @throws ECARException
	 */
	public List getItensByEstruturaOrdenadosPorItemPai(Long codEstrutura, List codItensPais) throws ECARException {

		try {
			StringBuilder qry = new StringBuilder("from ItemEstruturaIett as iett").append(" where ").append(" iett.indAtivoIett = 'S' and ").append(" iett.estruturaEtt.codEtt = :codEtt and (");

			Iterator codItensPaisIt = codItensPais.iterator();

			while (codItensPaisIt.hasNext()) {
				Long codPai = (Long) codItensPaisIt.next();
				qry.append(" iett.itemEstruturaIett.codIett = " + codPai + " or");
			}

			StringBuilder newQry = new StringBuilder(qry.substring(0, qry.length() - 2));
			newQry.append(")");

			newQry.append(" order by iett.itemEstruturaIett.codIett, iett.nomeIett");

			Query q = this.session.createQuery(newQry.toString());

			q.setLong("codEtt", codEstrutura.longValue());

			return q.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	public List getItensAtivosByEstrutura(Long codEstrutura) throws ECARException {

		try {
			StringBuilder qry = new StringBuilder("from ItemEstruturaIett as iett").append(" where ").append(" iett.indAtivoIett = 'S' and ").append(" iett.estruturaEtt.codEtt = :codEtt");

			Query q = this.session.createQuery(qry.toString());

			q.setLong("codEtt", codEstrutura.longValue());

			return q.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Retorna uma lista de ItemEstruturaIett ordenados pelos cï¿½digos dos pais
	 * 
	 * @param itens
	 * @return List de ItemEstruturaIett
	 * @throws ECARException
	 */
	public List<ItemEstruturaIett> getItensOrdenadosPorItemPai(Set itens) throws ECARException {

		try {

			StringBuilder qry = new StringBuilder("from ItemEstruturaIett as iett").append(" where ").append(" iett.indAtivoIett = 'S' and ").append(" iett.codIett in (:listCodIett) ");

			qry.append(" order by iett.itemEstruturaIett.codIett, iett.nomeIett");

			Query q = this.session.createQuery(qry.toString());

			List<Long> listaCodigosIett = new ArrayList<Long>();
			for (Iterator iter = itens.iterator(); iter.hasNext();) {
				ItemEstruturaIett item = (ItemEstruturaIett) iter.next();
				listaCodigosIett.add(item.getCodIett());
			}

			q.setParameterList("listCodIett", listaCodigosIett);

			return q.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * 
	 * @param codEstrutura
	 * @return
	 * @throws ECARException
	 */
	public List getItensByEstrutura(Set gruposUsuarios, UsuarioUsu usuario, Long... codEstrutura) throws ECARException {
		try {
			StringBuilder qry = new StringBuilder("select distinct iett from ItemEstruturaIett as iett").append(" inner join iett.itemEstrutUsuarioIettusesByCodIett perm ").append(" where ")
					.append(" iett.indAtivoIett = 'S' and ").append(" iett.estruturaEtt.codEtt in (:codEtt)").append(" AND (perm.usuarioUsu.codUsu = :codUsu OR ")
					.append(" perm.sisAtributoSatb in (:gruposUsuarios))");

			Query q = this.session.createQuery(qry.toString());

			q.setParameter("codUsu", usuario.getCodUsu());
			q.setParameterList("gruposUsuarios", gruposUsuarios);
			q.setParameterList("codEtt", codEstrutura);
			return q.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			e.printStackTrace();
			throw new ECARException("erro.hibernateException");
		}
	}

	public List getItensByEstrutura(Long codEstrutura) throws ECARException {
		try {
			StringBuilder qry = new StringBuilder("select iett from ItemEstruturaIett as iett").append(" where ").append(" iett.indAtivoIett = 'S' and ")
					.append(" iett.estruturaEtt.codEtt in (:codEtt)");

			Query q = this.session.createQuery(qry.toString());

			q.setParameter("codEtt", codEstrutura);
			return q.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			e.printStackTrace();
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * 
	 * @param codEstrutura
	 * @return
	 * @throws ECARException
	 */
	public List getItensByEstruturaPPA(Long codEstrutura) throws ECARException {
		try {
			StringBuilder qry = new StringBuilder("from ItemEstruturaIettPPA as iett").append(" where ").append(" iett.indAtivoIett = 'S' and ").append(" iett.estruturaEtt.codEtt = :codEtt")
					.append(" order by iett.nomeIett  ");

			Query q = this.session.createQuery(qry.toString());
			q.setLong("codEtt", codEstrutura.longValue());

			return q.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Listar somente itens com revisï¿½o.
	 * 
	 * @param tipoApendice
	 * @param periodoIni
	 * @param periodoFim
	 * @return List
	 * @throws ECARException
	 */

	/*
	 * Este mï¿½todo sï¿½ ï¿½ usado nos Relatï¿½rio dos Apï¿½ndices do PPA
	 */
	public List getArvoreItensIettComRevisao(String tipoApendice, String periodoIni, String periodoFim) throws ECARException {
		try {
			SisAtributoDao sisAtributoDao = new SisAtributoDao(request);
			List retorno = new ArrayList();
			StringBuilder qry = new StringBuilder("from ItemEstruturaIett as iett").append(" where ")
			// " iett.nivelIett = :nivelProduto and" +
					.append(" iett.indAtivoIett = 'S'").append(" and iett.dataInicioIett >= :dataIni").append(" and (iett.dataTerminoIett is null or iett.dataTerminoIett <= :dataFim)");

			Query q = this.session.createQuery(qry.toString());

			Date dataIni = Data.parseDate("01/01/" + periodoIni);
			Date dataFim = Data.parseDate("31/12/" + periodoFim);

			// q.setInteger("nivelProduto", 4);
			q.setDate("dataIni", dataIni);
			q.setDate("dataFim", dataFim);

			List itens = new ArrayList(q.list());
			if (itens != null && itens.size() > 0) {
				Iterator itRet = itens.iterator();
				while (itRet.hasNext()) {
					ItemEstruturaIett iett = (ItemEstruturaIett) itRet.next();

					boolean possuiNivelPPA = false;
					List niveisPlanejamento = getNivelAcessoById(iett);
					Iterator itNiveis = niveisPlanejamento.iterator();

					while (itNiveis.hasNext()) {
						Long codSatb = (Long) itNiveis.next();
						SisAtributoSatb nivel = (SisAtributoSatb) sisAtributoDao.buscar(SisAtributoSatb.class, codSatb);

						if (nivel != null && "PPA".equals(nivel.getDescricaoSatb())) {// Nï¿½o
																						// foi
																						// usado
																						// o
																						// codSatb
																						// para
																						// testar
																						// devido
																						// ï¿½s
																						// diferenï¿½as
																						// de
																						// cï¿½digos
																						// em
																						// Produï¿½ï¿½o
																						// e
																						// Desenvolvimento
							possuiNivelPPA = true;
							break;
						}
					}
					if (possuiNivelPPA) {
						retorno.add(iett);
					}
				}
			}
			/*
			 * Esta parte foi comentada pois, ref. mantis 5042, o sistema
			 * buscarï¿½ todos os campos na estrutura normal dos itens do eCAr,
			 * 
			 * segmentoCategoria.setSegmentoCategTpAcessSgts(new HashSet());
			 * if(!
			 * segmentoCategoria.getSegmentoCategTpAcessSgts().contains(atributo
			 * )){
			 * segmentoCategoria.getSegmentoCategTpAcessSgts().add(atributo); }
			 * 
			 * ignorando portanto a tabela revisï¿½o.
			 * 
			 * List itens = new ArrayList(q.list()); if(itens != null &&
			 * itens.size() > 0){ Iterator itRet = itens.iterator();
			 * while(itRet.hasNext()){ ItemEstruturaIett iett =
			 * (ItemEstruturaIett) itRet.next();
			 * 
			 * List itensRev = new
			 * ArrayList(iett.getItemEstruturarevisaoIettrevs()); Iterator itRev
			 * = itensRev.iterator(); while(itRev.hasNext()){
			 * ItemEstruturarevisaoIettrev iettRev =
			 * (ItemEstruturarevisaoIettrev) itRev.next();
			 * if("apendice2".equals(tipoApendice)){
			 * //if("I".equals(iettRev.getSituacaoIettrev()) ||
			 * "A".equals(iettRev.getSituacaoIettrev())){
			 * if(!"E".equals(iettRev.getSituacaoIettrev())){ retorno.add(iett);
			 * break;arg0 } } else if("apendice3".equals(tipoApendice)){
			 * if("E".equals(iettRev.getSituacaoIettrev())){ retorno.add(iett);
			 * break; } } } } }
			 */

			return getItensOrdenadosPorSiglaIett(getArvoreItens(retorno, null));
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Retorna lista de Itens da Estrutura de acordo com a data dataTerminoIett,
	 * dataInicioIett, dataR1, dataR2, dataR3, dataR4, dataR5. <br>
	 * 
	 * @author rogerio
	 * @version 0.1, 09/02/2007
	 * @param dataLimiteStr
	 * @return List
	 * @throws ECARException
	 */
	public List listarItemEstruturaPorDataLimite(String dataLimiteStr) throws ECARException {
		List list = null;

		try {
			Query query = session.createQuery(" from ItemEstruturaIett " + " where indAtivoIett = 'S' and dataTerminoIett = :data or dataInicioIett = :data "
					+ " or dataR1 = :data or dataR2 = :data or dataR3 = :data " + " or dataR4 = :data or dataR5 = :data ");

			Date data = Data.parseDate(dataLimiteStr);
			query.setDate("data", data);

			list = query.list();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}

		return list;
	} // fim listarItemEstruturaPorDataLimite

	/**
	 * Retorna lista de Itens da Estrutura de um determinado nï¿½vel
	 * 
	 * @author cristiano
	 * @version 0.1, 13/02/2007
	 * @param nivel
	 * @return List
	 * @throws ECARException
	 */
	public List listarItemEstruturaPorNivel(int nivel) throws ECARException {
		Query query = session.createQuery(" from ItemEstruturaIett where nivelIett = :nivel and indAtivoIett = 'S'");

		query.setInteger("nivel", nivel);

		return query.list();
	}

	/**
	 * Mï¿½todo que verifica se um item possui itens filhos com indicador de
	 * etapas de nï¿½vel superior na estrutura.
	 * 
	 * @author aleixo
	 * @version 0.2 - 26/03/2007, 0.1 - 21/03/2007
	 * @param item
	 * @return boolean
	 */
	public boolean verificaItemPossuiEtapas(ItemEstruturaIett item) {
		return !this.getEtapasIett(item).isEmpty();
	}

	/**
	 * Retorna uma lista com as etapas de um item.<br>
	 * Etapas de um item sï¿½o os itens filhos de um item que possuem indicador
	 * de etapa de nï¿½vel superior na estrutura.
	 * 
	 * @author aleixo
	 * @version 0.1, 22/03/2007
	 * @param item
	 * @return List
	 */
	public List getEtapasIett(ItemEstruturaIett item) {
		List<ItemEstruturaIett> retorno = new ArrayList<ItemEstruturaIett>();

		if (item.getItemEstruturaIetts() != null) {
			/*
			 * Aqui, pegar somente as etapas que sï¿½o filhos de 1ï¿½
			 * geraï¿½ï¿½o do item (somentes os filhos do item), ignorando os
			 * outros descendentes do item (.getDescendentes()), como filhos dos
			 * filhos, ou filhos dos filhos dos filhos, etc...;
			 */

			for (Iterator itEtapas = item.getItemEstruturaIetts().iterator(); itEtapas.hasNext();) {
				ItemEstruturaIett iettFilho = (ItemEstruturaIett) itEtapas.next();

				if ("S".equals(iettFilho.getEstruturaEtt().getIndEtapaNivelSuperiorEtt())) {
					retorno.add(iettFilho);
				}
			}
		}
		return retorno;
	}

	/**
	 * Retorna uma lista de {@link ItemWebServiceBean} para os WebSerices,
	 * utilizando SQL Nativo.
	 * 
	 * @author aleixo
	 * @version 0.1 - 05/04/2007
	 * @param codigos
	 * @param buscarFilhos
	 * @param buscarEstrutura
	 * @return List
	 */
	public List getIettToWebService(Long[] codigos, boolean buscarFilhos, boolean buscarEstrutura) {
		List<ItemWebServiceBean> retorno = new ArrayList<ItemWebServiceBean>();

		try {

			if (codigos == null || (codigos != null && codigos.length < 1))
				return retorno;

			// Seleciono os itens pedidos no banco de dados.

			StringBuilder select = new StringBuilder();

			select.append("select item.cod_iett, ");
			select.append("item.nome_iett, ");
			select.append("item.ind_ativo_iett, ");
			select.append("item.data_inicio_iett, ");
			select.append("item.data_termino_iett, ");
			select.append("orgao.descricao_org, ");
			select.append("orgao.sigla_org ");
			select.append("from tb_item_estrutura_iett item ");

			// Utilizando "left join" para que exiba os dados, mesmo que nï¿½o
			// exista a relaï¿½ï¿½o orgao -> item
			select.append(" left join tb_orgao_org orgao");
			select.append(" on orgao.cod_org = item.cod_orgao_responsavel1_iett");

			if (buscarEstrutura) {
				select.append(" where item.cod_ett ");
			} else {
				select.append(" where item.cod_iett ");
			}

			select.append(" in (");

			// Montando lista de codigos com virgulas para ser usado na
			// clï¿½usula IN do select.
			String cods = "";
			int ultimaPosicao = codigos.length - 1;
			for (int i = 0; i < codigos.length; i++) {
				Long codigo = codigos[i];
				cods += codigo.toString();
				if (i != ultimaPosicao) {
					cods += ", ";
				}
			}
			select.append(cods + ")");

			SQLQuery q = this.session.createSQLQuery(select.toString());

			List objects = q.list();

			// Com a lista obtida, percorro a lista para popular os beans...
			// retornar

			Iterator itObj = objects.iterator();
			while (itObj.hasNext()) {
				Object[] item = (Object[]) itObj.next();
				/*
				 * item ï¿½ um array de Object, onde:
				 * 
				 * posiï¿½ï¿½o | Conteudo
				 * ---------------------------------------- 0 | codIett 1 |
				 * nomeIett 2 | indAtivoIett 3 | dataInicioIett 4 |
				 * dataTerminoIett 5 | nomeCliente 6 | siglaCliente
				 */

				ItemWebServiceBean iwsBean = new ItemWebServiceBean();

				Long codItem = item[0] != null ? Long.valueOf(item[0].toString()) : null;
				String nomeItem = item[1] != null ? item[1].toString() : null;
				String indAtivo = item[2] != null ? item[2].toString() : null;

				Date dataInicial = null;
				if (item[3] != null)
					dataInicial = Data.parseDate(item[3].toString(), "yyyy-mm-dd hh:mm:ss.S");

				Date dataFinal = null;
				if (item[4] != null)
					dataFinal = Data.parseDate(item[4].toString(), "yyyy-mm-dd hh:mm:ss.S");

				// Setando dados referentes ao item
				iwsBean.setCodItem(codItem);
				iwsBean.setNomeItem(nomeItem);
				iwsBean.setIndAtivo(indAtivo);
				iwsBean.setDataInicial(dataInicial);
				iwsBean.setDataFinal(dataFinal);

				// Setando dados referentes ao Orgï¿½o/Cliente
				String nomeCliente = item[5] != null ? item[5].toString() : null;
				String siglaCliente = item[6] != null ? item[6].toString() : null;
				iwsBean.setNomeCliente(nomeCliente);
				iwsBean.setSiglaCliente(siglaCliente);

				// Setando coleï¿½ï¿½o de itens filhos
				ArrayList filhos = new ArrayList();
				if (buscarFilhos && codItem != null) {
					List codFilhos = this.getItensFilhosWebService(codItem);
					Iterator itFilhos = codFilhos.iterator();

					Long[] codigosFilhos = new Long[codFilhos.size()];
					int i = 0;
					while (itFilhos.hasNext()) {
						Object codFilho = (Object) itFilhos.next();
						if (codFilho != null) {
							Long codFilhoLong = Long.valueOf(codFilho.toString());
							codigosFilhos[i] = codFilhoLong;
							i++;
						}
					}
					/*
					 * Para os filhos, passo buscaEstrutura = false, para que
					 * busque os filhos pelos cï¿½digos dos filhos, e nï¿½o pelo
					 * cï¿½digo da estrutura dos filhos.
					 */
					filhos.addAll(this.getIettToWebService(codigosFilhos, buscarFilhos, false));
				}
				iwsBean.setItensFilhos(filhos);

				// Setando dados referentes ao nï¿½vel de planejamento/Fase
				ArrayList<IndicativoItemWebServiceBean> indicativos = new ArrayList<IndicativoItemWebServiceBean>();
				List codNiveis = this.getNivelIettWebService(codItem);
				Iterator itCodNiveis = codNiveis.iterator();
				while (itCodNiveis.hasNext()) {
					Object[] o = (Object[]) itCodNiveis.next();

					IndicativoItemWebServiceBean indicativoBean = new IndicativoItemWebServiceBean();

					indicativoBean.setCodIndicativo((o[0] != null) ? Long.valueOf(o[0].toString()) : null);
					indicativoBean.setDescricaoIndicativo((o[1] != null) ? o[1].toString() : null);

					indicativos.add(indicativoBean);
				}
				iwsBean.setIndicativoItem(indicativos);
				retorno.add(iwsBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * Retorna os cï¿½digos dos itens filhos de um item, utilizando SQL Nativo.
	 * 
	 * @author aleixo
	 * @version 0.1 - 09/04/2007
	 * @param Long
	 *            codIett - cï¿½digo do item
	 * @return List
	 */
	private List getItensFilhosWebService(Long codIett) {
		StringBuilder select = new StringBuilder();

		select.append("select cod_iett from tb_item_estrutura_iett ");
		select.append(" where  cod_iett_pai = " + codIett.toString());
		SQLQuery q = this.session.createSQLQuery(select.toString());

		List objects = q.list();

		if (objects != null && !objects.isEmpty())
			return objects;
		return new ArrayList();
	}

	/**
	 * Retorna uma lista o cï¿½digo e a descriï¿½ï¿½o do nï¿½vel de
	 * planejamento/fase., utilizando SQL Nativo<br>
	 * cï¿½digo = posiï¿½ï¿½o [0]<br>
	 * descriï¿½ï¿½o = posicao [1].
	 * 
	 * @author aleixo
	 * @version 0.1 - 09/04/2007
	 * @param Long
	 *            codIett - cï¿½digo do item.
	 * @return List
	 */
	private ArrayList getNivelIettWebService(Long codIett) {
		StringBuilder select = new StringBuilder();

		select.append("select atb.cod_satb, atb.descricao_satb from tb_item_estrutura_nivel_iettn nivel, tb_sis_atributo_satb atb ");
		select.append(" where  nivel.cod_iett = " + codIett.toString());
		select.append(" and atb.cod_satb = nivel.cod_atb");
		SQLQuery q = this.session.createSQLQuery(select.toString());

		ArrayList objects = (ArrayList) q.list();

		if (objects != null && !objects.isEmpty())
			return objects;

		return new ArrayList();
	}

	/**
	 * Retorna uma lista de itens ItemWSMinBean, que contï¿½m apenas o cï¿½digo
	 * (sigla) e a descriï¿½ï¿½o (nome) de cada item.<br>
	 * Se codigo != null: Retorna dados de apenas um item. Se codigo == null ou
	 * vazio: Retorna dados de todos os itens.
	 * 
	 * Se nivel != null: Filtra itens de determinado(s) nï¿½vel(is) de
	 * estrutura(s). Se nivel == null ou vazio: Considera todos os nï¿½veis de
	 * estrutura.
	 * 
	 * @author aleixo
	 * @since 29/05/2007
	 * @param codigo
	 * @param niveis
	 * @return List
	 */
	public List listIettToWSMin(String codigo, Long[] niveis) {
		List<ItemWSMinBean> retorno = new ArrayList<ItemWSMinBean>();

		// Seleciono os itens pedidos no banco de dados.

		StringBuilder select = new StringBuilder();

		select.append("select item.sigla_iett, item.nome_iett ");
		select.append("from tb_item_estrutura_iett item ");

		boolean where = false;
		if (codigo != null && !"".equals(codigo.trim())) {
			where = true;
			select.append(" where item.sigla_iett = :codigo");
		}

		if (niveis != null && niveis.length > 0) {
			select.append(!where ? " where " : " and ");

			select.append(" item.cod_ett in (");

			String strNiveis = "";
			int ultimaPosicao = niveis.length - 1;
			for (int i = 0; i < niveis.length; i++) {
				Long nivel = niveis[i];
				strNiveis += nivel.toString();
				if (i != ultimaPosicao) {
					strNiveis += ", ";
				}
			}

			select.append(strNiveis + ")");
		}

		select.append(" order by item.sigla_iett");

		SQLQuery q = this.session.createSQLQuery(select.toString());

		if (codigo != null && !"".equals(codigo.trim())) {
			q.setString("codigo", codigo);
		}

		List objects = q.list();

		if (objects != null && !objects.isEmpty()) {
			for (Iterator it = objects.iterator(); it.hasNext();) {
				Object[] o = (Object[]) it.next();
				/*
				 * item ï¿½ um array de Object, onde:
				 * 
				 * posiï¿½ï¿½o | Conteudo
				 * ---------------------------------------- 0 | siglaIett 1 |
				 * nomeIett
				 */

				ItemWSMinBean item = new ItemWSMinBean();

				item.setCodItem((o[0] != null) ? o[0].toString() : "");
				item.setNomeItem((o[1] != null) ? o[1].toString() : "");
				retorno.add(item);
			}
		}
		return retorno;
	}

	/**
	 * Retorna um item de determinada sigla em uma estrutura.<br>
	 * Caso estrutura seja nula, a pesquisa ï¿½ feita em todas as estruturas.
	 * 
	 * @author aleixo
	 * @since 27/07/2007
	 * @param sigla
	 * @param estrutura
	 * @return ItemEstruturaIett
	 * @throws ECARException
	 */
	public ItemEstruturaIett getIettBySigla(String sigla, EstruturaEtt estrutura) throws ECARException {
		try {
			StringBuilder s = new StringBuilder();
			s.append("select iett from ItemEstruturaIett iett where iett.siglaIett = :sigla and iett.indAtivoIett = 'S'");

			if (estrutura != null) {
				s.append(" and iett.estruturaEtt.codEtt = :estrutura");
			}

			Query q = this.session.createQuery(s.toString());

			q.setString("sigla", sigla);

			if (estrutura != null) {
				q.setLong("estrutura", estrutura.getCodEtt().longValue());
			}

			q.setMaxResults(1);

			Object o = q.uniqueResult();

			return (o != null) ? (ItemEstruturaIett) o : null;

		} catch (Exception e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}

	/**
	 * Retorna os Itens de Estrutura que podem ser selecionados de acordo com a
	 * Sala de Situaï¿½ï¿½o.<br>
	 * 
	 * @author
	 * @since
	 * @param listaItensSelecionaveis
	 * @param ta
	 *            Tipo de Acompanhamento onde estï¿½ definido Sala de
	 *            Situaï¿½ï¿½o
	 * @return List Lista com os itens de estrutura selecionï¿½veis filtrados
	 *         pela Sala de Situaï¿½ï¿½o
	 * @throws ECARException
	 */
	public List getItensSelecionaveisFiltradosPorAtributo(List listaItensSelecionaveis, TipoAcompanhamentoTa ta) throws ECARException {

		List listaRetorno = new ArrayList();
		List listaIettPai = new ArrayList();
		EstruturaAtributoDao estruturaAtributoDao = new EstruturaAtributoDao(request);

		ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);

		try {

			boolean tipoAcompPossuiNivelPlanejamento = false;

			if (ta.getSisAtributoSatbs() != null && ta.getSisAtributoSatbs().size() > 0) {

				Iterator itSisAtributosTa = ta.getSisAtributoSatbs().iterator();

				while (itSisAtributosTa.hasNext()) {

					SisAtributoSatb sisAtributo = (SisAtributoSatb) itSisAtributosTa.next();

					// Se for nivel de planejamento
					if (sisAtributo.getSisGrupoAtributoSga().equals(configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan())) {

						tipoAcompPossuiNivelPlanejamento = true;
						break;
					}
				}
			}

			if (listaItensSelecionaveis != null && listaItensSelecionaveis.size() > 0 && tipoAcompPossuiNivelPlanejamento) {

				// if (ta.getSisAtributoSatbs() != null &&
				// ta.getSisAtributoSatbs().size() > 0){
				//
				// Iterator itSisAtributosTA =
				// ta.getSisAtributoSatbs().iterator();
				//
				// ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(null);
				// boolean tipoAcompPossuiNivelPlanejamento = false;
				//
				// while (itSisAtributosTA.hasNext()){
				//
				// SisAtributoSatb sisAtb = (SisAtributoSatb)
				// itSisAtributosTA.next();
				//
				// if
				// (sisAtb.getSisGrupoAtributoSga().equals(configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan())){
				// tipoAcompPossuiNivelPlanejamento = true;
				// break;
				// }
				// }

				// if (tipoAcompPossuiNivelPlanejamento){

				Iterator itListaItensSelecionaveis = listaItensSelecionaveis.iterator();

				// percorre a lista de itens selecionaveis
				while (itListaItensSelecionaveis.hasNext()) {

					// AtributoEstruturaListagemItens atbList =
					// (AtributoEstruturaListagemItens)
					// itListaItensSelecionaveis.next();
					ItemEstruturaIett iett = (ItemEstruturaIett) itListaItensSelecionaveis.next();

					if (verificaNivelHierarquicoEstrutura(ta.getEstruturaNivelGeracaoTa(), iett.getEstruturaEtt()) && iett.getItemEstruturaNivelIettns() != null
							&& iett.getItemEstruturaNivelIettns().size() > 0) {
						Iterator itListaAtributoItemEstrutura = iett.getItemEstruturaNivelIettns().iterator();

						// percorre a lista de atributos de cada item de
						// estrutura
						while (itListaAtributoItemEstrutura.hasNext()) {
							SisAtributoSatb nivel = (SisAtributoSatb) itListaAtributoItemEstrutura.next();

							if (nivel.getSisGrupoAtributoSga().equals(configuracaoDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan()) && ta.getSisAtributoSatbs().contains(nivel)) {
								if (iett.getItemEstruturaIett() != null && !listaIettPai.contains(iett.getItemEstruturaIett())) {
									Iterator itListaRetorno = listaRetorno.iterator();
									boolean achou = false;
									while (itListaRetorno.hasNext()) {
										ItemEstruturaIett iettRetorno = (ItemEstruturaIett) itListaRetorno.next();
										if (iettRetorno.equals(iett.getItemEstruturaIett())) {
											achou = true;
										}
									}
									if (!achou) {
										listaRetorno.add(iett.getItemEstruturaIett());
										listaIettPai.add(iett.getItemEstruturaIett());
									}
								}
								listaRetorno.add(iett);
							}
						}
					}

				} // fim do while

				return listaRetorno;
			}// fim do if
			else {

				Iterator itItensSelecionaveis = listaItensSelecionaveis.iterator();
				List itensSelecionaveis = new ArrayList();

				while (itItensSelecionaveis.hasNext()) {

					ItemEstruturaIett iett = (ItemEstruturaIett) itItensSelecionaveis.next();
					// Verifica se o item
					if (verificaNivelHierarquicoEstrutura(ta.getEstruturaNivelGeracaoTa(), iett.getEstruturaEtt())) {

						itensSelecionaveis.add(iett);
					}
				}

				return itensSelecionaveis;

			}

			// return listaRetorno;

		} catch (Exception e) {
			this.logger.error(e);
			throw new ECARException(e);
		}

	}

	/**
	 * Ordena os itens de acordo com o que foi definido nas configuraï¿½ï¿½es de
	 * tipo de Acompanhamento, montando a hierarquia dos itens.
	 * 
	 * @param itens
	 * @param tipoAcomp
	 * @return List
	 * @throws ECARException
	 * @author Josï¿½ Andrï¿½
	 */
	public List getItensOrdenados(List itens, TipoAcompanhamentoTa tipoAcomp) throws ECARException {

		return getItensOrdenados(itens, tipoAcomp, true);

	}

	/**
	 * Retorna os SisAtributos do grupo e do Iett passados.<br>
	 * 
	 * @author
	 * @since 27/07/2008
	 * @param iett
	 * @param codSisGrupoAtributo
	 * @return List
	 */
	public List getSisAtributosIett(ItemEstruturaIett iett, Long codSisGrupoAtributo) {
		List listaRetorno = new ArrayList();
		if (iett.getItemEstruturaSisAtributoIettSatbs() != null) {
			Iterator it = iett.getItemEstruturaSisAtributoIettSatbs().iterator();
			while (it.hasNext()) {
				ItemEstruturaSisAtributoIettSatb iettSatb = (ItemEstruturaSisAtributoIettSatb) it.next();
				if (iettSatb.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga().equals(codSisGrupoAtributo)) {
					listaRetorno.add(iettSatb.getSisAtributoSatb());
				}
			}
		}

		return listaRetorno;

	}

	/**
	 * Retorna os ItemEstruturaSisAtributoIettSatbs do grupo e do Iett passados.<br>
	 * 
	 * @author
	 * @since 27/07/2008
	 * @param iett
	 * @param codSisGrupoAtributo
	 * @return List
	 */
	public List getItemEstruturaSisAtributoIettSatbsIett(ItemEstruturaIett iett, Long codSisGrupoAtributo) {
		List listaRetorno = new ArrayList();
		if (iett.getItemEstruturaSisAtributoIettSatbs() != null) {
			Iterator it = iett.getItemEstruturaSisAtributoIettSatbs().iterator();
			while (it.hasNext()) {
				ItemEstruturaSisAtributoIettSatb iettSatb = (ItemEstruturaSisAtributoIettSatb) it.next();
				if (iettSatb.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga().equals(codSisGrupoAtributo)) {
					listaRetorno.add(iettSatb);
				}
			}
		}

		return listaRetorno;

	}

	/**
	 * Extrai de uma colection ordenada os filhos imediatos do itemEstrutura
	 * passado.
	 * 
	 * @param itemEstrutura
	 * @param colecaoItens
	 * @return Lista com os descendentes imediatos
	 */
	public List<AtributoEstruturaListagemItens> recuperaDescendentesImediatos(ItemEstruturaIett itemEstrutura, List<AtributoEstruturaListagemItens> colecaoItens) {
		List<AtributoEstruturaListagemItens> retornoList = new ArrayList<AtributoEstruturaListagemItens>();
		AtributoEstruturaListagemItens atEstListagem = null;
		ItemEstruturaIett item = null;
		Iterator colecaoItensIt = colecaoItens.iterator();

		while (colecaoItensIt.hasNext()) {
			atEstListagem = (AtributoEstruturaListagemItens) colecaoItensIt.next();
			item = atEstListagem.getItem();

			if (item.getCodIett().equals(itemEstrutura.getCodIett())) {
				while (colecaoItensIt.hasNext()) {
					atEstListagem = (AtributoEstruturaListagemItens) colecaoItensIt.next();
					item = atEstListagem.getItem();
					if (item.getNivelIett() == itemEstrutura.getNivelIett() + 1)
						retornoList.add(atEstListagem);
					if (item.getNivelIett() <= itemEstrutura.getNivelIett())
						break;
				}
				break;
			}
		}

		return retornoList;
	}

	/**
	 * Verifica em uma colection ordenada se determinado itemEstrutura passado
	 * tem filhos.
	 * 
	 * @param itemEstrutura
	 * @param colecaoItens
	 * @return Lista com os descendentes imediatos
	 */
	public boolean existeDescendentesImediatos(ItemEstruturaIett itemEstrutura, List<AtributoEstruturaListagemItens> colecaoItens) {
		boolean retorno = false;
		AtributoEstruturaListagemItens atEstListagem = null;
		ItemEstruturaIett item = null;
		Iterator colecaoItensIt = colecaoItens.iterator();

		while (colecaoItensIt.hasNext()) {
			atEstListagem = (AtributoEstruturaListagemItens) colecaoItensIt.next();
			item = atEstListagem.getItem();

			if (item.getCodIett().equals(itemEstrutura.getCodIett())) {
				if (colecaoItensIt.hasNext()) {
					atEstListagem = (AtributoEstruturaListagemItens) colecaoItensIt.next();
					item = atEstListagem.getItem();
					if (item.getNivelIett() == itemEstrutura.getNivelIett() + 1)
						retorno = true;
				}
				break;
			}
		}

		return retorno;
	}

	/**
	 * Verificar se o usuario eh funcao de acompanhamento com permissao de
	 * editar o campo mesmo que ele esteja bloqueado para planejamento
	 * 
	 * @param itemEstrutura
	 * @param atributo
	 * @param usuario
	 * @param gruposAcesso
	 * @return
	 */
	public boolean podeEditarAtributoBloqueadoNaEstrutura(ItemEstruturaIett itemEstrutura, ObjetoEstrutura atributo, UsuarioUsu usuario, Set gruposAcesso) {
		List funcoesAcompanhamenoDoUsuario = null;

		try {
			funcoesAcompanhamenoDoUsuario = new TipoFuncAcompDao(null).getFuncoesAcompNaEstruturaDoUsuario(itemEstrutura, usuario, gruposAcesso);
			Set liberadoParaFuncoesAcompanhamento = atributo.iGetLibTipoFuncAcompTpfas();

			for (Iterator<TipoFuncAcompTpfa> itFuncaosAcompDoUsuario = funcoesAcompanhamenoDoUsuario.iterator(); itFuncaosAcompDoUsuario.hasNext();) {
				TipoFuncAcompTpfa funcaoAcompDoUsuario = (TipoFuncAcompTpfa) itFuncaosAcompDoUsuario.next();

				if (liberadoParaFuncoesAcompanhamento != null && liberadoParaFuncoesAcompanhamento.contains(funcaoAcompDoUsuario)) {
					return true; // pode editar o campo
				}

			}

		} catch (ECARException e) {
			e.printStackTrace();
		}

		return false;
	}

	private boolean podeEditarAtributoBloqueadoNaEstrutura(ItemEstruturaIett itemEstrutura, ObjetoEstrutura atributo, List funcoesAcompanhamenoDoUsuario) {

		Set liberadoParaFuncoesAcompanhamento = atributo.iGetLibTipoFuncAcompTpfas();

		for (Iterator<TipoFuncAcompTpfa> itFuncaosAcompDoUsuario = funcoesAcompanhamenoDoUsuario.iterator(); itFuncaosAcompDoUsuario.hasNext();) {
			TipoFuncAcompTpfa funcaoAcompDoUsuario = (TipoFuncAcompTpfa) itFuncaosAcompDoUsuario.next();

			if (liberadoParaFuncoesAcompanhamento != null && liberadoParaFuncoesAcompanhamento.contains(funcaoAcompDoUsuario)) {
				return true; // pode editar o campo
			}

		}

		return false;
	}

	/**
	 * 
	 * @param inicio
	 * @param fim
	 * @param tipoHistorico
	 * @param codigos
	 * @return
	 */
	@SuppressWarnings("empty-statement")
	public List<HistoricoXml> listaHistorico(Date inicio, Date fim, String[] tipoHistorico, String[] codigos) {
		Historico<HistoricoItemEstruturaIett, ItemEstruturaIett> historico = new Historico<HistoricoItemEstruturaIett, ItemEstruturaIett>() {
		};
		try {
			return historico.listaHistorico(null, inicio, fim, tipoHistorico, codigos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
		return null;
	}

	/**
	 * 
	 * @param inicio
	 * @param fim
	 * @param tipoHistorico
	 * @param codigos
	 * @return
	 */
	@SuppressWarnings("empty-statement")
	public List<HistoricoItemEstruturaIett> listaObjetoHistorico(Date inicio, Date fim, String[] tipoHistorico, String[] codigos) {
		Historico<HistoricoItemEstruturaIett, ItemEstruturaIett> historico = new Historico<HistoricoItemEstruturaIett, ItemEstruturaIett>() {
		};
		try {
			return historico.listaObjetoHistorico(null, inicio, fim, tipoHistorico, codigos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		;
		return null;
	}

	/**
	 * Ordena uma lista de itens de estrutura pelo titulo a ser apresentado na
	 * arvore ajax Nï¿½o considera a hierarquia.
	 * 
	 * 
	 * @param itens
	 *            - Lista de itens de estrutura a serem ordenados
	 */
	public void ordenaListaItensListaModelo(List itens) throws ECARException {

		Collections.sort(itens, new Comparator() {
			public int compare(Object o1, Object o2) {
				ItemEstruturaIett item1 = (ItemEstruturaIett) o1;
				ItemEstruturaIett item2 = (ItemEstruturaIett) o2;

				EstruturaDao estruturaDao = new EstruturaDao(null);

				EstruturaEtt estruturaItem1;
				EstruturaEtt estruturaItem2;
				String key1 = "";
				String key2 = "";

				try {
					estruturaItem1 = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, item1.getEstruturaEtt().getCodEtt());
					// no metodo abaixo item.getEstruturaEtt() causa um null
					// pointer, por isso precisamos da linha acima
					key1 = new ItemEstruturaDao(request).criaColunaConteudoColunaArvoreAjax(item1, estruturaItem1);

					estruturaItem2 = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, item2.getEstruturaEtt().getCodEtt());
					// no metodo abaixo item.getEstruturaEtt() causa um null
					// pointer, por isso precisamos da linha acima
					key2 = new ItemEstruturaDao(request).criaColunaConteudoColunaArvoreAjax(item2, estruturaItem2);

				} catch (ECARException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return key1.compareToIgnoreCase(key2);

			}
		});

	}

	/**
	 * Pesquisa todos os itens de Estrutura que sï¿½o modelo e possuam o nome
	 * enviado como parï¿½metro nas estruturas marcadas
	 * 
	 * @param item
	 *            - Item pai- usado na recursividade para buscar seus filhos
	 * @param estrutura
	 *            - Usado como parametro para o primeiro nï¿½vel de busca nesse
	 *            algoritmo recursivo
	 * @param nomePesquisa
	 *            - Filtro de nome indicado pelo usuï¿½rio
	 * @param indCopiar
	 *            - Indicador da pï¿½gina de criar item a partir de modelo. Se
	 *            diferente de <b>todaEstrutura</b> apenas busca itens no nivel
	 *            da estrutura informada. Caso seja igual a "todaEstrutura"
	 *            fazer a busca pelos itens filhos que tambï¿½m sï¿½o modelo
	 * @param seguranca
	 * @return
	 * @throws ECARException
	 */
	public List getListaItensModelo(ItemEstruturaIett item, EstruturaEtt estrutura, String nomePesquisa, String indCopiar, SegurancaECAR seguranca) throws ECARException {
		List lista = new ArrayList();

		Criteria select = session.createCriteria(ItemEstruturaIett.class);
		if (estrutura != null) {
			select.add(Restrictions.eq("estruturaEtt", estrutura));
		}

		if (item != null) {
			select.add(Restrictions.eq("itemEstruturaIett", item));
		}

		if (nomePesquisa != null && !nomePesquisa.equals(Dominios.STRING_VAZIA)) {
			select.add(Restrictions.ilike("nomeIett", "%" + nomePesquisa + "%"));
		}

		List pais = select.add(Restrictions.eq("indModeloIett", Dominios.SIM)).add(Restrictions.eq("indAtivoIett", Dominios.SIM)).addOrder(Order.asc("nomeIett")).list();

		// ordena pelos campos apresentados na arvore ajax
		ordenaListaItensListaModelo(pais);

		// Verifica permissï¿½es de acesso
		ValidaPermissao validaPermissao = new ValidaPermissao();

		if (indCopiar != null && indCopiar.equals("todaEstrutura")) {
			Iterator it = pais.iterator();
			while (it.hasNext()) {
				item = (ItemEstruturaIett) it.next();

				validaPermissao.permissoesItem(item, seguranca.getUsuario(), seguranca.getGruposAcesso());

				if (validaPermissao.permissaoConsultarItem()) {
					lista.add(item);
					lista.addAll(this.getListaItensModelo(item, null, null, "todaEstrutura", seguranca));
				}
			}
		} else {
			lista = pais;
		}

		Iterator itItens = lista.iterator();
		while (itItens.hasNext()) {

			item = (ItemEstruturaIett) itItens.next();

			validaPermissao.permissoesItem(item, seguranca.getUsuario(), seguranca.getGruposAcesso());

			if (!validaPermissao.permissaoConsultarItem()) {
				itItens.remove();
			}
		}

		return lista;

	}

	/**
	 * 
	 * @param chaveHistorico
	 * @return
	 */
	@SuppressWarnings("empty-statement")
	public HistoricoItemEstruturaIett getHistorico(Long chaveHistorico) {
		Historico<HistoricoItemEstruturaIett, ItemEstruturaIett> historico = new Historico<HistoricoItemEstruturaIett, ItemEstruturaIett>() {
		};
		try {
			return historico.getObjetoSerializado(chaveHistorico);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		return null;
	}

	/**
	 * 
	 * Pesquisa todos os itens de Estrutura que possuam o nome enviado como
	 * parï¿½metro nas estruturas marcadas
	 * 
	 * @param estruturasSelecionadas
	 * @param nomeItemPesquisar
	 * @param seguranca
	 * @return
	 * @throws ECARException
	 */
	public List pesquisarItemNaEstrutura(String[] estruturasSelecionadas, String nomeItemPesquisar, SegurancaECAR seguranca) throws ECARException {

		List<Integer> lista = new ArrayList<Integer>();
		StringBuilder query = new StringBuilder();
		Map<EstruturaEtt, List<ItemEstruturaIett>> map = new HashMap<EstruturaEtt, List<ItemEstruturaIett>>();
		List<ItemEstruturaIett> itensEstrutura;

		for (int i = 0; i < estruturasSelecionadas.length; i++) {
			if (estruturasSelecionadas[i] != null) {
				Integer codigoEstrutura = Integer.parseInt(estruturasSelecionadas[i]);

				lista.add(codigoEstrutura);
			}
		}

		query.append("select itemEstrutura from ItemEstruturaIett itemEstrutura");
		query.append(" inner join itemEstrutura.estruturaEtt estrutura ");
		query.append(" where itemEstrutura.indAtivoIett = 'S' and");
		query.append(" 		 estrutura.codEtt in (:grupo)");

		if (nomeItemPesquisar != null && !nomeItemPesquisar.equals(Dominios.STRING_VAZIA)) {
			query.append(" and Lower(itemEstrutura.nomeIett) like :nome ");
		}
		query.append(" order by itemEstrutura.nomeIett ");

		try {
			Query _obj = this.getSession().createQuery(query.toString());

			_obj.setParameterList("grupo", lista, new IntegerType());

			if (nomeItemPesquisar != null && !nomeItemPesquisar.equals(Dominios.STRING_VAZIA)) {
				_obj.setString("nome", "%" + nomeItemPesquisar.toLowerCase() + "%");
			}

			itensEstrutura = _obj.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}

		return itensEstrutura;
	}

	/**
	 * Retorna as Estruturas virtuais cujo o item estï¿½ associado.
	 * 
	 * @param item
	 * @return
	 */
	public Set pesquisarEstruturasVirtuaisAssociadas(ItemEstruturaIett item) {

		if (!getSession().contains(item)) {

			item = (ItemEstruturaIett) getSession().load(ItemEstruturaIett.class, item.getCodIett());
		}

		Hibernate.initialize(item.getEstruturasVirtual());

		return item.getEstruturasVirtual();

	}

	/**
	 * Gera uma lista com os nï¿½s da ï¿½rvore que formam o caminho entre o item
	 * superior e o item inferior. A lista comeï¿½a com o identificador do item
	 * superior e termina com o identificador do item inferior. O formato do
	 * codItemInferior, codItemSuperior, codPaiEstrutura e dos itens da lista de
	 * retorno obedecem o seguinte padrï¿½o: Se for um itemEstrutura ->
	 * "iett<codIett>" Se for uma estrutura -> "ett<codEtt>"
	 * 
	 * @param codItemInferior
	 *            - item inferior da ï¿½rvore
	 * @param codItemSuperior
	 *            - item superior da ï¿½rvore
	 * @param codPaiEstrutura
	 *            - atributo opcional, sï¿½ ï¿½ usado quando "codItemInferior"
	 *            ï¿½ uma estrutura
	 * @param codEstruturaFilhaVirtualExpandida
	 *            - atributo opcional, sï¿½ ï¿½ usado quando a estrutura for
	 *            "filha" de uma estrutura virtual
	 * @return
	 */
	public List geraCaminhoArvoreCadastro(String codItemInferior, String codItemSuperior, String codPaiEstrutura, String codEstruturaFilhaVirtualExpandida) {
		ArrayList<String> caminho = new ArrayList<String>();

		try {
			// no caso do filho ser um item estrutura
			if (codItemInferior.startsWith("iett")) {
				ItemEstruturaIett itemEstrutura = null;
				String codIett = "";
				ItemEstruturaDao itemDao = new ItemEstruturaDao(null);

				codIett = codItemInferior.substring(4, codItemInferior.length());
				itemEstrutura = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, new Long(codIett));

				do {
					// salva o cï¿½digo do prï¿½prio item
					caminho.add("iett" + itemEstrutura.getCodIett());
					if (codItemSuperior.startsWith("iett") && codItemSuperior.substring(4, codItemSuperior.length()).equals(itemEstrutura.getCodIett().toString())) {
						break;
					}

					// salva o cï¿½digo da estrutura a que ele pertence
					caminho.add("ett" + itemEstrutura.getEstruturaEtt().getCodEtt());
					if (codItemSuperior.startsWith("ett") && codItemSuperior.substring(3, codItemSuperior.length()).equals(itemEstrutura.getEstruturaEtt().getCodEtt().toString())) {
						break;
					}

					itemEstrutura = itemEstrutura.getItemEstruturaIett();
				} while (itemEstrutura != null);

			}

			// no caso do filho ser uma estrutura
			else if (codItemInferior.startsWith("ett")) {

				EstruturaDao estruturaDao = new EstruturaDao(null);
				String codEtt = codItemInferior.substring(3, codItemInferior.length());
				EstruturaEtt estrutura = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, new Long(codEtt));

				caminho.add("ett" + estrutura.getCodEtt());

				if (codPaiEstrutura != null && !codPaiEstrutura.equals("iett")) {

					ItemEstruturaIett itemEstrutura = null;
					String codIett = "";
					ItemEstruturaDao itemDao = new ItemEstruturaDao(null);

					codIett = codPaiEstrutura.substring(4, codPaiEstrutura.length());
					itemEstrutura = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, new Long(codIett));

					do {
						// salva o cï¿½digo do prï¿½prio item
						caminho.add("iett" + itemEstrutura.getCodIett());
						if (codItemSuperior.startsWith("iett") && codItemSuperior.substring(4, codItemSuperior.length()).equals(itemEstrutura.getCodIett().toString())) {
							break;
						}

						// salva o cï¿½digo da estrutura a que ele pertence
						caminho.add("ett" + itemEstrutura.getEstruturaEtt().getCodEtt());
						if (codItemSuperior.startsWith("ett") && codItemSuperior.substring(3, codItemSuperior.length()).equals(itemEstrutura.getEstruturaEtt().getCodEtt().toString())) {
							break;
						}

						itemEstrutura = itemEstrutura.getItemEstruturaIett();
					} while (itemEstrutura != null);

				}

				if (estrutura.isVirtual()) {

					// salva o codigo dos itens aos quais a estrutura pertence
					Map mapItensEstruturaVirtual = null;
					ActionEstrutura action = new ActionEstrutura();
					mapItensEstruturaVirtual = action.montarMapItensEstruturaVirtual(estrutura);

					caminho.remove("ett" + estrutura.getCodEtt());
					Iterator itEstruturas = mapItensEstruturaVirtual.keySet().iterator();

					while (itEstruturas.hasNext()) {
						EstruturaEtt estruturaFilha = (EstruturaEtt) itEstruturas.next();
						if (codEstruturaFilhaVirtualExpandida != null && codEstruturaFilhaVirtualExpandida.equals(estruturaFilha.getCodEtt().toString())) {
							List listaItensEstrutura = (List) mapItensEstruturaVirtual.get(estruturaFilha);
							Iterator itLisItensEstrutura = listaItensEstrutura.iterator();
							while (itLisItensEstrutura.hasNext()) {
								ItemEstruturaIett item = (ItemEstruturaIett) itLisItensEstrutura.next();
								caminho.add("iett" + item.getCodIett() + "_pai_" + "ett" + estrutura.getCodEtt());
							}
							caminho.add("ett" + estruturaFilha.getCodEtt() + "v");
							break;
						}

					}

					caminho.add("ett" + estrutura.getCodEtt());
				}
			}

		} catch (Exception e) {
			// Nï¿½o precisa levantar exceï¿½ï¿½o, sï¿½ retorna a lista vazia
			caminho = new ArrayList<String>();
		}

		// inverte a lista
		Collections.reverse(caminho);

		return caminho;
	}

	/**
	 * 
	 * 
	 * @param estrutura
	 *            - Estrura do primero nivel
	 * @param listItens
	 *            - esta lista jï¿½ deve estar na ordem de apresentaï¿½ï¿½o.
	 * @return Map com a chave a estrutura e o valor o nivel da estrutura
	 */
	public Map<EstruturaEtt, Integer> montaMapEstruturaNivel(EstruturaEtt estrutura, List listItens) {
		Map mapEstruturasNivel = new HashMap<EstruturaEtt, Integer>();
		int nivel;
		Iterator itItens = listItens.iterator();
		ItemEstruturaIett itemPrimeiroNivel = null;
		ItemEstruturaIett itemAnterior = null;

		mapEstruturasNivel.put(estrutura, new Integer(0));

		while (itItens.hasNext()) {
			ItemEstruturaIett item = (ItemEstruturaIett) itItens.next();

			if (item.getEstruturaEtt().getCodEtt().equals(estrutura.getCodEtt())) {
				nivel = 0;
				itemPrimeiroNivel = item;
				// Verifica se o item anterior ï¿½ pai do item atual
			} else if (mapEstruturasNivel.containsKey(item.getEstruturaEtt())) {
				nivel = ((Integer) mapEstruturasNivel.get(item.getEstruturaEtt())).intValue();
			} else { // if (
						// itemAnterior.getCodIett().equals(item.getItemEstruturaIett().getCodIett()
						// ) ) {
				int nivelPai = ((Integer) mapEstruturasNivel.get(item.getItemEstruturaIett().getEstruturaEtt())).intValue();
				nivel = ++nivelPai;
				mapEstruturasNivel.put(item.getEstruturaEtt(), Integer.valueOf(nivel));
			}
			itemAnterior = item;
		}// fim while

		return mapEstruturasNivel;
	}

	/**
	 * 
	 * 
	 * @param mapItensFuncoes
	 * @param usuarioLogado
	 * @return
	 * @throws ECARException
	 */
	public ItemEstruturaIett criarCopiaItensFuncoes(Map mapItensFuncoes, UsuarioUsu usuarioLogado, ItemEstruturaIett itemEstruturaSelecionado) throws ECARException {

		Transaction tx = null;
		ArrayList objetos = new ArrayList();
		ItemEstruturaIett itemPai = null; // Item do primeiro nivel, serï¿½
											// exibido apos a operaï¿½ï¿½o

		try {

			super.inicializarLogBean();

			tx = session.beginTransaction();

			Iterator<ItemEstruturaIett> itItem = null; // Iterador com a chave
														// do map contendo o
														// conjunto de itens que
														// se deseja copiar
			ItemEstruturaIett item = null; // Item que se deseja criar a copia
			List listFuncoes = null; // Lista das funï¿½ï¿½es que se deseja
										// copiar do item
			boolean ehPaiMaster = true;

			itItem = mapItensFuncoes.keySet().iterator();

			// A transaï¿½ï¿½o ï¿½ para comeï¿½ar aqui pois se der erro na copia
			// de algum item a operaï¿½ï¿½o deverï¿½ ser abortada

			// Esta estrutura representa a correspondï¿½ncia entre o
			// ItemEstrutura antigo e novo que foi inserido como modelo,
			// para servir de suporte ï¿½ associaï¿½ï¿½o do novo Item filho ao
			// novo Item pai
			Map itensInseridos = new LinkedMap();

			while (itItem.hasNext()) {
				item = (ItemEstruturaIett) itItem.next();

				listFuncoes = (List) mapItensFuncoes.get(item);

				if (ehPaiMaster) {
					itemPai = copiarItemComoModelo(item, listFuncoes, itensInseridos, objetos, usuarioLogado, itemEstruturaSelecionado);
					ehPaiMaster = false;
				} else {
					copiarItemComoModelo(item, listFuncoes, itensInseridos, objetos, usuarioLogado, itemEstruturaSelecionado);
				}
			}

			tx.commit();

		} catch (HibernateException e) {

			itemPai = null;// operaï¿½ï¿½o abortada, nï¿½o salvou nenhum pai

			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}

		if (super.logBean != null) {
			super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
			super.logBean.setOperacao("ALT");
			Iterator itObj = objetos.iterator();

			while (itObj.hasNext()) {
				super.logBean.setObj(itObj.next());
				super.loggerAuditoria.info(logBean.toString());
			}
		}

		return itemPai;
	}

	/**
	 * Gera e retorna um novo ï¿½tem contendo os mesmos atributos e
	 * relacionamentos das funï¿½ï¿½es passadas por parï¿½metro, do
	 * ItemEstrutura passado como parï¿½metro
	 * 
	 * @param itemOrigem
	 * @param funcoes
	 * @param itensInseridos
	 * @param usuarioLogado
	 * @param objetos
	 * @return ItemEstruturaIett
	 * @throws ECARException
	 */
	public ItemEstruturaIett copiarItemComoModelo(ItemEstruturaIett itemOrigem, List funcoes, Map itensInseridos, List objetos, UsuarioUsu usuarioLogado, ItemEstruturaIett itemEstruturaSelecionado)
			throws ECARException {

		ItemEstruturaIett novoItem = new ItemEstruturaIett();

		// Seta a EstruturaEtt
		novoItem.setEstruturaEtt(itemOrigem.getEstruturaEtt());

		// Seta os atributos de Dados Gerais
		copiarDadosGerais(itemOrigem, novoItem, usuarioLogado);

		// Seta o pai do novo item, acessando o Map de correspondï¿½ncia entre o
		// pai antigo e o pai novo
		if (itemOrigem.getItemEstruturaIett() != null) {

			if ((ItemEstruturaIett) itensInseridos.get(itemOrigem.getItemEstruturaIett()) != null) {
				ItemEstruturaIett itemPai = (ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, ((ItemEstruturaIett) itensInseridos.get(itemOrigem.getItemEstruturaIett())).getCodIett());
				novoItem.setItemEstruturaIett(itemPai);
			}

			// o pai nï¿½o ï¿½ um ï¿½tem ja inserido. Serï¿½ igual ao pai do
			// item de origem
			if (novoItem.getItemEstruturaIett() == null) {

				// novoItem.setItemEstruturaIett(itemOrigem.getItemEstruturaIett());
				novoItem.setItemEstruturaIett(itemEstruturaSelecionado);
			}
		}

		novoItem.setItemEstUsutpfuacIettutfas(copiarFuncaoAcompanhamentoDadosGerais(itemOrigem, novoItem, usuarioLogado));

		List filhos = new ArrayList();
		if (novoItem.getItemEstUsutpfuacIettutfas() != null) {
			filhos.addAll(novoItem.getItemEstUsutpfuacIettutfas());
		}

		session.save(novoItem);
		objetos.add(novoItem);

		Set<ItemEstrutUsuarioIettus> permissoesObtidas = new ControlePermissao().getPermissoesItemEstruturaUsarModelo(novoItem, request);

		// Salva os itens de permissï¿½es obtidos
		for (ItemEstrutUsuarioIettus itemEstrutUser : permissoesObtidas) {
			session.save(itemEstrutUser);
			objetos.add(itemEstrutUser);

		}

		// Permissï¿½es de acesso para as funï¿½ï¿½es de acompanhamento e
		// funï¿½ï¿½es de seu pai que estï¿½o como manter no prï¿½ximo nï¿½vel.

		// gravar permissï¿½o para o usuï¿½rio que criou o item
		ItemEstrutUsuarioIettus itemEstrutUsuario = new ItemEstrutUsuarioIettus();

		itemEstrutUsuario.setItemEstruturaIett(novoItem);
		itemEstrutUsuario.setItemEstruturaIettOrigem(novoItem);
		itemEstrutUsuario.setCodTpPermIettus(ControlePermissao.PERMISSAO_USUARIO);
		itemEstrutUsuario.setUsuarioUsu(novoItem.getUsuarioUsuByCodUsuIncIett());

		itemEstrutUsuario.setIndLeituraIettus("S");
		itemEstrutUsuario.setIndEdicaoIettus("S");
		itemEstrutUsuario.setIndExcluirIettus("S");

		itemEstrutUsuario.setIndAtivMonitIettus("N");
		itemEstrutUsuario.setIndDesatMonitIettus("N");
		itemEstrutUsuario.setIndBloqPlanIettus("N");
		itemEstrutUsuario.setIndDesblPlanIettus("N");
		itemEstrutUsuario.setIndInfAndamentoIettus("N");
		itemEstrutUsuario.setIndEmitePosIettus("N");
		itemEstrutUsuario.setIndProxNivelIettus("N");

		itemEstrutUsuario.setDataInclusaoIettus(Data.getDataAtual());

		Iterator itFilhos = filhos.iterator();
		while (itFilhos.hasNext()) {
			PaiFilho object = (PaiFilho) itFilhos.next();
			object.atribuirPKPai();
			// salva os filhos
			session.save(object);
			objetos.add(object);
		}

		session.save(itemEstrutUsuario);
		objetos.add(itemEstrutUsuario);

		// Atributos livres
		List atributosLivresDadosGerais = copiarAtributosLivresDadosGerais(itemOrigem, novoItem, usuarioLogado);

		for (Iterator it = atributosLivresDadosGerais.iterator(); it.hasNext();) {
			ItemEstruturaSisAtributoIettSatb atbLivre = (ItemEstruturaSisAtributoIettSatb) it.next();
			session.save(atbLivre);
			objetos.add(atbLivre);
		}

		// Seta os atributos de outras funï¿½ï¿½es
		Iterator itFuncoes = funcoes.iterator();

		while (itFuncoes.hasNext()) {

			FuncaoFun funcao = (FuncaoFun) itFuncoes.next();
			String nomeFuncao = funcao.getNomeFun();

			if (nomeFuncao.equals(FuncaoDao.NOME_FUNCAO_AGENDA)) {
				novoItem.setAgendaAge(copiarAgendas(novoItem, itemOrigem.getAgendaAge(), usuarioLogado));
			} // Editores leitores = permissï¿½o de acesso
				// else if
				// (nomeFuncao.equals(FuncaoDao.NOME_FUNCAO_EDITORES_LEITORES)){

			// novoItem.setItemEstrutUsuarioIettusesByCodIett
			// (copiarEditoresLeitores(itemOrigem, novoItem, itensInseridos,
			// usuarioLogado));
			// copiouPermissoes = true;
			// }
			else if (nomeFuncao.equals(FuncaoDao.NOME_FUNCAO_ASSOCIACAO_DEMANDAS)) {

				// novoItem.setItemRegdemandaIregds(copiarDemandasAssociadas(novoItem,
				// itemOrigem.getItemRegdemandaIregds()));
				copiarDemandasAssociadas(novoItem, itemOrigem.getItemRegdemandaIregds());
			} else if (nomeFuncao.equals(FuncaoDao.NOME_FUNCAO_BENEFICIARIO)) {

				novoItem.setItemEstrtBenefIettbs(copiarBeneficiarios(novoItem, itemOrigem.getItemEstrtBenefIettbs(), usuarioLogado));
			} else if (nomeFuncao.equals(FuncaoDao.NOME_FUNCAO_CATEGORIAS)) {

				novoItem.setItemEstrUplCategIettucs(copiarCategorias(novoItem, itemOrigem.getItemEstrUplCategIettucs(), usuarioLogado));
			} else if (nomeFuncao.equals(FuncaoDao.NOME_FUNCAO_CRITERIOS)) {

				novoItem.setItemEstrutCriterioIettcs(copiarCriterios(novoItem, itemOrigem.getItemEstrutCriterioIettcs(), usuarioLogado));
			} else if (nomeFuncao.equals(FuncaoDao.NOME_FUNCAO_ENTIDADES)) {

				novoItem.setItemEstrutEntidadeIettes(copiarEntidades(novoItem, itemOrigem.getItemEstrutEntidadeIettes(), usuarioLogado));
			} else if (nomeFuncao.equals(FuncaoDao.NOME_FUNCAO_EVENTOS)) { // tambï¿½m
																			// conhecida
																			// como
																			// Aï¿½ï¿½O
																			// e
																			// Diï¿½rio
																			// de
																			// bordo

				novoItem.setItemEstrutAcaoIettas(copiarEventos(novoItem, itemOrigem.getItemEstrutAcaoIettas(), usuarioLogado));
			} else if (nomeFuncao.equals(FuncaoDao.NOME_FUNCAO_LOCALIZACAO)) {

				novoItem.setItemEstrutLocalIettls(copiarLocalizacao(novoItem, itemOrigem.getItemEstrutLocalIettls(), usuarioLogado));
			} else if (nomeFuncao.equals(FuncaoDao.NOME_FUNCAO_METAS_INDICADORES)) {
				// Tambï¿½m conhecido como indicadores de resultados
				novoItem.setItemEstrtIndResulIettrs(copiarMetasIndicadores(novoItem, itemOrigem.getItemEstrtIndResulIettrs(), usuarioLogado));
			}
			// else if
			// (nomeFuncao.equals(FuncaoDao.NOME_FUNCAO_FONTES_RECURSOS)){
			// //tambï¿½m conhecida como Aï¿½ï¿½O e Diï¿½rio de bordo
			//
			// novoItem.setEfIettFonteTotEfiefts(copiarFonteRecursos(novoItem,
			// itemOrigem.getEfIettFonteTotEfiefts()));
			// }
			else if (nomeFuncao.equals(FuncaoDao.NOME_FUNCAO_PONTOS_CRITICOS)) { // tambï¿½m
																					// conhecida
																					// como
																					// Restriï¿½ï¿½es

				novoItem.setPontoCriticoPtcs(copiarPontosCriticos(novoItem, itemOrigem.getPontoCriticoPtcs(), usuarioLogado));
			}

		}

		// if (!copiouPermissoes){
		// copiarPermissoesFuncoesAcompanhamento(itemOrigem, novoItem,
		// itensInseridos, usuarioLogado);
		// }

		session.update(novoItem);
		// session.merge(novoItem);
		HistoricoItemEstruturaIett hiett = new HistoricoItemEstruturaIett();
		hiett.carregar(novoItem);
		gerarHistorico(hiett, Historico.INCLUIR);

		// atualiza Map de correspondï¿½ncia entre itens antigos e novos
		itensInseridos.put(itemOrigem, novoItem);

		return novoItem;
	}

	/**
	 * Copia o valor de todos os atributos de dados gerais, incluindo atributos
	 * livres, do ItemEstrutura modelo para o novo ItemEstrutura a ser
	 * incluï¿½do
	 * 
	 * @param itemOrigem
	 * @param novoItem
	 * @throws ECARException
	 */
	private void copiarDadosGerais(ItemEstruturaIett itemOrigem, ItemEstruturaIett novoItem, UsuarioUsu usuarioLogado) throws ECARException {

		novoItem.setNomeIett(itemOrigem.getNomeIett());

		novoItem.setValPrevistoFuturoIett(itemOrigem.getValPrevistoFuturoIett());
		novoItem.setIndBloqPlanejamentoIett(itemOrigem.getIndBloqPlanejamentoIett());
		novoItem.setBeneficiosIett(itemOrigem.getBeneficiosIett());
		novoItem.setOrigemIett(itemOrigem.getOrigemIett());
		novoItem.setObjetivoEspecificoIett(itemOrigem.getObjetivoEspecificoIett());
		novoItem.setObjetivoGeralIett(itemOrigem.getObjetivoGeralIett());
		novoItem.setIndMonitoramentoIett(itemOrigem.getIndMonitoramentoIett());
		novoItem.setIndCriticaIett(itemOrigem.getIndCriticaIett());
		novoItem.setDataInicioMonitoramentoIett(itemOrigem.getDataInicioMonitoramentoIett());
		novoItem.setDataTerminoIett(itemOrigem.getDataTerminoIett());
		novoItem.setDataInicioIett(itemOrigem.getDataInicioIett());
		novoItem.setIndAtivoIett(itemOrigem.getIndAtivoIett());
		novoItem.setDataUltManutencaoIett(Data.getDataAtual());
		novoItem.setDataInclusaoIett(Data.getDataAtual());
		novoItem.setDescricaoIett(itemOrigem.getDescricaoIett());
		novoItem.setSiglaIett(itemOrigem.getSiglaIett());

		novoItem.setDescricaoR5(itemOrigem.getDescricaoR5());
		novoItem.setDescricaoR4(itemOrigem.getDescricaoR4());
		novoItem.setDescricaoR3(itemOrigem.getDescricaoR3());
		novoItem.setDescricaoR2(itemOrigem.getDescricaoR2());
		novoItem.setDescricaoR1(itemOrigem.getDescricaoR1());

		novoItem.setDataR5(itemOrigem.getDataR5());
		novoItem.setDataR4(itemOrigem.getDataR4());
		novoItem.setDataR3(itemOrigem.getDataR3());
		novoItem.setDataR2(itemOrigem.getDataR2());
		novoItem.setDataR1(itemOrigem.getDataR1());

		novoItem.setNivelIett(itemOrigem.getNivelIett());
		novoItem.setOrgaoOrgByCodOrgaoResponsavel1Iett(itemOrigem.getOrgaoOrgByCodOrgaoResponsavel1Iett());
		novoItem.setOrgaoOrgByCodOrgaoResponsavel2Iett(itemOrigem.getOrgaoOrgByCodOrgaoResponsavel2Iett());

		// novoItem.setItemEstruturaIett(itemOrigem.getItemEstruturaIett());
		novoItem.setSubAreaSare(itemOrigem.getSubAreaSare());
		novoItem.setAreaAre(itemOrigem.getAreaAre());

		novoItem.setUsuarioUsuByCodUsuUltManutIett(usuarioLogado);
		novoItem.setUsuarioUsuByCodUsuIncIett(usuarioLogado);
		novoItem.setPeriodicidadePrdc(itemOrigem.getPeriodicidadePrdc());
		novoItem.setUnidadeOrcamentariaUO(itemOrigem.getUnidadeOrcamentariaUO());

		novoItem.setSituacaoSit(itemOrigem.getSituacaoSit());

		if (itemOrigem.getItemEstruturaNivelIettns() != null && !itemOrigem.getItemEstruturaNivelIettns().isEmpty()) {
			novoItem.setItemEstruturaNivelIettns(new HashSet());
			Iterator itItemEstruturaNivelIettns = itemOrigem.getItemEstruturaNivelIettns().iterator();
			while (itItemEstruturaNivelIettns.hasNext()) {
				SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) itItemEstruturaNivelIettns.next();
				novoItem.getItemEstruturaNivelIettns().add(this.buscar(SisAtributoSatb.class, sisAtributoSatb.getCodSatb()));
			}
		} else {
			novoItem.setItemEstruturaNivelIettns(null);

		}

	}

	/**
	 * Associa os atributos livres associados ao item original para o item novo
	 * 
	 * @param itemOrigem
	 * @param novoItem
	 * @return List de ItemEstruturaSisAtributoIettSatb
	 */
	private List copiarAtributosLivresDadosGerais(ItemEstruturaIett itemOrigem, ItemEstruturaIett novoItem, UsuarioUsu usuarioLogado) throws ECARException {

		// Cï¿½PIA DOS ATRIBUTOS LIVRES

		List atributosLivresDadosGerais = new ArrayList();
		ActionSisAtributo action = new ActionSisAtributo();

		FuncaoFun funcaoDadosGerais = new FuncaoFun();
		funcaoDadosGerais.setNomeFun(FuncaoDao.NOME_FUNCAO_DADOS_GERAIS);

		ConfiguracaoCfg configuracaoCfg = new ConfiguracaoDao(request).getConfiguracao();

		List funcoes = null;
		try {
			funcoes = (new FuncaoDao(request)).pesquisar(funcaoDadosGerais, null);

			if (funcoes != null && funcoes.size() > 0) {
				funcaoDadosGerais = (FuncaoFun) funcoes.get(0);
			}

			Iterator itItemEstruturaSisAtributos = itemOrigem.getItemEstruturaSisAtributoIettSatbs().iterator();

			while (itItemEstruturaSisAtributos.hasNext()) {

				ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoOrigem = (ItemEstruturaSisAtributoIettSatb) itItemEstruturaSisAtributos.next();
				ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributoNovo = new ItemEstruturaSisAtributoIettSatb();

				itemEstruturaSisAtributoNovo.setDataUltManutencao(Data.getDataAtual());
				itemEstruturaSisAtributoNovo.setUsuarioUsu(usuarioLogado);
				itemEstruturaSisAtributoNovo.setItemEstruturaIett(novoItem);
				itemEstruturaSisAtributoNovo.setSisAtributoSatb(itemEstruturaSisAtributoOrigem.getSisAtributoSatb());

				// Para atributos do tipo CAMPO_ID
				if (itemEstruturaSisAtributoOrigem.getInformacao() != null
						&& itemEstruturaSisAtributoOrigem.getSisAtributoSatb().getAtribInfCompSatb() != null
						&& (itemEstruturaSisAtributoOrigem.getSisAtributoSatb().getAtribInfCompSatb().equals(ConstantesECAR.VALIDACAO_ATRIBUTO_MASCARA)
								|| itemEstruturaSisAtributoOrigem.getSisAtributoSatb().getAtribInfCompSatb().equals(ConstantesECAR.VALIDACAO_ATRIBUTO_MASCARA_EDITAVEL) || itemEstruturaSisAtributoOrigem
								.getSisAtributoSatb().getAtribInfCompSatb().equals(ConstantesECAR.VALIDACAO_ATRIBUTO_INCREMENTAL))) {

					if (!itemEstruturaSisAtributoOrigem.getSisAtributoSatb().getAtribInfCompSatb().equals(ConstantesECAR.VALIDACAO_ATRIBUTO_INCREMENTAL)) {
						itemEstruturaSisAtributoNovo.setInformacao(action.geraConteudo(itemEstruturaSisAtributoOrigem.getSisAtributoSatb().getMascara()));
					}
					geraValorIncremental(itemEstruturaSisAtributoNovo, itemEstruturaSisAtributoNovo.getSisAtributoSatb(), funcaoDadosGerais, itemEstruturaSisAtributoNovo.getItemEstruturaIett()
							.getEstruturaEtt(), null);

				} else if (itemEstruturaSisAtributoOrigem.getInformacao() != null
						&& itemEstruturaSisAtributoOrigem.getSisAtributoSatb().getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {

					String formato = "ddMMyyyyHHmmssSSS";
					SimpleDateFormat formatter = new SimpleDateFormat(formato);
					String dataGravacao = formatter.format(new Date());

					String pathRaiz = configuracaoCfg.getRaizUpload();
					String pathAnexo = configuracaoCfg.getUploadUsuarios();

					String novaInformacao = itemEstruturaSisAtributoOrigem.getInformacao().substring(0, itemEstruturaSisAtributoOrigem.getInformacao().lastIndexOf("/") + 1) + dataGravacao + "--"
							+ itemEstruturaSisAtributoOrigem.getInformacao().substring(itemEstruturaSisAtributoOrigem.getInformacao().lastIndexOf("/") + 1);

					String informacaoOrigem = itemEstruturaSisAtributoOrigem.getInformacao().substring(itemEstruturaSisAtributoOrigem.getInformacao().lastIndexOf("/") + 1);

					// String novaInformacao = dataGravacao + "--" +
					// informacaoOrigem;

					// Faz a copia do arquivo armazenado no servidor.

					// copiaUpload.setNomeOriginalIettup(uploadOrigem.getNomeOriginalIettup());
					// copiaUpload.setTamanhoIettup(uploadOrigem.getTamanhoIettup());

					String arquivoOrigem = FileUpload.getPathFisico(pathRaiz, pathAnexo, informacaoOrigem);// itemEstruturaSisAtributoOrigem.getInformacao();
					String arquivoDestino = FileUpload.getPathFisico(pathRaiz, pathAnexo, dataGravacao + "--" + informacaoOrigem);

					itemEstruturaSisAtributoNovo.setInformacao(novaInformacao);

					try {
						FileUpload.copiarArquivo(arquivoOrigem, arquivoDestino);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// Para atributos do tipo TEXT, TEXTAREA, MULTITEXTO, IMAGEM e
				// VALIDAï¿½ï¿½O
				else if (itemEstruturaSisAtributoOrigem.getInformacao() != null) {
					itemEstruturaSisAtributoNovo.setInformacao(itemEstruturaSisAtributoOrigem.getInformacao());
				}

				itemEstruturaSisAtributoNovo.atribuirPKPai();

				atributosLivresDadosGerais.add(itemEstruturaSisAtributoNovo);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ECARException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return atributosLivresDadosGerais;
	}

	/**
	 * 
	 * 
	 * @param itemOrigem
	 * @param novoItem
	 * @return List de
	 * @throws Exception
	 */
	private Set copiarFuncaoAcompanhamentoDadosGerais(ItemEstruturaIett itemOrigem, ItemEstruturaIett novoItem, UsuarioUsu usuarioLogado) {

		// Cï¿½PIA DOS ATRIBUTOS LIVRES

		Set itemEstUsuTpfaDadosGerais = new HashSet();

		Iterator itItemEstUsuTpfas = itemOrigem.getItemEstUsutpfuacIettutfas().iterator();

		try {
			while (itItemEstUsuTpfas.hasNext()) {

				ItemEstUsutpfuacIettutfa itemEstUsuTpfaOrigem = (ItemEstUsutpfuacIettutfa) itItemEstUsuTpfas.next();
				ItemEstUsutpfuacIettutfa itemEstUsuTpfaNovo = new ItemEstUsutpfuacIettutfa();
				Entidade.clonarPojo(itemEstUsuTpfaOrigem, itemEstUsuTpfaNovo);
				itemEstUsuTpfaNovo.setDataUltManutencao(Data.getDataAtual());
				itemEstUsuTpfaNovo.setItemEstruturaIett(novoItem);
				// itemEstUsuTpfaNovo.atribuirPKPai();
				// itemEstUsuTpfaNovo.setUsuarioUsu(usuarioLogado);
				itemEstUsuTpfaNovo.setUsuManutencao(usuarioLogado);
				itemEstUsuTpfaDadosGerais.add(itemEstUsuTpfaNovo);

				// session.save(itemEstUsuTpfaNovo);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return itemEstUsuTpfaDadosGerais;
	}

	/**
	 * Gera e retorna uma cï¿½pia da coleï¿½ï¿½o de agendas relacionada com um
	 * ItemEstrutura
	 * 
	 * @param novoItem
	 * @param agendasOrigem
	 * @return Set de AgendaAge
	 */
	private Set copiarAgendas(ItemEstruturaIett novoItem, Set agendasOrigem, UsuarioUsu usuarioLogado) {

		Set agendas = new HashSet();

		Iterator itAgendasORigem = agendasOrigem.iterator();

		while (itAgendasORigem.hasNext()) {

			try {

				AgendaAge agendaOrigem = (AgendaAge) itAgendasORigem.next();
				AgendaAge copiaAgenda = new AgendaAge();
				Entidade.clonarPojo(agendaOrigem, copiaAgenda);
				copiaAgenda.setCodAge(null);
				copiaAgenda.setItemEstruturaIett(novoItem);
				copiaAgenda.setUsuarioUsu(usuarioLogado);
				agendas.add(copiaAgenda);

				session.save(copiaAgenda);

				// DestaqueItemRelDtqirs
				Iterator itDestaquesOrigem = agendaOrigem.getDestaqueItemRelDtqirs().iterator();
				copiaAgenda.setDestaqueItemRelDtqirs(new HashSet());

				while (itDestaquesOrigem.hasNext()) {
					DestaqueItemRelDtqir destaqueItemOrigem = (DestaqueItemRelDtqir) itDestaquesOrigem.next();
					DestaqueItemRelDtqir destaqueItemNovo = new DestaqueItemRelDtqir();

					Entidade.clonarPojo(destaqueItemOrigem, destaqueItemNovo);
					destaqueItemNovo.setAgendaAge(copiaAgenda);
					destaqueItemNovo.setCodDtqir(null);

					copiaAgenda.getDestaqueItemRelDtqirs().add(destaqueItemNovo);

					session.save(destaqueItemNovo);
				}

				// AgendaOcorrenciaAgeos
				Iterator itOcorrenciasOrigem = copiaAgenda.getAgendaOcorrenciaAgeos().iterator();
				copiaAgenda.setAgendaOcorrenciaAgeos(new HashSet());

				while (itOcorrenciasOrigem.hasNext()) {
					AgendaOcorrenciaAgeo agendaOcorrenciaOrigem = (AgendaOcorrenciaAgeo) itOcorrenciasOrigem.next();
					AgendaOcorrenciaAgeo agendaOcorrenciaNovo = new AgendaOcorrenciaAgeo();

					Entidade.clonarPojo(agendaOcorrenciaOrigem, agendaOcorrenciaNovo);
					agendaOcorrenciaNovo.setAgendaAge(copiaAgenda);
					agendaOcorrenciaNovo.setCodAgeo(null);

					copiaAgenda.getAgendaOcorrenciaAgeos().add(agendaOcorrenciaNovo);

					session.save(agendaOcorrenciaNovo);
				}

				// AgendaEntidadesAgeent
				Iterator itEntidadesOrigem = agendaOrigem.getAgendaEntidadesAgeent().iterator();
				copiaAgenda.setAgendaEntidadesAgeent(new HashSet());

				while (itEntidadesOrigem.hasNext()) {
					AgendaEntidadesAgeent agendaEntidadeOrigem = (AgendaEntidadesAgeent) itEntidadesOrigem.next();
					AgendaEntidadesAgeent agendaEntidadeNovo = new AgendaEntidadesAgeent();

					Entidade.clonarPojo(agendaEntidadeOrigem, agendaEntidadeNovo);
					agendaEntidadeNovo.setAgendaAge(copiaAgenda);
					agendaEntidadeNovo.atribuirPKPai();
					agendaEntidadeNovo.setDataInclusaoAgeent(Data.getDataAtual());
					agendaEntidadeNovo.setUsuarioUsuManutencao(usuarioLogado);

					copiaAgenda.getAgendaEntidadesAgeent().add(agendaEntidadeNovo);

					session.save(agendaEntidadeNovo);
				}

			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return agendas;
	}

	/**
	 * Gera e retorna uma cï¿½pia da coleï¿½ï¿½o de Editores_Leitores
	 * (Permissï¿½o de Acesso) relacionada com um ItemEstrutura
	 * 
	 * @param novoItem
	 * @param itensInseridos
	 * @param agendasOrigem
	 * @return Set de AgendaAge
	 */
	private Set copiarEditoresLeitores(ItemEstruturaIett itemOrigem, ItemEstruturaIett novoItem, Map itensInseridos, UsuarioUsu usuarioLogado) {

		Set permissoes = new HashSet();

		Iterator itEditoresLeitoresOrigem = itemOrigem.getItemEstrutUsuarioIettusesByCodIett().iterator();

		for (Iterator itIns = itensInseridos.keySet().iterator(); itIns.hasNext();) {
			ItemEstruturaIett itemAntigo = (ItemEstruturaIett) itIns.next();
			ItemEstruturaIett itemNovo = (ItemEstruturaIett) itensInseridos.get(itemAntigo);
		}

		while (itEditoresLeitoresOrigem.hasNext()) {

			try {

				ItemEstrutUsuarioIettus editaLerUsuOrigem = (ItemEstrutUsuarioIettus) itEditoresLeitoresOrigem.next();

				if (editaLerUsuOrigem.getCodTpPermIettus() != null
						&& (editaLerUsuOrigem.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_USUARIO) || editaLerUsuOrigem.getCodTpPermIettus().equals(ControlePermissao.PERMISSAO_GRUPO))) {

					ItemEstrutUsuarioIettus copiaEditaLer = new ItemEstrutUsuarioIettus();
					Entidade.clonarPojo(editaLerUsuOrigem, copiaEditaLer);
					copiaEditaLer.setCodIettus(null);
					copiaEditaLer.setItemEstruturaIett(novoItem);
					copiaEditaLer.setDataInclusaoIettus(Data.getDataAtual());
					// copiaEditaLer.setUsuarioUsu(usuarioLogado);
					copiaEditaLer.setUsuManutencao(usuarioLogado);

					if (copiaEditaLer.getItemEstruturaIettOrigem().getCodIett().equals(itemOrigem.getCodIett())) {
						copiaEditaLer.setItemEstruturaIettOrigem(novoItem);
					} else if (itensInseridos.containsKey(copiaEditaLer.getItemEstruturaIettOrigem())) {
						ItemEstruturaIett iettNovo = (ItemEstruturaIett) itensInseridos.get(copiaEditaLer.getItemEstruturaIettOrigem());
						copiaEditaLer.setItemEstruturaIettOrigem(iettNovo);
					}
					permissoes.add(copiaEditaLer);
					session.save(copiaEditaLer);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return permissoes;
	}

	/**
	 * Gera e retorna uma cï¿½pia da coleï¿½ï¿½o de Editores_Leitores
	 * EditoresLeitores que se referem a funï¿½ï¿½es de acompanhamento apenas,
	 * relacionada com um ItemEstrutura
	 * 
	 * @param novoItem
	 * @param itensInseridos
	 * @param agendasOrigem
	 * @return Set de ItemEstrutUsuarioIettus
	 */
	// private Set copiarPermissoesFuncoesAcompanhamento (ItemEstruturaIett
	// itemOrigem, ItemEstruturaIett novoItem, Map itensInseridos, UsuarioUsu
	// usuarioLogado){
	//
	// Set permissoes = new HashSet();
	//
	// Iterator itEditoresLeitoresOrigem =
	// itemOrigem.getItemEstrutUsuarioIettusesByCodIett().iterator();
	//
	// for (Iterator itIns = itensInseridos.keySet().iterator() ;
	// itIns.hasNext();){
	// ItemEstruturaIett itemAntigo = (ItemEstruturaIett)itIns.next();
	// ItemEstruturaIett itemNovo =
	// (ItemEstruturaIett)itensInseridos.get(itemAntigo);
	// }
	//
	// while (itEditoresLeitoresOrigem.hasNext()){
	//
	// try{
	//
	// ItemEstrutUsuarioIettus editaLerUsuOrigem =
	// (ItemEstrutUsuarioIettus)itEditoresLeitoresOrigem.next();
	//
	// if (editaLerUsuOrigem.getTipoFuncAcompTpfa() != null){
	//
	// ItemEstrutUsuarioIettus copiaEditaLer = new ItemEstrutUsuarioIettus();
	// Entidade.clonarPojo(editaLerUsuOrigem, copiaEditaLer);
	// copiaEditaLer.setCodIettus(null);
	// copiaEditaLer.setItemEstruturaIett(novoItem);
	// copiaEditaLer.setDataInclusaoIettus(Data.getDataAtual());
	// copiaEditaLer.setUsuarioUsu(usuarioLogado);
	// copiaEditaLer.setUsuarioUsu(usuarioLogado);
	//
	// if
	// (copiaEditaLer.getItemEstruturaIettOrigem().getCodIett().equals(itemOrigem.getCodIett())){
	// copiaEditaLer.setItemEstruturaIettOrigem (novoItem);
	// } else if
	// (itensInseridos.containsKey(copiaEditaLer.getItemEstruturaIettOrigem())){
	// ItemEstruturaIett iettNovo = (ItemEstruturaIett)
	// itensInseridos.get(copiaEditaLer.getItemEstruturaIettOrigem());
	// copiaEditaLer.setItemEstruturaIettOrigem (iettNovo);
	// }
	// permissoes.add(copiaEditaLer);
	// session.save(copiaEditaLer);
	// }
	// }
	// catch (Exception e){
	// e.printStackTrace();
	// }
	// }
	//
	// return permissoes;
	// }

	private Set copiarDemandasAssociadas(ItemEstruturaIett novoItem, Set demandasAssociadasOrigem) {

		Set demandasAssociadas = new HashSet();
		novoItem.setItemRegdemandaIregds(new HashSet());
		Iterator itDemandasAssociadasOrigem = demandasAssociadasOrigem.iterator();

		while (itDemandasAssociadasOrigem.hasNext()) {

			try {
				RegDemandaRegd demandaAssociadaOrigem = (RegDemandaRegd) itDemandasAssociadasOrigem.next();
				demandaAssociadaOrigem.getItemRegdemandaIregds().add(novoItem);

				demandasAssociadas.add(demandaAssociadaOrigem);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return demandasAssociadas;
	}

	private Set copiarBeneficiarios(ItemEstruturaIett novoItem, Set beneficiariosOrigem, UsuarioUsu usuarioLogado) {

		Iterator iterator = beneficiariosOrigem.iterator();
		novoItem.setItemEstrtBenefIettbs(new HashSet());

		while (iterator.hasNext()) {

			try {
				ItemEstrtBenefIettb beneficiarioOrigem = (ItemEstrtBenefIettb) iterator.next();
				ItemEstrtBenefIettb beneficiarioNovo = new ItemEstrtBenefIettb();

				Entidade.clonarPojo(beneficiarioOrigem, beneficiarioNovo);
				beneficiarioNovo.setItemEstruturaIett(novoItem);
				beneficiarioNovo.atribuirPKPai();
				beneficiarioNovo.setDataUltManutencaoIettb(Data.getDataAtual());
				beneficiarioNovo.setUsuarioUsuManutencao(usuarioLogado);

				novoItem.getItemEstrtBenefIettbs().add(beneficiarioNovo);

				session.save(beneficiarioNovo);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return novoItem.getItemEstrtBenefIettbs();
	}

	private Set copiarCategorias(ItemEstruturaIett novoItem, Set categoriasOrigem, UsuarioUsu usuarioLogado) throws ECARException {

		Iterator itCategoriasOrigem = categoriasOrigem.iterator();
		ConfiguracaoCfg configuracaoCfg = new ConfiguracaoDao(request).getConfiguracao();

		while (itCategoriasOrigem.hasNext()) {

			try {

				ItemEstrUplCategIettuc categoriaOrigem = (ItemEstrUplCategIettuc) itCategoriasOrigem.next();
				ItemEstrUplCategIettuc categoriaNovo = new ItemEstrUplCategIettuc();
				Entidade.clonarPojo(categoriaOrigem, categoriaNovo);
				categoriaNovo.setCodIettuc(null);
				categoriaNovo.setItemEstruturaIett(novoItem);
				categoriaNovo.setDataInclusaoIettuc(Data.getDataAtual());

				session.save(categoriaNovo);

				// Cï¿½pia os itens anexos
				// itemEstrutUploadIettup

				// Cï¿½PIA DOS ARQUIVOS ANEXOS DO ITEM
				Iterator itArquivosUpload = categoriaOrigem.getItemEstrutUploadIettups().iterator();
				categoriaNovo.setItemEstrutUploadIettups(new HashSet());

				while (itArquivosUpload.hasNext()) {

					try {

						ItemEstrutUploadIettup uploadOrigem = (ItemEstrutUploadIettup) itArquivosUpload.next();
						ItemEstrutUploadIettup copiaUpload = new ItemEstrutUploadIettup();
						Entidade.clonarPojo(uploadOrigem, copiaUpload);
						copiaUpload.setCodIettup(null);
						copiaUpload.setItemEstruturaIett(novoItem);
						copiaUpload.setItemEstrUplCategIettuc(categoriaNovo);
						copiaUpload.setDataInclusaoIettup(Data.getDataAtual());
						copiaUpload.setUsuarioUsu(usuarioLogado);
						copiaUpload.setUsuarioUsuManutencao(usuarioLogado);

						String formato = "ddMMyyyyHHmmssSSS";
						SimpleDateFormat formatter = new SimpleDateFormat(formato);
						String dataGravacao = formatter.format(new Date());

						String pathRaiz = configuracaoCfg.getRaizUpload();// _msg.getPathUploadRaiz("path .upload.raiz");
						String pathAnexo = configuracaoCfg.getUploadAnexos();
						// Faz a copia do arquivo armazenado no servidor.
						copiaUpload.setArquivoIettup(FileUpload.getPathFisico("", pathAnexo, dataGravacao + " - " + uploadOrigem.getNomeOriginalIettup()));
						copiaUpload.setNomeOriginalIettup(uploadOrigem.getNomeOriginalIettup());
						copiaUpload.setTamanhoIettup(uploadOrigem.getTamanhoIettup());

						String arquivoOrigem = pathRaiz + uploadOrigem.getArquivoIettup();
						String arquivoDestino = FileUpload.getPathFisico(pathRaiz, pathAnexo, dataGravacao + " - " + copiaUpload.getNomeOriginalIettup());

						try {
							FileUpload.copiarArquivo(arquivoOrigem, arquivoDestino);
						} catch (IOException e) {
							e.printStackTrace();
						}

						session.save(copiaUpload);
						categoriaNovo.getItemEstrutUploadIettups().add(copiaUpload);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return novoItem.getItemEstrUplCategIettucs();
	}

	private Set copiarCriterios(ItemEstruturaIett novoItem, Set criterios, UsuarioUsu usuarioLogado) {
		Iterator itOrigem = criterios.iterator();
		novoItem.setItemEstrutCriterioIettcs(new HashSet());

		while (itOrigem.hasNext()) {

			try {
				ItemEstrutCriterioIettc criterioOrigem = (ItemEstrutCriterioIettc) itOrigem.next();
				ItemEstrutCriterioIettc criterioNovo = new ItemEstrutCriterioIettc();
				Entidade.clonarPojo(criterioOrigem, criterioNovo);
				// criterio.set CodIettus(null);
				criterioNovo.setItemEstruturaIett(novoItem);
				criterioNovo.setDataUltManutencao(Data.getDataAtual());
				criterioNovo.setUsuManutencao(usuarioLogado);
				criterioNovo.atribuirPK();

				novoItem.getItemEstrutCriterioIettcs().add(criterioNovo);

				session.save(criterioNovo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return novoItem.getItemEstrutCriterioIettcs();
	}

	private Set copiarEntidades(ItemEstruturaIett novoItem, Set entidades, UsuarioUsu usuarioLogado) {
		// Set entidadesAdicionadas = new HashSet();
		Iterator itOrigem = entidades.iterator();
		novoItem.setItemEstrutEntidadeIettes(new HashSet());

		while (itOrigem.hasNext()) {

			try {
				ItemEstrutEntidadeIette entidadeOrigem = (ItemEstrutEntidadeIette) itOrigem.next();
				ItemEstrutEntidadeIette entidadeNovo = new ItemEstrutEntidadeIette();
				Entidade.clonarPojo(entidadeOrigem, entidadeNovo);
				entidadeNovo.setItemEstruturaIett(novoItem);
				entidadeNovo.setDataUltManutencaoIette(Data.getDataAtual());
				entidadeNovo.setUsuarioUsuManutencao(usuarioLogado);
				entidadeNovo.atribuirPK();

				novoItem.getItemEstrutEntidadeIettes().add(entidadeNovo);
				// entidadesAdicionadas.add(entidadeNovo);

				session.save(entidadeNovo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return novoItem.getItemEstrutEntidadeIettes();
	}

	private Set copiarEventos(ItemEstruturaIett novoItem, Set acao, UsuarioUsu usuarioLogado) {
		// Set acoesAdicionadas = new HashSet();
		Iterator itOrigem = acao.iterator();
		novoItem.setItemEstrutAcaoIettas(new HashSet());

		while (itOrigem.hasNext()) {

			try {
				ItemEstrutAcaoIetta acaoOrigem = (ItemEstrutAcaoIetta) itOrigem.next();
				ItemEstrutAcaoIetta acaoNova = new ItemEstrutAcaoIetta();
				Entidade.clonarPojo(acaoOrigem, acaoNova);
				acaoNova.setCodIetta(null);
				acaoNova.setItemEstruturaIett(novoItem);
				acaoNova.setDataInclusaoIetta(Data.getDataAtual());
				acaoNova.setUsuarioUsu(usuarioLogado);
				acaoNova.setUsuarioUsuManutencao(usuarioLogado);

				novoItem.getItemEstrutAcaoIettas().add(acaoNova);
				// acoesAdicionadas.add(entidadeNovo);

				session.save(acaoNova);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return novoItem.getItemEstrutAcaoIettas();
	}

	private Set copiarFonteRecursos(ItemEstruturaIett novoItem, Set fonteRecursosOrigem) {

		Set fonteRecursos = new HashSet();

		Iterator iterator = fonteRecursosOrigem.iterator();
		novoItem.setEfIettFonteTotEfiefts(new HashSet());

		while (iterator.hasNext()) {

			try {
				EfIettFonteTotEfieft fonteRecursoOrigem = (EfIettFonteTotEfieft) iterator.next();
				EfIettFonteTotEfieft fonteRecursoNovo = new EfIettFonteTotEfieft();

				Entidade.clonarPojo(fonteRecursoOrigem, fonteRecursoNovo);
				fonteRecursoNovo.setItemEstruturaIett(novoItem);
				fonteRecursoNovo.atribuirPKPai();

				novoItem.getEfIettFonteTotEfiefts().add(fonteRecursoNovo);

				session.save(fonteRecursoNovo);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return novoItem.getEfIettFonteTotEfiefts();
	}

	private Set copiarPontosCriticos(ItemEstruturaIett novoItem, Set pontosCriticosOrigem, UsuarioUsu usuarioLogado) {

		Iterator itPontosCriticosOrigem = pontosCriticosOrigem.iterator();
		PontoCriticoDao ptcDao = new PontoCriticoDao(null);
		ActionSisAtributo action = new ActionSisAtributo();
		FuncaoFun funcaoPontoCritico = new FuncaoFun();
		funcaoPontoCritico.setNomeFun(FuncaoDao.NOME_FUNCAO_PONTOS_CRITICOS);
		List funcoes = null;

		while (itPontosCriticosOrigem.hasNext()) {

			try {

				funcoes = (new FuncaoDao(request)).pesquisar(funcaoPontoCritico, null);

				if (funcoes != null && funcoes.size() > 0) {
					funcaoPontoCritico = (FuncaoFun) funcoes.get(0);
				}

				PontoCriticoPtc pontoCriticoOrigem = (PontoCriticoPtc) itPontosCriticosOrigem.next();

				if (!pontoCriticoOrigem.getIndExcluidoPtc().equals("S")) {

					PontoCriticoPtc pontoCriticoNovo = new PontoCriticoPtc();
					Entidade.clonarPojo(pontoCriticoOrigem, pontoCriticoNovo);
					pontoCriticoNovo.setCodPtc(null);
					pontoCriticoNovo.setItemEstruturaIett(novoItem);

					pontoCriticoNovo.setDataUltManutencaoPtc(Data.getDataAtual());
					pontoCriticoNovo.setUsuarioUsuInclusao(usuarioLogado);
					pontoCriticoNovo.setUsuarioUsuByCodUsuUltManutPtc(usuarioLogado);
					pontoCriticoNovo.setUsuarioUsu(pontoCriticoOrigem.getUsuarioUsu());

					session.save(pontoCriticoNovo);

					// Cï¿½PIA DOS ATRIBUTOS LIVRES

					Set atributosLivresPontosCriticos = new HashSet();

					Iterator itPontoCriticoSisAtributos = pontoCriticoOrigem.getPontoCriticoSisAtributoPtcSatbs().iterator();

					while (itPontoCriticoSisAtributos.hasNext()) {

						PontoCriticoSisAtributoPtcSatb pontoCriticoSisAtributoOrigem = (PontoCriticoSisAtributoPtcSatb) itPontoCriticoSisAtributos.next();

						PontoCriticoSisAtributoPtcSatb pontoCriticoSisAtributoNovo = new PontoCriticoSisAtributoPtcSatb();

						pontoCriticoSisAtributoNovo.setDataUltManutencao(Data.getDataAtual());
						// pontoCriticoSisAtributoNovo.setUsuarioUsu(usuarioLogado);
						pontoCriticoSisAtributoNovo.setPontoCriticoPtc(pontoCriticoNovo);
						pontoCriticoSisAtributoNovo.setSisAtributoSatb(pontoCriticoSisAtributoOrigem.getSisAtributoSatb());
						pontoCriticoSisAtributoNovo.setUsuarioUsu(pontoCriticoSisAtributoOrigem.getUsuarioUsu());

						// Para atributos do tipo CAMPO_ID
						if (pontoCriticoSisAtributoOrigem.getInformacao() != null && pontoCriticoSisAtributoOrigem.getSisAtributoSatb().getAtribInfCompSatb() != null
								&& (pontoCriticoSisAtributoOrigem.getSisAtributoSatb().isAtributoContemMascara() || pontoCriticoSisAtributoOrigem.getSisAtributoSatb().isAtributoAutoIcremental())) {

							if (!pontoCriticoSisAtributoOrigem.getSisAtributoSatb().isAtributoAutoIcremental()) {
								pontoCriticoSisAtributoNovo.setInformacao(action.geraConteudo(pontoCriticoSisAtributoOrigem.getSisAtributoSatb().getMascara()));
							}
							geraValorIncremental(pontoCriticoSisAtributoNovo, pontoCriticoSisAtributoNovo.getSisAtributoSatb(), funcaoPontoCritico, pontoCriticoSisAtributoNovo.getPontoCriticoPtc()
									.getItemEstruturaIett().getEstruturaEtt(), null);

							// Para atributos do tipo TEXT, TEXTAREA,
							// MULTITEXTO, IMAGEM e VALIDAï¿½ï¿½O
						} else if (pontoCriticoSisAtributoOrigem.getInformacao() != null) {
							pontoCriticoSisAtributoNovo.setInformacao(pontoCriticoSisAtributoOrigem.getInformacao());
						}

						pontoCriticoSisAtributoNovo.atribuirPKPai();

						// atributosLivresPontosCriticos.add(pontoCriticoSisAtributoNovo);

						session.save(pontoCriticoSisAtributoNovo);
					}

					// Cï¿½PIA DOS APONTAMENTOS
					Iterator itApontamentosOrigem = pontoCriticoOrigem.getApontamentoApts().iterator();
					pontoCriticoNovo.setApontamentoApts(new HashSet());

					while (itApontamentosOrigem.hasNext()) {

						try {

							ApontamentoApt apontamentoOrigem = (ApontamentoApt) itApontamentosOrigem.next();
							ApontamentoApt copiaApontamento = new ApontamentoApt();
							Entidade.clonarPojo(apontamentoOrigem, copiaApontamento);
							copiaApontamento.setCodApt(null);
							copiaApontamento.setItemEstruturaIett(novoItem);
							copiaApontamento.setPontoCriticoPtc(pontoCriticoNovo);
							copiaApontamento.setDataInclusaoApt(Data.getDataAtual());
							copiaApontamento.setUsuarioUsu(apontamentoOrigem.getUsuarioUsu());

							session.save(copiaApontamento);
							pontoCriticoNovo.getApontamentoApts().add(copiaApontamento);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					HistoricoPontoCriticoPtc hptc = new HistoricoPontoCriticoPtc();
					hptc.carregar(pontoCriticoNovo);
					ptcDao.gerarHistorico(hptc, Historico.INCLUIR, usuarioLogado);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// return atributosLivresDadosGerais;

		return novoItem.getPontoCriticoPtcs();
	}

	private Set copiarLocalizacao(ItemEstruturaIett novoItem, Set locaisItens, UsuarioUsu usuarioLogado) {
		Iterator itOrigem = locaisItens.iterator();
		novoItem.setItemEstrutLocalIettls(new HashSet());

		while (itOrigem.hasNext()) {

			try {
				ItemEstrutLocalIettl localOrigem = (ItemEstrutLocalIettl) itOrigem.next();
				ItemEstrutLocalIettl localNovo = new ItemEstrutLocalIettl();
				Entidade.clonarPojo(localOrigem, localNovo);
				localNovo.setItemEstruturaIett(novoItem);
				localNovo.setDataInclusaoIettl(Data.getDataAtual());
				localNovo.setUsuarioUsuManutencao(usuarioLogado);
				localNovo.atribuirPKPai();

				novoItem.getItemEstrutLocalIettls().add(localNovo);

				session.save(localNovo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return novoItem.getItemEstrutLocalIettls();
	}

	private Set copiarMetasIndicadores(ItemEstruturaIett novoItem, Set setMetasIndicadores, UsuarioUsu usuarioLogado) {
		ItemEstrtIndResulIettr indResulOrigem = null;
		ItemEstrtIndResulIettr indResulNovo = null;
		Iterator iterator = null;
		Date dataAtual = Data.getDataAtual();

		Iterator itOrigem = setMetasIndicadores.iterator();
		novoItem.setItemEstrtIndResulIettrs(new HashSet());
		try {

			while (itOrigem.hasNext()) {
				indResulNovo = new ItemEstrtIndResulIettr();
				indResulOrigem = (ItemEstrtIndResulIettr) itOrigem.next();
				// Estï¿½ dando problema de lazy no mï¿½todo clonarPojo
				// Como atualmente a equipe nao sabe para q serve esta
				// propriedade e ela apresenta um erro de Lazy foi colocada como
				// null
				indResulOrigem.setItemEstruturaIettPPA(null);

				Entidade.clonarPojo(indResulOrigem, indResulNovo);
				indResulNovo.setCodIettir(null);
				indResulNovo.setItemEstruturaIett(novoItem);
				indResulNovo.setDataUltManutencao(dataAtual);
				indResulNovo.setUsuarioUsuManutencao(usuarioLogado);

				if (indResulNovo.getIndAtivoIettr().equals(Dominios.SIM)) {
					novoItem.getItemEstrtIndResulIettrs().add(indResulNovo);
					session.save(indResulNovo);
				} else {
					continue;
				}

				// ItemEstrutFisicoIettf
				iterator = indResulOrigem.getItemEstrutFisicoIettfs().iterator();
				indResulNovo.setItemEstrutFisicoIettfs(new HashSet());

				while (iterator.hasNext()) {
					ItemEstrutFisicoIettf indResultFisicoOrigem = (ItemEstrutFisicoIettf) iterator.next();
					ItemEstrutFisicoIettf indResultFisicoNovo = new ItemEstrutFisicoIettf();

					Entidade.clonarPojo(indResultFisicoOrigem, indResultFisicoNovo);
					indResultFisicoNovo.setDataUltManutencao(dataAtual);
					indResultFisicoNovo.setItemEstrtIndResulIettr(indResulNovo);

					// Mantis 0010128 - Qtd prevista nï¿½o ï¿½ mais informado
					// por exercï¿½cio
					// indResultFisicoNovo.atribuirPK();

					session.save(indResultFisicoNovo);
					indResulNovo.getItemEstrutFisicoIettfs().add(indResultFisicoNovo);

					for (ItemEstrtIndResulLocalIettirl itemLocal : indResultFisicoOrigem.getItemEstrtIndResulLocalIettirls()) {
						ItemEstrtIndResulLocalIettirl itemOrigem = itemLocal;
						ItemEstrtIndResulLocalIettirl itemNovo = new ItemEstrtIndResulLocalIettirl();

						Entidade.clonarPojo(itemOrigem, itemNovo);

						itemNovo.setDataInclusaoIettirl(dataAtual);
						itemNovo.setItemEstrutFisicoIettf(indResultFisicoNovo);

						session.save(itemNovo);
						indResultFisicoNovo.getItemEstrtIndResulLocalIettirls().add(itemNovo);

					}
				}

				// agora o previsto por local estï¿½ associa a um
				// ItemEstrutFisicoIettf
				// o cï¿½digo abaixo nï¿½o ï¿½ mais necessï¿½rio

				// //ItemEstrtIndResulLocalIettirl
				// iterator =
				// indResulOrigem.getItemEstrtIndResulLocalIettirls().iterator();
				// indResulNovo.setItemEstrtIndResulLocalIettirls(new
				// HashSet());
				//
				// while (iterator.hasNext()){
				// ItemEstrtIndResulLocalIettirl indResultLocalOrigem=
				// (ItemEstrtIndResulLocalIettirl)iterator.next();
				// ItemEstrtIndResulLocalIettirl indResultLocalNovo = new
				// ItemEstrtIndResulLocalIettirl();
				//
				// Entidade.clonarPojo(indResultLocalOrigem ,
				// indResultLocalNovo);
				// indResultLocalNovo.setCodIettirl(null);
				// indResultLocalNovo.setDataInclusaoIettirl(dataAtual);
				//
				// //Mantis 0010128 - Qtd prevista nï¿½o ï¿½ mais informado por
				// exercï¿½cio
				// //Este mï¿½todo precisa ser refeito. Previsto por local nao
				// esta mais
				// //associado diretamente com o indicador.
				//
				// //linha comentada:
				// //indResultLocalNovo.setItemEsrtIndResulIettr(indResulNovo);
				//
				// session.save(indResultLocalNovo);
				// indResulNovo.getItemEstrtIndResulLocalIettirls().add(indResultLocalNovo);
				// }

				// ItemEstrtIndResulCorIettrcor
				iterator = indResulOrigem.getItemEstrtIndResulCorIettrcores().iterator();
				indResulNovo.setItemEstrtIndResulCorIettrcores(new HashSet());

				while (iterator.hasNext()) {
					ItemEstrtIndResulCorIettrcor indResultCorOrigem = (ItemEstrtIndResulCorIettrcor) iterator.next();
					ItemEstrtIndResulCorIettrcor indResultCorNovo = new ItemEstrtIndResulCorIettrcor();

					Entidade.clonarPojo(indResultCorOrigem, indResultCorNovo);
					indResultCorNovo.setItemEstrtIndResulIettr(indResulNovo);
					indResultCorNovo.atribuirPKPai();

					session.save(indResultCorNovo);
					indResulNovo.getItemEstrtIndResulCorIettrcores().add(indResultCorNovo);
				}

			} // FIM WHILE itOrigem

		} catch (Exception e) {
			e.printStackTrace();
		}

		return novoItem.getItemEstrtIndResulIettrs();
	}

	/*
	 * Histï¿½rico
	 */
	private void gerarHistorico(HistoricoItemEstruturaIett historicoItemEstruturaIett, Long tipoHistorico) throws ECARException {
		Historico historico = new Historico<HistoricoItemEstruturaIett, ItemEstruturaIett>() {
		};
		// Grupo de atributo configurado como nï¿½vel de planejamento
		SisGrupoAtributoSga sisGrupoAtributoNivelPlanejamento = null;
		if (historicoItemEstruturaIett != null) {
			EstruturaEtt ett = historicoItemEstruturaIett.getEstruturaEtt();
			// Pega todos os atributos/funï¿½ï¿½es de acompanhamento
			// configurados para a funï¿½ï¿½o de dados gerais
			List<ObjetoEstrutura> lista = (new EstruturaDao(null)).getAtributosEstruturaDadosGerais(ett);
			// Inicializa a coleï¿½ï¿½o de atributos livres
			Hibernate.initialize(historicoItemEstruturaIett.getItemEstruturaSisAtributoIettSatbs());
			// Percorre a lista de atributos/funï¿½ï¿½es para inicializar as
			// coleï¿½ï¿½es
			for (ObjetoEstrutura etta : lista) {
				if (etta.iGetGrupoAtributosLivres() != null) {
					Hibernate.initialize(etta.iGetGrupoAtributosLivres());
					if (etta.iGetGrupoAtributosLivres().getSisAtributoSatbs() != null) {
						Hibernate.initialize(etta.iGetGrupoAtributosLivres().getSisAtributoSatbs());
					}
				}
				if (etta.iGetLibTipoFuncAcompTpfas() != null) {
					Hibernate.initialize(etta.iGetLibTipoFuncAcompTpfas());
				}
			}
			if (lista != null)
				historicoItemEstruturaIett.setAtributoEstrutura(lista);

			sisGrupoAtributoNivelPlanejamento = new ConfiguracaoDao(null).getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan();

			// Inicializa os objetos dependentes do grupo de atributo do nï¿½vel
			// de planejamento
			if (sisGrupoAtributoNivelPlanejamento != null) {
				if (sisGrupoAtributoNivelPlanejamento.getSisTipoExibicGrupoSteg() != null) {
					Hibernate.initialize(sisGrupoAtributoNivelPlanejamento.getSisTipoExibicGrupoSteg());
				}
				if (sisGrupoAtributoNivelPlanejamento.getSisAtributoSatbs() != null) {
					Hibernate.initialize(sisGrupoAtributoNivelPlanejamento.getSisAtributoSatbs());
				}

				historicoItemEstruturaIett.setSisGrupoAtributoNivelPlanejamento(sisGrupoAtributoNivelPlanejamento);

			}

			if (historicoItemEstruturaIett.getItemEstruturaNivelIettns() != null) {
				Hibernate.initialize(historicoItemEstruturaIett.getItemEstruturaNivelIettns());
			}

			historico.setHistorico(historicoItemEstruturaIett, tipoHistorico, ((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario(), session);

			ett = null;
			lista = null;
		}
		historico = null;
		sisGrupoAtributoNivelPlanejamento = null;
	}

	/**
	 * Consulta a lista de ItensAtivos ativos que utilizam o atributo Livre
	 * utilizado como parï¿½metro.
	 * 
	 * @param sisAtributo
	 * @return
	 * @throws ECARException
	 */
	public List<PontoCriticoSisAtributoPtcSatb> consultarItensEstruturaSisAtributoAtivos(SisAtributoSatb sisAtributo) throws ECARException {
		try {
			String hql = MessageFormat.format(Util.getHql(ConstantesECAR.PESQUISA_ITEMESTRUTURA_ATRIBUTOLIVRE_ATIVO, request.getSession().getServletContext()), sisAtributo.getCodSatb(), "\'S\'");
			Query query = this.session.createQuery(hql);
			return query.list();
		} catch (IOException ioex) {
			this.logger.error(ioex);
			throw new ECARException(ioex);
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}

	/**
	 * Retorna o Objeto do tipo Sequencial contido na lista.
	 * 
	 * @param itemEstruturaSisAtributo
	 * @return
	 * @throws ECARException
	 */
	public TipoValor obterTipoSequencial(ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributo) throws ECARException {

		TipoValor tipoSeq = null;
		Iterator<TipoValor> it = null;

		try {
			if (getSession().contains(itemEstruturaSisAtributo)) {
				it = itemEstruturaSisAtributo.getTiposValores().iterator();
			} else {
				throw new ECARException(new Throwable("Sessï¿½o encerrada."));
			}
		} catch (ECARException ecarex) {
			this.logger.error(ecarex.getCausaRaiz() + " Recuperaï¿½ï¿½o realizada com sucesso.");
			itemEstruturaSisAtributo = this.carregarObjetoItemEstruturaSisAtributo(itemEstruturaSisAtributo);

			it = itemEstruturaSisAtributo.getTiposValores().iterator();
		}

		while (it.hasNext()) {
			tipoSeq = (TipoValor) it.next();

			if (tipoSeq.getTipo().compareTo(TipoValorEnum.SEQUENCIAL) == 0) {
				break;
			}
		}

		return tipoSeq;
	}

	/**
	 * Utilizado para carregar o objeto ItemEstruturaSisAtributoIettSatb caso
	 * ele nï¿½o esteja na sessï¿½o do hibernate. TODO Novos atributos lazy
	 * poderï¿½o ser adicionados ao Hibernate.initialize de acordo com a
	 * necessidade
	 * 
	 * @param itemEstruturaSisAtributo
	 * @return
	 * @throws ECARException
	 */
	private ItemEstruturaSisAtributoIettSatb carregarObjetoItemEstruturaSisAtributo(ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributo) throws ECARException {

		if (!getSession().contains(itemEstruturaSisAtributo)) {
			itemEstruturaSisAtributo = (ItemEstruturaSisAtributoIettSatb) this.buscar(ItemEstruturaSisAtributoIettSatb.class, itemEstruturaSisAtributo.getComp_id());
		}

		Hibernate.initialize(itemEstruturaSisAtributo.getTiposValores());

		return itemEstruturaSisAtributo;

	}

	/**
	 * efetua a carga inicial do histï¿½rico de dados gerais Devido ao problema
	 * de java heap space e ao problema de nï¿½o termos tempo devido a entrega
	 * da versï¿½o, esse mï¿½todo sï¿½ da a carga de 250 registros de item a
	 * cada vez que ï¿½ chamado
	 * 
	 * @throws ECARException
	 */
	public void cargaInicialHistorico() throws ECARException {
		HistoricoItemEstruturaIett pojoHistorico = new HistoricoItemEstruturaIett();
		HistoricoDao historicoDao = new HistoricoDao(request);
		Transaction tx = null;
		try {
			String[] ordem = new String[] { "codIett", "asc" };
			List<Long> listaIdObjetoSerializado = historicoDao.listaIdObjetoSerializado(HistoricoItemEstruturaIett.class);
			List<ItemEstruturaIett> lista = getItemEstruturaIettSemHistorico(listaIdObjetoSerializado);
			Iterator itLista = lista.iterator();
			int count = 1;
			ItemEstruturaIett iett = null;
			while (itLista.hasNext() && count <= 250) {
				iett = (ItemEstruturaIett) itLista.next();
				System.out.println("Codigo Item: " + iett.getCodIett());
				System.out.println("Contador Iett: " + count);
				tx = session.beginTransaction();
				pojoHistorico.carregar(iett);
				gerarHistorico(pojoHistorico, Historico.INCLUIR);
				tx.commit();
				count++;

				iett = null;
				if (count % 100 == 0) {
					System.gc();
				}
			}
			pojoHistorico = null;
			ordem = null;
			lista = null;
			listaIdObjetoSerializado = null;
		} catch (ECARException e) {
			if (tx != null)
				tx.rollback();
			this.logger.error(e);
			throw new ECARException(e);
		}
	}

	/**
	 * Retorna os itens que ainda nï¿½o tem histï¿½rico gerado.
	 * 
	 * @param codigos
	 * @return
	 */
	public List getItemEstruturaIettSemHistorico(List<Long> codigos) {
		StringBuilder query = new StringBuilder();
		query.append("select iett from ItemEstruturaIett iett where iett.indAtivoIett = 'S' and iett.estruturaEtt.indAtivoEtt = 'S' ");
		if (codigos != null && codigos.size() > 0) {
			query.append("and iett.codIett not in (:codigos)");
		}
		Query q = this.session.createQuery(query.toString());
		if (codigos != null && codigos.size() > 0) {
			q.setParameterList("codigos", codigos);
		}

		return q.list();
	}

	/**
	 * Retorna a quantidade de registros ativos de itemEstrutura
	 * 
	 * @return
	 */
	public Integer getQuantidadeLinhasIettAtivos() {
		Query q = this.session.createQuery("select count(iett) from ItemEstruturaIett iett where iett.indAtivoIett = 'S' and iett.estruturaEtt.indAtivoEtt = 'S'");
		return (Integer) q.uniqueResult();
	}

	/**
	 * Cria o conteï¿½do da coluna(<td>) que faz parte da linha do item(
	 * <tr>
	 * ) que compï¿½e a tabela da ï¿½rvore de ajax. O nome do item ï¿½ setado de
	 * acordo com a configuraï¿½ï¿½o de estruturaAtributo.
	 * 
	 * @param item
	 * @param estruturaSelecionada
	 * @return String nomeItem
	 * @throws ECARException
	 */
	public String criaColunaConteudoColunaArvoreAjax(ItemEstruturaIett item, EstruturaEtt estruturaSelecionada) throws ECARException {

		String nomeItem = "";
		EstruturaDao estruturaDao = new EstruturaDao(null);

		ConfiguracaoCfg configuracaoCfg = new ConfiguracaoDao(request).getConfiguracao();
		List atributos = estruturaDao.getAtributosArvoreEstrutura(estruturaSelecionada);
		Iterator itAtributos = atributos.iterator();

		if (item.getCodIett() != null) {
			item = (ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, item.getCodIett());
		}

		if (!itAtributos.hasNext()) {
			nomeItem = "Atributo nï¿½o configurado";
		} else if (item.getCodIett() != null) {
			while (itAtributos.hasNext()) {
				EstruturaAtributoEttat atributo = (EstruturaAtributoEttat) itAtributos.next();
				boolean campoMultiValor = false;
				boolean primeiro = true;

				if (atributo.getAtributosAtb().getSisGrupoAtributoSga() == null) { // Se
																					// nï¿½o
																					// for
																					// atributo
																					// livre
					if (atributo.getAtributosAtb().getSisGrupoAtributoSga() == null) { // Se
																						// nï¿½o
																						// for
																						// atributo
																						// livre
						if ("nivelPlanejamento".equals(atributo.iGetNome())) {
							String niveis = "";
							if (item.getItemEstruturaNivelIettns() != null && !item.getItemEstruturaNivelIettns().isEmpty()) {
								Iterator itNiveis = item.getItemEstruturaNivelIettns().iterator();
								while (itNiveis.hasNext()) {
									SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
									niveis += nivel.getDescricaoSatb() + "; ";
								}
								niveis = niveis.substring(0, niveis.lastIndexOf(";"));
							}
							nomeItem += niveis + (itAtributos.hasNext() ? " - " : "");
						} else {
							nomeItem += atributo.iGetValor(item) + (itAtributos.hasNext() ? " - " : "");
						}
					}
				} else {// Se for atributo livre
					Set<SisAtributoSatb> sisAtributos = atributo.getAtributosAtb().getSisGrupoAtributoSga().getSisAtributoSatbs();

					Iterator sisAtributosIt = sisAtributos.iterator();
					while (sisAtributosIt.hasNext()) { // Obtem os atributos
														// livres do grupo
						SisAtributoSatb sisAtributo = (SisAtributoSatb) sisAtributosIt.next();
						ItemEstruturaSisAtributoIettSatbPK itemSisAtributoPk = new ItemEstruturaSisAtributoIettSatbPK(item.getCodIett(), sisAtributo.getCodSatb());

						try {

							// Busca o conteudo gravado para o atributo livre
							ItemEstruturaSisAtributoIettSatb itemEstruturaSisAtributo = (ItemEstruturaSisAtributoIettSatb) this.buscar(ItemEstruturaSisAtributoIettSatb.class, itemSisAtributoPk);

							if (itemEstruturaSisAtributo.getInformacao() != null && !itemEstruturaSisAtributo.getInformacao().equals("")) { // Alguns
																																			// atributos
																																			// livres
																																			// nï¿½o
																																			// possuem
																																			// conteudo
																																			// no
																																			// campo
																																			// informaï¿½ï¿½o,
																																			// caso
																																			// possua
																																			// deve
																																			// obter
																																			// a
																																			// informaï¿½ï¿½o
								if (campoMultiValor && !primeiro) {
									nomeItem += configuracaoCfg.getSeparadorCampoMultivalor();
								}
								nomeItem += itemEstruturaSisAtributo.getInformacaoFormatada();
								primeiro = false;
							} else if (!sisAtributo.getDescricaoSatb().equals("")) {// caso
																					// nï¿½o
																					// possua,
																					// deve
																					// obter
																					// apenas
																					// a
																					// descriï¿½ï¿½o
																					// do
																					// atributo
																					// livre.
																					// throws
																					// ECARException{
								if (campoMultiValor && !primeiro) {
									nomeItem += configuracaoCfg.getSeparadorCampoMultivalor();
								}
								nomeItem += sisAtributo.getDescricaoSatb();
								primeiro = false;
							}

						} catch (ECARException ex) { // Retira o caracter "-" da
														// ï¿½ltima posiï¿½ï¿½o
														// da string, caso ele
														// ocorra. Ex.:
														// "Teste -" => "Teste"
							// nomeItem += (sisAtributosIt.hasNext() ?
							// configuracaoCfg.getSeparadorCampoMultivalor()+" "
							// : "");
							/*
							 * if (!itAtributos.hasNext()) { if
							 * (nomeItem.substring(nomeItem.length()-2,
							 * nomeItem.length()).contains("-")){ nomeItem =
							 * nomeItem.substring(0, nomeItem.length()-2); } }
							 */
						}
						campoMultiValor = true;
					}
					nomeItem += (itAtributos.hasNext() ? " - " : "");
				}
			}
		}

		/*
		 * if (nomeItem.length() > 1 && nomeItem.substring(nomeItem.length()-2,
		 * nomeItem.length()).contains("-")){ nomeItem = nomeItem.substring(0,
		 * nomeItem.length()-2); }
		 */

		// retira caracteres especiais
		nomeItem = nomeItem.replace("\n", " ").replace("\r", " ").replace("\t", " ");

		return nomeItem;
	}

	/**
	 * Mï¿½todo para carga de dados do PacInter - Temporï¿½rio
	 * 
	 * @param codIettPai
	 * @param codEstrutura
	 * @param nomeItem
	 * @return
	 */
	public ItemEstruturaIett getItemEstruturaIett(String descricaoR1Pai, Long codEstruturaEtt, String nomeItem) throws ECARException {
		ItemEstruturaIett itemEstruturaIett = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select itemEstruturaIett from ItemEstruturaIett itemEstruturaIett ");
			hql.append("where itemEstruturaIett.itemEstruturaIett.descricaoR1 = :descricaoR1Pai and ");
			hql.append("itemEstruturaIett.estruturaEtt.codEtt = :codEstruturaEtt and ");
			hql.append("itemEstruturaIett.nomeIett = :nomeItem");
			Query q = this.session.createQuery(hql.toString());
			q.setString("descricaoR1Pai", descricaoR1Pai);
			q.setLong("codEstruturaEtt", codEstruturaEtt);
			q.setString("nomeItem", nomeItem);
			q.setMaxResults(1);
			itemEstruturaIett = (ItemEstruturaIett) q.uniqueResult();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return itemEstruturaIett;
	}

	/**
	 * MÃ©todo utilizado obter ItemEstruturaIett atravÃ©s de um
	 * AcompReferenciaItemAri com o intuito de obter ganho de performance
	 * 
	 * @param ari
	 * @return
	 * @throws ECARException
	 */
	public ItemEstruturaIett getItemEstruturaIett(AcompReferenciaItemAri ari) throws ECARException {
		ItemEstruturaIett itemEstruturaIett = null;
		try {

			StringBuilder query = new StringBuilder();
			query.append("select ari.itemEstruturaIett.codIett from AcompReferenciaItemAri ari ");
			query.append(" where ari.codAri = :codAri ");

			Query q = this.session.createQuery(query.toString());
			q.setLong("codAri", ari.getCodAri());
			q.setMaxResults(1);
			Long codigoIett = (Long) q.uniqueResult();

			StringBuilder hql = new StringBuilder();
			hql.append("select iett from ItemEstruturaIett iett ");
			hql.append(" where iett.codIett = :codIett ");
			q = this.session.createQuery(hql.toString());
			q.setLong("codIett", codigoIett);
			q.setMaxResults(1);
			itemEstruturaIett = (ItemEstruturaIett) q.uniqueResult();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return itemEstruturaIett;
	}

	/**
	 * Mï¿½todo para carga de dados do PACInter Temporï¿½rio
	 * 
	 * @param nomeIett
	 * @param siglaIett
	 * @return
	 * @throws ECARException
	 */
	public ItemEstruturaIett getItemEstruturaBySiglaDescricaoR1Avo(String siglaIett, String descricaoR1Avo) throws ECARException {
		ItemEstruturaIett itemEstruturaIett = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select itemEstruturaIett from ItemEstruturaIett itemEstruturaIett ");
			hql.append("where itemEstruturaIett.siglaIett = :siglaIett and ");
			hql.append("itemEstruturaIett.itemEstruturaIett.itemEstruturaIett.descricaoR1 = :descricaoR1 ");
			Query q = this.session.createQuery(hql.toString());
			q.setString("siglaIett", siglaIett);
			q.setString("descricaoR1", descricaoR1Avo);
			q.setMaxResults(1);
			itemEstruturaIett = (ItemEstruturaIett) q.uniqueResult();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return itemEstruturaIett;
	}

	/**
	 * 
	 * @param estruturaEtt
	 * @param descricaoR1
	 * @return
	 */
	public ItemEstruturaIett getItemEstruturaIettByEstruturaDescricaoR1(EstruturaEtt estruturaEtt, String descricaoR1) throws ECARException {
		ItemEstruturaIett itemEstruturaIett = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select itemEstruturaIett from ItemEstruturaIett itemEstruturaIett ");
			hql.append("where itemEstruturaIett.descricaoR1 = :descricaoR1 and ");
			hql.append("itemEstruturaIett.estruturaEtt = :estruturaEtt and ");
			hql.append("itemEstruturaIett.indAtivoIett = :indAtivo ");
			Query q = this.session.createQuery(hql.toString());
			q.setParameter("estruturaEtt", estruturaEtt);
			q.setString("descricaoR1", descricaoR1);
			q.setString("indAtivo", "S");
			q.setMaxResults(1);
			itemEstruturaIett = (ItemEstruturaIett) q.uniqueResult();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return itemEstruturaIett;
	}

	/**
	 * 
	 * @param estruturaEtt
	 * @param descricaoR1
	 * @return
	 */
	public ItemEstruturaIett getItemEstruturaIettByEstruturaDescricaoR3(EstruturaEtt estruturaEtt, ItemEstruturaIett itemBase, String descricaoR3) throws ECARException {
		ItemEstruturaIett itemEstruturaIett = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select itemEstruturaIett from ItemEstruturaIett itemEstruturaIett ");
			hql.append("where itemEstruturaIett.descricaoR3 = :descricaoR3 and ");
			hql.append("itemEstruturaIett.itemEstruturaIett = :itemBase and ");
			hql.append("itemEstruturaIett.estruturaEtt = :estruturaEtt and ");
			hql.append("itemEstruturaIett.indAtivoIett = :indAtivo and ");
			hql.append("itemEstruturaIett.itemEstruturaIett.indAtivoIett = :indAtivo ");
			Query q = this.session.createQuery(hql.toString());
			q.setParameter("estruturaEtt", estruturaEtt);
			// q.setString("descricaoR1", descricaoR1);
			q.setString("descricaoR3", descricaoR3);
			q.setString("indAtivo", "S");
			q.setParameter("itemBase", itemBase);
			q.setMaxResults(1);
			itemEstruturaIett = (ItemEstruturaIett) q.uniqueResult();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return itemEstruturaIett;
	}

	/**
	 * 
	 * @param estruturaEtt
	 * @param descricaoR1
	 * @return
	 */
	public ItemEstruturaIett getItemEstruturaIettByEstruturaDescricaoR3Validacao(EstruturaEtt estruturaEtt, String descricaoR1, String descricaoR3) throws ECARException {
		ItemEstruturaIett itemEstruturaIett = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select itemEstruturaIett from ItemEstruturaIett itemEstruturaIett ");
			hql.append("where itemEstruturaIett.itemEstruturaIett.descricaoR1 = :descricaoR1 and ");
			hql.append("itemEstruturaIett.descricaoR3 = :descricaoR3 and ");
			hql.append("itemEstruturaIett.estruturaEtt = :estruturaEtt and  ");
			hql.append("itemEstruturaIett.indAtivoIett = :indAtivo and ");
			hql.append("itemEstruturaIett.itemEstruturaIett.indAtivoIett = :indAtivo ");
			Query q = this.session.createQuery(hql.toString());
			q.setParameter("estruturaEtt", estruturaEtt);
			q.setString("descricaoR1", descricaoR1);
			q.setString("descricaoR3", descricaoR3);
			q.setString("indAtivo", "S");
			q.setMaxResults(1);
			itemEstruturaIett = (ItemEstruturaIett) q.uniqueResult();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return itemEstruturaIett;
	}

	/**
	 * 
	 * @param estruturaEtt
	 * @param siglaIett
	 * @return
	 */
	public ItemEstruturaIett getItemEstruturaIettByEstruturaSiglaIett(EstruturaEtt estruturaEtt, String siglaIett, String descricaoR1, String descricaoR3) throws ECARException {
		ItemEstruturaIett itemEstruturaIett = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select itemEstruturaIett from ItemEstruturaIett itemEstruturaIett ");
			hql.append("where itemEstruturaIett.siglaIett = :siglaIett and ");
			hql.append("itemEstruturaIett.indAtivoIett = 'S' and ");
			hql.append("itemEstruturaIett.estruturaEtt = :estruturaEtt and ");
			hql.append("itemEstruturaIett.descricaoR1 = :descricaoR1 and ");
			hql.append("itemEstruturaIett.descricaoR3 = :descricaoR3 ");
			Query q = this.session.createQuery(hql.toString());
			q.setParameter("estruturaEtt", estruturaEtt);
			q.setString("siglaIett", siglaIett);
			q.setString("descricaoR1", descricaoR1);
			q.setString("descricaoR3", descricaoR3);
			q.setMaxResults(1);
			itemEstruturaIett = (ItemEstruturaIett) q.uniqueResult();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return itemEstruturaIett;
	}

	/**
	 * 
	 * @param estruturaEtt
	 * @param itemEstruturaSuperior
	 * @param nomeIett
	 * @return
	 * @throws ECARException
	 */
	public ItemEstruturaIett getItemEstruturaIett(EstruturaEtt estruturaEtt, ItemEstruturaIett itemEstruturaSuperior, String nomeIett) throws ECARException {
		ItemEstruturaIett itemEstruturaIett = null;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append("select itemEstruturaIett from ItemEstruturaIett itemEstruturaIett ");
			hql.append("where itemEstruturaIett.estruturaEtt = :estruturaEtt and ");
			hql.append("itemEstruturaIett.itemEstruturaIett = :itemEstruturaSuperior and ");
			hql.append("itemEstruturaIett.nomeIett = :nomeIett");
			Query q = this.session.createQuery(hql.toString());
			q.setParameter("estruturaEtt", estruturaEtt);
			q.setParameter("itemEstruturaSuperior", itemEstruturaSuperior);
			q.setString("nomeIett", nomeIett);
			q.setMaxResults(1);
			itemEstruturaIett = (ItemEstruturaIett) q.uniqueResult();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return itemEstruturaIett;
	}

	/**
	 * Retorna uma lista com os meses que estï¿½o entre as datas inicial e final
	 * do item.
	 * 
	 * caso o item nï¿½o tenha a data inicial ou final, retorna uma lista vazia.
	 * 
	 * @param itemEstrutura
	 *            :
	 * @return Lista com os meses que podem ser acompanhados pelo item
	 * @throws ECARException
	 */
	public List<GregorianCalendar> GetMesesReferencia(ItemEstruturaIett itemEstrutura) throws ECARException {
		GregorianCalendar dataInicial = new GregorianCalendar();
		GregorianCalendar dataFinal = new GregorianCalendar();

		List<GregorianCalendar> resultado = new ArrayList<GregorianCalendar>();

		dataInicial.setTime(this.ObtemDataInicialItemEstrutura(itemEstrutura));
		dataInicial.set(Calendar.DAY_OF_MONTH, 1);

		dataFinal.setTime(this.ObtemDataTerminoItemEstrutura(itemEstrutura));
		dataFinal.set(Calendar.DAY_OF_MONTH, 1);

		while (dataInicial.compareTo(dataFinal) <= 0) {
			resultado.add((GregorianCalendar) dataInicial.clone());
			dataInicial.add(GregorianCalendar.MONTH, 1);
		}

		return resultado;
	}

	public List<GregorianCalendar> GetMesesReferenciaFiltraAno(ItemEstruturaIett itemEstrutura, long ano) throws ECARException {
		GregorianCalendar dataInicial = new GregorianCalendar();
		GregorianCalendar dataFinal = new GregorianCalendar();

		GregorianCalendar filtroDataInicial = new GregorianCalendar((int) ano, 0, 1);
		GregorianCalendar filtroDataFinal = new GregorianCalendar((int) ano, 11, 31);

		List<GregorianCalendar> resultado = new ArrayList<GregorianCalendar>();

		dataInicial.setTime(this.ObtemDataInicialItemEstrutura(itemEstrutura));
		dataFinal.setTime(this.ObtemDataTerminoItemEstrutura(itemEstrutura));

		if ((dataInicial.compareTo(filtroDataFinal) <= 0) && (dataFinal.compareTo(filtroDataInicial) >= 0)) {

			if (dataInicial.compareTo(filtroDataInicial) <= 0) {
				dataInicial = filtroDataInicial;
			}

			if (dataFinal.compareTo(filtroDataFinal) > 0) {
				dataFinal = filtroDataFinal;
			}

			while ((dataInicial.get(GregorianCalendar.MONTH) <= dataFinal.get(GregorianCalendar.MONTH)) && (dataInicial.get(GregorianCalendar.YEAR) == ano)) {
				resultado.add((GregorianCalendar) dataInicial.clone());
				dataInicial.add(GregorianCalendar.MONTH, 1);
			}

		}

		return resultado;
	}

	/**
	 * 
	 * @param orgao
	 * @param acompReferencia
	 * @return
	 * @throws ECARException
	 */
	public List[] getItensAlterarAcompanhamentoPorOrgao(List itensSelecionaveis, OrgaoOrg orgao, AcompReferenciaAref acompReferencia) throws ECARException {

		ValidaPermissao validaPermissao = new ValidaPermissao();
		List[] retorno = new List[2];
		// Mï¿½todo exclui da lista de selecionï¿½veis o itens que o usuï¿½rio
		// nï¿½o tem acesso
		List selecionaveis = this.getItensSelecionaveisFiltradosPorAtributo(itensSelecionaveis, acompReferencia.getTipoAcompanhamentoTa());
		retorno[0] = getItensOrdenados(selecionaveis, acompReferencia.getTipoAcompanhamentoTa());
		retorno[1] = selecionaveis;
		return retorno;
	}

	public void atualizaIndNaoPorLocal(ItemEstruturaIett itemEstrutura) {

		for (Object indicador : itemEstrutura.getItemEstrtIndResulIettrs()) {
			ItemEstrtIndResulIettr item = (ItemEstrtIndResulIettr) indicador;

			if (("S".equals(item.getIndPrevPorLocal())) || ("S".equals(item.getIndRealPorLocal()))) {

				item.setIndPrevPorLocal("N");
				item.setIndRealPorLocal("N");
				item.setNivelAbrangencia(null);

				try {
					this.salvar(item);
				} catch (ECARException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * Retorna a menor data inicial dos exercï¿½cios. Caso o item tenha a data
	 * inicial do cadastro maior que a data inicial dos exercï¿½cios retorna a
	 * data do cadastro.
	 * 
	 * @param itemEstrutura
	 * @return
	 * @throws ECARException
	 */
	public Date ObtemDataInicialItemEstrutura(ItemEstruturaIett itemEstrutura) throws ECARException {
		Date retorno = null;
		Date dataInicioItem = null;
		Date dataInicioExercicio = null;

		dataInicioItem = itemEstrutura.getDataInicioIett();

		ExercicioDao exercicioDao = new ExercicioDao(this.request);
		dataInicioExercicio = exercicioDao.getDataInicialExePrimeiroExercicio();

		if (dataInicioItem != null) {
			if (dataInicioItem.compareTo(dataInicioExercicio) > 0) {
				retorno = dataInicioItem;
			} else {
				retorno = dataInicioExercicio;
			}
		} else {
			retorno = dataInicioExercicio;
		}

		return retorno;
	}

	/**
	 * Retorna a data final de um item de estrutura. Retorna a maior data final
	 * dos exercï¿½cios. Caso o item tenha a data final do cadastro menor que a
	 * data final dos exercï¿½cios retorna a data do cadastro.
	 * 
	 * @param itemEstrutura
	 * @return
	 * @throws ECARException
	 */
	public Date ObtemDataTerminoItemEstrutura(ItemEstruturaIett itemEstrutura) throws ECARException {
		Date retorno = null;
		Date dataTerminoItem = null;
		Date dataFinalExercicio = null;

		dataTerminoItem = itemEstrutura.getDataTerminoIett();

		ExercicioDao exercicioDao = new ExercicioDao(this.request);
		dataFinalExercicio = exercicioDao.getDataFinalExeUltimoExercicio();

		if (dataTerminoItem != null) {
			if (dataTerminoItem.compareTo(dataFinalExercicio) < 0) {
				retorno = dataTerminoItem;
			} else {
				retorno = dataFinalExercicio;
			}
		} else {
			retorno = dataFinalExercicio;
		}
		return retorno;
	}

	/**
	 * Retorna se um sisAtributoSatb de item estï¿½ sendo usado como atributo
	 * restritivo numa estrutura filha
	 * 
	 * @param itemEstrutura
	 * @return
	 * @throws ECARException
	 */
	public boolean existeEstruturaFilhaUsandoAtributoComoRestritivo(ItemEstruturaIett itemEstrutura, SisAtributoSatb sisAtributo) throws ECARException {

		boolean existe = false;

		try {

			// se a estrutura do item possui estruturas filhas
			if (itemEstrutura != null && itemEstrutura.getEstruturaEtt() != null
					&& (itemEstrutura.getEstruturaEtt().getEstruturaEtts() != null && !itemEstrutura.getEstruturaEtt().getEstruturaEtts().isEmpty())) {

				// pesquisa se alguma estrutura filha usa o atributo como
				// restritivo
				StringBuilder hql = new StringBuilder();
				hql.append("select estruturaEtt from EstruturaEtt estruturaEtt ");
				hql.append("join estruturaEtt.sisAtributoSatbEttSuperior sisAtb ");
				hql.append("join estruturaEtt.itemEstruturaIetts iett ");
				hql.append("where sisAtb.codSatb = :codSatb ");
				hql.append("and estruturaEtt.codEtt in (:listaCodEstruturaEtt) ");
				hql.append("and iett.indAtivoIett = 'S' ");
				hql.append("and iett.itemEstruturaIett = :itemEstruturaIett");

				Query q = this.session.createQuery(hql.toString());

				q.setParameter("codSatb", sisAtributo.getCodSatb());

				List listaCodEstruturaEtt = new ArrayList();

				Iterator itEstruturaFilhas = itemEstrutura.getEstruturaEtt().getEstruturaEtts().iterator();

				while (itEstruturaFilhas.hasNext()) {
					EstruturaEtt estrututuraFilha = (EstruturaEtt) itEstruturaFilhas.next();
					listaCodEstruturaEtt.add(estrututuraFilha.getCodEtt());
				}
				q.setParameterList("listaCodEstruturaEtt", listaCodEstruturaEtt);
				q.setEntity("itemEstruturaIett", itemEstrutura);

				List listaItens = q.list();

				if (listaItens != null && !listaItens.isEmpty()) {
					existe = true;
				}
			}

			return existe;

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Lista todos os itens de estrutura.
	 * 
	 * @return
	 */
	public List<ItemEstruturaIett> getListaDeItens() {
		String select = "select item from ItemEstruturaIett as item";
		Query q = this.session.createQuery(select);
		return q.list();
	}

	public ItemEstruturaIett getItemEtruturaById(Long id) {

		String select = "select item from ItemEstruturaIett as item where item.codIett = " + id;
		Query q = this.session.createQuery(select);
		return (ItemEstruturaIett) q.uniqueResult();
	}

	public List<ObjetivoEstrategico> listarObjetivoEstrategico(UsuarioUsu usuario, Long codigoUsuario, List<Long> gruposUsuarios) {
		String sql = "select distinct new ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico(iett.codIett, iett.nomeIett, iett.siglaIett) " + "from ItemEstruturaIett iett "
				+ " inner join iett.itemEstrutUsuarioIettusesByCodIett perm " + "where iett.nivelIett = 1 " + "and iett.indAtivoIett = 'S' " + " AND (perm.usuarioUsu.codUsu = :codUsu OR "
				+ " perm.sisAtributoSatb in (:gruposUsuarios))";

		Query q = this.session.createQuery(sql);
		q.setParameter("codUsu", codigoUsuario);

		Set atributos = new HashSet();
		for (Long atb : gruposUsuarios) {
			SisAtributoSatb sisAtributoSatb = new SisAtributoSatb();
			sisAtributoSatb.setCodSatb(atb);
			atributos.add(sisAtributoSatb);
		}

		q.setParameterList("gruposUsuarios", atributos);

		return q.list();
	}

	public List<ObjetivoEstrategico> listarObjetivoEstrategicoAlt(UsuarioUsu usuario) {
		String sql = "select new ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico(iett.codIett, iett.nomeIett, iett.siglaIett) " + "from ItemEstruturaIett iett " + "where iett.nivelIett = 1 "
				+ "and iett.indAtivoIett = 'S' " + "order by iett.siglaIett";

		Query q = this.session.createQuery(sql);

		return q.list();
	}

	public List<ResutadoSituacaoContar> listarResultadoSituacaoContar(UsuarioUsu usuario) {
		String hql = "select distinct new ecar.pojo.acompanhamentoEstrategico.ResutadoSituacaoContar(" + "count(iett.situacaoSit.codSit), iett.situacaoSit.codSit, iett.situacaoSit.descricaoSit) "
				+ "from ItemEstruturaIett iett " + "where iett.nivelIett = 3 " + "and iett.indAtivoIett = 'S' " + "group by iett.situacaoSit.codSit, iett.situacaoSit.descricaoSit "
				+ "order by iett.situacaoSit.codSit";

		Query q = this.session.createQuery(hql);

		return q.list();
	}

	public List<ResultadoStatusContar> listarResultadoStatus(UsuarioUsu usuario, Long exercicio) {

		String sql =

		"SELECT  COALESCE(cor.cod_cor, 0) AS codigo, COALESCE(cor.significado_cor,'NAO MONITORADO') AS nome, count(distinct iett.cod_iett) AS contar" + "        FROM"
				+ "            tb_item_estrutura_iett iett" + "        INNER JOIN" + "            tb_acomp_referencia_item_ari ari" + "                ON ari.cod_iett = iett.cod_iett"
				+ "        LEFT JOIN" + "            tb_acomp_relatorio_arel arel" + "                ON arel.cod_ari = ari.cod_ari" + "	LEFT JOIN" + "	    tb_cor cor ON cor.cod_cor = arel.cod_cor"
				+ "  WHERE" + "        iett.nivel_iett = 3" + "        AND iett.ind_ativo_iett = 'S'" + "						  "
				+ "        AND (((iett.cod_iett,arel.data_ult_manut_arel) IN (SELECT ari.cod_iett, MAX(arel.data_ult_manut_arel)"
				+ "							 FROM tb_acomp_referencia_item_ari ari INNER JOIN tb_acomp_relatorio_arel arel ON ari.cod_ari = arel.cod_ari"
				+ "							 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) "
				+ "										        AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio)))" + "							 GROUP BY 1)" + "	AND arel.cod_cor IS NOT NULL)" + "			" + "	OR" + "		"
				+ "	(iett.cod_iett, ari.cod_ari, 0) IN(" + "				SELECT cod_iett, cod_ari, contagem" + "				FROM(	 " + "				WITH todos_ietts_ari as (SELECT DISTINCT ari.cod_iett, ari.cod_ari"
				+ "							 FROM tb_acomp_referencia_item_ari ari " + "							 INNER JOIN tb_acomp_relatorio_arel arel " + "								ON arel.cod_ari = ari.cod_ari"
				+ "							 INNER JOIN tb_acomp_referencia_aref aref" + "								ON aref.cod_aref = ari.cod_aref" + "							 WHERE " + "							      (:exercicio=0 OR (arel.data_inclusao_arel"
				+ "							      BETWEEN (SELECT" + "								    data_inicial_exe" + "								FROM" + "								    tb_exercicio_exe" + "								WHERE" + "								    cod_exe=:exercicio) AND (SELECT"
				+ "								    data_final_exe " + "								FROM" + "								    tb_exercicio_exe" + "								WHERE" + "								    cod_exe=:exercicio)))" + "		"
				+ "								   AND (TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY')) IN (SELECT MAX(TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY'))"
				+ "									 FROM tb_acomp_referencia_aref arefFiltro " + "									 WHERE (:exercicio=0 OR (TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY')"
				+ "									 BETWEEN (SELECT" + "								    data_inicial_exe " + "								FROM" + "								    tb_exercicio_exe" + "								WHERE" + "								    cod_exe=:exercicio) AND (SELECT"
				+ "								    data_final_exe " + "								FROM" + "								    tb_exercicio_exe" + "								WHERE" + "								    cod_exe=:exercicio)))" + "									 ) 	    " + "								     "
				+ "							), " + "				     totais_por_iett as (SELECT ari.cod_iett, COUNT(arel.cod_arel) as total" + "							 FROM tb_acomp_referencia_item_ari ari	 "
				+ "							 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari" + "							 WHERE (:exercicio=0 OR (arel.data_ult_manut_arel" + "							      BETWEEN (SELECT"
				+ "								    data_inicial_exe" + "								FROM" + "								    tb_exercicio_exe" + "								WHERE" + "								    cod_exe=:exercicio) AND (SELECT" + "								    data_final_exe "
				+ "								FROM" + "								    tb_exercicio_exe" + "								WHERE" + "								    cod_exe=:exercicio)))" + "							 AND arel.cod_cor IS NOT NULL"
				+ "							 AND arel.data_ult_manut_arel IS NOT NULL" + "							 GROUP BY 1" + "							 ) " + "				SELECT tia.cod_iett, tia.cod_ari, COALESCE(tpi.total,0) as contagem"
				+ "				FROM todos_ietts_ari tia " + "				LEFT JOIN totais_por_iett tpi ON tpi.cod_iett = tia.cod_iett" + "				) AS NAOMONITORADOS)" + "	)" + "GROUP BY 1,2";

		SQLQuery q = this.session.createSQLQuery(sql.toString());

		if (exercicio != null) {
			q.setParameter("exercicio", exercicio);
		} else {
			// ExercicioDao exercicioDao = new ExercicioDao(request);
			// q.setParameter("exercicio",
			// exercicioDao.buscaCodigoExercicioCorrente());
			q.setParameter("exercicio", exercicioDao.buscaCodigoExercicioCorrente());
		}

		q.setResultTransformer(Transformers.aliasToBean(ResultadoStatusContar.class));

		q.addScalar("contar", Hibernate.LONG);
		q.addScalar("codigo", Hibernate.LONG);
		q.addScalar("nome", Hibernate.STRING);

		List<ResultadoStatusContar> status = q.list();

		return status;
	}

	public List<ResultadoNaoMonitorado> listarResultadoNaoMonitorado(UsuarioUsu usuario) {

		Query q = queryStatusOuNaoMonitorado(true);

		return q.list();
	}

	private Query queryStatusOuNaoMonitorado(boolean naoMonitorado) {
		StringBuilder hql = new StringBuilder();
		if (naoMonitorado) {
			hql.append("select distinct new ecar.pojo.acompanhamentoEstrategico.ResultadoNaoMonitorado( ");
		} else {
			hql.append("select distinct new ecar.pojo.acompanhamentoEstrategico.ResultadoStatusContar( ");
		}
		hql.append("count(cor.codCor),  cor.codCor, cor.significadoCor) ");
		hql.append("from AcompReferenciaItemAri ari ");
		hql.append("join ari.acompRelatorioArels arel ");
		if (naoMonitorado) {
			hql.append("left ");
		}
		hql.append("join arel.cor cor ");
		hql.append("where ari.acompReferenciaAref.codAref = 12 ");
		// hql.append("(select max(aref.codAref) from AcompReferenciaAref aref) ");
		hql.append("and arel.tipoFuncAcompTpfa.codTpfa = 6 ");
		if (naoMonitorado) {
			hql.append("and cor.codCor is null ");
		}
		hql.append("group by cor.codCor, cor.significadoCor ");

		Query q = this.session.createQuery(hql.toString());
		return q;
	}

	public List<ObjetivoEstrategico> listarObjetivoEstrategicoFiltro(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria, List<Integer> resultadoStatus,
			Long codigoUsuario, List<Long> gruposUsuarios, boolean prioritario, List<Long> secretarias) {
		StringBuilder hql = new StringBuilder();

		queryListaObjetivoEstrategicoFiltro(codigoObjetivoEstrategico, etiqueta, hql, prioritario, secretarias);

		SQLQuery q = this.session.createSQLQuery(hql.toString());
		//
		// q.setInteger("codObj", 10);
		if (codigoObjetivoEstrategico.size() != 0) {
			q.setParameterList("codObj", codigoObjetivoEstrategico);
		}

		if (etiqueta.size() != 0) {
			q.setParameterList("etiquetas", etiqueta);
		}

		if (secretarias != null && secretarias.size() != 0) {
			q.setParameterList("secretarias", secretarias);
		}

		q.setParameter("codUsu", codigoUsuario);
		q.setParameterList("gruposUsuarios", gruposUsuarios);

		// q.addScalar("codResponsavelResultado", Hibernate.LONG);
		// q.addScalar("responsavelResultado", Hibernate.STRING);
		// q.addScalar("mes", Hibernate.STRING);
		// q.addScalar("ano", Hibernate.STRING);
		// q.addScalar("parecer", Hibernate.STRING);
		// q.addScalar("codAri", Hibernate.LONG);
		// q.addScalar("codCor", Hibernate.LONG);
		// q.addScalar("nomeCor", Hibernate.STRING);
		q.addScalar("siglaIett", Hibernate.STRING);
		q.addScalar("siglaEstrategia", Hibernate.STRING);
		q.addScalar("siglaResultado", Hibernate.STRING);
		q.addScalar("siglaProduto", Hibernate.STRING);
		q.addScalar("siglaAcao", Hibernate.STRING);
		q.addScalar("codObj", Hibernate.LONG);
		q.addScalar("codEstrat", Hibernate.LONG);
		q.addScalar("codResult", Hibernate.LONG);
		q.addScalar("codProd", Hibernate.LONG);
		q.addScalar("codAcao", Hibernate.LONG);
		q.addScalar("objetivo", Hibernate.STRING);
		q.addScalar("estrategia", Hibernate.STRING);
		q.addScalar("resultado", Hibernate.STRING);
		q.addScalar("prioritario", Hibernate.STRING);
		q.addScalar("produto", Hibernate.STRING);
		q.addScalar("acao", Hibernate.STRING);
		q.addScalar("dataInicioResultado", Hibernate.DATE);
		q.addScalar("dataFimResultado", Hibernate.DATE);
		q.addScalar("dataInicioProduto", Hibernate.DATE);
		q.addScalar("dataFimProduto", Hibernate.DATE);
		q.addScalar("atencao", Hibernate.BOOLEAN);
		q.addScalar("ativoProd", Hibernate.STRING);
		q.addScalar("ativoAcao", Hibernate.STRING);

		q.setResultTransformer(Transformers.aliasToBean(ObjetivoEstrategico.class));

		List<ObjetivoEstrategico> lista = q.list();

		Map<String, ObjetivoEstrategico> mapaObjetivo = montaObjetivoEstrategico(lista, resultadoStatus, codigoUsuario, gruposUsuarios);

		return new ArrayList<ObjetivoEstrategico>(mapaObjetivo.values());
	}

	private void queryListaObjetivoEstrategicoFiltro(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, StringBuilder hql, boolean prioritario, List<Long> secretarias) {
		hql.append("select distinct ");
		// "tempResult.codUsuResult as codResponsavelResultado," +
		// "tempResult.usuResult as responsavelResultado," +
		// "tempResult.mesResult as mes, " +
		// "tempResult.anoResult as ano, " +
		// "tempResult.parecerResult as parecer, " +
		// "tempResult.codAriR as codAri, ");
		// hql.append("" +
		// "tempResult.codCorResult as codCor, " +
		// "tempResult.significadoCorResult as nomeCor, " +
		hql.append("tempResult.sigla as siglaIett, " + "tempResult.siglaE as siglaEstrategia, " + "tempResult.siglaR as siglaResultado, " + "tempResult.siglaP as siglaProduto, "
				+ "tempResult.siglaA as siglaAcao, " + "tempResult.codObj as codObj, " + "tempResult.codEstrat as codEstrat, ");
		hql.append("tempResult.codResult as codResult, " + "tempResult.codProd as codProd, " + "tempResult.codAcao as codAcao, ");
		hql.append("tempResult.objetivo as objetivo,  ");
		hql.append("tempResult.estrategia as estrategia, t" + "empResult.resultado as resultado, " + "tempResult.priori as prioritario, " + "tempResult.produto as produto, "
				+ "tempResult.acao as acao, " + "tempResult.dataInicioResultado as dataInicioResultado, " + "tempResult.dataFimResultado as dataFimResultado, "
				+ "tempResult.dataInicioProduto as dataInicioProduto, " + "tempResult.dataFimProduto as dataFimProduto, " + "tempResult.atencao as atencao, " + "tempResult.ativoProd as ativoProd, "
				+ "tempResult.ativoAcao as ativoAcao ");

		hql.append("from  ");
		hql.append("( ");

		hql.append("select distinct  temp0.* , prioritario.cod_atb as priori " + "from tb_etiqueta etiq, tb_item_estrutura_iett iett ");
		hql.append("right join ( ");

		hql.append("select distinct " + "obj.sigla_iett sigla, " + "estrat.sigla_iett siglaE, " + "result.sigla_iett siglaR, " + "prod.sigla_iett siglaP, " + "acao.sigla_iett siglaA, "
				+ "obj.cod_iett codObj, " + "estrat.cod_iett codEstrat, ");
		hql.append("result.cod_iett codResult, ");
		hql.append("prod.cod_iett codProd,  ");
		hql.append("acao.cod_iett codAcao, estrat.ind_ativo_iett, ");
		hql.append("obj.nome_iett as objetivo,  ");
		hql.append("estrat.nome_iett as estrategia, ");
		hql.append("estrat.cod_sit as situacaoEstrat, ");
		hql.append("result.nome_iett as resultado, ");
		hql.append("result.cod_sit as situacaoResult, ");
		hql.append("prod.nome_iett as produto, ");
		hql.append("prod.cod_sit as situacaoProd, ");
		hql.append("acao.nome_iett as acao, ");
		hql.append("acao.cod_sit as situacaoAcao, ");
		hql.append("result.data_inicio_iett as dataInicioResultado, " + "result.data_termino_iett as dataFimResultado, " + "prod.data_inicio_iett as dataInicioProduto, "
				+ "prod.data_termino_iett as dataFimProduto," + "result.atencao_iett as atencao," + "prod.ind_ativo_iett as ativoProd, " + "acao.ind_ativo_iett as ativoAcao ");
		// hql.append("usuR.cod_usu codUsuResult," +
		// "usuR.nome_usu usuResult, " +
		// "arefR.mes_aref as mesResult," +
		// "arefR.ano_aref as anoResult," +
		// "arelR.descricao_arel as parecerResult," +
		// "corR.cod_cor as codCorResult," +
		// "corR.significado_cor as significadoCorResult," +
		// "ariR.cod_ari as codAriR ");
		hql.append("from tb_item_estrutura_iett acao  ");
		// hql.append("inner join tb_acomp_referencia_item_ari ariA on ariA.cod_iett = acao.cod_iett ");
		// hql.append("inner join tb_acomp_referencia_aref arefA on ariA.cod_aref = arefA.cod_aref ");
		// hql.append("inner join tb_acomp_relatorio_arel arelA on ariA.cod_ari = arelA.cod_ari ");
		// hql.append("left join tb_usuario_usu usuA on usuA.cod_usu = arelA.cod_usuultmanut_arel ");
		// hql.append("left join tb_cor corA on arelA.cod_cor = corA.cod_cor ");

		hql.append("right join tb_item_estrutura_iett prod on acao.cod_iett_pai = prod.cod_iett ");
		// hql.append("inner join tb_acomp_referencia_item_ari ariP on ariP.cod_iett = prod.cod_iett ");
		// hql.append("inner join tb_acomp_referencia_aref arefP on ariP.cod_aref = arefP.cod_aref ");
		// hql.append("inner join tb_acomp_relatorio_arel arelP on ariP.cod_ari = arelP.cod_ari ");
		// hql.append("left join tb_usuario_usu usuP on usuP.cod_usu = arelP.cod_usuultmanut_arel ");
		// hql.append("left join tb_cor corP on arelP.cod_cor = corP.cod_cor ");

		hql.append("right join tb_item_estrutura_iett result on prod.cod_iett_pai = result.cod_iett ");
		// hql.append("inner join tb_acomp_referencia_item_ari ariR on ariR.cod_iett = result.cod_iett ");
		// hql.append("inner join tb_acomp_referencia_aref arefR on ariR.cod_aref = arefR.cod_aref ");
		// hql.append("inner join tb_acomp_relatorio_arel arelR on ariR.cod_ari = arelR.cod_ari ");
		// hql.append("left join tb_usuario_usu usuR on usuR.cod_usu = arelR.cod_usuultmanut_arel ");
		// hql.append("left join tb_cor corR on arelR.cod_cor = corR.cod_cor ");

		hql.append("right join tb_item_estrutura_iett estrat on result.cod_iett_pai = estrat.cod_iett ");
		hql.append("right join tb_item_estrutura_iett obj on estrat.cod_iett_pai = obj.cod_iett ");

		hql.append("where  ");
		// hql.append(" ((prod.ind_ativo_iett = 'S' OR prod.cod_iett is NULL) AND 1=1) ) ");
		// hql.append("AND ((acao.ind_ativo_iett = 'S' OR acao.cod_iett is NULL) AND 1=1) ");
		hql.append("((result.ind_ativo_iett = 'S' OR result.cod_iett is NULL) AND 1=1) ");
		hql.append("and ((estrat.ind_ativo_iett = 'S' OR estrat.cod_iett is NULL) AND 1=1) ");
		hql.append("and obj.ind_ativo_iett = 'S' ");
		// hql.append("and obj.cod_iett = 11 "); //Teste para relatï¿½rios
		/* POR SECRETARIA */
		if (secretarias != null && secretarias.size() != 0) {
			hql.append("and ((prod.cod_orgao_responsavel1_iett in (:secretarias)) ");
			hql.append("OR (result.cod_orgao_responsavel1_iett in (:secretarias))) ");
		}
		/* FIM SECRETARIA */

		if (codigoObjetivoEstrategico.size() != 0) {
			hql.append("and obj.cod_iett in (:codObj) ");
		} else {
			hql.append("and obj.cod_iett in ( ");
			hql.append("select iettObjs.cod_iett from tb_item_estrutura_iett iettObjs ");
			hql.append("where iettObjs.nivel_iett = 1 and iettObjs.ind_ativo_iett='S') ");
		}

		// hql.append("AND arefR.data_ult_manut_aref = ( ");
		// hql.append("select max(aref.data_ult_manut_aref) ");
		// hql.append("from tb_acomp_referencia_aref aref ");
		// hql.append("inner join tb_acomp_referencia_item_ari ari ");
		// hql.append("on ari.cod_aref = aref.cod_aref ) ");

		hql.append(") as temp0  ");

		hql.append("on temp0.codObj = iett.cod_iett ");

		if (etiqueta.size() != 0) {
			hql.append("left join rel_itemestrutiett_etiqueta eti ");
			hql.append("on eti.cod_iett = temp0.codObj ");
			hql.append("LEFT join rel_itemestrutiett_etiqueta eti1 ");
			hql.append("on eti1.cod_iett = temp0.codEstrat ");
			hql.append("LEFT join rel_itemestrutiett_etiqueta eti4 ");
			hql.append("on eti4.cod_iett = temp0.codResult ");
			hql.append("left join rel_itemestrutiett_etiqueta eti2 ");
			hql.append("on eti2.cod_iett = temp0.codProd ");
			hql.append("left join rel_itemestrutiett_etiqueta eti3 ");
			hql.append("on eti3.cod_iett = temp0.codAcao ");
		}

		if (prioritario) {
			hql.append("inner join TB_ITEM_ESTRUTURA_NIVEL_IETTN prioritario ");
		} else {
			hql.append("left join TB_ITEM_ESTRUTURA_NIVEL_IETTN prioritario ");
		}

		hql.append("on temp0.codResult = prioritario.cod_iett ");

		if (etiqueta.size() != 0) {
			hql.append("where etiq.nome_etiqueta_fonetico in (:etiquetas) ");
			hql.append("AND (etiq.cod_etiqueta = eti.cod_etiqueta ");
			hql.append("OR etiq.cod_etiqueta = eti1.cod_etiqueta ");
			hql.append("OR etiq.cod_etiqueta = eti2.cod_etiqueta ");
			hql.append("OR etiq.cod_etiqueta = eti3.cod_etiqueta ");
			hql.append("OR etiq.cod_etiqueta = eti4.cod_etiqueta) ");
			hql.append("AND etiq.ind_ativo = 'S' ");
		}
		// hql.append("or eti1.cod_etiqueta = 19 ");
		// hql.append("or eti2.cod_etiqueta = 19 ");
		// hql.append("or eti3.cod_etiqueta = 19) ");
		// hql.append("and (situacaoEstrat = 6 ");
		// hql.append("or situacaoProd = 6 ");
		// hql.append("or situacaoAcao = 6) ");
		hql.append(")  as tempResult ");

		/* FILTRA USUARIO */
		hql.append(", TB_ITEM_ESTRUT_USUARIO_IETTUS perm ");
		hql.append("where " + "(perm.cod_iett = tempresult.codObj " + "or perm.cod_iett = tempresult.codEstrat " + "or perm.cod_iett = tempresult.codResult "
				+ "or perm.cod_iett = tempresult.codProd " + "or perm.cod_iett = tempresult.codAcao) " + "and (perm.COD_USU= :codUsu " + "or perm.COD_ATB in (:gruposUsuarios)) ");// +
		// "and (perm.IND_EMITE_POS_IETTUS = 'S' OR " +
		// "perm.IND_LEITURA_PARECER_IETTUS = 'S') " +
		// "and perm.IND_INF_ANDAMENTO_IETTUS = 'S' ");
		/* FIM FILTRA USUARIO */

		// hql.append("tb_acomp_referencia_item_ari ari ");
		// hql.append("inner join tb_item_estrutura_iett iett ");
		// hql.append("on ari.cod_iett = iett.cod_iett ");
		// hql.append("inner join tb_acomp_referencia_aref aref ");
		// hql.append("on ari.cod_aref = aref.cod_aref ");
		// hql.append("inner join tb_acomp_relatorio_arel arel ");
		// hql.append("on ari.cod_ari = arel.cod_ari ");
		// hql.append("left join tb_cor cor ");
		// hql.append("on arel.cod_cor = cor.cod_cor  ");

		// hql.append("where ari.cod_iett = iett.cod_iett) ");
		// hql.append("and aref.cod_aref in (14,15,16) ");
		// hql.append("and arel.cod_tpfa in (6,7) ");
		// hql.append("and (tempResult.codAcao = iett.cod_iett ");
		// hql.append("or tempResult.codProd = iett.cod_iett ");
		// hql.append("or tempResult.codResult = iett.cod_iett ");
		// hql.append("or tempResult.codEstrat = iett.cod_iett) ");
		hql.append("order by tempResult.sigla, tempResult.siglaE, tempResult.siglaR, tempResult.siglaP, tempResult.siglaA");
	}

	/**
	 * Recupera os objetivos estratï¿½gicos que contï¿½m estratï¿½gias que
	 * contï¿½m resultados que contï¿½m indicadores :)
	 * 
	 * @param codigoUsuario
	 *            TODO
	 * @param gruposUsuarios
	 *            TODO
	 * @param isIndicador
	 *            TODO
	 * @param apenasParecer
	 *            TODO
	 * @param prioritario
	 *            TODO
	 * @param secretarias
	 *            TODO
	 * @param relEncaminhamento
	 *            TODO
	 * @param dataInicial
	 *            TODO
	 * @param dataFinal
	 *            TODO
	 * @throws ECARException
	 * */

	public List<ObjetivoEstrategico> listarObjetivoEstrategicoFiltroComIndicador(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria,
			List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios, boolean isIndicador, boolean apenasParecer, boolean prioritario, List<Long> secretarias,
			boolean relEncaminhamento, Date dataInicial, Date dataFinal) throws ECARException {
		StringBuilder hql = new StringBuilder();

		queryListaObjetivoEstrategicoFiltro(codigoObjetivoEstrategico, etiqueta, hql, prioritario, secretarias);

		SQLQuery q = this.session.createSQLQuery(hql.toString());
		//
		// q.setInteger("codObj", 10);
		if (codigoObjetivoEstrategico.size() != 0) {
			q.setParameterList("codObj", codigoObjetivoEstrategico);
		}

		if (etiqueta.size() != 0) {
			q.setParameterList("etiquetas", etiqueta);
		}

		if (secretarias != null && secretarias.size() != 0) {
			q.setParameterList("secretarias", secretarias);
		}

		// TODO retirar qnd implementar a passagem no relatorio.
		// codigoUsuario = 1L;
		// gruposUsuarios.add(134L);
		// gruposUsuarios.add(19L);
		// retirar qnd implementar a passagem no relatorio.

		q.setParameter("codUsu", codigoUsuario);
		q.setParameterList("gruposUsuarios", gruposUsuarios);

		// q.addScalar("codResponsavelResultado", Hibernate.LONG);
		// q.addScalar("responsavelResultado", Hibernate.STRING);
		// q.addScalar("mes", Hibernate.STRING);
		// q.addScalar("ano", Hibernate.STRING);
		// q.addScalar("parecer", Hibernate.STRING);
		// q.addScalar("codAri", Hibernate.LONG);
		// q.addScalar("codCor", Hibernate.LONG);
		// q.addScalar("nomeCor", Hibernate.STRING);
		q.addScalar("siglaIett", Hibernate.STRING);
		q.addScalar("siglaEstrategia", Hibernate.STRING);
		q.addScalar("siglaResultado", Hibernate.STRING);
		q.addScalar("siglaProduto", Hibernate.STRING);
		q.addScalar("siglaAcao", Hibernate.STRING);
		q.addScalar("codObj", Hibernate.LONG);
		q.addScalar("codEstrat", Hibernate.LONG);
		q.addScalar("codResult", Hibernate.LONG);
		q.addScalar("codProd", Hibernate.LONG);
		q.addScalar("codAcao", Hibernate.LONG);
		q.addScalar("objetivo", Hibernate.STRING);
		q.addScalar("estrategia", Hibernate.STRING);
		q.addScalar("resultado", Hibernate.STRING);
		q.addScalar("prioritario", Hibernate.STRING);
		q.addScalar("produto", Hibernate.STRING);
		q.addScalar("acao", Hibernate.STRING);
		q.addScalar("dataInicioResultado", Hibernate.DATE);
		q.addScalar("dataFimResultado", Hibernate.DATE);
		q.addScalar("dataInicioProduto", Hibernate.DATE);
		q.addScalar("dataFimProduto", Hibernate.DATE);
		q.addScalar("atencao", Hibernate.BOOLEAN);
		q.addScalar("ativoProd", Hibernate.STRING);
		q.addScalar("ativoAcao", Hibernate.STRING);

		q.setResultTransformer(Transformers.aliasToBean(ObjetivoEstrategico.class));

		List<ObjetivoEstrategico> lista = q.list();

		Map<String, ObjetivoEstrategico> mapaObjetivo = montaObjetivoEstrategicoComIndicador(lista, resultadoStatus, isIndicador, apenasParecer, codigoUsuario, gruposUsuarios, relEncaminhamento,
				dataInicial, dataFinal);

		return new ArrayList<ObjetivoEstrategico>(mapaObjetivo.values());
	}

	private Map<String, ObjetivoEstrategico> montaObjetivoEstrategico(List<ObjetivoEstrategico> lista, List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios) {
		Map<String, ObjetivoEstrategico> mapaObjetivo = new TreeMap<String, ObjetivoEstrategico>();
		Map<Long, Estrategia> mapaEstrategia = new TreeMap<Long, Estrategia>();
		Map<String, Resultado> mapaResultado = new TreeMap<String, Resultado>();
		Map<String, Produto> mapaProduto = new TreeMap<String, Produto>();
		Map<Long, Acao> mapaAcao = new TreeMap<Long, Acao>();

		montaMapas(lista, mapaObjetivo, mapaEstrategia, mapaResultado, mapaProduto, mapaAcao, resultadoStatus, false, codigoUsuario, gruposUsuarios);

		List<Produto> listaProduto = populaAcao(mapaProduto, mapaAcao, false);

		List<Resultado> listaResultado = populaProduto(mapaResultado, listaProduto);

		List<Estrategia> listaEstrategia = populaResultado(mapaEstrategia, listaResultado);

		populaEstrategia(mapaObjetivo, listaEstrategia);

		return mapaObjetivo;
	}

	private Map<String, ObjetivoEstrategico> montaObjetivoEstrategicoComIndicador(List<ObjetivoEstrategico> lista, List<Integer> resultadoStatus, boolean isIndicador, boolean apenasParecer,
			Long codigoUsuario, List<Long> gruposUsuarios, boolean relEncaminhamento, Date dataInicial, Date dataFinal) throws ECARException {
		Map<String, ObjetivoEstrategico> mapaObjetivo = new TreeMap<String, ObjetivoEstrategico>();
		Map<Long, Estrategia> mapaEstrategia = new TreeMap<Long, Estrategia>();
		Map<String, Resultado> mapaResultado = new TreeMap<String, Resultado>();
		Map<String, Produto> mapaProduto = new TreeMap<String, Produto>();
		Map<Long, Acao> mapaAcao = new TreeMap<Long, Acao>();

		montaMapas(lista, mapaObjetivo, mapaEstrategia, mapaResultado, mapaProduto, mapaAcao, resultadoStatus, true, codigoUsuario, gruposUsuarios);

		List<Produto> listaProduto = populaAcaoComIndicador(mapaProduto, mapaAcao);

		List<Resultado> listaResultado = populaProduto(mapaResultado, listaProduto);

		List<Estrategia> listaEstrategia = populaResultadoComIndicador(mapaEstrategia, listaResultado, isIndicador, apenasParecer, relEncaminhamento, dataInicial, dataFinal, false, false);

		populaEstrategia(mapaObjetivo, listaEstrategia);

		return mapaObjetivo;
	}

	private void montaMapas(List<ObjetivoEstrategico> lista, Map<String, ObjetivoEstrategico> mapaObjetivo, Map<Long, Estrategia> mapaEstrategia, Map<String, Resultado> mapaResultado,
			Map<String, Produto> mapaProduto, Map<Long, Acao> mapaAcao, List<Integer> resultadoStatus, boolean comIndicador, Long codigoUsuario, List<Long> gruposUsuarios) {
		for (ObjetivoEstrategico obj : lista) {
			if (!mapaObjetivo.containsKey(obj.getSiglaIett())) {
				mapaObjetivo.put(obj.getSiglaIett(), new ObjetivoEstrategico(obj.getCodObj(), obj.getObjetivo(), obj.getSiglaIett()));
			}

			if (!mapaEstrategia.containsKey(obj.getCodEstrat())) {
				mapaEstrategia.put(obj.getCodEstrat(), new Estrategia(obj.getCodEstrat(), obj.getEstrategia(), obj.getCodObj(), obj.getSiglaEstrategia()));
			}

			if (obj.getCodResult() != null && !mapaResultado.containsKey(obj.getCodResult().toString())) {
				List<Resultado> resultado = buscarComplemento(obj.getCodResult(), resultadoStatus, codigoUsuario, gruposUsuarios);
				if (resultado.size() > 0) {
					mapaResultado.put(obj.getCodResult().toString(),
							new Resultado(obj.getCodResult(), obj.getResultado(), obj.getCodEstrat(), resultado.get(0).getCodAri(), comIndicador ? resultado.get(0).getParecer() : "", resultado.get(0)
									.getMes(), resultado.get(0).getAno(), resultado.get(0).getCodCor(), resultado.get(0).getNomeCor(), obj.getPrioritario(), obj.getSiglaResultado(), resultado.get(0)
									.getUsu(), resultado.get(0).getCodResponsavel(), obj.getDataInicioResultado(), obj.getDataFimResultado(), obj.isAtencao(), resultado.get(0).getOrgao()));
					for (Responsavel responsavel : buscarCoResponsavel(obj.getCodResult())) {
						if (responsavel.getCodTipo().equals(2)) {
							mapaResultado.get(obj.getCodResult().toString()).getArticulacao().add(responsavel);
						} else {
							mapaResultado.get(obj.getCodResult().toString()).getCoResponsavel().add(responsavel);
						}
					}

				}
				// Se for necessÃ¡rio mostrar os resultados sem ARI, mas que
				// tenham produtos com ARI(monitorados)
				// else{
				// mapaResultado.put(obj.getCodResult(), new Resultado(
				// obj.getCodResult(), obj.getResultado(), obj.getCodEstrat(),
				// null,
				// null, null, null,
				// null, null,
				// obj.getPrioritario(), obj.getSiglaResultado(), null, null));
				// }
			}

			if (obj.getCodProd() != null && !mapaProduto.containsKey(obj.getCodProd().toString()) && (obj.getAtivoProd() != null && obj.getAtivoProd().equals("S"))) {
				List<Produto> produto = buscarComplementoProduto(obj.getCodProd(), codigoUsuario, gruposUsuarios);
				if (produto.size() > 0) {
					mapaProduto.put(obj.getCodProd().toString(), new Produto(obj.getCodProd(), obj.getProduto(), obj.getCodResult(), obj.getSiglaProduto(), produto.get(0).getUsu(), produto.get(0)
							.getCodResponsavel(), produto.get(0).getCodAri(), comIndicador ? produto.get(0).getParecer() : "", produto.get(0).getMes(), produto.get(0).getAno(), produto.get(0)
							.getCodCor(), produto.get(0).getNomeCor(), obj.getDataInicioProduto(), obj.getDataFimProduto(), produto.get(0).getOrgao()));

					for (Responsavel responsavel : buscarCoResponsavel(obj.getCodProd())) {
						if (responsavel.getCodTipo().equals(2)) {
							mapaProduto.get(obj.getCodProd().toString()).getCoResponsavel().add(responsavel);
						}
					}
				}
			}

			if (comIndicador && obj.getCodAcao() != null && !mapaAcao.containsKey(obj.getCodAcao()) && (obj.getAtivoAcao() != null && obj.getAtivoAcao().equals("S"))) {
				mapaAcao.put(obj.getCodAcao(), new Acao(obj.getCodAcao(), obj.getAcao(), obj.getCodProd(), obj.getSiglaAcao()));
			}
		}
	}

	private Collection<Responsavel> buscarCoResponsavel(Long codigo) {
		String sql = "select iette.cod_ent as codigo, ent.nome_ent as nome, iette.cod_tpp as codTipo " + "from TB_ITEM_ESTRUT_ENTIDADE_IETTE iette " + "inner join TB_ENTIDADE_ENT ent "
				+ "on ent.cod_ent = iette.cod_ent " + "inner join tb_item_estrutura_iett iett " + "on iett.cod_iett = iette.cod_iett " + "where iett.cod_iett = :codigo "
				+ "and iett.ind_ativo_iett = 'S' ";

		SQLQuery q = this.session.createSQLQuery(sql.toString());
		q.setParameter("codigo", codigo);

		q.addScalar("codigo", Hibernate.LONG);
		q.addScalar("nome", Hibernate.STRING);
		q.addScalar("codTipo", Hibernate.INTEGER);

		q.setResultTransformer(Transformers.aliasToBean(Responsavel.class));
		List<Responsavel> lista = q.list();
		if (lista.size() == 0) {
			Responsavel coRespVAZ = new Responsavel();
			coRespVAZ.setCodTipo(1);
			lista.add(coRespVAZ);

			Responsavel artiVAZ = new Responsavel();
			artiVAZ.setCodTipo(2);
			lista.add(artiVAZ);
		}
		return lista;
	}

	private List<Resultado> buscarComplemento(Long codigo, List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios) {
		SQLQuery q = queryComplemento(codigo, resultadoStatus, true, codigoUsuario, gruposUsuarios);

		q.setResultTransformer(Transformers.aliasToBean(Resultado.class));
		List<Resultado> resultado = q.list();
		return resultado;
	}

	private List<Produto> buscarComplementoProduto(Long codigo, Long codigoUsuario, List<Long> gruposUsuarios) {
		SQLQuery q = queryComplemento(codigo, null, false, codigoUsuario, gruposUsuarios);

		q.setResultTransformer(Transformers.aliasToBean(Produto.class));
		List<Produto> produto = q.list();
		return produto;
	}

	private SQLQuery queryComplemento(Long codigo, List<Integer> resultadoStatus, boolean isResultado, Long codigoUsuario, List<Long> gruposUsuarios) {
		boolean retiraCodZero = false;
		String sql = "select distinct ari.cod_ari as codAri, " + "CASE WHEN arel.descricao_arel ISNULL  THEN (" + "select " + "case " + "when (select count(ari.cod_ari) " + "from "
				+ "tb_acomp_referencia_item_ari ari " + "inner join tb_acomp_referencia_aref arefT " + "on ari.cod_aref = arefT.cod_aref " + "inner join tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "WHERE ari.cod_iett = :codigo " + "and arel.data_ult_manut_arel = " + "(select max(arel.data_ult_manut_arel) "
				+ "from tb_acomp_referencia_item_ari ari " + "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari " + "where ari.cod_iett = :codigo)) = 0 " + "then (SELECT "
				+ "arel.descricao_arel " + "from " + "tb_acomp_referencia_item_ari ari " + "inner join tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "WHERE "
				+ "ari.cod_iett = :codigo  " + "order by " + "aref.data_ult_manut_aref LIMIT 1 ) " + "else (select arel.descricao_arel " + "from " + "tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_referencia_aref arefT " + "on ari.cod_aref = arefT.cod_aref " + "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari "
				+ "WHERE ari.cod_iett = :codigo " + "and arel.data_ult_manut_arel = " + "(select max(arel.data_ult_manut_arel) " + "from tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari " + "where ari.cod_iett = :codigo)) " + "end) " + "ELSE (arel.descricao_arel) " + "END as parecer, " +

				"CASE WHEN " + "usu.nome_usu ISNULL  THEN " + "(select usuUtfa.nome_usu from TB_ITEM_EST_USUTPFUAC_IETTUTFA utfa " + "inner join tb_usuario_usu usuUtfa "
				+ "on usuUtfa.cod_usu = utfa.cod_usu " + "where utfa.cod_iett = :codigo) " + "ELSE (usu.nome_usu) " + "END as usu, " + "usu.cod_usu as codResponsavel, " +

				"CASE WHEN cor.cod_cor ISNULL  THEN ( " + "select " + "case " + "when (select count(ari.cod_ari) " + "from " + "tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_referencia_aref arefT " + "on ari.cod_aref = arefT.cod_aref " + "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari "
				+ "WHERE ari.cod_iett = :codigo " + "and arel.data_ult_manut_arel = " + "(select max(arel.data_ult_manut_arel) " + "from tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari " + "where ari.cod_iett = :codigo)) = 0 " + "then (SELECT " + "cor.cod_cor " + "from "
				+ "tb_acomp_referencia_item_ari ari " + "inner join tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join tb_cor cor " + "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codigo  " + "order by "
				+ "aref.data_ult_manut_aref LIMIT 1 ) " + "else (select cor.cod_cor " + "from " + "tb_acomp_referencia_item_ari ari " + "inner join tb_acomp_referencia_aref arefT "
				+ "on ari.cod_aref = arefT.cod_aref " + "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari " + "inner join tb_cor cor " + "on arel.cod_cor = cor.cod_cor "
				+ "WHERE ari.cod_iett = :codigo " + "and arel.data_ult_manut_arel = " + "(select max(arel.data_ult_manut_arel) " + "from tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari " + "where ari.cod_iett = :codigo)) " + "end) " + "ELSE (cor.cod_cor) " + "END as codCor, " +

				"CASE WHEN cor.nome_cor ISNULL  THEN ( " + "select " + "case " + "when (select count(ari.cod_ari) " + "from " + "tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_referencia_aref arefT " + "on ari.cod_aref = arefT.cod_aref " + "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari "
				+ "WHERE ari.cod_iett = :codigo " + "and arel.data_ult_manut_arel = " + "(select max(arel.data_ult_manut_arel) " + "from tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari " + "where ari.cod_iett = :codigo)) = 0 " + "then (SELECT " + "cor.significado_cor " + "from "
				+ "tb_acomp_referencia_item_ari ari " + "inner join tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join tb_cor cor " + "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codigo  " + "order by "
				+ "aref.data_ult_manut_aref LIMIT 1 ) " + "else (select cor.significado_cor " + "from " + "tb_acomp_referencia_item_ari ari " + "inner join tb_acomp_referencia_aref arefT "
				+ "on ari.cod_aref = arefT.cod_aref " + "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari " + "inner join tb_cor cor " + "on arel.cod_cor = cor.cod_cor "
				+ "WHERE ari.cod_iett = :codigo " + "and arel.data_ult_manut_arel = " + "(select max(arel.data_ult_manut_arel) " + "from tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari " + "where ari.cod_iett = :codigo)) " + "end) " + "ELSE (cor.significado_cor) " + "END as nomeCor, " +

				"CASE WHEN cor.cod_cor ISNULL  THEN (" + "select " + "case " + "when (select count(ari.cod_ari) " + "from " + "tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_referencia_aref arefT " + "on ari.cod_aref = arefT.cod_aref " + "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari "
				+ "WHERE ari.cod_iett = :codigo " + "and arel.data_ult_manut_arel = " + "(select max(arel.data_ult_manut_arel) " + "from tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari " + "where ari.cod_iett = :codigo)) = 0 " + "then (SELECT " + "aref.mes_aref " + "from "
				+ "tb_acomp_referencia_item_ari ari " + "inner join tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "WHERE " + "ari.cod_iett = :codigo  " + "order by "
				+ "aref.data_ult_manut_aref LIMIT 1 ) " + "else (select arefT.mes_aref " + "from " + "tb_acomp_referencia_item_ari ari " + "inner join tb_acomp_referencia_aref arefT "
				+ "on ari.cod_aref = arefT.cod_aref " + "inner join tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari " + "WHERE ari.cod_iett = :codigo "
				+ "and arel.data_ult_manut_arel = " + "(select max(arel.data_ult_manut_arel) " + "from tb_acomp_referencia_item_ari ari " + "inner join tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari "
				+ "where ari.cod_iett = :codigo)) "
				+ "end) "
				+ "else (aref.mes_aref) "
				+ "END as mes, "
				+

				"CASE WHEN cor.cod_cor ISNULL  THEN ("
				+ "select "
				+ "case "
				+ "when (select count(ari.cod_ari) "
				+ "from "
				+ "tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_referencia_aref arefT "
				+ "on ari.cod_aref = arefT.cod_aref "
				+ "inner join tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari "
				+ "WHERE ari.cod_iett = :codigo "
				+ "and arel.data_ult_manut_arel = "
				+ "(select max(arel.data_ult_manut_arel) "
				+ "from tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari "
				+ "where ari.cod_iett = :codigo)) = 0 "
				+ "then (SELECT "
				+ "aref.ano_aref "
				+ "from "
				+ "tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_referencia_aref aref "
				+ "on ari.cod_aref = aref.cod_aref "
				+ "WHERE "
				+ "ari.cod_iett = :codigo  "
				+ "order by "
				+ "aref.data_ult_manut_aref LIMIT 1 ) "
				+ "else (select arefT.ano_aref "
				+ "from "
				+ "tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_referencia_aref arefT "
				+ "on ari.cod_aref = arefT.cod_aref "
				+ "inner join tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari "
				+ "WHERE ari.cod_iett = :codigo "
				+ "and arel.data_ult_manut_arel = "
				+ "(select max(arel.data_ult_manut_arel) "
				+ "from tb_acomp_referencia_item_ari ari "
				+ "inner join tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari "
				+ "where ari.cod_iett = :codigo)) "
				+ "end) "
				+ "else (aref.ano_aref) "
				+ "END as ano, "
				+

				"CASE WHEN usu.nome_usu ISNULL  THEN ( "
				+ "select o.sigla_org from tb_usuario_usu u "
				+ "inner join TB_USUARIO_ORGAO_USUORG uo "
				+ "on u.cod_usu = uo.cod_usu "
				+ "inner join TB_ORGAO_ORG o "
				+ "on o.cod_org = uo.cod_org "
				+ "inner join TB_ITEM_EST_USUTPFUAC_IETTUTFA utfa "
				+ "on u.cod_usu = utfa.cod_usu "
				+ "where "
				+ "utfa.cod_iett = :codigo LIMIT 1) "
				+ "ELSE (o.sigla_org) "
				+ "END as orgao "
				+
				// "from tb_item_estrutura_iett iett " +
				// "inner join tb_acomp_referencia_item_ari ari " +
				// "on ari.cod_iett = iett.cod_iett " +
				// "inner join " +
				// "tb_acomp_referencia_aref aref " +
				// "on ari.cod_aref = aref.cod_aref " +
				// "left join " +
				// "tb_acomp_relatorio_arel arel " +
				// "on ari.cod_ari = arel.cod_ari " +
				// "left join " +
				// "tb_usuario_usu usu " +
				// "on usu.cod_usu = arel.cod_usuultmanut_arel " +
				// "left join " +
				// "tb_cor cor " +
				// "on arel.cod_cor = cor.cod_cor " +
				// "inner join TB_ITEM_ESTRUT_USUARIO_IETTUS perm " +
				// "on perm.cod_iett = iett.cod_iett " +

				"from TB_ACOMP_REFERENCIA_ITEM_ARI ari " + "inner join TB_ITEM_ESTRUTURA_IETT iett " + "on ari.COD_IETT=iett.COD_IETT " + "inner join TB_ITEM_ESTRUT_USUARIO_IETTUS perm "
				+ "on iett.COD_IETT=perm.COD_IETT " + "inner join TB_ACOMP_REFERENCIA_AREF aref " + "on ari.COD_AREF=aref.COD_AREF " + "inner join TB_TIPO_ACOMPANHAMENTO_TA ta "
				+ "on aref.COD_TA=ta.COD_TA " + "inner join tb_tipoacomp_tipofuncacomp_sisatributo_tatpfasatb permTAGRU " + "on ta.COD_TA=permTAGRU.cod_ta "
				+ "left outer join TB_ACOMP_RELATORIO_AREL arel " + "on ari.COD_ARI=arel.COD_ARI " + "left join tb_usuario_usu usu " + "on usu.cod_usu = arel.cod_usuultmanut_arel " +

				"left join TB_USUARIO_ORGAO_USUORG uo " + "on usu.cod_usu = uo.cod_usu " + "left join TB_ORGAO_ORG o " + "on o.cod_org = uo.cod_org " +

				"left join tb_cor cor " + "on arel.cod_cor = cor.cod_cor , " + "TB_TIPO_FUNC_ACOMP_TPFA tpfa " +

				"where iett.cod_iett = :codigo " + "and permTAGRU.cod_tpfa=tpfa.COD_TPFA ";

		sql += "AND aref.data_ult_manut_aref = ( " + "select max(aref.data_ult_manut_aref) " + "from tb_acomp_referencia_aref aref " + "inner join tb_acomp_referencia_item_ari ari "
				+ "on ari.cod_aref = aref.cod_aref " + "inner join TB_ACOMP_RELATORIO_AREL arel " + "on ari.COD_ARI=arel.COD_ARI " + "where ari.cod_iett = iett.cod_iett ) ";

		sql += "and iett.ind_ativo_iett = 'S' " + "and (perm.IND_EMITE_POS_IETTUS='S' " + "or perm.IND_LEITURA_PARECER_IETTUS='S' " + "or perm.IND_INF_ANDAMENTO_IETTUS='S') "
				+ "and (perm.COD_USU=:codUsu " + "or perm.COD_ATB in (:gruposUsuarios)) " + "and permTAGRU.ind_leitura_parecer='S' " + "and tpfa.IND_EMITE_POSICAO_TPFA='S' "
				+ "and (permTAGRU.cod_satb in (:gruposUsuarios)) ";

		// TODO Se for necessÃ¡rio adicionar o filtro por Aref e TA descomentar
		// abaixo
		// "and (ari.COD_AREF in (:codAref)) " +
		// "and aref.COD_TA=:codTa "
		if (resultadoStatus != null && resultadoStatus.size() > 0) {
			// Verifica se tem o cod 0 se sim retira e busca por null
			if (resultadoStatus.contains(0)) {
				resultadoStatus.remove((resultadoStatus.indexOf(0)));
				retiraCodZero = true;
				sql += "and (cor.cod_cor is null ";
				if (resultadoStatus.size() > 0) {
					sql += "or cor.cod_cor in (:status) ";
				}
				sql += ") ";
			} else {
				sql += "and cor.cod_cor in (:status) ";
			}
		}

		SQLQuery q = this.session.createSQLQuery(sql.toString());
		q.setParameter("codigo", codigo);

		if (resultadoStatus != null && resultadoStatus.size() > 0) {
			q.setParameterList("status", resultadoStatus);
		}

		q.setParameter("codUsu", codigoUsuario);
		q.setParameterList("gruposUsuarios", gruposUsuarios);

		// Recoloca o cod 0 se foi retirado anteriormente.
		if (retiraCodZero) {
			resultadoStatus.add(0);
		}

		q.addScalar("codAri", Hibernate.LONG);
		q.addScalar("parecer", Hibernate.STRING);
		q.addScalar("usu", Hibernate.STRING);
		q.addScalar("codResponsavel", Hibernate.LONG);
		q.addScalar("codCor", Hibernate.LONG);
		q.addScalar("nomeCor", Hibernate.STRING);
		q.addScalar("mes", Hibernate.STRING);
		q.addScalar("ano", Hibernate.STRING);
		q.addScalar("orgao", Hibernate.STRING);
		return q;
	}

	private List<Produto> populaAcao(Map<String, Produto> mapaProduto, Map<Long, Acao> mapaAcao, boolean isResultadoOuProduto) {
		List<Produto> lista = new ArrayList<Produto>(mapaProduto.values());

		if (lista != null) {
			Collections.sort(lista);

			// mapaProduto.clear();
			if (isResultadoOuProduto) {
				for (Produto produto : lista) {
					for (Acao acao : mapaAcao.values()) {
						if (acao.getCodProd() == produto.getCodigo()) {
							produto.addAcoes(acao);
						}
					}
					// mapaProduto.put(produto.getSiglaProduto(), produto);
				}
			}
		}
		return lista;
	}

	private List<Produto> populaAcaoComIndicador(Map<String, Produto> mapaProduto, Map<Long, Acao> mapaAcao) {
		List<Produto> lista = new ArrayList<Produto>(mapaProduto.values());
		// Collections.sort(lista);
		mapaProduto.clear();

		for (Produto produto : lista) {

			for (Acao acao : mapaAcao.values()) {
				if (acao.getCodProd() == produto.getCodigo()) {
					produto.addAcoes(acao);
				}
			}
			// mapaProduto.put(produto.getSiglaProduto(), produto);

		}
		return lista;
	}

	private List<Resultado> populaProduto(Map<String, Resultado> mapaResultado, List<Produto> listaProduto) {
		// List<Long> listaExclusao = new ArrayList<Long>();
		List<Resultado> lista = new ArrayList<Resultado>(mapaResultado.values());
		Collections.sort(lista);
		// mapaResultado.clear();

		for (Resultado resultado : lista) {
			for (Produto produto : listaProduto) {
				if (produto.getCodResultado() == resultado.getCodigo()) {
					resultado.addProdutos(produto);
				}

			}

			// mapaResultado.put(resultado.getSiglaResultado(), resultado);

			// Se for necessÃ¡rio mostrar os resultados sem ARI, mas que tenham
			// produtos com ARI(monitorados)
			// if(resultado.getProdutos().size() == 0 && resultado.getCodAri()
			// == null){
			// listaExclusao.add(resultado.getCodigo());
			// }
		}
		return lista;
		// Se for necessÃ¡rio mostrar os resultados sem ARI, mas que tenham
		// produtos com ARI(monitorados)
		// for (Long exclui : listaExclusao) {
		// mapaResultado.remove(exclui);
		// }
	}

	private List<Estrategia> populaResultado(Map<Long, Estrategia> mapaEstrategia, List<Resultado> listaResultado) {
		List<Estrategia> listaExclusao = new ArrayList<Estrategia>();
		List<Estrategia> lista = new ArrayList<Estrategia>(mapaEstrategia.values());
		Collections.sort(lista);
		mapaEstrategia.clear();

		for (Estrategia estrategia : lista) {
			for (Resultado resultado : listaResultado) {
				if (resultado.getCodEstrat() == estrategia.getCodigo()) {
					estrategia.addResultados(resultado);
				}
			}
			if (estrategia.getResultados().size() == 0) {
				listaExclusao.add(estrategia);
			}

			// mapaEstrategia.put(new Long(estrategia.getSiglaEstrategia()),
			// estrategia);
		}

		for (Estrategia exclui : listaExclusao) {
			lista.remove(exclui);
		}
		return lista;
	}

	/**
	 * Popula apenas resultados que tem indicador
	 * 
	 * @param isIndicador
	 *            TODO
	 * @param apenasParecer
	 *            TODO
	 * @param dataInicial
	 *            TODO
	 * @param dataFinal
	 *            TODO
	 * @param somenteREM
	 *            TODO
	 * @param isDetalhamento
	 *            TODO
	 * @throws ECARException
	 * */
	private List<Estrategia> populaResultadoComIndicador(Map<Long, Estrategia> mapaEstrategia, List<Resultado> listaResultado, boolean isIndicador, boolean apenasParecer, boolean relEncaminhamento,
			Date dataInicial, Date dataFinal, boolean somenteREM, boolean isDetalhamento) throws ECARException {
		List<Estrategia> listaExclusao = new ArrayList<Estrategia>();
		List<Estrategia> lista = new ArrayList<Estrategia>(mapaEstrategia.values());
		Collections.sort(lista);

		for (Estrategia estrategia : lista) {
			for (Resultado resultado : listaResultado) {
				if (!apenasParecer) {
					resultado.setIndicador(getListIndicador(resultado.getCodigo(), somenteREM));
					if (isDetalhamento) {
						populaIndicadorDetalhamento(resultado);
					}
				}

				if (isIndicador) {
					if (resultado.getCodEstrat() == estrategia.getCodigo() && !(resultado.getIndicador() == null || resultado.getIndicador().isEmpty())) {
						estrategia.addResultados(resultado);
					}
				} else {
					if (resultado.getCodEstrat() == estrategia.getCodigo()) {
						if (relEncaminhamento) {
							resultado.setComentarios(daoIettAcao.loadComentarioWS(resultado.getCodigo(), dataInicial, dataFinal));
							if ((resultado.getComentarios() == null || resultado.getComentarios().isEmpty())) {
								continue;
							}
						}

						estrategia.addResultados(resultado);
					}
				}

			}
			if (estrategia.getResultados().size() == 0) {
				listaExclusao.add(estrategia);
			}
		}

		for (Estrategia exclui : listaExclusao) {
			lista.remove(exclui);
		}

		return lista;
	}

	private void populaIndicadorDetalhamento(Resultado resultado) {
		List<String> meses = new ArrayList<String>();
		ExercicioExe exercicio = new ExercicioExe();
		try {
			exercicio = exercicioDao.getExercicio("05", "2013");
			meses = exercicioDao.getMesesDentroDoExercicio(exercicio);
		} catch (ECARException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (Indicador indicador : resultado.getIndicador()) {
			List valores = new ArrayList();

			try {
				String grupoIndicador = "";
				ItemEstrtIndResulIettr indResul = new ItemEstrtIndResulIettr();
				indResul.setCodIettir(new Long(indicador.getCodigo()));
				Map mapMeses = daoRealFisico.getQtdRealizadaExercicioPorMes(exercicio, indResul);
				// Iterator itMeses = meses.iterator();
				double total = 0;
				IndicadorResultado wrapperIndicador = new IndicadorResultado(indResul.getCodIettir());

				double totalPrevisto = 0.0;
				for (String datas : meses) {
					IndicadorDetalhamento detalhamentoPrevisto = new IndicadorDetalhamento();
					IndicadorDetalhamento detalhamentoRealizado = new IndicadorDetalhamento();

					String mes = datas.split("-")[0];
					String ano = datas.split("-")[1];
					EcarData ecarData = new EcarData(mes, ano);

					Previsto prev = wrapperIndicador.getPrevistoMensal(ecarData);

					String valorPrevisto = "-";

					if (prev != null) {
						valorPrevisto = prev.getPrevistoFormatado();
						totalPrevisto += prev.getValorPrevisto();
					}
					// System.out.println(mes + "/" + ano);
					// System.out.println("P - " + valorPrevisto);
					detalhamentoPrevisto.setMes(Data.getAbreviaturaMes(Integer.valueOf(mes)));
					detalhamentoPrevisto.setAno(ano);
					// detalhamentoPrevisto.setPrevistoNoMes(prev!=null?prev.getValorPrevisto():0.0);
					detalhamentoPrevisto.setRealizadoNoMes(prev != null ? prev.getValorPrevisto() : 0.0);
					detalhamentoPrevisto.setTipo("P");
					indicador.getDetalhe().add(detalhamentoPrevisto);

					boolean possuiValor = true;
					String mesAnoMap = datas;// itMeses.next().toString();
					Object m = mapMeses.get(mesAnoMap);
					if (m == null) {
						possuiValor = false;
					}

					String strValor = "";
					Double valor = 0.0;
					if (possuiValor) {
						valor = new Double(m.toString());
						valores.add(valor);

						strValor = Util.formataMoeda(valor);

					} else {
						strValor = "-";
					}
					// System.out.println(mesAnoMap);
					// System.out.println("R - " + strValor);

					detalhamentoRealizado.setMes(Data.getAbreviaturaMes(Integer.valueOf(mes)));
					detalhamentoRealizado.setAno(ano);
					detalhamentoRealizado.setRealizadoNoMes(valor);
					detalhamentoRealizado.setTipo("R");
					indicador.getDetalhe().add(detalhamentoRealizado);

				}

				/*
				 * String totalPrevistoStr = ""; totalPrevistoStr =
				 * Util.formataMoeda(totalPrevisto);
				 * System.out.println(totalPrevistoStr);
				 * 
				 * Collections.reverse(valores); total =
				 * daoRealFisico.getSomaValoresArfs
				 * (wrapperIndicador.getRealObject(), valores).doubleValue();
				 * String strTotal = ""; strTotal =
				 * Util.formataMoeda(Double.valueOf(total));
				 * System.out.println(strTotal);
				 */

			} catch (ECARException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void populaEstrategia(Map<String, ObjetivoEstrategico> mapaObjetivo, List<Estrategia> listaEstrategia) {
		List<String> listaExclusao = new ArrayList<String>();

		for (ObjetivoEstrategico objetivoEstrategico : mapaObjetivo.values()) {
			for (Estrategia estrategia : listaEstrategia) {
				if (estrategia.getCodObj() == objetivoEstrategico.getCodigo()) {
					objetivoEstrategico.addEstrategias(estrategia);
				}
			}
			if (objetivoEstrategico.getEstrategias().size() == 0) {
				listaExclusao.add(objetivoEstrategico.getSiglaIett());
			}
		}

		for (String exclui : listaExclusao) {
			mapaObjetivo.remove(exclui);
		}
	}

	/**
	 * Recupera os descendentes e ordena por sigla e nome.
	 * 
	 * @param codigoIett
	 * @return
	 */
	public List<ItemEstruturaIett> listarDescendentes(Long codigoIett) {
		StringBuilder hql = new StringBuilder();
		hql.append("select new ItemEstruturaIett(item.siglaIett, item.nomeIett, item.dataInicioIett, ");
		hql.append("item.dataTerminoIett) from ItemEstruturaIett item where item.itemEstruturaIett.codIett = ? ");
		hql.append("and item.indAtivoIett = 'S' order by item.siglaIett, item.nomeIett");

		return this.listarPorHQL(hql.toString(), codigoIett);
	}

	public Resultado listarResultado(Integer codigoResultado, String mes, String ano, Long codigoUsuario, List<Long> gruposUsuarios, String uuid) throws ECARException {
		// TODO Retirar essa verificaï¿½ï¿½o, quando atualizar o site para
		// sempre passar mes e ano.
		// if(mes == null){
		// List<PeriodoAcompanhamento> periodos =
		// recuperaPeriodosAcompanhamento(codigoResultado.longValue());
		// if(periodos.size()>0){
		// mes = periodos.get(0).getMes();
		// ano = Integer.toString(periodos.get(0).getAno());
		// }
		// }
		// TODO Retirar os cases, quando atualizar o site para sempre passar mes
		// e ano.

		this.uuid = uuid;

		StringBuilder hql = new StringBuilder();
		hql.append("select distinct result.cod_iett as codigo, result.sigla_iett as siglaResultado, " + "prod.sigla_iett as siglaProduto, " + "ariR.cod_ari as codAri, " + "ariP.cod_ari as codAriP, "
				+ "usu.nome_usu as usu, usu.cod_usu as codResponsavel, " + "usup.nome_usu as usuP, usup.cod_usu as codRespProduto, " +

				"CASE WHEN cor.cod_cor ISNULL  THEN (SELECT " + "cor.cod_cor  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join " + "tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_cor cor "
				+ "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codResultado " + "order by " + "aref.data_ult_manut_aref LIMIT 1 ) " + "ELSE (cor.cod_cor) " + "END as codCor, " +

				"CASE WHEN cor.nome_cor ISNULL  THEN (SELECT " + "cor.significado_cor  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join " + "tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_cor cor "
				+ "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codResultado " + "order by " + "aref.data_ult_manut_aref LIMIT 1 ) " + "ELSE (cor.significado_cor) "
				+ "END as nomeCor, ");
		hql.append("CASE WHEN corP.cod_cor ISNULL  THEN (SELECT " + "cor.cod_cor  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join " + "tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_cor cor "
				+ "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codResultado " + "order by " + "aref.data_ult_manut_aref LIMIT 1 ) " + "ELSE (corP.cod_cor) " + "END as codCorP, " +

				"CASE WHEN corP.nome_cor ISNULL  THEN (SELECT " + "cor.significado_cor  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join " + "tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_cor cor "
				+ "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codResultado " + "order by " + "aref.data_ult_manut_aref LIMIT 1 ) " + "ELSE (corP.significado_cor) "
				+ "END as nomeCorP, ");
		hql.append("aref.cod_aref as codAref,arefP.cod_aref as codArefP, " + "CASE WHEN cor.cod_cor ISNULL  THEN (" + "SELECT aref.mes_aref  from " + "tb_acomp_referencia_item_ari ari "
				+ "inner join " + "tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "WHERE ari.cod_iett = :codResultado " + "order by aref.data_ult_manut_aref " + "LIMIT 1 "
				+ ") " + "ELSE (aref.mes_aref) " + "END as mes, " +

				"CASE WHEN cor.cod_cor ISNULL  THEN (" + "SELECT aref.ano_aref  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_referencia_aref aref "
				+ "on ari.cod_aref = aref.cod_aref " + "WHERE ari.cod_iett = :codResultado " + "order by aref.data_ult_manut_aref " + "LIMIT 1 " + ") " + "ELSE (aref.ano_aref) " + "END as ano, ");
		hql.append("CASE WHEN corP.cod_cor ISNULL  THEN (" + "SELECT aref.mes_aref  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_referencia_aref aref "
				+ "on ari.cod_aref = aref.cod_aref " + "WHERE ari.cod_iett = :codResultado " + "order by aref.data_ult_manut_aref " + "LIMIT 1 " + ") " + "ELSE (arefP.mes_aref) " + "END as mesP, " +

				"CASE WHEN corP.cod_cor ISNULL  THEN (" + "SELECT aref.ano_aref  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_referencia_aref aref "
				+ "on ari.cod_aref = aref.cod_aref " + "WHERE ari.cod_iett = :codResultado " + "order by aref.data_ult_manut_aref " + "LIMIT 1 " + ") " + "ELSE (arefP.ano_aref) " + "END as anoP, ");
		hql.append("CASE WHEN arel.descricao_arel ISNULL  THEN (" + "SELECT arel.descricao_arel  " + "from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "WHERE " + "ari.cod_iett = :codResultado " + "order by " + "arel.data_ult_manut_arel LIMIT 1  ) " + "ELSE (arel.descricao_arel) "
				+ "END as parecer, ");
		hql.append("CASE WHEN arelP.descricao_arel ISNULL  THEN (" + "SELECT arel.descricao_arel  " + "from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "WHERE " + "ari.cod_iett = :codResultado " + "order by " + "arel.data_ult_manut_arel LIMIT 1  ) " + "ELSE (arelP.descricao_arel) "
				+ "END as parecerP, ");
		hql.append("result.cod_iett as codResul, result.nome_iett as nomeResul, ");
		hql.append("prod.cod_iett codProd, ");
		hql.append("prod.nome_iett as produto," + "prod.data_inicio_iett as dataInicio, prod.data_termino_iett as dataFim, ");

		hql.append("acao.cod_iett as codAcao, " + "acao.nome_iett as acao, " + "acao.sigla_iett as siglaAcao, " + "acao.data_inicio_iett as dataInicioAcao, "
				+ "acao.data_termino_iett as dataFimAcao, " + "ariA.cod_ari as codAriA, " + "usuA.cod_usu as codRespAcao, " + "usua.nome_usu as responsavelAcao, " +

				"CASE WHEN corA.cod_cor ISNULL  THEN (SELECT " + "cor.cod_cor  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join " + "tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_cor cor "
				+ "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codResultado " + "order by " + "aref.data_ult_manut_aref LIMIT 1 ) " + "ELSE (corA.cod_cor) " + "END as codCorA, " +

				"CASE WHEN corA.nome_cor ISNULL  THEN (SELECT " + "cor.significado_cor  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join " + "tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_cor cor "
				+ "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codResultado " + "order by " + "aref.data_ult_manut_aref LIMIT 1 ) " + "ELSE (corA.significado_cor) "
				+ "END as nomeCorA, " + "arefA.cod_aref as codArefA, " +

				"CASE WHEN corA.cod_cor ISNULL  THEN (" + "SELECT aref.mes_aref  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_referencia_aref aref "
				+ "on ari.cod_aref = aref.cod_aref " + "WHERE ari.cod_iett = :codResultado " + "order by aref.data_ult_manut_aref " + "LIMIT 1 " + ") " + "ELSE (arefA.mes_aref) " + "END as mesA, " +

				"CASE WHEN corA.cod_cor ISNULL  THEN (" + "SELECT aref.ano_aref  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_referencia_aref aref "
				+ "on ari.cod_aref = aref.cod_aref " + "WHERE ari.cod_iett = :codResultado " + "order by aref.data_ult_manut_aref " + "LIMIT 1 " + ") " + "ELSE (arefA.ano_aref) " + "END as anoA, " +

				"CASE WHEN arelA.descricao_arel ISNULL  THEN (" + "SELECT arel.descricao_arel  " + "from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "WHERE " + "ari.cod_iett = :codResultado " + "order by " + "arel.data_ult_manut_arel LIMIT 1  ) " + "ELSE (arelA.descricao_arel) "
				+ "END as parecerA, " +

				"result.atencao_iett as atencao,  " + "prioritario.cod_atb as prioritario," + "prod.ind_ativo_iett as ativoProd, " + "acao.ind_ativo_iett as ativoAcao ");

		hql.append("from tb_item_estrutura_iett acao ");
		hql.append("inner join tb_acomp_referencia_item_ari ariA ");
		hql.append("on ariA.cod_iett = acao.cod_iett ");
		hql.append("inner join tb_acomp_referencia_aref arefA ");
		hql.append("on ariA.cod_aref = arefA.cod_aref ");
		hql.append("left join tb_acomp_relatorio_arel arelA ");
		hql.append("on ariA.cod_ari = arelA.cod_ari ");
		hql.append("left join tb_cor corA ");
		hql.append("on arelA.cod_cor = corA.cod_cor ");
		hql.append("left join  tb_usuario_usu usuA ");
		hql.append("on usuA.cod_usu = arelA.cod_usuultmanut_arel ");

		hql.append("right join tb_item_estrutura_iett prod ");
		hql.append("on acao.cod_iett_pai = prod.cod_iett ");

		hql.append("inner join tb_acomp_referencia_item_ari ariP ");
		hql.append("on ariP.cod_iett = prod.cod_iett ");
		hql.append("inner join tb_acomp_referencia_aref arefP ");
		hql.append("on ariP.cod_aref = arefP.cod_aref ");
		hql.append("left join tb_acomp_relatorio_arel arelP ");
		hql.append("on ariP.cod_ari = arelP.cod_ari ");
		hql.append("left join tb_cor corP ");
		hql.append("on arelP.cod_cor = corP.cod_cor ");
		hql.append("left join  tb_usuario_usu usuP ");
		hql.append("on usuP.cod_usu = arelP.cod_usuultmanut_arel ");

		hql.append("right join tb_item_estrutura_iett result on prod.cod_iett_pai = result.cod_iett ");

		hql.append("left join TB_ITEM_ESTRUTURA_NIVEL_IETTN prioritario ");
		hql.append("on result.cod_iett = prioritario.cod_iett ");

		hql.append("inner join tb_acomp_referencia_item_ari ariR ");
		hql.append("on ariR.cod_iett = result.cod_iett ");
		hql.append("inner join tb_acomp_referencia_aref aref ");
		hql.append("on ariR.cod_aref = aref.cod_aref ");
		hql.append("left join tb_acomp_relatorio_arel arel ");
		hql.append("on ariR.cod_ari = arel.cod_ari ");
		hql.append("left join tb_cor cor ");
		hql.append("on arel.cod_cor = cor.cod_cor ");
		hql.append("left join  tb_usuario_usu usu ");
		hql.append("on usu.cod_usu = arel.cod_usuultmanut_arel ");

		hql.append("inner join TB_ITEM_ESTRUT_USUARIO_IETTUS perm ");
		hql.append("on result.COD_IETT=perm.COD_IETT ");
		hql.append("inner join TB_TIPO_ACOMPANHAMENTO_TA ta ");
		hql.append("on aref.COD_TA=ta.COD_TA ");
		hql.append("inner join tb_tipoacomp_tipofuncacomp_sisatributo_tatpfasatb permTAGRU ");
		hql.append("on ta.COD_TA=permTAGRU.cod_ta, ");
		hql.append("TB_TIPO_FUNC_ACOMP_TPFA tpfa ");
		hql.append("where ");
		hql.append("permTAGRU.cod_tpfa=tpfa.COD_TPFA ");
		hql.append("and ");

		if (mes == null && ano == null) {
			hql.append(" (aref.data_ult_manut_aref = ( ");
			hql.append("select max(aref.data_ult_manut_aref) ");
			hql.append("from tb_acomp_referencia_aref aref ");
			hql.append("inner join tb_acomp_referencia_item_ari ari ");
			hql.append("on ari.cod_aref = aref.cod_aref ");
			hql.append("where ari.cod_iett = result.cod_iett) ");

			hql.append("or arefP.data_ult_manut_aref = ( ");
			hql.append("select max(arefP.data_ult_manut_aref) ");
			hql.append("from tb_acomp_referencia_aref arefP ");
			hql.append("inner join tb_acomp_referencia_item_ari ariP ");
			hql.append("on ariP.cod_aref = arefP.cod_aref ");
			hql.append("where ariP.cod_iett = prod.cod_iett) ");

			hql.append("or arefA.data_ult_manut_aref = ( ");
			hql.append("select max(arefA.data_ult_manut_aref) ");
			hql.append("from tb_acomp_referencia_aref arefA ");
			hql.append("inner join tb_acomp_referencia_item_ari ariA ");
			hql.append("on ariA.cod_aref = arefA.cod_aref ");
			hql.append("where ariA.cod_iett = acao.cod_iett) ) ");
			hql.append("AND ");
		} else {
			hql.append(" ");
		}

		// hql.append(" ((prod.ind_ativo_iett = 'S' OR prod.cod_iett is NULL) AND 1=1)  ");
		// hql.append("AND ((acao.ind_ativo_iett = 'S' OR acao.cod_iett is NULL) AND 1=1) ");
		hql.append(" result.ind_ativo_iett = 'S' ");
		hql.append("and arel.cod_tpfa in (3,6,7) ");
		hql.append("and result.cod_iett=:codResultado ");

		if (mes != null && ano != null) {
			hql.append("and aref.mes_aref = :mes ");
			hql.append("and aref.ano_aref = :ano ");
			// hql.append("and ((arefP.mes_aref <= :mes ");
			// hql.append("and arefP.ano_aref <= :ano) ");
			// hql.append("or (arefA.mes_aref <= :mes ");
			// hql.append("and arefA.ano_aref <= :ano)) ");
		}

		hql.append("and (perm.IND_EMITE_POS_IETTUS='S' ");
		hql.append("or perm.IND_LEITURA_PARECER_IETTUS='S' ");
		hql.append("or perm.IND_INF_ANDAMENTO_IETTUS='S') ");
		hql.append("and (perm.COD_USU=:codUsu ");
		hql.append("or perm.COD_ATB in (:gruposUsuarios)) ");
		hql.append("and permTAGRU.ind_leitura_parecer='S' ");
		hql.append("and tpfa.IND_EMITE_POSICAO_TPFA='S' ");
		hql.append("and (permTAGRU.cod_satb in (:gruposUsuarios)) ");

		hql.append("order by result.sigla_iett,prod.sigla_iett,acao.sigla_iett," + "mes desc, mesP desc, mesA desc ");

		// hql.append(",aref.data_ult_manut_aref desc");

		SQLQuery q = this.session.createSQLQuery(hql.toString());

		q.setLong("codResultado", codigoResultado);

		if (mes != null && ano != null) {
			q.setString("mes", mes);
			q.setString("ano", ano);
		}

		q.setParameter("codUsu", codigoUsuario);
		q.setParameterList("gruposUsuarios", gruposUsuarios);

		q.addScalar("codigo", Hibernate.LONG);
		q.addScalar("siglaResultado", Hibernate.STRING);
		q.addScalar("siglaProduto", Hibernate.STRING);
		q.addScalar("codAri", Hibernate.LONG);
		q.addScalar("codAriP", Hibernate.LONG);
		q.addScalar("usu", Hibernate.STRING);
		q.addScalar("codResponsavel", Hibernate.LONG);
		q.addScalar("usuP", Hibernate.STRING);
		q.addScalar("codRespProduto", Hibernate.LONG);
		q.addScalar("codCor", Hibernate.LONG);
		q.addScalar("nomeCor", Hibernate.STRING);
		q.addScalar("codCorP", Hibernate.LONG);
		q.addScalar("nomeCorP", Hibernate.STRING);
		q.addScalar("codAref", Hibernate.LONG);
		q.addScalar("codArefP", Hibernate.LONG);
		q.addScalar("mes", Hibernate.STRING);
		q.addScalar("ano", Hibernate.STRING);
		q.addScalar("mesP", Hibernate.STRING);
		q.addScalar("anoP", Hibernate.STRING);
		q.addScalar("parecer", Hibernate.STRING);
		q.addScalar("parecerP", Hibernate.STRING);
		q.addScalar("codResul", Hibernate.LONG);
		q.addScalar("nomeResul", Hibernate.STRING);
		q.addScalar("codProd", Hibernate.LONG);
		q.addScalar("produto", Hibernate.STRING);
		q.addScalar("dataInicio", Hibernate.DATE);
		q.addScalar("dataFim", Hibernate.DATE);

		q.addScalar("codAcao", Hibernate.LONG);
		q.addScalar("acao", Hibernate.STRING);
		q.addScalar("siglaAcao", Hibernate.STRING);
		q.addScalar("dataInicioAcao", Hibernate.DATE);
		q.addScalar("dataFimAcao", Hibernate.DATE);
		q.addScalar("codAriA", Hibernate.LONG);
		q.addScalar("codRespAcao", Hibernate.LONG);
		q.addScalar("responsavelAcao", Hibernate.STRING);
		q.addScalar("codCorA", Hibernate.LONG);
		q.addScalar("nomeCorA", Hibernate.STRING);
		q.addScalar("codArefA", Hibernate.LONG);
		q.addScalar("mesA", Hibernate.STRING);
		q.addScalar("anoA", Hibernate.STRING);
		q.addScalar("parecerA", Hibernate.STRING);

		q.addScalar("atencao", Hibernate.BOOLEAN);
		q.addScalar("prioritario", Hibernate.STRING);
		q.addScalar("ativoProd", Hibernate.STRING);
		q.addScalar("ativoAcao", Hibernate.STRING);

		q.setResultTransformer(Transformers.aliasToBean(Resultado.class));

		List<Resultado> lista = q.list();

		lista = montaResultado(lista);

		if (lista.size() == 0) {
			return new Resultado();
		}

		Resultado res = lista.get(0);
		// Comentarios
		res.setComentarios(daoIettAcao.loadComentarioWS(res.getCodigo(), null, null));
		// FIM Comentarios

		// Periodos Acompanhamentos
		List<PeriodoAcompanhamento> periodos = recuperaPeriodosAcompanhamento(res.getCodigo(), null, null);
		// res.getPeriodoAcompanhamento().get(0).setCiclo(periodos.size());
		int i = 1;
		PeriodoAcompanhamento pa = new PeriodoAcompanhamento();
		for (PeriodoAcompanhamento periodoAcompanhamento : periodos) {
			if (periodoAcompanhamento.getCodigo() != res.getPeriodoAcompanhamento().get(0).getCodigo()) {
				periodoAcompanhamento.setCiclo(i);
			} else {
				res.getPeriodoAcompanhamento().get(0).setCiclo(i);
				pa = periodoAcompanhamento;
			}
			i++;
		}
		periodos.remove(pa);
		res.getPeriodoAcompanhamento().addAll(periodos);
		// FIM Periodos Acompanhamentos
		return lista.get(0);
	}

	/**
	 * @deprecated Use {@link #recuperaPeriodosAcompanhamento(Long,Long, List)}
	 *             instead
	 */
	public List<PeriodoAcompanhamento> recuperaPeriodosAcompanhamento(Long codResultado) {
		return recuperaPeriodosAcompanhamento(codResultado, null, null);
	}

	/**
	 * @deprecated Use {@link #recuperaPeriodosAcompanhamento(Long,Long,List)}
	 *             instead
	 */
	public List<PeriodoAcompanhamento> recuperaPeriodosAcompanhamento(Long codResultado, Long codAref) {
		return recuperaPeriodosAcompanhamento(codResultado, codAref, null);
	}

	public List<PeriodoAcompanhamento> recuperaPeriodosAcompanhamento(Long codResultado, Long codAref, List<Integer> resultadoStatus) {
		String hql = "SELECT new ecar.pojo.acompanhamentoEstrategico.PeriodoAcompanhamento(" + "ari.codAri," + "arel.descricaoArel, " + "aref.mesAref," + "aref.anoAref, " + "cor.codCor, "
				+ "cor.significadoCor, " + "arel.codArel " + ") " + "FROM AcompReferenciaItemAri ari " + "join ari.acompReferenciaAref aref  " + "left join ari.acompRelatorioArels arel "
				+ "left join arel.cor cor " + "where ari.itemEstruturaIett.indAtivoIett = 'S' " + "AND ari.itemEstruturaIett.codIett = :codIett " + "AND cor.codCor is not null ";

		if (resultadoStatus != null && resultadoStatus.size() > 0) {
			hql += "AND ((ari.itemEstruturaIett.nivelIett = 3 AND cor.codCor in(:status)) OR ari.itemEstruturaIett.nivelIett > 3) ";
		}

		if (codAref != null && !codAref.equals(0L)) {
			hql += "AND aref.codAref <= :codAref ";
		}

		hql += "ORDER BY aref.dataUltManutAref desc ";

		Query q = getSession().createQuery(hql);

		q.setLong("codIett", codResultado);

		if (resultadoStatus != null && resultadoStatus.size() > 0) {

			List<Long> longStatus = new ArrayList<Long>();
			for (Integer i : resultadoStatus) {
				longStatus.add(i.longValue());
			}

			q.setParameterList("status", longStatus);
		}

		if (codAref != null && !codAref.equals(0L)) {
			q.setLong("codAref", codAref);
		}
		q.setMaxResults(3);

		List<PeriodoAcompanhamento> periodos = q.list();

		return periodos;
	}

	public List<Indicador> getListIndicador(Long codResultado, boolean somenteREM) throws ECARException {
		String hql = "SELECT new ecar.pojo.acompanhamentoEstrategico.Indicador(" + "ind.codIettir," + "ind.nomeIettir," + "ind.metodoCalculo" + ") " + "FROM ItemEstrtIndResulIettr ind "
				+ "WHERE ind.indAtivoIettr = 'S' " + "AND ind.itemEstruturaIett.indAtivoIett = 'S' " + "AND ind.itemEstruturaIett.codIett = :codIett ";
		if (somenteREM) {
			hql += "AND ind.sisAtributoSatb.codSatb = 257";
		}

		Query q = getSession().createQuery(hql);

		q.setLong("codIett", codResultado);

		List<Indicador> indicadores = q.list();

		Exercicio exe2012 = new Exercicio(exercicioDao.getExercicio("05", "2012"));
		Exercicio exe2013 = new Exercicio(exercicioDao.getExercicio("05", "2013"));
		Exercicio exe2014 = new Exercicio(exercicioDao.getExercicio("05", "2014"));
		Exercicio exe2015 = new Exercicio(exercicioDao.getExercicio("05", "2015"));

		for (Indicador indicador : indicadores) {
			ItemEstrtIndResulIettr indEcar = (ItemEstrtIndResulIettr) indResulIettrDao.localizar(ItemEstrtIndResulIettr.class, indicador.getCodigo().longValue());
			IndicadorResultado wrapperIndicador = new IndicadorResultado(indEcar);

			indicador.setMeta2012(Util.formatarDecimal(wrapperIndicador.getPrevistoNoExercicio(exe2012)));
			indicador.setMeta2013(Util.formatarDecimal(wrapperIndicador.getPrevistoNoExercicio(exe2013)));
			indicador.setMeta2014(Util.formatarDecimal(wrapperIndicador.getPrevistoNoExercicio(exe2014)));
			indicador.setMeta2015(Util.formatarDecimal(wrapperIndicador.getPrevistoNoExercicio(exe2015)));
			indicador.setCosolidadoAnual(Util.formatarDecimal(wrapperIndicador.getRealizadoNoExercio(exe2013)));
		}

		return indicadores;
	}

	public List<Indicador> getIndicadores(Long codResultado) throws ECARException {
		String hql = "SELECT new ecar.pojo.acompanhamentoEstrategico.Indicador(" + "ind.codIettir," + "ind.nomeIettir," + "ind.metodoCalculo" + ") " + "FROM ItemEstrtIndResulIettr ind "
				+ "WHERE ind.indAtivoIettr = 'S' " + "AND ind.itemEstruturaIett.indAtivoIett = 'S' " + "AND ind.itemEstruturaIett.codIett = :codIett ";

		Query q = getSession().createQuery(hql);

		q.setLong("codIett", codResultado);

		List<Indicador> indicadores = q.list();

		Exercicio exe2012 = new Exercicio(exercicioDao.getExercicio("05", "2012"));
		Exercicio exe2013 = new Exercicio(exercicioDao.getExercicio("05", "2013"));
		Exercicio exe2014 = new Exercicio(exercicioDao.getExercicio("05", "2014"));
		Exercicio exe2011 = new Exercicio(exercicioDao.getExercicio("05", "2011"));

		for (Indicador indicador : indicadores) {
			ItemEstrtIndResulIettr indEcar = (ItemEstrtIndResulIettr) indResulIettrDao.localizar(ItemEstrtIndResulIettr.class, indicador.getCodigo().longValue());
			IndicadorResultado wrapperIndicador = new IndicadorResultado(indEcar);

			indicador.setMeta2012(Util.formatarDecimal(wrapperIndicador.getPrevistoNoExercicio(exe2012)));
			indicador.setMeta2013(Util.formatarDecimal(wrapperIndicador.getPrevistoNoExercicio(exe2013)));
			indicador.setMeta2014(Util.formatarDecimal(wrapperIndicador.getPrevistoNoExercicio(exe2014)));
			indicador.setMeta2015(Util.formatarDecimal(wrapperIndicador.getPrevistoNoExercicio(exe2011)));
			indicador.setCosolidadoAnual(Util.formatarDecimal(wrapperIndicador.getRealizadoNoExercio(exe2012)));
			indicador.setValorRealizado2012(Util.formatarDecimal(wrapperIndicador.getRealizadoNoExercio(exe2012)));
			indicador.setValorRealizado2013(Util.formatarDecimal(wrapperIndicador.getRealizadoNoExercio(exe2013)));
			indicador.setValorRealizado2014(Util.formatarDecimal(wrapperIndicador.getRealizadoNoExercio(exe2014)));
			indicador.setValorRealizado2015(Util.formatarDecimal(wrapperIndicador.getRealizadoNoExercio(exe2011)));
		}

		return indicadores;
	}

	private List<Resultado> montaResultado(List<Resultado> lista) throws ECARException {

		Map<String, Resultado> mapaResultado = new TreeMap<String, Resultado>();
		Map<String, Produto> mapaProduto = new TreeMap<String, Produto>();
		Map<Long, Acao> mapaAcao = new TreeMap<Long, Acao>();

		List<AcompReferenciaAref> listaAref = getAcompReferenciaArefList();

		montaMapas(lista, mapaResultado, mapaProduto, mapaAcao, listaAref);

		List<Produto> listaProduto = populaAcao(mapaProduto, mapaAcao, true);
		List<Resultado> listaResultado = populaProduto(mapaResultado, listaProduto);

		return listaResultado;
	}

	private void montaMapas(List<Resultado> lista, Map<String, Resultado> mapaResultado, Map<String, Produto> mapaProduto, Map<Long, Acao> mapaAcao, List<AcompReferenciaAref> listaAref)
			throws ECARException {

		for (Resultado res : lista) {
			if (!mapaResultado.containsKey(res.getCodResul().toString())) {
				Resultado rNew = new Resultado(res.getCodResul(), res.getNomeResul(), res.getCodEstrat(), res.getCodAri(), montaParecer(res.getParecer(), res.getCodResul(), listaAref), res.getMes(),
						res.getAno(), res.getCodCor(), res.getNomeCor(), res.getPrioritario(), res.getSiglaResultado(), res.getUsu(), res.getCodResponsavel(), null, null, res.isAtencao(), null);
				mapaResultado.put(res.getCodResul().toString(), rNew);
				List indicadores = getListIndicador(res.getCodResul(), false);
				if (indicadores != null && indicadores.size() > 0) {
					mapaResultado.get(res.getCodResul().toString()).getIndicador().addAll(indicadores);
				}
				for (Responsavel responsavel : buscarCoResponsavel(res.getCodResul())) {
					if (responsavel.getCodTipo().equals(2)) {
						mapaResultado.get(res.getCodResul().toString()).getArticulacao().add(responsavel);
					} else {
						mapaResultado.get(res.getCodResul().toString()).getCoResponsavel().add(responsavel);
					}
				}
			}

			if (res.getCodProd() != null && !mapaProduto.containsKey(res.getCodProd().toString()) && (res.getAtivoProd() != null && res.getAtivoProd().equals("S"))) {
				if (res.getUsuP() != null) {
					mapaProduto.put(
							res.getCodProd().toString(),
							new Produto(res.getCodProd(), res.getProduto(), res.getCodResul(), res.getSiglaProduto(), res.getUsuP(), res.getCodRespProduto(), res.getCodAriP(), res.getParecerP(), res
									.getMesP(), res.getAnoP(), res.getCodCorP(), res.getNomeCorP(), res.getDataInicio(), res.getDataFim(), null));
				} else {
					mapaProduto.put(
							res.getCodProd().toString(),
							new Produto(res.getCodProd(), res.getProduto(), res.getCodResul(), res.getSiglaProduto(), null, null, res.getCodAriP(), null, res.getMesP(), res.getAnoP(), null, null, res
									.getDataInicio(), res.getDataFim(), null));
				}

			}

			if (res.getCodAcao() != null && !mapaAcao.containsKey(res.getCodAcao()) && (res.getAtivoAcao() != null && res.getAtivoAcao().equals("S"))) {
				if (res.getResponsavelAcao() != null) {
					mapaAcao.put(res.getCodAcao(), new Acao(res.getCodAcao(), res.getAcao(), res.getSiglaAcao(), res.getResponsavelAcao(), res.getCodRespAcao(), res.getCodAriA(), res.getParecerA(),
							res.getMesA(), res.getAnoA(), res.getCodCorA(), res.getNomeCorA(), res.getDataInicioAcao(), res.getDataFimAcao(), res.getCodProd()));
				} else {
					mapaAcao.put(
							res.getCodAcao(),
							new Acao(res.getCodAcao(), res.getAcao(), res.getSiglaAcao(), null, null, res.getCodAriA(), null, res.getMesA(), res.getAnoA(), null, null, res.getDataInicioAcao(), res
									.getDataFimAcao(), res.getCodProd()));
				}

			}
		}
	}

	public Produto listarProduto(Integer codigoProduto, String uiid) {

		this.uuid = uiid;

		StringBuilder hql = new StringBuilder();
		hql.append("select distinct prod.cod_iett as codigo, " + "prod.sigla_iett as siglaProduto, " + "acao.sigla_iett as siglaAcao, " + "ariP.cod_ari as codAri, " + "ariA.cod_ari as codAriA, "
				+ "usu.nome_usu as usu, " + "usu.cod_usu as codResponsavel, " + "usuA.nome_usu as responsavelAcao, " + "usuA.cod_usu as codRespAcao, " +

				"CASE WHEN cor.cod_cor ISNULL  THEN (SELECT " + "cor.cod_cor  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join " + "tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_cor cor "
				+ "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codigoProduto " + "order by " + "aref.data_ult_manut_aref LIMIT 1 ) " + "ELSE (cor.cod_cor) " + "END as codCor, " +

				"CASE WHEN cor.nome_cor ISNULL  THEN (SELECT " + "cor.significado_cor  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join " + "tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_cor cor "
				+ "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codigoProduto " + "order by " + "aref.data_ult_manut_aref LIMIT 1 ) " + "ELSE (cor.significado_cor) "
				+ "END as nomeCor, ");
		hql.append("CASE WHEN corA.cod_cor ISNULL  THEN (SELECT " + "cor.cod_cor  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join " + "tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_cor cor "
				+ "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codigoProduto " + "order by " + "aref.data_ult_manut_aref LIMIT 1 ) " + "ELSE (corA.cod_cor) " + "END as codCorA, " +

				"CASE WHEN corA.nome_cor ISNULL  THEN (SELECT " + "cor.significado_cor  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join " + "tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_cor cor "
				+ "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codigoProduto " + "order by " + "aref.data_ult_manut_aref LIMIT 1 ) " + "ELSE (corA.significado_cor) "
				+ "END as nomeCorA, ");
		hql.append("aref.cod_aref as codAref, " + "arefA.cod_aref as codArefA, " +

		"CASE WHEN aref.mes_aref ISNULL  THEN (" + "SELECT aref.mes_aref  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_referencia_aref aref "
				+ "on ari.cod_aref = aref.cod_aref " + "WHERE ari.cod_iett = :codigoProduto " + "order by aref.data_ult_manut_aref " + "LIMIT 1 " + ") " + "ELSE (aref.mes_aref) " + "END as mes, " +

				"CASE WHEN aref.ano_aref ISNULL  THEN (" + "SELECT aref.ano_aref  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_referencia_aref aref "
				+ "on ari.cod_aref = aref.cod_aref " + "WHERE ari.cod_iett = :codigoProduto " + "order by aref.data_ult_manut_aref " + "LIMIT 1 " + ") " + "ELSE (aref.ano_aref) " + "END as ano, ");
		hql.append("CASE WHEN arefA.mes_aref ISNULL  THEN (" + "SELECT aref.mes_aref  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_referencia_aref aref "
				+ "on ari.cod_aref = aref.cod_aref " + "WHERE ari.cod_iett = :codigoProduto " + "order by aref.data_ult_manut_aref " + "LIMIT 1 " + ") " + "ELSE (arefA.mes_aref) " + "END as mesA, " +

				"CASE WHEN arefA.ano_aref ISNULL  THEN (" + "SELECT aref.ano_aref  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_referencia_aref aref "
				+ "on ari.cod_aref = aref.cod_aref " + "WHERE ari.cod_iett = :codigoProduto " + "order by aref.data_ult_manut_aref " + "LIMIT 1 " + ") " + "ELSE (arefA.ano_aref) " + "END as anoA, ");
		hql.append("CASE WHEN arel.descricao_arel ISNULL  THEN (" + "SELECT arel.descricao_arel  " + "from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "WHERE " + "ari.cod_iett = :codigoProduto " + "order by " + "arel.data_ult_manut_arel LIMIT 1  ) " + "ELSE (arel.descricao_arel) "
				+ "END as parecer, ");
		hql.append("CASE WHEN arelA.descricao_arel ISNULL  THEN (" + "SELECT arel.descricao_arel  " + "from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "WHERE " + "ari.cod_iett = :codigoProduto " + "order by " + "arel.data_ult_manut_arel LIMIT 1  ) " + "ELSE (arelA.descricao_arel) "
				+ "END as parecerA, ");
		hql.append("prod.nome_iett as nome, " + "prod.data_inicio_iett as dataInicio, " + "prod.data_termino_iett as dataFim, ");
		hql.append("acao.cod_iett codAcao, ");
		hql.append("acao.nome_iett as acao, " + "acao.data_inicio_iett as dataInicioAcao, " + "acao.data_termino_iett as dataFimAcao, ");

		hql.append("atividade.cod_iett as codAtividade, " + "atividade.nome_iett as atividade, " + "atividade.sigla_iett as siglaAtividade, " + "atividade.data_inicio_iett as dataInicioAtividade, "
				+ "atividade.data_termino_iett as dataFimAtividade, " + "ariAt.cod_ari as codAriAt, " + "usuAt.cod_usu as codRespAtividade, " + "usuAt.nome_usu as responsavelAtividade, " +

				"CASE WHEN corAt.cod_cor ISNULL  THEN (SELECT " + "cor.cod_cor  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join " + "tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_cor cor "
				+ "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codigoProduto " + "order by " + "aref.data_ult_manut_aref LIMIT 1 ) " + "ELSE (corAt.cod_cor) " + "END as codCorAt, " +

				"CASE WHEN corAt.nome_cor ISNULL  THEN (SELECT " + "cor.significado_cor  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "inner join " + "tb_acomp_referencia_aref aref " + "on ari.cod_aref = aref.cod_aref " + "inner join tb_cor cor "
				+ "on arel.cod_cor = cor.cod_cor " + "WHERE " + "ari.cod_iett = :codigoProduto " + "order by " + "aref.data_ult_manut_aref LIMIT 1 ) " + "ELSE (corAt.significado_cor) "
				+ "END as nomeCorAt, " +

				"arefAt.cod_aref as codArefAt, " +

				"CASE WHEN arefAt.mes_aref ISNULL  THEN (" + "SELECT aref.mes_aref  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_referencia_aref aref "
				+ "on ari.cod_aref = aref.cod_aref " + "WHERE ari.cod_iett = :codigoProduto " + "order by aref.data_ult_manut_aref " + "LIMIT 1 " + ") " + "ELSE (arefAt.mes_aref) " + "END as mesAt, "
				+

				"CASE WHEN arefAt.ano_aref ISNULL  THEN (" + "SELECT aref.ano_aref  from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_referencia_aref aref "
				+ "on ari.cod_aref = aref.cod_aref " + "WHERE ari.cod_iett = :codigoProduto " + "order by aref.data_ult_manut_aref " + "LIMIT 1 " + ") " + "ELSE (arefAt.ano_aref) " + "END as anoAt, "
				+

				"CASE WHEN arelAt.descricao_arel ISNULL  THEN (" + "SELECT arel.descricao_arel  " + "from " + "tb_acomp_referencia_item_ari ari " + "inner join " + "tb_acomp_relatorio_arel arel "
				+ "on ari.cod_ari = arel.cod_ari " + "WHERE " + "ari.cod_iett = :codigoProduto " + "order by " + "arel.data_ult_manut_arel LIMIT 1  ) " + "ELSE (arelAt.descricao_arel) "
				+ "END as parecerAt , " + "acao.ind_ativo_iett as ativoAcao, " + "atividade.ind_ativo_iett as ativoAtiv ");

		hql.append("from tb_item_estrutura_iett atividade ");
		hql.append("inner join tb_acomp_referencia_item_ari ariAt ");
		hql.append("on ariAt.cod_iett = atividade.cod_iett ");
		hql.append("inner join tb_acomp_referencia_aref arefAt ");
		hql.append("on ariAt.cod_aref = arefAt.cod_aref ");
		hql.append("left join tb_acomp_relatorio_arel arelAt ");
		hql.append("on ariAt.cod_ari = arelAt.cod_ari ");
		hql.append("left join tb_cor corAt ");
		hql.append("on arelAt.cod_cor = corAt.cod_cor ");
		hql.append("left join  tb_usuario_usu usuAt ");
		hql.append("on usuAt.cod_usu = arelAt.cod_usuultmanut_arel ");

		hql.append("right join tb_item_estrutura_iett acao ");
		hql.append("on atividade.cod_iett_pai = acao.cod_iett ");

		hql.append("inner join tb_acomp_referencia_item_ari ariA ");
		hql.append("on ariA.cod_iett = acao.cod_iett ");

		hql.append("inner join tb_acomp_referencia_aref arefA ");
		hql.append("on ariA.cod_aref = arefA.cod_aref ");
		hql.append("left join tb_acomp_relatorio_arel arelA ");
		hql.append("on ariA.cod_ari = arelA.cod_ari ");
		hql.append("left join tb_cor corA ");
		hql.append("on arelA.cod_cor = corA.cod_cor ");
		hql.append("left join  tb_usuario_usu usuA ");
		hql.append("on usuA.cod_usu = arelA.cod_usuultmanut_arel ");

		hql.append("right join tb_item_estrutura_iett prod on acao.cod_iett_pai = prod.cod_iett ");

		// hql.append("left join TB_ITEM_ESTRUTURA_NIVEL_IETTN prioritario ");
		// hql.append("on result.cod_iett = prioritario.cod_iett ");

		hql.append("inner join tb_acomp_referencia_item_ari ariP ");
		hql.append("on ariP.cod_iett = prod.cod_iett ");
		hql.append("inner join tb_acomp_referencia_aref aref ");
		hql.append("on ariP.cod_aref = aref.cod_aref ");
		hql.append("left join tb_acomp_relatorio_arel arel ");
		hql.append("on ariP.cod_ari = arel.cod_ari ");
		hql.append("left join tb_cor cor ");
		hql.append("on arel.cod_cor = cor.cod_cor ");
		hql.append("left join  tb_usuario_usu usu ");
		hql.append("on usu.cod_usu = arel.cod_usuultmanut_arel ");

		// hql.append("inner join TB_ITEM_ESTRUT_USUARIO_IETTUS perm ");
		// hql.append("on result.COD_IETT=perm.COD_IETT ");
		// hql.append("inner join TB_TIPO_ACOMPANHAMENTO_TA ta ");
		// hql.append("on aref.COD_TA=ta.COD_TA ");
		// hql.append("inner join tb_tipoacomp_tipofuncacomp_sisatributo_tatpfasatb permTAGRU ");
		// hql.append("on ta.COD_TA=permTAGRU.cod_ta, ");
		// hql.append("TB_TIPO_FUNC_ACOMP_TPFA tpfa ");

		hql.append("where (aref.data_ult_manut_aref = ( ");
		hql.append("select max(aref.data_ult_manut_aref) ");
		hql.append("from tb_acomp_referencia_aref aref ");
		hql.append("inner join tb_acomp_referencia_item_ari ari ");
		hql.append("on ari.cod_aref = aref.cod_aref ");
		hql.append("where ari.cod_iett = prod.cod_iett) ");

		hql.append("or arefA.data_ult_manut_aref = ( ");
		hql.append("select max(arefA.data_ult_manut_aref) ");
		hql.append("from tb_acomp_referencia_aref arefA ");
		hql.append("inner join tb_acomp_referencia_item_ari ariA ");
		hql.append("on ariA.cod_aref = arefA.cod_aref ");
		hql.append("where ariA.cod_iett = acao.cod_iett) ");

		hql.append("or arefAt.data_ult_manut_aref = ( ");
		hql.append("select max(arefAt.data_ult_manut_aref) ");
		hql.append("from tb_acomp_referencia_aref arefAt ");
		hql.append("inner join tb_acomp_referencia_item_ari ariAt ");
		hql.append("on ariAt.cod_aref = arefAt.cod_aref ");
		hql.append("where ariAt.cod_iett = atividade.cod_iett)) ");

		// hql.append("AND ((acao.ind_ativo_iett = 'S' OR acao.cod_iett is NULL) AND 1=1)  ");
		// hql.append("AND ((atividade.ind_ativo_iett = 'S' OR atividade.cod_iett is NULL) AND 1=1) ");
		hql.append("and prod.ind_ativo_iett = 'S' ");
		hql.append("and arel.cod_tpfa in (6,7,14) ");
		hql.append("and prod.cod_iett=:codigoProduto ");
		hql.append("order by prod.sigla_iett,acao.sigla_iett,atividade.sigla_iett," + "mes desc, mesA desc, mesAt desc ");

		SQLQuery q = this.session.createSQLQuery(hql.toString());

		q.setLong("codigoProduto", codigoProduto);

		q.addScalar("codigo", Hibernate.LONG);
		q.addScalar("siglaProduto", Hibernate.STRING);
		q.addScalar("codAri", Hibernate.LONG);
		q.addScalar("usu", Hibernate.STRING);
		q.addScalar("codResponsavel", Hibernate.LONG);
		q.addScalar("codCor", Hibernate.LONG);
		q.addScalar("nomeCor", Hibernate.STRING);
		q.addScalar("codAref", Hibernate.LONG);
		q.addScalar("mes", Hibernate.STRING);
		q.addScalar("ano", Hibernate.STRING);
		q.addScalar("parecer", Hibernate.STRING);
		q.addScalar("nome", Hibernate.STRING);
		q.addScalar("dataInicio", Hibernate.DATE);
		q.addScalar("dataFim", Hibernate.DATE);

		q.addScalar("codAcao", Hibernate.LONG);
		q.addScalar("acao", Hibernate.STRING);
		q.addScalar("siglaAcao", Hibernate.STRING);
		q.addScalar("dataInicioAcao", Hibernate.DATE);
		q.addScalar("dataFimAcao", Hibernate.DATE);
		q.addScalar("codAriA", Hibernate.LONG);
		q.addScalar("codRespAcao", Hibernate.LONG);
		q.addScalar("responsavelAcao", Hibernate.STRING);
		q.addScalar("codCorA", Hibernate.LONG);
		q.addScalar("nomeCorA", Hibernate.STRING);
		q.addScalar("codArefA", Hibernate.LONG);
		q.addScalar("mesA", Hibernate.STRING);
		q.addScalar("anoA", Hibernate.STRING);
		q.addScalar("parecerA", Hibernate.STRING);

		q.addScalar("codAtividade", Hibernate.LONG);
		q.addScalar("atividade", Hibernate.STRING);
		q.addScalar("siglaAtividade", Hibernate.STRING);
		q.addScalar("dataInicioAtividade", Hibernate.DATE);
		q.addScalar("dataFimAtividade", Hibernate.DATE);
		q.addScalar("codAriAt", Hibernate.LONG);
		q.addScalar("codRespAtividade", Hibernate.LONG);
		q.addScalar("responsavelAtividade", Hibernate.STRING);
		q.addScalar("codCorAt", Hibernate.LONG);
		q.addScalar("nomeCorAt", Hibernate.STRING);
		q.addScalar("codArefAt", Hibernate.LONG);
		q.addScalar("mesAt", Hibernate.STRING);
		q.addScalar("anoAt", Hibernate.STRING);
		q.addScalar("parecerAt", Hibernate.STRING);
		q.addScalar("ativoAcao", Hibernate.STRING);
		q.addScalar("ativoAtiv", Hibernate.STRING);

		q.setResultTransformer(Transformers.aliasToBean(Produto.class));

		List<Produto> lista = q.list();

		lista = montaProduto(lista);

		return lista.get(0);
	}

	private List<Produto> montaProduto(List<Produto> lista) {

		Map<String, Produto> mapaProduto = new TreeMap<String, Produto>();
		Map<Long, Acao> mapaAcao = new TreeMap<Long, Acao>();
		Map<Long, Atividade> mapaAtividade = new TreeMap<Long, Atividade>();

		List<AcompReferenciaAref> listaAref = getAcompReferenciaArefList();

		montaMapasProduto(lista, mapaProduto, mapaAcao, mapaAtividade, listaAref);

		// populaAtividade(mapaAcao, mapaAtividade);
		List<Produto> listaProduto = populaAcao(mapaProduto, mapaAcao, true);

		return listaProduto;
	}

	private void montaMapasProduto(List<Produto> lista, Map<String, Produto> mapaProduto, Map<Long, Acao> mapaAcao, Map<Long, Atividade> mapaAtividade, List<AcompReferenciaAref> listaAref) {
		for (Produto prod : lista) {
			// if(!mapaResultado.containsKey(prod.getCodResul())) {
			// mapaResultado.put(prod.getCodResul(), new Resultado(
			// prod.getCodResul(), prod.getNomeResul(), prod.getCodEstrat(),
			// prod.getCodAri(),
			// prod.getParecer(), prod.getMes(), prod.getAno(),
			// prod.getCodCor(), prod.getNomeCor(),
			// null, prod.getSiglaResultado(), prod.getUsu(),
			// prod.getCodResponsavel()));
			// List indicadores = getListIndicador(prod.getCodResul());
			// if(indicadores != null && indicadores.size() > 0){
			// mapaResultado.get(prod.getCodResul()).getIndicador().addAll(indicadores);
			// }
			// for (Responsavel responsavel :
			// buscarCoResponsavel(prod.getCodResul())) {
			// mapaResultado.get(prod.getCodResul()).getCoResponsavel().add(responsavel);
			// }
			// }

			if (!mapaProduto.containsKey(prod.getCodigo().toString())) {
				if (prod.getUsu() != null) {
					mapaProduto.put(prod.getCodigo().toString(), new Produto(prod.getCodigo(), prod.getNome(), 0L, prod.getSiglaProduto(), prod.getUsu(), prod.getCodResponsavel(), prod.getCodAri(),
							montaParecer(prod.getParecer(), prod.getCodigo(), listaAref), prod.getMes(), prod.getAno(), prod.getCodCor(), prod.getNomeCor(), prod.getDataInicio(), prod.getDataFim(),
							null));
				} else {
					mapaProduto.put(prod.getCodigo().toString(), new Produto(prod.getCodigo(), prod.getNome(), 0L, prod.getSiglaProduto(), null, null, prod.getCodAri(), null, null, null, null, null,
							prod.getDataInicio(), prod.getDataFim(), null));
				}
				for (Responsavel responsavel : buscarCoResponsavel(prod.getCodigo())) {
					mapaProduto.get(prod.getCodigo().toString()).getCoResponsavel().add(responsavel);
				}

			}

			if (prod.getCodAcao() != null && !mapaAcao.containsKey(prod.getCodAcao()) && (prod.getAtivoAcao() != null && prod.getAtivoAcao().equals("S"))) {
				if (prod.getResponsavelAcao() != null) {
					mapaAcao.put(prod.getCodAcao(),
							new Acao(prod.getCodAcao(), prod.getAcao(), prod.getSiglaAcao(), prod.getResponsavelAcao(), prod.getCodRespAcao(), prod.getCodAriA(), prod.getParecerA(), prod.getMesA(),
									prod.getAnoA(), prod.getCodCorA(), prod.getNomeCorA(), prod.getDataInicioAcao(), prod.getDataFimAcao(), prod.getCodigo()));
				} else {
					mapaAcao.put(prod.getCodAcao(), new Acao(prod.getCodAcao(), prod.getAcao(), prod.getSiglaAcao(), null, null, prod.getCodAriA(), null, prod.getMesA(), prod.getAnoA(), null, null,
							prod.getDataInicioAcao(), prod.getDataFimAcao(), prod.getCodigo()));
				}

			}
		}
	}

	public Collection<Responsavel> listarSecretarias() {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct iette.cod_ent as codigo, ent.nome_ent as nome ");
		sql.append("from TB_ITEM_ESTRUT_ENTIDADE_IETTE iette ");
		sql.append("inner join TB_ENTIDADE_ENT ent ");
		sql.append("on ent.cod_ent = iette.cod_ent ");
		sql.append("inner join tb_item_estrutura_iett iett ");
		sql.append("on iett.cod_iett = iette.cod_iett ");
		sql.append("where iett.ind_ativo_iett = 'S' limit 6 ");

		SQLQuery q = this.session.createSQLQuery(sql.toString());

		q.addScalar("codigo", Hibernate.LONG);
		q.addScalar("nome", Hibernate.STRING);

		q.setResultTransformer(Transformers.aliasToBean(Responsavel.class));
		List<Responsavel> lista = q.list();

		return lista;
	}

	public void marcarDesmarcarAtencaoMinistro(Long codIett) throws ECARException {
		ItemEstruturaIett iett = (ItemEstruturaIett) this.localizar(ItemEstruturaIett.class, codIett);
		if (iett.getAtencaoIett() != null) {
			iett.setAtencaoIett(!iett.getAtencaoIett());
		} else {
			iett.setAtencaoIett(true);
		}
		this.alterar(iett);
	}

	public WsIndicador buscarIndicador(Long codIett) {
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT new ecar.pojo.acompanhamentoEstrategico.Indicador(");
		hql.append("ind.codIettir,ind.unidMedidaIettr,ind.nomeIettir,ind.metodoCalculo,");
		hql.append("ind.fonteIettr,ind.formulaIettr) ");
		hql.append("FROM ItemEstrtIndResulIettr ind ");
		hql.append("WHERE ind.indAtivoIettr = 'S' ");
		hql.append("AND ind.itemEstruturaIett.indAtivoIett = 'S' ");
		hql.append("AND ind.itemEstruturaIett.codIett = :codIett ");

		Query query = this.session.createQuery(hql.toString());
		query.setLong("codIett", codIett);

		Indicador indicador = (Indicador) query.uniqueResult();

		WsIndicador wsIndicador = preencherWsIndicador(indicador);

		return wsIndicador;
	}

	private WsIndicador preencherWsIndicador(Indicador indicador) {
		WsIndicador wsIndicador = new WsIndicador();
		WsIndicadorCadastro wsIndicadorCadastro = preencherWsIndicadorCadastro(indicador);
		WsIndicadorMetodologia wsIndicadorMetodologia = preencherWsIndicadorMetodologia(indicador);
		wsIndicador.setDadosCadastro(wsIndicadorCadastro);
		wsIndicador.setDadosMetodologia(wsIndicadorMetodologia);

		return wsIndicador;
	}

	private WsIndicadorMetodologia preencherWsIndicadorMetodologia(Indicador indicador) {
		WsIndicadorMetodologia wsIndicadorMetodologia = new WsIndicadorMetodologia();
		WsDominio wsDominio = new WsDominio();
		wsDominio.setCodigo(1);
		wsDominio.setDescricao(indicador.getMetodoCalculo());
		wsIndicadorMetodologia.setMetodoCalculo(wsDominio);

		return wsIndicadorMetodologia;
	}

	private WsIndicadorCadastro preencherWsIndicadorCadastro(Indicador indicador) {
		WsIndicadorCadastro wsIndicadorCadastro = new WsIndicadorCadastro();
		wsIndicadorCadastro.setChvExterno(indicador.getCodigo());
		wsIndicadorCadastro.setNomeIndicador(indicador.getNome());

		return wsIndicadorCadastro;
	}

	public String montaParecer(String parecer, Long codigo, List<AcompReferenciaAref> listaAref) {

		StringBuilder anexos = new StringBuilder();
		String dataFormatada = null;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		try {
			itemEstruturaUploadDao = new ItemEstruturaUploadDao(null);
			List<ItemEstrutUploadIettup> listaDeAnexos = new ArrayList<ItemEstrutUploadIettup>();
			listaDeAnexos = itemEstruturaUploadDao.getAnexosIettNovo(codigo, listaAref);

			if (listaDeAnexos.size() > 0) {

				String nomeArquivo = "";
				anexos.append("<fieldset><legend>Anexos</legend>");
				for (ItemEstrutUploadIettup anexo : listaDeAnexos) {

					nomeArquivo = CriptografiaUtil.encriptar(CriptografiaUtil.CHAVE, anexo.getCodIettup().toString());

					// nomeArquivo = URLEncoder.encode(nomeArquivo);

					dataFormatada = format.format(anexo.getDataInclusaoIettup());

					anexos.append("<a href='http://ecar.saude.gov.br:8080/pe2012/DownloadFileIett?RemoteFile=" + nomeArquivo + "&uiid=" + this.uuid + "'  target='_blank'>" + dataFormatada + " | "
							+ anexo.getNomeOriginalIettup() + "</a></br>");
				}
				anexos.append("</fieldset>");
			}

		} catch (ECARException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return parecer + anexos.toString();

	}

	/**
	 * 
	 * @param codigoObjetivoEstrategico
	 * @param etiqueta
	 * @param etiquetaPrioritaria
	 * @param resultadoStatus
	 * @param codigoUsuario
	 * @param gruposUsuarios
	 * @param prioritario
	 * @param secretarias
	 * @param exercicio
	 * @param painelIndicador
	 *            - 0 indica que não existe o paramêtro, 1 - indica a inclusão
	 *            do paramêtro.
	 * @return
	 * @deprecated Use {@link
	 *             #listarObjetivoEstrategicoFiltroMaisRapidoEuEspero(List<
	 *             Integer
	 *             >,List<String>,List<String>,List<Integer>,Long,List<Long
	 *             >,boolean,List<Long>,Long,int,Long)} instead
	 */
	public List<ObjetivoEstrategico> listarObjetivoEstrategicoFiltroMaisRapidoEuEspero(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria,
			List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios, boolean prioritario, List<Long> secretarias, Long exercicio, int painelIndicador) {
		return listarObjetivoEstrategicoFiltroMaisRapidoEuEspero(codigoObjetivoEstrategico, etiqueta, etiquetaPrioritaria, resultadoStatus, codigoUsuario, gruposUsuarios, prioritario, secretarias,
				exercicio, painelIndicador, null);
	}

	private List<AcompReferenciaAref> getAcompReferenciaArefList() {
		StringBuilder hql = null;
		List<AcompReferenciaAref> listaAref = null;
		Query query = null;

		try {

			hql = new StringBuilder();
			hql.append("FROM AcompReferenciaAref aref ");
			hql.append("ORDER BY aref.codAref DESC ");
			query = getSession().createQuery(hql.toString());
			query.setMaxResults(3);

			listaAref = query.list();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listaAref;

	}

	/**
	 * Copia o SQL de uma String para um arquivo texto
	 * 
	 * @param pathname
	 * @param str
	 */
	private void sqlToFile(String pathname, String str) {
		File file = null;
		FileWriter writer = null;
		file = new File(pathname);
		try {
			writer = new FileWriter(file);
			writer.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.flush();
				writer.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * @param codigoObjetivoEstrategico
	 * @param etiqueta
	 * @param etiquetaPrioritaria
	 * @param resultadoStatus
	 * @param codigoUsuario
	 * @param gruposUsuarios
	 * @param prioritario
	 * @param secretarias
	 * @param exercicio
	 * @param painelIndicador
	 *            - 0 indica que não existe o paramêtro, 1 - indica a inclusão
	 *            do paramêtro.
	 * @param acompReferencia
	 *            TODO
	 * @return
	 */
	public List<ObjetivoEstrategico> listarObjetivoEstrategicoFiltroMaisRapidoEuEspero(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria,
			List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios, boolean prioritario, List<Long> secretarias, Long exercicio, int painelIndicador, Long acompReferencia) {

		Logger log = Logger.getLogger(ItemEstruturaDao.class);
		long tempoInicio = 0;
		long tempoFim = 0;

		tempoInicio = System.currentTimeMillis();
		log.info(String.format("Filtro iniciado: %.2f%n", 0d));

		StringBuilder hql = null;
		StringBuilder sqlHql = null;

		hql = new StringBuilder();
		sqlHql = new StringBuilder();

		queryListaObjetivoEstrategicoFiltroMaisRapidoEuEspero(codigoObjetivoEstrategico, etiqueta, hql, prioritario, secretarias, resultadoStatus, exercicio);

		sqlHql = hql;

		SQLQuery q = this.session.createSQLQuery(hql.toString());

		if (codigoObjetivoEstrategico.size() != 0) {
			q.setParameterList("codObj", codigoObjetivoEstrategico);
		}

		if (etiqueta.size() != 0) {
			q.setParameterList("etiquetas", etiqueta);
		}

		if (secretarias != null && secretarias.size() != 0) {
			q.setParameterList("secretarias", secretarias);
		}

		Boolean somenteNaoMonitorados = false;

		if (resultadoStatus.size() == 1 && resultadoStatus.contains(0))
			somenteNaoMonitorados = true;

		if (!somenteNaoMonitorados) {
			if (resultadoStatus != null && resultadoStatus.size() > 0) {
				q.setParameterList("status", resultadoStatus);
			}
		}

		if (exercicio != null) {
			q.setParameter("exercicio", exercicio);
		} else {
			// ExercicioDao exercicioDao = new ExercicioDao(request);
			// q.setParameter("exercicio",
			// exercicioDao.buscaCodigoExercicioCorrente());
			q.setParameter("exercicio", 0L);
		}

		if (acompReferencia != null) {
			q.setLong("codAref", acompReferencia);
		} else {
			q.setLong("codAref", 0L);
		}

		q.setParameter("codUsu", codigoUsuario);
		q.setParameterList("gruposUsuarios", gruposUsuarios);

		Long init = System.currentTimeMillis();

		// sqlToFile("C:\\temp\\queryRecursivo.txt", sqlHql.toString());

		List<Object[]> lista = q.list();
		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("Consulta - Demorou: " + (diff / 1000) + " segundos");

		Map<String, ObjetivoEstrategico> mapaObjetivo = new TreeMap<String, ObjetivoEstrategico>();
		Map<Long, Estrategia> mapaEstrategia = new TreeMap<Long, Estrategia>();
		Map<String, Resultado> mapaResultado = new TreeMap<String, Resultado>();
		Map<String, Produto> mapaProduto = new TreeMap<String, Produto>();
		Map<Long, Acao> mapaAcao = new TreeMap<Long, Acao>();

		Integer ContaResultado = 0;

		String resultadoMaisAtual = "";

		List<AcompReferenciaAref> listaAref = getAcompReferenciaArefList();

		for (Object[] obj : lista) {

			if (obj[4].toString().equals("1")) {
				ObjetivoEstrategico objetivoEstrategico = new ObjetivoEstrategico();
				objetivoEstrategico.setCodigo(Long.parseLong(obj[0].toString()));
				objetivoEstrategico.setNome(buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio));
				objetivoEstrategico.setSiglaIett(obj[2].toString());
				mapaObjetivo.put(obj[2].toString(), objetivoEstrategico);
			}

			if (obj[4].toString().equals("2")) {
				Estrategia estrategia = new Estrategia();
				estrategia.setCodigo(Long.parseLong(obj[0].toString()));
				estrategia.setNome(buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio));
				estrategia.setSiglaEstrategia(obj[2].toString());
				estrategia.setCodObj(Long.parseLong(obj[1].toString()));
				mapaEstrategia.put(Long.parseLong(obj[0].toString()), estrategia);
			}

			if (obj[4].toString().equals("3")) {
				// if(!resultadoMaisAtual.equals(obj[0].toString())){
				Resultado resultado = new Resultado();
				resultado.setCodigo(Long.parseLong(obj[0].toString()));
				resultado.setCodEstrat(Long.parseLong(obj[1].toString()));
				resultado.setSiglaResultado(obj[2].toString());
				resultado.setNome(buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio));
				if (obj[14] != null)
					resultado.setPrioritario("S");

				resultado.setAtencao((Boolean) obj[15]);

				if (obj[10] != null) {

					Orgao orgao = new Orgao();
					orgao.setNome(obj[13].toString());

					Responsavel responsavel = new Responsavel();
					responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[9].toString());
					responsavel.setOrgao(orgao);

					resultado.responsavel = responsavel;

					List<Responsavel> coResponsavel = new ArrayList<Responsavel>();
					List<Responsavel> articulador = new ArrayList<Responsavel>();

					for (Responsavel responsavelArticulador : buscarCoResponsavel(resultado.getCodigo())) {
						if (responsavelArticulador.getCodTipo() != null && responsavelArticulador.getCodTipo().equals(2)) {
							articulador.add(responsavelArticulador);
						} else {
							if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
								coResponsavel.add(responsavelArticulador);
							}

						}
					}

					if (coResponsavel.size() > 0)
						resultado.setCoResponsavel(coResponsavel);

					if (articulador.size() > 0)
						resultado.setArticulacao(articulador);
				}

				PeriodoAcompanhamento periodoAcompanhamento = new PeriodoAcompanhamento();

				if (obj[16] != null) {
					periodoAcompanhamento = new PeriodoAcompanhamento(0L, montaParecer(obj[16].toString(), Long.parseLong(obj[0].toString()), listaAref), obj[11].toString(), obj[12].toString(),
							Long.parseLong(obj[5].toString()), obj[7].toString(), 0L);
				}

				periodoAcompanhamento.setAno(Integer.parseInt(obj[12].toString()));
				periodoAcompanhamento.setMes(obj[11].toString());

				resultado.addPeriodoAcompanhamento(periodoAcompanhamento);

				if (painelIndicador == 1) {
					AcompReferenciaItemAri ari = null;
					try {
						ari = (AcompReferenciaItemAri) this.buscar(AcompReferenciaItemAri.class, periodoAcompanhamento.getCodigo());
						IndicadorMonitoramentoBean indMon = new IndicadorMonitoramentoBean();
						// periodoAcompanhamento.indMonitoramento =
						// indMon.ListaIndicadores(ari,this.getIndicadores(resultado.getCodigo()));
						periodoAcompanhamento.indMonitoramento = indMon.ListaIndicadores(periodoAcompanhamento, this.getIndicadores(resultado.getCodigo()));
					} catch (ECARException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				ContaResultado++;

				mapaResultado.put(obj[2].toString() + "_" + obj[1].toString(), resultado);
				// }
				// else
				// resultadoMaisAtual = obj[0].toString();
			}
			if (obj[4].toString().equals("4")) {
				Produto produto = new Produto();
				produto.setCodigo(Long.parseLong(obj[0].toString()));
				produto.setNome(buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio));
				produto.setMes(obj[11].toString());
				produto.setAno(obj[12].toString());
				produto.setCodResultado(Long.parseLong(obj[1].toString()));
				produto.setSiglaProduto(obj[2].toString());
				if (obj[10] != null) {
					Orgao orgao = new Orgao();
					orgao.setNome(obj[13].toString());

					Responsavel responsavel = new Responsavel();
					responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[9].toString());
					responsavel.setOrgao(orgao);

					produto.responsavel = responsavel;

					List<Responsavel> coResponsavel = new ArrayList<Responsavel>();
					List<Responsavel> articulador = new ArrayList<Responsavel>();

					for (Responsavel responsavelArticulador : buscarCoResponsavel(produto.getCodigo())) {
						if (responsavelArticulador.getCodTipo().equals(2)) {
							articulador.add(responsavelArticulador);
						} else {
							if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
								coResponsavel.add(responsavelArticulador);
							}

						}
					}

					if (coResponsavel.size() > 0)
						produto.setCoResponsavel(coResponsavel);
				}

				PeriodoAcompanhamento periodoAcompanhamento = new PeriodoAcompanhamento();

				if (obj[16] != null) {
					periodoAcompanhamento = new PeriodoAcompanhamento(0L, montaParecer(obj[16].toString(), Long.parseLong(obj[0].toString()), listaAref), obj[11].toString(), obj[12].toString(),
							Long.parseLong(obj[5].toString()), obj[7].toString(), 0L);
				}

				periodoAcompanhamento.setAno(Integer.parseInt(obj[12].toString()));
				periodoAcompanhamento.setMes(obj[11].toString());

				produto.addPeriodosAcompanhamento(periodoAcompanhamento);

				mapaProduto.put(obj[2].toString() + "_" + obj[1].toString(), produto);

			}

			if (obj[4].toString().equals("5")) {
				Acao acao = new Acao(Long.parseLong(obj[0].toString()), buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio), Long.parseLong(obj[1].toString()),
						obj[2].toString());

				mapaAcao.put(Long.parseLong(obj[0].toString()), acao);
			}

		}

		System.out.println(ContaResultado);

		List<Produto> listaProduto = populaAcao(mapaProduto, mapaAcao, false);

		List<Resultado> listaResultado = populaProduto(mapaResultado, listaProduto);

		List<Estrategia> listaEstrategia = populaResultado(mapaEstrategia, listaResultado);

		populaEstrategia(mapaObjetivo, listaEstrategia);

		tempoFim = System.currentTimeMillis();
		log.info(String.format("Filtro finalizado: %.2f%n", (tempoFim - tempoInicio) / 1000d));

		return new ArrayList<ObjetivoEstrategico>(mapaObjetivo.values());

	}

	/**
	 * @deprecated Use {@link
	 *             #listarProduto2(Integer,Long,List<Long>,String,Long,Long)}
	 *             instead
	 */
	public Produto listarProduto2(Integer codigoProduto, Long codigoUsuario, List<Long> gruposUsuarios, String uiid, Long exercicio) {
		return listarProduto2(codigoProduto, codigoUsuario, gruposUsuarios, uiid, exercicio, null);
	}

	public Produto listarProduto2(Integer codigoProduto, Long codigoUsuario, List<Long> gruposUsuarios, String uiid, Long exercicio, Long acompReferencia) {

		this.uuid = uuid;

		StringBuilder hql = new StringBuilder();

		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();

		codigoObjetivoEstrategico.add(codigoProduto);

		queryListaObjetivoEstrategicoFiltroMaisRapidoEuEspero(codigoObjetivoEstrategico, null, hql, false, null, null, exercicio);

		SQLQuery q = this.session.createSQLQuery(hql.toString());

		if (codigoObjetivoEstrategico.size() != 0) {
			q.setParameterList("codObj", codigoObjetivoEstrategico);
		}

		q.setParameter("codUsu", codigoUsuario);
		q.setParameterList("gruposUsuarios", gruposUsuarios);

		if (exercicio != null) {
			q.setParameter("exercicio", exercicio);
		}

		if (acompReferencia != null) {
			q.setLong("codAref", acompReferencia);
		} else {
			q.setLong("codAref", 0L);
		}

		List<Object[]> lista = q.list();

		Map<String, Produto> mapaProduto = new TreeMap<String, Produto>();
		Map<Long, Acao> mapaAcao = new TreeMap<Long, Acao>();
		Map<Long, Atividade> mapaAtividade = new TreeMap<Long, Atividade>();

		Produto produto = new Produto();

		List<AcompReferenciaAref> listaAref = getAcompReferenciaArefList();

		for (Object[] obj : lista) {

			if (obj[4].toString().equals("4")) {
				produto.setCodigo(Long.parseLong(obj[0].toString()));
				produto.setNome(buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio));
				produto.setMes(obj[11].toString());
				produto.setAno(obj[12].toString());
				produto.setCodResultado(Long.parseLong(obj[1].toString()));
				produto.setSiglaProduto(obj[2].toString());

				produto.setDataInicio((Date) obj[17]);
				if (obj[18] != null)
					produto.setDataFim((Date) obj[18]);

				if (obj[10] != null) {
					Orgao orgao = new Orgao();
					orgao.setNome(obj[13].toString());

					Responsavel responsavel = new Responsavel();
					responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[9].toString());
					responsavel.setOrgao(orgao);

					produto.responsavel = responsavel;

					List<Responsavel> coResponsavel = new ArrayList<Responsavel>();
					List<Responsavel> articulador = new ArrayList<Responsavel>();

					for (Responsavel responsavelArticulador : buscarCoResponsavel(produto.getCodigo())) {
						if (responsavelArticulador.getCodTipo().equals(2)) {
							articulador.add(responsavelArticulador);
						} else {
							if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
								coResponsavel.add(responsavelArticulador);
							}

						}
					}

					if (coResponsavel.size() > 0)
						produto.setCoResponsavel(coResponsavel);
				}

				PeriodoAcompanhamento periodoAcompanhamento = new PeriodoAcompanhamento();

				if (obj[16] != null) {
					periodoAcompanhamento = new PeriodoAcompanhamento(0L, montaParecer(obj[16].toString(), Long.parseLong(obj[0].toString()), listaAref), obj[11].toString(), obj[12].toString(),
							Long.parseLong(obj[5].toString()), obj[7].toString(), 0L);
				}

				periodoAcompanhamento.setAno(Integer.parseInt(obj[12].toString()));
				periodoAcompanhamento.setMes(obj[11].toString());

				produto.addPeriodosAcompanhamento(periodoAcompanhamento);

				mapaProduto.put(obj[2].toString() + "_" + obj[1].toString(), produto);

			}

			if (obj[4].toString().equals("5")) {
				Acao acao = new Acao(Long.parseLong(obj[0].toString()), buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio), Long.parseLong(obj[1].toString()),
						obj[2].toString());

				if (obj[10] != null) {

					Orgao orgao = new Orgao();
					orgao.setNome(obj[13].toString());

					Responsavel responsavel = new Responsavel();
					responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[9].toString());
					responsavel.setOrgao(orgao);

					acao.responsavel = responsavel;

				}

				acao.setDataInicio((Date) obj[17]);
				if (obj[18] != null)
					acao.setDataFim((Date) obj[18]);

				PeriodoAcompanhamento periodoAcompanhamento = new PeriodoAcompanhamento();

				if (obj[16] != null) {

					periodoAcompanhamento = new PeriodoAcompanhamento(0L, montaParecer(obj[16].toString(), Long.parseLong(obj[0].toString()), listaAref), obj[11].toString(), obj[12].toString(),
							Long.parseLong(obj[5].toString()), obj[7].toString(), 0L);
				}

				periodoAcompanhamento.setAno(Integer.parseInt(obj[12].toString()));
				periodoAcompanhamento.setMes(obj[11].toString());

				acao.addPeriodosAcompanhamento(periodoAcompanhamento);

				mapaAcao.put(Long.parseLong(obj[0].toString()), acao);
			}

		}

		List<Produto> listaProduto = populaAcao(mapaProduto, mapaAcao, true);

		return produto;
	}

	/**
	 * @deprecated Use {@link
	 *             #listarResultado2(Integer,Long,List<Long>,String,Long,Long)}
	 *             instead
	 */
	public Resultado listarResultado2(Integer codigoResultado, Long codigoUsuario, List<Long> gruposUsuarios, String uuid, Long exercicio) throws ECARException {
		return listarResultado2(codigoResultado, codigoUsuario, gruposUsuarios, uuid, exercicio, null);
	}

	public Resultado listarResultado2(Integer codigoResultado, Long codigoUsuario, List<Long> gruposUsuarios, String uuid, Long exercicio, Long acompReferencia) throws ECARException {

		this.uuid = uuid;

		StringBuilder hql = new StringBuilder();

		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();

		codigoObjetivoEstrategico.add(codigoResultado);

		queryListaObjetivoEstrategicoFiltroMaisRapidoEuEspero(codigoObjetivoEstrategico, null, hql, false, null, null, exercicio);

		SQLQuery q = this.session.createSQLQuery(hql.toString());

		if (codigoObjetivoEstrategico.size() != 0) {
			q.setParameterList("codObj", codigoObjetivoEstrategico);
		}

		q.setParameter("codUsu", codigoUsuario);
		q.setParameterList("gruposUsuarios", gruposUsuarios);

		if (exercicio != null) {
			q.setParameter("exercicio", exercicio);
		}

		if (acompReferencia != null) {
			q.setLong("codAref", acompReferencia);
		} else {
			q.setLong("codAref", 0L);
		}

		List<Object[]> lista = q.list();

		Map<String, Resultado> mapaResultado = new TreeMap<String, Resultado>();
		Map<String, Produto> mapaProduto = new TreeMap<String, Produto>();
		Map<Long, Acao> mapaAcao = new TreeMap<Long, Acao>();

		Resultado resultado = new Resultado();

		List<AcompReferenciaAref> listaAref = getAcompReferenciaArefList();

		for (Object[] obj : lista) {

			if (obj[4].toString().equals("3")) {
				resultado.setCodigo(Long.parseLong(obj[0].toString()));
				resultado.setCodEstrat(Long.parseLong(obj[1].toString()));
				resultado.setSiglaResultado(obj[2].toString());
				resultado.setNome(buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio));
				if (obj[14] != null)
					resultado.setPrioritario("S");

				resultado.setAtencao((Boolean) obj[15]);
				resultado.setDataInicio((Date) obj[17]);
				if (obj[18] != null)
					resultado.setDataFim((Date) obj[18]);

				if (obj[10] != null) {

					Orgao orgao = new Orgao();
					orgao.setNome(obj[13].toString());

					Responsavel responsavel = new Responsavel();
					responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[9].toString());
					responsavel.setOrgao(orgao);

					resultado.responsavel = responsavel;

					List<Responsavel> coResponsavel = new ArrayList<Responsavel>();
					List<Responsavel> articulador = new ArrayList<Responsavel>();

					for (Responsavel responsavelArticulador : buscarCoResponsavel(resultado.getCodigo())) {
						if (responsavelArticulador.getCodTipo() != null && responsavelArticulador.getCodTipo().equals(2)) {
							articulador.add(responsavelArticulador);
						} else {
							if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
								coResponsavel.add(responsavelArticulador);
							}

						}
					}

					if (coResponsavel.size() > 0)
						resultado.setCoResponsavel(coResponsavel);

					if (articulador.size() > 0)
						resultado.setArticulacao(articulador);
				}

				PeriodoAcompanhamento periodoAcompanhamento = new PeriodoAcompanhamento();

				if (obj[16] != null) {
					periodoAcompanhamento = new PeriodoAcompanhamento(0L, montaParecer(obj[16].toString(), Long.parseLong(obj[0].toString()), listaAref), obj[11].toString(), obj[12].toString(),
							Long.parseLong(obj[5].toString()), obj[7].toString(), 0L);
				}

				periodoAcompanhamento.setAno(Integer.parseInt(obj[12].toString()));
				periodoAcompanhamento.setMes(obj[11].toString());

				resultado.addPeriodoAcompanhamento(periodoAcompanhamento);

				mapaResultado.put(obj[2].toString() + "_" + obj[1].toString(), resultado);
			}

			if (obj[4].toString().equals("4")) {
				Produto produto = new Produto();
				produto.setCodigo(Long.parseLong(obj[0].toString()));
				produto.setNome(buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio));
				produto.setMes(obj[11].toString());
				produto.setAno(obj[12].toString());
				produto.setCodResultado(Long.parseLong(obj[1].toString()));
				produto.setSiglaProduto(obj[2].toString());
				if (obj[16] != null)
					produto.setParecer(obj[16].toString());
				produto.setDataInicio((Date) obj[17]);
				if (obj[18] != null)
					produto.setDataFim((Date) obj[18]);

				if (obj[10] != null) {
					Orgao orgao = new Orgao();
					orgao.setNome(obj[13].toString());

					Responsavel responsavel = new Responsavel();
					responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[9].toString());
					responsavel.setOrgao(orgao);

					produto.responsavel = responsavel;

					List<Responsavel> coResponsavel = new ArrayList<Responsavel>();
					List<Responsavel> articulador = new ArrayList<Responsavel>();

					for (Responsavel responsavelArticulador : buscarCoResponsavel(produto.getCodigo())) {
						if (responsavelArticulador.getCodTipo().equals(2)) {
							articulador.add(responsavelArticulador);
						} else {
							if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
								coResponsavel.add(responsavelArticulador);
							}

						}
					}

					if (coResponsavel.size() > 0)
						produto.setCoResponsavel(coResponsavel);
				}

				PeriodoAcompanhamento periodoAcompanhamento = new PeriodoAcompanhamento();

				if (obj[16] != null) {
					periodoAcompanhamento = new PeriodoAcompanhamento(0L, montaParecer(obj[16].toString(), Long.parseLong(obj[0].toString()), listaAref), obj[11].toString(), obj[12].toString(),
							Long.parseLong(obj[5].toString()), obj[7].toString(), 0L);
				}

				periodoAcompanhamento.setAno(Integer.parseInt(obj[12].toString()));
				periodoAcompanhamento.setMes(obj[11].toString());

				produto.addPeriodosAcompanhamento(periodoAcompanhamento);

				mapaProduto.put(obj[2].toString() + "_" + obj[1].toString(), produto);

			}

			if (obj[4].toString().equals("5")) {
				Acao acao = new Acao(Long.parseLong(obj[0].toString()), buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio), Long.parseLong(obj[1].toString()),
						obj[2].toString());

				acao.setDataInicio((Date) obj[17]);
				if (obj[18] != null)
					acao.setDataFim((Date) obj[18]);

				if (obj[10] != null) {

					Orgao orgao = new Orgao();
					orgao.setNome(obj[13].toString());

					Responsavel responsavel = new Responsavel();
					responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[9].toString());
					responsavel.setOrgao(orgao);

					acao.responsavel = responsavel;

				}

				acao.setDataInicio((Date) obj[17]);
				if (obj[18] != null)
					acao.setDataFim((Date) obj[18]);

				PeriodoAcompanhamento periodoAcompanhamento = new PeriodoAcompanhamento();

				if (obj[16] != null) {

					periodoAcompanhamento = new PeriodoAcompanhamento(0L, montaParecer(obj[16].toString(), Long.parseLong(obj[0].toString()), listaAref), obj[11].toString(), obj[12].toString(),
							Long.parseLong(obj[5].toString()), obj[7].toString(), 0L);
				}

				periodoAcompanhamento.setAno(Integer.parseInt(obj[12].toString()));
				periodoAcompanhamento.setMes(obj[11].toString());

				acao.addPeriodosAcompanhamento(periodoAcompanhamento);

				mapaAcao.put(Long.parseLong(obj[0].toString()), acao);
			}

		}

		List<Produto> listaProduto = populaAcao(mapaProduto, mapaAcao, true);

		List<Resultado> listaResultado = populaProduto(mapaResultado, listaProduto);

		// Comentarios
		resultado.setComentarios(daoIettAcao.loadComentarioWS(resultado.getCodigo(), null, null));
		// FIM Comentarios

		// Periodos Acompanhamentos
		List<PeriodoAcompanhamento> periodos = recuperaPeriodosAcompanhamento(resultado.getCodigo(), null, null);

		/* Incio Inclusão dos indicadores de Monitoramento de acordo com o GHC * */
		IndicadorMonitoramentoBean indMon = new IndicadorMonitoramentoBean();
		AcompRelatorioDao acompRelatorioDao = new AcompRelatorioDao(request);

		// 4 = código da função de acompanhamento do responsável pelo item
		TipoFuncAcompTpfa fa = (TipoFuncAcompTpfa) this.buscar(TipoFuncAcompTpfa.class, 3l);
		UsuarioUsu usuario = (UsuarioUsu) this.buscar(UsuarioUsu.class, codigoUsuario);
		int i = 1;

		for (PeriodoAcompanhamento periodoAcompanhamento : periodos) {
			AcompReferenciaItemAri ari = (AcompReferenciaItemAri) this.buscar(AcompReferenciaItemAri.class, periodoAcompanhamento.getCodigo());

			// Verifica se o usuário tem permissão para justificar o percentual
			// de atingimento de um indicador
			AcompRelatorioArel arel = acompRelatorioDao.getAcompRelatorio(fa, ari);
			Boolean edicaoJustificativa = false;
			if (arel != null) {
				// Se o usuário possui permissão para gravar relatório
				// (parecer), ele também possui permissão para justificar
				if (acompRelatorioDao.podeGravarRelatorio(usuario, fa, ari, arel) == AcompRelatorioDao.OPERACAO_PERMITIDA)
					// verifica se Data limite foi superada
					if (!acompRelatorioDao.isDataLimiteParecerVencida(fa, ari))
						// Permite registrar informação somente a partir da data
						// de início do acompanhamento
						if (acompRelatorioDao.isDataInicialParecerAtingida(ari))
							edicaoJustificativa = true;
			}
			// List<Indicador> listaIndicador =
			// getListIndicador(ari.getItemEstruturaIett().getCodIett());
			// Lista, para cada período de acompanhamento, os indicadores do
			// item de estrutura (ari)
			// periodoAcompanhamento.indMonitoramento =
			// indMon.ListaIndicadores(ari, edicaoJustificativa);

			periodoAcompanhamento.indMonitoramento = indMon.ListaIndicadores(ari, getIndicadores(resultado.getCodigo()));

			// Seta o número do ciclo
			periodoAcompanhamento.setCiclo(i);

			periodoAcompanhamento.setParecer(montaParecer(periodoAcompanhamento.getParecer(), resultado.getCodigo(), listaAref));

			i++;
		}
		if (periodos != null && periodos.size() > 0) {
			resultado.setPeriodoAcompanhamento(periodos);
		}
		// else{
		// PeriodoAcompanhamento pa = new PeriodoAcompanhamento();
		// resultado.addPeriodoAcompanhamento(pa);
		// }
		/* FIM Inclusão dos indicadores de Monitoramento de acordo com o GHC * */

		// res.getPeriodoAcompanhamento().get(0).setCiclo(periodos.size());
		/*
		 * int i =1; PeriodoAcompanhamento pa = new PeriodoAcompanhamento(); for
		 * (PeriodoAcompanhamento periodoAcompanhamento : periodos) {
		 * if(periodoAcompanhamento.getCodigo() !=
		 * resultado.getPeriodoAcompanhamento().get(0).getCodigo()){
		 * periodoAcompanhamento.setCiclo(i); }else{
		 * resultado.getPeriodoAcompanhamento().get(0).setCiclo(i); pa =
		 * periodoAcompanhamento; } i++; } periodos.remove(pa);
		 * resultado.getPeriodoAcompanhamento().addAll(periodos); //FIM Periodos
		 * Acompanhamentos
		 */
		return resultado;
	}

	public List<br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAcaoCadastro> obtemListaAcao(Map<String, Object> params, EstruturaEtt... estruturas) {
		StringBuilder hqlAcaoCad = new StringBuilder();
		// WsAcaoCadastro
		hqlAcaoCad.append("SELECT ");
		hqlAcaoCad.append("NEW br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAcaoCadastro( ");
		hqlAcaoCad.append("iett.codIett,");// chvExterno
		hqlAcaoCad.append("proj.atribInfCompSatb,");// projeto - COD
		hqlAcaoCad.append("proj.descricaoSatb,");// projeto - DESC
		hqlAcaoCad.append("eixo.atribInfCompSatb,");// eixo - COD
		hqlAcaoCad.append("eixo.descricaoSatb,");// eixo - DESC
		hqlAcaoCad.append("iett.descricaoR2,");// nomeAcao
		hqlAcaoCad.append("'" + String.valueOf(params.get("cnpj")) + "', ");// orgaoResponsavel
																			// -
																			// CNPJ
		hqlAcaoCad.append("'" + String.valueOf(params.get("nomeEmpresa")) + "', ");// orgaoResponsavel
																					// -
																					// NomeEmpresa
		hqlAcaoCad.append("iett.dataR1,");// dataInicioAcao
		hqlAcaoCad.append("iett.dataR2,");// dataTerminoAcao
		hqlAcaoCad.append("iett.situacaoSit.codSit,");// situacaoAcao - Cod
		hqlAcaoCad.append("iett.situacaoSit.descricaoSit,");// situacaoAcao -
															// Cod
		hqlAcaoCad.append("resp.cnpjCpfUsu,");// gerenteAcao - numeroCpf
		// hqlAcaoCad.append("respAcao.usuarioUsu.nomeUsu,");//gerenteAcao -
		// numeroCpf
		hqlAcaoCad.append("resp.nomeUsu, ");// gerenteAcao - nome
		hqlAcaoCad.append("iett.indAtivoIett ");// Ativo
		hqlAcaoCad.append(") ");
		hqlAcaoCad.append("FROM ");
		hqlAcaoCad.append("ItemEstruturaIett iett ");
		hqlAcaoCad.append("JOIN iett.itemEstUsutpfuacIettutfas respAcao ");
		hqlAcaoCad.append("JOIN respAcao.usuarioUsu resp ");
		hqlAcaoCad.append("JOIN iett.itemEstruturaSisAtributoIettSatbs proj1 ");
		hqlAcaoCad.append("JOIN proj1.sisAtributoSatb proj ");
		hqlAcaoCad.append("JOIN iett.itemEstruturaSisAtributoIettSatbs eixo1 ");
		hqlAcaoCad.append("JOIN eixo1.sisAtributoSatb eixo ");
		hqlAcaoCad.append("WHERE ");
		hqlAcaoCad.append("proj.sisGrupoAtributoSga.codSga = 34 ");
		hqlAcaoCad.append("AND ");
		hqlAcaoCad.append("eixo.sisGrupoAtributoSga.codSga = 36 ");
		hqlAcaoCad.append("AND ");
		hqlAcaoCad.append("respAcao.tipoFuncAcompTpfa in (3,6) ");
		hqlAcaoCad.append("AND ");
		hqlAcaoCad.append("iett.descricaoR1 <> '' ");
		hqlAcaoCad.append("AND ");
		hqlAcaoCad.append("iett.descricaoR2 <> '' ");
		hqlAcaoCad.append("AND ");
		hqlAcaoCad.append("iett.descricaoR5 <> 'EXCP' ");
		hqlAcaoCad.append("AND ");
		hqlAcaoCad.append("iett.estruturaEtt in (:ests) ");

		Query q = getSession().createQuery(hqlAcaoCad.toString());

		q.setParameterList("ests", estruturas);

		List<br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAcaoCadastro> lista = q.list();
		// q.setParameter("tpfa", params.get("tpfa"));

		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<WsAtividadeCadastro> obtemListaAcaoTarefa(Integer chvExterno, boolean isCodPai, boolean nIntermediario) {
		StringBuilder hqlTafCad = new StringBuilder();
		logger.debug("Inicio do obtemAcaoTarefa()...");
		hqlTafCad.append("SELECT NEW br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAtividadeCadastro(");
		hqlTafCad.append("ativSP.codIett,");// chvExterno
		hqlTafCad.append("tipoTaf.atribInfCompSatb,");// tipoTarefa - COD
		hqlTafCad.append("ativSP.descricaoR2,");// nomeTarefa
		hqlTafCad.append("unMedida.atribInfCompSatb,");// unidadeMedidaDuracaoCod
														// - COD
		hqlTafCad.append("resp.cnpjCpfUsu,");// responsavel - CPF
		hqlTafCad.append("resp.nomeUsu,");// responsavel - NOME
		hqlTafCad.append("ativSP.dataR1,");// dataHoraInicioPrevisto
		hqlTafCad.append("ativSP.dataR2,");// dataHoraInicioPrevisto
		hqlTafCad.append("ativSP.descricaoR1,");// Sigla
		hqlTafCad.append("ativSP.situacaoSit.codSit,");// Situacao
		hqlTafCad.append(chvExterno);// chaveAcaoExterna
		hqlTafCad.append(") ");
		hqlTafCad.append("FROM ItemEstruturaIett ativSP ");
		if (nIntermediario) {
			hqlTafCad.append("JOIN ativSP.itemEstruturaIett nIntermediario ");
			hqlTafCad.append("JOIN nIntermediario.itemEstruturaIett acaoSP ");
		} else {
			hqlTafCad.append("JOIN ativSP.itemEstruturaIett acaoSP ");
		}

		hqlTafCad.append("JOIN ativSP.itemEstUsutpfuacIettutfas resp1 ");
		hqlTafCad.append("JOIN resp1.usuarioUsu resp ");
		hqlTafCad.append("JOIN ativSP.itemEstruturaSisAtributoIettSatbs tipoTaf1 ");
		hqlTafCad.append("JOIN tipoTaf1.sisAtributoSatb tipoTaf ");
		hqlTafCad.append("JOIN ativSP.itemEstruturaSisAtributoIettSatbs unMedida1 ");
		hqlTafCad.append("JOIN unMedida1.sisAtributoSatb unMedida ");
		hqlTafCad.append("WHERE ");
		hqlTafCad.append("tipoTaf.sisGrupoAtributoSga.codSga = 37 ");
		hqlTafCad.append("AND unMedida.sisGrupoAtributoSga.codSga = 39 ");

		hqlTafCad.append("AND ");
		hqlTafCad.append("resp1.tipoFuncAcompTpfa.codTpfa in (3,6,7) ");

		hqlTafCad.append("AND ");
		if (isCodPai) {
			hqlTafCad.append("acaoSP.codIett = :chvEx ");
		} else {
			hqlTafCad.append("ativSP.codIett = :chvEx ");
		}

		hqlTafCad.append("ORDER BY ativSP.siglaIett ");

		Query q = getSession().createQuery(hqlTafCad.toString());

		q.setLong("chvEx", chvExterno);
		// q.setParameter("tpfa", params.get("tpfa"));
		return q.list();
	}

	/**
	 * @deprecated Use {@link 
	 *             #listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero
	 *             (List<Integer>,List<String>,List<String>,List<Integer>,Long,
	 *             List
	 *             <Long>,boolean,boolean,boolean,List<Long>,boolean,Date,Date
	 *             ,Long,boolean,Long)} instead
	 */
	public List<ObjetivoEstrategico> listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria,
			List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios, boolean isIndicador, boolean apenasParecer, boolean prioritario, List<Long> secretarias,
			boolean relEncaminhamento, Date dataInicial, Date dataFinal, Long exercicio, boolean somenteREM) throws ECARException {
		return listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(codigoObjetivoEstrategico, etiqueta, etiquetaPrioritaria, resultadoStatus, codigoUsuario, gruposUsuarios, isIndicador,
				apenasParecer, prioritario, secretarias, relEncaminhamento, dataInicial, dataFinal, exercicio, somenteREM, null, false);
	}

	public List<ObjetivoEstrategico> listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria,
			List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios, boolean isIndicador, boolean apenasParecer, boolean prioritario, List<Long> secretarias,
			boolean relEncaminhamento, Date dataInicial, Date dataFinal, Long exercicio, boolean somenteREM, Long acompReferencia, boolean isDetalhamento) throws ECARException {
		StringBuilder hql = new StringBuilder();

		queryListaObjetivoEstrategicoFiltroMaisRapidoEuEspero(codigoObjetivoEstrategico, etiqueta, hql, prioritario, secretarias, resultadoStatus, exercicio);

		SQLQuery q = this.session.createSQLQuery(hql.toString());

		if (codigoObjetivoEstrategico.size() != 0) {
			q.setParameterList("codObj", codigoObjetivoEstrategico);
		}

		if (etiqueta.size() != 0) {
			q.setParameterList("etiquetas", etiqueta);
		}

		if (secretarias != null && secretarias.size() != 0) {
			q.setParameterList("secretarias", secretarias);
		}

		Boolean somenteNaoMonitorados = false;

		if (resultadoStatus != null && resultadoStatus.size() == 1 && resultadoStatus.contains(0))
			somenteNaoMonitorados = true;

		if (resultadoStatus != null && resultadoStatus.size() > 0 && !somenteNaoMonitorados) {
			q.setParameterList("status", resultadoStatus);
		}

		if (exercicio != null) {
			q.setParameter("exercicio", exercicio);
		} else {
			// ExercicioDao exercicioDao = new ExercicioDao(request);
			// q.setParameter("exercicio",
			// exercicioDao.buscaCodigoExercicioCorrente());
			q.setParameter("exercicio", 0L);
		}

		if (acompReferencia != null) {
			q.setLong("codAref", acompReferencia);
		} else {
			q.setLong("codAref", 0L);
		}

		q.setParameter("codUsu", codigoUsuario);
		q.setParameterList("gruposUsuarios", gruposUsuarios);

		List<Object[]> lista = q.list();

		Map<String, ObjetivoEstrategico> mapaObjetivo = new TreeMap<String, ObjetivoEstrategico>();
		Map<Long, Estrategia> mapaEstrategia = new TreeMap<Long, Estrategia>();
		Map<String, Resultado> mapaResultado = new TreeMap<String, Resultado>();
		Map<String, Produto> mapaProduto = new TreeMap<String, Produto>();
		Map<Long, Acao> mapaAcao = new TreeMap<Long, Acao>();

		for (Object[] obj : lista) {

			if (obj[4].toString().equals("1")) {
				ObjetivoEstrategico objetivoEstrategico = new ObjetivoEstrategico();
				objetivoEstrategico.setCodigo(Long.parseLong(obj[0].toString()));
				objetivoEstrategico.setNome(buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio));
				objetivoEstrategico.setSiglaIett(obj[2].toString());
				if (obj[19] != null)
					objetivoEstrategico.setSituacao(obj[19].toString());
				mapaObjetivo.put(obj[2].toString(), objetivoEstrategico);
			}

			if (obj[4].toString().equals("2")) {
				Estrategia estrategia = new Estrategia();
				estrategia.setCodigo(Long.parseLong(obj[0].toString()));
				estrategia.setNome(buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio));
				estrategia.setSiglaEstrategia(obj[2].toString());
				estrategia.setCodObj(Long.parseLong(obj[1].toString()));
				if (obj[19] != null)
					estrategia.setSituacao(obj[19].toString());
				mapaEstrategia.put(Long.parseLong(obj[0].toString()), estrategia);
			}

			if (obj[4].toString().equals("3")) {
				Resultado resultado = new Resultado();
				resultado.setCodigo(Long.parseLong(obj[0].toString()));
				resultado.setCodEstrat(Long.parseLong(obj[1].toString()));
				resultado.setSiglaResultado(obj[2].toString());
				resultado.setNome(buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio));
				if (obj[19] != null)
					resultado.setSituacao(obj[19].toString());
				if (obj[14] != null)
					resultado.setPrioritario("S");

				resultado.setAtencao((Boolean) obj[15]);

				if (obj[10] != null) {

					Orgao orgao = new Orgao();
					orgao.setNome(obj[13].toString());

					Responsavel responsavel = new Responsavel();
					responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[9].toString());
					responsavel.setOrgao(orgao);

					resultado.responsavel = responsavel;

					List<Responsavel> coResponsavel = new ArrayList<Responsavel>();
					List<Responsavel> articulador = new ArrayList<Responsavel>();

					for (Responsavel responsavelArticulador : buscarCoResponsavel(resultado.getCodigo())) {
						if (responsavelArticulador.getCodTipo() != null && responsavelArticulador.getCodTipo().equals(2)) {
							articulador.add(responsavelArticulador);
						} else {
							if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
								coResponsavel.add(responsavelArticulador);
							}

						}
					}

					if (coResponsavel.size() > 0)
						resultado.setCoResponsavel(coResponsavel);

					if (articulador.size() > 0)
						resultado.setArticulacao(articulador);
				}

				// Boolean seInseri = true;
				//
				// if (acompReferencia != null && !acompReferencia.equals(0L)) {
				//
				//
				// List<PeriodoAcompanhamento> listaPA = new
				// ArrayList<PeriodoAcompanhamento>();
				// listaPA =
				// recuperaPeriodosAcompanhamento(resultado.getCodigo(),
				// acompReferencia, resultadoStatus);
				//
				// if(listaPA.size()>0){
				// resultado.setPeriodoAcompanhamento(listaPA);
				//
				// AcompRelatorioDao acompRelatorioDao = new
				// AcompRelatorioDao(null);
				//
				// AcompRelatorioArel acompRelatorioArel = (AcompRelatorioArel)
				// acompRelatorioDao.buscar(AcompRelatorioArel.class,
				// listaPA.get(0).getCodArel() );
				//
				//
				// Responsavel responsavel = new Responsavel();
				//
				// responsavel.setCodigo(acompRelatorioArel.getUsuarioUsuUltimaManutencao().getCodUsu());
				// responsavel.setNome(acompRelatorioArel.getUsuarioUsuUltimaManutencao().getNomeUsu());
				//
				//
				//
				// Iterator iterator =
				// acompRelatorioArel.getUsuarioUsuUltimaManutencao().getOrgaoOrgs().iterator();
				//
				// OrgaoOrg orgaoOrg = new OrgaoOrg();
				//
				// while(iterator.hasNext()){
				// orgaoOrg = (OrgaoOrg) iterator.next();
				// }
				//
				// Orgao orgao = new Orgao();
				// orgao.setNome(orgaoOrg.getSiglaOrg()!=null?orgaoOrg.getSiglaOrg():"");
				//
				// responsavel.setOrgao(orgao);
				//
				// resultado.responsavel = responsavel;
				//
				// resultado.setSituacao(acompRelatorioArel.getSituacaoSit().getDescricaoSit());
				//
				//
				// }
				// else{
				// seInseri = false;
				//
				// AcompReferenciaDao acompReferenciaDao = new
				// AcompReferenciaDao(null);
				// AcompReferenciaAref acompReferenciaAref =
				// (AcompReferenciaAref)
				// acompReferenciaDao.buscar(AcompReferenciaAref.class,
				// acompReferencia);
				//
				// List<AcompReferenciaItemAri> listaAri =
				// (List<AcompReferenciaItemAri>)
				// acompReferenciaDao.getAcompReferenciaItemAriByAref(acompReferenciaAref.getCodAref());
				//
				// for(AcompReferenciaItemAri ari:listaAri){
				// if(ari.getItemEstruturaIett().getCodIett().longValue()==resultado.getCodigo()){
				//
				// if (resultadoStatus != null
				// && resultadoStatus.size() > 0
				// && !resultadoStatus.contains(0)) {
				// seInseri = false;
				// } else {
				// seInseri = true;
				// PeriodoAcompanhamento periodoAcompanhamento = new
				// PeriodoAcompanhamento();
				// periodoAcompanhamento.setMes(acompReferenciaAref.getMesAref());
				// periodoAcompanhamento.setAno(Integer.parseInt(acompReferenciaAref.getAnoAref()));
				// resultado.addPeriodoAcompanhamento(periodoAcompanhamento);
				// break;
				// }
				// }
				// }
				//
				//
				// }
				//
				//
				// } else {

				PeriodoAcompanhamento periodoAcompanhamento = new PeriodoAcompanhamento();

				if (obj[16] != null) {
					periodoAcompanhamento = new PeriodoAcompanhamento(0L, limpaParecer(obj[16].toString()), obj[11].toString(), obj[12].toString(), Long.parseLong(obj[5].toString()),
							obj[7].toString(), 0L);
				}
				periodoAcompanhamento.setAno(Integer.parseInt(obj[12].toString()));
				periodoAcompanhamento.setMes(obj[11].toString());

				resultado.addPeriodoAcompanhamento(periodoAcompanhamento);
				// }

				// if(seInseri)
				mapaResultado.put(obj[2].toString() + "_" + obj[1].toString(), resultado);
			}

			if (obj[4].toString().equals("4")) {
				Produto produto = new Produto();
				produto.setCodigo(Long.parseLong(obj[0].toString()));
				produto.setNome(buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio));
				produto.setMes(obj[11].toString());
				produto.setAno(obj[12].toString());
				produto.setCodResultado(Long.parseLong(obj[1].toString()));
				produto.setSiglaProduto(obj[2].toString());
				if (obj[19] != null)
					produto.setSituacao(obj[19].toString());
				if (obj[10] != null) {
					Orgao orgao = new Orgao();
					orgao.setNome(obj[13].toString());

					Responsavel responsavel = new Responsavel();
					responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[9].toString());
					responsavel.setOrgao(orgao);

					produto.responsavel = responsavel;

					List<Responsavel> coResponsavel = new ArrayList<Responsavel>();
					List<Responsavel> articulador = new ArrayList<Responsavel>();

					for (Responsavel responsavelArticulador : buscarCoResponsavel(produto.getCodigo())) {
						if (responsavelArticulador.getCodTipo().equals(2)) {
							articulador.add(responsavelArticulador);
						} else {
							if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
								coResponsavel.add(responsavelArticulador);
							}

						}
					}

					if (coResponsavel.size() > 0)
						produto.setCoResponsavel(coResponsavel);
				}

				// Boolean seInseri = true;
				//
				// if (acompReferencia != null && !acompReferencia.equals(0L)) {
				//
				//
				// List<PeriodoAcompanhamento> listaPA = new
				// ArrayList<PeriodoAcompanhamento>();
				// listaPA = recuperaPeriodosAcompanhamento(produto.getCodigo(),
				// acompReferencia, resultadoStatus);
				//
				// if(listaPA.size()>0){
				// produto.setPeriodoAcompanhamento(listaPA);
				//
				// AcompRelatorioDao acompRelatorioDao = new
				// AcompRelatorioDao(null);
				//
				// AcompRelatorioArel acompRelatorioArel = (AcompRelatorioArel)
				// acompRelatorioDao.buscar(AcompRelatorioArel.class,
				// listaPA.get(0).getCodArel() );
				//
				//
				// Responsavel responsavel = new Responsavel();
				//
				// responsavel.setCodigo(acompRelatorioArel.getUsuarioUsuUltimaManutencao().getCodUsu());
				// responsavel.setNome(acompRelatorioArel.getUsuarioUsuUltimaManutencao().getNomeUsu());
				//
				//
				//
				// Iterator iterator =
				// acompRelatorioArel.getUsuarioUsuUltimaManutencao().getOrgaoOrgs().iterator();
				//
				// OrgaoOrg orgaoOrg = new OrgaoOrg();
				//
				// while(iterator.hasNext()){
				// orgaoOrg = (OrgaoOrg) iterator.next();
				// }
				//
				// Orgao orgao = new Orgao();
				// orgao.setNome(orgaoOrg.getSiglaOrg()!=null?orgaoOrg.getSiglaOrg():"");
				//
				// responsavel.setOrgao(orgao);
				//
				// produto.responsavel = responsavel;
				//
				// produto.setSituacao(acompRelatorioArel.getSituacaoSit().getDescricaoSit());
				//
				// }
				// else{
				// seInseri = false;
				// AcompReferenciaDao acompReferenciaDao = new
				// AcompReferenciaDao(null);
				// AcompReferenciaAref acompReferenciaAref =
				// (AcompReferenciaAref)
				// acompReferenciaDao.buscar(AcompReferenciaAref.class,
				// acompReferencia);
				//
				// List<AcompReferenciaItemAri> listaAri =
				// (List<AcompReferenciaItemAri>)
				// acompReferenciaDao.getAcompReferenciaItemAriByAref(acompReferenciaAref.getCodAref());
				//
				// for(AcompReferenciaItemAri ari:listaAri){
				// if(ari.getItemEstruturaIett().getCodIett().longValue()==produto.getCodigo()){
				//
				// seInseri = true;
				// PeriodoAcompanhamento periodoAcompanhamento = new
				// PeriodoAcompanhamento();
				// periodoAcompanhamento.setMes(acompReferenciaAref.getMesAref());
				// periodoAcompanhamento.setAno(Integer.parseInt(acompReferenciaAref.getAnoAref()));
				// produto.addPeriodoAcompanhamento(periodoAcompanhamento);
				// break;
				//
				// }
				// }
				// }
				//
				//
				// } else {

				PeriodoAcompanhamento periodoAcompanhamento = new PeriodoAcompanhamento();

				if (obj[16] != null) {
					periodoAcompanhamento = new PeriodoAcompanhamento(0L, limpaParecer(obj[16].toString()), obj[11].toString(), obj[12].toString(), Long.parseLong(obj[5].toString()),
							obj[7].toString(), 0L);
				}
				periodoAcompanhamento.setAno(Integer.parseInt(obj[12].toString()));
				periodoAcompanhamento.setMes(obj[11].toString());

				produto.addPeriodosAcompanhamento(periodoAcompanhamento);
				// }

				// if(seInseri)
				mapaProduto.put(obj[2].toString() + "_" + obj[1].toString(), produto);

			}

			if (obj[4].toString().equals("5")) {
				Acao acao = new Acao(Long.parseLong(obj[0].toString()), buscarNomeIettHistorico(obj[3].toString(), Long.parseLong(obj[0].toString()), exercicio), Long.parseLong(obj[1].toString()),
						obj[2].toString());
				if (obj[19] != null)
					acao.setSituacao(obj[19].toString());
				mapaAcao.put(Long.parseLong(obj[0].toString()), acao);
			}

		}

		List<Produto> listaProduto = populaAcaoComIndicador(mapaProduto, mapaAcao);

		List<Resultado> listaResultado = populaProduto(mapaResultado, listaProduto);

		List<Estrategia> listaEstrategia = populaResultadoComIndicador(mapaEstrategia, listaResultado, isIndicador, apenasParecer, relEncaminhamento, dataInicial, dataFinal, somenteREM,
				isDetalhamento);

		populaEstrategia(mapaObjetivo, listaEstrategia);

		return new ArrayList<ObjetivoEstrategico>(mapaObjetivo.values());

		// -->
		// List<Produto> listaProduto = populaAcaoComIndicador(mapaProduto,
		// mapaAcao);
		//
		// List<Resultado> listaResultado = populaProduto(mapaResultado,
		// listaProduto);
		//
		// List<Estrategia> listaEstrategia =
		// populaResultadoComIndicador(mapaEstrategia, listaResultado,
		// isIndicador, apenasParecer, relEncaminhamento, dataInicial,
		// dataFinal);
		//
		// populaEstrategia(mapaObjetivo, listaEstrategia);
		//
		// return new ArrayList<ObjetivoEstrategico>(mapaObjetivo.values());
		// -->

	}

	private void queryListaObjetivoEstrategicoFiltroMaisRapidoEuEspero(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, StringBuilder hql, boolean prioritario, List<Long> secretarias,
			List<Integer> resultadoStatus, Long exercicio) {

		queryRecursivoFiltro(codigoObjetivoEstrategico, hql);

		hql.append(" SELECT distinct ir.cod_iett," + "	   ir.cod_iett_pai," + "       ir.sigla_iett," + "       ir.nome_iett," + "       ir.nivel_iett," + "       arel.cod_cor,"
				+ "       cor.nome_cor," + "       cor.significado_cor," + "       arel.data_ult_manut_arel," + "       NULL as usu," + "       0 as codResponsavel," + "	  	NULL as mes,"
				+ "       NULL as ano," + "		NULL as sigla_orgao," + "		0 as prioritario," + "		ir.atencao_iett as atencao," + "		NULL as parecer," + "		ir.data_inicio_iett as dataInicio,"
				+ "		ir.data_termino_iett as dataTermino," + "		sit.descricao_sit as situacao," + "		aref.cod_aref as codAref" + " FROM iett_recursivo ir"
				+ " LEFT JOIN rel_itemestrutiett_etiqueta rie ON rie.cod_iett = ir.cod_iett" + " LEFT JOIN tb_etiqueta te ON te.cod_etiqueta = rie.cod_etiqueta"
				+ " LEFT JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = ir.cod_iett" + " LEFT JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari"
				+ " LEFT JOIN tb_cor cor ON cor.cod_cor = arel.cod_cor" + " LEFT JOIN tb_acomp_referencia_aref aref ON aref.cod_aref = ari.cod_aref"
				+ " LEFT JOIN tb_situacao_sit sit ON sit.cod_sit = ir.cod_sit" + " WHERE ir.nivel_iett IN (1,2)");

		Boolean somenteNaoMonitorados = false;

		if (resultadoStatus != null && resultadoStatus.size() == 1 && resultadoStatus.contains(0))
			somenteNaoMonitorados = true;

		if (!somenteNaoMonitorados) {
			/* SOMENTE MONITORADOS */
			queryConsultaItensMonitorados(etiqueta, hql, prioritario, secretarias, resultadoStatus);

		}

		if ((resultadoStatus != null && resultadoStatus.size() > 0 && resultadoStatus.contains(0)) || (resultadoStatus != null && resultadoStatus.size() <= 0) || (resultadoStatus == null)) {
			/* NÃO MONITORADOS */
			queryConsultaItensNaoMonitorados(etiqueta, hql, prioritario, secretarias);

		}

		hql.append(" ORDER BY cod_iett, cod_iett_pai , nivel_iett");

	}

	private void queryConsultaItensNaoMonitorados(List<String> etiqueta, StringBuilder hql, boolean prioritario, List<Long> secretarias) {
		hql.append(" UNION ALL " + " SELECT distinct ir.cod_iett," + "	   ir.cod_iett_pai," + "       ir.sigla_iett," + "       ir.nome_iett," + "       ir.nivel_iett," + "       arel.cod_cor,"
				+ "       cor.nome_cor," + "       cor.significado_cor," + "       arel.data_ult_manut_arel," + "	   CASE" + "          WHEN usu.nome_usu ISNULL  THEN (select"
				+ "              usuUtfa.nome_usu" + "          from" + "              TB_ITEM_EST_USUTPFUAC_IETTUTFA utfa" + "          inner join" + "              tb_usuario_usu usuUtfa"
				+ "                  on usuUtfa.cod_usu = utfa.cod_usu" + "          where" + "              utfa.cod_iett = ari.cod_iett)" + "          ELSE (" + "              usu.nome_usu"
				+ "          )" + "		END as usu," + "		CASE" + "          WHEN usu.cod_usu ISNULL  THEN (select" + "              usuUtfa.cod_usu" + "          from"
				+ "              TB_ITEM_EST_USUTPFUAC_IETTUTFA utfa" + "          inner join" + "              tb_usuario_usu usuUtfa" + "                  on usuUtfa.cod_usu = utfa.cod_usu"
				+ "          where" + "              utfa.cod_iett = ari.cod_iett)" + "          ELSE (" + "              usu.cod_usu" + "          )" + "		END as codResponsavel,"
				+ "        aref.mes_aref," + "        aref.ano_aref," + "		CASE " + "		WHEN usu.nome_usu ISNULL  THEN ( select" + "    		o.sigla_org" + "			from" + "	    	tb_usuario_usu u "
				+ "			inner join" + "	    	TB_USUARIO_ORGAO_USUORG uo " + "	    	on u.cod_usu = uo.cod_usu " + "			inner join" + "	    	TB_ORGAO_ORG o " + "       	on o.cod_org = uo.cod_org "
				+ "			inner join" + "	    	TB_ITEM_EST_USUTPFUAC_IETTUTFA utfa " + "       	on u.cod_usu = utfa.cod_usu " + "			where" + "	    	utfa.cod_iett = ari.cod_iett LIMIT 1) " + "			ELSE ("
				+ "	    		o.sigla_org" + "			) " + "			END as orgao, 	" + "		prioritario.cod_atb as prioritario," + "		ir.atencao_iett as atencao," + "		arel.descricao_arel as parecer,"
				+ "		ir.data_inicio_iett as dataInicio," + "		ir.data_termino_iett as dataTermino," + "		sit.descricao_sit as situacao," + "		aref.cod_aref as codAref" + " FROM iett_recursivo ir"
				+ " LEFT JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = ir.cod_iett" + " LEFT JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari"
				+ " LEFT JOIN tb_cor cor ON cor.cod_cor = arel.cod_cor" + " LEFT JOIN tb_acomp_referencia_aref aref ON aref.cod_aref = ari.cod_aref"
				+ " INNER JOIN tb_item_estrut_usuario_iettus perm ON ir.cod_iett = perm.cod_iett" + " INNER JOIN tb_tipo_acompanhamento_ta ta ON aref.cod_ta = ta.cod_ta"
				+ " INNER JOIN tb_tipoacomp_tipofuncacomp_sisatributo_tatpfasatb permTAGRU ON ta.COD_TA=permTAGRU.cod_ta" + " LEFT JOIN tb_usuario_usu usu ON usu.cod_usu = arel.cod_usuultmanut_arel"
				+ " LEFT JOIN tb_usuario_orgao_usuorg uo ON usu.cod_usu = uo.cod_usu" + " LEFT JOIN tb_orgao_org  o ON o.cod_org = uo.cod_org"
				+ " LEFT JOIN tb_item_estrutura_nivel_iettn prioritario ON prioritario.cod_iett = ir.cod_iett" + " LEFT JOIN tb_situacao_sit sit ON sit.cod_sit = ir.cod_sit"
				+ " ,tb_tipo_func_acomp_tpfa tpfa" + "  WHERE 	ir.nivel_iett IN(3,4,5,6) AND   (ir.cod_iett, ari.cod_ari, 0) IN(" + "			SELECT cod_iett, cod_ari, contagem " + "			FROM(	 "
				+ "			WITH todos_ietts_ari as (SELECT DISTINCT ari.cod_iett, ari.cod_ari" + "						 FROM tb_acomp_referencia_item_ari ari "
				+ " 						 INNER JOIN iett_recursivo ir ON ir.cod_iett = ari.cod_iett" + "						 INNER JOIN tb_acomp_relatorio_arel arel " + "							ON arel.cod_ari = ari.cod_ari "
				+ " 						 LEFT JOIN rel_itemestrutiett_etiqueta rie ON rie.cod_iett = ari.cod_iett" + " 						 LEFT JOIN tb_etiqueta te ON te.cod_etiqueta = rie.cod_etiqueta"
				+ "						 WHERE ((:codAref<>0 AND ari.cod_aref<=:codAref) OR ((:exercicio=0 AND :codAref=0) OR ( :exercicio<>0 AND :codAref=0 AND " + "						      arel.data_inclusao_arel"
				+ "						      BETWEEN (SELECT" + "							    data_inicial_exe" + "							FROM" + "							    tb_exercicio_exe" + "							WHERE" + "							    cod_exe=:exercicio) AND (SELECT"
				+ "							    data_final_exe " + "							FROM" + "							    tb_exercicio_exe" + "							WHERE" + "							    cod_exe=:exercicio))))");
		/* ETIQUETA */
		if (etiqueta != null && etiqueta.size() != 0) {
			hql.append(" AND (" + "		(te.nome_etiqueta_fonetico IN(:etiquetas))  AND (rie.ind_ativo = 'S')" + "	)");
		}

		/* FIM ETIQUETA */
		/* POR SECRETARIA */
		if (secretarias != null && secretarias.size() != 0) {
			hql.append(" AND ((ir.nivel_iett = 3 AND ir.cod_orgao_responsavel1_iett IN(:secretarias)) OR ir.nivel_iett>3)");
		}
		/* FIM SECRETARIA */
		/* SE PRIORITARIO */
		if (prioritario) {
			hql.append(" AND (" + "	(ir.nivel_iett = 3 AND ir.cod_iett IN(SELECT cod_iett FROM tb_item_estrutura_nivel_iettn))" + "	OR (ir.nivel_iett > 3) " + ")");
		}
		/* FIM PRIORITARIO */
		hql.append("						), " + "			     totais_por_iett as (SELECT ari.cod_iett, COUNT(arel.cod_arel) as total" + "						 FROM tb_acomp_referencia_item_ari ari	 "
				+ " 						 INNER JOIN iett_recursivo ir ON ir.cod_iett = ari.cod_iett" + "						 INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari"
				+ " 						 LEFT JOIN rel_itemestrutiett_etiqueta rie ON rie.cod_iett = ari.cod_iett" + " 						 LEFT JOIN tb_etiqueta te ON te.cod_etiqueta = rie.cod_etiqueta"
				+ "						 WHERE ( (:codAref<>0 AND ari.cod_aref<=:codAref) OR ((:exercicio=0 AND :codAref=0) OR (:exercicio<>0 AND :codAref=0 AND arel.data_ult_manut_arel"
				+ "						      BETWEEN (SELECT" + "							    data_inicial_exe" + "							FROM" + "							    tb_exercicio_exe" + "							WHERE" + "							    cod_exe=:exercicio) AND (SELECT"
				+ "							    data_final_exe " + "							FROM" + "							    tb_exercicio_exe" + "							WHERE" + "							    cod_exe=:exercicio) ) ))" + "						 AND arel.cod_cor IS NOT NULL"
				+ "						 AND arel.data_ult_manut_arel IS NOT NULL");
		/* ETIQUETA */
		if (etiqueta != null && etiqueta.size() != 0) {
			hql.append(" AND (" + "		(te.nome_etiqueta_fonetico IN(:etiquetas)  AND (rie.ind_ativo = 'S'))" + "	)");
		}

		/* FIM ETIQUETA */
		/* POR SECRETARIA */
		if (secretarias != null && secretarias.size() != 0) {
			hql.append(" AND ((ir.nivel_iett = 3 AND ir.cod_orgao_responsavel1_iett IN(:secretarias)) OR ir.nivel_iett>3)");
		}
		/* FIM SECRETARIA */
		/* SE PRIORITARIO */
		if (prioritario) {
			hql.append(" AND (" + "	(ir.nivel_iett = 3 AND ir.cod_iett IN(SELECT cod_iett FROM tb_item_estrutura_nivel_iettn))" + "	OR (ir.nivel_iett > 3) " + ")");
		}
		/* FIM PRIORITARIO */
		hql.append("						 GROUP BY 1"
				+ "						 ) "
				+ "			SELECT tia.cod_iett, tia.cod_ari, COALESCE(tpi.total,0) as contagem"
				+ "			FROM todos_ietts_ari tia "
				+ "			LEFT JOIN totais_por_iett tpi ON tpi.cod_iett = tia.cod_iett"
				+ "			) AS NAOMONITORADOS)"
				+ "   AND (TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY')) IN (SELECT MAX(TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY'))"
				+ "								 FROM tb_acomp_referencia_aref arefFiltro "
				+ "								 WHERE ( (:codAref<>0 AND arefFiltro.cod_aref<=:codAref) OR ((:exercicio=0 AND :codAref=0) OR (:exercicio<>0 AND :codAref=0 AND TO_DATE(arefFiltro.mes_aref||'/'||arefFiltro.ano_aref, 'MM/YYYY')"
				+ "								 BETWEEN (SELECT" + "							    data_inicial_exe" + "							FROM" + "							    tb_exercicio_exe" + "							WHERE" + "							    cod_exe=:exercicio) AND (SELECT"
				+ "							    data_final_exe " + "							FROM" + "							    tb_exercicio_exe" + "							WHERE" + "							    cod_exe=:exercicio) ) ) )" + "								 ) 			" + " AND ("
				+ "    perm.IND_EMITE_POS_IETTUS='S'" + "    OR perm.IND_LEITURA_PARECER_IETTUS='S'" + "    OR perm.IND_INF_ANDAMENTO_IETTUS='S'" + "	)" + " AND (" + "    	(" + "          ("
				+ "          perm.COD_USU=:codUsu" + "          OR perm.COD_ATB IN (" + "              :gruposUsuarios" + "              )" + "          )"
				+ "          AND permTAGRU.cod_tpfa=tpfa.COD_TPFA" + "          AND permTAGRU.ind_leitura_parecer='S'" + "          AND tpfa.IND_EMITE_POSICAO_TPFA='S'" + "          AND ("
				+ "          permTAGRU.cod_satb IN (" + "              :gruposUsuarios" + "              )" + "          )" + "   		)" + "	)" + " AND ir.nivel_iett NOT IN(1,2)" + "  ");
	}

	private void queryConsultaItensMonitorados(List<String> etiqueta, StringBuilder hql, boolean prioritario, List<Long> secretarias, List<Integer> resultadoStatus) {
		hql.append(" UNION ALL"
				+ " SELECT distinct ir.cod_iett,"
				+ "	   ir.cod_iett_pai,"
				+ "       ir.sigla_iett,"
				+ "       ir.nome_iett,"
				+ "       ir.nivel_iett,"
				+ "       arel.cod_cor,"
				+ "       cor.nome_cor,"
				+ "       cor.significado_cor,"
				+ "       arel.data_ult_manut_arel,"
				+ "	   CASE"
				+ "          WHEN usu.nome_usu ISNULL  THEN (select"
				+ "              usuUtfa.nome_usu"
				+ "          from"
				+ "              TB_ITEM_EST_USUTPFUAC_IETTUTFA utfa"
				+ "          inner join"
				+ "              tb_usuario_usu usuUtfa"
				+ "                  on usuUtfa.cod_usu = utfa.cod_usu"
				+ "          where"
				+ "              utfa.cod_iett = ari.cod_iett)"
				+ "          ELSE ("
				+ "              usu.nome_usu"
				+ "          )"
				+ "		END as usu,"
				+ "		CASE"
				+ "          WHEN usu.cod_usu ISNULL  THEN (select"
				+ "              usuUtfa.cod_usu"
				+ "          from"
				+ "              TB_ITEM_EST_USUTPFUAC_IETTUTFA utfa"
				+ "          inner join"
				+ "              tb_usuario_usu usuUtfa"
				+ "                  on usuUtfa.cod_usu = utfa.cod_usu"
				+ "          where"
				+ "              utfa.cod_iett = ari.cod_iett)"
				+ "          ELSE ("
				+ "              usu.cod_usu"
				+ "          )"
				+ "		END as codResponsavel,"
				+ "        aref.mes_aref,"
				+ "        aref.ano_aref,"
				+ "		CASE "
				+ "		WHEN usu.nome_usu ISNULL  THEN ( select"
				+ "    		o.sigla_org"
				+ "			from"
				+ "	    	tb_usuario_usu u "
				+ "			inner join"
				+ "	    	TB_USUARIO_ORGAO_USUORG uo "
				+ "	    	on u.cod_usu = uo.cod_usu "
				+ "			inner join"
				+ "	    	TB_ORGAO_ORG o "
				+ "       	on o.cod_org = uo.cod_org "
				+ "			inner join"
				+ "	    	TB_ITEM_EST_USUTPFUAC_IETTUTFA utfa "
				+ "       	on u.cod_usu = utfa.cod_usu "
				+ "			where"
				+ "	    	utfa.cod_iett = ari.cod_iett LIMIT 1) "
				+ "			ELSE ("
				+ "	    		o.sigla_org"
				+ "			) "
				+ "			END as orgao, 	"
				+ "		prioritario.cod_atb as prioritario,"
				+ "		ir.atencao_iett as atencao,"
				+ "		arel.descricao_arel as parecer,"
				+ "		ir.data_inicio_iett as dataInicio,"
				+ "		ir.data_termino_iett as dataTermino,"
				+ "		sit.descricao_sit as situacao,"
				+ "		aref.cod_aref as codAref"
				+ " FROM iett_recursivo ir"
				+ " LEFT JOIN rel_itemestrutiett_etiqueta rie ON rie.cod_iett = ir.cod_iett"
				+ " LEFT JOIN tb_etiqueta te ON te.cod_etiqueta = rie.cod_etiqueta"
				+ " INNER JOIN tb_acomp_referencia_item_ari ari ON ari.cod_iett = ir.cod_iett"
				+ " INNER JOIN tb_acomp_relatorio_arel arel ON arel.cod_ari = ari.cod_ari"
				+ " INNER JOIN tb_cor cor ON cor.cod_cor = arel.cod_cor"
				+ " INNER JOIN tb_acomp_referencia_aref aref ON aref.cod_aref = ari.cod_aref"
				+ " INNER JOIN tb_item_estrut_usuario_iettus perm ON ir.cod_iett = perm.cod_iett"
				+ " INNER JOIN tb_tipo_acompanhamento_ta ta ON aref.cod_ta = ta.cod_ta"
				+ " INNER JOIN tb_tipoacomp_tipofuncacomp_sisatributo_tatpfasatb permTAGRU ON ta.COD_TA=permTAGRU.cod_ta"
				+ " LEFT JOIN tb_usuario_usu usu ON usu.cod_usu = arel.cod_usuultmanut_arel"
				+ " LEFT JOIN tb_usuario_orgao_usuorg uo ON usu.cod_usu = uo.cod_usu"
				+ " LEFT JOIN tb_orgao_org  o ON o.cod_org = uo.cod_org"
				+ " LEFT JOIN tb_item_estrutura_nivel_iettn prioritario ON prioritario.cod_iett = ir.cod_iett"
				+ " LEFT JOIN tb_situacao_sit sit ON sit.cod_sit = arel.cod_sit"
				+ " ,tb_tipo_func_acomp_tpfa tpfa"
				+ "   WHERE"
				+ "    ("
				+ "        ("
				+ "            ir.nivel_iett IN(3,4,5,6)"
				+ "            AND (ir.cod_iett, arel.data_ult_manut_arel) IN ("
				+ "                SELECT"
				+ "                    ari.cod_iett,"
				+ "                    MAX(arel.data_ult_manut_arel)"
				+ "                FROM"
				+ "                    tb_acomp_relatorio_arel arel"
				+ "                INNER JOIN"
				+ "                    tb_acomp_referencia_item_ari ari"
				+ "                        ON  ari.cod_ari = arel.cod_ari"
				+ "                WHERE ( (:codAref<>0 AND ari.cod_aref<=:codAref) OR ((:exercicio=0 AND :codAref=0)  OR ( :exercicio<>0 AND :codAref=0 AND arel.data_ult_manut_arel BETWEEN (SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) AND (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio))))"
				+ "                GROUP BY" + "                    ari.cod_iett)" + "                    AND arel.cod_tpfa in (3,6,7,14) )" + "     )" + " AND ("
				+ "    perm.IND_EMITE_POS_IETTUS='S'" + "    OR perm.IND_LEITURA_PARECER_IETTUS='S'" + "    OR perm.IND_INF_ANDAMENTO_IETTUS='S'" + "	)" + " AND (" + "    	(" + "          ("
				+ "          perm.COD_USU=:codUsu" + "          OR perm.COD_ATB IN (" + "              :gruposUsuarios" + "              )" + "          )"
				+ "          AND permTAGRU.cod_tpfa=tpfa.COD_TPFA" + "          AND permTAGRU.ind_leitura_parecer='S'" + "          AND tpfa.IND_EMITE_POSICAO_TPFA='S'" + "          AND ("
				+ "          permTAGRU.cod_satb IN (" + "              :gruposUsuarios" + "              )" + "          )" + "   		)" + "	)" + " AND ir.nivel_iett NOT IN(1,2)" + "  ");
		/* POR SECRETARIA */
		if (secretarias != null && secretarias.size() != 0) {
			hql.append(" AND ((ir.nivel_iett = 3 AND ir.cod_orgao_responsavel1_iett IN(:secretarias)) OR ir.nivel_iett>3)");
		}
		/* FIM SECRETARIA */
		/* SE PRIORITARIO */
		if (prioritario) {
			hql.append(" AND (" + "	(ir.nivel_iett = 3 AND ir.cod_iett IN(SELECT cod_iett FROM tb_item_estrutura_nivel_iettn))" + "	OR (ir.nivel_iett > 3) " + ")");
		}
		/* FIM PRIORITARIO */
		/* ETIQUETA */

		if (etiqueta != null && etiqueta.size() != 0) {
			hql.append(" AND (" + "		(te.nome_etiqueta_fonetico IN(:etiquetas)) AND (rie.ind_ativo = 'S')" + "	)");
		}

		/* FIM ETIQUETA */
		/* FIlTRO STATUS */
		if (resultadoStatus != null && resultadoStatus.size() > 0) {

			hql.append(" AND (" + "	(ir.nivel_iett = 3 AND arel.cod_cor IN(:status))" + "	OR (ir.nivel_iett > 3)" + ")");

		}
		/* FIM FILTRO STATUS */
	}

	public void queryRecursivoFiltro(List<Integer> codigoObjetivoEstrategico, StringBuilder hql) {
		hql.append("WITH RECURSIVE iett_recursivo(" +

		"cod_iett," + "cod_are," + "cod_sare," + "val_previsto_futuro_iett," + "ind_bloq_planejamento_iett," + "beneficios_iett," + "origem_iett," + "objetivo_especifico_iett,"
				+ "objetivo_geral_iett," + "ind_monitoramento_iett," + "ind_critica_iett," + "data_inicio_monitoramento_iett," + "data_termino_iett," + "data_inicio_iett," + "ind_ativo_iett,"
				+ "data_ult_manutencao_iett," + "data_inclusao_iett," + "descricao_iett," + "sigla_iett," + "cod_usu_inc_iett," + "cod_usu_ult_manut_iett," + "cod_orgao_responsavel1_iett,"
				+ "cod_orgao_responsavel2_iett," + "cod_iett_pai," + "cod_prcd_iett," + "cod_ett," + "descricao_r5," + "descricao_r4," + "descricao_r3," + "descricao_r2," + "descricao_r1,"
				+ "data_r5," + "data_r4," + "data_r3," + "data_r2," + "data_r1," + "nome_iett," + "cod_uo," + "cod_sit," + "nivel_iett," + "ind_exclusao_pos_historico," + "ind_modelo_iett,"
				+ "atencao_iett" +

				")" +

				" AS(" + " SELECT" + "  cod_iett," + "  cod_are," + "  cod_sare," + "  val_previsto_futuro_iett," + "  ind_bloq_planejamento_iett," + "  beneficios_iett," + "  origem_iett,"
				+ "  objetivo_especifico_iett," + "  objetivo_geral_iett," + "  ind_monitoramento_iett," + "  ind_critica_iett," + "  data_inicio_monitoramento_iett," + "  data_termino_iett,"
				+ "  data_inicio_iett," + "  ind_ativo_iett," + "  data_ult_manutencao_iett," + "  data_inclusao_iett," + "  descricao_iett," + "  sigla_iett," + "  cod_usu_inc_iett,"
				+ "  cod_usu_ult_manut_iett," + "  cod_orgao_responsavel1_iett," + "  cod_orgao_responsavel2_iett," + "  cod_iett_pai," + "  cod_prcd_iett," + "  cod_ett," + "  descricao_r5,"
				+ "  descricao_r4," + "  descricao_r3," + "  descricao_r2," + "  descricao_r1," + "  data_r5," + "  data_r4," + "  data_r3," + "  data_r2," + "  data_r1," + "  nome_iett,"
				+ "  cod_uo," + "  cod_sit," + "  nivel_iett," + "  ind_exclusao_pos_historico," + "  ind_modelo_iett," + "  atencao_iett" + " FROM" + "  public.tb_item_estrutura_iett");

		if (codigoObjetivoEstrategico.size() != 0) {
			hql.append(" WHERE cod_iett IN (:codObj) ");
		} else {
			hql.append(" WHERE cod_iett IN ( ");
			hql.append("select iettObjs.cod_iett from tb_item_estrutura_iett iettObjs ");
			hql.append("where iettObjs.nivel_iett = 1 and iettObjs.ind_ativo_iett='S') ");
		}

		hql.append(" " + " AND ind_ativo_iett = 'S'" +

		" UNION ALL" +

		" SELECT" + "  iett.cod_iett," + "  iett.cod_are," + "  iett.cod_sare," + "  iett.val_previsto_futuro_iett," + "  iett.ind_bloq_planejamento_iett," + "  iett.beneficios_iett,"
				+ "  iett.origem_iett," + "  iett.objetivo_especifico_iett," + "  iett.objetivo_geral_iett," + "  iett.ind_monitoramento_iett," + "  iett.ind_critica_iett,"
				+ "  iett.data_inicio_monitoramento_iett," + "  iett.data_termino_iett," + "  iett.data_inicio_iett," + "  iett.ind_ativo_iett," + "  iett.data_ult_manutencao_iett,"
				+ "  iett.data_inclusao_iett," + "  iett.descricao_iett," + "  iett.sigla_iett," + "  iett.cod_usu_inc_iett," + "  iett.cod_usu_ult_manut_iett,"
				+ "  iett.cod_orgao_responsavel1_iett," + "  iett.cod_orgao_responsavel2_iett," + "  iett.cod_iett_pai," + "  iett.cod_prcd_iett," + "  iett.cod_ett," + "  iett.descricao_r5,"
				+ "  iett.descricao_r4," + "  iett.descricao_r3," + "  iett.descricao_r2," + "  iett.descricao_r1," + "  iett.data_r5," + "  iett.data_r4," + "  iett.data_r3," + "  iett.data_r2,"
				+ "  iett.data_r1," + "  iett.nome_iett," + "  iett.cod_uo," + "  iett.cod_sit," + "  iett.nivel_iett," + "  iett.ind_exclusao_pos_historico," + "  iett.ind_modelo_iett,"
				+ "  iett.atencao_iett" + " FROM" + "  public.tb_item_estrutura_iett iett" + "  INNER JOIN iett_recursivo ON iett.cod_iett_pai =  iett_recursivo.cod_iett"
				+ " WHERE iett.ind_ativo_iett = 'S'" +

				")");
	}

	private void queryListaItensRecursivoEtiquetasFilhos(Long codigoItem, Long codigoEtiqueta, StringBuilder hql) {

		hql.append("WITH RECURSIVE iett_recursivo(" +

		"cod_iett," + "cod_are," + "cod_sare," + "val_previsto_futuro_iett," + "ind_bloq_planejamento_iett," + "beneficios_iett," + "origem_iett," + "objetivo_especifico_iett,"
				+ "objetivo_geral_iett," + "ind_monitoramento_iett," + "ind_critica_iett," + "data_inicio_monitoramento_iett," + "data_termino_iett," + "data_inicio_iett," + "ind_ativo_iett,"
				+ "data_ult_manutencao_iett," + "data_inclusao_iett," + "descricao_iett," + "sigla_iett," + "cod_usu_inc_iett," + "cod_usu_ult_manut_iett," + "cod_orgao_responsavel1_iett,"
				+ "cod_orgao_responsavel2_iett," + "cod_iett_pai," + "cod_prcd_iett," + "cod_ett," + "descricao_r5," + "descricao_r4," + "descricao_r3," + "descricao_r2," + "descricao_r1,"
				+ "data_r5," + "data_r4," + "data_r3," + "data_r2," + "data_r1," + "nome_iett," + "cod_uo," + "cod_sit," + "nivel_iett," + "ind_exclusao_pos_historico," + "ind_modelo_iett,"
				+ "atencao_iett" +

				")" +

				" AS(" + " SELECT" + "  cod_iett," + "  cod_are," + "  cod_sare," + "  val_previsto_futuro_iett," + "  ind_bloq_planejamento_iett," + "  beneficios_iett," + "  origem_iett,"
				+ "  objetivo_especifico_iett," + "  objetivo_geral_iett," + "  ind_monitoramento_iett," + "  ind_critica_iett," + "  data_inicio_monitoramento_iett," + "  data_termino_iett,"
				+ "  data_inicio_iett," + "  ind_ativo_iett," + "  data_ult_manutencao_iett," + "  data_inclusao_iett," + "  descricao_iett," + "  sigla_iett," + "  cod_usu_inc_iett,"
				+ "  cod_usu_ult_manut_iett," + "  cod_orgao_responsavel1_iett," + "  cod_orgao_responsavel2_iett," + "  cod_iett_pai," + "  cod_prcd_iett," + "  cod_ett," + "  descricao_r5,"
				+ "  descricao_r4," + "  descricao_r3," + "  descricao_r2," + "  descricao_r1," + "  data_r5," + "  data_r4," + "  data_r3," + "  data_r2," + "  data_r1," + "  nome_iett,"
				+ "  cod_uo," + "  cod_sit," + "  nivel_iett," + "  ind_exclusao_pos_historico," + "  ind_modelo_iett," + "  atencao_iett" + " FROM" + "  public.tb_item_estrutura_iett"
				+ " WHERE cod_iett = :codigoItem " + " AND ind_ativo_iett = 'S'" +

				" UNION ALL" +

				" SELECT" + "  iett.cod_iett," + "  iett.cod_are," + "  iett.cod_sare," + "  iett.val_previsto_futuro_iett," + "  iett.ind_bloq_planejamento_iett," + "  iett.beneficios_iett,"
				+ "  iett.origem_iett," + "  iett.objetivo_especifico_iett," + "  iett.objetivo_geral_iett," + "  iett.ind_monitoramento_iett," + "  iett.ind_critica_iett,"
				+ "  iett.data_inicio_monitoramento_iett," + "  iett.data_termino_iett," + "  iett.data_inicio_iett," + "  iett.ind_ativo_iett," + "  iett.data_ult_manutencao_iett,"
				+ "  iett.data_inclusao_iett," + "  iett.descricao_iett," + "  iett.sigla_iett," + "  iett.cod_usu_inc_iett," + "  iett.cod_usu_ult_manut_iett,"
				+ "  iett.cod_orgao_responsavel1_iett," + "  iett.cod_orgao_responsavel2_iett," + "  iett.cod_iett_pai," + "  iett.cod_prcd_iett," + "  iett.cod_ett," + "  iett.descricao_r5,"
				+ "  iett.descricao_r4," + "  iett.descricao_r3," + "  iett.descricao_r2," + "  iett.descricao_r1," + "  iett.data_r5," + "  iett.data_r4," + "  iett.data_r3," + "  iett.data_r2,"
				+ "  iett.data_r1," + "  iett.nome_iett," + "  iett.cod_uo," + "  iett.cod_sit," + "  iett.nivel_iett," + "  iett.ind_exclusao_pos_historico," + "  iett.ind_modelo_iett,"
				+ "  iett.atencao_iett" + " FROM" + "  public.tb_item_estrutura_iett iett" + "  INNER JOIN iett_recursivo ON iett.cod_iett_pai =  iett_recursivo.cod_iett"
				+ " WHERE iett.ind_ativo_iett = 'S'" +

				")" + " SELECT distinct " + "		ir.cod_iett," + "	    ir.cod_iett_pai," + "       ir.sigla_iett," + "       ir.nome_iett," + "       ir.nivel_iett,"
				+ "       rie.cod_itemestrutiett_etiq" + " FROM iett_recursivo ir" + " INNER JOIN rel_itemestrutiett_etiqueta rie ON rie.cod_iett = ir.cod_iett"
				+ " INNER JOIN tb_etiqueta e ON e.cod_etiqueta = rie.cod_etiqueta" + " WHERE e.cod_etiqueta=:codigoEtiqueta" + " ORDER BY cod_iett, cod_iett_pai , nivel_iett");

	}

	public List<Object[]> listaRelacionamentoEtiquetaFilhos(Long codigoIett, Long codigoEtiqueta) {

		StringBuilder hql = new StringBuilder();
		queryListaItensRecursivoEtiquetasFilhos(codigoIett, codigoEtiqueta, hql);

		SQLQuery q = this.session.createSQLQuery(hql.toString());

		q.setParameter("codigoItem", codigoIett);
		q.setParameter("codigoEtiqueta", codigoEtiqueta);

		List<Object[]> lista = q.list();

		return lista;
	}

	private void queryListaItensRecursivoEtiquetasPais(Long codigoItem, Long codigoEtiqueta, StringBuilder hql) {

		hql.append("WITH RECURSIVE iett_recursivo(" +

		"cod_iett," + "cod_are," + "cod_sare," + "val_previsto_futuro_iett," + "ind_bloq_planejamento_iett," + "beneficios_iett," + "origem_iett," + "objetivo_especifico_iett,"
				+ "objetivo_geral_iett," + "ind_monitoramento_iett," + "ind_critica_iett," + "data_inicio_monitoramento_iett," + "data_termino_iett," + "data_inicio_iett," + "ind_ativo_iett,"
				+ "data_ult_manutencao_iett," + "data_inclusao_iett," + "descricao_iett," + "sigla_iett," + "cod_usu_inc_iett," + "cod_usu_ult_manut_iett," + "cod_orgao_responsavel1_iett,"
				+ "cod_orgao_responsavel2_iett," + "cod_iett_pai," + "cod_prcd_iett," + "cod_ett," + "descricao_r5," + "descricao_r4," + "descricao_r3," + "descricao_r2," + "descricao_r1,"
				+ "data_r5," + "data_r4," + "data_r3," + "data_r2," + "data_r1," + "nome_iett," + "cod_uo," + "cod_sit," + "nivel_iett," + "ind_exclusao_pos_historico," + "ind_modelo_iett,"
				+ "atencao_iett" +

				")" +

				" AS(" + " SELECT" + "  cod_iett," + "  cod_are," + "  cod_sare," + "  val_previsto_futuro_iett," + "  ind_bloq_planejamento_iett," + "  beneficios_iett," + "  origem_iett,"
				+ "  objetivo_especifico_iett," + "  objetivo_geral_iett," + "  ind_monitoramento_iett," + "  ind_critica_iett," + "  data_inicio_monitoramento_iett," + "  data_termino_iett,"
				+ "  data_inicio_iett," + "  ind_ativo_iett," + "  data_ult_manutencao_iett," + "  data_inclusao_iett," + "  descricao_iett," + "  sigla_iett," + "  cod_usu_inc_iett,"
				+ "  cod_usu_ult_manut_iett," + "  cod_orgao_responsavel1_iett," + "  cod_orgao_responsavel2_iett," + "  cod_iett_pai," + "  cod_prcd_iett," + "  cod_ett," + "  descricao_r5,"
				+ "  descricao_r4," + "  descricao_r3," + "  descricao_r2," + "  descricao_r1," + "  data_r5," + "  data_r4," + "  data_r3," + "  data_r2," + "  data_r1," + "  nome_iett,"
				+ "  cod_uo," + "  cod_sit," + "  nivel_iett," + "  ind_exclusao_pos_historico," + "  ind_modelo_iett," + "  atencao_iett" + " FROM" + "  public.tb_item_estrutura_iett"
				+ " WHERE cod_iett = :codigoItem " + " AND ind_ativo_iett = 'S'" +

				" UNION ALL" +

				" SELECT" + "  iett.cod_iett," + "  iett.cod_are," + "  iett.cod_sare," + "  iett.val_previsto_futuro_iett," + "  iett.ind_bloq_planejamento_iett," + "  iett.beneficios_iett,"
				+ "  iett.origem_iett," + "  iett.objetivo_especifico_iett," + "  iett.objetivo_geral_iett," + "  iett.ind_monitoramento_iett," + "  iett.ind_critica_iett,"
				+ "  iett.data_inicio_monitoramento_iett," + "  iett.data_termino_iett," + "  iett.data_inicio_iett," + "  iett.ind_ativo_iett," + "  iett.data_ult_manutencao_iett,"
				+ "  iett.data_inclusao_iett," + "  iett.descricao_iett," + "  iett.sigla_iett," + "  iett.cod_usu_inc_iett," + "  iett.cod_usu_ult_manut_iett,"
				+ "  iett.cod_orgao_responsavel1_iett," + "  iett.cod_orgao_responsavel2_iett," + "  iett.cod_iett_pai," + "  iett.cod_prcd_iett," + "  iett.cod_ett," + "  iett.descricao_r5,"
				+ "  iett.descricao_r4," + "  iett.descricao_r3," + "  iett.descricao_r2," + "  iett.descricao_r1," + "  iett.data_r5," + "  iett.data_r4," + "  iett.data_r3," + "  iett.data_r2,"
				+ "  iett.data_r1," + "  iett.nome_iett," + "  iett.cod_uo," + "  iett.cod_sit," + "  iett.nivel_iett," + "  iett.ind_exclusao_pos_historico," + "  iett.ind_modelo_iett,"
				+ "  iett.atencao_iett" + " FROM" + "  public.tb_item_estrutura_iett iett" + "  INNER JOIN iett_recursivo ON iett.cod_iett =  iett_recursivo.cod_iett_pai"
				+ " WHERE iett.ind_ativo_iett = 'S'" +

				")" + " SELECT distinct " + "		ir.cod_iett," + "	    ir.cod_iett_pai," + "       ir.sigla_iett," + "       ir.nome_iett," + "       ir.nivel_iett,"
				+ "       rie.cod_itemestrutiett_etiq" + " FROM iett_recursivo ir" + " INNER JOIN rel_itemestrutiett_etiqueta rie ON rie.cod_iett = ir.cod_iett"
				+ " INNER JOIN tb_etiqueta e ON e.cod_etiqueta = rie.cod_etiqueta" + " WHERE e.cod_etiqueta=:codigoEtiqueta" + " ORDER BY ir.nivel_iett DESC");

	}

	public List<Object[]> listaRelacionamentoEtiquetaPais(Long codigoIett, Long codigoEtiqueta) {

		StringBuilder hql = new StringBuilder();
		queryListaItensRecursivoEtiquetasPais(codigoIett, codigoEtiqueta, hql);

		SQLQuery q = this.session.createSQLQuery(hql.toString());

		q.setParameter("codigoItem", codigoIett);
		q.setParameter("codigoEtiqueta", codigoEtiqueta);

		List<Object[]> lista = q.list();

		return lista;
	}

	public List<Object[]> listarResultadoTipoItem(UsuarioUsu usuario, Long exercicio, List<Integer> codigoObjetivoEstrategico, List<Long> secretarias) {

		String sqlRecursivo = queryRecursivoEstatisticas(codigoObjetivoEstrategico);

		String sql = "select iett.nivel_iett, count(DISTINCT iett.cod_iett) FROM iett_recursivo iett WHERE iett.ind_ativo_iett = 'S' AND nivel_iett IN(1,2) GROUP BY 1 UNION ALL "
				+ "select nivel_iett, count(DISTINCT ari.cod_iett) " + "from tb_acomp_referencia_item_ari ari " + "inner join iett_recursivo iett " + "on iett.cod_iett = ari.cod_iett "
				+ "inner join " + "tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari " + "LEFT JOIN tb_acomp_referencia_aref aref ON aref.cod_aref = ari.cod_aref "
				+ "Where iett.ind_ativo_iett='S' " + "AND nivel_iett IN(3,4,5,6)";

		/* FILTRO EXERCICIO */
		// Verifica pelo exercicio o intervalo do Ciclo
		if (exercicio != null && !exercicio.equals(0L)) {
			sql += " AND TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY') BETWEEN " + "(SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) AND"
					+ " (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio)";
		} else if (exercicio == null) {
			sql += " AND TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY') BETWEEN "
					+ "(SELECT data_inicial_exe FROM tb_exercicio_exe WHERE NOW() BETWEEN data_inicial_exe AND data_final_exe) AND"
					+ " (SELECT data_final_exe FROM tb_exercicio_exe WHERE NOW() BETWEEN data_inicial_exe AND data_final_exe) ";

		} else if (exercicio != null && exercicio.equals(0L)) {
			sql += " AND TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY') " + " BETWEEN (SELECT MIN(data_inicial_exe) FROM tb_exercicio_exe)" + " AND"
					+ " (SELECT MAX(data_final_exe) FROM tb_exercicio_exe) ";
		}
		/* FIM FILTRO EXERCICIO */

		/* FIM FILTRO EXERCICIO */

		if (secretarias != null && secretarias.size() != 0) {
			sql += " AND (" + "	(iett.nivel_iett = 3 AND iett.cod_orgao_responsavel1_iett IN(:secretarias))" + "    OR "
					+ "   (iett.nivel_iett = 4 AND iett.cod_orgao_responsavel1_iett IN(:secretarias))" + ")";
		}
		/* FIM SECRETARIA */

		sql += "GROUP BY 1";

		// Query q = queryStatusOuNaoMonitorado(false);
		SQLQuery q = this.session.createSQLQuery(sqlRecursivo.toString() + sql.toString());

		if (exercicio != null && !exercicio.equals(0L)) {
			q.setParameter("exercicio", exercicio);
		}

		if (codigoObjetivoEstrategico.size() != 0) {
			q.setParameterList("codObj", codigoObjetivoEstrategico);
		}

		if (secretarias != null && secretarias.size() != 0) {
			q.setParameterList("secretarias", secretarias);
		}

		// q.setResultTransformer( Transformers.aliasToBean( Long.class ) );

		List<Object[]> totais = q.list();

		return totais;
	}

	public List<ResultadoStatusContar> listarResultadoStatusEstatistica(UsuarioUsu usuario, Long exercicio, List<Integer> codigoObjetivoEstrategico, List<Long> secretarias) {

		String sqlRecursivo = queryRecursivoEstatisticas(codigoObjetivoEstrategico);

		String sql = "select count(DISTINCT ari.cod_iett) " + "from tb_acomp_referencia_item_ari ari " + "inner join iett_recursivo iett " + "on iett.cod_iett = ari.cod_iett " + "inner join "
				+ "tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari " + "LEFT JOIN tb_acomp_referencia_aref aref ON aref.cod_aref = ari.cod_aref " + "where iett.nivel_iett = 3 "
				+ "and iett.ind_ativo_iett='S' ";

		/* FILTRO EXERCICIO */
		// Verifica pelo exercicio o intervalo do Ciclo
		if (exercicio != null && !exercicio.equals(0L)) {
			sql += " AND TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY') BETWEEN " + "(SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) AND"
					+ " (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio)";
		} else if (exercicio == null) {
			sql += " AND TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY') BETWEEN "
					+ "(SELECT data_inicial_exe FROM tb_exercicio_exe WHERE NOW() BETWEEN data_inicial_exe AND data_final_exe) AND"
					+ " (SELECT data_final_exe FROM tb_exercicio_exe WHERE NOW() BETWEEN data_inicial_exe AND data_final_exe) ";

		} else if (exercicio != null && exercicio.equals(0L)) {
			sql += " AND TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY') " + " BETWEEN (SELECT MIN(data_inicial_exe) FROM tb_exercicio_exe)" + " AND"
					+ " (SELECT MAX(data_final_exe) FROM tb_exercicio_exe) ";
		}

		/* FIM FILTRO EXERCICIO */

		if (secretarias != null && secretarias.size() != 0) {
			sql += " AND (" + "	(iett.nivel_iett = 3 AND iett.cod_orgao_responsavel1_iett IN(:secretarias))" + "    OR "
					+ "   (iett.nivel_iett = 4 AND iett.cod_orgao_responsavel1_iett IN(:secretarias))" + ")";
		}
		/* FIM SECRETARIA */

		// Query q = queryStatusOuNaoMonitorado(false);
		SQLQuery q = this.session.createSQLQuery(sqlRecursivo.toString() + sql.toString());

		if (exercicio != null && !exercicio.equals(0L)) {
			q.setParameter("exercicio", exercicio);
		}

		if (codigoObjetivoEstrategico != null && codigoObjetivoEstrategico.size() != 0) {
			q.setParameterList("codObj", codigoObjetivoEstrategico);
		}

		if (secretarias != null && secretarias.size() != 0) {
			q.setParameterList("secretarias", secretarias);
		}

		// q.setResultTransformer( Transformers.aliasToBean( Long.class ) );

		BigInteger total = (BigInteger) q.uniqueResult();

		sql = "select count (cor.cod_cor) as contar ,cor.significado_cor as nome,cor.cod_cor as codigo " + "from tb_acomp_referencia_item_ari ari " + "inner join iett_recursivo iett "
				+ "on iett.cod_iett = ari.cod_iett " + "inner join " + "tb_acomp_relatorio_arel arel " + "on ari.cod_ari = arel.cod_ari " + "inner join " + "tb_cor cor "
				+ "on arel.cod_cor = cor.cod_cor " + "LEFT JOIN tb_acomp_referencia_aref aref ON aref.cod_aref = ari.cod_aref " + "where iett.nivel_iett = 3 " + "and iett.ind_ativo_iett='S' "
				+ "and arel.data_ult_manut_arel = (" + "select " + "max(arel.data_ult_manut_arel) " + "from " + "tb_acomp_relatorio_arel arel " + "inner join " + "tb_acomp_referencia_item_ari ari "
				+ "on ari.cod_ari = arel.cod_ari " + "where " + "ari.cod_iett = iett.cod_iett " + "and arel.cod_cor is not null) ";

		/* FILTRO EXERCICIO */
		// Verifica pelo exercicio o intervalo do Ciclo
		if (exercicio != null && !exercicio.equals(0L)) {
			sql += " AND TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY') BETWEEN " + "(SELECT data_inicial_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio) AND"
					+ " (SELECT data_final_exe FROM tb_exercicio_exe WHERE cod_exe=:exercicio)";
		} else if (exercicio == null) {
			sql += " AND TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY') BETWEEN "
					+ "(SELECT data_inicial_exe FROM tb_exercicio_exe WHERE NOW() BETWEEN data_inicial_exe AND data_final_exe) AND"
					+ " (SELECT data_final_exe FROM tb_exercicio_exe WHERE NOW() BETWEEN data_inicial_exe AND data_final_exe) ";

		} else if (exercicio != null && exercicio.equals(0L)) {
			sql += " AND TO_DATE(aref.mes_aref||'/'||aref.ano_aref, 'MM/YYYY') " + " BETWEEN (SELECT MIN(data_inicial_exe) FROM tb_exercicio_exe)" + " AND"
					+ " (SELECT MAX(data_final_exe) FROM tb_exercicio_exe) ";
		}
		/* FIM FILTRO EXERCICIO */

		/* FIM FILTRO EXERCICIO */

		if (secretarias != null && secretarias.size() != 0) {
			sql += " AND (" + "	(iett.nivel_iett = 3 AND iett.cod_orgao_responsavel1_iett IN(:secretarias))" + "    OR "
					+ "   (iett.nivel_iett = 4 AND iett.cod_orgao_responsavel1_iett IN(:secretarias))" + ")";
		}
		/* FIM SECRETARIA */

		sql += " group by " + "cor.significado_cor, cor.cod_cor";

		q = this.session.createSQLQuery(sqlRecursivo.toString() + sql.toString());

		if (exercicio != null && !exercicio.equals(0L)) {
			q.setParameter("exercicio", exercicio);
		}

		if (codigoObjetivoEstrategico.size() != 0) {
			q.setParameterList("codObj", codigoObjetivoEstrategico);
		}

		if (secretarias != null && secretarias.size() != 0) {
			q.setParameterList("secretarias", secretarias);
		}

		q.setResultTransformer(Transformers.aliasToBean(ResultadoStatusContar.class));

		q.addScalar("contar", Hibernate.LONG);
		q.addScalar("codigo", Hibernate.LONG);
		q.addScalar("nome", Hibernate.STRING);

		List<ResultadoStatusContar> status = q.list();
		Long somaStatus = 0L;

		for (ResultadoStatusContar resultadoStatusContar : status) {
			somaStatus += resultadoStatusContar.getContar();
		}

		ResultadoStatusContar naoMonitorado = new ResultadoStatusContar();
		naoMonitorado.setCodigo(0L);
		naoMonitorado.setContar(total.longValue() - somaStatus);
		naoMonitorado.setNome("NAO MONITORADO");

		status.add(naoMonitorado);

		return status;
	}

	private String queryRecursivoEstatisticas(List<Integer> codigoObjetivoEstrategico) {
		String sqlRecursivo = "WITH RECURSIVE iett_recursivo(cod_iett, " + "ind_ativo_iett, " + "descricao_iett, " + "sigla_iett, " + "nome_iett, " + "cod_uo, " + "cod_sit, " + "nivel_iett, "
				+ "atencao_iett," + "cod_orgao_responsavel1_iett) " + "AS( SELECT  cod_iett, " + "ind_ativo_iett, " + "descricao_iett, " + "sigla_iett, " + "nome_iett, " + "cod_uo, " + "cod_sit, "
				+ "nivel_iett, " + "atencao_iett," + "cod_orgao_responsavel1_iett " + "FROM  public.tb_item_estrutura_iett ";
		if (codigoObjetivoEstrategico.size() != 0) {
			sqlRecursivo += "WHERE cod_iett IN(:codObj) ";
		} else {
			sqlRecursivo += "WHERE cod_iett IN(select iettObjs.cod_iett from tb_item_estrutura_iett iettObjs where iettObjs.nivel_iett = 1 and iettObjs.ind_ativo_iett='S') ";
		}
		sqlRecursivo += "AND ind_ativo_iett = 'S' " + "UNION ALL " + "SELECT  iett.cod_iett, " + "iett.ind_ativo_iett, " + "iett.descricao_iett, " + "iett.sigla_iett, " + "iett.nome_iett, "
				+ "iett.cod_uo, " + "iett.cod_sit, " + "iett.nivel_iett, " + "iett.atencao_iett," + "iett.cod_orgao_responsavel1_iett " + "FROM  public.tb_item_estrutura_iett iett "
				+ "INNER JOIN iett_recursivo ON iett.cod_iett_pai =  iett_recursivo.cod_iett " + "WHERE iett.ind_ativo_iett = 'S') ";
		return sqlRecursivo;
	}

	public String buscarNomeIettHistorico(String nome, Long codIett, Long exercicio) {

		StringBuilder hql = new StringBuilder();

		hql.append("select 1 from tb_exercicio_exe where now() between data_inicial_exe and data_final_exe and cod_exe = :exercicio");

		SQLQuery q = this.session.createSQLQuery(hql.toString());

		if (exercicio != null && !exercicio.equals(0L)) {
			q.setParameter("exercicio", exercicio);
		} else {
			return nome;
		}

		if (q.uniqueResult() == null) {

			hql = new StringBuilder();

			hql.append(" select ietth.nome_iett " + " from tb_historico_ietth ietth " + " where (ietth.cod_iett,ietth.data_ult_manutencao_iett) "
					+ "		in (select cod_iett, max(data_ult_manutencao_iett) " + "		    from tb_historico_ietth "
					+ "		    where data_ult_manutencao_iett <= (select data_final_exe from tb_exercicio_exe where cod_exe = :exercicio) " + "		    group by cod_iett) "
					+ " and ietth.cod_iett = :codIett ");

			q = this.session.createSQLQuery(hql.toString());

			q.setParameter("exercicio", exercicio);
			q.setParameter("codIett", codIett);

			if (q.uniqueResult() != null)
				nome = q.uniqueResult().toString();

			return nome;

		} else {
			return nome;
		}

	}

	public String limpaParecer(String parecer) {

		if (parecer != null && parecer.toLowerCase().indexOf("<li") != -1) {

			String[] aParecer = parecer.toLowerCase().split("<li");

			StringBuilder sbParecer = null;
			StringBuilder saida = new StringBuilder();

			for (int i = 0; i < aParecer.length; i++) {
				sbParecer = new StringBuilder(aParecer[i]);

				try {
					sbParecer.delete(0, sbParecer.indexOf(">"));
					saida.append("<li " + sbParecer.toString());
				} catch (Exception e) {
					continue;
				}
			}

			if (saida.length() > 0) {
				return saida.toString();
			} else {
				return parecer;
			}

		} else
			return parecer;
	}

	private List<String> fonetizarEtiqueta(List<String> etiqueta) {
		List<String> etiquetaF = new ArrayList<String>();

		for (String et : etiqueta) {
			etiquetaF.add(EtiquetaUtils.fonetizar(et));
		}
		return etiquetaF;
	}

}