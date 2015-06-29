package ecar.taglib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import ecar.bean.NomeImgsNivelPlanejamentoBean;
import ecar.dao.AcompReferenciaDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.CorDao;
import ecar.dao.ItemEstUsutpfuacDao;
import ecar.dao.ItemEstrutMarcadorDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.TipoFuncAcompDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.Cor;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstrutMarcadorIettm;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.StatusRelatorioSrl;
import ecar.pojo.TipoAcompFuncAcompTafc;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UsuarioAtributoUsua;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * @author BarbaraSiqueira
 * @since 28/11/2007
 */
public class ArvoreFilhosItemEstruturaTag extends TagSupport {

	private static final long serialVersionUID = 6791633584021020042L;
	
	// atributos da tag
	private ItemEstruturaIett itemEstrutura;
	private UsuarioUsu usuario;
	private StatusRelatorioSrl statusLiberado;
	private List<ItemEstruturaIett> colecaoItens;
	private Collection<AcompReferenciaAref> periodosConsiderados;
	
	private List tpfaOrdenadosPorEstrutura;
	
	private String status;
	private String idPagina;
	private String pathEcar;
	private String caminhoUrl;
	private String codTipoAcompanhamento;
	private String exigeLiberarAcompanhamento;
	private String codAref;
		
	private HttpServletRequest request;
	private SegurancaECAR seguranca;
	private boolean permissaoAdministradorAcompanhamento;
	
	private List itensSessao;
	private List itensSessaoVisualizar;
	private boolean usuarioLogadoEmiteParecer;
	/**
     * Inicializa a montagem da tag para ser adicionada na tela de HTML.<br>
     * 
	 * @return int
	 * @throws JspException
     */
    @Override
    public int doStartTag() throws JspException {
    	
        JspWriter writer = this.pageContext.getOut();                
        try {        	       	        	
        	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao();
        	
            StringBuffer s = new StringBuffer();
            if(tpfaOrdenadosPorEstrutura==null)
            	tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas(); 

            s = imprimeColunasDoItem(getItemEstrutura());
			
            writer.print(s.toString());
        } catch (Exception e) {
        	Logger.getLogger(this.getClass()).error(e);
        	e.printStackTrace();
        }
        return Tag.SKIP_BODY;
    }
    
    private StringBuffer imprimeColunasDoItem(ItemEstruturaIett pItemEstrutura) {
    	StringBuffer retorno = new StringBuffer();
    	StringBuffer imprimeFilhos = new StringBuffer();

    	if(request.getAttribute("itensSessao")!=null)
    		itensSessao = (List)request.getAttribute("itensSessao");
    	if(request.getAttribute("itensSessaoVisualizar")!=null)
    		itensSessaoVisualizar = (List)request.getAttribute("itensSessaoVisualizar");

    	//Verficar se tem filhos e se tiver deverá gerar o link para a arvore deles
    	String linkAbrirArvoreIettComeco = "";
    	String linkAbrirArvoreIettFim = "";
    	ItemEstruturaDao itemDao = new ItemEstruturaDao(getRequest());
    	List<ItemEstruturaIett> filhosExibir = new ArrayList<ItemEstruturaIett>();
		List<ItemEstruturaIett> filhos = new ArrayList<ItemEstruturaIett>();
		try {
			filhos = itemDao.getDescendentesViaQry(pItemEstrutura);
		} catch(ECARException e) {
			Logger.getLogger(this.getClass()).error(e);
			e.printStackTrace();
		}
		if(filhos != null && filhos.size() > 0){
			Iterator<ItemEstruturaIett> itFilhos = filhos.iterator();
			int contadorFilhos = 1;
			while(itFilhos.hasNext()){
				ItemEstruturaIett filho = (ItemEstruturaIett) itFilhos.next();
				if(getColecaoItens().contains(filho)){
					//monta a árvore de filhos
					imprimeFilhos.append("</tr>");
					imprimeFilhos.append("<tr id=\"itensFilhoDoItem"+pItemEstrutura.getCodIett()+"Filho"+contadorFilhos+"\" style=\"display:none\" class=\"cor_sim\"");
					imprimeFilhos.append("onmouseover=\"javascript:destacaLinha(this,'over','')\" onmouseout=\"javascript: destacaLinha(this,'out','cor_sim')\"");
					imprimeFilhos.append("onClick=\"javascript:destacaLinha(this,'click','cor_sim')\" bgcolor=\""+filho.getEstruturaEtt().getCodCor3Ett()+"\">");
					imprimeFilhos.append(imprimeColunasDoItem(filho));
					contadorFilhos++;
					if(!getIdPagina().equals("proximosItens")) {
						//criação do link para abrir a arvore de itens do item
						linkAbrirArvoreIettComeco = "<a href=\"javascript:abrirItem(form, '" + pItemEstrutura.getCodIett() + "', '" + status + "',"+filhos.size()+")\">";
						linkAbrirArvoreIettFim = "</a>";
					}
				} 
			}
		}
    	
    	//System.out.println(">>>>>>>>>> comeca a imprimir "+ System.currentTimeMillis());
    	retorno.append(imprimeColunaDoMarcador(pItemEstrutura));
    	retorno.append(imprimeColunaIdentacaoNivel(pItemEstrutura, linkAbrirArvoreIettComeco, linkAbrirArvoreIettFim));
    	retorno.append(imprimeColunaImagemNivel(pItemEstrutura));
    	retorno.append(imprimeColunaOrgaoResponsavel(pItemEstrutura));
    	retorno.append(imprimeColunaImagemAnotacao(pItemEstrutura));
    	retorno.append(imprimeColunaPareceres(pItemEstrutura));
    	//System.out.println(">>>>>>>>>> termina de imprimir "+ System.currentTimeMillis());

    	//monta a árvore de filhos
    	retorno.append(imprimeFilhos);
		
    	return retorno;
    }
	
