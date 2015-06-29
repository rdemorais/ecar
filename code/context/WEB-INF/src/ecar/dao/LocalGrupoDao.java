/*
 * Criado em 05/11/2004
 *
 *
 * Classe para tratar dos grupos de locais e hierarquia de grupos de locais.
 * A hierarquia de grupos de locais eh implicita pois representa um conjunto dentro
 * de cada objeto local. Um grupo pode ter muitos pais(grupos) diferentes.
 * Um grupo também pode ter muitos filhos diferentes.
 * Relacionamentos cíclicos não são permitidos. 
 * 
 * Sejam os seguintes grupos:
 * Pais, Regiao, Estado, Municipio
 * 
 * e as respectivas associacoes:
 * Municipio é filho de Estado
 * Municipio é filho de Regiao
 * Estado é filho de Regiao
 * Regiao é filho de Pais
 * 
 * Ao selecionarmos um grupo de local quem podem ser seus filhos válidos?
 * 
 * R.O conjunto de todos os grupos "G" menos o proprio grupo "g" e seus ascendentes.
 * 
 * G - {{g} U Ascendentes{g}}
 * 
 * Ao selecionarmos o grupo Municipio, quem pode ser seus filhos? 
 * R. ninguém, pois:
 * 
 * {P, R, E, M} - {M, E, R, P} = {conjunto vazio}
 *
 * Quem podem ser os filhos de Regiao?
 * R. {P, R, E, M} - {R, P} = {E, M}
 *
 *
 *
 */
package ecar.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;

import comum.database.Dao;

import ecar.exception.ECARException;
import ecar.pojo.LocalGrupoLgp;

/**
 * @author felipev
 *
 */
public class LocalGrupoDao extends Dao{
	/**
	 * Construtor. Chama o Session factory do Hibernate
         *
         * @param request
         */
	public LocalGrupoDao(HttpServletRequest request) {
		super();
		this.request = request;
	}
	
	/**
	 * Retorna um list com identificações de todos os grupos filhos de um grupo
	 * @param localGrupo
	 * @return List de Long
	 */
	public List getFilhosById(LocalGrupoLgp localGrupo){
	    List filhos = new ArrayList();
	    if(localGrupo.getLocalGrupoHierarquiaLgphsByCodLgpPai() != null){
		    Iterator it = localGrupo.getLocalGrupoHierarquiaLgphsByCodLgpPai().iterator();
		    while(it.hasNext()){
		        LocalGrupoLgp localGrupoFilho = (LocalGrupoLgp) it.next();
		        filhos.add(localGrupoFilho.getCodLgp());
		    }	        
	    }
	    return filhos;
	}
	
	/**
	 * Retorna os Grupos acima do Grupo passado como parâmetro (pais, avôs, etc)
	 * @param localGrupo
	 * @return Set Coleção de Grupos de Local
	 * Pseudocodigo:
	 * 
	 * Ascendentes(g) {
	 * 		resultado = {conjunto vazio}
	 * 		
	 * 		se (Pais(g) != {conjunto vazio})
	 * 			para cada p em Pais(g) faca 
	 * 				resultado <- {p} U Ascendentes(p)
	 * 		return (resultado) 
	 * } 
	 */
	public List getAscendentes(LocalGrupoLgp localGrupo){
	    List grupos = new ArrayList();
	    if(localGrupo.getLocalGrupoHierarquiaLgphsByCodLgp() != null){
	        // Coleção dos Pais
	        Iterator it = localGrupo.getLocalGrupoHierarquiaLgphsByCodLgp().iterator();
	        while(it.hasNext()){
	            LocalGrupoLgp localGrupoLgp = (LocalGrupoLgp) it.next();
	            if(!grupos.contains(localGrupoLgp)){
	                grupos.add(localGrupoLgp);
	                grupos.addAll(getAscendentes(localGrupoLgp));
	            }    
	        }
	    }
	    return grupos;
	}
	
