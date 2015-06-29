
<jsp:directive.page import="gov.pr.celepar.sentinela.excecao.SentinelaException"/><!--  usuario/usuario -->

<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
 
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="comum.util.FileUpload" %>
 
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<%
	boolean isFormUpload = FileUpload.isMultipartContent(request); 
	List campos = new ArrayList();
	if(isFormUpload){
		campos = FileUpload.criaListaCampos(request); 
	}
%>


<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	
	<input type="hidden" name="hidPesquisou" value="">
	
</form>
 
<%
	UsuarioUsu usuario = new UsuarioUsu();
	UsuarioDao usuarioDao = new UsuarioDao(request);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
	
	if (incluir) {
		try {
		    /* seta os valores recebidos da página no objeto */
			usuarioDao.setUsuario(request, usuario, true);
			
			usuarioDao.associarAhGruposSentinela(request, usuario);
			
			refazPesquisa = false;
			submit = "frm_inc.jsp";		
			usuarioDao.salvar(usuario);
			msg = _msg.getMensagem("usuario.inclusao.sucesso" );
			session.removeAttribute("objUsuario");
		} catch (SentinelaException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem("usuario.alteracao.semPermissao.Sentinela" );
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objUsuario", usuario);		
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objUsuario", usuario);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {			
			/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */
			usuario = (UsuarioUsu) usuarioDao.buscar(UsuarioUsu.class, Long.valueOf(Pagina.getParamLong(request, "codigo")));			
			usuarioDao.alterar(usuario, request);
			
			usuarioDao.associarAhGruposSentinela(request, usuario);
			
			
			msg = _msg.getMensagem("usuario.alteracao.sucesso" );
			session.removeAttribute("objUsuario");
			
		} catch (SentinelaException e) {
				org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
				msg = _msg.getMensagem("usuario.alteracao.semPermissao.Sentinela" );
				refazPesquisa = false;
				submit = "frm_alt.jsp";
				session.setAttribute("objUsuario", usuario);
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objUsuario", usuario);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			
			usuarioDao.excluir( (UsuarioUsu) usuarioDao.buscar(UsuarioUsu.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			
			msg = _msg.getMensagem("usuario.exclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
			if(e.getMessageArgs() != null) {
				for(int k = 0; k < e.getMessageArgs().length; k++) {
					if(!e.getMessageArgs()[k].equals(""))
						msg += ": " + e.getMessageArgs()[k];
				}
			}
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
		try{
			/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
			usuarioDao.setUsuario(request, usuario, false);
			session.setAttribute("objPesquisa", usuario);
			
			UsuarioUsu usuarioDadosSentinela = new UsuarioUsu();
			usuarioDadosSentinela.setNomeUsu(Pagina.getParamStr(request, "nomeUsu"));
			usuarioDadosSentinela.setCnpjCpfUsu(Pagina.getParamStr(request, "cnpjCpfUsu"));
			usuarioDadosSentinela.setEmail1Usu(Pagina.getParamStr(request, "email1Usu"));
			usuarioDadosSentinela.setIdUsuarioUsu(Pagina.getParamStr(request, "idUsuarioUsu"));
			
			session.setAttribute("objUsuarioDadosSentinela", usuarioDadosSentinela);
		}catch(ECARException e){
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			e.printStackTrace();
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	}

	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 

	if (refazPesquisa) {
		try {
			usuario = (UsuarioUsu) session.getAttribute("objPesquisa");

			//List lista = usuarioDao.pesquisar( (UsuarioUsu) usuario, Pagina.getParamStr(request, "nomeUsu"), Pagina.getParamStr(request, "idUsuarioUsu"), Pagina.getParamStr(request, "cnpjCpfUsu"), Pagina.getParamStr(request, "email1Usu"));			
			List lista = new ArrayList();
			if (pesquisar){
				lista = usuarioDao.pesquisar( (UsuarioUsu) usuario, Pagina.getParamStr(request, "nomeUsu"), Pagina.getParamStr(request, "idUsuarioUsu"), Pagina.getParamStr(request, "cnpjCpfUsu"), Pagina.getParamStr(request, "email1Usu"));
			}
			else{
				UsuarioUsu usuarioDadosSentinela = (UsuarioUsu) session.getAttribute("objUsuarioDadosSentinela");
				if(usuarioDadosSentinela != null)
					lista = usuarioDao.pesquisar( (UsuarioUsu) usuario,usuarioDadosSentinela.getNomeUsuSent(),usuarioDadosSentinela.getIdUsuarioUsu(), usuarioDadosSentinela.getCnpjCpfUsu(),usuarioDadosSentinela.getEmail1UsuSent());
			}
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
		   	    Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("estrutura.pesquisa.nenhumRegistro");
			}
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			e.printStackTrace();
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		if (msg != null){
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
		}
	}
	%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 
</body>
</html>
			
