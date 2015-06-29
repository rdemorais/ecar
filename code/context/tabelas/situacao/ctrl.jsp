<%@ include file="../../login/validaAcesso.jsp"%>

<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.SituacaoSit" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.dao.SituacaoDao" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashSet" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
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
	SituacaoSit situacao = new SituacaoSit();
	SituacaoDao situacaoDao = new SituacaoDao(request);
	EstruturaDao estruturaDao = new EstruturaDao(request);
	TipoFuncAcompDao funcaoDao = new TipoFuncAcompDao(request);
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String submit = "frm_pes.jsp";

	/* default sempre refazer a pesquisa */
	boolean refazPesquisa = true;  

	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean pesquisar = "pesquisar".equalsIgnoreCase(hidAcao);

	if (incluir) {
		try{
			
			/* melhor usar getParamStr, pois nunca devolve null */		
			situacao.setIndComentarioSit(Pagina.getParamStr(request, "indComentarioSit").trim()); 
			situacao.setIndConcluidoSit(Pagina.getParamStr(request, "indConcluidoSit").trim());
			situacao.setDescricaoSit(Pagina.getParamStr(request, "descricaoSit").trim());
			situacao.setComentarioSit(Pagina.getParamStr(request, "comentarioSit").trim());
			situacao.setIndMetaFisicaSit(Pagina.getParamStr(request, "indMetaFisicaSit").trim());
			situacao.setIndSemAcompanhamentoSit(Pagina.getParamStr(request, "indSemAcompanhamentoSit").trim());
			
			String[] funcoes = request.getParameterValues("tipoFuncAcompTpfa");
			
			if(funcoes != null){
				situacao.setSituacaoTpFuncAcmpSitfas(funcaoDao.getSetFuncoesAcompanhamento(funcoes));
			}
			
			String[] estruturas = request.getParameterValues("estruturaEtt");	
			
			if(estruturas != null){
				 situacao.setEstruturaSituacaoEtts(estruturaDao.getSetEstruturas(estruturas));
			}
			refazPesquisa = false;
			submit = "frm_inc.jsp";
			situacaoDao.salvar(situacao);
			
			msg = _msg.getMensagem("situacao.inclusao.sucesso");
			session.removeAttribute("objSituacao");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objSituacao", situacao);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			situacao = (SituacaoSit) situacaoDao.buscar(SituacaoSit.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			/* altera o que foi modificado na tela */
			situacao.setIndComentarioSit((Pagina.getParamStr(request, "indComentarioSit"))); 
			situacao.setIndConcluidoSit((Pagina.getParamStr(request, "indConcluidoSit")));
			situacao.setDescricaoSit(Pagina.getParamStr(request, "descricaoSit"));
			situacao.setComentarioSit(Pagina.getParamStr(request, "comentarioSit"));
			situacao.setIndMetaFisicaSit((Pagina.getParamStr(request, "indMetaFisicaSit")));
			situacao.setIndSemAcompanhamentoSit(Pagina.getParamStr(request, "indSemAcompanhamentoSit").trim());
			
			String[] funcoes = request.getParameterValues("tipoFuncAcompTpfa");
			
			if(funcoes != null){
				situacao.setSituacaoTpFuncAcmpSitfas(funcaoDao.getSetFuncoesAcompanhamento(funcoes));
			} else  {
				situacao.setSituacaoTpFuncAcmpSitfas(null);
			}
			
		   String[] estruturas = request.getParameterValues("estruturaEtt");	
			
			if(estruturas != null){
				situacao.setEstruturaSituacaoEtts(estruturaDao.getSetEstruturas(estruturas));
			} else  {
				situacao.setEstruturaSituacaoEtts(null);
			}
			
			/* altera no BD */
			situacaoDao.alterar(situacao);
			msg = _msg.getMensagem("situacao.alteracao.sucesso");
			session.removeAttribute("objSituacao");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objSituacao", situacao);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			/* busca e exclui o objeto do BD */
			situacaoDao.excluir((SituacaoSit)situacaoDao.buscar(SituacaoSit.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("situacao.exclusao.sucesso");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
			if(e.getMessageArgs() != null) {
				for(int k = 0; k < e.getMessageArgs().length; k++) {
					msg += ": " + e.getMessageArgs()[k];
				}
			}
			
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
	
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		situacao.setIndComentarioSit((Pagina.getParam(request, "indComentarioSit"))); 
		situacao.setIndConcluidoSit((Pagina.getParam(request, "indConcluidoSit")));
		situacao.setDescricaoSit(Pagina.getParam(request, "descricaoSit"));
		situacao.setComentarioSit(Pagina.getParam(request, "comentarioSit"));	
		situacao.setIndSemAcompanhamentoSit(Pagina.getParam(request, "indSemAcompanhamentoSit"));
		situacao.setIndMetaFisicaSit(Pagina.getParam(request, "indMetaFisicaSit"));
		
		String[] funcoes = request.getParameterValues("tipoFuncAcompTpfa");
		
		if(funcoes != null){
			situacao.setSituacaoTpFuncAcmpSitfas(funcaoDao.getSetFuncoesAcompanhamento(funcoes));
		}
		
		String[] estruturas = request.getParameterValues("estruturaEtt");	
		
		if(estruturas != null){
			situacao.setEstruturaSituacaoEtts(estruturaDao.getSetEstruturas(estruturas));
		}
		session.setAttribute("objPesquisa", situacao);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			situacao = (SituacaoSit) session.getAttribute("objPesquisa");
			List lista = situacaoDao.pesquisar(situacao, new String[] {"descricaoSit","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("situacao.pesquisa.nenhumRegistro");
			}
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
	}
	%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 


</body>
</html>


