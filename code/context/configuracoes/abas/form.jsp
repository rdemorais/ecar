
<jsp:directive.page import="comum.util.Util" />
<jsp:directive.page import="comum.util.Pagina"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.dao.TipoAcompanhamentoDao"/>
<jsp:directive.page import="ecar.pojo.TipoAcompanhamentoTa"/>
<jsp:directive.page import="ecar.pojo.Link"/>
<jsp:directive.page import="ecar.dao.LinkDao"/>
<jsp:directive.page import="ecar.pojo.Aba"/>
<jsp:directive.page import="ecar.dao.FuncaoDao"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="ecar.pojo.FuncaoFun"/>
<jsp:directive.page import="ecar.dao.AbaDao"/>
<jsp:directive.page import="java.util.Collection"/>
<jsp:directive.page import="comum.database.Dao"/>
<jsp:directive.page import="ecar.pojo.HistoricoConfig"/>
<jsp:directive.page import="ecar.dao.HistoricoDao"/>
<script language="javascript" src="<%=_pathEcar%>/js/AnchorPosition.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/ColorPicker2.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/PopupWindow.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	var field = "";
	// Create a new ColorPicker object using Window Popup
	var cp = new ColorPicker('window');

	function pickColor(color) {
		field.value = color;
	}
	
	function selecionarCor(campo){
		field = campo;
		cp.select(campo,'pick');		
	}

	function habilitardesabilitarRegistro(campo, posicao, tipo, numeroAba) {
		
		var i = 0;

		// usuario clicou no posicao
		if (tipo == 1) {
			for (;i<form.ehPadraoRegistro.length;i++) {
				// campo posicao foi checado
				if(!campo.checked) {
	
					// eh padrao registro esta selecionado e esta na mesma linha do campo posicao
					if(form.ehPadraoRegistro[i].checked && ((posicao+1)==(i+1+""))) {
						form.ehPadraoRegistro[i].checked = false;
					}
	
					// eh padrao visualizacao esta selecionado e esta na mesma linha do campo posicao
					if(form.ehPadraoVisualizacao[i].checked && ((posicao+1)==(i+1+""))) {
						form.ehPadraoVisualizacao[i].checked = false;
					}
				}
			}

		// usuario clicou no eh registro ou eh visualizacao
		} else {	

			// eh registro ou eh visualizacao foi checado
			if(campo.checked) {
				// posicao não checada
				if(!document.getElementById('exibePosicaoAba' + numeroAba.toString()).checked) {
					campo.checked = false;
					if (tipo==2)
								alert('O campo Posição precisa estar selecionado para definição do campo de Registro'); 
					else if(tipo ==3)
								alert('O campo Posição precisa estar selecionado para definição do campo de Visualização');
				}
			}
		}	
	}

		
