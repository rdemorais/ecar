/*
 * Criado em 07/12/2007
 *
 */
package ecar.taglib.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

import comum.database.Dao;
import comum.util.Pagina;
import comum.util.Util;

import ecar.dao.ConfiguracaoDao;
import ecar.dao.SisGrupoAtributoDao;
import ecar.dao.SituacaoDao;
import ecar.dao.UsuarioDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.AreaAre;
import ecar.pojo.AtributoLivre;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.PeriodicidadePrdc;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.SituacaoSit;
import ecar.pojo.SubAreaSare;
import ecar.pojo.UnidadeOrcamentariaUO;
import ecar.util.Dominios;

/**
 * 
 * @author Milton Pereira e Davi Gadelha SERPRO
 */
public class FormEstruturaAtributoFiltroTag implements Tag {

	ValidaPermissao validaPermissao = new ValidaPermissao();
	private ObjetoEstrutura atributo;
	private ItemEstruturaIett itemEstrutura;
	private EstruturaEtt estrutura;
	private Boolean desabilitar;
	private SegurancaECAR seguranca;
	private Boolean exibirBotoes = new Boolean(true);

	private PageContext page = null;
	private String contextPath = null;
	private String codEstrutura = null;

	private UsuarioDao usu = null;

	private HttpServletRequest request;
	

	private Boolean transformarComboBoxListaChecks = new Boolean(false);
	private Boolean transformarRadioListaChecks = new Boolean(false);
	
	/**
	 * Inicializa a montagem da tag para ser adicionada na tela de HTML.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
	 */
	public int doStartTag() throws JspException {
		try {
			usu = new UsuarioDao();
			if (atributo.iGetTipo() == EstruturaAtributoEttat.class) {

				String nomeMetodo = "geraHTML"
						+ Util.primeiraLetraToUpperCase(atributo.iGetNome());
				if (atributo.iGetGrupoAtributosLivres() != null) {
					nomeMetodo = "geraHTMLAtributoLivre";
				}
				this.getClass().getMethod(nomeMetodo, null).invoke(this, null);
			}
		
		} catch (Exception e) {
			// não é necessário lançar exceção aqui
		}
		return Tag.EVAL_BODY_INCLUDE;
	}

