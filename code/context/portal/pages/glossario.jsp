<%@ include file="/frm_global.jsp"%>

<%@ page import="ecar.pojo.SegmentoCategoriaSgtc" %> 
<%@ page import="ecar.pojo.SegmentoItemSgti" %> 
<%@ page import="ecar.dao.SegmentoItemDao" %>
<%@ page import="ecar.pojo.UsuarioUsu" %>  

<%@ page import="comum.util.Data" %> 

<%@ page import="java.util.Iterator" %> 
<%@ page import="java.util.List" %> 
<%@ page import="java.util.Collections" %> 
<%@ page import="java.util.Comparator" %>
<%@ page import="java.lang.String" %>

<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>

<%@ page import="ecar.portal.CapaGlossario" %>
<%@ page import="comum.database.Dao" %>


<%
	ConfiguracaoCfg configuracao = (ConfiguracaoCfg)session.getAttribute("configuracao");
	String pathRaiz = configuracao.getRaizUpload();	

	String cod = Pagina.getParamStr(request, "codSgti");
	
	UsuarioUsu usuario = new UsuarioUsu();
	
	String acessoPublico = Pagina.getParamStr(request, "acessoPublico");
	String parametroAcessoPublico = ("true".equals(acessoPublico)) ? "&acessoPublico=true" : "";
	
	if(!"true".equals(acessoPublico))
		usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	CapaGlossario capa = new CapaGlossario(usuario,request);
	
	CapaGlossario capaGlossario = new CapaGlossario(usuario,request);
	
	Dao contador = new Dao();

%>
<script language="JavaScript" src="<%=request.getContextPath()%>/js/email.js"></script>

<script language="JavaScript">

function limpar() {		
	document.form.action="contato.jsp";
	document.form.submit();
}

function Habilitacao( codigoGlossario )
{
	var aux = codigoGlossario;
	var i = 0;
	var el = document.getElementsByTagName("TABLE");
	
		
	if(aux != "")
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
    
    /* return(true);*/
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
	window.open(url,"null");

}
</script>
<script type="text/javascript" src="/ecar/js/dtree.js"></script>


<%@page import="comum.util.Util"%><html lang="pt-br">
<head>
<%@ include file="/titulo.jsp"%>
<%@ include file="/include/meta.jsp"%>

<link rel="stylesheet" href="<%=_pathEcar%>/css/style_<%=configuracaoCfg.getEstilo().getNome()%>.css" type="text/css">
</head>
<body>
<%@ include file="/cabecalho.jsp" %>
	<% 
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
			<td><a href="index.jsp">Capa</a> > Glossário</td>
			<td align="right">&lt;&lt;<a href="<%=request.getContextPath()%>/portal/pages/index.jsp"> Voltar</a></td>
		</tr>
	</table>
	<%
	}
	%>
	<h1 class="interno">Glossário</h1>
		<table cellspacing="0" class="formulario">
		<tr>
		  <td class="form_grupo" valign="middle">
		  	<center>
		  	<table cellspacing="0">
		  		<tr>
<%
		
				String espacoBranco = "     ";
				SegmentoCategoriaSgtc categoriaGlossario = new SegmentoCategoriaSgtc();
				SegmentoItemDao itemDao1 = new SegmentoItemDao(request);
												
				java.lang.Long codigoGlossario;
				String tituloGlossario = "";
				
				List lCategoriaGlossario = capaGlossario.getListSegmentoCategorias();
				
				//Garante a ordenação da lista lCategoriaGlossario
				Collections.sort(lCategoriaGlossario, new Comparator() {
				     public int compare(Object o1, Object o2) {
				         SegmentoCategoriaSgtc obj1 = (SegmentoCategoriaSgtc) o1;
				         SegmentoCategoriaSgtc obj2 = (SegmentoCategoriaSgtc) o2;
				         return obj1.getTituloSgtc().compareTo(obj2.getTituloSgtc());
				     }
				 });

				
				Iterator itCategoriaGlossario;
	
				itCategoriaGlossario = lCategoriaGlossario.iterator();

				while (itCategoriaGlossario.hasNext()) {
		
					categoriaGlossario = (SegmentoCategoriaSgtc) itCategoriaGlossario.next();
					
					List lGloss = new ArrayList();
					
					if("true".equals(acessoPublico))
						lGloss = itemDao1.getListSegmentoItensGlossarioAcessoPublico(categoriaGlossario);
					else
						lGloss = itemDao1.getListSegmentoItensGlossarioVinculadosAoUsuario(categoriaGlossario,usuario);
					
					codigoGlossario = categoriaGlossario.getCodSgtc();
					tituloGlossario = categoriaGlossario.getTituloSgtc();

%>								
				 <td>
				 	
				 		<%
				 		if(lGloss != null && lGloss.size() > 0){ 
				 		%>
						<table class="abadesabilitada"><tr><td nowrap>
						
						<a href="glossario.jsp?tipo=<%=codigoGlossario.longValue()%><%=parametroAcessoPublico%>">
						<b><%=tituloGlossario%></b>
						</a>
						
						<% } else{ %>
						<table class="abahabilitada"><tr><td nowrap>
						<%=tituloGlossario%>
						<% } %>
						</td></tr></table>
				  	
				</td>
<%				}//while			
%>	
			  	</tr>
			</table></center>
		  </td>
		</tr>	
		
		</table>
		
<%

