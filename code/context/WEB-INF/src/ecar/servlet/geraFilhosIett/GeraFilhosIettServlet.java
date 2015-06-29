package ecar.servlet.geraFilhosIett;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ecar.bean.AtributoEstruturaListagemItens;
import ecar.dao.AcompReferenciaItemDao;
import ecar.dao.ItemEstruturaDao;
import ecar.dao.TipoFuncAcompDao;
import ecar.dao.UsuarioDao;
import ecar.pojo.AcompReferenciaAref;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.StatusRelatorioSrl;
import ecar.pojo.UsuarioUsu;

/**
 *
 * @author 70744416353
 */
public class GeraFilhosIettServlet extends HttpServlet{

	/**
	 * 		As classes java, GeraFilhosIettServlet e ItemEstruturaHtml, junto com o javascript listaItens  
	 * 	formam a base da implementação de Ajax na listagem de itens em monitoramento. Cada uma delas possuem
	 *  funções e particularidades abaixo descritas.
	 *  
	 *  GeraFilhosIettServlet -> Servlet responsável por receber, processar e retornar os dados  
	 * 								.......     .............
	 */
	private static final long serialVersionUID = -8290318086971631602L;
	private Logger logger = Logger.getLogger(this.getClass());
	//itemEstrutura é o nó central
	private ItemEstruturaIett itemEstrutura;
	private List tpfaOrdenadosPorEstrutura;
	private StatusRelatorioSrl statusLiberado;	
	private String status;
	private String idPagina;
	private String pathEcar;
	private String caminhoUrl;
	private String codTipoAcompanhamento;
	private String exigeLiberarAcompanhamento;
	private String codAref;
	private String codArefSelecionado;
	private String enderecoAbaRegistro;
	private String periodo;
	private String nomeAbaVisualizacao;
	private boolean temPermissaAcessarAba;
	private boolean permissaoAdministradorAcompanhamento;
	private List<AtributoEstruturaListagemItens> colecaoItens;
	private Collection<AcompReferenciaAref> periodosConsiderados;
	private UsuarioUsu usuario;
	private HttpServletRequest request;

		
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("request :: GET:: Classe:: " + this.getClass().getName() );
		execute(request,response);
	}
	
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("request :: POST:: Classe:: " + this.getClass().getName() );
		execute(request,response);				
	}

		
	private void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseHTML = "";		
		try{						
			setaValoresIniciais(request);
						
			ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request); 
			List<AtributoEstruturaListagemItens> descendentesAtEstListagem = itemEstruturaDao.recuperaDescendentesImediatos(itemEstrutura, colecaoItens);
			ItemEstruturaIett itemFilho = null;
			
			//Aqui começa a imprimir os filhos do item 
			if(descendentesAtEstListagem != null && descendentesAtEstListagem.size() > 0) {
				int quantidadeFilhosExibir = descendentesAtEstListagem.size();		
				Iterator descendentesAtEstListagemIt = descendentesAtEstListagem.iterator();
				tpfaOrdenadosPorEstrutura = new TipoFuncAcompDao().getFuncaoAcompOrderByEstruturasAtivasInativas();

				int contadorFilhos = 1;
				while(descendentesAtEstListagemIt.hasNext()){					
					AtributoEstruturaListagemItens atEstListagem = (AtributoEstruturaListagemItens)descendentesAtEstListagemIt.next();
					itemFilho =  (ItemEstruturaIett) itemEstruturaDao.buscar(ItemEstruturaIett.class, atEstListagem.getItem().getCodIett());
					
					boolean itemEstruturaPossuiFilho = itemEstruturaDao.existeDescendentesImediatos(itemFilho, colecaoItens);
					
					//buscar o codAref correto 
					
					
					
					
					ItemEstruturaHtml itemEstruturaHtml = new ItemEstruturaHtml(itemFilho,atEstListagem,
							usuario,statusLiberado,periodosConsiderados,  tpfaOrdenadosPorEstrutura,
							status,idPagina,pathEcar,caminhoUrl,codTipoAcompanhamento,exigeLiberarAcompanhamento,
							codAref, codArefSelecionado, request,permissaoAdministradorAcompanhamento,itemEstruturaPossuiFilho, enderecoAbaRegistro, periodo, 
							temPermissaAcessarAba, nomeAbaVisualizacao);
					
					responseHTML += itemEstruturaHtml.geraHtmlDoItem();
					
					//Se existir mais de um filho, separa-os por "*@*", marcador utilizado na apresentação
//					if(contadorFilhos != quantidadeFilhosExibir){						
//						responseHTML += "*@*";
//					}							 

					contadorFilhos++;
				}								
				
			}
						
			//Seta o comprimento dos dados dos filhos
			int numeroColunasPorFilho = 0;
			
			if(status.equals("true")){
				// se for relatorio vai ter mais uma coluna com o checkbox
				numeroColunasPorFilho = 8 + periodosConsiderados.size();
			} else {
				numeroColunasPorFilho = 7 + periodosConsiderados.size();
			}
			responseHTML = numeroColunasPorFilho + "*@*" + (itemFilho!=null?itemFilho.getEstruturaEtt().getCodCor3Ett():"") + responseHTML;
			responseToHTML(response, responseHTML);
		}catch(Exception e){
			e.printStackTrace(System.out);
			logger.error(e);			
			responseHTML = "<tr>Nenhum registro encontrado</tr>";
			responseToHTML(response, responseHTML);
		}
	}

		
	private void responseToHTML ( HttpServletResponse response, String conteudo ) throws IOException {
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		writer.append(conteudo);
		writer.flush();
		writer.close();
	}
	
	private void setaValoresIniciais(HttpServletRequest request) throws Exception{
		//TipoFuncAcompDao tipoFuncAcompDao = new TipoFuncAcompDao();
    	UsuarioDao usuDao = new UsuarioDao(request);  
    	ItemEstruturaDao itemDao = new ItemEstruturaDao(request);
    	
    	
        //this.tpfaOrdenadosPorEstrutura = tipoFuncAcompDao.getFuncaoAcompOrderByEstruturas(); 
    	this.statusLiberado = (StatusRelatorioSrl) itemDao.
        										buscar(StatusRelatorioSrl.class,
        										Long.valueOf(AcompReferenciaItemDao.STATUS_LIBERADO));    	
    	this.usuario = (UsuarioUsu) usuDao.buscar(UsuarioUsu.class, new Long(request.getParameter("codUsuarioLogado")));    	    	
    	this.periodosConsiderados = (Collection) request.getSession().getAttribute("periodosConsideradosAgrupados");
    	if(!request.getParameter("codOrg").equals(""))
    		this.colecaoItens = (List) request.getSession().getAttribute("colecaoItens_org" + request.getParameter("codOrg"));
    	else 
    		this.colecaoItens = (List) request.getSession().getAttribute("colecaoItens");
    	this.status = request.getParameter("status");
    	this.pathEcar = request.getParameter("pathEcar");
    	this.idPagina = request.getParameter("idPagina");
    	this.caminhoUrl = request.getParameter("caminhoUrl");
    	this.codTipoAcompanhamento = request.getParameter("codTipoAcompanhamento");
    	this.exigeLiberarAcompanhamento = request.getParameter("exigeLiberarAcompanhamento");
    	this.permissaoAdministradorAcompanhamento = new Boolean(request.getParameter("permissaoAdministradorAcompanhamento")).booleanValue();
    	this.codAref = request.getParameter("codAref");
    	this.codArefSelecionado = request.getParameter("codArefSelecionado");
    	this.itemEstrutura = (ItemEstruturaIett) itemDao.buscar(ItemEstruturaIett.class, new Long(request.getParameter("codIett")));
    	this.enderecoAbaRegistro = request.getParameter("endereco");
    	this.periodo = request.getParameter("periodo");
    	this.temPermissaAcessarAba = new Boolean(request.getParameter("abaConfigurada")).booleanValue(); 
    	this.nomeAbaVisualizacao = request.getParameter("nomeAbaVisualizacao");
    	    	
	}
	
}
