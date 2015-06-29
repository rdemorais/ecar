<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.ItemEstruturaCriterioDao" %>
<%@ page import="ecar.exception.ECARException" %>

<%@ page import="ecar.pojo.UsuarioUsu" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
</head> 
<body>
<form name="form" method="post">  
<%
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
%>
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAba" value=<%=Pagina.getParamStr(request, "codAba")%>>
<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="codIett" value=<%=Pagina.getParamStr(request, "codIett")%>>
<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>
<%
	String msg = "";
	String submit = "";
	try{ 
			ItemEstruturaCriterioDao itemEstruturaCriterioDao = new ItemEstruturaCriterioDao(request);			
			/* ?clearsession? */
			if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){
				//itemEstruturaCriterioDao.salvar( Long.valueOf(Pagina.getParamStr(request, "codCriterio")),  Long.valueOf(Pagina.getParamStr(request, "codIett")));

				String codigos = Pagina.getParamStr(request, "codCriterio");
				String codigoIett = Pagina.getParamStr(request, "codIett");
				String[] codigosParaIncluir = codigos.split("\\|");
				
				for(int i = 0; i < codigosParaIncluir.length; i++){
					try{						
						itemEstruturaCriterioDao.salvar(Long.valueOf(codigosParaIncluir[i]), Long.valueOf(codigoIett), 
							((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());

						
						submit = "lista.jsp";
						msg = _msg.getMensagem("itemEstrutura.criterio.inclusao.sucesso");

					} catch(ECARException e) {
						org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
						/*
						* Se cair aqui é por que o código já existe.
						* Então, não faz nada e passa pro próximo código.
						*/
						
						submit = "lista.jsp";
						msg = _msg.getMensagem(e.getMessageKey());
						break;
					}
				}
				
			}	

			if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
				String codigosParaExcluir[] = request.getParameterValues("excluir");
				
				itemEstruturaCriterioDao.excluir(codigosParaExcluir, Long.valueOf(Pagina.getParamStr(request, "codIett")), 
					((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
				
				submit = "lista.jsp";
				msg = _msg.getMensagem("itemEstrutura.criterio.exclusao.sucesso");				
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