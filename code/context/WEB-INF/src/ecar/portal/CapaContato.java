/*
 * Created on 11/07/2005
 *
 */
package ecar.portal;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ecar.dao.ContatoAreaDao;
import ecar.dao.ContatoMailDao;
import ecar.exception.ECARException;
import ecar.pojo.ContatoAreaCtta;
import ecar.pojo.ContatoMailCttm;

/**
 * @author cristiano
 *
 * Classe que carrega informações para serem exibidas na tela contato do portal 
 * 
 */

public class CapaContato extends Portal {
	
	//private static final String ID_SECAO_SUPER_DESTAQUE = "SECAO_SUPER_DESTAQUE"; 
	//private static final String ID_SECAO_MAIS_ARTIGOS = "SECAO_MAIS_ARTIGOS"; 
	//private static final String ID_SECAO_AGENDA = "SECAO_AGENDA"; 
	//private static final String ID_SECAO_TAXACAO_CLIP = "SECAO_TAXACAO_CLIP"; 

	// DAOs acessados pela classe
	private ContatoAreaDao contatoAreaDao = null;
	
	private List listAreasContato;
	
	private String areaContatoSelecionada = "";

	/**
     * Construtor.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param HttpServletRequest request
     * @throws ECARException
     */
	public CapaContato(HttpServletRequest request) throws ECARException {
		super(request);

		this.contatoAreaDao= new ContatoAreaDao(null);
		
		this.carregarComboAreaContato();
		
		//this.carregarSuperDestaqueArtigos();
		//this.carregarMaisArtigos();
		//this.carregarAgenda();
		//this.carregarTaxacaoClip();
		//this.carregarAreaSubAreaDestaque();
	}
    
    
    /**
     * Método para retornar o DAO da tabela ContatoAreaDao.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @return ContatoAreaDao
     */
	private ContatoAreaDao getContatoAreaDao() {
		return this.contatoAreaDao;
	}

    /**
     * Método para popular combo área contato.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @throws ECARException
     */
	private void carregarComboAreaContato() throws ECARException {
		
		this.setListAreasContato(this.getContatoAreaDao().getContatoAreaAtivas());
		
	}

    /**
     * Envia e-mail.<br>
     * 
     * @author N/C
     * @since N/C
     * @version N/C
     * @param HttpServletRequest request
     * @param Long codCtta
     * @param String remetente
     * @param String msg
     * @throws Exception
     */
	public void enviarEmail(HttpServletRequest request, Long codCtta, String remetente, String msg) throws Exception {
		String assunto = "", destinatarioPara = "", destinatarioCc = "", destinatarioBcc = "";
		
		ContatoAreaCtta contatoAreaCtta = new ContatoAreaCtta();
		ContatoMailCttm contatoMailCttm = new ContatoMailCttm();
		ContatoAreaDao contatoAreaDao = new ContatoAreaDao(request);
		ContatoMailDao contatoMailDao = new ContatoMailDao(request);
		
		contatoAreaCtta = (ContatoAreaCtta)contatoAreaDao.buscar(ContatoAreaCtta.class, codCtta);
		
		request.setAttribute("contatoAreaCtta", codCtta);
		contatoMailDao.setContatoMail(contatoMailCttm, request, false);
				
		ContatoAreaCtta ctta = new ContatoAreaCtta();
		ctta.setCodCtta(codCtta);
		List list = contatoMailDao.getListContatoMailCttm(ctta);
		
		for (int i = 0; i < list.size(); i++) {			
			ContatoMailCttm cttm = (ContatoMailCttm) list.get(i);
			
			if (cttm.getContatoMailCategoriaCttm().getCodCttmc().intValue() == 1)	destinatarioPara += cttm.getEmailCttm() + ",";	
			if (cttm.getContatoMailCategoriaCttm().getCodCttmc().intValue() == 2)	destinatarioCc += cttm.getEmailCttm() + ",";	
			if (cttm.getContatoMailCategoriaCttm().getCodCttmc().intValue() == 3)	destinatarioBcc += cttm.getEmailCttm() + ",";			
		}
				
		
		if (contatoAreaCtta.getAssuntoRetornoCtta()!=null) assunto = contatoAreaCtta.getAssuntoRetornoCtta();
			
		comum.util.Util.enviarEmail(assunto, null, remetente, msg, destinatarioPara, destinatarioCc, destinatarioBcc, null);
		
		if ((contatoAreaCtta.getIndEmailRespostaCtta()!=null)&&("S".equals(contatoAreaCtta.getIndEmailRespostaCtta())))	{
			destinatarioPara = remetente;	
			
			comum.util.Util.enviarEmail(assunto, contatoAreaCtta.getNomeCtta(), remetente, contatoAreaCtta.getTextoRetornoCtta(), 
					destinatarioPara, null, null, null);			
		}		
	}

	/**
	 * Retorna List listAreasContato.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return List
	 */
	public List getListAreasContato() {
		return listAreasContato;
	}

	/**
	 * Atribui valor especificado para List listAreasContato.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param List listAreasContato
	 */
	private void setListAreasContato(List listAreasContato) {
		this.listAreasContato = listAreasContato;
	}

	/**
	 * Retorna String areaContatoSelecionada.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return String
	 */
	public String getAreaContatoSelecionada() {
		return areaContatoSelecionada;
	}


	/**
	 * Atribui valor especificado para String areaContatoSelecionada.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @param String areaContatoSelecionada
	 */
	public void setAreaContatoSelecionada(String areaContatoSelecionada) {
		this.areaContatoSelecionada = areaContatoSelecionada;
	}

	/**
	 * Retorna lista ordenada pelo nome.<br>
	 * 
	 * @author N/C
     * @since N/C
     * @version N/C
	 * @return List
	 */
	public List getListAreasContatoOrdenada() {
		Collections.sort(listAreasContato,
	            new Comparator() {
		    		public int compare(Object o1, Object o2) {
		    			ContatoAreaCtta c1 = (ContatoAreaCtta) o1;
		    			ContatoAreaCtta c2 = (ContatoAreaCtta) o2;
		    		    return c1.getNomeCtta().compareTo(c2.getNomeCtta());
		    		}
		} );			
		return listAreasContato;
	}
}
