package ecar.servlet.geraFilhosIett;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import comum.util.ConstantesECAR;
import comum.util.Util;

import ecar.bean.AtributoEstruturaListagemItens;
import ecar.bean.NomeImgsNivelPlanejamentoBean;
import ecar.dao.AbaDao;
import ecar.dao.AcompReferenciaDao;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.ConfiguracaoDao;
import ecar.dao.CorDao;
import ecar.dao.ItemEstUsutpfuacDao;
import ecar.dao.ItemEstrutMarcadorDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.OrgaoDao;
import ecar.dao.TipoAcompanhamentoDao;
import ecar.dao.TipoFuncAcompDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.AcompReferenciaItemAri;
import ecar.pojo.AcompRelatorioArel;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.Cor;
import ecar.pojo.ItemEstUsutpfuacIettutfa;
import ecar.pojo.ItemEstrutMarcadorIettm;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.StatusRelatorioSrl;
import ecar.pojo.TipoAcompFuncAcompTafc;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.TipoFuncAcompTpfa;
import ecar.pojo.UsuarioAtributoUsua;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * @author José André e Bárbara Siqueira
 * @since 08/02/2008
 */

public class ItemEstruturaHtml {
	
	private ItemEstruturaIett itemEstrutura;
	private UsuarioUsu usuario;
	private StatusRelatorioSrl statusLiberado;
	private Collection<AcompReferenciaAref> periodosConsiderados;
	
	
	private List tpfaOrdenadosPorEstrutura;
	
	private String status;
	private String idPagina;
	private String pathEcar;
	private String caminhoUrl;
	private String codTipoAcompanhamento;
	private String exigeLiberarAcompanhamento;
	private String codAref;
	private String codArefSelecionado;
	
		
	private HttpServletRequest request;
	private SegurancaECAR seguranca;
	private boolean permissaoAdministradorAcompanhamento;
	
	private List itensSessao;
	private List itensSessaoVisualizar;
	private boolean usuarioLogadoEmiteParecer;
	private AtributoEstruturaListagemItens atEstListagem;
	
	private String nomeOrgaoSepararPorOrgao;
	private boolean itemEstruturaPossuiFilho;
	private String periodo;
	private boolean temPermissaAcessarAbaVisualizacao;
	private String enderecoAbaRegistro;
	private String nomeAbaVisualizacao;
	
	/**
	 * 
	 * @param itemEstrutura
         * @param atEstListagem
         * @param usuario
	 * @param statusLiberado
	 * @param periodosConsiderados
	 * @param tpfaOrdenadosPorEstrutura
	 * @param status
	 * @param idPagina
	 * @param pathEcar
	 * @param caminhoUrl
	 * @param codTipoAcompanhamento
	 * @param exigeLiberarAcompanhamento
	 * @param codAref
	 * @param request
         * @param permissaoAdministradorAcompanhamento
         * @param temPermissaAcessarAba
         * @param itemEstruturaPossuiFilho
         * @param enderecoAbaRegistro
         * @param periodo
         * @param nomeAbaVisualizacao
	 */	
	public ItemEstruturaHtml(ItemEstruturaIett itemEstrutura,
			AtributoEstruturaListagemItens atEstListagem,
			UsuarioUsu usuario, StatusRelatorioSrl statusLiberado,
			Collection<AcompReferenciaAref> periodosConsiderados,
			List tpfaOrdenadosPorEstrutura, String status, String idPagina,
			String pathEcar, String caminhoUrl, String codTipoAcompanhamento,
			String exigeLiberarAcompanhamento, String codAref, String codArefSelecionado,
			HttpServletRequest request,
			boolean permissaoAdministradorAcompanhamento,
			boolean itemEstruturaPossuiFilho,
			String enderecoAbaRegistro,String periodo, boolean temPermissaAcessarAba,
			String nomeAbaVisualizacao) {		
		this.itemEstrutura = itemEstrutura;
		this.atEstListagem = atEstListagem;
		this.usuario = usuario;
		this.statusLiberado = statusLiberado;
		this.periodosConsiderados = periodosConsiderados;
		this.tpfaOrdenadosPorEstrutura = tpfaOrdenadosPorEstrutura;
		this.status = status;
		this.idPagina = idPagina;
		this.pathEcar = pathEcar;
		this.caminhoUrl = caminhoUrl;
		this.codTipoAcompanhamento = codTipoAcompanhamento;
		this.exigeLiberarAcompanhamento = exigeLiberarAcompanhamento;
		this.codAref = codAref;
		this.codArefSelecionado = codArefSelecionado;
		this.request = request;
		this.permissaoAdministradorAcompanhamento = permissaoAdministradorAcompanhamento;
		this.itemEstruturaPossuiFilho = itemEstruturaPossuiFilho;
		this.enderecoAbaRegistro = enderecoAbaRegistro;
		this.periodo = periodo;
		this.temPermissaAcessarAbaVisualizacao = temPermissaAcessarAba;
		this.nomeAbaVisualizacao = nomeAbaVisualizacao;
	}