</script>
<table class="form">
	<tr>
		<td>&nbsp;</td>
	</tr>
	<% 
		String codTaSlc= Pagina.trocaNull(request.getParameter("CodTa")) ;
       /* Iterator itAcompanhamentos = (new TipoAcompanhamentoDao()).getTipoAcompanhamentoAtivosComAcompanhamentoBySeqApresentacao().iterator();
		 
		if (itAcompanhamentos!=null && itAcompanhamentos.hasNext() ){*/
	%> 
	<%--tr>
		<td colspan="8"> <span class="label"> Tipo de acompanhamento</span>
		  <select name="CodTa" id="CodTa" onchange="document.form.submit()">
		  	<option id="padrao" value = "" >Padrão</option>
		  <%
		  while(itAcompanhamentos.hasNext()){
		  TipoAcompanhamentoTa acompanhamento =  (TipoAcompanhamentoTa) itAcompanhamentos.next(); 
		   %>
			<option value = "<%=acompanhamento.getCodTa()%>" <%= Pagina.isSelected(acompanhamento.getCodTa(),  codTaSlc) %>><%=acompanhamento.getDescricaoTa() %>   </option>
		<%} %>
		  </select>  
		  <input type="hidden" name="indGeral" id="indGeral" value="<%=( codTaSlc.equals("")?"S":"N") %>">
		</td>
	</tr%>
		<%} --%>
	
	
	<tr> 
		<%--  Para adicionar uma nova aba superior deve-se especificar na base de dados o campo
				aba_superior = 'S' da tabela tb_abas 
		
		   --%>
		<td><H1><b>Abas das Listas </b></H1></td>
	</tr>
	<tr class="linha_subtitulo">
		<td width="20%">Nome </td>
		<td width="5%">Posi&ccedil;&atilde;o</td>
		<td width="5%">Ordem</td>
		<td width="30%" colspan="2">Fun&ccedil;&atilde;o</td>
		<td width="40%" colspan="2">Label</td>
	</tr>
	
	
	<%	
	//new AbaDao(request).listarSuperior();
	Collection listaSup = abaDao.listarSuperior();//listar(Aba.class, new String[]{"codAba", "asc"}); //abaDao.listarSuperior();
	Iterator itSup = listaSup.iterator();
	String labelFuncao;
	
	FuncaoDao funcaoDao = new FuncaoDao(request);
	List funcoes = funcaoDao.getFuncoesOpcionais(); 
	
	
	StringBuffer validarOrdensSup = new StringBuffer();
	StringBuffer validarLabelsSup = new StringBuffer();
	
	while (itSup.hasNext()) {
		Aba aba = (Aba) itSup.next();
		
		if (!"NIVEL_PLANEJAMENTO".equals(aba.getNomeAba()) && !aba.getNomeAba().equals("SITUACAO_INDICADORES") ) {
			%>
			<tr>
				<td><%=aba.getNomeAba()%></td>
				<td align="left" width="5%">
					<input type="checkbox" class="form_check_radio" id="exibePosicaoAba<%=aba.getCodAba()%>" 
							name="exibePosicaoAba<%=aba.getCodAba()%>"
							value="S" <%=Pagina.isChecked(aba.getExibePosicaoAba(), "S")%>>
				</td>
				<td>
					<input type="text" name="ordemAba<%=aba.getCodAba()%>" value="<%=aba.getOrdemAba()%>" size="4" maxlength="4">
				</td>
				<td colspan="2">
					<select id="funAba<%=aba.getCodAba()%>" name="funAba<%=aba.getCodAba()%>" onchange="javascript:mostraEscondeLabel('<%=aba.getCodAba()%>')">
						<option value=""></option>
<%
			Iterator itf = funcoes.iterator();
			while(itf.hasNext()){
				FuncaoFun funcao = (FuncaoFun) itf.next();
%>						<option value="<%=funcao.getCodFun()%>" id="<%=funcao.getCodFun()%>" 
							<%if (aba.getFuncaoFun() != null && aba.getFuncaoFun().getCodFun() == funcao.getCodFun()){%> selected <%}%>><%=funcao.getNomeFun()%>
						</option>
							<%}//fim while%>
					</select>
				</td>
				<td colspan="2">
					<input type="text" id="labelAba<%=aba.getCodAba()%>" name="labelAba<%=aba.getCodAba()%>" value="<%=aba.getLabelAba()%>" size="25" 
					<%if(aba.getFuncaoFun()!=null){%>
						style="display:none"
					<%} %>>
					<%if(aba.getFuncaoFun()!=null){
						labelFuncao = aba.getFuncaoFun().getLabelPadraoFun();
					}else{
						labelFuncao = "";
					}%>
					<input type="text" id="labelFun<%=aba.getCodAba()%>" name="labelFun<%=aba.getCodAba()%>" value="<%=labelFuncao%>" size="25" disabled
					<%if(aba.getFuncaoFun()==null){%>
						style="display:none"
					<%} %>
					>
				</td>
			</tr>
<%
			validarOrdensSup.append("\t if (!validaString(form.ordemAba").append(aba.getCodAba().toString()).append(", \"Ordem\", true)) { \n");
			validarOrdensSup.append("\t\t return(false); \n");
			validarOrdensSup.append("\t } else {\n");
			validarOrdensSup.append("\t\t if (!validaNum(form.ordemAba").append(aba.getCodAba().toString()).append(", \"Ordem\", false)) { \n");
			validarOrdensSup.append("\t\t\t alert(\"").append(_msg.getMensagem("aba.validacao.ordemAba.invalido")).append("\"); \n");
			validarOrdensSup.append("\t\t\t form.ordemAba").append(aba.getCodAba().toString()).append(".focus(); \n");
			validarOrdensSup.append("\t\t\t return(false); \n");
			validarOrdensSup.append("\t\t } \n");
			validarOrdensSup.append("\t } \n");
			
			validarLabelsSup.append("\t if (!validaString(form.labelAba").append(aba.getCodAba().toString()).append(", \"Label\", true)) { \n");
			validarLabelsSup.append("\t\t return(false); \n");
			validarLabelsSup.append("\t } \n");
		}
	}
