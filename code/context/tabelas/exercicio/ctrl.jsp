
<jsp:directive.page import="ecar.pojo.PeriodoExercicioPerExe"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="comum.util.Data" %> 
<%@ page import="java.util.List" %>
<%@ page import="ecar.dao.PeriodoExercicioDao" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.api.facade.*" %>

<%@page import="ecar.api.facade.Exercicio"%>
<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">
</form>

<%
	ExercicioExe exercicio = new ExercicioExe();
	ExercicioDao exercicioDao = new ExercicioDao(request);
	PeriodoExercicioDao perExeDao = new PeriodoExercicioDao(request);
	Mensagem mensagem = new Mensagem(application);
	
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
		/* melhor usar getParamStr, pois nunca devolve null */
		exercicio.setDescricaoExe(Pagina.getParamStr(request, "descricaoExe").trim()); 
		exercicio.setDataInicialExe( Data.parseDate(Pagina.getParamStr(request, "dataInicialExe")));
		exercicio.setDataFinalExe( Data.parseDate(Pagina.getParamStr(request, "dataFinalExe")));
		
		if(!"".equals(Pagina.getParamStr(request, "periodoExercicioPerExe"))){
			exercicio.setPeriodoExercicioPerExe((PeriodoExercicioPerExe) perExeDao.buscar(PeriodoExercicioPerExe.class, Long.valueOf(Pagina.getParamStr(request, "periodoExercicioPerExe"))));
		}
		
		refazPesquisa = false;
		submit = "frm_inc.jsp";
		try {
			exercicioDao.salvar(exercicio);
			msg = _msg.getMensagem("exercicio.inclusao.sucesso");
			session.removeAttribute("objExercicio");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objExercicio", exercicio);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			exercicio = (ExercicioExe) exercicioDao.buscar(ExercicioExe.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			/* altera o que foi modificado na tela */
			exercicio.setDescricaoExe(Pagina.getParamStr(request, "descricaoExe").trim());
			exercicio.setDataInicialExe(Data.parseDate(Pagina.getParamStr(request, "dataInicialExe")));
			exercicio.setDataFinalExe(Data.parseDate(Pagina.getParamStr(request, "dataFinalExe")));

			if(!"".equals(Pagina.getParamStr(request, "periodoExercicioPerExe"))){
				exercicio.setPeriodoExercicioPerExe((PeriodoExercicioPerExe) perExeDao.buscar(PeriodoExercicioPerExe.class, Long.valueOf(Pagina.getParamStr(request, "periodoExercicioPerExe"))));
			}
			else {
				exercicio.setPeriodoExercicioPerExe(null);
			}

			Exercicio exeWrapper = new Exercicio(exercicio);
			String dataInicial = Pagina.getParamStr(request, "dataInicialExe");
			String dataFinal = Pagina.getParamStr(request, "dataFinalExe");
			//verifica se a mudança nas datas do exercício vai 
			//causar impacto nos itens de estrutura
			if(exeWrapper.podeAlterarDatasLimites(dataInicial, dataFinal) == false){
				String keyAndProperties = exeWrapper.getErrorKey();
				int length = keyAndProperties.split("#").length;
				String key = keyAndProperties.split("#")[0];
				String parameters[] = new String[length-1];
				
				for(int i=1; i < length; i++){
					parameters[i-1] = keyAndProperties.split("#")[i]; 	
				}
				
				msg = _msg.getMensagem(key, parameters);
				msg = msg.replaceFirst("item", "exercício");
			}else{
				/* altera no BD */
				exercicioDao.alterar(exercicio);
				msg = _msg.getMensagem("exercicio.alteracao.sucesso" );
			}
			
			
			
			session.removeAttribute("objExercicio");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_alt.jsp";
			session.setAttribute("objExercicio", exercicio);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			exercicioDao.excluir((ExercicioExe) exercicioDao.buscar(ExercicioExe.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("exercicio.exclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
		/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
		exercicio.setDescricaoExe(Pagina.getParam(request, "descricaoExe"));
		
		if (Pagina.getParam(request, "dataInicialExe") != null)
			exercicio.setDataInicialExe(Data.parseDate(Pagina.getParamStr(request, "dataInicialExe")));
		
		if (Pagina.getParam(request, "dataFinalExe") != null)
			exercicio.setDataFinalExe(Data.parseDate(Pagina.getParamStr(request, "dataFinalExe")));
		
		if(Pagina.getParam(request, "periodoExercicioPerExe") != null){
			exercicio.setPeriodoExercicioPerExe((PeriodoExercicioPerExe) perExeDao.buscar(PeriodoExercicioPerExe.class, Long.valueOf(Pagina.getParamStr(request, "periodoExercicioPerExe"))));
		}
		
		session.setAttribute("objPesquisa", exercicio);
	}
	
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		
	if (refazPesquisa) {
		try {
			exercicio = (ExercicioExe) session.getAttribute("objPesquisa");
			List lista = exercicioDao.pesquisar(exercicio, new String[] {"descricaoExe","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("exercicio.pesquisa.nenhumRegistro");
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

