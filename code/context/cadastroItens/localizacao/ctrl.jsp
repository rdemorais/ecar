<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstrutLocalIettl" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstrutLocalDao" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>

<%@ page import="comum.util.Data" %>


<%@page import="java.util.ArrayList"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>
<%
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request,"ultimoIdLinhaDetalhado");
%>
<form name="form" method="post">
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	
	<!-- chave primaria-->
	<input type="hidden" name="codIett" value="<%=Pagina.getParamInt(request, "codIett")%>">
	<input type="hidden" name="codLit" value="<%=Pagina.getParamInt(request, "codLit")%>">
	<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>	
</form>

<%
	String msg = "";
	StringBuffer sbmsg = new StringBuffer();
	String submit = "";
	
	try{
		ItemEstrutLocalIettl itemEstrutLocal = new ItemEstrutLocalIettl();
		ItemEstrutLocalDao itemEstrutLocalDao = new ItemEstrutLocalDao(request);
		
		if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			itemEstrutLocalDao.setItemEstrutLocal(request, itemEstrutLocal);
			itemEstrutLocal.setDataInclusaoIettl(Data.getDataAtual());
			itemEstrutLocal.setUsuarioUsuManutencao(((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
			itemEstrutLocalDao.salvar(itemEstrutLocal);
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.localItem.inclusao.sucesso");
		}
		
		if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){			
			String codigosParaExcluir[] = request.getParameterValues("excluir");			
			Long codIett = Long.valueOf(Pagina.getParamStr(request, "codIett"));
			
			if (itemEstrutLocalDao.validaExclusaoLocalItemEstrutura(codigosParaExcluir, codIett, sbmsg)){
				itemEstrutLocalDao.excluir(codigosParaExcluir, codIett,
						((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
				
		        ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
		        ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, codIett);
		        
		        
		        if ((itemEstrutura.getItemEstrutLocalIettls() == null)
             		||(itemEstrutura.getItemEstrutLocalIettls().size() == 0)){
		        	//Seta o local o previsto por local e o realizado por local
		        	//para N
		        	itemEstruturaDao.atualizaIndNaoPorLocal(itemEstrutura);
		        	
		        }
		        
								
				
				submit = "lista.jsp";
				msg = _msg.getMensagem("itemEstrutura.localItem.exclusao.sucesso");
			}
			else{
				submit = "lista.jsp";
				msg = _msg.getMensagem(sbmsg.toString());
			}
		}
		
	} catch ( ECARException e){
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

	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

