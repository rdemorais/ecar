/*
 * Created on 11/05/2005
 * 
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.AgendaAge;
import ecar.pojo.AgendaEntidadesAgeent;
import ecar.pojo.AgendaOcorrenciaAgeo;
import ecar.pojo.EntidadeEnt;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.SisAtributoSatb;
import ecar.pojo.UsuarioUsu;

/**
 * @author evandro
 *
 */
public class AgendaDao extends Dao{
	
    /**
     *
     */
    public static final String PRDC_UNICO = "U";
        /**
         *
         */
        public static final String PRDC_SEMANAL = "S";
        /**
         *
         */
        public static final String PRDC_QUINZENAL = "Q";
        /**
         *
         */
        public static final String PRDC_MENSAL = "M";
        /**
         *
         */
        public static final String PRDC_ANUAL = "A";
        /**
         *
         */
        public static final String PRDC_OUTRO = "O";
	
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public AgendaDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Metodo para setar os valores no objeto Agenda
	 * @param campos
	 * @param agenda
	 * @throws ECARException 
	 */
	public void setAgenda (HttpServletRequest campos, AgendaAge agenda) throws ECARException{
		
		if (request.getSession().getAttribute("seguranca")!=null)
			agenda.setUsuarioUsu(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
		
		if(Pagina.getParamDataBanco(campos, "dataAge")!=null)
	 		agenda.setDataAge(Pagina.getParamDataBanco(campos, "dataAge"));
	 	if(!Pagina.getParamStr(campos, "horaEventoAge").equals(""))
	 		agenda.setHoraEventoAge(Integer.valueOf(Pagina.getParamStr(campos, "horaEventoAge")));
	 	if(!Pagina.getParamStr(campos, "minutoEventoAge").equals(""))
	 		agenda.setMinutoEventoAge(Integer.valueOf(Pagina.getParamStr(campos, "minutoEventoAge")));
	 	if(Pagina.getParamDataBanco(campos, "dataLimiteAge")!=null)
		 	agenda.setDataLimiteAge(Pagina.getParamDataBanco(campos, "dataLimiteAge"));
	 	if(!Pagina.getParamStr(campos, "eventoAge").equals("") )
		 	agenda.setEventoAge(Pagina.getParamStr(campos, "eventoAge"));
	 	if(!Pagina.getParamStr(campos, "localAge").equals(""))
		 	agenda.setLocalAge(Pagina.getParamStr(campos, "localAge"));
	 	if(!Pagina.getParamStr(campos, "descricaoAge").equals(""))
		 	agenda.setDescricaoAge(Pagina.getParamStr(campos, "descricaoAge"));
	 	//caso de checkbox
	 	if(!Pagina.getParamStr(campos, "indAtivo").equals("")) {
	 		agenda.setIndAtivoAge(Pagina.getParamStr(campos, "indAtivo"));
	 	} else {
	 		agenda.setIndAtivoAge("N");
	 	}
		 	
	 	if(!Pagina.getParamStr(campos, "nomeContato").equals("")) 	
		 	agenda.setNomeContato(Pagina.getParamStr(campos, "nomeContato"));
	 	if(!Pagina.getParamStr(campos, "telefoneContato").equals(""))
		 	agenda.setTelefoneContato(Pagina.getParamStr(campos, "telefoneContato"));
	 	if(!Pagina.getParamStr(campos, "orgaoContato").equals(""))
		 	agenda.setOrgaoContato(Pagina.getParamStr(campos, "orgaoContato"));
	 	if(!Pagina.getParamStr(campos, "comentario").equals(""))
	   	    agenda.setComentario(Pagina.getParamStr(campos, "comentario"));
	 	// caso de checkbox
	 	if(!Pagina.getParamStr(campos, "realizado").equals("")) {
		    agenda.setRealizado(Pagina.getParamStr(campos, "realizado"));
	 	} else {
	 		agenda.setRealizado("N");
	 	}	
	 	
	 	if(!Pagina.getParamStr(campos, "exibirPortal").equals(""))
		    agenda.setExibirPortal(Pagina.getParamStr(campos, "exibirPortal"));
	 	if(Pagina.getParamDataBanco(campos, "dataRealizado")!=null)
		    agenda.setDataRealizado(Pagina.getParamDataBanco(campos, "dataRealizado"));

	 	if(!Pagina.getParamStr(campos, "codIett").equals("")){
			agenda.setItemEstruturaIett((ItemEstruturaIett) this.buscar(ItemEstruturaIett.class, Long.valueOf(Pagina.getParamLong(request,  "codIett"))));
		}
	 						
	 	if (!Pagina.getParamStr(campos, "tipoEventoSatb").equals("")){
			agenda.setTipoEventoSatb((SisAtributoSatb ) this.buscar(SisAtributoSatb.class, Long.valueOf(Pagina.getParamLong(request,  "tipoEventoSatb"))));
		} else{ 
			agenda.setTipoEventoSatb(null);
		}
	}
	 
	 
	/**
	 * Salva objeto em AgendaAge e gera ocorr�ncias em AgendaOcorrenciaAgeo
	 * 		a partir da data at� a data limite de acordo com a periodicidade escolhida.
	 * @param agenda
         * @param campos
	 * @throws ECARException
	 */
	public void salvar(AgendaAge agenda, HttpServletRequest campos) throws ECARException{
		AgendaOcorrenciaDao agendaOCDao = new AgendaOcorrenciaDao(campos);
		Transaction tx = null;
		try {
		    ArrayList objetos = new ArrayList();
			super.inicializarLogBean();

			tx = session.beginTransaction();
			
			session.save(agenda);
			objetos.add(agenda);

			ArrayList<AgendaEntidadesAgeent> listaAgendaEntidade = setAgendaEntidades(agenda);
			for (AgendaEntidadesAgeent agendaEntidade : listaAgendaEntidade) {
				session.save(agendaEntidade);
			}
			
			if (!Pagina.getParamStr(campos, "opcaoPrdc").equals("")) { 
				String opcaoPrdc = Pagina.getParamStr(campos, "opcaoPrdc");
				
				if(PRDC_UNICO.equalsIgnoreCase(opcaoPrdc)){
					/* gera uma �nica ocorr�ncia em AgendaOcorrencia igual a Agenda */
					AgendaOcorrenciaAgeo agendaOC = new AgendaOcorrenciaAgeo();
					agendaOCDao.setAgendaOcorrencia(agendaOC, agenda);
					session.save(agendaOC);
					objetos.add(agendaOC);
				}
				else if(PRDC_SEMANAL.equalsIgnoreCase(opcaoPrdc)){
					/* gera ocorr�ncia(s) at� a data limite, somente para dias da semana selecionados */
					/*    (EX.: se SEG e QUA - s�o gerados em AgendaOcorrencia da data inicial at� a data limite */
					/*          todas as SEGs e QUAs) */
					
					AgendaOcorrenciaAgeo agendaOC = new AgendaOcorrenciaAgeo();
					agendaOCDao.setAgendaOcorrencia(agendaOC, agenda);
					//session.save(agendaOC);
					//objetos.add(agendaOC);
					
					String[] diasSemana = campos.getParameterValues("diasSemanaPrdc");
	
					for(int i = 0; i < diasSemana.length; i++){
						Calendar data = Data.getGregorianCalendar(agenda.getDataAge());
						
						/* Calcular a diferen�a do dia da semana */
						if( data.get(Calendar.DAY_OF_WEEK) != (Long.valueOf(diasSemana[i])).intValue() ){
							/* Havendo diferen�a com o dia da semana selecionado verifica a diferen�a e soma encontrando o primeiro a partir do in�cio */
							int difDiaSemana = ( 7 - data.get(Calendar.DAY_OF_WEEK) ) + (Long.valueOf(diasSemana[i])).intValue();
							
							if (difDiaSemana > 7)
								difDiaSemana = difDiaSemana - 7;
							
							data.add(Calendar.DATE, difDiaSemana);
						}else{
							/* Se mesmo dia da semana, � somado uma semana, pois j� foi inclu�da a primera semana */
							data.add(Calendar.DATE, 7);
						}
						
						while(data.before(Data.getGregorianCalendar(agenda.getDataLimiteAge())) ||
									data.equals(Data.getGregorianCalendar(agenda.getDataLimiteAge()))){
							/* Para cada inclus�o, instanciar um objeto */
							
							AgendaOcorrenciaAgeo agendaOCSemanal = new AgendaOcorrenciaAgeo();
							
							agendaOCDao.setAgendaOcorrencia(agendaOCSemanal, agenda);
							agendaOCSemanal.setDataEventoAgeo(data.getTime());
							
							session.save(agendaOCSemanal);
							objetos.add(agendaOCSemanal);
							
							data.add(Calendar.DATE, 7);
						}
		            } 
				}
				else if(PRDC_QUINZENAL.equalsIgnoreCase(opcaoPrdc)){
					/* gera ocorr�ncia(s) at� a data limite, de 14 em 14 dias, */
					/*    para que sempre seja o mesmo dia da semana */
					
					Calendar data = Data.getGregorianCalendar(agenda.getDataAge());
					
					while(data.before(Data.getGregorianCalendar(agenda.getDataLimiteAge())) ||
								data.equals(Data.getGregorianCalendar(agenda.getDataLimiteAge()))){
						AgendaOcorrenciaAgeo agendaOC = new AgendaOcorrenciaAgeo();
						
						agendaOCDao.setAgendaOcorrencia(agendaOC, agenda);
						agendaOC.setDataEventoAgeo(data.getTime());
						
						session.save(agendaOC);
						objetos.add(agendaOC);
						
						data.add(Calendar.DATE, 14);
					}
				}
				else if(PRDC_MENSAL.equalsIgnoreCase(opcaoPrdc)){
					/* gera ocorr�ncia(s) at� a data limite, soma 1 m�s*/
					
					Calendar data = Data.getGregorianCalendar(agenda.getDataAge());
					
					while(data.before(Data.getGregorianCalendar(agenda.getDataLimiteAge())) ||
								data.equals(Data.getGregorianCalendar(agenda.getDataLimiteAge()))){
						AgendaOcorrenciaAgeo agendaOC = new AgendaOcorrenciaAgeo();
						
						agendaOCDao.setAgendaOcorrencia(agendaOC, agenda);
						agendaOC.setDataEventoAgeo(data.getTime());
						
						session.save(agendaOC);
						objetos.add(agendaOC);
						
						data.add(Calendar.MONTH, 1);
					}
				}
				else if(PRDC_ANUAL.equalsIgnoreCase(opcaoPrdc)){
					/* gera ocorr�ncia(s) at� a data limite, soma 1 ano */
					
					Calendar data = Data.getGregorianCalendar(agenda.getDataAge());
					
					while(data.before(Data.getGregorianCalendar(agenda.getDataLimiteAge())) ||
								data.equals(Data.getGregorianCalendar(agenda.getDataLimiteAge()))){
						AgendaOcorrenciaAgeo agendaOC = new AgendaOcorrenciaAgeo();
						
						agendaOCDao.setAgendaOcorrencia(agendaOC, agenda);
						agendaOC.setDataEventoAgeo(data.getTime());
						
						session.save(agendaOC);
						objetos.add(agendaOC);
						
						data.add(Calendar.YEAR, 1);
					}
				}
				else if(PRDC_OUTRO.equalsIgnoreCase(opcaoPrdc)){
					/* gera ocorr�ncia(s) at� a data limite, soma X dias */
					
					Calendar data = Data.getGregorianCalendar(agenda.getDataAge());
					int dias = Pagina.getParamInt(campos, "diasPrdc"); 
					
					while(data.before(Data.getGregorianCalendar(agenda.getDataLimiteAge())) ||
								data.equals(Data.getGregorianCalendar(agenda.getDataLimiteAge()))){
						AgendaOcorrenciaAgeo agendaOC = new AgendaOcorrenciaAgeo();
						
						agendaOCDao.setAgendaOcorrencia(agendaOC, agenda);
						agendaOC.setDataEventoAgeo(data.getTime());
						
						session.save(agendaOC);
						objetos.add(agendaOC);
						
						data.add(Calendar.DATE, dias);
					}
				}
			}

			tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC");
				
				for (Iterator itObj = objetos.iterator(); itObj.hasNext();) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			}
		}catch (HibernateException e) {
			if (tx != null){
				try {
					tx.rollback();
				} catch (HibernateException r) {
		            this.logger.error(r);
					throw new ECARException("erro.hibernateException"); 
				}
			}
            this.logger.error(e);
			throw new ECARException("erro.hibernateException"); 
		}
	}

