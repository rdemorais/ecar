/*
 * Created on 11/07/2005
 *
 */
package ecar.portal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.util.Data;
import comum.util.Util;

import ecar.dao.AgendaOcorrenciaDao;
import ecar.dao.DestaqueSubAreaDao;
import ecar.dao.SegmentoItemDao;
import ecar.exception.ECARException;
import ecar.pojo.AgendaOcorrenciaAgeo;
import ecar.pojo.DestaqueSubAreaDtqsa;
import ecar.pojo.SegmentoItemLeiauteSgtil;
import ecar.pojo.SegmentoItemSgti;
import ecar.portal.bean.InformacaoBean;
import ecar.portal.bean.SecaoBean;

/**
 * Classe que carrega informações para serem exibidas na tela principal do portal.<br>
 * @author cristiano
 */

public class CapaPrincipal extends Portal {
	
	private static final String ID_SECAO_SUPER_DESTAQUE = "SECAO_SUPER_DESTAQUE"; 
	private static final String ID_SECAO_MAIS_ARTIGOS = "SECAO_MAIS_ARTIGOS"; 
	private static final String ID_SECAO_AGENDA = "SECAO_AGENDA"; 
	private static final String ID_SECAO_TAXACAO_CLIP = "SECAO_TAXACAO_CLIP"; 
	private static final String ID_SECAO_SUB_AREA_DESTAQUE = "SECAO_SUB_AREA_DESTAQUE"; 

	// DAOs acessados pela classe
	private SegmentoItemDao segmentoItemDao = null;
	private AgendaOcorrenciaDao agendaOcorrenciaDao = null;

	/**
     * Construtor.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param HttpServletRequest request
     * @throws ECARException
     */
	public CapaPrincipal(HttpServletRequest request) throws ECARException {
		super(request);

		this.segmentoItemDao = new SegmentoItemDao(null);
		
		this.carregarMenuNavegacao();
		this.carregarSuperDestaqueArtigos();
		this.carregarMaisArtigos();
		this.carregarAgenda();
		this.carregarTaxacaoClip();
		this.carregarAreaSubAreaDestaque();
	}
    
    
    /**
     * Método para retornar o DAO da tabela SegmentoItem.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return SegmentoItemDao
     */
	private SegmentoItemDao getSegmentoItemDao() {
		return segmentoItemDao;
	}

