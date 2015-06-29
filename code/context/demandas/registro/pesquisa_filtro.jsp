<%@ page import="java.util.Set"%>
<%@ page import="java.util.HashSet"%>

<%@ page import="ecar.dao.PrioridadeDao" %>
<%@ page import="ecar.pojo.PrioridadePrior" %>
<%@ page import="ecar.pojo.SitDemandaSitd" %>
<%@ page import="ecar.dao.SitDemandaDao" %>
<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.dao.EntidadeDao" %>
<%@ page import="ecar.dao.LocalItemDao"%>
<%@ page import="ecar.pojo.LocalItemLit"%>
<%@ page import="ecar.dao.OrgaoDao" %>
<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>
<%@ page import="ecar.dao.SisGrupoAtributoDao" %>
<%@ page import="ecar.pojo.SisAtributoSatb" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<jsp:directive.page import="ecar.dao.AtributoDemandaDao"/>
<jsp:directive.page import="ecar.pojo.ObjetoDemanda"/>
<jsp:directive.page import="ecar.dao.RegDemandaDao"/>
<jsp:directive.page import="ecar.pojo.RegDemandaRegd"/>

<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>


<%@page import="ecar.pojo.VisaoDemandasVisDem"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%
	String acao = "filtrar";
	if(session.getAttribute("classifica") != null && "classifica".equals(session.getAttribute("classifica").toString())) {
		acao = "filtrarClassificar";
	} else {
		acao = "filtrarCadastro";
	}
	
%>

<%@ include file="/titulo.jsp"%>
<%@ include file="funcoes.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">

<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/numero.js"></script>
<script language="javascript" src="../../js/popUp.js"></script>
<script language="javascript" src="../../js/validacoes.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="../../js/datas.js"></script>
<script language="javascript" src="../../js/validacoesAtributoLivre.js"></script>
<script language="javascript" src="../../js/limpezaCamposMultivalorados.js"></script>


<script language="JavaScript">

function aoClicarFiltrar(visao) {
	if(validar(document.form)) {
		document.form.hidAcao.value = "filtrar";
		document.form.visao.value = visao;
		document.form.submit();
	}
}

function getDadosPesquisa(codigo, descricao){
	adicionaEntidade(codigo, descricao);	
}

function limparFiltros()  {	
	var form, elements, i, elm; 
    form = document.forms[0]; 

	if (document.getElementsByTagName)	{
		elements = form.getElementsByTagName('input');
		for( i=0, elm; elm=elements.item(i++); )		{
			if (elm.getAttribute('type') == "text")			{
				elm.value = '';
			} else if (elm.getAttribute('type') == "checkbox")	{
				elm.checked = false;
			}
		}
	} else	{
		elements = form.elements;
		for( i=0, elm; elm=elements[i++]; )		{
			if (elm.type == "text" || elm.type == "textarea") {
				elm.value ='';
			} else if (elm.getAttribute('type') == "checkbox")	{
				elm.checked = false;
			} else if (elm.getAttribute('type') == "select") {
				elm.value = '-1';
			}
		}
		
	}
//	form.prioridade.value = "-1";
//	form.situacao = "-1";
//	form.orgao = "-1";
	form.hidAcao.value = "limpar_filtro";
	form.submit();
}

function voltar() {
	form.hidAcao.value = "";
	form.action = form.origem.value;	
	form.submit();
}

function inicio() {
	var sFormas = document.form.formasContato.value;
	var formas = new Array();
	var sCodEnts = "";
	
	if(document.form.codEnts != null)
		 sCodEnts = document.form.codEnts.value;
	
	var codEnts = new Array();
		
	formas = sFormas.split(',');
	for (var i=0;i<formas.length;i++)	{
		
		for(j=0 ; (document.form.formaContato != null && document.form.formaContato[j] != null); j++){
			if (document.form.formaContato[j].value == formas[i])	document.form.formaContato[j].checked = true;
		}	
	}
	codEnts = sCodEnts.split(',');
	for (var i=0;i<codEnts.length;i++)	{
		for(j=0 ; (document.form.tipo_entidade != null && document.form.tipo_entidade[j] != null); j++){
			if (document.form.tipo_entidade[j].value == codEnts[i])	document.form.tipo_entidade[j].checked = true;
		}	
	}
	
	if (document.form.codIdentLocais.value != "") {
		var sCodIdentLocais = document.form.codIdentLocais.value;
		var codIdentLocais = new Array();
		
		codIdentLocais = sCodIdentLocais.split(';');
	
		for (var i=0; i < codIdentLocais.length - 1; i++) {			
			var aux = codIdentLocais[i].split(',');
			adicionaLocal(aux[0], aux[1]);
		}		
	}
}

