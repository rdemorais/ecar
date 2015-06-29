package ecar.dao.intercambioDados;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;

import comum.database.Dao;

public class DadosTecnologiaPerfilDao extends Dao {

    /**
	 * 
	 */
    public DadosTecnologiaPerfilDao() {
	super();
    }

    /**
     * Construtor. Chama o Session factory do Hibernate
     * 
     * @param request
     */
    public DadosTecnologiaPerfilDao(HttpServletRequest request) {
	super();
	this.request = request;
    }
    
    public void salvar(Session session, Object object){
	
	session.saveOrUpdate(object);
	
    }
    
 

}
