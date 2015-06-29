<%@ include file="../../login/validaAcesso.jsp"%>

<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.LocalItemLit" %>
<%@ page import="ecar.dao.LocalItemDao" %>
<%@ page import="ecar.pojo.LocalGrupoLgp" %>
<%@ page import="ecar.dao.LocalGrupoDao" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
	<input type="hidden" name="localGrupoLgp" value="<%=Pagina.getParam(request, "localGrupoLgp")%>">
	<input type="hidden" name="localItemLit" value="<%=Pagina.getParam(request, "localItemLit")%>">
	<input type="hidden" name="msgOperacao" value=""> 
</form>

<%
	String msg = null; 
	Mensagem mensagem = new Mensagem(application);
	LocalItemDao localItemDao = new LocalItemDao(request);
	
	try{
		LocalItemLit localItem = (LocalItemLit) localItemDao.buscar(LocalItemLit.class, Long.valueOf(Pagina.getParam(request, "localItemLit")));
		
		String[] locaisPais = request.getParameterValues("localItemLitPai");
		String[] locaisFilhos = request.getParameterValues("localItemLitFilho");
		
		/* Limpa a lista de filhos para alterá-la ou para o caso de não ser informado nenhum filho gravar lista vazia */
		localItem.getLocalItemHierarquiaLithsByCodLit().clear();
		localItem.getLocalItemHierarquiaLithsByCodLitPai().clear();
		
		if(locaisPais != null){
			for(int i = 0; i < locaisPais.length; i++){
				LocalItemLit localPai = (LocalItemLit) localItemDao.buscar(LocalItemLit.class, Long.valueOf(locaisPais[i]));
				localItem.getLocalItemHierarquiaLithsByCodLit().add(localPai);
			}		
		}
		
		if(locaisFilhos != null){
			for(int i = 0; i < locaisFilhos.length; i++){
				LocalItemLit localFilho = (LocalItemLit) localItemDao.buscar(LocalItemLit.class, Long.valueOf(locaisFilhos[i]));
				localItem.getLocalItemHierarquiaLithsByCodLitPai().add(localFilho);
			}		
		}
		
		localItemDao.alterar(localItem);
		msg = _msg.getMensagem("hierarquiaLocalItem.atualizacao.sucesso");
	}
	catch(ECARException e){
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

<script language="javascript">
	document.form.action = "frm_inc.jsp";
	document.form.submit();
</script> 

</body>
</html>
