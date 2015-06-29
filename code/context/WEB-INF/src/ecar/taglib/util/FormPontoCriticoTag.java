/*
 * Criado em 28/12/2004
 *
 */
package ecar.taglib.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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

import comum.util.Pagina;
import comum.util.Util;

import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.AcompRelatorioDao;
import ecar.dao.ConfigMailCfgmDAO;
import ecar.dao.ConfiguracaoDao;
import ecar.dao.CorDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.PontocriticoCorPtccorDAO;
import ecar.dao.SisGrupoAtributoDao;
import ecar.dao.SisTipoExibicGrupoDao;
import ecar.dao.UsuarioDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.AreaAre;
import ecar.pojo.AtributoLivre;
import ecar.pojo.ConfigMailCfgm;
import ecar.pojo.Cor;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.PontoCriticoSisAtributoPtcSatb;
import ecar.pojo.PontocriticoCorPtccor;
import ecar.pojo.PontocriticoCorPtccorPK;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SisGrupoAtributoSga;
import ecar.pojo.SubAreaSare;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;
/** 
 * As referencias para AtributoEstruturaTela foram subtituidas por ObjetoEstrutura.<br>
 * 
 *  @author 
 */
public class FormPontoCriticoTag implements Tag {

	ValidaPermissao validaPermissao = new ValidaPermissao();
    private ObjetoEstrutura atributo;
    private PontoCriticoPtc pontoCriticoPtc;
    private ItemEstruturaIett iett;
    private Boolean desabilitar;
	private SegurancaECAR seguranca;
	private Boolean exibirBotoes = new Boolean(true);
	private HttpServletRequest request;
	private AcompReferenciaItemAri acompReferenciaItemAri;
	private Boolean novoPontoCritico;
	

	private PageContext page = null;
    private String contextPath = null;
    
