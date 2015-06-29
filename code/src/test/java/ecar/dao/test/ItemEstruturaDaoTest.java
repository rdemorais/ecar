package ecar.dao.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.junit.Test;

import br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.PlanoAcaoServicePortProxy;
import br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAcaoCadastro;
import br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsAtividadeCadastro;
import br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsUsuarioAutenticacao;

import comum.util.Data;
import comum.util.EtiquetaUtils;
import comum.util.Util;

import ecar.api.facade.EcarData;
import ecar.api.facade.IndicadorResultado;
import ecar.api.facade.Previsto;
import ecar.dao.AcompRealFisicoDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.EstatisticaDao;
import ecar.dao.EstruturaDao;
import ecar.dao.EtiquetaDao;
import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstruturaAcaoDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.OrgaoDao;
import ecar.dao.SiteDao;
import ecar.dao.UsuarioDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ExercicioExe;
import ecar.pojo.ItemEstrtIndResulIettr;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.acompanhamentoEstrategico.Acao;
import ecar.pojo.acompanhamentoEstrategico.Atividade;
import ecar.pojo.acompanhamentoEstrategico.Comentario;
import ecar.pojo.acompanhamentoEstrategico.Estrategia;
import ecar.pojo.acompanhamentoEstrategico.Etiqueta;
import ecar.pojo.acompanhamentoEstrategico.Indicador;
import ecar.pojo.acompanhamentoEstrategico.IndicadorDetalhamento;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.pojo.acompanhamentoEstrategico.PeriodoAcompanhamento;
import ecar.pojo.acompanhamentoEstrategico.Produto;
import ecar.pojo.acompanhamentoEstrategico.Responsavel;
import ecar.pojo.acompanhamentoEstrategico.Resultado;
import ecar.pojo.acompanhamentoEstrategico.ResultadoStatusContar;
import ecar.pojo.dto.EstatisticaDTO;
import ecar.pojo.dto.SecretariaEstatisticaDTO;
import ecar.servico.RelatorioAcompanhamentoService;
import ecar.servlet.indicador.IndicadorMonitoramentoDto;
import ecar.servlet.indicador.IndicadorValorDto;
import ecar.servlet.relatorio.dto.SecretariaDTO;
import ecar.util.Dominios;

//burlar certificado invalido

public class ItemEstruturaDaoTest {

	// @Test
	public void testListIndicador() throws ECARException {
		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		List<Indicador> indicadores = dao.getListIndicador(2061L, false);
		for (Indicador indicador : indicadores) {
			System.out.println(indicador.getNome());
		}
	}

	// @Test
	public void testListarObjetivoEstrategicoFiltro() {

		Long init = System.currentTimeMillis();
		ItemEstruturaDao dao = new ItemEstruturaDao(null);

		// TESTE - dao.listarObjetivoEstrategicoFiltro
		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();
		// codigoObjetivoEstrategico.add(7);
		// codigoObjetivoEstrategico.add(10);
		// codigoObjetivoEstrategico.add(11);
		// codigoObjetivoEstrategico.add(12);
		// codigoObjetivoEstrategico.add(14);
		// codigoObjetivoEstrategico.add(15);
		// codigoObjetivoEstrategico.add(16);
		// codigoObjetivoEstrategico.add(17);
		// codigoObjetivoEstrategico.add(18);
		// codigoObjetivoEstrategico.add(19);
		// codigoObjetivoEstrategico.add(20);
		// codigoObjetivoEstrategico.add(21);
		// codigoObjetivoEstrategico.add(23);
		// codigoObjetivoEstrategico.add(692);
		// codigoObjetivoEstrategico.add(1823);
		// codigoObjetivoEstrategico.add(2005);
		List<String> etiqueta = new ArrayList<String>();
		// List<String> etiquetaPrioritaria = new ArrayList<String>();

		// etiqueta.add("TESTE_17092012");
		// etiquetaPrioritaria.add("REDE CEGONHA");

		List<String> etiquetaF = fonetizarEtiqueta(etiqueta);
		// List<String> prioritariaF = fonetizarEtiqueta(etiquetaPrioritaria);
		//		
		// etiquetaF.addAll(prioritariaF);
		//		
		List<Integer> resultadoStatus = new ArrayList<Integer>();
		// resultadoStatus.add(1);
		// resultadoStatus.add(0);

		List<Long> gruposUsuarios = new ArrayList<Long>();
		// gruposUsuarios.add(175L);
		// gruposUsuarios.add(125L);
		gruposUsuarios.add(134L);
		gruposUsuarios.add(19L);

		// Long codUsuario = 18L;
		// Long codUsuario = 64L;
		Long codUsuario = 1L;

		List<ObjetivoEstrategico> lista = dao.listarObjetivoEstrategicoFiltroMaisRapidoEuEspero(codigoObjetivoEstrategico, etiquetaF, null, resultadoStatus, codUsuario, gruposUsuarios, false, null, null, 0, null);

		for (ObjetivoEstrategico objetivoEstrategico : lista) {
			System.out.println("OE -> " + objetivoEstrategico.getSiglaIett() + " - " + objetivoEstrategico.getNome());
			for (Estrategia estrategia : objetivoEstrategico.getEstrategias()) {
				System.out.println("\t E ->" + estrategia.getSiglaEstrategia() + "-" + estrategia.getNome());
				for (Resultado resultado : estrategia.getResultados()) {
					System.out.println("\t \t R -> " + resultado.getSiglaResultado() + "-" + resultado.getNome() + " P: " + resultado.getPrioritario() + " A: " + resultado.isAtencao());

					if (resultado.periodoAcompanhamento.size() > 0) {
						// System.out.println("COR: " +
						// resultado.periodoAcompanhamento.get(0).getNomeCor());
						for (PeriodoAcompanhamento periodoAcompanhamento : resultado.periodoAcompanhamento) {
							System.out.println("COR: " + periodoAcompanhamento.getNomeCor());
							System.out.println(periodoAcompanhamento.getMes());
							System.out.println(periodoAcompanhamento.getCiclo());
							// System.out.println(periodoAcompanhamento.getParecer());
						}
					} else {
						System.out.println("COR: NÃO MONITORADO ");
					}

					String nome = "";
					String co = "";
					String orgao = "";
					if (resultado.responsavel != null) {
						nome = resultado.responsavel.toString();
						orgao = resultado.getResponsavel().orgao.getNome();
					}
					for (Responsavel responsavel : resultado.getCoResponsavel()) {
						co = responsavel.getNomeEOrgao() + ", ";
					}
					System.out.println("\t \t -> Resp: - " + nome /*
																 * + " / " +
																 * orgao
																 */);
					System.out.println("\t \t -> CO Resp: - " + co);
					for (Produto produto : resultado.getProdutos()) {
						System.out.println("\t \t \t P-> " + produto.getSiglaProduto() + "-" + produto.getNome());
						if (produto.periodoAcompanhamento.size() > 0) {
							System.out.println("COR: " + produto.periodoAcompanhamento.get(0).getNomeCor());
						} else {
							System.out.println("COR: NÃO MONITORADO ");
						}

						if (produto.responsavel != null) {
							nome = produto.responsavel.getNome();
						}
						for (Responsavel responsavel : produto.getCoResponsavel()) {
							co = responsavel.getNome() + ", ";
						}
						System.out.println("\t \t -> Resp: - " + nome);
						System.out.println("\t \t -> CO Resp: - " + co);
					}
				}
			}
		}

		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("Demorou " + (diff / 1000) + " segundos");
	}

