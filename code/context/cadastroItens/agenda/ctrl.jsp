
<jsp:directive.page import="ecar.pojo.AgendaEntidadesAgeent"/>
<jsp:directive.page import="ecar.dao.AgendaEntidadesAgeentDAO"/>
<jsp:directive.page import="ecar.pojo.AgendaAge"/>
<jsp:directive.page import="ecar.dao.AgendaDao"/>
<jsp:directive.page import="comum.util.Data"/>
<jsp:directive.page import="ecar.login.SegurancaECAR"/>
<jsp:directive.page import="ecar.dao.ItemEstruturaAcaoDao"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="ecar.dao.ItemEstruturaBeneficiarioDao"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="java.util.Date"%>

<% //@ page import="ecar.dao.ItemEstruturaBeneficiarioDao" %>
<%//@ page import="ecar.pojo.ItemEstrtBenefIettb"%>


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
<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="codAge" value=<%=Pagina.getParamStr(request, "codAge")%>>
<input type="hidden" name="codIett" value=<%=Pagina.getParamStr(request, "codIett")%>>
<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>	

<%
	AgendaAge agenda = new AgendaAge();
	AgendaDao agendaDao = new AgendaDao(request);

	String msg = "";
	String submit = "";
	String hidAcao = Pagina.getParamStr(request, "hidAcao");
	boolean listar = "listar".equalsIgnoreCase(hidAcao);//OK
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);//OK
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);//OK
	
	try{
			if(incluir){
				submit = "frm_inc.jsp";
				agendaDao.setAgenda(request, agenda);
				agendaDao.salvar(agenda, request);

				msg = _msg.getMensagem("admPortal.agenda.inclusao.sucesso");
				session.removeAttribute("objAgenda");
			} else if(alterar) {

			/* busca o objeto para manter o que já esta cadastrado */
				agenda = (AgendaAge) agendaDao.buscar(AgendaAge.class, Long.valueOf(Pagina.getParamStr(request, "codAge")));
				agendaDao.setAgenda(request, agenda);
				agenda.setUsuarioUsu(((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
				agendaDao.alterar(agenda, request);
				submit = "frm_con.jsp";
				msg = _msg.getMensagem("admPortal.agenda.alteracao.sucesso");
				session.removeAttribute("objSegmentoItem");
			} else if(excluir){
				String codigosParaExcluir[] = request.getParameterValues("excluir");
				agendaDao.excluir(codigosParaExcluir, ((SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
				submit = "lista.jsp";
				msg = _msg.getMensagem("admPortal.agenda.exclusao.sucesso");				
			} else {
				throw new ECARException("Opção não encontrada");
			}
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "lista.jsp";
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