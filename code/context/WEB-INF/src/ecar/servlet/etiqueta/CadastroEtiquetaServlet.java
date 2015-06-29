package ecar.servlet.etiqueta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import comum.util.EtiquetaUtils;

import ecar.dao.EtiquetaDao;
import ecar.dao.ItemEstruturaDao;
import ecar.login.SegurancaECAR;
import ecar.permissao.ValidaPermissao;
import ecar.pojo.EstruturaAtributoEttat;
import ecar.pojo.EstruturaEtt;
import ecar.pojo.Etiqueta;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.RelacionamentoIettEtiqueta;
import ecar.servico.EtiquetaService;
import gov.pr.celepar.sentinela.client.SentinelaLogin;

/**
 * Classe que verifica acesso do usuário e faz o encaminhamento de página. <br/>
 * Contém as regras para criação de vínculo de uma etiqueta a um item.
 * */
public class CadastroEtiquetaServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

	private static final long serialVersionUID = 1L;
	private EtiquetaDao etiquetaDao = new EtiquetaDao();
	private Logger logger = Logger.getLogger(this.getClass());
	
	private Integer codAba = 0;
	private Integer codIett = 0;
	private String ultimoIdLinhaDetalhado = "";
	private String titulo = "";
	private String acao = "";
	ArrayList<String> msg = new ArrayList<String>();
	
	private SegurancaECAR segurancaEcar = new SegurancaECAR();
	private SentinelaLogin sentinelaLogin  = new SentinelaLogin();
	private String sentinelaSecurityCode = "";
	
	private ItemEstruturaIett itemEstrutura = new ItemEstruturaIett();
	private ValidaPermissao validaPermissao = new ValidaPermissao();
	private Boolean permissaoAlterar = false;
	private Boolean permissaoExibirHistorico = false;
	private EstruturaEtt estrutura = new EstruturaEtt();
	private List<EstruturaAtributoEttat> atributos = new ArrayList<EstruturaAtributoEttat>();
	private List<RelacionamentoIettEtiqueta> listaRelEtiquetas = new ArrayList<RelacionamentoIettEtiqueta>();
	

	
	/**
	 * Construtor
	 * @since 02/04/2012
	 * */
	public CadastroEtiquetaServlet(){
		super();
	}
	
	
	/**
	 * Método que deve ser executado quando o usuário faz uma requisição HTML do tipo GET, incluindo AJAX. <br/>
	 * Faz as verificações de permissão do usuário e decide a apresentação de tela.
	 * @since 02/04/2012
	 * */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			EtiquetaService etiquetaService = new EtiquetaService();
			listaRelEtiquetas.clear();
			String url = "";
			msg.clear();
			
			/* Carregando informações do usuário */
			HttpSession session = request.getSession();
			setSegurancaEcar((SegurancaECAR) session.getAttribute("seguranca"));
			setSentinelaLogin((SentinelaLogin) session.getAttribute("SENTINELA_LOGIN"));
			setSentinelaSecurityCode(session.getAttribute("SENTINELA_SECURITY_CODE").toString());
			
			//HashSet<SisAtributoSatb> atributosUruario = (HashSet<SisAtributoSatb>) segurancaEcar.getGruposAcesso();
			
			request.setAttribute("permissaoAlterar", this.permissaoAlterar);
			
			/* Capturando parâmetros de requisição */
			String codiett = request.getParameter("codIett");
			String acao = request.getParameter("acao");
			String codRel = request.getParameter("codRel");
			ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(request) ;
			
			
			ItemEstruturaIett iett = new ItemEstruturaIett();
			RelacionamentoIettEtiqueta rel = new RelacionamentoIettEtiqueta();
			
			if (acao != null && acao.equalsIgnoreCase("apagarEtiqueta")) {
				rel = etiquetaService.recuperarRelacionamentoEtiquetaIettPorId(new Long(codRel));
				rel.setIndAtivo("N");
				etiquetaService.atualizarRelacionamentoEtiquetaIettPorId(rel);
			}
			
			if(codiett == null || codiett.isEmpty()){
				iett  = rel.getItemEstruturaIett();
				listaRelEtiquetas.addAll(etiquetaService.listarRelacionamentosIettEtiquetaById(iett.getCodIett()));
			}else{
				iett = itemEstruturaDao.getItemEtruturaById(new Long(codiett));
				listaRelEtiquetas.addAll(etiquetaService.listarRelacionamentosIettEtiquetaById(new Long(codiett)));
			}
			
			request.setAttribute("listaRelEtiquetas", listaRelEtiquetas);
			request.setAttribute("msgSistema", msg);
			
			/* Verifica permissão de acesso do usuário */
			if(usuarioComPermissao(getSegurancaEcar())){
				url = "cadastroEtiquetas.jsp"; 
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
			}else{
				url = "/sair.jsp?msgOperacao=Usuario nao autenticado!"; 
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
				
			}
		
		} catch (Exception e) {
			logger.fatal("Erro ao fazer requisicao GET. Exception capturada: " + e.getMessage());
		}
		
	}
	
	/**
	 * Método que deve ser executado quando o usuário faz uma requisição HTML do tipo POST.
	 * @since 02/04/2012
	 * */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EtiquetaService etiquetaService = new EtiquetaService();
		String url = "";
		msg.clear();
		try {
			String tags = request.getParameter("nomeEtiq");
			listaRelEtiquetas.clear();
			/* Capturando parâmetros de requisição */
			Long codAba = new Long (request.getParameter("codAba"));
			String ultimoIdLinhaDetalhado = request.getParameter("ultimoIdLinhaDetalhado");
			Long codIett = new Long (request.getParameter("codIett"));
			String acao = request.getParameter("acao");
			String idEtiqueta = request.getParameter("idEtiqueta");
			ItemEstruturaIett iett = new ItemEstruturaIett();
			iett.setCodIett(codIett);

			if (acao != null && acao.equalsIgnoreCase("apagarEtiqueta")) {
				RelacionamentoIettEtiqueta rel = new RelacionamentoIettEtiqueta();
				rel = etiquetaService.recuperarRelacionamentoEtiquetaIettPorId(new Long(idEtiqueta));
				
				ItemEstruturaDao itemEstruturaDao = new ItemEstruturaDao(null);
				
				//Recupera os itens filhos do item corrente(Resultado, Produto, Ação...) que contenham a mesma etiqueta que está sendo excluida
				List<Object[]> listaRelFilhos = itemEstruturaDao.listaRelacionamentoEtiquetaFilhos(iett.getCodIett(), rel.getEtiqueta().getCodigo());
				for(Object[] obj:listaRelFilhos){
					RelacionamentoIettEtiqueta relFilho = new RelacionamentoIettEtiqueta();
					relFilho = etiquetaService.recuperarRelacionamentoEtiquetaIettPorId(new Long(obj[5].toString()));
					relFilho.setIndAtivo("N");				
					etiquetaService.apagarRelacionamento(relFilho);					
				}
				
				//Recupera os itens pais do item corrente e verifica se este não possui outros filhos com o mesmo relacionamento para exclusão
				List<Object[]> listaRelPais = itemEstruturaDao.listaRelacionamentoEtiquetaPais(iett.getCodIett(), rel.getEtiqueta().getCodigo());
				for(Object[] obj:listaRelPais){
					
					List<Object[]> listaFilhosComMesmaEtiqueta = itemEstruturaDao.listaRelacionamentoEtiquetaFilhos(new Long(obj[0].toString()), rel.getEtiqueta().getCodigo());
					
					//Verifica se existe outros filhos com a mesma etiqueta
					if (listaFilhosComMesmaEtiqueta == null
							|| listaFilhosComMesmaEtiqueta.size() <= 1) {

						RelacionamentoIettEtiqueta relPai = new RelacionamentoIettEtiqueta();
						if (relPai != null) {
							relPai = etiquetaService
									.recuperarRelacionamentoEtiquetaIettPorId(new Long(
											obj[5].toString()));
							relPai.setIndAtivo("N");
							etiquetaService.apagarRelacionamento(relPai);
						}
					}
				}				
				
				
				//rel.setIndAtivo("N");				
				//etiquetaService.apagarRelacionamento(rel);				
			}else{
				msg = escreverEtiquetasEmItem(etiquetaService, tags, iett);
			}
															
			
			
			
			listaRelEtiquetas.addAll(etiquetaService.listarRelacionamentosIettEtiquetaById(codIett));
			
			/* Setando parâmetros da página */
			request.setAttribute("listaRelEtiquetas", listaRelEtiquetas);
			request.setAttribute("permissaoAlterar", this.permissaoAlterar);
			request.setAttribute("codAba", codAba);
			request.setAttribute("msgSistema", msg);
			request.setAttribute("ultimoIdLinhaDetalhado", ultimoIdLinhaDetalhado);
			
			/* Verifica permissão de acesso do usuário */
			if(usuarioComPermissao(getSegurancaEcar())){
				url = "cadastroEtiquetas.jsp?codAba=" + codAba + "&ultimoIdLinhaDetalhado=" + ultimoIdLinhaDetalhado + "&ultimoIdLinhaDetalhado"; 
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
			}else{
				url = "/sair.jsp?msgOperacao=Usuario nao autenticado!"; 
				RequestDispatcher dispatcher = request.getRequestDispatcher(url);
				dispatcher.forward(request, response);
				
			}
		
		} 
		catch (Exception e) {
			logger.fatal("Erro ao fazer requisição POST. Exception capturada: " + e.getMessage());
		}
	}
	
	
	/**
	 * Pesquisa coleção de etiquetas
	 * @param etiqueta Nome ou descrição do meta-dado. 
	 * */
	public Set<Etiqueta> pesquisaColecaoEtiquetas(String etiqueta){
		Set<Etiqueta> rs = new HashSet<Etiqueta>();
		try {
			rs = etiquetaDao.pesquisarListaEtiquetas(etiqueta);
		} catch (Exception e) {
			logger.fatal("Erro ao pesquisar etiquetas. Exception capturada: " + e.getMessage());
		}
		return rs;
	}
	
	/**
	 * Método que verifica se os compos do fomulário estão com seus campos com formatos certos e os obrigatórios preenchidos.
	 * @since 02/04/2012
	 * */
	@SuppressWarnings("unused")
	private Boolean validarFormulario(String campo){
		if(campo != null && !campo.isEmpty()){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * Verifica se o usuário tem acesso ao item acessado.
	 * */
	private Boolean usuarioComPermissao(SegurancaECAR seguranca){
		if (seguranca == null || !seguranca.isAutenticado()){
			return false;
		}
		return true;
	}
	
	
	/**
	 * Gera as etiquetas qua não existem no banco e cria o relacionamento com o ItemEtiquetaIett
	 * @return Mensagens da não criação do relacionamento, caso a etiqueta esteja inativa.
	 * */
	public ArrayList<String> escreverEtiquetasEmItem(EtiquetaService es, String textoEtiquetas, ItemEstruturaIett iett ){
		ArrayList<String> retorno = new ArrayList<String>();
		
		
		try {
			ArrayList<String> palavras = new ArrayList<String>();
			String particulas[] = textoEtiquetas.split(" ");
			for (String p : particulas) {
				if(!p.trim().equals("")) {
					palavras.add(p);
				}
			}
			palavras.add(textoEtiquetas);
			
			for (String palavra : palavras) {
				if (!EtiquetaUtils.verificarPreposicao(palavra.toUpperCase())) { // Não sendo preposição
					Etiqueta etiq = es.buscarEtiquetaPorNome(palavra.trim());
					if(etiq == null) // Não existindo a etiqueta
					{	
						/* Criando etiqueta */
						Etiqueta etiqueta = new Etiqueta();
						etiqueta.setNome(palavra.toUpperCase().trim());
						etiqueta.setNomeFonetico(EtiquetaUtils.fonetizar(palavra));
						etiqueta.setIndAtivo("S");
						etiqueta.setIndPrioritario("N");
						es.inserirEtiqueta(etiqueta);
						
						/* Criando relação */
						es.criarVinculoEtiquetaItem(etiqueta, iett);
					}
					else if(etiq != null && etiq.getIndAtivo().equalsIgnoreCase("N")) // Existindo a etiqueta inativa
					{
						retorno.add("A etiqueta " + palavra + " n&atilde;o pode ser utilizada. ");
					}
					else if (etiq != null && etiq.getIndAtivo().equalsIgnoreCase("S")) // Existindo a etiqueta ativa
					{
						RelacionamentoIettEtiqueta rela = es.recuperarRelacionamentoEtiqueta(iett.getCodIett(), etiq.getCodigo());
						
						if(rela != null){ // Existindo relacionamento
							rela.setIndAtivo("S");
							es.atualizarRelacionamentoEtiquetaIettPorId(rela);
						}else{ // Não existindo relacionamento.
							es.criarVinculoEtiquetaItem(etiq, iett);
						}
					}
				}
			}
			
		}catch (Exception e) {
			retorno.add("Ocorreu algum erro inesperado ao vincular as etiquetas ao item");
			logger.fatal("Erro ao criar relacao entre ItemEstruturaIett e Etiqueta. Exception capturada: " + e.getMessage());
		}
		return retorno;
		
	}
	
	
	
	
	/*
	 * GETTERS E SETTERS
	 * */

	public Integer getCodAba() {
		return codAba;
	}

	public void setCodAba(Integer codAba) {
		this.codAba = codAba;
	}

	public Integer getCodIett() {
		return codIett;
	}

	public void setCodIett(Integer codIett) {
		this.codIett = codIett;
	}

	public String getUltimoIdLinhaDetalhado() {
		return ultimoIdLinhaDetalhado;
	}

	public void setUltimoIdLinhaDetalhado(String ultimoIdLinhaDetalhado) {
		this.ultimoIdLinhaDetalhado = ultimoIdLinhaDetalhado;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public SegurancaECAR getSegurancaEcar() {
		return segurancaEcar;
	}

	private void setSegurancaEcar(SegurancaECAR segurancaEcar) {
		this.segurancaEcar = segurancaEcar;
	}

	public SentinelaLogin getSentinelaLogin() {
		return sentinelaLogin;
	}

	private void setSentinelaLogin(SentinelaLogin sentinelaLogin) {
		this.sentinelaLogin = sentinelaLogin;
	}

	public String getSentinelaSecurityCode() {
		return sentinelaSecurityCode;
	}

	private void setSentinelaSecurityCode(String sentinelaSecurityCode) {
		this.sentinelaSecurityCode = sentinelaSecurityCode;
	}

	public EtiquetaDao getEtiquetaDao() {
		return etiquetaDao;
	}

	public void setEtiquetaDao(EtiquetaDao etiquetaDao) {
		this.etiquetaDao = etiquetaDao;
	}

	public ItemEstruturaIett getItemEstrutura() {
		return itemEstrutura;
	}

	public void setItemEstrutura(ItemEstruturaIett itemEstrutura) {
		this.itemEstrutura = itemEstrutura;
	}

	public ValidaPermissao getValidaPermissao() {
		return validaPermissao;
	}

	public void setValidaPermissao(ValidaPermissao validaPermissao) {
		this.validaPermissao = validaPermissao;
	}

	public Boolean getPermissaoAlterar() {
		return permissaoAlterar;
	}

	public void setPermissaoAlterar(Boolean permissaoAlterar) {
		this.permissaoAlterar = permissaoAlterar;
	}

	public Boolean isPermissaoExibirHistorico() {
		return permissaoExibirHistorico;
	}

	public void setPermissaoExibirHistorico(Boolean permissaoExibirHistorico) {
		this.permissaoExibirHistorico = permissaoExibirHistorico;
	}

	public EstruturaEtt getEstrutura() {
		return estrutura;
	}

	public void setEstrutura(EstruturaEtt estrutura) {
		this.estrutura = estrutura;
	}

	public List<EstruturaAtributoEttat> getAtributos() {
		return atributos;
	}


	public void setAcao(String acao) {
		this.acao = acao;
	}


	public String getAcao() {
		return acao;
	}

}