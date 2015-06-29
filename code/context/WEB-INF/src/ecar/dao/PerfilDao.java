/*
 * Created on 01/12/2004
 * 
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.OpcaoOpc;
import ecar.pojo.PerfilPfl;

/**
 * @author evandro
 *
 */
public class PerfilDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public PerfilDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	/**
	 * Verifica depois exclui
	 * 
	 * @param perfil
	 * @throws ECARException
	 */
	public void excluir(PerfilPfl perfil) throws ECARException {	    
	   try{
	       	boolean excluir = true;
		    if(contar(perfil.getOpcaoPerfilOpcps()) > 0){ 
		        excluir = false;
			    throw new ECARException("perfil.exclusao.erro.opcaoPerfilOpcps");
		    } 

		    if(excluir)
		        super.excluir(perfil);	
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}
	
	/**
	 * Lista Opções possíveis de serem incluídas no perfil
	 * excluindo as que já estão relacionadas a ele
         *
         * @param perfil
         * @return
         * @throws ECARException
         */
	public List getOpcoesDisponiveis(PerfilPfl perfil) throws ECARException {
	    List opcoes = new ArrayList();
	    
	    if(perfil != null){
	    	try {
	    		opcoes = this.listar(OpcaoOpc.class, new String[] {"descricaoOpc","asc"});
	    		opcoes.removeAll(perfil.getOpcaoPerfilOpcps());
	    	} catch (ECARException e) {
	    		this.logger.error(e);
				throw e;
			}
	    }
	    return opcoes;
	}
}