	// @Test
	public void testListarResultadoSituacaoContar() {

		Long init = System.currentTimeMillis();
		ItemEstruturaDao dao = new ItemEstruturaDao(null);

		dao.listarResultadoSituacaoContar(null);

		// TESTE - dao.listarResultadoStatus
		List<ResultadoStatusContar> lista = dao.listarResultadoStatus(null, null);
		for (ResultadoStatusContar resultadoStatusContar : lista) {
			System.out.println(resultadoStatusContar.getContar() + " - " + resultadoStatusContar.getNome());
		}

		// dao.listarResultadoNaoMonitorado(null);

		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("Demorou " + (diff / 1000) + " segundos");
	}

	// @Test
	public void testListarObjetivoEstrategico() {

		Long init = System.currentTimeMillis();
		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		List<Long> gruposUsuarios = new ArrayList<Long>();
		gruposUsuarios.add(134L);
		gruposUsuarios.add(19L);

		Long codUsuario = 18L;
		// TESTE - dao.listarObjetivoEstrategico
		List<ObjetivoEstrategico> lista = dao.listarObjetivoEstrategico(null, 1L, gruposUsuarios);
		for (ObjetivoEstrategico objetivoEstrategico : lista) {
			System.out.println(objetivoEstrategico.getCodigo() + " - " + objetivoEstrategico.getNome());
		}

		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("Demorou " + (diff / 1000) + " segundos");
	}

	// @Test
	public void testListarEtiqueta() {

		Long init = System.currentTimeMillis();

		// TESTE - dao.listarEtiqueta
		EtiquetaDao dao = new EtiquetaDao();
		// List<Etiqueta> lista = dao.listarEtiqueta(null, "S", null);
		List<Etiqueta> lista = dao.listarEtiqueta(null, "N", EtiquetaUtils.fonetizar("TESTE"));
		for (Etiqueta et : lista) {
			System.out.println(et.getNome() + " -> " + et.getCategoria().getNome());
		}

		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("Demorou " + (diff / 1000) + " segundos");
	}

	// @Test
	public void testListarResultado() throws ECARException {

		Long init = System.currentTimeMillis();
		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		ExercicioDao daoExercicio = new ExercicioDao(null);
		List<Long> gruposUsuarios = new ArrayList<Long>();
		// gruposUsuarios.add(175L);
		// gruposUsuarios.add(125L);
		gruposUsuarios.add(134L);
		gruposUsuarios.add(19L);

		// Long codUsuario = 18L;
		// Long codUsuario = 64L;
		Long codUsuario = 1L;

		// TESTE - dao.listarResultado
		// List<PeriodoAcompanhamento> periodos =
		// dao.recuperaPeriodosAcompanhamento(8705L);
		// Resultado resultado = new Resultado();
		// if(periodos.size()>0){
		// resultado =
		// dao.listarResultado(8705,periodos.get(0).getMes(),Integer.toString(periodos.get(0).getAno())/*4507,
		// 1890 6316 1928*/, codUsuario, gruposUsuarios);
		// }else{
		// resultado = dao.listarResultado(8705,null,null/*4507, 1890 6316
		// 1928*/, codUsuario, gruposUsuarios);
		// }

		Resultado resultado = dao.listarResultado2(4485/*
														 * 2061 4905 3584 2777
														 * 3468 1894
														 *//*
															 * 4507, 1890 6316
															 * 1928
															 */, codUsuario, gruposUsuarios, "", daoExercicio.buscaCodigoExercicioCorrente(), null);
		// System.out.println(lista.getCodigo() + " - " + lista.getNome());
		System.out.println("\t  -> " + resultado.getSiglaResultado() + "-" + resultado.getNome() + " P: " + resultado.getPrioritario());
		if (resultado.periodoAcompanhamento != null) {
			// System.out.println("Mes: " +
			// resultado.periodoAcompanhamento.get(0).getMes());
			// System.out.println("ANO: " +
			// resultado.periodoAcompanhamento.get(0).getAno());
			for (PeriodoAcompanhamento periodoAcompanhamento : resultado.periodoAcompanhamento) {
				System.out.println("Mes: " + periodoAcompanhamento.getMes());
				System.out.println("ANO: " + periodoAcompanhamento.getAno());
				System.out.println("CICLO: " + periodoAcompanhamento.getCiclo());
				for (IndicadorMonitoramentoDto ind : periodoAcompanhamento.indMonitoramento) {
					// System.out.println("Valores-> " +
					// ind.getValores().size());
					for (IndicadorValorDto indi : ind.getValores()) {
						System.out.println("M -> " + indi.getPrevisto());
						System.out.println("R -> " + indi.getRealizado());
					}
				}
			}
		}

		String nome = "";
		String co = "";
		if (resultado.responsavel != null) {
			nome = resultado.responsavel.getNome();
		}
		for (Responsavel responsavel : resultado.getCoResponsavel()) {
			co = responsavel.getNome() + ", ";
		}
		System.out.println("\t -> Resp: - " + nome);
		System.out.println("\t -> CO Resp: - " + co);
		for (Produto produto : resultado.getProdutos()) {
			System.out.println("\t \t P-> " + produto.getSiglaProduto() + "-" + produto.getNome());
			System.out.println("Mes: " + produto.periodoAcompanhamento.get(0).getMes());
			System.out.println("ANO: " + produto.periodoAcompanhamento.get(0).getAno());

			for (Acao acao : produto.getAcoes()) {
				System.out.println("\t \t \t A-> " + acao.getSiglaAcao() + "-" + acao.getNome());
			}
		}
		for (Indicador indicador : resultado.getIndicador()) {
			System.out.println("\t  I-> " + indicador.getNome() + "-" + indicador.getMetodoCalculo());
		}

		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("Demorou " + (diff / 1000) + " segundos");
	}

	// @Test
	public void testListarProduto() {

		Long init = System.currentTimeMillis();
		ItemEstruturaDao dao = new ItemEstruturaDao(null);

		List<Long> gruposUsuarios = new ArrayList<Long>();
		// gruposUsuarios.add(175L);
		// gruposUsuarios.add(125L);
		gruposUsuarios.add(134L);
		gruposUsuarios.add(19L);

		// Long codUsuario = 18L;
		// Long codUsuario = 64L;
		Long codUsuario = 1L;

		// TESTE - dao.listarProduto
		Produto produto = dao.listarProduto2(3595/* 6325 */, codUsuario, gruposUsuarios, "", null, null);
		// System.out.println(lista.getCodigo() + " - " + lista.getNome());
		System.out.println("\t -> " + produto.getSiglaProduto() + "-" + produto.getNome());
		String nome = "";
		String co = "";
		if (produto.responsavel != null) {
			nome = produto.responsavel.getNome();
		}
		for (Responsavel responsavel : produto.getCoResponsavel()) {
			co = responsavel.getNome() + ", ";
		}
		System.out.println("\t  -> Resp: - " + nome);
		System.out.println("\t  -> CO Resp: - " + co);
		for (Acao acao : produto.getAcoes()) {
			System.out.println("\t \t A-> " + acao.getSiglaAcao() + "-" + acao.getNome());
			for (Atividade atividade : acao.getAtividades()) {
				System.out.println("\t \t \t At-> " + atividade.getSiglaAtividade() + "-" + atividade.getNome());
			}
		}

		// Set gruposUsuarios = new HashSet();
		// gruposUsuarios.add(0);
		// UsuarioUsu usuario = new UsuarioUsu();
		// usuario.setCodUsu(7L);
		// try {
		// dao.getItensByEstrutura(gruposUsuarios, usuario, 1L);
		// } catch (ECARException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("Demorou " + (diff / 1000) + " segundos");
	}

	private List<String> fonetizarEtiqueta(List<String> etiqueta) {
		List<String> etiquetaF = new ArrayList<String>();

		for (String et : etiqueta) {
			etiquetaF.add(EtiquetaUtils.fonetizar(et));
		}
		return etiquetaF;
	}

