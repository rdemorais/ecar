<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="comum.util.Pagina"/>
<jsp:directive.page import="ecar.pojo.ItemEstUsutpfuacIettutfa"/>
<jsp:directive.page import="ecar.dao.ItemEstUsutpfuacDao"/>
<jsp:directive.page import="ecar.pojo.UsuarioAtributoUsua"/>

<%

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<%@ include file="/titulo.jsp"%> 
    <%@ include file="/include/meta.jsp"%>
    <link rel="stylesheet" href="<%=path%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
  </head>
  <body onload="javascript:moveTo(0,0);" class="dica" onblur="javascript:this.close();">
	  <div id="toolTip" class="nameTip">
		Dica	
      </div> 
      <%
      	//SE FOR ALTERAR O USUÁRIO
      	      	      	
      	StringBuffer strBfDica = new StringBuffer("");
      	StringBuffer strBfDicaMouse = new StringBuffer("");
   	
   		ItemEstUsutpfuacDao itemEstUsuDao = new ItemEstUsutpfuacDao(request);
   	
   		Long codIett = Long.valueOf(request.getParameter("codIett"));
   		Long codTpfa = Long.valueOf(request.getParameter("codTpfa"));
   	
		ItemEstUsutpfuacIettutfa itemEstUsu 
				= itemEstUsuDao.buscar(codIett, codTpfa);
		if(itemEstUsu != null) {
			if (itemEstUsu.getUsuarioUsu() != null) {
			
				strBfDica.append("<table  cellpadding='0' cellspacing='0'>");
			
				if(itemEstUsu.getUsuarioUsu().getNomeUsu() != null) {
					strBfDica.append("<tr><td><b>Resp</b>: ").append(itemEstUsu.getUsuarioUsu().getNomeUsu()).append("</tr></td>");
					strBfDicaMouse.append("<b>Resp: </b>").append(itemEstUsu.getUsuarioUsu().getNomeUsu()).append("<br>");
				} else {
					strBfDica.append("<tr><td><b>Resp</b>: ").append("").append("</tr></td>");
				}
				
				if(itemEstUsu.getUsuarioUsu().getComercTelefoneUsu() != null) {
					strBfDica.append("<tr><td><b>Tel</b>: " ).append(itemEstUsu.getUsuarioUsu().getComercTelefoneUsu()).append("</tr></td>");
				} else {
					strBfDica.append("<tr><td><b>Tel</b>: " ).append("").append("</tr></td>");
				}
				
				if(itemEstUsu.getUsuarioUsu().getEmail1UsuSent() != null) {	 	
					strBfDica.append("<tr><td><b>E-mail</b>: " ).append(itemEstUsu.getUsuarioUsu().getEmail1UsuSent()).append("</tr></td>");
				} else {
					strBfDica.append("<tr><td><b>E-mail</b>: " ).append("").append("</tr></td>");
				}	
				
				strBfDica.append("</table>");	
					
			} else if (itemEstUsu.getSisAtributoSatb() != null) {
				if (itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas() != null) {
					
					strBfDica.append("<table border='0' cellpadding='0' cellspacing='0' >");
					strBfDica.append("<tr><td><b>GRUPO</b>: ").append(itemEstUsu.getSisAtributoSatb().getDescricaoSatb()).append("</tr></td>");
					strBfDicaMouse.append("<b>GRUPO</b>: ").append(itemEstUsu.getSisAtributoSatb().getDescricaoSatb()).append("<br>");
					strBfDica.append("</table>");
					
					
					Iterator itUsuarios = itemEstUsu.getSisAtributoSatb().getUsuarioAtributoUsuas().iterator();
					
					strBfDica.append("<table border='0' cellpadding='0' cellspacing='0' style='BORDER-COLLAPSE: collapse'>");
					strBfDica.append("<tr><td><b>Resp</b></td>");
					strBfDica.append("<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tel</b></td>");
					strBfDica.append("<td><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;E-mail</b></tr></td>");
					
					while (itUsuarios.hasNext()) {
						UsuarioAtributoUsua usuarioAtributoUsua = (UsuarioAtributoUsua) itUsuarios.next();
						
						if(usuarioAtributoUsua.getUsuarioUsu() != null) {
							
							strBfDica.append("<tr>");
							
							if(usuarioAtributoUsua.getUsuarioUsu().getNomeUsu() != null) {
								strBfDica.append("<td>").append(usuarioAtributoUsua.getUsuarioUsu().getNomeUsu()).append("</td>");
								//strBfDicaMouse.append("<b>Resp: </b>").append(usuarioAtributoUsua.getUsuarioUsu().getNomeUsu()).append("<br>");
							} else {
								strBfDica.append("</td>");
								//strBfDicaMouse.append("<b>Resp: </b>").append("<br>");			
							}	
							
							if(usuarioAtributoUsua.getUsuarioUsu().getComercTelefoneUsu() != null) {
								strBfDica.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" ).append(usuarioAtributoUsua.getUsuarioUsu().getComercTelefoneUsu()).append("</td>");
							} else {
								strBfDica.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>" );
							}
							
							if(usuarioAtributoUsua.getUsuarioUsu().getEmail1UsuSent() != null) { 	
								strBfDica.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" ).append(usuarioAtributoUsua.getUsuarioUsu().getEmail1UsuSent()).append("</td>");
							} else  {
								strBfDica.append("<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>");		
							}
					
							strBfDica.append("</tr>");
							
						}
					}
										
					strBfDica.append("</table>");
										
				}
			} 
		}
      %>   
	  <div id="toolTip" class="descTip"><%=strBfDica%></div>
  </body>
</html>
