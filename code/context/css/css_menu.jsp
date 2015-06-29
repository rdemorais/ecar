<%@ page import="java.util.List" %>
<%@ page import="comum.database.SentinelaUtil" %>
<%@ page import="gov.pr.celepar.sentinela.comunicacao.SentinelaParam" %>
<%@ page import="comum.util.ItemMenu" %>
<%
/* MENU TREE */
SentinelaUtil sentinelaUtil = new SentinelaUtil(request);
SentinelaParam[] me = sentinelaUtil.getSentinelaInterface().getMenu();
List menuVertical = ItemMenu.carregaMenu(me);

int qtdeMenus = menuVertical.size();

for(int x = 1; x <= qtdeMenus; x++){
out.println("#item" + x + " {");
out.println("	background-image: url(../images/icone_menu_mais_gd.gif);");
out.println("	background-repeat: no-repeat;");
out.println("	background-color: #B8C5DC;");
out.println("	font-family: Verdana, Arial, Helvetica, sans-serif;");
out.println("	font-size: 10px;");
out.println("	display:block;");
out.println("	border: 1px solid #596D9B;");
out.println("	margin:1px;");
out.println("	width: 166px;");
out.println("}");
out.println("#item" + x + " a{");
out.println("	color: #596D9B;");
out.println("	text-decoration: none;");
out.println("	height: 14px;");
out.println("	display:block;");
out.println("	padding-left: 21px;");
out.println("	padding-top:1px;");
out.println("}");
out.println("#item" + x + " a:hover{");
out.println("	text-decoration: underline;");
out.println("}");

out.println("#submenu" + x + " {");
out.println("	background-color: #FFFFFF;");
out.println("	width: 100%;");
out.println("	display: block;");
out.println("	padding: 2px;");
out.println("	border: 1px solid #596D9B;");
out.println("	margin:1px;");
	
out.println("	width: 162px;");
//out.println("	_width: 166px;");
out.println("	padding-bottom:15px;");
	
out.println("	overflow:auto;");
out.println("	scrollbar-track-color: #F5F1F6;");
out.println("	scrollbar-face-color: #E4E6EC;");
out.println("	scrollbar-shadow-color: #E4E6EC;");
out.println("	scrollbar-highlight-color: #E4E6EC;");
out.println("	scrollbar-3dlight-color: #E4E6EC;");
out.println("	scrollbar-darkshadow-color: #A4ABC6;");
out.println("	scrollbar-arrow-color: #5A6D9C;");
out.println("	scrollbar-base-color : #E4E6EC;");
out.println("}");
}

out.println(".dtree {");
out.println("	font-family: Verdana, Arial, Helvetica, sans-serif;");
out.println("	font-size: 10px;");
out.println("	color: #596D9B;");
out.println("	white-space: nowrap;");
out.println("}");
out.println(".dtree img {");
out.println("	border: 0px;");
out.println("	vertical-align: middle;");
out.println("}");

for(int x = 0; x <= qtdeMenus; x++){
out.println("#isubmenu"+ x +"0 {");
out.println("	display:none;");
out.println("}");
}
out.println(".dtree a {");
out.println("	color: #596D9B;");
out.println("	text-decoration: none;");
out.println("}");
out.println(".dtree a.node, .dtree a.nodeSel {");
out.println("	padding: 1px 2px 1px 2px;");
out.println("	white-space: nowrap;");
out.println("}");
out.println(".dtree a.node:hover, .dtree a.nodeSel:hover {");
out.println("	color: #596D9B;");
out.println("	text-decoration: underline;");
out.println("}");
out.println(".dtree a.nodeSel {");
out.println("	background-color: #ffffff;");
out.println("}");
out.println(".dtree .clip {");
out.println("	width: 155px;");
out.println("	position:relative;");
	/*overflow: hidden;*/
out.println("}");
/* MENU TREE */



%>