package ecar.dao;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import comum.database.Dao;
import comum.util.Util;

import ecar.exception.ECARException;
import ecar.pojo.ConfigSisExecFinanCsef;
import ecar.pojo.ConfigSisExecFinanCsefv;

/**
 * Classe de manipulação de objetos da classe AtributoAtt.
 * 
 * @author CodeGenerator - Esta classe foi gerada automaticamente
 * @since 1.0
 * @version 1.0, Fri Jan 27 07:54:28 BRST 2006
 *
 */
public class ConfigSisExecFinanCsefvDao extends Dao {
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public ConfigSisExecFinanCsefvDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Retorna uma versão (ConfigSisExecFinanCsefv) de um sistema (ConfigSisExecFinanCsef) com base 
	 * no sistema, mes e ano.
	 * @param ano
	 * @param mes
	 * @param sistema
	 * @return ConfigSisExecFinanCsefv
	 * @throws ECARException
	 */	
	public ConfigSisExecFinanCsefv getConfigSisExecFinanCsefv(Long ano, Long mes, ConfigSisExecFinanCsef sistema) throws ECARException{
		try {
			StringBuilder sql = new StringBuilder("from ConfigSisExecFinanCsefv versao")
									.append(" where versao.configSisExecFinanCsef.codCsef = :codCsef")
									.append("   and (versao.mesVersaoCsefv <= :mesVersao)")
									.append("   and (versao.anoVersaoCsefv <= :anoVersao)")
									.append("   order by versao.anoVersaoCsefv desc, versao.mesVersaoCsefv desc");
			
			Query q = this.session.createQuery(sql.toString());
			q.setLong("codCsef", sistema.getCodCsef().longValue());
			q.setLong("mesVersao", mes.longValue());
			q.setLong("anoVersao", ano.longValue());
			
			q.setMaxResults(1);
			
			return (ConfigSisExecFinanCsefv) q.uniqueResult();
		} catch (HibernateException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
		}
		return null;
	}

	/**
	 * Retorna as versões que possuem sistemas ativos, ordenados pelo nome do sistema.
	 * @return List
	 * @throws ECARException
	 */
	public List getCsefvOrderBySistema() throws ECARException{
		List retorno = new ArrayList();
		try {
			StringBuilder sql = new StringBuilder("select versao from ConfigSisExecFinanCsefv versao")
									    .append(" join versao.configSisExecFinanCsef sistema")
									    .append(" where sistema.indAtivoCsef = 'S'")
									    .append("   and versao.indAtivoCsefv = 'S'")
									    .append(" order by versao.configSisExecFinanCsef.nomeCsef, versao.anoVersaoCsefv, versao.mesVersaoCsefv");
				
			Query q = this.session.createQuery(sql.toString());
			retorno = q.list();
		} catch (HibernateException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
		}
		return retorno;
	}

	/**
	 * Retorna as versões que possuem sistemas ativos, ordenados pelo ano/mes da versao
	 * @return List
	 * @throws ECARException
	 */
	public List getCsefvOrderByMesAno() throws ECARException{
		List retorno = new ArrayList();
		try {
			StringBuilder sql = new StringBuilder("select versao from ConfigSisExecFinanCsefv versao")
									    .append(" join versao.configSisExecFinanCsef sistema")
									    .append(" where sistema.indAtivoCsef = 'S'")
									    .append("   and versao.indAtivoCsefv = 'S'")
									    .append(" order by versao.anoVersaoCsefv, versao.mesVersaoCsefv");
			
			Query q = this.session.createQuery(sql.toString());
			retorno = q.list();
		} catch (HibernateException e) {
        	Logger logger = Logger.getLogger(this.getClass());
        	logger.error(e);
		}
		return retorno;
	}
	
