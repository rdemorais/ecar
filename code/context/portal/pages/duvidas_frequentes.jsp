<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.SegmentoCategoriaSgtc" %> 
<%@ page import="ecar.pojo.SegmentoItemSgti" %> 
<%@ page import="ecar.dao.SegmentoItemDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>  

<%@ page import="comum.util.Data" %> 

<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %> 

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.portal.CapaDuvidas" %>

<%
	String cod = Pagina.getParamStr(request, "codSgti");
	
	UsuarioUsu usuario = new UsuarioUsu();
	
	String acessoPublico = Pagina.getParamStr(request, "acessoPublico");
	String parametroAcessoPublico = ("true".equals(acessoPublico)) ? "acessoPublico=true&" : "";
	
	if(!"true".equals(acessoPublico))
		usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();

	CapaDuvidas capaDuvidas = null;
	
	if("true".equals(acessoPublico))
		capaDuvidas = new CapaDuvidas(request);
	else
		capaDuvidas = new CapaDuvidas(usuario,request);
%>
<script language="JavaScript" src="<%=request.getContextPath()%>/js/email.js"></script>

<script language="JavaScript">

function limpar() {		
	document.form.action="contato.jsp";
	document.form.submit();
}

function Habilitacao( form )
{
	var aux = form.segmentoCategoria.value;
	var i = 0;
	var el = document.getElementsByTagName("TABLE");
	
		
	if(form.segmentoCategoria.value != "")
	{
		while (i <= el.length - 1){
            if (el.item(i).id.substr(0,10) == "categoria_") { 
                el.item(i).style.display = 'none';
            } // if
            i++;  
     	}// While
     	
		document.getElementById('categoria' + '_' + aux).style.display  = '';
		
	}
	
	else
	{
		
		while (i <= el.length - 1){
            if (el.item(i).id.substr(0,10) == "categoria_") { 
                el.item(i).style.display = 'none';
            } // if
            i++;  
     	}// While
	}
    form.segmentoCategoria.value = "";
    return(true);
}

function MostraLinha( parmCodigo ) {

  if (document.getElementById('linha' + '_' + parmCodigo).style.display=='none') 
  {
     document.getElementById('linha' + '_' + parmCodigo).style.display='';
  }
  else 
  {
     document.getElementById('linha' + '_' + parmCodigo).style.display='none';
  }

}

function MostraLink( url )
{
	var i = 0;
		
	if ((url.substr(0,7) != "http://") && (url.substr(0,6) != "ftp://")) { 
    	url = 'http://' + url;
    }
    
	window.open(url,"null");

}

function MostraDuvidas( form )
{
	var codigo = form.segmentoCategoria.value;
	window.location.href = "duvidas_frequentes.jsp?<%=parametroAcessoPublico%>tipo=" + codigo;

}


</script>



<%@page import="comum.util.Util"%><html lang="pt-br">
<head>
<%@ include file="/titulo.jsp"%>
<%@ include file="/include/meta.jsp"%>
<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
</head>
<body>
<%@ include file="/cabecalho.jsp" %>
	<% 
	if("true".equals(acessoPublico)){
		// atributo na sessão para utilizar o DownloadFile sem logar
		request.getSession().setAttribute("downloadDeslogado", new Boolean(true));
	}
		String parametro = "0";
		
		if(request.getQueryString() != null && "".equals(cod) && request.getParameter("tipo") != null){
			parametro = request.getParameter("tipo");			
		} 		
		
	%>
<form name="form" id="form" method="post" action="ctrl_contato.jsp" onsubmit="javascript:return validar();">
	<%
	if(!"true".equals(acessoPublico)){
	%>
	<table id="barra_localizacao" cellspacing="0">
		<tr>
			<td><a href="index.jsp">Capa</a> > Dúvidas Freqüentes </td>
			<td align="right">&lt;&lt;<a href="<%=request.getContextPath()%>/portal/pages/index.jsp"> Voltar</a></td>
		</tr>
	</table>
	<%
	}
	%>
	<h1 class="interno">Dúvidas Freqüentes</h1>
		<table cellspacing="0" class="formulario">
		<tr>
		  <td class="form_grupo">
		  	<table cellspacing="0">
				
				<tr>
				  <td class="form_label">Categoria</td>
				  <td class="form_campo">
						<combo:ComboTag 
								nome="segmentoCategoria"
								objeto="ecar.pojo.SegmentoCategoriaSgtc" 
								label="tituloSgtc" 
								value="codSgtc" 
								order="tituloSgtc"
								colecao="<%=capaDuvidas.getListSegmentoCategorias()%>"								
								textoPadrao="-- Selecione uma categoria --"
								scripts="OnChange='MostraDuvidas(this.form)'"
						/>		
				  </td>
			  	</tr>
			</table>
		  </td>
		</tr>	
		
		<tr>
			
		  <td>
		  	<table cellspacing="0">
		
