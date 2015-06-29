package ecar.dao;

import java.util.ArrayList;
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
import ecar.pojo.PeriodoRevisaoPrev;

/*
 * Created on 23/05/2006
 * 
 */

/**
 * @author igor
 *
 */

public class PeriodoRevisaoPrevDao extends Dao{
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         * @param prev
         * @return
         * @throws ECARException
         */

	public boolean setPeriodoRevisaoPrev(HttpServletRequest request, PeriodoRevisaoPrev prev) throws ECARException 
	{
		if (!existeIntersecaoPeriodos(Pagina.getParamDataBanco(request, "dtInicioPrev"), Pagina.getParamDataBanco(request, "dtFimPrev"), prev))
		{
			if (!"".equals(Pagina.getParamStr(request, "dtInicioPrev")))
				prev.setDtInicioPrev(Pagina.getParamDataBanco(request, "dtInicioPrev"));
			else
				prev.setDtInicioPrev(null);
			if (!"".equals(Pagina.getParamStr(request, "dtInicioPrev")))
				prev.setDtFimPrev(Pagina.getParamDataBanco(request, "dtFimPrev"));
			else
				prev.setDtFimPrev(null);
			prev.setDescricaoPrev(Pagina.getParamStr(request, "descricaoPrev"));
			prev.setConfiguracaoCfg(new ConfiguracaoDao(request).getConfiguracao());
			return true;
		}	
		return false;
	}
	
	/**
	 * Contrutora
	 * @param request
	 */
	public PeriodoRevisaoPrevDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * 
	 * @param dtInicio
	 * @param dtFim
	 * @param prevComp
	 * @return boolean
	 * @throws ECARException
	 */
	public boolean existeIntersecaoPeriodos(Date dtInicio, Date dtFim, PeriodoRevisaoPrev prevComp) throws ECARException
	{
		List periodos = this.listar(PeriodoRevisaoPrev.class, new String[]{"dtInicioPrev", "ASC"});
		if (!periodos.equals(null) && periodos.size() > 0){
			Iterator itPeriodos = periodos.iterator();
			while (itPeriodos.hasNext())
			{
				PeriodoRevisaoPrev prev = (PeriodoRevisaoPrev) itPeriodos.next();
				if (!prevComp.equals(prev))
				{
					if (((!dtInicio.before(prev.getDtInicioPrev())) && (!dtInicio.after(prev.getDtFimPrev())))
				     || ((!dtFim.before(prev.getDtInicioPrev())) && (!dtFim.after(prev.getDtFimPrev()))))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param data
	 * @return boolean
	 * @throws ECARException
	 */
	public boolean existePeriodo(Date data) throws ECARException
	{
		List periodos = this.listar(PeriodoRevisaoPrev.class, new String[]{"dtInicioPrev", "ASC"});
		if (!periodos.equals(null) && periodos.size() > 0){
			Iterator itPeriodos = periodos.iterator();
			while (itPeriodos.hasNext())
			{
				PeriodoRevisaoPrev prev = (PeriodoRevisaoPrev) itPeriodos.next();
				if (!(data.before(prev.getDtInicioPrev())) && (!data.after(prev.getDtFimPrev())))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return boolean
	 * @throws ECARException
	 */
	public PeriodoRevisaoPrev getPeriodoAtual() throws ECARException
	{
		List periodos = this.listar(PeriodoRevisaoPrev.class, new String[]{"dtInicioPrev", "ASC"});
		PeriodoRevisaoPrev prev = new PeriodoRevisaoPrev();
		if (!periodos.equals(null) && periodos.size() > 0){
			Iterator itPeriodos = periodos.iterator();
			while (itPeriodos.hasNext())
			{
				prev = (PeriodoRevisaoPrev) itPeriodos.next();
				Date hoje = Data.getDataAtual();
				if ((!hoje.before(prev.getDtInicioPrev())) && (!hoje.after(prev.getDtFimPrev())))
				{
					return prev;
				}
			}
		}
		return prev;		
	}
	
        /**
         *
         * @param data
         * @return
         * @throws ECARException
         */
        public boolean estaNoPeriodoAtual(Date data) throws ECARException
	{
		PeriodoRevisaoPrev prev = this.getPeriodoAtual();
		if ((!data.before(prev.getDtInicioPrev())) && (!data.after(prev.getDtFimPrev()))){
			return true;
		}
		return false;
	}
	
	
    /**
     * Recebe um array contendo códigos de itens da estrutura e exclui todos os
     * registros. Todas os dados do item e todos os dados dos dependentes são excluidos também
     * 
     * @param codigosParaExcluir
     * @throws ECARException
     */
    public void excluir(String[] codigosParaExcluir) throws ECARException {
        Transaction tx = null;

        try{
		    ArrayList objetos = new ArrayList();

		    super.inicializarLogBean();

            tx = session.beginTransaction();

	        for (int i = 0; i < codigosParaExcluir.length; i++) {
	            PeriodoRevisaoPrev prev = (PeriodoRevisaoPrev) buscar(PeriodoRevisaoPrev.class, Long.valueOf(codigosParaExcluir[i]));
	            session.delete(prev);
				objetos.add(prev);
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

