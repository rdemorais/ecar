package ecar.dao;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.pojo.Email;
import ecar.pojo.UsuarioUsu;

/**
 *
 * @author 70744416353
 */
public class EmailDao extends Dao{
	
    /**
     *
     */
    public EmailDao() {
		super();
	}
	
    /**
     *
     * @param request
     * @param usu
     * @return
     */
    public Email setEmail(HttpServletRequest request, UsuarioUsu usu){
		try{
			Email email = new Email(usu);
			email.setCodEmail(Long.valueOf(Pagina.getParam(request,"codEmail")));
			return email;
		}
		catch(Exception e){
			logger.error(e);
			return null;
		}
	}
	
        /**
         *
         * @param list
         * @return
         */
        @SuppressWarnings("unchecked")
	public Set getLista(String[] list){
		try{
			Set lista = new HashSet();
			for (int i = 0; i < list.length; i++) {
				lista.add(this.buscar(Email.class,Long.valueOf(list[i])));
			}
			return lista;
		}
		catch(Exception e){
			logger.error(e);
			return null;
		}
	}
}
