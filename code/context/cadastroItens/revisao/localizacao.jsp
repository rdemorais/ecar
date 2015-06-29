<%
		FuncaoFun funcaoLoc = new FuncaoFun();
		EstruturaFuncaoEttf etfLoc = new EstruturaFuncaoEttf();
		funcaoLoc.setNomeFun("Localizacao");
		List pesquisaLoc = new FuncaoDao(request).pesquisar(funcaoLoc, new String[]{"nomeFun","asc"});
		if ((pesquisaLoc != null) && (pesquisaLoc.size() > 0))
		{
			FuncaoFun fLoc = (FuncaoFun) pesquisaLoc.iterator().next();
			etfLoc.setComp_id(new EstruturaFuncaoEttfPK(estrutura.getCodEtt(), fLoc.getCodFun()));
			List pesquisaEttfLoc = new EstruturaFuncaoDao(request).pesquisar(etfLoc, new String[]{"labelEttf","asc"});
			if ((pesquisaEttfLoc != null) && (pesquisaEttfLoc.size() > 0))
			{
				etfLoc = (EstruturaFuncaoEttf) pesquisaEttfLoc.iterator().next();
				String codAbaLocal = fLoc.getCodFun().toString();
	ItemEstLocalRevIettlrDAO  itemEstrutLocalDao = new ItemEstLocalRevIettlrDAO(request);

	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
%>

<script>

function aoClicarExcluirLocal(form){
	if(validarExclusao(form, "excluirLocal")){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		form.hidAcao.value = "excluir";
		form.action = "local/ctrl.jsp";
		form.submit();	
	}
}	

function aoClicarIncluirLocal(form){
	if(form.hidGravado.value != "S")
		alert("Antes de inserir local é necessário GRAVAR a revisão\nFaça isto clicando em \"Gravar\"");
	else
	{
		var variavel_undefined;
		form.lgpSelecionado.value = "N";	
		popup_pesquisa('ecar.popup.PopUpLocalItem', variavel_undefined, '&codLgp='+form.codLgp.value);
	}
}

function aoClicarListarLocal(form){
	if(form.hidGravado.value != "S")
		alert("Antes de inserir localização é necessário GRAVAR a revisão\nFaça isto clicando em \"Gravar\"");
	else
	{
		if (form.codLgp.value == '')
			alert("Selecione um Grupo de Locais");
		else
		{
			form.lgpSelecionado.value="S";
			form.submit();
		}
	}
}

function getDadosPesquisa(codigo, descricao){
	form.codLit.value = codigo;
	form.identificacaoLit.value = descricao;
	form.lgpSelecionado.value="N";
	form.hidAcao.value="incluir";
	form.action='local/ctrl.jsp';
	form.submit();
}

</script>

<input type="hidden" name="codLit" value="">
<input type="hidden" name="identificacaoLit" value="">
<input type="hidden" name="lgpSelecionado" value="">
<input type="hidden" name="codIettirr" value="">
<input type="hidden" name="codAbaLocal" value="<%=codAbaLocal%>">


<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador" colspan=3><img src="../../images/pixel.gif"></td></tr>
	<tr>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador" colspan=3><img src="../../images/pixel.gif"></td></tr>
	<tr class="linha_titulo" >
	<td>
		<h1><%=etfLoc.getLabelEttf()%></h1>
	</td>
<%	
	boolean iettlrNull = false;
	if ("S".equals(Pagina.getParamStr(request, "lgpSelecionado"))) {%>	
	<script>
		var variavel_undefined;
		form.lgpSelecionado.value = "N";	
		popup_pesquisa('ecar.popup.PopUpLocalItem', variavel_undefined, '&codLgp=<%=Pagina.getParamStr(request, "codLgp")%>');
	</script>	
<%
	}else{
		if ((ieRevisao.getItemEstLocalRevIettlrs() == null)){	
			iettlrNull = true;
		}
	}
	if (iettlrNull || (ieRevisao.getItemEstLocalRevIettlrs().size() < 1))
	{	
%>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<%if (!desabilitar){%>	
		<tr class="linha_subtitulo" align="center">
		<td>
			<combo:ComboTag 
					nome="codLgp"
					objeto="ecar.pojo.LocalGrupoLgp"
					label="identificacaoLgp"
					value="codLgp"
					filters="indAtivoLgp=S"
					order="identificacaoLgp"
			/>		&nbsp;	<input type="button" class="botao" value="Listar" onclick="javascript:aoClicarListarLocal(form);">
			
		</td>
	</tr>
	<tr class="linha_subtitulo" align="center">
		<td>
			Para inserir um outro local relativo a outra abrangência,<br>
			é necessário excluir todos os locais já relacionados.<br>
			Somente é possivel uma abrangência por item.
		</td>
	</tr>
	<tr><td class="espacador"><img src="../../images/pixel.gif"></td></tr>
	</tr>
	
	
<%	}
} else {
%>
		<input type="hidden" name="codLgp" value="">
		<td align="right">
		<%if (!desabilitar){
		%>
			<input type="button" class="botao" value="Adicionar revisão em <%=etfLoc.getLabelEttf()%>" onclick="javascript:aoClicarIncluirLocal(form);">&nbsp;
			<input type="button" class="botao" value="Excluir revisão em <%=etfLoc.getLabelEttf()%>" onclick="javascript:aoClicarExcluirLocal(form);">&nbsp;
		<%}%>
		</td>
	</tr>

	</table>

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador" colspan="3"><img src="../../images/pixel.gif"></td></tr>
	<tr class="linha_subtitulo">
		<td width="2%">
		<%if (!desabilitar){%>	
			<input type=checkBox class="form_check_radio" name="todosLocal" onclick="javascript:selectAll(document.form, 'todosLocal', 'excluirLocal')"></td>
		<%}%>
		<td>Identificação</td>
		<td>Abrangência:&nbsp;<%=itemEstrutLocalDao.getAbrangencia(ieRevisao.getCodIettrev())%></td>
	</tr>
	<tr><td class="espacador" colspan="3"><img src="../../images/pixel.gif"></td></tr>

<%
	if (ieRevisao.getItemEstLocalRevIettlrs() != null){
    	List lista = ieRevisaoDao.ordenaSet(ieRevisao.getItemEstLocalRevIettlrs(), "this.localItemLit.identificacaoLit", "asc");
		
		Iterator it = lista.iterator();	
		
		ItemEstLocalRevIettlr itemEstrutLocal = new ItemEstLocalRevIettlr();
		
		while (it.hasNext()) {
			itemEstrutLocal = (ItemEstLocalRevIettlr) it.next();
%>		
			<script language="JavaScript">
				form.codLgp.value = "<%=itemEstrutLocal.getLocalItemLit().getLocalGrupoLgp().getCodLgp()%>";
			</script>
			<tr class="linha_subtitulo2">
				<td width="2%">
				<%if (!desabilitar){%>					
					<input type="checkbox" class="form_check_radio" name="excluirLocal" value="<%=itemEstrutLocal.getLocalItemLit().getCodLit()%>">
				<%}%>
				</td>
				<td colspan="2">
					<%=itemEstrutLocal.getLocalItemLit().getIdentificacaoLit()%>
				</td>
			</tr>
<%
		}
	}
%>
	</table>
<%
}
}
}
%>