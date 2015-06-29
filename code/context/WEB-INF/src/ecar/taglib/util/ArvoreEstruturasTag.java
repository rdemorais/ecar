/*
 * Criado em 14/12/2004
 *
 */
package ecar.taglib.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import ecar.dao.EstruturaDao;
import ecar.dao.FuncaoDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.TipoAcompanhamentoDao;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.util.Dominios;

/**
 * @author felipev
 *  
 */
public class ArvoreEstruturasTag extends TagSupport {

	ValidaPermissao validaPermissao = new ValidaPermissao();

	/**
	 * 
	 */
	private static final long serialVersionUID = -2194337577924245741L;
	private ItemEstruturaIett itemEstrutura;
    private Boolean proximoNivel;
    private Boolean exibirLinks;
    private String contextPath;
	private SegurancaECAR seguranca;

    private String primeiroIettClicado; // utilizado no relatório de acompanhamento
    private String primeiroAriClicado; // utilizado no relatório de acompanhamento
    private String telaAnterior; //utilizado no relatório de acompanhamento

    private String codTipoAcompanhamentoSelecionado; //utilizado no relatório de acompanhamento
    private String periodoSelecionado;
 
    //O idLinha, nesse caso, obedecerá o seguinte padrão: ett<codEtt>_pai_iett<codIett>
	//Esse parâmetro é usado para tratar o caso de estrutura virtual
    private String idLinhaCadastro;
    
    // variavel para definir se o item foi expandido na tela de listagem(lista.jsp)
    private String ultimoIdLinhaExpandido;
    
    private Boolean estruturaVirtual = Boolean.FALSE;
    
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
 
