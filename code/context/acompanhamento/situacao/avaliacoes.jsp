

<%@page import="ecar.pojo.ItemEstUsutpfuacIettutfa"%>
<%@page import="ecar.dao.ItemEstUsutpfuacDao"%>
<%@page import="java.util.Iterator"%>
<%@page import="ecar.pojo.UsuarioAtributoUsua"%><jsp:directive.page import="comum.util.FileUpload"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="comum.util.Pagina"/><!--BARRA AVALIAÇÕES -->
<table class="barrabotoes" cellpadding="0" cellspacing="0">
	<tr>
		<td align="left">
		<b><%=configuracao.getLabelSituacaoListaPareceres() %></b>
		</td>
	</tr>
</table>
<% 
	CorDao corDao =  new CorDao(request);
	UsuarioDao usuDao = new UsuarioDao(request); 
	
	String primeiroIettClicado = Pagina.getParamStr(request, "primeiroIettClicado");
	itemDoNivelClicado = Pagina.getParamStr(request, "itemDoNivelClicado");
	String primeiroAriClicado = Pagina.getParamStr(request, "primeiroAriClicado");
	
	//String msgConfirm = null;
	
	boolean isFormUpload = FileUpload.isMultipartContent(request);
	List campos = new ArrayList();
	if(isFormUpload){
		campos = FileUpload.criaListaCampos(request); 
	}
	if (isFormUpload) {
		msgConfirm = FileUpload.verificaValorCampo(campos, "msgConfirm");
	} else {
		msgConfirm = Pagina.getParamStr(request, "msgConfirm");
	}

	List listAcompRef = ariDao.getAcompRelatorioArelOrderByFuncaoAcomp(ari);
	Iterator it = listAcompRef.iterator();
	String NAO_INFORMADO = "N/I";
	String NAO_LIBERADO = "N/L";
	boolean liberado = true;
	
	String dataUltimaAtualizacao = "";
	
	String cor = "N/I";
	int contAval = 0;
	
	String titulo = "";
	String caminhoImagem = "N/I";
	
	/* Regras para o usuário poder registrar o seu parecer ....  */
	// Revisar regras 
	/* 1. Usuário ter acesso a alteração ao item da estrutura  */
	/* 2. Usuáirio ser atribuido a função de acompahamento */
	/* 3. Acompanhamento não liberado   */
	/* 4. Data limite para o usuário não ser atinjida */
	/* 5. Se o superior dele já tiver dado o parecer ele não pode escrever o seu parecer  */
	/* 6. Se o usuário fizer parte de um grupo com acesso ao sistema ele também terá acesso */
	  
	  
	/* Regras para visualização do parecer */
	// Revisar regras
	/* 1. Se o usuário fizer parte de uma função de acompanhamento do item então terá acesso aos   */
	/* 	  relatorios dos acompanhamentos não liberados onde as funcao de acompanhamento tenham liberados */
	/*    o seu parecer    */  
	/* 2. Os usuários que tem permissão de visualizar o item só terão acesso aos relatorios */
	/*    quando o acompanhamento estiver liberado */
	
	while (it.hasNext() && (!indExibirSituacoesFormatoAbas.equals("A"))) { 
%>
<table class="form" width="100%" border="0" cellpadding="0" cellspacing="0">

	<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">
<%
		titulo = "";
		//procurar acompRelatorio
		AcompRelatorioArel acompRelatorio = (AcompRelatorioArel) it.next();
		TipoFuncAcompTpfa tpfa = null;
		TipoFuncAcompTpfa tpfaUsuario = null;
		
		//Se for abas
		if(indExibirSituacoesFormatoAbas.equals("S")) {
					
			//recupera o codigo tpfa passado pela tag BarraLinksregAcompParecerTag
			String codTpfa = Pagina.getParamStr(request, "codTpfa");
			
			//Se vier de um clique nas abas de Pareceres 
			if(codTpfa != null && !codTpfa.equals("")) {
				tpfa = (TipoFuncAcompTpfa) dao.buscar(TipoFuncAcompTpfa.class, Long	.valueOf(codTpfa));
				
			} else {
			
					
				//Se vier de um clique nas abas de Monitoramento
				if(itensFuncaoAcompanhamento != null && itensFuncaoAcompanhamento.size() > 0) {					
					tpfa = (TipoFuncAcompTpfa) itensFuncaoAcompanhamento.get(0);
				}		
			}
			
			
			//buscar na lista o parecer da função de acompanhamento
			Iterator itParecer = listAcompRef.iterator();
			while(itParecer.hasNext()) {
				AcompRelatorioArel acompRelatorioParecer = (AcompRelatorioArel) itParecer.next();
				if(tpfa != null && acompRelatorioParecer.getTipoFuncAcompTpfa().getCodTpfa() ==  tpfa.getCodTpfa()) {
					acompRelatorio = acompRelatorioParecer;
				}
			}
			
			//pára o laço para imprimir apenas o parecer da função de acompanhamento selecionada nas abas	
			indExibirSituacoesFormatoAbas = "A"; 	
			
		} else {
			// Se for tela inteira
			tpfa = acompRelatorio.getTipoFuncAcompTpfa();
		}
		

		ItemEstUsutpfuacDao iettEstUsuTpfaDao = new ItemEstUsutpfuacDao(request);
		ItemEstUsutpfuacIettutfa itemEstUsuTpfa = (ItemEstUsutpfuacIettutfa) iettEstUsuTpfaDao.getUsuarioAcompanhamento(ari.getItemEstruturaIett(), acompRelatorio.getTipoFuncAcompTpfa());
		
		//INÍCIO DA LÓGICA DE PERMISSÃO DE REGISTRO DE PARECER																				
		boolean usuarioLogadoEmiteParecer = false;
		Boolean permissaoAlterar =  new Boolean(false);
		
		
		ItemEstUsutpfuacIettutfa itemEstUsuAux = null;
		
		if("N".equals(acompRelatorio.getIndLiberadoArel())){			
			        itemEstUsuAux 
					= iettEstUsuTpfaDao.buscar(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());
		} else{
			if(acompRelatorio.getTipoFuncAcompTpfaUsuario() != null){
		        itemEstUsuAux 
				= iettEstUsuTpfaDao.buscar(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfaUsuario().getCodTpfa());
			} else{
		        itemEstUsuAux 
				= iettEstUsuTpfaDao.buscar(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());				
			}
		}
			
		boolean verificaResponsavel = false;
		ItemEstUsutpfuacIettutfa itemEstUsuAuxResponsavel 
		= iettEstUsuTpfaDao.buscar(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());	
		if(itemEstUsuAuxResponsavel !=null && itemEstUsuAuxResponsavel.getUsuarioUsu() != null){		
			if(itemEstUsuAuxResponsavel.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
				verificaResponsavel = true;
			}
		}
		
		boolean usuarioLogadoEmitiuUltimoParecer = true;
		
        //Verifica se a permissão é de grupo ou usuário
        if(itemEstUsuAux != null || verificaResponsavel == true) {
        
        	
        
        	if (itemEstUsuAux == null){
        		itemEstUsuAux = itemEstUsuAuxResponsavel;
        		usuarioLogadoEmitiuUltimoParecer = false;
        	}
        
			if (itemEstUsuAux.getUsuarioUsu() != null) {
				usuarioLogadoEmiteParecer = itemEstUsuAux.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
				if (usuarioLogadoEmitiuUltimoParecer){
					permissaoAlterar = new Boolean(usuarioLogadoEmiteParecer);
				}
				else{
					permissaoAlterar = new Boolean(usuarioLogadoEmitiuUltimoParecer);
				}

				if(!permissaoAlterar.booleanValue()){
					//adiciona a parte de buscar superiores
					List listaEstruturas = null;
					if(acompRelatorio.getIndLiberadoArel() == null || "N".equals(acompRelatorio.getIndLiberadoArel())) {
						listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());
					} else{
						if(acompRelatorio.getTipoFuncAcompTpfaUsuario()!=null){
							ItemEstUsutpfuacIettutfa itemEstUsuSuperior = 
								iettEstUsuTpfaDao.buscar(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfaUsuario().getCodTpfa());
							if(itemEstUsuSuperior!=null && itemEstUsuSuperior.getUsuarioUsu()!=null){
								usuarioLogadoEmiteParecer = itemEstUsuSuperior.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
								permissaoAlterar = new Boolean(usuarioLogadoEmiteParecer);								
							}
							if(!permissaoAlterar.booleanValue()){
								listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfaUsuario().getCodTpfa());
							}
						} else{
							listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());
						}
					}
					if(listaEstruturas != null) {
						Iterator itEstruturas = listaEstruturas.iterator();	
						while (itEstruturas.hasNext()){
							ItemEstUsutpfuacIettutfa itemEst = (ItemEstUsutpfuacIettutfa) itEstruturas.next();
					        if(itemEst != null) {							 							
								if (itemEst.getUsuarioUsu() != null) {
									usuarioLogadoEmiteParecer = itemEst.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
									permissaoAlterar = new Boolean(usuarioLogadoEmiteParecer);
									if(permissaoAlterar.booleanValue()){
										//acompRelatorio.setTipoFuncAcompTpfaUsuario(itemEst.getTipoFuncAcompTpfa());
										//tpfaUsuario = itemEst.getTipoFuncAcompTpfa();
										//request.getSession().setAttribute("codTpfaUsuario", tpfaUsuario.getCodTpfa());
										break;	
									}
								} else if (itemEst.getSisAtributoSatb() != null && itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {					
									Iterator itUsuarios = itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
									while (itUsuarios.hasNext()) {
										UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
										if (usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
											usuarioLogadoEmiteParecer = true;
											permissaoAlterar = new Boolean(true);
											break;
										}
									}					        	
						        }
					        } 
						}				
					}		
				}
				
			} else if (itemEstUsuAux.getSisAtributoSatb() != null) {
				if (itemEstUsuAux.getSisAtributoSatb() != null && itemEstUsuAux.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
					Iterator itUsuarios = itemEstUsuAux.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
					while (itUsuarios.hasNext()) {
						UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
						if (usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
							usuarioLogadoEmiteParecer = true;
							permissaoAlterar = new Boolean(true);
							break;
						}
					}
				}
				if(!permissaoAlterar.booleanValue()){
					//adiciona a parte de buscar superiores
					List listaEstruturas = null;
					if(acompRelatorio.getIndLiberadoArel() == null || "N".equals(acompRelatorio.getIndLiberadoArel())) {
						listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());
					} else{
						if(acompRelatorio.getTipoFuncAcompTpfaUsuario()!=null ){
							ItemEstUsutpfuacIettutfa itemEstUsuSuperior = 
								iettEstUsuTpfaDao.buscar(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfaUsuario().getCodTpfa());
							if(itemEstUsuSuperior!=null&& itemEstUsuSuperior.getUsuarioUsu()!=null){
								usuarioLogadoEmiteParecer = itemEstUsuSuperior.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
								permissaoAlterar = new Boolean(usuarioLogadoEmiteParecer);								
							}
							if(!permissaoAlterar.booleanValue()){
								listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfaUsuario().getCodTpfa());
							}
						} else{
							listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());
						}
					}
					if(listaEstruturas != null) {
						Iterator itEstruturas = listaEstruturas.iterator();	
						while (itEstruturas.hasNext()){
							ItemEstUsutpfuacIettutfa itemEst = (ItemEstUsutpfuacIettutfa) itEstruturas.next();
					        if(itemEst != null) {							 							
								if (itemEst.getUsuarioUsu() != null) {
									usuarioLogadoEmiteParecer = itemEst.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
									permissaoAlterar = new Boolean(usuarioLogadoEmiteParecer);
									if(permissaoAlterar.booleanValue()){
										break;	
									}
								} else if (itemEst.getSisAtributoSatb() != null && itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
									Iterator itUsuarios = itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
									while (itUsuarios.hasNext()) {
										UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
										if (usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
											usuarioLogadoEmiteParecer = true;
											permissaoAlterar = new Boolean(true);
											break;
										}
									}
								}
	
					        }
						}
					}						
				}			
			}
		} else{
			//adiciona a parte de buscar superiores
			List listaEstruturas = null;
			if(acompRelatorio.getIndLiberadoArel() == null || "N".equals(acompRelatorio.getIndLiberadoArel())) {
				listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());
			} else{
				if(acompRelatorio.getTipoFuncAcompTpfaUsuario()!=null){
					listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfaUsuario().getCodTpfa());
				} else{
					listaEstruturas = iettEstUsuTpfaDao.buscarSuperiores(ari.getItemEstruturaIett().getCodIett(), acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa());
				}
			}
			if(listaEstruturas != null) {
				Iterator itEstruturas = listaEstruturas.iterator();	
				while (itEstruturas.hasNext()){
					ItemEstUsutpfuacIettutfa itemEst = (ItemEstUsutpfuacIettutfa) itEstruturas.next();
			        if(itemEst != null) {							 							
						if (itemEst.getUsuarioUsu() != null) {
							usuarioLogadoEmiteParecer = itemEst.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu());
							permissaoAlterar = new Boolean(usuarioLogadoEmiteParecer);
							if(permissaoAlterar.booleanValue()){
								//acompRelatorio.setTipoFuncAcompTpfaUsuario(itemEst.getTipoFuncAcompTpfa());
								//tpfaUsuario = itemEst.getTipoFuncAcompTpfa();
								//request.getSession().setAttribute("codTpfaUsuario", tpfaUsuario.getCodTpfa());
								break;	
							}
						} else if (itemEst.getSisAtributoSatb() != null && itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {					
							Iterator itUsuarios = itemEst.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
							while (itUsuarios.hasNext()) {
								UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
								if (usuarioAtributoUsua.getUsuarioUsu().getCodUsu().equals(usuario.getCodUsu())){
									usuarioLogadoEmiteParecer = true;
									permissaoAlterar = new Boolean(true);
									break;
								}
							}					        	
				        }
			        } 
				}				
			}	
		}
		
		if (listaPermissaoTpfa.contains(tpfa) 
				&& validaPermissao.permissaoLeituraAcompanhamento(acompRelatorio.getAcompReferenciaItemAri(), seguranca.getUsuario(), seguranca.getGruposAcesso())){
%>
			<tr>
				<td colspan="2">
					<table class="barrabotoes" cellpadding="0" cellspacing="0">
						<tr>
							<td align="left">
							<b><%=tpfa.getDescricaoTpfa()%></b>
							<%	if (usuarioLogadoEmiteParecer && ehRegistro == false) {
									if(itemDoNivelClicado != null && itemDoNivelClicado.equals("")) { 
										itemDoNivelClicado = primeiroIettClicado;
									} %>
							<!-- ADICIONAR A IMAGEM DO LÁPIS --> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<a href="javascript:reloadRegistro(<%=primeiroIettClicado%>, <%=primeiroAriClicado%>, <%=itemDoNivelClicado%>, <%=tpfa.getCodTpfa()%>)" title="Editar">
									<img src="<%=_pathEcar%>/images/icon_alterar.png" border="0" alt=""></a>
								</a>
							<%
							} %>
							</td>
						</tr>
					</table>
				</td>
			</tr>
<%	
			if (
					(usuarioLogadoEmiteParecer && ehRegistro == true)
				) {
				verificaResponsavel = false;
			//TERMINA LOGICA DE PERMISSAO DE REGISTRO DE PARECER     
%>
				<tr>
					<td colspan="2">
						<table>
							<tr><td>
								<%@ include file="acompRelatorio.jsp" %>
							</td></tr>
						</table>
					</td>
				</tr>
<% 

			} else if(tpfa.getIndVisualizarParecer().equalsIgnoreCase("S")) {// Se o usuário não for autorizado para registrar o parecer
				
				//colocar pra aparecer as coisas
				if(acompRelatorio.getUsuarioUsuUltimaManutencao() == null){
					acompRelatorio.getUsuarioUsu().setNomeUsu(NAO_INFORMADO);
					titulo += " Não Informado <br> ";
				} else {
					acompRelatorio.getUsuarioUsu().setNomeUsu(acompRelatorio.getUsuarioUsuUltimaManutencao().getNomeUsuSent());
				} 
				
				if(acompRelatorio.getDescricaoArel() == null)
						acompRelatorio.setDescricaoArel( NAO_INFORMADO);
				
				if(acompRelatorio.getComplementoArel() == null)
					acompRelatorio.setComplementoArel(NAO_INFORMADO);
				
				if(acompRelatorio.getSituacaoSit() == null){
					SituacaoSit situacao = new SituacaoSit();
					situacao.setDescricaoSit(NAO_INFORMADO) ;
					acompRelatorio.setSituacaoSit( situacao );
				}
				
				UsuarioUsu usuarioImagem = null;  
        		String hashNomeArquivo = "";
        		String imagePath = "";
        		String pathRaiz = configuracao.getRaizUpload();
				
				if(acompRelatorio.getCor() != null){
					imagePath = corDao.getImagemPersonalizada(acompRelatorio.getCor(), acompRelatorio.getTipoFuncAcompTpfa(), "D");
					if( imagePath != null && imagePath.trim().length()>0) {
						usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
    					hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagePath);
    					Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagePath);
						
						/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */ 
					    caminhoImagem = "<img border=\"0\" src=\"" + request.getContextPath() + "/DownloadFile?tipo=open&RemoteFile=";
					    caminhoImagem += hashNomeArquivo + "\" style=\"width: 23px; height: 23px;\" title=\"" + acompRelatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\">";
					} else {
						if( acompRelatorio.getCor() != null && acompRelatorio.getCor().getCodCor() != null ) { 
							caminhoImagem = "<img border=\"0\" src=\"" + _pathEcar + "/images/";
							caminhoImagem += corDao.getImagemSinal(acompRelatorio.getCor(), acompRelatorio.getTipoFuncAcompTpfa()) + "\" title=\"";
							caminhoImagem += acompRelatorio.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
						}
					}
				} else {
					caminhoImagem = "N/I";
				} 
					
				if(acompRelatorio.getDataUltManutArel() != null)
					dataUltimaAtualizacao = Data.parseDate(acompRelatorio.getDataUltManutArel());
				else
					dataUltimaAtualizacao = "N/I";
					
				if(acompRelatorio.getIndLiberadoArel() == null || "N".equals(acompRelatorio.getIndLiberadoArel())) {
					liberado = false;
				}
				
				if(acompRelatorio.getUsuarioUsuUltimaManutencao() != null){
					String telefone = "";
			        if(acompRelatorio.getUsuarioUsuUltimaManutencao().getComercDddUsu() != null && !"".equals(acompRelatorio.getUsuarioUsuUltimaManutencao().getComercDddUsu())){
			        	telefone += acompRelatorio.getUsuarioUsuUltimaManutencao().getComercDddUsu();
			        }
			        if(acompRelatorio.getUsuarioUsuUltimaManutencao().getComercTelefoneUsu() != null && !"".equals(acompRelatorio.getUsuarioUsuUltimaManutencao().getComercTelefoneUsu())){
			        	if(!"".equals(telefone)){
			        		telefone += " ";
			        	}
			        	telefone += acompRelatorio.getUsuarioUsuUltimaManutencao().getComercTelefoneUsu();
			        }
			        titulo += telefone;
			        
			        if (usuDao.getCelularByUsuario(acompRelatorio.getUsuarioUsuUltimaManutencao()) != null && !"".equals(usuDao.getCelularByUsuario(acompRelatorio.getUsuarioUsuUltimaManutencao()))){ 
			        	titulo += " <br> ";
			        	titulo += usuDao.getCelularByUsuario(acompRelatorio.getUsuarioUsuUltimaManutencao());
			        }
					
			        if(!"".equals(titulo)){
						titulo += " <br> ";
					}
					titulo += acompRelatorio.getUsuarioUsuUltimaManutencao().getEmail1UsuSent();
				}
				
				//variavel de controle, passada como parametro para a funcao que faz o hint: dica
				String posicao ="-1";
				String imagem_inativa = "";
				if (acompRelatorio.getUsuarioUsuUltimaManutencao() != null) {
					if (!"S".equals(acompRelatorio.getUsuarioUsuUltimaManutencao().getIndAtivoUsu())){
						imagem_inativa="<img src=\"/../../images/icon_usuario_inativo.png\" title=\"Usuário Inativo\">";
					}
				}		
