<util:msgObrigatorio obrigatorio="<%=_obrigatorio%>" />
		<tr>
			<td class="label"><%=_obrigatorio%> Data</td>
			<td>
				<input type="text" name="dataEventoAgeo" size="14" maxlength="10" value="<%=Pagina.trocaNullData(agendaOC.getDataEventoAgeo())%>" onkeyup="mascaraData(event, this);">
				&nbsp;&nbsp;<input type="text" name="horaEventoAgeo" size="4" maxlength="2" value="<%=Pagina.getZeroAEsquerda(Pagina.trocaNull(agendaOC.getHoraEventoAgeo()))%>">
				:&nbsp;<input type="text" name="minutoEventoAgeo" size="4" maxlength="2" value="<%=Pagina.getZeroAEsquerda(Pagina.trocaNull(agendaOC.getMinutoEventoAgeo()))%>">
			</td>
		</tr>
		<tr>
			<td class="label"><%=_obrigatorio%> Evento</td>
			<td>
				<%
				if ((hidAcao!=null)&&("ALTERAR_OCORRENCIAS".equals(hidAcao))) { %>
					<input type="text" name="eventoAge" size="63" maxlength="200" value="<%=Pagina.trocaNull(agendaOC.getAgendaAge().getEventoAge())%>">
			<%	}
				else {%>
					<input type="text" name="eventoAge" size="63" maxlength="200" <%=_disabled%> value="<%=Pagina.trocaNull(agendaOC.getAgendaAge().getEventoAge())%>">											
			<%  }%>
			</td>
		</tr>
		<tr>
			<td class="label"><%=_obrigatorio%> Local</td>
			<td>
				<textarea name="localAgeo" rows="4" cols="60" 
					onkeyup="return validaTamanhoLimite(this, 2000);" 
					onkeydown="return validaTamanhoLimite(this, 2000);"
					onblur="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(agendaOC.getLocalAgeo())%></textarea>
			</td>
		</tr>
		<tr>
			<td class="label"><%=_obrigatorio%> Descrição</td>
			<td>
				<script language="JavaScript" type="text/javascript">
				//Uso: initRTE(imagesPath, includesPath, cssFile, genXHTML)
				initRTE("<%=_pathEcar%>/images/rte/", "<%=_pathEcar%>", "", false);
				</script>
				
				<script language="JavaScript" type="text/javascript">
				//Uso: writeRichText(fieldname, html, width, height, buttons, readOnly)
				writeRichText('rte', "<%=Pagina.trocaNull(agendaOC.getDescricaoAgeo()).replaceAll("\n", " ").replaceAll("\r", " ").replaceAll("</SCRIPT>","&lt;/SCRIPT&gt;").replaceAll("</script>","&lt;/script&gt;").replaceAll("</script -->","&lt;/script --&gt;").replaceAll("</SCRIPT -->","&lt;/SCRIPT --&gt;")%>", 400, 200, true, false);
				</script>				
			
				<input type="hidden" name="richText" id="richText"" size="90"  >
				<input type="hidden" name="descricaoAgeo" id="descricaoAgeo" size="90"  >	
						
<%--				<textarea name="descricaoAgeo" rows="4" cols="60" onkeyup="return validaTamanhoLimite(this, 2000);"><%=Pagina.trocaNull(agendaOC.getDescricaoAgeo())%></textarea>--%>
			</td>
			
		</tr>
		<tr>
			<td class="label" valign="top"> Ativo</td>
			<td>
				<%if ("S".equals(agendaOC.getAgendaAge().getIndAtivoAge())) { %>
				<input type="checkbox" class="form_check_radio" name="ativo" checked="checked">
				<%} else { %>
				<input type="checkbox" class="form_check_radio" name="ativo">
				<%}%>				
			</td>
		</tr>
		<tr><td class="label">&nbsp;</td></tr>