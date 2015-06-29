<%@ page import="java.util.List" %>
<%@ page import="ecar.dao.UploadTipoCategoriaDao" %>
<%@ page import="ecar.pojo.UploadTipoCategoriaUtc" %>

<%
UploadTipoCategoriaDao uploadTipoCatDao = new UploadTipoCategoriaDao(request);
UploadTipoCategoriaUtc uploadTipoCat = new UploadTipoCategoriaUtc();

List listaUtc = uploadTipoCatDao.getListUploadTipoCategoria();

for(int x = 0; x <= listaUtc.size(); x++){
	out.println("#anexotipomenu"+x+".borda{");
	out.println("border-top: 1px solid #AF8800; border-left: 1px solid #AF8800;");
	out.println("border-right: 1px solid #AF8800; padding: 5px; z-index: 1;");
	out.println("}");
	out.println("#anexotipomenu"+x+".aberto{");
	out.println("border-top: 1px solid #AF8800; border-left: 1px solid #AF8800;");
	out.println("border-right: 1px solid #AF8800; padding: 5px; z-index: 1;");
	out.println("background-color: #F5E699;");
	out.println("}");
	out.println("#anexotipo"+x+".tipo{");
	out.println("font-family: Verdana, Arial, Helvetica, sans-serif;");
	out.println("font-size: 10px; font-weight: bold;");
	out.println("color:	#000000; text-decoration: none; padding-left: 15px;");
	out.println("}");
	out.println("#anexotipo"+x+".aberto{");
	out.println("font-family: Verdana, Arial, Helvetica, sans-serif;");
	out.println("font-size: 10px; font-weight: bold;");
	out.println("color: #000000; text-decoration:none; padding-left: 15px;");
	out.println("background-repeat: no-repeat;");
	out.println("background-image: url(../images/expanded_button.gif);");
	out.println("}");
}

%>