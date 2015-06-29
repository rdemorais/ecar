/*
 * Created on 18/09/2004
 * 
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.SubAreaSare;

/**
 * @author evandro
 * @author felipe
 *
 */
public class SubAreaDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public SubAreaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}

	/**
	 * Utiliza o método excluir da classe Dao realizando antes validações de relacionamento da 
	 * sub área com registros em outras tabelas.
         * @param subArea
	 * @throws ECARException
	 */
	public void excluir(SubAreaSare subArea) throws ECARException {	    
		   try{
		       	boolean excluir = true;
			    if(contar(subArea.getItemEstruturaIetts()) > 0){
			        excluir = false;
				    throw new ECARException("areas.exclusao.erro.itemEstruturaIetts");
			    }      			       
			    if(excluir)
			        super.excluir(subArea);
		   }catch(ECARException e){
			   this.logger.error(e);
		       throw new ECARException(e);
		   }    
		}

	/**
	 * Verifica duplicação depois salva
	 * @param subArea
	 * @throws ECARException
	 */
	public void salvar(SubAreaSare subArea) throws ECARException {
		if(subArea.getCodigoIdentSare() != null)
			if (pesquisarDuplos(subArea, new String[] {"codigoIdentSare"}, "codSare").size() > 0)
				throw new ECARException("subArea.validacao.registroDuplicado");
		super.salvar(subArea);
	}
	
	/**
	 * Verifica duplicação depois altera
	 * @param subArea
	 * @throws ECARException
	 */
	public void alterar(SubAreaSare subArea) throws ECARException {
		if(subArea.getCodigoIdentSare() != null)
			if (pesquisarDuplos(subArea, new String[] {"codigoIdentSare"}, "codSare").size() > 0)
				throw new ECARException("subArea.validacao.registroDuplicado");
		super.alterar(subArea);
	}
}
