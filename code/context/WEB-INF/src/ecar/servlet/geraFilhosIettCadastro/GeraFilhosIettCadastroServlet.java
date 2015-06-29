package ecar.servlet.geraFilhosIettCadastro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import comum.util.Pagina;

import ecar.dao.EstruturaDao;
import ecar.dao.ItemEstruturaDao;
import ecar.exception.ECARException;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.ItemEstruturaIett;

/**
 *
 * @author 70744416353
 */
public class GeraFilhosIettCadastroServlet extends HttpServlet{
	
	/**
	 * 		As classes java, GeraFilhosIettCadastroServlet e ItemEstruturaCadastroHtml, junto com o javascript 
	 *  listaItensCadastro.js formam a base da implementação de Ajax na listagem de itens do cadastro.
	 *  
	 *  GeraFilhosIettCadastroServlet -> Servlet responsável por receber, processar e retornar os dados para o 
	 *  javascript listaItensCadastro.js. Os dados são retornados no formato JSON.  
	 * 							
	 */	
	private static final long serialVersionUID = -6154650334660068552L;
	private Logger logger = Logger.getLogger(this.getClass());
			
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("request :: GET:: Classe:: " + this.getClass().getName() );
		execute(request,response);
	}
	
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("request :: POST:: Classe:: " + this.getClass().getName() );
		execute(request,response);				
	}
		
	private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {			
		String responseHTML = "";		
		
		try{	
			responseHTML =  createStringJSON(request);
			responseToHTML(response, responseHTML);
		}catch(Exception e){
			e.printStackTrace(System.out);
			logger.error(e);			
			responseHTML = e.getMessage();
			responseToHTML(response, responseHTML);
		}
	}
	
	/**
	 * Cria um objeto JSON, que encapsula os dados dos itens a serem exibidos na tela de cadastro.
	 * @param request
	 * @return
	 * @throws ECARException
	 */
	private String createStringJSON(HttpServletRequest request) throws ECARException{
		String stringJSON = "";								

		String codIett = "";
		String codEtt = "";
		String codEttPai = "";
		ItemEstruturaIett itemEstruturaSelecionado = null;
		EstruturaEtt estruturaSelecionada = null;
		ItemEstruturaCadastroHtml itemEstruturaCadastroHtml = null;
		EstruturaEtt estruturaPai = null; 
		String tipoItemClicado = "";
		ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
		EstruturaDao estruturaDao = new EstruturaDao(request);		
				
		String idLinha = request.getParameter("idLinha");
		if(idLinha.startsWith("ett")){
			tipoItemClicado = "estrutura";
			codEtt = idLinha.substring(3, idLinha.indexOf("_pai_"));
			codIett = idLinha.substring(idLinha.indexOf("iett")+4, idLinha.length());
			
			//se for uma chamada a uma estrutura "filha" de uma estrutura virtual (uso pratico apenas na montagem da arvore)
			if(codIett.contains("ett")) {
				codEttPai = idLinha.substring(idLinha.indexOf("_pai_ett") + 8, idLinha.indexOf("_avo"));
				codIett = idLinha.substring(idLinha.lastIndexOf("_avo_")+5, idLinha.length());
			}
		}
		else if(idLinha.startsWith("iett")){
			tipoItemClicado = "itemEstrutura";
			codIett = idLinha.substring(4, idLinha.indexOf("_pai_"));
			codEtt = idLinha.substring(idLinha.lastIndexOf("ett")+3, idLinha.length());
		}		
		
		//procura a estrutura se ele existir
		if(!codEtt.equals("")) {
			estruturaSelecionada = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, new Long(codEtt));
		}
			
		//procura o item se ele existir
		if(!codIett.equals("")) {
			itemEstruturaSelecionado = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, new Long(codIett));			
		}		
		
		if (Pagina.getParamStr(request, "hidFuncaoAjaxSelecionada").equals("detalharArvoreItens")){
			if (itemEstruturaSelecionado != null){
				
				
				//guarda se a estrutura pode ser vista ou nao 
				boolean estruturaPodeSerVista = false;
				//recupera a lista de estruturas do proximo nivel
				List estruturasProximoNivel = estruturaDao.getEstruturas(itemEstruturaSelecionado.getEstruturaEtt());	
				
				
				if (estruturasProximoNivel != null && estruturasProximoNivel.size() > 0){
				
					if (!estruturasProximoNivel.contains(estruturaSelecionada) ){
						Iterator itEstruturasProximoNivel = estruturasProximoNivel.iterator();
						//vai escolher uma estrutura do proximo nivel que pode ser vista
						while(!estruturaPodeSerVista && itEstruturasProximoNivel.hasNext()) {
							estruturaSelecionada = (EstruturaEtt) itEstruturasProximoNivel.next();
							if(estruturaDao.verificarExibeEstrutura(estruturaSelecionada, itemEstruturaSelecionado)) {
								estruturaPodeSerVista = true;
								break;
							}
						}
					}
				}
			}
		}
		
		//se for uma estrutura e ela for virtual
		if(tipoItemClicado.equals("estrutura") && estruturaSelecionada.isVirtual()) {
			tipoItemClicado = "estruturaVirtual";
			codEtt = idLinha.substring(idLinha.lastIndexOf("ett")+3, idLinha.length());
		}
		
		
		//Caso o item clicado seja do tipo Estrutura raiz, o itemEstruturaSelecionado será null
		if(codEttPai.equals("")) {
			itemEstruturaCadastroHtml = 
				new ItemEstruturaCadastroHtml (itemEstruturaSelecionado, estruturaSelecionada, request);		
		
		} else {
			
			//se for uma chamada a uma estrutura "filha" de uma estrutura virtual (uso pratico apenas na montagem da arvore)
			estruturaPai = (EstruturaEtt) estruturaDao.buscar(EstruturaEtt.class, new Long(codEttPai));
			itemEstruturaCadastroHtml = 
				new ItemEstruturaCadastroHtml (itemEstruturaSelecionado, estruturaSelecionada, request, estruturaPai);
		}
			
		/*
		 * A "funcaoAjaxSelecionada" guarda a informação de qual objeto JSON retornar.
		 * "ehTelaListagem" indica se a árvore é carregada na tela de listagem ou no detalhamento
		 * Para tanto, foi utilizado o objeto ItemEstruturaCadastroHtml para gerar o
		 * objeto JSON.    
		 */		
		stringJSON = itemEstruturaCadastroHtml.geraObjetoJSON(tipoItemClicado, Pagina.getParamStr(request, "hidFuncaoAjaxSelecionada"), Pagina.getParamStr(request, "ehTelaListagem"));
		
		return stringJSON;
	}
		
	private void responseToHTML ( HttpServletResponse response, String conteudo ) throws IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.append(conteudo);
		writer.flush();
		writer.close();
	}
		
}
