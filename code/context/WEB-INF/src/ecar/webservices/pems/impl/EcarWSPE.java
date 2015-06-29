/**
 * 
 */
package ecar.webservices.pems.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import comum.util.Data;
import comum.util.EtiquetaUtils;

import ecar.dao.EtiquetaDao;
import ecar.dao.ExercicioDao;
import ecar.dao.ItemEstruturaAcaoDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.OrgaoDao;
import ecar.dao.SiteDao;
import ecar.dao.UsuarioDao;
import ecar.exception.ECARException;
import ecar.pojo.ExercicioExe;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.acompanhamentoEstrategico.Comentario;
import ecar.pojo.acompanhamentoEstrategico.Estrategia;
import ecar.pojo.acompanhamentoEstrategico.Etiqueta;
import ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico;
import ecar.pojo.acompanhamentoEstrategico.PeriodoAcompanhamento;
import ecar.pojo.acompanhamentoEstrategico.Produto;
import ecar.pojo.acompanhamentoEstrategico.Responsavel;
import ecar.pojo.acompanhamentoEstrategico.Resultado;
import ecar.pojo.acompanhamentoEstrategico.ResultadoStatusContar;
import ecar.servlet.indicador.IndicadorMonitoramentoDto;
import ecar.servlet.indicador.IndicadorValorDto;
import ecar.webservices.Seguranca;
import ecar.webservices.SegurancaWS;
import ecar.webservices.exc.SegurancaWSException;

/**
 * @author gekson.silva
 * 
 */
@Path("/pems")
@Consumes({ "application/xml", "application/json" })
@Produces("application/json")
public class EcarWSPE implements ecar.webservices.pems.EcarWSPE {

	protected Logger logger = Logger.getLogger(EcarWSPE.class);

	private ItemEstruturaAcaoDao daoIettAcao = new ItemEstruturaAcaoDao(null);
	private SegurancaWS segurancaWS = SegurancaWS.getInstance();

	@SuppressWarnings("rawtypes")
	@GET
	@Path("loginWS")
	public Seguranca loginWS(@MatrixParam("usuario") String usuario, @MatrixParam("senha") String senha) {
		logger.debug("Iniciando autenticacao via loginWS - SITE PEMS");
		Seguranca seguranca = segurancaWS.autenticacaoFALHA();
		try {
			UsuarioUsu usu = segurancaWS.autentica(usuario, senha);
			// resposta = Response.ok(usu.getSessionWS().getUuid()).build();

			String uuid = usu.getSessionWS().getUuid() + ">" + usu.getCodUsu() + ">";

			UsuarioDao usuarioDao = new UsuarioDao();
			Set grupoUsuario = usuarioDao.getClassesAcessoUsuario(usu);
			for (Object atributoSatb : grupoUsuario) {
				try {
					uuid += ((SisAtributoSatb) atributoSatb).getCodSatb().toString() + ",";
				} catch (ClassCastException e) {
					// TODO: handle exception
				}
			}

			seguranca = segurancaWS.autenticacaoOk(uuid.substring(0, uuid.length() - 1));

			logger.debug("Sucesso na autenticacao");
		} catch (SegurancaWSException e) {
			logger.error("Problemas na autenticacao", e.getCause());
		} catch (ECARException e) {
			logger.error("Problemas ao buscar grupoUsuario", e.getCause());
			e.printStackTrace();
		}
		return seguranca;
	}

	@GET
	@Path("logOffWS")
	public Seguranca logOffWS(@MatrixParam("uuid") String uuid) {
		logger.debug("Iniciando logoffWS - SITE PEMS");
		Seguranca seguranca = segurancaWS.autenticacaoFALHA();
		try {
			segurancaWS.logOff(uuid);

			logger.debug("Sucesso no logoff");
		} catch (SegurancaWSException e) {
			logger.error("Problemas no logoff", e.getCause());
		}
		return seguranca;
	}