	// @Test
	public void testListarObjetivoEstrategicoFiltroComIndicador() {

		Long init = System.currentTimeMillis();
		ItemEstruturaDao dao = new ItemEstruturaDao(null);

		// TESTE - dao.listarObjetivoEstrategicoFiltro
		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();
		codigoObjetivoEstrategico.add(7);
		// codigoObjetivoEstrategico.add(10);
		// codigoObjetivoEstrategico.add(11);
		// codigoObjetivoEstrategico.add(12);
		// codigoObjetivoEstrategico.add(14);
		// codigoObjetivoEstrategico.add(15);
		// codigoObjetivoEstrategico.add(16);
		// codigoObjetivoEstrategico.add(17);
		// codigoObjetivoEstrategico.add(18);
		// codigoObjetivoEstrategico.add(19);
		// codigoObjetivoEstrategico.add(20);
		// codigoObjetivoEstrategico.add(21);
		// codigoObjetivoEstrategico.add(23);
		// codigoObjetivoEstrategico.add(692);
		// codigoObjetivoEstrategico.add(1823);
		// codigoObjetivoEstrategico.add(2005);
		List<String> etiqueta = new ArrayList<String>();
		// List<String> etiquetaPrioritaria = new ArrayList<String>();

		// etiqueta.add("CASA");
		etiqueta.add("TESTE_17092012");
		// etiquetaPrioritaria.add("REDE CEGONHA");

		List<String> etiquetaF = fonetizarEtiqueta(etiqueta);
		// List<String> prioritariaF = fonetizarEtiqueta(etiquetaPrioritaria);
		//		
		// etiquetaF.addAll(prioritariaF);
		//		
		List<Integer> resultadoStatus = new ArrayList<Integer>();
		// resultadoStatus.add(1);
		// resultadoStatus.add(0);

		List<Long> gruposUsuarios = new ArrayList<Long>();
		gruposUsuarios.add(134L);
		gruposUsuarios.add(19L);

		List<ObjetivoEstrategico> lista = new ArrayList<ObjetivoEstrategico>();
		try {
			lista = dao.listarObjetivoEstrategicoFiltroComIndicador(codigoObjetivoEstrategico, etiquetaF, null, resultadoStatus, 1L, gruposUsuarios, false, false, false, null, true, null, null);
		} catch (ECARException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ObjetivoEstrategico objetivoEstrategico : lista) {
			System.out.println("OE -> " + objetivoEstrategico.getSiglaIett() + " - " + objetivoEstrategico.getNome());
			for (Estrategia estrategia : objetivoEstrategico.getEstrategias()) {
				System.out.println("\t E ->" + estrategia.getSiglaEstrategia() + "-" + estrategia.getNome());
				for (Resultado resultado : estrategia.getResultados()) {
					System.out.println("\t \t R -> " + resultado.getSiglaResultado() + "-" + resultado.getNome() + " P: " + resultado.getPrioritario() + " A: " + resultado.isAtencao());
					System.out.println(resultado.getMesAnoCicloAtual());
					for (Indicador indicador : resultado.getIndicador()) {
						System.out.println("\t \t I-> " + indicador.getNome() + "-" + indicador.getMetodoCalculo());
					}

					if (resultado.periodoAcompanhamento != null) {
						System.out.println("COR: " + resultado.periodoAcompanhamento.get(0).getNomeCor());
					} else {
						System.out.println("COR: NAO MONITORADO ");
					}

					String nome = "";
					String co = "";
					if (resultado.responsavel != null) {
						nome = resultado.responsavel.getNome();
					}
					for (Responsavel responsavel : resultado.getCoResponsavel()) {
						co = responsavel.getNome() + ", ";
					}
					System.out.println("\t \t -> Resp: - " + nome);
					System.out.println("\t \t -> CO Resp: - " + co);
					for (Produto produto : resultado.getProdutos()) {
						System.out.println("\t \t \t P-> " + produto.getSiglaProduto() + "-" + produto.getNome());
						if (produto.periodoAcompanhamento != null) {
							System.out.println("COR: " + produto.periodoAcompanhamento.get(0).getNomeCor());
						} else {
							System.out.println("COR: NAO MONITORADO ");
						}

						if (produto.responsavel != null) {
							nome = produto.responsavel.getNome();
						}
						for (Responsavel responsavel : produto.getCoResponsavel()) {
							co = responsavel.getNome() + ", ";
						}
						System.out.println("\t \t -> Resp: - " + nome);
						System.out.println("\t \t -> CO Resp: - " + co);
					}
				}
			}
		}

		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("Demorou " + (diff / 1000) + " segundos");
	}

	// @Test
	public void testListarIdsSecretarias() {
		OrgaoDao dao = new OrgaoDao(null);
		List<Long> ids = dao.listarIdsSecretarias("SAS");
		for (Long id : ids) {
			System.out.println(id);
		}
	}

	// @Test
	public void testRecuperaPeriodosAcompanhamento() {
		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		List<PeriodoAcompanhamento> periodos = dao.recuperaPeriodosAcompanhamento(4969L, null, null);
		for (PeriodoAcompanhamento periodoAcompanhamento : periodos) {
			System.out.println(periodoAcompanhamento.getMes());
			System.out.println(periodoAcompanhamento.getCiclo());
			System.out.println(periodoAcompanhamento.getParecer());
		}
	}

	// @Test
	public void testListarDescendentes() {
		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		List<ItemEstruturaIett> ld = dao.listarDescendentes(4969L);

	}

	// @Test
	public void testFormatoData() {
		String dataFinal = "11/07/2012";
		Calendar dt = Data.getCalendar(Data.parseDate(dataFinal));
		dt.add(Calendar.HOUR_OF_DAY, 23);
		dt.add(Calendar.MINUTE, 59);
		dt.add(Calendar.SECOND, 59);
		System.out.println(dt.getTime());
	}

	// @Test
	public void testListarSecretarias() {
		RelatorioAcompanhamentoService relatorioAcompanhamentoService = new RelatorioAcompanhamentoService();
		List<SecretariaDTO> l = relatorioAcompanhamentoService.listarSecretarias();
		for (SecretariaDTO SecretariaEstatisticaDTO : l) {
			System.out.println(SecretariaEstatisticaDTO.getSiglaSecretaria());
		}
	}

	// @Test
	public void testBuscaResponsavel() {
		UsuarioDao dao = new UsuarioDao();
		List<Responsavel> l = dao.buscaResponsavel("rafael");
		for (Responsavel r : l) {
			System.out.println(r.getNome());
		}
	}

	// @Test
	public void testListaCometarios() {
		ItemEstruturaAcaoDao dao = new ItemEstruturaAcaoDao(null);
		List<Comentario> l = dao.loadComentarioWS(3584L, null, null);
		for (Comentario c : l) {
			System.out.println(c.getTexto());
		}
	}

	// @Test
	public void testLegendasGraficoRelatorioGerencial() {
		AcompReferenciaItemDao dao = new AcompReferenciaItemDao(null);
		List<Long> idsResultados = new ArrayList<Long>();
		idsResultados.add(2017L);
		idsResultados.add(2135L);
		idsResultados.add(2214L);
		idsResultados.add(2237L);
		idsResultados.add(2261L);
		idsResultados.add(2279L);
		idsResultados.add(2361L);
		idsResultados.add(2370L);
		idsResultados.add(2374L);
		idsResultados.add(6640L);
		idsResultados.add(6642L);
		idsResultados.add(6644L);
		idsResultados.add(6648L);
		idsResultados.add(6654L);
		idsResultados.add(6675L);
		idsResultados.add(6679L);
		idsResultados.add(6690L);
		idsResultados.add(6702L);
		idsResultados.add(6709L);

		List l = dao.obterLegendasGraficoRelatorioGerencial(6, idsResultados, false, null, null);

		for (int i = 0; i < l.size(); i++) {
			Object[] resultados = (Object[]) l.get(i);
			System.out.println(resultados[0] + " " + resultados[2]);

		}
	}

