/*
 * Created on 06/07/2007
 * 
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.PoderPeriodoExercicioPodPerExe;
import ecar.pojo.PoderPod;

/**
 * @author aleixo
 *
 */
public class PoderDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public PoderDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Utiliza o método excluir da classe Dao realizando antes validações de relacionamento do
	 * poder com registros em outras tabelas.
         * @param poder
	 * @throws ECARException
	 */
	public void excluir(PoderPod poder) throws ECARException {	    
	   try{
	       	boolean excluir = true;
		    if(contar(poder.getOrgaoOrgs()) > 0){
		        excluir = false;
			    throw new ECARException("poder.exclusao.erro.orgaoOrgs");
		    }       
		   		    
		    if(excluir)
		        super.excluir(poder);
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}
	
	/**
	 * Retorna listagem de poderes ordenado por nome
	 * @return List<PoderPod>
	 * @throws ECARException
	 */
    public List getPoderes() throws ECARException {
    	try{
    		StringBuilder qry = new StringBuilder("from PoderPod as poder")
    									.append(" order by poder.nomePod");
    		
    		Query q = this.session.createQuery(qry.toString());
    		
    		return q.list();
    		
    	}
    	catch (HibernateException e){
    		this.logger.error(e);
    		throw new ECARException("erro.hibernateException");
    	}
    }
    
    /**
     * Carrega listagem de Poderes de acordo com o periodo
     * @param codPerExe Identificador <PeriodoExercicioPerExe>
     * @param indAtivoPodPerExe 
     * @return ArrayList<PoderPod>
     * @throws ECARException
     */
    public ArrayList<PoderPod> getPoderesByPeriodicidade( Long codPerExe, Character indAtivoPodPerExe ) throws ECARException{
    	
    	try{
    		StringBuilder qry = new StringBuilder("from PoderPeriodoExercicioPodPerExe as poder")
								.append(" where poder.periodoExercicioPerExe.codPerExe  = :codigo")
    							.append(" and poder.indAtivoPodPerExe  = :indAtivo")
    							.append(" order by poder.poderPod.codPod");
    		
    		Query q = this.session.createQuery(qry.toString());
    		q.setLong("codigo", codPerExe.longValue());
    		q.setCharacter("indAtivo", indAtivoPodPerExe );  
    		
    		List poderPerexe = q.list();
    		ArrayList<PoderPod> listPoder = new ArrayList<PoderPod>();
//    		PoderPod[] arrayPoder = new PoderPod[4];
    		
    		for (Iterator iter = poderPerexe.iterator(); iter.hasNext();) {
				PoderPeriodoExercicioPodPerExe podTmp = (PoderPeriodoExercicioPodPerExe) iter.next();
	
				listPoder.add( podTmp.getPoderPod() );
/* 				if ( podTmp.getPoderPod().getNomePod().equalsIgnoreCase("Poder Legislativo" ) ){
					arrayPoder[0] = podTmp.getPoderPod() ;
				}

				if ( podTmp.getPoderPod().getNomePod().equalsIgnoreCase("Poder Judiciário" ) ){
					arrayPoder[1] = podTmp.getPoderPod() ;					
				}
				
				if ( podTmp.getPoderPod().getNomePod().equalsIgnoreCase("Ministério Público" ) ){
					arrayPoder[2] = podTmp.getPoderPod() ;					
				}
				
				if ( podTmp.getPoderPod().getNomePod().equalsIgnoreCase("Poder Executivo" ) ){
					arrayPoder[3] = podTmp.getPoderPod() ;					
				}				
*/				
			}
    		
/*    		listPoder.add(arrayPoder[0]);
    		listPoder.add(arrayPoder[1]);
    		listPoder.add(arrayPoder[2]);
    		listPoder.add(arrayPoder[3]);
    		*/
    		return listPoder;
    		
    	}
    	catch (HibernateException e){
    		this.logger.error(e);
    		throw new ECARException("erro.hibernateException");
    	}
    	    	
    }
	
}
