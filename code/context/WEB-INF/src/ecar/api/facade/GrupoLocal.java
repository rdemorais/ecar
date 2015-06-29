package ecar.api.facade;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import comum.database.HibernateUtil;

import ecar.dao.LocalGrupoDao;
import ecar.exception.ECARException;
import ecar.pojo.LocalGrupoLgp;

/**
 * Classe wrapper para ler do banco de dados 
 * o tipos de locais cadastrados i.e: estado,
 * município, bairro, etc
 * 
 * @see LocalGrupoDao
 * 
 * @author 82035644020
 *
 */
public class GrupoLocal {

	private Map<Integer, String> grupoLocais = null;
	private static final GrupoLocal instance = new GrupoLocal();
	public static final String NAO_DEFINIDO = "Não Identificado";
	
	
	private GrupoLocal(){}
	
	/**
	 * Retorna uma instância de GrupoLocal	
	 * @return
	 */
	public static GrupoLocal getInstance(){
		return instance;
	}
	
	/**
	 * Busca os dados da tabela tb_local_grupo_lgp
	 */
	private void fetch(){
		grupoLocais = new HashMap<Integer, String>();

		Session session = HibernateUtil.currentSession();
		Query myQuery = session.createSQLQuery("select grupo.cod_lgp, grupo.identificacao_lgp from tb_local_grupo_lgp as grupo");
		
		List list = myQuery.list();

		if(list != null && list.size() > 0){
			for(Object obj: list){
				Object[] array = (Object[]) obj;
				try {
				 Integer id = Integer.parseInt(array[0].toString());
				 String nome = (String)array[1];
				 grupoLocais.put(id, nome);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * Retorna o nome do grupo dado o codLgp
	 * @param codLgp
	 * @return
	 */
	public String getNomeGrupo(int codLgp){
		if(grupoLocais == null){
			fetch();
		}
		
		if(grupoLocais.get(codLgp) == null){
			return NAO_DEFINIDO;
		}
		
		return grupoLocais.get(codLgp); 
	}

	
	/**
	 * Percorre recursivamente a estrutura de hierarquias.
	 * @param grupo
	 * @param buffer
	 */
	private void getInferior(LocalGrupoLgp grupo, Set<String> buffer){
		LocalGrupoDao dao =  new LocalGrupoDao(null);
		if(dao.getGruposFilhos(grupo) == null){
			return;
		}else{
			for(Object filho: dao.getGruposFilhos(grupo)){
				buffer.add(((LocalGrupoLgp)filho).getIdentificacaoLgp());
				getInferior((LocalGrupoLgp)filho, buffer);
			}
		}
	}
	
	
	/**
	 * Retorna um conjunto contendo os grupos abaixo do id do grupo 
	 * passado como argumento.
	 * 
	 * 
	 * @param codLgp
	 * @return
	 * @throws ECARException
	 */
	public Set<String> getHierarquiaInferior(int codLgp) throws ECARException{
		
		LocalGrupoDao dao =  new LocalGrupoDao(null);
		LocalGrupoLgp grupo = (LocalGrupoLgp) dao.buscar(LocalGrupoLgp.class, (long)codLgp);

		Set<String> buffer = new HashSet<String>();
		buffer.add(grupo.getIdentificacaoLgp());
		getInferior(grupo, buffer);
		return buffer;
	}
	
}
