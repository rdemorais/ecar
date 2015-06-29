/*
 * Created on 09/05/2005
 * 
 */
package ecar.taglib.util;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import comum.util.Data;

import ecar.dao.PopUpDao;
import ecar.pojo.PopupPpp;
import ecar.pojo.UsuarioUsu;

/**
 * @author felipe
 * 
 */
public class PopUpsTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3920610661946783698L;

	private Logger logger = Logger.getLogger(this.getClass());

	private String pathEcar;
	
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
		
		try {
			
			List popUps = new PopUpDao(null).getPopUpsApresentadasNoDia(Data.getDataAtual());
			
			if("true".equals(this.pageContext.getRequest().getParameter("mostrarPopUps"))){
				Iterator it = popUps.iterator();
				while(it.hasNext()){
					PopupPpp pp = (PopupPpp) it.next();
					UsuarioUsu usuario = ((ecar.login.SegurancaECAR)this.pageContext.getSession().getAttribute("seguranca")).getUsuario();
					StringBuffer nomeCookie = new StringBuffer("popUp_").append(usuario.getCodUsu()).append("_").append(pp.getCodPpp());
					writer.println("<script>");
					writer.println("if(getCookie('" + nomeCookie + "') == null){");
					writer.println("abreJanela('" + getPathEcar() +  "/popUp/popUpAvisoPadrao.jsp?codPpp=" +  pp.getCodPpp() + "', " + pp.getJanelaLarguraPpp() + "," +  pp.getJanelaAlturaPpp() + ",'" + pp.getCodPpp() + "');");
					if(pp.getPopupComportamentoPppc().getCodPppc().intValue() == PopUpDao.POPUP_COMPORTAMENTO_ABRIR_SOMENTE_UMA_VEZ){
						writer.println("setCookie('" + nomeCookie + "','1',null,'" + this.pathEcar + "',null,null);");						
					}
					writer.println("}");
					writer.println("</script>");
				}			
			}
			
		} catch (Exception e) {
			logger.error(e);
		}
		/* nao processa o conteudo do corpo da tag, porque nao existe */
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
	    /* processa o restante da página jsp */
		return Tag.EVAL_PAGE;
	}

	/**
	 * Retorna String pathEcar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String - (Returns the pathEcar)
	 */
	public String getPathEcar() {
		return pathEcar;
	}
	
	/**
	 * Atribui valor especificado para String pathEcar.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param pathEcar
	 */
	public void setPathEcar(String pathEcar) {
		this.pathEcar = pathEcar;
	}
}