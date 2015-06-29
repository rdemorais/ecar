
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>

<%@ page import="comum.database.HibernateUtil" %>

<%@ page import="ecar.portal.Portal" %>
<%@ page import="ecar.portal.bean.MenuTopoBean" %>
<%@ page import="ecar.util.Dominios" %>

<%@ page import="ecar.dao.ConfiguracaoDao" %>
<%@ page import="ecar.pojo.ConfiguracaoCfg" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<!-- PP: Coloquei para facilitar uso destas tags para programação -->
<!-- http://jakarta.apache.org/taglibs/ -->
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/tlds/x.tld" prefix="x" %>
<%@ taglib uri="/WEB-INF/tlds/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/tlds/fn.tld" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tlds/sql.tld" prefix="sql" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-datetime.tld" prefix="datetime" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-application.tld" prefix="application" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-benchmark.tld" prefix="benchmark" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-dbtags.tld" prefix="dbtags" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-log.tld" prefix="log" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-mailer.tld" prefix="mailer" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-page.tld" prefix="page" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-random.tld" prefix="random" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-regexp.tld" prefix="regexp" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-request.tld" prefix="request" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-response.tld" prefix="response" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-session.tld" prefix="session" %>
<%@ taglib uri="/WEB-INF/tlds/taglibs-string.tld" prefix="string" %>
<!-- PP: Coloquei para facilitar uso Table com exportação para varios formatos (ex.: xls, pdf, etc) -->
<!-- http://displaytag.sourceforge.net/11/ -->

<%
ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
Portal portal = (Portal)session.getAttribute("portal");
EmpresaEmp cabEmpresa = (EmpresaEmp)session.getAttribute("cabEmpresa");
String loginUsuario = "";
String linkEncerrar = "";
String cabImagem = "";
List cabConfg = new ArrayList();

//Portal portal = null;

//try {
	//ecar.dao.EmpresaDao cabEmpresaDao = new ecar.dao.EmpresaDao(request);
	//cabConfg = cabEmpresaDao.listar(ecar.pojo.EmpresaEmp.class, null);	
//} catch (Exception ex1) {
	//org.apache.log4j.Logger.getLogger(this.getClass()).error(ex1);
	/* pode dar uma excessao de ECARException */
//}

try {
	if (session.getAttribute("seguranca") != null) {

		loginUsuario = ("Usuário: " + ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario().getNomeUsuSent().toUpperCase());
	}
} catch (Exception ex2) { 
	org.apache.log4j.Logger.getLogger(this.getClass()).error(ex2);
	/* pode dar exception de session invalidated na tela de login */
}


// Tag para a tela de espera no carregamento de longas listagens		
// Alterado seu posicionamento para cima, para evitar problemas na interpretação do IE6 ou <
// Por Rogério (06/03/2007)
if(request.getAttribute("exibirAguarde") != null) { %>
	<%@ include file="/include/exibirAguarde.jsp" %> <%
} %>


<%@page import="ecar.login.SegurancaECAR"%>
<%@page import="ecar.pojo.EmpresaEmp"%><script language="javascript" src="<%=_pathEcar%>/js/popUp.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracao.getEstilo().getNome()%>.css" type="text/css">

<link rel="stylesheet" href="<%=_pathEcar%>/css/css_menu_<%=configuracao.getEstilo().getNome()%>.jsp" type="text/css">
<% if(configuracao.getEstilo().getNome().equals("pac") && session.getAttribute("seguranca") == null) { %>
<div id="barra-amarela">
    <div id="assinatura">
       <img src="<%=_pathEcar%>/images/estilo/AssinaPresRepublica.gif" alt="Presidência da República Federativa do Brasil" title="Presidência da República Federativa do Brasil"  />
    </div>
    <div id="select">
      <label for="pr" class="hiddenStructure">Selecione um destaque do governo</label>
      <select class="pr" id="pr" name="sltVoce"
              title="P&aacute;ginas do Governo Federal"
              onchange="try{if (this.value!='') window.open(this.value);}catch(e){}"
              size="1">
        <option value="&quot; &quot;" selected="selected">Destaques do Governo</option>
        <option value="http://www.brasil.gov.br">Portal do Governo Federal</option>

        <option value="http://www.e.gov.br">Portal de Servi&ccedil;os do Governo</option>
        <option value="http://www.agenciabrasil.gov.br">Portal da Ag&ecirc;ncia de Not&iacute;cias</option>
        <option value="http://www.previdenciasocial.gov.br/reforma/">Reforma da Previd&ecirc;ncia</option>
        <option value="http://www.fomezero.gov.br">Programa Fome Zero</option>
      </select>
    </div>
    <div id="marca-brasil">
      <a href="http://www.brasil.gov.br" target="_blank">
        <img src="<%=_pathEcar%>/images/estilo/MarcaBrasil.gif" alt="Brasil um país de todos" title="Brasil um país de todos" />       </a>     </div>
    <div class="visualClear"></div>
</div>
<% } %>

