
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.dao.ItemEstruturaBeneficiarioDao" %>
<%@ page import="ecar.pojo.ItemEstrtBenefIettb"%>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="java.util.Date"%>

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
<input type="hidden" name="codIett" value=<%=Pagina.getParamStr(request, "codIett")%>>
<input type="hidden" name="codBnf" value=<%=Pagina.getParamStr(request, "codBnf")%>>
<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>	

<%
	String msg = "";
	String submit = "";
	try{
			ItemEstrtBenefIettb itemEstruturaBeneficiario = new ItemEstrtBenefIettb();
			ItemEstruturaBeneficiarioDao itemEstruturaBeneficiarioDao = new ItemEstruturaBeneficiarioDao(request);			
			/* ?clearsession? */
			if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
				itemEstruturaBeneficiarioDao.setItemEstruturaBeneficiario(request, itemEstruturaBeneficiario);	
				
				itemEstruturaBeneficiarioDao.salvar(itemEstruturaBeneficiario);
				submit = "lista.jsp";
				msg = _msg.getMensagem("itemEstrutura.beneficiario.inclusao.sucesso");
			}	
			if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){
				itemEstruturaBeneficiario = itemEstruturaBeneficiarioDao.buscar(Long.valueOf(Pagina.getParamStr(request, "codIett")),Long.valueOf(Pagina.getParamStr(request, "codBnf")));
				itemEstruturaBeneficiarioDao.setItemEstruturaBeneficiario(request, itemEstruturaBeneficiario);
				itemEstruturaBeneficiario.setUsuarioUsuManutencao(((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
				itemEstruturaBeneficiario.setDataUltManutencaoIettb(new Date());
				itemEstruturaBeneficiarioDao.alterar(itemEstruturaBeneficiario);
				submit = "frm_con.jsp";
				msg = _msg.getMensagem("itemEstrutura.beneficiario.alteracao.sucesso");
			}
			if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
				String codigosParaExcluir[] = request.getParameterValues("excluir");
				itemEstruturaBeneficiarioDao.excluir(codigosParaExcluir, Long.valueOf(Pagina.getParamStr(request, "codIett")),
						((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
				submit = "lista.jsp";
				msg = _msg.getMensagem("itemEstrutura.beneficiario.exclusao.sucesso");				
			}
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "lista.jsp";
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
		submit = "lista.jsp";
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