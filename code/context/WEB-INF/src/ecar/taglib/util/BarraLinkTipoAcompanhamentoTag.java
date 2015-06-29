/*
 * Criado em 06/04/2005
 *
 */
package ecar.taglib.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import ecar.dao.TipoAcompanhamentoDao;
import ecar.exception.ECARException;
import ecar.pojo.TipoAcompanhamentoTa;


/**
 * @author felipev
 *
 */
public class BarraLinkTipoAcompanhamentoTag extends TagSupport{
	
	private static final long serialVersionUID = 2869225621544161026L;

	private static final String ABA_TIPO_ACOMPANHAMENTO_HABILITADA = "abatipoacompanhamentohabilitada";
    private static final String ABA_TIPO_ACOMPANHAMENTO_DESABILITADA = "abatipoacompanhamentodesabilitada";

    private String codTipoAcompanhamentoSelecionado;
    private String chamarPagina;
    private String codTipoRefAcompanhamentoSelecionado;
    private String tela;
    
    //verifica se é pra exibir todas as abas de acompanhamento ou apenas a selecionada no filtro
    private Boolean exibirAbasAcompanhamento;
    
    private String caminho;
    private Boolean exibirAbaFiltro = Boolean.TRUE;
    
    //verifica se a tag é chamada na tela de gerar periodo
    private Boolean gerarPeriodo;
    
    //verifica se a tag é chamada em gerar relatorio
    private Boolean gerarRelatorio;
    
    //verifica se o usuário possui alguma aba configurada para o tipo de acompanhamento para o qual vai ser criada a aba
    // não é obrigatório porque apenas vai ser usada em monitoramento
    private Set gruposUsuario;
   
    
    private HttpServletRequest request;
    
    //guarda o tipo de filtro selecionado quando trocar de aba (apenas em monitoramento)
    private String hidAcao;
    

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
         
        	
        	
        	if(gerarPeriodo != null && gerarPeriodo) {
        		 writer.print(gerarAbaAcompanhamentoGerarPeriodo().toString());
        		
        	}
        	else if (gerarRelatorio != null && gerarRelatorio){
        		writer.print(gerarAbaAcompanhamentoGerarRelatorio().toString());
        	}
        	else {	
        		
        		/*****************TELA MONITORAMENTO***********************************/
        		StringBuffer s = new StringBuffer();
        		TipoAcompanhamentoDao tipoAcompanhamentoDao = new TipoAcompanhamentoDao();
               	List tiposAcompanhamentos = tipoAcompanhamentoDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
               	if(tiposAcompanhamentos != null && tiposAcompanhamentos.size() > 0) {
            		
	            	s.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" id=\"abastipoacompanhamento\">")
	            	 .append("<tr>")
	            	 .append("<td class=\"texto_negrito\">");
	
			    	//sempre vai criar o aba filtros desabilitada
	                criaAba("Filtros", caminho + "/acompanhamento/filtroItens.jsp", ABA_TIPO_ACOMPANHAMENTO_DESABILITADA, s);
	                
	                
	                // quando o tipo de filtro for pesquisas salvas ou filtro personalizado, nao precisa guardar o parametro
	                // porque os dois são específicos para um tipo de acompanhamento. Ao trocar por outro tipo de acompanhamento,
	                // os mesmos parâmetros não podem ser utilizados. 
	                if(hidAcao != null && (hidAcao.equals("pesquisaSalva") || hidAcao.equals("pesquisarFiltros"))) {
	                	hidAcao = "";	                	
	                }
	                
	               
	                for(Iterator it = tiposAcompanhamentos.iterator();it.hasNext();) {
	        			TipoAcompanhamentoTa ta = (TipoAcompanhamentoTa)it.next();
	        			
	        			//Se vier de um filtro que nao for a opção geral apenas exibe a aba do acompanhamento selecionado
	        			if (exibirAbasAcompanhamento!= null && exibirAbasAcompanhamento.booleanValue()== false){
	        				
		                    if(getCodTipoAcompanhamentoSelecionado().equals(ta.getCodTa().toString())) {
		                    	String situacaoAba = ABA_TIPO_ACOMPANHAMENTO_HABILITADA;
		                    	
		                    	String parametros = "?nuPagina=1&codTipoAcompanhamento=" + ta.getCodTa().toString() 
                									+  "&codTipoRefAcompanhamento=" + getCodTipoRefAcompanhamentoSelecionado()
                									+ "&hidAcao=" + hidAcao;
		                    	
		                    	if(exibirAbaFiltro && tipoAcompanhamentoDao.existeAbaConfiguradaTipoAcompanhamento(ta, this.gruposUsuario)) {
		                    		criaAba(ta.getDescricaoTa(), caminho + "/acompanhamento/" + getChamarPagina() + parametros , situacaoAba, s);
		                    		
		                    	}	
		                    }    
	                		
	                	} else {
	                		
	                		// Se vier do filtro geral, exibe todas as abas
	            			String situacaoAba = ABA_TIPO_ACOMPANHAMENTO_DESABILITADA;
		                    if(getCodTipoAcompanhamentoSelecionado().equals(ta.getCodTa().toString())) 
		                        situacaoAba = ABA_TIPO_ACOMPANHAMENTO_HABILITADA;
		                    
		                    // passa a forma de visualização para que as abas de acompanhamento nao percam a configuração do filtro geral
		                    String parametros = "?nuPagina=1&codTipoAcompanhamento=" + ta.getCodTa().toString() +  "&codTipoRefAcompanhamento=" +
		                    					getCodTipoRefAcompanhamentoSelecionado() + "&hidFormaVisualizacaoEscolhida=geral" +
		                    					"&hidAcao=" + hidAcao;
		                    
		                    //cria todas as abas
		                    if(tipoAcompanhamentoDao.existeAbaConfiguradaTipoAcompanhamento(ta,  this.gruposUsuario))
		                    	criaAba(ta.getDescricaoTa(), caminho + "/acompanhamento/" + getChamarPagina() + parametros , situacaoAba, s);
		                   
		            	}
	                }
	                s.append("</td>")
	                 .append("</tr>")
	                 .append("</table>");
	                
	        	}
               	
