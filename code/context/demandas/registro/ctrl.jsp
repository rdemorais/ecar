
<jsp:directive.page import="ecar.pojo.LocalItemLit"/>
<jsp:directive.page import="ecar.pojo.DemandasGrpAcesso"/>
<jsp:directive.page import="ecar.pojo.SisAtributoSatb"/>
<jsp:directive.page import="ecar.dao.DemandasGrpAcessoDao"/>
<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="java.util.ArrayList"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.RegDemandaRegd" %>
<%@ page import="ecar.dao.RegDemandaDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.dao.SisTipoExibicGrupoDao" %>
<%@ page import="ecar.pojo.SitDemandaSitd" %>
<%@ page import="ecar.dao.SitDemandaDao" %>
<%@ page import="ecar.pojo.PrioridadePrior" %>
<%@ page import="ecar.pojo.EntidadeEnt" %>
<%@ page import="ecar.dao.EntidadeDao" %>

<%@ page import="comum.util.Data" %> 
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="comum.database.Dao" %>

<%@ page import="ecar.pojo.ObjetoDemanda" %>
<%@ page import="ecar.pojo.SisGrupoAtributoSga" %>



<%@page import="ecar.pojo.VisaoDemandasVisDem"%>
<%@page import="ecar.dao.AtributoDemandaDao"%><html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">
	
	
	<%//Campos de ordenação da listagem%>
	<input type="hidden" name="clCampo" value="<%=Pagina.getParam(request, "clCampo")%>">
	<input type="hidden" name="clOrdem" value="<%=Pagina.getParam(request, "clOrdem")%>">
	<input type="hidden" name="refazPesquisa" value="">
	<input type="hidden" name="refazOrdenacao" value="">
	
	<%//Campos utilizado para alteração chamada da consulta/apontamento%>
	<input type="hidden" name="codRegd" value="<%=Pagina.getParam(request, "codRegd")%>">
	<input type="hidden" name="origem" value="<%=Pagina.getParam(request, "origem")%>">

	<!--  -->
	<input type="hidden" name="hidAcao" value="<%=Pagina.getParam(request, "hidAcao")%>">
	<input type="hidden" name="hidGerarArquivo" id="hidGerarArquivo" value="<%=Pagina.getParam(request, "hidGerarArquivo")%>">
	
	<%
	String telaAssociacaoDemandas = Pagina.getParamStr(request, "telaAssociacaoDemandas");
	String telaDetalhamentoDemanda = Pagina.getParamStr(request, "telaDetalhamentoDemanda");
	String codAba = Pagina.getParamStr(request, "codAba");
	String codIett = Pagina.getParamStr(request, "codIett");
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
	%>
	<input type="hidden" id="telaAssociacaoDemandas" name="telaAssociacaoDemandas" value="<%=telaAssociacaoDemandas%>">
	<input type="hidden" id="telaDetalhamentoDemanda" name="telaDetalhamentoDemanda" value="<%=telaDetalhamentoDemanda%>">
	<input type="hidden" id="codAba" name="codAba" value="<%=codAba%>">
	<input type="hidden" id="codIett" name="codIett" value="<%=codIett%>">
	<input type="hidden" id="ultimoIdLinhaDetalhado" name="ultimoIdLinhaDetalhado" value="<%=ultimoIdLinhaDetalhado%>">
	
</form>

