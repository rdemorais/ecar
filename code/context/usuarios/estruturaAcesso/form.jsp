<%@ page import="ecar.pojo.EstruturaAcessoEtta"%>
<%@ page import="ecar.dao.EstruturaAcessoDao"%>
<%@ page import="ecar.dao.SisGrupoAtributoDao"%>
<%@ page import="ecar.pojo.EstruturaAtributoEttat"%>
<%@ page import="ecar.pojo.SisAtributoSatb"%>
<%@ page import="ecar.dao.SisAtributoDao"%>
<%@ page import="ecar.dao.SisAtributoDao"%>
<%@ page import="ecar.dao.PesquisaDao"%>
<%@ page import="ecar.pojo.TipoAcompanhamentoTa"%>
<%@ page import="ecar.dao.TipoAcompanhamentoDao"%>
<%@ page import="ecar.pojo.TipoAcompGrpAcesso"%>
<%@ page import="ecar.dao.TipoAcompGrpAcessoDao"%>
<%@ page import="ecar.pojo.TipoAcompGrpAcessoId"%>
<%@ page import="ecar.dao.TipoFuncAcompDao"%>
<%@ page import="ecar.pojo.TipoFuncAcompTpfa"%>
<%@ page import="ecar.pojo.TipoAcompFuncAcompTafc"%>
<%@ page import="ecar.dao.TipoAcompFuncAcompDao"%>
<%@ page import="ecar.dao.EstruturaDao"%>
<%@ page import="ecar.pojo.EstruturaEtt"%>
<%@ page import="ecar.util.Dominios"%>
<%@ page import="ecar.pojo.Aba"%>
<%@ page import="ecar.dao.AbaDao"%>

<%@page import="ecar.pojo.VisaoDemandasGrpAcesso"%>
<%@page import="ecar.dao.VisaoGrpAcessoDao"%><jsp:directive.page import="ecar.pojo.TipoAcompTipofuncacompSisatributoTaTpfaSatb"/>
<jsp:directive.page import="ecar.dao.TipoAcompTipofuncacompSisatributoTaTpfaSatbDao"/>
<jsp:directive.page import="ecar.dao.VisaoDao"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="comum.util.Pagina"/>
<jsp:directive.page import="ecar.pojo.TipoAcompAbasSisatributoTaAbaSatb"/>
<jsp:directive.page import="ecar.dao.TipoAcompAbasSisatributoTaAbaSatbDao"/>
<jsp:directive.page import="ecar.dao.DemandasGrpAcessoDao"/>
<jsp:directive.page import="ecar.pojo.DemandasGrpAcesso"/>
<jsp:directive.page import="ecar.dao.PesquisaGrpAcessoDao"/>
<jsp:directive.page import="ecar.pojo.PesquisaGrpAcesso"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="ecar.pojo.Pesquisa"/>
<jsp:directive.page import="comum.database.Dao"/>
<jsp:directive.page import="ecar.exception.ECARException"/>
<jsp:directive.page import="java.util.List"/>

<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<%@ include file="/include/estadoMenu.jsp"%>
<%
    TipoAcompGrpAcessoDao tagaDao = new TipoAcompGrpAcessoDao();
    TipoFuncAcompDao tpfaDao      = new TipoFuncAcompDao();
    TipoAcompFuncAcompDao tafcDao = new TipoAcompFuncAcompDao();
    EstruturaDao ettDao           = new EstruturaDao(request);
    SisAtributoDao sisDao         = new SisAtributoDao(request);
    AbaDao abaDao                 = new AbaDao(request);
    //PesquisaDao	pesquisaDao		  = new PesquisaDao(request);	
    
    TipoAcompTipofuncacompSisatributoTaTpfaSatbDao tatpfasatbDao = new TipoAcompTipofuncacompSisatributoTaTpfaSatbDao();
    TipoAcompAbasSisatributoTaAbaSatbDao taabasatbDao = new TipoAcompAbasSisatributoTaAbaSatbDao();
    
    Aba abaDatasLimites = abaDao.buscarAba("DATAS_LIMITES");
    Integer codAbaDatasLimites = abaDatasLimites.getCodAba();
    Aba abaSituacao = abaDao.buscarAba("SITUACAO");
    Integer codAbaSituacao = abaSituacao.getCodAba();


    String selected = "";
    if ( Pagina.getParam(request, "sisAtributoSatb") == null)
        _disabled = "disabled";
    else
        selected = Pagina.getParamStr(request, "sisAtributoSatb").toString();
