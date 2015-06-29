
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.EfItemEstContaEfiec" %>
<%@ page import="ecar.dao.ItemEstruturaContaOrcamentoDao" %>
<%@ page import="comum.util.Pagina" %>

<%
	String scriptsFR = Pagina.getParamStr(request, "disabled") + " onchange='atualizaComboFonteRecurso(document.form);'";
	List fontes = new java.util.ArrayList();

	if(!"".equals(Pagina.getParamStr(request, "exercicioExe"))){
			fontes = new ecar.dao.ItemEstruturaFonteRecursoDao(request).getFontesRecursosByExercicio(Long.valueOf(Pagina.getParamStr(request, "codIett")),  Long.valueOf(Pagina.getParamStr(request, "exercicioExe")));
	}
	String selectedFonr = "";
	
	EfItemEstContaEfiec conta = new EfItemEstContaEfiec();
	if(!"".equals(Pagina.getParamStr(request, "codConta"))){
		conta = (EfItemEstContaEfiec) new ItemEstruturaContaOrcamentoDao(request).buscar(EfItemEstContaEfiec.class, Long.valueOf(Pagina.getParamStr(request, "codConta")));
		if(conta != null){
			selectedFonr = conta.getFonteRecursoFonr().getCodFonr().toString();
		}
	}
	
	if("".equals(selectedFonr)){
		selectedFonr = Pagina.getParamStr(request, "fonteRecursoFonr");
	}
%>
<combo:ComboTag 
					nome="fonteRecursoFonr"
					objeto="ecar.pojo.FonteRecursoFonr"
					label="nomeFonr"
					value="codFonr"
					order="descricaoFonr"
					selected="<%=selectedFonr%>"
					scripts="<%=scriptsFR%>"
					colecao="<%=fontes%>"
/>