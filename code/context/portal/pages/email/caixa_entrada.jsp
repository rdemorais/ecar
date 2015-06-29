<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<jsp:directive.page import="org.hibernate.HibernateException"/>
<%@ include file="/login/validaAcesso.jsp"%>
<%@ include file="/frm_global.jsp"%>

<%@page import="ecar.pojo.UsuarioUsu"%>
<%@ page import="ecar.dao.EmailDao" %>
<%@ page import="ecar.pojo.Email" %>
<%@ page import="comum.util.Data" %>
<%@ page import="java.lang.Math" %>
<%@ page import="org.apache.log4j.Logger" %>


<%@ taglib uri="/WEB-INF/ecar-util.tld" prefix="util" %>
<%
try{
	UsuarioUsu usuario = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	
	EmailDao emailDao = new EmailDao();
	Email email = new Email();
	
	String codEmail = Pagina.getParamStr(request,"codEmail");
	codEmail = ("0".equals(codEmail))?"":codEmail;
	String nPagina = Pagina.getParamStr(request,"nPagina");
	nPagina = ("".equals(nPagina) || "0".equals(nPagina))?"1":nPagina;
	 
	email.setUsuarioUsu(usuario);
	List lista = emailDao.pesquisar(email,new String[]{"dataHoraEnvio",EmailDao.ORDEM_DESC});
	
	if(!"".equalsIgnoreCase(codEmail)){
		email = (Email) emailDao.buscar(Email.class,Long.valueOf(codEmail));
		email.setLido(Email.LIDO);
		emailDao.alterar(email);
	}
	
	int quantidadeEmails = 0;

	for(int i = 0; i < lista.size(); i++){
		Email mail = (Email)lista.get(i);
		if(!Email.LIDO.equals(mail.getLido())){
			quantidadeEmails++;
		}
	}
	
	int intNumLinhas = 25;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="pt-br">
  <head>
<%@ include file="/include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>
<script type="text/javascript">
function abreEmail(email,pagina){
	document.form.codEmail.value = email;
	document.form.nPagina.value = pagina;
	document.form.submit();
}

function voltar(){
	document.form.action = "../index.jsp";
	document.form.submit();
}

function apagar(){
	var nCheck = document.getElementsByTagName("input");
	var n = 0;
	if(<%="".equalsIgnoreCase(codEmail)%>){
		for(i=0; i< nCheck.length; i++){
			if(nCheck.item(i) != null)
				if(nCheck.item(i).checked && nCheck.item(i).name == "checkmail")
					n++;
		}
	
		if (n == 0){
			alert("Marcar pelo menos um email");
			return 0;
		}
	}	
	document.form.hidAcao.value="excluir";
	document.form.codEmail.value="<%=codEmail%>";
	document.form.action = "ctrl.jsp";
	document.form.submit();
	return 1;
}

function marcartodas(){
	var nCheck = document.getElementsByTagName("input");
	var ChkTodos = document.getElementById("ChkTodos"); 
	
	for(i=0; i< nCheck.length; i++){
		if(nCheck.item(i) != null)
			if(nCheck.item(i).name == "checkmail")
				nCheck.item(i).checked = ChkTodos.checked;
	}
}
function destacaLinha(obj){
	switch(obj.className){
		case 'cor1': obj.className = 'cor1_over'; break;
		case 'cor2': obj.className = 'cor2_over'; break;
		case 'cor1_over': obj.className = 'cor1'; break;
		case 'cor2_over': obj.className = 'cor2';
	}
}

function marcar(opcao){
	var nCheck = document.getElementsByTagName("input");
	var n = 0;
		for(i=0; i< nCheck.length; i++){
			if(nCheck.item(i) != null)
				if(nCheck.item(i).checked && nCheck.item(i).name == "checkmail")
					n++;
		}
	
		if (n == 0){
			alert("Marcar pelo menos um email");
			return 0;
		}
		else{
			document.form.hidAcao.value = opcao;
			document.form.action="ctrl.jsp";
			document.form.submit();
		}
}

/* funcao que serve para a propriedade hover do CSS funcionar no IE */
function IEHoverPseudo() {

	var navItems = document.getElementById("nav").getElementsByTagName("input");

	for (var i=0; i<navItems.length; i++) {
			navItems[i].onmouseover=function() { this.className += " over";}
			navItems[i].onmouseout=function()  { this.className = "btnemail";}
	}
}
window.onload = IEHoverPseudo;
</script>
  </head>
  <body>
<%@ include file="/cabecalho.jsp" %>
	<div id="barra_localizacao">
		<a class="link" class="link" href="#" onclick="javascript:voltar();">Capa</a> > Email
	</div>
	<div id="corpo">
		<form name="form" method="post" action="caixa_entrada.jsp">
			<input type="hidden" name="codEmail" value="<%=codEmail%>">
			<input type="hidden" name="nPagina"  value="<%=nPagina%>">
			<input type="hidden" name="hidAcao"  value="">
			<div id="menu_email">
				<ul>
					<li><a class="link" href="#" onclick="javascript:abreEmail(0,0);">Caixa de Entrada (<%=quantidadeEmails%>)</a></li>
					<li><a class="link" href="#" onclick="javascript:voltar();">Sair</a></li>
				</ul>
			</div>
			<div id="corpo_email">
				<util:barraAcoesEmail
					codEmail="<%=codEmail%>"
					lista="<%=lista%>"					
				/>
<%
	if("".equalsIgnoreCase(codEmail)){
		int fim = Integer.parseInt(nPagina) * intNumLinhas;
		int ini = fim - intNumLinhas;
		fim = (fim > lista.size())?lista.size():fim;
		ini = (ini > fim)?fim:ini;
		if(lista != null || lista.size() >= fim){
			if( lista.size() == 0){
				out.print("<br><br><p> a caixa de e-mail est&aacute; vazia <p>");
			}
			else{
				Iterator it = lista.subList(ini,fim).iterator();
				int count = 0;
%>
				<hr>
				<table id="tabela_email">
					<thead>
						<tr>
							<td width="5px"  ><input type="checkbox" class="form_check_radio" onclick="javascript:marcartodas();" id="ChkTodos"></td>
							<td width="300px">Assunto</td>
							<td width="340px">Sistema</td>
							<td>Data / Hora</td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>&nbsp;</td>
						</tr>
<% 
				while(it.hasNext()){
					if(count >= intNumLinhas) break;
					email = (Email) it.next();
%> 
						<tr class='<%=((count % 2) == 0)?"cor1":"cor2"%>' onmouseover="javascript:destacaLinha(this);" onmouseout="javascript:destacaLinha(this);">
							<td><input type="checkbox" class="form_check_radio" id="chkBox<%=email.getCodEmail()%>" name="checkmail" value="<%=email.getCodEmail()%>"></td>
							<td class="<%=(email.getLido().equalsIgnoreCase(Email.LIDO))?"lido":"nlido"%>" onclick="javascript:abreEmail(<%=email.getCodEmail()%>,<%=nPagina%>);"><%=email.getAssunto()%></td>
							<td class="<%=(email.getLido().equalsIgnoreCase(Email.LIDO))?"lido":"nlido"%>" onclick="javascript:abreEmail(<%=email.getCodEmail()%>,<%=nPagina%>);"><%=email.getSistema()%></td>
							<td class="<%=(email.getLido().equalsIgnoreCase(Email.LIDO))?"lido":"nlido"%>" onclick="javascript:abreEmail(<%=email.getCodEmail()%>,<%=nPagina%>);"><%=Data.parseDateHourMinuteSecond(email.getDataHoraEnvio())%></td>
						</tr>
<% 
					count++;
				}
%> 
					</tbody>
				</table>
<%
			}
		} 
	}
	else{
		try{
%>			
				<div id="detalhe_email">
<%			
			out.print(email.getConteudo());
%>
				</div>
<%
		}
		catch(NumberFormatException e){
			Logger.getLogger(this.getClass()).error(e);
			out.print(_msg.getMensagem("portal.email.abrir.erro"));	
		}
	}
	
	if(lista.size() > intNumLinhas && "".equalsIgnoreCase(codEmail)){
		int intTotPaginas = (int) Math.ceil(((double)lista.size())/((double)intNumLinhas));
		int count = 1;
%>
				<div id="paginacao">
					<ul>
						<li>P&aacute;ginas: </li><li>
<%
		while(count <= intTotPaginas){
			if(count == Integer.parseInt(nPagina)){ 
%>					
						</li><li><%=count%></li><li>
<%
			}
			else{ 
%>					
						</li><li><a class="link" href="#" onclick="javascript:abreEmail(0,<%=count%>)"><%=count%></a></li><li>
<%
			}
			count++;
		} 
%>					
					</li></ul>
				</div>
			</div>
<%	
	}
}
catch(Exception e){
	Logger.getLogger(this.getClass()).error(e);
%>
	<script type="text/javascript">
		alert('<%=_msg.getMensagem("portal.email.erro")%>');
		window.history.back(1);
	</script>
<%	
}
%>
		</form>
	</div>
  </body>
</html>
