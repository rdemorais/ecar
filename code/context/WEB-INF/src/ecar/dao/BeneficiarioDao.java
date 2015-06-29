/*
 * Created on 13/09/2004
 * 
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.BeneficiarioBnf;

/**
 * @author denilson
 *
 */
public class BeneficiarioDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public BeneficiarioDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * verifica o beneficiario antes de excluir
	 * @author n/c
	 * @param beneficiario
	 * @throws ECARException
	 */
	public void excluir(BeneficiarioBnf beneficiario) throws ECARException {	    
		   try{
		       	boolean excluir = true;
			   		       
			    if(contar(beneficiario.getItemEstrtBenefIettbs()) > 0){
			        excluir = false;
			        throw new ECARException("beneficiario.exclusao.erro.itemEstrutura");			       
				}
			    if(excluir)
			        super.excluir(beneficiario);	
		   }catch(ECARException e){
			   this.logger.error(e);
		       throw e;
		   }    
		}
	
	/**
	 * verifica se não foi duplicado o beneficiario antes de salvar
	 * @author n/c
	 * @param beneficiario
	 * @throws ECARException
	 */
	public void salvar(BeneficiarioBnf beneficiario) throws ECARException {
		if (pesquisarDuplos(beneficiario, new String[] {"nomeBnf"}, "codBnf").size() > 0)
		    throw new ECARException("beneficiario.validacao.registroDuplicado");
		super.salvar(beneficiario);
	}
	
	/**
	 * verifica se não foi duplicado o beneficiario antes de alterar
	 * @author n/c
	 * @param beneficiario
	 * @throws ECARException
	 */
	public void alterar(BeneficiarioBnf beneficiario) throws ECARException {
		if (pesquisarDuplos(beneficiario, new String[] {"nomeBnf"}, "codBnf").size() > 0)
		    throw new ECARException("beneficiario.validacao.registroDuplicado");
		super.alterar(beneficiario);
	}
}