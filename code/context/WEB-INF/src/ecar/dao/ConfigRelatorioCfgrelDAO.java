package ecar.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.ConfigRelatorioCfgrel;

/**
 * Classe de manipulação de objetos da classe ConfigRelatorioCfgrel.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Thu Aug 03 10:21:52 BRT 2006
 *
 */
public class ConfigRelatorioCfgrelDAO extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ConfigRelatorioCfgrelDAO(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Retorna a Configuração de relatório
	 * @return ConfigRelatorioCfgrel
	 * @throws ECARException
	 */
	public ConfigRelatorioCfgrel getConfigRelatorioCfgrel() throws ECARException{
		List config = this.listar(ConfigRelatorioCfgrel.class, null);
		if(config != null && config.size() > 0)
			return (ConfigRelatorioCfgrel) config.iterator().next();
		else
			return null;
	}
}