    /*
     * Método que imprime a primeira coluna da linha do item: coluna do marcador se tiver anotação
     */
    private StringBuffer imprimeColunaDoMarcador(ItemEstruturaIett pItemEstrutura) {
    	StringBuffer retorno = new StringBuffer();
    	String caminhoEcar = getPathEcar();
    	ItemEstruturaIett item = pItemEstrutura;
    	ItemEstrutMarcadorDao marcadorDao = new ItemEstrutMarcadorDao(getRequest());

        retorno.append("<td>");
		if(!getStatus().equals("true")){
			try {
				ItemEstrutMarcadorIettm marcador = marcadorDao.getUltimoMarcador(item, getUsuario());
				if(marcador != null){
					retorno.append("<a href=\"javascript:abrirPopUpListaAnotacao("+item.getCodIett()+")\">");
					if(marcador.getCor() != null) { 
						retorno.append("<img src='" + caminhoEcar + "/images/relAcomp/"+marcador.getCor().getNomeCor().toLowerCase()+"_inf.png' border='0' alt=\"\">");
					} else {
						retorno.append("<img src='" + caminhoEcar + "/images/relAcomp/branco_inf.png' border='0' alt=\"\">");
					}
					retorno.append("</a>");
				}
			} catch(ECARException ecarex) {
				Logger.getLogger(this.getClass()).error(ecarex);
				ecarex.printStackTrace();
			}
		}
		retorno.append("</td>");
		return retorno;
    }
    
    /*
     * Método que imprime a segunda coluna da linha do item: coluna da identação pelo nivel 
     */
    private StringBuffer imprimeColunaIdentacaoNivel(ItemEstruturaIett pItemEstrutura, 
    		String linkComeco, String linkFim) {
    	
    	StringBuffer retorno = new StringBuffer();
    	ItemEstruturaIett item = pItemEstrutura;
    	
    	retorno.append("<td>");
    	retorno.append("<table>");
    	retorno.append("<tr bgcolor=\""+item.getEstruturaEtt().getCodCor3Ett()+"\">");
    	retorno.append("<td nowrap>&nbsp;");
		int nivel = item.getNivelIett().intValue();
		for(int i = 1; i < nivel;i++) {
			retorno.append("<img src=\""+getRequest().getContextPath()+"/images/pixel.gif\" width=\"22\" height=\"9\" alt=\"\">");
		}
		retorno.append("</td>");
		
		// Verificar se a imagem é de + ou -							
		String caminhoImagem = "";
		if(linkFim.equals("")) {
			//Quando nao puder expandir o item, a imagem mostrada sera a de -
			caminhoImagem = "/images/expanded_button.gif";
		} else {
			//Quando puder expandir o item, a imagem mostrada sera a de +
			caminhoImagem = "/images/collapsed_button.gif";
		}
		
       //retorno.append("<td valign=\"top\">"+linkComeco+"<img src=\""+getPathEcar()+"/images/icon_bullet_seta.png\" alt=\"\" border=\"0\">"+linkFim+"</td>");
		retorno.append("<td valign=\"top\">"+linkComeco+"<img src=\""+getPathEcar()+caminhoImagem+ "\" alt=\"\" border=\"0\">"+linkFim+"</td>");
		retorno.append("<td title=\""+item.getEstruturaEtt().getNomeEtt().trim()+"\">");
		retorno.append("<td colspan=\"3\">"+item.getNomeIett()+"</td>");
		retorno.append("</font>");
		retorno.append("</td>");
		retorno.append("</tr>");
		retorno.append("</table>");
		retorno.append("</td>");
		
    	return retorno;
    }
    
