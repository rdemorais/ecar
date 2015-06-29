<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="comum.util.Pagina" %>


<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link href="<%=_pathEcar%>/css/style.css" rel="stylesheet" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/menu_retratil.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPadrao.js" type="text/javascript"></script>
<script language="javascript" src="<%=_pathEcar%>/js/richtext.js" type="text/javascript"></script>
<script language="javascript">

function iniciarProcesso(hidAcao){
	document.form.hidAcao.value=hidAcao;
	document.form.action = "ctrl.jsp";
	
	document.form.submit();	
}


</script>
</head>


	<!-- sempre colocar o foco inicial no primeiro campo -->
	<body>

		<%@ include file="/cabecalho.jsp"%>
		<%@ include file="/divmenu.jsp"%>

		<div id="conteudo">

			<form name="form" method="post" action="frm_nav.jsp">
				<input type="hidden" name="hidAcao" value="">

				<!-- TITULO -->
				<%@ include file="/titulo_tela.jsp"%>

				<%
			
					boolean ehPesquisa = false;
					boolean ehIncluir = false;
					boolean exibirDadosManutencao = true;
				
					try {
						Integer posicaoRegistroLista = (Integer)session.getAttribute("hidRegistro"); 
						session.removeAttribute("hidRegistro");
						
						PerfilIntercambioDadosPflid perfilIntercambioDadospflid = new PerfilIntercambioDadosCadastroPidc();
						PerfilIntercambioDadosDao perfilIntercambioDadosDao = new PerfilIntercambioDadosDao(request);
						
						List listaPerfilPesquisado = (List) session.getAttribute("listaPerfilPesquisado");
						
						//int i = 0;
						int hidTotRegistro = listaPerfilPesquisado.size(); //Total de Registros
						int hidRegistro = 0;
						
						if (posicaoRegistroLista == null) {
							hidRegistro = Pagina.getParamInt(request, "hidRegistro"); //Registro atual
							
							//Se o último registro for excluído deve apontar para a última posição.
							if (hidRegistro >= hidTotRegistro)
								hidRegistro = hidTotRegistro - 1;

						} else {
							hidRegistro = posicaoRegistroLista.intValue();
						}
						
						
						perfilIntercambioDadospflid = (PerfilIntercambioDadosPflid) perfilIntercambioDadosDao.buscar(PerfilIntercambioDadosPflid.class, ((PerfilIntercambioDadosPflid) listaPerfilPesquisado.get(hidRegistro)).getCodPflid());
							
						session.setAttribute("perfilConsultado", perfilIntercambioDadospflid);
						session.setAttribute("codPflid", perfilIntercambioDadospflid.getCodPflid());
						
						_disabled = "disabled";
						_readOnly = "readOnly";
						
						String strReadOnly = "true";
						String _pesqdisabled = "disabled";
						boolean blVisualizaDesc = true;
					%>
			
							<util:linkstop incluir="javascript:iniciarProcesso('iniciarIncluir');" pesquisar="javascript:iniciarProcesso('iniciarPesquisa');" />
				
							<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" atual="<%=hidRegistro + 1%>" total="<%=hidTotRegistro%>" />
				
							<input type="hidden" name="hidRegistro" value="<%=hidRegistro%>">
							<input type="hidden" name="hidTotRegistro" value="<%=hidTotRegistro%>">
							<input type="hidden" name="codigo" value="<%=perfilIntercambioDadospflid.getCodPflid()%>">
			
							<%@ include file="form.jsp"%>
				
							
				
					<%
						} catch (Exception e) {
							org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
							Mensagem.alert(_jspx_page_context, _msg.getMensagem("erro.exception"));
					%>
							<script language="javascript" type="text/javascript">
								document.form.action = "frm_pes.jsp";
								document.form.submit();
							</script>
					<% } %>
			
			<util:barrabotoes navegacao="true" btn1="Alterar" excluir="Excluir" />
			
			</form>
		</div>
	</body>
	<%@ include file="/include/mensagemAlert.jsp"%>
</html>