<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 

<jsp:directive.page import="ecar.pojo.AgendaAge"/>
<jsp:directive.page import="ecar.dao.AgendaDao"/>
<jsp:directive.page import="ecar.pojo.ItemEstrutEntidadeIette"/>
<jsp:directive.page import="comum.util.Pagina"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.pojo.AgendaEntidadesAgeent"/>
<jsp:directive.page import="ecar.pojo.EntidadeEnt"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="ecar.pojo.SisGrupoAtributoSga"/>
<jsp:directive.page import="ecar.pojo.ConfiguracaoCfg"/>
<jsp:directive.page import="ecar.dao.SisGrupoAtributoDao"/>
<jsp:directive.page import="ecar.pojo.SisAtributoSatb"/>
<jsp:directive.page import="ecar.pojo.AtributoLivre"/>
<jsp:directive.page import="ecar.pojo.ItemEstrtIndResulIettr"/>
<jsp:directive.page import="ecar.dao.ItemEstrtIndResulIettrDao"/>
<jsp:directive.page import="java.util.Set"/>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>

<%
	String codEnt = new String();
	String nomeEnt = new String();
	
	ItemEstrutEntidadeIette itemEstrutEntidade = new ItemEstrutEntidadeIette();
	if (itemEstrutEntidade.getEntidadeEnt() != null){
		codEnt = itemEstrutEntidade.getEntidadeEnt().getCodEnt().toString();
		nomeEnt = itemEstrutEntidade.getEntidadeEnt().getNomeEnt();
	}else{
		codEnt = "";
		nomeEnt = "";
	}

	AgendaDao agendaDao = null; 
	AgendaAge agenda = new AgendaAge();// (AgendaAge) agendaDao.buscar(AgendaAge.class, Long.valueOf(Pagina.getParamStr(request, "codAge")));

	if(!Pagina.getParamStr(request, "codAge").equals("")) {
		agendaDao = new AgendaDao(request); 
		agenda = (AgendaAge) agendaDao.buscar(AgendaAge.class, Long.valueOf(Pagina.getParamStr(request, "codAge")));
	}
	
	if(session.getAttribute("objAgenda") != null){
		agenda = (AgendaAge) session.getAttribute("objAgenda");
		session.removeAttribute("objAgenda");
	}

%>


	<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
		<tr> 
			<td class="label"><%=_obrigatorio%> <label for="eventoAge"> Evento</label></td>
			<td>	
				<input type="text" name="eventoAge" id="eventoAge" size="63" maxlength="200" value="<%=Pagina.trocaNull(agenda.getEventoAge())%>" <%=_disabled %>>
				<input type="hidden" name="codAge" title="Nome do Evento" value="<%=Pagina.trocaNull(agenda.getCodAge()) %>">
			</td>
		</tr>
		<tr>
			<td class="label"><label for="dataAge"> <%=_obrigatorio%> Per&iacute;odo Proposto In&iacute;cio </label></td>
			<td>
				<input type="text" id="dataAge" name="dataAge" size="14" title="Ciclo Proposto Início" maxlength="10" value="<%=Pagina.trocaNullData(agenda.getDataAge())%>" <%=_disabled%> onkeypress="return txtBoxFormat(this, '99/99/9999', event);">
				<c:if test="<%=_disabled.equals("")%>">
					<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataAge, '')">
				</c:if>
			</td>	
		</tr>
		<%if (!tipoAgenda.equals("P")){  %>
		<tr>
			<td class="label"><label for="dataLimiteAge"><%=_obrigatorio%> Per&iacute;odo Proposto Fim </label></td>
			<td>
				<input type="text" name="dataLimiteAge" id="dataLimiteAge" title="Ciclo Proposto Fim" size="14" maxlength="10" value="<%=Pagina.trocaNullData(agenda.getDataLimiteAge())%>" <%=_disabled%> onkeypress="return txtBoxFormat(this, '99/99/9999', event);">
				<c:if test="<%=_disabled.equals("")%>">
					<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataLimiteAge, '')">
				</c:if>
			</td>
		</tr>
		<%} %>
		<tr>
			<td class="label"><label for="horaEventoAge"><%=_obrigatorio%> Hor&aacute;rio </label></td>
			<td>
				<input type="text" name="horaEventoAge" id="horaEventoAge" tile="Hora do Horário" size="4" maxlength="2" value="<%=Pagina.getZeroAEsquerda(Pagina.trocaNull(agenda.getHoraEventoAge()))%>" <%=_disabled%> onkeypress="return txtBoxFormat(this, '99', event);"> :&nbsp;
				<input type="text" name="minutoEventoAge" id="minutoEventoAge" tile="Minuto do Horário"  size="4" maxlength="2" value="<%=Pagina.getZeroAEsquerda(Pagina.trocaNull(agenda.getMinutoEventoAge()))%>" <%=_disabled %> onkeypress="return txtBoxFormat(this, '99', event);">
			</td>
		</tr>
		<tr>
			<td class="label">	<label for="localAge"> <%=_obrigatorio%> Local</label> </td>
			<td>
				<textarea name="localAge" id="localAge" rows="2" cols="60" tile="Local" <%=_disabled%>  
					onkeyup="return validaTamanhoLimite(this, 2000);"
					onkeydown="return validaTamanhoLimite(this, 2000);"
					onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(agenda.getLocalAge())%></textarea>
			</td>
		</tr>

