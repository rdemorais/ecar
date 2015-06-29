<%@page import="comum.util.Pagina"%>
<%@page import="ecar.dao.ItemEstruturaDao"%>
<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="ecar.pojo.acompanhamentoEstrategico.ResultadoStatusContar"%>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="ecar.dao.EstatisticaDao" %>
<%@ include file="../../login/validaAcesso.jsp"%>

<%

		List<Integer> codigoObjetivoEstrategico = new ArrayList<Integer>();

		String codigoObjetivo = request.getParameter("codigoObjetivo");
		String exercicio = request.getParameter("exercicio");
		
		if(!codigoObjetivo.toString().equals("0"))
			codigoObjetivoEstrategico.add(Integer.parseInt(codigoObjetivo.toString()));

		EstatisticaDao dao = new EstatisticaDao(request);
		
		out.print(dao.estatisticaJSON(codigoObjetivoEstrategico, Long.parseLong(exercicio.toString())));
		
										
%>										