<%

try {
	//ecar.pojo.ConfiguracaoCfg configuracao = new ecar.dao.ConfiguracaoDao(request).getConfiguracao();
	String pathRaiz = configuracao.getRaizUpload();	
	
	SegmentoCategoriaSgtc categoria = new SegmentoCategoriaSgtc();
	SegmentoCategoriaSgtc categoriaAux = new SegmentoCategoriaSgtc();
	SegmentoItemSgti item = new SegmentoItemSgti();
	SegmentoItemDao itemDao = new SegmentoItemDao(request);
	java.lang.Long codDuvida;
	java.lang.Long codCategoria;
	String nomeCategoria = "";	
	String titulo = "";
	String integra = "";
	String anexoLink = "";
	String anexoLegenda = "";
	String anexo = "";
	String Aux = "";
	
	List lCategoria = capaDuvidas.getListSegmentoCategorias();
		
	Iterator itItem;
	Iterator itCategoria;
	
	itCategoria = lCategoria.iterator();
	
	int cont = 0;
	int contNome = 0;
	String cor = new String();
		
		
		if ("".equals(cod)){
		
			while (itCategoria.hasNext()) {
				categoriaAux = (SegmentoCategoriaSgtc) itCategoria.next();
				Aux = categoriaAux.getCodSgtc().toString();
				if (Aux.equalsIgnoreCase(parametro)){
					nomeCategoria = categoriaAux.getTituloSgtc();	
				}
			}

			categoria.setCodSgtc(Long.valueOf(parametro));
			codCategoria = categoria.getCodSgtc();
			
						
			List lItem = new ArrayList();
			
			if("true".equals(acessoPublico))
				lItem = itemDao.getListSegmentoItensAcessoPublico(categoria);
			else
				lItem = itemDao.getListSegmentoItensVinculadosAoUsuario(categoria,usuario);	
			itItem = lItem.iterator();
%>
					
			<table class="formulario" cellspacing="0" id="categoria_<%=codCategoria%>">
			
			
<%			while (itItem.hasNext()) {
		
				item = (SegmentoItemSgti) itItem.next();
			
				codDuvida = item.getCodSgti();
				titulo = item.getTituloSgti();
				integra = item.getIntegraSgti();
				anexoLink = item.getUrlLinkSgti();
				anexoLegenda = item.getAnexoLegendaSgti();
				anexo = item.getAnexoEnderecoSgti();
												
				if (cont % 2 == 0){
					cor = "destaque1";
				} else {
					cor = "cor_nao"; 
				}	
				
				if (contNome == 0){		
%>
				<tr class="cor_nao">
				<td width="2%">
				</td>
				<td>
					<table cellspacing="2">
					<tr>
					<td class="form_label"><b><%=nomeCategoria%></b>
					</td></tr>
					</table>
				</td></tr>
				
<%				}//if
%>
				<tr class="<%=cor%>">
				<td width="2%">
				</td>
				<td>
					<table cellspacing="2" id="teste_<%=codDuvida.longValue()%>"><tr>
					<td width="20"></td>
					<td class="form_label"><a href="javascript:MostraLinha('<%=codDuvida.longValue()%>')" title='Mostrar/esconder resposta' >
					<%=titulo%>			
					</a></td></tr></table>
				</td></tr>		
						
				<tr class="<%=cor%>">
				<td width="2%">
				</td>				
				<td>
				<table cellspacing="2" id="linha_<%=codDuvida.longValue()%>" style="display:none">			
					
					<tr>
						<td width="20"></td><td class="form_label"><%=integra%></td>						
					</tr>
										
					<%
					
					String hashNomeArquivo = null;
					UsuarioUsu usuarioImagem = null;  
					
					if(anexo != null){
						
						hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, anexo);
						usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
						Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, anexo);
					%>
					<tr>
						<td width="20"></td>
						<td class="form_label">						
						<a href="<%=request.getContextPath()%>/DownloadFile?<%=parametroAcessoPublico%>RemoteFile=<%=hashNomeArquivo%>">
							
							<%	if(anexoLegenda != null){	%>
							<%=anexoLegenda%>
							<%	} else{ %>
							Clique aqui para ver/salvar o arquivo anexo</a>
							<%	} %>
						</a>	
						</td>					
					</tr>
					<%	}//if %>
					
					<%	if(anexoLink != null){	%>
					<tr>
						<td width="20"></td>	
						<td class="form_label">Veja também:
						<a href="javascript:MostraLink('<%=anexoLink%>')" title='Abrir link'>
						<%=anexoLink%></a></td>						
						
					</tr>
					<%	}//if %>
									
				</table>
				</td></tr>
						
<%				
				cont++;
				contNome++;
			}