    /*
     * Método que imprime a terceira coluna da linha do item: imagem nivel planejamento 
     */
    private StringBuffer imprimeColunaImagemNivel(ItemEstruturaIett pItemEstrutura) {
    	StringBuffer retorno = new StringBuffer();
    	ItemEstruturaDao itemDao = new ItemEstruturaDao(getRequest());
    	
    	//imagem nivel planejamento
    	retorno.append("<td nowrap>");
    	Iterator<NomeImgsNivelPlanejamentoBean> itNiveis = itemDao.getNomeImgsNivelPlanejamentoItemAgrupado(pItemEstrutura).iterator();
		while(itNiveis.hasNext()){
			NomeImgsNivelPlanejamentoBean imagemNivelPlanejamento = (NomeImgsNivelPlanejamentoBean) itNiveis.next();
			retorno.append(imagemNivelPlanejamento.geraHtmlImg(getRequest()));
		}
		retorno.append("</td>");

    	return retorno;
    }
    
    /*
     * Método que imprime a quarta coluna da linha do item: nome do Órgao Responsável 
     */
    private StringBuffer imprimeColunaOrgaoResponsavel(ItemEstruturaIett pItemEstrutura) {
    	StringBuffer retorno = new StringBuffer();
    	ItemEstruturaIett item = pItemEstrutura;
    	
    	retorno.append("<td align=\"center\">");
		if(item.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
			if(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() != null)
				retorno.append(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());	
			else
				retorno.append(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());	
		} else {
			//Se não possuir orgao procura orgao do pai
			ItemEstruturaIett itemAux = item;
			while(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() == null && itemAux.getItemEstruturaIett() != null){
				itemAux = itemAux.getItemEstruturaIett();
			}
			if(itemAux != null && itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null){
				if(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg() != null)
					retorno.append(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getSiglaOrg());	
				else
					retorno.append(itemAux.getOrgaoOrgByCodOrgaoResponsavel1Iett().getDescricaoOrg());											
			}
		}
		retorno.append("</td>");
    	
    	return retorno;
    }
    
    /*
     * Método que imprime a quinta coluna da linha do item: imagem inserir anotação 
     */
    private StringBuffer imprimeColunaImagemAnotacao(ItemEstruturaIett pItemEstrutura) {
    	StringBuffer retorno = new StringBuffer();

		//criação do link para detalhamento do item 
		String linkAbrirIettComeco = "<a href=\"javascript:detalharItemEstrutura(form, '" + pItemEstrutura.getCodIett() + "', '" + status + "')\" title=\"Ação\">";
    	
		//imagem inserir anotação
		retorno.append("<td>");
		if(!getStatus().equals("true")){
			retorno.append("<a href=\"javascript:abrirPopUpInserirAnotacao("+pItemEstrutura.getCodIett()+")\" title=\"Inserir anotação\"> ");
			retorno.append("<img src=\""+getPathEcar()+"/images/relAcomp/icon_anotacoes.png\" border=\"0\" alt=\"\"> </a>");
		}
		retorno.append("</td>");
	
		// ADICIONAR A IMAGEM PARA EDITAR				
		usuarioLogadoEmiteParecer = false;		
		boolean permissaoLapis = false;
		
		if(permissaoAdministradorAcompanhamento){
			permissaoLapis = true;
		}																				
		
		TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao();
		ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(request);
		AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
		//List tpfaOrdenadosPorEstrutura = null;
		Map  mapAcao = null;
		Iterator itPeriodosAcao = periodosConsiderados.iterator();		

		try {
			//tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
			mapAcao = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsiderados, itemEstrutura);
		} catch(ECARException ecarex) {
			Logger.getLogger(this.getClass()).error(ecarex);
			ecarex.printStackTrace();
		}

