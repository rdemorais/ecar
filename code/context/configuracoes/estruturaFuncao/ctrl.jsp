<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashSet"%>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttfPK" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %> 
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
	EstruturaFuncaoEttf estruturaFuncao = new EstruturaFuncaoEttf();
	EstruturaFuncaoDao estruturaFuncaoDao = new EstruturaFuncaoDao(request);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
//	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	
	if ("alterar".equalsIgnoreCase(hidAcao)) {
		try {			
			EstruturaFuncaoEttfPK chave = new EstruturaFuncaoEttfPK();
			chave.setCodEtt(Long.valueOf(Pagina.getParamLong(request, "codEtt")));
			chave.setCodFun(Long.valueOf(Pagina.getParamLong(request, "codFun")));
			/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */			
			estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.buscar(EstruturaFuncaoEttf.class, chave);
			
			/* recupera os novos dados da EstruturaFuncao*/
			estruturaFuncaoDao.setEstruturaFuncao(request, estruturaFuncao, true);
			
			/*Verificação das estruturas de acompanhementos que para quem o bloqueio foi liberado */
			if (estruturaFuncao.getIndPodeBloquearEttf().equals(Pagina.SIM) ){
				estruturaFuncao.setLibTipoFuncAcompTpfas(estruturaFuncaoDao.setLimbTipoFuncAcompTpfa());
			} else { //Verificar se tem que excluir as referencias
				estruturaFuncao.setLibTipoFuncAcompTpfas(new HashSet());
			}
			
						
			estruturaFuncaoDao.alterar(estruturaFuncao);
			
			msg = _msg.getMensagem("estruturaFuncao.alteracao.sucesso" );

		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("pesquisar".equalsIgnoreCase(hidAcao)) {
		try{
			/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
			estruturaFuncaoDao.setEstruturaFuncao(request, estruturaFuncao, false);
			session.setAttribute("objPesquisa", estruturaFuncao);		
		}catch(ECARException e){
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());		
		}
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 

	if (refazPesquisa) {
		try {
			estruturaFuncao = (EstruturaFuncaoEttf) session.getAttribute("objPesquisa");
			//List lista = estruturaFuncaoDao.pesquisar( (EstruturaFuncaoEttf) estruturaFuncao, new String[] {"estruturaEtt","asc"});
			List lista = estruturaFuncaoDao.pesquisar( (EstruturaFuncaoEttf) estruturaFuncao, new String[] {"comp_id.codEtt", "asc", "comp_id.codFun", "asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("estruturaFuncao.pesquisa.nenhumRegistro");
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
			