%>
			</table>			
				
<%		cont = 0;
		contNome = 0;		
		
		} else {				

			SegmentoItemSgti itemCapa = new SegmentoItemSgti();
			SegmentoItemDao segItemDao = new SegmentoItemDao(request);	
			itemCapa = segItemDao.getSegmentoItemCodSgti(Long.parseLong(cod));
			
			java.lang.Long codDuvidaCapa;
			java.lang.Long codCategoriaCapa;
			String nomeCategoriaCapa = "";	
			String tituloCapa = "";
			String integraCapa = "";
			String anexoLinkCapa = "";
			String anexoLegendaCapa = "";
			String anexoCapa = "";
			
			codDuvidaCapa = itemCapa.getCodSgti();
			tituloCapa = itemCapa.getTituloSgti();
			integraCapa = itemCapa.getIntegraSgti();
			anexoLinkCapa = itemCapa.getUrlLinkSgti();
			anexoLegendaCapa = itemCapa.getAnexoLegendaSgti();
			anexoCapa = itemCapa.getAnexoEnderecoSgti();
			codCategoriaCapa = itemCapa.getSegmentoCategoriaSgtc().getCodSgtc();
			nomeCategoriaCapa = itemCapa.getSegmentoCategoriaSgtc().getDescricaoSgtc();
%>
		<table class="formulario" cellspacing="0" id="categoria_<%=codCategoriaCapa%>">
				<tr>
				<td width="2%">
				</td>
				<td>
					<table cellspacing="2">
					<tr>
					<td class="form_label"><b><%=nomeCategoriaCapa%></b>
					</td></tr>
					</table>
				</td></tr>

				<tr class="destaque1">
				<td width="2%">
				</td>
				<td>
					<table cellspacing="2" id="teste_<%=codDuvidaCapa.longValue()%>"><tr>
					<td width="20"></td>
					<td class="form_label"><a href="javascript:MostraLinha('<%=codDuvidaCapa.longValue()%>')" title='Mostrar/esconder resposta' >
					<%=tituloCapa%>			
					</a></td></tr></table>
				</td></tr>		
						
				<tr class="<%=cor%>">
				<td width="2%">
				</td>				
				<td>
				<table cellspacing="2" id="linha_<%=codDuvidaCapa.longValue()%>" style="display:none">			
					
					<tr>
						<td width="20"></td><td class="form_label"><%=integraCapa%></td>						
					</tr>
										
					<%	
						String hashNomeArquivo = null;
						UsuarioUsu usuarioImagem = null;  
					
						if(anexoCapa != null){
						
							hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, anexoCapa);
							usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
							Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, anexoCapa);
						%>
					<tr>
						<td width="20"></td>
						<td class="form_label">						
						<a href="<%=request.getContextPath()%>/DownloadFile?<%=parametroAcessoPublico%>RemoteFile=<%=hashNomeArquivo%>">
							
							<%	if(anexoLegendaCapa != null){	%>
							<%=anexoLegendaCapa%>
							<%	} else{ %>
							Clique aqui para ver/salvar o arquivo anexo</a>
							<%	} %>
						</a>	
						</td>					
					</tr>
					<%	}//if %>
					
					<%	if(anexoLinkCapa != null){	%>
					<tr>
						<td width="20"></td>	
						<td class="form_label">Veja também:
						<a href="javascript:MostraLink('<%=anexoLinkCapa%>')" title='Abrir link'>
						<%=anexoLinkCapa%></a></td>						
						
					</tr>
					<%	}//if %>
									
				</table>
				</td></tr>

<%
		}
		
	} catch (ECARException e) {
		org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
		Mensagem.alert(_jspx_page_context, e.getMessageKey());
	} catch (Exception e){
	%>
		<%@ include file="/excecao.jsp"%>
	<%
	}
%>
			</table>
		  </td>
		</tr>
	
	</table>
	<hr>
</form>
</body>
<%@ include file="/include/mensagemAlert.jsp"%>
</html>