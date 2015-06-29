/*
 * Created on 20/02/2006
 *
 */
package ecar.servlet.relatorio;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;
import comum.util.XmlBuilder;

import ecar.bean.AtributoEstruturaListagemItens;
import ecar.bean.TotalizadorRelatorios;
import ecar.dao.ConfiguracaoDao;
import ecar.dao.EstruturaDao;
import ecar.dao.FuncaoDao;
import ecar.dao.ItemEstruturaCriterioDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.SituacaoDao;
import ecar.exception.ECARException;
import ecar.pojo.AtributoLivre;
import ecar.pojo.ConfiguracaoCfg;
import ecar.pojo.CriterioCri;
import ecar.pojo.FuncaoFun;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturaSisAtributoIettSatb;
import ecar.pojo.ObjetoEstrutura;
import ecar.pojo.OrgaoOrg;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.SituacaoSit;

/**
 * @author aleixo
 *
 */
public class RelacaoItemEstrutura extends AbstractServletReportXmlXsl {

	private static final long serialVersionUID = 2395457474744685932L;
	private List idsEstrutura;
	private List totalizadores;
	private ItemEstruturaCriterioDao itemCriterioDao;
	private SituacaoDao situacaoDao;
	private FuncaoDao funcaoDao;
	private List itensSelecionados;
	private boolean imprimirEstrutura;
	
	/**
	 * CONSTANTES
	 */
	private static final int COMBOBOX = 1;
	private static final int CHECKBOX = 2;
	private static final int LISTBOX = 3;
	private static final int RADIO_BUTTON = 4;
	private static final int TEXT = 5;
	private static final int IMAGEM = 6;   
	private static final int MULTITEXTO = 7;   
	private static final int VALIDACAO = 8;
	private static final int MULTIPLO = 9;
	private static final int TEXTAREA = 10;
	
	/**
	 * Gera XML.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param request
	 * @return StringBuffer
	 * @throws ECARException
	 */
	public StringBuffer getXml(HttpServletRequest request) throws ECARException{
		
		Dao dao = new Dao();
        idsEstrutura = new ArrayList();
        totalizadores = new ArrayList();
        itemCriterioDao = new ItemEstruturaCriterioDao(request);
        funcaoDao = new FuncaoDao(request);
        situacaoDao =  new SituacaoDao(request);
        itensSelecionados = new ArrayList();
        
        
        XmlBuilder builder = new XmlBuilder();

    	imprimirEstrutura = ("S".equals(Pagina.getParamStr(request, "imprimirEstrutura"))) ? true : false;
    	
        String datahora = Data.parseDateHour(new Date()).substring(0,16); //este método retorna dd/mm/aaaa hh:mm:ss:ssss. Faço Substring para poder pegar só "dd/mm/aaaa hh:mm"
        String orgao = request.getParameter("codOrgaoSelecionado");
        
        ConfiguracaoCfg config = new ConfiguracaoDao(null).getConfiguracao();
        String titulo = config.getTituloSistema();
        String tituloItens = "";
        String cabecalho = "Relação dos Itens da Estrutura do " + titulo;
        String indTipoRelatorio = Pagina.getParamStr(request, "indTipoRelatorio");
        
    	//Pegando os critérios que foram selecionados como filtro
    	String criteriosCom = Pagina.getParamStr(request, "criteriosIncluidosCom");
    	String criteriosSem = Pagina.getParamStr(request, "criteriosIncluidosSem");
    	String[] criteriosParaFiltrarCom = (!"".equals(criteriosCom.trim())) ? criteriosCom.split("\\|") : new String[] {};
    	String[] criteriosParaFiltrarSem = (!"".equals(criteriosSem.trim())) ? criteriosSem.split("\\|") : new String[] {};

    	List listaCriteriosCom = new ArrayList();					
    	List listaCriteriosSem = new ArrayList();					

    	for(int i = 0; i < criteriosParaFiltrarCom.length; i++){
    		listaCriteriosCom.add(criteriosParaFiltrarCom[i]);
    	}        

    	for(int i = 0; i < criteriosParaFiltrarSem.length; i++){
    		listaCriteriosSem.add(criteriosParaFiltrarSem[i]);
    	}        
        
    	//Pegando as situacoes que foram selecionados como filtro
    	String situacoes = Pagina.getParamStr(request, "situacoesIncluidas");
    	String[] situacoesParaFiltrar = (!"".equals(situacoes.trim())) ? situacoes.split("\\|") : new String[] {};

    	List listaSituacoes = new ArrayList();					

    	for(int i = 0; i < situacoesParaFiltrar.length; i++){
    		listaSituacoes.add(situacoesParaFiltrar[i]);
    	}        
    	
    	//Pegando o órgão selecionado
    	OrgaoOrg orgaoResponsavel = new OrgaoOrg();
    	if(!"".equals(orgao) && orgao != null){
    		orgaoResponsavel = (OrgaoOrg) dao.buscar(OrgaoOrg.class, Long.valueOf(orgao));
    	}
    	
    	String orgaoEscolhido = (orgaoResponsavel.getCodOrg() != null) ? orgaoResponsavel.getSiglaOrg() : "Todos os Órgãos";
        
        if("T".equals(Pagina.getParamStr(request, "todosCheck"))){
        	tituloItens = "Todos";
        }
        else if("M".equals(Pagina.getParamStr(request, "todosCheck"))){
        	tituloItens = "Monitorados";
        }
        else if("N".equals(Pagina.getParamStr(request, "todosCheck"))){
        	tituloItens = "Não Monitorados";
        }
        else {
        	tituloItens = "Conforme Relação Informada Pelo Usuário";
        }
        
        ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request);
        ItemEstruturaIett item = new ItemEstruturaIett();
        	
