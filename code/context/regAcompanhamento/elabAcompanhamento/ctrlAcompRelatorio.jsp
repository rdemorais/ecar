
<jsp:directive.page import="ecar.util.Dominios"/>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.dao.AcompRelatorioDao" %> 
<%@ page import="ecar.pojo.AcompRelatorioArel" %> 
<%@ page import="ecar.dao.AcompReferenciaItemDao" %>
<%@ page import="ecar.pojo.AcompReferenciaItemAri" %>
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
<%@ page import="comum.util.FileUpload"%>
<%@ page import="ecar.login.SegurancaECAR" %>

<html lang="pt-br">
<head>
<%@ include file="../../include/meta.jsp"%>
</head>   
<body>
<form name="form" method="post">  
<%
	String codAri = null;
	String codArel = null;
	String codTpfa = null;
	String funcao = null;
	String funcaoParecer = null;
	String codAcomp = null;
	String primeiroAcomp = null;
	String autorizarMail = null;
	
	Long codIett = null;
	
	boolean isFormUpload = FileUpload.isMultipartContent(request);
	
	List campos = new ArrayList();
	if(isFormUpload){
		campos = FileUpload.criaListaCampos(request); 
	 }
	if(isFormUpload){
		codAri = FileUpload.verificaValorCampo(campos,"codAri");
		codArel = FileUpload.verificaValorCampo(campos, "codArel");
		codTpfa = FileUpload.verificaValorCampo(campos, "codTpfa");
		funcao = FileUpload.verificaValorCampo(campos, "funcao");
		funcaoParecer = FileUpload.verificaValorCampo(campos, "funcaoParecer");
		codAcomp = FileUpload.verificaValorCampo(campos, "codAcomp");
		primeiroAcomp = FileUpload.verificaValorCampo(campos, "primeiroAcomp");
		autorizarMail = FileUpload.verificaValorCampo(campos, "autorizarMail");
	} else {
		codAri = Pagina.getParamStr(request,"codAri");
		codArel = Pagina.getParamStr(request, "codArel");
		codTpfa = Pagina.getParamStr(request, "codTpfa");
		funcao = Pagina.getParamStr(request, "funcao");
		funcaoParecer = Pagina.getParamStr(request, "funcaoParecer");
		codAcomp = Pagina.getParamStr(request, "codAcomp");
		primeiroAcomp = Pagina.getParamStr(request, "primeiroAcomp");
		autorizarMail = Pagina.getParamStr(request, "autorizarMail");
	}
	 
	AcompRelatorioDao acompRelatorioDao = new AcompRelatorioDao(request);
	AcompReferenciaItemDao ariDAO = new AcompReferenciaItemDao(request);
	AcompReferenciaItemAri ari = (AcompReferenciaItemAri)  ariDAO.buscar(AcompReferenciaItemAri.class, Long.valueOf(codAri));
	ConfigMailCfgmDAO configMailCfgmDAO = new ConfigMailCfgmDAO();
	TfuncacompConfigmailTfacfgmDAO tfuncacompConfigmailTfacfgmDAO = new TfuncacompConfigmailTfacfgmDAO();
	UsuarioDao usuDAO = new UsuarioDao();
	EmpresaDao empDAO = new EmpresaDao(request);
	
	
	if( ari.getItemEstruturaIett() != null ){
		codIett = ari.getItemEstruturaIett().getCodIett();
	}
	Dao dao = new Dao();

%>
<!-- campo de controle para mensagens -->
<input type="hidden" name="msgOperacao" value=""> 
<input type="hidden" name="msgConfirm" value=""> 
<input type="hidden" name="codArel" value="<%=codArel%>"> 
<input type="hidden" name="codAri" value="<%=codAri%>">
<input type="hidden" name="codTpfa" value="<%=codTpfa%>">
<input type="hidden" name="funcao" value="<%=funcao%>">
<input type="hidden" name="funcaoParecer" value="<%=funcaoParecer%>">
<input type="hidden" name="codAcomp" value="<%=codAcomp%>">
<input type="hidden" name="primeiroAcomp" value="<%=primeiroAcomp%>">