%>
	<tr>
		<td>&nbsp;</td>
	</tr>
	




	<tr>
		<td><H1><b>Abas do Item</b></H1></td>
	</tr>
	
	<tr class="linha_subtitulo">
		<td width="20%">Nome </td>
		<td width="5%">Posi&ccedil;&atilde;o</td>
		<td width="5%">Registro</td>
		<td width="5%">Visualiza&ccedil;&atilde;o</td>
		<td width="5%">Ordem</td>
		<td width="25%">Fun&ccedil;&atilde;o</td>
		<td width="35%">Label</td>
	</tr>
	
	
	
<%
	
	Collection lista =  abaDao.listarAbasMonitoramento(); //listar(Aba.class, new String[]{"codAba", "asc"});
	Iterator it = lista.iterator();

	StringBuffer validarOrdens = new StringBuffer();
	StringBuffer validarLabels = new StringBuffer();
	int posicao = 0;
	
	while (it.hasNext()) {
		Aba aba = (Aba) it.next();
		if (!"NIVEL_PLANEJAMENTO".equals(aba.getNomeAba()) && !aba.getNomeAba().equals("SITUACAO_INDICADORES")) {
			%>
			<tr>
				<td><%=aba.getNomeAba()%></td>
				<td align="left">
					<input type="checkbox" class="form_check_radio" id="exibePosicaoAba<%=aba.getCodAba()%>" 
							name="exibePosicaoAba<%=aba.getCodAba()%>" 
							value="S" <%=Pagina.isChecked(aba.getExibePosicaoAba(), "S")%> onclick="habilitardesabilitarRegistro(this, <%=posicao%>, 1, <%=aba.getCodAba()%>);">
				</td>
				<td align="left">
					<input type="radio" class="form_check_radio" id="ehPadraoRegistro" 
							name="ehPadraoRegistro" 
							value="<%=aba.getCodAba()%>" <%=Pagina.isChecked(aba.getEhPadraoRegistro(), "S")%> onclick="habilitardesabilitarRegistro(this, <%=posicao%>, 2, <%=aba.getCodAba()%>);">
				</td>
				<td align="left">
					<input type="radio" class="form_check_radio" id="ehPadraoVisualizacao" 
							name="ehPadraoVisualizacao" 
							value="<%=aba.getCodAba()%>" <%=Pagina.isChecked(aba.getEhPadraoVisualizacao(), "S")%> onclick="habilitardesabilitarRegistro(this, <%=posicao%>, 3, <%=aba.getCodAba()%>);">
				</td>
				<td>
					<input type="text" name="ordemAba<%=aba.getCodAba()%>" value="<%=aba.getOrdemAba()%>" size="4" maxlength="4">
				</td>
				<td>
					<select id="funAba<%=aba.getCodAba()%>" name="funAba<%=aba.getCodAba()%>" onchange="javascript:mostraEscondeLabel('<%=aba.getCodAba()%>')">
						<option value=""></option>
<%
					Iterator itf = funcoes.iterator();
					while(itf.hasNext()){
						FuncaoFun funcao = (FuncaoFun) itf.next();
%>						<option value="<%=funcao.getCodFun()%>" id="<%=funcao.getCodFun()%>" <%if (aba.getFuncaoFun() != null && aba.getFuncaoFun().getCodFun() == funcao.getCodFun()){%>selected <%}%>><%=funcao.getNomeFun()%></option>
					<%}%>
				</select>
				</td>
				<td>
					<input type="text" id="labelAba<%=aba.getCodAba()%>" name="labelAba<%=aba.getCodAba()%>" value="<%=aba.getLabelAba()%>" size="25" 
					<%if(aba.getFuncaoFun()!=null){%>
						style="display:none"
					<%} %>>
					<%if(aba.getFuncaoFun()!=null){
						labelFuncao = aba.getFuncaoFun().getLabelPadraoFun();
					}else{
						labelFuncao = "";
					}%>
					<input type="text" id="labelFun<%=aba.getCodAba()%>" name="labelFun<%=aba.getCodAba()%>" value="<%=labelFuncao%>" size="25" disabled
					<%if(aba.getFuncaoFun()==null){%>
						style="display:none"
					<%} %>
					>
				</td>
			</tr>
<%
			validarOrdens.append("\t if (!validaString(form.ordemAba").append(aba.getCodAba().toString()).append(", \"Ordem\", true)) { \n");
			validarOrdens.append("\t\t return(false); \n");
			validarOrdens.append("\t } else {\n");
			validarOrdens.append("\t\t if (!validaNum(form.ordemAba").append(aba.getCodAba().toString()).append(", \"Ordem\", false)) { \n");
			validarOrdens.append("\t\t\t alert(\"").append(_msg.getMensagem("aba.validacao.ordemAba.invalido")).append("\"); \n");
			validarOrdens.append("\t\t\t form.ordemAba").append(aba.getCodAba().toString()).append(".focus(); \n");
			validarOrdens.append("\t\t\t return(false); \n");
			validarOrdens.append("\t\t } \n");
			validarOrdens.append("\t } \n");
			
			validarLabels.append("\t if (!validaString(form.labelAba").append(aba.getCodAba().toString()).append(", \"Label\", true)) { \n");
			validarLabels.append("\t\t return(false); \n");
			validarLabels.append("\t } \n");
			
			posicao++;
		}
	}
