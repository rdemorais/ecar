/*
 * Criado em 18/19/2007
 *
 */
package ecar.taglib.util;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import comum.util.Pagina;
import comum.util.Util;

import ecar.dao.ItemEstruturaUploadCategoriaDao;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.EstruturaFuncaoEttf;
import ecar.pojo.ItemEstrUplCategIettuc;
import ecar.pojo.ItemEstrutUploadIettup;
import ecar.pojo.UsuarioUsu;


/**
 * Taglib que gera a tela de upload para um controle HTML.<br>
 * 
 * @author Milton Pereira
 */
public class AnexosListaTag implements Tag {
	
	
	private EstruturaFuncaoEttf estruturaFuncao;
	private ItemEstrUplCategIettuc categoriaAnexo;
	private Boolean permissaoAlterar;
	private ItemEstrutUploadIettup anexo;
	private String caminhoRaizUpload;
	private String pathEcar;
	private AcompRelatorioArel acompRelatorioArel;
	private String disabled;
	private HttpServletRequest request;
	
	//variavel utilizada apenas pela tela de acompanhamento
	private String indice;

    private PageContext page = null;

    private Tag parent;
    
    private String imagem;

    /**
     *
     */
    public static final long MAXLENGTH = 200;
    
    private JspWriter writerParametro = null;
    
    // usado pra quando nao pode aparecer o lapis para editar (tela de visualizar)
    private Boolean podeEditar;
    