	/**
     * Método para popular a seção de MENU DE NAVEGAÇÃO do PortalBean.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
	private void carregarMenuNavegacao() throws ECARException {
		// falta programar (mesma idéia do método carregarSuperDestaqueArtigos())
	}
    
    /**
     * Método para popular a seção de SUPER-DESTAQUE ARTIGOS do PortalBean.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
	private void carregarSuperDestaqueArtigos() throws ECARException {
		SegmentoItemSgti seg = this.getSegmentoItemDao().getSegmentoItemSuperDestaque(
				this.getRequest().getSession().getServletContext());
		
		if(seg != null) {
			SecaoBean secaoBean = new SecaoBean();
			
			secaoBean.setId(ID_SECAO_SUPER_DESTAQUE);
			secaoBean.setTitulo(seg.getTituloSgti());
			
			ArrayList list = new ArrayList();
			
			InformacaoBean info = new InformacaoBean();
			info.setDataHora(Data.parseDate(seg.getDataItemSgti()));
			info.setDescricao(seg.getLinhaApoioSgti());
			info.setImagem(new ecar.dao.ConfiguracaoDao(null).getConfiguracao().getRaizUpload() + seg.getImagemCapaSgti());
			info.setLink(seg.getSegmentoItemLeiauteSgtil().getLinkSgtil());
			info.setLinkMais("ctrl_artigos.jsp");
			info.setTitulo(seg.getLegendaImagCapaSgti());
			info.setCodSgti(seg.getCodSgti());
			info.setFonte(seg.getSegmentoItemFonteSgtif().getDescricaoSgtif());
			
			list.add(info);
			
			secaoBean.setInformacaoBean(list);

			this.portalBean.adicionarElementoNoSecaoBean(secaoBean);
		}
	}

    /**
     * Método para popular a seção de MAIS ARTIGOS do PortalBean.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
	private void carregarMaisArtigos() throws ECARException {
		List list = this.getSegmentoItemDao().getSegmentoItemMaisArtigos(
				this.getRequest().getSession().getServletContext(),((ecar.login.SegurancaECAR)this.getRequest().getSession().getAttribute("seguranca")).getUsuario());
		
		Iterator it = list.iterator();
		
		SecaoBean secaoBean = new SecaoBean();		
		secaoBean.setId(ID_SECAO_MAIS_ARTIGOS);
		
		ArrayList aList = new ArrayList();
		
		while(it.hasNext()) {
			SegmentoItemSgti seg = (SegmentoItemSgti)it.next();
						
			InformacaoBean info = new InformacaoBean();
			info.setDataHora(Data.parseDate(seg.getDataItemSgti()));
			
			String linha = seg.getLinhaApoioSgti();
			
			if (linha.length() >= 50){
				for (int i = 50; i > 0; i--) {
					if (linha.charAt(i)==' ') {
						linha = Util.substring(linha, 0, i)+"...";
						break;
					}
				}				
			}						
			info.setDescricao(linha);			
			info.setTitulo(seg.getTituloSgti());
			info.setDataHora(Data.parseDate(seg.getDataItemSgti()));
			info.setCodSgti(seg.getCodSgti());
			info.setLink(seg.getSegmentoItemLeiauteSgtil().getLinkSgtil());
			
			aList.add(info);			
		}
		secaoBean.setInformacaoBean(aList);

		this.portalBean.adicionarElementoNoSecaoBean(secaoBean);
	}

	/**
     * Método para popular a seção de AGENDA do PortalBean.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
	private void carregarAgenda() throws ECARException {
				
		agendaOcorrenciaDao = new AgendaOcorrenciaDao(this.getRequest());
		
		String strNow = Data.parseDate(Data.getDataAtual()); 
		
		this.getRequest().setAttribute("dataDe", strNow);
		this.getRequest().setAttribute("ativo", "S");
		
		List list = agendaOcorrenciaDao.pesquisar(this.getRequest());
		Iterator it = list.iterator();

		ArrayList aList = new ArrayList();
		SecaoBean secaoBean = new SecaoBean();
		secaoBean.setId(ID_SECAO_AGENDA);
		
		while(it.hasNext()) {
			AgendaOcorrenciaAgeo agendaOc = (AgendaOcorrenciaAgeo)it.next();
						
			InformacaoBean info = new InformacaoBean();
						
			String sHora = String.valueOf(agendaOc.getHoraEventoAgeo());
			if (sHora.length()==1) sHora = "0"+sHora;
			
			String sMin = String.valueOf(agendaOc.getMinutoEventoAgeo());
			if (sMin.length()==1) sMin = "0"+sMin;
			
			info.setDataHora(Data.parseDate(agendaOc.getDataEventoAgeo())+" - "+sHora+":"+sMin);
			info.setTitulo(agendaOc.getAgendaAge().getEventoAge());
			info.setCodSgti(agendaOc.getCodAgeo());
			info.setLink("ctrl_agenda.jsp");  //TODO: link?			
			
			aList.add(info);			
		}		
		secaoBean.setInformacaoBean(aList);
		this.portalBean.adicionarElementoNoSecaoBean(secaoBean);
	}

    /**
     * Método para popular a seção de TAXACAO/CLIPS do PortalBean.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
	private void carregarTaxacaoClip() throws ECARException {
					
			List list = this.getSegmentoItemDao().getTaxacaoClip(
					this.getRequest().getSession().getServletContext());
			
			Iterator it = list.iterator();
			
			SecaoBean secaoBean = new SecaoBean();			
			secaoBean.setId(ID_SECAO_TAXACAO_CLIP);
			
			ArrayList aList = new ArrayList();
			
			while(it.hasNext()) {
				SegmentoItemSgti seg = (SegmentoItemSgti)it.next();
							
				InformacaoBean info = new InformacaoBean();
				info.setDataHora(Data.parseDate(seg.getDataItemSgti()));
				info.setDescricao(seg.getSegmentoItemFonteSgtif().getDescricaoSgtif());
				info.setTitulo(seg.getTituloSgti());
				SegmentoItemLeiauteSgtil leiaute = seg.getSegmentoItemLeiauteSgtil();
				info.setLink(leiaute!=null?leiaute.getLinkSgtil():"ctrl_taxacao.jsp"); //TODO: retirar ctrl_taxacao.jsp
				info.setCodSgti(seg.getCodSgti());
				
				aList.add(info);			
			}			
			secaoBean.setInformacaoBean(aList);

			this.portalBean.adicionarElementoNoSecaoBean(secaoBean);
	}

    /**
     * Método para popular a seção de SUB-AREAS DESTAQUE do PortalBean.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
	private void carregarAreaSubAreaDestaque() throws ECARException {
		
		DestaqueSubAreaDao dDAO = new DestaqueSubAreaDao(this.getRequest());
		
		List list = dDAO.getSubAreaCapa();
		
		SecaoBean secaoBean = new SecaoBean();		
		secaoBean.setId(ID_SECAO_SUB_AREA_DESTAQUE);
		
		List aList = new ArrayList();
		
		if ((list != null)&&(list.size()>0)){			
			Iterator it = list.iterator();			
			while(it.hasNext()) {
				DestaqueSubAreaDtqsa destDtqsa = (DestaqueSubAreaDtqsa)it.next();
				
				InformacaoBean info = new InformacaoBean();
				info.setDataHora(Data.parseDate(destDtqsa.getDestaqueAreaDtqa().getDataInclusaoDtqa()));
				info.setDescricao(destDtqsa.getDestaqueAreaDtqa().getDescricaoDtqa());
				info.setTitulo(destDtqsa.getDestaqueAreaDtqa().getIdentificacaoDtqa());
				
				aList.add(info);
			}			
		}
		secaoBean.setInformacaoBean(aList);
	}
	

    /**
     * Método para recuperar a seção de SUPER-DESTAQUE ARTIGOS do PortalBean.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return SecaoBean
     * @throws ECARException
     */
	public SecaoBean getSuperDestaqueArtigos() throws ECARException {
		if(this.getPortalBean().getSecaoBean() != null) {
			Iterator it = this.getPortalBean().getSecaoBean().iterator();
	
			while(it.hasNext()) {
				SecaoBean secao = (SecaoBean)it.next();
				
				if(ID_SECAO_SUPER_DESTAQUE.equals(secao.getId())) {
					return secao;
				}
			}
		}
		return null;
	}
	