        if(!imprimirEstrutura){
        	item = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamStr(request, "codIett")));
        }
        
        builder.addNode("relatorio", 
        		"titulo=\"" + builder.normalize(Util.normalizaCaracterMarcador(cabecalho)) + "\"" +
        		" datahora=\"" + datahora + "\"");
        
        geraXMLCapa(builder, titulo, tituloItens, orgaoEscolhido, listaCriteriosCom, listaCriteriosSem, listaSituacoes);
        
        builder.addNode("item");
        String[] filhos = request.getParameterValues("itemFilho");
        if(filhos != null){
            for(int i=0; i < filhos.length;i++){
            	itensSelecionados.add((ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(filhos[i])));
            }                
        }
        
        List listaFilhos = new ArrayList();
        
        if(!imprimirEstrutura){
        	//listaFilhos.addAll(itemEstruturaDao.getArvoreItens(itensSelecionados, item));
        	listaFilhos.addAll(itensSelecionados);
        }
        else {
//        	long codIettPai = Pagina.getParamLong(request, "codIettPaiImprimir");
//        	ItemEstruturaIett iettPai = null;
//        	if(codIettPai != 0){
//        		iettPai = (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, Long.valueOf(codIettPai));
//        	}
        	//listaFilhos.addAll(itemEstruturaDao.getArvoreItens(itensSelecionados,iettPai));
        	listaFilhos.addAll(itensSelecionados);
        }
        
        List listaFilhosOrdenada = new ArrayList(itemEstruturaDao.getItensOrdenados(listaFilhos, null));
        
        //Iterator itFilhos = listaFilhos.iterator();
        Iterator itFilhos = listaFilhosOrdenada.iterator();
        while(itFilhos.hasNext()){
        	AtributoEstruturaListagemItens atbList = (AtributoEstruturaListagemItens) itFilhos.next();
        	//ItemEstruturaIett filho = (ItemEstruturaIett) itFilhos.next();
        	ItemEstruturaIett filho = atbList.getItem();
        	String nomeDinamico = "".equals(atbList.getDescricao().trim()) ? filho.getNomeIett() : atbList.getDescricao();
        	geraXMLEstrutura(builder, atbList.getItem(), nomeDinamico, indTipoRelatorio, orgao, request);
        }
        
        builder.closeNode("item");
        
        this.geraXMLTotalizador(builder);
        
        builder.closeNode("relatorio");
        return builder.toStringBuffer();
    }
    
	/**
	 * Gera capa XML.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
         * @param builder
         * @param titulo
         * @param orgao
         * @param tituloItens
         * @param listaCriteriosSem
         * @param listaCriteriosCom 
         * @param listaSituacoes
         * @throws ECARException
	 */
    public void geraXMLCapa(XmlBuilder builder, String titulo, String tituloItens, String orgao, 
    		List listaCriteriosCom, List listaCriteriosSem, List listaSituacoes) throws ECARException{
        builder.addNode("capa",
        		"titulo=\"" + builder.normalize(Util.normalizaCaracterMarcador(titulo)) + "\"" +
        		" tituloItens=\"" + builder.normalize(Util.normalizaCaracterMarcador(tituloItens)) + "\"" +
        		" orgao=\"" + builder.normalize(Util.normalizaCaracterMarcador(orgao)) + "\"");
        
    	FuncaoFun criterioFun = funcaoDao.getFuncao(Long.valueOf("6")); // 6 = Critérios
    	
    	if(criterioFun != null && 
    			(listaCriteriosCom != null && listaCriteriosCom.size() > 0
    			|| listaCriteriosSem != null && listaCriteriosSem.size() > 0)){
        	builder.addNode("filtros", "tipo=\"" + builder.normalize(Util.normalizaCaracterMarcador(criterioFun.getLabelPadraoFun())) + "\"" +
        					" estilo=\"CS\""); //CS --> Monta tabela com colunas de Com/Sem
	        
        	if(listaCriteriosCom != null && listaCriteriosCom.size() > 0){
	        	Iterator itCriterios = listaCriteriosCom.iterator();
		        while(itCriterios.hasNext()){
		        	String id = (String) itCriterios.next();
		        	CriterioCri criterio = (CriterioCri) itemCriterioDao.buscar(CriterioCri.class, Long.valueOf(id));
		        	
		        	if(criterio != null){
		        		builder.addClosedNode("filtro", " valor=\"" + builder.normalize(Util.normalizaCaracterMarcador(criterio.getDescricaoCri())) + "\" tipo=\"C\"");
		        	}
		        }
        	}
        	
        	if(listaCriteriosSem != null && listaCriteriosSem.size() > 0){
	        	Iterator itCriterios = listaCriteriosSem.iterator();
		        while(itCriterios.hasNext()){
		        	String id = (String) itCriterios.next();
		        	CriterioCri criterio = (CriterioCri) itemCriterioDao.buscar(CriterioCri.class, Long.valueOf(id));
		        	
		        	if(criterio != null){
		        		builder.addClosedNode("filtro", " valor=\"" + builder.normalize(Util.normalizaCaracterMarcador(criterio.getDescricaoCri())) + "\" tipo=\"S\"");
		        	}
		        }
        	}
	        
        	builder.closeNode("filtros");
    	}
    	
    	if(listaSituacoes != null && listaSituacoes.size() > 0){
        	builder.addNode("filtros", "tipo=\"" + builder.normalize("Situações") + "\"" +
			" estilo=\"SI\""); //SI --> Simples, Sem colunas de Com/Sem
        	
        	Iterator itSituacoes = listaSituacoes.iterator();
        	while(itSituacoes.hasNext()){
        		String id = (String) itSituacoes.next();
        		SituacaoSit situacao = (SituacaoSit) situacaoDao.buscar(SituacaoSit.class, Long.valueOf(id)); 
        		if(situacao != null){
        			builder.addClosedNode("filtro", " valor=\"" + builder.normalize(Util.normalizaCaracterMarcador(situacao.getDescricaoSit())) + "\" tipo=\"N\"");
        		}
        	}
        	builder.closeNode("filtros");
    	}
        
        builder.closeNode("capa");
    }

    /**
     * Gera estrutura Xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @param nomeDinamico
     * @param orgao
     * @param tipoRelatorio
     * @throws ECARException
     */
    public void geraXMLEstrutura(XmlBuilder builder, ItemEstruturaIett item, String nomeDinamico, 
    		String tipoRelatorio, String orgao, HttpServletRequest request) throws ECARException{

    	String itemSelecionado = "S";
    	String separador = null;
        
    	if(!itensSelecionados.contains(item)){
    		itemSelecionado = "N";
    	}
    	
        builder.addNode("estrutura", "nome=\"" + builder.normalize(Util.normalizaCaracterMarcador(item.getEstruturaEtt().getNomeEtt())) + "\" " +
        		//" descricao=\"" + builder.normalize(item.getSiglaIett()) + " - " + builder.normalize(item.getNomeIett()) + "\" " +
        		" descricao=\"" + builder.normalize(Util.normalizaCaracterMarcador(nomeDinamico)) + "\" " +
        		" nivel=\"" + builder.normalize(Util.normalizaCaracterMarcador(item.getNivelIett().toString())) + "\"" +
        		" itemSelecionado=\"" + builder.normalize(Util.normalizaCaracterMarcador(itemSelecionado)) + "\"");
        
        this.incrementarTotalizador(item.getEstruturaEtt().getCodEtt(), item.getEstruturaEtt().getNomeEtt());
        
        boolean mostrarDados = false;
        
        if("".equals(orgao) || orgao == null){ //Todos
        	mostrarDados = true;
        }
        else if (orgao != null && item.getOrgaoOrgByCodOrgaoResponsavel1Iett() != null && orgao.equals(item.getOrgaoOrgByCodOrgaoResponsavel1Iett().getCodOrg())){
        	mostrarDados = true;
        }
        
        if (!itensSelecionados.contains(item)){
        	mostrarDados = false;
        }
        
        if(mostrarDados){
	        List dados = new EstruturaDao(null).getAtributosEstruturaRelatorio(item.getEstruturaEtt(), tipoRelatorio);
	        if(dados.size() > 0){
	            builder.addNode("campos");
	            Iterator itDados = dados.iterator();
	            while(itDados.hasNext()){
	                ObjetoEstrutura estruturaAtributo = (ObjetoEstrutura) itDados.next();
	
	                
                    String valor = "";
                    
                    if(estruturaAtributo.iGetGrupoAtributosLivres() != null){
                    	//Atributo Livre:
                    	if(item.getItemEstruturaSisAtributoIettSatbs() != null){
                    		List<ItemEstruturaSisAtributoIettSatb> listaSisAtributosInformados = item.obterAtirbutosLivresInformados(estruturaAtributo.iGetGrupoAtributosLivres());
                    		int tam = listaSisAtributosInformados.size();
                    		int pos = 0;
                	    	Iterator itAtribLivres = item.getItemEstruturaSisAtributoIettSatbs().iterator();
                	    	while(itAtribLivres.hasNext()){
                	    		ItemEstruturaSisAtributoIettSatb atributo = (ItemEstruturaSisAtributoIettSatb) itAtribLivres.next();
                	    		AtributoLivre atributoLivre = new AtributoLivre();
                	    		atributoLivre.setSisAtributoSatb(atributo.getSisAtributoSatb());
                	    		atributoLivre.setInformacao(atributo.getInformacao());
                	    		
                   	    		if(!atributoLivre.getSisAtributoSatb().getSisGrupoAtributoSga().equals(estruturaAtributo.iGetGrupoAtributosLivres()))
                	    			continue;
                	    		
                	    		if((atributoLivre.getSisAtributoSatb()
                	    				.getSisGrupoAtributoSga()
                	    				.getSisTipoExibicGrupoSteg()
                	    				.getCodSteg().intValue() != TEXT) && 
                	    			(atributoLivre.getSisAtributoSatb()
                        	    			.getSisGrupoAtributoSga()
                        	    			.getSisTipoExibicGrupoSteg()
                        	    			.getCodSteg().intValue() != TEXTAREA) &&
                	    			(atributoLivre.getSisAtributoSatb()
                        	    			.getSisGrupoAtributoSga()
                        	    			.getSisTipoExibicGrupoSteg()
                        	    			.getCodSteg().intValue() != VALIDACAO)) {
                	    		
	                	    		if(separador == null){
	                	    			separador = new ConfiguracaoDao(request).getConfiguracao().getSeparadorCampoMultivalor();
	                	    		}
	                	    		
	                	    		if(atributoLivre.getSisAtributoSatb() != null) {
	                	    			valor += atributoLivre.getSisAtributoSatb().getDescricaoSatb();
	                	    			pos++;
	                	    		} else {
	                	    			valor += atributoLivre.getInformacao();
	                	    		}
	                	    		
	                	    		if (pos < tam){
	                	    			valor += separador;
	                	    		}

                	    		} else {
                	    			valor = atributoLivre.getInformacao();
                	    		}
                	    	}
                    	}
             
                    	valor = valor.trim(); 
                    } else {
                    	if("nivelPlanejamento".equals(estruturaAtributo.iGetNome())){
                        	if(item.getItemEstruturaNivelIettns() != null){
                        		int tam = item.getItemEstruturaNivelIettns().size();
                        		int pos = 1;
                    	    	Iterator niveis = item.getItemEstruturaNivelIettns().iterator();
                    	    	while(niveis.hasNext()){
                    	    		SisAtributoSatb nivel = (SisAtributoSatb) niveis.next();
                    	    		
                    	    		if(separador == null){
                    	    			separador = new ConfiguracaoDao(request).getConfiguracao().getSeparadorCampoMultivalor();
                    	    		}

                    	    		valor += nivel.getDescricaoSatb();
                    	    		pos++;
                    	    		
                    	    		if (pos < tam){
                    	    			valor += separador;
                    	    		}
                    	    		
                    	    	}
                        	}
                 
                        	valor = valor.trim(); 
                    		
                    	}
                    	else {
                        	valor = estruturaAtributo.iGetValor(item);
                    	}
                    }
	                
	                valor = Util.normalizaCaracterMarcador(valor);
                    
	                builder.addClosedNode("campo", "label=\"" + builder.normalize(Util.normalizaCaracterMarcador(estruturaAtributo.iGetLabel())) + ": \"" + " valor=\"" + builder.normalize(Util.normalizaCaracterMarcador(valor)) + "\"");
	            }
	            builder.closeNode("campos");
	        }            
        }
        builder.closeNode("estrutura");
    }

    /**
     * Gera Xml totalizador.<br>
     * 
     * @param builder
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
    public void geraXMLTotalizador(XmlBuilder builder) throws ECARException{
        try{
        	int total = 0;
        	builder.addNode("totalizadores");        	
            Iterator itTotalizadores = totalizadores.iterator();
            while(itTotalizadores.hasNext()){
            	TotalizadorRelatorios tr = (TotalizadorRelatorios) itTotalizadores.next();
            	String nome = tr.getEstrutura();
            	String valor = "" + tr.getTotal();
            	total += tr.getTotal();
            	builder.addClosedNode("totalizador",
            			"nome=\"" + builder.normalize(Util.normalizaCaracterMarcador(nome)) + ":\"" +
            			" valor=\"" + builder.normalize(Util.normalizaCaracterMarcador(valor)) + "\"");
            }        	
        	builder.addClosedNode("total", 
        			"nome=\"Total:\"" +
        			" valor=\"" + builder.normalize(Util.normalizaCaracterMarcador(String.valueOf(total))) + "\"");
            builder.closeNode("totalizadores");
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: Totalizadores");
        }            
    }
    
    /**
     * Incrementa totalizador.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Long idEstrutura
     * @param String nomeEstrutura
     */
    private void incrementarTotalizador(Long idEstrutura, String nomeEstrutura){
    	if(!idsEstrutura.contains(idEstrutura)){
    		TotalizadorRelatorios totalizador = new TotalizadorRelatorios();
    		totalizador.setId(idEstrutura);
    		totalizador.setEstrutura(nomeEstrutura);
    		totalizador.setTotal(1);
    		
    		idsEstrutura.add(idEstrutura);
    		totalizadores.add(totalizador);
    	}
    	else {
    		Iterator itTotalizadores = totalizadores.iterator();
    		while(itTotalizadores.hasNext()){
    	   		TotalizadorRelatorios totalizador = (TotalizadorRelatorios) itTotalizadores.next();
    	   	    
    	   		if(idEstrutura.equals(totalizador.getId())){
    	   			totalizador.incrementeTotal();
    	   			break;
    	   	    }
    		}
    	}
    }
    

    /**
     * Pega o nome do arquivo xls.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return String
     */
    public String getXslFileName() {
        return "relacaoEstrutura.xsl";
    }
    
    /**
     * Pega o erro de pagina.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param request
     * @param mensagem
     * @return String
     */
    public String getErrorPage(HttpServletRequest request, String mensagem){        
        String errorPage = "frm_rel.jsp?codIett=" + Pagina.getParamStr(request, "codIett") + "&msgOperacao=" + mensagem + "&codAba="+Pagina.getParamStr(request, "codAba"); 
        return errorPage;
    }

}