<% String trueFalse=(_disabled.trim().equals("") ?"false":"true"); %>
        <tr>
            <td class="label"><%=_obrigatorio%> Descri&ccedil;&atilde;o</td>
            <td>
               <script language="JavaScript" type="text/javascript">
               //Usage: initRTE(imagesPath, includesPath, cssFile, genXHTML)
               initRTE("<%=_pathEcar%>/images/rte/", "<%=_pathEcar%>", "", false);
               </script>
               
               <script language="JavaScript" type="text/javascript">
               //Usage: writeRichText(fieldname, html, width, height, buttons, readOnly)
               //writeRichText('rte', 'enter body text here', 400, 200, true, false);
               writeRichText('rte', "<%=Pagina.trocaNull(agenda.getDescricaoAge()).replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("</SCRIPT>","&lt;/SCRIPT&gt;").replaceAll("</script>","&lt;/script&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</SCRIPT -->","&lt;/SCRIPT --&gt;")%>", 400, 200, true, <%= trueFalse%> );
               
               </script>       
              
               <input type="hidden" name="richText" id="richText" value="" />
               <input type="hidden" name="descricaoAge" value=""/>
               
<%--           <textarea name="descricaoAge" rows="4" cols="60" onkeyup="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(agenda.getDescricaoAge())%></textarea>--%>
            </td>
        </tr>
		

		<tr> <td colspan="1" class="label"> <label for="contNome"> Contato: </label></td></tr>
		<tr>
			<td class="label" valign="top" ><label for="contNome"> Nome</label></td>
			<td> 
				<input type="text" name="nomeContato" id="contNome" title="Nome do contato" value="<%=Pagina.trocaNull(agenda.getNomeContato()) %>" <%=_disabled %>>
			</td>
		</tr>
		<tr>
			<td class="label" valign="top" ><label for="contTelefone""> Telefone</label></td>
			<td> 
				<input type="text" name="telefoneContato" id="contTelefone" title="Telefone do contato" maxlength="13" value="<%=Pagina.trocaNull(agenda.getTelefoneContato()) %>" <%=_disabled %> onkeypress="return txtBoxFormat(this, '(99)9999-9999', event);" >
			</td>
		</tr>
		<tr>
			<td class="label" valign="top" ><label for="contOrgao""> &Oacute;rg&atilde;o </label></td>
			<td> 
				<input type="text" name="orgaoContato" id="contOrgao" value="<%=Pagina.trocaNull(agenda.getOrgaoContato()) %>" <%=_disabled %> title="Órgão do contato" >
			</td>
		</tr>
		<tr> 
			<td class="label" colspan="2" >&nbsp; 	</td>
		</tr>
		<tr>
			<td class="label" valign="top" ><label for="comentario"> Coment&aacute;rio  </label></td>
			<td> 
				<textarea name="comentario" rows="2" cols="60" id="comentario"  <%=_disabled %> 
					onkeyup="return validaTamanhoLimite(this, 200);"
					onkeydown="return validaTamanhoLimite(this, 200);"
					onblur="return validaTamanhoLimite(this, 200);"><%=Pagina.trocaNull(agenda.getComentario())%></textarea>
			</td>
		</tr>
		<%	//Recuperar Tipo de Evento na tabela de Atributos do SistemaS
			SisGrupoAtributoSga tipoEventoSga = configuracao.getSisGrupoAtributoSgaByTipoEvento();
			Iterator itTipoEventoSatb=null;
			if (tipoEventoSga!=null)
			 itTipoEventoSatb = tipoEventoSga.getSisAtributoSatbs().iterator() ;
		
		if( itTipoEventoSatb!=null && itTipoEventoSatb.hasNext() ){
		%>		
		<tr>

			<td class="label" ><label for="tipoEventoSatb"> <%= tipoEventoSga.getIndObrigatorioSga().equalsIgnoreCase("S")?_obrigatorio:"" %> <%= tipoEventoSga.getDescricaoSga() %> </label></td>
			<td> 
				<select id="tipoEventoSatb" name="tipoEventoSatb" <%= _disabled %>>
					<option value="" >  </option>
		<%	
			String codSelect="";
			
			if (agenda.getTipoEventoSatb()!= null)
				codSelect = agenda.getTipoEventoSatb().getCodSatb().toString();
			
			while(itTipoEventoSatb.hasNext()){
				SisAtributoSatb tipoEvento = (SisAtributoSatb) itTipoEventoSatb.next();
				String codigo =   tipoEvento.getCodSatb().toString();
			%>
					<option <%= Pagina.isSelected(codSelect, codigo ) %>   value="<%=codigo %>"  >  <%=tipoEvento.getDescricaoSatb() %>  </option>
			<% 
			}//fim while
			%>
			 	</select>
			</td >
		</tr>

	<%
	}//fim if tipo de evento
	
	//Recuperar lista de Atores envolvidos
	Iterator itAgeEnt =null;
	if (agenda.getAgendaEntidadesAgeent()!=null)
	 	itAgeEnt = agenda.getAgendaEntidadesAgeent().iterator();
		
	 boolean exibeExcluir=false;
	 //1 e 1
	 if (itAgeEnt!= null && itAgeEnt.hasNext() ) {
	 	exibeExcluir=true;
	 }
	 %> 

		<tr id="labelAtores">
			<td class="label" rowspan="2">Ator(es) Principal(is) Presente(s) ao Evento </td>
			<td style="height:25px;vertical-align: bottom; " > 
			<%if (_disabled.equals("")) {%>
				<a href="#" onClick="popup_pesquisa_check('ecar.popup.PopUpEntidade')";> Adicionar Ator(es) Presente(s) </a>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a id="lbAtorExcluir" href="#" onClick="removeAtor();"><%= (exibeExcluir?"Excluir":"")  %></a>
			<%}%>
			</td>
		</tr>
		<tr>
			<!-- td >&nbsp; </td-->
			<td id="ator"> 
			 
			 </td>
	<%//Exibe lista de Atores envolvidos
	while (itAgeEnt!= null && itAgeEnt.hasNext() ) {
		AgendaEntidadesAgeent ageEnt =  (AgendaEntidadesAgeent)itAgeEnt.next();
		EntidadeEnt entidade = ageEnt.getEntidadeEnt();
		//chama o script que adiciona e exclue atores na tela.
		out.println("<script type=\"text/javascript\"> adicionarAtor("+ entidade.getCodEnt()+", \""+entidade.getNomeEnt()+"\" );</script>");
	}
	 %>			 
		</tr>
		

		 
