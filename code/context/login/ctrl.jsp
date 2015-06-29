
<jsp:directive.page import="ecar.dao.permissao.link.GerenciadorPermissaoAcessoLink"/>
<jsp:directive.page import="ecar.dao.permissao.link.PermissaoAcessoLinkCommand"/>
<jsp:directive.page import="ecar.dao.permissao.link.ErroPermissaoAcessoLinkEnum"/>
<jsp:useBean id="seguranca" scope="session" class="ecar.login.SegurancaECAR"/>

<%@ page import="ecar.pojo.ConfiguracaoCfg" %>
<%@ page import="ecar.pojo.RegControleAcessoRca" %>
<%@ page import="ecar.dao.ConfiguracaoDao" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.RegControleAcessoRcaDao" %>
<%@ page import="ecar.login.SegurancaECAR" %>
<%@ page import="comum.database.SentinelaUtil" %>
<%@ page import="gov.pr.celepar.sentinela.comunicacao.SentinelaParam" %>

<%@ include file="../frm_global.jsp"%>

<%@page import="ecar.portal.Portal"%>
<%@page import="ecar.pojo.EmpresaEmp"%>
<%@page import="ecar.dao.EmpresaDao"%><html lang="pt-br">
<head>
<%@ include file="../include/meta.jsp"%>
</head>
<body>
<form name="form" method="post">
	<input type="hidden" name="msgOperacao" value=""> 
</form>

<%
	String submit;
	String msg;
	String pagInicial = new String();
	ConfiguracaoCfg configuracao = new ConfiguracaoCfg();
	ConfiguracaoDao configuracaoDao = new ConfiguracaoDao(request);
	
	try {
		// verificar comunicação com o Sentinela
		 
		try {
			SentinelaParam[] menuSentinela = sentinelaUtil.getSentinelaInterface().getMenu();
    	} catch (Exception e) {
    		e.printStackTrace();
    		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = "Seu grupo não possui funções vinculadas no Sentinela";
			Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
			submit = _pathEcar + "/login.jsp";
			
			//remove os atributos da session
			session.removeAttribute("seguranca");
			if((String) session.getAttribute("evento") != null){
				session.removeAttribute("evento");
			}
			if((String) session.getAttribute("param")!= null){
				session.removeAttribute("param");
			}
			
			%>
			<script language="javascript">
				document.form.action = "<%=submit%>";
				document.form.submit();
			</script> 
			<%
    	}
    	
    	
		//Obter demais dados do usuário logado no banco de dados ECAR
		seguranca.getDadosUsuario(request);
	 
		List confg = configuracaoDao.listar(ConfiguracaoCfg.class, null);
		if(confg != null && confg.size() > 0){
			configuracao = (ConfiguracaoCfg) confg.iterator().next();
			session.setAttribute("configuracao", configuracao);
			Portal portal = new Portal(request);
			session.setAttribute("portal", portal);
			EmpresaEmp cabEmpresa = new EmpresaDao(request).buscarConfiguracoesEmpresa();
			session.setAttribute("cabEmpresa", cabEmpresa);
		} 

		if(seguranca.getPaginaInicialUsuario() != null && !"".equals(seguranca.getPaginaInicialUsuario().trim())) {
			//Seleciona a Pagina Inicial do sistema a partir do Usuario Logado
			if(seguranca.getPaginaInicialUsuario().indexOf("?") > 0) {
				pagInicial = seguranca.getPaginaInicialUsuario() + "&mostrarPopUps=true";
			}
			else {
				pagInicial = seguranca.getPaginaInicialUsuario() + "?mostrarPopUps=true";
			}
		} else if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni() != null && configuracao.getSisAtributoSatbByCodSapadrao().getAtribInfCompSatb() != null) {
			//Seleciona a Pagina Inicial do sistema a partir da configuração do sistema - SisAtributo
			if(configuracao.getSisAtributoSatbByCodSapadrao().getAtribInfCompSatb().indexOf("?") > 0) {
				pagInicial = configuracao.getSisAtributoSatbByCodSapadrao().getAtribInfCompSatb() + "&mostrarPopUps=true";
			}
			else {
				pagInicial = configuracao.getSisAtributoSatbByCodSapadrao().getAtribInfCompSatb() + "?mostrarPopUps=true";
			}
		} else {
			pagInicial = "index.jsp?mostrarPopUps=true";
		}
		
		
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		msg = e.getMessage();
	} catch (Exception e){
		
		
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}	
		Date dataAtual = new Date();
	if (seguranca.isAutenticado()) {
		/*************************LINK MENSAGEM ***********************/
		
		try{	
			// verifica se o endereço do servidor alterou
			String esquema  = request.getScheme();
			String servidor = request.getServerName(); 
			int porta    = request.getServerPort();
			
		    String contextPath = esquema + "://" +
		    	 servidor;
		    
		    if( porta > 0 ) {
				contextPath += ":";
				contextPath += request.getServerPort();
		    }
		    
		    contextPath += request.getContextPath();
			
			if(!configuracao.getContextPath().equals(contextPath)) {
				configuracao.setContextPath(contextPath);
			}
			
			
			
			
			String evento = (String) session.getAttribute("evento");
			String param = (String) session.getAttribute("param");
			
			
			//se existir um evento
			if(evento != null) {
				GerenciadorPermissaoAcessoLink gerenciador = (GerenciadorPermissaoAcessoLink) GerenciadorPermissaoAcessoLink.getInstancia();
				if(gerenciador != null) {
					pagInicial = gerenciador.criarLinkRedirecionamento(evento, param, seguranca, this.getServletContext());
				}
				//remove os atributos da session
				session.removeAttribute("evento");
				if(param != null) {			
					session.removeAttribute("param");
				}
			}
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			//msg = e.getMessage();
			
			%>
				<script language="Javascript">
				alert('<%=e.getMessageKey()%>');
				</script>

			<%		
			
		}		
			/*************************LINK MENSAGEM ***********************/
		try {
			// testa se está habilitado a gravação de registro de acesso no sistema (Mantis: 10395)
			if("S".equalsIgnoreCase(_msg.getMensagem("gravarRegistroAcesso"))) {
				/*Se usuario esta autenticado, registra o acesso ao sistema na tabela TB_Reg_Controle_Acesso_RCA*/
				RegControleAcessoRcaDao regDao = new RegControleAcessoRcaDao(request);
				RegControleAcessoRca reg = new RegControleAcessoRca();
				reg.setTpContAcessoRca("L");
				reg.setDataAcessoRca(dataAtual);
				reg.setUsuarioUsu(seguranca.getUsuario());
				regDao.salvar(reg);
			}
		} catch (Exception e){
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		}
		
		submit = _pathEcar + "/" + pagInicial;
		session.setAttribute("seguranca", seguranca);
	} else {
		msg = "Este usuário está cadastrado no sistema de segurança Sentinela, mas não foi cadastrado/ativado no sistema " + configuracao.getTituloSistema();
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
		submit = _pathEcar + "/login.jsp";
		
		//remove os atributos da session
		session.removeAttribute("seguranca");
		if((String) session.getAttribute("evento") != null){
			session.removeAttribute("evento");
		}
		if((String) session.getAttribute("param")!= null){
			session.removeAttribute("param");
		}
		
	}
%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 
</body>
</html>