<table  border="0" cellpadding="0" cellspacing="0" class="cabec">
  <tr>
    <td width="45%" align="left">
    	<!-- imagem da esquerda no cabeçalho -->
    	<%
    	
    	String paginaInicial = "";    	    	
    	    	    	
    	if ((SegurancaECAR)session.getAttribute("seguranca") != null){
    	
	    	if(((SegurancaECAR)session.getAttribute("seguranca")).getPaginaInicialUsuario() != null && !"".equals(((SegurancaECAR)session.getAttribute("seguranca")).getPaginaInicialUsuario().trim())) {
	    		if(((SegurancaECAR)session.getAttribute("seguranca")).getPaginaInicialUsuario().indexOf("?") > 0) {
	    			paginaInicial = request.getContextPath() + "/" + ((SegurancaECAR)session.getAttribute("seguranca")).getPaginaInicialUsuario() + "&mostrarPopUps=true";
	    		}
	    		else{
					paginaInicial = request.getContextPath() + "/" + ((SegurancaECAR)session.getAttribute("seguranca")).getPaginaInicialUsuario() + "?mostrarPopUps=true";
	    		}
			}
	    	else if(configuracao.getSisGrupoAtributoSgaByCodSgaGrAtrPgIni() != null && configuracao.getSisAtributoSatbByCodSapadrao().getAtribInfCompSatb() != null) {
	    		//Seleciona a Pagina Inicial do sistema a partir da configuração do sistema - SisAtributo
				if(configuracao.getSisAtributoSatbByCodSapadrao().getAtribInfCompSatb().indexOf("?") > 0) {
					paginaInicial = request.getContextPath() + "/" + configuracao.getSisAtributoSatbByCodSapadrao().getAtribInfCompSatb() + "&mostrarPopUps=true";
				}
				else {
					paginaInicial = request.getContextPath() + "/" + configuracao.getSisAtributoSatbByCodSapadrao().getAtribInfCompSatb() + "?mostrarPopUps=true";
				}    	
		    } else {
		    	paginaInicial = request.getContextPath() + "/" + "index.jsp?mostrarPopUps=true";
			}
    	}
    	%>
    	<a href="<%=paginaInicial%>" id="link<%=String.valueOf(0)%>">
			<img border="0" src="<%=_pathEcar%>/images/estilo/logo_cabec_esq_<%=configuracao.getEstilo().getNome()%>.jpg">			
		</a>
		
    	
    </td>
    <td width="10%" align="center">&nbsp;</td>
    <td width="45%" align="right">
    	<!-- imagem da direita no cabeçalho -->
 <!--  	UsuarioUsu usuarioImagem = null;
		String hashNomeArquivo = null;
 -->
		
	    <%//if(cabConfg != null && cabConfg.size() > 0){
			    //ecar.pojo.EmpresaEmp cabEmpresa = new ecar.pojo.EmpresaEmp();
			    //cabEmpresa = (ecar.pojo.EmpresaEmp) cabConfg.iterator().next();
		
//			    usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
//			    
//			    hashNomeArquivo = Util.calcularHashNomeArquivo(configura.getRaizUpload(), cabEmpresa.getLogotipoEmp());
//				Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, configura.getRaizUpload(), cabEmpresa.getLogotipoEmp());
		    %>
		    <img src="<%=_pathEcar%>/DownloadFile?tipo=open&RemoteFile=<%=configuracao.getRaizUpload() + cabEmpresa.getLogotipoEmp()%>">
	    <%//}%>
    </td>
  </tr>
</table>

