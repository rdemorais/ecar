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
  	<td colspan="4">HISTÓRICO DAS VERSÕES</td>
  </tr>
  <tr align="center"> 
  	<td>VERSÃO</td>
  	<td>ORIGEM</td>  
  	<td>DATA</td>
  	<td>COMENTÁRIOS/NOVIDADES EM RELAÇÃO À VERSÃO ANTERIOR</td>
  </tr>

 <tr> 
  	<td align="center">v8.8.0</td>
  	<td align="center">HEAD</td>  
  	<td align="center">12/01/2011</td>
  	<td>
 	- CORREÇÕES DE ERROS E MANUTENÇÕES:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [RESTRIÇÕES] Bloqueio seletivo de campos - A funcionalidade de bloqueio seletivo de campos na função de restrições não está obedecendo à configuração de atributo na estrutura, onde é indicado os campos que devem aparecer bloqueados para as funções definidas. Deveria se comportar semelhante ao cadastro, após o bloqueio do item através do botão de bloqueio, bloquear os campos configurados conforme a função do usuário.<br>  	  	  	
 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [RESTRIÇÕES] Ordenação pela primeira coluna - Ordenar listagem de restrições, inclusive por atributos livres, na primeira coluna.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [RESTRIÇÕES] Como opção padrão para o cadastro de restrições o checkbox "Ativo" do conjunto "Estados e Envio de e-mails" deve vir desativado.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [INTEGRAÇÃO FINANCEIRA] 1. Erro na vinculação de contas do orçamento - Correção do erro que ocorre durante a associação de contas do orçamento e ajuste dos labels conforme visualização da Função ou Sub-Função.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [ACOMPANHAMENTO] No acompanhamento, replicar o link de acesso ao modo de consulta (link da carinha e/ou do quadrado em branco da tela de posição geral) no nome texto do item que é apresentado na tela de acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [ACOMPANHAMENTO] Ocultar a aba de pareceres quando o usuário não tem permissão de visualizar nenhum dos pareceres.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [ACOMPANHAMENTO] Avaliar o reflexo das mudanças no relatório da tela de Acompanhamento. Manter a compatibilidade com o que foi implementado no previsto mensal.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [ACOMPANHAMENTO] Na tela de preenchimento do parecer, destacar com a cor vermelha as mensagens de datas e vencimento dos pareceres.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [ACOMPANHAMENTO] Erro ao Recuperar Usuário (Usuário não encontrado). Na tela de listagem de itens, o sistema, em algumas situações, não está recuperando o nome dos usuários definidos para as funções de acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [DADOS GERAIS] Após bloquear um item com atributo seletivo (define se a estrutura filha será apresentada ou não) e alterar o item, o sistema retorna erro informando que o atributo seletivo foi alterado. Verificar se ocorre somente no atributo livre do tipo checkbox.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [METAS E INDICADORES] Desabilitar edição de valores realizados de indicadores de meses anteriores. O campo deve estar habilitado para edição e os valores somente podem ser alterados nos respectivos meses em que houve a gravação da informação e quando a data limite para informação de realizado físico estiver em ciclo válido para tal.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [METAS E INDICADORES] Revisão da tela de visualização e preenchimento dos indicadores: Sempre apresentar os valores previstos; Adequação da tela para utilizar a opção Acumulados, cortando o valor até o ciclo visualizado se a opção estiver marcada até o ciclo e apresentando tudo se a opção for Total; Redefinição dos gráficos.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [METAS E INDICADORES] Erro na ordenação dos indicadores dos itens filhos. A listagem dos itens filhos na tela de metas-indicadores do acompanhamento está seno retornada com uma ordem aleatória. A ordenação deverá ser equivalente a do cadastro de itens.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [PORTAL] Corrigir a apresentação da imagem de detalhe do item. A imagem que deve ser apresentada na tela de detalhe do clipping deve ser a que foi configurada no campo imagem 1 da configuração, vide menu: Administração Portal > Taxações > Taxação.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [PORTAL] Trocar label "taxação/clipping" por "na mídia".<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [EXPORTAÇÃO] Adicionar exportação de informação de arquivos anexos aos itens.<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  [BANCO] Adaptar o e-Car para que seja compatível com a versão 9.0 do Banco de Dados PostgreSQL.<br>
  	</td>
  </tr>

 <tr> 
  	<td align="center">v8.7.2</td>
  	<td align="center">branch_08_07_02_20101129</td>  
  	<td align="center">30/11/2010</td>
  	<td>
 	- CORREÇÕES DE ERROS:<br>
  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  Ao gerar um ciclo de acompanhamento não separado por órgão o sistema não inclui os itens do cadastro que possuem o campo órgão responsável informado.<br>
 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  Ao copiar um modelo de estrutura para um novo projeto, o sistema mostra o erro: <i>if(window.opener){ alert('Não foi possível acessar a funcionalidade através desta opção escolhida. \nExceção: null ...</i><br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-  Na copia de um modelo de estrutura para um novo projeto os itens de estrura estão aparecendo com "pais" diferentes do cadastrado.<br>
  	</td>
  </tr>
  
 <tr> 
  	<td align="center">v8.7.1</td>
  	<td align="center">branch_08_07_01_20101105</td>  
  	<td align="center">08/11/2010</td>
  	<td>
 	- CORREÇÕES DE ERROS:<br>
  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro na visualização da aba de Resumo no Acompanhamento.<br>
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.7 BETA 1</td>
  	<td align="center">HEAD</td>  
  	<td align="center">26/08/2010</td>
  	<td>
  	  	  	 	
  	- MANUTENÇÕES:<br>
  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar comportamento do perfil para permitir as operações de pesquisa, inclusão, alteração e exclusão, bem como a definição de novos parâmetros de configuração por tecnologia de obtenção dos dados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a funcionalidade de importação dos dados de empreendimentos do PACInter para itens de estrutura no e-CAR, considerando os novos conceitos de Tipo de Funcionalidade e Tecnologia de Comunicação, limitando às opções "Cadastro" e "Arquivo Texto em Formato Específico", respectivamente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a funcionalidade de consulta ao log de importação, considerando os novos conceitos de Tipo de Funcionalidade e Tecnologia de Comunicação.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Permitir a geração de ciclos separados por órgão. O usuário poderá exibir, em uma mesma referência, todos os ciclos gerados para um mesmo dia/mês/ano na listagem de acompanhamento. A listagem do acompanhamento e a listagem de itens disponíveis para geração serão exibidas de forma agrupada, separando os itens por órgão. Permitir a geração de arquivos e relatórios para referências separadas por órgão.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a forma de geração de arquivos (cadastro de itens e acompanhamento) para não permitir a exportação de subfunções sem que a função relacionada seja exportada. Desta forma, para a exportação das subfunções 'Quantidades Previstas' e 'Encaminhamentos' será necessária a exportação das funções 'Metas/Indicadores' e 'Restrições' respectivamente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Permitir que o usuário indique um grupo de atributos livres da estrutura pai a ser utilizado como critério de restrição para exibição de estruturas. A configuração será realizada no cadastro de estruturas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar as validações existentes na configuração de estruturas. Remover a obrigatoriedade dos campos 'Data Início', 'Data Fim', 'Órgão Responsável' e 'Periodicidade de Acompanhamento' do cadastro de estruturas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a exibição das abas do acompanhamento de acordo com configuração de exibição de abas e ícones e função na estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar o arquivo gerado no cadastro de demandas, com a inclusão do cabeçalho para as colunas geradas, e a inclusão de duas colunas com os identificadores das demandas e dos encaminhamentos relacionados.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Criar aviso, na listagem de ciclos gerados, informando quando um órgão estiver inativo (seguir padrão utilizado para destacar itens com órgão alterado após a geração de ciclos).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na geração de ciclos separados por órgão, não considerar os itens de estruturas que não possuem o campo órgão configurado.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Retirar obrigatoriedade de haver ao menos um item selecionado para a alteração de ciclos.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtrar os locais possíveis de uso para registro do previsto ou realizado. Somente locais vinculados ao item, ou seus locais filhos podem ser utilizados como locais possíveis de registro do previsto ou realizado.  Se já houver locais com valores previstos ou realizados registrados, somente locais da mesma abrangência, podem ser utilizados para novos registros de previstos ou realizados.<br> 
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Valores previstos de indicadores passaram a ser  informados por mês. Registro de valores previstos tratando valores por mês em cada ano, utilizando o critério de árvore para exibição dos locais e seus filhos, onde o usuário poderá registrar os valores (popup valores previstos informados por local).<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Melhorias no gráfico de realizado por exercício. Foi incluído no gráfico de realizado por exercício as informações do previsto calculando os valores baseados nos valores informados no previsto mensal (soma, final, maior, soma locais, etc). Antes estava sendo exibido o previsto informado em cada ano no momento do cadastro do indicador. Incluído link no gráfico do realizado por exercício para ao clicar na barra de um exercício apresentar o gráfico de realizado por mês no exercício para indicadores agrupados ou não.
                                    Os gráficos com exercício com meses de anos diferentes (tabela de exercícios), devem apresentar o ano ao qual pertence o exercício (realizado mensal), tanto no gráfico como na tabela exibida.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Melhoria no gráfico de realizado por mês. Incluir no gráfico de realizado por mês no exercício as informações do previsto por mês.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Melhorias no gráfico de projeção. No gráfico de projeção, o sistema deverá apresentar a data de previsão de término ao invés da porcentagem de execução do projeto em seu encerramento. Manter na projeção gráfica, a linha que é traçada até o final do projeto ou até que sejam concluídos os valores previstos, o que for maior. Na visualização do gráfico, o usuário poderá ver qual a dimensão da diferença dos valores previstos e realizados até o final do projeto. A projeção deverá utilizar o cálculo para uma progressão aritmética média, porém o usuário poderá optar por outro método de cálculo de aproximação tal como, progressão através da média de aceleração.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ajustes no valor calculado do realizado sobre o previsto (%). O cálculo de valor realizado sobre o previsto (%) deve ser revisto ocultando a opção "não se aplica" no cadastro do indicador. O termo TOTAL será mantido na tela de visualização do realizado independente se o indicador é acumulável ou não.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Mostrar Indicadores de Itens filhos no acompanhamento. Na tela de acompanhamento de indicadores, exibir Indicadores de itens filhos de um item tanto para a opção REGISTRAR quanto para a opção VISUALIZAR os valores.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Valores para sinalização são em porcentagem (%). No cadastro de indicadores, o sistema deve apresentar a grade de valores para sinalização incluindo a unidade "%". Os valores informados serão sempre percentuais que deverão ser aplicados sobre os valores previstos e realizados para identificação da sinalização a utilizar, onde se aplicar a sinalização.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ajustes no tratamento de edição para número ou moeda. No cadastro e no acompanhamento de indicadores, corrigir tratamento de edição para número ou moeda, onde número somente deve apresentar casas decimais se o valor informado contiver valores significativos na casa decimal. Aceitar e guardar tantas casas decimais quantas forem informadas, quando for formato número. Para valor tratar sempre como 2 casas decimais, arredondando.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Exportação de Indicadores. No cadastro, ajustar a exportação dos valores dos indicadores considerando o previsto mensal. No acompanhamento, implementar a exportação de indicadores (valores realizados).<br>
          	  	 	
  	- CORREÇÕES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Demandas - O "Caracter Separador para Campos Multivalorados", informado em Configuração Geral, não está sendo utilizado na listagem nem no detalhamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - O sistema não exibe corretamente os textos do combo ciclo de referência, não concordando com o número de ciclos gerados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento -  A partir da aba "Etapa Nível Inferior" (em visualização), ao clicar em qualquer aba superior, o sistema apresenta erro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro: Revisão - O sistema apresenta erro ao clicar no botão adicionar da função 'Revisão' do cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - A partir da listagem de itens, ao selecionar a opção de impressão o sistema exibe a tela de geração de relatório do cadastro com uma aba cujo link leva para a mesma tela. O link deverá ser retirado.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configuração Sistema / Cadastro - O sistema disponibiliza a adição de órgãos inativos no cadastro de usuários e não exibe, no cadastro de itens, os órgãos inativos, mesmo que estejam sendo utilizados por um item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Demandas - O sistema não verifica a obrigatoriedade do campo Prioridade.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Demandas - Na alteração do cadastro de demandas, o sistema apaga os valores  informados  nos campos do tipo multitexto configurados apenas como 'Exibível?'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - As árvores Ajax e de Navegação estão exibindo os caminhos dos arquivos salvos no cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - Diário de Bordo - O sistema não grava corretamente as datas informadas fora do padrão DD/MM/AAAA.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - O sistema exibe erro de nullpointer quando o usuário clica no botão para excluir uma anotação sem ter nenhuma anotação selecionada.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - Associação de Demandas - O "Caracter Separador para Campos Multivalorados", informado em Configuração Geral, não está sendo utilizado na listagem das demandas exibidas no popup de associação.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Resumo - O sistema não disponibiliza link para todas as funções de acompanhamento na aba de resumo.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Situação Pontos Críticos - Erro ao recarregar a página pressionando a tecla 'ENTER' no campo 'comparativo'.  A validação de datas (Comparativo deve ser maior que data atual) não é realizada caso o usuário recarregue a página utilizando o 'ENTER'. Além disso, os filtros de estado são perdidos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Restrições - O sistema exibe erro de nullpointer caso o usuário tente detalhar uma restrição de um item (acompanhamento de itens) sem que a estrutura deste item esteja configurada para exibir a sub-função apontamentos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configuração Sistema - Geral - Na Configuração Geral, ao digitar caracteres alfa nos campos numéricos, e confirmar a gravação, o sistema exibe a mensagem: "Não foi possível acessar a funcionalidade através desta opção escolhida. Exceção: For input string:" <br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Confiuração Estrutura -  Ao incluir uma nova estrutura, o combo 'Nível Superior' está exibindo estruturas inativas para serem Pai. Porém, na alteração, as estruturas inativas não são apresentadas no combo, o qual apaga o valor anteriormente informado.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - O "Caracter Separador para Campos Multivalorados", informado em Configuração Geral, não está sendo devidamente exibido na listagem de itens do Cadastro. O problema ocorre quando o caracter "\" é utilizado como separador. <br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro / Acompanhamento - Relatórios Impressos - Em alguns campos do tipo atributo livre o valor exibido no campo corresponde ao nome do Atributo, são eles: upload de arquivo, todos os campos de validação e multitexto.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Administração Sistema - Tabelas - Poder - O sistema exibe mensagem de erro "Ocorreu um erro interno no HibernateErro" ao tentar realizar a exclusão de Poder.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Administração Sistema - Tabelas - Cor - O sistema exibe mensagem de erro "Ocorreu um erro interno" ao tentar realizar a exclusão de Cor. <br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Relatórios Impressos - Acompanhamento - A exibição dos pareceres está diferente da regra definida na listagem do Acompanhamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Correção na regra para exibição dos pareceres emitidos - "Mostrar Últimos(as) Situações Emitidos(as)" e "Mostrar Todos(as) Situações Emitidos(as)". O sistema deve apresentar apenas os pareceres liberados, considerando a configuração realizada no Tipo de Acompanhamento (opções 'Exige Liberar Parecer' e 'Exige Liberar Acompanhamento').<br>

    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento de Indicadores - Considerar critério de tipos indicadores para incluir novos indicadores no acompanhamento. O sistema não considera o critério de tipos de indicadores válidos para o tipo de acompanhamento. Ocorre no momento de listar novos indicadores encontrados no cadastro, apesar de considerar ao tentar incluí-los no acompanhamento.<br> 
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro de Indicadores - O botão limpar no atributo tipo de indicador não atua sobre o dado gravado. Ao alterar o tipo, o sistema grava o dado alterado, mas o limpar não grava o registro sem um tipo associado.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento de Indicadores - No campo de registro do valor realizado o uso do termo "até o mês" deve ser utilizado caso o Indicador seja do tipo NÃO Acumulável. Para Indicador do tipo Acumulável utilizar o termo "no mês".<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Geração de Ciclos de Acompanhamento - Indicadores excluídos no cadastro continuam sendo criados e exibidos no acompanhamento.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro e Acompanhamento de Indicadores. Erro ao informar quantidade prevista e realizada em indicadores de um item sem data inicial ou final informada. Caso o item de estrutura não tenha data de início ou término busca a data dos exercícios cadastrados.<br>
  	
  	</td>
  </tr>
  
  
  <tr> 
  	<td align="center">v8.6 BETA 7</td>
  	<td align="center">ECAR-BR-AJUSTE-8-6-BETA-6</td>  
  	<td align="center">04/06/2010</td>
  	<td>

  	- CORREÇÕES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao desativar uma estrutura de 2º Nível, o acompanhamento dessa estrutura e de suas filhas não aparecem na listagem de posição geral.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na geração de arquivos em Demandas, no IE não está apresentando o arquivo para download ao clicar no botão gerar arquivo, somente está sendo possível baixar o arquivo pelo Firefox.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Demandas - Os atributos livres de texto com validação não estão respeitando a limitação do tamanho do campo, aceitando mais caracteres do que foi configurado em atributo na demanda.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Os atributos livres de texto com validação, do tipo Valor, estão aceitando um número a menos do que o configurado em atributo na demanda.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - A árvore de navegação não aparece ao adicionar um item na estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Sistema - Ao entrar pelo Portal não está sendo possível configurar o sistema para abrir os módulos do sistema já com o menu lateral encolhido. <br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Ao definir o menor nível de visualização para o tipo de acompanhamento, o sistema está colocando a cor azul no fundo dos itens filhos na tela de posição geral.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Utilizando o IE na aba de Etapa de nível inferior um ícone quebrado que é apresentado na listagem.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Na tela de posição geral, utilizando a visualização por menor nível de visualização na configuração do tipo de acompanhamento, os pareceres das funções não estão sendo apresentados conforme a permissão definida no grupo de acesso.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - Metas-Indicadores - Ao alterar uma meta-indicador cadastrada o sistema apresenta a tela de carregamento junto com o aviso de item alterado com sucesso e só termina o carregamento da página após o clique no OK da mensagem.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro - Localização Geográfica - Os botões de adicionar e excluir não estão funcionando no IE.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Na tela de posição geral a partir dos itens ajax, ao clicar no ícone da posição (carinha) o sistema é direcionado para a tela de registro de parecer e não para a de consulta.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Na tela de posição geral a partir dos itens ajax, ao clicar no ícone da posição (carinha) o sistema não direciona para aba configurada no cadastro de exibição de abas e ícones.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Na tela de posição geral a partir dos itens ajax, o sistema não exibe o hint com o nome da estrutura do item.<br>
  	  	
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.6 BETA 6</td>
  	<td align="center">ECAR-BR-AJUSTE-8-6-BETA-6</td>  
  	<td align="center">31/05/2010</td>
  	<td>
  	  	  	 	
  	- MANUTENÇÕES:<br>
  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar conteúdo do campo "Formato" no arquivo de exportação de Metas/Indicadores de "Q" ou "V" para "Número" ou "Moeda" respectivamente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Remover campo "Código de Atuação" do arquivo de exportação de Entidades.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Permitir edição e visualização de itens de estruturas inativas no acompanhamento, apenas para as seguintes abas: Datas Limites, Metas/Indicadores e Situação.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Generalizar mensagens de sucesso durante a manutenção dos Apontamentos das Restrições.<br>
      	  	 	
  	- CORREÇÕES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de cadastro de demandas, não está sendo validada a quantidade de caracteres permitidos para o cadastramento de dados no campo de texto, quando o valor do tamanho do campo definido na configuração é maior do que 80 e vira um textarea.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Os atributos livres do tipo TextArea não estão validando o tamanho do campo conforme a configuração nem em Demandas nem no Cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de cadastro de demandas, o label do primeiro item de campos do tipo MultiTexto está fora de posicionamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Quando definido como página inicial do usuário o módulo de portal, o sistema não está exibindo as mensagens configuradas no cadastro de popup ao efetuar login no sistema.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na geração de arquivos em Demandas, quando prenchido o campo de upload o arquivo de exportação vem sem os outros campos exportados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de filtros de Acompanhamento, ao excluir um filtro criado, sendo este de itens pertencentes a um tipo acompanhamento diferente do que aparece em primeiro, e depois selecionando outro filtro para consulta, ocorre um erro com a seguinte mensagem: "Não foi possível acessar a funcionalidade através desta opção escolhida. Exceção: Index: 0, Size: 0".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na aba de Situação dos Pontos Críticos, no Acompanhamento, para um item que não teve acompanhamento gerado para o ciclo exibido, o sistema está exibindo os pontos críticos deste item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema permite que um usuário, sem permissão de criar filtro pessoal e/ou do sistema, salve uma pesquisa no acompanhamento criando assim um filtro.<br>
 	
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.6 BETA 5</td>
  	<td align="center">HEAD</td>  
  	<td align="center">12/05/2010</td>
  	<td>
  	  	  	 	
  	- MANUTENÇÕES:<br>
  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na configuração de visões, permitir informar um nome de até 500 caracteres para uma visão; e no Cadastramento de Demandas, apresentar os nomes das visões como links.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Exibir, um atributo configurado para usar um grupo de atributo do tipo entrada de texto, um campo texto ou área de texto de acordo com o tamanho configurado para o conteúdo. Se o tamanho configurado para o conteúdo for menor ou igual a 80, exibir o mesmo como texto, caso configurado com conteúdo maior que 80, exibir o mesmo como área de texto - mesmo este utilizando um grupo de atributo com tipo entrada de texto.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Disponibilizar um link no canto superior direito da tela de edição de demanda, que retorne para a listagem de demandas.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Retirar as mensagens de aviso do Internet Explorer quando o sistema for acessado através deste navegador com uso de https.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar mensagens de validação dos campos Sequência e Largura de Função de Acomp. na Estrutura para "Obrigatório o preenchimento do campo Sequência na Lista do Cadastro de Itens quando preenchida a opção Exibir na Lista." e "Obrigatório o preenchimento do campo Largura na Lista do Cadastro de Itens quando preenchida a opção Exibir na Lista.".<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar mensagens de validação dos campos Sequência e Largura de Atributo na Estrutura para "Obrigatório o preenchimento do campo Sequência na Lista do Cadastro de Itens quando preenchida a opção Exibir na Lista." e "Obrigatório o preenchimento do campo Largura da Coluna na Lista do Cadastro de Itens quando preenchida a opção Exibir na Lista.".<br>
      	  	 	
  	- CORREÇÕES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Não está sendo possível excluir manualmente as pastas do cadastro (função Pastas para Anexos). Ao selecionar a pasta já vazia e selecionar o botão de excluir é apresentada a mensagem: Não é possível excluir registro pois ele possui vínculos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O Gráfico de Evolução das Posições não exibe as quantidades corretamente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Função "Beneficiário" do cadastro, ao incluir um beneficiário com quantidade prevista com um valor maior ou igual a 1000000 , o sistema grava formatando o número com ponto. Ao tentar alterar o registro criado, o sistema exibe mensagem indicando que o campo está com valor inválido (Valor inválido para o campo Quantidade Prevista).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema apresenta na listagem todas as demandas que foram cadastradas desde que se tenha pelo menos 1 item marcado na coluna manter demandas e mesmo que a coluna filtrar demandas não tenha nenhuma situação marcada. Demandas - Problemas no posicionamento do ícone de dica de campo em alguns atributos fixos e livres. <br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Demandas - Campos Orgão e Entidade: Na tela de cadastramento de demandas, ao adicionar um orgão ou entidade, o mesmo não recebe a caixa de checkbox, aparece somente o nome incluído na lista de orgão ou entidade, não permitindo a exclusão. Após gravar o item aparecem os checkbox para cada item. Porém se for adicionado algum novo item não é possível excluir os itens que possuem o checkbox, ao clicar no link excluir o sistema não reage.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Campos do tipo radio (sim/não) opcionais não estão sendo gravados sem valor no cadastro de item, ou seja, mesmo clicando no botão limpar, o sistema continua gravando 'Não'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao tentar excluir uma situação usada num parecer, o sistema exibe mensagem de validação com o texto do parecer.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na funcionalidade de 'Situação Pontos Críticos', disponibilizada na barra superior da listagem geral de acompanhamentos, o sistema permite que o usuário selecione a opção 'SELECIONE' no combo de referências. Ao selecionar a opção, o sistema recarrega a tela e exibe a mensagem "erro.ObjectNotFound".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na função de resumo, o sistema não informa corretamente a quantidade de arquivos anexos para a função 'Galeria Anexo'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao clicar no botão cancelar, o sistema desmarca os campos de check 'Manter Demandas' mas não habilita os campos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema não está exibindo as mensagens configurados no cadastro de popup ao efetuar login no sistema.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A consulta de área do site não está retornando o registro 'INFORMAÇÕES'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na geração de arquivos em demandas, o caminho do arquivo de upload é exibido no arquivo exportado.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro de 'null' ao alterar um usuário.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema está gerando ciclos separados por órgão, mesmo que o Tipo de Acompanhamento não esteja com a opção 'separar por órgãos' selecionada.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema permite que o usuário edite um parecer na tela de visualização (clicando nas carinhas).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Há um espaço em branco entre a barra de botões e o início do formulário de cadastro de usuário.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro ao tentar criar um item a partir de modelo.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na geração de ciclos, o sistema não valida corretamente os campos de datas, quando utilizado o padrão: DD/MM/AA.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema apresenta a mensagem 'Ocorreu erro interno' ao tentar associar uma demanda a um item no cadastro de itens.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Demandas, o sistema não grava nome do solicitante.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ocorre erro interno de hibernate ao tentar gravar uma demanda com mais de 50 caracteres no campo 'Nome do solicitante'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema apresenta uma aba em branco no detalhamento de um item (clicar no lápis) na tela de acompanhamento. Erro de String "" ao clicar na aba em branco.<br>
  	 	
  	</td>
  </tr>
  
  
  <tr> 
  	<td align="center">v8.6 BETA 4</td>
  	<td align="center">HEAD</td>  
  	<td align="center">12/04/2010</td>
  	<td>
  	  	  	 	
  	- CORREÇÕES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No arquivo de Dados Gerais exportado estão aparecendo os campos "Registro Ativo?" e "Início Monitoramento", que não aparecem na tela para o usuário.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No arquivo de Dados Gerais exportado, os atributos livres do tipo Upload estão exibindo o caminho completo do arquivo no servidor, o que vai contra a regra de segurança de download recém implementada.<br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- As pastas criadas por usuários na Função Pastas para Anexos são excluídas automaticamente quando se exclui o último arquivo de dentro delas. Esse comportamento deve existir apenas nas pastas criadas por acompanhamentos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A configuração "Exibir na Lista", na tela de configuração de Atributo na Estrutura, não está funcionando. Independente desta configuração estar marcada ou não, o atributo em questão não é exibido na listagem do Cadastro. Porém, se a opção "Exibir na Árvore" for marcada, o atributo passa a ser exibido tanto na árvore de navegação quanto na listagem do Cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O gráfico de Posições no acompanhamento não está funcional. Corrigir a visualização das funções conforme a permissão de acesso definida em grupo de acesso.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- As configurações de função de acompanhamento não estão sendo respeitadas no cadastro e acompanhamento, exemplo: a sequência de apresentação não está sendo respeitada, a configuração de exibir na lista também não está funcional.<br>  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Intercâmbio de Dados há um problema ao informar * no campo "Separador de campos". O sistema entra em loop.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao alterar de uma estrutura com itens adicionando um atributo do tipo ID, o sistema exibe a mensagem de validação "Não é possivel inserir atributo do tipo ID INCREMENTAL ou MASCARA EDITAVEL pois a estrutura atual já possui itens cadastrados" e volta para a tela com os campos desabilitados. Deveria permanecer com os campos habilitados para edição.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Função "Beneficiário" do cadastro, ao incluir um beneficiário com quantidade prevista com um valor maior ou igual a 1000, o sistema grava formatando o número com ponto. Ao tentar alterar o registro criado, o sistema exibe mensagem indicando que o campo está com valor inválido (Valor inválido para o campo Quantidade Prevista).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Função "Metas/Indicadores" do cadastro, ocorre um erro de hibernate ao tentar incluir/alterar uma meta preenchendo o campo "Quantidade Prevista" com mais de 12 dígitos. O erro também acontece na quantidade prevista por local.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro ao tentar gerar arquivo no cadastro de demanda quando não tem nenhum atributo configurado na visão.<br>  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O Campo 'Nome' na tela de atributos na visão não é exibido de forma ordenada.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Adição de * no campo de visão na tela de Cadastramento de Demandas, por este campo ser obrigatório, e remoção de * no campo "Nome" da tela de cadastramento de Visões. Adição do label "* Campos de preenchimento obrigatório" nas seguintes telas, conforme padrão do e-car: cadastros de itens, restrições e demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Tela de Demandas. Para os campos Entidades e Órgão Solucionador, quando estes estão apenas como exibíveis, retirar o combo de seleção e exibir apenas o nome da entidade ou órgão.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao tentar alterar o campo 'código' da função de dados gerais para um número com 10 dígitos (Ex: 9999999999), o sistema não grava o valor.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Cadastro de Itens, o sistema exibe erro de NullPointer ao tentar alterar um indicador, cadastrado na função de metas/indicadores, após o usuário bloquear o planejamento do item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema não está filtrando as demandas quando a visão não tem nenhuma situação associada, ou seja, está exibindo todas quando não deveria exibir nenhuma.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema não deveria permitir a inclusão de atributos na visão com a opção 'Editável' selecionada sem que o usuário marcasse também a opção 'Exibível'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de Atributo na Visão, alterar mensagem de validação na inclusão de atributos obrigatórios, informando sobre a dependência com os dois campos 'Exibível' E 'editável'. Atualmente o sistema exibe a mensagem com um 'OU': "Não pode existir atributo obrigatório se não for editável OU exibível".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema perde os valores dos campos 'Nome' e 'Visão' ao recarregar a tela de Atributos na Visão, após erro na validação do campo 'Restritivo'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao gerar ciclo, o sistema não está montando a hierarquia completa dos itens.  O erro acontece quando a sala de situação do item superior está diferente da sala de situação do tipo de acompanhamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro ao clicar nos links "Dúvidas Frequentes" e "Glossário" que aparecem no cabeçalho.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao detalhar um item no cadastro e tentar adicionar um critério, caso o usuário não selecione um dos critérios resultantes da pesquisa e acione o botão adicionar, o sistema exibe a mensagem "Selecione uma opção!", mas após isto exibe uma outra mensagem com erro: "Não foi possível acessar a funcionalidade através desta opção escolhida.  Exceção: For input string: ".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao detalhar uma demanda, os campos "Comentário" da aba Encaminhamento e "Descrição" da aba Anexos estão editáveis mesmo quando a visão não está configurada com as opções de Alterar para Encaminhamento e Anexo Demanda.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao detalhar uma demanda, os checkbox do campo "Arquivos Anexos" só deverão ser exibidos quando a visão estiver configurada com a opção "Excluir Anexo Encaminhamento".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Capa Portal, ao clicar na imagem de uma matéria ou no link "Leia mais", o sistema exibe uma tela de Erro.
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Capa Portal, ao clicar no link de uma taxação cadastrada, o sistema exibe tela de Erro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de Função de Acompanhamento na Estrutura, remover obrigatoriedade dos campos do tipo checkBox, adicionar obrigatoriedade no campo "Sequência" (Tela de cadastramento) e alterar a mensagem de validação para: "Obrigatório preenchimento do campo sequência" (O sistema deve manter o foco no campo sequência após a validação).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Pesquisa na Estrutura, o histórico de Dados Gerais está indisponível a partir do item pesquisado na estrutura. O sistema exibe a mensagem: Não foi possível acessar a funcionalidade através desta opção escolhida. Exceção: For input string: "null" quando o botão histórico é acionado. A Alteração do item também está indisponível.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Administração do Portal, cadastro de Matérias, ao excluir as imagens anexadas a uma matéria os rótulos dos campos não estão se comportando como devido. Apenas o nome do campo deveria ser exibido, mas o sistema exibe "Alterar [nome do campo] para". O erro também ocorre no cadastro de Taxações e de Categorias.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Administração do Portal, cadastro de Taxações, ao excluir as imagens anexadas a uma taxação o sistema exibe a figura de imagem quebrada, junto do rótulo "[nome do campo] Atual, com a opção de excluir. O erro também ocorre no cadastro de Categorias.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Ciclos de revisão (Administração do Sistema), na tentativa de inclusão sem o preenchimento dos campos obrigatórios, o sistema exibe a mensagem: "Mensagem não econtrada".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Ciclos de revisão (Administração do Sistema), o campo "Descrição" marcado como obrigatório não está sendo validado. Tanto a inclusão, quanto a alteração com este campo em branco estão sendo permitidas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Administração do Portal, cadastro de Matérias, após excluir a imagem com sucesso, caso o usuário tente excluir a matéria, o sistema exibe a mensagem: "Ocorreu um erro interno". Além disso a tela de Capa Portal trava. O erro também ocorre nas telas de Taxação e Categorias.<br>
  	   	
  	
  	
  		
  	 	
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.6 BETA 3</td>
  	<td align="center">HEAD</td>  
  	<td align="center">26/03/2010</td>
  	<td>
  	- MANUTENÇÕES:<br>
  	  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Impedir a exibição no navegador do caminho direto do arquivo no download, bem como verificar a permissão de acesso do usuário, não permitindo que usuários acessem arquivos do sistema diretamente pelo navegador.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Permitir a configuração da exibição das opções de imprimir e gerar arquivos de acordo com o Grupo de Acesso.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Disponibilizar a extração de dados da funcionalidade de acompanhamento de itens do e-CAR para arquivos de texto, como forma de exportação para uso fora da fronteira do sistema. Os arquivos serão gerados de acordo com as abas configuradas para o tipo de acompanhamento do ciclo de referência selecionado e com as funções cadastradas para cada estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a forma de exibição dos itens na listagem de monitoramento. Após a alteração, todos os itens deverão ser ordenados por estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar o perfil de configuração para importação de dados do PACInter para permitir a inclusão de itens por tipo de empreendimento. Após a alteração será possível importar itens para mais de um item da estrutura do item de nível superior.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a funcionalidade de importação manual via arquivo texto, de dados de empreendimentos do PACInter para itens de estrutura no e-CAR, distribuindo os empreendimentos nas cidades da copa cadastradas, considerando também a distribuição dos itens importados por tipo de empreendimento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Permitir consulta aos dados de log gerados a partir do processamento do arquivo de importação.<br>
  	 	
  	- CORREÇÕES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Caso um usuário liberasse um parecer emitido, somente usuários na Função de Acompanhamento da mais alta hierarquia conseguiam recuperar o parecer.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No cadastro, ao anexar algum arquivo nos campos de upload de imagem o mesmo apresenta a imagem quebrada na tela de edição e só mostra a imagem anexada ao realizar a gravação do item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na Listagem de Posição Geral do acompanhamento, os campos do tipo: CheckBox, ListBox, ComboBox e  RadioButton apresentam o conteúdo sem o último caracter.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao visualizar determinadas visões os campos que foram preenchidos e gravados em outra visão estão aparecendo sem conteúdo. O problema ocorre ao gravar uma demanda com um campo bloqueado (não editável).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O menu lateral esquerdo não está aparecendo para o usuário admin. Para os demais usuários aparece normalmente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela inicial do cadastramento de demandas (quando esta é a tela inicial quando o usuário loga no sistema) o formulário exibe os botões de cancelar e consultar um em cima do outro no Firefox. Isso não acontece quando se clica no menu para acessar a tela.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao tentar excluir uma visão de demanda (que foi configurada para ser utilizada na Associção de demandas) o sistema exibe mensagem de erro interno de hibernate.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao tentar excluir uma situação de demanda (que está sendo utilizada) o sistema exibe mensagem de erro com exceção Java.lang.Long.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Administração do Portal > Taxação: no formulário de Taxações a mensagem de "Campos de preenchimento obrigatório" está sendo exibida repetida. O mesmo ocorre nos seguintes formulários também em Administração do Portal: Perguntas Frequentes > Categorias, Itens Livres > Segmentos, Itens Livres > Categorias.<br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Uma mensagem de erro está sendo apresentada durante a tentativa de exclusão de algumas funções de acompanhamento. O mesmo erro ocorre em: Administração Sistema > Tabelas > Entidade > Entidade.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Está ocorrendo um erro interno durante a navegação na paginação das Matérias.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A partir da listagem de situação geral de pontos críticos, os itens aparecem com link para edição. O link deve ser retirado, pois ao clicar nele o sistema mostra o item em edição mas nenhuma aba de lista fica selecionada e os botões avançar e retroceder ficam desabilitados (mesmo havendo itens).<br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na listagem geral de pontos críticos, não está sendo verificado se o usuário tem permissão de edição e/ou visualização para exibir o checkbox de geração do relatório geral de ptos criticos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro de itens de estrutura (árvore ajax). O atributo Grupo de Planejamento configurado para aparecer na listagem e na árvore do cadastro é exibido com valor na listagem e sem valor na árvore.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Árvore de Ajax do cadastro. Ao clicar para detalhar um item e então clicar na aba de Localização Geográfica, a árvore de ajax para de funcionar.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de exibição de abas e ícones, o sistema não valida o valor inserido no campo 'ordem' dos links, permitindo que o usuário informe valores muito grandes (o que gera erro no sistema).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro botão avançar e retroceder. Ao adicionar/alterar/excluir um item na aba de Diário de Bordo e depois clicar no botão avançar/retroceder, o sistema avança/retroceder mas encaminha o usuário para a aba selecionada como padrão para a edição, quando deveria direcionar o usuário para a mesma aba do item anterior (no caso Diário de Bordo). O mesmo ocorre com as abas: Dados Gerais, Datas Limites, Restrições, Apontamentos e Metas/Indicadores.<br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na geração de ciclos, ao alterar um ciclo para remover um acompanhamento existente, o sistema exibe a seguinte mensagem: "Existem 'x' acompanhamentos já informados para exclusão: 'nome dos acompanhamentos'. Confirma exclusão?". Mensagem alterada para: "Os valores informados para o realizado físico dos seguintes itens serão excluídos: 'Item A', 'Item B', 'Item C'. A exclusão poderá influenciar na visualização dos valores em outros tipos de acompanhamentos associados aos itens, bem como ciclos posteriores que utilizem valores acumulados. Deseja excluir acompanhamento?"<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Aba Relatório Impresso. Ao clicar na opção para selecionar as opções de impressão, o sistema perde as informações dos botões avançar e retroceder e estes aparecem desabilitados<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Aba Relatório Impresso. Ao clicar na aba de relatório impresso as demais abas ficam sem ação. No console de erros do navegador aparece a seguinte mensagem: trocarAba is not defined.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro > Empreendimentos > "Detalhar Item" > Aba Beneficiário. Ao tentar excluir um beneficiário, o sistema exibe a mensagem de sucesso, mas não o exclui.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Cadastro > Empreendimentos > "Detalhar Item" > Aba Critérios. Ao tentar excluir um critério, o sistema exibe a mensagem de sucesso, mas não o exclui.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Monitoramento > Visualização - botão avançar/retroceder e link [voltar] na aba de galeria. Ao usar o botão avançar/retroceder na aba de galeria de anexos (na visualização), o sistema não avança ou retrocede e o link [voltar] some da árvore de localização. O mesmo acontece em Dados Gerais.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Copiar item como modelo. Ao copiar um item, o sistema copia os arquivos anexos, porém ao excluir o arquivo de um dos itens, o sistema apaga dos dois.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- MONITORAMENTO > REGISTRO. O botão recuperar abaixo do parecer não está validando a hierarquia das funções de acompanhamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Demandas > Aba de dados gerais da demanda. Na aba que exibe os dados da demanda, os campos do tipo upload de arquivo sempre aparece em branco. Exibir o nome do arquivo, e se for imagem exibe também a imagem.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na visualização de situação de um item, o ícone da cor exibida para a função de acomp Responsável está diferente do ícone configurado na tabela de cor.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Lista geral de monitoramento. Na árvore montada na lista geral do monitoramento, os campos de texto (entrada de texto e textarea) estão sendo exibidos com ';' ao final. O mesmo ocorre com entradas de texto com validação.<br>
  		
  	
  	
  	 	
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.6 BETA 2</td>
  	<td align="center">ECAR-BR-IMPORTACAO-MELHORIAS-20100305</td>  
  	<td align="center">10/03/2010</td>
  	<td>
  	- MANUTENÇÕES:<br>
  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Adequação do layout de importação para inclusão de novos campos e indicação do item superior.<br>
  	 	
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.6 BETA 1</td>
  	<td align="center">HEAD</td>  
  	<td align="center">05/02/2010</td>
  	<td>
  	- MANUTENÇÕES:<br>
  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Exportação de Dados do Cadastro de Itens. Disponibilizar a extração de dados de itens da funcionalidade de cadastro do e-Car para arquivos de texto, como forma de exportação para uso fora da fronteira do sistema. Os arquivos serão gerados de acordo com as funções cadastradas para cada estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Remoção de campo indicativo de função de acompanhamento em restrições.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Remover o campo que indica a função de acompanhamento relacionada ao ponto crítico (restrição) cadastrada no acompanhamento. Com a alteração o campo não deve aparecer disponível na configuração da estrutura.<br>
  	
  	- CORREÇÕES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Internet Explorer (6.0), ao entrar na tela de Cadastro, através do menu superior CADASTROS ou login no sistema, não era possível ordenar a listagem de itens clicando sobre os nomes das colunas. Era necessário navegar no cadastro (ex.: detalhar um item, acessar outra aba de estrutura) para que o sistema voltasse a permitir ordenação da listagem clicando nos nomes das colunas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No relatório do Cadastro, o caracter separador de campos multivalorados aparecia no final dos campos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Caso um usuário liberasse um parecer emitido, somente usuários na Função de Acompanhamento da mais alta hierarquia conseguiam recuperar o parecer. Por exemplo, criadas 3 Funções de Acompanhamento, com a seguinte hierarquia: Diretor -> Gestor -> Responsável, o parecer emitido e liberado pelo Responsável não podia ser recuperado nem pelo Responsável, nem pelo Gestor, somente pelo Diretor.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Mesmo que o projeto não possuísse Data de Início informada, o sistema estava exibindo exercícios sem valor previsto ou realizado. <br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Havia erro ao criar um item baseado em modelo navegando pela área da direita . Quando a navegação no sistema era feita pela área de detalhamento (área à direita da tela), após criar um item baseado em um modelo, o sistema se perdia e, ao clicar em "Ir para Listagem", trazia todos os itens do nível indicado, sem pai algum. Além disso, caso o usuário saísse dessa tela, não era mais possível acessar o novo item, ou mesmo saber onde ele foi criado.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Impossível re-gerar Ciclo de Acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro interno do hibernate ao tentar alterar ciclo de acompanhamento.<br> 	
  	
  	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.5</td>
  	<td align="center">HEAD</td>  
  	<td align="center">22/01/2010</td>
  	<td>
  	- MANUTENÇÕES:<br>
  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Uniformização da apresentação do item de estrutura. Ao apresentar um item de estrutura, o sistema deverá considerar os atributos configurados para serem exibidos na árvore.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configuração da aba de situação geral de indicadores. Retirar as abas referentes à situação geral de indicadores das telas de acompanhamento  e o acesso a sua configuração.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Reformulação das abas Situação e Resumo. Apresentar na aba de situação a data limite do parecer para cada função de acompanhamento; Reformular a apresentação da aba de resumo.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Apresentação de título na coluna de órgão na listagem de itens em acompanhamento. Retirar o rótulo ': Todos' da coluna associada a órgão na listagem de itens de acompanhamento, apresentado apenas o nome configurado para a coluna.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ordenação de itens de estrutura na árvore de navegação. Os itens de estrutura na árvore de navegação devem ser apresentados ordenados, considerando apenas o primeiro atributo visível na árvore.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ordenação em Situação Geral de Pontos Críticos. Ordenação decrescente de pontos críticos, na listagem dos itens acompanhados, através do atributo data limite; Se existirem pontos críticos com datas limites iguais , eles serão ordenados de acordo com o conteúdo da 1ª coluna, seguindo a ordem alfabética; Se existirem pontos críticos sem data limite, eles serão exibidos após os pontos críticos que possuem data limite e ordenados de acordo com o conteúdo da 1ª coluna, seguindo a ordem alfabética.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Árvore Ajax e Filtros. Filtragem dos itens contidos na árvore de navegação (com Ajax) e na árvore de localização do cadastro de itens e a manutenção da opção de filtro ("Concluídos" ou "Não Concluídos" ou "Todos") selecionada pelo usuário enquanto este navegar pelas funcionalidades de cadastro de itens.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Recolhimento de Datas Críticas em Situação Geral de Pontos Críticos. Possibilidade de agrupar/desagrupar os pontos críticos de um item específico na listagem dos itens acompanhados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ordenação em Situação Geral de Pontos Críticos. Ordenação decrescente de pontos críticos, na listagem dos itens acompanhados, através do atributo data limite; Se existirem pontos críticos com datas limites iguais , eles serão ordenados de acordo com o conteúdo da 1ª coluna, seguindo a ordem alfabética; Se existirem pontos críticos sem data limite, eles serão exibidos após os pontos críticos que possuem data limite e ordenados de acordo com o conteúdo da 1ª coluna, seguindo a ordem alfabética.<br>  	  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Regras para Super-Função. Armazenar o código da função associada ao usuário que altera (emite, libera ou recupera) o parecer, pois em um outro momento, esse usuário pode precisar alterar esse parecer e não estar mais associado a função de acompanhamento armazenada; Retirar da tela de configuração de função de acompanhamento a seleção automática das funções filhas de uma função que for selecionada para o campo "Pode alterar parecer de função filha".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Correções e Melhorias no cadastro de atributos livres. Em todas as telas de grupo de atributo que exibe o indicador de uso no cadastro do usuário, o campo referente ao indicador de uso no Meu Cadastro (indicador de cadastro no site, sim ou não) deverá ser exibido. Este campo só será habilitado se o grupo de atributo for configurado para ser utilizado no cadastro de usuário. Além disso, o campo que indica o tipo de exibição no cadastro de usuário também deverá ser exibido.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Tabela de Gráfico de Gantt. Exibir os pontos críticos ordenados no gráfico de Gantt, no cadastro e no acompanhamento, segundo a ordem decrescente de data limite informada; Exibir apenas os pontos críticos com data limite informada; A mensagem "Não há restrições cadastradas com data limite informada." será exibida ao usuário caso a aba do gráfico da Gantt seja acionada e as restrições cadastradas não possuam data limite informada.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Anexação de arquivos. Padronizar no sistema a forma de apresentação para anexar arquivos; Gerar nome interno ao sistema para evitar problemas com anexação de mais de um arquivo utilizando mesmo nome; Permitir a exclusão de arquivos anexados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configuração da Aba de Resumo. Tornar o acesso a registro e visualização do acompanhamento configurável, ou seja, definir através do sistema para que aba direcionar o usuário quando o mesmo acessar registro e/ou visualização do acompanhamento; Para as opções de avançar e retroceder para itens no acompanhamento, direcionar sempre o usuário logado para a mesma aba em uso no item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Correções e Revisão das Regras de Metas/Indicadores. Validar a regra de exibição dos exercícios na tela de resultados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alteração de Rótulo na Configuração de Função de Acompanhamento. Tornar os rótulos "Ativar Monitoramento" e "Desativar Monitoramento" configuráveis<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configuração de perfil para importação de empreendimentos do PACInter. Disponibilizar configuração para importação de dados do PACInter para o e-CAR através de um perfil pré-definido.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Adicionar validação na exclusão de situação de item. Impedir que uma situação de item associada a um perfil de configuração de intercâmbio de dados seja excluída.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Adicionar validação na exclusão de órgão. Impedir que um órgão associado a um perfil de configuração de intercâmbio de dados seja excluído.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Adicionar validação na exclusão de estrutura. Impedir que uma estrutura associada a um perfil de configuração de intercâmbio de dados seja excluída.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Adicionar validação na exclusão de usuário. Impedir que um usuário associado a um perfil de configuração de intercâmbio de dados seja excluído.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Importar empreendimentos do PACInter. Disponibilizar a funcionalidade de importação manual via arquivo texto, de dados de empreendimentos do PACInter  para itens de estrutura no e-CAR, distribuindo os empreendimentos nas cidades da copa cadastradas.<br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Uniformização da apresentação dos caracteres separadores dos campos multi-valorados na listagem do Cadastro. O sistema passou a utilizar o campo "Caracter Separador para Campos Multivalorados", informado em Configuração Geral, na listagem do Cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Uniformização da apresentação dos caracteres separadores dos campos multi-valorados na geração de relatórios. O sistema passou a utilizar o campo "Caracter Separador para Campos Multivalorados", informado em Configuração Geral, na geração de relatórios do Cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Remoção da linha exibida com o resumo dos pareceres durante a geração de relatórios na tela de acompanhamentos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Remoção da faixa em branco apresentada no formulário da função de anexação de arquivos ("Pastas para Anexos").<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Aplicação de nova regra na manutenção de ponto critico em Acompanhamento. No acompanhamento, após liberar o parecer, o sistema deixava de apresentar os botões de criação e de alteração dos pontos críticos cadastrados por meio do acompanhamento, somente sendo possível alterar/criar pelo cadastro. A criação e alteração dos pontos críticos deve ser independente da liberação ou não dos pareceres, respeitando somente a permissão do usuário quanto à alteração / exclusão.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alteração da regra de apresentação do botão "usar modelo". Ao invés de apresentar o botão se o atributo "é modelo" estiver sendo utilizado na estrutura, foi criado um checkbox na configuração da criação da estrutura para indicar a apresentação do botão ou não (assim como os checkbox para exibir o botão de geração de arquivos e botão de imprimir).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Implementação de melhorias na Segurança do Sistema:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Removido o acesso a pastas não permitidas via a utilização das cadeias de caracteres "../" ou "..\\" de navegação em sistemas de arquivos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Autenticação de quem estava tentando fazer o download não era feita. Está sendo verificado se há usuário na sessão e se ele está logado quando se tenta acessar diretamente o arquivo via URL.<br>
  	
  	  	
  	- CORREÇÕES DE ERROS:<br>
  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O filtro de Entidade Solucionadora, no cadastro de demandas, não funcionava corretamente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de exibição de anexos de demandas, o botão excluir estava sempre presente, mesmo que a visão em questão não permitisse excluir anexos (no Internet Explorer, o botão gerava um erro na página).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Caso o usuário não possuísse nenhuma entidade solucionadora informada em seu cadastro, ocorria erro interno ao detalhar uma visão que utilizasse o filtro de 'entidade solucionadora'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Os campos 'status' e 'prioridade' permaneciam editáveis na demanda, mesmo que não estivessem marcados como editáveis na configuração de Atributo na Visão.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Cadastro de Itens, caso o campo 'Nome' de um item possuísse mais de uma linha, o sistema removia a quebra de linha, exibindo o texto sem espaços.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A opção 'alterar anexo de encaminhamento', da configuração de visões, não tinha utilidade. Existindo apenas essa permissão selecionada, nenhuma ação era possível sobre os arquivos anexados (opção removida).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema não permitia que o usuário alterasse uma descrição de um arquivo anexado.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro na exibição do nome do item apresentado no pop-up de anotações na listagem do acompanhamento. Adição de hífen entre o nome da estrutura e o nome configurado para o item de estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Removida configuração da aba Situação Indicadores da tela de grupos de acesso.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O valor do campo 'Cadastro do Site' dos atributos livres do cadastro de usuário não era exibido na tela de alteração de um registro existente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtro de Pesquisa Personalizada (Acompanhamento) não executava nenhuma ação quando o botão de avançar era acionado antes da página estar completamente carregada. Foi inserida imagem com mensagem "Aguarde, carregando..." para ser exibida até que a página estivesse totalmente carregada, a exemplo do que ocorre na tela de listagem do cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ordenação da árvore AJAX não funcionava corretamente. Quando configurado outro atributo para ser exibido na primeira posição da árvore, com exceção do nome, a árvore não era ordenada corretamente. Além disso, caso um atributo não possuísse valor o sistema não colocava os hifens.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Revisadas todas as possibilidades de definição da função a ser direcionada ao acessar o registro ou visualização no acompanhamento. Por exemplo, ao definir como função inicial a aba de dados gerais na visualização, o sistema apontava para uma página inexistente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acrescentada a aba de Resumo na tela de Exibição de Abas e Ícones, pois do contrário ela nunca poderia ser configurada como 1ª aba da tela de registro ou visualização.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em grupo de acesso era apresenta a possibilidade de utilizar uma aba sem correspondência com as funções do cadastro e acompanhamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao gerar um relatório no acompanhamento, os atributos livres estavam vindo com o conteúdo null.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O campo de Data Limite foi posicionado abaixo do campo de observações, na função de Situação dos Pareceres.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A linha Data Limite - Físico - correspondia exatamente a data limite da liberação das metas-indicadores, portanto tornou-se uma linha com informação duplicada. A linha foi removida.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Foi alinhado o label com o campo referente à apresentação da sinalização atual e da sinalização até uma data específica, na tela de Situação de Pontos Críticos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao gravar um ponto crítico, o campo estados e envio de e-mails estava sendo preenchido com os valores 0 e sendo ativada a configuração de envio de e-mails. O comportamento normal seria de não preencher nenhum valor caso não fosse informado pelo usuário. Desta forma, na alteração do item acusava o erro de preenchimento de valores iguais para as cores diferentes e ainda habilitava o envio de e-mails.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Hifens estavam sendo utilizados para separar valores de um mesmo campo, na uniformização da apresentação de um item. O sistema passou a utilizar o caracter de separação informado em Configuração Geral.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Acompanhamento, o sistema não permitia alteração da descrição de anexo de acompanhamento. Ao clicar sobre o nome do anexo para alterá-lo, o sistema exibia a tela com todos os campos vazios, permitindo a criação de um novo anexo para substituir o existente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Após detalhar um item no Acompanhamento, se o usuário clicasse em "Lista Geral", o filtro de Nível de Planejamento era perdida.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Acompanhamento, caso o detalhamento de um item fosse feito através da opção visualizar, os botões "Avançar" e "Retroceder" levavam sempre para a aba "Resumo", independente de qual estivesse ativa no momento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Acompanhamento, ao se detalhar um item que possuísse restrições, a aba "Restrições" não ficava marcada como se estivesse ativa. Caso o detalhamento fosse feito através da opção visualizar, ao se clicar nos botões "Avançar" ou "Retroceder", os mesmos passavam a ficar desabilitados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Cadastro, mesmo que os filtros "Todos" ou "Concluídos" fossem definidos como padrão na configuração geral, o sistema exibia na área da direita apenas os itens não concluídos. Era necessário clicar sobre o filtro "Todos" para que o sistema exibisse realmente todos os itens.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema não permitia alterar alguns ciclos de acompanhamento. O botão "Alterar Acompanhamento" não executava ação alguma. Novos ciclos de acompanhamento criados podem ser alterados normalmente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na lista geral de Situação de Pontos Críticos no Acompanhamento, no Internet Explorer o sistema não identava itens pais e filhos, ficando todos alinhados no mesmo nível. Além disso, o alinhamento do primeiro item na tela não seguia o dos demais itens, tanto no Internet Explorer quanto no Firefox.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Cadastro, usando o Firefox, ao tentar fazer upload de um arquivo em um item através de um atributo livre do tipo anexo, o sistema exibia a mensagem "Erro interno no Hibernate". Usando o Internet Explorer, o sistema permitia a inclusão, porém exibia a mensagem "Ocorreu um erro interno no Hibernate" após o usuário excluir o anexo e tentar gravar o item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Acompanhamento, tela de resultados, caso um item não possuísse valor na sua Data de Início, o sistema não exibia os exercícios que tinham apenas valores realizados, nem considerava esses valores na totalização. Além disso, o sistema estava exibindo todos os exercícios até a Data de Término, mesmo os que não possuíam valor algum; quando deveria exibir somente os exercícios com valores previstos ou realizados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Caso um usuário liberasse um parecer emitido, somente usuários em Funções de Acompanhamento superiores conseguiam recuperar o parecer.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O filtro de Demandas não estava funcionando corretamente. Ao acionar a opção "Filtrar", caso não fossem adicionados órgão solucionadores ao filtro, o sistema retornava a mensagem "Nenhum registro foi encontrado para os critérios de filtro especificados.", mesmo que existissem demandas que satisfizessem os filtros.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Cadastro, a listagem de itens é ordenada pelo primeiro campo da lista. Ao clicar no nome do campo (coluna), o sistema também ordena a listagem. Porém, essas ordenações estavam diferentes, embora devessem usar os mesmos critérios de ordenação.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na ordenação da listagem do cadastro, não estava mais funcionando a ordenação decrescente ao clicar duas vezes em alguma coluna do cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Correções realizadas nas tabelas de cores. Verificada a tela de configuração das cores do sistema, que estava definindo como obrigatório o preenchimento de todos os campos da tabela. Verificada também a chamada do link de cores para a sinalização dos pareceres.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Correção na Recuperação de Parecer. Não estava sendo possível a gravação nem a recuperação dos pareceres emitidos. O sistema apresentava o erro "null".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Correção de erro ao abrir um anexo na galeria de anexos. No detalhamento de um acompanhamento, ao clicar na função "Galeria" e selecionar a opção para detalhar uma pasta, o sistema não exibia corretamente o popup de detalhamento da pasta, com as imagens pertencentes à pasta.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Problemas na criação de um item a partir do modelo foram corrigidos:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao criar um novo item baseado em um modelo, o item criado apresentava o campo "É modelo" marcado como sim, dessa forma criava um novo modelo a cada novo item criado. Este campo deveria vir em branco para os novos itens criados a partir de um modelo.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Quando o usuário criava um novo item a partir de um modelo, o novo item era sempre criado embaixo da estrutura pai do item que serviu de modelo.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No Acompanhamento, tela de resultados, caso um item não possuísse valor na sua Data de Início, o sistema não exibia os exercícios que tinham apenas valores realizados, nem considerava esses valores na totalização.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Correção na a tela de 'Situação', no Registro de acompanhamentos, exibindo o botão Recuperar, sempre que o usuário for responsável por uma função de acompanhamento, mas um usuário de nível superior liberou o parecer. O sistema exibirá o botão para recuperar o parecer, e, caso o usuário clique no botão, a seguinte mensagem será exibida: "O PARECER só pode ser recuperado por função igual ou superior a que liberou".<br>
  	
  	</td>
  </tr>
  
  
  <tr> 
  	<td align="center">v8.4.5</td>
  	<td align="center">ECAR-BR-08-04-00-20091023</td>  
  	<td align="center">26/03/2010</td>
  	<td>
  	
  	- MANUTENÇÕES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configurar na visão a exibição de situações disponíveis para as demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Modificar padrão de ordenação dos atributos listados no detalhamento de demandas.<br>
  	- CORREÇÕES DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao tentar incluir uma visão com o mesmo nome de uma existente, o sistema apresentava a mensagem "Já existe um Atributo com o nome ou o label padrão informado."  Este comportamento também ocorria durante a alteração.<br>
	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.4.4</td>
  	<td align="center">ECAR-BR-08-04-00-20091023</td>  
  	<td align="center">26/02/2010</td>
  	<td>
  	
  	- CORREÇÕES DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Os atributos livres definidos como não editáveis em uma visão estão tendo seus valores sobrescritos com valor em branco quando há gravação da demanda.<br>
	</td>
  </tr>
  
   <tr> 
  
  <tr> 
  	<td align="center">v8.4.3</td>
  	<td align="center">ECAR-BR-08-04-00-20091023</td>  
  	<td align="center">08/12/2009</td>
  	<td>
  	
  	- CORREÇÕES DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao selecionar mais de uma Prioridade no Filtro de 'Cadastramento de Demandas', o sistema apresenta a mensagem "ocorreu um erro interno".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema não filtra corretamente ao selecionar múltiplas opções nos filtros de checkbox de atributos fixos de demandas.<br>
	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.4.2</td>
  	<td align="center">ECAR-BR-08-04-00-20091023</td>  
  	<td align="center">27/11/2009</td>
  	<td>
  	
  	- CORREÇÕES DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Quando o atributo "Nome" de um item tem mais de uma linha, ao clicar no "+" para listar, o sistema fica eternamente na tela de Aguarde, carregando...<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema não permite que o usuário exclua itens de uma estrutura de primeiro nível. Mesmo que o usuário selecione um item da estrutura e clique na opção 'Excluir' o sistema exibe a mensagem: "Pelo menos um registro deve ser selecionado".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O filtro de entidade solucionadora no cadastro de demandas não está funcionando corretamente.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de exibição de anexo de demanda, o botão Excluir está sempre presente, mesmo que a visão em questão não permita excluir anexos. No Internet Explorer, o botão gera um erro na página.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Quando um usuário que não possui Entidade Solucionadora no seu cadastro tenta acessar uma Visão que utiliza regra de Entidade Solucionadora, o sistema exibe um erro interno.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Os campos Status e Prioridade ficam sempre editáveis na demanda, mesmo que não tenham sido marcados como editáveis na configuração de Atributo na Visão.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Quando o campo Nome de um item possui mais de uma linha, o sistema está juntando o texto do final da linha com o do início da próxima linha.<br>  	
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Opção alterar anexo de encaminhamento da configuração de 'visões' não tem utilidade. Caso só essa permissão seja marcada para anexos de encaminhamento, não é possível executar ação alguma sobre ele.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Sistema não permite que o usuário altere uma descrição de um arquivo anexo.<br>
	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.4.1</td>
  	<td align="center">ECAR-BR-08-04-00-20091023</td>  
  	<td align="center">27/10/2009</td>
  	<td>
  	
  	- CORREÇÕES DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao mudar o filtro, perde-se a referência da árvore de navegação e os itens apresentados não são filtrados conforme a hierarquia definida, são apresentados todos os itens cadastrados para a estrutura que estava sendo visualizada.<br>
  	
	</td>
  </tr>
  
  <tr> 
  	<td align="center">v8.4</td>
  	<td align="center">HEAD</td>  
  	<td align="center">01/10/2009</td>
  	<td>
  	- MANUTENÇÕES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alteração na rotina de gravação de indicadores automáticos. Esta alteração teve como finalidade garantir que os indicadores dos acompanhamentos, definidos para utilizar serviços de forma automática e que tenham data limite para informar realizado físico expirada, sejam gravados diariamente. A gravação deixou de ser vinculada ao envio de e-mail, passando a ser executada de forma independente sempre antes da rotina de envio de e-mails, quando esta estiver configurada para ser executada no mesmo dia.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alteração nos Componentes de Edição de Textos. Os textos copiados de fontes externas ao sistema e colados nos componentes de edição devem manter sua formatação original. Os componentes de edição não exibiam ou exibiam com erros os campos nos quais uma página da internet ou trechos de texto do BROffice eram copiados e colados.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alteração na forma de associação de demandas aos itens do cadastro de itens. Com a alteração, será necessário que o usuário informe, na tela de configuração geral do sistema, qual visão de demandas será utilizada durante a associação.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Nova regra para os números informados no campo "Antecedência (em dias)" em Restrições, constante na tabela de Estados e Envio de e-mails. Os números informados para cada estado deverão ser distintos.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alteração na exibição de resumo dos pareceres das funções de acompanhamento, na aba de "Resumo".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Inclusão do rótulo "Data Limite: " antes das datas limites associadas aos pareceres. <br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em cada função de acompanhamento com parecer liberado, apresentação da data do parecer logo após o ícone associado ao mesmo, utilizando o rótulo: "- Liberado em: " entre o ícone e a data associada ao parecer.<br>
  	
  	- CORREÇÕES DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Apresentação de dicas na função 'Dados Gerais'. As dicas de alguns tipos de atributos livres não estavam sendo exibidas na função 'Dados Gerais'. O problema também ocorria na tela de histórico da função.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ativação de prioritário quando existem campos obrigatórios bloqueados. O sistema não validava corretamente quando o usuário selecionava a opção para 'ativar prioritário' na função 'Dados Gerais'. Não era possível ativar prioritário quando existiam campos obrigatórios bloqueados. Foi utilizada a mesma regra do botão Bloquear/Liberar Planejamento para não validar campos obrigatórios ao acioná-lo.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Validação de Datas na função 'Restrições'. O sistema não fazia validação quando o usuário informava no campo 'Antecedência em dias' uma data posterior a data definida no campo 'Data Limite'.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Correção na validação de filtros do tipo data em Acompanhamento. A validação do sistema nos campos data não permitia que o usuário realizasse consultas utilizando data inicial igual a data final.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Configuração de atributos restritivos em demandas. Para algumas situações o atributo definido como restritivo (em atributo na visão) não estava sendo exibido corretamente no cadastro de demandas. Isso ocorria com as seguintes combinações no cadastro de grupos de atributo livre:
									Tipo de Exibição Cadastro de Demandas - ComboBox (aparecia checkbox)
									Tipo de Exibição Cadastro de Usuário - ListBox
									e
									Tipo de Exibição Cadastro de Demandas - ComboBox (aparecia checkbox)
									Tipo de Exibição Cadastro de Usuário - CheckBox.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Corrigida a ocultação indevida do cabeçalho na listagem de pontos críticos. Na listagem geral de restrições, em acompanhamento, havia situações em que o cabeçalho da listagem de pontos críticos não aparecia, mesmo que o item possuísse pontos críticos. Essa situação ocorria dependendo do filtro de estado dos pontos críticos setado na parte superior da tela.<br>								
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Mudança na forma de alteração de ciclos gerados para correção de erro de produção. Alguns acompanhamentos gerados não apresentavam, na aba 'datas limites', as datas limites de todas as funções de acompanhamento utilizadas para o monitoramento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Correção na alteração de ciclos gerados, permitindo ao usuário retirar o acompanhamento de itens concluídos. Durante a alteração, o sistema desabilitava os itens com situação 'concluído' e acompanhamento gerado.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Exclusão de pasta criada na função "pastas para anexos" no cadastro de itens, quando o último arquivo anexo da pasta for excluído.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Durante a digitação do parecer na função de acompanhamento, se o usuário apertasse ENTER após o final do texto e acionasse a opção de gravar, ao acessar a aba Galeria nenhuma das quatro pastas fixas aparecia.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na geração de ciclo de acompanhamento, não é exibido o componente de calendário para os prazos das Funções de Acompanhamento. O componente também não é exibido nos campos de data de filtros no Acompanhamento, quando usado Internet Explorer (versão 6.0).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O sistema permite a indicação de valores negativos para a antecedência de envio de e-mails em Restrições.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Pareceres emitidos usando o Internet Explorer (versão 6.0) são exibidos com tags html no seu texto. Ex.: Cada enter é exibido como &lt;p&gt; . No relatório em pdf, o caracter &lt; é substituído por <i>&amp;lt;</i>, porém o caracter > permanece.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No relatório de acompanhamento, caracteres especiais como aspas, "hífen grande" e marcadores colados tanto do Word quando do Writer não estão sendo exibidos corretamente.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O relatório de acompanhamento só está sendo gerado para o primeiro Tipo de Acompanhamento exibido na tela. Ao tentar gerar os relatórios para os demais Tipos de Acompanhamento, os dados apresentados são os mesmos do primeiro Tipo de Acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Não se consegue gerar exportação em txt para os acompanhamentos. As opções Conselho Revisor e Acompanhamento do menu Relatórios apresentam os seguintes problemas: Após escolher a Referência desejada, os tipos de Emissão não estão filtrando corretamente. Emissão por órgão, Estrutura ou situação diz que nenhum registro foi encontrado. Geração de arquivo .txt traz uma tela vazia. Clicar em alguma aba de Tipo de Acompanhamento leva para a tela de página não cadastrada no Sentinela.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No acompanhamento, a funcionalidade Situação dos Pontos Críticos não mostra a descrição dos Pontos Críticos de itens filhos.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Quando informado valor muito grande (ex.: 999999), no campo antecedência de envio de e-mails em Restrições, o sistema passa a exibir 0 no campo. Verificar como o sistema está interpretando isso e, se for o caso, limitar o número de caracteres do campo.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No acompanhamento, a funcionalidade Situação dos Pontos Críticos não mostra a descrição dos Pontos Críticos de itens filhos. Aparentemente, o sistema exibe apenas a descrição do primeiro Ponto Crítico dos itens filhos.<br>
	</td>
  </tr>
   
  <tr> 
  	<td align="center">v8.3</td>
  	<td align="center">HEAD</td>  
  	<td align="center">15/07/2009</td>
  	<td>
  	- MANUTENÇÕES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Acesso às informações de histórico para fins de acompanhamento e auditoria;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Inclusão de link de referência na mensagem recebida via Expresso a respeito dos projetos;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Criação de campos de id que não permitam dois registros com mesmo valor na mesma lista;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Possibilidade de ordenação de itens de uma lista numérica sem necessidade de utilização de zeros à esquerda;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Número de identificação composto por ano corrente e número incremental (mascara utilizada para aquisições - HSAQ).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Possibilidade de criação de escritórios de suporte e controle criarem itens de estrutura nas suas superintendências para agrupamento de projetos que seriam os programas e portfolios (Estruturas Virtuais)	.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Implementar em filtros um sistema de busca por intervalo de requisitos (datas, várias situações, intervalo de valores,etc).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Criação de atributos livres na funcionalidade de pontos críticos, com possibilidade de bloqueio de campo para possibilitar a preservação da informação da data planejada inicialmente para o marco. Deve permitir atributos livres no acompanhamento (Aba Situação) nos pareceres.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Possibilidade de geração de novo projeto baseado em projeto existente, como modelo.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Configuração dos campos que devem aparecer no detalhamento da demanda; Criação de um novo momento para a função de encaminhamento fora do nivel de cadastro (Visões como filtros das situações das demandas).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Reformulação da listagem de monitoramento e abas do detalhamento do item.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Correção de erro na aba de restrições do item: Ao clicar em uma restrição para detalhamento o sistema dá erro indicando que o usuário não tem acesso à página.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Reformulação da tela de exibição de abas, que passa a se chamar Exibição de Abas e Ícones.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Revisão das abas de listas. Atualmente estas abas listam todas as situações (aba "Lista Geral"), situações das restrições (aba "Situação Restrições") e situações dos pontos críticos (aba "Situações Pontos Críticos") dos itens do tipo acompanhamento que está sendo visualizado.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Habilitação para informar o campo Nível Superior na pesquisa da tela de estrutura.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Artigos no Portal:
   									Erro ao inserir um artigo. Em "Administração do Portal > Artigos > Matérias", cadastrar um novo artigo escolhendo-o como destaque, selecionando o "Leiaute" que usa três imagens, e anexando todas as possíveis imagens. O sistema indica que a inclusão foi realizada com sucesso, mas ao consultar o artigo, a "Imagem 2" não aparece enquanto que as demais aparecem.
  									Em "Informações" (link no menu superior),  detalhar o artigo que está como destaque. Quando o artigo é cadastrado com o leiaut que usa mais de uma imagem o sistema exibe uma tela de erro indicando que não pôde achar a página.
  									No link em "Mais Artigos", na tela do Portal listar todos os artigos. Listar todos os artigos que foram cadastrados, 
  									inclusive os artigos cadastrados como destaque;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Relatórios / Lista de usuários -> Substituição da geração do arquivo CSV pela geração de um arquivo txt 
  									através da remoção da classe "com.Ostermiller.util.ExcelCSVPrinter";<br> 
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Atualização dos valores previstos por local no cadastro de metas/indicadores -> Substituição da classe
  									"com.Ostermiller.util.StringTokenizer" pela classe "java.util.StringTokenizer".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Alteração na árvore de Ajax da listagem de itens no cadastro:<br>

   	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Permitir que a exibição da árvore em Ajax no cadastro seja configurável, ou seja, permitir ao administrador de configuração habilitar ou não o uso dessa estrutura. A exibição da árvore de Ajax deverá ser configurável (Configuração Geral do Sistema, opção "Exibir Árvore de Navegação"). Independente da opção de configuração (exibição ou não da árvore em Ajax), a área da listagem de itens do cadastro deverá ser exibida sempre da mesma forma exibindo inclusive as Estruturas em abas.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Links na árvore em Ajax: (1) ao clicar em um item de estrutura, o sistema deverá detalhar o item (ir para aba de Dados Gerais), (2) ao clicar no ícone "+", o sistema deverá expandir o item listando os respectivos filhos.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Ao expandir uma estrutura virtual na árvore o sistema deve exibir os itens agrupados pelas estruturas reais que contem os itens.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Detalhamento de um item de estrutura no cadastro: na árvore de navegação exibida nas telas (abas) de detalhamento de um item de estrutura, exibir o link "Ir para o próximo nível".<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Associação de Itens a Estrutura Virtual: no pop-up que exibe o resultado da consulta de itens para associação de itens a uma estrutura virtual, considerar a configuração do atributo na estrutura (campo: Exibir na Árvore) para a exibição do item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-No cadastro de estruturas, campo relativo a configuração de Nível Superior, o sistema não deve exibir como opção as estruturas virtuais criadas. Uma Estrutura Virtual não pode ser pai de outra estrutura.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tela de listagem de itens em Acompanhamento, o sistema deve exibir apenas as abas dos tipo de acompanhamento para os quais o usuário logado tenha permissão de leitura para pelo menos uma aba do item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tela de seleção de filtros no Acompanhamento, ao ser selecionada a opção personalizada (não geral) o sistema deve exibir para seleção somente as funções de acompanhamento que o usuário logado tem permissão de visualizar.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tela de emissão do relatório no Acompanhamento, na seleção das opções para impressão, o sistema deve exibir para seleção somente as funções de acompanhamento que o usuário logado tem permissão de visualizar.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-No Acompanhamento, emissão de parecer: excluir pastas para anexos criadas em um acompanhamento quando for excluído o último arquivo anexado em um acompanhamento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Utilização do padrão 'Nome' seguido por 'Label' das estruturas entre parêntesis nos seguintes locais:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Nome da estrutura na árvore de navegação do cadastro;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Hint da árvore de navegação do cadastro;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Nome da estrutura na árvore de navegação do detalhamento de um item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Listar, na barra superior contendo as estruturas cadastradas no sistema (detalhamento de uma estrutura no cadastro de itens), as estruturas por Label ou nome, este último apenas quando não houver label informado.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Abaixo da barra de estruturas, foi inserida uma linha com o nome da estrutura que está sendo detalhada.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Manter a configuração de cor de fundo do título, definida no cadastro de estruturas, nas linhas contendo os botões de adição/inserção e exclusão e na nova linha inserida (que contém o nome da estrutura).<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alteração na forma de utilização do pop-up que associa itens à estruturas virtuais para não permitir navegação do usuário na instância do browser que originou a criação do pop-up.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alteração no cadastro de estruturas para não mais possibilitar a criação de uma estrutura virtual. As estruturas virtuais já presentes na base permanecem.<br>
  	
  	- CORREÇÃO DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Caso o administrador do sistema tenha alterado um atributo de uma estrutura para ser obrigatório, os itens que já tenham sido bloqueados e que o atributo alterado não possua valor irá falhar na validação quando o usuário tentar desbloquear o item.(HOMOLOGAÇÃO)<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-O sistema não ordena corretamente quando o usuário clica em deteminada coluna. A ordenação leva em consideração as letras maiusculas e minusculas, fazendo com que a ordenação não seja realizada de forma correta.(HOMOLOGAÇÃO)<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-O sistema insere espaços desnecessários no campo de texto na hora de incluir um anexo em demandas.(HOMOLOGAÇÃO)<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-O campo dica dos atributos livres não são apresentados na tela de histórico (tela de comparação).(HOMOLOGAÇÃO)<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando o usuário tenta anexar um arquivo na tela de demandas o texto em edição é perdido.(HOMOLOGAÇÃO)<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Nas pesquisas salvas do filtro de monitoramento, corrigir o texto que aparece antes do link de excluir. O texto apresentado é "Último Referência". Corrigir para "Última Referência".(HOMOLOGAÇÃO)<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na listagem em monitoramento o filtro por cor não está considerando a liberação do acompanhamento.(HOMOLOGAÇÃO)<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Ao gerar ciclo está sendo exibida a mensagem de solicitação de configuração de estrutura, mesmo ela não pertencendo à hierarquia estabelecida na configuração do tipo de acompanhamento, através da opção "Gerar Acompanhamento a partir da Estrutura".(HOMOLOGAÇÃO)<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Exibição do gráfico de gantt nas telas de cadastro de itens e monitoramento (correção no ambiente de homologação).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Alteração dos seguintes pontos no filtro de Nível de planejamento da listagem de monitoramento:<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Exibição para seleção no pop-up de níveis de planejamento todos os níveis cadastrados no sistema, marcando apenas os itens que estão sendo utilizados pelo usuário;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Realização do filtro de planejamento nas pesquisas salvas;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; * Adição de funcionalidade ao filtro 'sem informação', permitindo que os itens cujo nível de planejamento esteja em branco sejam exibidos.<br>
  	</td>
  </tr>
     
  <tr> 
  	<td align="center">v8.2</td>
  	<td align="center">ECAR-BR-08-01-00-20081028</td>  
  	<td align="center">21/11/2008</td>
  	<td>
  	- MANUTENÇÕES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Permitir a criação de Filtro (salvar pesquisa) em monitoramento;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Criar conceito de super função de acompanhamento para registro de pareceres;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Criação de ícone indicando o acesso a anexação de arquivos no encaminhamento de uma demanda. Criação de contador indicando a quantidade de arquivos anexos;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Aplicação da árvore com AJAX no cadastro de estrutura;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Permitir configurar a exibição dos pareceres na aba de Situação: exibição em abas ou em lista.<br>
  	</td>
  </tr>  
  <tr> 
  	<td align="center">v8.1</td>
  	<td align="center">ECAR-BR-08-01-00-20081103</td>  
  	<td align="center">24/10/2008</td>
  	<td>
  	- MANUTENÇÕES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-No gráfico de evolução, listar os níveis selecionados para(cadastro de usuário)/pelo(opção do usuário na tela de itens de acompanhamento - ícone do filtro nível de planejamento) o usuário logado. Isso deve ocorrer quando estiver configurado na exibição de abas para utilizar o filtro no acompanhamento;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-No cadastramento dos pontos críticos Colocar a data limite como primeira coluna, permitindo classificar por todas as colunas. Como padrão ordenar a primeira coluna (data limite) em ordem crescente;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Notificação de alerta quando a sessão estiver próxima de expirar;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Possibilidade de escolher a data em um calendário para preenchimento dos campos de data;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Associar Usuários a Grupos do Sentinela - permissão de grupos será feito no ecar e não mais no sentinela;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Modificar o campo de anexo no cadastro das demandas primárias para possibilitar o anexo de tipos diferentes de arquivos;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Voltar a notícia destaque de capa para a área de origem e colocar um link para a Caixa de Entrada de email na área à esquerda da área da notícia destaque (logo abaixo de 'Meu Cadastro' colocar o label 'Caixa de Entrada' e abaixo o link 'Você tem x mensagens não lidas.');<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tabela de configuração do tipo de acompanhamento, criar a opção (radio button): Exige Liberar Parecer. O radio 'Sim' (Exige Liberar Parecer) vem marcado como padrão.Proceder semelhante ao item liberar acompanhamento (Até suprimir o botão liberar posição e alterar a regra de gravação de rascunho);<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tela resumo do acompanhamento, buscar o label das funções na configuração daquele nível de estrutura e listar as posições (carinhas) obedecendo a ordem das datas limites para as funções de acompanhamento. A ordem deve ser portanto, a partir da data mais próxima até a data mais distante que está para expirar;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Criar campo de Data da última manutenção e usuário que a realizou no cadastro e classificação de demandas primárias;<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na listagem das demandas primárias acrescentar a quantidade de registros por pagina. (Ex: 1-10 de 100);<br>
  	  	
  	- CORREÇÃO DE ERROS:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na listagem de itens de demandas (cadastro e classificação), o filtro por coluna só filtra a página retornada pela paginação (pagina atual) e não a pesquisa toda;<br>
  	</td>
  </tr>   
     
  <tr>
  	<td align="center">v8.0.7</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">10/07/2009</td>
  	<td>
	- MANUTENÇÕES:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Utilizar o label dos campos de grupos do Sentinela na tela de cadastro de usuários no e-CAR.<br>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Não exibir a aba de um tipo de acompanhamento para a qual o usuário logado não tenha nenhuma permissão de leitura.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Criação de tipo de acompanhamento e geração de ciclo - não obrigar o uso do nível de planejamento para que o sistema funcione.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - ao selecionar a opção personalizado (não geral) somente exibir para seleção as funções de acompanhamento que o usuário tem permissão de visualizar.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - emissão do relatório - na seleção das opções para impressão somente exibir para seleção as funções de acompanhamento que o usuário tem permissão de visualizar.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Emissão de Parecer - excluir pasta criadas em um acompanhamento quando for excluído o último arquivo anexado em um acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Metas e indicadores - acompanhamento - exibir os indicadores de filhos do item sendo consultado, respeitando as permissões de leitura de pareceres para o item.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Tratar cenário não coberto pela rotina de exportação de arquivos retirando as tags HTML geradas indevidamente no arquivo txt de acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alteração na geração de arquivos TXT e PDF para suportar os caracteres copiados do microsoft word: aspas simples, aspas duplas e os marcadores padrão do word. As alterações foram realizadas nas gerações de arquivos TXT do cadastro de demandas, cadastro de itens, relatório TXT de acompanhamento e na geração dos arquivos PDF do cadastro de itens e no relatório PDF do acompanhamento.<br>
		
	- CORREÇÃO DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Não enviar mensagens para datas de Pontos Críticos já excluídos.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na geração de ciclo de acompanhamento o sistema deve permitir selecionar somente alguns filhos e mais o item pai.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Geração de ciclo de acompanhamento - sinalizar periodicidade mensal e trimestral corretamente.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - Aba resumo - o sistema lista sempre a mesma data limite para todas as funções de acompanhamento. Listar as respectivas datas das funções.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acompanhamento - quando o usuário tem permissão para um tipo de acompanhamento que não possui nenhum ciclo de acompanhamento gerado, ocorre um erro ao acessar a página de monitoramento.<br>
	</td>
  </tr>
  
  <tr>
  	<td align="center">v8.0.6</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">05/06/2009</td>
  	<td>
	- MANUTENÇÕES:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Permitir inclusão de novas funções de acompanhamento na alteração de um ciclo de acompanhamento gerado.<br>		
	</td>
  </tr>
  
  <tr>
  	<td align="center">v8.0.5</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">11/05/09</td>
  	<td>
	- MANUTENÇÕES:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Relatórios / Lista de usuários -> Substituição da geração do arquivo CSV, que usa a classe "com.Ostermiller.util.ExcelCSVPrinter", pela geração de um arquivo txt. Este arquivo seria semelhante aqueles gerados na listagem de itens de estrutura, usando inclusive o separador "Caracter Separador em Arq. Textos" da configuração geral do sistema para separar os campos do usuário.<br> 	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Atualização dos valores previstos por local no cadastro de metas/indicadores -> Substituição da classe "com.Ostermiller.util.StringTokenizer" pela classe "java.util.StringTokenizer".<br>
	</td>
  </tr>
  
  <tr>
  	<td align="center">v8.0.4</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">16/01/2009</td>
  	<td>
	- CORREÇÃO DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro ao anexar mais de um arquivo com o mesmo nome (problemas no download).<br> 	
	</td>
  </tr>
  
  <tr>
  	<td align="center">v8.0.3</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">18/12/2008</td>
  	<td>
	- CORREÇÃO DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro ao gerar o Gráfico de Gantt em Monitoramento/Visualização.<br> 	
	</td>
  </tr>
   
   <tr>
  	<td align="center">v8.0.2</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">02/12/2008</td>
  	<td>
  	- MANUTENÇÕES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Inclusão de opção em estrutura para a possibilidade de visualizar ou não o botão "Gerar Arquivos" na listagem de itens em cadastro.<br>
	- CORREÇÃO DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A tela de geração de ciclos ao trazer os itens não está montando a árvore completa do item, impossibilitando de localizá-lo e diferenciá-lo, pois todos estão no mesmo nível.<br> 	
	</td>
  </tr>
   
   <tr>
  	<td align="center">v8.0.1</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">10/11/2008</td>
  	<td>
  	- MANUTENÇÕES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Cadastro de Metas/Indicadores:<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.Na tela de cadastro das metas-indicadores, mudar o label das opções do campo Formato: de Quantidade para Número e de Valor para Moeda<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Metas/Indicadores em monitoramento:<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.No Gráfico de Projeção, utilizar o valor referência para o início da projeção.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Função tela de Gantt:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.Apresentar todos os pontos críticos cadastrados, mesmo os que não foram concluídos.<br>
	 
	- CORREÇÃO DE ERROS:<br>
		
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No filtro de Monitoramento:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tela de filtro do monitoramento, se não marcarmos nenhum filtro e clicarmos em avançar o sistema não emite nenhuma mensagem de aviso para que o usuário marque um dos filtros.<br> 	
	</td>
  </tr>
   
  <tr>
  	<td align="center">v8.0</td>
  	<td align="center">ECAR-BR-08-00-00-20080930</td>  
  	<td align="center">23/10/2008</td>
  	<td>
  	- MANUTENÇÕES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Cadastro de Estrutura: possibilidade de exportação dos dados de cadastro (dados gerais dos itens) em arquivo txt. São gerados, a partir do nível visualizado, arquivos com os itens cadastrados por estrutura e nível de estrutura.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Relatórios > Relatórios Impressos > Conselho Revisor: possibilidade de exportação dos dados dos itens em monitoramento (dados gerais e pareceres emitidos) em arquivo txt, por ciclo de referência selecionado. É gerado, a partir do perído de referência selecionado, arquivos com os itens em monitoramento para o dado ciclo separados por estrutura e por nível. <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Criação de estrutura para gravação do histórico de datas críticas.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Estrutura de serviços: possibilidade de utilização de serviços no cadastro de metas/indicadores. Serviços poderão ser utilizados para apuração dos valores realizados. Serviços disponíveis:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.Apuração de Conclusão de Datas Críticas (Pontos Críticos);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-2.Apuração de Atraso de Conclusão de Datas Críticas (Pontos Críticos);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-3.Apuração de Total de Datas Críticas (Pontos Críticos);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-4.Apuração de Reprogramação de Datas Críticas (Pontos Críticos) - Cálculo de Extensão de Prazo;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-5.Apuração de Reprogramação de Datas Críticas (Pontos Críticos) - Cálculo Redução de Prazo.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Cadastro de Metas/Indicadores:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.Possibilidade de utilização de serviços para apuração de valor realizado na fase de monitoramento;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-2.Possibilidade de utilização de gráfico de grupo para possibilitar a visualização de 'n' indicadores (que pertençam ao mesmo grupo e sejam: ou públicos ou do mesmo item em visualização) em um mesmo gráfico;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-3.Possibilidade de uso de sinalização indicando cores para cada intervalo de valor da meta/indicador.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Metas/Indicadores em monitoramento:<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.Visualização de gráficos de grupo no gráfico realizado por exercício;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-2.Uso dos serviços na apuração do valor realizado. No caso do serviço ter sido configurado como manual: quando o valor realizado ainda não foi gravado, se o serviço retornar um valor apurado é exibido um botão 'Gravar'; caso algum valor já tenha sido gravado e seja diferente do apurado deve-se exibir o botão 'Limpar' com um ícone de alerta indicando que há diferença; quando o valor apurado for gravado deve ser exibido o botão 'Limpar'. No caso do serviço ter sido configurado como automático, o valor apurado será sempre exibido com o ícone de alerta indicando que o valor não está gravado e poder mudar, mas será gravado apenas quando a data limite para informação do realizado expirar.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Função de Acompanhamento: inclusão do indicador 'Manter no próximo nível' na função de acompanhamento na estrutura. A rotina da função de permissão de acesso no cadastro de estrutura foi alterada para considerar esta nova possibilidade de configuração.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Função tela de Gantt: <br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-1.possibilidade de visualização dos marcos (pontos críticos) cadastrados para um item de estrutura em termos do que foi planejado e do que foi realizado. Em cadastro a visualização é sempre com base na data atual. Em monitoramento é possível a visualização baseada: na data atual (padrão), na data de início de monitoramento, data limite para realizado físico.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Unificação da tela de metas/indicadores em monitoramento: unificação da visualização da tela quando se está em registro ou em visualização.<br>
 
	 
	- CORREÇÃO DE ERROS:<br>
		
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na aba de Resultados em Monitoramento:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando o formato do indicador é definido para quantidade, o usuário não consegue gravar na tela de resultados;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Caso o usuário digite um valor superior a 1.000, o sistema insere o ponto e quando o valor é carregado ele não permite mais a gravação. Na validação do valor exibida, o sistema não aceita mais o ponto que ele mesmo inseriu;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando a opção "quantidade informada realizada por local" for selecionada, abrirá um popup para informar os valors dos locais. Caso não exista nenhum filho, ocorre um erro de gravação.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando o valor realizado do indicador for igual a zero ou não for informado, o gráfico de projeção não é carregado e nenhuma mensagem de erro é exibida;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando muda a situação do indicador o sistema, exibe um erro de permissão de acesso do sentinela dentro da própria página (sobre cabeçalho da página);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Caso o usuário tente alterar o valor de apenas um indicador o sistema validará a situação de todos os outros, forçando o usuário a informar a situação de todos, inclusive dos que estejam sem valor.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na aba de Metas e Indicadores em Cadastro:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-O radio button que aparece após a selecção de "Acumulável - Não" deveria ser exibido abaixo da opção "Acumulável" e não dá opção "É público";<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Deve existir um nome único para cada indicador, pois atualmente mais de um indicador com o mesmo nome acarreta numa perda de informações. Em uma lista de indicadores com mesmo nome o gráfico exibirá apenas uma barra com o último valor encontrado; <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando o usuário insere qualquer valor ao campo "Índice Referência", o sistema adiciona uma vírgula no final do campo e grava os dados. Caso o usuário tente alterar os dados desse indicador, mantendo o valor informado com a vírgula, o sistema irá validar de forma errada o campo e entrará em loop infinito;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Validar a seleção do campo "Quantidade. Prevista Informada por Local?" como obrigatória;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-O popup que se abre ao clicar em "Informar Quantidade/Valor Previsto por Local " estava mal formatada quando o local não possuía filhos.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Permissão de acesso: correção da rotina de atualização das permissões de acesso pois permissões dadas a funções de acompanhamento previamente, mesmo quando alterados os usuários de cada função de acompanhamento, estavam permanecendo.<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na aba de Metas e Indicadores em Cadastro:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-No IE o campo valor final com as opções: Maior, Último e Não se Aplica está sendo apresentado quando marcamos o campo vinculado Acumulável = Sim. O correto seria apresentar essa marcação quando preenchido o campo Acumulável = Não;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na aba de Situação em Monitoramento:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Erro de imagem quebrada (pixel.gif) localizada acima do texto "Mostrar Todos(as) Situações Emitidos(as)". No IE, ainda está aparecendo a imagem quebrada;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Botão Cancelar.png. Os botões cancelar na tela de registro de parecer estão sem ação.<br>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Na tela de registro de parecer, no IE, ao clicar em cima de um posicionamento, a ajuda não funciona para todos os casos.<br>		
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na aba de Metas e Indicadores em Monitoramento:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Após gravar uma meta definida com serviço e preenchimento manual, o sistema apresentou os botões limpar, porém eles não estão realizando nenhuma ação (estão vindo desabilitados);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Quando dois indicadores possuem o mesmo nome, o sistema exibe uma linha em branco abaixo do primeiro indicador cujo nome é repetido;<br>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Ao acessar a tela de metas/indicadores no acompanhamento, o aviso de não conformida entre as datas de cadastro e acompanhamento aparece em casos que não deveria aparecer;<br>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na parte do Cadastro:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Exportação de Arquivos - Erro na geração de arquivo de saída. 1- Não está varrendo todas as estruturas; 2- Caracteres Especiais para a Nomenclatura do item; <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No filtro de Monitoramento:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Erro de validação do Campo Exibir na tela inicial do filtro de acompanhamento; <br>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Relatório Impresso  de Monitoramento:<br>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Geração de Arquivos: Não está gerando para os itens que não tem o acompanhamento do item liberado; <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Geração de Arquivos: O sistema está gerando arquivos sem considerar a regra de acesso aos itens; <br>
	
	</td>
  </tr>
  <tr>
  	<td align="center">v7.0</td>
  	<td align="center">HEAD</td>  
  	<td align="center">21/07/2008</td>
  	<td>
  	- MANUTENÇÕES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Árvore de itens de estrutura na tela de monitoramento: somente exibir itens na árvore que possuam itens no próximo nível a exibir. Isto implica em descer na estrutura até o final eventualmente, quando o item com visualização permitida estiver no último nível da árvore. Nas telas de cadastro, o sistema já procede desta maneira, porém as permissões verificadas neste caso são diferentes das permissões que devem ser testadas no visualizar ou registrar acompanhamento. Como recurso inicial utilizaremos a seguinte regra:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-O sistema exibe TODOS os itens até o nível máximo de exibição da estrutura em configuração do Tipo de acompanhamento. Todos os registros devem aparecer com sinal de mais[+];<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Ao clicar no mais para expandir a estrutura, o sistema verifica se existem registros a exibir no nível inferior (conforme descrito acima);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Caso não existam, o sistema troca o quadrado para um quadrado vazio;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Caso existam, o sistema deve apresentar todos os itens do nível seguinte com o sinal de mais ao lado (desde que o item dê acesso a algum item no próximo nível seguinte a este);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Itens que não dêem acesso a algum item a visualizar não devem ser exibidos (não devem aparecer na lista);<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Itens com ARI, mas sem permissão para o usuário visualizar este item, segundo os critérios já existentes de visualizar parecer);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Se não tiver filhos, apresentar quadrado em branco;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Se tiver filhos e estiver fechado, apresentar quadrado com mais;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Se tiver filhos e estiver expandido, apresentar quadrado com menos;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Árvore: obedecer o que foi configurado na tela de "Tipo de Acompanhamento" para o maior nível a exibir.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em alteração do tipo de acompanhamento está aparecendo o erro (IE) "Stack overflow...." (estouro de pilha).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Gerar Ciclo de Acompanhamento só mostrar o que está vinculado com Sala de Situação (estrutura de Nível de Planejamento do sistema). <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em Gerar Ciclo de Acompanhamento identar a exibição dos registros. Mostrar o label da estrutura na árvore, na árvore, quando este existir na especificação da estrutura. <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acrescentar em configuração geral um campo para indicar o ciclo padrão para ser adotado pelo acompanhamento.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a tela de filtro e colocar o ciclo padrão definido na exibição inicial.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Tela de Exibição de Abas, unificar os checks de posição e elaboração.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em situação, trocar a mensagem (do onMouseOver) que está acumulada com o texto anterior.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Verificar no cadastro de item de estrutura o cadastro de imagem como atributo livre (não mostra a imagem depois da alteração) no IE.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Abas da relação, situação restrições e situação indicadores devem ficar acima do avançar/retroceder (pertence a todos os empreendimentos, ou seja, é comum a um conjunto de dados).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela "Grupo Acesso" depois de salvar, voltar a aparecer o botão alterar (em modo bloqueado).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtros: implementar atributos livres em filtros dinâmicos.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtros: alterar o label "Acompanhamento" para "Dados Acompanhamento" em filtros.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Filtros: levar o algoritmo de data mais próxima (referência) para a tela de filtro.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtros: campos de data e valor, ficar como intervalo em filtros. Atributos de dados gerais devem ser tratados de forma fixa e atributos livres devem ser tratados somente itens de texto com "validação" onde a regra de validação é que define o tipo de tratamento (data ou valor).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtros: mostrar uma mensagem na aba Dados Gerais, caso não tenha sido marcado nenhum filtro.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Permitir incluir restrições e apontamento de restrições na aba de restrições, verificando qual função de acompanhamento pertence.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em avaliações, deixar o lápis para facilitar a alteração.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Verificar em "grupo de acesso" as consistências: <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Situação com seleção de algum visualizar pareceres;<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Gerar ciclos com seleção de datas limites.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Tela de Resumo:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Alterar a tela de resumos para uma aba "resumo" nas abas de monitoramento;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Alterar a tela de resumo, excluir a exibição de pareceres se não tiver acesso a aba de situação.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Incluir um campo para a sigla no cadastro de Local e Grupo Local. Nas tabelas TB_LOCAL_ITEM_LIT e TB_LOCAL_GRUPO_LGP, incluir um campo para SIGLA - varchar(10).<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Identidade Visual:<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Adequar a identidade visual do ambiente ao mais próximo possível do padrão definido para SISPAC;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Em configurações gerais incluir campo para o administrador selecionar um arquivo .CSS entre alguns cadastrados;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Alterar as páginas .JSP para enxergarem o .CSS indicado na configuração. <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtros:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Criar uma nova aba na tela de filtros com o label "Filtros". Esta aba deverá apresentar os filtros previamente gravados. Como neste momento não temos a funcionalidade de gravar os filtros, incluir as seguintes opções fixas nesta aba, para seleção de uma delas:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Geral: exibe todos os registros sem aplicação de filtro;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Minha Visão: exibe todos os acompanhados que o usuário logado tenha permissão para editar alguma coisa;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Minhas pendências: exibe todos os acompanhamentos que o usuário logado tenha algum parecer ainda não liberado, e/ou todos os acompanhamentos em que o usuário é administrador e que o acompanhamento exija liberar o acompanhamento (definido em tipo de acompanhamento) e o acompanhamento ainda não tenha sido liberado; <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Todas estas opções devem seguir o seguinte critério: Exibe os acompanhamentos do primeiro tipo de acompanhamento, segundo a seqüência de exibição definida em Tipo de Acompanhamento, que o usuário logado tem permissão de visualizar, com ciclos = 1 e ciclo de referência mais próximo a data atual (conforme critério já existente anteriormente);<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Personalizado: desvia para a tela seguinte ;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtro: melhorar a interface, vinculando "Cor" e "Função de Acompanhamento" de uma forma que identifique melhor que um esta relacionado ao outro.<br>
	
	- CORREÇÃO DE ERROS:<br>
		
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de monitoramento, não está aparecendo todas as informações dos campos definidos em atributo na estrutura para a descrição do item monitorado. Verificar campos fixos e atributos livres.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Logo ao criar uma estrutura e sem conceder nenhuma permissão para o usuário/grupo, aparece o botão de Excluir na tela de listagem de projetos.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Tela de restrições: Ao preencher o campo responsável pela solução sem preencher o campo data da solução, não é persistida a informação. Deveria haver um tratamento informando que a persistência desse campo só ocorrerá quando for preenchido o campo data da solução.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Rever as regras consideradas pelo filtro no momento da geração de um ciclo de acompanhamento. Verificar as regras dos atributos "Gerar acompanhamento a partir da estrutura", "inclui monitorado", "inclui não monitorado". <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- O menu lateral aparece fora de formatação quando possui poucos itens.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Rever as regras de "Pode ser Bloqueado?" e "Está liberado para" em atributo na estrutura e função de acomp na estrutura.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- A aba de dados gerais, não fica ativa após a seleção do filtro personalizado, esta aba deveria ser de visualização opcional, e não obrigatória após clicar em avançar.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Acessar página de Monitoramento (tanto através do menu superior "Monitoramento", quanto pelo menu lateral "Acompanhamento / Monitoramento"). Problema: No Firefox ao clickar no botão avançar nada acontece. No internet explorer ao clicar no botão avançar ocorre erro de JavaScript em anexo. Perfil: Administrador Local<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Após realizar uma pesquisa na tela "Demanda > Classifica Demanda", selecionar e alterar o status de um registro, o e-CAR retorna para a tela de filtro mas não atualiza o status desse registro. Problema: Tela de filtro de Demandas não é atualizada no retorno da alteração Navegador: Firefox, não testei no IE Perfil: Administrador Local<br>
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
  	- MANUTENÇÕES:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar a regra de visualização da listagem das demandas primárias, retirando a visibilidade de demandas 
								    que forem cadastradas pelo mesmo grupo do usuário logado, ou seja, o usuário
                   				    logado não poderá visualizar as demandas que foram cadastradas por outros usuários do mesmo grupo que o dele.
                   				    Tanto para a listagem do cadastro quanto para a classificação. <br>
	
	- CORREÇÃO DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao classificar uma demanda no Internet Explorer, a demanda continua aparecendo na listagem de demandas, 
  									até que se atualize a página ou clique novamente no link de acesso a classificação. No Firefox    
                   					funciona corretamente.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Erro ao gravar anexos e erro de liberação do acompanhamento no parecer.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No cadastro de demanda primária, no campo órgão solucionador, a tela de pesquisa não está funcionando.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao alterar o campo órgão solucionador de uma demanda primária já cadastrada, está dando um erro na exclusão de um 
    							    item cadastrado no Internet Explorer.<br>
    							    
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Não está obedecendo a configuração de visualização de pareceres conforme o papel definido na configuração do grupo, 
    							    menu Administração Sistema > Usuários > Grupo de Acesso, deveria restringir o acesso a visualização do 
    							    parecer conforme o papel selecionado. Ou seja, o usuário está conseguindo acessar todos os pareceres definidos para o item.<br>
    							    
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Não está considerando a configuração na aba de permissões de acesso em um item do cadastro, para permitir ao usuário 
								    indicado o acesso ao acompanhamento desse item. Ou seja, o usuário que possui essa permissão definida no cadastro não
								    está conseguindo enxergar esse item no acompanhamento para visualizar os pareceres.<br> 
    							    
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Lista Geral - na tela de parecer do acompanhamento ao clicar na aba lista geral, o sistema perde a referência e não mostra a lista de itens. <br>
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No monitoramento, ao mudar a seleção dos campos Ciclo de Exibição e Ciclo de Referência, o sistema perde a referência da listagem de itens. <br>
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Gráfico de resultados (Metas e Indicadores) - Não está apresentando o gráfico de projeção, quando prenchido os vários ciclos.<br>
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em parecer, apresenta o ícone mesmo sem haver preenchido o parecer da função.
	</td>
  	</tr>
  
  
   <tr>
  	<td align="center">v6.6</td>  
  	<td align="center">branch_v6_00_b5</td>
  	<td align="center">05/06/2008</td>
  	<!-- <td align="center">em teste</td> -->
  	<td>
  	- MANUTENÇÕES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na tela de classificação de demandas, habilitar para a edição os campos indicados:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Município/Gov. do Estado;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Entidades;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Data de Protocolo;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Órgão Solucionador;<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-Nome do Solicitante.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Atualização do arquivo informando as alterações liberadas em cada versão.  <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar no banco de dados o endereço de e-mail dos textos do site alterando para lista-ecarserpro@grupos.serpro.gov.br.<br>
	- CORREÇÃO DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ajuste da tabulação dos itens na tela de filtro no acompanhamento.<br> 
	</td>
  	</tr>
   	
   	<tr>
  	<td align="center">v6.5</td>  
  	<td align="center">branch_v6_00_b5</td>
  	<td align="center">29/05/2008</td>
  	<!-- <td align="center">em teste</td> -->
  	<td>
  	- MANUTENÇÕES:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ajuste do campo Entidades Solucionadoras do Cadastro de Usuários não deve ser de preenchimento obrigatório.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtrar a tela de Classificação por:<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Somente os projetos com situação = "a classificar" E <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Pelo Campo Entidades Solucionadoras do Cadastro OU pelo Campo UF - Atributo Livre Restritivo em Atributo na Demanda.<br>
									
									
	- CORREÇÃO DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ao preencher o realizado físico de uma meta/indicador, e atribuir um valor ao campo situação, o sistema 
									mostra a tela de bloqueio do sentinela mesclada ao topo da página.<br>
	
	</td>
  </tr>
  
  <tr>
  	<td align="center">v6.4</td> 
  	<td align="center">branch_v6_00_b5</td>
  	<td align="center">21/05/2008</td>
  	<!--  <td align="center">em teste</td> -->
  	<td>
  	- MANUTENÇÕES:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Apresentar a UF vinculada na tela de pesquisa dos municípios<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ajustar as telas Cadastro e Classificação de Demandas para que o campo Orgão Solucionador 
									possa receber mais de um valor. Cada órgão teria como listar. <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar Nomenclatura no módulo de monitoramento edição de parecer:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1) Trocar nas abas das funções o nome Relação por Lista Geral;<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2) No parecer alterar a Situação por Estágio e Cor por Situação. 
									(Alterar a nomenclatura de Botões, de Avaliação para Situação e de Posição para Situação) <br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Aumentar a paginação na listagem de itens do cadastro e classificação de demandas, de 10 para 20.<br>								
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na geração de relatórios do tipo conselho revisor e acompanhamento, permitir selecionar a imagem que aparece no cabeçalho.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtrar também por UF na tela de listagem das demandas.<br> 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Alterar o cadastro de grupo de atributo em demanda adicionando a opção que indica que o grupo de atributo 
									vai para a Tela de cadastro de usuário.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Alterar o cadastro de atributo na demanda adicionando a opção "É restritivo" que indica se o atributo 
									será usado para filtrar as demandas a serem exibidas na listagem.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Criar um novo atributo na tabela grupo_atributo (livre) que conteria o tipo de exibição no cadastro de usuário 
									(que poderá ser diferente do tipo de exibição no outro local do grupo estrutura, demanda, entidade, etc).
									Ao criar este campo na base de dados colocar '2' para o SGA = 27 (UF de demandas). 
									Para os demais registros copiar valor do cod_STeg para o novo campo.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Converter a Tabela de órgãos como Entidade.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Na listagem do acompanhamento (monitoramento), na tela onde aparece a posição geral dos itens (carinhas), mudar a cor do título da coluna do ciclo de referência com a cor clara, os demais ciclos devem ficar com a cor escura.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em classificação de demandas os campos Descrição Demandas e Observação devem estar editáveis.<br>
									
	- CORREÇÃO DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No cadastramento (Inclusão) de uma demanda, o campo fixo "Descrição da Demanda" só está aceitando 200 caracteres, 
									não está lendo da configuração que especifica 3000 caracteres.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-	Campo Fixo "Situação" na classificação de uma demanda só aceita 20 caracteres.<br>
  	</td>
  </tr>
  
  <tr>
  	<td align="center">v6.3</td>  
  	<td align="center">branch_v6_00_b5</td>
  	<td align="center">24/04/2008</td>
  	<!--  <td align="center">em teste</td> -->
  	<td>
  	- MANUTENÇÕES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Relatório de demandas primárias, criar saída de dados via texto com separador para o módulo de demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Aumentar o tamanho da janela dos campos para preenchimento do parecer.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Criar opção em configuração geral que permita ocultar o campo de observações no parecer.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- No encaminhamento (apontamento) ampliar caracteres para 3 mil. Último encaminhamento pode ser excluído, os anteriores não.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Criar um novo momento para a  função de encaminhamento fora do nível de  cadastro.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Alterar  os labels do módulo Demandas  "Apontamento" para  "Encaminhamento".<br>
  	                                
  	- CORREÇÃO DE ERROS:<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Abertura de arquivos anexos utilizando Internet Explorer.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em geração de acompanhamento, no primeiro nível da estrutura o lápis não está sendo exibido na primeira linha.<br>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em geração de relatório de acompanhamento, a relação não traz nenhum item.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Em geração de relatório no cadastro aparece uma tela com null.<br>
  	</td>
  </tr>
  
  <tr>
  	<td align="center">v6.2</td>  
  	<td align="center">branch_v6_00_b5</td>
  	<td align="center">15/04/2008</td>
  	<!--  <td align="center">em teste</td> -->
  	<td>
  	- MANUTENÇÕES:<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Implementar funcionalidades tela atributo_demanda.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Refazer tela de listagem de demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Refazer tela de cadastro de demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Ajustar tela de classifica demanda.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Refazer tela de consulta de demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Criar filtro dinâmico em demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Replicar alterações em empreendimento.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Filtrar a apresentação das demandas.<br>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;- Incluir um campo para anexar documentos em Apontamento de Demandas.<br>
  	</td>
  </tr>

  </table>
  
</div>
 
</body>
<%@ include file="/include/estadoMenu.jsp"%>
<%@ include file="include/ocultarAguarde.jsp"%>

</html>