%>

	<tr>
		<td>&nbsp;</td>
	</tr>

	<tr>
		<td><H1><b>Histórico</b></H1></td>
	</tr>
	
	<tr class="linha_subtitulo">
		<td width="17%">Nome</td>
		<td width="43%" colspan="3">Cor</td>
		<td width="45" colspan="3">Ícone</td>		
	</tr>

<%
	//ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
	HistoricoDao historicoDao = new HistoricoDao(request);
	List historicos = historicoDao.listar(HistoricoConfig.class, new String[]{"codHistorico", "asc"});
	Iterator itHistoricos = historicos.iterator();
	String pathRaiz = request.getContextPath();
	String pathRaizUpload = configuracao.getRaizUpload() + new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getUploadIconeLinks() + "/";
	//String caminhoImg = "";
	_obrigatorio = "";
	
	while (itHistoricos.hasNext()) {
		HistoricoConfig historico = (HistoricoConfig) itHistoricos.next();
		//if(historico.getIconeHistorico() != null && !(historico.getIconeHistorico().trim()).equals(""))
		//	caminhoImg = pathRaiz +"/DownloadFile?tipo=open&RemoteFile=" + pathRaizUpload + historico.getIconeHistorico();
		pathRaizUpload = configuracao.getRaizUpload() + new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getUploadIconeLinks() + "/";
//		pathRaizUpload  = "/home/02759475484/pessoal/upload/iconeLinks/";
		
		String imagePathHistorico = historicoDao.getImagemPersonalizadaHistorico(historico);
		String arquivo = Util.getNomeArquivo(imagePathHistorico);
		if( imagePathHistorico != null ) { 
	    	//caminhoImg = request.getContextPath()+"/DownloadFile?tipo=open&RemoteFile=" + imagePathHistorico;
	    	arquivo = Util.getNomeArquivo(imagePathHistorico);
		} //else {
		//	if(HistoricoConfig.COD_ICONE_PARA_EXIBIR_HISTORICO.equals(historico.getCodHistorico())) { 
		//		//caminhoImg = _pathEcar + HistoricoConfig.ICONE_PADRAO_PARA_EXIBIR_HISTORICO;
		//		pathRaizUpload = "/images/";
		//		arquivo = Util.getNomeArquivo(HistoricoConfig.ICONE_PADRAO_PARA_EXIBIR_HISTORICO);
		//	} else if(HistoricoConfig.COD_ICONE_PARA_OCULTAR_HISTORICO.equals(historico.getCodHistorico())) {
		//		//caminhoImg = _pathEcar + HistoricoConfig.ICONE_PADRAO_PARA_OCULTAR_HISTORICO;
		//		pathRaizUpload = "/images/";
		//		arquivo = Util.getNomeArquivo(HistoricoConfig.ICONE_PADRAO_PARA_OCULTAR_HISTORICO);
		//	} else if(HistoricoConfig.COD_ICONE_EXCLUSAO.equals(historico.getCodHistorico())) {
		//		//caminhoImg = _pathEcar + HistoricoConfig.ICONE_PADRAO_EXCLUSAO;
		//		pathRaizUpload = "/images/";
		//		arquivo = Util.getNomeArquivo(HistoricoConfig.ICONE_PADRAO_EXCLUSAO);
		//	} else if(HistoricoConfig.COD_ICONE_ALTERACAO.equals(historico.getCodHistorico())) {
		//		//caminhoImg = _pathEcar + HistoricoConfig.ICONE_PADRAO_ALTERACAO;
		//		pathRaizUpload = "/images/";
		//		arquivo = Util.getNomeArquivo(HistoricoConfig.ICONE_PADRAO_ALTERACAO);
		//	} else if(HistoricoConfig.COD_ICONE_MOVIDO_ENTRE_ESTRUTURAS.equals(historico.getCodHistorico())) {
		//		//caminhoImg = _pathEcar + HistoricoConfig.ICONE_PADRAO_MOVIDO_ENTRE_ESTRUTURAS;
		//		pathRaizUpload = "/images/";
		//		arquivo = Util.getNomeArquivo(HistoricoConfig.ICONE_PADRAO_MOVIDO_ENTRE_ESTRUTURAS);
		//	}
		//} 

		String nomeCampo = "iconeHistorico" + historico.getCodHistorico();
		String labelCampo = "Ícone de " + historico.getNomeHistorico() + " ";
		%>
		<tr>
			<td valign="top"><%= historico.getNomeHistorico() %></td>
			<td colspan="3" valign="top">				
				<input type="text" name="corHistorico<%=historico.getCodHistorico()%>" size="7" maxlength="7"
					value="<%=Pagina.trocaNull(historico.getCorHistorico())%>">
					<A HREF="#"
						onClick="selecionarCor(document.forms[0].corHistorico<%=historico.getCodHistorico()%>);return false;"
						NAME="pick" ID="pick">Selecionar Cor</A>								
			</td>
			<td colspan="3" valign="top">
				<table align="left">
					<util:uploadArquivoTag 
						nomeCampo="<%=nomeCampo%>" 
						labelCampo="<%=labelCampo%>"
						contextPath="<%=request.getContextPath()%>"
						pathRaiz="<%=pathRaizUpload%>"
						arquivo="<%=arquivo%>"
						desabilitado="<%=_disabled%>"
						obrigatorio="<%=_obrigatorio%>"
					/>
				</table>
			</td>											
		</tr>
		
		
		
<%  
		//caminhoImg = "";
	}%>

	
	<tr>
		<td>&nbsp;</td>
	</tr>

	<tr>
		<td><H1><b>Links</b></H1></td>
	</tr>
	
	<tr class="linha_subtitulo">
		<td width="17%" colspan="2">Nome </td>		
		<td width="4%">Monitoramento</td>
		<td width="4%">Ordem</td>						
		<td width="25%">Label</td>
		<td width="45%" colspan="2"></td>
	</tr>