try {

	SegmentoCategoriaSgtc categoria = new SegmentoCategoriaSgtc();
	SegmentoItemSgti item = new SegmentoItemSgti();
	SegmentoItemDao itemDao = new SegmentoItemDao(request);
	java.lang.Long codItemGlossario;
	java.lang.Long codCategoria;	
	String titulo = "";
	String integra = "";
	String anexoLink = "";
	String anexoLegenda = "";
	String anexo = "";
	
	List lCategoria = capa.getListSegmentoCategorias();
		
	Iterator itItem;
	Iterator itCategoria;
	
	itCategoria = lCategoria.iterator();

		if ("".equals(cod)){
			
			categoria.setCodSgtc(Long.valueOf(parametro));
			codCategoria = categoria.getCodSgtc();
						
			List lItem = new ArrayList();
			
			if("true".equals(acessoPublico))
				lItem = itemDao.getListSegmentoItensGlossarioAcessoPublico(categoria);
			else
				lItem = itemDao.getListSegmentoItensGlossarioVinculadosAoUsuario(categoria,usuario);			
				
			itItem = lItem.iterator();
%>
	<table cellspacing="0" class="formulario">
	<tr>
		  <td class="form_label">
		  	
		  	 						
			<table class="formulario" cellspacing="0" id="categoria_<%=codCategoria.longValue()%>">
			
			
<%			while (itItem.hasNext()) {
		
				item = (SegmentoItemSgti) itItem.next();
			
				codItemGlossario = item.getCodSgti();
				titulo = item.getTituloSgti();
				integra = item.getIntegraSgti();
				anexoLegenda = item.getLegendaImag1Sgti();
				anexo = item.getImagem1Stgi();
					
				String hashNomeArquivo = null;
				UsuarioUsu usuarioImagem = null; 
%>
				<tr>
				<td width="20%"></td>
				<td>
					<table cellspacing="2" id="teste_<%=codItemGlossario.longValue()%>">
					<tr>
					<td class="form_label"><a href="javascript:MostraLinha('<%=codItemGlossario.longValue()%>')" title='Mostrar/esconder resposta' >
					<%=titulo%>			
					</a></td>
					</tr>
					</table>
				</td>
				</tr>		
						
				<tr>
				<td width="20%"></td>
				<td>
				<table cellspacing="2" id="linha_<%=codItemGlossario.longValue()%>" style="display:none">			
					
					<tr>
						<td class="form_label"><%=integra%></td>						
					</tr>					
										
					<%	if(anexo != null){	
							
							hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, anexo);
							usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
							Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, anexo);
					%>
					<tr>
						
						<td class="form_label">						
						<a href="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=pathRaiz + anexo %>">
							
							<%	if(anexoLegenda != null){	%>
							<%=anexoLegenda%>
							<%	} else{ %>
							Clique aqui para ver/salvar o arquivo anexo</a>
							<%	} %>
						</a>	
						</td>					
					</tr>
					<%	}//if %>
														
				</table>
				</td></tr>
												
<%			}//while 
%>			</table>
		</table>			
				
<%		} else{
%>
		<table cellspacing="0" class="formulario">
		
<%						
				SegmentoItemSgti itemCapa = new SegmentoItemSgti();
				SegmentoItemDao segItemDao = new SegmentoItemDao(request);	
				itemCapa = segItemDao.getSegmentoItemCodSgti(Long.parseLong(cod));				
				
				java.lang.Long codItemCapa;	
				String tituloCapa = "";
				String integraCapa = "";
				String anexoLinkCapa = "";
				String anexoLegendaCapa = "";
				String anexoCapa = "";
							
				codItemCapa = itemCapa.getCodSgti();
				tituloCapa = itemCapa.getTituloSgti();
				integraCapa = itemCapa.getIntegraSgti();
				anexoLegendaCapa = itemCapa.getLegendaImag1Sgti();
				anexoCapa = itemCapa.getImagem1Stgi();		
%>			
				<tr>
				<td width="20%"></td>
				<td>
					<table cellspacing="2" id="teste_<%=codItemCapa.longValue()%>">
					<tr>
					<td class="form_label"><a href="javascript:MostraLinha('<%=codItemCapa.longValue()%>')" title='Mostrar/esconder resposta' >
					<%=tituloCapa%>			
					</a></td>
					</tr>
					</table>
				</td>
				</tr>		
						
				<tr>
				<td width="20%"></td>
				<td>
				<table cellspacing="2" id="linha_<%=codItemCapa.longValue()%>" style="display:none">			
					
					<tr>
						<td class="form_label"><%=integraCapa%></td>						
					</tr>					
										
					<%	if(anexoCapa != null){	%>
					<tr>
						
						<td class="form_label">						
						<a href="<%=request.getContextPath()%>/DownloadFile?RemoteFile=<%=pathRaiz + anexoCapa %>">
							
							<%	if(anexoLegendaCapa != null){	%>
							<%=anexoLegendaCapa%>
							<%	} else{ %>
							Clique aqui para ver/salvar o arquivo anexo</a>
							<%	} %>
						</a>	
						</td>					
					</tr>
					<%	}//if %>
														
				</table>
				</td></tr>
			
			<!--	<tr>
					<!-- espaço em branco para ajuste da largura da tabela -->
					<!--<td width="80%"><%//espacoBranco%></td>					
				</tr>-->
			
			</table>
<%		}//else
	
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

