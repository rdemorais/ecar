package ecar.taglib.combos;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import ecar.dao.AreaDao;
import ecar.pojo.AreaAre;

/**
 * @author rodrigo.hjort
 */
public class AreaTag implements Tag {

	private String selected = "";
	private PageContext page = null;
	private AreaDao areaDao = new AreaDao(null);
	private AreaAre area = new AreaAre();
	
	/**
	 * Construtor.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 */
	public AreaTag() {
		super();
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
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param arg0
	 */
	public void setParent(Tag arg0) {
	}

	/**
	 * Retorna null.<br>
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
	 * Inicializa a montagem da tag para ser adicionada na tela de HTML.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
     * @return int
     * @throws JspException
	 */
	public int doStartTag() throws JspException {
		JspWriter writer = this.page.getOut();

		try {
			List lista = areaDao.listar(AreaAre.class, new String[] {"nomeAre","asc"});
			writer.println("<option value=\"\"" + (selected.equals("") ? " selected" : "") + "></option>");
			if (lista != null) {
				for (int ii = 0; ii < lista.size(); ii++) {
					area = (AreaAre) lista.get(ii);
					String codigo = area.getCodAre().toString();
					writer.println("<option value=\"" + codigo + "\"" +
						(selected.equalsIgnoreCase(codigo) ? " selected" : "") + ">" + 
						area.getNomeAre() + "</option>");
				}
			}
		} catch (Exception e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		}
		return Tag.EVAL_BODY_INCLUDE;
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
	public int doEndTag() throws JspException {
		return Tag.EVAL_PAGE;
	}

	/**
	 * Atribui null para selected.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 */
	public void release() {
		this.selected = null;
	}

	/**
	 * Retorna String selected.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getSelected() {
		return selected;
	}

	/**
	 * Atribui valor epsecificado para selected.<br>
	 * 
         * @param string
         * @author N/C
     * @since N/C
     * @version N/C
	 */
	public void setSelected(String string) {
		selected = string;
	}

}