    /**
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public AnexosListaTag() {
    	
    }
    
    /**
     * Atribui valor especificado para JspWriter writerParametro.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param writer
     */
    public AnexosListaTag(JspWriter writer) {
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
        StringBuffer s = new StringBuffer();
        Input tagInput = (Input) getParent();
        try {

            s.append("<table class=\"form\">");
            s.append("	<tr>");
    		s.append("		<td class=\"label\">");
    		s.append(			estruturaFuncao.getLabelEttf()+":");
    		s.append("  	</td>");
    		s.append("		<td>");
    		if (categoriaAnexo != null && categoriaAnexo.getNomeIettuc() != null) {
    			s.append(			categoriaAnexo.getNomeIettuc());
    		}
    		s.append("		</td>");
    		s.append("	</tr>");
    		s.append("</table>");
    		s.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
    		s.append("	<tr> <td class=\"espacador\"><img src=\"" + "" + pathEcar + "/images/pixel.gif\"></td> </tr>");
    		s.append("	<tr class=\"linha_titulo\" align=\"right\">");
    		s.append("		<td>");
    		
    		//se tiver permissao pra alterar e se puder editar
    		if (permissaoAlterar.booleanValue() && (podeEditar == null || (podeEditar != null && podeEditar.booleanValue()))){
    			if(getIndice() != null && !getIndice().equals("")) {
    				s.append("		<input " + disabled + " type=\"button\" value=\"Adicionar\" class=\"botao\" onclick=\"javascript:aoClicarIncluir(document.form, '"+getIndice()+"')\">");  			 
    				s.append("		<input " + disabled + " type=\"button\" value=\"Excluir\" class=\"botao\" onclick=\"javascript:aoClicarExcluir(document.form, '"+getIndice()+"')\">");    				
    			} else {
    				s.append("		<input " + disabled + " type=\"button\" value=\"Adicionar\" class=\"botao\" onclick=\"javascript:aoClicarIncluir(document.form)\">");  			 
    				s.append("		<input " + disabled + " type=\"button\" value=\"Excluir\" class=\"botao\" onclick=\"javascript:aoClicarExcluir(document.form)\">");
    			}
    		}
    		
    		s.append("		</td>");
    		s.append("	</tr>");
    		s.append("</table>");
    		//############### Listagem  ###################
    		s.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
    		s.append("	<thead> ");
    		s.append("		<tr><td class=\"espacador\" colspan=6><img src=\"" + "" + pathEcar + "/images/pixel.gif\"></td></tr>");
    		s.append("		<tr class=\"linha_subtitulo\">");
    		s.append("			<td width=\"5%\">");
    		if(podeEditar == null || (podeEditar != null && podeEditar.booleanValue()))  {
	    		if(getIndice() != null && !getIndice().equals("")) {
	    			s.append("				<input type=\"checkBox\" class=\"form_check_radio\" name=\"todosAnexos"+getIndice()+"\" id=\"todosAnexos"+getIndice()+"\" onclick=\"javascript:selectAllParecer(document.forms[1], 'todosAnexos"+getIndice()+"', 'excluir"+getIndice()+"')\">&nbsp;");
	    		} else {
	    			s.append("				<input type=\"checkBox\" class=\"form_check_radio\" name=\"todos\" onclick=\"javascript:selectAll(document.form)\">&nbsp;");
	    		}
    		}	
    		s.append("			</td>");
    		s.append("			<td width=\"5%\">A&ccedil;&atilde;o</td>");
    		s.append("			<td width=\"10%\"><a href=\"#\" onclick=\"this.blur(); return sortTable('corpo', 2, false, true);\">Data</a></td>");
    		s.append("			<td width=\"55%\"><a href=\"#\" onclick=\"this.blur(); return sortTable('corpo', 3, false);\">Descri&ccedil;&atilde;o</a></td>");
    		s.append("			<td width=\"15%\"><a href=\"#\" onclick=\"this.blur(); return sortTable('corpo', 4, false);\">Arquivo</a></td>");
    		s.append("			<td width=\"10%\"><a href=\"#\" onclick=\"this.blur(); return sortTable('corpo', 5, false);\">Tamanho</a></td>");
    		s.append("		</tr>");
    		s.append("	</thead>");
    		s.append("	<tbody id=\"corpo\">");
    		s.append("");
	  	
    		Collection anexos = null;    		
    		
      		
    		if (acompRelatorioArel != null) {
    			ItemEstrutUploadIettup itemEstrutUploadIettup = new ItemEstrutUploadIettup();
    			itemEstrutUploadIettup.setAcompRelatorioArel(acompRelatorioArel);
    			itemEstrutUploadIettup.setIndAtivoIettup("S");
    			ItemEstruturaUploadCategoriaDao categoriaAnexoDao = new ItemEstruturaUploadCategoriaDao(null);
    			anexos = categoriaAnexoDao.pesquisar(itemEstrutUploadIettup, new String[]{"acompRelatorioArel", "asc"});
    		} else {
    			anexos = categoriaAnexo.getItemEstrutUploadIettups();
    		}
    		
    		if (anexos != null ) {
    		
    			Iterator itAnx = anexos.iterator();
   				while(itAnx.hasNext()){
   					
   					anexo = (ItemEstrutUploadIettup) itAnx.next();
   					if( anexo.getIndAtivoIettup() == null || "S".equals(anexo.getIndAtivoIettup()) ) {
   						s.append("<tr class=\"linha_subtitulo2\">");
   						s.append("	<td>");
   						if(podeEditar == null || (podeEditar != null && podeEditar.booleanValue()))  {
	   						if(getIndice() != null && !getIndice().equals("")) {
	   							s.append("		<input type=\"checkbox\" class=\"form_check_radio\" name=\"excluir"+getIndice()+"\" id=\"excluir"+getIndice()+"\" value=\""+anexo.getCodIettup()+"\">");
	   						} else {
	   							s.append("		<input type=\"checkbox\" class=\"form_check_radio\" name=\"excluir\" value=\""+anexo.getCodIettup()+"\">");
	   						}
   						}	
   						s.append("	</td>");
   						
   					    // nao pode fazer o download se for pra visualizar
   						String hashNomeArquivo = null;
   						UsuarioUsu usuarioImagem = null;
   						//String pathRaiz = new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getRaizUpload();
   						if(anexo.getArquivoIettup() != null) { 
   							
   							hashNomeArquivo = Util.calcularHashNomeArquivo(caminhoRaizUpload, anexo.getArquivoIettup());
   							usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
   							Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, caminhoRaizUpload, anexo.getArquivoIettup());
   							
   							s.append("  <td><a href=\""+pathEcar+"/DownloadFile?RemoteFile="+ hashNomeArquivo +"\"><img src=\"" + "" + pathEcar + "/images/icon_salvar.png\" border=\"0\" alt=\"Download\" title=\"Download\"></a></td>");
	   					} else {
	   						s.append("  <td>&nbsp;</td>");
	   					}
   						
   						s.append("	<td> "+ Pagina.trocaNullData(anexo.getDataInclusaoIettup()) +"</td>");
    					s.append("	<td> ");
    					if(podeEditar == null || (podeEditar != null && podeEditar.booleanValue()))  {
	    					if(getIndice() != null && !getIndice().equals("")) {
	    						s.append("      <a href=\"javascript:aoClicarConsultar(document.forms[1], " + getIndice() +" ," + anexo.getCodIettup()+ ")\">");
	    					} else {
	    						s.append("      <a href=\"javascript:aoClicarConsultar(document.form," + anexo.getCodIettup()+ ")\">");
	    					}
    					}	
    					if (anexo.getDescricaoIettup() != null)
    						s.append(anexo.getDescricaoIettup());
    					else 
    						s.append("Sem conteúdo");
    					
    					s.append("		</a>");
    					s.append("	</td>");
    					if(anexo.getArquivoIettup() != null ) {
	    					s.append("	<td> "+ Pagina.trocaNull(anexo.getNomeOriginalIettup()) + " </td>");
	    					s.append("	<td> "+ Util.formataByte(anexo.getTamanhoIettup()) + " </td>");
    					} else {
    						s.append("	<td>&nbsp;</td>");
	    					s.append("	<td>&nbsp;</td>");
    					}
    					s.append("</tr>");  									
    				}
   				}
    			
    		}
    		
			s.append("<script language=\"Javascript\">");
			s.append("sortTable('corpo', 1, false, true);"); //Ordenamento default pela data
			s.append("</script>");
			s.append("</tbody>");
				 
			s.append("</table>");																

            writer.print(s.toString());
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
     * @author N/C
	 * @since N/C
	 * @version N/C
         * @param arg0
     */
    public void setPageContext(PageContext arg0) {
        this.page = arg0;
    }

    /**
     * Atribui valor especificado para Tag parent.<br>
     * 
     * @param arg0
     * @author N/C
	 * @since N/C
	 * @version N/C
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
        public EstruturaFuncaoEttf getEstruturaFuncao() {
		return estruturaFuncao;
	}

        /**
         *
         * @param estruturaFuncao
         */
        public void setEstruturaFuncao(EstruturaFuncaoEttf estruturaFuncao) {
		this.estruturaFuncao = estruturaFuncao;
	}

        /**
         *
         * @return
         */
        public ItemEstrUplCategIettuc getCategoriaAnexo() {
		return categoriaAnexo;
	}

        /**
         *
         * @param categoriaAnexo
         */
        public void setCategoriaAnexo(ItemEstrUplCategIettuc categoriaAnexo) {
		this.categoriaAnexo = categoriaAnexo;
	}

        /**
         *
         * @return
         */
        public Boolean getPermissaoAlterar() {
		return permissaoAlterar;
	}

        /**
         *
         * @param permissaoAlterar
         */
        public void setPermissaoAlterar(Boolean permissaoAlterar) {
		this.permissaoAlterar = permissaoAlterar;
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
        public String getCaminhoRaizUpload() {
		return caminhoRaizUpload;
	}

        /**
         *
         * @param caminhoRaizUpload
         */
        public void setCaminhoRaizUpload(String caminhoRaizUpload) {
		this.caminhoRaizUpload = caminhoRaizUpload;
	}

        /**
         *
         * @return
         */
        public String getPathEcar() {
		return pathEcar;
	}

        /**
         *
         * @param pathEcar
         */
        public void setPathEcar(String pathEcar) {
		this.pathEcar = pathEcar;
	}

        /**
         *
         * @return
         */
        public AcompRelatorioArel getAcompRelatorioArel() {
		return acompRelatorioArel;
	}

        /**
         *
         * @param acompRelatorioArel
         */
        public void setAcompRelatorioArel(AcompRelatorioArel acompRelatorioArel) {
		this.acompRelatorioArel = acompRelatorioArel;
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
        public Boolean getPodeEditar() {
		return podeEditar;
	}

        /**
         *
         * @param podeEditar
         */
        public void setPodeEditar(Boolean podeEditar) {
		this.podeEditar = podeEditar;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
}