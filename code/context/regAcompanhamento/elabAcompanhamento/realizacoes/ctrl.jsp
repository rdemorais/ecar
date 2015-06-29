<jsp:directive.page import="ecar.util.Dominios"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.ItemEstruturaAcaoDao" %>
<%@ page import="ecar.pojo.ItemEstrutAcaoIetta"%>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.EmpresaEmp" %>
<%@ page import="ecar.dao.EmpresaDao" %>
<%@ page import="comum.database.Dao" %>
<%@ page import="comum.util.Util" %>

<html lang="pt-br"> 
<head>
<%@ include file="/include/meta.jsp"%>
<%
	AcompReferenciaItemDao acompReferenciaItemDao = new AcompReferenciaItemDao(request);
	AcompReferenciaItemAri acompReferenciaItem = (AcompReferenciaItemAri) acompReferenciaItemDao.buscar(AcompReferenciaItemAri.class, Long.valueOf(Pagina.getParamStr(request, "codAri")));
	Long codIett = acompReferenciaItem.getItemEstruturaIett().getCodIett();
	String msg = "";
	String submit = "";
	String hidAcao = Pagina.getParamStr(request,"hidAcao");
%>
</head>
<body>
<form name="form" method="post">  
<input type="hidden" name="msgOperacao"   value=""> 
<input type="hidden" name="codAri"        value="<%=Pagina.getParamStr(request, "codAri")%>">
<input type="hidden" name="primeiroAcomp" value="<%=Pagina.getParamStr(request, "primeiroAcomp")%>">
<input type="hidden" name="codAcomp"      value="<%=Pagina.getParamStr(request, "codAcomp")%>">
<input type="hidden" name="hidAcao"       value="<%=Pagina.getParamStr(request, "hidAcao")%>">
<input type="hidden" name="codIett"       value="<%=Pagina.getParamStr(request, "codIett")%>">
<input type="hidden" name="codIetta"      value="<%=Pagina.getParamStr(request, "codIetta")%>">
<input type="hidden" name="codAba"        value="<%=Pagina.getParamStr(request, "codAba")%>">
<%
	String descricaoEvento = null;
	try{
			ItemEstruturaAcaoDao itemEstruturaAcaoDao = new ItemEstruturaAcaoDao(request);			
			Dao dao = new Dao();
			UsuarioDao usuDAO = new UsuarioDao();
			EmpresaDao empDAO = new EmpresaDao(request);

			ItemEstrutAcaoIetta itemEstruturaAcao = new ItemEstrutAcaoIetta();
	
			if("incluir".equals(hidAcao)){
				descricaoEvento = "Inclusão de " + Pagina.getParamStr(request, "labelEttf");
				itemEstruturaAcaoDao.setItemEstruturaAcao(request, itemEstruturaAcao);	
				itemEstruturaAcaoDao.salvar(itemEstruturaAcao, ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario() );
				
				submit = "eventos.jsp";
				msg = _msg.getMensagem("itemEstrutura.acao.inclusao.sucesso");
			}	
			if("alterar".equals(hidAcao)){
				descricaoEvento = "Alteração de " + Pagina.getParamStr(request, "labelEttf");
				itemEstruturaAcao = (ItemEstrutAcaoIetta) itemEstruturaAcaoDao.buscar(ItemEstrutAcaoIetta.class, Long.valueOf(Pagina.getParamStr(request, "codIetta")));
				itemEstruturaAcaoDao.setItemEstruturaAcao(request, itemEstruturaAcao);	
				itemEstruturaAcaoDao.alterar(itemEstruturaAcao, ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario() );
				submit = "frm_con.jsp";
				msg = _msg.getMensagem("itemEstrutura.acao.alteracao.sucesso");
			}
			if("excluir".equals(hidAcao)){
				descricaoEvento = "Exclusão de " + Pagina.getParamStr(request, "labelEttf");
				String codigosParaExcluir[] = request.getParameterValues("excluir");
				itemEstruturaAcaoDao.excluir(codigosParaExcluir, ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario() );
				submit = "eventos.jsp";
				msg = _msg.getMensagem("itemEstrutura.acao.exclusao.sucesso");				
			}
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "eventos.jsp";
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
		submit = "eventos.jsp";
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