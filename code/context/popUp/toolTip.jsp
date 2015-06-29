<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<%

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="pt-br">
  <head>
	<%@ include file="/titulo.jsp"%> 
   	<%@ include file="/include/meta.jsp"%>
    <link rel="stylesheet" href="<%=path%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
  </head>
  <body onload="javascript:moveTo(0,0);" class="dica" onblur="javascript:this.close();">
	  <div id="toolTip" class="nameTip">
		Dica	
      </div>
	  <div id="toolTip" class="descTip"><%=request.getParameter("tip")%></div>
  </body>
</html>
