<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>
<%
try{
//ecar.pojo.ConfiguracaoCfg configura = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();

String indTipoRelatorio = Pagina.getParamStr(request, "indTipoRelatorio");

%>
<form name="form" method="post">
	<%
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
	/* TODO: Remover esta validação quando o processo PPA terminar.*/
	if("RPPA".equals(indTipoRelatorio)){
		indTipoRelatorio = "R";
		%>
		<input type="hidden" name="contextoEcarPPA" value="S">
		<%
	}
	%>
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">
	<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<input type="hidden" name="codIett" value="<%=Pagina.getParamStr(request, "codIett")%>">	
	<input type="hidden" name="titulo_sistema" value="<%=configuracaoCfg.getTituloSistema()%>">
	<input type="hidden" name="indTipoRelatorio" value="<%=indTipoRelatorio%>">
	<input type="hidden" name="todosCheck" value="<%=Pagina.getParamStr(request, "todosCheck")%>">
	<input type="hidden" name="codOrgaoSelecionado" value="<%=Pagina.getParamStr(request, "codOrgaoSelecionado")%>">
	<input type="hidden" name="criteriosIncluidosCom" value="<%=Pagina.getParamStr(request, "criteriosIncluidosCom")%>">
	<input type="hidden" name="criteriosIncluidosSem" value="<%=Pagina.getParamStr(request, "criteriosIncluidosSem")%>">
	<input type="hidden" name="situacoesIncluidas" value="<%=Pagina.getParamStr(request, "situacoesIncluidas")%>">
	<input type="hidden" name="imprimirEstrutura" value="<%=Pagina.getParamStr(request, "imprimirEstrutura")%>">
	<input type="hidden" name="codEttImprimir" value="<%=Pagina.getParamStr(request, "codEttImprimir")%>">
	<input type="hidden" name="codIettPaiImprimir" value="<%=Pagina.getParamLong(request, "codIettPaiImprimir")%>">
	<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>
	
<%
    // passa para o servlet todos os checkbox marcados
	String[] filhos = request.getParameterValues("itemFilho");
	if (filhos != null) {
		for (int i = 0; i < filhos.length; i++) {
%>
			<input type="hidden" name="itemFilho" value="<%=filhos[i]%>"><br>
<%
		}
	}
%>	
	
	

</form>

<%
	String msg = "";
%>

	
	<SCRIPT language="javascript">
	<%  
		if("imprimir".equals(Pagina.getParamStr(request, "hidAcao"))){
			if("L".equals(indTipoRelatorio)){
			%>
				document.form.action = "<%=request.getContextPath()%>/RelacaoItemEstrutura";
			<%
			}
			
			else if ("RE".equals(indTipoRelatorio)){
			%>
				document.form.action = "<%=request.getContextPath()%>/RevisaoItemEstrutura";
			<%
			}

			else if ("REMF".equals(indTipoRelatorio)){
				%>
					document.form.action = "<%=request.getContextPath()%>/RevisaoItemEstruturaMetasFisicas";
				<%
			}

			else {
			%>
				document.form.action = "<%=request.getContextPath()%>/RelatorioItemEstrutura";
			<%
			}
		} else{ 
		%>
			document.form.action = "";
		<%
		}
		%>		
		document.form.submit();
	</SCRIPT>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</body>
</html>

