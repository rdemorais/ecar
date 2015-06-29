<jsp:directive.page import="ecar.dao.EmailDao"/>
<jsp:directive.page import="ecar.pojo.Email"/>
<jsp:directive.page import="comum.util.Pagina"/>
<jsp:directive.page import="ecar.pojo.UsuarioUsu"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Iterator"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
</head>
<body>

<form name="form" method="post">
<!-- campo de controle para mensagens -->
<!--
<input type="hidden" name="codEmail" id="codEmail" value="<%=Pagina.getParamStr(request,"codEmail")%>" />
<input type="hidden" name="nPagina"  id="nPagina"  value="<%=Pagina.getParamStr(request,"nPagina")%>" /> 
-->
</form>
<%
	String submit = "caixa_entrada.jsp";
	EmailDao emailDao = new EmailDao();
	Email email = new Email();
	UsuarioUsu usu = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	try{
		String acao = Pagina.getParamStr(request,"hidAcao");
		String codEmail = Pagina.getParamStr(request,"codEmail");
		
		if ("excluir".equalsIgnoreCase(acao)){
			if(!"".equalsIgnoreCase(codEmail))
				emailDao.excluir((Email) emailDao.buscar(Email.class,Long.valueOf(codEmail)));
			else{
				String emailsExcluir[] = request.getParameterValues("checkmail");
				Set lista = emailDao.getLista(emailsExcluir);
				emailDao.excluir(lista);
			}
		}
		else if(Email.MARCA_LIDO.equals(acao) || Email.MARCA_NLIDO.equals(acao)){
			String emailsMarcados[] = request.getParameterValues("checkmail"); 
			Set lista = emailDao.getLista(emailsMarcados);
			Iterator it = lista.iterator();
			while(it.hasNext()){
				email = (Email) it.next();
				email.setLido((Email.MARCA_LIDO.equals(acao))?Email.LIDO:Email.NLIDO);
			}
			emailDao.alterar(lista);
		}
	}
	catch(Exception e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
%>
	<%@ include file="/excecao.jsp"%>
<%
	}
%>
<script language="javascript">
	document.form.action = "<%=submit%>";
	document.form.submit();
</script> 
</body>
</html>