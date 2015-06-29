/*
 * Criado em 07/12/2004
 *
 */
package ecar.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.database.SentinelaUtil;
import comum.util.Data;
import comum.util.FileUpload;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.EntidadeEnt;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.PaiFilho;
import ecar.pojo.SessionWS;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.TipoEnderecoCorrTpec;
import ecar.pojo.Uf;
import ecar.pojo.UsuarioAtributoUsua;
import ecar.pojo.UsuarioUsu;
import ecar.pojo.acompanhamentoEstrategico.Responsavel;
import ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid;
import ecar.taglib.util.Input;
import gov.pr.celepar.sentinela.comunicacao.SentinelaAdmInterface;
import gov.pr.celepar.sentinela.comunicacao.SentinelaInterface;
import gov.pr.celepar.sentinela.comunicacao.SentinelaParam;
import gov.pr.celepar.sentinela.excecao.SentinelaException;

/**
 * @author felipev
 * 
 */
@SuppressWarnings("rawtypes")
public class UsuarioDao extends Dao {

	/**
     *
     */
	public static String INFORMACAO_NAO_LOCALIZADA_SENTINELA = "Usu�rio n�o encontrado";
	private static String EMAIL_NAO_LOCALIZADO_SENTINELA = "";

	/*
	 * private SisGrupoAtributoDao sisGrupoAtributoDao = null; private
	 * ConfiguracaoDao configuracaoDao = null;
	 */

	ValidaPermissao validaPermissao = new ValidaPermissao();

	/**
         *
         */
	public UsuarioDao() {
		super();
	}

	/**
	 * Construtor. Chama o Session factory do Hibernate
	 * 
	 * @param request
	 */

	@SuppressWarnings("unchecked")
	public UsuarioDao(HttpServletRequest request) {
		super();
		this.request = request;
		/*
		 * sisGrupoAtributoDao = new SisGrupoAtributoDao(request);
		 * configuracaoDao = new ConfiguracaoDao(request);
		 */
	}

	/**
	 * Atribui os valores passados por request a um objeto Usu�rio
	 * 
	 * @param request
	 *            Request
	 * @param usuario
	 * @param usarParamStr
	 *            <b>True </b> se os par�metros ser�o recuperados por
	 *            Pagina.getParamStr <b>False </b> se os par�metros ser�o
	 *            recuperados por Pagina.getParam
	 * @throws ECARException
	 */
	public void setUsuario(HttpServletRequest request, UsuarioUsu usuario, boolean usarParamStr) throws ECARException {

		if (usarParamStr) {
			// usuario.setNomeUsu(Pagina.getParamStr(request, "nomeUsu"));
			usuario.setDataNascimentoUsu(Data.parseDate(Pagina.getParamStr(request, "dataNascimentoUsu")));
			if (!"".equals(Pagina.getParamStr(request, "dataNascimentoUsu")))
				usuario.setDataNascimentoUsu(Data.parseDate(Pagina.getParamStr(request, "dataNascimentoUsu")));
			else
				usuario.setDataNascimentoUsu(null);
			// usuario.setCnpjCpfUsu(Pagina.getParamStr(request, "cnpjCpfUsu"));
			usuario.setResidEnderecoUsu(Pagina.getParamStr(request, "residEnderecoUsu"));
			usuario.setResidComplementoUsu(Pagina.getParamStr(request, "residComplementoUsu"));
			usuario.setResidBairroUsu(Pagina.getParamStr(request, "residBairroUsu"));
			usuario.setResidCidadeUsu(Pagina.getParamStr(request, "residCidadeUsu"));
			usuario.setResidCepUsu(Pagina.getParamStr(request, "residCepUsu"));
			usuario.setResidBairroUsu(Pagina.getParamStr(request, "residBairroUsu"));
			usuario.setResidDddUsu(Pagina.getParamStr(request, "residDddUsu"));
			usuario.setResidTelefoneUsu(Pagina.getParamStr(request, "residTelefoneUsu"));
			usuario.setResidRamalUsu(Pagina.getParamStr(request, "residRamalUsu"));
			usuario.setComercEnderecoUsu(Pagina.getParamStr(request, "comercEnderecoUsu"));
			usuario.setComercComplementoUsu(Pagina.getParamStr(request, "comercComplementoUsu"));
			usuario.setComercBairroUsu(Pagina.getParamStr(request, "comercBairroUsu"));
			usuario.setComercCidadeUsu(Pagina.getParamStr(request, "comercCidadeUsu"));
			usuario.setComercCepUsu(Pagina.getParamStr(request, "comercCepUsu"));
			usuario.setComercBairroUsu(Pagina.getParamStr(request, "comercBairroUsu"));
			usuario.setComercDddUsu(Pagina.getParamStr(request, "comercDddUsu"));
			usuario.setComercTelefoneUsu(Pagina.getParamStr(request, "comercTelefoneUsu"));
			usuario.setComercRamalUsu(Pagina.getParamStr(request, "comercRamalUsu"));
			usuario.setFaxUsu(Pagina.getParamStr(request, "faxUsu"));
			usuario.setDddFaxUsu(Pagina.getParamStr(request, "dddFaxUsu"));
			// usuario.setEmail1Usu(Pagina.getParamStr(request, "email1Usu"));
			usuario.setEmail2Usu(Pagina.getParamStr(request, "email2Usu"));
			usuario.setIdDominioUsu(Pagina.getParamStr(request, "idDominioUsu"));
			// usuario.setIdUsuarioUsu(Pagina.getParamStr(request,
			// "idUsuarioUsu"));
			// usuario.setSenhaUsu(Pagina.getParamStr(request, "senhaUsu"));
			// usuario.setPerguntaLembreteUsu(Pagina.getParamStr(request,"perguntaLembreteUsu"));
			// usuario.setRespostaLembreteUsu(Pagina.getParamStr(request,"respostaLembreteUsu"));
			usuario.setIndAtivoUsu(Pagina.getParamStr(request, "indAtivoUsu"));
		} else {
			// usuario.setNomeUsu(Pagina.getParam(request, "nomeUsu"));
			if (!"".equals(Pagina.getParamStr(request, "dataNascimentoUsu")))
				usuario.setDataNascimentoUsu(Data.parseDate(Pagina.getParamStr(request, "dataNascimentoUsu")));
			// usuario.setCnpjCpfUsu(Pagina.getParam(request, "cnpjCpfUsu"));
			usuario.setResidEnderecoUsu(Pagina.getParam(request, "residEnderecoUsu"));
			usuario.setResidComplementoUsu(Pagina.getParam(request, "residComplementoUsu"));
			usuario.setResidBairroUsu(Pagina.getParam(request, "residBairroUsu"));
			usuario.setResidCidadeUsu(Pagina.getParam(request, "residCidadeUsu"));
			usuario.setResidCepUsu(Pagina.getParam(request, "residCepUsu"));
			usuario.setResidBairroUsu(Pagina.getParam(request, "residBairroUsu"));
			usuario.setResidDddUsu(Pagina.getParam(request, "residDddUsu"));
			usuario.setResidTelefoneUsu(Pagina.getParam(request, "residTelefoneUsu"));
			usuario.setResidRamalUsu(Pagina.getParam(request, "residRamalUsu"));
			usuario.setComercEnderecoUsu(Pagina.getParam(request, "comercEnderecoUsu"));
			usuario.setComercComplementoUsu(Pagina.getParam(request, "comercComplementoUsu"));
			usuario.setComercBairroUsu(Pagina.getParam(request, "comercBairroUsu"));
			usuario.setComercCidadeUsu(Pagina.getParam(request, "comercCidadeUsu"));
			usuario.setComercCepUsu(Pagina.getParam(request, "comercCepUsu"));
			usuario.setComercBairroUsu(Pagina.getParam(request, "comercBairroUsu"));
			usuario.setComercDddUsu(Pagina.getParam(request, "comercDddUsu"));
			usuario.setComercTelefoneUsu(Pagina.getParam(request, "comercTelefoneUsu"));
			usuario.setComercRamalUsu(Pagina.getParam(request, "comercRamalUsu"));
			usuario.setFaxUsu(Pagina.getParam(request, "faxUsu"));
			usuario.setDddFaxUsu(Pagina.getParam(request, "dddFaxUsu"));
			// usuario.setEmail1Usu(Pagina.getParam(request, "email1Usu"));
			usuario.setEmail2Usu(Pagina.getParam(request, "email2Usu"));
			usuario.setIdDominioUsu(Pagina.getParam(request, "idDominioUsu"));
			// usuario.setIdUsuarioUsu(Pagina.getParam(request,
			// "idUsuarioUsu"));
			// usuario.setSenhaUsu(Pagina.getParam(request, "senhaUsu"));
			// usuario.setPerguntaLembreteUsu(Pagina.getParam(request,"perguntaLembreteUsu"));
			// usuario.setRespostaLembreteUsu(Pagina.getParam(request,"respostaLembreteUsu"));
			usuario.setIndAtivoUsu(Pagina.getParam(request, "indAtivoUsu"));
		}

		usuario.setEmail1Usu(usuario.getEmail1UsuSent());
		usuario.setNomeUsu(usuario.getNomeUsuSent());

		/*
		 * Igor em 01/06/2006 Inclus�o de "n" orgaos por usuario
		 */
		if (Pagina.getParamInt(request, "numOrgaos") > 0)
			usuario.setOrgaoOrgs(new HashSet());
		for (int i = 1; i <= Pagina.getParamInt(request, "numOrgaos"); i++) {
			if ("S".equals(Pagina.getParamStr(request, "adicionaOrgaoOrg" + i))) {
				if (!"".equals(Pagina.getParamStr(request, "orgaoOrg" + i)))
					usuario.getOrgaoOrgs().add(((OrgaoOrg) super.buscar(OrgaoOrg.class, Long.valueOf(Pagina.getParamStr(request, "orgaoOrg" + i)))));
			}
		}
		/*
		 * Fim de Igor (01/06/2006)
		 */

		/*
		 * Jos� Andr� em 16/05/2008 Inclus�o de "n" entidades por usuario
		 */
		if (Pagina.getParamInt(request, "numEntidades") > 0)
			usuario.setEntidadeEnts(new HashSet());
		for (int i = 1; i <= Pagina.getParamInt(request, "numEntidades"); i++) {
			if ("S".equals(Pagina.getParamStr(request, "adicionaEntidadeEnt" + i))) {
				if (!"".equals(Pagina.getParamStr(request, "entidadeEnt" + i)))
					usuario.getEntidadeEnts().add(((EntidadeEnt) super.buscar(EntidadeEnt.class, Long.valueOf(Pagina.getParamStr(request, "entidadeEnt" + i)))));
			}
		}
		/*
		 * Fim Jos� Andr� (16/05/2008)
		 */

		if (!"".equals(Pagina.getParamStr(request, "residUfUsu")))
			usuario.setUfByResidUfUsu((Uf) super.buscar(Uf.class, Pagina.getParamStr(request, "residUfUsu")));
		if (!"".equals(Pagina.getParamStr(request, "comercUfUsu")))
			usuario.setUfByComercUfUsu((Uf) super.buscar(Uf.class, Pagina.getParamStr(request, "comercUfUsu")));
		if (!"".equals(Pagina.getParamStr(request, "tipoEnderecoCorrTpec")))
			usuario.setTipoEnderecoCorrTpec((TipoEnderecoCorrTpec) super.buscar(TipoEnderecoCorrTpec.class, Long.valueOf(Pagina.getParamStr(request, "tipoEnderecoCorrTpec"))));

		setAtributosUsuario(request, usuario, false);
		setClasseAcessoUsuario(request, usuario);

	}

