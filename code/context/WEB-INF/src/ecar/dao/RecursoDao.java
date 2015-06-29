/*
 * Created on 04/11/2004
 * 
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.EfItemEstContaEfiec;
import ecar.pojo.EfItemEstPrevisaoEfiep;
import ecar.pojo.RecursoRec;

/**
 * @author evandro
 *
 */
public class RecursoDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public RecursoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Verifica  depois exclui recurso
	 * @param recurso
	 * @throws ECARException
	 */
	public void excluir(RecursoRec recurso) throws ECARException {	    
	   try{
	       	boolean excluir = true;
		    if(contar(recurso.getEfItemEstContaEfiecs()) > 0){
		    	List list = new ArrayList(recurso.getEfItemEstContaEfiecs());
		    	EfItemEstContaEfiec ocorrencia = (EfItemEstContaEfiec) list.get(0);
		    	
		    	excluir = false;
			    throw new ECARException(
			    		"recurso.exclusao.erro.efItemEstContaEfiecs", null, new String[] {ocorrencia.getContaSistemaOrcEfiec()});
		    }
		    if(contar(recurso.getEfItemEstPrevisaoEfieps()) > 0){
		    	List list = new ArrayList(recurso.getEfItemEstPrevisaoEfieps());
		    	EfItemEstPrevisaoEfiep ocorrencia = (EfItemEstPrevisaoEfiep) list.get(0);
		    	
		    	String iett = (ocorrencia.getItemEstruturaIett() != null) ? ocorrencia.getItemEstruturaIett().getSiglaIett() + ": " + ocorrencia.getItemEstruturaIett().getNomeIett() : "";
		    	String exercicio = (ocorrencia.getExercicioExe() != null) ? ocorrencia.getExercicioExe().getDescricaoExe() : "";
		    	String fonteRecurso = (ocorrencia.getFonteRecursoFonr() != null) ? ocorrencia.getFonteRecursoFonr().getNomeFonr() : "";
		    	
		        excluir = false;
			    throw new ECARException(
			    		"recurso.exclusao.erro.efItemEstPrevisaoEfieps", null, new String[] {iett, exercicio, fonteRecurso});
		    }
		    /*
		     FIXME : Historico : Valida a existência no histórico antes de excluir.
		    if(contar(recurso.getEfItemEstPrevhistEfiephs()) > 0){ 
		    	List list = new ArrayList(recurso.getEfItemEstPrevhistEfiephs());
		    	EfItemEstPrevhistEfieph ocorrencia = (EfItemEstPrevhistEfieph) list.get(0);
		    	
		    	String iett = (ocorrencia.getItemEstruturaIett() != null) ? ocorrencia.getItemEstruturaIett().getSiglaIett() + ": " + ocorrencia.getItemEstruturaIett().getNomeIett() : "";
		    	String exercicio = (ocorrencia.getExercicioExe() != null) ? ocorrencia.getExercicioExe().getDescricaoExe() : "";
		    	String fonteRecurso = (ocorrencia.getFonteRecursoFonr() != null) ? ocorrencia.getFonteRecursoFonr().getNomeFonr() : "";
		    	
		        excluir = false;
			    throw new ECARException(
			    		"recurso.exclusao.erro.efItemEstPrevhistEfiephs", null, new String[] {iett, exercicio, fonteRecurso});
		    }
		    */
		    if(excluir)
		        super.excluir(recurso);
		    
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}
	
	/**
	 * Retorna uma lista de recursos ativos
         * @return
         * @throws ECARException
	 */
	public List getAtivos()throws ECARException {
		
		RecursoRec recursoRec = new RecursoRec();
		recursoRec.setIndAtivoRec("S");
		return super.pesquisar(recursoRec, new String[] {"nomeRec","asc"});		
	}	
	
	
}