	@GET
	@Path("buscaEtiqueta")
	public List<Etiqueta> buscaEtiqueta(@MatrixParam("uuid") String uuid, @MatrixParam("nome") String nome) {

		List<Long> gruposUsuarios = new ArrayList<Long>();
		Seguranca seguranca = new Seguranca();
		seguranca.setUuid(uuid);

		desmembraUUID(seguranca, gruposUsuarios);

		try {
			if (!segurancaWS.verificaSessao(seguranca.getUuid())) {
				return new ArrayList<Etiqueta>();
			}
		} catch (SegurancaWSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EtiquetaDao dao = new EtiquetaDao();
		String nomeF = "";
		if (nome != null) {
			nomeF = EtiquetaUtils.fonetizar(nome.replace("%20", " ").trim().toUpperCase());
		} else {
			nomeF = nome;
		}
		return dao.listarEtiqueta(null, "N", nomeF);
	}

	private void desmembraUUID(Seguranca seguranca, List<Long> gruposUsuarios) {
		String[] uuidDesmontada = seguranca.getUuid().split(">");

		seguranca.setUuid(uuidDesmontada[0]);
		seguranca.setCodigoUsuario(new Long(uuidDesmontada[1]));

		String[] grupos = uuidDesmontada[2].split(",");
		for (int i = 0; i < grupos.length; i++) {
			gruposUsuarios.add(new Long(grupos[i]));
		}
	}

	@GET
	@Path("buscaEtiquetaPrioritaria")
	public List<Etiqueta> buscaEtiquetaPrioritaria(@MatrixParam("uuid") String uuid) {

		EtiquetaDao dao = new EtiquetaDao();

		return dao.listarEtiqueta(null, "S", null);
	}

	@GET
	@Path("buscaObjetivoEstrategicoFiltro")
	public List<ObjetivoEstrategico> buscaObjetivoEstrategicoFiltro(@MatrixParam("uuid") String uuid, @MatrixParam("codOE") List<Integer> codigoObjetivoEstrategico,
			@MatrixParam("etiqueta") List<String> etiqueta, @MatrixParam("etiquetaP") List<String> etiquetaPrioritaria, @MatrixParam("rStatus") List<Integer> resultadoStatus,
			@MatrixParam("exercicio") Long exercicio, @MatrixParam("painel") int painelIndicador) {

		List<Long> gruposUsuarios = new ArrayList<Long>();
		Seguranca seguranca = new Seguranca();
		seguranca.setUuid(uuid);

		desmembraUUID(seguranca, gruposUsuarios);
		try {
			if (!segurancaWS.verificaSessao(seguranca.getUuid())) {
				return new ArrayList<ObjetivoEstrategico>();
			}
		} catch (SegurancaWSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Long init = System.currentTimeMillis();
		SiteDao dao = new SiteDao();
//		Site2Dao dao = new Site2Dao();
		List<Long> secretarias = new ArrayList<Long>();

		Boolean prioritario = verificaPrioritarioSecretaria(etiquetaPrioritaria, secretarias);

		List<String> etiquetaF = fonetizarEtiqueta(etiqueta);
		List<String> prioritariaF = fonetizarEtiqueta(etiquetaPrioritaria);

		etiquetaF.addAll(prioritariaF);

		List<ObjetivoEstrategico> retorno = dao.getListaObjetivoEstrategicoFiltroPE(
				codigoObjetivoEstrategico, 
				etiquetaF, 
				null, 
				resultadoStatus, 
				seguranca.getCodigoUsuario(), 
				gruposUsuarios,
				false,
				false,
				prioritario, 
				secretarias,
				false,
				null,
				null,
				exercicio,
				false,
				0L,
				false,
				painelIndicador
		);

		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("listarObjetivoEstrategicoFiltro - Demorou: " + (diff / 1000) + " segundos");

		return retorno;

	}

	private Boolean verificaPrioritarioSecretaria(List<String> etiqueta, List<Long> secretarias) {
		OrgaoDao orgaoDao = new OrgaoDao(null);
		Boolean prioritario = false;

		Map<String, String> mapaSecretaria = montaMapaSecretaria();
		List<String> excluidos = new ArrayList<String>();

		for (String et : etiqueta) {
			if (et.equals("PRIORITARIO")) {
				prioritario = true;
				excluidos.add(et);
			}
			if (mapaSecretaria.containsKey(et)) {
				secretarias.addAll(orgaoDao.listarIdsSecretarias(et));
				excluidos.add(et);
			}
		}

		etiqueta.removeAll(excluidos);

		return prioritario;
	}

	private Map<String, String> montaMapaSecretaria() {
		Map<String, String> mapaSecretaria = new HashMap<String, String>();

		EtiquetaDao etiquetaDao = new EtiquetaDao();
		List<ecar.pojo.acompanhamentoEstrategico.Etiqueta> listaEtiqueta = etiquetaDao.listarEtiqueta(null, "S", null);

		for (ecar.pojo.acompanhamentoEstrategico.Etiqueta etiqueta : listaEtiqueta) {

			if (etiqueta.getCategoria().getCodigo() == 9L || etiqueta.getCategoria().getCodigo() == 10L) {
				mapaSecretaria.put(etiqueta.getNome(), etiqueta.getNome());

			}

		}

		return mapaSecretaria;
	}

	private List<String> fonetizarEtiqueta(List<String> etiqueta) {
		List<String> etiquetaF = new ArrayList<String>();

		for (String et : etiqueta) {
			etiquetaF.add(EtiquetaUtils.fonetizar(et.replace("%20", " ").trim()));
		}
		return etiquetaF;
	}

	@GET
	@Path("buscaObjetivoEstrategicoLista")
	public List<ObjetivoEstrategico> buscaObjetivoEstrategicoLista(@MatrixParam("uuid") String uuid) {

		List<Long> gruposUsuarios = new ArrayList<Long>();
		Seguranca seguranca = new Seguranca();
		seguranca.setUuid(uuid);

		desmembraUUID(seguranca, gruposUsuarios);
		try {
			if (!segurancaWS.verificaSessao(seguranca.getUuid())) {
				System.out.println("AQUI");
				return new ArrayList<ObjetivoEstrategico>();
			}
		} catch (SegurancaWSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ItemEstruturaDao dao = new ItemEstruturaDao(null);

		return dao.listarObjetivoEstrategico(null, seguranca.getCodigoUsuario(), gruposUsuarios);
	}

	@GET
	@Path("buscaProduto")
	public Produto buscaProduto(@MatrixParam("uuid") String uuid, @MatrixParam("codP") Integer codigoProduto, @MatrixParam("exercicio") Long exercicio) {
		List<Long> gruposUsuarios = new ArrayList<Long>();
		Seguranca seguranca = new Seguranca();
		seguranca.setUuid(uuid);

		desmembraUUID(seguranca, gruposUsuarios);
		try {
			if (!segurancaWS.verificaSessao(seguranca.getUuid())) {
				return new Produto();
			}
		} catch (SegurancaWSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		SiteDao dao = new SiteDao();
		
		return dao.listarProduto(codigoProduto, seguranca.getCodigoUsuario(), gruposUsuarios, seguranca.getUuid(), exercicio, 0L);
//		return dao.listarProduto2(codigoProduto, seguranca.getCodigoUsuario(), gruposUsuarios, seguranca.getUuid(), exercicio, 0L);
	}

	@GET
	@Path("buscaResultado")
	public Resultado buscaResultado(@MatrixParam("uuid") String uuid, @MatrixParam("codR") Integer codigoResultado, @MatrixParam("mes") String mes, @MatrixParam("ano") String ano,
			@MatrixParam("exercicio") Long exercicio) {
		List<Long> gruposUsuarios = new ArrayList<Long>();
		Seguranca seguranca = new Seguranca();
		
		System.out.println(uuid + " $$ " + codigoResultado);
		
		seguranca.setUuid(uuid);

		desmembraUUID(seguranca, gruposUsuarios);
		try {
			if (!segurancaWS.verificaSessao(seguranca.getUuid())) {
				return new Resultado();
			}
		} catch (SegurancaWSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		SiteDao dao = new SiteDao();

		try {
//			return dao.listarResultado2(codigoResultado, seguranca.getCodigoUsuario(), gruposUsuarios, seguranca.getUuid(), exercicio, null);
			return dao.listarResultado(codigoResultado, seguranca.getCodigoUsuario(), gruposUsuarios, seguranca.getUuid(), exercicio, null);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@GET
	@Path("buscaResultadoStatus")
	public List<ResultadoStatusContar> buscaResultadoStatus(@MatrixParam("uuid") String uuid, @MatrixParam("exercicio") Long exercicio) {

		SiteDao dao = new SiteDao();

		return dao.listarResultadoStatus(null, exercicio);
	}

	@GET
	@Path("buscaComentario")
	public List<Comentario> buscaComentario(@MatrixParam("uuid") String uuid, @MatrixParam("codR") Integer codigoResultado) {
		return daoIettAcao.loadComentarioWS(codigoResultado.longValue(), null, null);
	}

	@GET
	@Path("gravarComentario")
	public Comentario gravarComentario(@MatrixParam("uuid") String uuid, @MatrixParam("codR") Integer codResultado, @MatrixParam("texto") String texto,
			@MatrixParam("atencaoMinistro") Boolean atencaoMinistro, @MatrixParam("prazo") String prazo, @MatrixParam("codResponsavel") Integer codResponsavel) {
		System.out.println("COMENTARIO: " + texto);
		Comentario comentario = new Comentario();
		comentario.setCodResultado(codResultado);
		comentario.setPrazo(prazo != null ? Data.parseDate(prazo.replace("%2F", "/")) : new Date());
		comentario.setCodResponsavel(codResponsavel != null ? new Long(codResponsavel) : 0);
		decodificarISO(texto, comentario);
		System.out.println("COMENTARIO DECODE: " + comentario.getTexto());

		try {
			daoIettAcao.salvarComentarioWS(comentario);
			marcarAtencaoMinistro(uuid, codResultado);
			return comentario;
		} catch (ECARException e) {
			e.printStackTrace();
			return new Comentario();
		}
	}

	private void decodificarISO(String texto, Comentario comentario) {
		try {
			// String decode = convertUTF8toISO(texto);
			// String decode = URLDecoder.decode(texto, "ISO-8859-1");
			String decode = decode(texto, "ISO-8859-1");

			comentario.setTexto(decode);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private String convertUTF8toISO(String str) {
		String ret = null;
		try {
			ret = new String(str.getBytes("ISO-8859-1"), "UTF-8");
		} catch (java.io.UnsupportedEncodingException e) {
			return null;
		}
		return ret;
	}

	@GET
	@Path("marcarAtencaoMinistro")
	public Response marcarAtencaoMinistro(@MatrixParam("uuid") String uuid, @MatrixParam("codR") Integer codResultado) {
		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		Response resposta = Response.ok("SUCESSO").build();

		try {
			dao.marcarDesmarcarAtencaoMinistro(codResultado.longValue());
		} catch (ECARException e) {
			resposta = Response.ok("ERRO").build();
			e.printStackTrace();
		}

		return resposta;
	}

	@GET
	@Path("buscaResponsavel")
	public List<Responsavel> buscaResponsavel(@MatrixParam("uuid") String uuid, @MatrixParam("nome") String nome) {

		List<Long> gruposUsuarios = new ArrayList<Long>();
		Seguranca seguranca = new Seguranca();
		seguranca.setUuid(uuid);

		desmembraUUID(seguranca, gruposUsuarios);

		try {
			if (!segurancaWS.verificaSessao(seguranca.getUuid())) {
				return new ArrayList<Responsavel>();
			}
		} catch (SegurancaWSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		UsuarioDao dao = new UsuarioDao();

		return dao.buscaResponsavel(nome);
	}

	public String decode(String s, String enc) throws UnsupportedEncodingException {

		boolean needToChange = false;
		int numChars = s.length();
		StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);
		int i = 0;

		if (enc.length() == 0) {
			throw new UnsupportedEncodingException("URLDecoder: empty string enc parameter");
		}

		char c;
		byte[] bytes = null;
		while (i < numChars) {
			c = s.charAt(i);
			switch (c) {
			case '+':
				sb.append(' ');
				i++;
				needToChange = true;
				break;
			case '%':
				/*
				 * Starting with this instance of %, process all consecutive
				 * substrings of the form %xy. Each substring %xy will yield a
				 * byte. Convert all consecutive bytes obtained this way to
				 * whatever character(s) they represent in the provided
				 * encoding.
				 */

				try {

					// (numChars-i)/3 is an upper bound for the number
					// of remaining bytes
					if (bytes == null)
						bytes = new byte[(numChars - i) / 3];
					int pos = 0;

					while (((i + 2) < numChars) && (c == '%')) {
						bytes[pos++] = (byte) Integer.parseInt(s.substring(i + 1, i + 3), 16);
						i += 3;
						if (i < numChars)
							c = s.charAt(i);
					}

					// A trailing, incomplete byte encoding such as
					// "%x" will cause an exception to be thrown

					if ((i < numChars) && (c == '%'))
						throw new IllegalArgumentException("URLDecoder: Incomplete trailing escape (%) pattern");

					sb.append(new String(bytes, 0, pos, enc));
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - " + e.getMessage());
				}
				needToChange = true;
				break;
			default:
				sb.append(c);
				i++;
				break;
			}
		}

		return (needToChange ? sb.toString() : s);
	}

	@GET
	@Path("excluirComentario")
	public Response excluirComentario(@MatrixParam("uuid") String uuid, @MatrixParam("codComentario") Integer codComentario) {
		Comentario comentario = new Comentario();
		comentario.setCodigo(codComentario);
		Response resposta = Response.ok("SUCESSO").build();

		try {
			daoIettAcao.excluirComentarioWS(comentario);

		} catch (ECARException e) {
			e.printStackTrace();
			resposta = Response.ok("ERRO").build();
		}

		return resposta;
	}

	@GET
	@Path("buscaExercicio")
	public List<ExercicioExe> buscaExercicio(@MatrixParam("uuid") String uuid, @MatrixParam("nome") String nome) {
		List<Long> gruposUsuarios = new ArrayList<Long>();
		Seguranca seguranca = new Seguranca();
		seguranca.setUuid(uuid);

		desmembraUUID(seguranca, gruposUsuarios);

		try {
			if (!segurancaWS.verificaSessao(seguranca.getUuid())) {
				return new ArrayList<ExercicioExe>();
			}
		} catch (SegurancaWSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ExercicioDao dao = new ExercicioDao(null);

		return dao.listarExercios();
	}

	@GET
	@Path("buscaPainelIndicadores")
	public List<ObjetivoEstrategico> buscaPainelIndicadores(@MatrixParam("uuid") String uuid, @MatrixParam("codOE") List<Integer> codigoObjetivoEstrategico,
			@MatrixParam("etiqueta") List<String> etiqueta, @MatrixParam("etiquetaP") List<String> etiquetaPrioritaria, @MatrixParam("rStatus") List<Integer> resultadoStatus,
			@MatrixParam("exercicio") Long exercicio, @MatrixParam("painel") int painelIndicador) {

		List<Long> gruposUsuarios = new ArrayList<Long>();
		Seguranca seguranca = new Seguranca();
		seguranca.setUuid(uuid);

		desmembraUUID(seguranca, gruposUsuarios);
		try {
			if (!segurancaWS.verificaSessao(seguranca.getUuid())) {
				return new ArrayList<ObjetivoEstrategico>();
			}
		} catch (SegurancaWSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Long init = System.currentTimeMillis();
		ItemEstruturaDao dao = new ItemEstruturaDao(null);
		List<Long> secretarias = new ArrayList<Long>();

		Boolean prioritario = verificaPrioritarioSecretaria(etiquetaPrioritaria, secretarias);

		List<String> etiquetaF = fonetizarEtiqueta(etiqueta);
		List<String> prioritariaF = fonetizarEtiqueta(etiquetaPrioritaria);

		etiquetaF.addAll(prioritariaF);

		List<ObjetivoEstrategico> listaOe = dao.listarObjetivoEstrategicoFiltroMaisRapidoEuEspero(codigoObjetivoEstrategico, etiquetaF, null, resultadoStatus, seguranca.getCodigoUsuario(),
				gruposUsuarios, prioritario, secretarias, exercicio, painelIndicador, 0L);

		/* List<Resultado> retorno = new ArrayList<Resultado>(); */
		removeResultadoSemIndicador(listaOe);

		Long end = System.currentTimeMillis();
		Long diff = end - init;
		System.out.println("listarObjetivoEstrategicoFiltro - Demorou: " + (diff / 1000) + " segundos");

		return listaOe;
	}

	/**
	 * Remover resultados que não tenham indicadores
	 * 
	 * @param listaOe
	 */
	private void removeResultadoSemIndicador(List<ObjetivoEstrategico> listaOe) {
		List<Resultado> listaExclusao = new ArrayList<Resultado>();
		for (ObjetivoEstrategico oe : listaOe) {
			for (Estrategia estrategia : oe.estrategias) {
				// retorno.addAll(estrategia.resultados);
				for (Resultado resultado : estrategia.resultados) {
					if (resultado.periodoAcompanhamento != null) {
						for (PeriodoAcompanhamento periodoAcompanhamento : resultado.periodoAcompanhamento) {
							if (periodoAcompanhamento.indMonitoramento.isEmpty()) {
								listaExclusao.add(resultado);
							} else {
								IndicadorMonitoramentoDto ind = new IndicadorMonitoramentoDto();
								ind.setId(0L);
								ind.setNomeIndicador("");
								ind.setTipo("");
								ind.setCodAref(0L);
								ind.setCodArf(0L);
								ind.setUnidadeMedida("");
								ind.setValores(new ArrayList<IndicadorValorDto>());
								periodoAcompanhamento.indMonitoramento.add(ind);
								break;
							}
						}
					}
				}
			}
		}

		for (ObjetivoEstrategico oe : listaOe) {
			for (Estrategia estrategia : oe.estrategias) {
				for (Resultado exclui : listaExclusao) {
					estrategia.resultados.remove(exclui);
				}
			}
		}
	}
}