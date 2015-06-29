package ecar.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import comum.database.Dao;
import comum.util.Data;
import comum.util.EtiquetaUtils;
import ecar.api.facade.EcarData;
import ecar.api.facade.IndicadorResultado;
import ecar.api.facade.Previsto;
import ecar.bean.FiltroPesquisaMonitoramento;
import ecar.enumerator.TipoRelatorioAcompanhamento;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstrutUploadIettup;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.acompanhamentoEstrategico.Acao;
import ecar.pojo.acompanhamentoEstrategico.Estrategia;
import ecar.pojo.acompanhamentoEstrategico.Indicador;
import ecar.pojo.acompanhamentoEstrategico.IndicadorDetalhamento;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.pojo.acompanhamentoEstrategico.Orgao;
import ecar.pojo.acompanhamentoEstrategico.PeriodoAcompanhamento;
import ecar.pojo.acompanhamentoEstrategico.Produto;
import ecar.pojo.acompanhamentoEstrategico.Responsavel;
import ecar.pojo.acompanhamentoEstrategico.Resultado;
import ecar.pojo.acompanhamentoEstrategico.ResultadoStatusContar;
import ecar.pojo.acompanhamentoEstrategico.StatusPeriodoAcompanhamento;
import ecar.servlet.indicador.IndicadorMonitoramentoBean;
import ecar.util.CriptografiaUtil;

public class SiteDao extends Dao<SiteDao> {

	private String uuid;
	ItemEstruturaAcaoDao daoIettAcao;
	ItemEstrtIndResulIettrDao indResulIettrDao;
	ItemEstruturaUploadDao itemEstruturaUploadDao;
	ExercicioDao exercicioDao;
	AcompRealFisicoDao daoRealFisico = new AcompRealFisicoDao(null);

	public SiteDao() {
		super();
		daoIettAcao = new ItemEstruturaAcaoDao(null);
		indResulIettrDao = new ItemEstrtIndResulIettrDao(null);
		exercicioDao = new ExercicioDao(null);
	}

	public SiteDao(HttpServletRequest request) {
		super();
		this.request = request;
		daoIettAcao = new ItemEstruturaAcaoDao(null);
		indResulIettrDao = new ItemEstrtIndResulIettrDao(null);
		exercicioDao = new ExercicioDao(null);
	}

	public SiteDao(Session session) {
		super(session);
	}

