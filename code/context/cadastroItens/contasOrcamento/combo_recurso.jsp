
<jsp:directive.page import="ecar.util.Dominios"/>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ page import="java.util.List" %>
<%@ page import="ecar.pojo.EfItemEstContaEfiec" %>
<%@ page import="ecar.dao.ItemEstruturaContaOrcamentoDao" %>
<%@ page import="comum.util.Pagina" %>
<%
	String scriptsR = Pagina.getParamStr(request, "disabled");
	List recursos = new java.util.ArrayList();

	String selectedRec = "";

	EfItemEstContaEfiec conta = new EfItemEstContaEfiec();
	if(!"".equals(Pagina.getParamStr(request, "codConta"))){
		conta = (EfItemEstContaEfiec) new ItemEstruturaContaOrcamentoDao(request).buscar(EfItemEstContaEfiec.class, Long.valueOf(Pagina.getParamStr(request, "codConta")));
		if(conta != null){
			selectedRec = conta.getRecursoRec().getCodRec().toString();
		}
	}	
	
	if("".equals(selectedRec)){
		selectedRec = Pagina.getParamStr(request, "recursoRec");
	}

	if(conta.getRecursoRec() != null)
			recursos = new ecar.dao.ItemEstruturaPrevisaoDao(request).getRecursosByFonteRecursoExercicio(conta.getItemEstruturaIett(),  conta.getExercicioExe(), conta.getFonteRecursoFonr());			
	if(!"".equals(Pagina.getParamStr(request, "fonteRecursoFonr"))){
			recursos = new ecar.dao.ItemEstruturaPrevisaoDao(request).getRecursosByFonteRecursoExercicio(Long.valueOf(Pagina.getParamStr(request, "codIett")),  Long.valueOf(Pagina.getParamStr(request, "exercicioExe")), Long.valueOf(Pagina.getParamStr(request, "fonteRecursoFonr")));
	}
	
%>
<combo:ComboTag 
					nome="recursoRec"
					objeto="ecar.pojo.RecursoRec"
					label="nomeRec"
					value="codRec" 
					order="nomeRec"
					selected="<%=selectedRec%>"
					scripts="<%=scriptsR%>"
					colecao="<%=recursos%>"
/>
