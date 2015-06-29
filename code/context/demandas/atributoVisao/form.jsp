
<%@page import="ecar.taglib.util.Options"%>
<%@page import="java.util.Set"%>
<%@page import="ecar.pojo.VisaoAtributoDemanda"%><jsp:directive.page import="ecar.taglib.util.Input"/>
<jsp:directive.page import="ecar.dao.AtributoDemandaDao"/>
<jsp:directive.page import="ecar.dao.VisaoDao"/>
<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="java.util.Iterator"/>
<%@ taglib uri="/WEB-INF/ecar-input.tld" prefix="ecar" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%> 
<%
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

function DesabilitarExibivel(check,campo){
	if(document.form.elements[campo]){
		if(check.checked){		
			document.form.elements[campo].checked=1;
			document.form.elements[campo].disabled=true;
			document.form.elements[campo+"Hidden"].value="<%=Pagina.SIM%>";
		}else{
			document.form.elements[campo].disabled=false;
			document.form.elements[campo+"Hidden"].value="<%=Pagina.NAO%>";
		}
	}
}

function checarDesabilitarExibivelEditavel(check) {
    if(check.checked) {       
        form.indEditavelAtbvis.checked=true;
        form.indEditavelAtbvis.disabled=true;
        form.indEditavelAtbvisHidden.value="<%=Pagina.SIM%>";
       
        form.indExibivelAtbvis.checked=true;
        form.indExibivelAtbvis.disabled=true;
        form.indExibivelAtbvisHidden.value="<%=Pagina.SIM%>";
    } else {
        form.indEditavelAtbvis.disabled=false;
        //form.indEditavelAtbvis.checked=false;
        //form.indEditavelAtbvisHidden.value="< %=Pagina.NAO%>";

        //form.indExibivelAtbvis.disabled=false;
        //form.indExibivelAtbvis.checked=false;
        //form.indExibivelAtbvisHidden.value="< %=Pagina.NAO%>";
    }
}

function limpaHidden(check, campo) {
	if(document.form.elements[campo]){
		if(!check.checked){
			document.form.elements[campo+"Hidden"].value="<%=Pagina.NAO%>";
		}
	}
}

function verificarObrigatoriedade(form) {
	
	if(form.indExibivelAtbvis.checked && !form.indEditavelAtbvis.checked && form.indObrigatorioAtbvis.checked) {
		alert('Não pode existir atributo exibível e obrigatório se não for editável'); 
		form.indObrigatorioAtbvis.checked = false; 
	}
}

function verificarObrigatoriedadeExibivel(form) {
	
	if(!form.indExibivelAtbvis.checked && form.indEditavelAtbvis.checked) {
		alert('Não pode existir atributo editável se não for exibível'); 
		form.indEditavelAtbvis.checked = false; 
	}
}

</script>

<table class="form">
<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>"/>
<tr>
	<td class="label"> <%=_obrigatorio%> Nome</td>
	<td>		
<%
	
	if (visaoAtributoDemanda!=null && visaoAtributoDemanda.getVisaoAtributoDemandaPk()!=null &&
		visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda()!=null &&
		visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().getCodAtbdem()!=null && 
		visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao() != null && 
		visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao() != null && !ehIncluir &&
		!Pagina.getParamStr(request, "hidPesquisou").equals("false")) {		
%>
			<combo:ComboTag 
			nome="atributosDemandas"
			objeto="ecar.pojo.AtributoDemandaAtbdem"
			label="labelPadraoAtbdem"
			value="codAtbdem"
			order="labelPadraoAtbdem"	
			selected="<%=visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().getCodAtbdem().toString()%>"
			disabled="true"
			/>			
<%
	} else {
		if (visaoAtributoDemanda != null && 
				visaoAtributoDemanda.getVisaoAtributoDemandaPk() != null &&
				visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda() != null &&
				visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().getCodAtbdem() != null){
			%>
			<combo:ComboTag 
			nome="atributosDemandas"
			objeto="ecar.pojo.AtributoDemandaAtbdem"
			label="labelPadraoAtbdem"
			value="codAtbdem"
			order="labelPadraoAtbdem"	
			selected="<%=visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().getCodAtbdem().toString()%>"
			/>
			<%	
		} else {
%>		
			<combo:ComboTag 
			nome="atributosDemandas"
			objeto="ecar.pojo.AtributoDemandaAtbdem"
			label="labelPadraoAtbdem"
			value="codAtbdem"
			order="labelPadraoAtbdem"	
			/>
<%
		}
	}
