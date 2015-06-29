package ecar.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.exception.ConstraintViolationException;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.Etiqueta;
import ecar.pojo.ItemEstruturaIett;
import ecar.pojo.RelacionamentoIettEtiqueta;
import ecar.pojo.UsuarioUsu;

/**
 * Classe da camada Modelo que trata as conversações com o banco de dados, dentro do escopo dos negócios de Etiquetas. <br/>
 * As excessões devem ser tratadas na classe controladora.
 * @since 22/03/2012
 * */
public class EtiquetaDao extends Dao<Etiqueta> {
	
	
	/**
	 * Insere uma entidade Etiqueta no banco de dados.
	 * */
	public Long incluirEtiqueta(Etiqueta etiqueta) throws ECARException{
	    super.salvar(etiqueta);
	    return etiqueta.getCodigo();
	}
	
	
	/**
	 * Atualiza uma entidade Etiqueta no banco de dados.
	 * */
	public void editarEtiqueta(Etiqueta etiqueta) throws Exception{
		try {
			this.getSession().update(etiqueta);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	/**
	 * Pesquisa uma entidade Etiqueta no banco de dados.
	 * */
	public Etiqueta pesquisarEtiqueta(Long codigoEtiqueta) throws Exception{
		Etiqueta etiqueta = new Etiqueta();
		try {
			etiqueta = (Etiqueta) this.getSession().get(Etiqueta.class, etiqueta);
		} catch (Exception e) {
			throw e;
		}
		return etiqueta;
	}
	
	
	/**
	 * Pesquisa relacionamentos ATIVOS IettEtiqueta no banco de dados.
	 * @return Uma lista de objetos RelacionamentoIettEtiqueta.
	 * */
	@SuppressWarnings("unchecked")
	public List<RelacionamentoIettEtiqueta> pesquisarListaRelacionamentosIettEtiqueta(Long codigoItem) throws Exception{
		List relacionamentosIettEtiqueta;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" select rel from ecar.pojo.RelacionamentoIettEtiqueta rel ");
			hql.append(" where rel.itemEstruturaIett.codIett = :codigoiett ");
			hql.append(" and rel.indAtivo = 'S' ");
			hql.append(" and rel.etiqueta.indAtivo = 'S' ");
			
			
			Query query = this.getSession().createQuery(hql.toString());
			query.setLong("codigoiett", codigoItem);
			
			relacionamentosIettEtiqueta = new ArrayList(query.list());

		} catch (Exception e) {
			throw e;
		}
		return relacionamentosIettEtiqueta;
	}
	
	/**
	 * Pesquisa relacionamentos IettEtiqueta no banco de dados.
	 * @return Uma lista de objetos RelacionamentoIettEtiqueta.
	 * */
	@SuppressWarnings("unchecked")
	public List<RelacionamentoIettEtiqueta> pesquisarListaRelacionamentosIettEtiqueta(Long codigoItem, String nomeEtiqueta) throws Exception{
		List relacionamentosIettEtiqueta;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" select rel from ecar.pojo.RelacionamentoIettEtiqueta rel ");
			hql.append(" where rel.itemEstruturaIett.codIett = :codigoiett ");
			hql.append(" and rel.etiqueta.indAtivo = 'S' ");
			hql.append(" and upper(rel.etiqueta.nome) like '" + nomeEtiqueta.toUpperCase() + "%'");
			
			
			Query query = this.getSession().createQuery(hql.toString());
			query.setLong("codigoiett", codigoItem);
			