	// @Test
	public void testMontaParecer() {
		List<AcompReferenciaAref> listaAref = null;
		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		String teste = dao.montaParecer("ddddd", 8973L, null);

		System.out.println(teste);

	}

	// @Test
	public void testConsultaIett() {

		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();

		codigoObjetivoEstrategico.add(16);

		List<String> etiqueta = new ArrayList<String>();

		List<String> etiquetaF = fonetizarEtiqueta(etiqueta);

		List<Integer> resultadoStatus = new ArrayList<Integer>();

		List<Long> gruposUsuarios = new ArrayList<Long>();

		gruposUsuarios.add(134L);
		gruposUsuarios.add(19L);

		Long codUsuario = 1L;

		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		try {
			dao.listarResultado2(3584, codUsuario, gruposUsuarios, "", null, null);
		} catch (ECARException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println(teste);

	}

	// @Test
	public void testAtualizaEtiquetasRecursivo() {

		// EtiquetaDao dao = new EtiquetaDao();
		// dao.atualizaEtiquetaRecursiva(null,null);

	}

	// @Test
	public void testCadastraAcaoClienteSimpr() {

		Long init = System.currentTimeMillis();

		// Configure the SSLContext with a TrustManager
		SSLContext ctx;
		try {
			ctx = SSLContext.getInstance("TLS");
			ctx.init(new KeyManager[0], new TrustManager[] { new DefaultTrustManager() }, new SecureRandom());
			SSLContext.setDefault(ctx);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.getProperties().setProperty("http.proxySet", "true");
		System.getProperties().setProperty("http.proxyHost", "proxy");

		PlanoAcaoServicePortProxy planoAcaoPorta = new PlanoAcaoServicePortProxy();
		WsUsuarioAutenticacao wsUsuarioAutenticacao = new WsUsuarioAutenticacao();

		wsUsuarioAutenticacao.setUsuario("75687909249");
		wsUsuarioAutenticacao.setSenha("99SVhGXh");

		EstruturaEtt resul = null;
		EstruturaEtt prod = null;

		try {
			resul = (EstruturaEtt) new EstruturaDao(null).buscar(EstruturaEtt.class, 4L);
			prod = (EstruturaEtt) new EstruturaDao(null).buscar(EstruturaEtt.class, 5L);
		} catch (ECARException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cnpj", "00394544012787");
		params.put("nomeEmpresa", "Ministerio da Saude");
		// params.put("tpfa", (TipoFuncAcompTpfa) new
		// TipoFuncAcompDao().buscar(TipoFuncAcompTpfa.class, 3L));

		ItemEstruturaDao dao = new ItemEstruturaDao(null);

		List<WsAcaoCadastro> listaAcao = dao.obtemListaAcao(params, resul, prod);

		StringBuffer csv = new StringBuffer();

		for (WsAcaoCadastro acao : listaAcao) {

			csv.append(acao.toStringCSV() + "\n\r");

			try {

				br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsRetorno wsRetorno = planoAcaoPorta.cadastraAcao(wsUsuarioAutenticacao, acao);
				if (!wsRetorno.isSucesso()) {

					System.out.println("Cadastro Ação:" + wsRetorno.getMensagensErro());

				} else
					System.out.println("Ação código: " + acao.getChvAcaoExterna() + " enviada com sucesso");

			} catch (RemoteException e) {
				System.out.println("Cadastro Ação:" + "Erro Conexão");
				e.printStackTrace();
			}

			ItemEstruturaIett iett = dao.getItemEtruturaById(Long.parseLong(String.valueOf(acao.getChvAcaoExterna())));

			boolean isIntermediario = false;

			isIntermediario = (iett.getEstruturaEtt().getCodEtt() == 5 ? false : true);

			// descricaoR5 est� sendo usada para exce��o do SOS Emergencias.
			// Plano de Acao dos Hospitais
			if (iett.getDescricaoR5() == "EXC") {
				isIntermediario = false;
			}

			List<WsAtividadeCadastro> listaAtividade = dao.obtemListaAcaoTarefa(acao.getChvAcaoExterna(), true, isIntermediario);
			// if(listaAtividade.size()>0){
			// System.out.println(listaAtividade.size());
			// }

			for (WsAtividadeCadastro atividade : listaAtividade) {
				csv.append(atividade.toStringCSV() + "\n\r");
				try {

					br.gov.presidencia.ondaswsdev.ws.server.PlanoAcaoService_php.WsRetorno wsRetorno = planoAcaoPorta.cadastraAtividade(wsUsuarioAutenticacao, atividade);
					if (!wsRetorno.isSucesso()) {

						System.out.println("Cadastro Atividade:" + wsRetorno.getMensagensErro());

					} else
						System.out.println("Atividade código: " + atividade.getChvAtividadeExterna() + " enviada com sucesso");

				} catch (RemoteException e) {
					System.out.println("Cadastro Atividade:" + "Erro Conexão");
					e.printStackTrace();
				}

			}

		}

		// Arquivo excell
		File csvFile = new File("C://extratoSimPR.csv");

		try {
			csvFile.createNewFile();
			OutputStream os = new FileOutputStream(csvFile);
			os.write(csv.toString().getBytes());
			os.flush();
			os.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// Fim Arquivo excell
		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("Demorou " + (diff / 1000) + " segundos");

	}

	private static class DefaultTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	}

	// @Test
	public void testExtratoOficinaMonitoramento() {

		Long init = System.currentTimeMillis();
		ItemEstruturaDao dao = new ItemEstruturaDao(null);

		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();

		List<String> etiqueta = new ArrayList<String>();

		List<String> etiquetaF = fonetizarEtiqueta(etiqueta);
		List<Integer> resultadoStatus = new ArrayList<Integer>();

		List<Long> gruposUsuarios = new ArrayList<Long>();
		gruposUsuarios.add(134L);
		gruposUsuarios.add(19L);

		Long codUsuario = 1L;

		List<ObjetivoEstrategico> lista = dao.listarObjetivoEstrategicoFiltroMaisRapidoEuEspero(codigoObjetivoEstrategico, etiquetaF, null, resultadoStatus, codUsuario, gruposUsuarios, false, null, null, 0, null);

		StringBuffer csv = new StringBuffer();
		csv.append("OBJETIVO ESTRATÉGICO;ESTRATÉGIA;SECRETARIA;SIGLA;RESULTADO;SITUAÇÃO;CICLO" + "\n\r");

		for (ObjetivoEstrategico objetivoEstrategico : lista) {
			// System.out.println("OE -> " +
			// objetivoEstrategico.getSiglaIett() + " - " +
			// objetivoEstrategico.getNome());
			for (Estrategia estrategia : objetivoEstrategico.getEstrategias()) {
				// System.out.println("\t E ->"+ estrategia.getSiglaEstrategia()
				// + "-"+ estrategia.getNome());
				for (Resultado resultado : estrategia.getResultados()) {
					String nome = "";
					String orgao = "";
					String cor = "";
					String mes = "";
					int ano = 0;
					if (resultado.responsavel != null) {
						nome = resultado.responsavel.toString();
						orgao = resultado.getResponsavel().orgao.getNome();
					}

					if (resultado.periodoAcompanhamento.size() > 0) {
						// System.out.println("COR: " +
						// resultado.periodoAcompanhamento.get(0).getNomeCor());
						for (PeriodoAcompanhamento periodoAcompanhamento : resultado.periodoAcompanhamento) {
							cor = periodoAcompanhamento.getNomeCor();
							mes = periodoAcompanhamento.getMes();
							ano = periodoAcompanhamento.getAno();
						}
					}

					// System.out.println(objetivoEstrategico.getSiglaIett()+
					// "-"+ objetivoEstrategico.getNome()+ ";" +
					// orgao +";"+ resultado.getSiglaResultado()+";"+
					// resultado.getNome() + ";"+cor+"-"+mes+"/"+ano);

					csv.append(objetivoEstrategico.getSiglaIett() + ";" + estrategia.getSiglaEstrategia() + ";" + orgao + ";" + resultado.getSiglaResultado() + ";\"" + resultado.getNome() + "\";" + cor.replace("N&atilde;o", "Não") + ";" + mes + "/" + ano + "\n\r");

				}
			}
			csv.append("\n\r");
		}

		// Arquivo excell
		File csvFile = new File("/home/rafael/extratoOficinaMonitoramento.csv");

		try {
			csvFile.createNewFile();
			OutputStream os = new FileOutputStream(csvFile);
			os.write(csv.toString().getBytes());
			os.flush();
			os.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("Demorou " + (diff / 1000) + " segundos");
	}

	// @Test
	public void testListaExercicioExe() {
		ExercicioDao dao = new ExercicioDao(null);

		List<ExercicioExe> exercicios = new ArrayList<ExercicioExe>();

		exercicios = dao.listarExercios();

		for (ExercicioExe exercicio : exercicios) {
			System.out.println(exercicio.getDescricaoExe());
		}

	}

	// @Test
	public void testListaEstatisticas() {
		EstatisticaDao dao = new EstatisticaDao(null);

		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();
		Long exercicio = 11L;

		List<Object[]> lista = new ArrayList<Object[]>();
		lista = dao.listaDadosEstatisticos(codigoObjetivoEstrategico, exercicio);

		List<SecretariaEstatisticaDTO> secretariaObjetivoEstrategico = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaEstrategia = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaIndicadores = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaREM = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaResultado = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaProduto = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaAcao = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaAtividade = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaVerde = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaAmarelo = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaVermelho = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaAzul = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaCinza = new ArrayList<SecretariaEstatisticaDTO>();
		List<SecretariaEstatisticaDTO> secretariaBranco = new ArrayList<SecretariaEstatisticaDTO>();

		for (Object[] obj : lista) {

			if (Long.parseLong(obj[2].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[2].toString()));
				secretariaObjetivoEstrategico.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

			if (Long.parseLong(obj[3].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[3].toString()));
				secretariaEstrategia.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

			if (Long.parseLong(obj[4].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[4].toString()));
				secretariaResultado.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

			if (Long.parseLong(obj[5].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[5].toString()));
				secretariaProduto.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

			if (Long.parseLong(obj[6].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[6].toString()));
				secretariaAcao.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

			if (Long.parseLong(obj[7].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[7].toString()));
				secretariaAtividade.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

			if (Long.parseLong(obj[8].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[8].toString()));
				secretariaREM.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

			if (Long.parseLong(obj[9].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[9].toString()));
				secretariaIndicadores.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

			if (Long.parseLong(obj[10].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[10].toString()));
				secretariaVerde.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

			if (Long.parseLong(obj[11].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[11].toString()));
				secretariaAmarelo.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

			if (Long.parseLong(obj[12].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[12].toString()));
				secretariaVermelho.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

			if (Long.parseLong(obj[13].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[13].toString()));
				secretariaAzul.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

			if (Long.parseLong(obj[14].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[14].toString()));
				secretariaCinza.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

			if (Long.parseLong(obj[15].toString()) != 0L) {
				SecretariaEstatisticaDTO secretariaEstatisticaDTO = new SecretariaEstatisticaDTO(Long.parseLong(obj[0].toString()), obj[1].toString(), Long.parseLong(obj[15].toString()));
				secretariaBranco.add(secretariaEstatisticaDTO);
				secretariaEstatisticaDTO = null;
			}

		}

		EstatisticaDTO estatisticaDTO = new EstatisticaDTO();

		estatisticaDTO.setQuantidadeObjetivoEstregico(secretariaObjetivoEstrategico);
		estatisticaDTO.setQuantidadeEstrategia(secretariaEstrategia);
		estatisticaDTO.setQuantidadeResultado(secretariaResultado);
		estatisticaDTO.setQuantidadeProduto(secretariaProduto);
		estatisticaDTO.setQuantidadeAcao(secretariaAcao);
		estatisticaDTO.setQuantidadeAtividade(secretariaAtividade);
		estatisticaDTO.setQuantidadeREM(secretariaREM);
		estatisticaDTO.setQuantidadeIndicadores(secretariaIndicadores);
		estatisticaDTO.setQuantidadeVerde(secretariaVerde);
		estatisticaDTO.setQuantidadeAmarelo(secretariaAmarelo);
		estatisticaDTO.setQuantidadeVermelho(secretariaVermelho);
		estatisticaDTO.setQuantidadeAzul(secretariaAzul);
		estatisticaDTO.setQuantidadeCinza(secretariaCinza);
		estatisticaDTO.setQuantidadeBranco(secretariaBranco);

		for (SecretariaEstatisticaDTO teste : estatisticaDTO.getQuantidadeVerde()) {
			System.out.println(teste.getQuantidade());
		}

	}

	// @Test
	public void testEstatisticaJSON() {
		EstatisticaDao dao = new EstatisticaDao(null);
		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();
		Long exercicio = 11L;
		System.out.println(dao.estatisticaJSON(codigoObjetivoEstrategico, exercicio));
	}

	// @Test
	public void testBuscarNomeIettHistorico() {
		String nome = "teste";
		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		nome = dao.buscarNomeIettHistorico(nome, 1826L, 10L);

		System.out.println(nome);
	}

	// @Test
	public void testMascaraNumero() {
		System.out.println(Util.formataNumeroDecimal(10000.98754));
	}

	// @Test
	public void testPainelIndicador() throws ECARException {

		Long init = System.currentTimeMillis();
		ItemEstruturaDao dao = new ItemEstruturaDao(null);

		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();
		// codigoObjetivoEstrategico.add(7);
		// codigoObjetivoEstrategico.add(10);
		// codigoObjetivoEstrategico.add(11);
		// codigoObjetivoEstrategico.add(12);
		// codigoObjetivoEstrategico.add(14);
		// codigoObjetivoEstrategico.add(15);
		// codigoObjetivoEstrategico.add(16);
		// codigoObjetivoEstrategico.add(17);
		// codigoObjetivoEstrategico.add(18);
		// codigoObjetivoEstrategico.add(19);
		// codigoObjetivoEstrategico.add(20);
		// codigoObjetivoEstrategico.add(21);
		// codigoObjetivoEstrategico.add(23);
		// codigoObjetivoEstrategico.add(692);
		// codigoObjetivoEstrategico.add(1823);
		// codigoObjetivoEstrategico.add(2005);
		List<String> etiqueta = new ArrayList<String>();
		// List<String> etiquetaPrioritaria = new ArrayList<String>();

		// etiqueta.add("CASA");
		// etiqueta.add("TESTE_17092012");
		// etiquetaPrioritaria.add("REDE CEGONHA");

		List<String> etiquetaF = fonetizarEtiqueta(etiqueta);
		// List<String> prioritariaF = fonetizarEtiqueta(etiquetaPrioritaria);
		//		
		// etiquetaF.addAll(prioritariaF);
		//		
		List<Integer> resultadoStatus = new ArrayList<Integer>();
		// resultadoStatus.add(1);
		// resultadoStatus.add(0);

		List<Long> gruposUsuarios = new ArrayList<Long>();
		gruposUsuarios.add(134L);
		gruposUsuarios.add(19L);

		List<ObjetivoEstrategico> lista = new ArrayList<ObjetivoEstrategico>();

		// lista =
		// dao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(codigoObjetivoEstrategico,etiquetaF,null,
		// resultadoStatus, 1L, gruposUsuarios, false, false, false, null, true,
		// null, null, 0L, false);
		lista = dao.listarObjetivoEstrategicoFiltroMaisRapidoEuEspero(codigoObjetivoEstrategico, etiquetaF, null, resultadoStatus, 1L, gruposUsuarios, false, null, null, 1, null);

		List<Resultado> retorno = new ArrayList<Resultado>();
		for (ObjetivoEstrategico oe : lista) {
			for (Estrategia estrategia : oe.estrategias) {
				retorno.addAll(estrategia.resultados);
			}
		}
		List<Resultado> listaExclusao = new ArrayList<Resultado>();
		for (Resultado resultado : retorno) {
			if (resultado.periodoAcompanhamento != null) {
				for (PeriodoAcompanhamento periodoAcompanhamento : resultado.periodoAcompanhamento) {
					if (periodoAcompanhamento.indMonitoramento.isEmpty()) {
						listaExclusao.add(resultado);
					}
				}
			}
		}
		System.out.println("Tam antes exclusão: " + retorno.size());
		for (Resultado exclui : listaExclusao) {
			retorno.remove(exclui);
		}
		System.out.println("Tam depois exclusão: " + retorno.size());

		for (Resultado resultado : retorno) {
			// Resultado res = dao.listarResultado2(new
			// Long(resultado.getCodigo()).intValue(), 1L, gruposUsuarios, "",
			// 0L);

			if (resultado.periodoAcompanhamento != null) {
				// System.out.println("Mes: " +
				// resultado.periodoAcompanhamento.get(0).getMes());
				// System.out.println("ANO: " +
				// resultado.periodoAcompanhamento.get(0).getAno());
				for (PeriodoAcompanhamento periodoAcompanhamento : resultado.periodoAcompanhamento) {

					// AcompReferenciaItemAri ari = (AcompReferenciaItemAri)
					// dao.buscar(AcompReferenciaItemAri.class,
					// periodoAcompanhamento.getCodigo());
					// IndicadorMonitoramentoBean indMon = new
					// IndicadorMonitoramentoBean();
					// periodoAcompanhamento.indMonitoramento =
					// indMon.ListaIndicadores(ari,dao.getIndicadores(resultado.getCodigo()));

					for (IndicadorMonitoramentoDto ind : periodoAcompanhamento.indMonitoramento) {
						// System.out.println("Valores-> " +
						// ind.getValores().size());
						System.out.println("Mes: " + periodoAcompanhamento.getMes());
						System.out.println("ANO: " + periodoAcompanhamento.getAno());
						System.out.println("CICLO: " + periodoAcompanhamento.getCiclo());
						for (IndicadorValorDto indi : ind.getValores()) {
							System.out.println("M -> " + indi.getPrevisto());
							System.out.println("R -> " + indi.getRealizado());
						}
					}
				}
			}
		}

		/*
		 * for (ObjetivoEstrategico objetivoEstrategico : lista) { //
		 * System.out.println("OE -> " + // objetivoEstrategico.getSiglaIett() +
		 * " - " + objetivoEstrategico.getNome()); for (Estrategia estrategia :
		 * objetivoEstrategico.getEstrategias()) { //
		 * System.out.println("\t E ->"+ estrategia.getSiglaEstrategia() + "-"+
		 * estrategia.getNome()); for (Resultado resultado :
		 * estrategia.getResultados()) { //Resultado res =
		 * dao.listarResultado2(new Long(resultado.getCodigo()).intValue(), 1L,
		 * gruposUsuarios, "", 0L);
		 * 
		 * if(resultado.periodoAcompanhamento != null){ //
		 * System.out.println("Mes: " +
		 * resultado.periodoAcompanhamento.get(0).getMes()); //
		 * System.out.println("ANO: " +
		 * resultado.periodoAcompanhamento.get(0).getAno()); for
		 * (PeriodoAcompanhamento periodoAcompanhamento :
		 * resultado.periodoAcompanhamento) {
		 * 
		 * // AcompReferenciaItemAri ari = (AcompReferenciaItemAri)
		 * dao.buscar(AcompReferenciaItemAri.class,
		 * periodoAcompanhamento.getCodigo()); // IndicadorMonitoramentoBean
		 * indMon = new IndicadorMonitoramentoBean(); //
		 * periodoAcompanhamento.indMonitoramento =
		 * indMon.ListaIndicadores(ari,dao
		 * .getIndicadores(resultado.getCodigo()));
		 * 
		 * for (IndicadorMonitoramentoDto ind :
		 * periodoAcompanhamento.indMonitoramento) { //
		 * System.out.println("Valores-> " + ind.getValores().size());
		 * System.out.println("Mes: " + periodoAcompanhamento.getMes());
		 * System.out.println("ANO: " + periodoAcompanhamento.getAno());
		 * System.out.println("CICLO: " + periodoAcompanhamento.getCiclo()); for
		 * (IndicadorValorDto indi : ind.getValores()) {
		 * System.out.println("M -> " + indi.getPrevisto());
		 * System.out.println("R -> " + indi.getRealizado()); } } } } } } }
		 */
		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("Demorou " + (diff / 1000) + " segundos");
	}

	@Test
	public void testListarObjetivoEstrategicoFiltroExcell() {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet firstSheet = workbook.createSheet("Aba1");

		HSSFCellStyle estilo = workbook.createCellStyle();
		estilo.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		estilo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// Long init = System.currentTimeMillis();
		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		SiteDao siteDao = new SiteDao();

		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();

		List<String> etiqueta = new ArrayList<String>();

		List<String> etiquetaF = fonetizarEtiqueta(etiqueta);

		List<Integer> resultadoStatus = new ArrayList<Integer>();

		List<Long> gruposUsuarios = new ArrayList<Long>();
		gruposUsuarios.add(134L);
		gruposUsuarios.add(19L);
		List<Long> secretarias = new ArrayList<Long>();

		Long codUsuario = 1L;

		// StringBuilder csv = new StringBuilder();

		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(new File("C:\\publicacao.xls"));

			//List<ObjetivoEstrategico> lista = dao.listarObjetivoEstrategicoFiltroMaisRapidoEuEspero(codigoObjetivoEstrategico, 
			//		etiquetaF, null, resultadoStatus, codUsuario, gruposUsuarios, false, null, 12L, 0, null);
			List<ObjetivoEstrategico> lista = siteDao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(
					codigoObjetivoEstrategico, etiqueta, null, resultadoStatus, 
					codUsuario, gruposUsuarios, false, false, false, secretarias, false, null, null, 12L, false, 0L, false, 0);

			populaPlanilha(firstSheet, dao, lista);
			workbook.write(fos);
			// System.out.println("Total Resultados -> "+totalResultado);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro ao exportar arquivo");
		} finally {
			try {
				fos.flush();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// Long end = System.currentTimeMillis();
		// Long diff = end - init;
		// System.out.println("Demorou " + (diff / 1000) + " segundos");

	}

	private void populaPlanilha(HSSFSheet firstSheet, ItemEstruturaDao dao, List<ObjetivoEstrategico> lista) throws ECARException {
		int i = 0;
		int totalResultado = 0;
		for (ObjetivoEstrategico objetivoEstrategico : lista) {

			HSSFRow row = firstSheet.createRow(i);

			row.createCell(0).setCellValue("OE -> " + objetivoEstrategico.getSiglaIett());
			row.createCell(1).setCellValue(objetivoEstrategico.getNome());

			@SuppressWarnings("deprecation")
			CellRangeAddress mesclaCelulas = new CellRangeAddress(i, i, 1, 4);

			firstSheet.addMergedRegion(mesclaCelulas);

			i++;
			for (Estrategia estrategia : objetivoEstrategico.getEstrategias()) {

				row = firstSheet.createRow(i);

				row.createCell(0).setCellValue(" ");
				row.createCell(1).setCellValue("Estratégia -> " + estrategia.getSiglaEstrategia());
				row.createCell(2).setCellValue(estrategia.getNome());

				@SuppressWarnings("deprecation")
				CellRangeAddress mesclaCelulasEstrategia = new CellRangeAddress(i, i, 2, 4);

				firstSheet.addMergedRegion(mesclaCelulasEstrategia);

				i++;
				for (Resultado resultado : estrategia.getResultados()) {

					// ItemEstruturaDao itemEstruturaDao = new
					// ItemEstruturaDao(
					// null);
					//
					// ItemEstruturaIett iett = (ItemEstruturaIett)
					// itemEstruturaDao
					// .buscar(ItemEstruturaIett.class,
					// resultado.getCodigo());
					// System.out.println(iett.getIndAtivoIett());
					// if (iett.getIndAtivoIett() != "N") {

					String secretariaResponsavel = "";

					ItemEstruturaIett itemEstrutura = null;

					if (resultado.responsavel != null) {
						itemEstrutura = (ItemEstruturaIett) dao.buscar(ItemEstruturaIett.class, resultado.getCodigo());
						if (itemEstrutura.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null) {
							secretariaResponsavel = itemEstrutura.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg();
						}
					}

					row = firstSheet.createRow(i);

					// if(resultado.getPrioritario()=="S"){
					// row.setRowStyle(estilo);
					// }

					row.createCell(0).setCellValue(" ");
					row.createCell(1).setCellValue(" ");
					row.createCell(2).setCellValue("Resultado -> " + resultado.getSiglaResultado());
					row.createCell(3).setCellValue(resultado.getNome());
					row.createCell(4).setCellValue((resultado.responsavel != null ? " Responsável: " + secretariaResponsavel : ""));
					if (resultado.getPrioritario() == "S") {
						// row.getCell(0).setCellStyle(estilo);
						// row.getCell(1).setCellStyle(estilo);
						// row.getCell(2).setCellStyle(estilo);
						// row.getCell(3).setCellStyle(estilo);
						// row.getCell(4).setCellStyle(estilo);
						row.getCell(1).setCellValue("X");
					}
					i++;
					totalResultado++;
				}
			}
		}
		// }
		HSSFRow row = firstSheet.createRow(i++);
		row.createCell(0).setCellValue("Total Resultados -> " + totalResultado++);
	}

	// @Test
	public void testRelatorioIndicadorDetalheExercicio() {

		Long init = System.currentTimeMillis();
		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		AcompRealFisicoDao daoRealFisico = new AcompRealFisicoDao(null);
		ExercicioDao exercicioDao = new ExercicioDao(null);

		// TESTE - dao.listarObjetivoEstrategicoFiltro
		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();
		// codigoObjetivoEstrategico.add(7);
		// codigoObjetivoEstrategico.add(10);
		// codigoObjetivoEstrategico.add(11);
		// codigoObjetivoEstrategico.add(12);
		// codigoObjetivoEstrategico.add(14);
		// codigoObjetivoEstrategico.add(15);
		// codigoObjetivoEstrategico.add(16);
		// codigoObjetivoEstrategico.add(17);
		// codigoObjetivoEstrategico.add(18);
		// codigoObjetivoEstrategico.add(19);
		// codigoObjetivoEstrategico.add(20);
		// codigoObjetivoEstrategico.add(21);
		codigoObjetivoEstrategico.add(23);
		// codigoObjetivoEstrategico.add(692);
		// codigoObjetivoEstrategico.add(1823);
		// codigoObjetivoEstrategico.add(2005);
		List<String> etiqueta = new ArrayList<String>();
		// List<String> etiquetaPrioritaria = new ArrayList<String>();

		// etiqueta.add("CASA");
		// etiqueta.add("TESTE_17092012");
		// etiquetaPrioritaria.add("REDE CEGONHA");

		List<String> etiquetaF = fonetizarEtiqueta(etiqueta);
		// List<String> prioritariaF = fonetizarEtiqueta(etiquetaPrioritaria);
		//		
		// etiquetaF.addAll(prioritariaF);
		//		
		List<Integer> resultadoStatus = new ArrayList<Integer>();
		// resultadoStatus.add(1);
		// resultadoStatus.add(0);

		List<Long> gruposUsuarios = new ArrayList<Long>();
		gruposUsuarios.add(134L);
		gruposUsuarios.add(19L);

		List<ObjetivoEstrategico> lista = new ArrayList<ObjetivoEstrategico>();
		try {
			lista = dao.listarObjetivoEstrategicoFiltroComIndicadorMaisRapidoEuEspero(codigoObjetivoEstrategico, etiquetaF, null, resultadoStatus, 1L, gruposUsuarios, false, false, false, null, false, null, null, 0L, false, 0L, true);
		} catch (ECARException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (ObjetivoEstrategico objetivoEstrategico : lista) {
			System.out.println("OE -> " + objetivoEstrategico.getSiglaIett() + " - " + objetivoEstrategico.getNome());
			for (Estrategia estrategia : objetivoEstrategico.getEstrategias()) {
				System.out.println("\t E ->" + estrategia.getSiglaEstrategia() + "-" + estrategia.getNome());
				for (Resultado resultado : estrategia.getResultados()) {
					System.out.println("\t \t R -> " + resultado.getSiglaResultado() + "-" + resultado.getNome() + " P: " + resultado.getPrioritario() + " A: " + resultado.isAtencao());
					System.out.println(resultado.getMesAnoCicloAtual());// if(1==1){break;}
					for (Indicador indicador : resultado.getIndicador()) {
						System.out.println("\t \t I-> " + indicador.getNome() + "-" + indicador.getMetodoCalculo());
						Double totalP = 0.0;
						Double totalR = 0.0;
						for (IndicadorDetalhamento detalhamento : indicador.detalhe) {
							System.out.println(detalhamento.getMes() + "/" + detalhamento.getAno());
							if (detalhamento.getTipo().equals("P")) {
								System.out.println(detalhamento.getTipo() + "->" + detalhamento.getRealizadoNoMes());
								totalP += detalhamento.getRealizadoNoMes();
							} else {
								System.out.println(detalhamento.getTipo() + "->" + detalhamento.getRealizadoNoMes());
								totalR += detalhamento.getRealizadoNoMes();
							}
						}
						System.out.println(totalP);
						System.out.println(totalR);

						// ExercicioExe exeAref =
						// ari.getAcompReferenciaAref().getExercicioExe();
						// q.setParameter("exercicio",
						// exercicioDao.buscaCodigoExercicioCorrente());
						// AcompReferenciaItemAri ari;
						// List<String> meses = new ArrayList<String>();
						// List valores = new ArrayList();
						//						
						// try {
						// ExercicioExe exercicio =
						// exercicioDao.getExercicio("05", "2013");
						// meses =
						// exercicioDao.getMesesDentroDoExercicio(exercicio);
						//							
						// //List<PeriodoAcompanhamento> periodos =
						// dao.recuperaPeriodosAcompanhamento(resultado.getCodigo(),
						// null, null);
						//							
						// //if(periodos.size() > 0){
						// //ari = (AcompReferenciaItemAri)
						// dao.buscar(AcompReferenciaItemAri.class,
						// periodos.get(0).getCodigo());
						// //List indResultados =
						// daoRealFisico.getIndResulByAcompRefItemBySituacao(ari,
						// Dominios.TODOS, false);
						//								
						// //Iterator itIndicadores = indResultados.iterator();
						// String grupoIndicador = "";
						// //while(itIndicadores.hasNext()){
						// //AcompRealFisicoArf indi = (AcompRealFisicoArf)
						// itIndicadores.next();
						// ItemEstrtIndResulIettr indResul = new
						// ItemEstrtIndResulIettr();
						// indResul.setCodIettir(new
						// Long(indicador.getCodigo()));
						// Map mapMeses =
						// daoRealFisico.getQtdRealizadaExercicioPorMes(exercicio,
						// indResul);
						// Iterator itMeses = meses.iterator();
						// double total = 0;
						// IndicadorResultado wrapperIndicador = new
						// IndicadorResultado(indResul.getCodIettir());
						//									
						// double totalPrevisto = 0.0;
						// for(String datas: meses){
						// String mes = datas.split("-")[0];
						// String ano = datas.split("-")[1];
						// EcarData ecarData = new EcarData(mes, ano);
						//																				
						// Previsto prev =
						// wrapperIndicador.getPrevistoMensal(ecarData);
						//										
						// String valorPrevisto = "-";
						//										
						// if(prev != null){
						// valorPrevisto = prev.getPrevistoFormatado();
						// totalPrevisto += prev.getValorPrevisto();
						// }
						// System.out.println(mes + "/" + ano);
						// System.out.println("P - " + valorPrevisto);
						// //}
						//									
						//									
						//									
						// //while(itMeses.hasNext()){
						// boolean possuiValor = true;
						// String mesAnoMap = itMeses.next().toString();
						// Object m = mapMeses.get(mesAnoMap);
						// if(m == null) {
						// possuiValor = false;
						// }
						//																			
						// String strValor = "";
						// if(possuiValor){
						// Double valor = new Double(m.toString());
						// valores.add(valor);
						//																						
						// strValor = Util.formataMoeda(valor);
						//										
						// } else {
						// strValor = "-";
						// }
						// //System.out.println(mesAnoMap);
						// System.out.println("R - " + strValor);
						//																				
						// }
						//									
						// String totalPrevistoStr = "";
						// totalPrevistoStr = Util.formataMoeda(totalPrevisto);
						// System.out.println(totalPrevistoStr);
						//									
						// Collections.reverse(valores);
						// total =
						// daoRealFisico.getSomaValoresArfs(wrapperIndicador.getRealObject(),
						// valores).doubleValue();
						// String strTotal = "";
						// strTotal = Util.formataMoeda(Double.valueOf(total));
						// System.out.println(strTotal);
						//															
						//							
						// } catch (ECARException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// } catch (Exception e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
						//						
					}

					if (resultado.periodoAcompanhamento != null) {
						System.out.println("COR: " + resultado.periodoAcompanhamento.get(0).getNomeCor());
					} else {
						System.out.println("COR: NAO MONITORADO ");
					}
					/*
					 * String nome = ""; String co = "";
					 * if(resultado.responsavel!=null) { nome =
					 * resultado.responsavel.getNome(); }
					 * 
					 * for (Responsavel responsavel :
					 * resultado.getCoResponsavel()) { co =
					 * responsavel.getNome() + ", "; }
					 * System.out.println("\t \t -> Resp: - " + nome);
					 * System.out.println("\t \t -> CO Resp: - " + co);
					 * 
					 * for (Produto produto : resultado.getProdutos()) {
					 * System.out.println("\t \t \t P-> " +
					 * produto.getSiglaProduto()+"-"+ produto.getNome() );
					 * if(produto.periodoAcompanhamento != null){
					 * System.out.println("COR: " +
					 * produto.periodoAcompanhamento.get(0).getNomeCor()); }else
					 * { System.out.println("COR: NAO MONITORADO "); }
					 * 
					 * 
					 * if(produto.responsavel!=null) { nome =
					 * produto.responsavel.getNome(); } for (Responsavel
					 * responsavel : produto.getCoResponsavel()) { co =
					 * responsavel.getNome() + ", "; }
					 * System.out.println("\t \t -> Resp: - " + nome);
					 * System.out.println("\t \t -> CO Resp: - " + co); }
					 */
				}
			}
		}

		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("Demorou " + (diff / 1000) + " segundos");
	}

	//@Test
	public void testConsolidadoObjetivoEstrategicoFiltroExcell() {

		// --Filtros

		ItemEstruturaDao dao = new ItemEstruturaDao(null);

		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();

		List<String> etiqueta = new ArrayList<String>();

		List<String> etiquetaF = fonetizarEtiqueta(etiqueta);

		List<Integer> resultadoStatus = new ArrayList<Integer>();

		List<Long> gruposUsuarios = new ArrayList<Long>();
		gruposUsuarios.add(134L);
		gruposUsuarios.add(19L);

		Long codUsuario = 1L;

		// -->

		RelatorioAcompanhamentoService relatorioAcompanhamentoService = new RelatorioAcompanhamentoService();

		List<SecretariaDTO> listaSecretarias = relatorioAcompanhamentoService.listarSecretarias();

		FileOutputStream arquivo = null;

		HSSFWorkbook workbook = new HSSFWorkbook();

		for (SecretariaDTO secretariaDTO : listaSecretarias) {

			HSSFSheet secretariaSheet = workbook.createSheet(secretariaDTO.getSiglaSecretaria());

			List<ObjetivoEstrategico> lista = dao.listarObjetivoEstrategicoFiltroMaisRapidoEuEspero(codigoObjetivoEstrategico, etiquetaF, null, resultadoStatus, codUsuario, gruposUsuarios, false, secretariaDTO.getCodigosSecretarias(), 11L, 0, null);

			try {
				populaPlanilhaPorSecretaria(secretariaSheet, dao, lista);
			} catch (ECARException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		try {
			arquivo = new FileOutputStream(new File("C:\\publicacao.xls"));
			workbook.write(arquivo);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void preencheCabecalho(HSSFSheet firstSheet) {
		HSSFRow row = firstSheet.createRow(1);
		row.createCell(0).setCellValue("OE");
		row.createCell(1).setCellValue("ES");
		row.createCell(2).setCellValue("R");
		row.createCell(3).setCellValue("Descrições");
		row.createCell(4).setCellValue("REM");
		row.createCell(5).setCellValue("Indicador");
		row.createCell(6).setCellValue("Situação");
		row.createCell(7).setCellValue("Responsável");
		row.createCell(8).setCellValue("Área Responsável");
	}

	private void populaPlanilhaPorSecretaria(HSSFSheet firstSheet, ItemEstruturaDao dao, List<ObjetivoEstrategico> lista) throws ECARException {
		int i = 2;
		int totalResultado = 0;
		// popula a primeira linha da planinha com os títulos padrões. 
		preencheCabecalho(firstSheet);
		
		for (ObjetivoEstrategico objetivoEstrategico : lista) {

			for (Estrategia estrategia : objetivoEstrategico.getEstrategias()) {

				for (Resultado resultado : estrategia.getResultados()) {

					HSSFRow row = firstSheet.createRow(i);

					String secretariaResponsavel = "";

					ItemEstruturaIett itemEstrutura = null;

					if (resultado.responsavel != null) {
						itemEstrutura = (ItemEstruturaIett) dao.buscar(ItemEstruturaIett.class, resultado.getCodigo());
						if (itemEstrutura.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null) {
							secretariaResponsavel = itemEstrutura.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg();
						}
					}

					row.createCell(0).setCellValue(objetivoEstrategico.getSiglaIett());
					row.createCell(1).setCellValue(estrategia.getSiglaEstrategia());
					row.createCell(2).setCellValue(resultado.getSiglaResultado());
					row.createCell(3).setCellValue(resultado.getNome());
					row.createCell(7).setCellValue(resultado.responsavel.getNome());
					row.createCell(8).setCellValue((resultado.responsavel != null ? resultado.responsavel.getNomeEOrgao() : ""));
					// if (resultado.getPrioritario() == "S") {
					// row.getCell(0).setCellStyle(estilo);
					// row.getCell(1).setCellStyle(estilo);
					// row.getCell(2).setCellStyle(estilo);
					// row.getCell(3).setCellStyle(estilo);
					// row.getCell(4).setCellStyle(estilo);
					// row.getCell(1).setCellValue("X");
					// }
					i++;
					totalResultado++;
				}
			}
		}
		// }
		HSSFRow row = firstSheet.createRow(i++);
		// row.createCell(0).setCellValue("Total Resultados -> "+
		// totalResultado++);
	}

}