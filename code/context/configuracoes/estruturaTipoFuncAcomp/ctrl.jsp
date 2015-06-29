
<jsp:directive.page import="java.util.HashSet"/>
<jsp:directive.page import="ecar.dao.EstruturaDao"/>
<jsp:directive.page import="ecar.permissao.ControlePermissao"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.pojo.ItemEstruturaIett"/><%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.EstrutTpFuncAcmpEtttfa" %>
<%@ page import="ecar.pojo.EstrutTpFuncAcmpEtttfaPK" %>
<%@ page import="ecar.dao.EstruturaTipoFuncAcompDao" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.ItemEstrutUsuarioIettus" %>
<%@ page import="ecar.dao.ItemEstrutUsuarioDao" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="java.util.List" %>

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
	EstrutTpFuncAcmpEtttfa estruturaTipoFuncAcomp = new EstrutTpFuncAcmpEtttfa();
	EstruturaTipoFuncAcompDao estruturaTipoFuncAcompDao = new EstruturaTipoFuncAcompDao(request);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);
	
	if (alterar) {
		try {			
			EstrutTpFuncAcmpEtttfaPK chave = new EstrutTpFuncAcmpEtttfaPK();
			chave.setCodEtt(Long.valueOf(Pagina.getParamLong(request, "codEtt")));
			chave.setCodTpfa(Long.valueOf(Pagina.getParamLong(request, "codTpfa")));
			/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */
			estruturaTipoFuncAcomp = (EstrutTpFuncAcmpEtttfa) estruturaTipoFuncAcompDao.buscar(EstrutTpFuncAcmpEtttfa.class, chave);
			
			/* recupera os novos dados da EstruturaFuncao*/
			estruturaTipoFuncAcompDao.setEstruturaTipoFuncAcomp(request, estruturaTipoFuncAcomp, false);
			
			
			/*Verificação das estruturas de acompanhementos que para quem o bloqueio foi liberado */
			if (estruturaTipoFuncAcomp.getIdPodeBloquearEtttfa() != null &&	estruturaTipoFuncAcomp.getIdPodeBloquearEtttfa().equals(Pagina.SIM) ){
				estruturaTipoFuncAcomp.setLibTipoFuncAcompTpfas(estruturaTipoFuncAcompDao.setLibTipoFuncAcompTpfa());
			} else { //Verificar se tem que excluir as referencias
				estruturaTipoFuncAcomp.setLibTipoFuncAcompTpfas(new HashSet());
			}

			estruturaTipoFuncAcompDao.alterar(estruturaTipoFuncAcomp);

			msg = _msg.getMensagem("estruturaTipoFuncAcomp.alteracao.sucesso" );

		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
		try{
			/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
			estruturaTipoFuncAcompDao.setEstruturaTipoFuncAcomp(request, estruturaTipoFuncAcomp, true);
			session.setAttribute("objPesquisa", estruturaTipoFuncAcomp);		
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
			estruturaTipoFuncAcomp = (EstrutTpFuncAcmpEtttfa) session.getAttribute("objPesquisa");
			List lista = estruturaTipoFuncAcompDao.pesquisar( (EstrutTpFuncAcmpEtttfa) estruturaTipoFuncAcomp, new String[] {"estruturaEtt","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("estruturaTipoFuncAcomp.pesquisa.nenhumRegistro");
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