<%
	if (tipoAgenda.equals("P") && operacao.equals("incluir") ){ //portal %>				
		<tr>
			<td class="label" valign="top"><%=_obrigatorio%> Periodicidade</td>
			<td>
				<input type="radio" class="form_check_radio" name="opcaoPrdc" value="<%=agendaDao.PRDC_UNICO%>">
				Único <br>
				<input type="radio" class="form_check_radio" name="opcaoPrdc" value="<%=agendaDao.PRDC_SEMANAL%>">
				Semanal&nbsp;&nbsp;
					<input type="checkbox" class="form_check_radio" name="diasSemanaPrdc" value="1"> D&nbsp;
					<input type="checkbox" class="form_check_radio" name="diasSemanaPrdc" value="2"> S&nbsp;
					<input type="checkbox" class="form_check_radio" name="diasSemanaPrdc" value="3"> T&nbsp;
					<input type="checkbox" class="form_check_radio" name="diasSemanaPrdc" value="4"> Q&nbsp;
					<input type="checkbox" class="form_check_radio" name="diasSemanaPrdc" value="5"> Q&nbsp;
					<input type="checkbox" class="form_check_radio" name="diasSemanaPrdc" value="6"> S&nbsp;
					<input type="checkbox" class="form_check_radio" name="diasSemanaPrdc" value="7"> S&nbsp;
					<br>
				<input type="radio" class="form_check_radio" name="opcaoPrdc" value="<%=agendaDao.PRDC_QUINZENAL%>">
				Quinzenal <br>
				<input type="radio" class="form_check_radio" name="opcaoPrdc" value="<%=agendaDao.PRDC_MENSAL%>">
				Mensal <br>
				<input type="radio" class="form_check_radio" name="opcaoPrdc" value="<%=agendaDao.PRDC_ANUAL%>">
				Anual <br>
				<input type="radio" class="form_check_radio" name="opcaoPrdc" value="<%=agendaDao.PRDC_OUTRO%>">
				Outro&nbsp;&nbsp;
					<input type="text" name="diasPrdc" size="4" maxlength="2" value=""> Dias
			</td>
		</tr>
		
		<tr>
			<td class="label"><%=_obrigatorio%> Data Limite</td>
			<td>
				<input type="text" name="dataLimiteAge" size="14" maxlength="10" value="<%=Pagina.trocaNullData(agenda.getDataLimiteAge())%>" onkeypress="return txtBoxFormat(this, '99/99/9999', event);">
				<c:if test="<%=_disabled.equals("")%>">
					<img class="posicao" title="Selecione a data" src="../../images/icone_calendar.gif" onclick="open_calendar('data', document.forms[0].dataLimiteAge, '')">
				</c:if>
			</td>
		</tr>
		<%}	if (tipoAgenda.equals("P") && operacao.equals("alterar") ){ //portal %>
			<input type="hidden" name="dataLimiteAge" id="dataLimiteAge" value="<%=Pagina.trocaNullData(agenda.getDataLimiteAge())%>" >
		<%} if (tipoAgenda.equals("P")){
				boolean indAtivo=(Pagina.trocaNull(agenda.getIndAtivoAge()).equalsIgnoreCase("S")?true:false);
		%>
		<tr>
			<td class="label" valign="top"> <label for="ativo"> Ativo </label></td>
			<td>
			    <!-- mudei -->
			    <!--input type="checkbox" class="form_check_radio" id= "ativo" name="ativo" checked="true" onclick="respostaChk('ativo','respAtivo')"> <spam id="respAtivo" > Sim </span>!-->
				<input type="checkbox" class="form_check_radio" id= "ativo" name="ativo" <%= indAtivo?"checked":" " %> > Sim				
			</td>
		</tr>
		<%}
		
		
		
		
			
		if (!tipoAgenda.equalsIgnoreCase("P")){//if exibir no portal
			String chkExibirPortalNao = "checked";
			String chkExibirPortalSim="";
		 
			if ((Pagina.trocaNull(agenda.getExibirPortal())).equalsIgnoreCase("S")){
				chkExibirPortalNao = "";
				chkExibirPortalSim="checked";
			}
		%>
		
		
		<%-- tr>
			<td class="label" valign="top"><label for="exibirPortalNao"> Exibir no Portal </label></td>
			<td>
				<input type="radio" class="form_check_radio" id="exibirPortalSim" name="exibirPortal"  value="S" <%=chkExibirPortalSim %> <%=_disabled %>> Sim &nbsp;&nbsp;&nbsp;
				<input type="radio" class="form_check_radio" id="exibirPortalNao" name="exibirPortal"  value="N" <%=chkExibirPortalNao %> <%=_disabled %>>N&atilde;o
			</td>
		</tr --%>
		
		<%	
			}//fim if exibir no portal
			String  visibility="";
			boolean realizado=(Pagina.trocaNull(agenda.getRealizado()).equalsIgnoreCase("S")?true:false);
			visibility = (realizado?"visible":"hidden");
			String vlrTabelaRealizado = (realizado?"S":"N");
		
		 %>		
		<tr>
			<td class="label" valign="top" ><label for="rel"> Realizado </label></td>
			<td style="vertical-align: top"> 
				<!-- input type="radio" class="form_check_radio" name="realizado" value="S" id="relS" </%=_disabled %> </%= realizado?"checked":" " %> onclick="exibe('lbDtRel');exibe('txDtRel');apagaCampo('dtRel');"/> Sim &nbsp;&nbsp;&nbsp;!-->
				<!-- input type="radio" class="form_check_radio" name="realizado" value="N" id="relN" </%=_disabled %> </%= realizado ? " " :"checked" %> onclick="esconde('lbDtRel');esconde('txDtRel');"/> N&atilde;o-->
				<input type="checkbox" class="form_check_radio" name="realizado"  <%=_disabled %> <%= realizado?"checked":" " %>  value= <%=vlrTabelaRealizado %> id="relS" onclick="verificaRealizado(this,'lbDtRel','txDtRel','dtRel');"> Sim
			</td>
		</tr>
		<tr>
			<td class="label" valign="top" id= "lbDtRel" style="visibility:<%=visibility%>;">
				<label for="dtRel"><%=_obrigatorio %> Data da Realiza&ccedil;&atilde;o </label>
			</td>
			<td style="visibility: <%=visibility%>;" valign="top" id="txDtRel"> 
				<input type="text" name="dataRealizado" title="Data da Realização" id="dtRel" maxlength="10" value="<%=Pagina.trocaNullData(agenda.getDataRealizado()) %>" <%=_disabled %>  onkeypress="return txtBoxFormat(this, '99/99/9999', event);" /> 
			</td> 	
		</tr>
		
		<!-- tr><td class="label">&nbsp;</td></tr-->
		
