
<jsp:directive.page import="ecar.pojo.SisTipoExibicGrupoSteg"/>
<jsp:directive.page import="ecar.taglib.util.Input"/>
<jsp:directive.page import="ecar.pojo.SisGrupoAtributoSga"/>
<jsp:directive.page import="ecar.dao.SisGrupoAtributoDao"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.Iterator"/><%
/*
 * arquivo comum para as telas de inclusao, alteracao, exclusao e pesquisa 
 * observar a variavel _disabled e setar o objeto corretamente pelo construtor
 * assinalando os campos com os valores iniciais para inclusao
 */ 
%>

<script language="javascript">
function limparRadioButtons(radioObj){
		
	var radioLength = radioObj.length;
	if(radioLength == undefined) {
		radioObj.checked = false;
		
	}
	for(var i = 0; i < radioLength; i++) {
		radioObj[i].checked = false;
	}
	
	
}

</script>

<table class="form">

<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />

<tr>
	<td class="label"><%=_obrigatorio%> Nome</td>
	<%if (ehPesquisa || atributoDemanda.getSisGrupoAtributoSga() != null || ehIncluir){ %>
		<td><input type="text" name="nomeAtbdem" size="42" maxlength="40" value="<%=Pagina.trocaNull(atributoDemanda.getNomeAtbdem())%>" <%=_disabled%>></td>
	<%}else{ %>
		<td><input type="text" name="nomeAtbdemDisabled" size="42" maxlength="40" value="<%=Pagina.trocaNull(atributoDemanda.getNomeAtbdem())%>" disabled></td>
		<input type="hidden" name="nomeAtbdem" size="42" maxlength="40" value="<%=Pagina.trocaNull(atributoDemanda.getNomeAtbdem())%>">
	<%}%>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Label Padr&atilde;o</td>
	<td><input type="text" name="labelPadraoAtbdem" size="42" maxlength="40" value="<%=Pagina.trocaNull(atributoDemanda.getLabelPadraoAtbdem())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label"><%=_obrigatorio%> Grupo de Atributos Livres</td>
	<td>
<%
	SisTipoExibicGrupoSteg sisTipoExibicGrupoSteg = new SisTipoExibicGrupoSteg();
	sisTipoExibicGrupoSteg.setCodSteg(new Long(Input.VALIDACAO));
	
	SisGrupoAtributoSga sisGrupoAtributoSga = new SisGrupoAtributoSga();
	sisGrupoAtributoSga.setSisTipoExibicGrupoSteg(sisTipoExibicGrupoSteg);
	
	SisGrupoAtributoDao sisGrupoAtributoDao = new SisGrupoAtributoDao();
	
	List teste = sisGrupoAtributoDao.pesquisar(sisGrupoAtributoSga, new String[]{"sisTipoExibicGrupoSteg", "asc"});
		
	Iterator it = teste.iterator();
	String codigosSisGrupoValidacao = "";
	while (it.hasNext()) {
		sisGrupoAtributoSga = (SisGrupoAtributoSga) it.next(); 	
		codigosSisGrupoValidacao += sisGrupoAtributoSga.getCodSga() + "_";
				
	 }

	String filtro = "indAtivoSga=S;indTabelaUsoSga=D";
	if (atributoDemanda.getSisGrupoAtributoSga() != null){
					
%>			
			<combo:ComboTag 
			nome="sisGrupoAtributoSga"
			objeto="ecar.pojo.SisGrupoAtributoSga"
			label="descricaoSga"
			value="codSga"
			order="descricaoSga"
			filters="<%=filtro%>"
			selected="<%=atributoDemanda.getSisGrupoAtributoSga().getCodSga().toString()%>"
			scripts="<%=_disabled%>"
		/>
<%
	}else if(!ehPesquisa && !ehIncluir){
%>
			<combo:ComboTag 
			nome="sisGrupoAtributoSga"
			objeto="ecar.pojo.SisGrupoAtributoSga"
			label="descricaoSga"
			value="codSga"
			order="descricaoSga"
			filters="<%=filtro%>"
			scripts="<%="disabled"%>"
		/>
<%
	}else{
	%>
	<combo:ComboTag 
		nome="sisGrupoAtributoSga"
			objeto="ecar.pojo.SisGrupoAtributoSga"
			label="descricaoSga"
			value="codSga"
			order="descricaoSga"
			filters="<%=filtro%>"
			scripts="<%=_disabled%>"
	/>
	<%	
	}
%>		
	</td>
</tr>
<tr>
	<td class="label"> Campo que representa o c&oacute;digo da chave estrangeira</td>
	<td><input type="text" name="codFkAtbdem" size="42" maxlength="40" value="<%=Pagina.trocaNull(atributoDemanda.getCodFkAtbdem())%>" <%=_disabled%>></td>
</tr>
<tr>
	<td class="label"> Campo que representa a descri&ccedil;&atilde;o da chave estrangeira</td>
	<td><input type="text" name="nomeFkAtbdem" size="42" maxlength="40" value="<%=Pagina.trocaNull(atributoDemanda.getNomeFkAtbdem())%>" <%=_disabled%>></td>
</tr>

<tr>
	<td class="label">Tamanho do conteúdo</td>
	<td>
		<input type="text" name="tamanhoConteudoAtbdem" id="tamanhoConteudoAtbdem" size="4" maxlength="4"
			value="<%=Pagina.trocaNull(atributoDemanda.getTamanhoConteudoAtbdem())%>"
			<%=_disabled%>>
	</td>
</tr>

<tr>
		<td class="label"><%=_obrigatorio%> Ativo</td>
		<td>
				<util:comboSimNaoTag nome="indAtivoAtbdem" valorSelecionado="<%=atributoDemanda.getIndAtivoAtbdem()%>" scripts="<%=_disabled%>" opcaoBranco="<%=_comboSimNao%>"/>
		</td>
</tr>

<%if (blVisualizaDesc == true){ %>
        <tr>
            <td class="label">Documenta&ccedil;&atilde;o</td>
            <td>
               <script language="JavaScript" type="text/javascript">
               //Usage: initRTE(imagesPath, includesPath, cssFile, genXHTML)
				initRTE("<%=_pathEcar%>/images/rte/", "<%=_pathEcar%>", "", false);
               </script>
               
               <script language="JavaScript" type="text/javascript">
               //Usage: writeRichText(fieldname, html, width, height, buttons, readOnly)
               //writeRichText('rte', 'enter body text here', 400, 200, true, false);                          
               writeRichText('rte', "<%=Pagina.trocaNull(atributoDemanda.getDocumentacaoAtbdem()).replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("</SCRIPT>","&lt;/SCRIPT&gt;").replaceAll("</script>","&lt;/script&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</SCRIPT -->","&lt;/SCRIPT --&gt;")%>", 400, 200, true,<%=strReadOnly%>);
               toggleHTMLSrc('rte', false);
               </script>       
               
               <input type="hidden" name="richText" id="richText" size="2000">
               <input type="hidden" name="documentacaoAtbdem" size="2000">
            </td>
        </tr>
<%
}
%>

<%@ include file="/include/estadoMenu.jsp"%>
</table>