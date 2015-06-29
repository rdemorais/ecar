/*
 * Created on 11/07/2005
 *
 */
package ecar.portal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.SentinelaUtil;
import comum.util.Mensagem;

import ecar.dao.PaginaAreaSiteDao;
import ecar.exception.ECARException;
import ecar.pojo.PaginaAreaSitePa;
import ecar.portal.bean.MenuTopoBean;
import ecar.portal.bean.PortalBean;
import gov.pr.celepar.sentinela.comunicacao.SentinelaParam;

/**
 * Classe que disponibiliza métodos que retornam informações para serem exibidas nas telas do portal.<br>
 * @author cristiano
 */

public class Portal {
	
	protected PortalBean portalBean = new PortalBean();
	private HttpServletRequest request;
	private Mensagem msg;
	
	private static final String IND_CAPA_SIM = "S"; 

	// DAOs acessados pela classe
	private PaginaAreaSiteDao paginaAreaSiteDao = null;
    
    /**
     * Construtor.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param HttpServletRequest request
     * @throws ECARException
     */
	public Portal(HttpServletRequest request) throws ECARException {
		this.request = request;
		this.msg = new Mensagem(this.getRequest().getSession().getServletContext());
		this.paginaAreaSiteDao = new PaginaAreaSiteDao(null);
		
		this.carregarMenuOpcoesTopo();
	}

    
    /**
     * Método para popular o PortalBean com as opções do menu topo.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
	private void carregarMenuOpcoesTopo() throws ECARException {
		//List menuHorizontal = (List)getRequest().getSession().getAttribute("menuHorizontal");
		
		//if(menuHorizontal == null) {
			List linksFuncoesUsuarioSentinela = new ArrayList();

        	// foi necessário adicionar a linha abaixo para não perder informações do sentinela
	        SentinelaUtil sentinelaUtil = new SentinelaUtil(this.request); 
	        SentinelaParam[] menuSentinela;
	        
	        /******** Tratamento funções menu *******/
	        
	        menuSentinela = sentinelaUtil.getSentinelaInterface().getMenu();
	        
			if(menuSentinela != null) {
				String ignorarApos = this.getRequest().getContextPath() + "/";
				for(int i = 0; i < menuSentinela.length; i++) {
					
					if(menuSentinela[i].getParamAux()[0] != null && !"".equals(menuSentinela[i].getParamAux()[0])) {
						int indice = menuSentinela[i].getParamAux()[0].indexOf(ignorarApos);
						
						String linkAposContexto = menuSentinela[i].getParamAux()[0].substring(indice + ignorarApos.length());
						
						linksFuncoesUsuarioSentinela.add(linkAposContexto);
					}
				}
				
			}
			
			/***************************************/
			
	        /******** Tratamento funções Genéricas *******/
	        
			menuSentinela = sentinelaUtil.getSentinelaInterface().getFuncoesGenericas();
	        
			if(menuSentinela != null) {
				String ignorarApos = this.getRequest().getContextPath() + "/";
				for(int i = 0; i < menuSentinela.length; i++) {
					
					if(menuSentinela[i].getParamAux()[0] != null && !"".equals(menuSentinela[i].getParamAux()[0])) {
						
						String linkAposContexto = menuSentinela[i].getParamAux()[0].substring(1);
						
						linksFuncoesUsuarioSentinela.add(linkAposContexto);
					}
				}
				
			}
			
			/***************************************/			

			List listMenu = this.getPaginaAreaSiteDao().getPaginaAreaSitePa(IND_CAPA_SIM);
			
			ArrayList menu = new ArrayList();
			
			//Retirado o Item de menu 'INICIAL' para o link ser colocado no logotipo do eCar
			//Alteração solicitada por Beier em Recife nas reuniões dos dias 29 e 30/04/2010
			MenuTopoBean menuTopoBean; // = new MenuTopoBean();
			
			
			// Menu INICIAL: Não está no banco de dados
//			menuTopoBean.setTitulo("INICIAL");
//			menuTopoBean.setDescricao("Retorna a tela inicial");
//			if (this.getRequest().getSession().getAttribute("seguranca") != null){
//				menuTopoBean.setLink(this.getRequest().getContextPath() + "/" + ((SegurancaECAR)this.getRequest().getSession().getAttribute("seguranca")).getPaginaInicialUsuario());
//			}
			
//			menu.add(menuTopoBean);
	
			String contexto = this.getRequest().getContextPath() + "/";
			Iterator it = listMenu.iterator();
			while(it.hasNext()) {
				PaginaAreaSitePa p = (PaginaAreaSitePa)it.next();
				
				if(linksFuncoesUsuarioSentinela.contains(p.getUrlPas())){
					
					menuTopoBean = new MenuTopoBean();
					
					menuTopoBean.setTitulo(p.getNomePas());
					menuTopoBean.setDescricao(p.getTextoPas());
					menuTopoBean.setLink(contexto + p.getUrlPas());
					menuTopoBean.setTituloPagina(p.getTituloPas());
					
					menu.add(menuTopoBean);
				}
			}
			
			this.portalBean.setMenuTopoBean(menu);
			
			getRequest().getSession().setAttribute("menuHorizontal", menu);
		//}
		//else {
		//	this.portalBean.setMenuTopoBean(menuHorizontal);
		//}
	}

    /**
     * Método para retornar o bean contendo as informações para a tela.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return PortalBean
     */
	public PortalBean getPortalBean() {
		return portalBean;
	}

    /**
     * Método que retorna o HttpServletRequest.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return HttpServletRequest
     */
	protected HttpServletRequest getRequest() {
		return request;
	}

    /**
     * Método que retorna a instância de Mensagem.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return Mensagem
     */
	protected Mensagem getMensagem() {
		return msg;
	}
    
    /**
     * Método para retornar o DAO da tabela PaginaAreaSite.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return PaginaAreaSiteDao
     */
	protected PaginaAreaSiteDao getPaginaAreaSiteDao() {
		return paginaAreaSiteDao;
	}
}