	public SiteDao(HttpServletRequest request, Session session) {
		super(session);
		if (request != null) {
			this.request = request;
		}
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

	private String limpaParecer(String parecer) {

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void populaIndicadorDetalhamento(Resultado resultado, long exe) {
		List<String> meses = new ArrayList<String>();
		ExercicioExe exercicio = new ExercicioExe();
		try {
			exercicio = (ExercicioExe) exercicioDao.localizar(ExercicioExe.class, exe);
			//exercicio = exercicioDao.getExercicio("04", "2014");
			meses = exercicioDao.getMesesDentroDoExercicio(exercicio);
		} catch (ECARException e1) {
			e1.printStackTrace();
		}

		for (Indicador indicador : resultado.getIndicador()) {
			List valores = new ArrayList();

			try {
				ItemEstrtIndResulIettr indResul = new ItemEstrtIndResulIettr();
				indResul.setCodIettir(new Long(indicador.getCodigo()));
				Map mapMeses = daoRealFisico.getQtdRealizadaExercicioPorMes(exercicio, indResul);
				IndicadorResultado wrapperIndicador = new IndicadorResultado(indResul.getCodIettir());

				for (String datas : meses) {
					IndicadorDetalhamento detalhamentoPrevisto = new IndicadorDetalhamento();
					IndicadorDetalhamento detalhamentoRealizado = new IndicadorDetalhamento();

					String mes = datas.split("-")[0];
					String ano = datas.split("-")[1];
					EcarData ecarData = new EcarData(mes, ano);

					Previsto prev = wrapperIndicador.getPrevistoMensal(ecarData);

					if (prev != null) {
					}
					detalhamentoPrevisto.setMes(Data.getAbreviaturaMes(Integer.valueOf(mes)));
					detalhamentoPrevisto.setAno(ano);
					detalhamentoPrevisto.setRealizadoNoMes(prev != null ? prev.getValorPrevisto() : 0.0);
					detalhamentoPrevisto.setTipo("P");
					indicador.getDetalhe().add(detalhamentoPrevisto);

					boolean possuiValor = true;
					String mesAnoMap = datas;
					Object m = mapMeses.get(mesAnoMap);
					if (m == null) {
						possuiValor = false;
					}

					Double valor = 0.0;
					if (possuiValor) {
						valor = new Double(m.toString());
						valores.add(valor);

					} else {
					}

					detalhamentoRealizado.setMes(Data.getAbreviaturaMes(Integer.valueOf(mes)));
					detalhamentoRealizado.setAno(ano);
					detalhamentoRealizado.setRealizadoNoMes(valor);
					detalhamentoRealizado.setTipo("R");
					indicador.getDetalhe().add(detalhamentoRealizado);

				}

			} catch (ECARException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private List<Estrategia> populaResultadoComIndicador(Map<String, Estrategia> mapaEstrategia, List<Resultado> listaResultado, boolean isIndicador, boolean apenasParecer, boolean relEncaminhamento,
			Date dataInicial, Date dataFinal, boolean somenteREM, boolean isDetalhamento, int painelIndicador, long exercicio) throws ECARException {
		List<Estrategia> listaExclusao = new ArrayList<Estrategia>();
		List<Estrategia> lista = new ArrayList<Estrategia>(mapaEstrategia.values());
		Collections.sort(lista);

		for (Estrategia estrategia : lista) {
			for (Resultado resultado : listaResultado) {
				if (!apenasParecer) {
					resultado.setIndicador(getIndicadoresSite(resultado.getCodigo())); // getListIndicador(resultado.getCodigo(),
																						// somenteREM));
					if (isDetalhamento) {
						populaIndicadorDetalhamento(resultado, exercicio);
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

	/**
	 * Preenche o Filtro de acordo com os parametros vindos do Relatorio
	 * 
	 * @param String
	 *            [] objetivosSelecionados
	 * @param String
	 *            [] etiquetasSelecionadas
	 * @param String
	 *            [] statusSelecionados
	 * @param String
	 *            [] secretariasSelecionadas
	 * @param String
	 *            prioritario
	 * @param String
	 *            codigoRelatorio
	 * @param String
	 *            ordenarPorStatus
	 * @param SegurancaECAR
	 *            seguranca
	 * @param String
	 *            dataInicial
	 * @param String
	 *            dataFinal
	 * @param String
	 *            comIndicadores
	 * @param String
	 *            exercicio
	 * @param String
	 *            somenteREM
	 * @param String
	 *            acompReferencia
	 * @return FiltroPesquisaMonitoramento
	 */
	@SuppressWarnings("unused")
	private FiltroPesquisaMonitoramento preencherFiltroPesquisaMonitoramento(String[] objetivosSelecionados, String[] etiquetasSelecionadas, String[] statusSelecionados,
			String[] secretariasSelecionadas, String prioritario, String codigoRelatorio, String ordenarPorStatus, SegurancaECAR seguranca, String dataInicial, String dataFinal,
			String comIndicadores, String exercicio, String somenteREM, String acompReferencia) {

		FiltroPesquisaMonitoramento filtroPesquisaMonitoramento = new FiltroPesquisaMonitoramento();
		filtroPesquisaMonitoramento.setCodigosObjetivosEstrategicos(preencherCodigosObjetivosEstrategicos(objetivosSelecionados != null ? objetivosSelecionados : new String[0]));
		filtroPesquisaMonitoramento.setEtiquetasSelecionadas(preencherEtiquetasSelecionadas(etiquetasSelecionadas != null ? etiquetasSelecionadas : new String[0]));
		filtroPesquisaMonitoramento.setStatusSelecionados(preencherStatusSelecionados(statusSelecionados != null ? statusSelecionados : new String[0]));
		filtroPesquisaMonitoramento.setSecretariasSelecionadas(preencherSecretariasSelecionadas(secretariasSelecionadas != null ? secretariasSelecionadas : new String[0]));
		filtroPesquisaMonitoramento.setPrioritario(StringUtils.isNotBlank(prioritario) ? true : false);
		filtroPesquisaMonitoramento.setTipoRelatorioAcompanhamento(preencherTipoRelatorioAcompanhamento(codigoRelatorio));
		filtroPesquisaMonitoramento.setOrdenarPorStatus(preencherOrdenacaoPorStatus(ordenarPorStatus));
		filtroPesquisaMonitoramento.setCodigosUsuariosPermissao(preencherCodigosGruposUsuarios(seguranca));
		filtroPesquisaMonitoramento.setCodigoUsuario(seguranca.getCodUsu());
		filtroPesquisaMonitoramento.setEtiquetasSemFonetizacao(preencherEtiquetasSemFonetizacao(etiquetasSelecionadas));
		filtroPesquisaMonitoramento.setDataInicial(dataInicial != null ? Data.parseDate(dataInicial) : null);
		filtroPesquisaMonitoramento.setDataFinal(preencherDataFinal(dataFinal != null ? dataFinal : null));
		filtroPesquisaMonitoramento.setComIndicadores(preencherComIndicadores(comIndicadores));
		filtroPesquisaMonitoramento.setExercicio((exercicio != null && !exercicio.equals("")) ? Long.parseLong(exercicio.toString()) : 0L);
		filtroPesquisaMonitoramento.setSomenteREM(preencherComIndicadores(somenteREM));
		filtroPesquisaMonitoramento.setAcompReferencia((acompReferencia != null && acompReferencia != "") ? Long.parseLong(acompReferencia) : 0L);

		return filtroPesquisaMonitoramento;
	}

	/**
	 * Preenche o Filtro de acordo com o Web Services (EcarWSPE)
	 * 
	 * @param List
	 *            <Integer> codigoObjetivoEstrategico
	 * @param List
	 *            <String> etiqueta
	 * @param List
	 *            <String> etiquetaPrioritaria
	 * @param List
	 *            <Integer> resultadoStatus
	 * @param Long
	 *            codigoUsuario
	 * @param List
	 *            <Long> gruposUsuarios
	 * @param boolean prioritario
	 * @param List
	 *            <Long> secretarias
	 * @param Long
	 *            exercicio
	 * @param int painelIndicador
	 * @param Long
	 *            acompReferencia
	 * @return FiltroPesquisaMonitoramento
	 */
	private FiltroPesquisaMonitoramento preencherFiltroPesquisaMonitoramentoWS(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria,
			List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios, boolean prioritario, List<Long> secretarias, Long exercicio, int painelIndicador, Long acompReferencia) {

		FiltroPesquisaMonitoramento filtroPesquisaMonitoramento = new FiltroPesquisaMonitoramento();
		filtroPesquisaMonitoramento.setCodigosObjetivosEstrategicos(codigoObjetivoEstrategico);
		filtroPesquisaMonitoramento.setEtiquetasSelecionadas(etiqueta);
		filtroPesquisaMonitoramento.setStatusSelecionados(resultadoStatus);
		filtroPesquisaMonitoramento.setSecretariasSelecionadas(secretarias);
		filtroPesquisaMonitoramento.setPrioritario(prioritario);
		filtroPesquisaMonitoramento.setTipoRelatorioAcompanhamento(null);
		filtroPesquisaMonitoramento.setOrdenarPorStatus(false);
		filtroPesquisaMonitoramento.setCodigosUsuariosPermissao(gruposUsuarios);
		filtroPesquisaMonitoramento.setCodigoUsuario(codigoUsuario);
		filtroPesquisaMonitoramento.setEtiquetasSemFonetizacao(null);
		filtroPesquisaMonitoramento.setDataInicial(null);
		filtroPesquisaMonitoramento.setDataFinal(null);
		filtroPesquisaMonitoramento.setComIndicadores(false);
		filtroPesquisaMonitoramento.setExercicio(exercicio);
		filtroPesquisaMonitoramento.setSomenteREM(false);
		filtroPesquisaMonitoramento.setAcompReferencia(acompReferencia);

		return filtroPesquisaMonitoramento;

	}

	private boolean preencherComIndicadores(String comIndicadores) {
		if (StringUtils.isNotBlank(comIndicadores)) {
			return true;
		} else {
			return false;
		}
	}

	private Date preencherDataFinal(String dataFinal) {
		if (dataFinal == null || dataFinal.isEmpty()) {
			return null;
		}

		Calendar dt = Data.getCalendar(Data.parseDate(dataFinal));
		dt.add(Calendar.HOUR_OF_DAY, 23);
		dt.add(Calendar.MINUTE, 59);
		dt.add(Calendar.SECOND, 59);

		return dt.getTime();
	}

	private List<String> preencherEtiquetasSemFonetizacao(String[] etiquetasSelecionadas) {
		List<String> listaEtiquetasSemFonetizacao = new ArrayList<String>();
		for (String etiquetaSelecionada : etiquetasSelecionadas) {
			String[] etiquetas = etiquetaSelecionada.split(",");
			for (String etiqueta : etiquetas) {
				if (StringUtils.isNotBlank(etiqueta)) {
					listaEtiquetasSemFonetizacao.add(etiqueta);
				}
			}
		}
		return listaEtiquetasSemFonetizacao;
	}

	@SuppressWarnings("rawtypes")
	private List<Long> preencherCodigosGruposUsuarios(SegurancaECAR seguranca) {
		List<Long> codigosGruposUsuarios = new ArrayList<Long>();
		Iterator i = seguranca.getGruposAcesso().iterator();
		while (i.hasNext()) {
			SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) i.next();
			codigosGruposUsuarios.add(sisAtributoSatb.getCodSatb());
		}
		return codigosGruposUsuarios;
	}

	private boolean preencherOrdenacaoPorStatus(String ordenarPorStatus) {
		if (StringUtils.isNotBlank(ordenarPorStatus)) {
			return true;
		} else {
			return false;
		}
	}

	private TipoRelatorioAcompanhamento preencherTipoRelatorioAcompanhamento(String codigoRelatorio) {
		TipoRelatorioAcompanhamento tipoRelatorioAcompanhamento = null;
		for (TipoRelatorioAcompanhamento tipo : TipoRelatorioAcompanhamento.values()) {
			if (StringUtils.equals(tipo.getCodigo(), codigoRelatorio)) {
				tipoRelatorioAcompanhamento = tipo;
				break;
			}
		}

		return tipoRelatorioAcompanhamento;
	}

	private List<Long> preencherSecretariasSelecionadas(String[] secretariasSelecionadas) {
		List<Long> codigosSecretarias = new ArrayList<Long>();
		if (secretariasSelecionadas != null) {
			for (String secretaria : secretariasSelecionadas) {
				String[] codigos = secretaria.split(",");
				for (String codigo : codigos) {
					if (StringUtils.isNotBlank(codigo)) {
						codigosSecretarias.add(new Long(codigo.replaceAll("[^0-9]", "")));
					}
				}
			}
		}

		return codigosSecretarias;
	}

	private List<Integer> preencherCodigosObjetivosEstrategicos(String[] objetivosSelecionados) {
		List<Integer> codigosObjetivos = new ArrayList<Integer>();
		for (String codigo : objetivosSelecionados) {
			codigosObjetivos.add(new Integer(codigo));
		}
		return codigosObjetivos;
	}

	private List<String> preencherEtiquetasSelecionadas(String[] etiquetasSelecionadas) {
		List<String> listaEtiquetas = new ArrayList<String>();
		for (String etiquetaSelecionada : etiquetasSelecionadas) {
			String[] etiquetas = etiquetaSelecionada.split(",");
			for (String etiqueta : etiquetas) {
				if (StringUtils.isNotBlank(etiqueta)) {
					listaEtiquetas.add(EtiquetaUtils.fonetizar(etiqueta));
				}
			}
		}
		return listaEtiquetas;
	}

	private List<Integer> preencherStatusSelecionados(String[] statusSelecionados) {
		List<Integer> listaStatus = new ArrayList<Integer>();
		if (statusSelecionados != null) {
			for (String status : statusSelecionados) {
				listaStatus.add(new Integer(status));
			}
		}

		return listaStatus;
	}
	
	private String formataNumero(String numero){
		numero = String.format("%.2f", Double.valueOf(numero));
		return numero;
	}

	@SuppressWarnings("unchecked")
	public List<Indicador> getIndicadoresSite(Long codResultado) {

		Integer anoRealizado = null;
		Integer anoPrevisto = null;
		SQLQuery query = null;
		StringBuilder hql = null;

		hql = new StringBuilder();
		hql.append("SELECT ind.cod_iett, ind.cod_iettir, ind.ano_rea, ind.mes_rea, ind.qtd_realizada, ind.ano_prev,");
		hql.append(" ind.mes_prev, ind.qtd_prevista, ind.taxa_exec, ind.cod_cor, indres.nome_iettir");
		hql.append(" FROM site.tb_indicador ind");
		hql.append(" INNER JOIN tb_item_estrt_ind_resul_iettr indres on indres.cod_iettir = ind.cod_iettir");
		hql.append(" where ind.cod_iett = :cod_iett");
		hql.append(" ORDER by indres.nome_iettir");

		query = this.session.createSQLQuery(hql.toString());

		query.setParameter("cod_iett", codResultado);

		List<Object[]> lista = query.list();
		List<Indicador> indicadorList = new ArrayList<Indicador>();
		Indicador indicador = new Indicador();
		Integer codigoAtual = null;
		String realizado = null;
		String previsto = null;

		
		for (Object[] obj : lista) {

			codigoAtual = Integer.parseInt(obj[1].toString());

			if (!indicador.getCodigo().equals(codigoAtual)) {
				indicador = new Indicador();
				indicadorList.add(indicador);
				indicador.setCodigo(codigoAtual);
			}
			
			realizado = formataNumero(obj[4].toString());

			if (obj[2] != null) {
				anoRealizado = Integer.parseInt(obj[2].toString());
				switch (anoRealizado) {
				case 2012:
					indicador.setValorRealizado2012(realizado);
					break;
				case 2013:
					indicador.setValorRealizado2013(realizado);
					break;
				case 2014:
					indicador.setValorRealizado2014(realizado);
					break;
				case 2015:
					indicador.setValorRealizado2015(realizado);
					break;
				}
			}
			
			previsto = formataNumero(obj[7].toString());

			if (obj[5] != null) {
				anoPrevisto = Integer.parseInt(obj[5].toString());
				switch (anoPrevisto) {
				case 2012:
					indicador.setMeta2012(previsto);
					break;
				case 2013:
					indicador.setMeta2013(previsto);
					break;
				case 2014:
					indicador.setMeta2014(previsto);
					break;
				case 2015:
					indicador.setMeta2015(previsto);
					break;
				}
			}

			if (obj[10] != null) {
				indicador.setNome(obj[10].toString());
			}


		}

		return indicadorList;

	}
	
	@SuppressWarnings("unchecked")
	private List<PeriodoAcompanhamento> recuperaPeriodosAcompanhamentoSite(Long codigoResultado, Long exercicio){
		
		StringBuilder hql = new StringBuilder();
		PeriodoAcompanhamento periodoAcompanhamento = null;
		List<PeriodoAcompanhamento> periodoAcompanhamentoList = null;
		
		hql.append("SELECT cod_iett, mes, ano, cod_cor, nome_cor, significado_cor, cod_sit,"); 
		hql.append(" descricao_sit, parecer, data_parecer, ultimo_parecer, nao_monitorado,"); 
		hql.append(" cod_usu, cod_exe");
		hql.append(" FROM site.tb_monitoramento mon_res");
		hql.append(" where mon_res.cod_iett = :codigoResultado");
		hql.append(" and cod_exe = :exercicio");
		hql.append(" and cod_cor is not null");
		hql.append(" order by CAST(ano AS integer) desc, CAST(mes AS integer) desc");

		SQLQuery q = this.getSession().createSQLQuery(hql.toString());
		
		q.setMaxResults(3);
		
		q.setLong("codigoResultado", codigoResultado);
		
		q.setLong("exercicio", exercicio);
		
		List<Object[]> lista = q.list();
		
		periodoAcompanhamentoList = new ArrayList<PeriodoAcompanhamento>();
		
		for(Object obj[] : lista){
			periodoAcompanhamento = new PeriodoAcompanhamento(
					0L,
					obj[8] != null ? obj[8].toString() : "", 
					obj[1].toString() != null ? obj[1].toString() : "", 
					obj[2].toString() != null ? obj[2].toString() : null,
					obj[3] != null ? Long.parseLong(obj[3].toString()) : 0L,
					obj[4] != null ? obj[4].toString() : "N&atilde;o Monitorado",
					0L
			);			
			periodoAcompanhamentoList.add(periodoAcompanhamento);
		}
		
		return periodoAcompanhamentoList;
		
	}

	@SuppressWarnings("unchecked")
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

	private String montaParecer(String parecer, Long codigo, List<AcompReferenciaAref> listaAref) {

		StringBuilder anexos = new StringBuilder();
		String dataFormatada = null;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		ItemEstruturaUploadDao itemEstruturaUploadDao = null;

		try {
			itemEstruturaUploadDao = new ItemEstruturaUploadDao(null);
			List<ItemEstrutUploadIettup> listaDeAnexos = new ArrayList<ItemEstrutUploadIettup>();
			
			listaDeAnexos = itemEstruturaUploadDao.getAnexosIettNovo(codigo, listaAref);

			if (!listaDeAnexos.isEmpty()) {

				String nomeArquivo = "";
				anexos.append("<p></p><fieldset><legend>Anexos</legend>");
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
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return parecer + anexos.toString();

	}

	@SuppressWarnings("unchecked")
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

	private List<Produto> populaAcao(Map<String, Produto> mapaProduto, Map<Long, Acao> mapaAcao, boolean isResultadoOuProduto) {
		List<Produto> lista = new ArrayList<Produto>(mapaProduto.values());

		if (lista != null) {
			// Collections.sort(lista);
			if (isResultadoOuProduto) {
				for (Produto produto : lista) {
					for (Acao acao : mapaAcao.values()) {
						if (acao.getCodProd() == produto.getCodigo()) {
							produto.addAcoes(acao);
						}
					}
				}
			}
		}
		return lista;
	}

	private List<Resultado> populaProduto(Map<String, Resultado> mapaResultado, List<Produto> listaProduto) {
		List<Resultado> lista = new ArrayList<Resultado>(mapaResultado.values());
		// Collections.sort(lista);

		for (Resultado resultado : lista) {
			for (Produto produto : listaProduto) {
				if (produto.getCodResultado() == resultado.getCodigo()) {
					resultado.addProdutos(produto);
				}

			}
		}
		return lista;
	}

	private List<Estrategia> populaResultado(Map<String, Estrategia> mapaEstrategia, List<Resultado> listaResultado) {
		List<Estrategia> listaExclusao = new ArrayList<Estrategia>();
		List<Estrategia> lista = new ArrayList<Estrategia>(mapaEstrategia.values());
		// Collections.sort(lista);
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

		}

		for (Estrategia exclui : listaExclusao) {
			lista.remove(exclui);
		}
		return lista;
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

	@SuppressWarnings("unchecked")
	private List<Object[]> getListaPorFiltroGeral(FiltroPesquisaMonitoramento filtro) {

		StringBuilder hql = new StringBuilder();

		hql.append("select");
		hql.append(" obj.cod_oe,");
		hql.append(" sigla_oe,");
		hql.append(" nome_oe,");
		hql.append(" est.cod_est,");
		hql.append(" sigla_est,");
		hql.append(" nome_est,");

		// Resultado
		hql.append(" res.cod_res,");
		hql.append(" sigla_res,");
		hql.append(" site.nomeiett(nome_res, :exercicio, res.cod_res) nome_res,");
		hql.append(" res.rem,");
		hql.append(" site.orgaoUsu(res.cod_usu, mon_res.cod_usu) res_org_usu,");
		hql.append(" site.nomeUsu(res.cod_usu, mon_res.cod_usu) res_nome_usu,");
		hql.append(" mon_res.mes as res_mes,");
		hql.append(" mon_res.ano as res_ano,");
		hql.append(" mon_res.cod_cor as res_cod_cor,");
		hql.append(" mon_res.nome_cor as res_nome_cor,");

		// Produto
		hql.append(" prod.cod_prod,");
		hql.append(" prod.sigla_prod,");
		hql.append(" site.nomeiett(prod.nome_prod, :exercicio, prod.cod_prod) nome_prod,");
		hql.append(" site.orgaoUsu(prod.cod_usu, mon_prod.cod_usu) prod_org_usu,");
		hql.append(" site.nomeUsu(prod.cod_usu, mon_prod.cod_usu) prod_nome_usu,");
		hql.append(" mon_prod.mes as prod_mes,");
		hql.append(" mon_prod.ano as prod_ano,");
		hql.append(" mon_prod.cod_cor as prod_cod_cor,");
		hql.append(" mon_prod.nome_cor as prod_nome_cor,");
		hql.append(" mon_prod.parecer,");
		hql.append(" mon_res.cod_sit as res_cod_sit,");
		hql.append(" mon_prod.cod_sit as prod_cod_sit");

		hql.append(" from site.tb_oe obj");
		hql.append(" left join site.tb_estrategia est on est.cod_oe = obj.cod_oe");
		hql.append(" left join site.tb_resultado res on res.cod_est = est.cod_est");
		hql.append(" left join site.tb_produto prod on prod.cod_res = res.cod_res");
		hql.append(" left join site.tb_monitoramento mon_res on res.cod_res = mon_res.cod_iett");
		hql.append(" left join site.tb_monitoramento mon_prod on prod.cod_prod = mon_prod.cod_iett");
		hql.append(" inner join tb_item_estrut_usuario_iettus perm on res.cod_res = perm.cod_iett");

		if (!filtro.getEtiquetasSelecionadas().isEmpty()) {
			hql.append(" inner join site.tb_etiqueta_rel eti on res.cod_res = eti.cod_iett");
		}

		hql.append(" where (mon_res.ultimo_parecer = 'Y' or mon_res.nao_monitorado = 'Y')");

		hql.append(" and ((mon_prod.cod_exe = (select max(cod_exe) from site.tb_monitoramento where cod_iett = prod.cod_prod and cod_exe <= :exercicio)");
		hql.append(" and ((mon_prod.ultimo_parecer = 'Y' or mon_prod.nao_monitorado = 'Y') )) or mon_prod.cod_iett is null)");

		// exercicio
		if (filtro.getExercicio() > 0) {
			hql.append(" and mon_res.cod_exe = :exercicio ");
		}

		// Objetivo Estrategico 12
		if (!filtro.getCodigosObjetivosEstrategicos().isEmpty()) {
			hql.append(" and obj.cod_oe in (:codObj)");
		}

		// Etiqueta 128
		if (!filtro.getEtiquetasSelecionadas().isEmpty()) {
			hql.append(" and eti.nome_etiqueta_fonetico in (:etiquetas)");
		}

		// status

		if (!filtro.getStatusSelecionados().isEmpty()) {
			if (filtro.getStatusSelecionados().size() == 1 && filtro.getStatusSelecionados().contains(0)) {
				hql.append(" and mon_res.cod_cor is null");
			} else if (filtro.getStatusSelecionados().size() > 1 && filtro.getStatusSelecionados().contains(0)) {
				hql.append(" and (mon_res.cod_cor is null or mon_res.cod_cor in (:status))");
			} else {
				hql.append(" and mon_res.cod_cor in (:status)");
			}
		}

		// REM
		if (filtro.isPrioritario()) {
			hql.append(" and res.rem = 'Y'");
		}

		// Secretarias 14
		if (!filtro.getSecretariasSelecionadas().isEmpty()) {
			hql.append(" and res.cod_org in (:secretarias)");
		}

		// nao exibir os finalizados em 2013
		//if (filtro.getExercicio() == 11L) {
		//	hql.append(" and res.ind_finalizado <> 'Y'");
		//}

		// Permissao
		hql.append(" and (perm.cod_usu in (:codUsu) or perm.cod_atb in (:gruposUsuarios))");
		hql.append(" order by sigla_oe, sigla_est, sigla_res, sigla_prod");

		SQLQuery query = this.session.createSQLQuery(hql.toString());

		// CONFIGURACAO DOS PARAMETROS

		if (!filtro.getCodigosObjetivosEstrategicos().isEmpty()) {
			query.setParameterList("codObj", filtro.getCodigosObjetivosEstrategicos());
		}

		if (!filtro.getEtiquetasSelecionadas().isEmpty()) {
			query.setParameterList("etiquetas", filtro.getEtiquetasSelecionadas());
		}

		if (!filtro.getSecretariasSelecionadas().isEmpty()) {
			query.setParameterList("secretarias", filtro.getSecretariasSelecionadas());
		}

		if (filtro.getStatusSelecionados().size() > 1 && filtro.getStatusSelecionados().contains(0)) {
			List<Integer> statusSelecionados = new ArrayList<Integer>();
			for (Integer status : filtro.getStatusSelecionados()) {
				if (status != 0) {
					statusSelecionados.add(status);
				}
			}
			query.setParameterList("status", filtro.getStatusSelecionados());
		} else if (!filtro.getStatusSelecionados().isEmpty() && !filtro.getStatusSelecionados().contains(0)) {
			query.setParameterList("status", filtro.getStatusSelecionados());
		}

		if (filtro.getExercicio() > 0) {
			query.setParameter("exercicio", filtro.getExercicio());
		}

		query.setParameter("codUsu", filtro.getCodigoUsuario());
		query.setParameterList("gruposUsuarios", filtro.getCodigosUsuariosPermissao());

		// FIM CONFIGURACAO DOS PARAMETROS

		List<Object[]> lista = query.list();

		return lista;

	}
	
	@SuppressWarnings("unchecked")
	public List<Object[]> getListaPorFiltroRelatorioExcel(FiltroPesquisaMonitoramento filtro) {
		StringBuilder hql = new StringBuilder();

		hql.append("select distinct");
		hql.append(" obj.cod_oe,"); // 0
		hql.append(" obj.sigla_oe,"); // 1
		hql.append(" est.cod_est,"); // 2
		hql.append(" est.sigla_est,"); // 3

		// Resultado
		hql.append(" res.cod_res,"); // 4
		hql.append(" res.sigla_res,"); // 5
		hql.append(" site.nomeiett(nome_res, :exercicio, res.cod_res) nome_res,"); // 6
		hql.append(" res.rem,"); // 7
		hql.append(" res.ds_secretaria_responsavel res_sec,"); // 8
		hql.append(" res.ds_nome_usu_cadastro res_nome_usu,"); // 9
		hql.append(" mon_res.descricao_sit as res_descricao_sit,"); // 10

		// Produto
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN prod.cod_prod ELSE NULL END cod_prod,"); // 11
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN prod.sigla_prod ELSE NULL END sigla_prod,"); // 12
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN site.nomeiett(prod.nome_prod, :exercicio, prod.cod_prod) ELSE NULL END nome_prod,"); // 13
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN prod.ds_secretaria_responsavel ELSE NULL END prod_org,"); // 14
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN prod.ds_nome_usu_cadastro ELSE NULL END prod_nome_usu,"); // 15
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN mon_prod.descricao_sit ELSE NULL END prod_descricao_sit, "); // 16
		
		hql.append(" ac.cod_ac,"); // 17
		hql.append(" ac.sigla_ac,"); // 18
		hql.append(" site.nomeiett(nome_ac, :exercicio, ac.cod_ac) nome_ac, "); // 19
		hql.append(" CASE  mon_ac.cod_exe WHEN :exercicio THEN mon_ac.descricao_sit ELSE NULL END ac_descricao_sit, "); // 20
		hql.append(" ac.ds_nome_usu_cadastro ac_nome_usu, "); // 21
		
		hql.append(" res.ds_email_usu_cadastro res_email_usu, "); // 22
		hql.append(" prod.ds_email_usu_cadastro prod_email_usu, "); // 23
		hql.append(" ac.ds_email_usu_cadastro ac_email_usu, "); // 24
		hql.append(" ac.ds_secretaria_responsavel ac_sec, "); // 25
		
		//Indicador
		hql.append(" iettir.nome_iettir "); // 26
		
		hql.append(" from site.tb_oe obj");
		hql.append(" left join site.tb_estrategia est on est.cod_oe = obj.cod_oe");
		hql.append(" left join site.tb_resultado res on res.cod_est = est.cod_est");
		hql.append(" left join site.tb_produto prod on prod.cod_res = res.cod_res");
		hql.append(" left join site.tb_acao ac on ac.cod_prod = prod.cod_prod"); 
		hql.append(" inner join site.tb_monitoramento mon_res on res.cod_res = mon_res.cod_iett");
		hql.append(" left join site.tb_monitoramento mon_prod on prod.cod_prod = mon_prod.cod_iett");
		hql.append(" left join site.tb_monitoramento mon_ac on ac.cod_ac = mon_ac.cod_iett"); 
		hql.append(" inner join tb_item_estrut_usuario_iettus perm on res.cod_res = perm.cod_iett");
		hql.append(" left join tb_item_estrt_ind_resul_iettr iettir on res.cod_res = iettir.cod_iettir ");

		if (!filtro.getEtiquetasSelecionadas().isEmpty()) {
			hql.append(" inner join site.tb_etiqueta_rel eti on res.cod_res = eti.cod_iett");
		}

		hql.append(" where (mon_res.ultimo_parecer = 'Y' or mon_res.nao_monitorado = 'Y')");

		hql.append(" and ((mon_prod.cod_exe = (select max(cod_exe) from site.tb_monitoramento where cod_iett = prod.cod_prod and cod_exe <= :exercicio)");
		hql.append(" and ((mon_prod.ultimo_parecer = 'Y' or mon_prod.nao_monitorado = 'Y') )) or mon_prod.cod_iett is null)");

		// exercicio
		if (filtro.getExercicio() > 0) {
			hql.append(" and mon_res.cod_exe = :exercicio ");
		}

		// Objetivo Estrategico 12
		if (!filtro.getCodigosObjetivosEstrategicos().isEmpty()) {
			hql.append(" and obj.cod_oe in (:codObj)");
		}
		
		// RESULTADOS E PRODUTOS QUE NUNCA FORAM MONITORADOS
		hql.append(" and ((select count(cod_iett) from site.tb_monitoramento where cod_iett = prod.cod_prod) > 0 or");
		hql.append(" ((select count(p.cod_prod) from site.tb_resultado r inner join site.tb_produto p on r.cod_res = p.cod_res where r.cod_res = res.cod_res) = 0");
		hql.append(" or (SELECT count(p.cod_prod) FROM site.tb_resultado r INNER JOIN site.tb_produto p ON r.cod_res = p.cod_res WHERE r.cod_res = res.cod_res) > 0))");
		// Etiqueta 128
		if (!filtro.getEtiquetasSelecionadas().isEmpty()) {
			hql.append(" and eti.nome_etiqueta_fonetico in (:etiquetas)");
		}

		// status

		if (!filtro.getStatusSelecionados().isEmpty()) {
			if (filtro.getStatusSelecionados().size() == 1 && filtro.getStatusSelecionados().contains(0)) {
				hql.append(" and mon_res.cod_cor is null");
			} else if (filtro.getStatusSelecionados().size() > 1 && filtro.getStatusSelecionados().contains(0)) {
				hql.append(" and (mon_res.cod_cor is null or mon_res.cod_cor in (:status))");
			} else {
				hql.append(" and mon_res.cod_cor in (:status)");
			}
		}

		// REM
		if (filtro.isPrioritario()) {
			hql.append(" and res.rem = 'Y'");
		}

		// Secretarias 14
		if (!filtro.getSecretariasSelecionadas().isEmpty()) {
			hql.append(" and res.cod_org in (:secretarias)");
		}

		// nao exibir os finalizados em 2013
		//if (filtro.getExercicio() == 11L) {
		//	hql.append(" and res.ind_finalizado <> 'Y'");
		//}

		// Permissao
		hql.append(" and (perm.cod_usu in (:codUsu) or perm.cod_atb in (:gruposUsuarios))");
		hql.append(" order by res.ds_secretaria_responsavel, sigla_oe, sigla_est, sigla_res, sigla_prod");

		SQLQuery query = this.session.createSQLQuery(hql.toString());

		// CONFIGURACAO DOS PARAMETROS

		if (!filtro.getCodigosObjetivosEstrategicos().isEmpty()) {
			query.setParameterList("codObj", filtro.getCodigosObjetivosEstrategicos());
		}

		if (!filtro.getEtiquetasSelecionadas().isEmpty()) {
			query.setParameterList("etiquetas", filtro.getEtiquetasSelecionadas());
		}

		if (!filtro.getSecretariasSelecionadas().isEmpty()) {
			query.setParameterList("secretarias", filtro.getSecretariasSelecionadas());
		}

		if (filtro.getStatusSelecionados().size() > 1 && filtro.getStatusSelecionados().contains(0)) {
			List<Integer> statusSelecionados = new ArrayList<Integer>();
			for (Integer status : filtro.getStatusSelecionados()) {
				if (status != 0) {
					statusSelecionados.add(status);
				}
			}
			query.setParameterList("status", filtro.getStatusSelecionados());
		} else if (!filtro.getStatusSelecionados().isEmpty() && !filtro.getStatusSelecionados().contains(0)) {
			query.setParameterList("status", filtro.getStatusSelecionados());
		}

		if (filtro.getExercicio() > 0) {
			query.setParameter("exercicio", filtro.getExercicio());
		}

		query.setParameter("codUsu", filtro.getCodigoUsuario());
		query.setParameterList("gruposUsuarios", filtro.getCodigosUsuariosPermissao());

		// FIM CONFIGURACAO DOS PARAMETROS
		List<Object[]> lista = query.list();

		return lista;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getListaPorFiltroRelatorio(FiltroPesquisaMonitoramento filtro) {

		StringBuilder hql = new StringBuilder();

		hql.append("select distinct");
		hql.append(" obj.cod_oe,"); // 0
		hql.append(" obj.sigla_oe,"); // 1
		hql.append(" obj.nome_oe,"); // 2
		hql.append(" est.cod_est,"); // 3
		hql.append(" est.sigla_est,"); // 4
		hql.append(" est.nome_est,"); // 5

		// Resultado
		hql.append(" res.cod_res,"); // 6
		hql.append(" res.sigla_res,"); // 7
		hql.append(" site.nomeiett(nome_res, :exercicio, res.cod_res) nome_res,"); // 8
		hql.append(" res.rem,"); // 9
		hql.append(" site.orgaoUsu(res.cod_usu, mon_res.cod_usu) res_org_usu,"); // 10
		hql.append(" site.nomeUsu(res.cod_usu, mon_res.cod_usu) res_nome_usu,"); // 11
		hql.append(" mon_res.mes as res_mes,"); // 12
		hql.append(" mon_res.ano as res_ano,"); // 13
		hql.append(" mon_res.cod_cor as res_cod_cor,"); // 14
		hql.append(" mon_res.nome_cor as res_nome_cor,"); // 15

		// Produto
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN prod.cod_prod ELSE NULL END cod_prod,"); // 16
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN prod.sigla_prod ELSE NULL END sigla_prod,"); // 17
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN site.nomeiett(prod.nome_prod, :exercicio, prod.cod_prod) ELSE NULL END nome_prod,"); // 18
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN site.orgaoUsu(prod.cod_usu, mon_prod.cod_usu) ELSE NULL END prod_org_usu,"); // 19
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN site.nomeUsu(prod.cod_usu, mon_prod.cod_usu) ELSE NULL END prod_nome_usu,"); // 20
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN mon_prod.mes ELSE NULL END prod_mes,"); // 21
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN mon_prod.ano ELSE NULL END prod_ano,"); // 22
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN mon_prod.cod_cor ELSE NULL END prod_cod_cor,"); // 23
		hql.append(" CASE  mon_prod.cod_exe WHEN :exercicio THEN mon_prod.nome_cor ELSE NULL END prod_nome_cor,"); // 24
		
		/*hql.append(" ac.cod_ac,"); // 25
		hql.append(" ac.sigla_ac,"); // 26
		hql.append(" site.nomeiett(nome_ac, :exercicio, ac.cod_ac) nome_ac,"); // 27
		hql.append(" ac.data_inicio as ac_data_inicio,"); // 28
		hql.append(" ac.data_termino as ac_data_termino,"); // 29
		hql.append(" ac.cod_org as ac_cod_org,"); // 30
		hql.append(" ac.cod_usu as ac_cod_usu,"); // 31
		hql.append(" site.orgaoUsu(ac.cod_usu, mon_ac.cod_usu) ac_org_usu,"); // 32
		hql.append(" site.nomeUsu(ac.cod_usu, mon_ac.cod_usu) ac_nome_usu,"); // 33
		hql.append(" mon_ac.mes as ac_mes,"); // 34
		hql.append(" mon_ac.ano as ac_ano,"); // 35
		hql.append(" mon_ac.cod_cor as ac_cod_cor,"); // 36
		hql.append(" mon_ac.nome_cor as ac_nome_cor,"); // 37 comentado por Wesley em 16/05/2014 */ 
		/*
		hql.append(" 1 as cod_ac,"); // 25
		hql.append(" 'A' as sigla_ac,"); // 26
		hql.append(" 'B' as nome_ac,"); // 27
		hql.append(" now() as ac_data_inicio,"); // 28
		hql.append(" now() as ac_data_termino,"); // 29
		hql.append(" 'D' as ac_cod_org,"); // 30
		hql.append(" 'F' as ac_cod_usu,"); // 31
		hql.append(" 'G' ac_org_usu,"); // 32
		hql.append(" 'C' ac_nome_usu,"); // 33
		hql.append(" 'Z' as ac_mes,"); // 34
		hql.append(" 'V' as ac_ano,"); // 35
		hql.append(" 'Q' as ac_cod_cor,"); // 36
		hql.append(" 'W' as ac_nome_cor,"); // 37
		*/
		
		/*
		hql.append(" 1 as cod_ac,"); // 25
		hql.append(" 'A' as sigla_ac,"); // 26
		hql.append(" 'B' as nome_ac,"); // 27
		hql.append(" now() as ac_data_inicio,"); // 28
		hql.append(" now() as ac_data_termino,"); // 29
		hql.append(" 'D' as ac_cod_org,"); // 30
		hql.append(" 'F' as ac_cod_usu,"); // 31
		hql.append(" 'G' ac_org_usu,"); // 32
		hql.append(" 'C' ac_nome_usu,"); // 33
		hql.append(" 'Z' as ac_mes,"); // 34
		hql.append(" 'V' as ac_ano,"); // 35
		hql.append(" 'Q' as ac_cod_cor,"); // 36
		hql.append(" 'W' as ac_nome_cor,"); // 37
		*/
		
		hql.append(" mon_prod.parecer as prod_parecer,"); // 38
		hql.append(" prod.data_inicio as prod_data_inicio,"); // 39
		hql.append(" prod.data_termino as prod_data_termino,"); // 40
		hql.append(" mon_res.cod_sit as res_cod_sit,");// 41
		hql.append(" mon_prod.cod_sit as prod_cod_sit,");// 42
		hql.append(" mon_res.parecer as res_parecer,");// 43
		
		/*hql.append(" mon_ac.parecer as ac_parecer,");// 44
		hql.append(" mon_ac.cod_sit as ac_cod_sit,");// 45 comentado por Wesley em 16/05/2014 */
		
		/*
		hql.append(" 'D' as ac_parecer,");// 44
		hql.append(" 'W' as ac_cod_sit,");// 45
		*/
		
		
		/*
		hql.append(" 'D' as ac_parecer,");// 44
		hql.append(" 'W' as ac_cod_sit,");// 45
		*/
		
		hql.append(" res.ind_finalizado as res_finalizado,");// 46
		hql.append(" prod.ind_finalizado as prod_finalizado"); // 47
		hql.append(" from site.tb_oe obj");
		hql.append(" left join site.tb_estrategia est on est.cod_oe = obj.cod_oe");
		hql.append(" left join site.tb_resultado res on res.cod_est = est.cod_est");
		hql.append(" left join site.tb_produto prod on prod.cod_res = res.cod_res");
		//hql.append(" left join site.tb_acao ac on ac.cod_prod = prod.cod_prod"); comentado por Wesley em 16/05/2014
		hql.append(" inner join site.tb_monitoramento mon_res on res.cod_res = mon_res.cod_iett");
		hql.append(" left join site.tb_monitoramento mon_prod on prod.cod_prod = mon_prod.cod_iett");
		//hql.append(" left join site.tb_monitoramento mon_ac on ac.cod_ac = mon_ac.cod_iett"); comentado por Wesley em 16/05/2014
		hql.append(" inner join tb_item_estrut_usuario_iettus perm on res.cod_res = perm.cod_iett");

		if (!filtro.getEtiquetasSelecionadas().isEmpty()) {
			hql.append(" inner join site.tb_etiqueta_rel eti on res.cod_res = eti.cod_iett");
		}

		hql.append(" where (mon_res.ultimo_parecer = 'Y' or mon_res.nao_monitorado = 'Y')");

		hql.append(" and ((mon_prod.cod_exe = (select max(cod_exe) from site.tb_monitoramento where cod_iett = prod.cod_prod and cod_exe <= :exercicio)");
		hql.append(" and ((mon_prod.ultimo_parecer = 'Y' or mon_prod.nao_monitorado = 'Y') )) or mon_prod.cod_iett is null)");

		// exercicio
		if (filtro.getExercicio() > 0) {
			hql.append(" and mon_res.cod_exe = :exercicio ");
		}

		// Objetivo Estrategico 12
		if (!filtro.getCodigosObjetivosEstrategicos().isEmpty()) {
			hql.append(" and obj.cod_oe in (:codObj)");
		}
		
		// RESULTADOS E PRODUTOS QUE NUNCA FORAM MONITORADOS
		hql.append(" and ((select count(cod_iett) from site.tb_monitoramento where cod_iett = prod.cod_prod) > 0 or");
		hql.append(" ((select count(p.cod_prod) from site.tb_resultado r inner join site.tb_produto p on r.cod_res = p.cod_res where r.cod_res = res.cod_res) = 0");
		hql.append(" or (SELECT count(p.cod_prod) FROM site.tb_resultado r INNER JOIN site.tb_produto p ON r.cod_res = p.cod_res WHERE r.cod_res = res.cod_res) > 0))");
		// Etiqueta 128
		if (!filtro.getEtiquetasSelecionadas().isEmpty()) {
			hql.append(" and eti.nome_etiqueta_fonetico in (:etiquetas)");
		}

		// status

		if (!filtro.getStatusSelecionados().isEmpty()) {
			if (filtro.getStatusSelecionados().size() == 1 && filtro.getStatusSelecionados().contains(0)) {
				hql.append(" and mon_res.cod_cor is null");
			} else if (filtro.getStatusSelecionados().size() > 1 && filtro.getStatusSelecionados().contains(0)) {
				hql.append(" and (mon_res.cod_cor is null or mon_res.cod_cor in (:status))");
			} else {
				hql.append(" and mon_res.cod_cor in (:status)");
			}
		}

		// REM
		if (filtro.isPrioritario()) {
			hql.append(" and res.rem = 'Y'");
		}

		// Secretarias 14
		if (!filtro.getSecretariasSelecionadas().isEmpty()) {
			hql.append(" and res.cod_org in (:secretarias)");
		}

		// nao exibir os finalizados em 2013
		//if (filtro.getExercicio() == 11L) {
		//	hql.append(" and res.ind_finalizado <> 'Y'");
		//}

		// Permissao
		hql.append(" and (perm.cod_usu in (:codUsu) or perm.cod_atb in (:gruposUsuarios))");
		hql.append(" order by sigla_oe, sigla_est, sigla_res, sigla_prod");

		SQLQuery query = this.session.createSQLQuery(hql.toString());

		// CONFIGURACAO DOS PARAMETROS

		if (!filtro.getCodigosObjetivosEstrategicos().isEmpty()) {
			query.setParameterList("codObj", filtro.getCodigosObjetivosEstrategicos());
		}

		if (!filtro.getEtiquetasSelecionadas().isEmpty()) {
			query.setParameterList("etiquetas", filtro.getEtiquetasSelecionadas());
		}

		if (!filtro.getSecretariasSelecionadas().isEmpty()) {
			query.setParameterList("secretarias", filtro.getSecretariasSelecionadas());
		}

		if (filtro.getStatusSelecionados().size() > 1 && filtro.getStatusSelecionados().contains(0)) {
			List<Integer> statusSelecionados = new ArrayList<Integer>();
			for (Integer status : filtro.getStatusSelecionados()) {
				if (status != 0) {
					statusSelecionados.add(status);
				}
			}
			query.setParameterList("status", filtro.getStatusSelecionados());
		} else if (!filtro.getStatusSelecionados().isEmpty() && !filtro.getStatusSelecionados().contains(0)) {
			query.setParameterList("status", filtro.getStatusSelecionados());
		}

		if (filtro.getExercicio() > 0) {
			query.setParameter("exercicio", filtro.getExercicio());
		}

		query.setParameter("codUsu", filtro.getCodigoUsuario());
		query.setParameterList("gruposUsuarios", filtro.getCodigosUsuariosPermissao());

		// FIM CONFIGURACAO DOS PARAMETROS

		List<Object[]> lista = query.list();

		return lista;

	}

	@SuppressWarnings("unchecked")
	private List<Object[]> getListaPorFiltroResultado(FiltroPesquisaMonitoramento filtro) {

		StringBuilder hql = new StringBuilder();

		hql.append("select distinct");
		hql.append(" obj.cod_oe,");// 0
		hql.append(" sigla_oe,");// 1
		hql.append(" nome_oe,");// 2
		hql.append(" est.cod_est,");// 3
		hql.append(" sigla_est,");// 4
		hql.append(" nome_est,");// 5

		// Resultado
		hql.append(" res.cod_res,");// 6
		hql.append(" sigla_res,");// 7
		hql.append(" site.nomeiett(nome_res, :exercicio, res.cod_res) nome_res,");// 8
		hql.append(" res.rem,");// 9
		hql.append(" site.orgaoUsu(res.cod_usu, mon_res.cod_usu) res_org_usu,");// 10
		hql.append(" site.nomeUsu(res.cod_usu, mon_res.cod_usu) res_nome_usu,");// 11
		hql.append(" mon_res.mes as res_mes,");// 12
		hql.append(" mon_res.ano as res_ano,");// 13
		hql.append(" mon_res.cod_cor as res_cod_cor,");// 14
		hql.append(" mon_res.nome_cor as res_nome_cor,");// 15

		// Produto
		hql.append(" prod.cod_prod,");// 16
		hql.append(" prod.sigla_prod,");// 17
		hql.append(" site.nomeiett(prod.nome_prod, :exercicio, prod.cod_prod) nome_prod,");// 18
		hql.append(" site.orgaoUsu(prod.cod_usu, mon_prod.cod_usu) prod_org_usu,");// 19
		hql.append(" site.nomeUsu(prod.cod_usu, mon_prod.cod_usu) prod_nome_usu,");// 20
		hql.append(" mon_prod.mes as prod_mes,");// 21
		hql.append(" mon_prod.ano as prod_ano,");// 22
		hql.append(" mon_prod.cod_cor as prod_cod_cor,");// 23
		hql.append(" mon_prod.nome_cor as prod_nome_cor,");// 24
		hql.append(" ac.cod_ac,");// 25
		hql.append(" ac.sigla_ac,");// 26
		hql.append(" site.nomeiett(nome_ac, :exercicio, ac.cod_ac) nome_ac,");// 27
		hql.append(" ac.data_inicio as ac_data_inicio,");// 28
		hql.append(" ac.data_termino as ac_data_termino,");// 29
		hql.append(" ac.cod_org as ac_cod_org,");// 30
		hql.append(" ac.cod_usu as ac_cod_usu,");// 31
		hql.append(" site.orgaoUsu(ac.cod_usu, mon_ac.cod_usu) ac_org_usu,");// 32
		hql.append(" site.nomeUsu(ac.cod_usu, mon_ac.cod_usu) ac_nome_usu,");// 33
		hql.append(" mon_ac.mes as ac_mes,");// 34
		hql.append(" mon_ac.ano as ac_ano,");// 35
		hql.append(" mon_ac.cod_cor as ac_cod_cor,");// 36
		hql.append(" mon_ac.nome_cor as ac_nome_cor,");// 37
		hql.append(" mon_prod.parecer as prod_parecer,");// 38
		hql.append(" prod.data_inicio as prod_data_inicio,");// 39
		hql.append(" prod.data_termino as prod_data_termino,");// 40
		hql.append(" mon_res.parecer as res_parecer,");// 41
		hql.append(" mon_ac.parecer as ac_parecer");// 42

		hql.append(" from site.tb_oe obj");
		hql.append(" left join site.tb_estrategia est on est.cod_oe = obj.cod_oe");
		hql.append(" left join site.tb_resultado res on res.cod_est = est.cod_est");
		hql.append(" left join site.tb_produto prod on prod.cod_res = res.cod_res");
		hql.append(" left join site.tb_acao ac on ac.cod_prod = prod.cod_prod");
		hql.append(" inner join site.tb_monitoramento mon_res on res.cod_res = mon_res.cod_iett");
		hql.append(" left join site.tb_monitoramento mon_prod on prod.cod_prod = mon_prod.cod_iett");
		hql.append(" left join site.tb_monitoramento mon_ac on ac.cod_ac = mon_ac.cod_iett");
		hql.append(" inner join tb_item_estrut_usuario_iettus perm on res.cod_res = perm.cod_iett");

		if (!filtro.getEtiquetasSelecionadas().isEmpty()) {
			hql.append(" inner join site.tb_etiqueta_rel eti on res.cod_res = eti.cod_iett");
		}

		hql.append(" where (mon_res.ultimo_parecer = 'Y' or mon_res.nao_monitorado = 'Y')");

		hql.append(" and ((mon_prod.cod_exe = (select max(cod_exe) from site.tb_monitoramento where cod_iett = prod.cod_prod and cod_exe <= :exercicio)");
		hql.append(" and ((mon_prod.ultimo_parecer = 'Y' or mon_prod.nao_monitorado = 'Y') )) or mon_prod.cod_iett is null)");

		// exercicio
		if (filtro.getExercicio() > 0) {
			hql.append(" and mon_res.cod_exe = :exercicio");
		}

		// Objetivo Estrategico 12
		if (!filtro.getCodigosObjetivosEstrategicos().isEmpty()) {
			hql.append(" and res.cod_res in (:codObj)");
		}

		// RESULTADOS E PRODUTOS QUE NUNCA FORAM MONITORADOS
		hql.append(" and ((select count(cod_iett) from site.tb_monitoramento where cod_iett = prod.cod_prod) > 0 or");
		hql.append(" ((select count(p.cod_prod) from site.tb_resultado r inner join site.tb_produto p on r.cod_res = p.cod_res where r.cod_res = res.cod_res) = 0");
		hql.append(" or (SELECT count(p.cod_prod) FROM site.tb_resultado r INNER JOIN site.tb_produto p ON r.cod_res = p.cod_res WHERE r.cod_res = res.cod_res) > 0))");
		// nao exibir os finalizados em 2013
		//if (filtro.getExercicio() == 11L) {
		//	hql.append(" and res.ind_finalizado <> 'Y'");
		//}

		// Permissao
		hql.append(" and (perm.cod_usu in (:codUsu) or perm.cod_atb in (:gruposUsuarios))");
		hql.append(" order by sigla_oe, sigla_est, sigla_res, sigla_prod");

		SQLQuery query = this.session.createSQLQuery(hql.toString());

		// CONFIGURACAO DOS PARAMETROS

		if (!filtro.getCodigosObjetivosEstrategicos().isEmpty()) {
			query.setParameterList("codObj", filtro.getCodigosObjetivosEstrategicos());
		}

		if (filtro.getExercicio() > 0) {
			query.setParameter("exercicio", filtro.getExercicio());
		}

		query.setParameter("codUsu", filtro.getCodigoUsuario());
		query.setParameterList("gruposUsuarios", filtro.getCodigosUsuariosPermissao());

		// FIM CONFIGURACAO DOS PARAMETROS

		List<Object[]> lista = query.list();

		return lista;

	}

	@SuppressWarnings("unchecked")
	private List<Object[]> getListaPorFiltroProduto(FiltroPesquisaMonitoramento filtro) {

		StringBuilder hql = new StringBuilder();

		hql.append("select");
		hql.append(" obj.cod_oe,");
		hql.append(" sigla_oe,");
		hql.append(" nome_oe,");
		hql.append(" est.cod_est,");
		hql.append(" sigla_est,");
		hql.append(" nome_est,");

		// Resultado
		hql.append(" res.cod_res,");
		hql.append(" sigla_res,");
		hql.append(" site.nomeiett(nome_res, :exercicio, res.cod_res) nome_res,");
		hql.append(" res.rem,");
		hql.append(" site.orgaoUsu(res.cod_usu, mon_res.cod_usu) res_org_usu,");
		hql.append(" site.nomeUsu(res.cod_usu, mon_res.cod_usu) res_nome_usu,");
		hql.append(" mon_res.mes as res_mes,");
		hql.append(" mon_res.ano as res_ano,");
		hql.append(" mon_res.cod_cor as res_cod_cor,");
		hql.append(" mon_res.nome_cor as res_nome_cor,");

		// Produto
		hql.append(" prod.cod_prod,");
		hql.append(" prod.sigla_prod,");
		hql.append(" site.nomeiett(nome_prod, :exercicio, prod.cod_prod) nome_prod,");	
		hql.append(" site.orgaoUsu(prod.cod_usu, mon_prod.cod_usu) prod_org_usu,");
		hql.append(" site.nomeUsu(prod.cod_usu, mon_prod.cod_usu) prod_nome_usu,");
		hql.append(" mon_prod.mes as prod_mes,");
		hql.append(" mon_prod.ano as prod_ano,");
		hql.append(" mon_prod.cod_cor as prod_cod_cor,");
		hql.append(" mon_prod.nome_cor as prod_nome_cor,");
		hql.append(" ac.cod_ac,");
		hql.append(" ac.sigla_ac,");
		hql.append(" site.nomeiett(ac.nome_ac, :exercicio, ac.cod_ac) nome_ac,");
		hql.append(" ac.data_inicio as ac_data_inicio,");
		hql.append(" ac.data_termino as ac_data_termino,");
		hql.append(" ac.cod_org as ac_cod_org,");
		hql.append(" ac.cod_usu as ac_cod_usu,");
		hql.append(" site.orgaoUsu(ac.cod_usu, mon_ac.cod_usu) ac_org_usu,");
		hql.append(" site.nomeUsu(ac.cod_usu, mon_ac.cod_usu) ac_nome_usu,");
		hql.append(" mon_ac.mes as ac_mes,");
		hql.append(" mon_ac.ano as ac_ano,");
		hql.append(" mon_ac.cod_cor as ac_cod_cor,");
		hql.append(" mon_ac.nome_cor as ac_nome_cor,");
		hql.append(" mon_prod.parecer,");
		hql.append(" prod.data_inicio as prod_data_inicio,");
		hql.append(" prod.data_termino as prod_data_termino,");// 40
		hql.append(" mon_res.parecer as res_parecer,");// 41
		hql.append(" mon_ac.parecer as ac_parecer");// 42

		hql.append(" from site.tb_oe obj");
		hql.append(" left join site.tb_estrategia est on est.cod_oe = obj.cod_oe");
		hql.append(" left join site.tb_resultado res on res.cod_est = est.cod_est");
		hql.append(" left join site.tb_produto prod on prod.cod_res = res.cod_res");
		hql.append(" left join site.tb_acao ac on ac.cod_prod = prod.cod_prod");
		hql.append(" inner join site.tb_monitoramento mon_res on res.cod_res = mon_res.cod_iett");
		hql.append(" left join site.tb_monitoramento mon_prod on prod.cod_prod = mon_prod.cod_iett");
		hql.append(" left join site.tb_monitoramento mon_ac on ac.cod_ac = mon_ac.cod_iett");
		hql.append(" inner join tb_item_estrut_usuario_iettus perm on res.cod_res = perm.cod_iett");

		if (!filtro.getEtiquetasSelecionadas().isEmpty()) {
			hql.append(" inner join site.tb_etiqueta_rel eti on res.cod_res = eti.cod_iett");
		}

		hql.append(" where (mon_res.ultimo_parecer = 'Y' or mon_res.nao_monitorado = 'Y')");
		hql.append(" and ((mon_prod.cod_exe = (select max(p.cod_exe) from site.tb_monitoramento p where p.cod_iett = mon_prod.cod_iett and cod_exe <= :exercicio)");
		hql.append(" and ((mon_prod.ultimo_parecer = 'Y' or mon_prod.nao_monitorado = 'Y') )) or mon_prod.cod_iett is null)");

		// exercicio
		if (filtro.getExercicio() > 0) {
			hql.append(" and mon_res.cod_exe = :exercicio");
		}

		// Objetivo Estrategico 12
		if (!filtro.getCodigosObjetivosEstrategicos().isEmpty()) {
			hql.append(" and prod.cod_prod in (:codObj)");
		}

		// RESULTADOS E PRODUTOS QUE NUNCA FORAM MONITORADOS
		hql.append(" and ((select count(cod_iett) from site.tb_monitoramento where cod_iett = prod.cod_prod) > 0 or");
		hql.append(" ((select count(p.cod_prod) from site.tb_resultado r inner join site.tb_produto p on r.cod_res = p.cod_res where r.cod_res = res.cod_res) = 0");
		hql.append(" or (SELECT count(p.cod_prod) FROM site.tb_resultado r INNER JOIN site.tb_produto p ON r.cod_res = p.cod_res WHERE r.cod_res = res.cod_res) > 0))");
		// nao exibir os finalizados
		//hql.append(" and mon_res.cod_sit not in (19)");

		// Permissao
		hql.append(" and (perm.cod_usu in (:codUsu) or perm.cod_atb in (:gruposUsuarios))");
		hql.append(" order by sigla_oe, sigla_est, sigla_res, sigla_prod");

		SQLQuery query = this.session.createSQLQuery(hql.toString());

		// CONFIGURACAO DOS PARAMETROS

		if (!filtro.getCodigosObjetivosEstrategicos().isEmpty()) {
			query.setParameterList("codObj", filtro.getCodigosObjetivosEstrategicos());
		}

		if (filtro.getExercicio() > 0) {
			query.setParameter("exercicio", filtro.getExercicio());
		}

		query.setParameter("codUsu", filtro.getCodigoUsuario());
		query.setParameterList("gruposUsuarios", filtro.getCodigosUsuariosPermissao());

		// FIM CONFIGURACAO DOS PARAMETROS

		List<Object[]> lista = query.list();

		return lista;

	}

	private List<StatusPeriodoAcompanhamento> getStatusPeriodoAcompanhamento(Long codCor, String nomeCor) {
		List<StatusPeriodoAcompanhamento> statusPeriodoAcompanhamento = new ArrayList<StatusPeriodoAcompanhamento>();
		StatusPeriodoAcompanhamento status = new StatusPeriodoAcompanhamento(codCor, nomeCor);
		statusPeriodoAcompanhamento.add(status);
		return statusPeriodoAcompanhamento;
	}

	public List<ObjetivoEstrategico> getListaObjetivoEstrategicoFiltro(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria, List<Integer> resultadoStatus,
			Long codigoUsuario, List<Long> gruposUsuarios, boolean isIndicador, boolean apenasParecer, boolean prioritario, List<Long> secretarias, boolean relEncaminhamento, Date dataInicial,
			Date dataFinal, Long exercicio, boolean somenteREM, Long acompReferencia, boolean isDetalhamento, int painelIndicador) {

		List<String> etiquetasSelecionadas = new ArrayList<String>();

		if (etiqueta != null && !etiqueta.isEmpty()) {
			for (String s : etiqueta) {
				etiquetasSelecionadas.add(s);
			}
		}

		if (etiquetaPrioritaria != null && !etiquetaPrioritaria.isEmpty()) {
			for (String s : etiquetaPrioritaria) {
				etiquetasSelecionadas.add(s);
			}
		}

		FiltroPesquisaMonitoramento filtro = preencherFiltroPesquisaMonitoramentoWS(codigoObjetivoEstrategico, etiqueta, etiquetaPrioritaria, resultadoStatus, codigoUsuario, gruposUsuarios,
				prioritario, secretarias, exercicio, painelIndicador, acompReferencia);

		List<Object[]> lista = getListaPorFiltroGeral(filtro);

		Map<String, ObjetivoEstrategico> mapaObjetivo = new TreeMap<String, ObjetivoEstrategico>();
		Map<String, Estrategia> mapaEstrategia = new TreeMap<String, Estrategia>();
		Map<String, Resultado> mapaResultado = new TreeMap<String, Resultado>();
		Map<String, Produto> mapaProduto = new TreeMap<String, Produto>();
		Map<Long, Acao> mapaAcao = new TreeMap<Long, Acao>();

		ObjetivoEstrategico objetivoEstrategico = null;
		Estrategia estrategia = null;
		Resultado resultado = null;
		Produto produto = null;
		Orgao orgao = null;
		PeriodoAcompanhamento periodoAcompanhamento = null;
		Responsavel responsavel = null;
		List<Responsavel> coResponsavel = null;
		List<Responsavel> articulador = null;
		List<Produto> listaProduto = null;
		List<Resultado> listaResultado = null;
		List<Estrategia> listaEstrategia = null;

		for (Object[] obj : lista) {

			if (obj[0] != null) {
				objetivoEstrategico = new ObjetivoEstrategico();
				objetivoEstrategico.setCodigo(Long.parseLong(obj[0].toString()));
				objetivoEstrategico.setNome(obj[2].toString());
				objetivoEstrategico.setSiglaIett(obj[1].toString());
			}

			if (obj[3] != null) {
				estrategia = new Estrategia();
				estrategia.setCodigo(Long.parseLong(obj[3].toString()));
				estrategia.setNome(obj[5].toString());
				estrategia.setSiglaEstrategia(obj[4].toString());
				estrategia.setCodObj(objetivoEstrategico.getCodigo());
			}

			if (obj[6] != null) {
				resultado = new Resultado();
				resultado.setCodigo(Long.parseLong(obj[6].toString()));
				resultado.setCodEstrat(estrategia.getCodigo());
				resultado.setSiglaResultado(obj[7].toString());
				resultado.setNome(obj[8].toString());
				resultado.setMes(obj[12].toString());
				resultado.setAno(obj[13].toString());
				if (obj[14] != null) {
					resultado.setCodCor(Long.valueOf(obj[14].toString()));
				}
				if (obj[15] != null) {
					resultado.setNomeCor(obj[15].toString());
				}

				if (obj[9] != null && obj[9].toString().equals("Y")) {
					resultado.setPrioritario("S");
				}

				if (obj[10] != null) {

					orgao = new Orgao();
					orgao.setNome(obj[10].toString());

					responsavel = new Responsavel();
					// responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[11].toString());
					responsavel.setOrgao(orgao);

					resultado.responsavel = responsavel;

					coResponsavel = new ArrayList<Responsavel>();
					articulador = new ArrayList<Responsavel>();

					for (Responsavel responsavelArticulador : buscarCoResponsavel(resultado.getCodigo())) {
						if (responsavelArticulador.getCodTipo() != null && responsavelArticulador.getCodTipo().equals(2)) {
							articulador.add(responsavelArticulador);
						} else {
							if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
								coResponsavel.add(responsavelArticulador);
							}

						}
					}

					if (!coResponsavel.isEmpty())
						resultado.setCoResponsavel(coResponsavel);

					if (!articulador.isEmpty())
						resultado.setArticulacao(articulador);
				}

				periodoAcompanhamento = new PeriodoAcompanhamento(0L, "", resultado.getMes(), resultado.getAno(), resultado.getCodCor() == null ? 0L : resultado.getCodCor(),
						resultado.getNomeCor() == null ? "N&atilde;o Monitorado" : resultado.getNomeCor(), 0L);
				periodoAcompanhamento.setAno(Integer.parseInt(resultado.getAno()));
				periodoAcompanhamento.setMes(resultado.getMes());

				resultado.addPeriodoAcompanhamento(periodoAcompanhamento);

			}

			if (obj[16] != null) {
				produto = new Produto();
				produto.setCodigo(Long.parseLong(obj[16].toString()));
				produto.setNome(obj[18].toString());

				periodoAcompanhamento = new PeriodoAcompanhamento();

				if (obj[21] != null) {
					produto.setMes(obj[21].toString());
					periodoAcompanhamento.setMes(produto.getMes());
				}
				if (obj[22] != null) {
					produto.setAno(obj[22].toString());
					periodoAcompanhamento.setAno(Integer.parseInt(produto.getAno()));
				}

				if (obj[23] != null) {
					produto.setCodCor(Long.valueOf(obj[23].toString()));
				}
				if (obj[24] != null) {
					produto.setNomeCor(obj[24].toString());
				}

				produto.setCodResultado(resultado.getCodigo());
				produto.setSiglaProduto(obj[17].toString());
				if (obj[20] != null) {
					orgao = new Orgao();
					orgao.setNome(obj[19].toString());

					responsavel = new Responsavel();
					// responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[20].toString());
					responsavel.setOrgao(orgao);

					produto.responsavel = responsavel;

					coResponsavel = new ArrayList<Responsavel>();
					articulador = new ArrayList<Responsavel>();

					for (Responsavel responsavelArticulador : buscarCoResponsavel(produto.getCodigo())) {
						if (responsavelArticulador.getCodTipo().equals(2)) {
							articulador.add(responsavelArticulador);
						} else {
							if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
								coResponsavel.add(responsavelArticulador);
							}
						}
					}
					if (!coResponsavel.isEmpty())
						produto.setCoResponsavel(coResponsavel);
				}

				if (obj[25] != null) {
					periodoAcompanhamento = new PeriodoAcompanhamento(0L, "", produto.getMes(), produto.getAno(), produto.getCodCor(), produto.getNomeCor(), 0L);
				}

				produto.addPeriodosAcompanhamento(periodoAcompanhamento);

			}

			if (mapaObjetivo.get(objetivoEstrategico.getSiglaIett()) == null) {
				mapaObjetivo.put(objetivoEstrategico.getSiglaIett(), objetivoEstrategico);
			}

			if (mapaEstrategia.get(estrategia.getSiglaEstrategia() + "_" + objetivoEstrategico.getCodigo()) == null) {
				mapaEstrategia.put(estrategia.getSiglaEstrategia() + "_" + objetivoEstrategico.getCodigo(), estrategia);
			}

			if (mapaResultado.get(resultado.getSiglaResultado() + "_" + estrategia.getCodigo()) == null) {
				mapaResultado.put(resultado.getSiglaResultado() + "_" + estrategia.getCodigo(), resultado);
			}

			if (produto != null) {
				if (mapaProduto.get(produto.getSiglaProduto() + "_" + resultado.getCodigo()) == null) {
					mapaProduto.put(produto.getSiglaProduto() + "_" + resultado.getCodigo(), produto);
				}
			}

		}

		listaProduto = populaAcao(mapaProduto, mapaAcao, false);
		listaResultado = populaProduto(mapaResultado, listaProduto);
		listaEstrategia = populaResultado(mapaEstrategia, listaResultado);
		populaEstrategia(mapaObjetivo, listaEstrategia);

		return new ArrayList<ObjetivoEstrategico>(mapaObjetivo.values());

	}

	public List<ObjetivoEstrategico> getListaObjetivoEstrategicoFiltroPE(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria,
			List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios, boolean isIndicador, boolean apenasParecer, boolean prioritario, List<Long> secretarias,
			boolean relEncaminhamento, Date dataInicial, Date dataFinal, Long exercicio, boolean somenteREM, Long acompReferencia, boolean isDetalhamento, int painelIndicador) {

		List<String> etiquetasSelecionadas = new ArrayList<String>();

		if (etiqueta != null && !etiqueta.isEmpty()) {
			for (String s : etiqueta) {
				etiquetasSelecionadas.add(s);
			}
		}

		if (etiquetaPrioritaria != null && !etiquetaPrioritaria.isEmpty()) {
			for (String s : etiquetaPrioritaria) {
				etiquetasSelecionadas.add(s);
			}
		}

		FiltroPesquisaMonitoramento filtro = preencherFiltroPesquisaMonitoramentoWS(codigoObjetivoEstrategico, etiqueta, etiquetaPrioritaria, resultadoStatus, codigoUsuario, gruposUsuarios,
				prioritario, secretarias, exercicio, painelIndicador, acompReferencia);

		Long init = System.currentTimeMillis();
		Long end = 0L;
		Long diff = 0L;
		List<Object[]> lista = getListaPorFiltroRelatorio(filtro);
		end = System.currentTimeMillis();
		diff = end - init;
		System.out.println("Filtro - Demorou: " + (diff / 1000) + " segundos");

		Map<String, ObjetivoEstrategico> mapaObjetivo = new TreeMap<String, ObjetivoEstrategico>();
		Map<String, Estrategia> mapaEstrategia = new TreeMap<String, Estrategia>();
		Map<String, Resultado> mapaResultado = new TreeMap<String, Resultado>();
		Map<String, Produto> mapaProduto = new TreeMap<String, Produto>();
		Map<Long, Acao> mapaAcao = new TreeMap<Long, Acao>();

		ObjetivoEstrategico objetivoEstrategico = null;
		Estrategia estrategia = null;
		Resultado resultado = null;
		Produto produto = null;
		Orgao orgao = null;
		Acao acao = null;
		PeriodoAcompanhamento periodoAcompanhamento = null;
		Responsavel responsavel = null;
		List<Responsavel> coResponsavel = null;
		List<Responsavel> articulador = null;
		List<Produto> listaProduto = null;
		List<Resultado> listaResultado = null;
		List<Estrategia> listaEstrategia = null;
		
		List<AcompReferenciaAref> listaAref = getAcompReferenciaArefList();

		init = System.currentTimeMillis();
		for (Object[] obj : lista) {

			if (obj[0] != null) {
				objetivoEstrategico = new ObjetivoEstrategico();
				objetivoEstrategico.setCodigo(Long.parseLong(obj[0].toString()));
				objetivoEstrategico.setNome(obj[2].toString());
				objetivoEstrategico.setSiglaIett(obj[1].toString());
			}

			if (obj[3] != null) {
				estrategia = new Estrategia();
				estrategia.setCodigo(Long.parseLong(obj[3].toString()));
				estrategia.setNome(obj[5].toString());
				estrategia.setSiglaEstrategia(obj[4].toString());
				estrategia.setCodObj(objetivoEstrategico.getCodigo());
			}

			if (obj[6] != null) {
				resultado = new Resultado();
				resultado.setCodigo(Long.parseLong(obj[6].toString()));
				resultado.setCodEstrat(estrategia.getCodigo());
				resultado.setSiglaResultado(obj[7].toString());
				resultado.setNome(obj[8].toString());
				resultado.setMes(obj[12].toString());
				resultado.setAno(obj[13].toString());

				//if(obj[46] != null && obj[46].equals('Y')) {
				if(obj[31] != null && obj[31].equals('Y')) {
					resultado.setSituacao(getSituacao("19"));
				//}else if(obj[41] != null) {
				}else if(obj[28] != null) {
					resultado.setSituacao(getSituacao(obj[28].toString()));
				}else {
					resultado.setSituacao(getSituacao("6"));					
				}
				
				
				if (obj[14] != null) {
					resultado.setCodCor(Long.valueOf(obj[14].toString()));
				}
				if (obj[15] != null) {
					resultado.setNomeCor(obj[15].toString());
				}

				if (obj[9] != null && obj[9].toString().equals("Y")) {
					resultado.setPrioritario("S");
				}

				if (obj[10] != null) {

					orgao = new Orgao();
					orgao.setNome(obj[10].toString());

					responsavel = new Responsavel();
					// responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[11].toString());
					responsavel.setOrgao(orgao);

					resultado.responsavel = responsavel;

					coResponsavel = new ArrayList<Responsavel>();
					articulador = new ArrayList<Responsavel>();

					for (Responsavel responsavelArticulador : buscarCoResponsavel(resultado.getCodigo())) {
						if (responsavelArticulador.getCodTipo() != null && responsavelArticulador.getCodTipo().equals(2)) {
							articulador.add(responsavelArticulador);
						} else {
							if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
								coResponsavel.add(responsavelArticulador);
							}

						}
					}

					if (!coResponsavel.isEmpty())
						resultado.setCoResponsavel(coResponsavel);

					if (!articulador.isEmpty())
						resultado.setArticulacao(articulador);
				}
				
				periodoAcompanhamento = new PeriodoAcompanhamento();

				//if (obj[43] != null) {
				if (obj[30] != null) {
					periodoAcompanhamento = new PeriodoAcompanhamento(0L, montaParecer(limpaParecer(obj[30].toString()), resultado.getCodigo(), listaAref) , resultado.getMes(), resultado.getAno(), resultado.getCodCor() == null ? 0L
							: resultado.getCodCor(), resultado.getNomeCor() == null ? "N&atilde;o Monitorado" : resultado.getNomeCor(), 0L);
				}
				if(resultado.getAno() != null){
					periodoAcompanhamento.setAno(Integer.parseInt(resultado.getAno()));
				}
				if(resultado.getMes() != null){
					periodoAcompanhamento.setMes(resultado.getMes());
				}

				resultado.addPeriodoAcompanhamento(periodoAcompanhamento);

			}

			if (obj[16] != null) {

				produto = new Produto();
				produto.setCodigo(Long.parseLong(obj[16].toString()));
				produto.setSiglaProduto(obj[17].toString());
				produto.setNome(obj[18].toString());

				periodoAcompanhamento = new PeriodoAcompanhamento();

				if (obj[21] != null) {
					produto.setMes(obj[21].toString());
					periodoAcompanhamento.setMes(produto.getMes());
				}
				if (obj[22] != null) {
					produto.setAno(obj[22].toString());
					periodoAcompanhamento.setAno(Integer.parseInt(produto.getAno()));
				}

				if (obj[23] != null) {
					produto.setCodCor(Long.valueOf(obj[23].toString()));
				}
				if (obj[24] != null) {
					produto.setNomeCor(obj[24].toString());
				}

				produto.setCodResultado(resultado.getCodigo());
				if (obj[20] != null) {
					orgao = new Orgao();
					orgao.setNome(obj[19].toString());

					responsavel = new Responsavel();
					// responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[20].toString());
					responsavel.setOrgao(orgao);

					produto.responsavel = responsavel;

					coResponsavel = new ArrayList<Responsavel>();
					articulador = new ArrayList<Responsavel>();

					for (Responsavel responsavelArticulador : buscarCoResponsavel(produto.getCodigo())) {
						if (responsavelArticulador.getCodTipo().equals(2)) {
							articulador.add(responsavelArticulador);
						} else {
							if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
								coResponsavel.add(responsavelArticulador);
							}
						}
					}
					if (!coResponsavel.isEmpty())
						produto.setCoResponsavel(coResponsavel);
				}

				//if (obj[38] != null) {
				if (obj[25] != null) {
					periodoAcompanhamento = new PeriodoAcompanhamento(0L, limpaParecer(obj[25].toString()), produto.getMes(), produto.getAno(), produto.getCodCor(), produto.getNomeCor(), 0L);
				}

				produto.addPeriodosAcompanhamento(periodoAcompanhamento);

				//if(obj[47] != null && obj[47].equals('Y')) {
				if(obj[32] != null && obj[32].equals('Y')) {
					produto.setSituacao(getSituacao("19"));
				}else if(obj[29] != null) {
					produto.setSituacao(getSituacao(obj[29].toString()));
				}else {
					produto.setSituacao(getSituacao("6"));					
				}


			}

//			if (obj[25] != null) {
//				acao = new Acao(Long.parseLong(obj[25].toString()), obj[27].toString(), produto.getCodigo(), obj[26].toString());
//				if (obj[45] != null) {
//					acao.setSituacao(getSituacao(obj[45].toString()));
//				} else {
//					//TODO Quando a situação da Ação vier nula o que eu ponho?
//					acao.setSituacao(getSituacao("6"));
//				}
//				mapaAcao.put(acao.getCodigo(), acao);
//			}

			if (mapaObjetivo.get(objetivoEstrategico.getSiglaIett()) == null) {
				mapaObjetivo.put(objetivoEstrategico.getSiglaIett(), objetivoEstrategico);
			}

			if (mapaEstrategia.get(estrategia.getSiglaEstrategia() + "_" + objetivoEstrategico.getCodigo()) == null) {
				mapaEstrategia.put(estrategia.getSiglaEstrategia() + "_" + objetivoEstrategico.getCodigo(), estrategia);
			}

			if (mapaResultado.get(resultado.getSiglaResultado() + "_" + estrategia.getCodigo()) == null) {
				mapaResultado.put(resultado.getSiglaResultado() + "_" + estrategia.getCodigo(), resultado);
			}

			if (produto != null) {
				if (mapaProduto.get(produto.getSiglaProduto() + "_" + resultado.getCodigo()) == null) {
					mapaProduto.put(produto.getSiglaProduto() + "_" + resultado.getCodigo(), produto);
				}
			}

		}

		end = System.currentTimeMillis();
		diff = end - init;
		System.out.println("Percorrer a lista Demorou: " + (diff / 1000) + " segundos");

		init = System.currentTimeMillis();
		listaProduto = populaAcaoComIndicador(mapaProduto, mapaAcao);
		end = System.currentTimeMillis();
		diff = end - init;
		System.out.println("populaAcaoComIndicador Demorou: " + (diff / 1000) + " segundos");

		init = System.currentTimeMillis();
		listaResultado = populaProduto(mapaResultado, listaProduto);
		end = System.currentTimeMillis();
		diff = end - init;
		System.out.println("populaProduto Demorou: " + (diff / 1000) + " segundos");

		init = System.currentTimeMillis();
		try {
			listaEstrategia = populaResultadoComIndicador(mapaEstrategia, listaResultado, isIndicador, apenasParecer, relEncaminhamento, dataInicial, dataFinal, somenteREM, isDetalhamento, 0, exercicio);
		} catch (Exception e) {
			e.printStackTrace();
		}
		end = System.currentTimeMillis();
		diff = end - init;
		System.out.println("populaResultadoComIndicador Demorou: " + (diff / 1000) + " segundos");

		init = System.currentTimeMillis();
		populaEstrategia(mapaObjetivo, listaEstrategia);
		end = System.currentTimeMillis();
		diff = end - init;
		System.out.println("populaEstrategia Demorou: " + (diff / 1000) + " segundos");

		return new ArrayList<ObjetivoEstrategico>(mapaObjetivo.values());

	}

	private String getSituacao(String situacao) {
		Map<String, String> situacoes = new HashMap<String, String>();
		situacoes.put("2", "Alcan\u00e7ado");
		situacoes.put("4", "Suspenso");
		situacoes.put("6", "Em andamento");
		situacoes.put("15", "N\u00e3o Iniciada");
		situacoes.put("16", "Em Atraso");
		situacoes.put("17", "Cancelado");
		situacoes.put("18", "Realizado Parcialmente");
		situacoes.put("19", "Finalizado");
		String retorno = null;
		retorno = situacoes.get(situacao) != null ? situacoes.get(situacao) : situacao;
		return retorno;
	}

	public Produto listarProduto(Integer codigoProduto, Long codigoUsuario, List<Long> gruposUsuarios, String uiid, Long exercicio, Long acompReferencia) {

		this.uuid = uiid;

		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();

		codigoObjetivoEstrategico.add(codigoProduto);

		List<String> etiqueta = new ArrayList<String>();

		List<Integer> resultadoStatus = new ArrayList<Integer>();

		List<Long> secretarias = new ArrayList<Long>();

		FiltroPesquisaMonitoramento filtro = preencherFiltroPesquisaMonitoramentoWS(codigoObjetivoEstrategico, etiqueta, etiqueta, resultadoStatus, codigoUsuario, gruposUsuarios, false, secretarias,
				exercicio, 0, acompReferencia);

		List<Object[]> lista = getListaPorFiltroProduto(filtro);

		Map<String, Produto> mapaProduto = new TreeMap<String, Produto>();
		Map<Long, Acao> mapaAcao = new TreeMap<Long, Acao>();
		// Map<Long, Atividade> mapaAtividade = new TreeMap<Long, Atividade>();

		Produto produto = null;
		Acao acao = null;
		Orgao orgao = null;
		Responsavel responsavel = null;
		PeriodoAcompanhamento periodoAcompanhamento = null;
		List<Responsavel> coResponsavel = null;
		List<Responsavel> articulador = null;

		List<AcompReferenciaAref> listaAref = getAcompReferenciaArefList();

		for (Object[] obj : lista) {

			if (obj[16] != null && produto == null) {
				produto = new Produto();
				produto.setCodigo(Long.parseLong(obj[16].toString()));
				produto.setNome(obj[18].toString());
				produto.setMes(obj[21] != null ? obj[21].toString() : "");
				produto.setAno(obj[22] != null ? obj[22].toString() : "0");
				produto.setCodCor(obj[23] != null ? Long.parseLong(obj[23].toString()) : 0L);
				produto.setNomeCor(obj[24] != null ? obj[24].toString() : "N&atilde;o Monitorado");
				produto.setCodResultado(Long.parseLong(obj[6].toString()));
				produto.setSiglaProduto(obj[17].toString());
				produto.setParecer(obj[38] != null ? montaParecer(obj[38].toString(), produto.getCodigo(), listaAref) : "");

				produto.setDataInicio((Date) obj[39]);
				if (obj[40] != null)
					produto.setDataFim((Date) obj[40]);

				if (obj[19] != null) {
					orgao = new Orgao();
					orgao.setNome(obj[19].toString());

					responsavel = new Responsavel();
					responsavel.setNome(obj[20] != null ? obj[20].toString() : "");
					responsavel.setOrgao(orgao);

					produto.responsavel = responsavel;

					coResponsavel = new ArrayList<Responsavel>();
					articulador = new ArrayList<Responsavel>();

					for (Responsavel responsavelArticulador : buscarCoResponsavel(produto.getCodigo())) {
						if (responsavelArticulador.getCodTipo().equals(2)) {
							articulador.add(responsavelArticulador);
						} else {
							if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
								coResponsavel.add(responsavelArticulador);
							}

						}
					}

					if (!coResponsavel.isEmpty())
						produto.setCoResponsavel(coResponsavel);
				}

				periodoAcompanhamento = new PeriodoAcompanhamento(0L, produto.getParecer(), produto.getMes(), produto.getAno(), produto.getCodCor(), produto.getNomeCor(), 0L);

				produto.addPeriodosAcompanhamento(periodoAcompanhamento);

			}

			if (obj[25] != null) {
				acao = new Acao(Long.parseLong(obj[25].toString()), obj[27].toString(), produto.getCodigo(), obj[26].toString());
				acao.setCodProd(produto.getCodigo());
				orgao = new Orgao();
				orgao.setNome(obj[32] != null ? obj[32].toString() : "");
				responsavel = new Responsavel();
				responsavel.setNome(obj[33] != null ? obj[33].toString() : "");
				responsavel.setOrgao(orgao);
				acao.responsavel = responsavel;

				acao.setDataInicio((Date) obj[28]);
				if (obj[29] != null)
					acao.setDataFim((Date) obj[29]);

				periodoAcompanhamento = new PeriodoAcompanhamento();
				periodoAcompanhamento.setCodCor(obj[36] != null ? Long.parseLong(obj[36].toString()) : 0L);
				periodoAcompanhamento.setNomeCor(obj[37] != null ? obj[37].toString() : "N&atilde;o Monitorado");
				periodoAcompanhamento.setStatusPeriodoAcompanhamento(getStatusPeriodoAcompanhamento(periodoAcompanhamento.getCodCor(), periodoAcompanhamento.getNomeCor()));
				periodoAcompanhamento.setParecer(obj[38] != null ? montaParecer(obj[38].toString(), acao.getCodigo(), listaAref) : "");
				periodoAcompanhamento.setAno(obj[35] != null ? Integer.parseInt(obj[35].toString()) : 0);
				periodoAcompanhamento.setMes(obj[34] != null ? obj[34].toString() : "");
				periodoAcompanhamento.setCodArel(0L);

				acao.addPeriodosAcompanhamento(periodoAcompanhamento);

				mapaAcao.put(acao.getCodigo(), acao);
			}

			if (mapaProduto.get(produto.getSiglaProduto() + "_" + produto.getCodResultado()) == null) {
				mapaProduto.put(produto.getSiglaProduto() + "_" + produto.getCodResultado(), produto);
			}

		}

		populaAcaoComIndicador(mapaProduto, mapaAcao);

		return produto;

	}

	public Resultado listarResultado(Integer codigoResultado, Long codigoUsuario, List<Long> gruposUsuarios, String uuid, Long exercicio, Object object) throws ECARException {

		this.uuid = uuid;

		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();

		codigoObjetivoEstrategico.add(codigoResultado);

		List<String> etiqueta = new ArrayList<String>();

		List<Integer> resultadoStatus = new ArrayList<Integer>();

		List<Long> secretarias = new ArrayList<Long>();

		Long acompReferencia = 0L;

		FiltroPesquisaMonitoramento filtro = preencherFiltroPesquisaMonitoramentoWS(codigoObjetivoEstrategico, etiqueta, etiqueta, resultadoStatus, codigoUsuario, gruposUsuarios, false, secretarias,
				exercicio, 0, acompReferencia);

		List<Object[]> lista = getListaPorFiltroResultado(filtro);

		Map<String, Resultado> mapaResultado = new TreeMap<String, Resultado>();
		Map<String, Produto> mapaProduto = new TreeMap<String, Produto>();
		Map<Long, Acao> mapaAcao = new TreeMap<Long, Acao>();

		Resultado resultado = null;
		Produto produto = null;
		PeriodoAcompanhamento periodoAcompanhamento = null;
		Orgao orgao = null;

		List<AcompReferenciaAref> listaAref = getAcompReferenciaArefList();

		for (Object[] obj : lista) {

			if (obj[6] != null && resultado == null) {
				resultado = new Resultado();
				resultado.setCodigo(codigoResultado);
				resultado.setCodEstrat(Long.parseLong(obj[3].toString()));
				resultado.setSiglaResultado(obj[7].toString());
				resultado.setNome(obj[8].toString());
				if (obj[9] != null && obj[9].toString().contains("Y")) {
					resultado.setPrioritario("S");
				}
				resultado.setMes(obj[12] != null ? obj[12].toString() : "");
				resultado.setAno(obj[13] != null ? obj[13].toString() : "0");
				resultado.setCodCor(obj[14] != null ? Long.parseLong(obj[14].toString()) : 0L);
				resultado.setNomeCor(obj[15] != null ? obj[15].toString() : "N&atilde;o Monitorado");

				// TODO Esta faltando os SET ATENCAO

				resultado.setDataInicio((Date) obj[28]);
				if (obj[29] != null)
					resultado.setDataFim((Date) obj[29]);

				if (obj[10] != null) {

					orgao = new Orgao();
					orgao.setNome(obj[10].toString());

					Responsavel responsavel = new Responsavel();
					// TODO Esta faltando o codigo do responsavel
					// responsavel.setCodigo(Long.parseLong(obj[10].toString()));
					responsavel.setNome(obj[11].toString());
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

					if (!coResponsavel.isEmpty())
						resultado.setCoResponsavel(coResponsavel);

					if (!articulador.isEmpty())
						resultado.setArticulacao(articulador);
				}

				if (obj[41] != null) {
					resultado.setParecer(montaParecer(obj[41].toString(), resultado.getCodigo(), listaAref));
				}

				periodoAcompanhamento = new PeriodoAcompanhamento(0L, resultado.getParecer(), resultado.getMes(), resultado.getAno(), resultado.getCodCor(), resultado.getNomeCor(), 0L);

				resultado.addPeriodoAcompanhamento(periodoAcompanhamento);

			}

			if (obj[16] != null) {
				produto = new Produto();
				produto.setCodigo(Long.parseLong(obj[16].toString()));
				produto.setNome(obj[18].toString());
				produto.setMes(obj[21] != null ? obj[21].toString() : "");
				produto.setAno(obj[22] != null ? obj[22].toString() : "0");
				produto.setCodCor(obj[23] != null ? Long.parseLong(obj[23].toString()) : 0L);
				produto.setNomeCor(obj[24] != null ? obj[24].toString() : "N&atilde;o Monitorado");
				produto.setCodResultado(resultado.getCodigo());
				produto.setSiglaProduto(obj[17].toString());
				produto.setParecer(obj[38] != null ? montaParecer(obj[38].toString(), produto.getCodigo(), listaAref) : "");
				produto.setDataInicio((Date) obj[39]);
				if (obj[40] != null) {
					produto.setDataFim((Date) obj[40]);
				}

				if (obj[19] != null) {
					orgao = new Orgao();
					orgao.setNome(obj[19].toString());

					Responsavel responsavel = new Responsavel();
					responsavel.setNome(obj[20] != null ? obj[20].toString() : "");
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

					if (!coResponsavel.isEmpty())
						produto.setCoResponsavel(coResponsavel);
				}

				periodoAcompanhamento = new PeriodoAcompanhamento(0L, produto.getParecer(), produto.getMes(), produto.getAno(), produto.getCodCor(), produto.getNomeCor(), 0L);

				produto.addPeriodosAcompanhamento(periodoAcompanhamento);

			}

			if (obj[25] != null) {
				Acao acao = new Acao(Long.parseLong(obj[25].toString()), obj[27].toString(), produto.getCodigo(), obj[26].toString());

				acao.setDataInicio((Date) obj[28]);
				if (obj[29] != null)
					acao.setDataFim((Date) obj[29]);

				if (obj[32] != null) {

					orgao = new Orgao();
					orgao.setNome(obj[32].toString());

					Responsavel responsavel = new Responsavel();
					responsavel.setCodigo(Long.parseLong(obj[31].toString()));
					responsavel.setNome(obj[33].toString());
					responsavel.setOrgao(orgao);

					acao.responsavel = responsavel;

				}

				acao.setDataInicio((Date) obj[28]);
				if (obj[29] != null)
					acao.setDataFim((Date) obj[29]);

				periodoAcompanhamento = new PeriodoAcompanhamento();

				if (obj[35] != null) {
					periodoAcompanhamento.setAno(Integer.parseInt(obj[35].toString()));
				}
				if (obj[34] != null) {
					periodoAcompanhamento.setMes(obj[34].toString());
				}

				acao.addPeriodosAcompanhamento(periodoAcompanhamento);

				mapaAcao.put(acao.getCodigo(), acao);

			}

			if (produto != null) {
				if (mapaProduto.get(produto.getSiglaProduto() + "_" + produto.getCodResultado()) == null) {
					mapaProduto.put(produto.getSiglaProduto() + "_" + produto.getCodResultado(), produto);
				}
			}

			if (mapaResultado.get(resultado.getSiglaResultado() + "_" + resultado.getCodEstrat()) == null) {
				mapaResultado.put(resultado.getSiglaResultado() + "_" + resultado.getCodEstrat(), resultado);
			}

		}

		if (lista != null & !lista.isEmpty()) {

			List<Produto> listaProduto = populaAcao(mapaProduto, mapaAcao, true);

			List<Resultado> listaResultado = populaProduto(mapaResultado, listaProduto);

			resultado = listaResultado.get(0);

			// Comentarios
			resultado.setComentarios(daoIettAcao.loadComentarioWS(resultado.getCodigo(), null, null));
			// FIM Comentarios

//			List<PeriodoAcompanhamento> periodoList = recuperaPeriodosAcompanhamento(resultado.getCodigo(), 0L, resultadoStatus);
			List<PeriodoAcompanhamento> periodoList = recuperaPeriodosAcompanhamentoSite(resultado.getCodigo(), filtro.getExercicio());
			for (PeriodoAcompanhamento pa : periodoList) {
				IndicadorMonitoramentoBean indMon = new IndicadorMonitoramentoBean();
				pa.indMonitoramento = indMon.ListaIndicadores(pa, getIndicadoresSite(resultado.getCodigo()));
			}

			resultado.setPeriodoAcompanhamento(periodoList);

		}

		return resultado;

	}

	public List<ObjetivoEstrategico> listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria,
			List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios, boolean isIndicador, boolean apenasParecer, boolean prioritario, List<Long> secretarias,
			boolean relEncaminhamento, Date dataInicial, Date dataFinal, Long exercicio, boolean somenteREM, Long acompReferencia, boolean isDetalhamento, int painelIndicador) throws ECARException {

		return getListaObjetivoEstrategicoFiltroPE(codigoObjetivoEstrategico, etiqueta, etiquetaPrioritaria, resultadoStatus, codigoUsuario, gruposUsuarios, isIndicador, apenasParecer, prioritario,
				secretarias, relEncaminhamento, dataInicial, dataFinal, exercicio, somenteREM, acompReferencia, isDetalhamento, painelIndicador);

	}
	
	@SuppressWarnings("unchecked")
	public List<ResultadoStatusContar> listarResultadoStatus(UsuarioUsu usuario, Long exercicio) {
		String sql = "select cod_cor as codigo, significado_cor as nome, count(cod_cor) as contar " +
				"from site.tb_monitoramento mon " +
				"inner join site.tb_resultado res on mon.cod_iett = res.cod_res " +
				"where cod_exe = :exercicio " +
				"and ultimo_parecer = 'Y' " +
				"group by significado_cor, cod_cor " +
				"union all " +
				"select 0 as codigo, 'NAO MONITORADO' as nome, count(cod_iett) as contar " +
				"from site.tb_monitoramento mon " +
				"inner join site.tb_resultado res on mon.cod_iett = res.cod_res " +
				"where cod_exe = :exercicio " +
				"and nao_monitorado = 'Y' ";
		
		SQLQuery q = this.session.createSQLQuery(sql.toString());
		
		if (exercicio != null) {
			q.setParameter("exercicio", exercicio);
		} else {
			q.setParameter("exercicio", exercicioDao.buscaCodigoExercicioCorrente());
		}
		
		q.setResultTransformer(Transformers.aliasToBean(ResultadoStatusContar.class));

		q.addScalar("contar", Hibernate.LONG);
		q.addScalar("codigo", Hibernate.LONG);
		q.addScalar("nome", Hibernate.STRING);

		List<ResultadoStatusContar> status = q.list();

		return status;
	}
	
	public List<ObjetivoEstrategico> listarObjetivoEstrategicoFiltroComIndicadorMonitoramentos(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria,
			List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios, boolean isIndicador, boolean apenasParecer, boolean prioritario, List<Long> secretarias,
			boolean relEncaminhamento, Date dataInicial, Date dataFinal, Long exercicio, boolean somenteREM, Long acompReferencia, boolean isDetalhamento, int painelIndicador) throws ECARException {

		return getListaObjetivoEstrategicoFiltroPEMonitoramentos(codigoObjetivoEstrategico, etiqueta, etiquetaPrioritaria, resultadoStatus, codigoUsuario, gruposUsuarios, isIndicador, apenasParecer, prioritario,
				secretarias, relEncaminhamento, dataInicial, dataFinal, exercicio, somenteREM, acompReferencia, isDetalhamento, painelIndicador);

	}

	public List<ObjetivoEstrategico> getListaObjetivoEstrategicoFiltroPEMonitoramentos(List<Integer> codigoObjetivoEstrategico, List<String> etiqueta, List<String> etiquetaPrioritaria,
			List<Integer> resultadoStatus, Long codigoUsuario, List<Long> gruposUsuarios, boolean isIndicador, boolean apenasParecer, boolean prioritario, List<Long> secretarias,
			boolean relEncaminhamento, Date dataInicial, Date dataFinal, Long exercicio, boolean somenteREM, Long acompReferencia, boolean isDetalhamento, int painelIndicador) {

		List<String> etiquetasSelecionadas = new ArrayList<String>();

		if (etiqueta != null && !etiqueta.isEmpty()) {
			for (String s : etiqueta) {
				etiquetasSelecionadas.add(s);
			}
		}

		if (etiquetaPrioritaria != null && !etiquetaPrioritaria.isEmpty()) {
			for (String s : etiquetaPrioritaria) {
				etiquetasSelecionadas.add(s);
			}
		}

		FiltroPesquisaMonitoramento filtro = preencherFiltroPesquisaMonitoramentoWS(codigoObjetivoEstrategico, etiqueta, etiquetaPrioritaria, resultadoStatus, codigoUsuario, gruposUsuarios,
				prioritario, secretarias, exercicio, painelIndicador, acompReferencia);

		Long init = System.currentTimeMillis();
		Long end = 0L;
		Long diff = 0L;
		List<Object[]> lista = getListaPorFiltroRelatorio(filtro);
		end = System.currentTimeMillis();
		diff = end - init;
		System.out.println("Filtro - Demorou: " + (diff / 1000) + " segundos");

		Map<String, ObjetivoEstrategico> mapaObjetivo = new TreeMap<String, ObjetivoEstrategico>();
		Map<String, Estrategia> mapaEstrategia = new TreeMap<String, Estrategia>();
		Map<String, Resultado> mapaResultado = new TreeMap<String, Resultado>();
		Map<String, Produto> mapaProduto = new TreeMap<String, Produto>();
		Map<Long, Acao> mapaAcao = new TreeMap<Long, Acao>();

		ObjetivoEstrategico objetivoEstrategico = null;
		Estrategia estrategia = null;
		Resultado resultado = null;
		Produto produto = null;
		Orgao orgao = null;
		Acao acao = null;
		PeriodoAcompanhamento periodoAcompanhamento = null;
		Responsavel responsavel = null;
		List<Responsavel> coResponsavel = null;
		List<Responsavel> articulador = null;
		List<Produto> listaProduto = null;
		List<Resultado> listaResultado = null;
		List<Estrategia> listaEstrategia = null;
		
		List<AcompReferenciaAref> listaAref = getAcompReferenciaArefList();

		init = System.currentTimeMillis();
		for (Object[] obj : lista) {

			if (obj[0] != null) {
				objetivoEstrategico = new ObjetivoEstrategico();
				objetivoEstrategico.setCodigo(Long.parseLong(obj[0].toString()));
				objetivoEstrategico.setNome(obj[2].toString());
				objetivoEstrategico.setSiglaIett(obj[1].toString());
			}

			if (obj[3] != null) {
				estrategia = new Estrategia();
				estrategia.setCodigo(Long.parseLong(obj[3].toString()));
				estrategia.setNome(obj[5].toString());
				estrategia.setSiglaEstrategia(obj[4].toString());
				estrategia.setCodObj(objetivoEstrategico.getCodigo());
			}

			if (obj[6] != null) {
				resultado = new Resultado();
				resultado.setCodigo(Long.parseLong(obj[6].toString()));

				List<PeriodoAcompanhamento> periodoList = recuperaPeriodosAcompanhamentoSite(resultado.getCodigo(), filtro.getExercicio());
				for (PeriodoAcompanhamento pa : periodoList) {
					IndicadorMonitoramentoBean indMon = new IndicadorMonitoramentoBean();
					pa.indMonitoramento = indMon.ListaIndicadores(pa, getIndicadoresSite(resultado.getCodigo()));
				}
				
				resultado.setCodEstrat(estrategia.getCodigo());
				resultado.setSiglaResultado(obj[7].toString());
				resultado.setNome(obj[8].toString());
				resultado.setMes(obj[12].toString());
				resultado.setAno(obj[13].toString());

				if(obj[31] != null && obj[31].equals('Y')) {
					resultado.setSituacao(getSituacao("19"));
				}else if(obj[28] != null) {
					resultado.setSituacao(getSituacao(obj[28].toString()));
				}else {
					resultado.setSituacao(getSituacao("6"));					
				}
				
				
				if (obj[14] != null) {
					resultado.setCodCor(Long.valueOf(obj[14].toString()));
				}
				if (obj[15] != null) {
					resultado.setNomeCor(obj[15].toString());
				}

				if (obj[9] != null && obj[9].toString().equals("Y")) {
					resultado.setPrioritario("S");
				}

				if (obj[10] != null) {

					orgao = new Orgao();
					orgao.setNome(obj[10].toString());

					responsavel = new Responsavel();
					responsavel.setNome(obj[11].toString());
					responsavel.setOrgao(orgao);

					resultado.responsavel = responsavel;

					coResponsavel = new ArrayList<Responsavel>();
					articulador = new ArrayList<Responsavel>();

					for (Responsavel responsavelArticulador : buscarCoResponsavel(resultado.getCodigo())) {
						if (responsavelArticulador.getCodTipo() != null && responsavelArticulador.getCodTipo().equals(2)) {
							articulador.add(responsavelArticulador);
						} else {
							if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
								coResponsavel.add(responsavelArticulador);
							}
						}
					}

					if (!coResponsavel.isEmpty())
						resultado.setCoResponsavel(coResponsavel);

					if (!articulador.isEmpty())
						resultado.setArticulacao(articulador);
				}
				
				periodoAcompanhamento = new PeriodoAcompanhamento();
				
				if (obj[30] != null) {
					periodoAcompanhamento = new PeriodoAcompanhamento(0L, montaParecer(limpaParecer(obj[30].toString()), resultado.getCodigo(), listaAref) , resultado.getMes(), resultado.getAno(), resultado.getCodCor() == null ? 0L
							: resultado.getCodCor(), resultado.getNomeCor() == null ? "N&atilde;o Monitorado" : resultado.getNomeCor(), 0L);
				}
				if(resultado.getAno() != null){
					periodoAcompanhamento.setAno(Integer.parseInt(resultado.getAno()));
				}
				if(resultado.getMes() != null){
					periodoAcompanhamento.setMes(resultado.getMes());
				}
				if (periodoList == null || periodoList.size() == 0) {
					periodoList.add(periodoAcompanhamento);
				} else {
					periodoList.set(0, periodoAcompanhamento);
				}
				resultado.setPeriodoAcompanhamento(periodoList);

				if (obj[16] != null) {
					produto = new Produto();
					produto.setCodigo(Long.parseLong(obj[16].toString()));
					produto = listarProduto(Integer.valueOf(produto.getCodigo().toString()), codigoUsuario, gruposUsuarios, uuid, exercicio, 0L);
					List<PeriodoAcompanhamento> periodoListProduto = recuperaPeriodosAcompanhamentoSite(produto.getCodigo(), filtro.getExercicio());
					produto.setPeriodoAcompanhamento(periodoListProduto);
					produto.setSiglaProduto(obj[17].toString());
					produto.setNome(obj[18].toString());

					periodoAcompanhamento = new PeriodoAcompanhamento();

					if (obj[21] != null) {
						produto.setMes(obj[21].toString());
						periodoAcompanhamento.setMes(produto.getMes());
					}
					if (obj[22] != null) {
						produto.setAno(obj[22].toString());
						periodoAcompanhamento.setAno(Integer.parseInt(produto.getAno()));
					}

					if (obj[23] != null) {
						produto.setCodCor(Long.valueOf(obj[23].toString()));
					}
					if (obj[24] != null) {
						produto.setNomeCor(obj[24].toString());
					}

					produto.setCodResultado(resultado.getCodigo());
					if (obj[20] != null) {
						orgao = new Orgao();
						orgao.setNome(obj[19].toString());

						responsavel = new Responsavel();
						responsavel.setNome(obj[20].toString());
						responsavel.setOrgao(orgao);

						produto.responsavel = responsavel;

						coResponsavel = new ArrayList<Responsavel>();
						articulador = new ArrayList<Responsavel>();

						for (Responsavel responsavelArticulador : buscarCoResponsavel(produto.getCodigo())) {
							if (responsavelArticulador.getCodTipo().equals(2)) {
								articulador.add(responsavelArticulador);
							} else {
								if (responsavelArticulador.getCodigo() != responsavel.getCodigo()) {
									coResponsavel.add(responsavelArticulador);
								}
							}
						}
						if (!coResponsavel.isEmpty())
							produto.setCoResponsavel(coResponsavel);
					}

					if (obj[25] != null) {
						periodoAcompanhamento = new PeriodoAcompanhamento(0L, limpaParecer(obj[25].toString()), produto.getMes(), produto.getAno(), produto.getCodCor(), produto.getNomeCor(), 0L);
					}
					if (periodoListProduto == null || periodoListProduto.size() == 0) {
						periodoListProduto .add(periodoAcompanhamento);
					} else {
						periodoListProduto.set(0, periodoAcompanhamento);
					}
					produto.setPeriodoAcompanhamento(periodoListProduto);

					if(obj[32] != null && obj[32].equals('Y')) {
						produto.setSituacao(getSituacao("19"));
					}else if(obj[29] != null) {
						produto.setSituacao(getSituacao(obj[29].toString()));
					}else {
						produto.setSituacao(getSituacao("6"));					
					}
					
					resultado.addProdutos(produto);
				}
			}

			if (mapaObjetivo.get(objetivoEstrategico.getSiglaIett()) == null) {
				mapaObjetivo.put(objetivoEstrategico.getSiglaIett(), objetivoEstrategico);
			}

			if (mapaEstrategia.get(estrategia.getSiglaEstrategia() + "_" + objetivoEstrategico.getCodigo()) == null) {
				mapaEstrategia.put(estrategia.getSiglaEstrategia() + "_" + objetivoEstrategico.getCodigo(), estrategia);
			}

			if (mapaResultado.get(resultado.getSiglaResultado() + "_" + estrategia.getCodigo()) == null) {
				mapaResultado.put(resultado.getSiglaResultado() + "_" + estrategia.getCodigo(), resultado);
			}

			if (resultado.getProdutos() != null && resultado.getProdutos().size() > 0) {
				produto = resultado.getProdutos().get(0);
				if (mapaProduto.get(produto.getSiglaProduto() + "_" + resultado.getCodigo()) == null) {
					mapaProduto.put(produto.getSiglaProduto() + "_" + resultado.getCodigo(), produto);
				}
			}

		}

		end = System.currentTimeMillis();
		diff = end - init;
		System.out.println("Percorrer a lista Demorou: " + (diff / 1000) + " segundos");

		init = System.currentTimeMillis();
		listaProduto = populaAcaoComIndicador(mapaProduto, mapaAcao);
		end = System.currentTimeMillis();
		diff = end - init;
		System.out.println("populaAcaoComIndicador Demorou: " + (diff / 1000) + " segundos");

		init = System.currentTimeMillis();
		listaResultado = populaProduto(mapaResultado, listaProduto);
		end = System.currentTimeMillis();
		diff = end - init;
		System.out.println("populaProduto Demorou: " + (diff / 1000) + " segundos");

		init = System.currentTimeMillis();
		try {
			listaEstrategia = populaResultadoComIndicador(mapaEstrategia, listaResultado, isIndicador, apenasParecer, relEncaminhamento, dataInicial, dataFinal, somenteREM, isDetalhamento, 0, exercicio);
		} catch (Exception e) {
			e.printStackTrace();
		}
		end = System.currentTimeMillis();
		diff = end - init;
		System.out.println("populaResultadoComIndicador Demorou: " + (diff / 1000) + " segundos");

		init = System.currentTimeMillis();
		populaEstrategia(mapaObjetivo, listaEstrategia);
		end = System.currentTimeMillis();
		diff = end - init;
		System.out.println("populaEstrategia Demorou: " + (diff / 1000) + " segundos");

		return new ArrayList<ObjetivoEstrategico>(mapaObjetivo.values());

	}

}
