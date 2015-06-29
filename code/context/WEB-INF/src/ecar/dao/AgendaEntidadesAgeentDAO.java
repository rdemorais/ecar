package ecar.dao;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;
import comum.util.Pagina;

import ecar.exception.ECARException;
import ecar.pojo.AgendaAge;
import ecar.pojo.AgendaEntidadesAgeent;
import ecar.pojo.AgendaEntidadesAgeentPK;
import ecar.pojo.EntidadeEnt;

/**
 * Classe de manipulação de objetos da classe AgendaEntidadesAgeent.
 * 
 * 
 */
public class AgendaEntidadesAgeentDAO extends Dao{
	

    /**
     *
     * @param request
     */
    public AgendaEntidadesAgeentDAO(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
        /**
         *
         * @param request
         * @param agendaEntidades
         * @throws ECARException
         */
        public void setAgendaEntidadesAgeentDAO(HttpServletRequest request, AgendaEntidadesAgeent agendaEntidades ) throws ECARException {
        agendaEntidades.setAgendaAge( (AgendaAge) this.buscar(AgendaAge.class, Long.valueOf(Pagina.getParamStr(request, "codAge"))) );
        agendaEntidades.setEntidadeEnt((EntidadeEnt) new EntidadeDao (request).buscar( EntidadeEnt.class, Long.valueOf(Pagina.getParamStr(request, "codEnt")))) ;
    }
	
    /**
     * Método utilizado para setar os valores da PK da classe
     * 
     * @param agendaEntidade
     */
    public void setPK(AgendaEntidadesAgeent agendaEntidade) {
        AgendaEntidadesAgeentPK chave = new AgendaEntidadesAgeentPK();
        
        chave.setCodAge((Long.valueOf(agendaEntidade.getAgendaAge().getCodAge().longValue())));
        chave.setCodEnt((Long.valueOf(agendaEntidade.getEntidadeEnt().getCodEnt().longValue())));
        
        agendaEntidade.setComp_id(chave);
    }
    
    /**
     * Grava uma relação entre Agenda e Entidade
     * 
     * @param agendaEntidade
     * @throws ECARException
     */
    public void salvar(AgendaEntidadesAgeent agendaEntidade) throws ECARException {
        setPK(agendaEntidade);
        try {
            if (buscar(AgendaEntidadesAgeent.class, agendaEntidade.getComp_id()) != null)
            	throw new ECARException("agenda.entidade.inclusao.jaExiste");
        } catch (ECARException e) {
        	this.logger.error(e);
            if (e.getMessageKey().equalsIgnoreCase("erro.objectNotFound")) {
                super.salvar(agendaEntidade);
            } else
                /* joga para frente a inclusao.jaExiste */
                throw e;
        }
    }
    
    /**
     * Recebe o código da agenda e um array contendo códigos de
     * entidades e exclui todos os registros que relacionam esta 
     * agenda com cada um dos códigos das enntidades
     * 
     * @param codigosParaExcluir
     * @param codAgenda
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir, Long codAgenda) throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	        	AgendaEntidadesAgeentPK chave = new AgendaEntidadesAgeentPK(Long.valueOf(codigosParaExcluir[i]), codAgenda);
	            AgendaEntidadesAgeent agendaEntidade = (AgendaEntidadesAgeent) buscar(AgendaEntidadesAgeent.class, chave);
	            
	            session.delete(agendaEntidade);
				objetos.add(agendaEntidade);
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
}
