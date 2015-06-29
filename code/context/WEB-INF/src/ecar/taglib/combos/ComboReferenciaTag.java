/*
 * Criado em 08/04/2004
 *
 */
package ecar.taglib.combos;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

import ecar.dao.AcompReferenciaItemDao;
import ecar.pojo.AcompReferenciaItemAri;

/**
 * Classe para geração de Combo para Referência, utilizada no Relatório de Acompanhamento.<br>
 * Exemplo de Utilização:<br> 
 * <combo:ComboTag <r>
 * nome="referencia"<br>
 * acompReferenciaItem="<%=acompReferenciaItem%>"/><br>
 * 
 * @author evandro
 */
public class ComboReferenciaTag implements Tag {

    /**
     * Nome da combo. Obrigatório.<br>
     * 
     */
    private String nome;

    /**
     * Objeto de onde se pega o item que monta a lista de valores a partir dele
     * e também o valor que ficará selecionado na combo.<br>
     */
    private AcompReferenciaItemAri acompReferenciaItem;
    
    /**
     * Scripts da combo.
     */
    private String scripts;
    
    /**
     * Style da combo.
     */
    private String style;
    
    /**
     * Seta o estado da combo como disabled
     */
    private String status;

    private PageContext page = null;
    
    private Logger logger = null;

    /**
     *
     */
    public ComboReferenciaTag() {
        super();
        this.logger = Logger.getLogger(this.getClass());
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
        StringBuffer s = new StringBuffer();
        
        AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(null);
        
        try {
            /** Cria uma instância do objeto * */
            //Object obj = Class.forName(getObjeto()).newInstance();
            //Dao dao = new Dao();
            //Session session = dao.getSession();

			/** Constroi a combo * */
            s.append("<select name=\"").append(nome).append("\" ");
            s.append(" id=\"").append(nome).append("\" ");
            
            if(scripts != null && !"".equals(scripts))
                s.append( scripts );

            if (style != null && !"".equals(style))
                s.append(" style=\"").append(style).append("\"");
            
            if (status != null && !"".equals(status))
                s.append( status );
            
            s.append(">");
            
            //Montar as opções a partir do item
            List lista = acompReferenciaItemDao.getReferenciaByItem(acompReferenciaItem);
			Iterator it = lista.iterator();
			AcompReferenciaItemAri acompRefItemLista;
            
			String sel = "";
			
			while(it.hasNext()){
				acompRefItemLista = (AcompReferenciaItemAri) it.next();
				sel = "";
				
				if(acompRefItemLista.getCodAri() == acompReferenciaItem.getCodAri())
					sel = "selected";
				
				s.append("<option ").append(sel).append(" value=\"").append(acompRefItemLista.getCodAri()).append("\">");
				s.append(acompRefItemLista.getAcompReferenciaAref().getNomeAref()).append("</option>");
			}
			
            s.append("</select>");
            writer.print(s.toString());
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
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void release() {
        //this.selected = null;
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
     * @param page
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void setPage(PageContext page) {
        this.page = page;
    }


    /**
     * Retorna String nome.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String - (Returns the nome)
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atribui valor especificado para String nome.<br>
     * 
     * @param nome
     * @author N/C
     * @since N/C
     * @version N/C
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

	/**
	 * Retorna AcompReferenciaItemAri acompReferenciaItem.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return AcompReferenciaItemAri - (Returns the acompReferenciaItem)
	 */
	public AcompReferenciaItemAri getAcompReferenciaItem() {
		return acompReferenciaItem;
	}
	
	/**
	 * Atribui valor especificado para AcompReferenciaItemAri acompReferenciaItem.<br>
	 * 
         * @param acompReferenciaItem
         * @author N/C
     * @since N/C
     * @version N/C
	 */
	public void setAcompReferenciaItem(AcompReferenciaItemAri acompReferenciaItem) {
		this.acompReferenciaItem = acompReferenciaItem;
	}
	
	/**
	 * Retorna String scripts.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String - (Returns the scripts)
	 */
	public String getScripts() {
		return scripts;
	}
	
	/**
	 * Atribui valor especificado para String scripts.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param scripts
	 */
	public void setScripts(String scripts) {
		this.scripts = scripts;
	}
	
	/**
	 * Retorna String style.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String - (Returns the style)
	 */
	public String getStyle() {
		return style;
	}
	/**
	 * Atribui valor especificado para String style.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param style
	 */
	public void setStyle(String style) {
		this.style = style;
	}
	/**
	 * Retorna String status.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String - (Returns the style)
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Atribui valor especificado para String status
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}