    private UsuarioDao usu = null;
    
    
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
            		nomeMetodo = "geraHTMLAtributoLivre";
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
        } catch (Exception e) {
        	System.out.println("Erro ao gerar form de pontos críticos: " + e.getMessage());
        	// não é necessário tratar a exceção aqui
        }
        return Tag.EVAL_BODY_INCLUDE;
    }
    
    /**
     * Gera html indAtivoPtc.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLIndAtivoPtc() {
        List opcoes = new ArrayList();
        opcoes.add(new String[] { "S", "Ativo" });
        opcoes.add(new String[] { "N", "Cancelado" });
        try {
            criaRadio(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValor(getPontoCriticoPtc()), opcoes, atributo.iGetDica());
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

    /**
     * Gera html data de inclusão do ponto crítico.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataInclusaoPtc() {
    	try {
        	if (atributo.iGetIndOpcional() == null || atributo.iGetIndOpcional().booleanValue() == false){
    			if(atributo.iGetSequenciaCampoEmTela() != null && atributo.iGetSequenciaCampoEmTela().intValue() != 0){
        			criaLabelText(atributo.iGetNome(), atributo.iGetLabel(), "15", "15", atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
    			}
    		}
    		else {
	    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
	    			criaLabelText(atributo.iGetNome(), atributo.iGetLabel(), "15", "15", atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
	    		}
    		}
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html data de solução do ponto crítico.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataSolucaoPtc() {
        try {
            criaInputTextData(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html da data da última manutenção do ponto crítico.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataUltManutencaoPtc() {
        try {
        	if (atributo.iGetIndOpcional() == null || atributo.iGetIndOpcional().booleanValue() == false){
    			if(atributo.iGetSequenciaCampoEmTela() != null && atributo.iGetSequenciaCampoEmTela().intValue() != 0){
        			criaLabelText(atributo.iGetNome(), atributo.iGetLabel(), "15", "15", atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
    			}
    		}
    		else {
	    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
	    			criaLabelText(atributo.iGetNome(), atributo.iGetLabel(), "15", "15", atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
	    		}
    		}
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }

   
    /**
     * Gera html da descrição da solução do ponto crítico.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDescricaoSolucaoPtc() {
        try {
        	if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
        		criaTextArea(atributo.iGetNome(), atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
        	} else {
                criaInputText(atributo.iGetNome(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
        	}
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html indAmbitoInternoGovPtc.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLIndAmbitoInternoGovPtc() {
        List opcoes = new ArrayList();
        opcoes.add(new String[] { "I", "Interno" });
        opcoes.add(new String[] { "E", "Externo" });
        try {
            criaRadio(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValor(getPontoCriticoPtc()), opcoes, atributo.iGetDica());
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
  
    /**
     * Gera html da data limite do ponto crítico.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataLimitePtc() {
        try {
            criaInputTextData(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html da data de identificação do ponto crítico.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDataIdentificacaoPtc() {
        try {
            criaInputTextData(atributo.iGetNome(), atributo.iGetLabel(), atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
    /**
     * Gera html da descrição do ponto crítico.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void geraHTMLDescricaoPtc() {
        try {
        	if (atributo.iGetTamanhoConteudoAtrib() > ObjetoEstrutura.DEFAULT_TAMANHO_CAMPO_TEXT) {
        		criaTextArea(atributo.iGetNome(), atributo.iGetLabel(), "4", "60", 
        				atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
        	} else {
                criaInputText(atributo.iGetNome(), atributo.iGetLabel(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetTamanhoConteudoAtrib().toString(), 
                		atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
        	}
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
    
   /**
     * Gera html do usuário.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
    */
    public void geraHTMLUsuarioUsu(){
    	try {
    		criaPesquisa(atributo.iGetNome(), true, atributo.iGetLabel(),
                         "ecar.popup.PopUpUsuario", "50", atributo.iGetValorCodFk(getPontoCriticoPtc()), atributo.iGetValor(getPontoCriticoPtc()), "100", atributo.iGetDica());
    		
    	} catch (ECARException e) {
    		Logger logger = Logger.getLogger(this.getClass());
    		logger.error(e);
    	}
    }
    
    /**
     * Gera html do usuário de inclusão.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
    */
    public void geraHTMLUsuarioUsuInclusao(){
    	try{
    		if (atributo.iGetIndOpcional() == null || atributo.iGetIndOpcional().booleanValue() == false){
    			if(atributo.iGetSequenciaCampoEmTela() != null && atributo.iGetSequenciaCampoEmTela().intValue() != 0){
        			criaLabelText(atributo.iGetNome(), atributo.iGetLabel(), "50", "50", atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
    			}
    		}
    		else {
	    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
	    			criaLabelText(atributo.iGetNome(), atributo.iGetLabel(), "50", "50", atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
	    		}
    		}
    	}
    	catch (ECARException e) {
    		Logger logger = Logger.getLogger(this.getClass());
    		logger.error(e);
    	}
    }
    
    /**
     * Gera html do usuário da última manutenção.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
    */
    public void geraHTMLUsuarioUsuByCodUsuUltManutPtc(){
    	try{
    		if (atributo.iGetIndOpcional() == null || atributo.iGetIndOpcional().booleanValue() == false){
    			if(atributo.iGetSequenciaCampoEmTela() != null && atributo.iGetSequenciaCampoEmTela().intValue() != 0){
        			criaLabelText(atributo.iGetNome(), atributo.iGetLabel(), "50", "50", atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
    			}
    		}
    		else {
	    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
	    			criaLabelText(atributo.iGetNome(), atributo.iGetLabel(), "50", "50", atributo.iGetValor(getPontoCriticoPtc()), atributo.iGetDica());
	    		}
    		}
    	}
    	catch (ECARException e) {
    		Logger logger = Logger.getLogger(this.getClass());
    		logger.error(e);
    	}
    }
    
    /**
     * Gera html do atributo livre configurado em configuração geral.<br>
     * 
     * @author N/C
	 * @since N/C
     * @throws ECARException
     * @throws JspException
     * @version N/C
    */
    public void geraHTMLSisAtributoTipo() throws ECARException, JspException{
    	
    	JspWriter writer = this.page.getOut();

    	SisGrupoAtributoSga grupoAtributo = new ConfiguracaoDao(null).getConfiguracao().getSisGrupoAtributoSgaTipoPontoCritico();
        	
    	Input input = new Input(writer);
    	input.setRequest(request);
    	input.setLabel(atributo.iGetLabel());
    	input.setTipo(new Integer(SisTipoExibicGrupoDao.COMBOBOX).intValue());
    	input.setObrigatorio(atributo.iGetObrigatorio().booleanValue() ? "S" : "N");
    	input.setLabelObrigatorio("* ");
    	
    	if(getBloquearCampo()){
    		input.setDisabled("S");
    	}
    	
    	input.setNome("codSgaPontoCritico");
    	input.setClassLabel("label");
    	
    	List satbSelecionado = new ArrayList();

    	if(getPontoCriticoPtc().getSisAtributoTipo() != null){
			SisAtributoSatb satb = getPontoCriticoPtc().getSisAtributoTipo();
    		AtributoLivre atributoLivre = new AtributoLivre();
    		atributoLivre.setSisAtributoSatb(satb);
    		satbSelecionado.add(atributoLivre);
    	}
    	
    	input.setContexto(getContextPath());
    	input.setSelecionados(satbSelecionado);
    	input.setSisAtributo((SisAtributoSatb)grupoAtributo.getSisAtributoSatbs().iterator().next());
    	//input.setPathRaiz();
    	//input.setSize();
    	if(atributo.iGetDica() != null)
    		input.setDica(atributo.iGetDica());
    	
    	input.doStartTag();
    	
    	Options options = new Options(writer);
    	
		List opcoes = new ArrayList();
		if(grupoAtributo.getCodSga() != null && grupoAtributo.getCodSga().longValue() != 1){
			if(grupoAtributo.getSisTipoOrdenacaoSto() != null){
				opcoes = new SisGrupoAtributoDao(null).getAtributosOrdenados(grupoAtributo);
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
     * Gera html do das cores de pontos críticos.<br>
     * 
     * @author N/C
	 * @since N/C
     * @throws IOException
     * @version N/C
     * @throws NoSuchAlgorithmException 
    */
    public void geraHTMLPontoCriticoCorPtccores() throws IOException, NoSuchAlgorithmException{
    	JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        String campoObrigatorio = "";
    	try {
    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true){
    			campoObrigatorio = "* ";
    		}
            
    		s.append("<tr>");
			s.append("	<td class=\"label\">" + campoObrigatorio + atributo.iGetLabel() + "</td>");
			s.append("	<td> ");
			 if( atributo.iGetDica() != null && !"".equals(atributo.iGetDica()) )
	            	s.append(Util.getTagDica(atributo.iGetNome(), this.getContextPath(), atributo.iGetDica()));
	            
			s.append("  </td>");
			s.append("</tr>");
			s.append("	<tr>");
			s.append("		<td>&nbsp;</td>");
			s.append("		<td>");
			s.append("			<table border=\"1\">");
			s.append("				<tr>");
			s.append("					<td class=\"titulo\" align=\"center\" width=\"20\">Estado</td>");
			s.append("					<td class=\"titulo\" align=\"center\" width=\"20\"> <nobr>Antecedência (em dias)</nobr></td>");
			s.append("					<td class=\"titulo\" align=\"center\" width=\"20\"> <nobr>Freqüência de envio (em dias)</nobr></td>");
			s.append("					<td class=\"titulo\" align=\"center\" width=\"10\">Ativo</td>");
			s.append("				</tr><!--");			
			s.append("				<tr>");
			s.append("					<td class=\"form_label\" align=\"center\">");
			s.append("						<img src=\"../../images/pcBranco1.png\">");
			s.append("					</td>");
			s.append("					<td valign=\"middle\" colspan=\"2\" class=\"form_label\" align=\"center\">Nenhuma Ação</td>");
			s.append("					<td valign=\"middle\" class=\"form_label\" align=\"center\">&nbsp;</td>");
			s.append("				</tr>");
			s.append("				-->");
			
			PontocriticoCorPtccor ptcCor = null;
			ConfigMailCfgm configMailVencto = (ConfigMailCfgm) new ConfigMailCfgmDAO(getRequest()).buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_VENCIMENTO_LIMITE_PONTO_CRITICO);
			List setCores = new CorDao(getRequest()).listar(Cor.class, new String[]{"ordemCor","asc"});
			Cor cor = null;
			PontocriticoCorPtccorPK id = null;
			Iterator itCores = null;
			if (setCores != null)
				itCores = setCores.iterator();
			
			String imagePath = "";
			CorDao cDao = new CorDao(getRequest());
			
			while (itCores.hasNext())
			{
				cor = (Cor) itCores.next(); 			
				id = new PontocriticoCorPtccorPK(getPontoCriticoPtc().getCodPtc(), cor.getCodCor());
				ptcCor = (PontocriticoCorPtccor)new PontocriticoCorPtccorDAO(getRequest()).buscar(cor, getPontoCriticoPtc());
				
				if(cor.getIndPontoCriticoCor().equals("S")){
					s.append("<tr>");
					s.append("<td valign=\"middle\"  class=\"form_label\" align=\"center\">");
					// Por Rogério (06/03/2007)
					// Modificada a forma de obtenção da imagem.
					// Referente ao Mantis #7442
					imagePath = cDao.getImagemPersonalizada(cor, null, null);
					if( imagePath != null ) {
						
   						String hashNomeArquivo = null;
   						UsuarioUsu usuarioImagem = null;
   						
   						String pathRaiz = new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getRaizUpload();
   						imagePath = imagePath.substring(pathRaiz.length());
						
   						hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagePath);
						usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
						Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagePath); 
						
						/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */
				    	s.append("<img border=\"0\" src=\""+getRequest().getContextPath()+"/DownloadFile?tipo=open&RemoteFile="+hashNomeArquivo+"\" title=\""+cor.getSignificadoCor()+"\">");
					} else {
						if( cor.getCodCor() != null ) {
							s.append("<img src=\"../../images/pc"+cor.getNomeCor()+"1.png\" title=\""+cor.getSignificadoCor()+"\">"); 
						}
					} 
					s.append("</td>");
					s.append("<td valign=\"middle\" class=\"form_label\" align=\"center\">");
					
					s.append("	<input type=\"text\" onkeypress=\"javascript:return(digitaNumero(this, event));\" maxlength=\"5\" name=\"ant_"+cor.getCodCor()+"\" id=\"ant_"+cor.getCodCor()+"\" value=\""+Pagina.trocaNull(ptcCor.getAntecedenciaPrimEmailPtccor())+"\" size=\"4\" "+ (getBloquearCampo() ? "disabled":"") + " >");
					if ( !getBloquearCampo()) {  
						//s.append("<img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"alert ('teste');open_calendar('difDias', document.forms[0].ant_"+cor.getCodCor()+", document.forms[0].dataLimitePtc.value)\">");							
						s.append("<img class=\"posicao\" title=\"Selecione a data\" src=\"../../images/icone_calendar.gif\" onclick=\"javascript:return(validarCampo('difDias',document.forms[0].ant_"+cor.getCodCor()+", document.forms[0].ativo_"+cor.getCodCor()+"));\">");
					}
					s.append("</td>");
					s.append("<td valign=\"middle\" class=\"form_label\" align=\"center\">");
					s.append("<input type=\"text\" onkeypress=\"javascript:return(digitaNumero(this, event));\" maxlength=\"5\" name=\"freq_"+cor.getCodCor()+"\" id=\"freq_"+cor.getCodCor()+"\" value=\""+Pagina.trocaNull(ptcCor.getFrequenciaEnvioEmailPtccor())+"\" size=\"4\" " + (getBloquearCampo() ? "disabled":"") + ">");
					s.append("</td>");
					s.append("<td valign=\"middle\" class=\"form_label\" align=\"center\">");
					s.append("<input type=\"checkBox\" class=\"form_check_radio\" value=\"S\" name=\"ativo_"+cor.getCodCor()+"\" id=\"ativo_"+cor.getCodCor()+"\"   onclick=\"validaPontoCritico("+cor.getCodCor()+");\"  "); 

					if (!"N".equals(ptcCor.getIndAtivoEnvioEmailPtccor()) && getNovoPontoCritico() == false) {
						s.append(" checked"); 
					}

					s.append(" " + (getBloquearCampo() ? "disabled=\"true\"":"") + ">");
					s.append("</td>");
					
					s.append("</tr>");
				}			
			}
			
			s.append("<!--<tr>");
			s.append("	<td valign=\"middle\" class=\"form_label\" align=\"center\">");
			s.append("		<img src=\"../../images/pcChecked1.png\">");
			s.append("	</td>");
			s.append("	<td valign=\"middle\" class=\"form_label\" align=\"center\" colspan=\"2\">Concluído</td>");
			s.append("	<td valign=\"middle\" class=\"form_label\" align=\"center\">&nbsp;</td>");
			s.append("</tr>");
			s.append("<tr>");
			s.append("	<td valign=\"middle\" class=\"form_label\" align=\"center\">");
			s.append("		<img src=\"../../images/pcPretoFixo1.png\">");
			s.append("	</td>");
			s.append("	<td valign=\"middle\" class=\"form_label\" align=\"center\" colspan=\"2\">Limite Ultrapassado</td>");
			s.append("	<td valign=\"middle\" class=\"form_label\" align=\"center\">");
			s.append("		<input type=\"checkBox\" class=\"form_check_radio\" name=\"ativoPreto\" id=\"ativoPreto\" ");
			if ("S".equals(configMailVencto.getAtivoCfgm())) {
				s.append(" checked"); 
			}
			s.append("disabled >");					
			s.append("	</td>");
			s.append("</tr>");	
			s.append("--></table>");
			s.append("</td>");
			s.append("</tr>");	
			writer.print(s.toString());
    	} catch (ECARException e) {
    		Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
    	}
    }
    
    /**
     * Gera html do usuário da última manutenção.<br>
     * 
     * @author N/C
	 * @since N/C
     * @throws IOException
     * @version N/C
    */
    public void geraHTMLAcompRelatorioArel() throws IOException{
    	JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
    	try{
    		 /*  
    		 *  Verificar o grupo de acesso que esse usuario faz parte e quais as funções
    		 *  de acompanhamento que ele pode representar
    		 *  
    		 *  Como a funcao de acompanhamento é ligada ao relatorio. O código passado é o 
    		 *  codArel (codigo d o relatorio em que o usuario está dando parecer) 
    		 *  mas o texto exibido é o label do Tipo funcao de acompanhamento.
    		 */ 
    		if (getAcompReferenciaItemAri() != null ) {
    			
    	    	AcompReferenciaItemDao ariDao = new AcompReferenciaItemDao(request);
    	    	
    	    	List listTpfa = ariDao.getAcompRelatorioArelOrderByFuncaoAcomp(getAcompReferenciaItemAri());
    	    	AcompRelatorioDao arelDao = new AcompRelatorioDao(request);
    	    	int numOpcoes =0;
    	    	StringBuffer strTemp = new StringBuffer(""); 
    	    	
    	    	AcompRelatorioArel selectedArel = new AcompRelatorioArel();
    	    	if (getPontoCriticoPtc() !=null && getPontoCriticoPtc().getAcompRelatorioArel()!=null  )
    	    		selectedArel = getPontoCriticoPtc().getAcompRelatorioArel();
    	    	
    	    	if (listTpfa!=null ){
    	    		//Iterator<TipoFuncAcompTpfa> it = listTpfa.iterator();
    	    		Iterator<AcompRelatorioArel> it = listTpfa.iterator();
    	    	
    	    		s.append("	<tr>");
    	    		s.append("		<td class=\"label\">");
    	    		if (atributo.iGetObrigatorio() != null && atributo.iGetObrigatorio().booleanValue() == true)
    	                s.append("* ");
    	    		s.append("Função de Acompanhamento</td>\n");
    	    		s.append("		<td>");
    	    		s.append("			<select name=").append(atributo.iGetNome() + " "+ (getBloquearCampo() ? "disabled=\"true\"":"") + "> \n" );
    				
    	    		while (it.hasNext()) {
    	    			AcompRelatorioArel arel = (AcompRelatorioArel) it.next();
    	    			if (arelDao.podeGravarRelatorio(seguranca.getUsuario(), arel.getTipoFuncAcompTpfa(), getAcompReferenciaItemAri() , arel ) == AcompRelatorioDao.OPERACAO_PERMITIDA){
    		        		String selected = "";
    		        		
    		        		if (selectedArel.equals(arel) ){
    		        			selected = "selected"; 
    		        		}
    		        		
    		        		
    		        		strTemp.append("				<option value=\"" + arel.getCodArel() + "\" "+ selected +"   > " + arel.getTipoFuncAcompTpfa().getLabelTpfa()  +" </option>\n");
    	    				numOpcoes++;
    		        	}
    				}
    	    		
    	    		/* Exibe uma opção vazia caso o usuario seja mais de um tipo de funcao de acompanhamento */
    	    		if (numOpcoes >1) {
    	        		s.append("				<option value=\"\">   </option>\n");
    	    		} else if (numOpcoes==0 && selectedArel!= null){
    	    			s.append("				<option value=\""+ selectedArel.getCodArel()+"\"> "+  selectedArel.getTipoFuncAcompTpfa().getLabelTpfa() +" </option>\n");
    	    			
    	    		}

    	    		s.append(strTemp.toString());	        		
    	    		s.append("			</select>");
    	    		s.append("		</td>");
    	    		s.append("	</tr>");	
    	    	} else {
    	    		//Redirecionar para a página de acesso indevido
    	    		
    	    	}
    		}
    		writer.print(s.toString());
    	}
    	catch (ECARException e) {
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
    public void geraHTMLAtributoLivre() throws ECARException, JspException{

    	if(atributo.iGetGrupoAtributosLivres() != null){

        	JspWriter writer = this.page.getOut();

        	SisGrupoAtributoSga grupoAtributo = atributo.iGetGrupoAtributosLivres(); 
            	
        	Input input = new Input(writer);
        	input.setPage(page);
        	input.setRequest(request);
        	input.setLabel(atributo.iGetLabel());
        	input.setTipo(grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().intValue());
        	input.setObrigatorio(atributo.iGetObrigatorio().booleanValue() ? "S" : "N"); //Obrigatoriedade estï¿½ no atributo
        	
        	input.setPathRaiz(this.getContextPath());
        	        	
        	input.setLabelObrigatorio("*");
        	input.setSize(atributo.iGetTamanhoConteudoAtrib().toString());

        	if (getBloquearCampo()) {
        		input.setDisabled("S");
        	}
        	input.setNome("a" + grupoAtributo.getCodSga().toString());
        	input.setClassLabel("label");
        	
        	List aributosLivresSelecionados = new ArrayList();

        	if(getPontoCriticoPtc().getPontoCriticoSisAtributoPtcSatbs() != null){
    	    	Iterator itAtribLivres = getPontoCriticoPtc().getPontoCriticoSisAtributoPtcSatbs().iterator();
    	    	while(itAtribLivres.hasNext()){
    	    		PontoCriticoSisAtributoPtcSatb atributo = (PontoCriticoSisAtributoPtcSatb) itAtribLivres.next();
    	    		AtributoLivre atributoLivre = new AtributoLivre();
    	    		atributoLivre.setInformacao(atributo.getInformacao());
    	    		atributoLivre.setSisAtributoSatb(atributo.getSisAtributoSatb());
    	    		aributosLivresSelecionados.add(atributoLivre);
    	    	}
        	}
        	
        	input.setSelecionados(aributosLivresSelecionados);
        	input.setSisAtributo((SisAtributoSatb)grupoAtributo.getSisAtributoSatbs().iterator().next());
        	input.setContexto(getContextPath());
        	if(atributo.iGetDica() != null)
        		input.setDica(atributo.iGetDica());
        	
        	input.doStartTag();
        	
        	Options options = new Options(writer);
        	
    		List opcoes = new ArrayList();
    		String selectedCodSapadrao = "";
    		if(grupoAtributo.getCodSga() != null && grupoAtributo.getCodSga().longValue() != 1){
    			if(grupoAtributo.getSisTipoOrdenacaoSto() != null){
    				List<SisAtributoSatb> innerList = new SisGrupoAtributoDao(null).getAtributosOrdenados(grupoAtributo);
    				for (SisAtributoSatb sisAtributoSatb : innerList) {
						if (sisAtributoSatb.isAtivo()){
							opcoes.add(sisAtributoSatb);
						}
					}
    			}
    		} 
        	
    		if(!opcoes.isEmpty()) {
    	    	options.setOptions(opcoes);
    	    	options.setValor("codSatb");
    	    	options.setLabel("descricaoSatb");
    	    	//options.setImagem(getContextPath() + "/images/relAcomp/");
    	    	if(atributo.iGetDica() != null){
    	    		options.setContexto(this.getContextPath());
    	    		options.setDica(atributo.iGetDica());
    	    	}
    	    	options.setParent(input);
    	    	options.setNome("a" + grupoAtributo.getCodSga().toString());
    	    	options.doStartTag();
    		}
    		
    		input.doEndTag();
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
     * @param valorText
     * @param dica
     * @param maxlength
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
	             .append("onclick=\"document.form.nome")
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
     * Cria uma linha no formulário com um campo SELECT. <br>
     * 
     * @author n/c, rogerio
     * @since 0.1, n/c
     * @version 0.2, 22/03/2007
     * @param nome
     * @param label
     * @param valor
     * @param scripts
     * @param dica 
     * @param opcoes
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
       
            boolean test = page == page;
            
            if (getBloquearCampo())
                s.append(" disabled=\"true\"");
            
            
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
                        s.append(" disabled=\"true\"");
                    if (chaveValor[0].equals(valor))
                        s.append(" checked ");
                    s.append(">");
                    s.append(chaveValor[1]);                    
                }
                s.append("&nbsp;<input type=\"button\" name=\"buttonLimpar\" value=\"Limpar\" class=\"botao\" ")
                .append("onclick=\"limparRadioButtons(document.getElementsByName('" + nome + "'));\"");
                if (getBloquearCampo())
	    	   		s.append(" disabled=\"true\"");
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
     * @param name
     * @param onclick
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
     * @param label 
     * @param size
     * @param maxlength
     * @param dica
     * @param valor
     */
    public void criaInputText(String nome, String label, String size, String maxlength, String valor, String dica) {
        JspWriter writer = this.page.getOut();
        StringBuffer s   = new StringBuffer();
        
        try {
        	
        	
        	if(atributo.iGetTamanhoConteudoAtrib() != null){
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
     * @param size
     * @param label
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
     * @param valor 
     * @param maxlength
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
     * @param valor 
     * @param label
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
     * @param arg0
     * @author N/C
	 * @since N/C
	 * @version N/C
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
     * @param desabilitar
     * @author N/C
	 * @since N/C
	 * @version N/C
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
    public PontoCriticoPtc getPontoCriticoPtc() {
        return pontoCriticoPtc;
    }
    
    /**
     * Atribui valor especificado para ItemEstruturaIett itemEstrutura.<br>
     * 
     * @param pontoCriticoPtc
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setItemEstrutura(PontoCriticoPtc pontoCriticoPtc) {
        this.pontoCriticoPtc = pontoCriticoPtc;
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
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param seguranca
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
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param exibirBotoes
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

		//não bloqueia o campo caso seja o cadastro
		//de um novo ponto crítico
		//alteração requerida pelo mantis  0011103
		if(getNovoPontoCritico() == true){
			return false;
		}
		
		
		// caso a interface nï¿½o seja de consulta, deve verificar se o planejamento esteja bloqueado.
		if(getIett().getIndBloqPlanejamentoIett() != null && 
				"S".equals(getIett().getIndBloqPlanejamentoIett())) {
			// Se o planejamento estï¿½ bloqueado, ï¿½ verificado a configuraï¿½ï¿½o do atributo para 
			// ver se ele pode ser editado mesmo com o planejamento bloqueado.
			if (atributo.iGetBloqueado()) {
				ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null); 
				boolean podeEditar = itemEstruturaDao.podeEditarAtributoBloqueadoNaEstrutura(getIett(), atributo, seguranca.getUsuario(), seguranca.getGruposAcesso() );
				return !podeEditar; //quando o campo estï¿½ liberado retorna falso
			}
		}
		// Por default, o campo pode ser editado.
		return false;

	}
	
	
	
        /**
         *
         * @return
         */
        public ItemEstruturaIett getIett(){
		return iett;
	}
	
        /**
         *
         * @param iett
         */
        public void setIett(ItemEstruturaIett iett){
		this.iett = iett;
	}
	
        /**
         *
         * @return
         */
        public HttpServletRequest getRequest(){
		return request;
	}
	
        /**
         *
         * @param acompReferenciaItemAri
         */
        public void setAcompReferenciaItemAri(AcompReferenciaItemAri acompReferenciaItemAri){
		this.acompReferenciaItemAri = acompReferenciaItemAri;
	}
	
        /**
         *
         * @return
         */
        public AcompReferenciaItemAri getAcompReferenciaItemAri(){
		return acompReferenciaItemAri;
		
	}

        /**
         *
         * @return
         */
        public ValidaPermissao getValidaPermissao() {
		return validaPermissao;
	}

        /**
         *
         * @param validaPermissao
         */
        public void setValidaPermissao(ValidaPermissao validaPermissao) {
		this.validaPermissao = validaPermissao;
	}

        /**
         *
         * @param pontoCriticoPtc
         */
        public void setPontoCriticoPtc(PontoCriticoPtc pontoCriticoPtc) {
		this.pontoCriticoPtc = pontoCriticoPtc;
	}

        /**
         *
         * @param request
         */
        public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
        
        
    /**
     * Indica se está sendo criado um novo ponto crítico ou seja,
     * que o form para a criação de um novo ponto crítico está 
     * sendo rendereizado.
     *      
     * @return
     */
    public Boolean getNovoPontoCritico() {
  		return novoPontoCritico;
  	}

  	public void setNovoPontoCritico(Boolean novoPontoCritico) {
   		this.novoPontoCritico = novoPontoCritico;
  	}
     
        
        
}