/**************************************************************************************************************/
/* Funcao para transformar mascara moeda para formato double suportado pelo interpretador de javascript
 * caso não esteja no formato moeda, não é transformado
 *        */
/*************************************************************************************************************/
function transformaValorMascaraMoedaParaDouble(numero, tipoInfinito){
	
	var pos = "";
	var valorTransformado = "";
	
	// se for de tamanho maior que 
	if(numero.length>2 && (numero.indexOf(".")!= -1 || numero.indexOf(",")!= -1)) {
			for(var i=numero.length; i>0; i--){
				pos = numero.substring(i, i-1);
				if (pos==".") {
					if((numero.length-i)==2) {
						valorTransformado = pos + valorTransformado;
					}
				} else if(pos==",") {
					if ((numero.length-i)==2) {
						valorTransformado = "." + valorTransformado;
					}
				} else {
					valorTransformado = pos + valorTransformado;
				}
			}
	} else {
		valorTransformado = numero;	
	}

	
    return valorTransformado;
}

</script>


</head>
<body>
<%@ include file="/cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%>
<%
try {			
	String num_registro = (String)request.getSession().getAttribute("num_registro");
	String descricao = (String)request.getSession().getAttribute("descricao");
	String observacao = (String)request.getSession().getAttribute("observacao");
	String num_doc_origem = (String)request.getSession().getAttribute("num_doc_origem");
	String usu_sol = (String)request.getSession().getAttribute("usu_sol");
	String nome_sigla = (String)request.getSession().getAttribute("nome_sigla");
	String codLocais = (String)request.getSession().getAttribute("codLocais");
	String data_limite_ini = (String)request.getSession().getAttribute("data_limite_ini");
	String data_limite_final = (String)request.getSession().getAttribute("data_limite_final");
	String data_sol_ini = (String)request.getSession().getAttribute("data_sol_ini");
	String data_sol_final = (String)request.getSession().getAttribute("data_sol_final");	
	String prioridade = request.getSession().getAttribute("prioridade")!=null?(String)request.getSession().getAttribute("prioridade"):"0";
	String situacao = request.getSession().getAttribute("situacao")!=null?(String)request.getSession().getAttribute("situacao"):"0";
	String orgao = request.getSession().getAttribute("orgao")!=null?(String)request.getSession().getAttribute("orgao"):"0";
	String formasContato = (String)request.getSession().getAttribute("formasContato");
	
	/* Atributos variáveis de Entidade */
	EntidadeEnt entidade = new EntidadeEnt();
	if (request.getSession().getAttribute("entidade") != null) {
		entidade = (EntidadeEnt) request.getSession().getAttribute("entidade");
	}
	
	String codIdentLocais = "";
	List listLocais = (ArrayList)request.getSession().getAttribute("listLocais");
	if (listLocais != null) {
		for (Iterator it = listLocais.iterator();it.hasNext();) {
			LocalItemLit localItemLit = (LocalItemLit)it.next();	
			codIdentLocais += localItemLit.getCodLit().longValue() +","+ localItemLit.getIdentificacaoLit() + ";";
		}
	}
	
	EntidadeDao eDao = new EntidadeDao(request);
	
	VisaoDemandasVisDem visaoSessao = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA));
	
	String visaoDescricao = " > " + visaoSessao.getDescricaoVisao() ;
	
