

<jsp:directive.page import="java.util.Collections"/>
<jsp:directive.page import="java.util.Comparator"/>
<jsp:directive.page import="java.util.HashMap"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="java.util.LinkedList"/>

<%@page import="ecar.dao.ItemEstruturaDao"%>
<%@page import="ecar.dao.ExercicioDao"%>
<%@page import="ecar.pojo.ExercicioExe"%>
<%@page import="ecar.pojo.UsuarioUsu"%>
<%@page import="ecar.pojo.OrgaoOrg" %>
<%@page import="ecar.servlet.relatorio.dto.SecretariaDTO" %>
<%@page import="ecar.pojo.acompanhamentoEstrategico.ObjetivoEstrategico"%>
<%@page import="ecar.servico.RelatorioAcompanhamentoService" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>



<%@page import="java.util.Enumeration"%>
<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%@ taglib uri="/WEB-INF/ecar-combo.tld" prefix="combo" %>


<%@page import="comum.util.ConstantesECAR"%><html lang="pt-br">
<head>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style_${sessionScope.configuracao.estilo.nome}.css" type="text/css"/>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.1/jquery-ui.js"></script>

<script language="javascript" src="../../js/menu_retratil.js"></script>
<script language="javascript" src="../../js/focoInicial.js"></script>
<script language="javascript" src="${pageContext.request.contextPath}/js/limpezaCamposMultivalorados.js"></script>

<script language="javascript" src="${pageContext.request.contextPath}/js/cookie.js"></script>
<%@ include file="../../login/validaAcesso.jsp"%>
<%@ include file="../../frm_global.jsp"%>
<%@ include file="../../include/meta.jsp"%>
<%@ include file="../../titulo.jsp"%>

