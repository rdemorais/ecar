<%@ page import="ecar.dao.OrgaoDao" %>
<%@ page import="ecar.dao.SisAtributoDao" %>
<%@ page import="ecar.pojo.UsuarioAtributoUsua"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Collection" %> 
<%@ page import="ecar.pojo.OrgaoOrg" %>
<%@ page import="java.util.Collections" %>
<%@ page import="ecar.pojo.SisAtributoSatb"%>
<jsp:directive.page import="ecar.pojo.UsuarioUsu"/>
<%@ page import="ecar.bean.AtributoEstruturaListagemItens"%>
<%@ page import="ecar.pojo.ItemEstruturaIett"%>
<%@ page import="ecar.pojo.AcompReferenciaAref"%>
<%@ page import="ecar.dao.AcompReferenciaDao"%>
<%@ page import="ecar.dao.ItemEstruturaDao"%>
<%@ page import="ecar.dao.ItemEstUsutpfuacDao" %>
<%@ page import="ecar.pojo.ItemEstUsutpfuacIettutfa" %>
<%@ page import="ecar.dao.TipoFuncAcompDao"%>
<%@ page import="ecar.util.Dominios" %>
<%@ page import="ecar.dao.AbaDao"%>
<%@ page import="ecar.dao.TipoAcompanhamentoDao" %>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa" %>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.pojo.StatusRelatorioSrl"%>
<%@ page import="ecar.pojo.EstruturaEtt"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="ecar.dao.SisGrupoAtributoDao"%>
<%@ page import="ecar.dao.ConfiguracaoDao"%>