%>
<table class="form">
<tr>
    <td>
        <table>
        <tr>
            <td class="label">* Grupos de Acesso</td>
            <td><combo:ComboTag 
                nome="sisAtributoSatb"
                objeto="ecar.pojo.SisAtributoSatb"
                label="descricaoSatb"
                value="codSatb"
                order="descricaoAtb"
                colecao="<%=sisDao.getListaAcesso()%>"
                selected="<%=selected%>"
                scripts='onchange=\"aoSelecionarClasseDeAcesso(form);\"'
            />
            </td>
        </tr>
        </table>
    </td>
</tr>
</table>

<!-- Div utilizada para guardar valor dos checks 'Datas Limite' e 'Situação' quando desabilitados -->
<% 
    if (!"".equals(selected)){
        SisAtributoSatb satb = (SisAtributoSatb) sisDao.buscar(SisAtributoSatb.class,Long.valueOf(selected));
        List listtpfa = tpfaDao.getFuncAcompByLabel();
        
        Iterator itett = ettDao.getListaEstruturas().iterator();
    
        if(itett.hasNext()){
            EstruturaAcessoDao ettaDao = new EstruturaAcessoDao(request);
%>
<table class="form">

<tr>
    <td>
        <table>
        <tr>
            <td class="label" style="text-align: left;">Acesso a Estrutura</td>
            <td class="label" style="text-align: center;">Adicionar Novos Itens</td>
            <td class="label" style="text-align: center;">Histórico</td>
            <td class="label" style="text-align: center;">Imprimir</td>
            <td class="label" style="text-align: center;">Gerar Arquivos</td>
        </tr>
<%  
            while (itett.hasNext()){
                EstruturaEtt ett = (EstruturaEtt) itett.next();
                EstruturaAcessoEtta etta = ettaDao.getEstruturaAcessoEtta(satb,ett);
%>
        <tr>
            <td>
<%
                 int nivel = ettDao.getNivel(ett);
                 
                 for(int i = 0; i < nivel; i++)
                    out.print("&nbsp;&nbsp;&nbsp;");
%>          
                <%=ett.getNomeEtt()%>
            </td>
            <td style="text-align: center;">
            	<c:choose>
					<c:when test="<%=!ett.isVirtual()%>">
	            		<input 
	                    type="checkbox" class="form_check_radio" 
	                    name="indIncItemEtta<%=ett.getCodEtt()%>" 
	                    <%=(etta != null)?(Dominios.COM_ACESSO_INCLUSAO.equals(etta.getIndIncItemEtta()))?"checked":"":""%> 
	                    value="<%=Dominios.COM_ACESSO_INCLUSAO%>"
	                    <%=_disabled%>/>
	                </c:when>
					<c:otherwise>
	            		<input 
	                    type="checkbox" disabled="disabled"/>
	                </c:otherwise>
                </c:choose>
            </td>
            <td style="text-align: center;">
            	<c:choose>
					<c:when test="<%=!ett.isVirtual()%>">
	            		<input 
		                    type="checkbox" class="form_check_radio" 
		                    name="indExibirHistoricoEtta<%=ett.getCodEtt()%>" 
		                    <%=(etta != null)?(Dominios.COM_ACESSO_INCLUSAO.equals(etta.getIndExibirHistoricoEtta()))?"checked":"":""%> 
		                    value="<%=Dominios.COM_ACESSO_INCLUSAO%>"
		                    <%=_disabled%>/>
	                </c:when>
					<c:otherwise>
	            		<input 
	                    type="checkbox" disabled="disabled"/>
	                </c:otherwise>
                </c:choose>
            		
            </td>
            
            <td style="text-align: center;">
            	<c:choose>
					<c:when test="<%=!ett.isVirtual() && ett.getIndExibirImprimirListagem() != null && ett.getIndExibirImprimirListagem().equals(Dominios.SIM)%>">
	            		<input 
		                    type="checkbox" class="form_check_radio" 
		                    name="indExibirImprimirEtta<%=ett.getCodEtt()%>" 
		                    <%=(etta != null)?(Dominios.COM_ACESSO_INCLUSAO.equals(etta.getIndExibirImprimirEtta()))?"checked":"":""%> 
		                    value="<%=Dominios.COM_ACESSO_INCLUSAO%>"
		                    <%=_disabled%>/>
	                </c:when>
					<c:otherwise>
	            		<input 
		                    type="checkbox" class="form_check_radio" 
		                    name="indExibirImprimirEtta<%=ett.getCodEtt()%>_disabled" 
		                    <%=(etta != null)?(Dominios.COM_ACESSO_INCLUSAO.equals(etta.getIndExibirImprimirEtta()))?"checked":"":""%> 
		                    value="<%=Dominios.COM_ACESSO_INCLUSAO%>" disabled="disabled"/>
		                 <input type="hidden" name="indExibirImprimirEtta<%=ett.getCodEtt()%>" value="<%=etta != null ? etta.getIndExibirImprimirEtta() : "N" %>"/>
		            </c:otherwise>
                </c:choose>
            		
            </td>
            
            <td style="text-align: center;">
            	<c:choose>
					<c:when test="<%=!ett.isVirtual() && ett.getIndExibirGerarArquivos() != null && ett.getIndExibirGerarArquivos().equals(Dominios.SIM)%>">
	            		<input 
		                    type="checkbox" class="form_check_radio" 
		                    name="indExibirGerarArquivosEtta<%=ett.getCodEtt()%>" 
		                    <%=(etta != null)?(Dominios.COM_ACESSO_INCLUSAO.equals(etta.getIndExibirGerarArquivosEtta()))?"checked":"":""%> 
		                    value="<%=Dominios.COM_ACESSO_INCLUSAO%>"
		                    <%=_disabled%>/>
	                </c:when>
					<c:otherwise>
	            		<input 
		                    type="checkbox" class="form_check_radio" 
		                    name="indExibirGerarArquivosEtta<%=ett.getCodEtt()%>_disabled" 
		                    <%=(etta != null)?(Dominios.COM_ACESSO_INCLUSAO.equals(etta.getIndExibirGerarArquivosEtta()))?"checked":"":""%> 
		                    value="<%=Dominios.COM_ACESSO_INCLUSAO%>" disabled="disabled"/>
		                 <input type="hidden" name="indExibirGerarArquivosEtta<%=ett.getCodEtt()%>" value="<%=etta != null ? etta.getIndExibirGerarArquivosEtta() : "N" %>"/>
	                </c:otherwise>
                </c:choose>
            		
            </td>
            
        </tr>
<%          
            }
%>
        </table>
    </td>
</tr>
</table>
<%	 		
		}
%>		
    
<table class="form">	
	<tr>
		<td class="label" style="text-align: left">Acesso ao Cadastro de Demandas:</td>
	</tr>
	<tr>
		<td>
		<ul style="list-style: none">
			<li style="font-weight: bold"><br>Visões:</li>
	
		
				<% 
					VisaoDao visaoDao = new VisaoDao(request);
					VisaoGrpAcessoDao visaoGrpAcessoDao = new VisaoGrpAcessoDao();				
					
					List visoesGrupoAcesso = visaoGrpAcessoDao.getVisoesVisaoDemandasGrpAcesso(satb.getCodSatb());
					
					List visoes = visaoDao.getVisoes();
					
					request.setAttribute("todasVisoes", visoes);
					request.setAttribute("visoesGrupoAcesso", visoesGrupoAcesso);
				%>
				<c:set var="iguais" value="false" scope="request"/>
				<c:forEach var="visaoTodas" items="${todasVisoes}">
				   <c:if test="${visoesGrupoAcesso !=null}">
						<c:forEach var="visaoGrupoAcesso" items="${visoesGrupoAcesso}">
							<c:if test="${visaoGrupoAcesso.codVisao == visaoTodas.codVisao}">
							   <li>
								   <input type="checkbox" class="form_check_radio"  name="visoes" value="${visaoGrupoAcesso.codVisao}" checked="true" <%=_disabled%>>
								   <c:out value ="${visaoGrupoAcesso.descricaoVisao}"/>
								   <c:set var="iguais" value="true" scope="request"/>
								   <br>
							   </li>
							</c:if> 		
					 	</c:forEach>
					 	<c:if test="${iguais == false}">
							<input type="checkbox" class="form_check_radio"  name="visoes" value="${visaoTodas.codVisao}" <%=_disabled%>>
							<c:out value ="${visaoTodas.descricaoVisao}"/>
							<br>
						</c:if> 
						<c:set var="iguais" value="false" scope="request"/>
				   </c:if> 		
				    <c:if test="${visoesGrupoAcesso == null}">
						   <input type="checkbox" class="form_check_radio"  name="visoes" value="${visaoTodas.codVisao}" <%=_disabled%>>
						   <c:out value ="${visaoTodas.descricaoVisao}"/>
						   <br>
				 	</c:if> 		
				</c:forEach>
			</ul>
<%	
	request.removeAttribute("todasVisoes");
	request.removeAttribute("visoesAtributo");				
%>			
		</td>
	</tr>
</table> 


<%


PesquisaGrpAcessoDao pesquisasGrpAcessoDao = new PesquisaGrpAcessoDao();
PesquisaDao pesquisaDao = new PesquisaDao(request);
PesquisaGrpAcesso pesquisaGrpAcesso = (PesquisaGrpAcesso) pesquisasGrpAcessoDao.getPesquisaGrpAcesso(satb);
Iterator itPesquisas = null;
Set pesquisas = null;

if (satb.getPesquisas()!= null && satb.getPesquisas().size()!=0) {
 pesquisas = satb.getPesquisas();
} 


try {
 List lPesquisas = pesquisaDao.listarPesquisaSistema();
 if (lPesquisas.size()!= 0) {
 	itPesquisas = lPesquisas.iterator();
 }
}catch(ECARException e) {

}

//Caso não encontre uma referencia para o grupo na tabela ele cria um objeto vazio
if (pesquisaGrpAcesso== null) 
	pesquisaGrpAcesso = new  PesquisaGrpAcesso();
 %>
<table class="form">
<tr>
    <td class="label" style="text-align: left">Acesso a Pesquisa:</td>
</tr>
<tr>
    <td>
        <ul style="list-style: none">
			<li style="font-weight: bold"><br>Pode visualizar:</li>
            <li>
			<li >
                 <input type="checkbox" class="form_check_radio" name="indPodeVerGeral" value="<%=Dominios.SIM%>" <%= ((pesquisaGrpAcesso.getIndPodeVerGeral()!= null) && pesquisaGrpAcesso.getIndPodeVerGeral().equals(Dominios.SIM))? "checked":""  %> <%=_disabled%>  />
                 Geral
            </li>
			<li >
                 <input type="checkbox" class="form_check_radio" name="indPodeVerMinhaVisao" value="<%=Dominios.SIM%>" <%= ((pesquisaGrpAcesso.getIndPodeVerMinhaVisao()!= null) && pesquisaGrpAcesso.getIndPodeVerMinhaVisao().equals(Dominios.SIM))? "checked":""  %> <%=_disabled%>  />
                 Minha Visão
            </li>             
			<li >
                 <input type="checkbox" class="form_check_radio" name="indPodeVerPendencias" value="<%=Dominios.SIM%>" <%= ((pesquisaGrpAcesso.getIndPodeVerPendencias()!= null) && pesquisaGrpAcesso.getIndPodeVerPendencias().equals(Dominios.SIM))? "checked":""  %> <%=_disabled%>  /> 
                 Produtos Prioritários
            </li>
            <%while (itPesquisas!= null && itPesquisas.hasNext()){
            	Pesquisa pesquisa = (Pesquisa)itPesquisas.next();
             %>
            <li>
                 <input type="checkbox" class="form_check_radio" name="pesquisa" value="<%=pesquisa.getCodPesquisa()%>" <%= ((pesquisas!= null) && pesquisas.contains(pesquisa))? "checked":""  %> <%=_disabled%>  /> 
                 <%=pesquisa.getNomePesquisa() %>
            </li>             
            <%}//fim while itPesquisa %>            
                         
			<li >
                 <input type="checkbox" class="form_check_radio" name="indPodeVerPersonalizado" value="<%=Dominios.SIM%>" <%= ((pesquisaGrpAcesso.getIndPodeVerPersonalizado()!= null) && pesquisaGrpAcesso.getIndPodeVerPersonalizado().equals(Dominios.SIM))? "checked":""  %> <%=_disabled%>  />
                 Personalizado
            </li>                
			<li style="font-weight: bold"><br>Pode criar:</li>
            <li>
			<li >
                 <input type="checkbox" class="form_check_radio" name="indPodeCriarPesquisaUsuario" value="<%=Dominios.SIM%>" <%= ((pesquisaGrpAcesso.getIndPodeCriarPesquisaUsuario()!= null) && pesquisaGrpAcesso.getIndPodeCriarPesquisaUsuario().equals(Dominios.SIM))? "checked":""  %> <%=_disabled%>  />
                 Pessoais
            </li>
			<li >
                 <input type="checkbox" class="form_check_radio" name="indPodeCriarPesquisaSistema" value="<%=Dominios.SIM%>" <%= ((pesquisaGrpAcesso.getIndPodeCriarPesquisaSistema()!= null) && pesquisaGrpAcesso.getIndPodeCriarPesquisaSistema().equals(Dominios.SIM))? "checked":""  %> <%=_disabled%>  />
                 Do sistema
            </li>
		</ul>
	</td>
	</tr>
</table>             
	

<%
        Iterator itta = (new TipoAcompanhamentoDao()).getTipoAcompanhamentoAtivosByDescricao().iterator();
        
        if (itta.hasNext()){  
%>
<table class="form">
<tr>
    <td class="label" style="text-align: left">Acompanhamento:</td>
</tr>
<tr>
    <td>
        <ul style="list-style: none">
<%
            while (itta.hasNext()){
                TipoAcompanhamentoTa ta = (TipoAcompanhamentoTa) itta.next();

                TipoAcompGrpAcesso taa = (TipoAcompGrpAcesso) tagaDao.getTipoAcompGrpAcesso(satb,ta);
                
                boolean acessotaa = (taa != null)?(taa.getAcessoInclusao() != null)?(Dominios.COM_ACESSO_INCLUSAO.equals(taa.getAcessoInclusao()))?true:false:false:false;
                int sepOrgao = (taa != null)?(taa.getSepararPorOrgao() != null)?taa.getSepararPorOrgao().intValue():0:0;
%>          
             
            
            <li style="font-weight: bold"><br><%=ta.getDescricaoTa()%>
            </li>
            <li>
            <%boolean geraAcompMarcado = false; %>
                <ul style="list-style: none">
                    <li style="font-weight: bold">
                        <input type="checkbox" class="form_check_radio" 
                                        name="GeraAcomp<%=ta.getCodTa()%>" 
                                        value="<%=Dominios.COM_ACESSO_INCLUSAO%>"   <%=(acessotaa)?"checked":"" %>
                                        onclick="javascript:ShowHide(this,'divGeraAcomp<%=ta.getCodTa()%>');javascript:DesabilitarDatasLimites(this,'tpaba<%=ta.getCodTa()%>-<%=codAbaDatasLimites%>-<%=satb.getCodSatb()%>');" 
                                        <%=_disabled%>/>Gerar Periodo de Acompanhamento
                    </li>
                    <div id="divGeraAcomp<%=ta.getCodTa()%>" style='display: &lt;%=('>
                    
				<%if(acessotaa){
					geraAcompMarcado = true;
				}%>

<%
                if(Dominios.SIM.equals(ta.getIndSepararOrgaoTa())){ 
%>                  
                    <li>
                        <ul style="list-style: none;">
                        
                            <li><input type="radio" class="form_check_radio" name="SepararPorOrgao<%=ta.getCodTa()%>" value="1" <%=(sepOrgao == 1 || sepOrgao == 0)?"checked":""%> <%=_disabled%>>Todos os Órgãos</li>
                            <li><input type="radio" class="form_check_radio" name="SepararPorOrgao<%=ta.getCodTa()%>" value="2" <%=(sepOrgao == 2)?"checked":""%> <%=_disabled%>>Somente seu Orgão</li>
                        </ul>
                    </li>
<%
                } 
%>
                    </div>
	                <li style="font-weight: bold">
	                	<%
						//Guarda o valor do indicador GerarArquivo em um campo hidden para ser enviado quando o check estiver desabilitado.  
	                    if(ta.getIndGerarArquivoTa().equals(Dominios.NAO)) { %>
	                    	<input type="hidden"
	                    		value="<% if (taa != null && Dominios.SIM.equals(taa.getIndGerarArquivo())){
	                 			%>S<% } else { %>N<% } %>"
	                    		name="GerarArquivo<%=ta.getCodTa()%>"
	                    		id="GerarArquivo<%=ta.getCodTa()%>" />
	                    		
	                    	<input type="checkbox" class="form_check_radio" 
	                		name="GerarArquivo<%=ta.getCodTa()%>_disabled"
	                		id="GerarArquivo<%=ta.getCodTa()%>_disabled" 
	                        value="<%=Dominios.SIM%>" <%=taa != null ? Pagina.isChecked(taa.getIndGerarArquivo(), Pagina.SIM) : ""%> 
	                        <%= (ta.getIndGerarArquivoTa().equals(Dominios.SIM)) ? _disabled : "disabled"%> />Gerar Arquivos
	                    <% } else {%>
	                	<input type="checkbox" class="form_check_radio" 
	                		name="GerarArquivo<%=ta.getCodTa()%>"
	                		id="GerarArquivo<%=ta.getCodTa()%>" 
	                        value="<%=Dominios.SIM%>" <%=taa != null ? Pagina.isChecked(taa.getIndGerarArquivo(), Pagina.SIM) : ""%> 
	                        <%= (ta.getIndGerarArquivoTa().equals(Dominios.SIM)) ? _disabled : "disabled"%> />Gerar Arquivos
	                    <%} %>
	                </li>
                    <li style="font-weight: bold"><br>Leitura de Acompanhamento Permitido</li>
                    <li>
                        <ul style="list-style: none;">
                             <li style="font-weight: bold"><br>Pareceres</li>
<%				boolean existeParecerMarcado = false;
                for(Iterator it = listtpfa.iterator();it.hasNext();){
                    TipoFuncAcompTpfa tpfa = (TipoFuncAcompTpfa) it.next();
                    
                    TipoAcompTipofuncacompSisatributoTaTpfaSatb tatpfasatb = tatpfasatbDao.buscar(ta.getCodTa(),tpfa.getCodTpfa(),satb.getCodSatb());
                    
//                  TipoAcompFuncAcompTafc tafc = tafcDao.buscar(ta.getCodTa(),tpfa.getCodTpfa()); 
%>
                            <li><input type="checkbox" class="form_check_radio" 
                                    name='Tpfa<%=ta.getCodTa() + "-" + tpfa.getCodTpfa() + "-" + satb.getCodSatb()%>'
                                    value="<%=Dominios.COM_ACESSO_LEITURA%>"
                                    <%=(tatpfasatb != null)?Dominios.COM_ACESSO_LEITURA.equals(tatpfasatb.getIndLeituraParecer())?"checked":"":""%> 
									onclick="javascript:DesabilitarSituacao(this,'tpaba<%=ta.getCodTa()%>-<%=codAbaSituacao%>-<%=satb.getCodSatb()%>','Tpfa<%=ta.getCodTa()%>');"
                                    <%=_disabled%>/>
                                    <%=tpfa.getLabelTpfa()%>
                            </li>
                   <%if(tatpfasatb != null && Dominios.COM_ACESSO_LEITURA.equals(tatpfasatb.getIndLeituraParecer())){
						existeParecerMarcado = true;
                   }%>
<%                   
                }
%>
                            <li style="font-weight: bold"><br>Abas</li>
                        
<%
                for(Iterator it = abaDao.getListaAbas().iterator();it.hasNext();){
                    Aba aba = (Aba) it.next();
                    
                    TipoAcompAbasSisatributoTaAbaSatb taabasatb = taabasatbDao.buscar(ta.getCodTa(),new Long(aba.getCodAba().longValue()), satb.getCodSatb());
//                  TipoAcompAba tpaba = tpabaDao.buscar(new Integer(ta.getCodTa().intValue()),aba.getCodAba());


					if("n".equalsIgnoreCase(aba.getAbaSuperior()) && !aba.getNomeAba().equals("SITUACAO_INDICADORES") && !aba.getNomeAba().equals("NIVEL_PLANEJAMENTO")){
%>                  
                            <li><input 
                                    type="checkbox" class="form_check_radio" 
                                    value="<%=Dominios.COM_ACESSO_LEITURA%>"
                                    <%=(taabasatb != null && taabasatb.getIndVisualizaAba() != null)?Dominios.COM_ACESSO_LEITURA.equals(taabasatb.getIndVisualizaAba())?"checked":"":""%>
                                	<%=_disabled%>                          
                                    <%if(aba.getCodAba().equals(codAbaSituacao)){%>
										name="tpaba<%=ta.getCodTa()+ "-" + aba.getCodAba() + "-" + satb.getCodSatb()+"_hidden"%>"
										onclick="javascript:alteraCampoHidden(this,'tpaba<%=ta.getCodTa()%>-<%=codAbaSituacao%>-<%=satb.getCodSatb()%>');"
                                    	<%if(existeParecerMarcado) {                                    		
                                    	%>                                    		
                                    		disabled checked                                     		
                                    	<%}%>
                                    <%}else if(aba.getCodAba().equals(codAbaDatasLimites)){%>
                                    	name="tpaba<%=ta.getCodTa()+ "-" + aba.getCodAba() + "-" + satb.getCodSatb()+"_hidden"%>"
										onclick="javascript:alteraCampoHidden(this,'tpaba<%=ta.getCodTa()%>-<%=codAbaDatasLimites%>-<%=satb.getCodSatb()%>');"
                                    	<%if(geraAcompMarcado){%>
                                    		disabled checked
                                    		
                                    	<%}%>
                                  	<%}else{%>
	                                  	name="tpaba<%=ta.getCodTa()+ "-" + aba.getCodAba() + "-" + satb.getCodSatb()%>"
                                  	<% }%>                             
								>
								
								                                
                                <%//Guarda o valor da aba "Situação" em um campo hidden para ser enviado quando a aba estiver desabilitada.  
                                if(aba.getCodAba().equals(codAbaSituacao)){%>
                                	<input
                                		type=hidden
                                		value="<% if (existeParecerMarcado){//if(taabasatb != null && taabasatb.getIndVisualizaAba() != null && Dominios.COM_ACESSO_LEITURA.equals(taabasatb.getIndVisualizaAba())){
                                					%>S<%
              	                 				}else{%>N<%}%>"
                                		name="tpaba<%=ta.getCodTa()+ "-" + aba.getCodAba() + "-" + satb.getCodSatb()%>"
                                		id="tpaba<%=ta.getCodTa()+ "-" + aba.getCodAba() + "-" + satb.getCodSatb()%>"
                                	>
                                <%
                                //Guarda o valor da aba "Datas Limites" em um campo hidden para ser enviado quando a aba estiver desabilitada.
                                }else if(aba.getCodAba().equals(codAbaDatasLimites)){%>
                                	<input
                                		type=hidden
                                		value="<%if(taabasatb != null && taabasatb.getIndVisualizaAba() != null && Dominios.COM_ACESSO_LEITURA.equals(taabasatb.getIndVisualizaAba())){
                                					%>S<%
              	                 				}else{%>N<%}%>"
                                		name="tpaba<%=ta.getCodTa()+ "-" + aba.getCodAba() + "-" + satb.getCodSatb()%>"
                                	>
								<%}
								
								if(aba.getFuncaoFun()!=null){%>
									<%=aba.getFuncaoFun().getLabelPadraoFun()%>
								<%}else{%>
                                	<%=aba.getLabelAba()%>
                                <%}%>
                            </li>
<%
                                }                          
                }
%>              
                            <li>&nbsp;</li>
                        </ul>
                    </li>
                </ul>
            </li>
<% 
            } 
%>          
        </ul>
    </td>
</tr>
<%
        }
    }
%>
</table>