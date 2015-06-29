/*
 * Criado em 16/02/2005
 */ 
package ecar.taglib.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import comum.util.Util;

import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.ItemEstUsutpfuacDao;
import ecar.dao.UsuarioDao;
import ecar.exception.ECARException;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.TipoAcompFuncAcompTafc;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UsuarioUsu;

/** 
 * @author felipev
 *
 */
public class BarraLinksRegAcompParecerTag extends TagSupport{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6728974818698770839L;

	private AcompReferenciaItemAri acompReferenciaItem;
    private UsuarioUsu usuario;
    private String selectedFuncao;
    private AcompReferenciaItemAri acompReferenciaItemSubNivel;
    private String primeiroAcomp;
    private HttpServletRequest request;
    private List itensFuncaoAcompanhamento;
    private String srcImg;
    private boolean liberado = false;
    private String periodo;
    private String tela;
    private String primeiroIettClicado;
    private String primeiroAriClicado;
    private String itemDoNivelClicado;
    private String codTipoAcompanhamento;
    private static final String PATH_ACOMP = "/acompanhamento/situacao/";
    private String hidFormaVisualizacaoEscolhida;


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
        	srcImg = "";
        	s.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" id=\"abas\"><tr><td>");
			
			/* Aba de relação dos Itens, é fixa não está no banco */
        	String situacaoAba = "abadesabilitada";
            
            String linkPrimeiro = "";
            if(!"".equals(primeiroAcomp))
            	linkPrimeiro = "primeiroAcomp=" + primeiroAcomp + "&";
            
            String link = "";
            ItemEstUsutpfuacDao itemEstUsutpfuacDao = new ItemEstUsutpfuacDao(request);
            List usuariosPermitidos = itemEstUsutpfuacDao.getUsuariosAcompanhamento(acompReferenciaItem.getItemEstruturaIett());
            
            /* Abas de Pareceres */
            
            AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao (request);
            ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(request);
            List funcoesAcompanhamento = new AcompReferenciaItemDao(request).getTipoFuncAcompByAcompRefrenciaItem(acompReferenciaItem);
            List listTipoAcompFuncAcomp = new ArrayList(getAcompReferenciaItem().getAcompReferenciaAref().getTipoAcompanhamentoTa().getTipoAcompFuncAcompTafcs());
            
            Iterator itAcompAref = itensFuncaoAcompanhamento.iterator();
            
        	
            while(itAcompAref.hasNext()){                
            
            	TipoFuncAcompTpfa tipoFuncaoAcomp = (TipoFuncAcompTpfa) itAcompAref.next();
                
             
				/*
				 * Nova regra (mantis 11289):
				 * Ao exibir as abas de tipos de funções de acompanhamento:
				 * Para registro de posição OBRIGATÓRIO continua com está atualmente.
				 * Para tipo de função de acompanhamento que seja OPCIONAL o registro de posição no tipo de 
				 * acompanhamento selecionado (TipoAcompFuncAcompTafc.indRegistroPosicaoTafc), 
				 * somente apresentar a aba para o usuário que possuir permissão 
				 * ou que o acompanhamento esteja liberado.
				 */
                
                /*
                 * Se o ari não tiver arel informado, não exibe a aba.
                 * Se tiver, e o arel estiver liberado, exibe a aba.
                 * Se tiver ari e arel não liberado, exibir a aba só se o usuario logado = usuario da tpfa do tafc.
                 */
                
                if(getAcompReferenciaItem() != null && getAcompReferenciaItem().getAcompRelatorioArels() != null && !getAcompReferenciaItem().getAcompRelatorioArels().isEmpty()){
                
                	//Descobrindo o Arel da Funcao
                	AcompRelatorioArel arel = null;
                	for(Iterator itArel = getAcompReferenciaItem().getAcompRelatorioArels().iterator(); itArel.hasNext();){
                		AcompRelatorioArel auxArel = (AcompRelatorioArel) itArel.next();
                		if(auxArel.getTipoFuncAcompTpfa().equals(tipoFuncaoAcomp)){
                			arel = auxArel;
                			break;
                		}
                	}
                	
                	if(arel == null){
                		continue;
                	}
                	
                	//Descobrindo o Tafc da Funcao
	                TipoAcompFuncAcompTafc tafc = null;
					for(Iterator itTafc = listTipoAcompFuncAcomp.iterator(); itTafc.hasNext();){
						TipoAcompFuncAcompTafc tafcTemp = (TipoAcompFuncAcompTafc) itTafc.next();
						if(tafcTemp.getTipoFuncAcompTpfa().equals(tipoFuncaoAcomp)){
							tafc = tafcTemp;
							break;
						}
					}
		            
	                String funcaoParecer = "ACOMP" + tipoFuncaoAcomp.getCodTpfa();
	                
	                situacaoAba = "abadesabilitada";
	                
	                if(funcaoParecer.equals(selectedFuncao))                    
	                	situacaoAba = "abahabilitada";
	                else if (selectedFuncao.equals("")) {
	                	situacaoAba = "abahabilitada";
	                	selectedFuncao = funcaoParecer;
	                }
	                	//habilitar a primeira aba
	                	
	               String tipoPadraoExibicaoAba = (String) request.getAttribute("tipoPadraoExibicaoAba");
	               
	               link = request.getContextPath() + PATH_ACOMP + "relatorios.jsp?"+ linkPrimeiro+"funcao=SITUACAO"+ "&funcaoParecer=" + funcaoParecer + "&codTpfa=" + tipoFuncaoAcomp.getCodTpfa() + 
	                 		"&periodo="+ periodo +"&tela=" + tela+"&hidFormaVisualizacaoEscolhida=" + hidFormaVisualizacaoEscolhida+ "&itemDoNivelClicado="+itemDoNivelClicado + "&codTipoAcompanhamento=" + codTipoAcompanhamento + "&tipoPadraoExibicaoAba=" + tipoPadraoExibicaoAba;
	                 
	                if(tela.equals("V")) {
	                	 link+=	"&primeiroIettClicado="+ primeiroIettClicado + "&primeiroAriClicado="+ primeiroAriClicado;
	                } 
	                
	                link+="&";
	                
	                // monta as informaçoes complementares como Dica
	                String titulo = "";
					UsuarioUsu usu = null;
					SisAtributoSatb sisAtributoSatb = null;
					UsuarioDao usuDao = new UsuarioDao(null);

				
					if(arel != null) {						
						
						
						ItemEstUsutpfuacIettutfa itemEstUsu 
							= itemEstUsuDao.buscar(acompReferenciaItem.getItemEstruturaIett().getCodIett(), arel.getTipoFuncAcompTpfa().getCodTpfa());
				
						titulo += "<table  cellpadding='0' cellspacing='0'>";
						if(itemEstUsu != null) {
							if (itemEstUsu.getUsuarioUsu() != null) {
									
								if(itemEstUsu.getUsuarioUsu().getNomeUsu() != null) {
									titulo += "<tr><td><b>Resp</b>: " + itemEstUsu.getUsuarioUsu().getNomeUsu() + ("</tr></td>");
								} else {
									titulo += "<tr><td><b>Resp</b>: "+"</tr></td>";
								}
										
								if(itemEstUsu.getUsuarioUsu().getComercTelefoneUsu() != null) {
									titulo += "<tr><td><b>Tel</b>: " + itemEstUsu.getUsuarioUsu().getComercTelefoneUsu() + "</tr></td>";
								} else {
									titulo += "<tr><td><b>Tel</b>: " + "</tr></td>";
								}
										
								if(itemEstUsu.getUsuarioUsu().getEmail1UsuSent() != null) {	 	
									titulo += "<tr><td><b>E-mail</b>: " + itemEstUsu.getUsuarioUsu().getEmail1UsuSent() + "</tr></td>";
								} else {
									titulo += "<tr><td><b>E-mail</b>: " + "</tr></td>";
								}	
								
							
							} else if (itemEstUsu.getSisAtributoSatb() != null) {
								
								//Quando for grupo
								if (itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
									titulo += "<tr><td><b>GRUPO</b>: "+itemEstUsu.getSisAtributoSatb().getDescricaoSatb()+"</tr></td>";
									Iterator itUsuarios = itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
								}
							
							} 
						
						} else {
								
							titulo += "<tr><td><b>Resp: </b>Não existe usuário ou grupo associado.";
							
						}
						
						titulo +="</table>";
				
						liberado = false;
						srcImg = "";
											
						if(arel.getIndLiberadoArel() != null && arel.getIndLiberadoArel().equals("S")){
							liberado = true;
							srcImg = Util.getURLImagemAcompanhamento(arel.getCor(), request, arel.getTipoFuncAcompTpfa());
						}
						
					} else {
						//if arel != null
						List funcoes = new ItemEstUsutpfuacDao(null).getFuacOrderByFuncAcomp(acompReferenciaItem.getItemEstruturaIett());
						for (Iterator itUsu = funcoes.iterator(); itUsu.hasNext();) {
							ItemEstUsutpfuacIettutfa fuac = (ItemEstUsutpfuacIettutfa) itUsu.next();
							
							if(fuac.getTipoFuncAcompTpfa().equals(tipoFuncaoAcomp)){
								if (fuac.getUsuarioUsu() != null){
									usu = fuac.getUsuarioUsu();
								} else if (fuac.getSisAtributoSatb() != null){
									sisAtributoSatb = fuac.getSisAtributoSatb(); 
								}
								
							}
						}
						if (usu != null){
							titulo += usu.getNomeUsuSent() + "Aguarda elaboração do parecer <br>";
						} else if (sisAtributoSatb != null){
							titulo += sisAtributoSatb.getDescricaoSatb() + "Aguarda elaboração do parecer <br>";
						}
						
					}
					
	                criaAbaFuncaoAcomp(situacaoAba, link, tipoFuncaoAcomp.getLabelPosicaoTpfa(), s, titulo, usu, sisAtributoSatb);
                }
            }
            /* **** FIM - Abas de Pareceres */ 
            s.append("</td></tr></table>");
            
            writer.print(s.toString());
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return Tag.SKIP_BODY;

    }
	

	/**
	 * Cria Aba com .<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param String situacaoAba
	 * @param String link
	 * @param String label
	 * @param StringBuffer s
	 * @throws ECARException
	 */
	private void criaAbaFuncaoAcomp(String situacaoAba, String link, String label, StringBuffer s, String titulo, UsuarioUsu usu, 
			SisAtributoSatb sisAtributoSatb) throws ECARException{
		
		
		link += "codAri=" + acompReferenciaItem.getCodAri() + "&codAcomp=" + acompReferenciaItem.getAcompReferenciaAref().getCodAref();
		
		String codArisControle = request.getParameter("codArisControle");
        String codArisControleVisualizacao = request.getParameter("codArisControleVisualizacao");
        
		if(codArisControle != null && !codArisControle.equals(""))
			link += "&codArisControle=" + codArisControle;
		
		if(codArisControleVisualizacao != null && !codArisControleVisualizacao.equals(""))
			link += "&codArisControleVisualizacao=" + codArisControleVisualizacao;
		
		s.append("<table class=\"").append(situacaoAba).append("\"><tr><td nowrap>");
		UsuarioDao usuDAO = new UsuarioDao();
	    s.append("<a href=\"").append(link).append("\">");
        s.append(label);
        s.append("</a>");
	
        if ( !liberado && (srcImg == null || "".equals(srcImg.trim()))) //como estava anteriormente
        	s.append(Util.getTagDica(label, request.getContextPath() , titulo));
        else 
        	s.append(Util.getTagDicaComImagemParecer(label, request.getContextPath(), srcImg, titulo));
        s.append("</td></tr></table>");
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
     * Retorna valor especificado para String periodo.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the periodo)
     */
    public String getPeriodo() {
        return periodo;
    }
    
    /**
     * Atribui valor especificado para String periodo.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param periodo
     */
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
    
    /**
     * Retorna valor especificado para String itemDoNivelClicado.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the itemDoNivelClicado)
     */
    public String getItemDoNivelClicado() {
        return itemDoNivelClicado;
    }
    
    /**
     * Atribui valor especificado para String itemDoNivelClicado.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param itemDoNivelClicado
     */
    public void setItemDoNivelClicado(String itemDoNivelClicado) {
        this.itemDoNivelClicado = itemDoNivelClicado;
    }
    
    
    /**
     * Retorna valor especificado para String primeiroAriClicado.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the primeiroAriClicado)
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
     * Retorna valor especificado para String primeiroIettClicado.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the primeiroIettClicado)
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
     * Retorna valor especificado para String tela.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the tela)
     */
    public String getTela() {
        return tela;
    }
    
    /**
     * Atribui valor especificado para String tela.<br>
     * 
     * @param tela
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setTela(String tela) {
        this.tela = tela;
    }
    
    /**
     * Retorna valor especificado para String codTipoAcompanhamento.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String codTipoAcompanhamento- (Returns the codTipoAcompanhamento)
     */
    public String getCodTipoAcompanhamento() {
        return codTipoAcompanhamento;
    }
    
    /**
     * Atribui valor especificado para String codTipoAcompanhamento.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param codTipoAcompanhamento
     */
    public void setCodTipoAcompanhamento(String codTipoAcompanhamento) {
        this.codTipoAcompanhamento = codTipoAcompanhamento;
    }
    
    
    /**
     * Retorna valor especificado para String selectedFuncao.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return String - (Returns the selectedFuncao)
     */
    public String getSelectedFuncao() {
        return selectedFuncao;
    }
    
    /**
     * Atribui valor especificado para String selectedFuncao.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param selectedFuncao
     */
    public void setSelectedFuncao(String selectedFuncao) {
        this.selectedFuncao = selectedFuncao;
    }
    
    /**
     * Retorna UsuarioUsu usuario.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return UsuarioUsu - (Returns the usuario)
     */
    public UsuarioUsu getUsuario() {
        return usuario;
    }
    
    /**
     * Atribui valor especificado para UsuarioUsu usuario.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @param usuario
     */
    public void setUsuario(UsuarioUsu usuario) {
        this.usuario = usuario;
    }

    /**
     * Retorna AcompReferenciaItemAri acompReferenciaItemSubNivel.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return AcompReferenciaItemAri
     */
	public AcompReferenciaItemAri getAcompReferenciaItemSubNivel() {
		return acompReferenciaItemSubNivel;
	}

	/**
	 * Atribui valor especificado para AcompReferenciaItemAri acompReferenciaItemSubNivel.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
         * @param acompReferenciaItemSubNivel
	 */
	public void setAcompReferenciaItemSubNivel(AcompReferenciaItemAri acompReferenciaItemSubNivel) {
		this.acompReferenciaItemSubNivel = acompReferenciaItemSubNivel;
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
	
    /**
     * @return HttpServletRequest
     */
    public HttpServletRequest getRequest() {
		return request;
	}

    /**
     * @param request 
     */
    public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
    
    
    /**
     * Retorna List itensFuncaoAcompanhamento.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
     * @return List - (Returns the itensFuncaoAcompanhamento)
     */
    public List getItensFuncaoAcompanhamento() {
        return itensFuncaoAcompanhamento;
    }
    
    /**
     * Atribui valor especificado para List itensFuncaoAcompanhamento.<br>
     * 
     * @param itensFuncaoAcompanhamento
     * @author N/C
	 * @since N/C
	 * @version N/C
     */
    public void setItensFuncaoAcompanhamento(List itensFuncaoAcompanhamento) {
        this.itensFuncaoAcompanhamento = itensFuncaoAcompanhamento;
    }


    /**
     *
     * @return
     */
    public String getHidFormaVisualizacaoEscolhida() {
		return hidFormaVisualizacaoEscolhida;
	}


        /**
         *
         * @param hidFormaVisualizacaoEscolhida
         */
        public void setHidFormaVisualizacaoEscolhida(
			String hidFormaVisualizacaoEscolhida) {
		this.hidFormaVisualizacaoEscolhida = hidFormaVisualizacaoEscolhida;
	}
    
}
