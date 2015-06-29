<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.SegmentoItemSgti" %> 
<%@ page import="ecar.dao.SegmentoItemDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>  
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="comum.util.Data" %>
 
<html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body> 

<form name="form" method="post">
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
</form>

<%
String submit = "taxacao.jsp";	
try{
	String acao = Pagina.getParamStr(request, "hidAcao");
	
	if ("".equals(acao)) {
		acao = (request.getParameter("acao") != null ? request.getParameter("acao") : "");
	}

	UsuarioUsu usuario = new UsuarioUsu();	
	usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();

	SegmentoItemDao segmentoItemDao = new SegmentoItemDao(request);
	
		if ("TAXACAO".equals(acao) || "GENERICA".equals(acao)){
			request.setAttribute("qtd", "00");
			
			List list = segmentoItemDao.getListSegmentoItemTaxacoes(usuario);
					
			long l  = 0; 
			l = segmentoItemDao.getQtdSegmentoItemTaxacoes(usuario);
			
			int dia = Data.getDia(Data.getDataAtual());
			int mes = Data.getMes(Data.getDataAtual())+1;
						
			String dt = "("+(dia>=10?String.valueOf(dia):"0"+dia);
			dt += "/"+(mes>=10?String.valueOf(mes):"0"+mes);
			dt += " "+Data.getDiaSemanaNome(Data.getDataAtual())+")";
						
			long qtd = l>3?3:l;
						
			request.getSession().setAttribute("qtdSegmentoItem", "");
			request.getSession().setAttribute("strQtdSegmentoItem", "");
			request.getSession().setAttribute("strDia", dt);
			request.getSession().setAttribute("listaSegmentoItemTaxacao", list);
		} else if ("TAXACAO_TODOS".equals(acao)){
			submit = "taxacao_todos.jsp";	
			request.setAttribute("qtd", "00");			
			request.setAttribute("data", "00");	
			request.getSession().setAttribute("listaTaxacoes", segmentoItemDao.getListSegmentoItemTaxacoes(usuario));				
		} else if ("PESQUISAR".equals(acao)){
			submit = "taxacao.jsp";			
						
			request.getSession().setAttribute("palavra", Pagina.getParamStr(request, "palavra"));			
			request.getSession().setAttribute("dataInicial", Pagina.getParamStr(request, "dataInicial"));			
			request.getSession().setAttribute("dataFinal", Pagina.getParamStr(request, "dataFinal"));			
			request.getSession().setAttribute("fonte", Pagina.getParamStr(request, "fonte"));	
			request.getSession().setAttribute("editorias", Pagina.getParamStr(request, "editorias"));							
			request.getSession().setAttribute("listaSegmentoItemTaxacao", segmentoItemDao.pesquisaTaxacoes(usuario));				
		} else if ("DETALHE".equals(acao)) {
			submit = "taxacao_detalhe.jsp";
			
			long cod = Long.parseLong(Pagina.getParamStr(request, "codSgti"));	
			int dia = Data.getDia(Data.getDataAtual());
			int mes = Data.getMes(Data.getDataAtual())+1;
			
			String dt = "("+(dia>=10?String.valueOf(dia):"0"+dia);
			dt += "/"+(mes>=10?String.valueOf(mes):"0"+mes);
			dt += " "+Data.getDiaSemanaNome(Data.getDataAtual())+")";
						
			request.getSession().setAttribute("segmentoItem", segmentoItemDao.getSegmentoItemCodSgti(cod));
			request.getSession().setAttribute("strDia", dt);
		}
	
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

