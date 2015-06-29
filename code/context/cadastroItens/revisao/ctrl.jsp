<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.ItemEstruturarevisaoIettrev" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ItemEstruturarevisaoIettrevDAO" %>

<html lang="pt-br"> 
<head>
<%@ include file="../../include/meta.jsp"%>
</head>
<body>
<form name="form" method="post">  

<%
String codAba = Pagina.getParamStr(request, "codAba");
%>
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAba" value=<%=codAba%>>
<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="codIett" value=<%=Pagina.getParamStr(request, "codIett")%>>
<input type="hidden" name="codIettrev" value=<%=Pagina.getParamStr(request, "codIettrev")%>>
<input type="hidden" name="ultimoIdLinhaDetalhado" value="<%=Pagina.getParamStr(request, "ultimoIdLinhaDetalhado")%>">

<%
String acao = Pagina.getParamStr(request, "hidAcao");
%>
<input type="hidden" name="hidAcao" value="<%=acao%>"> 
<%
	String msg = "";
	String submit = "";
	UsuarioUsu usuario = new UsuarioUsu();
	try{
			ItemEstruturarevisaoIettrev itemEstruturaRev = new ItemEstruturarevisaoIettrev();
			ItemEstruturarevisaoIettrevDAO itemEstruturaRevDao = new ItemEstruturarevisaoIettrevDAO(request);			
			ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);			

			if("incluir".equals(acao)){
				itemEstruturaRevDao.setItemEstruturaRevisao(request, itemEstruturaRev);	
				itemEstruturaRev.setItemEstruturaIettrev((ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett"))));
				usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
				itemEstruturaRev.setUsuarioUsuByCodUsuIncIettrev(usuario);
				itemEstruturaRev.setDataInclusaoIettrev(Data.getDataAtual());
				if("S".equals(Pagina.getParamStr(request, "hidGravado")))
					itemEstruturaRevDao.alterar(itemEstruturaRev);	
				else
					itemEstruturaRevDao.salvar(itemEstruturaRev);
				submit = "lista.jsp?codAba="+codAba+"&codIett="+Pagina.getParamStr(request, "codIett");
				msg = _msg.getMensagem("itemEstrutura.acao.inclusao.sucesso");
			}	
			if("primeira".equals(acao)){
				itemEstruturaRevDao.setItemEstruturaRevisao(request, itemEstruturaRev);	
				itemEstruturaRev.setItemEstruturaIettrev((ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett"))));
				usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
				itemEstruturaRev.setUsuarioUsuByCodUsuIncIettrev(usuario);
				itemEstruturaRev.setDataInclusaoIettrev(Data.getDataAtual());				
				itemEstruturaRevDao.salvar(itemEstruturaRev);
				submit = "frm_inc.jsp?codIettrev="+itemEstruturaRev.getCodIettrev();
				msg = _msg.getMensagem("itemEstrutura.acao.inclusao.sucesso");
			}	
			if("alterar".equals(acao)){
				itemEstruturaRevDao.setItemEstruturaRevisao(request, itemEstruturaRev);	
				itemEstruturaRev.setItemEstruturaIettrev((ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett"))));
				usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
				itemEstruturaRev.setUsuarioUsuByCodUsuIncIettrev(usuario);	
				itemEstruturaRev.setDataInclusaoIettrev(Data.getDataAtual());				
				itemEstruturaRevDao.alterar(itemEstruturaRev);
				submit = "frm_inc.jsp?codIettrev="+itemEstruturaRev.getCodIettrev();
				msg = _msg.getMensagem("itemEstrutura.acao.alteracao.sucesso");
			}
			if("excluir".equals(acao)){
				String codigosParaExcluir[] = request.getParameterValues("excluir");
				itemEstruturaRevDao.excluir(codigosParaExcluir);
				submit = "lista.jsp?codAba="+codAba+"&codIett="+Pagina.getParamStr(request, "codIett");
				msg = _msg.getMensagem("itemEstrutura.acao.exclusao.sucesso");				
			}
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "lista.jsp?codAba="+codAba+"&codIett="+Pagina.getParamStr(request, "codIett");
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