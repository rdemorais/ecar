/*
 *
 */
package ecar.taglib.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

import comum.database.Dao;
import comum.util.Pagina;
import comum.util.Util;

import ecar.dao.SisGrupoAtributoDao;
import ecar.dao.SisTipoExibicGrupoDao;
import ecar.dao.VisaoDao;
import ecar.dao.VisaoSituacaoDemandaDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.AtributoLivre;
import ecar.pojo.DemAtributoDema;
import ecar.pojo.EntidadeEnt;
import ecar.pojo.LocalItemLit;
import ecar.pojo.ObjetoDemanda;
import ecar.pojo.PrioridadePrior;
import ecar.pojo.RegDemandaRegd;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.SitDemandaSitd;
import ecar.pojo.VisaoDemandasVisDem;
import ecar.util.Dominios;
/** 
 * Tag para preencher a tela de formulï¿½rio de demanda.<br>
 * 
 *  @author Josï¿½, Fernandes
 */
public class FormRegDemandaTag implements Tag {

	private ObjetoDemanda atributo;
	private RegDemandaRegd regDemanda;
    private Boolean desabilitar;
    private String codigoVisao;
	private SegurancaECAR seguranca;	 
    private String contextPath = null;
    
    private String acao;
    private HttpServletRequest request;
    
    private Boolean transformarComboBoxListaChecks = new Boolean(false);
    private Boolean transformarRadioListaChecks = new Boolean(false);
    
    private PageContext page = null;
       
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
            	String nomeMetodo = "geraHTML" + Util.primeiraLetraToUpperCase(atributo.iGetNome());
            	if(atributo.iGetGrupoAtributosLivres() != null){
            		nomeMetodo = "geraHTMLAtributoLivre";
            	}
            	            	
