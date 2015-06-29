		<% 
		Iterator lista = pontoCriticoDao.listaHistorico(null, null, null, new String[] {pontoCriticoPtc.getCodPtc().toString()}).iterator();
  		HistoricoDao historicoDao = new HistoricoDao(null);
		String imagem = "";
		List histCor = historicoDao.listar(HistoricoConfig.class, new String[]{"codHistorico", "asc"});
	
		if (lista != null & lista.hasNext()){
	%>
<tr><td colspan="11">
<table id="tableHistorico" width="100%" border="0" cellpadding="0" cellspacing="0">
	
	<tr >
		<td width="10%"></td>
		<td> </td>
		<td> Histórico </td>
		<td> Código </td>
		<td> Tipo </td>
		<td> Data </td>
		<td> Usuário </td>
	</tr>
	<%
	while (lista.hasNext())
	{
		HistoricoPontoCriticoPtc hptc = (HistoricoPontoCriticoPtc) lista.next();
		
		String tipo = ""; String cor = "";
		UsuarioUsu usuarioImagem = null;  
		String hashNomeArquivo = null;
		String pathRaiz = null;
		
		 if (hptc.getTipoHistorico() != null) 
			switch (hptc.getTipoHistorico().intValue()) {
			case 1: tipo = "Incluído";  cor = ((HistoricoConfig) histCor.get(0)).getCorHistorico(); 
			imagem = historicoDao.getImagemPersonalizadaHistorico(((HistoricoConfig) histCor.get(0)));break;
			case 2: tipo = "Alterardo"; cor = ((HistoricoConfig) histCor.get(1)).getCorHistorico(); 
			imagem = historicoDao.getImagemPersonalizadaHistorico(((HistoricoConfig) histCor.get(1)));break;
			case 3: tipo = "Excluído";  cor = ((HistoricoConfig) histCor.get(2)).getCorHistorico();
			imagem = historicoDao.getImagemPersonalizadaHistorico(((HistoricoConfig) histCor.get(2)));break;
			default: tipo = "-"; break;}
	%>
	<tr>
		<td width="10%"></td>
		<td width="5%">
			<%if (imagem != null){
				
			usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario(); 
			hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaiz, imagem);
			Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaiz, imagem);	
				
			imagem = request.getContextPath()+"/DownloadFile?tipo=open&RemoteFile=" + hashNomeArquivo; %>
			
			
			<img name="iconeHistorico<%=hptc.getIdHistorico()%>" src="<%=imagem%>" style="width: 15px; height: 15px;" width="" heigth="">
			<%}%>
			<input type="checkbox" class="form_check_radio" name="codHist" value="<%=hptc.getIdHistorico()%>">
			<input type="hidden" name="codPtc" value="<%=hptc.getCodPtc()%>">
		</td>
		<td style="color:<%=cor %>" width="5%"><%=hptc.getIdHistorico()%></td>
		<td style="color:<%=cor %>" width="5%"><%=hptc.getCodPtc()%></td>
		<td style="color:<%=cor %>">
			<%=tipo%>
		</td>
		<td style="color:<%=cor %>"><%=Pagina.trocaNullDataHora(hptc.getDataHistorico())%></td>
		<td style="color:<%=cor %>"><%=hptc.getUsuarioUsuOperacao() != null ? hptc.getUsuarioUsuOperacao().getNomeUsu() : ""%></td>
	</tr>
	<%
	}%>
	<tr>
		<td class="espacador" colspan="<%=lColunas.size() + 4%>">
		<img src="<%=request.getContextPath()%>/images/pixel.gif">
	</td>
	</tr>
</table>
</td></tr>
		