%>
<div id="conteudo">
<form name="form" method="post" action="ctrl.jsp">

	<input type="hidden" name="hidAcao" value="">	
	<input type="hidden" name="origem" value="<%=Pagina.getParam(request, "origem")%>">
	<input type="hidden" name="codLocais" value="<%=codLocais%>">	
	<input type="hidden" name="formasContato" value="<%=formasContato%>">	
	<input type="hidden" name="codIdentLocais" value="<%=codIdentLocais%>">	

	<h1 >Pesquisa de Registro de Demanda<%=visaoDescricao %></h1>

		<table class="form" >
			<tr class="linha_titulo">
				<td class="label">Filtros</td>
			  	<td >	
			  		<input class="botao" type="button" value="Filtrar" onclick="aoClicarFiltrar(<%=visaoSessao.getCodVisao().toString()%>);">&nbsp;
			  		<input class="botao" type="button" value="Cancelar" onclick="voltar();">
			  	</td>	
			</tr>
			<tr>
				<td class="label">&nbsp;</td>
				<td class="label" align="right"><a href="#" onclick="javascript:limparFiltros();">[Limpar Filtros]</a></td>
			</tr>			
				
				<%
				RegDemandaDao regDemandaDao = new RegDemandaDao(null);
				AtributoDemandaDao atributoDemandaDao = new AtributoDemandaDao(request);
				
				// recupera o id da visao atual selecionada
				int visaoParameter = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)).getCodVisao().intValue();
				
				List atributos = atributoDemandaDao.getAtributosDemandaVisaoAtivosQueSaoFiltro(new Long(visaoParameter));		 
			
				//O padrão é desabilitado 
				Boolean desabilitar = new Boolean(false);
				
				
			
			//Chama a tag que imprime os atributos
			if(atributos != null){
				Iterator it = atributos.iterator();  
				
				//Campos de ordenação da listagem  %>				
				<input type="hidden" name="clCampo" value="<%=Pagina.getParam(request, "clCampo")%>">
				<input type="hidden" name="clOrdem" value="<%=Pagina.getParam(request, "clOrdem")%>">
				<input type="hidden" name="refazPesquisa" value="">
				<input type="hidden" name="visao" value="">
				
				<script language="javascript">
					
				<util:validacaoRegDemanda
					atributos="<%=atributos%>"  
					sisGrupoAtributoDao="<%= new SisGrupoAtributoDao(request)%>" 
					acao="<%=acao%>"
					codigoVisao ="<%=Integer.toString(visaoParameter)%>"
				/>
				</script>
				
						
				<%	
				while(it.hasNext()){
					ObjetoDemanda atributo = (ObjetoDemanda) it.next();
					%>		

					<util:formRegDemanda 
						atributo="<%=atributo%>" 
						regDemanda="<%=new RegDemandaRegd()%>" 
						desabilitar="<%=desabilitar%>" 
						seguranca="<%=seguranca%>" 
						contextPath="<%=request.getContextPath()%>"
						acao="<%=acao%>"
						request="<%=request%>"	
						codigoVisao ="<%=Integer.toString(visaoParameter)%>"	
						transformarComboBoxListaChecks="<%=new Boolean(true)%>"
						transformarRadioListaChecks="<%=new Boolean(true)%>"
					/>	
					<%
				} 
			} %>	
			
			<tr>
				<td class="label">&nbsp;</td>
				<td class="label" align="right"><a href="#" onclick="javascript:limparFiltros();">[Limpar Filtros]</a></td>
			</tr>		
			<tr class="linha_titulo">
				<td class="label">Filtros</td>
			  	<td>	
			  		<input class="botao" type="button" value="Filtrar" onclick="aoClicarFiltrar();">&nbsp;
			  		<input class="botao" type="button" value="Cancelar" onclick="voltar();">
			  	</td>	
			</tr>
		</table>
	
	<%@ include file="../../include/estadoMenu.jsp"%>
	
</form>
<%
} catch (Exception e){
%>
	<%@ include file="/excecao.jsp"%>
<%
}
%>
</div>
</body>

<script language="Javascript">
window.onload=inicio();
</script>

</html>
