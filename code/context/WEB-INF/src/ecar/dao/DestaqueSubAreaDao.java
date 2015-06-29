/*
 * Created on 10/05/2005
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.AgendaAge;
import ecar.pojo.AgendaOcorrenciaAgeo;
import ecar.pojo.DestaqueAreaDtqa;
import ecar.pojo.DestaqueItemRelDtqir;
import ecar.pojo.DestaqueSubAreaDtqsa;
import ecar.pojo.DestaqueTipoOrdemDtqto;
import ecar.pojo.SegmentoAgendaBean;
import ecar.pojo.SegmentoItemSgti;
import ecar.pojo.SegmentoSgt;

/**
 * @author felipe
 *
 */
public class DestaqueSubAreaDao extends Dao{
	
    /**
     *
     * @param request
     */
    public DestaqueSubAreaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
     * A partir de dados passados por request popula um objeto DestaqueSubAreaDtqsa
         * @param destaqueSubArea
         * @param request
     * @param recuperarParametrosComoString indica se irá recuperar dados nulos como String vazia
     * @throws ECARException
     */
    public void setDestaqueSubArea(DestaqueSubAreaDtqsa destaqueSubArea, HttpServletRequest request, boolean recuperarParametrosComoString) throws ECARException{
        
        try{                     
            if(!"".equals(Pagina.getParamStr(request, "destaqueAreaDtqa"))){
                destaqueSubArea.setDestaqueAreaDtqa((DestaqueAreaDtqa) this.buscar(
                			DestaqueAreaDtqa.class,
                            Long.valueOf(Pagina.getParamStr(request, "destaqueAreaDtqa"))));
            }
            if(!"".equals(Pagina.getParamStr(request, "destaqueTipoOrdemDtqto"))){
                destaqueSubArea.setDestaqueTipoOrdemDtqto((DestaqueTipoOrdemDtqto) this.buscar(
                			DestaqueTipoOrdemDtqto.class,
                            Long.valueOf(Pagina.getParamStr(request, "destaqueTipoOrdemDtqto"))));
            }
        	if(!"".equals(Pagina.getParamStr(request, "qtdMaxItensDtqsa")))
        		destaqueSubArea.setQtdMaxItensDtqsa(Integer.valueOf(Pagina.getParamStr(request, "qtdMaxItensDtqsa")));

            if(recuperarParametrosComoString){
            	destaqueSubArea.setIdentificacaoDtqsa(Pagina.getParamStr(request, "identificacaoDtqsa"));
            	destaqueSubArea.setDescricaoDtqsa(Pagina.getParamStr(request, "descricaoDtqsa"));
            } else {
            	destaqueSubArea.setIdentificacaoDtqsa(Pagina.getParam(request, "identificacaoDtqsa"));
            	destaqueSubArea.setDescricaoDtqsa(Pagina.getParam(request, "descricaoDtqsa"));
            }
        } catch(Exception e){
            this.logger.error(e);
            throw new ECARException(e);
        }
        
    }
    
    
	/**
	 * Exclui uma sub área de destaque, verificando antes se ela possui relação com outras tabelas. Neste caso, não permite
	 * exclusão
         * @param destaqueSubArea
         * @throws ECARException
	 */
	public void excluir(DestaqueSubAreaDtqsa destaqueSubArea) throws ECARException {	    
	   try{
	       	boolean excluir = true;

		    if(contar(destaqueSubArea.getDestaqueItemRelDtqirs()) > 0){ 
		        excluir = false;
			    throw new ECARException("destaqueSubArea.exclusao.erro.destaqueItemRelDtqirs");
		    }      			       
		    if(excluir)
		        super.excluir(destaqueSubArea);	
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}
	
	/**
	 * Devolve uma lista com todos os Destaques de Uma Sub Área de Destaque. Estes destaques podem ser
	 * Ocorrências da Agenda ou Itens de Segmento. Por isso o método os emcapsula em um objeto SegmentoAgendaBean.
	 * @param subArea
	 * @return
	 * @throws ECARException
	 */
	public List getItensDestaqueSubArea(DestaqueSubAreaDtqsa subArea) throws ECARException{
		List retorno = new ArrayList();
		Iterator it = subArea.getDestaqueItemRelDtqirs().iterator();
		while(it.hasNext()){
			DestaqueItemRelDtqir destaqueItem = (DestaqueItemRelDtqir) it.next();
			SegmentoAgendaBean segAgd = new SegmentoAgendaBean();
			segAgd.setCodigo(destaqueItem.getCodDtqir());
			if(destaqueItem.getAgendaOcorrenciaAgeo() != null){				
				segAgd.setDescricao(new AgendaOcorrenciaDao(request).getLabelExibicao(destaqueItem.getAgendaOcorrenciaAgeo()));
				segAgd.setData(destaqueItem.getAgendaOcorrenciaAgeo().getDataEventoAgeo());
			}			
			if(destaqueItem.getSegmentoItemSgti() != null){
				segAgd.setDescricao(destaqueItem.getSegmentoItemSgti().getTituloSgti());
				segAgd.setData(destaqueItem.getSegmentoItemSgti().getDataItemSgti());
			}
			retorno.add(segAgd);
	        Collections.sort(retorno,
		            new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			SegmentoAgendaBean sa1 = (SegmentoAgendaBean) o1;
		        			SegmentoAgendaBean sa2 = (SegmentoAgendaBean) o2;
		        		    return sa1.getData().compareTo(sa2.getData());
		        		}
		   } );					
		}
		return retorno;
	}
	
	
	/**
	 * Retorna uma lista com todos os Segmentos e Agendas ativas encapsulados em objetos SegmentoAgendaBean
	 * @return
	 * @throws ECARException
	 */
	public List getSegmentosAgendas() throws ECARException{
		List segmentos = new SegmentoDao(request).getAtivos();
	
		List retorno = new ArrayList();

		Iterator itS = segmentos.iterator();
		while(itS.hasNext()){
			SegmentoSgt segmento = (SegmentoSgt) itS.next();
			SegmentoAgendaBean segAgd = new SegmentoAgendaBean();
			segAgd.setAgenda(false);
			segAgd.setSegmento(true);
			segAgd.setDescricao(segmento.getTituloSgt());
			segAgd.setCodigo(segmento.getCodSgt());
			retorno.add(segAgd);
		}
		List agendas = new AgendaDao(request).getAtivos();
		Iterator itA = agendas.iterator();
		while(itA.hasNext()){
			AgendaAge agenda = (AgendaAge) itA.next();
			SegmentoAgendaBean segAgd = new SegmentoAgendaBean();
			segAgd.setAgenda(true);
			segAgd.setSegmento(false);
			segAgd.setDescricao(agenda.getEventoAge());
			segAgd.setCodigo(agenda.getCodAge());
			retorno.add(segAgd);
		}
			
        Collections.sort(retorno,
	            new Comparator() {
	        		public int compare(Object o1, Object o2) {
	        			SegmentoAgendaBean sa1 = (SegmentoAgendaBean) o1;
	        			SegmentoAgendaBean sa2 = (SegmentoAgendaBean) o2;
	        		    return sa1.getDescricao().compareToIgnoreCase(sa2.getDescricao());
	        		}
	   } );		
		return retorno;
	}
    
	/**
	 * Vincula os itens de Segmentos da lista a uma sub área de destaque
	 * @param listaDeSegmentosItem
	 * @param subArea
	 * @throws ECARException
	 */
	public void vincularSegmentosaSubArea(List listaDeSegmentosItem, DestaqueSubAreaDtqsa subArea) throws ECARException{
			List objsParaSalvar = new ArrayList();
			Iterator it = listaDeSegmentosItem.iterator();
			while(it.hasNext()){
				SegmentoItemSgti segmento = (SegmentoItemSgti) it.next();
				DestaqueItemRelDtqir destaqueItem = new DestaqueItemRelDtqir();
				destaqueItem.setDestaqueSubAreaDtqsa(subArea);
				destaqueItem.setSegmentoItemSgti(segmento);
				objsParaSalvar.add(destaqueItem);
			}
			super.salvarOuAlterar(objsParaSalvar);
	}
	
	/**
	 * Vincula as ocorrências de agenda da lista a uma sub área de destaque
	 * @param listaDeAgendas
	 * @param subArea
	 * @throws ECARException
	 */
	public void vincularAgendasSubArea(List listaDeAgendas, DestaqueSubAreaDtqsa subArea) throws ECARException{
		List objsParaSalvar = new ArrayList();
		Iterator it = listaDeAgendas.iterator();
		while(it.hasNext()){
			AgendaOcorrenciaAgeo agenda = (AgendaOcorrenciaAgeo) it.next();
			DestaqueItemRelDtqir destaqueItem = new DestaqueItemRelDtqir();
			destaqueItem.setDestaqueSubAreaDtqsa(subArea);
			destaqueItem.setAgendaOcorrenciaAgeo(agenda);
			destaqueItem.setAgendaAge(agenda.getAgendaAge());
			objsParaSalvar.add(destaqueItem);
		}
		super.salvarOuAlterar(objsParaSalvar);		
	}
	
	
	
	/**
	 * Retira vinculo entre os Destaques da lista e a sub área de destaque (exclusão de destaques)
	 * @param listaDeDestaques
	 * @throws ECARException
	 */
	public void desvincularDestaquesSubArea(List listaDeDestaques) throws ECARException{
		super.excluir(listaDeDestaques);
	}
	
	/**
	 * Retorna todas subáreas e os itens associados a estas relativos área de capa
	 * @return DestaqueSubAreaDtqsa
	 * @throws ECARException
	 */
	public List getSubAreaCapa()throws ECARException{
		try{			
			Query query = this.getSession().createQuery("from DestaqueSubAreaDtqsa sub where sub.codDtqsa = 1 ");
			return query.list();
		} catch (Exception e){
            this.logger.error(e);
            throw new ECARException(e);
		}
	}
	
}
