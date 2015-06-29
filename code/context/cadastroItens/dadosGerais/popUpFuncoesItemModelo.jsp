 <%@ page import="comum.database.Dao" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.pojo.EstruturaEtt" %>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="ecar.dao.ItemEstruturaDao"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.pojo.ItemEstruturaIett"/>
<jsp:directive.page import="comum.util.Util"/>
<jsp:directive.page import="ecar.pojo.FuncaoFun"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="ecar.dao.EstruturaFuncaoDao"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.HashSet"/>
<jsp:directive.page import="ecar.dao.FuncaoDao"/>
<jsp:directive.page import="java.util.Collections"/>
<jsp:directive.page import="java.util.Comparator"/>
<jsp:directive.page import="java.util.Queue"/>
<jsp:directive.page import="java.util.LinkedHashSet"/>


<%@ include file="../../frm_global.jsp"%>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>


<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
<script language="javascript" src="<%=_pathEcar%>/js/focoInicial.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/frmPesquisa.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/validacoes.js"></script>
<script language="javascript" src="<%=_pathEcar%>/js/destaqueLinha.js"></script>

<script language="javascript" type="text/javascript">

function aoClicarVoltar (form){
	history.go(-2);
}

function aoClicarCriar (form){
	if (validar()) {	
		form.hidAcao.value='criarModelo';
		form.action='ctrl.jsp';//?hidAcao=funcoesModelo'
		form.submit();
	} else {
		return false;
	}
}

function validar(form){
	return true;
}

/* Seleciona as Funções em */
function selecionarDependencia(codEstrutura, codFuncaoMaster, codFuncaoDependente){
	if (document.getElementById('funcao_'+codEstrutura+'_'+ codFuncaoMaster)) {
		funcaoMaster = document.getElementById('funcao_'+codEstrutura+'_'+ codFuncaoMaster);
		if (document.getElementById('funcao_'+codEstrutura+'_'+ codFuncaoDependente)) {
			var funcaoDependente = document.getElementById('funcao_'+codEstrutura+'_'+ codFuncaoDependente);
			
			if (funcaoDependente.checked) {
				funcaoMaster.checked = true;
			}
			
		}
	
	}
}
</script>

