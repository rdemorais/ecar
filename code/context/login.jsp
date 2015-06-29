<%@ taglib uri="http://celepar.pr.gov.br/taglibs/login.tld" prefix="sistema" %>

<%@ include file="frm_global.jsp"%>

<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>

<%@ page import="java.util.Enumeration" %>
<%@ page import="gov.pr.celepar.sentinela.comunicacao.SentinelaInterface" %>
<%@ page import="gov.pr.celepar.sentinela.comunicacao.SentinelaParam" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
	<% ConfiguracaoCfg cfgLogin = new ConfiguracaoDao().buscarConfiguracaoTituloENomeArquivoCss(); %>
	<%@ include file="include/meta.jsp"%>
	<title><%=cfgLogin.getTituloSistema()%></title>

	<link href="<%=request.getContextPath()%>/css/style_<%=cfgLogin.getEstilo().getNome()%>.css" rel="stylesheet" type="text/css">

	<script language="javascript" src="<%=request.getContextPath()%>/js/menu_retratil.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/js/focoInicial.js"></script>
	<script language="javascript" src="<%=request.getContextPath()%>/js/popUp.js"></script>
	<script language="javascript">
		function showMenuPublico(link){
			var janela = abreJanela(link + "?acessoPublico=true", 785, 500, "menuPublico");
			janela.focus();
		}
	</script>
</head>
<body>


	<%
	
	/*************************ITEM LINK ***********************/
	
	/******IMPORTANTE: ********/
	// O link a ser passado no email deve ser do tipo
	//http://localhost:8080/ecar/login.jsp?evento=1&param=funcao=SITUACAO,funcaoParecer=ACOMP5,codTpfa=5,periodo=1,tela=,codAri=965,codAcomp=74
	//Onde o evento é o numero do evento a ser tratado. Ver ecar/util/Dominios.java
	//e param deve conter todos os parametros necessarios para a navegação da página redirecionada. Os parametros devem ser separados por virgula 
	
	
	//guarda o codigo do evento
	String evento = (String) request.getParameter("evento");	
	
	//guarda todos os parametros necessários para o carregamento da página
	String param = (String) request.getParameter("param");

	//se existir um evento
	if(evento != null) {
		session.setAttribute("evento", evento);
		if(param != null) {
			session.setAttribute("param", param);
		}
		
	}
	
	
	/*************************ITEM LINK ***********************/
		

	// atributo na sessão para utilizar o DownloadFile sem logar
	request.getSession().setAttribute("downloadDeslogado", new Boolean(true));
	%>
	<table  border="0" cellpadding="0" cellspacing="0" class="cabec">
  		<tr>
    		<td width="45%" align="left">
    			<!-- imagem da esquerda no cabeçalho -->
		    	<img border="0" src="<%=_pathEcar%>/images/estilo/logo_cabec_esq_<%=cfgLogin.getEstilo().getNome()%>.jpg"/>			
    		</td>
		    <td width="10%" align="center">&nbsp;</td>
		    <td width="45%" align="right">
	    	</td>
  		</tr>
	</table> 
	<%
	// Pega a interface de comunicação do sentinela
	SentinelaInterface sentinelaInterface = SentinelaUtil.getSentinelaInterface();
	if(sentinelaInterface != null){
		SentinelaParam[] sentParam = sentinelaInterface.getMenuPublico();
		if (sentParam != null && sentParam.length > 0){
	%>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="menu_portal_inicial">
		<tr>
		  	<td>
			   	<td class="usuario" align="left">&nbsp;</td>
		  	</td>
		  	<td class="funcoes">
	<%
			for(int i = 0; i < sentParam.length; i++){
				String paramAux = sentParam[i].getParamAux()[0];
	%>
				<a href="#" onclick="javascript:showMenuPublico('<%=paramAux%>')">
					
					<%=sentParam[i].getNome()%>
				</a>
	<%
			}
	%>
			</td>
		</tr>
	</table>
	<%
		}
	}
	
	
	String porta = String.valueOf(request.getLocalPort());
	if( !"8443".equals(porta) ) { %>
	<script type="text/javascript">
		var showed = '0';
		function showTip() {
			var div = document.getElementById('securityalert');
			div.style.display = ( showed == '1' ? 'none' : 'block');
			showed = ( showed == '1' ? '0' : '1');
		}
	</script>
	
	<center> <br>	
	</center> <%
	} %>
	<br>
	<div id="login">
		<table class="texto_negrito_login" width="100%">
			<tr>
	        	<td>Acesso ao e-Car Ministério da Saúde</td>
	    	</tr>
		</table>
		<sistema:login 
			action="<%=request.getContextPath()+"/login/ctrl.jsp"%>" 
			classe="login" 
			classeInterna="texto_login"
			classeBotao="botao" 
			classeLabel="texto_login"
			classeTitulo="texto_negrito_login"
			classeAlternativa="texto_login"
			classeAlternativaInterna="texto_login"
			classeAlternativaMensagem="texto_erro_login"
			classeOpcoes="texto_login"
	
			tituloEsqueciSenha="Esqueci a Senha"
			tituloTrocaSenha="Troca de Senha"
	
			labelName="Usuário"
			labelPass="Senha"
			labelBotao="Entrar"		   		   
			labelCPF="CPF"
			labelEsqueciSenha="Esqueci a Senha"
			labelMudarSenha="Mudar Senha"
			labelNovaSenha="Nova Senha"
			labelRedigiteSenha="Redigite Senha"
			
			desativaErro="nao"
			timeRefresh="5"
			/>
	</div>
	<br>
	<div id="rodape">
		<hr>
		<div class="text_rodape">
			Navegadores compatíveis:<br>Mozilla 1.0.x ou superior e Internet Explorer 5.5 ou superior <br><br>
			Melhor visualizado em 1024x768 com Mozilla 1.0.x<br>
			<%@ include file="versao.jsp"%>
		</div>
		<div align="center">
			<a href="http://www.saude.gov.br/" target="_blank"><img src="<%=request.getContextPath()%>/images/ms_rodape.gif" border="0" align="absmiddle"></a>
			&nbsp;&nbsp;&nbsp;
			<% 
			if( "8443".equals(porta) ) { %>
			<a href="http://pt.wikipedia.org/wiki/Https" target="_blank">
			<img src="<%=request.getContextPath()%>/images/img-https-celepar.png" border="0" align="absmiddle"></a> <%
			} %>			 
		</div>
	</div>
</body>
<%@ include file="include/mensagemAlert.jsp"%>
</html>
