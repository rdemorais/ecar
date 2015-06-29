/*
 * Created on 11/05/2005
 * 
 */
package ecar.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Query;

import comum.database.Dao;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.EfImportOcorrenciasEfio;

/**
 * @author evandro
 *
 */
public class EfImportOcorrenciasDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public EfImportOcorrenciasDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Metodo para setar os valores no objeto EfImportOcorrenciasEfio
	 * @param request
	 * @param ocorrencia
	 */
	public void setOcorrencia (HttpServletRequest request, EfImportOcorrenciasEfio ocorrencia){
		ocorrencia.setDescricaoEfio(Pagina.getParamStr(request, "descricaoEfio"));
	}
	
	/**
	 * Retorna lista de ocorrencias.
	 * @return
	 * @throws ECARException
	 */
	public List getOcorrenciasAnteriores() throws ECARException{
		List retorno = new ArrayList();
		try{
			String select = "select distinct ocorrencias.importacaoImp.dataHoraImp from EfImportOcorrenciasEfio ocorrencias" +
					" order by ocorrencias.importacaoImp.dataHoraImp desc";
			Query q = this.session.createQuery(select);
			retorno = q.list();			
		}catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
		}
		return retorno;
	} 
	
	/**
	 * Retorna uma lista de ocorrências referente à data informada no parâmetro.
	 * @param data
	 * @return
	 */
	public List getOcorrencias(Timestamp data){
		List retorno = new ArrayList();
		try{
			String select = "from EfImportOcorrenciasEfio ocorrencias";
			
			if(data != null)
				select += " where ocorrencias.importacaoImp.dataHoraImp = :data";
			
			Query q = this.session.createQuery(select);
			
			if(data != null)
				q.setTimestamp("data", data);
			
			retorno = q.list();			
		}catch (Exception e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
		}
		return retorno;
	}
}
