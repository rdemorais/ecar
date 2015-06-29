/*
 * Created on 19/05/2006
 *
 */
package ecar.servlet.relatorio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import comum.util.Data;
import comum.util.Pagina;
import comum.util.Util;
import comum.util.XmlBuilder;

import ecar.bean.FonteRecursosPPA;
import ecar.bean.TotalizadorRelatorios;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.ItemEstruturarevisaoIettrev;

/**
 * @author aleixo
 *
 */
public class RelatorioApendiceTres extends AbstractServletReportXmlXsl {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2395457474744685932L;
	private static final int nivelPrograma = 2;
	private static final int nivelAcao = 3;
	private static final int nivelProduto = 4;
	
	private ItemEstruturaDao itemEstruturaDao;
	
	private String periodoIni;
	private String periodoFim;
	private int paginaInicial;
	private String titulo;
	private List todosItens;
	private String tipoRelatorio;
	private String tipoValor;
	
	private List idsEstrutura;
	private List totalizadorEstrutura;
	private List totalizadorValores;
	
	private double totalGeralProgramas;
	private double totalGeralAcoes;
	
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
		XmlBuilder builder = new XmlBuilder();
		itemEstruturaDao = new ItemEstruturaDao(request);
		
		idsEstrutura = new ArrayList();
		totalizadorEstrutura = new ArrayList();
		totalizadorValores = new ArrayList();
		
		totalGeralAcoes = 0;
		totalGeralProgramas = 0;
		
		periodoIni = Pagina.getParamStr(request, "periodoIni");
		periodoFim = Pagina.getParamStr(request, "periodoFim");
		paginaInicial = Pagina.getParamInt(request, "paginaInicial");
		tipoRelatorio = Pagina.getParamStr(request, "indTipoRelatorio");
		tipoValor = Pagina.getParamStr(request, "indTipoValor");
		
		titulo = "ESTADO DO PARANÁ - PPA " + periodoIni + "/" + periodoFim + " - Atualizado";
		
		Util.liberarImagem();
		
		String nomeRelatorio = "";
		String valorRelatorio = ""; //A = Valores Aprovados, R = Valores Revisados (Não aparece este último pq é o oficial
		if("A".equals(tipoValor)){
			valorRelatorio = " - Valores Aprovados (Histórico)";
		}
		String indMostrarTotalizador = Pagina.getParamStr(request, "indMostrarTotalizador");

		nomeRelatorio = "REVISÃO DO PPA " + periodoIni + "-" + periodoFim + " - PPA ATUALIZADO";
		
		String mesAnoGeracao = Data.getNomeMesExtenso(Data.getMes(Data.getDataAtual()) + 1).toUpperCase() + " " + String.valueOf(Data.getAno(Data.getDataAtual()));

		builder.addNode("relatorio", 
				" titulo=\"" + builder.normalize(titulo) + "\"" +
				" capa=\"" + builder.normalize(String.valueOf(paginaInicial)) + "\"" +
				" paginaInicial=\"" + builder.normalize(String.valueOf(paginaInicial+1)) + "\"" +
				" nomeRelatorio=\"" + builder.normalize(nomeRelatorio) + "\"" +
				" nomeRelatorioRodape=\"" + builder.normalize(valorRelatorio) + "\"" +
				" tipo=\"" + builder.normalize(tipoRelatorio) + "\"" +
				" anoIni=\"" + builder.normalize(periodoIni) + "\"" +
				" anoFim=\"" + builder.normalize(periodoFim) + "\"" +
				" valores=\"" + builder.normalize(tipoValor) + "\"" +
				" mesAnoGeracao=\"" + builder.normalize(mesAnoGeracao) + "\"" +
				" mostrarTotalizador=\"" + builder.normalize(indMostrarTotalizador) + "\"");
		
		todosItens = new ArrayList(itemEstruturaDao.getArvoreItensIettComRevisao(tipoRelatorio, periodoIni, periodoFim));
		
		geraXMLItens(builder);
		
		geraXMLTotalizador(builder);
		