%>
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr>
				
					<td class="label" align="right" width="50%"> 
						<%=acompRelatorio.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()%> atribuído por:
					</td>
					<td class="item_InfoUsu_td" onMouseOver="javascript:showInfoUsu(<%=contAval%>,'<%=acompRelatorio.getUsuarioUsu().getNomeUsu() %>','<%=posicao%>')" onMouseOut="javascript:hideInfoUsu(<%=contAval%>,'<%= acompRelatorio.getUsuarioUsu().getNomeUsu() %>','<%=posicao%>')"><%= acompRelatorio.getUsuarioUsu().getNomeUsu()%><span id="spanInfoUsu<%=contAval%>_<%= acompRelatorio.getUsuarioUsu().getNomeUsu() %>_<%=posicao%>" class='item_InfoUsu_span'><%=titulo%></span><%=imagem_inativa%></td>
				
					
										
				</tr>
				
				<tr>
					<td class="label" align="right">Função:</td>
					<td><%=acompRelatorio.getTipoFuncAcompTpfa().getLabelTpfa()%></td>
				</tr>
<% 
			//Acompanhamentos liberados -> NÃO MUDA
			if("N".equals(exigeLiberarAcompanhamento) || acompRelatorio.getAcompReferenciaItemAri().getStatusRelatorioSrl().equals(statusLiberado)){
%>
		
				<tr>
					<td class="label" align="right"><%=configuracao.getLabelSituacaoParecer() %>:</td>		
					<td><%=acompRelatorio.getSituacaoSit().getDescricaoSit()%></td>
				</tr>
				<tr>
					<td class="label" align="right"><%=configuracao.getLabelCorParecer() %>:</td>
					<td><%= caminhoImagem %></td>
				</tr>
				<tr>
					<td class="label" align="right"><%=acompRelatorio.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()%>:</td>
					<td><%= acompRelatorio.getDescricaoArel() %></td>
				</tr>
					
<%
			if (ocultarObservacoesParecer == null || ocultarObservacoesParecer.equals("N")){
%>
	
				<tr>
					<td class="label" align="right">Observações:</td>
					<td><%= acompRelatorio.getComplementoArel()%></td>
				</tr>
		
<%
			}
%>
	
				<tr>
					<td class="label" valign="top">Data Limite:</td>
					<%
					AcompRefItemLimitesArli limites = ariDao.getAcompRefItemLimitesByAcompReferenciaItemTipoFuncAcomp(acompRelatorio.getTipoFuncAcompTpfa(), ari);
					if(limites !=null){
						%>
						<td><%= Pagina.trocaNullData(limites.getDataLimiteArli())%></td>
					<%
					}
					%>
				</tr>
			
			<tr>
				<td class="label" align="right">Atualização:</td>
				<td><%=dataUltimaAtualizacao%></td>
			</tr>
<%
			} else {
%>	
				<tr>
					<td>&nbsp;</td>
					<td><b>Acompanhamento não liberado.</b></td>
				</tr>			
<%
			}