<%
if(acompReferenciaItem != null){

	String formaVisualizarControle = Pagina.getParamStr(request, "hidFormaVisualizacaoEscolhida");
	
%>
<script language="javascript">
//Função utilizada nos botões "Avançar" e "Retroceder".
function nextPrevious(codAri,codIett){
    document.form.codAri.value = codAri;
    document.form.primeiroIettClicado.value = codIett;
    document.form.primeiroAriClicado.value = codAri; 
    // o link de controle foi alterado porque quando era "S", ele estava indo para pagina detalhar item
    // e trocava o ari passado pelo botao avança retrocede pela variavel referencia   
    document.form.action = "<%=paginaBtAvancRetroceder%>&hidFormaVisualizacaoEscolhida=<%=formaVisualizarControle%>";
    document.form.submit();
}
</script>

<%
String retroceder = "";
String avancar = "";
String strCodArisControleVisualizacao = "";
List itensControle = new ArrayList();
AcompReferenciaItemAri ariSessao = null;

String codLido = "";
String ehMudancaReferencia = Pagina.getParamStr(request, "ehMudancaReferencia");
String codAriControle = Pagina.getParamStr(request, "codArisControleVisualizacao");
if(!"".equals(codAriControle)){
    strCodArisControleVisualizacao = Pagina.getParamStr(request, "codArisControleVisualizacao");
    
    String[] codArisControleVisualizacao = strCodArisControleVisualizacao.split("\\|");
    
    itensControle = new ArrayList();
    for(int i = 0; i < codArisControleVisualizacao.length; i++){
        codLido = codArisControleVisualizacao[i];
        if(!"".equals(codLido)){

            ariSessao = (AcompReferenciaItemAri) new AcompReferenciaItemDao(null).buscar(AcompReferenciaItemAri.class, Long.valueOf(codLido));
            
            if (Dominios.SIM.equals(acompReferenciaItem.getAcompReferenciaAref().getTipoAcompanhamentoTa().getIndSepararOrgaoTa())){
				if (ariSessao.getAcompReferenciaAref().getDiaAref().equals(acompReferenciaItem.getAcompReferenciaAref().getDiaAref()) &&
						ariSessao.getAcompReferenciaAref().getMesAref().equals(acompReferenciaItem.getAcompReferenciaAref().getMesAref()) && 
						ariSessao.getAcompReferenciaAref().getAnoAref().equals(acompReferenciaItem.getAcompReferenciaAref().getAnoAref())){
					itensControle.add(ariSessao);
				}
			} else {
				if(ariSessao.getAcompReferenciaAref().equals(acompReferenciaItem.getAcompReferenciaAref())){
					itensControle.add(ariSessao);
				}
			}
        }
    }

}    

if(itensControle.size()== 0 || "".equals(codAriControle) || ehMudancaReferencia.equals("S")) {

	// se a lista nao vier carregada, forma de novo
	// copiado de detalhar item
	String niveisPlanej = Pagina.getParamStr(request, "niveisPlanejamento");
	String mesRef = acompReferenciaItem.getAcompReferenciaAref().getCodAref().toString();
	ItemEstruturaIett itemEst = null;
	List itensSessaoVisualizar = new ArrayList();
	String strCdArisControleAux = "";
	String codTipoAcomp = Pagina.getParamStr(request, "codTipoAcompanhamento");
	String exigeLiberarAcomp = "";
	
	TipoFuncAcompDao tpFuncAcompDao = new TipoFuncAcompDao(request);
	AcompReferenciaItemDao ariRefDao = new AcompReferenciaItemDao(request);
	AcompReferenciaDao acompRefDao = new AcompReferenciaDao(request);
	ItemEstruturaDao itemEstDao = new ItemEstruturaDao(request);
	ItemEstUsutpfuacDao itEstUsuDao = new ItemEstUsutpfuacDao(request);
	TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);
	EstruturaDao estrutDao = new EstruturaDao(request);
	
	
	List tpfaOrdenadosEstrutura = tpFuncAcompDao.getFuncaoAcompOrderByEstruturas();
	
	OrgaoDao orgaoDao = new OrgaoDao(request);
	SisAtributoDao sisAtribDao = new SisAtributoDao(request);
			
	Boolean isSemInformacaoNivelPlanejamento = new Boolean(true);
	
	
	UsuarioUsu usuarioLogado = ((ecar.login.SegurancaECAR)session.getAttribute("seguranca")).getUsuario();
	OrgaoOrg orgaoResp = new OrgaoOrg();
		
	List listaNiveis = new ArrayList();
	//Recuperando por todos níveis de planejamento
	String strAux2 = "";
	ConfiguracaoDao configDao = new ConfiguracaoDao(request);
	List listNiveisPlanejamento = new SisGrupoAtributoDao().getAtributosOrdenados(configDao.getConfiguracao().getSisGrupoAtributoSgaByCodSgaGrAtrNvPlan());
	Iterator it = listNiveisPlanejamento.iterator();
	while(it.hasNext()){
		SisAtributoSatb nivel = (SisAtributoSatb) it.next();
		
		strAux2 += nivel.getCodSatb() + ":";
	}
	if("".equals(niveisPlanej)){
		niveisPlanej = strAux2.toString();
	}
	String[] niveisBotao = niveisPlanej.split(":");
	
	for(int n = 0; n < niveisBotao.length; n++){
		String codNivel = niveisBotao[n];
				
		if(!codNivel.equals("")){
			listaNiveis.add(sisAtribDao.buscar(SisAtributoSatb.class, Long.valueOf(codNivel)));
		}
	}
			
	Long codArefReferencia = Long.valueOf(mesRef);				
	Collection periodosConsiderados = new ArrayList();
	int qtdePeriodosAnteriores = 1;
			
				
	if(codArefReferencia.intValue() > 0) {
		periodosConsiderados = acompRefDao.getPeriodosAnterioresOrdenado(codArefReferencia, qtdePeriodosAnteriores,  Long.valueOf(codTipoAcomp), false);
	}
	
	String codIettR = Pagina.getParamStr(request, "codIettRelacao");
	String strCdAri = Pagina.getParamStr(request, "codAri");
	itemEst = acompReferenciaItem.getItemEstruturaIett();
	
	Long codIettPai = itemEstDao.getAscendenteMaximo(itemEst).getCodIett();
	
	
	/*		
	Object itens[] = ariRefDao.getItensAcompanhamentoInPeriodosByOrgaoRespPaginado(
									periodosConsiderados, listaNiveis, orgaoResp, 
									usuarioLogado, seguranca.getGruposAcesso(), 
									Long.valueOf(codTipoAcomp), codIettPai,
									isSemInformacaoNivelPlanejamento, null, null, -2, 1, null);
	
	List itensAcompanhamentos = new ArrayList((Collection)itens[0]);;
	List itensGeral = new ArrayList((Collection)itens[1]);
	*/
	List itensAcompanhamentos = null;
	List itensGeral = null;
	 
			
	if(!(Pagina.getParamStr(request, "orgaoResponsavel")).equals("")){
		orgaoResp = (OrgaoOrg) orgaoDao.buscar(OrgaoOrg.class, Long.valueOf( Pagina.getParamStr(request, "orgaoResponsavel")));
	}
	
	
	
	if(itensAcompanhamentos != null && itensAcompanhamentos.size() > 0) {
		int menorNivel = -1;
		Iterator itensAcompanhamentosIt = itensAcompanhamentos.iterator();
		while(itensAcompanhamentosIt.hasNext()) {
			AtributoEstruturaListagemItens aeIett = (AtributoEstruturaListagemItens) itensAcompanhamentosIt.next();
			ItemEstruturaIett item = aeIett.getItem();
																				
			Iterator itPeriodosAcao = periodosConsiderados.iterator();
			Map  mapAcao = ariRefDao.criarMapPeriodoAri(periodosConsiderados, item);
			TipoAcompanhamentoTa tipoAcomp  = null;
			if(codTipoAcomp != null) {
				tipoAcomp = (TipoAcompanhamentoTa) taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(codTipoAcomp));
				exigeLiberarAcomp = tipoAcomp.getIndLiberarAcompTa();
				EstruturaEtt menorEttCfg = tipoAcomp.getEstruturaEtt();
				if(menorEttCfg != null)
					menorNivel = estrutDao.getNivel(menorEttCfg);
			}
			

			if(itPeriodosAcao.hasNext()) {
				//Pega só o ciclo selecionado (Aref), que é o primeiro
				AcompReferenciaAref acompReferencia = (AcompReferenciaAref) itPeriodosAcao.next();
				if(!mapAcao.isEmpty() && mapAcao.containsKey(acompReferencia)) {
					AcompReferenciaItemAri ariAcao = (AcompReferenciaItemAri) mapAcao.get(acompReferencia);
					
					StatusRelatorioSrl statusRelatorio = (StatusRelatorioSrl) itemEstDao.
		                         buscar(StatusRelatorioSrl.class, Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));
				
		        	if((Dominios.NAO).equals(exigeLiberarAcomp) || ariAcao.getStatusRelatorioSrl().equals(statusRelatorio)) {
						itensSessaoVisualizar.add(ariAcao.getCodAri().toString());	
						
						List filhosExibir = new ArrayList();
						
						//depois mudar
						if(menorNivel != -1){
							List filhos = itemEstDao.getDescendentesViaQry(ariAcao.getItemEstruturaIett());
							if(filhos != null && filhos.size() > 0){
								Iterator itFilhos = filhos.iterator();
								while(itFilhos.hasNext()){
									ItemEstruturaIett filho = (ItemEstruturaIett) itFilhos.next();
									if(itensGeral.contains(filho)){
										if(filho.getNivelIett().intValue() > menorNivel) {
											Iterator itAris = filho.getAcompReferenciaItemAris().iterator();
											while(itAris.hasNext()) {
												AcompReferenciaItemAri ariFilho = (AcompReferenciaItemAri)itAris.next();
												itensSessaoVisualizar.add(String.valueOf(ariFilho.getCodAri()));
											}	
										}
									}
								}
							}
						}	
					
					}		
				}// fim do if(!mapAcao.isEmpty() && mapAcao.containsKey(acompReferencia)) {
			} // fim do if(itPeriodosAcao.hasNext()) 
	  }	// fim do while(itensAcompanhamentosIt.hasNext()) {
	} // fim do if(itensAcompanhamentos != null && itensAcompanhamentos.size() > 0) {
	
	
	//Setando hidden com os itens da listagem para os botões Avançar/Retroceder
	Iterator itCodArisControle = itensSessaoVisualizar.iterator();
	while(itCodArisControle.hasNext()){
		String cdAri = (String) itCodArisControle.next();
		strCdArisControleAux += cdAri + "|";
		
		if(!"".equals(cdAri)){
			ariSessao = (AcompReferenciaItemAri) new AcompReferenciaItemDao(null).buscar(AcompReferenciaItemAri.class, Long.valueOf(cdAri));
			if(ariSessao.getAcompReferenciaAref().equals(acompReferenciaItem.getAcompReferenciaAref())){
				itensControle.add(ariSessao);
			}
		}
			
	}
	
	strCodArisControleVisualizacao = strCdArisControleAux;
	

	 
}// fim do if