	/**
	 * Gera html pesquisa função de acompanhamento.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLPesquisaFuncaoAcompanhamento() {
		try {
			String codigo = "";
			String valor = "";
			boolean indAtivoUsu = true;
			if (getItemEstrutura().getCodIett() != null) {
				codigo = atributo.iGetValorCodFk(getItemEstrutura());
				valor = atributo.iGetValor(getItemEstrutura());
				
				
			}
			if (codigo != null && !"".equals(codigo) && codigo.startsWith("U")) {
				indAtivoUsu = usu.verificarUsuarioAtivo(Long.valueOf(codigo
						.substring(1)));
			}
			criaPesquisa(atributo.iGetNome(), indAtivoUsu,
					atributo.iGetLabel(), "ecar.popup.PopUpUsuarioEGrupo",
					"50", codigo, valor, "100", atributo.iGetDica());
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera html NomeIett.<br>
	 * 
	 * @author N/C
	 * @since N/C
         * @throws ECARException
         * @version N/C
	 */
	public void geraHTMLNomeIett() throws ECARException {
		try {
			if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
				criaTextArea(codEstrutura + "_nomeIett", atributo.iGetLabel(),
						"4", "60", atributo.iGetValor(getItemEstrutura()),
						atributo.iGetDica());
			
			} else {
				criaInputText(codEstrutura + "_nomeIett", atributo.iGetLabel(),
						atributo.iGetTamanhoConteudoAtrib().toString(),
						atributo.iGetTamanhoConteudoAtrib().toString(),
						atributo.iGetValor(getItemEstrutura()), atributo
								.iGetDica());
			
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera html SiglaIett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLSiglaIett() {
		try {
			criaInputText(codEstrutura + "_siglaIett", atributo.iGetLabel(),
					"12", "10", atributo.iGetValor(getItemEstrutura()),
					atributo.iGetDica());
			
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera html DescriçãoIett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDescricaoIett() {
		try {
			if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
				criaTextArea(codEstrutura + "_descricaoIett", atributo
						.iGetLabel(), "4", "60", atributo
						.iGetValor(getItemEstrutura()), atributo.iGetDica());
				
			} else {
				criaInputText(codEstrutura + "_descricaoIett", atributo
						.iGetLabel(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetValor(getItemEstrutura()),
						atributo.iGetDica());
				
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera html Origem Iett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLOrigemIett() {
		try {
			if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
				criaTextArea(codEstrutura + "_origemIett",
						atributo.iGetLabel(), "4", "60", atributo
								.iGetValor(getItemEstrutura()), atributo
								.iGetDica());
				
			} else {
				criaInputText(codEstrutura + "_origemIett", atributo
						.iGetLabel(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetValor(getItemEstrutura()),
						atributo.iGetDica());
				

			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera html objetivo Geral Iett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLObjetivoGeralIett() {
		try {
			if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
				criaTextArea(codEstrutura + "_objetivoGeralIett", atributo
						.iGetLabel(), "4", "60", atributo
						.iGetValor(getItemEstrutura()), atributo.iGetDica());
				
			} else {
				criaInputText(codEstrutura + "_objetivoGeralIett", atributo
						.iGetLabel(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetValor(getItemEstrutura()),
						atributo.iGetDica());
				
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera html objetivoEspecificoIett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLObjetivoEspecificoIett() {
		try {
			if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
				criaTextArea(codEstrutura + "_objetivoEspecificoIett", atributo
						.iGetLabel(), "4", "60", atributo
						.iGetValor(getItemEstrutura()), atributo.iGetDica());
				
			} else {
				criaInputText(codEstrutura + "_objetivoEspecificoIett",
						atributo.iGetLabel(), atributo
								.iGetTamanhoConteudoAtrib().toString(),
						atributo.iGetTamanhoConteudoAtrib().toString(),
						atributo.iGetValor(getItemEstrutura()), atributo
								.iGetDica());
				
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera html Beneficios Iett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLBeneficiosIett() {
		try {
			if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
				criaTextArea(codEstrutura + "_beneficiosIett", atributo
						.iGetLabel(), "4", "60", atributo
						.iGetValor(getItemEstrutura()), atributo.iGetDica());
				
			} else {
				criaInputText(codEstrutura + "_beneficiosIett", atributo
						.iGetLabel(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetValor(getItemEstrutura()),
						atributo.iGetDica());
				
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera html Data inicio Iett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDataInicioIett() {
		try {
			criaInputTextData(codEstrutura + "_dataInicioIett", atributo
					.iGetLabel(), atributo.iGetValor(getItemEstrutura()),
					atributo.iGetDica());
			
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera data termino Iett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDataTerminoIett() {
		try {
			criaInputTextData(codEstrutura + "_dataTerminoIett", atributo
					.iGetLabel(), atributo.iGetValor(getItemEstrutura()),
					atributo.iGetDica());
			
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera html IndCriticaIett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLIndCriticaIett() {
		List opcoes = new ArrayList();
		opcoes.add(new String[] { "S", "Sim" });
		opcoes.add(new String[] { "N", "Não" });
		try {
			
			if (transformarRadioListaChecks.booleanValue()) {
				criaListaChecksApartirRadio(codEstrutura + "_" + atributo.iGetNome(), atributo
						.iGetLabel(), atributo.iGetValor(getItemEstrutura()),
						opcoes, atributo.iGetDica());
			} else {
				criaRadio(codEstrutura + "_" + atributo.iGetNome(), atributo
						.iGetLabel(), atributo.iGetValor(getItemEstrutura()),
						opcoes, atributo.iGetDica());
			}
		
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera html Val Previsto Futuro Iett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLValPrevistoFuturoIett() {
		String valor = "";
		try {
			if (!"".equals(atributo.iGetValor(getItemEstrutura())))
				valor = Pagina.trocaNullNumeroDecimalSemMilhar(Double
						.valueOf(atributo.iGetValor(getItemEstrutura())));
			

			criaInputTextMoeda(codEstrutura + "_valPrevistoFuturoIett",
					atributo.iGetLabel(), "12", "30", valor, atributo
							.iGetDica());
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera data inicio monitoramento.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDataInicioMonitoramentoIett() {
		try {
			criaInputTextData(codEstrutura + "_" + atributo.iGetNome(),
					atributo.iGetLabel(), atributo
							.iGetValor(getItemEstrutura()), atributo.iGetDica());
		
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}

	}

	/**
	 * Gera html Data inclusão Iett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDataInclusaoIett() {
		try {
			criaInputTextData(codEstrutura + "_" + atributo.iGetNome(),
					atributo.iGetLabel(), atributo
							.iGetValor(getItemEstrutura()), atributo.iGetDica());
		
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera html UsuarioUsuByCodUsuIncIett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLUsuarioUsuByCodUsuIncIett() {
		try {
			if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
				criaTextArea(codEstrutura + "_" + atributo.iGetNome(), atributo
						.iGetLabel(), "4", "60", atributo
						.iGetValor(getItemEstrutura()), atributo.iGetDica());
				
			} else {
				criaInputText(codEstrutura + "_" + atributo.iGetNome(),
						atributo.iGetLabel(), atributo
								.iGetTamanhoConteudoAtrib().toString(),
						atributo.iGetTamanhoConteudoAtrib().toString(),
						atributo.iGetValor(getItemEstrutura()), atributo
								.iGetDica());
				
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera html data ultima manutenção Iett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDataUltManutencaoIett() {
		try {
			criaInputTextData(codEstrutura + "_" + atributo.iGetNome(),
					atributo.iGetLabel(), atributo
							.iGetValor(getItemEstrutura()), atributo.iGetDica());
			
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera html UsuarioUsuByCodUsuUltManutIett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLUsuarioUsuByCodUsuUltManutIett() {
		try {
			if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
				criaTextArea(codEstrutura + "_" + atributo.iGetNome(), atributo
						.iGetLabel(), "4", "60", atributo
						.iGetValor(getItemEstrutura()), atributo.iGetDica());
				
			} else {
				criaInputText(codEstrutura + "_" + atributo.iGetNome(),
						atributo.iGetLabel(), atributo
								.iGetTamanhoConteudoAtrib().toString(),
						atributo.iGetTamanhoConteudoAtrib().toString(),
						atributo.iGetValor(getItemEstrutura()), atributo
								.iGetDica());
				
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLIndAtivoIett() {
		List opcoes = new ArrayList();
		opcoes.add(new String[] { "S", "Sim" });
		opcoes.add(new String[] { "N", "Não" });
		try {
			if (transformarRadioListaChecks.booleanValue()) {
				criaListaChecksApartirRadio(codEstrutura + "_" + atributo.iGetNome(), atributo
						.iGetLabel(), atributo.iGetValor(getItemEstrutura()),
						opcoes, atributo.iGetDica());
			} else {
				criaRadio(codEstrutura + "_" + atributo.iGetNome(), atributo
						.iGetLabel(), atributo.iGetValor(getItemEstrutura()),
						opcoes, atributo.iGetDica());
			}

		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML DataR1.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDataR1() {
		try {
			criaInputTextData(codEstrutura + "_dataR1", atributo.iGetLabel(),
					atributo.iGetValor(getItemEstrutura()), atributo.iGetDica());
			
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML DataR2.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDataR2() {
		try {
			criaInputTextData(codEstrutura + "_dataR2", atributo.iGetLabel(),
					atributo.iGetValor(getItemEstrutura()), atributo.iGetDica());
			
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML DataR3.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDataR3() {
		try {
			criaInputTextData(codEstrutura + "_dataR3", atributo.iGetLabel(),
					atributo.iGetValor(getItemEstrutura()), atributo.iGetDica());
			
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML DataR4.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDataR4() {
		try {
			criaInputTextData(codEstrutura + "_dataR4", atributo.iGetLabel(),
					atributo.iGetValor(getItemEstrutura()), atributo.iGetDica());
		
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML DataR5.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDataR5() {
		try {
			criaInputTextData(codEstrutura + "_dataR5", atributo.iGetLabel(),
					atributo.iGetValor(getItemEstrutura()), atributo.iGetDica());
			
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML DescricaoR1.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDescricaoR1() {
		try {
			if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
				criaTextArea(codEstrutura + "_descricaoR1", atributo
						.iGetLabel(), "4", "60", atributo
						.iGetValor(getItemEstrutura()), atributo.iGetDica());
				
			} else {
				criaInputText(codEstrutura + "_descricaoR1", atributo
						.iGetLabel(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetValor(getItemEstrutura()),
						atributo.iGetDica());
				
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML DescricaoR2.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDescricaoR2() {
		try {
			if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
				criaTextArea(codEstrutura + "_descricaoR2", atributo
						.iGetLabel(), "4", "60", atributo
						.iGetValor(getItemEstrutura()), atributo.iGetDica());
				
			} else {
				criaInputText(codEstrutura + "_descricaoR2", atributo
						.iGetLabel(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetValor(getItemEstrutura()),
						atributo.iGetDica());
				
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML DescricaoR3.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDescricaoR3() {
		try {
			if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
				criaTextArea(codEstrutura + "_descricaoR3", atributo
						.iGetLabel(), "4", "60", atributo
						.iGetValor(getItemEstrutura()), atributo.iGetDica());
				
			} else {
				criaInputText(codEstrutura + "_descricaoR3", atributo
						.iGetLabel(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetValor(getItemEstrutura()),
						atributo.iGetDica());
				
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML DescricaoR4.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDescricaoR4() {
		try {
			if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
				criaTextArea(codEstrutura + "_descricaoR4", atributo
						.iGetLabel(), "4", "60", atributo
						.iGetValor(getItemEstrutura()), atributo.iGetDica());
				
			} else {
				criaInputText(codEstrutura + "_descricaoR4", atributo
						.iGetLabel(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetValor(getItemEstrutura()),
						atributo.iGetDica());
				
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML DescricaoR5.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLDescricaoR5() {
		try {
			if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
				criaTextArea(codEstrutura + "_descricaoR5", atributo
						.iGetLabel(), "4", "60", atributo
						.iGetValor(getItemEstrutura()), atributo.iGetDica());
				
			} else {
				criaInputText(codEstrutura + "_descricaoR5", atributo
						.iGetLabel(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetTamanhoConteudoAtrib()
						.toString(), atributo.iGetValor(getItemEstrutura()),
						atributo.iGetDica());
				
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML AreaAre.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLAreaAre() {
		AreaAre area = new AreaAre();
		area.setIndAtivoAre("S");
		try {
			List areas = new Dao().pesquisar(area, new String[] {
					atributo.iGetNomeFk(), "asc" });
			List options = new ArrayList();
			Iterator it = areas.iterator();
			while (it.hasNext()) {
				area = (AreaAre) it.next();
				options
						.add(new String[] {
								area.getCodAre().toString(),
								Util.invocaGet(area, atributo.iGetNomeFk())
										.toString() });
			}

			if (transformarComboBoxListaChecks.booleanValue()) {
				criaListaChecksApartirSelect(codEstrutura + "_areaAre", atributo.iGetLabel(),
						atributo.iGetValorCodFk(getItemEstrutura()), options, "",
						atributo.iGetDica()); 
			} else {
				criaSelect(codEstrutura + "_areaAre", atributo.iGetLabel(),
					atributo.iGetValorCodFk(getItemEstrutura()), options, "",
					atributo.iGetDica());
			}
			
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML SubAreaSare.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLSubAreaSare() {
		SubAreaSare sArea = new SubAreaSare();
		sArea.setIndAtivoSare("S");
		try {
			List sAreas = new Dao().pesquisar(sArea, new String[] {
					atributo.iGetNomeFk(), "asc" });
			List options = new ArrayList();
			Iterator it = sAreas.iterator();
			while (it.hasNext()) {
				sArea = (SubAreaSare) it.next();
				options
						.add(new String[] {
								sArea.getCodSare().toString(),
								Util.invocaGet(sArea, atributo.iGetNomeFk())
										.toString() });
			}
			if (transformarComboBoxListaChecks.booleanValue()) {
				criaListaChecksApartirSelect(codEstrutura + "_subAreaSare", atributo.iGetLabel(),
						atributo.iGetValorCodFk(getItemEstrutura()), options, "",
						atributo.iGetDica()); 
			} else {
				criaSelect(codEstrutura + "_subAreaSare", atributo.iGetLabel(),
					atributo.iGetValorCodFk(getItemEstrutura()), options, "",
					atributo.iGetDica());
			}
			
		
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML UnidadeOrcamentariaUO.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLUnidadeOrcamentariaUO() {
		if (transformarComboBoxListaChecks.booleanValue()) {
			try {
				
				List unidades = new Dao().listar(UnidadeOrcamentariaUO.class, new String[]{"descricaoUo", Dao.ORDEM_ASC});
				
				List options = new ArrayList();
				Iterator it = unidades.iterator();
				UnidadeOrcamentariaUO unidade = null;
				while (it.hasNext()) {
					unidade = (UnidadeOrcamentariaUO) it.next();
					options
							.add(new String[] {
									unidade.getCodUo().toString(),
									unidade.getDescricaoUo()});
				}
			
				criaListaChecksApartirSelect(codEstrutura + "_unidadeOrcamentariaUO", "Unidade Orçamentária", null, options, "", null);
			} catch (ECARException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} else {
			criaDiv(codEstrutura + "_unidadeOrcamentariaDiv", atributo.iGetLabel(),
				atributo.iGetDica());
		}
	}
	

	/**
	 * Gera HTML OrgaoOrgByCodOrgaoResponsavel1Iett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLOrgaoOrgByCodOrgaoResponsavel1Iett() {
		OrgaoOrg orgao = new OrgaoOrg();
		orgao.setIndAtivoOrg("S");
		try {
			List orgaos = new Dao().pesquisar(orgao, new String[] {
					atributo.iGetNomeFk(), "asc" });
			List options = new ArrayList();
			Iterator it = orgaos.iterator();
			while (it.hasNext()) {
				orgao = (OrgaoOrg) it.next();
				options
						.add(new String[] {
								orgao.getCodOrg().toString(),
								Util.invocaGet(orgao, atributo.iGetNomeFk())
										.toString() });
			}

			String disabled = (getBloquearCampo()) ? "disabled" : "";

			String scripts = "";

			if (transformarComboBoxListaChecks.booleanValue()) {
				criaListaChecksApartirSelect(codEstrutura + "_orgaoOrgByCodOrgaoResponsavel1Iett",
					atributo.iGetLabel(), atributo
							.iGetValorCodFk(getItemEstrutura()), options,
					scripts, atributo.iGetDica());
			} else {
				
				scripts = "onchange=\"javascript:carregaUnidadeOrc(this.value,'"
					+ disabled + "', '" + codEstrutura + "');\"";
				
				criaSelect(codEstrutura + "_orgaoOrgByCodOrgaoResponsavel1Iett",
						atributo.iGetLabel(), atributo
								.iGetValorCodFk(getItemEstrutura()), options,
						scripts, atributo.iGetDica());
			}

		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML OrgaoOrgByCodOrgaoResponsavel2Iett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLOrgaoOrgByCodOrgaoResponsavel2Iett() {
		OrgaoOrg orgao = new OrgaoOrg();
		orgao.setIndAtivoOrg("S");
		try {
			List orgaos = new Dao().pesquisar(orgao, new String[] {
					atributo.iGetNomeFk(), "asc" });
			List options = new ArrayList();
			Iterator it = orgaos.iterator();
			while (it.hasNext()) {
				orgao = (OrgaoOrg) it.next();
				options
						.add(new String[] {
								orgao.getCodOrg().toString(),
								Util.invocaGet(orgao, atributo.iGetNomeFk())
										.toString() });
			}
			
			if (transformarComboBoxListaChecks.booleanValue()) {
				criaListaChecksApartirSelect(codEstrutura + "_orgaoOrgByCodOrgaoResponsavel2Iett",
						atributo.iGetLabel(), atributo
						.iGetValorCodFk(getItemEstrutura()), options, "",
				atributo.iGetDica());
			} else {
				criaSelect(codEstrutura + "_orgaoOrgByCodOrgaoResponsavel2Iett",
					atributo.iGetLabel(), atributo
							.iGetValorCodFk(getItemEstrutura()), options, "",
					atributo.iGetDica());
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML PeriodicidadePrdc.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLPeriodicidadePrdc() {
		PeriodicidadePrdc prd = new PeriodicidadePrdc();
		try {
			List periodicidades = new Dao().pesquisar(prd, new String[] {
					atributo.iGetNomeFk(), "asc" });
			List options = new ArrayList();
			Iterator it = periodicidades.iterator();
			while (it.hasNext()) {
				prd = (PeriodicidadePrdc) it.next();
				options
						.add(new String[] {
								prd.getCodPrdc().toString(),
								Util.invocaGet(prd, atributo.iGetNomeFk())
										.toString() });
			}
			PeriodicidadePrdc perConfig = new ConfiguracaoDao(null)
					.getConfiguracao().getPeriodicidadePrdc();
			if ((getItemEstrutura() == null || getItemEstrutura().getCodIett() == null)
					&& perConfig != null) {
				if (transformarComboBoxListaChecks.booleanValue()) {
					criaListaChecksApartirSelect(codEstrutura + "_periodicidadePrdc", atributo
							.iGetLabel(), atributo
							.iGetValorCodFk(getItemEstrutura()),
							options, "", atributo.iGetDica());
				} else {
					criaSelect(codEstrutura + "_periodicidadePrdc", atributo
							.iGetLabel(), atributo
							.iGetValorCodFk(getItemEstrutura()),
							options, "", atributo.iGetDica());
				}
			} else {
				if (transformarComboBoxListaChecks.booleanValue()) {
					criaListaChecksApartirSelect(codEstrutura + "_periodicidadePrdc", atributo
						.iGetLabel(), atributo
						.iGetValorCodFk(getItemEstrutura()), options, "",
						atributo.iGetDica());
				} else {
					criaSelect(codEstrutura + "_periodicidadePrdc", atributo
							.iGetLabel(), atributo
							.iGetValorCodFk(getItemEstrutura()), options, "",
							atributo.iGetDica());
				}
		
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML SituacaoSit.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLSituacaoSit() {
		SituacaoSit situacao = new SituacaoSit();
		try {
			List situacoes = new SituacaoDao(null).getSituacaoByEstrutura(
					estrutura, new String[] { "descricaoSit", "asc" });

			List options = new ArrayList();
			Iterator it = situacoes.iterator();
			while (it.hasNext()) {
				situacao = (SituacaoSit) it.next();
				options.add(new String[] {
						situacao.getCodSit().toString(),
						Util.invocaGet(situacao, atributo.iGetNomeFk())
								.toString() });
			}

			if (transformarComboBoxListaChecks.booleanValue()) {
				criaListaChecksApartirSelect(codEstrutura + "_situacaoSit", atributo.iGetLabel(),
						atributo.iGetValorCodFk(getItemEstrutura()), options, "",
						atributo.iGetDica());
			} else {
				criaSelect(codEstrutura + "_situacaoSit", atributo.iGetLabel(),
					atributo.iGetValorCodFk(getItemEstrutura()), options, "",
					atributo.iGetDica());
			}
			
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera Atributo Livre
	 * 
	 * @author aleixo
	 * @since 24/05/2007
	 * @version 0.1
         * @throws ECARException
         * @throws JspException
	 */
	public void geraHTMLAtributoLivre() throws ECARException, JspException {

		if (atributo.iGetGrupoAtributosLivres() != null) {

			JspWriter writer = this.page.getOut();

			SisGrupoAtributoSga grupoAtributo = atributo
					.iGetGrupoAtributosLivres();
			int tipoInput = grupoAtributo.getSisTipoExibicGrupoSteg()
					.getCodSteg().intValue();
			if (tipoInput == Input.CHECKBOX || tipoInput == Input.COMBOBOX
					|| tipoInput == Input.LISTBOX
					|| tipoInput == Input.MULTIPLO
					|| tipoInput == Input.MULTITEXTO
					|| tipoInput == Input.RADIO_BUTTON
					|| tipoInput == Input.TEXT || tipoInput == Input.TEXTAREA
					|| tipoInput == Input.VALIDACAO) {

				Input input = new Input(writer);
				input.setRequest(request);
				input.setTelaFiltro(new Boolean(true));
				input.setLabel(atributo.iGetLabel());
				input.setTipo(tipoInput);
				input.setObrigatorio("N"); // Nenhum filtro é obrigatório

				input.setPathRaiz(this.getContextPath());
				
				input.setSize(atributo.iGetTamanhoConteudoAtrib().toString());

				if (getBloquearCampo()) {
					input.setDisabled("S");
				}
				input.setNome(codEstrutura + "_a"
						+ grupoAtributo.getCodSga().toString());
				input.setClassLabel("label");

				List aributosLivresSelecionados = new ArrayList();

				
				
				
				if (getItemEstrutura().getItemEstruturaSisAtributoIettSatbs() != null) {
					Iterator itAtribLivres = getItemEstrutura()
							.getItemEstruturaSisAtributoIettSatbs().iterator();
					while (itAtribLivres.hasNext()) {
						ItemEstruturaSisAtributoIettSatb atributo = (ItemEstruturaSisAtributoIettSatb) itAtribLivres
								.next();
						AtributoLivre atributoLivre = new AtributoLivre();
						atributoLivre.setInformacao(atributo
								.getInformacao());
						atributoLivre.setSisAtributoSatb(atributo
								.getSisAtributoSatb());
						aributosLivresSelecionados.add(atributoLivre);
						
					}
					
				} else {
			
					if (this.atributo.iGetGrupoAtributosLivres().getSisAtributoSatbs() != null) {
						Iterator itAtribLivres = atributo.iGetGrupoAtributosLivres().getSisAtributoSatbs().iterator();
						while (itAtribLivres.hasNext()) {
							SisAtributoSatb atributoSis = (SisAtributoSatb) itAtribLivres.next();
							AtributoLivre atributoLivre = new AtributoLivre();
							String atrib = "";
							if(tipoInput == Input.MULTITEXTO) {
								atrib = Pagina.getParamStr(request, codEstrutura + "_a"+ atributoSis.getSisGrupoAtributoSga().getCodSga().toString() + 
																				"_" + atributoSis.getCodSatb().toString());
								
								if(atrib != null && !atrib.equals("")) {
									atributoLivre.setInformacao(atrib);
									atributoLivre.setSisAtributoSatb(atributoSis);
									aributosLivresSelecionados.add(atributoLivre);
								}
								
								
							} else {	
								String listaAtrib[] = Pagina.getParamLista(request, codEstrutura + "_a"+ atributoSis.getSisGrupoAtributoSga().getCodSga().toString());
								
								if(listaAtrib!= null && listaAtrib.length>0) {
									for(int i=0; i < listaAtrib.length; i++) {
										if(listaAtrib[i] != null && !listaAtrib[i].equals("") && listaAtrib[i].equals(atributoSis.getCodSatb().toString())) {
											atributoLivre.setInformacao(listaAtrib[i]);
											atributoLivre.setSisAtributoSatb(atributoSis);
											aributosLivresSelecionados.add(atributoLivre);
										}
									}
								}
							}
						}
					}	
				}

			
				input.setSelecionados(aributosLivresSelecionados);
				input.setSisAtributo((SisAtributoSatb) grupoAtributo
						.getSisAtributoSatbs().iterator().next());
				if (atributo.iGetDica() != null)
					input.setDica(atributo.iGetDica());
				
				String atribInicio = Pagina.getParamStr(request, codEstrutura + "_a"
							+ grupoAtributo.getCodSga().toString());
				if(atribInicio == null) {
					atribInicio = "";
				}
				
		        AtributoLivre atributoLivreTeste = new AtributoLivre();
                atributoLivreTeste.setInformacao(atribInicio);
                if(tipoInput == Input.VALIDACAO){
                	Iterator it = atributo.iGetGrupoAtributosLivres().getSisAtributoSatbs().iterator();
                	atributoLivreTeste.setSisAtributoSatb((SisAtributoSatb)it.next());
                }
                input.setAtribLivre(atributoLivreTeste);
			    input.setTransformarComboBoxListaChecks(transformarComboBoxListaChecks.booleanValue());
               
               
				input.doStartTag();
				
				
				Options options = new Options(writer);
				
				options.setTransformarComboBoxListaChecks(transformarComboBoxListaChecks.booleanValue());
				options.setTransformarRadioListaChecks(transformarRadioListaChecks.booleanValue());
				
				List opcoes = new ArrayList();
				String selectedCodSapadrao = "";
				if (grupoAtributo.getCodSga() != null
						&& grupoAtributo.getCodSga().longValue() != 1) {
					if (grupoAtributo.getSisTipoOrdenacaoSto() != null) {
						opcoes = new SisGrupoAtributoDao(null)
								.getAtributosOrdenados(grupoAtributo);
					}
				}

				if (!opcoes.isEmpty()) {
					options.setOptions(opcoes);
					options.setValor("codSatb");
					options.setLabel("descricaoSatb");
					options.setParent(input);
					options.setNome(codEstrutura + "_a"
							+ grupoAtributo.getCodSga().toString());
					options.doStartTag();
				}

				input.doEndTag();
			}
		}
	}

	/**
	 * Gera HTML NivelPlanejamento.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @throws ECARException
	 * @throws JspException
	 */
	public void geraHTMLNivelPlanejamento() throws ECARException, JspException {

		JspWriter writer = this.page.getOut();

		SisGrupoAtributoSga grupoAtributo = new ConfiguracaoDao(null)
				.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan();

		Input input = new Input(writer);
		input.setRequest(request);
		input.setLabel(grupoAtributo.getDescricaoSga());
		input.setTipo(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg()
				.intValue());
		input.setObrigatorio("N");
		
		if (getBloquearCampo())
			input.setDisabled("S");

		input.setNome(codEstrutura + "_a"
				+ grupoAtributo.getCodSga().toString());
		input.setClassLabel("label");

		List niveisSelecionados = new ArrayList();

		if (getItemEstrutura().getItemEstruturaNivelIettns() != null) {
			Iterator itNiveis = getItemEstrutura()
					.getItemEstruturaNivelIettns().iterator();
			while (itNiveis.hasNext()) {
				SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
				AtributoLivre atributoLivre = new AtributoLivre();
				atributoLivre.setSisAtributoSatb(nivel);
				niveisSelecionados.add(atributoLivre);
			}
		} else {
			
			if(grupoAtributo.getSisAtributoSatbs() != null) {
				Iterator itNiveis = grupoAtributo.getSisAtributoSatbs().iterator();
				while (itNiveis.hasNext()) {
					SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
						
					String nomeHiddenNivelPlanejamento = codEstrutura + "_a" + grupoAtributo.getCodSga().toString();        
       				String valorHiddenNivelPlanejamento[] = Pagina.getParamLista(request, nomeHiddenNivelPlanejamento);
					
       				if(valorHiddenNivelPlanejamento != null && !(valorHiddenNivelPlanejamento.length==1 && valorHiddenNivelPlanejamento[0].equals(""))) {
       					for(int i=0; i< valorHiddenNivelPlanejamento.length; i++) {
       						if(valorHiddenNivelPlanejamento[i] != null && !valorHiddenNivelPlanejamento[i].equals("") && 
       								valorHiddenNivelPlanejamento[i].equals(nivel.getCodSatb().toString())) {
       							AtributoLivre atributoLivre = new AtributoLivre();
       							atributoLivre.setSisAtributoSatb(nivel);
       							niveisSelecionados.add(atributoLivre);
       						}	
       					}	
       				}	
				}
			}
		}

		input.setSelecionados(niveisSelecionados);
		input.setSisAtributo((SisAtributoSatb) grupoAtributo
				.getSisAtributoSatbs().iterator().next());
		
		if (atributo.iGetDica() != null)
			input.setDica(atributo.iGetDica());

		
		input.setTransformarComboBoxListaChecks(transformarComboBoxListaChecks.booleanValue());
		
		input.doStartTag();

		Options options = new Options(writer);
		
		options.setTransformarComboBoxListaChecks(transformarComboBoxListaChecks.booleanValue());
		options.setTransformarComboBoxListaChecks(transformarRadioListaChecks.booleanValue());
		
		List opcoes = new ArrayList();
		if (grupoAtributo.getCodSga() != null
				&& grupoAtributo.getCodSga().longValue() != 1) {
			if (grupoAtributo.getSisTipoOrdenacaoSto() != null) {
				opcoes = new SisGrupoAtributoDao(null)
						.getAtributosOrdenados(grupoAtributo);
			}
		}

		List opcoesAtivos = new ArrayList();
		if (!opcoes.isEmpty()) {
			Iterator opcoesIt = opcoes.iterator();
			while (opcoesIt.hasNext()) {
				SisAtributoSatb sisAtributoSatb = (SisAtributoSatb) opcoesIt
						.next();
				if (sisAtributoSatb.getIndAtivoSatb().equals(Dominios.SIM)) {
					opcoesAtivos.add(sisAtributoSatb);
				}
			}
			options.setOptions(opcoesAtivos);
			options.setValor("codSatb");
			options.setLabel("descricaoSatb");
			options.setImagem(getContextPath() + "/images/relAcomp/");
			options.setParent(input);
			options.doStartTag();
		}

		input.doEndTag();

	}

	/**
	 * Gera HTML FuncaoAcompanhamento.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLFuncaoAcompanhamento() {
		try {

			// Verifica Indicador de Usuario Ativo
			String codigo = "";
			boolean indAtivoUsu = true;
			if (codigo != null && !"".equals(codigo)) {
				indAtivoUsu = usu.verificarUsuarioAtivo(Long.valueOf(codigo));
			}

			criaPesquisa(codEstrutura + "_" + atributo.iGetNome(), indAtivoUsu,
					atributo.iGetLabel(), "ecar.pojo.UsuarioUsu", "50",
					atributo.iGetValorCodFk(getItemEstrutura()), atributo
							.iGetValor(getItemEstrutura()), "100", atributo
							.iGetDica());
			
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Este Método cria o botão que Permite Ativar e Retirar Monitoramento.<br>
	 * Ele cria o botão, um campo hidden com o flag e um javascript para ser
	 * executado no método onclick do botão O javascript verificar o label atual
	 * do botão, seta o flag do campo hidden dependendo deste label.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLIndMonitoramentoIett() {
		List opcoes = new ArrayList();
		opcoes.add(new String[] { "S", "Sim" });
		opcoes.add(new String[] { "N", "Não" });
		try {
			if (transformarRadioListaChecks.booleanValue()) {
				criaListaChecksApartirRadio(codEstrutura + "_" + atributo.iGetNome(), atributo
					.iGetLabel(), atributo.iGetValor(getItemEstrutura()),
					opcoes, atributo.iGetDica());
			} else {
				criaRadio(codEstrutura + "_" + atributo.iGetNome(), atributo
						.iGetLabel(), atributo.iGetValor(getItemEstrutura()),
						opcoes, atributo.iGetDica());
			}
		
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera HTML IndBloqPlanejamentoIett.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void geraHTMLIndBloqPlanejamentoIett() {
		List opcoes = new ArrayList();
		opcoes.add(new String[] { "S", "Sim" });
		opcoes.add(new String[] { "N", "Não" });
		try {
			if (transformarRadioListaChecks.booleanValue()) {
				criaListaChecksApartirRadio(codEstrutura + "_" + atributo.iGetNome(), atributo
						.iGetLabel(), atributo.iGetValor(getItemEstrutura()),
						opcoes, atributo.iGetDica());
			} else {
				criaRadio(codEstrutura + "_" + atributo.iGetNome(), atributo
						.iGetLabel(), atributo.iGetValor(getItemEstrutura()),
						opcoes, atributo.iGetDica());
			}
		} catch (ECARException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Gera uma linha do formulário, contendo campo de pesquisa. <br>
	 * 
	 * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.2, 22/03/2007
         * @param nome
         * @param size
         * @param IndAtivoUsu
         * @param label
         * @param classePesquisa
         * @param valorText
         * @param valor
         * @param maxlength
         * @param dica
	 */
	public void criaPesquisa(String nome, boolean IndAtivoUsu, String label,
			String classePesquisa, String size, String valor, String valorText,
			String maxlength, String dica) {
		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();

		try {

			// Cria a linha
			s.append("<TR><TD class=\"label\">");

			// Verifica se usuario estah ativo.
			String imagem_inativa = "";
			if (!IndAtivoUsu) {
				imagem_inativa = "<img src=\"../../images/icon_usuario_inativo.png\" title=\"Usuário Inativo\">";
			}

			// Aplica o label do campo
			s.append(label);

			s.append("</TD>").append("<TD>").append(
					"<input type=\"text\" disabled name=\"nome").append(nome)
					.append("\" size=\"").append(size).append("\" value=\"")
					.append(valorText).append("\" maxlength=\"").append(
							maxlength).append("\"").append(">").append(
							imagem_inativa).append(
							"<input type=\"hidden\" name=\"").append(nome)
					.append("\" value=\"").append(valor).append("\">");

			// Adiciona o campo
			if (this.getExibirBotoes().booleanValue()) {
				s
						.append("&nbsp;&nbsp;<input type=\"button\" name=\"pesq\" value=\"Pesquisar\" class=\"botao\" ");
				if (getBloquearCampo())
					s.append(" disabled");

				s
						.append(" onclick=\"popup_pesquisa('")
						.append(classePesquisa)
						.append("', 'retorno")
						.append(nome)
						.append("');\">")
						.append(
								"&nbsp;&nbsp;<input type=\"button\" name=\"pesq\" value=\"Limpar\" class=\"botao\" ")
						.append(
								"onclick=\"document.form.alterou.value='S';document.form.nome")
						.append(nome).append(".value=''; document.form.")
						.append(nome).append(".value=''\"");

				if (getBloquearCampo())
					s.append(" disabled");

				s.append(">");
			}

			// Aplica a dica de uso da linha, caso ela exista no cadastro.
			// Mantis #8688. Por Rogério (22/03/2007)
			if (dica != null && !"".equals(dica))
				s.append(Util.getTagDica(nome, this.getContextPath(), dica));

			s.append("</TD></TR>");

			writer.print(s.toString());

		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}

	}

	/**
	 * Cria uma linha no formulário com um campo SELECT. <br>
	 * 
	 * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.2, 22/03/2007
         * @param nome
         * @param label
         * @param valor
         * @param scripts
         * @param opcoes
         * @param dica
	 */
	public void criaSelect(String nome, String label, String valor,
			Collection opcoes, String scripts, String dica) {

		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();

		try {

			String atribInicio = Pagina.getParamStr(request, nome);
			if(atribInicio == null) {
				atribInicio = "";
			}

			valor = atribInicio;
		
			// Inicia a linha
			s.append("<TR><TD class=\"label\">");

			// Aplica o label do campo
			s.append(label);

			s.append("</TD>");
			
			// se não for pra mudar forma de apresentacao do combobox
            
			s.append("<TD>");
			s.append("<select name=\"").append(nome).append("\" ").append(
						scripts);
			if (getBloquearCampo())
				s.append(" disabled");
			s.append(">");
			s.append("<option value=\"\"></option>");
			if (opcoes != null) {
				Iterator it = opcoes.iterator();
				while (it.hasNext()) {
					String[] chaveValor = (String[]) it.next();
					s.append("<option value=\"").append(chaveValor[0]).append(
								"\"");
					if (chaveValor[0].equals(valor))
							s.append(" selected ");
						s.append(">");
						s.append(chaveValor[1]);
						s.append("</option>");
					}
			}
			s.append("</select>");
			s.append("");
			// Aplica a dica de uso da linha, caso ela exista no cadastro.
			// Mantis #8688. Por Rogério (22/03/2007)
			if (dica != null && !"".equals(dica))
					s.append(Util.getTagDica(nome, this.getContextPath(), dica));

            
			// finaliza linha
			s.append("</TD></TR>");

			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}
	
	
	/**
	 * Cria uma linha no formulário com um campo SELECT. <br>
	 * 
	 * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.2, 22/03/2007
         * @param nome
         * @param label
         * @param valor
         * @param opcoes
         * @param scripts
         * @param dica
	 */
	public void criaListaChecksApartirSelect(String nome, String label, String valor,
			Collection opcoes, String scripts, String dica) {

		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();

		try {

			//Recupera todos os valores que foram selecionados
			String[] listaValoresAtributo =  Pagina.getParamLista(request, nome);
			
			// Inicia a linha
			s.append("<TR><TD class=\"label\">");

			// Aplica o label do campo
			s.append(label);

			s.append("</TD>");
			
			// se não for pra mudar forma de apresentacao do combobox
			s.append("<TD>");
			
			if (opcoes != null) {
				Iterator it = opcoes.iterator();
				while (it.hasNext()) {
					String[] chaveValor = (String[]) it.next();
					s.append("<input type=\"checkbox\" class=\"form_check_radio\"  name=\"").append(nome)
		             .append("\" value=\"").append(chaveValor[0]).append("\"");
			
					if (listaValoresAtributo != null) {
						if (listaValoresAtributo!=null && !(listaValoresAtributo.length==1 && listaValoresAtributo[0].equals(""))) {  
							for (int i=0; i<listaValoresAtributo.length;i++) {
		        				if (chaveValor[0].equals(listaValoresAtributo[i])) {
		        					s.append(" checked  ");
				    			}	
		        			}
						}
					}
			
					if (getBloquearCampo())
						s.append(" disabled");
					s.append(">");	
					s.append(chaveValor[1]);
					s.append("<br>\n");
				} 
			}

			// dica
			if (dica != null && !"".equals(dica))
					s.append(Util.getTagDica(nome, this.getContextPath(), dica));

			// finaliza linha
			s.append("</TD></TR>");

			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}
	
	
	/**
	 * Cria linha no formulário com um campo RADIO BUTTON. <br>
	 * 
	 * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.2, 22/03/2007
         * @param nome
         * @param label
         * @param opcoes
         * @param valor
         * @param dica
	 */
	public void criaRadio(String nome, String label, String valor,
			Collection opcoes, String dica) {
		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();
		
		String atribInicio = Pagina.getParamStr(request, nome);
		if(atribInicio == null) {
			atribInicio = "";
		}

		valor = atribInicio;
		
		try {
			s.append("<TR><TD class=\"label\">");
			s.append(label);
			s.append("</TD>");
			s.append("<TD>");
			if (opcoes != null) {
				Iterator it = opcoes.iterator();
				while (it.hasNext()) {
					String[] chaveValor = (String[]) it.next();
					s
							.append(
									"<input type=\"radio\" class=\"form_check_radio\" name=\"")
							.append(nome).append("\" value=\"").append(
									chaveValor[0]).append("\"");

					if (getBloquearCampo())
						s.append(" disabled");
					if (chaveValor[0].equals(valor))
						s.append(" checked ");
					s.append(">");
					s.append(chaveValor[1]);
				}
				s
						.append(
								"&nbsp;<input type=\"button\" name=\"buttonLimpar\" value=\"Limpar\" class=\"botao\" ")
						.append(
								"onclick=\"limparRadioButtons(document.getElementsByName('"
										+ nome + "'));\"");
				if (getBloquearCampo())
					s.append(" disabled ");
				s.append(">");
			}

			// Aplica a dica de uso da linha, caso ela exista no cadastro.
			// Mantis #8688. Por Rogério (22/03/2007)
			if (dica != null && !"".equals(dica))
				s.append(Util.getTagDica(nome, this.getContextPath(), dica));

			s.append("</TD></TR>");
			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}
	
	
	
	/**
	 * Cria linha no formulário com um campo RADIO BUTTON. <br>
	 * 
	 * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.2, 22/03/2007
	 * @param String
	 *            nome
	 * @param String
	 *            label
	 * @param String
	 *            valor
	 * @param Collection
	 *            opcoes
	 * @param String
	 *            dica
	 */
	private void criaListaChecksApartirRadio(String nome, String label, String valor,
			Collection opcoes, String dica) {
		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();

		//Recupera todos os valores que foram selecionados
		String[] listaValoresAtributo =  Pagina.getParamLista(request, nome);

		try {
			s.append("<TR><TD class=\"label\">");
			s.append(label);
			s.append("</TD>");
			s.append("<TD>");
			if (opcoes != null) {
				Iterator it = opcoes.iterator();
				while (it.hasNext()) {
					String[] chaveValor = (String[]) it.next();
					s.append("<input type=\"checkbox\" class=\"form_check_radio\"  name=\"").append(nome)
		             .append("\" value=\"").append(chaveValor[0]).append("\"");
					if (listaValoresAtributo != null) {
						if (listaValoresAtributo!=null && !(listaValoresAtributo.length==1 && listaValoresAtributo[0].equals(""))) {  
							for (int i=0; i<listaValoresAtributo.length;i++) {
		        				if (chaveValor[0].equals(listaValoresAtributo[i])) {
		        					s.append(" checked  ");
		        					break;
				    			}	
		        			}
						}
					}
			
					if (getBloquearCampo())
						s.append(" disabled");
					s.append(">");	
					s.append(chaveValor[1]);
					s.append("<br>\n");
				} 
			}

			// Aplica a dica de uso da linha, caso ela exista no cadastro.
			// Mantis #8688. Por Rogério (22/03/2007)
			if (dica != null && !"".equals(dica))
				s.append(Util.getTagDica(nome, this.getContextPath(), dica));

			s.append("</TD></TR>");
			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}
	
	
	

	/**
	 * Cria uma linha no formulário com objeto botão. <br>
	 * 
	 * @author n/c
	 * @since 0.1, n/c
	 * @version 0.1, n/c
         * @param label
         * @param onclick
         * @param name
	 */
	public void criaInputButton(String label, String name, String onclick) {
		if (!this.getExibirBotoes().booleanValue())
			return;

		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();
		try {
			s.append("<TR><TD>&nbsp;</TD><TD>");
			s.append("<input type=\"button\" ");
			/*
			 * Regra de bloqueio destes botões é diferente. Deve ficar sempre
			 * liberado, menos nos casos que o usuário não tem permissão.
			 */
			if (getDesabilitar() != null && getDesabilitar()) {
				s.append(" disabled ");
			}
			s.append("value=\"").append(label).append("\" name=\"bt").append(
					name).append("\" onclick=\"").append(onclick).append("\"")
					.append("></TD></TR>");
			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Cria campo do tipo hidden no formulário. <br>
	 * 
	 * @author n/c
	 * @since 0.1, n/c
	 * @version 0.1, n/c
         * @param nome
         * @param valor
	 */
	public void criaInputHidden(String nome, String valor) {
		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();
		try {
			s.append("<input type=\"hidden\" name=\"").append(nome).append(
					"\" value=\"").append(valor).append("\">");
			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Cria uma linha na tabela contendo um campo texto com label.<br>
	 * 
	 * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.2, 21/03/2007
         * @param nome
         * @param label
         * @param size
         * @param maxlength
         * @param valor
         * @param dica
	 */
	public void criaInputText(String nome, String label, String size,
			String maxlength, String valor, String dica) {
		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();

		String atribInicio = Pagina.getParamStr(request, nome);
		if(atribInicio == null) {
			atribInicio = "";
		}

		valor = atribInicio;
		
		

		try {

			if (atributo.iGetTamanhoConteudoAtrib() != null) {
				maxlength = String.valueOf(atributo.iGetTamanhoConteudoAtrib()
						.intValue());
			}

			// inicia a linha
			s.append("<TR><TD class=\"label\">");

			// adiciona o texto
			s.append(label);

			s.append("</TD>").append("<TD nowrap>").append(
					"<input type=\"text\" name=\"").append(nome).append(
					"\" size=\"").append(size).append("\" value=\"").append(
					valor).append("\" maxlength=\"").append(maxlength).append(
					"\"");

			if (getBloquearCampo())
				s.append(" disabled");

			s.append(">");

			// Aplica a dica de uso da linha, caso ela exista no cadastro.
			// Mantis #8688. Por Rogério (21/03/2007)
			if (dica != null && !"".equals(dica))
				s.append(Util.getTagDica(nome, this.getContextPath(), dica));

			s.append("</TD></TR>");
			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Cria uma linha no formulário com campo não editável para exibição de
	 * textos. <br>
	 * 
	 * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.2, 22/03/2007
         * @param nome
         * @param label
         * @param maxlength
         * @param valor
         * @param size
         * @param dica
	 */
	public void criaLabelText(String nome, String label, String size,
			String maxlength, String valor, String dica) {
		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();

		try {
			s.append("<TR><TD class=\"label\">");
			s.append(label);
			s.append("</TD>").append("<TD>").append(valor);

			// Aplica a dica de uso da linha, caso ela exista no cadastro.
			// Mantis #8688. Por Rogério (22/03/2007)
			if (dica != null && !"".equals(dica))
				s.append(Util.getTagDica(nome, this.getContextPath(), dica));

			s.append("</TD></TR>");

			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Cria uma linha no formulário para um campo text para receber dados no
	 * formato de moeda. <br>
	 * 
	 * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.2, 22/03/2007
         * @param nome
         * @param label
         * @param size
         * @param maxlength
         * @param valor
         * @param dica
	 */
	public void criaInputTextMoeda(String nome, String label, String size,
			String maxlength, String valor, String dica) {
		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();

		
		
		String atribInicio = Pagina.getParamStr(request, nome + "_Inicio");
		if(atribInicio == null) {
			atribInicio = "";
		}

		valor = atribInicio;
		
		

		try {
			s.append("<TR><TD class=\"label\">");
			s.append(label);
			s.append("</TD>");
			s.append("<TD nowrap>");
			/*
			 * s.append("<input type=\"text\"
			 * onkeydown=\"formataMoeda(this,event);\" name=\""
			 */
			s.append("<input type=\"text\" name=\"").append(nome + "_Inicio")
					.append("\" size=\"").append(size).append("\" value=\"")
					.append(valor).append("\" maxlength=\"").append(maxlength)
					.append("\"");

			
			String atribFim = Pagina.getParamStr(request, nome + "_Fim");
			if(atribFim == null) {
				atribFim = "";
			}

			valor = atribFim;	
			
			if (getBloquearCampo())
				s.append(" disabled");

			// Limite superior para tratar valores como intervalo
			s.append("<!--/TD>");
			s.append("<TD class=\"label\" colspan=\"3\"-->");
			s.append(" a ");
			s.append("<!--/TD>").append("<TD nowrap-->").append(
					"<input type=\"text\" name=\"").append(nome + "_Fim")
					.append("\" size=\"11\" value=\"").append(valor).append(
							"\" maxlength=\"10\"");

			s.append(">");

			// Aplica a dica de uso da linha, caso ela exista no cadastro.
			// Mantis #8688. Por Rogério (22/03/2007)
			if (dica != null && !"".equals(dica))
				s.append(Util.getTagDica(nome, this.getContextPath(), dica));

			s.append("</TD></TR>");
			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Cria uma linha no formulário com campo text para receber datas. <br>
	 * 
	 * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.2, 22/03/2007
         * @param nome
         * @param label
         * @param valor
         * @param dica
	 */
	public void criaInputTextData(String nome, String label, String valor,
			String dica) {
		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();

		String atribInicio = Pagina.getParamStr(request, nome + "_Inicio");
		if(atribInicio == null) {
			atribInicio = "";
		}

		valor = atribInicio;

		try {
			s.append("<TR><TD class=\"label\">");
			s.append(label);
			s.append("</TD>");
			s.append("<TD nowrap>");
			s.append("<input type=\"text\" name=\"");
			s.append(nome + "_Inicio");
			s.append("\" size=\"11\" value=\"");

		
			s.append(valor);
					s
					.append("\" maxlength=\"10\" onkeyup=\"mascaraData(event, this);\"");
			if (getBloquearCampo()) {
				s.append(" disabled >");
			} else {// Mantis: 8210 - Componente de Calendário
				s
						.append(">		<img  style=\"cursor:pointer;top:4px;\" title=\"Selecione a data\" src=\"../images/icone_calendar.gif\" onclick=\"open_calendar('data', document.getElementsByName('"
								+ nome
								+ "_Inicio')[0], '','../calendario/calendario.jsp')\" "
								+ this.getDesabilitar()
								+ " "
								+ this.getDesabilitar() + ">");
			}

			// Limite superior para tratar datas como intervalo
			
			String atribFim = Pagina.getParamStr(request, nome + "_Fim");
			if(atribFim == null) {
				atribFim = "";
			}

			valor = atribFim;

			s.append(" a ");
			s.append("<input type=\"text\" name=\"");
			s.append(nome + "_Fim");
			s.append("\" size=\"11\" value=\"");
			s.append(valor);
			
			s
					.append("\" maxlength=\"10\" onkeyup=\"mascaraData(event, this);\"");
			if (getBloquearCampo()) {
				s.append(" disabled >");
			} else { 
				s
						.append(">		<img  style=\"cursor:pointer;top:4px;\" title=\"Selecione a data\" src=\"../images/icone_calendar.gif\" onclick=\"open_calendar('data', document.getElementsByName('"
								+ nome
								+ "_Fim')[0], '','../calendario/calendario.jsp')\" "
								+ this.getDesabilitar()
								+ " "
								+ this.getDesabilitar() + ">");

			}

			

			// Aplica a dica de uso da linha, caso ela exista no cadastro.
			// Mantis #8688. Por Rogério (22/03/2007)
			if (dica != null && !"".equals(dica))
				s.append(Util.getTagDica(nome, this.getContextPath(), dica));

			s.append("</TD>");
			s.append("</TR>");
			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Cria linha no formulário com campo Text Area para entrada de longos
	 * textos.<br>
	 * 
	 * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.3, 22/03/2007
         * @param nome
         * @param rows
         * @param label
         * @param cols
         * @param valor
         * @param dica
	 */
	public void criaTextArea(String nome, String label, String rows,
			String cols, String valor, String dica) {
		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();


		String atribInicio = Pagina.getParamStr(request, nome);
		if(atribInicio == null) {
			atribInicio = "";
		}

		valor = atribInicio;
			

		try {
			s.append("<TR><TD class=\"label\">");

			s.append(label).append("</TD>");

			s.append("<TD>").append("<div style=\"float: left;\">").append(
					"<textarea name=\"").append(nome).append("\" rows=\"")
					.append(rows).append("\" cols=\"").append(cols);

			String tam = "2000";
			if (atributo.iGetTamanhoConteudoAtrib() != null) {
				tam = String.valueOf(atributo.iGetTamanhoConteudoAtrib()
						.intValue());
			}
			s.append("\" onkeyup=\"return validaTamanhoLimite(this, " + tam
					+ ");\"");
			if (getBloquearCampo()) {
				s
						.append(" style=\"background-color:#FFF;color:#999999;\" readonly=\"readonly\"");
			}
			s.append(">")

			.append(valor)

			.append("</textarea>").append(
					"</div><div style=\"float: left;\"><br><br>");

			// Aplica a dica de uso da linha, caso ela exista no cadastro.
			// Mantis #8688. Por Rogério (22/03/2007)
			if (dica != null && !"".equals(dica))
				s.append(Util.getTagDica(nome, this.getContextPath(), dica));

			s.append("</div>").append("</TD></TR>");

			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Cria script de validação em JS para Area.<br>
	 * 
	 * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.2, 22/03/2007
         * @param areas
	 */
	public void criaJScriptArea(List areas) {
		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();
		AreaAre area;
		SubAreaSare subArea;
		List lSubAreas = new ArrayList(0);
		try {
			s.append("\n<script language=\"javascript\">\n");
			s.append("aSubArea = new Array(").append(areas.size()).append(1)
					.append(");\n");

			s.append("for (var i = 0; i < aSubArea.length; ++i) { \n");
			s.append("	aSubArea[i] = new Array();\n");
			s.append("}\n");
			s.append("aSubArea[0][0] = new Option('Selecione uma Área','');\n");

			for (int i = 0; i < areas.size(); i++) {
				area = (AreaAre) areas.get(i);
				s.append("aSubArea[").append(i + 1).append(
						"][0] = new Option('');\n");
				lSubAreas.clear();
				// lSubAreas.addAll(area.getSubAreaSares());
				int indiceSubArea = 1;
				for (int j = 0; j < lSubAreas.size(); j++) {
					subArea = (SubAreaSare) lSubAreas.get(j);
					if ("S".equals(subArea.getIndAtivoSare()))
						s.append("aSubArea[").append(i + 1).append("][")
								.append(indiceSubArea++).append(
										"] = new Option('").append(
										subArea.getNomeSare()).append("','")
								.append(subArea.getCodSare().toString())
								.append("');\n");
				}
			}
			s.append("</script>\n");
			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Cria linha no formulário com um Div para carregar páginas via Ajax.
	 * 
	 * @param nome
	 * @param label
	 * @param dica
	 */
	public void criaDiv(String nome, String label, String dica) {
		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();

		try {
			s.append("<TR><TD class=\"label\">");
			s.append(label).append("</TD>");

			s.append("<TD>").append("<div id=\"").append(nome).append(
					"\" style=\"float: left;\"></div>").append(
					"<div style=\"float: left;\">");
			// Aplica a dica de uso da linha, caso ela exista no cadastro.
			// Mantis #8688. Por Rogério (22/03/2007)

			if (dica != null && !"".equals(dica))
				s.append(Util.getTagDica(nome, this.getContextPath(), dica));

			s.append("</div>").append("</TD></TR>");

			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

	/**
	 * Monta final de tag.<br>
	 * 
	 * @author n/c, rogerio
	 * @since 0.1, n/c
	 * @version 0.1, n/c
	 * @return int
	 * @throws JspException
	 */
	public int doEndTag() throws JspException {
		return Tag.EVAL_PAGE;
	}

	/**
	 * Atribui valor especificado para PageContext page.<br>
	 * 
         * @param arg0
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setPageContext(PageContext arg0) {
		this.page = arg0;
	}

	/**
	 * 
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param arg0
	 */
	public void setParent(Tag arg0) {
	}

	/**
	 * Retorna Tag null.<br.
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Tag
	 */
	public Tag getParent() {
		return null;
	}

	/**
	 * Retorna PageContext page.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return PageContext - (Returns the page)
	 */
	public PageContext getPage() {
		return page;
	}

	/**
	 * Atribui valor especificado para PageContext page.<br>
	 * 
         * @param page
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setPage(PageContext page) {
		this.page = page;
	}

	/**
	 * 
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void release() {

	}

	/**
	 * Retorna ObjetoEstrutura atributo.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return ObjetoEstrutura - (Returns the atributo)
	 */
	public ObjetoEstrutura getAtributo() {
		return atributo;
	}

	/**
	 * Atribui valor especificado para ObjetoEstrutura atributo.<br>
	 * 
         * @param atributo
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setAtributo(ObjetoEstrutura atributo) {
		this.atributo = atributo;
	}

	/**
	 * Retorna Boolean desabilitar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Boolean - (Returns the desabilitar)
	 */
	public Boolean getDesabilitar() {
		return desabilitar;
	}

	/**
	 * Atribui valor especificado para Boolean desabilitar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param desabilitar
	 */
	public void setDesabilitar(Boolean desabilitar) {
		this.desabilitar = desabilitar;
	}

	/**
	 * Retorna ItemEstruturaIett itemEstrutura.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return ItemEstruturaIett - (Returns the itemEstrutura)
	 */
	public ItemEstruturaIett getItemEstrutura() {
		return itemEstrutura;
	}

	/**
	 * Atribui valor especificado para ItemEstruturaIett itemEstrutura.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param itemEstrutura
	 */
	public void setItemEstrutura(ItemEstruturaIett itemEstrutura) {
		this.itemEstrutura = itemEstrutura;
	}

	/**
	 * Retorna SegurancaECAR seguranca.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return SegurancaECAR - (Returns the seguranca)
	 */
	public SegurancaECAR getSeguranca() {
		return seguranca;
	}

	/**
	 * Atribui valor especificado para SegurancaECAR seguranca.<br>
	 * 
         * @param seguranca
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setSeguranca(SegurancaECAR seguranca) {
		this.seguranca = seguranca;
	}

	/**
	 * Retorna Boolean exibirBotoes.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Boolean
	 */
	public Boolean getExibirBotoes() {
		return exibirBotoes;
	}

	/**
	 * Atribui valor especificado para Boolean exibirBotoes.<br>
	 * 
         * @param exibirBotoes
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setExibirBotoes(Boolean exibirBotoes) {
		this.exibirBotoes = exibirBotoes;
	}

	/**
	 * Retorna String contextPath.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * Atribui valor especificado para String contextPath.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param contextPath
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	/**
	 * Verifica se a interface deve apresentar o campo bloqueado ou
	 * desbloqueado. True caso deva bloquear o campo e false caso deva
	 * desbloquear
	 * 
	 * @return
	 */
	public Boolean getBloquearCampo() {
		// Primeiro verifica se a interface é de consulta. neste caso, deve
		// aparecer bloqueado.
		if (getDesabilitar() != null && getDesabilitar()) {
			return true;
		}
		// caso a interface não seja de consulta, deve verificar se o
		// planejamento esteja bloqueado.
		if (getItemEstrutura().getIndBloqPlanejamentoIett() != null
				&& "S".equals(getItemEstrutura().getIndBloqPlanejamentoIett())) {
			// Se o planejamento está bloqueado, é verificado a configuração do
			// atributo para
			// ver se ele pode ser editado mesmo com o planejamento bloqueado.
			if (atributo.iGetBloqueado()) {
				return true;
			}
		}
		// Por default, o campo pode ser editado.
		return false;

	}

        /**
         *
         * @return
         */
        public String getCodEstrutura() {
		return codEstrutura;
	}

        /**
         *
         * @param codEstrutura
         */
        public void setCodEstrutura(String codEstrutura) {
		this.codEstrutura = codEstrutura;
	}

        /**
         *
         * @return
         */
        public EstruturaEtt getEstrutura() {
		return estrutura;
	}

        /**
         *
         * @param estrutura
         */
        public void setEstrutura(EstruturaEtt estrutura) {
		this.estrutura = estrutura;
	}

        /**
         *
         * @return
         */
        public HttpServletRequest getRequest() {
		return request;
	}

        /**
         *
         * @param request
         */
        public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

        /**
         *
         * @return
         */
        public Boolean getTransformarComboBoxListaChecks() {
		return transformarComboBoxListaChecks;
	}

        /**
         *
         * @param transformarComboBoxListaChecks
         */
        public void setTransformarComboBoxListaChecks(Boolean transformarComboBoxListaChecks) {
		this.transformarComboBoxListaChecks = transformarComboBoxListaChecks;
	}

        /**
         *
         * @return
         */
        public Boolean getTransformarRadioListaChecks() {
		return transformarRadioListaChecks;
	}

        /**
         *
         * @param transformarRadioListaChecks
         */
        public void setTransformarRadioListaChecks(Boolean transformarRadioListaChecks) {
		this.transformarRadioListaChecks = transformarRadioListaChecks;
	}


	
	

}