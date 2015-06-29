<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>

<%@ page import="ecar.pojo.EmpresaEmp" %>
<%@ page import="ecar.dao.EmpresaDao" %>
<%@ page import="ecar.pojo.Uf" %>
<%@ page import="ecar.dao.UfDao" %>

<%@ page import="comum.util.FileUpload"%>

<%@ page import="java.io.File" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList"%>

<%@ page import="org.apache.commons.fileupload.FileItem"%>

<html lang="pt-br">
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
</form>

<%
	EmpresaEmp empresa = new EmpresaEmp();
	EmpresaDao empresaDao = new EmpresaDao(request);
	Uf uf = new Uf();
	UfDao ufDao = new UfDao(request);
	Mensagem mensagem = new Mensagem(application);
	
	List campos = new ArrayList();
	campos = FileUpload.criaListaCampos(request); 
	
	String hidAcao = FileUpload.verificaValorCampo(campos, "hidAcao");
	String msg = null;
	String submit = "frm_inc.jsp";
	
	/* obtem os caminhos fisicos para gravacao do arquivo a partir do arquivo de propriedades */
	String pathRaiz = configuracaoCfg.getRaizUpload();//_msg.getPathUploadRaiz("path .upload.raiz");
	String pathEmpresa = configuracaoCfg.getUploadEmpresa();//_msg.getMensagem("path .upload.empresa");
		
	try{
		List confg = empresaDao.listar(EmpresaEmp.class, null);
		boolean incluir = true;
		if(confg != null && confg.size() > 0){
			empresa = (EmpresaEmp) confg.iterator().next();
			incluir = false;
		}
		
		empresa.setRazaoSocialEmp(FileUpload.verificaValorCampo(campos, "razaoSocialEmp").trim()); 
		empresa.setSiglaEmp(FileUpload.verificaValorCampo(campos, "siglaEmp").trim());
		empresa.setCnpjCpfEmp(FileUpload.verificaValorCampo(campos, "cnpjCpfEmp").trim());
		empresa.setInscrEstadualEmp(FileUpload.verificaValorCampo(campos, "inscrEstadualEmp").trim());
		empresa.setInscrMunicipalEmp(FileUpload.verificaValorCampo(campos, "inscrMunicipalEmp").trim());
		empresa.setEnderecoEmp(FileUpload.verificaValorCampo(campos, "enderecoEmp").trim());
		empresa.setComplementoEmp(FileUpload.verificaValorCampo(campos, "complementoEmp").trim());
		empresa.setBairroEmp(FileUpload.verificaValorCampo(campos, "bairroEmp").trim());
		empresa.setCidadeEmp(FileUpload.verificaValorCampo(campos, "cidadeEmp").trim());
		empresa.setCepEmp(FileUpload.verificaValorCampo(campos, "cepEmp").trim());
		
		if(FileUpload.verificaValorCampo(campos, "uf") != null && !"".equals(FileUpload.verificaValorCampo(campos, "uf")))
			empresa.setUf( (Uf) ufDao.buscar(Uf.class, (FileUpload.verificaValorCampo(campos, "uf").toString())));
			
		empresa.setDdd1Emp(FileUpload.verificaValorCampo(campos, "ddd1Emp").trim());
		empresa.setTelefone1Emp(FileUpload.verificaValorCampo(campos, "telefone1Emp").trim());
		empresa.setDdd2Emp(FileUpload.verificaValorCampo(campos, "ddd2Emp").trim());
		empresa.setTelefone2Emp(FileUpload.verificaValorCampo(campos, "telefone2Emp").trim());
		empresa.setDddFaxEmp(FileUpload.verificaValorCampo(campos, "dddFaxEmp").trim());
		empresa.setFaxEmp(FileUpload.verificaValorCampo(campos, "faxEmp").trim());
		empresa.setEmailContatoEmp(FileUpload.verificaValorCampo(campos, "emailContatoEmp").trim());
		empresa.setEmailErrosEmp(FileUpload.verificaValorCampo(campos, "emailErrosEmp").trim());
		empresa.setHomePageEmp(FileUpload.verificaValorCampo(campos, "homePageEmp").trim());
		
		submit = "frm_inc.jsp";
		
		if (empresa.getLogotipoEmp() != null && !"".equals(empresa.getLogotipoEmp()) 
				&& "_excluir".equals(FileUpload.verificaValorCampo(campos, "hidlogotipoEmp"))){
					
			try {
	        	
	        	FileUpload.apagarArquivo(pathRaiz + pathEmpresa + "/" + FileUpload.getNomeArquivo(empresa.getLogotipoEmp()));		        	
	        	empresa.setLogotipoEmp("");
            }catch (Exception e) {
            	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			}
		} 
		if (empresa.getLogotipoEmailEmp() != null && !"".equals(empresa.getLogotipoEmailEmp()) 
				&& "_excluir".equals(FileUpload.verificaValorCampo(campos, "hidlogotipoEmailEmp"))){
		
			try {
	        	FileUpload.apagarArquivo(pathRaiz + pathEmpresa + "/" + FileUpload.getNomeArquivo(empresa.getLogotipoEmailEmp()));
	        	empresa.setLogotipoEmailEmp("");
            }catch (Exception e) {
            	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			}
			
		}
		if (empresa.getLogotipoRelatorioEmp() != null && !"".equals(empresa.getLogotipoRelatorioEmp()) 
				&& "_excluir".equals(FileUpload.verificaValorCampo(campos, "hidlogotipoRelatorioEmp"))){
		
			try {
				FileUpload.apagarArquivo(pathRaiz + pathEmpresa + "/" + FileUpload.getNomeArquivo(empresa.getLogotipoRelatorioEmp()));
	        	empresa.setLogotipoRelatorioEmp("");
            }catch (Exception e) {
            	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
			}
			
		}		
		
		/* realiza o upload do arquivo */
        Iterator it = campos.iterator();  
        while(it.hasNext()){
            FileItem fileItem = (FileItem) it.next();
			
            if(!fileItem.isFormField() && !"".equals(fileItem.getName())){    
				
                if("logotipoEmp".equals(fileItem.getFieldName())){
                	String status = FileUpload.verificaValorCampo(campos, "hidlogotipoEmp").trim();
                	if(empresa.getLogotipoEmp() != null){
				    	try {
		                    FileUpload.apagarArquivo(empresa.getLogotipoEmp());
		                    if(status.equals("_excluir"))
		                    	empresa.setLogotipoEmp("");
	                    } catch (Exception e) {
	                    	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
	                    	//msg = _msg.getMensagem("empresa.logotipoEmp.exclusao.erro" );
						}
	                } 
                	 
                	if (!"".equals(fileItem.getName())){
                		File arquivoGravado = FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisico(pathRaiz, pathEmpresa, FileUpload.getNomeArquivo(fileItem.getName())));
    	                empresa.setLogotipoEmp(FileUpload.getPathFisico("", pathEmpresa, FileUpload.getNomeArquivo(arquivoGravado.getName())));
                	}
                	
				}
                
                if("logotipoEmailEmp".equals(fileItem.getFieldName())){
				    String status = FileUpload.verificaValorCampo(campos, "hidlogotipoEmailEmp").trim();
                	if(empresa.getLogotipoEmailEmp() != null){
				    	try {
		                    FileUpload.apagarArquivo(empresa.getLogotipoEmailEmp());
		                    if(status.equals("_excluir"))
		                    	empresa.setLogotipoEmailEmp("");
		                }catch (Exception e) {
		                	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		                	//msg = _msg.getMensagem("empresa.logotipoEmailEmp.exclusao.erro" );
						}
	                }
                	
				    if (!"".equals(fileItem.getName())){
	                	File arquivoGravado = FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisico(pathRaiz, pathEmpresa, FileUpload.getNomeArquivo(FileUpload.getNomeArquivo(fileItem.getName()))));
		                empresa.setLogotipoEmailEmp(FileUpload.getPathFisico("", pathEmpresa, FileUpload.getNomeArquivo(arquivoGravado.getName())));
				    }
				}
				
				if("logotipoRelatorioEmp".equals(fileItem.getFieldName())){
				    String status = FileUpload.verificaValorCampo(campos, "hidlogotipoRelatorioEmp").trim();
					if(empresa.getLogotipoRelatorioEmp() != null){
				    	try {
		                    FileUpload.apagarArquivo(empresa.getLogotipoRelatorioEmp());
		                    if(status.equals("_excluir"))
		                    	empresa.setLogotipoRelatorioEmp("");
		                }catch (Exception e) {
		                	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
						}
	                }
				    
				    if (!"".equals(fileItem.getName())){
	                	File arquivoGravado = FileUpload.salvarNoDisco(fileItem, FileUpload.getPathFisico(pathRaiz, pathEmpresa, FileUpload.getNomeArquivo(FileUpload.getNomeArquivo(fileItem.getName()))));
		                empresa.setLogotipoRelatorioEmp(FileUpload.getPathFisico("", pathEmpresa, FileUpload.getNomeArquivo(arquivoGravado.getName())));
				    }
				}
            }
        }
        
		if(incluir){						
			empresaDao.salvar(empresa);
			msg = _msg.getMensagem("empresa.inclusao.sucesso" );
		} else {	
			empresaDao.alterar(empresa);
			msg = _msg.getMensagem("empresa.alteracao.sucesso" );		
		}
		
	}catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		msg = _msg.getMensagem(e.getMessageKey());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
	
	/* da o alert para incluir, alterar, pesquisar ou excluir */
	if (msg != null) 
		Mensagem.setInput(_jspx_page_context, "document.form.msgOperacao.value", msg); 
%>
	<script language="javascript">
		document.form.action = "<%=submit%>";
		document.form.submit();
	</script> 
</body>
</html>
