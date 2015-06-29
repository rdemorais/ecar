/*
 *
 */
package ecar.taglib.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

import comum.util.ConstantesECAR;
import comum.util.FileUpload;
import comum.util.Util;

import ecar.dao.ConfiguracaoDao;
import ecar.exception.ECARException;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.DemAtributoDema;
import ecar.pojo.ObjetoDemanda;
import ecar.pojo.RegDemandaRegd;
import ecar.pojo.UsuarioUsu;

/** 
 * Tag para preencher a tela de consulta de demanda.<br>
 * 
 *  @author José, Fernandes
 */
public class FormConsultaRegDemandaTag implements Tag {

	private ObjetoDemanda atributo;
	private RegDemandaRegd regDemanda;
	private HttpServletRequest request;
	
	private String contextPath;
    
   
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
            	if(atributo.iGetGrupoAtributosLivres() != null){
            		geraHTMLAtributoLivre();
            	} else{
            		geraHTMLAtributosFixos();
            	}
        } catch (Exception e) {
        	// não é necessário lançar exceção aqui
        }
        return Tag.EVAL_BODY_INCLUDE;
    }

	
	/**
     * Gera html dos atributos fixos.<br>
     *
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
	public void geraHTMLAtributosFixos() {
        try {
        	if(atributo.iGetNome().equals("indAtivoRegd")){
        		String valorAtributoAtivo = "";
        		if(atributo.iGetValor(getRegDemanda()).equals("N")) {
        			valorAtributoAtivo = "Não";        		
        		} else if(atributo.iGetValor(getRegDemanda()).equals("S")){
        			valorAtributoAtivo = "Sim";
        		}
        		criaLabelText(atributo.iGetLabel(), valorAtributoAtivo);
        	} else{
        		criaLabelText(atributo.iGetLabel(), atributo.iGetValor(getRegDemanda()));
        	}
        } catch (ECARException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
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
    public void geraHTMLAtributoLivre() throws ECARException, JspException, UnsupportedEncodingException, NoSuchAlgorithmException{
		String informacaoAtbdem = "";		
		Iterator itRegDem =  regDemanda.getDemAtributoDemas().iterator();
		
			
		ConfiguracaoCfg configura = new ConfiguracaoDao(request).getConfiguracao();
		
		if(atributo.iGetGrupoAtributosLivres() != null 
    		&& (atributo.iGetGrupoAtributosLivres().getIndAtivoSga()!=null 
    			&&  atributo.iGetGrupoAtributosLivres().getIndAtivoSga().equals("S")) ){
			
			while (itRegDem.hasNext()) {
				DemAtributoDema demAtributoDema = (DemAtributoDema) itRegDem.next();
					
				if (demAtributoDema.getSisAtributoSatb().getSisGrupoAtributoSga().equals(atributo.iGetGrupoAtributosLivres())){
					
					if (atributo.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXT)) ||
						atributo.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.TEXTAREA)) ||
					 	atributo.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.MULTITEXTO)) ||
					 	atributo.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO)) ) {
						 
						informacaoAtbdem = informacaoAtbdem + demAtributoDema.getInformacao() + configura.getSeparadorCampoMultivalor();
					
					} else if (atributo.iGetGrupoAtributosLivres().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.IMAGEM))) {
									
						String arquivo = demAtributoDema.getInformacao();
						
						String nomeArquivo = "";
		           		String width = "";
		           		String heigth = "";
		           	    if(arquivo.lastIndexOf("\\") != -1) {
		           			nomeArquivo = arquivo.substring(arquivo.lastIndexOf("\\") + 1); 
		           		} else 
		           			if (arquivo.lastIndexOf("/") != -1)     
		           	        	nomeArquivo = arquivo.substring(arquivo.lastIndexOf("/") + 1);
		           	    
		           	    //Modificado por José André Fernandes
		           	    if (arquivo.lastIndexOf("=") != -1)     
		           	    	arquivo = arquivo.substring(arquivo.lastIndexOf("=") + 1);
						
		           		String raiz = new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getRaizUpload();
		           		UsuarioUsu usuario = ((ecar.login.SegurancaECAR)page.getSession().getAttribute("seguranca")).getUsuario();
		           		
		           		String urlPath = "";
		           		
		           		try{
		           			File file = new File(arquivo);
			           		FileImageInputStream input = new FileImageInputStream(file);  
		       		        Iterator i = ImageIO.getImageReaders(input);  
		       		        
		       		        String hashNomeArquivo = null;
		       		        String hashNomeURLEncoder = null;
		       		        
		       		        StringBuffer s = new StringBuffer();
		       		        
		       		        if (file.exists()){
		       		        
		       		        	hashNomeArquivo = Util.calcularHashNomeArquivo(raiz, arquivo);
				           		hashNomeURLEncoder = Util.calcularHashNomeArquivo(raiz, URLEncoder.encode(arquivo,ConstantesECAR.ENCODE_PADRAO));
				           		
				           		Util.adicionarMapArquivosAtuaisUsuarios(usuario, hashNomeArquivo, raiz, arquivo);
				           		Util.adicionarMapArquivosAtuaisUsuarios(usuario, hashNomeURLEncoder, raiz, arquivo);
				           		
				           		urlPath = contextPath +"/DownloadFile?RemoteFile=" +hashNomeArquivo;
				           		
				           		if (arquivo!=null && arquivo.length()>0) {
					           		arquivo = contextPath +"/DownloadFile?RemoteFile=" + hashNomeURLEncoder;
					           	}
				           		
			       		        //se o arquivo existir e for uma imagem
			       		        if (i.hasNext()) {				           		
									informacaoAtbdem = informacaoAtbdem + "<img name=\"imagem" + atributo.iGetNome() + "\" src=\"" + 
									urlPath + "\" width=\"40\"" + 
					           		"heigth=\"40\"" + "> ";
				           									           		
					           		s.append("<a href=\"").append(urlPath).append("\">").append(FileUpload.getNomeArquivoOriginal(nomeArquivo)).append("</a></div>");
					        		s.append("<br>\n");
					        		
					        		informacaoAtbdem = informacaoAtbdem + s.toString();
			       		        }	 
			       		        //se o arquivo existir mas não for uma imagem
			       		        else{		       		        	
					           		
					           		s.append("<a href=\"").append(urlPath).append("\">").append(FileUpload.getNomeArquivoOriginal(nomeArquivo)).append("</a></div>");
					        		s.append("<br>\n");
					           		
			//						informacaoAtbdem = informacaoAtbdem + demAtributoDema.getInformacao().substring(
			//								demAtributoDema.getInformacao().lastIndexOf("/")+1) +  ", ";
					        		
					        		informacaoAtbdem = informacaoAtbdem + s.toString();
			       		        }
		       		        }
		           		}
		           		catch(FileNotFoundException e){		           			
		           			throw new ECARException(e.getMessage());
		           		}
		           		catch (IOException e){
		           			throw new ECARException(e.getMessage());
		           		}
					} 
					else{
						
						informacaoAtbdem = informacaoAtbdem + demAtributoDema.getSisAtributoSatb().getDescricaoSatb() + configura.getSeparadorCampoMultivalor();
						
					}
				}
			}
		
			if (informacaoAtbdem.length() > 0){
				informacaoAtbdem = informacaoAtbdem.substring(0, informacaoAtbdem.length() - configura.getSeparadorCampoMultivalor().length()); 
			}
					
			criaLabelText(atributo.iGetLabel(), informacaoAtbdem);
			
		}
	
    }

    /**
     * Cria um label como coluna na tela de consulta de demandas . <br>
     * 
     * @author n/c, 
     * @param label 
     * @param valor
     */
    public void criaLabelText(String label, String valor) {
        JspWriter writer = this.page.getOut();
        StringBuffer s = new StringBuffer();
        try {
        	s.append("<TD align=\"right\"><b>");
            s.append(label);
            s.append("</b></TD>")
             .append("<TD>")
             .append(valor );                        
            s.append("</TD>");
            
            writer.print(s.toString());
        } catch (IOException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
        }
    }
       
    public int doEndTag() throws JspException {
        return Tag.EVAL_PAGE;
       }

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
        
        public HttpServletRequest getRequest() {
    		return request;
    	}


    	public void setRequest(HttpServletRequest request) {
    		this.request = request;
    	}
        
        public String getContextPath() {
    		return contextPath;
    	}


    	public void setContextPath(String contextPath) {
    		this.contextPath = contextPath;
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

   	public Tag getParent() {
   		return null;
   	}

   	public void setPageContext(PageContext arg0) {
   		this.page = arg0;
   	}

   	public void setParent(Tag arg0) {	
   	}
	
}