%>
			<tr>
				<td class="espacador" colspan="2"><img src="<%=_pathEcar%>/images/pixel.gif" alt=""></td>
			</tr>
			
<% 			//Exibe Ultimas Posições e popup de Todas Posições 
			TipoFuncAcompTpfa funcao = (TipoFuncAcompTpfa) tipoFuncAcompDao.buscar(TipoFuncAcompTpfa.class, tpfa.getCodTpfa());
			List arels = ariDao.getUltimosAcompanhamentosItem(ari, funcao, Integer.valueOf(2));
			if (arels != null && arels.size() > 0) {
%>
		
				<tr>
					<td colspan="2" class="label" style="text-align: left"><!-- a href="#" onclick="mostrarEsconder('</%=acompRelatorio.getCodArel()%>');" >Mostrar &Uacute;ltimas Posi&ccedil;&otilde;es Emitidas</a></td-->			
					<a href="javascript:mostrarEscondePosicoes('<%=acompRelatorio.getCodArel()%>');">Mostrar &Uacute;ltimos(as) <%=configuracao.getLabelSituacaoListaPareceres()%> Emitidos(as)</a></td>
				</tr>
				<tr>
					<td colspan="2" >
						<table id="ultimasPosicoes<%=acompRelatorio.getCodArel()%>" style="text-align:left;display:none" class="label" >
<%
				periodo = "";
				String mesAno = "";
				String situacao = "";
				cor = "";
				String descricao = "";
				String observacao = "";
	
				Iterator itArels = arels.iterator();
				while(itArels.hasNext()){
					AcompRelatorioArel arel = (AcompRelatorioArel) itArels.next();
					//if(Pagina.getParamStr(request, "codTpfa").equals(arel.getTipoFuncAcompTpfa().getCodTpfa().toString())){				
						if(arel.getAcompReferenciaItemAri() != null &&
							arel.getAcompReferenciaItemAri().getAcompReferenciaAref() != null){
							periodo = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getNomeAref();
							mesAno = arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getDiaAref() + "/" + 
								arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getMesAref() + "/" +
								arel.getAcompReferenciaItemAri().getAcompReferenciaAref().getAnoAref();
						}
													
						if(arel.getSituacaoSit() != null){
							situacao = arel.getSituacaoSit().getDescricaoSit();
						}
						else{
							situacao = "N/I";
						}
						
						if(arel.getCor() != null){
							imagePath = corDao.getImagemPersonalizada(arel.getCor(), arel.getTipoFuncAcompTpfa(), "D");
							if( imagePath != null && imagePath.trim().length()>0) {
								usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
								hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagePath);
								Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagePath);
								
								/* -- As tags do CSS "max-width" e "max-height" não funcionam no IE6 ou menor -- */ 
							    cor = "<img border=\"0\" src=\"" + request.getContextPath() + "/DownloadFile?tipo=open&RemoteFile=";
							    cor += hashNomeArquivo + "\" style=\"width: 23px; height: 23px;\" title=\"" + arel.getTipoFuncAcompTpfa().getLabelTpfa() + "\">";
							} else {
								if( arel.getCor() != null && arel.getCor().getCodCor() != null ) { 
									cor = "<img border=\"0\" src=\"" + _pathEcar + "/images/";
									cor += corDao.getImagemSinal(arel.getCor(), arel.getTipoFuncAcompTpfa()) + "\" title=\"";
									cor += arel.getTipoFuncAcompTpfa().getLabelTpfa() + "\" >";
								}
							}
						}
						else {
							cor = "N/I";
						}
						
						if(arel.getDescricaoArel() != null && !"".equals(arel.getDescricaoArel().trim())){
							descricao = Util.normalizaQuebraDeLinhaHTML(arel.getDescricaoArel());
							descricao = Util.normalizaCaracterMarcador(descricao);
							descricao = Util.normalizaCaracterHTML(descricao);
						}
						else {
							descricao = "N/I";
						}
						
						if(arel.getComplementoArel() != null && !"".equals(arel.getComplementoArel().trim())){
							observacao = Util.normalizaQuebraDeLinhaHTML(arel.getComplementoArel());
							observacao = Util.normalizaCaracterMarcador(observacao);
							observacao = Util.normalizaCaracterHTML(observacao);
						}
						else {
							observacao = "N/I";
						}
