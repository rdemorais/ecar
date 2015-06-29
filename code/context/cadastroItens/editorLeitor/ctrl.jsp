<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstrutUsuarioIettus" %>
<%@ page import="ecar.dao.ItemEstrutUsuarioDao" %>

<%@ page import="comum.util.Data" %>
<%@ page import="ecar.login.SegurancaECAR" %>

<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>
<%
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request,"ultimoIdLinhaDetalhado");
%>
<form name="form" method="post" action="">
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="codAba" value='<%=Pagina.getParamStr(request, "codAba")%>'>
	<input type="hidden" name="codIett" value='<%=Pagina.getParamInt(request, "codIett")%>'>
	<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>	
</form>

<%
	String msg = "";
	String submit = "";
	
	try{
		ItemEstrutUsuarioIettus itemEstrutUsuario = new ItemEstrutUsuarioIettus();
		ItemEstrutUsuarioDao itemEstrutUsuarioDao = new ItemEstrutUsuarioDao(request);
		
		if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			itemEstrutUsuarioDao.setItemUsuarioGrupo(request, itemEstrutUsuario);
			itemEstrutUsuarioDao.setItemEstrutUsuario(request, itemEstrutUsuario);
			
			/* Valor inicial padrão */
			itemEstrutUsuario.setIndAtivMonitIettus("N");
			itemEstrutUsuario.setIndDesatMonitIettus("N");
			itemEstrutUsuario.setIndBloqPlanIettus("N");
			itemEstrutUsuario.setIndDesblPlanIettus("N");
			itemEstrutUsuario.setIndInfAndamentoIettus("N");
			itemEstrutUsuario.setIndEmitePosIettus("N");
			/* ******************** */
			
			itemEstrutUsuario.setDataInclusaoIettus(Data.getDataAtual());
			itemEstrutUsuario.setUsuManutencao( ((SegurancaECAR)session.getAttribute("seguranca")).getUsuario() );

			itemEstrutUsuarioDao.salvar(itemEstrutUsuario, ((SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.usuario.inclusao.sucesso");
		}
		
		if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){
		
			itemEstrutUsuario = (ItemEstrutUsuarioIettus) itemEstrutUsuarioDao.buscar(ItemEstrutUsuarioIettus.class, Long.valueOf(Pagina.getParamStr(request, "codIettus")));
			//clonando objeto para tornar possível a geração do histórico
			ItemEstrutUsuarioIettus old = (ItemEstrutUsuarioIettus) itemEstrutUsuario.clone();
						
			String indProxNivelIettusAnterior = itemEstrutUsuario.getIndProxNivelIettus();
			
			// atribui o usuário que fez a manutenção
			itemEstrutUsuario.setUsuManutencao(((SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
			old.setUsuManutencao(((SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
			
			itemEstrutUsuarioDao.setItemEstrutUsuario(request, itemEstrutUsuario);
			itemEstrutUsuarioDao.alterar(itemEstrutUsuario, old, indProxNivelIettusAnterior);
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.usuario.alteracao.sucesso");
		}
		
		if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			String codigosParaExcluir[] = request.getParameterValues("excluir");
			itemEstrutUsuarioDao.excluir(codigosParaExcluir, ((SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.usuario.exclusao.sucesso");				
		}
		
	} catch ( ECARException e){
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

	<script language="javascript" type="text/javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

