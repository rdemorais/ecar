/*
 * Created on 20/06/2006
 * 
 */
package ecar.dao;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.RegControleAcessoRca;
import ecar.pojo.UsuarioUsu;

/**
 * @author garten
 *
 */
public class RegControleAcessoRcaDao extends Dao {
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public RegControleAcessoRcaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Retorna lista com RegControleAcessoRca
         * @param codUsuario
         * @param dataInicio
         * @param dataFim
         * @param ordenacao
         * @return
	 * @throws ECARException
	 */
	public List listar(Long codUsuario, Date dataInicio, Date dataFim, String ordenacao) throws ECARException{
		try{
			// setar hora, minuto se segundo mínimo para a data de início e máxima para a data de fim
			Calendar calendarDataInicio = Calendar.getInstance();
			Calendar calendarDataFim = Calendar.getInstance();
			
			calendarDataInicio.setTime(dataInicio);
			calendarDataInicio.set(Calendar.HOUR_OF_DAY, 0);
			calendarDataInicio.set(Calendar.MINUTE, 0);
			calendarDataInicio.set(Calendar.SECOND, 0);
			calendarDataInicio.set(Calendar.MILLISECOND, 0);
			
			calendarDataFim.setTime(dataFim);
			calendarDataFim.set(Calendar.HOUR_OF_DAY, 23);
			calendarDataFim.set(Calendar.MINUTE, 59);
			calendarDataFim.set(Calendar.SECOND, 59);
			calendarDataFim.set(Calendar.MILLISECOND, 999);
			
			String query = " from RegControleAcessoRca rca";
			
			query += " where";

			if (codUsuario != null) {
				query += " rca.usuarioUsu.codUsu = :codUsu and";
			}
			query += " rca.dataAcessoRca >= :dataInicio and rca.dataAcessoRca <= :dataFim";
			
			query += " order by rca.dataAcessoRca desc";
			
			Query q = session.createQuery(query);
	
			if (codUsuario != null) {
				q.setLong("codUsu", codUsuario.longValue());
			}
			q.setCalendar("dataInicio", calendarDataInicio);
			q.setCalendar("dataFim", calendarDataFim);
			
			List retorno = q.list();

			if("usuario".equals(ordenacao)) {
				// ordenação manualmente pois o nome do usuário está no Sentinela
			   	Collections.sort(retorno,
		            new Comparator() {
		        		public int compare(Object o1, Object o2) {		        		    	        		 
		        		    UsuarioUsu u1 = ((RegControleAcessoRca) o1).getUsuarioUsu();
		        		    UsuarioUsu u2 = ((RegControleAcessoRca) o2).getUsuarioUsu();	        		        		        
		        		    
		        		    return u1.getNomeUsuSent().compareToIgnoreCase(u2.getNomeUsuSent());	
						}
		   			}
				);
			}
			
			return retorno;

		} catch(HibernateException e) {
			this.logger.error(e);
			throw new ECARException(e);
		}  
	} 

}