            	this.getClass().getMethod(nomeMetodo, null).invoke(this, null);
            		
        } catch (Exception e) {
        	// nï¿½o ï¿½ necessï¿½rio lanï¿½ar exceï¿½ï¿½o aqui
        }
        return Tag.EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
     return Tag.EVAL_PAGE;
    }
	  /**
	  * 
	  * @author N/C
		 * @since N/C
		 * @version N/C
	  */
	public void release() {
	
	}

        /**
         *
         * @return
         */
        public ObjetoDemanda getAtributo() {
		return atributo;
	}

        /**
         *
         * @param atributo
         */
        public void setAtributo(ObjetoDemanda atributo) {
		this.atributo = atributo;
	}

        /**
         *
         * @return
         */
        public RegDemandaRegd getRegDemanda() {
		return regDemanda;
	}

        /**
         *
         * @param regDemanda
         */
        public void setRegDemanda(RegDemandaRegd regDemanda) {
		this.regDemanda = regDemanda;
	}

        /**
         *
         * @return
         */
        public Boolean getDesabilitar() {
		return desabilitar;
	}

        /**
         *
         * @param desabilitar
         */
        public void setDesabilitar(Boolean desabilitar) {
		this.desabilitar = desabilitar;
	}

        /**
         *
         * @return
         */
        public SegurancaECAR getSeguranca() {
		return seguranca;
	}

        /**
         *
         * @param seguranca
         */
        public void setSeguranca(SegurancaECAR seguranca) {
		this.seguranca = seguranca;
	}

        /**
         *
         * @return
         */
        public PageContext getPage() {
		return page;
	}

        /**
         *
         * @param page
         */
        public void setPage(PageContext page) {
		this.page = page;
	}

        /**
         *
         * @return
         */
        public String getContextPath() {
		return contextPath;
	}

        /**
         *
         * @param contextPath
         */
        public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public Tag getParent() {
		return null;
	}

	public void setPageContext(PageContext arg0) {
		this.page = arg0;
	}

	public void setParent(Tag arg0) {	
	}
	
	/**
     * Gera html de DescricaoRegd.<br>
     *
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDescricaoRegd() {
        try {
        	if (atributo.iGetTamanhoConteudoAtbdem() > ObjetoDemanda.DEFAULT_TAMANHO_CAMPO_TEXT) {
        		criaTextArea("descricaoRegd", atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
        	} else {
                criaInputText(atributo.iGetNome(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtbdem().toString(), 
                		atributo.iGetTamanhoConteudoAtbdem().toString(), 
                		atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
        	}
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html de DataLimiteRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataLimiteRegd() {
        try {
            if(getBloquearCampo()){
                criaInputHidden(atributo.iGetNome(), atributo.iGetValor(getRegDemanda()));        	
                criaInputTextData(atributo.iGetNome()+"Disabled", atributo.iGetLabel(), atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));            	
            } else {
                criaInputTextData(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));            	
            }
        	
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera HTML de OrgaoOrg.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
//    public void geraHTMLOrgaoOrg() {
//        OrgaoOrg orgao = new OrgaoOrg();
//        orgao.setIndAtivoOrg("S");
//        try {
//            List orgaos = new Dao().pesquisar(orgao, new String[] {atributo.iGetNomeFk(), "asc" });
//            List options = new ArrayList();
//            Iterator it = orgaos.iterator();
//            while (it.hasNext()) {
//                orgao = (OrgaoOrg) it.next();
//                options.add(new String[] { orgao.getCodOrg().toString(), Util.invocaGet(orgao, atributo.iGetNomeFk()).toString() });
//            }
//            criaSelect(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValorCodFk(getRegDemanda()), options, "", atributo.iGetDica(), getBloquearCampo());
//        } catch (ECARException e) {
//        	Logger logger = Logger.getLogger(this.getClass());
//        	logger.error(e);
//        }
//    }

    /**
     * Gera HTML de SitDemandaSitd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLSitDemandaSitd() {
        SitDemandaSitd sitDemandaSitd = new SitDemandaSitd();
        VisaoDao visaoDao = new VisaoDao(request);
        VisaoSituacaoDemandaDao visaoSituacaoDemandaDao = new VisaoSituacaoDemandaDao(request);
        try {
        	List options = new ArrayList();
        	boolean telaFiltro = "filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao) ? true:false;
        	VisaoDemandasVisDem visaoDemandasVisDem = null;
        	
        	if (codigoVisao != null && !codigoVisao.equals("")){
        		visaoDemandasVisDem = (VisaoDemandasVisDem) visaoDao.buscar(VisaoDemandasVisDem.class, Long.valueOf(codigoVisao));
        	}
        	
        	/**
        	 * 03909001416: Esta parte do cï¿½digo foi comentada devido a uma solicitaï¿½ï¿½o de alternativas 
        	 * para o item 41 da Bitacora. Solicitaï¿½ï¿½o: na tela de filtros da classificaï¿½ï¿½o de demandas, 
        	 * sejam listados todas as situaï¿½ï¿½es no campo status (Situaï¿½ï¿½o). 
        	 */
        	/*if (acao.equals("filtrarClassificar")){
        		sitDemandaSitd.setIndPrimeiraSituacaoSitd("S");
        	}*/
        	        	
        	String filtroSituacaoVisao = "";
        	if (telaFiltro){
        		filtroSituacaoVisao = VisaoSituacaoDemandaDao.SITUACAO_VISAO_FILTRO;
        	} else {
        		filtroSituacaoVisao = VisaoSituacaoDemandaDao.SITUACAO_VISAO_EDICAO;
        	}

        	List situacoes = visaoSituacaoDemandaDao.getSituacoesVisao(visaoDemandasVisDem, filtroSituacaoVisao);
        	
        	Iterator it = situacoes.iterator();
        	String primeiraSituacao = null;
            while (it.hasNext()) {
            	sitDemandaSitd = (SitDemandaSitd) it.next();
                options.add(new String[] { sitDemandaSitd.getCodSitd().toString(), Util.invocaGet(sitDemandaSitd, atributo.iGetNomeFk()).toString() });
                if (sitDemandaSitd.getIndPrimeiraSituacaoSitd().equals("S")){
                	primeiraSituacao = sitDemandaSitd.getCodSitd().toString();
                }
            }
            
            Boolean bloqueado = null;
            if (acao.equals("incluir") && atributo.iGetIndEditavel(Long.valueOf(codigoVisao)).equals("N")) {
            	bloqueado = new Boolean(true);
            }
            else{
            	bloqueado = new Boolean(false);
            }
            
            String valorSituacao = null;
            
            //Se for inclusão preenche o campo de situação da demanda com a primeira demanda configurada
            //na tabela de situações da demanda
            if (acao.equals("incluir") && primeiraSituacao != null){
            	valorSituacao = primeiraSituacao;
            }
            else{
            	valorSituacao = atributo.iGetValorCodFk(getRegDemanda());
            }
                        
            if (transformarComboBoxListaChecks.booleanValue()) {
            	criaListaChecksApartirSelect(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValorCodFk(getRegDemanda()), options, "", atributo.iGetDica(Long.valueOf(codigoVisao)), getBloquearCampo()); 
            } else {
            	criaSelect(atributo.iGetNome(), atributo.iGetLabel(), valorSituacao /**atributo.iGetValorCodFk(getRegDemanda())**/, options, "", atributo.iGetDica(Long.valueOf(codigoVisao)), getBloquearCampo());            	  
            }
            
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera HTML de PrioridadePrior.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLPrioridadePrior() {
    	PrioridadePrior prioridadePrior = new PrioridadePrior();

        try {
        	List options = new ArrayList();
        	//List situacoes = new Dao().pesquisar(sitDemandaSitd, new String[] {atributo.iGetNomeFk(), "asc" });            	                        
        	List prioridades = new Dao().pesquisar(prioridadePrior, new String[] {atributo.iGetNomeFk(), "asc" });
        	Iterator it = prioridades.iterator();
            while (it.hasNext()) {
            	prioridadePrior = (PrioridadePrior) it.next();
                options.add(new String[] { prioridadePrior.getCodPrior().toString(), Util.invocaGet(prioridadePrior , atributo.iGetNomeFk()).toString() });
            }

            Boolean bloqueado = new Boolean(false);
            
        	if (transformarComboBoxListaChecks.booleanValue()) {
        		criaListaChecksApartirSelect(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValorCodFk(getRegDemanda()), options, "", atributo.iGetDica(Long.valueOf(codigoVisao)), getBloquearCampo());
        	} else {	
        		criaSelect(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValorCodFk(getRegDemanda()), options, "", atributo.iGetDica(Long.valueOf(codigoVisao)), getBloquearCampo());
        	}
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**Segmento
     * Gera HTML de IndAtivoRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLIndAtivoRegd() {
        try {
            List options = new ArrayList();
            options.add(new String[] { "S", "Sim"});
            options.add(new String[] { "N", "Não"});
            
            if (transformarComboBoxListaChecks.booleanValue()) {
            	criaListaChecksApartirSelect(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValor(getRegDemanda()), options, "", atributo.iGetDica(Long.valueOf(codigoVisao)), getBloquearCampo());
            } else {
            	criaSelect(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValor(getRegDemanda()), options, "", atributo.iGetDica(Long.valueOf(codigoVisao)), getBloquearCampo());
            }
       } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html NumeroDocOrigemRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLNumeroDocOrigemRegd() {
        try {

        	if ("filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao)){
        		criaInputTextInicioFim(atributo.iGetNome(), atributo.iGetLabel(), 
     	        		atributo.iGetTamanhoConteudoAtbdem().toString(), 
		        		atributo.iGetTamanhoConteudoAtbdem().toString(), 
		        		atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
        	} else {
        		if(getBloquearCampo()){
                    criaInputHidden(atributo.iGetNome(), atributo.iGetValor(getRegDemanda()));        	
                	criaInputText(atributo.iGetNome()+"Disabled", atributo.iGetLabel(), 
         	        		atributo.iGetTamanhoConteudoAtbdem().toString(), 
    		        		atributo.iGetTamanhoConteudoAtbdem().toString(), 
    		        		atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));                
                } else {
                	criaInputText(atributo.iGetNome(), atributo.iGetLabel(), 
         	        		atributo.iGetTamanhoConteudoAtbdem().toString(), 
    		        		atributo.iGetTamanhoConteudoAtbdem().toString(), 
    		        		atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));            	
                }
        	}
            
        	        	
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html regDemandaRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLRegDemandaRegd() {
        try {
			List listObjetosIgnorado = new ArrayList();
			if(regDemanda.getCodRegd() != null) {
				listObjetosIgnorado.add(regDemanda);
			}//            	if(!acao.equals("classificar") && !telaFiltro)
//    		bloqueado = new Boolean(true);    

			request.getSession().removeAttribute("objetosIgnorados");
			request.getSession().setAttribute("objetosIgnorados", listObjetosIgnorado);
			
        	criaInputHidden("regDemandaRegd",atributo.iGetValorCodFk(getRegDemanda()));
        	criaPesquisaDemanda("demandaRelacionada", atributo.iGetLabel(), 
     	        		atributo.iGetTamanhoConteudoAtbdem().toString(),atributo.iGetTamanhoConteudoAtbdem().toString(), 
		        		atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html Nome Solicitante.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLNomeSolicitanteRegd() {
        try {
        	String disabled = "";
        	if(getBloquearCampo()){
        		disabled = "Disabled";
        	}
            
            if(atributo.iGetTamanhoConteudoAtbdem() > ObjetoDemanda.DEFAULT_TAMANHO_CAMPO_TEXT){
            	criaTextArea(atributo.iGetNome() + disabled, atributo.iGetLabel(), 
 	        		"4", 
	        		"60", 
	        		atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao))); 
            }else{
            	criaInputText(atributo.iGetNome() + disabled, atributo.iGetLabel(), 
     	        		atributo.iGetTamanhoConteudoAtbdem().toString(), 
		        		atributo.iGetTamanhoConteudoAtbdem().toString(),  
		        		atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
            }
            
                   	
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html de DataSolicitacaoRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataSolicitacaoRegd() {
        try {
        	criaInputHidden("dataHoje", Pagina.trocaNullData(comum.util.Data.getDataAtual()));
        	if(getBloquearCampo()){
                criaInputHidden(atributo.iGetNome(), atributo.iGetValor(getRegDemanda()));        	
                criaInputTextData(atributo.iGetNome()+"Disabled", atributo.iGetLabel(), atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));            	
            } else {
                criaInputTextData(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));            	
            }
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    
    /**
     * Gera html de observacaoRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLObservacaoRegd() {
        try {
        	if(getBloquearCampo()){
                criaInputHidden(atributo.iGetNome(), atributo.iGetValor(getRegDemanda()));        	
            	if (atributo.iGetTamanhoConteudoAtbdem() > ObjetoDemanda.DEFAULT_TAMANHO_CAMPO_TEXT) {
            		criaTextArea(atributo.iGetNome()+"Disabled", atributo.iGetLabel(), "4", "60", 
            				atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
            	} else {
                    criaInputText(atributo.iGetNome()+"Disabled", atributo.iGetLabel(), 
                    		atributo.iGetTamanhoConteudoAtbdem().toString(), 
                    		atributo.iGetTamanhoConteudoAtbdem().toString(), 
                    		atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
            	}
            } else {
            	if (atributo.iGetTamanhoConteudoAtbdem() > ObjetoDemanda.DEFAULT_TAMANHO_CAMPO_TEXT) {
            		criaTextArea(atributo.iGetNome(), atributo.iGetLabel(), "4", "60", 
            				atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
            	} else {
                    criaInputText(atributo.iGetNome(), atributo.iGetLabel(), 
                    		atributo.iGetTamanhoConteudoAtbdem().toString(), 
                    		atributo.iGetTamanhoConteudoAtbdem().toString(), 
                    		atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
            	}
            }        	        	
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html de codRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLCodRegd() {
        try {
        	if ("filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao)){
        		criaInputText(atributo.iGetNome(), atributo.iGetLabel(),
            			atributo.iGetTamanhoConteudoAtbdem().toString(),
            			atributo.iGetTamanhoConteudoAtbdem().toString(), 
            			atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
        	} else {
	        	if ("alterar".equals(acao) || "classificar".equals(acao)){
//	            	criaInputHidden(atributo.iGetNome(), atributo.iGetValor(getRegDemanda()));
	            	criaLabelText(atributo.iGetNome(), atributo.iGetLabel(),
	            			atributo.iGetTamanhoConteudoAtbdem().toString(),
	            			atributo.iGetTamanhoConteudoAtbdem().toString(), 
	            			atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));        		
	        	}
        	}
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html de usuarioUsuByCodUsuInclusaoRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLUsuarioUsuByCodUsuInclusaoRegd() {
        try {
        	if ("filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao)){
        		criaInputText(atributo.iGetNome(), atributo.iGetLabel(),
            			atributo.iGetTamanhoConteudoAtbdem().toString(),
            			atributo.iGetTamanhoConteudoAtbdem().toString(), 
            			atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
        	} else {
	        	if (acao.equals("alterar") || acao.equals("classificar")) {
	            	criaLabelText(atributo.iGetNome(), atributo.iGetLabel(),
	            			atributo.iGetTamanhoConteudoAtbdem().toString(),
	            			atributo.iGetTamanhoConteudoAtbdem().toString(), 
	            			atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));        		
	        	}
        	}
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html de usuarioUsuByCodUsuAlteracaoRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLUsuarioUsuByCodUsuAlteracaoRegd() {
        try {
        	if ("filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao)){
        		criaInputText(atributo.iGetNome(), atributo.iGetLabel(),
            			atributo.iGetTamanhoConteudoAtbdem().toString(),
            			atributo.iGetTamanhoConteudoAtbdem().toString(), 
            			atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
        	} else {
	        	if (acao.equals("alterar") || acao.equals("classificar")) {
	            	criaLabelText(atributo.iGetNome(), atributo.iGetLabel(),
	            			atributo.iGetTamanhoConteudoAtbdem().toString(),
	            			atributo.iGetTamanhoConteudoAtbdem().toString(), 
	            			atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));        		
	        	}
        	}
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }


    /**
     * Gera html de DataInclusaoRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataInclusaoRegd() {
        try {
        	if ("filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao)){
        		criaInputTextData(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
        	} else {
	        	if (acao.equals("alterar") || acao.equals("classificar")) {
	            	criaLabelText(atributo.iGetNome(), atributo.iGetLabel(),
	            			atributo.iGetTamanhoConteudoAtbdem().toString(),
	            			atributo.iGetTamanhoConteudoAtbdem().toString(), 
	            			atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));        		
	        	}
        	}
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html de DataAlteracaoRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataAlteracaoRegd() {
        try {
        	if ("filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao)){
        		criaInputTextData(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
        	} else {
	        	if (acao.equals("alterar") || acao.equals("classificar")) {
	            	criaLabelText(atributo.iGetNome(), atributo.iGetLabel(),
	            			atributo.iGetTamanhoConteudoAtbdem().toString(),
	            			atributo.iGetTamanhoConteudoAtbdem().toString(), 
	            			atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));        		
	        	}
        	}
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html de DataSituacaoRegd.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataSituacaoRegd() {
        try {
        	if ("filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao)){
        		criaInputTextData(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));
        	} else {
	        	if (acao.equals("alterar") || acao.equals("classificar")) {
	            	criaLabelText(atributo.iGetNome(), atributo.iGetLabel(),
	            			atributo.iGetTamanhoConteudoAtbdem().toString(),
	            			atributo.iGetTamanhoConteudoAtbdem().toString(), 
	            			atributo.iGetValor(getRegDemanda()), atributo.iGetDica(Long.valueOf(codigoVisao)));        		
	        	}
        	}
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html de EntidadeDemandaEntds.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLEntidadeDemandaEntds() {
        try {
        	Set entidades = new HashSet();
        	if (regDemanda.getEntidadeDemandaEntds() == null) {
        		regDemanda.setEntidadeDemandaEntds(new HashSet());
        	}
        	entidades = regDemanda.getEntidadeDemandaEntds();
        	//modificar depois para pegar o label do atributo.
        	criaPesquisaEntidade(atributo.iGetNome() , atributo.iGetLabel(), entidades, atributo.iGetDica(Long.valueOf(codigoVisao)), atributo.iGetExibivelConsulta(Long.valueOf(codigoVisao)).booleanValue(), atributo.iGetIndEditavel(Long.valueOf(codigoVisao)));

        } catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html de EntidadeOrgaoDemandaEntorgds.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLEntidadeOrgaoDemandaEntorgds() {
        try {
        	Set entidadeOrgaos = new HashSet();
        	if (regDemanda.getEntidadeOrgaoDemandaEntorgds() == null) {
        		regDemanda.setEntidadeOrgaoDemandaEntorgds(new HashSet());
        	}
        	entidadeOrgaos = regDemanda.getEntidadeOrgaoDemandaEntorgds();
        	//modificar depois para pegar o label do atributo.
        	criaPesquisaEntidadeOrgao(atributo.iGetNome() , atributo.iGetLabel(), entidadeOrgaos, atributo.iGetDica(Long.valueOf(codigoVisao)), atributo.iGetExibivelConsulta(Long.valueOf(codigoVisao)).booleanValue(), atributo.iGetIndEditavel(Long.valueOf(codigoVisao)));

        } catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html de LocalDemandaLdems.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLLocalDemandaLdems() {
        try {
        	Set locais = new HashSet();
        	if (regDemanda.getLocalDemandaLdems() == null) {
        		regDemanda.setLocalDemandaLdems(new HashSet());
        	}
        	locais = regDemanda.getLocalDemandaLdems();        	
        	criaPesquisaLocal(atributo.iGetNome(), atributo.iGetLabel(), locais, atributo.iGetDica(Long.valueOf(codigoVisao)));

        } catch (Exception e) {
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
     * Cria linha no formulï¿½rio com campo Text Area para entrada de longos textos.<br>
     * 
     * @author n/c, rogerio
     * @since 0.1, n/c
     * @version 0.3, 22/03/2007
     * @param nome
     * @param label
     * @param cols
     * @param valor
     * @param rows
     * @param dica
     */
    public void criaTextArea(String nome, String label, String rows, String cols, String valor, String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        
        try {
        	s.append("<TR><TD class=\"label\">");
        	boolean telaFiltro = "filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao) ? true:false;
            if (!telaFiltro && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)) != null && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true)
                s.append("* ");
            
            s.append(label)
             .append("</TD>");
             
            s.append("<TD valign=\"top\"><nobr>")
             .append("<div style=\"float: left;\">")
             .append("<textarea name=\"")
             .append(nome)
             .append("\" rows=\"")
             .append(rows)
             .append("\" cols=\"")
             .append(cols);
            
            String tam = "2000";
            if(atributo.iGetTamanhoConteudoAtbdem() != null){
            	tam = String.valueOf(atributo.iGetTamanhoConteudoAtbdem().intValue());
            }
            s.append("\" onkeyup=\"return validaTamanhoLimite(this, " + tam + ");\"");
            if (getBloquearCampo()) {
                s.append(" style=\"background-color:#FFF;color:#999999;\" readonly=\"readonly\"");
            }
            s.append(">")
             .append(valor)
             .append("</textarea>");
              // Aplica a dica de uso da linha, caso ela exista no cadastro.
            // Mantis #8688. Por Rogï¿½rio (22/03/2007)
            if( dica != null && !"".equals(dica) )
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
            
            s.append("</div><div style=\"float: left;\"><br><br>");

           
            
            s.append("</div>")
             .append("<nobr></TD></TR>");
            
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
	 *
     */
    public void criaInputText(String nome, String label, String size, String maxlength, String valor, String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s   = new StringBuffer();
        
        try {        	
        	
        	if(atributo.iGetTamanhoConteudoAtbdem() != null){
        		maxlength = String.valueOf(atributo.iGetTamanhoConteudoAtbdem().intValue());
        	}
        	
        	// inicia a linha
        	s.append("<TR><TD class=\"label\">");
        	
        	boolean telaFiltro = "filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao) ? true:false;
        	
            if (!telaFiltro && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)) != null && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true)
                s.append("* ");
            
            // adiciona o texto
            s.append(label);
            
            s.append("</TD>")
             .append("<TD nowrap><nobr>")
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
            
            s.append("<nobr></TD></TR>");
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
	 *
     */
    public void criaInputTextInicioFim(String nome, String label, String size, String maxlength, String valor, String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s   = new StringBuffer();
        
        try {        	
        	
        	if(atributo.iGetTamanhoConteudoAtbdem() != null){
        		maxlength = String.valueOf(atributo.iGetTamanhoConteudoAtbdem().intValue());
        	}
        	
        	// inicia a linha
        	s.append("<TR>  ");
        	s.append("<TD class=\"label\">");
        	// adiciona o texto
            s.append(label);
            s.append("</TD>");
            
            
            s.append("<TD nowrap>")
             .append("<input type=\"text\" name=\"")
             .append(nome + "_Inicio")
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

            
            s.append(" a ");
            
            s.append("<input type=\"text\" name=\"")
            .append(nome + "_Fim")
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
           
           s.append("</TD>");
            		
            
            s.append("</TR>");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }


    /**
     * Cria uma linha no formulï¿½rio com campo text para receber datas. <br>
     * 
     * @author n/c, 
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
        	if ("filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao)){
        		            
	       		s.append("<TR><TD class=\"label\">");          
	            s.append(label);
	            s.append("</TD>");
	            s.append("<TD nowrap>");
	            s.append("<input type=\"text\" name=\"");
	            s.append(nome+"_Inicio");
	            s.append("\" size=\"11\" value=\"");
	            s.append(valor);
	            s.append("\" maxlength=\"10\" onkeyup=\"mascaraData(event, this);\"");
	            if (getBloquearCampo()) {
	                s.append(" disabled");
	                s.append(">");
	            } else {
	            	s.append(">");
	            	s.append(" <img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"open_calendar('data', document.forms[0]."+nome+"_Inicio, '')\" " + this.getBloquearCampo() + " " + this.getBloquearCampo() + ">");
	            }
	            
	            
	            //Limite superior para tratar datas como intervalo
	            s.append("<!--/TD>");
	            s.append("<TD class=\"label\" colspan=\"3\"-->");
	            s.append(" a ");
	            s.append("<!--/TD>");
	            s.append("<TD nowrap-->");
	            s.append("<input type=\"text\" name=\"");
	            s.append(nome+"_Fim");
	            s.append("\" size=\"11\" value=\"");
	            s.append(valor);
	            s.append("\" maxlength=\"10\" onkeyup=\"mascaraData(event, this);\"");
	            if (getBloquearCampo()) {
	               s.append(" disabled");
	               s.append(">");
	            } else {
	            	s.append(">");
	            	s.append(" <img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"open_calendar('data', document.forms[0]."+nome+"_Fim, '')\" " + this.getBloquearCampo() + " " + this.getBloquearCampo() + ">");
	            }
	           
	        // Aplica a dica de uso da linha, caso ela exista no cadastro.
	            // Mantis #8688. Por Rogï¿½rio (22/03/2007)
	            if( dica != null && !"".equals(dica) )
	            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
	           s.append("</TD>");
	            	            
	            s.append("</TR>");
    	    
        	} else {
        		
        	
	       		s.append("<TR><TD class=\"label\">");          
	            if (atributo.iGetObrigatorio(Long.valueOf(codigoVisao)) != null && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true) {
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
	            if (getBloquearCampo()) {
	                s.append(" disabled");
	            }
	            
	            s.append(">");


	            if (!this.getBloquearCampo() && nome != null && nome.startsWith(Dominios.PREFIX_DATA)) {
	    			s.append("		<img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"open_calendar('data', document.forms[0]."+nome+", '')\" " + this.getBloquearCampo() + " " + this.getBloquearCampo() + ">");
				}

	            // Aplica a dica de uso da linha, caso ela exista no cadastro.
	            // Mantis #8688. Por Rogï¿½rio (22/03/2007)
	            if( dica != null && !"".equals(dica) )
	            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
	            
	            s.append("</TD></TR>");
        	}
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Cria uma linha no formulï¿½rio com um campo SELECT. <br>
     * 
     * @author n/c, 
     * @since 0.1, n/c
     * @version 0.2, 22/03/2007
     * @param nome
     * @param label
     * @param valor 
     * @param bloqueado
     * @param opcoes
     * @param scripts
     * @param dica
     */
    public void criaSelect(String nome, String label, String valor, Collection opcoes, String scripts, String dica, Boolean bloqueado) {
        
    	JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        
        try {
        	// Inicia a linha
        	s.append("<TR><TD class=\"label\">");
        	
        	
           	boolean telaFiltro = "filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao) ? true:false;
           	
        	// Aplica a marca de campo obrigatï¿½rio
            if (!telaFiltro && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)) != null && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true)
                s.append("* ");   
            
            // Aplica o label do campo
            s.append(label);            
            s.append("</TD>");
            
            // se não for pra mudar forma de apresentacao do combobox
            if (!transformarComboBoxListaChecks.booleanValue()) {
            	//
                s.append("<TD>");
                s.append("<select name=\"").append(nome).append("\" ").append(scripts);          	
                if(bloqueado)
                	s.append(" disabled");
                s.append(">");
                
                if (!nome.equals("sitDemandaSitd") || telaFiltro)
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
            } else {
            	
            }
            
            
            
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
     * @param bloqueado
	 */
	public void criaListaChecksApartirSelect(String nome, String label, String valor,
			Collection opcoes, String scripts, String dica, boolean bloqueado) {

		JspWriter writer = this.page.getOut();
		StringBuffer s = new StringBuffer();

		try {

			Map atributosMap = (Map) request.getSession().getAttribute(
					"atributosMap");
			String atribInicio = null;

			if (atributosMap != null) {
				atribInicio = (String) atributosMap.get(nome);
				valor = atribInicio;
			}

			// Inicia a linha
			s.append("<TR><TD valign=\"top\" class=\"label\">");

			// Aplica o label do campo
			s.append(label);

			s.append("</TD>");
			
			// se não for pra mudar forma de apresentacao do combobox
			s.append("<TD>");
			
			if (opcoes != null) {
				Iterator it = opcoes.iterator();
				int contador = 1;
				while (it.hasNext()) {
					String[] chaveValor = (String[]) it.next();
					s.append("<input type=\"checkbox\" class=\"form_check_radio\"  name=\"").append(nome)
		             .append("\" value=\"").append(chaveValor[0]).append("\"");
					if (chaveValor[0].equals(valor))
						s.append(" checked  ");
					if (bloqueado)
						s.append(" disabled");
					s.append(">");	
					s.append(chaveValor[1]);
                    if(contador == 1){
                        if( dica != null && !"".equals(dica) ){
                        	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
                        }
                    }
                    contador++;
					s.append("<br>\n");
				} 
			}


			// finaliza linha
			s.append("</TD></TR>");

			writer.print(s.toString());
		} catch (IOException e) {
			Logger logger = Logger.getLogger(this.getClass());
			logger.error(e);
		}
	}

    /**
     * Gera uma linha do formulï¿½rio, contendo campo de pesquisa para Demanda. <br>
     * 
     * @author n/c, 
     * @since 0.1, n/c
     * @version 0.2, 
     * @param nome
     * @param label
     * @param size
     * @param maxlength
     * @param valor
     * @param dica
     */
    public void criaPesquisaDemanda(String nome, String label, String size, String maxlength, String valor,String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        
        try {
       		// Cria a linha
        	s.append("<TR><TD class=\"label\">");

            // Aplica a marca de campo obrigatï¿½rio
        	boolean telaFiltro = "filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao) ? true:false;
        	if (!telaFiltro && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)) != null && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true)
                s.append("* "); 
        	
            // Aplica o label do campo
        	s.append(label);
            s.append("</TD>")
             .append("<TD>")
             .append("<input type=\"text\" disabled name=\"")
             .append(nome)
             .append("\" size=\"")
             .append(size)
             .append("\" value=\"")
             .append(valor)
             .append("\" maxlength=\"")
             .append(maxlength)
             .append("\"")
             .append(">");
            
            // Adiciona o botï¿½o Pesquisar e Limpar
            if (!getBloquearCampo()) {
            	s.append("&nbsp;&nbsp;<input type=\"button\" name=\"buttonPesquisar\" value=\"Pesquisar\" class=\"botao\" ");            
            	s.append(" onclick=\"pesquisar();\">")
            		.append("&nbsp;<input type=\"button\" name=\"buttonLimpar\" value=\"Limpar\" class=\"botao\" ")
            		.append("onclick=\"limpaCampos();\"");
            	s.append(">");
            }
            

            
            if( dica != null && !"".equals(dica) )
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
            
            s.append("</TD></TR>");
            writer.print(s.toString());
            
        } catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }    

    /**
     * Gera uma linha do formulï¿½rio, contendo os links de inclusï¿½o e exclusï¿½o para Entidade. <br>
     * 
     * @author n/c, 
     * @since 0.1, n/c
     * @version 0.2, 
     * @param nome
     * @param label
     * @param entidades
     * @param dica
     */
    public void criaPesquisaEntidade(String nome, String label, Set entidades, String dica, boolean exibivel, String editavel) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        
        try {
        	
        	int quantidade = 0;
        	if (entidades!=null){
        		quantidade = entidades.size();
        	}

        	
        	s.append("\n<input type=\"hidden\" id=\"contEntidade\" name=\"contEntidade\" value=\"" + quantidade +"\">");
        	s.append("\n<input type=\"hidden\" id=\"contadorEntidadeReal\" name=\"contadorEntidadeReal\" value=\"" + quantidade +"\">");
        	
        	// Cria a linha
        	s.append("<tr id=\"labelEntidade\">");
                    	
        	s.append("<td valign=\"top\" class=\"label\">");            
        	// Aplica a marca de campo obrigatï¿½rio
        	boolean telaFiltro = "filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao) ? true:false;
        	if (!telaFiltro && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)) != null && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true)
                s.append("* ");        	
        	// Aplica o label do campo        	
        	s.append(label);
        	s.append("</td>");
        	
        	if(!this.getBloquearCampo()){
        		s.append("<td>");
        		s.append("<a href=\"#\" onClick=\"popup_pesquisa('ecar.popup.PopUpEntidade', 'adicionaEntidade');\"> Adicionar </a>");
        		s.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        		s.append("<a href=\"#\" onClick=\"removeEntidade()\"> Excluir </a>");
        	}
        	if( dica != null && !"".equals(dica) ){
        		if (this.getBloquearCampo()){
        			s.append("<td valign=\"top\">");
        		}
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
        	}
            	
        	if (editavel.equals("S")){
	        	s.append("</td>");
	        	s.append("</tr>");
	        	
	        	s.append("<tr><td>&nbsp;</td><td id=\"entidade\" colspan=\"2\"></td></tr>");
	        	s.append("<tr><td class=\"label\" colspan=\"2\">");   
	        	
	        	s.append("</td></tr>");
        	}
        	else{
        		
        		s.append("</td>");
	        	s.append("</tr>");
        		
	        	s.append("<tr><td></td>");
	        	
        		s.append("<td id=\"entidade\"></td></tr>");
	        	s.append("<tr><td class=\"label\" colspan=\"2\">"); 
        	}

        	//Cria o script que apresenta as entidades da demanda
        	Iterator itEnt = entidades.iterator();
        	EntidadeEnt entid = new EntidadeEnt();        	
        	s.append("\n<script>");
        	
        	boolean adicionaCheck = true;
    		if (exibivel && !editavel.equals("S")){
    			adicionaCheck = false;
    		}
        	
        	while (itEnt.hasNext())	{
        		entid = (EntidadeEnt) itEnt.next();
        		
        		s.append("adicionaEntidade(\"" + entid.getCodEnt().toString().trim() + "\", \"" + entid.getNomeEnt().trim() + "\", \"" + adicionaCheck + "\");\n");
        	}
        	s.append("</script>");        	        	
        	writer.print(s.toString());
        	
	    } catch (Exception e) {
		    	Logger logger = Logger.getLogger(this.getClass());
		    	logger.error(e);
		    }    	
    }
    
    
    /**
     * Gera uma linha do formulï¿½rio, contendo os links de inclusï¿½o e exclusï¿½o para EntidadeOrgao. <br>
     * 
     * @author n/c, 
     * @since 0.1, n/c
     * @version 0.2, 
     * @param nome
     * @param label
     * @param entidadeOrgaos
     * @param dica
     */
    public void criaPesquisaEntidadeOrgao(String nome, String label, Set entidadeOrgaos, String dica, boolean exibivel, String editavel) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        
        try {
        	
        	int quantidade = 0;
        	if (entidadeOrgaos!=null){
        		quantidade = entidadeOrgaos.size();
        	}

        	s.append("\n<input type=\"hidden\" id=\"contEntidadeOrgao\" name=\"contEntidadeOrgao\" value=\"" + quantidade +"\">");
        	s.append("\n<input type=\"hidden\" id=\"contadorEntidadeOrgaoReal\" name=\"contadorEntidadeOrgaoReal\" value=\"" + quantidade + "\">");
        	
        	// Cria a linha
        	s.append("<tr id=\"labelEntidadeOrgao\">");
                    	
        	s.append("<td valign=\"top\" class=\"label\">");            
        	// Aplica a marca de campo obrigatï¿½rio
        	boolean telaFiltro = "filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao) ? true:false;
        	if (!telaFiltro && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)) != null && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true)
                s.append("* ");        	
        	// Aplica o label do campo        	
        	s.append(label);
        	s.append("</td>");
        	
        	if(!this.getBloquearCampo()){
        		s.append("<td>");
        		s.append("<a href=\"#\" onClick=\"popup_pesquisa('ecar.popup.PopUpEntidadeOrgao', 'adicionaEntidadeOrgao');\"> Adicionar </a>");
        		s.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        		s.append("<a href=\"#\" onClick=\"removeEntidadeOrgao()\"> Excluir </a>");
        	}
        	if( dica != null && !"".equals(dica) ){
        		if (this.getBloquearCampo()){
        			s.append("<td valign=\"top\">");
        		}
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
        	}
            	
        	if (editavel.equals("S")){
	        	s.append("</td>");
	        	s.append("</tr>");
	        	
	        	s.append("<tr><td>&nbsp;</td><td id=\"entidadeOrgao\" colspan=\"2\"></td></tr>");
	        	s.append("<tr><td class=\"label\" colspan=\"2\">");   
	        	
	        	s.append("</td></tr>");
        	}
        	else{
        		
        		s.append("</td>");
	        	s.append("</tr>");
        		
	        	s.append("<tr><td></td>");
	        	
        		s.append("<td id=\"entidadeOrgao\"></td></tr>");
	        	s.append("<tr><td class=\"label\" colspan=\"2\">"); 
        	}

        	//Cria o script que apresenta as entidades que são órgãos da demanda
        	Iterator itEnt = entidadeOrgaos.iterator();
        	EntidadeEnt entid = new EntidadeEnt();        	
        	s.append("\n<script>");
        	
        	boolean adicionaCheck = true;
    		if (exibivel && !editavel.equals("S")){
    			adicionaCheck = false;
    		}
        	
        	while (itEnt.hasNext())	{
        		entid = (EntidadeEnt) itEnt.next();
        		
        		s.append("adicionaEntidadeOrgao(\"" + entid.getCodEnt().toString().trim() + "\", \"" + entid.getNomeEnt().trim() + "\", \"" + adicionaCheck + "\");\n");
        	}
        	s.append("</script>");        	        	
        	writer.print(s.toString());
        	
	    } catch (Exception e) {
		    	Logger logger = Logger.getLogger(this.getClass());
		    	logger.error(e);
		    }    	
    }

    /**
     * Gera uma linha do formulï¿½rio, contendo os links de inclusï¿½o e exclusï¿½o para Local. <br>
     * formRegDemanda 
     * @author n/c, 
     * @since 0.1, n/c
     * @version 0.2, 
     * @param nome
     * @param label
     * @param locais
     * @param dica
     */
    public void criaPesquisaLocal(String nome, String label, Set locais, String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        
        try {
        	
        	int quantidade = 0;
        	if (locais!=null){
        		quantidade = locais.size();
        	}

        	
        	s.append("\n<input type=\"hidden\" id=\"contLocal\" name=\"contLocal\" value=\"" + quantidade +"\">");
        	s.append("\n<input type=\"hidden\" id=\"contadorLocalReal\" name=\"contadorLocalReal\" value=\"" + quantidade +"\">");

        	// Cria a linha
        	s.append("<tr id=\"labelLocais\">");
                    	
        	s.append("<td class=\"label\">");
        	// Aplica a marca de campo obrigatï¿½rio
        	boolean telaFiltro = "filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao) ? true:false;
        	if (!telaFiltro && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)) != null && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() == true)
                s.append("* ");        	
            // Aplica o label do campo
        	s.append(label);
        	s.append("</td>");
        	s.append("<td>");
        	if(!this.getBloquearCampo()){
        		s.append("<a href=\"#\" onClick=\"abreJanela('popUpLocal.jsp','500', '300', 'LocalItem')\"> Adicionar </a>");
        		s.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        		s.append("<a href=\"#\" onClick=\"removeLocal()\"> Excluir </a>");
        	}
        	if( dica != null && !"".equals(dica) )
            	s.append(Util.getTagDica(nome, this.getContextPath(), dica));
        	s.append("</td>");
        	s.append("</tr>");
        	s.append("<tr><td>&nbsp;</td><td id=\"local\" colspan=\"2\">");        	
        	s.append("</td></tr>");

        	//Cria o script que apresenta os locais da demanda
        	Iterator itLoc = locais.iterator();
        	LocalItemLit loc = new LocalItemLit();
        	
        	
        	s.append("\n<script>");
        	while (itLoc.hasNext())	{
        		loc = (LocalItemLit) itLoc.next(); 
        		
        		// verifica se o local tem um pai
        		if(loc.getLocalItemHierarquiaLithsByCodLit() != null && !loc.getLocalItemHierarquiaLithsByCodLit().isEmpty()) {
		
					// se tiver imprime na tela o nome do registro com o nome do pai -> diferenciar cidades que sejam de regiï¿½es diferentes -> aplica-se para todos os registros
					Iterator itListaLocalPai = loc.getLocalItemHierarquiaLithsByCodLit().iterator();
					LocalItemLit pai = null;
				
					if(itListaLocalPai.hasNext()) {
						pai = (LocalItemLit) itListaLocalPai.next();
						s.append("adicionaLocal(\"" + loc.getCodLit().toString().trim() + "\", \"" + loc.getIdentificacaoLit().trim() + " - " + pai.getIdentificacaoLit().trim() + "\");\n");	
					} else 
						s.append("adicionaLocal(\"" + loc.getCodLit().toString().trim() + "\", \"" + loc.getIdentificacaoLit().trim() + "\");\n");
		
        		} else {	
        		
        			s.append("adicionaLocal(\"" + loc.getCodLit().toString().trim() + "\", \"" + loc.getIdentificacaoLit().trim() + "\");\n");
        		}
        		
        	}
        	s.append("</script>");       	
            writer.print(s.toString());
        
        	        	        	
	    } catch (Exception e) {
		    	Logger logger = Logger.getLogger(this.getClass());
		    	logger.error(e); 
		    }    	
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
		// Por default, o campo pode ser editado.
		return false;
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
     *
     * @return
     */
    public String getAcao() {
		return acao;
	}

    /**
     * Gera Atributo Livre
     * 
     * @author 
     * @since 
     * @version 
     * @throws ECARException
     * @throws JspException
     */
    public void geraHTMLAtributoLivre() throws ECARException, JspException{
    	boolean telaFiltro = "filtrarClassificar".equals(acao) || "filtrarCadastro".equals(acao) ? true:false;
    	if(atributo.iGetGrupoAtributosLivres() != null 
    			&& (atributo.iGetGrupoAtributosLivres().getIndAtivoSga()!=null 
    			&&  atributo.iGetGrupoAtributosLivres().getIndAtivoSga().equals("S")) ){
    		if (atributo.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().intValue() != Input.IMAGEM || !telaFiltro){    			
	        	JspWriter writer = this.page.getOut();
	
	        	SisGrupoAtributoSga grupoAtributo = atributo.iGetGrupoAtributosLivres(); 
	            	
	        	Input input = new Input(writer);
	        	input.setRequest(request);
	        	input.setLabel(atributo.iGetLabel());
	        	input.setPage(page);
	        	//Caso o tipo do grupo seja combobox(1) deve ser trocado pelo tipo checkbox(2).
	        	// apenas se estiver em tela de pesquisa
	        	if (transformarComboBoxListaChecks.booleanValue()==true && grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(SisTipoExibicGrupoDao.COMBOBOX))){
	        		input.setTipo(new Long(SisTipoExibicGrupoDao.CHECKBOX).intValue());
	        	} else {
	        		input.setTipo(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().intValue());	
	        	}
	        	
	        	input.setObrigatorio(!telaFiltro && atributo.iGetObrigatorio(Long.valueOf(codigoVisao)).booleanValue() ? "S" : "N"); //Obrigatoriedade estï¿½ no atributo
	        	
	        	input.setPathRaiz(this.getContextPath());
	        	input.setLabelObrigatorio("*");
	        	input.setSize(atributo.iGetTamanhoConteudoAtbdem().toString());
	        	if (telaFiltro){
	        		input.setTelaFiltro(new Boolean(true));
	        	} else {
	        		input.setTelaFiltro(new Boolean(false));
	        	}
	        	
	        	
	        	if (getBloquearCampo()) {
	        		input.setDisabled("S");
	        	}
	        	input.setNome("a" + grupoAtributo.getCodSga().toString());
	        	input.setClassLabel("label");
	        	input.setContexto(this.getContextPath());
	        	
	        	List aributosLivresSelecionados = new ArrayList();
	        	
	        	if(regDemanda.getDemAtributoDemas() != null){
	    	    	Iterator itAtribLivres = regDemanda.getDemAtributoDemas().iterator();
	    	    	while(itAtribLivres.hasNext()){
	    	    		DemAtributoDema atributo = (DemAtributoDema) itAtribLivres.next();
	    	    		AtributoLivre atributoLivre = new AtributoLivre();
	    	    		atributoLivre.setInformacao(atributo.getInformacao());
	    	    		atributoLivre.setSisAtributoSatb(atributo.getSisAtributoSatb());
	    	    		aributosLivresSelecionados.add(atributoLivre);
	    	    	}
	        	}
	        	
	        	input.setSelecionados(aributosLivresSelecionados);
	        	input.setSisAtributo((SisAtributoSatb)grupoAtributo.getSisAtributoSatbs().iterator().next());
	        	if(atributo.iGetDica(Long.valueOf(codigoVisao)) != null) {
	        		input.setDica(atributo.iGetDica(Long.valueOf(codigoVisao)));
	        	}
	        	
	        	
	        	input.setTransformarComboBoxListaChecks(transformarComboBoxListaChecks.booleanValue());
	        	
	        	input.doStartTag();
	        	
	        	Options options = new Options(writer);
	        	
	        	options.setTransformarComboBoxListaChecks(transformarComboBoxListaChecks.booleanValue());
	        	options.setTransformarRadioListaChecks(transformarRadioListaChecks.booleanValue());
	        	
	    		List opcoes = new ArrayList();
	    		String selectedCodSapadrao = "";
	    		if(grupoAtributo.getCodSga() != null && grupoAtributo.getCodSga().longValue() != 1){
	    			if(grupoAtributo.getSisTipoOrdenacaoSto() != null){
	    				opcoes = new SisGrupoAtributoDao(null).getAtributosOrdenados(grupoAtributo);
	    				if (!opcoes.isEmpty()){
	    					Iterator itOpcoes = opcoes.iterator();
	    					while (itOpcoes.hasNext()){
	    						if (((SisAtributoSatb) itOpcoes.next()).getIndAtivoSatb().equals("N")){
	    							itOpcoes.remove();
	    						}
	    					}
	    				}
	    			}
	    		} 
	    		
	        	
	    		if(!opcoes.isEmpty()) {
	    	    	options.setOptions(opcoes);
	    	    	options.setValor("codSatb");
	    	    	options.setLabel("descricaoSatb");
	    	    	options.setDica(atributo.iGetDica(Long.valueOf(codigoVisao)));
	    	    	options.setNome("a" + grupoAtributo.getCodSga().toString());
	    	    	options.setContexto(this.getContextPath());
	    	    	//options.setImagem(getContextPath() + "/images/relAcomp/");
	    	    	options.setParent(input);	    	    	
	    	    	options.doStartTag();
	    		}
	    		
	    		input.doEndTag();
    		}
    	}
    }
    
    /**
     * Cria input para separar datas/valores
     * 
     * @author 
     * @since 
     * @version 
     */
    public void criaInputSeparadorDatasValores(){
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {            
            s.append(" a ");
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     *
     * @param acao
     */
    public void setAcao(String acao) {
		this.acao = acao;
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
        public String getCodigoVisao() {
		return codigoVisao;
	}

        /**
         *
         * @param codigoVisao
         */
        public void setCodigoVisao(String codigoVisao) {
		this.codigoVisao = codigoVisao;
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