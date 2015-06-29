/*
 * Criado em 01/12/2007
 *
 */
package ecar.taglib.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import comum.util.Pagina;
import comum.util.Util;

import ecar.dao.LinkDao;
import ecar.dao.PesquisaGrpAcessoDao;
import ecar.dao.TipoAcompGrpAcessoDao;
import ecar.dao.TipoAcompanhamentoDao;
import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.Link;
import ecar.pojo.PesquisaGrpAcesso;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.TipoAcompGrpAcesso;
import ecar.pojo.TipoAcompanhamentoTa;
import ecar.pojo.UsuarioUsu;
import ecar.util.Dominios;

/**
 * @author jose andre
 *
 */
public class BarraLinksTag extends TagSupport{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4043310305013938101L;

	//Página que chamou a taglib.
	//Se ela for a página de monitoramento(true), é verificada a permissão;
	//senão (false) imprime todos os links
	private Boolean verificaMonitoramento;
	private String _pathEcar;
	private String pathRaizUpload;
        /**
         *
         */
        protected HttpServletRequest request;
	    
    /**
     * Inicializa a montagem da tag para ser adicionada na tela de HTML.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
     */
    @Override
    public int doStartTag() throws JspException {
    	
        JspWriter writer = this.pageContext.getOut();
        try {
        	
            StringBuffer s = new StringBuffer();
			
            pathRaizUpload = new ecar.dao.ConfiguracaoDao(null).getConfiguracao().getRaizUpload();
        	LinkDao linkDao = new LinkDao(null);
        	List links = linkDao.listar(Link.class, new String[]{"ordemLink", "asc"});
        	Iterator itLinks = links.iterator();
        	Link link = new Link();
			
        	PesquisaGrpAcesso configPesquisaGrpAcesso = new PesquisaGrpAcesso();
			PesquisaGrpAcessoDao pesqGrpAcessoDao = new PesquisaGrpAcessoDao();
			
			boolean permiteGerarArquivoGrp = false;
			boolean geraArquivo = false;
			
			if(request!=null){
				SegurancaECAR seguranca = (SegurancaECAR)this.request.getSession().getAttribute("seguranca");
				configPesquisaGrpAcesso = pesqGrpAcessoDao.getConfiguracaoPesquisaGrupoAcesso(seguranca.getGruposAcesso());

				if(request.getParameter("codTipoAcompanhamento") != null && !request.getParameter("codTipoAcompanhamento").equals("")) {
					TipoAcompanhamentoDao taDao = new TipoAcompanhamentoDao(request);
					TipoAcompanhamentoTa tipoAcompanhamento = (TipoAcompanhamentoTa)taDao.buscar(TipoAcompanhamentoTa.class, Long.valueOf(request.getParameter("codTipoAcompanhamento")));
					Iterator<SisAtributoSatb> gruposAcessoSatbIt = seguranca.getGruposAcesso().iterator();
	
					while(gruposAcessoSatbIt.hasNext()) {
			    		SisAtributoSatb grupoPermissaoAcessoSatb = (SisAtributoSatb) gruposAcessoSatbIt.next();
			    		TipoAcompGrpAcessoDao tipoAcompGrpAcessoDao = new TipoAcompGrpAcessoDao();
			    		TipoAcompGrpAcesso tipoAcompGrpAcesso = tipoAcompGrpAcessoDao.getTipoAcompGrpAcesso(grupoPermissaoAcessoSatb, tipoAcompanhamento);
			   			if(tipoAcompGrpAcesso != null && tipoAcompGrpAcesso.getIndGerarArquivo().equals(Dominios.SIM)) {
			   				permiteGerarArquivoGrp = true;
			   				break;
			    		}
			    	}
					if(tipoAcompanhamento != null && tipoAcompanhamento.getIndGerarArquivoTa().equals(Dominios.SIM) && permiteGerarArquivoGrp) {
						geraArquivo = true;
					}
				}
			}
        	
        	if(verificaMonitoramento){
        		while(itLinks.hasNext()){
        			link = (Link) itLinks.next();
        			if(link.getExibeMonitoramentoLink()!=null && link.getExibeMonitoramentoLink().equals(Pagina.SIM)){
        				if(link.getNomeLink().equals("GRAVAR_SELECAO_DE_FILTRO")){
        					if(configPesquisaGrpAcesso.getIndPodeCriarPesquisaSistema().equals("S") || configPesquisaGrpAcesso.getIndPodeCriarPesquisaUsuario().equals("S")){
        						criaLink(link,s);	
        					}
        				} else if(link.getNomeLink().equals("GERAR_ARQUIVO")) {
       						if(geraArquivo)
       							criaLink(link,s);
        				} else {
        					criaLink(link,s);
        				}
        			}        			
        		}
        	} else{
        		while(itLinks.hasNext()){
        			link = (Link) itLinks.next();        			
        			criaLink(link,s);        			        			
        		}
        	}
                      
            writer.print(s.toString());
        } catch (Exception e) {
        	org.apache.log4j.Logger.getLogger(this.getClass()).error(e);
        	e.printStackTrace();
        }
        return Tag.SKIP_BODY;
    }
    
