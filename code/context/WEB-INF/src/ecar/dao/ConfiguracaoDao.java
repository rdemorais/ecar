/*
 * Criado em 02/12/2004
 *
 */
package ecar.dao;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import comum.database.Dao;
import comum.util.Pagina;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.ConfigMailCfgm;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.Estilo;
import ecar.pojo.PeriodicidadePrdc;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.TextosSiteTxt;
import ecar.pojo.VisaoDemandasVisDem;

/**
 * @author felipev
 * 
 */
public class ConfiguracaoDao extends Dao {
	/**
	 * Construtor. Chama o Session factory do Hibernate
	 * 
	 * @param request
	 */
	private ConfiguracaoCfg configuracao;

	public ConfiguracaoDao(HttpServletRequest request) {
		super();
		this.request = request;
		try {
			configuracao = (ConfiguracaoCfg) this.listar(ConfiguracaoCfg.class, null).iterator().next();
		} catch (ECARException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ConfiguracaoDao(){
		
	}
	
	

	/**
	 * Atribui os valores dos atributos passados por request a um objeto
	 * Configura��o
	 * 
	 * @param request
	 * @param configuracao
	 * @throws ECARException
	 */
	public void setConfiguracao(HttpServletRequest request,
			ConfiguracaoCfg configuracao) throws ECARException {
		if (!"".equals(Pagina.getParamStr(request, "codCfg"))) {
			configuracao.setCodCfg(Long.valueOf(Pagina.getParamStr(request,
					"codCfg")));
		}

		configuracao.setNomeEstruturaCfg(Pagina.getParamStr(request,
				"nomeEstruturaCfg"));

		if (!"".equals(Pagina.getParamStr(request, "periodicidadePrdc"))) {
			configuracao.setPeriodicidadePrdc((PeriodicidadePrdc) this.buscar(
					PeriodicidadePrdc.class, Long.valueOf(Pagina.getParam(
							request, "periodicidadePrdc"))));
		}

		configuracao.setExibDefaultEstCfg(Pagina.getParamStr(request,
				"exibicaoDefaultEstruturaCfg"));

		configuracao.setIndGerarHistoricoCfg(Pagina.getParamStr(request,
				"indGerarHistoricoCfg"));

		configuracao.setTpArqIntegFinanceiraCfg(Pagina.getParamStr(request,
				"tpArqIntegFinanceiraCfg"));

		if (!"".equals(Pagina.getParamStr(request, "codSgaUnidMedida"))) {
			configuracao
					.setSisGrupoAtributoSgaByUnidMedida((SisGrupoAtributoSga) this
							.buscar(SisGrupoAtributoSga.class, Long
									.valueOf(Pagina.getParam(request,
											"codSgaUnidMedida"))));
		} else {
			configuracao.setSisGrupoAtributoSgaByUnidMedida(null);
		}

		if (!"".equals(Pagina.getParamStr(request, "codSgaTipoEvento"))) {
			configuracao
					.setSisGrupoAtributoSgaByTipoEvento((SisGrupoAtributoSga) this
							.buscar(SisGrupoAtributoSga.class, Long
									.valueOf(Pagina.getParam(request,
											"codSgaTipoEvento"))));
		} else {
			configuracao.setSisGrupoAtributoSgaByTipoEvento(null);
		}

		if (!"".equals(Pagina.getParamStr(request, "codSgaGrAtrClAcesso"))) {
			configuracao
					.setSisGrupoAtributoSgaByCodSgaGrAtrClAcesso((SisGrupoAtributoSga) this
							.buscar(SisGrupoAtributoSga.class, Long
									.valueOf(Pagina.getParam(request,
											"codSgaGrAtrClAcesso"))));
		}

		if (!"".equals(Pagina.getParamStr(request, "codSgaGrAtrPgIni"))) {
			configuracao
					.setSisGrupoAtributoSgaByCodSgaGrAtrPgIni((SisGrupoAtributoSga) this
							.buscar(SisGrupoAtributoSga.class, Long
									.valueOf(Pagina.getParam(request,
											"codSgaGrAtrPgIni"))));
		}

		if (!"".equals(Pagina.getParamStr(request, "codSapadrao"))) {
			configuracao.setSisAtributoSatbByCodSapadrao((SisAtributoSatb) this
					.buscar(SisAtributoSatb.class, Long.valueOf(Pagina
							.getParam(request, "codSapadrao"))));
		}

		if (!"".equals(Pagina.getParamStr(request, "codSgaGrAtrLeiCapa"))) {
			configuracao
					.setSisGrupoAtributoSgaByCodSgaGrAtrLeiCapa((SisGrupoAtributoSga) this
							.buscar(SisGrupoAtributoSga.class, Long
									.valueOf(Pagina.getParam(request,
											"codSgaGrAtrLeiCapa"))));
		}

		if (!"".equals(Pagina.getParamStr(request, "codSgaPontoCritico"))) {
			configuracao
					.setSisGrupoAtributoSgaTipoPontoCritico((SisGrupoAtributoSga) this
							.buscar(SisGrupoAtributoSga.class, Long
									.valueOf(Pagina.getParam(request,
											"codSgaPontoCritico"))));
		} else {
			configuracao.setSisGrupoAtributoSgaTipoPontoCritico(null);
		}

		if (!"".equals(Pagina.getParamStr(request, "codSacapa"))) {
			configuracao.setSisAtributoSatbByCodSacapa((SisAtributoSatb) this
					.buscar(SisAtributoSatb.class, Long.valueOf(Pagina
							.getParam(request, "codSacapa"))));
		}

		if (!"".equals(Pagina.getParamStr(request, "atrTpAcesso"))) {
			configuracao
					.setSisGrupoAtributoSgaByCodSgaGrAtrTpAcesso((SisGrupoAtributoSga) this
							.buscar(SisGrupoAtributoSga.class, Long
									.valueOf(Pagina.getParam(request,
											"atrTpAcesso"))));
		}

		if (!"".equals(Pagina.getParamStr(request, "codSaUsu"))) {
			configuracao.setSisAtributoSatbByCodSaAcesso((SisAtributoSatb) this
					.buscar(SisAtributoSatb.class, Long.valueOf(Pagina
							.getParam(request, "codSaUsu"))));
		}

		if (!"".equals(Pagina.getParamStr(request, "atrNvPlan"))) {
			configuracao
					.setSisGrupoAtributoSgaByCodSgaGrAtrNvPlan((SisGrupoAtributoSga) this
							.buscar(SisGrupoAtributoSga.class, Long
									.valueOf(Pagina.getParam(request,
											"atrNvPlan"))));
		}

		/*
		 * -- Add conforme modifica��o solicitada no UC04-05
		 * 
		 * @author rogeriom
		 * 
		 * @since 12/04/2006 --
		 */
		if (!""
				.equals(Pagina
						.getParamStr(request, "intervaloAtualizacaoEmail"))) {
			configuracao.setIntervaloAtualizacaoEmail(Long.valueOf(Pagina
					.getParamStr(request, "intervaloAtualizacaoEmail")));
		}

		if (!"".equals(Pagina.getParamStr(request, "diasAntecedenciaMailCfg"))) {
			configuracao.setDiasAntecedenciaMailCfg(Long.valueOf(Pagina
					.getParamStr(request, "diasAntecedenciaMailCfg")));
		}

		if (!"".equals(Pagina.getParamStr(request, "horaEnvioMailCfg"))) {
			configuracao.setHoraEnvioMailCfg(Pagina.getParamStr(request,
					"horaEnvioMailCfg"));
		}

		this.setConfiguracaoMail(request, configuracao); /*
														 * -- Op��es de
														 * envio --
														 */

		Estilo estilo = new Estilo();
		estilo.setCodEstilo(Integer.valueOf((Pagina.getParamStr(request,
				"estilo"))));
		configuracao.setEstilo(estilo);

		/*
		 * -- Adicionado por Igor para migra��o dos properties para Banco
		 * 10/05/2006
		 */
		configuracao.setTituloSistema(Pagina.getParamStr(request,
				"tituloSistema"));
		configuracao.setEmailServer(Pagina.getParamStr(request, "emailServer"));
		configuracao.setImagemEsquerda(Pagina.getParamStr(request,
				"imagemEsquerda"));
		configuracao.setRaizUpload(Pagina.getParamStr(request, "raizUpload"));
		configuracao.setUploadUsuarios(Pagina.getParamStr(request,
				"uploadUsuarios"));
		configuracao.setUploadEmpresa(Pagina.getParamStr(request,
				"uploadEmpresa"));
		configuracao.setUploadCategoria(Pagina.getParamStr(request,
				"uploadCategoria"));
		configuracao.setUploadAnexos(Pagina
				.getParamStr(request, "uploadAnexos"));
		configuracao.setUploadAdmPortal(Pagina.getParamStr(request,
				"uploadAdmPortal"));
		configuracao.setUploadIconeLinks(Pagina.getParamStr(request,
				"uploadIconeLinks"));
		configuracao.setUploadExportacaoDemandas(Pagina.getParamStr(request,
				"uploadExportacaoDemandas"));

		configuracao.setSeparadorArqTXT(Pagina.getParamStr(request,
				"separadorArqTXT"));
		configuracao.setSeparadorCampoMultivalor(Pagina.getParamStr(request,
				"separadorCampoMultivalor"));

		configuracao.setNumRegistros(Long.valueOf(Pagina.getParamStr(request,
				"numRegistros")));
		configuracao.setQtdeItensGalAnexo(Long.valueOf(Pagina.getParamStr(
				request, "qteItensGalAnexo")));
		configuracao.setNuItensExibidosPaginacao(Integer.valueOf(
				Pagina.getParamStr(request, "nuItensExibidosPaginacao"))
				.intValue());

		configuracao.setLabelMonitorado(Pagina.getParamStr(request,
				"labelMonitorado"));
		configuracao.setLabelOrgao(Pagina.getParamStr(request, "labelOrgao"));
		configuracao.setLabelAgrupamentoItensSemOrgao(Pagina.getParamStr(request, "labelAgrupamentoItensSemOrgao"));

		configuracao.setLabelSituacaoParecer(Pagina.getParamStr(request,
				"labelSituacaoParecer"));
		configuracao.setLabelCorParecer(Pagina.getParamStr(request,
				"labelCorParecer"));
		configuracao.setLabelSituacaoListaPareceres(Pagina.getParamStr(request,
				"labelSituacaoListaPareceres"));

		/*
		 * -- Adicionado por aleixo - Integra��o Financeira 02/08/2006
		 */
		configuracao.setFinanceiroDescValor1Cfg(Pagina.getParamStr(request,
				"financeiroDescValor1Cfg"));
		configuracao.setFinanceiroDescValor2Cfg(Pagina.getParamStr(request,
				"financeiroDescValor2Cfg"));
		configuracao.setFinanceiroDescValor3Cfg(Pagina.getParamStr(request,
				"financeiroDescValor3Cfg"));
		configuracao.setFinanceiroDescValor4Cfg(Pagina.getParamStr(request,
				"financeiroDescValor4Cfg"));
		configuracao.setFinanceiroDescValor5Cfg(Pagina.getParamStr(request,
				"financeiroDescValor5Cfg"));
		configuracao.setFinanceiroDescValor6Cfg(Pagina.getParamStr(request,
				"financeiroDescValor6Cfg"));

		configuracao.setUploadIntegracao(Pagina.getParamStr(request,
				"uploadIntegracao"));

		/*
		 * -- Adicionado por aleixo - Integra��o Financeira (Cadastro de
		 * Recursos) 09/03/2007
		 */
		configuracao.setRecursoDescValor1Cfg(Pagina.getParamStr(request,
				"recursoDescValor1Cfg"));
		configuracao.setRecursoDescValor2Cfg(Pagina.getParamStr(request,
				"recursoDescValor2Cfg"));
		configuracao.setRecursoDescValor3Cfg(Pagina.getParamStr(request,
				"recursoDescValor3Cfg"));

		/*
		 * -- Adicionado por Rogerio Inclus�o de campo sobre Metas F�sicas
		 * na tela 15/03/2007 --
		 */
		if (!"".equals(Pagina.getParamStr(request, "codSgaMetasFisicas"))) {
			configuracao
					.setSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas((SisGrupoAtributoSga) this
							.buscar(SisGrupoAtributoSga.class, Long
									.valueOf(Pagina.getParam(request,
											"codSgaMetasFisicas"))));
		} else {
			configuracao.setSisGrupoAtributoSgaByCodSgaGrAtrMetasFisicas(null);
		}

		/*--
		 * Adicionado por Davi Gad�lha - SERPRO/DERCE
		 * Inclus�o do campo de per�odo padr�o para ser usado na tela de filtros
		 * 24/01/2008
		 */
		if (!"".equals(Pagina.getParamStr(request, "periodoPadrao"))) {
			configuracao.setPeriodoPadrao(Long.valueOf((Pagina.getParamStr(
					request, "periodoPadrao"))));
		} else {
			configuracao.setPeriodoPadrao(null);
		}

		if (!"".equals(Pagina.getParamStr(request, "tempoSessao"))) {
			configuracao.setTempoSessao((Long.valueOf((Pagina.getParamStr(
					request, "tempoSessao")))));
		} else {
			configuracao.setTempoSessao(2L);
		}

		if (!"".equals(Pagina.getParamStr(request,
				"indOcultarObservacoesParecer"))) {
			configuracao.setIndOcultarObservacoesParecer(Pagina.getParamStr(
					request, "indOcultarObservacoesParecer"));
		}

		if (!"".equals(Pagina.getParamStr(request,
				"indExibirSituacoesFormatoAbas"))) {
			String indExibirSituacoesFormatoAbas = Pagina.getParamStr(request,
					"indExibirSituacoesFormatoAbas");

			configuracao
					.setIndExibirSituacoesFormatoAbas(indExibirSituacoesFormatoAbas);
		}

		configuracao.setIndExibirArvoreNavegacaoCfg(Pagina.getParamStr(request,
				"indExibirArvoreNavegacaoCfg"));

		if (!"".equals(Pagina.getParamStr(request, "visaoDemandasVisDem"))) {
			configuracao.setVisaoDemandasVisDem((VisaoDemandasVisDem) this
					.buscar(VisaoDemandasVisDem.class, Long.valueOf(Pagina
							.getParam(request, "visaoDemandasVisDem"))));
		} else {
			configuracao.setVisaoDemandasVisDem(null);
		}
	} // fim setConfiguracao

	/**
	 * obtem dados da lista, testa para setar como ativo / inativo,testa para
	 * atribuir o texto e-mail,testa para atribuir o texto sms, por fim adiciona
	 * as configura��es
	 * 
	 * @param request
	 * @param configuracao
	 * @throws ECARException
	 */
	public void setConfiguracaoMail(HttpServletRequest request,
			ConfiguracaoCfg configuracao) throws ECARException {
		// obtem os dados da lista
		String[] listCfgMail = request.getParameterValues("chkAtivoCfgM");
		String[] listCfgObrigatorio = request
				.getParameterValues("chkObrigatorioCfgM");
		String[] listCboEmail = request
				.getParameterValues("cboCodTextoSiteEmail");
		String[] listCboSMS = request.getParameterValues("cboCodTextoSiteSMS");

		ConfigMailCfgmDAO configMailDao = new ConfigMailCfgmDAO(request);
		Iterator itConfigMail = configMailDao.listar(ConfigMailCfgm.class,
				new String[] { "descricaoCfgm", "asc" }).iterator();

		TextosSiteDao textoSiteDao = new TextosSiteDao(request);

		int indice = 0;

		while (itConfigMail.hasNext()) {
			ConfigMailCfgm configMail = (ConfigMailCfgm) itConfigMail.next();
			configMail.setAtivoCfgm(String.valueOf("N"));
			configMail.setIndEnvioObrigatorio(String.valueOf("N"));

			// teste para setar como ativo/ inativo
			if (listCfgMail != null) {
				for (int i = 0; i < listCfgMail.length; i++) {
					if (configMail.getCodCfgm().equals(
							Integer.valueOf(listCfgMail[i]))) {
						configMail.setAtivoCfgm(String.valueOf("S"));
					}
				}
			}

			// luana: incluindo o campo obrigatorio
			if (listCfgObrigatorio != null) {
				for (int i = 0; i < listCfgObrigatorio.length; i++) {
					if (configMail.getCodCfgm().equals(
							Integer.valueOf(listCfgObrigatorio[i]))) {
						configMail.setIndEnvioObrigatorio(String.valueOf("S"));
					}
				}
			}

			// teste para atribuir o texto e-mail
			if (listCboEmail != null) {
				if (!"0".equals(listCboEmail[indice])) {
					configMail.setTextosSiteMail((TextosSiteTxt) textoSiteDao
							.buscar(TextosSiteTxt.class, Long
									.valueOf(listCboEmail[indice])));
				} else {
					configMail.setTextosSiteMail(null);
				}
			}

			// teste para atribuir o texto sms
			if (listCboSMS != null) {
				if (!"0".equals(listCboSMS[indice])) {
					configMail.setTextosSiteSms((TextosSiteTxt) textoSiteDao
							.buscar(TextosSiteTxt.class, Long
									.valueOf(listCboSMS[indice])));
				} else {
					configMail.setTextosSiteSms(null);
				}
			}

			indice++;

			if (configuracao.getConfigMailCfgms() == null) {
				configuracao.setConfigMailCfgms(new HashSet());
			}

			configuracao.getConfigMailCfgms().add(configMail);
		}
	}

	/**
	 * Grava as configura��es ( ou altera se j� houver um registro gravado
	 * )
	 * 
	 * @param request
	 * @param configuracao
	 * @throws ECARException
	 * @throws HibernateException
	 * @throws AddressException 
	 * @throws AddressException 
	 */
	public void salvar(HttpServletRequest request, ConfiguracaoCfg configuracao)
			throws ECARException, HibernateException, AddressException{
		

		String ipStr = configuracao.getEmailServer();

		if (!Util.validaIP(ipStr)) {
			throw new ECARException("geral.validacao.servidor.email",null,new String[]{ipStr});
		}
		
		String horaEnvioEmail = configuracao.getHoraEnvioMailCfg();
		
		if (!Util.validaHora(horaEnvioEmail)) {
			throw new ECARException("geral.validacao.hora.envio.email",null,new String[]{horaEnvioEmail});
		}
		
		this.salvarOuAlterar(configuracao);
		
	}
	
	/**
	 * Verifica se j� foi gravado um registro de Configura��o
	 * 
	 * @return
	 * @throws ECARException
	 */
	public boolean existeConfiguracao() throws ECARException {
		List confg = this.listar(ConfiguracaoCfg.class, null);

		if ((confg != null) && (confg.size() > 0)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retorna o objeto configura��o do Sistema caso algum j� tenha sido
	 * gravado, caso contr�rio retorna null
	 * 
	 * @return
	 * @throws ECARException
	 */
	public ConfiguracaoCfg getConfiguracao() throws ECARException {
		return configuracao;
		// return (ConfiguracaoCfg) this.listar( ConfiguracaoCfg.class, null
		// ).iterator().next();
	}
	
	public ConfiguracaoCfg buscarConfiguracaoTituloENomeArquivoCss()
	{
		StringBuilder hql = new StringBuilder();
		hql.append("select new ecar.pojo.ConfiguracaoCfg");
		hql.append("(c.estilo.nome, c.tituloSistema) ");
		hql.append("from ConfiguracaoCfg c ");
		
		List<ConfiguracaoCfg> lista = this.consultarPorHQL(hql.toString());
		if(!lista.isEmpty()){
			return lista.get(0);
		}
		
		return null;
	}
}
