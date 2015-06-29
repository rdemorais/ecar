package ecar.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.Link;

/**
 * @author Jose Andre
 * @since 30/11/2007
 */
public class LinkDao extends Dao {
	
    /**
     *
     * @param request
     */
    public LinkDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
    /**
     *
     * @param request
     * @param objetos
     * @throws ECARException
     */
    @SuppressWarnings("unchecked")
	public void alterar (HttpServletRequest request, ArrayList objetos) throws ECARException {
		Transaction tx = null;
		try {
			//Alteração para colocar as alterações da tela de abas em uma única transação
			
			//ArrayList<Link> objetos = new ArrayList<Link>();
			//super.inicializarLogBean();
			//tx = session.beginTransaction();
			
			/* Passar por todas as abas e atualizar */
			ArrayList<Link> lista = (ArrayList<Link>)this.listar(Link.class, new String[]{"codLink", "asc"});

			for(Link link : lista){
				//O request deve passar os parametros no formato [nome]+[codLink]
				if ("S".equals(Pagina.getParamStr(request, "exibeMonitoramentoLink" + link.getCodLink().toString()))) {
					link.setExibeMonitoramentoLink("S");
				} else {
					link.setExibeMonitoramentoLink("N");					
				}
							
				link.setOrdemLink(Integer.valueOf(Pagina.getParamInt(request, "ordemLink" + link.getCodLink().toString())));
				link.setLabelLink(Pagina.getParamStr(request, "labelLink" + link.getCodLink().toString()));
				link.setIconeLink(Pagina.getParamStr(request, "iconeLink" + link.getCodLink().toString()));
				//link.setUrlLink(Pagina.getParamStr(request, "urlLink" + link.getCodLink().toString()));
				
				session.update(link);
				objetos.add(link);
			}
			//Alteração para colocar as alterações da tela de abas em uma única transação
			//tx.commit();
			
			/* log */
//			if (super.logBean != null) {
//				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
//				super.logBean.setOperacao("ALT");
//
//				for (Iterator itObj = objetos.iterator(); itObj.hasNext();) {
//					super.logBean.setObj(itObj.next());
//					super.loggerAuditoria.info(logBean.toString());
//				}
//			}
		} catch (HibernateException e) {
			if (tx != null)
				try {
					//tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		} catch (ECARException e) {
			if (tx != null)
				try {
					//tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			this.logger.error(e);
			throw e;
		}
	}
	
	 
	/**
     * Retorna um link de acordo com o nome passado como parametro
     * 
         * @param nomeLink
     * @return Link
     */
	public Link buscarLink(String nomeLink) {
		
		List <Link> listLinks =  this.getSession().createQuery(
					"select link " +
					"from Link link " +
					"where nomeLink = :nomeLink " )
					.setParameter("nomeLink", nomeLink).list();
			
		if (listLinks!=null && (!listLinks.isEmpty()) ){
			return (Link)listLinks.iterator().next();
		} else {
			return null;
		}
		
	}
	

	/**
     * Retorna um link de acordo com o nome passado como parametro
     * 
         * @param nomeLink
         * @return Link
     */
	public boolean estaConfiguradoLink(String nomeLink) {
		boolean existe = false;
		
		Link link = buscarLink(nomeLink);
		if (link.getExibeMonitoramentoLink().equals("S")) {
			return true;
		} else {
			return false;
		}
		
	}
	
}
