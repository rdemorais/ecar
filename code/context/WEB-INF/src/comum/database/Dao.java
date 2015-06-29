package comum.database;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.log4j.Logger;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import comum.util.Data;
import comum.util.LogBean;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.login.SegurancaECAR;
import ecar.pojo.AvaliaMetodo;
import ecar.pojo.PaiFilho;

/**
 * Classe para extens�o das classes do tipo DAO.<BR>
 * Implementa as caracter�sticas b�sicas de manuten��o de objetos utilizando
 * Hibernate.<BR>
 * Pode ser estendida para classes xxxxDao onde xxxx � o nome da classe que
 * precisa implementar m�todos mais espec�ficos.
 */
public class Dao<T> {

	/* constantes para ordenacao */
	/**
     *
     */
	public static final String ORDEM_ASC = "asc";
	/**
         *
         */
	public static final String ORDEM_DESC = "desc";
	/**
         *
         */
	protected Logger logger = null;
	/**
         *
         */
	protected Logger loggerAuditoria = null;
	/**
         *
         */
	protected LogBean logBean = null;
	/**
         *
         */
	protected Filter filtro = null;

	/* objeto de sessao */
	/**
         *
         */
	protected Session session;

	/**
         *
         */
	protected HttpServletRequest request = null;

	/**
	 * Construtor Dao.<br>
	 * Tenta abrir uma conex�o com o banco de dados pela SessionFactory do
	 * Hibernate.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public Dao() {
		try {
			session = HibernateUtil.currentSession();

			logger = Logger.getLogger(this.getClass());
		} catch (HibernateException e) {
			this.logger.error(e);
		} catch (Exception e) {
			this.logger.error(e);
		}
	}

	/**
	 * Cria um Dao com um sess�o j� criada.
	 * 
	 * @param session
	 */
	public Dao(Session session) {
		this.session = session;
	}

	public Dao(String cfg) {
		try {
			session = HibernateUtilMarcas.currentSession();

			logger = Logger.getLogger(this.getClass());
		} catch (HibernateException e) {
			this.logger.error(e);
		} catch (Exception e) {
			this.logger.error(e);
		}
	}

	/**
	 * Retorna a sess�o corrente.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return Session
	 */
	public Session getSession() {
		return session;
	}

