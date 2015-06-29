package ecar.dao;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.ConfigMailCfgm;

/**
 * @author rogeriom
 * @version 0.1, 13/04/2006
 */
public class ConfigMailCfgmDAO extends Dao {
	
    /**
     *
     */
    public ConfigMailCfgmDAO() {
		super();
	}
	
        /**
         *
         * @param request
         */
        public ConfigMailCfgmDAO(HttpServletRequest request) {
		super();
		this.request = request;
	}

	/**
	 * Lista os itens de configuração.
	 * @return List
	 * @throws ECARException
	 */
	public List listar() throws ECARException {
			return this.listar(ConfigMailCfgm.class, null);
	}
	
	/**
	 * Retorna lista de Configurações de Emails Ativos.
	 * @return List
	 * @throws ECARException
	 */
	public List getMailsAtivos() throws ECARException {
		
		
		List list = this.listar();
		
		for (Iterator it = list.iterator(); it.hasNext();) {
			ConfigMailCfgm configMailCfgm = (ConfigMailCfgm) it.next();
					
			if( !"S".equals(configMailCfgm.getAtivoCfgm()) )  {
				it.remove();
			}
		}
		
		return list;
	}
}
