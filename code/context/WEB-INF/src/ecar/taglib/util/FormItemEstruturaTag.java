/*
 * Criado em 28/12/2004
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
import ecar.dao.ItemEstruturaDao;
import ecar.dao.OrgaoDao;
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
import ecar.pojo.historico.HistoricoItemEstruturaIett;
import ecar.util.Dominios;
/** 
 * Modificado em 08/03/05 por garten.<br>
 * As referencias para AtributoEstruturaTela foram subtituidas por ObjetoEstrutura.<br>
 * 
 *  @author felipev, garten
 */
public class FormItemEstruturaTag implements Tag {

	ValidaPermissao validaPermissao = new ValidaPermissao();
    private ObjetoEstrutura atributo;
    private ItemEstruturaIett itemEstruturaIett;
    private HistoricoItemEstruturaIett historicoItemEstruturaIett;
    private Boolean desabilitar;
	private SegurancaECAR seguranca;
	private Boolean exibirBotoes = new Boolean(true);		 

    private PageContext page = null;
    private String contextPath = null;
    
    private UsuarioDao usu = null;
    
    private Boolean ehHistorico = Boolean.FALSE;
    
    private Boolean hidden = Boolean.FALSE;
    
    private String codigo = "";
    
    private HttpServletRequest request;
    
    
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
            if (atributo.iGetTipo() == EstruturaAtributoEttat.class){
            	
            	String nomeMetodo = "geraHTML" + Util.primeiraLetraToUpperCase(atributo.iGetNome());
            	if(atributo.iGetGrupoAtributosLivres() != null){
            		if (getEhHistorico().booleanValue()){
            			nomeMetodo = "geraHTMLAtributoLivreHistorico";
            		} else {
            			nomeMetodo = "geraHTMLAtributoLivre";
            		}
            		
            	}
            	
            	if(atributo.iGetIndOpcional() == null || atributo.iGetIndOpcional().booleanValue() == false){
        			// Mantis 6514: para atributos não opcionais verificar pelo campo "sequencia de apresentacao em telas de informação"
            		if(atributo.iGetSequenciaCampoEmTela() != null && atributo.iGetSequenciaCampoEmTela().intValue() != 0){
                		this.getClass().getMethod(nomeMetodo, null).invoke(this, null);
            		}
            	}
            	else {
            		this.getClass().getMethod(nomeMetodo, null).invoke(this, null);
            	}
            }
            else {
               geraHTMLPesquisaFuncaoAcompanhamento();
            }
        } catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
        return Tag.EVAL_BODY_INCLUDE;
    }

    /**
     * Gera html pesquisa funï¿½ï¿½o de acompanhamento.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLPesquisaFuncaoAcompanhamento() {
    	Long codIett = null;
    	if (ehHistorico.booleanValue()){
    		codIett = ((HistoricoItemEstruturaIett) getItem()).getCodIett();
    	} else {
    		codIett = ((ItemEstruturaIett) getItem()).getCodIett();
    	}
        try {
			String codigo = "";
			String valor = "";
			boolean indAtivoUsu = true;
			if(codIett != null){
				codigo = atributo.iGetValorCodFk(getItem());
				valor = atributo.iGetValor(getItem());
			}
			if (codigo != null && !"".equals(codigo) && codigo.startsWith("U") && !getEhHistorico().booleanValue()){
				indAtivoUsu = usu.verificarUsuarioAtivo(Long.valueOf(codigo.substring(1)));
			}
			if(!hidden){
	            criaPesquisa(atributo.iGetNome() + this.getCodigo(), 
	            			indAtivoUsu,
	                    	atributo.iGetLabel(),
	                    	"ecar.popup.PopUpUsuarioEGrupo",
	                    	"50",
	                    	codigo, 
	                    	valor, 
	                    	"100",
	                    	atributo.iGetDica());
			}else{
	            criaPesquisaFA(atributo.iGetNome() + this.getCodigo(), 
            			indAtivoUsu,
                    	atributo.iGetLabel(),
                    	"ecar.popup.PopUpUsuarioEGrupo",
                    	"50",
                    	codigo, 
                    	valor, 
                    	"100",
                    	atributo.iGetDica());
            }
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
	 * @version N/C
     */
    public void geraHTMLNomeIett() {
        try {
        	if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
        		criaTextArea("nomeIett" + this.getCodigo(), atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getItem()), atributo.iGetDica());
        	} else {
                criaInputText("nomeIett" + this.getCodigo(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetValor(getItem()), atributo.iGetDica());
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
            criaInputText("siglaIett" + this.getCodigo(), atributo.iGetLabel(), "12", "10", atributo.iGetValor(getItem()), atributo.iGetDica());
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html Descriï¿½ï¿½oIett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDescricaoIett() {
        try {
        	if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
        		criaTextArea("descricaoIett" + this.getCodigo(), atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getItem()), atributo.iGetDica());
        	} else {
                criaInputText("descricaoIett" + this.getCodigo(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetValor(getItem()), atributo.iGetDica());
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
        		criaTextArea("origemIett" + this.getCodigo(), atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getItem()), atributo.iGetDica());
        	} else {
                criaInputText("origemIett" + this.getCodigo(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetValor(getItem()), atributo.iGetDica());
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
        		criaTextArea("objetivoGeralIett" + this.getCodigo(), atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getItem()), atributo.iGetDica());
        	} else {
                criaInputText("objetivoGeralIett" + this.getCodigo(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetValor(getItem()), atributo.iGetDica());
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
        		criaTextArea("objetivoEspecificoIett" + this.getCodigo(), atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getItem()), atributo.iGetDica());
        	} else {
                criaInputText("objetivoEspecificoIett" + this.getCodigo(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetValor(getItem()), atributo.iGetDica());
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
        		criaTextArea("beneficiosIett" + this.getCodigo(), atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getItem()), atributo.iGetDica());
        	} else {
                criaInputText("beneficiosIett" + this.getCodigo(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetValor(getItem()), atributo.iGetDica());
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
            criaInputTextData("dataInicioIett" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValor(getItem()), atributo.iGetDica());
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
            criaInputTextData("dataTerminoIett" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValor(getItem()), atributo.iGetDica());
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
            criaRadio(atributo.iGetNome() + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValor(getItem()), opcoes, atributo.iGetDica());
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
            if(!"".equals(atributo.iGetValor(getItem())))				
                valor = Pagina.trocaNullNumeroDecimalSemMilhar(Double.valueOf(atributo.iGetValor(getItem())));
            
            criaInputTextMoeda("valPrevistoFuturoIett" + this.getCodigo(), atributo.iGetLabel(), "12", "30", valor, atributo.iGetDica());
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
    }

    /**
     * Gera html Data inclusï¿½o Iett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataInclusaoIett() {
    	try{
    		if (atributo.iGetIndOpcional() == null || atributo.iGetIndOpcional().booleanValue() == false){
    			// Mantis 6514: para atributos nï¿½o opcionais verificar pelo campo "sequencia de apresentacao em telas de informaï¿½ï¿½o"
    			if(atributo.iGetSequenciaCampoEmTela() != null && atributo.iGetSequenciaCampoEmTela().intValue() != 0){
	    			criaLabelText(atributo.iGetNome() + this.getCodigo(), atributo.iGetLabel(), "15", "15", atributo.iGetValor(getItem()), atributo.iGetDica());
    			}
    		}
    		else {
	    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
	    			criaLabelText(atributo.iGetNome() + this.getCodigo(), atributo.iGetLabel(), "15", "15", atributo.iGetValor(getItem()), atributo.iGetDica());
	    		}
    		}
    	}
    	catch (ECARException e) {
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
    	try{
    		if (atributo.iGetIndOpcional() == null || atributo.iGetIndOpcional().booleanValue() == false){
    			// Mantis 6514: para atributos nï¿½o opcionais verificar pelo campo "sequencia de apresentacao em telas de informaï¿½ï¿½o"
    			if(atributo.iGetSequenciaCampoEmTela() != null && atributo.iGetSequenciaCampoEmTela().intValue() != 0){
        			criaLabelText(atributo.iGetNome() + this.getCodigo(), atributo.iGetLabel(), "50", "50", atributo.iGetValor(getItem()), atributo.iGetDica());
    			}
    		}
    		else {
	    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
	    			criaLabelText(atributo.iGetNome() + this.getCodigo(), atributo.iGetLabel(), "50", "50", atributo.iGetValor(getItem()), atributo.iGetDica());
	    		}
    		}
    	}
    	catch (ECARException e) {
    		Logger logger = Logger.getLogger(this.getClass());
    		logger.error(e);
    	}
    }

    /**
     * Gera html data ultima manutenï¿½ï¿½o Iett.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataUltManutencaoIett() {
    	try{
    		if (atributo.iGetIndOpcional() == null || atributo.iGetIndOpcional().booleanValue() == false){
    			// Mantis 6514: para atributos nï¿½o opcionais verificar pelo campo "sequencia de apresentacao em telas de informaï¿½ï¿½o"
    			if(atributo.iGetSequenciaCampoEmTela() != null && atributo.iGetSequenciaCampoEmTela().intValue() != 0){
        			criaLabelText(atributo.iGetNome() + this.getCodigo(), atributo.iGetLabel(), "15", "15", atributo.iGetValor(getItem()), atributo.iGetDica());
    			}
    		}
    		else {
	    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
	    			criaLabelText(atributo.iGetNome() + this.getCodigo(), atributo.iGetLabel(), "15", "15", atributo.iGetValor(getItem()), atributo.iGetDica());
	    		}
    		}
    	}
    	catch (ECARException e) {
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
    	try{
    		if (atributo.iGetIndOpcional() == null || atributo.iGetIndOpcional().booleanValue() == false){
    			// Mantis 6514: para atributos nï¿½o opcionais verificar pelo campo "sequencia de apresentacao em telas de informaï¿½ï¿½o"
    			if(atributo.iGetSequenciaCampoEmTela() != null && atributo.iGetSequenciaCampoEmTela().intValue() != 0){
        			criaLabelText(atributo.iGetNome() + this.getCodigo(), atributo.iGetLabel(), "50", "50", atributo.iGetValor(getItem()), atributo.iGetDica());
    			}
    		}
    		else {
	    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
	    			criaLabelText(atributo.iGetNome() + this.getCodigo(), atributo.iGetLabel(), "50", "50", atributo.iGetValor(getItem()), atributo.iGetDica());
	    		}
    		}
    	}
    	catch (ECARException e) {
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
            criaInputTextData("dataR1" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValor(getItem()), atributo.iGetDica());
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
            criaInputTextData("dataR2" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValor(getItem()), atributo.iGetDica());
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
            criaInputTextData("dataR3" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValor(getItem()), atributo.iGetDica());
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
            criaInputTextData("dataR4" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValor(getItem()), atributo.iGetDica());
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
            criaInputTextData("dataR5" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValor(getItem()), atributo.iGetDica());
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
        		criaTextArea("descricaoR1" + this.getCodigo(), atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getItem()), atributo.iGetDica());
        	} else {
                criaInputText("descricaoR1" + this.getCodigo(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetValor(getItem()), atributo.iGetDica());
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
        		criaTextArea("descricaoR2" + this.getCodigo(), atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getItem()), atributo.iGetDica());
        	} else {
                criaInputText("descricaoR2" + this.getCodigo(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetValor(getItem()), atributo.iGetDica());
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
        		criaTextArea("descricaoR3" + this.getCodigo(), atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getItem()), atributo.iGetDica());
        	} else {
                criaInputText("descricaoR3" + this.getCodigo(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetValor(getItem()), atributo.iGetDica());
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
        		criaTextArea("descricaoR4" + this.getCodigo(), atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getItem()), atributo.iGetDica());
        	} else {
                criaInputText("descricaoR4" + this.getCodigo(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetValor(getItem()), atributo.iGetDica());
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
        		criaTextArea("descricaoR5" + this.getCodigo(), atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getItem()), atributo.iGetDica());
        	} else {
                criaInputText("descricaoR5" + this.getCodigo(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetValor(getItem()), atributo.iGetDica());
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
    	AreaAre area = null;
        try {
        	List areas = null;
        	if (getEhHistorico().booleanValue()){
        		area = getHistoricoItemEstruturaIett().getAreaAre();
        		areas = new ArrayList();
        		if (area != null){
        			areas.add(area);
        		}
        	} else {
                area = new AreaAre();
                area.setIndAtivoAre("S");
        		areas = new Dao().pesquisar(area, new String[] { atributo.iGetNomeFk(), "asc" });
        	}
            
            List options = new ArrayList();
            Iterator it = areas.iterator();
            while (it.hasNext()) {
                area = (AreaAre) it.next();
                options.add(new String[] { area.getCodAre().toString(), Util.invocaGet(area, atributo.iGetNomeFk()).toString() });
            }
            
            //criaSelect("areaAre", atributo.iGetLabel(), atributo.iGetValorCodFk(getItem()), options, "onChange=\"updateSub(this.selectedIndex, this.form.subAreaSare);\"");
            criaSelect("areaAre" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValorCodFk(getItem()), options, "", atributo.iGetDica());
            
            //criaJScriptArea(areas);
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
        SubAreaSare sArea = null;
        
        try {
        	List sAreas = null;
            if (getEhHistorico().booleanValue()){
            	sArea = getHistoricoItemEstruturaIett().getSubAreaSare();
            	sAreas = new ArrayList();
            	if (sArea != null){
            		sAreas.add(sArea);
            	}
            } else {
            	sArea = new SubAreaSare();
            	sArea.setIndAtivoSare("S");
            	sAreas = new Dao().pesquisar(sArea, new String[] { atributo.iGetNomeFk(), "asc" });
            }
        	
            List options = new ArrayList();
            Iterator it = sAreas.iterator();
            while (it.hasNext()) {
                sArea = (SubAreaSare) it.next();
                options.add(new String[] { sArea.getCodSare().toString(), Util.invocaGet(sArea, atributo.iGetNomeFk()).toString() });
            }
            criaSelect("subAreaSare" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValorCodFk(getItem()), options, "", atributo.iGetDica());
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
    public void geraHTMLUnidadeOrcamentariaUO(){
    	/*
        UnidadeOrcamentariaUO unidade = new UnidadeOrcamentariaUO();
        unidade.setIndAtivoUo("S");
        try {
            List unidades = new Dao().pesquisar(unidade, new String[] { atributo.iGetNomeFk(), "asc" });
            List options = new ArrayList();
            Iterator it = unidades.iterator();
            while (it.hasNext()) {
                unidade = (UnidadeOrcamentariaUO) it.next();
                options.add(new String[] { unidade.getCodUo().toString(), Util.invocaGet(unidade, atributo.iGetNomeFk()).toString() });
            }
            criaSelect("unidadeOrcamentariaUo", atributo.iGetLabel(), atributo.iGetValorCodFk(getItem()), options, "", atributo.iGetDica());
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
        */
    	try {
    		if (getEhHistorico().booleanValue()){
        		UnidadeOrcamentariaUO unidadeOrcamentariaUO = getHistoricoItemEstruturaIett().getUnidadeOrcamentariaUO();
            	List uos = new ArrayList();
            	if (unidadeOrcamentariaUO != null){
            		uos.add(unidadeOrcamentariaUO);
            	}
            	
            	List options = new ArrayList();
                Iterator it = uos.iterator();
                while (it.hasNext()) {
                	unidadeOrcamentariaUO = (UnidadeOrcamentariaUO) it.next();
                    options.add(new String[] { unidadeOrcamentariaUO.getCodUo().toString(), Util.invocaGet(unidadeOrcamentariaUO, atributo.iGetNomeFk()).toString() });
                }
                criaSelect("unidadeOrcamentariaUo" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValorCodFk(getItem()), options, "", atributo.iGetDica());
            	
        	} else {
        		criaDiv("unidadeOrcamentariaDiv" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetDica());
           		
           		String disabled = "";
           		
           		if (getBloquearCampo())
           			disabled = "disabled";
           		
           		//Indica se o campo unidadeOrcamentaria está ou não bloqueado
           		criaInputHidden(atributo.iGetNome() + "Hidden" + this.getCodigo(), disabled);
        	}
    	} catch (ECARException e) {
    		Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
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
        OrgaoOrg orgao = null;
        OrgaoDao orgaDao = new OrgaoDao(null);
        try {
            List orgaos = null; 
            if (getEhHistorico().booleanValue()){
            	orgao = getHistoricoItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel1Iett();
            	orgaos = new ArrayList();
            	if (orgao != null){
            		orgaos.add(orgao);
            	}
            } else {
            	orgao = new OrgaoOrg();
            	orgao.setIndAtivoOrg("S");
//            	orgaos = new Dao().pesquisar(orgao, new String[] {atributo.iGetNomeFk(), "asc" });
            	
            	//Caso o itemEstrutura possua o codigo(PK) então o fluxo é de alteração  
            	if (getItemEstruturaIett() != null && getItemEstruturaIett().getCodIett() != null){
            		orgaos = orgaDao.consultarOrgaosAtivosOuAssociadoItem(getItemEstruturaIett());
            	} else {//Caso o itemEstrutura não possua o codigo(PK) então o fluxo é de inclusão 
            		orgaos = orgaDao.consultarOrgaosAtivosOuAssociadoItem(null);
            	}
            	
            }
            
            List options = new ArrayList();
            Iterator it = orgaos.iterator();
            while (it.hasNext()) {
                orgao = (OrgaoOrg) it.next();
                options.add(new String[] { orgao.getCodOrg().toString(), Util.invocaGet(orgao, atributo.iGetNomeFk()).toString() });
            }
            
            String disabled = (getBloquearCampo()) ? "disabled" : "";
            
            String scripts = "onchange=\"javascript:carregaUnidadeOrc(this.value,'" + disabled + "');\"";
            
            criaSelect("orgaoOrgByCodOrgaoResponsavel1Iett" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValorCodFk(getItem()), options, scripts, atributo.iGetDica());
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
        OrgaoOrg orgao = null;
        
        try {
            List orgaos = null;
            if (getEhHistorico().booleanValue()){
            	orgao = getHistoricoItemEstruturaIett().getOrgaoOrgByCodOrgaoResponsavel2Iett();
            	orgaos = new ArrayList();
            	if (orgao != null){
            		orgaos.add(orgao);
            	}
            } else {
            	orgao = new OrgaoOrg();
            	orgao.setIndAtivoOrg("S");
            	orgaos = new Dao().pesquisar(orgao, new String[] {atributo.iGetNomeFk(), "asc" });
            }
            List options = new ArrayList();
            Iterator it = orgaos.iterator();
            while (it.hasNext()) {
                orgao = (OrgaoOrg) it.next();
                options.add(new String[] { orgao.getCodOrg().toString(), Util.invocaGet(orgao, atributo.iGetNomeFk()).toString() });
            }
            criaSelect("orgaoOrgByCodOrgaoResponsavel2Iett" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValorCodFk(getItem()), options, "", atributo.iGetDica());
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
        PeriodicidadePrdc prd = null;//new PeriodicidadePrdc();
        Long codIett = null;
       
        try {
        	List periodicidades = null;
            if (getEhHistorico().booleanValue()){
            	codIett = getHistoricoItemEstruturaIett().getCodIett();
            	prd = getHistoricoItemEstruturaIett().getPeriodicidadePrdc();
            	periodicidades = new ArrayList();
            	if (prd != null){
            		periodicidades.add(prd);
            	}
            } else {
            	if (getItemEstruturaIett() != null){
            		codIett = getItemEstruturaIett().getCodIett();
            	}
            	prd = new PeriodicidadePrdc();
            	periodicidades = new Dao().pesquisar(prd, new String[] {atributo.iGetNomeFk(), "asc" });
            }
            
            
            List options = new ArrayList();
            Iterator it = periodicidades.iterator();
            while (it.hasNext()) {
                prd = (PeriodicidadePrdc) it.next();
                options.add(new String[] { prd.getCodPrdc().toString(), Util.invocaGet(prd, atributo.iGetNomeFk()).toString() });
            }
            
        	PeriodicidadePrdc perConfig = new ConfiguracaoDao(null).getConfiguracao().getPeriodicidadePrdc();
            if((getItem() == null || codIett == null) && perConfig != null && !getEhHistorico().booleanValue()){
           		criaSelect("periodicidadePrdc" + this.getCodigo(), atributo.iGetLabel(), perConfig.getCodPrdc().toString(), options, "", atributo.iGetDica());
            }
            else {
            	criaSelect("periodicidadePrdc" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValorCodFk(getItem()), options, "", atributo.iGetDica());
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
    	EstruturaEtt estruturaEtt = null;
    	SituacaoSit situacao = null;
    	
    	try {
    		
    		List situacoes = null;
    		
	    	if (getEhHistorico().booleanValue()){
	    		estruturaEtt = getHistoricoItemEstruturaIett().getEstruturaEtt();
	    		situacoes = new ArrayList();
	    		if (getHistoricoItemEstruturaIett().getSituacaoSit() != null){
	    			situacoes.add(getHistoricoItemEstruturaIett().getSituacaoSit());
	    		}
	    	} else {
	    		estruturaEtt = getItemEstruturaIett().getEstruturaEtt();
	    		situacoes = new SituacaoDao(null).getSituacaoByEstrutura(estruturaEtt, new String[] {"descricaoSit","asc"});
	    	}
    	
        	List options = new ArrayList();
        	Iterator it = situacoes.iterator();
        	while(it.hasNext()){
        		situacao = (SituacaoSit) it.next();
        		options.add(new String[] {situacao.getCodSit().toString(), Util.invocaGet(situacao, atributo.iGetNomeFk()).toString() });
        	}
            criaSelect("situacaoSit" + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValorCodFk(getItem()), options, "", atributo.iGetDica());
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     *
     * @throws ECARException
     * @throws JspException
     */
    public void geraHTMLAtributoLivreHistorico() throws ECARException, JspException{
    	
    	if (atributo.iGetGrupoAtributosLivres() != null) {
    		
			JspWriter writer = this.page.getOut();
			
			SisGrupoAtributoSga grupoAtributo =  atributo.iGetGrupoAtributosLivres();
			
			Input input = new Input(writer);
			input.setRequest(request);
			input.setLabel(atributo.iGetLabel());
			input.setTipo(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().intValue());
			input.setObrigatorio(atributo.iGetObrigatorio().booleanValue() ? "S" : "N"); // Obrigatoriedade
			// estï¿½
			// no
			// atributo
			input.setPathRaiz(this.getContextPath());
			input.setLabelObrigatorio("*");
			input.setSize(atributo.iGetTamanhoConteudoAtrib().toString());
			if (getBloquearCampo()) {
				input.setDisabled("S");
			}
			input.setNome("a" + grupoAtributo.getCodSga().toString() + codigo);
			input.setClassLabel("label");
			List aributosLivresSelecionados = new ArrayList();
			if (getHistoricoItemEstruturaIett().getItemEstruturaSisAtributoIettSatbs() != null) {
				Iterator itAtribLivres = getHistoricoItemEstruturaIett().getItemEstruturaSisAtributoIettSatbs().iterator();
				while (itAtribLivres.hasNext()) {
					ItemEstruturaSisAtributoIettSatb atributo = (ItemEstruturaSisAtributoIettSatb) itAtribLivres.next();
					AtributoLivre atributoLivre = new AtributoLivre();
					atributoLivre.setInformacao(atributo.getInformacao());
					atributoLivre.setSisAtributoSatb(atributo.getSisAtributoSatb());
					aributosLivresSelecionados.add(atributoLivre);
				}
			}
			input.setSelecionados(aributosLivresSelecionados);
			input.setSisAtributo((SisAtributoSatb) grupoAtributo.getSisAtributoSatbs().iterator().next());
			//input.setSisAtributo((SisAtributoSatb) pontoCriticoPtc.getPontoCriticoSisAtributoPtcSatbs().iterator().next().getSisAtributoSatb());
			if (atributo.iGetDica() != null){
				input.setDica(atributo.iGetDica());
				input.setContexto(this.getContextPath());
			}
			
			input.doStartTag();
			Options options = new Options(writer);
			List opcoes = new ArrayList();
			String selectedCodSapadrao = "";
			 if(grupoAtributo.getCodSga() != null && grupoAtributo.getCodSga().longValue() != 1){
				 if(grupoAtributo.getSisTipoOrdenacaoSto() != null){
					 //for (Object obj : grupoAtributo.)
					 opcoes.addAll(grupoAtributo.getSisAtributoSatbs()) ;//new SisGrupoAtributoDao(null).getAtributosOrdenados(grupoAtributo);
				 }
			 }
			if (!opcoes.isEmpty()) {
				options.setOptions(opcoes);
				options.setValor("codSatb");
				options.setLabel("descricaoSatb");
				// options.setImagem(getContextPath() + "/images/relAcomp/");
				options.setParent(input);
				options.setNome("a" + grupoAtributo.getCodSga().toString() + codigo);
				if(atributo.iGetDica() != null){
    	    		options.setContexto(this.getContextPath());
    	    		options.setDica(atributo.iGetDica());
    	    	}
				options.doStartTag();
			}
			input.doEndTag();
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
    public void geraHTMLAtributoLivre() throws ECARException, JspException{

    	if(atributo.iGetGrupoAtributosLivres() != null){

        	JspWriter writer = this.page.getOut();

        	SisGrupoAtributoSga grupoAtributo = atributo.iGetGrupoAtributosLivres(); 
            	
        	Input input = new Input(writer);
        	input.setRequest(request);
        	input.setPage(page);
        	input.setLabel(atributo.iGetLabel());
        	input.setTipo(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().intValue());
        	input.setObrigatorio(atributo.iGetObrigatorio().booleanValue() ? "S" : "N"); //Obrigatoriedade estï¿½ no atributo
        	
        	//Adicionado por Josï¿½ Andrï¿½ Fernandes
        	//Setar o path raiz. 26/09/2007
        	input.setPathRaiz(this.getContextPath());
        	
        	        	
        	input.setLabelObrigatorio("*");
        	input.setSize(atributo.iGetTamanhoConteudoAtrib().toString());

        	if (getBloquearCampo()) {
        		input.setDisabled("S");
        	}
        	input.setNome("a" + grupoAtributo.getCodSga().toString());
        	input.setClassLabel("label");
        	
        	List aributosLivresSelecionados = new ArrayList();

        	if(((ItemEstruturaIett)getItem()).getItemEstruturaSisAtributoIettSatbs() != null){
    	    	Iterator itAtribLivres = ((ItemEstruturaIett)getItem()).getItemEstruturaSisAtributoIettSatbs().iterator();
    	    	while(itAtribLivres.hasNext()){
    	    		ItemEstruturaSisAtributoIettSatb atributo = (ItemEstruturaSisAtributoIettSatb) itAtribLivres.next();
    	    		AtributoLivre atributoLivre = new AtributoLivre();
    	    		atributoLivre.setInformacao(atributo.getInformacao());
    	    		atributoLivre.setSisAtributoSatb(atributo.getSisAtributoSatb());
    	    		aributosLivresSelecionados.add(atributoLivre);
    	    	}
        	}
        	
        	input.setSelecionados(aributosLivresSelecionados);
        	input.setSisAtributo((SisAtributoSatb)grupoAtributo.getSisAtributoSatbs().iterator().next());
        	if(atributo.iGetDica() != null){
        		input.setDica(atributo.iGetDica());
        		input.setContexto(this.getContextPath());
        	}
        	
        	input.doStartTag();
        	
        	Options options = new Options(writer);
        	
    		List opcoes = new ArrayList();
    		String selectedCodSapadrao = "";
    		if(grupoAtributo.getCodSga() != null && grupoAtributo.getCodSga().longValue() != 1){
    			if(grupoAtributo.getSisTipoOrdenacaoSto() != null){
    				opcoes = new SisGrupoAtributoDao(null).getAtributosOrdenados(grupoAtributo);
    			}
    		} 
        	
    		if(!opcoes.isEmpty()) {
    	    	options.setOptions(opcoes);
    	    	options.setValor("codSatb");
    	    	options.setLabel("descricaoSatb");
    	    	//options.setImagem(getContextPath() + "/images/relAcomp/");
    	    	options.setParent(input);
    	    	options.setNome("a" + grupoAtributo.getCodSga().toString());
    	    	if(atributo.iGetDica() != null){
    	    		options.setContexto(this.getContextPath());
    	    		options.setDica(atributo.iGetDica());
    	    	}
    	    	options.doStartTag();
    		}
    		
    		input.doEndTag();
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
    	SisGrupoAtributoSga grupoAtributo = null;
    	
    	if (getEhHistorico().booleanValue()){
    		grupoAtributo = getHistoricoItemEstruturaIett().getSisGrupoAtributoNivelPlanejamento();
    	} else {
    		grupoAtributo = new ConfiguracaoDao(null).getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan();
    	}
    		
    	Input input = new Input(writer);
    	input.setRequest(request);
    	input.setLabel(atributo.iGetLabel());
    	input.setTipo(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().intValue());
    	input.setObrigatorio(atributo.iGetObrigatorio().booleanValue() ? "S" : "N"); //Obrigatoriedade estï¿½ no atributo
    	input.setLabelObrigatorio("*");
    	if(getBloquearCampo())
    		input.setDisabled("S");
    	
    	input.setNome("a" + grupoAtributo.getCodSga().toString() + codigo);
    	input.setClassLabel("label");
    	
    	List niveisSelecionados = new ArrayList();
    	List itemEstruturaNivelIettns = null;
    	if (getEhHistorico().booleanValue()){
    		itemEstruturaNivelIettns = new ArrayList(getHistoricoItemEstruturaIett().getItemEstruturaNivelIettns());
    	} else {
    		if (getItemEstruturaIett().getItemEstruturaNivelIettns() != null){
    			itemEstruturaNivelIettns = new ArrayList(getItemEstruturaIett().getItemEstruturaNivelIettns());
    		}
    	}
   		if(itemEstruturaNivelIettns != null){
	    	Iterator itNiveis = itemEstruturaNivelIettns.iterator();
	    	while(itNiveis.hasNext()){
	    		SisAtributoSatb nivel = (SisAtributoSatb) itNiveis.next();
	    		AtributoLivre atributoLivre = new AtributoLivre();
	    		atributoLivre.setSisAtributoSatb(nivel);
	    		niveisSelecionados.add(atributoLivre);
	    	}
    	}
    	
    	input.setSelecionados(niveisSelecionados);
    	input.setSisAtributo((SisAtributoSatb)grupoAtributo.getSisAtributoSatbs().iterator().next());
    	//input.setPathRaiz();
    	//input.setSize();
    	if(atributo.iGetDica() != null){
    		input.setDica(atributo.iGetDica());
    		input.setContexto(this.getContextPath());
    	}
    	
    	input.doStartTag();
    	
    	Options options = new Options(writer);
    	
		List opcoes = new ArrayList();
		if(grupoAtributo.getCodSga() != null && grupoAtributo.getCodSga().longValue() != 1){
			if(grupoAtributo.getSisTipoOrdenacaoSto() != null){
				if (getEhHistorico().booleanValue()){
					opcoes = new ArrayList(grupoAtributo.getSisAtributoSatbs());
				} else {
					opcoes = new SisGrupoAtributoDao(null).getAtributosOrdenados(grupoAtributo);
				}
				
			}
		} 
    	
		List opcoesAtivos = new ArrayList();
		if(!opcoes.isEmpty()) {
			Iterator opcoesIt = opcoes.iterator();
			while(opcoesIt.hasNext()) {
				SisAtributoSatb sisAtributoSatb = (SisAtributoSatb)opcoesIt.next();
				if(sisAtributoSatb.getIndAtivoSatb().equals(Dominios.SIM)) {
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
        	
        	//Verifica Indicador de Usuario Ativo
			String codigo = "";
        	boolean indAtivoUsu = true;
        	if (codigo != null && !"".equals(codigo)){
				indAtivoUsu = usu.verificarUsuarioAtivo(Long.valueOf(codigo));
			}        	
            
        	criaPesquisa(atributo.iGetNome() + this.getCodigo(), indAtivoUsu, atributo.iGetLabel(),
                    "ecar.pojo.UsuarioUsu", "50", atributo.iGetValorCodFk(getItem()), atributo.iGetValor(getItem()), "100", atributo.iGetDica());
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Este Método cria o botão que Permite Ativar e Retirar Monitoramento.<br>
     * Ele cria o botõ, um campo hidden com o flag e um javascript para ser executado no método onclick do botão
     * O javascript verificar o label atual do botão, seta o flag do campo hidden dependendo deste label.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLIndMonitoramentoIett() {
        String labelAtual = "Ativar ";
        try {
			if (!getEhHistorico().booleanValue()){
												
				boolean temPermissao = validaPermissao.permissaoAtivarMonitoramentoItem((ItemEstruturaIett) getItem(), seguranca.getUsuario(), seguranca.getGruposAcesso()); 
				
	            if("S".equals(atributo.iGetValor(getItem()))){
					temPermissao = validaPermissao.permissaoDesativarMonitoramentoItem((ItemEstruturaIett) getItem(), seguranca.getUsuario(), seguranca.getGruposAcesso());
	                labelAtual = "Retirar ";        
	            }
	            if("N".equals(atributo.iGetValor(getItem()))){
					temPermissao = validaPermissao.permissaoAtivarMonitoramentoItem((ItemEstruturaIett) getItem(), seguranca.getUsuario(), seguranca.getGruposAcesso());
	                labelAtual = "Ativar ";        
	            }
	            if("".equals(atributo.iGetValor(getItem()))){
					temPermissao = validaPermissao.permissaoAtivarMonitoramentoItem((ItemEstruturaIett) getItem(), seguranca.getUsuario(), seguranca.getGruposAcesso());
					labelAtual = "Ativar ";
	                ((ItemEstruturaIett)getItem()).setIndMonitoramentoIett("N");
	            }    
	            
	            //Se a estrutura estiver inativa não permite o usuário alterar
	            if (!((ItemEstruturaIett)getItem()).getEstruturaEtt().getIndAtivoEtt().equals("S")){
	            	temPermissao = false;
	            }
				
				if(temPermissao){
					setDesabilitar(Boolean.FALSE);
				}
				else{
					setDesabilitar(Boolean.TRUE);
				}
	            StringBuffer auxScriptValueBtn = new StringBuffer("document.form.bt").append(atributo.iGetNome()).append(".value");
	            StringBuffer auxScriptValueHidden = new StringBuffer("document.form.").append(atributo.iGetNome()).append(".value");
	            StringBuffer auxScriptValueHidden1 = new StringBuffer("'N'");
	            StringBuffer auxScriptValueHidden2 = new StringBuffer("'S'");
	            StringBuffer script = new StringBuffer(" if(")
	            							.append(auxScriptValueHidden)
	            							.append("==")
	            							.append(auxScriptValueHidden2)
	            							.append(") {")
	            							.append(auxScriptValueHidden)
	            							.append("=")
	            							.append(auxScriptValueHidden1)
	            							.append(";")
	            							.append("aoClicarMonitoramento(document.form);this.disabled=true;")
	            							.append("} ")
	            							.append("else { if(")
	            							.append(auxScriptValueHidden)
	            							.append("==")
	            							.append(auxScriptValueHidden1)
	            							.append(") {")
	            							.append(auxScriptValueHidden)
	            							.append("=")
	            							.append(auxScriptValueHidden2)
	            							.append(";aoClicarMonitoramento(document.form);this.disabled=true;")
	            							.append("} }");
	            criaInputButton(labelAtual + atributo.iGetLabel(), atributo.iGetNome() , script.toString());
	            criaInputHidden(atributo.iGetNome(), atributo.iGetValor(getItem()));
	            
			} else {
				if("S".equals(atributo.iGetValor(getItem()))){
					labelAtual = "Retirar ";
				}
				criaInputButton(labelAtual + atributo.iGetLabel(), atributo.iGetNome() , "");
	            criaInputHidden(atributo.iGetNome(), atributo.iGetValor(getItem()));
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
        String labelAtual = "Bloquear ";
        try {
	        if (!getEhHistorico().booleanValue()){
	        	boolean temPermissao = validaPermissao.permissaoBloquearPlanejamentoItem((ItemEstruturaIett) getItem(), seguranca.getUsuario(), seguranca.getGruposAcesso()); 
	            
	            
	            if("S".equals(atributo.iGetValor(getItem()))){
					temPermissao = validaPermissao.permissaoLiberarPlanejamentoItem((ItemEstruturaIett) getItem(), seguranca.getUsuario(), seguranca.getGruposAcesso());
	                labelAtual = "Liberar ";        
	            } else if("".equals(atributo.iGetValor(getItem()))) {
	                ((ItemEstruturaIett) getItem()).setIndBloqPlanejamentoIett("N");
	            }    
				
	            //Se a estrutura estiver inativa não permite o usuário alterar
	            if (!((ItemEstruturaIett)getItem()).getEstruturaEtt().getIndAtivoEtt().equals("S")){
	            	temPermissao = false;
	            }
	            
				if(temPermissao){
					setDesabilitar(Boolean.FALSE);
				}
				else{
					setDesabilitar(Boolean.TRUE);
				}

				StringBuffer auxScriptValueBtn = new StringBuffer("document.form.bt").append(atributo.iGetNome()).append(".value");
	            StringBuffer auxScriptValueHidden = new StringBuffer("document.form.").append(atributo.iGetNome()).append(".value");
	            StringBuffer auxScriptValueHidden1 = new StringBuffer("'N'");
	            StringBuffer auxScriptValueHidden2 = new StringBuffer("'S'");

	            StringBuffer script = new StringBuffer(" if(")
	            							.append(auxScriptValueHidden)
	            							.append("==")
	            							.append(auxScriptValueHidden2)
	            							.append(") {")
	            							.append(auxScriptValueHidden)
	            							.append("=")
	            							.append(auxScriptValueHidden1)
	            							.append(";aoClicarPlanejamento(document.form);this.disabled=true;} ")            							
	            							.append("else { if(")
	            							.append(auxScriptValueHidden)
	            							.append("==")
	            							.append(auxScriptValueHidden1)
	            							.append(") {")            							
	            							.append(auxScriptValueHidden)
	            							.append("=")
	            							.append(auxScriptValueHidden2)
	            							.append(";aoClicarPlanejamento(document.form);this.disabled=true;} }");
	            
	            criaInputButton(labelAtual + atributo.iGetLabel(), atributo.iGetNome() , script.toString());
				
	            criaInputHidden(atributo.iGetNome(), atributo.iGetValor(getItem()));

	        } else {
	        	if("S".equals(atributo.iGetValor(getItem()))){
	        		labelAtual = "Liberar ";
	        	}
	        	criaInputButton(labelAtual + atributo.iGetLabel(), atributo.iGetNome() , "");
	            criaInputHidden(atributo.iGetNome(), atributo.iGetValor(getItem()));
	        }
			            
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
	 * @version N/C
     */
    public void geraHTMLIndModeloIett() {
	    List opcoes = new ArrayList();
	    opcoes.add(new String[] { "S", "Sim" });
	    opcoes.add(new String[] { "N", "Não" });
	    try {
	    	criaRadio(atributo.iGetNome() + this.getCodigo(), atributo.iGetLabel(), atributo.iGetValor(getItem()), opcoes, atributo.iGetDica());
	    } catch (ECARException e) {
	    	Logger logger = Logger.getLogger(this.getClass());
	    	logger.error(e);
	    }
    }

   
    
    /**
     * Gera uma linha do formulï¿½rio, contendo campo de pesquisa. <br>
     * 
     * @author n/c, rogerio
     * @since 0.1, n/c
     * @version 0.2, 22/03/2007 
     * @param nome 
     * @param IndAtivoUsu
     * @param label
     * @param classePesquisa
     * @param size
     * @param maxlength
     * @param valor
     * @param valorText
     * @param dica
     */
    public void criaPesquisaFA(String nome, boolean IndAtivoUsu, String label, String classePesquisa, String size, String valor, String valorText, String maxlength, String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        
        try {
       		// Cria a linha
        	s.append("<TR><TD class=\"label\">");

            // Aplica a marca de campo obrigatï¿½rio
        	if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true)
                s.append("* "); 
        	
        	// Verifica se usuario estah ativo.
        	String imagem_inativa = "";
			if (! IndAtivoUsu){
				imagem_inativa="<img src=\"../../images/icon_usuario_inativo.png\" title=\"Usuário Inativo\">";
			}
        	
            // Aplica o label do campo
        	s.append(label);
        	
            s.append("</TD>")
             .append("<TD>")
             .append("<input type=\"text\" disabled name=\"nome")
             .append(nome)
             .append("\" size=\"")
             .append(size)
             .append("\" value=\"")
             .append(valorText)
             .append("\" maxlength=\"")
             .append(maxlength)
             .append("\"")
             .append(">")
             .append(imagem_inativa)
             .append("<input type=\"hidden\" name=\"")
             .append(nome)
             .append("\" value=\"")
             .append(valor)
             .append("\">");
            
            s.append("<input type=hidden name=\"nome")
            .append(nome+"Original")
            .append("\" value=\"")
            .append(valorText)
            .append("\">");
            
            s.append("<input type=\"hidden\" name=\"")
            .append("codigo" + nome)
            .append("\" value=\"")
            .append(valor)
            .append("\">");
            
            // Adiciona o campo
            if(this.getExibirBotoes().booleanValue() ){
	            s.append("&nbsp;&nbsp;<input type=\"button\" name=\"pesq\" value=\"Pesquisar\" class=\"botao\" ");
	            if (getBloquearCampo())
	                s.append(" disabled");
	            
	            s.append(" onclick=\"popup_pesquisa('")
	             .append(classePesquisa)
	             .append("', 'retorno")
	             .append(nome)
	             .append("');\">")
	             .append("&nbsp;&nbsp;<input type=\"button\" name=\"pesq\" value=\"Limpar\" class=\"botao\" ")
	             .append("onclick=\"document.form.alterou.value='S';document.form.nome")
	             .append(nome)
	             .append(".value=''; document.form.")
	             .append(nome)
	             .append(".value=''\"");
	            
	            
	
	            if (getBloquearCampo())
	                s.append(" disabled");
	            
	            s.append(">");
            }
            
            // Aplica a dica de uso da linha, caso ela exista no cadastro.
            // Mantis #8688. Por Rogï¿½rio (22/03/2007)
            if( dica != null && !"".equals(dica) )
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
            
            s.append("</TD></TR>");
            writer.print(s.toString());
            
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }


    /**
     * Gera uma linha do formulï¿½rio, contendo campo de pesquisa. <br>
     * 
     * @author n/c, rogerio
     * @since 0.1, n/c
     * @version 0.2, 22/03/2007 
     * @param nome 
     * @param IndAtivoUsu
     * @param label
     * @param classePesquisa 
     * @param size
     * @param valor
     * @param maxlength
     * @param dica
     * @param valorText
     */
    public void criaPesquisa(String nome, boolean IndAtivoUsu, String label, String classePesquisa, String size, String valor, String valorText, String maxlength, String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        
        try {
       		// Cria a linha
        	s.append("<TR><TD class=\"label\">");

            // Aplica a marca de campo obrigatï¿½rio
        	if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true)
                s.append("* "); 
        	
        	// Verifica se usuario estah ativo.
        	String imagem_inativa = "";
			if (! IndAtivoUsu){
				imagem_inativa="<img src=\"../../images/icon_usuario_inativo.png\" title=\"Usuário Inativo\">";
			}
        	
            // Aplica o label do campo
        	s.append(label);
        	
            s.append("</TD>")
             .append("<TD>")
             .append("<input type=\"text\" disabled name=\"nome")
             .append(nome)
             .append("\" size=\"")
             .append(size)
             .append("\" value=\"")
             .append(valorText)
             .append("\" maxlength=\"")
             .append(maxlength)
             .append("\"")
             .append(">")
             .append(imagem_inativa)
             .append("<input type=\"hidden\" name=\"")
             .append(nome)
             .append("\" value=\"")
             .append(valor)
             .append("\">");
            
            
            s.append("<input type=hidden name=\"nome")
            .append(nome+"Original")
            .append("\" value=\"")
            .append(valorText)
            .append("\">");
            
            s.append("<input type=\"hidden\" name=\"")
            .append("codigo" + nome)
            .append("\" value=\"")
            .append(valor)
            .append("\">");
            
            // Adiciona o campo
            if(this.getExibirBotoes().booleanValue() ){
	            s.append("&nbsp;&nbsp;<input type=\"button\" name=\"pesq\" value=\"Pesquisar\" class=\"botao\" ");
	            if (getBloquearCampo())
	                s.append(" disabled");
	            
	            s.append(" onclick=\"popup_pesquisa('")
	             .append(classePesquisa)
	             .append("', 'retorno")
	             .append(nome)
	             .append("');\">")
	             .append("&nbsp;&nbsp;<input type=\"button\" name=\"pesq\" value=\"Limpar\" class=\"botao\" ")
	             .append("onclick=\"document.form.alterou.value='S';document.form.nome")
	             .append(nome)
	             .append(".value=''; document.form.")
	             .append(nome)
	             .append(".value=''\"");
	
	            if (getBloquearCampo())
	                s.append(" disabled");
	            
	            s.append(">");
            }
            
            // Aplica a dica de uso da linha, caso ela exista no cadastro.
            // Mantis #8688. Por Rogï¿½rio (22/03/2007)
            if( dica != null && !"".equals(dica) )
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
            
            s.append("</TD></TR>");
            writer.print(s.toString());
            
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria uma linha no formulï¿½rio com um campo SELECT. <br>
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
    public void criaSelect(String nome, String label, String valor, Collection opcoes, String scripts, String dica) {
        
    	JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        
        try {
        	// Inicia a linha
        	s.append("<TR><TD class=\"label\">");

        	// Aplica a marca de campo obrigatï¿½rio
            if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true)
                s.append("* ");
            
            // Aplica o label do campo
            s.append(label);
            
            s.append("</TD>");
            s.append("<TD>");
            s.append("<select name=\"").append(nome).append("\" ").append(scripts);
            if (getBloquearCampo())
                s.append(" disabled");
            s.append(">");
            s.append("<option value=\"\"></option>");
            if (opcoes != null) {
                Iterator it = opcoes.iterator();
                while (it.hasNext()) {
                    String[] chaveValor = (String[]) it.next();
                    s.append("<option value=\"").append(chaveValor[0]).append("\"");
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
            // Mantis #8688. Por Rogï¿½rio (22/03/2007)
            if( dica != null && !"".equals(dica) )
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
            
            s.append("</TD></TR>");
            
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria linha no formulï¿½rio com um campo RADIO BUTTON. <br>
     * 
     * @author n/c, rogerio
     * @since 0.1, n/c
     * @version 0.2, 22/03/2007
     * @param nome 
     * @param label
     * @param valor
     * @param opcoes
     * @param dica
     */
    public void criaRadio(String nome, String label, String valor, Collection opcoes, String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {
       		s.append("<TR><TD class=\"label\">");
       		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true)
                s.append("* ");
            s.append(label);         
            s.append("</TD>");
            s.append("<TD>");
            if (opcoes != null) {
                Iterator it = opcoes.iterator();
                while (it.hasNext()) {
                    String[] chaveValor = (String[]) it.next();
                    s.append("<input type=\"radio\" class=\"form_check_radio\" name=\"").append(nome)
                      .append("\" value=\"").append(chaveValor[0]).append("\"");
                      
                    if (getBloquearCampo())
                        s.append(" disabled");
                    if (chaveValor[0].equals(valor))
                        s.append(" checked ");
                    s.append(">");
                    s.append(chaveValor[1]);                    
                }
                s.append("&nbsp;<input type=\"button\" name=\"buttonLimpar\" value=\"Limpar\" class=\"botao\" ")
                .append("onclick=\"limparRadioButtons(document.getElementsByName('" + nome + "'));\"");
                if (getBloquearCampo())
	    	   		s.append(" disabled ");
                s.append(">");
            }
            
            // Aplica a dica de uso da linha, caso ela exista no cadastro.
            // Mantis #8688. Por Rogï¿½rio (22/03/2007)
            if( dica != null && !"".equals(dica) )
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
            
            s.append("</TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria uma linha no formulï¿½rio com objeto botï¿½o. <br>
     * 
     * @author n/c
     * @since 0.1, n/c
     * @version 0.1, n/c
     * @param label 
     * @param onclick
     * @param name
     */
    public void criaInputButton(String label, String name, String onclick) {
    	if(!this.getExibirBotoes().booleanValue())
    		return;
    	
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {   
            s.append("<TR><TD>&nbsp;</TD><TD>");
            s.append("<input type=\"button\" ");
            /*
             * Regra de bloqueio destes botï¿½es ï¿½ diferente. Deve ficar sempre liberado, menos nos casos
             * que o usuï¿½rio nï¿½o tem permissï¿½o.
             */ 
            if (getDesabilitar() != null && getDesabilitar()) {
                s.append(" disabled ");
            }
            s.append("value=\"").append(label).append("\" name=\"bt").append(name).append("\" onclick=\"")
             .append(onclick).append("\"").append("></TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) { 
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }        
    }

    /**
     * Cria campo do tipo hidden no formulï¿½rio. <br>
     * 
     * @author n/c
     * @since 0.1, n/c
     * @version 0.1, n/c
     * @param nome 
     * @param valor
     */
    public void criaInputHidden(String nome,String valor) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {            
            s.append("<input type=\"hidden\" name=\"").append(nome).append("\" value=\"").append(valor).append("\">");
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
     * @param size
     * @param label 
     * @param maxlength
     * @param valor 
     * @param dica
     */
    public void criaInputText(String nome, String label, String size, String maxlength, String valor, String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s   = new StringBuffer();
        
        try {
        	
        	
        	if(atributo.iGetTamanhoConteudoAtrib() != null && !"".equals(maxlength) && atributo.iGetTamanhoConteudoAtrib().intValue() <= Integer.parseInt(maxlength)){
        		maxlength = String.valueOf(atributo.iGetTamanhoConteudoAtrib().intValue());
        	}
        	
        	// inicia a linha
        	s.append("<TR><TD class=\"label\">");
        	       	
            if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true)
                s.append("* ");
            
            // adiciona o texto
            s.append(label);
            
            s.append("</TD>")
             .append("<TD nowrap>")
             .append("<input type=\"text\" name=\"")
             .append(nome)
             .append("\" size=\"")
             .append(size)
             .append("\" value=\"")
             .append(valor)
             .append("\" maxlength=\"")
             .append(maxlength)
             .append("\"");
            
            if (getBloquearCampo())
            	s.append(" disabled");
            
            s.append(">");

            // Aplica a dica de uso da linha, caso ela exista no cadastro.
            // Mantis #8688. Por Rogï¿½rio (21/03/2007)
            if( dica != null && !"".equals(dica) )
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
            
            s.append("</TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria uma linha no formulï¿½rio com campo nï¿½o editï¿½vel para exibiï¿½ï¿½o de textos. <br>
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
    public void criaLabelText(String nome, String label, String size, String maxlength, String valor, String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {
        	s.append("<TR><TD class=\"label\">");
            s.append(label);
            s.append("</TD>")
             .append("<TD>")
             .append(valor );
            
            // Aplica a dica de uso da linha, caso ela exista no cadastro.
            // Mantis #8688. Por Rogï¿½rio (22/03/2007)
            if( dica != null && !"".equals(dica) )
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
            
            s.append("</TD></TR>");
            
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria uma linha no formulï¿½rio para um campo text para receber dados no formato de moeda. <br>
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
    public void criaInputTextMoeda(String nome, String label, String size, String maxlength, String valor, String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {
       		s.append("<TR><TD class=\"label\">");
            if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true)
                s.append("* ");
            s.append(label);
            s.append("</TD>");
            s.append("<TD nowrap>");
            /*s.append("<input type=\"text\" onkeydown=\"formataMoeda(this,event);\" name=\""*/
            s.append("<input type=\"text\" name=\"")
             .append(nome)
             .append("\" size=\"")
             .append(size)
             .append("\" value=\"")
             .append(valor)
             .append("\" maxlength=\"")
             .append(maxlength)
             .append("\"");
            if (getBloquearCampo())
                s.append(" disabled");
            
            s.append(">");
            
            // Aplica a dica de uso da linha, caso ela exista no cadastro.
            // Mantis #8688. Por Rogï¿½rio (22/03/2007)
            if( dica != null && !"".equals(dica) )
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
            
            s.append("</TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria uma linha no formulï¿½rio com campo text para receber datas. <br>
     * 
     * @author n/c, rogerio
     * @since 0.1, n/c
     * @version 0.2, 22/03/2007
     * @param nome
     * @param label
     * @param valor
     * @param dica
     */
    public void criaInputTextData(String nome, String label, String valor, String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {            
       		s.append("<TR><TD class=\"label\">");          
            if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true) {
                s.append("* ");
            }
            s.append(label);
            s.append("</TD>");
            s.append("<TD nowrap>");
            s.append("<input type=\"text\" name=\"");
            s.append(nome);
            s.append("\" size=\"11\" value=\"");
            s.append(valor);
            s.append("\" maxlength=\"10\" onkeyup=\"mascaraData(event, this);\"");
            if (getBloquearCampo()){
                s.append(" disabled");
            }
            s.append(">");
            
            // Aplica a dica de uso da linha, caso ela exista no cadastro.
            // Mantis #8688. Por Rogï¿½rio (22/03/2007)
            if( dica != null && !"".equals(dica) ) {
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
            }
            
    		if (!this.getBloquearCampo() && nome != null && nome.startsWith(Dominios.PREFIX_DATA)) {
    			s.append("		<img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"open_calendar('data', document.forms[0]."+nome+", '')\" " + this.getBloquearCampo() + " " + this.getBloquearCampo() + ">");
			}
    		
			
            s.append("</TD></TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria linha no formulï¿½rio com campo Text Area para entrada de longos textos.<br>
     * 
     * @author n/c, rogerio
     * @since 0.1, n/c
     * @version 0.3, 22/03/2007
     * @param nome 
     * @param label
     * @param rows
     * @param cols
     * @param valor
     * @param dica
     */
    public void criaTextArea(String nome, String label, String rows, String cols, String valor, String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        
        try {
        	s.append("<TR><TD class=\"label\">");
            if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true)
                s.append("* ");
            
            s.append(label)
             .append("</TD>");
             
            s.append("<TD>")
             .append("<div style=\"float: left;\">")
             .append("<textarea name=\"")
             .append(nome)
             .append("\" rows=\"")
             .append(rows)
             .append("\" cols=\"")
             .append(cols);
            
            String tam = "2000";
            if(atributo.iGetTamanhoConteudoAtrib() != null){
            	tam = String.valueOf(atributo.iGetTamanhoConteudoAtrib().intValue());
            }
            s.append("\" onkeyup=\"return validaTamanhoLimite(this, " + tam + ");\"");
            if (getBloquearCampo()) {
                s.append(" style=\"background-color:#FFF;color:#999999;\" readonly=\"readonly\"");
            }
            s.append(">")
             .append(valor)
             .append("</textarea>")
             .append("</div><div style=\"float: left;\"><br><br>");

            // Aplica a dica de uso da linha, caso ela exista no cadastro.
            // Mantis #8688. Por Rogï¿½rio (22/03/2007)
            if( dica != null && !"".equals(dica) )
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
            
            s.append("</div>")
             .append("</TD></TR>");
            
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Cria script de validaï¿½ï¿½o em JS para Area.<br>
     * 
     * @param areas
     * @author n/c, rogerio
     * @since 0.1, n/c
     * @version 0.2, 22/03/2007
     */
    public void criaJScriptArea(List areas) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        AreaAre area;
        SubAreaSare subArea;
        List lSubAreas = new ArrayList(0);
        try {
            s.append("\n<script language=\"javascript\">\n");
            s.append("aSubArea = new Array(").append(areas.size()).append(1).append(");\n");

            s.append("for (var i = 0; i < aSubArea.length; ++i) { \n");
            s.append("	aSubArea[i] = new Array();\n");
            s.append("}\n");
            s.append("aSubArea[0][0] = new Option('Selecione uma área','');\n");

            for (int i = 0; i < areas.size(); i++) {
                area = (AreaAre) areas.get(i);
                s.append("aSubArea[").append(i+1).append("][0] = new Option('');\n");
                lSubAreas.clear();
                //lSubAreas.addAll(area.getSubAreaSares());
                int indiceSubArea = 1;
                for (int j = 0; j < lSubAreas.size(); j++) {
                    subArea = (SubAreaSare) lSubAreas.get(j);
                    if ("S".equals(subArea.getIndAtivoSare()))
                        s.append("aSubArea[").append(i+1).append("][").append(indiceSubArea++)
                         .append("] = new Option('").append(subArea.getNomeSare()).append("','")
                         .append(subArea.getCodSare().toString()).append("');\n");
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
     * Cria linha no formulï¿½rio com um Div para carregar pï¿½ginas via Ajax.
     * @param nome
     * @param label
     * @param dica
     */
    public void criaDiv(String nome, String label, String dica){
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        
        try {
        	s.append("<TR><TD class=\"label\">");
            if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true)
                s.append("* ");
            s.append(label)
             .append("</TD>");
             
            s.append("<TD>")
             .append("<div id=\"")
             .append(nome)
             .append("\" style=\"float: left;\"></div>")
             .append("<div style=\"float: left;\">");
            // Aplica a dica de uso da linha, caso ela exista no cadastro.
            // Mantis #8688. Por Rogï¿½rio (22/03/2007)

            if( dica != null && !"".equals(dica) )
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
            
            s.append("</div>")
             .append("</TD></TR>");
            
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
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param page
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
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param atributo
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
    public Object getItem() {
    	if (getEhHistorico().booleanValue()){
    		return getHistoricoItemEstruturaIett();
    	} else {
    		return getItemEstruturaIett();
    	}
        
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
         * @param contextPath
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	
	/**
	 * Verifica se a interface deve apresentar o campo bloqueado ou desbloqueado.
	 * True caso deva bloquear o campo e false caso deva desbloquear
	 * 
	 * @return
	 */
	public Boolean getBloquearCampo() {
		// Primeiro verifica se a interface ï¿½ de consulta. neste caso, deve aparecer bloqueado.
		
		if (getDesabilitar() != null && getDesabilitar()) {
			return true;
		}
		
		if (!getEhHistorico().booleanValue()){
			// caso a interface nï¿½o seja de consulta, deve verificar se o planejamento esteja bloqueado.
			if(((ItemEstruturaIett)getItem()).getIndBloqPlanejamentoIett() != null && 
					"S".equals(((ItemEstruturaIett)getItem()).getIndBloqPlanejamentoIett())) {
				// Se o planejamento estï¿½ bloqueado, ï¿½ verificado a configuraï¿½ï¿½o do atributo para 
				// ver se ele pode ser editado mesmo com o planejamento bloqueado.
				if (atributo.iGetBloqueado()) {
					ItemEstruturaDao itemEstrutDao = new ItemEstruturaDao(null); 
					boolean podeEditar = itemEstrutDao.podeEditarAtributoBloqueadoNaEstrutura((ItemEstruturaIett) getItem(), atributo, seguranca.getUsuario(), seguranca.getGruposAcesso() );
					return !podeEditar; //quando o campo estï¿½ liberado retorna falso
				}
			}
		}
		// Por default, o campo pode ser editado.
		return false;

	}

        /**
         *
         * @return
         */
        public ItemEstruturaIett getItemEstruturaIett() {
		return itemEstruturaIett;
	}

        /**
         *
         * @param itemEstruturaIett
         */
        public void setItemEstruturaIett(ItemEstruturaIett itemEstruturaIett) {
		this.itemEstruturaIett = itemEstruturaIett;
	}

        /**
         *
         * @return
         */
        public HistoricoItemEstruturaIett getHistoricoItemEstruturaIett() {
		return historicoItemEstruturaIett;
	}

        /**
         *
         * @param historicoItemEstruturaIett
         */
        public void setHistoricoItemEstruturaIett(
			HistoricoItemEstruturaIett historicoItemEstruturaIett) {
		this.historicoItemEstruturaIett = historicoItemEstruturaIett;
	}

        /**
         *
         * @return
         */
        public Boolean getEhHistorico() {
		return ehHistorico;
	}

        /**
         *
         * @param ehHistorico
         */
        public void setEhHistorico(Boolean ehHistorico) {
		this.ehHistorico = ehHistorico;
	}

        /**
         *
         * @return
         */
        public String getCodigo() {
		return codigo;
	}

        /**
         *
         * @param codigo
         */
        public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

        /**
         *
         * @return
         */
        public Boolean getHidden() {
		return hidden;
	}

        /**
         *
         * @param hidden
         */
        public void setHidden(Boolean hidden) {
		this.hidden = hidden;
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
}