	private ArrayList<AgendaEntidadesAgeent> setAgendaEntidades(AgendaAge agenda) throws ECARException {
		ArrayList<AgendaEntidadesAgeent> lista = new ArrayList<AgendaEntidadesAgeent>();
		//Salva entidades (atores) relacionadas com essa agenda 
		if(request.getParameterValues("codEnt")!=null){
			String strEntidades[] = request.getParameterValues("codEnt");
			Long codEntidade=null;//[] = new Long[strEntidades.length];
			
			for (int i = 0; i < strEntidades.length; i++) {
				codEntidade = Long.parseLong(strEntidades[i]);

				AgendaEntidadesAgeent agendaEntidade = new AgendaEntidadesAgeent();
				agendaEntidade.setAgendaAge(agenda);
				agendaEntidade.setEntidadeEnt((EntidadeEnt) this.buscar(EntidadeEnt.class, codEntidade));
				agendaEntidade.setDataInclusaoAgeent(new Date());
				agendaEntidade.setUsuarioUsuManutencao(((SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
				agendaEntidade.atribuirPKPai();
				lista.add(agendaEntidade);
			}//fim for
		}//fim if
		return lista;
	}
	
	
	/**
	 * M�todo que exclui uma AgendaOcorr�ncia, e sendo �nica exclui tamb�m Agenda
	 * 		Tamb�m ocorre a exclus�o de uma Agenda inteira, com todas AgendaOcorrencia
	 * @param agenda
	 * @param agendaOC
         * @param excluir
	 * @throws ECARException
	 */
	public void excluir (AgendaAge agenda, AgendaOcorrenciaAgeo agendaOC, String excluir) throws ECARException{
		if("todos".equalsIgnoreCase(excluir)){
			if(contar(agenda.getDestaqueItemRelDtqirs()) > 0) 
			    throw new ECARException("admPortal.agenda.exclusao.erro.destaqueItemRelDtqirs");
		    else
				super.excluir(agenda);
		}else{
			if(contar(agendaOC.getDestaqueItemRelDtqirs()) > 0)
				throw new ECARException("admPortal.agenda.exclusao.erro.destaqueItemRelDtqirs");
			else
				super.excluir(agendaOC);
		}
	}
	
	
	
	
    /**
     * Recebe um array de c�digos de ItemEstruturaAcao e exclui os registro
     * referenciados por estes c�digos
     * 
     * @param codigosParaExcluir
     * @param usuario
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir, UsuarioUsu usuario) throws ECARException {
        Transaction tx = null;

        try{
		    List<AgendaAge> objetos = new ArrayList();
		    super.inicializarLogBean();
            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	        	AgendaAge agenda = (AgendaAge) this.buscar(AgendaAge.class, Long.valueOf(codigosParaExcluir[i]));
	            agenda.setUsuarioUsu(usuario);
	            
	            session.delete(agenda);
				objetos.add(agenda);
	        }
			
			tx.commit();
	
			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("EXC");
				Iterator itObj = objetos.iterator();
	
				while(itObj.hasNext()) {
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
	 * M�todo que altera uma AgendaOcorr�ncia, 
	 * @param agenda
	 * @throws ECARException
	 */
	public void alterar (AgendaAge agenda) throws ECARException{
		super.alterar(agenda);		
	}
	
	
	
	
	/**
	 * M�todo que altera uma Agenda de fun��o de estrurara relacionada a 0:N entidades, 
	 * @param agenda
         * @param campos
	 * @throws ECARException
	 */
	public void alterar (AgendaAge agenda, HttpServletRequest campos) throws ECARException{
		
		Transaction tx = null;
		
		try {
			List objetos = new ArrayList();
			super.inicializarLogBean();
			tx = session.beginTransaction();
			
			this.setAgenda(campos, agenda);
			session.update(agenda);

			objetos.add(agenda);
			
			if (agenda.getAgendaEntidadesAgeent() != null) {
				Iterator itAgeEnt = agenda.getAgendaEntidadesAgeent().iterator();
				while (itAgeEnt.hasNext()) {
					AgendaEntidadesAgeent ageEnt = (AgendaEntidadesAgeent)itAgeEnt.next();
					session.delete(ageEnt);
					objetos.add(ageEnt);
				}
			}
			
			agenda.setAgendaEntidadesAgeent(null);
			this.setAgenda(request, agenda);
			ArrayList<AgendaEntidadesAgeent> listaAgendaEntidade = setAgendaEntidades(agenda);
			

            for (AgendaEntidadesAgeent agendaEntidade : listaAgendaEntidade) {
				session.save(agendaEntidade);
				objetos.add(agendaEntidade);
			}
			
			tx.commit();
			
//			if (super.logBean != null) {
//				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
//				super.logBean.setOperacao("INC_ALT_EXC");
//				Iterator itObj = objetos.iterator();
//
//				while (itObj.hasNext()) {
//					super.logBean.setObj(itObj.next());
//					super.loggerAuditoria.info(logBean.toString());
//				}
//			}
			
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
		} catch (ECARException e) {
			this.logger.error(e);
			if (tx != null)
				try {
					tx.rollback();
				} catch (HibernateException r) {
					this.logger.error(r);
					throw new ECARException("erro.hibernateException");
				}
			throw e;
		}
	}

	
	
	
	
	/**
	 * Retorna lista com Agendas ativas
	 * @return
	 * @throws ECARException
	 */
	public List getAtivos() throws ECARException{
		AgendaAge agenda = new AgendaAge();
		agenda.setIndAtivoAge("S");
		return super.pesquisar(agenda, new String[] {"descricaoAge","asc"});
	} 
	
	
	
		
}
