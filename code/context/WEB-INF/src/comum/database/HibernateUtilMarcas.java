package comum.database;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtilMarcas {

	private static final SessionFactory sessionFactory;
	private static final ThreadLocal thread = new ThreadLocal();
	private static Configuration configuration = null;

	static {
		try {
			configuration = new Configuration().configure("marcas.cfg.xml");
			sessionFactory = configuration.buildSessionFactory();
		} catch (HibernateException ex) { 
			ex.printStackTrace();
			throw new RuntimeException("Problema de configuração: " + ex.getMessage());
		}
	}
	/**
	 * Retorna sessao ativa.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Session
	 * @throws HibernateException
	 */
	public static Session currentSession() throws HibernateException {
		Session s = (Session) thread.get();
		if (s == null) {
			s = sessionFactory.openSession();
			s.setFlushMode(FlushMode.COMMIT);
			thread.set(s);
		} 
		return s;
	}		
	
	/**
	 * Retorna sessao ativa.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Session
	 * @throws HibernateException
	 */
	public static SessionFactory currentSessionFactory() throws HibernateException {
		return sessionFactory;
	}

	/**
	 * Fecha a Sessao.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		Session s = (Session) thread.get();
		thread.set(null);
		if (s != null) {
		    s.close();
		}
	}

	/**
	 * Limpa a sessao.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @throws HibernateException
	 */
	public static void clearSession() throws HibernateException {
		Session s = (Session) thread.get();
		if (s != null) {
		    s.clear();
		}
	}

	/**
	 * Retorna a Configuracao.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Configuration
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}
}