<script>


function verificaRealizado(ckRealizado,lbDtRel, txDtRel, dtRel) {
	if(ckRealizado.checked==true) {
		exibe(lbDtRel);
		exibe(txDtRel);
		apagaCampo(dtRel);
		ckRealizado.value="S";
	} else {
		
		escondeCampoDtRealizado(lbDtRel);
		escondeCampoDtRealizado(txDtRel);
		apagaCampo(dtRel);
		ckRealizado.value="N";
		
	}
	
}

// pra nao dar conflito com o esconde do menu_retratil
function escondeCampoDtRealizado(elemento) {
	elem= document.getElementById(elemento);
	elem.style.visibility='hidden';

}


function validaTipo(){

	<%//as duas primeiras comparações é para que não dê java.lang.NullPointerException
		if(tipoEventoSga!=null&& tipoEventoSga.getIndObrigatorioSga()!=null && tipoEventoSga.getIndObrigatorioSga().equalsIgnoreCase("S") ) { %>
	if (document.getElementById('tipoEventoSatb')){
		tipoEventoSatb = document.getElementById('tipoEventoSatb');
		if (tipoEventoSatb.value==''){
			tipoEventoSatb.focus();
			alert("É preciso informar <%= tipoEventoSga.getDescricaoSga() %>.");
			return false;
		}
	}
	<%}%>
	return true;
}
</script>