	/**
	 * Atribui os valores passados por request a um objeto Usu�rio
	 * 
	 * @param request
	 *            Request
	 * @param usuario
	 * @throws ECARException
	 */
	public void setUsuarioCadastroSite(HttpServletRequest request, UsuarioUsu usuario) throws ECARException {

		// usuario.setNomeUsu(Pagina.getParamStr(request, "nomeUsu"));
		// usuario.setDataNascimentoUsu(Data.parseDate(Pagina.getParamStr(request,
		// "dataNascimentoUsu")));
		if (!"".equals(Pagina.getParamStr(request, "dataNascimentoUsu")))
			usuario.setDataNascimentoUsu(Data.parseDate(Pagina.getParamStr(request, "dataNascimentoUsu")));
		else
			usuario.setDataNascimentoUsu(null);
		// usuario.setCnpjCpfUsu(Pagina.getParamStr(request, "cnpjCpfUsu"));
		usuario.setResidEnderecoUsu(Pagina.getParamStr(request, "residEnderecoUsu"));
		usuario.setResidComplementoUsu(Pagina.getParamStr(request, "residComplementoUsu"));
		usuario.setResidBairroUsu(Pagina.getParamStr(request, "residBairroUsu"));
		usuario.setResidCidadeUsu(Pagina.getParamStr(request, "residCidadeUsu"));
		usuario.setResidCepUsu(Pagina.getParamStr(request, "residCepUsu"));
		usuario.setResidBairroUsu(Pagina.getParamStr(request, "residBairroUsu"));
		usuario.setResidDddUsu(Pagina.getParamStr(request, "residDddUsu"));
		usuario.setResidTelefoneUsu(Pagina.getParamStr(request, "residTelefoneUsu"));
		usuario.setResidRamalUsu(Pagina.getParamStr(request, "residRamalUsu"));
		usuario.setComercEnderecoUsu(Pagina.getParamStr(request, "comercEnderecoUsu"));
		usuario.setComercComplementoUsu(Pagina.getParamStr(request, "comercComplementoUsu"));
		usuario.setComercBairroUsu(Pagina.getParamStr(request, "comercBairroUsu"));
		usuario.setComercCidadeUsu(Pagina.getParamStr(request, "comercCidadeUsu"));
		usuario.setComercCepUsu(Pagina.getParamStr(request, "comercCepUsu"));
		usuario.setComercBairroUsu(Pagina.getParamStr(request, "comercBairroUsu"));
		usuario.setComercDddUsu(Pagina.getParamStr(request, "comercDddUsu"));
		usuario.setComercTelefoneUsu(Pagina.getParamStr(request, "comercTelefoneUsu"));
		usuario.setComercRamalUsu(Pagina.getParamStr(request, "comercRamalUsu"));
		usuario.setFaxUsu(Pagina.getParamStr(request, "faxUsu"));
		usuario.setDddFaxUsu(Pagina.getParamStr(request, "dddFaxUsu"));
		// usuario.setEmail1Usu(Pagina.getParamStr(request, "email1Usu"));
		usuario.setEmail2Usu(Pagina.getParamStr(request, "email2Usu"));
		// usuario.setSenhaUsu(Pagina.getParamStr(request, "senhaUsu"));
		// usuario.setPerguntaLembreteUsu(Pagina.getParamStr(request,"perguntaLembreteUsu"));
		// usuario.setRespostaLembreteUsu(Pagina.getParamStr(request,"respostaLembreteUsu"));
		if (!"".equals(Pagina.getParamStr(request, "residUfUsu")))
			usuario.setUfByResidUfUsu((Uf) super.buscar(Uf.class, Pagina.getParamStr(request, "residUfUsu")));
		if (!"".equals(Pagina.getParamStr(request, "comercUfUsu")))
			usuario.setUfByComercUfUsu((Uf) super.buscar(Uf.class, Pagina.getParamStr(request, "comercUfUsu")));
		if (!"".equals(Pagina.getParamStr(request, "tipoEnderecoCorrTpec")))
			usuario.setTipoEnderecoCorrTpec((TipoEnderecoCorrTpec) super.buscar(TipoEnderecoCorrTpec.class, Long.valueOf(Pagina.getParamStr(request, "tipoEnderecoCorrTpec"))));

		/*
		 * Igor em 01/06/2006 Inclus�o de "n" orgaos por usuario
		 */
		if (Pagina.getParamInt(request, "numOrgaos") > 0)
			usuario.setOrgaoOrgs(new HashSet());
		for (int i = 1; i <= Pagina.getParamInt(request, "numOrgaos"); i++) {
			if ("S".equals(Pagina.getParamStr(request, "adicionaOrgaoOrg" + i))) {
				if (!"".equals(Pagina.getParamStr(request, "orgaoOrg" + i)))
					usuario.getOrgaoOrgs().add(((OrgaoOrg) super.buscar(OrgaoOrg.class, Long.valueOf(Pagina.getParamStr(request, "orgaoOrg" + i)))));
			}
		}
		/*
		 * Fim de Igor (01/06/2006)
		 */

		setAtributosUsuario(request, usuario, true);

	}

	/**
	 * M�todo para criar a cole��o de atributos de Classe de Acesso do Usu�rio a
	 * partir de par�metros passados por request
	 * 
	 * @param request
	 * @param usuario
	 * @throws ECARException
	 */
	public void setClasseAcessoUsuario(HttpServletRequest request, UsuarioUsu usuario) throws ECARException {
		String[] classesAcesso = request.getParameterValues("classeAcesso");
		if (classesAcesso != null) {
			for (int i = 0; i < classesAcesso.length; i++) {
				UsuarioAtributoUsua usuarioAtributo = new UsuarioAtributoUsua();
				usuarioAtributo.setUsuarioUsu(usuario);
				usuarioAtributo.setSisAtributoSatb((SisAtributoSatb) super.buscar(SisAtributoSatb.class, Long.valueOf(classesAcesso[i])));
				usuarioAtributo.setDataInclusao(Data.getDataAtual());
				if (usuario.getUsuarioAtributoUsuas() == null)
					usuario.setUsuarioAtributoUsuas(new HashSet());
				usuario.getUsuarioAtributoUsuas().add(usuarioAtributo);
			}

		}
	}