%>					
				
						<!-- Mostrar últimas posições emitidas -->
						<tr> 
							<td class="label">Per&iacute;odo</td>
							<td><%=periodo%></td>
						</tr>
						<tr>
							<td class="label">Dia/Mês/Ano</td>
							<td><%=mesAno%></td>
						</tr>
						<tr>
							 <td class="label"><%=configuracao.getLabelSituacaoParecer() %>:</td>
							 <td><%=situacao%></td>
						</tr>
						<tr>
							 <td class="label"><%=configuracao.getLabelCorParecer() %>:</td>
							 <td><%=cor%></td>
						</tr> 
						<tr>
							 <td class="label"><%=acompRelatorio.getTipoFuncAcompTpfa().getLabelPosicaoTpfa()%>:</td>
							 <td><%=descricao%></td>
						</tr> 
						<tr>
							 <td class="label">Observa&ccedil;&otilde;es:</td>
							 <td><%=observacao%></td>
						</tr> 
						<tr>
							 <td class="label" colspan="2"> &nbsp;</td>
						</tr> 
						
						
<%
				//}
			}//fim while
			
%>
				</table>
				<table class="form" width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td style="text-align: center;">
							<label class="label">C&Oacute;DIGO</label>
						</td>
						<td style="text-align: center;">
							<label class="label">NOME</label>
						</td>
						<td style="text-align: center;">
							<label class="label">DATA IN&Iacute;CIO</label>
						</td>
						<td style="text-align: center;">
							<label class="label">DATA T&Eacute;RMINO</label>
						</td>
					</tr>
					<%
					for(ItemEstruturaIett estruturaIett : listaDescendentes){
					%>
					<tr>
						<td style="text-align: center; width: 15%;">
							<%=estruturaIett.getSiglaIett()%>
						</td>
						<td style="width: 55%; text-align: justify;">
							<%=estruturaIett.getNomeIett()%>
						</td>
						<td style="text-align: center; width: 15%;">
							<%=Data.parseDate(estruturaIett.getDataInicioIett())%>
						</td>
						<td style="text-align: center; width: 15%;">
							<%=Data.parseDate(estruturaIett.getDataTerminoIett())%>
						</td>
					</tr>
					<%
						}
					%> 	
			</td>
		</tr>
