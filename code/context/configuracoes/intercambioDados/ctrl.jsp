<%@ include file="../../login/validaAcesso.jsp"%>

<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>

<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.dao.TipoFuncAcompDao" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Set" %>

<%@ include file="../../frm_global.jsp"%>


<%@page import="ecar.pojo.intercambioDados.PerfilIntercambioDadosPflid"%>
<%@page import="ecar.dao.intercambioDados.PerfilIntercambioDadosDao"%>

<%@page import="ecar.pojo.intercambioDados.funcionalidade.PerfilIntercambioDadosCadastroPidc"%>
<%@page import="ecar.dao.EstruturaDao"%>
<%@page import="ecar.pojo.EstruturaEtt"%>
<%@page import="ecar.pojo.intercambioDados.funcionalidade.TipoFuncionalidadeEnum"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
</form>

<%
	String msg = null;
	String msg_pesquisa = null;
	String submit = "frm_inc.jsp";
	String submitErro = "";
	
	try{
		String hidAcao = request.getParameter("hidAcao");
		
		if (hidAcao.equals("incluir")) {
			PerfilIntercambioDadosPflid perfilIntercambioDadospflid = new PerfilIntercambioDadosCadastroPidc();
			PerfilIntercambioDadosDao perfilIntercambioDadosDao = new PerfilIntercambioDadosDao(request);
			
			perfilIntercambioDadosDao.setPerfilIntercambioDadosPflid(request, perfilIntercambioDadospflid);
			session.setAttribute("estruturaEttCriacaoItemImp",request.getParameter("estruturaEttCriacaoItemImp"));
			submitErro = "frm_inc.jsp";
			perfilIntercambioDadosDao.salvar(request, perfilIntercambioDadospflid);
			
			session.removeAttribute("perfilConsultado");
			submit = "frm_inc.jsp";
			msg = _msg.getMensagem("configuracao.intercambioDados.inclusao.sucesso");
		} else if (hidAcao.equals("recarregaEstruturaItemNivelSuperior")){
			EstruturaDao estruturaDao = new EstruturaDao(request);
			PerfilIntercambioDadosDao perfilDao = new PerfilIntercambioDadosDao(request);
			Long idEstruturaBaseSelecionada = new Long(Pagina.getParamLong(request,"estruturaEttBaseImp"));
			
			PerfilIntercambioDadosPflid perfilConsultado = new PerfilIntercambioDadosCadastroPidc();
			
			perfilDao.setPerfilIntercambioDadosPflid(request,perfilConsultado);
			
			session.setAttribute("idEstruturaBaseSelecionada",idEstruturaBaseSelecionada);
			session.setAttribute("perfilConsultado",perfilConsultado);
			
			//Deve remover o atributo da Estrutura de Item de Nivel Superior para o combo de criação de itens seja carregado vazio.
			session.removeAttribute("idEstruturaItemNivelSuperiorSelecionada");

			
			submit = Pagina.getParamStr(request,"jspOrigem");
		} else if (hidAcao.equals("recarregaEstruturaCriacaoItem")){
			EstruturaDao estruturaDao = new EstruturaDao(request);
			PerfilIntercambioDadosDao perfilDao = new PerfilIntercambioDadosDao(request);
			
			PerfilIntercambioDadosPflid perfilConsultado = new PerfilIntercambioDadosCadastroPidc();
			Long idEstruturaItemNivelSuperior = new Long(Pagina.getParamLong(request,"estruturaEttItemNivelSuperiorImp"));
			perfilDao.setPerfilIntercambioDadosPflid(request,perfilConsultado);
			
			session.setAttribute("perfilConsultado",perfilConsultado);
			session.setAttribute("idEstruturaItemNivelSuperiorSelecionada",idEstruturaItemNivelSuperior);
			session.setAttribute("estruturaEttCriacaoItemImp",request.getParameter("estruturaEttCriacaoItemImp"));
			
			
			submit = Pagina.getParamStr(request,"jspOrigem");
		} else if ("pesquisar".equalsIgnoreCase(hidAcao)) {
			PerfilIntercambioDadosPflid perfilIntercambioDadospflid = new PerfilIntercambioDadosCadastroPidc();
			PerfilIntercambioDadosDao perfilIntercambioDadosDao = new PerfilIntercambioDadosDao(request);
			
			perfilIntercambioDadosDao.setPerfilIntercambioDadosPflid(request, perfilIntercambioDadospflid);
						
			List listaPerfilPesquisado = perfilIntercambioDadosDao.pesquisar(perfilIntercambioDadospflid);
			
			session.setAttribute("listaPerfilPesquisado", listaPerfilPesquisado);
			session.setAttribute("perfilConsultadoInicio",perfilIntercambioDadospflid);
			
			if (listaPerfilPesquisado.isEmpty()){
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("perfil.intercambioDados.nenhumRegistro");
				session.removeAttribute("perfilConsultado");
			} else {
				session.removeAttribute("hidRegistro");
				submit = "frm_nav.jsp";
			}
			
		} else if ("iniciarPesquisa".equalsIgnoreCase(hidAcao)) {
			session.removeAttribute("perfilConsultado");
			session.removeAttribute("idEstruturaBaseSelecionada");
			session.removeAttribute("idEstruturaItemNivelSuperiorSelecionada");
			submit = "frm_pes.jsp";
		} else if ("alterar".equalsIgnoreCase(hidAcao)){
			PerfilIntercambioDadosDao perfilIntercambioDadosDao = new PerfilIntercambioDadosDao(request);
			PerfilIntercambioDadosPflid perfilConsultadoInicio = (PerfilIntercambioDadosPflid)session.getAttribute("perfilConsultadoInicio");
			PerfilIntercambioDadosPflid perfilAlterado = (PerfilIntercambioDadosPflid)session.getAttribute("perfilConsultado");
			perfilIntercambioDadosDao.buscar(perfilAlterado,perfilAlterado.getCodPflid());
			
			
			submitErro = "frm_alt.jsp";
			perfilIntercambioDadosDao.setPerfilIntercambioDadosPflid(request, perfilAlterado);
			perfilIntercambioDadosDao.alterar(perfilAlterado);
			
			msg = _msg.getMensagem("configuracao.intercambioDados.alteracao.sucesso");

			List listaPerfilPesquisado = perfilIntercambioDadosDao.pesquisar(perfilConsultadoInicio);
			
			session.setAttribute("listaPerfilPesquisado", listaPerfilPesquisado);
			
			//verifica se a lista pesquisada está vazia.
			if (listaPerfilPesquisado.isEmpty()){
				submit = "frm_pes.jsp";
				msg_pesquisa = _msg.getMensagem("perfil.intercambioDados.nenhumRegistro");
				session.removeAttribute("perfilConsultado");
			} else {
				submit = "frm_nav.jsp";
				
				//verifica se o registro alterado ainda está na lista pesquisada.
				if (listaPerfilPesquisado.contains(perfilAlterado)){
					session.setAttribute("hidRegistro",new Integer(listaPerfilPesquisado.indexOf(perfilAlterado)));
				}
			}
		} else if ("excluir".equalsIgnoreCase(hidAcao)){
			PerfilIntercambioDadosDao perfilIntercambioDadosDao = new PerfilIntercambioDadosDao(request);
			PerfilIntercambioDadosPflid perfilConsultadoInicio = (PerfilIntercambioDadosPflid)session.getAttribute("perfilConsultadoInicio");
			PerfilIntercambioDadosPflid perfilExcluido = (PerfilIntercambioDadosPflid)session.getAttribute("perfilConsultado");
			
			perfilIntercambioDadosDao.excluir(perfilExcluido);

			msg = _msg.getMensagem("configuracao.intercambioDados.exclusao.sucesso");
			
			List listaPerfilPesquisado = perfilIntercambioDadosDao.pesquisar(perfilConsultadoInicio);
			
			session.setAttribute("listaPerfilPesquisado", listaPerfilPesquisado);
			
			//verifica se a lista pesquisada está vazia.
			if (listaPerfilPesquisado.isEmpty()){
				submit = "frm_pes.jsp";
				msg_pesquisa = _msg.getMensagem("perfil.intercambioDados.nenhumRegistro");
				session.removeAttribute("perfilConsultado");
			} else {
				submit = "frm_nav.jsp";				
			}
		} else if ("iniciarIncluir".equalsIgnoreCase(hidAcao)){ 
			session.removeAttribute("perfilConsultado");
			session.removeAttribute("idEstruturaBaseSelecionada");
			session.removeAttribute("idEstruturaItemNivelSuperiorSelecionada");
			submit = "frm_inc.jsp";
		} else if ("cancelar".equalsIgnoreCase(hidAcao)) {
			PerfilIntercambioDadosDao perfilIntercambioDadosDao = new PerfilIntercambioDadosDao(request);
			PerfilIntercambioDadosPflid perfilConsultadoTela = (PerfilIntercambioDadosPflid)session.getAttribute("perfilConsultado");
			PerfilIntercambioDadosPflid perfilAtual = (PerfilIntercambioDadosPflid)perfilIntercambioDadosDao.buscar(PerfilIntercambioDadosPflid.class,perfilConsultadoTela.getCodPflid());
			
			List listaPerfilPesquisado = (List)session.getAttribute("listaPerfilPesquisado");
			int posicao = listaPerfilPesquisado.indexOf(perfilAtual); 
			
			submit = "frm_nav.jsp";
			session.setAttribute("hidRegistro",new Integer(posicao));
		}
	} catch(ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		msg = _msg.getMensagem(e.getMessageKey());
		submit = submitErro;
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}

	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 	
	
	if (msg_pesquisa != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg_pesquisa); 	
	
%>

<script language="javascript">
document.form.action = "<%=submit%>";
document.form.submit();
</script> 
</body>
</html>