	/**
	 * Exclui um grupo de Locais, verificando antes se ele possui relação com outras tabelas. Neste caso, não permite
	 * exclusão
         * @param localGrupo
	 * @throws ECARException
	 */
	public void excluir(LocalGrupoLgp localGrupo) throws ECARException {	    
	   try{
	       	boolean excluir = true;
		    if(contar(localGrupo.getLocalItemLits()) > 0){ 
		        excluir = false;
			    throw new ECARException("localGrupo.exclusao.erro.localItemLits");
		    }     
	       	if(contar(localGrupo.getLocalGrupoHierarquiaLgphsByCodLgp()) > 0){ 
		        excluir = false;
			    throw new ECARException("localGrupo.exclusao.erro.localGrupoHierarquiaLgphsByCodLgp");
		    }      			       	       	
		    if(contar(localGrupo.getLocalGrupoHierarquiaLgphsByCodLgpPai()) > 0){ 
		        excluir = false;
			    throw new ECARException("localGrupo.exclusao.erro.localGrupoHierarquiaLgphsByCodLgpPai");
		    }      			       

		    if(excluir)
		        super.excluir(localGrupo);	
	   }catch(ECARException e){
		   this.logger.error(e);
	       throw e;
	   }    
	}

	/**
	 * verifica duplicação depois salva
	 * @param grupo
	 * @throws ECARException
	 */
	public void salvar(LocalGrupoLgp grupo) throws ECARException {
		if (pesquisarDuplos(grupo, new String[] {"identificacaoLgp"}, "codLgp").size() > 0)
		    throw new ECARException("localGrupo.validacao.registroDuplicado");
		super.salvar(grupo);
	}
	
	/**
	 * verifica duplicação depois altera
	 * @param grupo
	 * @throws ECARException
	 */
	public void alterar(LocalGrupoLgp grupo) throws ECARException {
		if (pesquisarDuplos(grupo, new String[] {"identificacaoLgp"}, "codLgp").size() > 0)
		    throw new ECARException("localGrupo.validacao.registroDuplicado");
		super.alterar(grupo);
	}
	
	/**
	 * Retorna a lista de LocalGrupoLgpPais (1o nível) ativos de um grupo
	 * @param grupo
	 * @return List LocalGrupoLgp
	 * @throws HibernateException 
	 */
	public List getGruposPais(LocalGrupoLgp grupo) throws HibernateException {
		List grupos = new ArrayList();
		
		if (grupo.getLocalGrupoHierarquiaLgphsByCodLgp() != null) {
			grupos = new ArrayList(grupo.getLocalGrupoHierarquiaLgphsByCodLgp());
			Iterator it = grupos.iterator();
			while(it.hasNext()) {
				LocalGrupoLgp lgp = (LocalGrupoLgp)it.next();
				if(!"S".equals(lgp.getIndAtivoLgp())) {
					it.remove();
				}
			}
	        Collections.sort(grupos,
		            new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			return ((LocalGrupoLgp) o1).getIdentificacaoLgp().compareToIgnoreCase(((LocalGrupoLgp) o2).getIdentificacaoLgp());
		        		}
		    		} );
		}
		
		return grupos;
	}
	
	/**
	 * Retorna a lista de LocalGrupoLgpFilhos (1o nível) ativos de um grupo
	 * @param grupo
	 * @return List LocalGrupoLgp
	 * @throws HibernateException 
	 */
	public List getGruposFilhos(LocalGrupoLgp grupo) throws HibernateException {
		List grupos = new ArrayList();
		
		if (grupo.getLocalGrupoHierarquiaLgphsByCodLgpPai() != null) {
			grupos = new ArrayList(grupo.getLocalGrupoHierarquiaLgphsByCodLgpPai());
			Iterator it = grupos.iterator();
			while(it.hasNext()) {
				LocalGrupoLgp lgp = (LocalGrupoLgp)it.next();
				if(!"S".equals(lgp.getIndAtivoLgp())) {
					it.remove();
				}
			}
	        Collections.sort(grupos,
		            new Comparator() {
		        		public int compare(Object o1, Object o2) {
		        			return ((LocalGrupoLgp) o1).getIdentificacaoLgp().compareToIgnoreCase(((LocalGrupoLgp) o2).getIdentificacaoLgp());
		        		}
		    		} );
		}
		
		return grupos;
	}
	
	public List<LocalGrupoLgp> getGrupoLocaisItem(){
		List<LocalGrupoLgp> grupos = new ArrayList();
		
		return grupos;
	}
	
	
}
