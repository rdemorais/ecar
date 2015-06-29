<%@ page import="ecar.pojo.EfIettFonteTotEfieft" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttf" %>
<%@ page import="ecar.pojo.ExercicioExe" %>
<%@ page import="ecar.pojo.EfItemEstPrevisaoEfiep" %>
<%@ page import="ecar.pojo.EfItemEstPrevisaoEfiepPK" %>
<%@ page import="ecar.pojo.RecursoRec" %>
<%@ page import="ecar.dao.ItemEstruturaFonteRecursoDao" %>
<%@ page import="ecar.dao.EstruturaFuncaoDao" %>
<%@ page import="ecar.dao.ExercicioDao" %>
<%@ page import="ecar.dao.FonteRecursoDao" %>
<%@ page import="ecar.dao.ItemEstruturaPrevisaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaFonteRecursoRevDao" %>
<%@ page import="ecar.pojo.EfIettFonTotRevEfieftr" %>
<%@ page import="ecar.pojo.FuncaoFun" %>
<%@ page import="ecar.dao.FuncaoDao" %>
<%@ page import="ecar.dao.ItemEstruturaPrevisaoRevDao" %>
<%@ page import="ecar.pojo.ItemEstruturarevisaoIettrev" %>
<%@ page import="ecar.pojo.EstruturaFuncaoEttfPK" %>
<%@ page import="ecar.pojo.EfIettPrevisaoRevEfieprPK" %>
<%@ page import="ecar.pojo.EfIettPrevisaoRevEfiepr" %>
<%@ page import="java.util.Collection" %>
<%@ page import="java.util.List" %>
<%@ page import="comum.util.Util" %>
<jsp:directive.page import="ecar.util.Dominios"/>

