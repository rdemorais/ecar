<jsp:directive.page import="ecar.util.Dominios"/>
<jsp:directive.page import="ecar.historico.HistoricoPtc"/>
<jsp:directive.page import="ecar.dao.ConfiguracaoDao"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.dao.PontoCriticoDao" %>
<%@ page import="ecar.pojo.PontoCriticoPtc"%>

<%@ page import="ecar.dao.ConfigMailCfgmDAO" %>
<%@ page import="ecar.pojo.ConfigMailCfgm" %>
<%@ page import="ecar.email.AgendadorEmail" %>
<%@ page import="ecar.dao.UsuarioDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>
<%@ page import="ecar.pojo.ItemEstruturaIett" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgmPK" %>
<%@ page import="ecar.pojo.TfuncacompConfigmailTfacfgm" %>
<%@ page import="ecar.dao.TfuncacompConfigmailTfacfgmDAO" %>
<%@ page import="ecar.pojo.EmpresaEmp" %>
<%@ page import="ecar.dao.EmpresaDao" %>
<%@ page import="comum.database.Dao" %>
<%@ page import="comum.util.Util" %>

<%@page import="ecar.evento.Evento"%>
<%@page import="ecar.evento.URLEvento"%>
<%@page import="ecar.evento.EventoManutencaoPontoCritico"%><html lang="pt-br">
<head>
<%@ include file="/include/meta.jsp"%>
</head> 
<body>
<form name="form" method="post" action="">   
<%
	Long   codIett	 = Long.valueOf(Pagina.getParamStr(request, "codIett"));
	String msg = "";
	String submit = "";
	String ultimoIdLinhaDetalhado = Pagina.getParamStr(request,"ultimoIdLinhaDetalhado");