<%
	UsuarioUsu usuarioLogado = ((SegurancaECAR)session.getAttribute("seguranca")).getUsuario();

	String msg = "";
	String submit = "";
	boolean salvar = false; 
	boolean alterar = false; 
	String descricaoEvento = null;
	boolean liberar = false;	
	Long codAri2 = null;
	boolean podeLiberarAcomp = false;
	
	try{
	   AcompRelatorioArel acompRelatorio = new AcompRelatorioArel();
	   if(!"".equals(codArel)){
            acompRelatorio = (AcompRelatorioArel) acompRelatorioDao.buscar(AcompRelatorioArel.class, 
                    										  Long.valueOf(codArel));            

			codAri2 = acompRelatorio.getAcompReferenciaItemAri().getCodAri();
            alterar = true;
            if ("S".equals(acompRelatorio.getIndLiberadoArel())){
	            liberar = false;
            } else {
				liberar = true;            
            }
       } else {
       		salvar = true; 
       }
		if(salvar){
			descricaoEvento = "Adicionado um novo Parecer de Avaliação.";
			msg = _msg.getMensagem(acompRelatorioDao.salvar(acompRelatorio, request, campos));
			submit = "acompRelatorio.jsp";
		}
		if(alterar){
			descricaoEvento = "Parecer de Avaliação alterado.";
			msg = _msg.getMensagem(acompRelatorioDao.alterar(acompRelatorio, request, campos));
			submit = "acompRelatorio.jsp";
			
			if (liberar){
				podeLiberarAcomp = true;
			
				AcompReferenciaItemAri acompRefItemAri = new AcompReferenciaItemAri();
				
				acompRefItemAri = (AcompReferenciaItemAri)ariDAO.buscar(AcompReferenciaItemAri.class,codAri2);
				
	   			List lista = new ArrayList(acompRefItemAri.getAcompRelatorioArels());
				Iterator itLista = lista.iterator();
				 	
				while(itLista.hasNext() && podeLiberarAcomp) {
					AcompRelatorioArel acompRelatorioArel = (AcompRelatorioArel) itLista.next();
					if (!"S".equals(acompRelatorioArel.getIndLiberadoArel())){
						podeLiberarAcomp = false;
					}
				}
			}
			
			if (podeLiberarAcomp){
				msg = msg + ". Deseja liberar acompanhamento?";
			}
			
			if( "S".equals(autorizarMail) ) {
								
					ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) configMailCfgmDAO.buscar(ConfigMailCfgm.class, ((liberar == true) ? Dominios.CFG_MAIL_LIBERACAO_PARECER : Dominios.CFG_MAIL_RECUPERACAO_PARECER));
				
					ItemEstruturaIett item = (ItemEstruturaIett) dao.buscar(ItemEstruturaIett.class, codIett);	
					List listIett = new ArrayList(item.getItemEstUsutpfuacIettutfas());
					Iterator itListIett = listIett.iterator();
				 
					// data limite para ...
					String dataLimitePara = "";
					String dataLimiteParaAnt = "";				
					
					//FIXME: enviar e-mail somente para usuário que possui AREL no acompanhamento
					/*
					List listUsuariosComArel = new ArrayList();
					Iterator itArels = acompRelatorio.getAcompReferenciaItemAri().getAcompRelatorioArels().iterator();
					while(itArels.hasNext()) {
						AcompRelatorioArel arel = (AcompRelatorioArel)itArels.next();
						if(!listUsuariosComArel.contains(arel.getUsuarioUsu())) {
							listUsuariosComArel.add(arel.getUsuarioUsu());
						}
						if(!listUsuariosComArel.contains(arel.getUsuarioUsuUltimaManutencao())) {
							listUsuariosComArel.add(arel.getUsuarioUsuUltimaManutencao());
						}
					}
					*/

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
							
							
								// controle para não enviar e-mail para o usuário logado
								if(usu.equals(usuarioLogado)) {
									continue;
								}
								
								//FIXME: enviar e-mail somente para usuário que possui AREL no acompanhamento
								/*
								if(!listUsuariosComArel.contains(usu)) {
									continue;
								}
								*/
							
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
								
								html = ae.montaEmail(textoMail, usu.getNomeUsu(), usuarioLogado.getNomeUsuSent(), codIett, 
										descricaoEvento, null, null, 
										AgendadorEmail.LABEL_WHO_CHANGE_ALTERACAO,
										acompRelatorio.getAcompReferenciaItemAri().getAcompReferenciaAref().getTipoAcompanhamentoTa().getDescricaoTa()).toString();
																							
								ae.enviarEmail(assunto, remetente, html, usu.getEmail1UsuSent(), "", "",usu);
							}
					}
				}
			}
		}
		
	}
	catch ( ECARException e){
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		submit = "acompRelatorio.jsp";
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	if (podeLiberarAcomp){
		if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgConfirm.value", msg); 	
	} else {
		if (msg != null)
			Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
	}
	%>

<script>
			document.form.action = "<%=submit%>";
			document.form.submit();
</script>

</form>
</body>
</html>