	/**
	 * Descobre o até qual mes/ano pertence à uma versão e retorna uma string, sendo:
	 * 
	 * CODVERSAO + | + MESINICIAL + | +  ANOINICIAL + | +  MESFINAL + | +  ANOFINAL + CODSISTEMA
	 * @param versao
	 * @return
	 * @throws ECARException 
	 */
	public String getPeriodoByVersao(ConfigSisExecFinanCsefv versao) throws ECARException{
		
		String retorno = ""; 
			
		List versoesOrdenadasPelaData = this.getCsefvOrderByMesAno();
		Iterator itVersoes = versoesOrdenadasPelaData.iterator();
		boolean achou = false;
		while(itVersoes.hasNext()){
			ConfigSisExecFinanCsefv v = (ConfigSisExecFinanCsefv) itVersoes.next();
			
			if(!v.getCodCsefv().equals(versao.getCodCsefv()) && !achou)
				continue;
			
			if(v.getCodCsefv().equals(versao.getCodCsefv()))
				achou = true;
			
			//if(achou && !v.getCodCsefv().equals(versao.getCodCsefv())){
			if(achou){
				//É a próxima versão depois da versão informada no parametro.
				String mesIni = (versao.getMesVersaoCsefv().longValue() < 10) ? "0" + String.valueOf(versao.getMesVersaoCsefv()) : String.valueOf(versao.getMesVersaoCsefv());
				String mesFim = (v.getMesVersaoCsefv().longValue() < 10) ? "0" + String.valueOf(v.getMesVersaoCsefv()) : String.valueOf(v.getMesVersaoCsefv());
				retorno = String.valueOf(versao.getCodCsefv()) + "|" + 
						mesIni + "|" + 
						String.valueOf(versao.getAnoVersaoCsefv()) + "|" +
						mesFim + "|" + 
						String.valueOf(v.getAnoVersaoCsefv()) + "|" +  
						String.valueOf(versao.getConfigSisExecFinanCsef().getCodCsef());
				break;
			}
		}
		
		return retorno;
	}
	
	
    @Override
	public List pesquisar(Object obj, String[] ordem) throws ECARException {
		
		List list = new ArrayList();	// lista resultado
		Criteria select;				// select (do hibernate)
		
		if (obj == null)
			return list;
		
		try {
		    /* limpa os objeto do cache antes de buscar, para garantir que a busca será no BD e nao no cache */
		    /* TODO avaliar e utilizacao do cache */
		    //clearSession();
		    
			select = session.createCriteria(obj.getClass());
			List listaMetodos = Util.listaMetodosGet(obj);
			
			//*************************************************************
			// Lista de métodos que devem ser desconsiderados
			// no resto do algoritmo
			String[] listaExclusao = new String[]{"getSistemaVersaoCsefv"};
			
			// Rotina para eliminar os métodos desnecessários
			for (int j=0; j<listaExclusao.length; j++) {
				
				String nomeMetodo = listaExclusao[j];
				Iterator x = listaMetodos.iterator();
				
				while (x.hasNext()) {
					
					Method metodo = (Method) x.next();
						
					if (metodo.getName().equals(nomeMetodo)) {
						listaMetodos.remove(metodo);
						break;
					}
				}
			}
			//*************************************************************
			
			Object auxObj;
			String nomeAtributo;
			String nomeMetodo;
			String tipoRetorno;
			for (int i = 0; i < listaMetodos.size(); i++) {
				auxObj = ((Method)listaMetodos.get(i)).invoke(obj, null);
				// somente adiciona um criterio se o conteudo for != vazio
				if (auxObj != null) {
					// obtem o nome do método para retirar o nome do atributo
					nomeMetodo = ((Method)listaMetodos.get(i)).getName();
					tipoRetorno = ((Method)listaMetodos.get(i)).getReturnType().getName().toLowerCase();
					nomeAtributo = nomeMetodo.substring(3,4).toLowerCase() +
						nomeMetodo.substring(4);
					
					if (tipoRetorno.endsWith("string"))
						select.add(Expression.ilike(nomeAtributo, "%" + auxObj + "%"));
					else{			    
					    // Se o atributo for Set não entra na pesquisa será filtrado depois
					    if(!tipoRetorno.endsWith("set"))
					        select.add(Expression.eq(nomeAtributo, auxObj));
					}    
				}
			}
			
			if (ordem != null)
				for (int i = 0; i < ordem.length; i+=2) //anda aos pares
					if (ordem[i+1].equalsIgnoreCase(Dao.ORDEM_ASC))
						select.addOrder(Order.asc(ordem[i]));
					else if (ordem[i+1].equalsIgnoreCase(Dao.ORDEM_DESC))
						select.addOrder(Order.desc(ordem[i]));
			
			list = select.list();
		
		} catch (HibernateException e) {
			this.logger.error(e);
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
	
	/**
	 * Exclui um objeto passando a classe e a chave como parâmetro. Primeiro carrega o objeto<br> 
         *
         * @param obj
         * @throws ECARException
         */
	
	public void excluir (ConfigSisExecFinanCsefv obj) throws ECARException {
		
        try {
         
        	boolean excluir = true;

            if ((contar(obj.getConfigExecFinanCefs()) > 0) || (contar(obj.getImportacaoImps()) > 0)) {
                excluir = false;
                throw new ECARException("integracaoFinanceira.versaoSistema.exclusao.erro");
            }
            if (excluir)
                super.excluir(obj);
        } catch (ECARException e) {
        	this.logger.error(e);
            throw e;
        }

	}
}