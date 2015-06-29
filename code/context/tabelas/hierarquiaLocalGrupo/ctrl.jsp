<%@ include file="../../login/validaAcesso.jsp"%>

<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.LocalGrupoLgp" %>
<%@ page import="ecar.dao.LocalGrupoDao" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
	<input type="hidden" name="localGrupoLgp" value="<%=Pagina.getParamInt(request, "localGrupoLgp")%>">
	<input type="hidden" name="msgOperacao" value=""> 
</form>

<%
	String msg = null; 
	LocalGrupoLgp localGrupo = new LocalGrupoLgp();
	LocalGrupoDao localGrupoDao = new LocalGrupoDao(request);
	
	try{
		LocalGrupoLgp localGrupoPai = (LocalGrupoLgp) localGrupoDao.buscar(LocalGrupoLgp.class, Long.valueOf(Pagina.getParam(request, "localGrupoLgp")));
		
		String[] gruposFilhos = request.getParameterValues("localGrupoLgpFilho");
		
		/* Limpa a lista de filhos para alterá-la ou para o caso de não ser informado nenhum filho gravar lista vazia */
		localGrupoPai.getLocalGrupoHierarquiaLgphsByCodLgpPai().clear();
		
		if(gruposFilhos != null){
			for(int i = 0; i < gruposFilhos.length; i++){
				LocalGrupoLgp grupoFilho = (LocalGrupoLgp) localGrupoDao.buscar(LocalGrupoLgp.class, Long.valueOf(gruposFilhos[i]));
				localGrupoPai.getLocalGrupoHierarquiaLgphsByCodLgpPai().add( grupoFilho );
			}		
		}
		
		localGrupoDao.alterar( localGrupoPai );
		msg = _msg.getMensagem("localGrupo.hierarquia.inclusao.sucesso");
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
