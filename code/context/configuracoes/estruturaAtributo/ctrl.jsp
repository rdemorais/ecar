
<%@page import="java.util.HashSet"%>
<jsp:directive.page import="ecar.pojo.FuncaoFun"/>
<jsp:directive.page import="ecar.dao.FuncaoDao"/><%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.EstruturaEtt" %>
<%@ page import="ecar.pojo.EstruturaAtributoEttat" %>
<%@ page import="ecar.pojo.EstruturaAtributoEttatPK" %>
<%@ page import="ecar.dao.EstruturaDao" %>
<%@ page import="ecar.dao.EstruturaAtributoDao" %>  
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ecar.pojo.EstAtribTipoAcompEata" %>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>


<%@page import="java.util.Iterator"%><html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
</head>
<body>

<form name="form" method="post">
    <!-- campo de controle para a navegação de registros -->
	<input type="hidden" name="hidRegistro" value="<%=Pagina.getParamInt(request, "hidRegistro")%>">
	
	<!-- campo de controle para mensagens -->
	<input type="hidden" name="msgOperacao" value=""> 
	<input type="hidden" name="msgPesquisa" value="">	
	<input type="hidden" name="hidPesquisou" value="">	
	
</form>

<%
	EstruturaAtributoEttat estruturaAtributo = new EstruturaAtributoEttat();
	EstruturaAtributoDao estruturaAtributoDao = new EstruturaAtributoDao(request);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	String msgSequenciaDuplicada = null;
	String submit = "frm_pes.jsp";
	boolean refazPesquisa = true;