		builder.closeNode("relatorio");
        return builder.toStringBuffer();
    }
    

	/**
	 * Pega o nome do arquivo xsl.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
    public String getXslFileName() {
        return "relatorioApendice.xsl";
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
        String errorPage = "rel_ppa.jsp?msgOperacao=" + mensagem; 
        return errorPage;
    }
    
    /**
     * Gera itens xml.<br>
     * 
     * @param builder
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
    public void geraXMLItens (XmlBuilder builder) throws ECARException {
    	List itens = new ArrayList(todosItens);
    	
    	if(itens != null){
    		Iterator itItens = itens.iterator();
    		while(itItens.hasNext()){
    			ItemEstruturaIett iett = (ItemEstruturaIett) itItens.next();
    			
    			if(iett.getNivelIett().intValue() == nivelPrograma){
    				geraXMLPrograma(builder, iett);
    			}
    		}
    	}
    }
    
    /**
     * Gera tag Programa no XML.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @throws ECARException
     */
    
    public void geraXMLPrograma(XmlBuilder builder, ItemEstruturaIett item) throws ECARException{
    	
    	//this.incrementarTotalizadorEstrutura(item.getEstruturaEtt().getCodEtt(), "Programas Considerados");
    	
    	String nomePrograma = item.getNomeIett();
    	String codigoPrograma = item.getSiglaIett();
    	
    	builder.addNode("programa", 
    			"nome=\"" + builder.normalize(nomePrograma) + "\"" +
    			" codigo=\"" + builder.normalize(codigoPrograma) + "\"");
    	
    	geraXMLDadosPrograma(builder, item);
    	
    	builder.addNode("acao", 
    			"periodo=\"" + builder.normalize(periodoIni + "-" + periodoFim) + "\"" +
    			" tipoApendice=\"" + builder.normalize(tipoRelatorio) + "\"");
    	
    	//Para cada Programa, gerar as ações...
    	List itensAcao = new ArrayList(todosItens);
    	Iterator itAcao = itensAcao.iterator();
    	boolean gerouAcao = false;
    	while(itAcao.hasNext()){
    		ItemEstruturaIett iettAcao = (ItemEstruturaIett) itAcao.next();
    		if(iettAcao.getNivelIett().intValue() == nivelAcao && item.equals(iettAcao.getItemEstruturaIett())){
    			boolean aux = geraXMLAcao(builder, iettAcao);
    			gerouAcao = gerouAcao || aux;
    			/*
    			 * Foi necessário criar uma variavel, pois se fizer:
    			 * gerouAcao = gerouAcao || geraXMLAcao() e gerouAcao for setada como true,
    			 * nas próximas vezes o método que gera as ações nem é executado.
    			 */
    		}
    	}
    	
    	if(gerouAcao)
    		this.incrementarTotalizadorEstrutura(item.getEstruturaEtt().getCodEtt(), "Programas Considerados");
    	
    	builder.closeNode("acao");
    	
    	builder.closeNode("programa");
    }

    /**
     * Gera dados programa xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @throws ECARException
     */
    public void geraXMLDadosPrograma(XmlBuilder builder, ItemEstruturaIett item) throws ECARException{
    	builder.addNode("dados");
    	String orgao = "";
    	if(item.getOrgaoOrgByCodOrgaoResponsavel2Iett() != null)
    		orgao = item.getOrgaoOrgByCodOrgaoResponsavel2Iett().getDescricaoOrg();
    	
    	builder.addClosedNode("campo", "label=\"Órgão Resp.\" valor=\"" + builder.normalize(orgao) + "\"");
    	builder.closeNode("dados");
    }

    /**
     * Gera tag Ação no XMl.<br>
     * 
     * @param builder
     * @param item
     * @author N/C
     * @since N/C
     * @version N/C
     * @return boolean
     * @throws ECARException
     */
    public boolean geraXMLAcao(XmlBuilder builder, ItemEstruturaIett item) throws ECARException{

    	String situacaoAcao = "";
    	String tipoSituacaoAcao = "";
    	String justificativa = "";
    	String nome = "";
    	
    	boolean acaoExcluida = false;
    	boolean gerouAcao = false;
    	
    	if(item.getItemEstruturarevisaoIettrevs() != null && item.getItemEstruturarevisaoIettrevs().size() > 0){
    		ItemEstruturarevisaoIettrev ultRevAcao = getUltimaRevisaoIett(item.getItemEstruturarevisaoIettrevs());
    		if(ultRevAcao != null){
    			tipoSituacaoAcao = ultRevAcao.getSituacaoIettrev();
    			
    			if(ultRevAcao.getDescricaoR3rev() != null && !"".equals(ultRevAcao.getDescricaoR3rev()))
    				nome = ultRevAcao.getDescricaoR3rev();
    			else
    				nome = ultRevAcao.getNomeIettrev();
    			
    			if("E".equals(tipoSituacaoAcao)){
    				situacaoAcao = " (Ação Excluída)";
    				acaoExcluida = true;
    				justificativa = ultRevAcao.getJustificativaIettrev();
    			}
    		}
    	}

    		
		boolean gerarAcao = acaoExcluida;
		
		if(!acaoExcluida){
			gerarAcao = verificarProdutosExcluidos(item);
		}
		
		if(gerarAcao){
		
			if("".equals(nome)){
				if(item.getDescricaoR3() != null && !"".equals(item.getDescricaoR3()))
					nome = item.getDescricaoR3();
				else
					nome = item.getNomeIett();
			}
			
	    	builder.addNode("itemAcao", 
	    			//"nome=\"" + builder.normalize(item.getNomeIett()) + "\"" +
	    			"nome=\"" + builder.normalize(nome.toUpperCase()) + "\"" +
	    			" codigo=\"" + builder.normalize(item.getSiglaIett()) + "\"" +
	    			" valorTotal=\"\""  +
	    			" tipoSituacaoAcao=\"" + builder.normalize(tipoSituacaoAcao) + "\"" +
	    			" situacaoAcao=\"" + builder.normalize(situacaoAcao) + "\"" +
	    			" justificativa=\"" + builder.normalize(justificativa) + "\"" +
	    			" larguraColAcao=\"13.5cm\"" +
	    			" larguraColAux=\"1cm\"");
	    	
	    	gerouAcao = true;
	    	this.incrementarTotalizadorEstrutura(item.getEstruturaEtt().getCodEtt(), "Quantidade de Ações Excluídas");
	    	
	    	//Para cada Ação, gerar os produtos...
	    	List itensProduto = new ArrayList(todosItens);
	    	Iterator itProduto = itensProduto.iterator();
	    	boolean gerouProduto = false;
	    	while(itProduto.hasNext()){
	    		ItemEstruturaIett iettProduto = (ItemEstruturaIett) itProduto.next();
	    		if(iettProduto.getNivelIett().intValue() == nivelProduto && item.equals(iettProduto.getItemEstruturaIett())){
	    			boolean aux = geraXMLProduto(builder, iettProduto, acaoExcluida);
	    			gerouProduto = gerouProduto || aux;
	    			/*
	    			 * Foi necessário criar uma variavel, pois se fizer:
	    			 * gerouProduto = gerouProduto || geraXMLProduto() e gerouProduto for setada como true,
	    			 * nas próximas vezes o método que gera os produtos nem é executado.
	    			 */
	    			
	    		}
	    	}
	    	
	    	/*
	    	gerouAcao = gerouProduto;
	    	
	    	if(gerouAcao)
	    		this.incrementarTotalizadorEstrutura(item.getEstruturaEtt().getCodEtt(), "Quantidade de Ações Excluídas");
	    	*/
	    	builder.closeNode("itemAcao");
		}
		
		return gerouAcao;
    }
    
    /**
     * Verifica produtos Excluidos.<br>
     * 
     * @param acao
     * @author N/C
     * @since N/C
     * @version N/C
     * @return boolean
     */
    public boolean verificarProdutosExcluidos(ItemEstruturaIett acao){
    	List itensProduto = new ArrayList(todosItens);
    	Iterator itProduto = itensProduto.iterator();
    	while(itProduto.hasNext()){
    		ItemEstruturaIett produto = (ItemEstruturaIett) itProduto.next();
    		if(produto.getNivelIett().intValue() == nivelProduto && acao.equals(produto.getItemEstruturaIett())){
    			if(produto.getItemEstruturarevisaoIettrevs() != null && produto.getItemEstruturarevisaoIettrevs().size() > 0){
    				Iterator itRevisoesProduto = produto.getItemEstruturarevisaoIettrevs().iterator();
    				while(itRevisoesProduto.hasNext()){
    					ItemEstruturarevisaoIettrev revProduto = (ItemEstruturarevisaoIettrev) itRevisoesProduto.next();
    					if("E".equals(revProduto.getSituacaoIettrev())){
    						return true;
    					}
    				}
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * Gera tag Produto no XML.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param produtoAcaoExcluida 
     * @param item
     * @return boolean
     * @throws ECARException
     */
    
    public boolean geraXMLProduto(XmlBuilder builder, ItemEstruturaIett item, boolean produtoAcaoExcluida) throws ECARException{
    	boolean gerouProduto = false;
    	String nome = "";
    	String tipoSituacao = "";
    	String justificativa = ""; //Este campo só será usado no apendice3

		List revisoes = new ArrayList(item.getItemEstruturarevisaoIettrevs());
		Iterator itRevisoes = revisoes.iterator();
		while(itRevisoes.hasNext()){
			ItemEstruturarevisaoIettrev iettRev = (ItemEstruturarevisaoIettrev) itRevisoes.next();
			
			nome = "";
			if(iettRev.getDescricaoR3rev() != null && !"".equals(iettRev.getDescricaoR3rev()))
				nome = iettRev.getDescricaoR3rev();
			else
				nome = iettRev.getNomeIettrev();
			
			if("".equals(nome)){
				if(item.getDescricaoR3() != null && !"".equals(item.getDescricaoR3()))
					nome = item.getDescricaoR3(); //NomePPA
				else
					nome = item.getNomeIett();
			}
			
			/*
			 * Ref. mantis 6097: 
			 * 4) Quando uma exclusão aconteceu em nível de ação, apresentar apenas a
			 * justificativa da ação; a justificativa dos produtos ficará em branco.
			 */
			if(produtoAcaoExcluida)  
				justificativa = "";
			else
				justificativa = iettRev.getJustificativaIettrev();
			
			tipoSituacao = iettRev.getSituacaoIettrev();
			
			if(produtoAcaoExcluida)
				tipoSituacao = "E";
			
			if("E".equals(tipoSituacao)){
				geraXMLItemProduto(builder, item, nome, "", "", tipoSituacao, "", "", justificativa);
				gerouProduto = true;
			}
		}
		
		return gerouProduto;
    }

    /**
     * Gera item produto xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @param item
     * @param nome
     * @param tipo
     * @param unidade 
     * @param tipoSituacao
     * @param regiao
     * @param valor
     * @param justificativa
     */
    public void geraXMLItemProduto(XmlBuilder builder, ItemEstruturaIett item, String nome, 
    		String tipo, String unidade, String tipoSituacao, String regiao, String valor, String justificativa){
    	String situacao = "";
		if("E".equals(tipoSituacao)){
			situacao = "(Excluído)";
		}

    	this.incrementarTotalizadorEstrutura(item.getEstruturaEtt().getCodEtt(), "Quantidade de Produtos Excluídos");
		
    	builder.addClosedNode("produto", 
    			"nome=\"" + builder.normalize(nome) + "\" " +
    			"tipo=\"" + builder.normalize(tipo) + "\" " +
    			"unidade=\"" + builder.normalize(unidade) + "\" " +
    			"situacao=\"" + builder.normalize(situacao) + "\" " +
    			"tipoSituacao=\"" + builder.normalize(tipoSituacao) + "\" " +
    			"regiao=\"" + builder.normalize(regiao) + "\" " +
    			"valor=\"" + builder.normalize(valor) + "\" " +
    			"justificativa=\"" + builder.normalize(justificativa) + "\" " +
    			"tipoRelatorio=\"" + builder.normalize(tipoRelatorio) + "\"");
    }
    
    /**
     * Pega ultima revisão iett.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param listaRevisoes
     * @return ItemEstruturarevisaoIettrev 
     */
    public ItemEstruturarevisaoIettrev getUltimaRevisaoIett(Set listaRevisoes){
    	ItemEstruturarevisaoIettrev retorno = null;
    	if(listaRevisoes != null && listaRevisoes.size() > 0){
    		List revisoes = new ArrayList(listaRevisoes);
    		Collections.sort(revisoes,
    			new Comparator(){
					public int compare(Object o1, Object o2) {
						ItemEstruturarevisaoIettrev iett1 = (ItemEstruturarevisaoIettrev) o1;
						ItemEstruturarevisaoIettrev iett2 = (ItemEstruturarevisaoIettrev) o2;
						
						/*
						String data1 = Data.parseDate(iett1.getDataInclusaoIettrev());
						String data2 = Data.parseDate(iett2.getDataInclusaoIettrev());
						
						//dd/mm/yyyy -> yyyymmaa
						data1 = data1.substring(6) + data1.substring(3, 5) + data1.substring(0, 2);
						data2 = data2.substring(6) + data2.substring(3, 5) + data2.substring(0, 2);
						
						return data1.compareTo(data2);*/
						return iett1.getDataInclusaoIettrev().compareTo(iett2.getDataInclusaoIettrev());
					}	
    			}
    		);
    		ItemEstruturarevisaoIettrev ultimoIettRev = (ItemEstruturarevisaoIettrev) revisoes.get(revisoes.size() - 1);
    		retorno = ultimoIettRev;
    	}
    	
    	return retorno;
    }

    /**
     * Incrementa totalizador estrutura.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param Long idEstrutura
     * @param String nomeEstrutura
     */
    private void incrementarTotalizadorEstrutura(Long idEstrutura, String nomeEstrutura){
    	if(!idsEstrutura.contains(idEstrutura)){
    		TotalizadorRelatorios totalizador = new TotalizadorRelatorios();
    		totalizador.setId(idEstrutura);
    		totalizador.setEstrutura(nomeEstrutura);
    		totalizador.setTotal(1);
    		
    		idsEstrutura.add(idEstrutura);
    		totalizadorEstrutura.add(totalizador);
    	}
    	else {
    		Iterator itTotalizadores = totalizadorEstrutura.iterator();
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
     * Gera totalizador xml.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param builder
     * @throws ECARException
     */
    public void geraXMLTotalizador(XmlBuilder builder) throws ECARException{
        try{
        	int total = 0;
        	builder.addNode("totalizadores");        	
            Iterator itTotalizadores = totalizadorEstrutura.iterator();
            while(itTotalizadores.hasNext()){
            	TotalizadorRelatorios tr = (TotalizadorRelatorios) itTotalizadores.next();
            	String nome = tr.getEstrutura();
            	String valor = "" + tr.getTotal();
            	total += tr.getTotal();
            	builder.addClosedNode("totalizador",
            			"nome=\"" + builder.normalize(nome) + "\"" +
            			" valor=\"" + builder.normalize(valor) + "\"");
            }        	
        	builder.addClosedNode("total", 
        			"nome=\"Total:\"" +
        			" valor=\"" + builder.normalize(String.valueOf(total)) + "\"");
            builder.closeNode("totalizadores");
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: Totalizadores");
        }            
    }
    
    /**
     * Gera totalizador de valores xml.<br>
     * 
     * @param builder
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
    public void geraXMLTotalizadorValores(XmlBuilder builder) throws ECARException{
        try{
        	//int total = 0;
        	builder.addNode("totalizadorValores");

        	Iterator itTotalizadores = totalizadorValores.iterator();
            while(itTotalizadores.hasNext()){
            	FonteRecursosPPA tr = (FonteRecursosPPA) itTotalizadores.next();
            	String nome = tr.getLabel();
            	String valor = Util.formataMoeda(tr.getValor());
            	
            	//total += tr.getTotal();
            	builder.addClosedNode("totalizador",
            			"nome=\"" + builder.normalize(nome) + "\"" +
            			" valor=\"" + builder.normalize(valor) + "\"");
            }        	

            builder.addClosedNode("totalizador",
        			"nome=\"Total Geral dos Programas\"" +
        			" valor=\"" + builder.normalize(Util.formataMoeda(totalGeralProgramas)) + "\"");
        	
        	builder.addClosedNode("totalizador",
        			"nome=\"Total Geral das Ações\"" +
        			" valor=\"" + builder.normalize(Util.formataMoeda(totalGeralAcoes)) + "\"");

            builder.closeNode("totalizadorValores");
        } catch(Exception e){
        	this.logger.error(e);
            throw new ECARException("Erro na criação do relatório: TotalizadorValores");
        }            
    }
}
