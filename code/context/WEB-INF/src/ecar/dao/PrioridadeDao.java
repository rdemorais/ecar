package ecar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.PrioridadePrior;

/**
 * Classe de manipulação de objetos da classe AtributoAtt.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Fri Jan 27 07:54:28 BRST 2006
 *
 */
public class PrioridadeDao extends Dao {
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public PrioridadeDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * verifica prioridade depois exclui
	 * @param prior
	 * @throws ECARException
	 */
	public void excluir(PrioridadePrior prior) throws ECARException {
		try {
			boolean excluir = true;
	        if (contar(prior.getRegDemandaRegds()) > 0) {
	        	excluir = false;
	            throw new ECARException("prioridade.exclusao.erro.regDemandaRegds");
	        }
			if(excluir)
				super.excluir(prior);	
		}catch(ECARException e){
			this.logger.error(e);
			throw e;
		}    
	}
	
	/**
	 * Busca
	 * @return List
	 * @throws ECARException
	 */
	public List buscar() throws ECARException {
		return super.listar((new PrioridadePrior()).getClass(), new String[] {"descricaoPrior", Dao.ORDEM_ASC});
	}
}