	/**
	 * Gera a linha da tabela em html do ItemEstrutura 
         *
         * @return
         */
    public String geraHtmlDoItem() {
    	   	
    	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao();
    	StringBuffer s = new StringBuffer();
    	if(!request.getParameter("codOrg").equals(""))
    		nomeOrgaoSepararPorOrgao = "_org" + request.getParameter("codOrg");
    	else
    		nomeOrgaoSepararPorOrgao = "";
    	
    	try {        	       	        	        	        	                    
            //tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas();   
    		
        	s = imprimeColunasDoItem(getItemEstrutura());		
        } catch (Exception e) {
        	Logger.getLogger(this.getClass()).error(e);
        	e.printStackTrace();
        }
        
        return s.toString();
    }
    
    /**
     * 
     * @param pItemEstrutura
     * @return
     */
    private StringBuffer imprimeColunasDoItem(ItemEstruturaIett pItemEstrutura) throws ECARException{
    	StringBuffer retorno = new StringBuffer();
    	String linkAbrirArvoreIettComeco = "";
    	String linkAbrirArvoreIettFim = "";
    	itensSessao = new ArrayList();
    	itensSessaoVisualizar = new ArrayList();

    	if(request.getAttribute("itensSessao")!=null)
    		itensSessao = (List)request.getAttribute("itensSessao");
    	if(request.getAttribute("itensSessaoVisualizar")!=null)
    		itensSessaoVisualizar = (List)request.getAttribute("itensSessaoVisualizar");
    	
    	if(itemEstruturaPossuiFilho){
    		linkAbrirArvoreIettComeco = "<a href=\"javascript:carregarFilhosNo('" + pItemEstrutura.getCodIett()+ "','" 
				+ request.getParameter("codOrg") + "','"
				+ this.enderecoAbaRegistro + "','"
				+ periodo +"','"
				+ this.codAref +"',"
				+ this.temPermissaAcessarAbaVisualizacao +",'" 
				+ nomeAbaVisualizacao 
				+"')\">";
    		linkAbrirArvoreIettFim = "</a>";	
    	}

    	retorno.append("*@*");
    	retorno.append(pItemEstrutura.getCodIett());
    	retorno.append("*@*");
    	retorno.append(imprimeColunaDoMarcador(pItemEstrutura));
    	retorno.append("*@*");
    	retorno.append(imprimeColunaIdentacaoNivel(pItemEstrutura, linkAbrirArvoreIettComeco, linkAbrirArvoreIettFim));
    	retorno.append("*@*");
    	retorno.append(imprimeColunaImagemNivel(pItemEstrutura));
    	retorno.append("*@*");
    	retorno.append(imprimeColunaOrgaoResponsavel(pItemEstrutura));
    	retorno.append("*@*");
    	retorno.append(imprimeColunaImagemAnotacao(pItemEstrutura));
    	retorno.append("*@*");
    	retorno.append(imprimeColunaPareceres(pItemEstrutura));
    	retorno.append(imprimeColunaCheckBox(pItemEstrutura));
    	

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
    	
    	String linkAbrirIettComeco = "";
    	String linkAbrirIettFim = "";
    	AcompReferenciaAref acompReferenciaAref = null;    	
    	AcompReferenciaItemAri ari = null;
    	AbaDao abaDao = new AbaDao(request);
    	SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
    	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
    	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
    	
    	StringBuffer retorno = new StringBuffer();
    	ItemEstruturaIett item = pItemEstrutura;
    	
    	retorno.append("<td>");
    	retorno.append("<table>");
    	retorno.append("<tr>");
    	retorno.append("<td nowrap>&nbsp;");
		int nivel = item.getNivelIett().intValue();
		for(int i = 1; i < nivel;i++) {
			retorno.append("<img src=\""+getRequest().getContextPath()+"/images/pixel.gif\" width=\"22\" height=\"9\" alt=\"\">");
		}
		retorno.append("</td>");
		
		// Verificar se a imagem é de mais ou branco							
		String caminhoImagem = "";
		if(linkFim.equals("")) {
			//Quando nao puder expandir o item, a imagem mostrada sera um quadrado em branco
			caminhoImagem = "/images/square.gif";
		} else {
			//Quando puder expandir o item, a imagem mostrada sera a de +
			caminhoImagem = "/images/collapsed_button.gif";
		}
		try{			
			if(codTipoAcompanhamento  != null && !codTipoAcompanhamento.equals("") && codAref != null && !codAref.equals("")) {
								
				
				
				acompReferenciaAref =  (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(codAref));
		      
				if(acompReferenciaAref.getTipoAcompanhamentoTa() != null) {
					TipoAcompanhamentoTa tipoAcompanhamento = acompReferenciaAref.getTipoAcompanhamentoTa();
					String enderecoAbaVisualizacao = abaDao.pesquisaEnderecoAbaRegistro(tipoAcompanhamento, seguranca.getGruposAcesso(), 2, null);
					
					ari = (AcompReferenciaItemAri) acompReferenciaItemDao.getAcompReferenciaItemByItemEstruturaIett(acompReferenciaAref, pItemEstrutura);
		    			    	
					//Ajustar link...
					/*linkAbrirIettComeco = "<a href=\"javascript:detalharItemEstrutura(form, '" 
																	+ status
																	+ "', '" + ari.getCodAri().toString()
																	+ "', '" + this.periodo 
																	+ "', '" + this.codTipoAcompanhamento  
																	+ "', '" + this.codAref 
																	+ "', '" + enderecoAbaRegistro + "')\" title=\"Ação\">";
					*/
					
					if((Dominios.NAO).equals(getExigeLiberarAcompanhamento()) || ari.getStatusRelatorioSrl().equals(getStatusLiberado())) {
						if(!"true".equals(status)){
							linkAbrirIettComeco = "<a name=\"ancora"+ari.getItemEstruturaIett().getCodIett()+"\" href=\"#\" " +
									"onclick=\"javascript:aoClicarConsultarExibicaoAba(form, '" + enderecoAbaVisualizacao + "', "+
									+ari.getCodAri()+ ", "+ ari.getItemEstruturaIett().getCodIett()+ ", "+ this.temPermissaAcessarAbaVisualizacao
									+ ",'" + this.nomeAbaVisualizacao + "')\">";						

							itensSessaoVisualizar.add(ari.getCodAri().toString());
							request.setAttribute("itensSessaoVisualizar",itensSessaoVisualizar );							
							if(usuarioLogadoEmiteParecer || permissaoAdministradorAcompanhamento) {
								itensSessao.add(ari.getCodAri().toString());
								request.setAttribute("itensSessao",itensSessao );
							}
						}
					} else {
						if((Dominios.NAO).equals(getExigeLiberarAcompanhamento()) || (ari.getAcompRelatorioArels() != null && ari.getAcompRelatorioArels().size() > 0)) {
							if(!"true".equals(status)){
								linkAbrirIettComeco = "<a name=\"ancora<%=ari.getItemEstruturaIett().getCodIett()%>\" href=\"#\" + " +
										"onclick=\"javascript:aoClicarConsultarExibicaoAba(form, '"+ enderecoAbaVisualizacao + "', " + ari.getCodAri()+", "
										+ ari.getItemEstruturaIett().getCodIett()+ ", " + this.temPermissaAcessarAbaVisualizacao 
										+ ",'" + this.nomeAbaVisualizacao + "')\">";	
																
								itensSessaoVisualizar.add(ari.getCodAri().toString());
								request.setAttribute("itensSessaoVisualizar",itensSessaoVisualizar );							
								if(usuarioLogadoEmiteParecer || permissaoAdministradorAcompanhamento) {
									itensSessao.add(ari.getCodAri().toString());
									request.setAttribute("itensSessao",itensSessao );
								}
							}							
						}
					}
				}
			}
    	} catch (Exception e) {
          	Logger.getLogger(this.getClass()).error(e);
          	e.printStackTrace();
        }
    	if (linkAbrirIettComeco.length() > 0){
    		linkAbrirIettFim = "</a>";
    	} 

		
		
       //retorno.append("<td valign=\"top\">"+linkComeco+"<img src=\""+getPathEcar()+"/images/icon_bullet_seta.png\" alt=\"\" border=\"0\">"+linkFim+"</td>");
		String nomeIett = "".equals(atEstListagem.getDescricao()) ? "[Não Informado]" : atEstListagem.getDescricao();
		retorno.append("<td id=\"iconeExpandirIett" + item.getCodIett() + nomeOrgaoSepararPorOrgao + "\" valign=\"top\">"
				+linkComeco+"<img id=\"imagemMaisMenosIett" + item.getCodIett() + nomeOrgaoSepararPorOrgao + "\" src=\""+getPathEcar()+caminhoImagem+ "\" alt=\"\" border=\"0\">"+linkFim+"</td>");
		retorno.append("<td>");
		retorno.append("<td title=\""+item.getEstruturaEtt().getNomeEtt().trim()+"\" colspan=\"3\"><font color='"+ item.getEstruturaEtt().getCodCor4Ett() + "'>" + linkAbrirIettComeco + nomeIett + linkAbrirIettFim + "</font></td>");
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
    	
    	retorno.append("<td align=\"center\"><font color='"+ item.getEstruturaEtt().getCodCor4Ett() + "'>");
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
		retorno.append("</font></td>");
    	
    	return retorno;
    }
    
