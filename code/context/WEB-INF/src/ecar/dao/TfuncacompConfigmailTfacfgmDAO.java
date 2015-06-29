package ecar.dao;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.TfuncacompConfigmailTfacfgm;
import ecar.pojo.TfuncacompConfigmailTfacfgmPK;

/**
 * Classe de manipulação de objetos da classe TfuncacompConfigmailTfacfgm.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Thu Apr 06 14:20:07 BRT 2006
 *
 */
public class TfuncacompConfigmailTfacfgmDAO extends Dao {
	
	/**
	 * 
	 * @param pk
	 * @return TfuncacompConfigmailTfacfgm
	 * @throws ECARException
	 */
	public TfuncacompConfigmailTfacfgm obter(TfuncacompConfigmailTfacfgmPK pk) throws ECARException {
		TfuncacompConfigmailTfacfgm obj;
		
		try {
			obj = (TfuncacompConfigmailTfacfgm) super.buscar(TfuncacompConfigmailTfacfgm.class, pk);
		} catch(Exception e) {
			obj = null;
		}
		
		return obj;
	} 
	
}
