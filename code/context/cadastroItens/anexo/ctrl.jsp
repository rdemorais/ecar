
<jsp:directive.page import="ecar.util.Dominios"/><%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ page import="ecar.dao.ItemEstruturaUploadDao" %>
<%@page import="ecar.pojo.ItemEstrutUploadIettup"%>  
 
<%@page import="comum.util.FileUpload"%>
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
<%@ page import="comum.util.Data" %>
<%@ page import="ecar.evento.Evento" %>
<%@ page import="ecar.evento.URLEvento" %>
<%@page import="ecar.evento.EventoManutencaoAnexo"%>
 

<%@page import="ecar.pojo.ConfiguracaoCfg"%>
<%@page import="ecar.dao.ConfiguracaoDao"%><html lang="pt-br"> 
<head>
<%@ include file="../../include/meta.jsp"%>
</head>
<body> 
<form name="form" method="post">   
<%
	Long   codIett	 = null;
	String autorizarMail = null;
	Dao dao = new Dao();
	TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
	UsuarioDao usuDAO = new UsuarioDao();
	ItemEstruturaUploadDao anexoDao = new ItemEstruturaUploadDao(request);
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	EmpresaDao empDAO = new EmpresaDao(request);
	String labelEttf = "";
%>
<% 
	String msg = "";
	String submit = "";
	try{ 
		boolean isFormUpload = FileUpload.isMultipartContent(request);
		List campos = new ArrayList();
		if(isFormUpload){
			campos = FileUpload.criaListaCampos(request); 
			 }
%>

<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 

<%if(isFormUpload){
		autorizarMail = FileUpload.verificaValorCampo(campos, "autorizarMail");
		codIett	 = Long.valueOf(FileUpload.verificaValorCampo(campos,"codIett"));
		String ultimoIdLinhaDetalhado = FileUpload.verificaValorCampo(campos,"ultimoIdLinhaDetalhado");
		labelEttf = FileUpload.verificaValorCampo(campos,"labelEttf");
%>
<input type="hidden" name="codAba" value=<%=FileUpload.verificaValorCampo(campos,"codAba")%>>
<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="cod" value=<%=FileUpload.verificaValorCampo(campos,"cod")%>>
<input type="hidden" name="codIettuc" value=<%=FileUpload.verificaValorCampo(campos,"codIettuc")%>>
<input type="hidden" name="codIett" value=<%=FileUpload.verificaValorCampo(campos,"codIett")%>>
<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>
<%}else{
		autorizarMail = Pagina.getParamStr(request, "autorizarMail");
		codIett	 = Long.valueOf(Pagina.getParamStr(request,"codIett"));
		String ultimoIdLinhaDetalhado = Pagina.getParamStr(request, "ultimoIdLinhaDetalhado");
		labelEttf = Pagina.getParamStr(request, "labelEttf");
		%>
<input type="hidden" name="codAba" value=<%=Pagina.getParamStr(request, "codAba")%>>
<!-- campo hidden para cada chave primaria da tabela --> 
<input type="hidden" name="cod" value=<%=Pagina.getParamStr(request, "cod")%>>
<input type="hidden" name="codIettuc" value=<%=Pagina.getParamStr(request, "codIettuc")%>>
<input type="hidden" name="codIett" value=<%=codIett%>>
<input type="hidden" name="ultimoIdLinhaDetalhado" value=<%=ultimoIdLinhaDetalhado%>>
<%}%>

<%
			String descricaoEvento = null;
			ConfiguracaoCfg configura = (ConfiguracaoCfg)session.getAttribute("configuracao");
			String strAcao = "";
			if(isFormUpload)
				strAcao = FileUpload.verificaValorCampo(campos, "hidAcao");
			else
				strAcao = Pagina.getParamStr(request, "hidAcao");
							
			/* obtem os caminhos fisicos para gravacao do arquivo a partir do arquivo de propriedades */
			//ecar.pojo.ConfiguracaoCfg configura = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();
			String pathRaiz = configura.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
			String pathAnexo = configura.getUploadAnexos(); //_msg.getMensagem("path .upload.anexos");
			
			ItemEstrutUploadIettup anexo = new ItemEstrutUploadIettup();
			if("incluir".equals(strAcao)){
				descricaoEvento = "Inclusão de " + labelEttf;
				anexoDao.setItemEstruturaUpload(campos, anexo, pathRaiz, pathAnexo);
                
				anexoDao.salvar(anexo, ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario());
				submit = "lista.jsp";
				msg = _msg.getMensagem("itemEstrutura.anexo.inclusao.sucesso");
			}	
			if("alterar".equals(strAcao)){
				descricaoEvento = "Alteração de " +labelEttf;
				anexo = (ItemEstrutUploadIettup) anexoDao.buscar(ItemEstrutUploadIettup.class, Long.valueOf(FileUpload.verificaValorCampo(campos, "cod")));	
				anexoDao.setItemEstruturaUpload(campos, anexo, pathRaiz, pathAnexo);
				anexoDao.alterar(anexo, ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario() );
				submit = "frm_con.jsp";
				msg = _msg.getMensagem("itemEstrutura.anexo.alteracao.sucesso");
			}
			if("excluir".equals(strAcao)){
				descricaoEvento = "Exclusão de " + labelEttf;
				String codigosParaExcluir[] = request.getParameterValues("excluir");
				anexoDao.excluir(codigosParaExcluir, pathRaiz, ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario(),false);
				submit = "lista.jsp";
				msg = _msg.getMensagem("itemEstrutura.anexo.exclusao.sucesso");				
			}
			
			if( "S".equals(autorizarMail) ) {
				String link = null;	
				if("incluir".equals(strAcao) || "alterar".equals(strAcao)){
					String[] valores = new String[3];
				    //ConfiguracaoCfg configCfg = new ConfiguracaoDao(null).getConfiguracao();
				    String contextPath = configura.getContextPath();
					valores[0] = FileUpload.verificaValorCampo(campos,"codIett");				
					valores[1] = FileUpload.verificaValorCampo(campos,"codAba");
					valores[2] = FileUpload.verificaValorCampo(campos,"codIettuc");
					
					link = null;										
					Evento evento = new EventoManutencaoAnexo();									     
				    link = URLEvento.montaURLEventoSemRequest(evento, contextPath, valores);
				} else if("excluir".equals(strAcao)){
					link = null;										
					Evento evento = new EventoManutencaoAnexo();									     
				    link = URLEvento.montaURLEvento(evento, request);					
				}
				
				ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, Dominios.CFG_MAIL_MANUTENCAO_ANEXO);
			
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
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	if (msg != null)
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	%>

<script>
			document.form.action = "<%=submit%>";
			document.form.submit();
</script>

</form>
</body>
</html>