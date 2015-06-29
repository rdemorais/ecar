/*
 * Created on 27/10/2004
 * 
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.PeriodoExercicioPerExe;

/**
 * @author aleixo
 *
 */
public class PeriodoExercicioDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public PeriodoExercicioDao(HttpServletRequest request)  {
		super();
		this.request = request;
	}
	
	/**
	 * Exclui um periodo de exercicio se este nao estah associado com um exercicio.
	 * 
	 * @author yuricooper
	 * @since 31/JUL/2007
	 * @param perExe
	 * @throws ECARException
	 */
	public void excluir(PeriodoExercicioPerExe perExe) throws ECARException{
		
		if (contar(perExe.getExercicioExes()) > 0){
			throw new ECARException("perExe.exclusao.erro.exercicioExes");
		}
		else{
			super.excluir(perExe);
		}
	}
	
	/** 
	 * Salva um Periodo de Exercicio somente se não sobrepuser nenhum periodo de exercicio já cadastrado.
	 * 
         * @param perExe
         * @author yuricooper
	 * @since 31/JUL/2007
	 * @throws ECARException
	 */
	public void salvar(PeriodoExercicioPerExe perExe) throws ECARException {
		if (pesquisarDuplos(perExe, new String[] {"nomePerExe"}, "codPerExe").size() > 0)
		    throw new ECARException("perExe.validacao.registroDuplicado");
		else {
	        super.salvar(perExe);
	    }
	}
	
	/**
	 * Altera um periodo de exercicio somente se não sobrepoem nenhum outro periodo de exercicio.
	 * @author yuricooper
	 * @since 31/JUL/2007
	 * @param perExe
	 * @throws ECARException
	 */
	public void alterar(PeriodoExercicioPerExe perExe) throws ECARException {
		if (pesquisarDuplos(perExe, new String[] {"nomePerExe"}, "codPerExe").size() > 0)
		    throw new ECARException("perExe.validacao.registroDuplicado");
	   else {
	        super.alterar(perExe);
	    }
	}
}