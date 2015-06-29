/*
 * Created on 29/10/2004
 * 
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.EfIettFonteTotEfieft;
import ecar.pojo.EfItemEstContaEfiec;
import ecar.pojo.EfItemEstPrevisaoEfiep;
import ecar.pojo.FonteRecursoFonr;
import ecar.pojo.ItemEstruturaIett;

/**
 * @author evandro
 *
 */
public class FonteRecursoDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public FonteRecursoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Verifica e exclui
	 *  
	 * @author n/c
	 * @param fonteRecurso
	 * @throws ECARException
	 */
	public void excluir(FonteRecursoFonr fonteRecurso) throws ECARException {	    
	   try{
	       	boolean excluir = true;
		    if(contar(fonteRecurso.getEfItemEstContaEfiecs()) > 0){
		    	List list = new ArrayList(fonteRecurso.getEfItemEstContaEfiecs());
		    	EfItemEstContaEfiec ocorrencia = (EfItemEstContaEfiec) list.get(0);
		    	
		        excluir = false;
			    throw new ECARException(
			    		"fonteRecurso.exclusao.erro.efItemEstContaEfiecs", null, new String[] {ocorrencia.getContaSistemaOrcEfiec()});
		    }
		    if(contar(fonteRecurso.getEfItemEstPrevisaoEfieps()) > 0){
		    	List list = new ArrayList(fonteRecurso.getEfItemEstPrevisaoEfieps());
		    	EfItemEstPrevisaoEfiep ocorrencia = (EfItemEstPrevisaoEfiep) list.get(0);
		    	
		    	String iett = (ocorrencia.getItemEstruturaIett() != null) ? ocorrencia.getItemEstruturaIett().getSiglaIett() + ": " + ocorrencia.getItemEstruturaIett().getNomeIett() : "";
		    	String exercicio = (ocorrencia.getExercicioExe() != null) ? ocorrencia.getExercicioExe().getDescricaoExe() : "";
		    	String recurso = (ocorrencia.getRecursoRec() != null) ? ocorrencia.getRecursoRec().getNomeRec() : "";

		    	excluir = false;
			    throw new ECARException(
			    		"fonteRecurso.exclusao.erro.efItemEstPrevisaoEfieps", null, new String[] {iett, exercicio, recurso});
		    } /*
		    FIXME : Historico : Valida existência no histórico antes de excluir.
		    if(contar(fonteRecurso.getEfItemEstPrevhistEfiephs()) > 0){
		    	List list = new ArrayList(fonteRecurso.getEfItemEstPrevhistEfiephs());
		    	EfItemEstPrevhistEfieph ocorrencia = (EfItemEstPrevhistEfieph) list.get(0);
		    			    	
		    	String iett = (ocorrencia.getItemEstruturaIett() != null) ? ocorrencia.getItemEstruturaIett().getSiglaIett() + ": " + ocorrencia.getItemEstruturaIett().getNomeIett() : "";
		    	String exercicio = (ocorrencia.getExercicioExe() != null) ? ocorrencia.getExercicioExe().getDescricaoExe() : "";
		    	String recurso = (ocorrencia.getRecursoRec() != null) ? ocorrencia.getRecursoRec().getNomeRec() : "";

		    	excluir = false;
			    throw new ECARException(
			    		"fonteRecurso.exclusao.erro.efItemEstPrevhistEfiephs", null, new String[] {iett, exercicio, recurso});
		    }
		    */
	       	if(contar(fonteRecurso.getEfIettFonteTotEfiefts()) > 0){
	       		List list = new ArrayList(fonteRecurso.getEfIettFonteTotEfiefts());
	       		EfIettFonteTotEfieft ocorrencia = (EfIettFonteTotEfieft) list.get(0);
	       		
	       		excluir = false;
		    	String iett = (ocorrencia.getItemEstruturaIett() != null) ? ocorrencia.getItemEstruturaIett().getSiglaIett() + ": " + ocorrencia.getItemEstruturaIett().getNomeIett() : "";
		    	throw new ECARException(
		    			"fonteRecurso.exclusao.erro.efIettFonteTotEfiefts", null, new String[] {iett});
	       	}
	       	/*
	       	FIXME : Historico : Valida a existência no histórico antes de excluir.
	       	if(contar(fonteRecurso.getEfIettFonteTothisEfiefhs()) > 0){
	       		List list = new ArrayList(fonteRecurso.getEfIettFonteTothisEfiefhs());
	       		EfIettFonteTothisEfiefh ocorrencia = (EfIettFonteTothisEfiefh) list.get(0);
	       		
	       		excluir = false;
	       		String iett = (ocorrencia.getItemEstruturaIett() != null) ? ocorrencia.getItemEstruturaIett().getSiglaIett() + ": " + ocorrencia.getItemEstruturaIett().getNomeIett() : "";
		    	throw new ECARException(
		    			"fonteRecurso.exclusao.erro.efIettFonteTothisEfiefh", null, new String[] {iett});
	       	}
		    */
		    if(excluir)
		        super.excluir(fonteRecurso);	
		    
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}
	
	/**
	 * Retorna um lista de exercícios que estão dentro do período de validade cadastrado para a fonte de recurso e
	 * ainda não estejam  vinculados a esta fonte
         * @param item
         * @param fonteRecurso
	 * @return
         * @throws ECARException
         * @throws HibernateException
	 */
	public Collection getExerciciosContidosPeriodoValidadeFonteRecurso(ItemEstruturaIett item, FonteRecursoFonr fonteRecurso) throws ECARException{	    
        try{
        	
        	
    	    String select = "from ExercicioExe e where not (:fimValidade < e.dataInicialExe or :iniValidade > e.dataFinalExe)";
    	    
    	    Query q = this.session.createQuery(select);
    	    
    	    q.setDate("fimValidade", fonteRecurso.getDataFimValidadeFonr());
    	    q.setDate("iniValidade", fonteRecurso.getDataIniValidadeFonr());
    	    
    	    List exerciciosPermitidos = q.list();
    	    List exerciciosCadastrados = new ArrayList();
    	    
            return Util.diferenca(exerciciosPermitidos, exerciciosCadastrados);  
            
        } catch(HibernateException e){
        	this.logger.error(e);
            throw new ECARException(e);
        }        
	}
	
	/**
	 * Retorna uma lista dos itens que contém determinada fonte de recursos
	 * @param fonteRecurso
	 * @return List
	 * @throws ECARException
	 */
	public List getItemEstruturaIetts(FonteRecursoFonr fonteRecurso) throws ECARException {
		List retorno = new ArrayList();
		return retorno;
	}
	
	/**
	 * Retorna uma lista das fontes de recursos ativos
         * @return
         * @throws ECARException
	 */
	public List getAtivos()throws ECARException {
		
		FonteRecursoFonr fonteRecurso = new FonteRecursoFonr();
		fonteRecurso.setIndAtivoFonr("S");
		return super.pesquisar(fonteRecurso, new String[] {"nomeFonr","asc"});		
	}
	
}