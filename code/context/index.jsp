<%@ page import="gov.pr.celepar.sentinela.comunicacao.*" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/informacao.tld" prefix="info" %>
<%@ taglib uri="http://celepar.pr.gov.br/taglibs/menu.tld" prefix="menu" %>
<%@ include file="frm_global.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="pt-br">
<head>
<%@ include file="include/meta.jsp"%>
<%@ include file="/titulo.jsp"%>

<script language="javascript" src="js/menu_retratil.js"></script>
</head>

<body>

<%
request.setAttribute("exibirAguarde", "true");
%>
<%@ include file="cabecalho.jsp" %>
<%@ include file="/divmenu.jsp"%>

<div id="conteudo">
	<h1>Selecione a op&ccedil;&atilde;o no menu</h1>
	
  <table class="form" border="1" cellspacing="1">
  <tr align="center"> 
  	<td colspan="4">HIST�RICO DAS VERS�ES</td>
  </tr>
  <tr align="center"> 
  	<td>VERS�O</td>
  	<td>ORIGEM</td>  
  	<td>DATA</td>
  	<td>COMENT�RIOS/NOVIDADES EM RELA��O � VERS�O ANTERIOR</td>
  </tr>

 <tr> 
  	<td align="center">v8.8.0</td>
  	<td align="center">HEAD</td>  
  	<td align="center">12/01/2011</td>
  	<td>
 	- CORRE��ES DE ERROS E MANUTEN��ES:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [RESTRI��ES] Bloqueio seletivo de campos - A funcionalidade de bloqueio seletivo de campos na fun��o de restri��es n�o est� obedecendo � configura��o de atributo na estrutura, onde � indicado os campos que devem aparecer bloqueados para as fun��es definidas. Deveria se comportar semelhante ao cadastro, ap�s o bloqueio do item atrav�s do bot�o de bloqueio, bloquear os campos configurados conforme a fun��o do usu�rio.<br>  	  	  	
 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [RESTRI��ES] Ordena��o pela primeira coluna - Ordenar listagem de restri��es, inclusive por atributos livres, na primeira coluna.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [RESTRI��ES] Como op��o padr�o para o cadastro de restri��es o checkbox "Ativo" do conjunto "Estados e Envio de e-mails" deve vir desativado.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [INTEGRA��O FINANCEIRA] 1. Erro na vincula��o de contas do or�amento - Corre��o do erro que ocorre durante a associa��o de contas do or�amento e ajuste dos labels conforme visualiza��o da Fun��o ou Sub-Fun��o.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [ACOMPANHAMENTO] No acompanhamento, replicar o link de acesso ao modo de consulta (link da carinha e/ou do quadrado em branco da tela de posi��o geral) no nome texto do item que � apresentado na tela de acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [ACOMPANHAMENTO] Ocultar a aba de pareceres quando o usu�rio n�o tem permiss�o de visualizar nenhum dos pareceres.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [ACOMPANHAMENTO] Avaliar o reflexo das mudan�as no relat�rio da tela de Acompanhamento. Manter a compatibilidade com o que foi implementado no previsto mensal.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [ACOMPANHAMENTO] Na tela de preenchimento do parecer, destacar com a cor vermelha as mensagens de datas e vencimento dos pareceres.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [ACOMPANHAMENTO] Erro ao Recuperar Usu�rio (Usu�rio n�o encontrado). Na tela de listagem de itens, o sistema, em algumas situa��es, n�o est� recuperando o nome dos usu�rios definidos para as fun��es de acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [DADOS GERAIS] Ap�s bloquear um item com atributo seletivo (define se a estrutura filha ser� apresentada ou n�o) e alterar o item, o sistema retorna erro informando que o atributo seletivo foi alterado. Verificar se ocorre somente no atributo livre do tipo checkbox.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [METAS E INDICADORES] Desabilitar edi��o de valores realizados de indicadores de meses anteriores. O campo deve estar habilitado para edi��o e os valores somente podem ser alterados nos respectivos meses em que houve a grava��o da informa��o e quando a data limite para informa��o de realizado f�sico estiver em ciclo v�lido para tal.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [METAS E INDICADORES] Revis�o da tela de visualiza��o e preenchimento dos indicadores: Sempre apresentar os valores previstos; Adequa��o da tela para utilizar a op��o Acumulados, cortando o valor at� o ciclo visualizado se a op��o estiver marcada at� o ciclo e apresentando tudo se a op��o for Total; Redefini��o dos gr�ficos.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [METAS E INDICADORES] Erro na ordena��o dos indicadores dos itens filhos. A listagem dos itens filhos na tela de metas-indicadores do acompanhamento est� seno retornada com uma ordem aleat�ria. A ordena��o dever� ser equivalente a do cadastro de itens.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [PORTAL] Corrigir a apresenta��o da imagem de detalhe do item. A imagem que deve ser apresentada na tela de detalhe do clipping deve ser a que foi configurada no campo imagem 1 da configura��o, vide menu: Administra��o Portal > Taxa��es > Taxa��o.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [PORTAL] Trocar label "taxa��o/clipping" por "na m�dia".<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [EXPORTA��O] Adicionar exporta��o de informa��o de arquivos anexos aos itens.<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [BANCO] Adaptar o e-Car para que seja compat�vel com a vers�o 9.0 do Banco de Dados PostgreSQL.<br>
  	</td>
  </tr>

 <tr> 
  	<td align="center">v8.7.2</td>
  	<td align="center">branch_08_07_02_20101129</td>  
  	<td align="center">30/11/2010</td>
  	<td>
 	- CORRE��ES DE ERROS:<br>
  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  Ao gerar um ciclo de acompanhamento n�o separado por �rg�o o sistema n�o inclui os itens do cadastro que possuem o campo �rg�o respons�vel informado.<br>
 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  Ao copiar um modelo de estrutura para um novo projeto, o sistema mostra o erro: <i>if(window.opener){ alert('N�o foi poss�vel acessar a funcionalidade atrav�s desta op��o escolhida. \nExce��o: null ...</i><br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  Na copia de um modelo de estrutura para um novo projeto os itens de estrura est�o aparecendo com "pais" diferentes do cadastrado.<br>
  	</td>
  </tr>
  
 <tr> 
  	<td align="center">v8.7.1</td>
  	<td align="center">branch_08_07_01_20101105</td>  
  	<td align="center">08/11/2010</td>
  	<td>
 	- CORRE��ES DE ERROS:<br>
  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro na visualiza��o da aba de Resumo no Acompanhamento.<br>
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.7 BETA 1</td>
  	<td align="center">HEAD</td>  
  	<td align="center">26/08/2010</td>
  	<td>
  	  	  	 	
  	- MANUTEN��ES:<br>
  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar comportamento do perfil para permitir as opera��es de pesquisa, inclus�o, altera��o e exclus�o, bem como a defini��o de novos par�metros de configura��o por tecnologia de obten��o dos dados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a funcionalidade de importa��o dos dados de empreendimentos do PACInter para itens de estrutura no e-CAR, considerando os novos conceitos de Tipo de Funcionalidade e Tecnologia de Comunica��o, limitando �s op��es "Cadastro" e "Arquivo Texto em Formato Espec�fico", respectivamente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a funcionalidade de consulta ao log de importa��o, considerando os novos conceitos de Tipo de Funcionalidade e Tecnologia de Comunica��o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Permitir a gera��o de ciclos separados por �rg�o. O usu�rio poder� exibir, em uma mesma refer�ncia, todos os ciclos gerados para um mesmo dia/m�s/ano na listagem de acompanhamento. A listagem do acompanhamento e a listagem de itens dispon�veis para gera��o ser�o exibidas de forma agrupada, separando os itens por �rg�o. Permitir a gera��o de arquivos e relat�rios para refer�ncias separadas por �rg�o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a forma de gera��o de arquivos (cadastro de itens e acompanhamento) para n�o permitir a exporta��o de subfun��es sem que a fun��o relacionada seja exportada. Desta forma, para a exporta��o das subfun��es 'Quantidades Previstas' e 'Encaminhamentos' ser� necess�ria a exporta��o das fun��es 'Metas/Indicadores' e 'Restri��es' respectivamente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Permitir que o usu�rio indique um grupo de atributos livres da estrutura pai a ser utilizado como crit�rio de restri��o para exibi��o de estruturas. A configura��o ser� realizada no cadastro de estruturas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar as valida��es existentes na configura��o de estruturas. Remover a obrigatoriedade dos campos 'Data In�cio', 'Data Fim', '�rg�o Respons�vel' e 'Periodicidade de Acompanhamento' do cadastro de estruturas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a exibi��o das abas do acompanhamento de acordo com configura��o de exibi��o de abas e �cones e fun��o na estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar o arquivo gerado no cadastro de demandas, com a inclus�o do cabe�alho para as colunas geradas, e a inclus�o de duas colunas com os identificadores das demandas e dos encaminhamentos relacionados.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Criar aviso, na listagem de ciclos gerados, informando quando um �rg�o estiver inativo (seguir padr�o utilizado para destacar itens com �rg�o alterado ap�s a gera��o de ciclos).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na gera��o de ciclos separados por �rg�o, n�o considerar os itens de estruturas que n�o possuem o campo �rg�o configurado.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Retirar obrigatoriedade de haver ao menos um item selecionado para a altera��o de ciclos.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtrar os locais poss�veis de uso para registro do previsto ou realizado. Somente locais vinculados ao item, ou seus locais filhos podem ser utilizados como locais poss�veis de registro do previsto ou realizado.  Se j� houver locais com valores previstos ou realizados registrados, somente locais da mesma abrang�ncia, podem ser utilizados para novos registros de previstos ou realizados.<br> 
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Valores previstos de indicadores passaram a ser  informados por m�s. Registro de valores previstos tratando valores por m�s em cada ano, utilizando o crit�rio de �rvore para exibi��o dos locais e seus filhos, onde o usu�rio poder� registrar os valores (popup valores previstos informados por local).<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Melhorias no gr�fico de realizado por exerc�cio. Foi inclu�do no gr�fico de realizado por exerc�cio as informa��es do previsto calculando os valores baseados nos valores informados no previsto mensal (soma, final, maior, soma locais, etc). Antes estava sendo exibido o previsto informado em cada ano no momento do cadastro do indicador. Inclu�do link no gr�fico do realizado por exerc�cio para ao clicar na barra de um exerc�cio apresentar o gr�fico de realizado por m�s no exerc�cio para indicadores agrupados ou n�o.
                                    Os gr�ficos com exerc�cio com meses de anos diferentes (tabela de exerc�cios), devem apresentar o ano ao qual pertence o exerc�cio (realizado mensal), tanto no gr�fico como na tabela exibida.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Melhoria no gr�fico de realizado por m�s. Incluir no gr�fico de realizado por m�s no exerc�cio as informa��es do previsto por m�s.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Melhorias no gr�fico de proje��o. No gr�fico de proje��o, o sistema dever� apresentar a data de previs�o de t�rmino ao inv�s da porcentagem de execu��o do projeto em seu encerramento. Manter na proje��o gr�fica, a linha que � tra�ada at� o final do projeto ou at� que sejam conclu�dos os valores previstos, o que for maior. Na visualiza��o do gr�fico, o usu�rio poder� ver qual a dimens�o da diferen�a dos valores previstos e realizados at� o final do projeto. A proje��o dever� utilizar o c�lculo para uma progress�o aritm�tica m�dia, por�m o usu�rio poder� optar por outro m�todo de c�lculo de aproxima��o tal como, progress�o atrav�s da m�dia de acelera��o.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ajustes no valor calculado do realizado sobre o previsto (%). O c�lculo de valor realizado sobre o previsto (%) deve ser revisto ocultando a op��o "n�o se aplica" no cadastro do indicador. O termo TOTAL ser� mantido na tela de visualiza��o do realizado independente se o indicador � acumul�vel ou n�o.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Mostrar Indicadores de Itens filhos no acompanhamento. Na tela de acompanhamento de indicadores, exibir Indicadores de itens filhos de um item tanto para a op��o REGISTRAR quanto para a op��o VISUALIZAR os valores.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Valores para sinaliza��o s�o em porcentagem (%). No cadastro de indicadores, o sistema deve apresentar a grade de valores para sinaliza��o incluindo a unidade "%". Os valores informados ser�o sempre percentuais que dever�o ser aplicados sobre os valores previstos e realizados para identifica��o da sinaliza��o a utilizar, onde se aplicar a sinaliza��o.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ajustes no tratamento de edi��o para n�mero ou moeda. No cadastro e no acompanhamento de indicadores, corrigir tratamento de edi��o para n�mero ou moeda, onde n�mero somente deve apresentar casas decimais se o valor informado contiver valores significativos na casa decimal. Aceitar e guardar tantas casas decimais quantas forem informadas, quando for formato n�mero. Para valor tratar sempre como 2 casas decimais, arredondando.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Exporta��o de Indicadores. No cadastro, ajustar a exporta��o dos valores dos indicadores considerando o previsto mensal. No acompanhamento, implementar a exporta��o de indicadores (valores realizados).<br>
          	  	 	
  	- CORRE��ES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Demandas - O "Caracter Separador para Campos Multivalorados", informado em Configura��o Geral, n�o est� sendo utilizado na listagem nem no detalhamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - O sistema n�o exibe corretamente os textos do combo ciclo de refer�ncia, n�o concordando com o n�mero de ciclos gerados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento -  A partir da aba "Etapa N�vel Inferior" (em visualiza��o), ao clicar em qualquer aba superior, o sistema apresenta erro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro: Revis�o - O sistema apresenta erro ao clicar no bot�o adicionar da fun��o 'Revis�o' do cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - A partir da listagem de itens, ao selecionar a op��o de impress�o o sistema exibe a tela de gera��o de relat�rio do cadastro com uma aba cujo link leva para a mesma tela. O link dever� ser retirado.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configura��o Sistema / Cadastro - O sistema disponibiliza a adi��o de �rg�os inativos no cadastro de usu�rios e n�o exibe, no cadastro de itens, os �rg�os inativos, mesmo que estejam sendo utilizados por um item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Demandas - O sistema n�o verifica a obrigatoriedade do campo Prioridade.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Demandas - Na altera��o do cadastro de demandas, o sistema apaga os valores  informados  nos campos do tipo multitexto configurados apenas como 'Exib�vel?'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - As �rvores Ajax e de Navega��o est�o exibindo os caminhos dos arquivos salvos no cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - Di�rio de Bordo - O sistema n�o grava corretamente as datas informadas fora do padr�o DD/MM/AAAA.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - O sistema exibe erro de nullpointer quando o usu�rio clica no bot�o para excluir uma anota��o sem ter nenhuma anota��o selecionada.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - Associa��o de Demandas - O "Caracter Separador para Campos Multivalorados", informado em Configura��o Geral, n�o est� sendo utilizado na listagem das demandas exibidas no popup de associa��o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Resumo - O sistema n�o disponibiliza link para todas as fun��es de acompanhamento na aba de resumo.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Situa��o Pontos Cr�ticos - Erro ao recarregar a p�gina pressionando a tecla 'ENTER' no campo 'comparativo'.  A valida��o de datas (Comparativo deve ser maior que data atual) n�o � realizada caso o usu�rio recarregue a p�gina utilizando o 'ENTER'. Al�m disso, os filtros de estado s�o perdidos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Restri��es - O sistema exibe erro de nullpointer caso o usu�rio tente detalhar uma restri��o de um item (acompanhamento de itens) sem que a estrutura deste item esteja configurada para exibir a sub-fun��o apontamentos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configura��o Sistema - Geral - Na Configura��o Geral, ao digitar caracteres alfa nos campos num�ricos, e confirmar a grava��o, o sistema exibe a mensagem: "N�o foi poss�vel acessar a funcionalidade atrav�s desta op��o escolhida. Exce��o: For input string:" <br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Confiura��o Estrutura -  Ao incluir uma nova estrutura, o combo 'N�vel Superior' est� exibindo estruturas inativas para serem Pai. Por�m, na altera��o, as estruturas inativas n�o s�o apresentadas no combo, o qual apaga o valor anteriormente informado.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - O "Caracter Separador para Campos Multivalorados", informado em Configura��o Geral, n�o est� sendo devidamente exibido na listagem de itens do Cadastro. O problema ocorre quando o caracter "\" � utilizado como separador. <br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro / Acompanhamento - Relat�rios Impressos - Em alguns campos do tipo atributo livre o valor exibido no campo corresponde ao nome do Atributo, s�o eles: upload de arquivo, todos os campos de valida��o e multitexto.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Administra��o Sistema - Tabelas - Poder - O sistema exibe mensagem de erro "Ocorreu um erro interno no HibernateErro" ao tentar realizar a exclus�o de Poder.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Administra��o Sistema - Tabelas - Cor - O sistema exibe mensagem de erro "Ocorreu um erro interno" ao tentar realizar a exclus�o de Cor. <br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Relat�rios Impressos - Acompanhamento - A exibi��o dos pareceres est� diferente da regra definida na listagem do Acompanhamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Corre��o na regra para exibi��o dos pareceres emitidos - "Mostrar �ltimos(as) Situa��es Emitidos(as)" e "Mostrar Todos(as) Situa��es Emitidos(as)". O sistema deve apresentar apenas os pareceres liberados, considerando a configura��o realizada no Tipo de Acompanhamento (op��es 'Exige Liberar Parecer' e 'Exige Liberar Acompanhamento').<br>

    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento de Indicadores - Considerar crit�rio de tipos indicadores para incluir novos indicadores no acompanhamento. O sistema n�o considera o crit�rio de tipos de indicadores v�lidos para o tipo de acompanhamento. Ocorre no momento de listar novos indicadores encontrados no cadastro, apesar de considerar ao tentar inclu�-los no acompanhamento.<br> 
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro de Indicadores - O bot�o limpar no atributo tipo de indicador n�o atua sobre o dado gravado. Ao alterar o tipo, o sistema grava o dado alterado, mas o limpar n�o grava o registro sem um tipo associado.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento de Indicadores - No campo de registro do valor realizado o uso do termo "at� o m�s" deve ser utilizado caso o Indicador seja do tipo N�O Acumul�vel. Para Indicador do tipo Acumul�vel utilizar o termo "no m�s".<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Gera��o de Ciclos de Acompanhamento - Indicadores exclu�dos no cadastro continuam sendo criados e exibidos no acompanhamento.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro e Acompanhamento de Indicadores. Erro ao informar quantidade prevista e realizada em indicadores de um item sem data inicial ou final informada. Caso o item de estrutura n�o tenha data de in�cio ou t�rmino busca a data dos exerc�cios cadastrados.<br>
  	
  	</td>
  </tr>
  
  
  <tr> 
  	<td align="center">v8.6 BETA 7</td>
  	<td align="center">ECAR-BR-AJUSTE-8-6-BETA-6</td>  
  	<td align="center">04/06/2010</td>
  	<td>

  	- CORRE��ES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao desativar uma estrutura de 2� N�vel, o acompanhamento dessa estrutura e de suas filhas n�o aparecem na listagem de posi��o geral.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na gera��o de arquivos em Demandas, no IE n�o est� apresentando o arquivo para download ao clicar no bot�o gerar arquivo, somente est� sendo poss�vel baixar o arquivo pelo Firefox.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Demandas - Os atributos livres de texto com valida��o n�o est�o respeitando a limita��o do tamanho do campo, aceitando mais caracteres do que foi configurado em atributo na demanda.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Os atributos livres de texto com valida��o, do tipo Valor, est�o aceitando um n�mero a menos do que o configurado em atributo na demanda.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - A �rvore de navega��o n�o aparece ao adicionar um item na estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Sistema - Ao entrar pelo Portal n�o est� sendo poss�vel configurar o sistema para abrir os m�dulos do sistema j� com o menu lateral encolhido. <br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Ao definir o menor n�vel de visualiza��o para o tipo de acompanhamento, o sistema est� colocando a cor azul no fundo dos itens filhos na tela de posi��o geral.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Utilizando o IE na aba de Etapa de n�vel inferior um �cone quebrado que � apresentado na listagem.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Na tela de posi��o geral, utilizando a visualiza��o por menor n�vel de visualiza��o na configura��o do tipo de acompanhamento, os pareceres das fun��es n�o est�o sendo apresentados conforme a permiss�o definida no grupo de acesso.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - Metas-Indicadores - Ao alterar uma meta-indicador cadastrada o sistema apresenta a tela de carregamento junto com o aviso de item alterado com sucesso e s� termina o carregamento da p�gina ap�s o clique no OK da mensagem.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - Localiza��o Geogr�fica - Os bot�es de adicionar e excluir n�o est�o funcionando no IE.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Na tela de posi��o geral a partir dos itens ajax, ao clicar no �cone da posi��o (carinha) o sistema � direcionado para a tela de registro de parecer e n�o para a de consulta.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Na tela de posi��o geral a partir dos itens ajax, ao clicar no �cone da posi��o (carinha) o sistema n�o direciona para aba configurada no cadastro de exibi��o de abas e �cones.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Na tela de posi��o geral a partir dos itens ajax, o sistema n�o exibe o hint com o nome da estrutura do item.<br>
  	  	
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.6 BETA 6</td>
  	<td align="center">ECAR-BR-AJUSTE-8-6-BETA-6</td>  
  	<td align="center">31/05/2010</td>
  	<td>
  	  	  	 	
  	- MANUTEN��ES:<br>
  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar conte�do do campo "Formato" no arquivo de exporta��o de Metas/Indicadores de "Q" ou "V" para "N�mero" ou "Moeda" respectivamente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Remover campo "C�digo de Atua��o" do arquivo de exporta��o de Entidades.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Permitir edi��o e visualiza��o de itens de estruturas inativas no acompanhamento, apenas para as seguintes abas: Datas Limites, Metas/Indicadores e Situa��o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Generalizar mensagens de sucesso durante a manuten��o dos Apontamentos das Restri��es.<br>
      	  	 	
  	- CORRE��ES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de cadastro de demandas, n�o est� sendo validada a quantidade de caracteres permitidos para o cadastramento de dados no campo de texto, quando o valor do tamanho do campo definido na configura��o � maior do que 80 e vira um textarea.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Os atributos livres do tipo TextArea n�o est�o validando o tamanho do campo conforme a configura��o nem em Demandas nem no Cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de cadastro de demandas, o label do primeiro item de campos do tipo MultiTexto est� fora de posicionamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Quando definido como p�gina inicial do usu�rio o m�dulo de portal, o sistema n�o est� exibindo as mensagens configuradas no cadastro de popup ao efetuar login no sistema.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na gera��o de arquivos em Demandas, quando prenchido o campo de upload o arquivo de exporta��o vem sem os outros campos exportados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de filtros de Acompanhamento, ao excluir um filtro criado, sendo este de itens pertencentes a um tipo acompanhamento diferente do que aparece em primeiro, e depois selecionando outro filtro para consulta, ocorre um erro com a seguinte mensagem: "N�o foi poss�vel acessar a funcionalidade atrav�s desta op��o escolhida. Exce��o: Index: 0, Size: 0".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na aba de Situa��o dos Pontos Cr�ticos, no Acompanhamento, para um item que n�o teve acompanhamento gerado para o ciclo exibido, o sistema est� exibindo os pontos cr�ticos deste item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema permite que um usu�rio, sem permiss�o de criar filtro pessoal e/ou do sistema, salve uma pesquisa no acompanhamento criando assim um filtro.<br>
 	
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.6 BETA 5</td>
  	<td align="center">HEAD</td>  
  	<td align="center">12/05/2010</td>
  	<td>
  	  	  	 	
  	- MANUTEN��ES:<br>
  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na configura��o de vis�es, permitir informar um nome de at� 500 caracteres para uma vis�o; e no Cadastramento de Demandas, apresentar os nomes das vis�es como links.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Exibir, um atributo configurado para usar um grupo de atributo do tipo entrada de texto, um campo texto ou �rea de texto de acordo com o tamanho configurado para o conte�do. Se o tamanho configurado para o conte�do for menor ou igual a 80, exibir o mesmo como texto, caso configurado com conte�do maior que 80, exibir o mesmo como �rea de texto - mesmo este utilizando um grupo de atributo com tipo entrada de texto.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Disponibilizar um link no canto superior direito da tela de edi��o de demanda, que retorne para a listagem de demandas.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Retirar as mensagens de aviso do Internet Explorer quando o sistema for acessado atrav�s deste navegador com uso de https.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar mensagens de valida��o dos campos Sequ�ncia e Largura de Fun��o de Acomp. na Estrutura para "Obrigat�rio o preenchimento do campo Sequ�ncia na Lista do Cadastro de Itens quando preenchida a op��o Exibir na Lista." e "Obrigat�rio o preenchimento do campo Largura na Lista do Cadastro de Itens quando preenchida a op��o Exibir na Lista.".<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar mensagens de valida��o dos campos Sequ�ncia e Largura de Atributo na Estrutura para "Obrigat�rio o preenchimento do campo Sequ�ncia na Lista do Cadastro de Itens quando preenchida a op��o Exibir na Lista." e "Obrigat�rio o preenchimento do campo Largura da Coluna na Lista do Cadastro de Itens quando preenchida a op��o Exibir na Lista.".<br>
      	  	 	
  	- CORRE��ES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- N�o est� sendo poss�vel excluir manualmente as pastas do cadastro (fun��o Pastas para Anexos). Ao selecionar a pasta j� vazia e selecionar o bot�o de excluir � apresentada a mensagem: N�o � poss�vel excluir registro pois ele possui v�nculos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O Gr�fico de Evolu��o das Posi��es n�o exibe as quantidades corretamente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Fun��o "Benefici�rio" do cadastro, ao incluir um benefici�rio com quantidade prevista com um valor maior ou igual a 1000000 , o sistema grava formatando o n�mero com ponto. Ao tentar alterar o registro criado, o sistema exibe mensagem indicando que o campo est� com valor inv�lido (Valor inv�lido para o campo Quantidade Prevista).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema apresenta na listagem todas as demandas que foram cadastradas desde que se tenha pelo menos 1 item marcado na coluna manter demandas e mesmo que a coluna filtrar demandas n�o tenha nenhuma situa��o marcada. Demandas - Problemas no posicionamento do �cone de dica de campo em alguns atributos fixos e livres. <br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Demandas - Campos Org�o e Entidade: Na tela de cadastramento de demandas, ao adicionar um org�o ou entidade, o mesmo n�o recebe a caixa de checkbox, aparece somente o nome inclu�do na lista de org�o ou entidade, n�o permitindo a exclus�o. Ap�s gravar o item aparecem os checkbox para cada item. Por�m se for adicionado algum novo item n�o � poss�vel excluir os itens que possuem o checkbox, ao clicar no link excluir o sistema n�o reage.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Campos do tipo radio (sim/n�o) opcionais n�o est�o sendo gravados sem valor no cadastro de item, ou seja, mesmo clicando no bot�o limpar, o sistema continua gravando 'N�o'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao tentar excluir uma situa��o usada num parecer, o sistema exibe mensagem de valida��o com o texto do parecer.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na funcionalidade de 'Situa��o Pontos Cr�ticos', disponibilizada na barra superior da listagem geral de acompanhamentos, o sistema permite que o usu�rio selecione a op��o 'SELECIONE' no combo de refer�ncias. Ao selecionar a op��o, o sistema recarrega a tela e exibe a mensagem "erro.ObjectNotFound".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na fun��o de resumo, o sistema n�o informa corretamente a quantidade de arquivos anexos para a fun��o 'Galeria Anexo'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao clicar no bot�o cancelar, o sistema desmarca os campos de check 'Manter Demandas' mas n�o habilita os campos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema n�o est� exibindo as mensagens configurados no cadastro de popup ao efetuar login no sistema.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A consulta de �rea do site n�o est� retornando o registro 'INFORMA��ES'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na gera��o de arquivos em demandas, o caminho do arquivo de upload � exibido no arquivo exportado.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro de 'null' ao alterar um usu�rio.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema est� gerando ciclos separados por �rg�o, mesmo que o Tipo de Acompanhamento n�o esteja com a op��o 'separar por �rg�os' selecionada.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema permite que o usu�rio edite um parecer na tela de visualiza��o (clicando nas carinhas).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- H� um espa�o em branco entre a barra de bot�es e o in�cio do formul�rio de cadastro de usu�rio.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro ao tentar criar um item a partir de modelo.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na gera��o de ciclos, o sistema n�o valida corretamente os campos de datas, quando utilizado o padr�o: DD/MM/AA.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema apresenta a mensagem 'Ocorreu erro interno' ao tentar associar uma demanda a um item no cadastro de itens.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Demandas, o sistema n�o grava nome do solicitante.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ocorre erro interno de hibernate ao tentar gravar uma demanda com mais de 50 caracteres no campo 'Nome do solicitante'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema apresenta uma aba em branco no detalhamento de um item (clicar no l�pis) na tela de acompanhamento. Erro de String "" ao clicar na aba em branco.<br>
  	 	
  	</td>
  </tr>
  
  
  <tr> 
  	<td align="center">v8.6 BETA 4</td>
  	<td align="center">HEAD</td>  
  	<td align="center">12/04/2010</td>
  	<td>
  	  	  	 	
  	- CORRE��ES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No arquivo de Dados Gerais exportado est�o aparecendo os campos "Registro Ativo?" e "In�cio Monitoramento", que n�o aparecem na tela para o usu�rio.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No arquivo de Dados Gerais exportado, os atributos livres do tipo Upload est�o exibindo o caminho completo do arquivo no servidor, o que vai contra a regra de seguran�a de download rec�m implementada.<br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- As pastas criadas por usu�rios na Fun��o Pastas para Anexos s�o exclu�das automaticamente quando se exclui o �ltimo arquivo de dentro delas. Esse comportamento deve existir apenas nas pastas criadas por acompanhamentos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A configura��o "Exibir na Lista", na tela de configura��o de Atributo na Estrutura, n�o est� funcionando. Independente desta configura��o estar marcada ou n�o, o atributo em quest�o n�o � exibido na listagem do Cadastro. Por�m, se a op��o "Exibir na �rvore" for marcada, o atributo passa a ser exibido tanto na �rvore de navega��o quanto na listagem do Cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O gr�fico de Posi��es no acompanhamento n�o est� funcional. Corrigir a visualiza��o das fun��es conforme a permiss�o de acesso definida em grupo de acesso.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- As configura��es de fun��o de acompanhamento n�o est�o sendo respeitadas no cadastro e acompanhamento, exemplo: a sequ�ncia de apresenta��o n�o est� sendo respeitada, a configura��o de exibir na lista tamb�m n�o est� funcional.<br>  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Interc�mbio de Dados h� um problema ao informar * no campo "Separador de campos". O sistema entra em loop.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao alterar de uma estrutura com itens adicionando um atributo do tipo ID, o sistema exibe a mensagem de valida��o "N�o � possivel inserir atributo do tipo ID INCREMENTAL ou MASCARA EDITAVEL pois a estrutura atual j� possui itens cadastrados" e volta para a tela com os campos desabilitados. Deveria permanecer com os campos habilitados para edi��o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Fun��o "Benefici�rio" do cadastro, ao incluir um benefici�rio com quantidade prevista com um valor maior ou igual a 1000, o sistema grava formatando o n�mero com ponto. Ao tentar alterar o registro criado, o sistema exibe mensagem indicando que o campo est� com valor inv�lido (Valor inv�lido para o campo Quantidade Prevista).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Fun��o "Metas/Indicadores" do cadastro, ocorre um erro de hibernate ao tentar incluir/alterar uma meta preenchendo o campo "Quantidade Prevista" com mais de 12 d�gitos. O erro tamb�m acontece na quantidade prevista por local.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro ao tentar gerar arquivo no cadastro de demanda quando n�o tem nenhum atributo configurado na vis�o.<br>  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O Campo 'Nome' na tela de atributos na vis�o n�o � exibido de forma ordenada.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Adi��o de * no campo de vis�o na tela de Cadastramento de Demandas, por este campo ser obrigat�rio, e remo��o de * no campo "Nome" da tela de cadastramento de Vis�es. Adi��o do label "* Campos de preenchimento obrigat�rio" nas seguintes telas, conforme padr�o do e-car: cadastros de itens, restri��es e demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Tela de Demandas. Para os campos Entidades e �rg�o Solucionador, quando estes est�o apenas como exib�veis, retirar o combo de sele��o e exibir apenas o nome da entidade ou �rg�o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao tentar alterar o campo 'c�digo' da fun��o de dados gerais para um n�mero com 10 d�gitos (Ex: 9999999999), o sistema n�o grava o valor.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Cadastro de Itens, o sistema exibe erro de NullPointer ao tentar alterar um indicador, cadastrado na fun��o de metas/indicadores, ap�s o usu�rio bloquear o planejamento do item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema n�o est� filtrando as demandas quando a vis�o n�o tem nenhuma situa��o associada, ou seja, est� exibindo todas quando n�o deveria exibir nenhuma.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema n�o deveria permitir a inclus�o de atributos na vis�o com a op��o 'Edit�vel' selecionada sem que o usu�rio marcasse tamb�m a op��o 'Exib�vel'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de Atributo na Vis�o, alterar mensagem de valida��o na inclus�o de atributos obrigat�rios, informando sobre a depend�ncia com os dois campos 'Exib�vel' E 'edit�vel'. Atualmente o sistema exibe a mensagem com um 'OU': "N�o pode existir atributo obrigat�rio se n�o for edit�vel OU exib�vel".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema perde os valores dos campos 'Nome' e 'Vis�o' ao recarregar a tela de Atributos na Vis�o, ap�s erro na valida��o do campo 'Restritivo'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao gerar ciclo, o sistema n�o est� montando a hierarquia completa dos itens.  O erro acontece quando a sala de situa��o do item superior est� diferente da sala de situa��o do tipo de acompanhamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro ao clicar nos links "D�vidas Frequentes" e "Gloss�rio" que aparecem no cabe�alho.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao detalhar um item no cadastro e tentar adicionar um crit�rio, caso o usu�rio n�o selecione um dos crit�rios resultantes da pesquisa e acione o bot�o adicionar, o sistema exibe a mensagem "Selecione uma op��o!", mas ap�s isto exibe uma outra mensagem com erro: "N�o foi poss�vel acessar a funcionalidade atrav�s desta op��o escolhida.  Exce��o: For input string: ".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao detalhar uma demanda, os campos "Coment�rio" da aba Encaminhamento e "Descri��o" da aba Anexos est�o edit�veis mesmo quando a vis�o n�o est� configurada com as op��es de Alterar para Encaminhamento e Anexo Demanda.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao detalhar uma demanda, os checkbox do campo "Arquivos Anexos" s� dever�o ser exibidos quando a vis�o estiver configurada com a op��o "Excluir Anexo Encaminhamento".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Capa Portal, ao clicar na imagem de uma mat�ria ou no link "Leia mais", o sistema exibe uma tela de Erro.
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Capa Portal, ao clicar no link de uma taxa��o cadastrada, o sistema exibe tela de Erro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de Fun��o de Acompanhamento na Estrutura, remover obrigatoriedade dos campos do tipo checkBox, adicionar obrigatoriedade no campo "Sequ�ncia" (Tela de cadastramento) e alterar a mensagem de valida��o para: "Obrigat�rio preenchimento do campo sequ�ncia" (O sistema deve manter o foco no campo sequ�ncia ap�s a valida��o).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Pesquisa na Estrutura, o hist�rico de Dados Gerais est� indispon�vel a partir do item pesquisado na estrutura. O sistema exibe a mensagem: N�o foi poss�vel acessar a funcionalidade atrav�s desta op��o escolhida. Exce��o: For input string: "null" quando o bot�o hist�rico � acionado. A Altera��o do item tamb�m est� indispon�vel.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Administra��o do Portal, cadastro de Mat�rias, ao excluir as imagens anexadas a uma mat�ria os r�tulos dos campos n�o est�o se comportando como devido. Apenas o nome do campo deveria ser exibido, mas o sistema exibe "Alterar [nome do campo] para". O erro tamb�m ocorre no cadastro de Taxa��es e de Categorias.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Administra��o do Portal, cadastro de Taxa��es, ao excluir as imagens anexadas a uma taxa��o o sistema exibe a figura de imagem quebrada, junto do r�tulo "[nome do campo] Atual, com a op��o de excluir. O erro tamb�m ocorre no cadastro de Categorias.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Ciclos de revis�o (Administra��o do Sistema), na tentativa de inclus�o sem o preenchimento dos campos obrigat�rios, o sistema exibe a mensagem: "Mensagem n�o econtrada".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Ciclos de revis�o (Administra��o do Sistema), o campo "Descri��o" marcado como obrigat�rio n�o est� sendo validado. Tanto a inclus�o, quanto a altera��o com este campo em branco est�o sendo permitidas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Administra��o do Portal, cadastro de Mat�rias, ap�s excluir a imagem com sucesso, caso o usu�rio tente excluir a mat�ria, o sistema exibe a mensagem: "Ocorreu um erro interno". Al�m disso a tela de Capa Portal trava. O erro tamb�m ocorre nas telas de Taxa��o e Categorias.<br>
  	   	
  	
  	
  		
  	 	
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.6 BETA 3</td>
  	<td align="center">HEAD</td>  
  	<td align="center">26/03/2010</td>
  	<td>
  	- MANUTEN��ES:<br>
  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Impedir a exibi��o no navegador do caminho direto do arquivo no download, bem como verificar a permiss�o de acesso do usu�rio, n�o permitindo que usu�rios acessem arquivos do sistema diretamente pelo navegador.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Permitir a configura��o da exibi��o das op��es de imprimir e gerar arquivos de acordo com o Grupo de Acesso.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Disponibilizar a extra��o de dados da funcionalidade de acompanhamento de itens do e-CAR para arquivos de texto, como forma de exporta��o para uso fora da fronteira do sistema. Os arquivos ser�o gerados de acordo com as abas configuradas para o tipo de acompanhamento do ciclo de refer�ncia selecionado e com as fun��es cadastradas para cada estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a forma de exibi��o dos itens na listagem de monitoramento. Ap�s a altera��o, todos os itens dever�o ser ordenados por estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar o perfil de configura��o para importa��o de dados do PACInter para permitir a inclus�o de itens por tipo de empreendimento. Ap�s a altera��o ser� poss�vel importar itens para mais de um item da estrutura do item de n�vel superior.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a funcionalidade de importa��o manual via arquivo texto, de dados de empreendimentos do PACInter para itens de estrutura no e-CAR, distribuindo os empreendimentos nas cidades da copa cadastradas, considerando tamb�m a distribui��o dos itens importados por tipo de empreendimento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Permitir consulta aos dados de log gerados a partir do processamento do arquivo de importa��o.<br>
  	 	
  	- CORRE��ES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Caso um usu�rio liberasse um parecer emitido, somente usu�rios na Fun��o de Acompanhamento da mais alta hierarquia conseguiam recuperar o parecer.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No cadastro, ao anexar algum arquivo nos campos de upload de imagem o mesmo apresenta a imagem quebrada na tela de edi��o e s� mostra a imagem anexada ao realizar a grava��o do item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Listagem de Posi��o Geral do acompanhamento, os campos do tipo: CheckBox, ListBox, ComboBox e  RadioButton apresentam o conte�do sem o �ltimo caracter.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao visualizar determinadas vis�es os campos que foram preenchidos e gravados em outra vis�o est�o aparecendo sem conte�do. O problema ocorre ao gravar uma demanda com um campo bloqueado (n�o edit�vel).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O menu lateral esquerdo n�o est� aparecendo para o usu�rio admin. Para os demais usu�rios aparece normalmente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela inicial do cadastramento de demandas (quando esta � a tela inicial quando o usu�rio loga no sistema) o formul�rio exibe os bot�es de cancelar e consultar um em cima do outro no Firefox. Isso n�o acontece quando se clica no menu para acessar a tela.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao tentar excluir uma vis�o de demanda (que foi configurada para ser utilizada na Associ��o de demandas) o sistema exibe mensagem de erro interno de hibernate.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao tentar excluir uma situa��o de demanda (que est� sendo utilizada) o sistema exibe mensagem de erro com exce��o Java.lang.Long.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Administra��o do Portal > Taxa��o: no formul�rio de Taxa��es a mensagem de "Campos de preenchimento obrigat�rio" est� sendo exibida repetida. O mesmo ocorre nos seguintes formul�rios tamb�m em Administra��o do Portal: Perguntas Frequentes > Categorias, Itens Livres > Segmentos, Itens Livres > Categorias.<br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Uma mensagem de erro est� sendo apresentada durante a tentativa de exclus�o de algumas fun��es de acompanhamento. O mesmo erro ocorre em: Administra��o Sistema > Tabelas > Entidade > Entidade.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Est� ocorrendo um erro interno durante a navega��o na pagina��o das Mat�rias.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A partir da listagem de situa��o geral de pontos cr�ticos, os itens aparecem com link para edi��o. O link deve ser retirado, pois ao clicar nele o sistema mostra o item em edi��o mas nenhuma aba de lista fica selecionada e os bot�es avan�ar e retroceder ficam desabilitados (mesmo havendo itens).<br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na listagem geral de pontos cr�ticos, n�o est� sendo verificado se o usu�rio tem permiss�o de edi��o e/ou visualiza��o para exibir o checkbox de gera��o do relat�rio geral de ptos criticos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro de itens de estrutura (�rvore ajax). O atributo Grupo de Planejamento configurado para aparecer na listagem e na �rvore do cadastro � exibido com valor na listagem e sem valor na �rvore.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- �rvore de Ajax do cadastro. Ao clicar para detalhar um item e ent�o clicar na aba de Localiza��o Geogr�fica, a �rvore de ajax para de funcionar.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de exibi��o de abas e �cones, o sistema n�o valida o valor inserido no campo 'ordem' dos links, permitindo que o usu�rio informe valores muito grandes (o que gera erro no sistema).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro bot�o avan�ar e retroceder. Ao adicionar/alterar/excluir um item na aba de Di�rio de Bordo e depois clicar no bot�o avan�ar/retroceder, o sistema avan�a/retroceder mas encaminha o usu�rio para a aba selecionada como padr�o para a edi��o, quando deveria direcionar o usu�rio para a mesma aba do item anterior (no caso Di�rio de Bordo). O mesmo ocorre com as abas: Dados Gerais, Datas Limites, Restri��es, Apontamentos e Metas/Indicadores.<br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na gera��o de ciclos, ao alterar um ciclo para remover um acompanhamento existente, o sistema exibe a seguinte mensagem: "Existem 'x' acompanhamentos j� informados para exclus�o: 'nome dos acompanhamentos'. Confirma exclus�o?". Mensagem alterada para: "Os valores informados para o realizado f�sico dos seguintes itens ser�o exclu�dos: 'Item A', 'Item B', 'Item C'. A exclus�o poder� influenciar na visualiza��o dos valores em outros tipos de acompanhamentos associados aos itens, bem como ciclos posteriores que utilizem valores acumulados. Deseja excluir acompanhamento?"<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Aba Relat�rio Impresso. Ao clicar na op��o para selecionar as op��es de impress�o, o sistema perde as informa��es dos bot�es avan�ar e retroceder e estes aparecem desabilitados<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Aba Relat�rio Impresso. Ao clicar na aba de relat�rio impresso as demais abas ficam sem a��o. No console de erros do navegador aparece a seguinte mensagem: trocarAba is not defined.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro > Empreendimentos > "Detalhar Item" > Aba Benefici�rio. Ao tentar excluir um benefici�rio, o sistema exibe a mensagem de sucesso, mas n�o o exclui.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro > Empreendimentos > "Detalhar Item" > Aba Crit�rios. Ao tentar excluir um crit�rio, o sistema exibe a mensagem de sucesso, mas n�o o exclui.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Monitoramento > Visualiza��o - bot�o avan�ar/retroceder e link [voltar] na aba de galeria. Ao usar o bot�o avan�ar/retroceder na aba de galeria de anexos (na visualiza��o), o sistema n�o avan�a ou retrocede e o link [voltar] some da �rvore de localiza��o. O mesmo acontece em Dados Gerais.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Copiar item como modelo. Ao copiar um item, o sistema copia os arquivos anexos, por�m ao excluir o arquivo de um dos itens, o sistema apaga dos dois.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- MONITORAMENTO > REGISTRO. O bot�o recuperar abaixo do parecer n�o est� validando a hierarquia das fun��es de acompanhamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Demandas > Aba de dados gerais da demanda. Na aba que exibe os dados da demanda, os campos do tipo upload de arquivo sempre aparece em branco. Exibir o nome do arquivo, e se for imagem exibe tamb�m a imagem.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na visualiza��o de situa��o de um item, o �cone da cor exibida para a fun��o de acomp Respons�vel est� diferente do �cone configurado na tabela de cor.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Lista geral de monitoramento. Na �rvore montada na lista geral do monitoramento, os campos de texto (entrada de texto e textarea) est�o sendo exibidos com ';' ao final. O mesmo ocorre com entradas de texto com valida��o.<br>
  		
  	
  	
  	 	
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.6 BETA 2</td>
  	<td align="center">ECAR-BR-IMPORTACAO-MELHORIAS-20100305</td>  
  	<td align="center">10/03/2010</td>
  	<td>
  	- MANUTEN��ES:<br>
  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Adequa��o do layout de importa��o para inclus�o de novos campos e indica��o do item superior.<br>
  	 	
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.6 BETA 1</td>
  	<td align="center">HEAD</td>  
  	<td align="center">05/02/2010</td>
  	<td>
  	- MANUTEN��ES:<br>
  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Exporta��o de Dados do Cadastro de Itens. Disponibilizar a extra��o de dados de itens da funcionalidade de cadastro do e-Car para arquivos de texto, como forma de exporta��o para uso fora da fronteira do sistema. Os arquivos ser�o gerados de acordo com as fun��es cadastradas para cada estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Remo��o de campo indicativo de fun��o de acompanhamento em restri��es.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Remover o campo que indica a fun��o de acompanhamento relacionada ao ponto cr�tico (restri��o) cadastrada no acompanhamento. Com a altera��o o campo n�o deve aparecer dispon�vel na configura��o da estrutura.<br>
  	
  	- CORRE��ES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Internet Explorer (6.0), ao entrar na tela de Cadastro, atrav�s do menu superior CADASTROS ou login no sistema, n�o era poss�vel ordenar a listagem de itens clicando sobre os nomes das colunas. Era necess�rio navegar no cadastro (ex.: detalhar um item, acessar outra aba de estrutura) para que o sistema voltasse a permitir ordena��o da listagem clicando nos nomes das colunas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No relat�rio do Cadastro, o caracter separador de campos multivalorados aparecia no final dos campos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Caso um usu�rio liberasse um parecer emitido, somente usu�rios na Fun��o de Acompanhamento da mais alta hierarquia conseguiam recuperar o parecer. Por exemplo, criadas 3 Fun��es de Acompanhamento, com a seguinte hierarquia: Diretor -> Gestor -> Respons�vel, o parecer emitido e liberado pelo Respons�vel n�o podia ser recuperado nem pelo Respons�vel, nem pelo Gestor, somente pelo Diretor.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Mesmo que o projeto n�o possu�sse Data de In�cio informada, o sistema estava exibindo exerc�cios sem valor previsto ou realizado. <br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Havia erro ao criar um item baseado em modelo navegando pela �rea da direita . Quando a navega��o no sistema era feita pela �rea de detalhamento (�rea � direita da tela), ap�s criar um item baseado em um modelo, o sistema se perdia e, ao clicar em "Ir para Listagem", trazia todos os itens do n�vel indicado, sem pai algum. Al�m disso, caso o usu�rio sa�sse dessa tela, n�o era mais poss�vel acessar o novo item, ou mesmo saber onde ele foi criado.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Imposs�vel re-gerar Ciclo de Acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro interno do hibernate ao tentar alterar ciclo de acompanhamento.<br> 	
  	
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.5</td>
  	<td align="center">HEAD</td>  
  	<td align="center">22/01/2010</td>
  	<td>
  	- MANUTEN��ES:<br>
  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Uniformiza��o da apresenta��o do item de estrutura. Ao apresentar um item de estrutura, o sistema dever� considerar os atributos configurados para serem exibidos na �rvore.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configura��o da aba de situa��o geral de indicadores. Retirar as abas referentes � situa��o geral de indicadores das telas de acompanhamento  e o acesso a sua configura��o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Reformula��o das abas Situa��o e Resumo. Apresentar na aba de situa��o a data limite do parecer para cada fun��o de acompanhamento; Reformular a apresenta��o da aba de resumo.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Apresenta��o de t�tulo na coluna de �rg�o na listagem de itens em acompanhamento. Retirar o r�tulo ': Todos' da coluna associada a �rg�o na listagem de itens de acompanhamento, apresentado apenas o nome configurado para a coluna.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ordena��o de itens de estrutura na �rvore de navega��o. Os itens de estrutura na �rvore de navega��o devem ser apresentados ordenados, considerando apenas o primeiro atributo vis�vel na �rvore.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ordena��o em Situa��o Geral de Pontos Cr�ticos. Ordena��o decrescente de pontos cr�ticos, na listagem dos itens acompanhados, atrav�s do atributo data limite; Se existirem pontos cr�ticos com datas limites iguais , eles ser�o ordenados de acordo com o conte�do da 1� coluna, seguindo a ordem alfab�tica; Se existirem pontos cr�ticos sem data limite, eles ser�o exibidos ap�s os pontos cr�ticos que possuem data limite e ordenados de acordo com o conte�do da 1� coluna, seguindo a ordem alfab�tica.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- �rvore Ajax e Filtros. Filtragem dos itens contidos na �rvore de navega��o (com Ajax) e na �rvore de localiza��o do cadastro de itens e a manuten��o da op��o de filtro ("Conclu�dos" ou "N�o Conclu�dos" ou "Todos") selecionada pelo usu�rio enquanto este navegar pelas funcionalidades de cadastro de itens.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Recolhimento de Datas Cr�ticas em Situa��o Geral de Pontos Cr�ticos. Possibilidade de agrupar/desagrupar os pontos cr�ticos de um item espec�fico na listagem dos itens acompanhados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ordena��o em Situa��o Geral de Pontos Cr�ticos. Ordena��o decrescente de pontos cr�ticos, na listagem dos itens acompanhados, atrav�s do atributo data limite; Se existirem pontos cr�ticos com datas limites iguais , eles ser�o ordenados de acordo com o conte�do da 1� coluna, seguindo a ordem alfab�tica; Se existirem pontos cr�ticos sem data limite, eles ser�o exibidos ap�s os pontos cr�ticos que possuem data limite e ordenados de acordo com o conte�do da 1� coluna, seguindo a ordem alfab�tica.<br>  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Regras para Super-Fun��o. Armazenar o c�digo da fun��o associada ao usu�rio que altera (emite, libera ou recupera) o parecer, pois em um outro momento, esse usu�rio pode precisar alterar esse parecer e n�o estar mais associado a fun��o de acompanhamento armazenada; Retirar da tela de configura��o de fun��o de acompanhamento a sele��o autom�tica das fun��es filhas de uma fun��o que for selecionada para o campo "Pode alterar parecer de fun��o filha".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Corre��es e Melhorias no cadastro de atributos livres. Em todas as telas de grupo de atributo que exibe o indicador de uso no cadastro do usu�rio, o campo referente ao indicador de uso no Meu Cadastro (indicador de cadastro no site, sim ou n�o) dever� ser exibido. Este campo s� ser� habilitado se o grupo de atributo for configurado para ser utilizado no cadastro de usu�rio. Al�m disso, o campo que indica o tipo de exibi��o no cadastro de usu�rio tamb�m dever� ser exibido.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Tabela de Gr�fico de Gantt. Exibir os pontos cr�ticos ordenados no gr�fico de Gantt, no cadastro e no acompanhamento, segundo a ordem decrescente de data limite informada; Exibir apenas os pontos cr�ticos com data limite informada; A mensagem "N�o h� restri��es cadastradas com data limite informada." ser� exibida ao usu�rio caso a aba do gr�fico da Gantt seja acionada e as restri��es cadastradas n�o possuam data limite informada.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Anexa��o de arquivos. Padronizar no sistema a forma de apresenta��o para anexar arquivos; Gerar nome interno ao sistema para evitar problemas com anexa��o de mais de um arquivo utilizando mesmo nome; Permitir a exclus�o de arquivos anexados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configura��o da Aba de Resumo. Tornar o acesso a registro e visualiza��o do acompanhamento configur�vel, ou seja, definir atrav�s do sistema para que aba direcionar o usu�rio quando o mesmo acessar registro e/ou visualiza��o do acompanhamento; Para as op��es de avan�ar e retroceder para itens no acompanhamento, direcionar sempre o usu�rio logado para a mesma aba em uso no item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Corre��es e Revis�o das Regras de Metas/Indicadores. Validar a regra de exibi��o dos exerc�cios na tela de resultados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Altera��o de R�tulo na Configura��o de Fun��o de Acompanhamento. Tornar os r�tulos "Ativar Monitoramento" e "Desativar Monitoramento" configur�veis<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configura��o de perfil para importa��o de empreendimentos do PACInter. Disponibilizar configura��o para importa��o de dados do PACInter para o e-CAR atrav�s de um perfil pr�-definido.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Adicionar valida��o na exclus�o de situa��o de item. Impedir que uma situa��o de item associada a um perfil de configura��o de interc�mbio de dados seja exclu�da.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Adicionar valida��o na exclus�o de �rg�o. Impedir que um �rg�o associado a um perfil de configura��o de interc�mbio de dados seja exclu�do.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Adicionar valida��o na exclus�o de estrutura. Impedir que uma estrutura associada a um perfil de configura��o de interc�mbio de dados seja exclu�da.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Adicionar valida��o na exclus�o de usu�rio. Impedir que um usu�rio associado a um perfil de configura��o de interc�mbio de dados seja exclu�do.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Importar empreendimentos do PACInter. Disponibilizar a funcionalidade de importa��o manual via arquivo texto, de dados de empreendimentos do PACInter  para itens de estrutura no e-CAR, distribuindo os empreendimentos nas cidades da copa cadastradas.<br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Uniformiza��o da apresenta��o dos caracteres separadores dos campos multi-valorados na listagem do Cadastro. O sistema passou a utilizar o campo "Caracter Separador para Campos Multivalorados", informado em Configura��o Geral, na listagem do Cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Uniformiza��o da apresenta��o dos caracteres separadores dos campos multi-valorados na gera��o de relat�rios. O sistema passou a utilizar o campo "Caracter Separador para Campos Multivalorados", informado em Configura��o Geral, na gera��o de relat�rios do Cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Remo��o da linha exibida com o resumo dos pareceres durante a gera��o de relat�rios na tela de acompanhamentos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Remo��o da faixa em branco apresentada no formul�rio da fun��o de anexa��o de arquivos ("Pastas para Anexos").<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Aplica��o de nova regra na manuten��o de ponto critico em Acompanhamento. No acompanhamento, ap�s liberar o parecer, o sistema deixava de apresentar os bot�es de cria��o e de altera��o dos pontos cr�ticos cadastrados por meio do acompanhamento, somente sendo poss�vel alterar/criar pelo cadastro. A cria��o e altera��o dos pontos cr�ticos deve ser independente da libera��o ou n�o dos pareceres, respeitando somente a permiss�o do usu�rio quanto � altera��o / exclus�o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Altera��o da regra de apresenta��o do bot�o "usar modelo". Ao inv�s de apresentar o bot�o se o atributo "� modelo" estiver sendo utilizado na estrutura, foi criado um checkbox na configura��o da cria��o da estrutura para indicar a apresenta��o do bot�o ou n�o (assim como os checkbox para exibir o bot�o de gera��o de arquivos e bot�o de imprimir).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Implementa��o de melhorias na Seguran�a do Sistema:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Removido o acesso a pastas n�o permitidas via a utiliza��o das cadeias de caracteres "../" ou "..\\" de navega��o em sistemas de arquivos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Autentica��o de quem estava tentando fazer o download n�o era feita. Est� sendo verificado se h� usu�rio na sess�o e se ele est� logado quando se tenta acessar diretamente o arquivo via URL.<br>
  	
  	  	
  	- CORRE��ES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O filtro de Entidade Solucionadora, no cadastro de demandas, n�o funcionava corretamente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de exibi��o de anexos de demandas, o bot�o excluir estava sempre presente, mesmo que a vis�o em quest�o n�o permitisse excluir anexos (no Internet Explorer, o bot�o gerava um erro na p�gina).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Caso o usu�rio n�o possu�sse nenhuma entidade solucionadora informada em seu cadastro, ocorria erro interno ao detalhar uma vis�o que utilizasse o filtro de 'entidade solucionadora'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Os campos 'status' e 'prioridade' permaneciam edit�veis na demanda, mesmo que n�o estivessem marcados como edit�veis na configura��o de Atributo na Vis�o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Cadastro de Itens, caso o campo 'Nome' de um item possu�sse mais de uma linha, o sistema removia a quebra de linha, exibindo o texto sem espa�os.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A op��o 'alterar anexo de encaminhamento', da configura��o de vis�es, n�o tinha utilidade. Existindo apenas essa permiss�o selecionada, nenhuma a��o era poss�vel sobre os arquivos anexados (op��o removida).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema n�o permitia que o usu�rio alterasse uma descri��o de um arquivo anexado.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro na exibi��o do nome do item apresentado no pop-up de anota��es na listagem do acompanhamento. Adi��o de h�fen entre o nome da estrutura e o nome configurado para o item de estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Removida configura��o da aba Situa��o Indicadores da tela de grupos de acesso.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O valor do campo 'Cadastro do Site' dos atributos livres do cadastro de usu�rio n�o era exibido na tela de altera��o de um registro existente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtro de Pesquisa Personalizada (Acompanhamento) n�o executava nenhuma a��o quando o bot�o de avan�ar era acionado antes da p�gina estar completamente carregada. Foi inserida imagem com mensagem "Aguarde, carregando..." para ser exibida at� que a p�gina estivesse totalmente carregada, a exemplo do que ocorre na tela de listagem do cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ordena��o da �rvore AJAX n�o funcionava corretamente. Quando configurado outro atributo para ser exibido na primeira posi��o da �rvore, com exce��o do nome, a �rvore n�o era ordenada corretamente. Al�m disso, caso um atributo n�o possu�sse valor o sistema n�o colocava os hifens.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Revisadas todas as possibilidades de defini��o da fun��o a ser direcionada ao acessar o registro ou visualiza��o no acompanhamento. Por exemplo, ao definir como fun��o inicial a aba de dados gerais na visualiza��o, o sistema apontava para uma p�gina inexistente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acrescentada a aba de Resumo na tela de Exibi��o de Abas e �cones, pois do contr�rio ela nunca poderia ser configurada como 1� aba da tela de registro ou visualiza��o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em grupo de acesso era apresenta a possibilidade de utilizar uma aba sem correspond�ncia com as fun��es do cadastro e acompanhamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao gerar um relat�rio no acompanhamento, os atributos livres estavam vindo com o conte�do null.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O campo de Data Limite foi posicionado abaixo do campo de observa��es, na fun��o de Situa��o dos Pareceres.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A linha Data Limite - F�sico - correspondia exatamente a data limite da libera��o das metas-indicadores, portanto tornou-se uma linha com informa��o duplicada. A linha foi removida.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Foi alinhado o label com o campo referente � apresenta��o da sinaliza��o atual e da sinaliza��o at� uma data espec�fica, na tela de Situa��o de Pontos Cr�ticos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao gravar um ponto cr�tico, o campo estados e envio de e-mails estava sendo preenchido com os valores 0 e sendo ativada a configura��o de envio de e-mails. O comportamento normal seria de n�o preencher nenhum valor caso n�o fosse informado pelo usu�rio. Desta forma, na altera��o do item acusava o erro de preenchimento de valores iguais para as cores diferentes e ainda habilitava o envio de e-mails.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Hifens estavam sendo utilizados para separar valores de um mesmo campo, na uniformiza��o da apresenta��o de um item. O sistema passou a utilizar o caracter de separa��o informado em Configura��o Geral.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Acompanhamento, o sistema n�o permitia altera��o da descri��o de anexo de acompanhamento. Ao clicar sobre o nome do anexo para alter�-lo, o sistema exibia a tela com todos os campos vazios, permitindo a cria��o de um novo anexo para substituir o existente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ap�s detalhar um item no Acompanhamento, se o usu�rio clicasse em "Lista Geral", o filtro de N�vel de Planejamento era perdida.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Acompanhamento, caso o detalhamento de um item fosse feito atrav�s da op��o visualizar, os bot�es "Avan�ar" e "Retroceder" levavam sempre para a aba "Resumo", independente de qual estivesse ativa no momento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Acompanhamento, ao se detalhar um item que possu�sse restri��es, a aba "Restri��es" n�o ficava marcada como se estivesse ativa. Caso o detalhamento fosse feito atrav�s da op��o visualizar, ao se clicar nos bot�es "Avan�ar" ou "Retroceder", os mesmos passavam a ficar desabilitados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Cadastro, mesmo que os filtros "Todos" ou "Conclu�dos" fossem definidos como padr�o na configura��o geral, o sistema exibia na �rea da direita apenas os itens n�o conclu�dos. Era necess�rio clicar sobre o filtro "Todos" para que o sistema exibisse realmente todos os itens.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema n�o permitia alterar alguns ciclos de acompanhamento. O bot�o "Alterar Acompanhamento" n�o executava a��o alguma. Novos ciclos de acompanhamento criados podem ser alterados normalmente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na lista geral de Situa��o de Pontos Cr�ticos no Acompanhamento, no Internet Explorer o sistema n�o identava itens pais e filhos, ficando todos alinhados no mesmo n�vel. Al�m disso, o alinhamento do primeiro item na tela n�o seguia o dos demais itens, tanto no Internet Explorer quanto no Firefox.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Cadastro, usando o Firefox, ao tentar fazer upload de um arquivo em um item atrav�s de um atributo livre do tipo anexo, o sistema exibia a mensagem "Erro interno no Hibernate". Usando o Internet Explorer, o sistema permitia a inclus�o, por�m exibia a mensagem "Ocorreu um erro interno no Hibernate" ap�s o usu�rio excluir o anexo e tentar gravar o item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Acompanhamento, tela de resultados, caso um item n�o possu�sse valor na sua Data de In�cio, o sistema n�o exibia os exerc�cios que tinham apenas valores realizados, nem considerava esses valores na totaliza��o. Al�m disso, o sistema estava exibindo todos os exerc�cios at� a Data de T�rmino, mesmo os que n�o possu�am valor algum; quando deveria exibir somente os exerc�cios com valores previstos ou realizados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Caso um usu�rio liberasse um parecer emitido, somente usu�rios em Fun��es de Acompanhamento superiores conseguiam recuperar o parecer.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O filtro de Demandas n�o estava funcionando corretamente. Ao acionar a op��o "Filtrar", caso n�o fossem adicionados �rg�o solucionadores ao filtro, o sistema retornava a mensagem "Nenhum registro foi encontrado para os crit�rios de filtro especificados.", mesmo que existissem demandas que satisfizessem os filtros.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Cadastro, a listagem de itens � ordenada pelo primeiro campo da lista. Ao clicar no nome do campo (coluna), o sistema tamb�m ordena a listagem. Por�m, essas ordena��es estavam diferentes, embora devessem usar os mesmos crit�rios de ordena��o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na ordena��o da listagem do cadastro, n�o estava mais funcionando a ordena��o decrescente ao clicar duas vezes em alguma coluna do cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Corre��es realizadas nas tabelas de cores. Verificada a tela de configura��o das cores do sistema, que estava definindo como obrigat�rio o preenchimento de todos os campos da tabela. Verificada tamb�m a chamada do link de cores para a sinaliza��o dos pareceres.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Corre��o na Recupera��o de Parecer. N�o estava sendo poss�vel a grava��o nem a recupera��o dos pareceres emitidos. O sistema apresentava o erro "null".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Corre��o de erro ao abrir um anexo na galeria de anexos. No detalhamento de um acompanhamento, ao clicar na fun��o "Galeria" e selecionar a op��o para detalhar uma pasta, o sistema n�o exibia corretamente o popup de detalhamento da pasta, com as imagens pertencentes � pasta.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Problemas na cria��o de um item a partir do modelo foram corrigidos:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao criar um novo item baseado em um modelo, o item criado apresentava o campo "� modelo" marcado como sim, dessa forma criava um novo modelo a cada novo item criado. Este campo deveria vir em branco para os novos itens criados a partir de um modelo.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Quando o usu�rio criava um novo item a partir de um modelo, o novo item era sempre criado embaixo da estrutura pai do item que serviu de modelo.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Acompanhamento, tela de resultados, caso um item n�o possu�sse valor na sua Data de In�cio, o sistema n�o exibia os exerc�cios que tinham apenas valores realizados, nem considerava esses valores na totaliza��o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Corre��o na a tela de 'Situa��o', no Registro de acompanhamentos, exibindo o bot�o Recuperar, sempre que o usu�rio for respons�vel por uma fun��o de acompanhamento, mas um usu�rio de n�vel superior liberou o parecer. O sistema exibir� o bot�o para recuperar o parecer, e, caso o usu�rio clique no bot�o, a seguinte mensagem ser� exibida: "O PARECER s� pode ser recuperado por fun��o igual ou superior a que liberou".<br>
  	
  	</td>
  </tr>
  
  
  <tr> 
  	<td align="center">v8.4.5</td>
  	<td align="center">ECAR-BR-08-04-00-20091023</td>  
  	<td align="center">26/03/2010</td>
  	<td>
  	
  	- MANUTEN��ES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configurar na vis�o a exibi��o de situa��es dispon�veis para as demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Modificar padr�o de ordena��o dos atributos listados no detalhamento de demandas.<br>
  	- CORRE��ES DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao tentar incluir uma vis�o com o mesmo nome de uma existente, o sistema apresentava a mensagem "J� existe um Atributo com o nome ou o label padr�o informado."  Este comportamento tamb�m ocorria durante a altera��o.<br>
	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.4.4</td>
  	<td align="center">ECAR-BR-08-04-00-20091023</td>  
  	<td align="center">26/02/2010</td>
  	<td>
  	
  	- CORRE��ES DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Os atributos livres definidos como n�o edit�veis em uma vis�o est�o tendo seus valores sobrescritos com valor em branco quando h� grava��o da demanda.<br>
	</td>
  </tr>
  
   <tr> 
  
  <tr> 
  	<td align="center">v8.4.3</td>
  	<td align="center">ECAR-BR-08-04-00-20091023</td>  
  	<td align="center">08/12/2009</td>
  	<td>
  	
  	- CORRE��ES DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao selecionar mais de uma Prioridade no Filtro de 'Cadastramento de Demandas', o sistema apresenta a mensagem "ocorreu um erro interno".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema n�o filtra corretamente ao selecionar m�ltiplas op��es nos filtros de checkbox de atributos fixos de demandas.<br>
	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.4.2</td>
  	<td align="center">ECAR-BR-08-04-00-20091023</td>  
  	<td align="center">27/11/2009</td>
  	<td>
  	
  	- CORRE��ES DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Quando o atributo "Nome" de um item tem mais de uma linha, ao clicar no "+" para listar, o sistema fica eternamente na tela de Aguarde, carregando...<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema n�o permite que o usu�rio exclua itens de uma estrutura de primeiro n�vel. Mesmo que o usu�rio selecione um item da estrutura e clique na op��o 'Excluir' o sistema exibe a mensagem: "Pelo menos um registro deve ser selecionado".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O filtro de entidade solucionadora no cadastro de demandas n�o est� funcionando corretamente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de exibi��o de anexo de demanda, o bot�o Excluir est� sempre presente, mesmo que a vis�o em quest�o n�o permita excluir anexos. No Internet Explorer, o bot�o gera um erro na p�gina.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Quando um usu�rio que n�o possui Entidade Solucionadora no seu cadastro tenta acessar uma Vis�o que utiliza regra de Entidade Solucionadora, o sistema exibe um erro interno.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Os campos Status e Prioridade ficam sempre edit�veis na demanda, mesmo que n�o tenham sido marcados como edit�veis na configura��o de Atributo na Vis�o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Quando o campo Nome de um item possui mais de uma linha, o sistema est� juntando o texto do final da linha com o do in�cio da pr�xima linha.<br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Op��o alterar anexo de encaminhamento da configura��o de 'vis�es' n�o tem utilidade. Caso s� essa permiss�o seja marcada para anexos de encaminhamento, n�o � poss�vel executar a��o alguma sobre ele.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Sistema n�o permite que o usu�rio altere uma descri��o de um arquivo anexo.<br>
	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.4.1</td>
  	<td align="center">ECAR-BR-08-04-00-20091023</td>  
  	<td align="center">27/10/2009</td>
  	<td>
  	
  	- CORRE��ES DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao mudar o filtro, perde-se a refer�ncia da �rvore de navega��o e os itens apresentados n�o s�o filtrados conforme a hierarquia definida, s�o apresentados todos os itens cadastrados para a estrutura que estava sendo visualizada.<br>
  	
	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.4</td>
  	<td align="center">HEAD</td>  
  	<td align="center">01/10/2009</td>
  	<td>
  	- MANUTEN��ES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Altera��o na rotina de grava��o de indicadores autom�ticos. Esta altera��o teve como finalidade garantir que os indicadores dos acompanhamentos, definidos para utilizar servi�os de forma autom�tica e que tenham data limite para informar realizado f�sico expirada, sejam gravados diariamente. A grava��o deixou de ser vinculada ao envio de e-mail, passando a ser executada de forma independente sempre antes da rotina de envio de e-mails, quando esta estiver configurada para ser executada no mesmo dia.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Altera��o nos Componentes de Edi��o de Textos. Os textos copiados de fontes externas ao sistema e colados nos componentes de edi��o devem manter sua formata��o original. Os componentes de edi��o n�o exibiam ou exibiam com erros os campos nos quais uma p�gina da internet ou trechos de texto do BROffice eram copiados e colados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Altera��o na forma de associa��o de demandas aos itens do cadastro de itens. Com a altera��o, ser� necess�rio que o usu�rio informe, na tela de configura��o geral do sistema, qual vis�o de demandas ser� utilizada durante a associa��o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Nova regra para os n�meros informados no campo "Anteced�ncia (em dias)" em Restri��es, constante na tabela de Estados e Envio de e-mails. Os n�meros informados para cada estado dever�o ser distintos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Altera��o na exibi��o de resumo dos pareceres das fun��es de acompanhamento, na aba de "Resumo".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Inclus�o do r�tulo "Data Limite: " antes das datas limites associadas aos pareceres. <br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em cada fun��o de acompanhamento com parecer liberado, apresenta��o da data do parecer logo ap�s o �cone associado ao mesmo, utilizando o r�tulo: "- Liberado em: " entre o �cone e a data associada ao parecer.<br>
  	
  	- CORRE��ES DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Apresenta��o de dicas na fun��o 'Dados Gerais'. As dicas de alguns tipos de atributos livres n�o estavam sendo exibidas na fun��o 'Dados Gerais'. O problema tamb�m ocorria na tela de hist�rico da fun��o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ativa��o de priorit�rio quando existem campos obrigat�rios bloqueados. O sistema n�o validava corretamente quando o usu�rio selecionava a op��o para 'ativar priorit�rio' na fun��o 'Dados Gerais'. N�o era poss�vel ativar priorit�rio quando existiam campos obrigat�rios bloqueados. Foi utilizada a mesma regra do bot�o Bloquear/Liberar Planejamento para n�o validar campos obrigat�rios ao acion�-lo.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Valida��o de Datas na fun��o 'Restri��es'. O sistema n�o fazia valida��o quando o usu�rio informava no campo 'Anteced�ncia em dias' uma data posterior a data definida no campo 'Data Limite'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Corre��o na valida��o de filtros do tipo data em Acompanhamento. A valida��o do sistema nos campos data n�o permitia que o usu�rio realizasse consultas utilizando data inicial igual a data final.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configura��o de atributos restritivos em demandas. Para algumas situa��es o atributo definido como restritivo (em atributo na vis�o) n�o estava sendo exibido corretamente no cadastro de demandas. Isso ocorria com as seguintes combina��es no cadastro de grupos de atributo livre:
									Tipo de Exibi��o Cadastro de Demandas - ComboBox (aparecia checkbox)
									Tipo de Exibi��o Cadastro de Usu�rio - ListBox
									e
									Tipo de Exibi��o Cadastro de Demandas - ComboBox (aparecia checkbox)
									Tipo de Exibi��o Cadastro de Usu�rio - CheckBox.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Corrigida a oculta��o indevida do cabe�alho na listagem de pontos cr�ticos. Na listagem geral de restri��es, em acompanhamento, havia situa��es em que o cabe�alho da listagem de pontos cr�ticos n�o aparecia, mesmo que o item possu�sse pontos cr�ticos. Essa situa��o ocorria dependendo do filtro de estado dos pontos cr�ticos setado na parte superior da tela.<br>								
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Mudan�a na forma de altera��o de ciclos gerados para corre��o de erro de produ��o. Alguns acompanhamentos gerados n�o apresentavam, na aba 'datas limites', as datas limites de todas as fun��es de acompanhamento utilizadas para o monitoramento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Corre��o na altera��o de ciclos gerados, permitindo ao usu�rio retirar o acompanhamento de itens conclu�dos. Durante a altera��o, o sistema desabilitava os itens com situa��o 'conclu�do' e acompanhamento gerado.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Exclus�o de pasta criada na fun��o "pastas para anexos" no cadastro de itens, quando o �ltimo arquivo anexo da pasta for exclu�do.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Durante a digita��o do parecer na fun��o de acompanhamento, se o usu�rio apertasse ENTER ap�s o final do texto e acionasse a op��o de gravar, ao acessar a aba Galeria nenhuma das quatro pastas fixas aparecia.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na gera��o de ciclo de acompanhamento, n�o � exibido o componente de calend�rio para os prazos das Fun��es de Acompanhamento. O componente tamb�m n�o � exibido nos campos de data de filtros no Acompanhamento, quando usado Internet Explorer (vers�o 6.0).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema permite a indica��o de valores negativos para a anteced�ncia de envio de e-mails em Restri��es.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Pareceres emitidos usando o Internet Explorer (vers�o 6.0) s�o exibidos com tags html no seu texto. Ex.: Cada enter � exibido como &lt;p&gt; . No relat�rio em pdf, o caracter &lt; � substitu�do por <i>&amp;lt;</i>, por�m o caracter > permanece.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No relat�rio de acompanhamento, caracteres especiais como aspas, "h�fen grande" e marcadores colados tanto do Word quando do Writer n�o est�o sendo exibidos corretamente.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O relat�rio de acompanhamento s� est� sendo gerado para o primeiro Tipo de Acompanhamento exibido na tela. Ao tentar gerar os relat�rios para os demais Tipos de Acompanhamento, os dados apresentados s�o os mesmos do primeiro Tipo de Acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- N�o se consegue gerar exporta��o em txt para os acompanhamentos. As op��es Conselho Revisor e Acompanhamento do menu Relat�rios apresentam os seguintes problemas: Ap�s escolher a Refer�ncia desejada, os tipos de Emiss�o n�o est�o filtrando corretamente. Emiss�o por �rg�o, Estrutura ou situa��o diz que nenhum registro foi encontrado. Gera��o de arquivo .txt traz uma tela vazia. Clicar em alguma aba de Tipo de Acompanhamento leva para a tela de p�gina n�o cadastrada no Sentinela.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No acompanhamento, a funcionalidade Situa��o dos Pontos Cr�ticos n�o mostra a descri��o dos Pontos Cr�ticos de itens filhos.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Quando informado valor muito grande (ex.: 999999), no campo anteced�ncia de envio de e-mails em Restri��es, o sistema passa a exibir 0 no campo. Verificar como o sistema est� interpretando isso e, se for o caso, limitar o n�mero de caracteres do campo.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No acompanhamento, a funcionalidade Situa��o dos Pontos Cr�ticos n�o mostra a descri��o dos Pontos Cr�ticos de itens filhos. Aparentemente, o sistema exibe apenas a descri��o do primeiro Ponto Cr�tico dos itens filhos.<br>
	</td>
  </tr>
   
  <tr> 
  	<td align="center">v8.3</td>
  	<td align="center">HEAD</td>  
  	<td align="center">15/07/2009</td>
  	<td>
  	- MANUTEN��ES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Acesso �s informa��es de hist�rico para fins de acompanhamento e auditoria;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Inclus�o de link de refer�ncia na mensagem recebida via Expresso a respeito dos projetos;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Cria��o de campos de id que n�o permitam dois registros com mesmo valor na mesma lista;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Possibilidade de ordena��o de itens de uma lista num�rica sem necessidade de utiliza��o de zeros � esquerda;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-N�mero de identifica��o composto por ano corrente e n�mero incremental (mascara utilizada para aquisi��es - HSAQ).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Possibilidade de cria��o de escrit�rios de suporte e controle criarem itens de estrutura nas suas superintend�ncias para agrupamento de projetos que seriam os programas e portfolios (Estruturas Virtuais)	.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Implementar em filtros um sistema de busca por intervalo de requisitos (datas, v�rias situa��es, intervalo de valores,etc).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Cria��o de atributos livres na funcionalidade de pontos cr�ticos, com possibilidade de bloqueio de campo para possibilitar a preserva��o da informa��o da data planejada inicialmente para o marco. Deve permitir atributos livres no acompanhamento (Aba Situa��o) nos pareceres.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Possibilidade de gera��o de novo projeto baseado em projeto existente, como modelo.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Configura��o dos campos que devem aparecer no detalhamento da demanda; Cria��o de um novo momento para a fun��o de encaminhamento fora do nivel de cadastro (Vis�es como filtros das situa��es das demandas).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Reformula��o da listagem de monitoramento e abas do detalhamento do item.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Corre��o de erro na aba de restri��es do item: Ao clicar em uma restri��o para detalhamento o sistema d� erro indicando que o usu�rio n�o tem acesso � p�gina.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Reformula��o da tela de exibi��o de abas, que passa a se chamar Exibi��o de Abas e �cones.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Revis�o das abas de listas. Atualmente estas abas listam todas as situa��es (aba "Lista Geral"), situa��es das restri��es (aba "Situa��o Restri��es") e situa��es dos pontos cr�ticos (aba "Situa��es Pontos Cr�ticos") dos itens do tipo acompanhamento que est� sendo visualizado.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Habilita��o para informar o campo N�vel Superior na pesquisa da tela de estrutura.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Artigos no Portal:
   									Erro ao inserir um artigo. Em "Administra��o do Portal > Artigos > Mat�rias", cadastrar um novo artigo escolhendo-o como destaque, selecionando o "Leiaute" que usa tr�s imagens, e anexando todas as poss�veis imagens. O sistema indica que a inclus�o foi realizada com sucesso, mas ao consultar o artigo, a "Imagem 2" n�o aparece enquanto que as demais aparecem.
  									Em "Informa��es" (link no menu superior),  detalhar o artigo que est� como destaque. Quando o artigo � cadastrado com o leiaut que usa mais de uma imagem o sistema exibe uma tela de erro indicando que n�o p�de achar a p�gina.
  									No link em "Mais Artigos", na tela do Portal listar todos os artigos. Listar todos os artigos que foram cadastrados, 
  									inclusive os artigos cadastrados como destaque;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Relat�rios / Lista de usu�rios -> Substitui��o da gera��o do arquivo CSV pela gera��o de um arquivo txt 
  									atrav�s da remo��o da classe "com.Ostermiller.util.ExcelCSVPrinter";<br> 
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Atualiza��o dos valores previstos por local no cadastro de metas/indicadores -> Substitui��o da classe
  									"com.Ostermiller.util.StringTokenizer" pela classe "java.util.StringTokenizer".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Altera��o na �rvore de Ajax da listagem de itens no cadastro:<br>

   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Permitir que a exibi��o da �rvore em Ajax no cadastro seja configur�vel, ou seja, permitir ao administrador de configura��o habilitar ou n�o o uso dessa estrutura. A exibi��o da �rvore de Ajax dever� ser configur�vel (Configura��o Geral do Sistema, op��o "Exibir �rvore de Navega��o"). Independente da op��o de configura��o (exibi��o ou n�o da �rvore em Ajax), a �rea da listagem de itens do cadastro dever� ser exibida sempre da mesma forma exibindo inclusive as Estruturas em abas.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Links na �rvore em Ajax: (1) ao clicar em um item de estrutura, o sistema dever� detalhar o item (ir para aba de Dados Gerais), (2) ao clicar no �cone "+", o sistema dever� expandir o item listando os respectivos filhos.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Ao expandir uma estrutura virtual na �rvore o sistema deve exibir os itens agrupados pelas estruturas reais que contem os itens.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Detalhamento de um item de estrutura no cadastro: na �rvore de navega��o exibida nas telas (abas) de detalhamento de um item de estrutura, exibir o link "Ir para o pr�ximo n�vel".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Associa��o de Itens a Estrutura Virtual: no pop-up que exibe o resultado da consulta de itens para associa��o de itens a uma estrutura virtual, considerar a configura��o do atributo na estrutura (campo: Exibir na �rvore) para a exibi��o do item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-No cadastro de estruturas, campo relativo a configura��o de N�vel Superior, o sistema n�o deve exibir como op��o as estruturas virtuais criadas. Uma Estrutura Virtual n�o pode ser pai de outra estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tela de listagem de itens em Acompanhamento, o sistema deve exibir apenas as abas dos tipo de acompanhamento para os quais o usu�rio logado tenha permiss�o de leitura para pelo menos uma aba do item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tela de sele��o de filtros no Acompanhamento, ao ser selecionada a op��o personalizada (n�o geral) o sistema deve exibir para sele��o somente as fun��es de acompanhamento que o usu�rio logado tem permiss�o de visualizar.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tela de emiss�o do relat�rio no Acompanhamento, na sele��o das op��es para impress�o, o sistema deve exibir para sele��o somente as fun��es de acompanhamento que o usu�rio logado tem permiss�o de visualizar.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-No Acompanhamento, emiss�o de parecer: excluir pastas para anexos criadas em um acompanhamento quando for exclu�do o �ltimo arquivo anexado em um acompanhamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Utiliza��o do padr�o 'Nome' seguido por 'Label' das estruturas entre par�ntesis nos seguintes locais:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Nome da estrutura na �rvore de navega��o do cadastro;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Hint da �rvore de navega��o do cadastro;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Nome da estrutura na �rvore de navega��o do detalhamento de um item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Listar, na barra superior contendo as estruturas cadastradas no sistema (detalhamento de uma estrutura no cadastro de itens), as estruturas por Label ou nome, este �ltimo apenas quando n�o houver label informado.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Abaixo da barra de estruturas, foi inserida uma linha com o nome da estrutura que est� sendo detalhada.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Manter a configura��o de cor de fundo do t�tulo, definida no cadastro de estruturas, nas linhas contendo os bot�es de adi��o/inser��o e exclus�o e na nova linha inserida (que cont�m o nome da estrutura).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Altera��o na forma de utiliza��o do pop-up que associa itens � estruturas virtuais para n�o permitir navega��o do usu�rio na inst�ncia do browser que originou a cria��o do pop-up.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Altera��o no cadastro de estruturas para n�o mais possibilitar a cria��o de uma estrutura virtual. As estruturas virtuais j� presentes na base permanecem.<br>
  	
  	- CORRE��O DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Caso o administrador do sistema tenha alterado um atributo de uma estrutura para ser obrigat�rio, os itens que j� tenham sido bloqueados e que o atributo alterado n�o possua valor ir� falhar na valida��o quando o usu�rio tentar desbloquear o item.(HOMOLOGA��O)<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-O sistema n�o ordena corretamente quando o usu�rio clica em deteminada coluna. A ordena��o leva em considera��o as letras maiusculas e minusculas, fazendo com que a ordena��o n�o seja realizada de forma correta.(HOMOLOGA��O)<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-O sistema insere espa�os desnecess�rios no campo de texto na hora de incluir um anexo em demandas.(HOMOLOGA��O)<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-O campo dica dos atributos livres n�o s�o apresentados na tela de hist�rico (tela de compara��o).(HOMOLOGA��O)<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando o usu�rio tenta anexar um arquivo na tela de demandas o texto em edi��o � perdido.(HOMOLOGA��O)<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Nas pesquisas salvas do filtro de monitoramento, corrigir o texto que aparece antes do link de excluir. O texto apresentado � "�ltimo Refer�ncia". Corrigir para "�ltima Refer�ncia".(HOMOLOGA��O)<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na listagem em monitoramento o filtro por cor n�o est� considerando a libera��o do acompanhamento.(HOMOLOGA��O)<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Ao gerar ciclo est� sendo exibida a mensagem de solicita��o de configura��o de estrutura, mesmo ela n�o pertencendo � hierarquia estabelecida na configura��o do tipo de acompanhamento, atrav�s da op��o "Gerar Acompanhamento a partir da Estrutura".(HOMOLOGA��O)<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Exibi��o do gr�fico de gantt nas telas de cadastro de itens e monitoramento (corre��o no ambiente de homologa��o).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Altera��o dos seguintes pontos no filtro de N�vel de planejamento da listagem de monitoramento:<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Exibi��o para sele��o no pop-up de n�veis de planejamento todos os n�veis cadastrados no sistema, marcando apenas os itens que est�o sendo utilizados pelo usu�rio;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Realiza��o do filtro de planejamento nas pesquisas salvas;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Adi��o de funcionalidade ao filtro 'sem informa��o', permitindo que os itens cujo n�vel de planejamento esteja em branco sejam exibidos.<br>
  	</td>
  </tr>
     
  <tr> 
  	<td align="center">v8.2</td>
  	<td align="center">ECAR-BR-08-01-00-20081028</td>  
  	<td align="center">21/11/2008</td>
  	<td>
  	- MANUTEN��ES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Permitir a cria��o de Filtro (salvar pesquisa) em monitoramento;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Criar conceito de super fun��o de acompanhamento para registro de pareceres;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Cria��o de �cone indicando o acesso a anexa��o de arquivos no encaminhamento de uma demanda. Cria��o de contador indicando a quantidade de arquivos anexos;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Aplica��o da �rvore com AJAX no cadastro de estrutura;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Permitir configurar a exibi��o dos pareceres na aba de Situa��o: exibi��o em abas ou em lista.<br>
  	</td>
  </tr>  
  <tr> 
  	<td align="center">v8.1</td>
  	<td align="center">ECAR-BR-08-01-00-20081103</td>  
  	<td align="center">24/10/2008</td>
  	<td>
  	- MANUTEN��ES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-No gr�fico de evolu��o, listar os n�veis selecionados para(cadastro de usu�rio)/pelo(op��o do usu�rio na tela de itens de acompanhamento - �cone do filtro n�vel de planejamento) o usu�rio logado. Isso deve ocorrer quando estiver configurado na exibi��o de abas para utilizar o filtro no acompanhamento;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-No cadastramento dos pontos cr�ticos Colocar a data limite como primeira coluna, permitindo classificar por todas as colunas. Como padr�o ordenar a primeira coluna (data limite) em ordem crescente;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Notifica��o de alerta quando a sess�o estiver pr�xima de expirar;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Possibilidade de escolher a data em um calend�rio para preenchimento dos campos de data;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Associar Usu�rios a Grupos do Sentinela - permiss�o de grupos ser� feito no ecar e n�o mais no sentinela;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Modificar o campo de anexo no cadastro das demandas prim�rias para possibilitar o anexo de tipos diferentes de arquivos;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Voltar a not�cia destaque de capa para a �rea de origem e colocar um link para a Caixa de Entrada de email na �rea � esquerda da �rea da not�cia destaque (logo abaixo de 'Meu Cadastro' colocar o label 'Caixa de Entrada' e abaixo o link 'Voc� tem x mensagens n�o lidas.');<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tabela de configura��o do tipo de acompanhamento, criar a op��o (radio button): Exige Liberar Parecer. O radio 'Sim' (Exige Liberar Parecer) vem marcado como padr�o.Proceder semelhante ao item liberar acompanhamento (At� suprimir o bot�o liberar posi��o e alterar a regra de grava��o de rascunho);<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tela resumo do acompanhamento, buscar o label das fun��es na configura��o daquele n�vel de estrutura e listar as posi��es (carinhas) obedecendo a ordem das datas limites para as fun��es de acompanhamento. A ordem deve ser portanto, a partir da data mais pr�xima at� a data mais distante que est� para expirar;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Criar campo de Data da �ltima manuten��o e usu�rio que a realizou no cadastro e classifica��o de demandas prim�rias;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na listagem das demandas prim�rias acrescentar a quantidade de registros por pagina. (Ex: 1-10 de 100);<br>
  	  	
  	- CORRE��O DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na listagem de itens de demandas (cadastro e classifica��o), o filtro por coluna s� filtra a p�gina retornada pela pagina��o (pagina atual) e n�o a pesquisa toda;<br>
  	</td>
  </tr>   
     
  <tr>
  	<td align="center">v8.0.7</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">10/07/2009</td>
  	<td>
	- MANUTEN��ES:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Utilizar o label dos campos de grupos do Sentinela na tela de cadastro de usu�rios no e-CAR.<br>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- N�o exibir a aba de um tipo de acompanhamento para a qual o usu�rio logado n�o tenha nenhuma permiss�o de leitura.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cria��o de tipo de acompanhamento e gera��o de ciclo - n�o obrigar o uso do n�vel de planejamento para que o sistema funcione.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - ao selecionar a op��o personalizado (n�o geral) somente exibir para sele��o as fun��es de acompanhamento que o usu�rio tem permiss�o de visualizar.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - emiss�o do relat�rio - na sele��o das op��es para impress�o somente exibir para sele��o as fun��es de acompanhamento que o usu�rio tem permiss�o de visualizar.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Emiss�o de Parecer - excluir pasta criadas em um acompanhamento quando for exclu�do o �ltimo arquivo anexado em um acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Metas e indicadores - acompanhamento - exibir os indicadores de filhos do item sendo consultado, respeitando as permiss�es de leitura de pareceres para o item.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Tratar cen�rio n�o coberto pela rotina de exporta��o de arquivos retirando as tags HTML geradas indevidamente no arquivo txt de acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Altera��o na gera��o de arquivos TXT e PDF para suportar os caracteres copiados do microsoft word: aspas simples, aspas duplas e os marcadores padr�o do word. As altera��es foram realizadas nas gera��es de arquivos TXT do cadastro de demandas, cadastro de itens, relat�rio TXT de acompanhamento e na gera��o dos arquivos PDF do cadastro de itens e no relat�rio PDF do acompanhamento.<br>
		
	- CORRE��O DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- N�o enviar mensagens para datas de Pontos Cr�ticos j� exclu�dos.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na gera��o de ciclo de acompanhamento o sistema deve permitir selecionar somente alguns filhos e mais o item pai.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Gera��o de ciclo de acompanhamento - sinalizar periodicidade mensal e trimestral corretamente.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Aba resumo - o sistema lista sempre a mesma data limite para todas as fun��es de acompanhamento. Listar as respectivas datas das fun��es.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - quando o usu�rio tem permiss�o para um tipo de acompanhamento que n�o possui nenhum ciclo de acompanhamento gerado, ocorre um erro ao acessar a p�gina de monitoramento.<br>
	</td>
  </tr>
  
  <tr>
  	<td align="center">v8.0.6</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">05/06/2009</td>
  	<td>
	- MANUTEN��ES:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Permitir inclus�o de novas fun��es de acompanhamento na altera��o de um ciclo de acompanhamento gerado.<br>		
	</td>
  </tr>
  
  <tr>
  	<td align="center">v8.0.5</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">11/05/09</td>
  	<td>
	- MANUTEN��ES:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Relat�rios / Lista de usu�rios -> Substitui��o da gera��o do arquivo CSV, que usa a classe "com.Ostermiller.util.ExcelCSVPrinter", pela gera��o de um arquivo txt. Este arquivo seria semelhante aqueles gerados na listagem de itens de estrutura, usando inclusive o separador "Caracter Separador em Arq. Textos" da configura��o geral do sistema para separar os campos do usu�rio.<br> 	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Atualiza��o dos valores previstos por local no cadastro de metas/indicadores -> Substitui��o da classe "com.Ostermiller.util.StringTokenizer" pela classe "java.util.StringTokenizer".<br>
	</td>
  </tr>
  
  <tr>
  	<td align="center">v8.0.4</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">16/01/2009</td>
  	<td>
	- CORRE��O DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro ao anexar mais de um arquivo com o mesmo nome (problemas no download).<br> 	
	</td>
  </tr>
  
  <tr>
  	<td align="center">v8.0.3</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">18/12/2008</td>
  	<td>
	- CORRE��O DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro ao gerar o Gr�fico de Gantt em Monitoramento/Visualiza��o.<br> 	
	</td>
  </tr>
   
   <tr>
  	<td align="center">v8.0.2</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">02/12/2008</td>
  	<td>
  	- MANUTEN��ES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Inclus�o de op��o em estrutura para a possibilidade de visualizar ou n�o o bot�o "Gerar Arquivos" na listagem de itens em cadastro.<br>
	- CORRE��O DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A tela de gera��o de ciclos ao trazer os itens n�o est� montando a �rvore completa do item, impossibilitando de localiz�-lo e diferenci�-lo, pois todos est�o no mesmo n�vel.<br> 	
	</td>
  </tr>
   
   <tr>
  	<td align="center">v8.0.1</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">10/11/2008</td>
  	<td>
  	- MANUTEN��ES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Cadastro de Metas/Indicadores:<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.Na tela de cadastro das metas-indicadores, mudar o label das op��es do campo Formato: de Quantidade para N�mero e de Valor para Moeda<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Metas/Indicadores em monitoramento:<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.No Gr�fico de Proje��o, utilizar o valor refer�ncia para o in�cio da proje��o.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Fun��o tela de Gantt:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.Apresentar todos os pontos cr�ticos cadastrados, mesmo os que n�o foram conclu�dos.<br>
	 
	- CORRE��O DE ERROS:<br>
		
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No filtro de Monitoramento:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tela de filtro do monitoramento, se n�o marcarmos nenhum filtro e clicarmos em avan�ar o sistema n�o emite nenhuma mensagem de aviso para que o usu�rio marque um dos filtros.<br> 	
	</td>
  </tr>
   
  <tr>
  	<td align="center">v8.0</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">23/10/2008</td>
  	<td>
  	- MANUTEN��ES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Cadastro de Estrutura: possibilidade de exporta��o dos dados de cadastro (dados gerais dos itens) em arquivo txt. S�o gerados, a partir do n�vel visualizado, arquivos com os itens cadastrados por estrutura e n�vel de estrutura.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Relat�rios > Relat�rios Impressos > Conselho Revisor: possibilidade de exporta��o dos dados dos itens em monitoramento (dados gerais e pareceres emitidos) em arquivo txt, por ciclo de refer�ncia selecionado. � gerado, a partir do per�do de refer�ncia selecionado, arquivos com os itens em monitoramento para o dado ciclo separados por estrutura e por n�vel. <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Cria��o de estrutura para grava��o do hist�rico de datas cr�ticas.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Estrutura de servi�os: possibilidade de utiliza��o de servi�os no cadastro de metas/indicadores. Servi�os poder�o ser utilizados para apura��o dos valores realizados. Servi�os dispon�veis:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.Apura��o de Conclus�o de Datas Cr�ticas (Pontos Cr�ticos);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-2.Apura��o de Atraso de Conclus�o de Datas Cr�ticas (Pontos Cr�ticos);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-3.Apura��o de Total de Datas Cr�ticas (Pontos Cr�ticos);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-4.Apura��o de Reprograma��o de Datas Cr�ticas (Pontos Cr�ticos) - C�lculo de Extens�o de Prazo;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-5.Apura��o de Reprograma��o de Datas Cr�ticas (Pontos Cr�ticos) - C�lculo Redu��o de Prazo.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Cadastro de Metas/Indicadores:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.Possibilidade de utiliza��o de servi�os para apura��o de valor realizado na fase de monitoramento;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-2.Possibilidade de utiliza��o de gr�fico de grupo para possibilitar a visualiza��o de 'n' indicadores (que perten�am ao mesmo grupo e sejam: ou p�blicos ou do mesmo item em visualiza��o) em um mesmo gr�fico;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-3.Possibilidade de uso de sinaliza��o indicando cores para cada intervalo de valor da meta/indicador.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Metas/Indicadores em monitoramento:<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.Visualiza��o de gr�ficos de grupo no gr�fico realizado por exerc�cio;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-2.Uso dos servi�os na apura��o do valor realizado. No caso do servi�o ter sido configurado como manual: quando o valor realizado ainda n�o foi gravado, se o servi�o retornar um valor apurado � exibido um bot�o 'Gravar'; caso algum valor j� tenha sido gravado e seja diferente do apurado deve-se exibir o bot�o 'Limpar' com um �cone de alerta indicando que h� diferen�a; quando o valor apurado for gravado deve ser exibido o bot�o 'Limpar'. No caso do servi�o ter sido configurado como autom�tico, o valor apurado ser� sempre exibido com o �cone de alerta indicando que o valor n�o est� gravado e poder mudar, mas ser� gravado apenas quando a data limite para informa��o do realizado expirar.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Fun��o de Acompanhamento: inclus�o do indicador 'Manter no pr�ximo n�vel' na fun��o de acompanhamento na estrutura. A rotina da fun��o de permiss�o de acesso no cadastro de estrutura foi alterada para considerar esta nova possibilidade de configura��o.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Fun��o tela de Gantt: <br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.possibilidade de visualiza��o dos marcos (pontos cr�ticos) cadastrados para um item de estrutura em termos do que foi planejado e do que foi realizado. Em cadastro a visualiza��o � sempre com base na data atual. Em monitoramento � poss�vel a visualiza��o baseada: na data atual (padr�o), na data de in�cio de monitoramento, data limite para realizado f�sico.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Unifica��o da tela de metas/indicadores em monitoramento: unifica��o da visualiza��o da tela quando se est� em registro ou em visualiza��o.<br>
 
	 
	- CORRE��O DE ERROS:<br>
		
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na aba de Resultados em Monitoramento:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando o formato do indicador � definido para quantidade, o usu�rio n�o consegue gravar na tela de resultados;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Caso o usu�rio digite um valor superior a 1.000, o sistema insere o ponto e quando o valor � carregado ele n�o permite mais a grava��o. Na valida��o do valor exibida, o sistema n�o aceita mais o ponto que ele mesmo inseriu;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando a op��o "quantidade informada realizada por local" for selecionada, abrir� um popup para informar os valors dos locais. Caso n�o exista nenhum filho, ocorre um erro de grava��o.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando o valor realizado do indicador for igual a zero ou n�o for informado, o gr�fico de proje��o n�o � carregado e nenhuma mensagem de erro � exibida;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando muda a situa��o do indicador o sistema, exibe um erro de permiss�o de acesso do sentinela dentro da pr�pria p�gina (sobre cabe�alho da p�gina);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Caso o usu�rio tente alterar o valor de apenas um indicador o sistema validar� a situa��o de todos os outros, for�ando o usu�rio a informar a situa��o de todos, inclusive dos que estejam sem valor.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na aba de Metas e Indicadores em Cadastro:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-O radio button que aparece ap�s a selec��o de "Acumul�vel - N�o" deveria ser exibido abaixo da op��o "Acumul�vel" e n�o d� op��o "� p�blico";<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Deve existir um nome �nico para cada indicador, pois atualmente mais de um indicador com o mesmo nome acarreta numa perda de informa��es. Em uma lista de indicadores com mesmo nome o gr�fico exibir� apenas uma barra com o �ltimo valor encontrado; <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando o usu�rio insere qualquer valor ao campo "�ndice Refer�ncia", o sistema adiciona uma v�rgula no final do campo e grava os dados. Caso o usu�rio tente alterar os dados desse indicador, mantendo o valor informado com a v�rgula, o sistema ir� validar de forma errada o campo e entrar� em loop infinito;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Validar a sele��o do campo "Quantidade. Prevista Informada por Local?" como obrigat�ria;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-O popup que se abre ao clicar em "Informar Quantidade/Valor Previsto por Local " estava mal formatada quando o local n�o possu�a filhos.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Permiss�o de acesso: corre��o da rotina de atualiza��o das permiss�es de acesso pois permiss�es dadas a fun��es de acompanhamento previamente, mesmo quando alterados os usu�rios de cada fun��o de acompanhamento, estavam permanecendo.<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na aba de Metas e Indicadores em Cadastro:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-No IE o campo valor final com as op��es: Maior, �ltimo e N�o se Aplica est� sendo apresentado quando marcamos o campo vinculado Acumul�vel = Sim. O correto seria apresentar essa marca��o quando preenchido o campo Acumul�vel = N�o;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na aba de Situa��o em Monitoramento:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Erro de imagem quebrada (pixel.gif) localizada acima do texto "Mostrar Todos(as) Situa��es Emitidos(as)". No IE, ainda est� aparecendo a imagem quebrada;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Bot�o Cancelar.png. Os bot�es cancelar na tela de registro de parecer est�o sem a��o.<br>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tela de registro de parecer, no IE, ao clicar em cima de um posicionamento, a ajuda n�o funciona para todos os casos.<br>		
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na aba de Metas e Indicadores em Monitoramento:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Ap�s gravar uma meta definida com servi�o e preenchimento manual, o sistema apresentou os bot�es limpar, por�m eles n�o est�o realizando nenhuma a��o (est�o vindo desabilitados);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando dois indicadores possuem o mesmo nome, o sistema exibe uma linha em branco abaixo do primeiro indicador cujo nome � repetido;<br>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Ao acessar a tela de metas/indicadores no acompanhamento, o aviso de n�o conformida entre as datas de cadastro e acompanhamento aparece em casos que n�o deveria aparecer;<br>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na parte do Cadastro:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Exporta��o de Arquivos - Erro na gera��o de arquivo de sa�da. 1- N�o est� varrendo todas as estruturas; 2- Caracteres Especiais para a Nomenclatura do item; <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No filtro de Monitoramento:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Erro de valida��o do Campo Exibir na tela inicial do filtro de acompanhamento; <br>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Relat�rio Impresso  de Monitoramento:<br>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Gera��o de Arquivos: N�o est� gerando para os itens que n�o tem o acompanhamento do item liberado; <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Gera��o de Arquivos: O sistema est� gerando arquivos sem considerar a regra de acesso aos itens; <br>
	
	</td>
  </tr>
  <tr>
  	<td align="center">v7.0</td>
  	<td align="center">HEAD</td>  
  	<td align="center">21/07/2008</td>
  	<td>
  	- MANUTEN��ES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- �rvore de itens de estrutura na tela de monitoramento: somente exibir itens na �rvore que possuam itens no pr�ximo n�vel a exibir. Isto implica em descer na estrutura at� o final eventualmente, quando o item com visualiza��o permitida estiver no �ltimo n�vel da �rvore. Nas telas de cadastro, o sistema j� procede desta maneira, por�m as permiss�es verificadas neste caso s�o diferentes das permiss�es que devem ser testadas no visualizar ou registrar acompanhamento. Como recurso inicial utilizaremos a seguinte regra:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-O sistema exibe TODOS os itens at� o n�vel m�ximo de exibi��o da estrutura em configura��o do Tipo de acompanhamento. Todos os registros devem aparecer com sinal de mais[+];<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Ao clicar no mais para expandir a estrutura, o sistema verifica se existem registros a exibir no n�vel inferior (conforme descrito acima);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Caso n�o existam, o sistema troca o quadrado para um quadrado vazio;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Caso existam, o sistema deve apresentar todos os itens do n�vel seguinte com o sinal de mais ao lado (desde que o item d� acesso a algum item no pr�ximo n�vel seguinte a este);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Itens que n�o d�em acesso a algum item a visualizar n�o devem ser exibidos (n�o devem aparecer na lista);<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Itens com ARI, mas sem permiss�o para o usu�rio visualizar este item, segundo os crit�rios j� existentes de visualizar parecer);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Se n�o tiver filhos, apresentar quadrado em branco;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Se tiver filhos e estiver fechado, apresentar quadrado com mais;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Se tiver filhos e estiver expandido, apresentar quadrado com menos;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-�rvore: obedecer o que foi configurado na tela de "Tipo de Acompanhamento" para o maior n�vel a exibir.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em altera��o do tipo de acompanhamento est� aparecendo o erro (IE) "Stack overflow...." (estouro de pilha).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Gerar Ciclo de Acompanhamento s� mostrar o que est� vinculado com Sala de Situa��o (estrutura de N�vel de Planejamento do sistema). <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Gerar Ciclo de Acompanhamento identar a exibi��o dos registros. Mostrar o label da estrutura na �rvore, na �rvore, quando este existir na especifica��o da estrutura. <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acrescentar em configura��o geral um campo para indicar o ciclo padr�o para ser adotado pelo acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a tela de filtro e colocar o ciclo padr�o definido na exibi��o inicial.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Tela de Exibi��o de Abas, unificar os checks de posi��o e elabora��o.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em situa��o, trocar a mensagem (do onMouseOver) que est� acumulada com o texto anterior.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Verificar no cadastro de item de estrutura o cadastro de imagem como atributo livre (n�o mostra a imagem depois da altera��o) no IE.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Abas da rela��o, situa��o restri��es e situa��o indicadores devem ficar acima do avan�ar/retroceder (pertence a todos os empreendimentos, ou seja, � comum a um conjunto de dados).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela "Grupo Acesso" depois de salvar, voltar a aparecer o bot�o alterar (em modo bloqueado).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtros: implementar atributos livres em filtros din�micos.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtros: alterar o label "Acompanhamento" para "Dados Acompanhamento" em filtros.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Filtros: levar o algoritmo de data mais pr�xima (refer�ncia) para a tela de filtro.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtros: campos de data e valor, ficar como intervalo em filtros. Atributos de dados gerais devem ser tratados de forma fixa e atributos livres devem ser tratados somente itens de texto com "valida��o" onde a regra de valida��o � que define o tipo de tratamento (data ou valor).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtros: mostrar uma mensagem na aba Dados Gerais, caso n�o tenha sido marcado nenhum filtro.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Permitir incluir restri��es e apontamento de restri��es na aba de restri��es, verificando qual fun��o de acompanhamento pertence.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em avalia��es, deixar o l�pis para facilitar a altera��o.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Verificar em "grupo de acesso" as consist�ncias: <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Situa��o com sele��o de algum visualizar pareceres;<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Gerar ciclos com sele��o de datas limites.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Tela de Resumo:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Alterar a tela de resumos para uma aba "resumo" nas abas de monitoramento;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Alterar a tela de resumo, excluir a exibi��o de pareceres se n�o tiver acesso a aba de situa��o.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Incluir um campo para a sigla no cadastro de Local e Grupo Local. Nas tabelas TB_LOCAL_ITEM_LIT e TB_LOCAL_GRUPO_LGP, incluir um campo para SIGLA - varchar(10).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Identidade Visual:<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Adequar a identidade visual do ambiente ao mais pr�ximo poss�vel do padr�o definido para SISPAC;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Em configura��es gerais incluir campo para o administrador selecionar um arquivo .CSS entre alguns cadastrados;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Alterar as p�ginas .JSP para enxergarem o .CSS indicado na configura��o. <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtros:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Criar uma nova aba na tela de filtros com o label "Filtros". Esta aba dever� apresentar os filtros previamente gravados. Como neste momento n�o temos a funcionalidade de gravar os filtros, incluir as seguintes op��es fixas nesta aba, para sele��o de uma delas:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Geral: exibe todos os registros sem aplica��o de filtro;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Minha Vis�o: exibe todos os acompanhados que o usu�rio logado tenha permiss�o para editar alguma coisa;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Minhas pend�ncias: exibe todos os acompanhamentos que o usu�rio logado tenha algum parecer ainda n�o liberado, e/ou todos os acompanhamentos em que o usu�rio � administrador e que o acompanhamento exija liberar o acompanhamento (definido em tipo de acompanhamento) e o acompanhamento ainda n�o tenha sido liberado; <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Todas estas op��es devem seguir o seguinte crit�rio: Exibe os acompanhamentos do primeiro tipo de acompanhamento, segundo a seq��ncia de exibi��o definida em Tipo de Acompanhamento, que o usu�rio logado tem permiss�o de visualizar, com ciclos = 1 e ciclo de refer�ncia mais pr�ximo a data atual (conforme crit�rio j� existente anteriormente);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Personalizado: desvia para a tela seguinte ;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtro: melhorar a interface, vinculando "Cor" e "Fun��o de Acompanhamento" de uma forma que identifique melhor que um esta relacionado ao outro.<br>
	
	- CORRE��O DE ERROS:<br>
		
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de monitoramento, n�o est� aparecendo todas as informa��es dos campos definidos em atributo na estrutura para a descri��o do item monitorado. Verificar campos fixos e atributos livres.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Logo ao criar uma estrutura e sem conceder nenhuma permiss�o para o usu�rio/grupo, aparece o bot�o de Excluir na tela de listagem de projetos.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Tela de restri��es: Ao preencher o campo respons�vel pela solu��o sem preencher o campo data da solu��o, n�o � persistida a informa��o. Deveria haver um tratamento informando que a persist�ncia desse campo s� ocorrer� quando for preenchido o campo data da solu��o.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Rever as regras consideradas pelo filtro no momento da gera��o de um ciclo de acompanhamento. Verificar as regras dos atributos "Gerar acompanhamento a partir da estrutura", "inclui monitorado", "inclui n�o monitorado". <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O menu lateral aparece fora de formata��o quando possui poucos itens.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Rever as regras de "Pode ser Bloqueado?" e "Est� liberado para" em atributo na estrutura e fun��o de acomp na estrutura.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A aba de dados gerais, n�o fica ativa ap�s a sele��o do filtro personalizado, esta aba deveria ser de visualiza��o opcional, e n�o obrigat�ria ap�s clicar em avan�ar.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acessar p�gina de Monitoramento (tanto atrav�s do menu superior "Monitoramento", quanto pelo menu lateral "Acompanhamento / Monitoramento"). Problema: No Firefox ao clickar no bot�o avan�ar nada acontece. No internet explorer ao clicar no bot�o avan�ar ocorre erro de JavaScript em anexo. Perfil: Administrador Local<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ap�s realizar uma pesquisa na tela "Demanda > Classifica Demanda", selecionar e alterar o status de um registro, o e-CAR retorna para a tela de filtro mas n�o atualiza o status desse registro. Problema: Tela de filtro de Demandas n�o � atualizada no retorno da altera��o Navegador: Firefox, n�o testei no IE Perfil: Administrador Local<br>
	- MERGES:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 6.2 <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 6.3 <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 6.4 <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 6.5 <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 6.6 <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- 6.6.1 <br> 
	 
	</td>
  	</tr>
  	
  	<tr>
  	<td align="center">v6.6.1</td>  
  	<td align="center">branch_v6_00_b5</td>
  	<td align="center">08/07/2008</td>
  	<!-- <td align="center">em teste</td> -->
  	<td>
  	- MANUTEN��ES:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a regra de visualiza��o da listagem das demandas prim�rias, retirando a visibilidade de demandas 
								    que forem cadastradas pelo mesmo grupo do usu�rio logado, ou seja, o usu�rio
                   				    logado n�o poder� visualizar as demandas que foram cadastradas por outros usu�rios do mesmo grupo que o dele.
                   				    Tanto para a listagem do cadastro quanto para a classifica��o. <br>
	
	- CORRE��O DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao classificar uma demanda no Internet Explorer, a demanda continua aparecendo na listagem de demandas, 
  									at� que se atualize a p�gina ou clique novamente no link de acesso a classifica��o. No Firefox    
                   					funciona corretamente.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro ao gravar anexos e erro de libera��o do acompanhamento no parecer.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No cadastro de demanda prim�ria, no campo �rg�o solucionador, a tela de pesquisa n�o est� funcionando.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao alterar o campo �rg�o solucionador de uma demanda prim�ria j� cadastrada, est� dando um erro na exclus�o de um 
    							    item cadastrado no Internet Explorer.<br>
    							    
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- N�o est� obedecendo a configura��o de visualiza��o de pareceres conforme o papel definido na configura��o do grupo, 
    							    menu Administra��o Sistema > Usu�rios > Grupo de Acesso, deveria restringir o acesso a visualiza��o do 
    							    parecer conforme o papel selecionado. Ou seja, o usu�rio est� conseguindo acessar todos os pareceres definidos para o item.<br>
    							    
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- N�o est� considerando a configura��o na aba de permiss�es de acesso em um item do cadastro, para permitir ao usu�rio 
								    indicado o acesso ao acompanhamento desse item. Ou seja, o usu�rio que possui essa permiss�o definida no cadastro n�o
								    est� conseguindo enxergar esse item no acompanhamento para visualizar os pareceres.<br> 
    							    
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Lista Geral - na tela de parecer do acompanhamento ao clicar na aba lista geral, o sistema perde a refer�ncia e n�o mostra a lista de itens. <br>
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No monitoramento, ao mudar a sele��o dos campos Ciclo de Exibi��o e Ciclo de Refer�ncia, o sistema perde a refer�ncia da listagem de itens. <br>
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Gr�fico de resultados (Metas e Indicadores) - N�o est� apresentando o gr�fico de proje��o, quando prenchido os v�rios ciclos.<br>
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em parecer, apresenta o �cone mesmo sem haver preenchido o parecer da fun��o.
	</td>
  	</tr>
  
  
   <tr>
  	<td align="center">v6.6</td>  
  	<td align="center">branch_v6_00_b5</td>
  	<td align="center">05/06/2008</td>
  	<!-- <td align="center">em teste</td> -->
  	<td>
  	- MANUTEN��ES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de classifica��o de demandas, habilitar para a edi��o os campos indicados:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Munic�pio/Gov. do Estado;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Entidades;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Data de Protocolo;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-�rg�o Solucionador;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Nome do Solicitante.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Atualiza��o do arquivo informando as altera��es liberadas em cada vers�o.  <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar no banco de dados o endere�o de e-mail dos textos do site alterando para lista-ecarserpro@grupos.serpro.gov.br.<br>
	- CORRE��O DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ajuste da tabula��o dos itens na tela de filtro no acompanhamento.<br> 
	</td>
  	</tr>
   	
   	<tr>
  	<td align="center">v6.5</td>  
  	<td align="center">branch_v6_00_b5</td>
  	<td align="center">29/05/2008</td>
  	<!-- <td align="center">em teste</td> -->
  	<td>
  	- MANUTEN��ES:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ajuste do campo Entidades Solucionadoras do Cadastro de Usu�rios n�o deve ser de preenchimento obrigat�rio.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtrar a tela de Classifica��o por:<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Somente os projetos com situa��o = "a classificar" E <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Pelo Campo Entidades Solucionadoras do Cadastro OU pelo Campo UF - Atributo Livre Restritivo em Atributo na Demanda.<br>
									
									
	- CORRE��O DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao preencher o realizado f�sico de uma meta/indicador, e atribuir um valor ao campo situa��o, o sistema 
									mostra a tela de bloqueio do sentinela mesclada ao topo da p�gina.<br>
	
	</td>
  </tr>
  
  <tr>
  	<td align="center">v6.4</td> 
  	<td align="center">branch_v6_00_b5</td>
  	<td align="center">21/05/2008</td>
  	<!--  <td align="center">em teste</td> -->
  	<td>
  	- MANUTEN��ES:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Apresentar a UF vinculada na tela de pesquisa dos munic�pios<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ajustar as telas Cadastro e Classifica��o de Demandas para que o campo Org�o Solucionador 
									possa receber mais de um valor. Cada �rg�o teria como listar. <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar Nomenclatura no m�dulo de monitoramento edi��o de parecer:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1) Trocar nas abas das fun��es o nome Rela��o por Lista Geral;<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2) No parecer alterar a Situa��o por Est�gio e Cor por Situa��o. 
									(Alterar a nomenclatura de Bot�es, de Avalia��o para Situa��o e de Posi��o para Situa��o) <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Aumentar a pagina��o na listagem de itens do cadastro e classifica��o de demandas, de 10 para 20.<br>								
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na gera��o de relat�rios do tipo conselho revisor e acompanhamento, permitir selecionar a imagem que aparece no cabe�alho.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtrar tamb�m por UF na tela de listagem das demandas.<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Alterar o cadastro de grupo de atributo em demanda adicionando a op��o que indica que o grupo de atributo 
									vai para a Tela de cadastro de usu�rio.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Alterar o cadastro de atributo na demanda adicionando a op��o "� restritivo" que indica se o atributo 
									ser� usado para filtrar as demandas a serem exibidas na listagem.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Criar um novo atributo na tabela grupo_atributo (livre) que conteria o tipo de exibi��o no cadastro de usu�rio 
									(que poder� ser diferente do tipo de exibi��o no outro local do grupo estrutura, demanda, entidade, etc).
									Ao criar este campo na base de dados colocar '2' para o SGA = 27 (UF de demandas). 
									Para os demais registros copiar valor do cod_STeg para o novo campo.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Converter a Tabela de �rg�os como Entidade.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na listagem do acompanhamento (monitoramento), na tela onde aparece a posi��o geral dos itens (carinhas), mudar a cor do t�tulo da coluna do ciclo de refer�ncia com a cor clara, os demais ciclos devem ficar com a cor escura.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em classifica��o de demandas os campos Descri��o Demandas e Observa��o devem estar edit�veis.<br>
									
	- CORRE��O DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No cadastramento (Inclus�o) de uma demanda, o campo fixo "Descri��o da Demanda" s� est� aceitando 200 caracteres, 
									n�o est� lendo da configura��o que especifica 3000 caracteres.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Campo Fixo "Situa��o" na classifica��o de uma demanda s� aceita 20 caracteres.<br>
  	</td>
  </tr>
  
  <tr>
  	<td align="center">v6.3</td>  
  	<td align="center">branch_v6_00_b5</td>
  	<td align="center">24/04/2008</td>
  	<!--  <td align="center">em teste</td> -->
  	<td>
  	- MANUTEN��ES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Relat�rio de demandas prim�rias, criar sa�da de dados via texto com separador para o m�dulo de demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Aumentar o tamanho da janela dos campos para preenchimento do parecer.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Criar op��o em configura��o geral que permita ocultar o campo de observa��es no parecer.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No encaminhamento (apontamento) ampliar caracteres para 3 mil. �ltimo encaminhamento pode ser exclu�do, os anteriores n�o.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Criar um novo momento para a� fun��o de encaminhamento fora do n�vel de� cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar� os labels do m�dulo Demandas� "Apontamento" para� "Encaminhamento".<br>
  	                                
  	- CORRE��O DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Abertura de arquivos anexos utilizando Internet Explorer.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em gera��o de acompanhamento, no primeiro n�vel da estrutura o l�pis n�o est� sendo exibido na primeira linha.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em gera��o de relat�rio de acompanhamento, a rela��o n�o traz nenhum item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em gera��o de relat�rio no cadastro aparece uma tela com null.<br>
  	</td>
  </tr>
  
  <tr>
  	<td align="center">v6.2</td>  
  	<td align="center">branch_v6_00_b5</td>
  	<td align="center">15/04/2008</td>
  	<!--  <td align="center">em teste</td> -->
  	<td>
  	- MANUTEN��ES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Implementar funcionalidades tela atributo_demanda.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Refazer tela de listagem de demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Refazer tela de cadastro de demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ajustar tela de classifica demanda.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Refazer tela de consulta de demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Criar filtro din�mico em demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Replicar altera��es em empreendimento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtrar a apresenta��o das demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Incluir um campo para anexar documentos em Apontamento de Demandas.<br>
  	</td>
  </tr>

  </table>
  
</div>
 
</body>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="include/ocultarAguarde.jsp"%>

</html>
