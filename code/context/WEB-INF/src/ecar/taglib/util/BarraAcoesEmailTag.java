package ecar.taglib.util;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import ecar.pojo.Email;

/**
 *
 * @author 70744416353
 */
public class BarraAcoesEmailTag extends TagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codEmail;
	private List lista; 
	
	@Override
	public int doStartTag() throws JspException {
		StringBuffer s = new StringBuffer();
		JspWriter writer = this.pageContext.getOut();
		try {
			if(lista.size() > 0){
				s.append("<div id=\"nav\"> <input type=\"button\" class=\"btnemail\" name=\"btnApagar\" onclick=\"javascript:apagar();\" value=\"");
				s.append(("".equals(codEmail))?"Apagar Selecionados\"":"Apagar Email\"");
				s.append("> ");
				if("".equals(codEmail)){
					s.append("<br><br> Selecionar: ");
					s.append("<a class=\"link\" href=\"#\" onclick=\"javascript:marcar(" + Email.MARCA_LIDO + ");\">Marcar como lida</a>, ");
					s.append("<a class=\"link\" href=\"#\" onclick=\"javascript:marcar(" + Email.MARCA_NLIDO + ");\">Marcar como n&atilde;o lida</a>");
				}
				s.append("</div>");
				writer.print(s.toString());
			}
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).error(e);
			e.printStackTrace();
		}
		return super.doStartTag();
	}

        /**
         *
         * @param codEmail
         */
        public void setCodEmail(String codEmail) {
		this.codEmail = codEmail;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		return super.doEndTag();
	}	
	
        /**
         *
         * @param lista
         */
        public void setLista(List lista) {
		this.lista = lista;
	}

}