<%

		FuncaoFun funcaoRec = new FuncaoFun();
		EstruturaFuncaoEttf etfRec = new EstruturaFuncaoEttf();
		funcaoRec.setNomeFun("Fontes_Recursos");
		List pesquisaRec = new FuncaoDao(request).pesquisar(funcaoRec, new String[]{"nomeFun","asc"});
		if ((pesquisaRec != null) && (pesquisaRec.size() > 0))
		{
			FuncaoFun fRec = (FuncaoFun) pesquisaRec.iterator().next();
			etfRec.setComp_id(new EstruturaFuncaoEttfPK(estrutura.getCodEtt(), fRec.getCodFun()));
			List pesquisaEttfRec = new EstruturaFuncaoDao(request).pesquisar(etfRec, new String[]{"labelEttf","asc"});
			if ((pesquisaEttfRec != null) && (pesquisaEttfRec.size() > 0))
			{
				etfRec = (EstruturaFuncaoEttf) pesquisaEttfRec.iterator().next();
				String codAbaRec = fRec.getCodFun().toString();
	
%>
  
<script language="javascript">
function aoClicarConsultar(form, codigo1){
	form.codFonr.value = codigo1;
	document.form.action = "frm_con.jsp";
	document.form.submit();	
} 

function aoClicarEditarRecurso(form, codFonr){
		opcoes = '?codAba=<%=codAba%>&codAbaRec=<%=codAbaRec%>&codIett=<%=Pagina.getParamStr(request, "codIett")%>&codIettrev=<%=Pagina.getParamStr(request, "codIettrev")%>&codFonr='+codFonr;
		caminho=_pathEcar + '/cadastroItens/revisao/fonteRecurso/frm_con.jsp'+opcoes;
		return abreJanela(caminho, '600', '400', 'FonteRecurso');
} 

function MostraLinhaRecurso(parmCodigo) {
  if (document.getElementById('linhaRecurso' + '_' + parmCodigo).style.display=='none') {
     document.getElementById('linhaRecurso' + '_' + parmCodigo).style.display='';
  } else {
     document.getElementById('linhaRecurso' + '_' + parmCodigo).style.display='none';
  }
}

function aoClicarIncluirRecurso(form){
	if(form.hidGravado.value != "S")
		alert("Antes de inserir <%=etfRec.getLabelEttf()%> é necessário GRAVAR a revisão\nFaça isto clicando em \"Gravar\"");
	else
	{
		opcoes = '?codAba=<%=codAba%>&codAbaRec=<%=codAbaRec%>&codIett=<%=Pagina.getParamStr(request, "codIett")%>&codIettrev=<%=Pagina.getParamStr(request, "codIettrev")%>';
		caminho=_pathEcar + '/cadastroItens/revisao/fonteRecurso/frm_inc.jsp'+opcoes;
		return abreJanela(caminho, '600', '400', 'FonteRecurso');
	}
}

function aoClicarExcluirRecurso(form){
	if(validarExclusao(form, "excluirRecurso")){
		if(!confirm("Confirma a exclusão?")){
			return(false);
		}
		form.hidAcao.value = "excluir";
		form.action = "fonteRecurso/ctrl.jsp";
		form.submit();	
	}
}
</script>
 
<input type="hidden" name="codAbaRec" value="<%=codAbaRec%>"> 

<% 
//	ItemEstruturarevisaoIettrev itemEstruturaRev = (ItemEstruturarevisaoIettrev) itemEstruturaDao.buscar(ItemEstruturarevisaoIettrev.class, Integer.valueOf(Pagina.getParamStr(request, "codIettrev")));
	ItemEstruturaFonteRecursoRevDao ieFonteRecDao = new ItemEstruturaFonteRecursoRevDao(request);

	// Comentado devido ao BUG 4851, NÃO retirar a linha abaixo
	//boolean podeIncluir = ieFonteRecDao.verificaPossibilidadeInclusao(itemEstrutura);
	boolean podeIncluir = true;
//	ValidaPermissao validaPermissao = new ValidaPermissao();
	estruturaFuncao = (EstruturaFuncaoEttf) estruturaFuncaoDao.getLabelFuncao(itemEstrutura.getEstruturaEtt(), Long.valueOf(codAba));
	Boolean permissaoAlterar = validaPermissao.permissaoAlterarItem(itemEstrutura, seguranca.getUsuario(), seguranca.getGruposAcesso(), estruturaFuncao);
%>

<table width="100%" border="0" cellpadding="0" cellspacing="0">
<input type=hidden name="codIett" value="<%=itemEstrutura.getCodIett()%>">
<input type=hidden name="codAba" value="<%=Pagina.getParamStr(request, "codAba")%>">
<input type=hidden name="codFonr" value="">

	<tr ><td colspan="2" class="espacador"><img src="../../images/pixel.gif"></td></tr>
	<tr>
	</tr>
	<tr class="linha_titulo">
	<td>
		<h1><%=etfRec.getLabelEttf()%></h1>
	</td>
		<td align="right">
		<%if (!desabilitar){%>
			<input type="button" class="botao" value="Adicionar revisão em <%=etfRec.getLabelEttf()%>" onclick="javascript:aoClicarIncluirRecurso(form);">&nbsp;
			<input type="button" class="botao" value="Excluir revisão em <%=etfRec.getLabelEttf()%>" onclick="javascript:aoClicarExcluirRecurso(form);">&nbsp;
		<%}%>
		</td>
	</tr>
	</table>

	<!-- ############### Listagem  ################### -->
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr><td class="espacador" colspan=7><img src="../../images/pixel.gif"></td></tr>
	<tr class="linha_subtitulo">
		<td width="4%">
		<%if (!desabilitar){%>			
			<input type=checkBox class="form_check_radio" name="todosRec" onclick="javascript:selectAll(document.form, 'todosRec', 'excluirRecurso')">&nbsp;
		<%}%>			
		</td>
		<td width="4%"></td>
		<td>Nome</td>
		<td>Total Aprovado</td>
		<td>Total Revisado</td> 
	</tr>
	<tr><td class="espacador" colspan=7><img src="../../images/pixel.gif"></td></tr>
<%
	int linha = 0;
	if(podeIncluir){
	if (ieRevisao.getEfIettPrevisaoRevEfieprs() != null)
	{
		
		List fontesAtivas = ieFonteRecDao.getAtivos(ieRevisao);
		if(fontesAtivas != null ){ 
			Iterator itFonteRecurso = fontesAtivas.iterator();
			while(itFonteRecurso.hasNext()){
				EfIettFonTotRevEfieftr ieFonteRecurso = (EfIettFonTotRevEfieftr) itFonteRecurso.next();
				List listaRecurso = new ArrayList();
				linha++;
				%>
				<tr class="linha_subtitulo2"> 
					<td width="4%">
					<%if (!desabilitar){%>			
						<input type="checkbox" class="form_check_radio" name="excluirRecurso" value="<%=ieFonteRecurso.getFonteRecursoFonr().getCodFonr()%>">
					<%}%>			
					</td>
					<td width="4%">
					<%if (!desabilitar){%>								
						<a href=# onClick="javascript:aoClicarEditarRecurso(document.form,<%=ieFonteRecurso.getFonteRecursoFonr().getCodFonr()%>)">
						<img border="0" src="../../images/icon_alterar.png" alt="Alterar"></a>&nbsp;
					<%}%>
					</td>
					<td>	
						<a href="javascript:MostraLinhaRecurso('<%=linha%>');">
						<%=ieFonteRecurso.getFonteRecursoFonr().getSequenciaFonr().toString() + " " + ieFonteRecurso.getFonteRecursoFonr().getSiglaFonr() + " - " + ieFonteRecurso.getFonteRecursoFonr().getNomeFonr()%></a>
					</td>
					<%
					double somaRecursosAprovados = new ItemEstruturaFonteRecursoRevDao(request).getSomaRecursosFonteRecurso(ieFonteRecurso, "Aprovado");
					double somaRecursosRevisados = new ItemEstruturaFonteRecursoRevDao(request).getSomaRecursosFonteRecurso(ieFonteRecurso, "Revisado");
					%>
					<td><%=Pagina.trocaNullNumeroDecimalSemMilhar(new Double(somaRecursosAprovados))%></td>					
					<td><%=Pagina.trocaNullNumeroDecimalSemMilhar(new Double(somaRecursosRevisados))%></td>					
				</tr>
				<tr>
					<td colspan="8">
						<table id="linhaRecurso_<%=linha%>" style="display:none" border="1" cellspacing="0">
						<%
						ItemEstruturaPrevisaoRevDao iePrevisaoDao = new ItemEstruturaPrevisaoRevDao(request);
						List listaRecursos = iePrevisaoDao.getRecursosByFonteRecurso(ieFonteRecurso.getComp_id().getCodFonr(), ieRevisao.getCodIettrev());
						if (listaRecursos != null && listaRecursos.size() > 0) {
							// ItemEstPrevisao cadastrados
							List listaItemRec = iePrevisaoDao.getRecursosByFonteRecurso(ieFonteRecurso.getFonteRecursoFonr().getCodFonr(), ieFonteRecurso.getItemEstruturarevisaoIettrev().getCodIettrev());
							Iterator itItemRec = listaItemRec.iterator();
								
							String valorCampo = "";
							%>
							<tr class="titulo">
								<td align="center">Recurso</td>
								<td align="center">Valor</td>
								<%
								ExercicioDao exercicioDao = new ExercicioDao(request);
								
								Collection listaExercicios = exercicioDao.getExerciciosValidos(ieRevisao.getItemEstruturaIettrev().getCodIett());
								Iterator itExerc = listaExercicios.iterator();
									
								int numExe = 0;
								
								while (itExerc.hasNext()) {
									ExercicioExe exercicio = (ExercicioExe) itExerc.next();
									numExe++;
									%>
									<td align="center"><%=exercicio.getDescricaoExe()%></td>
									<%
								}
									
								double[] totalAprovExe = new double[numExe];
								double[] totalRevExe = new double[numExe];
								itExerc = listaExercicios.iterator();
								numExe = 0;
								while (itExerc.hasNext()) {
									ExercicioExe exercicio = (ExercicioExe) itExerc.next();
									totalAprovExe[numExe] = 0;
									totalRevExe[numExe] = 0;
									numExe++;
								}
								%>
								<td align="center">Total</td>
							</tr>
							<%
							int col = 0;
							
							// RecursoRec distintos cadastrados (linhas das tabelas)
							List listaRec = iePrevisaoDao.getRecursosDistintosByFonteRecurso(ieFonteRecurso.getFonteRecursoFonr().getCodFonr(), ieFonteRecurso.getItemEstruturarevisaoIettrev().getCodIettrev());
							Iterator itRec = listaRec.iterator();
							
							while (itRec.hasNext()) {
								RecursoRec recurso = (RecursoRec) itRec.next();
								double totalAprovRec = 0;
								double totalRevRec = 0;
								%>
								<tr>
									<td class="form_label" rowspan="2" align="center" valign="midle"><%=recurso.getNomeRec()%></td>
									<td class="form_label">Aprovado</td>
									<%
									col = 0;
									itExerc = listaExercicios.iterator();
									while (itExerc.hasNext()) {
										ExercicioExe exercicio = (ExercicioExe) itExerc.next();
										
										valorCampo = "&nbsp";
											
										itItemRec = listaItemRec.iterator();
										while (itItemRec.hasNext()) {
											EfIettPrevisaoRevEfiepr ieRecurso = (EfIettPrevisaoRevEfiepr) itItemRec.next();
											if (ieRecurso.getExercicioExe().equals(exercicio) && 
														ieRecurso.getRecursoRec().equals(recurso)) {
												valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(ieRecurso.getValorAprovadoEfiepr());
												totalAprovRec = totalAprovRec + ieRecurso.getValorAprovadoEfiepr().doubleValue();
												totalAprovExe[col] = totalAprovExe[col] + ieRecurso.getValorAprovadoEfiepr().doubleValue();
											}
										}
										%>
										<td align="center" class="form_label"><%=valorCampo%></td>
										<%
										col++;
									}
									%>
									<td class="form_label" class="form_label"><%=Util.formataNumeroDecimalSemMilhar(totalAprovRec)%></td>
								</tr>
								<tr>
									<td class="form_label">Revisado</td>
									<%
									col = 0;
									itExerc = listaExercicios.iterator();
									while (itExerc.hasNext()) {
										ExercicioExe exercicio = (ExercicioExe) itExerc.next();
										
										valorCampo = "&nbsp";
										
										itItemRec = listaItemRec.iterator();
										while (itItemRec.hasNext()) {
											EfIettPrevisaoRevEfiepr ieRecurso = (EfIettPrevisaoRevEfiepr) itItemRec.next();
											if (ieRecurso.getExercicioExe().equals(exercicio) && 
														ieRecurso.getRecursoRec().equals(recurso)) {
												valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(ieRecurso.getValorRevisadoEfiepr());
												totalRevRec += ieRecurso.getValorRevisadoEfiepr().doubleValue();
												totalRevExe[col] += ieRecurso.getValorRevisadoEfiepr().doubleValue();
											}
										}
										%>
										<td align="center" class="form_label"><%=valorCampo%></td>
										<%
										col++;
									}
									%>
									<td class="form_label" class="form_label"><%=Util.formataNumeroDecimalSemMilhar(totalRevRec)%></td>
								</tr>
							<%
							}
							%>
							<tr class="titulo">
								<td rowspan="3" align="center">&nbsp;</td>
								<td>Total Aprovado</td>
								<%
								col = 0;
								double totalAprovadoGeral = 0;
								Iterator itTotAprov = listaExercicios.iterator();
								while (itTotAprov.hasNext()) {
									ExercicioExe exercicio = (ExercicioExe) itTotAprov.next();
									
									valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(new Double(totalAprovExe[col]));
									totalAprovadoGeral = totalAprovadoGeral + totalAprovExe[col];
									%>
									<td align="right"><%=valorCampo%></td>
									<%
									col++;
								}
								%>
								<td align="right"><%=Util.formataNumeroDecimalSemMilhar(totalAprovadoGeral)%></td>
							</tr>
							<tr class="titulo">
								<td>Total Revisado</td>
								<%
								col = 0;
								double totalRevisadoGeral = 0;
								Iterator itTotRev = listaExercicios.iterator();
								while (itTotRev.hasNext()) {
									ExercicioExe exercicio = (ExercicioExe) itTotRev.next();
									
									valorCampo = Pagina.trocaNullNumeroDecimalSemMilhar(new Double(totalRevExe[col]));
									totalRevisadoGeral += totalRevExe[col];
									%>
									<td align="right"><%=valorCampo%></td>
									<%
									col++;
								}
								%>
								<td align="right"><%=Util.formataNumeroDecimalSemMilhar(totalRevisadoGeral)%></td>
							</tr>
							<tr class="titulo">
								<td>Diferença</td>
								<%
								col = 0;
								Iterator itDif = listaExercicios.iterator();
								while (itDif.hasNext()) {
									ExercicioExe exercicio = (ExercicioExe) itDif.next();
									
									valorCampo = Util.formataNumeroDecimalSemMilhar(totalRevExe[col] - totalAprovExe[col]);
									%>
									<td align="right"><%=valorCampo%></td>
									<%
									col++;
								}
								
								double difTotal = totalRevisadoGeral - totalAprovadoGeral;
								%>
								<td align="right"><%=Util.formataNumeroDecimalSemMilhar(difTotal)%></td>
							</tr>
							<%
						} else {
						%>
							<tr><td class="titulo" align="center">Nenhum recurso cadastrado</td></tr>
						<%
						}
						%>
						</table>
				<%
			}  
		}
	}			
	}else{
		%>
		<tr><td colspan=6><br><%=_msg.getMensagem("itemEstrutura.fonteRecurso.inclusao.naoPermitida")%><br></td></tr>
		<%
		}
	}
}
%>