<%
if (session.getAttribute("seguranca") != null){
	//portal = new Portal(request);
%>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="menu_portal">
	  	<tr>
		  	<td align="left"> 
				<% if(configuracao.getEstilo().getNome().equals(Dominios.ESTILO_DEFAULT)) { %>
				<img src="<%=_pathEcar%>/portal/images/pixel.png" width="18" height="17" align="left">
				<% } %>
					<%
					int k = 1;
					Iterator it = portal.getPortalBean().getMenuTopoBean().iterator();
					while(it.hasNext()) {
						MenuTopoBean menu = (MenuTopoBean)it.next();
						String titulo = "";
						if(configuracao.getEstilo().getNome().equals(Dominios.ESTILO_DEFAULT)) {
							titulo = menu.getTitulo();
						} else {
							titulo = menu.getTitulo().toLowerCase();
						}
					%>
						<table align="left" cellspacing="0" id="table<%=String.valueOf(k)%>" class="item_portal">
							<tr>
								<td>
									<span id="span<%=String.valueOf(k)%>" class="item_portal_span"><%=menu.getDescricao()%></span>
									<%
									String links = menu.getLink();
									if(links.contains("?")) {
										links += "&fecharMenu=true";
									} else {
										links += "?fecharMenu=true";
									}
									
									links += "&titulo=" + menu.getTituloPagina();
									%>
									<a href="<%=_pathEcar%>/abrirUrl.jsp?url=<%=links%>" id="link<%=String.valueOf(k)%>" onMouseOver="showMenuPortal(<%=String.valueOf(k)%>)" onMouseOut="hideMenuPortal(<%=String.valueOf(k)%>)" class="item_portal_a">
										<%=titulo%>
									</a>
								</td>
							</tr>
						</table>
					<%
						k++;
					}
					%>
			</td>
		</tr>
	</table>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="submenu_portal">
		<tr>
		  	<td>
			   	<td class="usuario" align="left"><%=loginUsuario%></td>
		  	</td>
			<td class="funcoes">
				<a href="<%=_pathEcar%>/abrirUrl.jsp?url=<%=_pathEcar%>/portal/pages/contato.jsp"><img src="<%=_pathEcar%>/images/estilo/icon_faleconosco_<%=configuracao.getEstilo().getNome()%>.png" border="0"> Fale Conosco</a>
				<a href="<%=_pathEcar%>/abrirUrl.jsp?url=<%=_pathEcar%>/portal/pages/duvidas_frequentes.jsp"><img src="<%=_pathEcar%>/images/estilo/icon_duvidas_<%=configuracao.getEstilo().getNome()%>.png" border="0"> Dúvidas</a>
				<a href="<%=_pathEcar%>/abrirUrl.jsp?url=<%=_pathEcar%>/portal/pages/glossario.jsp"><img src="<%=_pathEcar%>/images/estilo/icon_glossario_<%=configuracao.getEstilo().getNome()%>.png" border="0"> Glossário</a>
				<a href="<%=_pathEcar%>/abrirUrl.jsp?url=<%=_pathEcar%>/portal/pages/pesquisa.jsp"><img src="<%=_pathEcar%>/images/estilo/icon_pesquisa_<%=configuracao.getEstilo().getNome()%>.png" border="0"> Pesquisa</a>
				<a href="<%=_pathEcar%>/abrirUrl.jsp?url=<%=_pathEcar%>/sair.jsp"><img src="<%=_pathEcar%>/images/estilo/icon_sair_<%=configuracao.getEstilo().getNome()%>.png" border="0"> Sair</a>
			</td>
		</tr>
			
	</table>
 
	<util:popUpsTag pathEcar="<%=_pathEcar%>"/>
 
<%
}
%>