    /*
     * Método que imprime a quinta coluna da linha do item: imagem inserir anotação 
     */
    private StringBuffer imprimeColunaImagemAnotacao(ItemEstruturaIett pItemEstrutura) throws ECARException {
    	StringBuffer retorno = new StringBuffer();
    	String linkAbrirIettComeco = "";
    	OrgaoDao orgaoDao = new OrgaoDao(request);
		TipoAcompanhamentoTa tipoAcompanhamento = null;
		AcompReferenciaItemAri ari = null;
		OrgaoOrg orgao = null;
		AcompReferenciaAref acompReferenciaAref = null;
		AcompReferenciaAref acompReferenciaSelecionado = null;
		    
		TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao();
		ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(request);
		AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
		AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(request);
		TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(getRequest());
		Map  mapAcao = null;
		Iterator itPeriodosAcao = null;
		// guarda se a referencia selecionada é igual a referencia atual. Isso serve pra saber se vai colocar o lapis quando mais de um periodo for selecionado
		boolean referenciaSelecionadaIgualReferenciaItem = true;
    	
    	try{ 

		   
		    
		    if(codTipoAcompanhamento  != null && !codTipoAcompanhamento.equals("") && codAref != null && !codAref.equals("")) {
		      acompReferenciaAref =  (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(codAref));
		      
		      if(acompReferenciaAref.getTipoAcompanhamentoTa() != null) {
		    	  tipoAcompanhamento = acompReferenciaAref.getTipoAcompanhamentoTa();
		    	  ari = (AcompReferenciaItemAri) acompReferenciaItemDao.getAcompReferenciaItemByItemEstruturaIett(acompReferenciaAref, pItemEstrutura);
		    			    	
			 
		    	  linkAbrirIettComeco = "<a href=\"javascript:detalharItemEstrutura(form, '" 
																	+ status
																	+ "', '" + ari.getCodAri().toString()
																	+ "', '" + this.periodo 
																	+ "', '" + this.codTipoAcompanhamento  
																	+ "', '" + this.codAref 
																	+ "', '" + enderecoAbaRegistro + "')\" title=\"Ação\">";
	    	
			
		    }
		 } 
		    
    	} catch (Exception e) {
          	Logger.getLogger(this.getClass()).error(e);
          	e.printStackTrace();
        }
		
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
		
		
				

		try {
		
		
			
			if(tipoAcompanhamento == null)
				tipoAcompanhamento = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
			
			
			//Retorna um AREF na chave do Map e um ARI referente ao item no valor do MAP.
			if(tipoAcompanhamento != null && tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")) {
				Collection listaReferenciasOrgaoAtual = new ArrayList();
				Iterator itPeriodosOrgaoAtual =  periodosConsiderados.iterator();
				if(codArefSelecionado != null && !codArefSelecionado.equals(""))
	        		acompReferenciaSelecionado = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(codArefSelecionado));
			

				if(acompReferenciaAref != null && acompReferenciaSelecionado != null) { 
						if(acompReferenciaAref.getDiaAref().equals(acompReferenciaSelecionado.getDiaAref()) && 
								acompReferenciaAref.getMesAref().equals(acompReferenciaSelecionado.getMesAref()) &&
								acompReferenciaAref.getAnoAref().equals(acompReferenciaSelecionado.getAnoAref())) {
							listaReferenciasOrgaoAtual.add(acompReferenciaAref);
						} else {
							referenciaSelecionadaIgualReferenciaItem = false;
						}
					
				}
				
				listaReferenciasOrgaoAtual.add(acompReferenciaSelecionado);
					//agrupa outros arefs de outros periodos com o mesmo orgao
					while(itPeriodosOrgaoAtual.hasNext() ) {
						AcompReferenciaAref arefPeriodo	= (AcompReferenciaAref) itPeriodosOrgaoAtual.next();
						AcompReferenciaAref refOrgaoAtual = acompReferenciaDao.getAcompReferenciaByOrgaoDiaMesAnoAref(acompReferenciaAref.getOrgaoOrg(), arefPeriodo);
						if(refOrgaoAtual != null && !listaReferenciasOrgaoAtual.contains(refOrgaoAtual) && referenciaSelecionadaIgualReferenciaItem) {
							listaReferenciasOrgaoAtual.add(refOrgaoAtual);
						} 
					}
					
				
				
				mapAcao = acompReferenciaItemDao.criarMapPeriodoAriMontarListagem(periodosConsiderados, listaReferenciasOrgaoAtual, pItemEstrutura);
				
		    } else {
		    	referenciaSelecionadaIgualReferenciaItem = true;
		    	mapAcao = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsiderados, pItemEstrutura);
		    }
			
		
		
		} catch(ECARException ecarex) {
			Logger.getLogger(this.getClass()).error(ecarex);
			ecarex.printStackTrace();
		}
		
		itPeriodosAcao = periodosConsiderados.iterator();

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
						//ecarex.printStackTrace();
					}
					
					//Verifica se a permissão é de grupo ou usuário
					if(itemEstUsu!=null){							 							
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
					}
				}//fecha while relatórios																
			}
		}
														
		retorno.append("*@*");	//Marcador que será utilizado na apresentação dos Itens
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
		
		if((usuarioLogadoEmiteParecer || permissaoLapis) && acompAri != null && referenciaSelecionadaIgualReferenciaItem && !getStatus().equals("true")) {
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
    	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(getRequest());
    	CorDao corDao = new CorDao(getRequest());
    	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(getRequest());
    	AbaDao abaDao = new AbaDao(null);
    	SegurancaECAR seguranca = (SegurancaECAR) request.getSession().getAttribute("seguranca");
    	AcompReferenciaAref acompReferencia = null;
    	AcompReferenciaAref acompReferenciaOrgaoAtual = null;
    	TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao(getRequest());
    	try {

        	TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcompanhamento));
        	String enderecoAbaVisualizacao = abaDao.pesquisaEnderecoAbaRegistro(tipoAcompanhamento, seguranca.getGruposAcesso(), 2, null);
        	ConfiguracaoCfg configuracao = new ConfiguracaoDao(request).getConfiguracao();
			String pathRaiz = configuracao.getRaizUpload();	
			
        	if(codAref != null && !codAref.equals(""))
        		acompReferenciaOrgaoAtual = (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(codAref));
        	
        	
        	
        	
        	Map map = null;
			//map utilizado para recuperar o Ari
			if(tipoAcompanhamento != null && tipoAcompanhamento.getIndSepararOrgaoTa() != null && tipoAcompanhamento.getIndSepararOrgaoTa().equals("S")) {
				
				Collection listaReferenciasOrgaoAtual = new ArrayList();
				
				Iterator itPeriodosOrgaoAtual =  periodosConsiderados.iterator();
				
				if(acompReferenciaOrgaoAtual != null)
					listaReferenciasOrgaoAtual.add(acompReferenciaOrgaoAtual);
				
				//agrupa outros arefs de outros periodos com o mesmo orgao
				while(itPeriodosOrgaoAtual.hasNext()) {
					AcompReferenciaAref arefPeriodo	= (AcompReferenciaAref) itPeriodosOrgaoAtual.next();
					AcompReferenciaAref refOrgaoAtual = acompReferenciaDao.getAcompReferenciaByOrgaoDiaMesAnoAref(acompReferenciaOrgaoAtual.getOrgaoOrg(), arefPeriodo);
					if(refOrgaoAtual != null && !listaReferenciasOrgaoAtual.contains(refOrgaoAtual)) {
						listaReferenciasOrgaoAtual.add(refOrgaoAtual);
					} 
				}
				map = acompReferenciaItemDao.criarMapPeriodoAriMontarListagem(periodosConsiderados, listaReferenciasOrgaoAtual,  item);
		    } else {
		    	map = acompReferenciaItemDao.criarMapPeriodoAri(periodosConsiderados, item);
		    }
			
    	    
			while(itPeriodos.hasNext()) { 
				acompReferencia = (AcompReferenciaAref) itPeriodos.next();
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
													retorno.append("<a name=\"ancora"+ari.getItemEstruturaIett().getCodIett()+"\" href=\"#\" " +
									"onclick=\"javascript:aoClicarConsultarExibicaoAba(form, '" + enderecoAbaVisualizacao + "', "+
									+ari.getCodAri()+ ", "+ ari.getItemEstruturaIett().getCodIett()+ ", "+ this.temPermissaAcessarAbaVisualizacao
									+ ",'" + this.nomeAbaVisualizacao + "')\">");							
							
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
						
						ValidaPermissao validaPermissao = new ValidaPermissao();
						
						List listaPermissaoTpfa = validaPermissao.permissaoVisualizarPareceres(tipoAcompanhamento,seguranca.getGruposAcesso());
						
						while(itRelatorios.hasNext()){												
							AcompRelatorioArel relatorio = (AcompRelatorioArel) itRelatorios.next();
							
							if(listaPermissaoTpfa.contains(relatorio.getTipoFuncAcompTpfa())
									&& validaPermissao.permissaoLeituraAcompanhamento(relatorio.getAcompReferenciaItemAri(), seguranca.getUsuario(), seguranca.getGruposAcesso())){
								
								UsuarioUsu usuarioImagem = null;  
			            		String hashNomeArquivo = null;
								
								boolean imageError = false;
								if( (Dominios.SIM).equals(relatorio.getIndLiberadoArel()) ) {
									Cor cor = ( relatorio.getCor()!=null ? relatorio.getCor() : null );
									TipoFuncAcompTpfa tpfa = ( relatorio.getTipoFuncAcompTpfa() != null ? relatorio.getTipoFuncAcompTpfa() : null );
																							
									imagePath = corDao.getImagemPersonalizada(cor, tpfa, "L");
									
									if( imagePath != null ) {
										
										usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
				    					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagePath);
				    					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagePath);
										
									    aval += "<img border=\"0\" src=\"" + request.getContextPath() + "/DownloadFile?tipo=open&RemoteFile=";
									    aval += hashNomeArquivo + "\" style=\"width: 23px; height: 23px;\" title=\"" + relatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\">";
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
						}
						
						retorno.append(aval);
																			
						if(!"true".equals(status)){
							retorno.append("</a>");
						}
					} else {
						if((Dominios.NAO).equals(getExigeLiberarAcompanhamento()) || (ari.getAcompRelatorioArels() != null && ari.getAcompRelatorioArels().size() > 0)) {
							if(!"true".equals(status)){
								retorno.append("<a name=\"ancora<%=ari.getItemEstruturaIett().getCodIett()%>\" href=\"#\" + " +
										"onclick=\"javascript:aoClicarConsultarExibicaoAba(form, '"+ enderecoAbaVisualizacao + "', " + ari.getCodAri()+", "
										+ ari.getItemEstruturaIett().getCodIett()+ ", " + this.temPermissaAcessarAbaVisualizacao 
										+ ",'" + this.nomeAbaVisualizacao + "')\">");	
																
								itensSessaoVisualizar.add(ari.getCodAri().toString());
								request.setAttribute("itensSessaoVisualizar",itensSessaoVisualizar );							
								if(usuarioLogadoEmiteParecer || permissaoAdministradorAcompanhamento) {
									itensSessao.add(ari.getCodAri().toString());
									request.setAttribute("itensSessao",itensSessao );
								}
							}  
							retorno.append("<p title=\"Não liberado\"><font color='"+ pItemEstrutura.getEstruturaEtt().getCodCor4Ett() + "'>" + "N/L"+ "</font></p>"); 
							if(!"true".equals(status)){ 
								retorno.append("</a>");
							}
						} else {
							retorno.append("&nbsp;");
						}
					}
					retorno.append("</td>");
				}
				
				
				
			
				
				//Incluir o separador no caso de existir mais de um periodo a exibir
				if(itPeriodos.hasNext())
					retorno.append("*@*");
			}
		
    	    				
    	} catch(ECARException ecarEx) {
    		Logger.getLogger(this.getClass()).error(ecarEx);
			ecarEx.printStackTrace();
    	}
    	catch(Exception e) {
			Logger.getLogger(this.getClass()).error(e);
			e.printStackTrace();
    	}
    	
    	return retorno;
    }
    
    
    
    /*
     * Método que imprime a coluna de checkbox do item se for a tela de relatorio ou situacao datas 
     */
    private StringBuffer imprimeColunaCheckBox(ItemEstruturaIett pItemEstrutura) {
    	StringBuffer retorno = new StringBuffer();
    	ItemEstruturaIett item = pItemEstrutura;
    	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(getRequest());
    	TipoAcompanhamentoTa tipoAcomp = null; 
    	AcompReferenciaDao acompReferenciaDao = new AcompReferenciaDao(getRequest());
    	AcompReferenciaAref acompReferenciaAref = null;
    	String nomeReferencia,idReferencia = "";
    	try {
	    	
    		nomeReferencia = item.getCodIett().toString();
    		
	    	if(codAref != null && !codAref.equals("")) {
	    		 acompReferenciaAref =  (AcompReferenciaAref) acompReferenciaDao.buscar(AcompReferenciaAref.class, Long.valueOf(codAref));
	    		 if(acompReferenciaAref.getTipoAcompanhamentoTa() != null) {
	    			 tipoAcomp = acompReferenciaAref.getTipoAcompanhamentoTa();
	    			 if(tipoAcomp.getIndSepararOrgaoTa() != null && tipoAcomp.getIndSepararOrgaoTa().equals("S")) {
	    				 	if(acompReferenciaAref.getOrgaoOrg() != null) { 
	    				 		nomeReferencia = nomeReferencia + "_org" + acompReferenciaAref.getOrgaoOrg().getCodOrg().toString();
	    				 		idReferencia = acompReferenciaAref.getOrgaoOrg().getCodOrg().toString();
	    				 	} else { 
	    				 		nomeReferencia = nomeReferencia + "_orgX";
	    				 		idReferencia = ConstantesECAR.ZERO;
	    				 	}
	    			 }	 	
	    		 }	 
	    	} 
	    	if(getStatus().equals("true")){
	    		retorno.append("*@*");
				retorno.append("<td>");
				retorno.append("<input type=\"checkbox\" class=\"form_check_radio\"");
				retorno.append(" id=\""+idReferencia+"\" value=\""+ nomeReferencia + "\">");
				retorno.append("</td>");
			}
    	
    	
		} catch(ECARException ecarEx) {
			Logger.getLogger(this.getClass()).error(ecarEx);
			ecarEx.printStackTrace();
		}
	   
    	return retorno;
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
   public String getCodArefSelecionado() {
		return codArefSelecionado;
	}

   /**
    *
    * @param codAref
    */
   public void setCodArefSelecionado(String codArefSelecionado) {
		this.codArefSelecionado = codArefSelecionado;
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
        public void setPermissaoAdministradorAcompanhamento(boolean permissaoAdministradorAcompanhamento) {
		this.permissaoAdministradorAcompanhamento = permissaoAdministradorAcompanhamento;
	}
	
        /**
         *
         * @return
         */
        public String getEnderecoAbaRegistro() {
		return enderecoAbaRegistro;
	}

        /**
         *
         * @param enderecoAbaRegistro
         */
        public void setEnderecoAbaRegistro(
			String enderecoAbaRegistro) {
		this.enderecoAbaRegistro = enderecoAbaRegistro;
	}
	
        /**
         *
         * @return
         */
        public boolean isTemPermissaAcessarAbaVisualizacao() {
		return temPermissaAcessarAbaVisualizacao;
	}

        /**
         *
         * @param temPermissaAcessarAbaVisualizacao
         */
        public void setTemPermissaAcessarAbaVisualizacao( boolean temPermissaAcessarAbaVisualizacao) {
		this.temPermissaAcessarAbaVisualizacao = temPermissaAcessarAbaVisualizacao;
	}
	
        /**
         *
         * @return
         */
        public String getNomeAbaVisualizacao() {
		return nomeAbaVisualizacao;
	}

        /**
         *
         * @param nomeAbaVisualizacao
         */
        public void setNomeAbaVisualizacao( String nomeAbaVisualizacao) {
		this.nomeAbaVisualizacao = nomeAbaVisualizacao;
	}
        
}