String teste = Pagina.getParam(request, "visoes");
%>
			
	</td>
</tr>

<tr>
	<td class="label"><%=_obrigatorio%> Visão</td>
	<td>
	<%
	
	if (visaoAtributoDemanda!=null && visaoAtributoDemanda.getVisaoAtributoDemandaPk()!=null &&
		visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda()!=null &&
		visaoAtributoDemanda.getVisaoAtributoDemandaPk().getAtributoDemanda().getCodAtbdem()!=null && 
		visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao()!=null &&
		visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao()!=null && !ehIncluir &&
		!Pagina.getParamStr(request, "hidPesquisou").equals("false")) {		
%>
		<combo:ComboTag 
			nome="visoes"
			objeto="ecar.pojo.VisaoDemandasVisDem"
			label="descricaoVisao"
			value="codVisao"
			order="descricaoVisao"
			selected="<%=visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao().toString()%>"
			disabled="true"
			/>
<%
	} else {
		if (visaoAtributoDemanda != null && 
				visaoAtributoDemanda.getVisaoAtributoDemandaPk() != null && 
				visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao() != null &&
				visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao() != null){
			%>
				<combo:ComboTag 
					nome="visoes"
					objeto="ecar.pojo.VisaoDemandasVisDem"
					label="descricaoVisao"
					value="codVisao"
					selected="<%=visaoAtributoDemanda.getVisaoAtributoDemandaPk().getVisao().getCodVisao().toString()%>"
					order="descricaoVisao"
		
					/>	
						
			<%
		} else {
	
%>
			<combo:ComboTag 
				nome="visoes"
				objeto="ecar.pojo.VisaoDemandasVisDem"
				label="descricaoVisao"
				value="codVisao"
				order="descricaoVisao"	
				/>	
<%
		}
	}
%>
	</td>
</tr>
<!-- *************************** Lista do Cadastro de Itens ************************** -->
<tr>
	<td class="label">
		Listagem
	</td>
	<td>
		&nbsp;
	</td>
</tr>
<tr>
	<td class="label">
		&nbsp;
	</td>
	<td>
		<table class="form">
			<tr>
				<td width="110px">Exibir na Lista de Demandas?</td>
				<td>
				<%
				if (ehPesquisa) {
				%>
					<input type="radio" class="form_check_radio" name="indListagemItensAtbvis" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndListagemItensAtbvis(), Pagina.SIM)%> <%=_disabled%> >Sim&nbsp;
					<input type="radio" class="form_check_radio" name="indListagemItensAtbvis" value="<%=Pagina.NAO%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndListagemItensAtbvis(), Pagina.NAO)%> <%=_disabled%> >Não&nbsp;
					<input type="button" name="buttonLimpar" value="Limpar" class="botao" onclick="limparRadioButtons(document.getElementsByName('indListagemItensAtbvis'));"  <%=_disabled%>><br>
				<%
				} else {
				%> 
					<input type="checkbox" name="indListagemItensAtbvis" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndListagemItensAtbvis(), Pagina.SIM)%> <%=_disabled%>>
				<% 
				}
				%>
				</td>								
			</tr>
			<tr>
				<td width="110px">&Eacute; Filtro?</td>
				<td>
				<%
				if (ehPesquisa) {
				%>
					<input type="radio" class="form_check_radio" name="indFiltroAtbvis" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndFiltroAtbvis(), Pagina.SIM)%> <%=_disabled%> >Sim&nbsp;
					<input type="radio" class="form_check_radio" name="indFiltroAtbvis" value="<%=Pagina.NAO%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndFiltroAtbvis(), Pagina.NAO)%> <%=_disabled%> >Não&nbsp;
					<input type="button" name="buttonLimpar" value="Limpar" class="botao" onclick="limparRadioButtons(document.getElementsByName('indFiltroAtbvis'));"  <%=_disabled%>><br>
				<%
				} else {
				%> 
					<input type="checkbox" name="indFiltroAtbvis" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndFiltroAtbvis(), Pagina.SIM)%> <%=_disabled%>>
				<% 
				}
				%>
				</td>
			</tr>
			<!-- Fim SERPRO 17/08/07 -->


			<tr>
				<td width="110px">
					Seq&uuml;&ecirc;ncia da Lista de Demandas
				</td>
				<td>
					<input type="text" name="seqApresListagemTelaAtbvis" size="6" maxlength="4" value="<%=Pagina.trocaNull(visaoAtributoDemanda.getSeqApresListagemTelaAtbvis())%>" <%=_disabled%>>
				</td>
			</tr>
			<tr>
				<td width="110px">
					Largura da Coluna na Lista de Demandas
				</td>
				<td>
					<input type="text" name="larguraListagemTelaAtbvis" size="5"
						maxlength="2"
						value="<%=Pagina.trocaNull(visaoAtributoDemanda.getLarguraListagemTelaAtbvis())%>" <%=_disabled%>>
				</td>
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td class="label">
		Consulta
	</td>
	<td>
		&nbsp;
	</td>