int tam = itensControle.size();
for(int i = 0; i < tam; i++){
	AcompReferenciaItemAri ariLido = (AcompReferenciaItemAri) itensControle.get(i);
     if(ariLido.equals(acompReferenciaItem)){
    	 if(i > 0){
         	AcompReferenciaItemAri ariAnt = (AcompReferenciaItemAri) itensControle.get(i-1);
            retroceder = ariAnt.getCodAri().toString() + "," + ariAnt.getItemEstruturaIett().getCodIett();
         }
         
         if(i < (tam-1)){
         	AcompReferenciaItemAri ariPos = (AcompReferenciaItemAri) itensControle.get(i+1);
            avancar = ariPos.getCodAri().toString() + "," + ariPos.getItemEstruturaIett().getCodIett();
         }
         break;
    }
}


    
%>

<table align="center">
    <tr>
<%
String desabilitarBotao = "disabled";

if(!"".equals(retroceder)){
    desabilitarBotao = "";
}
%>
        <td>
            <input type="button" name="btRetroceder" value="&lt;&lt; Retroceder" onclick="javascript:nextPrevious(<%=retroceder%>)" <%=desabilitarBotao%>>
        </td>
<%
desabilitarBotao = "disabled";
if(!"".equals(avancar)){
    desabilitarBotao = "";
}
%>
        <td>
            <input type="button" name="btAvancar" value="Avançar &gt;&gt;" onclick="javascript:nextPrevious(<%=avancar%>)" <%=desabilitarBotao%>>                       
        </td>
    </tr>
</table>
<%
}
%>

