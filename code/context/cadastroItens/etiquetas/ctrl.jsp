<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.ItemEstrtIndResulIettr" %>
<%@ page import="ecar.pojo.ItemEstrutFisicoIettf" %>
<%@ page import="ecar.dao.ItemEstrtIndResulDao" %>

<%@ page import="java.util.Date" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>
<%
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request,"ultimoIdLinhaDetalhado");
%>
<form name="form" method="post">
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>
	
	<!-- chave primaria-->
	<input type="hidden" name="codIett" value="<%=Pagina.getParamInt(request, "codIett")%>">
	<input type="hidden" name="codIettir" value="<%=Pagina.getParamInt(request, "codIettir")%>">

<%
	String msg = "";
	String submit = "";
	
	try{
		ItemEstrtIndResulIettr itemEstrtIndResul = new ItemEstrtIndResulIettr();
		ItemEstrtIndResulDao itemEstrtIndResulDao = new ItemEstrtIndResulDao(request);
		String nomeIettir = "";
		String labelGraficoGrupoIettir = "";
		boolean permiteAlteracao = true;
		long codIettirEncontrado = 0;

		if("incluir".equals(Pagina.getParamStr(request, "hidAcao"))){ 

			if(Pagina.getParamStr(request, "nomeIettir") != ""){
				nomeIettir = Pagina.getParamStr(request,"nomeIettir");
				labelGraficoGrupoIettir = Pagina.getParamStr(request,"labelGraficoGrupoIettir");
				
				//Verifica, quando o usuário vincula o indicador a um grupo, se já existe algum indicador com mesmo nome cadastrado
				if(!labelGraficoGrupoIettir.equals("")){
					codIettirEncontrado = itemEstrtIndResulDao.verificarExistenciaNomeIndicadorGraficoGrupo(nomeIettir,labelGraficoGrupoIettir);
				}
				if(codIettirEncontrado>0){					
					//Seta o indicador na sessão para ser utilizado quando a página for recarregada
					itemEstrtIndResulDao.setItemEstrtIndResul(request, itemEstrtIndResul);
					session.setAttribute("indicadorResultado",itemEstrtIndResul);
					%><input type="hidden" name="existeNomeGraficoGrupo" value="S"><%
					submit = "frm_inc.jsp";
					List<ItemEstrutFisicoIettf> listaQtd = itemEstrtIndResulDao.getListaQuantidadePrevista(request, itemEstrtIndResul);
					session.setAttribute("listaQtdRealizado", listaQtd);
					msg = _msg.getMensagem("itemEstrutura.indResultado.validacao.NomeGrupoGrafico");
				}else{
					itemEstrtIndResulDao.setItemEstrtIndResul(request, itemEstrtIndResul);
					List<ItemEstrutFisicoIettf> listaQtd = itemEstrtIndResulDao.getListaQuantidadePrevista(request, itemEstrtIndResul);
					
					itemEstrtIndResulDao.salvar(itemEstrtIndResul, listaQtd, 
													((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
					submit = "lista.jsp";
					msg = _msg.getMensagem("itemEstrutura.indResultado.inclusao.sucesso");				
				}
			}
		}
		
		if("alterar".equals(Pagina.getParamStr(request, "hidAcao"))){
			
			itemEstrtIndResul = (ItemEstrtIndResulIettr) itemEstrtIndResulDao.buscar(ItemEstrtIndResulIettr.class, Long.valueOf(Pagina.getParamInt(request, "codIettir")));
			
			if(Pagina.getParamStr(request, "nomeIettir") != "" && Pagina.getParamStr(request, "labelGraficoGrupoIettir") != ""){
				nomeIettir = Pagina.getParamStr(request,"nomeIettir");
				labelGraficoGrupoIettir = Pagina.getParamStr(request,"labelGraficoGrupoIettir");
					
				//Verifica, caso o usuário tenha alterado o nome do grupo e/ou do indicador, se o novo nome já existe para o grupo cadastrado
				if(!nomeIettir.equals(itemEstrtIndResul.getNomeIettir()) || !labelGraficoGrupoIettir.equals(itemEstrtIndResul.getLabelGraficoGrupoIettir())){
					codIettirEncontrado = itemEstrtIndResulDao.verificarExistenciaNomeIndicadorGraficoGrupo(nomeIettir,labelGraficoGrupoIettir);
					if(codIettirEncontrado>0){
						permiteAlteracao = false;
					}
				}
				if(permiteAlteracao){
					itemEstrtIndResulDao.setItemEstrtIndResul(request, itemEstrtIndResul);
					List<ItemEstrutFisicoIettf> listaQtd = itemEstrtIndResulDao.getListaQuantidadePrevista(request, itemEstrtIndResul);
					itemEstrtIndResul.setDataUltManutencao(new Date());
					itemEstrtIndResul.setUsuarioUsuManutencao(((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
					itemEstrtIndResulDao.alterar(itemEstrtIndResul, listaQtd);
					submit = "frm_con.jsp";
					msg = _msg.getMensagem("itemEstrutura.indResultado.alteracao.sucesso");						
				}else{
					submit = "frm_alt.jsp";
					itemEstrtIndResul = new ItemEstrtIndResulIettr();
					itemEstrtIndResulDao.setItemEstrtIndResul(request, itemEstrtIndResul);
					itemEstrtIndResul.setCodIettir(Long.valueOf(Pagina.getParamInt(request, "codIettir")));
					
					//Correção do exceção: failed to lazily initialize a collection of role
					if(itemEstrtIndResul.getAcompRealFisicoArfs()!=null){
						itemEstrtIndResul.getAcompRealFisicoArfs().size();
					}
					if(itemEstrtIndResul.getItemEstrtIndResulCorIettrcores()!=null){
						itemEstrtIndResul.getItemEstrtIndResulCorIettrcores().size();
					}
					
					//Seta o indicador na sessão para ser utilizado quando a página for recarregada
					session.setAttribute("indicadorResultado",itemEstrtIndResul);
					%><input type="hidden" name="existeNomeGraficoGrupo" value="S"><%
					List<ItemEstrutFisicoIettf> listaQtd = itemEstrtIndResulDao.getListaQuantidadePrevista(request, itemEstrtIndResul);
					session.setAttribute("listaQtdRealizado", listaQtd);
					msg = _msg.getMensagem("itemEstrutura.indResultado.validacao.NomeGrupoGrafico");
				}
			}else{
				itemEstrtIndResulDao.setItemEstrtIndResul(request, itemEstrtIndResul);
				List<ItemEstrutFisicoIettf> listaQtd = itemEstrtIndResulDao.getListaQuantidadePrevista(request, itemEstrtIndResul);
				itemEstrtIndResul.setDataUltManutencao(new Date());
				itemEstrtIndResul.setUsuarioUsuManutencao(((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
				itemEstrtIndResulDao.alterar(itemEstrtIndResul, listaQtd);
				submit = "frm_con.jsp";
				msg = _msg.getMensagem("itemEstrutura.indResultado.alteracao.sucesso");
			}
		}
		
		if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			String codigosParaExcluir[] = request.getParameterValues("excluir");
			itemEstrtIndResulDao.excluir(codigosParaExcluir,
									((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
			submit = "lista.jsp";
			msg = _msg.getMensagem("itemEstrutura.indResultado.exclusao.sucesso");
		}
		
	} catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "lista.jsp";
		msg = _msg.getMensagem(e.getMessageKey());
		
		if("excluir".equals(Pagina.getParamStr(request, "hidAcao"))){
			if(e.getMessageArgs() != null){
				for(int k = 0; k < e.getMessageArgs().length; k++){
					msg += " Indicador: " + e.getMessageArgs()[k];
				}
			}
		}
		
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg);
%>

	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 
</form>
</body>
</html>