<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html lang="pt-br">
<head > 
	<%@ include file="/include/meta.jsp"%>
	<title>E-Car - Calendário</title>
	<html:base/>
	<link rel="stylesheet" href="../css/style_<%=new ecar.dao.ConfiguracaoDao(request).getConfiguracao().getEstilo().getNome()%>.css" type="text/css">
	<script language="JavaScript" src="../js/calendario.js"></script>
</HEAD>
<body onLoad='populate_calendar(monthNow, yearNow, eval(0) + eval(default_month))' bgcolor=#FFFFFF background='' link=#000000 alink=#CCCCCC vlink=#000000 >

<center>
<form name='myForm' method='post'>

<table border=0 cellpadding=0 cellspacing=0 width=200>
	<tr>
		<td colspan=3></td>
	</tr>
	<tr>
		<td ></td>
		<td valign=top >

			<table border=0 cellpadding=0 cellspacing=0 width=100%>
				<tr>
					<td colspan=3></td>
				</tr>
				<tr>
					<td ></td>
					<td valign=top >
						<table id="calendario" class="borda1px">
							<tr>
								<td align="right" width="20%">
									<!--
									onMouseOver = "swap_icon('prior', 'warm')"
										 onMouseOut = "swap_icon('prior', 'cold')"
										 
									-->
									<a id="calendario" class="seta" href="javascript: populate_calendar(mCurrent, yCurrent, -12 )">
									<<
									</a>
									&nbsp;&nbsp;
									<a id="calendario" class="seta" href="javascript: populate_calendar(mCurrent, yCurrent, -1 )">
									<
									</a>
									
								</td>
								<td align="center" width="60%"> 
								    <input readonly="true" id="calendario" class="h1Calendario" type=text name='date' width="60" height="20" value='' size=15 onChange='populate_calendar(mCurrent,yCurrent,0)'>
								</td>
								<td align="left" width="20%">
									<a id="calendario" class="seta" href="javascript: populate_calendar(mCurrent, yCurrent, 1)">
									>
									</a>
									
									&nbsp;&nbsp;
									<a id="calendario" class="seta" href="javascript: populate_calendar(mCurrent, yCurrent, 12)">
									>>
									</a>
									
									</td>
							</tr>
							<tr>
								<td colspan=3 align=center>

									<table id="calendario" class="borda1px">
										<tr>
											<th id="calendario" class="tdHeader1">Dom</th>
                                            <th id="calendario" class="tdHeader1">Seg</th>
                                            <th id="calendario" class="tdHeader1">Ter</th>
                                            <th id="calendario" class="tdHeader1">Qua</th>
                                            <th id="calendario" class="tdHeader1">Qui</th>
                                            <th id="calendario" class="tdHeader1">Sex</th>
                                            <th id="calendario" class="tdHeader1">Sáb</th>
										</tr>

										<tr>
											<td><input type='button' name='box0' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box1' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box2' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box3' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box4' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box5' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box6' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
										</tr>
										<tr>
											<td><input type='button' name='box7' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box8' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box9' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box10' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box11' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box12' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box13' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
										</tr>
										<tr>
											<td><input type='button' name='box14' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box15' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box16' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box17' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box18' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box19' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box20' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
										</tr>
										<tr>
											<td><input type='button' name='box21' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box22' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box23' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box24' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box25' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box26' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box27' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
										</tr>
										<tr>
											<td><input type='button' name='box28' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box29' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box30' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box31' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box32' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box33' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box34' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
										</tr>
										<tr>
											<td><input type='button' name='box35' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box36' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box37' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box38' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box39' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box40' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
											<td><input type='button' name='box41' id="calendario" class="buttonCalendario" value='  ' onClick='captureDaySelection(this.value)'></td>
										</tr>
									</table>

								</td>
							</tr>
						</table>

					</td>
					<td ></td>
				</tr>
				<tr>
					<td colspan=3></td>
				</tr>
				<tr>
					<td colspan=3>
						<center>
						<a href='javascript:closeWindow()'>
						Fechar Janela</a>
						</center>
					</td>
				</tr>
			</table>

		</td>
	</tr>
</table>

<input type='hidden' name='daySelected'>
<input type='hidden' name='monthYearSelected'>

</form>
</center>
</body>
</html>

