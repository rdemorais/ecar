<%@ include file="../../login/validaAcesso.jsp"%>

<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.dao.EntidadeDao" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
	<input type="hidden" name="codEnt" value="<%=Pagina.getParamInt(request, "codEnt")%>">
	<input type="hidden" name="msgOperacao" value=""> 
</form>

<%
	String msg = null; 
	EntidadeEnt entidade = new EntidadeEnt();
	EntidadeDao entidadeDao = new EntidadeDao(request);
	
	try{
		entidade = (EntidadeEnt) entidadeDao.buscar(EntidadeEnt.class, Long.valueOf(Pagina.getParam(request, "codEnt")));
		
		String[] filhos = request.getParameterValues("entidadeEntFilho");
		String[] pais = request.getParameterValues("entidadeEntPai");
		
		/* Limpa a lista de filhos para alterá-la ou para o caso de não ser informado nenhum filho gravar lista vazia */
		entidade.getHierarquiaEntidadeHentsByCodEnt().clear();
		entidade.getHierarquiaEntidadeHentsByCodEntPai().clear();
		
		if (pais != null) {
			for (int i = 0; i < pais.length; i++) {
				EntidadeEnt entidadePai = (EntidadeEnt) entidadeDao.buscar(EntidadeEnt.class, Long.valueOf(pais[i]));
				entidade.getHierarquiaEntidadeHentsByCodEnt().add(entidadePai);
			}
		}
		
		if (filhos != null) {
			for (int i = 0; i < filhos.length; i++) {
				EntidadeEnt entidadeFilho = (EntidadeEnt) entidadeDao.buscar(EntidadeEnt.class, Long.valueOf(filhos[i]));
				entidade.getHierarquiaEntidadeHentsByCodEntPai().add(entidadeFilho);
			}
		}
		
		entidadeDao.alterar(entidade);
		msg = _msg.getMensagem("entidade.hierarquia.inclusao.sucesso");
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
