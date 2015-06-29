<jsp:directive.page import="java.util.List"/>
<jsp:directive.page import="org.apache.velocity.runtime.directive.Foreach"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="ecar.login.SegurancaECAR"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>
<%@ page import="ecar.pojo.Pesquisa" %>
<%@ page import="ecar.dao.PesquisaDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="comum.util.Data" %> 
<%@ page import="java.util.Date" %>
<%@ page import="comum.util.Mensagem" %>
<%@ page import="comum.util.Pagina" %>
<%@ page import="ecar.exception.ECARException" %>
<%
	
	Pesquisa pesquisa = new Pesquisa();
	PesquisaDao pesquisaDao = new PesquisaDao(request);

	Mensagem mensagem = new Mensagem(application);
	
	String hidAcao = Pagina.getParam(request, "hidAcao");
	String msg = null;
	boolean existePesquisaMesmoNome = false;
	boolean existeItem = true;
	
	if (hidAcao.equals("incluir")){
		
		String msgString;
		
		pesquisaDao.setPesquisa(request, pesquisa);
		
		if (pesquisa.getIndTipoPesquisa().equals("S")){
			List pesquisasSistema = pesquisaDao.listarPesquisaSistema();
			for (Iterator iterator = pesquisasSistema.iterator(); iterator.hasNext(); ) {  
				Pesquisa nomeFiltro = (Pesquisa) iterator.next();
				if ( nomeFiltro.getNomePesquisa().equals(pesquisa.getNomePesquisa())){
					existePesquisaMesmoNome = true;		
				}
			}
		}
		
		if (pesquisa.getIndTipoPesquisa().equals("U")){
			UsuarioUsu usuarioUsu = ((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();
			List pesquisasUsuario = pesquisaDao.listarPesquisaUsuario(usuarioUsu);
			for (Iterator iterator = pesquisasUsuario.iterator(); iterator.hasNext(); ) {  
				Pesquisa nomeFiltro = (Pesquisa) iterator.next();
				if ( nomeFiltro.getNomePesquisa().equals(pesquisa.getNomePesquisa())){
					existePesquisaMesmoNome = true;		
				}
			}
		}
		
		if ((pesquisa.getPesquisaIetts() == null) || (pesquisa.getPesquisaIetts().size() == 0)) {
			existeItem = false;
        }
		
		if (existePesquisaMesmoNome){
			msg = "Nome da Pesquisa já Existe!";
		} else if (!existeItem){
			msg = _msg.getMensagem("pesquisa.validacao.itemAref");
		} else {
			pesquisaDao.salvarPesquisa(pesquisa);
			msg = "Inclusão Realizada com Sucesso!";
		}
	%>
	
	<script language="javascript">
		alert('<%=msg%>'); 
		//window.opener.reload(); 
		<%if(!existePesquisaMesmoNome && existeItem){%>
			window.close();
		<%}else{%>
			history.back(1);			
		<%}%>
	</script> 
	<%
	} else if (hidAcao.equals("excluir")){
		String codPesqExc = Pagina.getParamStr(request, "codPesquisaExcluir");
		try {
			pesquisa = (Pesquisa) pesquisaDao.buscar(Pesquisa.class, Long.valueOf(codPesqExc));
			pesquisaDao.excluir(pesquisa);
		} catch ( ECARException e){
			%>FALHA<%
		}
	}
		

%>