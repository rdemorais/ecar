package ecar.taglib.util;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

import comum.util.FileUpload;
import comum.util.Util;

import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

public class UploadArquivoTag implements Tag{

	private PageContext page = null;
	private JspWriter writerParametro = null;
	private Logger logger = null;
	
	private String nomeCampo;
	
	private String labelCampo;
	private String contextPath;
	private String pathRaiz;
	private String arquivo;
	private String desabilitado; //Recebe conteúdo da variável _disabled da página.
	private String obrigatorio; //Recebe conteúdo da variável _obrigatorio da página.
	private String ehTabelaCor;
	private String nomeCorTabela;
	
	/**
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public UploadArquivoTag() {
    	super();
    	ehTabelaCor = "false";
    	this.logger = Logger.getLogger(this.getClass());
    }
    
    /**
     * Atribui valor especificado para JspWriter writerParametro.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param writer
     */
    public UploadArquivoTag(JspWriter writer) {
    	this.writerParametro = writer;
    }

	public int doStartTag() throws JspException {
		
		JspWriter writer = null;
    	if(writerParametro != null) {
    		writer = writerParametro;
    	} else {
        	writer = this.page.getOut();
    	}
    	
    	Map<Long, String> mapArquivosUsuarios = null;
    	UsuarioUsu usuario = null;
    	String hashNomeArquivo = null;
    	String hashNomeArquivo2 = null;
    	
    	StringBuffer s = new StringBuffer();
    	StringBuffer sJavascript = new StringBuffer();    	
    	StringBuffer arquivoURLEncoder = new StringBuffer();
    	
        try {

        	 	usuario = ((ecar.login.SegurancaECAR)page.getSession().getAttribute("seguranca")).getUsuario();       	        	
        	if (arquivo != null && !"".equals(arquivo)){
        		
        		// substitui por URL Encoding
        		arquivoURLEncoder.append(URLEncoder.encode(arquivo, Dominios.ENCODING_DEFAULT));
        		
        		hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, arquivo);				  
				Util.adicionarMapArquivosAtuaisUsuarios(usuario, hashNomeArquivo, pathRaiz, arquivo);
        		        		
//        		hashNomeArquivo2 = Util.calcularHashNomeArquivo(pathRaiz, arquivoURLEncoder.toString());
//        		Util.adicionarMapArquivosAtuaisUsuarios(usuario, hashNomeArquivo2, pathRaiz, arquivoURLEncoder.toString());
        		        		
        		s.append("    <input type=\"hidden\" id=\"hidNomeArquivo" + nomeCampo + "\" name=\"hidNomeArquivo" + nomeCampo + "\" value=\"" + FileUpload.getNomeArquivo(arquivo) + "\">");
        		
        		s.append("<tr>");
        		s.append("  <td valign=\"top\" id=\"lblAtual_" + nomeCampo + "\" class=\"label\">" + labelCampo + " Atual:</td>");
        		s.append("  <td  id=\"tb_" + nomeCampo + "\">");        		
        		s.append("    <table>"); 
        		s.append("      <tr>");
        		s.append("        <td id=\"img_" + nomeCampo + "\">");
        		s.append("          <img src=\"" + contextPath + "/DownloadFile?tipo=open&RemoteFile=" + hashNomeArquivo + "\">");
        		s.append("        </td>");	        				
        		s.append("      </tr>");	 										
        		s.append("      <tr>");		
        		s.append("        <td id=\"link_" + nomeCampo + "\">");
//        		s.append("          <a href=\"" + contextPath + "/DownloadFile?RemoteFile=" + pathRaiz + arquivoURLEncoder.toString() + "\">" +  FileUpload.getNomeArquivo(FileUpload.getNomeArquivoOriginal(arquivo)) + "</a>");
        		s.append("          <a href=\"" + contextPath + "/DownloadFile?RemoteFile=" + hashNomeArquivo + "\">" +  FileUpload.getNomeArquivo(FileUpload.getNomeArquivoOriginal(arquivo)) + "</a>");
        		s.append("        </td>");
        		s.append("      </tr>");	
        		s.append("      <tr>");										
        		s.append("        <td id=\"btn_" + nomeCampo + "\">");     			
        		s.append("        <input type=\"button\" value=\"Excluir " + labelCampo + "Atual\" onclick=\"javascript:aoClicarExcluir_" + nomeCampo + "('" + nomeCampo + "', '" + labelCampo + "')\"" + desabilitado + ">"); 
        		s.append("        </td>");		        				
        		s.append("      </tr>");
        		s.append("    </table>"); 
        		s.append("  </td>");		
        		s.append("</tr>");	        		        	
        	}
   		
        	s.append("<tr>");
        	s.append("  <td id=\"lbl_" + nomeCampo + "\" class=\"label\">");
        	if (arquivo != null && !"".equals(arquivo)){
    		
        		s.append("Alterar " + labelCampo + " para");
        	}
        	else{
        		s.append(obrigatorio + " " + labelCampo);
        	}        	
        	s.append("  </td>");	
        	s.append("  <td>");
        	if (ehTabelaCor!=null && ehTabelaCor.equals("true")) {
        		if ((arquivo==null || arquivo.trim().length()==0) && nomeCorTabela.indexOf("null")==-1) {
        			s.append("<img border=\"0\" src=\"../../images/").append(nomeCorTabela).append("\">");
        		}
        	}
        	s.append("    <input type=\"file\" id=\"" + nomeCampo + "\" name=\"" + nomeCampo + "\" onchange=\"javascript:aoSelecionarArquivo_" + nomeCampo + "('" + nomeCampo + "')\" " + desabilitado + ">");
        	s.append("    <input type=\"hidden\" id=\"hid" + nomeCampo + "\" name=\"hid" + nomeCampo + "\" value=\"\">"); 
        	s.append("  </td>");
        	s.append("</tr>");
    		
    		s.append("<tr>");
    		s.append("  <td/>");
    		s.append("  <td style=\"color:#FF0000\" id=\"msg" + nomeCampo + "\">");
    		s.append("  </td>");		        				
    		s.append("</tr>");
    		
    		s.append("<tr>");
    		s.append("  <td><br></td>");
    		s.append("  <td>");
    		s.append("    <br>");
    		s.append("  </td>");		        				
    		s.append("</tr>");
    		        	
        	writer.print(s.toString());
        	
        	sJavascript.append("<script language=\"javascript\">");
        	sJavascript.append("  function aoClicarExcluir_" + nomeCampo + "(id, valorLabel){");
//        	sJavascript.append("    if (!confirm(\"O arquivo será excluído. Confirma a exclusão?\")){");        	
//        	sJavascript.append("      return false;");
//        	sJavascript.append("    }");
        	sJavascript.append("    document.getElementById(\"lblAtual_\" + id).innerHTML = '';");
        	sJavascript.append("    document.getElementById(\"tb_\" + id).innerHTML = '';");
        	sJavascript.append("    document.getElementById(\"lbl_\" + id).innerHTML = valorLabel;");
        	sJavascript.append("    document.getElementById(\"hid\" + id ).value = '_excluir';");
        	sJavascript.append("    document.getElementById(\"msg\" + id ).innerHTML = 'A exclusão será efetuada na atualização do formulário';");
        	sJavascript.append("  }");
        	
           	sJavascript.append("  function aoSelecionarArquivo_" + nomeCampo + "(id){");
           	sJavascript.append("    if (document.getElementById(id).value != '')");
           	if (arquivo != null && !arquivo.equals("")){
           		sJavascript.append("      document.getElementById(\"msg\" + id ).innerHTML = 'A alteração será efetuada na atualização do formulário';");
           	}
           	else{
           		sJavascript.append("      document.getElementById(\"msg\" + id ).innerHTML = 'A inclusão será efetuada na atualização do formulário';");
           	}
           	sJavascript.append("  }");
           	
        	sJavascript.append("</script>");
        	
        	writer.print(sJavascript.toString());
      
		} catch (Exception e) {
	    	this.logger.error(e);
	    	try {
	            writer.print("Erro na geração da Combo: " + e.getMessage());
	            this.logger.error(e);
	        } catch (IOException ioE) {
	            this.logger.error(ioE);
	        }
	
	    }
        