<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
<script language="javascript">
		function showMenuPortal(n) {
			document.getElementById('span'+n).className = 'item_portal_span_hover';
			document.getElementById('table'+n).className= 'item_portal_hover';
			document.getElementById('link'+n).className = 'item_portal_a_hover';
		}
		
		function hideMenuPortal(n) {
			document.getElementById('span'+n).className = 'item_portal_span';
			document.getElementById('table'+n).className= 'item_portal';
			document.getElementById('link'+n).className = 'item_portal_a';
		}
		
		function showInfoUsu(n,codtpfa,pos) {
			if (pos == "-1")
			document.getElementById('spanInfoUsu'+n+'_'+codtpfa+'_'+pos).className = 'item_InfoUsu_span_hover';
			if (pos == "1")
			document.getElementById('spanInfoUsu'+n+'_'+codtpfa+'_'+pos).className = 'item_InfoUsu_right1_span_hover';
			if (pos == "10")
			document.getElementById('spanInfoUsu'+n+'_'+codtpfa+'_'+pos).className = 'item_InfoUsu_right10_span_hover';
		}
		
		function hideInfoUsu(n,codtpfa,pos) {
			document.getElementById('spanInfoUsu'+n+'_'+codtpfa+'_'+pos).className = 'item_InfoUsu_span';
		}
		
		function viewFieldTip(obj, spanname) {

			if(obj != null) {	
				if(isIE) {
					var screenwidth = document.body.clientWidth;
				} else {
					var screenwidth = document.width;
				}
				var objspan     = document.getElementById(spanname);
				var imgposw     = 0;
	
			    while (obj) { <% /* Obtem a posicao do objeto na tela */ %>
			        imgposw += obj.offsetLeft;
			        obj      = obj.offsetParent;
			    }
	
				if(isIE == false) { <% /* Apenas mostra o tooltip se for != IE */ %>
					if(screenwidth - imgposw <= 300) {
						objspan.className = 'dicaright';
					} else {
						objspan.className =  'dicaleft';
					}
					//objspan.className = ( screenwidth - imgposw <= 300 ? 'dicaright' : 'dicaleft');
			    	//objspan.style.display = 'block';				
				}
				
				objspan.style.display = 'block';
			}	
		}
		
		function viewFieldTipPopUp(tip) {
			var page = '<%=_pathEcar%>/popUp/toolTip.jsp?tip='+tip;
			var options  = 'tollbar=no, directories=no, menubar=no, status=no, scrollbars=yes, ';
			    options += 'resizable=no, width=325, height=200';
			window.open(page, 'tooltip', options);
		}
		
		function viewFieldTipDicaParecerPopUp(tip){
						
			var page = '<%=_pathEcar%>/popUp/toolTipDicaParecer.jsp?' + tip;
			var options  = 'tollbar=no, directories=no, menubar=no, status=no, scrollbars=yes, ';
			    options += 'resizable=no, width=525, height=200';
			
			window.open(page, 'toolTipDicaParecer', options);			
		}
		
		function noViewFieldTip(spanname) {
			var objspan = document.getElementById(spanname);
	    	objspan.style.display = 'none';
		}


		function aviso()
		{
			if (flag==<%=configuracao.getTempoSessao()%> & flag != 0)
			{ 
		    	alert('Atenção: Esta sessão cairá em <%=configuracao.getTempoSessao()%> minutos.\nSalve suas alterações.'); 
		    } 
		        else
			{ 
		       	if (flag != 99 & flag != 0) setTimeout('aviso()',<%=(session.getMaxInactiveInterval()-(configuracao.getTempoSessao().intValue()*60)) * 1000 %>);  
		    }
		    flag=<%=configuracao.getTempoSessao()%>; 
		} 

		function aviso2()
		{ 
		    if (flag==<%=configuracao.getTempoSessao()%>)
			{ 
	        	HoraHor = 0; 
				HoraMin = 0; 
				HoraSec = 0;
					  			 
				document.getElementById("tempoExp").innerHTML = "00:00:00"; 
				alert('Esta sessão foi encerrada!!!\nFaça o login novamente.');              
	            flag=99; 
		    } 
		        else
			{ 
		        setTimeout('aviso2()',<%=session.getMaxInactiveInterval()*1000%>); 
		    } 
		} 

		// Exibe uam hora no formato HH:MM:SS
		function sivamtime() 
		{
				  		  		  		
				var tempoServ = <%=session.getMaxInactiveInterval()%>; 
				if (tempoServ >= 3600)
				{
				   HoraHor = parseInt(tempoServ / 3600); 
				   tempoServ =  tempoServ % 3600; 
				}  	
				else
				HoraHor = 0; 	
				HoraMin = tempoServ / 60;  		  		  		
				HoraSec = 0;
			    		  		
				document.getElementById("tempoExp").innerHTML = (HoraHor<10?"0"+HoraHor:HoraHor) + ":" + (HoraMin<=9?"0"+HoraMin:HoraMin) + ":" + (HoraSec<9?"0"+HoraSec:HoraSec);   		
				
				setTimeout("sivamtime3(0,0,1)", 1000);
				
		}	

		// Esta Função soma horas
		function sivamtime3(addH, addM, addS) 
		{
		    
				if ((HoraHor > 0) || (HoraMin > 0) || (HoraSec > 0))
				{
					hour3 = HoraHor - parseInt(addH);
		  		min3 =  HoraMin  - parseInt(addM);
					sec3 =  HoraSec - parseInt(addS);

					if (sec3<0) { sec3 = 59 ; min3 = parseInt(min3)-1; }
		  		if (min3<0) { min3 = 59; hour3 = parseInt(hour3)-1; }
		  		if (hour3<=0) { hour3 = 0; }
				
					if (sec3<=9) { sec3="0"+sec3; }
		  		if (min3<=9) { min3="0"+min3; }
		  		if (hour3<=9) { hour3="0"+hour3; }
				  		
					HoraHor = hour3; 
		  		HoraMin = min3; 
		  		HoraSec = sec3; 
				  		
				    document.getElementById("tempoExp").innerHTML = hour3 + ":" + min3 + ":" + sec3;   		
					setTimeout("sivamtime3(0,0,1)", 1000);  		  		
			    }
				
		}

		function window_load(){ 
		<% if (session.getAttribute("seguranca") != null) { %>	
		    	flag=-1; 
		        aviso2(); 
		        aviso(); 
				sivamtime(); 		
		<% } %>
		} 
</script>


<%
if (session.getAttribute("seguranca") == null){ //Se não estiver logado, feche a sessão.REF. BUG 4407
	try {
		HibernateUtil.closeSession();
	}
	catch(Exception e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	}
}
%>