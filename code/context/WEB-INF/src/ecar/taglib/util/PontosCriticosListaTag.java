/*
 * Criado em 27/09/2007
 *
 */
package ecar.taglib.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import comum.util.Data;
import comum.util.Pagina;

import ecar.dao.PontoCriticoDao;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.PontoCriticoPtc;
import ecar.pojo.SisGrupoAtributoSga;


/**
 * Taglib que gera a tela com a lista de pontos criticos para um controle HTML.<br>
 * 
 * @author Milton Pereira
 */
public class PontosCriticosListaTag implements Tag {
	
	private SisGrupoAtributoSga grupoAssunto;
	private PontoCriticoDao pontoCriticoDao;
	private ItemEstruturaIett itemEstrutura;
	private AcompRelatorioArel acompRelatorioArel;
	private Boolean apontamentos;
	private String contextPath;

	//variavel usada apenas pela nova tela de acompanhamento
	private String indice;
	
	
	// Os quatros parametros abaixo sao para adicionar Pontos Criticos na aba de Acompanhamento / Monitoramento 
	private Boolean abaPontoCriticoDeAcompanhamento = new Boolean(false);
	private AcompReferenciaItemAri ari;
	private SegurancaECAR seguranca;
	private String tipoSelecao = "";

	
	private PageContext page = null;

    private Tag parent;

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
    public PontosCriticosListaTag() {
    	
    }
    