<%
	LinkDao linkDao = new LinkDao(request);
	List links = linkDao.listar(Link.class, new String[]{"codLink", "asc"});
	Iterator itLinks = links.iterator();
	//caminhoImg = "";
	_obrigatorio = "";
	pathRaizUpload = configuracao.getRaizUpload() + configuracao.getUploadIconeLinks() + "/";
	
	while (itLinks.hasNext()) {
		Link link = (Link) itLinks.next();
		String arquivo = "";
		if(link.getIconeLink() != null && !(link.getIconeLink().trim()).equals("")) {
			arquivo = Util.getNomeArquivo(link.getIconeLink());
			//caminhoImg = pathRaiz +"/DownloadFile?tipo=open&RemoteFile=" + pathRaizUpload + link.getIconeLink();
		}
		
		String nomeCampo = "iconeLink" + link.getCodLink();
		String labelCampo = "Ícone de Link ";
	%>
		<tr>
			<td valign="top" colspan="2"><%= link.getNomeLink() %></td>
			<td align="center" valign="top">
				<input type="checkbox" class="form_check_radio" name="exibeMonitoramentoLink<%=link.getCodLink()%>" value="S" <%=Pagina.isChecked(link.getExibeMonitoramentoLink(), "S")%>>				
			</td>	
			<td align="center" valign="top">
				<input type="text" name="ordemLink<%=link.getCodLink()%>" value="<%=link.getOrdemLink()%>" size="4" maxlength="4">
			</td>				
			<td valign="top">
				<input type="text" name="labelLink<%=link.getCodLink()%>" value="<%=link.getLabelLink() %>" size="25">
			</td>
			<td valign="top" colspan="2">				
				<!-- img name="icone< %=link.getCodLink()%>" src="< %=caminhoImg%>" width="" heigth="">				
				<input type="hidden" name="iconeLink< %=link.getCodLink()%>" value="< %=link.getIconeLink()%>"/>
				<input type=button class="botao" name="inserir< %=link.getCodLink()%>" value="Inserir" onClick="javascript:abrirPopUpUpload('< %=link.getCodLink()%>', 'Icone')"-->
				<table align="left">
					<util:uploadArquivoTag 
						nomeCampo="<%=nomeCampo%>" 
						labelCampo="<%=labelCampo%>"
						contextPath="<%=request.getContextPath()%>"
						pathRaiz="<%=pathRaizUpload%>"
						arquivo="<%=arquivo%>"
						desabilitado="<%=_disabled%>"
						obrigatorio="<%=_obrigatorio%>"
					/>
				</table>								
			</td>
		</tr>
		
		
		
<%  
			validarOrdens.append("\t if (form.exibeMonitoramentoLink").append(link.getCodLink().toString()).append(".checked == true) { \n");
			validarOrdens.append("\t if (!validaString(form.ordemLink").append(link.getCodLink().toString()).append(", \"Ordem\", true)) { \n");
			validarOrdens.append("\t\t return(false); \n");
			validarOrdens.append("\t } else {\n");
			validarOrdens.append("\t\t if (!validaNum(form.ordemLink").append(link.getCodLink().toString()).append(", \"Ordem\", false)) { \n");
			validarOrdens.append("\t\t\t alert(\"").append("O valor deve ser inteiro!").append("\"); \n");
			validarOrdens.append("\t\t\t form.ordemLink").append(link.getCodLink().toString()).append(".focus(); \n");
			validarOrdens.append("\t\t\t return(false); \n");
			validarOrdens.append("\t\t } \n");
			validarOrdens.append("\t } \n");
			
			validarLabels.append("\t if (form.exibeMonitoramentoLink").append(link.getCodLink().toString()).append(".checked == true) { \n");
			validarLabels.append("\t if (!validaString(form.labelLink").append(link.getCodLink().toString()).append(", \"Label\", true)) { \n");
			validarLabels.append("\t\t return(false); \n");
			validarLabels.append("\t } \n");
			validarLabels.append("\t } \n");
			validarOrdens.append("\t } \n");
			
			//caminhoImg = "";
	}%>

	<!--
	<tr>
		<td>&nbsp;</td>
	</tr>
	
 
	<tr>
		<td><H1><b>Filtros</b></H1></td>
	</tr>	
	<tr class="linha_subtitulo">
		<td width="35%">Nome</td>
		<td colspan="6" width="55%">Posi&ccedil;&atilde;o</td>
	</tr>
	 -->
