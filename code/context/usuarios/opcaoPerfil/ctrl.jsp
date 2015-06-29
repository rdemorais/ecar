<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.OpcaoOpc" %>
<%@ page import="ecar.dao.OpcaoDao" %>
<%@ page import="ecar.pojo.PerfilPfl" %>
<%@ page import="ecar.dao.PerfilDao" %>
<%@ page import="java.util.List" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
</form>

<%
	String msg = null; 
	Mensagem mensagem = new Mensagem(application);
	
	PerfilDao perfilDao = new PerfilDao(request);
	OpcaoDao opcaoDao = new OpcaoDao(request);
	
	try{
		PerfilPfl perfilPfl = (PerfilPfl) perfilDao.buscar(PerfilPfl.class, Long.valueOf(Pagina.getParam(request, "perfilPfl")));
		
		String[] opcaoOpcLiberadas = request.getParameterValues("opcaoOpcLiberadas");
		
		/* Limpa a lista de filhos para alterá-la ou para o caso de não ser informado nenhum filho gravar lista vazia */
		perfilPfl.getOpcaoPerfilOpcps().clear();
		
		if(opcaoOpcLiberadas != null){
			for(int i = 0; i < opcaoOpcLiberadas.length; i++){
				OpcaoOpc opcaoOpc = (OpcaoOpc) opcaoDao.buscar(OpcaoOpc.class, Long.valueOf(opcaoOpcLiberadas[i]));
				perfilPfl.getOpcaoPerfilOpcps().add( opcaoOpc );
			}		
		}
		perfilDao.alterar( perfilPfl );
		msg = _msg.getMensagem("opcaoPerfil.inclusao.sucesso");
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