    /**
     * Cria Aba.<br>
     * 
     * @author José André
	 * @since N/C
	 * @version N/C
	 * @param Link link que deve ser impresso
     * @throws ECARException
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    private void criaLink(Link link, StringBuffer s) throws ECARException, NoSuchAlgorithmException, UnsupportedEncodingException{
   	    				
    	String hashNomeArquivo = null;
		UsuarioUsu usuarioImagem = null;
		
		if (link.getIconeLink() != null){
		
		hashNomeArquivo = Util.calcularHashNomeArquivo(pathRaizUpload, link.getIconeLink());
		usuarioImagem = ((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario();  
		Util.adicionarMapArquivosAtuaisUsuarios(usuarioImagem, hashNomeArquivo, pathRaizUpload, link.getIconeLink());

		s.append("<a href=\""+link.getUrlLink()+"\"");
		s.append("title=\""+link.getLabelLink()+"\"><img ");
		s.append("src=\""+_pathEcar+"/DownloadFile?tipo=open&RemoteFile=" + hashNomeArquivo+"\" border=\"0\" alt=\"\">" +
				link.getLabelLink()+"</a> &nbsp&nbsp");
		}
		else{
			s.append("<a href=\""+link.getUrlLink()+"\"");
			s.append("title=\""+link.getLabelLink()+"\">" +
				link.getLabelLink()+"</a> &nbsp&nbsp");
		}
		
//		s.append("src=\""+_pathEcar+"/DownloadFile?tipo=open&RemoteFile=" + pathRaizUpload + link.getIconeLink() +"\" border=\"0\" alt=\"\">" +
//				link.getLabelLink()+"</a> &nbsp&nbsp");	
    }

    /**
     * Encerra Tag.<br>
     * 
     * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws JspException
     */
    @Override
    public int doEndTag() throws JspException {
        /* processa o restante da página jsp */
        return Tag.EVAL_PAGE;
    }

    /**
     *
     * @return
     */
    public Boolean getVerificaMonitoramento() {
		return verificaMonitoramento;
	}

        /**
         *
         * @param verificaMonitoramento
         */
        public void setVerificaMonitoramento(Boolean verificaMonitoramento) {
		this.verificaMonitoramento = verificaMonitoramento;
	}

        /**
         *
         * @return
         */
        public String get_pathEcar() {
		return _pathEcar;
	}

        /**
         *
         * @param ecar
         */
        public void set_pathEcar(String ecar) {
		_pathEcar = ecar;
	}

        /**
         *
         * @return
         */
        public HttpServletRequest getRequest() {
		return request;
	}

        /**
         *
         * @param request
         */
        public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
    

	
	
	
}