
<jsp:directive.page import="ecar.dao.ItemEstruturaDao"/>
<jsp:directive.page import="ecar.pojo.ItemEstruturaIett"/><%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<jsp:directive.page import="ecar.pojo.UnidadeOrcamentariaUO"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="ecar.dao.UnidadeOrcamentariaDao"/>
<jsp:directive.page import="comum.util.Pagina"/>
<jsp:directive.page import="ecar.dao.OrgaoDao"/>
<jsp:directive.page import="ecar.pojo.OrgaoOrg"/>
<jsp:directive.page import="java.util.ArrayList"/>
<%

	String codIett = Pagina.getParamStr(request, "codIett");
	String codOrgao = Pagina.getParamStr(request, "codOrgaoUnidadeOrc");

	List unidades = new ArrayList();
	
	if(!"".equals(codOrgao))
		unidades = new UnidadeOrcamentariaDao(request).getUnidadeOrcamentariaByOrgao((OrgaoOrg)new OrgaoDao(request).buscar(OrgaoOrg.class, Long.valueOf(codOrgao)), "");
	
	String unidadeSel = "";
	
	
%>

<combo:ComboTag 
	nome="unidadeOrcamentariaUO"
	objeto="ecar.pojo.UnidadeOrcamentariaUO"
	label="descricaoUo"
	value="codUo"
	order="descricaoUo"
	colecao="<%=unidades%>"
	selected="<%=unidadeSel%>"
	scripts="<%=Pagina.getParamStr(request, "disabled")%>"
/>