    /**
     * Atribui valor especificado para JspWriter writerParametro.<br>
     * 
     * @param writer
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public PontosCriticosListaTag(JspWriter writer) {
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
        try {
        	s.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        	s.append("<tr><td class=\"espacador\" colspan=8><img src=\"" + contextPath + "/images/pixel.gif\"></td></tr>");
        	s.append("<tr class=\"linha_subtitulo\">");
        	s.append("	<td width=\"4%\">");
        	if(getIndice() != null && !getIndice().equals("")) {
        		s.append("		<input type=\"checkBox\" class=\"form_check_radio\" name=\"todosPontosCriticos"+getIndice()+"\" onclick=\"javascript:selectAllParecer(document.forms[1], 'todosPontosCriticos"+getIndice()+"', 'excluirPontoCritico"+getIndice()+"')\">&nbsp;");
			} else {
				s.append("		<input type=\"checkBox\" class=\"form_check_radio\" name=\"todosPontosCriticos\" onclick=\"javascript:selectAll(document.form, 'todosPontosCriticos', 'excluirPontoCritico')\">&nbsp;");
			}
        	s.append("	</td>	");
        	s.append("	<td width=\"4%\"></td>");
        	s.append("	<td>Data Limite</td>");
        	if (grupoAssunto != null){
        		s.append("<td>");
    			s.append(grupoAssunto.getDescricaoSga());
    			s.append("</td>");
    		}
        	s.append("	<td>Descri&ccedil;&atilde;o</td>");
        	s.append("	<td>Estado</td>");        	
        	s.append("	<td>Data da Solu&ccedil;&atilde;o</td>");
        	s.append("	<td>Respons&aacute;vel</td>");
        	s.append("</tr>");
        	s.append("<tr><td class=\"espacador\" colspan=8><img src=\"" + contextPath + "/images/pixel.gif\"></td></tr>");
        	
        	
        	Collection<PontoCriticoPtc> pontosAtivos = Collections.EMPTY_LIST;
        	
        	/* Verifica qual opção está marcada na tela*/
			if(abaPontoCriticoDeAcompanhamento.booleanValue() ){
				if( "".equals(tipoSelecao) || "T".equals(tipoSelecao) )
					pontosAtivos  = pontoCriticoDao.getPontosCriticosOrdenadoDataLimite(itemEstrutura); 
				else{
					if("S".equals(tipoSelecao))
						pontosAtivos  = pontoCriticoDao.getPontosCriticosSolucionadosOrdenadoDataLimite(itemEstrutura);
					if("N".equals(tipoSelecao))
						pontosAtivos  = pontoCriticoDao.getPontosCriticosNaoSolucionadosOrdenadoDataLimite(itemEstrutura);
				}
			 
        	
    		/* Percorre a lista de Pontos Criticos de ItemEstrutura e imprime na tela */
			} else if (acompRelatorioArel != null ){ //como feito anteriormente
        		PontoCriticoPtc pontoCriticoPtc = new PontoCriticoPtc();
        		pontoCriticoPtc.setAcompRelatorioArel(acompRelatorioArel);
        		pontoCriticoPtc.setItemEstruturaIett(itemEstrutura);
        		pontoCriticoPtc.setIndAtivoPtc("S");
        		pontosAtivos = pontoCriticoDao.pesquisar(pontoCriticoPtc, new String[]{"acompRelatorioArel", "asc"});
        	} else  {
        		//pontosAtivos = pontoCriticoDao.getAtivos(itemEstrutura);
        		pontosAtivos = pontoCriticoDao.getPontosCriticosOrdenadoDataLimite(itemEstrutura);
        	}
        	
    		
    		String corRelogio = "Branco";
    		String descRelogio = "";
    		if(pontosAtivos != null ){ 
				Iterator itPc = pontosAtivos.iterator();
				while(itPc.hasNext()){
					PontoCriticoPtc pontoCritico = (PontoCriticoPtc) itPc.next();
					
					//Se o ponto crítico não foi excluído
					if (pontoCritico.getIndExcluidoPtc() == null || !pontoCritico.getIndExcluidoPtc().equals("S")){
					
						String[] relogioAtual = pontoCriticoDao.getRelogioNaData(pontoCritico, Data.getDataAtual());
						corRelogio = relogioAtual[0];
						descRelogio = relogioAtual[1];
						s.append("<tr class=\"linha_subtitulo2\">");
						s.append("	<td width=\"4%\">");
						if(getIndice() != null && !getIndice().equals("")) {
							s.append("		<input type=\"checkbox\" class=\"form_check_radio\" name=\"excluirPontoCritico"+getIndice()+"\" id=\"excluirPontoCritico"+getIndice()+"\" value=\"" + pontoCritico.getCodPtc() + "\">");
						} else {
							s.append("		<input type=\"checkbox\" class=\"form_check_radio\" name=\"excluirPontoCritico\" value=\"" + pontoCritico.getCodPtc() + "\">");
						}
						s.append("	</td>");
						s.append("	<td width=\"4%\">");
						if(getIndice() != null && !getIndice().equals("")) {
							s.append("		<a href=\"javascript:aoClicarEditar(document.form, " + getIndice() + " ," + pontoCritico.getCodPtc() +  ")\">");
						} else {
							s.append("		<a href=\"javascript:aoClicarEditar(document.form," + pontoCritico.getCodPtc() +  ")\">");
						}
						s.append("			<img border=\"0\" src=\"" + contextPath + "/images/icon_alterar.png\" alt=\"Alterar\"></a>&nbsp;");
						s.append("	</td>");
						
						s.append("	<td> " + Pagina.trocaNullData(pontoCritico.getDataLimitePtc()) + "</td>");
						if (grupoAssunto != null) {
							s.append("<td>");
							if (apontamentos != null && apontamentos.booleanValue()){
								s.append("<a href=\"javascript:aoClicarConsultar(document.form, " + pontoCritico.getCodPtc() + ")\">");
							}
							
							if (pontoCritico.getSisAtributoTipo() != null) {
								s.append(pontoCritico.getSisAtributoTipo().getDescricaoSatb());
							}
							if (apontamentos != null && apontamentos.booleanValue()){
								s.append("</a>");
							}
							
							s.append("</td>");
							
						}
						s.append("	<td>");
						if (apontamentos != null && apontamentos.booleanValue()){
							s.append("		<a href=\"javascript:aoClicarConsultar(document.form," + pontoCritico.getCodPtc()+ ")\">");
						}
						
						s.append(pontoCritico.getDescricaoPtc());
						if (apontamentos != null && apontamentos.booleanValue()){
							s.append("		</a>");
						}
						s.append("	</td>");
						s.append("	<td><img src=\"" + contextPath + "/images/pc"+corRelogio+"1.png\" title=\""+descRelogio+"\"></td>");
						//s.append("	<td> " + Pagina.trocaNullData(pontoCritico.getDataLimitePtc()) + "</td>");
						s.append("	<td> " + Pagina.trocaNullData(pontoCritico.getDataSolucaoPtc())+ "</td>");																								
						s.append("	<td>");
						if(pontoCritico.getUsuarioUsu()!=null){
							s.append(Pagina.trocaNull(pontoCritico.getUsuarioUsu().getNomeUsuSent()));
						}
						s.append("	</td>");
						s.append("	</tr>");	
					}
				}  
			}		
    		
