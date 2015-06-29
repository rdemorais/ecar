<head>
<%@page import="comum.util.Pagina"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.pgxp.statixnate.business.implementation.Performance"%>
<%@page import="comum.database.HibernateUtil"%>
<%@page import="br.com.pgxp.statixnate.util.Conversor"%>
<%@page import="comum.util.Data"%>
<%@page import="ecar.webservices.pacinter.CadastroService"%>
<%@page import="ecar.webservices.pacinter.Cadastro"%>
<%@page import="java.util.List"%>
<%@page import="ecar.webservices.pacinter.DtoItem"%>
<%@page import="ecar.webservices.pacinter.DtoPtc"%>
<%@page import="ecar.webservices.pacinter.DtoApt"%>
<%@page import="ecar.api.facade.*"%>
</head> 
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">

<%

/*
try {
    CadastroService service = new CadastroService();
    Cadastro port = service.getCadastroPort();
    
    Iterator it = port.getItemTodos().iterator();

    while(it.hasNext()){
        System.out.println("Result = " + ( (DtoItem) it.next()).getDescricao());
    }
    
    it = port.getPtcTodos().iterator();

    while(it.hasNext()){
        System.out.println("Result = " + ( (DtoPtc) it.next()).getDescricao());
    }
    
    it =  port.getAptTodos().iterator();

    while(it.hasNext()){
        System.out.println("Result = " + ( (DtoApt) it.next()).getDescricao());
    }

} catch (Exception ex) {
    System.out.println(ex.getMessage());
}
*/

String statisticTitle = "Ecar".concat(EcarData.now());
Performance perf = new Performance();
perf.avaliacaoHibernate(HibernateUtil.currentSessionFactory().getStatistics(), false, statisticTitle, false);
Iterator itperf = perf.avaliacaoHibernate(HibernateUtil.currentSessionFactory().getStatistics(), false, false, "ecar").iterator();
String linha;
while(itperf.hasNext()){
linha = (String) itperf.next();
%>
<tr><td>
		<%=linha%>
</td></tr>		

<%}; 
%>

</table>
</body>

</html>