<script>


	
	$(function(){ 
		$(".close").on("click", function(e){
			
			var coluna = $(this).parent().parent().attr("id"); 
			coluna = coluna.toString().substring(coluna.length-1,coluna.length);
			
						
			$(this).parent().parent().attr("ondrop", "drop(event)");
			$(this).parent().parent().attr("ondragover", "allowDrop(event)");
			
			
			$(this).css('visibility','hidden');
			
			//alert($(this).parent().attr("id"));
			var indice
			for(i=0;i<paineis.length;i++){
				
				if(paineis[i].id==$(this).parent().attr("id")){
					indice = i;
				}
			}
			
			paineis.splice(indice,1);
			
//			console.log(paineis);
			
			$('#listaObjetivos').append($(this).parent());
			
			$('#resultadoOE' + coluna.toString()).html('');

			$('#resultadoES' + coluna.toString()).html('');

			$('#resultadoR' + coluna.toString()).html('');

			$('#resultadoP' + coluna.toString()).html('');

			$('#resultadoAc' + coluna.toString()).html('');

			$('#resultadoAt' + coluna.toString()).html('');

			$('#resultadoIndicadores' + coluna.toString()).html('');

			$('#resultadoREM' + coluna.toString()).html('');

			$('#resultadoVerde' + coluna.toString()).html('');

			$('#resultadoAmarelo' + coluna.toString()).html('');

			$('#resultadoVermelho' + coluna.toString()).html('');

			$('#resultadoAzul' + coluna.toString()).html('');

			$('#resultadoCinza' + coluna.toString()).html('');

			$('#resultadoBranco' + coluna.toString()).html('');
			
			
			somaQuantitativos();

		});
		
		$("#comboExercicio").change(function(){ $('.close').trigger('click'); });

	});

	function allowDrop(ev) {
		ev.preventDefault();
	}

	function drag(ev) {
		ev.dataTransfer.setData("Text", ev.target.id);
	}

	function drop(ev) {
		
//		if($('#'+ev.target.id).find('button').length>0){
//			return false;
//		}


		ev.preventDefault();
		var data = ev.dataTransfer.getData("Text");
		
		if(parseInt(data)==0){ 
			$('.close').trigger('click');
		}
		
		ev.target.appendChild(document.getElementById(data));

		$('#'+ev.target.id).find('button').css('visibility','visible');
		
		var exercicio = $("#comboExercicio option:selected").val();

		var coluna = ev.target.id;
		coluna = coluna.toString().substring(coluna.length - 1, coluna.length);
		


		carregaEstatistica(data, exercicio, coluna);
		
		$('#'+ev.target.id).removeAttr("ondrop");
		$('#'+ev.target.id).removeAttr("ondragover");
		
	}

	
	var paineis = [];

	function carregaEstatistica(codigoObjetivoEstrategico, exercicio, alvo) {
		$("#overlay").fadeIn();
		$("#aguarde").fadeIn();
		

		$.getJSON('ctrlEstatistica.jsp?codigoObjetivo='
				+ codigoObjetivoEstrategico + "&exercicio=" + exercicio,
				function(data) {

//					console.log("ESTATÍSTICAS");
//					console.log(data);


					var painel = new Object();
					
					painel.id = codigoObjetivoEstrategico;
					painel.resultado = data;

					paineis.push(painel);

//					console.log("Todos os resultados");
//					console.log(paineis);

//					console.log('RESULTADO JSON');
					$.each(data, function(chave, valor) {
//						console.log(chave);
//						console.log(valor)

						//console.log('DADOS POR SECRETARIA');

						var somaQuantidade = 0;
						var listaSecretarias = "";
						for (i = 0; i < valor.length; i++) {
							somaQuantidade = somaQuantidade
									+ valor[i].quantidade;
							listaSecretarias+="<p>"+valor[i].siglaOrgao+":"+valor[i].quantidade+"</p>";
										
										 
						}

						if (chave == 'quantidadeObjetivoEstregico') {
							$('#resultadoOE' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

						if (chave == 'quantidadeEstrategia') {
							$('#resultadoES' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

						if (chave == 'quantidadeResultado') {
							$('#resultadoR' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

						if (chave == 'quantidadeProduto') {
							$('#resultadoP' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

						if (chave == 'quantidadeAcao') {
							$('#resultadoAc' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

						if (chave == 'quantidadeAtividade') {
							$('#resultadoAt' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

						if (chave == 'quantidadeIndicadores') {
							$('#resultadoIndicadores' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

						if (chave == 'quantidadeREM') {
							$('#resultadoREM' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

						if (chave == 'quantidadeVerde') {
							$('#resultadoVerde' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

						if (chave == 'quantidadeAmarelo') {
							$('#resultadoAmarelo' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

						if (chave == 'quantidadeVermelho') {
							$('#resultadoVermelho' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

						if (chave == 'quantidadeAzul') {
							$('#resultadoAzul' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

						if (chave == 'quantidadeCinza') {
							$('#resultadoCinza' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

						if (chave == 'quantidadeBranco') {
							$('#resultadoBranco' + alvo.toString()).html(
									"<span onclick='mostraSecretarias(event, this);'>"+somaQuantidade+"</span><div class='caixa' style='display:none;'>"+listaSecretarias+"</div>");
						}

					});
					
					
					//

				}).done(function() { somaQuantitativos(); });
		
		
	}
	
	
	function mostraSecretarias(e, elementoClicado){
		
		
		$('#'+elementoClicado.parentNode.id).find('div').dialog({
			autoOpen: false,
	        position: {my: "left top", of: e, offset: "5 70"},
	        show: "slide",
	        hide: "slide",
			width: 200,
			close: function(ev, ui) { $(this).dialog('destroy'); }
		}).dialog("open");
		
		
		//console.log(e);
		//console.log(elementoClicado.parentNode);
		//console.log($('#'+elementoClicado.parentNode.id).find("#div.caixa"));
	}
	
	function somaQuantitativos(){
		
		console.log('paineis');
		console.log(paineis);
		
		
		var somaQtdObjetivoEstrategivo=0;
		var somaQtdEstrategia=0;
		var somaQtdResultado=0;
		var somaQtdProduto=0;
		var somaQtdAcao=0;
		var somaQtdAtividade=0;
		var somaQtdREM=0;
		var somaQtdIndicadores=0;
		var somaQtdVerde=0;
		var somaQtdAmarelo=0;
		var somaQtdVermelho=0;
		var somaQtdAzul=0;
		var somaQtdCinza=0;
		var somaQtdBranco=0;
		
		
		var secretariasObjetivoEstrategivo=[];
		var secretariasEstrategia=[];
		var secretariasResultado=[];
		var secretariasProduto=[];
		var secretariasAcao=[];
		var secretariasAtividade=[];
		var secretariasREM=[];
		var secretariasIndicadores=[];
		var secretariasVerde=[];
		var secretariasAmarelo=[];
		var secretariasVermelho=[];
		var secretariasAzul=[];
		var secretariasCinza=[];
		var secretariasBranco=[];
		
		console.log('secretariasObjetivoEstrategivo');
		console.log(secretariasObjetivoEstrategivo);
		
		$.each(paineis, function(chave, valor){
			
			$.each(valor.resultado, function(chaveResultado, valorResultado){
				
				if (chaveResultado == 'quantidadeObjetivoEstregico') {
					
					
					for(i=0;i<valorResultado.length;i++){
						somaQtdObjetivoEstrategivo += valorResultado[i].quantidade;
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
//						$.map(secretariasObjetivoEstrategivo, function(n){
//							
//							
//							if(n.siglaOrgao==sigla){
//								n.quantidade += quantidade;
//								seRepetido = true;
//							}
//						});
						for(j=0;j<secretariasObjetivoEstrategivo.length;j++){
							if(secretariasObjetivoEstrategivo[j].siglaOrgao==sigla){
								secretariasObjetivoEstrategivo[j].quantidade+=quantidade;
								seRepetido = true;
							}
						}
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasObjetivoEstrategivo.push(secretaria);
						}
							
						
						//console.log(secretariasObjetivoEstrategivo);
					}					
				}

				if (chaveResultado == 'quantidadeEstrategia') {
					
					for(i=0;i<valorResultado.length;i++){
						somaQtdEstrategia += valorResultado[i].quantidade;
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
						$.map(secretariasEstrategia, function(n){
							
							if(n.siglaOrgao==sigla){
								n.quantidade += quantidade;
								seRepetido = true;
							}
							
						});
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasEstrategia.push(secretaria);
						}
							
					}						
				}

				if (chaveResultado == 'quantidadeResultado') {

					
					for(i=0;i<valorResultado.length;i++){
						somaQtdResultado += valorResultado[i].quantidade;
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
						$.map(secretariasResultado, function(n){
							
							if(n.siglaOrgao==sigla){
								n.quantidade += quantidade;
								seRepetido = true;
							}
							
						});
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasResultado.push(secretaria);
						}	
					}	
										
				}

				if (chaveResultado == 'quantidadeProduto') {

					
					for(i=0;i<valorResultado.length;i++){
						somaQtdProduto += valorResultado[i].quantidade;
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
						$.map(secretariasProduto, function(n){
							
							if(n.siglaOrgao==sigla){
								n.quantidade += quantidade;
								seRepetido = true;
							}
							
						});
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasProduto.push(secretaria);
						}
							
					}	
											
				}

				if (chaveResultado == 'quantidadeAcao') {

					
					for(i=0;i<valorResultado.length;i++){
						somaQtdAcao += valorResultado[i].quantidade;
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
						$.map(secretariasAcao, function(n){
							
							if(n.siglaOrgao==sigla){
								n.quantidade += quantidade;
								seRepetido = true;
							}
							
						});
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasAcao.push(secretaria);
						}
							
					}	
				}

				if (chaveResultado == 'quantidadeAtividade') {

					for(i=0;i<valorResultado.length;i++){
						somaQtdAtividade += valorResultado[i].quantidade;
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
						$.map(secretariasAtividade, function(n){
							
							if(n.siglaOrgao==sigla){
								n.quantidade += quantidade;
								seRepetido = true;
							}
							
						});
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasAtividade.push(secretaria);
						}
							
					}						
				}

				if (chaveResultado == 'quantidadeIndicadores') {

					
					for(i=0;i<valorResultado.length;i++){
						somaQtdIndicadores += valorResultado[i].quantidade;
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
						$.map(secretariasIndicadores, function(n){
							
							if(n.siglaOrgao==sigla){
								n.quantidade += quantidade;
								seRepetido = true;
							}
							
						});
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasIndicadores.push(secretaria);
						}
							
					}	
				}

				if (chaveResultado == 'quantidadeREM') {
					for(i=0;i<valorResultado.length;i++){
						somaQtdREM += valorResultado[i].quantidade;
						//secretariasREM+="<p>"+valorResultado[i].siglaOrgao+":"+valorResultado[i].quantidade+"</p>";
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
						$.map(secretariasREM, function(n){
							
							if(n.siglaOrgao==sigla){
								n.quantidade += quantidade;
								seRepetido = true;
							}
							
						});
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasREM.push(secretaria);
						}
							
					}
					//console.log(secretariasREM);
				}

				if (chaveResultado == 'quantidadeVerde') {

					
					for(i=0;i<valorResultado.length;i++){
						somaQtdVerde += valorResultado[i].quantidade;
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
						$.map(secretariasVerde, function(n){
							
							if(n.siglaOrgao==sigla){
								n.quantidade += quantidade;
								seRepetido = true;
							}
							
						});
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasVerde.push(secretaria);
						}
							
					}						
				}

				if (chaveResultado == 'quantidadeAmarelo') {

					
					for(i=0;i<valorResultado.length;i++){
						somaQtdAmarelo += valorResultado[i].quantidade;
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
						$.map(secretariasAmarelo, function(n){
							
							if(n.siglaOrgao==sigla){
								n.quantidade += quantidade;
								seRepetido = true;
							}
							
						});
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasAmarelo.push(secretaria);
						}
							
					}						
				}

				if (chaveResultado == 'quantidadeVermelho') {
						
					for(i=0;i<valorResultado.length;i++){
						somaQtdVermelho += valorResultado[i].quantidade;
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
						$.map(secretariasVermelho, function(n){
							
							if(n.siglaOrgao==sigla){
								n.quantidade += quantidade;
								seRepetido = true;
							}
							
						});
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasVermelho.push(secretaria);
						}
							
					}						
				}

				if (chaveResultado == 'quantidadeAzul') {
					
					for(i=0;i<valorResultado.length;i++){
						somaQtdAzul += valorResultado[i].quantidade;
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
						$.map(secretariasAzul, function(n){
							
							if(n.siglaOrgao==sigla){
								n.quantidade += quantidade;
								seRepetido = true;
							}
							
						});
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasAzul.push(secretaria);
						}
							
					}		
				}

				if (chaveResultado == 'quantidadeCinza') {
								
					for(i=0;i<valorResultado.length;i++){
						somaQtdCinza += valorResultado[i].quantidade;
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
						$.map(secretariasCinza, function(n){
							
							if(n.siglaOrgao==sigla){
								n.quantidade += quantidade;
								seRepetido = true;
							}
							
						});
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasCinza.push(secretaria);
						}
							
					}	
				}

				if (chaveResultado == 'quantidadeBranco') {
					
					for(i=0;i<valorResultado.length;i++){
						somaQtdBranco += valorResultado[i].quantidade;
						var sigla = valorResultado[i].siglaOrgao;
						var quantidade = +valorResultado[i].quantidade;
						var seRepetido = false;
						
						$.map(secretariasBranco, function(n){
							
							if(n.siglaOrgao==sigla){
								n.quantidade += quantidade;
								seRepetido = true;
							}
							
						});
						
						if(!seRepetido){
							var secretaria=new Object();
							secretaria.siglaOrgao = sigla;
							secretaria.quantidade = quantidade;
							secretariasBranco.push(secretaria);
						}
							
					}	
				}
				
				
			});
			
		});
		
		
		
		$('#resultadoOETotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdObjetivoEstrategivo+"</span><div class='caixa' style='display:none;'><table>"+$.map(secretariasObjetivoEstrategivo, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		$('#resultadoESTotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdEstrategia+"<div class='caixa' style='display:none;'><table>"+$.map(secretariasEstrategia, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		$('#resultadoRTotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdResultado+"<div class='caixa' style='display:none;'><table>"+$.map(secretariasResultado, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		$('#resultadoPTotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdProduto+"<div class='caixa' style='display:none;'><table>"+$.map(secretariasProduto, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		$('#resultadoAcTotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdAcao+"<div class='caixa' style='display:none;'><table>"+$.map(secretariasAcao, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		$('#resultadoAtTotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdAtividade+"<div class='caixa' style='display:none;'><table>"+$.map(secretariasAtividade, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		$('#resultadoIndicadoresTotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdIndicadores+"<div class='caixa' style='display:none;'><table>"+$.map(secretariasIndicadores, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		$('#resultadoREMTotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdREM+"<div class='caixa' style='display:none;'><table>"+$.map(secretariasREM, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		$('#resultadoVerdeTotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdVerde+"<div class='caixa' style='display:none;'><table>"+$.map(secretariasVerde, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		$('#resultadoAmareloTotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdAmarelo+"<div class='caixa' style='display:none;'><table>"+$.map(secretariasAmarelo, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		$('#resultadoVermelhoTotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdVermelho+"<div class='caixa' style='display:none;'><table>"+$.map(secretariasVermelho, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		$('#resultadoAzulTotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdAzul+"<div class='caixa' style='display:none;'><table>"+$.map(secretariasAzul, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		$('#resultadoCinzaTotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdCinza+"<div class='caixa' style='display:none;'><table>"+$.map(secretariasCinza, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		$('#resultadoBrancoTotal').html(
				"<span onclick='mostraSecretarias(event, this);'>"+somaQtdBranco+"<div class='caixa' style='display:none;'><table>"+$.map(secretariasBranco, function(n){ return "<tr><td>"+n.siglaOrgao+"</td><td>"+n.quantidade+"</td></tr>";})+"</table></div>");
		
		document.getElementById("aguarde").style.display="none";
		document.getElementById("overlay").style.display="none";
	
	}
</script>


<style type="text/css">

body
{
    overflow-y:
        scroll;
}


.ui-menu .ui-menu-item a {
	background-color: white;
   	display: block;
   	line-height: 1.5;
   	padding: 0.2em 0.4em;
   	text-decoration: none;
   	width: 300px;
}
.ui-menu {
   	list-style: none outside none;
   	width: 300px;
}
ul.ui-autocomplete{
	padding-left: 0;
	width: 300px;
}
.totalStatus {


	font-size: 14px;
	text-align: center;


}

.totalItens {

	font-size: 14px;
	text-align: left;


}



.draggable { width: 100px; height: 100px; padding: 0.5em; float: left; margin: 10px 10px 10px 0; }
.droppable { width: 125px; height: 55px; padding: 0.5em; float: left; margin: 5px; border-style: dashed; border-radius: 10px}

.resultado { width: 138px; height: 55px; padding: 0.5em; float: left; margin: 0px; border-style: solid; border-width: thin;}

.resultado span{font-size: 20px; font-weight: bolder; text-align: center; top: 50%; left: 50%; position:relative; cursor:pointer;}

.resultadoItens { width: 138px; height: 20px; padding: 0.5em; float: left; margin: 0px; border-bottom-style: dashed; border-left-style: solid; border-right-style: solid; border-width: thin;}

.resultadoItens span{font-size: 15px; font-weight: bolder; text-align: center; top: 50%; left: 50%; position:relative; cursor:pointer;}

.legenda { width: 65px; height: 55px; padding: 0.5em; float: left; margin: 0px; border-style: solid; border-width: thin;}

.legendaItens { width: 75px; height: 29px; float: left; border-style: solid; border-width: thin;}

.legendaStatus { width: 75px; height: 64px; float: left; border-style: solid; border-width: thin;}

.resultadoStatus { width: 138px; height: 55px; padding: 0.5em; float: left; margin: 0px; border-bottom-style: dashed; border-left-style: solid; border-right-style: solid; border-width: thin;}

.resultadoStatus span{font-size: 20px; font-weight: bolder; text-align: center; top: 50%; left: 50%; position:relative;cursor:pointer;}

#resultadoEstatistica { width: 830px;}

	.objetivoEstrategico{
		float: left; 
		height: 50px; 
		width: 120px; 
		margin-right: 45px; 
		margin-bottom: 5px;
		display: table-cell;
		text-align: center;
		font-weight: bold;
		font-size: 25px;
		color: white;
		border-radius: 10px;
		cursor: pointer;
	}
	
	.objetivoEstrategico button {
	
		visibility: hidden;
	}
	
	.objetivoEstrategico span{text-align: center; top: 50%; left: 50%;}
	
	
	
	fieldset{
		border: 1px solid #DEE3EF;
		padding-bottom: 5px;
	}
	legend{
		background-color: #DEE3EF;
    	color: #596A96;
    	font-size: 11px;
    	font-weight: bold;
    	padding: 5px;
	}	
	
	#listaObjetivos{

		height: 200px; 
		width: 600px; 
	}
	
	
	.close {
	background:none;
    border: 0 none;
    display: block;
    height: 7px;
    top: 10px;
    width: 7px;
    font-weight: bolder;
    font-size: 10px;
    color: white;
    float:right;
	}
	

   

</style>

</head>

<% request.setAttribute("exibirAguarde", "true"); %>

<%@ include file="/cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%> 


<html lang="pt-br">

<body>


	
<div id="conteudo">  
 
<!-- TÍTULO -->
<%@ include file="/titulo_tela.jsp"%>

<div id="parametros">

<form name="form" method="post" onsubmit="">
</form>
<fieldset>
								<legend>OBJETIVOS ESTRATÉGICOS</legend>
								<div id="listaObjetivos" style="text-align: center; width: 660px;">
	<%
		if (seguranca != null && seguranca.isAutenticado()) {

			UsuarioUsu usuario = seguranca.getUsuario();
			ItemEstruturaDao dao = new ItemEstruturaDao(request);

			List<ObjetivoEstrategico> listaOE = new ArrayList<ObjetivoEstrategico>();
			listaOE = dao.listarObjetivoEstrategicoAlt(usuario);


			for (ObjetivoEstrategico oe : listaOE) {
				
				
				String style = "";
				String numeracao = "";
				switch(Integer.parseInt(oe.getCodigo().toString())){
				case 7:
					style="background-color: #F16477; color: white;";
					numeracao = "1";
					break;
				case 10:
					style="background-color: #B93092; color: white;";
					numeracao = "2";
					break;
				case 11:
					style="background-color: #9B5BA4; color: white;";
					numeracao = "3";
					break;
				case 12:
					style="background-color: #B1005D; color: white;";
					numeracao = "4";
					break;
				case 2005:
					style="background-color: #0079C1; color: white;";
					numeracao = "5";
					break;
				case 21:
					style="background-color: #00B5CC; color: white;";
					numeracao = "6";
					break;
				case 14:
					style="background-color: #7E81BE; color: white;";
					numeracao = "7";
					break;
				case 15:
					style="background-color: #492F92; color: white;";
					numeracao = "8";
					break;
				case 16:
					style="background-color: #7BC143; color: white;";
					numeracao = "9";
					break;
				case 692:
					style="background-color: #B2BB1E; color: white;";
					numeracao = "10";
					break;
				case 19:
					style="background-color: #61C5BA; color: white;";
					numeracao = "11";
					break;
				case 18:
					style="background-color: #00958F; color: white;";
					numeracao = "12";
					break;
				case 17:
					style="background-color: #F58025; color: white;";
					numeracao = "13";
					break;
				case 20:
					style="background-color: #FDB813; color: white;";
					numeracao = "14";
					break;
				case 1823:
					style="background-color: #BB8D0A; color: white;";
					numeracao = "15";
					break;
				case 23:
					style="background-color: #B15C11; color: white;";
					numeracao = "16";
					break;
					
					
					
				}
				
				
				
				out.println("<div id='"+oe.getCodigo()+"' style='"+style+"' class='objetivoEstrategico' draggable='true' ondragstart='drag(event)'>");
				out.println("<button class='close'>x</button>");
				out.println("<span>");
				out.println(numeracao);
				out.print("</span>");
				
				
				out.println("</div>");
			}
			
			//todos os OEs
			out.println("<div id='0' style='background-color: #000000; color: white;' class='objetivoEstrategico' draggable='true' ondragstart='drag(event)'>");
			out.println("<button class='close'>x</button>");
			out.println("<span>Todos</span>");
			out.println("</div>");
			//-->

		}
	%>
								</div>
							</fieldset>
<fieldset>
<legend>EXERCÍCIO</legend>
<select id="comboExercicio">
<%

ExercicioDao exercicioDao = new ExercicioDao(request);ExercicioDao daoExercicio = new ExercicioDao(null);
List<ExercicioExe> listaExercicios = daoExercicio.listarExercios();

out.println("<option value='0'>Todos os exercícios</option>");
for (ExercicioExe exercicioExe : listaExercicios) {
		out.println("<option value='"+exercicioExe.getCodExe()+"'"+(exercicioExe.getCicloCorrenteExe() ?"selected='selected'":"") +" >"+exercicioExe.getDescricaoExe()+"</option>");	
}


%>

</select>

</fieldset>




	    <div id="objetivoEstrategico1" class="droppable" ondrop="drop(event)" ondragover="allowDrop(event)" style="margin-left: 80px;">
			
		</div>
		
		<div id="objetivoEstrategico2" class="droppable" ondrop="drop(event)" ondragover="allowDrop(event)">
			
		</div>
		
		<div id="objetivoEstrategico3" class="droppable" ondrop="drop(event)" ondragover="allowDrop(event)">
			
		</div>
		
		<div id="objetivoEstrategico4" class="droppable" ondrop="drop(event)" ondragover="allowDrop(event)">
			
		</div>
		
		<div id="objetivoEstrategicoTotal" class="droppable" style="visibility: hidden;">
			<span style="visibility: visible; font-size: 15px; text-align: center;">Consolidado</span>
		</div>		
</div>
<div id="resultadoEstatistica">		
		
		<!-- REM -->
		
		<div id="legendaREM" class="legenda">
			REM
		</div>
		
		<div id="resultadoREM1" class="resultado">
			
		</div>
		
		<div id="resultadoREM2" class="resultado">
			
		</div>
		
		<div id="resultadoREM3" class="resultado">
			
		</div>
		
		<div id="resultadoREM4" class="resultado">
			
		</div>
		
		<div id="resultadoREMTotal" class="resultado">
			
		</div>
		
		<!-- INDICADORES -->

		 <div id="legendaIndicadores" class="legenda">
			Indicadores
		</div>
		
		<div id="resultadoIndicadores1" class="resultado">
			
		</div>
		
		<div id="resultadoIndicadores2" class="resultado">
			
		</div>
		
		<div id="resultadoIndicadores3" class="resultado">
			
		</div>
		
		<div id="resultadoIndicadores4" class="resultado">
			
		</div>
		
		<div id="resultadoIndicadoresTotal" class="resultado">
			
		</div>
		
		<!-- ITENS -->
		
		<div id="legendaOE" class="legendaItens">
			OE
		</div>
		
		<div id="resultadoOE1" class="resultadoItens">
			
		</div>
		
		<div id="resultadoOE2" class="resultadoItens">
			
		</div>
		
		<div id="resultadoOE3" class="resultadoItens">
			
		</div>
		
		<div id="resultadoOE4" class="resultadoItens">
			
		</div>
		
		<div id="resultadoOETotal" class="resultadoItens">
			
		</div>		

		<div id="legendaES" class="legendaItens">
			Estratégia
		</div>
		
		<div id="resultadoES1" class="resultadoItens">
			
		</div>
		
		<div id="resultadoES2" class="resultadoItens">
			
		</div>
		
		<div id="resultadoES3" class="resultadoItens">
			
		</div>
		
		<div id="resultadoES4" class="resultadoItens">
			
		</div>	
		
		<div id="resultadoESTotal" class="resultadoItens">
			
		</div>			
		
		<div id="legendaR" class="legendaItens">
			Resultado
		</div>
		
		<div id="resultadoR1" class="resultadoItens">
			
		</div>
		
		<div id="resultadoR2" class="resultadoItens">
			
		</div>
		
		<div id="resultadoR3" class="resultadoItens">
			
		</div>
		
		<div id="resultadoR4" class="resultadoItens">
			
		</div>	
		
		<div id="resultadoRTotal" class="resultadoItens">
			
		</div>			
		
				
		<div id="legendaP" class="legendaItens">
			Produto
		</div>
		
		<div id="resultadoP1" class="resultadoItens">
			
		</div>
		
		<div id="resultadoP2" class="resultadoItens">
			
		</div>
		
		<div id="resultadoP3" class="resultadoItens">
			
		</div>
		
		<div id="resultadoP4" class="resultadoItens">
			
		</div>	
				
		<div id="resultadoPTotal" class="resultadoItens">
			
		</div>	
		
		<div id="legendaAc" class="legendaItens">
			Ação
		</div>
		
		<div id="resultadoAc1" class="resultadoItens">
			
		</div>
		
		<div id="resultadoAc2" class="resultadoItens">
			
		</div>
		
		<div id="resultadoAc3" class="resultadoItens">
			
		</div>
		
		<div id="resultadoAc4" class="resultadoItens">
			
		</div>	
		
		<div id="resultadoAcTotal" class="resultadoItens">
			
		</div>			
		
		<div id="legendaAt" class="legendaItens">
			Atividade
		</div>
		
		<div id="resultadoAt1" class="resultadoItens" style="border-bottom-style: solid; border-bottom-width: thin;">
			
		</div>
		
		<div id="resultadoAt2" class="resultadoItens" style="border-bottom-style: solid; border-bottom-width: thin;">
			
		</div>
		
		<div id="resultadoAt3" class="resultadoItens" style="border-bottom-style: solid; border-bottom-width: thin;">
			
		</div>
		
		<div id="resultadoAt4" class="resultadoItens" style="border-bottom-style: solid; border-bottom-width: thin;">
			
		</div>
		
		<div id="resultadoAtTotal" class="resultadoItens" style="border-bottom-style: solid; border-bottom-width: thin;">
			
		</div>		

		<!-- STATUS  -->		

		<div id="legendaVerde" class="legendaStatus">
			<img alt="" src="${pageContext.request.contextPath}/images/sVerde9.png">
		</div>
		
		<div id="resultadoVerde1" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoVerde2" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoVerde3" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoVerde4" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoVerdeTotal" class="resultadoStatus">
			
		</div>		

		<div id="legendaAmarelo" class="legendaStatus">
			<img alt="" src="${pageContext.request.contextPath}/images/sAmarelo9.png">
		</div>
		
		<div id="resultadoAmarelo1" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoAmarelo2" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoAmarelo3" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoAmarelo4" class="resultadoStatus">
			
		</div>	
		
				
		<div id="resultadoAmareloTotal" class="resultadoStatus">
			
		</div>	
		
		<div id="legendaVermelho" class="legendaStatus">
			<img alt="" src="${pageContext.request.contextPath}/images/sVermelho9.png">
		</div>
		
		<div id="resultadoVermelho1" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoVermelho2" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoVermelho3" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoVermelho4" class="resultadoStatus">
			
		</div>	
		
		<div id="resultadoVermelhoTotal" class="resultadoStatus">
			
		</div>	
		
				
		<div id="legendaAzul" class="legendaStatus">
			<img alt="" src="${pageContext.request.contextPath}/images/sAzul9.png">
		</div>
		
		<div id="resultadoAzul1" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoAzul2" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoAzul3" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoAzul4" class="resultadoStatus">
			
		</div>	
		
			<div id="resultadoAzulTotal" class="resultadoStatus">
			
		</div>	
		
		<div id="legendaCinza" class="legendaStatus">
			<img alt="" src="${pageContext.request.contextPath}/images/sCinza9.png">
		</div>
		
		<div id="resultadoCinza1" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoCinza2" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoCinza3" class="resultadoStatus">
			
		</div>
		
		<div id="resultadoCinza4" class="resultadoStatus">
			
		</div>	
		
		<div id="resultadoCinzaTotal" class="resultadoStatus">
			
		</div>			
		
		<div id="legendaBranco" class="legendaStatus">
			<img alt="" src="${pageContext.request.contextPath}/images/sBranco9.png">
		</div>
		
		<div id="resultadoBranco1" class="resultadoStatus" style="border-bottom-style: solid; border-bottom-width: thin;">
			
		</div>
		
		<div id="resultadoBranco2" class="resultadoStatus" style="border-bottom-style: solid; border-bottom-width: thin;">
			
		</div>
		
		<div id="resultadoBranco3" class="resultadoStatus" style="border-bottom-style: solid; border-bottom-width: thin;">
			
		</div>
		
		<div id="resultadoBranco4" class="resultadoStatus" style="border-bottom-style: solid; border-bottom-width: thin;">
			
		</div>
		
		<div id="resultadoBrancoTotal" class="resultadoStatus" style="border-bottom-style: solid; border-bottom-width: thin;">
			
		</div>		

</div>


</div>	





<% /* Controla o estado do Menu (aberto ou fechado) */%>
<%@ include file="../../include/estadoMenu.jsp"%>
<%@ include file="../../include/ocultarAguarde.jsp"%>
<%@ include file="../../include/mensagemAlert.jsp" %>	
</body>
</html>			