    		Collection pontosInativos = null;
    		
    		if (acompRelatorioArel != null){
        		PontoCriticoPtc pontoCriticoPtc = new PontoCriticoPtc();
        		pontoCriticoPtc.setAcompRelatorioArel(acompRelatorioArel);
        		pontoCriticoPtc.setItemEstruturaIett(itemEstrutura);
        		pontoCriticoPtc.setIndAtivoPtc("N");
        		pontosInativos = pontoCriticoDao.pesquisar(pontoCriticoPtc, new String[]{"acompRelatorioArel", "asc"});
        	} else  {
        		pontosInativos = pontoCriticoDao.getInativos(itemEstrutura);
        	}
    		if(pontosInativos != null ){ 
				Iterator itPc = pontosInativos.iterator();
				while(itPc.hasNext()){
					PontoCriticoPtc pontoCritico = (PontoCriticoPtc) itPc.next();
					
					//Se o ponto crítico não foi excluído
					if (pontoCritico.getIndExcluidoPtc() == null || !pontoCritico.getIndExcluidoPtc().equals("S")){
					
						String[] relogioAtual = pontoCriticoDao.getRelogioNaData(pontoCritico, Data.getDataAtual());						
						corRelogio = relogioAtual[0];
						descRelogio = relogioAtual[1];						
						s.append("<tr class=\"linha_subtitulo2inativo\">"); 
						s.append("	<td width=\"4%\">");
						if(getIndice() != null && !getIndice().equals("")) {
							s.append("		<input type=\"checkbox\" class=\"form_check_radio\" name=\"excluirPontoCritico"+getIndice()+"\" id=\"excluirPontoCritico"+getIndice()+"\" value=\"" + pontoCritico.getCodPtc() + "\">");
						} else {
							s.append("		<input type=\"checkbox\" class=\"form_check_radio\" name=\"excluir\" value=\""+pontoCritico.getCodPtc()+"\">");
						}
						s.append("	</td>");
						s.append("	<td width=\"4%\">");
						if(getIndice() != null && !getIndice().equals("")) {
							s.append("		<a href=\"javascript:aoClicarEditar(document.form, " + getIndice() + " ," + pontoCritico.getCodPtc() +  ")\">");
						} else {
							s.append(" 		<a  href=\"javascript:aoClicarEditar(document.form,"+pontoCritico.getCodPtc()+")\">");
						}
						s.append("		<img border=\"0\" src=\"" + contextPath + "/images/icon_alterar.png\" alt=\"Alterar\"></a>&nbsp;");
						s.append("	</td>");
						if (grupoAssunto != null) {
							s.append("<td>");	
							if (apontamentos != null && apontamentos.booleanValue()){
								s.append("<a href=\"javascript:aoClicarConsultar(document.form,"+pontoCritico.getCodPtc()+")\">");
							}
							
							if (pontoCritico.getSisAtributoTipo() != null) {
								s.append(pontoCritico.getSisAtributoTipo().getDescricaoSatb());
							}
							if (apontamentos != null && apontamentos.booleanValue()){
								s.append("</a>");
							}
							
							s.append("</td>");
						}
						s.append("<td>");
						if (apontamentos != null && apontamentos.booleanValue()){
							s.append("	<a href=\"javascript:aoClicarConsultar(document.form,"+pontoCritico.getCodPtc()+")\">");
						}
						
						s.append(pontoCritico.getDescricaoPtc());
						if (apontamentos != null && apontamentos.booleanValue()){
							s.append("	</a>");
						}
						
						s.append("</td>");
						s.append("<td><img src=\"" + contextPath + "/images/pc"+corRelogio+"1.png\" title=\""+descRelogio+"\"></td>");										
						s.append("<td>"+Pagina.trocaNullData(pontoCritico.getDataLimitePtc())+"</td>");																	
						s.append("<td>"+Pagina.trocaNullData(pontoCritico.getDataSolucaoPtc())+"</td>");																								
						s.append("<td>");
						if(pontoCritico.getUsuarioUsu()!=null){
							s.append(Pagina.trocaNull(pontoCritico.getUsuarioUsu().getNomeUsuSent()));
						}
						s.append("</td>");
						s.append("</tr>");	
					}
				}  
			}			

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
    public SisGrupoAtributoSga getGrupoAssunto() {
		return grupoAssunto;
	}