	/**
	 * M�todo para criar a cole��o de atributos do usu�rio a partir de
	 * par�metros passados por request
	 * 
	 * @param request
	 * @param usuario
	 *            Objeto usu�rio a ser manipulado
	 * @param cadastroSite
	 * @throws ECARException
	 */
	public void setAtributosUsuario(HttpServletRequest request, UsuarioUsu usuario, boolean cadastroSite) throws ECARException {
		usuario.setUsuarioAtributoUsuas(null);
		List lAtributos;
		if (cadastroSite)
			lAtributos = new SisGrupoAtributoDao(request).getGruposAtributosCadastroUsuarioSite();
		else
			lAtributos = new SisGrupoAtributoDao(request).getGruposAtributosCadastro("U");
		Iterator it = lAtributos.iterator();
		while (it.hasNext()) {
			SisGrupoAtributoSga grupoAtributo = (SisGrupoAtributoSga) it.next();
			String tipoExibicao = "";
			if (grupoAtributo.getSisTipoExibicGrupoCadUsuSteg() != null) {
				tipoExibicao = grupoAtributo.getSisTipoExibicGrupoCadUsuSteg().getCodSteg().toString();
			} else {
				tipoExibicao = grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString();
			}
			if (!"".equals(Pagina.getParamStr(request, "a" + grupoAtributo.getCodSga().toString()))) {
				UsuarioAtributoUsua usuarioAtributo = new UsuarioAtributoUsua();
				usuarioAtributo.setUsuarioUsu(usuario);
				/*
				 * Caso o tipo de campo seja texto considera-se que o Grupo de
				 * Atributos possuir� apenas 1 atributo que o representa.
				 */
				if (SisTipoExibicGrupoDao.TEXT.equals(tipoExibicao) || SisTipoExibicGrupoDao.VALIDACAO.equals(tipoExibicao) || SisTipoExibicGrupoDao.TEXTAREA.equals(tipoExibicao)
						|| SisTipoExibicGrupoDao.IMAGEM.equals(tipoExibicao)) {
					if (grupoAtributo.getSisAtributoSatbs() != null && grupoAtributo.getSisAtributoSatbs().size() > 0) {
						usuarioAtributo.setInformacao(Pagina.getParamStr(request, "a" + grupoAtributo.getCodSga().toString()));
						usuarioAtributo.setSisAtributoSatb((SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next());

						String pathRaiz = request.getContextPath();

						// tratamento imagem
						String caminhoAuxiliarImagem = Pagina.getParamStr(request, "hidImagem" + "a" + grupoAtributo.getCodSga().toString());
						if (caminhoAuxiliarImagem != null && caminhoAuxiliarImagem.length() > 0) {

							String chave = usuarioAtributo.getInformacao();
							chave = chave.substring(chave.indexOf("RemoteFile=") + "RemoteFile=".length());
							UsuarioUsu usuarioSeg = ((ecar.login.SegurancaECAR) request.getSession().getAttribute("seguranca")).getUsuario();
							if (usuarioSeg.getMapArquivosAtuaisUsuarios() != null && usuarioSeg.getMapArquivosAtuaisUsuarios().containsKey(chave)) {
								// usuarioAtributo.setInformacao(usuarioSeg.getMapArquivosAtuaisUsuarios().get(chave));

								caminhoAuxiliarImagem = usuarioSeg.getMapArquivosAtuaisUsuarios().get(chave);
								caminhoAuxiliarImagem = pathRaiz + "/DownloadFile?RemoteFile=" + caminhoAuxiliarImagem;
							}
							// else{

							// salvar a imagem fisicamente que tem o caminho
							// real em "a" + codigo grupo
							try {
								String nomeArquivoNovo = FileUpload.salvarArquivoSessaoFisicamente(request, "a" + grupoAtributo.getCodSga().toString(), caminhoAuxiliarImagem);
								if (nomeArquivoNovo != null && !nomeArquivoNovo.equals(""))
									usuarioAtributo.setInformacao(nomeArquivoNovo);
							} catch (FileNotFoundException e) {
								throw new ECARException("erro.arquivoUrl", e, new String[] { caminhoAuxiliarImagem });
							} catch (Exception e) {
								throw new ECARException("erro.upload", e, new String[] { caminhoAuxiliarImagem });
							}
							// }
						}

						usuarioAtributo.setDataInclusao(Data.getDataAtual());
						if (usuario.getUsuarioAtributoUsuas() == null)
							usuario.setUsuarioAtributoUsuas(new HashSet());
						usuario.getUsuarioAtributoUsuas().add(usuarioAtributo);
					}
				} else {

					String[] atributos = request.getParameterValues("a" + grupoAtributo.getCodSga().toString());
					for (int i = 0; i < atributos.length; i++) {
						/*
						 * Tenho que criar novamente o usu�rio atributo sen�o
						 * ele n�o � adicionado no set no final deste la�o
						 */
						usuarioAtributo = new UsuarioAtributoUsua();
						usuarioAtributo.setUsuarioUsu(usuario);
						usuarioAtributo.setSisAtributoSatb((SisAtributoSatb) super.buscar(SisAtributoSatb.class, Long.valueOf(atributos[i])));
						usuarioAtributo.setDataInclusao(Data.getDataAtual());
						if (usuario.getUsuarioAtributoUsuas() == null)
							usuario.setUsuarioAtributoUsuas(new HashSet());
						usuario.getUsuarioAtributoUsuas().add(usuarioAtributo);
					}
				}
			}
			/*
			 * Foi necess�rio alterar o nome dos campos dos elementos
			 * multitexto, adicionando "-codSatb" Assim, ficamos com o nome do
			 * campo no seguinte padr�o: "a + codSteg + _ + codSatb" (ex.:
			 * a12_38) Isto foi feito visto a diferen�a existente entre um grupo
			 * com suporte a 1 campo texto e este, que suporta v�rios campos
			 * texto.
			 */
			else {
				if (SisTipoExibicGrupoDao.MULTITEXTO.equals(tipoExibicao)) {
					Enumeration lAtrib = request.getParameterNames();
					while (lAtrib.hasMoreElements()) {
						String atrib = (String) lAtrib.nextElement();
						if (atrib.lastIndexOf('_') > 0) {
							String nomeAtrib = atrib.substring(0, atrib.lastIndexOf('_'));
							String nomeCampo = atrib.substring(atrib.lastIndexOf('_') + 1);
							if (nomeAtrib.equals("a" + grupoAtributo.getCodSga().toString()) && !"".equals(Pagina.getParamStr(request, atrib))) {
								UsuarioAtributoUsua usuarioAtributo = new UsuarioAtributoUsua();
								usuarioAtributo.setUsuarioUsu(usuario);
								usuarioAtributo.setInformacao(Pagina.getParamStr(request, atrib));
								usuarioAtributo.setSisAtributoSatb((SisAtributoSatb) super.buscar(SisAtributoSatb.class, Long.valueOf(nomeCampo)));
								usuarioAtributo.setDataInclusao(Data.getDataAtual());
								if (usuario.getUsuarioAtributoUsuas() == null)
									usuario.setUsuarioAtributoUsuas(new HashSet());
								usuario.getUsuarioAtributoUsuas().add(usuarioAtributo);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Concatena todos as cole��es de Atributos e Perfis do Usu�rio em uma �nica
	 * lista e chama o m�todo salvar do Dao para gravar essa lista e o pr�prio
	 * usu�rio
	 * 
	 * @param usuario
	 * @throws ECARException
	 */
	public void salvar(UsuarioUsu usuario) throws ECARException {
		if (pesquisarDuplos(usuario, new String[] { "idDominioUsu" }, "codUsu").size() > 0)
			throw new ECARException("usuario.validacao.registroDuplicado");
		usuario.setDataInclusaoUsu(Data.getDataAtual());
		List filhos = new ArrayList();
		if (usuario.getUsuarioAtributoUsuas() != null)
			filhos.addAll(usuario.getUsuarioAtributoUsuas());
		super.salvar(usuario, filhos);
	}

	/**
	 * Altera um objeto estrutura e salva os registros filhos passados na List
	 * 
	 * @param usuario
	 * @param request
	 * @throws Exception
	 */
	public void alterar(UsuarioUsu usuario, HttpServletRequest request) throws Exception {
		Transaction tx = null;

		// verifica se o usu�rio possui um ID v�lido que correspondente ao ID da
		// base do Sentinela
		if (usuario.getIdDominioUsu() == null) {
			throw new ECARException("usuario.alteracao.erro.ausente.sentinela");
		}

		UsuarioUsu usuarioSentinela = this.consultarUsuarioSentinelaPorCodigo(Long.parseLong(usuario.getIdDominioUsu()));

		if (usuarioSentinela == null) {
			throw new ECARException("usuario.alteracao.erro.ausente.sentinela");
		}

		// List<String> listImagesAntigas = new ArrayList<String>();

		try {
			ArrayList objetos = new ArrayList();
			super.inicializarLogBean();

			tx = session.beginTransaction();

			/* apaga os filhos para serem gravados novamente */
			if (usuario.getUsuarioAtributoUsuas() != null) {
				Iterator itAtb = usuario.getUsuarioAtributoUsuas().iterator();
				while (itAtb.hasNext()) {
					UsuarioAtributoUsua usuAtrib = (UsuarioAtributoUsua) itAtb.next();
					session.delete(usuAtrib);
					if (usuAtrib.getSisAtributoSatb().getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg() == Input.IMAGEM) {

						String nomeCampo = request.getParameter("a" + usuAtrib.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga().toString());

						if (nomeCampo != null && nomeCampo.equals("")) {

							String fullFile = usuAtrib.getInformacao();

							if (fullFile.lastIndexOf("=") != -1)
								fullFile = fullFile.substring(fullFile.lastIndexOf("=") + 1);

							File f = new File(fullFile);
							if (f.exists())
								FileUpload.apagarArquivo(fullFile);
						}
					}
					objetos.add(usuAtrib);

					/*
					 * String tipoExibicao = ""; if
					 * (usuAtrib.getSisAtributoSatb(
					 * ).getSisGrupoAtributoSga().getSisTipoExibicGrupoCadUsuSteg
					 * () != null){ tipoExibicao =
					 * usuAtrib.getSisAtributoSatb().
					 * getSisGrupoAtributoSga().getSisTipoExibicGrupoCadUsuSteg
					 * ().getCodSteg().toString(); } else { tipoExibicao =
					 * usuAtrib.getSisAtributoSatb().getSisGrupoAtributoSga().
					 * getSisTipoExibicGrupoSteg().getCodSteg().toString(); }
					 * if(SisTipoExibicGrupoDao.IMAGEM.equals(tipoExibicao)) {
					 * listImagesAntigas.add(usuAtrib.getInformacao()); }
					 */
				}
			}
			usuario.setUsuarioAtributoUsuas(null);

			this.setUsuario(request, usuario, false);

			/*
			 * Iterator usuAtributoit =
			 * usuario.getUsuarioAtributoUsuas().iterator(); while
			 * (usuAtributoit.hasNext()) { UsuarioAtributoUsua usuAtrib =
			 * (UsuarioAtributoUsua) usuAtributoit.next(); String tipoExibicao =
			 * ""; if (usuAtrib.getSisAtributoSatb().getSisGrupoAtributoSga().
			 * getSisTipoExibicGrupoCadUsuSteg() != null) { tipoExibicao =
			 * usuAtrib.getSisAtributoSatb().getSisGrupoAtributoSga().
			 * getSisTipoExibicGrupoCadUsuSteg().getCodSteg().toString(); } else
			 * { tipoExibicao =
			 * usuAtrib.getSisAtributoSatb().getSisGrupoAtributoSga
			 * ().getSisTipoExibicGrupoSteg().getCodSteg().toString(); } if
			 * (SisTipoExibicGrupoDao.IMAGEM.equals(tipoExibicao)) {
			 * if(listImagesAntigas.contains(usuAtrib.getInformacao())) {
			 * listImagesAntigas.remove(usuAtrib.getInformacao()); } } }
			 */

			usuario.setDataUltAlteracaoUsu(Data.getDataAtual());

			List filhos = new ArrayList();
			if (usuario.getUsuarioAtributoUsuas() != null)
				filhos.addAll(usuario.getUsuarioAtributoUsuas());

			session.update(usuario);
			objetos.add(usuario);

			Iterator it = filhos.iterator();

			while (it.hasNext()) {
				PaiFilho object = (PaiFilho) it.next();
				object.atribuirPKPai();
				// salva os filhos
				session.save(object);
				objetos.add(object);
			}

			tx.commit();

			if (super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC_ALT_EXC");
				Iterator itObj = objetos.iterator();

				while (itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}

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
		} catch (ECARException e) {
			this.logger.error(e);
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			throw e;
		}

		/*
		 * -- Se a altera��o dos registros foi bem sucedida, realiza a exclus�o
		 * das imagens alteradas e registradas no servidor. --
		 */
		/*
		 * try { for( int i=0; i<listImagesAntigas.size(); i++ ) { if(
		 * listImagesAntigas.get(i) != null ) {
		 * 
		 * String caminhoImagem = listImagesAntigas.get(i); caminhoImagem =
		 * caminhoImagem.substring(caminhoImagem.lastIndexOf("=") + 1);
		 * 
		 * File f = new File(caminhoImagem); if(f.exists())
		 * FileUpload.apagarArquivo(caminhoImagem); } } } catch( Exception e ) {
		 * this.logger.error(e); throw new ECARException("erro.exception"); }
		 */
	}

	private UsuarioUsu consultarUsuarioSentinelaPorCodigo(long idDominio) {

		SentinelaInterface s = SentinelaUtil.getSentinelaInterface();

		// valida usuario na base do sentinela antes de seguir com a altera��o.
		long[] idSentinela = new long[] { idDominio };

		UsuarioUsu usuarioEcar = null;
		SentinelaParam[] usuarioSentinela = s.getUsuariosSistemaByCodigo(idSentinela);

		if (usuarioSentinela != null) {
			usuarioEcar = new UsuarioUsu();

			usuarioEcar.setIdDominioUsu(usuarioSentinela[0].getCodigo() + "");
			usuarioEcar.setNomeUsu(usuarioSentinela[0].getNome());

			String[] paramAux = usuarioSentinela[0].getParamAux();

			if (paramAux != null) {
				usuarioEcar.setCnpjCpfUsu(paramAux[1]);
				usuarioEcar.setEmail1Usu(paramAux[2]);
			}

		}

		return usuarioEcar;
	}

	/**
	 * Altera um objeto estrutura e salva os registros filhos passados na List
	 * 
	 * @param usuario
	 * @param request
	 * @throws ECARException
	 */
	public void alterarCadastroSite(UsuarioUsu usuario, HttpServletRequest request) throws ECARException {
		Transaction tx = null;

		try {
			ArrayList objetos = new ArrayList();
			super.inicializarLogBean();

			tx = session.beginTransaction();

			SisGrupoAtributoSga grupoClasseAcesso = new ConfiguracaoDao(request).getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso();

			/*
			 * apaga os filhos para serem gravados novamente (menos os de class
			 * de acesso e grupos de atributos que n�o aparecem no site)
			 */
			if (usuario.getUsuarioAtributoUsuas() != null) {
				Iterator itAtb = usuario.getUsuarioAtributoUsuas().iterator();
				while (itAtb.hasNext()) {
					UsuarioAtributoUsua usuAtrib = (UsuarioAtributoUsua) itAtb.next();
					SisGrupoAtributoSga grupoAtributo = usuAtrib.getSisAtributoSatb().getSisGrupoAtributoSga();
					if ((!grupoAtributo.equals(grupoClasseAcesso)) && (grupoAtributo != null && "S".equals(grupoAtributo.getIndCadSiteSga()))) {
						session.delete(usuAtrib);
						objetos.add(usuAtrib);
						itAtb.remove();
					}
				}
			}
			usuario.setUsuarioAtributoUsuas(null);

			this.setUsuarioCadastroSite(request, usuario);

			usuario.setDataUltAlteracaoUsu(Data.getDataAtual());

			List filhos = new ArrayList();
			if (usuario.getUsuarioAtributoUsuas() != null)
				filhos.addAll(usuario.getUsuarioAtributoUsuas());

			session.update(usuario);
			objetos.add(usuario);

			Iterator it = filhos.iterator();

			while (it.hasNext()) {
				PaiFilho object = (PaiFilho) it.next();
				object.atribuirPKPai();
				// salva os filhos
				session.save(object);
				objetos.add(object);
			}

			tx.commit();

			if (super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC_ALT_EXC");
				Iterator itObj = objetos.iterator();

				while (itObj.hasNext()) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}

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
		} catch (ECARException e) {
			this.logger.error(e);
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			throw e;
		}
	}

	/**
	 * Exclui registros das tabelas relacionadas ao usu�rio
	 * 
	 * @param usuario
	 * @throws ECARException
	 * 
	 *             TODO: Definir quando excluir em cascata e quando n�o permitir
	 *             exclus�o
	 */

	/**
	 * Exclui um usu�rio, verificando antes se existem registros nas tabelas
	 * relacionadas
	 * 
	 * @param usuario
	 * @throws ECARException
	 */
	public void excluir(UsuarioUsu usuario) throws ECARException {
		try {
			boolean excluir = true;
			if (contar(usuario.getAcompReferenciaArefsByCodUsuIncAref()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.acompReferenciaArefsByCodUsuIncAref");
			}
			if (contar(usuario.getAcompReferenciaArefsByCodUsuUltManutAref()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.acompReferenciaArefsByCodUsuUltManutAref");
			}
			if (contar(usuario.getAcompRelatorioArelsByUsuarioUsu()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.acompRelatorioArels");
			}
			if (contar(usuario.getAcompRelatorioArelsByUsuarioUsuUltimaManutencao()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.acompRelatorioArels");
			}
			if (contar(usuario.getAgendaAges()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.agendaAges");
			}
			if (contar(usuario.getApontamentoApts()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.apontamentoApts");
			}
			if (contar(usuario.getEfItemEstRealizadoEfiers()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.efItemEstRealizadoEfiers");
			}
			if (contar(usuario.getEstruturaEtts()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.estruturaEtts");
			}
			if (contar(usuario.getItemEstrutMarcadorIettms()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.itemEstrutMarcadorIettms");
			}
			if (contar(usuario.getItemEstrutUploadIettups()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.itemEstrutUploadIettups");
			}
			if (contar(usuario.getItemEstruturaIettsByCodUsuIncIett()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.itemEstruturaIettsByCodUsuIncIett");
			}
			if (contar(usuario.getItemEstruturaIettsByCodUsuUltManutIett()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.itemEstruturaIettsByCodUsuUltManutIett");
			}
			if (contar(usuario.getItemEstrutUsuarioIettuses()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.itemEstrutUsuarioIettuses");
			}
			if (contar(usuario.getItemEstUsutpfuacIettutfas()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.itemEstUsutpfuacIettutfas");
			}
			if (contar(usuario.getLogs()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.logs");
			}
			if (contar(usuario.getRegControleAcessoRcas()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.regControleAcessoRcas");
			}
			if (contar(usuario.getSegmentoCategoriaSgtcs()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.segmentoCategoriaSgtcs");
			}
			if (contar(usuario.getSegmentoItemSgtis()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.segmentoItemSgtis");
			}
			if (contar(usuario.getSegmentoSgts()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.segmentoSgts");
			}
			if (contar(usuario.getHistoricoIettusHs()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.historicoIettusHs");
			}

			// Retirado do mapeamento
			// if (contar(usuario.getIettutfaHistorIettutfahsByCodUsu()) > 0) {
			// excluir = false;
			// throw new
			// ECARException("usuario.exclusao.erro.iettutfaHistorIettutfahsByCodUsu");
			// }
			// if
			// (contar(usuario.getIettutfaHistorIettutfahsByCodUsuHistoricoIettutfah())
			// > 0) {
			// excluir = false;
			// throw new
			// ECARException("usuario.exclusao.erro.iettutfaHistorIettutfahsByCodUsuHistoricoIettutfah");
			// }
			if (contar(usuario.getRegDemandaRegdsByCodUsuInclusaoRegd()) > 0) {
				excluir = false;
				throw new ECARException("usuario.exclusao.erro.regDemandaRegdsByCodUsuInclusaoRegd");
			}

			if (contar(usuario.getPerfilIntercambioDadosPflids()) > 0) {
				excluir = false;
				PerfilIntercambioDadosPflid plfid = (PerfilIntercambioDadosPflid) new ArrayList(usuario.getPerfilIntercambioDadosPflids()).get(0);
				throw new ECARException("usuario.exclusao.erro.perfilIntercambioDadosPflids", null, new String[] { plfid.getNomePflid() });
			}

			if (excluir) {

				List objs = new ArrayList();
				if (usuario.getUsuarioAtributoUsuas() != null) {
					Iterator itAtb = usuario.getUsuarioAtributoUsuas().iterator();
					while (itAtb.hasNext()) {
						UsuarioAtributoUsua usuAtrib = (UsuarioAtributoUsua) itAtb.next();
						objs.add(usuAtrib);
					}
				}
				usuario.setUsuarioAtributoUsuas(null);

				objs.add(usuario);
				super.excluir(objs);
			}
		} catch (ECARException e) {
			this.logger.error(e);
			throw e;
		}
	}

	/**
	 * Retorna uma Lista (List) de objetos do tipo UsuarioAtributoUsua de um
	 * determinado usuario
	 * 
	 * @param usuario
	 *            UsuarioUsu
	 * @return List dos UsuarioAtributoUsua de um usu�rio
	 * @throws ECARException
	 */

	public List getAtributosUsuario(UsuarioUsu usuario) throws ECARException {
		List retorno = new ArrayList();
		Set result = usuario.getUsuarioAtributoUsuas();
		if (result != null) {
			if (result.size() > 0) {
				Iterator it = result.iterator();
				while (it.hasNext()) {
					retorno.add((UsuarioAtributoUsua) it.next());
				}

			}
		}
		return retorno;
	}

	/**
	 * Retorna uma Lista (List) de objetos do tipo UsuarioAtributoUsua de um
	 * determinado usuario pertencentes a um determinado SisGrupoAtributoSga
	 * 
	 * @param usuario
	 *            UsuarioUsu
	 * @param grupo
	 *            SisGrupoAtibutoSga
	 * @return List dos UsuarioAtributoUsua de um usu�rio
	 * @throws ECARException
	 */

	public List getAtributosUsuarioByGrupo(UsuarioUsu usuario, SisGrupoAtributoSga grupo) throws ECARException {
		List retorno = new ArrayList();
		Set result = usuario.getUsuarioAtributoUsuas();
		if (result != null) {
			if (result.size() > 0) {
				Iterator it = result.iterator();
				while (it.hasNext()) {
					UsuarioAtributoUsua usuarioAtributo = (UsuarioAtributoUsua) it.next();
					if (usuarioAtributo.getSisAtributoSatb().getSisGrupoAtributoSga().equals(grupo))
						retorno.add(usuarioAtributo);
				}
			}
		}
		return retorno;
	}

	/**
	 * Retorna uma Lista (List) de objetos do tipo Usuario de um determinado
	 * SisAtributoSatb
	 * 
	 * @param sisAtributoSatb
	 *            SisAtibrutoSatb
	 * @return List dos Usuarios de um SisAtributoSatb
	 * @throws ECARException
	 */

	public List getUsuariosBySisAtributoSatb(SisAtributoSatb sisAtributoSatb) throws ECARException {
		List retorno = new ArrayList();

		Set result = sisAtributoSatb.getUsuarioAtributoUsuas();
		if (result != null) {
			Iterator it = result.iterator();
			while (it.hasNext()) {
				UsuarioAtributoUsua usuarioAtributo = (UsuarioAtributoUsua) it.next();
				retorno.add(usuarioAtributo.getUsuarioUsu());

			}
		}
		return retorno;
	}

	/**
	 * Retorna uma Lista com os IDs Atributos do Sistema que definem a classe de
	 * acesso do usu�rio
	 * 
	 * @param usuario
	 * @return
	 * @throws ECARException
	 */
	public List getClassesAcessoUsuarioById(UsuarioUsu usuario) throws ECARException {

		List retorno = new ArrayList();

		Set classesAcesso = getClassesAcessoUsuario(usuario);

		if (classesAcesso != null) {
			Iterator it = classesAcesso.iterator();
			while (it.hasNext()) {
				retorno.add(((SisAtributoSatb) it.next()).getCodSatb());
			}
		}

		return retorno;

	}

	/**
	 * Retorna uma Lista com os Atributos do Sistema que definem a classe de
	 * acesso do usu�rio
	 * 
	 * @param usuario
	 * @return
	 * @throws ECARException
	 */

	public Set getClassesAcessoUsuario(UsuarioUsu usuario) throws ECARException {
		Set lClasses = new HashSet();
		if (usuario.getUsuarioAtributoUsuas() != null) {
			SisGrupoAtributoSga grupoAtributosClasseAcesso = new ConfiguracaoDao(request).getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrClAcesso();

			Set lAtributosClasseAcesso = grupoAtributosClasseAcesso.getSisAtributoSatbs();
			Iterator itAtributos = usuario.getUsuarioAtributoUsuas().iterator();
			while (itAtributos.hasNext())
				lClasses.add(((UsuarioAtributoUsua) itAtributos.next()).getSisAtributoSatb());
			if (usuario.getUsuarioAtributoUsuas() != null) {
				Iterator it = lClasses.iterator();
				while (it.hasNext()) {
					SisAtributoSatb atributo = (SisAtributoSatb) it.next();
					if (!lAtributosClasseAcesso.contains(atributo))
						it.remove();
				}
			}
		}

		return lClasses;
	}

	/**
	 * Devolve um usuario cadastrado em UsuarioUsu a partir de seu ID de usu�rio
	 * no sentinela. Retorna um usuario vazio caso nao exista
	 * 
	 * @param idDominioUsu
	 *            String ID do usu�rio no sentinela
	 * @return
	 * @throws ECARException
	 */
	public UsuarioUsu getUsuarioByIdDominio(String idDominioUsu) throws ECARException {
		UsuarioUsu usuario;

		try {
			Query query = this.session.createQuery("select u from UsuarioUsu u where u.idDominioUsu = :idDominioUsu");

			query.setString("idDominioUsu", idDominioUsu);

			List lista = query.list();

			if (lista.size() > 0) {
				usuario = (UsuarioUsu) lista.get(0);

				// BUG 4608 - Comentada a linha abaixo porque estava fazendo
				// querys desnecess�rias
				// usuario.getItemEstrutUsuarioIettuses().size();

				// recuperar os dados do usu�rio no sentinela
				// this.setUsuarioUsuDadosSentinela(usuario);

			} else
				usuario = new UsuarioUsu();
		} catch (HibernateException e) {
			this.logger.error(e, e.getCause());
			throw new ECARException("erro.hibernateException");
		}

		return usuario;
	}

	/**
	 * Devolve uma lista com todos os idDominioUsu cadastrados no ECAR
	 * 
	 * @return
	 * @throws ECARException
	 */
	public List getListaIdDominio() throws ECARException {
		List lista;
		try {
			lista = this.session.createQuery("select u.idDominioUsu from UsuarioUsu u").list();
			return lista;
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Invoca o m�todo pesquisar do Dao e itera sobre o resultado para
	 * inicializar as Collections.<br>
	 * 
	 * @param usuario
	 * @param nome
	 * @param login
	 * @param cpf
	 * @param eMail
	 * @return List
	 * @throws ECARException
	 */
	public List pesquisar(UsuarioUsu usuario, String nome, String login, String cpf, String eMail) throws ECARException {

		// classes de acesso marcadas na tela como filtro
		List<UsuarioAtributoUsua> listClassesAcessoFiltro = new ArrayList<UsuarioAtributoUsua>();
		if (usuario.getUsuarioAtributoUsuas() != null && usuario.getUsuarioAtributoUsuas().size() > 0) {

			for (Iterator itAtr = usuario.getUsuarioAtributoUsuas().iterator(); itAtr.hasNext();) {
				UsuarioAtributoUsua usuAtr = (UsuarioAtributoUsua) itAtr.next();
				listClassesAcessoFiltro.add(usuAtr);
			}
		}

		List listaOrgaos = null;
		if (usuario.getOrgaoOrgs() != null) {
			listaOrgaos = new ArrayList(usuario.getOrgaoOrgs());
			usuario.setOrgaoOrgs(null);
		}

		List pesquisa = super.pesquisar(usuario, new String[] { "codUsu", "asc" });

		for (Iterator it = pesquisa.iterator(); it.hasNext();) {
			UsuarioUsu usu = (UsuarioUsu) it.next();

			// filtrando por orgao
			boolean pesquisaOrgao = false;
			boolean achouOrgao = false;

			// se tiver alguma coisa na lista de orgaos
			if (listaOrgaos != null && !listaOrgaos.isEmpty()) {
				pesquisaOrgao = true;
				// se o usuario possui uma lista de orgaos
				if (usu.getOrgaoOrgs() != null && !usu.getOrgaoOrgs().isEmpty()) {

					for (Iterator itOrgaos = usu.getOrgaoOrgs().iterator(); itOrgaos.hasNext();) {

						OrgaoOrg aux = (OrgaoOrg) itOrgaos.next();
						// se o usuario possui um orgao
						if (listaOrgaos.contains(aux)) {
							achouOrgao = true;
							continue;
						}
					}
				}
			}

			// se pesquisou e n�o achou
			if (pesquisaOrgao && !achouOrgao) {
				it.remove();
				continue;
			}

			// verifica��es se foi marcado classes de acesso como filtro da
			// pesquisa
			boolean ignorar = false;
			List<UsuarioAtributoUsua> listVerificacoes = new ArrayList<UsuarioAtributoUsua>();

			if (listClassesAcessoFiltro.size() > 0) {
				if (usu.getUsuarioAtributoUsuas() != null && usu.getUsuarioAtributoUsuas().size() > 0) {

					for (Iterator itAtr = usu.getUsuarioAtributoUsuas().iterator(); itAtr.hasNext();) {
						UsuarioAtributoUsua usuAtr = (UsuarioAtributoUsua) itAtr.next();
						listVerificacoes.add(usuAtr);
					}

					List<String> listIgnorar = new ArrayList<String>();
					String ignorarItem = "";
					for (Iterator itClassesTela = listClassesAcessoFiltro.iterator(); itClassesTela.hasNext();) {
						UsuarioAtributoUsua atributosTela = (UsuarioAtributoUsua) itClassesTela.next();

						ignorarItem = "SIM";

						for (Iterator itVerificacoes = listVerificacoes.iterator(); itVerificacoes.hasNext();) {
							UsuarioAtributoUsua atributosUsuario = (UsuarioAtributoUsua) itVerificacoes.next();

							SisGrupoAtributoSga tipo = (SisGrupoAtributoSga) atributosTela.getSisAtributoSatb().getSisGrupoAtributoSga();
							if (SisTipoExibicGrupoDao.TEXT.equals(tipo.getSisTipoExibicGrupoSteg().getCodSteg().toString())) {
								if (atributosUsuario.getInformacao() != null && atributosUsuario.getInformacao().length() > 0) {
									if (atributosUsuario.getInformacao().indexOf(atributosTela.getInformacao()) > -1) {
										ignorarItem = "NAO";
										break;
									}
								}
							} else {
								if (atributosUsuario.getSisAtributoSatb().getCodSatb().floatValue() == atributosTela.getSisAtributoSatb().getCodSatb().floatValue()) {
									ignorarItem = "NAO";
									break;
								}
							}
						}

						listIgnorar.add(ignorarItem);
					}

					if (listIgnorar.contains("SIM")) {
						ignorar = true;
					} else {
						ignorar = false;
					}
				} else {
					ignorar = true;
				}
			}
			if (!ignorar) {
				boolean retirarCollection = false;
				if (nome != null && !"".equals(nome.trim())) {
					if (usu.getNomeUsuSent().toUpperCase().lastIndexOf(nome.toUpperCase()) == -1)
						retirarCollection = true;
				}
				if (login != null && !"".equals(login.trim())) {
					if (usu.getIdUsuarioUsu().toUpperCase().lastIndexOf(login.toUpperCase()) == -1)
						retirarCollection = true;
				}
				if (cpf != null && !"".equals(cpf.trim())) {
					if (!usu.getCnpjCpfUsu().equals(cpf))
						retirarCollection = true;
				}
				if (eMail != null && !"".equals(eMail.trim())) {
					if (!usu.getEmail1UsuSent().toUpperCase().contains(eMail.toUpperCase()))
						retirarCollection = true;
				}

				if (!retirarCollection) {
					usu.getUsuarioAtributoUsuas().size();
				} else {
					it.remove();
				}
			} else {
				it.remove();
			}
		}

		Collections.sort(pesquisa, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((UsuarioUsu) o1).getNomeUsuSent().compareToIgnoreCase(((UsuarioUsu) o2).getNomeUsuSent());
			}
		});

		return pesquisa;
	}

	/**
	 * lista os niveis de planejamento do usuario
	 * 
	 * @param usuario
	 * @return List
	 * @throws ECARException
	 */
	public List getNiveisPlanejamentoUsuario(UsuarioUsu usuario) throws ECARException {
		try {

			SisGrupoAtributoSga grupoNivelPlanejamento = new ConfiguracaoDao(request).getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan();

			String str = "select usuarioAtributo from UsuarioAtributoUsua " + "usuarioAtributo where usuarioAtributo.sisAtributoSatb.sisGrupoAtributoSga.codSga = ? ";

			boolean ignorarPermissoes = this.validaPermissao.getIgnorarPermissoes();
			if (!ignorarPermissoes) {
				str += "and usuarioAtributo.usuarioUsu.codUsu = ?";
			}

			Query q = this.getSession().createQuery(str);

			q.setLong(0, grupoNivelPlanejamento.getCodSga().longValue());

			if (!ignorarPermissoes) {
				q.setLong(1, usuario.getCodUsu().longValue());
			}

			return q.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}
	}

	/**
	 * Pesquisa a P�gina Inicial configurada para o Usu�rio que est� se logando
	 * no sistema. Obs.: Recebe da Configura��o Geral (ConfiguracaoCfg) o grupo
	 * de Atributo (SisGrupoAtributoSga) que est� cadastro em P�gina Inicial. A
	 * partir do grupo de atributo � filtrado o atributo relacionado a p�gina
	 * inicial.
	 * 
	 * @param usuario
	 * @param paginaInicialSga
	 * @return
	 * @throws ECARException
	 */
	public SisAtributoSatb getPaginaInicialByUsuarioAndCodSgaPgIni(UsuarioUsu usuario, SisGrupoAtributoSga paginaInicialSga) throws ECARException {
		try {
			SisAtributoSatb sisAtributo = new SisAtributoSatb();
			sisAtributo.setSisGrupoAtributoSga(paginaInicialSga);

			List lista = this.pesquisar(sisAtributo, new String[] { "descricaoSatb", "asc" });
			List listaCodSga = new ArrayList();
			Iterator it = lista.iterator();
			while (it.hasNext()) {
				SisAtributoSatb satb = (SisAtributoSatb) it.next();
				listaCodSga.add(satb.getCodSatb());
			}

			StringBuilder select = new StringBuilder("select usuarioAtributo from UsuarioAtributoUsua usuarioAtributo").append(
					" where usuarioAtributo.sisAtributoSatb.sisGrupoAtributoSga.codSga in (:listaSga)").append(" and usuarioAtributo.usuarioUsu.codUsu = :usuario");

			Query q = this.getSession().createQuery(select.toString());
			q.setParameterList("listaSga", listaCodSga);
			q.setLong("usuario", usuario.getCodUsu().longValue());

			List listaPgIni = q.list();

			if (listaPgIni != null && listaPgIni.size() > 0)
				return ((UsuarioAtributoUsua) listaPgIni.iterator().next()).getSisAtributoSatb();

			return null;
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Retorna um objeto usu�rio com dados de um usu�rio do Sentinela a partir
	 * de seu codigo
	 * 
	 * @param codUsuarioSentinela
	 * @return
	 */
	/*
	 * public UsuarioUsu getUsuarioSentinela(long codUsuarioSentinela){
	 * SentinelaInterface s = SentinelaUtil.getSentinelaInterface();
	 * SentinelaParam[] usuarios = s.getUsuariosSistema(); List retorno = new
	 * ArrayList();
	 * 
	 * for(int i = 0; i < usuarios.length; i++){ if(usuarios[i].getCodigo() ==
	 * codUsuarioSentinela){ UsuarioUsu u = new UsuarioUsu();
	 * u.setIdDominioUsu(Long.valueOf(usuarios[i].getCodigo()).toString());
	 * u.setIdUsuarioUsu(usuarios[i].getParamAux()[0]);
	 * u.setCnpjCpfUsu(usuarios[i].getParamAux()[1]);
	 * u.setEmail1Usu(usuarios[i].getParamAux()[2]);
	 * u.setNomeUsu(usuarios[i].getNome()); return u; } } return null; }
	 */

	public String getNomeUsuarioSentinela(long codUsuarioSentinela) {
		SentinelaInterface s = SentinelaUtil.getSentinelaInterface();

		if (s == null && request != null) {
			s = SentinelaUtil.getSentinelaInterface();
			logger.error("Erro ao tentar acessar a interface do sentinela, sera feita nova tentativa");
		}

		if (s != null) {
			SentinelaParam usuario = s.getUsuarioById(codUsuarioSentinela);
			if (usuario != null) {
				return usuario.getNome();
			} else {
				logger.error("Erro ao tentar consultar o nome do usuario via Sentinela");
			}
		} else {

			String str = "select nomeUsu from UsuarioUsu " + "where idDominioUsu = ? ";

			Query q = this.getSession().createQuery(str);

			q.setLong(0, codUsuarioSentinela);

			if (q.list() != null) {
				return q.list().get(0).toString();
			} else {
				logger.error("Erro ao tentar obter a interface do Sentinela");
				return INFORMACAO_NAO_LOCALIZADA_SENTINELA;
			}

		}

		return INFORMACAO_NAO_LOCALIZADA_SENTINELA;
	}

	/**
	 * pega o login do usuario no sentinela
	 * 
	 * @param codUsuarioSentinela
	 * @return String
	 */
	public String getLoginUsuarioSentinela(long codUsuarioSentinela) {
		SentinelaInterface s = SentinelaUtil.getSentinelaInterface();

		if (s != null) {
			SentinelaParam usuario = s.getUsuarioById(codUsuarioSentinela);
			if (usuario != null) {
				return usuario.getParamAux()[0];
			}
			/*
			 * SentinelaParam[] usuarios = s.getUsuariosSistema();
			 * 
			 * if (usuarios != null) { for (int i = 0; i < usuarios.length; i++)
			 * { if (usuarios[i].getCodigo() == codUsuarioSentinela) { return
			 * usuarios[i].getParamAux()[0]; } } }
			 */
		}

		return INFORMACAO_NAO_LOCALIZADA_SENTINELA;
	}

	/**
	 * Pega o CPF usuario no sentinela
	 * 
	 * @param codUsuarioSentinela
	 * @return String
	 */
	public String getCpfUsuarioSentinela(long codUsuarioSentinela) {
		SentinelaInterface s = SentinelaUtil.getSentinelaInterface();

		if (s != null) {
			SentinelaParam usuario = s.getUsuarioById(codUsuarioSentinela);
			if (usuario != null) {
				return usuario.getParamAux()[1];
			}
			/*
			 * SentinelaParam[] usuarios = s.getUsuariosSistema();
			 * 
			 * if (usuarios != null) { for (int i = 0; i < usuarios.length; i++)
			 * { if (usuarios[i].getCodigo() == codUsuarioSentinela) { return
			 * usuarios[i].getParamAux()[1]; } } }
			 */
		}

		return INFORMACAO_NAO_LOCALIZADA_SENTINELA;
	}

	/**
	 * Pega o e-mail do usuario no sentinela
	 * 
	 * @param codUsuarioSentinela
	 * @return String
	 */
	public String getEmailUsuarioSentinela(long codUsuarioSentinela) {
		SentinelaInterface s = SentinelaUtil.getSentinelaInterface();

		if (s != null) {
			SentinelaParam usuario = s.getUsuarioById(codUsuarioSentinela);
			if (usuario != null) {
				return usuario.getParamAux()[2];
			}
			/*
			 * SentinelaParam[] usuarios = s.getUsuariosSistema();
			 * 
			 * if (usuarios != null) { for (int i = 0; i < usuarios.length; i++)
			 * { if (usuarios[i].getCodigo() == codUsuarioSentinela) { return
			 * usuarios[i].getParamAux()[2]; } } }
			 */
		}

		return EMAIL_NAO_LOCALIZADO_SENTINELA;
	}

	/**
	 * Retorna uma lista de objetos usu�rio com dados de usu�rios do Sentinela,
	 * a partir dos par�metros informados
	 * 
	 * @param nome
	 *            String a ser usada na pesquisa
	 * @param tipoComp
	 *            Se "N" pesquisa nome em Nome, se for "L" pesquisa nome em
	 *            Login
	 * @return List
	 * @throws ECARException
	 */
	public List pesquisarUsuarioSentinela(String nome, String tipoComp) throws ECARException {
		SentinelaInterface s = SentinelaUtil.getSentinelaInterface();

		List retorno = new ArrayList();

		if (s != null) {
			SentinelaParam[] usuarios = s.getGeralUsuarios();

			List ids = getListaIdDominio();

			if (usuarios != null) {
				for (int i = 0; i < usuarios.length; i++) {
					UsuarioUsu u = new UsuarioUsu();

					String[] param = usuarios[i].getParamAux();

					if (!ids.contains(Long.valueOf(usuarios[i].getCodigo()).toString())) {
						u.setCodUsu(Long.valueOf(usuarios[i].getCodigo()));
						u.setNomeUsu(usuarios[i].getNome());

						if (param != null) {
							u.setIdUsuarioUsu(param[0]);
							u.setCnpjCpfUsu(param[1]);
							u.setEmail1Usu(param[2]);
						}

						if ("N".equals(tipoComp)) {
							if (u.getNomeUsuSent() != null && u.getNomeUsuSent().toLowerCase().lastIndexOf(nome.toLowerCase()) >= 0)
								retorno.add(u);
						}
						if ("L".equals(tipoComp)) {
							if (u.getIdUsuarioUsu() != null && u.getIdUsuarioUsu().toLowerCase().lastIndexOf(nome.toLowerCase()) >= 0)
								retorno.add(u);
						}

					}
				}
			}
		}
		return retorno;
	}

	/**
	 * Atribuir ao objeto UsuarioUsu os seus respectivos dados do sentinela:
	 * CPF, Login, E-mail, Nome
	 * 
	 * @param UsuarioUsu
	 * @return
	 * @throws ECARException
	 */
	/*
	 * public void setUsuarioUsuDadosSentinela(UsuarioUsu usu) throws
	 * ECARException { // recuperar os dados do usu�rio no sentinela UsuarioUsu
	 * usuarioSentinela =
	 * this.getUsuarioSentinela(Long.parseLong(usu.getIdDominioUsu()));
	 * 
	 * usu.setCnpjCpfUsu(usuarioSentinela.getCnpjCpfUsu());
	 * usu.setIdUsuarioUsu(usuarioSentinela.getIdUsuarioUsu());
	 * usu.setEmail1Usu(usuarioSentinela.getEmail1UsuSent());
	 * usu.setNomeUsu(usuarioSentinela.getNomeUsu()); }
	 */

	/**
	 * Busca um objeto do banco de dados pela chave
	 * 
	 * @param html
	 * @param status
	 * @return Object - um objeto reencarnado do banco de dados da classe
	 *         informada contendo os dados do sentinela
	 * @throws ECARException
	 *             - NAO tem rollback
	 * @throws IOException
	 */
	/*
	 * public Object buscar(Class cl, Serializable chave) throws ECARException {
	 * Object obj = super.buscar(cl, chave);
	 * 
	 * //this.setUsuarioUsuDadosSentinela((UsuarioUsu)obj);
	 * 
	 * return obj; }
	 */

	public String geraListaTelefonica(StringBuffer html, String status) throws ECARException, IOException {
		ConfiguracaoDao configDao = new ConfiguracaoDao(request);
		ConfiguracaoCfg config = configDao.getConfiguracao();

		String path = config.getRaizUpload() + "/listaTelefonica.txt";
		String separador = config.getSeparadorArqTXT();
		String separadorMultivalor = config.getSeparadorCampoMultivalor();

		FileWriter csvFile = new FileWriter(path);
		// BufferedWriter outStr = new BufferedWriter(new FileWriter(path));
		// ExcelCSVPrinter csvPrinter = new ExcelCSVPrinter(outStr);

		UsuarioDao usuDao = new UsuarioDao(request);
		List usuarios = new ArrayList();

		if ("ativo".equals(status)) {

			status = "S";
			usuarios = selecionaPorIndAtivo(status);

		} else if ("inativo".equals(status)) {

			status = "N";
			usuarios = selecionaPorIndAtivo(status);

		} else if ("todos".equals(status)) {

			usuarios = usuDao.pesquisar(new UsuarioUsu(), "", "", "", "");
		}

		int qtdeUsu = usuarios.size();
		Iterator itUsu = usuarios.iterator();

		/*
		 * Cria��o da matriz que abrigar� os valores A matriz conta com 7
		 * colunas (nome, orgao, e-mail, fone, login e tipoAcesso, situa��o
		 * cadst) e qtdeUsuarios * cm. (Usando a "Aproxima��o de Steinmacher",
		 * em que cm � uma constante que, para casos de usuarios relacionados a
		 * orgaos e grupos de acesso, tem valor "12"
		 */
		int linhas = qtdeUsu * 12;
		if (linhas < 1) {
			html.append("<table align=\"center\">");
			html.append("\n\t<tr class=\"linha_subtitulo_estrutura\" bgcolor=\"#EAEEF4\">");

			String checked = "";
			if ("S".equals(status))
				checked = "checked";
			html.append("\n\t<tr>" + "<td></td>" + "<td></td>" + "<td class=\"label\">Situa��o Cadastral:</td>" + "<td align=\"left\" >"
					+ "<input type=\"radio\" class=\"form_check_radio\" name=\"indAtivo\" id=\"indAtivo\" " + checked + " onclick=\"filtraPorIndAtivo(this.value)\" value=\"ativo\">   Ativos"
					+ "</td>" + "</tr>");
			checked = "";

			if ("N".equals(status))
				checked = "checked";
			html.append("<tr>" + "<td></td>" + "<td></td>" + "<td></td>" + "<td align=\"left\" >" + "<input type=\"radio\" class=\"form_check_radio\" name=\"indAtivo\" id=\"indAtivo\" " + checked
					+ " onclick=\"filtraPorIndAtivo(this.value)\" value=\"inativo\">  Inativos" + "</td>" + "</tr>");

			checked = "";
			if ("todos".equals(status))
				checked = "checked";
			html.append("<tr>" + "<td></td>" + "<td></td>" + "<td></td>" + "<td align=\"left\" >" + "<input type=\"radio\" class=\"form_check_radio\" name=\"indAtivo\" id=\"indAtivo\" " + checked
					+ " onclick=\"filtraPorIndAtivo(this.value)\"  value=\"todos\">  Todos" + "</td>" + "</tr>" + "<tr>" + "</tr>");
			html.append("<br>");
			html.append("<tr>" + "<td colspan=\"4\" align=\"center\" >" + "<b>N�o existem usu�rios inativos</b>" + "</td>" + "</tr>" + "<tr>" + "</tr>");
			html.append("</table>");
			return path;
		}

		html.append("<table cellspacing=0>");
		html.append("\n\t<tr class=\"linha_subtitulo_estrutura\" bgcolor=\"#EAEEF4\">");

		String checked = "";
		if ("S".equals(status))
			checked = "checked";
		html.append("\n\t<tr>" + "<td class=\"label\" colspan=\"3\">Situa��o Cadastral:</td>" + "<td align=\"left\" >"
				+ "<input type=\"radio\" class=\"form_check_radio\" name=\"indAtivo\" id=\"indAtivo\" " + checked + " onclick=\"filtraPorIndAtivo(this.value)\" value=\"ativo\">   Ativos" + "</td>"
				+ "</tr>");
		checked = "";

		if ("N".equals(status))
			checked = "checked";
		html.append("<tr>" + "<td></td>" + "<td></td>" + "<td></td>" + "<td align=\"left\" >" + "<input type=\"radio\" class=\"form_check_radio\" name=\"indAtivo\" id=\"indAtivo\" " + checked
				+ " onclick=\"filtraPorIndAtivo(this.value)\" value=\"inativo\">  Inativos" + "</td>" + "</tr>");

		checked = "";
		if ("todos".equals(status))
			checked = "checked";
		html.append("<tr>" + "<td></td>" + "<td></td>" + "<td></td>" + "<td align=\"left\" >" + "<input type=\"radio\" class=\"form_check_radio\" name=\"indAtivo\" id=\"indAtivo\" " + checked
				+ " onclick=\"filtraPorIndAtivo(this.value)\"  value=\"todos\">  Todos" + "</td>" + "</tr>" + "<tr>" + "</tr>");

		String[][] arquivo = new String[linhas][7];
		int linha = 2, coluna = 0;

		/*
		 * Formato atual da coluna: Nome | Orgao(s) | e-mail | DDD - Fone |
		 * login | tipoAcesso | situa��oCadastral
		 */

		// Cabe�alho da lista
		arquivo[0][0] = "Nome";
		arquivo[0][1] = "Org�o(s)";
		arquivo[0][2] = "e-mail";
		arquivo[0][3] = "Fone";
		arquivo[0][4] = "Login";
		arquivo[0][5] = "Grupos de Acesso";
		arquivo[0][6] = "Situa��o Cadastral";

		while (itUsu.hasNext()) {
			UsuarioUsu usu = (UsuarioUsu) itUsu.next();

			// Coluna do nome do Usuario
			arquivo[linha][coluna] = Pagina.trocaNull(usu.getNomeUsuSent());
			coluna++;

			// Coluna dos �rg�os
			int contOrg = 0;
			if ((usu.getOrgaoOrgs() != null) && (usu.getOrgaoOrgs().size() > 0)) {
				Iterator itOrg = usu.getOrgaoOrgs().iterator();
				while (itOrg.hasNext()) {
					OrgaoOrg orgao = (OrgaoOrg) itOrg.next();
					arquivo[linha + (contOrg++)][coluna] = Pagina.trocaNull(orgao.getSiglaOrg().toString());
				}
			}
			coluna++;

			// Coluna do e-mail
			arquivo[linha][coluna] = Pagina.trocaNull(usu.getEmail1UsuSent());
			coluna++;

			// Coluna do Telefone
			arquivo[linha][coluna] = "(" + Pagina.trocaNull(usu.getComercDddUsu()) + ") " + Pagina.trocaNull(usu.getComercTelefoneUsu());
			coluna++;

			// Coluna do login
			arquivo[linha][coluna] = Pagina.trocaNull(usu.getIdUsuarioUsu());
			coluna++;

			// Coluna dos grupos de acesso
			Set atribUsu = usu.getUsuarioAtributoUsuas();
			Iterator itAtrib = atribUsu.iterator();
			int contAcesso = 0;
			while (itAtrib.hasNext()) {
				UsuarioAtributoUsua sisatr = (UsuarioAtributoUsua) itAtrib.next();
				if (sisatr.getSisAtributoSatb().getSisGrupoAtributoSga().getCodSga().equals(Long.valueOf(4))) {
					arquivo[linha + (contAcesso++)][coluna] = Pagina.trocaNull(sisatr.getSisAtributoSatb().getDescricaoSatb());
				}
			}
			coluna++;

			// Coluna da Situa��o Cadastral
			arquivo[linha][coluna] = Pagina.trocaNull(usu.getIndAtivoUsu());
			if ("S".equals(arquivo[linha][coluna]))
				arquivo[linha][coluna] = "Ativo";
			else
				arquivo[linha][coluna] = "Inativo";

			coluna++;

			linha = linha + (Math.max(contAcesso, contOrg));
			coluna = 0;
		}
		int j = 0;

		for (j = 0; j < 7; j++) {
			String width = "";
			if (j == 3) { // Fone
				width = "width=\"100px\" nowrap";
			}
			html.append("\n\t\t<td ").append(width).append(" >");
			html.append("\n\t\t\t<b>").append(arquivo[0][j]).append("</b>");
			html.append("\n\t</td>");
		}
		html.append("\n\t</tr>");

		String cor = "";
		int auxCor = 0;
		for (int i = 1; i < linha; i++) {
			auxCor++;

			html.append("\n\t<tr class=\"linha_subtitulo2_estrutura\" >");
			for (j = 0; j < 7; j++) {
				String width = "";
				if (j == 3) { // Fone
					width = "width=\"100px\" nowrap";
				}
				cor = (auxCor % 2 == 0) ? "#F6F7FB\" " : "#FFFFFF\" ";

				html.append("\n\t\t<td ").append(width).append(" bgcolor=\"").append(cor);
				html.append(">\n\t\t\t").append(Pagina.trocaNull(arquivo[i][j]));
				html.append("\n\t\t</td>");

			}

			if (i + 1 < linha && "".equals(arquivo[i + 1][0]) || arquivo[i + 1][0] == null && "".equals(arquivo[i + 1][1]) || arquivo[i + 1][1] == null && "".equals(arquivo[i + 1][2])
					|| arquivo[i + 1][2] == null && "".equals(arquivo[i + 1][3]) || arquivo[i + 1][3] == null && "".equals(arquivo[i + 1][4]) || arquivo[i + 1][4] == null) {
				html.append("\n\t<tr class=\"linha_subtitulo2_estrutura\" >");
				html.append("\n\t\t<td ").append(" bgcolor=\"").append(cor).append(">\n\t\t</td>");
				html.append("\n\t\t<td ").append(" bgcolor=\"").append(cor).append(">\n\t\t</td>");
				html.append("\n\t\t<td ").append(" bgcolor=\"").append(cor).append(">\n\t\t</td>");
				html.append("\n\t\t<td ").append(" bgcolor=\"").append(cor).append(">\n\t\t</td>");
				html.append("\n\t\t<td ").append(" bgcolor=\"").append(cor).append(">\n\t\t</td>");

				html.append("\n\t\t<td ").append(" bgcolor=\"").append(cor);
				html.append(">\n\t\t\t ").append(Pagina.trocaNull(arquivo[i + 1][5]));
				html.append("\n\t\t</td>");

				html.append("\n\t\t<td ").append(" bgcolor=\"").append(cor).append(">\n\t\t</td>");
				html.append("\n\t</tr>");
				i++;
			} else
				html.append("\n\t</tr>");
		}
		html.append("</table>");

		this.generateFile(path, arquivo, separador);

		// csvPrinter.writeln(arquivo);
		// outStr.close();
		return path;
	}

	/**
	 * Retorna uma lista de usuarios que pode ser apenas os ativos, apenas os
	 * inativos.
	 * 
	 * @param status
	 * @author luanaoliveira
	 * @return
	 * @throws ECARException
	 */
	public List selecionaPorIndAtivo(String status) throws ECARException {

		try {
			Query query = this.session.createQuery("select u from UsuarioUsu u where u.indAtivoUsu = :status");

			query.setString("status", status);

			List lista = query.list();

			return lista;

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}

	}

	/**
	 * Retorna celular se este existir.
	 * 
	 * @author yuri
	 * @since 21/06/2007
	 * @param usuario
	 * @return String
	 * 
	 */
	public String getCelularByUsuario(UsuarioUsu usuario) {
		String retorno = "";

		if (usuario.getUsuarioAtributoUsuas() != null && !usuario.getUsuarioAtributoUsuas().isEmpty()) {
			for (Iterator it = usuario.getUsuarioAtributoUsuas().iterator(); it.hasNext();) {
				UsuarioAtributoUsua usua = (UsuarioAtributoUsua) it.next();
				if (usua.getSisAtributoSatb().getCodSatb().longValue() == 22) { // 22
																				// eh
																				// o
																				// codigo
																				// do
																				// atributo
																				// que
																				// corresponde
																				// ao
																				// celular.
					retorno = usua.getInformacao();
					break;
				}
			}
		}

		return retorno;
	}

	/**
	 * Retorna True se o Indicador de Usuario ativo existir e for S.
	 * 
	 * @param codUsu
	 * @author yuri
	 * @since 26/06/2007
	 * @return boolean
	 * @throws ECARException
	 * 
	 */
	public boolean verificarUsuarioAtivo(Long codUsu) throws ECARException {
		UsuarioUsu usu = (UsuarioUsu) this.buscar(UsuarioUsu.class, codUsu);
		if (usu != null)
			return "S".equals(usu.getIndAtivoUsu()) ? true : false;
		else
			return false;
	}

	/**
	 * Este m�todo analisa quais os grupos que n�o estavam vinculados ao
	 * sentinela antes e ent�o s� vincula estes grupos
	 * 
	 * @param request
	 * @param usuario
	 * @throws ECARException
	 * @throws SentinelaException
	 */
	public void associarAhGruposSentinela(HttpServletRequest request, UsuarioUsu usuario) throws ECARException, SentinelaException {
		long codUsuarioSentinela = 0l;
		int i = 0;
		SentinelaAdmInterface sentinelaAdmin = SentinelaUtil.getSentinelaAdmInterface(request);
		SentinelaInterface sentinela = SentinelaUtil.getSentinelaInterface();

		try {
			if (usuario.getIdDominioUsu() != null && usuario.getIdDominioUsu().length() != 0)
				codUsuarioSentinela = Long.parseLong(usuario.getIdDominioUsu());
		} catch (NumberFormatException numExcep) {
			throw new ECARException();
		}
		Set gruposVinculadosDepois = new HashSet<Long>(1);
		String[] strGruposVinculadosDepois = request.getParameterValues("vinculandos");

		SentinelaParam[] gruposVinculadosSent = null;

		if (codUsuarioSentinela != 0l)
			gruposVinculadosSent = sentinela.getGruposByUsuario(codUsuarioSentinela);

		if (strGruposVinculadosDepois != null && strGruposVinculadosDepois.length != 0) {
			gruposVinculadosDepois = new HashSet<Long>(strGruposVinculadosDepois.length);

			for (i = 0; i < strGruposVinculadosDepois.length; i++) {
				gruposVinculadosDepois.add(new Long(strGruposVinculadosDepois[i]));
			}
		}

		// try {

		for (int j = 0; j < gruposVinculadosSent.length; j++) {
			Long codigo = new Long(gruposVinculadosSent[j].getCodigo());

			if (gruposVinculadosDepois.contains(codigo)) {
				gruposVinculadosDepois.remove(codigo);
			} else {
				sentinelaAdmin.desvincularUsuarioGrupo(codUsuarioSentinela, codigo);
			}
		}

		for (Iterator iterator = gruposVinculadosDepois.iterator(); iterator.hasNext();) {
			Long codigo = (Long) iterator.next();
			sentinelaAdmin.vincularUsuarioGrupo(codUsuarioSentinela, codigo);
		}

		// } catch (SentinelaException e) {
		// throw new
		// ECARException("Usu�rio sem permiss�o aos m�todos de vincula��o de usu�rios aos grupos do Sentinela.");
		// }

	}

	private void generateFile(String sFileName, String[][] conteudo, String separador) throws IOException {
		FileWriter writer = new FileWriter(sFileName);

		for (int i = 0; i < conteudo.length; i++) {
			for (int j = 0; j < conteudo[i].length; j++) {
				if (conteudo[i][j] != null) {
					writer.append(conteudo[i][j]);
				}
				if (j + 1 < conteudo[i].length) {
					writer.append(separador);
				}
			}
			writer.append('\n');
		}

		// generate whatever data you want

		writer.flush();
		writer.close();

	}

	/**
	 * 
	 * @param nome
	 * @return
	 */
	public UsuarioUsu getUsuarioUsuByNome(String nomeUsu) throws ECARException {
		UsuarioUsu usuarioUsu = null;
		try {
			String hql = "select usuarioUsu from UsuarioUsu usuarioUsu where usuarioUsu.nomeUsu = :nomeUsu";
			Query q = this.session.createQuery(hql);
			q.setString("nomeUsu", nomeUsu);
			q.setMaxResults(1);
			usuarioUsu = (UsuarioUsu) q.uniqueResult();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return usuarioUsu;
	}

	public UsuarioUsu getUsuarioUsuByEmail(String emailUser) throws ECARException {
		UsuarioUsu usuarioUsu = null;
		try {
			String hql = "select usuarioUsu from UsuarioUsu usuarioUsu where usuarioUsu.email1Usu = :email";
			Query q = this.session.createQuery(hql);
			q.setString("email", emailUser);
			q.setMaxResults(1);
			usuarioUsu = (UsuarioUsu) q.uniqueResult();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return usuarioUsu;
	}

	public UsuarioUsu verificaSessionWS(UsuarioUsu usuarioUsu) throws ECARException {
		try {
			String hql = "FROM UsuarioUsu u WHERE u = :u";
			Query q = this.session.createQuery(hql);
			q.setParameter("u", usuarioUsu);
			UsuarioUsu u = (UsuarioUsu) q.uniqueResult();
			return u;
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException", e);
		}
	}

	public void criarSessionWS(UsuarioUsu usuarioUsu) throws ECARException {
		SessionWS sessionWS = new SessionWS();
		sessionWS.setDataHoraInicio(new Date());
		sessionWS.setUuid(UUID.randomUUID().toString());
		sessionWS.setUsuarioUsu(usuarioUsu);
		usuarioUsu.setSessionWS(sessionWS);

		this.salvar(sessionWS);
	}

	public UsuarioUsu verificaSessionWS(String uuid) throws ECARException {
		try {
			String hql = "FROM UsuarioUsu usu WHERE usu.sessionWS.uuid = :uuid";
			Query q = this.session.createQuery(hql);
			q.setParameter("uuid", uuid);
			UsuarioUsu usuarioUsu = (UsuarioUsu) q.uniqueResult();
			return usuarioUsu;
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException", e);
		}
	}

	public void atualizaSessionWS(UsuarioUsu usuarioUsu, boolean atualizaUUID) throws ECARException {
		usuarioUsu.getSessionWS().setDataHoraInicio(new Date());

		if (atualizaUUID) {
			usuarioUsu.getSessionWS().setUuid(UUID.randomUUID().toString());
		}

		this.salvar(usuarioUsu.getSessionWS());
	}

	/**
	 * Exclui a SessionWS pelo UsuarioUsu como par�metro.<br>
	 * Primeiro retira do Usuario a sessão, depois exclui a session, na mesma
	 * transação.<br>
	 * 
	 * @param usuarioUsu
	 * @throws ECARException
	 *             - executa o rollback da transa��o
	 */
	public void removeSessao(UsuarioUsu usuarioUsu) throws ECARException {
		inicializarLogBean();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			SessionWS sessionWS = usuarioUsu.getSessionWS();
			usuarioUsu.setSessionWS(null);
			session.saveOrUpdate(usuarioUsu);
			session.delete(sessionWS);
			tx.commit();
			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setObj(usuarioUsu);
				logBean.setOperacao("EXC");
				loggerAuditoria.info(logBean.toString());
			}
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

	public List<Responsavel> buscaResponsavel(String nomeUsu) {
		String hql = "select new ecar.pojo.acompanhamentoEstrategico.Responsavel( " + "u.codUsu, u.nomeUsu) " + "from UsuarioUsu u " + "where upper(u.nomeUsu) like :nomeUsu";

		Query q = this.session.createQuery(hql);
		q.setString("nomeUsu", "%" + nomeUsu.toUpperCase() + "%");

		return q.list();
	}
}