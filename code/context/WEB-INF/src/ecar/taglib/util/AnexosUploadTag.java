/*
 * Criado em 18/19/2007
 *
 */
package ecar.taglib.util;

import java.util.Collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import comum.util.Pagina;
import comum.util.Util;

import ecar.pojo.ItemEstrutUploadIettup;
import ecar.taglib.combos.ComboTag;


/**
 * Taglib que gera a tela de upload para um controle HTML.<br>
 * 
 * @author Milton Pereira
 */
public class AnexosUploadTag implements Tag {
	
	private ItemEstrutUploadIettup anexo;
	private String disabled;
	private String readOnly;

	private String nomeComboTag;
	private String objetoComboTag;
	private String labelComboTag;
	private String valueComboTag;
	private String orderComboTag;
	private String scriptsComboTag;
	private Collection colecao;
  
	//variavel usada apenas na nova tela de acompanhamento
	private String indice;

    private PageContext page = null;

    private Tag parent;
    
    private String imagem;

    /**
     *
     */
    public static final long MAXLENGTH = 200;
    
    private JspWriter writerParametro = null;
    
    /**
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public AnexosUploadTag() {
    	
    }
    
    /**
     * Atribui valor especificado para JspWriter writerParametro.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param writer
     */
    public AnexosUploadTag(JspWriter writer) {
    	this.writerParametro = writer;
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
    	JspWriter writer = null;
    	if(writerParametro != null) {
    		writer = writerParametro;
    	} else {
        	writer = this.page.getOut();
    	}
    	String idCampo = "";
        StringBuffer s = new StringBuffer();
        StringBuffer sFim = new StringBuffer();
        Input tagInput = (Input) getParent();
        try {
        	
        	s.append("<table class=\"form\">");
            s.append("<tr> ");
            s.append("	<td colspan=2> ");
    		s.append("		<div class=\"camposobrigatorios\">* Campos de preenchimento obrigat&oacute;rio</div>");
    		s.append("	</td>");
    		s.append("</tr>");
    		s.append("<tr>");
    		s.append("	<td class=\"label\">");
    		if(anexo != null && anexo.getArquivoIettup()!=null){
    			s.append("Novo Arquivo:");
    		} else {	
    			s.append("* Arquivo:");	
    		}
    		s.append("	</td>");
    		if(getIndice()!= null && !getIndice().equals(""))
    			idCampo = " id=\"arquivoIettup"+getIndice()+"\" ";
    		s.append("	<td>");
   			s.append("		<input type=\"file\" name=\"arquivoIettup\"" + disabled + idCampo + ">");
    		s.append("	</td>");
    		s.append("</tr>");
    		s.append("<tr>");
    		s.append("	<td class=\"label\">* Descri&ccedil;&atilde;o</td>");		
    		if(getIndice()!= null && !getIndice().equals("")) {
    			idCampo = " id=\"descricaoIettup"+getIndice()+"\" ";
    			s.append("	<td><textarea name=\"descricaoIettup"+getIndice()+"\" rows=\"4\" cols=\"60\" "+ readOnly + idCampo);
    		} else {
    			s.append("	<td><textarea name=\"descricaoIettup\" rows=\"4\" cols=\"60\" "+ readOnly);
    		}
    		s.append("		onkeyup=\"return validaTamanhoLimite(this, 2000);\"");
    		s.append("  	onkeydown=\"return validaTamanhoLimite(this, 2000);\"");
    		s.append("  	onblur=\"return validaTamanhoLimite(this, 2000);\">" + Pagina.trocaNull(anexo.getDescricaoIettup()) + "</textarea>");
    		s.append("	</td>");
    		s.append("</tr>");
    		
    		if(anexo!= null && anexo.getArquivoIettup()!=null){
    			s.append("<tr>");
    			s.append("	<td class=\"label\">Arquivo Atual:</td>");
    			s.append("	<td> "+ anexo.getNomeOriginalIettup() + "</td>");
    			s.append("</tr>");
    			s.append("<tr>");
    			s.append("	<td class=\"label\">Tamanho Atual:</td>");
    			s.append("	<td>  " + Util.formataByte(anexo.getTamanhoIettup()) + "</td>");
    			s.append("</tr>");
    			s.append("<tr>");
    			s.append("	<td class=\"label\">Data:</td>");
    			s.append("	<td> " + Pagina.trocaNullData(anexo.getDataInclusaoIettup()) + "</td>");
    			s.append("</tr>");
    		}
    		s.append("<tr>");
    		s.append("	<td class=\"label\">* Tipo</td>");
    		s.append("	<td>");
    		
    		writer.print(s.toString());

    		String selectedTipo = "";
    		if(anexo.getUploadTipoArquivoUta() != null)
    			selectedTipo = anexo.getUploadTipoArquivoUta().getCodUta().toString();
        	
    		ComboTag comboTag = new ComboTag(writer);
    		if(getIndice()!= null && !getIndice().equals(""))
    			nomeComboTag = nomeComboTag+getIndice();
    		comboTag.setNome(nomeComboTag);
        	comboTag.setObjeto(objetoComboTag);
        	comboTag.setLabel(labelComboTag);
        	comboTag.setValue(valueComboTag);
        	comboTag.setSelected(selectedTipo);
        	comboTag.setScripts(scriptsComboTag);
        	comboTag.setColecao(colecao);
        			
    	    comboTag.doStartTag();
    	    
    		sFim.append("</td>");
    		sFim.append("</tr>");
    		
    		writer.print(sFim);
  	 
            
        } catch (Exception e) {
        	
        }
        return Tag.EVAL_BODY_INCLUDE;
    } 