	/**
	 * Atualiza a sess�o corrente com uma nova sess�o.<br>
	 * 
	 * @param session
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	/**
	 * Devolve o tamanho de uma collection sem inicializ�-la.<br>
	 * 
	 * @param col
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @return int
	 * @throws ECARException
	 */
	@SuppressWarnings("rawtypes")
	public int contar(Collection col) throws ECARException {
		try {
			if (col != null) {
				Object obj = session.createFilter(col, "select count(*)").iterate().next();
				if (obj instanceof Long) {
					return ((Long) obj).intValue();
				} else {
					return ((Integer) obj).intValue();
				}
			} else {
				return -1;
			}

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Insere um objeto utilizando uma transa��o do Hibernate.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param obj
	 * @throws ECARException
	 *             - executa o rollback da transa��o e dispara e exception
	 */
	public void salvar(Object obj) throws ECARException {
		inicializarLogBean();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(obj);
			tx.commit();
			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setObj(obj);
				logBean.setOperacao("INC");
				loggerAuditoria.info(logBean.toString());
			}
		} catch (HibernateException e) {
			e.printStackTrace();
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
	 * Insere um objeto pai e uma cole��o de objetos filhos utilizando uma
	 * transa��o do Hibernate.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param obj
	 * @param lista
	 * @throws ECARException
	 *             - executa o rollback da transa��o e dispara e exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void salvar(Object obj, Collection lista) throws ECARException {
		inicializarLogBean();
		Transaction tx = null;
		try {
			ArrayList objetosInseridos = new ArrayList();
			tx = session.beginTransaction();

			// salva o pai
			session.save(obj);
			objetosInseridos.add(obj);

			Iterator it = lista.iterator();
			while (it.hasNext()) {
				PaiFilho object = (PaiFilho) it.next();
				object.atribuirPKPai();
				// salva os filhos
				session.save(object);
				objetosInseridos.add(object);
			}
			tx.commit();

			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("INC");
				Iterator it2 = objetosInseridos.iterator();

				while (it2.hasNext()) {
					logBean.setObj(it2.next());
					loggerAuditoria.info(logBean.toString());
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
	 * @param objs
	 * @param lista
	 * @throws ECARException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void salvar(Object[] objs, Collection lista) throws ECARException {
		inicializarLogBean();
		Transaction tx = null;
		try {
			ArrayList objetosInseridos = new ArrayList();
			tx = session.beginTransaction();

			for (Object obj : objs) {
				session.save(obj);
				objetosInseridos.add(obj);
			}

			Iterator it = lista.iterator();
			while (it.hasNext()) {
				PaiFilho object = (PaiFilho) it.next();
				object.atribuirPKPai();
				// salva os filhos
				session.save(object);
				objetosInseridos.add(object);
			}
			tx.commit();

			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("INC");
				Iterator it2 = objetosInseridos.iterator();

				while (it2.hasNext()) {
					logBean.setObj(it2.next());
					loggerAuditoria.info(logBean.toString());
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
	 * Salva uma lista de objetos.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param objs
	 * @throws ECARException
	 *             - executa o rollback da transa��o e dispara e exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void salvar(Object[] objs) throws ECARException {
		inicializarLogBean();
		Transaction tx = null;
		try {
			ArrayList objetosInseridos = new ArrayList();
			tx = session.beginTransaction();

			for (Object obj : objs) {
				session.save(obj);
				objetosInseridos.add(obj);
			}

			tx.commit();

			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("INC");
				Iterator it2 = objetosInseridos.iterator();

				while (it2.hasNext()) {
					logBean.setObj(it2.next());
					loggerAuditoria.info(logBean.toString());
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
	 * Salva um objeto utilizando uma transa��o do Hibernate.<br>
	 * 
	 * @param obj
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @throws ECARException
	 *             - executa o rollback da transa��o e dispara e exception
	 */
	public void salvarOuAlterar(Object obj) throws ECARException {
		inicializarLogBean();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(obj);
			tx.commit();

			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setObj(obj);
				logBean.setOperacao("INC_ALT");
				loggerAuditoria.info(logBean.toString());
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
			e.printStackTrace();
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Salva um objeto pai e uma cole��o de objetos filhos utilizando uma
	 * transa��o do Hibernate.<br>
	 * 
	 * @param lista
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @throws ECARException
	 *             - executa o rollback da transa��o e dispara e exception
	 */
	@SuppressWarnings("rawtypes")
	public void salvarOuAlterar(Collection lista) throws ECARException {
		inicializarLogBean();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();

			Iterator it = lista.iterator();
			while (it.hasNext()) {
				session.saveOrUpdate(it.next());
			}
			tx.commit();

			if (logBean != null) {
				it = lista.iterator();
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("INC_ALT");
				while (it.hasNext()) {
					logBean.setObj(it.next());
					loggerAuditoria.info(logBean.toString());
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
	 * Altera um objeto utilizando uma transa��o do Hibernate.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param obj
	 * @throws ECARException
	 *             - executa o rollback da transa��o
	 */
	public void alterar(Object obj) throws ECARException {
		inicializarLogBean();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();
			session.update(obj);
			tx.commit();
			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setObj(obj);
				logBean.setOperacao("ALT");
				loggerAuditoria.info(logBean.toString());
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
			e.printStackTrace();
			throw new ECARException("erro.hibernateException");
		}
	}

	/**
	 * Altera um objeto pai e salva uma cole��o de Filhos utilizando uma
	 * transa��o do Hibernate.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param obj
	 * @param lista
	 * @throws ECARException
	 *             - executa o rollback da transa��o
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void alterar(Object obj, Collection lista) throws ECARException {
		inicializarLogBean();
		Transaction tx = null;
		try {
			ArrayList objetosAlterados = new ArrayList();

			tx = session.beginTransaction();
			session.update(obj);
			objetosAlterados.add(obj);

			Iterator it = lista.iterator();
			while (it.hasNext()) {
				PaiFilho object = (PaiFilho) it.next();
				object.atribuirPKPai();
				// salva os filhos
				session.save(object);
				objetosAlterados.add(object);
			}
			tx.commit();

			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("ALT");
				Iterator it2 = objetosAlterados.iterator();

				while (it2.hasNext()) {
					logBean.setObj(it2.next());
					loggerAuditoria.info(logBean.toString());
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
	 * Altera uma cole��o de objetos utilizando uma transa��o do Hibernate.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param lista
	 * @throws ECARException
	 *             - executa o rollback da transa��o e dispara e exception
	 */
	@SuppressWarnings("rawtypes")
	public void alterar(Collection lista) throws ECARException {
		inicializarLogBean();
		Transaction tx = null;
		try {

			tx = session.beginTransaction();

			Iterator it = lista.iterator();
			while (it.hasNext()) {
				session.update(it.next());
			}
			tx.commit();

			if (logBean != null) {
				it = lista.iterator();
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("ALT");
				while (it.hasNext()) {
					logBean.setObj(it.next());
					loggerAuditoria.info(logBean.toString());
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
	 * Exclui um objeto passando a classe e a chave como par�metro.<br>
	 * Primeiro carrega o objeto na sess�o e depois cria uma transa��o para
	 * excluir.<br>
	 * 
	 * @param obj
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @throws ECARException
	 *             - executa o rollback da transa��o
	 */
	public void excluir(Object obj) throws ECARException {
		inicializarLogBean();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(obj);
			tx.commit();
			if (logBean != null) {
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setObj(obj);
				logBean.setOperacao("EXC");
				loggerAuditoria.info(logBean.toString());
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
	 * Exclui uma lista de objetos. A lista deve estar na ordem tal que n�o gere
	 * viola��o de integridade.<br>
	 * 
	 * @param lista
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @throws ECARException
	 *             - executa o rollback da transa��o
	 */
	@SuppressWarnings("rawtypes")
	public void excluir(Collection lista) throws ECARException {
		inicializarLogBean();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Iterator it = lista.iterator();
			while (it.hasNext())
				session.delete(it.next());
			tx.commit();

			if (logBean != null) {
				it = lista.iterator();
				logBean.setCodigoTransacao(Data.getHoraAtual(false));
				logBean.setOperacao("EXC");
				while (it.hasNext()) {
					logBean.setObj(it.next());
					loggerAuditoria.info(logBean.toString());
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
	 * Busca um objeto do banco de dados pela chave. Usando o load<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param cl
	 * @param chave
	 * @return Object - um objeto reencarnado do banco de dados da classe
	 *         informada de um objeto.(Segundo a Parapsicologia Orientada a
	 *         Objetos isso � poss�vel.)
	 * @throws ECARException
	 *             - NAO tem rollback
	 */
	@SuppressWarnings("rawtypes")
	public Object buscar(Class cl, Serializable chave) throws ECARException {
		Object obj = null;
		try {
			/*
			 * aten��o. o session.load joga o objeto para o cache interno do
			 * hibernate
			 */
			/* TODO descobrir a melhor maneira de lidar com o cache do hibernate */
			/*
			 * isto �, quando deve ser aproveitado o cache e quando ele deve ser
			 * limpo
			 */
			/*
			 * para evitar que ocorra uma carga desatualizada de um objeto do
			 * cache
			 */
			/*
			 * descobrir que metodos do hibernate l�em o cache e quais gravam no
			 * cache
			 */

			obj = session.load(cl, chave);
		} catch (ObjectNotFoundException e) {
			// this.logger.error(e);
			throw new ECARException("erro.objectNotFound", e);
		} catch (HibernateException e) {
			this.logger.error(e);
			e.printStackTrace();
			throw new ECARException("erro.hibernateException");
		} catch (Exception e) {
			this.logger.error(e);
			throw new ECARException("erro.exception");
		}
		return (obj);
	}

	/**
	 * Busca um objeto do banco de dados pela chave. Usando o load<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param _object
	 * @param chave
	 * @return Object - um objeto reencarnado do banco de dados da classe
	 *         informada de um objeto.(Segundo a Parapsicologia Orientada a
	 *         Objetos isso � poss�vel.)
	 * @throws ECARException
	 *             - NAO tem rollback
	 */
	public void buscar(Object _object, Serializable chave) throws ECARException {
		try {
			/*
			 * aten��o. o session.load joga o objeto para o cache interno do
			 * hibernate
			 */
			/* TODO descobrir a melhor maneira de lidar com o cache do hibernate */
			/*
			 * isto �, quando deve ser aproveitado o cache e quando ele deve ser
			 * limpo
			 */
			/*
			 * para evitar que ocorra uma carga desatualizada de um objeto do
			 * cache
			 */
			/*
			 * descobrir que metodos do hibernate l�em o cache e quais gravam no
			 * cache
			 */

			session.load(_object, chave);
		} catch (ObjectNotFoundException e) {
			// this.logger.error(e);
			throw new ECARException("erro.objectNotFound", e);
		} catch (HibernateException e) {
			this.logger.error(e);
			e.printStackTrace();
			throw new ECARException("erro.hibernateException");
		} catch (Exception e) {
			this.logger.error(e);
			throw new ECARException("erro.exception");
		}
	}

	/**
	 * Busca um objeto do banco de dados pela chave. usando o Get<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param cl
	 * @param chave
	 * @return Object - um objeto reencarnado do banco de dados da classe
	 *         informada de um objeto.(Segundo a Parapsicologia Orientada a
	 *         Objetos isso � poss�vel.)
	 * @throws ECARException
	 *             - NAO tem rollback
	 */
	@SuppressWarnings("rawtypes")
	public Object localizar(Class cl, Serializable chave) throws ECARException {
		Object obj = null;
		try {
			/*
			 * aten��o. o session.load joga o objeto para o cache interno do
			 * hibernate
			 */
			/* TODO descobrir a melhor maneira de lidar com o cache do hibernate */
			/*
			 * isto �, quando deve ser aproveitado o cache e quando ele deve ser
			 * limpo
			 */
			/*
			 * para evitar que ocorra uma carga desatualizada de um objeto do
			 * cache
			 */
			/*
			 * descobrir que metodos do hibernate l�em o cache e quais gravam no
			 * cache
			 */

			obj = session.get(cl, chave);
		} catch (ObjectNotFoundException e) {
			// this.logger.error(e);
			throw new ECARException("erro.objectNotFound");
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		} catch (Exception e) {
			this.logger.error(e);
			e.printStackTrace();
			throw new ECARException("erro.exception");
		}
		return (obj);
	}

	/**
	 * M�todo para pesquisar objetos.<br>
	 * Recebe um objeto a pesquisar e um array de ordena��es e devolve uma lista
	 * de objetos do tipo informado.<br>
	 * Os atributos a pesquisar devem estar setados no objeto antes da chamada.<br>
	 * Exemplo:<br>
	 * Pessoa p = new Pessoa();<br>
	 * p.setNome("Joao%"); // argumento de pesquisa. o metodo vai pesquisar por
	 * nome.<br>
	 * List l1 = PessoaDao.pesquisar(p, new String[]{"nome","asc"}); // ordena
	 * por nome ascendente.<br>
	 * sempre passar o array de ordenacao aos pares campo e ordem.<br>
	 * List l2 = PessoaDao.pesquisar(p, null); // nenhuma ordenacao ou ordem
	 * natural.<br>
	 * <p>
	 * Esse m�todo deve ser utilizado preferencialmente para as classes POJO que
	 * contenham<br>
	 * m�todos get e seus respectivos atributos, porque utiliza essa estrutura
	 * para descobrir<br>
	 * os atributos que est�o preenchidos com os argumentos da pesquisa.<br>
	 * <p>
	 * Utiliza a expressao like para realizar a pesquisa em cada um dos
	 * atributos preenchidos.<br>
	 * Para funcionar corretamente os atributos que nao devem entrar na pesquisa
	 * precisam conter null.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param obj
	 * @param ordem
	 * @return List - lista de objetos do tipo pesquisado
	 * @throws ECARException
	 */
	@SuppressWarnings("rawtypes")
	public List pesquisar(Object obj, String[] ordem) throws ECARException {

		List list = new ArrayList(); // lista resultado
		Criteria select; // select (do hibernate)

		if (obj == null)
			return list;

		try {
			/*
			 * limpa os objeto do cache antes de buscar, para garantir que a
			 * busca ser� no BD e nao no cache
			 */
			/* TODO avaliar e utilizacao do cache */
			// clearSession();

			select = session.createCriteria(obj.getClass());
			List listaMetodos = Util.listaMetodosGet(obj);
			Object auxObj = null;
			String nomeAtributo;
			String nomeMetodo;
			String tipoRetorno;
			AvaliaMetodo avaliaMetodo;
			for (int i = 0; i < listaMetodos.size(); i++) {
				if (((Method) listaMetodos.get(i)).getParameterTypes().length == 0) {
					avaliaMetodo = ((Method) listaMetodos.get(i)).getAnnotation(AvaliaMetodo.class);
					if (avaliaMetodo != null && !avaliaMetodo.valida()) {
						auxObj = null;
					} else {
						auxObj = ((Method) listaMetodos.get(i)).invoke(obj, null);
					}
				}
				// somente adiciona um criterio se o conteudo for != vazio
				if (auxObj != null) {
					// obtem o nome do m�todo para retirar o nome do atributo
					nomeMetodo = ((Method) listaMetodos.get(i)).getName();
					if (nomeMetodo.equals("getNomeUsuSent")) {
						continue;
					}
					tipoRetorno = ((Method) listaMetodos.get(i)).getReturnType().getName().toLowerCase();
					nomeAtributo = nomeMetodo.substring(3, 4).toLowerCase() + nomeMetodo.substring(4);
					avaliaMetodo = ((Method) listaMetodos.get(i)).getAnnotation(AvaliaMetodo.class);

					if (tipoRetorno.endsWith("string")) {
						select.add(Expression.ilike(nomeAtributo, "%" + auxObj + "%"));
					} else if (avaliaMetodo != null && avaliaMetodo.valida() && tipoRetorno.endsWith("boolean")) {
						select.add(Expression.eq(nomeAtributo, auxObj));
					} else {
						// Se o atributo for Set n�o entra na pesquisa ser�
						// filtrado depois
						if (avaliaMetodo == null) {
							if (!tipoRetorno.endsWith("set"))
								select.add(Expression.eq(nomeAtributo, auxObj));
						} else {
							if (avaliaMetodo.valida()) {
								if (!tipoRetorno.endsWith("set"))
									select.add(Expression.eq(nomeAtributo, auxObj));
							}
						}
					}
				}
			}

			if (ordem != null)
				for (int i = 0; i < ordem.length; i += 2)
					// anda aos pares
					if (ordem[i + 1].equalsIgnoreCase(Dao.ORDEM_ASC))
						select.addOrder(Order.asc(ordem[i]));
					else if (ordem[i + 1].equalsIgnoreCase(Dao.ORDEM_DESC))
						select.addOrder(Order.desc(ordem[i]));

			if (this.filtro != null) {
				configuraCriteria(obj, select);
			}
			select.setCacheable(true);
			select.setCacheMode(CacheMode.NORMAL);
			list = select.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			e.printStackTrace();
			throw new ECARException("erro.hibernateException");
		} catch (IllegalAccessException e) {
			this.logger.error(e);
			throw new ECARException("erro.exception");
		} catch (IllegalArgumentException e) {
			this.logger.error(e);
			throw new ECARException("erro.exception");
		} catch (InvocationTargetException e) {
			this.logger.error(e);
			throw new ECARException("erro.exception");
		}

		return list;
	}

	private void configuraCriteria(Object obj, Criteria select) {

		if (filtro.getOperacao().equals(Filter.OU)) {
			adicionaClausulaOu(filtro, select);
		} else if (filtro.getOperacao().equals(Filter.AND)) {
			if (filtro.getPrimeiroCriterio() == null || filtro.getSegundoCriterio() == null) {
				adicionaClausula(filtro, select);
			} else if (filtro.getPrimeiroCriterio() != null && filtro.getSegundoCriterio() != null) {
				adicionaClausulaAnd(filtro, select);
			}
		}

	}

	private void adicionaClausulaAnd(Filter filtro2, Criteria select) {
		select.add(Restrictions.and(filtro.getPrimeiroCriterio(), filtro.getSegundoCriterio()));

	}

	private void adicionaClausula(Filter filtro, Criteria select) {

		if (filtro.getPrimeiroCriterio() != null) {
			select.add(filtro.getPrimeiroCriterio());
		} else if (filtro.getSegundoCriterio() != null) {
			select.add(filtro.getSegundoCriterio());
		}

	}

	private void adicionaClausulaOu(Filter filtro, Criteria select) {

		select.add(Restrictions.or(filtro.getPrimeiroCriterio(), filtro.getSegundoCriterio()));

	}

	/**
	 * Devolve uma lista de objetos com campos duplicados.<br>
	 * Pesquisa, a partir do objeto passado como par�metro, os campos que n�o
	 * podem conter valores duplicados.<br>
	 * Por exemplo, num cadastro de pessoas, n�o pode haver duas pessoas com o
	 * mesmo CNPJ ou o mesmo Nome.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param obj
	 * @param nomeCamposNaoDuplos
	 * @param nomeChave
	 * @return List - uma lista de objetos que cont�m campos duplicados ou uma
	 *         lista vazia cc.
	 * @throws ECARException
	 */
	@SuppressWarnings("rawtypes")
	public List pesquisarDuplos(Object obj, String[] nomeCamposNaoDuplos, String nomeChave) throws ECARException {

		List list = new ArrayList();
		Criteria select;

		if (obj == null)
			return list;

		try {
			/*
			 * n�o d� para usar clearSession aqui, porque esse m�todo ser�
			 * utilizando momentos antes de salvar ou atualizar um objeto. Ao
			 * salvar ou atualizar um objeto, s�o utilizados alguns dados que
			 * est�o no cache, por exemplo collection do objeto que ser�
			 * trabalhado. clearSession();
			 */

			select = session.createCriteria(obj.getClass());
			Object auxObj;
			String nomeAtributo;
			String tipoRetorno;
			Criterion crit = Expression.sql("1 = 0"); // Adiciona um express�o
														// falsa para n�o
														// interferir nas
														// clausulas where
			for (int i = 0; i < nomeCamposNaoDuplos.length; i++) {
				nomeAtributo = nomeCamposNaoDuplos[i];
				auxObj = Util.invocaGet(obj, nomeAtributo);
				tipoRetorno = auxObj.getClass().getName().toLowerCase();
				// somente adiciona um criterio se o conteudo for != vazio
				if (auxObj != null) {
					if (tipoRetorno.endsWith("string"))
						crit = Expression.or(crit, Expression.ilike(nomeAtributo, auxObj));
					else {
						// Se o atributo for Set n�o entra na pesquisa ser�
						// filtrado depois
						if (!tipoRetorno.endsWith("set"))
							crit = Expression.or(crit, Expression.eq(nomeAtributo, auxObj));
					}
				}
			}
			select.add(crit);

			// adiciona o nome do campo codigo que deve ser diferente do atual
			auxObj = Util.invocaGet(obj, nomeChave);
			if (auxObj != null)
				select.add(Expression.not(Expression.eq(nomeChave, auxObj)));

			select.setCacheable(true);
			select.setCacheMode(CacheMode.NORMAL);
			list = select.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		} catch (IllegalArgumentException e) {
			this.logger.error(e);
			throw new ECARException("erro.exception");
		}
		return list;
	}

	/**
	 * Devolve uma lista de objetos com campos duplicados.<br>
	 * Pesquisa, a partir do objeto passado como par�metro, os campos que n�o
	 * podem conter valores duplicados.<br>
	 * Por exemplo, num cadastro de pessoas, n�o pode haver duas pessoas com o
	 * mesmo CNPJ ou o mesmo Nome.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param obj
	 * @param nomeCamposNaoDuplos
	 * @param nomeChave
	 * @return List - uma lista de objetos que cont�m campos duplicados ou uma
	 *         lista vazia cc.
	 * @throws ECARException
	 */
	@SuppressWarnings("rawtypes")
	public List pesquisarDuplosExatos(Object obj, String[] nomeCamposNaoDuplos, String nomeChave) throws ECARException {

		List list = new ArrayList();
		Criteria select;

		if (obj == null)
			return list;

		try {
			/*
			 * n�o d� para usar clearSession aqui, porque esse m�todo ser�
			 * utilizando momentos antes de salvar ou atualizar um objeto. Ao
			 * salvar ou atualizar um objeto, s�o utilizados alguns dados que
			 * est�o no cache, por exemplo collection do objeto que ser�
			 * trabalhado. clearSession();
			 */

			select = session.createCriteria(obj.getClass());
			Object auxObj;
			String nomeAtributo;
			String tipoRetorno;
			Criterion crit = Expression.sql("1 = 1");
			; // Adiciona um express�o true para n�o interferir nas clausulas
				// where
			for (int i = 0; i < nomeCamposNaoDuplos.length; i++) {
				nomeAtributo = nomeCamposNaoDuplos[i];
				auxObj = Util.invocaGet(obj, nomeAtributo);
				tipoRetorno = auxObj.getClass().getName().toLowerCase();
				// somente adiciona um criterio se o conteudo for != vazio
				if (auxObj != null) {
					if (tipoRetorno.endsWith("string")) {
						crit = Expression.and(crit, Expression.eq(nomeAtributo, auxObj));
					} else {
						// Se o atributo for Set n�o entra na pesquisa ser�
						// filtrado depois
						if (!tipoRetorno.endsWith("set"))
							crit = Expression.and(crit, Expression.eq(nomeAtributo, auxObj));
					}
				}
			}

			select.add(crit);

			// adiciona o nome do campo codigo que deve ser diferente do atual
			auxObj = Util.invocaGet(obj, nomeChave);
			if (auxObj != null)
				select.add(Expression.not(Expression.eq(nomeChave, auxObj)));

			select.setCacheable(true);
			select.setCacheMode(CacheMode.NORMAL);
			list = select.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		} catch (IllegalArgumentException e) {
			this.logger.error(e);
			throw new ECARException("erro.exception");
		} catch (Exception e) {
			this.logger.error(e);
			throw new ECARException("erro.exception");
		}
		return list;
	}

	/**
	 * Devolve uma lista de objetos de uma determinada classe.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @param cl
	 * @param ordem
	 * @return List - lista de objetos de acordo com os parametros especificados<br>
	 *         Exemplo de utilizacao<br>
	 *         List l = corDao.listar(Cor.class, new String[]
	 *         {"nomeCor",Dao.ORDEM_ASC});<br>
	 *         List l = corDao.listar(Cor.class, new String[]
	 *         {"significadoCor","asc"});<br>
	 * @throws ECARException
	 */
	@SuppressWarnings("rawtypes")
	public List listar(Class cl, String[] ordem) throws ECARException {
		List list = null;

		if (cl == null)
			return list;

		try {
			/*
			 * n�o d� para usar clearSession aqui, porque limpar� a session e
			 * assim outros objetos que j� tenham sido carregados e estejam
			 * sendo usados na constru��o da p�gina ser�o perdidos
			 * clearSession();
			 */

			Criteria c = session.createCriteria(cl);

			if (ordem != null)
				for (int i = 0; i < ordem.length; i += 2)
					// anda aos pares
					if (ordem[i + 1].equalsIgnoreCase(Dao.ORDEM_ASC))
						c.addOrder(Order.asc(ordem[i]));
					else if (ordem[i + 1].equalsIgnoreCase(Dao.ORDEM_DESC))
						c.addOrder(Order.desc(ordem[i]));
			c.setCacheable(true);
			c.setCacheMode(CacheMode.NORMAL);

			list = c.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			e.printStackTrace();
			throw new ECARException("erro.hibernateException");
		}
		return list;
	}

	/**
	 * Ordena.<br>
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 * @param colecao
	 * @param campo
	 * @param ordem
	 * @return List
	 * @throws ECARException
	 */
	@SuppressWarnings("rawtypes")
	public List ordenaSet(Set colecao, String campo, String ordem) throws ECARException {
		try {
			return this.getSession().createFilter(colecao, " order by " + campo + " " + ordem).list();
		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}

	}

	/**
	 * 
	 * @param colecao
	 * @param campo
	 * @return
	 * @throws ECARException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List ordenaList(List colecao, String campo) throws ECARException {
		Collection medias = colecao;
		BeanComparator ordenaMedia = new BeanComparator(campo, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) o2).compareTo((Comparable) o1);
			}
		});
		Collections.sort((List) medias, ordenaMedia);

		return colecao;

	}

	/**
	 * 
	 * @param colecao
	 * @param campo
	 * @return
	 * @throws ECARException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List ordenaListInvert(List colecao, String campo) throws ECARException {
		Collection medias = colecao;
		BeanComparator ordenaMedia = new BeanComparator(campo, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) o2).compareTo((Comparable) o1) * -1;
			}
		});
		Collections.sort((List) medias, ordenaMedia);

		return colecao;

	}

	/**
	 * Inicializa o objeto de Log.
	 * 
	 * @author N/C
	 * @since N/C
	 * @version N/C
	 */
	public void inicializarLogBean() {
		this.logBean = null;
		if (this.request != null) {
			this.loggerAuditoria = Logger.getLogger("AUDITORIA");
			SegurancaECAR seguranca = (SegurancaECAR) this.request.getSession().getAttribute("seguranca");
			this.logBean = new LogBean();
			this.logBean.setIPUsuario(this.request.getRemoteAddr());
			this.logBean.setUsuario(seguranca.getUsuario());
			this.logBean.setCodigoSessao(this.request.getSession().getId());
		}
	}

	/**
	 * 
	 * @return
	 */
	public Filter getFiltro() {
		return filtro;
	}

	/**
	 * 
	 * @param filtro
	 */
	public void setFiltro(Filter filtro) {
		this.filtro = filtro;
	}

	/**
	 * Busca uma lista de objetos de uma determinada classe, com valores entre o
	 * primeiro e ultimo.<br>
	 * 
	 * @author Paulo Pinheiro
	 * @since N/C
	 * @param cl
	 * @param campoIntervalo
	 * @param primeiro
	 * @param argumentos
	 * @param ultimo
	 * @param ordem
	 * @return List - lista de objetos de acordo com os parametros especificados<br>
	 *         Exemplo de utilizacao<br>
	 *         Map<String, Object> argumentos = null;
	 *         argumentos.put(propriedade, valor); -- Pode se inserido varios
	 *         List l = corDao.buscar(Cor.class, argumentos);<br>
	 * @throws ECARException
	 */
	@SuppressWarnings("rawtypes")
	public List buscar(Class cl, String campoIntervalo, Object primeiro, Object ultimo, Map<String, Object> argumentos, String[] ordem) throws ECARException {
		List list = null;

		if (cl == null)
			return list;

		try {

			Criteria c = session.createCriteria(cl);

			if (campoIntervalo != null & primeiro != null) {
				c.add(Restrictions.ge(campoIntervalo, primeiro));
			}

			if (campoIntervalo != null && ultimo != null) {
				c.add(Restrictions.le(campoIntervalo, ultimo));
			}

			if (argumentos != null)
				c.add(Restrictions.allEq(argumentos));

			if (ordem != null)
				for (int i = 0; i < ordem.length; i += 2)
					// anda aos pares
					if (ordem[i + 1].equalsIgnoreCase(Dao.ORDEM_ASC))
						c.addOrder(Order.asc(ordem[i]));
					else if (ordem[i + 1].equalsIgnoreCase(Dao.ORDEM_DESC))
						c.addOrder(Order.desc(ordem[i]));
			c.setCacheable(true);
			c.setCacheMode(CacheMode.NORMAL);
			list = c.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return list;
	}

	/**
	 * Busca uma lista de objetos de uma determinada classe, com valores entre o
	 * primeiro e ultimo.<br>
	 * 
	 * @author Paulo Pinheiro
	 * @since N/C
	 * @param cl
	 * @param campoIntervalo
	 * @param primeiro
	 * @param ultimo
	 * @param argumentosIN
	 * @param argumentos
	 * @param ordem
	 * @param campoIN
	 * @return List - lista de objetos de acordo com os parametros especificados<br>
	 *         Exemplo de utilizacao<br>
	 *         Map<String, Object> argumentos = null;
	 *         argumentos.put(propriedade, valor); -- Pode se inserido varios
	 *         List l = corDao.buscar(Cor.class, argumentos);<br>
	 * @throws ECARException
	 */
	@SuppressWarnings("rawtypes")
	public List buscar(Class cl, String campoIntervalo, Object primeiro, Object ultimo, String campoIN, List argumentosIN, Map<String, Object> argumentos, String[] ordem) throws ECARException {
		List list = null;

		if (cl == null)
			return list;

		try {

			Criteria c = session.createCriteria(cl);

			if (campoIntervalo != null & primeiro != null)
				c.add(Restrictions.ge(campoIntervalo, primeiro));

			if (campoIntervalo != null & ultimo != null)
				c.add(Restrictions.le(campoIntervalo, ultimo));

			if (campoIN != null & argumentosIN != null)
				c.add(Restrictions.in(campoIN, argumentosIN));

			if (argumentos != null)
				c.add(Restrictions.allEq(argumentos));

			if (ordem != null)
				for (int i = 0; i < ordem.length; i += 2)
					// anda aos pares
					if (ordem[i + 1].equalsIgnoreCase(Dao.ORDEM_ASC))
						c.addOrder(Order.asc(ordem[i]));
					else if (ordem[i + 1].equalsIgnoreCase(Dao.ORDEM_DESC))
						c.addOrder(Order.desc(ordem[i]));
			c.setCacheable(true);
			c.setCacheMode(CacheMode.NORMAL);
			list = c.list();

		} catch (HibernateException e) {
			this.logger.error(e);
			throw new ECARException("erro.hibernateException");
		}
		return list;
	}

	/**
	 * Busca uma lista de objetos de uma determinada classe, com valores entre o
	 * primeiro e ultimo.<br>
	 * 
	 * @author Paulo Pinheiro
	 * @since N/C
	 * @param Class
	 *            cl - classe do objeto
	 * @param Map
	 *            - parametros contando a propriedade e o valor
	 * @return List - lista de objetos de acordo com os parametros especificados<br>
	 *         Exemplo de utilizacao<br>
	 *         Map<String, Object> argumentos = null;
	 *         argumentos.put(propriedade, valor); -- Pode se inserido varios
	 *         List l = corDao.buscar(Cor.class, argumentos);<br>
	 * @throws ECARException
	 */
	// public List buscar(Class cl, String[] colunas, Map<String, Object>
	// argumentos,
	// String[] ordem) throws ECARException {
	// List list = null;
	//
	// if (cl == null)
	// return list;
	//
	// try {
	//
	// Criteria c = session.createCriteria(cl);
	//
	// if (argumentos != null)
	// c.add(Restrictions.allEq(argumentos));
	//
	// if (ordem != null)
	// for (int i = 0; i < ordem.length; i+=2) //anda aos pares
	// if (ordem[i+1].equalsIgnoreCase(Dao.ORDEM_ASC))
	// c.addOrder(Order.asc(ordem[i]));
	// else if (ordem[i+1].equalsIgnoreCase(Dao.ORDEM_DESC))
	// c.addOrder(Order.desc(ordem[i]));
	//
	// list = c.list();
	//
	// } catch (HibernateException e) {
	// this.logger.error(e);
	// throw new ECARException("erro.hibernateException");
	// }
	// return list;
	// }

	/**
	 * 
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List consultarPorHQL(String hql) {

		Query query = this.getSession().createQuery(hql);

		return query.list();

	}

	// Métodos criados para usar retorno genérico das consultas por hql.

	/**
	 * Método que retorna um único objeto.
	 * 
	 * @param String
	 *            hql.
	 * @return objeto.
	 */
	@SuppressWarnings("unchecked")
	public T buscarPorHQL(String hql) {
		Query query = this.getSession().createQuery(hql);

		return (T) query.uniqueResult();
	}

	/**
	 * Método que retorna um único objeto de acordo com os parametros
	 * informados.
	 * 
	 * @param String
	 *            hql, parametros da pesquisa.
	 * @return objeto.
	 */
	@SuppressWarnings("unchecked")
	public T buscarPorHQL(String hql, Object... parametros) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; i < parametros.length; i++) {
			query.setParameter(i, parametros[i]);
		}

		return (T) query.uniqueResult();
	}

	/**
	 * Método que retorna uma lista de objetos.
	 * 
	 * @param String
	 *            hql.
	 * @return lista de objetos.
	 */
	@SuppressWarnings("unchecked")
	public List<T> listarPorHQL(String hql) {
		Query query = this.getSession().createQuery(hql);

		return query.list();
	}

	/**
	 * Método que retorna uma lista de objetos de acordo com os parametros
	 * informados.
	 * 
	 * @param String
	 *            hql, parametros da pesquisa.
	 * @return lista de objetos.
	 */
	@SuppressWarnings("unchecked")
	public List<T> listarPorHQL(String hql, Object... parametros) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; i < parametros.length; i++) {
			query.setParameter(i, parametros[i]);
		}

		return query.list();
	}

	public Long buscarQuantidadeRegistros(String hql, Object... parametros) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; i < parametros.length; i++) {
			query.setParameter(i, parametros[i]);
		}

		return (Long) query.uniqueResult();
	}

}