		if(itPeriodosAcao.hasNext()) {
			AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodosAcao.next();
			if(!mapAcao.isEmpty() && mapAcao.containsKey(acompReferencia)) {
				AcompReferenciaItemAri ariAcao = (AcompReferenciaItemAri) mapAcao.get(acompReferencia);
				List relatorios = null;
				try{
					relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ariAcao, tpfaOrdenadosPorEstrutura);
				} catch(ECARException ecarex) {
					Logger.getLogger(this.getClass()).error(ecarex);
					ecarex.printStackTrace();
				}
				Iterator itRelatorios = relatorios.iterator();
				
				while(itRelatorios.hasNext()){												
					AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();										
					ItemEstUsutpfuacIettutfa itemEstUsu = null;
					try{
						itemEstUsu = itemEstUsuDao.buscar(itemEstrutura.getCodIett(), relatorio.getTipoFuncAcompTpfa().getCodTpfa());
					} catch(ECARException ecarex) {
						Logger.getLogger(this.getClass()).error(ecarex);
						ecarex.printStackTrace();
					}
					 
					//Verifica se a permissão é de grupo ou usuário							 							
					if (itemEstUsu.getUsuarioUsu() != null) {
						usuarioLogadoEmiteParecer = itemEstUsu.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
					} else if (itemEstUsu.getSisAtributoSatb() != null) {
						if (itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
							Iterator itUsuarios = itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
							while (itUsuarios.hasNext()) {
								UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
								if (usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
									usuarioLogadoEmiteParecer = true;
									break;
								}
							}
						}
					}
					if(usuarioLogadoEmiteParecer==true)
						break;											
				}//fecha while relatórios																
			}
		}
														
		retorno.append("<td align=\"center\">");
		
		AcompReferenciaAref arefSelecionada = null;   
		AcompReferenciaItemAri acompAri = null;

		try {
			arefSelecionada = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(getCodAref()));   
			acompAri = acompReferenciaItemDao.getAcompReferenciaItemByItemEstruturaIett(arefSelecionada,pItemEstrutura);
		} catch(ECARException ecarex) {
			Logger.getLogger(this.getClass()).error(ecarex);
			ecarex.printStackTrace();
		}
		
		if((usuarioLogadoEmiteParecer || permissaoLapis) && acompAri != null) {
			retorno.append(linkAbrirIettComeco+"<img src=\""+getPathEcar()+"/images/icon_alterar.png\" border=\"0\" alt=\"\"></a>");
		}
		retorno.append("</td>");
    	return retorno;
    }
    
    /*
     * Método que imprime a sexta coluna da linha do item: pareceres 
     */
    private StringBuffer imprimeColunaPareceres(ItemEstruturaIett pItemEstrutura) {
    	StringBuffer retorno = new StringBuffer();
    	ItemEstruturaIett item = pItemEstrutura;
    	Iterator<AcompReferenciaAref> itPeriodos = getPeriodosConsiderados().iterator();
    	
    	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(getRequest());
    	CorDao corDao = new CorDao(getRequest());
    	
    	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(getRequest());
    	try {
	    	//List tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();
			
	    	Map  map = acompReferenciaItemDao.criarMapPeriodoAri(getPeriodosConsiderados(), item);
			
			while(itPeriodos.hasNext()) { 
				AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodos.next();
				if(map.isEmpty()) {
					retorno.append("<td align=\"center\">&nbsp;</td>");
				} else if(!map.containsKey(acompReferencia)) {
					retorno.append("<td align=\"center\" valign=\"middle\">");
					retorno.append("<p title=\"Não foi solicitado acompanhamento\">N/A</p>");
					retorno.append("</td>");
				} else {
					AcompReferenciaItemAri ari = (AcompReferenciaItemAri) map.get(acompReferencia);										
					retorno.append("<td align=\"center\" nowrap>");
					if((Dominios.NAO).equals(getExigeLiberarAcompanhamento()) || ari.getStatusRelatorioSrl().equals(getStatusLiberado())) {
						if(!"true".equals(status)){
							retorno.append("<a name=\"ancora"+ari.getItemEstruturaIett().getCodIett()+"\" href=\"#\" onclick=\"javascript:aoClicarConsultar(form, "+ari.getCodAri()+", "+ ari.getItemEstruturaIett().getCodIett()+ ")\">");							
							itensSessaoVisualizar.add(ari.getCodAri().toString());
							request.setAttribute("itensSessaoVisualizar",itensSessaoVisualizar );							
							if(usuarioLogadoEmiteParecer || permissaoAdministradorAcompanhamento) {
								itensSessao.add(ari.getCodAri().toString());
								request.setAttribute("itensSessao",itensSessao );
							}
						}
						List<AcompRelatorioArel> relatorios = acompReferenciaItemDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari, tpfaOrdenadosPorEstrutura);
						Iterator<AcompRelatorioArel> itRelatorios = relatorios.iterator();
	
						String imagePath = "";
						String aval = "";
						while(itRelatorios.hasNext()){												
							AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();																																													
							boolean imageError = false;
							if( (Dominios.SIM).equals(relatorio.getIndLiberadoArel()) ) {
								Cor cor = ( relatorio.getCor()!=null ? relatorio.getCor() : null );
								TipoFuncAcompTpfa tpfa = ( relatorio.getTipoFuncAcompTpfa() != null ? relatorio.getTipoFuncAcompTpfa() : null );
																						
								imagePath = corDao.getImagemPersonalizada(cor, tpfa, "L");
								
								if( imagePath != null ) {
								    aval += "<img border=\"0\" src=\"" + request.getContextPath() + "/DownloadFile?tipo=open&RemoteFile=";
								    aval += imagePath + "\" style=\"width: 23px; height: 23px;\" title=\"" + relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\">";
								} else {
									if( relatorio.getCor() != null && relatorio.getCor().getCodCor() != null ) { 
										aval += "<img border=\"0\" src=\"" + getPathEcar() + "/images/relAcomp/";
										aval += corDao.getImagemRelatorio(relatorio.getCor(), relatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
										aval += relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
									} else {
										imageError = true;
									}
								}
							} else {
								imageError = true;
							}
																					
							// Verifica se o parecer é obrigatorio ou opcional
							List<TipoAcompFuncAcompTafc> listTipoAcompFuncAcomp = new ArrayList<TipoAcompFuncAcompTafc>(acompReferencia.getTipoAcompanhamentoTa().getTipoAcompFuncAcompTafcs());
							boolean ehObrigatorio = true;
							if(listTipoAcompFuncAcomp != null) {
								Iterator<TipoAcompFuncAcompTafc> tipoFuncAcompIt = listTipoAcompFuncAcomp.iterator();
								while(tipoFuncAcompIt.hasNext()) {
									TipoAcompFuncAcompTafc tipoAcompFuncAcompTafc = (TipoAcompFuncAcompTafc)tipoFuncAcompIt.next();
									if(	relatorio.getTipoFuncAcompTpfa().getCodTpfa().equals(tipoAcompFuncAcompTafc.getTipoFuncAcompTpfa().getCodTpfa())
										&& tipoAcompFuncAcompTafc.getIndRegistroPosicaoTafc() != null 
										&& tipoAcompFuncAcompTafc.getIndRegistroPosicaoTafc().equals(Dominios.OPCIONAL)) {
										ehObrigatorio = false;
									}
								}
							}
							
							if( imageError && ehObrigatorio) {
								aval += "<img border=\"0\" src=\"" + getPathEcar() + "/images/relAcomp/";
								aval += corDao.getImagemRelatorio(null, relatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
								aval += relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
							}
						}
						
						retorno.append(aval);
																			
						if(!"true".equals(status)){
							retorno.append("</a>");
						}
					} else {
						if((Dominios.NAO).equals(getExigeLiberarAcompanhamento()) || (ari.getAcompRelatorioArels() != null && ari.getAcompRelatorioArels().size() > 0)) {
							if(!"true".equals(status)){
								retorno.append("<a name=\"ancora<%=ari.getItemEstruturaIett().getCodIett()%>\" href=\"#\" onclick=\"javascript:aoClicarConsultar(form, "+ari.getCodAri()+", "+ ari.getItemEstruturaIett().getCodIett()+ ")\">");								
								itensSessaoVisualizar.add(ari.getCodAri().toString());
								request.setAttribute("itensSessaoVisualizar",itensSessaoVisualizar );							
								if(usuarioLogadoEmiteParecer || permissaoAdministradorAcompanhamento) {
									itensSessao.add(ari.getCodAri().toString());
									request.setAttribute("itensSessao",itensSessao );
								}
							}  
							retorno.append("<p title=\"Não liberado\">N/L</p>"); 
							if(!"true".equals(status)){ 
								retorno.append("</a>");
							}
						} else {
							retorno.append("&nbsp;");
						}
					}
					retorno.append("</td>");
				}
			}
    	} catch(ECARException ecarEx) {
    		Logger.getLogger(this.getClass()).error(ecarEx);
			ecarEx.printStackTrace();
    	}
    	return retorno;
    }
    
    /**
     * Encerra Tag.<br>
     * 
	 * @return int
	 * @throws JspException
     */
    @Override
    public int doEndTag() throws JspException {
        /* processa o restante da página jsp */
        return Tag.EVAL_PAGE;
    }
	
    /**
     *
     * @return
     */
    public String getCodAref() {
		return codAref;
	}

        /**
         *
         * @param codAref
         */
        public void setCodAref(String codAref) {
		this.codAref = codAref;
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
        public UsuarioUsu getUsuario() {
		return usuario;
	}
        /**
         *
         * @param usuario
         */
        public void setUsuario(UsuarioUsu usuario) {
		this.usuario = usuario;
	}
	
        /**
         *
         * @return
         */
        public StatusRelatorioSrl getStatusLiberado() {
		return statusLiberado;
	}

        /**
         *
         * @param statusLiberado
         */
        public void setStatusLiberado(StatusRelatorioSrl statusLiberado) {
		this.statusLiberado = statusLiberado;
	}

        /**
         *
         * @return
         */
        public List<ItemEstruturaIett> getColecaoItens() {
		return colecaoItens;
	}

        /**
         *
         * @param colecaoItens
         */
        public void setColecaoItens(List<ItemEstruturaIett> colecaoItens) {
		this.colecaoItens = colecaoItens;
	}

        /**
         *
         * @return
         */
        public Collection<AcompReferenciaAref> getPeriodosConsiderados() {
		return periodosConsiderados;
	}

        /**
         *
         * @param periodosConsiderados
         */
        public void setPeriodosConsiderados(Collection<AcompReferenciaAref> periodosConsiderados) {
		this.periodosConsiderados = periodosConsiderados;
	}

        /**
         *
         * @return
         */
        public String getStatus() {
		return status;
	}
        /**
         *
         * @param status
         */
        public void setStatus(String status) {
		this.status = status;
	}
	
        /**
         *
         * @return
         */
        public String getIdPagina() {
		return idPagina;
	}

        /**
         *
         * @param idPagina
         */
        public void setIdPagina(String idPagina) {
		this.idPagina = idPagina;
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
        public String getCaminhoUrl() {
		return caminhoUrl;
	}
        /**
         *
         * @param caminhoUrl
         */
        public void setCaminhoUrl(String caminhoUrl) {
		this.caminhoUrl = caminhoUrl;
	}
	
        /**
         *
         * @return
         */
        public String getCodTipoAcompanhamento() {
		return codTipoAcompanhamento;
	}
        /**
         *
         * @param codTipoAcompanhamento
         */
        public void setCodTipoAcompanhamento(String codTipoAcompanhamento) {
		this.codTipoAcompanhamento = codTipoAcompanhamento;
	}
	
        /**
         *
         * @return
         */
        public String getExigeLiberarAcompanhamento() {
		return exigeLiberarAcompanhamento;
	}

        /**
         *
         * @param exigeLiberarAcompanhamento
         */
        public void setExigeLiberarAcompanhamento(String exigeLiberarAcompanhamento) {
		this.exigeLiberarAcompanhamento = exigeLiberarAcompanhamento;
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
        public List getTpfaOrdenadosPorEstrutura() {
		return tpfaOrdenadosPorEstrutura;
	}

        /**
         *
         * @param tpfaOrdenadosPorEstrutura
         */
        public void setTpfaOrdenadosPorEstrutura(List tpfaOrdenadosPorEstrutura) {
		this.tpfaOrdenadosPorEstrutura = tpfaOrdenadosPorEstrutura;
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
        public boolean isPermissaoAdministradorAcompanhamento() {
		return permissaoAdministradorAcompanhamento;
	}

        /**
         *
         * @param permissaoAdministradorAcompanhamento
         */
        public void setPermissaoAdministradorAcompanhamento(
			boolean permissaoAdministradorAcompanhamento) {
		this.permissaoAdministradorAcompanhamento = permissaoAdministradorAcompanhamento;
	}
	
}