<%
	RegDemandaRegd regDemanda = new RegDemandaRegd();
	RegDemandaDao regDemandaDao = new RegDemandaDao(request);

	Mensagem mensagem = new Mensagem(application);
		
	String hidAcao = Pagina.getParamStr(request, "hidAcao");
	String msg = null;
	String submit = "lista.jsp";
	String origem = Pagina.getParam(request, "origem");
	String hidGerarArquivo = Pagina.getParamStr(request, "hidGerarArquivo");

	/* default sempre refazer a pesquisa */
	boolean incluir = "incluir".equalsIgnoreCase(hidAcao);
	boolean alterar = "alterar".equalsIgnoreCase(hidAcao);
	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	boolean inicio_filtro = "inicio_filtro".equalsIgnoreCase(hidAcao);	
	boolean filtrar = "filtrar".equalsIgnoreCase(hidAcao);
	boolean classificar = "classificar".equalsIgnoreCase(hidAcao);
	boolean limpar_filtro  = "limpar_filtro".equalsIgnoreCase(hidAcao);	
	boolean gerarArquivo = "exportar".equalsIgnoreCase(hidAcao);
	
	boolean classifica = false;
	
	//Variável setada para caso de uso ClassificaDemanda
	if ("classifica".equals(session.getAttribute("classifica"))) {
		classifica = true;
	}

	if (incluir) {
		try {
			regDemandaDao.setRegDemanda(regDemanda, request);
			regDemandaDao.salvar(regDemanda);
			
			msg = _msg.getMensagem("registroDemanda.inclusao.sucesso");
			session.removeAttribute("objRegDemanda");
			%>
			<script language="JavaScript">
			//Após executar alguma ação refazer a listagem
			document.form.refazPesquisa.value = "refaz";
			document.form.refazOrdenacao.value = "refaz";
			</script>
			<%
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objRegDemanda", regDemanda);
			msg = _msg.getMensagem(e.getMessageKey());
			origem = "frm_inc.jsp";
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (alterar) {
		try {
			regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, Long.valueOf(Pagina.getParamLong(request, "codRegd")));
			//
			regDemandaDao.alterar(regDemanda, request);
			msg = _msg.getMensagem("registroDemanda.alteracao.sucesso");
			session.removeAttribute("objRegDemanda");
			
			if (!"".equals(origem)) {
				submit = origem;
			}
			
			%>
			<script language="JavaScript">
				//Após executar alguma ação refazer a listagem
				document.form.refazPesquisa.value = "refaz";
				document.form.refazOrdenacao.value = "refaz";
			</script>
			<%
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objRegDemanda", regDemanda);
			msg = _msg.getMensagem(e.getMessageKey());
			
			if (!"".equals(origem)) {
				submit = origem;
			}	
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (excluir) {
		try {
			String codigosParaExcluir[] = request.getParameterValues("excluir");
			regDemandaDao.excluir(codigosParaExcluir);
			msg = _msg.getMensagem("registroDemanda.exclusao.sucesso");
			
			%>
			<script language="JavaScript">
				//Após executar alguma ação refazer a listagem
				document.form.refazPesquisa.value = "refaz";
				document.form.refazOrdenacao.value = "refaz";
			</script>
			<%
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}	
	} else if (classificar) {
		submit = "classificacao.jsp";
		try {
			boolean permiteClass = true;
		
			regDemanda = (RegDemandaRegd) regDemandaDao.buscar(RegDemandaRegd.class, Long.valueOf(Pagina.getParamLong(request, "codRegd")));
			//A tela de Classifica demandas também editará os campos [Thaise 23/04/2008]
			regDemanda.setUsuarioUsuByCodUsuAlteracaoRegd(((ecar.login.SegurancaECAR) session.getAttribute("seguranca")).getUsuario());
			regDemandaDao.alterar(regDemanda, request);
			
			
			SitDemandaSitd situacaoAnt = (SitDemandaSitd) regDemandaDao.buscar(SitDemandaSitd.class, regDemanda.getSitDemandaSitd().getCodSitd());
			
			if (Pagina.getParam(request, "prioridadePrior") != null){
				regDemanda.setPrioridadePrior((PrioridadePrior) regDemandaDao.buscar(PrioridadePrior.class, Long.valueOf(Pagina.getParam(request, "prioridadePrior"))));
			}
			else{
				regDemanda.setPrioridadePrior(null);
			}
			
			if (Pagina.getParam(request, "sitDemandaSitd") != null){
				regDemanda.setSitDemandaSitd((SitDemandaSitd) regDemandaDao.buscar(SitDemandaSitd.class, Long.valueOf(Pagina.getParam(request, "sitDemandaSitd"))));
			}
			
			//Gravar data da situação de demanda se houve modificação na situação da demanda.
			if(situacaoAnt != null && !situacaoAnt.equals(regDemanda.getSitDemandaSitd())){
				regDemanda.setDataSituacaoRegd(Data.getDataAtual());
			}
			
			//validar situação com item estrutura
			//localizar a situação com Ind_Primeira_Situacao == S
			SitDemandaSitd situacaoPrim = new SitDemandaSitd();
			situacaoPrim.setIndPrimeiraSituacaoSitd("S");
			SitDemandaDao situacaoDao = new SitDemandaDao(request);
			List listaSit = situacaoDao.pesquisar(situacaoPrim, new String[] {"codSitd", "asc"});
			if (listaSit != null && listaSit.size() > 0) {
				situacaoPrim = (SitDemandaSitd) listaSit.iterator().next();
			}
			
			//se situacao == ind_prim_situacao && situcao foi alterada
			if (situacaoPrim.equals(regDemanda.getSitDemandaSitd()) && !situacaoAnt.equals(regDemanda.getSitDemandaSitd())) {
				//verifica se existe iten de estrutura
				if (regDemanda.getItemRegdemandaIregds() != null && regDemanda.getItemRegdemandaIregds().size() > 0) {
					msg = _msg.getMensagem("classificarDemanda.validacao.sitDemandaSitd.invalido");
					permiteClass = false;
					submit = "frm_classifica.jsp";
				}
			}
			
			if (permiteClass) {
				regDemandaDao.alterar(regDemanda);
				msg = _msg.getMensagem("classificarDemanda.gravar.sucesso");
				session.removeAttribute("objRegDemanda");
			
				%>
				<script language="JavaScript">
					//Após executar alguma ação refazer a listagem
					document.form.refazOrdenacao.value = "refaz";
					document.form.refazPesquisa.value = "refaz";
				</script>
				<%
			}
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			session.setAttribute("objRegDemanda", regDemanda);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (limpar_filtro) {
		request.getSession().removeAttribute("num_registro");
		request.getSession().removeAttribute("descricao");
		request.getSession().removeAttribute("observacao");
		request.getSession().removeAttribute("num_doc_origem");
		request.getSession().removeAttribute("usu_sol");
		request.getSession().removeAttribute("nome_sigla");
		request.getSession().removeAttribute("data_limite_ini");
		request.getSession().removeAttribute("data_limite_final");
		request.getSession().removeAttribute("data_sol_ini");
		request.getSession().removeAttribute("data_sol_final");
		request.getSession().removeAttribute("codLocais");	
		request.getSession().removeAttribute("prioridade");
		request.getSession().removeAttribute("situacao");
		request.getSession().removeAttribute("orgao");
		request.getSession().removeAttribute("entidade");
		request.getSession().removeAttribute("formasContato");
		request.getSession().removeAttribute("listLocais");

		List list = regDemandaDao.pesquisar(
				Long.parseLong("0"),
				"", 
				"", 
				0,
				"", 
				"", 
				null, 
				null, 
				null, 
				null, 
				"", 
				Long.parseLong("0"),
				Long.parseLong("0"),
				Long.parseLong("0"),
				new EntidadeEnt(),
				"",
				request);
		
		//Merge		
		//session.setAttribute("listaRegistros", list);	
		request.getSession().removeAttribute("parametrosFiltroDemanda");	
		
		submit = "pesquisa_filtro.jsp";
	} else if (inicio_filtro) {
		submit = "pesquisa_filtro.jsp";		
		
	} else if (filtrar) {
		try {
		
			String num_registro = Pagina.getParamStr(request, "num_registro");
			String descricao = Pagina.getParamStr(request, "descricao");
			String observacao = Pagina.getParamStr(request, "observacao");			
			String num_doc_origem = Pagina.getParamStr(request, "num_doc_origem");
			String usu_sol = Pagina.getParamStr(request, "usu_sol");
			String nome_sigla = Pagina.getParamStr(request, "nome_sigla");
			String codLocais = Pagina.getParamStr(request, "codLocais"); 
			Date data_limite_ini = Data.parseDate(Pagina.getParamStr(request, "data_limite_ini"));
			Date data_limite_final = Data.parseDate(Pagina.getParamStr(request, "data_limite_final"));
			Date data_sol_ini = Data.parseDate(Pagina.getParamStr(request, "data_sol_ini"));
			Date data_sol_final = Data.parseDate(Pagina.getParamStr(request, "data_sol_final"));
			
			String prioridade = Pagina.getParamStr(request, "prioridade");
			String situacao = Pagina.getParamStr(request, "situacao");
			String orgao = Pagina.getParamStr(request, "orgao");
			String formasContato = Pagina.getParamStr(request, "formasContato");
			
			/* ** Atributos Variáveis de Entidade ** */
			EntidadeEnt entidade = new EntidadeEnt();
			EntidadeDao entidadeDao = new EntidadeDao(request);
			entidadeDao.setAtributosEntidade(request, entidade);

			RegDemandaRegd regDemandaPesq = new RegDemandaRegd();
			
			//Merge
			//session.setAttribute("listaRegistros", lista);
			session.setAttribute("filtrado", "S");
				
			
			//***********************************************
			//******* INÍCIO SALVAR FILTRO NA SESSÃO ********
			//***********************************************
						
			Map parametrosFiltroDemanda = new HashMap();
			
			// recupera o id da visao atual selecionada
			int visaoParameter = ((VisaoDemandasVisDem)request.getSession().getAttribute(VisaoDemandasVisDem.VISAO_SELECIONADA)).getCodVisao().intValue();
			
			AtributoDemandaDao atributoDemandaDao = new AtributoDemandaDao(request);
			
			//Lista com os nomes de todos os atributos do request
   			Enumeration listaNomesAtributosRequestTemp = request.getParameterNames();
   			List listaNomesAtributosRequest = new ArrayList();
   			while(listaNomesAtributosRequestTemp.hasMoreElements()){
   				listaNomesAtributosRequest.add(listaNomesAtributosRequestTemp.nextElement());
   			}
   			
   			parametrosFiltroDemanda.put("listaNomesAtributosRequest", listaNomesAtributosRequest);			
			
			//atributos livres			
   			List atributosLivres = atributoDemandaDao.getAtributosDemandaVisaoAtivosQueSaoFiltro(new Long(visaoParameter));
   			Iterator itAtributos = atributosLivres.iterator();
   			
			while (itAtributos.hasNext()){
   				ObjetoDemanda atributoDemanda = (ObjetoDemanda) itAtributos.next();
   				SisGrupoAtributoSga grupoAtributo = atributoDemanda.iGetGrupoAtributosLivres();
   				String nomeCampo = null;
   				String tipoCampo = null;
   				// atributo livre
   				if (grupoAtributo!=null) {
   	   				nomeCampo = "a" + grupoAtributo.getCodSga().toString();
   	   				tipoCampo = grupoAtributo.getSisTipoExibicGrupoSteg().getCodSteg().toString();
   					String[] atributos = request.getParameterValues(nomeCampo);
   	    			parametrosFiltroDemanda.put(nomeCampo, atributos);
   				// não é atributo livre
   				} else {
   					String[] atributos = request.getParameterValues(atributoDemanda.iGetNome());
   					if (atributos!=null && atributos.length==1)
   	    				parametrosFiltroDemanda.put(atributoDemanda.iGetNome(), atributos[0]);
   					else
   						parametrosFiltroDemanda.put(atributoDemanda.iGetNome(), atributos);
   				}
   				
				//Se for CheckBox ou RadioButton ou ListBox
   	 //   		if(tipoCampo.equals(SisTipoExibicGrupoDao.CHECKBOX) || tipoCampo.equals(SisTipoExibicGrupoDao.LISTBOX)
   	   // 		   || tipoCampo.equals(SisTipoExibicGrupoDao.COMBOBOX) || tipoCampo.equals(SisTipoExibicGrupoDao.RADIO_BUTTON)){
   	    //			String[] atributos = request.getParameterValues(nomeCampo);
   	    			//parametrosFiltroDemanda.put(nomeCampo, atributos);
   	    	//	} 
   	    	}
		
			//outros atributos
   			String nomeCampo = "";
   			Enumeration lAtrib = request.getParameterNames();
   				
   			while (lAtrib.hasMoreElements()) {
   				nomeCampo = (String) lAtrib.nextElement();
   				if (!parametrosFiltroDemanda.containsKey(nomeCampo)){
   					parametrosFiltroDemanda.put(nomeCampo, Pagina.getParamStr(request, nomeCampo));
   				}
   	    	}
			
			session.setAttribute("parametrosFiltroDemanda", parametrosFiltroDemanda);		
						
			//******************************************
			//******* FIM SALVAR FILTRO NA SESSÃO ******
			//******************************************
			
			request.getSession().setAttribute("num_registro", num_registro);
			request.getSession().setAttribute("descricao", descricao);
			request.getSession().setAttribute("observacao", observacao);
			request.getSession().setAttribute("num_doc_origem", num_doc_origem);
			request.getSession().setAttribute("usu_sol", usu_sol);
			request.getSession().setAttribute("nome_sigla", nome_sigla);
			request.getSession().setAttribute("data_limite_ini", Pagina.getParamStr(request, "data_limite_ini"));
			request.getSession().setAttribute("data_limite_final", Pagina.getParamStr(request, "data_limite_final"));
			request.getSession().setAttribute("data_sol_ini", Pagina.getParamStr(request, "data_sol_ini"));
			request.getSession().setAttribute("data_sol_final", Pagina.getParamStr(request, "data_sol_final"));
			request.getSession().setAttribute("codLocais", codLocais);
			request.getSession().setAttribute("prioridade", prioridade);
			request.getSession().setAttribute("situacao", situacao);
			request.getSession().setAttribute("orgao", orgao);
			request.getSession().setAttribute("entidade", entidade);
			request.getSession().setAttribute("formasContato", formasContato);
			//		
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if (gerarArquivo) {
		
		List listaRegistros = (List) session.getAttribute("listaRegistrosRelatorio");
		if (!(listaRegistros !=null && listaRegistros.size()>0)) {
			 msg = _msg.getMensagem("registroAnexoDemanda.gerarArquivo.listaDemandasVazia");
		}
		submit = "geracaoArquivo.jsp";
		
	}
	
	if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
%>

	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 

</body>
</html>