               	writer.print(s.toString());
        	}

            
        } catch (IOException e) {
        	Logger.getLogger(this.getClass()).error(e);
        }
        catch(ECARException e){
        	Logger.getLogger(this.getClass()).error(e);
        }
        return Tag.SKIP_BODY;

    }
	
	/**
	 *  Cria Abas de Acompanhamento do Relatório.
	 * 
	 * @return
	 * @throws ECARException
	 */
	public StringBuffer gerarAbaAcompanhamentoGerarRelatorio() throws ECARException{
		
		StringBuffer s = new StringBuffer();
		
		TipoAcompanhamentoDao tipoAcompanhamentoDao = new TipoAcompanhamentoDao();

    	List tiposAcompanhamentos = tipoAcompanhamentoDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
    	
    	if(tiposAcompanhamentos != null && tiposAcompanhamentos.size() > 0) {
        	s.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" id=\"abastipoacompanhamento\">")
        	 .append("<tr>")
        	 .append("<td class=\"texto_negrito\">");

      	for(Iterator it = tiposAcompanhamentos.iterator();it.hasNext();) {
    			TipoAcompanhamentoTa ta = (TipoAcompanhamentoTa)it.next();
    			
    			//Se vier de um filtro que nao for a opção geral
    			// apenas exibe a aba do acompanhamento selecionado
    			if (exibirAbasAcompanhamento!= null && exibirAbasAcompanhamento.booleanValue()== false){
    				
                    if(getCodTipoAcompanhamentoSelecionado().equals(ta.getCodTa().toString())) {
                    	String situacaoAba = ABA_TIPO_ACOMPANHAMENTO_HABILITADA;
                    	//só vai criar se for a aba selecionada
                        criaAba(ta.getDescricaoTa(), getChamarPagina() + "?nuPagina=1&codTipoAcompanhamento=" + ta.getCodTa().toString() +  "&codTipoRefAcompanhamento=" + getCodTipoRefAcompanhamentoSelecionado() , situacaoAba, s);
                    }    
            		
            	} else {
            		
            		// Se vier do filtro geral, exibe todas as abas
        			String situacaoAba = ABA_TIPO_ACOMPANHAMENTO_DESABILITADA;
                    if(getCodTipoAcompanhamentoSelecionado().equals(ta.getCodTa().toString())) 
                        situacaoAba = ABA_TIPO_ACOMPANHAMENTO_HABILITADA;
                    
                    // passa a forma de visualização para que as abas de acompanhamento nao percam a configuração do filtro geral
                    String parametros = "?nuPagina=1&codTipoAcompanhamento=" + ta.getCodTa().toString() +  "&codTipoRefAcompanhamento=" +
                    					getCodTipoRefAcompanhamentoSelecionado() + "&hidFormaVisualizacaoEscolhida=geral";
                    
                    //cria todas as abas
                    if(getTela() != null && !getTela().equals("") && (getTela().equals("lista") || getTela().equals("opcoes")) ){
                    	criaAba(ta.getDescricaoTa(), getChamarPagina() + parametros , situacaoAba, s);
                    } 
        		
            	}
            }
            s.append("</td>")
             .append("</tr>");
             if(getTela() != null && !getTela().equals("") && getTela().equals("opcoes"))
            	 s.append("<tr>  <td class=\"espacador\" colspan=\"2\"><img src=\"../../images/pixel.gif\" height=\"2\"></td></tr>");
             s.append("</table>");
    	}
    	
    	return s;
	}
	
	/**
	 * Cria Abas de Acompanhamento de Gerar Periodo.<br>
	 * 
         * @return
         * @author N/C
	 * @since N/C
	 * @version N/C
	 * @throws ECARException
	 */
	public StringBuffer gerarAbaAcompanhamentoGerarPeriodo() throws ECARException {

		
		 StringBuffer s = new StringBuffer();
		
		TipoAcompanhamentoDao tipoAcompanhamentoDao = new TipoAcompanhamentoDao();

    	List tiposAcompanhamentos = tipoAcompanhamentoDao.getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao();
    	
    	if(tiposAcompanhamentos != null && tiposAcompanhamentos.size() > 0) {
        	s.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" id=\"abastipoacompanhamento\">")
        	 .append("<tr>")
        	 .append("<td class=\"texto_negrito\">");

        	if (getExibirAbaFiltro().booleanValue()){
		    	//sempre vai criar o aba filtros desabilitada
                if(getTela() != null && !getTela().equals("") && getTela().equals("lista")){
                	criaAba("Filtros", "filtroItens.jsp", ABA_TIPO_ACOMPANHAMENTO_DESABILITADA, s);
                }else  if (getTela() != null && !getTela().equals("") && getTela().equals("visualizacao")){
                	criaAba("Filtros", "../../filtroItens.jsp", ABA_TIPO_ACOMPANHAMENTO_DESABILITADA, s);
                }else if(getTela() != null && !getTela().equals("") && getTela().equals("relatorioAcompanhamento")){
                	criaAba("Filtros", "../filtroItens.jsp", ABA_TIPO_ACOMPANHAMENTO_DESABILITADA, s);
                } else {	
                	criaAba("Filtros", "../filtroItens.jsp", ABA_TIPO_ACOMPANHAMENTO_DESABILITADA, s);
                }
        	}
        	
    		for(Iterator it = tiposAcompanhamentos.iterator();it.hasNext();) {
    			TipoAcompanhamentoTa ta = (TipoAcompanhamentoTa)it.next();
    			
    			//Se vier de um filtro que nao for a opção geral
    			// apenas exibe a aba do acompanhamento selecionado
    			if (exibirAbasAcompanhamento!= null && exibirAbasAcompanhamento.booleanValue()== false){
    				
                    if(getCodTipoAcompanhamentoSelecionado().equals(ta.getCodTa().toString())) {
                    	String situacaoAba = ABA_TIPO_ACOMPANHAMENTO_HABILITADA;
                    	//só vai criar se for a aba selecionada
                        criaAba(ta.getDescricaoTa(), getChamarPagina() + "?nuPagina=1&codTipoAcompanhamento=" + ta.getCodTa().toString() +  "&codTipoRefAcompanhamento=" + getCodTipoRefAcompanhamentoSelecionado() , situacaoAba, s);
                    }    
            		
            	} else {
            		
            		// Se vier do filtro geral, exibe todas as abas
        			String situacaoAba = ABA_TIPO_ACOMPANHAMENTO_DESABILITADA;
                    if(getCodTipoAcompanhamentoSelecionado().equals(ta.getCodTa().toString())) 
                        situacaoAba = ABA_TIPO_ACOMPANHAMENTO_HABILITADA;
                    
                    // passa a forma de visualização para que as abas de acompanhamento nao percam a configuração do filtro geral
                    String parametros = "?nuPagina=1&codTipoAcompanhamento=" + ta.getCodTa().toString() +  "&codTipoRefAcompanhamento=" +
                    					getCodTipoRefAcompanhamentoSelecionado() + "&hidFormaVisualizacaoEscolhida=geral";
                    
                    //cria todas as abas
                    if(getTela() != null && !getTela().equals("") && getTela().equals("lista")){
                    	criaAba(ta.getDescricaoTa(), getChamarPagina() + parametros , situacaoAba, s);
                    }else if (getTela() != null && !getTela().equals("") && getTela().equals("visualizacao")){
                    	criaAba(ta.getDescricaoTa(), "../../"+getChamarPagina() + parametros , situacaoAba, s);
                    }else{
                    	criaAba(ta.getDescricaoTa(), "../"+getChamarPagina() + parametros , situacaoAba, s);
                    }
        		
            	}
            }
            s.append("</td>")
             .append("</tr>")
             .append("</table>");
    	}
    	
    	return s;
		
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
    private void criaAba(String label, String pagina, String situacaoAba, StringBuffer s){
        s.append("<table class=\"").append(situacaoAba).append("\">")
         .append("<tr>")
         .append("<td nowrap>");
        
        //se estiver desabilitada, cria o link 
        if(situacaoAba != null && situacaoAba.equals(ABA_TIPO_ACOMPANHAMENTO_DESABILITADA)) {
        
			s.append("<a href=\"").append(pagina).append("\">");
	        
			s.append(label);
			
			s.append("</a>");
	        
        } else {
        
        	s.append(label);
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
     * Retorna String codTipoAcompanhamentoSelecionado.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String
     */
	public String getCodTipoAcompanhamentoSelecionado() {
		return codTipoAcompanhamentoSelecionado;
	}

	/**
	 * Atribui valor especificado para String codTipoAcompanhamentoSelecionado.<br>
	 * 
         * @param codTipoAcompanhamentoSelecionado
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setCodTipoAcompanhamentoSelecionado(String codTipoAcompanhamentoSelecionado) {
		this.codTipoAcompanhamentoSelecionado = codTipoAcompanhamentoSelecionado;
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
     * @param chamarPagina
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
	public void setChamarPagina(String chamarPagina) {
		this.chamarPagina = chamarPagina;
	}
	
	 /**
     * Retorna String codTipoRefAcompanhamentoSelecionado.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String
     */
	public String getCodTipoRefAcompanhamentoSelecionado() {
		return codTipoRefAcompanhamentoSelecionado;
	}

	 /**
     * Retorna String caminho.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String
     */
	public String getCaminho() {
		return caminho;
	}

	
	/**
	 * Atribui valor especificado para String codTipoRefAcompanhamentoSelecionado.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param codTipoRefAcompanhamentoSelecionado
	 */
	public void setCodTipoRefAcompanhamentoSelecionado(String codTipoRefAcompanhamentoSelecionado) {
		this.codTipoRefAcompanhamentoSelecionado = codTipoRefAcompanhamentoSelecionado;
	}

	 /**
     * Retorna Boolean exibirAbasAcompanhamento.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return Boolean exibirAbasAcompanhamento
     */	
	public Boolean getExibirAbasAcompanhamento() {
		return exibirAbasAcompanhamento;
	}

	/**
	 * Atribui valor especificado para Boolean exibirAbasAcompanhamento.<br>
	 * 
         * @param exibirAbasAcompanhamento
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */	
	public void setExibirAbasAcompanhamento(Boolean exibirAbasAcompanhamento) {
		this.exibirAbasAcompanhamento = exibirAbasAcompanhamento;
	}

        /**
         *
         * @return
         */
        public String getTela() {
		return tela;
	}

        /**
         *
         * @param tela
         */
        public void setTela(String tela) {
		this.tela = tela;
	}
	
        /**
         *
         * @param caminho
         */
        public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	
        /**
         *
         * @return
         */
        public Boolean getExibirAbaFiltro() {
		return exibirAbaFiltro;
	}

        /**
         *
         * @param exibirAbaFiltro
         */
        public void setExibirAbaFiltro(Boolean exibirAbaFiltro) {
		this.exibirAbaFiltro = exibirAbaFiltro;
	}
	
	
        /**
         *
         * @return
         */
        public Boolean getGerarPeriodo() {
		return gerarPeriodo;
	}

        /**
         *
         * @param gerarPeriodo
         */
        public void setGerarPeriodo(Boolean gerarPeriodo) {
		this.gerarPeriodo = gerarPeriodo;
	}
	
        /**
         *
         * @return
         */
        public Boolean getGerarRelatorio() {
		return gerarRelatorio;
	}


        /**
         *
         * @param gerarRelatorio
         */
        public void setGerarRelatorio(Boolean gerarRelatorio) {
		this.gerarRelatorio = gerarRelatorio;
	}
	
        /**
         *
         * @return
         */
        public Set getGruposUsuario() {
		return gruposUsuario;
	}
	

        /**
         *
         * @param gruposUsuario
         */
        public void setGruposUsuario(Set gruposUsuario) {
		this.gruposUsuario = gruposUsuario;
	}
	
	
        /**
         *
         * @return
         */
        public HttpServletRequest getRequest() {
		return request;
	}

        /**
         *
         * @param request
         */
        public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
        /**
         *
         * @return
         */
        public String getHidAcao() {
		return hidAcao;
	}

        /**
         *
         * @param hidAcao
         */
        public void setHidAcao(String hidAcao) {
		this.hidAcao = hidAcao;
	}
	
	
}