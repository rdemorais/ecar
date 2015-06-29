<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.util.Dominios"%>
<%@ page import="ecar.pojo.EstruturaEtt"%> 
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %> 
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>
<%@ page import="ecar.pojo.ObjetoEstrutura"%>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.dao.ItemEstruturaDao" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %> 
<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.dao.AcompReferenciaItemDao"%>

<%@ page import="java.util.List" %> 
<%@ page import="java.util.Set" %> 
<%@ page import="java.util.Iterator" %> 
<%@ page import="comum.util.Data"%>


<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@page import="ecar.dao.ItemEstruturaDao"%><html lang="pt-br"> 
<head>

<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

</head>

<body>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 

<div id="conteudo">
<%
   ItemEstruturaDao iettDao = new ItemEstruturaDao(null);
	ItemEstruturaIett iett = new ItemEstruturaIett();
	//iett.get;
	//List lista = ;
%>

<display:table name="<%=iettDao.pegarHistorico()%>" export="enable" defaultsort="2" >

       <display:column property="codIett" title="ID" href="frm_con.jsp"/>
       <display:column property="descricaoIett" title="Descrição" nulls="--------"  />
       <display:column property="siglaIett" title="Sigla"/>
        
<display:footer media="all" />
</display:table>
<br>
 


<form name="form" method="post">

</form>
<br>
</div>
</body>
</html>