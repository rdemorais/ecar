/*
 * Created on 17/02/2005
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;

import comum.database.Dao;
import comum.util.Data;

import ecar.exception.ECARException;
import ecar.pojo.AcompRealFisicoArf;
import ecar.pojo.AcompRealFisicoLocalArfl;
import ecar.pojo.LocalItemLit;

/**
 * @author cristiano
 *
 */
public class AcompRealFisicoLocalDao extends Dao {
    /**
     * 
     * @param request
     */
    public AcompRealFisicoLocalDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
    
    /**
     * Retorna um objeto AcompRealFisicoLocalArfl a partir do seu código
     * 
     * @param codArfl
     * @return AcompRealFisicoLocalArfl
     * @throws ECARException
     */
    public AcompRealFisicoLocalArfl buscar(Long codArfl) throws ECARException {
        return (AcompRealFisicoLocalArfl) super.buscar(AcompRealFisicoLocalArfl.class, codArfl);
    }
    
    /**
	 * Exclui uma lista de AcompRealFisicoLocalArfl de um AcompRealFisicoArf e atualiza a quantidade do AcompRealFisicoArf
	 * 
     * @param listaCodArfl
     * @param codArf
     * @throws ECARException
	 */
	public void excluir(List listaCodArfl, Long codArf) throws ECARException {
		Transaction tx = null;

		try {
			if(listaCodArfl == null || listaCodArfl.isEmpty()) {
				return;
			}
		    ArrayList objetos = new ArrayList();
			super.inicializarLogBean();

			AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
			
			tx = session.beginTransaction();

			AcompRealFisicoArf arf = (AcompRealFisicoArf)acompRealFisicoDao.buscar(codArf);
			
			double qtdeRealizadaArf = 0;
			
			if(arf.getQtdRealizadaArf() != null) {
				qtdeRealizadaArf = arf.getQtdRealizadaArf().doubleValue();
			}

			for (Iterator it = listaCodArfl.iterator(); it.hasNext();) {
				AcompRealFisicoLocalArfl arfl = (AcompRealFisicoLocalArfl)this.buscar(Long.valueOf(it.next().toString()));
				
				qtdeRealizadaArf = qtdeRealizadaArf - arfl.getQuantidadeArfl().doubleValue();
				arf.setQtdRealizadaArf(Double.valueOf(qtdeRealizadaArf));
				
				session.delete(arfl);
				objetos.add(arfl);
	
				session.update(arf);
				objetos.add(arf);
			}

			tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("ALT_EXC");
				
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
	 * Insere um AcompRealFisicoLocalArfl e atualiza a quantidade do AcompRealFisicoArf
	 * 
         * @param arfl
         * @throws ECARException
	 */
	public void salvar(AcompRealFisicoLocalArfl arfl) throws ECARException {
		Transaction tx = null;

		try {
		    ArrayList objetos = new ArrayList();
			super.inicializarLogBean();

			AcompRealFisicoDao acompRealFisicoDao = new AcompRealFisicoDao(request);
			tx = session.beginTransaction();
			AcompRealFisicoArf arf = (AcompRealFisicoArf)acompRealFisicoDao.buscar(arfl.getAcompRealFisicoArf().getCodArf());
			double qtdeRealizadaArf = 0;		
			if(arf.getQtdRealizadaArf() != null) {
				qtdeRealizadaArf = arf.getQtdRealizadaArf().doubleValue();
			}
			arf.setQtdRealizadaArf(new Double(qtdeRealizadaArf + arfl.getQuantidadeArfl().doubleValue()));			
			session.save(arfl);
			objetos.add(arfl);
			session.update(arf);
			objetos.add(arf);
			tx.commit();

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC_ALT");
				
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
	 * Exclui uma lista de AcompRealFisicoLocalArfl de um AcompRealFisicoArf e atualiza a quantidade do AcompRealFisicoArf
	 * 
     * @param listaArfl
     * @param arf
	 * @throws ECARException
	 */
	public void salvar(List listaArfl, AcompRealFisicoArf arf) throws ECARException {
		Transaction tx = null;

		try {

			tx = session.beginTransaction();
			
			if(listaArfl == null || listaArfl.isEmpty()) {
				arf.setQtdRealizadaArf(null);
				session.update(arf);
			}else{

			
			Date dataAtual = new Date();
			arf.setDataUltManut(dataAtual);
			arf.setUsuarioUltManut(((ecar.login.SegurancaECAR)request.getSession().getAttribute("seguranca")).getUsuario());
			
			
			//setar os arf para os itens da lista de arfl
		    ArrayList objetos = new ArrayList();
			super.inicializarLogBean();

			// iniciando a transação


			// obtendo a quantidade total registrada no arf antes da gravação dos valores por local
			double qtdeRealizadaArf = 0;

			// salvando os arfl um a um
			for (Iterator it = listaArfl.iterator(); it.hasNext();) {
				AcompRealFisicoLocalArfl arfl = (AcompRealFisicoLocalArfl) it.next(); 
				qtdeRealizadaArf = qtdeRealizadaArf + arfl.getQuantidadeArfl().doubleValue();
				arfl.setAcompRealFisicoArf(arf);
				
				session.save(arfl);
				objetos.add(arfl);
			}

			// atualizando o valor total no arf
			arf.setQtdRealizadaArf(Double.valueOf(qtdeRealizadaArf));
			session.update(arf);
			objetos.add(arf);
			

			if(super.logBean != null) {
				super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
				super.logBean.setOperacao("INC_ALT");
				
				for (Iterator itObj = objetos.iterator(); itObj.hasNext();) {
					super.logBean.setObj(itObj.next());
					super.loggerAuditoria.info(logBean.toString());
				}
			  }			
			}
			// encerrando a transação
			tx.commit();

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
	 * Retorna uma lista de locais de um AcompRealFisicoArf.
	 * @author aleixo
	 * @since 0.1 14/03/2007
	 * @version 0.1 14/03/2007
         * @param arf
	 * @return List de AcompRealFisicoLocalArfl se achar algum item; new ArrayList() se não achar nenhum.
	 */
	public List getAcompRealFisicoLocalByArf(AcompRealFisicoArf arf){
		List retorno = new ArrayList();
		Query q = this.session.createQuery("select arfl from AcompRealFisicoLocalArfl arfl where arfl.acompRealFisicoArf.codArf = :codArf");
		q.setLong("codArf", arf.getCodArf().longValue());
		retorno = q.list();
		return retorno!=null?retorno:new ArrayList();
	}

	/**
	 * Retorna uma lista de locais de um AcompRealFisicoArf.
	 * @author aleixo
	 * @since 0.1 14/03/2007
	 * @version 0.1 14/03/2007
         * @param arf
         * @param lit
         * @return List de AcompRealFisicoLocalArfl se achar algum item; new ArrayList() se não achar nenhum.
	 */
	public AcompRealFisicoLocalArfl getAcompRealFisicoLocalByLocal(AcompRealFisicoArf arf, LocalItemLit lit){


        try {
        	
        	StringBuilder sb = new StringBuilder();
        	sb.append("from AcompRealFisicoLocalArfl bean");
        	sb.append(" where ");
        	sb.append(" bean.acompRealFisicoArf.codArf = :codArf ");
        	sb.append(" and bean.localItemLit.codLit = :codLitl ");
        	
            Query query = session.createQuery(sb.toString());
            
            query.setLong("codArf", arf.getCodArf().longValue());
            query.setLong("codLitl", lit.getCodLit().longValue());
            
            Object retorno = query.uniqueResult();
            
            if ( retorno != null && retorno instanceof AcompRealFisicoLocalArfl ){
            	return (AcompRealFisicoLocalArfl) retorno;
            }
            
        } catch (HibernateException e) {
        	e.printStackTrace(System.out);
        	this.logger.error(e);
            AcompRealFisicoLocalArfl  arfl = new AcompRealFisicoLocalArfl();
            arfl.setLocalItemLit(lit);
            arfl.setQuantidadeArfl(0.0);
            arfl.setAcompRealFisicoArf(arf);
            
            return arfl;        	
        }
        
        AcompRealFisicoLocalArfl  arfl = new AcompRealFisicoLocalArfl();
        arfl.setLocalItemLit(lit);
//        arfl.setQuantidadeArfl(0.0);
        arfl.setAcompRealFisicoArf(arf);
        
        return arfl;

	}
    /**
     * Conta a quantidade de registros de realizados por local existentes 
     * dada uma lista de locais e um código de item de estrutura.
     * 
     * @param codigosDeLocais - Lista com os códigos de locais
     * @param codIett         - Código do Item de estrutura
     * @return
     */
    public Long countByLocaisItemEstrutura(List<Long> codigosDeLocais, Long codIett){
        try
        {	
        	StringBuilder sb = new StringBuilder(  );
        	sb.append( " select count(*) from AcompRealFisicoLocalArfl as ARFL " );			
			sb.append( " where ARFL.localItemLit.codLit in (:locais) " );			
			sb.append( " and ARFL.acompRealFisicoArf.itemEstrtIndResulIettr.itemEstruturaIett.codIett = :codIett " );
			Query query = this.getSession().createQuery(sb.toString());
			query.setParameterList("locais", codigosDeLocais);
			query.setLong("codIett", codIett);
			query.setMaxResults(1);

			Long valor = (Long)query.uniqueResult();
			
			return valor;
        
        } catch ( HibernateException e )
        {
            e.printStackTrace( System.out );
            this.logger.error( e );

            return 0l;
        }
    }
    
    public void excluirLocaleARF(AcompRealFisicoArf arf){
    	Transaction tx = null;
	    ArrayList objetos = new ArrayList();
	    
        try{    	
		// iniciando a transação
		tx = session.beginTransaction();
		
		for(Object obj : arf.getAcompRealFisicoLocalArfls()){
			AcompRealFisicoLocalArfl arflocal = (AcompRealFisicoLocalArfl) obj;
			
			session.delete(arflocal);
			objetos.add(arflocal);

		}
		
		tx.commit();

		if(super.logBean != null) {
			super.logBean.setCodigoTransacao(Data.getHoraAtual(false));
			super.logBean.setOperacao("ALT_EXC");
		
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
        			//throw new ECARException("erro.hibernateException"); 
        		}
       		this.logger.error(e);
//       		throw new ECARException("erro.hibernateException"); 
        }
    }		
		
}