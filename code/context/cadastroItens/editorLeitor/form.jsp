<%
	
	String codUsu = new String();
	String nomeUsu = new String();
	
	if (itemEstrutUsuario.getUsuarioUsu() != null){
		codUsu = "U" + itemEstrutUsuario.getUsuarioUsu().getCodUsu().toString();
		nomeUsu = itemEstrutUsuario.getUsuarioUsu().getNomeUsuSent();
	}else{
		if (itemEstrutUsuario.getSisAtributoSatb() != null){
			codUsu = "G" + itemEstrutUsuario.getSisAtributoSatb().getCodSatb().toString();
			nomeUsu = itemEstrutUsuario.getSisAtributoSatb().getDescricaoSatb();
		}else{
			codUsu = "";
			nomeUsu = "";
		}
	}
%>
<table class="form">
	
	<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
	<input type=hidden name="codIettus" value="<%=Pagina.getParamStr(request, "codIettus")%>">
	<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
	
		

	<tr>
		<td colspan="4">
			<div class="camposobrigatorios">* Campos de preenchimento obrigat&oacute;rio</div>
		</td>
	</tr>
	<tr>
		<td class="label">* Nome</td>
		<td colspan="3">
			<input type="hidden" name="codUsu" value="<%=codUsu%>">
			<input type="text" name="nomeUsu" size="55" value="<%=nomeUsu%>" disabled>
			<input type="button" class="botao" name="pesquisarUsu" value="Pesquisar" onclick="popup_pesquisa('ecar.popup.PopUpUsuarioEGrupo')" <%=_disabled%>>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td width="5%">Leitura</td>
		<td width="5%">Edi&ccedil;&atilde;o</td>
		<td>Exclus&atilde;o</td>
	</tr>
	<tr>
		<td class="label" >Cadastro de Item na Estrutura</td>
		<td><input type="checkbox" class="form_check_radio" name="indLeituraIettus" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndLeituraIettus(), "S")%> <%=_disabled%> onclick="marcarLeitura()" ></td>
		<td><input type="checkbox" class="form_check_radio" name="indEdicaoIettus" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndEdicaoIettus(), "S")%> <%=_disabled%> onclick="marcarLeitura()" ></td>
		<td><input type="checkbox" class="form_check_radio" name="indExcluirIettus" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndExcluirIettus(), "S")%> <%=_disabled%> onclick="marcarLeitura()" ></td>
	</tr>
	<tr>
		<td class="label" >Visualiza&ccedil;&atilde;o do Parecer</td>
		<td><input type="checkbox" class="form_check_radio" name="indLeituraParecerIettus" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndLeituraParecerIettus(),"S") %> <%=_disabled%>></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td class="label"> Mant&eacute;m permiss&atilde;o nos pr&oacute;ximos n&iacute;veis</td>
		
		<%
		
		//Retirada, por pedido de Beier, validação de permissão por funções de acompanhamento que o usuário seja
		//e que a função permita manter no próximo nível.
		
//		String disabledProximoNivel = "disabled";
		String disabledProximoNivel = "";
		
/*		if (ehInclusao){
			
			ItemEstrutUsuarioIettus itemEstrutUsuarioLogado = new ItemEstrutUsuarioIettus();
			itemEstrutUsuarioLogado.setUsuarioUsu(seguranca.getUsuario());
			itemEstrutUsuarioLogado.setItemEstruturaIett(itemEstrutura);
			
			ItemEstrutUsuarioDao itemEstruturaUsuarioDao = new ItemEstrutUsuarioDao(request);
			List permissoesUsuarioLogado = itemEstruturaUsuarioDao.pesquisar(itemEstrutUsuarioLogado, new String[]{"codIettus", "asc"});	
			
			Iterator itPermissoes = permissoesUsuarioLogado.iterator();
			
			boolean permissaoProxNivelUsuarioLogado = false;
			
			while (itPermissoes.hasNext()){
				
				ItemEstrutUsuarioIettus permissao = (ItemEstrutUsuarioIettus) itPermissoes.next();
				
				if (permissao.getIndProxNivelIettus() != null && permissao.getIndProxNivelIettus().equals("S")){			
					permissaoProxNivelUsuarioLogado = true;
					
					break;
				}
			}
			
			if (permissaoProxNivelUsuarioLogado){
				disabledProximoNivel = "";
			}
		}
*/
		%>
		
		<td><input type="checkbox" class="form_check_radio" name="indProxNivelIettus" value="S" <%=Pagina.isChecked(itemEstrutUsuario.getIndProxNivelIettus(), "S")%> <%=disabledProximoNivel%>></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<%@ include file="/include/estadoMenu.jsp"%>
</table>
	