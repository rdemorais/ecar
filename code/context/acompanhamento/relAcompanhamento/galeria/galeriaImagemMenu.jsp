<%@ include file="../../../login/validaAcesso.jsp"%>
<%@ include file="../../../frm_global.jsp"%>

<%@ page import="ecar.dao.ItemEstruturaUploadCategoriaDao" %>
<%@ page import="ecar.pojo.ItemEstrUplCategIettuc" %>
<%@ page import="ecar.pojo.ItemEstrutUploadIettup" %>

<%@ page import="comum.util.Util" %> 

<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %> 

<%
try{ 
	ItemEstruturaUploadCategoriaDao itemEstUCDao = new ItemEstruturaUploadCategoriaDao(request); 
	
	String strCodIettuc = Pagina.getParamStr(request, "codIettuc");
	
	ItemEstrUplCategIettuc itemEstUC = (ItemEstrUplCategIettuc) itemEstUCDao.buscar(ItemEstrUplCategIettuc.class, Long.valueOf(strCodIettuc));
%>

<html lang="pt-br"> 
<head>
<%@ include file="../../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<!--<script language="javascript" src="../../../js/menu_retratil.js"></script>-->
</head>

<body class="body_anexos">
<!--<body class="corpo_popup">-->

<!--<div class="titulo_anexos"><%=itemEstUC.getNomeIettuc()%></div>-->
<!--<div class="texto_negrito"><%=itemEstUC.getNomeIettuc()%></div>-->

<table id="list_anexos" cellspacing="0">
	<tr>
		<th align="center"><%=itemEstUC.getNomeIettuc()%></th>
	</tr>
<%
	if(itemEstUC.getItemEstrutUploadIettups().size() > 0){
		Iterator itItemEstUp = itemEstUCDao.getItemEstrutUploadIettups(itemEstUC).iterator();

		String cor = "sim";
		List listaArquivos = new ArrayList();
		while (itItemEstUp.hasNext()){
			ItemEstrutUploadIettup itemEstrutUpload = (ItemEstrutUploadIettup) itItemEstUp.next();
%>
			<tr class="list_anexos_cor_<%=cor%>">
				<td><a href="galeriaImagemVisualizacao.jsp?codImagem=<%=itemEstrutUpload.getCodIettup()%>" target="imagem" title="<%=Util.formataByte(itemEstrutUpload.getTamanhoIettup())%>"><%=itemEstrutUpload.getDescricaoIettup()%></a></td>
			</tr>
<%
			listaArquivos.add(itemEstrutUpload.getCodIettup());
			if(!"sim".equals(cor))
				cor = "sim";
			else
				cor = "nao";
		}
		request.getSession().setAttribute("listaArquivos", listaArquivos);
	}else{
%>	
		<tr>
			<td align="center"><br>Nenhum anexo cadastrado<br><br></td>
		</tr>
<%
	}
%>
</table>


<%
} catch (ECARException e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	Mensagem.alert(_jspx_page_context, _msg.getMensagem(e.getMessageKey()));
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>

<br>

</body>
</html>