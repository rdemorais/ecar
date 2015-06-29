<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="comum.util.Data" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.taglib.util.Input"%>

 

<%@page import="ecar.action.ActionSisAtributo"%>
<%@page import="ecar.util.Dominios"%><html lang="pt-br">
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
	<INPUT type="hidden" name="indTabelaUso" value="<%=request.getParameter("indTabelaUso")%>">		
</form>

<%
	SisAtributoSatb atributo = new SisAtributoSatb();
	SisAtributoDao atributoDao = new SisAtributoDao(request);
	SisGrupoAtributoDao SisGrupoAtributoDao = new SisGrupoAtributoDao(request);
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
		try {
			/* melhor usar getParamStr, pois nunca devolve null */
			
			atributo.setSisGrupoAtributoSga((SisGrupoAtributoSga) SisGrupoAtributoDao.buscar(SisGrupoAtributoSga.class, Long.valueOf(Pagina.getParam(request, "sisGrupoAtributoSga"))));

			//verifica se é do tipo ID
			String tipoValidacao = Pagina.getParamStr(request, "atribInfCompSatb2");
			if (tipoValidacao != null){
				if (tipoValidacao.equals("mascaraScript") || tipoValidacao.equals("mascaraEditavelScript")) {
					ActionSisAtributo action = new ActionSisAtributo();
					
					action.validaMascara(request.getParameter("mascara"));
					
					atributo.setMascara(request.getParameter("mascara"));
					atributo.setGeral(new Boolean(request.getParameter("geral")));
					atributo.setPeriodico(new Boolean(request.getParameter("periodico")));
				} else if (tipoValidacao.equals("autoIncrementalScript")) {
					
					atributo.setGeral(new Boolean(request.getParameter("geral")));
					atributo.setPeriodico(new Boolean(request.getParameter("periodico")));
				}
			} 
			
			atributo.setDescricaoSatb(Pagina.getParamStr(request, "descricaoSatb").trim());
			
			if(atributo.getSisGrupoAtributoSga()!=null){
				if(atributo.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO))){
					atributo.setAtribInfCompSatb(Pagina.getParamStr(request, "atribInfCompSatb2").trim());
				} else {
					atributo.setAtribInfCompSatb(Pagina.getParamStr(request, "atribInfCompSatb").trim());
				}
			}			
			atributo.setIndAtivoSatb(Pagina.getParamStr(request, "indAtivoSatb"));
			atributo.setDataInclusaoSatb(Data.getDataAtual());
			
			refazPesquisa = false;
			submit = "frm_inc.jsp";
			atributoDao.salvar(atributo);
			msg = _msg.getMensagem("atributo.inclusao.sucesso");
			session.removeAttribute("objAtributo");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objAtributo", atributo);
			refazPesquisa = false;
			submit = "frm_inc.jsp";
			session.removeAttribute("objAtributo");
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			atributo = (SisAtributoSatb) atributoDao.buscar(SisAtributoSatb.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			atributo.setSisGrupoAtributoSga( (SisGrupoAtributoSga) SisGrupoAtributoDao.buscar(SisGrupoAtributoSga.class, Long.valueOf(Pagina.getParam(request, "sisGrupoAtributoSga"))));
			atributo.setDescricaoSatb(Pagina.getParamStr(request, "descricaoSatb").trim());
			atributo.setIndAtivoSatb(Pagina.getParamStr(request, "indAtivoSatb"));
			
			if(atributo.getSisGrupoAtributoSga()!=null){
				if(atributo.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO))){
					atributo.setAtribInfCompSatb(Pagina.getParamStr(request, "atribInfCompSatb2").trim());
				} else {
					atributo.setAtribInfCompSatb(Pagina.getParamStr(request, "atribInfCompSatb").trim());
				}
			}			
					
			//verifica se é do tipo ID
			String tipoValidacao = Pagina.getParamStr(request, "atribInfCompSatb2");
			if (tipoValidacao != null){
				if (tipoValidacao.equals("mascaraScript") || tipoValidacao.equals("mascaraEditavelScript")) {
					ActionSisAtributo action = new ActionSisAtributo();
				
					action.validaMascara(request.getParameter("mascara"));
					
				} 
				atributo.setGeral(new Boolean(request.getParameter("geral")));
				atributo.setPeriodico(new Boolean(request.getParameter("periodico")));
				atributo.setMascara(Pagina.getParamStrOrNull(request,"mascara"));
				
			}
			
			atributoDao.alterar(atributo);
			msg = _msg.getMensagem("atributo.alteracao.sucesso");
			session.removeAttribute("objAtributo");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			refazPesquisa = false;
			submit = "frm_nav.jsp";
			session.setAttribute("objAtributo", atributo);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			atributoDao.excluir((SisAtributoSatb) atributoDao.buscar(SisAtributoSatb.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("atributo.exclusao.sucesso" );
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (pesquisar) {
		try {
			/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
			if (Pagina.getParam(request, "sisGrupoAtributoSga") != null)
				atributo.setSisGrupoAtributoSga((SisGrupoAtributoSga) SisGrupoAtributoDao.buscar(SisGrupoAtributoSga.class, Long.valueOf(Pagina.getParam(request, "sisGrupoAtributoSga"))));
		
			atributo.setDescricaoSatb(Pagina.getParam(request, "descricaoSatb"));
			
			if(atributo.getSisGrupoAtributoSga()!=null){
				if(atributo.getSisGrupoAtributoSga().getSisTipoExibicGrupoSteg().getCodSteg().equals(new Long(Input.VALIDACAO))){
					atributo.setAtribInfCompSatb(Pagina.getParamStr(request, "atribInfCompSatb2").trim());
				} else {
					atributo.setAtribInfCompSatb(Pagina.getParamStr(request, "atribInfCompSatb").trim());
				}
			} else {
				atributo.setAtribInfCompSatb(Pagina.getParamStr(request, "atribInfCompSatb").trim());
			}
											
			//verifica se é do tipo ID
			String tipoValidacao = Pagina.getParamStr(request, "atribInfCompSatb2");
			if (tipoValidacao != null && (tipoValidacao.equals("autoIncrementalScript") || tipoValidacao.equals("mascaraScript") || tipoValidacao.equals("mascaraEditavelScript"))){
				
				if (request.getParameter("geral") != null) {
					atributo.setGeral(new Boolean(request.getParameter("geral")));	
				}
				
				if (request.getParameter("periodico") != null) {
					atributo.setPeriodico(new Boolean(request.getParameter("periodico")));	
				}
				
				if (request.getParameter("mascara") != null && !request.getParameter("mascara").equals(Dominios.STRING_VAZIA)) {
					atributo.setMascara(request.getParameter("mascara"));
				}
			}
			
			atributo.setIndAtivoSatb(Pagina.getParam(request, "indAtivoSatb"));
						
			session.setAttribute("objPesquisa", atributo);
		} catch (ECARException e) {
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
			atributo = (SisAtributoSatb) session.getAttribute("objPesquisa");
			List lista = atributoDao.pesquisar(atributo, request.getParameter("indTabelaUso"));
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("objPesquisa", atributo);
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("atributo.pesquisa.nenhumRegistro");
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

