/*
 * Criado em 06/04/2005
 *
 */
package ecar.taglib.util;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import ecar.exception.ECARException;

/**
 * @author felipev
 *
 */
public class BarraLinksRelatorioItens extends TagSupport{

	private static final long serialVersionUID = 2869225621544161026L;
	
	private String chamarPagina;
	private String complementoRequest;
	private List itensBarra;
	private int indexAtivo;
	private String semLinks;
	
	private static final String ABA_HABILITADA = "abarelatoriohabilitada";
	private static final String ABA_DESABILITADA = "abarelatoriodesabilitada";

	/**
	 * Inicializa a montagem da tag para ser adicionada na tela de HTML.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
	 */
    @SuppressWarnings("empty-statement")
    @Override
	public int doStartTag() throws JspException {
        JspWriter writer = this.pageContext.getOut();
        try {
            StringBuffer s = new StringBuffer();
            
            if(itensBarra != null && itensBarra.size() > 0){
            	
            	s.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" id=\"abastipoacompanhamento\">")
            	 .append("<tr>")
            	 .append("<td class=\"texto_negrito\">");
            	
            	Iterator itItens = itensBarra.iterator();
    			
            	int index = 0;
        		while (itItens.hasNext()) {
        			String item = (String) itItens.next();
        			
        			String situacaoAba = ABA_DESABILITADA;
                    if(index == indexAtivo) {
                        situacaoAba = ABA_HABILITADA;
                    }
                    
                    String comp = "";
                    if(complementoRequest != null)
                    	comp = complementoRequest;
                    
                    criaAba(item, getChamarPagina() + "?proximaAba=" + index + comp, situacaoAba, s);
                    index++;
        		}
        		
                s.append("</td>")
                 .append("</tr>")
                 .append("</table>");
        	}

            writer.print(s.toString());
        } catch (Exception e) {
      ;
        }
        return Tag.SKIP_BODY;

    }
    
	/**
	 * Cria Aba.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param String label
	 * @param String pagina
	 * @param String situacaoAba
	 * @param StringBuffer s
	 * @throws ECARException
	 */
    private void criaAba(String label, String pagina, String situacaoAba, StringBuffer s) throws ECARException{
        s.append("<table class=\"").append(situacaoAba).append("\">")
         .append("<tr>")
         .append("<td nowrap>");
        
        if(!"S".equals(getSemLinks()) && situacaoAba.equals(ABA_DESABILITADA)){
        	s.append("<a href=\"").append(pagina).append("\">");
        }

		s.append(label);
		
        if(!"S".equals(getSemLinks())){
        	s.append("</a>");
        }
        
        s.append("</td>")
         .append("</tr>")
         .append("</table>");            
    }

    /**
     * Encerra Tag.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
     */
    @Override
    public int doEndTag() throws JspException {
        /* processa o restante da página jsp */
        return Tag.EVAL_PAGE;
    }

    /**
     * Retorna String chamarPagina.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String
     */
    public String getChamarPagina() {
		return chamarPagina;
	}

    /**
     * Atribui valor especificado para String chamarPagina.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param chamarPagina
     */
	public void setChamarPagina(String chamarPagina) {
		this.chamarPagina = chamarPagina;
	}

	/**
	 * Retorna int indexAtivo.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 */
	public int getIndexAtivo() {
		return indexAtivo;
	}

	/**
	 * Atribui valor especificado para int indexAtivo.<br>
	 * 
         * @param indexAtivo
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setIndexAtivo(int indexAtivo) {
		this.indexAtivo = indexAtivo;
	}

	/**
	 * Retorna List itensBarra.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return List
	 */
	public List getItensBarra() {
		return itensBarra;
	}

	/**
	 * Atribui valor especificado para List itensBarra.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param itensBarra
	 */
	public void setItensBarra(List itensBarra) {
		this.itensBarra = itensBarra;
	}

	/**
	 * Retorna String complementoRequest
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getComplementoRequest() {
		return complementoRequest;
	}

	/**
	 * Atribui valor especificado para String complementoRequest.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param complementoRequest
	 */
	public void setComplementoRequest(String complementoRequest) {
		this.complementoRequest = complementoRequest;
	}

	/**
	 * Retorna String semLinks.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getSemLinks() {
		return semLinks;
	}

	/**
	 * Atribui valor especificado para String semLinks.<br>
	 * 
         * @param semLinks
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setSemLinks(String semLinks) {
		this.semLinks = semLinks;
	}
}