<%
	}// fim if (arels != null && arels.size() > 0){
	
%>

<script language=javascript>
	function abrirPopUpTodasPosicoesVisualizar(cdAri, cdTpfa){
        abreJanela("<%=_pathEcar%>/acompanhamento/situacao/popupTodasPosicoes.jsp?codAri=" + cdAri+ "&codTpfa=" + cdTpfa, 700, 500, "Todas");
}

function mostrarEscondePosicoes(indice) {
		if (document.getElementById('ultimasPosicoes'+indice).style.display=='none') {
		     document.getElementById('ultimasPosicoes'+indice).style.display='';
		} else {
		     document.getElementById('ultimasPosicoes'+indice).style.display='none';
		}
}   

</script>
		<tr>
			<td colspan="2" class="label" style="text-align: left"> 
				<a href="#" onclick="abrirPopUpTodasPosicoesVisualizar('<%=acompRelatorio.getAcompReferenciaItemAri().getCodAri()%>','<%=acompRelatorio.getTipoFuncAcompTpfa().getCodTpfa()%>');">
						Mostrar Todos(as) <%=configuracao.getLabelSituacaoListaPareceres()%> Emitidos(as)</a> 
			</td>			
		</tr>
		
		<tr>
			<td colspan="2">
			    <!-- Chama a tela de anexo pra visualizar -->
				<%@ include  file='frmAnexo.jsp' %>
			</td>
		</tr>
		
	</table>
<%			
		
		}// fim else do if acompRelatorio.getUsuarioUsu().equals(usuario)
	}	
%>
	</table>
<%
		contAval++;
	}//fecha while (it.hasNext())
%>