<%
	//lista = abaDao.listar(Aba.class, new String[]{"codAba", "asc"});
	//it = lista.iterator();
	
	//while (it.hasNext()) {
		//Aba aba = (Aba) it.next();
		
		//if ("NIVEL_PLANEJAMENTO".equals(aba.getNomeAba())) {
		%>
		<!--  
			<tr>
				<td><%//=aba.getNomeAba()%></td>
				<td align="center">
					<input type="checkbox" class="form_check_radio" name="exibePosicaoAba<%//=aba.getCodAba()%>" value="S" <%//=Pagina.isChecked(aba.getExibePosicaoAba(), "S")%>>
				</td>
			</tr>
		-->
		<%
		//}
	//}
 %>	
	
	<tr>
		<td>&nbsp;</td>
	</tr>
	
<script language="JavaScript">
function validarOrdens(form){
<%=validarOrdens.toString()%>
	return(true);
}
function validarLabels(form){
<%=validarLabels.toString()%>
	return(true);
}

function Funcao(codigo, nome){  
	this.codigo = codigo;
	this.nome = nome;
}


var arrayFuncoes =  new Array();

<%
	Iterator itf = funcoes.iterator();
	int k =0;
	while (itf.hasNext()){
		FuncaoFun funcao = (FuncaoFun) itf.next();
%>
	arrayFuncoes[<%= k++%>] = new Funcao('<%= funcao.getCodFun()%>', '<%= funcao.getLabelPadraoFun()%>' );
<%} // fim while%> 