%>
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="codAba" value='<%=Pagina.getParamStr(request, "codAba")%>'>
<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="cod" value='<%=Pagina.getParamStr(request, "cod")%>'>
<input type="hidden" name="codIett" value="<%=codIett%>">
<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>
<%
	String descricaoEvento = null;
	
	PontoCriticoDao pontoCriticoDao = new PontoCriticoDao(request);
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
	UsuarioDao usuDAO = new UsuarioDao();
	EmpresaDao empDAO = new EmpresaDao(request);
	Dao dao = new Dao();

	try{
			PontoCriticoPtc pontoCritico = new PontoCriticoPtc();
			UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
			String hidAcao = Pagina.getParamStr(request, "hidAcao");			
			if("incluir".equals(hidAcao)){
				descricaoEvento = "Inserção de "+Pagina.getParamStr(request, "labelEttf");
				pontoCriticoDao.setPontoCritico(request, pontoCritico);	
				pontoCriticoDao.salvar(pontoCritico, usuario);
				submit = "lista.jsp";
				msg = _msg.getMensagem("itemEstrutura.pontoCritico.inclusao.sucesso");
			}	
			if("alterar".equals(hidAcao)){
				descricaoEvento = "Alteração de "+ Pagina.getParamStr(request, "labelEttf");
				pontoCritico = (PontoCriticoPtc) pontoCriticoDao.buscar(PontoCriticoPtc.class, Long.valueOf(Pagina.getParamStr(request, "cod")));	

				/***Historico***/
	            	HistoricoPtc historico = new HistoricoPtc(pontoCritico, 
   	            												HistoricoPtc.alteracao,
	            												pontoCriticoDao.getSession(),
	            												new ConfiguracaoDao(request),
	            												request);
	            /***Historico***/
				
				pontoCriticoDao.alterar(pontoCritico, request, usuario, historico);
				submit = "frm_con.jsp";
				msg = _msg.getMensagem("itemEstrutura.pontoCritico.alteracao.sucesso");
			}
			if("excluir".equals(hidAcao)){
				descricaoEvento = "Exclusão de " + Pagina.getParamStr(request, "labelEttf");
				String codigosParaExcluir[] = request.getParameterValues("excluirPontoCritico");
				pontoCriticoDao.excluir(codigosParaExcluir, usuario);
				submit = "lista.jsp";
				msg = _msg.getMensagem("itemEstrutura.pontoCritico.exclusao.sucesso");				
			}
			
			if( "S".equals(Pagina.getParamStr(request, "autorizarMail")) ) {
							
				ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_PONTO_CRITICO);
			
				ItemEstruturaIett item = (ItemEstruturaIett) dao.buscar(ItemEstruturaIett.class, codIett);	
				List listIett = new ArrayList(item.getItemEstUsutpfuacIettutfas());
				Iterator itListIett = listIett.iterator();
			 
				// data limite para ...
				String dataLimitePara = "";
				String dataLimiteParaAnt = "";				
				
				while( itListIett.hasNext() ) {
				
					ItemEstUsutpfuacIettutfa itemEstUsutpfacIetutfa = (ItemEstUsutpfuacIettutfa) itListIett.next();
					TipoFuncAcompTpfa tpfa = itemEstUsutpfacIetutfa.getTipoFuncAcompTpfa();
			 
					TfuncacompConfigmailTfacfgmPK tfcfgmPK = new TfuncacompConfigmailTfacfgmPK();
					tfcfgmPK.setCodCfgm(configMailCfgm.getCodCfgm());
					tfcfgmPK.setCodTpfa(tpfa.getCodTpfa());
										
					TfuncacompConfigmailTfacfgm tfcfm = (TfuncacompConfigmailTfacfgm) tfuncacompConfigmailTfacfgmDAO.buscar(TfuncacompConfigmailTfacfgm.class, tfcfgmPK);
			 
					if ("S".equals(tfcfm.getEnviaMailTfacfgm())) {
						
						//UsuarioUsu usu = (UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getComp_id().getCodUsu());
						
						
						List usuarios = new ArrayList();
						if (itemEstUsutpfacIetutfa.getUsuarioUsu() != null) {
							usuarios.add((UsuarioUsu) usuDAO.buscar(UsuarioUsu.class, itemEstUsutpfacIetutfa.getUsuarioUsu().getCodUsu()));
						} else if (itemEstUsutpfacIetutfa.getSisAtributoSatb() != null){
							usuarios.addAll(usuDAO.getUsuariosBySisAtributoSatb(itemEstUsutpfacIetutfa.getSisAtributoSatb()));
						}
						
						Iterator itUsu = usuarios.iterator();
						
						// monta link para enviar via email para usuarios relacionados ao item 
						String link = null;
								
						Evento evento = null;
						evento = new EventoManutencaoPontoCritico();
					    link = URLEvento.montaURLEvento(evento, request);
						
						while (itUsu.hasNext()){
						
							UsuarioUsu usu = (UsuarioUsu) itUsu.next();
						
							String textoMail = "";
							String assunto   = "";
							String remetente = "";
									
							if( configMailCfgm.getTextosSiteMail() != null ) {
								textoMail = configMailCfgm.getTextosSiteMail().getTextoTxts();
								assunto   = configMailCfgm.getTextosSiteMail().getDescricaoUsoTxts();
								remetente = configMailCfgm.getTextosSiteMail().getEmailResponsavelTxts();
							}
									
							// se nao tem remetente configurado no textoSite, pega da empresa.
							if( "".equals(remetente) || remetente == null ) {
								List listEmpresa = new ArrayList();
								EmpresaEmp emp = null;
						
								listEmpresa = empDAO.listar(EmpresaEmp.class, null);
							
								Iterator it = listEmpresa.iterator();
								while( it.hasNext() ) {
									emp = (EmpresaEmp) it.next();
								}
							 
								remetente = emp.getEmailContatoEmp();
							}
											
							AgendadorEmail ae = new AgendadorEmail();
											
							String html = null;				
							
							html = ae.montaEmailComLink(textoMail, usu.getNomeUsu(), null, codIett, descricaoEvento, null, null, null, null, link).toString();
								
							if (usu.getEmail1UsuSent()!=null && usu.getEmail1UsuSent().length()>0)
								ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "",usu);
						}
				}
			}
		}
			
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "lista.jsp";
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
		submit = "lista.jsp";
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	%>

<script type="text/javascript">
			document.form.action = "<%=submit%>";
			document.form.submit();
</script>

</form>
</body>
</html>