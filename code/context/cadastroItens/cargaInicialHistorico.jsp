<!-- http://localhost:8080/ecar/tabelas/localItem/importarLocais.jsp -->

<%@ page import="ecar.servlet.importaLocal.ImportarLocalIBGE" %>

<%@ include file="/frm_global.jsp"%>


<%@page import="ecar.dao.HistoricoDao"%>
<%@page import="ecar.pojo.ItemEstruturaIett"%>
<%@page import="ecar.pojo.EstruturaEtt"%>
<%@page import="java.util.List"%>
<%@page import="ecar.dao.ItemEstruturaDao"%>
<%@page import="ecar.pojo.historico.HistoricoItemEstruturaIett"%>
<%@page import="ecar.pojo.historico.HistoricoPontoCriticoPtc"%>
<%@page import="ecar.dao.PontoCriticoDao"%><html lang="pt-br">
<head>  
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<script language="javascript">
function aoClicarAtualizar(form){
	document.form.hidAcao.value = "atualizar";
	document.form.submit();
}
</script>
 
</head>

<%
String acao = Pagina.getParamStr(request, "hidAcao");
String msg = "";
%>

<body> 
<div id="conteudo">
<%
	if("atualizar".equals(acao)) {
%>
		<div id="aguarde" class="fontAguardeAtualizaBD">
			&nbsp;Aguarde...
		</div>
<%
		try {
			ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
			PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
			HistoricoDao historicoDao = new HistoricoDao(request);
			System.out.println("QuantidadeItensHistorico Iett: " + historicoDao.getQuantidadeLinhasHistorico(HistoricoItemEstruturaIett.class).intValue());
			System.out.println("getQuantidadeLinhasIettAtivos: " + itemEstruturaDao.getQuantidadeLinhasIettAtivos().intValue());
			System.out.println("QuantidadeItensHistorico Ptc: " + historicoDao.getQuantidadeLinhasHistorico(HistoricoPontoCriticoPtc.class).intValue());
			System.out.println("getQuantidadeLinhasPtcAtivos: " + pontoCriticoDao.getQuantidadeLinhasPtc().intValue());
			historicoDao.cargaInicialHistorico();			
			msg += "Itens Ativos com Estruturas Ativas: " + itemEstruturaDao.getQuantidadeLinhasIettAtivos().intValue();
			msg += "<br> Histórico Iett: " + historicoDao.getQuantidadeLinhasHistorico(HistoricoItemEstruturaIett.class).intValue();			
			msg += "<br> Pontos Críticos: " + pontoCriticoDao.getQuantidadeLinhasPtc().intValue();
			msg += "<br> Histórico Ptc: " + historicoDao.getQuantidadeLinhasHistorico(HistoricoPontoCriticoPtc.class).intValue();
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = "Erro ao iniciar histórico.";
			Mensagem.alert(_jspx_page_context, e.getMessageKey());
		} catch (Exception e){
			msg = "Erro ao iniciar histórico.";
%>
			<%@ include file="/excecao.jsp"%>
<%
		}
	}
%>

	<form name="form" method="post" action="cargaInicialHistorico.jsp">
		<input type="hidden" name="hidAcao" value="">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr class="linha_titulo">
				<td align="center">
					Carga Inicial do Histórico
				</td>
			</tr>
			<tr class="texto">
				<td align="center">
					&nbsp;
				</td>
			</tr>
			<tr class="texto">
				<td align="left">
					*** Clique somente uma vez no botão ***
				</td>
			</tr>
			<tr class="linha_titulo">
				<td align="center">
					<input type="button" value="Iniciar" onclick="javascript:aoClicarAtualizar(document.form)"> 
				</td>
			</tr>
			<tr class="linha_titulo">
				<td align="center">
					<%=msg%>
				</td>
			</tr>
		</table>
	</form>

	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr><td>&nbsp;</td></tr>
	</table>

<%
if("atualizar".equals(acao)) {
%>
	<script>
		document.getElementById("aguarde").style.display="none";
	</script>
<%
}
%>

</div>

</body>

</html>