	/**
     * Método para recuperar a seção de MAIS ARTIGOS do PortalBean.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return SecaoBean
     * @throws ECARException
     */
	public SecaoBean getMaisArtigos() throws ECARException {
		if(this.getPortalBean().getSecaoBean() != null) {
			Iterator it = this.getPortalBean().getSecaoBean().iterator();
	
			while(it.hasNext()) {
				SecaoBean secao = (SecaoBean)it.next();
				
				if(ID_SECAO_MAIS_ARTIGOS.equals(secao.getId())) {
					return secao;
				}
			}
		}
		return null;
	}
	
	/**
     * Método para recuperar a seção da AGENDA do PortalBean.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return SecaoBean 
     * @throws ECARException
     */
	public SecaoBean getAgenda() throws ECARException {
		if(this.getPortalBean().getSecaoBean() != null) {
			Iterator it = this.getPortalBean().getSecaoBean().iterator();
	
			while(it.hasNext()) {
				SecaoBean secao = (SecaoBean)it.next();
				
				if(ID_SECAO_AGENDA.equals(secao.getId())) {
					return secao;
				}
			}
		}
		return null;
	}
	
	/**
     * Método para recuperar a seção da TAXACAO/CLIPS do PortalBean.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return SecaoBean 
     * @throws ECARException
     */
	public SecaoBean getTaxacaoClips() throws ECARException {
		if(this.getPortalBean().getSecaoBean() != null) {
			Iterator it = this.getPortalBean().getSecaoBean().iterator();
	
			while(it.hasNext()) {
				SecaoBean secao = (SecaoBean)it.next();
				
				if(ID_SECAO_TAXACAO_CLIP.equals(secao.getId())) {
					return secao;
				}
			}
		}
		return null;
	}
	
	/**
     * Método para recuperar a seção da SUB-ÁREAS DE DESTAQUE do PortalBean.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return SecaoBean 
     * @throws ECARException
     */
	public SecaoBean getSubAreaDestaque() throws ECARException {
		if(this.getPortalBean().getSecaoBean() != null) {
			Iterator it = this.getPortalBean().getSecaoBean().iterator();
	
			while(it.hasNext()) {
				SecaoBean secao = (SecaoBean)it.next();
				
				if(ID_SECAO_SUB_AREA_DESTAQUE.equals(secao.getId())) {
					return secao;
				}
			}
		}
		return null;
	}
}