</tr>
<tr>
	<td class="label">
		&nbsp;
	</td>
	<td>
		<table class="form">
			<tr>
				<td width="110px">Exibir na Tela de Consulta?</td>
				<td>
				<%
				if (ehPesquisa) {
				%>
					<input type="radio" class="form_check_radio" name="indExibivelConsultaAtbvis" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndExibivelConsultaAtbvis(), Pagina.SIM)%> <%=_disabled%> >Sim&nbsp;
					<input type="radio" class="form_check_radio" name="indExibivelConsultaAtbvis" value="<%=Pagina.NAO%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndExibivelConsultaAtbvis(), Pagina.NAO)%> <%=_disabled%> >Não&nbsp;
					<input type="button" name="buttonLimpar" value="Limpar" class="botao" onclick="limparRadioButtons(document.getElementsByName('indExibivelConsultaAtbvis'));"  <%=_disabled%>>
				<%
				} else {
				%> 
					<input type="checkbox" name="indExibivelConsultaAtbvis" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndExibivelConsultaAtbvis(), Pagina.SIM)%> <%=_disabled%>>
				<% 
				}
				%>
				</td>								
			</tr>
		</table>
	</td>
</tr>
<tr>
	<td class="label">
		Inclusão/Alteração
	</td>
	<td>
		&nbsp;
	</td>
