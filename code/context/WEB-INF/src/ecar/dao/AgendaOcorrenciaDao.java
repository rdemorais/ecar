/*
 * Created on 12/05/2005
 * 
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.AgendaAge;
import ecar.pojo.AgendaOcorrenciaAgeo;
import ecar.pojo.DestaqueItemRelDtqir;
import ecar.pojo.DestaqueSubAreaDtqsa;

/**
 * @author evandro
 *
 */
public class AgendaOcorrenciaDao extends Dao{
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public AgendaOcorrenciaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
        /**
         *
         * @param request
         * @return
         * @throws ECARException
         */
        public List pesquisar(HttpServletRequest request) throws ECARException{
		try{
			String dataDe = Pagina.getParamStr(request, "dataDe");
			//String dataAte = Pagina.getParamStr(request, "dataAte");		
			String dataLimite = Pagina.getParamStr(request, "dataLimite");		
			String argumento = Pagina.getParamStr(request, "argumento");
			String ativo = Pagina.getParamStr(request, "ativo");
						
			boolean orderHoraMinuto = Pagina.getParamBool(request, "orderHoraMinuto"); 
			long codAgeo = Pagina.getParamLong(request, "codAgeo"); 
			
			String select = "select agendaOC from AgendaOcorrenciaAgeo agendaOC"; 
			String where = ""; 
			String order = "";
			
			if (codAgeo > 0) where = " where agendaOC.codAgeo=:codAgeo";
			
			
			if(!"".equals(argumento)){
				where = " where ( upper( agendaOC.agendaAge.eventoAge ) like :argumento" +
							" or upper( agendaOC.descricaoAgeo ) like :argumento" +
							" or upper( agendaOC.localAgeo ) like :argumento )";
			}
			
			if(dataDe != null && !"".equals(dataDe))	{
				if("".equals(where))
					where = " where ";
				else
					where = where + " and ";
				
				//where = where + "(agendaOC.dataEventoAgeo >= :dataDe and agendaOC.dataEventoAgeo <= :dataAte)" ;//+
				
				if(dataLimite != null && !"".equals(dataLimite)) {
					where = where + "(agendaOC.dataEventoAgeo >= :dataDe and agendaOC.dataEventoAgeo <= :dataLimite)" ;//+		
				}
				else {
					where = where + "(agendaOC.dataEventoAgeo >= :dataDe)" ;//+
				}
			}
			
			if ((ativo != null)&& !("".equals(ativo))) {
				where += " and agendaOC.agendaAge.indAtivoAge = 'S'";
			}			
			
			if (orderHoraMinuto) 
				order = " order by agendaOC.dataEventoAgeo, agendaOC.horaEventoAgeo, agendaOC.minutoEventoAgeo, agendaOC.agendaAge.eventoAge";
			else order = " order by agendaOC.dataEventoAgeo, agendaOC.agendaAge.eventoAge";
			
			Query query = this.getSession().createQuery(select + where + order);
			
			// setar somente quando os campos estiverem sendo usados na query
			if(!"".equals(argumento)){
				query.setString("argumento", "%" + argumento.toUpperCase() + "%");
			}			
			if(dataDe != null && !"".equals(dataDe))	{
				query.setDate("dataDe", Data.parseDate(dataDe));				
			}
			/*
			if(dataAte != null && !"".equals(dataAte))	{
				query.setDate("dataAte", Data.parseDate(dataAte));				
			}*/
			if(dataLimite != null && !"".equals(dataLimite)) {
				query.setDate("dataLimite", Data.parseDate(dataLimite));				
			}

			if (codAgeo > 0) {
				query.setLong("codAgeo", codAgeo);
			}
			
			return query.list();
		} catch (Exception e){
            this.logger.error(e);
            throw new ECARException(e);
		}
	}
	
	/**
	 * Retorna Coleção com as Ocorrências de uma agenda ordenadas por data
	 * @param agenda
         * @param subArea
         * @return
	 * @throws ECARException
	 */
	public Collection getAgendaOcorrenciaNaoVinculadaASubAreaOrderByData(AgendaAge agenda, DestaqueSubAreaDtqsa subArea) throws ECARException{
		try{						
			Query query = this.getSession().createQuery(
				"select agendaOC from AgendaOcorrenciaAgeo agendaOC " +
				"where agendaOC.agendaAge.codAge = :codAge " +
				"order by agendaOC.dataEventoAgeo asc");
			query.setLong("codAge", agenda.getCodAge().longValue());
	
			Collection cAgenda = query.list(); 
			List cSubArea = new ArrayList();
			
			for (Iterator it = subArea.getDestaqueItemRelDtqirs().iterator(); it.hasNext();) {
				DestaqueItemRelDtqir dtqItem = (DestaqueItemRelDtqir) it.next();
				if(dtqItem.getAgendaOcorrenciaAgeo() != null)
					cSubArea.add(dtqItem.getAgendaOcorrenciaAgeo());
			}
			cAgenda.removeAll(cSubArea);
			return cAgenda;
		}catch(HibernateException e){
			this.logger.error(e);
			throw new ECARException(e);
		}		
	}	
	
