<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.EstruturaEtt"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map" %>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="ecar.login.SegurancaECAR"%>


<%@page import="ecar.dao.EstruturaDao"%><html lang="pt-br"> 
<head>
<%@ include file="../../include/meta.jsp"%>
</head>
<body>
<form name="form" method="post">  
<%

String ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
String ultimoIdLinhaExpandido = Pagina.getParamStr(request, "ultimoIdLinhaExpandido");

%>
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<!-- codigo da estrutura principal -->
<input type="hidden" name="codIettPrincipal" value="<%=Pagina.getParamLong(request, "codIettPrincipal")%>"> 
<input type="hidden" name="codEttSelecionado" value="<%=Pagina.getParamStr(request, "codEttSelecionado") %>">
<input type="hidden" name="ultEttSelecionado" value="<%=Pagina.getParamStr(request, "ultEttSelecionado") %>">
<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>
<input type="hidden" name="ultimoIdLinhaExpandido" value=<%=ultimoIdLinhaExpandido%>>


<%

	String msg = "";
	String submit = "";
	try{
		ItemEstruturaIett itemEstrutura = new ItemEstruturaIett();
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);			
		
		if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
		} else if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){
		} else if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			String codigosParaExcluir[] = request.getParameterValues(Pagina.getParamStr(request, "nomeCheckBox"));
			itemEstruturaDao.excluir(codigosParaExcluir, ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.exclusao.sucesso");				
		} else if ("abrirPopUp".equals(Pagina.getParamStr(request, "hidAcao"))){ 
			submit = "popUpPesquisa.jsp";
		} else if ("pesquisar".equals(Pagina.getParamStr(request, "hidAcao"))) {
			String[] estruturasSelecionadas = request.getParameterValues("itemEstrutura");
			String nomeItemPesquisar = Pagina.getParamStr(request, "nomePesquisa");
			SegurancaECAR security = (SegurancaECAR) session.getAttribute("seguranca");
			
			
			List listaItens = itemEstruturaDao.pesquisarItemNaEstrutura(estruturasSelecionadas,nomeItemPesquisar,security);
			
			submit = "popUpPesquisa.jsp?retorno=true";
			
			session.setAttribute("listaItens",listaItens);
		} else if ("voltar".equals(Pagina.getParamStr(request, "hidAcao"))){
			submit = "popUpPesquisa.jsp";
		} else if ("confirmar".equals(Pagina.getParamStr(request, "hidAcao"))){
			
			EstruturaEtt estrutura = (EstruturaEtt)session.getAttribute("estruturaVirtual");
			
			EstruturaDao estruturaDao = new EstruturaDao (request);
			
			estruturaDao.associarItensEstruturaVirtual(estrutura,request.getParameterValues("itemEstruturaPesquisado"));
				
			submit = "popUpPesquisa.jsp?msg=Item associado com sucesso.";
			%>
			<script type="text/javascript">
				window.opener.document.form.action="lista.jsp";
				window.opener.document.form.submit();
				self.close();
			</script>
<%
		} else if ("desassociar".equals(Pagina.getParamStr(request, "hidAcao"))) {
			EstruturaEtt estrutura = (EstruturaEtt)session.getAttribute("estruturaVirtual");

			EstruturaDao estruturaDao = new EstruturaDao (request);
			
			estruturaDao.desassociarItensEstruturaVirtual(estrutura,request.getParameterValues("itemSelecionado"));
				
			// se vier na forma ettXX_pai_ettYY_avo_ZZ -> monta na forma aceita ettYY_pai_iettZZ
			if(ultimoIdLinhaDetalhado.contains("_avo_")) {
				String codEttLinha = ultimoIdLinhaDetalhado.substring(ultimoIdLinhaDetalhado.indexOf("_ett") + 4, ultimoIdLinhaDetalhado.indexOf("_avo_"));
				String codIettLinha = ultimoIdLinhaDetalhado.substring(ultimoIdLinhaDetalhado.indexOf("_avo_")+5, ultimoIdLinhaDetalhado.length());
				ultimoIdLinhaDetalhado = "ett" + codEttLinha + "_pai_iett" + codIettLinha; 
				
			}
			
			submit = "lista.jsp?msg=O(s) item(ns) foi(ram) desvinculado(s) da estrutura "+estrutura.getNomeEtt()+" com sucesso.&ultimoIdLinhaDetalhado=" + ultimoIdLinhaDetalhado 
								+"&ultimoIdLinhaExpandido=" + ultimoIdLinhaExpandido;
		} else {
			submit = "../../excecao.jsp";
		}
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "lista.jsp";
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	} 
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	%>


<script>
	document.form.action = "<%=submit%>";
	document.form.submit();
</script>

</form>
</body>
</html>