/*
 * Created on 18/09/2004
 * 
 */
package ecar.dao;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.AreaAre;

/**
 * @author evandro
 *
 */
public class AreaDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public AreaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Exclui uma área, verificando antes se ela possui relação com outras tabelas. Neste caso, não permite
	 * exclusão
         * @param area
	 * @throws ECARException
	 */
	public void excluir(AreaAre area) throws ECARException {	    
	   try{
	       	boolean excluir = true;

		    if(contar(area.getItemEstruturaIetts()) > 0){
		        excluir = false;
		        throw new ECARException("area.exclusao.erro.itemEstrutura");			       
			}
		    if(excluir)
		        super.excluir(area);	
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}
	
        /**
         *
         * @param area
         * @throws ECARException
         */
        public void salvar(AreaAre area) throws ECARException {
		if (pesquisarDuplos(area, new String[] {"nomeAre"}, "codAre").size() > 0)
		    throw new ECARException("area.validacao.registroDuplicado");
		if(area.getCodigoIdentAre() != null)
			if (pesquisarDuplos(area, new String[] {"codigoIdentAre"}, "codAre").size() > 0)
				throw new ECARException("area.validacao.registroDuplicado.codigoIdentAre");
		super.salvar(area);
	}
	
        /**
         *
         * @param area
         * @throws ECARException
         */
        public void alterar(AreaAre area) throws ECARException {
		if (pesquisarDuplos(area, new String[] {"nomeAre"}, "codAre").size() > 0)
		    throw new ECARException("area.validacao.registroDuplicado");
		if(area.getCodigoIdentAre() != null)
			if (pesquisarDuplos(area, new String[] {"codigoIdentAre"}, "codAre").size() > 0)
				throw new ECARException("area.validacao.registroDuplicado.codigoIdentAre");
		super.alterar(area);
	}
}