//	boolean excluir = "excluir".equalsIgnoreCase(hidAcao);
	
	if ("alterar".equalsIgnoreCase(hidAcao)) {
		try {			
			EstruturaAtributoEttatPK chave = new EstruturaAtributoEttatPK();
			chave.setCodEtt(Long.valueOf(Pagina.getParamLong(request, "codEtt")));
			chave.setCodAtb(Long.valueOf(Pagina.getParamLong(request, "codAtb")));
			/* o buscar deve estar no try, pois pode ocorrer um erro de hibernate ou bd */			
			estruturaAtributo = (EstruturaAtributoEttat) estruturaAtributoDao.buscar(EstruturaAtributoEttat.class, chave);
			
			/*Verificação das estruturas de acompanhementos que para quem o bloqueio foi liberado */
			if ((estruturaAtributo.getIndPodeBloquearEttat() != null && estruturaAtributo.getIndPodeBloquearEttat().equals(Pagina.SIM) ) 
				|| (Pagina.getParamStr(request, "indPodeBloquearEttat").equals(Pagina.SIM) ) ){
				estruturaAtributo.setLibTipoFuncAcompTpfas(estruturaAtributoDao.setLimbTipoFuncAcompTpfa());
			} else { //Verificar se tem que excluir as referencias
				estruturaAtributo.setLibTipoFuncAcompTpfas(new HashSet());
			}
			
			/* recupera os novos dados da EstruturaAtributo*/
			//estruturaAtributoDao.setEstruturaAtributo(request, estruturaAtributo, true);
						
			estruturaAtributoDao.alterar(request, estruturaAtributo);
			
			//Verifica se a sequência de aparição no formulário, lista e árvore configurada no atributo na estrutura em Dados Gerais é mínima
			//e se é duplicada de outro atributo
			
			int menorSequencia = verificaOrdenacaoAtributo(estruturaAtributo, estruturaAtributoDao);
			if (menorSequencia != -1){
				msgSequenciaDuplicada = _msg.getMensagem("estruturaAtributo.alteracao.primeiraSequenciaDuplicada", new String[]{""+menorSequencia});
			}
			
			msg = _msg.getMensagem("estruturaAtributo.alteracao.sucesso" );
			

		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
	} else if ("pesquisar".equalsIgnoreCase(hidAcao)) {
		try{
			/* aqui devemos usar getParam, pois queremos null se o campo estiver vazio */
			estruturaAtributoDao.setEstruturaAtributo(request, estruturaAtributo, false);
			session.setAttribute("objPesquisa", estruturaAtributo);
			FuncaoFun funcaoFun = null;
			if (Pagina.getParam(request, "funcaoFun") != null){
	    		funcaoFun = (FuncaoFun) new FuncaoDao(request).buscar(FuncaoFun.class,Long.valueOf(Pagina.getParam(request, "funcaoFun")));
	    	}
			session.setAttribute("objPesquisaFuncaoFun", funcaoFun);		
			
			/* Setando os valores dos tipos de acompanhamentos escolhidos na tela */
			List lTipoAcomp = new ArrayList();
			String codTas = Pagina.getParamStr(request, "codTas");
			if(!"".equals(codTas)){
				String[] tiposAcomps = codTas.split("|");
				TipoAcompanhamentoDao tipoAcompDao = new TipoAcompanhamentoDao(request);
				for(int i = 0; i < tiposAcomps.length; i++){
					if(!"".equals(tiposAcomps[i]) && !"|".equals(tiposAcomps[i])){
						String campoCheck = "exibirEata" + tiposAcomps[i];
						String campoSequencia = "sequenciaEata" + tiposAcomps[i];
						// SERPRO
						String campoCheckFiltro = "filtroEata" + tiposAcomps[i];
						String exibirTa = Pagina.getParamStr(request, campoCheck);
						String sequenciaTa = Pagina.getParamStr(request, campoSequencia);
						// SERPRO
						String filtroTa = Pagina.getParamStr(request, campoCheckFiltro);
						
						if(!"".equals(exibirTa) || !"".equals(sequenciaTa) || !"".equals(filtroTa)){
							EstAtribTipoAcompEata tipoAcomp = new EstAtribTipoAcompEata();
							tipoAcomp.setExibirEata(exibirTa);
							if(!"".equals(sequenciaTa)){
								tipoAcomp.setSequenciaEata(Long.valueOf(sequenciaTa));
							}
							// SERPRO
							tipoAcomp.setFiltroEata(filtroTa);
							tipoAcomp.setTipoAcompanhamentoTa((TipoAcompanhamentoTa)tipoAcompDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(tiposAcomps[i])));
							lTipoAcomp.add(tipoAcomp);
						}
					}
				}
			}
			
			if(!lTipoAcomp.isEmpty()){
				session.setAttribute("objPesquisaTipoAcomp", lTipoAcomp);
			}
			
		}catch(ECARException e){
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());		
		}
	}
	
	if (msgSequenciaDuplicada != null){
		Mensagem.alert(_jspx_page_context, msgSequenciaDuplicada);
	}
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 

	if (refazPesquisa) {
		try {
			estruturaAtributo = (EstruturaAtributoEttat) session.getAttribute("objPesquisa");
			List lTipoAcomp = (List) session.getAttribute("objPesquisaTipoAcomp");
			FuncaoFun funcaoFun = (FuncaoFun) session.getAttribute("objPesquisaFuncaoFun");
			
			List lista = estruturaAtributoDao.filtrarEstruturaAtributoByEata(estruturaAtributoDao.pesquisar( (EstruturaAtributoEttat) estruturaAtributo, new String[]{"comp_id.codEtt", "asc", "comp_id.codAtb", "asc"}), lTipoAcomp, funcaoFun);
			
			boolean pesquisou = (lista.size() > 0) ? true : false;
			session.setAttribute("lista", lista);
			if (pesquisou) {
				submit = "frm_nav.jsp";
				msg = null;
				msgSequenciaDuplicada = null;
			} else {
  			    Mensagem.setInput(_jspx_page_context, "document.form.hidPesquisou.value", "false");
				submit = "frm_pes.jsp";
				msg = _msg.getMensagem("estruturaAtributo.pesquisa.nenhumRegistro");
			}
		} catch (ECARException e) {
			org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			msg = _msg.getMensagem(e.getMessageKey());
		} catch (Exception e){
		%>
			<%@ include file="/excecao.jsp"%>
		<%
		}
		
		//Mensagem de aviso caso o usuário configure um atributo na estrutura com a sequência mínima duplicada
		if (msgSequenciaDuplicada != null){
			Mensagem.alert(_jspx_page_context, msgSequenciaDuplicada);
		}
		if (msg != null){
			Mensagem.setInput(_jspx_page_context, "document.form.msgPesquisa.value", msg);
		}
	}
	%>
	
	<%!
		//Método que verifica se o atributo na estrutura sendo modificado está com uma sequência de apresentação na listagem / árvore igual à menor sequência
		//configurada a um atributo anteriormente				
		private int verificaOrdenacaoAtributo(EstruturaAtributoEttat estruturaAtributo, EstruturaAtributoDao estruturaAtributoDao) throws ECARException{
		
			int menorSequencia = -1;
		
			if (estruturaAtributo.getAtributosAtb().getFuncaoFun().getNomeFun().equals(FuncaoDao.NOME_FUNCAO_DADOS_GERAIS)){
				
				List estruturaAtributos = estruturaAtributoDao.getEstruturaAtributosFuncao(estruturaAtributo.getEstruturaEtt(), estruturaAtributo.getAtributosAtb().getFuncaoFun());
				
				Iterator itEstruturaAtributos = estruturaAtributos.iterator();
																
				while (itEstruturaAtributos.hasNext()){
					
					EstruturaAtributoEttat estruturaAtributoCorrente = (EstruturaAtributoEttat) itEstruturaAtributos.next();
					
					if (menorSequencia == -1 && !estruturaAtributoCorrente.equals(estruturaAtributo) 
							&& estruturaAtributoCorrente.getSeqApresListagemTelaEttat() != null){						
						
						menorSequencia = estruturaAtributoCorrente.getSeqApresListagemTelaEttat().intValue();
						continue;
					}
					
					if (!estruturaAtributoCorrente.equals(estruturaAtributo) && estruturaAtributoCorrente.getSeqApresListagemTelaEttat() != null
							&& estruturaAtributoCorrente.getSeqApresListagemTelaEttat().intValue() < menorSequencia){
												
						menorSequencia = menorSequencia = estruturaAtributoCorrente.getSeqApresListagemTelaEttat().intValue();
					}
				}
				
				if (estruturaAtributo.getSeqApresListagemTelaEttat() == null || estruturaAtributo.getSeqApresListagemTelaEttat().intValue() != menorSequencia){
					menorSequencia = -1;
				}
			}
			
			return menorSequencia;
		}
				
	%>
	
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 
</body>
</html>
