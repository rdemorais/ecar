<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.pojo.SisTipoExibicGrupoSteg" %>
<%@ page import="ecar.dao.SisTipoExibicGrupoDao" %>
<%@ page import="ecar.pojo.SisTipoOrdenacaoSto" %>
<%@ page import="ecar.dao.SisTipoOrdenacaoDao" %>
<%@ page import="comum.util.Data" %>
<%@ page import="java.util.List" %>

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
	<!-- campo de controle -->
	<input type="hidden" name="indTabelaUso" value="<%=Pagina.trocaNull(Pagina.getParam(request, "indTabelaUso"))%>">
</form>

<%
	SisGrupoAtributoSga grupoAtributo = new SisGrupoAtributoSga();
	SisGrupoAtributoDao grupoAtributoDao = new SisGrupoAtributoDao(request);
	SisTipoExibicGrupoDao sisTipoExibicGrupoDao = new SisTipoExibicGrupoDao(request);
	SisTipoOrdenacaoDao sisTipoOrdenacaoDao = new SisTipoOrdenacaoDao(request);
	
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
			grupoAtributo.setDescricaoSga(Pagina.getParamStr(request, "descricaoSga").trim());
			grupoAtributo.setSisTipoExibicGrupoSteg( (SisTipoExibicGrupoSteg) sisTipoExibicGrupoDao.buscar(SisTipoExibicGrupoSteg.class, Long.valueOf(Pagina.getParam(request, "sisTipoExibicGrupoSteg"))));
			//if (Pagina.getParam(request, "indTabelaUso").equals("U")){
			//	if (Pagina.getParam(request, "sisTipoExibicGrupoSteg") != null && !Pagina.getParamStr(request, "sisTipoExibicGrupoSteg").equals("")) {
			//		grupoAtributo.setSisTipoExibicGrupoCadUsuSteg((SisTipoExibicGrupoSteg) sisTipoExibicGrupoDao.buscar(SisTipoExibicGrupoSteg.class, Long.valueOf(Pagina.getParam(request, "sisTipoExibicGrupoSteg"))));			
			//	} 
			//}
			//else{
				if (Pagina.getParam(request, "sisTipoExibicGrupoCadUsuSteg") != null && !Pagina.getParamStr(request, "sisTipoExibicGrupoCadUsuSteg").equals("")) {
					grupoAtributo.setSisTipoExibicGrupoCadUsuSteg((SisTipoExibicGrupoSteg) sisTipoExibicGrupoDao.buscar(SisTipoExibicGrupoSteg.class, Long.valueOf(Pagina.getParam(request, "sisTipoExibicGrupoCadUsuSteg"))));			
				} 
			//}
			grupoAtributo.setSisTipoOrdenacaoSto( (SisTipoOrdenacaoSto) sisTipoOrdenacaoDao.buscar(SisTipoOrdenacaoSto.class, Long.valueOf(Pagina.getParam(request, "sisTipoOrdenacaoSto"))));
			grupoAtributo.setIndObrigatorioSga(Pagina.getParamStr(request, "indObrigatorioSga"));
			grupoAtributo.setSeqApresentacaoSga(Integer.valueOf(Pagina.getParamInt(request, "seqApresentacaoSga")));
			grupoAtributo.setIndAtivoSga(Pagina.getParamStr(request, "indAtivoSga"));
			grupoAtributo.setDataInclusaoSga(Data.getDataAtual());
			grupoAtributo.setIndTabelaUsoSga(Pagina.getParamStr(request, "indTabelaUso"));
			grupoAtributo.setIndCadUsuSga(Pagina.getParamStr(request, "indCadUsuSga"));
			grupoAtributo.setIndCadSiteSga(Pagina.getParamStr(request, "indCadSiteSga"));			
			grupoAtributo.setIndSistemaSga("N");
			grupoAtributo.setDescInfoComplementar(Pagina.getParamStr(request, "descInfCompSga").trim());
			
			refazPesquisa = false;
			submit = "frm_inc.jsp";
			grupoAtributoDao.salvar(grupoAtributo);
			msg = _msg.getMensagem("demGrupoAtributo.inclusao.sucesso");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			/* busca o objeto para manter o que já esta cadastrado */
			grupoAtributo = (SisGrupoAtributoSga) grupoAtributoDao.buscar(SisGrupoAtributoSga.class, Long.valueOf(Pagina.getParam(request, "codigo")));
			
			grupoAtributo.setDescricaoSga(Pagina.getParamStr(request, "descricaoSga").trim());
			//grupoAtributo.setSisTipoExibicGrupoSteg( (SisTipoExibicGrupoSteg) sisTipoExibicGrupoDao.buscar(SisTipoExibicGrupoSteg.class, Long.valueOf(Pagina.getParam(request, "sisTipoExibicGrupoSteg"))));

			//Correção do Mantis 8067
			//if (Pagina.getParam(request, "indTabelaUso").equals("U")){
			//	if (Pagina.getParam(request, "sisTipoExibicGrupoSteg") != null && !Pagina.getParamStr(request, "sisTipoExibicGrupoSteg").equals("")) {
			//		grupoAtributo.setSisTipoExibicGrupoCadUsuSteg((SisTipoExibicGrupoSteg) sisTipoExibicGrupoDao.buscar(SisTipoExibicGrupoSteg.class, Long.valueOf(Pagina.getParam(request, "sisTipoExibicGrupoSteg"))));			
			//	} 
			//} else {
				if (Pagina.getParam(request, "sisTipoExibicGrupoCadUsuSteg") != null && !Pagina.getParamStr(request, "sisTipoExibicGrupoCadUsuSteg").equals("")) {
					grupoAtributo.setSisTipoExibicGrupoCadUsuSteg((SisTipoExibicGrupoSteg) sisTipoExibicGrupoDao.buscar(SisTipoExibicGrupoSteg.class, Long.valueOf(Pagina.getParam(request, "sisTipoExibicGrupoCadUsuSteg"))));			
				} else {
					grupoAtributo.setSisTipoExibicGrupoCadUsuSteg(null);
				}
			//}
			
				
			grupoAtributo.setSisTipoOrdenacaoSto( (SisTipoOrdenacaoSto) sisTipoOrdenacaoDao.buscar(SisTipoOrdenacaoSto.class, Long.valueOf(Pagina.getParam(request, "sisTipoOrdenacaoSto"))));
			grupoAtributo.setIndObrigatorioSga(Pagina.getParamStr(request, "indObrigatorioSga"));
			grupoAtributo.setSeqApresentacaoSga(Integer.valueOf(Pagina.getParamInt(request, "seqApresentacaoSga")));
			grupoAtributo.setIndAtivoSga(Pagina.getParamStr(request, "indAtivoSga"));
			grupoAtributo.setIndCadUsuSga(Pagina.getParamStr(request, "indCadUsuSga"));
			grupoAtributo.setIndCadSiteSga(Pagina.getParamStr(request, "indCadSiteSga"));
			grupoAtributo.setDescInfoComplementar(Pagina.getParamStr(request, "descInfCompSga").trim());			
			
			grupoAtributoDao.alterar(grupoAtributo, Long.valueOf(Pagina.getParam(request, "sisTipoExibicGrupoSteg")));
			msg = _msg.getMensagem("demGrupoAtributo.alteracao.sucesso");
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			grupoAtributoDao.excluir((SisGrupoAtributoSga) grupoAtributoDao.buscar(SisGrupoAtributoSga.class, Long.valueOf(Pagina.getParam(request, "codigo"))));
			msg = _msg.getMensagem("demGrupoAtributo.exclusao.sucesso" );
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
			grupoAtributo.setDescricaoSga(Pagina.getParam(request, "descricaoSga"));
		
			if(Pagina.getParam(request, "indCadUsuSga") != null)
				grupoAtributo.setIndCadUsuSga(Pagina.getParam(request, "indCadUsuSga"));
		
			if(Pagina.getParam(request, "sisTipoExibicGrupoSteg") != null)
				grupoAtributo.setSisTipoExibicGrupoSteg( (SisTipoExibicGrupoSteg) sisTipoExibicGrupoDao.buscar(SisTipoExibicGrupoSteg.class, Long.valueOf(Pagina.getParam(request, "sisTipoExibicGrupoSteg"))));
				
			if(Pagina.getParam(request, "sisTipoExibicGrupoCadUsuSteg") != null)
				grupoAtributo.setSisTipoExibicGrupoCadUsuSteg( (SisTipoExibicGrupoSteg) sisTipoExibicGrupoDao.buscar(SisTipoExibicGrupoSteg.class, Long.valueOf(Pagina.getParam(request, "sisTipoExibicGrupoCadUsuSteg"))));
			
			if(Pagina.getParam(request, "sisTipoOrdenacaoSto") != null)
				grupoAtributo.setSisTipoOrdenacaoSto( (SisTipoOrdenacaoSto) sisTipoOrdenacaoDao.buscar(SisTipoOrdenacaoSto.class, Long.valueOf(Pagina.getParam(request, "sisTipoOrdenacaoSto"))));
			
			grupoAtributo.setIndObrigatorioSga(Pagina.getParam(request, "indObrigatorioSga"));

			if(Pagina.getParam(request, "indCadSiteSga") != null)
				grupoAtributo.setIndCadSiteSga(Pagina.getParam(request, "indCadSiteSga"));

			if(Pagina.getParam(request, "seqApresentacaoSga") != null)
				grupoAtributo.setSeqApresentacaoSga(Integer.valueOf(Pagina.getParamInt(request, "seqApresentacaoSga")));
			
			grupoAtributo.setIndAtivoSga(Pagina.getParam(request, "indAtivoSga"));
			
			grupoAtributo.setIndTabelaUsoSga(Pagina.getParam(request, "indTabelaUso"));
			
			grupoAtributo.setDescInfoComplementar(Pagina.getParam(request, "descInfCompSga"));
			
			session.setAttribute("objPesquisa", grupoAtributo);
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
			grupoAtributo = (SisGrupoAtributoSga) session.getAttribute("objPesquisa");
			List lista = grupoAtributoDao.pesquisar(grupoAtributo, new String[] {"descricaoSga","asc"});
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
			} else {
				Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("demGrupoAtributo.pesquisa.nenhumRegistro");
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

