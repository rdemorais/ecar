
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.pojo.RegDemandaRegd" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="ecar.exception.ECARException" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
</head> 
<body>
<%
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request,"ultimoIdLinhaDetalhado");
%>
<form name="form" method="post">  

<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAba" value=<%=Pagina.getParamStr(request, "codAba")%>>
<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>	
<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="codIett" value=<%=Pagina.getParamStr(request, "codIett")%>>
<%//Campos de ordenação da listagem%>
<input type="hidden" name="clCampo" value="<%=Pagina.getParam(request, "clCampo")%>">
<input type="hidden" name="clOrdem" value="<%=Pagina.getParam(request, "clOrdem")%>">
<input type="hidden" name="refazOrdenacao" value="refaz">

<%
	String msg = "";
	String submit = "lista.jsp";
	
	try{ 
		RegDemandaDao regDemandaDao = new RegDemandaDao(request);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		
		ItemEstruturaIett itemEstrutura = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
						
		if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			RegDemandaRegd regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, Long.valueOf(Pagina.getParamStr(request, "codRegd")));
			
			if (itemEstrutura.getItemRegdemandaIregds() == null) {
				//List lista = new ArrayList();
				//itemEstrutura.getItemRegdemandaIregds().addAll(lista);
				itemEstrutura.getItemRegdemandaIregds().addAll(new ArrayList());
			}
			itemEstrutura.getItemRegdemandaIregds().add(regDemanda);
						
//			itemEstruturaDao.salvar(request, itemEstrutura, funcao);
			itemEstruturaDao.alterar(itemEstrutura);
			msg = _msg.getMensagem("itemEstrutura.regDemanda.inclusao.sucesso");
		}

		if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			String codigosParaExcluir[] = request.getParameterValues("excluir");
			
			for(int i = 0; i < codigosParaExcluir.length; i++){
				RegDemandaRegd regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, Long.valueOf(codigosParaExcluir[i]));
				itemEstrutura.getItemRegdemandaIregds().remove(regDemanda);
			}
			
//			itemEstruturaDao.salvar(request, itemEstrutura, funcao);
			itemEstruturaDao.alterar(itemEstrutura);
			msg = _msg.getMensagem("itemEstrutura.regDemanda.exclusao.sucesso");				
		}
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
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