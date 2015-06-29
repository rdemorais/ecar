<script type="text/javascript" src="<%=_pathEcar%>/js/cookie.js"></script>
<%   //Se o parâmetro "ocultar" for passado(ocultar=true) ele deve esconder o menu lateral
	 //sem verificar o cookie. Caso contrário, ele verifica o cookie ou seta como aberto(valor default). 
	String ocultar = request.getParameter("ocultar");
	String fecharMenu = request.getParameter("fecharMenu");	
%>
<script>

//Controle para exibir ou não o menu lateral, feito via parâmetros
<%
if (ocultar!=null && "true".equals(ocultar)){ %>
	botao('fechado');
	mudaEstado('fechado');
<%
}else{ %> 
	if(getCookie("estadoMenu")!=null){
		botao(getCookie("estadoMenu"));
	}else{
		botao('aberto');
	}
<%}%>

//Controle, no menu lateral, dos itens abertos e fechados   
if(getCookie("menuAberto")!=null){
	 abremenu(getCookie("menuAberto"));
}
<%
if ("true".equals(fecharMenu)){
	String qtdeMenus = (String) session.getAttribute("qtdeMenusPrincipais");
	if(qtdeMenus != null && !"".equals(qtdeMenus)){
		int qtdeMenusPrincipais = Integer.parseInt(qtdeMenus);
		for(int iMenus = 1; iMenus <= qtdeMenusPrincipais; iMenus++){
	%>
		fechamenu(<%=iMenus%>);
	<%
		}
	}
}
%>
</script>