</head>
<%
try {
		EstruturaDao estruturaDao = new EstruturaDao(request);
		ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
		EstruturaEtt estrutura = (EstruturaEtt)estruturaDao.buscar(EstruturaEtt.class, Long.valueOf(Pagina.getParamStr(request, "codEtt")));
		List listItens = (List) session.getAttribute("listItens");
		Set setEstruturas = new LinkedHashSet(); //Contém as estruturas ordenadas na sequencia de apresentação
		//session.removeAttribute("listItens");
		FuncaoDao funcaoDao = new FuncaoDao(request);//Executa os procedimento de funcoes no banco de dados
				FuncaoFun funcao = null;
		List listFuncoes = null; //List com as funcoes das estruurast
		Map mapEstruturasNivel = itemEstruturaDao.montaMapEstruturaNivel(estrutura, listItens);
		int nivel=0;
		
		String codFunPontosCriticos ="";
		String codFunLocalizacao ="";
		
		FuncaoFun funcaoPesquisa = new FuncaoFun();
		funcaoPesquisa.setIndCopiaFun("S");
		//Busca todas as funcoes do sistema
//		listFuncoes = funcaoDao.listar(FuncaoFun.class, new String[] {"nomeFun",Dao.ORDEM_ASC} );
		//Busca apenas as funções com indicativo de cópia na base de dados
		listFuncoes = funcaoDao.pesquisar(funcaoPesquisa, new String[] {"nomeFun",Dao.ORDEM_ASC});
		Iterator itFuncoes = listFuncoes.iterator();
		while (itFuncoes.hasNext() ) {
			funcao = (FuncaoFun)itFuncoes.next();
			if (funcao.getNomeFun().equals( FuncaoDao.NOME_FUNCAO_PONTOS_CRITICOS)){
				codFunPontosCriticos = funcao.getCodFun()+"";
			} else if (funcao.getNomeFun().equals( FuncaoDao.NOME_FUNCAO_LOCALIZACAO)){
				codFunLocalizacao = funcao.getCodFun()+"";
			}
		}
 %>

<h1> Modelos da Estrutura</h1>
	
<br>
<% 
		if (listItens!= null && listItens.size()>0) {
		%>
  <form name="listaItens" action="">
    <input type="hidden" name="ultimoIdLinhaDetalhado" id="ultimoIdLinhaDetalhado" value="<%=Pagina.getParamStr(request,"ultimoIdLinhaDetalhado")%>">	
	<input type="hidden" name="ultimoIdLinhaExpandido" id="ultimoIdLinhaExpandido" value="<%=Pagina.getParamStr(request,"ultimoIdLinhaExpandido")%>"> 
  
	<div id="subconteudo" style="border-left-style: solid;border-left-color:#CED7E7; border-right-style: solid; border-right-color:#CED7E7;">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<tr><td class="espacador" colspan="2" ></td></tr>
			<tr ><td colspan="2" class="label" style="text-align: left;"><%= estrutura.getNomeEtt()%></td></tr>
			<tr><td class="espacador" colspan="2"></td></tr>
		<% 
			Iterator itItens = listItens.iterator();
			ItemEstruturaIett itemPai=null;
			while (itItens.hasNext()){
				ItemEstruturaIett item = (ItemEstruturaIett) itItens.next();
				EstruturaEtt estruturaItens = (EstruturaEtt)estruturaDao.buscar(EstruturaEtt.class, item.getEstruturaEtt().getCodEtt());
				nivel = ((Integer)mapEstruturasNivel.get(item.getEstruturaEtt())).intValue();
				setEstruturas.add(item.getEstruturaEtt());
				
				if (nivel==0) {
				itemPai = item;
		%>
			<tr class="cor_nao" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" onClick="javascript: destacaLinha(this,'click','cor_nao');">
				<td width="2" nowrap> &nbsp; </td>
				<td> 
					<label>  
						<input type="radio" name="hidIett" disabled checked> <%=item.getEstruturaEtt().getNomeEtt()%> - <%=new ItemEstruturaDao(request).criaColunaConteudoColunaArvoreAjax(item, estruturaItens)%>
						<input type="hidden" name="iett" value="<%=item.getCodIett() %>" >
					</label>  
				</td>
			</tr>
		<%
				} else {
		%>
			<tr class="cor_nao" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" >
				<td width="2"> &nbsp; </td>
				<td nowrap> 
					<label>  
						<%= Util.getEspacosEmBrancoHTML(nivel*3 ) %> <input type="checkbox" name="hid_iett_<%=itemPai.getCodIett()%>" value="<%=item.getCodIett()%>" disabled checked> <%=item.getEstruturaEtt().getNomeEtt()%> - <%=new ItemEstruturaDao(request).criaColunaConteudoColunaArvoreAjax(item, estruturaItens)%>
						<input type="hidden" name="iett_<%=itemPai.getCodIett()%>" value="<%=item.getCodIett()%>"> 
					</label>  
				</td>
			</tr>
		<%
				}
			} // fim while
%>		
				<tr>
					<td class="espacador" colspan="2">
						<img src="../../images/pixel.gif">
					</td>
				</tr>

				<tr><td class="espacador" colspan="2"><img src="../../images/pixel.gif"></td></tr>
	</table>
	</div>
	<br>
	
<%	if (listItens!= null && listItens.size()>0 ) { 
		//Lista com os itens pertencentes a estrutura que são modelos
			%>
		<h1>Funções</h1>
		  <div id="subconteudo" style="border-left-style: solid;border-left-color:#CED7E7; border-right-style: solid; border-right-color:#CED7E7;">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">	
			<% 
				
				Iterator itEstruturas = setEstruturas.iterator();
				while (itEstruturas.hasNext()) {
					EstruturaEtt estruturaFuncao = (EstruturaEtt) itEstruturas.next();
					nivel = ((Integer) mapEstruturasNivel.get(estruturaFuncao)).intValue();
%>
				<tr><td class="espacador" colspan="2"></td></tr>
				<tr >
					<td colspan="2" class="label" style="text-align: left;">
						<%= Util.getEspacosEmBrancoHTML(nivel*4 ) %> <%= estruturaFuncao.getNomeEtt()%>
					</td>
				</tr>
				<tr><td class="espacador" colspan="2"></td></tr>

<%	
					//Busca lista de funcoes da estrutrura atual 
					listFuncoes = (new EstruturaFuncaoDao(request)).getFuncoesCopia((EstruturaEtt)estruturaDao.buscar(EstruturaEtt.class, estruturaFuncao.getCodEtt()));// Funcoes(estruturaFuncao);
//					funcaoDao.filtraFuncoesParaCopiar(listFuncoes);
					String script = "";
					String hiddenAuxiliar=""; 
					Collections.sort(listFuncoes,
						new Comparator() {
			        		public int compare(Object o1, Object o2) {
			        		    return ( (FuncaoFun)o1 ).getNomeFun().compareToIgnoreCase( ( (FuncaoFun)o2 ).getNomeFun() );
			        		}
			    		} );
			    				
					
					if (listFuncoes!= null && listFuncoes.size()>0) {
						itFuncoes = listFuncoes.iterator();
						
						while (itFuncoes.hasNext()) {
							funcao = (FuncaoFun) itFuncoes.next();
							script="";
							//Não é para exibir a função Dados Gerais na listagem
							if (funcao.getNomeFun().equals(FuncaoDao.NOME_FUNCAO_DADOS_GERAIS) ) {
								continue;
							} else if (funcao.getNomeFun().equals(FuncaoDao.NOME_FUNCAO_EDITORES_LEITORES)){
								//Não é para exibir a função editores/leitores (permissão de acesso).
								continue;
							}else if (funcao.getNomeFun().equals(FuncaoDao.NOME_FUNCAO_APONTAMENTOS) ){ 
								script = "onclick=\"selecionarDependencia('"+ estruturaFuncao.getCodEtt()+"', '"+ codFunPontosCriticos+"' , '"+ funcao.getCodFun()+"');\" ";
								hiddenAuxiliar=" <input type=\"hidden\" name=\"funcao_"+estruturaFuncao.getCodEtt()+"\" value=\"\" id=\"funcao_"+ estruturaFuncao.getCodEtt()+"_"+funcao.getCodFun()+"\" > ";
							} else if (funcao.getNomeFun().equals(FuncaoDao.NOME_FUNCAO_METAS_INDICADORES) ){ 
								script = "onclick=\"selecionarDependencia('"+ estruturaFuncao.getCodEtt()+"', '"+ codFunLocalizacao+"', '"+ funcao.getCodFun()+"');\" ";
							}   
							
							
			%>					
				<tr class="cor_nao" onmouseover="javascript:destacaLinha(this,'over','')" onmouseout="javascript: destacaLinha(this,'out','cor_nao')" >
					<td width="2"> &nbsp; </td>
					<td nowrap> 
						<label>  
							<%= Util.getEspacosEmBrancoHTML(nivel*4 ) %> <input type="checkbox" name="funcao_<%=estruturaFuncao.getCodEtt()%>" id="funcao_<%=estruturaFuncao.getCodEtt()+"_"+funcao.getCodFun()%>" value="<%=funcao.getCodFun()%>" <%=script%> > <%=funcao.getLabelPadraoFun()%>
							
						</label>  
					</td>

				</tr>
<%
						}// fim while funcoes
					} // fim if

				}// fim while estruturas
			
%>		
					<tr>
						<td class="espacador" colspan="2">
							<img src="../../images/pixel.gif">
						</td>
					</tr>

					<tr> 
						<td colspan="2" align=center>
							<input type="button" name="btnAvancar" value="Criar" onclick="aoClicarCriar(this.form);"/>
							<input type="button" name="btnVoltar" value="Voltar" onclick="aoClicarVoltar(this.form)"/>
							<input type="hidden" name="hidAcao" value="" >
						</td>
					</tr>
					<tr><td class="espacador" colspan="2"><img src="../../images/pixel.gif"></td></tr>

				</table>
				</div>
	<%
} // fim if

 %>	
	
	
</form>

<%
} else { 
%>
	<p style="padding-left: 30">
	Nenhum item encontrado.
	</p>
<%	}

	


} catch (Exception e){
	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
%>
	<%@ include file="/excecao.jsp"%>
<%}%>

</body>
</html>