	/**
	 * Retorna um label para Exibição de um registro de AgendaOcorrencia no formato
	 * dd/mm/aaaa hh:mm descricao evento da agenda
	 * @param ocorrencia
	 * @return
	 */
	public String getLabelExibicao(AgendaOcorrenciaAgeo ocorrencia){
		String strData = Data.parseDate(ocorrencia.getDataEventoAgeo());
		String strHora = ocorrencia.getHoraEventoAgeo().toString();
		String strMinutos = ocorrencia.getMinutoEventoAgeo().toString();
		if(strMinutos.length() == 1)
			strMinutos = "0" + strMinutos;
		String strEvento = ocorrencia.getAgendaAge().getEventoAge();
		return strData + " " + strHora + ":" + strMinutos + " " + strEvento;
	}
	
	/**
	 * Método para setar Agenda em AgendaOcorrencia
	 * @param agendaOC
	 * @param agenda
	 */
	public void setAgendaOcorrencia(AgendaOcorrenciaAgeo agendaOC, AgendaAge agenda){
		agendaOC.setAgendaAge(agenda);
		agendaOC.setDataEventoAgeo(agenda.getDataAge());
		agendaOC.setDescricaoAgeo(agenda.getDescricaoAge());
		agendaOC.setHoraEventoAgeo(agenda.getHoraEventoAge());
		agendaOC.setMinutoEventoAgeo(agenda.getMinutoEventoAge());
		agendaOC.setLocalAgeo(agenda.getLocalAge());
	}
	
	/**
	 * Método para setar AgendaOcorrencia a partir dos parâmetros - Alterar 
         * @param campos
         * @param agendaOC
	 */
	public void setAgendaOcorrencia(HttpServletRequest campos, AgendaOcorrenciaAgeo agendaOC){
		if (Pagina.getParamDataBanco(campos, "dataEventoAgeo")!=null)
			agendaOC.setDataEventoAgeo(Pagina.getParamDataBanco(campos, "dataEventoAgeo"));
		if (!Pagina.getParamStr(campos, "descricaoAgeo").equals(""))
			agendaOC.setDescricaoAgeo(Pagina.getParamStr(campos, "descricaoAgeo"));
		if (! Pagina.getParamStr(campos, "horaEventoAgeo").equals(""))
			agendaOC.setHoraEventoAgeo(Integer.valueOf(Pagina.getParamStr(campos, "horaEventoAgeo")));
		if (! Pagina.getParamStr(campos, "minutoEventoAgeo").equals(""))
			agendaOC.setMinutoEventoAgeo(Integer.valueOf(Pagina.getParamStr(campos, "minutoEventoAgeo")));
		if (!Pagina.getParamStr(campos, "localAgeo").equals(""))
			agendaOC.setLocalAgeo(Pagina.getParamStr(campos, "localAgeo"));
	}
	
	/**
	 * Método para setar AgendaOcorrencia a partir dos parâmetros - Alterar 
	 * @param request
         * @param agenda
         * @throws ECARException
	 */
	public void alterarOcorrencias(HttpServletRequest request, AgendaAge agenda) throws ECARException{
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();
	
            for (Iterator it = agenda.getAgendaOcorrenciaAgeos().iterator(); it.hasNext();) {
				AgendaOcorrenciaAgeo agendaOC = (AgendaOcorrenciaAgeo) it.next();
				agendaOC.setDescricaoAgeo(Pagina.getParamStr(request, "descricaoAgeo"));
				agendaOC.setHoraEventoAgeo(Integer.valueOf(Pagina.getParamStr(request, "horaEventoAgeo")));
				agendaOC.setMinutoEventoAgeo(Integer.valueOf(Pagina.getParamStr(request, "minutoEventoAgeo")));
				agendaOC.setLocalAgeo(Pagina.getParamStr(request, "localAgeo"));
				session.update(agendaOC);
				objetos.add(agendaOC);
			}
			
			tx.commit();
	
			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("ALT");
				
				for (Iterator itObj = objetos.iterator(); itObj.hasNext();) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		} catch (HibernateException e) {
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
	        this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}
	
        /**
         *
         * @return
         * @throws ECARException
         */
        public List getAnosComAgenda() throws ECARException {
		try{
			List anos = new ArrayList();
			Query query = this.getSession().createQuery(
					"select agendaOC from AgendaOcorrenciaAgeo agendaOC order by agendaOC.dataEventoAgeo");
			
			List list = query.list(); 
			
			for (Iterator it = list.iterator(); it.hasNext();) {
				AgendaOcorrenciaAgeo ageo = (AgendaOcorrenciaAgeo) it.next();
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(ageo.getDataEventoAgeo());
				
				String ano = String.valueOf(calendar.get(Calendar.YEAR));
				
				if(!anos.contains(ano)) {
					anos.add(ano);
				}
			}

			return anos; 
		} catch (Exception e){
            this.logger.error(e);
            throw new ECARException(e);
		}
	}
	
}