			relacionamentosIettEtiqueta = new ArrayList(query.list());

		} catch (Exception e) {
			throw e;
		}
		return relacionamentosIettEtiqueta;
	}
	
	public RelacionamentoIettEtiqueta pesquisarRelacionamentoIettEtiqueta(Long codigoRelacionamento) throws Exception{
		RelacionamentoIettEtiqueta relacionamentoIettEtiqueta;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" select rel from ecar.pojo.RelacionamentoIettEtiqueta rel ");
			hql.append(" where rel.codigo = :codigoRelacionamento ");
			
			Query query = this.getSession().createQuery(hql.toString());
			query.setLong("codigoRelacionamento", codigoRelacionamento);
			
			relacionamentoIettEtiqueta = (RelacionamentoIettEtiqueta) query.uniqueResult();

		} catch (Exception e) {
			throw e;
		}
		return relacionamentoIettEtiqueta;
	}
	
	
	public RelacionamentoIettEtiqueta pesquisarRelacionamentoIettEtiqueta(Long codigoEtiqueta, Long codigoIett) throws Exception{
		RelacionamentoIettEtiqueta relacionamentoIettEtiqueta;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" select rel from ecar.pojo.RelacionamentoIettEtiqueta rel ");
			hql.append(" where rel.etiqueta.codigo = :codigoEtiqueta ");
			hql.append(" and rel.itemEstruturaIett.codIett = :codigoIett ");
			
			Query query = this.getSession().createQuery(hql.toString());
			query.setLong("codigoEtiqueta", codigoEtiqueta);
			query.setLong("codigoIett", codigoIett);
			
			relacionamentoIettEtiqueta = (RelacionamentoIettEtiqueta) query.uniqueResult();

		} catch (Exception e) {
			throw e;
		}
		return relacionamentoIettEtiqueta;
	}
	
	
	/**
	 * Pesquisa relacionamento IettEtiqueta no banco de dados.
	 * @return Uma objeto RelacionamentoIettEtiqueta.
	 * @param codigoRelacionamento Id do relacionamento.
	 * */
	public RelacionamentoIettEtiqueta pesquisarRelacionamentoIettEtiqueta(String nomeEtiqueta, Long codigoIett) throws Exception{
		RelacionamentoIettEtiqueta relacionamentoIettEtiqueta;
		try {
			StringBuilder hql = new StringBuilder();
			hql.append(" select rel from ecar.pojo.RelacionamentoIettEtiqueta rel ");
			hql.append(" where rel.itemEstruturaIett.codIett = :codigoIett ");
			hql.append(" and rel.etiqueta.codigo = ");
			hql.append(" ( ");
			hql.append("	select etiqueta.codigo from ecar.pojo.Etiqueta  etiqueta");
			hql.append(" 	where upper(etiqueta.nome) like '" + nomeEtiqueta.toUpperCase() + "'");
			hql.append(" ) ");
			Query query = this.getSession().createQuery(hql.toString());
			query.setLong("codigoIett", codigoIett);
			//query.setString("nomeEtiqueta", nomeEtiqueta);
			//query.setParameter("nomeEtiqueta", nomeEtiqueta);
			
			relacionamentoIettEtiqueta = (RelacionamentoIettEtiqueta) query.uniqueResult();

		} catch (Exception e) {
			throw e;
		}
		return relacionamentoIettEtiqueta;
	}
	
	
	/**
	 * Atualiza relacionamento IettEtiqueta no banco de dados.
	 * @param rel Objeto a ser atualizado no banco.
	 * */
	public void atualizarRelacionamentoIettEtiqueta(RelacionamentoIettEtiqueta rel) throws Exception{
		try {
			
			this.session.merge(rel);
			this.session.flush();
			
//			StringBuilder hql = new StringBuilder();
//			hql.append(" select rel from ecar.pojo.RelacionamentoIettEtiqueta rel ");
//			hql.append(" where rel.codigo = :codigoRelacionamento ");
//			
//			Query query = this.getSession().createQuery(hql.toString());
//			query.setLong("codigoRelacionamento", codigoRelacionamento);
//			
//			relacionamentosIettEtiqueta = new ArrayList(query.list());

		} catch (Exception e) {
			throw e;
		}

	}
	
	
	/**
	 * Insere uma entidade RelacionamentoIettEtiqueta no banco de dados.
	 * */
	public void incluirRelacionamentoEtiquetaItem(RelacionamentoIettEtiqueta rel) throws Exception{
		try {
			super.salvar(rel);
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * Pesquisa entidades Etiqueta no banco de dados.
	 * @return Uma coleção, sem repetições, de objetos Etiqueta.
	 * */
	public Set<Etiqueta> pesquisarListaEtiquetas(String nomeFoneticoTag) throws Exception{
		Set<Etiqueta> etiquetas = new HashSet<Etiqueta>();
		try {
			
		} catch (Exception e) {
			throw e;
		}
		return etiquetas;
	}
	
	@SuppressWarnings("unchecked")
	public List<Etiqueta> listarEtiqueta(String prioritaria, String nome) {
		StringBuffer hql = new StringBuffer();
		
		hql.append("FROM Etiqueta eti ");
		hql.append("WHERE eti.nome like :nomeEti ");
		if(prioritaria.equals("S")) {
			hql.append("AND eti.indPrioritario = 'S' ");	
		}
		Query q = getSession().createQuery(hql.toString());
		q.setParameter("nomeEti", "%"+nome+"%");
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<ecar.pojo.acompanhamentoEstrategico.Etiqueta> listarEtiqueta(UsuarioUsu usuario, 
			String prioritaria, String nome) {
		String sql = "select new ecar.pojo.acompanhamentoEstrategico.Etiqueta(" +
				"et.codigo, et.nome, et.indPrioritario, c.codigo, c.nome) " +
				"from Etiqueta et " +
				"left join et.categoria as c " +
				"where et.indAtivo = 'S' " ;
		
		if(prioritaria.equals("S")) {
			sql += "and et.indPrioritario = :prioritario ";	
		}				
		
		if(nome != null) {
			//fonetizar e pesquisar
			sql += ("and et.nomeFonetico like :nome ");
		}
		Query query = this.getSession().createQuery(sql.toString());
		
		if(prioritaria.equals("S")) {
			query.setString("prioritario", prioritaria);	
		}		
		
		if(nome != null) {
			query.setParameter("nome", "%"+nome+"%");
		}
		
		return query.list();
	}
	
	
	
	private void queryArvoreFilhos(
			StringBuffer hql ) {

			hql.append( "WITH RECURSIVE iett_recursivo("+
		
						  "cod_iett,"+
						  "cod_iett_pai,"+
						  "nivel_iett"+
		
						")"+
		
						" AS("+
						" SELECT"+
						"  cod_iett,"+
						"  cod_iett_pai,"+
						"  nivel_iett"+
						" FROM"+
						"  public.tb_item_estrutura_iett"+
						" WHERE cod_iett = :codIett "+	
						" AND ind_ativo_iett = 'S'"+
						" UNION ALL "+
						" SELECT"+
						"  iett.cod_iett,"+
						"  iett.cod_iett_pai,"+
						"  iett.nivel_iett"+
						" FROM"+
						"  public.tb_item_estrutura_iett iett"+
						"  INNER JOIN iett_recursivo ON iett.cod_iett_pai =  iett_recursivo.cod_iett"+
						" WHERE iett.ind_ativo_iett = 'S'"+
		
						")"+
						" SELECT * "+
						" FROM iett_recursivo ir");
	
	}
	
	private void queryArvorePais(
			StringBuffer hql ) {

			hql.append( "WITH RECURSIVE iett_recursivo("+
		
						  "cod_iett,"+
						  "cod_iett_pai,"+
						  "nivel_iett"+
		
						")"+
		
						" AS("+
						" SELECT"+
						"  cod_iett,"+
						"  cod_iett_pai,"+
						"  nivel_iett"+
						" FROM"+
						"  public.tb_item_estrutura_iett"+
						" WHERE cod_iett = :codIett"+	
						" AND ind_ativo_iett = 'S'"+
						" UNION ALL "+
						" SELECT"+
						"  iett.cod_iett,"+
						"  iett.cod_iett_pai,"+
						"  iett.nivel_iett"+
						" FROM"+
						"  public.tb_item_estrutura_iett iett"+
						"  INNER JOIN iett_recursivo ON iett.cod_iett =  iett_recursivo.cod_iett_pai"+
						" WHERE iett.ind_ativo_iett = 'S'"+
		
						")"+
						" SELECT * "+
						" FROM iett_recursivo ir");
	
	}	
	
	
	private void queryConsultaExistenciaRelacionamentoEtiqueta(
			StringBuffer hql ) {

			hql.append( "select count(*) from public.rel_itemestrutiett_etiqueta rel where rel.cod_iett=:codigo_iett and rel.cod_etiqueta=:codigo_etiqueta");
	
	}	
	
	
	public void atualizaEtiquetaRecursiva(Long codigoIett, Long codigoEtiqueta){
		
		ItemEstruturaDao daoIett = new ItemEstruturaDao(null);
		
		StringBuffer hqlPrincipal = new StringBuffer();
		
		hqlPrincipal.append("select iett.cod_iett, iett.nivel_iett, etiqueta.cod_etiqueta, etiqueta.ind_ativo " +
				"			 from public.tb_item_estrutura_iett iett" +
				"			 inner join public.rel_itemestrutiett_etiqueta etiqueta on etiqueta.cod_iett = iett.cod_iett ");
		
		
		if(codigoIett !=null && codigoEtiqueta != null){
				hqlPrincipal.append(" where iett.cod_iett=:codigo_iett and etiqueta.cod_etiqueta=:codigo_etiqueta"); 
		}
		
		hqlPrincipal.append("						  order by iett.nivel_iett");
		

		
		
		SQLQuery consultaPrincipal = this.session.createSQLQuery(hqlPrincipal.toString());
		if(codigoIett !=null && codigoEtiqueta !=null){
			consultaPrincipal.setParameter("codigo_iett", codigoIett);
			consultaPrincipal.setParameter("codigo_etiqueta", codigoEtiqueta);
		}
		List<Object[]> listaIett = consultaPrincipal.list();
		
		
		for(Object[] registroPrincipal : listaIett){
			
			
			StringBuffer hqlPais = new StringBuffer();
			StringBuffer hqlFilhos = new StringBuffer();
			
			
			//Atualiza Etiquetas dos filhos do Item Corrente
			queryArvoreFilhos(hqlFilhos);
			SQLQuery consultaFilhos = this.session.createSQLQuery(hqlFilhos.toString());
			consultaFilhos.setParameter("codIett", registroPrincipal[0]);
			List<Object[]> listaIettFilhos = consultaFilhos.list();
			
			System.out.println("_____________________");
			System.out.println(registroPrincipal[0].toString()+":");
			System.out.println("Filhos:");
			for(Object[] filhos : listaIettFilhos){
				
				
				System.out.println("-" + filhos[0].toString() + "|" + filhos[2].toString());
				
				StringBuffer hqlExisteRelacionamento = new StringBuffer();
				queryConsultaExistenciaRelacionamentoEtiqueta(hqlExisteRelacionamento);
				SQLQuery consultaExistencia = this.session.createSQLQuery(hqlExisteRelacionamento.toString());
				consultaExistencia.setParameter("codigo_iett", filhos[0]);
				consultaExistencia.setParameter("codigo_etiqueta", registroPrincipal[2]);
				BigInteger existeRelacionamentoEtiqueta = (BigInteger) consultaExistencia.uniqueResult();
				
				
				if(existeRelacionamentoEtiqueta.equals(new BigInteger("0"))){
					ItemEstruturaIett iett = null;
					try {
						iett = (ItemEstruturaIett) daoIett.buscar(ItemEstruturaIett.class, Long.parseLong(filhos[0].toString()));
					} catch (ECARException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Etiqueta etiqueta = null;
					try {
						etiqueta = (Etiqueta) buscar(Etiqueta.class, Long.parseLong(registroPrincipal[2].toString()));
					} catch (ECARException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
					
					RelacionamentoIettEtiqueta rel = new RelacionamentoIettEtiqueta();
					rel.setEtiqueta(etiqueta);
					rel.setItemEstruturaIett(iett);
					rel.setIndAtivo(registroPrincipal[3].toString());
					
					try {
						incluirRelacionamentoEtiquetaItem(rel);
					} 
					catch(ConstraintViolationException ce){
						System.out.println("Já existe.");
					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
				else
					System.out.println("Já existe.");
				
			}			
			//Fim Atualiza Etiqueta dos filhos
			
		
			
			
			//Atualiza Etiquetas para os pais do item corrente
			hqlPais = new StringBuffer();
			queryArvorePais(hqlPais);
			SQLQuery consultaPais = this.session.createSQLQuery(hqlPais.toString());
			consultaPais.setParameter("codIett", registroPrincipal[0]);
			List<Object[]> listaIettPais = consultaPais.list();
			
			System.out.println("Pais:");
			for(Object[] pais : listaIettPais){
				
				
				System.out.println("-" + pais[0].toString()+"|"+pais[2].toString());
				
				StringBuffer hqlExisteRelacionamento = new StringBuffer();
				queryConsultaExistenciaRelacionamentoEtiqueta(hqlExisteRelacionamento);
				SQLQuery consultaExistencia = this.session.createSQLQuery(hqlExisteRelacionamento.toString());
				consultaExistencia.setParameter("codigo_iett", pais[0]);
				consultaExistencia.setParameter("codigo_etiqueta", registroPrincipal[2]);
				BigInteger existeRelacionamentoEtiqueta = (BigInteger) consultaExistencia.uniqueResult();
				
				if(existeRelacionamentoEtiqueta.equals(new BigInteger("0"))){				
				
					ItemEstruturaIett iett = null;
					try {
						iett = (ItemEstruturaIett) daoIett.buscar(ItemEstruturaIett.class, Long.parseLong(pais[0].toString()));
					} catch (ECARException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					Etiqueta etiqueta = null;
					try {
						etiqueta = (Etiqueta) buscar(Etiqueta.class, Long.parseLong(registroPrincipal[2].toString()));
					} catch (ECARException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
					
					RelacionamentoIettEtiqueta rel = new RelacionamentoIettEtiqueta();
					rel.setEtiqueta(etiqueta);
					rel.setItemEstruturaIett(iett);
					rel.setIndAtivo(registroPrincipal[3].toString());
					
					try {
						incluirRelacionamentoEtiquetaItem(rel);
						
					} 
					catch(ConstraintViolationException ce){
						System.out.println("Já existe.");
					}
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
					System.out.println("Já existe.");
			}	
			//Fim Atualiza Etiquetas Pais
			
			
		}
		
		
		
	}
	
	public void atualizaEtiquetaInclusaoItem(Long codIett, Long codIettPai) {

		try {

			List<RelacionamentoIettEtiqueta> listaRelacionamentoIettEtiqueta = pesquisarListaRelacionamentosIettEtiqueta(codIettPai);
			ItemEstruturaDao daoIett = new ItemEstruturaDao(null);
			ItemEstruturaIett iett = (ItemEstruturaIett) daoIett.buscar(
					ItemEstruturaIett.class, codIett);

			for (RelacionamentoIettEtiqueta rie : listaRelacionamentoIettEtiqueta) {
				RelacionamentoIettEtiqueta rel = new RelacionamentoIettEtiqueta();
				rel.setEtiqueta(rie.getEtiqueta());
				rel.setItemEstruturaIett(iett);
				rel.setIndAtivo(rie.getIndAtivo());
				incluirRelacionamentoEtiquetaItem(rel);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	
	
}
