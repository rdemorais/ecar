<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
 
<html lang="pt-br">  
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">			
</form>

<%
	EstruturaEtt estrutura = new EstruturaEtt();
	EstruturaDao estruturaDao = new EstruturaDao(request);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
	if ("incluir".equalsIgnoreCase(hidAcao)) {
		try {
		    /* seta os valores recebidos da página no objeto */
			estruturaDao.setEstrutura(request, estrutura, true, true, true);

			refazPesquisa = false;
			submit = "frm_inc.jsp";		 
			estruturaDao.salvar(estrutura);
			msg = _msg.getMensagem("estrutura.inclusao.sucesso" );
			session.removeAttribute("objEstrutura");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			//session.setAttribute("objEstrutura", estrutura);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("alterar".equalsIgnoreCase(hidAcao)) {
		try {
			estruturaDao.alterar(request);
			msg = _msg.getMensagem("estrutura.alteracao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
			
			if(e.getMessageArgs() != null) {
				for(int k = 0; k < e.getMessageArgs().length; k++) {
					if(!e.getMessageArgs()[k].equals(""))
						msg += ": " + e.getMessageArgs()[k];
				}
			}
			refazPesquisa = false;
			
			Long codigo = Long.valueOf(Pagina.getParamLong(request, "codigo"));
			submit = "frm_alt.jsp?codigo="+codigo;
			
			
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("excluir".equalsIgnoreCase(hidAcao)) {
		try {
			estruturaDao.excluir( (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("estrutura.exclusao.sucesso" );
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
	} else if ("pesquisar".equalsIgnoreCase(hidAcao)) {
		try{
			if(estrutura.getSeqApresentacaoEtt() == null || estrutura.getSeqApresentacaoEtt().longValue() < 1){
				estrutura.setSeqApresentacaoEtt(null);
			}
			estrutura.setAtributoAtbExibirEstruturaEtt(estruturaDao.getAtributoAtbExibirEstruturaEtt(Pagina.getParam(request, "codAtbExibirEstruturaEtt")));
			 			
			/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
			estruturaDao.setEstrutura(request, estrutura, false, false, true);
			
			if(Pagina.getParam(request, "indExibirEstruturaEtt") == null)
				estrutura.setIndExibirEstruturaEtt("");
			
			session.setAttribute("objPesquisa", estrutura);		
		}catch(ECARException e){
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
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
			estrutura = (EstruturaEtt) session.getAttribute("objPesquisa");
//			estrutura.setAtributoAtbExibirEstruturaEtt(estruturaDao.getAtributoAtbExibirEstruturaEtt(Pagina.getParam(request, "codAtbExibirEstruturaEtt")));
			
//			if(session.getAttribute("indExibirEstruturaEtt") == null)
//				estrutura.setIndExibirEstruturaEtt("");
			
			List lista = estruturaDao.pesquisar(estrutura, new String[] {"codEtt","asc"});
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