<%@ page import="java.io.*" %> 
<html lang="pt-br">
<head>
<title>Donwload File</title>
<%@ include file="../../include/meta.jsp"%>
</head>

<body class="bgBranco">
<b> 
<%
String file = request.getParameter("RemoteFile");
String fileName = file.substring(file.lastIndexOf("/")+1);


response.setContentType("image/jpeg");

//para visualizar o arquivo
response.setHeader("Cache-Control", "no-cache; filename=" + fileName);

java.io.InputStream is = null;
OutputStream out2 = null;
try {
    out2 = response.getOutputStream();
	is = new FileInputStream(file);
	int i;
    while ((i=is.read()) != -1) {
        out2.write(i);
    }
} finally {
    if(out2 != null) {
         out2.flush();
         out2.close();
   }
    if (is != null) {
        is.close();
    }
}
%> 
</b> 
</body>
</html>
