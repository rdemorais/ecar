
<jsp:directive.page import="ecar.pojo.AcompRealFisicoArf"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.AcompRealFisicoDao" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
</head>   
<body>
<form name="form" method="post">  

<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAri" value="<%=Pagina.getParamStr(request, "codAri")%>"> 
<input type="hidden" name="subNiveis" value="<%=Pagina.getParamStr(request, "subNiveis")%>">
<input type="hidden" name="primeiroItemClicado" value="<%=Pagina.getParamStr(request, "primeiroItemClicado")%>">
<input type="hidden" name="primeiroItemAriClicado" value="<%=Pagina.getParamStr(request, "primeiroItemAriClicado")%>">
<input type="hidden" name="codAcomp" value="<%=Pagina.getParamStr(request, "codAcomp")%>">
<input type="hidden" name="codAriSubNivel" value="<%=Pagina.getParamStr(request, "codAriSubNivel")%>">
<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
<input type="hidden" name="radConcluido" value="<%=Pagina.getParamStr(request, "radConcluido")%>">
<!-- campo hidden para nao perder variaveis necessarias ao botao avançar e retroceder-->
<input type="hidden" name="codArisControle" value="<%=Pagina.getParamStr(request, "codArisControle")%>">
<input type="hidden" name="itemDoNivelClicado" value="<%=Pagina.getParamStr(request, "itemDoNivelClicado")%>">
<input type="hidden" name="periodo" value="<%=Pagina.getParamStr(request, "periodo")%>">
<input type="hidden" name="clicouAba" value="<%= Pagina.getParamStr(request, "clicouAba")%>">
<input type="hidden" name="codArisControleVisualizacao"	value="<%=Pagina.getParamStr(request, "codArisControleVisualizacao")%>">


<input type="hidden" name="mesReferencia" value="<%=Pagina.getParamStr(request, "mesReferencia")%>">
<input type="hidden" name="codigosItem" value="<%=Pagina.getParamStr(request, "codigosItem")%>">
<input type="hidden" name="semInformacaoNivelPlanejamento" value="<%=Pagina.getParamStr(request, "semInformacaoNivelPlanejamento")%>" >
<input type="hidden" name = "tela" value="<%=Pagina.getParamStr(request, "tela") %>">
<input type="hidden" name="codTipoAcompanhamento" value="<%= Pagina.getParamStr(request, "codTipoAcompanhamento") %>">
<input type="hidden" name="primeiroIettClicado" value="<%= Pagina.getParamStr(request, "primeiroIettClicado") %>">
<!-- Campo necessario para guardar a forma de visualização escolhida no filtro -->
<input type="hidden" name="hidFormaVisualizacaoEscolhida" value='<%=Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida")%>'>
<input type="hidden" name="tipoPadraoExibicaoAba" value="<%= Pagina.getParamStr(request, "tipoPadraoExibicaoAba")%>">

<%
    String nivel = Pagina.getParamStr(request, "hidNivel");
	String msg = "";
	String submit = "";
	AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
	if ("filho".equals(nivel))
	{
		try{
			AcompRealFisicoArf acompRealFisicoArf = new AcompRealFisicoArf();
			int countSelecionado = Pagina.getParamInt(request, "hidContSelecionado"); //numero de linhas
			int countFilhoSelecionado = Pagina.getParamInt(request, "hidContFilhoSelecionado");

			acompRealFisicoArf = acompRealFisicoDao.buscar(Long.valueOf(Pagina.getParamStr(request, "codArf" + countSelecionado + "-" + countFilhoSelecionado)));
			acompRealFisicoDao.setAcompRealFisicoFilho(request, acompRealFisicoArf);

			//Se o indicador usar serviço e optou por limpar o valor no banco de dados			
			if (acompRealFisicoArf.getItemEstrtIndResulIettr().getRealizadoServicoSer()!= null && Pagina.getParamStr(request, "acao").equals("limpar")){
				acompRealFisicoArf.setQtdRealizadaArf(null);
				acompRealFisicoArf.setSituacaoSit(null);
			}
			if (Pagina.getParamStr(request, "acao").equals("limpar")){
				acompRealFisicoDao.alterar(acompRealFisicoArf);
				msg = _msg.getMensagem("acompanhamento.realizadoFisico.atualizacao.sucesso");
				submit = "indicadoresResultado.jsp";
			}
			else 
				if (Pagina.getParamStr(request, "acao").equals("excluir")){
					acompRealFisicoDao.excluir(acompRealFisicoArf);				
					msg = _msg.getMensagem("acompanhamento.realizadoFisico.exclusao.exclusaoSucesso");
					submit = "indicadoresResultado.jsp";				
				}
				else{			
					acompRealFisicoDao.alterar(acompRealFisicoArf);
					msg = _msg.getMensagem("acompanhamento.realizadoFisico.atualizacao.sucesso");
					submit = "indicadoresResultado.jsp";				
				}
		}
		catch ( ECARException e){
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			submit = "indicadoresResultado.jsp";
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	}
	else
	{
		try{
			AcompRealFisicoArf acompRealFisicoArf = new AcompRealFisicoArf();
			
			int countSelecionado = Pagina.getParamInt(request, "hidContSelecionado");
			acompRealFisicoArf = acompRealFisicoDao.buscar(Long.valueOf(Pagina.getParamStr(request, "codArf" + countSelecionado)));
			acompRealFisicoDao.setAcompRealFisico(request, acompRealFisicoArf);
			
			//Se o indicador usar serviço e optou por limpar o valor no banco de dados			
			if (acompRealFisicoArf.getItemEstrtIndResulIettr().getRealizadoServicoSer()!= null && Pagina.getParamStr(request, "acao").equals("limpar")){
				acompRealFisicoArf.setQtdRealizadaArf(null);
				acompRealFisicoArf.setSituacaoSit(null);
			} 
			
			if (Pagina.getParamStr(request, "acao").equals("excluir")){
				acompRealFisicoDao.excluir(acompRealFisicoArf);
				msg = _msg.getMensagem("acompanhamento.realizadoFisico.exclusao.exclusaoSucesso");
				submit = "indicadoresResultado.jsp";
			}
			else 
				if(Pagina.getParamStr(request, "acao").equals("limpar")){
					acompRealFisicoDao.alterar(acompRealFisicoArf);
					msg = _msg.getMensagem("acompanhamento.realizadoFisico.atualizacao.sucesso");
					submit = "indicadoresResultado.jsp";				
				}
				else{
					acompRealFisicoDao.alterar(acompRealFisicoArf);
					msg = _msg.getMensagem("acompanhamento.realizadoFisico.atualizacao.sucesso");
					submit = "indicadoresResultado.jsp";
				}
			
		}
		catch ( ECARException e){
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			submit = "indicadoresResultado.jsp";
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	}
%>

<script>
	document.form.action = "<%=submit%>";
	document.form.submit();
</script>

</form>
</body>
</html>

