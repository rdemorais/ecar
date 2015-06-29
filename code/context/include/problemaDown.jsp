<script type="text/javascript">
	function showPopUpDownloadProblem() {
		var text = "<font size=3><strong>Problemas com o download do arquivo?</strong></font>";
		text += "<font size=2>";
		text += "<p>Ao clicar no link para realizar o donwload do arquivo, escolha a opção \"SALVAR\" para posteriormente ";
		text += "abrir o arquivo, isso fará com que ele seja salvo em seu disco rígido, em um diretório de sua escolha ";
		text += "para só então ser aberto pelo navegador. </p>";
		text += "<p>O erro para abrir diretamente o arquivo, ocorre exclusivamente no Microsoft Internet Explorer 6, com ";
		text += "atualização SP2, rodando sob o sistema operacional Microsoft Windows XP. Por algum motivo, em alguns casos, ";
		text += "o navegador se perde ao tentar abrir arquivos diretamente da Internet sem antes salva-los em disco.</p>";
		text += "</font>";
		var url = "<%=request.getContextPath()%>/popUp/popUpAvisoPadrao.jsp?visualizacao=true&texto=" + text + "&altura=600&largura=500";

		abreJanela(url, 550, 600, 'Visualização');
	} // showPopUpDownloadProblem
</script>
<div class="link_problemaDown">Problemas com download do arquivo? <a  href="javascript:showPopUpDownloadProblem(void(0));" >Clique aqui.</a></div>