    /**
     *
     * @return
     */
    public String getIndice() {
		return indice;
	}

    /**
     *
     * @param indice
     */
    public void setIndice(String indice) {
		this.indice = indice;
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
     * Atribui valor especificado para Tag parent.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param arg0
     */
    public void setParent(Tag arg0) {
        parent = arg0;
    }

    /**
     * Retorna Tag parent.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Tag
     */
    public Tag getParent() {
        return parent;
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
     * 
     * 
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
     *
     * @return
     */
    public String getImagem() {
		return imagem;
	}

    /**
     *
     * @param imagem
     */
    public void setImagem(String imagem) {
		this.imagem = imagem;
	}

        /**
         *
         * @return
         */
        public ItemEstrutUploadIettup getAnexo() {
		return anexo;
	}

        /**
         *
         * @param anexo
         */
        public void setAnexo(ItemEstrutUploadIettup anexo) {
		this.anexo = anexo;
	}

        /**
         *
         * @return
         */
        public String getDisabled() {
		return disabled;
	}

        /**
         *
         * @param disabled
         */
        public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

        /**
         *
         * @return
         */
        public String getReadOnly() {
		return readOnly;
	}

        /**
         *
         * @param readOnly
         */
        public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

        /**
         *
         * @return
         */
        public String getNomeComboTag() {
		return nomeComboTag;
	}

        /**
         *
         * @param nomeComboTag
         */
        public void setNomeComboTag(String nomeComboTag) {
		this.nomeComboTag = nomeComboTag;
	}

        /**
         *
         * @return
         */
        public String getObjetoComboTag() {
		return objetoComboTag;
	}

        /**
         *
         * @param objetoComboTag
         */
        public void setObjetoComboTag(String objetoComboTag) {
		this.objetoComboTag = objetoComboTag;
	}

        /**
         *
         * @return
         */
        public String getLabelComboTag() {
		return labelComboTag;
	}

        /**
         *
         * @param labelComboTag
         */
        public void setLabelComboTag(String labelComboTag) {
		this.labelComboTag = labelComboTag;
	}

        /**
         *
         * @return
         */
        public String getValueComboTag() {
		return valueComboTag;
	}

        /**
         *
         * @param valueComboTag
         */
        public void setValueComboTag(String valueComboTag) {
		this.valueComboTag = valueComboTag;
	}

        /**
         *
         * @return
         */
        public String getOrderComboTag() {
		return orderComboTag;
	}

        /**
         *
         * @param orderComboTag
         */
        public void setOrderComboTag(String orderComboTag) {
		this.orderComboTag = orderComboTag;
	}

        /**
         *
         * @return
         */
        public String getScriptsComboTag() {
		return scriptsComboTag;
	}

        /**
         *
         * @param scriptsComboTag
         */
        public void setScriptsComboTag(String scriptsComboTag) {
		this.scriptsComboTag = scriptsComboTag;
	}

        /**
         *
         * @return
         */
        public Collection getColecao() {
		return colecao;
	}

        /**
         *
         * @param colecao
         */
        public void setColecao(Collection colecao) {
		this.colecao = colecao;
	}

        /**
         *
         * @return
         */
        public JspWriter getWriterParametro() {
		return writerParametro;
	}

        /**
         *
         * @param writerParametro
         */
        public void setWriterParametro(JspWriter writerParametro) {
		this.writerParametro = writerParametro;
	}


}