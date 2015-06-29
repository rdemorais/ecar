package comum.database;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DaoUtil<pojo extends Serializable> extends Dao {
	private final Class<pojo> pojozz;
	private Transaction tx;

	/**
	    *
	    */
	public DaoUtil() {
		pojozz = (Class<pojo>) ((ParameterizedType) (getClass()
				.getGenericSuperclass())).getActualTypeArguments()[0];
	}

	/**
	 * @param hql
	 * @return
	 */
	public List<pojo> consultarHQL(String hql) {
		try {
			return HibernateUtil.currentSession().createQuery(hql)
					.setCacheable(true).setCacheMode(CacheMode.GET).list();
		} catch (final Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	// --- Métodos de consulta ---------------------
	/**
	 * @param id
	 * @return
	 */
	public pojo consultarID(Serializable id) {
		try {
			return (pojo) HibernateUtil.currentSession().get(pojozz, id);
		} catch (final Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * @param sql
	 * @return
	 */
	public List<pojo> consultarSQL(String sql) {
		try {
			return HibernateUtil.currentSession().createSQLQuery(sql)
					.setCacheable(true).setCacheMode(CacheMode.GET).list();
		} catch (final Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<pojo> consultarTodos() {
		try {
			return HibernateUtil.currentSession().createCriteria(pojozz)
					.setCacheable(true).setCacheMode(CacheMode.GET).list();
		} catch (final Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * @param orderBy
	 * @return
	 */
	public List consultarTodos(String[][] orderBy) {
		try {
			final Criteria criteria = HibernateUtil.currentSession()
					.createCriteria(pojozz);

			for (final String[] element : orderBy) {
				final String campo = element[0];
				final String ordem = element[1];

				if (campo.contains(".")) {
					final String tabela = campo
							.substring(0, campo.indexOf("."));
					criteria.createAlias(tabela, tabela);
				}

				if (ordem.toUpperCase().equals("ASC")) {
					criteria.addOrder(Order.asc(campo));
				} else if (ordem.toUpperCase().equals("DESC")) {
					criteria.addOrder(Order.desc(campo));
				}
			}

			return criteria.setCacheable(true).setCacheMode(CacheMode.REFRESH)
					.list();
		} catch (final Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	/**
	 * @param campo
	 * @return
	 */
	public List<String> listaDistinta(String campo) {
		final Criteria criteria = HibernateUtil.currentSession()
				.createCriteria(pojozz);
		criteria.setProjection(Projections
				.distinct(Projections.property(campo)));

		return criteria.list();
	}

	/**
	 * @param campo
	 * @param campoFiltro
	 * @param idFiltro
	 * @return
	 */
	public List<pojo> listaDistinta(String campo, String campoFiltro, Serializable idFiltro) {
		final Criteria criteria = HibernateUtil.currentSession()
				.createCriteria(pojozz);
		criteria.setProjection(Projections
				.distinct(Projections.property(campo)));
		criteria.add(Restrictions.eq(campoFiltro, idFiltro));

		return criteria.list();
	}

	public List<pojo> listaIN(String campoEstrangeiro, Long[] idsEstrangeiro, String[] campoFiltro, Object[] valoresFiltro) {
		
		if (campoEstrangeiro != null && !campoEstrangeiro.equals("") && idsEstrangeiro != null && idsEstrangeiro.length > 0){
			final Criteria criteria = HibernateUtil.currentSession()
			.createCriteria(pojozz);
			criteria.add(Restrictions.in(campoEstrangeiro, idsEstrangeiro));
			
			if (campoFiltro != null && valoresFiltro != null && campoFiltro.length > 0 
					&& valoresFiltro.length > 0 && campoFiltro.length == valoresFiltro.length){
				
				for (int i = 0; i < campoFiltro.length; i++){
					criteria.add(Restrictions.ilike(campoFiltro[i], valoresFiltro[i]));
				}
				
			}
			
			return criteria.list();
			
		} else {
			return new ArrayList<pojo>();
		}
		
	}
	
	/**
	 * @param campo
	 * @return
	 */
	public Long maximo(String campo) {
		final Criteria criteria = HibernateUtil.currentSession()
				.createCriteria(pojozz);
		criteria.setProjection(Projections.max(campo));

		return (Long) criteria.uniqueResult();
	}

	/**
	 * @param campoMax
	 * @param campoFiltro
	 * @param idFiltro
	 * @return
	 */
	public Long maximo(String campoMax, String campoFiltro,
			Serializable idFiltro) {
		final Criteria criteria = HibernateUtil.currentSession()
				.createCriteria(pojozz);
		criteria.setProjection(Projections.max(campoMax));
		criteria.add(Restrictions.eq(campoFiltro, idFiltro));

		return (Long) criteria.uniqueResult();
	}

	/**
	 * @param campo
	 * @return
	 */
	public Double media(String campo) {
		final Criteria criteria = HibernateUtil.currentSession()
				.createCriteria(pojozz);
		criteria.setProjection(Projections.avg(campo));

		return (Double) criteria.uniqueResult();
	}

	/**
	 * @param campoAvg
	 * @param campoFiltro
	 * @param idFiltro
	 * @return
	 */
	public Double media(String campoAvg, String campoFiltro,
			Serializable idFiltro) {
		final Criteria criteria = HibernateUtil.currentSession()
				.createCriteria(pojozz);
		criteria.setProjection(Projections.avg(campoAvg));
		criteria.add(Restrictions.eq(campoFiltro, idFiltro));

		return (Double) criteria.uniqueResult();
	}

	/**
	 * @param campo
	 * @return
	 */
	public Long minimo(String campo) {
		final Criteria criteria = HibernateUtil.currentSession()
				.createCriteria(pojozz);
		criteria.setProjection(Projections.min(campo));

		return (Long) criteria.uniqueResult();
	}

	/**
	 * @param campoMin
	 * @param campoFiltro
	 * @param idFiltro
	 * @return
	 */
	public Long minimo(String campoMin, String campoFiltro,
			Serializable idFiltro) {
		final Criteria criteria = HibernateUtil.currentSession()
				.createCriteria(pojozz);
		criteria.setProjection(Projections.min(campoMin));
		criteria.add(Restrictions.eq(campoFiltro, idFiltro));

		return (Long) criteria.uniqueResult();
	}

	/**
	 * @param campo
	 * @return
	 */
	public Long quantidade(String campo) {
		final Criteria criteria = HibernateUtil.currentSession()
				.createCriteria(pojozz);
		criteria.setProjection(Projections.count(campo));

		return (Long) criteria.uniqueResult();
	}

	/**
	 * @param campoCount
	 * @param campoFiltro
	 * @param idFiltro
	 * @return
	 */
	public Long quantidade(String campoCount, String campoFiltro,
			Serializable idFiltro) {
		final Criteria criteria = HibernateUtil.currentSession()
				.createCriteria(pojozz);
		criteria.setProjection(Projections.count(campoCount));
		criteria.add(Restrictions.eq(campoFiltro, idFiltro));

		return (Long) criteria.uniqueResult();
	}

	/**
	 * @param campo
	 * @return
	 */
	public Long quantidadeDistinta(String campo) {
		final Criteria criteria = HibernateUtil.currentSession()
				.createCriteria(pojozz);
		criteria.setProjection(Projections.countDistinct(campo));

		return (Long) criteria.uniqueResult();
	}

	/**
	 * @param campoCount
	 * @param campoFiltro
	 * @param idFiltro
	 * @return
	 */
	public Long quantidadeDistinta(String campoCount, String campoFiltro,
			Serializable idFiltro) {
		final Criteria criteria = HibernateUtil.currentSession()
				.createCriteria(pojozz);
		criteria.setProjection(Projections.countDistinct(campoCount));
		criteria.add(Restrictions.eq(campoFiltro, idFiltro));

		return (Long) criteria.uniqueResult();
	}

}