    	return Tag.EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException {		
		return Tag.EVAL_PAGE;
	}

	public Tag getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public void release() {
		// TODO Auto-generated method stub
		
	}

	public void setPageContext(PageContext pc) {
		this.page = pc;
	}

	public void setParent(Tag t) {
		// TODO Auto-generated method stub
		
	}
	
	public PageContext getPage() {
		return page;
	}

	public void setPage(PageContext page) {
		this.page = page;
	}

	public JspWriter getWriterParametro() {
		return writerParametro;
	}

	public void setWriterParametro(JspWriter writerParametro) {
		this.writerParametro = writerParametro;
	}
	
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public String getLabelCampo() {
		return labelCampo;
	}

	public void setLabelCampo(String labelCampo) {
		this.labelCampo = labelCampo;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getPathRaiz() {
		return pathRaiz;
	}

	public void setPathRaiz(String pathRaiz) {
		this.pathRaiz = pathRaiz;
	}

	public String getArquivo() {
		return arquivo;
	}

	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}

	public String getDesabilitado() {
		return desabilitado;
	}

	public void setDesabilitado(String desabilitado) {
		this.desabilitado = desabilitado;
	}

	public String getObrigatorio() {
		return obrigatorio;
	}

	public void setObrigatorio(String obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	public String getEhTabelaCor() {
		return ehTabelaCor;
	}

	public void setEhTabelaCor(String ehTabelaCor) {
		this.ehTabelaCor = ehTabelaCor;
	}

	public String getNomeCorTabela() {
		return nomeCorTabela;
	}

	public void setNomeCorTabela(String nomeCorTabela) {
		this.nomeCorTabela = nomeCorTabela;
	}

}
