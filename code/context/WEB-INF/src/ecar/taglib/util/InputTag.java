/*
 * Created on 17/09/2004
 *
 */
package ecar.taglib.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * @author garten
 * 
 * TagLib para tratar campos input
 *
 */
public class InputTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3448973752966957420L;

	private Logger logger = Logger.getLogger(this.getClass());

	// constantes da sintaxe do html
	private final String ASPAS = "\"";
	private final String TYPE = " type=";
	private final String NAME = " name=";
	private final String VALUE = " value=";
	private final String SIZE = " size=";
	private final String MAXLENGTH = " maxlength=";
	private final String EXTRA = " ";
	private final String ALIGN = " align=";
	private final String COLSPAN = " colspan=";
	private final String ROWSPAN = " rowspan=";
	private final String LABEL = "";
	private final String END_LABEL = " &nbsp;";
	private final String INPUT = " <input ";
	private final String END_INPUT = " > ";
	private final String TD = " <td ";
	private final String END_TD = "</td>";
	private final String CLASS = " class=";
	private final String WIDTH = " width=";
	
	// opcoes do input
	private String itype = "text";
	private String iname = "inputTag";
	private String ivalue = "";
	private String isize = "10";
	private String imaxlength = "10";
	private String iextra = null;
	// opcoes do td do input
	private String ialign = "left";
	private String icolspan = null;
	private String irowspan = null;
	private String iclass = null;
	private String iwidth = "70%";

	// opcoes do label
	private String lname = "";
	// opcoes do td do label
	private String lalign = "right";
	private String lcolspan = null;
	private String lrowspan = null;
	private String lclass = null;
	private String lwidth = "30%";

	//private String checked;
	//private String disabled;
	//private String readonly;
	
	/**
	 * Inicializa a montagem da tag para ser adicionada na tela de HTML.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
	 */
    @Override
	public int doStartTag() throws JspException {
		JspWriter writer = this.pageContext.getOut();

		StringBuffer input = new StringBuffer();
		StringBuffer tdLabel = new StringBuffer();
		StringBuffer tdInput = new StringBuffer();

		// opcoes do input
		if (this.getItype() != null)
			input.append(TYPE).append(ASPAS).append(this.getItype()).append(ASPAS);
		
		if (this.getIname() != null)
			input.append(NAME).append(ASPAS).append(this.getIname()).append(ASPAS);
		
		if (this.getIsize() != null)
			input.append(SIZE).append(ASPAS).append(this.getIsize()).append(ASPAS);
		
		if (this.getImaxlength() != null)
			input.append(MAXLENGTH).append(ASPAS).append(this.getImaxlength()).append(ASPAS);
		
		if (this.getIvalue() != null)
			input.append(VALUE).append(ASPAS).append(this.getIvalue()).append(ASPAS);

		if (this.getIextra() != null)
			input.append(EXTRA).append(ASPAS).append(this.getIextra()).append(ASPAS);

		//opcoes do td do input
		if (this.getIalign() != null)
			tdInput.append(ALIGN).append(ASPAS).append(this.getIalign()).append(ASPAS);
		
		if (this.getIcolspan() != null)
			tdInput.append(COLSPAN).append(ASPAS).append(this.getIcolspan()).append(ASPAS);
		
		if (this.getIrowspan() != null)
			tdInput.append(ROWSPAN).append(ASPAS).append(this.getIrowspan()).append(ASPAS);

		if (this.getIclass() != null)
			tdInput.append(CLASS).append(ASPAS).append(this.getIclass()).append(ASPAS);

		if (this.getIwidth() != null)
			tdInput.append(WIDTH).append(ASPAS).append(this.getIwidth()).append(ASPAS);

		// opcoes do td do label
		if (this.getLalign() != null)
			tdLabel.append(ALIGN).append(ASPAS).append(this.getLalign()).append(ASPAS);
		
		if (this.getLcolspan() != null)
			tdLabel.append(COLSPAN).append(ASPAS).append(this.getLcolspan()).append(ASPAS);
		
		if (this.getLrowspan() != null)
			tdLabel.append(ROWSPAN).append(ASPAS).append(this.getLrowspan()).append(ASPAS);

		if (this.getLclass() != null)
			tdLabel.append(CLASS).append(ASPAS).append(this.getLclass()).append(ASPAS);
		
		if (this.getLwidth() != null)
			tdLabel.append(WIDTH).append(ASPAS).append(this.getLwidth()).append(ASPAS);
		
		try {
			writer.println("<tr>");

			writer.println(TD + tdLabel + ">");
			writer.println(LABEL + this.getLname() + END_LABEL);
			writer.println(END_TD);

			writer.println(TD + tdInput + ">");
			writer.println(INPUT + input + END_INPUT);
			writer.println(END_TD);

			writer.println("</tr>");
		} catch (Exception e) {
			logger.error(e);
		}
		return Tag.SKIP_BODY;
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
		return Tag.EVAL_PAGE;
	}
	
	/**
	 * Retorna String ialign
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getIalign() {
		return ialign;
	}
	
	/**
	 * Atribui valor especificado para String ialign.<br>
	 * 
         * @param ialign
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setIalign(String ialign) {
		this.ialign = ialign;
	}
	
	/**
	 * Retorna String iclass.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getIclass() {
		return iclass;
	}
	
	/**
	 * Atribui valor especificado para String iclass.<br>
	 * 
         * @param iclass
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setIclass(String iclass) {
		this.iclass = iclass;
	}
	
	/**
	 * Retorna String icolspan.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getIcolspan() {
		return icolspan;
	}
	
	/**
	 * Atribui valor especificado para String icolspan.<br>
	 * 
         * @param icolspan
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setIcolspan(String icolspan) {
		this.icolspan = icolspan;
	}
	
	/**
	 * Retorna String iextra.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getIextra() {
		return iextra;
	}
	
	/**
	 * Atribui valor especificado para String iextra.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param iextra
	 */
	public void setIextra(String iextra) {
		this.iextra = iextra;
	}
	
	/**
	 * Retorna String imaxlength.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getImaxlength() {
		return imaxlength;
	}
	
	/**
	 * Atribui valor especificado para String imaxlength.<br>
	 * 
         * @param imaxlength
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setImaxlength(String imaxlength) {
		this.imaxlength = imaxlength;
	}
	
	/**
	 * Retorna String iname.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getIname() {
		return iname;
	}
	
	/**
	 * Atribui valor especificado para String iname.<br>
	 * 
         * @param iname
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setIname(String iname) {
		this.iname = iname;
	}
	
	/**
	 * Retorna String irowspan.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getIrowspan() {
		return irowspan;
	}
	
	/**
	 * Atribui valor especificado para String irowspan.<br>
	 * 
         * @param irowspan
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setIrowspan(String irowspan) {
		this.irowspan = irowspan;
	}
	
	/**
	 * Retorna String isize.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getIsize() {
		return isize;
	}
	
	/**
	 * Atribui valor especificado para String isize.<br>
	 * 
         * @param isize
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setIsize(String isize) {
		this.isize = isize;
	}
	
	/**
	 * Retorna String itype.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getItype() {
		return itype;
	}
	
	/**
	 * Atribui valor especificado para String itype.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param itype
	 */
	public void setItype(String itype) {
		this.itype = itype;
	}
	
	/**
	 * Retorna String ivalue.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getIvalue() {
		return ivalue;
	}
	
	/**
	 * Atribui valor especificado para String ivalue.<br>
	 * 
         * @param ivalue
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setIvalue(String ivalue) {
		this.ivalue = ivalue;
	}
	
	/**
	 * Retorna String lalign.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getLalign() {
		return lalign;
	}
	
	/**
	 * Atribui valor especificado para String lalign.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param lalign
	 */
	public void setLalign(String lalign) {
		this.lalign = lalign;
	}
	
	/**
	 * Retorna String lclass.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getLclass() {
		return lclass;
	}
	
	/**
	 * Atribui valor especificado para String lclass.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param lclass
	 */
	public void setLclass(String lclass) {
		this.lclass = lclass;
	}
	
	/**
	 * Retorna String lcolspan.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getLcolspan() {
		return lcolspan;
	}
	
	/**
	 * Atribui valor especificado para String lcolspan.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param lcolspan
	 */
	public void setLcolspan(String lcolspan) {
		this.lcolspan = lcolspan;
	}
	
	/**
	 * Retorna String lname.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getLname() {
		return lname;
	}
	
	/**
	 * Atribui valor especificado para String lname.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param lname
	 */
	public void setLname(String lname) {
		this.lname = lname;
	}
	
	/**
	 * Retorna String lrowspan.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getLrowspan() {
		return lrowspan;
	}
	
	/**
	 * Atribui valor especificado para String lrowspan.<br>
	 * 
         * @param lrowspan
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setLrowspan(String lrowspan) {
		this.lrowspan = lrowspan;
	}
	
	/**
	 * Retorna String iwidth.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getIwidth() {
		return iwidth;
	}
	
	/**
	 * Atribui valor especificado para String iwidth.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param iwidth
	 */
	public void setIwidth(String iwidth) {
		this.iwidth = iwidth;
	}
	
	/**
	 * Retorna String lwidth.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getLwidth() {
		return lwidth;
	}
	
	/**
	 * Atribui valor especificado para String lwidth.<br>
	 * 
         * @param lwidth
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setLwidth(String lwidth) {
		this.lwidth = lwidth;
	}
}