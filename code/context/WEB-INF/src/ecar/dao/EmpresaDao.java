/*
 * Created on 16/11/2004
 * 
 */
package ecar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.EmpresaEmp;

/**
 * @author evandro
 *
 */
public class EmpresaDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public EmpresaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	
	/**
	 * testa antes de excluir empresa 
	 * @author n/c
	 * @param empresa
	 * @throws ECARException
	 */
	public void excluir(EmpresaEmp empresa) throws ECARException {	    
	   try{
	       	boolean excluir = true;
		    if(contar(empresa.getTextosSiteTxts()) > 0){ 
		        excluir = false;
			    throw new ECARException("empresa.exclusao.erro.textosSiteTxts");
		    }   
	
		    if(excluir)
		        super.excluir(empresa);	
	   }catch(ECARException e){
		   this.logger.error(e);
		   throw e;
	   }    
	}
	
	public EmpresaEmp buscarConfiguracoesEmpresa(){
		StringBuilder hql = new StringBuilder();
		hql.append("select new ecar.pojo.EmpresaEmp(e.logotipoEmp) ");
		hql.append("from EmpresaEmp e ");
		
		List<EmpresaEmp> lista = this.consultarPorHQL(hql.toString());
		
		return lista.get(0);
	}
}