</tr>
<tr>
	<td class="label">
		&nbsp;
	</td>
	<td>
	<table class="form">
		<tr>
			<td width="110px">Exib&iacute;vel?</td>
			<td>			
				<%
				if (ehPesquisa) {
				%>
					<input type="radio" class="form_check_radio" name="indExibivelAtbvis" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndExibivelAtbvis(), Pagina.SIM)%> <%=_disabled%> >Sim&nbsp;
					<input type="radio" class="form_check_radio" name="indExibivelAtbvis" value="<%=Pagina.NAO%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndExibivelAtbvis(), Pagina.NAO)%> <%=_disabled%> >Não&nbsp;
					<input type="button" name="buttonLimpar" value="Limpar" class="botao" onclick="limparRadioButtons(document.getElementsByName('indExibivelAtbvis'));"  <%=_disabled%>><br>
				<%
				} else {
					String desabilita = "";
					if(visaoAtributoDemanda != null && visaoAtributoDemanda.getIndObrigatorioAtbvis() != null && visaoAtributoDemanda.getIndObrigatorioAtbvis().equals(Pagina.SIM)) {
                        desabilita = "disabled";
                    } else if(visaoAtributoDemanda != null && visaoAtributoDemanda.getIndEditavelAtbvis() != null && visaoAtributoDemanda.getIndEditavelAtbvis().equals(Pagina.SIM)) {
                        desabilita = "disabled";
                    }
				%> 
					<input type="checkbox" name="indExibivelAtbvis" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndExibivelAtbvis(), Pagina.SIM)%> onchange="verificarObrigatoriedade(form)" <%=_disabled%> <%=desabilita%> onclick="limpaHidden(this, 'indExibivelAtbvis');">
					
					<input type="hidden" name="indExibivelAtbvisHidden" <%if(!desabilita.equals("")) { %> value="<%=Pagina.SIM%>" <%}%>>
				<% 
				}
				%>								
			</td>
		</tr>
		<tr>
			<td width="110px">Edit&aacute;vel?</td>
			<td>
				<%
				if (ehPesquisa) {
				%>
					<input type="radio" class="form_check_radio" name="indEditavelAtbvis" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndEditavelAtbvis(), Pagina.SIM)%> <%=_disabled%> >Sim&nbsp;
					<input type="radio" class="form_check_radio" name="indEditavelAtbvis" value="<%=Pagina.NAO%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndEditavelAtbvis(), Pagina.NAO)%> <%=_disabled%> >Não&nbsp;
					<input type="button" name="buttonLimpar" value="Limpar" class="botao" onclick="limparRadioButtons(document.getElementsByName('indEditavelAtbvis'));"  <%=_disabled%>><br>
				<%
				} else {
					String desabilita = "";
                    if(visaoAtributoDemanda != null && visaoAtributoDemanda.getIndObrigatorioAtbvis() != null && visaoAtributoDemanda.getIndObrigatorioAtbvis().equals(Pagina.SIM))
                        desabilita = "disabled";
				%> 
					<input type="checkbox" name="indEditavelAtbvis" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndEditavelAtbvis(), Pagina.SIM)%> onchange="verificarObrigatoriedade(form)" onclick="DesabilitarExibivel(this, 'indExibivelAtbvis');limpaHidden(this, 'indEditavelAtbvis');" <%=_disabled%> <%=desabilita%>>
					<input type="hidden" name="indEditavelAtbvisHidden" <%if(!desabilita.equals("")) { %> value="<%=Pagina.SIM%>" <%}%>>
				<% 
				}
				%>	
			</td>                            
		</tr>
		<tr>
			<td width="110px">Obrigat&oacute;rio?</td>
			<td>
				<%
				if (ehPesquisa) {
				%>
					<input type="radio" class="form_check_radio" name="indObrigatorioAtbvis" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndObrigatorioAtbvis(), Pagina.SIM)%> <%=_disabled%> >Sim&nbsp;
					<input type="radio" class="form_check_radio" name="indObrigatorioAtbvis" value="<%=Pagina.NAO%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndObrigatorioAtbvis(), Pagina.NAO)%> <%=_disabled%> >Não&nbsp;
					<input type="button" name="buttonLimpar" value="Limpar" class="botao" onclick="limparRadioButtons(document.getElementsByName('indObrigatorioAtbvis'));"  <%=_disabled%>><br>
				<%
				} else {
				%> 
					<input type="checkbox" name="indObrigatorioAtbvis" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndObrigatorioAtbvis(), Pagina.SIM)%> onchange="checarDesabilitarExibivelEditavel(this);" <%=_disabled%> onclick="checarDesabilitarExibivelEditavel(this);">
				<% 
				}
				%>	
			</td>
		</tr>
		<tr>
		<td width="110px">Restritivo?</td>
			<td>
				<%
				if (ehPesquisa) {
				%>
					<input type="radio" class="form_check_radio" name="indRestritivo" value="<%=Pagina.SIM%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndRestritivo(), Pagina.SIM)%> <%=_disabled%> >Sim&nbsp;
					<input type="radio" class="form_check_radio" name="indRestritivo" value="<%=Pagina.NAO%>" <%=Pagina.isChecked(visaoAtributoDemanda.getIndRestritivo(), Pagina.NAO)%> <%=_disabled%> >Não&nbsp;
					<input type="button" name="buttonLimpar" value="Limpar" class="botao" onclick="limparRadioButtons(document.getElementsByName('indRestritivo'));"  <%=_disabled%>><br>
				<%
				} else {
				%> 
				<input type="checkbox" name="indRestritivo" value="<%=Pagina.SIM%>"
						<%=Pagina.isChecked(visaoAtributoDemanda.getIndRestritivo(), Pagina.SIM)%>
						<%=_disabled%>>	
				<% 
				}
				%>	
			
			
			</td>		
		</tr>
		<tr>
			<td width="110px">Seq&uuml;&ecirc;ncia no Formulário</td>
			<td>
				<input type="text" name="seqApresTelaCampoAtbvis" size="6"
						maxlength="4" value="<%=Pagina.trocaNull(visaoAtributoDemanda.getSeqApresTelaCampoAtbvis())%>" <%=_disabled%>>			
			</td>
		</tr>		
		<tr>
			<td width="110px">
				Dica do campo
			</td>
			<td>
				<textarea name="dicaAtbvis" cols="60" rows="4"
					onkeyup="return validaTamanhoLimite(this, 2000);"
					onkeydown="return validaTamanhoLimite(this, 2000);"
					onblur="return validaTamanhoLimite(this, 2000);" <%=_disabled%>><%=Pagina.trocaNull(visaoAtributoDemanda.getDicaAtbvis())%></textarea>
			</td>
		</tr>
	</table>
	</td>
</tr>

<%@ include file="/include/estadoMenu.jsp"%>
</table>