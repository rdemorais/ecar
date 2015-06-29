<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@ include file="../../include/meta.jsp"%>
<html lang="pt-br">
<head>
<link rel="stylesheet" href="<c:out value="${pageContext.request.contextPath}"/>/css/style_<c:out value="${sessionScope.configuracao.estilo.nome}"/>.css" type="text/css">
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="/titulo.jsp"%>
<style type="text/css">
	a:link{
		text-decoration: none;
	}
	a {
		outline : none;
	}
	a img {
		outline : none;
	}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.1.7.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.dataTables.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		 var oTable = $('#listaEtiquetasCadastradas').dataTable( {
		        "oLanguage": {
			 		"sLengthMenu": "Mostrar _MENU_ registros por p&aacute;gina",
					"sZeroRecords": "Nehum registro encontrado.",
					"sInfo": "Mostrando _START_ a _END_ de _TOTAL_ registros",
					"sInfoEmpty": "Mostrando 0 a 0 de 0 registros",
					"sInfoFiltered": "(filtrado de _MAX_ registros totais)",
					"sSearch": "Filtrar",
					"oPaginate": {
						 "sNext": "Pr&oacute;ximo",
						 "sPrevious": "Anterior"
					}
		        }
		    } );
		     
		    $("tfoot input").keyup( function () {
		        oTable.fnFilter( this.value, $("tfoot input").index(this) );
		    } );
		     
		    $("tfoot input").each( function (i) {
		        asInitVals[i] = this.value;
		    } );
		     
		    $("tfoot input").focus( function () {
		        if ( this.className == "search_init" )
		        {
		            this.className = "";
		            this.value = "";
		        }
		    } );
		     
		    $("tfoot input").blur( function (i) {
		        if ( this.value == "" )
		        {
		            this.className = "search_init";
		            this.value = asInitVals[$("tfoot input").index(this)];
		        }
		    } );
	});
	function validarCadastroEtiquetas(){
		if($("#nomeEtiqueta").val() == ''){
			alert('Informe o nome da etiqueta.');
			return false;
		}else{
			return true;
		}	
	}

	function abrirPopupExclusao(pagina, nome ,w , h, scroll){
		LeftPosition = (screen.width) ? (screen.width-w)/2 : 0;
		TopPosition = (screen.height) ? (screen.height-h)/2 : 0;
		settings = 'height='+h+',width='+w+',top='+TopPosition+',left='+LeftPosition+',scrollbars='+scroll+',resizable';
		win = window.open(pagina, nome, settings);
	}

	//function abrirPopupExclusao(codigo){
		//window.open('administracaoEtiqueta?acao=excluir&codigoEtiqueta='+codigo, 'Ecar','toolbar=0,location=0,status=0,menubar=0,scrollbars=0,resizable=0,width=500,height=500');
	//}

	function initRequest() {
		if (window.XMLHttpRequest) {
			return new XMLHttpRequest();
		} else if (window.ActiveXObject) {
	        isIE = true;
	        return new ActiveXObject("Microsoft.XMLHTTP");
	    }
	}
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/menu_retratil.js"></script>
</head>
<body>
<%@ include file="../../frm_global.jsp"%>
<%@ include file="../../cabecalho.jsp" %>
<%@ include file="../../divmenu.jsp"%> 
<div id="conteudo">
	<div id="tituloTelaCadastro">
		<!-- TITULO -->
		<%@ include file="/titulo_tela.jsp"%> 
	</div>
		<div id="mensagem" style="#height: 40px; height: 40px; line-height: 40px; text-align: center; font-size: 12px;">
			${mensagem}
		</div>
		<div style="background-color: #EAEEF4; #width: 800px; width: 800px; padding: 10px;">
			<form method="post" action="administracaoEtiqueta" onsubmit="return validarCadastroEtiquetas();">
				<c:if test="${etiqueta.indAtivo == 'S'}">
					<input type="hidden" name="ativo" value="on"/>
				</c:if>
				<input type="hidden" value="${etiqueta.codigo}" name="codigoEtiqueta">
					<div style="float: left; #width: 25%; width: 20%; font-weight: bold;">
						Nome da Etiqueta:
					</div>
					<div style="float: right; #width: 80%; width: 75%;">
						<input id="nomeEtiqueta" name="nomeEtiqueta" value="${etiqueta.nome}" onkeyup="this.value = this.value.toUpperCase();" style="#width: 100%; width: 100%;"/>
					</div>
					<div style="clear: both;">
						<div style="float: right; font-weight: bold; #width: 20%; width: 15%; text-align: right;">
							<c:choose>
		                    	<c:when test="${etiqueta.codigo != null}">
		                     		<c:choose>
		                          		<c:when test="${etiqueta.indAtivo == 'S'}">
		                          			<input type="checkbox" name="ativo" checked="checked" disabled="disabled"/>
		                          		</c:when>
		                          		<c:otherwise>
		                          			<input type="checkbox" name="ativo"/>
		                          		</c:otherwise>
		                          	</c:choose>
		                        </c:when>
		                        <c:otherwise>
		                        	<input type="checkbox" name="ativo" checked="checked" disabled="disabled"/>
		                        </c:otherwise>
		                   	</c:choose>
		                   	Ativo
						</div>
						<div style="float: right; font-weight: bold; #width: 20%; width: 15%; text-align: right;">
							<c:choose>
		                    	<c:when test="${etiqueta.codigo != null}">
		                    		<c:choose>
		                    			<c:when test="${etiqueta.indPrioritario == 'S'}">
		                    				<input type="checkbox" name="prioritario" checked="checked"/>
		                    			</c:when>
		                    			<c:otherwise>
		                    				<input type="checkbox" name="prioritario"/>
		                    			</c:otherwise>
		                    		</c:choose>
		                    	</c:when>
		                    	<c:otherwise>
		                    		<input type="checkbox" name="prioritario"/>
		                    	</c:otherwise>
		                    </c:choose>
		                    Prioritário
						</div>
					</div>
					<div style="clear: both; text-align: right;">
						<input type="submit" value="Salvar" class="botao"/>
		          		<c:if test="${etiqueta.codigo != null}">
		          			<input type="button" value="Cancelar" class="botao"  onclick="location.href='administracaoEtiqueta'"/>
		          		</c:if>
					</div>						
			</form>
		</div>
		<div style="text-align: center; #width: 75%;">
			<table cellpadding="0" cellspacing="0" border="0" class="display" id="listaEtiquetasCadastradas" style="#width:100%; width:100%; clear: both;">
				<thead>
					<tr style="height: 35px;">
						<th>NOME</th>
						<th>PRIORITÁRIO</th>
						<th>ATIVO</th>
						<th>OPÇÕES</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${listaEtiquetas}" var="etiqueta">
						<tr>
							<td style="text-align: left;">
								${etiqueta.nome}
							</td>
							<td style="text-align: center; #width: 15%; width: 15%;">
								${etiqueta.prioritario}
							</td>
							<td style="text-align: center; #width: 15%; width: 15%;">
								${etiqueta.ativo}
							</td>
							<td style="text-align: center; #width: 15%; width: 15%;">
								<a href="administracaoEtiqueta?acao=editar&codigoEtiqueta=${etiqueta.codigo}" style="border: 0;">
			    					<img src="images/editar.png" alt="Editar" title="Editar" style="#padding-right: 5px; padding-right: 5px; border: 0;"/>
			    				</a>
			    				<c:choose>
			    					<c:when test="${etiqueta.indAtivo == 'N'}">
			    						<a href="#" onclick="alert('Etiqueta está inativa.');" style="border: 0;">
							    			<img src="images/excluir.png" alt="Excluir" title="Excluir" style="#padding-right: 5px; padding-left: 5px; border: 0;"/>
							    		</a>
			    					</c:when>
			    					<c:otherwise>
								    	<a href="javascript:abrirPopupExclusao('administracaoEtiqueta?acao=excluir&codigoEtiqueta=${etiqueta.codigo}', 'Ecar', '500', '500', 'no')" style="border: 0;">
								    		<img src="images/excluir.png" alt="Excluir" title="Excluir" style="padding-left: 5px; border: 0;"/>
								    	</a>
							    	</c:otherwise>
						    	</c:choose>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
</div>
</body>
<%@ include file="/include/estadoMenu.jsp"%>
</html>