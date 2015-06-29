package ecar.dao;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.ModeloRelatorioMrel;

/**
 * Classe de manipula��o de objetos da classe ModeloRelatorioMrel.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Thu Aug 03 10:21:52 BRT 2006
 *
 */
public class ModeloRelatorioMrelDAO extends Dao {
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ModeloRelatorioMrelDAO(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * 
	 * @param codAlfa
	 * @return ModeloRelatorioMrel
	 * @throws ECARException
	 */
	public ModeloRelatorioMrel getModeloRelatorioByCodAlfa(String codAlfa) throws ECARException{
		ModeloRelatorioMrel retorno = null;
		List modelos = this.listar(ModeloRelatorioMrel.class, new String[] {"codAlfaMrel", "asc"});
		
		if(modelos != null && !modelos.isEmpty()){
			Iterator it = modelos.iterator();
			while(it.hasNext()){
				ModeloRelatorioMrel mrel = (ModeloRelatorioMrel) it.next();
				if(codAlfa.equalsIgnoreCase(mrel.getCodAlfaMrel())){
					retorno = mrel;
					break;
				}
			}
		}
		
		return retorno;
	}
}