function mostraEscondeLabel(codAba){
	funAbaSel = document.getElementById('funAba'+codAba);
	if (funAbaSel.value !="") {
	     document.getElementById('labelAba'+codAba).style.display='none';
	     document.getElementById('labelFun'+codAba).style.display='inline';
	     
	     opts = getSelections(funAbaSel);
	     
	     if (opts.length > 0) {
	     	selecionado = opts[0].value; 
	     	for (var k=0; k < arrayFuncoes.length;k++ ) {
	     		var funcao = arrayFuncoes[k];
	     		if (funcao.codigo == selecionado ) {	
	     			document.getElementById('labelFun'+codAba).value=funcao.nome;
	     		}
	     	}//fim for
	     }//fim if
	     
	} else {
	     document.getElementById('labelAba'+codAba).style.display='';
	     document.getElementById('labelFun'+codAba).style.display='none';
	}
}

function validaPosicaoAbas(){

	var checkPosicaoAba = null;
	
	<%
	Iterator itAbas = lista.iterator();
	
	while (itAbas.hasNext()){
		
		Aba aba = (Aba) itAbas.next();
		
		if (!"NIVEL_PLANEJAMENTO".equals(aba.getNomeAba()) && !aba.getNomeAba().equals("SITUACAO_INDICADORES")) {
	%>
	
	checkPosicaoAba = document.getElementById('exibePosicaoAba' + <%=aba.getCodAba()%>);
				
	if(checkPosicaoAba.checked == true) {		
		return true;
	}

	<%
		}
	}
	%>
	
	alert("<%=_msg.getMensagem("aba.validacao.posicaoAba.obrigatorio")%>");
	
	return false;
}

</script>
	
	<%@ include file="/include/estadoMenu.jsp"%>
	
</table>