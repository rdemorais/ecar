/*
 * Created on 29/10/2004
 * 
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.PeriodicidadePrdc;

/**
 * @author evandro
 *
 */
public class PeriodicidadeDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public PeriodicidadeDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Verifica depois exclui
	 * 
	 * @param periodicidade
	 * @throws ECARException
	 */
	public void excluir(PeriodicidadePrdc periodicidade) throws ECARException {	    
	   try{
	       	boolean excluir = true;
		    if(contar(periodicidade.getConfiguracaoCfgs()) > 0){ 
		        excluir = false;
			    throw new ECARException("periodicidade.exclusao.erro.configuracaoCfgs");
		    }
		    if(contar(periodicidade.getItemEstruturaIetts()) > 0){ 
		        excluir = false;
			    throw new ECARException("periodicidade.exclusao.erro.itemEstruturaIetts");
		    } 

		    if(excluir)
		        super.excluir(periodicidade);	
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}
	
	/**
	 * Verifica depois salva
	 * 
	 * @param periodicidade
	 * @throws ECARException
	 */
	public void salvar(PeriodicidadePrdc periodicidade) throws ECARException {
		if (pesquisarDuplos(periodicidade, new String[] {"descricaoPrdc","numMesesPrdc"}, "codPrdc").size() > 0)
		    throw new ECARException("periodicidade.validacao.registroDuplicado");
		super.salvar(periodicidade);
	}
	
	/**
	 * Verifica depois altera
	 * 
	 * @param periodicidade
	 * @throws ECARException
	 */
	public void alterar(PeriodicidadePrdc periodicidade) throws ECARException {
		if (pesquisarDuplos(periodicidade, new String[] {"descricaoPrdc","numMesesPrdc"}, "codPrdc").size() > 0)
		    throw new ECARException("periodicidade.validacao.registroDuplicado");
		super.alterar(periodicidade);
	}
}