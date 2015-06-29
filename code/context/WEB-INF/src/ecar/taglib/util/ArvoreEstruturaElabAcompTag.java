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

import ecar.dao.ItemEstruturaDao;
import ecar.pojo.ItemEstruturaIett;

/**
 * @author cristiano
 *  
 */
public class ArvoreEstruturaElabAcompTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8136547080998871299L;

	private Logger logger = Logger.getLogger(this.getClass());

	private ItemEstruturaIett itemEstrutura;
    private String contextPath;
    private String codigoAcomp;
    private String primeiroItemAriClicado;
    private String codItemClicadoSubNiveis;
    private List listaParaArvore; // lista de strings contendo a concatenação de iett_ari relacionada na aba relações
    private String nivelPrimeiroIettClicado;
    private String abaAtual;
    private String primeiroAcomp;
    
    /**
     *
     */
    public static String ABA_DADOS_BASICOS = "ABA_DADOS_BASICOS";
    /**
     *
     */
    public static String ABA_REALIZADO_FISICO = "ABA_REALIZADO_FISICO";
    /**
     *
     */
    public static String ABA_ACOMPANHAMENTOS = "ABA_ACOMPANHAMENTOS";
    /**
     *
     */
    public static String PATH_ACOMP = "/regAcompanhamento/elabAcompanhamento/";

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
            StringBuffer s = new StringBuffer();
            List lista = new ArrayList();

            if(getItemEstrutura() != null)
            	lista = new ItemEstruturaDao(null).getAscendentes(getItemEstrutura());	
                        
            if (getItemEstrutura() != null)
            	lista.add(getItemEstrutura());

            Iterator it = lista.iterator();
            ItemEstruturaIett itemEstruturaP;

            s.append("<div id=\"menuemcascata\">");
            
            int nivel = 1;

            String linkPrimeiro = "";
            if(!"".equals(primeiroAcomp)){
            	linkPrimeiro = "primeiroAcomp="+primeiroAcomp+"&";
            }
            
            while (it.hasNext()) {
                itemEstruturaP = (ItemEstruturaIett) it.next();
                
                s.append("<div class=\"cascata_nivel_" + nivel + "\">");
                
                //testar o item selecionado
                if(getItemEstrutura().getCodIett() == itemEstruturaP.getCodIett()){
                	s.append("<div class=\"selecionado\">");
                }
                
                s.append("<img src=\"" + getContextPath() + "/images/icon_seta_ident.gif\"> ");

                s.append(itemEstruturaP.getEstruturaEtt().getNomeEtt().trim());
                s.append(" - ");
                
                if(ABA_ACOMPANHAMENTOS.equals(this.getAbaAtual())) {
                    s.append(itemEstruturaP.getNomeIett().trim());
                    s.append("</div>");                    
                }
                else {
                    String codAri = "";
                    
                    if(itemEstrutura.getCodIett() == itemEstruturaP.getCodIett()){
    	                Long codAriIett = getCodAri(itemEstruturaP.getCodIett());
    	                
    	                if(codAriIett != null) {
    	                	codAri = "?"+linkPrimeiro+"codAri=" + codAriIett;
    	                }
                    }
                	
                    if("".equals(codAri)) {
                        s.append(itemEstruturaP.getNomeIett().trim());
                	}
                	else {
                        if(ABA_DADOS_BASICOS.equals(this.getAbaAtual())) {
                        	s.append("<a href=\"" + getContextPath() + PATH_ACOMP + "dadosGerais/frm_con.jsp");
                        }
                        else {
                        	s.append("<a href=\"" + getContextPath() + PATH_ACOMP + "realizadoFisico.jsp");
                        }
                        s.append(codAri + "&codAcomp=" + getCodigoAcomp()); 
                        s.append("\">");
                        s.append(itemEstruturaP.getNomeIett().trim());
                        s.append("</a>");
                	}
                
    				if(getCodItemClicadoSubNiveis() != null && getPrimeiroItemAriClicado() != null
    						&& !"".equals(getCodItemClicadoSubNiveis()) && !"".equals(getPrimeiroItemAriClicado())  
    						&& Long.parseLong(getCodItemClicadoSubNiveis()) == itemEstruturaP.getCodIett().longValue()) {
    					
                        s.append("<a href=\"" + getContextPath() + PATH_ACOMP + "realizadoFisico.jsp");
                        s.append("?"+linkPrimeiro+"codAri=" + getPrimeiroItemAriClicado());
                        s.append("&codAcomp=" + getCodigoAcomp());
    					s.append("\">");
                        s.append("[Voltar]</a>");
    				}
    				else {
    					if(nivel == Integer.parseInt(this.getNivelPrimeiroIettClicado())) {
	                        s.append("<a href=\"" + getContextPath() + PATH_ACOMP + "listaItens.jsp");
	                        s.append("?"+linkPrimeiro+"codAcomp=" + getCodigoAcomp() + "&sinalizarItem=" + itemEstruturaP.getCodIett() + "#ancoraItem" + itemEstruturaP.getCodIett());
	    					s.append("\">");
	   						s.append("[Ir para Relação]");
	                        s.append("</a>");
    					}
    				}
                    
                    s.append("</div>");                    
                }
				
                //testar para fechar o DIV selecionado
                if(itemEstrutura.getCodIett() == itemEstruturaP.getCodIett()){
                	s.append("</div>");
                }
                
                s.append("\n");
                nivel++;
            }

            
            s.append("</div>");
            
            s.append("<br>");
            writer.print(s.toString());
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
        /* processa o restante da página jsp */
        return Tag.EVAL_PAGE;
    }
    
    /**
     * Recupera o código de um ARI através do codIett apresentado na relação.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param Long codIett
     * @return Long
     */
    private Long getCodAri(Long codIett) {
    	Iterator it = getListaParaArvore().iterator();
    	
    	while(it.hasNext()) {
    		String str = (String)it.next();
    		int indiceUnderline = str.indexOf("_");
    		String item = str.substring(0, indiceUnderline);
    		String ari = str.substring(indiceUnderline + 1);
    		
    		if(Long.parseLong(item) == codIett.longValue()) {
    			return Long.valueOf(ari); 
    		}
    	}
    	
    	return null;
    }

    /**
     * Retorna String codigoAcomp.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String
     */
	public String getCodigoAcomp() {
		return codigoAcomp;
	}

	/**
	 * Atribui valor especificado para String codigoAcomp.<br>
	 * 
         * @param codigoAcomp
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setCodigoAcomp(String codigoAcomp) {
		this.codigoAcomp = codigoAcomp;
	}

	/**
	 * Retorna String codItemClicadoSubNiveis.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getCodItemClicadoSubNiveis() {
		return codItemClicadoSubNiveis;
	}

	/**
	 * Atribui valor especificado para String codItemClicadoSubNiveis.<br>
	 * 
         * @param codItemClicadoSubNiveis
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setCodItemClicadoSubNiveis(String codItemClicadoSubNiveis) {
		this.codItemClicadoSubNiveis = codItemClicadoSubNiveis;
	}

	/**
	 * Retorna String contextPath.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
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
	 * Retorna valor ItemEstruturaIett itemEstrutura.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return ItemEstruturaIett
	 */
	public ItemEstruturaIett getItemEstrutura() {
		return itemEstrutura;
	}

	/**
	 * Atribui valor especificado para ItemEstruturaIett itemEstrutura.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param itemEstrutura
	 */
	public void setItemEstrutura(ItemEstruturaIett itemEstrutura) {
		this.itemEstrutura = itemEstrutura;
	}

	/**
	 * Retorna List listaParaArvore.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return List
	 */
	public List getListaParaArvore() {
		return listaParaArvore;
	}

	/**
	 * Atribui valor especificado para List listaParaArvore.<br>
	 * 
         * @param listaParaArvore
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setListaParaArvore(List listaParaArvore) {
		this.listaParaArvore = listaParaArvore;
	}

	/**
	 * Retorna String nivelPrimeiroIettClicado.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getNivelPrimeiroIettClicado() {
		return nivelPrimeiroIettClicado;
	}

	/**
	 * Atribui valor especificado para String nivelPrimeiroIettClicado.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param nivelPrimeiroIettClicado
	 */
	public void setNivelPrimeiroIettClicado(String nivelPrimeiroIettClicado) {
		this.nivelPrimeiroIettClicado = nivelPrimeiroIettClicado;
	}

	/**
	 * Retorna String primeiroItemAriClicado.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getPrimeiroItemAriClicado() {
		return primeiroItemAriClicado;
	}

	/**
	 * Atribui valor especificado para String primeiroItemAriClicado.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param primeiroItemAriClicado
	 */
	public void setPrimeiroItemAriClicado(String primeiroItemAriClicado) {
		this.primeiroItemAriClicado = primeiroItemAriClicado;
	}

	/**
	 * Retorna String abaAtual.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getAbaAtual() {
		return abaAtual;
	}

	/**
	 * Atribui valor especificado para String abaAtual.<br>
	 * 
         * @param abaAtual
         * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setAbaAtual(String abaAtual) {
		this.abaAtual = abaAtual;
	}

	/**
	 * Retorna String primeiroAcomp.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return String
	 */
	public String getPrimeiroAcomp() {
		return primeiroAcomp;
	}

	/**
	 * Atribui valor especificado para String primeiroAcomp.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param primeiroAcomp
	 */
	public void setPrimeiroAcomp(String primeiroAcomp) {
		this.primeiroAcomp = primeiroAcomp;
	}
}