        /**
         *
         * @param grupoAssunto
         */
        public void setGrupoAssunto(SisGrupoAtributoSga grupoAssunto) {
		this.grupoAssunto = grupoAssunto;
	}

        /**
         *
         * @return
         */
        public PontoCriticoDao getPontoCriticoDao() {
		return pontoCriticoDao;
	}

        /**
         *
         * @param pontoCriticoDao
         */
        public void setPontoCriticoDao(PontoCriticoDao pontoCriticoDao) {
		this.pontoCriticoDao = pontoCriticoDao;
	}

        /**
         *
         * @return
         */
        public ItemEstruturaIett getItemEstrutura() {
		return itemEstrutura;
	}

        /**
         *
         * @param itemEstrutura
         */
        public void setItemEstrutura(ItemEstruturaIett itemEstrutura) {
		this.itemEstrutura = itemEstrutura;
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
        public Boolean getApontamentos() {
		return apontamentos;
	}

        /**
         *
         * @param apontamentos
         */
        public void setApontamentos(Boolean apontamentos) {
		this.apontamentos = apontamentos;
	}

        /**
         *
         * @return
         */
        public String getContextPath() {
		return contextPath;
	}

        /**
         *
         * @param contextPath
         */
        public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

        /**
         *
         * @return
         */
        public Boolean getAbaPontoCriticoDeAcompanhamento() {
		return abaPontoCriticoDeAcompanhamento;
	}

        /**
         *
         * @param abaPontoCriticoDeAcompanhamento
         */
        public void setAbaPontoCriticoDeAcompanhamento(
			Boolean abaPontoCriticoDeAcompanhamento) {
		this.abaPontoCriticoDeAcompanhamento = abaPontoCriticoDeAcompanhamento;
	}

        /**
         *
         * @return
         */
        public AcompReferenciaItemAri getAri() {
		return ari;
	}

        /**
         *
         * @param ari
         */
        public void setAri(AcompReferenciaItemAri ari) {
		this.ari = ari;
	}

        /**
         *
         * @return
         */
        public SegurancaECAR getSeguranca() {
		return seguranca;
	}

        /**
         *
         * @param seguranca
         */
        public void setSeguranca(SegurancaECAR seguranca) {
		this.seguranca = seguranca;
	}

        /**
         *
         * @return
         */
        public String getTipoSelecao() {
		return tipoSelecao;
	}

        /**
         *
         * @param tipoSelecao
         */
        public void setTipoSelecao(String tipoSelecao) {
		this.tipoSelecao = tipoSelecao;
	}
}