    	//String idLinhaCadastro=""; 	       	
    	ItemEstruturaIett itemEstruturaSelecionado = null;
    	ItemEstruturaIett itemEstruturaAvo = null;
    	EstruturaEtt estruturaSelecionada = null;  
		ItemEstruturaDao itemDao = new ItemEstruturaDao(null);
		EstruturaDao estruturaDao= new EstruturaDao(null);
		FuncaoDao funcaoDao = new FuncaoDao(null);
		try {
			String codEtt = "";
			String codIett = "";
			String codAvo = "";
			
			if(ultimoIdLinhaExpandido != null && ultimoIdLinhaExpandido.contains("_avo")){
				idLinhaCadastro = ultimoIdLinhaExpandido;
			} 
			
	    	if(idLinhaCadastro != null && idLinhaCadastro.startsWith("ett") ){
				
				//caso da estrutura "filha" da virtual
				if(idLinhaCadastro.contains("_avo_")) {
					codEtt = idLinhaCadastro.substring(idLinhaCadastro.indexOf("_ett") + 4, idLinhaCadastro.indexOf("_avo_"));
					codIett = idLinhaCadastro.substring(idLinhaCadastro.indexOf("_avo_")+5, idLinhaCadastro.length());
					codAvo = idLinhaCadastro.substring(idLinhaCadastro.indexOf("_avo_")+5, idLinhaCadastro.length());
				} else {
					codEtt = idLinhaCadastro.substring(3, idLinhaCadastro.indexOf("_pai_"));
					codIett = idLinhaCadastro.substring(idLinhaCadastro.indexOf("_pai_iett")+9, idLinhaCadastro.length());
				
				}
				
				if(!codEtt.equals("")){
					estruturaSelecionada = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, new Long(codEtt));	
				}
				
				if(!codIett.equals("")){
					itemEstruturaSelecionado = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, new Long(codIett));	
				}
				
				
			} 
		
			
		} catch (Exception e) {
			// Não precisa levantar exceção
		}
    	//--
    	
    	JspWriter writer = this.pageContext.getOut();
        String connector = "";
        
        try {
            StringBuffer s = new StringBuffer();
            List lista = new ArrayList();
                        
            //cria a lista com os ascendentes da estrutura virtual
            if(idLinhaCadastro!=null && !idLinhaCadastro.equals("") && estruturaSelecionada!=null && estruturaSelecionada.isVirtual()){
            	if(itemEstruturaSelecionado!=null){
                	lista = new ItemEstruturaDao(null).getAscendentes(itemEstruturaSelecionado);
                	lista.add(itemEstruturaSelecionado);            		
            	}
            }
            //cria a lista de uma estrutura normal
            else{
                if(getItemEstrutura() != null){
                	lista = new ItemEstruturaDao(null).getAscendentes(getItemEstrutura());
                	lista.add(getItemEstrutura());
                }            	
            }
	                                    
            Iterator it = lista.iterator();
            ItemEstruturaIett itemEstruturaP;
            
            
            // para encontrar o nome do tipo de acompanhamento pelo código
            // Serve para a árvore em monitoramento [Thaise]
            String codTipoAcompanhamento = null;
            TipoAcompanhamentoDao tipoAcompanhamentoDao = null; 
            TipoAcompanhamentoTa ta = null; 
            
            if (this.getCodTipoAcompanhamentoSelecionado()!=null){
	            codTipoAcompanhamento = this.getCodTipoAcompanhamentoSelecionado();
	            tipoAcompanhamentoDao = new TipoAcompanhamentoDao();
	            ta = (TipoAcompanhamentoTa) tipoAcompanhamentoDao.buscar(
	            		TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
	            //s.append(ta.getDescricaoTa());
            }
                                    
            
            //começa a árvore
            s.append("<div id=\"menuemcascata\">");
            
            int nivel = 1;

            while (it.hasNext()) {
            	connector = "?"; // define o primeiro conector para parametros na url
                itemEstruturaP = (ItemEstruturaIett) it.next();
                
                s.append("<div class=\"cascata_nivel_").append(nivel).append("\">");
                
                //testar o item selecionado
                if(getItemEstrutura().getCodIett() == itemEstruturaP.getCodIett()){
                	s.append("<div class=\"selecionado\">");
                }
                
                s.append("<img src=\"").append(getContextPath()).append("/images/icon_seta_ident.gif\"> ");
                
                String titleEstrutura = "";
                String label = itemEstruturaP.getEstruturaEtt().getLabelEtt();
				if (label == null || label.equals(Dominios.STRING_VAZIA)){
					titleEstrutura = itemEstruturaP.getEstruturaEtt().getNomeEtt();
				}
				else{
					titleEstrutura = itemEstruturaP.getEstruturaEtt().getNomeEtt() + " (" + itemEstruturaP.getEstruturaEtt().getLabelEtt() + ")";
				}
                
                s.append(titleEstrutura);
                
                boolean possuiPermissaoConsulta = true;
                
                // se não existir o objeto seguranca significa que não interessa a permissão de consulta
                if(seguranca != null) {
    				possuiPermissaoConsulta = validaPermissao.permissaoConsultarItem(
    						itemEstruturaP,seguranca.getUsuario(),seguranca.getGruposAcesso());
                }
                                               
                ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
                String descricaoItem = itemEstruturaDao.criaColunaConteudoColunaArvoreAjax(itemEstruturaP, itemEstruturaP.getEstruturaEtt());
                
				if(descricaoItem != null && !descricaoItem.equals("")){
                    s.append(" - ");
					if( (getExibirLinks() == null || getExibirLinks().booleanValue()) && possuiPermissaoConsulta){						
	                    s.append("<a href=\"").append(getContextPath()).append("/cadastroItens/dadosGerais/frm_con.jsp");
	                    s.append("?codIett=").append(itemEstruturaP.getCodIett());
                    	s.append("&codAba="+ funcaoDao.getCodFuncaoDadosGerais());
                    	if (itemEstruturaP.getItemEstruturaIett() != null) {
        	                s.append("&" + "codIettPrincipal=" + itemEstruturaP.getItemEstruturaIett().getCodIett());
        	            }
        	            
                    	s.append("&" + "ultEttSelecionado=" + itemEstruturaP.getEstruturaEtt().getCodEtt().toString());
        	           	
        	        	if (itemEstruturaP.getItemEstruturaIett() != null){
        	        		s.append("&" + "ultimoIdLinhaDetalhado=ett" + itemEstruturaP.getEstruturaEtt().getCodEtt() + "_pai_iett" + itemEstruturaP.getItemEstruturaIett().getCodIett());
        	        	} else {
        	        		s.append("&" + "ultimoIdLinhaDetalhado=ett" + itemEstruturaP.getEstruturaEtt().getCodEtt() + "_pai_iett");
        	        	}
                    	
						s.append("\">");
                    }     	                    
                    s.append(descricaoItem);
                    if( (getExibirLinks() == null || getExibirLinks().booleanValue()) && possuiPermissaoConsulta){
                        s.append("</a>");
                    }
                }

				
				if(getPrimeiroIettClicado() != null && !"".equals(getPrimeiroIettClicado()) 
						&& getPrimeiroIettClicado().equals(itemEstruturaP.getCodIett().toString())) {
					
					if(getTelaAnterior() != null && !"".equals(getTelaAnterior())){
						s.append("<a href=\"").append(getTelaAnterior());
					}
					else {
	                    s.append("<a href=\"").append(getContextPath()).append("/acompanhamento/posicaoGeral.jsp");
	                    s.append("?primeiroIettClicado=").append(getPrimeiroIettClicado());
	                    s.append("&primeiroAriClicado=").append(getPrimeiroAriClicado());
						if(getCodTipoAcompanhamentoSelecionado() != null && !"".equals(getCodTipoAcompanhamentoSelecionado())){
		                    s.append("&codTipoAcompanhamento=").append(getCodTipoAcompanhamentoSelecionado());
						}
						if(getPeriodoSelecionado() != null && !"".equals(getPeriodoSelecionado())){
		                    s.append("&periodo=").append(getPeriodoSelecionado());
						}
					}
					s.append("\"> ");
					
					// Se vier de demandas ou associacao de demandas, o link sera voltar para listagem
					Long linkCodDemanda = (Long) this.pageContext.getSession().getAttribute("linkCodDemanda");
			        if (linkCodDemanda != null && !"".equals(linkCodDemanda)) {
			        	s.append("[Ir para listagem]</a>");
			        } else {
			        	s.append("[Voltar]</a>");
			        }
					
                    
				}
                
                if((getExibirLinks() == null || getExibirLinks().booleanValue())){
                	
                    s.append("<a href=\"").append(getContextPath()).append("/cadastroItens/listaItem/lista.jsp");
                    
                    // Rogerio (28/03/2007). Mantis #9358.
                    // Prepara o link para retorno na aba correta.
                    String codIett="";
                    if (itemEstruturaP.getItemEstruturaIett() != null) {
                        s.append(connector + "codIettPrincipal=").append(itemEstruturaP.getItemEstruturaIett().getCodIett());
                        connector = "&";
                        codIett = itemEstruturaP.getItemEstruturaIett().getCodIett().toString();
                    }
                    
                   	s.append(connector + "ultEttSelecionado=" + itemEstruturaP.getEstruturaEtt().getCodEtt().toString() );
                   	connector = "&";
                      	if (itemEstruturaP.getItemEstruturaIett() != null){
                		s.append(connector + "ultimoIdLinhaDetalhado=ett" + itemEstruturaP.getEstruturaEtt().getCodEtt() + "_pai_iett" + itemEstruturaP.getItemEstruturaIett().getCodIett());
                	} else {
                		s.append(connector + "ultimoIdLinhaDetalhado=ett" + itemEstruturaP.getEstruturaEtt().getCodEtt() + "_pai_iett");
                	}
                	
                	connector = "&";
                   	
                   	
                	s.append(connector + "ultimoIdLinhaExpandido=" + getUltimoIdLinhaExpandido());                  	
                   	connector = "&";
                   	
                    // Rogerio Fim Mantis #9358.
                    
                    s.append("\" >");
                    s.append(" [Ir para listagem]</a></div>");
                    
                    // Rogerio (28/03/2007) Mantis #9360.
                    // Escreve a variavel em JS que será usada para o retorno do form quando usuario clicar em cancelar.
                    if( itemEstrutura.getEstruturaEtt() != null && getItemEstrutura().getCodIett() == itemEstruturaP.getCodIett() ) {                   	
                    	writer.print("<script type=\"text/javascript\">var jsUltEttSelecionado = " + 
                    			itemEstrutura.getEstruturaEtt().getCodEtt().toString() + ";</script>");
                    } 
                }
                
                //testar para fechar o DIV selecionado
                if(itemEstrutura.getCodIett() == itemEstruturaP.getCodIett()){
                	s.append("</div>");
                }
                s.append("\n");
                nivel++;
            }
            
          
            //Imprime o item e sua estrutura virtual
            if(estruturaSelecionada!=null && estruturaSelecionada.isVirtual()){
            	connector = "?";
            	s.append("<div class=\"cascata_nivel_").append(nivel).append("\">");
                
                //div do item selecionado
                s.append("<div class=\"selecionado\">");
                
                s.append("<img src=\"").append(getContextPath()).append("/images/icon_seta_ident.gif\"> ");
                
                String titleEstrutura = "";
                String label = "";
                label = estruturaSelecionada.getLabelEtt();
				if (label == null || label.equals(Dominios.STRING_VAZIA)){
					titleEstrutura = estruturaSelecionada.getNomeEtt();
				}
				else{
					titleEstrutura = estruturaSelecionada.getNomeEtt() + " (" + estruturaSelecionada.getLabelEtt() + ")";
				}
                s.append(titleEstrutura);
                
               // s.append(estruturaSelecionada.getNomeEtt());
                
                boolean possuiPermissaoConsulta = true;
                
                // se não existir o objeto seguranca significa que não interessa a permissão de consulta
                if(seguranca != null) {
    				possuiPermissaoConsulta = validaPermissao.permissaoConsultarItem(
    						itemEstrutura,seguranca.getUsuario(),seguranca.getGruposAcesso());
                }
                              
                ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
                String descricaoItem = itemEstruturaDao.criaColunaConteudoColunaArvoreAjax(itemEstrutura, itemEstrutura.getEstruturaEtt());
                
				if(descricaoItem != null && !getEstruturaVirtual()){
                    s.append(" - ");
					if( (getExibirLinks() == null || getExibirLinks().booleanValue()) && possuiPermissaoConsulta){						
	                    s.append("<a href=\"").append(getContextPath()).append("/cadastroItens/dadosGerais/frm_con.jsp");
	                    s.append("?codIett=").append(itemEstrutura.getCodIett());
	                    s.append("&codAba="+ funcaoDao.getCodFuncaoDadosGerais());
	                 	s.append("&ultimoIdLinhaDetalhado=" + idLinhaCadastro);                
						s.append("\">");
                    }     	                    
                    s.append(descricaoItem);
                    if( (getExibirLinks() == null || getExibirLinks().booleanValue()) && possuiPermissaoConsulta){
                        s.append("</a>");
                    }
                }
				
				
                if((getExibirLinks() == null || getExibirLinks().booleanValue())){
                	
                    s.append("<a href=\"").append(getContextPath()).append("/cadastroItens/listaItem/lista.jsp");
                    
                    // Rogerio (28/03/2007). Mantis #9358.
                    // Prepara o link para retorno na aba correta.
                    if (itemEstrutura.getItemEstruturaIett() != null) {
                        s.append(connector + "codIettPrincipal=").append(itemEstrutura.getItemEstruturaIett().getCodIett());
                        connector = "&";
                    }
                    
                    if (itemEstrutura.getEstruturaEtt() != null){
                    	s.append(connector + "ultEttSelecionado=" + estruturaSelecionada.getCodEtt().toString() );
                       	connector = "&";
                    }
                   	
                   	s.append(connector + "ultimoIdLinhaDetalhado=" + idLinhaCadastro  );                  	
                   	connector = "&";
                   	
                   	s.append(connector + "ultimoIdLinhaExpandido=" + getUltimoIdLinhaExpandido());                  	
                   	connector = "&";
                   	
                    // Rogerio Fim Mantis #9358.
                    
                    s.append("\" >");
                    s.append(" [Ir para listagem]</a></div>");
                    
                    // Rogerio (28/03/2007) Mantis #9360.
                    // Escreve a variavel em JS que será usada para o retorno do form quando usuario clicar em cancelar.
                    if( itemEstrutura.getEstruturaEtt() != null && getItemEstrutura().getCodIett() == itemEstrutura.getCodIett() ) {                   	
                    	writer.print("<script type=\"text/javascript\">var jsUltEttSelecionado = " + 
                    			itemEstrutura.getEstruturaEtt().getCodEtt().toString() + ";</script>");
                    } 
                }
                
                //fecha o DIV selecionado
                s.append("</div>");

                s.append("\n");				
            }
//********************
            
            boolean existeNiveisAtivos=false;
            Iterator<EstruturaEtt> itEstrutura = null;
            if (itemEstrutura != null && itemEstrutura.getEstruturaEtt() != null){
            	itEstrutura =  itemEstrutura.getEstruturaEtt().getEstruturaEtts().iterator();
            }
            

            
            if (itEstrutura != null){
            	while(itEstrutura.hasNext()){
                   	EstruturaEtt estrutura = (EstruturaEtt)itEstrutura.next();
                	if (estrutura.getIndAtivoEtt().equalsIgnoreCase("S") && estruturaDao.verificarExibeEstrutura(estrutura, itemEstrutura)){
                		existeNiveisAtivos = true;
                		break;
                	}
                }
            }
            
            
            /* só faz o proximo nivel se nao passar o parametro (default) ou se passar como true 
             * e se existir mais niveis ativos abaixo dele */
            if ((getProximoNivel() == null || getProximoNivel().booleanValue())&& existeNiveisAtivos){
                if(estruturaSelecionada==null || !estruturaSelecionada.isVirtual() ){
                    s.append("<div class=\"cascata_nivel_").append(nivel).append("\"><img src=\"").append(getContextPath()).append("/images/icon_seta_ident.gif\">");
                    s.append("<a href=\"" + getContextPath() + "/cadastroItens/listaItem/lista.jsp?codIettPrincipal=")
                    	.append(itemEstrutura.getCodIett()).append("&ultimoIdLinhaDetalhado=iett"+ itemEstrutura.getCodIett() + "_pai_ett" + itemEstrutura.getEstruturaEtt().getCodEtt());
                    
                    s.append("\" >");
                    s.append("Ir para o pr&oacute;ximo n&iacute;vel </a></div>");                	
                }
            }
            
            s.append("</div>");
            
            //s.append("<br>");
            writer.print(s.toString());
        } catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
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
        /* processa o restante da página jsp */
        return Tag.EVAL_PAGE;
    }

    /**
     * Retorna Boolean proximoNivel.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return Boolean - (Returns the proximoNivel)
     */
    public Boolean getProximoNivel() {
        return proximoNivel;
    }
    
    /**
     * Atribui valor especificado para Boolean proximoNivel.<br>
     * 
     * @param proximoNivel
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setProximoNivel(Boolean proximoNivel) {
        this.proximoNivel = proximoNivel;
    }
    
    /**
     * Retorna String contextPath.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the contextPath)
     */
    public String getContextPath() {
        return contextPath;
    }
    
    /**
     * Atribui valor especificado para String contextPath.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param contextPath
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    
    /**
     * Retorna Boolean exibirLinks.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return Boolean - (Returns the exibirLinks)
     */
    public Boolean getExibirLinks() {
        return exibirLinks;
    }
    
    /**
     * Atribui valor especificado para Boolean exibirLinks.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param exibirLinks
     */
    public void setExibirLinks(Boolean exibirLinks) {
        this.exibirLinks = exibirLinks;
    }

	/**
	 * Retorna SegurancaECAR seguranca.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return SegurancaECAR - (Returns the seguranca)
	 */
	public SegurancaECAR getSeguranca() {
		return seguranca;
	}

	/**
	 * Atribui valor especificado para SegurancaECAR seguranca.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param seguranca
	 */
	public void setSeguranca(SegurancaECAR seguranca) {
		this.seguranca = seguranca;
	}

	/**
	 * Retorna String primeiroIettClicado.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getPrimeiroIettClicado() {
		return primeiroIettClicado;
	}

	/**
	 * Atribui valor especificado para String primeiroIettClicado.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param primeiroIettClicado
	 */
	public void setPrimeiroIettClicado(String primeiroIettClicado) {
		this.primeiroIettClicado = primeiroIettClicado;
	}

	/**
	 * Retorna String primeiroAriClicado.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getPrimeiroAriClicado() {
		return primeiroAriClicado;
	}

	/**
	 * Atribui valor especificado para String primeiroAriClicado.<br>
	 * 
         * @param primeiroAriClicado
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setPrimeiroAriClicado(String primeiroAriClicado) {
		this.primeiroAriClicado = primeiroAriClicado;
	}

	/**
	 * Retorna String telaAnterior.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getTelaAnterior() {
		return telaAnterior;
	}

	/**
	 * Atribui valor especficado para String telaAnterior.<br>
	 * 
         * @param telaAnterior
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setTelaAnterior(String telaAnterior) {
		this.telaAnterior = telaAnterior;
	}
    /**
     * Retorna ItemEstruturaIett 
.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return ItemEstruturaIett - (Returns the estrutura)
     */
    public ItemEstruturaIett getItemEstrutura() {
        return itemEstrutura;
    }

    /**
     * Atribui valor especificado para ItemEstruturaIett itemEstrutura.<br>
     * 
     * @param itemEstrutura
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setItemEstrutura(ItemEstruturaIett itemEstrutura) {
        this.itemEstrutura = itemEstrutura;
    }

    /**
     * 
     * Retorna String codTipoAcompanhamentoSelecionado.<br>
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
         *
         * @return
         */
        public String getPeriodoSelecionado() {
		return periodoSelecionado;
	}

        /**
         *
         * @param periodoSelecionado
         */
        public void setPeriodoSelecionado(String periodoSelecionado) {
		this.periodoSelecionado = periodoSelecionado;
	}

        /**
         *
         * @return
         */
        public String getIdLinhaCadastro() {
		return idLinhaCadastro;
	}

        /**
         *
         * @param idLinhaCadastro
         */
        public void setIdLinhaCadastro(String idLinhaCadastro) {
		this.idLinhaCadastro = idLinhaCadastro;
	}

        /**
         *
         * @return
         */
        public String getUltimoIdLinhaExpandido() {
		return ultimoIdLinhaExpandido;
	}

        /**
         *
         * @param ultimoIdLinhaExpandido
         */
        public void setUltimoIdLinhaExpandido(String ultimoIdLinhaExpandido) {
		this.ultimoIdLinhaExpandido = ultimoIdLinhaExpandido;
	}

        /**
         *
         * @return
         */
        public Boolean getEstruturaVirtual() {
		return estruturaVirtual;
	}

        /**
         *
         * @param estruturaVirtual
         */
        public void setEstruturaVirtual(Boolean estruturaVirtual) {
		this.estruturaVirtual = estruturaVirtual;
	}

}